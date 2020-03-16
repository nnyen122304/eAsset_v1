import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { ApChange } from 'src/app/models/api/ap-change/ap-change.model';
import { ApChangeSC } from 'src/app/models/api/ap-change/ap-change-sc.model';
import { ApChangeSR } from 'src/app/models/api/ap-change/ap-change-sr.model';

@Injectable({
  providedIn: 'root'
})
export class ApChangeService {
  /**
   * サービスパス
   */
  private servicePath = environment.apiUrl + '/ap-change';

  constructor(private http: HttpClient) {
  }

  /**
   * 移動申請検索
   *
   * @param applicationId 移動申請書番号
   *
   * @return 検索結果
   */
  getApChange(applicationId: string): Observable<ApChange> {
    const params = {applicationId};
    return this.http.post<ApChange>(this.servicePath + '/getApChange', null, {params});
  }

  /**
   * 移動申請検索(e申請)
   *
   * @param applicationId 移動申請書番号
   *
   * @return 検索結果
   */
  getApChangeEapp(eappId: number): Observable<ApChange> {
    const params = {eappId: eappId.toString()};
    return this.http.post<ApChange>(this.servicePath + '/getApChangeEapp', null, {params});
  }

  /**
   * 移動申請検索
   *
   * @param loginStaffCode ログイン社員番号
   * @param  accessLevel アクセスレベル
   * @param  searchParam 検索条件
   *
   * @return 検索結果
   */
  searchApChange(loginStaffCode: string, accessLevel: string, searchParam: ApChangeSC): Observable<ApChangeSR[]> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<ApChangeSR[]>(this.servicePath + '/searchApChange', JSON.stringify(searchParam), {params});
  }

  /**
   * 移動申請更新
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param searchParam 移動申請データ
   *
   * @return 作成したデータの取得申請書番号
   */
  updateApChange(loginStaffCode: string, accessLevel: string, obj: ApChange): Observable<void> {
    const params = {loginStaffCode, accessLevel};
    return this.http.put<void>(this.servicePath + '/updateApChange', JSON.stringify(obj), {params});
  }

  /**
   * 移動申請作成
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param obj 移動申請データ
   *
   * @return 作成したデータの取得申請書番号
   */
  createApChange(loginStaffCode: string, accessLevel: string, obj: ApChange): Observable<void> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<void>(this.servicePath + '/createApChange', JSON.stringify(obj), {params});
  }

  /**
   * 移動申請承認依頼
   *
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param obj 移動申請データ
   *
   * @return 作成・更新したデータの移動申請書番号
   */
  applyApChange(loginStaffCode: string, accessLevel: string, obj: ApChange): Observable<NonObjectResponse<string>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.put<NonObjectResponse<string>>(this.servicePath + '/applyApChange', JSON.stringify(obj), {params});
  }

  //   /**
  //    * 移動申請承認依頼
  //    * @param loginStaffCode ログイン社員番号
  //    * @param accessLevel    アクセスレベル
  //    * @param obj 移動申請データ
  //    * @return 作成・更新したデータの移動申請書番号
  //    */
  //   deleteApChange( loginStaffCode: string, accessLevel: string, obj: ApChange): Observable<void> {
  //   const params = { loginStaffCode, accessLevel, obj };
  //   return this.http.delete<void>( this.servicePath + '/deleteApChange', { params });
  // }
  /**
   * 承認(eAsset画面から)
   *
   * @param loginStaffCode ログイン社員番号
   * @param obj 移動申請データ
   *
   * @return なし
   */
  approveApChange(loginStaffCode: string, obj: ApChange): Observable<void> {
    const params = {loginStaffCode};
    return this.http.put<void>(this.servicePath + '/approveApChange', JSON.stringify(obj), {params});
  }

  /**
   * 差戻し/却下(eAsset画面から)
   *
   * @param loginStaffCode ログイン社員番号
   * @param obj 移動申請データ
   * @param rejectType 却下区分
   * @param rejectReason 却下コメント
   *
   * @return なし
   */
  rejectApChange(loginStaffCode: string, obj: ApChange, rejectType: string, rejectReason: string): Observable<void> {
    const params = {loginStaffCode, rejectType, rejectReason};
    return this.http.put<void>(this.servicePath + '/rejectApChange', JSON.stringify(obj), {params});
  }
}
