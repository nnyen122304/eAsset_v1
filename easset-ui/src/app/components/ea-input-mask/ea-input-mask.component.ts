import { Component, forwardRef, Input, AfterViewChecked, Output, EventEmitter } from '@angular/core';
import { WjInputMask, wjInputMaskMeta } from 'wijmo/wijmo.angular2.input';
import { FormControl } from '@angular/forms';
import { DeleteHighlightedText, GetHighlightedText } from 'src/app/utils/highlited-text';

/**
 * マスク入力拡張
 * バインドされたオブジェクトがundefinedの時に、"undefined"と画面表示される問題の対応
 * (undefined => null へ変換)
 */
// tslint:disable-next-line: component-class-suffix
@Component({
  // tslint:disable-next-line: component-selector
  selector: 'ea-input-mask',
  template: wjInputMaskMeta.template,
  // tslint:disable-next-line: use-input-property-decorator
  inputs: wjInputMaskMeta.inputs,
  // tslint:disable-next-line: use-output-property-decorator
  outputs: wjInputMaskMeta.outputs,
  providers: [
    { provide: 'WjComponent', useExisting: forwardRef(() => EaInputMask) },
    wjInputMaskMeta.providers
  ]
})
// tslint:disable-next-line: component-class-suffix
export class EaInputMask extends WjInputMask implements AfterViewChecked {

  /**
   * 編集前値
   */
  prevValue = '';

  /**
   * 最大文字数
   */
  @Input() maxLength = null;

  /**
   * 必須項目状態
   */
  @Input() required = false;

  /**
   * 入力文字制限パターン
   */
  @Input() pattern = null;

  /**
   * 大文字化
   */
  @Input() capitalize = false;

  /**
   * 親コンポネントフォームコントロール
   */
  @Input() formControl: FormControl;

  /**
   * インプット作成後処理
   */
  created(): void {

    this.rawValueChangePC.subscribe((evt) => {
      if (!evt && this.value === 'undefined') {
        this.value = null;
      }
    });

    if (this.inputElement.value !== this.prevValue) { this.prevValue = this.inputElement.value; }

    this.inputElement.onkeyup = (e: KeyboardEvent) => {
      if (this.pattern || this.capitalize) {
        const value = this.inputElement.value ? this.inputElement.value : '';
        this.textFormat(value);
      }
    };
    this.inputElement.onpaste = (e) => {
      if (this.pattern || this.capitalize) {
        const selectionIndex = this.inputElement.selectionStart;
        const clipboard = (e.clipboardData) ? e.clipboardData.getData('text/plain') : window['clipboardData'].getData('Text');
        DeleteHighlightedText(this.inputElement);
        let count = 0;
        setTimeout(() => {
          count++;
          if (count === 2) {
            const leftPart = this.inputElement.value.substring(0, selectionIndex);
            const rightPart = this.inputElement.value.substring(selectionIndex, this.inputElement.value.length);
            const testVal = leftPart + clipboard + rightPart;
            this.textFormat(testVal);
          }
        }, 100);
      }
    };
  }

  /**
   * インプット更新処理
   */
  ngAfterViewChecked() {
    if (this.maxLength) { this.inputElement.maxLength = this.maxLength; }
    if (this.required) { this.hostElement.classList.add('required'); }
    if (this.pattern || this.capitalize) {
      if (this.pattern) {
        if (this.pattern.includes('A-Z') && !this.pattern.includes('a-z')) {
          this.capitalize = true;
        }
      }
      if (this.capitalize) {
        this.inputElement.style.textTransform = 'uppercase';
      }
    }
  }

  /**
   * Regexパターン通りに値をフォーマットする
   * @param value 項目値
   */
  textFormat(value: string) {
    if (value.length > 0) {
      if (this.capitalize) {
        value = value.toUpperCase();
      }
      if (this.pattern) {
        const regex = new RegExp('[' + this.pattern + ']+', 'g');
        if (value.match(regex)) {
          this.inputElement.value = value.match(regex).join('');
          if (this.maxLength && this.inputElement.value.length > this.maxLength) {
            this.inputElement.value = this.inputElement.value.slice(0, this.maxLength);
          }
          this.prevValue = this.inputElement.value;
        } else {
          this.inputElement.value = this.prevValue;
        }
      } else {
        this.inputElement.value = value;
        this.prevValue = this.inputElement.value;
      }
    } else {
      this.prevValue = '';
    }
    if (this.inputElement && this.formControl) { this.formControl.setValue(this.inputElement.value); }
  }


}
