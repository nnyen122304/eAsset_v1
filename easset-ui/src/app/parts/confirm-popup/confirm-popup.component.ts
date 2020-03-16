import { Component, ViewChild, Output, EventEmitter } from '@angular/core';

import { SessionService } from 'src/app/services/session.service';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';

/**
 * 確認アラートポップアップコンポネント
 */

@Component({
  selector: 'app-confirm-popup',
  templateUrl: './confirm-popup.component.html',
  styleUrls: ['./confirm-popup.component.scss']
})
export class ConfirmPopupComponent {
  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 表示するメッセージ
   */
  message: string;

  /**
   * 確認を押した時のイベント
   */
  @Output() confirm: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ Ref
   */
  @ViewChild('popup', null) popup: EaPopup;

  constructor(
    private sessionService: SessionService
  ) {
  }

  /**
   * ポップアップを開く
   * @param message 表示する確認メッセージ
   */
  prompt(message: string, opener: Element) {
    this.opener = opener;
    this.message = message;
    this.popup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    if (this.opener) {
      // tslint:disable-next-line: no-any
      const openerObj: any = this.opener;
      openerObj.focus();
    }
    this.popup.hide();
  }

  /**
   * 確認ボタンを押す時の処理
   */
  ok() {
    this.confirm.emit();
    this.close();
  }
}
