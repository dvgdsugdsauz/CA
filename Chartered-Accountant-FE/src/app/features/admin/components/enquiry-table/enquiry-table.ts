import { ChangeDetectionStrategy, Component, input, output } from '@angular/core';
import { ENQUIRY_STATUSES, Enquiry, EnquiryStatus } from '../../../../core/models';

export interface StatusChange {
  enquiry: Enquiry;
  status: EnquiryStatus;
}

@Component({
  selector: 'app-enquiry-table',
  standalone: false,
  templateUrl: './enquiry-table.html',
  styleUrl: './enquiry-table.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EnquiryTable {
  readonly enquiries = input.required<Enquiry[]>();
  /** Id of the enquiry whose status is being saved; its select is disabled meanwhile. */
  readonly savingId = input<number | null>(null);
  readonly statusChange = output<StatusChange>();

  protected readonly statuses = ENQUIRY_STATUSES;

  protected onStatusSelected(enquiry: Enquiry, event: Event): void {
    const status = (event.target as HTMLSelectElement).value as EnquiryStatus;
    if (status !== enquiry.status) this.statusChange.emit({ enquiry, status });
  }
}
