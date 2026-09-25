export interface ServiceCategory {
  categoryId: number;
  name: string;
  slug: string;
  sortOrder: number;
}

export interface ServiceCategoryRequest {
  categoryId: number | null;
  name: string;
  slug?: string | null;
  sortOrder?: number;
}

/** A service as it appears in lists and cards (`FirmServiceSummaryResponse.java`). */
export interface FirmServiceSummary {
  serviceId: number;
  title: string;
  slug: string;
  summary: string;
  icon: string | null;
  categoryId: number | null;
  categoryName: string | null;
  categorySlug: string | null;
  featured: boolean;
  active: boolean;
  sortOrder: number;
}

/** A service page (`FirmServiceResponse.java`). */
export interface FirmServiceDetail extends FirmServiceSummary {
  body: string | null;
  metaTitle: string | null;
  metaDescription: string | null;
  highlights: string[];
  /** Up to four other services in the same category. */
  relatedServices: FirmServiceSummary[];
}

/** `FirmServiceRequest.java`. Leave `serviceId` null to create. `highlights` replaces the list. */
export interface FirmServiceRequest {
  serviceId: number | null;
  categoryId?: number | null;
  title: string;
  slug?: string | null;
  summary: string;
  body?: string | null;
  icon?: string | null;
  metaTitle?: string | null;
  metaDescription?: string | null;
  featured?: boolean;
  active?: boolean;
  sortOrder?: number;
  highlights?: string[];
}
