/*===================================================================
 * ファイル名 : FldSession.java
 * 概要説明   : 固定資産(照会・管理項目修正)セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-14 1.0  リヨン           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.SQLException;
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
import jp.co.ctcg.easset.dto.fld.PpfsFld;
import jp.co.ctcg.easset.dto.fld.PpfsFldApp;
import jp.co.ctcg.easset.dto.fld.PpfsFldSC;
import jp.co.ctcg.easset.dto.fld.PpfsFldSR;
import jp.co.ctcg.easset.dto.fld.PpfsFldSupport;
import jp.co.ctcg.easset.dto.fld.PpfsUnUpd;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("fld")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface FldSession {

	/**
	 * 固定資産照会一覧検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("searchFld")
	public List<PpfsFldSR> searchFld(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode , PpfsFldSC searchParam);

	/**
	 * 固定資産CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param searchParam    検索条件
	 * @param itemDef ダウンロード定義
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("createFldCsv")
	public String createFldCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("outputPropList") List<String> outputPropList, PpfsFldSC searchParam, @QueryParam("itemDef") String itemDef);

	/**
	 * 固定資産照会_取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param koyuno    固有番号
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("getFld")
	public PpfsFld getFld(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("koyuno") Long koyuno);

	/**
	 * 固定資産照会取得申請単位_取得申請単位
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param applicationId 取得申請番号
	 * @return 固定資産照会取得申請単位_取得
	 * @throws SQLException
	 */
	@POST
	@Path("getFldApp")
	public PpfsFldApp getFldApp(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("applicationId") String applicationId);

	/**
	 * 固定資産照会取得申請単位_取得申請単位_固有番号から取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param koyuno 固有番号
	 * @return 固定資産照会取得申請単位_取得
	 * @throws SQLException
	 */
	@POST
	@Path("getFldAppKoyu")
	public PpfsFldApp getFldAppKoyu(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("koyuno") Long koyuno);

	/**
	 * 無形固定資産付加情報更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 更新オブジェクト
	 * @throws AppException
	 */
	@PUT
	@Path("updateFldApp")
	public void updateFldApp(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, PpfsFldApp obj) throws AppException;

	/**
	 * 無形固定資産付加情報更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 更新オブジェクト
	 * @param operation 更新履歴操作名
	 * @throws AppException
	 */
	public void updateFldApp(String loginStaffCode, String accessLevel, PpfsFldApp obj, String operation) throws AppException;

	/**
	 * ファイル・直入力可能マスタ値CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param propertyList 項目名一覧
	 * @return CSVファイル識別ID
	 */
	@POST
	@Path("createFldPossibleInputMasterCSV")
	public String createFldPossibleInputMasterCSV(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, List<String> propertyList);

	/**
	 * 補助台帳一覧取得
	 * @param koyuno 固有番号
	 * @param syubetuCode 種別コード
	 * @return 補助台帳一覧
	 */
	public List<PpfsFldSupport> getFldSupportList(Long koyuno, String syubetuCode);

	/**
	 * Ppfs未承認データ一覧取得
	 * @param companyCode 会社コード
	 * @param koyunoList 固有番号一覧
	 * @return Ppfs未承認データ一覧
	 * @throws SQLException
	 */
	public List<PpfsUnUpd> searchPpfsUnUpd(String companyCode, String koyunoPluar);

	/**
	 * Ppfs未承認データ一覧取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param obj 固定資産検索結果一覧
	 * @return Ppfs未承認データ一覧
	 * @throws SQLException
	 */
	@PUT
	@Path("checkPpfsFldUpdate")
	public void checkPpfsFldUpdate(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, List<PpfsFldSR> obj) throws AppException;

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
	 * CSVファイルから固定資産取得申請一括更新
	 * @param companyCode 会社コード
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル識別ID
	 * @param updatePropertyList 更新対象項目一覧
	 * @throws AppException
	 */
	@PUT
	@Path("callUpdateFldBulk")
	public void callUpdateFldBulk(@QueryParam("companyCode") String companyCode, @QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId, List<CodeName> updatePropertyList) throws AppException;

	/**
	 * 固定資産(無形)一括更新の一行処理
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param rowCt 処理対象のカレント行
	 * @param updateObj 更新対象
	 * @param updatePropSet 更新対象プロパティ情報
	 * @param fldTypeMap 資産判定用
	 * @param readMethods プロパティ設定用getterメソッド
	 * @param writeMethods プロパティ設定用setterメソッド
	 * @throws AppException
	 */
	public void updateFldBulkRow(String loginStaffCode, String accessLevel, int rowCt, PpfsFldApp updateObj, Set<String> updatePropSet, Map<String, String> apChangeTypeMap, Method[] readMethods, Method[] writeMethods) throws AppException;
	/**
	 * CSVファイルから管理項目一括更新
	 * @param companyCode 会社コード
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル識別ID
	 * @param execFile CSVファイル
	 * @param updatePropertyList 更新対象項目一覧
	 * @param logId 一括更新更新ログID
	 * @throws AppException
	 */
	public void updateFldBulk(String companyCode, String loginStaffCode, String accessLevel, String fileId, File execFile, List<CodeName> updatePropertyList, Long logId);

	/**
	 * eAssetとProPlusの資産数チェック
	 * @param applicationId 取得申請書番号
	 * @return 結果（true:OK,false:NG）
	 * @throws AppException
	 */
	public boolean checkFldCount(String applicationId) throws AppException;

	/**
	 * applicationIdに指定された情報機器が修正可能かどうかを判別
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param koyuno 固有番号
	 * @return
	 */
	@POST
	@Path("isEditableFld")
	public boolean isEditableFld(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("applicationId") String applicationId);


}
