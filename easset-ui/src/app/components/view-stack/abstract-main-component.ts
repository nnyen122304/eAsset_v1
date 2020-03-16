import { ViewChild, ViewChildren, QueryList, OnInit, Injectable } from '@angular/core';
import { WjTabPanel } from 'wijmo/wijmo.angular2.nav';
import { Subject } from 'rxjs';
import { EaViewStackTab } from './ea-view-stack-tab/ea-view-stack-tab.component';
import { SessionService } from 'src/app/services/session.service';
import { SystemMessage } from 'src/app/const/system-message';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';

/**
 * 機能メイン画面のスーパークラス
 */
export abstract class AbstractMainComponent {
  /**
   * 機能(子)画面が一度表示されたかどうか（タブ表示制御に使用）
   */
  isLoaded = new Map<number, boolean>();
  /**
   * 機能(子)画面切替イベント用Subjectを保持
   */
  changeChildSubject = new Map<number, Subject<number>>();
  /**
   * 機能(子)画面のオープンパラメータを一時的に保持
   */
  // tslint:disable-next-line: no-any
  private param: any;

  /**
   * 要求するindex
   */
  private requestedIndex: number;

  /**
   * 要求するパラメーター
   */
  // tslint:disable-next-line: no-any
  private requestedParam: {action?: string, params?: {[param: string]: any}};

  /**
   * 機能(子)画面のタブパネル
   */
  @ViewChild('viewStack') protected viewStack: WjTabPanel;

  /**
   * 確認用ポップアップ Ref
   */
  @ViewChild('confirmPopup') protected confirmPopup: ConfirmPopupComponent;

  /**
   * ビュータブ一覧 Ref
   */
  @ViewChildren(EaViewStackTab) protected viewStackTabList: QueryList<EaViewStackTab>;

  constructor(protected ssService: SessionService) {
  }

  /**
   * 機能(子)画面コンポーネントの表示
   * @param index 表示するコンポーネントのindex(tab-index)
   * @param param 表示するコンポーネントへのパラメータ
   */
  // tslint:disable-next-line: no-any
  changeChild(index: number, param?: {action?: string, params?: {[param: string]: any}}): void {

    if (this.ssService.getDataChange()) {
      this.requestedIndex = index;
      this.requestedParam = param;
      this.confirmPopup.prompt(SystemMessage.Conf.msg400001, document.activeElement);
    } else {
      this.acceptChange(index, param);
    }

  }

  /**
   * 画面遷移を許可する
   */
  // tslint:disable-next-line: no-any
  acceptChange(index: number, param?: {action?: string, params?: {[param: string]: any}}) {
    this.ssService.setDataChange(false);
    this.isLoaded.set(index, true);
    this.param = param;
    this.viewStack.selectedIndex = index;

    document.getElementById('focusInit').focus();

    if (this.viewStackTabList) {
      this.viewStackTabList.forEach((item, i) => {
        if (i !== index) {
          if (!item.keepViewState) {
            // keepViewState指定がないものはコンポーネントが破棄されるように設定
            this.isLoaded.set(i, item.keepViewState);
          }
        }
      });
    }
  }

  /**
   * 確認を押した時
   */
  onConfirm() {
    this.acceptChange(this.requestedIndex, this.requestedParam);
  }

  /**
   * タブの変更検知イベントで、該当ChildコンポーネントにchangeChildSubjectに値を送信する
   * @param index 選択されたタブindex
   */
  selectedIndexChangeHandler(index: number): void {
    const subject = this.changeChildSubject.get(index);
    if (subject) {
      subject.next(this.param);
    } else {
      console.log('画面遷移エラー');
    }
  }
}
