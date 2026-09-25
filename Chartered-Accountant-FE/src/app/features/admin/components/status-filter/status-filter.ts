import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { ENQUIRY_STATUSES, EnquiryStatus } from '../../../../core/models';

/** Status tabs. Each is a link that sets `?status=`, so filtered views can be shared. */
@Component({
  selector: 'app-status-filter',
  standalone: false,
  template: `
    <nav class="status-tabs" aria-label="Filter by status">
      <a
        [routerLink]="[]"
        [queryParams]="{ status: null, page: null }"
        queryParamsHandling="merge"
        [attr.aria-current]="active() === null ? 'true' : null"
      >
        All <span class="count">{{ total() }}</span>
      </a>
      @for (status of statuses; track status.value) {
        <a
          [routerLink]="[]"
          [queryParams]="{ status: status.value, page: null }"
          queryParamsHandling="merge"
          [attr.aria-current]="active() === status.value ? 'true' : null"
        >
          {{ status.label }} <span class="count">{{ counts()[status.value] }}</span>
        </a>
      }
    </nav>
  `,
  styleUrl: './status-filter.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StatusFilter {
  readonly counts = input.required<Record<EnquiryStatus, number>>();
  readonly total = input.required<number>();
  readonly active = input<EnquiryStatus | null>(null);

  protected readonly statuses = ENQUIRY_STATUSES;
}
