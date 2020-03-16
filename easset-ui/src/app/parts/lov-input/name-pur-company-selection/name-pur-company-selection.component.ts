import { Component, OnInit, ViewChild, EventEmitter, Input, Output } from '@angular/core';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { MessageService } from 'src/app/services/message.service';
import { EaInputMask } from 'src/app/components/ea-input-mask/ea-input-mask.component';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { FormGroup, FormBuilder } from '@angular/forms';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { MasterService } from 'src/app/services/api/master.service';
import { SystemMessage } from 'src/app/const/system-message';

@Component({
  selector: 'app-name-pur-company-selection',
  templateUrl: './name-pur-company-selection.component.html',
  styleUrls: ['./name-pur-company-selection.component.scss']
})
export class NamePurCompanySelectionComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * ステータス一覧
   */
  statusList: LovDataEx[];

  /**
   * ツリーフォーム
   */
  @Input() namePurCompanyForm: FormGroup;

  /**
   * ツリーフォーム
   */
  formSearch: FormGroup;
  /**
   * hiden formSearch
   */
  @Input() hidenSearch = false;

  /**
   *   @Input() title: string;
   */
  @Input() title: string;

  /**
   * styleTitle
   */
  @Input() styleTitle: string;

  /**
   * title popup
   */
  @Input() modalTitle: string;

  /**
   * label popup
   */
  @Input() modalLabelTitle: string;

  /**
   * title header grid
   */
  @Input() headerGrid: string;

  /**
   * クラスを拡張する
   */
  @Input() extClass = '';

  /**
   * hiden buton clear
   */
  @Input() isShowButtonClear = true;


  @Input() isReadOnly = true;
  /**
   * 部署コードコントロール名
   */
  @Input() namePurCompanyCode: string;
  /**
   * 部署コードコントロール名
   */
  @Input() namePurCompanyName: string;

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
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ ref
   */
  @ViewChild('codeNameSelectionPopup', null) codeNameSelectionPopup: EaPopup;

  /**
   * 社員コード入力 Ref
   */
  @ViewChild('inputDisplay', null) inputDisplay: EaInputMask;

  /**
   * ステータス選択一覧グリッド Ref
   */
  @ViewChild('gridCodeNameSelectionList', null) gridCodeNameSelectionList: EaFlexGrid;

  constructor(private messageService: MessageService, private masterService: MasterService, private fb: FormBuilder) {
    this.fb = fb;
    this.formSearch = this.fb.group({
      filterText: ['']
    });
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.codeNameSelectionPopup.show(true);
    this.formSearch.controls.filterText.setValue('');
    this.clear();
  }

  /**
   * 検索リクエスト
   */
  search() {
    const filterText = this.formSearch.controls.filterText.value ? this.formSearch.controls.filterText.value : '';
    this.paramMap = {...this.paramMap, name: filterText};
    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe((resp: LovDataEx[]) => {
      this.statusList = resp;
      this.gridCodeNameSelectionList.resetSelection();
    });
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.codeNameSelectionPopup.hide();
  }

  /**
   * ツリーをクリア
   */
  clear() {
    this.statusList = [];
    this.formSearch.reset();
    this.namePurCompanyForm.controls[this.namePurCompanyCode].setValue(null);
    this.namePurCompanyForm.controls[this.namePurCompanyName].setValue(null);
    this.select.emit(null);
  }

  /**
   * 親コンポネントに送信
   */
  sendChanges() {
    if (this.gridCodeNameSelectionList.selectedRows[0]) {
      this.namePurCompanyForm.controls[this.namePurCompanyName].setValue(this.gridCodeNameSelectionList.selectedItems[0].name);
      this.namePurCompanyForm.controls[this.namePurCompanyCode].setValue(this.gridCodeNameSelectionList.selectedItems[0].code);
      this.select.emit(this.gridCodeNameSelectionList.selectedRows[0].dataItem);
      this.close();
    }
  }

  /**
   * 確定
   */
  submit() {
    if (this.gridCodeNameSelectionList.selectedRows[0]) {
      this.sendChanges();
    } else {
      this.messageService.info(SystemMessage.Info.msg100013);
    }
  }

}
