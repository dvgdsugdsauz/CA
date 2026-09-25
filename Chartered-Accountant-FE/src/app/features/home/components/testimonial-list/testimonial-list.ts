import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { Testimonial } from '../../../../core/models';

@Component({
  selector: 'app-testimonial-list',
  standalone: false,
  template: `
    <div class="wrap">
      <div class="section-head">
        <span class="eyebrow">Clients</span>
        <h2>What clients say</h2>
      </div>
      <div class="quotes">
        @for (item of testimonials(); track item.testimonialId) {
          <figure class="quote">
            <blockquote>{{ item.quote }}</blockquote>
            <figcaption>
              <strong>{{ item.authorName }}</strong>
              <span>{{ item.authorTitle }}</span>
            </figcaption>
          </figure>
        }
      </div>
    </div>
  `,
  styleUrl: './testimonial-list.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class TestimonialList {
  readonly testimonials = input.required<Testimonial[]>();
}
