import { Injectable, computed, inject, signal } from '@angular/core';
import { Observable, map, tap } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { AdminUser, AuthSession, ChangePasswordRequest, LoginResponse } from '../models';

const STORAGE_KEY = 'ca.admin.session';

/** Back-office sign-in with the backend's JWT. The session lasts until the tab closes or the token expires. */
@Injectable({ providedIn: 'root' })
export class AuthService {
  private readonly api = inject(ApiClient);
  private readonly session = signal<AuthSession | null>(readStoredSession());

  readonly user = computed<AdminUser | null>(() => this.session()?.user ?? null);

  /** A method, not a signal: expiry depends on the clock, so it is checked on every call. */
  isAuthenticated(): boolean {
    const session = this.session();
    return session !== null && session.expiresAt > Date.now();
  }

  token(): string | null {
    return this.isAuthenticated() ? this.session()!.token : null;
  }

  login(username: string, password: string): Observable<AdminUser> {
    return this.api.post<LoginResponse>(API.Auth.LOGIN, { username, password }).pipe(
      map((login) => {
        if (!login) throw new Error('Login succeeded without a token');
        return login;
      }),
      tap((login) =>
        this.setSession({
          token: login.accessToken,
          expiresAt: Date.now() + login.expiresIn * 1000,
          user: login.user,
        }),
      ),
      map((login) => login.user),
    );
  }

  /** Refreshes the signed-in user's details from the backend. */
  loadProfile(): Observable<AdminUser | null> {
    return this.api.get<AdminUser>(API.Auth.GET_PROFILE).pipe(
      tap((user) => {
        const session = this.session();
        if (user && session) this.setSession({ ...session, user });
      }),
    );
  }

  changePassword(request: ChangePasswordRequest): Observable<CommandResult> {
    return this.api.command(API.Auth.CHANGE_PASSWORD, request);
  }

  logout(): void {
    this.setSession(null);
  }

  private setSession(session: AuthSession | null): void {
    this.session.set(session);
    try {
      if (session) sessionStorage.setItem(STORAGE_KEY, JSON.stringify(session));
      else sessionStorage.removeItem(STORAGE_KEY);
    } catch {
      // Storage can be unavailable (private mode, blocked site data). The in-memory session still works.
    }
  }
}

function readStoredSession(): AuthSession | null {
  try {
    const raw = sessionStorage.getItem(STORAGE_KEY);
    const session = raw ? (JSON.parse(raw) as AuthSession) : null;
    return session && typeof session.expiresAt === 'number' && session.token ? session : null;
  } catch {
    return null;
  }
}
