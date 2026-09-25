import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared-module';
import { ContactRoutingModule } from './contact-routing-module';
import { ContactPage } from './pages/contact-page/contact-page';
import { ThankYouPage } from './pages/thank-you-page/thank-you-page';

@NgModule({
  declarations: [ContactPage, ThankYouPage],
  imports: [SharedModule, ContactRoutingModule],
})
export class ContactModule {}
