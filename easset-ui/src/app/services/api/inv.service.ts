import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { InvStat } from 'src/app/models/api/inv/inv-stat';
import { InvSumSR } from 'src/app/models/api/inv/inv-sum-sr';
import { InvSumSC } from 'src/app/models/api/inv/inv-sum-sc';
import { InvLine } from 'src/app/models/api/inv/inv-line';

/**
 * 棚卸サービスAPIコール
 */
@Injectable({
  providedIn: 'root'
})
export class InvService {
  private servicePath = environment.apiUrl + '/inv'; // サービスパス


  constructor(private http: HttpClient) { }

  /**
   * 棚卸データ作成取得
   * @param companyCode 会社コード
   * @return 棚卸データ作成情報
   */
  searchInvStat(companyCode: string): Observable<InvStat[]> {
    const params = {companyCode};
    return this.http.post<InvStat[]>(this.servicePath + '/searchInvStat', null, {params});
  }

  /**
   * 棚卸データ作成実行呼び出し
   * @param companyCode 会社コード
   * @param period 会計年月
   * @param execStaffCode 実行者社員番号
   * @param overwriteFlag データ上書きフラグ
   */

  callCreateInvData(companyCode: string, period: string, execStaffCode: string, overwriteFlag: boolean): Observable<void> {
    const params = {companyCode, period, execStaffCode, overwriteFlag: overwriteFlag.toString()};
    return this.http.post<void>(this.servicePath + '/callCreateInvData', null, {params});
  }

  /**
   * 棚卸データ公開
   * @param companyCode 会社コード
   * @param period 会計年月
   * @param publicTypeArray 公開タイプ指定（有形～備品等）
   * @param sendExecArray メール送信指定（有形～備品等）
   * @param execStaffCode 実行者社員番号
   */
  publicInvData(companyCode: string, period: string, publicTypeArray: string[], sendExecArray: boolean[], execStaffCode: string): Observable<void> {
    const params = {companyCode, period, execStaffCode, publicTypeArray, sendExecArray: sendExecArray.map(v => String(v))};
    return this.http.post<void>(this.servicePath + '/publicInvData', null, {params});
  }

  /**
   * 最新会計年月判定
   * @param companyCode 会社コード
   * @param period 会計年月
   * @return 最新会計年月判定情報
   */
  getInvHistPeriodFlag(companyCode: string, period: string): Observable<NonObjectResponse<string>> {
    const params = {companyCode, period};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/getInvHistPeriodFlag', null, {params});
  }

  /**
   * 棚卸集約検索
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   * @return 棚卸集約検索情報
   */
  searchInvSum(loginStaffCode: string, accessLevel: string, companyCode: string, searchParam?: InvSumSC, searchParamList?: InvSumSC[]): Observable<InvSumSR[]> {
    const params = {loginStaffCode, accessLevel, companyCode};
    let body = null;
    if (searchParam) {
      body = searchParam;
    } else if (searchParamList) {
      body = searchParamList;
    }
    return this.http.post<InvSumSR[]>(this.servicePath + '/searchInvSum', JSON.stringify(body), {params});
  }

  /**
   * 棚卸集約検索
   * @param eappId e申請書類ID
   * @return 棚卸集約検索情報
   */
  searchInvSumEapp(eappId: number): Observable<InvSumSR[]> {
    const params = {eappId: eappId.toString()};
    return this.http.post<InvSumSR[]>(this.servicePath + '/searchInvSumEapp', null, {params});
  }

 /**
	 * 棚卸報告
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param list    棚卸データ一覧
	 */
  reportInvSum(loginStaffCode: string, accessLevel: string, list: InvSumSR[]): Observable<InvSumSR[]> {
      const params = {loginStaffCode, accessLevel};
      return this.http.put<InvSumSR[]>(this.servicePath + '/reportInvSum', JSON.stringify(list), {params});
    }

  /**
   * 棚卸集約情報CSV作成
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param companyCode    会社コード
   * @param searchParam    棚卸検索条件
   * @return CSV作成情報
   */
  createInvSumCsv(loginStaffCode: string, accessLevel: string, companyCode: string, searchParam: InvSumSC, searchScopeType?: string): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, companyCode};
    if (searchScopeType) { Object.assign(params, {searchScopeType}); }
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createInvSumCsv', JSON.stringify(searchParam), {params});
  }

 /**
	 * 実査表印刷
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param list    棚卸データ一覧
	 * @return PDF作成情報
	 * @throws SQLException
	 */
  createInvPdf(loginStaffCode: string, accessLevel: string, list: InvSumSR[]): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createInvPdf', JSON.stringify(list), {params});
  }

  /**
   * 督促メール
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param list    棚卸データ一覧
   */
  remindInv(loginStaffCode: string, accessLevel: string, list: InvSumSR[]): Observable<void> {
    const params = {loginStaffCode, accessLevel};
    return this.http.put<void>(this.servicePath + '/remindInv', JSON.stringify(list), {params});
  }

  /**
   * 明細ダウンロード
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   * @return ダウンロード情報
   */
  createInvLineListCsv(loginStaffCode: string, accessLevel: string, list: InvSumSR[], searchScopeType: string): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, searchScopeType};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createInvLineListCsv', JSON.stringify(list), {params});
  }

  /**
   * 棚卸検索
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   * @return 棚卸検索情報
   */
  searchInvLine(loginStaffCode: string, accessLevel: string, searchParam: InvSumSR, searchScopeType: string): Observable<InvLine[]> {
    const params = {loginStaffCode, accessLevel, searchScopeType};
    return this.http.post<InvLine[]>(this.servicePath + '/searchInvLine', JSON.stringify(searchParam), {params});
  }

  /**
   * 棚卸更新
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param list    棚卸データ一覧
   * @return 棚卸更新情報
   */
  updateInvLine(loginStaffCode: string, accessLevel: string, invSumSR: InvSumSR, list: InvLine[]): Observable<void> {
    const params = {loginStaffCode, accessLevel, invSumSR: JSON.stringify(invSumSR)};
    return this.http.put<void>(this.servicePath + '/updateInvLine', JSON.stringify(list), {params});
  }

  /**
   * エクスポート
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件:
   * @return エクスポート情報
   */
  createInvLineCsv(loginStaffCode: string, accessLevel: string, searchParam: InvSumSR, searchScopeType: string): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, searchScopeType};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createInvLineCsv', JSON.stringify(searchParam), {params});
  }

  /**
   * ｲﾝﾎﾟｰﾄ
   * @param fileId ファイルID
   * @return ファイル情報
   */
  getInvLineByCsv(loginStaffCode: string, accessLevel: string, fileId: string, obj: InvSumSR): Observable <InvLine[]> {
    const params = {loginStaffCode, accessLevel, fileId};
    return this.http.post<InvLine[]>(this.servicePath + '/getInvLineByCsv', JSON.stringify(obj), {params});
  }

 /**
  * 棚卸一括更新定型CSVダウンロード
  * @param companyCode 会社コード
  * @param period 会計年月
  * @return CSVダウンロード情報
  */
  createInvTemplateCsv(companyCode: string, period: string): Observable <NonObjectResponse<string>> {
    const params = {companyCode, period};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createInvTemplateCsv', null, {params});
  }

 /**
	 * 情報機器等一括更新処理
	 * @param loginStaffCode ログイン社員番号
	 * @param companyCode 会社コード
	 * @param accessLevel アクセスレベル
	 * @param fileId ファイルID
	 */
  callUpdateInvBulk(loginStaffCode: string, companyCode: string, accessLevel: string, fileId: string, period: string): Observable<void> {
    const params = {loginStaffCode, companyCode, accessLevel, fileId, period};
    return this.http.post<void>(this.servicePath + '/callUpdateInvBulk', null, {params});
  }

}
