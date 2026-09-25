import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { FIRM } from '../../core/constants/firm';
import { FirmServiceSummary, OfficeLocation } from '../../core/models';

@Component({
  selector: 'app-site-footer',
  standalone: false,
  templateUrl: './site-footer.html',
  styleUrl: './site-footer.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SiteFooter {
  readonly services = input<FirmServiceSummary[]>([]);
  readonly offices = input<OfficeLocation[]>([]);

  protected readonly firm = FIRM;
  protected readonly year = new Date().getFullYear();
  protected readonly firmLinks = [
    { label: 'About us', path: '/about' },
    { label: 'Newsletters', path: '/newsletters' },
    { label: 'Careers', path: '/careers' },
    { label: 'Contact', path: '/contact' },
  ];
}
