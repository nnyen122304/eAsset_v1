import { Injectable } from '@angular/core';
import { DatePipe } from '@angular/common';
import { environment } from '../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { BulkUpdateHist } from 'src/app/models/api/hist/bulk-update-hist';

/**
 * 履歴サービスAPIコール
 */
@Injectable({
  providedIn: 'root'
})
export class HistService {
  private servicePath = environment.apiUrl + '/hist'; // サービスパス

  constructor(private http: HttpClient, private datePipe: DatePipe) { }

 /**
	 * 一括更新履歴一覧検索
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @param function 機能
	 * @param createDateFrom 作成日From
	 * @param createDateTo 作成日To
	 * @param isExecOnly true:実行中のみ検索 false:全て検索
	 * @return 履歴一覧
	 * @throws SQLException
	 */
  getBulkUpdateHistList(companyCode: string, staffCode: string, functionName: string, createDateFrom: Date, createDateTo: Date, isExecOnly: boolean): Observable<BulkUpdateHist[]> {
    const params = {
      companyCode,
      function: functionName,
      isExecOnly: isExecOnly.toString()
    };
    if (staffCode) { Object.assign(params, {staffCode}); }
    if (createDateFrom) { Object.assign(params, {createDateFrom: this.datePipe.transform(createDateFrom, 'yyyy-MM-dd')}); }
    if (createDateTo) { Object.assign(params, {createDateTo: this.datePipe.transform(createDateTo, 'yyyy-MM-dd')}); }

    return this.http.post<BulkUpdateHist[]>(this.servicePath + '/getBulkUpdateHistList', null, {params});
  }

 /**
	 * 一括更新ログ更新
	 * @param logId ログID
	 * @param staffCode 社員番号
	 * @param execStatus 実行ステータス
	 * @param execCount データ件数
	 * @param successCount 処理成功件数
	 * @param failureCount 処理失敗件数
	 */
  updateBulkUpdateLog(logId: number, staffCode: string, execStatus: string, execCount: number, successCount: number, failureCount: number): Observable<void> {
    const params = {
      logId: logId.toString(),
      staffCode,
      execStatus
    };
    return this.http.post<void>(this.servicePath + '/updateBulkUpdateLog', null, {params});
  }

    /**
     * 更新履歴一覧検索
     * @param entityName 履歴一覧を検索するエンティティ(テーブル)名
     * @param keyValue キー値
     * @return 履歴一覧
     */
  getHistList(entityName: string, keyValue: string): Observable<BulkUpdateHist[]> {
    const params = { entityName, keyValue };
    return this.http.post<BulkUpdateHist[]>(this.servicePath + '/getHistList', null, {params});
  }
}
