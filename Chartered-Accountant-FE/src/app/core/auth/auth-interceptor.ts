import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ADMIN_PREFIX } from '../http/api-endpoints';
import { LOGIN_PATH } from './auth-guard';
import { AuthService } from './auth-service';

const ADMIN_API = `${environment.apiUrl}${ADMIN_PREFIX}/`;

/** Adds the bearer token to back-office API calls and signs out when the backend rejects it (401). */
export const authInterceptor: HttpInterceptorFn = (req, next) => {
  if (!req.url.startsWith(ADMIN_API)) return next(req);

  const auth = inject(AuthService);
  const router = inject(Router);
  const token = auth.token();
  const authorized = token ? req.clone({ setHeaders: { Authorization: `Bearer ${token}` } }) : req;

  return next(authorized).pipe(
    catchError((error: unknown) => {
      if (error instanceof HttpErrorResponse && error.status === 401) {
        auth.logout();
        router.navigate([LOGIN_PATH], { queryParams: { returnUrl: router.url } });
      }
      return throwError(() => error);
    }),
  );
};
