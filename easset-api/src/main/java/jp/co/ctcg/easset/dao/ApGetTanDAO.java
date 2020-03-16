/*===================================================================
 * ファイル名 : ApGetTanDAO.java
 * 概要説明   : 取得申請用DAO定義
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineAst;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineAtt;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineCostSec;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineLic;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineOtrCost;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLinePur;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanSC;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanSR;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;


public class ApGetTanDAO {

	/**
	 * 取得申請取得(ヘッダ)
	 * @param applicationId 取得申請書番号
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 取得申請(ヘッダ)
	 * @throws SQLException
	 */
	public ApGetTan selectApGetTan(String applicationId, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApGetTan)sqlMap.queryForObject("selectApGetTan_APGT", param);
	}

	/**
	 * 取得申請取得(ヘッダ)
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 取得申請(ヘッダ)
	 * @throws SQLException
	 */
	public ApGetTan selectApGetTan(Long eappId, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("eappId", eappId);
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		return (ApGetTan)sqlMap.queryForObject("selectApGetTan_APGT", param);
	}

	/**
	 * 取得申請購入明細取得
	 * @param applicationId 取得申請書番号
	 * @return 取得申請購入明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetTanLinePur> selectApGetTanLinePur(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApGetTanLinePur>)sqlMap.queryForList("selectApGetTanLinePur_APGT", param);
	}

	/**
	 * 取得申請その他費用明細取得
	 * @param applicationId 取得申請書番号
	 * @return 取得申請その他費用明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetTanLineOtrCost> selectApGetTanLineOtrCost(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApGetTanLineOtrCost>)sqlMap.queryForList("selectApGetTanLineOtrCost_APGT", param);
	}

	/**
	 * 取得申請添付明細取得
	 * @param applicationId 取得申請書番号
	 * @return 取得申請添付明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetTanLineAtt> selectApGetTanLineAtt(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApGetTanLineAtt>)sqlMap.queryForList("selectApGetTanLineAtt_APGT", param);
	}

	/**
	 * 取得申請経費負担明細取得
	 * @param applicationId 取得申請書番号
	 * @return 取得申請経費負担明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetTanLineCostSec> selectApGetTanLineCostSec(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApGetTanLineCostSec>)sqlMap.queryForList("selectApGetTanLineCostSec_APGT", param);
	}

	/**
	 * 取得申請資産(機器)明細取得
	 * @param applicationId 取得申請書番号
	 * @return 取得申請資産(機器)明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetTanLineAst> selectApGetTanLineAst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApGetTanLineAst>)sqlMap.queryForList("selectApGetTanLineAst_APGT", param);
	}

	/**
	 * 取得申請ライセンス明細取得
	 * @param applicationId 取得申請書番号
	 * @return 取得申請ライセンス明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetTanLineLic> selectApGetTanLineLic(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (List<ApGetTanLineLic>)sqlMap.queryForList("selectApGetTanLineLic_APGT", param);
	}

	/**
	 * 取得申請検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApGetTanSR> selectApGetTanList(String loginStaffCode, String accessLevel, ApGetTanSC searchParam) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApGetTanListParam(loginStaffCode, accessLevel, searchParam);

		// 改行置換文字
		param.put("enterChar", " ");

		return (List<ApGetTanSR>)sqlMap.queryForList("selectApGetTanList_APGT", param);
	}

	/**
	 * 取得申請検索結果CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param lineOutputFlag false:申請書単位,true:明細単位
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createApGetTanListCsv(String loginStaffCode, String accessLevel, ApGetTanSC searchParam, Boolean lineOutputFlag) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectApGetTanListParam(loginStaffCode, accessLevel, searchParam);

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
				List<CodeName> lineList = masterDao.selectDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, null);
				if(lineList != null){
					for(int i = 0; i < lineList.size(); i++){
						CodeName lineItem = (CodeName)lineList.get(i);
						propList.add(lineItem.getValue3());
					}
				}
				new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, accessLevel, propList, headerRow,  outputPropList, outputFormatList);
			}else{
				new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, accessLevel, headerRow, outputPropList, outputFormatList);
			}

			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));
			//	申請書単位
			if(lineOutputFlag != null && !lineOutputFlag.booleanValue()){
				sqlMap.queryWithRowHandler("selectApGetTanList_APGT", param, handler);
			//	明細単位
			}else{
				sqlMap.queryWithRowHandler("selectApGetTanLineList_APGT", param, handler);
			}

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 取得申請検索(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectApGetTanListParam(String loginStaffCode, String accessLevel, ApGetTanSC searchParam) throws SQLException {
		MasterDAO masterDao = new MasterDAO();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("searchParam", searchParam);

		Integer currentYear = masterDao.getCurrentYear(); // カレント年度取得

		//////////////////////////////// アクセスレベル
		if(Function.isAccessLevelGeneral(accessLevel)) { // 一般権限
			searchParam.setApStaffCode(loginStaffCode);
		} else if(Function.isAccessLevelSection(accessLevel)) { // 部署権限
			// アクセス可能階層部署コード一覧取得
			List<String> sectionCodeList = masterDao.selectAccessibleSectionCodeList(searchParam.getHolCompanyCode(), loginStaffCode, searchParam.getIncludeSectionHierarchyFlag());

			List<String> staffColumnList = new ArrayList<String>(); // 社員番号カラム
			staffColumnList.add("nagt.AP_STAFF_CODE");
			staffColumnList.add("nagt.AP_CREATE_STAFF_CODE");
			staffColumnList.add("nagt.AP_REGIST_STAFF_CODE");

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("nagt.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nagt.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nagt.HOL_SECTION_YEAR");

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
			companyColumnList.add("nagt.HOL_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nagt.HOL_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nagt.HOL_SECTION_YEAR");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear));
		}


		//////////////////////////////// 複数指定検索
		// 取得申請書番号
		List<String> applicationIdPluralList = Function.getPluralList(searchParam.getApplicationIdPlural());
		if(applicationIdPluralList.size() > 0) {
			param.put("applicationIdPluralList", Function.getInCondition("nagt.APPLICATION_ID", applicationIdPluralList, true));
		}
		// ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("nagt.AP_STATUS", apStatusPluralList, true));
		}
		// 取得形態
		List<String> apGetTypePluralList = Function.getPluralList(searchParam.getApGetType());
		if(apGetTypePluralList.size() > 0) {
			param.put("apGetTypePluralList", Function.getInCondition("nagt.AP_GET_TYPE", apGetTypePluralList, true));
		}
		// 故障交換対象情報機器管理番号
		List<String> failureAssetIdPluralList = Function.getPluralList(searchParam.getFailureAssetIdPlural());
		if(failureAssetIdPluralList.size() > 0) {
			param.put("failureAssetIdPluralList", Function.getInCondition("la.FAILURE_ASSET_ID", failureAssetIdPluralList, true));
		}
		// e申請書類ID
		List<String> eappIdPluralList = Function.getPluralList(searchParam.getEappIdPlural());
		if(eappIdPluralList.size() > 0) {
			param.put("eappIdPluralList", Function.getInCondition("nagt.EAPP_ID", eappIdPluralList, false));
		}
		// 購入先見積番号
		List<String> getPurEstimateNumberPluralList = Function.getPluralList(searchParam.getGetPurEstimateNumberPlural());
		if(getPurEstimateNumberPluralList.size() > 0) {
			param.put("getPurEstimateNumberPluralList", Function.getInCondition("nagtlp.GET_PUR_ESTIMATE_NUMBER", getPurEstimateNumberPluralList, true));
		}
		// リース・レンタル先見積番号
		List<String> getLeReEstimateNumberPluralList = Function.getPluralList(searchParam.getGetLeReEstimateNumberPlural());
		if(getLeReEstimateNumberPluralList.size() > 0) {
			param.put("getLeReEstimateNumberPluralList", Function.getInCondition("nagt.GET_LE_RE_ESTIMATE_NUMBER", getLeReEstimateNumberPluralList, true));
		}
		//////////////////////////////// 複数あいまい検索
		// 備考
		List<String> descriptionPluralList = Function.getPluralList(searchParam.getDescription());
		if(descriptionPluralList.size() > 0) {
			param.put("descriptionPluralListAp", Function.getFuzzyOrCondition("nagt.DESCRIPTION_AP", descriptionPluralList));
			param.put("descriptionPluralListAdmin", Function.getFuzzyOrCondition("nagt.DESCRIPTION_ADMIN", descriptionPluralList));
		}
		// eAssetレンタル発注
		List<String> reoOrderFlagPluralList = Function.getPluralList(searchParam.getReoOrderFlag());
		if(reoOrderFlagPluralList.size() > 0) {
			param.put("reoOrderFlagPluralList", Function.getInCondition("nagt.REO_ORDER_FLAG", reoOrderFlagPluralList, true));
		}
		//	発注抑止
		List<String> reoDisuseFlagPluralList = Function.getPluralList(searchParam.getReoDisuseFlag());
		if(reoDisuseFlagPluralList.size() > 0){
			param.put("reoDisuseFlagPluralList", Function.getInCondition("nagt.REO_DISUSE_FLAG", reoDisuseFlagPluralList, true));
		}

		return param;
	}

	/**
	 * 取得申請作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetTan(ApGetTan obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetTan_APGT", param);
	}

	/**
	 * 取得申請購入明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetTanLinePur(ApGetTanLinePur obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetTanLinePur_APGT", param);
	}

	/**
	 * 取得申請その他費用明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetTanLineOtrCost(ApGetTanLineOtrCost obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetTanLineOtrCost_APGT", param);
	}

	/**
	 * 取得申請添付明細作成
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetTanLineAtt(ApGetTanLineAtt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetTanLineAtt_APGT", param);
	}

	/**
	 * 取得申請経費負担部署明細作成
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetTanLineCostSec(ApGetTanLineCostSec obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetTanLineCostSec_APGT", param);
	}

	/**
	 * 取得申請資産(機器)明細作成
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetTanLineAst(ApGetTanLineAst obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetTanLineAst_APGT", param);
	}

	/**
	 * ライセンス明細作成
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void insertApGetTanLineLic(ApGetTanLineLic obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApGetTanLineLic_APGT", param);
	}

	/**
	 * 取得申請更新
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void updateApGetTan(ApGetTan obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateApGetTan_APGT", param);
	}

	/**
	 * 取得申請削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetTan(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApGetTan_APGT", param);
	}

	/**
	 * 取得申請購入明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetTanLinePur(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApGetTanLinePur_APGT", param);
	}

	/**
	 * 取得申請その他費用明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetTanLineOtrCost(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApGetTanLineOtrCost_APGT", param);
	}

	/**
	 * 取得申請添付明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetTanLineAtt(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApGetTanLineAtt_APGT", param);
	}

	/**
	 * 取得申請経費負担部署明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetTanLineCostSec(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApGetTanLineCostSec_APGT", param);
	}

	/**
	 * 取得申請資産(機器)明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetTanLineAst(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApGetTanLineAst_APGT", param);
	}

	/**
	 * 取得申請ライセンス明細削除
	 * @param applicationId 取得申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApGetTanLineLic(String applicationId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		sqlMap.delete("deleteApGetTanLineLic_APGT", param);
	}

	/**
	 * 入力可能マスタ値CSV作成
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createPossibleInputMasterList() throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		String headerRow = "項目,入力可能値,備考";
		String[] outputProps = new String[]{"categoryCode", "value1", "description"};
		Format[] outputFormats = new Format[]{null, null, null};

		CsvWriterRowHandler handler = null;
		try {
			handler = new CsvWriterRowHandler(headerRow, outputProps, outputFormats);
			sqlMap.queryWithRowHandler("selectPossibleInputMasterList_APGT", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

 	/**
 	 * 取得申請資産(機器)明細登録数の更新（+ OR -）
	 * @param loginStaffCode ログイン社員番号
 	 * @param apGetTanLineAstId 取得申請資産(機器)明細識別ID
 	 * @param addRegistQuantity 登録追加数量。マイナス値の場合は減少数量。
 	 * @throws SQLException
 	 */
	public void updateApGetTanLineAstRegist(String loginStaffCode, Long apGetTanLineAstId, Integer addRegistQuantity) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginStaffCode", loginStaffCode);
		param.put("apGetTanLineAstId", apGetTanLineAstId);
		param.put("addRegistQuantity", addRegistQuantity);
		param.put("sysdate", new Date());

		sqlMap.delete("updateApGetTanLineAstRegist_APGT", param);
	}

 	/**
 	 * 取得申請ライセンス明細登録数の更新（+ OR -）
	 * @param loginStaffCode ログイン社員番号
 	 * @param apGetTanLineLicId 取得申請ライセンス明細識別ID
 	 * @param addRegistQuantity 登録追加数量。マイナス値の場合は減少数量。
 	 * @throws SQLException
 	 */
	public void updateApGetTanLineLicRegist(String loginStaffCode, Long apGetTanLineLicId, Integer addRegistQuantity) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("loginStaffCode", loginStaffCode);
		param.put("apGetTanLineLicId", apGetTanLineLicId);
		param.put("addRegistQuantity", addRegistQuantity);
		param.put("sysdate", new Date());

		sqlMap.delete("updateApGetTanLineLicRegist_APGT", param);
	}

 	/**
 	 * 取得申請ライセンス明細登録数の更新（+ OR -）
	 * @param なし
 	 * @throws SQLException
 	 */
	public Long selectMaxReoReceptNumber() throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("date", new Date());

		return (Long)sqlMap.queryForObject("selectMaxReoReceptNumber_APGT", param);
	}

}
