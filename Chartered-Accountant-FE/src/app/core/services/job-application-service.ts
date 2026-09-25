import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult, PageResponse, emptyPage } from '../http/api-response';
import {
  JobApplication,
  JobApplicationQuery,
  JobApplicationRequest,
  JobApplicationStatusRequest,
} from '../models';

/** Applications against job openings (JobApplicationController). */
@Injectable({ providedIn: 'root' })
export class JobApplicationService {
  private readonly api = inject(ApiClient);

  // ---------------- Public ----------------

  /** Multipart: the `application` JSON part plus an optional `resume` file (max 5 MB). */
  apply(request: JobApplicationRequest, resume?: File | null): Observable<CommandResult> {
    const form = new FormData();
    form.append('application', new Blob([JSON.stringify(request)], { type: 'application/json' }));
    if (resume) form.append('resume', resume, resume.name);
    return this.api.command(API.JobApplication.APPLY_FOR_JOB, form);
  }

  // ---------------- Back office ----------------

  list(query: JobApplicationQuery = {}): Observable<PageResponse<JobApplication>> {
    return this.api
      .get<PageResponse<JobApplication>>(API.JobApplication.GET_ALL_APPLICATIONS, { ...query })
      .pipe(map((page) => page ?? emptyPage<JobApplication>()));
  }

  updateStatus(request: JobApplicationStatusRequest): Observable<CommandResult> {
    return this.api.command(API.JobApplication.UPDATE_APPLICATION_STATUS, request);
  }

  downloadResume(applicationId: number): Observable<Blob> {
    return this.api.download(API.JobApplication.DOWNLOAD_RESUME(applicationId));
  }
}
