import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { LicenseComponent } from '../license.component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { DownloadOptionPropComponent } from 'src/app/parts/option/download-option-prop/download-option-prop.component';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { LicenseService } from 'src/app/services/api/license.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { DatePipe } from '@angular/common';
import { LicenseSC } from 'src/app/models/api/license/license-sc.model';
import { LicenseSearchConditionComponent } from 'src/app/parts/screen/license/license-search-condition/license-search-condition.component';
import { LicenseSR } from 'src/app/models/api/license/license-sr.model';
import { SystemMessage } from 'src/app/const/system-message';
import { MessageService } from 'src/app/services/message.service';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-license-search',
  templateUrl: './license-search.component.html',
  styleUrls: ['./license-search.component.scss']
})
export class LicenseSearchComponent extends AbstractChildComponent<LicenseComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * アクセスレベル
   */
  accessLevel: string;

  /**
   * 選択されているメニュー
   */
  currentMenuId: string;

  /**
   * ログイン社員番号
   */
  loginStaffCode: string;

  /**
   * 検索条件
   */
  searchParamSC: LicenseSC = new LicenseSC();

  /**
   * メニューに関する定数
   */
  readonly MENU_ID_LICENSE_DELETE = SystemConst.Menu.menuIdLicenseDelete;

  /**
   * コンポーネントの参照
   */
  @ViewChild(DownloadOptionPropComponent, null) popupDownload: DownloadOptionPropComponent;

  /**
   * コンポーネントの参照
   */
  @ViewChild(LicenseSearchConditionComponent) licSearchCondition: LicenseSearchConditionComponent;

  constructor(
    private sessionService: SessionService,
    private fileDownloadService: FileDownloadService,
    private licenseService: LicenseService,
    private messageService: MessageService,
    private datePipe: DatePipe,
  ) {
    super();
  }

  /**
   * コンポネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.accessLevel = this.sessionInfo.currentAccessLevel;
    this.currentMenuId = this.sessionInfo.currentMenuId;
    this.loginStaffCode = this.sessionInfo.loginUser.staffCode;
  }

  /**
   * フォーマット用CSVダウンロード
   */
  downloadCSV(data) {
    this.searchParamSC = this.licSearchCondition.getSearch();
    if (this.searchParamSC !== null) {
      this.searchParamSC.dowloadLineItem = data.lineItem;
      this.licenseService.createLicenseCsv(this.loginStaffCode, this.accessLevel, data, this.searchParamSC, false).subscribe(
        (resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'text/csv';
          const fileName = 'ライセンス検索結果_' + '_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          this.fileDownloadService.download(fileId, fileName, contentType);
        }
      );
    }
  }

  /**
   * 検索処理
   */
  search() {
    this.searchParamSC = this.licSearchCondition.getSearch();
    this.licenseService.searchLicense(this.loginStaffCode, this.accessLevel, this.searchParamSC, false).subscribe(
      (resp: LicenseSR[]) => {
        if (resp.length > 0) {
          this.parent.changeChild(this.parent.viewIndexResult, {
            action: 'init',
            params: {
              dataList: resp
            },
          });
        } else {
          this.messageService.warn(SystemMessage.Warn.msg200002);
          return;
        }
      }
    );
  }

  /**
   * 検索をリセット
   */
  resetSearch() {
    this.licSearchCondition.resetSearch();
  }
}
