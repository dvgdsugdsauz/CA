import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult, PageResponse, emptyPage } from '../http/api-response';
import {
  Enquiry,
  EnquiryQuery,
  EnquiryRequest,
  EnquiryStatusSummary,
  EnquirySubmitResponse,
  EnquiryUpdateRequest,
} from '../models';

/** Leads from the website (EnquiryController). */
@Injectable({ providedIn: 'root' })
export class EnquiryService {
  private readonly api = inject(ApiClient);

  // ---------------- Public ----------------

  submit(request: EnquiryRequest): Observable<EnquirySubmitResponse> {
    return this.api.post<EnquirySubmitResponse>(API.Enquiry.SUBMIT_ENQUIRY, request).pipe(
      map((receipt) => {
        if (!receipt) throw new Error('Enquiry was accepted without a reference number');
        return receipt;
      }),
    );
  }

  // ---------------- Back office ----------------

  /** A page of enquiries, newest first. */
  list(query: EnquiryQuery = {}): Observable<PageResponse<Enquiry>> {
    return this.api
      .get<PageResponse<Enquiry>>(API.Enquiry.GET_ALL_ENQUIRIES, { ...query })
      .pipe(map((page) => page ?? emptyPage<Enquiry>()));
  }

  getStatusCounts(): Observable<EnquiryStatusSummary> {
    return this.api
      .get<EnquiryStatusSummary>(API.Enquiry.GET_ENQUIRY_STATUS_COUNTS)
      .pipe(map((summary) => summary ?? { total: 0, statuses: [] }));
  }

  getById(enquiryId: number): Observable<Enquiry | null> {
    return this.api.get<Enquiry>(API.Enquiry.GET_ENQUIRY_BY_ID(enquiryId));
  }

  /** Sends status, notes and assignee together. The backend overwrites all three. */
  update(request: EnquiryUpdateRequest): Observable<CommandResult> {
    return this.api.command(API.Enquiry.UPDATE_ENQUIRY, request);
  }
}
