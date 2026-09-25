import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { authGuard, guestGuard } from '../../core/auth/auth-guard';
import { AdminLayout } from './components/admin-layout/admin-layout';
import { EnquiryListPage } from './pages/enquiry-list-page/enquiry-list-page';
import { LoginPage } from './pages/login-page/login-page';

const routes: Routes = [
  { path: 'login', component: LoginPage, canActivate: [guestGuard], title: 'Sign in' },
  {
    path: '',
    component: AdminLayout,
    canActivate: [authGuard],
    children: [
      { path: '', pathMatch: 'full', redirectTo: 'enquiries' },
      { path: 'enquiries', component: EnquiryListPage, title: 'Enquiries' },
    ],
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AdminRoutingModule {}
