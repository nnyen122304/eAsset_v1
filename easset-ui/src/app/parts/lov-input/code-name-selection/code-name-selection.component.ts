import { Component, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { MessageService } from 'src/app/services/message.service';
import { MasterService } from 'src/app/services/api/master.service';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { SystemMessage } from 'src/app/const/system-message';
import { FormBuilder, FormGroup } from '@angular/forms';
import { EaInputMask } from 'src/app/components/ea-input-mask/ea-input-mask.component';

@Component({
  selector: 'app-code-name-selection',
  templateUrl: './code-name-selection.component.html',
  styleUrls: ['./code-name-selection.component.scss']
})
export class CodeNameSelectionComponent {

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
  @Input() formExportSearch: FormGroup;

  /**
   * ツリーフォーム
   */
  formSearch: FormGroup;
  /**
   * hiden formSearch
   */
  @Input() hidenSearch = false;

  /**
   * title popup
   */
  @Input() modalTitle = 'メーカー';

  /**
   * label popup
   */
  @Input() modalLabelTitle = 'メーカー選択';

  /**
   * title header grid
   */
  @Input() headerGrid: string;

  /**
   * grid
   */
  @Input() gridClass: string;

  /**
   * binding
   */
  @Input() value = 'value1';

  /**
   * クラスを拡張する
   */
  @Input() extClass = '';

  /**
   * hiden buton clear
   */
  @Input() isShowButtonClear = true;

  /**
   * 状態 -1:編集ロック、0:状態設定なし、1～：各エンティティの項目定義に従いセット
   */
  @Input() visibleStatus = true;


  @Input() isReadOnly = true;
  /**
   * 部署コードコントロール名
   */
  @Input() statusCodeControlName: string;
  /**
   * 部署コードコントロール名
   */
  @Input() statusNameControlName: string;

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
    if (this.sqlName === 'selectCodeName_LOV') {
      this.search();
    }
  }

  /**
   * 検索リクエスト
   */
  search() {
    const filterText = this.formSearch.controls.filterText.value ? this.formSearch.controls.filterText.value : '';

    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe((resp: LovDataEx[]) => {
      this.statusList = resp.filter(x => x.value1.toLowerCase().indexOf(filterText.toLowerCase()) > -1);
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
    this.formExportSearch.controls[this.statusCodeControlName].setValue(null);
    this.formExportSearch.controls[this.statusNameControlName].setValue(null);
    this.select.emit(null);
  }

  /**
   * 親コンポネントに送信
   */
  sendChanges() {
    if (this.gridCodeNameSelectionList.selectedRows[0]) {
      this.formExportSearch.controls[this.statusNameControlName].setValue(this.gridCodeNameSelectionList.selectedItems[0].value1);
      this.formExportSearch.controls[this.statusCodeControlName].setValue(this.gridCodeNameSelectionList.selectedItems[0].code);
      this.gridCodeNameSelectionList.selectedRows[0].isShow = true;
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
