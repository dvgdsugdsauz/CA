import { ChangeDetectionStrategy, Component } from '@angular/core';

@Component({
  selector: 'app-why-us',
  standalone: false,
  template: `
    <div class="wrap">
      <div class="section-head">
        <span class="eyebrow">How we work</span>
        <h2>Why clients stay with us</h2>
      </div>
      <div class="why-grid">
        @for (reason of reasons; track reason.title) {
          <div class="why-item">
            <h3>{{ reason.title }}</h3>
            <p>{{ reason.text }}</p>
          </div>
        }
      </div>
    </div>
  `,
  styleUrl: './why-us.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class WhyUs {
  protected readonly reasons = [
    {
      title: 'One point of contact',
      text: 'A named manager answers your calls and emails, usually the same working day.',
    },
    {
      title: 'Three-level review',
      text: 'Preparer, reviewer and partner sign-off on every return and report, backed by external peer review.',
    },
    {
      title: 'Qualified team',
      text: 'Chartered accountants with hands-on experience across audit, direct tax, GST and company law.',
    },
    {
      title: 'Reminders before due dates',
      text: 'We track your compliance calendar and ask for documents before each deadline, not after it.',
    },
    {
      title: 'Client portal',
      text: 'Share documents and download filed returns and reports from one secure place.',
    },
    {
      title: 'Weekly status note',
      text: 'What was filed, what is pending and what we need from you, every Friday.',
    },
  ];
}
