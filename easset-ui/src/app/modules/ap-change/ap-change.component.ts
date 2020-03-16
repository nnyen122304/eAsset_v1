import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ActivatedRoute } from '@angular/router';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-ap-change',
  templateUrl: './ap-change.component.html',
  styleUrls: ['./ap-change.component.scss']
})
export class ApChangeComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 検索画面Index: 移動申請検索
   */
  readonly viewIndexSearch = 5;

  /**
   * 登録画面Index: 移動申請_ライセンス検索結果
   */
  readonly viewIndexLicenseSearchResult = 6;

  /**
   * 登録画面Index: 移動申請登録
   */
  readonly viewIndexEntry = 2;

  /**
   * 履歴画面Index: 移動申請_更新履歴照会
   */
  readonly viewIndexHist = 3;

  /**
   * 履歴画面Index: 移動申請_ライセンス検索
   */
  readonly viewIndexSearchLicense = 4;

  /**
   * 履歴画面Index: 情報機器等検索
   */
  readonly viewIndexAssetSearch = 0;

  /**
   * 履歴画面Index: 情報機器等検索
   */
  readonly viewIndexAssetResult = 1;

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
      case SystemConst.Menu.menuIdApChangeSearch: // 申請 ＞ 取得申請 ＞ 照会/修正
        this.changeChild(this.viewIndexSearch, {
          action: 'initDetail', params: {
            applicationId: 'MH0600175',
          }
        });
        break;
      case SystemConst.Menu.menuIdApChangeCreate1: // 申請 ＞ 移動申請 ＞ 新規作成(現物情報の移動)
        this.changeChild(this.viewIndexAssetSearch, { action: 'init' });
        break;
    }
  }
}
