import { Component, OnInit, ViewChild } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';
import * as wjGrid from 'wijmo/wijmo.grid';
import { sprintf } from 'sprintf-js';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { InventoryComponent } from '../inventory.component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { EaFlexGridColumn } from 'src/app/components/ea-flex-grid/ea-flex-column.component';

import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { InvService } from 'src/app/services/api/inv.service';
import { MasterService } from 'src/app/services/api/master.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';

import { SessionInfo } from 'src/app/models/session-info';
import { CodeName } from 'src/app/models/api/code-name';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { InvStat } from 'src/app/models/api/inv/inv-stat';
import { InvSumSR } from 'src/app/models/api/inv/inv-sum-sr';
import { InvSumSC } from 'src/app/models/api/inv/inv-sum-sc';
import { Section } from 'src/app/models/api/section';

import { SystemMessage } from 'src/app/const/system-message';
import { DeepCopy } from 'src/app/utils/deep-copy';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';

/**
 * 棚卸一覧コンポネント
 */

@Component({
  selector: 'app-list',
  templateUrl: './list.component.html',
  styleUrls: ['./list.component.scss']
})
export class ListComponent extends AbstractChildComponent<InventoryComponent> implements OnInit {

  /**
   * 検索条件フォーム
   */
  searchForm: FormGroup;

  /**
   * 部署フォーム
   */
  sectionForm: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 表示するビューの判定用値
   */
  view: string;

  /**
   * 会計年月一覧データ
   */
  accountingDates: InvStat[];

  /**
   * 棚卸一覧データ（UI側表示編集用）
   */
  inventoryList: InvSumSR[];

  /**
   * 検索条件
   */
  selectedSearchScopeType: string;

  /**
   * 検索リクエスト用履歴フラグ
   */
  histPeriodFlag: boolean;

  /**
   * 検索する会計年月
   */
  searchPeriod: string;

  /**
   * 検索する部署コード
   */
  searchSectionCode: string;

  /**
   * 検索する資産名
   */
  searchAsset: string;

  /**
   * 検索リクエスト用条件パラメータ
   */
  searchParam: InvSumSC;

  /**
   * 完了報告用棚卸一覧
   */
  listToComplete: InvSumSC[];

  /**
   * 実施ステータス一覧
   */
  invImplementation: string[] = ['未実施', '実施中', '実施済'];

  /**
   * 申請ステータス一覧
   */
  apStatus: string[] = ['未申請', '申請中', '承認済'];

  /**
   * 資産カテゴリ一覧
   */
  invAssetType: string[] = ['有形固定資産', '資産除去費用', '無形固定資産(本勘定)', '無形固定資産(仮勘定)', 'リース資産', 'レンタル資産', '備品等'];

  /**
   * 完了報告ボタン許可
   */
  allowComplete: boolean;

  /**
   * メール送信ボタン許可
   */
  allowSendMail: boolean;

  /**
   * 資産ラベル
   */
  assetLabel: string;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('confirmPopup', null) confirmPopup: ConfirmPopupComponent;

  /**
   * 棚卸一覧グリッド Ref
   */
  @ViewChild('gridInventoryList', null) gridInventoryList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private sessionService: SessionService,
    private messageService: MessageService,
    private invService: InvService,
    private masterService: MasterService,
    private fileDownloadService: FileDownloadService
  ) {
    super();
    this.fb = fb;
    this.sectionForm = this.fb.group({
      sectionCode: [''],
      sectionName: [''],
      sectionYear: ['']
    });
    this.searchForm = this.fb.group({
      period: [''],
      searchScopeType: '1',
      includeSectionHierarchyFlag: false,
      invImplementation: this.fb.array(this.invImplementation.map((x) => false)),
      apStatus: this.fb.array(this.apStatus.map((x) => false)),
      invAssetType: this.fb.array(this.invAssetType.map((x) => false)),
    });
  }

  /**
   * 画面の読み込み
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        this.init();
      } else if (param.action === 'backFromAssetList') {
        if (param.params.updatedRows) {
          const updatedRow = param.params.updatedRows[0];
          Object.assign(updatedRow, {sel: false});
          const rowIndex = this.gridInventoryList.selectedRows[0].index;
          this.inventoryList[rowIndex] = updatedRow;
          this.gridInventoryList.collectionView.refresh();
        }
      }
    });
  }

  /**
   * 初期処理
   */
  init() {

    switch (this.sessionInfo.currentAccessLevel) {
      case 'S':
        this.view = 'admin';
        break;
      case 'C':
        this.view = 'general';
        break;
      case 'B':
        this.view = 'asset_manager';
        break;
      default:
        this.view = 'category';
        break;
    }

    const companyCode = this.sessionInfo.loginCompanyCode;

    this.invService.searchInvStat(companyCode).subscribe(
      (data: InvStat[]) => {

        if (this.sessionInfo.currentAccessLevel === 'S' || this.sessionInfo.currentAccessLevel === 'A') {
          this.accountingDates = data
          .map((obj: InvStat) => {
            obj.periodName = obj.period.slice(0, 4) + '-' + obj.period.slice(-2);
            return obj;
          });
        } else {
          this.accountingDates = data
          .filter((obj: InvStat) => {
            return (
              obj.fldTanPublicType === '2' ||
              obj.fldRoPublicType === '2' ||
              obj.fldIntPublicType === '2' ||
              obj.lePublicType === '2' ||
              obj.rePublicType === '2' ||
              obj.equipPublicType === '2'
            );
          })
          .map((obj: InvStat) => {
            obj.periodName = obj.period.slice(0, 4) + '-' + obj.period.slice(-2);
            return obj;
          });
        }

        if (this.accountingDates.length) {
          this.searchForm.controls.period.setValue(this.accountingDates[0].period);
          this.search();
        }
      }
    );

  }

  /**
   * 部署選択後処理
   * @param data 選択部署
   */
  selectSection(data: Section) {
  }

  /**
   * 棚卸ステータス切り替え
   */
  toggleInvStatus(e: MouseEvent) {
    const target = e.target as HTMLInputElement;
    if (target.id === 'item-list-implementation-2') {
      if (target.checked) {
        this.searchForm.controls.apStatus.setValue([true, true, true]);
      } else {
        this.searchForm.controls.apStatus.setValue([false, false, false]);
      }
    }
  }

  /**
   * 検索リクエスト、処理
   */
  search() {

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;

    let searchScopeType: string = this.searchForm.controls.searchScopeType.value;
    if (!searchScopeType || this.view !== 'general' && this.view !== 'asset_manager') {
      searchScopeType = '2';
    }

    this.selectedSearchScopeType = searchScopeType;

    this.searchParam = {
      companyCode,
      searchScopeType,
      period: this.searchForm.controls.period.value,
      invImplementation: this.searchForm.controls.invImplementation.value
        .map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' '),
      apStatus: this.searchForm.controls.apStatus.value
        .map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ') + (this.searchForm.controls.apStatus.value[0] === true ? ' 4' : ''),
      holSectionYear: this.sectionForm.controls.sectionYear.value,
      holSectionCode: this.sectionForm.controls.sectionCode.value,
      holSectionName: this.sectionForm.controls.sectionName.value,
      includeSectionHierarchyFlag: this.searchForm.controls.includeSectionHierarchyFlag.value ? '1' : '',
      invAssetType: this.searchForm.controls.invAssetType.value
        .map((x: boolean, i: number) => x ? ++i : null).filter((x) => x !== null).join(' ')
    };

    this.invService.getInvHistPeriodFlag(companyCode, this.searchParam.period)
    .subscribe(
      (resp: NonObjectResponse<string>) => {
        this.histPeriodFlag = JSON.parse(resp.value);
        switch (this.view) {
          case 'admin': // S: ADMIN
            if (!this.histPeriodFlag) {
              this.allowComplete = true;
              this.allowSendMail = true;
              this.assetLabel = '棚卸実施/確認';
            } else {
              this.allowComplete = false;
              this.allowSendMail = false;
              this.assetLabel = '詳細確認';
            }
            break;
          case 'general': // C: 一般
            this.assetLabel = '詳細確認';
            if (!this.histPeriodFlag) {
              this.allowComplete = false;
              this.allowSendMail = false;
              if (this.searchParam.searchScopeType === '1') {
                this.assetLabel = '棚卸実施/確認';
              }
            } else {
              this.allowComplete = false;
              this.allowSendMail = false;
            }
            break;
          case 'asset_manager': // B: 資産管理担当者
            this.assetLabel = '詳細確認';
            if (!this.histPeriodFlag) {
              if (this.searchParam.searchScopeType === '1') {
                this.allowComplete = true;
                this.allowSendMail = false;
                this.assetLabel = '棚卸実施/確認';
              } else {
                this.allowComplete = false;
                this.allowSendMail = false;
              }
            } else {
              this.allowComplete = false;
              this.allowSendMail = false;
            }
            break;
          default: // general - A: 資産管理者
            if (!this.histPeriodFlag) {
              this.allowComplete = true;
              this.allowSendMail = true;
              this.assetLabel = '棚卸実施/確認';
            } else {
              this.allowComplete = false;
              this.allowSendMail = false;
              this.assetLabel = '詳細確認';
            }
            break;
        }
      }
    );

    this.invService.searchInvSum(loginStaffCode, accessLevel, companyCode, this.searchParam)
    .subscribe(
      (data: InvSumSR[]) => {

        if (!data.length) {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }

        this.inventoryList = data.map((obj: InvSumSR) => {
          obj.sel = false;
          return obj;
        });

        this.searchPeriod = this.searchParam.period;
        this.searchSectionCode = this.searchParam.holSectionCode;
        this.searchAsset = this.searchForm.controls.invAssetType.value
          .map((x: boolean, i: number) => x ? this.invAssetType[i] : null)
          .filter((x) => x !== null)
          .join('_');

        this.gridInventoryList.resetSelection();
      }
    );

  }

  /**
   * 検索処理
   */
  research() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    this.invService.searchInvSum(loginStaffCode, accessLevel, companyCode, this.searchParam)
    .subscribe(
      (data: InvSumSR[]) => {
        if (!data.length) {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }
        this.inventoryList = data.map((obj: InvSumSR) => {
          obj.sel = false;
          return obj;
        });
        this.gridInventoryList.resetSelection();
      }
    );
  }

  /**
   * グリッド初期処理
   * @param grid Flexグリッド
   */
  onGridInitialized(grid: EaFlexGrid) {

    const extraRow = new wjGrid.Row();
    extraRow.allowMerging = true;
    const panel = grid.columnHeaders;
    panel.rows.splice(0, 0, extraRow);

    for (let colIndex = 4; colIndex <= 7; colIndex++) {
      panel.setCellData(0, colIndex, '棚卸状況(現物件数)');
    }

    const mergableCols = [0, 1, 2, 3, 8, 9, 10, 11, 12, 13, 14];
    grid.columns.forEach((col: EaFlexGridColumn) => {
      if (mergableCols.indexOf(col.index) >= 0) {
        col.allowMerging = true;
        panel.setCellData(0, col.index, col.header);
      }
    });

    this.gridInventoryList.columnFooters.rows.push(new wjGrid.GroupRow());
    this.gridInventoryList.bottomLeftCells.setCellData(0, 0, '');

    grid.frozenColumns = 4;

    grid.hostElement.addEventListener('click', (e: MouseEvent) => {

      if (!grid.hitTest(e).panel) {
        return;
      }

      if (grid.hitTest(e).panel.columns[grid.hitTest(e).col].header === '操作' && wjGrid.CellType[grid.hitTest(e).cellType] === 'Cell') {
        this.showDetails(grid.hitTest(e).row);
      }

    });

  }

  /**
   * CSVダウンロード
   */
  download() {

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode: string = this.sessionInfo.loginCompanyCode;

    let searchScopeType: string = this.searchForm.controls.searchScopeType.value;
    if (!searchScopeType || this.view !== 'general' && this.view !== 'asset_manager') {
      searchScopeType = '2';
    }

    this.invService.createInvSumCsv(loginStaffCode, accessLevel, companyCode, this.searchParam)
    .subscribe(
      (resp: NonObjectResponse<string>) => {
        const fileID = resp.value;
        const fileName = '棚卸実施状況_' + this.searchPeriod + '_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
        this.fileDownloadService.download(fileID, fileName, 'text/csv');
      }
    );
  }

  /**
   * 棚卸完了報告処理
   */
  inventoryComplete() {

    // 選択チェック
    this.listToComplete = this.gridInventoryList.getCheckedRows();

    if (this.listToComplete.length === 0) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    }

    // 保有部署/資産不明チェック
    const ownedDepartment = this.listToComplete.filter((obj: InvSumSR) => {
      return obj.holSectionCode === 'Z1' || obj.holSectionCode === 'Z2';
    });
    if (ownedDepartment.length) {
      const sections = ownedDepartment.splice(0, 1).map((obj: InvSumSR) => '「' + obj.holSectionName + '」').toString();
      this.messageService.err(sprintf(SystemMessage.Err.msg300009, sections));
      return;
    }

    // 棚卸未実施チェック
    const retainedList = this.listToComplete.filter((obj: InvSumSR) => {
      return obj.invCountUndecided > 0;
    });
    if (retainedList.length) {
      const sections = retainedList.splice(0, 100).map((obj: InvSumSR) => '「' + obj.holSectionName + '」').toString();
      this.messageService.err(sprintf(SystemMessage.Err.msg300008, sections));
      return;
    }

    // 管轄部署チェック
    const jurisdictionDepartment = this.listToComplete.filter((obj: InvSumSR) => {
      return obj.invStaffFlag === '1';
    });
    if (jurisdictionDepartment.length) {
      this.messageService.err(SystemMessage.Err.msg300007);
      return;
    }

    this.confirmPopup.prompt(SystemMessage.Conf.msg400002, document.activeElement);
  }

  /**
   * 棚卸完了報告リクエスト送信
   */
  confirmCompleteSend() {

    this.confirmPopup.close();

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    this.invService.reportInvSum(loginStaffCode, accessLevel, this.listToComplete)
    .subscribe(
      (resp: InvSumSR[]) => {
        this.research();
        this.messageService.info(SystemMessage.Info.msg100015);
      }
    );

  }

  /**
   * 督促メール送信
   */
  sendReminderMail() {

    const list: InvSumSR[] = this.gridInventoryList.getCheckedRows();
    if (list.length === 0) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    }

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;

    // 保有部署/資産不明チェック
    const ownedDepartment = list.filter((obj: InvSumSR) => {
      return obj.holSectionCode === 'Z1' || obj.holSectionCode === 'Z2';
    });
    if (ownedDepartment.length) {
      const sections = ownedDepartment.splice(0, 1).map((obj: InvSumSR) => '「' + obj.holSectionName + '」').toString();
      this.messageService.err(sprintf(SystemMessage.Err.msg300010, sections));
      return;
    }

    this.invService.remindInv(loginStaffCode, accessLevel, list)
    .subscribe(
      () => {
        this.messageService.info(SystemMessage.Info.msg100014);
      }
    );
  }

  /**
   * 詳細CSVダウンロード
   */
  downloadDetails() {

    const list: InvSumSR[] = this.gridInventoryList.getCheckedRows();

    if (list.length === 0) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    }

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    let searchScopeType: string = this.searchForm.controls.searchScopeType.value;
    if (!searchScopeType || this.view !== 'general' && this.view !== 'asset_manager') {
      searchScopeType = '2';
    }

    // 資産区分チェック
    if (list.length !== list.filter(item => item.invAssetType === list[0].invAssetType).length) {
      this.messageService.err(SystemMessage.Err.msg300011);
      return;
    }

    // 保有部署/資産不明チェック
    // 選択された行に、保有部署不明（部署コード:Z1）もしくは資産情報不明（部署コード:Z2）のデータが、他の保有部署のデータと混在していた場合エラー
    // ※　保有部署不明、資産情報不明をそれぞれ単独でダウンロードは可
    const z1Length = list.filter((obj) => obj.holSectionCode === 'Z1').length;
    const z2Length = list.filter((obj) => obj.holSectionCode === 'Z2').length;
    if ((z1Length && z1Length !== list.length) || (z2Length && z2Length !== list.length)) {
      this.messageService.err(SystemMessage.Err.msg300012);
      return;
    }

    this.invService.createInvLineListCsv(loginStaffCode, accessLevel, list, searchScopeType)
    .subscribe(
      (resp: NonObjectResponse<string>) => {
        const fileID = resp.value;
        const assetSection = list[0].invAssetTypeName;
        const fileName = '棚卸明細_' + (assetSection ? assetSection + '_' : '') + this.searchPeriod + '_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
        this.fileDownloadService.download(fileID, fileName, 'text/csv');
      }
    );
  }

  /**
   * PDF実査表ダウンロード
   */
  printFactSheet() {

    const list: InvSumSR[] = this.gridInventoryList.getCheckedRows();
    if (list.length === 0) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    }

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;

    // 資産区分チェック
    // ※　ただし無形固定資産(仮勘定)と無形固定資産(本勘定)は同一資産区分と判定
    const printList = DeepCopy(list);
    const printArr = printList.map((obj: InvSumSR) => {
      if (obj.invAssetType === '3' || obj.invAssetType === '4') {
        obj.invAssetType = 'intangible_assets';
      }
      return obj;
    });
    if (printArr.length !== printArr.filter(item => item.invAssetType === printArr[0].invAssetType).length) {
      this.messageService.err(SystemMessage.Err.msg300011);
      return;
    }

    // 保有部署/資産不明チェック
    const ownedDepartment = list.filter((obj: InvSumSR) => {
      return obj.holSectionCode === 'Z1' || obj.holSectionCode === 'Z2';
    });
    if (ownedDepartment.length) {
      const sections = ownedDepartment.splice(0, 100).map((obj: InvSumSR) => '「' + obj.holSectionName + '」').toString();
      this.messageService.err(sprintf(SystemMessage.Err.msg300013, sections));
      return;
    }

    // 資産区分チェック
    const rentalEquipList = list.filter((obj: InvSumSR) => {
      return obj.invAssetType === '6' || obj.invAssetType === '7';
    });
    if (rentalEquipList.length) {
      const asset = '「' + rentalEquipList[0].invAssetTypeName + '」';
      this.messageService.err(sprintf(SystemMessage.Err.msg300014, asset));
      return;
    }

    this.invService.createInvPdf(loginStaffCode, accessLevel, list)
    .subscribe(
      (resp: NonObjectResponse<string>) => {
        const fileID = resp.value;
        this.fileDownloadService.preview(fileID, 'application/pdf');
      }
    );
  }

  /**
   * 詳細を表示
   * @param rowID Selected row ID
   */
  showDetails(rowID: number) {
    const data = this.inventoryList[rowID];
    let categoryCode: string;
    switch (data.invAssetTypeName) {
      case '有形固定資産':
        categoryCode = 'AP_ATTENTION_INV_FLD_TAN';
        break;
      case '資産除去費用':
        categoryCode = 'AP_ATTENTION_INV_FLD_RO';
        break;
      case '無形固定資産(本勘定)':
        categoryCode = 'AP_ATTENTION_INV_FLD_INT';
        break;
      case '無形固定資産(仮勘定)':
        categoryCode = 'AP_ATTENTION_INV_FLD_INT_S';
        break;
      case 'リース資産':
        categoryCode = 'AP_ATTENTION_INV_LE';
        break;
      case 'レンタル資産':
        categoryCode = 'AP_ATTENTION_INV_RE';
        break;
      case '備品等':
        categoryCode = 'AP_ATTENTION_INV_EQUIP';
        break;
    }
    const companyCode = this.sessionInfo.loginCompanyCode;
    this.masterService.getCodeNameList(categoryCode, null, companyCode)
    .subscribe(
      (resp: CodeName[]) => {
        this.parent.changeChild(this.parent.viewIndexListAsset, {
          action: 'initAsset',
          params: {item: data,
          codeNameList: resp,
          categoryCode,
          searchScopeType: this.selectedSearchScopeType,
          histPeriodFlag: this.histPeriodFlag,
          rowID,
          view: this.view,
          searchParam: this.searchParam,
          searchAsset: this.searchAsset}
        });
      }
    );
  }

  /**
   * 会計年度取得
   * @param period 年月(YYYYMM)
   */
  getFiscalYear(period: string): number {
    return Number(period.slice(0, 4)) - ((period.slice(-2) <= '03') ? 1 : 0);
  }

}
