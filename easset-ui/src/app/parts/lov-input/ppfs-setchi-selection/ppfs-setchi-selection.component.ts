import { Component, Output, ViewChild, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import { MasterService } from 'src/app/services/api/master.service';
import { MessageService } from 'src/app/services/message.service';

import { SystemMessage } from 'src/app/const/system-message';

@Component({
  selector: 'app-ppfs-setchi-selection',
  templateUrl: './ppfs-setchi-selection.component.html',
  styleUrls: ['./ppfs-setchi-selection.component.scss']
})
export class PpfsSetchiSelectionComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 代表設置場所一覧
   */
  ppfsSetchiList = [];

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;
  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  /**
   * パラメータ
   */
  @Input() paramMap: object;
  /**
   * 社員検索用フォーム
   */
  @Input() ppfsForm: FormGroup;

  /**
   * 選択のみ
   */
  @Input() selectOnly = true;

  /**
   * 社員コードコントロール名
   */
  @Input() codeControlName: string;

  /**
   * 社員名コントロール名
   */
  @Input() nameControlName: string;
  /**
   * 必須
   */
  @Input() required = true;

  /**
   * 読み取り専用
   */
  @Input() isReadOnly = true;


  /**
   * 状態 -1:編集ロック、0:状態設定なし、1～：各エンティティの項目定義に従いセット
   */
  @Input() visibleStatus = true;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ Ref
   */
  @ViewChild('ppfsSetchiSelectionPopup', null) ppfsSetchiSelectionPopup: EaPopup;

  /**
   * グリッド Ref
   */
  @ViewChild('gridPpfsSetchiSelectionList', null) gridPpfsSetchiSelectionList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private masterService: MasterService,
    private messageService: MessageService
  ) {
    this.fb = fb;
    this.searchForm = this.fb.group({
      search: [],
    });
  }

  /**
   * 検索
   */
  search() {
    if (this.searchForm.controls.search.value !== '') {
      this.paramMap = {...this.paramMap, ...{name: this.searchForm.controls.search.value}};
    }

    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe(
      (resp) => {
        this.ppfsSetchiList = resp;
        if (this.gridPpfsSetchiSelectionList) {
          this.gridPpfsSetchiSelectionList.resetSelection();
        }
      });
  }

  /**
   * 選択をクリア
   */
  clear() {
    this.ppfsSetchiList = [];
    this.searchForm.reset();
    this.select.emit(null);
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.gridPpfsSetchiSelectionList && this.gridPpfsSetchiSelectionList.selectedRows.length) {
      this.ppfsForm.controls[this.codeControlName].setValue(this.gridPpfsSetchiSelectionList.selectedRows[0].dataItem.code);
      this.ppfsForm.controls[this.nameControlName].setValue(this.gridPpfsSetchiSelectionList.selectedRows[0].dataItem.name);
      this.select.emit(this.gridPpfsSetchiSelectionList.selectedRows[0].dataItem);
      this.close();
    } else {
      this.messageService.info(SystemMessage.Info.msg100008);
    }
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.search();
    this.ppfsSetchiSelectionPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.ppfsSetchiSelectionPopup.hide();
  }
}
