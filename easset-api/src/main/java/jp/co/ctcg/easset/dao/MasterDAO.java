/*===================================================================
 * ファイル名 : MasterDAO.java
 * 概要説明   : マスタ用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-06 1.0  リヨン        新規
 *=================================================================== */

package jp.co.ctcg.easset.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
//import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.ctcg.easset.dto.AssetCategory;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.CodeNameSet;
import jp.co.ctcg.easset.dto.CodeNameSetItem;
import jp.co.ctcg.easset.dto.CodeNameSetValid;
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
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

import com.ibatis.sqlmap.client.SqlMapClient;

public class MasterDAO {

	/**
	 * アカウントからユーザー情報取得
	 * @param account アカウント
	 * @return
	 * @throws SQLException
	 */
	public User selectUserByAccount(String account) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("account", account);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (User) sqlMap.queryForObject("selectUserByAccount_MST", sqlParam);
	}

	/**
	 * 社員番号からログイン可能な会社一覧取得
	 * @param staffCode 社員番号
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeName> selectUsableCompanyList(String staffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("staffCode", staffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		
		
		return (List<CodeName>)sqlMap.queryForList("selectUsableCompanyList_MST", sqlParam);
	}

	/**
	 * 社員番号、会社から全社権限一覧取得
	 * @param staffCode 社員番号
	 * @param companyCode 会社コード
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeName> selectRoleAdminList(String staffCode, String companyCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("staffCode", staffCode);
		sqlParam.put("companyCode", companyCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<CodeName>)sqlMap.queryForList("selectRoleAdminList_MST", sqlParam);
	}

	/**
	 * 社員番号、会社から部署権限一覧取得
	 * @param staffCode 社員番号
	 * @param companyCode 会社コード
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeName> selectRoleSectionList(String staffCode, String companyCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("staffCode", staffCode);
		sqlParam.put("companyCode", companyCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<CodeName>)sqlMap.queryForList("selectRoleSectionList_MST", sqlParam);
	}

	/**
	 * コードネーム一覧取得
	 * @param categoryCode カテゴリコード（必須）
	 * @param internalCode 識別コード（任意）
	 * @param companyCode 会社コード（任意）
	 * @param parentInternalCode 親コード（任意）
	 * @param values 値: 1番目(index0)=value1、2番目(index1)=value2・・・（任意）
	 * @param isEnableOnly 無効なデータも抜き出すかどうか true:有効データのみ、false:無効データも含める
	 * @return
	 * @throws SQLException
	 */
	public List<CodeName> selectCodeNameList(String categoryCode, String internalCode, String companyCode, String parentInternalCode, List<String> values, boolean isEnableOnly) throws SQLException {
		return selectCodeNameList(categoryCode, internalCode, companyCode, parentInternalCode, values, isEnableOnly, false);
	}


	/**
	 * コードネーム一覧取得
	 * @param categoryCode カテゴリコード（必須）
	 * @param internalCode 識別コード（任意）
	 * @param companyCode 会社コード（任意）
	 * @param parentInternalCode 親コード（任意）
	 * @param values 値: 1番目(index0)=value1、2番目(index1)=value2・・・（任意）
	 * @param isEnableOnly 無効なデータも抜き出すかどうか true:有効データのみ、false:無効データも含める
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeName> selectCodeNameList(String categoryCode, String internalCode, String companyCode, String parentInternalCode, List<String> values, boolean isEnableOnly, boolean isCompanyFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("categoryCode", categoryCode);
		sqlParam.put("internalCode", internalCode);
		sqlParam.put("parentInternalCode", parentInternalCode);

		if(companyCode == null){
			if(!isCompanyFlag){
				companyCode = "000";
			}
			else{
				companyCode = "";
			}
		}
		sqlParam.put("companyCode", companyCode);

		if(values != null) {
			for(int i = 0; i < values.size(); i++) {
				if(values.get(i) != null) {
					sqlParam.put("value" + String.valueOf(i + 1), values.get(i));
				}
			}
		}

		if(isEnableOnly) {
			sqlParam.put("deleteFlag", "0");
		}

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<CodeName>)sqlMap.queryForList("selectCodeNameList_MST", sqlParam);
	}

	/**
	 * 権限から利用可能な機能へのアクセスレベルを取得
	 * @param companyCode 会社コード
	 * @param roleCodeList 権限コードリスト
	 * @return 機能アクセスレベル一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeName> selectAccessLevelList(String companyCode, List<String> roleCodeList) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("roleCodeList", roleCodeList);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<CodeName>)sqlMap.queryForList("selectAccessLevelList_MST", sqlParam);
	}

	/**
	 * 人事部課検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param sectionName 部課名検索キーワード
	 * @param sectionCode 部課コード
	 * @param year 年度
	 * @return 人事部課リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Section> selectSectionList(String loginStaffCode, String accessLevel, String companyCode, String sectionName, String sectionCode, Integer year) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionName", sectionName);
		sqlParam.put("sectionCode", sectionCode);
		sqlParam.put("year", year);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<Section>) sqlMap.queryForList("selectSectionList_MST", sqlParam);
		//(List<Section>)sqlMap.queryForList("selectSectionList_MST", sqlParam);
	}

	/**
	 * 資産(機器)分類検索
	 * @param categoryName 分類名検索キーワード
	 * @param selectedAssetCategory1 変更前資産大分類
	 * @return 資産(機器)分類リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<AssetCategory> searchAssetCategory(String categoryName, String selectedAssetCategory1) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("categoryName", categoryName);
		sqlParam.put("selectedAssetCategory1", selectedAssetCategory1);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<AssetCategory>)sqlMap.queryForList("selectAssetCategoryList_MST", sqlParam);
	}

	/**
	 * 社員検索
	 * @param companyCode 会社コード(nullの場合は全G社)
	 * @param staffName 氏名検索キーワード
	 * @param sectionName 部課名検索キーワード
	 * @param enableStaffOnly 有効社員のみ検索指定
	 * @return 社員リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<User> selectStaffList(String companyCode, String staffName, String sectionName, Boolean enableStaffOnly) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("staffName", staffName);
		sqlParam.put("sectionName", sectionName);
		sqlParam.put("currentYear", getCurrentYear());
		if(enableStaffOnly){
			sqlParam.put("enableStaffOnly", "1");
		}
		else{
			sqlParam.put("enableStaffOnly", null);
		}

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<User>)sqlMap.queryForList("selectStaffList_MST", sqlParam);
	}

	/**
	 * 社員検索(1件のみ取得)
	 * @param companyCode 会社コード(nullの場合は全G社)
	 * @param staffCode 社員コード
	 * @param enableStaffOnly 有効社員のみ検索指定
	 * @return 社員リスト
	 * @throws SQLException
	 */
	public User selectStaff(String companyCode, String staffCode, Boolean enableStaffOnly) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("staffCode", staffCode);
		sqlParam.put("currentYear", getCurrentYear());
		if(enableStaffOnly){
			sqlParam.put("enableStaffOnly", "1");
		}
		else{
			sqlParam.put("enableStaffOnly", null);
		}

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (User)sqlMap.queryForObject("selectStaffList_MST", sqlParam);
	}

	/**
	 * 課長検索
	 * @param companyCode 会社コード
	 * @param costSectionCode 保有部署コード
	 * @return 課長リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<User> selectCostApproveStaffList(String companyCode, String costSectionCode ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("costSectionCode", costSectionCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<User>)sqlMap.queryForList("selectCostApproveStaffList_MST", sqlParam);

	}

	/**
	 * 課長検索(1件のみ)
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @return 課長
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public User selectCostApproveStaff(String companyCode, String staffCode ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("staffCode", staffCode);
		sqlParam.put("currentYear", getCurrentYear());
		sqlParam.put("titleCode", "TITLE_CODE_GL");
		sqlParam.put("enableStaffOnly", "1");
		List<User> userList = (List<User>)sqlMap.queryForList("selectStaffList_MST", sqlParam);
		User user = null;

		if(userList != null && userList.size() > 0){
			user = userList.get(0);
		}

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return user;

	}

	/**
	 * 部長検索
	 * @param companyCode 会社コード
	 * @param holSectionCode 保有部署コード
	 * @return 部長リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<User> selectHolApproveStaffList(String companyCode, String holSectionCode ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("holSectionCode", holSectionCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<User>)sqlMap.queryForList("selectHolApproveStaffList_MST", param);

	}

	/**
	 * 部長検索(1件のみ)
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @return 部長
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public User selectHoleApproveStaff(String companyCode, String staffCode ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("staffCode", staffCode);
		sqlParam.put("currentYear", getCurrentYear());
		sqlParam.put("titleCode", "TITLE_CODE_SECTION_MANAGER");
		sqlParam.put("enableStaffOnly", "1");
		List<User> userList = (List<User>)sqlMap.queryForList("selectStaffList_MST", sqlParam);
		User user = null;

		if(userList != null && userList.size() > 0){
			user = userList.get(0);
		}

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return user;

	}

	/**
	 * アクセス可能部署検索
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @return アクセス可能部署一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Section> selectAccessibleSectionList(String companyCode, String staffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("staffCode", staffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<Section>)sqlMap.queryForList("selectAccessibleSectionList_MST", sqlParam);
	}

	/**
	 * 階層部署検索
	 * @param companyCode 会社コード
	 * @param sectionCode 部署コード
	 * @param sectionYear 部初年度
	 * @return 階層部署一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectHierarchySectionCodeList(String companyCode, String sectionCode, Integer sectionYear) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionCode", sectionCode);
		sqlParam.put("sectionYear", sectionYear);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<String>)sqlMap.queryForList("selectHierarchySectionCodeList_MST", sqlParam);
	}

	/**
	 * 上位階層部署検索(所属部署基準)
	 * @param companyCode 会社コード
	 * @param sectionCode 部署コード
	 * @param sectionYear 部初年度
	 * @return 階層部署一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectUpperHierarchySectionCodeList(String companyCode, String sectionCode, Integer sectionYear) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionCode", sectionCode);
		sqlParam.put("sectionYear", sectionYear);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<String>)sqlMap.queryForList("selectUpperHierarchySectionCodeList_MST", sqlParam);
	}

	/**
	 * 親部署検索
	 * @param companyCode 会社コード
	 * @param sectionCode 部署コード
	 * @param sectionYear 部初年度
	 * @return 階層部署一覧
	 * @throws SQLException
	 */
	public String selectParentSectionCode(String companyCode, String sectionCode, Integer sectionYear) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionCode", sectionCode);
		sqlParam.put("sectionYear", sectionYear);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (String)sqlMap.queryForObject("selectParentSectionCodeList_MST", sqlParam);
	}

	/**
	 * アクセス可能部署の階層部署コード検索
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @param includeSectionHierarchyFlag 1:配下部署も全て取得 0:配下部署は取得しない
	 * @return
	 * @throws SQLException
	 */
	public List<String> selectAccessibleSectionCodeList(String companyCode, String staffCode, String includeSectionHierarchyFlag) throws SQLException {
		List<String> sectionCodeList = new ArrayList<String>();

		List<Section> list = this.selectAccessibleSectionList(companyCode, staffCode); // アクセス可能部署取得
		for(int i = 0; i < list.size(); i++) {
			Section sec = list.get(i);

			if(Function.nvl(includeSectionHierarchyFlag, "0").equals("1")) { // 階層指定有り
				// 配下部署取得
				List<String> hierarchyCodeList = this.selectHierarchySectionCodeList(sec.getCompanyCode(), sec.getSectionCode(), sec.getSectionYear());
				for(int j = 0; j < hierarchyCodeList.size(); j++) {
					sectionCodeList.add(hierarchyCodeList.get(j)); // 部署コード追加
				}
			} else { // 階層指定無し
				sectionCodeList.add(sec.getSectionCode()); // 部署コード追加
			}
		}

		return sectionCodeList;
	}

	/**
	 * 所属部署よりアクセス可能部署の階層部署コード検索
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectAccessibleUpperSectionCodeList(String companyCode, String staffCode) throws SQLException {

		List<String> sectionCodeList = new ArrayList<String>();

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("staffCode", staffCode);
		Integer currentYear = getCurrentYear();
		sqlParam.put("currentYear", currentYear);
		sqlParam.put("enableStaffOnly", "1");
		
		List<User> userList = (List<User>)sqlMap.queryForList("selectStaffList_MST", sqlParam);
		if(userList != null && userList.size() > 0){
			for(int i = 0; i < userList.size(); i++) {
				User user = userList.get(i);
				// 配下部署取得
				List<String> hierarchyCodeList = this.selectUpperHierarchySectionCodeList(user.getCompanyCode(), user.getSectionCode(), currentYear);
				for(int j = 0; j < hierarchyCodeList.size(); j++) {
					sectionCodeList.add(hierarchyCodeList.get(j)); // 部署コード追加
				}
			}
		}

		return sectionCodeList;
	}

	/**
	 * カレント年度取得
	 * @return カレント年度
	 * @throws SQLException
	 */
	public Integer getCurrentYear() throws SQLException {
		List<CodeName> list = this.selectCodeNameList("CURRENT_YEAR", "CURRENT_YEAR", null, null, null, true);
		return Integer.valueOf(list.get(0).getValue1());
	}

	/**
	 * 経費負担部署検索
	 * @param companyCode 会社コード
	 * @param sectionName 経費負担部課名
	 * @param searchDateFrom 検索日付From
	 * @param searchDateTo 検索日付To
	 * @return 経費負担部署一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Section> selectCostSectionList(String companyCode, String sectionName, Date searchDateFrom, Date searchDateTo) throws SQLException {
		List<Section> resultList;

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionName", sectionName);
		sqlParam.put("searchDateFrom", searchDateFrom);
		sqlParam.put("searchDateTo", searchDateTo);
		sqlParam.put("enableFlag", "Y");

		Integer currentYear = getCurrentYear();
		if(Function.nvl(searchDateFrom, "").equals("")) {
			searchDateFrom = Function.getDateFromStr(currentYear.toString() + "/04/01", "yyyy/MM/dd");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		Integer searchYear = Function.getYear(dateFormat.format(searchDateFrom));
		if(searchYear.intValue() < currentYear.intValue()) {
			sqlParam.put("year", searchYear);
			resultList = (List<Section>) sqlMap.queryForList("selectCostSectionYearList_MST", sqlParam);

			if(resultList == null || resultList.size() == 0) {
				resultList = (List<Section>) sqlMap.queryForList("selectMICostSectionList_MST", sqlParam);
			}
		}
		else {
			resultList = (List<Section>) sqlMap.queryForList("selectCostSectionList_MST", sqlParam);
		}
		return resultList;
	}

	/**
	 * 経費負担部署検索(1件のみ)
	 * @param companyCode 会社コード
	 * @param sectionCode 経費負担部課コード
	 * @param searchDateFrom 検索日付From
	 * @param searchDateTo 検索日付To
	 * @return 経費負担部署
	 * @throws SQLException
	 */
	public Section selectCostSection(String companyCode, String sectionCode, Date searchDateFrom, Date searchDateTo) throws SQLException {
		Section resultSection = null;

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionCode", sectionCode);
		sqlParam.put("searchDateFrom", searchDateFrom);
		sqlParam.put("searchDateTo", searchDateTo);
		sqlParam.put("enableFlag", "Y");

		Integer currentYear = getCurrentYear();
		if(Function.nvl(searchDateFrom, "").equals("")) {
			searchDateFrom = Function.getDateFromStr(currentYear.toString() + "/04/01", "yyyy/MM/dd");
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		Integer searchYear = Function.getYear(dateFormat.format(searchDateFrom));
		if(currentYear.intValue() != searchYear.intValue()) {
			sqlParam.put("year", searchYear);
			resultSection = (Section)sqlMap.queryForObject("selectCostSectionYearList_MST", sqlParam);

			if(resultSection == null) {
				resultSection = (Section)sqlMap.queryForObject("selectMICostSectionList_MST", sqlParam);
			}
		}
		else {
			resultSection = (Section)sqlMap.queryForObject("selectCostSectionList_MST", sqlParam);
		}
		return resultSection;
	}

	/**
	 * MIの経費負担部課コードチェック
	 * @param companyCode 会社コード
	 * @param sectionCode 経費負担部課コード
	 * @return true:NextMI, false:次期シス
	 * @throws SQLException
	 */
	public boolean isMICostSection(String companyCode, String sectionCode) throws SQLException {
		boolean ret = false;

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionCode", sectionCode);

		Section resultSection = (Section)sqlMap.queryForObject("selectMICostSectionList_MST", sqlParam);
		if(resultSection != null) {
			ret = true;
		}
		return ret;
	}

	/**
	 * IDシーケンス取得
	 * @return IDシーケンス
	 * @throws SQLException
	 */
	public Long selectIdSeq(String idPrefix, Integer year) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("idPrefix", idPrefix);
		sqlParam.put("year", year);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (Long) sqlMap.queryForObject("selectIdSeq_MST", sqlParam);
	}

	/**
	 * IDシーケンス作成
	 * @param idPrefix ID識別プレフィックス
	 * @param year 年度
	 * @return
	 * @throws SQLException
	 */
	public void insertIdSeq(String idPrefix, Integer year) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("idPrefix", idPrefix);
		sqlParam.put("year", year);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.insert("insertIdSeq_MST", sqlParam);
	}

	/**
	 * IDシーケンス更新
	 * @param idPrefix ID識別プレフィックス
	 * @param year 年度
	 * @return
	 * @throws SQLException
	 */
	public int updateIdSeq(String idPrefix, Integer year) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("idPrefix", idPrefix);
		sqlParam.put("year", year);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return sqlMap.update("updateIdSeq_MST", sqlParam);
	}

	/**
	 * Oracleシーケンスのnextval取得
	 * @return Oracleシーケンスのnextval
	 * @throws SQLException
	 */
	public Long selectNextVal(String seqName) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("seqName", seqName);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (Long) sqlMap.queryForObject("selectNextVal_MST", sqlParam);
	}

	/**
	 * CSVダウンロード用設定
	 * @param itemDef
	 * @param accessLevel アクセスレベル
	 * @param header セット項目：CSVヘッダ
	 * @param propList セット項目：ダウンロード項目リスト
	 * @param propFormatList セット項目：ダウンロード項目フォーマットリスト
	 * @throws SQLException
	 */
	public void setCsvDef(String itemDef, String accessLevel, StringBuffer header, List<String> propList, List<Format> propFormatList) throws SQLException {
		setCsvDef(itemDef, accessLevel, null, header, propList, propFormatList, false, null, null, null, null);
	}

	/**
	 * CSVダウンロード用設定
	 * @param itemDef
	 * @param accessLevel アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param header セット項目：CSVヘッダ
	 * @param propList セット項目：ダウンロード項目リスト
	 * @param propFormatList セット項目：ダウンロード項目フォーマットリスト
	 * @throws SQLException
	 */
	public void setCsvDef(String itemDef, String accessLevel, List<String> outputPropList, StringBuffer header, List<String> propList, List<Format> propFormatList) throws SQLException {
		setCsvDef(itemDef, accessLevel, outputPropList, header, propList, propFormatList, false, null, null, null, null);
	}

	/**
	 * CSVダウンロード用設定
	 * @param itemDef
	 * @param accessLevel アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param header セット項目：CSVヘッダ
	 * @param propList セット項目：ダウンロード項目リスト
	 * @param propFormatList セット項目：ダウンロード項目フォーマットリスト
	 * @param itemDef2 項目定義2
	 * @param itemDef2CategoryPrefix 項目定義2分の項目カテゴリ接頭語
	 * @param itemDef3 項目定義3
	 * @param itemDef3CategoryPrefix 項目定義3分の項目カテゴリ接頭語
	 * @throws SQLException
	 */
	public void setCsvDef(String itemDef, String accessLevel, List<String> outputPropList, StringBuffer header, List<String> propList, List<Format> propFormatList, String itemDef2, String itemDef2CategoryPrefix, String itemDef3, String itemDef3CategoryPrefix) throws SQLException {
		setCsvDef(itemDef, accessLevel, outputPropList, header, propList, propFormatList, false, itemDef2, itemDef2CategoryPrefix, itemDef3, itemDef3CategoryPrefix);
	}

	/**
	 * CSVダウンロード用設定
	 * @param itemDef
	 * @param accessLevel アクセスレベル
	 * @param header セット項目：CSVヘッダ
	 * @param propList セット項目：ダウンロード項目リスト
	 * @param propFormatList セット項目：ダウンロード項目フォーマットリスト
	 * @throws SQLException
	 */
	public void setCsvDefForUpload(String itemDef, String accessLevel, StringBuffer header, List<String> propList, List<Format> propFormatList) throws SQLException {
		setCsvDef(itemDef, accessLevel, null, header, propList, propFormatList, true, null, null, null, null);
	}

	/**
	 * CSVダウンロード用設定
	 * @param itemDef 項目定義
	 * @param accessLevel アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param header セット項目：CSVヘッダ
	 * @param propList セット項目：ダウンロード項目リスト
	 * @param propFormatList セット項目：ダウンロード項目フォーマットリスト
	 * @param itemDef2 項目定義2
	 * @param itemDef2CategoryPrefix 項目定義2分の項目カテゴリ接頭語
	 * @param itemDef3 項目定義3
	 * @param itemDef3CategoryPrefix 項目定義3分の項目カテゴリ接頭語
	 * @throws SQLException
	 */
	private void setCsvDef(String itemDef, String accessLevel, List<String> outputPropList, StringBuffer header, List<String> propList, List<Format> propFormatList, boolean isUpload, String itemDef2, String itemDef2CategoryPrefix, String itemDef3, String itemDef3CategoryPrefix) throws SQLException {
		List<CodeName> list = selectCodeNameList(itemDef, null, null, null, null, true);

		// 項目定義2が指定されている場合は追加
		if(itemDef2 != null) list.addAll(selectCodeNameList(itemDef2, null, null, null, null, true));
		if(itemDef3 != null) list.addAll(selectCodeNameList(itemDef3, null, null, null, null, true));

		Map<String, String> outputPropMap = null;
		// 出力項目の指定有り
		if(outputPropList != null) {
			outputPropMap = new HashMap<String, String>();
			for(Iterator<String> iter = outputPropList.iterator(); iter.hasNext();) {
				outputPropMap.put(iter.next(), "1");
			}
		}

		int propCt = 0;
		StringBuffer headerCategory = new StringBuffer();
		String lastHeaderCategory = "";
		for(int i = 0; i < list.size(); i++) {
			CodeName row = list.get(i);
			String downloadProp = Function.nvl(row.getValue7(), "0");

			if(downloadProp.equals("0") // ダウンロード項目外
				|| (isUpload && downloadProp.startsWith("1")) // アップロード時のアップロード項目以外
				|| (isUpload && !Function.isAccessLevelAdmin(accessLevel) && downloadProp.indexOf("*") >= 0) // アップロード時の一般権限アップロード不可項目
				|| (!isUpload && downloadProp.startsWith("3"))) { // ダウンロード時のダウンロード項目以外
				continue; // ダウンロード項目ではないのでスキップ
			}

			downloadProp.replaceAll("\\*", ""); // 一般権限アップロード付加指定除外

			if(outputPropMap != null && !outputPropMap.containsKey(row.getValue3())) continue; // 指定項目ではないのでスキップ
/*
			if(Function.isAccessLevelGeneral(accessLevel)) { // 一般権限
				if(downloadProp.startsWith("2")) {
					continue; // 全社権限のみの項目なのでスキップ
				}
			}
*/
			propCt += 1;
			if(propCt != 1) {
				header.append(",");
				headerCategory.append(",");
			}

			// ヘッダセット
			if(row.getValue5().indexOf(",") >= 0) {
				header.append("\"" + row.getValue5().replaceAll("\"", "\"\"") + "\"");
			} else {
				header.append(row.getValue5().replaceAll("\"", "\"\""));
			}

			// ヘッダカテゴリセット
			String category = Function.nvl(row.getDescription(), "");

			if(itemDef2 != null && !Function.nvl(itemDef2CategoryPrefix, "").equals("") && itemDef2.equals(row.getCategoryCode())) {
				// 2項目名の場合カテゴリPrefixをセット
				category = itemDef2CategoryPrefix + (category.equals("") ? "" : "-") + category;
			} else if(itemDef3 != null && !Function.nvl(itemDef3CategoryPrefix, "").equals("") && itemDef3.equals(row.getCategoryCode())) {
				// 3項目名の場合カテゴリPrefixをセット
				category = itemDef3CategoryPrefix + (category.equals("") ? "" : "-") + category;
			}

			if(!category.equals(lastHeaderCategory)) {
				if(category.indexOf(",") >= 0) {
					headerCategory.append("\"" + category.replaceAll("\"", "\"\"") + "\"");
				} else {
					headerCategory.append(category.replaceAll("\"", "\"\""));
				}
			}
			lastHeaderCategory = category;

			propList.add(row.getValue3());

			Format format = null;
			if(downloadProp.indexOf("FormatDate:") >= 0) {
				format = new SimpleDateFormat(downloadProp.replaceAll(".\\** FormatDate:", ""));
			}
			propFormatList.add(format);
		}

		header.insert(0, headerCategory.toString() + "\n");
	}

	/**
	 * 旧eAsset連携用セッションの生成
	 * @param account アカウント
	 * @param companyCode 会社コード
	 * @param oldRoleCode 権限コード(旧eAsset)
	 * @param urlPath アクセスURL
	 * @return セッションID
	 * @throws SQLException
	 */
	public String insertSession(String account, String companyCode, String oldRoleCode, String url) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("account", account);
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("oldRoleCode", oldRoleCode);
		sqlParam.put("url", url);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (String)sqlMap.insert("insertSession_MST", sqlParam);
	}

	/**
	 * ダウンロード項目取得
	 * @param itemDefName 項目制御 スペース区切りで２つまで指定可能
	 * @param lineItem 明細テーブル
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeName> selectDownloadItemList(String itemDefName, String lineItem) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();

		sqlParam.put("itemDefName", itemDefName);
		sqlParam.put("lineItem", lineItem);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<CodeName>) sqlMap.queryForList("selectDownloadItemList_MST", sqlParam);
	}

	/**
	 * ユーザー権限_全社権限検索
	 * @param companyCode 会社コード
	 * 		  roleCode 管理者権限コード
	 * 		  staffCode 社員番号
	 * @param lineItem 明細テーブル
	 * @return ユーザー権限_全社権限一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<RoleAdmin> selectRoleAdmin(String companyCode, String roleCode, String staffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("roleCode", roleCode);
		sqlParam.put("staffCode", staffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<RoleAdmin>) sqlMap.queryForList("selectRoleAdmin_MST", sqlParam);
	}

	/**
	 * ユーザー権限_部署(資産管理担当者)権限検索
	 * @param companyCode 会社コード
	 * 		  roleCode 管理者権限コード
	 * 		  staffCode 社員番号
	 * @param lineItem 明細テーブル
	 * @return ユーザー権限_全社権限一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<RoleSection> selectRoleSection(String companyCode, String sectionCode, String staffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionCode", sectionCode);
		sqlParam.put("staffCode", staffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		
		return (List<RoleSection>) sqlMap.queryForList("selectRoleSection_MST", sqlParam);
	}

	/**
	 * ユーザー権限_全社権限作成
	 * @param companyCode 会社コード
	 * 		  roleAdmin 全社権限エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertRoleAdmin(String companyCode, RoleAdmin roleAdmin) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("obj", roleAdmin);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.insert("insertRoleAdmin_MST", sqlParam);
	}

	/**
	 * ユーザー権限_部署(資産管理担当者)権限作成
	 * @param companyCode 会社コード
	 * 		  roleAdmin 全社権限エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertRoleSection(String companyCode, RoleSection roleSection) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("obj", roleSection);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.insert("insertRoleSection_MST", sqlParam);
	}

	/**
	 * ユーザー権限_全社権限削除
	 * @param companyCode 会社コード
	 * 		  roleAdmin 全社権限エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void deleteRoleAdmin(String companyCode, String roleCode, String staffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("roleCode", roleCode);
		sqlParam.put("staffCode", staffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.delete("deleteRoleAdmin_MST", sqlParam);
	}

	/**
	 * ユーザー権限_全社権限削除
	 * @param companyCode 会社コード
	 * 		  sectionCode 部署コード
	 * 		  staffCode 社員番号
	 * @return なし
	 * @throws SQLException
	 */
	public void deleteRoleSection(String companyCode, String sectionCode, String staffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionCode", sectionCode);
		sqlParam.put("staffCode", staffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.delete("deleteRoleSection_MST", sqlParam);
	}

	/**
	 * ユーザー権限_部署(資産管理担当者)権限CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createRoleSectionListCsv(String companyCode, String sectionCode, String staffCode) throws SQLException, IOException {

		// ヘッダ行
		String headerRow =
			"権限部署,権限者,権限者名,権限者e-mail"
			+ ",公開,公開コメント,設定日,設定者,設定者名"
		;

		// 出力プロパティ
		String[] outputProps = new String[] {
			"sectionName", "staffCode", "staffName", "mailAddress"
			, "publicFlagName", "publicComment", "updateDate", "updateStaffCode", "updateStaffName"
		};

		// プロパティフォーマット
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Format[] outputPropFormats = new Format[] {
				null, null, null, null
			  , null, null, dateFormat, null, null
		};

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("sectionCode", sectionCode);
		param.put("staffCode", staffCode);

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し

		CsvWriterRowHandler handler = null;
		try {
			handler = new CsvWriterRowHandler(headerRow, outputProps, (Format[]) outputPropFormats);
			sqlMap.queryWithRowHandler("selectRoleSection_MST", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

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
	@SuppressWarnings("unchecked")
	public List<RoleSection> selectSectionRoleProfileList(String loginStaffCode, String accessLevel, String companyCode, String sectionCode, String staffCode, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionCode", sectionCode);
		sqlParam.put("staffCode", staffCode);
		if(lockFlag) sqlParam.put("lockFlag", "1");

		if(Function.isAccessLevelSection(accessLevel)) { // 部署権限
			// アクセス可能階層部署コード一覧取得

			Integer currentYear = getCurrentYear(); // カレント年度取得

			List<String> sectionCodeList = selectAccessibleSectionCodeList(companyCode, loginStaffCode, "1");

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("SECTION_YEAR");

			sqlParam.put("accessLevelSection", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, null, sectionCodeList, currentYear));
		}

		//	SQLマップに定義されているSQLの呼び出し及び実行
		
		return (List<RoleSection>) sqlMap.queryForList("selectRoleSection_MST", sqlParam);
	}

	/**
	 * 資産管理担当者プロファイル更新
	 * @param companyCode 会社コード
	 * 		  roleSection 資産管理担当エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void updateSectionRoleProfile(String loginStaffCode, RoleSection roleSection) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("obj", roleSection);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("updateSectionRoleProfile_MST", sqlParam);
	}

	/**
	 * staffCodeユーザの指定機能・会社・部署でのアクセスレベル取得
	 * @param menuId メニューID
	 * @param staffCode 社員番号
	 * @param companyCode 会社コード
	 * @param sectionCode 部署コード
	 * @param sectionYear 部初年度
	 * @return アクセスレベル
	 * @throws SQLException
	 */
	public String selectAccessLevel(String menuId, String staffCode, String companyCode, String sectionCode, int sectionYear) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("menuId", menuId);
		sqlParam.put("staffCode", staffCode);
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sectionCode", sectionCode);
		sqlParam.put("sectionYear", sectionYear);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (String) sqlMap.queryForObject("selectAccessLevel_MST", sqlParam);
	}

	/**
	 * プロジェクト情報取得
	 * @param projectCode プロジェクトコード
	 * @return プロジェクト情報
	 * @throws SQLException
	 */
	public Project selectProject(String projectCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("projectCode", projectCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (Project) sqlMap.queryForObject("selectProject_MST", sqlParam);
	}

	/**
	 * コードネーム更新
	 * @param loginStaffCode ログインユーザ
	 * @param codeName コードネームオブジェクト
	 * @return なし
	 * @throws SQLException
	 */
	public void updateCodeName(String loginStaffCode, CodeName codeName) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("loginStaffCode", loginStaffCode);
		sqlParam.put("obj", codeName);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("updateCodeName_MST", sqlParam);
	}

	/**
	 * プロジェクト情報取得
	 * @param projectCode プロジェクトコード
	 * @return プロジェクト情報
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeNameSet> selectCodeNameSet(String loginStaffCode, String companyCode, String categoryName, List<String> roleCodeList) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("loginStaffCode", loginStaffCode);
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("categoryName", categoryName);
		sqlParam.put("roleCodeList", roleCodeList);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		
		return (List<CodeNameSet>) sqlMap.queryForList("selectCodeNameSet_MST", sqlParam);
	}

	/**
	 * 汎用マスタ設定項目検索
	 * @param categoryCode カテゴリコード
	 * @return 汎用マスタ項目制御
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeNameSetItem> selectCodeNameSetItem(String categoryCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("categoryCode", categoryCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<CodeNameSetItem>) sqlMap.queryForList("selectCodeNameSetItem_MST", sqlParam);
	}

	/**
	 * 汎用マスタ一覧検索
	 * @param categoryCode カテゴリコード（必須）
	 * @param internalCode 識別コード（任意）
	 * @param companyCode 会社コード（任意）
	 * @param values 値: 1番目(index0)=value1、2番目(index1)=value2・・・（任意）
	 * @param isEnableOnly 無効なデータも抜き出すかどうか true:有効データのみ、false:無効データも含める
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeName> selectCodeNameItemList(String categoryCode, String internalCode, String companyCode, List<String> values, boolean isEnableOnly) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("categoryCode", categoryCode);
		sqlParam.put("internalCode", internalCode);
		sqlParam.put("companyCode", companyCode);
		if(isEnableOnly){
			sqlParam.put("deleteFlag", "0");
		}

		if(values != null) {
			for(int i = 0; i < values.size(); i++) {
				if(values.get(i) != null) {
					sqlParam.put("value" + String.valueOf(i + 1), values.get(i));
				}
			}
		}

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<CodeName>) sqlMap.queryForList("selectCodeNameItemList_MST", sqlParam);
	}

	/**
	 * 汎用マスタ設定バリデーション
	 * @param categoryCode カテゴリコード（必須）
	 * @param values 値: 1番目(index0)=value1、2番目(index1)=value2・・・（任意）
	 * @param isEnableOnly 無効なデータも抜き出すかどうか true:有効データのみ、false:無効データも含める
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CodeNameSetValid> selectCodeNameSetValid(String categoryCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("categoryCode", categoryCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<CodeNameSetValid>) sqlMap.queryForList("selectCodeNameSetValid_MST", sqlParam);
	}

	/**
	 * 汎用マスタ設定バリデーション
	 * @param validTable バリデーションテーブル
	 * @param validColumn バリデーションカラム
	 * @return
	 * @throws SQLException
	 */
	public Object selectCodeNameSetValidCheck(String validTable, String validColumn, String internalCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("validTable", validTable);
		sqlParam.put("validColumn", validColumn + " = '" + internalCode + "' ");

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (Object) sqlMap.queryForObject("selectCodeNameSetValidCheck_MST", sqlParam);

	}

	/**
	 * 汎用マスタ設定新規作成
	 * @param obj 汎用データ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertCodeName(CodeName obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("obj", obj);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.insert("insertNeaCodeName_MST", sqlParam);
	}

	/**
	 * 汎用マスタ設定削除
	 * @param obj 汎用データ
	 * @return なし
	 * @throws SQLException
	 */
	public void deleteCodeName(String companyCode, String categoryCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("categoryCode", categoryCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.delete("deleteNeaCodeName_MST", sqlParam);
	}

	public void updateCodeNameSet(String loginStaffCode, String categoryCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("loginStaffCode", loginStaffCode);
		sqlParam.put("categoryCode", categoryCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("updateCodeNameSet_MST", sqlParam);

	}

	/**
	 * 汎用マスタ設定CSV作成
	 * @param categoryCode
	 * @param companyCode
	 * @return
	 * @throws SQLException
	 */
	public CsvWriterRowHandler createCodeNameListCsv(String categoryCode, String companyCode, String setCompanyCode) throws  SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("categoryCode", categoryCode);
		if(Function.nvl(setCompanyCode, "").equals("000")){
			param.put("companyCode", companyCode);
		}

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し
		param.put("isValue", "1");

		StringBuffer headerRow = new StringBuffer();

		CsvWriterRowHandler handler = null;
		try{

			List<CodeNameSetItem> codeNameSetItemList = selectCodeNameSetItem(categoryCode);

			headerRow.append("ソート値,親値,コード,停止,会社");
			List<String> outputPropList = new ArrayList<String>();
			List<Format> outputFormatList = new ArrayList<Format>();

			outputPropList.add("sortNumber");
			outputPropList.add("parentInternalName");
			outputPropList.add("internalCode");
			outputPropList.add("deleteFlagName");
			outputPropList.add("companyName");

			outputFormatList.add(null);
			outputFormatList.add(null);
			outputFormatList.add(null);
			outputFormatList.add(null);
			outputFormatList.add(null);

			for(int i = 0; i < codeNameSetItemList.size(); i++){
				CodeNameSetItem item = codeNameSetItemList.get((i));
				if(!Function.nvl(item.getItemName(), "").equals("")){
					headerRow.append(',' + item.getItemName());
					outputPropList.add("value" + (i+1));
					outputFormatList.add(null);
				}
			}

			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));
			sqlMap.queryWithRowHandler("selectCodeNameItemList_MST", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * ProPlus処理年月取得(取込済)
	 * @param companyCode 会社コード
	 * @return ProPlus処理年月
	 */
	public String getPpfsCurrentPeriod(String companyCode) throws  SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("companyCode", companyCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (String) sqlMap.queryForObject("selectPpfsCurrentPeriod_MST", param);
	}

	/**
	 * 資産管理担当者・部署長情報取得
	 * @param companyCode 会社コード
	 * @param sectionCode
	 * @return ProPlus処理年月
	 */
	@SuppressWarnings("unchecked")
	public List<RoleSection> selectUserRoleSectionAllList(String companyCode, String sectionCode, Integer sectionYear) throws  SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("companyCode", companyCode);
		param.put("sectionCode", sectionCode);
		param.put("sectionYear", sectionYear);
		return (List<RoleSection>) sqlMap.queryForList("selectUserRoleSectionAll_MST", param);
	}

	/**
	 * 資産(機器)一覧取得
	 * @param categoryCode カテゴリーコード
	 * @param parentCategoryCode 親カテゴリコード
	 * @param parentInternalCode 親識別コード
	 * @param companyCode 会社コード
	 * @param values 値一覧
	 * @return ProPlus処理年月
	 */
	@SuppressWarnings("unchecked")
	public List<LovDataEx> selectAstName(String parentCategoryCode, String parentInternalCode, String companyCode, Date sysdate, String makerName, String astName) throws  SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> sqlParam = new HashMap<String, Object>();

		sqlParam.put("parentCategoryCode", parentCategoryCode);
		sqlParam.put("parentInternalCode", parentInternalCode);
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("sysdate", sysdate);
		sqlParam.put("makerName", makerName);
		sqlParam.put("astName", astName);
		
		return (List<LovDataEx>) sqlMap.queryForList("selectAstName_LOV", sqlParam);
	}

	/**
	 * メニュー制御表示/非表示更新
	 * @param loginStaffCode ログイン社員番号
	 * @param companyCode 会社コード
	 * @param menuCode 親識別コード
	 * @param publicCode 親カテゴリコード
	 * @return なし
	 */
	public void updateAccessControl(String loginStaffCode, String companyCode, String menuCode, String publicCode) throws  SQLException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> sqlParam = new HashMap<String, Object>();

		sqlParam.put("loginStaffCode", loginStaffCode);
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("publicCode", publicCode);
		sqlParam.put("menuCode", menuCode);

		sqlMap.update("updateAccessControl_MST", sqlParam);

	}

	/**
	 * マスタデータ全件削除
	 * @param tableName テーブル名
	 * @return
	 * @throws SQLException
	 */
	public void deleteMasterData(String tableName) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("tableName", tableName);

		sqlMap.delete("deleteMasterData_MST", param);
	}

	/**
	 * 会社マスタデータ登録
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertCompanyMaster(CompanyMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertCompanyMaster_MST", param);
	}

	/**
	 * 部署マスタデータ登録
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertDivisionMaster(DivisionMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertDivisionMaster_MST", param);
	}

	/**
	 * 組織マスタデータ登録
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertNewDivisionMaster(NewDivisionMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertNewDivisionMaster_MST", param);
	}

	/**
	 * ユーザー所属マスタデータ登録
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertUserCompanyMaster(UserCompanyMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertUserCompany_MST", param);
	}

	/**
	 * ユーザー所属マスタ追加データリスト取得
	 * @param
	 * @return ユーザー所属マスタ追加データリスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<UserCompanyMasterData> selectUserCompanyOtherList() throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		
		return (List<UserCompanyMasterData>)sqlMap.queryForList("selectUserCompanyOtherList_MST");
	}

	/**
	 * ユーザー所属マスタ存在チェック
	 * @param userId ユーザID
	 * @param startDate 開始日
	 * @param officeCode オフィスコード
	 * @param companyCode 会社コード
	 * @param divisionCode 部課コード
	 * @param titleCode 役職コード
	 * @param additionalFlg 主務/兼務区分
	 * @return true:存在する, false:存在しない
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public boolean isUserCompanyMaster(Long userId, String startDate, String officeCode, String companyCode, String divisionCode, String titleCode, String additionalFlg) throws SQLException {
		boolean ret = false;

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("userId", userId);
		sqlParam.put("startDate", startDate);
		sqlParam.put("officeCode", officeCode);
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("divisionCode", divisionCode);
		sqlParam.put("titleCode", titleCode);
		sqlParam.put("additionalFlg", additionalFlg);
		
		List<UserCompanyMasterData> resultUserCompanyList = (List<UserCompanyMasterData>)sqlMap.queryForList("selectUserCompanyList_MST", sqlParam);
		if(resultUserCompanyList != null && resultUserCompanyList.size() > 0) {
			ret = true;
		}
		return ret;
	}

	/**
	 * ユーザーマスタデータ登録
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertUserMaster(UserMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertUser_MST", param);
	}

	/**
	 * ユーザーマスタ追加データリスト取得
	 * @param
	 * @return ユーザーマスタ追加データリスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<UserMasterData> selectUserOtherList() throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		
		return (List<UserMasterData>)sqlMap.queryForList("selectUserOtherList_MST");
	}

	/**
	 * ユーザーマスタ存在チェック
	 * @param userId ユーザID
	 * @return true:存在する, false:存在しない
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public boolean isUserMaster(Long userId) throws SQLException {
		boolean ret = false;

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("userId", userId);
		
		List<UserMasterData> resultUserList = (List<UserMasterData>)sqlMap.queryForList("selectUserList_MST", sqlParam);
		if(resultUserList != null && resultUserList.size() > 0) {
			ret = true;
		}
		return ret;
	}

	/**
	 * 検索用TEMPデータ登録
	 * @param searchKey 識別キー
	 * @param seq シーケンス
	 * @param jdbcType 値の型(NUMERIC or VARCHAR)
	 * @param obj 検索値
	 * @return
	 * @throws SQLException
	 */
	public void insertTempSearch(String searchKey, int seq, int jdbcType, Object value) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("searchKey", searchKey);
		param.put("seq", seq);
		if(jdbcType == java.sql.Types.NUMERIC) {
			param.put("numericValue", value);
		} else {
			param.put("varcharValue", value);
		}

		sqlMap.insert("insertTempSearch_MST", param);
	}

	/**
	 * 従業員所属マスタデータ更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void mergeSapAssignmentsMaster(SapAssignmentsMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		SapAssignmentsMasterData assignmentsData = (SapAssignmentsMasterData)sqlMap.queryForObject("selectSapAssignments_MST", param);

		if(assignmentsData == null) {
			sqlMap.insert("insertSapAssignments_MST", param);
		}
		else {
			sqlMap.update("updateSapAssignments_MST", param);
		}
	}

	/**
	 * 勘定科目マスタデータ更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void mergeSapAccountMaster(SapAccountMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		SapAccountMasterData accountData = (SapAccountMasterData)sqlMap.queryForObject("selectSapAccount_MST", param);

		if(accountData == null) {
			sqlMap.insert("insertSapAccount_MST", param);
		}
		else {
			sqlMap.update("updateSapAccount_MST", param);
		}
	}

	/**
	 * 顧客マスタデータ更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void mergeSapCustAccountsMaster(SapCustAccountsMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		SapCustAccountsMasterData custAccountsData = (SapCustAccountsMasterData)sqlMap.queryForObject("selectSapCustAccounts_MST", param);

		if(custAccountsData == null) {
			sqlMap.insert("insertSapCustAccounts_MST", param);
		}
		else {
			sqlMap.update("updateSapCustAccounts_MST", param);
		}
	}

	/**
	 * 仕入先マスタデータ更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void mergeSapVendorsMaster(SapVendorsMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		SapVendorsMasterData vendorsData = (SapVendorsMasterData)sqlMap.queryForObject("selectSapVendors_MST", param);

		if(vendorsData == null) {
			sqlMap.insert("insertSapVendors_MST", param);
		}
		else {
			sqlMap.update("updateSapVendors_MST", param);
		}
	}

	/**
	 * 経費負担部課マスタデータ更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void mergeSapExpDeptMaster(SapExpDeptMasterData obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		SapExpDeptMasterData expDeptData = (SapExpDeptMasterData)sqlMap.queryForObject("selectSapExpDept_MST", param);

		if(expDeptData == null) {
			sqlMap.insert("insertSapExpDept_MST", param);
		}
		else {
			Date startDate = obj.getStartDate();
			Date dbStartDate = expDeptData.getStartDate();
			if(dbStartDate.getTime() <= startDate.getTime()) {
				sqlMap.update("updateSapExpDept_MST", param);
			}
		}
	}

	/**
	 * Lovリスト情報取得
	 * @param sqlName SQL名
	 * @param paramMap SQLパラメータマップ
	 * @return LOVリスト
	 */
	@SuppressWarnings("unchecked")
	public List<LovDataEx> searchLovList(String sqlName, Map<String,Object> paramMap) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		//	SQLマップに定義されているSQLの呼び出し及び実行
		
		return (List<LovDataEx>)sqlMap.queryForList(sqlName, paramMap);
	}
}
