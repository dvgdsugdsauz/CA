import { ChangeDetectionStrategy, Component, ElementRef, inject, viewChild } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { LocationService } from '../../core/services/location-service';
import { ServiceCatalogService } from '../../core/services/service-catalog-service';
import { withFallback } from '../../core/utils/rx';

/** Chrome for every public page: topbar, sticky header, content and footer. */
@Component({
  selector: 'app-public-layout',
  standalone: false,
  templateUrl: './public-layout.html',
  styleUrl: './public-layout.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PublicLayout {
  protected readonly offices = toSignal(inject(LocationService).getActive().pipe(withFallback([])), {
    initialValue: [],
  });
  protected readonly services = toSignal(
    inject(ServiceCatalogService).getActive().pipe(withFallback([])),
    { initialValue: [] },
  );

  private readonly main = viewChild.required<ElementRef<HTMLElement>>('main');

  // A plain href="#main" would make the router leave the current page, so move focus by hand.
  protected skipToContent(event: Event): void {
    event.preventDefault();
    this.main().nativeElement.focus();
  }
}
