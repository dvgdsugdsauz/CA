import { Injectable, inject } from '@angular/core';
import { Observable, map } from 'rxjs';
import { ApiClient } from '../http/api-client';
import { API } from '../http/api-endpoints';
import { CommandResult, PageResponse, emptyPage } from '../http/api-response';
import { ArticleDetail, ArticleRequest, ArticleStatus, ArticleSummary, PageQuery } from '../models';

export interface PublishedArticleQuery extends PageQuery {
  category?: string | null;
}

export interface ArticleQuery extends PageQuery {
  status?: ArticleStatus | null;
  search?: string | null;
}

/** Newsletters (ArticleController). */
@Injectable({ providedIn: 'root' })
export class ArticleService {
  private readonly api = inject(ApiClient);

  // ---------------- Public ----------------

  /** Published articles, newest first. */
  getPublished(query: PublishedArticleQuery = {}): Observable<PageResponse<ArticleSummary>> {
    return this.api
      .get<PageResponse<ArticleSummary>>(API.Article.GET_PUBLISHED_ARTICLES, { ...query })
      .pipe(map((page) => page ?? emptyPage<ArticleSummary>()));
  }

  getLatest(count: number): Observable<ArticleSummary[]> {
    return this.getPublished({ page: 0, size: count }).pipe(map((page) => page.content));
  }

  getBySlug(slug: string): Observable<ArticleDetail | null> {
    return this.api.get<ArticleDetail>(API.Article.GET_ARTICLE_BY_SLUG(slug));
  }

  // ---------------- Back office ----------------

  /** All articles, drafts included, newest first. */
  getAll(query: ArticleQuery = {}): Observable<PageResponse<ArticleSummary>> {
    return this.api
      .get<PageResponse<ArticleSummary>>(API.Article.GET_ALL_ARTICLES, { ...query })
      .pipe(map((page) => page ?? emptyPage<ArticleSummary>()));
  }

  getById(articleId: number): Observable<ArticleDetail | null> {
    return this.api.get<ArticleDetail>(API.Article.GET_ARTICLE_BY_ID(articleId));
  }

  save(request: ArticleRequest): Observable<CommandResult> {
    return this.api.command(API.Article.SAVE_ARTICLE, request);
  }

  delete(articleId: number): Observable<CommandResult> {
    return this.api.command(API.Article.DELETE_ARTICLE, null, { articleId });
  }
}
