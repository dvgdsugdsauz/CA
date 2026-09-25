import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { firstValueFrom } from 'rxjs';
import { ApiClient } from './api-client';
import { apiErrorMessage, apiFieldErrors } from './api-error';

describe('ApiClient', () => {
  let api: ApiClient;
  let backend: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    api = TestBed.inject(ApiClient);
    backend = TestBed.inject(HttpTestingController);
  });

  afterEach(() => backend.verify());

  it('unwraps data and drops empty query params', async () => {
    const result = firstValueFrom(api.get<number[]>('/public/x', { page: 0, search: '', status: null }));
    const req = backend.expectOne((r) => r.url === '/api/public/x');
    expect(req.request.params.keys()).toEqual(['page']);
    req.flush({ statusCode: 200, message: 'Data fetched successfully', data: [1, 2] });

    expect(await result).toEqual([1, 2]);
  });

  it('treats statusCode 204 as no data, and an empty list for getList', async () => {
    const single = firstValueFrom(api.get('/public/one'));
    const many = firstValueFrom(api.getList('/public/many'));
    const empty = { statusCode: 204, message: 'Data not available', data: null };
    backend.expectOne('/api/public/one').flush(empty);
    backend.expectOne('/api/public/many').flush(empty);

    expect(await single).toBeNull();
    expect(await many).toEqual([]);
  });

  it('sends delete ids as query params and reports whether the command applied', async () => {
    const result = firstValueFrom(api.command('/admin/faq/delete', null, { faqId: 7 }));
    const req = backend.expectOne((r) => r.url === '/api/admin/faq/delete');
    expect(req.request.method).toBe('POST');
    expect(req.request.params.get('faqId')).toBe('7');
    req.flush({ statusCode: 204, message: 'Data not available', data: null });

    expect(await result).toEqual({ ok: false, message: 'Data not available' });
  });

  it('exposes the message and field errors of a failed request', async () => {
    const result = firstValueFrom(api.post('/public/enquiry/submit', {})).catch((e: unknown) => e);
    backend
      .expectOne('/api/public/enquiry/submit')
      .flush(
        { statusCode: 400, message: 'Please correct the highlighted fields', data: { email: 'Enter your email address' } },
        { status: 400, statusText: 'Bad Request' },
      );

    const error = await result;
    expect(apiErrorMessage(error, 'fallback')).toBe('Please correct the highlighted fields');
    expect(apiFieldErrors(error)).toEqual({ email: 'Enter your email address' });
  });

  it('falls back when the error has no envelope, e.g. the backend is down', async () => {
    const result = firstValueFrom(api.get('/public/x')).catch((e: unknown) => e);
    backend.expectOne('/api/public/x').error(new ProgressEvent('error'));

    expect(apiErrorMessage(await result, 'Try again later')).toBe('Try again later');
  });
});
