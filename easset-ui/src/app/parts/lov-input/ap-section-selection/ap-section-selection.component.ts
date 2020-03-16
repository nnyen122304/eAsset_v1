import { Component, Input, Output, ViewChild, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { WjTreeView } from 'wijmo/wijmo.angular2.nav';
import { SessionService } from 'src/app/services/session.service';
import { MasterService } from 'src/app/services/api/master.service';
import { SessionInfo } from 'src/app/models/session-info';
import { EaPopup } from 'src/app/components/ea-popup/ea-popup.component';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';

@Component({
  selector: 'app-ap-section-selection',
  templateUrl: './ap-section-selection.component.html',
  styleUrls: ['./ap-section-selection.component.scss']
})
export class ApSectionSelectionComponent {
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
   * 検索フォーム
   */
  searchForm: FormGroup;

  /**
   * Stores list of master parents
   */
  dataList: LovDataEx[];

  /**
   * 選択中部署
   */
  selectedItem: WjTreeView['selectedItem'];

  /**
   * 部署フォーム
   */
  @Input() sectionForm: FormGroup;

  /**
   * 申請部署コード
   */
  @Input() apSectionCodeControlName: string;

  /**
   * 申請部署名
   */
  @Input() apSectionNameControlName: string;

  /**
   * 必須か判定用
   */
  @Input() required: boolean;

  /**
   * 選択のみ
   */
  @Input() selectOnly: boolean;

  /**
   * ラベル申請部署
   */
  @Input() title = '申請部署';

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
  @ViewChild('apPopup', null) apPopup: EaPopup;

  /**
   * グリッド Ref
   */
  @ViewChild('gridSelectionList', null) gridSelectionList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private masterService: MasterService,
    private messageService: MessageService
  ) {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.fb = fb;
    this.searchForm = this.fb.group({
      filterText: ['']
    });
  }

  /**
   * 初期処理
   */
  init() {
    this.selectedItem = {};
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    this.paramMap = {...this.paramMap, ...{companyCode: companyCode}};

    this.masterService.searchLovList(this.sqlName, this.paramMap).subscribe(
      (resp: LovDataEx[]) => {
        this.dataList = resp;
        if (this.gridSelectionList) {
          this.gridSelectionList.resetSelection();
        }
      });
  }

  /**
   * Flexグリッド初期処理
   */
  onGridInitialized() {
    this.gridSelectionList.resetSelection();
  }

  /**
   * ツリーをクリア
   */
  clear() {
    this.selectedItem = {};
    this.sectionForm.controls[this.apSectionCodeControlName].setValue(null);
    this.sectionForm.controls[this.apSectionNameControlName].setValue(null);
    this.select.emit(null);
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit() {
    if (!this.gridSelectionList.selectedRows[0]) {
      this.messageService.info(SystemMessage.Info.msg100008);
      return;
    }

    this.selectedItem = this.gridSelectionList.selectedRows[0].dataItem;
    this.sectionForm.controls[this.apSectionCodeControlName].setValue(this.selectedItem.code);
    this.sectionForm.controls[this.apSectionNameControlName].setValue(this.selectedItem.name);
    this.select.emit(this.selectedItem);
    this.close();
  }

  /**
   * ポップアップを開く
   */
  open(opener: Element) {
    this.opener = opener;
    this.init();
    this.apPopup.show(true);
  }

  /**
   * ポップアップを閉じる
   */
  close() {
    this.apPopup.hide();
    this.dataList = [];
    // Reset treeForm
    this.searchForm = this.fb.group({
      filterText: ['']
    });
    // tslint:disable-next-line: no-any
    const openerObj: any = this.opener;
    openerObj.focus();
  }
}
