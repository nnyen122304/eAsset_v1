import { Component, OnInit, ViewChild } from '@angular/core';
import { DatePipe } from '@angular/common';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { SessionService } from 'src/app/services/session.service';
import { ApGetIntComponent } from '../ap-get-int.component';
import { MessageService } from 'src/app/services/message.service';
import { SystemMessage } from 'src/app/const/system-message';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { SessionInfo } from 'src/app/models/session-info';
import { ApGetIntSR } from 'src/app/models/api/ap-get-int/ap-get-int-sr.model';
import { ApGetIntResultListComponent } from 'src/app/parts/screen/ap-get-int/ap-get-int-result-list/ap-get-int-result-list.component';
import { ConfirmPopupComponent } from 'src/app/parts/confirm-popup/confirm-popup.component';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { ApGetIntService } from 'src/app/services/api/ap-get-int.service';
import { ApGetInt } from 'src/app/models/api/ap-get-int/ap-get-int.model';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';
import { DownloadOptionComponent } from 'src/app/parts/option/download-option/download-option.component';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-ap-get-int-result',
  templateUrl: './ap-get-int-result.component.html',
  styleUrls: ['./ap-get-int-result.component.scss']
})
export class ApGetIntResultComponent extends AbstractChildComponent<ApGetIntComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 取得申請検索結果一覧
   */
  apGetIntSRList: ApGetIntSR[] = [];

  /**
   * 取得申請検索結果
   */
  searchApGetIntSC: ApGetIntSC;

  /**
   * 全社権限の場合表示
   */
  searchApplicationId = '';

  /**
   * 変数定義
   */
  readonly stateMenuApGetInt = {
    menuIdApGetIntSearch: 'AP_GET_INT',
    menuIdApGetIntCopy: 'AP_GET_INT_COPY',
    menuIdApGetIntEdit1: 'AP_GET_INT_UPDATE1',
    menuIdApGetIntEdit2: 'AP_GET_INT_UPDATE2',
  };

  /**
   * ステートに応じた固定値設定
   */
  currentState = this.stateMenuApGetInt.menuIdApGetIntSearch;

  /**
   * ポップアップ Ref
   */
  @ViewChild(ApGetIntResultListComponent) resultList: ApGetIntResultListComponent;

  /**
   * 完了報告確認ポップアップ Ref
   */
  @ViewChild('confirmPopup', null) confirmPopup: ConfirmPopupComponent;
  @ViewChild(DownloadOptionComponent) downloadOption: DownloadOptionComponent;

  constructor(private sessionService: SessionService,
              private messageService: MessageService,
              private apGetIntService: ApGetIntService,
              private datePipe: DatePipe,
              private fileDownloadService: FileDownloadService
  ) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'initResult') {
        this.apGetIntSRList = param.params.dataList;
        this.searchApGetIntSC = param.params.searchParams;
        this.currentState = param.params.currentState;
      } else {
        this.goBack();
      }
    });
  }

  /**
   * Get access level
   */
  getAccessLevel() {
    let accessLevel = 'category';
    switch (this.sessionInfo.currentAccessLevel) {
      case 'S':
        accessLevel = 'admin';
        break;
      case 'C':
        accessLevel = 'general';
        break;
      case 'B':
        accessLevel = 'asset_manager';
        break;
      default:
        accessLevel = 'category';
        break;
    }

    return accessLevel;
  }

  /**
   * 検索画面呼び出し
   */
  goBack() {
    this.parent.changeChild(this.parent.viewIndexSearch, {
      action: 'backSearch',
      params: {
        searchParams: this.searchApGetIntSC,
        currentState: this.currentState
      },
    });
  }

  /**
   * 印刷
   */
  print() {
    const rows = this.resultList.getDataSelected();
    if (rows.length < 1) {
      this.messageService.err(SystemMessage.Err.msg300002);
      return;
    }

    // tslint:disable-next-line:no-any
    let printParam = [];
    rows.map((item: ApGetIntSR) => {
      let apGetIntItem = new ApGetInt();
      apGetIntItem = apGetIntItem.setData(item);
      printParam = [...printParam, ...[apGetIntItem]];
    });
    //  Call service create pdf file
    this.apGetIntService.createApGetIntPdf(printParam, true).subscribe((resp: NonObjectResponse<string>) => {
      // Call service preview file
      this.fileDownloadService.preview(resp.value, 'application/pdf');
    });
  }

  /**
   * 子画面切替処理
   */
  clickRow(row) {
    this.parent.changeChild(this.parent.viewIndexCreate1, {
      action: 'initDetail',
      params: {
        item: row
      },
    });
  }

  /**
   * ダウンロード
   */
  download(data) {
    let lineOutputFlag = false;
    if (data && data.lineOutputFlag) {
      lineOutputFlag = data.lineOutputFlag;
    }

    // 取得申請検ダウンロード
    this.apGetIntService.createApGetIntCsv(this.sessionInfo.loginUser.staffCode,
      this.sessionInfo.currentAccessLevel,
      this.searchApGetIntSC,
      lineOutputFlag).subscribe(
      (resp: NonObjectResponse<string>) => {
        let fileName = '取得申請(無形固定資産・長期前払費用)検索結果';

        if (lineOutputFlag) { // 明細単位
          fileName += '_明細単位_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
        } else { // 申請書単位
          fileName += '_申請書単位_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
        }
        this.fileDownloadService.download(resp.value, fileName, 'text/csv');
      }
    );
  }

  /**
   * 督促メール
   */
  sendMail() {
    const rows = this.resultList.getDataSelected();
    if (rows.length < 1) {
      this.messageService.err(SystemMessage.Err.msg30038('処理対象'));
      return;
    }

    // tslint:disable-next-line:no-any
    let mailParam = [];
    rows.map((item: ApGetIntSR) => {
      let apGetIntItem = new ApGetInt();
      apGetIntItem = apGetIntItem.setData(item);
      mailParam = [...mailParam, ...[apGetIntItem]];
    });
    //  Call service create pdf file
    this.apGetIntService.remindApGetInt(this.sessionInfo.loginUser.staffCode,
      this.sessionInfo.currentAccessLevel, mailParam).subscribe((resp: NonObjectResponse<string>) => {
      this.messageService.info(SystemMessage.Info.msg100014);
    });
  }

  /**
   * 検索
   */
  search() {
    if (this.searchApplicationId === null || this.searchApplicationId === '') {
      this.messageService.info(SystemMessage.Err.msg30030);
      return;
    }

    this.searchApGetIntSC.apCompanyCode = this.sessionInfo.loginCompanyCode;
    this.searchApGetIntSC.holCompanyCode = this.sessionInfo.loginCompanyCode;
    this.searchApGetIntSC.applicationId = this.searchApplicationId;
    this.apGetIntService.searchApGetInt(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, this.searchApGetIntSC).subscribe(
      (resp: ApGetIntSR[]) => {
        if (resp.length > 0) {
          this.apGetIntSRList = resp;
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }
      });
  }
}
