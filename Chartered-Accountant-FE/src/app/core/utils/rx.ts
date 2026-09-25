import { Observable, OperatorFunction, catchError, of } from 'rxjs';

/**
 * Replaces an errored stream with a fallback value. Use for page sections that
 * should render empty rather than break the page when their request fails.
 */
export function withFallback<T>(fallback: NoInfer<T>): OperatorFunction<T, T> {
  return (source: Observable<T>) =>
    source.pipe(
      catchError((error: unknown) => {
        console.error(error);
        return of(fallback);
      }),
    );
}
