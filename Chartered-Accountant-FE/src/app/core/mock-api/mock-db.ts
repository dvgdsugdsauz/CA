/*
 * In-memory stand-in for the backend, in the same shapes as its response DTOs and
 * seeded like V2__seed_content.sql. Used only when `environment.useMockApi` is true
 * (`npm run start:mock`). All names, numbers and addresses are placeholders.
 */
import {
  AdminUser,
  ENQUIRY_STATUSES,
  ArticleSummary,
  ComplianceDeadlineRequest,
  Enquiry,
  Faq,
  FirmServiceDetail,
  Industry,
  OfficeLocation,
  ServiceCategory,
  Testimonial,
} from '../models';

export const LOCATIONS: OfficeLocation[] = [
  location(1, 'Hyderabad', 'hyderabad', true, {
    heroHeading: 'Audit, tax and compliance for Hyderabad businesses',
    intro:
      'From GST returns for a Kondapur retailer to statutory audit for a Financial District subsidiary, our Hyderabad team handles the numbers so founders and finance heads can focus on running the business.',
    areasServed: ['Gachibowli', 'Madhapur', 'HITEC City', 'Financial District', 'Kondapur', 'Banjara Hills'],
    addressLine: '4th Floor, Example Towers, Road No. 2, Madhapur, Hyderabad 500081',
    phone: '+91 40 0000 0000',
    email: 'hyderabad@yourfirm.in',
  }),
  location(2, 'Bengaluru', 'bengaluru', false, {
    heroHeading: 'Audit, tax and compliance for Bengaluru businesses',
    intro:
      'Our Bengaluru team works with startups, SaaS companies and GCCs on audit, direct tax, GST and company law.',
    areasServed: ['Koramangala', 'Indiranagar', 'HSR Layout', 'Whitefield', 'Outer Ring Road'],
    addressLine: '2nd Floor, Sample House, 80 Feet Road, Koramangala, Bengaluru 560034',
    phone: '+91 80 0000 0000',
    email: 'bengaluru@yourfirm.in',
  }),
];

function location(
  locationId: number,
  city: string,
  slug: string,
  headOffice: boolean,
  details: Pick<OfficeLocation, 'heroHeading' | 'intro' | 'areasServed' | 'addressLine' | 'phone' | 'email'>,
): OfficeLocation {
  return {
    locationId,
    city,
    slug,
    headOffice,
    ...details,
    officeHours: 'Mon-Sat, 9:30 am - 6:30 pm',
    mapUrl: null,
    metaTitle: null,
    metaDescription: null,
    active: true,
    sortOrder: locationId,
  };
}

export const CATEGORIES: ServiceCategory[] = [
  { categoryId: 1, name: 'Assurance', slug: 'assurance', sortOrder: 1 },
  { categoryId: 2, name: 'Taxation', slug: 'taxation', sortOrder: 2 },
  { categoryId: 3, name: 'Company Law', slug: 'company-law', sortOrder: 3 },
  { categoryId: 4, name: 'Advisory & Outsourcing', slug: 'advisory', sortOrder: 4 },
];

export const SERVICES: FirmServiceDetail[] = [
  service(1, 'Statutory Audit', 'statutory-audit', 'audit', 1,
    'Audits under the Companies Act, 2013 planned around your year-end, with clear management letters.',
    [
      'Statutory audit under the Companies Act, 2013',
      'Tax audit under section 44AB and Form 3CD',
      'Management letter on control gaps and adjustments',
      'Coordination with your AGM and ROC filing timelines',
    ]),
  service(2, 'Internal Audit', 'internal-audit', 'internal-audit', 1,
    'Risk-based reviews of your processes, controls and fraud exposure, reported to the board.',
    [
      'Risk assessment and annual internal audit plan',
      'Process and internal financial controls testing',
      'Quarterly reports to the audit committee',
    ]),
  service(3, 'Income Tax & Consulting', 'income-tax', 'tax', 2,
    'Return filing, tax planning, assessments and appeals for individuals and companies.',
    [
      'Income tax returns for individuals, firms and companies',
      'Advance tax and TDS compliance',
      'Scrutiny assessments, rectifications and appeals',
    ]),
  service(4, 'GST Registration & Returns', 'gst-registration', 'gst', 2,
    'GST registration, monthly and annual returns, reconciliations and notice replies.',
    [
      'New GST registration, amendments and cancellations',
      'GSTR-1, GSTR-3B and GSTR-9 / 9C filings',
      'Monthly input tax credit reconciliation with GSTR-2B',
      'Replies to GST notices and audit queries',
    ],
    'From the first registration to GSTR-9 annual returns, we keep your GST filings on time and reconcile input tax credit against GSTR-2B every month.'),
  service(5, 'Transfer Pricing & International Tax', 'transfer-pricing', 'globe', 2,
    'Benchmarking studies, Form 3CEB, DTAA advice and cross-border structuring.',
    [
      'Transfer pricing documentation and benchmarking',
      'Form 3CEB certification',
      'DTAA and withholding tax advice',
    ]),
  service(6, 'Company Registration', 'company-registration', 'building', 3,
    'Private limited, LLP and subsidiary incorporation, with PAN, TAN and GST in one go.',
    [
      'Private limited, LLP and Indian subsidiary incorporation',
      'PAN, TAN, GST and bank account set-up',
      'First-year board meetings and ROC filings',
    ]),
  service(7, 'Virtual CFO Services', 'virtual-cfo', 'chart', 4,
    'Monthly MIS, budgeting, cash-flow forecasting and investor reporting without a full-time CFO.',
    [
      'Monthly MIS and management accounts',
      'Budgets and cash-flow forecasts',
      'Board and investor reporting',
    ]),
  service(8, 'Payroll Services', 'payroll', 'payroll', 4,
    'Salary processing, payslips, TDS on salary, PF, ESI and professional tax filings.',
    [
      'Monthly salary processing and payslips',
      'TDS on salary, Form 16 and quarterly returns',
      'PF, ESI and professional tax filings',
    ]),
];

function service(
  serviceId: number,
  title: string,
  slug: string,
  icon: string,
  categoryId: number,
  summary: string,
  highlights: string[],
  body = summary,
): FirmServiceDetail {
  const category = CATEGORIES.find((c) => c.categoryId === categoryId)!;
  return {
    serviceId,
    title,
    slug,
    icon,
    summary,
    categoryId,
    categoryName: category.name,
    categorySlug: category.slug,
    featured: true,
    active: true,
    sortOrder: serviceId,
    body,
    highlights,
    metaTitle: null,
    metaDescription: null,
    relatedServices: [],
  };
}

export const INDUSTRIES: Industry[] = [
  ['Information Technology', 'chip', 'SaaS, IT services and GCCs'],
  ['Pharmaceutical', 'flask', 'Manufacturers, CROs and distributors'],
  ['Real Estate', 'building', 'Developers, RERA and JDAs'],
  ['Retail', 'store', 'Multi-store and franchise retail'],
  ['E-commerce', 'cart', 'Marketplace sellers and D2C brands'],
  ['Automobile', 'car', 'Dealerships and component makers'],
  ['Hotels & Restaurants', 'cup', 'Hotels, QSRs and cloud kitchens'],
  ['Media & Entertainment', 'film', 'Production houses and studios'],
].map(([name, icon, summary], i) => ({
  industryId: i + 1,
  name,
  slug: name.toLowerCase().replace(/[^a-z0-9]+/g, '-'),
  icon,
  summary,
  sortOrder: i + 1,
}));

/** FAQ rows plus the slugs the public endpoint filters on. */
export type FaqRecord = Faq & { locationSlug: string | null; serviceSlug: string | null };

export const FAQS: FaqRecord[] = [
  faq(1, 'Which areas of Hyderabad do you serve?', 'Our office is in Madhapur and we regularly work with clients in Gachibowli, HITEC City, the Financial District, Kondapur and Banjara Hills. Most work is done online, so we also support clients elsewhere in Telangana.', 1),
  faq(2, 'Can you help with GST registration in Hyderabad?', 'Yes. We prepare the application, upload documents on the GST portal and follow up until the GSTIN is issued. We can then take over your monthly returns.', 1),
  faq(3, 'How do I book a consultation?', 'Use the enquiry form on this page or call the office. A chartered accountant will call you back within one working day to understand your requirement.'),
  faq(4, 'Do you work with startups?', 'Yes. We help startups with incorporation, DPIIT recognition, bookkeeping, payroll and investor reporting, and scale the engagement as the company grows.'),
  faq(5, 'Can a chartered accountant file my income tax return?', 'Yes. We prepare and file returns for salaried individuals, professionals, firms and companies, and handle any notices that follow.'),
  faq(6, 'Why hire a chartered accountant instead of doing compliance in-house?', 'A CA keeps track of changing laws and due dates, reduces penalties and interest, and gives you an independent view of your numbers that banks and investors rely on.'),
  faq(7, 'Can you help with GST registration in Hyderabad?', 'Yes. We prepare the application, upload documents on the GST portal and follow up until the GSTIN is issued. We can then take over your monthly returns.', null, 4),
  faq(8, 'What documents do I need for registration?', 'PAN of the business, proof of address for the place of business, identity and address proof of the promoters, and bank account details.', null, 4),
];

function faq(
  faqId: number,
  question: string,
  answer: string,
  locationId: number | null = null,
  serviceId: number | null = null,
): FaqRecord {
  const loc = LOCATIONS.find((l) => l.locationId === locationId);
  const svc = SERVICES.find((s) => s.serviceId === serviceId);
  return {
    faqId,
    question,
    answer,
    locationId,
    city: loc?.city ?? null,
    serviceId,
    serviceTitle: svc?.title ?? null,
    active: true,
    sortOrder: faqId,
    locationSlug: loc?.slug ?? null,
    serviceSlug: svc?.slug ?? null,
  };
}

export const TESTIMONIALS: Testimonial[] = [
  ['Example: the team closed our audit two weeks ahead of the AGM and explained every adjustment.', 'Director, SaaS company, Madhapur'],
  ['Example: GST reconciliations every month mean we no longer lose input credit.', 'Founder, D2C brand, Kondapur'],
  ['Example: one point of contact and weekly updates made the transition easy.', 'Finance head, pharma distributor'],
].map(([quote, authorTitle], i) => ({
  testimonialId: i + 1,
  authorName: 'Sample client',
  authorTitle,
  quote,
  rating: 5,
  active: true,
  sortOrder: i + 1,
}));

export const ARTICLES: ArticleSummary[] = [
  article(1, 'tax-audit-checklist', 'Tax audit checklist before 30 September', 'The documents and reconciliations to have ready before your tax auditor starts on Form 3CD.', 'Income Tax', '2026-09-10T09:00:00'),
  article(2, 'itc-reconciliation-gstr-2b', 'Reconciling input tax credit with GSTR-2B', 'Why a monthly ITC match matters and a simple process for doing it.', 'GST', '2026-08-22T09:00:00'),
  article(3, 'first-year-compliance-private-limited', 'First-year compliance for a new private limited company', 'Board meetings, auditor appointment and filings a new company must complete in year one.', 'Company Law', '2026-07-30T09:00:00'),
];

function article(
  articleId: number,
  slug: string,
  title: string,
  summary: string,
  category: string,
  publishedAt: string,
): ArticleSummary {
  return { articleId, slug, title, summary, category, author: 'Apex & Associates', status: 'PUBLISHED', publishedAt };
}

export const DEADLINES: Required<ComplianceDeadlineRequest>[] = [
  ['2026-09-30', 'Tax audit report (Form 3CA/3CB-3CD) for FY 2025-26', 'INCOME_TAX', 'Businesses and professionals liable to tax audit'],
  ['2026-10-07', 'TDS / TCS deposit for September', 'TDS', 'All deductors'],
  ['2026-10-11', 'GSTR-1 for September', 'GST', 'Monthly filers'],
  ['2026-10-20', 'GSTR-3B for September', 'GST', 'Monthly filers'],
  ['2026-10-29', 'AOC-4 financial statements for FY 2025-26', 'ROC', 'Companies, within 30 days of the AGM'],
  ['2026-10-31', 'Income tax return for audit cases, FY 2025-26', 'INCOME_TAX', 'Companies and audited taxpayers'],
  ['2026-11-07', 'TDS / TCS deposit for October', 'TDS', 'All deductors'],
  ['2026-11-11', 'GSTR-1 for October', 'GST', 'Monthly filers'],
  ['2026-11-20', 'GSTR-3B for October', 'GST', 'Monthly filers'],
  ['2026-11-29', 'MGT-7 annual return for FY 2025-26', 'ROC', 'Companies, within 60 days of the AGM'],
  ['2026-11-30', 'Income tax return for transfer pricing cases', 'INCOME_TAX', 'Taxpayers filing Form 3CEB'],
  ['2026-12-31', 'GSTR-9 / 9C annual return for FY 2025-26', 'GST', 'Registered taxpayers above the threshold'],
].map(([dueDate, title, law, appliesTo], i) => ({
  deadlineId: i + 1,
  dueDate,
  title,
  law: law as ComplianceDeadlineRequest['law'],
  appliesTo,
  active: true,
}));

export const ENQUIRIES: Enquiry[] = [
  enquiry(3, '2026-09-24T10:12:00', 'Example: R. Kumar', 'Kondapur Traders', '+91 90000 00001', 'rkumar@example.com', 4, 1, 'New GST registration for a second branch.', '/', 'NEW'),
  enquiry(2, '2026-09-23T16:40:00', 'Example: A. Reddy', 'Example Labs Pvt Ltd', '+91 90000 00002', 'areddy@example.com', 1, 1, 'Statutory audit for FY 2025-26, AGM planned for December.', '/services/statutory-audit', 'CONTACTED'),
  enquiry(1, '2026-09-22T11:05:00', 'Example: S. Iyer', null, '+91 90000 00003', 'siyer@example.com', 6, 2, 'Incorporate a private limited company with two founders.', '/contact', 'PROPOSAL_SENT'),
];

export function enquiry(
  enquiryId: number,
  createdAt: string,
  fullName: string,
  companyName: string | null,
  phone: string,
  email: string,
  serviceId: number | null,
  locationId: number | null,
  message: string | null,
  sourcePage: string,
  status: Enquiry['status'],
): Enquiry {
  return {
    enquiryId,
    referenceNo: `ENQ-${createdAt.slice(0, 4)}-${String(enquiryId).padStart(6, '0')}`,
    fullName,
    email,
    phone,
    companyName,
    serviceId,
    serviceTitle: SERVICES.find((s) => s.serviceId === serviceId)?.title ?? null,
    locationId,
    city: LOCATIONS.find((l) => l.locationId === locationId)?.city ?? null,
    message,
    sourcePage,
    status,
    statusLabel: statusLabel(status),
    internalNotes: null,
    assignedToId: null,
    assignedToName: null,
    createdAt,
    updatedAt: createdAt,
  };
}

export function statusLabel(status: Enquiry['status']): string {
  return ENQUIRY_STATUSES.find((s) => s.value === status)?.label ?? status;
}

export const MOCK_ADMIN: { password: string; token: string; user: AdminUser } = {
  password: 'Admin@123',
  token: 'mock-admin-token',
  user: {
    userId: 1,
    username: 'admin',
    fullName: 'Firm Administrator',
    email: 'admin@yourfirm.in',
    role: 'ADMIN',
    enabled: true,
    createdAt: '2026-09-01T09:00:00',
    lastLoginAt: null,
  },
};

export const SUBSCRIBERS = new Set<string>();
