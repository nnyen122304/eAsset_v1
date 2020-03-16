import {Injectable} from '@angular/core';
import {SnackbarService} from 'ngx-snackbar';
import {MessagePopupComponent} from '../parts/message-popup/message-popup.component';
import {MessageItem} from '../models/message-item';


/**
 * メッセージ（情報・警告・エラー）を画面に表示するサービス
 */
@Injectable({
  providedIn: 'root'
})
export class MessageService {

  /**
   * 行数
   */
  private nbRows = 5;

  /**
   * メッセージ一覧情報
   */
  private messagesList: MessageItem[];

  /**
   * ポップアップ Ref
   */
  private popupRef: MessagePopupComponent;

  constructor(private snackbarService: SnackbarService) {
    this.messagesList = [];
  }

  /**
   * ポップアップ refを設定
   * @param popupRef ポップアップ要素
   */
  public setPopupRef(popupRef: MessagePopupComponent) {
    this.popupRef = popupRef;
  }

  /**
   * 情報メッセージを表示する
   * @param msg 表示するメッセージ（単一メッセージの場合は文字列、複数メッセージの場合は文字配列）
   */
  public info(msg: string | string[]): void {
    this.addSnackbarMessage(msg, 'info');
  }

  /**
   * 警告メッセージを表示する
   * @param msg 表示するメッセージ（単一メッセージの場合は文字列、複数メッセージの場合は文字配列）
   */
  public warn(msg: string | string[]): void {
    this.addMessage(msg, 'warning');
    this.popupRef.open(document.activeElement);
  }

  /**
   * エラーメッセージを表示する
   * @param msg 表示するメッセージ（単一メッセージの場合は文字列、複数メッセージの場合は文字配列）
   */
  public err(msg: string | string[]): void {
    this.addMessage(msg, 'error');
    this.popupRef.open(document.activeElement);
  }

  /**
   * メッセージを取得
   * @return メッセージ配列
   */
  public getMessages(): MessageItem[] {
    return this.messagesList;
  }

  /**
   * メッセージをポップアップに追加する
   * @param msg 表示するメッセージ（単一メッセージの場合は文字列、複数メッセージの場合は文字配列）
   * @param msgType メッセージの種類
   */
  public addMessage(msg: string | string[], msgType: string): void {
    this.messagesList = [];
    if ((typeof msg === 'string') || !msg.forEach) {
      msg = [msg.toString()];
    }
    msg.forEach(value => {
      if (value && value.trim() !== '') {
        this.messagesList.unshift({
          type: msgType,
          message: value
        });
      }
    });
    this.messagesList = this.messagesList.slice();
  }

  /**
   * メッセージを削除する
   */
  public clear() {
    this.messagesList = [];
    this.messagesList = this.messagesList.slice();
  }

  /**
   * メッセージ表示処理
   * @param msg メッセージ
   * @param msgType メッセージ種別(info | warn | err)
   */
  private addSnackbarMessage(msg: string | string[], msgType: string): void {
    let snackbarMsg = '<div style="width: 450px; max-height: 300px; overflow-y:auto;"><div class="title-bar"></div><ul>';

    if ((typeof msg === 'string') || !msg.forEach) {
      msg = [msg.toString()];
    }

    msg.forEach(value => {
      if (value && value.trim() !== '') {
        snackbarMsg += '<li class="li-' + msgType + '">' + value + '</li>';
      }
    });

    if (msg.length < 5) {
      const remaining = this.nbRows - msg.length;
      for (let i = 0; i < remaining; i++) {
        snackbarMsg += '<li class="li-empty"></li>';
      }
    }

    snackbarMsg += '</ul>';

    snackbarMsg += '</div>';

    const snackbarData = {
      msg: snackbarMsg,
      timeout: msgType === 'info' ? 4000 : 0,
      action: {
        text: 'x'
      }
    };
    this.snackbarService.add(snackbarData);
  }

}
