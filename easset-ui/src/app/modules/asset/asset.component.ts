import { Component, OnInit } from '@angular/core';
import { SessionService } from 'src/app/services/session.service';
import { ActivatedRoute } from '@angular/router';
import { SessionInfo } from 'src/app/models/session-info';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-asset',
  templateUrl: './asset.component.html',
  styleUrls: ['./asset.component.scss']
})
export class AssetComponent extends AbstractMainComponent implements OnInit {
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
   * 履歴画面Index: 情報機器等履歴
   */
  readonly viewIndexHist = 3;

  readonly viewIndexParent = 4;

  /**
   * 履歴画面Index: 情報機器検索結果
   */
  readonly dispIdResult = 5;

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
      case SystemConst.Menu.menuIdAssetSearch: //
      case SystemConst.Menu.menuIdAssetDelete:
        this.changeChild(this.viewIndexSearch, { action: 'init' });
        break;
    }
  }
}
