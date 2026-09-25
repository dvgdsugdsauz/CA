import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { Faq, FaqFilter, FaqRequest } from '../models';

/** FAQs for city and service pages (FaqController). */
@Injectable({ providedIn: 'root' })
export class FaqService {
  private readonly api = inject(ApiClient);

  // ---------------- Public ----------------

  getActive(filter: FaqFilter = {}): Observable<Faq[]> {
    return this.api.getList<Faq>(API.Faq.GET_ACTIVE_FAQS, { ...filter });
  }

  // ---------------- Back office ----------------

  getAll(): Observable<Faq[]> {
    return this.api.getList<Faq>(API.Faq.GET_ALL_FAQS);
  }

  save(request: FaqRequest): Observable<CommandResult> {
    return this.api.command(API.Faq.SAVE_FAQ, request);
  }

  /** Soft delete: the FAQ is deactivated. */
  delete(faqId: number): Observable<CommandResult> {
    return this.api.command(API.Faq.DELETE_FAQ, null, { faqId });
  }
}
