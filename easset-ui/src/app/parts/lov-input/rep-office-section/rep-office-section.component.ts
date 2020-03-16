import { Component, Output, ViewChild, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import { SessionService } from 'src/app/services/session.service';
import { MasterService } from 'src/app/services/api/master.service';
import { MessageService } from 'src/app/services/message.service';

import { SessionInfo } from 'src/app/models/session-info';

import { SystemMessage } from 'src/app/const/system-message';
import { EaInputMask } from 'src/app/components/ea-input-mask/ea-input-mask.component';

/**
 * 社員選択コンポネント
 */

@Component({
  selector: 'app-rep-office-section',
  templateUrl: './rep-office-section.component.html',
  styleUrls: ['./rep-office-section.component.scss']
})
export class RepOfficeSectionComponent {

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
  repOfficeList;

  /**
   * 退職を含む値
   */
  includeIntired: boolean;

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;
  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  @Input() exTract: string;

  /**
   * パラメータ
   */
  @Input() paramMap: object;
  /**
   * 社員検索用フォーム
   */
  @Input() repOfficeForm: FormGroup;
    /**
     * 選択のみ
     */
  @Input() selectOnly = false;
  /**
   * 社員コードコントロール名
   */
  @Input() repOfficeCodeControlName: string;

  /**
   * 社員名コントロール名
   */
  @Input() repOfficeNameControlName: string;
  /**
   * 必須
   */
  @Input() required: boolean;
   /**
    * 社員コードコントロール名のみを表示
    */
  @Input() onlyRepOfficeCode = false;
  @Input() isShowStatus = true;

  /**
   * 選択時イベント
   */
  @Output() selectData: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ Ref
   */
  @ViewChild('repOfficeSelectionPopup', null) repOfficeSelectionPopup: EaPopup;

  /**
   * グリッド Ref
   */
  @ViewChild('gridRepOfficeSelectionList', null) gridRepOfficeSelectionList: EaFlexGrid;

  /**
   * 社員コード入力 Ref
   */
  @ViewChild('repOfficeCode', null) inputMask: EaInputMask;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
    private messageService: MessageService
  ) {
    this.fb = fb;
    this.includeIntired = true;
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.searchForm = this.fb.group({
      repOfficeName: [],
    });
  }

  init() {
    this.paramMap = {...this.paramMap, companyCode: this.sessionInfo.loginUser.companyCode };
    if (this.searchForm.controls.repOfficeName.value !== ''){
      this.paramMap = {...this.paramMap, name: this.searchForm.controls.repOfficeName.value };
    }
    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe(
      (resp) => {
        console.log(this.paramMap);
        this.repOfficeList = resp;
        if (this.gridRepOfficeSelectionList) {
          this.gridRepOfficeSelectionList.resetSelection();
        }
      });
  }

  /**
   * 選択をクリア
   */
  clear() {
    this.repOfficeList = [];
    this.searchForm.reset();
    this.selectData.emit(null);
  }

  /**
   * リセット
   */
  reset() {
    this.repOfficeList = [];
    this.searchForm.reset();
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.gridRepOfficeSelectionList && this.gridRepOfficeSelectionList.selectedRows.length) {
      this.repOfficeForm.controls[this.repOfficeCodeControlName].setValue(this.gridRepOfficeSelectionList.selectedRows[0].dataItem.code);
      this.repOfficeForm.controls[this.repOfficeNameControlName].setValue(this.gridRepOfficeSelectionList.selectedRows[0].dataItem.name);
      this.selectData.emit(this.gridRepOfficeSelectionList.selectedRows[0].dataItem);
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
    // this.reset();
    this.init();
    this.repOfficeSelectionPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.repOfficeSelectionPopup.hide();
  }
}
