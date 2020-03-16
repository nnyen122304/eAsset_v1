import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { ApGetIntComponent } from '../ap-get-int.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { SystemConst } from 'src/app/const/system-const';
import { ApGetIntSC } from 'src/app/models/api/ap-get-int/ap-get-int-sc.model';
import { ApGetIntService } from 'src/app/services/api/ap-get-int.service';
import { ApGetIntSR } from 'src/app/models/api/ap-get-int/ap-get-int-sr.model';
import { MessageService } from 'src/app/services/message.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { SystemMessage } from 'src/app/const/system-message';
import { DownloadOptionComponent } from 'src/app/parts/option/download-option/download-option.component';
import { ApGetIntSearchConditionComponent } from 'src/app/parts/screen/ap-get-int/ap-get-int-search-condition/ap-get-int-search-condition.component';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-ap-get-int-search',
  templateUrl: './ap-get-int-search.component.html',
  styleUrls: ['./ap-get-int-search.component.scss']
})

export class ApGetIntSearchComponent extends AbstractChildComponent<ApGetIntComponent> implements OnInit {

  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 選択されているメニュー
   */
  currentMenuId: string;

  /**
   * 現在の状態
   */
  currentState: string;

  /**
   * ログイン社員番号
   */
  loginStaffCode: string;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   * メニューID
   */
  menuId: string;

  /**
   * 検索条件
   */
  searchParam: ApGetIntSC;

  /**
   * メニュー
   */
  readonly sysMenu = SystemConst.Menu;

  @ViewChild(ApGetIntSearchConditionComponent) searchCondition: ApGetIntSearchConditionComponent;
  @ViewChild(DownloadOptionComponent) downloadOption: DownloadOptionComponent;

  constructor(
    private sessionService: SessionService,
    private apGetIntService: ApGetIntService,
    private messageService: MessageService,
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
    this.menuId = this.sessionInfo.currentMenuId;
    this.changeChildSubject.subscribe(param => {
      if (param.action === 'init') {
        this.currentState = param.params.currentState;
      }
    });
  }

  /**
   * 検索
   */
  search() {
    this.searchParam = this.searchCondition.getSearchCondition();
    this.apGetIntService.searchApGetInt(this.sessionInfo.loginUser.staffCode, this.sessionInfo.currentAccessLevel, this.searchParam).subscribe(
      (resp: ApGetIntSR[]) => {
        if (resp.length > 0) {
          this.parent.changeChild(this.parent.viewIndexResult, {
            action: 'initResult',
            params: {
              dataList: resp,
              searchParams: this.searchParam,
              currentMenu: this.currentState
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
  download(data) {
    this.searchParam = this.searchCondition.getSearchCondition();
    if (this.searchParam !== null) {
      const lineOutputFlag = data.lineOutputFlag;
      this.apGetIntService.createApGetIntCsv(
        this.sessionInfo.loginUser.staffCode,
        this.sessionInfo.currentAccessLevel,
        this.searchParam,
        lineOutputFlag).subscribe(
        (resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'test/csv';
          let fileName = '取得申請(無形固定資産・長期前払費用)検索結果';
          if (lineOutputFlag === true) { // 明細単位
            fileName += '_明細単位_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          } else { // 申請書単位
            fileName += '_申請書単位_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          }
          this.fileDownloadService.download(fileId, fileName, contentType);
        }
      );
    }
  }

  /**
   * クリア
   */
  clear() {
    this.searchCondition.resetSearch();
  }
}
