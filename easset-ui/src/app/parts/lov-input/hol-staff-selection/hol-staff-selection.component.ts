import { Component, Output, ViewChild, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import { SessionService } from 'src/app/services/session.service';
import { MasterService } from 'src/app/services/api/master.service';
import { MessageService } from 'src/app/services/message.service';

import { SessionInfo } from 'src/app/models/session-info';
import { SystemMessage } from 'src/app/const/system-message';

@Component({
  selector: 'app-hol-staff-selection',
  templateUrl: './hol-staff-selection.component.html',
  styleUrls: ['./hol-staff-selection.component.scss']
})
export class HolStaffSelectionComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 社員一覧
   */
  holStaffList = [];

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
  @Input() paramMap;

  /**
   * 社員検索用フォーム
   */
  @Input() holStaffForm: FormGroup;

  /**
   * クラスを拡張する
   */
  @Input() holStaffClass: string;

  /**
   * 選択のみ
   */
  @Input() selectOnly: boolean;

  /**
   * 社員コードコントロール名
   */
  @Input() staffCodeControlName: string;

  /**
   * 社員名コントロール名
   */
  @Input() staffNameControlName: string;

  /**
   * 必須
   */
  @Input() required: boolean;

  /**
   * 非表示/表示
   */
  @Input() isShowStatus = true;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ Ref
   */
  @ViewChild('holStaffSelectionPopup', null) holStaffSelectionPopup: EaPopup;
  @ViewChild('gridHolStaffSelectionList', null) gridHolStaffSelectionList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
    private messageService: MessageService
  ) {
    this.fb = fb;
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.searchForm = this.fb.group({
      sectionCode: [''],
    });
  }

  /**
   * データを取得する
   */
  init() {
    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe(
      (resp) => {
        this.holStaffList = resp;
        if (this.gridHolStaffSelectionList) {
          this.gridHolStaffSelectionList.resetSelection();
        }
      });
  }

  /**
   * 選択をクリア
   */
  clear() {
    this.holStaffForm.controls[this.staffCodeControlName].setValue(null);
    this.holStaffForm.controls[this.staffNameControlName].setValue(null);
    this.select.emit(null);
  }

  /**
   * リセット
   */
  reset() {
    this.holStaffList = [];
    this.searchForm.reset();
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.gridHolStaffSelectionList && this.gridHolStaffSelectionList.selectedRows.length > 0) {
      this.holStaffForm.controls[this.staffCodeControlName].setValue(this.gridHolStaffSelectionList.selectedRows[0].dataItem.code);
      this.holStaffForm.controls[this.staffNameControlName].setValue(this.gridHolStaffSelectionList.selectedRows[0].dataItem.value1);
      this.select.emit(this.gridHolStaffSelectionList.selectedRows[0].dataItem);
      this.close();
    } else {
      this.messageService.info(SystemMessage.Info.msg100008);
      this.close();
    }
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.reset();
    if (this.paramMap.sectionCode !== '') {
      this.init();
    }
    this.holStaffSelectionPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.holStaffSelectionPopup.hide();
  }

  /**
   * 探す
   */
  search() {
    const paramMap = {
      sectionCode: this.searchForm.controls.sectionCode.value,
      companyCode: this.sessionInfo.loginCompanyCode
    };

    this.masterService.searchLovList(this.sqlName, paramMap).subscribe(
      (resp) => {
        this.holStaffList = resp;
        if (this.gridHolStaffSelectionList) {
          this.gridHolStaffSelectionList.resetSelection();
        }
      });
  }
}

