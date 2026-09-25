import { ChangeDetectionStrategy, Component, computed, input } from '@angular/core';
import { ENQUIRY_STATUSES, EnquiryStatus } from '../../../../core/models';

@Component({
  selector: 'app-status-pill',
  standalone: false,
  template: `<span class="pill" [attr.data-status]="status()">{{ label() }}</span>`,
  styleUrl: './status-pill.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class StatusPill {
  readonly status = input.required<EnquiryStatus>();
  protected readonly label = computed(
    () => ENQUIRY_STATUSES.find((s) => s.value === this.status())?.label ?? this.status(),
  );
}
