import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { environment } from '../../../environments/environment';
import { API_NO_CONTENT, API_OK, ApiResponse, CommandResult } from './api-response';

export type QueryParams = Record<string, string | number | boolean | null | undefined>;

/**
 * Talks to the backend and unwraps its `{ statusCode, message, data }` envelope.
 * Errors (4xx/5xx) arrive as HttpErrorResponse with the envelope in `error`;
 * see api-error.ts for reading them.
 */
@Injectable({ providedIn: 'root' })
export class ApiClient {
  private readonly http = inject(HttpClient);
  private readonly baseUrl = environment.apiUrl;

  /** GET, resolving to `data`, or null when the backend has nothing to show (204). */
  get<T>(path: string, params?: QueryParams): Observable<T | null> {
    return this.http
      .get<ApiResponse<T>>(this.url(path), { params: toHttpParams(params) })
      .pipe(map(unwrap));
  }

  /** GET a list. A 204 becomes an empty list. */
  getList<T>(path: string, params?: QueryParams): Observable<T[]> {
    return this.get<T[]>(path, params).pipe(map((data) => data ?? []));
  }

  /** POST, resolving to `data` (null when there is none). */
  post<T>(path: string, body: unknown = null, params?: QueryParams): Observable<T | null> {
    return this.http
      .post<ApiResponse<T>>(this.url(path), body, { params: toHttpParams(params) })
      .pipe(map(unwrap));
  }

  /** POST a save, update or delete, which answer with a message and no data. */
  command(path: string, body: unknown = null, params?: QueryParams): Observable<CommandResult> {
    return this.http
      .post<ApiResponse<null>>(this.url(path), body, { params: toHttpParams(params) })
      .pipe(map((res) => ({ ok: res.statusCode === API_OK, message: res.message })));
  }

  /** GET a file. Not wrapped in the envelope. */
  download(path: string): Observable<Blob> {
    return this.http.get(this.url(path), { responseType: 'blob' });
  }

  private url(path: string): string {
    return `${this.baseUrl}${path}`;
  }
}

function unwrap<T>(res: ApiResponse<T>): T | null {
  return res.statusCode === API_NO_CONTENT ? null : res.data;
}

function toHttpParams(params: QueryParams | undefined): HttpParams {
  let httpParams = new HttpParams();
  for (const [key, value] of Object.entries(params ?? {})) {
    if (value !== null && value !== undefined && value !== '') {
      httpParams = httpParams.set(key, value);
    }
  }
  return httpParams;
}
