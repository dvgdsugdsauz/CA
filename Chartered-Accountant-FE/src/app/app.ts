import { ViewportScroller } from '@angular/common';
import { ChangeDetectionStrategy, Component, inject } from '@angular/core';

/** Height of the sticky site header, so in-page anchors don't land underneath it. */
const HEADER_OFFSET_PX = 96;

@Component({
  selector: 'app-root',
  standalone: false,
  template: '<router-outlet />',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class App {
  constructor() {
    inject(ViewportScroller).setOffset([0, HEADER_OFFSET_PX]);
  }
}
