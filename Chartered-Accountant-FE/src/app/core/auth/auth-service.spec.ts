import { provideHttpClient } from '@angular/common/http';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { TestBed } from '@angular/core/testing';
import { firstValueFrom } from 'rxjs';
import { AdminUser } from '../models';
import { AuthService } from './auth-service';

const USER: AdminUser = {
  userId: 1,
  username: 'admin',
  fullName: 'Firm Administrator',
  email: 'admin@yourfirm.in',
  role: 'ADMIN',
  enabled: true,
  createdAt: '2026-09-01T09:00:00',
  lastLoginAt: null,
};

describe('AuthService', () => {
  let auth: AuthService;
  let backend: HttpTestingController;

  beforeEach(() => {
    sessionStorage.clear();
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    auth = TestBed.inject(AuthService);
    backend = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    vi.useRealTimers();
    backend.verify();
  });

  function signIn(expiresIn: number): Promise<AdminUser> {
    const result = firstValueFrom(auth.login('admin', 'Admin@123'));
    const req = backend.expectOne('/api/auth/login');
    expect(req.request.body).toEqual({ username: 'admin', password: 'Admin@123' });
    req.flush({
      statusCode: 200,
      message: 'Login successful',
      data: { accessToken: 'jwt-123', tokenType: 'Bearer', expiresIn, user: USER },
    });
    return result;
  }

  it('keeps the access token and user from LoginResponse', async () => {
    expect(await signIn(3600)).toEqual(USER);
    expect(auth.isAuthenticated()).toBe(true);
    expect(auth.token()).toBe('jwt-123');
    expect(auth.user()?.fullName).toBe('Firm Administrator');
  });

  it('treats the session as signed out once the token expires', async () => {
    vi.useFakeTimers({ now: new Date('2026-09-25T10:00:00') });
    await signIn(60);

    vi.setSystemTime(new Date('2026-09-25T10:01:01'));

    expect(auth.isAuthenticated()).toBe(false);
    expect(auth.token()).toBeNull();
  });
});
