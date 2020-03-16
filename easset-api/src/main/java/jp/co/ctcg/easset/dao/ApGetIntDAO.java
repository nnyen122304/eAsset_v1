/*===================================================================
 * ファイル名 : ApGetIntDAO.java
 * 概要説明   : 取得申請(無形)用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
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
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineFld;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineDevSch;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineOtrCost;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineProfEst;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineAtt;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineCostSec;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntSC;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetInt;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntSR;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

public class ApGetIntDAO {

	/**
	 * 取得申請(無形)：ヘッダ取得
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 取得申請(無形)：ヘッダ
	 * @throws SQLException
	 */
	public ApGetInt selectApGetInt(String applicationId, String applicationVersion, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		if(Function.nvl(applicationVersion, "").equals("")){
			param.put("activeFlag", "1");
		}
		else{
			param.put("applicationVersion", applicationVersion);
		}
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApGetInt)sqlMap.queryForObject("selectApGetInt_APGI", param);
	}

	/**
	 * 取得申請(無形)：ヘッダ取得
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 取得申請(無形)：ヘッダ
	 * @throws SQLException
	 */
	public ApGetInt selectApGetInt(Long eappId, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eappId", eappId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApGetInt)sqlMap.queryForObject("selectApGetInt_APGI", param);
	}

	/**
	 * 取得申請(無形)：資産明細取得
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return 取得申請(無形)：資産明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetIntLineFld> selectApGetIntLineFld(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		return (List<ApGetIntLineFld>)sqlMap.queryForList("selectApGetIntLineFld_APGI", param);
	}

	/**
	 * 取得申請(無形)：開発スケジュール明細取得
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return 取得申請(無形)：開発スケジュール明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetIntLineDevSch> selectApGetIntLineDevSch(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		return (List<ApGetIntLineDevSch>)sqlMap.queryForList("selectApGetIntLineDevSch_APGI", param);
	}

	/**
	 * 取得申請(無形)：その他費用明細取得
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return 取得申請(無形)：その他費用明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetIntLineOtrCost> selectApGetIntLineOtrCost(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		return (List<ApGetIntLineOtrCost>)sqlMap.queryForList("selectApGetIntLineOtrCost_APGI", param);
	}

	/**
	 * 取得申請(無形)：利益予測明細取得
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return 取得申請(無形)：利益予測明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetIntLineProfEst> selectApGetIntLineProfEst(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		return (List<ApGetIntLineProfEst>)sqlMap.queryForList("selectApGetIntLineProfEst_APGI", param);
	}

	/**
	 * 取得申請(無形)：添付明細取得
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return 取得申請(無形)：添付明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetIntLineAtt> selectApGetIntLineAtt(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		return (List<ApGetIntLineAtt>)sqlMap.queryForList("selectApGetIntLineAtt_APGI", param);
	}

	/**
	 * 取得申請(無形)：経費負担明細取得
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return 取得申請(無形)：経費負担明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetIntLineCostSec> selectApGetIntLineCostSec(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		return (List<ApGetIntLineCostSec>)sqlMap.queryForList("selectApGetIntLineCostSec_APGI", param);
	}

	/**
	 * 取得申請(無形)：検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetIntSR> selectApGetIntList(String loginStaffCode, String accessLevel, ApGetIntSC searchParam) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApGetIntListParam(loginStaffCode, accessLevel, searchParam);

		// 改行置換文字
		param.put("enterChar", " ");

		return (List<ApGetIntSR>)sqlMap.queryForList("selectApGetIntList_APGI", param);
	}

	/**
	 * 取得申請(無形)：検索結果CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param lineOutputFlag false:申請書単位,true:明細単位
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createApGetIntListCsv(String loginStaffCode, String accessLevel, ApGetIntSC searchParam, Boolean lineOutputFlag) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApGetIntListParam(loginStaffCode, accessLevel, searchParam);

		MasterDAO masterDao = new MasterDAO();

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し

		StringBuffer headerRow = new StringBuffer();
		List<String> outputPropList = new ArrayList<String>();
		List<Format> outputFormatList = new ArrayList<Format>();
		List<String> propList = new ArrayList<String>();

		CsvWriterRowHandler handler = null;
		try {

			//	申請書単位の場合ヘッダのみ出力する。
			if(lineOutputFlag != null && !lineOutputFlag.booleanValue()){
				List<CodeName> lineList = masterDao.selectDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, null);
				if(lineList != null){
					for(int i = 0; i < lineList.size(); i++){
						CodeName lineItem = (CodeName)lineList.get(i);
						propList.add(lineItem.getValue3());
					}
				}
				new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, accessLevel, propList, headerRow,  outputPropList, outputFormatList);
			}else{
				new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, accessLevel, headerRow, outputPropList, outputFormatList);
			}

			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));
			//	申請書単位
			if(lineOutputFlag != null && !lineOutputFlag.booleanValue()){
				sqlMap.queryWithRowHandler("selectApGetIntList_APGI", param, handler);
			//	明細単位
			}else{
				sqlMap.queryWithRowHandler("selectApGetIntLineList_APGI", param, handler);
			}

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 取得申請(無形)：検索(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectApGetIntListParam(String loginStaffCode, String accessLevel, ApGetIntSC searchParam) throws SQLException {
		MasterDAO masterDao = new MasterDAO();

		Map<String, Object> param = new HashMap<String, Object>();

		// 改行置換文字
		param.put("enterChar", " ");

		param.put("searchParam", searchParam);

		Integer currentYear = masterDao.getCurrentYear(); // カレント年度取得

		//////////////////////////////// アクセスレベル
		// 一般権限、部署権限
		if(Function.isAccessLevelGeneral(accessLevel) || Function.isAccessLevelSection(accessLevel)) {
			// 部署リスト
			List<String> sectionCodeList = masterDao.selectAccessibleUpperSectionCodeList(searchParam.getHolCompanyCode(), loginStaffCode);

			if(Function.isAccessLevelSection(accessLevel)){
				// アクセス可能階層部署コード一覧取得
				sectionCodeList.addAll(masterDao.selectAccessibleSectionCodeList(searchParam.getHolCompanyCode(), loginStaffCode, searchParam.getIncludeSectionHierarchyFlag()));
			}

			// 検索対象部署が無い場合は全件出てしまう為、存在しないダミー部署コードを設定する。
			if(sectionCodeList == null){
				sectionCodeList = new ArrayList<String>();
			}
			if(sectionCodeList.size() == 0){
				sectionCodeList.add("X");
			}

			List<String> staffColumnList = new ArrayList<String>(); // 社員番号カラム
			staffColumnList.add("nagi.AP_STAFF_CODE");
			staffColumnList.add("nagi.AP_CREATE_STAFF_CODE");
			staffColumnList.add("nagi.HOL_STAFF_CODE");

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("nagi.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nagi.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nagi.HOL_SECTION_YEAR");

			param.put("accessLevelSection", Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCode(), loginStaffCode, sectionCodeList, currentYear));
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
			companyColumnList.add("nagi.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nagi.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nagi.HOL_SECTION_YEAR");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear));
		}

		//////////////////////////////// 複数指定検索
		// 取得申請書番号
		List<String> applicationIdPluralList = Function.getPluralList(searchParam.getApplicationIdPlural());
		if(applicationIdPluralList.size() > 0) {
			param.put("applicationIdPluralList", Function.getInCondition("nagi.APPLICATION_ID", applicationIdPluralList, true));
		}
		// ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("nagi.AP_STATUS", apStatusPluralList, true));
		}
		// 取得形態
		List<String> apGetTypePluralList = Function.getPluralList(searchParam.getApGetType());
		if(apGetTypePluralList.size() > 0) {
			param.put("apGetTypePluralList", Function.getInCondition("nagi.AP_GET_TYPE", apGetTypePluralList, true));
		}
		// e申請書類ID
		List<String> eappIdPluralList = Function.getPluralList(searchParam.getEappIdPlural());
		if(eappIdPluralList.size() > 0) {
			param.put("eappIdPluralList", Function.getInCondition("nagi.EAPP_ID", eappIdPluralList, false));
		}
		// サービス提供開始報告
		List<String> reportCompleteFlagPluralList = Function.getPluralList(searchParam.getReportCompleteFlag());
		if(reportCompleteFlagPluralList.size() > 0) {
			param.put("reportCompleteFlagPluralList", Function.getInCondition("nagi.REPORT_COMPLETE_FLAG", reportCompleteFlagPluralList, true));
		}
		// 計上区分
		List<String> addUpTypePluralList = Function.getPluralList(searchParam.getAddUpType());
		if(addUpTypePluralList.size() > 0) {
			StringBuffer addUpTypeColumn = new StringBuffer();
			addUpTypeColumn.append("(SELECT (CASE (SELECT max(LINE_SEQ)");
			addUpTypeColumn.append("                 FROM NEA_AP_GET_INT_LINE_FLD");
			addUpTypeColumn.append("                WHERE APPLICATION_ID = ni.APPLICATION_ID");
			addUpTypeColumn.append("                  AND APPLICATION_VERSION = ni.APPLICATION_VERSION)");
			addUpTypeColumn.append("         WHEN (SELECT count(*)");
			addUpTypeColumn.append("                 FROM NEA_AP_GET_INT_LINE_FLD");
			addUpTypeColumn.append("                WHERE APPLICATION_ID = ni.APPLICATION_ID");
			addUpTypeColumn.append("                  AND APPLICATION_VERSION = ni.APPLICATION_VERSION");
			addUpTypeColumn.append("                  AND AST_ADD_UP_TYPE = '1') THEN");
			addUpTypeColumn.append("            '1'");
			addUpTypeColumn.append("         WHEN (SELECT count(*)");
			addUpTypeColumn.append("                 FROM NEA_AP_GET_INT_LINE_FLD");
			addUpTypeColumn.append("                WHERE APPLICATION_ID = ni.APPLICATION_ID");
			addUpTypeColumn.append("                  AND APPLICATION_VERSION = ni.APPLICATION_VERSION");
			addUpTypeColumn.append("                  AND AST_ADD_UP_TYPE = '2') THEN");
			addUpTypeColumn.append("            '2'");
			addUpTypeColumn.append("         ELSE");
			addUpTypeColumn.append("            '3'");
			addUpTypeColumn.append("         END)");
			addUpTypeColumn.append("   FROM NEA_AP_GET_INT ni");
			addUpTypeColumn.append("  WHERE ni.APPLICATION_ID = nagi.APPLICATION_ID");
			addUpTypeColumn.append("    AND ni.APPLICATION_VERSION = nagi.APPLICATION_VERSION)");
			param.put("addUpTypePluralList", Function.getInCondition(addUpTypeColumn.toString(), addUpTypePluralList, true));
		}
		// リリース督促メール
		List<String> reminderFlagPluralList = Function.getPluralList(searchParam.getReminderFlag());
		if(reminderFlagPluralList.size() > 0){

			// String reminderFlagSql = "";
			String reminderOffFlag = "0";
			String reminderOnFlag = "0";
			for(int i = 0; i < reminderFlagPluralList.size(); i++) {
				String value = Function.nvl(reminderFlagPluralList.get(i).toString(), "");
				if(value.equals("0")){
					reminderOffFlag = "1";
				}
				else if(value.equals("1")){
					reminderOnFlag = "1";
				}
			}
			param.put("reminderOffFlag", reminderOffFlag);
			param.put("reminderOnFlag", reminderOnFlag);
		}
		//////////////////////////////// 複数あいまい検索
		// 備考
		List<String> descriptionPluralList = Function.getPluralList(searchParam.getDescription());
		if(descriptionPluralList.size() > 0) {
			param.put("descriptionPluralListAp", Function.getFuzzyOrCondition("nagi.DESCRIPTION_AP", descriptionPluralList));
			param.put("descriptionPluralListAdmin", Function.getFuzzyOrCondition("nagi.DESCRIPTION_ADMIN", descriptionPluralList));
		}
		return param;
	}

	/**
	 * 取得申請(無形)：作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetInt(ApGetInt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetInt_APGI", param);
	}

	/**
	 * 取得申請(無形)：資産明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetIntLineFld(ApGetIntLineFld obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetIntLineFld_APGI", param);
	}

	/**
	 * 取得申請(無形)：開発スケジュール明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetIntLineDevSch(ApGetIntLineDevSch obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetIntLineDevSch_APGI", param);
	}

	/**
	 * 取得申請(無形)：その他費用明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetIntLineOtrCost(ApGetIntLineOtrCost obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetIntLineOtrCost_APGI", param);
	}

	/**
	 * 取得申請(無形)：利益予測明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetIntLineProfEst(ApGetIntLineProfEst obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetIntLineProfEst_APGI", param);
	}

	/**
	 * 取得申請(無形)：添付明細作成
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetIntLineAtt(ApGetIntLineAtt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetIntLineAtt_APGI", param);
	}

	/**
	 * 取得申請(無形)：経費負担部署明細作成
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetIntLineCostSec(ApGetIntLineCostSec obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetIntLineCostSec_APGI", param);
	}

	/**
	 * 取得申請(無形)：更新
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void updateApGetInt(ApGetInt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApGetInt_APGI", param);
	}

	/**
	 * 取得申請(無形)：削除
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetInt(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		sqlMap.delete("deleteApGetInt_APGI", param);
	}

	/**
	 * 取得申請(無形)：資産明細削除
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetIntLineFld(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		sqlMap.delete("deleteApGetIntLineFld_APGI", param);
	}

	/**
	 * 取得申請(無形)：開発スケジュール明細削除
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetIntLineDevSch(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		sqlMap.delete("deleteApGetIntLineDevSch_APGI", param);
	}

	/**
	 * 取得申請(無形)：その他費用明細削除
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetIntLineOtrCost(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		sqlMap.delete("deleteApGetIntLineOtrCost_APGI", param);
	}

	/**
	 * 取得申請(無形)：利益予測明細削除
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetIntLineProfEst(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		sqlMap.delete("deleteApGetIntLineProfEst_APGI", param);
	}

	/**
	 * 取得申請(無形)：添付明細削除
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetIntLineAtt(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		sqlMap.delete("deleteApGetIntLineAtt_APGI", param);
	}

	/**
	 * 取得申請(無形)：経費負担部署明細削除
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetIntLineCostSec(String applicationId, String applicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("applicationVersion", applicationVersion);

		sqlMap.delete("deleteApGetIntLineCostSec_APGI", param);
	}

	/**
	 * 前回バージョンからの変更項目取得
	 * @param applicationId 取得申請書場の具
	 * @param oldApplicationVersion 旧バージョン
	 * @param newApplicationVersion 新バージョン
	 * @return
	 * @throws SQLException
	 */
	public String selectVerUpColumnName(String entityName, String applicationId, String oldApplicationVersion, String newApplicationVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("entityName", "NEA_" + entityName);
		param.put("applicationId", applicationId);
		param.put("oldApplicationVersion", oldApplicationVersion);
		param.put("newApplicationVersion", newApplicationVersion);
		param.put("itemDefName", Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT);

		return (String) sqlMap.queryForObject("selectVerUpColumnName_APGI", param);
	}
}
