import { Injectable, inject } from '@angular/core';
import { Observable, map, shareReplay } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { OfficeLocation, OfficeLocationRequest } from '../models';

/** Office locations and their city landing pages (OfficeLocationController). */
@Injectable({ providedIn: 'root' })
export class LocationService {
  private readonly api = inject(ApiClient);

  // Offices change rarely, so one request serves the whole session.
  private readonly active$ = this.api
    .getList<OfficeLocation>(API.OfficeLocation.GET_ALL_ACTIVE_LOCATIONS)
    .pipe(shareReplay({ bufferSize: 1, refCount: false }));

  // ---------------- Public ----------------

  getActive(): Observable<OfficeLocation[]> {
    return this.active$;
  }

  getHeadOffice(): Observable<OfficeLocation | undefined> {
    return this.active$.pipe(map((all) => all.find((l) => l.headOffice) ?? all[0]));
  }

  getBySlug(slug: string): Observable<OfficeLocation | null> {
    return this.api.get<OfficeLocation>(API.OfficeLocation.GET_LOCATION_BY_SLUG(slug));
  }

  // ---------------- Back office ----------------

  getAll(): Observable<OfficeLocation[]> {
    return this.api.getList<OfficeLocation>(API.OfficeLocation.GET_ALL_LOCATIONS);
  }

  getById(locationId: number): Observable<OfficeLocation | null> {
    return this.api.get<OfficeLocation>(API.OfficeLocation.GET_LOCATION_BY_ID(locationId));
  }

  save(request: OfficeLocationRequest): Observable<CommandResult> {
    return this.api.command(API.OfficeLocation.SAVE_LOCATION, request);
  }

  delete(locationId: number): Observable<CommandResult> {
    return this.api.command(API.OfficeLocation.DELETE_LOCATION, null, { locationId });
  }
}
