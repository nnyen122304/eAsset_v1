import {Component, forwardRef} from '@angular/core';
import * as wjcCore from 'wijmo/wijmo';
import * as wjcInput from 'wijmo/wijmo';
import {WjPopup, wjPopupMeta} from 'wijmo/wijmo.angular2.input';

/**
 * ポップアップ拡張
 */
@Component({
  selector: 'ea-popup',
  template: wjPopupMeta.template,
  // tslint:disable-next-line: use-input-property-decorator
  inputs: wjPopupMeta.inputs,
  // tslint:disable-next-line: use-output-property-decorator
  outputs: wjPopupMeta.outputs,
  providers: [
    {provide: 'WjComponent', useExisting: forwardRef(() => EaPopup)},
    wjPopupMeta.providers
  ]
})
// tslint:disable-next-line: component-class-suffix
export class EaPopup extends WjPopup {

  /**
   * ポップアップ作成後処理
   */
  created() {
    this.modal = false;
    const popup = this;
    let header = popup.hostElement.querySelector('.modal-header');
    if (!header) {
      header = popup.hostElement;
    }
    header.addEventListener('mousedown', (e: MouseEvent) => {
      this.dragStart(popup.hostElement, {
        x: e.clientX,
        y: e.clientY
      });
    });
  }

  /**
   * ドラッグ開始イベント
   * @param el ポップアップDOM Ref
   * @param startCursorInfo カーソル位置情報
   */
  dragStart(el: HTMLElement, startCursorInfo: { x: number, y: number }) {

    const startOffset = {
      top: el.offsetTop,
      left: el.offsetLeft
    };

    const mousemoveHandler = (e: MouseEvent) => {
      this.drag(el, startCursorInfo, startOffset, e);
    };

    const dragEndHandler = (e: MouseEvent) => {
      document.removeEventListener('mouseup', dragEndHandler);
      document.removeEventListener('mousemove', mousemoveHandler);
    };

    document.addEventListener('mouseup', dragEndHandler);
    document.addEventListener('mousemove', mousemoveHandler);
  }

  /**
   * ドラッグ中処理
   * @param el ポップアップDOM Ref
   * @param startCursorInfo ドラッグ開始カーソル位置情報
   * @param startOffset ドラッグ開始オフセット情報
   * @param e マウスイベント所不応
   */
  drag(el: HTMLElement, startCursorInfo: { x: number, y: number }, startOffset: { top: number, left: number }, e: MouseEvent) {
    wjcCore.setCss(el, {
      top: startOffset.top - (startCursorInfo.y - e.clientY),
      left: startOffset.left - (startCursorInfo.x - e.clientX),
    });
  }

  /**
   * 表示の時にクラスを追加
   */
  // tslint:disable-next-line:ban-types
  show(modal?: boolean, handleResult?: Function): void {
    if (modal) {
      document.getElementsByTagName('body')[0].classList.add('modal-opens'); // モーダル表示の場合背景CSS追加
    }
    super.show(modal, handleResult);
  }

  /**
   * 背景CSS除外
   */
  // 非表示時にクラスを外す
  hide(dialogResult?: any): void {
    document.getElementsByTagName('body')[0].classList.remove('modal-opens'); // 背景CSS除外
    super.hide(dialogResult);
  }
}
