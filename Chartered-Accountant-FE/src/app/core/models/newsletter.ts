import { IsoDateTime, PageQuery } from './common';

export interface NewsletterSubscriber {
  subscriberId: number;
  email: string;
  active: boolean;
  subscribedAt: IsoDateTime;
  unsubscribedAt: IsoDateTime | null;
}

export interface SubscriberQuery extends PageQuery {
  active?: boolean | null;
}
