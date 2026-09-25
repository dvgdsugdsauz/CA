import { ChangeDetectionStrategy, Component, computed, inject, input } from '@angular/core';
import { toObservable, toSignal } from '@angular/core/rxjs-interop';
import { switchMap } from 'rxjs';
import { FirmServiceDetail } from '../../../../core/models';
import { FaqService } from '../../../../core/services/faq-service';
import { withFallback } from '../../../../core/utils/rx';
import { Breadcrumb } from '../../../../shared/components/page-header/page-header';

@Component({
  selector: 'app-service-detail-page',
  standalone: false,
  templateUrl: './service-detail-page.html',
  styleUrl: './service-detail-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ServiceDetailPage {
  /** Bound from the route's `service` resolver. */
  readonly service = input.required<FirmServiceDetail>();

  private readonly faqService = inject(FaqService);

  protected readonly faqs = toSignal(
    toObservable(this.service).pipe(
      switchMap((s) => this.faqService.getActive({ serviceSlug: s.slug }).pipe(withFallback([]))),
    ),
    { initialValue: [] },
  );

  protected readonly breadcrumbs = computed<Breadcrumb[]>(() => [
    { label: 'Home', link: '/' },
    { label: 'Services', link: '/services' },
    { label: this.service().title },
  ]);
}
