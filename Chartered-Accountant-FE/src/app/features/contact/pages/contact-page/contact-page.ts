import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { LocationService } from '../../../../core/services/location-service';
import { withFallback } from '../../../../core/utils/rx';
import { Breadcrumb } from '../../../../shared/components/page-header/page-header';

@Component({
  selector: 'app-contact-page',
  standalone: false,
  templateUrl: './contact-page.html',
  styleUrl: './contact-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ContactPage {
  protected readonly breadcrumbs: Breadcrumb[] = [{ label: 'Home', link: '/' }, { label: 'Contact' }];
  protected readonly offices = toSignal(inject(LocationService).getActive().pipe(withFallback([])), {
    initialValue: [],
  });
}
