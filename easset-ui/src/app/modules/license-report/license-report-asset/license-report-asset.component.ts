import { Component, OnInit, ViewChild } from '@angular/core';
import { SessionInfo } from 'src/app/models/session-info';
import { AbstractChildComponent } from 'src/app/components/view-stack/abstract-child-component';
import { LicenseReportComponent } from '../license-report.component';
import { SessionService } from 'src/app/services/session.service';
import { ApAssetService } from 'src/app/services/api/ap-asset.service';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { FileDownloadService } from 'src/app/services/api/file-download.service';
import { DatePipe } from '@angular/common';
import { AssetSearchConditionComponent } from 'src/app/parts/screen/asset/asset-search-condition/asset-search-condition.component';

@Component({
  selector: 'app-license-report-asset',
  templateUrl: './license-report-asset.component.html',
  styleUrls: ['./license-report-asset.component.scss']
})
export class LicenseReportAssetComponent extends AbstractChildComponent<LicenseReportComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * Component Ref
   */
  @ViewChild(AssetSearchConditionComponent, null) assetSearchConditionComponent: AssetSearchConditionComponent;

  constructor(private sessionService: SessionService, private apAssetService: ApAssetService, private datePipe: DatePipe, private fileDownloadService: FileDownloadService) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    this.changeChildSubject.subscribe(param => {
      if (param === 'init') {
        this.assetSearchConditionComponent.changeCondition();
      }
    });
  }

  /**
   * ダウンロード
   */
  download() {
    const loginStaffCode: string = this.sessionInfo.loginUser.staffCode;
    const accessLevel: string = this.sessionInfo.currentAccessLevel;
    const assetSCObj = this.assetSearchConditionComponent.getSearchCondition();

    this.apAssetService.createAllocAssetCsv(loginStaffCode, accessLevel, assetSCObj).subscribe((resp: NonObjectResponse<string>) => {
      const fileId = resp.value;
      const fileName = `ライセンス_管理帳票_割当情報_${this.datePipe.transform(
        new Date(),
        'yyyyMMdd_HHmmss',
      )}.csv`;
      this.fileDownloadService.download(fileId, fileName, 'text/csv');
    });
  }

  /**
   * クリア
   */
  clear() {
    this.assetSearchConditionComponent.clearSearchCondition();
  }
}
