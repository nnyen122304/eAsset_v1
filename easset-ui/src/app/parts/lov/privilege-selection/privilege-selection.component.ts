import { Component, ViewChild, Output, EventEmitter, Input} from '@angular/core';

import * as wjGrid from 'wijmo/wijmo.grid';

import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';

import { LovDataEx } from 'src/app/models/api/lov-data-ex';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import { SystemMessage } from 'src/app/const/system-message';

/**
 * 権限選択ポップアップコンポネント
 */

@Component({
  selector: 'app-privilege-selection',
  templateUrl: './privilege-selection.component.html',
  styleUrls: ['./privilege-selection.component.scss']
})
export class PrivilegeSelectionComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * データフォーム
   */
  dataForm: object;

  /**
   * ステータス一覧
   */
  statusList: LovDataEx[];

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
   * ポップアップ Ref
   */
  @ViewChild('statusSelectionPopup', null) statusSelectionPopup: EaPopup;

  /**
   * 権限選択一覧グリッド Ref
   */
  @ViewChild('gridPrivilegeSelectionList', null) gridPrivilegeSelectionList: EaFlexGrid;

  constructor(
    private messageService: MessageService,
    private masterService: MasterService,
  ) { }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.statusSelectionPopup.show(true);
    if (!this.statusList) {
      this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe((resp: LovDataEx[]) => {
        this.statusList = resp;
        this.gridPrivilegeSelectionList.resetSelection();
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
      this.gridPrivilegeSelectionList.resetSelection();
      this.gridPrivilegeSelectionList.collectionView.sortDescriptions.clear();
    }, 500);
  }

  /**
   * 選択を親コンポネントに送信する
   */
  sendChanges() {
    if (this.gridPrivilegeSelectionList.selectedRows[0]) {
      this.select.emit({
        code: this.gridPrivilegeSelectionList.selectedRows[0].dataItem.code,
        name: this.gridPrivilegeSelectionList.selectedRows[0].dataItem.name
      });
      this.close();
    }
  }

  /**
   * 行選択後処理
   * @param e 選択アイテム
   */
  onRowSelecting(e: object) {
    if (wjGrid.CellType[this.gridPrivilegeSelectionList.hitTest(e).cellType]  === 'Cell') {
      this.sendChanges();
    }
  }

  /**
   * 確定
   */
  submit() {
    if (this.gridPrivilegeSelectionList.selectedRows[0]) {
      this.sendChanges();
    } else {
      this.messageService.info(SystemMessage.Info.msg100008);
    }
  }

}
