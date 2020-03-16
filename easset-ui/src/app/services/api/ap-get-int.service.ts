import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { ApGetInt } from 'src/app/models/api/ap-get-int/ap-get-int.model';
import { ApGetTanSC } from '../../models/api/ap-get-tan/ap-get-tan-sc.model';
import { ApGetTanSR } from '../../models/api/ap-get-tan/ap-get-tan-sr.model';
import { ApGetIntSC } from '../../models/api/ap-get-int/ap-get-int-sc.model';
import { ApGetIntSR } from '../../models/api/ap-get-int/ap-get-int-sr.model';

@Injectable({
  providedIn: 'root'
})

export class ApGetIntService {

  /**
   * サービスパス
   */
  private servicePath = environment.apiUrl + '/ap-get-int';

  constructor(private http: HttpClient) {
  }

  /**
   * 印刷用PDF生成
   *
   * @param applicationList 印刷対象申請書一覧
   *
   * @return PDFファイルID(一時領域に作成される)
   */
  createApGetIntPdf(applicationList: ApGetInt[], lineOutputFlag): Observable<NonObjectResponse<string>> {
    const params = {lineOutputFlag};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApGetIntPdf', JSON.stringify(applicationList), {params});
  }

  /**
   * 履歴印刷用PDF生成
   *
   * @param applicationId 印刷対象申請書番号
   * @param applicationVersion 印刷対象申請書バージョン
   * @param version バージョン
   *
   * @return PDFファイルID(一時領域に作成される)
   */
  createApGetIntHistPdf(applicationId: string, applicationVersion: string, version: number): Observable<NonObjectResponse<string>> {
    const params = {applicationId, applicationVersion, version: version.toString()};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApGetIntHistPdf', null, {params});
  }

  /**
   * 取得申請(無形)検索
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   *
   * @return 検索結果
   */
  searchApGetInt(loginStaffCode: string, accessLevel: string, searchParam: ApGetIntSC): Observable<ApGetIntSR[]> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<ApGetIntSR[]>(this.servicePath + '/searchApGetInt', JSON.stringify(searchParam), {params});
  }

  /**
   * 取得申請(無形)検索結果CSV作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   * @param lineOutputFlag false:申請書単位,true:明細単位
   *
   * @return CSVファイルID(一時領域に作成される)
   */
  createApGetIntCsv(loginStaffCode: string, accessLevel: string, searchParam: ApGetIntSC, lineOutputFlag: boolean = false): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, lineOutputFlag: lineOutputFlag.toString()};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApGetIntCsv', JSON.stringify(searchParam), {params});
  }

  /**
   * 取得申請(無形)検索結果CSV作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   * @param lineOutputFlag false:申請書単位,true:明細単位
   *
   * @return CSVファイルID(一時領域に作成される)
   */
  remindApGetInt(loginStaffCode: string, accessLevel: string, applicationList: ApGetInt[]): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/remindApGetInt', JSON.stringify(applicationList), {params});
  }
}
