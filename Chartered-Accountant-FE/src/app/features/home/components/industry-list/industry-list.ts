import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { Industry } from '../../../../core/models';

@Component({
  selector: 'app-industry-list',
  standalone: false,
  template: `
    <div class="wrap">
      <div class="section-head">
        <span class="eyebrow">Industries</span>
        <h2>Sectors we know from the inside</h2>
        <p>
          Each sector has its own reporting quirks, from RERA escrow in real estate to marketplace
          TCS in e-commerce. We staff engagements with people who have done that work before.
        </p>
      </div>
      <ul class="industry-list">
        @for (industry of industries(); track industry.industryId) {
          <li>
            <app-icon [name]="industry.icon" />
            <div>
              <strong>{{ industry.name }}</strong>
              <span>{{ industry.summary }}</span>
            </div>
          </li>
        }
      </ul>
    </div>
  `,
  styleUrl: './industry-list.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class IndustryList {
  readonly industries = input.required<Industry[]>();
}
