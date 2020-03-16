import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { ActivatedRoute } from '@angular/router';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-license',
  templateUrl: './license.component.html',
  styleUrls: ['./license.component.scss']
})
export class LicenseComponent extends AbstractMainComponent implements OnInit {
  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 画面識別Index: ライセンス検索
   */
  readonly viewIndexSearch = 0;
  
  /**
   * 画面識別Index:
   */
  readonly viewIndexResult = 1;
  /**
   * 画面識別Index:
   */
  readonly viewIndexList = 2;

  /**
   * 画面識別Index: アップグレードライセンス検索
   */
  readonly viewIndexUpgradeSearch = 3;
  /**
   * 画面識別Index:
   */
  readonly viewIndexUpgradeResult = 4;
  /**
   * 画面識別Index: タームライセンス検索
   */
  readonly viewIndexTermSearch = 5;
  /**
   * 画面識別Index:
   */
  readonly viewIndexTermResult = 6;
  /**
   * 画面識別Index: ライセンス履歴
   */
  readonly viewIndexHist = 7;

  /**
   * ライセンス：取得申請検索
   */
  readonly viewIndexLicenseApGetTanSearch = 8;

  /**
   * ライセンス：取得申請検索
   */
  readonly viewIndexLicenseApGetTanResult = 9;

  readonly viewIndexLicenseMain = 10;

  /**
   * 画面識別Index: 取得申請(無形)検索
   */
  readonly viewIndexApGetIntSearch = 14;

  /**
   * 取得申請(無形)検索結果
   */
  readonly viewIndexApGetIntResult = 15;

  /**
   * 画面識別Index:
   */
  readonly viewIndexEntry = 16;

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
      case SystemConst.Menu.menuIdLicenseSearch: // ライセンス ＞ 照会/修正
      case SystemConst.Menu.menuIdLicenseDelete: // ライセンス ＞ 抹消
        this.changeChild(this.viewIndexSearch, {action: 'init'});
        break;
    }
  }

}
