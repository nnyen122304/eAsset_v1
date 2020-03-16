/*===================================================================
 * ファイル名 : FldDAO.java
 * 概要説明   :  固定資産(照会・管理項目修正)用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-14 1.0  李           新規
 *=================================================================== */

package jp.co.ctcg.easset.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.fld.PpfsFld;
import jp.co.ctcg.easset.dto.fld.PpfsFldApp;
import jp.co.ctcg.easset.dto.fld.PpfsFldSC;
import jp.co.ctcg.easset.dto.fld.PpfsFldSR;
import jp.co.ctcg.easset.dto.fld.PpfsFldSupport;
import jp.co.ctcg.easset.dto.fld.PpfsUnUpd;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

import com.ibatis.sqlmap.client.SqlMapClient;


public class FldDAO {

	/**
	 * 固定資産(照会・管理項目修正)検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam    検索条件
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return 固定資産(照会・管理項目修正)一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PpfsFldSR> selectFldList(String loginStaffCode, String accessLevel, String companyCode, PpfsFldSC searchParam ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectFldListParam(loginStaffCode, accessLevel, companyCode, searchParam);

		return (List<PpfsFldSR>)sqlMap.queryForList("selectFldList_FLD", param);
	}

	/**
	 * 固定資産CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param searchParam    検索条件
	 * @param itemDef ダウンロード定義
	 * @param isHist true:履歴 false:カレント
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createFldListCsv(String loginStaffCode, String accessLevel, String companyCode, List<String> outputPropList, PpfsFldSC searchParam, String itemDef, boolean isHist) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		MasterDAO masterDao = new MasterDAO();
		Map<String, Object> param = getSelectFldListParam(loginStaffCode, accessLevel, companyCode, searchParam);

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し

		if(isHist) { // 履歴参照
			param.put("histTablePrefixLd", "H_"); // 台帳：履歴テーブル接頭語設定
		}

		if(!(Function.nvl(searchParam.getReportPeriod(), "").equals(Function.nvl(searchParam.getReportPeriodFrom(), ""))
			&& Function.nvl(searchParam.getReportPeriod(), "").equals(Function.nvl(searchParam.getReportPeriodTo(), "")))
				) {
			param.put("histTablePrefixFromTo", "H_"); // 期間明細：履歴テーブル接頭語設定
		}

		StringBuffer headerRow = new StringBuffer();
		List<String> propList = new ArrayList<String>();
		List<Format> propFormatList = new ArrayList<Format>();

		CsvWriterRowHandler handler = null;
		try {
			if(itemDef.indexOf(" ") != -1) { // itemDefが複合の場合
				String[] itemDefs = itemDef.split(" ");
				if(itemDefs.length == 2) {
					if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_PL_SCH)) { // スケジュールの場合

						// 月・四半期・半期・年で項目定義切り替え
						if(Function.nvl(searchParam.getReportSchUnit(), "").equals("1")) { // 月
							itemDefs[0] += "_M";
						} else if(Function.nvl(searchParam.getReportSchUnit(), "").equals("2")) { // 四半期
							itemDefs[0] += "_Q";
						} else if(Function.nvl(searchParam.getReportSchUnit(), "").equals("3")) { // 半期
							itemDefs[0] += "_H";
						} else if(Function.nvl(searchParam.getReportSchUnit(), "").equals("4")) { // 年
							itemDefs[0] += "_Y";
						}

						// 金額項目出力指定を自動的に追加
						for(int i = 1; i <= Constants.MAX_PPFS_SCH_CALC_YEAR; i++) { // 最大10年まで ■2011/11/11 高山改修
							if(Function.nvl(searchParam.getReportSchCalcYear(), Integer.valueOf(1)).intValue() >= i) {
								outputPropList.add("amount" + i + "_k"); // 期首簿価
								for(int j = 1; j <= 12; j++) {
									outputPropList.add("amount" + i + "_" + j); // スケジュール金額
								}
								outputPropList.add("amount" + i + "_n"); // 任意償却額
								outputPropList.add("amount" + i + "_z"); // 増加償却額
								outputPropList.add("amount" + i + "_t"); // 特別償却額
								outputPropList.add("amount" + i + "_g"); // 減損償却額
							}
						}
					}

					masterDao.setCsvDef(itemDefs[0], accessLevel, outputPropList, headerRow, propList, propFormatList, itemDefs[1], "", null, null);
//				} else {
//					masterDao.setCsvDef(itemDefs[0], accessLevel, outputPropList, headerRow, propList, propFormatList, itemDefs[1], "", null, null);
				}
			} else { // その他
				masterDao.setCsvDef(itemDef, accessLevel, outputPropList, headerRow, propList, propFormatList);
			}

			// CSVヘッダに基本条件をセット
			StringBuffer headerSC = new StringBuffer();
			if(!Function.nvl(searchParam.getReportPeriod(), "").equals("")) {
				headerSC.append("会計年月：" + searchParam.getReportPeriod());
			} else {
				headerSC.append("会計年月：" + masterDao.getPpfsCurrentPeriod(companyCode));
			}

			String sqlName = null;
			if(Function.nvl(itemDef, "").equals(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD)
				|| Function.nvl(itemDef, "").equals(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_TAN)
				|| Function.nvl(itemDef, "").equals(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_INT)) { // 固定資産台帳・固定資産照会
				sqlName = "selectFldList_FLD";
			} else if(Function.nvl(itemDef, "").equals(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_INT_APP)) { // 固定資産照会:申請書単位
				if(Function.isAccessLevelAdmin(accessLevel)) {
					headerSC.append("　一括更新可能項目：保有部署コード、無形管理担当者社員番号、管理項目1-20");
				} else {
					headerSC.append("　一括更新可能項目：保有部署コード(※)、無形管理担当者社員番号(※)、管理項目1-10 ※ ただし移動申請使用会社については、保有部署、無形管理担当者の変更は移動申請にて行ってください。");
				}
				sqlName = "selectFldListApp_FLD";
			} else if(Function.nvl(itemDef, "").equals(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_PL + " " + Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD)) { // 償却費・社内金利明細
				sqlName = "selectFldPlList_FLD";
				param.put("excludeCostSectionComWhere", "1"); // 共通検索条件の経費負担部課除外（明細の部課を使用するため）

				// CSVヘッダに基本条件をセット
				headerSC.append("　出力期間：" + searchParam.getReportPeriodFrom() + "-" + searchParam.getReportPeriodTo());
				headerSC.append("　配賦指定：" + (Function.nvl(searchParam.getReportDistType(), "").equals("1") ? "配賦前" : "配賦後"));
			} else if(Function.nvl(itemDef, "").equals(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_PL_SCH + " " + Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD)) { // 償却費・社内金利スケジュール
				sqlName = "selectFldPlSchList_FLD";
				param.put("excludeCostSectionComWhere", "1"); // 共通検索条件の経費負担部課除外（明細の部課を使用するため）

				// 社内金利表示
				List<CodeName> list = masterDao.selectCodeNameList(Constants.CATEGORY_CODE_USE_COMPANY, companyCode, null, null, null, false);
				String kinriDisplay = "";
				if(list != null && list.size() > 0) {
					kinriDisplay = Function.nvl(list.get(0).getValue8(), "");
				}
				if(kinriDisplay.equals(Constants.FLAG_YES)) {
					param.put("kinriDisplay", "1");
				}

				// 年数ループ用のパラメータセット&ヘッダ行に年数セット
				ArrayList<Integer> yearCountList = new ArrayList<Integer>();
				String replaceHeaderRow = headerRow.toString();
				int year = Function.getYear(searchParam.getReportSchCalcBasePeriod());
				for(int i = 1; i <= Constants.MAX_PPFS_SCH_CALC_YEAR; i++) { // 最大10年まで ■2014/11/11  高山改修
					yearCountList.add(Integer.valueOf(i));

					replaceHeaderRow = replaceHeaderRow.replaceAll("\\$\\{" + i + "年目\\}", String.valueOf(year) + "年度");
					year++;
				}
				param.put("yearCountList", yearCountList);
				headerRow = new StringBuffer(replaceHeaderRow);

				// 計算基準日のパラメータセット
				param.put("calcBasePeriod", searchParam.getReportSchCalcBasePeriod());


				// CSVヘッダに基本条件をセット
				headerSC.append("　配賦指定：" + (Function.nvl(searchParam.getReportDistType(), "").equals("1") ? "配賦前" : "配賦後"));
			} else if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_JOIN_KH)) { // 仮勘定-本勘定紐付リスト
				sqlName = "selectFldJoinKHList_FLD";
			} else if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_JOIN_SG_TAN)) { // 資産-現物紐付リスト(有形)
				sqlName = "selectFldJoinSGTanList_FLD";
			} else if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_JOIN_SG_INT)) { // 資産-現物紐付リスト(無形)
				sqlName = "selectFldJoinSGIntList_FLD";
			}


			if(headerSC.length() > 0) headerRow.insert(0, headerSC.toString() + "\n"); // 基本検索条件を追加
			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) propList.toArray(new String[0]), (Format[]) propFormatList.toArray(new Format[0]));

			sqlMap.queryWithRowHandler(sqlName, param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 固定資産(照会・管理項目修正)取得
	 * @param koyuno 固有番号
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 情報機器等
	 * @throws SQLException
	 */
	public PpfsFld selectFld(Long koyuno ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("koyuno", koyuno);

		return (PpfsFld)sqlMap.queryForObject("selectFld_FLD", param);

	}

	/**
	 * 固定資産(照会・管理項目修正)取得
	 * @param koyuno 固有番号
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 情報機器等
	 * @throws SQLException
	 */
	public PpfsFld selectFld(String shisanNum ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("shisanNum", shisanNum);

		return (PpfsFld)sqlMap.queryForObject("selectFld_FLD", param);

	}


	/**
	 * 固定資産(照会・管理項目修正)_取得申請単位
	 * @param applicationId 取得申請番号
	 * @param companyCode 会社コード
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 固定資産(照会・管理項目修正)_取得申請単位
	 * @throws SQLException
	 */
	public PpfsFldApp selectFldApp(String applicationId, String companyCode, boolean lockFlag ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("companyCode", companyCode);

		return (PpfsFldApp)sqlMap.queryForObject("selectFldApp_FLD", param);

	}

	/**
	 * 固定資産(照会・管理項目修正)_取得申請単位_更新
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void updateFldApp(PpfsFldApp obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.update("updateFldApp_FLD", param);
	}
	/**
	 * 固定資産(照会・管理項目修正)_取得申請単位_新規作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertFldApp(PpfsFldApp obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertFldApp_FLD", param);
	}

	/**
	 * 補助台帳からﾗｲｾﾝｽもしくは、情報機器の管理番号取得
	 * @param koyuno 固有番号
	 * @param 種別コード
	 * @return ﾗｲｾﾝｽもしくは、情報機器の管理番号一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PpfsFldSupport> selectFldSupportList(Long koyuno, String syubetucd ) throws SQLException {

		//	SQLマップ情報	生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("koyuno", koyuno);
		param.put("syubetucd", syubetucd);
		//	経費負担部課履歴？
		if( Function.nvl(syubetucd, "").equals(Constants.PPFS_SUPPORT_COST_SEC_HIST) ){
			param.put("costSecHist", "1");
		}

		return (List<PpfsFldSupport>)sqlMap.queryForList("selectFldSupport_FLD", param);

	}

	/**
	 * 固定資産照会検索(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getSelectFldListParam(String loginStaffCode, String accessLevel, String companyCode, PpfsFldSC searchParam) throws SQLException
	{
		MasterDAO masterDao = new MasterDAO();

		Map<String, Object> param = new HashMap<String, Object>();

		//	会社コード設定
		param.put("companyCode", companyCode);

		//	検索資産区分が有形？無形？両方？
		//	無形？
		String skkshisankbnTrans = "";
		if(Function.nvl(searchParam.getSkkshisankbn(), "").equals("")) {
			//	有形、無形両方検索？
			skkshisankbnTrans = "0";
		} else {
			// 有形？
			if(searchParam.getSkkshisankbn().indexOf("1") > -1){
				skkshisankbnTrans = "1";
			}
			if(searchParam.getSkkshisankbn().indexOf("2") > -1){
				//	有形、無形両方検索？
				if(searchParam.getSkkshisankbn().indexOf("1") > -1){
					skkshisankbnTrans = "0";
				}
				//	無形のみ？
				else{
					skkshisankbnTrans = "2";
				}
			}
		}
		param.put("skkshisankbnTrans", skkshisankbnTrans);

		Integer currentYear = masterDao.getCurrentYear();						// カレント年度取得

		String isSubSearch = getSubSearchFlag(searchParam);	//	情報機器、ﾗｲｾﾝｽ、無形固定資産付加情報の検索を行うかどうか

		// 検索対象：管轄分
		if(Function.nvl(searchParam.getSearchScopeType(), "").equals(Constants.SEARCH_SCOPE_TYPE_EDIT_ONLY)){
			// 無形固定資産用追加権限(所属部署の上位部署は照会可能)
			List<String> sectionCodeListI = masterDao.selectAccessibleUpperSectionCodeList(companyCode, loginStaffCode);

			//////////////////////////////// アクセスレベル
			if(Function.isAccessLevelGeneral(accessLevel)) {					// 一般権限
				// 機器検索条件
				param.put("accessLevelUserA", loginStaffCode);

				// ライセンス検索条件
				param.put("accessLevelUserL", loginStaffCode);

				// 無形付加情報検索条件
				List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
				companyColumnList.add("nie.HOL_COMPANY_CODE");
				List<String> staffColumnList = new ArrayList<String>();			// 社員番号カラム
				staffColumnList.add("nie.HOL_STAFF_CODE");
				List<String> sectionColumnList = new ArrayList<String>();		// 部署カラム
				sectionColumnList.add("nie.HOL_SECTION_CODE");
				List<String> sectionYearColumnList = new ArrayList<String>();	// 部署年度カラム
				sectionYearColumnList.add("nie.HOL_SECTION_YEAR");

				// アクセス可能階層部署コード一覧取得
				List<String> sectionCodeList = new ArrayList<String>(sectionCodeListI);
				if(sectionCodeList.size() == 0) {
					sectionCodeList.add("X");
				}

				param.put("accessLevelUserI", Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, loginStaffCode, sectionCodeList, currentYear));

			} else if(Function.isAccessLevelSection(accessLevel)) {				// 部署権限
				// アクセス可能階層部署コード一覧取得
				List<String> sectionCodeList = masterDao.selectAccessibleSectionCodeList(companyCode, loginStaffCode, searchParam.getIncludeSectionHierarchyFlag());

				// 機器検索条件
				List<String> staffColumnList = new ArrayList<String>();			// 社員番号カラム
				staffColumnList.add("HOL_STAFF_CODE");
				staffColumnList.add("USE_STAFF_CODE");
				List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
				companyColumnList.add("HOL_COMPANY_CODE");
				companyColumnList.add("USE_COMPANY_CODE");
				List<String> sectionColumnList = new ArrayList<String>();		// 部署カラム
				sectionColumnList.add("HOL_SECTION_CODE");
				sectionColumnList.add("USE_SECTION_CODE");
				List<String> sectionYearColumnList = new ArrayList<String>();	// 部署年度カラム
				sectionYearColumnList.add("HOL_SECTION_YEAR");
				sectionYearColumnList.add("USE_SECTION_YEAR");

				param.put("accessLevelSectionA", Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, loginStaffCode, sectionCodeList, currentYear));

				// ライセンス検索条件
				staffColumnList = new ArrayList<String>();			// 社員番号カラム
				staffColumnList.add("AP_STAFF_CODE");
				staffColumnList.add("HOL_STAFF_CODE");
				companyColumnList = new ArrayList<String>();			// 会社カラム
				companyColumnList.add("HOL_COMPANY_CODE");
				companyColumnList.add("USE_COMPANY_CODE");
				sectionColumnList = new ArrayList<String>();		// 部署カラム
				sectionColumnList.add("HOL_SECTION_CODE");
				sectionColumnList.add("USE_SECTION_CODE");
				sectionYearColumnList = new ArrayList<String>();	// 部署年度カラム
				sectionYearColumnList.add("HOL_SECTION_YEAR");
				sectionYearColumnList.add("USE_SECTION_YEAR");

				param.put("accessLevelSectionL", Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, loginStaffCode, sectionCodeList, currentYear));

				// 無形付加情報検索条件
				staffColumnList = new ArrayList<String>();			// 社員番号カラム
				staffColumnList.add("nie.HOL_STAFF_CODE");
				companyColumnList = new ArrayList<String>();			// 会社カラム
				companyColumnList.add("nie.HOL_COMPANY_CODE");
				sectionColumnList = new ArrayList<String>();		// 部署カラム
				sectionColumnList.add("nie.HOL_SECTION_CODE");
				sectionYearColumnList = new ArrayList<String>();	// 部署年度カラム
				sectionYearColumnList.add("nie.HOL_SECTION_YEAR");

				// 無形固定資産用権限追加
				sectionCodeList.addAll(sectionCodeListI);

				param.put("accessLevelSectionI", Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, loginStaffCode, sectionCodeList, currentYear));
			}

			isSubSearch = Constants.FLAG_YES;
		}

		//////////////////////////////// 部署検索
		if(!Function.nvl(searchParam.getHolSectionCode(), "").equals("")) {
			List<String> sectionCodeList = new ArrayList<String>();

			if(Function.nvl(searchParam.getIncludeSectionHierarchyFlag(), "0").equals("1")) { // 階層指定有
				sectionCodeList.addAll(masterDao.selectHierarchySectionCodeList(companyCode, searchParam.getHolSectionCode(), currentYear));
			} else { // 階層指定無し
				sectionCodeList.add(searchParam.getHolSectionCode());
			}

			List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
			companyColumnList.add("ptvf1.HOL_COMPANY_CODE");
			companyColumnList.add("ptvf1.USE_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>();			// 部署カラム
			sectionColumnList.add("ptvf1.HOL_SECTION_CODE");
			sectionColumnList.add("ptvf1.USE_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>();		// 部署年度カラム
			sectionYearColumnList.add("ptvf1.HOL_SECTION_YEAR");
			sectionYearColumnList.add("ptvf1.USE_SECTION_YEAR");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, null, sectionCodeList, currentYear));

			isSubSearch = Constants.FLAG_YES;
		}

		//////////////////////////////// 複数指定検索
		// 資産区分
		List<String> skkshisankbnPluralList = Function.getPluralList(searchParam.getSkkshisankbn());
		if(skkshisankbnPluralList.size() > 0) {
			param.put("skkshisankbnPluralList", Function.getInCondition("ptvf.SKKSHISANKBN", skkshisankbnPluralList, true));
		}
		//	資産管理区分
		List<String> shisanknrkbnPluralList = Function.getPluralList(searchParam.getShisanknrkbn());
		if(shisanknrkbnPluralList.size() > 0) {
			param.put("shisanknrkbnPluralList", Function.getInCondition("ptvf.SHISANKNRKBN", shisanknrkbnPluralList, true));
		}
		//	資産状態区分
		List<String> shisanjotaikbnPluralList = Function.getPluralList(searchParam.getShisanjotaikbn());
		if(shisanjotaikbnPluralList.size() > 0) {
			param.put("shisanjotaikbnPluralList", Function.getInCondition("ptvf.SHISANJOTAIKBN", shisanjotaikbnPluralList, true));
		}
		// 種類
		List<String> shuruinmPluralList = Function.getPluralList(searchParam.getShuruinmPlural());
		if(shuruinmPluralList.size() > 0) {
			param.put("shuruinmPluralList", Function.getInCondition("ptvf.SHURUICD", shuruinmPluralList, true));
		}
		// 分類
		List<String> bunruinmPluralList = Function.getPluralList(searchParam.getBunruinmPlural());
		if(bunruinmPluralList.size() > 0) {
			param.put("bunruinmPluralList", Function.getInCondition("ptvf.BUNRUICD", bunruinmPluralList, true));
		}
		// 代表設置場所
		List<String> setchinmPluralList = Function.getPluralList(searchParam.getSetchinmPlural());
		if(setchinmPluralList.size() > 0) {
			param.put("setchinmPluralList", Function.getInCondition("ptvf.SETCHICD", setchinmPluralList, true));
		}
		// 案件グループ
		List<String> itemGroupNamePluralList = Function.getPluralList(searchParam.getItemGroupNamePlural());
		if(itemGroupNamePluralList.size() > 0) {
			param.put("itemGroupNamePluralList", Function.getInCondition("ptvf.ITEM_GROUP_CODE", itemGroupNamePluralList, true));
		}
		// 伝票番号
		List<String> slipNumPluralList = Function.getPluralList(searchParam.getSlipNumPlural());
		if(slipNumPluralList.size() > 0) {
			param.put("slipNumPluralList", Function.getInCondition("ptvf.SLIP_NUM", slipNumPluralList, true));
		}
		//	資産番号
		// 関連資産も同時に検索する
		if(Function.nvl(searchParam.getRelationShisanNumFlag(), "").equals(Constants.FLAG_YES)){
			if(!Function.nvl(searchParam.getShisanNum(), "").equals("") && searchParam.getShisanNum().length() > 15){
				searchParam.setShisanNum(searchParam.getShisanNum().substring(0, 15));
			}
		}
		List<String> shisanNumPluralList = Function.getPluralList(searchParam.getShisanNumPlural());
		if(shisanNumPluralList.size() > 0) {
			// 関連資産も同時に検索する
			if(Function.nvl(searchParam.getRelationShisanNumFlag(), "").equals(Constants.FLAG_YES)){
				for(int i = 0; i < shisanNumPluralList.size(); i++){
					String shisanNum = Function.nvl((String)shisanNumPluralList.get(i), "");
					if(shisanNum.length() > 15){
						shisanNumPluralList.set(i, shisanNum.substring(0, 15));
					}
				}
				param.put("shisanNumPluralList", Function.getInCondition("ptvf.OYA", shisanNumPluralList, true));
			}
			else{
				param.put("shisanNumPluralList", Function.getInCondition("ptvf.OYA || ptvf.EDA", shisanNumPluralList, true));
			}
		}
		// 取得申請
		List<String> applicationIdPluralList = Function.getPluralList(searchParam.getApplicationIdPlural());
		if(applicationIdPluralList.size() > 0) {
			param.put("applicationIdPluralList", Function.getInCondition("ptvf.APPLICATION_ID", applicationIdPluralList, true));
		}
		// 情報機器管理番号
		List<String> assetIdPluralList = Function.getPluralList(searchParam.getAssetIdPlural());
		if(assetIdPluralList.size() > 0) {
			param.put("assetIdPluralList", Function.getInCondition("ptvf1.ASSET_ID", assetIdPluralList, true));
		}
		// ﾗｲｾﾝｽ管理番号
		List<String> licenseIdPluralList = Function.getPluralList(searchParam.getLicenseIdPlural());
		if(licenseIdPluralList.size() > 0) {
			param.put("licenseIdPluralList", Function.getInCondition("ptvf1.LICENSE_ID", licenseIdPluralList, true));
		}
		// 個別設置(使用)場所
		List<String> holOfficePluralList = Function.getPluralList(searchParam.getHolOfficePlural());
		if(holOfficePluralList.size() > 0) {
			param.put("holOfficePluralList", Function.getInCondition("ptvf1.HOL_OFFICE_CODE", holOfficePluralList, true));
		}
		// 管理区分
		List<String> astManageTypePluralList = Function.getPluralList(searchParam.getAstManageTypePlural());
		if(astManageTypePluralList.size() > 0) {
			param.put("astManageTypePluralList", Function.getInCondition("ptvf1.AST_MANAGE_TYPE", astManageTypePluralList, true));
		}

		// 管理項目
		List<String> dscAttributePluralList = Function.getPluralList(searchParam.getDscAttribute());
		if(dscAttributePluralList.size() > 0) {
			param.put("dscAttribute1PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE1", dscAttributePluralList));
			param.put("dscAttribute2PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE2", dscAttributePluralList));
			param.put("dscAttribute3PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE3", dscAttributePluralList));
			param.put("dscAttribute4PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE4", dscAttributePluralList));
			param.put("dscAttribute5PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE5", dscAttributePluralList));
			param.put("dscAttribute6PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE6", dscAttributePluralList));
			param.put("dscAttribute7PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE7", dscAttributePluralList));
			param.put("dscAttribute8PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE8", dscAttributePluralList));
			param.put("dscAttribute9PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE9", dscAttributePluralList));
			param.put("dscAttribute10PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE10", dscAttributePluralList));
			param.put("dscAttribute11PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE11", dscAttributePluralList));
			param.put("dscAttribute12PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE12", dscAttributePluralList));
			param.put("dscAttribute13PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE13", dscAttributePluralList));
			param.put("dscAttribute14PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE14", dscAttributePluralList));
			param.put("dscAttribute15PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE15", dscAttributePluralList));
			param.put("dscAttribute16PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE16", dscAttributePluralList));
			param.put("dscAttribute17PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE17", dscAttributePluralList));
			param.put("dscAttribute18PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE18", dscAttributePluralList));
			param.put("dscAttribute19PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE19", dscAttributePluralList));
			param.put("dscAttribute20PluralList", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE20", dscAttributePluralList));
		}
		List<String> dscAttribute1List = Function.getPluralList(searchParam.getDscAttribute1());
		if(dscAttribute1List.size() > 0) {
			param.put("dscAttribute1List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE1", dscAttribute1List));
		}
		List<String> dscAttribute2List = Function.getPluralList(searchParam.getDscAttribute2());
		if(dscAttribute2List.size() > 0) {
			param.put("dscAttribute2List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE2", dscAttribute2List));
		}
		List<String> dscAttribute3List = Function.getPluralList(searchParam.getDscAttribute3());
		if(dscAttribute3List.size() > 0) {
			param.put("dscAttribute3List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE3", dscAttribute3List));
		}
		List<String> dscAttribute4List = Function.getPluralList(searchParam.getDscAttribute4());
		if(dscAttribute4List.size() > 0) {
			param.put("dscAttribute4List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE4", dscAttribute4List));
		}
		List<String> dscAttribute5List = Function.getPluralList(searchParam.getDscAttribute5());
		if(dscAttribute5List.size() > 0) {
			param.put("dscAttribute5List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE5", dscAttribute5List));
		}
		List<String> dscAttribute6List = Function.getPluralList(searchParam.getDscAttribute6());
		if(dscAttribute6List.size() > 0) {
			param.put("dscAttribute6List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE6", dscAttribute6List));
		}
		List<String> dscAttribute7List = Function.getPluralList(searchParam.getDscAttribute7());
		if(dscAttribute7List.size() > 0) {
			param.put("dscAttribute7List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE7", dscAttribute7List));
		}
		List<String> dscAttribute8List = Function.getPluralList(searchParam.getDscAttribute8());
		if(dscAttribute8List.size() > 0) {
			param.put("dscAttribute8List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE8", dscAttribute8List));
		}
		List<String> dscAttribute9List = Function.getPluralList(searchParam.getDscAttribute9());
		if(dscAttribute9List.size() > 0) {
			param.put("dscAttribute9List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE9", dscAttribute9List));
		}
		List<String> dscAttribute10List = Function.getPluralList(searchParam.getDscAttribute10());
		if(dscAttribute10List.size() > 0) {
			param.put("dscAttribute10List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE10", dscAttribute10List));
		}
		List<String> dscAttribute11List = Function.getPluralList(searchParam.getDscAttribute11());
		if(dscAttribute11List.size() > 0) {
			param.put("dscAttribute11List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE11", dscAttribute11List));
		}
		List<String> dscAttribute12List = Function.getPluralList(searchParam.getDscAttribute12());
		if(dscAttribute12List.size() > 0) {
			param.put("dscAttribute12List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE12", dscAttribute12List));
		}
		List<String> dscAttribute13List = Function.getPluralList(searchParam.getDscAttribute13());
		if(dscAttribute13List.size() > 0) {
			param.put("dscAttribute13List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE13", dscAttribute13List));
		}
		List<String> dscAttribute14List = Function.getPluralList(searchParam.getDscAttribute14());
		if(dscAttribute14List.size() > 0) {
			param.put("dscAttribute14List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE14", dscAttribute14List));
		}
		List<String> dscAttribute15List = Function.getPluralList(searchParam.getDscAttribute15());
		if(dscAttribute15List.size() > 0) {
			param.put("dscAttribute15List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE15", dscAttribute15List));
		}
		List<String> dscAttribute16List = Function.getPluralList(searchParam.getDscAttribute16());
		if(dscAttribute16List.size() > 0) {
			param.put("dscAttribute16List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE16", dscAttribute16List));
		}
		List<String> dscAttribute17List = Function.getPluralList(searchParam.getDscAttribute17());
		if(dscAttribute17List.size() > 0) {
			param.put("dscAttribute17List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE17", dscAttribute17List));
		}
		List<String> dscAttribute18List = Function.getPluralList(searchParam.getDscAttribute18());
		if(dscAttribute18List.size() > 0) {
			param.put("dscAttribute18List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE18", dscAttribute18List));
		}
		List<String> dscAttribute19List = Function.getPluralList(searchParam.getDscAttribute19());
		if(dscAttribute19List.size() > 0) {
			param.put("dscAttribute19List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE19", dscAttribute19List));
		}
		List<String> dscAttribute20List = Function.getPluralList(searchParam.getDscAttribute20());
		if(dscAttribute20List.size() > 0) {
			param.put("dscAttribute20List", Function.getFuzzyOrCondition("ptvf1.DSC_ATTRIBUTE20", dscAttribute20List));
		}

		// 経費負担部課（複数）
		if(!Function.nvl(searchParam.getCostSectionCode0(), "").equals("") ||
		   !Function.nvl(searchParam.getCostSectionCode1(), "").equals("") ||
		   !Function.nvl(searchParam.getCostSectionCode2(), "").equals("") ||
		   !Function.nvl(searchParam.getCostSectionCode3(), "").equals("") ||
		   !Function.nvl(searchParam.getCostSectionCode4(), "").equals("") ||
		   !Function.nvl(searchParam.getCostSectionCode5(), "").equals("") ||
		   !Function.nvl(searchParam.getCostSectionCode6(), "").equals("") ||
		   !Function.nvl(searchParam.getCostSectionCode7(), "").equals("") ||
		   !Function.nvl(searchParam.getCostSectionCode8(), "").equals("") ||
		   !Function.nvl(searchParam.getCostSectionCode9(), "").equals("")) {
			String costSectionCodeIn = "";
			if(!Function.nvl(searchParam.getCostSectionCode0(), "").equals("")){
				costSectionCodeIn += "'" + searchParam.getCostSectionCode0() + "'";
			}
			if(!Function.nvl(searchParam.getCostSectionCode1(), "").equals("")){
				if(costSectionCodeIn.length() > 0) {
					costSectionCodeIn += ", ";
				}
				costSectionCodeIn += "'" + searchParam.getCostSectionCode1() + "'";
			}
			if(!Function.nvl(searchParam.getCostSectionCode2(), "").equals("")){
				if(costSectionCodeIn.length() > 0) {
					costSectionCodeIn += ", ";
				}
				costSectionCodeIn += "'" + searchParam.getCostSectionCode2() + "'";
			}
			if(!Function.nvl(searchParam.getCostSectionCode3(), "").equals("")){
				if(costSectionCodeIn.length() > 0) {
					costSectionCodeIn += ", ";
				}
				costSectionCodeIn += "'" + searchParam.getCostSectionCode3() + "'";
			}
			if(!Function.nvl(searchParam.getCostSectionCode4(), "").equals("")){
				if(costSectionCodeIn.length() > 0) {
					costSectionCodeIn += ", ";
				}
				costSectionCodeIn += "'" + searchParam.getCostSectionCode4() + "'";
			}
			if(!Function.nvl(searchParam.getCostSectionCode5(), "").equals("")){
				if(costSectionCodeIn.length() > 0) {
					costSectionCodeIn += ", ";
				}
				costSectionCodeIn += "'" + searchParam.getCostSectionCode5() + "'";
			}
			if(!Function.nvl(searchParam.getCostSectionCode6(), "").equals("")){
				if(costSectionCodeIn.length() > 0) {
					costSectionCodeIn += ", ";
				}
				costSectionCodeIn += "'" + searchParam.getCostSectionCode6() + "'";
			}
			if(!Function.nvl(searchParam.getCostSectionCode7(), "").equals("")){
				if(costSectionCodeIn.length() > 0) {
					costSectionCodeIn += ", ";
				}
				costSectionCodeIn += "'" + searchParam.getCostSectionCode7() + "'";
			}
			if(!Function.nvl(searchParam.getCostSectionCode8(), "").equals("")){
				if(costSectionCodeIn.length() > 0) {
					costSectionCodeIn += ", ";
				}
				costSectionCodeIn += "'" + searchParam.getCostSectionCode8() + "'";
			}
			if(!Function.nvl(searchParam.getCostSectionCode9(), "").equals("")){
				if(costSectionCodeIn.length() > 0) {
					costSectionCodeIn += ", ";
				}
				costSectionCodeIn += "'" + searchParam.getCostSectionCode9() + "'";
			}
			if(costSectionCodeIn.length() > 0) {
				param.put("costSectionCodeIn", costSectionCodeIn);
			}
		}

		param.put("searchParam", searchParam);

		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		//	現物情報固有番号取得
		if(Function.nvl(isSubSearch, "").equals(Constants.FLAG_YES)){
			List<Long> subKoyunoList = sqlMap.queryForList("selectFldKoyuno_FLD", param);
			if(subKoyunoList.size() > 0){
				param.put("subKoyunoList", Function.getInConditionByTempTable("ptvf.KOYUNO", subKoyunoList, masterDao));
			}
			else{
				param.put("subKoyunoList", " 1 != 1 ");
			}
		}

		return param;
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
	public CsvWriterRowHandler createFldPossibleInputMasterList(String companyCode, String accessLevel, List<String> propertyList) throws SQLException, IOException {
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
			sqlMap.queryWithRowHandler("selectFldPossibleInputMasterList_FLD", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * Ppfs未承認データ一覧取得
	 * @param companyCode 会社コード
	 * @param koyunoList 固有番号一覧
	 * @return Ppfs未承認データ一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PpfsUnUpd> selectPpfsUnUpd(String companyCode, String koyunoPluar) throws SQLException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("companyCode", companyCode);
		//	固有番号
		List<String> koyunoPluarList = Function.getPluralList(koyunoPluar);
		if(koyunoPluarList.size() > 0) {
			param.put("koyunoPluralList", Function.getInCondition("koyuno", koyunoPluarList, false));
		}

		return (List<PpfsUnUpd>)sqlMap.queryForList("selectPpfsUnUpd_FLD", param);
	}

	/**
	 * ProPlus固定資産更新チェック検索
	 * @param companyCode 会社コード
	 * @param loginStaffCode ログイン社員番号
	 * @param currentYear 年度
	 * @return ログイン社員関連所属部署一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PpfsFldSR> selectPpfsFld(String companyCode, String koyunoPluar ) throws SQLException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		//	固有番号
		List<String> koyunoPluarList = Function.getPluralList(koyunoPluar);
		if(koyunoPluarList.size() > 0) {
			param.put("koyunoPluralList", Function.getInCondition("koyuno", koyunoPluarList, false));
		}

		return (List<PpfsFldSR>)sqlMap.queryForList("selectPpfsFld_FLD", param);
	}

	/**
	 * 取得申請書番号に紐付くeAsset側の資産数を取得（サービス提供開始報告用）
	 * @param applicationId 取得申請書番号
	 * @return 資産数
	 * @throws SQLException
	 */
	public Integer selectFldCountEAsset(String applicationId) throws SQLException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (Integer)sqlMap.queryForObject("selectFldCountEAsset_FLD", param);
	}

	/**
	 * 取得申請書番号に紐付くProPlus側の資産数を取得（サービス提供開始報告用）
	 * @param applicationId 取得申請書番号
	 * @return 資産数
	 * @throws SQLException
	 */
	public Integer selectFldCountProPlus(String applicationId) throws SQLException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);

		return (Integer)sqlMap.queryForObject("selectFldCountProPlus_FLD", param);
	}

	/**
	 * サブクエリ用検索条件チェック
	 * @param searchParam 検索条件
	 * @return ログイン社員関連所属部署一覧
	 * @throws SQLException
	 */
	private String getSubSearchFlag( PpfsFldSC searchParam ){
		String isFlag = "";
		//	サブクエリ用検索条件チェック(現物情報など入力されているかチェック)
		if( !Function.nvl(searchParam.getAssetId(), "").equals("")
		||  !Function.nvl(searchParam.getAssetIdPlural(), "").equals("")
		||  !Function.nvl(searchParam.getLicenseId(), "").equals("")
		||  !Function.nvl(searchParam.getLicenseIdPlural(), "").equals("")
		||  !Function.nvl(searchParam.getHolSectionCode(), "").equals("")
		||  !Function.nvl(searchParam.getHolStaffCodeI(), "").equals("")
		||  !Function.nvl(searchParam.getHolStaffCodeT(), "").equals("")
		||  !Function.nvl(searchParam.getHolOfficeCode(), "").equals("")
		||  !Function.nvl(searchParam.getHolOfficePlural(), "").equals("")
		||  !Function.nvl(searchParam.getHolOfficeFloor(), "").equals("")
		||  !Function.nvl(searchParam.getUseStaffCode(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute1(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute2(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute3(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute4(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute5(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute6(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute7(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute8(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute9(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute10(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute11(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute12(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute13(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute14(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute15(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute16(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute17(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute18(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute19(), "").equals("")
		||  !Function.nvl(searchParam.getDscAttribute20(), "").equals("")
		||  !Function.nvl(searchParam.getAstSystemSectionDeployFlag(), "").equals("")
		||  !Function.nvl(searchParam.getAstManageTypePlural(), "").equals("")
		){
			isFlag = Constants.FLAG_YES;
		}

		return isFlag;

	}

}
