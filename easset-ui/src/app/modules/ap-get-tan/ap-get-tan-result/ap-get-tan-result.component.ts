import { Component, OnInit, ViewChild } from '@angular/core';
import { ApGetTanComponent } from '../ap-get-tan.component';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { EaFlexGrid } from 'src/app/components/ea-flex-grid/ea-flex-grid.component';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { ApGetTanService } from 'src/app/services/api/ap-get-tan.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { SessionInfo } from 'src/app/models/session-info';
import { DatePipe } from '@angular/common';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { ApGetTanResultListComponent } from 'src/app/parts/screen/ap-get-tan/ap-get-tan-result-list/ap-get-tan-result-list.component';
import { DownloadOptionComponent } from 'src/app/parts/option/download-option/download-option.component';
import { PrintOptionComponent } from 'src/app/parts/option/print-option/print-option.component';
import { SessionService } from 'src/app/services/session.service';
import { FormGroup } from '@angular/forms';

@Component({
  selector: 'app-ap-get-tan-result',
  templateUrl: './ap-get-tan-result.component.html',
  styleUrls: ['./ap-get-tan-result.component.scss']
})
export class ApGetTanResultComponent extends AbstractChildComponent<ApGetTanComponent> implements OnInit {

  /**
   * 検索条件
   */
  searchParamSC: ApGetTanSC = {};

  /**
   * 入力データ
   */
  listApGetTanSR: ApGetTanSR[] = [];

  /**
   * アプリケーションコードで検索
   */
  searchApplicationId = '';

  /**
   * 絞込フォーム
   */
  filterForm: FormGroup;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * ログイン社員番号
   */
  loginStaffCode: string;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild(ApGetTanResultListComponent) resultList: ApGetTanResultListComponent;

  /**
   * 公開設定ポップアップ要素Reference
   */
  @ViewChild(DownloadOptionComponent, null) popupDownloadOption: DownloadOptionComponent;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('gridApGetTanResultList', null) gridApGetTanResultList: EaFlexGrid;

  /**
   * 公開設定ポップアップ要素Reference
   */
  @ViewChild(PrintOptionComponent, null) popupPrintOption: PrintOptionComponent;

  constructor(
    private apGetTanService: ApGetTanService,
    private fileDownloadService: FileDownloadService,
    private datePipe: DatePipe,
    private messageService: MessageService,
    private sessionService: SessionService,
  ) {
    super();
  }

  /**
   * コンポネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initResult') {
        this.listApGetTanSR = param.params.datalist;
        this.searchParamSC = param.params.searchParam;
      } else if (param.action === 'back') {
        if (param.params.updateRow) {
          this.resultList.updateRow(param.params.updateRow);
        }
      } else {
        this.goBack();
      }
    });
  }

  /**
   * Go Back
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexResult, {
      action: 'backSearch',
      params: {
        searchParam: this.searchParamSC
      },
    });
  }

  /**
   * goi common popupDownloadOption
   */
  open2(event) {
    this.popupDownloadOption.open(event);
  }

  /**
   * goi common popupPrintOption
   */
  open1(event) {
    this.popupPrintOption.open(event);
  }

  /**
   * Go to detail
   */
  clickRow(row) {
    if (!row || !row.dataItem) {
      return;
    }

    this.parent.changeChild(this.parent.viewIndexEntry, {
      action: 'initCreate',
      params: {
        loadAssetId: row.dataItem.applicationId,
        loadAssetVersion: row.dataItem.applicationVersion,
        index: row.index
      },
    });
  }

  /**
   * 検索
   */
  filterAssets() {
    if (this.searchApplicationId === null || this.searchApplicationId === '') {
      this.messageService.info('検索条件を入力してください。');
      return;
    }
    this.searchParamSC.applicationId = this.searchApplicationId;
    this.apGetTanService.searchApGetTan(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, this.searchParamSC).subscribe(
      (resp: ApGetTanSR[]) => {
        if (resp.length > 0) {
          this.listApGetTanSR = resp;
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
          return;
        }
      });
  }

  /**
   * hanh dong in file pdf
   */
  print(option) {
    const printParam = [];
    const checkedList = this.gridApGetTanResultList.getCheckedRows();
    checkedList.map((item: ApGetTanSC) => {
      printParam.push(item.applicationId);
    });
    if (printParam.length > 0) {
      this.apGetTanService.createApGetTanPdf(printParam, option.lineOutputFlag)
        .subscribe(
          (resp: NonObjectResponse<string>) => {
            this.fileDownloadService.preview(resp.value, 'application/pdf');
          }
        );
    }
  }

  /**
   * hanh dong tai file csv
   */
  download(option) {
    const printParam = [];
    const checkedList = this.gridApGetTanResultList.getCheckedRows();
    checkedList.map((item: ApGetTanSC) => {
      printParam.push(item.applicationId);
    });
    const searchParam: ApGetTanSC = new ApGetTanSC();
    const loginStaffCode = this.sessionInfo.loginUser.staffCode;
    const accessLevel = this.sessionInfo.currentAccessLevel;
    this.apGetTanService.createApGetTanCsv(loginStaffCode, accessLevel, searchParam, option.lineOutputFlag)
      .subscribe(
        (resp: NonObjectResponse<string>) => {
          let fileName = '取得申請検索結果';
          const downloadLineOutputFlag = resp['lineOutputFlag'];
          if (downloadLineOutputFlag) {
            fileName += `_明細単位_${this.datePipe.transform(
              new Date(),
              'yyyyMMdd_HHmmss',
            )}.csv`;
          } else {
            fileName += `_申請書単位_${this.datePipe.transform(
              new Date(),
              'yyyyMMdd_HHmmss',
            )}.csv`;
          }
          this.fileDownloadService.download(resp.value, fileName, 'text/csv');
        }
      );
  }
}
