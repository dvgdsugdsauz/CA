import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ContactPage } from './pages/contact-page/contact-page';
import { ThankYouPage } from './pages/thank-you-page/thank-you-page';

const routes: Routes = [
  { path: '', component: ContactPage, title: 'Contact us' },
  { path: 'thank-you', component: ThankYouPage, title: 'Thank you' },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ContactRoutingModule {}
