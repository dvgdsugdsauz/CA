import { IsoDateTime, PageQuery } from './common';

export type EnquiryStatus = 'NEW' | 'CONTACTED' | 'PROPOSAL_SENT' | 'CONVERTED' | 'CLOSED';

/** Same order and labels as EnquiryStatus.java. */
export const ENQUIRY_STATUSES: readonly { value: EnquiryStatus; label: string }[] = [
  { value: 'NEW', label: 'New' },
  { value: 'CONTACTED', label: 'Contacted' },
  { value: 'PROPOSAL_SENT', label: 'Proposal sent' },
  { value: 'CONVERTED', label: 'Converted' },
  { value: 'CLOSED', label: 'Closed' },
];

export function isEnquiryStatus(value: unknown): value is EnquiryStatus {
  return ENQUIRY_STATUSES.some((s) => s.value === value);
}

/** Payload the public enquiry forms send (`EnquiryRequest.java`). */
export interface EnquiryRequest {
  fullName: string;
  phone: string;
  email: string;
  companyName: string | null;
  /** From the service dropdown; null means "Not sure yet". */
  serviceId: number | null;
  /** The city page the form was sent from; null elsewhere. */
  locationId: number | null;
  message: string | null;
  /** Angular route the form was sent from, e.g. / or /services/gst-registration. */
  sourcePage: string;
}

export interface EnquirySubmitResponse {
  referenceNo: string;
}

/** An enquiry as the back office sees it (`EnquiryResponse.java`). */
export interface Enquiry {
  enquiryId: number;
  referenceNo: string;
  fullName: string;
  email: string;
  phone: string;
  companyName: string | null;
  serviceId: number | null;
  serviceTitle: string | null;
  locationId: number | null;
  city: string | null;
  message: string | null;
  sourcePage: string | null;
  status: EnquiryStatus;
  statusLabel: string;
  internalNotes: string | null;
  assignedToId: number | null;
  assignedToName: string | null;
  createdAt: IsoDateTime;
  updatedAt: IsoDateTime;
}

export interface EnquiryQuery extends PageQuery {
  status?: EnquiryStatus | null;
  /** Matches name, email, phone, company or reference number. */
  search?: string | null;
}

export interface EnquiryStatusSummary {
  total: number;
  statuses: { status: EnquiryStatus; label: string; count: number }[];
}

/**
 * `EnquiryUpdateRequest.java`. The backend overwrites notes and assignee on every
 * update, so always send their current values.
 */
export interface EnquiryUpdateRequest {
  enquiryId: number;
  status: EnquiryStatus;
  internalNotes: string | null;
  assignedToId: number | null;
}
