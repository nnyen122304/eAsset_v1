import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApGetTan } from '../../models/api/ap-get-tan/ap-get-tan.model';
import { ApGetTanSR } from '../../models/api/ap-get-tan/ap-get-tan-sr.model';
import { ApGetTanSC } from '../../models/api/ap-get-tan/ap-get-tan-sc.model';
import { NonObjectResponse } from '../../models/api/non-object-response.model';

/**
 * 取得申請APIコール
 */
@Injectable({
  providedIn: 'root'
})
export class ApGetTanService {
  private servicePath = environment.apiUrl + '/apGetTan'; // サービスパス
  constructor(private http: HttpClient) {
  }

  /**
   * 取得申請検索
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param searchParam 検索条件
   *
   * @return 検索結果
   */
  searchApGetTan(loginStaffCode: string, accessLevel: string, searchParam: ApGetTanSC): Observable<ApGetTanSR[]> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<ApGetTanSR[]>(this.servicePath + '/searchApGetTan', JSON.stringify(searchParam), {params});
  }

  /**
   * 検索結果CSV作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param searchParam 検索条件
   *
   * @return CSVファイルID(一時領域に作成される)
   */
  createApGetTanCsv(loginStaffCode: string, accessLevel: string, searchParam: ApGetTanSC, lineOutputFlag?: boolean): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, lineOutputFlag: lineOutputFlag.toString()};
    return this.http.post<NonObjectResponse<string>>(
      this.servicePath + '/createApGetTanCsv', JSON.stringify(searchParam), {params}
    );
  }

  /**
   * 申請情報取得
   *
   * @param applicationId 取得申請書番号
   *
   * @return 取得申請データ
   */
  getApGetTan(applicationId: string): Observable<ApGetTan> {
    const params = {applicationId};
    return this.http.post<ApGetTan>(this.servicePath + '/getApGetTan', null, {params});
  }

  /**
   * 取得申請取得
   *
   * @param eappId e申請書類ID
   *
   * @return 取得申請データ
   */
  getApGetTanEapp(eappId: number): Observable<ApGetTan> {
    const params = {eappId: String(eappId)};
    return this.http.post<ApGetTan>(this.servicePath + '/getApGetTanEapp', null, {params});
  }

  /**
   * 取得申請更新
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param obj 取得申請データ
   *
   * @return なし
   */
  updateApGetTan(loginStaffCode: string, accessLevel: string, apGetTan: ApGetTan): Observable<void> {
    const params = {loginStaffCode, accessLevel};
    return this.http.put<void>(this.servicePath + '/updateApGetTan', JSON.stringify(apGetTan), {params});
  }

  /**
   * 印刷用PDF生成
   *
   * @param applicationIdList 印刷対象申請書番号一覧
   * @param lineOutputFlag 明細を印刷物に出力するかどうか true:する false:しない
   *
   * @return PDFファイルID(一時領域に作成される)
   */
  createApGetTanPdf(applicationIdList: string[], lineOutputFlag: boolean): Observable<NonObjectResponse<string>> {
    const params = {lineOutputFlag: String(lineOutputFlag)};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApGetTanPdf', JSON.stringify(applicationIdList), {params});
  }

  /**
   * 履歴印刷用PDF生成
   *
   * @param applicationId 印刷対象申請書番号
   * @param version バージョン
   *
   * @return PDFファイルID(一時領域に作成される)
   */
  createApGetTanHistPdf(applicationId: string, version: number): Observable<NonObjectResponse<string>> {
    const params = {applicationId, version: version.toString()};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApGetTanHistPdf', null, {params});
  }
}
