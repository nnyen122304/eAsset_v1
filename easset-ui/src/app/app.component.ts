import { Component, ViewChild, OnInit, AfterViewInit } from '@angular/core';
import { Idle, DEFAULT_INTERRUPTSOURCES } from '@ng-idle/core';
import { Router } from '@angular/router';
import { HttpParams } from '@angular/common/http';

import { CellType } from 'wijmo/wijmo.grid';
import { Popup, PopupTrigger } from 'wijmo/wijmo.input';
import { EaFlexGrid } from './components/ea-flex-grid/ea-flex-grid.component';

import { HeaderComponent } from './parts/header/header.component';
import { MessagePopupComponent } from './parts/message-popup/message-popup.component';
import { ConfirmPopupComponent } from './parts/confirm-popup/confirm-popup.component';

import { WjPopup } from 'wijmo/wijmo.angular2.input';
import { MessageService } from './services/message.service';
import { SessionService } from './services/session.service';

import { SessionInfo } from './models/session-info';
import { SessionInfoEx } from './models/session-info-ex';
import { MasterService } from './services/api/master.service';
import { CodeName } from './models/api/code-name';
import { MenuItem } from './models/menu-item';

import { environment } from '../environments/environment';
import { SystemConst } from './const/system-const';
import { SystemMessage } from './const/system-message';

/**
 * アプリケーションメイン画面
 */
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit, AfterViewInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfoEx;
  /**
   * 全社権限一覧
   */
  roleAdminList: CodeName[];
  /**
   * ログイン会社
   */
  loginCompanyCode: string;
  /**
   * 使用可能会社一覧
   */
  usableCompanyList: CodeName[];
  /**
   * フレームタイトル
   */
  frameTitle: string;
  /**
   * メニュー表示フラグ(アプリ起動時QueryParam)
   */
  menuDisplayFlag: boolean;
  /**
   * フレームタイトル表示フラグ(アプリ起動時QueryParam)
   */
  windowTitleDisplayFlag: boolean;
  /**
   * メニューデータ
   */
  private menuData: MenuItem[];

  /**
   * 要求メニューを保存
   */
  private requestedMenu: MenuItem;

  /**
   * 要求会社コードを保存
   */
  private requestedCompanyCode: string;

  /**
   * メニューIDとメニューデータのマップ
   */
  private menuDataMap = new Map<string, MenuItem>();

  /**
   * アプリケーション起動時のHttpParam
   */
  private httpParams: HttpParams;

  /**
   * 画面ヘッダー
   */
  @ViewChild('header') public header: HeaderComponent;

  /**
   * 権限選択ポップアップ
   */
  @ViewChild('selectAuthPopup') public selectAuthPopup: WjPopup;

  /**
   * 権限選択グリッド
   */
  @ViewChild('selectAuthGrid') public selectAuthGrid: EaFlexGrid;

  /**
   * エラーメッセージポップアップ Ref
   */
  @ViewChild('popupMessage') public popupMessage: MessagePopupComponent;

  /**
   * メニュー選択後確認ポップアップ Ref
   */
  @ViewChild('confirmPopupMenu') public confirmPopupMenu: ConfirmPopupComponent;

  /**
   * 会社選択後確認ポップアップ Ref
   */
  @ViewChild('confirmPopupComp') public confirmPopupComp: ConfirmPopupComponent;

  constructor(
    private idle: Idle, private router: Router,
    private sessionService: SessionService,
    private masterService: MasterService,
    public messageService: MessageService
  ) {
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit(): void {
    // ブラウザの戻る操作は初期画面に戻るようにする
    const initHref = window.location.href;
    window.addEventListener('popstate', (e) => {
      console.log('戻るボタンクリックのため初期画面表示');
      window.location.href = initHref;
      return;
    });

    // セッションタイムアウトの設定
    this.idle.setIdle(environment.sessionTimeout);
    this.idle.setTimeout(environment.sessionTimeout);
    this.idle.setInterrupts(DEFAULT_INTERRUPTSOURCES);
    this.idle.onTimeout.subscribe(() => {
      console.log('セッションタイムアウトのため初期画面表示');
      window.location.href = initHref;
    });
    this.idle.watch();

    // アプリケーション起動時のクエリパラメータを取得（ActivatedRouteを使用するとモジュール初期化のタイミングの関係か、このタイミングでの取得が出来ない）
    const params = window.location.search;
    const httpParams = new HttpParams({fromString: params.split('?')[1]});
    this.menuDisplayFlag = httpParams.get('menuDisplayFlag') === '0' ? false : true;
    this.windowTitleDisplayFlag = httpParams.get('windowTitleDisplayFlag') === '0' ? false : true;
    this.loginUser(httpParams);
    if (httpParams.get('menuId')) {
      document.querySelector('body').classList.add('site');
    }
  }

  ngAfterViewInit(): void {
    this.messageService.setPopupRef(this.popupMessage);
    if (document.querySelector('.icon-info')) {
      document.querySelector('.icon-info').addEventListener('click', () => {
        this.popupMessage.open(document.activeElement);
      });
    }
  }

  /**
   * 会社切替
   * @param companyCode 会社コード
   */
  changeCompany(companyCode: string): void {
    if (companyCode && this.sessionInfo && this.sessionInfo.loginCompanyCode && this.sessionInfo.loginCompanyCode !== companyCode) {
      if (this.sessionService.getDataChange()) {
        this.requestedCompanyCode = companyCode;
        this.confirmPopupComp.prompt(SystemMessage.Conf.msg400001, document.activeElement);
      } else {
        this.sessionService.setDataChange(false);
        this.loginCompany(companyCode);
      }
    }
    setTimeout(() => {
      if (this.selectAuthGrid) {
        this.selectAuthGrid.resetSelection();
      }
    }, 500);
  }

  /**
   * 画面遷移を許可する
   * @param menu メニュー項目
   */
  acceptChange(menu: MenuItem) {
    this.sessionService.setDataChange(false);
    this.sessionService.setCurrentMenu(menu.menuId);

    // httpパラメータ付き画面オープンの場合はパラメータを付加
    let commands = [];
    if (this.httpParams && this.httpParams.keys().length > 0) {
      const params = {};
      this.httpParams.keys().forEach((key) => {
        params[key] = this.httpParams.getAll(key);
      });
      commands = [menu.path, params];
    } else {
      commands = [menu.path];
    }

    // 画面遷移
    this.router.navigate(['']).then(v => { // 前回と同様のパスでも機能メイン画面の初期処理が実行されるように一度空遷移
      this.router.navigate(commands).then(value => {
        this.frameTitle = menu.frameTitle;
      }, (reason) => {
        this.sessionService.setCurrentMenu(undefined);
        this.messageService.err(reason);
      });
    });
  }

  /**
   * メニュー選択
   * @param menu メニュー
   */
  selectMenu(menu: MenuItem, skipConfirm?: boolean): void {
    if (this.sessionService.getDataChange()) {
      this.requestedMenu = menu;
      this.confirmPopupMenu.prompt(SystemMessage.Conf.msg400001, document.activeElement);
    } else {
      this.acceptChange(menu);
    }
  }

  /**
   * メニュー選択後の確認ポップアップをOKした場合
   */
  onConfirmMenu() {
    this.acceptChange(this.requestedMenu);
  }

  /**
   * 会社選択後の確認ポップアップをOKした場合
   */
  onConfirmCompany() {
    this.sessionService.setDataChange(false);
    this.loginCompany(this.requestedCompanyCode);
  }

  /**
   * グリッド初期処理
   * @param flexGrid グリッド
   */
  initializeGrid(flexGrid: EaFlexGrid): void {
    // イベント：ダブルクリック
    flexGrid.addEventListener(flexGrid.hostElement, 'dblclick', (e: MouseEvent) => {
      const ht = flexGrid.hitTest(e);
      if (ht.cellType === CellType.Cell) {
        this.selectAuth();
      }
    });
    flexGrid.resetSelection();
  }

  /**
   * 権限選択
   */
  selectAuth(): void {
    const row = this.selectAuthGrid.selection.row;
    const roleCode = this.selectAuthGrid.cells.getCellData(row, 0, true);
    const roleName = this.selectAuthGrid.cells.getCellData(row, 1, true);
    if (roleCode === 'X') {
      this.setSectionRole();
    } else {
      this.setAccessLevel(roleCode.split(','), roleName);
    }
    this.selectAuthPopup.hide();
  }

  /**
   * ユーザーログイン
   * @param httpParams アプリ起動時のクエリパラメータ
   */
  private loginUser(httpParams: HttpParams): void {
    // ユーザー情報取得
    this.sessionService.login().subscribe((sessionInfo: SessionInfo) => {
      this.sessionInfo = sessionInfo;
      let companyCode = httpParams.get('companyCode');
      if (!companyCode) {
        companyCode = sessionInfo.loginUser.companyCode;
      }
      // 使用可能会社一覧取得
      this.masterService.getUsableCompanyList(sessionInfo.loginUser.staffCode).subscribe((codeNameList: CodeName[]) => {
        this.usableCompanyList = codeNameList;
        // 検索結果最大件数取得
        this.masterService.getCodeName('MAX_SEARCH_ROW_COUNT').subscribe((codeNameMaxRow: CodeName) => {
          sessionInfo.maxSearchRowCount = parseInt(codeNameMaxRow.value1, 0);
          // カレント年度取得
          this.masterService.getCodeName('CURRENT_YEAR').subscribe((codeNameYear: CodeName) => {
            sessionInfo.currentYear = parseInt(codeNameYear.value1, 0);
            // ファイル保存ルートパス取得
            this.masterService.getCodeName('FILE_SAVE_ROOT_PATH').subscribe((codeNameRootPath: CodeName) => {
              sessionInfo.fileSaveRootPath = codeNameRootPath.value1;
              this.loginCompany(companyCode, httpParams);
            });
          });
        });
      });
    });
  }

  /**
   * 会社ログイン（切替）
   * @param companyCode 会社コード
   * @param httpParams アプリ起動時のクエリパラメータ
   */
  private loginCompany(companyCode: string, httpParams?: HttpParams) {
    this.httpParams = httpParams;
    this.loginCompanyCode = companyCode;
    this.sessionInfo.loginCompanyCode = companyCode;

    // 会社名取得
    this.masterService.getCodeName('USE_COMPANY', companyCode).subscribe(companyCodeName => {
      this.sessionInfo.loginCompanyName = companyCodeName.value1;

      // 全社権限取得
      this.masterService.getRoleAdminList(this.sessionInfo.loginUser.staffCode, companyCode)
        .subscribe((codeNameListRoleAdmin: CodeName[]) => {
          if (codeNameListRoleAdmin.length > 0) {
            // 全社権限あり
            this.roleAdminList = codeNameListRoleAdmin.filter(codeName => (!codeName.value2));

            // 総務・財経・情シスの権限は１行表示に
            if (codeNameListRoleAdmin.filter(codeName => (codeName.value2)).length > 0) {
              const mergeRoleCode = codeNameListRoleAdmin
                .filter(codeName => (codeName.value2)).map(codeName => codeName.internalCode).reduce((v1, v2) => v1 + ',' + v2);
              const mergeRoleName1 = codeNameListRoleAdmin
                .filter(codeName => (codeName.value2)).map(codeName => codeName.value1).reduce((v1, v2) => v1);
              const mergeRoleName2 = codeNameListRoleAdmin
                .filter(codeName => (codeName.value2)).map(codeName => codeName.value2).reduce((v1, v2) => v1 + ',' + v2);
              const mergeRole = new CodeName();
              mergeRole.internalCode = mergeRoleCode;
              mergeRole.value1 = mergeRoleName1 + '(' + mergeRoleName2 + ')';

              this.roleAdminList.push(mergeRole);
            }

            // 各部/一般
            const sectionRole = new CodeName();
            sectionRole.internalCode = 'X';
            sectionRole.value1 = '各部/一般';

            this.roleAdminList.push(sectionRole);

            this.roleAdminList.sort((v1, v2) => (v1.internalCode < v2.internalCode) ? -1 : 1);

            // 権限選択
            if (httpParams && httpParams.get('currentRoles') === 'auto') { // ロール自動選択
              const autoRole = this.roleAdminList[0];
              this.setAccessLevel(autoRole.internalCode.split(','), autoRole.value1);
            } else if (httpParams && httpParams.get('currentRoles') // ロール指定有り
              && httpParams.get('currentRoles') !== '' && httpParams.get('currentRoles') !== 'auto') {
              if (this.roleAdminList.filter(item => item.internalCode === httpParams.get('currentRoles')).length > 0) {
                // 指定ロールの権限あり
                this.setAccessLevel(httpParams.get('currentRoles').split(','), httpParams.get('currentRoleName'));
              } else {
                // 指定ロールの権限無し-各部/一般権限を取得する
                this.setSectionRole();
              }
            } else { // ロール選択表示
              this.selectAuthPopup.hideTrigger = PopupTrigger.None;
              this.selectAuthPopup.show(true, (sender: Popup) => {
              });
            }
          } else {
            // 各部/一般権限取得
            this.setSectionRole();
          }
        });
    });
  }

  /**
   * 各部権限設定
   */
  private setSectionRole(): void {
    this.masterService.getRoleSectionList(this.sessionInfo.loginUser.staffCode, this.sessionInfo.loginCompanyCode)
      .subscribe((codeNameListRoleSection: CodeName[]) => {
        this.masterService.getCodeName('ROLE', null, null, [null, null, '1']).subscribe((codeName: CodeName) => {
          const loginRoleCodeList = codeNameListRoleSection.map(item => item.internalCode).concat(codeName.internalCode);
          let loginRoleName: string;
          if (codeNameListRoleSection.length === 0) {
            loginRoleName = codeName.value1;
          } else {
            loginRoleName = codeNameListRoleSection.map(item => item.value1).reduce((v1, v2) => v1 + '/' + v2);
          }
          this.setAccessLevel(loginRoleCodeList, loginRoleName);
        });
      });
  }

  /**
   * アクセスレベルデータ設定
   */
  private setAccessLevel(roleCodeList: string[], roleName: string): void {
    this.masterService.getAccessLevelList(this.sessionInfo.loginCompanyCode, roleCodeList)
      .subscribe((codeNameList: CodeName[]) => {
        const accessLevelList = codeNameList.filter(codeName => codeName.value3);
        this.sessionService.setAccessLevel(roleCodeList, roleName, accessLevelList);
        this.menuData = this.getMenuData(accessLevelList);
        console.log('this.menuData');
        console.log(this.menuData);
        console.log('this.menuData');

        this.header.setHeaderInfo(this.sessionService.getSessionInfo(), this.menuData, this.usableCompanyList);

        // 初期画面表示(siteMap)
        let menu: MenuItem;
        if (this.httpParams) {
          menu = this.getMenu(this.httpParams.get('menuId')); // アプリケーション起動パラメータでメニュー指定有り
        }

        if (!menu) {
          menu = this.getMenu(SystemConst.Menu.menuIdSiteMap); // メニュー指定がない場合はサイトマップが初期画面
        }
        this.selectMenu(menu);
      });
  }

  /**
   * メニュ―データの取得
   * @param accessLevelList アクセスレベル一覧
   * @param parentMenuItem 親メニュー
   */
  private getMenuData(accessLevelList: CodeName[], parentMenuItem?: MenuItem, level = 1): MenuItem[] {
    if (!parentMenuItem) {
      this.menuDataMap.clear();
    }
    const menuData: MenuItem[] = accessLevelList
      .filter(codeName => codeName.value3)
      .map(codeName => {
        let menuId: string;
        let name: string;
        switch (level) {
          case 1:
            menuId = codeName.value5;
            name = codeName.value6;
            break;
          case 2:
            menuId = codeName.value7;
            name = codeName.value8;
            if (menuId) {
              break;
            }
          // tslint:disable-next-line: no-switch-case-fall-through
          case 3:
            menuId = codeName.value9;
            name = codeName.value10;
            if (menuId) {
              break;
            }
          // tslint:disable-next-line: no-switch-case-fall-through
          case 4:
            menuId = codeName.value11;
            name = codeName.value12;
            if (menuId) {
              break;
            }
        }
        const path = codeName.value4
          .replace(/Main/g, '')
          .replace(/([A-Z])/g, (s) => '-' + s.charAt(0).toLowerCase())
          .substr(1, codeName.value4.length - 1)
          .toLowerCase();

        return {
          menuId,
          name,
          frameTitle: codeName.value1,
          path
        };
      })
      .filter((menuItem, i, list) => {
        let b = true;
        if (i !== 0) {
          if (menuItem.menuId === list[i - 1].menuId) {
            b = false;
          }
        }

        if (b) { // マップにメニューを格納
          this.menuDataMap.set(menuItem.menuId, menuItem);
        }
        return b;
      });

    if (level < 4) {
      menuData.forEach(menuItem => {
        const childAccessLevelList = accessLevelList
          .filter((codeName) => {
            let b = true;
            if ((level === 1 && codeName.value5 !== menuItem.menuId)
              || (level === 2 && codeName.value7 !== menuItem.menuId)
              || (level === 3 && codeName.value9 !== menuItem.menuId)) {
              b = false;
            } else if (!codeName.value11) {
              b = false;
            }
            return b;
          });

        if (childAccessLevelList.length > 0) {
          menuItem.items = this.getMenuData(childAccessLevelList, menuItem, level + 1);
          if (menuItem.items) {
            menuItem.path = undefined;
          }
        }
      });
    }

    return menuData;
  }

  /**
   * メニューIDに応じたメニューデータを取得
   * @param menuId メニューID
   * @return メニューデータ
   */
  private getMenu(menuId: string): MenuItem {
    let menu: MenuItem;
    // メニューデータの取得
    // e申請からアクセスされたメニューIDの場合、各照会機能のメニューを取得
    // 外部Window参照からアクセスされたメニューIDの場合、各照会機能のメニューを取得してフレームタイトルを変更
    switch (menuId) {
      ///////////////////////////// ↓e申請用
      case SystemConst.Menu.Eapp.menuIdRefImplEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdRefImpl);
        break;
      case SystemConst.Menu.Eapp.menuIdApAssetEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApAssetSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdApBgnIntEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApBgnIntSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdApChangeEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApChangeSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdApEndIntEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApEndIntSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdApEndLeEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApEndLeSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdApEndReEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApEndReSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdApEndTanEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApEndTanSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdApGetIntEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApGetIntSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdApGetTanEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApGetTanSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdApLicenseEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApLicenseSearch);
        break;
      case SystemConst.Menu.Eapp.menuIdInvEapp:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdInv);
        break;
      ///////////////////////////// ↓外部Window参照用
      case SystemConst.Menu.Ref.menuIdRefImplRef:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdRefImpl);
        menu.frameTitle = SystemConst.Menu.Ref.frameTitleRefImplRef;
        break;
      case SystemConst.Menu.Ref.menuIdApGetIntRef:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApGetIntSearch);
        menu.frameTitle = SystemConst.Menu.Ref.frameTitleApGetIntRef;
        break;
      case SystemConst.Menu.Ref.menuIdApGetTanRef:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdApGetTanSearch);
        menu.frameTitle = SystemConst.Menu.Ref.frameTitleApGetTanRef;
        break;
      case SystemConst.Menu.Ref.menuIdAssetRef:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdAssetSearch);
        menu.frameTitle = SystemConst.Menu.Ref.frameTitleAssetRef;
        break;
      case SystemConst.Menu.Ref.menuIdFldRef:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdFldTanSearch);
        menu.frameTitle = SystemConst.Menu.Ref.frameTitleFldRef;
        break;
      case SystemConst.Menu.Ref.menuIdFldAppRef:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdFldIntSearch);
        menu.frameTitle = SystemConst.Menu.Ref.frameTitleFldAppRef;
        break;
      case SystemConst.Menu.Ref.menuIdInvRef:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdInv);
        menu.frameTitle = SystemConst.Menu.Ref.frameTitleInvRef;
        break;
      case SystemConst.Menu.Ref.menuIdLicenseRef:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdLicenseSearch);
        menu.frameTitle = SystemConst.Menu.Ref.frameTitleLicenseRef;
        break;
      case SystemConst.Menu.Ref.menuIdLldRef:
        menu = this.menuDataMap.get(SystemConst.Menu.menuIdLldSearch);
        menu.frameTitle = SystemConst.Menu.Ref.frameTitleLldRef;
        break;
      ///////////////////////////// ↓通常メニュー用
      default:
        menu = this.menuDataMap.get(menuId);
    }
    if (menu) {
      menu.menuId = menuId;
    }
    return menu;
  }
}
