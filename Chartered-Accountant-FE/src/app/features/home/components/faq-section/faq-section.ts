import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { Faq, OfficeLocation } from '../../../../core/models';

@Component({
  selector: 'app-faq-section',
  standalone: false,
  template: `
    <div class="wrap faq-layout">
      <div class="section-head">
        <span class="eyebrow">FAQ</span>
        <h2>Questions from {{ office() ? office()!.city + ' businesses' : 'our clients' }}</h2>
        @if (office(); as office) {
          <p>
            Can't find your answer? Call
            <a class="mono" [href]="'tel:' + office.phone">{{ office.phone }}</a>
            or send an enquiry below.
          </p>
        }
      </div>
      <app-faq-accordion [faqs]="faqs()" />
    </div>
  `,
  styleUrl: './faq-section.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FaqSection {
  readonly faqs = input.required<Faq[]>();
  readonly office = input<OfficeLocation | undefined>();
}
