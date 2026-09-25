import { ChangeDetectionStrategy, Component, DestroyRef, inject, input, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../../core/auth/auth-service';
import { FIRM } from '../../../../core/constants/firm';
import { apiErrorMessage } from '../../../../core/http/api-error';
import { environment } from '../../../../../environments/environment';

@Component({
  selector: 'app-login-page',
  standalone: false,
  templateUrl: './login-page.html',
  styleUrl: './login-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LoginPage {
  /** Bound from `?returnUrl=`, set by the auth guard. */
  readonly returnUrl = input<string>();

  private readonly auth = inject(AuthService);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);

  protected readonly firm = FIRM;
  protected readonly showDemoHint = environment.useMockApi;
  protected readonly submitted = signal(false);
  protected readonly signingIn = signal(false);
  protected readonly failure = signal<string | null>(null);

  protected readonly form = inject(FormBuilder).nonNullable.group({
    username: ['', Validators.required],
    password: ['', Validators.required],
  });

  protected showError(field: 'username' | 'password'): boolean {
    return this.submitted() && this.form.controls[field].invalid;
  }

  protected submit(): void {
    this.submitted.set(true);
    this.failure.set(null);
    if (this.form.invalid || this.signingIn()) return;

    const { username, password } = this.form.getRawValue();
    this.signingIn.set(true);
    this.auth
      .login(username.trim(), password)
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: () => this.router.navigateByUrl(this.safeReturnUrl()),
        // The backend explains 401s itself: wrong password, or a disabled account.
        error: (error: unknown) => {
          this.signingIn.set(false);
          this.failure.set(apiErrorMessage(error, 'Could not sign in just now. Please try again.'));
        },
      });
  }

  /** Only follow return URLs inside the back office. */
  private safeReturnUrl(): string {
    const url = this.returnUrl();
    return url?.startsWith('/admin') && !url.startsWith('/admin/login') ? url : '/admin';
  }
}
