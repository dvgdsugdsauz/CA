import { ChangeDetectionStrategy, Component, input } from '@angular/core';

export interface Breadcrumb {
  label: string;
  /** Omit for the current page. */
  link?: string;
}

/** Title band at the top of inner pages. */
@Component({
  selector: 'app-page-header',
  standalone: false,
  templateUrl: './page-header.html',
  styleUrl: './page-header.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class PageHeader {
  readonly heading = input.required<string>();
  readonly eyebrow = input<string | null>(null);
  readonly lede = input<string | null>(null);
  readonly breadcrumbs = input<Breadcrumb[]>([]);
}
