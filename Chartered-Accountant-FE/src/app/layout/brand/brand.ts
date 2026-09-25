import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { FIRM } from '../../core/constants/firm';

/** Firm monogram and name, linking home. `inverse` is for dark backgrounds. */
@Component({
  selector: 'app-brand',
  standalone: false,
  template: `
    <a class="brand" routerLink="/" [class.inverse]="inverse()">
      <span class="brand-mark" aria-hidden="true">{{ firm.monogram }}</span>
      <span>
        <span class="brand-name">{{ firm.name }}</span>
        <span class="brand-sub">{{ firm.descriptor }}</span>
      </span>
    </a>
  `,
  styleUrl: './brand.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Brand {
  readonly inverse = input(false);
  protected readonly firm = FIRM;
}
