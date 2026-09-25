import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { FirmServiceSummary } from '../../../core/models';

@Component({
  selector: 'app-service-card',
  standalone: false,
  template: `
    <a class="service" [routerLink]="['/services', service().slug]">
      <app-icon [name]="service().icon" />
      <h3>{{ service().title }}</h3>
      <p>{{ service().summary }}</p>
      <span class="link-more">Explore</span>
    </a>
  `,
  styleUrl: './service-card.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ServiceCard {
  readonly service = input.required<FirmServiceSummary>();
}
