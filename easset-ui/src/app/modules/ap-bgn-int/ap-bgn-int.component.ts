import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { SystemConst } from 'src/app/const/system-const';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ap-bgn-int',
  templateUrl: './ap-bgn-int.component.html',
  styleUrls: ['./ap-bgn-int.component.scss']
})
export class ApBgnIntComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 取得申請(無形)検索Index
   */
  readonly viewIndexApGetIntSearch = 0;

  /**
   * 取得申請(無形)検索結果Index
   */
  readonly viewIndexApGetIntResult = 1;


  /**
   * 登録画面Index
   */
  readonly viewIndexEntry = 2;

  /**
   * 検索画面Index
   */
  readonly viewIndexSearch = 3;

  /**
   * 登録画面Index
   */
  readonly viewIndexResult = 4;

  /**
   * 履歴画面Index
   */
  readonly viewIndexHist = 5;

  constructor(private sessionService: SessionService, private route: ActivatedRoute) {
    super(sessionService);
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {

    /**
     * メニューに応じた初期処理、子画面表示
     */
    this.updateView();
  }

  /**
   * メニューに応じた初期処理、子画面表示
   */
  updateView() {
    const paramMap = this.route.snapshot.paramMap; // オープンパラメータ
    switch (this.sessionInfo.currentMenuId) {
      case SystemConst.Menu.menuIdApBgnIntCreate:
        this.changeChild(this.viewIndexApGetIntSearch, {
          action: 'init'
        });
        break;
      case SystemConst.Menu.menuIdApBgnIntSearch:
        this.changeChild(this.viewIndexSearch, {
          action: 'init',
          params: {
            loadApGetIntId: 'AS1300070',
            loadApGetIntVersion: '01'
          }
        });
        break;
    }
  }
}
