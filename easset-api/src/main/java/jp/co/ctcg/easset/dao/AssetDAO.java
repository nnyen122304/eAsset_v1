/*===================================================================
 * ファイル名 : AssetDAO.java
 * 概要説明   : 情報機器等、情報機器等登録申請用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン       新規
 *=================================================================== */

package jp.co.ctcg.easset.dao;

import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.asset.Asset;
import jp.co.ctcg.easset.dto.asset.AssetLineComUsr;
import jp.co.ctcg.easset.dto.asset.AssetLineInv;
import jp.co.ctcg.easset.dto.asset.AssetLineNetwork;
import jp.co.ctcg.easset.dto.asset.AssetLineOs;
import jp.co.ctcg.easset.dto.asset.AssetSC;
import jp.co.ctcg.easset.dto.asset.AssetSR;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

import com.ibatis.sqlmap.client.SqlMapClient;


public class AssetDAO {

	private static final String TABLE_NAME_PREFIX_AP = "AP_";

	/**
	 * 情報機器等検索(ヘッダ)
	 * @param assetId 情報機器管理番号(登録申請番号)
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return 情報機器等
	 * @throws SQLException
	 */
	public Asset selectAsset(String assetId, boolean lockFlag, boolean isAp) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", "");

		return (Asset)sqlMap.queryForObject("selectAsset_AST", param);

	}

	/**
	 * 情報機器等_共有ユーザー明細
	 * @param assetId 情報機器管理番号(登録申請番号)
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<AssetLineComUsr> selectAssetLineComUsr(String assetId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		// 改行置換文字
		param.put("enterChar", "");

		return (List<AssetLineComUsr>)sqlMap.queryForList("selectAssetLineComUsr_AST", param);
	}

	/**
	 * 情報機器等_共有ユーザー明細
	 * @param assetId 情報機器管理番号(登録申請番号)
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<AssetLineOs> selectAssetLineOs(String assetId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		// 改行置換文字
		param.put("enterChar", "");

		return (List<AssetLineOs>)sqlMap.queryForList("selectAssetLineOs_AST", param);
	}

	/**
	 * 情報機器等_ネットワーク明細検索
	 * @param assetId 情報機器管理番号(登録申請番号)
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<AssetLineNetwork> selectAssetLineNetwork(String assetId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		// 改行置換文字
		param.put("enterChar", "");

		return (List<AssetLineNetwork>)sqlMap.queryForList("selectAssetLineNetwork_AST", param);
	}

	/**
	 * 情報機器等_棚卸明細検索
	 * @param assetId 情報機器管理番号(登録申請番号)
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<AssetLineInv> selectAssetLineInv(String assetId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		// 改行置換文字
		param.put("enterChar", "");

		return (List<AssetLineInv>)sqlMap.queryForList("selectAssetLineInv_AST", param);
	}

	/**
	 * 情報機器検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<AssetSR> selectAssetList(String loginStaffCode, String accessLevel, AssetSC searchParam, boolean isAp) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = getSelectAssetListParam(loginStaffCode, accessLevel, searchParam, isAp);
		String searchId = (String)param.get("searchId");

		// 改行置換文字
		param.put("enterChar", " ");

		try {
			List<AssetSR> assetList = (List<AssetSR>)sqlMap.queryForList("selectAssetList_AST", param);

			return assetList;
		} finally {
			if(!Function.nvl(searchId, "").equals("")) {
				Map<String, Object> deleteParam = new HashMap<String, Object>();
				deleteParam.put("searchId", searchId);
//				sqlMap.delete("deleteParentAssetId_AST", deleteParam);
			}
		}
	}

	/**
	 * 情報機器検索結果CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param searchParam    検索条件
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createAssetListCsv(String loginStaffCode, String accessLevel, List<String> outputPropList, AssetSC searchParam, boolean isAp) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		MasterDAO masterDao = new MasterDAO();
		String categoryCode = "";
		if(isAp){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_AP_ASSET;
		}
		else{
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_ASSET;
		}

		String downloadLineItem = searchParam.getDowloadLineItem();
		if(downloadLineItem != null && !downloadLineItem.equals("")){
			List<CodeName> lineList = masterDao.selectDownloadItemList(categoryCode, downloadLineItem);
			if(lineList != null){
				for(int i = 0; i < lineList.size(); i++){
					CodeName lineItem = (CodeName)lineList.get(i);
					outputPropList.add(lineItem.getValue3());
				}
			}
			if(isAp){
				searchParam.setDowloadLineItem(downloadLineItem.replaceAll("NEA_AP_ASSET_LINE_", ""));
			}
			else{
				searchParam.setDowloadLineItem(downloadLineItem.replaceAll("NEA_ASSET_LINE_", ""));
			}
		}

		Map<String, Object> param = getSelectAssetListParam(loginStaffCode, accessLevel, searchParam, isAp);
		String searchId = (String)param.get("searchId");

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し

		StringBuffer headerRow = new StringBuffer();
		List<String> propList = new ArrayList<String>();
		List<Format> propFormatList = new ArrayList<Format>();

		CsvWriterRowHandler handler = null;
		try {
			masterDao.setCsvDef(categoryCode, accessLevel, outputPropList, headerRow, propList, propFormatList);

			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) propList.toArray(new String[0]), (Format[]) propFormatList.toArray(new Format[0]));
			sqlMap.queryWithRowHandler("selectAssetList_AST", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ

			if(!Function.nvl(searchId, "").equals("")) {
				Map<String, Object> deleteParam = new HashMap<String, Object>();
				deleteParam.put("searchId", searchId);
//				sqlMap.delete("deleteParentAssetId_AST", deleteParam);
			}
		}
	}

	/**
	 * 情報機器検索(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return パラメータ
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getSelectAssetListParam(String loginStaffCode, String accessLevel, AssetSC searchParam, boolean isAp) throws SQLException {

		MasterDAO masterDao = new MasterDAO();
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		// 関連資産も同時に検索する
		if(Function.nvl(searchParam.getRelationShisanNumFlag(), "").equals(Constants.FLAG_YES)){
			if(!Function.nvl(searchParam.getShisanNum(), "").equals("") && searchParam.getShisanNum().length() > 15){
				searchParam.setShisanNum(searchParam.getShisanNum().substring(0, 15));
			}
		}
		param.put("searchParam", searchParam);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : ""));		// 登録申請の場合はテーブル名に接頭語を付加

		Integer currentYear = masterDao.getCurrentYear();						// カレント年度取得

		// 検索対象：管轄分
		if(Function.nvl(searchParam.getSearchScopeType(), "").equals(Constants.SEARCH_SCOPE_TYPE_EDIT_ONLY)){
			//////////////////////////////// アクセスレベル
			if(Function.isAccessLevelGeneral(accessLevel)) {					// 一般権限
				if(isAp) {
					searchParam.setApStaffCode(loginStaffCode);
				} else {
					searchParam.setUseStaffCode(loginStaffCode);
				}
			} else if(Function.isAccessLevelSection(accessLevel)) {				// 部署権限
				// アクセス可能階層部署コード一覧取得
				List<String> sectionCodeList = masterDao.selectAccessibleSectionCodeList(searchParam.getHolCompanyCodeALSection(), loginStaffCode, searchParam.getIncludeSectionHierarchyFlag());

				List<String> staffColumnList = new ArrayList<String>();			// 社員番号カラム
				if(isAp) {
					staffColumnList.add("nast.AP_STAFF_CODE");
					staffColumnList.add("nast.AP_CREATE_STAFF_CODE");
				} else {
					staffColumnList.add("nast.USE_STAFF_CODE");
				}

				List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
				companyColumnList.add("nast.HOL_COMPANY_CODE");
				companyColumnList.add("nast.USE_COMPANY_CODE");

				List<String> sectionColumnList = new ArrayList<String>();		// 部署カラム
				sectionColumnList.add("nast.HOL_SECTION_CODE");
				sectionColumnList.add("nast.USE_SECTION_CODE");

				List<String> sectionYearColumnList = new ArrayList<String>();	// 部署年度カラム
				sectionYearColumnList.add("nast.HOL_SECTION_YEAR");
				sectionYearColumnList.add("nast.USE_SECTION_YEAR");

				param.put("accessLevelSection", Function.getSectionCondition(staffColumnList, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCodeALSection(), loginStaffCode, sectionCodeList, currentYear));
			}
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
			companyColumnList.add("nast.HOL_COMPANY_CODE");
			companyColumnList.add("nast.USE_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>();			// 部署カラム
			sectionColumnList.add("nast.HOL_SECTION_CODE");
			sectionColumnList.add("nast.USE_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>();		// 部署年度カラム
			sectionYearColumnList.add("nast.HOL_SECTION_YEAR");
			sectionYearColumnList.add("nast.USE_SECTION_YEAR");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear));
		}

		//////////////////////////////// 複数指定検索
		// 情報機器管理番号
		List<String> assetIdPluralList = Function.getPluralList(searchParam.getAssetIdPlural());
		if(assetIdPluralList.size() > 0) {
			param.put("assetIdPluralList", Function.getInCondition("nast.ASSET_ID", assetIdPluralList, true));
		}
		// 親情報機器管理番号
		List<String> parentAssetIdPluralList = Function.getPluralList(searchParam.getParentAssetIdPlural());
		if(parentAssetIdPluralList.size() > 0) {
			param.put("parentAssetIdPluralList", Function.getInCondition("nast.PARENT_ASSET_ID", parentAssetIdPluralList, true));
		}
		// 取得申請書番号
		List<String> getApplicationIdPluralList = Function.getPluralList(searchParam.getGetApplicationIdPlural());
		if(getApplicationIdPluralList.size() > 0) {
			param.put("getApplicationIdPluralList", Function.getInCondition("nast.GET_APPLICATION_ID", getApplicationIdPluralList, true));
		}
		// 登録申請書番号
		List<String> registApplicationIdPluralList = Function.getPluralList(searchParam.getRegistApplicationIdPlural());
		if(registApplicationIdPluralList.size() > 0) {
			param.put("registApplicationIdPluralList", Function.getInCondition("nast.REGIST_APPLICATION_ID", registApplicationIdPluralList, true));
		}
		// 資産(機器)分類
		List<String> astCategory2PluralList = Function.getPluralList(searchParam.getAstCategory2Plural());
		if(astCategory2PluralList.size() > 0) {
			param.put("astCategoryPluralList", Function.getInCondition("nast.AST_CATEGORY2_CODE", astCategory2PluralList, true));
		}
		// シリアル番号
		List<String> astSerialCodePluralList = Function.getPluralList(searchParam.getAstSerialCodePlural());
		if(astSerialCodePluralList.size() > 0) {
			param.put("astSerialCodePluralList", Function.getInCondition("nast.AST_SERIAL_CODE", astSerialCodePluralList, true));
		}
		// OIR
		List<String> astOirPluralList = Function.getPluralList(searchParam.getAstOirPlural());
		if(astOirPluralList.size() > 0) {
			param.put("astOirPluralList", Function.getInCondition("nast.AST_OIR", astOirPluralList, true));
		}
		// 資産区分
		List<String> astAssetTypePluralList = Function.getPluralList(searchParam.getAstAssetTypePlural());
		if(astAssetTypePluralList.size() > 0) {
			param.put("astAssetTypePluralList", Function.getInCondition("nast.AST_ASSET_TYPE", astAssetTypePluralList, true));
		}
		// 管理区分
		List<String> astManageTypePluralList = Function.getPluralList(searchParam.getAstManageTypePlural());
		if(astManageTypePluralList.size() > 0) {
			param.put("astManageTypePluralList", Function.getInCondition("nast.AST_MANAGE_TYPE", astManageTypePluralList, true));
		}
		// 設置(使用)場所
		List<String> holOfficePluralList = Function.getPluralList(searchParam.getHolOfficePlural());
		if(holOfficePluralList.size() > 0) {
			param.put("holOfficePluralList", Function.getInCondition("nast.HOL_OFFICE_CODE", holOfficePluralList, true));
		}
		// ホスト名
		List<String> netHostNamePluralList = Function.getPluralList(searchParam.getNetHostNamePlural());
		if(netHostNamePluralList.size() > 0) {
			param.put("netHostNamePluralList", Function.getInCondition("nast.NET_HOST_NAME", netHostNamePluralList, true));
		}
		// 申請書ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("nast.AP_STATUS", apStatusPluralList, true));
		}
		// e申請書類ID
		List<String> eappIdPluralList = Function.getPluralList(searchParam.getEappIdPlural());
		if(eappIdPluralList.size() > 0) {
			param.put("eappIdPluralList", Function.getInCondition("nast.EAPP_ID", eappIdPluralList, false));
		}

		// 契約番号
		List<String> contractNumPluralList = Function.getPluralList(searchParam.getContractNumPlural());
		if(!Function.nvl(searchParam.getContractNum(), "").equals("") || contractNumPluralList.size() > 0) {
			if(Function.nvl(searchParam.getContractNum(), "").equals("")) { // 複数指定
				param.put("contractNumPluralList_pps", Function.getInCondition("l.KYKNO", contractNumPluralList, true));
				param.put("contractNumPluralList_ast", Function.getInCondition("a.CONTRACT_NUM", contractNumPluralList, true));
			}

			// 契約番号 -> 機器管理番号
			List<String> assetIdPluralByContractNumList = sqlMap.queryForList("selectAssetIdByContractNum_AST", param);
			if(assetIdPluralByContractNumList.size() > 0) {
				param.put("assetIdPluralByContractNumList", Function.getInConditionByTempTable("nast.ASSET_ID", assetIdPluralByContractNumList, masterDao));
			} else {
				param.put("assetIdPluralByContractNumList", " 1 != 1 "); // 検索結果無し
			}

		}

		// 契約枝番
		List<String> contractEdaPluralList = Function.getPluralList(searchParam.getContractEdaPlural());
		if(!Function.nvl(searchParam.getContractEda(), "").equals("") || contractEdaPluralList.size() > 0) {
			if(Function.nvl(searchParam.getContractEda(), "").equals("")) { // 複数指定
				param.put("contractEdaPluralList", Function.getInCondition("l.NINI_LD_17CD", contractEdaPluralList, true));
			}

			// 契約枝番 -> 機器管理番号
			List<String> assetIdPluralByContractEdaList = sqlMap.queryForList("selectAssetIdByContractEda_AST", param);
			if(assetIdPluralByContractEdaList.size() > 0) {
				param.put("assetIdPluralByContractEdaList", Function.getInConditionByTempTable("nast.ASSET_ID", assetIdPluralByContractEdaList, masterDao));
			} else {
				param.put("assetIdPluralByContractEdaList", " 1 != 1 "); // 検索結果無し
			}
		}

		// 資産番号
		List<String> shisanNumPluralList = Function.getPluralList(searchParam.getShisanNumPlural());
		if(!Function.nvl(searchParam.getShisanNum(), "").equals("") || shisanNumPluralList.size() > 0) {

			if(Function.nvl(searchParam.getShisanNum(), "").equals("")) { // 複数指定
				// 関連資産も同時に検索する
				if(Function.nvl(searchParam.getRelationShisanNumFlag(), "").equals(Constants.FLAG_YES)){
					for(int i = 0; i < shisanNumPluralList.size(); i++){
						String shisanNum = Function.nvl((String)shisanNumPluralList.get(i), "");
						if(shisanNum.length() > 15){
							shisanNumPluralList.set(i, shisanNum.substring(0, 15));
						}
					}
					param.put("shisanNumPluralList", Function.getInCondition("f.OYA", shisanNumPluralList, true));
				}
				else{
					param.put("shisanNumPluralList", Function.getInCondition("(f.OYA || f.EDA)", shisanNumPluralList, true));
				}
			}

			// 資産番号 -> 機器管理番号
			List<String> assetIdPluralByShisanNumList = sqlMap.queryForList("selectAssetIdByShisanNum_AST", param);
			if(assetIdPluralByShisanNumList.size() > 0) {
				param.put("assetIdPluralByShisanNumList", Function.getInConditionByTempTable("nast.ASSET_ID", assetIdPluralByShisanNumList, masterDao));
			} else {
				param.put("assetIdPluralByShisanNumList", " 1 != 1 "); // 検索結果無し
			}
		}

		//////////////////////////////// 複数あいまい検索
		// 備考
		List<String> dscDescriptionPluralList = Function.getPluralList(searchParam.getDscDescription());
		if(dscDescriptionPluralList.size() > 0) {
			param.put("dscDescriptionPluralList", Function.getFuzzyOrCondition("nast.DSC_DESCRIPTION", dscDescriptionPluralList));
		}
		// 管理項目
		List<String> dscAttributePluralList = Function.getPluralList(searchParam.getDscAttribute());
		if(dscAttributePluralList.size() > 0) {
			param.put("dscAttribute1PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE1", dscAttributePluralList));
			param.put("dscAttribute2PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE2", dscAttributePluralList));
			param.put("dscAttribute3PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE3", dscAttributePluralList));
			param.put("dscAttribute4PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE4", dscAttributePluralList));
			param.put("dscAttribute5PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE5", dscAttributePluralList));
			param.put("dscAttribute6PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE6", dscAttributePluralList));
			param.put("dscAttribute7PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE7", dscAttributePluralList));
			param.put("dscAttribute8PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE8", dscAttributePluralList));
			param.put("dscAttribute9PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE9", dscAttributePluralList));
			param.put("dscAttribute10PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE10", dscAttributePluralList));
			param.put("dscAttribute11PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE11", dscAttributePluralList));
			param.put("dscAttribute12PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE12", dscAttributePluralList));
			param.put("dscAttribute13PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE13", dscAttributePluralList));
			param.put("dscAttribute14PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE14", dscAttributePluralList));
			param.put("dscAttribute15PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE15", dscAttributePluralList));
			param.put("dscAttribute16PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE16", dscAttributePluralList));
			param.put("dscAttribute17PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE17", dscAttributePluralList));
			param.put("dscAttribute18PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE18", dscAttributePluralList));
			param.put("dscAttribute19PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE19", dscAttributePluralList));
			param.put("dscAttribute20PluralList", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE20", dscAttributePluralList));
		}
		List<String> dscAttribute1List = Function.getPluralList(searchParam.getDscAttribute1());
		if(dscAttribute1List.size() > 0) {
			param.put("dscAttribute1List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE1", dscAttribute1List));
		}
		List<String> dscAttribute2List = Function.getPluralList(searchParam.getDscAttribute2());
		if(dscAttribute2List.size() > 0) {
			param.put("dscAttribute2List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE2", dscAttribute2List));
		}
		List<String> dscAttribute3List = Function.getPluralList(searchParam.getDscAttribute3());
		if(dscAttribute3List.size() > 0) {
			param.put("dscAttribute3List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE3", dscAttribute3List));
		}
		List<String> dscAttribute4List = Function.getPluralList(searchParam.getDscAttribute4());
		if(dscAttribute4List.size() > 0) {
			param.put("dscAttribute4List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE4", dscAttribute4List));
		}
		List<String> dscAttribute5List = Function.getPluralList(searchParam.getDscAttribute5());
		if(dscAttribute5List.size() > 0) {
			param.put("dscAttribute5List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE5", dscAttribute5List));
		}
		List<String> dscAttribute6List = Function.getPluralList(searchParam.getDscAttribute6());
		if(dscAttribute6List.size() > 0) {
			param.put("dscAttribute6List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE6", dscAttribute6List));
		}
		List<String> dscAttribute7List = Function.getPluralList(searchParam.getDscAttribute7());
		if(dscAttribute7List.size() > 0) {
			param.put("dscAttribute7List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE7", dscAttribute7List));
		}
		List<String> dscAttribute8List = Function.getPluralList(searchParam.getDscAttribute8());
		if(dscAttribute8List.size() > 0) {
			param.put("dscAttribute8List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE8", dscAttribute8List));
		}
		List<String> dscAttribute9List = Function.getPluralList(searchParam.getDscAttribute9());
		if(dscAttribute9List.size() > 0) {
			param.put("dscAttribute9List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE9", dscAttribute9List));
		}
		List<String> dscAttribute10List = Function.getPluralList(searchParam.getDscAttribute10());
		if(dscAttribute10List.size() > 0) {
			param.put("dscAttribute10List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE10", dscAttribute10List));
		}
		List<String> dscAttribute11List = Function.getPluralList(searchParam.getDscAttribute11());
		if(dscAttribute11List.size() > 0) {
			param.put("dscAttribute11List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE11", dscAttribute11List));
		}
		List<String> dscAttribute12List = Function.getPluralList(searchParam.getDscAttribute12());
		if(dscAttribute12List.size() > 0) {
			param.put("dscAttribute12List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE12", dscAttribute12List));
		}
		List<String> dscAttribute13List = Function.getPluralList(searchParam.getDscAttribute13());
		if(dscAttribute13List.size() > 0) {
			param.put("dscAttribute13List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE13", dscAttribute13List));
		}
		List<String> dscAttribute14List = Function.getPluralList(searchParam.getDscAttribute14());
		if(dscAttribute14List.size() > 0) {
			param.put("dscAttribute14List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE14", dscAttribute14List));
		}
		List<String> dscAttribute15List = Function.getPluralList(searchParam.getDscAttribute15());
		if(dscAttribute15List.size() > 0) {
			param.put("dscAttribute15List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE15", dscAttribute15List));
		}
		List<String> dscAttribute16List = Function.getPluralList(searchParam.getDscAttribute16());
		if(dscAttribute16List.size() > 0) {
			param.put("dscAttribute16List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE16", dscAttribute16List));
		}
		List<String> dscAttribute17List = Function.getPluralList(searchParam.getDscAttribute17());
		if(dscAttribute17List.size() > 0) {
			param.put("dscAttribute17List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE17", dscAttribute17List));
		}
		List<String> dscAttribute18List = Function.getPluralList(searchParam.getDscAttribute18());
		if(dscAttribute18List.size() > 0) {
			param.put("dscAttribute18List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE18", dscAttribute18List));
		}
		List<String> dscAttribute19List = Function.getPluralList(searchParam.getDscAttribute19());
		if(dscAttribute19List.size() > 0) {
			param.put("dscAttribute19List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE19", dscAttribute19List));
		}
		List<String> dscAttribute20List = Function.getPluralList(searchParam.getDscAttribute20());
		if(dscAttribute20List.size() > 0) {
			param.put("dscAttribute20List", Function.getFuzzyOrCondition("nast.DSC_ATTRIBUTE20", dscAttribute20List));
		}

		// 関連(親子)データも同時に検索する
		if(Function.nvl(searchParam.getLevelAssetFlag(), "").equals(Constants.FLAG_YES)) {
			boolean mergeAssetIdFlag = false;

			/////// 条件検索実施
			// 改行置換文字
			param.put("enterChar", ""); // 指定無し
			param.put("isDownload", "1"); // 検索結果件数制限無し

			List<AssetSR> list = (List<AssetSR>)sqlMap.queryForList("selectAssetList_AST", param);
			Map<String, Object> insertParam = new HashMap<String, Object>();
			String searchId = loginStaffCode + Long.valueOf(new Date().getTime()).toString();

			/////// 関連データ検索
			boolean relAssetIdFlag = false;
			boolean parentAssetIdFlag = false;
			for(int i = 0; i < list.size();i++) {
				AssetSR row = list.get(i);

				String assetId = null;
				if(Function.nvl(row.getAssetId(), "").length() > 10) {
					assetId = row.getAssetId().substring(0, 11);
				}
				else {
					assetId = row.getAssetId();
				}

				String parentAssetId = null;
				if(Function.nvl(row.getParentAssetId(), "").length() > 10) {
					parentAssetId = row.getParentAssetId().substring(0, 11);
				}
				else {
					parentAssetId = row.getParentAssetId();
				}
				insertParam.clear();
				insertParam.put("searchId", searchId);
				insertParam.put("assetId", assetId);
				insertParam.put("relAssetId", assetId);
				insertParam.put("parentAssetId", parentAssetId);
				sqlMap.insert("insertParentAssetId_AST", insertParam);

				relAssetIdFlag = true;
				mergeAssetIdFlag = true;
				if(!Function.nvl(row.getParentAssetId(), "").equals("")) {
					parentAssetIdFlag = true;
				}
			}

			AssetSC sc = new AssetSC();
			sc.setDeleteFlag(searchParam.getDeleteFlag());

			Map<String, Object> param2 = new HashMap<String, Object>();
			param2.put("searchParam", sc);
			param2.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : ""));		// 登録申請の場合はテーブル名に接頭語を付加
			param2.put("enterChar", ""); // 指定無し
			param2.put("isDownload", "1"); // 検索結果件数制限無し

			// 検索結果機器の親データ検索
			if(parentAssetIdFlag) {
				String assetIdSearchSql1 = " nast.ASSET_ID IN ( ";
				assetIdSearchSql1 += " SELECT DISTINCT PARENT_ASSET_ID ";
				assetIdSearchSql1 +=   " FROM NEA_PARENT_ASSET_ID ";
				assetIdSearchSql1 +=  " WHERE PARENT_ASSET_ID IS NOT NULL ";
				assetIdSearchSql1 +=    " AND SEARCH_ID = '" + searchId + "') ";
				param2.put("assetIdPluralList", assetIdSearchSql1);
				param2.put("parentAssetIdPluralList", null);

				list = (List<AssetSR>)sqlMap.queryForList("selectAssetList_AST", param2);
				for(int i = 0; i < list.size();i++) {
					AssetSR row = list.get(i);

					String assetId = null;
					if(Function.nvl(row.getAssetId(), "").length() > 10) {
						assetId = row.getAssetId().substring(0, 11);
					}
					else {
						assetId = row.getAssetId();
					}
					insertParam.clear();
					insertParam.put("searchId", searchId);
					insertParam.put("assetId", assetId);
					insertParam.put("relAssetId", null);
					insertParam.put("parentAssetId", null);
					sqlMap.insert("insertParentAssetId_AST", insertParam);

					mergeAssetIdFlag = true;
				}
			}

			// 検索結果機器の子データ検索
			if(relAssetIdFlag) {
				param2.put("assetIdPluralList", null);
				String assetIdSearchSql2 = " nast.PARENT_ASSET_ID IN ( ";
				assetIdSearchSql2 += " SELECT DISTINCT REL_ASSET_ID ";
				assetIdSearchSql2 +=   " FROM NEA_PARENT_ASSET_ID ";
				assetIdSearchSql2 +=  " WHERE REL_ASSET_ID IS NOT NULL ";
				assetIdSearchSql2 +=    " AND SEARCH_ID = '" + searchId + "') ";
				param2.put("parentAssetIdPluralList", assetIdSearchSql2);

				list = (List<AssetSR>)sqlMap.queryForList("selectAssetList_AST", param2);
				for(int i = 0; i < list.size();i++) {
					AssetSR row = list.get(i);

					String assetId = null;
					if(Function.nvl(row.getAssetId(), "").length() > 10) {
						assetId = row.getAssetId().substring(0, 11);
					}
					else {
						assetId = row.getAssetId();
					}
					insertParam.clear();
					insertParam.put("searchId", searchId);
					insertParam.put("assetId", assetId);
					insertParam.put("relAssetId", null);
					insertParam.put("parentAssetId", null);
					sqlMap.insert("insertParentAssetId_AST", insertParam);

					mergeAssetIdFlag = true;
				}
			}

			if(!mergeAssetIdFlag) { // 結果が0件の場合
				insertParam.clear();
				insertParam.put("searchId", searchId);
				insertParam.put("assetId", "XXXXXXXXXX"); // 絶対に存在しない番号を検索条件に指定
				insertParam.put("relAssetId", null);
				insertParam.put("parentAssetId", null);
				sqlMap.insert("insertParentAssetId_AST", insertParam);
			}

			param.clear(); // 検索条件はここで取得したassetIdに限定
			param.put("searchParam", sc);
			param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : ""));		// 登録申請の場合はテーブル名に接頭語を付加
			String assetIdSearchSql3 = " nast.ASSET_ID IN ( ";
			assetIdSearchSql3 += " SELECT DISTINCT ASSET_ID ";
			assetIdSearchSql3 +=   " FROM NEA_PARENT_ASSET_ID ";
			assetIdSearchSql3 +=  " WHERE ASSET_ID IS NOT NULL ";
			assetIdSearchSql3 +=    " AND SEARCH_ID = '" + searchId + "') ";
			param.put("assetIdPluralList", assetIdSearchSql3);
			param.put("searchId", searchId);
		}

		return param;
	}

	/**
	 * 情報機器等ヘッダ作成
	 * @param obj データクラス
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void insertAsset(Asset obj, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.insert("insertAsset_AST", param);
	}

	/**
	 * 情報機器等_共有ユーザー明細作成
	 * @param obj データクラス
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void insertAssetLineComUsr(AssetLineComUsr obj, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.insert("insertAssetLineComUsr_AST", param);
	}

	/**
	 * 情報機器等_OS明細作成
	 * @param obj データクラス
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void insertAssetLineOs(AssetLineOs obj, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.insert("insertAssetLineOs_AST", param);
	}

	/**
	 * 情報機器等_ネットワーク明細作成
	 * @param obj データクラス
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void insertAssetLineNetwork(AssetLineNetwork obj, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.insert("insertAssetLineNetwork_AST", param);
	}

	/**
	 * 情報機器等_棚卸明細作成
	 * @param obj データクラス
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void insertAssetLineInv(AssetLineInv obj, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.insert("insertAssetLineInv_AST", param);
	}

	/**
	 * 情報機器等ヘッダ更新
	 * @param obj データクラス
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void updateAsset(Asset obj, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.update("updateAsset_AST", param);
	}

	/**
	 * 情報機器等ヘッダ削除
	 * @param assetId 情報機器番号
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void deleteAsset(String assetId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.delete("deleteAsset_AST", param);
	}

	/**
	 * 情報機器等_共有ユーザー明細削除
	 * @param assetId 情報機器番号
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void deleteAssetLineComUsr(String assetId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : ""));

		sqlMap.delete("deleteAssetLineComUsr_AST", param);
	}

	/**
	 * 情報機器等_OS明細削除
	 * @param assetId 情報機器番号
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void deleteAssetLineOs(String assetId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.delete("deleteAssetLineOs_AST", param);
	}

	/**
	 * 情報機器等_ネットワーク明細削除
	 * @param assetId 情報機器番号
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void deleteAssetLineNetwork(String assetId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.delete("deleteAssetLineNetwork_AST", param);
	}

	/**
	 * 情報機器等_棚卸明細削除
	 * @param assetId 情報機器番号
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return
	 * @throws SQLException
	 */
	public void deleteAssetLineInv(String assetId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("assetId", assetId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.delete("deleteAssetLineInv_AST", param);
	}

	/**
	 * 取得申請明細から登録申請データ取得
	 * @param apGetTanLineAstId 取得申請明細識別ID
	 * @return 登録申請データ
	 * @throws SQLException
	 */
	public Asset selectApAssetByApGetTan(Long apGetTanLineAstId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apGetTanLineAstId", apGetTanLineAstId);

		return (Asset)sqlMap.queryForObject("selectApAssetByApGetTan_AST", param);
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
	public CsvWriterRowHandler createAssetPossibleInputMasterList(String companyCode, String accessLevel, List<String> propertyList) throws SQLException, IOException {
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
			sqlMap.queryWithRowHandler("selectAssetPossibleInputMasterList_AST", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * ファイル・直入力可能マスタ値CSV作成(保有会社変更用)
	 * @param companyCode 会社コード
	 * @return CSVファイル識別ID
	 * @throws SQLException
	 * @throws IOException
	 */
	public CsvWriterRowHandler createAssetCompanyMoveInputMasterList(String companyCode) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("companyCode", companyCode);

		String headerRow = "項目,入力可能値,備考";
		String[] outputProps = new String[]{"categoryCode", "value1", "description"};
		Format[] outputFormats = new Format[]{null, null, null};

		CsvWriterRowHandler handler = null;
		try {
			handler = new CsvWriterRowHandler(headerRow, outputProps, outputFormats);
			sqlMap.queryWithRowHandler("selectAssetCompanyMoveInputMasterList_AST", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}


	/**
	 * 取得申請明細識別ID取得
	 * @param applicationId 取得申請書番号
	 * @param lineSeq 取得申請資産(機器)明細行番号
	 * @return 取得申請明細識別ID
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public Long selectApGetTanLineAstId(String applicationId, Integer lineSeq) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("lineSeq", lineSeq);

		Long ret = null;

		Map<Object, Object> map = (Map<Object, Object>) sqlMap.queryForObject("selectApGetTanLineAstId_AST", param);
		if(map != null) {
			Number val = (Number) map.get("AP_GET_TAN_LINE_AST_ID");
			if(val != null) ret = val.longValue();
		}
		return ret;
	}

	/**
	 * 取得申請明故障交換対象情報機器管理番号取得
	 * @param applicationId 取得申請書番号
	 * @param lineSeq 取得申請資産(機器)明細行番号
	 * @return 取得申請明細識別ID
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public String selectApGetTanFailureAssetId(String applicationId, Integer lineSeq) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("lineSeq", lineSeq);

		String ret = null;

		Map<Object, Object> map = (Map<Object, Object>) sqlMap.queryForObject("selectApGetTanLineAstId_AST", param);
		if(map != null) ret = (String) map.get("FAILURE_ASSET_ID");
		return ret;
	}

	/**
	 * 割当情報(機器から検索)-CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createAllocAssetListCsv(String loginStaffCode, String accessLevel, AssetSC searchParam) throws SQLException, IOException {

		// ヘッダ行
		String headerRow =
			"情報機器,,,,,,,,,,,ライセンス,,,,,,\n"
			+ "情報機器管理番号,HW保有会社,HW保有部署,HW使用会社,HW使用部署"
			+ ",資産管理担当者社員番号,資産管理担当者名,使用者社員番号,使用者名,管理区分"
			+ ",HW登録日,ライセンス管理番号,メーカー名,ソフトウェア名,SW保有会社"
			+ ",SW保有部署,許諾区分,許諾会社,許諾部署"
		;

		// 出力プロパティ
		String[] outputProps = new String[] {
			  "assetId", "holCompanyName", "holSectionName", "useCompanyName", "useSectionName"
			, "holStaffCode", "holStaffName", "useStaffCode", "useStaffName", "astManageTypeName"
			, "createDate", "licenseId", "softwareMakerName", "softwareName", "swHolCompanyName"
			, "swHolSectionName", "useTypeName", "licUseCompanyName", "licUseSectionName"
		};

		// プロパティフォーマット
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
		Format[] outputPropFormats = new Format[] {
				null, null, null, null, null
			  , null, null, null, null, null
			  , dateFormat, null, null, null, null
			  , null, null, null, null
		};

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = getSelectAssetListParam(loginStaffCode, accessLevel, searchParam, false);

		param.put("isAllocAsset", "1"); // 割当情報(機器から検索)のダウンロード

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し


		CsvWriterRowHandler handler = null;
		try {

			handler = new CsvWriterRowHandler(headerRow, outputProps, (Format[]) outputPropFormats);
			sqlMap.queryWithRowHandler("selectAssetList_AST", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 情報機器抹消
	 * @param updateStaffCode 更新者社員番号
	 * @param assetId 情報機器管理番号
	 * @param deleteDate 抹消日
	 * @param deleteReason 抹消理由
	 * @throws SQLException
	 */
	public void callDeleteAssetLogical(String updateStaffCode, String assetId, Date deleteDate, String deleteReason) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("updateStaffCode", updateStaffCode);
		param.put("assetId", assetId);
		param.put("deleteDate", deleteDate);
		param.put("deleteReason", deleteReason);

		sqlMap.update("callDeleteAssetLogical_AST", param);
	}

	/**
	 * 設定重複データの検索
	 * @param obj 情報機器データ
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return nullは重複なし。その他重複有で、重複項目に値が入っている
	 * @throws SQLException
	 */
	public AssetSR selectDuplicateAsset(Asset obj, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("obj", obj);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		return (AssetSR) sqlMap.queryForObject("selectDuplicateAsset_AST", param);
	}
}
