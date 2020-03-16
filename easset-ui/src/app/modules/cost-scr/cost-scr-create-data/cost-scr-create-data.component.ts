import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { DatePipe } from '@angular/common';
import { FormBuilder, FormGroup } from '@angular/forms';

import * as wjGrid from 'wijmo/wijmo.grid';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { CostScrComponent } from '../cost-scr.component';

import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { FileUploadService } from 'src/app/services/api/file-upload.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';

import { PpfsImportService } from 'src/app/services/api/ppfs-import.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { EaFlexGridColumn } from 'src/app/components/ea-flex-grid/ea-flex-column.component';
import { CostScrService } from 'src/app/services/api/cost-scr.service';
import { MasterService } from 'src/app/services/api/master.service';
import { CodeNameEx } from 'src/app/models/api/code-name-ex';
import { CostScrStat } from 'src/app/models/api/cost-scr/cost-scr-stat.model';
import { OpenCloseSwitchingComponent } from './open-close-switching/open-close-switching.component';

@Component({
  selector: 'app-cost-scr-create-data',
  templateUrl: './cost-scr-create-data.component.html',
  styleUrls: ['./cost-scr-create-data.component.scss']
})
export class CostScrCreateDataComponent extends AbstractChildComponent<CostScrComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 作成条件フォーム
   */
  creatorForm: FormGroup;

  /**
   * 編集グリッドデータ
   */
  dataReleaseList: CostScrStat[];

  /**
   * コードネーム一覧データ
   */
  codeNameList: CodeNameEx[];

  /**
   * アップロードするファイルのID
   */
  fileId: string;

  /**
   * 形式の会計年月
   */
  periodCostScrStatList: string;

  /**
   * 公開設定ポップアップ要素Reference
   */
  @ViewChild(OpenCloseSwitchingComponent, null) popupOpenCloseSwitching: OpenCloseSwitchingComponent;

  @ViewChild('gridDataReleaseList', null) gridDataReleaseList: EaFlexGrid;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private sessionService: SessionService,
    private ppfsImportService: PpfsImportService,
    private messageService: MessageService,
    private masterService: MasterService,
    private costScrService: CostScrService,
    private fileUploadService: FileUploadService,
    private fileDownloadService: FileDownloadService
  ) {
    super();
    this.fb = fb;
    this.creatorForm = this.fb.group({
      companyName: [''],
      period: [''],
      periodYYYYMM: [''],
      overwriteFlag: false,
      value2: null
    });
  }

  /**
   * ポップアップの再表示
   */
  toggleOverwritePopup() {
    if (this.creatorForm.controls.overwriteFlag.value === true) {
      this.messageService.warn(SystemMessage.Warn.msg200007);
    }
  }

  /**
   * グリッドの初期勝利
   * @param grid Flexグリッド要素
   */
  onGridInitialized(grid: EaFlexGrid) {
    const extraRow = new wjGrid.Row();
    extraRow.allowMerging = true;
    extraRow.allowResizing = true;
    const panel = grid.columnHeaders;
    panel.rows.splice(0, 0, extraRow);

    for (let colIndex = 1; colIndex <= 4; colIndex++) {
      panel.setCellData(0, colIndex, 'データ作成');
    }

    for (let colIndex = 5; colIndex <= 6; colIndex++) {
      panel.setCellData(0, colIndex, '精査担当部署設定');
    }

    for (let colIndex = 7; colIndex <= 8; colIndex++) {
      panel.setCellData(0, colIndex, '各部メニューOPEN');
    }

    for (let colIndex = 9; colIndex <= 11; colIndex++) {
      panel.setCellData(0, colIndex, '依頼メール送信');
    }

    for (let colIndex = 12; colIndex <= 13; colIndex++) {
      panel.setCellData(0, colIndex, '各部メニューCLOSE');
    }

    const mergableCols = [0];
    grid.columns.forEach((col: EaFlexGridColumn) => {
      if (mergableCols.indexOf(col.index) >= 0) {
        col.allowMerging = true;
        panel.setCellData(0, col.index, col.header);
      }
    });

    grid.frozenColumns = 1;
  }

  /**
   * 画面の読み込み
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        this.init();
      }
    });
  }

  /**
   * 初期処理
   */
  init() {
    const companyCode = this.sessionInfo.loginCompanyCode;
    this.creatorForm.controls.companyName.setValue(this.sessionInfo.loginCompanyName);
    this.ppfsImportService.getPpfsCurrentPeriodRT(companyCode).subscribe(
      (resp: NonObjectResponse<string>) => {
        this.creatorForm.controls.period.setValue(resp.value);
        this.creatorForm.controls.periodYYYYMM.setValue(this.creatorForm.controls.period.value.substring(0, 4) + this.creatorForm.controls.period.value.substring(5, 7));
      }
    );
    this.updateData();
  }

  /**
   * コードネーム一覧データを取得
   */
  getCodeNameList() {
    const categoryCode = 'MENU_ACCESS_CONTROL';
    const internalCode = null;
    const companyCode = this.sessionInfo.loginCompanyCode;
    const values = null;

    this.masterService.getCodeNameList(categoryCode, internalCode, companyCode, values)
      .subscribe(
        (resp: CodeNameEx[]) => {
          this.creatorForm.controls.value2.setValue(resp[0].value2);
          this.codeNameList = resp;
        }
      );
  }

  /**
   * グリッドのデータを生成
   */
  create() {
    const companyCode = this.sessionInfo.loginCompanyCode;
    const period = this.creatorForm.controls.periodYYYYMM.value;
    const execStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const overwriteFlag = this.creatorForm.controls.overwriteFlag.value;
    this.costScrService.callCreateCostScrData(companyCode, period, execStaffCode, overwriteFlag)
      .subscribe(
        () => {
          this.updateData();
        }
      );
  }

  /**
   * グリッドのデータを更新
   */
  updateData() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode = this.sessionInfo.loginCompanyCode;
    this.costScrService.searchCostScrStatList(loginStaffCode, accessLevel, companyCode).subscribe(
      (data: CostScrStat[]) => {
        this.dataReleaseList = data;
        this.dataReleaseList.forEach((obj) => {
          obj.period = obj.period.substring(0, 4) + '-' + obj.period.substring(4, 6);
        });
        this.periodCostScrStatList = data[0].period.substring(0, 4) + data[0].period.substring(5, 7);
        this.gridDataReleaseList.resetSelection();
      }
    );
    this.getCodeNameList();
  }

  /**
   * フォーマット用CSVダウンロード
   */
  getFormatCSV() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode = this.sessionInfo.loginCompanyCode;
    const propertyList = null;

    if (!this.creatorForm.controls.period.value) {
      return;
    }

    this.costScrService.createScrPossibleInputMasterCsv(loginStaffCode, accessLevel, companyCode, propertyList)
      .subscribe(
        (resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'text/csv';
          const fileName = '精査担当部署設定_CSV入力可能値一覧' + '_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          this.fileDownloadService.download(fileId, fileName, contentType);
        }
      );
  }

  /**
   * ファイル選択(精査部署設定更新)
   */
  fileSelected(files) {
    if (!files.length) {
      return;
    }

    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode = this.sessionInfo.loginCompanyCode;
    const period = this.periodCostScrStatList;

    // FileUploadServletを実施する
    this.costScrService.updateCostScrSectionByCsv(loginStaffCode, accessLevel, companyCode, period, files.fileId)
      .subscribe(
        () => {
          this.messageService.info(SystemMessage.Info.msg100017);
          this.updateData();
        }
      );
  }

  /**
   * OPEN/CLOSE切替の再表示
   */
  showCreateDataPopup(event) {
    this.codeNameList.map((obj, i: number) => {
      this.popupOpenCloseSwitching.setData(this.codeNameList[i]);
      this.popupOpenCloseSwitching.open(event);
    });
  }

  /**
   * OPEN/CLOSE切替を実施する
   * @param data コードネーム一覧データ
   */
  isSendMailUpdate(data: CodeNameEx) {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const companyCode = this.sessionInfo.loginCompanyCode;
    const period = this.periodCostScrStatList;
    const publicCode = data.value2;
    const isSendMail = data.isSendMail;
    this.costScrService.publicCostScrData(loginStaffCode, accessLevel, companyCode, period, publicCode, isSendMail)
      .subscribe(
        () => {
          this.messageService.info(SystemMessage.Info.msg100017);
          this.popupOpenCloseSwitching.close();
          this.updateData();
        }
      );
  }
}
