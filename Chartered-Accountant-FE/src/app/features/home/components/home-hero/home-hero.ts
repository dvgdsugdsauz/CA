import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { ComplianceDeadline, OfficeLocation } from '../../../../core/models';

@Component({
  selector: 'app-home-hero',
  standalone: false,
  templateUrl: './home-hero.html',
  styleUrl: './home-hero.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeHero {
  readonly office = input.required<OfficeLocation>();
  readonly deadlines = input<ComplianceDeadline[]>([]);
}
