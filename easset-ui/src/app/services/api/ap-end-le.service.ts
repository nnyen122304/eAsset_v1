import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class ApEndLeService {
  private servicePath = environment.apiUrl + '/ap-end-le';

  constructor(private http: HttpClient) {
  }

  /**
   * 印刷用PDF生成
   *
   * @param applicationIdList 印刷対象申請書番号一覧
   * @param lineOutputFlag 明細を印刷物に出力するかどうか true:する false:しない
   *
   * @return PDFファイルID(一時領域に作成される)
   */
  createApEndLePdf(applicationIdList: string[], lineOutputFlag: boolean): Observable<NonObjectResponse<string>> {
    const params = {applicationIdList, lineOutputFlag: lineOutputFlag.toString()};
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApEndRePdf', {applicationIdList}, {params});
  }
}
