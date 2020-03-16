/*===================================================================
 * ファイル名 : MasterSession.java
 * 概要説明   : マスタセッションEJB定義
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
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import jp.co.ctcg.easset.dto.AssetCategory;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.CodeNameSet;
import jp.co.ctcg.easset.dto.CodeNameSetItem;
import jp.co.ctcg.easset.dto.LovDataEx;
import jp.co.ctcg.easset.dto.Project;
import jp.co.ctcg.easset.dto.RoleAdmin;
import jp.co.ctcg.easset.dto.RoleSection;
import jp.co.ctcg.easset.dto.Section;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.master.CompanyMasterData;
import jp.co.ctcg.easset.dto.master.DivisionMasterData;
import jp.co.ctcg.easset.dto.master.NewDivisionMasterData;
import jp.co.ctcg.easset.dto.master.SapAccountMasterData;
import jp.co.ctcg.easset.dto.master.SapAssignmentsMasterData;
import jp.co.ctcg.easset.dto.master.SapCustAccountsMasterData;
import jp.co.ctcg.easset.dto.master.SapExpDeptMasterData;
import jp.co.ctcg.easset.dto.master.SapVendorsMasterData;
import jp.co.ctcg.easset.dto.master.UserCompanyMasterData;
import jp.co.ctcg.easset.dto.master.UserMasterData;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("master")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface MasterSession {
	/**
	 * アカウントからユーザー情報取得
	 * @param account アカウント
	 * @return ユーザー情報
	 */
	@POST
	@Path("getUserByAccount")
	public User getUserByAccount(@Context HttpServletRequest httpRequest);

	/**
	 * 社員番号からログイン可能な会社一覧取得
	 * @param staffCode 社員番号
	 * @return ログイン可能な会社一覧
	 */
	@POST
	@Path("getUsableCompanyList")
	public List<CodeName> getUsableCompanyList(@QueryParam("staffCode") String staffCode);

	/**
	 * 社員番号、会社から全社権限一覧取得
	 * @param staffCode 社員番号
	 * @param companyCode 会社コード
	 * @return 全社権限一覧
	 */
	@POST
	@Path("getRoleAdminList")
	public List<CodeName> getRoleAdminList(@QueryParam("staffCode") String staffCode, @QueryParam("companyCode") String companyCode);

	/**
	 * 社員番号、会社から部署権限一覧取得
	 * @param staffCode 社員番号
	 * @param companyCode 会社コード
	 * @return 部署権限一覧
	 */
	@POST
	@Path("getRoleSectionList")
	public List<CodeName> getRoleSectionList(@QueryParam("staffCode") String staffCode, @QueryParam("companyCode") String companyCode);

	/**
	 * コードネーム一覧取得(有効データのみ)
	 * @param categoryCode カテゴリコード（必須）
	 * @param internalCode 識別コード（任意）
	 * @param companyCode 会社コード（任意）
	 * @param values 値: 1番目(index0)=value1、2番目(index1)=value2・・・（任意）
	 * @return コードネーム一覧
	 */
	@POST
	@Path("getCodeNameList")
	public List<CodeName> getCodeNameList(@QueryParam("categoryCode") String categoryCode, @QueryParam("internalCode") String internalCode, @QueryParam("companyCode") String companyCode, List<String> values);

	/**
	 * コードネーム一覧取得(有効データのみ)
	 * @param categoryCode カテゴリコード
	 * @param parentInternalCode 識別コード
	 * @param companyCode 会社コード（任意）
	 * @return コードネーム一覧
	 */
	@POST
	@Path("getCodeNameListByParent")
	public List<CodeName> getCodeNameListByParent(@QueryParam("categoryCode") String categoryCode, @QueryParam("parentInternalCode") String parentInternalCode, @QueryParam("companyCode") String companyCode);

	/**
	 * コードネーム一覧取得(有効、無効両方のデータ)
	 * @param categoryCode カテゴリコード（必須）
	 * @param internalCode 識別コード（任意）
	 * @param companyCode 会社コード（任意）
	 * @param values 値: 1番目(index0)=value1、2番目(index1)=value2・・・（任意）
	 * @return コードネーム一覧
	 */
	@POST
	@Path("searchCodeNameList")
	public List<CodeName> searchCodeNameList(@QueryParam("categoryCode") String categoryCode, @QueryParam("internalCode") String internalCode, @QueryParam("companyCode") String companyCode, List<String> values, @QueryParam("isEnableOnly") boolean isEnableOnly);

	/**
	 * コードネーム取得
	 * @param categoryCode カテゴリコード（必須）
	 * @param internalCode 識別コード（任意）
	 * @param companyCode 会社コード（任意）
	 * @param values 値: 1番目(index0)=value1、2番目(index1)=value2・・・（任意）
	 * @return コードネーム
	 */
	@POST
	@Path("getCodeName")
	public CodeName getCodeName(@QueryParam("categoryCode") String categoryCode, @QueryParam("internalCode") String internalCode, @QueryParam("companyCode") String companyCode, List<String> values);

	/**
	 * 権限から利用可能な機能へのアクセスレベルを取得
	 * @param companyCode 会社コード
	 * @param roleCodeList 権限コードリスト
	 * @return 機能アクセスレベル一覧
	 */
	@POST
	@Path("getAccessLevelList")
	public List<CodeName> getAccessLevelList(@QueryParam("companyCode") String companyCode, @QueryParam("roleCodeList") List<String> roleCodeList);

	/**
	 * 人事部課検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param sectionName 部課名検索キーワード
	 * @return 人事部課リスト
	 */
	@POST
	@Path("searchSection")
	public List<Section> searchSection(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("sectionName") String sectionName, @QueryParam("year") Integer year);

	/**
	 * 人事部課取得(カレント年度)
	 * @param companyCode 会社コード 
	 * @param sectionCode 部課コード
	 * @return 人事部課データ
	 */
	@POST
	@Path("getSection")
	public Section getSection(@QueryParam("companyCode") String companyCode, @QueryParam("sectionCode") String sectionCode);

	/**
	 * 人事部課取得
	 * @param companyCode 会社コード
	 * @param sectionCode 部課コード
	 * @param year 年度
	 * @return 人事部課データ
	 */
	public Section getSection(String companyCode, String sectionCode, Integer year);

	/**
	 * 資産(機器)分類検索
	 * @param categoryName 分類名検索キーワード
	 * @param selectedAssetCategory1 変更前資産大分類
	 * @return 資産(機器)分類リスト
	 */
	@POST
	@Path("searchAssetCategory")
	public List<AssetCategory> searchAssetCategory(@QueryParam("categoryName") String categoryName, @QueryParam("selectedAssetCategory1") String selectedAssetCategory1);

	/**
	 * 社員検索
	 * @param companyCode 会社コード(nullの場合は全G社)
	 * @param staffName 氏名検索キーワード
	 * @param sectionName 部課名検索キーワード
	 * @param enableStaffOnly 有効社員のみ検索指定
	 * @return 社員リスト
	 */
	@POST
	@Path("searchStaff")
	public List<User> searchStaff(@QueryParam("companyCode") String companyCode, @QueryParam("staffName") String staffName, @QueryParam("sectionName") String sectionName, @QueryParam("enableStaffOnly") Boolean enableStaffOnly);

	/**
	 * 社員検索(1件の場合)：無効社員情報含む
	 * @param companyCode 会社コード(nullの場合は全G社)
	 * @param staffCode 社員コード
	 * @return 社員リスト
	 */
	@POST
	@Path("getStaff")
	public User getStaff(@QueryParam("companyCode") String companyCode, @QueryParam("staffCode") String staffCode);

	/**
	 * 社員検索(1件の場合)：有効社員のみ
	 * @param companyCode 会社コード(nullの場合は全G社)
	 * @param staffCode 社員コード
	 * @return 社員リスト
	 */
	public User getStaffValid(String companyCode, String staffCode);

	/**
	 * 課長検索
	 * @param companyCode 会社コード
	 * @param costSectionCode 保有部署コード
	 * @return 社員リスト
	 */
	@POST
	@Path("searchCostApproveStaff")
	public List<User> searchCostApproveStaff(@QueryParam("companyCode") String companyCode, @QueryParam("costSectionCode") String costSectionCode);

	/**
	 * 課長検索(1件以上)
	 * @param companyCode 会社コード
	 * @param costSectionCode 保有部署コード
	 * @return 社員リスト
	 */
	@POST
	@Path("searchCostApproveStaff")
	public User getCostApproveStaff(@QueryParam("companyCode") String companyCode, @QueryParam("companyCode") String staffCode);

	/**
	 * 部長検索
	 * @param companyCode 会社コード
	 * @param holSectionCode 保有部署コード
	 * @return 社員リスト
	 */
	@POST
	@Path("searchHolApproveStaff")
	public List<User> searchHolApproveStaff(@QueryParam("companyCode") String companyCode, @QueryParam("holSectionCode") String holSectionCode);

	/**
	 * 部長検索(1件以上)
	 * @param companyCode 会社コード
	 * @param costSectionCode 保有部署コード
	 * @return 社員リスト
	 */
	@POST
	@Path("getHolApproveStaff")
	public User getHolApproveStaff(@QueryParam("companyCode") String companyCode, @QueryParam("staffCode") String staffCode);

	/**
	 * 経費負担部署検索
	 * @param companyCode 会社コード
	 * @param sectionName 経費負担部課名
	 * @param searchDateFrom 検索日付From
	 * @param searchDateTo 検索日付To
	 * @return 経費負担部署一覧
	 */
	@POST
	@Path("searchCostSection")
	public List<Section> searchCostSection(@QueryParam("companyCode") String companyCode, @QueryParam("sectionName") String sectionName, @QueryParam("searchDateFrom") Date searchDateFrom, @QueryParam("searchDateTo") Date searchDateTo);

	/**
	 * 経費負担部署検索
	 * @param companyCode 会社コード
	 * @param sectionCode 経費負担部課コード
	 * @param searchDateFrom 検索日付From
	 * @param searchDateTo 検索日付To
	 * @return 経費負担部署
	 */
	@POST
	@Path("getCostSection")
	public Section getCostSection(@QueryParam("companyCode") String companyCode, @QueryParam("sectionName") String sectionName, @QueryParam("searchDateFrom") Date searchDateFrom, @QueryParam("searchDateTo") Date searchDateTo);

	/**
	 * MIの経費負担部課コードチェック
	 * @param companyCode 会社コード
	 * @param sectionCode 経費負担部課コード
	 * @return true:NextMI, false:次期シス
	 */
	public boolean isMICostSection(String companyCode, String sectionCode);

	/**
	 * IDのシーケンス採番
	 * @param idPrefix IDの接頭語
	 * @return IDの新シーケンス
	 */
	public String nextValId(String idPrefix);

	/**
	 * Oracleシーケンスのnextval取得
	 * @param seqname シーケンス名
	 * @return Oracleシーケンスのnextval
	 */
	public Long nextVal(String seqName);

	/**
	 * ファイル保存確定処理
	 * @param fileIdTmp 一時ファイル名
	 * @param entityName ファイル保持エンティティ名(取得申請:AP_GET_TAN、ライセンス:LICNESE)
	 * @param id 対象エンティティのid
	 * @return 確定ファイル
	 * @throws IOException
	 */
	public File fileCommit(String fileIdTmp, String entityName, String id);

	/**
	 * LOVデータ取得(キー検索)
	 * @param sqlName SQL名
	 * @param constParamMap 固定パラメータ
	 * @param code キー
	 * @return
	 */
	@POST
	@Path("getLovData")
	public LovDataEx getLovData(@QueryParam("sqlName") String sqlName, Map<String,Object> constParamMap, @QueryParam("code") String code);

	/**
	 * CodeNameの名称(値1)からコード取得
	 * @param categoryCode カテゴリコード
	 * @param companyCode 会社コード
	 * @param value1 名称
	 * @return コード
	 */
	public String getCodeNameIdByName(String categoryCode, String companyCode,  String value1);

	/**
	 * CodeNameの名称(値1)からコード取得
	 * @param categoryCode カテゴリコード
	 * @param companyCode 会社コード
	 * @param value1 名称
	 * @param isCompanyFlag
	 * @return コード
	 */
	public String getCodeNameIdByName(String categoryCode, String companyCode,  String value1, boolean isCompanyFlag);

	/**
	 * CodeNameの名称(値1)から親コード取得
	 * @param categoryCode カテゴリコード
	 * @param companyCode 会社コード
	 * @param value1 名称
	 * @return 親コード
	 */
	public String getCodeNameParentIdByName(String categoryCode, String companyCode,  String value1);

	/**
	 * 旧eAsset連携用セッション生成
	 * @param loginAccount ログインアカウント
	 * @param companyCode 会社コード
	 * @param roleCode 権限コード(新eAsset)
	 * @param urlPath 旧eAssetアクセスパス
	 * @return
	 */
	@PUT
	@Path("createOldEaSession")
	public String createOldEaSession(@QueryParam("loginAccount") String loginAccount, @QueryParam("companyCode") String companyCode, @QueryParam("roleCode") String roleCode, @QueryParam("urlPath") String urlPath);

	/**
	 * ダウンロード項目取得
	 * @param itemDefName 項目制御
	 * @param lineItem 明細テーブル
	 * @return ダウンロード項目一覧
	 */
	@PUT
	@Path("getDownloadItemList")
	public List<CodeName> getDownloadItemList(@QueryParam("itemDefName") String itemDefName, @QueryParam("lineItem") String lineItem);

	/**
	 * ユーザー権限_全社権限検索
	 * @param companyCode 会社コード
	 * 		  roleCode 管理者権限コード
	 * 		  staffCode 社員番号
	 * @param lineItem 明細テーブル
	 * @return ユーザー権限_全社権限一覧
	 */
	@POST
	@Path("searchRoleAdmin")
	public  List<RoleAdmin> searchRoleAdmin(@QueryParam("companyCode") String companyCode, @QueryParam("roleCode") String roleCode, @QueryParam("staffCode") String staffCode);

	/**
	 * ユーザー権限_部署(資産管理担当者)権限検索
	 * @param companyCode 会社コード
	 * 		  roleCode 管理者権限コード
	 * 		  staffCode 社員番号
	 * @param lineItem 明細テーブル
	 * @throws SQLException
	 */
	@POST
	@Path("searchRoleSection")
	public  List<RoleSection> searchRoleSection(@QueryParam("companyCode") String companyCode, @QueryParam("sectionCode") String sectionCode, @QueryParam("staffCode") String staffCode);

	/**
	 * ユーザー権限_全社権限作成
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * 		  roleAdmin 全社権限エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	@POST
	@Path("createRoleAdmin")
	public void createRoleAdmin(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("companyCode") String companyCode, RoleAdmin roleAdmin) throws AppException;

	/**
	 * ユーザー権限_部署(資産管理担当者)権限作成
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * 		  roleSection 部署権限エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	@POST
	@Path("createRoleSection")
	public void createRoleSection(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("companyCode") String companyCode, RoleSection roleSection) throws AppException;

	/**
	 * ユーザー権限_全社権限削除
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * 		  roleCode 権限コード
	 * 		  staffCode 社員番号
	 * @return なし
	 * @throws SQLException
	 */
	@DELETE
	@Path("deleteRoleAdmin")
	public void deleteRoleAdmin(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("companyCode") String companyCode, @QueryParam("roleCode") String roleCode, @QueryParam("staffCode") String staffCode);

	/**
	 * ユーザー権限_部署(資産管理担当者)権限削除
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * 		  sectionCode 部署コード
	 * 		  staffCode 社員番号
	 * @return なし
	 * @throws SQLException
	 */
	@DELETE
	@Path("deleteRoleSection")
	public void deleteRoleSection(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("companyCode") String companyCode, @QueryParam("sectionCode") String sectionCode, @QueryParam("staffCode") String staffCode);

	/**
	 * ユーザー権限_部署(資産管理担当者)権限CSVファイル作成
	 * @param loginStaffCode 社員番号
	 * @param companyCode 会社コード
	 * 		  sectionCode 部署コード
	 * 		  staffCode 社員番号
	 * @return なし
	 * @throws SQLException
	 */
	@POST
	@Path("createRoleSectionCsv")
	public String createRoleSectionCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("companyCode") String companyCode, @QueryParam("sectionCode") String sectionCode, @QueryParam("staffCode") String staffCode);

	/**
	 * 資産管理担当者プロファイル検索
	 * @param loginStaffCode ログイン社員番号
	 * 		  accessLevel アクセスレベル
	 * 		  companyCode 会社コード
	 * 		  roleCode 管理者権限コード
	 * 		  staffCode 社員番号
	 * @param lineItem 明細テーブル
	 * @return 資産管理担当者プロファイル一覧
	 * @throws SQLException
	 */
	@POST
	@Path("searchSectionRoleProfile")
	public  List<RoleSection> searchSectionRoleProfile(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("sectionCode") String sectionCode, @QueryParam("staffCode") String staffCode);

	/**
	 * 資産管理担当者プロファイル更新
	 * @param companyCode 会社コード
	 * 		  accessLevel アクセスレベル
	 * 		  roleSection 資産管理担当エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	@PUT
	@Path("updateSectionRoleProfile")
	public void updateSectionRoleProfile(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, RoleSection roleSection) throws AppException;

	/**
	 * 項目定義バリデーション
	 * itemDefNameでDB登録された項目定義に従って
	 * 文字数、最小・最大値、正規表現、必須チェックを行う。
	 * @param itemDefName 項目定義名
	 * @param entityName エンティティ名
	 * @param obj バリデーション対象obj
	 * @param status ステータス（申請：申請ステータス、機器・ライセンス：通常 OR 抹消）
	 * @param updatePropSet 一括更新項目一覧
	 * @return
	 */
	public String validateItemDef(String itemDefName, String entityName, Object obj, int status, Set<String> updatePropSet);

	/**
	 * staffCodeユーザの指定機能・会社・部署でのアクセスレベル取得
	 * @param menuId メニューID
	 * @param staffCode 社員番号
	 * @param companyCode 会社コード
	 * @param sectionCode 部署コード
	 * @param sectionYear 部初年度
	 * @return アクセスレベル
	 */
	public String getAccessLevel(String menuId, String staffCode, String companyCode, String sectionCode, int sectionYear);

	/**
	 * プロジェクト情報取得
	 * @param projectCode プロジェクトコード
	 * @return プロジェクト情報
	 */
	public Project getProject(String projectCode);

	/**
	 * コードネーム更新
	 * @param loginStaffCode ログインユーザ
	 * @param codeName コードネームオブジェクト
	 * @return なし
	 */
	public void updateCodeName(String loginStaffCode, CodeName codeName);

	/**
	 * 汎用マスタ設定検索
	 * @param loginStaffCode ログインユーザ
	 * @param companyCode 会社コード
	 * @param categoryName カテゴリ名
	 * @return 汎用マスタ設定検索一覧
	 */
	@POST
	@Path("searchCodeNameSet")
	public List<CodeNameSet> searchCodeNameSet(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("companyCode") String companyCode, @QueryParam("categoryName") String categoryName, @QueryParam("roleCodeList") List<String> roleCodeList);

	/**
	 * 汎用マスタ設定検索
	 * @param categoryCode カテゴリーコード
	 * @return 汎用マスタ設定検索一覧
	 */
	@POST
	@Path("getCodeNameSetItemList")
	public List<CodeNameSetItem> getCodeNameSetItemList(@QueryParam("categoryCode") String categoryCode);

	/**
	 * 汎用マスタ更新
	 * @param loginStaffCode 社員番号
	 * @param categoryCode カテゴリーコード
	 * @param companyCode 会社コード
	 * @param codeNameList 汎用データ一覧
	 * @return なし
	 */
	@PUT
	@Path("updateCodeNameList")
	public void updateCodeNameList(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("categoryCode") String categoryCode, @QueryParam("companyCode") String companyCode, List<CodeName> codeNameList)  throws AppException;

	/**
	 * 汎用マスタCSV作成
	 * @param loginStaffCode 社員番号
	 * @param categoryCode カテゴリーコード
	 * @param companyCode 会社コード
	 * @return なし
	 */
	@POST
	@Path("createCodeNameCsv")
	public String createCodeNameCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("categoryCode") String categoryCode, @QueryParam("companyCode") String companyCode, @QueryParam("setCompanyCode") String setCompanyCode);

	/**
	 * 汎用マスタ使用状況
	 * @param obj 汎用マスタ
	 * @return エラーメッセージ
	 */
	@POST
	@Path("getCodeNameUseStatus")
	public String getCodeNameUseStatus(CodeName obj);

	/**
	 * ProPlus処理年月取得(取込済)
	 * @param companyCode 会社コード
	 * @return ProPlus処理年月
	 */
	@POST
	@Path("getPpfsCurrentPeriod")
	public String getPpfsCurrentPeriod(@QueryParam("companyCode") String companyCode);

	/**
	 * 資産管理担当者・部署長情報
	 * @param companyCode 会社コード
	 * @param sectionCode 部署コード
	 * @param sectionYear 部署年度
	 * @return 資産管理担当者・部署長情報
	 */
	public List<RoleSection> getUserRoleSectionAllList(String companyCode, String sectionCode, Integer sectionYear);

	/**
	 * 親部署コード取得
	 * @param companyCode 会社コード
	 * @param sectionCode 部署コード
	 * @param sectionYear 部署年度
	 * @return 親部署コード取得
	 */
	public String searchParentSectionCode(String companyCode, String sectionCode, Integer sectionYear);

	/**
	 * 資産(機器)一覧取得
	 * @param categoryCode カテゴリーコード
	 * @param parentCategoryCode 親カテゴリコード
	 * @param parentInternalCode 親識別コード
	 * @param companyCode 会社コード
	 * @param values 値一覧
	 * @return ProPlus処理年月
	 */
	@PUT
	@Path("sarchAstName")
	public List<LovDataEx> sarchAstName(@QueryParam("parentCategoryCode") String parentCategoryCode, @QueryParam("parentInternalCode") String parentInternalCode, @QueryParam("companyCode") String companyCode, @QueryParam("sysdate") Date sysdate, @QueryParam("makerName") String makerName, @QueryParam("astName") String astName);

	/**
	 * メニュー制御表示/非表示更新
	 * @param loginStaffCode ログイン社員番号
	 * @param menuCode 親識別コード
	 * @param publicCode 親カテゴリコード
	 * @return なし
	 */
	public void updateAccessControl(String loginStaffCode, String companyCode, String menuCode, String publicCode);

	/**
	 * 会社マスタデータ登録
	 * @param companyList 会社マスタリスト
	 * @return
	 */
	public void insertCompanyMaster(List<CompanyMasterData> companyList);

	/**
	 * 部署マスタデータ登録
	 * @param divisionList 部署マスタリスト
	 * @return
	 */
	public void insertDivisionMaster(List<DivisionMasterData> divisionList);

	/**
	 * 組織マスタデータ登録
	 * @param newDivisionList 組織マスタリスト
	 * @return
	 */
	public void insertNewDivisionMaster(List<NewDivisionMasterData> newDivisionList);

	/**
	 * ユーザー所属マスタデータ登録
	 * @param userCompanyList ユーザー所属マスタリスト
	 * @return
	 */
	public String insertUserCompanyMaster(List<UserCompanyMasterData> userCompanyList);

	/**
	 * ユーザーマスタデータ登録
	 * @param userList ユーザーマスタリスト
	 * @return
	 */
	public String insertUserMaster(List<UserMasterData> userList);

	/**
	 * 従業員所属マスタデータ更新
	 * @param assignmentsList 従業員所属マスタリスト
	 * @return
	 */
	public void mergeSapAssignmentsMaster(List<SapAssignmentsMasterData> assignmentsList);

	/**
	 * 勘定科目マスタデータ更新
	 * @param accountList 勘定科目マスタリスト
	 * @return
	 */
	public void mergeSapAccountMaster(List<SapAccountMasterData> accountList);

	/**
	 * 顧客マスタデータ更新
	 * @param custAccountList 顧客マスタリスト
	 * @return
	 */
	public void mergeSapCustAccountsMaster(List<SapCustAccountsMasterData> custAccountList);

	/**
	 * 仕入先マスタデータ更新
	 * @param vendorsList 仕入先マスタリスト
	 * @return
	 */
	public void mergeSapVendorsMaster(List<SapVendorsMasterData> vendorsList);

	/**
	 * 経費負担部課マスタデータ更新
	 * @param expDeptList 経費負担部課マスタリスト
	 * @return
	 */
	public void mergeSapExpDeptMaster(List<SapExpDeptMasterData> expDeptList);

	/**
	 * LOV用一覧検索
	 * @param sqlName SQL名
	 * @param paramMap SQLパラメータマップ
	 * @return LOVデータ一覧
	 */
	@POST
	@Path("searchLovList")
	public List<LovDataEx> searchLovList(@QueryParam("sqlName") String sqlName, Map<String,Object> paramMap);

	/**
	 * リース/レンタル判定
	 * @param  latorihikikbnC  取引判定区分
	 * @param  bunruicdC       分類コード
	 * @return リース/レンタル判定結果
	* @throws AppException
	*/
	@PUT
	@Path("getLeaseRentalHantei")
	public String getLeaseRentalHantei(@QueryParam("latorihikikbnC") String latorihikikbnC, @QueryParam("bunruicdC") String bunruicdC);
}
