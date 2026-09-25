import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { FirmServiceSummary } from '../../../../core/models';

@Component({
  selector: 'app-service-overview',
  standalone: false,
  template: `
    <div class="wrap">
      <div class="section-head split">
        <div>
          <span class="eyebrow">Services</span>
          <h2>What we do for {{ city() ? city() + ' clients' : 'our clients' }}</h2>
        </div>
        <a class="link-more" routerLink="/services">All services</a>
      </div>
      <div class="service-grid">
        @for (service of services(); track service.serviceId) {
          <app-service-card [service]="service" />
        }
      </div>
    </div>
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ServiceOverview {
  readonly services = input.required<FirmServiceSummary[]>();
  readonly city = input<string | undefined>();
}
