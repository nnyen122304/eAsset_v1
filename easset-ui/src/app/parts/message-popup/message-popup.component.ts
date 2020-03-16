import { Component, Output, ViewChild, EventEmitter, Input, OnChanges, SimpleChanges } from '@angular/core';
import { FlexGrid } from 'wijmo/wijmo.grid';
import * as wjGrid from 'wijmo/wijmo.grid';

import { MessageItem } from 'src/app/models/message-item';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';

/**
 * メッセージポップアップコンポネント
 */

@Component({
  selector: 'app-message-popup',
  templateUrl: './message-popup.component.html',
  styleUrls: ['./message-popup.component.scss']
})
export class MessagePopupComponent implements OnChanges {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 空行配列
   */
  emptyRows: MessageItem[];

  /**
   * 全行配列
   */
  tableRows: MessageItem[];

  /**
   * 更新時に変化を認識する用値
   */
  previewsLength: number;

  /**
   * グリッド最大行数
   */
  nbRows = 10;

  /**
   * メッセージ一覧
   */
  @Input() messagesList: MessageItem[];

  /**
   * データ選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * メッセージポップアップ Ref
   */
  @ViewChild('messagePopup', null) messagePopup: EaPopup;

  /**
   * グリッド Ref
   */
  @ViewChild('gridMessageList', null) gridMessageList: FlexGrid;

  constructor() {
    this.clear();
  }

  /**
   * メッセージ配列更新処理
   * @param changes 更新情報
   */
  ngOnChanges(changes: SimpleChanges) {
    if (!changes.messagesList.firstChange) {
      const diff = this.messagesList.length - this.previewsLength;
      if (diff >= 1) { // If value added
        for (let i = 0; i < diff; i++) {
          this.emptyRows.pop();
        }
      } else { // If value removed
        for (let i = 0; i < diff; i++) {
          this.emptyRows.push({
            type: 'empty',
            message: ''
          });
        }
      }
      this.previewsLength = this.messagesList.length;
      this.updateTable();
    } else {
      this.messagesList = [];
      this.emptyRows = [];
      for (let i = 0; i < this.nbRows; i++) {
        this.emptyRows.push({
          type: 'empty',
          message: ''
        });
      }
      this.updateTable();
    }
  }

  /**
   * グリッド更新処理
   */
  onGridChanged() {
    this.gridMessageList.autoSizeRows(0, this.gridMessageList.rows.length - 1);
  }

  /**
   * 全行配列を更新
   */
  updateTable() {
    this.tableRows = [];
    this.tableRows = this.tableRows.concat(this.messagesList);
    this.tableRows = this.tableRows.concat(this.emptyRows);
  }

  /**
   * メッセージを削除する
   */
  clear() {
    this.previewsLength = 0;
    this.messagesList = [];
    this.emptyRows = [];
    for (let i = 0; i < this.nbRows; i++) {
      this.emptyRows.push({
        type: 'empty',
        message: ''
      });
    }
  }

  /**
   * ポップアップを表示する
   */
  open(opener: Element) {
    this.opener = opener;
    this.messagePopup.show(false); // モードレスでポップアップ表示
    setTimeout(() => {
      this.onGridChanged();
    }, 100);
    this.gridMessageList.cellEditEnding.addHandler((s: wjGrid.FlexGrid, e: wjGrid.CellEditEndingEventArgs) => {
      e.cancel = true;
    });
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
    this.messagePopup.hide();
  }
}
