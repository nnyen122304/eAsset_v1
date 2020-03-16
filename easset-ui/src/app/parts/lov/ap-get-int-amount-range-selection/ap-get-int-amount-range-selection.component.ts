import { Component, Input, ViewChild, EventEmitter, Output } from '@angular/core';
import * as wjGrid from 'wijmo/wijmo.grid';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { SystemMessage } from 'src/app/const/system-message';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-ap-get-int-amount-range-selection',
  templateUrl: './ap-get-int-amount-range-selection.component.html',
  styleUrls: ['./ap-get-int-amount-range-selection.component.scss']
})
export class ApGetIntAmountRangeSelectionComponent {

  /**
   * 題名
   */
  @Input() title: string;

  /**
   * ステータス一覧
   */
  apGetIntAmountRangeList: LovDataEx[];

  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  /**
   * パラメータ
   */
  @Input() paramMap: object;

  /**
   * 題名スタイル
   */
  @Input() styleTitle: string;

  /**
   * 題名スタイル
   */
  @Input() extClass: string;

  /**
   * 題名Popup
   */
  @Input() titlePopup: string;

  /**
   * 社員検索用フォーム
   */
  @Input() apGetAmountRange: FormGroup;

  /**
   * 社員コードコントロール名
   */
  @Input() apGetAmountRangeName: string;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ Ref
   */
  @ViewChild('apGetIntAmountRangeSelectionPopup', null) apGetIntAmountRangeSelectionPopup: EaPopup;

  /**
   * 取得金額範囲選択一覧グリッド Ref
   */
  @ViewChild('apGetIntAmountRangeSelectionList', null) apGetIntAmountRangeSelectionList: EaFlexGrid;

  constructor(private messageService: MessageService, private masterService: MasterService ) {}

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.apGetIntAmountRangeSelectionPopup.show(true);
    if (!this.apGetIntAmountRangeList) {
      this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe((resp: LovDataEx[]) => {
        this.apGetIntAmountRangeList = resp;
        this.apGetIntAmountRangeSelectionList.resetSelection();
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
    this.apGetIntAmountRangeSelectionPopup.hide();
    setTimeout(() => {
      this.apGetIntAmountRangeSelectionList.resetSelection();
      this.apGetIntAmountRangeSelectionList.collectionView.sortDescriptions.clear();
    }, 500);
  }

  /**
   * 親コンポネントに送信
   */
  sendChanges() {
    if (this.apGetIntAmountRangeSelectionList.selectedRows[0]) {
      this.apGetAmountRange.controls[this.apGetAmountRangeName].setValue(this.apGetIntAmountRangeSelectionList.selectedRows[0].dataItem.value1);
      this.select.emit({
        code: this.apGetIntAmountRangeSelectionList.selectedRows[0].dataItem.code,
        value1: this.apGetIntAmountRangeSelectionList.selectedRows[0].dataItem.value1,
        value7: this.apGetIntAmountRangeSelectionList.selectedRows[0].dataItem.value7,
        value10: this.apGetIntAmountRangeSelectionList.selectedRows[0].dataItem.value10,
        value11: this.apGetIntAmountRangeSelectionList.selectedRows[0].dataItem.value11,
        value12: this.apGetIntAmountRangeSelectionList.selectedRows[0].dataItem.value12
      });
      this.close();
    }
  }

  /**
   * 行選択後処理
   * @param e 選択アイテム情報
   */
  onRowSelecting(e: object) {
    if (wjGrid.CellType[this.apGetIntAmountRangeSelectionList.hitTest(e).cellType]  === 'Cell') {
      this.sendChanges();
    }
  }

  /**
   * 確定
   */
  submit() {
    if (this.apGetIntAmountRangeSelectionList.selectedRows[0]) {
      this.sendChanges();
    } else {
      this.messageService.info(SystemMessage.Info.msg100013);
    }
  }

}
