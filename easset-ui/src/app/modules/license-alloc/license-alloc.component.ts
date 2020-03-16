import { Component, OnInit } from '@angular/core';
import { SessionInfo } from 'src/app/models/session-info';
import { SessionService } from 'src/app/services/session.service';
import { ActivatedRoute } from '@angular/router';
import { SystemConst } from 'src/app/const/system-const';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';

@Component({
  selector: 'app-license-alloc',
  templateUrl: './license-alloc.component.html',
  styleUrls: ['./license-alloc.component.scss']
})
export class LicenseAllocComponent extends AbstractMainComponent implements OnInit {
  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 画面識別Index: 情報機器等検索
   */
  readonly viewIndexAllocAssetSearch = 0;

  /**
   * 画面識別Index: 情報機器等検索
   */
  readonly viewIndexAllocAssetResult = 1;

  /**
   * 画面識別Index: ライセンス検索
   */
  readonly viewIndexAllocLicenseSearch = 4;
  /**
   * 画面識別Index: ライセンス検索結果
   */
  readonly viewIndexLicenseResult = 5;

  /**
   * 画面識別Index: 割当
   */
  readonly viewIndexAlloc = 6;
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
      case SystemConst.Menu.menuIdLicenseAlloc:
        this.changeChild(this.viewIndexAllocAssetSearch, { action: 'init' });
        break;
    }
  }

}
