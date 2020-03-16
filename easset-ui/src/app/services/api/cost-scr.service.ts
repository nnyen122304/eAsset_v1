import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { CostScrSR } from 'src/app/models/api/cost-scr/cost-scr-sr.model';
import { CostScrSC } from 'src/app/models/api/cost-scr/cost-scr-sc.model';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { CostScrLine } from 'src/app/models/api/cost-scr/cost-scr-line.model';
import { CostScrStat } from 'src/app/models/api/cost-scr/cost-scr-stat.model';
import { Section } from 'src/app/models/api/section';

/**
 * 棚卸サービスAPIコール
 */
@Injectable({
  providedIn: 'root'
})

export class CostScrService {
  private servicePath = environment.apiUrl + '/cost-scr';

  constructor(private http: HttpClient) {
  }

  /**
   * 経費負担部課精査状況一覧検索
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param companyCode 会社コード
   *
   * @return データ作成状況一覧
   */
  searchCostScrStatList(loginStaffCode: string, accessLevel: string, companyCode: string): Observable<CostScrStat[]> {
    const params = {loginStaffCode, accessLevel, companyCode};
    return this.http.post<CostScrStat[]>(this.servicePath + '/searchCostScrStatList', null, {params});
  }

  /**
   * ファイル・直入力可能マスタ値CSV作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param companyCode 会社コード
   * @param propertyList 項目名一覧
   *
   * @return CSVファイル識別ID
   */
  createScrPossibleInputMasterCsv(loginStaffCode: string, accessLevel: string, companyCode: string, propertyList: string[]): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, companyCode, propertyList};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createScrPossibleInputMasterCsv', null, {params});
  }

  /**
   * 経費負担部課精査明細データ作成実行
   *
   * @param companyCode 会社コード
   * @param period 会計年月
   * @param execStaffCode 実行者社員番号
   * @param overwriteFlag データ上書きフラグ
   *
   * @return なし
   */
  callCreateCostScrData(companyCode: string, period: string, execStaffCode: string, overwriteFlag: boolean): Observable<void> {
    const params = {companyCode, period, execStaffCode, overwriteFlag: overwriteFlag.toString()};
    return this.http.post<void>(this.servicePath + '/callCreateCostScrData', null, {params});
  }

  /**
   * 精査担当者設定更新
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param companyCode 会社コード
   * @param period 会計年月
   * @param fileId ファイルID
   *
   * @return なし
   */
  updateCostScrSectionByCsv(loginStaffCode: string, accessLevel: string, companyCode: string, period: string, fileId: string): Observable<void> {
    const params = {loginStaffCode, accessLevel, companyCode, period, fileId};
    return this.http.post<void>(this.servicePath + '/updateCostScrSectionByCsv', null, {params});
  }

  /**
   * 経費負担部課明細データ作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param companyCode 会社コード
   * @param period 会計年月
   * @param publicCode 各部OPEN/CLOSE
   * @param isSendMail 依頼メール送信？
   *
   * @return なし
   */
  publicCostScrData(loginStaffCode: string, accessLevel: string, companyCode: string, period: string, publicCode: string, isSendMail: boolean): Observable<void> {
    const params = {loginStaffCode, accessLevel, companyCode, period, publicCode, isSendMail: isSendMail.toString()};
    return this.http.put<void>(this.servicePath + '/publicCostScrData', null, {params});
  }

  /**
   * 経費負担部課精査状況検索
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param companyCode 会社コード
   * @param searchParam 検索条件
   *
   * @return　経費負担部課精査明細一覧
   */
  searchCostScr(loginStaffCode: string, accessLevel: string, companyCode: string, searchParam?: CostScrSC): Observable<CostScrSR[]> {
    const params = {loginStaffCode, accessLevel, companyCode};
    let body = null;
    if (searchParam) {
      body = searchParam;
    }
    return this.http.post<CostScrSR[]>(this.servicePath + '/searchCostScr', body, {params});
  }

  /**
   * 明細ダウンロード
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param searchParam 検索条件
   *
   * @return　なし
   */
  createCostScrLineListCsv(loginStaffCode: string, accessLevel: string, list: CostScrSR[]): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createCostScrLineListCsv', list, {params});
  }

  /**
   * 経費負担部課精査状況CSVファイル作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param companyCode 会社コード
   * @param searchParam 検索条件
   *
   * @return　なし
   */
  createCostScrCsv(loginStaffCode: string, accessLevel: string, companyCode: string, searchParam: CostScrSC): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, companyCode};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createCostScrCsv', JSON.stringify(searchParam), {params});
  }

  /**
   * 経費負担部課精査状況更新
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param companyCode 会社コード
   * @param obj 検索結果
   * @param compFlag 精査完了フラグ
   *
   * @return なし
   */
  updateCostScr(loginStaffCode: string, accessLevel: string, list: CostScrSR[], compFlag: string): Observable<void> {
    const params = {loginStaffCode, accessLevel, compFlag};
    return this.http.put<void>(this.servicePath + '/updateCostScr', list, {params});
  }

  /**
   * 督促メール送信
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param list 督促メール対象一覧
   *
   * @return なし
   */
  remindCostScr(loginStaffCode: string, accessLevel: string, list: CostScrSR[]): Observable<void> {
    const params = {loginStaffCode, accessLevel};
    return this.http.put<void>(this.servicePath + '/remindCostScr', list, {params});
  }

  /**
   * 経費負担部課明細検索
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param companyCode 会社コード
   * @param searchParam    検索条件
   *
   * @return パラメータ
   */
  searchCostScrLine(loginStaffCode: string, accessLevel: string, companyCode: string, searchParam: CostScrSR): Observable<CostScrLine[]> {
    const params = {loginStaffCode, accessLevel, companyCode};
    return this.http.post<CostScrLine[]>(this.servicePath + '/searchCostScrLine', JSON.stringify(searchParam), {params});
  }

  /**
   * 経費負担部課明細エクスポートCSV作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param companyCode    会社コード
   * @param searchParam    処理対象の経費負担部課集計データ
   *
   * @return ファイルID
   */
  createCostScrLineCsv(loginStaffCode: string, accessLevel: string, companyCode: string, searchParam: CostScrSR): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, companyCode};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createCostScrLineCsv', JSON.stringify(searchParam), {params});
  }

  /**
   * 経費負担部課明細インポート
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param fileId         ファイルID
   * @param obj            検索条件
   *
   * @return 経費負担部課精査明細データクラス
   */
  getScrLineByCsv(loginStaffCode: string, accessLevel: string, fileId: string, obj: CostScrSR): Observable<CostScrLine[]> {
    const params = {loginStaffCode, accessLevel, fileId};
    return this.http.post<CostScrLine[]>(this.servicePath + '/getScrLineByCsv', JSON.stringify(obj), {params});
  }

  /**
   * 経費負担部課明細データ更新
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel　アクセスレベル
   * @param companyCode　会社コード
   * @param costScrSR　処理対象の経費負担部課集計データ
   * @param list　更新対象一覧
   *
   * @return なし
   */
  updateCostScrLine(loginStaffCode: string, accessLevel: string, companyCode: string, costScrSR: CostScrSR, list: CostScrLine[]): Observable<void> {
    const params = {loginStaffCode, accessLevel, companyCode, costScrSR: JSON.stringify(costScrSR)};
    return this.http.put<void>(this.servicePath + '/updateCostScrLine', JSON.stringify(list), {params});
  }

  /**
   * 精査担当部署更新
   *
   * @param loginStaffCode ログイン社員番号
   * @param list    更新対象一覧
   *
   * @return 更新対象一覧
   */
  updateScrSection(loginStaffCode: string, list: CostScrSR[]): Observable<void> {
    const params = {loginStaffCode};
    return this.http.put<void>(this.servicePath + '/updateScrSection', JSON.stringify(list), {params});
  }

  /**
   * 依頼メール送信
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param list    依頼メール対象一覧
   *
   * @return 依頼メール対象一覧
   */
  requestCostScr(loginStaffCode: string, accessLevel: string, list: CostScrSR[]): Observable<void> {
    const params = {loginStaffCode, accessLevel};
    return this.http.put<void>(this.servicePath + '/requestCostScr', JSON.stringify(list), {params});
  }

  /**
   * 部課名から人事部課データ検索
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param companyCode    会社コード
   * @param sectionName    部課名
   *
   * @return 部課名から人事部課データ検索
   */
  getSectionByName(loginStaffCode: string, accessLevel: string, companyCode: string, sectionName: string): Observable<Section> {
    const params = {loginStaffCode, accessLevel, companyCode, sectionName};
    return this.http.get<Section>(this.servicePath + '/getSectionByName', {params});
  }
}
