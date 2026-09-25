/** `LocalDateTime` from the backend: ISO 8601 without an offset, in Asia/Kolkata. */
export type IsoDateTime = string;

/** `LocalDate` from the backend, e.g. 2026-10-20. */
export type IsoDate = string;

/** `DropdownResponse.java`: id/name pairs for select boxes. */
export interface DropdownOption {
  id: number;
  name: string;
}

/** Paging for list endpoints. `page` is zero-based; the backend defaults to 20 per page, max 100. */
export interface PageQuery {
  page?: number;
  size?: number;
}
