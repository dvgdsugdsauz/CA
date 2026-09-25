/** Envelope around every backend response (`Response.java`). */
export interface ApiResponse<T> {
  /** 200 = data, 204 = nothing to show (sent with HTTP 200), 4xx/5xx = error. */
  statusCode: number;
  message: string;
  data: T | null;
}

/** A page of results (`PageResponse.java`). `page` is zero-based. */
export interface PageResponse<T> {
  content: T[];
  page: number;
  size: number;
  totalElements: number;
  totalPages: number;
}

/** Outcome of a save, update or delete, which return no data. */
export interface CommandResult {
  /** False when the backend answered 204, e.g. the record to update no longer exists. */
  ok: boolean;
  message: string;
}

export const API_OK = 200;
export const API_NO_CONTENT = 204;

export function emptyPage<T>(size = 0): PageResponse<T> {
  return { content: [], page: 0, size, totalElements: 0, totalPages: 0 };
}
