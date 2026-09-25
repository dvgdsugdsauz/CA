import { ChangeDetectionStrategy, Component, DestroyRef, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { FormControl } from '@angular/forms';
import { EMAIL_PATTERN } from '../../core/constants/validation';
import { apiErrorMessage, apiFieldErrors } from '../../core/http/api-error';
import { NewsletterService } from '../../core/services/newsletter-service';
import { patternTrimmed, requiredTrimmed } from '../../shared/forms/validators';

type SignupState = 'idle' | 'sending' | 'done' | 'error';

@Component({
  selector: 'app-newsletter-signup',
  standalone: false,
  templateUrl: './newsletter-signup.html',
  styleUrl: './newsletter-signup.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NewsletterSignup {
  private readonly newsletter = inject(NewsletterService);
  private readonly destroyRef = inject(DestroyRef);

  protected readonly email = new FormControl('', {
    nonNullable: true,
    validators: [requiredTrimmed, patternTrimmed(EMAIL_PATTERN)],
  });
  protected readonly state = signal<SignupState>('idle');
  /** The backend's wording, e.g. "Subscribed to tax updates" or "This email is already subscribed". */
  protected readonly message = signal('');

  protected submit(): void {
    if (this.email.invalid) {
      this.show('error', 'Enter a valid email address to subscribe');
      return;
    }
    this.state.set('sending');
    this.newsletter
      .subscribe(this.email.value.trim())
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: ({ message }) => {
          this.show('done', message);
          this.email.reset();
        },
        error: (error: unknown) =>
          this.show(
            'error',
            apiFieldErrors(error)?.['email'] ??
              apiErrorMessage(error, 'We could not subscribe you just now. Please try again.'),
          ),
      });
  }

  private show(state: SignupState, message: string): void {
    this.state.set(state);
    this.message.set(message);
  }
}
