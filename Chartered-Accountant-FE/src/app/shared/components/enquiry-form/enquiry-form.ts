import {
  ChangeDetectionStrategy,
  Component,
  DestroyRef,
  effect,
  inject,
  input,
  signal,
  untracked,
} from '@angular/core';
import { takeUntilDestroyed, toSignal } from '@angular/core/rxjs-interop';
import { FormBuilder } from '@angular/forms';
import { Router } from '@angular/router';
import { EMAIL_PATTERN, PHONE_PATTERN } from '../../../core/constants/validation';
import { apiErrorMessage, apiFieldErrors } from '../../../core/http/api-error';
import { EnquiryRequest } from '../../../core/models';
import { EnquiryService } from '../../../core/services/enquiry-service';
import { ServiceCatalogService } from '../../../core/services/service-catalog-service';
import { withFallback } from '../../../core/utils/rx';
import { patternTrimmed, requiredTrimmed } from '../../forms/validators';

type FieldName = 'fullName' | 'email' | 'phone';

// Wording matches EnquiryRequest.java so client and server errors read the same.
const MESSAGES: Record<FieldName, Record<string, string>> = {
  fullName: { required: 'Enter your name' },
  email: {
    required: 'Enter your email address',
    pattern: 'Enter a valid email address, like name@company.com',
  },
  phone: {
    required: 'Enter a phone number so we can call you back',
    pattern: 'Use digits only, e.g. +91 98765 43210',
  },
};

let nextId = 0;

/**
 * The enquiry form used on the home, service and contact pages. On success it
 * opens the thank-you page with the enquiry's reference number.
 */
@Component({
  selector: 'app-enquiry-form',
  standalone: false,
  templateUrl: './enquiry-form.html',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class EnquiryForm {
  private readonly enquiries = inject(EnquiryService);
  private readonly router = inject(Router);
  private readonly destroyRef = inject(DestroyRef);

  /** Path of the page hosting the form, stored as `source_page`. */
  readonly sourcePage = input.required<string>();
  /** Office of the city page hosting the form. */
  readonly locationId = input<number | null>(null);
  /** Service to preselect, on service pages. */
  readonly defaultServiceId = input<number | null>(null);
  /** `compact` drops the company field and stacks the fields, for sidebars. */
  readonly variant = input<'full' | 'compact'>('full');
  readonly note = input<string | null>(null);

  protected readonly id = `enquiry-${++nextId}`;
  protected readonly services = toSignal(
    inject(ServiceCatalogService).getDropdown().pipe(withFallback([])),
    { initialValue: [] },
  );
  protected readonly submitted = signal(false);
  protected readonly sending = signal(false);
  protected readonly failure = signal<string | null>(null);

  protected readonly form = inject(FormBuilder).nonNullable.group({
    fullName: ['', requiredTrimmed],
    phone: ['', [requiredTrimmed, patternTrimmed(PHONE_PATTERN)]],
    email: ['', [requiredTrimmed, patternTrimmed(EMAIL_PATTERN)]],
    companyName: [''],
    serviceId: [null as number | null],
    message: [''],
  });

  constructor() {
    // A service page reuses this component when moving to a related service,
    // so start a fresh form whenever the preselected service changes.
    effect(() => {
      const serviceId = this.defaultServiceId();
      untracked(() => {
        this.form.reset({ serviceId });
        this.submitted.set(false);
        this.failure.set(null);
      });
    });
  }

  /** Errors show after the first submit attempt, then update as the user types. */
  protected showError(field: FieldName): boolean {
    return this.submitted() && this.form.controls[field].invalid;
  }

  protected errorFor(field: FieldName): string {
    const errors = this.form.controls[field].errors ?? {};
    if (typeof errors['server'] === 'string') return errors['server'];
    const key = Object.keys(errors)[0];
    return MESSAGES[field][key] ?? 'Check this field';
  }

  protected submit(): void {
    this.submitted.set(true);
    this.failure.set(null);
    if (this.form.invalid || this.sending()) return;

    this.sending.set(true);
    this.enquiries
      .submit(this.toRequest())
      .pipe(takeUntilDestroyed(this.destroyRef))
      .subscribe({
        next: ({ referenceNo }) =>
          this.router.navigate(['/contact/thank-you'], { queryParams: { ref: referenceNo } }),
        error: (error: unknown) => {
          this.sending.set(false);
          this.handleError(error);
        },
      });
  }

  private toRequest(): EnquiryRequest {
    const v = this.form.getRawValue();
    const optional = (value: string) => value.trim() || null;
    return {
      fullName: v.fullName.trim(),
      phone: v.phone.trim(),
      email: v.email.trim(),
      companyName: this.variant() === 'full' ? optional(v.companyName) : null,
      serviceId: v.serviceId,
      locationId: this.locationId(),
      message: optional(v.message),
      sourcePage: this.sourcePage(),
    };
  }

  private handleError(error: unknown): void {
    const fieldErrors = apiFieldErrors(error);
    const unmatched: string[] = [];
    for (const [field, message] of Object.entries(fieldErrors ?? {})) {
      const control = this.form.get(field);
      if (control) control.setErrors({ server: message });
      else unmatched.push(message);
    }
    if (fieldErrors && !unmatched.length) return;

    this.failure.set(
      unmatched.length
        ? unmatched.join(' ')
        : apiErrorMessage(
            error,
            'We could not send your enquiry just now. Please try again, or call the office.',
          ),
    );
  }
}
