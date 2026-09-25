import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { AdminUser, AdminUserRequest, DropdownOption } from '../models';

/** Back-office users (AdminUserController). Everything except the dropdown needs the ADMIN role. */
@Injectable({ providedIn: 'root' })
export class AdminUserService {
  private readonly api = inject(ApiClient);

  getAll(): Observable<AdminUser[]> {
    return this.api.getList<AdminUser>(API.AdminUser.GET_ALL_USERS);
  }

  /** Enabled users, for "assign enquiry to". Available to STAFF too. */
  getDropdown(): Observable<DropdownOption[]> {
    return this.api.getList<DropdownOption>(API.AdminUser.GET_USER_DROPDOWN);
  }

  save(request: AdminUserRequest): Observable<CommandResult> {
    return this.api.command(API.AdminUser.SAVE_USER, request);
  }

  delete(userId: number): Observable<CommandResult> {
    return this.api.command(API.AdminUser.DELETE_USER, null, { userId });
  }
}
