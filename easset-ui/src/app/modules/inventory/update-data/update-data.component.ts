import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { DatePipe } from '@angular/common';

import * as wjGrid from 'wijmo/wijmo.grid';

import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';
import { InventoryComponent } from '../inventory.component';

import { SessionService } from 'src/app/services/session.service';
import { MessageService } from 'src/app/services/message.service';
import { InvService } from 'src/app/services/api/inv.service';
import { HistService } from 'src/app/services/api/hist.service';
import { FileUploadService } from 'src/app/services/api/file-upload.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';

import { SystemMessage } from 'src/app/const/system-message';
import { SessionInfo } from 'src/app/models/session-info';
import { InvStat } from 'src/app/models/api/inv/inv-stat';
import { BulkUpdateHist } from 'src/app/models/api/hist/bulk-update-hist';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';

/**
 * 棚卸データ一括更新コンポネント
 */

@Component({
  selector: 'app-update-data',
  templateUrl: './update-data.component.html',
  styleUrls: ['./update-data.component.scss']
})
export class UpdateDataComponent extends AbstractChildComponent<InventoryComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * フォーム
   */
  updateForm: FormGroup;

  /**
   * 会計年月一覧情報
   */
  accountingDates: InvStat[];

  /**
   * 履歴情報
   */
  historyList: BulkUpdateHist[];

  /**
   * アップロードするファイルのID
   */
  fileId: string;

  /**
   * 履歴表示するか判定用値
   */
  isHistoryShown: boolean;

  /**
   * キャンセル時のデータID
   */
  cancelDataID: number;

  /**
   * 棚卸一覧グリッド Ref
   */
  @ViewChild('fileSelectCSV') fileSelectCSV: ElementRef;

  /**
   * 履歴一覧グリッド Ref
   */
  @ViewChild('gridHistoryList', null) gridHistoryList: EaFlexGrid;

  /**
   * キャンセル用確認ポップアップ Ref
   */
  @ViewChild('confirmCancelPopup', null) confirmCancelPopup: ConfirmPopupComponent;

  constructor(
    private fb: FormBuilder,
    private datePipe: DatePipe,
    private sessionService: SessionService,
    private messageService: MessageService,
    private invService: InvService,
    private histService: HistService,
    private fileUploadService: FileUploadService,
    private fileDownloadService: FileDownloadService
  ) {
    super();
    this.fb = fb;
    this.updateForm = this.fb.group({
      period: [''],
      fileName: ['']
    });
  }

  /**
   * 画面を読み込み
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
    this.invService.searchInvStat(companyCode).subscribe(
      (data: InvStat[]) => {
        this.accountingDates = data
        .map((obj: InvStat) => {
          obj.periodName = obj.period.slice(0, 4) + '-' + obj.period.slice(-2);
          return obj;
        });
        if (this.accountingDates.length) {
          this.updateForm.controls.period.setValue(this.accountingDates[0].period);
        }
      }
    );
  }

  /**
   * グリッド初期処理
   * @param grid Flexグリッド
   */
  onGridInitialized(grid: EaFlexGrid) {
  }

  /**
   * フォーマット用CSVダウンロード
   */
  getFormatCSV() {
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const period: string = this.updateForm.controls.period.value;

    if (!this.updateForm.controls.period.value) {
      this.showNoPeriodError();
      return;
    }

    this.invService.createInvTemplateCsv(companyCode, period)
    .subscribe(
      (resp: NonObjectResponse<string>) => {
        const fileId = resp.value;
        const contentType = 'text/csv';
        const fileName = '棚卸データ一括更新用定型ファイル_' + period + '_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
        this.fileDownloadService.download(fileId, fileName, contentType);
      }
    );
  }

  showNoPeriodError() {
    this.messageService.err(SystemMessage.Err.msg300006);
  }

  /**
   * ファイル選択後処理
   * @param files ファイル情報
   */
  fileSelected(files: FileList) {
    if (!files.length) {
      return;
    }
    const selectedFile = files[0];
    if (files.length) {
      this.fileUploadService.upload(files)
      .subscribe(
        (fileId: NonObjectResponse<string>) => {
          this.updateForm.controls.fileName.setValue(selectedFile.name);
          this.fileId = fileId.value;
          this.isHistoryShown = false;
          this.fileSelectCSV.nativeElement.value = '';
          this.messageService.info(SystemMessage.Info.msg100016); // メッセージはSystemMessage.Infoを使用してください。
        }
      );
    }
  }

  /**
   * 一括更新用リクエスト
   */
  massiveUpdate() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const companyCode: string = this.sessionInfo.loginCompanyCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const fileId = this.fileId;
    const period: string = this.updateForm.controls.period.value;

    this.invService.callUpdateInvBulk(loginStaffCode, companyCode, accessLevel, fileId, period)
    .subscribe(
      () => {
        this.showHistory();
      }
    );
  }

  /**
   * アップロードキャンセル
   */
  cancel() {
    this.fileId = null;
    this.updateForm.controls.fileName.setValue(null);
    this.fileSelectCSV.nativeElement.value = '';
    this.isHistoryShown = false;
  }

  /**
   * 履歴を表示する
   */
  showHistory() {
    this.isHistoryShown = true;
    this.updateHistory();
  }

  /**
   * 履歴更新用リクエスト
   */
  updateHistory() {
    const companyCode = this.sessionInfo.loginCompanyCode;
    const loginStaffCode = null;
    const functionName = 'INV';
    const isExecOnly = false;
    const createDateFrom: Date = new Date();
    createDateFrom.setDate(createDateFrom.getDate() - 100);
    const createDateTo: Date = null;

    this.histService.getBulkUpdateHistList(companyCode, loginStaffCode, functionName, createDateFrom, createDateTo, isExecOnly)
      .subscribe(
        (data: BulkUpdateHist[]) => {
          this.historyList = data
          .map((obj: BulkUpdateHist) => {
            obj.updateColumnName = obj.updateColumnName.slice(0, 4) + '-' + obj.updateColumnName.slice(-2);
            return obj;
          });
          if (this.gridHistoryList) {
            this.gridHistoryList.selectionMode = wjGrid.SelectionMode.Row;
            setTimeout(() => {
              this.gridHistoryList.selectionMode = wjGrid.SelectionMode.None;
            });
            this.gridHistoryList.resetSelection();
          }
        }
    );
  }

  /**
   * メッセージファイルダウンロード
   * @param index 履歴 index
   */
  downloadMessageFile(index: number) {
    const fileId = this.gridHistoryList.collectionView.items[index].fileId + '_E';
    const filePath = this.sessionInfo.fileSaveRootPath + '/INV_BULK_UPDATE/' +
    this.datePipe.transform(this.gridHistoryList.collectionView.items[index].createDate, 'yyyyMM') + '/' +
    this.datePipe.transform(this.gridHistoryList.collectionView.items[index].createDate, 'yyyyMMdd') + '_' +
    this.gridHistoryList.collectionView.items[index].createStaffCode;
    const fileName = 'メッセージ.txt';
    const contentType = 'text/plain';
    this.fileDownloadService.download(fileId, fileName, contentType, filePath);
  }

  /**
   * 一括更新をキャンセルするリクエスト
   * @param index インデックス
   * @param confirm 確認ポップアップ表示か判定用
   */
  cancelUpdate(index?: number, confirm?: boolean) {
    if (index !== null && index !== undefined) {
      this.cancelDataID = index;
    }
    if (!confirm) {
      this.confirmCancelPopup.prompt(SystemMessage.Conf.msg400003, document.activeElement);
    } else {
      const logId = this.gridHistoryList.collectionView.items[this.cancelDataID].logId;
      const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
      const execStatus = '終了中';
      const execCount: number = this.gridHistoryList.collectionView.items[this.cancelDataID].execCount;
      const successCount: number = this.gridHistoryList.collectionView.items[this.cancelDataID].successCount;
      const failureCount: number = this.gridHistoryList.collectionView.items[this.cancelDataID].failureCount;
      this.histService.updateBulkUpdateLog(logId, loginStaffCode, execStatus, execCount, successCount, failureCount)
      .subscribe(
        () => {
          this.messageService.info(SystemMessage.Info.msg100005);
          this.updateHistory();
        }
      );
    }
  }

  /**
   * データダウンロード
   * @param type ステータスの種類
   * @param index 履歴 index
   */
  getData(type: string, index: number) {
    let fileIdCode: string;
    let fileName: string;
    switch (type) {
      case 'all':
        fileIdCode = '';
        fileName = '一括更新対象データ.csv';
        break;
      case 'success':
        fileIdCode = 'S';
        fileName = '一括更新成功データ.csv';
        break;
      case 'fail':
        fileIdCode = 'F';
        fileName = '一括更新失敗データ.csv';
        break;
    }
    const fileId = this.gridHistoryList.collectionView.items[index].fileId + (fileIdCode ? '_' + fileIdCode : '');
    const contentType = 'text/csv';
    const filePath = this.sessionInfo.fileSaveRootPath + '/INV_BULK_UPDATE/' +
    this.datePipe.transform(this.gridHistoryList.collectionView.items[index].createDate, 'yyyyMM') + '/' +
    this.datePipe.transform(this.gridHistoryList.collectionView.items[index].createDate, 'yyyyMMdd') + '_' +
    this.gridHistoryList.collectionView.items[index].createStaffCode;
    this.fileDownloadService.download(fileId, fileName, contentType, filePath, );
  }

}
