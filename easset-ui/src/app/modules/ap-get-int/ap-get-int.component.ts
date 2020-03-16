import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { SystemConst } from 'src/app/const/system-const';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-ap-get-int',
  templateUrl: './ap-get-int.component.html',
  styleUrls: ['./ap-get-int.component.scss']
})

export class ApGetIntComponent extends AbstractMainComponent implements OnInit {
  readonly stateMenuApGetInt = {
    menuIdApGetIntSearch: 'AP_GET_INT',
    menuIdApGetIntCopy: 'AP_GET_INT',
    menuIdApGetIntEdit1: 'AP_GET_INT_UPDATE1',
    menuIdApGetIntEdit2: 'AP_GET_INT_UPDATE2',
  };

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 登録画面Index
   */
  readonly viewIndexCreate1 = 0;

  /**
   * 登録画面Index
   */
  readonly viewIndexCreate2 = 1;

  /**
   * 登録画面Index
   */
  readonly viewIndexCreate3 = 2;

  /**
   * 検索画面Index
   */
  readonly viewIndexSearch = 3;

  /**
   * 登録画面Index
   */
  readonly viewIndexResult = 4;

  /**
   * 履歴画面Index: 取得申請(無形)履歴
   */
  readonly viewIndexHist = 3;

  /**
   * vi ap get tan search screen
   */
  readonly viewIndexApGetIntApGetTanSearch = 4;

  readonly viewIndexApGetIntMain = 5;

  readonly viewIndexEntry = 10;

  readonly viewIndexApGetIntApGetTanResult = 9;

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
      case SystemConst.Menu.menuIdApGetIntCreate1:
        this.changeChild(this.viewIndexCreate1, {action: 'init'});
        break;
      case SystemConst.Menu.menuIdApGetIntCreate2:
        this.changeChild(this.viewIndexCreate2, {action: 'init'});
        break;
      case SystemConst.Menu.menuIdApGetIntCreate3:
        this.changeChild(this.viewIndexCreate3, {action: 'init'});
        break;
      case SystemConst.Menu.menuIdApGetIntSearch:
      case SystemConst.Menu.menuIdApGetIntEdit1:
      case SystemConst.Menu.menuIdApGetIntEdit2:
      case SystemConst.Menu.menuIdApGetIntCopy:
        let currentState = this.stateMenuApGetInt.menuIdApGetIntSearch;
        if (this.sessionInfo.currentMenuId === SystemConst.Menu.menuIdApGetIntEdit1) {
          currentState = this.stateMenuApGetInt.menuIdApGetIntEdit1;
        } else if (this.sessionInfo.currentMenuId === SystemConst.Menu.menuIdApGetIntEdit2) {
          currentState = this.stateMenuApGetInt.menuIdApGetIntEdit2;
        } else if (this.sessionInfo.currentMenuId === SystemConst.Menu.menuIdApGetIntCopy) {
          currentState = this.stateMenuApGetInt.menuIdApGetIntCopy;
        }
        this.changeChild(this.viewIndexSearch, {
          action: 'init',
          params: {
            currentState: currentState
          }
        });
        break;
    }
  }
}
