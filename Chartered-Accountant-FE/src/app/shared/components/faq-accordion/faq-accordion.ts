import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { Faq } from '../../../core/models';

@Component({
  selector: 'app-faq-accordion',
  standalone: false,
  template: `
    @for (faq of faqs(); track faq.faqId; let first = $first) {
      <details [open]="first && openFirst()">
        <summary>{{ faq.question }}</summary>
        <p>{{ faq.answer }}</p>
      </details>
    }
  `,
  styleUrl: './faq-accordion.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class FaqAccordion {
  readonly faqs = input.required<Faq[]>();
  readonly openFirst = input(true);
}
