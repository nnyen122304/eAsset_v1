import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { ApBgnInt } from 'src/app/models/api/ap-bgn-int/ap-bgn-int.model';
import { ApBgnIntLineFld } from 'src/app/models/api/ap-bgn-int/ap-bgn-int-line-fld.model';

@Injectable({
  providedIn: 'root'
})
export class ApBgnIntService {

  /**
   * サービスパス
   */
  private servicePath = environment.apiUrl + '/ap-bgn-int';

  constructor(private http: HttpClient) {
  }

  /**
   * 印刷用PDF生成
   *
   * @param applicationList 印刷対象申請書一覧
   *
   * @return PDFファイルID(一時領域に作成される)
   */
  createApBgnIntPdf(applicationList: string[]): Observable<NonObjectResponse<string>> {
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApBgnIntPdf', JSON.stringify(applicationList));
  }

  /**
   * 履歴印刷用PDF生成
   *
   * @param applicationId 印刷対象申請書番号
   * @param version バージョン
   *
   * @return PDFファイルID(一時領域に作成される)
   */
  createApBgnIntHistPdf(applicationId: string, version: number): Observable<NonObjectResponse<string>> {
    const params = {applicationId, version: version.toString()};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApBgnIntPdf', null, {params});
  }

  /**
   *  取得申請書(無形)からサービス提供開始報告データ取得
   *
   * @param apGetIntId 取得申請書番号
   * @param apGetIntVersion 取得申請書バージョン
   *
   * @return サービス提供開始報告データ
   */
  getApBgnIntByApGetInt(apGetIntId?: string, apGetIntVersion?: string): Observable<ApBgnInt> {
    const params = {apGetIntId, apGetIntVersion};
    return this.http.post<ApBgnInt>(this.servicePath + '/getApBgnIntByApGetInt', null, {params});
  }

  /**
   * サービス提供開始報告作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param apBgnInt サービス提供開始報告データ
   *
   * @return なし
   */
  createApBgnInt(loginStaffCode: string, accessLevel: string, apBgnInt: {}): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApBgnInt', JSON.stringify(apBgnInt), {params});
  }

  /**
   *  サービス提供開始報告取得
   *
   * @param loadId 登録画面読込用IDセット
   *
   * @return サービス提供開始報告データ
   */
  getApBgnInt(applicationId?: string): Observable<ApBgnInt> {
    const params = {applicationId};
    return this.http.post<ApBgnInt>(this.servicePath + '/getApBgnInt', null, {params});
  }

  /**
   *  サービス提供開始報告検索
   *
   * @param loadId 登録画面読込用IDセット
   *
   * @return サービス提供開始報告データ
   */
  getApBgnIntEapp(eappId?: string): Observable<ApBgnInt> {
    const params = {eappId};
    return this.http.post<ApBgnInt>(this.servicePath + '/getApBgnIntEapp', null, {params});
  }

  /**
   * サービス提供開始報告更新
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param apBgnInt サービス提供開始報告データ
   *
   * @return なし
   */
  updateApBgnInt(loginStaffCode: string, accessLevel: string, apBgnInt: {}): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.put<NonObjectResponse<string>>(this.servicePath + '/updateApBgnInt', JSON.stringify(apBgnInt), {params});
  }

  /**
   * サービス提供開始報告削除
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param apBgnInt サービス提供開始報告データ
   *
   * @return なし
   */
  deleteApBgnInt(loginStaffCode: string, accessLevel: string, apBgnInt: {}): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/deleteApBgnInt', JSON.stringify(apBgnInt), {params});
  }

  /**
   * サービス提供開始報告承認依頼
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param apBgnInt サービス提供開始報告データ
   * @param isNew 新規作成フラグ(true:新規作成,false:更新)sss
   *
   * @return 作成・更新したデータのサービス提供開始報告書番号
   */
  applyApBgnInt(loginStaffCode: string, accessLevel: string, apBgnInt: {}, isNew = false): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel, isNew: isNew.toString()};
    return this.http.put<NonObjectResponse<string>>(this.servicePath + '/applyApBgnInt', JSON.stringify(apBgnInt), {params});
  }

  /**
   * 取得申請書番号からProPlusの資産明細情報取得
   *
   * @param companyCode 会社コード
   * @param applicationId 取得申請書番号
   * @param lineType 明細区分 1:申請時,2:承認時追加分
   *
   * @return サービス提供開始報告データ
   */
  getPpfsFldList(companyCode: string, applicationId: string, lineType: string) {
    const params = {companyCode, applicationId, lineType};
    return this.http.post<ApBgnIntLineFld[]>(this.servicePath + '/getPpfsFldList', null, {params});
  }
}
