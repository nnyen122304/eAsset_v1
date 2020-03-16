import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { ActivatedRoute } from '@angular/router';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-license-report',
  templateUrl: './license-report.component.html',
  styleUrls: ['./license-report.component.scss']
})
export class LicenseReportComponent extends AbstractMainComponent implements OnInit {
  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 画面識別Index: 割当情報(ライセンスから検索)
   */
  readonly viewIndexReportAllocLicense = 0;

  /**
   * 画面識別Index: 割当情報(機器から検索)
   */
  readonly viewIndexReportAllocAsset = 1;

  /**
   * 画面識別Index: アップグレード情報
   */
  readonly viewIndexReportUpgrade = 2;


  constructor(private sessionService: SessionService, private route: ActivatedRoute) {
    super(sessionService);
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.updateView();
  }

  /**
   * ビュー更新
   */
  updateView() {
    const paramMap = this.route.snapshot.paramMap; // オープンパラメータ
    // メニューに応じた初期処理、子画面表示
    switch (this.sessionInfo.currentMenuId) {
      case SystemConst.Menu.menuIdLicenseReportAllocLicense: // 割当情報(ライセンスから検索)
        this.changeChild(this.viewIndexReportAllocLicense, { action: 'init' });
        break;
      case SystemConst.Menu.menuIdLicenseReportAllocAsset: // 割当情報(機器から検索)
        this.changeChild(this.viewIndexReportAllocAsset, { action: 'init' });
        break;
      case SystemConst.Menu.menuIdLicenseReportUpgrade: // アップグレード情報
        this.changeChild(this.viewIndexReportUpgrade, { action: 'init' });
        break;
    }
  }
}
