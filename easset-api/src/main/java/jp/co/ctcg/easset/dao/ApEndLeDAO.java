/*===================================================================
 * ファイル名 : ApEndLeDAO.java
 * 概要説明   : リース満了・解約申請用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-12-21 1.0  高山         新規
 *=================================================================== */

package jp.co.ctcg.easset.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.ctcg.easset.dto.ap_end_le.ApEndLe;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeLineAst;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeLineAtt;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeLineLld;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeLineLic;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeSC;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeSR;
import jp.co.ctcg.easset.dto.lld.PpfsLldSC;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

import com.ibatis.sqlmap.client.SqlMapClient;

public class ApEndLeDAO {

	/**
	 * リース満了・解約申請検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return 固定資産(照会・管理項目修正)一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndLeSR> selectApEndLeList(String loginStaffCode, String accessLevel, ApEndLeSC searchParam ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApEndLeListParam(loginStaffCode, accessLevel, searchParam);

		//同一契約物件申請状態
		List<String> sameContractApStatusPluralList = Function.getPluralList(searchParam.getSameContractApStatus());
		if(sameContractApStatusPluralList.size() > 0) {
			param.put("sameContractApStatusPluralList", "1");

			String sameContractApStatus1 = "";
			String sameContractApStatus2 = "";
			String sameContractApStatus3 = "";
			String sameContractApStatus4 = "";
			String headNo = "";

			if(searchParam.getSameContractApStatus().indexOf("1") > -1){
				sameContractApStatus1 = "1";
				param.put("sameContractApStatus1", sameContractApStatus1);
				headNo = "1";
			}
			if(searchParam.getSameContractApStatus().indexOf("2") > -1){
				sameContractApStatus2 = "1";
				param.put("sameContractApStatus2", sameContractApStatus2);
				if(headNo.equals("")){
					headNo = "2";
				}
			}
			if(searchParam.getSameContractApStatus().indexOf("3") > -1){
				sameContractApStatus3 = "1";
				param.put("sameContractApStatus3", sameContractApStatus3);
				if(headNo.equals("")){
					headNo = "3";
				}
			}
			if(searchParam.getSameContractApStatus().indexOf("4") > -1){
				sameContractApStatus4 = "1";
				param.put("sameContractApStatus4", sameContractApStatus4);
				if(headNo.equals("")){
					headNo = "4";
				}
			}
			param.put("headNo", headNo);
		}
		return (List<ApEndLeSR>)sqlMap.queryForList("selectApEndLeList_APEL", param);
	}

	/**
	 * リース満了・解約申請取得(ヘッダ)
	 * @param applicationId 申請番号
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return リース満了・解約申請取得(ヘッダ)
	 * @throws SQLException
	 */
	public ApEndLe selectApEndLe(String applicationId, boolean lockFlag ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApEndLe)sqlMap.queryForObject("selectApEndLe_APEL", param);
	}

	/**
	 * リース満了・解約申請取得(ヘッダ)
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return リース満了・解約申請(ヘッダ)
	 * @throws SQLException
	 */
	public ApEndLe selectApEndLe(Long eappId, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eappId", eappId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApEndLe)sqlMap.queryForObject("selectApEndLe_APEL", param);
	}

	/**
	 * リース満了・解約申請_物件明細取得
	 * @param applicationId 申請書番号
	 * @return リース満了・解約申請_物件明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndLeLineLld> selectApEndLeLineLld(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApEndLeLineLld>)sqlMap.queryForList("selectApEndLeLineLld_APEL", param);
	}

	/**
	 * リース満了・解約申請_資産(機器)明細取得
	 * @param applicationId 申請書番号
	 * @return リース満了・解約申請_資産(機器)明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndLeLineAst> selectApEndLeLineAst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApEndLeLineAst>)sqlMap.queryForList("selectApEndLeLineAst_APEL", param);
	}

	/**
	 * リース満了・解約申請_ライセンス明細取得
	 * @param applicationId 申請書番号
	 * @return リース満了・解約申請_ライセンス明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndLeLineLic> selectApEndLeLineLic(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApEndLeLineLic>)sqlMap.queryForList("selectApEndLeLineLic_APEL", param);
	}

	/**
	 * リース満了・解約申請_同一契約他物件情報検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param koyunoC        固有番号（契約）
	 * @param koyunoAPlural  固有番号（物件）
	 * @return 固定資産(照会・管理項目修正)一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndLeLineLld> selectOtherLldList(String loginStaffCode, String accessLevel, String companyCode, Long ppIdContract, PpfsLldSC searchParam) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("ppIdContract", ppIdContract);
		List<String> koyunoAPluralList = Function.getPluralList(searchParam.getKoyunoAPlural());
		if(koyunoAPluralList.size() > 0) {
			param.put("koyunoANotPluralList", Function.getNotInCondition("ptvla.KOYUNO", koyunoAPluralList, true));
			param.put("ppIdAstNotPluralList", Function.getNotInCondition("naelll.PP_ID_AST", koyunoAPluralList, true));
		}

		return (List<ApEndLeLineLld>)sqlMap.queryForList("selectApEndLeLineOtherLld_APEL", param);
	}

	/**
	 * リース満了・解約申請_添付明細取得
	 * @param applicationId 申請書番号
	 * @return リース満了・解約申請_添付明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndLeLineAtt> selectApEndLeLineAtt(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApEndLeLineAtt>)sqlMap.queryForList("selectApEndLeLineAtt_APEL", param);
	}

	/**
	 * リース満了・解約申請(画面,CSV)共通パラメータ取得 ?????????????????????????
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectApEndLeListParam(String loginStaffCode, String accessLevel, ApEndLeSC searchParam) throws SQLException
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
			staffColumnList.add("nael1.AP_STAFF_CODE");
			staffColumnList.add("nael1.AP_CREATE_STAFF_CODE");

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("nael1.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nael1.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nael1.HOL_SECTION_YEAR");

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
			companyColumnList.add("nael1.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nael1.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nael1.HOL_SECTION_YEAR");

			String section = new String();
			section = "("
						+ Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear)
					+ ")";

			param.put("section", section);
			param.put("sectionYear", currentYear);
		}


		//////////////////////////////// 複数指定検索

		// 申請書番号
		List<String> applicationIdPluralList = Function.getPluralList(searchParam.getApplicationIdPlural());
		if(applicationIdPluralList.size() > 0) {
			param.put("applicationIdPluralList", Function.getInCondition("nael1.APPLICATION_ID", applicationIdPluralList, true));
		}
		// e申請書類ID
		List<String> eappIdPluralList = Function.getPluralList(searchParam.getEappIdPlural());
		if(eappIdPluralList.size() > 0) {
			param.put("eappIdPluralList", Function.getInCondition("nael1.EAPP_ID", eappIdPluralList, false));
		}
		// ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("nael1.AP_STATUS", apStatusPluralList, true));
		}
		// 申請区分
		List<String> apEndLeTypePluralList = Function.getPluralList(searchParam.getApEndLeType());
		if(apEndLeTypePluralList.size() > 0) {
			param.put("apEndLeTypePluralList", Function.getInCondition("nael1.AP_END_LE_TYPE", apEndLeTypePluralList, true));
		}
		// 契約番号
		List<String> contractNumPluralList = Function.getPluralList(searchParam.getContractNumPlural());
		if(contractNumPluralList.size() > 0) {
			param.put("contractNumPluralList", Function.getInCondition("nael1.CONTRACT_NUM", contractNumPluralList, true));
		}
		// 契約枝番
		List<String> contractSubNumPluralList = Function.getPluralList(searchParam.getContractSubNumPlural());
		if(contractSubNumPluralList.size() > 0) {
			param.put("contractSubNumPluralList", Function.getInCondition("nael1.CONTRACT_SUB_NUM", contractSubNumPluralList, true));
		}
		// 資産番号
		List<String> astNumPluralList = Function.getPluralList(searchParam.getAstNumPlural());
		if(astNumPluralList.size() > 0) {
			param.put("astNumPluralList", Function.getInCondition("nael1.AST_NUM", astNumPluralList, true));
		}
		// 情報機器管理番号
		List<String> assetIdPluralList = Function.getPluralList(searchParam.getAssetIdPlural());
		if(assetIdPluralList.size() > 0) {
			param.put("assetIdPluralList", Function.getInCondition("nael1.ASSET_ID", assetIdPluralList, true));
		}
		// ライセンス管理番号
		List<String> licenseIdPluralList = Function.getPluralList(searchParam.getLicenseIdPlural());
		if(licenseIdPluralList.size() > 0) {
			param.put("licenseIdPluralList", Function.getInCondition("nael1.LICENSE_ID", licenseIdPluralList, true));
		}

		param.put("searchParam", searchParam);

		return param;
	}

	/**
	 * リース満了・解約申請作成(ヘッダ)
	 * @param obj リース満了・解約申請エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndLe(ApEndLe obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndLe_APEL", param);
	}

	/**
	 * リース満了・解約申請_物件明細作成
	 * @param obj ﾘｰｽ満了・解約申請_資産エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndLeLineLld(ApEndLeLineLld obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndLeLineLld_APEL", param);
	}

	/**
	 * リース満了・解約申請_資産(機器)明細作成
	 * @param obj リース満了・解約申請_資産(機器)エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndLeLineAst(ApEndLeLineAst obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndLeLineAst_APEL", param);
	}

	/**
	 * リース満了・解約申請_ライセンス明細作成
	 * @param obj リース満了・解約申請_ライセンスエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndLeLineLic(ApEndLeLineLic obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndLeLineLic_APEL", param);
	}

	/**
	 * リース満了・解約申請_添付明細作成
	 * @param obj リース満了・解約申請_ライセンスエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApEndLeLineAtt(ApEndLeLineAtt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApEndLeLineAtt_APEL", param);
	}

	/**
	 * リース満了・解約申請ヘッダ更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndLe(ApEndLe obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndLe_APEL", param);
	}

	/**
	 * リース満了・解約申請_物件明細更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndLeLineLld(ApEndLeLineLld obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndLeLineLld_APEL", param);
	}

	/**
	 * リース満了・解約申請_資産(機器)明細更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndLeLineAst(ApEndLeLineAst obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndLeLineAst_APEL", param);
	}

	/**
	 * リース満了・解約申請_ライセンス明細更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndLeLineLic(ApEndLeLineLic obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndLeLineLic_APEL", param);
	}

	/**
	 * リース満了・解約申請_添付明細更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateApEndLeLineAtt(ApEndLeLineAtt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApEndLeLineAtt_APEL", param);
	}

	/**
	 * リース満了・解約申請(ヘッダ)削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndLe(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndLe_APEL", param);
	}

	/**
	 * リース満了・解約申請_物件明細削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndLeLineLld(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndLeLineLld_APEL", param);
	}

	/**
	 * リース満了・解約申請_資産(機器)明細削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndLeLineAst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndLeLineAst_APEL", param);
	}

	/**
	 * リース満了・解約申請_ライセンス明細削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndLeLineLic(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndLeLineLic_APEL", param);
	}

	/**
	 * リース満了・解約申請_添付明細削除
	 * @param applicationId 除売却申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApEndLeLineAtt(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApEndLeLineAtt_APEL", param);
	}

	/**
	 * 除売却申請検索結果CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param lineOutputFlag false:申請書単位,true:物件明細単位
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createApEndLeListCsv(String loginStaffCode, String accessLevel, ApEndLeSC searchParam, Boolean lineOutputFlag) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApEndLeListParam(loginStaffCode, accessLevel, searchParam);

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し

		StringBuffer headerRow = new StringBuffer();
		List<String> outputPropList = new ArrayList<String>();
		List<Format> outputFormatList = new ArrayList<Format>();

		CsvWriterRowHandler handler = null;
		try {
			new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_LE, accessLevel, headerRow, outputPropList, outputFormatList);
			String headerStr = null;
			if(headerRow != null) {
				headerStr = headerRow.toString();
				if(lineOutputFlag != null && lineOutputFlag.booleanValue()){
					headerStr = headerStr.replace("[代表]", "[明細]");
				}
			}
			handler = new CsvWriterRowHandler(headerStr, (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));

			//	申請書単位
			if(lineOutputFlag != null && !lineOutputFlag.booleanValue()){
				sqlMap.queryWithRowHandler("selectApEndLeList_APEL", param, handler);
			//	物件明細単位
			}else{
				sqlMap.queryWithRowHandler("selectApEndLeLldList_APEL", param, handler);
			}

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 返却予定日の過ぎたリース満了・解約申請リストを取得
	 * @param returnDateTo 基準日
	 * @return リース満了・解約申請リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApEndLeSR> selectReturnApEndLeList(Date returnDateTo) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("returnDateTo", returnDateTo);

		return (List<ApEndLeSR>)sqlMap.queryForList("selectReturnApEndLeList_APEL", param);
	}

	/**
	 * ProPlus連携区分を更新する
	 * @param applicationIdPluralList 申請ID
	 * @return
	 * @throws SQLException
	 */
	public void  updateApEndLePpSendFlag(List<String> applicationIdPluralList) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationIdPluralList", Function.getInCondition("APPLICATION_ID", applicationIdPluralList, true));

		sqlMap.update("updateApEndLePpSendFlag_APEL", param);

	}

	/**
	 * リース満了・解約申請_ ProPlus連携「する」のリストを取得
	 * @param contractNum    契約番号
	 * @return リース満了・解約申請リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectPpSendFlagYes(String contractNum) throws SQLException {


		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("contractNum", contractNum);

		return (List<String>)sqlMap.queryForList("selectPpSendFlagYes_APEL", param);
	}
	/**
	 * リース満了・解約申請_ ProPlus連携「しない」のリストを取得
	 * @param contractNum    契約番号
	 * @return リース満了・解約申請リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectPpSendFlagNo(String contractNum) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("contractNum", contractNum);

		return (List<String>)sqlMap.queryForList("selectPpSendFlagNo_APEL", param);
	}

	/**
	 * リース満了・解約申請_ ProPlus連携「しない」のリストを取得
	 * @param contractNum    契約番号
	 * @return リース満了・解約申請リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<String> selectPpSendFlagNoList(String contractNum) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("contractNum", contractNum);

		return (List<String>)sqlMap.queryForList("selectPpSendFlagNoList_APEL", param);
	}

}