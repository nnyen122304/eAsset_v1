import { Component, OnInit, ViewChild } from '@angular/core';
import { ApGetTanSearchConditionComponent } from 'src/app/parts/screen/ap-get-tan/ap-get-tan-search-condition/ap-get-tan-search-condition.component';
import { SessionService } from 'src/app/services/session.service';
import { ApGetTanService } from 'src/app/services/api/ap-get-tan.service';
import { MessageService } from 'src/app/services/message.service';
import { ApGetTanSC } from 'src/app/models/api/ap-get-tan/ap-get-tan-sc.model';
import { SessionInfo } from 'src/app/models/session-info';
import { ApGetTanSR } from 'src/app/models/api/ap-get-tan/ap-get-tan-sr.model';
import { SystemMessage } from 'src/app/const/system-message';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApGetTanComponent } from '../ap-get-tan.component';
import { DownloadOptionComponent } from 'src/app/parts/option/download-option/download-option.component';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { DatePipe } from '@angular/common';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-ap-get-tan-search',
  templateUrl: './ap-get-tan-search.component.html',
  styleUrls: ['./ap-get-tan-search.component.scss']
})
export class ApGetTanSearchComponent extends AbstractChildComponent<ApGetTanComponent> implements OnInit {

  /**
   * 取得申請
   */
  MENU_ID_AP_GET_CREATE_COPY = SystemConst.Menu.menuIdApGetTanCopy;

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 選択されているメニュー
   */
  currentMenuId: string;

  /**
   * ログイン社員番号
   */
  loginStaffCode: string;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   * 検索条件
   */
  searchParam: ApGetTanSC;

  @ViewChild(DownloadOptionComponent, null) popupDownload: DownloadOptionComponent;

  @ViewChild(ApGetTanSearchConditionComponent) searchCondition: ApGetTanSearchConditionComponent;

  constructor(
    private sessionService: SessionService,
    private apGetTanService: ApGetTanService,
    private messageService: MessageService,
    private fileDownloadService: FileDownloadService,
    private datePipe: DatePipe,
  ) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.accessLevel = this.sessionInfo.currentAccessLevel;
    this.loginStaffCode = this.sessionInfo.loginUser.staffCode;
    this.currentMenuId = this.sessionInfo.currentMenuId;
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        this.searchCondition.init();
      }
    });
  }

  /**
   * 検索
   */
  search() {
    this.searchParam = this.searchCondition.getSearchCondition();
    this.apGetTanService.searchApGetTan(this.loginStaffCode, this.accessLevel, this.searchParam).subscribe(
      (resp: ApGetTanSR[]) => {
        if (resp.length > 0) {
          this.parent.changeChild(this.parent.viewIndexResult, {
            action: 'initResult',
            params: {
              datalist: resp,
              searchParam: this.searchParam
            }
          });
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
        }
      }
    );
  }

  /**
   * ダウンロード
   */
  downloadCSV(data) {
    this.searchParam = this.searchCondition.getSearchCondition();
    if (this.searchParam !== null) {
      const lineOutputFlag = data.lineOutputFlag;
      this.apGetTanService.createApGetTanCsv(this.loginStaffCode, this.accessLevel, this.searchParam, lineOutputFlag).subscribe(
        (resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'text/csv';
          let fileName = '';
          if (lineOutputFlag === true) {
            fileName = '取得申請検索結果_明細単位_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          } else {
            fileName = '取得申請検索結果_申請書単位_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          }
          this.fileDownloadService.download(fileId, fileName, contentType);
        }
      );
    }
  }

  /**
   * クリア
   */
  resetSearch() {
    this.searchCondition.init();
  }
}
