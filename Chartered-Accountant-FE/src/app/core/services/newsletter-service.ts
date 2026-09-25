import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult, PageResponse, emptyPage } from '../http/api-response';
import { NewsletterSubscriber, SubscriberQuery } from '../models';

/** Newsletter subscriptions (NewsletterController). */
@Injectable({ providedIn: 'root' })
export class NewsletterService {
  private readonly api = inject(ApiClient);

  // ---------------- Public ----------------

  /** The message says whether this was a new or existing subscription. */
  subscribe(email: string): Observable<CommandResult> {
    return this.api.command(API.Newsletter.SUBSCRIBE, { email });
  }

  unsubscribe(email: string): Observable<CommandResult> {
    return this.api.command(API.Newsletter.UNSUBSCRIBE, { email });
  }

  // ---------------- Back office ----------------

  getSubscribers(query: SubscriberQuery = {}): Observable<PageResponse<NewsletterSubscriber>> {
    return this.api
      .get<PageResponse<NewsletterSubscriber>>(API.Newsletter.GET_ALL_SUBSCRIBERS, { ...query })
      .pipe(map((page) => page ?? emptyPage<NewsletterSubscriber>()));
  }
}
