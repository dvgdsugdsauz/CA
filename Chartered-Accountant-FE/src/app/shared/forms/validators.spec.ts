import { FormControl } from '@angular/forms';
import { EMAIL_PATTERN, PHONE_PATTERN } from '../../core/constants/validation';
import { patternTrimmed, requiredTrimmed } from './validators';

describe('requiredTrimmed', () => {
  it('rejects empty and whitespace-only values', () => {
    expect(requiredTrimmed(new FormControl(''))).toEqual({ required: true });
    expect(requiredTrimmed(new FormControl('   '))).toEqual({ required: true });
  });

  it('accepts text', () => {
    expect(requiredTrimmed(new FormControl(' Priya '))).toBeNull();
  });
});

describe('patternTrimmed', () => {
  const email = patternTrimmed(EMAIL_PATTERN);
  const phone = patternTrimmed(PHONE_PATTERN);

  it('leaves empty values to requiredTrimmed', () => {
    expect(email(new FormControl(''))).toBeNull();
  });

  it('checks email addresses like EnquiryForm.java', () => {
    expect(email(new FormControl(' priya@example.com '))).toBeNull();
    expect(email(new FormControl('priya@example'))).toEqual({ pattern: true });
    expect(email(new FormControl('priya example.com'))).toEqual({ pattern: true });
  });

  it('checks phone numbers like EnquiryForm.java', () => {
    expect(phone(new FormControl('+91 98765 43210'))).toBeNull();
    expect(phone(new FormControl('(040) 0000-0000'))).toBeNull();
    expect(phone(new FormControl('1234567'))).toEqual({ pattern: true });
    expect(phone(new FormControl('98765abc10'))).toEqual({ pattern: true });
  });
});
