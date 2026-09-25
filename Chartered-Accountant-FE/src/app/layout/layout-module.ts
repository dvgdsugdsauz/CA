import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared-module';
import { Brand } from './brand/brand';
import { NewsletterSignup } from './newsletter-signup/newsletter-signup';
import { PublicLayout } from './public-layout/public-layout';
import { SiteFooter } from './site-footer/site-footer';
import { SiteHeader } from './site-header/site-header';
import { Topbar } from './topbar/topbar';

/** The public site shell. The back office has its own shell in the admin feature. */
@NgModule({
  declarations: [Brand, NewsletterSignup, PublicLayout, SiteFooter, SiteHeader, Topbar],
  imports: [SharedModule],
  exports: [PublicLayout],
})
export class LayoutModule {}
