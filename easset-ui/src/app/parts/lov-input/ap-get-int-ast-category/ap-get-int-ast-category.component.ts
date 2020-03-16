import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { FormBuilder, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-ap-get-int-ast-category',
  templateUrl: './ap-get-int-ast-category.component.html',
  styleUrls: ['./ap-get-int-ast-category.component.scss']
})
export class ApGetIntAstCategoryComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * ステータス一覧
   */
  categoryList: LovDataEx[];

  /**
   * ツリーフォーム
   */
  searchForm: FormGroup;

  /**
   * 取得申請(無形):資産明細分類フォーム
   */
  @Input() astCategoryForm: FormGroup;

  /**
   * Modal title
   */
  @Input() modalTitle = '分類選択';

  /**
   * クラスを拡張する
   */
  @Input() extClass = '';

  /**
   * Hidden button clear
   */
  @Input() isShowButtonClear = true;

  /**
   * 状態 -1:編集ロック、0:状態設定なし、1～：各エンティティの項目定義に従いセット
   */
  @Input() visibleStatus = true;

  /**
   * 部署コードコントロール名
   */
  @Input() categoryCodeControlName: string;
  /**
   * 部署コードコントロール名
   */
  @Input() categoryNameControlName: string;

  /**
   * 必須か判定用
   */
  @Input() required: boolean;

  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  /**
   * Focus
   */
  @Input() isFocus = false;

  /**
   * パラメータ
   */
  @Input() paramMap: object;

  /**
   * Header label
   */
  @Input() headerGrid = '分類';

  /**
   * Field name
   */
  @Input() binding = 'value1';

  /**
   * Read only
   */
  @Input() isReadOnly = false;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ ref
   */
  @ViewChild('astCategoryPopup', null) astCategoryPopup: EaPopup;

  /**
   * ステータス選択一覧グリッド Ref
   */
  @ViewChild('gridCategoryList', null) gridCategoryList: EaFlexGrid;

  constructor(private messageService: MessageService, private masterService: MasterService, private fb: FormBuilder) {
    this.fb = fb;
    this.searchForm = this.fb.group({
      filterText: ['']
    });
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.astCategoryPopup.show(true);
    this.searchForm.controls.filterText.setValue('');
    this.search();
  }

  /**
   * 検索リクエスト
   */
  search() {
    const filterText = this.searchForm.controls.filterText.value ? this.searchForm.controls.filterText.value : '';

    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe((resp: LovDataEx[]) => {
      this.categoryList = resp;
      this.gridCategoryList.resetSelection();
    });
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.astCategoryPopup.hide();
  }

  /**
   * ツリーをクリア
   */
  clear() {
    this.categoryList = [];
    this.searchForm.reset();
    this.astCategoryForm.controls[this.categoryCodeControlName].setValue(null);
    this.astCategoryForm.controls[this.categoryNameControlName].setValue(null);
    this.select.emit(null);
  }

  /**
   * 確定
   */
  submit() {
    if (this.gridCategoryList.selectedRows[0]) {
      this.astCategoryForm.controls[this.categoryCodeControlName].setValue(this.gridCategoryList.selectedItems[0].code);
      this.astCategoryForm.controls[this.categoryNameControlName].setValue(this.gridCategoryList.selectedItems[0].value1);
      this.gridCategoryList.selectedRows[0].isShow = true;
      this.select.emit(this.gridCategoryList.selectedRows[0].dataItem);
      this.close();
    }
  }
}

