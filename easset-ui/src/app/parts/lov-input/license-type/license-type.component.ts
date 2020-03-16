import { Component, ViewChild, Output, EventEmitter, Input } from '@angular/core';

import * as wjGrid from 'wijmo/wijmo.grid';

import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';

import { LovDataEx } from 'src/app/models/api/lov-data-ex';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import { SystemMessage } from 'src/app/const/system-message';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-license-type',
  templateUrl: './license-type.component.html',
  styleUrls: ['./license-type.component.scss']
})
export class LicenseTypeComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * ステータス一覧
   */
  dataList: LovDataEx[];

  titlePopup: string;

  titleCol: string;
  /**
   * クラスを拡張する
   */
  popupClass: string;
  /**
   * 選択のみ
   */
  @Input() selectOnly: boolean;

  /**
   * クラスを拡張する
   */
  @Input() nameClass: string;

  /**
   * TODO Form License Type
   */
  @Input() licenseTypeForm: FormGroup;

  /**
   * TODO License Type Name
   */
  @Input() licenseTypeNameControlName: string;

  /**
   * TODO License Type Code
   */
  @Input() licenseTypeCodeControlName: string;

  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  /**
   * パラメータ
   */
  @Input() paramMap;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ ref
   */
  @ViewChild('selectionPopup', null) selectionPopup: EaPopup;

  /**
   * ステータス選択一覧グリッド Ref
   */
  @ViewChild('gridSelectionList', null) gridSelectionList: EaFlexGrid;

  constructor(private messageService: MessageService, private masterService: MasterService) { }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    switch (this.paramMap.categoryCode) {
      case 'LICENSE_ASSET_TYPE':
        this.titlePopup = '資産区分選択';
        this.titleCol = '資産区分';
        this.popupClass = '';
        break;
      case 'LICENSE_GET_TYPE':
        this.titlePopup = '取得形態選択';
        this.titleCol = '取得方法';
        this.popupClass = '';
        break;
      case 'LICENSE_TYPE':
        this.titlePopup = 'ライセンス形態選択';
        this.titleCol = 'ライセンス形態';
        this.popupClass = '';
        break;
      case 'LICENSE_VOLUME_TYPE':
        this.titlePopup = 'ボリュームタイプ選択';
        this.titleCol = 'ボリュームタイプ';
        this.popupClass = 'lic-type';
        break;
    }

    if (!this.dataList) {
      this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe((resp: LovDataEx[]) => {
        this.dataList = resp;
        this.gridSelectionList.resetSelection();
      });
    }
    this.selectionPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.selectionPopup.hide();
    setTimeout(() => {
      this.gridSelectionList.resetSelection();
      this.gridSelectionList.collectionView.sortDescriptions.clear();
    }, 500);
  }

  /**
   * 親コンポネントに送信
   */
  sendChanges() {
    if (this.gridSelectionList.selectedRows[0]) {
      const selectedData = this.gridSelectionList.selectedRows[0].dataItem;
      this.licenseTypeForm.controls[this.licenseTypeNameControlName].setValue(selectedData.value1);
      this.licenseTypeForm.controls[this.licenseTypeCodeControlName].setValue(selectedData.code);
      this.select.emit(selectedData);
      this.close();
    }
  }

  /**
   * 行選択後処理
   * @param e 選択アイテム情報
   */
  onRowSelecting(e: object) {
    if (wjGrid.CellType[this.gridSelectionList.hitTest(e).cellType] === 'Cell') {
      this.sendChanges();
    }
  }

  /**
   * 確定
   */
  submit() {
    if (this.gridSelectionList.selectedRows[0]) {
      this.sendChanges();
    } else {
      this.messageService.info(SystemMessage.Info.msg100013);
    }
  }

  /**
   * ツリーをクリア
   */
  clear() {
    this.licenseTypeForm.controls[this.licenseTypeCodeControlName].setValue('');
    this.licenseTypeForm.controls[this.licenseTypeNameControlName].setValue('');
    this.select.emit(null);
  }

}
