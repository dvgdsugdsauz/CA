import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared-module';
import { AdminRoutingModule } from './admin-routing-module';
import { AdminLayout } from './components/admin-layout/admin-layout';
import { EnquiryTable } from './components/enquiry-table/enquiry-table';
import { StatusFilter } from './components/status-filter/status-filter';
import { StatusPill } from './components/status-pill/status-pill';
import { EnquiryListPage } from './pages/enquiry-list-page/enquiry-list-page';
import { LoginPage } from './pages/login-page/login-page';

/** Back office at /admin. Lazy loaded, so public visitors never download it. */
@NgModule({
  declarations: [AdminLayout, EnquiryListPage, EnquiryTable, LoginPage, StatusFilter, StatusPill],
  imports: [SharedModule, AdminRoutingModule],
})
export class AdminModule {}
