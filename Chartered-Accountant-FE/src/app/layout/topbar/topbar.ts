import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { FIRM } from '../../core/constants/firm';
import { OfficeLocation } from '../../core/models';

@Component({
  selector: 'app-topbar',
  standalone: false,
  template: `
    <div class="wrap">
      <div class="topbar-contact">
        <a [href]="'tel:' + firm.phone">{{ firm.phone }}</a>
        <a [href]="'mailto:' + firm.email">{{ firm.email }}</a>
      </div>
      <div class="topbar-meta">
        @if (offices().length) {
          <span>
            Offices:
            @for (office of offices(); track office.locationId; let last = $last) {
              <a routerLink="/contact">{{ office.city }}</a>{{ last ? '' : ' · ' }}
            }
          </span>
        }
        <span class="mono registration">{{ firm.registration }}</span>
      </div>
    </div>
  `,
  styleUrl: './topbar.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Topbar {
  readonly offices = input<OfficeLocation[]>([]);
  protected readonly firm = FIRM;
}
