import { Component, forwardRef, Input, AfterViewChecked } from '@angular/core';
import { WjInputNumber, wjInputNumberMeta } from 'wijmo/wijmo.angular2.input';

/**
 * 数字入力拡張
 */
// tslint:disable-next-line: component-class-suffix
@Component({
  // tslint:disable-next-line: component-selector
  selector: 'ea-input-number',
  template: wjInputNumberMeta.template,
  // tslint:disable-next-line: use-input-property-decorator
  inputs: wjInputNumberMeta.inputs,
  // tslint:disable-next-line: use-output-property-decorator
  outputs: wjInputNumberMeta.outputs,
  providers: [
    { provide: 'WjComponent', useExisting: forwardRef(() => EaInputNumber) },
    wjInputNumberMeta.providers
  ]
})
// tslint:disable-next-line: component-class-suffix
export class EaInputNumber extends WjInputNumber {

  /**
   * 初期処理
   */
  created() {
    this.isRequired = false;
  }

}
