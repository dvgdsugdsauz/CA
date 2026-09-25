import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { SharedModule } from '../../shared/shared-module';
import { NotFoundPage } from './not-found-page/not-found-page';

const routes: Routes = [{ path: '', component: NotFoundPage, title: 'Page not found' }];

@NgModule({
  declarations: [NotFoundPage],
  imports: [SharedModule, RouterModule.forChild(routes)],
})
export class NotFoundModule {}
