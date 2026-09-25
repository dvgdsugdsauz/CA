import {
  HttpErrorResponse,
  HttpEvent,
  HttpInterceptorFn,
  HttpRequest,
  HttpResponse,
} from '@angular/common/http';
import { Observable, delay, dematerialize, materialize, of, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { EMAIL_PATTERN, PHONE_PATTERN } from '../constants/validation';
import { API } from '../http/api-endpoints';
import { ApiResponse, PageResponse } from '../http/api-response';
import {
  ComplianceDeadline,
  EnquiryRequest,
  EnquiryStatusSummary,
  EnquiryUpdateRequest,
  FirmServiceDetail,
  FirmServiceSummary,
  ENQUIRY_STATUSES,
  isEnquiryStatus,
} from '../models';
import { daysBetween, parseIsoDate, startOfToday } from '../utils/date';
import * as db from './mock-db';

const LATENCY_MS = 250;
const MONTHS = ['JAN', 'FEB', 'MAR', 'APR', 'MAY', 'JUN', 'JUL', 'AUG', 'SEP', 'OCT', 'NOV', 'DEC'];

/** HTTP status plus the envelope the backend would send. */
interface MockResult {
  httpStatus: number;
  body: ApiResponse<unknown>;
}

type Handler = (req: HttpRequest<unknown>, params: string[]) => MockResult;

/**
 * Answers the backend's endpoints from in-memory data so the frontend runs without
 * Spring (`npm run start:mock`). Mirrors the backend's envelope, 204 "no data" and
 * error shapes. Covers the endpoints the UI uses; anything else passes through.
 */
export const mockApiInterceptor: HttpInterceptorFn = (req, next) => {
  if (!req.url.startsWith(environment.apiUrl)) return next(req);

  const path = req.url.slice(environment.apiUrl.length);
  for (const [method, pattern, handler] of ROUTES) {
    const match = req.method === method ? pattern.exec(path) : null;
    if (match) return respond(req, handler(req, match.slice(1).map(decodeURIComponent)));
  }
  return next(req);
};

function respond(req: HttpRequest<unknown>, result: MockResult): Observable<HttpEvent<unknown>> {
  const response$: Observable<HttpEvent<unknown>> =
    result.httpStatus < 400
      ? of(new HttpResponse({ status: result.httpStatus, body: result.body, url: req.url }))
      : throwError(
          () => new HttpErrorResponse({ status: result.httpStatus, error: result.body, url: req.url }),
        );
  // materialize/dematerialize so errors are delayed like successes.
  return response$.pipe(materialize(), delay(LATENCY_MS), dematerialize());
}

// ---------------- Envelope helpers, as in the backend controllers ----------------

const ok = (data: unknown = null, message = 'Data fetched successfully'): MockResult => ({
  httpStatus: 200,
  body: { statusCode: 200, message, data },
});
const noContent = (data: unknown = null): MockResult => ({
  httpStatus: 200,
  body: { statusCode: 204, message: 'Data not available', data },
});
const list = (rows: unknown[]): MockResult => (rows.length ? ok(rows) : noContent());
const error = (status: number, message: string, data: unknown = null): MockResult => ({
  httpStatus: status,
  body: { statusCode: status, message, data },
});
const validationFailed = (errors: Record<string, string>) =>
  error(400, 'Please correct the highlighted fields', errors);
const unauthorized = () => error(401, 'Please sign in to continue');

function page<T>(rows: T[], req: HttpRequest<unknown>): MockResult {
  const pageNo = Math.max(0, Number(req.params.get('page') ?? 0) || 0);
  const size = Math.min(100, Math.max(1, Number(req.params.get('size') ?? 20) || 20));
  const body: PageResponse<T> = {
    content: rows.slice(pageNo * size, pageNo * size + size),
    page: pageNo,
    size,
    totalElements: rows.length,
    totalPages: Math.ceil(rows.length / size),
  };
  return rows.length ? ok(body) : noContent(body);
}

// ---------------- Helpers ----------------

/** Regex for an endpoint with one path variable, e.g. API.FirmService.GET_SERVICE_BY_SLUG. */
function withParam(build: (value: string) => string): RegExp {
  return new RegExp(`^${build('__PARAM__').replace('__PARAM__', '([^/]+)')}$`);
}

const exact = (path: string) => new RegExp(`^${path}$`);

function toSummary({
  body,
  highlights,
  metaTitle,
  metaDescription,
  relatedServices,
  ...summary
}: FirmServiceDetail): FirmServiceSummary {
  return summary;
}

function isSignedIn(req: HttpRequest<unknown>): boolean {
  return req.headers.get('Authorization') === `Bearer ${db.MOCK_ADMIN.token}`;
}

function required(value: unknown): string {
  return typeof value === 'string' ? value.trim() : '';
}

// Same messages as EnquiryRequest.java.
function validateEnquiry(body: Partial<EnquiryRequest>): Record<string, string> {
  const errors: Record<string, string> = {};
  const phone = required(body.phone);
  const email = required(body.email);
  if (!required(body.fullName)) errors['fullName'] = 'Enter your name';
  if (!phone) errors['phone'] = 'Enter a phone number so we can call you back';
  else if (!PHONE_PATTERN.test(phone)) errors['phone'] = 'Use digits only, e.g. +91 98765 43210';
  if (!email) errors['email'] = 'Enter your email address';
  else if (!EMAIL_PATTERN.test(email)) errors['email'] = 'Enter a valid email address, like name@company.com';
  return errors;
}

function toDeadline(row: (typeof db.DEADLINES)[number]): ComplianceDeadline {
  const date = parseIsoDate(row.dueDate);
  const days = daysBetween(startOfToday(), date);
  return {
    deadlineId: row.deadlineId!,
    dueDate: row.dueDate,
    day: String(date.getDate()).padStart(2, '0'),
    month: MONTHS[date.getMonth()],
    title: row.title,
    law: row.law,
    lawLabel: row.law === 'INCOME_TAX' ? 'Income Tax' : row.law,
    appliesTo: row.appliesTo,
    active: row.active,
    dueSoon: days >= 0 && days <= 7,
  };
}

// ---------------- Routes ----------------

const ROUTES: [string, RegExp, Handler][] = [
  ['GET', exact(API.OfficeLocation.GET_ALL_ACTIVE_LOCATIONS), () => list(db.LOCATIONS)],

  ['GET', exact(API.FirmService.GET_ALL_ACTIVE_SERVICES), () => list(db.SERVICES.map(toSummary))],

  ['GET', exact(API.FirmService.GET_SERVICE_DROPDOWN), () =>
    list(db.SERVICES.map((s) => ({ id: s.serviceId, name: s.title }))),
  ],

  ['GET', withParam(API.FirmService.GET_SERVICE_BY_SLUG), (_req, [slug]) => {
    const found = db.SERVICES.find((s) => s.slug === slug);
    if (!found) return noContent();
    const relatedServices = db.SERVICES.filter(
      (s) => s.categoryId === found.categoryId && s.serviceId !== found.serviceId,
    )
      .slice(0, 4)
      .map(toSummary);
    return ok({ ...found, relatedServices });
  }],

  ['GET', exact(API.Industry.GET_ALL_INDUSTRIES), () => list(db.INDUSTRIES)],

  ['GET', exact(API.Testimonial.GET_ACTIVE_TESTIMONIALS), () => list(db.TESTIMONIALS)],

  ['GET', exact(API.Article.GET_PUBLISHED_ARTICLES), (req) => page(db.ARTICLES, req)],

  ['GET', exact(API.Faq.GET_ACTIVE_FAQS), (req) => {
    const service = req.params.get('serviceSlug');
    const location = req.params.get('locationSlug');
    const rows = service
      ? db.FAQS.filter((f) => f.serviceSlug === service)
      : db.FAQS.filter((f) => !f.serviceSlug && (!f.locationSlug || f.locationSlug === location));
    return list(rows.map(({ locationSlug, serviceSlug, ...faq }) => faq));
  }],

  ['GET', exact(API.ComplianceDeadline.GET_UPCOMING_DEADLINES), (req) => {
    const limit = Math.min(20, Math.max(1, Number(req.params.get('limit') ?? 5) || 5));
    const today = startOfToday();
    return list(
      db.DEADLINES.filter((d) => parseIsoDate(d.dueDate) >= today)
        .slice(0, limit)
        .map(toDeadline),
    );
  }],

  ['POST', exact(API.Enquiry.SUBMIT_ENQUIRY), (req) => {
    const body = (req.body ?? {}) as Partial<EnquiryRequest>;
    const errors = validateEnquiry(body);
    if (Object.keys(errors).length) return validationFailed(errors);

    const id = Math.max(0, ...db.ENQUIRIES.map((e) => e.enquiryId)) + 1;
    const now = new Date().toISOString().slice(0, 19);
    const created = db.enquiry(
      id, now, required(body.fullName), body.companyName || null, required(body.phone),
      required(body.email), body.serviceId ?? null, body.locationId ?? null,
      body.message || null, body.sourcePage ?? '/', 'NEW',
    );
    db.ENQUIRIES.unshift(created);
    return ok({ referenceNo: created.referenceNo }, 'Thank you. A chartered accountant will call you back within one working day');
  }],

  ['POST', exact(API.Newsletter.SUBSCRIBE), (req) => {
    const email = required((req.body as { email?: string } | null)?.email).toLowerCase();
    if (!email) return validationFailed({ email: 'Enter your email address' });
    if (!EMAIL_PATTERN.test(email)) return validationFailed({ email: 'Enter a valid email address to subscribe' });
    const isNew = !db.SUBSCRIBERS.has(email);
    db.SUBSCRIBERS.add(email);
    return ok(null, isNew ? 'Subscribed to tax updates' : 'This email is already subscribed');
  }],

  ['POST', exact(API.Auth.LOGIN), (req) => {
    const { username, password } = (req.body ?? {}) as { username?: string; password?: string };
    if (username !== db.MOCK_ADMIN.user.username || password !== db.MOCK_ADMIN.password) {
      return error(401, 'Invalid username or password');
    }
    return ok(
      { accessToken: db.MOCK_ADMIN.token, tokenType: 'Bearer', expiresIn: 8 * 3600, user: db.MOCK_ADMIN.user },
      'Login successful',
    );
  }],

  ['GET', exact(API.Auth.GET_PROFILE), (req) => (isSignedIn(req) ? ok(db.MOCK_ADMIN.user) : unauthorized())],

  ['GET', exact(API.AdminUser.GET_USER_DROPDOWN), (req) =>
    isSignedIn(req) ? list([{ id: db.MOCK_ADMIN.user.userId, name: db.MOCK_ADMIN.user.fullName }]) : unauthorized(),
  ],

  ['GET', exact(API.Enquiry.GET_ALL_ENQUIRIES), (req) => {
    if (!isSignedIn(req)) return unauthorized();
    const status = req.params.get('status');
    const search = req.params.get('search')?.trim().toLowerCase();
    const rows = db.ENQUIRIES.filter(
      (e) =>
        (!status || e.status === status) &&
        (!search ||
          [e.fullName, e.email, e.phone, e.companyName ?? '', e.referenceNo].some((v) =>
            v.toLowerCase().includes(search),
          )),
    );
    return page(rows, req);
  }],

  ['GET', exact(API.Enquiry.GET_ENQUIRY_STATUS_COUNTS), (req) => {
    if (!isSignedIn(req)) return unauthorized();
    const summary: EnquiryStatusSummary = {
      total: db.ENQUIRIES.length,
      statuses: ENQUIRY_STATUSES.map(({ value, label }) => ({
        status: value,
        label,
        count: db.ENQUIRIES.filter((e) => e.status === value).length,
      })),
    };
    return ok(summary);
  }],

  ['POST', exact(API.Enquiry.UPDATE_ENQUIRY), (req) => {
    if (!isSignedIn(req)) return unauthorized();
    const body = (req.body ?? {}) as Partial<EnquiryUpdateRequest>;
    if (!isEnquiryStatus(body.status)) return validationFailed({ status: 'Select a status' });
    const found = db.ENQUIRIES.find((e) => e.enquiryId === body.enquiryId);
    if (!found) return noContent();
    Object.assign(found, {
      status: body.status,
      statusLabel: db.statusLabel(body.status),
      internalNotes: body.internalNotes?.trim() || null,
      assignedToId: body.assignedToId ?? null,
      assignedToName: body.assignedToId === db.MOCK_ADMIN.user.userId ? db.MOCK_ADMIN.user.fullName : null,
      updatedAt: new Date().toISOString().slice(0, 19),
    });
    return ok(null, 'Data updated successfully');
  }],
];
