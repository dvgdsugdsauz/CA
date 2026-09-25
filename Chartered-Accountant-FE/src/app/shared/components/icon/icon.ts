import { ChangeDetectionStrategy, Component, computed, inject, input } from '@angular/core';
import { DomSanitizer, SafeHtml } from '@angular/platform-browser';
import { ICONS } from './icons';

/** Decorative line icon. Unknown names render an empty box, so bad data never breaks a card. */
@Component({
  selector: 'app-icon',
  standalone: false,
  template: `<svg
    viewBox="0 0 24 24"
    fill="none"
    stroke="currentColor"
    stroke-width="1.6"
    stroke-linecap="round"
    stroke-linejoin="round"
    aria-hidden="true"
    focusable="false"
    [innerHTML]="markup()"
  ></svg>`,
  styleUrl: './icon.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class Icon {
  private readonly sanitizer = inject(DomSanitizer);

  readonly name = input.required<string | null>();

  // Safe to trust: the API only supplies a key; the markup comes from the constant ICONS map.
  protected readonly markup = computed<SafeHtml>(() =>
    this.sanitizer.bypassSecurityTrustHtml(ICONS[this.name() ?? ''] ?? ''),
  );
}
