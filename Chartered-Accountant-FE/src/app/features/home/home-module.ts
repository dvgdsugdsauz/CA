import { NgModule } from '@angular/core';
import { SharedModule } from '../../shared/shared-module';
import { ComplianceCalendar } from './components/compliance-calendar/compliance-calendar';
import { EnquirySection } from './components/enquiry-section/enquiry-section';
import { FaqSection } from './components/faq-section/faq-section';
import { HomeHero } from './components/home-hero/home-hero';
import { IndustryList } from './components/industry-list/industry-list';
import { LatestArticles } from './components/latest-articles/latest-articles';
import { ServiceOverview } from './components/service-overview/service-overview';
import { TestimonialList } from './components/testimonial-list/testimonial-list';
import { WhyUs } from './components/why-us/why-us';
import { HomeRoutingModule } from './home-routing-module';
import { HomePage } from './pages/home-page/home-page';

@NgModule({
  declarations: [
    HomePage,
    ComplianceCalendar,
    EnquirySection,
    FaqSection,
    HomeHero,
    IndustryList,
    LatestArticles,
    ServiceOverview,
    TestimonialList,
    WhyUs,
  ],
  imports: [SharedModule, HomeRoutingModule],
})
export class HomeModule {}
