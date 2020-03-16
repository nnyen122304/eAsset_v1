/*===================================================================
 * ファイル名 : ApBgnIntDAO.java
 * 概要説明   : サービス提供開始報告用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-10-22 1.0  申           新規
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
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntLineFld;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntLineProfEst;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntLineAtt;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntLineCostSec;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntSC;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnInt;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntSR;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

public class ApBgnIntDAO {

	/**
	 * サービス提供開始報告：ヘッダ取得
	 * @param applicationId 取得申請書番号
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return サービス提供開始報告：ヘッダ
	 * @throws SQLException
	 */
	public ApBgnInt selectApBgnInt(String applicationId, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApBgnInt)sqlMap.queryForObject("selectApBgnInt_APGI", param);
	}

	/**
	 * サービス提供開始報告：ヘッダ取得
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return サービス提供開始報告：ヘッダ
	 * @throws SQLException
	 */
	public ApBgnInt selectApBgnInt(Long eappId, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eappId", eappId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApBgnInt)sqlMap.queryForObject("selectApBgnInt_APGI", param);
	}

	/**
	 * 取得申請書(無形)からサービス提供開始報告データ取得
	 * @param apGetIntId 取得申請書番号
	 * @param apGetIntVersion 取得申請書バージョン
	 * @return サービス提供開始報告データ
	 * @return
	 */
	public ApBgnInt selectApBgnIntByApGetInt(String apGetIntId, String apGetIntVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apGetIntId", apGetIntId);
		param.put("apGetIntVersion", apGetIntVersion);

		return (ApBgnInt)sqlMap.queryForObject("selectApBgnIntByApGetInt_AST", param);
	}

	/**
	 * サービス提供開始報告：資産明細取得
	 * @param applicationId 取得申請書番号
	 * @return サービス提供開始報告：資産明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApBgnIntLineFld> selectApBgnIntLineFld(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApBgnIntLineFld>)sqlMap.queryForList("selectApBgnIntLineFld_APGI", param);
	}

	/**
	 * サービス提供開始報告：利益予測明細取得
	 * @param applicationId 取得申請書番号
	 * @return サービス提供開始報告：利益予測明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApBgnIntLineProfEst> selectApBgnIntLineProfEst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApBgnIntLineProfEst>)sqlMap.queryForList("selectApBgnIntLineProfEst_APGI", param);
	}

	/**
	 * 取得申請書(無形)から利益予測明細取得
	 * @param apGetIntId 取得申請書番号
	 * @param apGetIntVersion 取得申請書バージョン
	 * @return サービス提供開始報告：利益予測明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApBgnIntLineProfEst> selectApBgnIntLineProfEstByApGetInt(String apGetIntId, String apGetIntVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apGetIntId", apGetIntId);
		param.put("apGetIntVersion", apGetIntVersion);

		return (List<ApBgnIntLineProfEst>)sqlMap.queryForList("selectApBgnIntLineProfEstByApGetInt_APGI", param);
	}

	/**
	 * サービス提供開始報告：添付明細取得
	 * @param applicationId 取得申請書番号
	 * @return サービス提供開始報告：添付明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApBgnIntLineAtt> selectApBgnIntLineAtt(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApBgnIntLineAtt>)sqlMap.queryForList("selectApBgnIntLineAtt_APGI", param);
	}

	/**
	 * 取得申請書(無形)から添付明細取得
	 * @param apGetIntId 取得申請書番号
	 * @param apGetIntVersion 取得申請書バージョン
	 * @return サービス提供開始報告：添付明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApBgnIntLineAtt> selectApBgnIntLineAttByApGetInt(String apGetIntId, String apGetIntVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apGetIntId", apGetIntId);
		param.put("apGetIntVersion", apGetIntVersion);

		return (List<ApBgnIntLineAtt>)sqlMap.queryForList("selectApBgnIntLineAttByApGetInt_APGI", param);
	}

	/**
	 * サービス提供開始報告：経費負担明細取得
	 * @param applicationId 取得申請書番号
	 * @return サービス提供開始報告：経費負担明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApBgnIntLineCostSec> selectApBgnIntLineCostSec(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApBgnIntLineCostSec>)sqlMap.queryForList("selectApBgnIntLineCostSec_APGI", param);
	}

	/**
	 * 取得申請書(無形)から経費負担明細取得
	 * @param apGetIntId 取得申請書番号
	 * @param apGetIntVersion 取得申請書バージョン
	 * @return サービス提供開始報告：経費負担明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApBgnIntLineCostSec> selectApBgnIntLineCostSecByApGetInt(String apGetIntId, String apGetIntVersion) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apGetIntId", apGetIntId);
		param.put("apGetIntVersion", apGetIntVersion);

		return (List<ApBgnIntLineCostSec>)sqlMap.queryForList("selectApBgnIntLineCostSecByApGetInt_APGI", param);
	}

	/**
	 * サービス提供開始報告：検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApBgnIntSR> selectApBgnIntList(String loginStaffCode, String accessLevel, ApBgnIntSC searchParam) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApBgnIntListParam(loginStaffCode, accessLevel, searchParam);

		// 改行置換文字
		param.put("enterChar", " ");

		return (List<ApBgnIntSR>)sqlMap.queryForList("selectApBgnIntList_APGI", param);
	}

	/**
	 * サービス提供開始報告：検索結果CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createApBgnIntListCsv(String loginStaffCode, String accessLevel, ApBgnIntSC searchParam) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApBgnIntListParam(loginStaffCode, accessLevel, searchParam);

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

			List<CodeName> lineList = masterDao.selectDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_AP_BGN_INT, null);
			if(lineList != null){
				for(int i = 0; i < lineList.size(); i++){
					CodeName lineItem = (CodeName)lineList.get(i);
					propList.add(lineItem.getValue3());
				}
			}
			new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_BGN_INT, accessLevel, propList, headerRow,  outputPropList, outputFormatList);

			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));
			sqlMap.queryWithRowHandler("selectApBgnIntList_APGI", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * サービス提供開始報告：検索(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectApBgnIntListParam(String loginStaffCode, String accessLevel, ApBgnIntSC searchParam) throws SQLException {
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
			staffColumnList.add("nabi.AP_STAFF_CODE");
			staffColumnList.add("nabi.AP_CREATE_STAFF_CODE");
			staffColumnList.add("nabi.HOL_STAFF_CODE");

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("nabi.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nabi.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nabi.HOL_SECTION_YEAR");

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
			companyColumnList.add("nabi.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nabi.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nabi.HOL_SECTION_YEAR");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear));
		}

		//////////////////////////////// 複数指定検索
		// 取得申請書番号
		List<String> applicationIdPluralList = Function.getPluralList(searchParam.getApplicationIdPlural());
		if(applicationIdPluralList.size() > 0) {
			param.put("applicationIdPluralList", Function.getInCondition("nabi.APPLICATION_ID", applicationIdPluralList, true));
		}
		// ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("nabi.AP_STATUS", apStatusPluralList, true));
		}
		// 取得形態
		List<String> apGetTypePluralList = Function.getPluralList(searchParam.getApGetType());
		if(apGetTypePluralList.size() > 0) {
			param.put("apGetTypePluralList", Function.getInCondition("nabi.AP_GET_TYPE", apGetTypePluralList, true));
		}
		// e申請書類ID
		List<String> eappIdPluralList = Function.getPluralList(searchParam.getEappIdPlural());
		if(eappIdPluralList.size() > 0) {
			param.put("eappIdPluralList", Function.getInCondition("nabi.EAPP_ID", eappIdPluralList, false));
		}
		// 資産明細:資産番号
		List<String> astNumFldPluralList = Function.getPluralList(searchParam.getAstNumFldPlural());
		if(astNumFldPluralList.size() > 0) {
			param.put("astNumFldPluralList", Function.getInCondition("lf.AST_NUM", astNumFldPluralList, true));
		}
		return param;
	}

	/**
	 * サービス提供開始報告：作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApBgnInt(ApBgnInt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApBgnInt_APGI", param);
	}

	/**
	 * サービス提供開始報告：資産明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApBgnIntLineFld(ApBgnIntLineFld obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApBgnIntLineFld_APGI", param);
	}

	/**
	 * サービス提供開始報告：利益予測明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApBgnIntLineProfEst(ApBgnIntLineProfEst obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApBgnIntLineProfEst_APGI", param);
	}

	/**
	 * サービス提供開始報告：添付明細作成
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void insertApBgnIntLineAtt(ApBgnIntLineAtt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApBgnIntLineAtt_APGI", param);
	}

	/**
	 * サービス提供開始報告：経費負担部署明細作成
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void insertApBgnIntLineCostSec(ApBgnIntLineCostSec obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApBgnIntLineCostSec_APGI", param);
	}

	/**
	 * サービス提供開始報告：更新
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void updateApBgnInt(ApBgnInt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApBgnInt_APGI", param);
	}

	/**
	 * サービス提供開始報告：削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApBgnInt(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApBgnInt_APGI", param);
	}

	/**
	 * サービス提供開始報告：資産明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApBgnIntLineFld(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApBgnIntLineFld_APGI", param);
	}

	/**
	 * サービス提供開始報告：利益予測明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApBgnIntLineProfEst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApBgnIntLineProfEst_APGI", param);
	}

	/**
	 * サービス提供開始報告：添付明細削除
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return
	 * @throws SQLException
	 */
	public void deleteApBgnIntLineAtt(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApBgnIntLineAtt_APGI", param);
	}

	/**
	 * サービス提供開始報告：経費負担部署明細削除
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return
	 * @throws SQLException
	 */
	public void deleteApBgnIntLineCostSec(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApBgnIntLineCostSec_APGI", param);
	}
}
