import { Component, OnInit, ViewChild, SystemJsNgModuleLoader } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { ActivatedRoute } from '@angular/router';
import { SystemConst } from 'src/app/const/system-const';

/**
 * リファレンス実装-メイン画面
 */
@Component({
  selector: 'app-ref-impl',
  templateUrl: './ref-impl.component.html',
  styleUrls: ['./ref-impl.component.scss']
})
export class RefImplComponent extends AbstractMainComponent implements OnInit {
  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 画面識別Index:検索
   */
  readonly viewIndexSearch = 0;
  /**
   * 画面識別Index:検索結果
   */
  readonly viewIndexResult = 1;
  /**
   * 画面識別Index:詳細
   */
  readonly viewIndexEntry = 2;

  constructor(private sessionService: SessionService, private route: ActivatedRoute) {
    super(sessionService);
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    const paramMap = this.route.snapshot.paramMap; // オープンパラメータ

    // メニューに応じた初期処理、子画面表示
    switch (this.sessionInfo.currentMenuId) {
      case SystemConst.Menu.menuIdRefImpl: // リファレンス実装
        this.changeChild(this.viewIndexSearch, {action: 'init'});
        break;
      case SystemConst.Menu.Eapp.menuIdRefImplEapp: // e申請用画面
        this.changeChild(this.viewIndexEntry, {action: 'eapp', params: {eappId: paramMap.get('param2')}});
        break;
      case SystemConst.Menu.Ref.menuIdRefImplRef: // 別Window参照用
        this.changeChild(this.viewIndexEntry, {action: 'ref', params: {id: paramMap.get('id')}});
        break;
    }
  }
}
