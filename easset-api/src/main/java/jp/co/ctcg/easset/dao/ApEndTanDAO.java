/*===================================================================
 * ファイル名 : ApEndTanDAO.java
 * 概要説明   : 固定資産除売却申請用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-26 1.0  李           新規
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

import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTan;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineAst;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineAtt;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineFld;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineLic;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanSC;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanSR;
import jp.co.ctcg.easset.dto.ap_end_tan.PpfsYskCalc;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

public class ApEndTanDAO {

	/**
	 * 固定資産除売却申請検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return 固定資産(照会・管理項目修正)一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndTanSR> selectApEndTanList(String loginStaffCode, String accessLevel, ApEndTanSC searchParam ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApEndTanListParam(loginStaffCode, accessLevel, searchParam);

		return (List<ApEndTanSR>)sqlMap.queryForList("selectApEndTanList_APET", param);
	}

	/**
	 * 固定資産除売却申請取得(ヘッダ)
	 * @param applicationId 申請番号
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 固定資産除売却申請取得(ヘッダ)
	 * @throws SQLException
	 */
	public ApEndTan selectApEndTan(String applicationId, boolean lockFlag ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApEndTan)sqlMap.queryForObject("selectApEndTan_APET", param);
	}

	/**
	 * 固定資産除売却申請取得(ヘッダ)
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 取得申請(ヘッダ)
	 * @throws SQLException
	 */
	public ApEndTan selectApEndTan(Long eappId, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eappId", eappId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApEndTan)sqlMap.queryForObject("selectApEndTan_APET", param);
	}

	/**
	 * 固定資産除売却申請_資産明細取得
	 * @param applicationId 申請書番号
	 * @return 固定資産除売却申請_資産明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndTanLineFld> selectApEndTanLineFld(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApEndTanLineFld>)sqlMap.queryForList("selectApEndTanFldList_APET", param);
	}

	/**
	 * 固定資産除売却申請_資産(機器)明細取得
	 * @param applicationId 申請書番号
	 * @return 固定資産除売却申請_資産(機器)明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndTanLineAst> selectApEndTanLineAst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApEndTanLineAst>)sqlMap.queryForList("selectApEndTanAstList_APET", param);
	}

	/**
	 * 固定資産除売却申請_ライセンス明細取得
	 * @param applicationId 申請書番号
	 * @return 固定資産除売却申請_ライセンス明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndTanLineLic> selectApEndTanLineLic(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApEndTanLineLic>)sqlMap.queryForList("selectApEndTanLicList_APET", param);
	}

	/**
	 * 固定資産除売却申請_添付明細取得
	 * @param applicationId 申請書番号
	 * @return 固定資産除売却申請_添付明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndTanLineAtt> selectApEndTanLineAtt(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApEndTanLineAtt>)sqlMap.queryForList("selectApEndTanAttList_APET", param);
	}

	/**
	 * ProPlus取込：償却費予測結果取得
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @param koyunoPlueal 固有番号
	 * @param koyuno 固有番号
	 * @return 償却費予測結果一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PpfsYskCalc> selectPpfsYskCalc(String companyCode, Integer skkyydo, String koyunoPlueal, String koyuno) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("skkyydo", skkyydo);
		// 固有番号一覧
		List<String> koyunoPluealList = Function.getPluralList(koyunoPlueal);
		if(koyunoPluealList.size() > 0) {
			param.put("koyunoPluealList", Function.getInCondition("KOYUNO", koyunoPluealList, false));
		}
		param.put("koyuno", koyuno);

		return (List<PpfsYskCalc>)sqlMap.queryForList("selectPpfsYskCalc_APET", param);
	}

	/**
	 * ProPlus取込：償却費予測結果取得
	 * @param companyCode 会社コード
	 * @param periodFrom 会計年月(From)
	 * @param periodTo 会計年月(To)
	 * @param koyunoPlueal 固有番号
	 * @param koyuno 固有番号
	 * @return 償却費予測結果一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PpfsYskCalc> selectPpfsYskCalc(String companyCode, Integer skkyydoFrom, Integer skkyydoTo, String koyunoPlueal, String koyuno) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("skkyydoFrom", skkyydoFrom);
		param.put("skkyydoTo", skkyydoTo);
		// 固有番号一覧
		List<String> koyunoPluealList = Function.getPluralList(koyunoPlueal);
		if(koyunoPluealList.size() > 0) {
			param.put("koyunoPluealList", Function.getInCondition("KOYUNO", koyunoPluealList, false));
		}
		param.put("koyuno", koyuno);

		return (List<PpfsYskCalc>)sqlMap.queryForList("selectPpfsYskCalc_APET", param);
	}

	/**
	 * 固定資産除売却申請(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectApEndTanListParam(String loginStaffCode, String accessLevel, ApEndTanSC searchParam) throws SQLException
	{
		MasterDAO masterDao = new MasterDAO();

		Map<String, Object> param = new HashMap<String, Object>();

		Integer currentYear = masterDao.getCurrentYear(); // カレント年度主取得

		//////////////////////////////// アクセスレベル
		if(Function.isAccessLevelGeneral(accessLevel)) { // 一般権限
			searchParam.setApStaffCode(loginStaffCode);
		} else if(Function.isAccessLevelSection(accessLevel)) { // 部署権限
			// アクセス可能階層部署コード一覧取得
			List<String> sectionCodeList = masterDao.selectAccessibleSectionCodeList(searchParam.getHolCompanyCode(), loginStaffCode, searchParam.getIncludeSectionHierarchyFlag());

			List<String> staffColumnList = new ArrayList<String>(); // 社員番号カラム
			staffColumnList.add("naet1.AP_STAFF_CODE");
			staffColumnList.add("naet1.AP_CREATE_STAFF_CODE");

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("naet1.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("naet1.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("naet1.HOL_SECTION_YEAR");

			String accessLevelSection = new String();
			accessLevelSection = "("
				                 + Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCode(), loginStaffCode, sectionCodeList, currentYear)
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

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("naet1.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("naet1.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("naet1.HOL_SECTION_YEAR");

			String section = new String();
			section = "("
						+ Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear)
					+ ")";

			param.put("section", section);
			param.put("sectionYear", currentYear);
		}


		//////////////////////////////// 複数指定検索
		///////////////////// 基本
		// 除売却申請書番号
		List<String> applicationIdPluralList = Function.getPluralList(searchParam.getApplicationIdPlural());
		if(applicationIdPluralList.size() > 0) {
			param.put("applicationIdPluralList", Function.getInCondition("naet1.APPLICATION_ID", applicationIdPluralList, true));
		}
		// e申請書類ID
		List<String> eappIdPluralList = Function.getPluralList(searchParam.getEappIdPlural());
		if(eappIdPluralList.size() > 0) {
			param.put("eappIdPluralList", Function.getInCondition("naet1.EAPP_ID", eappIdPluralList, false));
		}
		// ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("naet1.AP_STATUS", apStatusPluralList, true));
		}
		// 除売却区分
		List<String> apEndTanTypePluralList = Function.getPluralList(searchParam.getApEndTanType());
		if(apEndTanTypePluralList.size() > 0) {
			param.put("apEndTanTypePluralList", Function.getInCondition("naet1.AP_END_TAN_TYPE", apEndTanTypePluralList, true));
		}
		// 除売却報告
		List<String> reportFlagPluralList = Function.getPluralList(searchParam.getReportFlag());
		if(reportFlagPluralList.size() > 0) {
			param.put("reportFlagPluralList", Function.getInCondition("naet1.REPORT_FLAG", reportFlagPluralList, true));
		}
		///////////////////// 管理番号
		// 情報機器管理番号
		List<String> assetIdPluralList = Function.getPluralList(searchParam.getAssetIdPlural());
		if(assetIdPluralList.size() > 0) {
			param.put("assetIdPluralList", Function.getInCondition("naet1.ASSET_ID", assetIdPluralList, true));
		}
		// ライセンス管理番号
		List<String> licenseIdPluralList = Function.getPluralList(searchParam.getLicenseIdPlural());
		if(licenseIdPluralList.size() > 0) {
			param.put("licenseIdPluralList", Function.getInCondition("naet1.LICENSE_ID", licenseIdPluralList, true));
		}
		// 取得申請書番号
		List<String> getApplicationIdPluralList = Function.getPluralList(searchParam.getGetApplicationIdPlural());
		if(getApplicationIdPluralList.size() > 0) {
			param.put("getApplicationIdPluralList", Function.getInCondition("naet1.GET_APPLICATION_ID", getApplicationIdPluralList, true));
		}
		//	資産番号
		// 取得申請書番号
		List<String> astNumPluralList = Function.getPluralList(searchParam.getAstNumPlural());
		if(astNumPluralList.size() > 0) {
			param.put("astNumPluralList", Function.getInCondition("naet1.AST_NUM", astNumPluralList, true));
		}

		param.put("searchParam", searchParam);

		return param;
	}

	/**
	 * 固定資産除売却申請作成(ヘッダ)
	 * @param obj 固定資産除売却申請エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndTan(ApEndTan obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndTan_APET", param);
	}

	/**
	 * 固定資産除売却申請_資産明細作成
	 * @param obj 固定資産除売却申請_資産エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndTanFld(ApEndTanLineFld obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndTanLineFld_APET", param);
	}

	/**
	 * 固定資産除売却申請_資産(機器)明細作成
	 * @param obj 固定資産除売却申請_資産(機器)エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndTanLineAst(ApEndTanLineAst obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndTanLineAst_APET", param);
	}

	/**
	 * 固定資産除売却申請_ライセンス明細作成
	 * @param obj 固定資産除売却申請_ライセンスエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndTanLineLic(ApEndTanLineLic obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndTanLineLic_APET", param);
	}

	/**
	 * 固定資産除売却申請_添付明細作成
	 * @param obj 固定資産除売却申請_ライセンスエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndTanLineAtt(ApEndTanLineAtt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndTanLineAtt_APET", param);
	}

	/**
	 * 固定資産除売却申請ヘッダ更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndTan(ApEndTan obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndTan_APET", param);
	}

	/**
	 * 固定資産除売却申請_資産更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndTanLineFld(ApEndTanLineFld obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndTanLineFld_APET", param);
	}

	/**
	 * 固定資産除売却申請_資産(機器)明細更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndTanLineAst(ApEndTanLineAst obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndTanLineAst_APET", param);
	}

	/**
	 * 固定資産除売却申請_ライセンス明細更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndTanLineLic(ApEndTanLineLic obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndTanLineLic_APET", param);
	}

	/**
	 * 固定資産除売却申請_添付明細更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndTanLineAtt(ApEndTanLineAtt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndTanLineAtt_APET", param);
	}

	/**
	 * 固定資産除売却申請(ヘッダ)削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndTan(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndTan_APET", param);
	}

	/**
	 * 固定資産除売却申請_資産明細削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndTanLineFld(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndTanLineFld_APET", param);
	}

	/**
	 * 固定資産除売却申請_資産(機器)明細削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndTanLineAst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndTanLineAst_APET", param);
	}

	/**
	 * 固定資産除売却申請_ライセンス明細削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndTanLineLic(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndTanLineLic_APET", param);
	}

	/**
	 * 固定資産除売却申請_添付明細削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndTanLineAtt(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndTanLineAtt_APET", param);
	}

	/**
	 * 除売却申請検索結果CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createApChangeListCsv(String loginStaffCode, String accessLevel, ApEndTanSC searchParam) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApEndTanListParam(loginStaffCode, accessLevel, searchParam);

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し

		StringBuffer headerRow = new StringBuffer();
		List<String> outputPropList = new ArrayList<String>();
		List<Format> outputFormatList = new ArrayList<Format>();

		CsvWriterRowHandler handler = null;
		try {
			String categoryItemDeg = "";
			//	有形?
			if(searchParam.getApFldType().equals("1")){
				categoryItemDeg = Constants.CATEGORY_CODE_ITEM_DEF_AP_END_TAN;
			}
			else{
				categoryItemDeg = Constants.CATEGORY_CODE_ITEM_DEF_AP_END_INT;
			}
			new MasterDAO().setCsvDef(categoryItemDeg, accessLevel, headerRow, outputPropList, outputFormatList);
			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));
			sqlMap.queryWithRowHandler("selectApEndTanList_APET", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 既に作成中の固定資産除売却申請_資産明細検索
	 * @param applicationId 申請書番号
	 * @return 固定資産除売却申請_資産明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndTanLineFld> selectApEndTanLineFldApply(ApEndTanSC searchParam) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		// ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("naet.AP_STATUS", apStatusPluralList, true));
		}
		//	資産番号
		// 取得申請書番号
		List<String> astNumPluralList = Function.getPluralList(searchParam.getAstNumPlural());
		if(astNumPluralList.size() > 0) {
			param.put("astNumPluralList", Function.getInCondition("fld.AST_NUM", astNumPluralList, true));
		}
		param.put("notApplicationId", searchParam.getApplicationId());

		return (List<ApEndTanLineFld>)sqlMap.queryForList("selectApEndTanFldList_APET", param);
	}

}
