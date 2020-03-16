import {Component, OnInit, ViewChild} from '@angular/core';
import {AbstractChildComponent} from 'src/app/components/view-stack/abstract-child-component';
import {SiteMapComponent} from '../site-map.component';
import {SessionInfo} from 'src/app/models/session-info';
import {SessionService} from 'src/app/services/session.service';
import {MasterService} from 'src/app/services/api/master.service';
import {WjTreeView} from 'wijmo/wijmo.angular2.nav';
import {CodeName} from 'src/app/models/api/code-name';
import {MenuItem} from 'src/app/models/menu-item';
import {SystemConst} from 'src/app/const/system-const';
import {HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {MessageService} from 'src/app/services/message.service';
import {SiteAccessService} from 'src/app/services/site-access.service';

/**
 * サイトマップコンポネント
 */

@Component({
  selector: 'app-site-map-result',
  templateUrl: './site-map-result.component.html',
  styleUrls: ['./site-map-result.component.scss']
})
export class SiteMapResultComponent extends AbstractChildComponent<SiteMapComponent> implements OnInit {
  /**
   * セッション情報
   */
  sessionInfo: SessionInfo;

  /**
   * ツリーデータ
   */
  menuData;

  /**
   * データツリーのメニュー
   */
  menuItems;

  /**
   * メニューモードを開く
   */
  labelType = 'この(同一)ウィンドウ';

  /**
   * アプリケーション起動時のHttpParam
   */
  private httpParams: HttpParams;

  /**
   * メニューIDとメニューデータのマップ
   */
  private menuDataMap = new Map<string, MenuItem>();

  /**
   * メニューIDとメニューデータのマップ
   */
  selectedItem: WjTreeView['selectedItem'];

  /**
   * ツリー Ref
   */
  @ViewChild('treeView1', null) treeView1: WjTreeView;
  @ViewChild('treeView2', null) treeView2: WjTreeView;
  @ViewChild('treeView3', null) treeView3: WjTreeView;
  @ViewChild('treeView4', null) treeView4: WjTreeView;
  @ViewChild('treeView5', null) treeView5: WjTreeView;
  @ViewChild('treeView6', null) treeView6: WjTreeView;
  @ViewChild('treeView7', null) treeView7: WjTreeView;
  @ViewChild('treeView8', null) treeView8: WjTreeView;

  constructor(private sessionService: SessionService,
              private masterService: MasterService,
              private router: Router,
              public messageService: MessageService,
              private siteAccessService: SiteAccessService) {
    super();
  }

  /**
   * コンポーネント初期処理
   */
  ngOnInit() {
    this.sessionInfo = this.sessionService.getSessionInfo();
    /**
     * 画面切替初期処理(param:画面オープンパラメータ)
     */
    this.changeChildSubject.subscribe((param) => {
      if (param.action === 'init') {
        this.init();
      }
    });
  }

  /**
   * 初期処理
   */
  init() {
    // レベルリストのアクセス取得APIを呼び出す
    this.masterService.getAccessLevelList(this.sessionInfo.loginCompanyCode, this.sessionInfo.loginRoleCodeList)
      .subscribe((codeNameList: CodeName[]) => {
        const accessLevelList = codeNameList.filter(codeName => codeName.value3);
        this.menuData = this.getMenuData(accessLevelList);
        this.parseMenuToTree(this.menuData);
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

    // データメニューを取得する
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
   * ツリーにデータを解析する
   *
   * @param data メニューデータ
   *
   * @return オブジェクトメニュー
   */
  parseMenuToTree(data) {
    data.map(item => {
      switch (item.menuId) {
        // 情報機器等
        case '01000':
          this.menuItems = {...this.menuItems, ...{tree1: {label: item.name, items: item.items}}};
          break;
        // ライセンス
        case '02000':
          this.menuItems = {...this.menuItems, ...{tree2: {label: item.name, items: item.items}}};
          break;
        // 期首移行
        case '19000':
          this.menuItems = {...this.menuItems, ...{tree3: {label: item.name, items: item.items}}};
          break;
        // 固定資産
        case '13000':
          this.menuItems = {...this.menuItems, ...{tree4: {label: item.name, items: item.items}}};
          break;
        // リース/レンタル
        case '14000':
          this.menuItems = {...this.menuItems, ...{tree5: {label: item.name, items: item.items}}};
          break;
        // 棚卸
        case '18000':
          this.menuItems = {...this.menuItems, ...{tree6: {label: item.name, items: item.items}}};
          break;
        // 管理
        case '03000':
          this.menuItems = {...this.menuItems, ...{tree7: {label: item.name, items: item.items}}};
          break;
        // 申請
        case '04990':
          this.menuItems = {...this.menuItems, ...{tree8: {label: item.name, items: item.items}}};
          break;
      }
    });
  }

  /**
   * モード変更ボタンをクリックするイベント
   */
  changeType() {
    this.labelType = this.labelType === 'この(同一)ウィンドウ' ? '新しい(別)ウィンドウ' : 'この(同一)ウィンドウ';
  }

  /**
   * ツリー初期処理
   */
  onTreeInitialized(treeView) {
    treeView.collapseToLevel(10000);
  }

  /**
   * 選択を親コンポネントに送信する
   */
  submit(treeView) {
    this.itemClickedMenu(treeView);
  }

  /**
   * メニュー項目をクリックするイベント
   *
   * @param treeView メニュー
   *
   * @return なし
   */
  itemClickedMenu(treeView) {
    if (treeView.selectedItem) {
      this.selectedItem = treeView.selectedItem;
      const childItems = this.selectedItem.items ? this.selectedItem.items : null;
      if (childItems === null) {
        const menuId = this.selectedItem.menuId;
        let menu: MenuItem;
        if (this.labelType === 'この(同一)ウィンドウ') {
          menu = this.getMenu(menuId); // 同一Window
          this.acceptChange(menu);
        } else {
          this.siteAccessService.openEasset(   // 別Window
            menuId,
            this.sessionInfo.loginCompanyCode,
            this.sessionInfo.loginRoleCodeList,
            this.sessionInfo.loginRoleName,
            true,
            null
          );
        }
      }
    }
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

    // 前回と同様のパスでも機能メイン画面の初期処理が実行されるように一度空遷移
    this.router.navigate(['']).then(v => {
      this.router.navigate(commands).then(value => {
      }, (reason) => {
        this.sessionService.setCurrentMenu(undefined);
        this.messageService.err(reason);
      });
    });
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
      // ↓e申請用
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
      // ↓外部Window参照用
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
      // ↓通常メニュー用
      default:
        menu = this.menuDataMap.get(menuId);
    }
    if (menu) {
      menu.menuId = menuId;
    }
    return menu;
  }
}
