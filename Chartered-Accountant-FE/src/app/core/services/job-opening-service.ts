import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { JobOpening, JobOpeningRequest } from '../models';

/** Careers: openings, including articleship (JobOpeningController). */
@Injectable({ providedIn: 'root' })
export class JobOpeningService {
  private readonly api = inject(ApiClient);

  // ---------------- Public ----------------

  getActive(): Observable<JobOpening[]> {
    return this.api.getList<JobOpening>(API.JobOpening.GET_ACTIVE_JOB_OPENINGS);
  }

  getBySlug(slug: string): Observable<JobOpening | null> {
    return this.api.get<JobOpening>(API.JobOpening.GET_JOB_OPENING_BY_SLUG(slug));
  }

  // ---------------- Back office ----------------

  getAll(): Observable<JobOpening[]> {
    return this.api.getList<JobOpening>(API.JobOpening.GET_ALL_JOB_OPENINGS);
  }

  getById(jobId: number): Observable<JobOpening | null> {
    return this.api.get<JobOpening>(API.JobOpening.GET_JOB_OPENING_BY_ID(jobId));
  }

  save(request: JobOpeningRequest): Observable<CommandResult> {
    return this.api.command(API.JobOpening.SAVE_JOB_OPENING, request);
  }

  delete(jobId: number): Observable<CommandResult> {
    return this.api.command(API.JobOpening.DELETE_JOB_OPENING, null, { jobId });
  }
}
