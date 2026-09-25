import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

function trimmed(control: AbstractControl): string {
  return typeof control.value === 'string' ? control.value.trim() : '';
}

/** Like Validators.required, but whitespace-only input also counts as empty. */
export const requiredTrimmed: ValidatorFn = (control): ValidationErrors | null =>
  trimmed(control) ? null : { required: true };

/** Tests the trimmed value against `pattern`. Empty values pass; pair with requiredTrimmed. */
export function patternTrimmed(pattern: RegExp): ValidatorFn {
  return (control): ValidationErrors | null => {
    const value = trimmed(control);
    return !value || pattern.test(value) ? null : { pattern: true };
  };
}
