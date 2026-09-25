import { HttpErrorResponse } from '@angular/common/http';
import { ApiResponse } from './api-response';

function envelope(error: unknown): Partial<ApiResponse<unknown>> | null {
  if (!(error instanceof HttpErrorResponse)) return null;
  return typeof error.error === 'object' && error.error !== null ? error.error : null;
}

/** The backend's error message, or `fallback` when there is none (network failure, proxy error). */
export function apiErrorMessage(error: unknown, fallback: string): string {
  const message = envelope(error)?.message;
  return typeof message === 'string' && message.trim() ? message : fallback;
}

/**
 * Field messages from a failed @Validated request (HTTP 400, `data` = { field: message }).
 * Null for any other error.
 */
export function apiFieldErrors(error: unknown): Record<string, string> | null {
  if (!(error instanceof HttpErrorResponse) || error.status !== 400) return null;
  const data = envelope(error)?.data;
  return typeof data === 'object' && data !== null && !Array.isArray(data)
    ? (data as Record<string, string>)
    : null;
}

export function isHttpStatus(error: unknown, status: number): boolean {
  return error instanceof HttpErrorResponse && error.status === status;
}
