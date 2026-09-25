import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { NgModule, provideBrowserGlobalErrorListeners } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { TitleStrategy } from '@angular/router';
import { environment } from '../environments/environment';
import { App } from './app';
import { AppRoutingModule } from './app-routing-module';
import { authInterceptor } from './core/auth/auth-interceptor';
import { mockApiInterceptor } from './core/mock-api/mock-api-interceptor';
import { PageTitleStrategy } from './core/routing/page-title-strategy';
import { LayoutModule } from './layout/layout-module';

@NgModule({
  declarations: [App],
  imports: [BrowserModule, AppRoutingModule, LayoutModule],
  providers: [
    provideBrowserGlobalErrorListeners(),
    provideHttpClient(
      withFetch(),
      // The mock API must be last so it sees requests after auth headers are added.
      withInterceptors([authInterceptor, ...(environment.useMockApi ? [mockApiInterceptor] : [])]),
    ),
    { provide: TitleStrategy, useClass: PageTitleStrategy },
  ],
  bootstrap: [App],
})
export class AppModule {}
