import { Component, Output, EventEmitter, ViewChildren, AfterViewChecked, QueryList, ViewChild, Input } from '@angular/core';
import { SessionInfo } from 'src/app/models/session-info';
import { CodeName } from 'src/app/models/api/code-name';
import * as input from 'wijmo/wijmo.input';
import { WjMenu, WjMenuItem } from 'wijmo/wijmo.angular2.input';
import { MenuItem } from 'src/app/models/menu-item';

import { PpfsImportService } from 'src/app/services/api/ppfs-import.service';

import { PpfsStat } from 'src/app/models/api/ppfs-import/ppfs-stat';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements AfterViewChecked {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * 会社ドロップダウンデータ
   */
  usableCompanyList: CodeName[];

  /**
   * ログイン会社
   */
  loginCompanyCode: string;

  /**
   * メニューデータ
   */
  menuData: MenuItem[];

  /**
   * 社員サービス
   */
  ppfsImportService: PpfsImportService;

  /**
   * 会計年月
   */
  accountingDate: string;

  /**
   * フレームタイトル
   */
  @Input() frameTitle: string;

  /**
   * メニュー表示フラグ
   */
  @Input() menuDisplayFlag: boolean;

  /**
   * フレームタイトル表示フラグ
   */
  @Input() windowTitleDisplayFlag: boolean;

  /**
   * 会社切替
   */
  @Output() changeCompany: EventEmitter<string> = new EventEmitter<string>();

  /**
   * メニュー選択
   */
  @Output() selectMenu: EventEmitter<MenuItem> = new EventEmitter<MenuItem>();

  /**
   * メニューアイテム
   */
  @ViewChildren(WjMenu) private menuComponentList: QueryList<WjMenu>;

  constructor(ppfsImportService: PpfsImportService) {
    this.ppfsImportService = ppfsImportService;
    this.accountingDate = '';
  }

  /**
   * メニュー描画を捕捉してクリックイベントを設定
   */
  ngAfterViewChecked(): void {
    this.menuComponentList.forEach((menu: WjMenu) => {
      if (menu.itemClicked.handlerCount === 1) {
        menu.itemClicked.addHandler(this.menuItemClicked.bind(this));
      }
    });

  }

  /**
   * セッション情報が設定された際に画面情報を設定
   * @param sessionInfo セッション情報
   * @param menuData メニュー情報
   * @param usableCompanyList 利用可能会社一覧
   */
  setHeaderInfo(sessionInfo: SessionInfo, menuData: MenuItem[], usableCompanyList: CodeName[]): void {
    this.sessionInfo = sessionInfo;
    if (!this.usableCompanyList && !this.loginCompanyCode) {
      this.loginCompanyCode = this.sessionInfo.loginCompanyCode;
      this.usableCompanyList = usableCompanyList;
    }

    if (this.loginCompanyCode !== sessionInfo.loginCompanyCode) {
      this.loginCompanyCode = sessionInfo.loginCompanyCode;
    }

    this.ppfsImportService.getPpfsStatList(this.loginCompanyCode, '1').subscribe((data: PpfsStat[]) => {
      const latestPeriod = data[0].period;
      this.accountingDate = latestPeriod.substring(0, 4) + '-' + latestPeriod.substring(4, 6);
    });

    this.menuData = menuData;
  }

  /**
   * 会社切替イベント起動
   * 指定会社でログインしなおす
   */
  changeCompanyList(): void {
    if (this.loginCompanyCode && this.loginCompanyCode !== this.sessionInfo.loginCompanyCode) {
      this.changeCompany.emit(this.loginCompanyCode);
    }
  }

  /**
   * メニュー選択
   * @param menu 選択されたメニュー
   */
  menuItemClicked(menu: input.Menu): void {
    this.selectMenuExec(menu.selectedItem);
  }

  /**
   * ルートメニュー選択
   * @param menuItem 選択されたメニューデータ
   */
  rootMenuItemClicked(menuItem: MenuItem): void {
    this.selectMenuExec(menuItem);
  }

  /**
   * メニュー選択イベント起動
   * @param menuItem 選択されたメニュー
   */
  private selectMenuExec(menuItem: MenuItem): void {
    if (menuItem && menuItem.path) {
      this.selectMenu.emit(menuItem);
    }
  }

}
