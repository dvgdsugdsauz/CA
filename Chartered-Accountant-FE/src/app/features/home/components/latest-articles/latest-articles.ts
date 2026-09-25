import { ChangeDetectionStrategy, Component, input } from '@angular/core';
import { ArticleSummary } from '../../../../core/models';

@Component({
  selector: 'app-latest-articles',
  standalone: false,
  template: `
    <div class="wrap">
      <div class="section-head split">
        <div>
          <span class="eyebrow">Newsletters</span>
          <h2>Latest from our desk</h2>
        </div>
        <a class="link-more" routerLink="/newsletters">All newsletters</a>
      </div>
      <ul class="article-list">
        @for (article of articles(); track article.articleId) {
          <li class="article-row">
            <time class="mono" [attr.datetime]="article.publishedAt">
              {{ article.publishedAt | date: 'd MMM y' }}
            </time>
            <div>
              <h3>
                <a [routerLink]="['/newsletters', article.slug]">{{ article.title }}</a>
              </h3>
              <p>{{ article.summary }}</p>
            </div>
            <span class="tag">{{ article.category }}</span>
          </li>
        }
      </ul>
    </div>
  `,
  styleUrl: './latest-articles.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class LatestArticles {
  readonly articles = input.required<ArticleSummary[]>();
}
