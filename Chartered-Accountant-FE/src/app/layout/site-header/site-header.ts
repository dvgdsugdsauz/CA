import { ChangeDetectionStrategy, Component, inject, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { NavigationEnd, Router } from '@angular/router';
import { filter } from 'rxjs';

interface NavItem {
  label: string;
  path: string;
}

@Component({
  selector: 'app-site-header',
  standalone: false,
  templateUrl: './site-header.html',
  styleUrl: './site-header.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class SiteHeader {
  protected readonly nav: NavItem[] = [
    { label: 'About', path: '/about' },
    { label: 'Services', path: '/services' },
    { label: 'Newsletters', path: '/newsletters' },
    { label: 'Careers', path: '/careers' },
    { label: 'Contact', path: '/contact' },
  ];

  protected readonly menuOpen = signal(false);

  constructor() {
    // Close the mobile menu once the user picks a page.
    inject(Router)
      .events.pipe(
        filter((e) => e instanceof NavigationEnd),
        takeUntilDestroyed(),
      )
      .subscribe(() => this.menuOpen.set(false));
  }

  protected toggleMenu(): void {
    this.menuOpen.update((open) => !open);
  }
}
