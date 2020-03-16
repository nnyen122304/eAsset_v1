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
  selector: 'app-reo-ast',
  templateUrl: './reo-ast.component.html',
  styleUrls: ['./reo-ast.component.scss']
})
export class ReoAstComponent {

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
  reoAstList;

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
  @Input() reoAstForm: FormGroup;

  /**
   * 選択のみ
   */
  @Input() selectOnly: boolean;

  /**
   * 社員コードコントロール名
   */
  @Input() reoAstCodeControlName: string;

  /**
   * 社員名コントロール名
   */
  @Input() reoAstNameControlName: string;

  /**
   * 必須
   */
  @Input() required: boolean;

  /**
   * 社員コードコントロール名のみを表示
   */
  @Input() isShowStatus = true;

  /**
   * 親カテゴリコード
   */
  @Input() parentCategoryCode: string;

  /**
   * 親識別コード
   */
  @Input() parentInternalCode: string;

  /**
   * メーカー名(あいまい)
   */
  @Input() makerName: string;

  /**
   * 資産(機器)名(あいまい)
   */
  @Input() astName: string;

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  /**
   * ポップアップ Ref
   */
  @ViewChild('reoAstPopup', null) reoAstPopup: EaPopup;

  /**
   * グリッド Ref
   */
  @ViewChild('gridReoAstList', null) gridReoAstList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
    private messageService: MessageService
  ) {
    this.fb = fb;
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.searchForm = this.fb.group({
      searchParamMakerName: [''],
      searchParamAstName: ['']
    });
  }

  /**
   * Init
   */
  init() {
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const makerName: string = this.searchForm.controls.searchParamMakerName.value;
    const astName: string = this.searchForm.controls.searchParamAstName.value;
    const sysdate = new Date();

    this.masterService.sarchAstName(this.parentCategoryCode, this.parentInternalCode, companyCode, sysdate, makerName, astName).subscribe(
      (resp) => {
        this.reoAstList = resp;
        if (this.gridReoAstList) {
          this.gridReoAstList.resetSelection();
        }
      });
  }

  /**
   * 選択をクリア
   */
  clear() {
    this.reoAstList = [];
    this.searchForm.reset();
    this.select.emit(null);
  }

  /**
   * リセット
   */
  reset() {
    this.reoAstList = [];
    this.searchForm.reset();
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (this.gridReoAstList && this.gridReoAstList.selectedRows.length) {
      this.reoAstForm.controls[this.reoAstNameControlName].setValue(this.gridReoAstList.selectedRows[0].dataItem.value2);
      this.select.emit(this.gridReoAstList.selectedRows[0].dataItem);
      this.close();
    } else {
      this.messageService.info(SystemMessage.Warn.msg200037);
    }
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.init();
    this.reoAstPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
    this.reoAstPopup.hide();
  }
}
