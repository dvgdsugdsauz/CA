import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-not-found-page',
  standalone: false,
  template: `
    <section class="wrap not-found">
      <span class="eyebrow mono">404</span>
      <h1>We couldn't find that page</h1>
      <p>The link may be out of date, or the page may have moved.</p>
      <div class="actions">
        <a class="btn btn-primary" routerLink="/">Go to home</a>
        <a class="btn btn-ghost" routerLink="/contact">Contact us</a>
      </div>
    </section>
  `,
  styles: `
    .not-found {
      max-width: 640px;
      display: grid;
      gap: 18px;
      padding-block: 80px;
    }
    .not-found p {
      color: var(--ink-2);
    }
    .actions {
      display: flex;
      flex-wrap: wrap;
      gap: 12px;
    }
  `,
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class NotFoundPage {}
