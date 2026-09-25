import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { ComplianceDeadline } from '../../../../core/models';

/** Upcoming statutory due dates, drawn as a ledger page. The backend decides which are due soon. */
@Component({
  selector: 'app-compliance-calendar',
  standalone: false,
  templateUrl: './compliance-calendar.html',
  styleUrl: './compliance-calendar.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ComplianceCalendar {
  readonly deadlines = input.required<ComplianceDeadline[]>();
  protected readonly today = new Date();
}
