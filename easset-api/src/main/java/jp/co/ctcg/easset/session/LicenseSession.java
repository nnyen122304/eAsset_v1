/*===================================================================
 * ファイル名 : LicenseSession.java
 * 概要説明   : ライセンス、ライセンス登録申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.io.File;
import java.io.IOException;
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
import jp.co.ctcg.easset.dto.license.License;
import jp.co.ctcg.easset.dto.license.LicenseAlloc;
import jp.co.ctcg.easset.dto.license.LicenseSC;
import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.dto.license.Software;
import jp.co.ctcg.easset.dto.license.SoftwareMaker;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("license")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface LicenseSession {

	/**
	 * ライセンス検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param isAp true:ライセンス登録申請,false:ライセンス等
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("searchLicense")
	public List<LicenseSR> searchLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, LicenseSC searchParam, @QueryParam("isAp") boolean isAp);

	/**
	 * ライセンス取得
	 * @param licenseId ライセンス管理番号
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("getLicense")
	public License getLicense(@QueryParam("licenseId") String licenseId, @QueryParam("isAp") boolean isAp);

	/**
	 * 検索結果CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param searchParam    検索条件
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @return CSVファイルID(一時領域に作成される)
	 * @throws SQLException
	 */
	@POST
	@Path("createLicenseCsv")
	public String createLicenseCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("outputPropList") List<String> outputPropList, LicenseSC searchParam, @QueryParam("isAp") boolean isAp);

	/**
	 * 割当情報(ライセンスから検索)-CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@POST
	@Path("createAllocLicenseCsv")
	public String createAllocLicenseCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, LicenseSC searchParam);

	/**
	 * アップグレード情報-CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
	@POST
	@Path("createUpgradeCsv")
	public String createUpgradeCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, LicenseSC searchParam);

	/**
	 *  ライセンス作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj ライセンスデータ
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータのライセンス番号
	 */
	@POST
	@Path("createLicense")
	public String createLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, License obj, @QueryParam("isAp") boolean isAp) throws AppException;

	/**
	 *  ライセンス更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj ライセンスデータ
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("updateLicense")
	public void updateLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, License obj, @QueryParam("isAp") boolean isAp) throws AppException;

	/**
	 *  ライセンス更新：保存時の操作(履歴表示用)指定
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj ライセンスデータ
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @param operation 操作(履歴表示用)
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	public void updateLicense(String loginStaffCode, String accessLevel, License obj, boolean isAp, String operation) throws AppException;

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
	@PUT
	@Path("deleteLicenseLogical")
	public void deleteLicenseLogical(@QueryParam("loginStaffCode") String loginStaffCode, String accessLevel, Date deleteDate, String deleteReason,  List<License> licenseList) throws AppException;

	/**
	 * ライセンス削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj ライセンスデータ
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@POST
	@Path("deleteLicense")
	public void deleteLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, License obj, @QueryParam("isAp") boolean isAp) throws AppException;

	/**
	 * ライセンス抹消取消
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj ライセンスデータ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("restoreLicense")
	public void restoreLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, License obj) throws AppException;

	/**
	 * ライセンス登録申請承認依頼
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 登録申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成・更新したデータの取得申請書番号
	 */
	@PUT
	@Path("applyApLicense")
	public String applyApLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, License obj) throws AppException;

	/**
	 * ライセンス登録申請承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveApLicense")
	public void approveApLicense(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * ライセンス登録申請引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApLicense")
	public void cancelApplyApLicense(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * ライセンス登録申請差戻し/却下
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("rejectApLicense")
	public void rejectApLicense(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;

	/**
	 * 登録申請照会(e申請)
	 * @param eappId e申請書類ID
	 * @param execStaffCode 処理実行者
	 */
	@POST
	@Path("searchApLicenseEapp")
	public List<LicenseSR> searchApLicenseEapp(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 取得申請(有形)明細から登録申請データ取得
	 * @param apGetTanLineLicId 取得申請明細識別ID
	 * @return 登録申請データ
	 */
	@POST
	@Path("getApLicenseByApGetTan")
	public License getApLicenseByApGetTan(@QueryParam("apGetTanLineLicId") Long apGetTanLineLicId);

	/**
	 * 取得申請(有形)明細の登録残明細から登録申請登録CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param isTermUpdate タームライセンスの更新かどうか
	 * @param apGetTanList 取得申請データ一覧
	 * @return CSVファイル識別ID
	 */
	@POST
	@Path("createApLicenseCsvByApGetTan")
	public String createApLicenseCsvByApGetTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("isTermUpdate") boolean isTermUpdate, List<ApGetTan> apGetTanList);

	/**
	 * ファイル・直入力可能マスタ値CSV作成
	 * @param companyCode 会社コード
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param propertyList 項目名一覧
	 * @return CSVファイル識別ID
	 */
	@POST
	@Path("createLicensePossibleInputMasterCsv")
	public String createLicensePossibleInputMasterCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, List<String> propertyList);

	/**
	 * ファイル・直入力可能マスタ値CSV作成(保有会社変更用)
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @return CSVファイル識別ID
	 */
	@PUT
	@Path("createLicenseCompanyMoveInputMasterCsv")
	public String createLicenseCompanyMoveInputMasterCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode);

	/**
	 * CSVファイルから登録申請一括作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル識別ID
	 * @param obj 申請データ
	 * @return 作成した登録申請の登録申請番号リスト
	 * @throws AppException
	 */
	@POST
	@Path("createApLicenseBulk")
	public List<String> createApLicenseBulk(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel,  @QueryParam("fileId") String fileId, License obj) throws AppException;


	/**
	 * CSVファイルからライセンス一括更新
	 * @param companyCode 会社コード
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル識別ID
	 * @param updatePropertyList 更新対象項目一覧
	 * @throws AppException
	 */
	@PUT
	@Path("callUpdateLicenseBulk")
	public void callUpdateLicenseBulk(@QueryParam("companyCode") String companyCode, @QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId, List<CodeName> updatePropertyList) throws AppException;

	/**
	 * CSVファイルからライセンス一括更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param fileId CSVファイル識別ID
	 * @param execFile CSVファイル
	 * @param updatePropertyList 更新対象項目一覧
	 * @param logId 一括更新更新ログID
	 * @throws AppException
	 */
	public void updateLicenseBulk(String loginStaffCode, String accessLevel, String companyCode, String fileId, File execFile, List<CodeName> updatePropertyList, Long logId);

	/**
	 * ライセンス一括更新の一行処理
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param rowCt 処理対象のカレント行
	 * @param updateObj 更新対象
	 * @param updatePropSet 更新対象プロパティ情報
	 * @param apChangeTypeMap 移動申請判定用
	 * @param readMethods プロパティ設定用getterメソッド
	 * @param writeMethods プロパティ設定用setterメソッド
	 * @throws AppException
	 */
	public void updateLicenseBulkRow(String loginStaffCode, String accessLevel, int rowCt, License updateObj, Set<String> updatePropSet, Map<String, String> apChangeTypeMap, Method[] readMethods, Method[] writeMethods) throws AppException;

	/**
	 * CSVファイルから登録申請一括申請
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル識別ID
	 * @param obj 申請データ
	 * @return 作成した登録申請の登録申請番号リスト
	 * @throws AppException
	 */
	@POST
	@Path("applyCreateApLicenseBulk")
	public List<String> applyCreateApLicenseBulk(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId, License obj) throws AppException;

	/**
	 * ソフトウェアメーカー名からソフトウェアメーカーID取得
	 * @param softwareMakerName ソフトウェアメーカー名
	 * @return ソフトウェアメーカーID
	 */
	@POST
	@Path("getSoftwareMakerIdByName")
	public Long getSoftwareMakerIdByName(@QueryParam("softwareMakerName") String softwareMakerName);

	/**
	 * ソフトウェア名からソフトウェアID取得
	 * @param softwareMakerId ソフトウェアメーカーID
	 * @param softwareName ソフトウェア名
	 * @return ソフトウェアID
	 */
	@POST
	@Path("getSoftwareIdByName")
	public Long getSoftwareIdByName(@QueryParam("softwareMakerId") Long softwareMakerId, @QueryParam("softwareName") String softwareName);

	/**
	 * ソフトウェアメーカー一覧検索
	 * @param softwareMakerName ソフトウェア名
	 * @param enableOnly 有効データ、無効データの検索 0:有効データ,1:無効データ(削除済)
	 * @return ソフトウェアID
	 */
	@POST
	@Path("searchSoftwareMaker")
	public List<SoftwareMaker> searchSoftwareMaker(@QueryParam("softwareMakerName") String softwareMakerName, @QueryParam("enableOnly") boolean enableOnly);

	/**
	 * ソフトウェアメーカー作成
	 * @param staffCode ログイン社員番号
	 * 		  softwareMaker ソフトウェアメーカーエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	@POST
	@Path("createSoftwareMaker")
	public Long createSoftwareMaker(@QueryParam("staffCode") String staffCode, SoftwareMaker softwareMaker) throws AppException;

	/**
	 * ソフトウェアメーカーアップデート
	 * @param staffCode	ソフトウェアメーカーID
	 * @param softwareMaker	ソフトウェアメーカーエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	@PUT
	@Path("updateSoftwareMaker")
	public void updateSoftwareMaker(@QueryParam("staffCode") String staffCode, SoftwareMaker softwareMaker) throws AppException;

	/**
	 * ソフトウェア一覧検索
	 * @param softwareMakerId ソフトウェアメーカーID
	 * @param softwareName ソフトウェア名
	 * @param enableOnly 有効データ、無効データの検索 0:有効データ,1:無効データ(削除済)
	 * @return ソフトウェアID
	 */
	@POST
	@Path("searchSoftware")
	public List<Software> searchSoftware(@QueryParam("staffCode") Long softwareId, @QueryParam("softwareName") String softwareName, @QueryParam("enableOnly") boolean enableOnly);

	/**
	 * ソフトウェア作成
	 * @param staffCode ログイン社員番号
	 * 		  softwareMaker ソフトウェアメーカーエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	@POST
	@Path("createSoftware")
	public Long createSoftware(@QueryParam("staffCode") String staffCode, Software software) throws AppException;

	/**
	 * ソフトウェアアップデート
	 * @param staffCode	ソフトウェアメーカーID
	 * @param softwareMaker	ソフトウェアメーカーエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	@PUT
	@Path("updateSoftware")
	public void updateSoftware(@QueryParam("staffCode") String staffCode, Software software) throws AppException;

	/**
	 * 一括申請
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apLicenseList 一括処理対象の登録申請一覧
	 * @throws AppException
	 */
	@PUT
	@Path("bulkApplyApLicense")
	public void bulkApplyApLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<License> apLicenseList) throws AppException;

	/**
	 * 一括削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apLicenseList 一括処理対象の登録申請一覧
	 * @throws AppException
	 */
	@POST
	@Path("bulkDeleteApLicense")
	public void bulkDeleteApLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<License> apLicenseList) throws AppException;

	/**
	 * 一括情報機器等登録
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param apLicenseList 一括処理対象の登録申請一覧
	 * @throws AppException
	 */
	@POST
	@Path("bulkCreateLicense")
	public void bulkCreateLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<License> apLicenseList) throws AppException;

	/**
	 * ライセンス割当情報検索
	 * @param assetLineOsId 情報機器等OS明細ID
	 * @throws
	 */
	@POST
	@Path("getLicenseAllocList")
	public List<LicenseAlloc> getLicenseAllocList(@QueryParam("assetLineOsId") Long assetLineOsId);

	/**
	 * ライセンス割当作成
	 * @param loginStaffInfo ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param assetId 情報機器管理番号
	 * @param assetLineOsId 情報機器OS明細ID
	 * @param licenseIdList ライセンスIDリスト
	 * @return なし
	 * @throws SQLException
	 */
	@POST
	@Path("createLicenseAlloc")
	public void createLicenseAlloc(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("assetId") String assetId, @QueryParam("assetLineOsId") long assetLineOsId, List<String> licenseIdList) throws AppException;

	/**
	 * ライセンス割当削除
	 * @param loginStaffInfo ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param licenseAlloc ライセンス割当エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	@POST
	@Path("deleteLicenseAlloc")
	public void deleteLicenseAlloc(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, LicenseAlloc licenseAlloc );

	/**
	 * ライセンス割当全削除
	 * @param loginStaffInfo ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param licenseId 割当削除対象ライセンス管理番号
	 * @return なし
	 * @throws SQLException
	 */
	public void deleteLicenseAllocAllByLic(String loginStaffCode, String accessLevel, String licenseId);

	/**
	 * ライセンス割当全削除
	 * @param loginStaffInfo ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param assetId 割当削除対象情報機器管理番号
	 * @param assetLineOsId 割当削除対象情報機器OS明細ID
	 * @return なし
	 * @throws SQLException
	 */
	@POST
	@Path("deleteLicenseAllocAllByAstLineOs")
	public void deleteLicenseAllocAllByAstLineOs(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("assetId") String assetId, @QueryParam("assetLineOsId") long assetLineOsId);

	/**
	 * licenseIdに指定されたライセンスが修正可能かどうかを判別
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param licenseId ライセンス管理番号
	 * @param isAp true:ライセンス登録申請,false:ライセンス等
	 * @return
	 */
	@POST
	@Path("isEditableLicense")
	public boolean isEditableLicense(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("licenseId") String licenseId, @QueryParam("isAp") boolean isAp);

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

}
