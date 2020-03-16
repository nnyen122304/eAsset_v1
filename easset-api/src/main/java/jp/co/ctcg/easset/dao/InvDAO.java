/*===================================================================
 * ファイル名 : InvDAO.java
 * 概要説明   : 棚卸用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2013-03-19 1.0  李            新規
 *=================================================================== */

package jp.co.ctcg.easset.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.ctcg.easset.dto.inv.InvLine;
import jp.co.ctcg.easset.dto.inv.InvStat;
import jp.co.ctcg.easset.dto.inv.InvSumSC;
import jp.co.ctcg.easset.dto.inv.InvSumSR;
import jp.co.ctcg.easset.dto.ppfs_import.PpfsStat;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

import com.ibatis.sqlmap.client.SqlMapClient;

public class InvDAO {

	/**
	 * 棚卸データ作成ステータス一覧取得
	 * @param companyCode 会社コード
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<InvStat> selectInvStatList(String companyCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);

		return (List<InvStat>)sqlMap.queryForList("selectInvStat_INV" ,param);
	}

	/**
	 * 棚卸データ作成ステータス取得
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @return
	 * @throws SQLException
	 */
	public InvStat selectInvStat(String companyCode, String period, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("period", period);
		if(lockFlag) param.put("lockFlag", "1");
		return (InvStat) sqlMap.queryForObject("selectInvStat_INV" ,param);
	}

	/**
	 * 棚卸集約情報検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照フラグ
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<InvSumSR> selectInvSumList(String loginStaffCode, String accessLevel, String companyCode, InvSumSC searchParam, boolean isHist, boolean lockFlag)  throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param = getSelectInvSumListParam(loginStaffCode, accessLevel, companyCode, searchParam, isHist);
		//	履歴参照？
		if(isHist){
			param.put("histTablePrefixLd", "H_");
		}
		if(lockFlag) param.put("lockFlag", "1");

		return (List<InvSumSR>)sqlMap.queryForList("selectInvSum_INV", param);
	}

	/**
	 * 棚卸集約情報検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照フラグ
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<InvSumSR> selectInvSumList(Long eappId, boolean isHist, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eappId", eappId);
		//	履歴参照？
		if(isHist){
			param.put("histTablePrefixLd", "H_");
		}
		if(lockFlag) param.put("lockFlag", "1");

		return (List<InvSumSR>)sqlMap.queryForList("selectInvSum_INV", param);
	}

	/**
	 * 棚卸集約情報(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectInvSumListParam(String loginStaffCode, String accessLevel, String companyCode, InvSumSC searchParam, boolean isHist) throws SQLException{

		MasterDAO masterDao = new MasterDAO();
		Integer currentYear = masterDao.getCurrentYear(); // カレント年度取得

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchParam", searchParam);
		param.put("companyCode", companyCode);
		// 検索対象：管轄分
		if(Function.nvl(searchParam.getSearchScopeType(), "").equals(Constants.SEARCH_SCOPE_TYPE_EDIT_ONLY)){
			//	一般？
			if(Function.isAccessLevelGeneral(accessLevel)){
				//	ログイン社員番号から
				//	保有部署取得
				param.put("isGeneral", "1");
				param.put("isUnknow", "1");
				param.put("invStaffCode", loginStaffCode);
				param.put("isPublic", "1");
			}
			else if(Function.isAccessLevelSection(accessLevel)) {				// 部署権限

				// アクセス可能階層部署コード一覧取得
				List<String> sectionCodeList = masterDao.selectAccessibleSectionCodeList(companyCode, loginStaffCode, searchParam.getIncludeSectionHierarchyFlag());
				List<String> staffColumnList = new ArrayList<String>();			// 社員番号カラム
				List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
				companyColumnList.add("nisum.COMPANY_CODE");
				List<String> sectionColumnList = new ArrayList<String>();		// 部署カラム
				sectionColumnList.add("nisum.HOL_SECTION_CODE");
				List<String> sectionYearColumnList = new ArrayList<String>();	// 部署年度カラム
				sectionYearColumnList.add("nisum.HOL_SECTION_YEAR");
				param.put("accessLevelSection", Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, loginStaffCode, sectionCodeList, currentYear));

				// NOTEXITST用アクセス可能階層部署コード一覧取得
				companyColumnList = new ArrayList<String>();			// 会社カラム
				companyColumnList.add("nisum1.COMPANY_CODE");
				sectionColumnList = new ArrayList<String>();		// 部署カラム
				sectionColumnList.add("nisum1.HOL_SECTION_CODE");
				sectionYearColumnList = new ArrayList<String>();	// 部署年度カラム
				sectionYearColumnList.add("nisum1.HOL_SECTION_YEAR");
				param.put("notExistsAccessLevelSection", Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, loginStaffCode, sectionCodeList, currentYear));

				param.put("isUnknow", "1");
				param.put("isPublic", "1");

				param.put("isSearchInvStaff", "1");
				param.put("invStaffCode", loginStaffCode);

				//	UNION ALLの部分棚卸実地状況
				List<String> invImplementationStaffPluralList = Function.getPluralList(searchParam.getInvImplementation());
				if(invImplementationStaffPluralList.size() > 0){
					StringBuffer invImplementationColumn = new StringBuffer();
					invImplementationColumn.append("(SELECT");
					invImplementationColumn.append("   CASE su.INV_COUNT_UNDECIDED ");
					invImplementationColumn.append("   WHEN su.INV_COUNT_TOTAL THEN '1' ");	//	未実施
					invImplementationColumn.append("   WHEN 0 THEN '3' ");	//	実施完了
					invImplementationColumn.append("   ELSE '2' END ");	//	実施中
					invImplementationColumn.append(" FROM ");
					// 一般権限？
					invImplementationColumn.append("   NEA" + (isHist ? "_H" : "") + "_INV_SUM_STAFF su ");
					invImplementationColumn.append(" WHERE su.PERIOD = nisum.PERIOD ");
					invImplementationColumn.append(" AND   su.COMPANY_CODE = nisum.COMPANY_CODE ");
					invImplementationColumn.append(" AND   su.HOL_SECTION_CODE = nisum.HOL_SECTION_CODE ");
					invImplementationColumn.append(" AND   su.HOL_SECTION_YEAR = nisum.HOL_SECTION_YEAR ");
					invImplementationColumn.append(" AND   su.INV_ASSET_TYPE = nisum.INV_ASSET_TYPE ");
					invImplementationColumn.append(" AND   su.INV_STAFF_CODE = '" + loginStaffCode + "' ");
					invImplementationColumn.append(")");
					param.put("invImplementationStaffPluralList", Function.getInCondition(invImplementationColumn.toString(), invImplementationStaffPluralList, true));
				}

			}
		}

		//////////////////////////////// 部署検索
		if(!Function.nvl(searchParam.getHolSectionCode(), "").equals("")) {
			List<String> sectionCodeList = new ArrayList<String>();

			if(Function.nvl(searchParam.getIncludeSectionHierarchyFlag(), "0").equals("1")) { // 階層指定有
				sectionCodeList.addAll(masterDao.selectHierarchySectionCodeList(searchParam.getCompanyCode(), searchParam.getHolSectionCode(), searchParam.getHolSectionYear()));
			} else { // 階層指定無し
				sectionCodeList.add(searchParam.getHolSectionCode());
			}

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("nisum.COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nisum.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nisum.HOL_SECTION_YEAR");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getCompanyCode(), null, sectionCodeList, searchParam.getHolSectionYear()));
		}

		// ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("nisum.AP_STATUS", apStatusPluralList, true));
		}

		// 資産区分
		List<String> invAssetTypePluralList = Function.getPluralList(searchParam.getInvAssetType());
		if(invAssetTypePluralList.size() > 0) {
			param.put("invAssetTypePluralList", Function.getInCondition("nisum.INV_ASSET_TYPE", invAssetTypePluralList, true));
		}

		//	棚卸実地状況
		List<String> invImplementationPluralList = Function.getPluralList(searchParam.getInvImplementation());
		if(invImplementationPluralList.size() > 0){
			StringBuffer invImplementationColumn = new StringBuffer();
			invImplementationColumn.append("(SELECT");
			invImplementationColumn.append("   CASE su.INV_COUNT_UNDECIDED ");
			invImplementationColumn.append("   WHEN su.INV_COUNT_TOTAL THEN '1' ");	//	未実施
			invImplementationColumn.append("   WHEN 0 THEN '3' ");	//	実施完了
			invImplementationColumn.append("   ELSE '2' END ");	//	実施中
			invImplementationColumn.append(" FROM ");
			// 一般権限？
			if(Function.isAccessLevelGeneral(accessLevel)){
				invImplementationColumn.append("   NEA" + (isHist ? "_H" : "") + "_INV_SUM_STAFF su ");
			}
			else{
				invImplementationColumn.append("   NEA"+ (isHist ? "_H" : "") + "_INV_SUM su ");
			}
			invImplementationColumn.append(" WHERE su.PERIOD = nisum.PERIOD ");
			invImplementationColumn.append(" AND   su.COMPANY_CODE = nisum.COMPANY_CODE ");
			invImplementationColumn.append(" AND   su.HOL_SECTION_CODE = nisum.HOL_SECTION_CODE ");
			invImplementationColumn.append(" AND   su.HOL_SECTION_YEAR = nisum.HOL_SECTION_YEAR ");
			invImplementationColumn.append(" AND   su.INV_ASSET_TYPE = nisum.INV_ASSET_TYPE ");
			//	一般権限？
			if(Function.isAccessLevelGeneral(accessLevel)){
				invImplementationColumn.append(" AND   su.INV_STAFF_CODE = '" + loginStaffCode + "' ");
			}
			invImplementationColumn.append(")");
			param.put("invImplementationPluralList", Function.getInCondition(invImplementationColumn.toString(), invImplementationPluralList, true));
		}

		// 棚卸データ紐付状態
		if(!Function.nvl(searchParam.getInvLinkType(), "").equals("")) {
			param.put("invLinkType", searchParam.getInvLinkType());
		}

		return param;

	}

	/**
	 * 棚卸明細情報(画面,CSV)共通パラメータ取得 ※ 集約情報画面からのﾀﾞｳﾝﾛｰﾄﾞのパラメータは別となっているので変更する場合、集約情報CSVのほうも注意
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectInvLineListParam(String loginStaffCode, String accessLevel, InvSumSR searchParam, String searchScopeType, boolean isHist) throws SQLException{

		Map<String, Object> param = new HashMap<String, Object>();
		String invLinkType = "";

		param.put("searchParam", searchParam);
		// 検索対象：管轄分
		if(Function.nvl(searchScopeType, "").equals(Constants.SEARCH_SCOPE_TYPE_EDIT_ONLY)){
			//	一般？
			if(Function.isAccessLevelGeneral(accessLevel)){
				//	棚卸対象者のみ参照
				param.put("invStaffCode", loginStaffCode);
			}
			else if(Function.isAccessLevelSection(accessLevel)){
				//
				if(Function.nvl(searchParam.getInvStaffFlag(), "").equals(Constants.FLAG_YES)){
					//	棚卸対象者のみ参照
					param.put("invStaffCode", loginStaffCode);
				}
			}
		}

		//	保有部署不明？
		if(searchParam.getHolSectionCode().equals(Constants.INV_UNKNOWN_SECTION_CODE)){
			if(invLinkType != ""){
				invLinkType = invLinkType + " ";
			}
			invLinkType = invLinkType + "2";
			//	保有部署空白にセット
			searchParam.setHolSectionCode(null);
			searchParam.setHolSectionYear(null);
		}
		//	資産情報不明？
		else if(searchParam.getHolSectionCode().equals(Constants.INV_UNKNOWN_ASSET)){
			if(invLinkType != ""){
				invLinkType = invLinkType + " ";
			}
			invLinkType = invLinkType + "3";
			//	保有部署空白にセット
			searchParam.setHolSectionCode(null);
			searchParam.setHolSectionYear(null);
		}else{
			invLinkType = "1";
		}
		// 棚卸連携
		List<String> invLinkTypePluralList = Function.getPluralList(invLinkType);
		if(invLinkTypePluralList.size() > 0) {
			param.put("invLinkTypePluralList", Function.getInCondition("nil.INV_LINK_TYPE", invLinkTypePluralList, true));
		}

		//////////////////////////////// 部署検索
		if(!Function.nvl(searchParam.getHolSectionCode(), "").equals("")) {
			List<String> sectionCodeList = new ArrayList<String>();

			sectionCodeList.add(searchParam.getHolSectionCode());

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("nil.COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nil.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nil.HOL_SECTION_YEAR");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getCompanyCode(), null, sectionCodeList, searchParam.getHolSectionYear()));
		}

		///////////////////////////	資産側の履歴テーブル参照フラグ
		String code = "";
		//	固定資産、資産除却費、無形固定資産(本勘定),無形固定資産(仮勘定)
		if(
			searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_TAN)
		|| searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_RO)
		|| searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT)
		|| searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT_S)
		){
			code = Constants.PPFS_IMPORT_DATA_TYPE_FLD;
		}
		//	リース・レンタル契約照会
		else if(searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_LE)
			|| searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_RE)){
			code = Constants.PPFS_IMPORT_DATA_TYPE_LLD;
		}
		if(!Function.nvl(code, "").equals("")){
			// 最新の取込年月取得
			String maxPeriod = "";
			PpfsImportDAO ppfsDao = new PpfsImportDAO();
			List<PpfsStat> importStatList = ppfsDao.selectPpfsStatList(searchParam.getCompanyCode(), code);
			for(Iterator<PpfsStat> iter = importStatList.iterator(); iter.hasNext();) {
				PpfsStat stat = iter.next();
				if(stat.getLastSuccessCreateDate() != null) {
					maxPeriod = stat.getPeriod();
					break;
				}
			}
			// 最新の取込年月と検索条件の年月比較
			if(!maxPeriod.equals(searchParam.getPeriod())) {
				//	固定資産、資産除却費、無形固定資産(本勘定),無形固定資産(仮勘定)
				if(
					searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_TAN)
				|| searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_RO)
				|| searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT)
				|| searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT_S)
				){
					param.put("histTablePrefixLdFld", "H_");
				}
				//	リース・レンタル契約照会
				else if(searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_LE)
					|| searchParam.getInvAssetType().equals(Constants.INV_ASSET_TYPE_RE)){
					param.put("histTablePrefixLdLld", "H_");
				}
			}
		}

		/////////////////////////	棚卸側の履歴テーブル参照フラグ
		//	履歴参照？
		if(isHist){
			param.put("histTablePrefixLdInv", "H_");
		}

		// 改行置換文字
		param.put("enterChar", " "); // 指定無し

		return param;

	}

	/**
	 * 棚卸データ情報検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照フラグ
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<InvLine> selectInvLineList(String loginStaffCode, String accessLevel, InvSumSR searchParam, String searchScopeType, boolean isHist)  throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param = getSelectInvLineListParam(loginStaffCode, accessLevel, searchParam, searchScopeType, isHist);
		String sqlName = "";

		//	実行SQL取得
		sqlName = getInvLineSqlName(searchParam.getInvAssetType());

		return (List<InvLine>)sqlMap.queryForList(sqlName, param);
	}

	/**
	 * 棚卸データ情報検索 ※資産、契約情報は除く
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam true:検索条件
	 * @param isDeleteFlag 棚卸対象外除外フラグ
	 * @param isHist 履歴参照フラグ
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<InvLine> selectInvLineOnlyList(String loginStaffCode, String accessLevel, InvLine searchParam, boolean isDeleteFlag, boolean isHist) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchParam", searchParam);
		if(isDeleteFlag){
			param.put("isDeleteFlag", "1");
		}
		//	履歴参照？
		if(isHist){
			param.put("histTablePrefixLd", "H_");
		}		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (List<InvLine>)sqlMap.queryForList("selectInvLine_INV", param);
	}

	/**
	 * 棚卸データ作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照フラグ
	 * @return
	 * @throws SQLException
	 */
	public void insertInvLine(InvLine obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertInvLine_Inv", param);
	}

	/**
	 * 棚卸データ作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照フラグ
	 * @return
	 * @throws SQLException
	 */
	public void updateInvLine(InvLine obj, boolean isHist) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		//	履歴参照？
		if(isHist){
			param.put("histTablePrefixLd", "H_");
		}

		sqlMap.update("updateInvLine_INV", param);
	}

	/**
	 * 棚卸集約情報更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照フラグ
	 * @return
	 * @throws SQLException
	 */
	public void updateInvSum(InvSumSR obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateInvSum_INV", param);
	}
	/**
	 * 棚卸集約対象者情報更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照フラグ
	 * @return
	 * @throws SQLException
	 */
	public void updateInvSumStaff(String loginStaffCode, String accessLevel, InvSumSR obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		param.put("invStaffCode", loginStaffCode);

		sqlMap.update("updateInvSumStaff_INV", param);
	}

	/**
	 * 棚卸データ削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照フラグ
	 * @return
	 * @throws SQLException
	 */
	public void deleteInvLine(String loginStaffCode, String accessLevel, InvLine obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();

		//	一般？
		if(Function.isAccessLevelGeneral(accessLevel)){
			param.put("isGeneral", "1");
			param.put("invStaffCode", loginStaffCode);	//	ログイン社員番号の棚卸対象データ
		}

		param.put("obj", obj);

		sqlMap.delete("deleteInvLine_INV", param);
	}

	/**
	 * 棚卸集約情報CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @return
	 * @throws SQLException
	 */
	public CsvWriterRowHandler createInvSumCsv(String loginStaffCode, String accessLevel, String companyCode, InvSumSC searchParam, boolean isHist) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param = getSelectInvSumListParam(loginStaffCode, accessLevel, companyCode, searchParam, isHist);
		// 改行置換文字
		param.put("enterChar", ""); // 指定無し
		//	履歴参照？
		if(isHist){
			param.put("histTablePrefixLd", "H_");
		}
		StringBuffer headerRow = new StringBuffer();
		List<String> outputPropList = new ArrayList<String>();
		List<Format> outputFormatList = new ArrayList<Format>();

		CsvWriterRowHandler handler = null;
		try {
			new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_INV_SUM, accessLevel, headerRow, outputPropList, outputFormatList);
			headerRow.insert(0, "会計年月：" + searchParam.getPeriod() + "\n");
			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));
			sqlMap.queryWithRowHandler("selectInvSum_INV", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 棚卸エクスポートCSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照？
	 * @return
	 * @throws SQLException
	 */
	public CsvWriterRowHandler createInvLineCsv(String loginStaffCode, String accessLevel, InvSumSR searchParam, String searchScopeType, boolean isHist) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		Map<String, Object> param = new HashMap<String, Object>();

		//	明細ﾀﾞｳﾝﾛｰﾄﾞとエクスポートは条件別で設定しているので注意
		param = getSelectInvLineListParam(loginStaffCode, accessLevel, searchParam, searchScopeType, isHist);

		return createInvLineCsvHandler(loginStaffCode, accessLevel, searchParam.getPeriod(), searchParam.getInvAssetType(), param);

	}

	/**
	 * 明細ダウンロードCSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @param isHist 履歴参照？
	 * @return
	 * @throws SQLException
	 */
	public CsvWriterRowHandler createInvLineListCsv(String loginStaffCode, String accessLevel, List<InvSumSR> list, String searchScopeType, boolean isHist) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		Map<String, Object> param = new HashMap<String, Object>();
		boolean isUnHol = false;	//	保有情報不明フラグ
		boolean isUnFld = false;	//	資産情報不明フラグ
		boolean isNormal = false;

		String invLinkType = "";

		StringBuffer section = new StringBuffer();

		param.put("searchParam", list.get(0));

		// 検索対象：管轄分
		if(Function.nvl(searchScopeType, "").equals(Constants.SEARCH_SCOPE_TYPE_EDIT_ONLY)){
			//	一般？
			if(Function.isAccessLevelGeneral(accessLevel)){
				//	棚卸対象者のみ参照
				param.put("invStaffCode", loginStaffCode);
			}
		}

		// 改行置換文字
		param.put("enterChar", " "); // 指定無し
		//////////////////////////////// 部署パラメータ
		List<String> sectionCodeList = new ArrayList<String>();
		for(Iterator<InvSumSR> iter = list.iterator(); iter.hasNext();){
			InvSumSR obj = iter.next();
			//	保有情報不明
			if(obj.getHolSectionCode().equals(Constants.INV_UNKNOWN_SECTION_CODE)){
				isUnHol = true;
			}
			//	資産情報不明
			else if(obj.getHolSectionCode().equals(Constants.INV_UNKNOWN_ASSET)){
				isUnFld = true;
			}
			else{
				sectionCodeList.add(obj.getHolSectionCode());
				isNormal = true;
			}
			// 検索対象：管轄分
			if(Function.nvl(searchScopeType, "").equals(Constants.SEARCH_SCOPE_TYPE_EDIT_ONLY)){
				//	部署長・資産管理担当者？
				if(Function.isAccessLevelSection(accessLevel)){
					if(section.length() == 0){
						section.append("( (");
					}
					else{
						section.append(" OR (");
					}
					//	棚卸対象者データ？
					if(Function.nvl(obj.getInvStaffFlag(), "").equals(Constants.FLAG_YES)){
						section.append("(nil.INV_STAFF_CODE = '" + loginStaffCode + "') AND ");
					}
					section.append(
									  "(nil.COMPANY_CODE,nil.HOL_SECTION_CODE,nil.HOL_SECTION_YEAR) IN "
									+ "(( '"+ obj.getCompanyCode() + "', '" + obj.getHolSectionCode() + "', " + obj.getHolSectionYear() + ")) "
									);
					section.append(")");

					section.append(")");

				}
			}
		}

		//	保有部署設定なし？
		if(section.length() == 0){
			List<String> companyColumnList = new ArrayList<String>();	// 会社カラム
			companyColumnList.add("nil.COMPANY_CODE");
			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nil.HOL_SECTION_CODE");
			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nil.HOL_SECTION_YEAR");
			section.append(Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, list.get(0).getCompanyCode(), null, sectionCodeList, list.get(0).getHolSectionYear()));
		}

		param.put("section", section.toString());

		if(isUnHol){
			if(invLinkType != ""){
				invLinkType = invLinkType + " ";
			}
			invLinkType = invLinkType + "2";
		}
		if(isUnFld){
			if(invLinkType != ""){
				invLinkType = invLinkType + " ";
			}
			invLinkType = invLinkType + "3";
		}
		if(isNormal){
			if(invLinkType != ""){
				invLinkType = invLinkType + " ";
			}
			invLinkType = invLinkType + "1";
		}
		// 棚卸連携
		List<String> invLinkTypePluralList = Function.getPluralList(invLinkType);
		if(invLinkTypePluralList.size() > 0) {
			param.put("invLinkTypePluralList", Function.getInCondition("nil.INV_LINK_TYPE", invLinkTypePluralList, true));
		}

		///////////////////////////	資産側の履歴テーブル参照フラグ
		String code = "";
		//	固定資産、資産除却費、無形固定資産(本勘定),無形固定資産(仮勘定)
		if(
			list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_TAN)
		|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_RO)
		|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT)
		|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT_S)
		){
			code = Constants.PPFS_IMPORT_DATA_TYPE_FLD;
		}
		//	リース・レンタル契約照会
		else if(list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_LE)
			|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_RE)){
			code = Constants.PPFS_IMPORT_DATA_TYPE_LLD;
		}
		if(!Function.nvl(code, "").equals("")){
			// 最新の取込年月取得
			String maxPeriod = "";
			PpfsImportDAO ppfsDao = new PpfsImportDAO();
			List<PpfsStat> importStatList = ppfsDao.selectPpfsStatList(list.get(0).getCompanyCode(), code);
			for(Iterator<PpfsStat> iter = importStatList.iterator(); iter.hasNext();) {
				PpfsStat stat = iter.next();
				if(stat.getLastSuccessCreateDate() != null) {
					maxPeriod = stat.getPeriod();
					break;
				}
			}
			// 最新の取込年月と検索条件の年月比較
			if(!maxPeriod.equals(list.get(0).getPeriod())) {
				//	固定資産、資産除却費、無形固定資産(本勘定),無形固定資産(仮勘定)
				if(
					list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_TAN)
				|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_RO)
				|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT)
				|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT_S)
				){
					param.put("histTablePrefixLdFld", "H_");
				}
				//	リース・レンタル契約照会
				else if(list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_LE)
					|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_RE)){
					param.put("histTablePrefixLdLld", "H_");
				}
			}
		}
		///////////////////////////	棚卸側の履歴テーブル参照フラグ
		//	履歴参照？
		if(isHist){
			param.put("histTablePrefixLdInv", "H_");
		}

		return createInvLineCsvHandler(loginStaffCode, accessLevel, list.get(0).getPeriod(), list.get(0).getInvAssetType(), param);

	}

	/**
	 * 棚卸明細ダウンロードおよび、エクスポートCSV作成取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param param ダウンロード検索条件
	 * @return
	 * @throws SQLException
	 */
	private CsvWriterRowHandler createInvLineCsvHandler(String loginStaffCode, String accessLevel, String period, String invAssetType, Map<String, Object> param) throws SQLException, IOException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		String categoryCode = getInvAssetCategoryCode(invAssetType);	//	カテゴリーコード取得
		String sqlName = getInvLineSqlName(invAssetType);	//	実行SQL名取得

		CsvWriterRowHandler handler = null;
		StringBuffer headerRow = new StringBuffer();
		List<String> outputPropList = new ArrayList<String>();
		List<Format> outputFormatList = new ArrayList<Format>();

		try {
			//	CSV用設定項目取得
			new MasterDAO().setCsvDef(categoryCode, accessLevel, headerRow, outputPropList, outputFormatList);
			headerRow.insert(0, "※インポートを実行する場合、棚卸状況、コメント以外の項目は編集しないでください。棚卸状況項目は「有」「無」を指定してください。" + "\n");
			headerRow.insert(0, "会計年月：" + period + " ");
			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));
			sqlMap.queryWithRowHandler(sqlName, param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 棚卸データ実行SQL取得
	 * @param invAssetType 資産区分
	 * @return
	 * @throws SQLException
	 */
	private String getInvLineSqlName(String invAssetType){
		String sqlName = "";
		//	固定資産、資産除却費、無形固定資産(本勘定)
		if(
			invAssetType.equals(Constants.INV_ASSET_TYPE_FLD_TAN)
		|| invAssetType.equals(Constants.INV_ASSET_TYPE_FLD_RO)
		|| invAssetType.equals(Constants.INV_ASSET_TYPE_FLD_INT)
		){
			sqlName = "selectInvLineFld_INV";
		}
		//	無形固定資産(仮勘定)
		else if(invAssetType.equals(Constants.INV_ASSET_TYPE_FLD_INT_S)){
			sqlName = "selectInvLineInt_INV";
		}
		//	リース・レンタル契約照会
		else if(invAssetType.equals(Constants.INV_ASSET_TYPE_LE)
			|| invAssetType.equals(Constants.INV_ASSET_TYPE_RE)){
			sqlName = "selectInvLineLld_INV";

		}
		//	備品
		else{
			sqlName = "selectInvLineEquip_INV";
		}
		return sqlName;
	}

	/**
	 * 棚卸データカテゴリーコード取得
	 * @param invAssetType 資産区分
	 * @return
	 * @throws SQLException
	 */
	private String getInvAssetCategoryCode(String invAssetType){
		String categoryCode = "";
		//	有形固定資産、資産除却費、
		if(
			invAssetType.equals(Constants.INV_ASSET_TYPE_FLD_TAN)
			|| invAssetType.equals(Constants.INV_ASSET_TYPE_FLD_RO)
		){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_FLD;
		}
		//	無形固定資産(本勘定)
		else if(invAssetType.equals(Constants.INV_ASSET_TYPE_FLD_INT)){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_INT;
		}
		//	無形固定資産(仮勘定)
		else if(invAssetType.equals(Constants.INV_ASSET_TYPE_FLD_INT_S)){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_INT_S;
		}
		//	リース契約
		else if(invAssetType.equals(Constants.INV_ASSET_TYPE_LE)){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_LE;
		}
		//	レンタル契約
		else if(invAssetType.equals(Constants.INV_ASSET_TYPE_RE)){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_RE;
		}
		//	備品
		else{
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_EQUIP;
		}
		return categoryCode;
	}

	/**
	 * 棚卸データ作成実行
	 * @param companyCode 会社コード
	 * @param period 会計年月(yyyyMM)
	 * @param execStaffCode 実行者社員番号
	 */
	public void callCreateInvData(String companyCode, String period, String execStaffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("period", period);
		sqlParam.put("execStaffCode", execStaffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("callCreateInvData_INV", sqlParam);
	}

	/**
	 * 棚卸データ作成ステータス更新
	 * @param companyCode 会社コード
	 * @param period 会計年月(yyyyMM)
	 * @param execStaffCode 実行者社員番号
	 * @param execStatus 実行ステータス 1:実行中、2:正常終了、3:異常終了
	 */
	public void callUpdateInvStatus(String companyCode, String period, String execStaffCode, String execStatus) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("period", period);
		sqlParam.put("execStaffCode", execStaffCode);
		sqlParam.put("execStatus", execStatus);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("callUpdateInvStatus_INV", sqlParam);
	}

	/**
	 * 棚卸集約データ実施状況更新
	 * @param companyCode 会社コード
	 * @param period 会計年月(yyyyMM)
	 * @param holSectionCode 保有部署
	 * @param holSectionYear 保有部署年度
	 * @param invAssetType 資産区分
	 * @param invStaffCode 棚卸担当者（一般権限実施の場合のみ指定する）
	 * @param execStaffCode 実行者社員番号
	 */
	public void callUpdateInvSumState(String companyCode, String period, String holSectionCode, Integer holSectionYear, String invAssetType, String invStaffCode, String execStaffCode, String apStatus, boolean isHist) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("period", period);
		sqlParam.put("holSectionCode", holSectionCode);
		sqlParam.put("holSectionYear", holSectionYear);
		sqlParam.put("invAssetType", invAssetType);
		sqlParam.put("invStaffCode", invStaffCode);
		sqlParam.put("execStaffCode", execStaffCode);
		sqlParam.put("apStatus", apStatus);
		//	履歴参照？
		if(isHist){
			sqlParam.put("histTablePrefixLd", "H_");
		}
		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("callUpdateInvSumState_INV", sqlParam);
	}

	/**
	 * 最新の棚卸会計年月取得
	 * @param companyCode 会社コード
	 */
	public String selectCurrentInvPeriod(String companyCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (String)sqlMap.queryForObject("selectCurrentInvPeriod_INV", sqlParam);
	}

	/**
	 * 棚卸ステータス更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateInvStat(InvStat obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateInvStat_INV", param);
	}

	/**
	 * 棚卸カレントデータ切替
	 * @param companyCode 会社コード
	 */
	public void callSwitchCurrentInvData(String companyCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("callSwitchCurrentInvData_INV", sqlParam);
	}

	/**
	 * 棚卸明細ダウンロードおよび、エクスポートCSV作成取得
	 * @return
	 * @throws SQLException
	 */
	public CsvWriterRowHandler createInvTemplateCsv(String companyCode, String period, boolean isHist) throws IOException, SQLException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		CsvWriterRowHandler handler = null;
		StringBuffer headerRow = new StringBuffer();
		Map<String, Object> param = new HashMap<String, Object>();

		try {
			//	CSV用設定項目取得
			headerRow.insert(0, "管理番号,棚卸状況,コメント");
			headerRow.insert(0, "\n");
			headerRow.insert(0, "※棚卸状況項目は「有」「無」を指定してください。\n");
			String[] outputProps = new String[]{"astLicId", "invStatusName", "invComment"};
			Format[] outputFormats = new Format[]{null, null, null};

			handler = new CsvWriterRowHandler(headerRow.toString(), outputProps, outputFormats);
			// パラメータ設定
			InvLine searchParam = new InvLine();
			searchParam.setCompanyCode(companyCode);
			searchParam.setPeriod(period);
			param.put("searchParam", searchParam);
			//	履歴参照？
			if(isHist){
				param.put("histTablePrefixLd", "H_");
			}
			// 改行置換文字
			param.put("enterChar", " "); // 指定無し
			param.put("isNotExistsAst+icId", "1"); // 管理番号が空白以外
			param.put("isDeleteFlag", "1"); // 棚卸対象外
			param.put("isOrderByAstLicId", "1");
			sqlMap.queryWithRowHandler("selectInvLine_INV", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}


}