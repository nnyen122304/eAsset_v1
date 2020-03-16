import { Component, OnInit, ViewChild, EventEmitter, Output, OnChanges, Input } from '@angular/core';
import { FormBuilder } from '@angular/forms';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';

import * as wjGrid from 'wijmo/wijmo.grid';

import { EaFlexGridColumn } from 'src/app/components/ea-flex-grid/ea-flex-column.component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { ApGetTanService } from 'src/app/services/api/ap-get-tan.service';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { InvSumSR } from 'src/app/models/api/inv/inv-sum-sr';


@Component({
  selector: 'app-ap-get-tan-result-list',
  templateUrl: './ap-get-tan-result-list.component.html',
  styleUrls: ['./ap-get-tan-result-list.component.scss']
})
export class ApGetTanResultListComponent implements OnChanges {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 検索する資産データ
   */
  item: InvSumSR;

  /**
   * 情報機器等登録(申請)
   */
  apGetTanList: ApGetTanSR[];

   dataList: [] = [];

  /**
   * 選択時イベント
   */
  @Output() select: EventEmitter<object> = new EventEmitter();

  loginStaffCode: string;
  accessLevel: string;
  searchParamSC: ApGetTanSC = {
    applicationId: null,
    applicationIdPlural: null,
    eappId: null,
    eappIdPlural: null,
    apStatus: null,
    apDateFrom: null,
    apDateTo: null,
    apStaffCode: null,
    apStaffName: null,
    apGetType: null,
    excludeCompleteFlag: null,
    reoOrderFlag: null,
    getItemName: null,
    getItemCloudType: null,
    getItemGroupCode: null,
    getItemGroupName: null,
    getDeliveryDateFrom: null,
    getDeliveryDateTo: null,
    getSystemSectionDeployFlag: null,
    getPurCompanyName: null,
    getPurEstimateNumber: null,
    getPurEstimateNumberPlural: null,
    getLeReCompanyCode: null,
    getLeReCompanyName: null,
    getLeReEstimateNumber: null,
    getLeReEstimateNumberPlural: null,
    getLeEappId: null,
    getTotalAmountFrom: null,
    getTotalAmountTo: null,
    costType: null,
    costDepPrjCode: null,
    costDepPrjName: null,
    costDepPrjUndecidedFlag: null,
    description: null,
    holCompanyCode: null,
    holCompanyName: null,
    holSectionCode: null,
    holSectionName: null,
    includeSectionHierarchyFlag: null,
    holStaffCode: null,
    holStaffName: null,
    holOfficeCode: null,
    holOfficeName: null,
    holOfficeFloor: null,
    astName: null,
    astMakerCode: null,
    astMakerName: null,
    astMakerModel: null,
    astPrjCode: null,
    astPrjName: null,
    softwareMakerId: null,
    softwareMakerName: null,
    softwareId: null,
    softwareName: null,
    failureAssetId: null,
    failureAssetIdPlural: null,
    lineExistsType: null,
  };

  searchParamSR: ApGetTanSR[];

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('gridApGetTanResultList', null) gridApGetTanResultList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private sessionService: SessionService,
    private apGetTanService: ApGetTanService,
    private messageService: MessageService) {
    this.fb = fb;
  }

  /**
   * 画面の読み込み
   */
  ngOnChanges() {
    this.gridApGetTanResultList.resetSelection();
    this.sessionInfo = this.sessionService.getSessionInfo();
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

    for (let colIndex = 7; colIndex <= 35; colIndex++) {
      if (colIndex >= 7 && colIndex <= 9) {
        panel.setCellData(0, colIndex, '申請者');
      }
      if (colIndex >= 10 && colIndex <= 11) {
        panel.setCellData(0, colIndex, '申請保有部署者');
      }
      if (colIndex >= 12 && colIndex <= 13) {
        panel.setCellData(0, colIndex, '資産管理担当者');
      }
      if (colIndex >= 14 && colIndex <= 15) {
        panel.setCellData(0, colIndex, '設置(使用)場所');
      }
      if (colIndex >= 16 && colIndex <= 19) {
        panel.setCellData(0, colIndex, '経費負担');
      }
      if (colIndex >= 20 && colIndex <= 23) {
        panel.setCellData(0, colIndex, '資産(機器)情報[代表]');
      }
      if (colIndex >= 24 && colIndex <= 27) {
        panel.setCellData(0, colIndex, '資産(機器)数量[合計]');
      }
      if (colIndex >= 28 && colIndex <= 29) {
        panel.setCellData(0, colIndex, 'ライセンス情報[代表]');
      }
      if (colIndex >= 30 && colIndex <= 32) {
        panel.setCellData(0, colIndex, 'ライセンス数量[合計]');
      }
      if (colIndex >= 34 && colIndex <= 35) {
        panel.setCellData(0, colIndex, '備考');
      }
    }

    const mergableCols = [0, 1, 2, 3, 4, 5, 6, 33, 36, 37];
    grid.columns.forEach((col: EaFlexGridColumn) => {
      if (mergableCols.indexOf(col.index) >= 0) {
        col.allowMerging = true;
        panel.setCellData(0, col.index, col.header);
      }
    });


    grid.frozenColumns = 3;
    grid.hostElement.addEventListener('dblclick', (e: MouseEvent) => {
      if (!grid.hitTest(e).panel) {
        return;
      }
    });
    document.getElementById('wp-grid-ap-get-tan-result-list').querySelectorAll('input[type=checkbox]').forEach((e) => {
      e.setAttribute('style', 'display:none');
    });
  }

  /**
   * 検索リクエスト、処理
   */
  init() {
    this.loginStaffCode = this.sessionInfo.loginUser.staffCode;
    this.accessLevel = this.sessionInfo.currentAccessLevel;
    this.apGetTanService.searchApGetTan(this.loginStaffCode, this.accessLevel, this.searchParamSC).subscribe(
      (resp: ApGetTanSR[]) => {
        if (!resp.length) {
          this.messageService.warn(SystemMessage.Warn.msg200002);
          return;
        }
        this.apGetTanList = resp.map((obj: ApGetTanSR) => {
          obj.sel = false;
          return obj;
        });
        this.apGetTanList = resp;
        this.gridApGetTanResultList.resetSelection();
        this.select.emit(this.gridApGetTanResultList);
      });
  }
}
