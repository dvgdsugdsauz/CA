import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared-module';
import { ServiceDetailPage } from './pages/service-detail-page/service-detail-page';
import { ServiceListPage } from './pages/service-list-page/service-list-page';
import { ServicesRoutingModule } from './services-routing-module';

@NgModule({
  declarations: [ServiceDetailPage, ServiceListPage],
  imports: [SharedModule, ServicesRoutingModule],
})
export class ServicesModule {}
