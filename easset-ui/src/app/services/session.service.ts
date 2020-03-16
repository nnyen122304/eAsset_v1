import { Injectable } from '@angular/core';
import { NgxSpinnerService } from 'ngx-spinner';
import { MasterService } from './api/master.service';
import { User } from '../models/api/user';
import { Observable, from } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { CodeName } from '../models/api/code-name';

import { SessionInfo } from '../models/session-info';
import { SessionInfoEx } from '../models/session-info-ex';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  /**
   * Loading表示呼出し回数
   */
  private loadingCt = 0;

  /**
   * セッション情報
   */
  private sessionInfo: SessionInfoEx;

  /**
   * アクセスレベルマップ<menuId, accessLevel>
   */
  private accessLevelMap: Map<string, string> = new Map();

  constructor(private spinnerService: NgxSpinnerService, private masterService: MasterService) {}

  /**
   * ログイン
   * @return Observable
   */
  login(): Observable<SessionInfo> {
      // ユーザー情報取得
      return this.masterService.getUserByAccount().pipe(switchMap((user: User) => {
        this.sessionInfo = new SessionInfo();
        this.sessionInfo.loginUser = user;
        return from([this.sessionInfo]);
      }));
  }

  /**
   * ログアウト
   */
  logout(): void {
    this.sessionInfo = undefined;
  }

  /**
   * アクセス権限の設定
   * @param roleCodeList 権限コードリスト
   * @param roleName 権限名
   * @param accessLevelList メニュー/アクセスレベル一覧
   */
  setAccessLevel(roleCodeList: string[], roleName: string, accessLevelList: CodeName[]): void {
    this.sessionInfo.loginRoleCodeList = roleCodeList;
    this.sessionInfo.loginRoleName = roleName;

    this.accessLevelMap = Object.entries(accessLevelList)
      .reduce((l, [k, v]) => l.set(v.value11 ? v.value11 : v.value5, v.value3), new Map<string, string>());
  }

  /**
   * カレントメニューIDの設定
   * @param menuId メニューID
   */
  setCurrentMenu(menuId: string): void {
    if (this.sessionInfo) {
      this.sessionInfo.currentMenuId = menuId;
      this.sessionInfo.currentAccessLevel = this.accessLevelMap.get(menuId);
    }
  }

  /**
   * セッション情報の取得
   * @return セッション情報
   */
  getSessionInfo(): SessionInfo {
    return this.sessionInfo;
  }

  /**
   * データ変更を通知する
   * @param status データが変更されたか判定用値
   */
  setDataChange(status: boolean): void {
    this.sessionInfo.isDataChanged = status;
  }

  /**
   * データ変更状態を取得する
   */
  getDataChange(): boolean {
    return this.sessionInfo.isDataChanged;
  }

  /**
   * Loading表示
   */
  showLoading(): void {
    if (this.loadingCt === 0) {
      this.spinnerService.show();
    }
    this.loadingCt++;
  }

  /**
   * Loading非表示
   */
  hideLoading(): void {
    this.loadingCt--;
    if (this.loadingCt === 0) {
      this.spinnerService.hide();
    } else if (this.loadingCt < 0) {
      this.loadingCt = 0;
    }
  }
}
