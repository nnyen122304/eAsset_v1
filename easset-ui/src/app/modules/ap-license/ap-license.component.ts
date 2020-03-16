import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ActivatedRoute } from '@angular/router';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-ap-license',
  templateUrl: './ap-license.component.html',
  styleUrls: ['./ap-license.component.scss']
})
export class ApLicenseComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * vi ap get tan search screen
   */
  readonly viewIndexApLicenseApGetTanSearch = 0;

  /**
   * vi ap get tan search screen
   */
  readonly viewIndexApLicenseApGetTanResult = 1;

  /**
   * 取得申請(無形)検索
   */
  readonly viewIndexApLicenseApGetIntSearch = 20;
  /**
   * 取得申請(無形)検索結果
   */
  readonly viewIndexApLicenseApGetIntResult = 5;

  /**
   * 検索画面Index: ライセンス登録申請検索
   */
  readonly viewIndexApLicenseSearch = 0;

  /**
   * 検索画面Index:
   */
  readonly viewIndexResult = 5;

  /**
   * 検索画面Index:
   */
  readonly viewIndexEntry = 6;

  /**
   * 検索画面Index: ライセンス登録申請履歴
   */
  readonly viewIndexHist = 7;

  readonly viewIndexApLicenseMain = 8;

  /**
   * 検索画面Index:
   */
  readonly viewAPGetTanResultLine = 9;

  constructor(private sessionService: SessionService, private route: ActivatedRoute) {
    super(sessionService);
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    // メニューに応じた初期処理、子画面表示
    this.updateView();
  }

  /**
   * メニューに応じた初期処理、子画面表示
   */
  updateView() {
    const paramMap = this.route.snapshot.paramMap; // オープンパラメータ
    // メニューに応じた初期処理、子画面表示
    switch (this.sessionInfo.currentMenuId) {
      case SystemConst.Menu.menuIdApLicenseCopy: // 登録申請 ＞ ライセンス ＞ コピー(既存申請コピー)
      case SystemConst.Menu.menuIdApLicenseSearch: // 登録申請 ＞ ライセンス ＞ 照会/修正
        this.changeChild(this.viewIndexApLicenseSearch, { action: 'init' });
        break;
    }
  }
}
