import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { OfficeLocation } from '../../../core/models';

/** Address, phone, email and hours for one office. */
@Component({
  selector: 'app-office-details',
  standalone: false,
  template: `
    @if (showCity()) {
      <h2>{{ office().city }}</h2>
    }
    <dl>
      <div>
        <dt>{{ showCity() ? 'Address' : 'Office' }}</dt>
        <dd>{{ office().addressLine }}</dd>
      </div>
      @if (office().phone; as phone) {
        <div>
          <dt>Phone</dt>
          <dd class="mono"><a [href]="'tel:' + phone">{{ phone }}</a></dd>
        </div>
      }
      @if (office().email; as email) {
        <div>
          <dt>Email</dt>
          <dd><a [href]="'mailto:' + email">{{ email }}</a></dd>
        </div>
      }
      @if (office().officeHours) {
        <div>
          <dt>Hours</dt>
          <dd>{{ office().officeHours }}</dd>
        </div>
      }
    </dl>
  `,
  styleUrl: './office-details.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class OfficeDetails {
  readonly office = input.required<OfficeLocation>();
  /** Show the city as a heading, for pages that list several offices. */
  readonly showCity = input(false);
}
