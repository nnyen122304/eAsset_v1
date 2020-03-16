import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { User } from 'src/app/models/api/user';
import { CodeName } from 'src/app/models/api/code-name';
import { Section } from 'src/app/models/api/section';
import { LovDataEx } from 'src/app/models/api/lov-data-ex';
import { RoleAdmin } from 'src/app/models/api/role-admin';
import { RoleSection } from 'src/app/models/api/role-section';
import { CodeNameSet } from 'src/app/models/api/code-name-set';
import { CodeNameSetItem } from 'src/app/models/api/code-name-set-item';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { DatePipe } from '@angular/common';
import { AssetCategoryNode } from 'src/app/models/api/asset-category-node';

/**
 * マスターサービスAPIコール
 */
@Injectable({
  providedIn: 'root'
})
export class MasterService {
  private servicePath = environment.apiUrl + '/master'; // サービスパス
  constructor(private http: HttpClient, private datePipe: DatePipe) { }

  /**
   * ユーザー情報取得
   * @return ユーザー情報
   */
  getUserByAccount(): Observable<User> {
    const params = {};
    return this.http.post<User>(this.servicePath + '/getUserByAccount', null, {params});
  }

  /**
   * ログイン可能な会社一覧取得
   * @param staffCode 社員番号
   * @return ログイン可能な会社一覧
   */
  getUsableCompanyList(staffCode: string): Observable<CodeName[]> {
    const params = {staffCode};
    return this.http.post<CodeName[]>(this.servicePath + '/getUsableCompanyList', null, {params});
  }

   /**
    * 汎用マスタ取得(有効データのみ)
    * @param categoryCode カテゴリコード
    * @param internalCode 識別コード(省略可)
    * @param companyCode 会社コード(省略可)
    * @param values 値(省略可)
    * @return 汎用マスタ
    */
  getCodeName(categoryCode: string, internalCode?: string, companyCode?: string, values?: string[]): Observable<CodeName> {
    const params = {categoryCode};
    if (internalCode) { Object.assign(params, {internalCode}); }
    if (companyCode) { Object.assign(params, {companyCode}); }
    return this.http.post<CodeName>(this.servicePath + '/getCodeName', values, {params});
  }

  /**
   * 全社権限一覧取得
   * @param staffCode 社員番号
   * @param companyCode 会社コード
   * @return 全社権限一覧
   */
  getRoleAdminList(staffCode: string, companyCode: string): Observable<CodeName[]> {
    const params = {staffCode, companyCode};
    return this.http.post<CodeName[]>(this.servicePath + '/getRoleAdminList', null, {params});
  }

  /**
   * 各部権限一覧取得
   * @param staffCode 社員番号
   * @param companyCode 会社コード
   * @return 各部権限一覧
   */
  getRoleSectionList(staffCode: string, companyCode: string): Observable<CodeName[]> {
    const params = {staffCode, companyCode};
    return this.http.post<CodeName[]>(this.servicePath + '/getRoleSectionList', null, {params});
  }

  /**
   * 権限から利用可能な機能へのアクセスレベルを取得
   * @param companyCode 会社コード
   * @param roleCodeList 権限コード一覧
   * @return アクセスレベル
   */
  getAccessLevelList(companyCode: string, roleCodeList: string[]): Observable<CodeName[]> {
    const params = {companyCode, roleCodeList};
    return this.http.post<CodeName[]>(this.servicePath + '/getAccessLevelList', null, {params});
  }

 /**
	 * コードネーム一覧取得(有効データのみ)
	 * @param categoryCode カテゴリコード（必須）
	 * @param internalCode 識別コード（任意）
	 * @param companyCode 会社コード（任意）
	 * @param values 値: 1番目(index0)=value1、2番目(index1)=value2・・・（任意）
	 * @return コードネーム一覧
	 */
  getCodeNameList(categoryCode: string, internalCode?: string, companyCode?: string, values?: string[]): Observable<CodeName[]> {
    const params = {categoryCode};
    if (internalCode) { Object.assign(params, {internalCode}); }
    if (companyCode) { Object.assign(params, {companyCode}); }
    if (values) { Object.assign(params, {values: JSON.stringify(values)}); }
    return this.http.post<CodeName[]>(this.servicePath + '/getCodeNameList', JSON.stringify(values), {params});
  }

 /**
	 * 人事部課検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param sectionName 部課名検索キーワード
	 * @return 人事部課リスト
	 */
  searchSection(loginStaffCode: string, accessLevel: string, companyCode: string, sectionName: string, year?: number): Observable<Section[]> {
    const params = {loginStaffCode, accessLevel, companyCode, sectionName};
    if (year) { Object.assign(params, {year: year.toString()}); }
    return this.http.post<Section[]>(this.servicePath + '/searchSection', null, {params});
  }

 /**
	 * 社員検索
	 * @param companyCode 会社コード(nullの場合は全G社)
	 * @param staffName 氏名検索キーワード
	 * @param sectionName 部課名検索キーワード
	 * @param enableStaffOnly 有効社員のみ検索指定
	 * @return 社員リスト
	 */
  searchStaff(companyCode: string, staffName: string, sectionName: string, enableStaffOnly: boolean): Observable<User[]> {
    let params = {staffName, sectionName, enableStaffOnly: enableStaffOnly.toString()};
    if (companyCode) { params = Object.assign(params, {companyCode}); }
    return this.http.post<User[]>(this.servicePath + '/searchStaff', null, {params});
  }

 /**
	 * 社員検索(1件の場合)：無効社員情報含む
	 * @param companyCode 会社コード(nullの場合は全G社)
	 * @param staffCode 社員コード
	 * @return 社員リスト
	 */
  getStaff(companyCode: string, staffCode: string): Observable<User> {
    const params = {staffCode};
    if (companyCode) { Object.assign(params, {companyCode}); }
    return this.http.post<User>(this.servicePath + '/getStaff', null, {params});
  }

  /**
   * LOV用一覧検索
   * @param sqlName SQL名
   * @param paramMap SQLパラメータマップ
   * @return LOVデータ一覧
   */
  searchLovList(sqlName: string, paramMap: object): Observable<LovDataEx[]> {
    const params = {sqlName};
    return this.http.post<LovDataEx[]>(this.servicePath + '/searchLovList', JSON.stringify(paramMap), {params});
  }

   /**
    * LOV用一覧検索
    * @param sqlName SQL名
    * @param paramMap SQLパラメータマップ
    *  @param code SQLパラメータマップ
    * @return LOVデータ一覧
    */
  getLovData(sqlName: string, paramMap: object, code: string): Observable<LovDataEx> {
    const params = {sqlName, code};
    return this.http.post<LovDataEx>(this.servicePath + '/getLovData', JSON.stringify(paramMap), {params});
  }

 /**
	 * ユーザー権限_全社権限検索
	 * @param companyCode 会社コード
	 * @param roleCode 管理者権限コード
	 * @param staffCode 社員番号
	 * @return ユーザー権限_全社権限一覧
	 */
  searchRoleAdmin(companyCode: string, roleCode: string, staffCode?: string): Observable<RoleAdmin[]> {
    let params = {companyCode, roleCode};
    if (staffCode) { params = Object.assign(params, {staffCode}); }
    return this.http.post<RoleAdmin[]>(this.servicePath + '/searchRoleAdmin', null, {params});
  }

 /**
	 * ユーザー権限_部署(資産管理担当者)権限検索
	 * @param companyCode 会社コード
	 * @param sectionCode 管理者部署コード
	 * @param staffCode 社員番号
	 * @return ユーザー権限_部署
	 */
  searchRoleSection(companyCode: string, sectionCode?: string, staffCode?: string): Observable<RoleSection[]> {
    let params = {companyCode};
    if (sectionCode) { params = Object.assign(params, {sectionCode}); }
    if (staffCode) { params = Object.assign(params, {staffCode}); }
    return this.http.post<RoleSection[]>(this.servicePath + '/searchRoleSection', null, {params});
  }

 /**
	 * ユーザー権限_部署(資産管理担当者)権限CSVファイル作成
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * @param sectionCode 部署コード
	 * @param staffCode 社員番号
	 */
  createRoleSectionCsv(loginStaffCode: string, companyCode: string, sectionCode: string, staffCode: string): Observable<NonObjectResponse<string>> {
    let params = {loginStaffCode, companyCode};
    if (sectionCode) { params = Object.assign(params, {sectionCode}); }
    if (staffCode) { params = Object.assign(params, {staffCode}); }
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createRoleSectionCsv', null, {params});
  }

 /**
	 * ユーザー権限_全社権限作成
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * @param roleAdmin 全社権限エンティティ
	 */
  createRoleAdmin(loginStaffCode: string, companyCode: string, roleAdmin: RoleAdmin): Observable<void> {
    const params = {loginStaffCode, companyCode};
    return this.http.post<void>(this.servicePath + '/createRoleAdmin', JSON.stringify(roleAdmin), {params});
  }

 /**
	 * ユーザー権限_部署(資産管理担当者)権限作成
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * @param roleSection 部署権限エンティティ
	 */
  createRoleSection(loginStaffCode: string, companyCode: string, roleSection: RoleSection): Observable<void> {
    const params = {loginStaffCode, companyCode};
    return this.http.post<void>(this.servicePath + '/createRoleSection', JSON.stringify(roleSection), {params});
  }

 /**
	 * ユーザー権限_全社権限削除
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * @param roleCode 権限コード
	 * @param staffCode 社員番号
	 */
  deleteRoleAdmin(loginStaffCode: string, companyCode: string, roleCode: string, staffCode: string): Observable<void> {
    const params = {loginStaffCode, companyCode, roleCode, staffCode};
    return this.http.delete<void>(this.servicePath + '/deleteRoleAdmin', {params});
  }

 /**
	 * ユーザー権限_全社権限削除
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * @param roleCode 権限コード
	 * @param staffCode 社員番号
	 */
  // tslint:disable-next-line: no-any
  deleteRoleSection(loginStaffCode: string, companyCode: string, sectionCode: string, staffCode: string): Observable<void> {
    const params = {loginStaffCode, companyCode, sectionCode, staffCode};
    return this.http.delete<void>(this.servicePath + '/deleteRoleSection', {params});
  }

 /**
	 * 汎用マスタ設定検索
	 * @param loginStaffCode ログインユーザ
	 * @param companyCode 会社コード
	 * @param categoryName カテゴリ名
	 * @return 汎用マスタ設定検索一覧
	 */
  searchCodeNameSet(loginStaffCode: string, companyCode: string, categoryName: string, roleCodeList: string[]): Observable<CodeNameSet[]> {
    const params = {loginStaffCode, companyCode, categoryName, roleCodeList};
    return this.http.post<CodeNameSet[]>(this.servicePath + '/searchCodeNameSet', null, {params});
  }

 /**
	 * 汎用マスタ設定検索
	 * @param categoryCode カテゴリーコード
	 * @return 汎用マスタ設定検索一覧
	 */
  getCodeNameSetItemList(categoryCode: string): Observable<CodeNameSetItem[]> {
    const params = {categoryCode};
    return this.http.post<CodeNameSetItem[]>(this.servicePath + '/getCodeNameSetItemList', null, {params});
  }

 /**
	 * コードネーム一覧取得(有効、無効両方のデータ)
	 * @param categoryCode カテゴリコード（必須）
	 * @param internalCode 識別コード（任意）
	 * @param companyCode 会社コード（任意）
	 * @param values 値: 1番目(index0)=value1、2番目(index1)=value2・・・（任意）
	 * @return コードネーム一覧
	 */
  searchCodeNameList(categoryCode: string, isEnableOnly: boolean, internalCode?: string, companyCode?: string, values?: string[]): Observable<CodeName[]> {
    const params = {categoryCode, isEnableOnly: isEnableOnly.toString()};
    if (internalCode) { Object.assign(params, {internalCode}); }
    if (companyCode) { Object.assign(params, {companyCode}); }
    const body = (values) ? JSON.stringify(values) : null;
    return this.http.post<CodeName[]>(this.servicePath + '/searchCodeNameList', body, {params});
  }

 /**
	 * 汎用マスタ更新
	 * @param loginStaffCode 社員番号
	 * @param categoryCode カテゴリーコード
	 * @param companyCode 会社コード
	 * @param codeNameList 汎用データ一覧
	 * @return なし
	 */
  updateCodeNameList(loginStaffCode: string, categoryCode: string, codeNameList: CodeName[], companyCode?: string): Observable<CodeName[]> {
    let params = {loginStaffCode, categoryCode};
    if (companyCode) { params = Object.assign(params, {companyCode}); }
    return this.http.put<CodeName[]>(this.servicePath + '/updateCodeNameList', JSON.stringify(codeNameList), {params});
  }

 /**
	 * 汎用マスタ使用状況
	 * @param obj 汎用マスタ
	 * @return エラーメッセージ
	 */
  getCodeNameUseStatus(obj: CodeName): Observable<NonObjectResponse<string>> {
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/getCodeNameUseStatus', JSON.stringify(obj), {});
  }

 /**
	 * 汎用マスタCSV作成
	 * @param loginStaffCode 社員番号
	 * @param categoryCode カテゴリーコード
	 * @param companyCode 会社コード
	 * @return なし
	 */
  createCodeNameCsv(loginStaffCode: string, categoryCode: string, companyCode: string, setCompanyCode: string): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, categoryCode, companyCode, setCompanyCode};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createCodeNameCsv', null, {params});
  }

 /**
	 * 資産管理担当者プロファイル検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param sectionCode 管理者権限コード
	 * @param staffCode 社員番号
	 * @return 資産管理担当者プロファイル一覧
	 */
  searchSectionRoleProfile(loginStaffCode: string, accessLevel: string, companyCode: string, sectionCode: string, staffCode: string): Observable<RoleSection[]> {
    const params = {loginStaffCode, accessLevel, companyCode, sectionCode, staffCode};
    return this.http.post<RoleSection[]>(this.servicePath + '/searchSectionRoleProfile', null, {params});
  }

 /**
	 * 資産管理担当者プロファイル更新
	 * @param companyCode 会社コード
	 * @param accessLevel アクセスレベル
	 * @param roleSection 資産管理担当エンティティ
	 * @return なし
	 */
  updateSectionRoleProfile(loginStaffCode: string, accessLevel: string, roleSection: RoleSection): Observable<void> {
    const params = {loginStaffCode, accessLevel};
    return this.http.put<void>(this.servicePath + '/updateSectionRoleProfile', JSON.stringify(roleSection), {params});
  }

  /**
   * 経費負担部署検索
   * @param companyCode 会社コード
   * @param sectionName 経費負担部課名
   * @param searchDateFrom 検索日付From
   * @param searchDateTo 検索日付To
   * @return 経費負担部署一覧
   */
  searchCostSection(companyCode: string, sectionName: string, searchDateFrom: Date, searchDateTo: Date): Observable<Section[]> {
    const params = {companyCode, sectionName};
    if (searchDateFrom) { Object.assign(params, {searchDateFrom: this.datePipe.transform(searchDateFrom, 'yyyy-MM-dd')}); }
    if (searchDateTo) { Object.assign(params, {searchDateTo: this.datePipe.transform(searchDateFrom, 'yyyy-MM-dd')}); }
    return this.http.post<Section[]>(this.servicePath + '/searchCostSection', null, {params});
  }

  /**
   * 経費負担部署検索
   * @param companyCode 会社コード
   * @param sectionCode 経費負担部課コード
   * @param searchDateFrom 検索日付From
   * @param searchDateTo 検索日付To
   * @return 経費負担部署
   */
  getCostSection(companyCode: string, sectionName: string, searchDateFrom: Date, searchDateTo: Date): Observable<Section> {
    const params = {companyCode, sectionName};
    if (searchDateFrom) { Object.assign(params, {searchDateFrom: this.datePipe.transform(searchDateFrom, 'yyyy-MM-dd')}); }
    if (searchDateTo) { Object.assign(params, {searchDateTo: this.datePipe.transform(searchDateTo, 'yyyy-MM-dd')}); }
    return this.http.post<Section>(this.servicePath + '/getCostSection', null, {params});
  }

  /**
   * 資産(機器)分類検索
   * @param categoryName 分類名検索キーワード
   * @param selectedAssetCategory1 変更前資産大分類
   * @return 資産(機器)分類リスト
   */
   searchAssetCategory(categoryName: string, selectedAssetCategory1: string): Observable<AssetCategoryNode[]> {
    const params = {categoryName, selectedAssetCategory1};
    return this.http.post<AssetCategoryNode[]>(this.servicePath + '/searchAssetCategory', null, {params});
  }

  /**
   * 資産(機器)一覧取得
   * @param categoryCode カテゴリーコード
   * @param parentCategoryCode 親カテゴリコード
   * @param parentInternalCode 親識別コード
   * @param companyCode 会社コード
   * @param values 値一覧
   * @return ProPlus処理年月
   */
  sarchAstName(parentCategoryCode: string, parentInternalCode: string, companyCode: string, sysdate: Date, makerName: string, astName: string): Observable<LovDataEx[]> {
    const params = {parentCategoryCode, parentInternalCode, companyCode, makerName, astName};
    if (sysdate) { Object.assign(params, {searchDateFrom: this.datePipe.transform(sysdate, 'yyyy-MM-dd')}); }
    return this.http.put<LovDataEx[]>(this.servicePath + '/sarchAstName', null, {params});
  }

  /**
   * ダウンロード項目取得
   * @param itemDefName 項目制御
   * @param lineItem 明細テーブル
   * @return ダウンロード項目一覧
   */
  getDownloadItemList(itemDefName: string, lineItem: string): Observable<CodeName[]> {
    const lineItemConvert = lineItem ? lineItem : null;
    const params = {itemDefName, lineItemConvert};
    return this.http.put<CodeName[]>(this.servicePath + '/getDownloadItemList', null, {params});
  }

    /**
     * 部長検索
     * @param companyCode 会社コード
     * @param holSectionCode 保有部署コード
     * @return 社員リスト
     */
  searchHolApproveStaff(companyCode: string, holSectionCode: string): Observable<User[]> {
    const params = {companyCode, holSectionCode};
    return this.http.post<User[]>(this.servicePath + '/searchHolApproveStaff', null, {params});
  }
}
