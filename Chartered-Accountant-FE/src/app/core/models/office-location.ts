export interface OfficeLocation {
  locationId: number;
  city: string;
  slug: string;
  heroHeading: string | null;
  intro: string | null;
  areasServed: string[];
  addressLine: string;
  phone: string | null;
  email: string | null;
  officeHours: string | null;
  mapUrl: string | null;
  metaTitle: string | null;
  metaDescription: string | null;
  headOffice: boolean;
  active: boolean;
  sortOrder: number;
}

/** `OfficeLocationRequest.java`. Leave `locationId` null to create. */
export interface OfficeLocationRequest {
  locationId: number | null;
  city: string;
  slug?: string | null;
  heroHeading?: string | null;
  intro?: string | null;
  areasServed?: string[];
  addressLine: string;
  phone?: string | null;
  email?: string | null;
  officeHours?: string | null;
  mapUrl?: string | null;
  metaTitle?: string | null;
  metaDescription?: string | null;
  headOffice?: boolean;
  active?: boolean;
  sortOrder?: number;
}
