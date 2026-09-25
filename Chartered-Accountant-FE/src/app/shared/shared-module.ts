import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
import { EnquiryForm } from './components/enquiry-form/enquiry-form';
import { FaqAccordion } from './components/faq-accordion/faq-accordion';
import { Icon } from './components/icon/icon';
import { OfficeDetails } from './components/office-details/office-details';
import { PageHeader } from './components/page-header/page-header';
import { ServiceCard } from './components/service-card/service-card';

const COMPONENTS = [EnquiryForm, FaqAccordion, Icon, OfficeDetails, PageHeader, ServiceCard];

/** Reusable UI building blocks. Feature modules import this instead of the Angular modules it re-exports. */
@NgModule({
  declarations: COMPONENTS,
  imports: [CommonModule, ReactiveFormsModule, RouterModule],
  exports: [...COMPONENTS, CommonModule, ReactiveFormsModule, RouterModule],
})
export class SharedModule {}
