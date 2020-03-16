/*===================================================================
 * ファイル名 : AssetSession.java
 * 概要説明   : 情報機器等、情報機器等登録申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-11-21 1.0  リヨン           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan;
import jp.co.ctcg.easset.dto.asset.Asset;
import jp.co.ctcg.easset.dto.asset.AssetSC;
import jp.co.ctcg.easset.dto.asset.AssetSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("ap-asset")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface AssetSession {

	/**
	 * 情報機器検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("searchAsset")
	public List<AssetSR> searchAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, AssetSC searchParam, @QueryParam("isAp") boolean isAp);

	/**
	 * 検索結果CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param searchParam    検索条件
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return CSVファイルID(一時領域に作成される)
	 * @throws SQLException
	 */
	@POST
	@Path("createAssetCsv")
	public String createAssetCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<String> outputPropList, @QueryParam("searchParam") AssetSC searchParam, @QueryParam("isAp") boolean isAp);

	/**
	 * 割当情報(機器から検索)-CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return CSVファイルID(一時領域に作成される)
	 * @throws SQLException
	 */
	@POST
	@Path("createAllocAssetCsv")
	public String createAllocAssetCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, AssetSC searchParam);

	/**
	 * 情報機器検索
	 * @param assetId 情報機器番号
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("getAsset")
	public Asset getAsset(@QueryParam("assetId") String assetId, @QueryParam("isAp") boolean isAp);

	/**
	 *  情報機器作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 情報機器データ
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの情報機器番号
	 */
	@POST
	@Path("createAsset")
	public String createAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, Asset obj, @QueryParam("isAp") boolean isAp) throws AppException;

	/**
	 *  情報機器更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 情報機器データ
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("updateAsset")
	public void updateAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, Asset obj, @QueryParam("isAp") boolean isAp) throws AppException;

	/**
	 *  情報機器更新：保存時の操作(履歴表示用)指定　※ 明細の更新は行わない
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 情報機器データ
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @param operation 操作(履歴表示用)
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	public void updateAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp, String operation) throws AppException;

	/**
	 * 情報機器抹消
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param deleteDate 抹消日
	 * @param deleteDate 抹消理由
	 * @param assetList 抹消対象の情報機器リスト
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("deleteAssetLogical")
	public void deleteAssetLogical(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("deleteDate") Date deleteDate,  @QueryParam("deleteReason") String deleteReason,  List<Asset> assetList) throws AppException;

	/**
	 * 情報機器削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 情報機器データ
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@POST
	@Path("deleteAsset")
	public void deleteAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, Asset obj, @QueryParam("isAp") boolean isAp) throws AppException;

	/**
	 * 情報機器抹消取消
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param assetId 情報機器管理番号
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("restoreAsset")
	public void restoreAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, Asset obj) throws AppException;

	/**
	 * 情報機器等登録申請承認依頼
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 登録申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成・更新したデータの取得申請書番号
	 */
	@PUT
	@Path("applyApAsset")
	public String applyApAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, Asset obj) throws AppException;

	/**
	 * 情報機器等登録申請承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveApAsset")
	public void approveApAsset(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 情報機器等登録申請引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApAsset")
	public void cancelApplyApAsset(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 情報機器等登録申請差戻し/却下
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("rejectApAsset")
	public void rejectApAsset(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;

	/**
	 * 登録申請照会(e申請)
	 * @param eappId e申請書類ID
	 * @param execStaffCode 処理実行者
	 */
	@POST
	@Path("searchApAssetEapp")
	public List<AssetSR> searchApAssetEapp(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 取得申請明細から登録申請データ取得
	 * @param apGetTanLineAstId 取得申請明細識別ID
	 * @return 登録申請データ
	 */
	@POST
	@Path("getApAssetByApGetTan")
	public Asset getApAssetByApGetTan(@QueryParam("apGetTanLineAstId") Long apGetTanLineAstId);

	/**
	 * 取得申請明細の登録残明細から登録申請登録CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apGetTanList 取得申請データ一覧
	 * @return CSVファイル識別ID
	 */
	@POST
	@Path("createApAssetCsvByApGetTan")
	public String createApAssetCsvByApGetTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<ApGetTan> apGetTanList);

	/**
	 * ファイル・直入力可能マスタ値CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param propertyList 項目名一覧
	 * @return CSVファイル識別ID
	 */
	@POST
	@Path("createAssetPossibleInputMasterCsv")
	public String createAssetPossibleInputMasterCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, List<String> propertyList);

	/**
	 * ファイル・直入力可能マスタ値CSV作成(保有会社変更用)
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @return CSVファイル識別ID
	 */
	@POST
	@Path("createAssetCompanyMoveInputMasterCsv")
	public String createAssetCompanyMoveInputMasterCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode);

	/**
	 * CSVファイルから登録申請一括作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル識別ID
	 * @param obj 申請データ
	 * @param isVM true:仮想機器, false:仮想機器以外
	 * @return 作成した登録申請の登録申請番号リスト
	 * @throws AppException
	 */
	@POST
	@Path("createApAssetBulk")
	public List<String> createApAssetBulk(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId, Asset obj, @QueryParam("isVM") boolean isVM) throws AppException;

	/**
	 * CSVファイルから情報機器等一括更新
	 * @param companyCode 会社コード
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル識別ID
	 * @param updatePropertyList 更新対象項目一覧
	 * @throws AppException
	 */
	@PUT
	@Path("callUpdateAssetBulk")
	public void callUpdateAssetBulk(@QueryParam("companyCode") String companyCode, @QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId, List<CodeName> updatePropertyList) throws AppException;

	/**
	 * CSVファイルから情報機器等一括更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param fileId CSVファイル識別ID
	 * @param execFile CSVファイル
	 * @param updatePropertyList 更新対象項目一覧
	 * @param logId 一括更新更新ログID
	 * @throws AppException
	 */
	public void updateAssetBulk(String loginStaffCode, String accessLevel, String companyCode, String fileId, File execFile, List<CodeName> updatePropertyList, Long logId);

	/**
	 * 情報機器等一括更新の一行処理
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param rowCt 処理対象のカレント行
	 * @param updateObj 更新対象
	 * @param isLineUpdate true:明細更新, false:ヘッダ更新
	 * @param updatePropSet 更新対象プロパティ情報
	 * @param apChangeTypeMap 移動申請判定用
	 * @param readMethods プロパティ設定用getterメソッド
	 * @param writeMethods プロパティ設定用setterメソッド
	 * @throws AppException
	 */
	public void updateAssetBulkRow(String loginStaffCode, String accessLevel, int rowCt, Asset updateObj, boolean isLineUpdate, Set<String> updatePropSet, Map<String, String> apChangeTypeMap, Method[] readMethods, Method[] writeMethods) throws AppException;

	/**
	 * CSVファイルから登録申請一括申請
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル識別ID
	 * @param obj 申請データ
	 * @param isVM true:仮想機器, false:仮想機器以外
	 * @return 作成した登録申請の登録申請番号リスト
	 * @throws AppException
	 */
	@PUT
	@Path("applyCreateApAssetBulk")
	public List<String> applyCreateApAssetBulk(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId, Asset obj, @QueryParam("isVM") boolean isVM) throws AppException;

	/**
	 * 一括申請
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apAssetList 一括処理対象の登録申請一覧
	 * @throws AppException
	 */
	@PUT
	@Path("bulkApplyApAsset")
	public void bulkApplyApAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<Asset> apAssetList) throws AppException;

	/**
	 * 一括削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apAssetList 一括処理対象の登録申請一覧
	 * @throws AppException
	 */
	@POST
	@Path("bulkDeleteApAsset")
	public void bulkDeleteApAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<Asset> apAssetList) throws AppException;

	/**
	 * 一括情報機器等登録
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apAssetList 一括処理対象の登録申請一覧
	 * @throws AppException
	 */
	@POST
	@Path("bulkCreateAsset")
	public void bulkCreateAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<Asset> apAssetList) throws AppException;

	/**
	 * 情報機器から仮想機器登録申請データ取得
	 * @param assetId
	 * @return
	 */
	@POST
	@Path("getApAssetVMByAsset")
	public Asset getApAssetVMByAsset(@QueryParam("assetId") String assetId);

	/**
	 * 情報機器(実機)から登録申請登録CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param assetList 情報機器(実機)データ一覧
	 * @return CSVファイル識別ID
	 */
	@POST
	@Path("createApAssetVMCsvByAsset")
	public String createApAssetVMCsvByAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<Asset> assetList);

	/**
	 * assetIdに指定された情報機器が修正可能かどうかを判別
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param assetId 情報機器管理番号
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 */
	@POST
	@Path("isEditableAsset")
	public boolean isEditableAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("assetId") String assetId, @QueryParam("isAp") boolean isAp);

	/**
	 * 警告バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	@POST
	@Path("isEditableAsset")
	public String validateWarningAsset(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, Asset obj, @QueryParam("isAp") boolean isAp);

	/**
	 * CSVファイルから更新可能プロパティ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル識別ID
	 * @return 更新可能プロパティリスト
	 * @throws AppException
	 */
	@POST
	@Path("getUpdatePropertyList")
	public List<CodeName> getUpdatePropertyList(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId) throws AppException;

	/**
	 * EGuardADにデータ作成(ネットワーク接続用)
	 * @param obj 情報機器等
	 * @return true:作成 false:未作成（既に存在している）
	 */
	public boolean createEGuardAsset(Asset obj);

	/**
	 * EGuardADからデータ削除(ネットワーク接続用)
	 * @param obj 情報機器等
	 * @return true:削除 false:未削除（存在しない）
	 */
	public boolean deleteEGuardAsset(Asset obj);

	/**
	 * EGuardADへデータ同期(ネットワーク接続用)
	 * データ追加：抹消されていない接続対象資産
	 * データ削除：上記以外
	 * @param obj 情報機器等
	 * @return 0:処理無し、1:データ追加、2:データ削除
	 */
	public int syncEGuardAsset(Asset obj);

	/**
	 * 情報機器等更新本体(バリデーションチェック指定)
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 情報機器等データ
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @param operation 操作
	 * @param isValidate バリデータチェックフラグ
	 * @throws AppException
	 */
	public void updateAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp, String operation, boolean isValidate) throws AppException;


}
