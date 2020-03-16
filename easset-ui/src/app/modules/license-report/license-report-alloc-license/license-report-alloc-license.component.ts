import { Component, OnInit, ViewChild } from '@angular/core';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { LicenseService } from 'src/app/services/api/license.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { DatePipe } from '@angular/common';
import { LicenseSC } from 'src/app/models/api/license/license-sc.model';
import { LicenseSearchConditionComponent } from 'src/app/parts/screen/license/license-search-condition/license-search-condition.component';
import { LicenseReportComponent } from '../license-report.component';

@Component({
  selector: 'app-license-report-alloc-license',
  templateUrl: './license-report-alloc-license.component.html',
  styleUrls: ['./license-report-alloc-license.component.scss']
})
export class LicenseReportAllocLicenseComponent extends AbstractChildComponent<LicenseReportComponent> implements OnInit {
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
   * コンポーネントの参照
   */
  @ViewChild(LicenseSearchConditionComponent) licSearchCondition: LicenseSearchConditionComponent;

  constructor(
    private sessionService: SessionService,
    private fileDownloadService: FileDownloadService,
    private licenseService: LicenseService,
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
  downloadCSV() {
    this.searchParamSC = this.licSearchCondition.getSearch();
    if (this.searchParamSC !== null) {
      this.licenseService.createAllocLicenseCsv(this.loginStaffCode, this.accessLevel, this.searchParamSC).subscribe(
        (resp: NonObjectResponse<string>) => {
          const fileId = resp.value;
          const contentType = 'text/csv';
          const fileName = 'ライセンス_管理帳票_割当情報_' + '_' + this.datePipe.transform(new Date(), 'yyyyMMdd_HHmmss') + '.csv';
          this.fileDownloadService.download(fileId, fileName, contentType);
        }
      );
    }
  }

  /**
   * 検索をリセット
   */
  resetSearch() {
    this.licSearchCondition.resetSearch();
  }
}
