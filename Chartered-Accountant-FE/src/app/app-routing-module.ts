import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PublicLayout } from './layout/public-layout/public-layout';

const routes: Routes = [
  // The back office has its own shell, so it sits outside the public layout.
  {
    path: 'admin',
    loadChildren: () => import('./features/admin/admin-module').then((m) => m.AdminModule),
  },
  {
    path: '',
    component: PublicLayout,
    children: [
      {
        path: '',
        pathMatch: 'full',
        loadChildren: () => import('./features/home/home-module').then((m) => m.HomeModule),
      },
      {
        path: 'services',
        loadChildren: () =>
          import('./features/services/services-module').then((m) => m.ServicesModule),
      },
      {
        path: 'contact',
        loadChildren: () => import('./features/contact/contact-module').then((m) => m.ContactModule),
      },
      // About, Newsletters and Careers have no pages yet and fall through to here.
      {
        path: '**',
        loadChildren: () =>
          import('./features/not-found/not-found-module').then((m) => m.NotFoundModule),
      },
    ],
  },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      bindToComponentInputs: true,
      anchorScrolling: 'enabled',
      scrollPositionRestoration: 'enabled',
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
