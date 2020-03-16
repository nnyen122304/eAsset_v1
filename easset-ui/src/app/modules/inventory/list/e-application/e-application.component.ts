import { Component, OnInit, ViewChild } from '@angular/core';
import { DatePipe } from '@angular/common';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { InventoryComponent } from '../../inventory.component';

import * as wjGrid from 'wijmo/wijmo.grid';
import { sprintf } from 'sprintf-js';

import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { EaFlexGridColumn } from 'src/app/components/ea-flex-grid/ea-flex-column.component';

import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { SiteAccessService } from 'src/app/services/site-access.service';
import { MasterService } from 'src/app/services/api/master.service';
import { InvService } from 'src/app/services/api/inv.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';

import { SessionInfo } from 'src/app/models/session-info';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { InvSumSR } from 'src/app/models/api/inv/inv-sum-sr';
import { DeepCopy } from 'src/app/utils/deep-copy';

import { SystemConst } from 'src/app/const/system-const';
import { SystemMessage } from 'src/app/const/system-message';

/**
 * e申請コンポネント
 */

@Component({
  selector: 'app-e-application',
  templateUrl: './e-application.component.html',
  styleUrls: ['./e-application.component.scss']
})
export class EApplicationComponent extends AbstractChildComponent<InventoryComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * e申請棚卸一覧（画面表示用）
   */
  applicationInventoryList: InvSumSR[];

  /**
   * 検索する資産名
   */
  searchAsset: string;

  /**
   * 検索リクエスト用検索条件パラメータ
   */
  searchParam: {period: string};

  /**
   * 検索条件
   */
  searchScopeType: string;

  /**
   * 現在e申請ID
   */
  eappId = 0;

  @ViewChild('gridApplicationList', null) gridApplicationList: EaFlexGrid;

  constructor(
    private datePipe: DatePipe,
    private sessionService: SessionService,
    private messageService: MessageService,
    private siteAccessService: SiteAccessService,
    private invService: InvService,
    private fileDownloadService: FileDownloadService
  ) { super(); }

  /**
   * 画面読み込み
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        this.eappId = parseInt(param.params.params.param2, 10);
        this.searchScopeType = '1';
        this.searchParam = param.params.searchParam;
        this.searchAsset = param.params.searchAsset;
        this.init();
      }
    });
  }

  /**
   * 初期処理
   */
  init() {

    this.invService.searchInvSumEapp(this.eappId)
    .subscribe(
      (data: InvSumSR[]) => {
        this.applicationInventoryList = data.map((obj: InvSumSR) => {
          obj.sel = false;
          return obj;
        });
      }
    );

  }

  /**
   * グリッド初期処理
   * @param grid Flexグリッド
   */
  onGridInitialized(grid: EaFlexGrid) {

    grid.resetSelection();

    const extraRow = new wjGrid.Row();
    extraRow.allowMerging = true;
    const panel = grid.columnHeaders;
    panel.rows.splice(0, 0, extraRow);

    for (let colIndex = 4; colIndex <= 7; colIndex++) {
      panel.setCellData(0, colIndex, '棚卸状況(現物件数)');
    }

    const mergableCols = [0, 1, 2, 3, 8];
    grid.columns.forEach((col: EaFlexGridColumn) => {
      if (mergableCols.indexOf(col.index) >= 0) {
        col.allowMerging = true;
        panel.setCellData(0, col.index, col.header);
      }
    });

    this.gridApplicationList.columnFooters.rows.push(new wjGrid.GroupRow());
    this.gridApplicationList.bottomLeftCells.setCellData(0, 0, '');
    this.gridApplicationList.columnFooters.setCellData(0, 3, '合計');

  }

  /**
   * CSVダウンロード
   */
  downloadDetails() {
    const list: InvSumSR[] = this.gridApplicationList.getCheckedRows();

    if (list.length === 0) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    }

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;

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

    this.invService.createInvLineListCsv(loginStaffCode, accessLevel, list, this.searchScopeType)
    .subscribe(
      (resp: NonObjectResponse<string>) => {
        const fileID = resp.value;
        const assetSection = list[0].invAssetTypeName;
        const fileName = '棚卸明細_' + (assetSection ? assetSection + '_' : '') + list[0].period + '_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
        this.fileDownloadService.download(fileID, fileName, 'text/csv');
      }
    );
  }

  /**
   * PDF実査表ダウンロード
   */
  printFactSheet() {
    const list: InvSumSR[] = this.gridApplicationList.getCheckedRows();
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
   * 詳細画面を開く
   * @param rowIndex 表示する行のIndex
   */
  showDetails(rowIndex: number) {
    this.siteAccessService.openEasset(
      SystemConst.Menu.Ref.menuIdInvRef, this.sessionInfo.loginCompanyCode,
      this.sessionInfo.loginRoleCodeList, this.sessionInfo.loginRoleName,
      false, {params: JSON.stringify(this.applicationInventoryList[rowIndex])}
    );
  }

}
