import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { PpfsStat } from 'src/app/models/api/ppfs-import/ppfs-stat';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';

/**
 * ProPlusデータ取込サービスAPIコール
 */
@Injectable({
  providedIn: 'root'
})
export class PpfsImportService {
  private servicePath = environment.apiUrl + '/ppfsImport'; // サービスパス

  constructor(private http: HttpClient) { }

 /**
	 * ProPlusデータ取込ステータス一覧取得
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル nullの場合は両方
	 * @return ProPlusデータ取込ステータス一覧
	 */
  getPpfsStatList(companyCode: string, dataType?: string): Observable<PpfsStat[]> {
    const params = {companyCode};
    if (dataType) {
      Object.assign(params, {dataType});
    }
    return this.http.post<PpfsStat[]>(this.servicePath + '/getPpfsStatList', null, {params});
  }

 /**
	 * ProPlus処理年月取得(リアルタイム)
	 * @param companyCode 会社コード
	 * @return ProPlus処理年月
	 */

  getPpfsCurrentPeriodRT(companyCode: string): Observable<NonObjectResponse<string>> {
    const params = {companyCode};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/getPpfsCurrentPeriodRT', null, {params});
  }

 /**
	 * ProPlusデータ取込実行
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル nullの場合は両方
	 * @param execStaffCode 実行者社員番号
	 * @param schCreateFlag スケジュール作成フラグ 0:未作成、1:作成
	 */
  callPpfsImport(companyCode: string, dataType: string, execStaffCode: string, schCreateFlag: string): Observable<void> {
    const params = {companyCode, execStaffCode, schCreateFlag};
    if (dataType) {
      Object.assign(params, {dataType});
    }
    return this.http.post<void>(this.servicePath + '/callPpfsImport', null, {params});
  }

}
