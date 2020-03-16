import { Component, OnInit } from '@angular/core';
import { AbstractMainComponent } from 'src/app/components/view-stack/abstract-main-component';
import { SessionService } from 'src/app/services/session.service';
import { SessionInfo } from 'src/app/models/session-info';
import { ActivatedRoute } from '@angular/router';
import { SystemConst } from 'src/app/const/system-const';

/**
 * 管理画面系親コンポネント
 */
@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.scss']
})
export class AdminComponent extends AbstractMainComponent implements OnInit {

  /**
   * セッション情報
   */
  readonly sessionInfo: SessionInfo = this.sessionService.getSessionInfo();

  /**
   * 画面識別Index: 管理者設定
   */
  readonly viewIndexSetting = 0;

  /**
   * 画面識別Index: 資産管理担当者プロファイル
   */
  readonly viewIndexProfile = 1;

  /**
   * 画面識別Index: 汎用マスタ管理
   */
  readonly viewIndexMaster = 2;

  /**
   * 画面識別Index: 汎用マスタ設定
   */
  readonly viewIndexMasterSetting = 3;

  /**
   * 画面識別Index: ProPlusデータ取込
   */
  readonly viewIndexProplus = 4;

  constructor(private sessionService: SessionService, private route: ActivatedRoute) {
    super(sessionService);
  }

  /**
   * コンポネント初期処理
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
      case SystemConst.Menu.menuIdRoleSetting: // 管理者設定
        this.changeChild(this.viewIndexSetting, {action: 'init'});
        break;
      case SystemConst.Menu.menuIdSectionRoleProfile: // 資産管理担当者プロファイル
        this.changeChild(this.viewIndexProfile, {action: 'init'});
        break;
      case SystemConst.Menu.menuIdCodeNameSetting: // 汎用マスタ管理
        this.changeChild(this.viewIndexMaster, {action: 'init'});
        break;
      case SystemConst.Menu.menuIdPpfsImport: // ProPlusデータ取込
        this.changeChild(this.viewIndexProplus, {action: 'init'});
        break;
    }
  }

}
