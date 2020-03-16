import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { NonObjectResponse } from 'src/app/models/api/non-object-response.model';
import { AssetSR } from 'src/app/models/api/asset/asset-sr.model';
import { AssetSC } from 'src/app/models/api/asset/asset-sc.model';
import { ApGetTan } from 'src/app/models/api/ap-get-tan/ap-get-tan.model'
import { Asset } from 'src/app/models/api/asset/asset.model';
import { DatePipe, JsonPipe } from '@angular/common';

@Injectable({
  providedIn: 'root'
})
export class ApAssetService {
  /**
   * サービスパス
   */
  private servicePath = environment.apiUrl + '/ap-asset';

  constructor(private http: HttpClient, private datePipe: DatePipe) {
  }

  /**
   * ファイル・直入力可能マスタ値CSV作成
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param companyCode 会社コード
   * @param propertyList 項目名一覧
   * @return CSVファイル識別ID
   */
  createAssetPossibleInputMasterCsv(loginStaffCode: string, accessLevel: string, companyCode: string, propertyList: string[]): Observable<NonObjectResponse<string>> {
    const params = { loginStaffCode, accessLevel, companyCode };
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createAssetPossibleInputMasterCsv', JSON.stringify(propertyList), { params });
  }


  /**
   * 情報機器検索
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   * @param isAp true:情報機器等登録申請,false:情報機器等
   * @return List AssetSR
   */
  searchAsset(loginStaffCode: string, accessLevel: string, searchParam: AssetSR, isAp: boolean): Observable<AssetSR[]> {
    const params = { loginStaffCode, accessLevel, isAp: JSON.stringify(isAp) };
    return this.http.post<AssetSR[]>(this.servicePath + '/searchAsset', JSON.stringify(searchParam), { params });
  }

  /**
   * 割当情報(機器から検索)-CSVファイル作成
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param searchParam    検索条件
   * @return CSVファイルID(一時領域に作成される)
   */
  createAllocAssetCsv(loginStaffCode: string, accessLevel: string, searchParam: AssetSC): Observable<NonObjectResponse<string>> {
    const params = { loginStaffCode, accessLevel };
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createAllocAssetCsv', JSON.stringify(searchParam), { params });
  }

  /**
   * 検索結果CSV作成
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param outputPropList 出力対象項目名
   * @param searchParam    検索条件
   * @param isAp true:情報機器等登録申請,false:情報機器等
   * @return CSVファイルID(一時領域に作成される)
   */
  createAssetCsv(loginStaffCode: string, accessLevel: string, outputPropList: string[], searchParam: AssetSC, isAp: boolean): Observable<NonObjectResponse<string>> {
    const params = { loginStaffCode, accessLevel, searchParam: JSON.stringify(searchParam), isAp: isAp.toString() };
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createAssetCsv', JSON.stringify(outputPropList), { params });
  }

  /**
   * 取得申請明細の登録残明細から登録申請登録CSV作成
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel アクセスレベル
   * @param apGetTanList 取得申請データ一覧
   * @return CSVファイル識別ID
   */
  createApAssetCsvByApGetTan(loginStaffCode: string, accessLevel: string, apGetTanList: ApGetTan[]): Observable<NonObjectResponse<string>> {
    const params = { loginStaffCode, accessLevel };
    return this.http.post<NonObjectResponse<string>>(this.servicePath + '/createApAssetCsvByApGetTan', JSON.stringify(apGetTanList), { params });
  }

  /**
   * 情報機器抹消
   * @param loginStaffCode ログイン社員番号
   * @param accessLevel    アクセスレベル
   * @param deleteDate 抹消日
   * @param deleteReason 抹消理由
   * @param assetList 抹消対象の情報機器リスト
   * @throws AppException バリデーションエラー時に発生
   * @return なし
   */
  deleteAssetLogical(loginStaffCode: string, accessLevel: string, deleteDate: Date, deleteReason: string, assetList: Asset[]): Observable<void> {
    const params = { loginStaffCode, accessLevel, deleteReason };
    if (deleteDate) { Object.assign(params, { deleteDate: this.datePipe.transform(deleteDate, 'yyyy-MM-dd') }); }
    return this.http.put<void>(this.servicePath + '/deleteAssetLogical', JSON.stringify(assetList), { params });
  }

 /**
	 * 一括申請
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apAssetList 一括処理対象の登録申請一覧
	 * @throws AppException
	 */
  bulkApplyApAsset(loginStaffCode: string, accessLevel: string, apAssetList: Asset[]): Observable<NonObjectResponse<void>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.put<NonObjectResponse<void>>(this.servicePath + '/bulkApplyApAsset', JSON.stringify(apAssetList), {params});
  }

 /**
	 * 一括削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apAssetList 一括処理対象の登録申請一覧
	 * @throws AppException
	 */
  bulkDeleteApAsset(loginStaffCode: string, accessLevel: string, apAssetList: Asset[]): Observable<NonObjectResponse<void>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<NonObjectResponse<void>>(this.servicePath + '/bulkDeleteApAsset', JSON.stringify(apAssetList), {params});
  }

 /**
	 * 一括情報機器等登録
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apAssetList 一括処理対象の登録申請一覧
	 * @throws AppException
	 */
  bulkCreateAsset(loginStaffCode: string, accessLevel: string, apAssetList: Asset[]): Observable<NonObjectResponse<void>> {
    const params = {loginStaffCode, accessLevel};
    return this.http.post<NonObjectResponse<void>>(this.servicePath + '/bulkCreateAsset', JSON.stringify(apAssetList), {params});
  }
}
