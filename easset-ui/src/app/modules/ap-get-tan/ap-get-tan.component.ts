import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { SystemConst } from 'src/app/const/system-const';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-ap-get-tan',
  templateUrl: './ap-get-tan.component.html',
  styleUrls: ['./ap-get-tan.component.scss']
})
export class ApGetTanComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 検索画面Index
   */
  readonly viewIndexSearch = 3;

  /**
   * 登録画面Index
   */
  readonly viewIndexResult = 1;

  /**
   * 登録画面Index
   */
  readonly viewIndexEntry = 2;

  /**
   * 履歴画面Index 情報機器等検索
   */
  readonly viewIndexAssetSearch = 0;

  /**
   * 履歴画面Index: 取得申請履歴
   */
  readonly viewIndexHist = 4;

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
    switch (this.sessionInfo.currentMenuId) {
      case SystemConst.Menu.menuIdApGetTanSearch: // 申請 ＞ 取得申請 ＞ 照会/修正
        this.changeChild(this.viewIndexSearch, { action: 'init' });
        break;
      case SystemConst.Menu.menuIdApGetTanSearch: // 申請 ＞ 取得申請 ＞ 照会/修正
        this.changeChild(this.viewIndexAssetSearch, { action: 'init-asset-search' });
        break;
      case SystemConst.Menu.menuIdApGetTanCreate1:
      case SystemConst.Menu.menuIdApGetTanCreate2:
      case SystemConst.Menu.menuIdApGetTanCreate3:
      case SystemConst.Menu.menuIdApGetTanCreate4:
        this.changeChild(this.viewIndexEntry, {
          action: 'init',
        });
        break;
    }
  }
}
