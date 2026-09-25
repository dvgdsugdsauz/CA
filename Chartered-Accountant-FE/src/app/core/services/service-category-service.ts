import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { ServiceCategory, ServiceCategoryRequest } from '../models';

/** Service categories (ServiceCategoryController). */
@Injectable({ providedIn: 'root' })
export class ServiceCategoryService {
  private readonly api = inject(ApiClient);

  getAll(): Observable<ServiceCategory[]> {
    return this.api.getList<ServiceCategory>(API.ServiceCategory.GET_ALL_CATEGORIES);
  }

  save(request: ServiceCategoryRequest): Observable<CommandResult> {
    return this.api.command(API.ServiceCategory.SAVE_CATEGORY, request);
  }

  delete(categoryId: number): Observable<CommandResult> {
    return this.api.command(API.ServiceCategory.DELETE_CATEGORY, null, { categoryId });
  }
}
