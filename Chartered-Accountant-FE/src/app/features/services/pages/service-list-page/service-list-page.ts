import { ChangeDetectionStrategy, Component, computed, inject } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { FirmServiceSummary } from '../../../../core/models';
import { ServiceCatalogService } from '../../../../core/services/service-catalog-service';
import { withFallback } from '../../../../core/utils/rx';
import { Breadcrumb } from '../../../../shared/components/page-header/page-header';

interface CategoryGroup {
  key: string;
  name: string;
  services: FirmServiceSummary[];
}

@Component({
  selector: 'app-service-list-page',
  standalone: false,
  templateUrl: './service-list-page.html',
  styleUrl: './service-list-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ServiceListPage {
  protected readonly breadcrumbs: Breadcrumb[] = [{ label: 'Home', link: '/' }, { label: 'Services' }];

  private readonly services = toSignal(
    inject(ServiceCatalogService).getActive().pipe(withFallback([])),
    { initialValue: [] },
  );

  /** Services grouped by category, in the API's order. Uncategorised services come last. */
  protected readonly groups = computed<CategoryGroup[]>(() => {
    const groups = new Map<string, CategoryGroup>();
    for (const service of this.services()) {
      const key = service.categorySlug ?? 'other';
      const group = groups.get(key) ?? {
        key,
        name: service.categoryName ?? 'Other services',
        services: [],
      };
      group.services.push(service);
      groups.set(key, group);
    }
    return [...groups.values()].sort((a, b) => Number(a.key === 'other') - Number(b.key === 'other'));
  });
}
