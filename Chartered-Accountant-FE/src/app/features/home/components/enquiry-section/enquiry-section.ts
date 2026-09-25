import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { OfficeLocation } from '../../../../core/models';

@Component({
  selector: 'app-enquiry-section',
  standalone: false,
  template: `
    <div class="wrap enquire-layout">
      <div class="form-card">
        <div class="section-head">
          <span class="eyebrow">Enquire</span>
          <h2>Tell us what you need</h2>
          <p>A chartered accountant reads every enquiry and calls you back within one working day.</p>
        </div>
        <app-enquiry-form
          sourcePage="/"
          [locationId]="office().locationId"
          note="We call back within one working day."
        />
      </div>
      <div>
        <h2 class="visually-hidden">Office details</h2>
        <app-office-details [office]="office()" />
      </div>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EnquirySection {
  readonly office = input.required<OfficeLocation>();
}
