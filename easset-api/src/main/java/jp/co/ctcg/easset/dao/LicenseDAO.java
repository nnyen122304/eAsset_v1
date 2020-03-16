/*===================================================================
 * ファイル名 : LicenseDAO.java
 * 概要説明   : ライセンス、ライセンス登録申請用DAO定義
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
import jp.co.ctcg.easset.dto.license.ApLicenseLineAtt;
import jp.co.ctcg.easset.dto.license.LicenseAlloc;
import jp.co.ctcg.easset.dto.license.LicenseLineQty;
import jp.co.ctcg.easset.dto.license.LicenseLineUpg;
import jp.co.ctcg.easset.dto.license.Software;
import jp.co.ctcg.easset.dto.license.License;
import jp.co.ctcg.easset.dto.license.LicenseSC;
import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.dto.license.SoftwareMaker;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;

import com.ibatis.sqlmap.client.SqlMapClient;


public class LicenseDAO {

	private static final String TABLE_NAME_PREFIX_AP = "AP_";

	/**
	 * ライセンス取得(ヘッダ)
	 * @param licenseId ライセンス管理番号(登録申請番号)
	 * @param lockFlag    テーブルロックフラグ
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @return
	 * @throws SQLException
	 */
	public License selectLicense(String licenseId, boolean lockFlag, boolean isAp) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("licenseId", licenseId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加
		if(lockFlag) param.put("lockFlag", "1");

		// 改行置換文字
		param.put("enterChar", "");

		return (License)sqlMap.queryForObject("selectLicense_LIC", param);
	}

	/**
	 * ライセンス検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param isAp true:ライセンス登録申請,false:ライセンス等
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<LicenseSR> selectLicenseList(String loginStaffCode, String accessLevel, LicenseSC searchParam, boolean isAp) throws SQLException {

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = getSelectLicenseListParam(loginStaffCode, accessLevel, searchParam, isAp);

		// 改行置換文字
		param.put("enterChar", " ");

		return (List<LicenseSR>)sqlMap.queryForList("selectLicenseList_LIC", param);
	}

	/**
	 * ライセンス検索結果CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param searchParam    検索条件
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createLicenseListCsv(String loginStaffCode, String accessLevel, List<String> outputPropList, LicenseSC searchParam, boolean isAp) throws SQLException, IOException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		MasterDAO masterDao = new MasterDAO();
		String categoryCode = "";
		if(isAp){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_AP_LICENSE;
		}
		else{
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_LICENSE;
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
				searchParam.setDowloadLineItem(downloadLineItem.replaceAll("NEA_AP_LICENSE_LINE_", ""));
			}
			else{
				searchParam.setDowloadLineItem(downloadLineItem.replaceAll("NEA_LICENSE_LINE_", ""));
			}
		}

		Map<String, Object> param = getSelectLicenseListParam(loginStaffCode, accessLevel, searchParam, isAp);

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
			sqlMap.queryWithRowHandler("selectLicenseList_LIC", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * ライセンス検索(画面,CSV)共通パラメータ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @return パラメータ
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	private Map<String, Object> getSelectLicenseListParam(String loginStaffCode, String accessLevel, LicenseSC searchParam, boolean isAp) throws SQLException {

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
				searchParam.setApStaffCode(loginStaffCode);
			} else if(Function.isAccessLevelSection(accessLevel)) {				// 部署権限
				// アクセス可能階層部署コード一覧取得
				List<String> sectionCodeList = masterDao.selectAccessibleSectionCodeList(searchParam.getHolCompanyCodeALSection(), loginStaffCode, searchParam.getIncludeSectionHierarchyFlag());

				List<String> staffColumnList = new ArrayList<String>();			// 社員番号カラム
				staffColumnList.add("nlcs.AP_STAFF_CODE");

				List<String> companyColumnList = new ArrayList<String>();			// 会社カラム
				companyColumnList.add("nlcs.HOL_COMPANY_CODE");
				companyColumnList.add("nlcs.USE_COMPANY_CODE");

				List<String> sectionColumnList = new ArrayList<String>();		// 部署カラム
				sectionColumnList.add("nlcs.HOL_SECTION_CODE");
				sectionColumnList.add("nlcs.USE_SECTION_CODE");

				List<String> sectionYearColumnList = new ArrayList<String>();	// 部署年度カラム
				sectionYearColumnList.add("nlcs.HOL_SECTION_YEAR");
				sectionYearColumnList.add("nlcs.USE_SECTION_YEAR");
//				sectionYearColumnList.add("NVL(nlcs.USE_SECTION_YEAR, " + currentYear + ")");

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
			companyColumnList.add("nlcs.HOL_COMPANY_CODE");
			companyColumnList.add("nlcs.USE_COMPANY_CODE");

			List<String> sectionColumnList = new ArrayList<String>();			// 部署カラム
			sectionColumnList.add("nlcs.HOL_SECTION_CODE");
			sectionColumnList.add("nlcs.USE_SECTION_CODE");

			List<String> sectionYearColumnList = new ArrayList<String>();		// 部署年度カラム
			sectionYearColumnList.add("nlcs.HOL_SECTION_YEAR");
			sectionYearColumnList.add("nlcs.USE_SECTION_YEAR");
//			sectionYearColumnList.add("NVL(nlcs.USE_SECTION_YEAR, " + currentYear + ")");

			param.put("section", Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, searchParam.getHolCompanyCode(), null, sectionCodeList, currentYear));
		}

		//////////////////////////////// 複数指定検索
		// ライセンス管理番号
		List<String> licenseIdPluralList = Function.getPluralList(searchParam.getLicenseIdPlural());
		if(licenseIdPluralList.size() > 0) {
			param.put("licenseIdPluralList", Function.getInCondition("nlcs.LICENSE_ID", licenseIdPluralList, true));
		}
		// 割当情報機器管理番号
		List<String> assetIdPluralList = Function.getPluralList(searchParam.getAssetIdPlural());
		if(assetIdPluralList.size() > 0) {
			param.put("assetIdPluralList", Function.getInCondition("nlla_sub.ASSET_ID", assetIdPluralList, true));
		}
		// 取得申請書番号
		List<String> getApplicationIdPluralList = Function.getPluralList(searchParam.getGetApplicationIdPlural());
		if(getApplicationIdPluralList.size() > 0) {
			param.put("getApplicationIdPluralList", Function.getInCondition("nlcs.GET_APPLICATION_ID", getApplicationIdPluralList, true));
		}
		// 登録申請書番号
		List<String> registApplicationIdPluralList = Function.getPluralList(searchParam.getRegistApplicationIdPlural());
		if(registApplicationIdPluralList.size() > 0) {
			param.put("registApplicationIdPluralList", Function.getInCondition("nlcs.REGIST_APPLICATION_ID", registApplicationIdPluralList, true));
		}
		// シリアル番号
		List<String> licSerialCodePluralList = Function.getPluralList(searchParam.getLicSerialCodePlural());
		if(licSerialCodePluralList.size() > 0) {
			param.put("licSerialCodePluralList", Function.getInCondition("nlcs.LIC_SERIAL_CODE", licSerialCodePluralList, true));
		}
		// プロダクトKEY
		List<String> licProductKeyPluralList = Function.getPluralList(searchParam.getLicProductKeyPlural());
		if(licProductKeyPluralList.size() > 0) {
			param.put("licProductKeyPluralList", Function.getInCondition("nlcs.LIC_PRODUCT_KEY", licProductKeyPluralList, true));
		}
		// ライセンス形態
		List<String> licLicenseTypePluralList = Function.getPluralList(searchParam.getLicLicenseTypePlural());
		if(licLicenseTypePluralList.size() > 0) {
			param.put("licLicenseTypePluralList", Function.getInCondition("nlcs.LIC_LICENSE_TYPE", licLicenseTypePluralList, true));
		}
		// 申請書ステータス
		List<String> apStatusPluralList = Function.getPluralList(searchParam.getApStatus());
		if(apStatusPluralList.size() > 0) {
			param.put("apStatusPluralList", Function.getInCondition("nlcs.AP_STATUS", apStatusPluralList, true));
		}
		// e申請書類ID
		List<String> eappIdPluralList = Function.getPluralList(searchParam.getEappIdPlural());
		if(eappIdPluralList.size() > 0) {
			param.put("eappIdPluralList", Function.getInCondition("nlcs.EAPP_ID", eappIdPluralList, false));
		}


		// 契約番号
		List<String> contractNumPluralList = Function.getPluralList(searchParam.getContractNumPlural());
		if(!Function.nvl(searchParam.getContractNum(), "").equals("") || contractNumPluralList.size() > 0) {
			if(Function.nvl(searchParam.getContractNum(), "").equals("")) { // 複数指定
				param.put("contractNumPluralList", Function.getInCondition("l.KYKNO", contractNumPluralList, true));
			}

			// 契約番号 -> ライセンス管理番号
			List<String> licenseIdPluralByContractNumList = sqlMap.queryForList("selectLicenseIdByContractNum_LIC", param);
			if(licenseIdPluralByContractNumList.size() > 0) {
				param.put("licenseIdPluralByContractNumList", Function.getInConditionByTempTable("nlcs.LICENSE_ID", licenseIdPluralByContractNumList, masterDao));
			} else {
				param.put("licenseIdPluralByContractNumList", " 1 != 1 "); // 検索結果無し
			}
		}

		// 契約枝番
		List<String> contractEdaPluralList = Function.getPluralList(searchParam.getContractEdaPlural());
		if(!Function.nvl(searchParam.getContractEda(), "").equals("") || contractEdaPluralList.size() > 0) {
			if(Function.nvl(searchParam.getContractEda(), "").equals("")) { // 複数指定
				param.put("contractEdaPluralList", Function.getInCondition("l.NINI_LD_17CD", contractEdaPluralList, true));
			}

			// 契約枝番 -> ライセンス管理番号
			List<String> licenseIdPluralByContractEdaList = sqlMap.queryForList("selectLicenseIdByContractEda_LIC", param);
			if(licenseIdPluralByContractEdaList.size() > 0) {
				param.put("licenseIdPluralByContractEdaList", Function.getInConditionByTempTable("nlcs.LICENSE_ID", licenseIdPluralByContractEdaList, masterDao));
			} else {
				param.put("licenseIdPluralByContractEdaList", " 1 != 1 "); // 検索結果無し
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

			// 資産番号 -> ライセンス管理番号
			List<String> licenseIdPluralByShisanNumList = sqlMap.queryForList("selectLicenseIdByShisanNum_LIC", param);
			if(licenseIdPluralByShisanNumList.size() > 0) {
				param.put("licenseIdPluralByShisanNumList", Function.getInConditionByTempTable("nlcs.LICENSE_ID", licenseIdPluralByShisanNumList, masterDao));
			} else {
				param.put("licenseIdPluralByShisanNumList", " 1 != 1 "); // 検索結果無し
			}
		}

		//////////////////////////////// 複数あいまい検索
		// 備考
		List<String> dscDescriptionPluralList = Function.getPluralList(searchParam.getDscDescription());
		if(dscDescriptionPluralList.size() > 0) {
			param.put("dscDescriptionPluralList", Function.getFuzzyOrCondition("nlcs.DSC_DESCRIPTION", dscDescriptionPluralList));
		}
		// 管理項目
		List<String> dscAttributePluralList = Function.getPluralList(searchParam.getDscAttribute());
		if(dscAttributePluralList.size() > 0) {
			param.put("dscAttribute1PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE1", dscAttributePluralList));
			param.put("dscAttribute2PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE2", dscAttributePluralList));
			param.put("dscAttribute3PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE3", dscAttributePluralList));
			param.put("dscAttribute4PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE4", dscAttributePluralList));
			param.put("dscAttribute5PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE5", dscAttributePluralList));
			param.put("dscAttribute6PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE6", dscAttributePluralList));
			param.put("dscAttribute7PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE7", dscAttributePluralList));
			param.put("dscAttribute8PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE8", dscAttributePluralList));
			param.put("dscAttribute9PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE9", dscAttributePluralList));
			param.put("dscAttribute10PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE10", dscAttributePluralList));
			param.put("dscAttribute11PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE11", dscAttributePluralList));
			param.put("dscAttribute12PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE12", dscAttributePluralList));
			param.put("dscAttribute13PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE13", dscAttributePluralList));
			param.put("dscAttribute14PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE14", dscAttributePluralList));
			param.put("dscAttribute15PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE15", dscAttributePluralList));
			param.put("dscAttribute16PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE16", dscAttributePluralList));
			param.put("dscAttribute17PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE17", dscAttributePluralList));
			param.put("dscAttribute18PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE18", dscAttributePluralList));
			param.put("dscAttribute19PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE19", dscAttributePluralList));
			param.put("dscAttribute20PluralList", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE20", dscAttributePluralList));
		}
		List<String> dscAttribute1List = Function.getPluralList(searchParam.getDscAttribute1());
		if(dscAttribute1List.size() > 0) {
			param.put("dscAttribute1List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE1", dscAttribute1List));
		}
		List<String> dscAttribute2List = Function.getPluralList(searchParam.getDscAttribute2());
		if(dscAttribute2List.size() > 0) {
			param.put("dscAttribute2List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE2", dscAttribute2List));
		}
		List<String> dscAttribute3List = Function.getPluralList(searchParam.getDscAttribute3());
		if(dscAttribute3List.size() > 0) {
			param.put("dscAttribute3List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE3", dscAttribute3List));
		}
		List<String> dscAttribute4List = Function.getPluralList(searchParam.getDscAttribute4());
		if(dscAttribute4List.size() > 0) {
			param.put("dscAttribute4List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE4", dscAttribute4List));
		}
		List<String> dscAttribute5List = Function.getPluralList(searchParam.getDscAttribute5());
		if(dscAttribute5List.size() > 0) {
			param.put("dscAttribute5List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE5", dscAttribute5List));
		}
		List<String> dscAttribute6List = Function.getPluralList(searchParam.getDscAttribute6());
		if(dscAttribute6List.size() > 0) {
			param.put("dscAttribute6List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE6", dscAttribute6List));
		}
		List<String> dscAttribute7List = Function.getPluralList(searchParam.getDscAttribute7());
		if(dscAttribute7List.size() > 0) {
			param.put("dscAttribute7List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE7", dscAttribute7List));
		}
		List<String> dscAttribute8List = Function.getPluralList(searchParam.getDscAttribute8());
		if(dscAttribute8List.size() > 0) {
			param.put("dscAttribute8List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE8", dscAttribute8List));
		}
		List<String> dscAttribute9List = Function.getPluralList(searchParam.getDscAttribute9());
		if(dscAttribute9List.size() > 0) {
			param.put("dscAttribute9List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE9", dscAttribute9List));
		}
		List<String> dscAttribute10List = Function.getPluralList(searchParam.getDscAttribute10());
		if(dscAttribute10List.size() > 0) {
			param.put("dscAttribute10List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE10", dscAttribute10List));
		}
		List<String> dscAttribute11List = Function.getPluralList(searchParam.getDscAttribute11());
		if(dscAttribute11List.size() > 0) {
			param.put("dscAttribute11List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE11", dscAttribute11List));
		}
		List<String> dscAttribute12List = Function.getPluralList(searchParam.getDscAttribute12());
		if(dscAttribute12List.size() > 0) {
			param.put("dscAttribute12List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE12", dscAttribute12List));
		}
		List<String> dscAttribute13List = Function.getPluralList(searchParam.getDscAttribute13());
		if(dscAttribute13List.size() > 0) {
			param.put("dscAttribute13List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE13", dscAttribute13List));
		}
		List<String> dscAttribute14List = Function.getPluralList(searchParam.getDscAttribute14());
		if(dscAttribute14List.size() > 0) {
			param.put("dscAttribute14List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE14", dscAttribute14List));
		}
		List<String> dscAttribute15List = Function.getPluralList(searchParam.getDscAttribute15());
		if(dscAttribute15List.size() > 0) {
			param.put("dscAttribute15List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE15", dscAttribute15List));
		}
		List<String> dscAttribute16List = Function.getPluralList(searchParam.getDscAttribute16());
		if(dscAttribute16List.size() > 0) {
			param.put("dscAttribute16List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE16", dscAttribute16List));
		}
		List<String> dscAttribute17List = Function.getPluralList(searchParam.getDscAttribute17());
		if(dscAttribute17List.size() > 0) {
			param.put("dscAttribute17List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE17", dscAttribute17List));
		}
		List<String> dscAttribute18List = Function.getPluralList(searchParam.getDscAttribute18());
		if(dscAttribute18List.size() > 0) {
			param.put("dscAttribute18List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE18", dscAttribute18List));
		}
		List<String> dscAttribute19List = Function.getPluralList(searchParam.getDscAttribute19());
		if(dscAttribute19List.size() > 0) {
			param.put("dscAttribute19List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE19", dscAttribute19List));
		}
		List<String> dscAttribute20List = Function.getPluralList(searchParam.getDscAttribute20());
		if(dscAttribute20List.size() > 0) {
			param.put("dscAttribute20List", Function.getFuzzyOrCondition("nlcs.DSC_ATTRIBUTE20", dscAttribute20List));
		}

		return param;
	}

	/**
	 * ライセンスヘッダ作成
	 * @param obj データクラス
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @return
	 * @throws SQLException
	 */
	public void insertLicense(License obj, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.insert("insertLicense_LIC", param);
	}

	/**
	 * ライセンス_貸出明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertLicenseLineQty(LicenseLineQty obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertLicenseLineQty_LIC", param);
	}

	/**
	 * ライセンス_アップグレード明細作成
	 * @param obj データクラス
	 * @return
	 * @throws SQLException
	 */
	public void insertLicenseLineUpg(LicenseLineUpg obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertLicenseLineUpg_LIC", param);
	}

	/**
	 * ライセンスヘッダ更新
	 * @param obj データクラス
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @return
	 * @throws SQLException
	 */
	public void updateLicense(License obj, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.update("updateLicense_LIC", param);
	}

	/**
	 * ライセンスヘッダ削除
	 * @param licenseId ライセンス番号
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @return
	 * @throws SQLException
	 */
	public void deleteLicense(String licenseId, boolean isAp) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("licenseId", licenseId);
		param.put("tableNamePrefix", (isAp ? TABLE_NAME_PREFIX_AP : "")); // 登録申請の場合はテーブル名に接頭語を付加

		sqlMap.delete("deleteLicense_LIC", param);
	}

	/**
	 * ライセンス_貸出明細削除
	 * @param licenseId ライセンス番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteLicenseLineQty(String licenseId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("licenseId", licenseId);

		sqlMap.delete("deleteLicenseLineQty_LIC", param);
	}

	/**
	 * ライセンス_アップグレード明細削除
	 * @param licenseId ライセンス管理番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteLicenseLineUpg(String licenseId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("licenseId", licenseId);

		sqlMap.delete("deleteLicenseLineUpg_LIC", param);
	}

	/**
	 * 取得申請(有形)明細から登録申請データ取得
	 * @param apGetTanLineLicId 取得申請明細識別ID
	 * @return 登録申請データ
	 * @throws SQLException
	 */
	public License selectApLicenseByApGetTan(Long apGetTanLineLicId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("apGetTanLineLicId", apGetTanLineLicId);

		return (License)sqlMap.queryForObject("selectApLicenseByApGetTan_LIC", param);
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
	public CsvWriterRowHandler createLicensePossibleInputMasterList(String companyCode, String accessLevel, List<String> propertyList) throws SQLException, IOException {
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
			sqlMap.queryWithRowHandler("selectLicensePossibleInputMasterList_LIC", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * ファイル・直入力可能マスタ値CSV作成(保有会社変更用)
	 * @param companyCode 会社コード
	 * @param accessLevel アクセスレベル
	 * @return CSVファイル識別ID
	 * @throws SQLException
	 * @throws IOException
	 */
	public CsvWriterRowHandler createLicenseCompanyMoveInputMasterList(String companyCode, String accessLevel) throws SQLException, IOException {
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
			sqlMap.queryWithRowHandler("selectLicenseCompanyMoveInputMasterList_LIC", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * 取得申請(有形)明細識別ID取得
	 * @param applicationId 取得申請書番号
	 * @param lineSeq ライセンス明細行番号
	 * @return 取得申請明細識別ID
	 * @throws SQLException
	 */
	public Long selectApGetTanLineLicId(String applicationId, Integer lineSeq) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("applicationId", applicationId);
		param.put("lineSeq", lineSeq);

		return (Long)sqlMap.queryForObject("selectApGetTanLineLicId_LIC", param);
	}

	/**
	 * ソフトウェアメーカー検索
	 * @param softwareMakerName ソフトウェアメーカー名(一致)
	 * @param softwareMakerNameFuzzy ソフトウェアメーカー名(あいまい)
	 * @param enableOnly true:有効データのみ、false:有効、無効データの両方
	 * @param lockFlag 行ロックフラグ true:ロックする、false:ロックしない
	 * @return ソフトウェアメーカー一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<SoftwareMaker> selectSoftwareMakerList(String softwareMakerName, String softwareMakerNameFuzzy, boolean enableOnly, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("softwareMakerNameCheck", softwareMakerName);
		sqlParam.put("softwareMakerNameFuzzy", softwareMakerNameFuzzy);
		if(!enableOnly) sqlParam.put("enableOnly", "0");
		if(lockFlag) sqlParam.put("lockFlag", "1");

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<SoftwareMaker>)sqlMap.queryForList("selectSoftwareMakerList_LIC", sqlParam);
	}

	/**
	 * ソフトウェア検索
	 * @param softwareMakerId ソフトウェアメーカーID
	 * @param softwareName ソフトウェア名(一致)
	 * @param softwareNameFuzzy ソフトウェア名(あいまい)
	 * @param enableOnly true:有効データのみ、false:有効、無効データの両方
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return ソフトウェア一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Software> selectSoftwareList(Long softwareMakerId, String softwareName, String softwareNameFuzzy, boolean enableOnly, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("softwareMakerId", softwareMakerId);
		sqlParam.put("softwareNameCheck", softwareName);
		sqlParam.put("softwareNameFuzzy", softwareNameFuzzy);
		if(!enableOnly) sqlParam.put("enableOnly", "0");
		if(lockFlag) sqlParam.put("lockFlag", "1");

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<Software>)sqlMap.queryForList("selectSoftwareList_LIC", sqlParam);
	}

	/**
	 * ソフトウェアメーカー作成
	 * @param staffCode ログイン社員番号
	 * 		  softwareMaker ソフトウェアメーカーエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public Long insertSoftwareMaker(String staffCode, SoftwareMaker softwareMaker) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("obj", softwareMaker);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (Long) sqlMap.insert("insertSoftwareMaker_LIC", sqlParam);
	}

	/**
	 * ソフトウェアメーカーアップデート
	 * @param staffCode	ソフトウェアメーカーID
	 * @param softwareMaker	ソフトウェアメーカーエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void updateSoftwareMaker(String staffCode, SoftwareMaker softwareMaker) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("staffCode", staffCode);
		sqlParam.put("obj", softwareMaker);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("updateSoftwareMaker_LIC", sqlParam);
	}

	/**
	 * ソフトウェアメーカー作成
	 * @param staffCode ログイン社員番号
	 * 		  softwareMaker ソフトウェアメーカーエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public Long insertSoftware(String staffCode, Software software) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("obj", software);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (Long) sqlMap.insert("insertSoftware_LIC", sqlParam);
	}

	/**
	 * ソフトウェアアップデート
	 * @param staffCode	ログイン社員番号
	 * @param software	ソフトウェアエンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void updateSoftware(String staffCode, Software software) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("staffCode", staffCode);
		sqlParam.put("obj", software);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("updateSoftware_LIC", sqlParam);
	}

	/**
	 * ソフトウェアメーカー検索_同一名（もしくは類似名）の存在チェック
	 * @param softwareMakerNameCheck ソフトウェアメーカー名
	 * @param enableOnly true:有効データのみ、false:有効、無効データの両方
	 * @param lockFlag 行ロックフラグ true:ロックする、false:ロックしない
	 * @return ソフトウェアメーカー一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<SoftwareMaker> selectSoftwareMakerCheckList(String softwareMakerNameCheck, boolean enableOnly, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("softwareMakerNameCheck", softwareMakerNameCheck);
		if(!enableOnly) sqlParam.put("enableOnly", "0");
		if(lockFlag) sqlParam.put("lockFlag", "1");

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<SoftwareMaker>)sqlMap.queryForList("selectSoftwareMakerList_LIC", sqlParam);
	}

	/**
	 * ソフトウェア検索_同一名（もしくは類似名）の存在チェック
	 * @param softwareMakerId ソフトウェアメーカーID
	 * @param softwareNameCheck ソフトウェア名
	 * @param enableOnly true:有効データのみ、false:有効、無効データの両方
	 * @param lockFlag データロックフラグ true:ロックする false:ロックしない
	 * @return ソフトウェア一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Software> selectSoftwareListCheckList(Long softwareMakerId, String softwareNameCheck, boolean enableOnly, boolean lockFlag) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("softwareMakerId", softwareMakerId);
		sqlParam.put("softwareNameCheck", softwareNameCheck);
		if(!enableOnly) sqlParam.put("enableOnly", "0");
		if(lockFlag) sqlParam.put("lockFlag", "1");

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<Software>)sqlMap.queryForList("selectSoftwareList_LIC", sqlParam);
	}

	/**
	 * 割当情報(ライセンスから検索)-CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createAllocLicenseListCsv(String loginStaffCode, String accessLevel, LicenseSC searchParam) throws SQLException, IOException {

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
			  "assetId", "hwHolCompanyName", "hwHolSectionName", "astUseCompanyName", "astUseSectionName"
			, "astHolStaffCode", "astHolStaffName", "useStaffCode", "useStaffName", "astManageTypeName"
			, "astCreateDate", "licenseId", "softwareMakerName", "softwareName", "holCompanyName"
			, "holSectionName", "useTypeName", "useCompanyName", "useSectionName"
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

		Map<String, Object> param = getSelectLicenseListParam(loginStaffCode, accessLevel, searchParam, false);

		param.put("isAllocLicense", "1"); // 割当情報(ライセンスから検索)のダウンロード

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し


		CsvWriterRowHandler handler = null;
		try {

			handler = new CsvWriterRowHandler(headerRow, outputProps, (Format[]) outputPropFormats);
			sqlMap.queryWithRowHandler("selectLicenseList_LIC", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/**
	 * アップグレード情報-CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 * @throws IOException
	 */
 	public CsvWriterRowHandler createUpgradeListCsv(String loginStaffCode, String accessLevel, LicenseSC searchParam) throws SQLException, IOException {

		// ヘッダ行
		String headerRow =
			"割当元ライセンス,,,,,,,,,,割当先ライセンス,,,,,,,,,割当\n"
			+ "ライセンス管理番号,メーカ名,ソフトウェア名,ベース/アップグレード,シリアル番号,プロダクトＫＥＹ,ライセンス数制限,保有数,有効数,アップグレード済数"
			+ ",ライセンス管理番号,メーカ名,ソフトウェア名,シリアル番号,プロダクトＫＥＹ,ライセンス数制限,保有数,有効数,アップグレード済数"
			+ ",アップグレード割当数"
		;

		// 出力プロパティ
		String[] outputProps = new String[] {
			  "licenseId", "softwareMakerName", "softwareName", "licUpgradeFlagName", "licSerialCode", "licProductKey", "licQuantityTypeName", "licQuantity", "licEnableQuantity", "licUpgradeToQuantity"
			, "upLicenseId", "upSoftwareMakerName", "upSoftwareName", "upLicSerialCode", "upLicProductKey", "upLicQuantityTypeName", "upLicQuantity", "upLicEnableQuantity", "upLicUpgradeToQuantity"
			, "upLicUpgradeQuantity"
		};

		// プロパティフォーマット
		Format[] outputPropFormats = new Format[] {
				null, null, null, null, null, null, null, null, null, null
			  , null, null, null, null, null, null, null, null, null
			  , null
		};

		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = getSelectLicenseListParam(loginStaffCode, accessLevel, searchParam, false);

		param.put("isAllocLicense", "1"); // 割当情報(ライセンスから検索)のダウンロード

		// 改行置換文字
		param.put("enterChar", ""); // 指定無し

		param.put("isDownload", "1"); // 検索結果件数制限無し


		CsvWriterRowHandler handler = null;
		try {

			handler = new CsvWriterRowHandler(headerRow, outputProps, (Format[]) outputPropFormats);
			sqlMap.queryWithRowHandler("selectLicenseUpgradeList_LIC", param, handler);

			return handler;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}


	/**
	 * ライセンス割当情報検索
	 * @param assetId 情報機器管理番号
	 * @return ライセンス割当情報一覧
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<LicenseAlloc> selectLicenseAllocList(LicenseAlloc obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("obj", obj);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<LicenseAlloc>)sqlMap.queryForList("selectLicenseAlloc_LIC", sqlParam);
	}

	/**
	 * ライセンス_数量管理明細情報検索
	 * @param licenseId ライセンス管理番号
	 * @param assetId 情報機器管理番号
	 * @param useType ライセンス使用許諾区分
	 * @param licQuantityType ライセンス数制限
	 * @return 数量管理明細リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<LicenseLineQty> selectLicenseLineQtyForAlloc(String licenseId, String assetId, String useType, String licQuantityType) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("licenseId", licenseId);
		param.put("assetId", assetId);
		param.put("useType", useType);
		param.put("licQuantityType", licQuantityType);
		param.put("forAlloc", "1");

		return (List<LicenseLineQty>)sqlMap.queryForList("selectLicenseLineQty_LIC", param);

	}

	/**
	 * ライセンス_数量管理明細取得(許諾部署、貸出部署両方)
	 * @param licenseId ライセンス管理番号
	 * @return 数量管理明細リスト
	 * @throws SQLException
	 */
	public List<LicenseLineQty> selectLicenseLineQty(String licenseId) throws SQLException {
		return selectLicenseLineQty(licenseId, null);
	}

	/**
	 * ライセンス_数量管理明細取得(許諾部署、貸出部署指定)
	 * @param licenseId ライセンス管理番号
	 * @param licenseLineQtyType ライセンス数量管理明細区分 1:許諾部署 2:貸出部署
	 * @return 数量管理明細リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<LicenseLineQty> selectLicenseLineQty(String licenseId, String licenseLineQtyType) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("licenseId", licenseId);
		param.put("licenseLineQtyType", licenseLineQtyType);

		return (List<LicenseLineQty>)sqlMap.queryForList("selectLicenseLineQty_LIC", param);
	}

	/**
	 * ライセンス_アップグレード元明細取得
	 * @param licenseId ライセンス管理番号
	 * @return アップグレード元明細リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<LicenseLineUpg> selectLicenseLineUpgSrc(String licenseId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("licenseId", licenseId);

		return (List<LicenseLineUpg>)sqlMap.queryForList("selectLicenseLineUpgSrc_LIC", param);
	}

	/**
	 * ライセンス_アップグレード先明細取得
	 * @param licenseId ライセンス管理番号
	 * @return アップグレード先明細リスト
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<LicenseLineUpg> selectLicenseLineUpgDst(String licenseId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("licenseId", licenseId);

		return (List<LicenseLineUpg>)sqlMap.queryForList("selectLicenseLineUpgDst_LIC", param);
	}

	/**
	 * ライセンス割当作成
	 * @param loginStaffInfo ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param licenseAlloc ライセンス割当エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertLicenseAlloc(String loginStaffCode, String accessLevel, LicenseAlloc obj ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertLicenseAlloc_LIC", param);
	}

	/**
	 * ライセンス_数量管理明細情報更新（有効数更新）
	 * @param obj ライセンスアップグレード割当エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void updateLicenseLineEnableQty(LicenseLineQty obj ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("updateLicenseLineEnableQty_LIC", param);
	}

	/**
	 * ライセンス_数量管理明細情報更新
	 * @param loginStaffInfo ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param obj ライセンスアップグレード割当エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void updateLicenseLineQty(String loginStaffCode, String accessLevel, LicenseLineQty obj ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("updateLicenseLineQty_LIC", param);
	}

	/**
	 * ライセンス割当削除
	 * @param loginStaffInfo ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param licenseAlloc ライセンス割当エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void deleteLicenseAlloc(String loginStaffCode, String accessLevel, LicenseAlloc obj ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.delete("deleteLicenseAlloc_LIC", param);
	}

	/**
	 * 情報機器抹消
	 * @param updateStaffCode 更新者社員番号
	 * @param licenseId ライセンス管理番号
	 * @param deleteDate 抹消日
	 * @param deleteReason 抹消理由
	 * @throws SQLException
	 */
	public void callDeleteLicenseLogical(String updateStaffCode, String licenseId, Date deleteDate, String deleteReason) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("updateStaffCode", updateStaffCode);
		param.put("licenseId", licenseId);
		param.put("deleteDate", deleteDate);
		param.put("deleteReason", deleteReason);

		sqlMap.update("callDeleteLicenseLogical_LIC", param);
	}

	/**
	 * ライセンス割当解除
	 * @param updateStaffCode 更新者社員番号
	 * @param licenseId ライセンス管理番号
	 * @param licenseLineQtyId ライセンス数量管理明細ID
	 * @param assetId 情報機器管理番号
	 * @param assetLineOsId 情報機器等_OS明細ID
	 * @throws SQLException
	 */
	public void callDeleteLicenseAlloc(String updateStaffCode, String licenseId, Long licenseLineQtyId, String assetId, Long assetLineOsId ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("updateStaffCode", updateStaffCode);
		param.put("licenseId", licenseId);
		param.put("licenseLineQtyId", licenseLineQtyId);
		param.put("assetId", assetId);
		param.put("assetLineOsId", assetLineOsId);

		sqlMap.update("callDeleteLicenseAlloc_LIC", param);
	}

	/**
	 * 数量管理明細使用部署情報更新
	 * @param loginStaffInfo ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param obj ライセンスアップグレード割当エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void updateLicenseLineQtyUseSection(String loginStaffCode, String accessLevel, LicenseLineQty obj ) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("updateLicenseLineQtyUseSection_LIC", param);
	}

	/**
	 * ライセンス登録申請_添付明細取得
	 * @param licenseId 登録申請書番号
	 * @return ライセンス登録申請_添付明細
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<ApLicenseLineAtt> selectApLicenseLineAtt(String licenseId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("licenseId", licenseId);

		return (List<ApLicenseLineAtt>)sqlMap.queryForList("selectApLicenseLineAtt_APGT", param);
	}


	/**
	 * ライセンス登録申請_添付明細作成
	 * @param obj ライセンス登録申請_添付明細エンティティ
	 * @return なし
	 * @throws SQLException
	 */
	public void insertApLicenseLineAtt(ApLicenseLineAtt obj) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("obj", obj);

		sqlMap.insert("insertApLicenseLineAtt_APGT", param);
	}

	/**
	 * ライセンス登録申請_添付明細削除
	 * @param licenseId 登録申請書番号
	 * @return
	 * @throws SQLException
	 */
	public void deleteApLicenseLineAtt(String licenseId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("licenseId", licenseId);

		sqlMap.delete("deleteApLicenseLineAtt_APGT", param);
	}

}