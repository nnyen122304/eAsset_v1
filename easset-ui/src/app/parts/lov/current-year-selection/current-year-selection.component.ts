import { Component, ViewChild, Output, EventEmitter, Input} from '@angular/core';

import * as wjGrid from 'wijmo/wijmo.grid';

import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';

import { LovDataEx } from 'src/app/models/api/lov-data-ex';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import { SystemMessage } from 'src/app/const/system-message';
import { FormGroup } from '@angular/forms';

/**
 * ステータス選択コンポネント
 */

@Component({
  selector: 'app-current-year-selection',
  templateUrl: './current-year-selection.component.html',
  styleUrls: ['./current-year-selection.component.scss']
})
export class CurrentYearSelectionComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * ステータス一覧
   */
  dateList: LovDataEx[];

  @Input() isReadOnly = false;
  /**
   * ツリーフォーム
   */
  @Input() form: FormGroup;

  /**
   * クラスを拡張する
   */
  @Input() extClass = '';

  /**
   * 状態 -1:編集ロック、0:状態設定なし、1～：各エンティティの項目定義に従いセット
   */
  @Input() visibleStatus = true;

  /**
   * 部署コードコントロール名
   */
  @Input() chgSchedulePeriod: string;
  /**
   * 部署コードコントロール名
   */
  @Input() chgSchedulePeriodName: string;

  /**
   * 必須か判定用
   */
  @Input() required: boolean;

  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  /**
   * パラメータ
   */
  @Input() paramMap: object;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ ref
   */
  @ViewChild('statusSelectionPopup', null) statusSelectionPopup: EaPopup;

  /**
   * ステータス選択一覧グリッド Ref
   */
  @ViewChild('gridStatusSelectionList', null) gridStatusSelectionList: EaFlexGrid;

  constructor(private messageService: MessageService, private masterService: MasterService) { }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.statusSelectionPopup.show(true);
    if (!this.dateList) {
      this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe((resp: LovDataEx[]) => {
        this.dateList = resp;
        this.gridStatusSelectionList.resetSelection();
      });
    }
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.statusSelectionPopup.hide();
    setTimeout(() => {
      this.gridStatusSelectionList.resetSelection();
      this.gridStatusSelectionList.collectionView.sortDescriptions.clear();
    }, 500);
  }

  /**
   * 親コンポネントに送信
   */
  sendChanges() {
    if (this.gridStatusSelectionList.selectedRows[0]) {
      this.select.emit({
        code: this.gridStatusSelectionList.selectedRows[0].dataItem.code,
        value1: this.gridStatusSelectionList.selectedRows[0].dataItem.value1
      });
      this.close();
    }
  }

  /**
   * 行選択後処理
   * @param e 選択アイテム情報
   */
  onRowSelecting(e: object) {
    if (wjGrid.CellType[this.gridStatusSelectionList.hitTest(e).cellType]  === 'Cell') {
      this.sendChanges();
    }
  }

  /**
   * 確定
   */
  submit() {
    if (this.gridStatusSelectionList.selectedRows[0]) {
      this.sendChanges();
    } else {
      this.messageService.info(SystemMessage.Info.msg100013);
    }
  }

}
