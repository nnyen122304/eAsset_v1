import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionService } from 'src/app/services/session.service';
import { ActivatedRoute } from '@angular/router';
import { SessionInfo } from 'src/app/models/session-info';
import { SystemConst } from 'src/app/const/system-const';

@Component({
  selector: 'app-ap-asset',
  templateUrl: './ap-asset.component.html',
  styleUrls: ['./ap-asset.component.scss']
})
export class ApAssetComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 検索画面Index:取得申請検索
   */
  readonly viewIndexApGetTanSearch = 0;

  /**
   * 検索結果画面Index:取得申請検索結果
   */
  readonly viewIndexApGetTanResult = 1;

  /**
   * 検索結果画面Index:取得申請明細選択
   */
  readonly viewIndexApGetTanResultLine = 2;

  /**
   * 登録画面Index:登録
   */
  readonly viewIndexEntry = 3;

  /**
   * 履歴画面Index
   */
  readonly viewIndexHist = 4;

  /**
   * 検索画面Index:
   */
  readonly viewIndexSearch = 5;

  /**
   * 登録画面Index
   */
  readonly viewIndexResult = 6;

  /**
   * 履歴画面Index 仮想機器実機検索
   */
  readonly viewIndexAssetVmSearch = 7;

  /**
   * 履歴画面Index 仮想機器実機検索
   */
  readonly viewIndexAssetVmResult = 8;


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
      case SystemConst.Menu.menuIdApAssetCreate1: // 申請 ＞ 登録申請 ＞ 情報機器等 ＞ 新規作成
        this.changeChild(this.viewIndexEntry, {
          action: 'intCreate',
          params: {loadApGetTanLineAstId: '140883'}
        });
        break;
      case SystemConst.Menu.menuIdApAssetCreate2: // 申請 ＞ 登録申請 ＞ 情報機器等 ＞ 新規作成(仮想機器)
        this.changeChild(this.viewIndexAssetVmSearch, {action: 'init'});
        break;
      case SystemConst.Menu.menuIdApAssetSearch: // 申請 ＞ 登録申請 ＞ 情報機器等 ＞ 照会/修正
        this.changeChild(this.viewIndexSearch, {action: 'init'});
        break;
    }
  }
}
