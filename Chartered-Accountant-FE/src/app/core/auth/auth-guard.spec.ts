import { TestBed } from '@angular/core/testing';
import { ActivatedRouteSnapshot, Router, RouterStateSnapshot, UrlTree, provideRouter } from '@angular/router';
import { authGuard } from './auth-guard';
import { AuthService } from './auth-service';

describe('authGuard', () => {
  function run(signedIn: boolean): boolean | UrlTree {
    TestBed.configureTestingModule({
      providers: [
        provideRouter([]),
        { provide: AuthService, useValue: { isAuthenticated: () => signedIn } },
      ],
    });
    return TestBed.runInInjectionContext(
      () =>
        authGuard({} as ActivatedRouteSnapshot, {
          url: '/admin/enquiries?status=NEW',
        } as RouterStateSnapshot) as boolean | UrlTree,
    );
  }

  it('lets signed-in staff through', () => {
    expect(run(true)).toBe(true);
  });

  it('sends everyone else to the login page with a return URL', () => {
    const result = run(false);
    const router = TestBed.inject(Router);

    expect(result).toBeInstanceOf(UrlTree);
    expect(router.serializeUrl(result as UrlTree)).toBe(
      '/admin/login?returnUrl=%2Fadmin%2Fenquiries%3Fstatus%3DNEW',
    );
  });
});
