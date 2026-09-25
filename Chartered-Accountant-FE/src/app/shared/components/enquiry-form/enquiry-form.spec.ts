import { HttpErrorResponse } from '@angular/common/http';
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Router, provideRouter } from '@angular/router';
import { of, throwError } from 'rxjs';
import { EnquiryService } from '../../../core/services/enquiry-service';
import { ServiceCatalogService } from '../../../core/services/service-catalog-service';
import { SharedModule } from '../../shared-module';
import { EnquiryForm } from './enquiry-form';

const GST = { id: 4, name: 'GST Registration & Returns' };

describe('EnquiryForm', () => {
  let fixture: ComponentFixture<EnquiryForm>;
  let element: HTMLElement;
  let submit: ReturnType<typeof vi.fn>;

  beforeEach(async () => {
    submit = vi.fn(() => of({ referenceNo: 'ENQ-2026-000004' }));
    await TestBed.configureTestingModule({
      imports: [SharedModule],
      providers: [
        provideRouter([]),
        { provide: ServiceCatalogService, useValue: { getDropdown: () => of([GST]) } },
        { provide: EnquiryService, useValue: { submit } },
      ],
    }).compileComponents();

    fixture = TestBed.createComponent(EnquiryForm);
    fixture.componentRef.setInput('sourcePage', '/contact');
    element = fixture.nativeElement;
    await fixture.whenStable();
  });

  function type(selector: string, value: string): void {
    const input = element.querySelector<HTMLInputElement>(selector)!;
    input.value = value;
    input.dispatchEvent(new Event('input'));
  }

  async function send(): Promise<void> {
    element.querySelector('form')!.dispatchEvent(new Event('submit'));
    await fixture.whenStable();
  }

  function errors(): string[] {
    return [...element.querySelectorAll('.error')].map((e) => e.textContent!.trim());
  }

  it('shows the required-field messages when sent empty', async () => {
    await send();

    expect(errors()).toEqual([
      'Enter your name',
      'Enter a phone number so we can call you back',
      'Enter your email address',
    ]);
    expect(submit).not.toHaveBeenCalled();
  });

  it('sends a trimmed request and opens the thank-you page', async () => {
    const navigate = vi.spyOn(TestBed.inject(Router), 'navigate').mockResolvedValue(true);
    type('input[autocomplete="name"]', ' Priya Sharma ');
    type('input[type="tel"]', '+91 98765 43210');
    type('input[type="email"]', 'priya@example.com');

    await send();

    expect(submit).toHaveBeenCalledWith({
      fullName: 'Priya Sharma',
      email: 'priya@example.com',
      phone: '+91 98765 43210',
      companyName: null,
      serviceId: null,
      locationId: null,
      message: null,
      sourcePage: '/contact',
    });
    expect(navigate).toHaveBeenCalledWith(['/contact/thank-you'], {
      queryParams: { ref: 'ENQ-2026-000004' },
    });
  });

  it('preselects the default service', async () => {
    fixture.componentRef.setInput('defaultServiceId', 4);
    await fixture.whenStable();

    const select = element.querySelector('select')!;
    expect(select.options[select.selectedIndex].textContent!.trim()).toBe('GST Registration & Returns');
  });

  it('shows field errors returned by the server', async () => {
    submit.mockReturnValue(
      throwError(
        () =>
          new HttpErrorResponse({
            status: 400,
            error: {
              statusCode: 400,
              message: 'Please correct the highlighted fields',
              data: { email: 'This email domain is not accepted' },
            },
          }),
      ),
    );
    type('input[autocomplete="name"]', 'Priya');
    type('input[type="tel"]', '+91 98765 43210');
    type('input[type="email"]', 'priya@example.com');

    await send();

    expect(errors()).toEqual(['This email domain is not accepted']);
  });
});
