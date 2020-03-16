import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { ActivatedRoute } from '@angular/router';
import { SystemConst } from 'src/app/const/system-const';

/**
 * 棚卸画面系親コンポネント
 */
@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss']
})
export class InventoryComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 画面識別Index: 棚卸データ作成
   */
  readonly viewIndexCreate = 0;

  /**
   * 画面識別Index: 棚卸一覧 > 保有部署別一覧
   */
  readonly viewIndexList = 1;

  /**
   * 画面識別Index: 棚卸一覧 > e申請
   */
  readonly viewIndexListEApplication = 2;

  /**
   * 画面識別Index: 棚卸一覧 > 資産別一覧
   */
  readonly viewIndexListAsset = 3;

  /**
   * 画面識別Index: 棚卸データ一括更新
   */
  readonly viewIndexUpdate = 4;


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
      case SystemConst.Menu.menuIdInvCreate: // 棚卸データ作成
        this.changeChild(this.viewIndexCreate, {action: 'init'});
        break;
      case SystemConst.Menu.menuIdInv: // 棚卸一覧
        this.changeChild(this.viewIndexList, {action: 'init'});
        break;
      case SystemConst.Menu.Ref.menuIdInvRef: // 棚卸一覧 > 資産別一覧別ウインドウ
        this.changeChild(this.viewIndexListAsset, {action: 'initRef', params: paramMap});
        break;
      case SystemConst.Menu.menuIdInvBulkUpdate: // 棚卸データ一括更新
        this.changeChild(this.viewIndexUpdate, {action: 'init'});
        break;
      case SystemConst.Menu.Eapp.menuIdInvEapp: // e申請用
        this.changeChild(this.viewIndexListEApplication, {action: 'init', params: paramMap});
        break;
    }
  }

}
