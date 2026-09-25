import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ServiceDetailPage } from './pages/service-detail-page/service-detail-page';
import { ServiceListPage } from './pages/service-list-page/service-list-page';
import { serviceDetailResolver, serviceTitleResolver } from './service-resolvers';

const routes: Routes = [
  { path: '', component: ServiceListPage, title: 'Services' },
  {
    path: ':slug',
    component: ServiceDetailPage,
    title: serviceTitleResolver,
    resolve: { service: serviceDetailResolver },
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ServicesRoutingModule {}
