import { Component, Output, ViewChild, EventEmitter, Input, AfterViewInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import { SessionService } from 'src/app/services/session.service';
import { MasterService } from 'src/app/services/api/master.service';
import { MessageService } from 'src/app/services/message.service';

import { SessionInfo } from 'src/app/models/session-info';
import { User } from 'src/app/models/api/user';

import { SystemMessage } from 'src/app/const/system-message';
import { EaInputMask } from 'src/app/components/ea-input-mask/ea-input-mask.component';

/**
 * 社員選択コンポネント
 */

@Component({
  selector: 'app-staff-selection',
  templateUrl: './staff-selection.component.html',
  styleUrls: ['./staff-selection.component.scss']
})
export class StaffSelectionComponent implements AfterViewInit {

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
  staffList: User[];

  /**
   * 退職を含む値
   */
  includeIntired: boolean;

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;

  /**
   * 社員検索用フォーム
   */
  @Input() staffForm: FormGroup;

  /**
   * 有効データのみ
   */
  @Input() usableDataOnly = false;

  /**
   * 会社内のみ
   */
  @Input() companyOnly = false;

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
   * 題名
   */
  @Input() title = '社員番号';

  /**
   * クラスを拡張する
   */
  @Input() extClass = '';

  /**
   * クラスを拡張する
   */
  @Input() extNameClass = '';

  /**
   * 社員コードコントロール名のみを表示
   */
  @Input() onlyStaffCode = false;

  /**
   * 状態 -1:編集ロック、0:状態設定なし、1～：各エンティティの項目定義に従いセット
   */
  @Input() visibleStatus = true;

  /**
   * 項目読取専用設定
   */
  @Input() readOnly = false;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();
  /**
   * ポップアップ Ref
   */
  @ViewChild('staffSelectionPopup', null) staffSelectionPopup: EaPopup;

  /**
   * グリッド Ref
   */
  @ViewChild('gridStaffSelectionList', null) gridStaffSelectionList: EaFlexGrid;

  /**
   * 社員コード入力 Ref
   */
  @ViewChild('staffCode', null) inputMask: EaInputMask;

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
      staffName: [],
      sectionName: []
    });
  }

  ngAfterViewInit() {
    // this.inputMask.setFormControl(this.staffForm.controls[this.staffCodeControlName]);
  }

  /**
   * 選択をクリア
   */
  clear() {
    this.staffList = [];
    this.searchForm.reset();
    this.staffForm.controls[this.staffCodeControlName].setValue(null);
    this.staffForm.controls[this.staffNameControlName].setValue(null);
    this.select.emit(null);
  }

  /**
   * リセット
   */
  reset() {
    this.staffList = [];
    this.searchForm.reset();
  }

  /**
   * 文字入力後処理
   * @param event キーボードイベント
   */
  onInput(event: KeyboardEvent) {
    if (event.keyCode === 13 || this.staffForm.controls[this.staffCodeControlName].value.length === 0) {
      this.getStaffData();
    }
  }

  /**
   * 社員情報取得
   */
  getStaffData() {
    setTimeout(() => {
      const value = this.staffForm.controls[this.staffCodeControlName].value;
      if (value.length >= 1) {
        const companyCode: string = (this.companyOnly) ? this.sessionInfo.loginCompanyCode : null;
        this.masterService.getStaff(companyCode, value)
          .subscribe(
            (resp: User) => {
              if (resp) {
                this.staffForm.controls[this.staffNameControlName].setValue(resp.name);
                this.staffForm.controls[this.staffCodeControlName].setValue(value);
                this.select.emit(resp);
              } else {
                this.staffForm.controls[this.staffNameControlName].setValue(null);
                this.select.emit(null);
              }
            }
          );
      } else {
        this.staffForm.controls[this.staffNameControlName].setValue(null);
        this.select.emit(null);
      }
    });
  }

  /**
   * フォーカス時に社員情報取得
   */
  getStaffDataOnFocus() {
    setTimeout(() => {
      const value = this.staffForm.controls[this.staffNameControlName].value;
      if (!value) {
        this.getStaffData();
      }
    }, 100);
  }

  /**
   * 検索リクエスト
   */
  search() {
    const companyCode: string = (this.companyOnly) ? this.sessionInfo.loginCompanyCode : null;
    const staffName: string = (this.searchForm.controls.staffName.value) ? this.searchForm.controls.staffName.value : '';
    const sectionName: string = (this.searchForm.controls.sectionName.value) ? this.searchForm.controls.sectionName.value : '';
    const enableStaffOnly = (this.usableDataOnly) ? true : !this.includeIntired;
    this.masterService.searchStaff(companyCode, staffName, sectionName, enableStaffOnly)
      .subscribe(
        (resp: User[]) => {
          this.staffList = resp;
          this.gridStaffSelectionList.resetSelection();
        }
      );

  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.gridStaffSelectionList && this.gridStaffSelectionList.selectedRows.length) {
      this.staffForm.controls[this.staffCodeControlName].setValue(this.gridStaffSelectionList.selectedRows[0].dataItem.staffCode);
      this.staffForm.controls[this.staffNameControlName].setValue(this.gridStaffSelectionList.selectedRows[0].dataItem.name);
      this.select.emit(this.gridStaffSelectionList.selectedRows[0].dataItem);
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
    this.reset();
    this.staffSelectionPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.staffSelectionPopup.hide();
  }


}
