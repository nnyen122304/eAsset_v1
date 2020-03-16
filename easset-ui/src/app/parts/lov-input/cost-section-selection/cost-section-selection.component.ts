import { Component, OnInit, ViewChild, Output, EventEmitter, Input } from '@angular/core';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { FormGroup, FormBuilder } from '@angular/forms';
import { EaInputMask } from 'src/app/components/ea-input-mask/ea-input-mask.component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { MasterService } from 'src/app/services/api/master.service';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { Section } from 'src/app/models/api/section';

@Component({
  selector: 'app-cost-section-selection',
  templateUrl: './cost-section-selection.component.html',
  styleUrls: ['./cost-section-selection.component.scss']
})
export class CostSectionSelectionComponent implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;

  /**
   * Info modal title
   */
  @Input() modalTitle = '経費負担部課選択';

  /**
   * Hidden button clear
   */
  @Input() isShow = false;

  /**
   * 題名
   */
  @Input() title: string;

  /**
   * 検索フォーム
   */
  @Input() sectionForm: FormGroup;

  /**
   * 社員名コントロール名
   */
  @Input() costSectionCodeControlName: string;

  /**
   * 社員名コントロール名
   */
  @Input() costSectionNameControlName: string;

  /**
   * 部課名検索キーワード
   */
  @Input() staffCode: string;

  /**
   * 検索日付From
   */
  @Input() searchDateFrom: Date;

  /**
   * 検索日付To
   */
  @Input() searchDateTo: Date;

  /**
   * Focus
   */
  @Input() isFocus = false;

  /**
   * コスト Class
   */
  @Input() extClass: string;

  /**
   * Disabled
   */
  @Input() disabled: boolean;

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
  @ViewChild('costSectionPopup', null) costSectionPopup: EaPopup;

  /**
   * 社員コード入力 Ref
   */
  @ViewChild('costSectionCode', null) inputMaskSectionCode: EaInputMask;

  /**
   * 社員コード入力 Ref
   */
  @ViewChild('costSectionName', null) inputMaskSectionName: EaInputMask;

  /**
   * 社員コード入力 Ref
   */
  @ViewChild('gridCostSectionList', null) gridCostSectionList: EaFlexGrid;

  sectionList: Section[] = [];

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
    private messageService: MessageService,
  ) {
    this.fb = fb;
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.searchForm = this.fb.group({
      sectionName: [''],
    });
  }

  /**
   * Init
   */
  ngOnInit() {
    if (this.isFocus) {
      document.getElementById('costSectionCode').focus();
    }
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.reset();
    this.costSectionPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.costSectionPopup.hide();
  }

  /**
   * リセット
   */
  reset() {
    this.sectionList = [];
    this.searchForm.reset();
  }

  /**
   * 検索リクエスト
   */
  search() {
    const sectionNameSearch = (this.searchForm.controls.sectionName.value) ? this.searchForm.controls.sectionName.value : '';

    this.masterService.searchCostSection(
      this.staffCode, sectionNameSearch, this.searchDateFrom, this.searchDateTo).subscribe(
      (data: Section[]) => {
        this.sectionList = data;
        this.gridCostSectionList.resetSelection();
      });
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.gridCostSectionList && this.gridCostSectionList.selectedRows.length) {
      this.sectionForm.controls[this.costSectionCodeControlName].setValue(this.gridCostSectionList.selectedRows[0].dataItem.sectionCode);
      this.sectionForm.controls[this.costSectionNameControlName].setValue(this.gridCostSectionList.selectedRows[0].dataItem.sectionName);
      this.select.emit(this.gridCostSectionList.selectedRows[0].dataItem);
      this.close();
    } else {
      this.messageService.info(SystemMessage.Info.msg100008);
    }
  }

  /**
   * 文字入力後処理
   * @param event キーボードイベント
   */
  onInput(event: KeyboardEvent) {
    if (event.keyCode === 13 || this.sectionForm.controls[this.costSectionCodeControlName].value.length === 0) {
      this.getCodeNameData();
    }
  }

  /**
   * 社員情報取得
   */
  getCodeNameData() {
    setTimeout(() => {
      const sectionCodeSearch = this.sectionForm.controls[this.costSectionCodeControlName].value;
      const searchDateTo = !this.searchDateTo ? new Date() : this.searchDateTo;
      const searchDateFrom = !this.searchDateFrom ? new Date() : this.searchDateFrom;
      if (sectionCodeSearch != null) {

        this.masterService.getCostSection(this.staffCode, sectionCodeSearch, searchDateFrom, searchDateTo).subscribe((resp: Section) => {
            if (resp) {
              this.sectionForm.controls[this.costSectionNameControlName].setValue(resp.sectionName);
              this.sectionForm.controls[this.costSectionCodeControlName].setValue(sectionCodeSearch);
              this.select.emit(resp);
            } else {
              this.sectionForm.controls[this.costSectionNameControlName].setValue(null);
              this.select.emit(null);
            }
          }
        );
      } else {
        this.sectionForm.controls[this.costSectionNameControlName].setValue(null);
        this.select.emit(null);
      }
    });
  }

  /**
   * フォーカス時に社員情報取得
   */
  getCostDataOnFocus() {
    setTimeout(() => {
      const value = this.sectionForm.controls[this.costSectionNameControlName].value;
      if (value !== '') {
        this.getCodeNameData();
      }
    }, 100);
  }
}
