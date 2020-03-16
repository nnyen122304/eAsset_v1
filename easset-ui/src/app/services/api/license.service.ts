import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LicenseSR } from 'src/app/models/api/license/license-sr.model';
import { LicenseSC } from 'src/app/models/api/license/license-sc.model';
import { ApGetTan } from 'src/app/models/api/ap-get-tan/ap-get-tan.model';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { License } from 'src/app/models/api/license/license.model';
import { DatePipe } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class LicenseService {
  /**
   * サービスパス
   */
  private servicePath = environment.apiUrl + '/license';

  constructor(private http: HttpClient, private datePipe: DatePipe) {
  }

  /**
   * ライセンス検索
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   * @param isAp true:ライセンス登録申請,false:ライセンス等
   *
   *  @return なし
   */
  searchLicense(loginStaffCode: string, accessLevel: string, searchParam: LicenseSC, isAp: boolean): Observable<LicenseSR[]> {
    const params = {loginStaffCode, accessLevel, isAp: String(isAp)};
    return this.http.post<LicenseSR[]>(this.servicePath + '/searchLicense', JSON.stringify(searchParam), {params});
  }

  /**
   * 検索結果CSV作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param outputPropList 出力対象項目名
   * @param searchParam    検索条件
   * @param isAp true:ライセンス登録申請,false:ライセンス
   *
   * @return CSVファイルID(一時領域に作成される)
   */
  createLicenseCsv(loginStaffCode: string, accessLevel: string, outputPropList: string[], searchParam: LicenseSC, isAp: boolean): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, outputPropList, isAp: isAp.toString()};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createLicenseCsv', JSON.stringify(searchParam), {params});
  }

  /**
   * 割当情報(ライセンスから検索)-CSVファイル作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   *
   *  @return なし
   */
  createAllocLicenseCsv(loginStaffCode: string, accessLevel: string, searchParam: LicenseSC): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createAllocLicenseCsv', JSON.stringify(searchParam), {params});
  }

  /**
   * アップグレード情報-CSVファイル作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   *
   *  @return なし
   */
  createUpgradeCsv(loginStaffCode: string, accessLevel: string, searchParam: LicenseSC): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createUpgradeCsv', JSON.stringify(searchParam), {params});
  }

  /**
   * 登録申請照会(e申請)
   *
   * @param eappId e申請書類ID
   * @param execStaffCode 処理実行者
   *
   *  @return なし
   */
  searchApLicenseEapp(eappId: number, loginStaffCode: string): Observable<LicenseSR[]> {
    const params = {eappId: eappId.toString(), loginStaffCode};
    return this.http.post<LicenseSR[]>(this.servicePath + '/searchApLicenseEapp', null, {params});
  }

  /**
   * 取得申請(有形)明細の登録残明細から登録申請登録CSV作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param isTermUpdate タームライセンスの更新かどうか
   * @param apGetTanList 取得申請データ一覧
   *
   * @return CSVファイル識別ID
   */
  createApLicenseCsvByApGetTan(loginStaffCode: string, accessLevel: string, isTermUpdate: boolean, apGetTanList: ApGetTan[]): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, isTermUpdate: isTermUpdate.toString()};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApLicenseCsvByApGetTan', JSON.stringify(apGetTanList), {params});
  }

 /**
	 * ライセンス抹消
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param deleteDate 抹消日
	 * @param deleteDate 抹消理由
	 * @param licenseList 抹消対象のライセンスリスト
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
  deleteLicenseLogical(loginStaffCode: string, accessLevel: string, deleteDate: Date, deleteReason: string, licenseList: License[]): Observable<void> {
    const params = { loginStaffCode, accessLevel, deleteReason };
    if (deleteDate) { Object.assign(params, { deleteDate: this.datePipe.transform(deleteDate, 'yyyy-MM-dd') }); }
    return this.http.put<void>(this.servicePath + '/deleteLicenseLogical', JSON.stringify(licenseList), { params });
  }
}
