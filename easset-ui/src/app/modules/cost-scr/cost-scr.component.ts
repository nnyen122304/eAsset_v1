import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionService } from 'src/app/services/session.service';
import { ActivatedRoute } from '@angular/router';
import { SessionInfo } from 'src/app/models/session-info';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-cost-scr',
  templateUrl: './cost-scr.component.html',
  styleUrls: ['./cost-scr.component.scss']
})
export class CostScrComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 画面識別Index: 経費負担部課精査データ作成
   */
  readonly viewIndexCreate = 0;

  /**
   * 画面識別Index: 期首移行 ＞ 経費負担部課精査
   */
  readonly viewIndexList = 1;
  /**
   * 画面識別Index: 期首移行 ＞ 経費負担部課精査
   */
  readonly viewIndexLine = 2;

  constructor(private sessionService: SessionService, private route: ActivatedRoute) {
    super(sessionService);
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.updateView();
  }

  updateView() {
    const paramMap = this.route.snapshot.paramMap; // オープンパラメータ
    // メニューに応じた初期処理、子画面表示
    switch (this.sessionInfo.currentMenuId) {
      case SystemConst.Menu.menuIdCostScrCreate: // 経費負担部課精査データ作成
        this.changeChild(this.viewIndexCreate, { action: 'init' });
        break;
      case SystemConst.Menu.menuIdCostScr: // 経費負担部課精査
        this.changeChild(this.viewIndexList, { action: 'init' });
        break;
      case SystemConst.Menu.Ref.menuIdInvRef: // 経費負担部課精査
        this.changeChild(this.viewIndexLine, { action: 'initRef', params: paramMap });
        break;
    }
  }
}
