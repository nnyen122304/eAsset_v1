import { Injectable } from '@angular/core';
import { environment } from '../../environments/environment';

/**
 * サイトアクセスサービス
 */
@Injectable({
  providedIn: 'root'
})
export class SiteAccessService {
  /**
   * URLオープン
   * @param url URL
   */
  openUrl(url: string): void {
    // 別Windowオープン
    const windowName = String(new Date().getTime());
    window.open(url , windowName, 'status=no,resizable=yes,menubar=no,toolbar=no,scroll=yes,location=no');
  }

  /**
   * eAssetを別Windowでオープン
   * @param menuId オープンする画面で開く機能のmenuId
   * @param companyCode オープンする画面のログイン会社コード
   * @param currentRoles オープンする画面のログイン権限コード配列
   * @param currentRoleName オープンする画面のログイン権限名
   * @param menuDisplayFlag オープンする画面にヘッダ（メニュー）を表示するかどうか true:表示する、false:表示しない
   * @param params オープンする画面に渡すクエリパラメータ
   */
  openEasset(menuId: string, companyCode: string, currentRoles: string[], currentRoleName: string, menuDisplayFlag: boolean, params?: {[params: string]: string}): void {
    // 別Windowオープン
    const windowName = String(new Date().getTime());
    window.open('about:blank', windowName, 'status=no,resizable=yes,menubar=no,toolbar=no,scroll=yes,location=no');

    // フォームコントロールを生成してリクエスト送信
    const form = document.createElement('form');
    document.body.appendChild(form);
    form.setAttribute('style', 'display: none');
    let input: HTMLInputElement;
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'menuId'; input.value = menuId;
    form.appendChild(input);
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'companyCode'; input.value = companyCode;
    form.appendChild(input);
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'currentRoles'; input.value = currentRoles.reduce((v1, v2) => v1 + ',' + v2);
    form.appendChild(input);
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'currentRoleName'; input.value = currentRoleName;
    form.appendChild(input);
    input = document.createElement('input'); input.type = 'hidden'; input.name = 'menuDisplayFlag'; input.value = menuDisplayFlag ? '1' : '0';
    form.appendChild(input);
    if (params) {
      for (const paramName of Object.keys(params)) {
        input = document.createElement('input'); input.type = 'hidden'; input.name = paramName; input.value = params[paramName];
        form.appendChild(input);
      }
    }
    form.action = environment.eAssetUrl;
    form.target = windowName;
    form.method = 'GET';
    form.submit();
    form.parentNode.removeChild(form);
  }
}
