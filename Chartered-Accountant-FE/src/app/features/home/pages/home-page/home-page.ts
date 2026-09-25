import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { of, shareReplay, switchMap } from 'rxjs';
import { ArticleService } from '../../../../core/services/article-service';
import { ComplianceDeadlineService } from '../../../../core/services/compliance-deadline-service';
import { FaqService } from '../../../../core/services/faq-service';
import { IndustryService } from '../../../../core/services/industry-service';
import { LocationService } from '../../../../core/services/location-service';
import { ServiceCatalogService } from '../../../../core/services/service-catalog-service';
import { TestimonialService } from '../../../../core/services/testimonial-service';
import { withFallback } from '../../../../core/utils/rx';

/**
 * Landing page for the head office's city. Loads the data and hands it to
 * presentational section components.
 */
@Component({
  selector: 'app-home-page',
  standalone: false,
  templateUrl: './home-page.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomePage {
  private readonly faqService = inject(FaqService);

  private readonly office$ = inject(LocationService)
    .getHeadOffice()
    .pipe(withFallback(undefined), shareReplay({ bufferSize: 1, refCount: true }));

  protected readonly office = toSignal(this.office$);
  protected readonly services = toSignal(
    inject(ServiceCatalogService).getActive().pipe(withFallback([])),
    { initialValue: [] },
  );
  protected readonly deadlines = toSignal(
    inject(ComplianceDeadlineService).getUpcoming(5).pipe(withFallback([])),
    { initialValue: [] },
  );
  protected readonly industries = toSignal(inject(IndustryService).getAll().pipe(withFallback([])), {
    initialValue: [],
  });
  protected readonly testimonials = toSignal(
    inject(TestimonialService).getActive().pipe(withFallback([])),
    { initialValue: [] },
  );
  protected readonly articles = toSignal(inject(ArticleService).getLatest(3).pipe(withFallback([])), {
    initialValue: [],
  });
  protected readonly faqs = toSignal(
    this.office$.pipe(
      switchMap((office) =>
        office ? this.faqService.getActive({ locationSlug: office.slug }) : of([]),
      ),
      withFallback([]),
    ),
    { initialValue: [] },
  );
}
