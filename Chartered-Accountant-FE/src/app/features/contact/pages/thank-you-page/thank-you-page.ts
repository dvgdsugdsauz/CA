import { ChangeDetectionStrategy, Component, input } from '@angular/core';

/** Shown after an enquiry is saved. */
@Component({
  selector: 'app-thank-you-page',
  standalone: false,
  template: `
    <section class="wrap thanks">
      <span class="eyebrow">Enquiry received</span>
      <h1>Thank you. We'll call you back.</h1>
      <p>
        A chartered accountant will read your enquiry and call you within one working day. If it's
        urgent, call the office and quote your reference.
      </p>
      @if (ref()) {
        <div>
          <p class="muted">Your reference</p>
          <span class="ref-box">{{ ref() }}</span>
        </div>
      }
      <div class="actions">
        <a class="btn btn-primary" routerLink="/">Back to home</a>
        <a class="btn btn-ghost" routerLink="/services">Browse services</a>
      </div>
    </section>
  `,
  styleUrl: './thank-you-page.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class ThankYouPage {
  /** Bound from the `?ref=` query parameter. */
  readonly ref = input<string>();
}
