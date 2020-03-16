import { Component, Input } from '@angular/core';
import { wjTabMeta, WjTab } from 'wijmo/wijmo.angular2.nav';

/**
 * タブ拡張
 */

@Component({
  // tslint:disable-next-line: component-selector
  selector: 'ea-view-stack-tab',
  template: wjTabMeta.template,
  // tslint:disable-next-line: use-input-property-decorator
  inputs: wjTabMeta.inputs,
  // tslint:disable-next-line: use-output-property-decorator
  outputs: wjTabMeta.outputs,
  providers: [
    wjTabMeta.providers
  ]
})
// tslint:disable-next-line: component-class-suffix
export class EaViewStackTab extends WjTab {
  /**
   * 画面遷移で別の画面に切り替わった後も、画面のデータ・状態を維持するかどうか
   */
  @Input() keepViewState = false;

}
