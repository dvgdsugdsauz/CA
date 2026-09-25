import { Injectable, inject } from '@angular/core';
import { Observable } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult } from '../http/api-response';
import { Testimonial, TestimonialRequest } from '../models';

/** Client testimonials (TestimonialController). */
@Injectable({ providedIn: 'root' })
export class TestimonialService {
  private readonly api = inject(ApiClient);

  // ---------------- Public ----------------

  getActive(): Observable<Testimonial[]> {
    return this.api.getList<Testimonial>(API.Testimonial.GET_ACTIVE_TESTIMONIALS);
  }

  // ---------------- Back office ----------------

  getAll(): Observable<Testimonial[]> {
    return this.api.getList<Testimonial>(API.Testimonial.GET_ALL_TESTIMONIALS);
  }

  save(request: TestimonialRequest): Observable<CommandResult> {
    return this.api.command(API.Testimonial.SAVE_TESTIMONIAL, request);
  }

  delete(testimonialId: number): Observable<CommandResult> {
    return this.api.command(API.Testimonial.DELETE_TESTIMONIAL, null, { testimonialId });
  }
}
