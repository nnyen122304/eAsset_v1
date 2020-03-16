import { Component, OnInit, ViewChild } from '@angular/core';

import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';

import { SessionService } from 'src/app/services/session.service';

import { SessionInfo } from 'src/app/models/session-info';
import { SystemConst } from 'src/app/const/system-const';

/**
 * サイトマップコンポネント
 */

@Component({
  selector: 'app-site-map',
  templateUrl: './site-map.component.html',
  styleUrls: ['./site-map.component.scss']
})
export class SiteMapComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 画面識別Index:サイトマップ
   */
  readonly viewIndexResult = 0;

  constructor(private sessionService: SessionService) {
    super(sessionService);
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    // メニューに応じた初期処理、子画面表示
    switch (this.sessionInfo.currentMenuId) {
      case SystemConst.Menu.menuIdSiteMap: // サイトマップ
        this.changeChild(this.viewIndexResult, {action: 'init'});
        break;
    }
  }

}
