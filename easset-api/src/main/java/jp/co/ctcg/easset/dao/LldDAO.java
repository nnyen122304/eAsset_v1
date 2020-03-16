/*===================================================================
 * ファイル名 : LldDAO.java
 * 概要説明   : リース・レンタル(照会)用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-25 1.0  李           新規
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
import jp.co.ctcg.easset.dto.lld.PpfsLld;
import jp.co.ctcg.easset.dto.lld.PpfsLldSC;
import jp.co.ctcg.easset.dto.lld.PpfsLldSR;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

import com.ibatis.sqlmap.client.SqlMapClient;


public class LldDAO {

	/**
	 * リース・レンタル契約情報照会検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return 固定資産(照会・管理項目修正)一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PpfsLldSR> selectLldList(String loginStaffCode, String accessLevel, String companyCode, PpfsLldSC searchParam ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = getSelectLldListParam(loginStaffCode, accessLevel, companyCode, searchParam, sqlMap);

		return (List<PpfsLldSR>)sqlMap.queryForList("selectLldList_LLD", param);
	}


	/**
	 * リース・レンタルCSVファイル作成
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
 	public CsvWriterRowHandler createLldListCsv(String loginStaffCode, String accessLevel, String companyCode, List<String> outputPropList, PpfsLldSC searchParam, String itemDef, boolean isHist) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		MasterDAO masterDao = new MasterDAO();
		Map<String, Object> param = getSelectLldListParam(loginStaffCode, accessLevel, companyCode, searchParam, sqlMap);

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し

		if(isHist) { // 履歴参照
			param.put("histTablePrefixLd", "H_"); // 台帳：履歴テーブル接頭語設定
		}

		if((!(Function.nvl(searchParam.getReportPeriod(), "").equals(Function.nvl(searchParam.getReportPeriodFrom(), ""))
			&& Function.nvl(searchParam.getReportPeriod(), "").equals(Function.nvl(searchParam.getReportPeriodTo(), ""))))
			|| (isHist)
			) {
			param.put("histTablePrefixFromTo", "H_"); // 期間明細：履歴テーブル接頭語設定
		}

		StringBuffer headerRow = new StringBuffer();
		List<String> propList = new ArrayList<String>();
		List<Format> propFormatList = new ArrayList<Format>();

		CsvWriterRowHandler handler = null;
		try {
			if(itemDef.indexOf(" ") != -1) { //  itemDefが複合の場合
				String[] itemDefs = itemDef.split(" ");


				if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PL_SCH)
					|| Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_BS_SCH)) { // スケジュールの場合

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
					for(int i = 1; i <= Constants.MAX_PPFS_SCH_CALC_YEAR; i++) { // 最大10年まで ■2014/11/11 高山改修
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

				if(itemDefs.length == 2) {
					String categoryTitle = Function.nvl(itemDefs[1], "").equals(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD) ? "": "固定資産情報";
					masterDao.setCsvDef(itemDefs[0], accessLevel, outputPropList, headerRow, propList, propFormatList, itemDefs[1], categoryTitle, null, null);
				} else if(itemDefs.length == 3) {
					masterDao.setCsvDef(itemDefs[0], accessLevel, outputPropList, headerRow, propList, propFormatList, itemDefs[1], "", itemDefs[2], "固定資産情報");
				}
			} else { // その他
				masterDao.setCsvDef(itemDef, accessLevel, outputPropList, headerRow, propList, propFormatList);
			}

			if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PL_SCH)
					|| Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_BS_SCH)) { // スケジュールの場合
				// 年数ループ用のパラメータセット&ヘッダ行に年数セット
				ArrayList<Integer> yearCountList = new ArrayList<Integer>();
				String replaceHeaderRow = headerRow.toString();
				int year = Function.getYear(searchParam.getReportSchCalcBasePeriod());
				for(int i = 1; i <= Constants.MAX_PPFS_SCH_CALC_YEAR; i++) { // 最大10年まで ■2014/11/11 高山改修
					yearCountList.add(Integer.valueOf(i));

					replaceHeaderRow = replaceHeaderRow.replaceAll("\\$\\{" + i + "年目\\}", String.valueOf(year) + "年度");
					year++;
				}
				param.put("yearCountList", yearCountList);
				headerRow = new StringBuffer(replaceHeaderRow);

				// 計算基準日のパラメータセット
				param.put("calcBasePeriod", searchParam.getReportSchCalcBasePeriod());
			}


			// CSVヘッダに基本条件をセット
			StringBuffer headerSC = new StringBuffer();
			if(!Function.nvl(searchParam.getReportPeriod(), "").equals("")) {
				headerSC.append("会計年月：" + searchParam.getReportPeriod());
			} else {
				headerSC.append("会計年月：" + masterDao.getPpfsCurrentPeriod(companyCode));
			}

			String sqlName = null;
			if(Function.nvl(itemDef, "").equals(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_SR)) { // 照会
				sqlName = "selectLldList_LLD";
			} else if(Function.nvl(itemDef, "").equals(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD)
				|| Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD + " ")) { // 管理台帳
				if(Function.nvl(searchParam.getReportLldOutUnit(), "").equals("2")) { // 契約単位
					sqlName = "selectLldCList_LLD";
				} else { // 物件単位
					sqlName = "selectLldList_LLD";
				}

				// CSVヘッダに基本条件をセット
				headerSC.append("　出力単位：" + (Function.nvl(searchParam.getReportLldOutUnit(), "").equals("1") ? "物件単位" : "契約単位"));
			} else if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PAY)) { // 請求明細
				sqlName = "selectLldPayList_LLD";
				param.put("excludeCostSectionComWhere", "1"); // 共通検索条件の経費負担部課除外（明細の部課を使用するため）

				// CSVヘッダに基本条件をセット
				headerSC.append("　出力期間：" + searchParam.getReportPeriodFrom() + "-" + searchParam.getReportPeriodTo());
			} else if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PL + " " + Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD)) { // 賃借料・償却費・社内金利他明細
				sqlName = "selectLldPlList_LLD";
				param.put("excludeCostSectionComWhere", "1"); // 共通検索条件の経費負担部課除外（明細の部課を使用するため）

				// CSVヘッダに基本条件をセット
				headerSC.append("　出力期間：" + searchParam.getReportPeriodFrom() + "-" + searchParam.getReportPeriodTo());
				headerSC.append("　配賦指定：" + (Function.nvl(searchParam.getReportDistType(), "").equals("1") ? "配賦前" : "配賦後"));
			} else if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PL_SCH)) { // 賃借料・償却費・社内金利他スケジュール
				sqlName = "selectLldPlSchList_LLD";
				param.put("excludeCostSectionComWhere", "1"); // 共通検索条件の経費負担部課除外（明細の部課を使用するため）

				// 社内金利表示
				List<CodeName> list = masterDao.selectCodeNameList(Constants.CATEGORY_CODE_USE_COMPANY, companyCode, null, null, null, false);
				String kinriDisplay = "";
				if(list != null && list.size() > 0) {
					kinriDisplay = Function.nvl(list.get(0).getValue8(), "");
					if(kinriDisplay.equals(Constants.FLAG_YES)) {
						param.put("kinriDisplay", "1");
					}
				}
				// 控除額表示
				list = masterDao.selectCodeNameList(Constants.CATEGORY_CODE_PPFS_LLD_KOJOGK_TYPE, null, companyCode, null, null, true);
				if(list != null && list.size() > 0) {
					for(int i = 0; i < list.size(); i++) {
						String koujoDisplay = "";
						koujoDisplay = Function.nvl(list.get(i).getValue2(), "");
						if(koujoDisplay.equals(Constants.FLAG_YES)) {
							param.put("koujoDisplay" + (i+1), "1");
						}
					}
				}

				// CSVヘッダに基本条件をセット
				headerSC.append("　配賦指定：" + (Function.nvl(searchParam.getReportDistType(), "").equals("1") ? "配賦前" : "配賦後"));
			} else if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_BS_SCH)) { // 元本返済スケジュール
				sqlName = "selectLldBsSchList_LLD";
				param.put("excludeCostSectionComWhere", "1"); // 共通検索条件の経費負担部課除外（明細の部課を使用するため）
			} else if(Function.nvl(itemDef, "").startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_JOIN_SG)) { // 契約/物件-現物紐付リスト
				sqlName = "selectLldJoinSGList_LLD";
			}

			if(headerSC.length() > 0) headerRow.insert(0, headerSC.toString() + "\n"); // 基本検索条件を追加
			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) propList.toArray(new String[0]), (Format[]) propFormatList.toArray(new Format[0]));

			// 固定資産台帳出力指定有り
			if(Function.nvl(itemDef, "").endsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD)) {
				param.put("isOutputFld", "1");
				sqlName = sqlName.replaceAll("_LLD", "_FLD_LLD");
			}
			sqlMap.queryWithRowHandler(sqlName, param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * リース・レンタル契約情報_取得
	 * @param koyuno 固有番号
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 情報機器等
	 * @throws SQLException
	 */
	public PpfsLld selectLld(Long koyunoA ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("koyunoA", koyunoA);

		return (PpfsLld)sqlMap.queryForObject("selectLld_LLD", param);

	}
	/**
	 * リース・レンタル契約情報照会取得(物件と紐付かない契約)
	 * @param koyuno 固有番号
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return 情報機器等
	 * @throws SQLException
	 */
	public PpfsLld selectLldC(Long koyunoC ) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("koyunoC", koyunoC);

		return (PpfsLld)sqlMap.queryForObject("selectLldC_LLD", param);

	}


	/**
	 * リース・レンタル契約情報照会(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param sqlMap
	 * @return パラメータ
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getSelectLldListParam(String loginStaffCode, String accessLevel, String companyCode, PpfsLldSC  searchParam, SqlMapClient sqlMap) throws SQLException
	{
		MasterDAO masterDao = new MasterDAO();

		Map<String, Object> param = new HashMap<String, Object>();

		Integer currentYear = masterDao.getCurrentYear();						// カレント年度取得
		String isSubSearch = getSubSearchFlag(searchParam);	//	情報機器、ﾗｲｾﾝｽ

		//	会社コード設定
		param.put("companyCode", companyCode);

		// 検索対象：管轄分
		if(Function.nvl(searchParam.getSearchScopeType(), "").equals(Constants.SEARCH_SCOPE_TYPE_EDIT_ONLY)){
			//////////////////////////////// アクセスレベル
			if(Function.isAccessLevelGeneral(accessLevel)) {					// 一般権限
				searchParam.setUseStaffCode(loginStaffCode);
			} else if(Function.isAccessLevelSection(accessLevel)) {				// 部署権限
				// アクセス可能階層部署コード一覧取得
				List<String> sectionCodeList = masterDao.selectAccessibleSectionCodeList(companyCode, loginStaffCode, searchParam.getIncludeSectionHierarchyFlag());

				List<String> staffColumnList = new ArrayList<String>();			// 社員番号カラム
				staffColumnList.add("ptvla1.USE_STAFF_CODE");

				List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
				companyColumnList.add("ptvla1.HOL_COMPANY_CODE");
				companyColumnList.add("ptvla1.USE_COMPANY_CODE");

				List<String> sectionColumnList = new ArrayList<String>();		// 部署カラム
				sectionColumnList.add("ptvla1.HOL_SECTION_CODE");
				sectionColumnList.add("ptvla1.USE_SECTION_CODE");

				List<String> sectionYearColumnList = new ArrayList<String>();	// 部署年度カラム
				sectionYearColumnList.add("ptvla1.HOL_SECTION_YEAR");
				sectionYearColumnList.add("NVL(ptvla1.USE_SECTION_YEAR, " + currentYear + ")");

				param.put("accessLevelSection", Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, loginStaffCode, sectionCodeList, currentYear));
			}
			isSubSearch = Constants.FLAG_YES;
		}
		//	全ての契約？
		else{
			//	検索条件-契約:契約状態区分の満了済みもしくは、解約済みにチェックあり？（もしくは指定無し）
			if(Function.nvl(searchParam.getKykjotaikbnC(), "").equals("") || searchParam.getKykjotaikbnC().indexOf("2") > -1 || searchParam.getKykjotaikbnC().indexOf("4") > -1 || searchParam.getKykjotaikbnC().indexOf("5") > -1) {
				// 物件・現物に関するの検索条件の指定が無い
				if(
					// 物件
					Function.nvl(searchParam.getKaiykymdAFrom(), "").equals("")
					&& Function.nvl(searchParam.getKaiykymdATo(), "").equals("")
					&& Function.nvl(searchParam.getShuruicdA(), "").equals("")
					&& Function.nvl(searchParam.getShuruinmAPlural(), "").equals("")
					&& Function.nvl(searchParam.getBunruicdA(), "").equals("")
					&& Function.nvl(searchParam.getBunruinmAPlural(), "").equals("")
					&& Function.nvl(searchParam.getShisannmA(), "").equals("")
					&& Function.nvl(searchParam.getSetchicdA(), "").equals("")
					&& Function.nvl(searchParam.getSetchinmAPlural(), "").equals("")
					&& Function.nvl(searchParam.getItemGroupCodeA(), "").equals("")
					&& Function.nvl(searchParam.getItemGroupNameAPlural(), "").equals("")
					// 管理番号
					&& Function.nvl(searchParam.getKykeda(), "").equals("")
					&& Function.nvl(searchParam.getKykedaPlural(), "").equals("")
					&& Function.nvl(searchParam.getShisanNumA(), "").equals("")
					&& Function.nvl(searchParam.getShisanNumAPlural(), "").equals("")
					&& Function.nvl(searchParam.getAssetId(), "").equals("")
					&& Function.nvl(searchParam.getAssetIdPlural(), "").equals("")
					&& Function.nvl(searchParam.getLicenseId(), "").equals("")
					&& Function.nvl(searchParam.getLicenseIdPlural(), "").equals("")
					// 現物情報
					&& Function.nvl(searchParam.getHolSectionCode(), "").equals("")
					&& Function.nvl(searchParam.getHolStaffCode(), "").equals("")
					&& Function.nvl(searchParam.getHolOfficeCode(), "").equals("")
					&& Function.nvl(searchParam.getHolOfficePlural(), "").equals("")
					&& Function.nvl(searchParam.getHolOfficeFloor(), "").equals("")
					&& Function.nvl(searchParam.getUseStaffCode(), "").equals("")
					&& Function.nvl(searchParam.getAstSystemSectionDeployFlag(), "").equals("")
					&& Function.nvl(searchParam.getAstManageTypePlural(), "").equals("")
					// 経費負担
					&& Function.nvl(searchParam.getCostType(), "").equals("")
					&& Function.nvl(searchParam.getCostDepPrjCode(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode0(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode1(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode2(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode3(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode4(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode5(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode6(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode7(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode8(), "").equals("")
					&& Function.nvl(searchParam.getCostSectionCode9(), "").equals("")
					) {
					param.put("isContractOnly", "1");
				}
			}
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
			companyColumnList.add("ptvla1.HOL_COMPANY_CODE");
			companyColumnList.add("ptvla1.USE_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>();			// 部署カラム
			sectionColumnList.add("ptvla1.HOL_SECTION_CODE");
			sectionColumnList.add("ptvla1.USE_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>();		// 部署年度カラム
			sectionYearColumnList.add("ptvla1.HOL_SECTION_YEAR");
			sectionYearColumnList.add("ptvla1.USE_SECTION_YEAR");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, companyCode, null, sectionCodeList, currentYear));
			isSubSearch = Constants.FLAG_YES;
		}

		//////////////////////////////// 複数指定検索
		//	リース/レンタル判定区分
		List<String> leaseRentalPluralList = Function.getPluralList(searchParam.getLeaseRentalHantei());
		if(leaseRentalPluralList.size() > 0) {
			param.put("leaseRentalPluralList", Function.getInCondition("NEA_UTIL_PKG.GET_LEASE_RENTAL_HANTEI_F(ptvlc.LATORIHIKIKBN,ptvlc.BUNRUICD)", leaseRentalPluralList, true));
		}

		//	取引判定区分
		List<String> latorihikikbnCPluralList = Function.getPluralList(searchParam.getLatorihikikbnC());
		if(latorihikikbnCPluralList.size() > 0) {
			param.put("latorihikikbnCPluralList", Function.getInCondition("ptvlc.LATORIHIKIKBN", latorihikikbnCPluralList, true));
		}

		/************** 物件 ************/
		// 物件状態区分(契約区分との複合検索)
		List<String> kykkbnAPluralList = Function.getPluralList(searchParam.getKykkbnA());
		List<String> shisanjotaikbnAPluralList = Function.getPluralList(searchParam.getShisanjotaikbnA());
		if(shisanjotaikbnAPluralList.size() > 0) {
			StringBuffer sql = new StringBuffer();
			for(int i = 0; i < shisanjotaikbnAPluralList.size(); i++) {

				String shisanjotaikbnA = shisanjotaikbnAPluralList.get(i);

				if(!Function.nvl(shisanjotaikbnA, "").equals("")) {
					if(sql.length() > 0) sql.append(" OR ");
					sql.append("(");
					if(Function.nvl(shisanjotaikbnA, "").equals("1")) { // 通常
						if(kykkbnAPluralList.size() > 0) {
							sql.append(Function.getInCondition("ptvlc.KYKKBN", kykkbnAPluralList, true) + " AND ");
						}
						sql.append("ptvla.SHISANJOTAIKBN = '" + shisanjotaikbnA + "'");
					} else {
						sql.append("ptvla.SHISANJOTAIKBN = '" + shisanjotaikbnA + "'");
					}
					sql.append(")");
				}
			}

			param.put("shisanjotaikbnAPluralList", "(" + sql + ")");
		}
		// 種類
		List<String> shuruinmPluralList = Function.getPluralList(searchParam.getShuruinmAPlural());
		if(shuruinmPluralList.size() > 0) {
			param.put("shuruinmPluralList", Function.getInCondition("ptvla.SHURUICD", shuruinmPluralList, true));
		}
		// 分類
		List<String> bunruinmPluralList = Function.getPluralList(searchParam.getBunruinmAPlural());
		if(bunruinmPluralList.size() > 0) {
			param.put("bunruinmPluralList", Function.getInCondition("ptvla.BUNRUICD", bunruinmPluralList, true));
		}
		// 代表設置場所
		List<String> setchinmPluralList = Function.getPluralList(searchParam.getSetchinmAPlural());
		if(setchinmPluralList.size() > 0) {
			param.put("setchinmPluralList", Function.getInCondition("ptvla.SETCHICD", setchinmPluralList, true));
		}
		// 案件グループ
		List<String> itemGroupNamePluralList = Function.getPluralList(searchParam.getItemGroupNameAPlural());
		if(itemGroupNamePluralList.size() > 0) {
			param.put("itemGroupNamePluralList", Function.getInCondition("ptvla.NINI_LD_3CD", itemGroupNamePluralList, true));
		}
		/************** 契約 ************/
		//	契約状態区分(契約区分との複合検索)
		List<String> kykjotaikbnCPluralList = Function.getPluralList(searchParam.getKykjotaikbnC());
		List<String> kykkbnCPluralList = Function.getPluralList(searchParam.getKykkbnC());
		if(kykjotaikbnCPluralList.size() > 0) {
			StringBuffer sql = new StringBuffer();
			for(int i = 0; i < kykjotaikbnCPluralList.size(); i++) {

				String kykjotaikbnC = kykjotaikbnCPluralList.get(i);

				if(!Function.nvl(kykjotaikbnC, "").equals("")) {
					if(sql.length() > 0) sql.append(" OR ");
					sql.append("(");
					if(Function.nvl(kykjotaikbnC, "").equals("1") || Function.nvl(kykjotaikbnC, "").equals("3")) { // 通常/物件中途解約済
						if(kykkbnCPluralList.size() > 0) {
							sql.append(Function.getInCondition("ptvlc.KYKKBN", kykkbnCPluralList, true) + " AND ");
						}
						sql.append("ptvlc.KYKJOTAIKBN = '" + kykjotaikbnC + "'");
					} else {
						sql.append("ptvlc.KYKJOTAIKBN = '" + kykjotaikbnC + "'");
					}
					sql.append(")");
				}
			}

			param.put("kykjotaikbnCPluralList", "(" + sql + ")");
		}
		// B/S計上区分
		List<String> bskeijokbnCPluralList = Function.getPluralList(searchParam.getBskeijokbnC());
		if(bskeijokbnCPluralList.size() > 0) {
			param.put("bskeijokbnCPluralList", Function.getInCondition("ptvlc.BSKEIJOKBN", bskeijokbnCPluralList, true));
		}
		/************** 管理番号 ************/
		//	契約番号
		List<String> kyknoCPluralList = Function.getPluralList(searchParam.getKyknoCPlural());
		if(kyknoCPluralList.size() > 0) {
			param.put("kyknoCPluralList", Function.getInCondition("ptvlc.KYKNO", kyknoCPluralList, true));
		}
		//	契約枝番
		List<String> kykedaPluralList = Function.getPluralList(searchParam.getKykedaPlural());
		if(kykedaPluralList.size() > 0) {
			param.put("kykedaPluralList", Function.getInCondition("ptvla.NINI_LD_17CD", kykedaPluralList, true));
		}
		//	資産番号
		List<String> shisanNumAPluralList = Function.getPluralList(searchParam.getShisanNumAPlural());
		if(shisanNumAPluralList.size() > 0) {
			param.put("shisanNumAPluralList", Function.getInCondition("ptvla.OYA || ptvla.EDA", shisanNumAPluralList, true));
		}
		// 取得申請
		List<String> applicationIdPluralList = Function.getPluralList(searchParam.getApplicationIdPlural());
		if(applicationIdPluralList.size() > 0) {
			param.put("applicationIdPluralList", Function.getInCondition("ptvlc.NINI_LD_8CD", applicationIdPluralList, true));
		}
		// 情報機器管理番号
		List<String> assetIdPluralList = Function.getPluralList(searchParam.getAssetIdPlural());
		if(assetIdPluralList.size() > 0) {
			param.put("assetIdPluralList", Function.getInCondition("ptvla1.ASSET_ID", assetIdPluralList, true));
		}
		// ﾗｲｾﾝｽ管理番号
		List<String> licenseIdPluralList = Function.getPluralList(searchParam.getLicenseIdPlural());
		if(licenseIdPluralList.size() > 0) {
			param.put("licenseIdPluralList", Function.getInCondition("ptvla1.LICENSE_ID", licenseIdPluralList, true));
		}
		/************** 現物情報************/
		// 取得申請番号
		// 個別設置(使用)場所
		List<String> holOfficePluralList = Function.getPluralList(searchParam.getHolOfficePlural());
		if(holOfficePluralList.size() > 0) {
			param.put("holOfficePluralList", Function.getInCondition("ptvla1.HOL_OFFICE_CODE", holOfficePluralList, true));
		}
		// 管理区分
		List<String> astManageTypePluralList = Function.getPluralList(searchParam.getAstManageTypePlural());
		if(astManageTypePluralList.size() > 0) {
			param.put("astManageTypePluralList", Function.getInCondition("ptvla1.AST_MANAGE_TYPE", astManageTypePluralList, true));
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

		/************** 満了済の再リース元契約番号も検索 ************/
		if(Function.nvl(searchParam.getSearchOldKykNoFlag(), "").equals(Constants.FLAG_YES)){
			List<String> niniLd_20cdCPluralList = new ArrayList<String>();
			List<String> oldKykNoList = (List<String>)sqlMap.queryForList("selectLldCOldKykNoList_LLD", param);
			if(oldKykNoList != null && oldKykNoList.size()>0){
				for(int i = 0; i < oldKykNoList.size(); i++){
					String oldKykNo = oldKykNoList.get(i);
					//	再リース元契約番号が存在する場合、検索条件に追加
					if(!Function.nvl(oldKykNo, "").equals("")){
						niniLd_20cdCPluralList.add(oldKykNo);
					}
				}
			}
			if(niniLd_20cdCPluralList.size() > 0) {
				param.put("niniLd_20cdCPluralList", Function.getInConditionByTempTable("ptvlc.NINI_LD_20CD", niniLd_20cdCPluralList, masterDao));
			}
		}

		param.put("searchParam", searchParam);

		//	現物情報固有番号取得
		if(Function.nvl(isSubSearch, "").equals(Constants.FLAG_YES)){
			List<Long> subKoyunoList = sqlMap.queryForList("selectLldKoyuno_LLD", param);
			if(subKoyunoList.size() > 0){
				param.put("subKoyunoList", Function.getInConditionByTempTable("ptvla.KOYUNO", subKoyunoList, masterDao));
			}
			else{
				param.put("subKoyunoList", " 1 != 1 ");
			}
		}

		return param;
	}


	/**
	 * ProPlus物件更新チェック検索
	 * @param companyCode 会社コード
	 * @param loginStaffCode ログイン社員番号
	 * @param currentYear 年度
	 * @return ログイン社員関連所属部署一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<PpfsLldSR> selectPpfsLld(String companyCode, String koyunoAPlural ) throws SQLException{
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);
		//	固有番号
		List<String> koyunoAPluralList = Function.getPluralList(koyunoAPlural);
		if(koyunoAPluralList.size() > 0) {
			param.put("koyunoAPluralList", Function.getInCondition("KOYUNO", koyunoAPluralList, false));
		}

		return (List<PpfsLldSR>)sqlMap.queryForList("selectPpfsLld_LLD", param);
	}


	/**
	 * サブクエリ用検索条件チェック
	 * @param searchParam 検索条件
	 * @return ログイン社員関連所属部署一覧
	 * @throws SQLException
	 */
	private String getSubSearchFlag( PpfsLldSC searchParam ){
		String isFlag = "";
		//	サブクエリ用検索条件チェック(現物情報など入力されているかチェック)
		if( !Function.nvl(searchParam.getAssetId(), "").equals("")
		||  !Function.nvl(searchParam.getAssetIdPlural(), "").equals("")
		||  !Function.nvl(searchParam.getLicenseId(), "").equals("")
		||  !Function.nvl(searchParam.getLicenseIdPlural(), "").equals("")
		||  !Function.nvl(searchParam.getHolSectionCode(), "").equals("")
		||  !Function.nvl(searchParam.getHolStaffCode(), "").equals("")
		||  !Function.nvl(searchParam.getHolOfficeCode(), "").equals("")
		||  !Function.nvl(searchParam.getHolOfficeFloor(), "").equals("")
		||  !Function.nvl(searchParam.getHolOfficePlural(), "").equals("")
		||  !Function.nvl(searchParam.getUseStaffCode(), "").equals("")
		||  !Function.nvl(searchParam.getAstSystemSectionDeployFlag(), "").equals("")
		||  !Function.nvl(searchParam.getAstManageTypePlural(), "").equals("")
		){
			isFlag = Constants.FLAG_YES;
		}

		return isFlag;

	}


}
