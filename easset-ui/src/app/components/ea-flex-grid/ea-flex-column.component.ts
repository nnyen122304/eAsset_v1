import { Component, forwardRef, Input } from '@angular/core';
import { wjFlexGridColumnMeta, WjFlexGridColumn } from 'wijmo/wijmo.angular2.grid';

/**
 * グリッドカラム拡張
 * isReadOnly属性のデフォルトをtrueに設定
 */
@Component({
  selector: 'ea-flex-grid-column',
  template: wjFlexGridColumnMeta.template,
  // tslint:disable-next-line: use-input-property-decorator
  inputs: wjFlexGridColumnMeta.inputs,
  // tslint:disable-next-line: use-output-property-decorator
  outputs: wjFlexGridColumnMeta.outputs,
  providers: [
    { provide: 'WjComponent', useExisting: forwardRef(() => EaFlexGridColumn) },
    wjFlexGridColumnMeta.providers
  ]
})
// tslint:disable-next-line: component-class-suffix
export class EaFlexGridColumn extends WjFlexGridColumn {
  /**
   * 初期処理
   */
  created(): void {
    this.isReadOnly = true;
    this.allowMerging = true;
  }
}
