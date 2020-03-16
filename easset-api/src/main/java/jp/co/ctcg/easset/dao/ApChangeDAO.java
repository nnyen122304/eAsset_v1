/*===================================================================
 * ファイル名 : ApChangeDAO.java
 * 概要説明   : 移動申請用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン       新規
 *=================================================================== */

package jp.co.ctcg.easset.dao;

import java.text.Format;
import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.ap_change.ApChange;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineAst;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineContract;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineCostSec;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineFld;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineLic;
import jp.co.ctcg.easset.dto.ap_change.ApChangeSR;
import jp.co.ctcg.easset.dto.ap_change.ApChangeSC;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

public class ApChangeDAO {

	/**
	 * 移動申請検索(ヘッダ)
	 * @param applicationId 移動申請書番号
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 移動申請(ヘッダ)
	 * @throws SQLException
	 */
	public ApChange selectApChange(String applicationId, boolean lockFlag) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", "");

		return (ApChange)sqlMap.queryForObject("selectApChange_APC", param);

	}

	/**
	 * 移動申請検索(ヘッダ)
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 移動申請(ヘッダ)
	 * @throws SQLException
	 */
	public ApChange selectApChange(Long eappId, boolean lockFlag) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eappId", eappId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", "");

		return (ApChange)sqlMap.queryForObject("selectApChange_APC", param);

	}

	/**
	 * 移動申請検索(資産明細)
	 * @param applicationId 移動申請書番号
	 * 		  apChangeLineFldType 資産明細区分-1:有形固定資産、2:無形固定資産
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApChangeLineFld> selectApChangeLineFld(String applicationId, String apChangeLineFldType) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("apChangeLineFldType", apChangeLineFldType);

		// 改行置換文字
		param.put("enterChar", "");

		return (List<ApChangeLineFld>)sqlMap.queryForList("selectApChangeLineFld_APC", param);
	}

	/**
	 * 移動申請検索(契約明細)
	 * @param applicationId 移動申請書番号
	 * 		  apChangeLineContractType 契約明細区分-1:リース契約、2:レンタル契約
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApChangeLineContract> selectApChangeLineContract(String applicationId, String apChangeLineContractType) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("apChangeLineContractType", apChangeLineContractType);

		// 改行置換文字
		param.put("enterChar", "");

		return (List<ApChangeLineContract>)sqlMap.queryForList("selectApChangeLineContract_APC", param);
	}

	/**
	 * 移動申請検索(資産(機器)明細)
	 * @param applicationId 移動申請書番号
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApChangeLineAst> selectApChangeLineAst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		// 改行置換文字
		param.put("enterChar", "");

		return (List<ApChangeLineAst>)sqlMap.queryForList("selectApChangeLineAst_APC", param);
	}

	/**
	 * 移動申請検索(ライセンス明細)
	 * @param applicationId 移動申請書番号
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApChangeLineLic> selectApChangeLineLic(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		// 改行置換文字
		param.put("enterChar", "");

		return (List<ApChangeLineLic>)sqlMap.queryForList("selectApChangeLineLic_APC", param);
	}

	/**
	 * 移動申請検索(経費負担部課明細)
	 * @param applicationId 移動申請書番号
	 * @param apChangeLineCostType 経費負担部課明細区分-1:1:リース契約,2:レンタル契約,A:移動元,B:移動先
	 * @param astNum 資産番号
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApChangeLineCostSec> selectApChangeLineCostSec(String applicationId, String apChangeLineCostType, String astNum) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("apChangeLineCostType", apChangeLineCostType);
		param.put("astNum", astNum);

		// 改行置換文字
		param.put("enterChar", "");

		return (List<ApChangeLineCostSec>)sqlMap.queryForList("selectApChangeLineCostSec_APC", param);
	}

	/**
	 * 移動申請検索
	 * @param loginStaffCode ログイン社員番号
	 *		  accessLevel    アクセスレベル
	 *		  searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApChangeSR> selectApChangeList(String loginStaffCode, String accessLevel, ApChangeSC searchParam) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = getSelectApChangeListParam(loginStaffCode, accessLevel, searchParam);

		// 改行置換文字
		param.put("enterChar", " ");

		return (List<ApChangeSR>)sqlMap.queryForList("selectApChangeList_APC", param);

	}

	/**
	 * 移動申請検索結果CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createApChangeListCsv(String loginStaffCode, String accessLevel, ApChangeSC searchParam) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApChangeListParam(loginStaffCode, accessLevel, searchParam);

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し

		StringBuffer headerRow = new StringBuffer();
		List<String> outputPropList = new ArrayList<String>();
		List<Format> outputFormatList = new ArrayList<Format>();

		CsvWriterRowHandler handler = null;
		try {
			new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_CHANGE, accessLevel, headerRow, outputPropList, outputFormatList);
			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));
			sqlMap.queryWithRowHandler("selectApChangeList_APC", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 移動申請検索(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectApChangeListParam(String loginStaffCode, String accessLevel, ApChangeSC searchParam) throws SQLException {

		MasterDAO masterDao = new MasterDAO();

		Map<String, Object> param = new HashMap<String, Object>();

		Integer currentYear = masterDao.getCurrentYear(); // カレント年度主取得

		param.put("searchParam", searchParam);

		//////////////////////////////// アクセスレベル
		if(Function.isAccessLevelGeneral(accessLevel)) { // 一般権限
			searchParam.setApStaffCode(loginStaffCode);
		} else if(Function.isAccessLevelSection(accessLevel)) { // 部署権限
			// アクセス可能階層部署コード一覧取得
			List<String> sectionCodeList = masterDao.selectAccessibleSectionCodeList(searchParam.getHolCompanyCode(), loginStaffCode, searchParam.getIncludeSectionHierarchyFlag());

			List<String> staffColumnList = new ArrayList<String>(); // 社員番号カラム
			staffColumnList.add("nac1.AP_STAFF_CODE");
			staffColumnList.add("nac1.AP_CREATE_STAFF_CODE");

			List<String> companyColumnListBefor = new ArrayList<String>();			// 会社カラム(変更前)
			companyColumnListBefor.add("nac1.OLD_HOL_COMPANY_CODE");
			List<String> companyColumnListAfter = new ArrayList<String>();			// 会社カラム(変更後)
			companyColumnListAfter.add("nac1.HOL_COMPANY_CODE");

			List<String> sectionColumnListBefor = new ArrayList<String>(); // 部署カラム(変更前)
			sectionColumnListBefor.add("nac1.OLD_HOL_SECTION_CODE");
			List<String> sectionColumnListAfter = new ArrayList<String>(); // 部署カラム(変更後)
			sectionColumnListAfter.add("nac1.HOL_SECTION_CODE");

			List<String> sectionYearColumnListBefor = new ArrayList<String>(); // 部署年度カラム(変更前)
			sectionYearColumnListBefor.add("nac1.OLD_HOL_SECTION_YEAR");
			List<String> sectionYearColumnListAfter = new ArrayList<String>(); // 部署年度カラム(変更後)
			sectionYearColumnListAfter.add("nac1.HOL_SECTION_YEAR");

			String accessLevelSection = new String();
			accessLevelSection = "(" + Function.getSectionCondition(staffColumnList, companyColumnListBefor, sectionColumnListBefor, sectionYearColumnListBefor, searchParam.getHolCompanyCode(), loginStaffCode, sectionCodeList, currentYear)
								 + " OR " + Function.getSectionCondition(staffColumnList, companyColumnListAfter, sectionColumnListAfter, sectionYearColumnListAfter, searchParam.getHolCompanyCode(), loginStaffCode, sectionCodeList, currentYear)
								 + ")";
			param.put("accessLevelSection", accessLevelSection);
		}


		//////////////////////////////// 部署検索
		if(!Function.nvl(searchParam.getHolSectionCode(), "").equals("")) {
			List<String> sectionCodeList = new ArrayList<String>();

			if(Function.nvl(searchParam.getIncludeSectionHierarchyFlag(), "0").equals("1")) { // 階層指定有
				sectionCodeList.addAll(masterDao.selectHierarchySectionCodeList(searchParam.getHolCompanyCode(), searchParam.getHolSectionCode(), currentYear));
			} else { // 階層指定無し
				sectionCodeList.add(searchParam.getHolSectionCode());
			}

			List<String> companyColumnListBefor = new ArrayList<String>();			// 会社カラム(変更前)
			companyColumnListBefor.add("nac1.OLD_HOL_COMPANY_CODE");
			List<String> companyColumnListAfter = new ArrayList<String>();			// 会社カラム(変更後)
			companyColumnListAfter.add("nac1.HOL_COMPANY_CODE");

			List<String> sectionColumnListBefor = new ArrayList<String>(); // 部署カラム(変更前)
			sectionColumnListBefor.add("nac1.OLD_HOL_SECTION_CODE");
			List<String> sectionColumnListAfter = new ArrayList<String>(); // 部署カラム(変更後)
			sectionColumnListAfter.add("nac1.HOL_SECTION_CODE");

			List<String> sectionYearColumnListBefor = new ArrayList<String>(); // 部署年度カラム(変更前)
			sectionYearColumnListBefor.add("nac1.OLD_HOL_SECTION_YEAR");
			List<String> sectionYearColumnListAfter = new ArrayList<String>(); // 部署年度カラム(変更後)
			sectionYearColumnListAfter.add("nac1.HOL_SECTION_YEAR");

			String section = new String();
			//	両方
			if(searchParam.getSearchTarget().equals("0")){
				section = "(" + Function.getSectionCondition(null, companyColumnListBefor, sectionColumnListBefor, sectionYearColumnListBefor, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear)
						  + " OR " + Function.getSectionCondition(null, companyColumnListAfter, sectionColumnListAfter, sectionYearColumnListAfter, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear)
						  + ")";
			//	変更前
			}else if(searchParam.getSearchTarget().equals("1")){
				section = Function.getSectionCondition(null, companyColumnListBefor, sectionColumnListBefor, sectionYearColumnListBefor, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear);
			//	変更後
			}else{
/*
				sectionColumnListAfter = new ArrayList<String>(); // 部署カラム(変更後、変更なしの場合は変更前)
				sectionColumnListAfter.add("NVL(nac1.HOL_SECTION_CODE, nac1.OLD_HOL_SECTION_CODE)");

				sectionYearColumnListAfter = new ArrayList<String>(); // 部署年度カラム(変更後、変更なしの場合は変更前)
				sectionYearColumnListAfter.add("NVL(nac1.HOL_SECTION_YEAR, nac1.OLD_HOL_SECTION_YEAR)");
*/
				List<String> companyColumnList = new ArrayList<String>();			// 会社カラム(変更前)
				companyColumnList.add("NVL(nac1.HOL_COMPANY_CODE, nac1.OLD_HOL_COMPANY_CODE)");

				section = Function.getSectionCondition(null, companyColumnList, sectionColumnListAfter, sectionYearColumnListAfter, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear);
			}

			param.put("section", section);
			param.put("sectionYear", currentYear);
		}

		//////////////////////////////// 複数指定検索
		// 申請区分
		List<String> apChangeTypePluralList = Function.getPluralList(searchParam.getApChangeType());
		if(apChangeTypePluralList.size() > 0) {
			param.put("apChangeTypePluralList", Function.getInCondition("nac1.AP_CHANGE_TYPE", apChangeTypePluralList, true));
		}
		// 取得申請書番号
		List<String> applicationIdPluralList = Function.getPluralList(searchParam.getApplicationIdPlural());
		if(applicationIdPluralList.size() > 0) {
			param.put("applicationIdPluralList", Function.getInCondition("nac1.APPLICATION_ID", applicationIdPluralList, true));
		}
		// ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("nac1.AP_STATUS", apStatusPluralList, true));
		}
		// 情報機器管理番号
		List<String> assetIdPluralList = Function.getPluralList(searchParam.getAssetIdPlural());
		if(assetIdPluralList.size() > 0) {
			param.put("assetIdPluralList", Function.getInCondition("nac1.ASSET_ID", assetIdPluralList, true));
		}
		// ライセンス管理番号
		List<String> licenseIdPluralList = Function.getPluralList(searchParam.getLicenseIdPlural());
		if(licenseIdPluralList.size() > 0) {
			param.put("licenseIdPluralList", Function.getInCondition("nac1.LICENSE_ID", licenseIdPluralList, true));
		}
		// 契約番号
		List<String> contractNumPluralList = Function.getPluralList(searchParam.getContractNumPlural());
		if(contractNumPluralList.size() > 0) {
			param.put("contractNumPluralList", Function.getInCondition("nac1.CONTRACT_NUM", contractNumPluralList, true));
		}
		// 契約枝番
		List<String> contractSubNumPluralList = Function.getPluralList(searchParam.getContractSubNumPlural());
		if(contractSubNumPluralList.size() > 0) {
			param.put("contractSubNumPluralList", Function.getInCondition("nac1.CONTRACT_BRANCH_NUM", contractSubNumPluralList, true));
		}
		// 資産番号
		List<String> astNumPluralList = Function.getPluralList(searchParam.getAstNumPlural());
		if(astNumPluralList.size() > 0) {
			param.put("astNumPluralList", Function.getInCondition("nac1.AST_NUM", astNumPluralList, true));
		}
		// e申請書類ID
		List<String> eappIdPluralList = Function.getPluralList(searchParam.getEappIdPlural());
		if(eappIdPluralList.size() > 0) {
			param.put("eappIdPluralList", Function.getInCondition("nac1.EAPP_ID", eappIdPluralList, false));
		}

		return param;

	}

	/**
	 * 移動申請ヘッダ作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApChange(ApChange obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApChange_APC", param);
	}

	/**
	 * 移動申請_資産明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApChangeLineFld(ApChangeLineFld obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApChangeLineFld_APC", param);
	}

	/**
	 * 移動申請_契約明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApChangeLineContract(ApChangeLineContract obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApChangeLineContract_APC", param);
	}

	/**
	 * 移動申請_資産(機器)明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApChangeLineAst(ApChangeLineAst obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApChangeLineAst_APC", param);
	}

	/**
	 * 移動申請_ライセンス明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApChangeLineLic(ApChangeLineLic obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApChangeLineLic_APC", param);
	}


	/**
	 * 移動申請_経費負担部課明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApChangeLineCostSec(ApChangeLineCostSec obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApChangeLineCostSec_APC", param);
	}

	/**
	 * 移動申請ヘッダ更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApChange(ApChange obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApChange_APC", param);
	}

	/**
	 * 移動申請ヘッダ削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApChange(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApChange_APC", param);
	}

	/**
	 * 移動申請_資産明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApChangeLineFld(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApChangeLineFld_APC", param);
	}

	/**
	 * 移動申請_契約明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApChangeLineContract(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApChangeLineContract_APC", param);
	}

	/**
	 * 移動申請_資産(機器)明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApChangeLineAst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApChangeLineAst_APC", param);
	}

	/**
	 * 移動申請_ライセンス明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApChangeLineLic(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApChangeLineLic_APC", param);
	}

	/**
	 * 移動申請_経費負担部課明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApChangeLineCostSec(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApChangeLineCostSec_APC", param);
	}

	/**
	 * 移動申請承認経路取得
	 * @param companyCode 会社コード
	 * @param changeItemList 変更対象項目コードリスト
	 * @param changeObjectTypeList 変更対象種別コードリスト
	 * @return 移動申請承認経路
	 * @throws SQLException
	 */
	public CodeName selectApChangeEappRoute(String companyCode, String[] changeItemList, String[] changeObjectTypeList) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("changeItemList", changeItemList);
		param.put("changeObjectTypeList", changeObjectTypeList);

		return (CodeName) sqlMap.queryForObject("selectApChangeEappRoute_APC", param);
	}

	/**
	 * 配賦マスタ経費負担部署検索
	 * @param companyCode 会社コード
	 * @param distCode 配賦コード
	 * @return 経費負担部課明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApChangeLineCostSec> selectApChangeLineCostSecDist(String companyCode, String distCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("distCode", distCode);

		return (List<ApChangeLineCostSec>)sqlMap.queryForList("selectApChangeLineCostSecDist_APC", param);

	}
}
