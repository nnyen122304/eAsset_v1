import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { SystemConst } from 'src/app/const/system-const';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-ap-end-re',
  templateUrl: './ap-end-re.component.html',
  styleUrls: ['./ap-end-re.component.scss']
})
export class ApEndReComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 検索画面Index
   */
  readonly viewIndexSearch = 0;

  /**
   * 登録画面Index
   */
  readonly viewIndexResult = 1;

  /**
   * 登録画面Index
   */
  readonly viewIndexEntry = 2;

  /**
   * 履歴画面Index: レンタル買取申請_履歴
   */
  readonly viewIndexHist = 3;

  /**
   * 履歴画面Index 申請対象情報機器等検索
   */
  readonly viewIndexAstSearch = 4;

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
      case SystemConst.Menu.menuIdApEndReSearch:
        this.changeChild(this.viewIndexAstSearch, { action: 'init' });
        break;
    }
  }
}
