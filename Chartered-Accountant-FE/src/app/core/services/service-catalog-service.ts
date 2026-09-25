import { Injectable, inject } from '@angular/core';
import { Observable, shareReplay } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { DropdownOption, FirmServiceDetail, FirmServiceRequest, FirmServiceSummary } from '../models';

/** The firm's services and their pages (FirmServiceController). */
@Injectable({ providedIn: 'root' })
export class ServiceCatalogService {
  private readonly api = inject(ApiClient);

  // Used by the footer, forms and pages, so fetch each once per session.
  private readonly active$ = this.api
    .getList<FirmServiceSummary>(API.FirmService.GET_ALL_ACTIVE_SERVICES)
    .pipe(shareReplay({ bufferSize: 1, refCount: false }));

  private readonly dropdown$ = this.api
    .getList<DropdownOption>(API.FirmService.GET_SERVICE_DROPDOWN)
    .pipe(shareReplay({ bufferSize: 1, refCount: false }));

  // ---------------- Public ----------------

  getActive(): Observable<FirmServiceSummary[]> {
    return this.active$;
  }

  /** Null when no active service has this slug. */
  getBySlug(slug: string): Observable<FirmServiceDetail | null> {
    return this.api.get<FirmServiceDetail>(API.FirmService.GET_SERVICE_BY_SLUG(slug));
  }

  /** Options for the enquiry form's service select. */
  getDropdown(): Observable<DropdownOption[]> {
    return this.dropdown$;
  }

  // ---------------- Back office ----------------

  getAll(): Observable<FirmServiceSummary[]> {
    return this.api.getList<FirmServiceSummary>(API.FirmService.GET_ALL_SERVICES);
  }

  getById(serviceId: number): Observable<FirmServiceDetail | null> {
    return this.api.get<FirmServiceDetail>(API.FirmService.GET_SERVICE_BY_ID(serviceId));
  }

  save(request: FirmServiceRequest): Observable<CommandResult> {
    return this.api.command(API.FirmService.SAVE_SERVICE, request);
  }

  delete(serviceId: number): Observable<CommandResult> {
    return this.api.command(API.FirmService.DELETE_SERVICE, null, { serviceId });
  }
}
