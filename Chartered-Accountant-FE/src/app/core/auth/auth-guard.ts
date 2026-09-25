import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';
import { AuthService } from './auth-service';

export const LOGIN_PATH = '/admin/login';

/** Lets signed-in staff through and sends everyone else to the login page. */
export const authGuard: CanActivateFn = (_route, state) => {
  if (inject(AuthService).isAuthenticated()) return true;
  return inject(Router).createUrlTree([LOGIN_PATH], { queryParams: { returnUrl: state.url } });
};

/** Keeps signed-in staff off the login page. */
export const guestGuard: CanActivateFn = () => {
  if (!inject(AuthService).isAuthenticated()) return true;
  return inject(Router).createUrlTree(['/admin']);
};
