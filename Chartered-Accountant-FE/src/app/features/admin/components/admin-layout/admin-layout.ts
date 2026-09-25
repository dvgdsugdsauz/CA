import { ChangeDetectionStrategy, Component, inject } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';
import { AuthService } from '../../../../core/auth/auth-service';
import { LOGIN_PATH } from '../../../../core/auth/auth-guard';
import { FIRM } from '../../../../core/constants/firm';

/** Back-office shell for signed-in staff. */
@Component({
  selector: 'app-admin-layout',
  standalone: false,
  template: `
    <header class="admin-bar">
      <div class="wrap">
        <strong>{{ firm.name }} · Back office</strong>
        <a routerLink="/">View site</a>
        @if (auth.user(); as user) {
          <span>{{ user.fullName }}</span>
        }
        <button type="button" (click)="signOut()">Sign out</button>
      </div>
    </header>
    <main class="wrap admin-main">
      <router-outlet />
    </main>
  `,
  styleUrl: './admin-layout.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class AdminLayout {
  protected readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  protected readonly firm = FIRM;

  constructor() {
    // Refresh the user's name and role. A revoked token answers 401, which signs out.
    this.auth.loadProfile().pipe(takeUntilDestroyed()).subscribe({ error: () => undefined });
  }

  protected signOut(): void {
    this.auth.logout();
    this.router.navigateByUrl(LOGIN_PATH);
  }
}
