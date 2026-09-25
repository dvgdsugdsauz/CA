import { inject } from '@angular/core';
import { RedirectCommand, ResolveFn, Router } from '@angular/router';
import { catchError, map, of } from 'rxjs';
import { FirmServiceDetail } from '../../core/models';
import { ServiceCatalogService } from '../../core/services/service-catalog-service';

/**
 * Loads the service for `:slug`. The backend answers 204 for an unknown or inactive
 * slug; that shows the not-found page at the same URL.
 */
export const serviceDetailResolver: ResolveFn<FirmServiceDetail | RedirectCommand> = (route) => {
  const router = inject(Router);
  return inject(ServiceCatalogService)
    .getBySlug(route.paramMap.get('slug') ?? '')
    .pipe(
      map(
        (service) =>
          service ??
          new RedirectCommand(router.parseUrl('/not-found'), { skipLocationChange: true }),
      ),
    );
};

/** Page title from the cached service list, so the detail request isn't made twice. */
export const serviceTitleResolver: ResolveFn<string> = (route) => {
  const slug = route.paramMap.get('slug');
  return inject(ServiceCatalogService)
    .getActive()
    .pipe(
      map((all) => all.find((s) => s.slug === slug)?.title ?? 'Services'),
      catchError(() => of('Services')),
    );
};
