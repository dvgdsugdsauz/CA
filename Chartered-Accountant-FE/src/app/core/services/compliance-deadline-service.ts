import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { ComplianceDeadline, ComplianceDeadlineRequest } from '../models';

/** The due-date calendar (ComplianceDeadlineController). */
@Injectable({ providedIn: 'root' })
export class ComplianceDeadlineService {
  private readonly api = inject(ApiClient);

  // ---------------- Public ----------------

  /** The next `limit` due dates from today (backend default 5, max 20). */
  getUpcoming(limit?: number): Observable<ComplianceDeadline[]> {
    return this.api.getList<ComplianceDeadline>(API.ComplianceDeadline.GET_UPCOMING_DEADLINES, {
      limit,
    });
  }

  // ---------------- Back office ----------------

  getAll(): Observable<ComplianceDeadline[]> {
    return this.api.getList<ComplianceDeadline>(API.ComplianceDeadline.GET_ALL_DEADLINES);
  }

  save(request: ComplianceDeadlineRequest): Observable<CommandResult> {
    return this.api.command(API.ComplianceDeadline.SAVE_DEADLINE, request);
  }

  delete(deadlineId: number): Observable<CommandResult> {
    return this.api.command(API.ComplianceDeadline.DELETE_DEADLINE, null, { deadlineId });
  }
}
