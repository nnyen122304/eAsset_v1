import { Component, Output, ViewChild, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';

import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { MasterService } from 'src/app/services/api/master.service';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { DatePipe } from '@angular/common';


@Component({
  selector: 'app-project-selection',
  templateUrl: './project-selection.component.html',
  styleUrls: ['./project-selection.component.scss']
})
export class ProjectSelectionComponent {

  /**
   * ポップアップ呼出元エレメント
   */
  opener: Element;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * リクエスト送信用SQLQUERY
   */
  @Input() sqlName: string;

  /**
   * パラメータ
   */
  @Input() paramMap: object;

  /**
   * Stores list of master parents
   */
  dataList: LovDataEx[];

  /**
   * 検索フォーム
   */
  searchForm: FormGroup;

  /**
   * 社員検索用フォーム
   */
  @Input() projectForm: FormGroup;

  /**
   * 社員コードコントロール名
   */
  @Input() projectCodeControlName: string;

  /**
   * 社員名コントロール名
   */
  @Input() projectNameControlName: string;

  /**
   * 社員名コントロール名
   */
  @Input() projectTypeControlName: string = '';

  /**
   * 必須
   */
  @Input() required: boolean;

  /**
   * 案件グループ名スタイル
   */
  @Input() searchDateFrom: Date;

  /**
   * 案件グループ名スタイル
   */
  @Input() searchDateTo: Date;

  /**
   * ラベル 資産ﾌﾟﾛｼﾞｪｸﾄｺｰﾄﾞ
   */
  @Input() title = '資産ﾌﾟﾛｼﾞｪｸﾄｺｰﾄﾞ';

  /**
   * クラスを拡張する
   */
  @Input() extClass: string;

  /**
   * クラス名を拡張する
   */
  @Input() extClassName: string;

  /**
   * クラスタイプを拡張する
   */
  @Input() extClassType: string;

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
  @ViewChild('projectSelectionPopup', null) projectSelectionPopup: EaPopup;

  /**
   * グリッド Ref
   */
  @ViewChild('gridProjectSelectionList', null) gridProjectSelectionList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
    private messageService: MessageService,
    private datePipe: DatePipe
  ) {
    this.fb = fb;
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.searchForm = this.fb.group({
      projectName: [],
      typeName: []
    });
  }

  /**
   * 選択をクリア
   */
  clear() {
    this.dataList = [];
    this.searchForm.reset();
    this.projectForm.controls[this.projectCodeControlName].setValue(null);
    this.projectForm.controls[this.projectNameControlName].setValue(null);
    if (this.projectTypeControlName !== '') {
      this.projectForm.controls[this.projectTypeControlName].setValue(null);
    }

    this.select.emit(null);
  }

  /**
   * リセット
   */
  reset() {
    this.dataList = [];
    this.searchForm.reset();
  }

  /**
   * 社員情報取得
   */
  getProjectData() {
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const searchDateFrom = !this.searchDateFrom ? new Date() : this.searchDateFrom;
    const searchDateTo = !this.searchDateFrom ? new Date() : this.searchDateTo;
    const paramMap = {
      ...this.paramMap, ...{
        searchDateFrom: this.datePipe.transform(searchDateFrom, 'yyyy-MM-dd'),
        searchDateTo: this.datePipe.transform(searchDateTo, 'yyyy-MM-dd'),
        name: this.searchForm.controls.projectName.value,
        value1: this.searchForm.controls.typeName.value,
        companyCode: companyCode
      }
    };
    this.masterService.searchLovList(this.sqlName, paramMap).subscribe(
      (resp: LovDataEx[]) => {
        this.dataList = resp;
        if (this.gridProjectSelectionList) {
          this.gridProjectSelectionList.resetSelection();
        }
      });
  }

  /**
   * フォーカス時に社員情報取得
   */
  getProjectDataByCode() {
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const code = this.projectForm.controls[this.projectCodeControlName].value;
    if (code) {
      const paramMap = {
        ...this.paramMap, ...{
          code: code,
          companyCode: companyCode
        }
      };
      this.masterService.searchLovList(this.sqlName, paramMap).subscribe(
        (resp: LovDataEx[]) => {
          if (resp.length > 0) {
            this.projectForm.controls[this.projectCodeControlName].setValue(resp[0].code);
            this.projectForm.controls[this.projectNameControlName].setValue(resp[0].name);
            if (this.projectTypeControlName !== '') {
              this.projectForm.controls[this.projectTypeControlName].setValue(resp[0].value1);
            }

            this.select.emit(resp[0]);
          } else {
            this.projectForm.controls[this.projectNameControlName].setValue(null);
            this.projectForm.controls[this.projectTypeControlName].setValue(null);
            if (this.projectTypeControlName !== '') {
              this.projectForm.controls[this.projectTypeControlName].setValue(null);
            }
            this.select.emit(null);
          }
        });
    } else {
      this.projectForm.controls[this.projectNameControlName].setValue(null);
      this.projectForm.controls[this.projectTypeControlName].setValue(null);
      if (this.projectTypeControlName !== '') {
        this.projectForm.controls[this.projectTypeControlName].setValue(null);
      }
      this.select.emit(null);
    }
  }

  /**
   * 検索リクエスト
   */
  search() {
    this.getProjectData();
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.gridProjectSelectionList && this.gridProjectSelectionList.selectedRows.length) {
      this.select.emit(this.gridProjectSelectionList.selectedRows[0].dataItem);
      this.projectForm.controls[this.projectCodeControlName].setValue(this.gridProjectSelectionList.selectedRows[0].dataItem.code);
      this.projectForm.controls[this.projectNameControlName].setValue(this.gridProjectSelectionList.selectedRows[0].dataItem.name);
      if (this.projectTypeControlName) {
        this.projectForm.controls[this.projectTypeControlName].setValue(this.gridProjectSelectionList.selectedRows[0].dataItem.value1);
      }

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
    this.projectSelectionPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    this.projectSelectionPopup.hide();
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();

  }
}
