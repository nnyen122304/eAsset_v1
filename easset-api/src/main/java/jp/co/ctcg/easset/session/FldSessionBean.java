/*===================================================================
 * ファイル名 : FldSessionBean.java
 * 概要説明   : 固定資産(照会・管理項目修正)セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.commons.lang3.StringUtils;

import org.apache.commons.beanutils.PropertyUtils;

import jp.co.ctcg.easset.dao.FldDAO;
import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.Section;
import jp.co.ctcg.easset.dto.asset.AssetSC;
import jp.co.ctcg.easset.dto.fld.PpfsFld;
import jp.co.ctcg.easset.dto.fld.PpfsFldApp;
import jp.co.ctcg.easset.dto.fld.PpfsFldSC;
import jp.co.ctcg.easset.dto.fld.PpfsFldSR;
import jp.co.ctcg.easset.dto.fld.PpfsFldSupport;
import jp.co.ctcg.easset.dto.fld.PpfsUnUpd;
import jp.co.ctcg.easset.dto.hist.BulkUpdateHist;
import jp.co.ctcg.easset.dto.license.LicenseSC;
import jp.co.ctcg.easset.dto.ppfs_import.PpfsStat;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.mdb.BulkUpdateMDBean;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvReaderRowHandler;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;

@Stateless
public class FldSessionBean implements FldSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession; // マスタセッション

	@EJB
	HistSession histSession; // 履歴セッション

	@EJB
	LicenseSession licenseSession; // ﾗｲｾﾝｽセッション

	@EJB
	AssetSession assetSession; // 情報機器セッション

	@EJB
	PpfsImportSession ppfsImportSession; // ProPlus取込セッション

	@EJB
	FldSession childFldSession; // 固定資産セッション（別トランザクション実行用）

	@Resource(mappedName = "java:/jms/queue/BulkUpdateQueue")
	private Queue bulkUpdateQueue; // 一括更新実行用キュー

	@Resource(mappedName = "java:/jms/BulkUpdateQueueFactory" )
	private ConnectionFactory bulkUpdateQueueFactory; // 一括更新実行用キュー接続ファクトリ

	// 履歴作成用
	private static final String HIST_ENTITY_NAME = "INT_EXT";
	private static final String HIST_OPERATION_CREATE = "新規作成";
	private static final String HIST_OPERATION_UPDATE = "更新";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#searchFld(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.fld.FldSC)
	 */
	public List<PpfsFldSR> searchFld(String loginStaffCode, String accessLevel, String companyCode, PpfsFldSC searchParam) {
		try {
			FldDAO fldDao = new FldDAO();
			replacePluralSearchCondition(searchParam, companyCode);

			return fldDao.selectFldList(loginStaffCode, accessLevel, companyCode, searchParam);

		} catch (SQLException e) {
			//	固定資産検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産照会検索"), e);
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#createFldCsv(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.fld.PpfsFldSC, java.lang.String)
	 */
	public String createFldCsv(String loginStaffCode, String accessLevel, String companyCode, List<String> outputPropList, PpfsFldSC searchParam, String itemDef) {
		try {
			FldDAO fldDAO = new FldDAO();
			replacePluralSearchCondition(searchParam, companyCode);

			//////////////////////////////////// 管理帳票の場合はカレント-履歴どちらを参照するかを判別
			boolean isHist = false;
			// 台帳・スケジュールは過去履歴の場合有り
			if(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD.equals(itemDef)
					|| itemDef.startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_PL_SCH)) {

				// 最新の取込年月取得
				String maxPeriod = "";
				List<PpfsStat> importStatList = ppfsImportSession.getPpfsStatList(companyCode, Constants.PPFS_IMPORT_DATA_TYPE_FLD);
				for(Iterator<PpfsStat> iter = importStatList.iterator(); iter.hasNext();) {
					PpfsStat stat = iter.next();
					if(stat.getLastSuccessCreateDate() != null) {
						maxPeriod = stat.getPeriod();
						break;
					}
				}

				// 最新の取込年月と検索条件の年月比較
				if(!maxPeriod.equals(searchParam.getReportPeriod())) {
					isHist = true;
				}
			}

			//////////////////////////////////// スケジュールの場合は基準年月・計算年数をパラ絵メータに設定
			if(itemDef.startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_PL_SCH)) {
				List<PpfsStat> importStatList = ppfsImportSession.getPpfsStatList(companyCode, Constants.PPFS_IMPORT_DATA_TYPE_FLD_SCH);
				for(Iterator<PpfsStat> iter = importStatList.iterator(); iter.hasNext();) {
					PpfsStat stat = iter.next();
					if(stat.getLastSuccessCreateDate() != null) {
						searchParam.setReportSchCalcBasePeriod(stat.getSchCalcBasePeriod());
						searchParam.setReportSchCalcYear(stat.getSchCalcYear());
						break;
					}
				}
			}

			//////////////////////////////////// ファイル作成

			CsvWriterRowHandler handler = fldDAO.createFldListCsv(loginStaffCode, accessLevel, companyCode, outputPropList, searchParam, itemDef, isHist);

			//////////////////////////////////// 操作ログ作成
			StringBuffer propStr = new StringBuffer();
			if(outputPropList != null) {
				for(int i = 0; i < outputPropList.size(); i++) {
					if(propStr.length() > 0) propStr.append(" ");
					propStr.append(outputPropList.get(i));
				}
			}
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_FLD_SEARCH, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam) + ",itemDef:" + itemDef + ",outputProperty:" + propStr.toString());

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産" + "ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産" + "ダウンロード"), e);
		}
	}

	/**
	 * 検索条件の名称複数入力項目の
	 * ・名称をコードに変換する。
	 * ・0パディングを行う。
	 * @param searchParam
	 */
	private void replacePluralSearchCondition(PpfsFldSC searchParam, String companyCode) {
		// 種類（0padding）
		List<String> shuruinmPluralList = Function.getPluralList(searchParam.getShuruinmPlural());
		if(shuruinmPluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < shuruinmPluralList.size(); i++) {
				String row = Function.nvl(shuruinmPluralList.get(i), "");
				if(!row.equals("")) {
					replaceStr.append(Function.paddingLeft(row, Constants.PPFS_MS01_CD_LEN, Constants.PPFS_MS_CD_PAD_CHAR) + " ");
				}
			}

			searchParam.setShuruinmPlural(replaceStr.toString());
		}

		// 分類（0padding）
		List<String> bunruinmPluralList = Function.getPluralList(searchParam.getBunruinmPlural());
		if(bunruinmPluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < bunruinmPluralList.size(); i++) {
				String row = Function.nvl(bunruinmPluralList.get(i), "");
				if(!row.equals("")) {
					replaceStr.append(Function.paddingLeft(row, Constants.PPFS_MS03_CD_LEN, Constants.PPFS_MS_CD_PAD_CHAR) + " ");
				}
			}

			searchParam.setBunruinmPlural(replaceStr.toString());
		}

		// 代表設置場所（0padding）
		List<String> setchinmPluralList = Function.getPluralList(searchParam.getSetchinmPlural());
		if(setchinmPluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < setchinmPluralList.size(); i++) {
				String row = Function.nvl(setchinmPluralList.get(i), "");
				if(!row.equals("")) {
					replaceStr.append(Function.paddingLeft(row, Constants.PPFS_MS07_CD_LEN, Constants.PPFS_MS_CD_PAD_CHAR) + " ");
				}
			}

			searchParam.setSetchinmPlural(replaceStr.toString());
		}

		// 案件グループ（0padding）
		List<String> itemGroupNamePluralList = Function.getPluralList(searchParam.getItemGroupNamePlural());
		if(itemGroupNamePluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < itemGroupNamePluralList.size(); i++) {
				String row = Function.nvl(itemGroupNamePluralList.get(i), "");
				if(!row.equals("")) {
					replaceStr.append(Function.paddingLeft(row, Constants.PPFS_GROUP_CD_LEN, Constants.PPFS_MS_CD_PAD_CHAR) + " ");
				}
			}

			searchParam.setItemGroupNamePlural(replaceStr.toString());
		}

		// オフィス 名称⇒コード
		List<String> holOfficePluralList = Function.getPluralList(searchParam.getHolOfficePlural());
		if(holOfficePluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < holOfficePluralList.size(); i++) {
				if(!Function.nvl(holOfficePluralList.get(i), "").equals("")) {
					String internalCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_OFFICE, companyCode, holOfficePluralList.get(i));
					if(!Function.nvl(internalCode, "").equals("")) {
						replaceStr.append(internalCode + " ");
					} else {
						replaceStr.append("--------NOT_EXISTS--------" + " ");
					}
				}
			}

			searchParam.setHolOfficePlural(replaceStr.toString());
		}

		// 管理区分 名称⇒コード
		List<String> astManageTypePluralList = Function.getPluralList(searchParam.getAstManageTypePlural());
		if(astManageTypePluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < astManageTypePluralList.size(); i++) {
				if(!Function.nvl(astManageTypePluralList.get(i), "").equals("")) {
					String internalCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, null, astManageTypePluralList.get(i));
					if(!Function.nvl(internalCode, "").equals("")) {
						replaceStr.append(internalCode + " ");
					} else {
						replaceStr.append("--------NOT_EXISTS--------" + " ");
					}
				}
			}

			searchParam.setAstManageTypePlural(replaceStr.toString());
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#getFld(java.lang.Long)
	 */
	public PpfsFld getFld(String loginStaffCode, String accessLevel, String companyCode, Long koyuno) {
		try {
			FldDAO fldDAO = new FldDAO();

			PpfsFld fld = fldDAO.selectFld(koyuno); // ヘッダの取得

			if(fld != null){
				//	有形？
				if(Function.nvl(fld.getSkkshisankbn(), "").equals(Constants.PPFS_FLD_SKKSHISANKBN_TAN1) || Function.nvl(fld.getSkkshisankbn(), "").equals(Constants.PPFS_FLD_SKKSHISANKBN_TAN2)){
					//	リース・レンタル契約に紐付く情報機器、ﾗｲｾﾝｽを取得
					if(!Function.nvl(fld.getShisanNum(), "").equals("")){
						//	情報機器検索
						AssetSC searchParam = new AssetSC();
						searchParam.setShisanNumPlural(fld.getShisanNum());
						searchParam.setHolCompanyCode(companyCode);
						fld.setAssetList(assetSession.searchAsset(loginStaffCode, accessLevel, searchParam, false));

						//	ﾗｲｾﾝｽ検索
						LicenseSC searchParam2 = new LicenseSC();
						searchParam2.setShisanNumPlural(fld.getShisanNum());
						searchParam2.setHolCompanyCode(companyCode);
						fld.setLicenseList(licenseSession.searchLicense(loginStaffCode, accessLevel, searchParam2, false));
					}
				}
			}

			return fld;

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産照会取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#getFld(java.lang.Long)
	 */
	public PpfsFldApp getFldApp(String loginStaffCode, String accessLevel, String companyCode, String applicationId) {
		return getFldApp(loginStaffCode, accessLevel, companyCode, applicationId, null, false);
	}
	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#getFld(java.lang.Long)
	 */
	public PpfsFldApp getFldAppKoyu(String loginStaffCode, String accessLevel, String companyCode, Long koyuno) {
		PpfsFld fld = getFld(loginStaffCode, accessLevel, companyCode, koyuno);

		if(Function.nvl(fld.getApplicationId(), "").equals("")) { // 申請書番号無し
			return getFldApp(loginStaffCode, accessLevel, companyCode, null, koyuno, false);
		} else { // 申請書番号有り
			return getFldApp(loginStaffCode, accessLevel, companyCode, fld.getApplicationId(), null, false);
		}
	}

	/**
	 * 固定資産照会_取得申請単位取得
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private PpfsFldApp getFldApp(String loginStaffCode, String accessLevel, String companyCode, String applicationId, Long koyuno, boolean lockFlag) {
		try {
			FldDAO fldDAO = new FldDAO();

			//	取得申請番号に紐付く固定資産検索(NEA_INT_EXTテーブルから取得)
			PpfsFldApp ppfsfFldApp = new PpfsFldApp();
			if(!Function.nvl(applicationId, "").equals("")){
				ppfsfFldApp = fldDAO.selectFldApp(applicationId, companyCode, lockFlag); // ヘッダの取得
				//	取得申請単位と紐がない？
				if(ppfsfFldApp == null){
					ppfsfFldApp = new PpfsFldApp();
				}
			}

			PpfsFldSC obj = new PpfsFldSC();
			//	無形セット
			obj.setSkkshisankbn(Constants.PPFS_FLD_SKKSHISANKBN_INT1 + " " + Constants.PPFS_FLD_SKKSHISANKBN_INT2);
			obj.setApplicationIdPlural(applicationId);
			obj.setKoyuno(koyuno);

			//	取得申請番号に紐付く固定資産(無形)取得(取得申請番号がない場合固有番号で検索)
			List<PpfsFldSR> list = fldDAO.selectFldList(loginStaffCode, accessLevel, companyCode, obj);

			//	仮勘定一覧
			List<PpfsFldSR> ppfsListK = new ArrayList<PpfsFldSR>();
			//	本勘定一覧
			List<PpfsFldSR> ppfsListH = new ArrayList<PpfsFldSR>();
			//	仮勘定、本勘定識別
			if(list != null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					PpfsFldSR item = list.get(i);
					//	仮勘定？
					if(Function.nvl(item.getShisanknrkbn(), "").equals("1")){
						ppfsListK.add(item);
					}
					//	本勘定？
					else{
						ppfsListH.add(item);
					}

					if(Function.nvl(ppfsfFldApp.getApplicationId(), "").equals("")) { // 既存の付加情報無し
						ppfsfFldApp.setApplicationId(item.getApplicationId()); // キーである申請書番号は設定
						ppfsfFldApp.setHolCompanyCode(companyCode);
					}
				}
			}

			ppfsfFldApp.setPpfsListK(ppfsListK);
			ppfsfFldApp.setPpfsListH(ppfsListH);

			//	取得申請番号あり？
			if(!Function.nvl(applicationId, "").equals("")){
				//	ﾗｲｾﾝｽ一覧検索
				LicenseSC obj2 = new LicenseSC();
				obj2.setGetApplicationIdPlural(applicationId);
				ppfsfFldApp.setLicenseList(licenseSession.searchLicense(loginStaffCode, accessLevel, obj2, false));
			}

			return ppfsfFldApp;

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産照会取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#updateFldApp(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.fld.FldApp)
	 */
	public void updateFldApp(String loginStaffCode, String accessLevel, PpfsFldApp obj) throws AppException {
		updateFldApp(loginStaffCode, accessLevel, obj, null, null);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#updateFldApp(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.fld.FldApp)
	 */
	public void updateFldApp(String loginStaffCode, String accessLevel, PpfsFldApp obj, String operation) throws AppException {
		updateFldApp(loginStaffCode, accessLevel, obj, operation, null);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#updateFldApp(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.fld.PpfsFldApp, java.lang.String)
	 */
	private void updateFldApp(String loginStaffCode, String accessLevel, PpfsFldApp obj, String operation, Set<String> updatePropSet) throws AppException {
		try {
			FldDAO fldDAO = new FldDAO();

			PpfsFldApp ppfsfFldAppOld = fldDAO.selectFldApp(obj.getApplicationId(), obj.getHolCompanyCode(), true); // 現データの取得

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			errMsg.append(validateFldApp(loginStaffCode, accessLevel, obj, !Function.nvl(operation, "").equals(""), updatePropSet));

			//	現データが存在？
			if(ppfsfFldAppOld != null){
				// バージョンチェック
				if(obj.getVersion().intValue() != ppfsfFldAppOld.getVersion().intValue()) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
				}
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新もしくは、新規作成
			//////////////////////////////////// 固定値セット
			Date sysdate = new Date(); // システム日付取得
			//	現データが存在？
			if(ppfsfFldAppOld != null){
				// 更新日・更新者
				obj.setUpdateDate(sysdate);
				obj.setUpdateStaffCode(loginStaffCode);
				// バージョン
				obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
				fldDAO.updateFldApp(obj); // 更新

				//////////////////////////////////// 操作ログ作成
				if(Function.nvl(operation, "").equals("")) operation = HIST_OPERATION_UPDATE;
				histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId() + " " + obj.getHolCompanyCode(), operation, null);
			}
			else{
				// 更新日・更新者
				obj.setCreateDate(sysdate);
				obj.setCreateStaffCode(loginStaffCode);
				obj.setUpdateDate(sysdate);
				obj.setUpdateStaffCode(loginStaffCode);
				// バージョン
				obj.setVersion(1);
				fldDAO.insertFldApp(obj); // 新規作成
				//////////////////////////////////// 操作ログ作成
				if(Function.nvl(operation, "").equals("")) operation = HIST_OPERATION_CREATE;
				histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId() + " " + obj.getHolCompanyCode(), operation, null);
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産照会_取得申請単位更新"), e);
		}
	}

	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @param isEditableSkip 修正権限チェックをするかどうか true:しない false:する
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateFldApp(String loginStaffCode, String accessLevel, PpfsFldApp obj, boolean isEditableSkip, Set<String> updatePropSet) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 修正権限バリデーション
		if(!isEditableSkip) {
			if(!Function.nvl(obj.getApplicationId(), "").equals("")) { // 修正
				if(!isEditableFld(loginStaffCode, accessLevel, obj.getHolCompanyCode(), obj.getApplicationId())) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_EDIT_AUTH));
					return errMsg.toString();
				}
			}
		}


		int status = -1;

		//	資産区分-通常？その他？
		boolean shisanjotaikbnNormalFlag = false;

		List<PpfsFldSR> k = obj.getPpfsListK();
		List<PpfsFldSR> h = obj.getPpfsListH();

		if(k != null && k.size() > 0){
			for(int i = 0; i < k.size(); i ++){
				PpfsFldSR item = k.get(i);
				if(Function.nvl(item.getShisanjotaikbn(), "").equals("1")){
					shisanjotaikbnNormalFlag = true;
					break;
				}
			}
		}

		if(!shisanjotaikbnNormalFlag){
			if(h != null && h.size() > 0){
				for(int i = 0; i < h.size(); i ++){
					PpfsFldSR item = h.get(i);
					if(Function.nvl(item.getShisanjotaikbn(), "").equals("1")){
						shisanjotaikbnNormalFlag = true;
						break;
					}
				}
			}
		}

		if(Function.isAccessLevelAdmin(accessLevel)) { // 全社権限の場合
			//	通常？
			if(shisanjotaikbnNormalFlag){
				status = 1;
			}
			else{
				status = 2;
			}
		}
		else{
			//	通常？
			if(shisanjotaikbnNormalFlag){
				status = 3;
			}
			else{
				status = 4;
			}
		}


		//	項目定義バリデーション
		errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_INT_APP, "NEA_INT_EXT", obj, status, updatePropSet));

		//////////////////////////////////// マスタ整合性バリデーション
		// 保有部署
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("holSectionCode")) ){
			if(!Function.nvl(obj.getHolSectionCode(), "").equals("")) {
				Section sec = masterSession.getSection(obj.getHolCompanyCode(), obj.getHolSectionCode(), obj.getHolSectionYear());
				if(sec == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有部署"));
				}
			}
		}
		// 無形固定資産担当者
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("holStaffCode")) ){
			if(!Function.nvl(obj.getHolStaffCode(), "").equals("")) {
				// 承認済・却下以外は退職社員NG
				if(masterSession.getStaffValid(obj.getHolCompanyCode(), obj.getHolStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "無形固定資産担当者"));
				}
			}
		}
		return errMsg.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createApAssetPossibleInputMasterCsv(java.lang.String, java.lang.String)
	 */
	public String createFldPossibleInputMasterCSV(String loginStaffCode, String accessLevel, String companyCode, List<String> propertyList) {
		try {
			FldDAO fldDAO = new FldDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = fldDAO.createFldPossibleInputMasterList(companyCode, accessLevel, propertyList);

			//////////////////////////////////// 操作ログ作成
			StringBuffer propStr = new StringBuffer();
			if(propertyList != null) {
				for(int i = 0; i < propertyList.size(); i++) {
					if(propStr.length() > 0) propStr.append(" ");
					propStr.append(propertyList.get(i));
				}
			}
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_FLD_MASTER, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",outputProperty:" + propStr.toString());

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#checkPpfsFldUpdate(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void checkPpfsFldUpdate(String loginStaffCode, String accessLevel, String companyCode, List<PpfsFldSR> obj) throws AppException{
		try{
			FldDAO fldDAO = new FldDAO();

			String koyunoPluar = "";
			for(int i = 0; i < obj.size(); i++){
				PpfsFldSR item = obj.get(i);
				if(!Function.nvl(koyunoPluar, "").equals("")){
					koyunoPluar = koyunoPluar + " " + item.getKoyuno();
				}
				else{
					koyunoPluar = String.valueOf(item.getKoyuno());
				}
			}

			List<PpfsFldSR> result = fldDAO.selectPpfsFld(companyCode, koyunoPluar);

			if(result != null){
				HashMap<Long, String> resultMap = new HashMap<Long, String>();
				// 検索結果を日付比較用に保持
				for(int i = 0; i < result.size(); i++) {
					PpfsFldSR ppfsFld  = result.get(i);
					resultMap.put(ppfsFld.getKoyuno(), Function.nvl(ppfsFld.getUpdymd(), "") + "_" + Function.nvl(ppfsFld.getUpdtime(), ""));
				}

				StringBuffer errMsg = new StringBuffer();
				for(int i = 0; i < obj.size(); i++){
					PpfsFldSR fld = obj.get(i);

					String ppfsYmdTime = resultMap.get(fld.getKoyuno());

					if(ppfsYmdTime != null) {
						String eaYmdTime = Function.nvl(fld.getUpdymd(), "") + "_" + Function.nvl(fld.getUpdtime(), "");

						if(!ppfsYmdTime.equals(eaYmdTime)) { // eAsset取込後にProPlus更新
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "eAssetの資産情報が最新ではありませんので、管理者にお問合せ下さい。(資産番号：" + fld.getShisanNum() + ")"));
						}
					} else { // 対応するPPFS情報無し
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "eAssetの資産情報が最新ではありませんので、管理者にお問合せ下さい。(資産番号：" + fld.getShisanNum() + ")"));
					}
				}

				// エラー有り
				if(errMsg.length() > 0) throw new AppException(errMsg.toString());
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産取得"), e);
		}
	}

	/**
	 * 補助台帳一覧取得
	 * @param koyuno 固有番号
	 * @param syubetuCode   種別コード
	 * @return 補助台帳一覧
	 */
	public List<PpfsFldSupport> getFldSupportList(Long koyuno, String syubetuCode) {
		try{
			FldDAO fldDAO = new FldDAO();
			return fldDAO.selectFldSupportList(koyuno, syubetuCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "補助台帳一覧取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#searchPpfsUnUpd(java.lang.String, java.util.List)
	 */
	public List<PpfsUnUpd> searchPpfsUnUpd(String companyCode, String koyunoPluar) {
		try{
			FldDAO fldDAO = new FldDAO();
			return fldDAO.selectPpfsUnUpd(companyCode, koyunoPluar);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "未承認データ一覧取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#getUpdatePropertyList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<CodeName> getUpdatePropertyList(String loginStaffCode, String accessLevel, String fileId) throws AppException {
		CsvReaderRowHandler handler = null;
		try {
			// ファイル内の有効更新項目取得
			handler = new CsvReaderRowHandler(accessLevel, fileId, PpfsFldApp.class, Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_INT_APP, 1);
			String[] inputProps = handler.getInputProps(); // 更新可能プロパティ取得
			HashSet<String> inputPropSet = new HashSet<String>();

			if(inputProps != null && inputProps.length > 0) {
				for(int i = 0; i < inputProps.length; i++) {
					inputPropSet.add(inputProps[i]);
				}
			}

			// DB上の更新項目一覧から、有効なもの意外を除外
			List<CodeName> propList = null;

			// ヘッダ項目
			if(propList == null || propList.size() == 0) {
				propList = masterSession.getDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_INT_APP, null);
				for(int i = (propList.size() - 1); i >= 0; i--) {
					CodeName row = propList.get(i);

					// ファイル内有効更新項目以外
					if(!inputPropSet.contains(row.getValue3())) {
						propList.remove(i);
						continue;
					}
				}
			}

			return propList;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}
	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#callUpdateAssetBulk(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void callUpdateFldBulk(String companyCode, String loginStaffCode, String accessLevel, String fileId, List<CodeName> updatePropertyList) throws AppException{
		//////////////////////////////////// 一括更新実行ログの作成
		Long logId = histSession.createBulkUpdateLog(companyCode, loginStaffCode, HIST_ENTITY_NAME, fileId, updatePropertyList);
		BulkUpdateHist hist = histSession.getBulkUpdateHist(logId); // カレントログ取得

		//////////////////////////////////// 対象ファイルを一時領域⇒保存領域へコピー
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		File execFile = masterSession.fileCommit(fileId, HIST_ENTITY_NAME + "_BULK_UPDATE", df.format(hist.getCreateDate()) + "_" + loginStaffCode);
		File successFile = new File(execFile.getAbsolutePath() + "_S");
		File failureFile = new File(execFile.getAbsolutePath() + "_F");
		File errorFile = new File(execFile.getAbsolutePath() + "_E");
		try {
			successFile.createNewFile();
			failureFile.createNewFile();
			errorFile.createNewFile();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "一括更新用ファイル生成"), e);
		}

		//////////////////////////////////// メッセージ送信
		// メッセージパラメータ作成
		HashMap<String, Object> param = new HashMap<String, Object>();

		param.put("functionName", BulkUpdateMDBean.FUNCTION_FLD_APP);
		param.put("companyCode", companyCode);
		param.put("loginStaffCode", loginStaffCode);
		param.put("accessLevel", accessLevel);
		param.put("fileId", fileId);
		param.put("execFile", execFile);
		param.put("updatePropertyList", updatePropertyList);
		param.put("logId", logId);

		Function.sendJmsMessage(bulkUpdateQueueFactory, bulkUpdateQueue, param);
	}
	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#updateFldBulkRow(java.lang.String, java.lang.String, int, jp.co.ctcg.easset.dto.fld.PpfsFldApp, java.util.Set, java.util.Map, java.lang.reflect.Method[], java.lang.reflect.Method[])
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // 新規トランザクション
	public void updateFldBulkRow(String loginStaffCode, String accessLevel, int rowCt, PpfsFldApp updateObj, Set<String> updatePropSet, Map<String, String> apChangeTypeMap, Method[] readMethods, Method[] writeMethods) throws AppException{
		PpfsFldApp obj = new PpfsFldApp();
		try{
			obj = getFldApp(loginStaffCode, accessLevel, updateObj.getHolCompanyCode(), updateObj.getApplicationId());
			//////////////////////////////////// 一括更新用バリデーション
			StringBuffer errMsg = new StringBuffer();

			//	資産区分-通常？その他？
			boolean flag = false;
			//	仮勘定
			if(obj.getPpfsListK() != null && obj.getPpfsListK().size() > 0){
				for(int i = 0; i < obj.getPpfsListK().size(); i++){
					PpfsFldSR item = obj.getPpfsListK().get(i);
					if(Function.nvl(item.getShisanjotaikbn(), "").equals("1")){
						flag = true;
						break;
					}
				}
			}
			//	本勘定
			if(!flag
			&& (obj.getPpfsListH() != null && obj.getPpfsListH().size() > 0)
			){
				for(int i = 0; i < obj.getPpfsListH().size(); i++){
					PpfsFldSR item = obj.getPpfsListH().get(i);
					if(Function.nvl(item.getShisanjotaikbn(), "").equals("1")){
						flag = true;
						break;
					}
				}
			}
			//	資産区分-通常以外
			if(!flag){
				//	一般権限？
				if(!Function.isAccessLevelAdmin(accessLevel)){
					//	保有部署変更？
					if(updatePropSet.contains("holSectionCode")){
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "固定資産区分が通常以外の場合、保有部署の変更はできません。"));
					}
				}
				//	無形固定資産管理担当者？
				if(updatePropSet.contains("holStaffCode")){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "固定資産区分が通常以外の場合、無形固定資産管理担当者の変更はできません。"));
				}
			}
			//	通常
			else{
				//	保有部署変更？
				//	無形管理担当者変更？
				if(updatePropSet.contains("holSectionCode")
				|| updatePropSet.contains("holStaffCode") ){
					// 一般・部署権限
					if(!Function.isAccessLevelAdmin(accessLevel)){
						// 移動申請使用区分:使用
						if(Function.nvl(apChangeTypeMap.get(obj.getHolCompanyCode()), "").equals(Constants.AP_CHANGE_USE_TYPE_ALL)) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "保有部署、無形管理担当者を変更するには、移動申請を行う必要があります。"));
						}
					}
				}
			}
			if(errMsg.length() > 0) throw new AppException(errMsg.toString());

			//////////////////////////////////// 更新
			// 更新項目のコピー
			for(int j = 0; j < writeMethods.length; j++) {
				writeMethods[j].invoke(obj, readMethods[j].invoke(updateObj, (Object[]) null));
			}

		} catch (IllegalArgumentException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産(無形)更新"), e);
		} catch (IllegalAccessException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産(無形)更新"), e);
		} catch (InvocationTargetException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産(無形)更新"), e);
		}

		updateFldApp(loginStaffCode, accessLevel, obj, null, updatePropSet);
	}
	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#updateFldBulk(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.File, java.util.List, java.lang.Long)
	 */
	public void updateFldBulk(String companyCode, String loginStaffCode, String accessLevel, String fileId, File execFile, List<CodeName> updatePropertyList, Long logId){
		// ログ更新用件数
		Integer execCount = null;
		Integer successCount = null;
		Integer failureCount = null;
		// ログファイル出力用
		File successFile = new File(execFile.getAbsolutePath() + "_S");
		File failureFile = new File(execFile.getAbsolutePath() + "_F");
		File errorFile = new File(execFile.getAbsolutePath() + "_E");
		BufferedReader execFileReader = null;

		try {
			if(updatePropertyList == null || updatePropertyList.size() == 0) throw new AppException("更新対象項目が指定されていません。");

			//////////////////////////////////// CSV読込
			List<PpfsFldApp> updateFldAppList = new ArrayList<PpfsFldApp>();
			int headerRowCt = setFldListByCsv(companyCode, accessLevel, fileId, false, updateFldAppList, updatePropertyList, logId);

			//////////////////////////////////// 一括更新実行ログの取得
			BulkUpdateHist logHist = histSession.getBulkUpdateHist(logId);

			if(!Function.nvl(logHist.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) { // ファイル読み込み中に中断されていない

				if(updateFldAppList.size() == 0) throw new AppException("更新対象データが入力されていません。");

				//////////////////////////////////// ファイル読込設定
				execFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(execFile), CsvReaderRowHandler.getCharsetName()));

				StringBuffer headerStr = new StringBuffer();
				for(int i = 0; i < headerRowCt + 1; i++) {
					headerStr.append(execFileReader.readLine() + "\n");
				}

				int skipCount = 0; // エラー再実行時に重複実行しないため、処理スキップする件数

				if(logHist.getSuccessCount() == null && logHist.getFailureCount() == null) { // 初回実行
					//////////////////////////////////// 一括更新実行ログの更新
					execCount = Integer.valueOf(updateFldAppList.size());
					successCount = Integer.valueOf(0);
					failureCount = Integer.valueOf(0);
					histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_UPDATE, execCount, successCount, failureCount);

					//////////////////////////////////// 成功、失敗ファイルにヘッダ出力
					Function.appendStrToFile(successFile, headerStr.toString());
					Function.appendStrToFile(failureFile, headerStr.toString());
				} else { // エラー発生により再実行
					if(Function.nvl(logHist.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_END)) {
						return; // 処理済であれば終了
					}

					skipCount = Function.nvl(logHist.getSuccessCount(), Integer.valueOf(0)).intValue() + Function.nvl(logHist.getFailureCount(), Integer.valueOf(0)).intValue();

					execCount = logHist.getExecCount();
					successCount = Function.nvl(logHist.getSuccessCount(), Integer.valueOf(0));
					failureCount = Function.nvl(logHist.getFailureCount(), Integer.valueOf(0));
				}

				//////////////////////////////////// 登録
				int rowCt = headerRowCt + 1;

				try {
					// 読み込み対象プロパティのgetter/setter取得
					List<Method> readMethodList = new ArrayList<Method>();
					List<Method> writeMethodList = new ArrayList<Method>();
					HashSet<String> updatePropSet = new HashSet<String>();
					PpfsFldApp a = new PpfsFldApp(); /// リフレクション用のダミー
					// 選択プロパティのgetter/setter設定
					for(int i = 0; i < updatePropertyList.size(); i++) {
						String prop = updatePropertyList.get(i).getValue3();
						updatePropSet.add(prop);
						PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(a, prop);
						readMethodList.add(PropertyUtils.getReadMethod(pd));
						writeMethodList.add(PropertyUtils.getWriteMethod(pd));

						// 追加更新プロパティの取得
						String addPropStr = Function.nvl(updatePropertyList.get(i).getValue16(), "");
						if(!addPropStr.equals("")) {
							String[] addProps = addPropStr.split(" ");
							for(int j = 0; j < addProps.length; j++) {
								updatePropSet.add(addProps[j]);
								pd = PropertyUtils.getPropertyDescriptor(a, addProps[j]);
								readMethodList.add(PropertyUtils.getReadMethod(pd));
								writeMethodList.add(PropertyUtils.getWriteMethod(pd));
							}
						}
					}

					Method[] readMethods = readMethodList.toArray(new Method[0]);
					Method[] writeMethods = writeMethodList.toArray(new Method[0]);

					// 移動申請区分取得
					List<CodeName> useCompanyList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_USE_COMPANY, null, null, null);
					HashMap<String, String> apChangeTypeMap = new HashMap<String, String>();
					for(int i = 0; i < useCompanyList.size(); i++) {
						CodeName row = useCompanyList.get(i);
						apChangeTypeMap.put(row.getInternalCode(), row.getValue4());
					}

					//////////////////////////////////// 登録(件数分)
					boolean lastStat = true; // 前行処理結果 true:成功 false:失敗
					for(int i = 0; i < updateFldAppList.size(); i++) {
						//////////////////////////////////// 中断リクエストされていないかどうか確認
						BulkUpdateHist log = histSession.getBulkUpdateHist(logId);
						if(Function.nvl(log.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) break; // 中断

						//////////////////////////////////// 1件処理
						rowCt++;
						String execFileRowStr = execFileReader.readLine(); // 対象ファイル一行読み込み（成功・失敗ファイル保存用）

						if(i < skipCount) {
							continue; // エラー前に実行済みのためスキップ
						}

						PpfsFldApp updateObj = updateFldAppList.get(i); // 更新内容取得
						if(Function.nvl(updateObj.getApplicationId(), "").equals("")) {
							if(lastStat) { // 前行処理が成功の場合は成功
								successCount = Integer.valueOf(successCount.intValue() + 1);
								Function.appendStrToFile(successFile, execFileRowStr + "\n");
							} else {
								failureCount = Integer.valueOf(failureCount.intValue() + 1);
								Function.appendStrToFile(failureFile, execFileRowStr + "\n");
							}
							continue; // 更新スキップ
						}

						try {
							//////////////////////////////////// 1件更新
							childFldSession.updateFldBulkRow(loginStaffCode, accessLevel, rowCt, updateObj, updatePropSet, apChangeTypeMap, readMethods, writeMethods);

							lastStat = true;
							successCount = Integer.valueOf(successCount.intValue() + 1);
							//////////////////////////////////// 一括更新実行ログの更新
							histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_UPDATE, execCount, successCount, failureCount);
							//////////////////////////////////// 実行データ保存
							Function.appendStrToFile(successFile, execFileRowStr + "\n");
						} catch(Exception e) {
							lastStat = false;
							failureCount = Integer.valueOf(failureCount.intValue() + 1);

							if(failureCount.intValue() == 1) { // 先頭エラーメッセージ表示
								StringBuffer errorMessage = new StringBuffer();
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "更新中に以下のエラーが発生しました。"));
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "※ 更新項目に指定されていない項目がエラーとなっている場合は、該当項目も事前に(同時に)修正する必要があります。"));

								Function.appendMessageToFile(errorFile, errorMessage.toString() + "\n");
							}

							String rowNumStr = "[" + rowCt + "行目(失敗データファイル " + (headerRowCt + 1 + failureCount.intValue()) + "行目):" + updateObj.getApplicationId() + "] "; // エラー表示用行識別に取得申請番号付加

							StringBuffer errorMessage = new StringBuffer();
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "以下のエラーがあります。"));
							errorMessage.append(e.getMessage());

							Function.appendMessageToFile(errorFile, errorMessage.toString() + "\n");

							//////////////////////////////////// 一括更新実行ログの更新
							histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_UPDATE, execCount, successCount, failureCount);
							//////////////////////////////////// 実行データ保存
							Function.appendStrToFile(failureFile, execFileRowStr + "\n");
						}
					}
				} catch (IllegalArgumentException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産(無形)更新"), e);
				} catch (IllegalAccessException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産(無形)更新"), e);
				} catch (InvocationTargetException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産(無形)更新"), e);
				} catch (NoSuchMethodException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産(無形)更新"), e);
				}
			}

		} catch(Exception e) {
			StringBuffer errorMessage = new StringBuffer();
			errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "ファイル読み込み中に以下のエラーが発生したため、処理を中断しました。"));
			errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "※ 下記表示されているエラー行以外も含め、全ての一括更新対象が更新されていませんので、対象ファイルのエラー行を削除もしくは、修正後再度一括更新を行ってください。"));
			errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "※ 更新項目に指定されていない項目がエラーとなっている場合は、該当項目も事前に(同時に)修正する必要があります。"));
			errorMessage.append("\n");
			errorMessage.append(e.getMessage() + "\n");

			if(!(e instanceof AppException)) errorMessage.append(Function.getStackTraceStr(e) + "\n");

			Function.appendMessageToFile(errorFile, errorMessage.toString() + "\n");
		} finally {
			//////////////////////////////////// 一括更新実行ログの更新
			histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_END, execCount, successCount, failureCount);

			if(execFileReader != null) {
				try {
					execFileReader.close();
				} catch (IOException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産(無形)更新"), e);
				}
			}

		}
	}
	/**
	 * CSVファイルから固定資産情報セット
	 * @param companyCode 会社コード
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル参照
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @param isLineUpdate 明細更新
	 * @param fldAppList 固定資産情報情報をセットするリスト
	 * @param updatePropertyList 更新項目一覧(nullは全て)
	 * @param logId 一括更新ログID(nullは一括更新以外)
	 * @return ヘッダ行数
	 * @throws AppException
	 */
	private int setFldListByCsv(String companyCode, String accessLevel, String fileId, boolean isLineUpdate, List<PpfsFldApp> fldAppList, List<CodeName> updatePropertyList, Long logId) throws AppException {
		CsvReaderRowHandler handler = null;

		try {
			FldDAO fldDAO = new FldDAO();

			handler = new CsvReaderRowHandler(accessLevel, fileId, PpfsFldApp.class, Constants.CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_INT_APP, updatePropertyList, 1);

			PpfsFldApp row = null;
			StringBuffer errorMessage = new StringBuffer(); // 全行エラーメッセージ
			int headerCt = handler.getHeaderRowCount() + 1;
			int rowCt = headerCt; // ファイル行カウンタ

			int currentYear = Integer.valueOf(masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null).getValue1()); // カレント年度
			boolean isUpdateHolSection = false; // 保有部署更新有
			boolean isUpdateHolStaffSection = false; //無形管理担当者更新有

			// 部署更新指定判別用
			if(updatePropertyList != null) {
				for(int i = 0; i < updatePropertyList.size(); i++) {
					String prop = Function.nvl(updatePropertyList.get(i).getValue3(), "");
					if("holSectionCode".equals(prop)) isUpdateHolSection = true;
					if("holStaffSectionCode".equals(prop)) isUpdateHolSection = true;
				}
			} else {
				isUpdateHolSection = true;
				isUpdateHolStaffSection = true;
			}

			do {
				//////////////////////////////////// 中断リクエストされていないかどうか確認
				if(logId != null) {
					BulkUpdateHist log = histSession.getBulkUpdateHist(logId);
					if(Function.nvl(log.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) break; // 中断
				}

				//////////////////////////////////// 1件処理
				rowCt++;
				String rowNumStr = "[" + rowCt + "行目] ";

				PpfsFldApp oldObj = null;

				// 行データ取得
				// 更新対象データ取得
				String applicationId = handler.handleId();
				if(Function.nvl(applicationId, "").equals("")) break; // 行データが取得できない場合は終了

				//	取得申請書番号0埋め
				applicationId = StringUtils.leftPad(applicationId, Constants.APPLICATION_ID_LENGTH, "0");

				oldObj = fldDAO.selectFldApp(applicationId, companyCode, false);	// 変更前オブジェクト保持用
				if(oldObj == null){
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "取得申請管理番号", applicationId));
					continue; // 取得申請管理番号が不正な場合はスキップ
				}

				row = (PpfsFldApp) handler.handleRow(oldObj);
				if(row == null) break; // 行データが取得できない場合は終了

				rowNumStr = "[" + rowCt + "行目:" + row.getApplicationId() + "] "; // エラー表示用行識別に取得申請管理番号付加

				// 保有会社
				if(oldObj == null || !Function.nvl(oldObj.getHolCompanyCode(), "").equals(Function.nvl(row.getHolCompanyCode(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setHolCompanyCode(null);
					if(!Function.nvl(row.getHolCompanyCode(), "").equals("")) {
						String holCompanyCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_USE_COMPANY, null, row.getHolCompanyCode());
						if(holCompanyCode == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "保有・設置-保有会社", row.getHolCompanyCode()));
						} else {
							row.setHolCompanyCode(holCompanyCode);
						}
					}
				}

				// 保有部署年度
				if(isUpdateHolSection) row.setHolSectionYear(currentYear);

				// 保有部署コード0抜け対応
				if(!Function.nvl(row.getHolSectionCode(), "").equals("") && Function.nvl(row.getHolSectionCode(), "").length() < Constants.SECTION_CODE_LENGTH) {
					row.setHolSectionCode(StringUtils.leftPad(row.getHolSectionCode(), Constants.SECTION_CODE_LENGTH, "0"));
				}

				// 無形管理担当者会社
				if(oldObj == null || !Function.nvl(oldObj.getHolStaffCompanyCode(), "").equals(Function.nvl(row.getHolStaffCompanyCode(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setHolStaffCompanyCode(null);
					if(!Function.nvl(row.getHolStaffCompanyCode(), "").equals("")) {
						String holStaffCompanyCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_USE_COMPANY, null, row.getHolStaffCompanyName());
						if(holStaffCompanyCode == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "保有-保有会社", row.getHolStaffCompanyName()));
						} else {
							row.setHolStaffCompanyCode(holStaffCompanyCode);
						}
					}
				}

				//	無形管理担当者年度
				if(isUpdateHolStaffSection) row.setHolStaffSectionYear(currentYear);

				// 無形管理担当者所属部署0抜け対応
				if(!Function.nvl(row.getHolStaffSectionCode(), "").equals("") && Function.nvl(row.getHolStaffSectionCode(), "").length() < Constants.SECTION_CODE_LENGTH) {
					row.setHolStaffSectionCode(StringUtils.leftPad(row.getHolStaffSectionCode(), Constants.SECTION_CODE_LENGTH, "0"));
				}

				//////////////////////////////////// 明細項目のセット
				// 共有ユーザ

				fldAppList.add(row); // リターンに追加

				if(handler.getRowStatus() == CsvReaderRowHandler.ROW_STATUS_ERROR) { // CSV読み込み時のエラー有
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + handler.getRowErrorMessage()));
				}

				//////////////////////////////////// 一括更新の場合読み込み件数をログ出力
				if(logId != null) {
					histSession.updateBulkUpdateLog(logId, null, Constants.BULK_UPDATE_STATUS_READ, rowCt - headerCt, null, null);
				}
			} while(true);

			if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

			return handler.getHeaderRowCount();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "一括入力CSVファイル読込"), e);
		} finally {
			if(handler != null) handler.close();
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#checkFldCount(java.lang.String)
	 */
	public boolean checkFldCount(String applicationId) throws AppException {

		try{
			FldDAO fldDAO = new FldDAO();
			int eassetCount = fldDAO.selectFldCountEAsset(applicationId).intValue();
			int proplusCount = fldDAO.selectFldCountProPlus(applicationId).intValue();

			if(proplusCount != eassetCount){
				return false;
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "資産数取得"), e);
		}
		return true;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#isEditableFld(java.lang.String, java.lang.String, java.lang.String, java.lang.Long)
	 */
	public boolean isEditableFld(String loginStaffCode, String accessLevel, String companyCode, String applicationId){
		boolean ret = false;

		//	取得申請書番号がない
		if(Function.nvl(applicationId, "").equals("")){
			return ret;
		}

		PpfsFldApp ppfsFldApp = getFldApp(loginStaffCode, accessLevel, companyCode, applicationId);

		if(Constants.STAFF_CODE_SYSTEM.equals(loginStaffCode)) {
			ret = true; // システム自動処理ユーザ
		} else if(Function.nvl(ppfsFldApp.getHolStaffCode(), "").equals(loginStaffCode)){
			ret = true; // 無形管理担当者が自分の機器は修正可能
		}
		else{
			if(Function.isAccessLevelAdmin(accessLevel)) { // 全社権限
				// 保有部署によるアクセスレベル取得
				String level = masterSession.getAccessLevel(Constants.MENU_ID_FLD_INT_SEARCH, loginStaffCode, companyCode, null, 0);
				if(Function.isAccessLevelAdmin(level)) {
					ret = true;
				}
			}
			//	新規作成？
			else if(Function.nvl(ppfsFldApp.getHolSectionCode(), "").equals("") && Function.nvl(ppfsFldApp.getHolStaffCode(), "").equals("")){
				ret = false;
			}
			// 資産管理担当者/部署長
			//	一般権限
			else {
				try {
					MasterDAO masterDAO = new MasterDAO();
					List<String> sectionList = null;

					// 所属部署もしくは、上位部署
					sectionList = masterDAO.selectAccessibleUpperSectionCodeList(companyCode, loginStaffCode);

					if(Function.isAccessLevelSection(accessLevel)) { // 部署権限の場合管轄部署も含める
						List<String> sectionList2 = masterDAO.selectAccessibleSectionCodeList(companyCode, loginStaffCode, "1");
						if(sectionList2.size() > 0) sectionList.addAll(sectionList2);
					}

					for(int i = 0; i < sectionList.size(); i++){
						String sectionCode = sectionList.get(i);
						if(Function.nvl(ppfsFldApp.getHolSectionCode(), "").equals(sectionCode)){
							ret = true;
							break;
						}
					}
				} catch (SQLException e) {
					Logging.warning(Function.getStackTraceStr(e));
					e.printStackTrace();
					ret = false;
				}
			}

		}

		return ret;
	}


}