import { IsoDate, IsoDateTime, PageQuery } from './common';

export type EmploymentType = 'FULL_TIME' | 'ARTICLESHIP' | 'INTERNSHIP';

export type ApplicationStatus =
  | 'RECEIVED'
  | 'SHORTLISTED'
  | 'INTERVIEW_SCHEDULED'
  | 'OFFERED'
  | 'HIRED'
  | 'REJECTED';

export interface JobOpening {
  jobId: number;
  title: string;
  slug: string;
  locationId: number | null;
  city: string | null;
  department: string | null;
  experienceRange: string | null;
  employmentType: EmploymentType;
  employmentTypeLabel: string;
  description: string | null;
  active: boolean;
  postedAt: IsoDateTime;
  /** Last date to apply; null means open until closed. */
  closesOn: IsoDate | null;
}

export interface JobOpeningRequest {
  jobId: number | null;
  title: string;
  slug?: string | null;
  locationId?: number | null;
  department?: string | null;
  experienceRange?: string | null;
  employmentType: EmploymentType;
  description?: string | null;
  active?: boolean;
  closesOn?: IsoDate | null;
}

/** The `application` part of the multipart apply request. The resume goes alongside it. */
export interface JobApplicationRequest {
  jobId: number;
  fullName: string;
  email: string;
  phone: string;
  qualification?: string | null;
  coverNote?: string | null;
}

export interface JobApplication {
  applicationId: number;
  jobId: number;
  jobTitle: string;
  fullName: string;
  email: string;
  phone: string;
  qualification: string | null;
  coverNote: string | null;
  status: ApplicationStatus;
  statusLabel: string;
  hasResume: boolean;
  createdAt: IsoDateTime;
}

export interface JobApplicationQuery extends PageQuery {
  jobId?: number | null;
  status?: ApplicationStatus | null;
}

export interface JobApplicationStatusRequest {
  applicationId: number;
  status: ApplicationStatus;
}
