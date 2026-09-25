import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { Industry, IndustryRequest } from '../models';

/** Industries served (IndustryController). */
@Injectable({ providedIn: 'root' })
export class IndustryService {
  private readonly api = inject(ApiClient);

  getAll(): Observable<Industry[]> {
    return this.api.getList<Industry>(API.Industry.GET_ALL_INDUSTRIES);
  }

  save(request: IndustryRequest): Observable<CommandResult> {
    return this.api.command(API.Industry.SAVE_INDUSTRY, request);
  }

  delete(industryId: number): Observable<CommandResult> {
    return this.api.command(API.Industry.DELETE_INDUSTRY, null, { industryId });
  }
}
