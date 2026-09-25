import { IsoDate, IsoDateTime } from './common';

// ------------------------------ Industry ------------------------------

export interface Industry {
  industryId: number;
  name: string;
  slug: string;
  summary: string | null;
  icon: string | null;
  sortOrder: number;
}

export interface IndustryRequest {
  industryId: number | null;
  name: string;
  slug?: string | null;
  summary?: string | null;
  icon?: string | null;
  sortOrder?: number;
}

// ------------------------------ FAQ ------------------------------

export interface Faq {
  faqId: number;
  question: string;
  answer: string;
  locationId: number | null;
  city: string | null;
  serviceId: number | null;
  serviceTitle: string | null;
  active: boolean;
  sortOrder: number;
}

export interface FaqRequest {
  faqId: number | null;
  question: string;
  answer: string;
  /** Null shows the FAQ on every city page. */
  locationId?: number | null;
  /** Null makes it a general FAQ. */
  serviceId?: number | null;
  active?: boolean;
  sortOrder?: number;
}

/** Public FAQ filter. With `serviceSlug`, only that service's FAQs; otherwise general FAQs for the city. */
export interface FaqFilter {
  locationSlug?: string;
  serviceSlug?: string;
}

// ------------------------------ Testimonial ------------------------------

export interface Testimonial {
  testimonialId: number;
  authorName: string;
  authorTitle: string | null;
  quote: string;
  rating: number | null;
  active: boolean;
  sortOrder: number;
}

export interface TestimonialRequest {
  testimonialId: number | null;
  authorName: string;
  authorTitle?: string | null;
  quote: string;
  /** 1 to 5. */
  rating?: number | null;
  active?: boolean;
  sortOrder?: number;
}

// ------------------------------ Team member ------------------------------

export interface TeamMember {
  teamMemberId: number;
  fullName: string;
  designation: string;
  qualifications: string | null;
  bio: string | null;
  photoUrl: string | null;
  locationId: number | null;
  city: string | null;
  active: boolean;
  sortOrder: number;
}

export interface TeamMemberRequest {
  teamMemberId: number | null;
  fullName: string;
  designation: string;
  qualifications?: string | null;
  bio?: string | null;
  photoUrl?: string | null;
  locationId?: number | null;
  active?: boolean;
  sortOrder?: number;
}

// ------------------------------ Article (newsletters) ------------------------------

export type ArticleStatus = 'DRAFT' | 'PUBLISHED';

export interface ArticleSummary {
  articleId: number;
  title: string;
  slug: string;
  summary: string | null;
  category: string | null;
  author: string | null;
  status: ArticleStatus;
  publishedAt: IsoDateTime | null;
}

export interface ArticleDetail extends ArticleSummary {
  body: string | null;
  createdAt: IsoDateTime;
  updatedAt: IsoDateTime;
}

export interface ArticleRequest {
  articleId: number | null;
  title: string;
  slug?: string | null;
  summary?: string | null;
  body?: string | null;
  category?: string | null;
  author?: string | null;
  status: ArticleStatus;
  /** A future time schedules the article. Defaults to now when publishing. */
  publishedAt?: IsoDateTime | null;
}

// ------------------------------ Compliance deadline ------------------------------

export type ComplianceLaw = 'GST' | 'TDS' | 'INCOME_TAX' | 'ROC';

export interface ComplianceDeadline {
  deadlineId: number;
  dueDate: IsoDate;
  /** Two-digit day, e.g. "07". */
  day: string;
  /** Upper-case short month, e.g. "OCT". */
  month: string;
  title: string;
  law: ComplianceLaw;
  lawLabel: string;
  appliesTo: string | null;
  active: boolean;
  /** True when the due date is within the next 7 days. */
  dueSoon: boolean;
}

export interface ComplianceDeadlineRequest {
  deadlineId: number | null;
  dueDate: IsoDate;
  title: string;
  law: ComplianceLaw;
  appliesTo?: string | null;
  active?: boolean;
}
