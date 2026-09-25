import { HttpClient, provideHttpClient, withInterceptors } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { firstValueFrom } from 'rxjs';
import { apiFieldErrors, isHttpStatus } from '../http/api-error';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { ApiResponse } from '../http/api-response';
import { FirmServiceDetail } from '../models';
import { mockApiInterceptor } from './mock-api-interceptor';

describe('mockApiInterceptor', () => {
  let api: ApiClient;
  let http: HttpClient;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(withInterceptors([mockApiInterceptor]))],
    });
    api = TestBed.inject(ApiClient);
    http = TestBed.inject(HttpClient);
  });

  it('wraps data in the backend envelope', async () => {
    const res = await firstValueFrom(
      http.get<ApiResponse<unknown[]>>(`/api${API.Industry.GET_ALL_INDUSTRIES}`),
    );
    expect(res.statusCode).toBe(200);
    expect(res.data?.length).toBe(8);
  });

  it('serves a service page with related services from the same category', async () => {
    const service = await firstValueFrom(
      api.get<FirmServiceDetail>(API.FirmService.GET_SERVICE_BY_SLUG('gst-registration')),
    );
    expect(service?.title).toBe('GST Registration & Returns');
    expect(service?.relatedServices.every((s) => s.categoryId === service.categoryId)).toBe(true);
  });

  it('answers 204 for an unknown service, like the backend', async () => {
    const service = await firstValueFrom(api.get(API.FirmService.GET_SERVICE_BY_SLUG('nope')));
    expect(service).toBeNull();
  });

  it('validates enquiries with the backend messages', async () => {
    const error = await firstValueFrom(
      api.post(API.Enquiry.SUBMIT_ENQUIRY, { fullName: '', email: 'x', phone: '' }),
    ).catch((e: unknown) => e);

    expect(isHttpStatus(error, 400)).toBe(true);
    expect(apiFieldErrors(error)).toEqual({
      fullName: 'Enter your name',
      phone: 'Enter a phone number so we can call you back',
      email: 'Enter a valid email address, like name@company.com',
    });
  });

  it('issues a reference number for a valid enquiry', async () => {
    const receipt = await firstValueFrom(
      api.post<{ referenceNo: string }>(API.Enquiry.SUBMIT_ENQUIRY, {
        fullName: 'Priya Sharma',
        email: 'priya@example.com',
        phone: '+91 98765 43210',
        serviceId: 4,
        locationId: 1,
        sourcePage: '/',
      }),
    );
    expect(receipt?.referenceNo).toMatch(/^ENQ-\d{4}-\d{6}$/);
  });

  it('keeps the back office behind sign-in', async () => {
    const error = await firstValueFrom(api.get(API.Enquiry.GET_ALL_ENQUIRIES)).catch((e) => e);
    expect(isHttpStatus(error, 401)).toBe(true);
  });
});
