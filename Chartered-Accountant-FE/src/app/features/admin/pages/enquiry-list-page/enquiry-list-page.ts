import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  computed,
  inject,
  input,
  signal,
} from '@angular/core';
import { takeUntilDestroyed, toObservable } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';
import { catchError, map, of, startWith, switchMap } from 'rxjs';
import { apiErrorMessage } from '../../../../core/http/api-error';
import { PageResponse } from '../../../../core/http/api-response';
import {
  ENQUIRY_STATUSES,
  Enquiry,
  EnquiryQuery,
  EnquiryStatus,
  EnquiryStatusSummary,
  isEnquiryStatus,
} from '../../../../core/models';
import { EnquiryService } from '../../../../core/services/enquiry-service';
import { StatusChange } from '../../components/enquiry-table/enquiry-table';

const PAGE_SIZE = 20;

type LoadResult =
  | { state: 'loading' }
  | { state: 'ready'; page: PageResponse<Enquiry> }
  | { state: 'error'; message: string };

/**
 * Enquiries board. Filter, search and page live in the URL
 * (`?status=NEW&search=kumar&page=2`), so any view can be bookmarked or shared.
 */
@Component({
  selector: 'app-enquiry-list-page',
  standalone: false,
  templateUrl: './enquiry-list-page.html',
  styleUrl: './enquiry-list-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EnquiryListPage {
  // Bound from query parameters.
  readonly status = input<string>();
  readonly search = input<string>();
  /** One-based in the URL; the API is zero-based. */
  readonly page = input<string>();

  private readonly enquiryService = inject(EnquiryService);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);

  protected readonly activeStatus = computed<EnquiryStatus | null>(() => {
    const status = this.status();
    return isEnquiryStatus(status) ? status : null;
  });
  protected readonly searchTerm = computed(() => this.search()?.trim() ?? '');
  private readonly pageIndex = computed(() => Math.max(0, (Number(this.page()) || 1) - 1));

  private readonly query = computed<EnquiryQuery>(() => ({
    status: this.activeStatus(),
    search: this.searchTerm() || null,
    page: this.pageIndex(),
    size: PAGE_SIZE,
  }));

  /** Bumped to re-run the current query, e.g. from "Try again". */
  private readonly reloads = signal(0);

  protected readonly result = signal<LoadResult>({ state: 'loading' });
  protected readonly summary = signal<EnquiryStatusSummary | null>(null);
  protected readonly savingId = signal<number | null>(null);
  protected readonly notice = signal<string | null>(null);

  protected readonly counts = computed(() => {
    const counts = Object.fromEntries(ENQUIRY_STATUSES.map((s) => [s.value, 0])) as Record<
      EnquiryStatus,
      number
    >;
    for (const row of this.summary()?.statuses ?? []) counts[row.status] = row.count;
    return counts;
  });

  constructor() {
    toObservable(computed(() => ({ query: this.query(), reload: this.reloads() })))
      .pipe(
        switchMap(({ query }) =>
          this.enquiryService.list(query).pipe(
            map((page): LoadResult => ({ state: 'ready', page })),
            catchError((error: unknown) =>
              of<LoadResult>({
                state: 'error',
                message: apiErrorMessage(error, 'Enquiries could not be loaded.'),
              }),
            ),
            startWith<LoadResult>({ state: 'loading' }),
          ),
        ),
        takeUntilDestroyed(),
      )
      .subscribe((result) => this.result.set(result));

    this.loadCounts();
  }

  protected retry(): void {
    this.reloads.update((n) => n + 1);
    this.loadCounts();
  }

  protected applySearch(term: string): void {
    this.router.navigate([], {
      queryParams: { search: term.trim() || null, page: null },
      queryParamsHandling: 'merge',
    });
  }

  /** Query params for the previous (-1) or next (+1) page. */
  protected pageParams(delta: number): Record<string, number | null> {
    const next = this.pageIndex() + delta;
    return { page: next > 0 ? next + 1 : null };
  }

  /**
   * Shows the new status straight away and rolls it back if the save fails.
   * Sends the current notes and assignee too, because the backend overwrites them.
   */
  protected changeStatus({ enquiry, status }: StatusChange): void {
    this.notice.set(null);
    this.savingId.set(enquiry.enquiryId);
    this.replaceRow({ ...enquiry, status });

    this.enquiryService
      .update({
        enquiryId: enquiry.enquiryId,
        status,
        internalNotes: enquiry.internalNotes,
        assignedToId: enquiry.assignedToId,
      })
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: ({ ok }) => {
          this.savingId.set(null);
          if (ok) {
            this.loadCounts();
          } else {
            this.replaceRow(enquiry);
            this.notice.set(`${enquiry.referenceNo} no longer exists. Refresh the list.`);
          }
        },
        error: (error: unknown) => {
          this.savingId.set(null);
          this.replaceRow(enquiry);
          this.notice.set(
            apiErrorMessage(error, `Could not update ${enquiry.referenceNo}. Please try again.`),
          );
        },
      });
  }

  private loadCounts(): void {
    this.enquiryService
      .getStatusCounts()
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({ next: (summary) => this.summary.set(summary), error: () => undefined });
  }

  private replaceRow(updated: Enquiry): void {
    this.result.update((result) =>
      result.state === 'ready'
        ? {
            ...result,
            page: {
              ...result.page,
              content: result.page.content.map((e) =>
                e.enquiryId === updated.enquiryId ? updated : e,
              ),
            },
          }
        : result,
    );
  }
}
