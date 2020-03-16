/*===================================================================
 * ファイル名 : CostScrDAO.java
 * 概要説明   : 経費負担部課精査用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2015-01-28 1.0  李            新規
 *=================================================================== */

package jp.co.ctcg.easset.dao;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.co.ctcg.easset.dto.costScr.CostScr;
import jp.co.ctcg.easset.dto.costScr.CostScrLine;
import jp.co.ctcg.easset.dto.costScr.CostScrStat;
import jp.co.ctcg.easset.dto.costScr.CostScrSC;
import jp.co.ctcg.easset.dto.costScr.CostScrSR;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

import com.ibatis.sqlmap.client.SqlMapClient;


public class CostScrDAO {

	/**
	 * 経費負担部課精査状況情報検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CostScrStat> selectCostScrStatList(String loginStaffCode, String accessLevel, String companyCode, boolean lockFlag)  throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("lockFlag", lockFlag);

		return (List<CostScrStat>)sqlMap.queryForList("selectCostScrStat_SCR", param);
	}

	/**
	 * 経費負担部課精査状況情報検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @return
	 * @throws SQLException
	 */
	public CostScrStat selectCostScrStat(String loginStaffCode, String accessLevel, String companyCode, String period, boolean lockFlag)  throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		param.put("period", period);
		param.put("lockFlag", lockFlag);

		return (CostScrStat)sqlMap.queryForObject("selectCostScrStat_SCR", param);
	}

	/**
	 * 経費負担部課集約情報検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CostScrSR> selectCostScrList(String loginStaffCode, String accessLevel, String companyCode, CostScrSC searchParam)  throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param = getSelectCostScrListParam(loginStaffCode, accessLevel, companyCode, searchParam);

		return (List<CostScrSR>)sqlMap.queryForList("selectCostScr_SCR", param);
	}

	/**
	 * 経費負担部課精査状況情報(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectCostScrListParam(String loginStaffCode, String accessLevel, String companyCode, CostScrSC searchParam)  throws SQLException {
		MasterDAO masterDao = new MasterDAO();
		Map<String, Object> param = new HashMap<String, Object>();
		Integer currentYear = masterDao.getCurrentYear();						// カレント年度取得

		//	一般？部署権限？
		if(Function.isAccessLevelGeneral(accessLevel)
		|| Function.isAccessLevelSection(accessLevel)){
			param.put("scrAccessLevel", "1"); // データ公開制御
		}


		//////////////////////////////// 部署検索
		if(!Function.nvl(searchParam.getScrSectionCode(), "").equals("")) {
			List<String> sectionCodeList = new ArrayList<String>();

			if(Function.nvl(searchParam.getIncludeSectionHierarchyFlag(), "0").equals("1")) { // 階層指定有
				sectionCodeList.addAll(masterDao.selectHierarchySectionCodeList(companyCode, searchParam.getScrSectionCode(), currentYear));
			} else { // 階層指定無し
				sectionCodeList.add(searchParam.getScrSectionCode());
			}

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("ncs.COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("ncs.SCR_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("ncs.SCR_SECTION_YEAR");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, null, sectionCodeList, currentYear));
		}

		param.put("searchParam", searchParam);
		param.put("companyCode", companyCode);

		// 精査種別
		List<String> scrTypeList = Function.getPluralList(searchParam.getScrType());
		if(scrTypeList.size() > 0) {
			param.put("scrTypeList", Function.getInCondition("ncs.SCR_TYPE", scrTypeList, true));
		}

		//	精査実施状況
		List<String> scrImplementationPluralList = Function.getPluralList(searchParam.getScrImplementation());
		if(scrImplementationPluralList.size() > 0){
			StringBuffer scrImplementationColumn = new StringBuffer();
			scrImplementationColumn.append("(");
			scrImplementationColumn.append("  CASE  ");
			scrImplementationColumn.append("   WHEN costScrCountUndecided = costScrCountUndecided + costScrCountOK + costScrCountNG THEN '1' ");	//	未実施
			scrImplementationColumn.append("   WHEN costScrCountUndecided > 0 THEN '2' ");	//	実施中
			scrImplementationColumn.append("   WHEN costScrCountUndecided = 0 AND compFlag = '0' THEN '3' ");	//	実施済
			scrImplementationColumn.append("   WHEN compFlag = '1' THEN '4' ");	//	精査完了
			scrImplementationColumn.append("   ELSE '0' END ");	//	未実施
			scrImplementationColumn.append(")");
			param.put("scrImplementationPluralList", Function.getInCondition(scrImplementationColumn.toString(), scrImplementationPluralList, true));
		}

			// 移動申請状況
			List<String> apChangeStatusPluralList = Function.getPluralList(searchParam.getApChangeStatus());
			if(apChangeStatusPluralList.size() > 0){
				StringBuffer apStatusColumn = new StringBuffer();
				apStatusColumn.append("(");
				apStatusColumn.append("  CASE  ");
				apStatusColumn.append("   WHEN (apChangeCountUnApply + apChangeCountApply + apChangeCountApprove) != 0 AND (apChangeCountUnApply + apChangeCountApply + apChangeCountApprove) != apChangeCountApprove THEN '1' ");	//	申請残有
				apStatusColumn.append("   WHEN ( costScrCountUndecided != (costScrCountUndecided + costScrCountOK + costScrCountNG) AND (apChangeCountUnApply + apChangeCountApply + apChangeCountApprove) = apChangeCountApprove) THEN '2' ");	//	承認済、申請不要
				apStatusColumn.append("   ELSE '0' END ");
				apStatusColumn.append(")");
				param.put("apStatusPluralList", Function.getInCondition(apStatusColumn.toString(), apChangeStatusPluralList, true));
			}

		return param;
	}

	/**
	 * 経費負担部課精査実施状況更新
	 * @param obj ログイン社員番号
	 * @return
	 * @throws SQLException
	 */
	public void updateCostScrStat(CostScrStat obj) throws SQLException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateCostScrStat_SCR", param);
	}

	/**
	 * 経費負担部課精査実施状況更新
	 * @param obj ログイン社員番号
	 * @return
	 * @throws SQLException
	 */
	public void updateCostScr(CostScr obj, boolean isUpdateScrSection) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		if(isUpdateScrSection){
			param.put("updateScrSectionOnly", "1");
		}
		else{
			param.put("updateScrSectionOnly", null);
		}

		sqlMap.update("updateCostScr_SCR", param);
	}

	/**
	 * 経費負担部課精査実施状況履歴作成
	 * @param obj
	 * @return
	 * @throws SQLException
	 */
	public void insertCostScrHistory(CostScr obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertCostScr_SCR_HISTORY", param);
	}

	/**
	 * 経費負担部課明細検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<CostScrLine> selectCostScrLine(String loginStaffCode, String accessLevel, String companyCode, CostScrSR searchParam) throws SQLException {
		Map<String, Object> param = new HashMap<String, Object>();
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		param = getSelectCostScrLineListParam(loginStaffCode, accessLevel, companyCode, searchParam);
		String sql = "";
		sql = getCostScrLineSqlName(searchParam.getScrType());

		return (List<CostScrLine>)sqlMap.queryForList(sql, param);
	}
	/**
	 * 経費負担部課精査明細(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	private Map<String, Object> getSelectCostScrLineListParam(String loginStaffCode, String accessLevel, String companyCode, CostScrSR searchParam)  throws SQLException {
		// MasterDAO masterDao = new MasterDAO();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("companyCode", companyCode);
		param.put("searchParam", searchParam);
		return param;
	}

	/**
	 * 経費負担部課集約情報CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam true:検索条件
	 * @return
	 * @throws SQLException
	 */
	public CsvWriterRowHandler createCostScrCsv(String loginStaffCode, String accessLevel, String companyCode, CostScrSC searchParam) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param = getSelectCostScrListParam(loginStaffCode, accessLevel, companyCode, searchParam);
		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		StringBuffer headerRow = new StringBuffer();
		List<String> outputPropList = new ArrayList<String>();
		List<Format> outputFormatList = new ArrayList<Format>();

		CsvWriterRowHandler handler = null;
		try {
			new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_COST_SCR, accessLevel, headerRow, outputPropList, outputFormatList);
			headerRow.insert(0, "会計年月：" + searchParam.getPeriod().substring(0, 4) + "-" + searchParam.getPeriod().substring(4, 6) + "\n");
			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));

			sqlMap.queryWithRowHandler("selectCostScr_SCR", param, handler);
			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 経費負担部課精査明細更新
	 * @param obj 経費負担部課精査明細(1件のみ)
	 * @return
	 * @throws SQLException
	 */
	public void updateCostScrLine(CostScrLine obj) throws SQLException, UnsupportedEncodingException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateCostScrLine_SCR", param);
	}

	/**
	 * 経費負担部課精査明細エクスポートCSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam 検索条件
	 * @return
	 * @throws SQLException
	 */
	public CsvWriterRowHandler createCostScrLineCsv(String loginStaffCode, String accessLevel, String companyCode, CostScrSR searchParam) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		Map<String, Object> param = new HashMap<String, Object>();
		param = getSelectCostScrLineListParam(loginStaffCode, accessLevel, companyCode, searchParam);

		return createCostScrLineCsvHandler(loginStaffCode, accessLevel, searchParam.getPeriod(), searchParam.getScrType(), param);

	}

	/**
	 * 明細ダウンロードCSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param list 一覧
	 * @return
	 * @throws SQLException
	 */
	public CsvWriterRowHandler createCostScrLineListCsv(String loginStaffCode, String accessLevel, List<CostScrSR> list) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		Map<String, Object> param = new HashMap<String, Object>();

		// 改行置換文字
		param.put("enterChar", " "); // 指定無し

		CostScrSR costScrSR = list.get(0);

		//	CSVダウンロード検索条件設定
		param.put("companyCode", costScrSR.getCompanyCode());
		//////////////////////////////// 経費負担部署パラメータ
		int codeCount = 0;
		StringBuffer costSectionSql = new StringBuffer();
		costSectionSql.append("(");
		costSectionSql.append("(ncsl.COST_SECTION_CODE,ncsl.COST_SECTION_CODE_OLD)" + " IN (");
		for(Iterator<CostScrSR> iter = list.iterator(); iter.hasNext();){
			codeCount++;
			CostScrSR obj = iter.next();
			if(codeCount > 1000) { // 1000個単位でIN句をOR結合(ORACLE最大)
				costSectionSql.append(") OR " + "(ncsl.COST_SECTION_CODE, ncsl.COST_SECTION_CODE_OLD)" + " IN (");
				codeCount = 1;
			}
			else if(codeCount > 1){
				costSectionSql.append(",");
			}
			costSectionSql.append(" ('" + obj.getCostSectionCode() + "', '" + obj.getCostSectionCodeOld() + "') ");
		}
		costSectionSql.append(")");
		costSectionSql.append(")");
		param.put("costSection", costSectionSql);

		// 検索条件クリア
		costScrSR.setCostSectionCode(null);
		costScrSR.setCostSectionCodeOld(null);
		param.put("searchParam", costScrSR);

		return createCostScrLineCsvHandler(loginStaffCode, accessLevel, costScrSR.getPeriod(), costScrSR.getScrType(), param);

	}


	/**
	 * 経費負担部課精査明細ダウンロードおよび、エクスポートCSV作成取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param param ダウンロード検索条件
	 * @return
	 * @throws SQLException
	 */
	private CsvWriterRowHandler createCostScrLineCsvHandler(String loginStaffCode, String accessLevel, String period, String scrType, Map<String, Object> param) throws SQLException, IOException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		String categoryCode = getScrTypeCategoryCode(scrType);	//	カテゴリーコード取得

		CsvWriterRowHandler handler = null;
		StringBuffer headerRow = new StringBuffer();
		List<String> outputPropList = new ArrayList<String>();
		List<Format> outputFormatList = new ArrayList<Format>();

		try {
			//	CSV用設定項目取得
			new MasterDAO().setCsvDef(categoryCode, accessLevel, headerRow, outputPropList, outputFormatList);
			String scrTypeName = "";
			if(Function.nvl(scrType, "").equals(Constants.SCR_TYPE_FLD_TAN)){
				scrTypeName = "有形固定資産";
				headerRow.insert(0, "　※　「取得価額」、「帳簿価額(4月末)」は資産番号別の合計金額です。\n");
			}
			else if(Function.nvl(scrType, "").equals(Constants.SCR_TYPE_FLD_INT)){
				scrTypeName = "無形固定資産";
				headerRow.insert(0, "\n");
			}
			else if(Function.nvl(scrType, "").equals(Constants.SCR_TYPE_RE)){
				scrTypeName = "レンタル";
				headerRow.insert(0, "　※　「契約金額」、「均等金額」は資産番号別の合計金額です。\n");
			}
			else if(Function.nvl(scrType, "").equals(Constants.SCR_TYPE_LE)){
				scrTypeName = "リース";
				headerRow.insert(0, "　※　「契約金額」、「均等金額」は資産番号別の合計金額です。\n");
			}
			headerRow.insert(0, "　※　インポート時に同一資産番号に対して異なる精査結果、コメントが入力されると、先頭行の入力内容が採用されます。" + "\n");
			headerRow.insert(0, "　※　インポートを実行する場合、精査結果、コメント以外の項目は編集しないでください。（精査結果項目は「OK」もしくは「要対応」を入力してください。）" + "\n");
			headerRow.insert(0, "会計年月：" + period.substring(0, 4) + "-" + period.substring(4, 6) + " " + "精査種別：" + scrTypeName + "\n");
			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) outputPropList.toArray(new String[0]), (Format[]) outputFormatList.toArray(new Format[0]));
			String sql = "";
			sql = getCostScrLineSqlName(scrType);
			sqlMap.queryWithRowHandler(sql, param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 経費負担部課精査カテゴリーコード取得
	 * @param invAssetType 資産区分
	 * @return
	 * @throws SQLException
	 */
	private String getScrTypeCategoryCode(String scrType){
		String categoryCode = "";
		//	有形固定資産
		if(scrType.equals(Constants.SCR_TYPE_FLD_TAN)){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_COST_SCR_LINE_FLD_TAN;
		}
		//	無形固定資産
		else if(scrType.equals(Constants.SCR_TYPE_FLD_INT)){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_COST_SCR_LINE_FLD_INT;
		}
		//	リース・レンタル
		else{
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_COST_SCR_LINE_LE_RE;
		}
		return categoryCode;
	}

	/**
	 * 経費負担部課精査検索SQL取得
	 * @param scrType 精査種別
	 * @return
	 * @throws SQLException
	 */
	private String getCostScrLineSqlName(String scrType){
		String sql = "";
		// 有形もしくは、無形？
		if(Function.nvl(scrType, "").equals(Constants.SCR_TYPE_FLD_TAN)
		|| Function.nvl(scrType, "").equals(Constants.SCR_TYPE_FLD_INT)){
			sql = "selectCostScrLineFld_SCR";
		}
		// リースもしくは、レンタル？
		else if(Function.nvl(scrType, "").equals(Constants.SCR_TYPE_LE)
		|| Function.nvl(scrType, "").equals(Constants.SCR_TYPE_RE)){
			sql = "selectCostScrLineLld_SCR";
		}
		return sql;
	}

	/**
	 * 経費負担部課精査明細データ作成実行
	 * @param companyCode 会社コード
	 * @param period 会計年月(yyyyMM)
	 * @param execStaffCode 実行者社員番号
	 */
	public void callCreateCostScrData(String companyCode, String period, String execStaffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("period", period);
		sqlParam.put("execStaffCode", execStaffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("callCreateCostScrData_SCR", sqlParam);
	}

	/**
	 * 経費負担部課精査明細データ作成実行
	 * @param companyCode 会社コード
	 * @param period 会計年月(yyyyMM)
	 * @param execStaffCode 実行者社員番号
	 * @param execStatus ステータス
	 */
	public void callUpdateCostScrStatus(String companyCode, String period, String execStaffCode, String execStatus) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("period", period);
		sqlParam.put("execStaffCode", execStaffCode);
		sqlParam.put("execStatus", execStatus);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("callUpdateCostScrStatus_SCR", sqlParam);
	}

	/**
	 * ファイル・直入力可能マスタ値CSV作成
	 * @param companyCode 会社コード
	 * @param accessLevel アクセスレベル
	 * @param propertyList 項目名一覧
	 * @return CSVファイル識別ID
	 * @throws SQLException
	 * @throws IOException
	 */
	public CsvWriterRowHandler createScrPossibleInputMasterList(String companyCode, String accessLevel, List<String> propertyList) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("companyCode", companyCode);
		param.put("accessLevel", accessLevel);
		param.put("propertyList", propertyList);

		String headerRow = "項目,入力可能値,備考";
		String[] outputProps = new String[]{"categoryCode", "value1", "description"};
		Format[] outputFormats = new Format[]{null, null, null};

		CsvWriterRowHandler handler = null;
		try {
			handler = new CsvWriterRowHandler(headerRow, outputProps, outputFormats);
			sqlMap.queryWithRowHandler("selectScrPossibleInputMasterList_SCR", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}


}