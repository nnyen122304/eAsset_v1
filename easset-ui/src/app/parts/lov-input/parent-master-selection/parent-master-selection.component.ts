import { Component, ViewChild, Input, Output, EventEmitter, OnChanges, SimpleChanges } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';

import { SessionService } from 'src/app/services/session.service';
import { MasterService } from 'src/app/services/api/master.service';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';


/**
 * ビュー更新
 */

@Component({
  selector: 'app-parent-master-selection',
  templateUrl: './parent-master-selection.component.html',
  styleUrls: ['./parent-master-selection.component.scss']
})
export class ParentMasterSelectionComponent implements OnChanges {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * Stores list of master parents
   */
  parentList: LovDataEx[];

  /**
   * Selected Parent Master
   */
  selectedItem: LovDataEx;

  /**
   * フィルターインプット
   */
  filter: string;

  /**
   * 親マスタフォーム
   */
  @Input() parentMasterForm: FormGroup;


  /**
   * マスタコードコントロール名
   */
  @Input() masterCodeControlName: string;

  /**
   * マスタ名コントロール名
   */
  @Input() masterNameControlName: string;

  /**
   * マスタカテゴリーコントロール名
   */
  @Input() masterCategoryControlName: string;

  /**
   * マスタ標準値設定
   */
  @Input() default: string[];

  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  /**
   * パラメータ
   */
  @Input() paramMap: object;

  /**
   * 必須か判定用
   */
  @Input() required: boolean;

  /**
   * 選択のみ
   */
  @Input() selectOnly: boolean;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ Ref
   */
  @ViewChild('parentMasterSelectionPopup', null) parentMasterSelectionPopup: EaPopup;

  /**
   * 親マスタ一覧グリッド Ref
   */
  @ViewChild('gridParentList', null) gridParentList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private masterService: MasterService,
  ) {
    this.fb = fb;
    this.filter = '';
  }

  /**
   * 親マスタ標準値を設定する
   */
  ngOnChanges(changes: SimpleChanges) {
    if (changes.default) {
      this.parentMasterForm.controls[this.masterNameControlName].setValue(this.default);
    }
  }

  /**
   * 初期処理
   */
  init() {
    this.selectedItem = {};
    this.paramMap = Object.assign(this.paramMap, {searchValue1: this.filter});
    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe(
      (resp: LovDataEx[]) => {
      this.parentList = resp;
      if (this.gridParentList) {
        this.gridParentList.resetSelection();
      }
    });
  }

  /**
   * Flexグリッド初期処理
   */
  onGridInitialized() {
    this.gridParentList.resetSelection();
  }

  /**
   * ツリーをクリア
   */
  clear() {
    this.selectedItem = null;
    this.parentMasterForm.controls[this.masterCodeControlName].setValue(null);
    this.parentMasterForm.controls[this.masterNameControlName].setValue(null);
    this.parentMasterForm.controls[this.masterCategoryControlName].setValue(null);
    this.select.emit(null);
    this.resetForm();
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (!this.gridParentList.selectedRows[0]) {
      return;
    }
    this.selectedItem = this.gridParentList.selectedRows[0].dataItem;
    this.parentMasterForm.controls[this.masterCodeControlName].setValue(this.selectedItem.internalCode);
    this.parentMasterForm.controls[this.masterNameControlName].setValue(this.selectedItem.value1);
    this.parentMasterForm.controls[this.masterCategoryControlName].setValue(this.selectedItem.categoryCode);
    this.select.emit(this.selectedItem);
    this.close();
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.resetForm();
    this.init();
    this.parentMasterSelectionPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.parentMasterSelectionPopup.hide();
  }

  /**
   * 絞込フォームリセット
   */
  resetForm() {
    this.parentList = [];
    this.filter = '';
    if (this.gridParentList) {
      this.gridParentList.collectionView.refresh();
    }
  }


}
