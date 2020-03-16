/*===================================================================
 * ファイル名 : LicenseSessionBean.java
 * 概要説明   : ライセンス、ライセンス登録申請セッションEJB定義
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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import jp.co.ctcg.easset.dao.LicenseDAO;
import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.LovDataEx;
import jp.co.ctcg.easset.dto.Section;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineLic;
import jp.co.ctcg.easset.dto.hist.BulkUpdateHist;
import jp.co.ctcg.easset.dto.license.ApLicenseLineAtt;
import jp.co.ctcg.easset.dto.license.License;
import jp.co.ctcg.easset.dto.license.LicenseAlloc;
import jp.co.ctcg.easset.dto.license.LicenseLineQty;
import jp.co.ctcg.easset.dto.license.LicenseLineUpg;
import jp.co.ctcg.easset.dto.license.LicenseSC;
import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.dto.license.Software;
import jp.co.ctcg.easset.dto.license.SoftwareMaker;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.mdb.BulkUpdateMDBean;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CopyUtil;
import jp.co.ctcg.easset.util.CsvReaderRowHandler;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;
import jp.co.ctcg.easset.ws.ApLicenseService;
import jp.co.ctcg.easset.ws.ApLicenseServiceProxy;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;

import org.apache.commons.lang3.StringUtils;

import org.apache.commons.beanutils.PropertyUtils;

@Stateless
public class LicenseSessionBean implements LicenseSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession; // マスタセッション

	@EJB
	AssetSession assetSession; // 情報機器等セッション

	@EJB
	HistSession histSession; // 履歴セッション

	@EJB
	ApGetTanSession apGetTanSession; // 取得申請(有形)セッション

	@EJB
	LicenseSession childLicenseSession; // ライセンスセッション（別トランザクション実行用）

	@Resource(mappedName = "java:/jms/queue/BulkUpdateQueue")
	private Queue bulkUpdateQueue; // 一括更新実行用キュー

	@Resource(mappedName = "java:/jms/BulkUpdateQueueFactory" )
	private ConnectionFactory bulkUpdateQueueFactory; // 一括更新実行用キュー接続ファクトリ

	private static final String ID_PREFIX = "S";
	private static final String FILE_SAVE_ENTITY_NAME = "LICENSE";
	private static final String FILE_SAVE_ENTITY_AP_NAME = "AP_LICENSE";

	// 履歴作成用
	private static final String HIST_ENTITY_NAME = "LICENSE";
	private static final String HIST_OPERATION_CREATE = "新規作成";
	private static final String HIST_OPERATION_UPDATE = "更新";
	private static final String HIST_OPERATION_DELETE = "抹消";
	private static final String HIST_OPERATION_RESTORE = "抹消取消";
	private static final String HIST_OPERATION_APPLY = "申請";
	private static final String hIST_OPERATION_APPROVE = "作業保留";
	private static final String HIST_OPERATION_SENDBACK = "差戻し";
	private static final String HIST_OPERATION_CANCEL_APPLY = "引戻し";
	private static final String HIST_OPERATION_CREATE_LICENSE = "ﾗｲｾﾝｽ新規登録";
	private static final String HIST_OPERATION_CREATE_LICENSE_ALLOC = "ﾗｲｾﾝｽ割当";
	private static final String HIST_OPERATION_DELETE_LICENSE_ALLOC = "ﾗｲｾﾝｽ割当解除";
	private static final String HIST_OPERATION_CREATE_ALLOC_UPGRADE = "ｱｯﾌﾟｸﾞﾚｰﾄﾞ割当";
	private static final String HIST_OPERATION_DELETE_ALLOC_UPGRADE = "ｱｯﾌﾟｸﾞﾚｰﾄﾞ割当解除";
	private static final String HIST_OPERATION_BOTH_ALLOC_UPGRADE = "ｱｯﾌﾟｸﾞﾚｰﾄﾞ割当/割当解除";
	private static final String HIST_OPERATION_CREATE_ALLOC_UPGRADE_RELATION = "ｱｯﾌﾟｸﾞﾚｰﾄﾞ割当_有効数変更";
	private static final String HIST_OPERATION_DELETE_ALLOC_UPGRADE_RELATION = "ｱｯﾌﾟｸﾞﾚｰﾄﾞ割当解除_有効数変更";
	private static final String HIST_OPERATION_AP_GET_JOIN = "取得申請紐付";
	private static final String HIST_OPERATION_AP_GET_JOIN_CANCEL = "取得申請紐付解除";

	// 取得申請側の履歴
	private static final String HIST_OPERATION_CREATE_AP_LICENSE = "登録申請作成(ﾗｲｾﾝｽ)";
	private static final String HIST_OPERATION_DELETE_AP_LICENSE = "登録申請削除(ﾗｲｾﾝｽ)";
	private static final String HIST_OPERATION_JOIN_AP_GET_TAN = "ﾗｲｾﾝｽ紐付";
	private static final String HIST_OPERATION_CANCEL_JOIN_AP_GET_TAN = "ﾗｲｾﾝｽ紐付解除";


	// 登録申請用
	private static final String HIST_ENTITY_NAME_AP_PREFIX = "AP_";
	private static final String ID_PREFIX_AP_SUFFIX = "W";

	private static final int UPGRADE_ALLOC_OPERATION_TYPE_NO = 0; // アップグレード割当変更無し
	private static final int UPGRADE_ALLOC_OPERATION_TYPE_YES1 = 1; // アップグレード割当変更有り
	private static final int UPGRADE_ALLOC_OPERATION_TYPE_YES2 = 2; // アップグレード割当変更有り＆消費数変更有り

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#searchLicense(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.license.LicenseSC, boolean)
	 */
	public List<LicenseSR> searchLicense(String loginStaffCode, String accessLevel, LicenseSC searchParam, boolean isAp) {
		try {
			LicenseDAO licenseDAO = new LicenseDAO();
			replacePluralSearchCondition(searchParam);
			return licenseDAO.selectLicenseList(loginStaffCode, accessLevel, searchParam, isAp);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス検索" + (isAp ? "(登録申請)" : "")), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createLicenseCsv(java.lang.String, java.lang.String, java.util.List, jp.co.ctcg.easset.dto.license.LicenseSC, boolean)
	 */
	public String createLicenseCsv(String loginStaffCode, String accessLevel, List<String> outputPropList, LicenseSC searchParam, boolean isAp) {
		try {
			LicenseDAO licenseDAO = new LicenseDAO();
			replacePluralSearchCondition(searchParam);
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = licenseDAO.createLicenseListCsv(loginStaffCode, accessLevel, outputPropList, searchParam, isAp);

			//////////////////////////////////// 操作ログ作成
			StringBuffer propStr = new StringBuffer();
			if(outputPropList != null) {
				for(int i = 0; i < outputPropList.size(); i++) {
					if(propStr.length() > 0) propStr.append(" ");
					propStr.append(outputPropList.get(i));
				}
			}
			histSession.createOpLog(loginStaffCode, (isAp ? Constants.COM_OP_FUNCTION_AP_LICENSE_SEARCH : Constants.COM_OP_FUNCTION_LICENSE_SEARCH), Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam) + ",outputProperty:" + propStr.toString());

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス" + (isAp ? "(登録申請)" : "") + "ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス" + (isAp ? "(登録申請)" : "") + "ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createAllocLicenseCsv(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.license.LicenseSC)
	 */
	public String createAllocLicenseCsv(String loginStaffCode, String accessLevel, LicenseSC searchParam) {
		try {
			LicenseDAO licenseDAO = new LicenseDAO();
			replacePluralSearchCondition(searchParam);

			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = licenseDAO.createAllocLicenseListCsv(loginStaffCode, accessLevel, searchParam);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_LICENSE_REPORT_ALLOC_LICENSE, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam));

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "割当情報(ライセンスから検索)" + "ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "割当情報(ライセンスから検索)" + "ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createUpgradeCsv(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.license.LicenseSC)
	 */
	public String createUpgradeCsv(String loginStaffCode, String accessLevel, LicenseSC searchParam) {
		try {
			LicenseDAO licenseDAO = new LicenseDAO();
			replacePluralSearchCondition(searchParam);

			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = licenseDAO.createUpgradeListCsv(loginStaffCode, accessLevel, searchParam);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_LICENSE_REPORT_UPGRADE, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam));

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "アップグレード情報" + "ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "アップグレード情報" + "ダウンロード"), e);
		}
	}

	/**
	 * 検索条件の名称複数入力（資産分類、オフィス）項目の名称をコードに変換する。
	 * @param searchParam
	 */
	private void replacePluralSearchCondition(LicenseSC searchParam) {
		// 取得形態 名称⇒コード
		List<String> licLicenseTypePluralList = Function.getPluralList(searchParam.getLicLicenseTypePlural());
		if(licLicenseTypePluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < licLicenseTypePluralList.size(); i++) {
				if(!Function.nvl(licLicenseTypePluralList.get(i), "").equals("")) {
					String internalCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LICENSE_TYPE, null, licLicenseTypePluralList.get(i));
					if(!Function.nvl(internalCode, "").equals("")) {
						replaceStr.append(internalCode + " ");
					} else {
						replaceStr.append("--------NOT_EXISTS--------" + " ");
					}
				}
			}

			searchParam.setLicLicenseTypePlural(replaceStr.toString());
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#getLicense(java.lang.String, boolean)
	 */
	public License getLicense(String licenseId, boolean isAp) {
		return getLicense(licenseId, false, isAp);
	}

	/**
	 *
	 * @param assetId ライセンス管理番号
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private License getLicense(String licenseId, boolean lockFlag, boolean isAp){
		try{

			LicenseDAO licenseDAO = new LicenseDAO();

			License license = licenseDAO.selectLicense(licenseId, lockFlag, isAp);

			if(license != null){
				// 明細
				if(!isAp){
					license.setLicenseLineQtyRental(licenseDAO.selectLicenseLineQty(licenseId, Constants.LICENSE_LINE_QTY_TYPE_RENTAL));			// 数量管理明細
					license.setLicenseLineUpgSrc(licenseDAO.selectLicenseLineUpgSrc(licenseId));	// アップグレード元明細
					license.setLicenseLineUpgDst(licenseDAO.selectLicenseLineUpgDst(licenseId));	// アップグレード先明細
				}
				else{
					license.setApLicenseLineAtt(licenseDAO.selectApLicenseLineAtt(licenseId));	// 添付明細
				}
			}

			return license;

		} catch (SQLException e) {
			//	ライセンス検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス取得" + (isAp ? "(登録申請)" : "")), e);
		}

	}

	/**
	 * 不正セット項目値の調整/許諾情報セット
	 * @param obj ライセンスデータ
	 * @param licenseDAO データアクセス
	 * @param isAp ture:ライセンス登録申請,false:ライセンス
	 * @throws SQLException
	 */
	private void setPropertyAdjust(License obj, LicenseDAO licenseDAO, boolean isAp) throws SQLException {
		// フラグがNULLの場合デフォルトセット
		if(obj.getLicHrdBandleFlag() == null) obj.setLicHrdBandleFlag(Constants.FLAG_NO);
		if(obj.getLicUpgradeFlag() == null) obj.setLicUpgradeFlag(Constants.FLAG_NO);
		if(obj.getLicTermFlag() == null) obj.setLicTermFlag(Constants.FLAG_NO);
		if(obj.getLicUseConsentFlag() == null) obj.setLicUseConsentFlag(Constants.FLAG_NO);
		if(obj.getLicDowngradeConsentFlag() == null) obj.setLicDowngradeConsentFlag(Constants.FLAG_NO);
		if(obj.getTrmAutoAllocFlag() == null) obj.setTrmAutoAllocFlag(Constants.FLAG_NO);
		if(obj.getUseRentalFlag() == null) obj.setUseRentalFlag(Constants.FLAG_NO);
		if(obj.getDeleteFlag() == null) obj.setDeleteFlag(Constants.FLAG_NO);

		// ライセンス数区分が無限の場合は保有数null
		if(Function.nvl(obj.getLicQuantityType(), "").equals(Constants.LIC_QUANTITY_TYPE_UNLIMITED)) {
			obj.setLicQuantity(null);
		}

		// 割当可不可取得
		boolean allocLic = true;
		if(!Function.nvl(obj.getLicLicenseType(), "").equals("")) {
			CodeName licenseType = masterSession.getCodeName(Constants.CATEGORY_CODE_LICENSE_TYPE, obj.getLicLicenseType(), null, null);
			if(licenseType != null) {
				if(Function.nvl(licenseType.getValue2(), "").equals(Constants.FLAG_NO)) {
					allocLic = false;
				}
			}
		}


		if(allocLic) {
			// 割当可のライセンスは使用状況ファイルクリア
			obj.setLicUseFileId(null);
			obj.setLicUseFileIdTmp(null);
			obj.setLicUseFileName(null);
		} else {
			// 割当不可のライセンスは貸出明細の消費数クリア
			List<LicenseLineQty> rentalQtyList = obj.getLicenseLineQtyRental();
			if(rentalQtyList != null && rentalQtyList.size() > 0 ) {
				for(int i = 0; i < rentalQtyList.size(); i++) {
					rentalQtyList.get(i).setLicLineUseQuantity(null);
				}
			}
		}

		// タームライセンス
		if(obj.getLicTermFlag().equals(Constants.FLAG_NO)) {
			obj.setTrmContractType(null);
			obj.setTrmStartDate(null);
			obj.setTrmEndDate(null);
			obj.setTrmAlertDate(null);
			obj.setTrmParentLicenseId(null);
			obj.setTrmAutoAllocFlag(null);
		}
		else if(!Function.nvl(obj.getTrmContractType(), "").equals(Constants.LICENSE_TERM_CONTRACT_TYPE_UPDATE)) {
			// タームライセンス契約区分が更新以外はクリア
			obj.setTrmParentLicenseId(null);
			obj.setTrmAutoAllocFlag(null);
		}

		// 許諾区分
		String useType = Function.nvl(obj.getUseType(), "");
		if(useType.equals(Constants.LICENSE_USE_TYPE_ALL_COMPANY) || useType.equals("")) {
			obj.setUseCompanyCode(null);
			obj.setUseCompanyName(null);
			obj.setUseSectionCode(null);
			obj.setUseSectionName(null);
			obj.setUseSectionYear(null);
			obj.setUseRentalFlag(Constants.FLAG_NO);
			obj.setLicenseLineQtyRental(null);
		}
		if(useType.equals(Constants.LICENSE_USE_TYPE_COMPANY)) {
			obj.setUseSectionCode(null);
			obj.setUseSectionName(null);
			obj.setUseSectionYear(null);
			obj.setUseRentalFlag(Constants.FLAG_NO);
			obj.setLicenseLineQtyRental(null);
		}

		// 貸出を行わない場合は貸出明細クリア
		if(Function.nvl(obj.getUseRentalFlag(), "").equals(Constants.FLAG_NO)) {
			obj.setLicenseLineQtyRental(new ArrayList<LicenseLineQty>());
		}

		// 許諾部署の数量管理明細取得
		if(!isAp) {
			if(!Function.nvl(obj.getLicenseId(), "").equals("")) { // 更新の場合は現情報を一度取得
				List<LicenseLineQty> qtyList = licenseDAO.selectLicenseLineQty(obj.getLicenseId(), Constants.LICENSE_LINE_QTY_TYPE_USE);
				if(qtyList != null && qtyList.size() > 0) {
					obj.setLicenseQtyUse(qtyList.get(0));
				}
			}
			setLicenseQtyUsaeProperty(obj, licenseDAO); // 許諾部署の貸出明細情報セット
		}

	}

	/**
	 * 許諾部署の数量管理明細情報セット
	 * @param obj
	 */
	private void setLicenseQtyUsaeProperty(License obj, LicenseDAO licenseDAO) throws SQLException {
		LicenseLineQty useQty = obj.getLicenseQtyUse();
		if(useQty == null) {
			useQty = new LicenseLineQty();
			obj.setLicenseQtyUse(useQty);
		}

		// 許諾会社/部署
		useQty.setUseCompanyCode(obj.getUseCompanyCode());
		useQty.setUseCompanyName(obj.getUseCompanyName());
		useQty.setUseSectionCode(obj.getUseSectionCode());
		useQty.setUseSectionName(obj.getUseSectionName());
		useQty.setUseSectionYear(obj.getUseSectionYear());


		// 貸出数取得
		long rentalQtyTotal = 0;
		List<LicenseLineQty> rentalQtyList = obj.getLicenseLineQtyRental();
		if(rentalQtyList != null && rentalQtyList.size() > 0 ) {
			for(int i = 0; i < rentalQtyList.size(); i++) {
				long rentalQty = Function.nvl(rentalQtyList.get(i).getLicLineEnableQuantity(), Long.valueOf(0)).longValue();
				rentalQtyTotal += rentalQty;
			}
		}
		// 許諾部署有効数セット
		if(Function.nvl(obj.getLicUpgradeFlag(), "").equals(Constants.FLAG_YES)) { // アップグレードライセンス
			List<LicenseLineUpg> upgFromLine = obj.getLicenseLineUpgSrc();
			if(upgFromLine != null && upgFromLine.size() > 0) {
				// アップグレード数取得
				long upgFromQtyTotal = 0;
				boolean isUpgFromQtyNull = false;

				for(int i = 0; i < upgFromLine.size(); i++) {
					long upgFromQty = Function.nvl(upgFromLine.get(i).getLicUpgradeQuantity(), Long.valueOf(0)).longValue();
					upgFromQtyTotal += upgFromQty;

					if(upgFromLine.get(i).getLicUpgradeQuantity() == null) isUpgFromQtyNull = true; // UPG割当数NULL(無限)
				}

				if(Function.nvl(obj.getLicQuantityType(), "").equals(Constants.LIC_QUANTITY_TYPE_LIMIT)) { // 有限
					// 「許諾部署数量 = アップグレード割当数 - 数量管理の貸出数合計」
					useQty.setLicLineEnableQuantity(Long.valueOf(upgFromQtyTotal - rentalQtyTotal));
				} else { // 無限
					if(isUpgFromQtyNull) {
						// 「無制限(NULL)」
						useQty.setLicLineEnableQuantity(null);
					} else {
						// 「許諾部署数量 = アップグレード割当数 - 数量管理の貸出数合計」
						useQty.setLicLineEnableQuantity(Long.valueOf(upgFromQtyTotal - rentalQtyTotal));
					}
				}
			} else {
				useQty.setLicLineEnableQuantity(Long.valueOf(0 - rentalQtyTotal)); // UPG割当無し
			}
		} else { // ベースライセンス
			if(Function.nvl(obj.getLicQuantityType(), "").equals(Constants.LIC_QUANTITY_TYPE_LIMIT)) { // 有限
				// 「許諾部署数量 = ヘッダ保有数 - 数量管理の貸出数合計」
				long licQtyTotal = Function.nvl(obj.getLicQuantity(), Long.valueOf(0)).longValue();
				useQty.setLicLineEnableQuantity(Long.valueOf(licQtyTotal - rentalQtyTotal));
			} else { // 無限
				// 「無制限(NULL)」
				useQty.setLicLineEnableQuantity(null);
			}
		}

		if(!Function.nvl(obj.getLicLicenseType(), "").equals("")) {
			CodeName licenseType = masterSession.getCodeName(Constants.CATEGORY_CODE_LICENSE_TYPE, obj.getLicLicenseType(), null, null);
			if(licenseType != null) {
				// 消費数(ユーザライセンスの場合)
				if(Function.nvl(licenseType.getValue2(), "").equals(Constants.FLAG_NO)) {
					useQty.setLicLineUseQuantity(obj.getLicUseQuantity());
				}
				//	新規登録？
				if(!Function.nvl(obj.getLicenseId(), "").equals("")){
					// 消費数(ユーザライセンス⇒マシン(CPU)ライセンスに切替えた場合)
					//	既存のDB情報取得
					License old = licenseDAO.selectLicense(obj.getLicenseId(), false, false);
					if(old != null){
						if(!Function.nvl(old.getLicLicenseType(), "").equals(obj.getLicLicenseType())
						&& Function.nvl(licenseType.getValue2(), "").equals(Constants.FLAG_YES)
						){
							useQty.setLicLineUseQuantity(0L);
						}
					}
				}
			}
		}

		// ライセンス有効数（PGロジックでのみ使用）
		if(useQty.getLicLineEnableQuantity() == null) { // 無限
			obj.setLicEnableQuantity(null);
		} else {
			obj.setLicEnableQuantity(Long.valueOf(rentalQtyTotal + useQty.getLicLineEnableQuantity().longValue()));
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createLicense(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.license.License, boolean)
	 */
	public String createLicense(String loginStaffCode, String accessLevel, License obj, boolean isAp) throws AppException {
		return createLicense(loginStaffCode, accessLevel, obj, isAp, true);
	}

	/**
	 * ライセンス作成本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj ライセンスデータ
	 * @param isAp ture:ライセンス登録申請,false:ライセンス
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータのライセンス番号
	 */
	private String createLicense(String loginStaffCode, String accessLevel, License obj, boolean isAp, boolean isHistCreate) throws AppException {
		try {
			LicenseDAO licenseDAO = new LicenseDAO();

			//////////////////////////////////// 他エンティティ更新
			if(!isAp) { // ライセンス新規登録
				// 登録申請を更新
				obj.setApStatus(Constants.AP_STATUS_ASSET_APPROVE);
				updateLicense(loginStaffCode, accessLevel, obj, true, true, true, HIST_OPERATION_CREATE_LICENSE, null);
			}

			//////////////////////////////////// 固定値セット
			if(!isAp) { // ライセンス新規登録
				// 登録申請番号セット＆ライセンス管理番号初期化
				obj.setRegistApplicationId(obj.getLicenseId());
				obj.setLicenseId(null);
			}

			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setCreateDate(sysdate);
			obj.setCreateStaffCode(loginStaffCode);
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			// 不正セット項目値の調整/許諾情報セット
			setPropertyAdjust(obj, licenseDAO, isAp);

			//////////////////////////////////// ライセンス登録特殊セット
			if(!isAp) {
				//////////////////////////////////// 手入力のメーカー・ソフトウェアをマスタ登録
				// メーカー
				if(obj.getSoftwareMakerId() == null) {
					try {
						// 同一名称で既に登録されているか確認
						Long softwareMakerId = getSoftwareMakerIdByName(obj.getSoftwareMakerName());

						if(softwareMakerId == null) {
							// 値セット
							SoftwareMaker softwareMaker = new SoftwareMaker();
							softwareMaker.setSoftwareMakerName(obj.getSoftwareMakerName());
							softwareMaker.setApStaffCode(obj.getApStaffCode());
							softwareMaker.setApDate(obj.getApDate());

							// 作成
							softwareMakerId = createSoftwareMaker(loginStaffCode, softwareMaker);
						}

						// idをライセンスにセット
						obj.setSoftwareMakerId(softwareMakerId);
					} catch (AppException e) {
						String errMessage = e.getMessage();
						errMessage = Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ソフトウェア-メーカー", "手入力ソフトウェアメーカーのマスタ登録時に以下のエラーが発生しました。") + errMessage;
						throw new AppException(errMessage);
					}
				}
				// ソフトウェア
				if(obj.getSoftwareId() == null) {
					try {
						Long softwareId = getSoftwareIdByName(obj.getSoftwareMakerId(), obj.getSoftwareName());

						if(softwareId == null) {
							// 値セット
							Software software = new Software();
							software.setSoftwareName(obj.getSoftwareName());
							software.setSoftwareMakerId(obj.getSoftwareMakerId());
							software.setSoftwareMakerName(obj.getSoftwareMakerName());
							software.setApStaffCode(obj.getApStaffCode());
							software.setApDate(obj.getApDate());

							// 作成
							softwareId = createSoftware(loginStaffCode, software);
						}

						// idをライセンスにセット
						obj.setSoftwareId(softwareId);
					} catch (AppException e) {
						String errMessage = e.getMessage();
						errMessage = Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ソフトウェア-ソフトウェア", "手入力ソフトウェアのマスタ登録時に以下のエラーが発生しました。") + errMessage;
						throw new AppException(errMessage);
					}
				}
			}

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			if(isAp) { // 登録申請
				errMsg.append(validateApGeTanLineLic(obj));
			}

			errMsg.append(validateLicense(loginStaffCode, accessLevel, obj, isAp, null));

			// 数量整合性チェック
			if(!isAp) errMsg.append(validateLicenseQuantity(loginStaffCode, accessLevel, obj, null, null));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// IDの採番
			String licenseId = masterSession.nextValId(ID_PREFIX + (isAp ? ID_PREFIX_AP_SUFFIX : ""));

			//////////////////////////////////// データ作成
			obj.setVersion(1); // バージョンセット
			obj.setLicenseId(licenseId); // IDセット

			// 添付ファイル保存
			if(Function.nvl(obj.getLicUseFileId(), "").equals("") && !Function.nvl(obj.getLicUseFileIdTmp(), "").equals("")) { // 一時領域にのみファイルが存在する（保存前）
				// 一時領域 ⇒ 本領域にファイルコピー
				masterSession.fileCommit(obj.getLicUseFileIdTmp(), FILE_SAVE_ENTITY_NAME, obj.getLicenseId());
				obj.setLicUseFileId(obj.getLicUseFileIdTmp());
			}

			// int createUpgradeAllocOperationType = UPGRADE_ALLOC_OPERATION_TYPE_NO; // アップグレード割当有無
			licenseDAO.insertLicense(obj, isAp); // ヘッダ作成
			// createUpgradeAllocOperationType = createLine(loginStaffCode, accessLevel, obj ,licenseDAO, obj.getLicenseId(), isAp); // 明細データ作成

			////////////////////////////////////履歴作成
			//	履歴作成
			if(isHistCreate) {
				// String operation = HIST_OPERATION_CREATE;
				// if(createUpgradeAllocOperationType != UPGRADE_ALLOC_OPERATION_TYPE_NO) operation += "/" + HIST_OPERATION_CREATE_ALLOC_UPGRADE;
				histSession.createHistData((isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + HIST_ENTITY_NAME, licenseId, HIST_OPERATION_CREATE, null);
			}

			//////////////////////////////////// 他エンティティ更新
			if(isAp) { // 登録申請
				// 取得申請の登録情報更新
				if(obj.getApGetTanLineLicId() != null) {
					apGetTanSession.updateApGetTanLineLicRegist(loginStaffCode, obj.getGetApplicationId(), obj.getApGetTanLineLicId(), 1, HIST_OPERATION_CREATE_AP_LICENSE);
				}
			}

			//////////////////////////////////// ターム契約更新による割当切り替え
			if(!isAp) { // ライセンス新規登録
				// 割当の自動切換指定
				if(Function.nvl(obj.getTrmAutoAllocFlag(), "").equals(Constants.FLAG_YES) &&
				   !Function.nvl(obj.getTrmParentLicenseId(), "").equals("")) {
					String parentLicenseId = obj.getTrmParentLicenseId();
					try {
						changeLicenseAlloc(loginStaffCode, accessLevel, parentLicenseId, obj.getLicenseId());
					} catch (AppException e) {
						String errMessage = e.getMessage();
						errMessage = Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ライセンス・資産-タームライセンス", "更新元ライセンスの割当自動切換時に以下のエラーが発生しました。") + errMessage;
						throw new AppException(errMessage);
					}
				}
			}

			return obj.getLicenseId();

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス作成" + (isAp ? "(登録申請)" : "")), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#updateLicense(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.license.License, boolean)
	 */
	public void updateLicense(String loginStaffCode, String accessLevel, License obj, boolean isAp) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateLicense(loginStaffCode, accessLevel, obj, isAp, true, true, null, null);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#updateAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean, java.lang.String)
	 */
	public void updateLicense(String loginStaffCode, String accessLevel, License obj, boolean isAp, Set<String> updatePropSet) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateLicense(loginStaffCode, accessLevel, obj, isAp, true, true, null, updatePropSet);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#updateLicense(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.license.License, boolean, java.lang.String)
	 */
	public void updateLicense(String loginStaffCode, String accessLevel, License obj, boolean isAp, String operation) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateLicense(loginStaffCode, accessLevel, obj, isAp, false, true, operation, null);
	}

	/**
	 * ライセンス更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj ライセンスデータ
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException
	 */
	private void updateLicense(String loginStaffCode, String accessLevel, License obj, boolean isAp, boolean isLineUpdate, boolean isHistCreate, String operation, Set<String> updatePropSet) throws AppException {
		try {
			LicenseDAO licenseDAO = new LicenseDAO();

			License licenseOld = getLicense(obj.getLicenseId(), true, isAp); // 現データの取得

			//////////////////////////////////// 固定値セット
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			// 不正セット項目値の調整/許諾情報セット
			setPropertyAdjust(obj, licenseDAO, isAp);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != licenseOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// バリデーション(登録画面更新の際のみ：連携等による更新時は行わない)
			if(isLineUpdate) {
				if(!Function.nvl(operation, "").equals(HIST_OPERATION_CREATE_ALLOC_UPGRADE)
					&& !Function.nvl(operation, "").equals(HIST_OPERATION_DELETE_ALLOC_UPGRADE)) {
					// アップグレード以外の場合
					errMsg.append(validateLicense(loginStaffCode, accessLevel, obj, isAp, updatePropSet));
				}

				// 数量整合性チェック
				if(!isAp) errMsg.append(validateLicenseQuantity(loginStaffCode, accessLevel, obj, licenseOld, updatePropSet));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 取得申請書番号の紐付け直し
			if(!isAp) { // ライセンス
				// 取得申請の選択あり or 取得申請クリア
				if(obj.getApGetTanLineLicId() != null
					|| (
							!Function.nvl(licenseOld.getGetApplicationId(), "").equals("")
							&& Function.nvl(obj.getGetApplicationId(), "").equals("")
						)
				) {
					License oldApObj = null;
					if(!Function.nvl(licenseOld.getRegistApplicationId(), "").equals("")) {
						oldApObj = getLicense(licenseOld.getRegistApplicationId(), true, true); // 登録申請取得
					}

					// 選択した取得申請ライセンス明細が同一の場合は以下の処理を行わない
					if(!(oldApObj != null && Function.nvl(oldApObj.getApGetTanLineLicId(), Long.valueOf(-1)).longValue() == Function.nvl(obj.getApGetTanLineLicId(), Long.valueOf(-1)).longValue())) {
						//////////////////////////////////// 変更前の紐付け解除
						if(oldApObj != null && oldApObj.getApGetTanLineLicId() != null) { // 取得申請の紐付け有り
							// 取得申請明細の登録数戻し
							apGetTanSession.updateApGetTanLineLicRegist(loginStaffCode, oldApObj.getGetApplicationId(), oldApObj.getApGetTanLineLicId(), -1, HIST_OPERATION_CANCEL_JOIN_AP_GET_TAN);
							// 登録申請の取得申請明細紐付け解除
							oldApObj.setGetApplicationId(null);
							oldApObj.setApGetTanLineLicId(null);
							updateLicense(loginStaffCode, accessLevel, oldApObj, true, HIST_OPERATION_AP_GET_JOIN_CANCEL);
						}

						//////////////////////////////////// 変更後の紐付け
						if(obj.getApGetTanLineLicId() != null) {
							License apObj = getLicense(obj.getRegistApplicationId(), true, true); // 登録申請取得
							if(apObj == null) { // 登録申請がない場合は作成（＆取得申請紐付け）
								// 登録申請データをライセンスからコピー作成
								apObj = (License) CopyUtil.deepCopy(obj);

								// 登録申請固有データのセット
								User user = masterSession.getStaff(null, loginStaffCode);
								CodeName currentYear = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
								apObj.setApStatus(Constants.AP_STATUS_LICENSE_APPROVE);
								apObj.setApDate(new Date());
								apObj.setApCreateStaffCode(loginStaffCode);
								apObj.setApCreateCompanyCode(user.getCompanyCode());
								apObj.setApCreateSectionCode(user.getSectionCode());
								apObj.setApCreateSectionYear(Integer.valueOf(currentYear.getValue1()));

								apObj.setLicenseId(null); // 登録申請新規作成ロジック用にクリア
								// 登録申請作成
								String apLicenseId = createLicense(Constants.STAFF_CODE_SYSTEM, accessLevel, apObj, true);

								apObj = getLicense(apLicenseId, true, true);

								obj.setRegistApplicationId(apLicenseId);
							} else { // 登録申請がある場合は取得申請との紐付けセット
								// 登録申請の取得申請明細紐付
								apObj.setGetApplicationId(obj.getGetApplicationId());
								apObj.setApGetTanLineLicId(obj.getApGetTanLineLicId());

								//////////////////////////////////// 登録数量バリデーション
								String err = validateApGeTanLineLic(apObj);

								if(err.length() > 0) { // バリデーションエラー有
									throw new AppException(err);
								}

								// 取得申請明細の登録数追加
								apGetTanSession.updateApGetTanLineLicRegist(loginStaffCode, apObj.getGetApplicationId(), apObj.getApGetTanLineLicId(), 1, HIST_OPERATION_JOIN_AP_GET_TAN);

								// 登録申請の更新
								updateLicense(loginStaffCode, accessLevel, apObj, true, HIST_OPERATION_AP_GET_JOIN);
							}
						}
					}
				}
			}

			//////////////////////////////////// データ更新
			obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1); // バージョンセット

			// 添付ファイル保存
			if(Function.nvl(obj.getLicUseFileId(), "").equals("") && !Function.nvl(obj.getLicUseFileIdTmp(), "").equals("")) { // 一時領域にのみファイルが存在する（保存前）
				// 一時領域 ⇒ 本領域にファイルコピー
				masterSession.fileCommit(obj.getLicUseFileIdTmp(), FILE_SAVE_ENTITY_NAME, obj.getLicenseId());
				obj.setLicUseFileId(obj.getLicUseFileIdTmp());
			}

			licenseDAO.updateLicense(obj, isAp); // ヘッダ更新

			int createUpgradeAllocOperationType = UPGRADE_ALLOC_OPERATION_TYPE_NO; // アップグレード割当有無
			int deleteUpgradeAllocOperationType = UPGRADE_ALLOC_OPERATION_TYPE_NO; // アップグレード割当解除有無
			// 抹消、抹消取消時は明細を更新しない
			if(isLineUpdate){
				// 明細を一度削除
				deleteUpgradeAllocOperationType = deleteLine(loginStaffCode, accessLevel,licenseDAO, obj, isAp);

				// 明細データ作成
				createUpgradeAllocOperationType = createLine(loginStaffCode, accessLevel, obj, licenseDAO, obj.getLicenseId(), isAp);
			}
			//	ライセンスで明細を更新しない？
			else if(!isAp && !isLineUpdate){
				//	更新対象データの使用部署とDBの使用部署に違いあり？
				if( !Function.nvl(licenseOld.getUseCompanyCode(), "").equals(Function.nvl(obj.getUseCompanyCode(), ""))
				||  !Function.nvl(licenseOld.getUseSectionCode(), "").equals(Function.nvl(obj.getUseSectionCode(), ""))
				||  !Function.nvl(licenseOld.getUseSectionYear(), Integer.valueOf(0)).equals(Function.nvl(obj.getUseSectionYear(), Integer.valueOf(0)))
				){
					//	ライセンス_数量管理明細テーブル使用部署更新(許諾部署のみ)
					updateLicenseLineQtyUseSection(loginStaffCode, accessLevel, obj, licenseDAO);
				}
			}

			////////////////////////////////////履歴作成
			if(isHistCreate) {

				StringBuffer lineChangeColumnName = new StringBuffer();

				if(Function.nvl(operation, "").equals(HIST_OPERATION_CREATE_LICENSE_ALLOC)
					|| Function.nvl(operation, "").equals(HIST_OPERATION_DELETE_LICENSE_ALLOC)) {
					// 割当、割当解除の変更項目は”消費数”で固定
					if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
					lineChangeColumnName.append("消費数");
				} else if(Function.nvl(operation, "").startsWith(HIST_OPERATION_CREATE_ALLOC_UPGRADE)
					|| Function.nvl(operation, "").startsWith(HIST_OPERATION_DELETE_ALLOC_UPGRADE)) {
					if(Function.nvl(obj.getLicUpgradeFlag(), "").equals(Constants.FLAG_YES)) {
						if(Function.nvl(operation, "").equals(HIST_OPERATION_CREATE_ALLOC_UPGRADE_RELATION)
							|| Function.nvl(operation, "").equals(HIST_OPERATION_DELETE_ALLOC_UPGRADE_RELATION)) {
							// アップグレード先から除外されたライセンスの関連更新の場合変更項目は”有効数”で固定
							if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
							lineChangeColumnName.append("有効数");


							operation = operation.replaceAll("_有効数変更", ""); // 有効数変更判別用の文言は削除
						}
					}
					// アップグレード割当、割当解除関連更新ライセンスの変更項目は”アップグレード明細”で固定
					if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
					lineChangeColumnName.append("アップグレード明細");
				} else {
					if(operation == null) operation = HIST_OPERATION_UPDATE;
					// 更新時のアップグレード割当明細操作を追加
					if(createUpgradeAllocOperationType != UPGRADE_ALLOC_OPERATION_TYPE_NO && deleteUpgradeAllocOperationType != UPGRADE_ALLOC_OPERATION_TYPE_NO) {
						operation += "/" + HIST_OPERATION_BOTH_ALLOC_UPGRADE;
					} else if(createUpgradeAllocOperationType != UPGRADE_ALLOC_OPERATION_TYPE_NO) {
						operation += "/" + HIST_OPERATION_CREATE_ALLOC_UPGRADE;
					} else if(deleteUpgradeAllocOperationType != UPGRADE_ALLOC_OPERATION_TYPE_NO) {
						operation += "/" + HIST_OPERATION_DELETE_ALLOC_UPGRADE;
					}

					// アップグレードライセンスはアップグレード割当時に有効数変更
					if(createUpgradeAllocOperationType == UPGRADE_ALLOC_OPERATION_TYPE_YES2 || deleteUpgradeAllocOperationType == UPGRADE_ALLOC_OPERATION_TYPE_YES2) {
						if(Function.nvl(obj.getLicUpgradeFlag(), "").equals(Constants.FLAG_YES)) {
							if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
							lineChangeColumnName.append("有効数");
						}
					}
					if(Function.nvl(obj.getLicUseQuantity(), Long.valueOf(0)).longValue() != Function.nvl(licenseOld.getLicUseQuantity(), Long.valueOf(0)).longValue()) {
						// 消費数の変動有り（ユーザライセンスで手入力のケースを想定）
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("消費数");
					}
				}

				if(isLineUpdate) {
					// 明細変更確認
					if(Function.isListChange(obj.getLicenseLineQtyRental(), licenseOld.getLicenseLineQtyRental())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("貸出明細");
					}
					if(Function.isListChange(obj.getLicenseLineUpgDst(), licenseOld.getLicenseLineUpgDst())
						|| Function.isListChange(obj.getLicenseLineUpgSrc(), licenseOld.getLicenseLineUpgSrc())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("アップグレード明細");
					}
				}

				histSession.createHistData((isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + HIST_ENTITY_NAME, obj.getLicenseId(), operation, lineChangeColumnName.toString());
			}

			//////////////////////////////////// ターム契約更新による割当切り替え
			if(!isAp) { // ライセンス
				// 割当の自動切換指定
				if(Function.nvl(obj.getTrmAutoAllocFlag(), "").equals(Constants.FLAG_YES) &&
				   !Function.nvl(obj.getTrmParentLicenseId(), "").equals("")) {
					String parentLicenseId = obj.getTrmParentLicenseId();
					try {
						changeLicenseAlloc(loginStaffCode, accessLevel, parentLicenseId, obj.getLicenseId());
					} catch (AppException e) {
						String errMessage = e.getMessage();
						errMessage = Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ライセンス・資産-タームライセンス", "更新元ライセンスの割当自動切換時に以下のエラーが発生しました。") + errMessage;
						throw new AppException(errMessage);
					}
				}
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#deleteLicenseLogical(java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.util.List)
	 */
	public void deleteLicenseLogical(String loginStaffCode, String accessLevel, Date deleteDate, String deleteReason,  List<License> licenseList) throws AppException {
		try {

			LicenseDAO licenseDAO = new LicenseDAO();

			//////////////////////////////////// バリデーション
			// 抹消日必須
			StringBuffer errMsg = new StringBuffer();
			if(deleteDate == null) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "抹消日"));
			}
			// 抹消理由必須
			if(Function.nvl(deleteReason, "").equals("")) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "抹消理由"));
			}
			// 抹消理由最大サイズ
			if(Function.nvl(deleteReason, "").getBytes(Constants.DB_CHARSET).length > Constants.MAX_CHAR_SIZE_DELETE_REASON) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MAX_SIZE, "抹消理由", new DecimalFormat().format(Constants.MAX_CHAR_SIZE_DELETE_REASON)));
			}

			if(errMsg.length() > 0) { // エラーあり
				throw new AppException(errMsg.toString());
			}

			errMsg = new StringBuffer();
			for(int i = 0; i < licenseList.size(); i++){
				LicenseSR licenseData = (LicenseSR)licenseList.get(i);
				String licenseId = licenseData.getLicenseId();

				License licenseOld = getLicense(licenseId, true, false); // 現データの取得
				if(licenseOld != null){

					// バージョンチェック
					if(licenseData.getVersion().intValue() != licenseOld.getVersion().intValue()) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, licenseData.getLicenseId(), Msg.ERR_MSG_VER_LIST));
					}

					// 修正権限チェック
					if(!isEditableLicense(loginStaffCode, accessLevel, licenseOld.getLicenseId(), false)) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, licenseData.getLicenseId(), Msg.ERR_MSG_EDIT_AUTH));
					}
				}
			}

			if(errMsg.length() > 0) { // エラーあり
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 更新
			for(int i = 0; i < licenseList.size(); i++) {
				licenseDAO.callDeleteLicenseLogical(loginStaffCode, licenseList.get(i).getLicenseId(), deleteDate, deleteReason);
			}
		} catch (UnsupportedEncodingException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス抹消"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス抹消"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#deleteLicense(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.license.License, boolean)
	 */
	public void deleteLicense(String loginStaffCode, String accessLevel, License obj, boolean isAp) throws AppException {
		try {
			LicenseDAO licenseDAO = new LicenseDAO();

			License licenseOld = getLicense(obj.getLicenseId(), true, isAp); // 現データの取得

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != licenseOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// 修正権限チェック
			if(!isEditableLicense(loginStaffCode, accessLevel, obj.getLicenseId(), isAp)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_EDIT_AUTH));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新(履歴作成用にバージョンアップ)
			// 現データを更新に使用
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			licenseOld.setUpdateDate(sysdate);
			licenseOld.setUpdateStaffCode(loginStaffCode);

			// バージョン
			licenseOld.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);

			// 更新コメント
			licenseOld.setUpdateComment(null);

			licenseDAO.updateLicense(licenseOld, isAp);

			//////////////////////////////////// 履歴作成
			histSession.createHistData((isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + HIST_ENTITY_NAME, obj.getLicenseId(), HIST_OPERATION_DELETE, null);

			//////////////////////////////////// 他エンティティ更新
			if(isAp) { // 登録申請
				if(obj.getApGetTanLineLicId() != null) {
					// 取得申請の登録情報更新
					apGetTanSession.updateApGetTanLineLicRegist(loginStaffCode, obj.getGetApplicationId(), obj.getApGetTanLineLicId(), -1, HIST_OPERATION_DELETE_AP_LICENSE);
				}
			}

			//////////////////////////////////// データ削除
			licenseDAO.deleteLicense(obj.getLicenseId(), isAp);
			if(!isAp) licenseDAO.deleteLicenseLineQty(obj.getLicenseId());
			if(!isAp) licenseDAO.deleteLicenseLineUpg(obj.getLicenseId());
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス削除" + (isAp ? "(登録申請)" : "")), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#restoreLicense(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.license.License)
	 */
	public void restoreLicense(String loginStaffCode, String accessLevel, License obj) throws AppException {
		License objOld = getLicense(obj.getLicenseId(), true, false); // 現データの取得
		/////////////////////////////////////// バリデーション
		StringBuffer errMsg = new StringBuffer();
		// バージョンチェック
		if(obj.getVersion().intValue() != objOld.getVersion().intValue()) {
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
		}

		// 修正権限チェック
		if(!isEditableLicense(loginStaffCode, accessLevel, obj.getLicenseId(), false)) {
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_EDIT_AUTH));
		}

		//	部署年度チェック
		CodeName currentYearCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
		int currentYear = Integer.valueOf(currentYearCodeName.getValue1());
		//	使用部署年度は許諾区分が「部署限定」の時のみ比較
		if(currentYear != obj.getHolSectionYear() ||  (Function.nvl(obj.getUseType(), "").equals(Constants.LICENSE_USE_TYPE_SECTION) && currentYear != obj.getUseSectionYear()) ) {
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "保有(許諾)部署に過去年度の部署が設定されているため、抹消取消できません。"));
		}

/*
				// 一般権限
				if(!Function.isAccessLevelAdmin(accessLevel)) {
					// 資産区分がリース/固定資産の場合
					if(!Function.nvl(obj.getLicAssetType(), "")) {
						if(Function.nvl(obj.getLicAssetType(), "").equals(Constants.LICENSE_ASSET_TYPE_LEASE)
							|| Function.nvl(obj.getLicAssetType(), "").equals(Constants.LICENSE_ASSET_TYPE_TAN)
							|| Function.nvl(obj.getLicAssetType(), "").equals(Constants.LICENSE_ASSET_TYPE_INTAN)) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "資産区分に " + obj.getLicAssetTypeName() + " が設定されているため、抹消取消しできません。"));
						}
					}
				}
*/
		//	エラーあり
		if(errMsg.length() > 0){
			throw new AppException(errMsg.toString());
		}

		/////////////////////////////////// 更新
		obj.setDeleteFlag(Constants.FLAG_NO);
		obj.setDeleteDate(null);
		obj.setDeleteReason(null);

		// 更新コメント
		obj.setUpdateComment(null);

		updateLicense(loginStaffCode, accessLevel, obj, false, HIST_OPERATION_RESTORE);
	}

	/**
	 * 明細作成
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel アクセスレベル
	 * @param obj ライセンスデータ
	 * @param licenseDAO データアクセスOBJ
	 * @param licenseId ライセンス管理番号
	 * @return 0:アップグレード割当解除無し 1:アップグレード割当解除有り 2:アップグレード割当解除有り、有効数変更有
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @throws SQLException
	 * @throws AppException
	 */
	private int createLine(String loginStaffCode, String accessLevel, License obj, LicenseDAO licenseDAO, String licenseId, boolean isAp) throws SQLException, AppException {
		int ret = UPGRADE_ALLOC_OPERATION_TYPE_NO;
		Date sysdate = new Date(); // システム日付取得

		// 登録申請
		if(isAp) {
			// 添付明細作成
			List<ApLicenseLineAtt> attList = obj.getApLicenseLineAtt();
			if(attList != null && attList.size() > 0) {
				for(int i = 0; i < attList.size(); i++) {
					ApLicenseLineAtt row = attList.get(i);
					row.setLicenseId(licenseId); // IDのセット
					row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

					// 更新日・更新者
					row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
					row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
					row.setUpdateDate(sysdate);
					row.setUpdateStaffCode(loginStaffCode);

					if(row.getAttFileId() == null) { // 一時領域にのみファイルが存在する（保存前）
						// 一時領域 ⇒ 本領域にファイルコピー
						masterSession.fileCommit(row.getAttFileIdTmp(), FILE_SAVE_ENTITY_AP_NAME, licenseId);
						row.setAttFileId(row.getAttFileIdTmp());
					}
					licenseDAO.insertApLicenseLineAtt(row);
				}
			}
		}
		else {
			//////////////////////////////////// 数量管理明細
			// 許諾
			List<LicenseLineQty> lineQtyList = new ArrayList<LicenseLineQty>();
			lineQtyList.add(obj.getLicenseQtyUse());
			// 貸出
			List<LicenseLineQty> lineQtyRentalList = obj.getLicenseLineQtyRental();
			if(lineQtyRentalList != null && lineQtyRentalList.size() > 0) {
				lineQtyList.addAll(lineQtyRentalList);
			}

			for(int i = 0; i < lineQtyList.size(); i++) {
				LicenseLineQty row = lineQtyList.get(i);
				row.setLicenseId(obj.getLicenseId()); // IDのセット
				if(i == 0) {
					row.setLineSeq(1); // 行シーケンスの振り直し:許諾
					row.setLicenseLineQtyType(Constants.LICENSE_LINE_QTY_TYPE_USE); // 数量管理明細区分
				} else {
					row.setLineSeq(i); // 行シーケンスの振り直し:貸出
					row.setLicenseLineQtyType(Constants.LICENSE_LINE_QTY_TYPE_RENTAL); // 数量管理明細区分
				}

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				if(row.getLicLineUseQuantity() == null) row.setLicLineUseQuantity(Long.valueOf(0));

				if(row.getLicenseLineQtyId() == null) row.setLicenseLineQtyId(masterSession.nextVal("NEA_LICENSE_LINE_QTY_S")); // 明細ID新規

				licenseDAO.insertLicenseLineQty(row);
			}

			//////////////////////////////////// アップグレード明細
			// アップグレード元明細
			List<LicenseLineUpg> lineUpgSrcList = obj.getLicenseLineUpgSrc();
			if(lineUpgSrcList != null){
				for(int i = 0; i < lineUpgSrcList.size(); i++) {
					LicenseLineUpg row = lineUpgSrcList.get(i);
					boolean isNew = row.getCreateDate() == null ? true : false;

					// 更新日・更新者
					row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
					row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
					row.setUpdateDate(sysdate);
					row.setUpdateStaffCode(loginStaffCode);

					licenseDAO.insertLicenseLineUpg(row);

					// アップグレード元更新(バージョン)
					if(isNew) {
						if(ret == UPGRADE_ALLOC_OPERATION_TYPE_NO) ret = UPGRADE_ALLOC_OPERATION_TYPE_YES2;
						License srcObj = getLicense(row.getLicenseId(), true, false);
						try {
							updateLicense(loginStaffCode, accessLevel, srcObj, false, true, true, HIST_OPERATION_CREATE_ALLOC_UPGRADE, null);
						} catch (AppException e) {
							String errMessage = e.getMessage();
							errMessage = Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード元", row.getLicenseId() + "を割当時に以下のエラーが発生しました。") + errMessage;
							throw new AppException(errMessage);
						}
					}
				}
			}

			// アップグレード先明細
			List<LicenseLineUpg> lineUpgDstList = obj.getLicenseLineUpgDst();
			if(lineUpgDstList != null){
				for(int i = 0; i < lineUpgDstList.size(); i++) {
					LicenseLineUpg row = lineUpgDstList.get(i);
					boolean isNew = row.getCreateDate() == null ? true : false;

					// 更新日・更新者
					row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
					row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
					row.setUpdateDate(sysdate);
					row.setUpdateStaffCode(loginStaffCode);

					licenseDAO.insertLicenseLineUpg(row);

					// アップグレード先更新(有効数)
					if(isNew) {
						if(ret == UPGRADE_ALLOC_OPERATION_TYPE_NO) ret = UPGRADE_ALLOC_OPERATION_TYPE_YES1;
						License dstObj = getLicense(row.getUpgradeLicenseId(), true, false);
						try {
							updateLicense(loginStaffCode, accessLevel, dstObj, false, true, true, HIST_OPERATION_CREATE_ALLOC_UPGRADE_RELATION, null);
						} catch (AppException e) {
							String errMessage = e.getMessage();
							errMessage = Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード先", row.getUpgradeLicenseId() + "を割当時に以下のエラーが発生しました。") + errMessage;
							throw new AppException(errMessage);
						}
					}
				}
			}
		}
		return ret;
	}

	/**
	 * 明細削除
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel アクセスレベル
	 * @param licenseDAO データアクセスOBJ
	 * @param obj ライセンスデータ
	 * @return 0:アップグレード割当解除無し 1:アップグレード割当解除有り 2:アップグレード割当解除有り、有効数変更有
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @throws SQLException
	 * @throws AppException
	 */
	private int deleteLine(String loginStaffCode, String accessLevel, LicenseDAO licenseDAO, License obj, boolean isAp) throws SQLException, AppException {
		int ret = UPGRADE_ALLOC_OPERATION_TYPE_NO;

		// 登録申請
		if(isAp) {
			// 添付明細削除
			licenseDAO.deleteApLicenseLineAtt(obj.getLicenseId());
		}
		else {
			//////////////////////////////////// 数量管理明細
			licenseDAO.deleteLicenseLineQty(obj.getLicenseId());

			//////////////////////////////////// アップグレード明細
			// アップグレード明細バックアップ
			List<LicenseLineUpg> lineUpgSrcList = licenseDAO.selectLicenseLineUpgSrc(obj.getLicenseId());
			List<LicenseLineUpg> lineUpgDstList = licenseDAO.selectLicenseLineUpgDst(obj.getLicenseId());

			List<LicenseLineUpg> newSrcList = obj.getLicenseLineUpgSrc();
			List<LicenseLineUpg> newDstList = obj.getLicenseLineUpgDst();
			if(newSrcList == null) newSrcList = new ArrayList<LicenseLineUpg>();
			if(newDstList == null) newDstList = new ArrayList<LicenseLineUpg>();

			// アップグレード明細削除
			licenseDAO.deleteLicenseLineUpg(obj.getLicenseId());

			// アップグレード元ライセンスの更新
			if(lineUpgSrcList != null){
				for(int i = 0; i < lineUpgSrcList.size(); i++) {
					LicenseLineUpg row = lineUpgSrcList.get(i);
					boolean isDelete = true;
					for(int j = 0; j < newSrcList.size(); j++) {
						// insertする明細に同一ライセンスデータがあれば削除とみなさない
						isDelete = !Function.nvl(row.getLicenseId(), "").equals(Function.nvl(newSrcList.get(j).getLicenseId(), ""));
						if(!isDelete) break;
					}

					// アップグレード元更新(バージョン)
					if(isDelete) {
						if(ret == UPGRADE_ALLOC_OPERATION_TYPE_NO) ret = UPGRADE_ALLOC_OPERATION_TYPE_YES2;
						License srcObj = getLicense(row.getLicenseId(), true, false);
						try {
							updateLicense(loginStaffCode, accessLevel, srcObj, false, true, true, HIST_OPERATION_DELETE_ALLOC_UPGRADE, null);
						} catch (AppException e) {
							String errMessage = e.getMessage();
							errMessage = Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード元", row.getLicenseId() + "を割当解除時に以下のエラーが発生しました。") + errMessage;
							throw new AppException(errMessage);
						}
					}
				}
			}

			// アップグレード先ライセンスの更新
			if(lineUpgDstList != null){
				for(int i = 0; i < lineUpgDstList.size(); i++) {
					LicenseLineUpg row = lineUpgDstList.get(i);
					boolean isDelete = true;
					for(int j = 0; j < newDstList.size(); j++) {
						// insertする明細に同一ライセンスデータがあれば削除とみなさない
						isDelete = !Function.nvl(row.getUpgradeLicenseId(), "").equals(Function.nvl(newDstList.get(j).getUpgradeLicenseId(), ""));
						if(!isDelete) break;
					}

					// アップグレード先更新(有効数)
					if(isDelete) {
						if(ret == UPGRADE_ALLOC_OPERATION_TYPE_NO) ret = UPGRADE_ALLOC_OPERATION_TYPE_YES1;
						License dstObj = getLicense(row.getUpgradeLicenseId(), true, false);
						try {
							updateLicense(loginStaffCode, accessLevel, dstObj, false, true, true, HIST_OPERATION_DELETE_ALLOC_UPGRADE_RELATION, null);
						} catch (AppException e) {
							String errMessage = e.getMessage();
							errMessage = Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード先", row.getUpgradeLicenseId() + "を割当解除時に以下のエラーが発生しました。") + errMessage;
							throw new AppException(errMessage);
						}
					}
				}
			}
		}
		return ret;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.elicense.session.LicenseSession#applyApLicense(java.lang.String, java.lang.String, jp.co.ctcg.elicense.dto.license.License)
	 */
	public String applyApLicense(String loginStaffCode, String accessLevel, License obj) throws AppException {
		String ret = null;
		boolean isNew = Function.nvl(obj.getLicenseId(), "").equals(""); // 新規の場合true

		//////////////////////////////////// 新規 or 更新呼び出し
		if(isNew) { // 新規
			ret = createLicense(loginStaffCode, accessLevel, obj, true, false);
		} else { // 更新
			// 更新コメント
			obj.setUpdateComment(null);

			ret = obj.getLicenseId();

			License apLicense = getLicense(obj.getLicenseId(), true, true);

			// 不正セット項目値の調整
			try {
				setPropertyAdjust(obj, new LicenseDAO(), true);
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
			}

			// バリデーション
			StringBuffer errMsg = new StringBuffer();
			// バージョンチェック
			if(obj.getVersion().intValue() != apLicense.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, obj.getLicenseId(), Msg.ERR_MSG_VER));
			}
			// ステータス更新前バリデーション
			errMsg.append(validateLicense(loginStaffCode, accessLevel, obj, true, null));
			if(errMsg.length() > 0) throw new AppException(errMsg.toString());
		}

		//////////////////////////////////// ステータス更新&ステータス更新後バリデーション
		obj.setApStatus(Constants.AP_STATUS_ASSET_APPLY);
		String errMsg = validateLicense(loginStaffCode, accessLevel, obj, true, null);
		if(errMsg.length() > 0) throw new AppException(errMsg);

		//////////////////////////////////// 申請
		Long eappId = callEappService(obj); // e申請連携

		// e申請IDを更新
		obj.setEappId(eappId);

		if(isNew) { // 新規
			updateLicense(loginStaffCode, accessLevel, obj, true, false, false, null, null);
			histSession.createHistData(HIST_ENTITY_NAME_AP_PREFIX + HIST_ENTITY_NAME, ret, HIST_OPERATION_APPLY, null); // 履歴作成
		} else {
			updateLicense(loginStaffCode, accessLevel, obj, true, true, true, HIST_OPERATION_APPLY, null);
		}

		return ret;
	}

	/**
	 * e申請サービス呼び出し
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(License obj) throws AppException {
		// e申請WebServiceエンドポイント取得
		CodeName codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_LICENSE, null, null);
		String eappWsEndpoint = codeNameUrl.getValue1(); // e申請WebSerivceエンドポイント
		String eLicenseUrl = codeNameUrl.getValue2(); // e申請インラインフレーム画面表示用のeLicenseUrl
		String eappStopMessage = codeNameUrl.getValue3(); // e申請との連携停止期間中のエラーメッセージ

		Long eappId = null;

		if(!Function.nvl(eappWsEndpoint, "").equals("")) { // e申請WebServiceエンドポイントが空白(PG検証用)の場合は連携無し
			// e申請との連携停止期間中のエラーメッセージ
			if(!eappWsEndpoint.startsWith("http")){
				throw new AppException(eappStopMessage);
			}

			eLicenseUrl += "&amp;companyCode=" + obj.getApCompanyCode();
			eLicenseUrl += "&amp;param2="; // e申請から書類IDが指定される

			//////////////////////////////////// 経路設定
			// e申請経路担当情報取得
			CodeName codeNameEappCharge = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_LICENSE, null, obj.getApCompanyCode(), null);

			// e申請経路権限情報取得
			CodeName codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_LICENSE, null, obj.getApCompanyCode(), null);

			if(codeNameEappRoute != null) { // 経路有り

				// 経路パラメータセット

				//////////////////////////////////// e申請サービス呼び出し
				String eappIdStr = null;
				try {
					ApLicenseService service = new ApLicenseServiceProxy(eappWsEndpoint);
					eappIdStr = service.apply(
							obj.getLicenseId() // applicationId
							, Constants.AP_TYPE_LICENSE // applicationType
							, obj.getApCompanyCode() // companyCode
							, obj.getApSectionCode() // apSectionCode
							, obj.getApCreateStaffCode() // apCreateStaffCode
							, obj.getApStaffCode() // apStaffCode
							, obj.getApTel() // apTel
							, "\\n" + Constants.AP_TITLE_LICENSE // apTitle
							, null // apSubTitle
							, obj.getApCompanyName() + Constants.AP_TITLE_LICENSE // apListTitle
							, eLicenseUrl // eLicenseUrl
							, codeNameEappRoute.getValue3() // acceptRouteAuthDisp
							, codeNameEappCharge.getValue3() // acceptRouteChargeDisp
							, "1" // acceptRouteDispType
					);
				} catch (SocketException e){
					try{
						//	ログ出力
						Logging.warning(e.getMessage(), e);
						//	WLSのURLを取得する。
						codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_EAPP_ID_RESTORE, null, null);
						eappWsEndpoint = codeNameUrl.getValue1(); // e申請WebSerivceエンドポイント
						EAssetService eAssetService = new EAssetServiceProxy(eappWsEndpoint);
						//	e申請書類ID取得
						Long getEAppIdService = eAssetService.getEAppId(obj.getLicenseId());
						//	nullの場合エラー
						if(Function.nvl(getEAppIdService, "").equals("")){
							throw new AppException("e申請との連携処理中に予期せぬエラーが発生しました。\n画面下の「保存」ボタンを押してデータ保存した後\ne申請を開いて申請情報が作成されているか、ご確認下さい。\n\ne申請に申請情報が作成されていた場合、\nその申請の「書類ID」と「書類管理番号」をeAsset管理者宛へご連絡下さい。\n", e);
						}else{
							eappIdStr = String.valueOf(getEAppIdService);
						}
					} catch (Exception exception) {
						Logging.error(e.getMessage(), e);
						throw new AppException("e申請との連携処理中に予期せぬエラーが発生しました。\n画面下の「保存」ボタンを押してデータ保存した後\ne申請を開いて申請情報が作成されているか、ご確認下さい。\n\ne申請に申請情報が作成されていた場合、\nその申請の「書類ID」と「書類管理番号」をeAsset管理者宛へご連絡下さい。\n", exception);
					}
				} catch (Exception e) {
					Logging.error(e.getMessage(), e);
					throw new AppException("e申請連携処理中に予期せぬエラーが発生しました。\n作業状態を保存するには「保存」ボタンをクリックしてください。\n", e);
				}

				if(Function.nvl(eappIdStr, "").equals("")) { // レスポンス無しエラー
					throw new AppException("e申請連携処理中に予期せぬエラーが発生しました。\n作業状態を保存するには「保存」ボタンをクリックしてください。\n\n(e申請書類IDが空白です)。");
				} else if(eappIdStr.startsWith("e")) { // e申請側アプリケーションエラー
					throw new AppException("e申請連携処理中に以下のエラーが発生しました。\n作業状態を保存するには「保存」ボタンをクリックしてください。\n" + eappIdStr.substring(1));
				} else {
					eappId = Long.valueOf(eappIdStr);
				}
			}
		}

		return eappId;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.elicense.session.LicenseSession#approveApLicense(java.lang.Long, java.lang.String)
	 */
	public void approveApLicense(Long eappId, String execStaffCode) throws AppException {
		LicenseSC searchParam = new LicenseSC();
		searchParam.setEappId(eappId);

		// 対象登録申請取得
		List<LicenseSR> list = searchLicense(execStaffCode, null, searchParam, true);

		for(int i = 0; i < list.size(); i++) {
			License license = list.get(i);

			if(Constants.AP_STATUS_LICENSE_APPLY.equals(license.getApStatus())) { // 申請中であれば差戻しを行う
				// ステータス
				license.setApStatus(Constants.AP_STATUS_LICENSE_SENDBACK);
				// 更新コメント
				license.setUpdateComment(null);

				updateLicense(execStaffCode, null, license, true, false, true, hIST_OPERATION_APPROVE, null);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.elicense.session.LicenseSession#cancelApplyApLicense(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyApLicense(Long eappId, String execStaffCode) throws AppException {
		LicenseSC searchParam = new LicenseSC();
		searchParam.setEappId(Function.nvl(eappId, Long.valueOf(-1)));

		// 対象登録申請取得
		List<LicenseSR> list = searchLicense(execStaffCode, null, searchParam, true);

		for(int i = 0; i < list.size(); i++) {
			License license = list.get(i);

			if(Constants.AP_STATUS_LICENSE_APPLY.equals(license.getApStatus())) { // 申請中であれば引戻しを行う
				// ステータス
				license.setApStatus(Constants.AP_STATUS_LICENSE_NOAPPLY);
				// 更新コメント
				license.setUpdateComment(null);
				license.setEappId(null); // 書類IDクリア

				updateLicense(execStaffCode, null, license, true, false, true, HIST_OPERATION_CANCEL_APPLY, null);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.elicense.session.LicenseSession#rejectApLicense(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectApLicense(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		LicenseSC searchParam = new LicenseSC();
		searchParam.setEappId(Function.nvl(eappId, Long.valueOf(-1)));

		// 対象登録申請取得
		List<LicenseSR> list = searchLicense(execStaffCode, null, searchParam, true);

		for(int i = 0; i < list.size(); i++) {
			License license = list.get(i);

			if(Constants.AP_STATUS_LICENSE_APPLY.equals(license.getApStatus())) { // 申請中であれば引戻しを行う
				// ステータス
				license.setApStatus(Constants.AP_STATUS_LICENSE_SENDBACK);
				// 更新コメント
				license.setUpdateComment(rejectReason);
				license.setEappId(null); // 書類IDクリア

				updateLicense(execStaffCode, null, license, true, false, true, HIST_OPERATION_SENDBACK, null);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.elicense.session.LicenseSession#searchApLicenseEapp(java.lang.Long, java.lang.String)
	 */
	public List<LicenseSR> searchApLicenseEapp(Long eappId, String execStaffCode) {

		LicenseSC searchParam = new LicenseSC();
		searchParam.setEappId(eappId);
		searchParam.setSearchEapp("1");
		// 対象登録申請取得
		return (List<LicenseSR>)searchLicense(execStaffCode, null, searchParam, true);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#getApLicenseByApGetTan(java.lang.Long)
	 */
	public License getApLicenseByApGetTan(Long apGetTanLineLicId) {
		try{
			LicenseDAO licenseDAO = new LicenseDAO();

			License obj = licenseDAO.selectApLicenseByApGetTan(apGetTanLineLicId);

			// メーカーが既に登録済みの場合はIDセット
			if(obj.getSoftwareMakerId() == null) {
				obj.setSoftwareMakerId(getSoftwareMakerIdByName(obj.getSoftwareMakerName()));
			}
			// ソフトウェアが既に登録済みの場合はIDセット
			if(obj.getSoftwareMakerId() != null && obj.getSoftwareId() == null) {
				obj.setSoftwareId(getSoftwareIdByName(obj.getSoftwareMakerId(), obj.getSoftwareName()));
			}

			return obj;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請明細データの取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createApLicenseCsvByApGetTan(java.lang.String, java.lang.String, boolean, java.util.List)
	 */
	public String createApLicenseCsvByApGetTan(String loginStaffCode, String accessLevel, boolean isTermUpdate, List<ApGetTan> apGetTanList) {
		CsvWriterRowHandler handler = null;
		try {
			//////////////////////////////////// ファイル作成
			StringBuffer headerRow = new StringBuffer();
			List<String> propList = new ArrayList<String>();
			List<Format> propFormatList = new ArrayList<Format>();

			new MasterDAO().setCsvDefForUpload(Constants.CATEGORY_CODE_ITEM_DEF_AP_LICENSE, accessLevel, headerRow, propList, propFormatList);

			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) propList.toArray(new String[0]), (Format[]) propFormatList.toArray(new Format[0]));

			if(apGetTanList != null) {
				String termContractTypeUpdateName = null;
				if(isTermUpdate) { // ターム更新の場合契約区分セット名を取得
					termContractTypeUpdateName = masterSession.getCodeName(Constants.CATEGORY_CODE_LICENSE_TERM_CONTRACT_TYPE, Constants.LICENSE_TERM_CONTRACT_TYPE_UPDATE, null, null).getValue1();
				}

				StringBuffer applicationIdStr = new StringBuffer(); // 操作ログ用
				for(int i = 0; i < apGetTanList.size(); i++) {
					// 取得申請ライセンス明細取得
					ApGetTan apGetTan = apGetTanSession.getApGetTan(apGetTanList.get(i).getApplicationId());

					// 操作ログ用
					if(applicationIdStr.length() > 0) applicationIdStr.append(" ");
					applicationIdStr.append(apGetTan.getApplicationId());

					List<ApGetTanLineLic> apGetTanLineLicList = apGetTan.getApGetTanLineLicList();

					for(int j = 0; j < apGetTanLineLicList.size(); j++) {
						ApGetTanLineLic apGetTanLineLic = apGetTanLineLicList.get(j);
						// 登録残数取得
						int registQty = Function.nvl(apGetTanLineLic.getQuantity(), Integer.valueOf(0)).intValue() - Function.nvl(apGetTanLineLic.getRegistQuantity(), Integer.valueOf(0)).intValue();

						if(registQty > 0) {
							License apLicense = getApLicenseByApGetTan(apGetTanLineLic.getApGetTanLineLicId());

							if(isTermUpdate) { // ターム更新の場合契約区分を更新にセット
								apLicense.setTrmContractType(Constants.LICENSE_TERM_CONTRACT_TYPE_UPDATE);
								apLicense.setTrmContractTypeName(termContractTypeUpdateName);
							}

							List<License> apLicenseList = new ArrayList<License>();

							int loopCt = registQty;

							for(int k = 0; k < loopCt; k++) { // 登録残数量分
								apLicenseList.add(apLicense);
							}

							for(int k = 0; k < apLicenseList.size(); k++) {
								handler.handleRow(apLicenseList.get(k)); // 行出力
							}
						}
					}
				}

				//////////////////////////////////// 操作ログ作成
				histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_LICENSE_BULK_CREATE, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",applicationId:" + applicationIdStr);
			}

			return handler.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "登録申請作成用CSVファイル作成"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "登録申請作成用CSVファイル作成"), e);
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createLicensePossibleInputMasterCsv(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public String createLicensePossibleInputMasterCsv(String loginStaffCode, String accessLevel, String companyCode, List<String> propertyList) {
		try {
			LicenseDAO licenseDAO = new LicenseDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = licenseDAO.createLicensePossibleInputMasterList(companyCode, accessLevel, propertyList);

			//////////////////////////////////// 操作ログ作成
			StringBuffer propStr = new StringBuffer();
			if(propertyList != null) {
				for(int i = 0; i < propertyList.size(); i++) {
					if(propStr.length() > 0) propStr.append(" ");
					propStr.append(propertyList.get(i));
				}
			}
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_LICENSE_MASTER, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",outputProperty:" + propStr.toString());

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createLicenseCompanyMoveInputMasterCsv(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String createLicenseCompanyMoveInputMasterCsv(String loginStaffCode, String accessLevel, String companyCode) {
		try {
			LicenseDAO licenseDAO = new LicenseDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = licenseDAO.createLicenseCompanyMoveInputMasterList(companyCode, accessLevel);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_LICENSE_MASTER, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel);

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.elicense.session.LicenseSession#createApLicenseBulk(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.elicense.dto.license.License)
	 */
	public List<String> createApLicenseBulk(String loginStaffCode, String accessLevel, String fileId, License obj) throws AppException {
		//////////////////////////////////// CSV読込
		List<License> createApLicenseList = new ArrayList<License>();
		int headerRowCt = setLicenseListByCsv(accessLevel, null, fileId, true, obj, createApLicenseList, null, null);

		if(createApLicenseList.size() == 0) throw new AppException("登録対象データが入力されていません。");

		//////////////////////////////////// 登録
		List<String> licenseIdList = new ArrayList<String>();

		StringBuffer errorMessage = new StringBuffer();
		int rowCt = headerRowCt;
		for(int i = 0; i < createApLicenseList.size(); i++) {
			rowCt++;
			String rowNumStr = "[" + rowCt + "行目] ";

			try {
				String licenseId = createLicense(loginStaffCode, accessLevel, createApLicenseList.get(i), true);
				licenseIdList.add(licenseId);
			} catch(AppException e) {
				errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "以下のエラーがあります。"));
				errorMessage.append(e.getMessage());
			}
		}

		if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

		return licenseIdList;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#callUpdateLicenseBulk(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void callUpdateLicenseBulk(String companyCode, String loginStaffCode, String accessLevel, String fileId, List<CodeName> updatePropertyList) throws AppException {
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

		param.put("functionName", BulkUpdateMDBean.FUNCTION_LICENSE);
		param.put("loginStaffCode", loginStaffCode);
		param.put("companyCode", companyCode);
		param.put("accessLevel", accessLevel);
		param.put("fileId", fileId);
		param.put("execFile", execFile);
		param.put("updatePropertyList", updatePropertyList);
		param.put("logId", logId);

		Function.sendJmsMessage(bulkUpdateQueueFactory, bulkUpdateQueue, param);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#updateLicenseBulk(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.File, java.util.List, java.lang.Long)
	 */
	public void updateLicenseBulk(String loginStaffCode, String accessLevel, String companyCode, String fileId, File execFile, List<CodeName> updatePropertyList, Long logId) {
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
			List<License> updateLicenseList = new ArrayList<License>();
			int headerRowCt = setLicenseListByCsv(accessLevel, companyCode, fileId, false, null, updateLicenseList, updatePropertyList, logId);

			//////////////////////////////////// 一括更新実行ログの取得
			BulkUpdateHist logHist = histSession.getBulkUpdateHist(logId);

			if(!Function.nvl(logHist.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) { // ファイル読み込み中に中断されていない
				if(updateLicenseList.size() == 0) throw new AppException("更新対象データが入力されていません。");

				//////////////////////////////////// ファイル読込設定
				execFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(execFile), CsvReaderRowHandler.getCharsetName()));
				StringBuffer headerStr = new StringBuffer();
				for(int i = 0; i < headerRowCt; i++) {
					headerStr.append(execFileReader.readLine() + "\n");
				}

				int skipCount = 0; // エラー再実行時に重複実行しないため、処理スキップする件数

				if(logHist.getSuccessCount() == null && logHist.getFailureCount() == null) { // 初回実行
					//////////////////////////////////// 一括更新実行ログの更新
					execCount = Integer.valueOf(updateLicenseList.size());
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
				int rowCt = headerRowCt;

				try {
					// 読み込み対象プロパティのgetter/setter取得
					List<Method> readMethodList = new ArrayList<Method>();
					List<Method> writeMethodList = new ArrayList<Method>();
					HashSet<String> updatePropSet = new HashSet<String>();
					License a = new License(); /// リフレクション用のダミー
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
					for(int i = 0; i < updateLicenseList.size(); i++) {
						//////////////////////////////////// 中断リクエストされていないかどうか確認
						BulkUpdateHist log = histSession.getBulkUpdateHist(logId);
						if(Function.nvl(log.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) break; // 中断

						//////////////////////////////////// 1件処理
						rowCt++;
						String execFileRowStr = execFileReader.readLine(); // 対象ファイル一行読み込み（成功・失敗ファイル保存用）

						if(i < skipCount) {
							continue; // エラー前に実行済みのためスキップ
						}

						License updateObj = updateLicenseList.get(i); // 更新内容取得
						if(Function.nvl(updateObj.getLicenseId(), "").equals("")) {
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
							childLicenseSession.updateLicenseBulkRow(loginStaffCode, accessLevel, rowCt, updateObj, updatePropSet, apChangeTypeMap, readMethods, writeMethods);

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

							String rowNumStr = "[" + rowCt + "行目(失敗データファイル " + (headerRowCt + failureCount.intValue()) + "行目):" + updateObj.getLicenseId() + "] "; // エラー表示用行識別にライセンス管理番号付加

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
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
				} catch (IllegalAccessException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
				} catch (InvocationTargetException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
				} catch (NoSuchMethodException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
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
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
				}
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#updateLicenseBulkRow(java.lang.String, java.lang.String, int, jp.co.ctcg.easset.dto.license.License, java.util.Set, java.util.Map, java.lang.reflect.Method[], java.lang.reflect.Method[])
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // 新規トランザクション
	public void updateLicenseBulkRow(String loginStaffCode, String accessLevel, int rowCt, License updateObj, Set<String> updatePropSet, Map<String, String> apChangeTypeMap, Method[] readMethods, Method[] writeMethods) throws AppException {
		License obj = getLicense(updateObj.getLicenseId(), true, false); // 更新対象取得

		//////////////////////////////////// 一括更新用バリデーション
		StringBuffer errMsg = new StringBuffer();
		// 抹消済
		if(Function.nvl(obj.getDeleteFlag(), "").equals(Constants.FLAG_YES)) {
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "抹消済のライセンスは更新できません。"));
		}
		// 消費数
		if(updatePropSet.contains("licUseQuantity")) {
			if(!Function.nvl(obj.getLicLicenseType(), "").equals("")) {
				CodeName licenseType = masterSession.getCodeName(Constants.CATEGORY_CODE_LICENSE_TYPE, obj.getLicLicenseType(), null, null);
				if(licenseType != null) {
					if(Function.nvl(licenseType.getValue2(), "").equals(Constants.FLAG_YES)) { // 割当可
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "ライセンス形態が「" + obj.getLicLicenseTypeName() + "」のライセンスに対して消費数は更新できません。"));
					}
				}
			}
		}

		// 移動申請項目
		if(updatePropSet.contains("holSectionCode")
			|| updatePropSet.contains("holStaffCode")) {
			if(!Function.isAccessLevelAdmin(accessLevel)) { // 一般・部署権限
				// 移動申請使用区分:使用
				if(Function.nvl(apChangeTypeMap.get(obj.getHolCompanyCode()), "").equals(Constants.AP_CHANGE_USE_TYPE_ALL)) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "保有部署、資産管理担当者を変更するには、移動申請を行う必要があります。"));
				}
			}
		}

		if(errMsg.length() > 0) throw new AppException(errMsg.toString());

		//////////////////////////////////// 更新
		// 更新項目のコピー
		try {
			for(int j = 0; j < writeMethods.length; j++) {
				writeMethods[j].invoke(obj, readMethods[j].invoke(updateObj, (Object[]) null));
			}
		} catch (IllegalArgumentException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
		} catch (IllegalAccessException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
		} catch (InvocationTargetException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス更新"), e);
		}

		// 保有会社変更時、未割当の貸出明細をクリア
		if(updatePropSet.contains("holCompanyCode")) {
			List<LicenseLineQty> newQtyLineRental = new ArrayList<LicenseLineQty>();
			List<LicenseLineQty> qtyLineRental = obj.getLicenseLineQtyRental();
			if(qtyLineRental != null && qtyLineRental.size() != 0) {
				for(int i = 1; i < qtyLineRental.size(); i++) {
					LicenseLineQty qtyObj = qtyLineRental.get(i);
					if(qtyObj.getLicLineUseQuantity() > 0) {
						newQtyLineRental.add(qtyObj);
					}
				}
				// 貸出明細が0件の場合、他部署への貸出を「 0：行わない」に更新
				if(newQtyLineRental.size() == 0) {
					obj.setUseRentalFlag("0");
				}

				obj.setLicenseLineQtyRental(newQtyLineRental);
			}
		}

		updateLicense(loginStaffCode, accessLevel, obj, false, updatePropSet);
	}

	/**
	 * CSVファイルからライセンス情報セット
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param fileId CSVファイル参照
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @param apLicenseObj 登録申請画面入力項目値
	 * @param licenseList ライセンス情報をセットするリスト
	 * @param updatePropertyList 更新項目一覧(nullは全て)
	 * @param logId 一括更新ログID(nullは一括更新以外)
	 * @return ヘッダ行数
	 * @throws AppException
	 */
	private int setLicenseListByCsv(String accessLevel, String companyCode, String fileId, boolean isAp, License apLicenseObj, List<License> licenseList, List<CodeName> updatePropertyList, Long logId) throws AppException {
		CsvReaderRowHandler handler = null;

		try {
			LicenseDAO licenseDAO = new LicenseDAO();

			if(isAp) {
				handler = new CsvReaderRowHandler(accessLevel, fileId, LicenseSR.class, Constants.CATEGORY_CODE_ITEM_DEF_AP_LICENSE);
			} else {
				handler = new CsvReaderRowHandler(accessLevel, fileId, LicenseSR.class, Constants.CATEGORY_CODE_ITEM_DEF_LICENSE, updatePropertyList);
			}

			LicenseSR row = null;
			StringBuffer errorMessage = new StringBuffer(); // 全行エラーメッセージ
			int headerCt = handler.getHeaderRowCount();
			int rowCt = headerCt; // ファイル行カウンタ

			String flagYesName = masterSession.getCodeName(Constants.CATEGORY_CODE_FLAG_YN, Constants.FLAG_YES, null, null).getValue1(); // フラグ名
			String flagYesNameAri = masterSession.getCodeName(Constants.CATEGORY_CODE_FLAG_AN, Constants.FLAG_YES, null, null).getValue1(); // フラグ名
			int currentYear = Integer.valueOf(masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null).getValue1()); // カレント年度
			boolean isUpdateHolCompany = false; // 保有会社更新有
			boolean isUpdateHolSection = false; // 保有部署更新有
			boolean isUpdateUseSection = false; // 使用部署更新有

			// 部署更新指定判別用
			if(updatePropertyList != null) {
				for(int i = 0; i < updatePropertyList.size(); i++) {
					String prop = Function.nvl(updatePropertyList.get(i).getValue3(), "");
					if("holCompanyName".equals(prop)) isUpdateHolCompany = true;
					if("holSectionCode".equals(prop)) isUpdateHolSection = true;
					if("useSectionCode".equals(prop)) isUpdateUseSection = true;
				}
			} else {
				isUpdateHolSection = true;
				isUpdateUseSection = true;
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

				LicenseSR oldObj = null;

				// 行データ取得
				if(isAp) { // 登録申請
					row = (LicenseSR) handler.handleRow();
					if(row == null) break; // 行データが取得できない場合は終了

				} else {
					// 更新対象データ取得
					String licenseId = handler.handleId();
					if(Function.nvl(licenseId, "").equals("")) break; // 行データが取得できない場合は終了

					LicenseSC param = new LicenseSC();
					param.setLicenseIdPlural(licenseId);
					List<LicenseSR> targetList = searchLicense(Constants.STAFF_CODE_SYSTEM, accessLevel, param, false);
					if(targetList.size() == 0) {
						errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "ライセンス管理番号", licenseId));
						continue; // ライセンス管理番号が不正な場合はスキップ
					}

					oldObj = searchLicense(Constants.STAFF_CODE_SYSTEM, accessLevel, param, false).get(0); // 変更前オブジェクト保持用

					row = (LicenseSR) handler.handleRow(targetList.get(0));
					if(row == null) break; // 行データが取得できない場合は終了

					rowNumStr = "[" + rowCt + "行目:" + row.getLicenseId() + "] "; // エラー表示用行識別にライセンス管理番号付加
				}

				if(isAp) { // 登録申請
					// 取得申請紐付セット
					if(!Function.nvl(row.getGetApplicationId(), "").equals("") && row.getApGetTanLineLicLineSeq() != null) {
						Long apGetTanLineLicId = licenseDAO.selectApGetTanLineLicId(row.getGetApplicationId(), row.getApGetTanLineLicLineSeq());
						row.setApGetTanLineLicId(apGetTanLineLicId);
					}

					// 申請情報セット
					row.setApStatus(apLicenseObj.getApStatus());
					row.setApDate(apLicenseObj.getApDate());

					row.setApCreateStaffCode(apLicenseObj.getApCreateStaffCode());
					row.setApCreateCompanyCode(apLicenseObj.getApCreateCompanyCode());
					row.setApCreateSectionCode(apLicenseObj.getApCreateSectionCode());
					row.setApCreateSectionYear(apLicenseObj.getApCreateSectionYear());

					row.setApStaffCode(apLicenseObj.getApStaffCode());
					row.setApCompanyCode(apLicenseObj.getApCompanyCode());
					row.setApSectionCode(apLicenseObj.getApSectionCode());
					row.setApSectionYear(apLicenseObj.getApSectionYear());

					row.setApTel(apLicenseObj.getApTel());
					row.setApOfficeName(apLicenseObj.getApOfficeName());
				} else { // ライセンス
					// 申請者
					if(!Function.nvl(oldObj.getApStaffCode(), "").equals(Function.nvl(row.getApStaffCode(), ""))) {
						row.setApStaffName(null);
						row.setApCompanyCode(null);
						row.setApCompanyName(null);
						row.setApSectionCode(null);
						row.setApSectionName(null);
						row.setApSectionYear(null);
						if(!Function.nvl(row.getApStaffCode(), "").equals("")) {
							User staff = masterSession.getStaff(null, row.getApStaffCode());
							if(staff != null) {
								row.setApStaffName(staff.getName());
								row.setApCompanyCode(staff.getCompanyCode());
								row.setApCompanyName(staff.getCompanyName());
								row.setApSectionCode(staff.getSectionCode());
								row.setApSectionName(staff.getSectionName());
								row.setApSectionYear(currentYear);
							}
						}
					}
				}

				// ソフトウェアメーカーマスタ値セット
				if(oldObj == null
					|| !Function.nvl(oldObj.getSoftwareMakerName(), "").equals(Function.nvl(row.getSoftwareMakerName(), ""))
					|| !Function.nvl(oldObj.getSoftwareName(), "").equals(Function.nvl(row.getSoftwareName(), ""))
					) { // 登録申請 or 一括更新で項目変更有り
					row.setSoftwareMakerId(null);
					row.setSoftwareId(null);
					if(!Function.nvl(row.getSoftwareMakerName(), "").equals("")) {
						Long softwareMakerId = getSoftwareMakerIdByName(row.getSoftwareMakerName());
						if(softwareMakerId == null) {  // 対応するマスタが見つからない場合は手入力扱い(ソフトウェアも)

						} else {
							row.setSoftwareMakerId(softwareMakerId);

							// ソフトウェアマスタ値セット
							if(!Function.nvl(row.getSoftwareName(), "").equals("")) {
								Long softwareId = getSoftwareIdByName(softwareMakerId, row.getSoftwareName());
								if(softwareId == null) {  // 対応するマスタが見つからない場合は手入力扱い

								} else {
									row.setSoftwareId(softwareId);
								}
							}
						}
					}
				}

				if(!isAp) { // 登録申請以外で対応マスタが見つからない場合はエラー
					if(oldObj == null || !Function.nvl(oldObj.getSoftwareMakerName(), "").equals(Function.nvl(row.getSoftwareMakerName(), ""))) { // 登録申請 or 一括更新で項目変更有り
						if(row.getSoftwareMakerId() == null) {
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "ソフトウェア-メーカー", row.getSoftwareMakerName()));
						}
					}
					if(oldObj == null || !Function.nvl(oldObj.getSoftwareName(), "").equals(Function.nvl(row.getSoftwareName(), ""))) { // 登録申請 or 一括更新で項目変更有り
						if(row.getSoftwareId() == null) {
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "ソフトウェア-ソフトウェア", row.getSoftwareName()));
						}
					}
				}

				// 資産区分マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getLicAssetTypeName(), "").equals(Function.nvl(row.getLicAssetTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setLicAssetType(null);
					if(!Function.nvl(row.getLicAssetTypeName(), "").equals("")) {
						String licAssetType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LICENSE_ASSET_TYPE, null, row.getLicAssetTypeName());
						if(licAssetType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "ライセンス・資産-取得/資産-資産区分", row.getLicAssetTypeName()));
						} else {
							row.setLicAssetType(licAssetType);
						}
					}
				}

				// 取得方法マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getLicGetTypeName(), "").equals(Function.nvl(row.getLicGetTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setLicGetType(null);
					if(!Function.nvl(row.getLicGetTypeName(), "").equals("")) {
						String licGetType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LICENSE_GET_TYPE, null, row.getLicGetTypeName());
						if(licGetType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "ライセンス・資産-取得/資産-取得方法", row.getLicGetTypeName()));
						} else {
							row.setLicGetType(licGetType);
						}
					}
				}

				// ハードウェアバンドル
				if(Function.nvl(row.getLicHrdBandleFlagName(), "").toUpperCase().equals(flagYesName.toUpperCase())) {
					row.setLicHrdBandleFlag(Constants.FLAG_YES);
				} else {
					row.setLicHrdBandleFlag(Constants.FLAG_NO);
				}

				// メディア形態マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getLicMediaTypeName(), "").equals(Function.nvl(row.getLicMediaTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setLicMediaType(null);
					if(!Function.nvl(row.getLicMediaTypeName(), "").equals("")) {
						String licMediaType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_SOFTWARE_MEDIA_TYPE, null, row.getLicMediaTypeName());
						if(licMediaType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "ライセンス・資産-取得/資産-メディア形態", row.getLicMediaTypeName()));
						} else {
							row.setLicMediaType(licMediaType);
						}
					}
				}

				// ライセンス形態マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getLicLicenseTypeName(), "").equals(Function.nvl(row.getLicLicenseTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setLicLicenseType(null);
					if(!Function.nvl(row.getLicLicenseTypeName(), "").equals("")) {
						String licLicenseType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LICENSE_TYPE, null, row.getLicLicenseTypeName());
						if(licLicenseType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "ライセンス・資産-ライセンス形態/ライセンス数-ライセンス形態", row.getLicLicenseTypeName()));
						} else {
							row.setLicLicenseType(licLicenseType);
						}
					}
				}

				// アップグレードライセンス
				if(Function.nvl(row.getLicUpgradeFlagName(), "").toUpperCase().equals(flagYesName.toUpperCase())) {
					row.setLicUpgradeFlag(Constants.FLAG_YES);
				} else {
					row.setLicUpgradeFlag(Constants.FLAG_NO);
				}

				// ボリュームタイプマスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getLicVolumeTypeName(), "").equals(Function.nvl(row.getLicVolumeTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setLicVolumeType(null);
					if(!Function.nvl(row.getLicVolumeTypeName(), "").equals("")) {
						String licVolumeType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LICENSE_VOLUME_TYPE, null, row.getLicVolumeTypeName());
						if(licVolumeType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "ライセンス・資産-ライセンス形態/ライセンス数-ボリュームタイプ", row.getLicVolumeTypeName()));
						} else {
							row.setLicVolumeType(licVolumeType);
						}
					}
				}

				// 使用許諾書有無
				if(Function.nvl(row.getLicUseConsentFlagName(), "").toUpperCase().equals(flagYesNameAri.toUpperCase())) {
					row.setLicUseConsentFlag(Constants.FLAG_YES);
				} else {
					row.setLicUseConsentFlag(Constants.FLAG_NO);
				}

				// ダウングレード許諾有無
				if(Function.nvl(row.getLicDowngradeConsentFlagName(), "").toUpperCase().equals(flagYesNameAri.toUpperCase())) {
					row.setLicDowngradeConsentFlag(Constants.FLAG_YES);
				} else {
					row.setLicDowngradeConsentFlag(Constants.FLAG_NO);
				}

				// ライセンス数制限マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getLicQuantityTypeName(), "").equals(Function.nvl(row.getLicQuantityTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setLicQuantityType(null);
					if(!Function.nvl(row.getLicQuantityTypeName(), "").equals("")) {
						String licQuantityType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LICENSE_QUANTITY_TYPE, null, row.getLicQuantityTypeName());
						if(licQuantityType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "ライセンス・資産-ライセンス形態/ライセンス数-ライセンス数制限", row.getLicQuantityTypeName()));
						} else {
							row.setLicQuantityType(licQuantityType);
						}
					}
				}

				// タームライセンス契約区分
				row.setTrmContractType(null);
				if(!Function.nvl(row.getTrmContractTypeName(), "").equals("")) {
					String trmContractType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LICENSE_TERM_CONTRACT_TYPE, null, row.getTrmContractTypeName());
					if(trmContractType == null) { // 対応するマスタが見つからない場合設定無し
					} else {
						row.setTrmContractType(trmContractType);
					}
				}

				// タームライセンス自動割当切替
				row.setTrmAutoAllocFlag(null);
				if(!Function.nvl(row.getTrmAutoAllocFlagName(), "").equals("")) {
					if(Function.nvl(row.getTrmAutoAllocFlagName(), "").toUpperCase().equals(flagYesNameAri.toUpperCase())) {
						row.setTrmAutoAllocFlag(Constants.FLAG_YES);
					} else {
						row.setTrmAutoAllocFlag(Constants.FLAG_NO);
					}
				}

				// タームライセンスフラグ
				if(!Function.nvl(row.getTrmContractType(), "").equals("")
					|| !Function.nvl(row.getTrmParentLicenseId(), "").equals("")
					|| !Function.nvl(row.getTrmStartDate(), "").equals("")
					|| !Function.nvl(row.getTrmEndDate(), "").equals("")
					|| !Function.nvl(row.getTrmAlertDate(), "").equals("")
					|| !Function.nvl(row.getTrmAutoAllocFlag(), "").equals("")
					) {
					row.setLicTermFlag(Constants.FLAG_YES); // タームライセンス項目入力があればYES

					// 更新元タームライセンスの入力がある場合
					if(!Function.nvl(row.getTrmParentLicenseId(), "").equals("")) {
						if(Function.nvl(row.getTrmContractType(), "").equals("")) {
							// 契約区分の入力がない場合は更新にセット
							row.setTrmContractType(Constants.LICENSE_TERM_CONTRACT_TYPE_UPDATE);
						} else if(Constants.LICENSE_TERM_CONTRACT_TYPE_NEW.equals(Function.nvl(row.getTrmContractType(), ""))) {
							// 契約区分に新規が入力されている場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr + "資産-タームライセンス-ターム契約区分", "ターム更新元ライセンス管理番号が入力されている場合「更新」しか指定できません。"));
						}
					}

				} else {
					row.setLicTermFlag(Constants.FLAG_NO);
				}

				// 保有会社
				if(oldObj == null || !Function.nvl(oldObj.getHolCompanyName(), "").equals(Function.nvl(row.getHolCompanyName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setHolCompanyCode(null);
					if(!Function.nvl(row.getHolCompanyName(), "").equals("")) {
						String holCompanyCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_USE_COMPANY, null, row.getHolCompanyName());
						if(holCompanyCode == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "保有・使用許諾-保有会社", row.getHolCompanyName()));
						} else {
							row.setHolCompanyCode(holCompanyCode);
						}
					}
				}

				// 保有部署年度
				if(isAp) { // 登録申請
					row.setHolSectionYear(row.getApSectionYear());
				} else {
					if(isUpdateHolSection) row.setHolSectionYear(currentYear);
				}

				// 保有部署コード0抜け対応
				if(!Function.nvl(row.getHolSectionCode(), "").equals("") && Function.nvl(row.getHolSectionCode(), "").length() < Constants.SECTION_CODE_LENGTH) {
					row.setHolSectionCode(StringUtils.leftPad(row.getHolSectionCode(), Constants.SECTION_CODE_LENGTH, "0"));
				}

				// 保有部署 <-> 資産管理担当者の整合性
				if(oldObj == null
					|| !Function.nvl(oldObj.getHolSectionCode(), "").equals(Function.nvl(row.getHolSectionCode(), ""))
					|| Function.nvl(oldObj.getHolSectionYear(), Integer.valueOf(0)).intValue() != Function.nvl(row.getHolSectionYear(), Integer.valueOf(0)).intValue()
					|| !Function.nvl(oldObj.getHolStaffCode(), "").equals(Function.nvl(row.getHolStaffCode(), ""))
					|| !Function.nvl(oldObj.getHolCompanyCode(), "").equals(Function.nvl(row.getHolCompanyCode(), ""))
					) { // 登録申請 or 一括更新で項目変更有り
					if(!Function.nvl(row.getHolSectionCode(), "").equals("")
						&& !Function.nvl(row.getHolStaffCode(), "").equals("") ) {
						HashMap<String, Object> param = new HashMap<String, Object>();

						param.put("companyCode", Function.nvl(row.getHolCompanyCode(), ""));
						param.put("sectionCode", row.getHolSectionCode());

						LovDataEx data = masterSession.getLovData("selectHolStaff_LOV", param, row.getHolStaffCode());
						if(data == null) {
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "保有・使用許諾-資産管理担当者", "指定された資産管理担当者は、該当保有部署の担当ではありません。"));
						}
					}
				}

				// 使用許諾マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getUseTypeName(), "").equals(Function.nvl(row.getUseTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setUseType(null);
					if(!Function.nvl(row.getUseTypeName(), "").equals("")) {
						String useType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LICENSE_USE_TYPE, null, row.getUseTypeName());
						if(useType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "保有・使用許諾-使用許諾", row.getUseTypeName()));
						} else {
							row.setUseType(useType);
						}
					}
				}

				// 保有会社変更時、使用許諾が部署限定・会社限定で使用許諾会社と一致しない場合はエラーとする
				if(!Function.nvl(row.getUseType(), "").equals(Constants.LICENSE_USE_TYPE_ALL_COMPANY)
				 && isUpdateHolCompany
				 && !Function.nvl(row.getHolCompanyName(), "").equals(Function.nvl(row.getUseCompanyName(), ""))) {
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "保有会社(" + Function.nvl(row.getHolCompanyName(), "") + ")と使用許諾会社(" + Function.nvl(row.getUseCompanyName(), "") + ")が一致しません"));
				}

				// 保有会社変更時、変更前の保有会社がログイン会社と一致しない場合はエラーとする
				if(isUpdateHolCompany && !Function.nvl(oldObj.getHolCompanyCode(), "").equals(Function.nvl(companyCode, ""))) {
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "他事業会社のライセンスは変更できません"));
				}

				// 使用許諾会社
				if(oldObj == null || !Function.nvl(oldObj.getUseCompanyName(), "").equals(Function.nvl(row.getUseCompanyName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setUseCompanyCode(null);
					if(!Function.nvl(row.getUseCompanyName(), "").equals("")) {
						String useCompanyCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_USE_COMPANY, null, row.getUseCompanyName());
						if(useCompanyCode == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "保有・使用許諾-使用許諾会社", row.getUseCompanyName()));
						} else {
							row.setUseCompanyCode(useCompanyCode);
						}
					}
				}

				// 使用許諾が部署限定・会社限定で使用許諾会社が設定されていない場合は保有会社セット
				if(!Function.nvl(row.getUseType(), "").equals(Constants.LICENSE_USE_TYPE_ALL_COMPANY)
					&& Function.nvl(row.getUseCompanyCode(), "").equals("")) {
					row.setUseCompanyCode(row.getHolCompanyCode());
				}

				// 使用許諾部署年度
				if(isAp) {
					row.setUseSectionYear(row.getApSectionYear());
				} else {
					if(isUpdateUseSection) row.setUseSectionYear(currentYear);
				}

				// 使用許諾部署コード0抜け対応
				if(!Function.nvl(row.getUseSectionCode(), "").equals("") && Function.nvl(row.getUseSectionCode(), "").length() < Constants.SECTION_CODE_LENGTH) {
					row.setUseSectionCode(StringUtils.leftPad(row.getUseSectionCode(), Constants.SECTION_CODE_LENGTH, "0"));
				}

				//////////////////////////////////// 取得申請との整合性チェック
				if(isAp) { // 登録申請
	 				if(row.getApGetTanLineLicId() != null) {
						License license = getApLicenseByApGetTan(row.getApGetTanLineLicId());

						// 一般権限の変更不可項目チェック
						if(!Function.isAccessLevelAdmin(accessLevel)) {
							// 資産区分
							if(!Function.nvl(row.getLicAssetType(), "").equals(Function.nvl(license.getLicAssetType(), ""))) {
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr + "ライセンス・資産-取得/資産-資産区分", "資産区分には「" + license.getLicAssetTypeName() + "」以外は指定できません。"));
							}
						}

						// 保有会社
						if(!Function.nvl(row.getHolCompanyCode(), "").equals(Function.nvl(license.getHolCompanyCode(), ""))) {
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr + "保有・使用許諾-保有会社", "保有会社には「" + license.getHolCompanyName() + "」以外は指定できません。"));
						}

						// 使用許諾会社
						if(!Function.nvl(row.getUseCompanyCode(), "").equals(Function.nvl(license.getUseCompanyCode(), ""))) {
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr + "保有・使用許諾-使用許諾会社", "使用許諾会社には「" + license.getUseCompanyName() + "」以外は指定できません。"));
						}
					}
				}

				licenseList.add(row); // リターンに追加

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
	 * @see jp.co.ctcg.elicense.session.LicenseSession#applyCreateApLicenseBulk(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.elicense.dto.license.License)
	 */
	public List<String> applyCreateApLicenseBulk(String loginStaffCode, String accessLevel, String fileId, License obj) throws AppException {
		//////////////////////////////////// 一括作成
		List<String> licenseIdList = createApLicenseBulk(loginStaffCode, accessLevel, fileId, obj);

		//////////////////////////////////// バリデーション(申請中ステータスでの)
		StringBuffer errorMessage = new StringBuffer();
		for(int i = 0; i < licenseIdList.size(); i++) {
			String rowNumStr = "[" + (i + 1) + "件目] ";

			String licenseId = licenseIdList.get(i);
			License apLicense = getLicense(licenseId, true, true);

			apLicense.setApStatus(Constants.AP_STATUS_LICENSE_APPLY);
			String rowErrMsg = validateLicense(loginStaffCode, accessLevel, apLicense, true, null);
			if(rowErrMsg.length() > 0) {
				errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "以下のエラーがあります。"));
				errorMessage.append(rowErrMsg);
			}
		}

		if(errorMessage.length() > 0) throw new AppException(errorMessage.toString()); // バリデーションエラー有

		//////////////////////////////////// 更新
		Long eappId = null;
		obj.setLicenseId(licenseIdList.get(0));
		eappId = callEappService(obj); // e申請連携

		for(int i = 0; i < licenseIdList.size(); i++) {
			String licenseId = licenseIdList.get(i);
			License apLicense = getLicense(licenseId, true, true);

			// e申請ID・ステータスを更新
			apLicense.setEappId(eappId);
			apLicense.setApStatus(Constants.AP_STATUS_LICENSE_APPLY);

			updateLicense(loginStaffCode, accessLevel, apLicense, true, false, false, null, null);

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME_AP_PREFIX + HIST_ENTITY_NAME, licenseId, HIST_OPERATION_APPLY, null);
		}

		return licenseIdList;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getSoftwareMakerIdByName(java.lang.String)
	 */
	public Long getSoftwareMakerIdByName(String softwareMakerName) {
		LicenseDAO licenseDAO = new LicenseDAO();

		try {
			List<SoftwareMaker> list = licenseDAO.selectSoftwareMakerList(softwareMakerName, null, false, false);

			Long ret = null;
			if(list.size() > 0) ret = list.get(0).getSoftwareMakerId();

			return ret;
		} catch (SQLException e) {
			throw new SysException("ソフトウェアメーカー検索に失敗しました", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getSoftwareIdByName(java.lang.Long, java.lang.String)
	 */
	public Long getSoftwareIdByName(Long softwareMakerId, String softwareName) {
		LicenseDAO licenseDAO = new LicenseDAO();

		try {
			List<Software> list = licenseDAO.selectSoftwareList(softwareMakerId, softwareName, null, false, false);

			Long ret = null;
			if(list.size() > 0) ret = list.get(0).getSoftwareId();

			return ret;
		} catch (SQLException e) {
			throw new SysException("ソフトウェア検索に失敗しました", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#searchSoftwareMaker(java.lang.String, boolean)
	 */
	public List<SoftwareMaker> searchSoftwareMaker(String softwareMakerName, boolean enableOnly) {
		LicenseDAO licenseDAO = new LicenseDAO();
		try {
			return licenseDAO.selectSoftwareMakerList(null, softwareMakerName, enableOnly, false);
		} catch (SQLException e) {
			throw new SysException("ソフトウェアメーカーの検索に失敗しました", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createSoftwareMaker(java.lang.String, jp.co.ctcg.easset.dto.license.SoftwareMaker)
	 */
	public Long createSoftwareMaker(String staffCode, SoftwareMaker softwareMaker) throws AppException{
		LicenseDAO licenseDAO = new LicenseDAO();
		try {

			StringBuffer errMsg = new StringBuffer();
			//////////////////////////////////// 項目定義バリデーション
			int status = 1;

			// ヘッダ
			errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_SOFTWARE_MAKER, "NEA_SOFTWARE_MAKER", softwareMaker, status, null));

			//	同一名チェック
			List<SoftwareMaker> list = licenseDAO.selectSoftwareMakerCheckList(softwareMaker.getSoftwareMakerName(), false, false);
			if(list.size() > 0){
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "同一名（もしくは類似名）のソフトウェアが既に存在するため保存できません。"));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 固定値セット
			//	作成者・作成日,更新者・更新日
			Date sysdate = new Date();
			softwareMaker.setCreateStaffCode(staffCode);
			softwareMaker.setCreateDate(sysdate);
			softwareMaker.setUpdateStaffCode(staffCode);
			softwareMaker.setUpdateDate(sysdate);

			// 申請者・申請日
			if(softwareMaker.getApStaffCode() == null) {
				softwareMaker.setApStaffCode(staffCode);
			}
			if(softwareMaker.getApDate() == null) {
				softwareMaker.setApDate(sysdate);
			}

			// 削除フラグ・削除実行者・削除日
			softwareMaker.setDeleteFlag(Function.nvl(softwareMaker.getDeleteFlag(), Constants.FLAG_NO));
			if(softwareMaker.getDeleteFlag().equals(Constants.FLAG_YES)) {
				softwareMaker.setDeleteStaffCode(staffCode);
				softwareMaker.setDeleteDate(sysdate);
			}

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(staffCode, Constants.cOM_OP_FUNCTION_SOFTWARE_MAKER_SETTING, Constants.COM_OP_OPERATION_CREATE, Function.toString(softwareMaker));

			//////////////////////////////////// 登録
			return licenseDAO.insertSoftwareMaker(staffCode, softwareMaker);
		} catch (SQLException e) {
			throw new SysException("ソフトウェアメーカーの作成に失敗しました", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#updateSoftwareMaker(java.lang.String, jp.co.ctcg.easset.dto.license.SoftwareMaker)
	 */
	public void updateSoftwareMaker(String staffCode, SoftwareMaker softwareMaker) throws AppException{
		LicenseDAO licenseDAO = new LicenseDAO();
		try {

			StringBuffer errMsg = new StringBuffer();
			//////////////////////////////////// 項目定義バリデーション
			int status = 1;

			// ヘッダ
			errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_SOFTWARE_MAKER, "NEA_SOFTWARE_MAKER", softwareMaker, status, null));

			//	同一名チェック
			List<SoftwareMaker> list = licenseDAO.selectSoftwareMakerCheckList(softwareMaker.getSoftwareMakerName(), true, true);
			if(list.size() > 0){
				boolean flag = false;
				for(int i = 0; i < list.size(); i++){
					SoftwareMaker item = list.get(i);
					//	同一IDではなく、同一名（もしくは類似名）データが存在?
					if(item.getSoftwareMakerId().compareTo(softwareMaker.getSoftwareMakerId()) != 0){
						flag = true;
						break;
					}
				}
				if(flag) errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "同一名（もしくは類似名）のソフトウェアが既に存在するため保存できません。"));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 固定値セット
			//	更新者・更新日
			Date sysdate = new Date();
			softwareMaker.setUpdateStaffCode(staffCode);
			softwareMaker.setUpdateDate(sysdate);

			// 削除フラグ・削除実行者・削除日
			softwareMaker.setDeleteFlag(Function.nvl(softwareMaker.getDeleteFlag(), Constants.FLAG_NO));
			if(softwareMaker.getDeleteFlag().equals(Constants.FLAG_YES)) {
				softwareMaker.setDeleteStaffCode(staffCode);
				softwareMaker.setDeleteDate(sysdate);
			}

			//////////////////////////////////// 登録
			licenseDAO.updateSoftwareMaker(staffCode, softwareMaker);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(staffCode, Constants.cOM_OP_FUNCTION_SOFTWARE_MAKER_SETTING, Constants.COM_OP_OPERATION_UPDATE, Function.toString(softwareMaker));
		} catch (SQLException e) {
			throw new SysException("ソフトウェアメーカーのアップデートに失敗しました", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#searchSoftware(java.lang.Long, java.lang.String, boolean)
	 */
	public List<Software> searchSoftware(Long softwareMakerId, String softwareName, boolean enableOnly) {
		LicenseDAO licenseDAO = new LicenseDAO();
		try {
			return licenseDAO.selectSoftwareList(softwareMakerId, null, softwareName, enableOnly, false);
		} catch (SQLException e) {
			throw new SysException("ソフトウェアの検索に失敗しました", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createSoftware(java.lang.String, jp.co.ctcg.easset.dto.license.Software)
	 */
	public Long createSoftware(String staffCode, Software software) throws AppException{
		LicenseDAO licenseDAO = new LicenseDAO();
		try {

			StringBuffer errMsg = new StringBuffer();
			//////////////////////////////////// 項目定義バリデーション
			int status = 1;

			// ヘッダ
			errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_SOFTWARE, "NEA_SOFTWARE", software, status, null));

			//	同一名チェック
			List<Software> list = licenseDAO.selectSoftwareListCheckList(software.getSoftwareMakerId(), software.getSoftwareName(), false, false);
			if(list.size() > 0){
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "同一名（もしくは類似名）のソフトウェアが既に存在するため保存できません。"));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 固定値セット
			//	作成者・作成日,更新者・更新日,
			Date sysdate = new Date();
			software.setCreateStaffCode(staffCode);
			software.setCreateDate(sysdate);
			software.setUpdateStaffCode(staffCode);
			software.setUpdateDate(sysdate);

			// 申請者・申請日
			if(software.getApStaffCode() == null) {
				software.setApStaffCode(staffCode);
			}
			if(software.getApDate() == null) {
				software.setApDate(sysdate);
			}

			// 削除フラグ・削除実行者・削除日
			software.setDeleteFlag(Function.nvl(software.getDeleteFlag(), Constants.FLAG_NO));
			if(software.getDeleteFlag().equals(Constants.FLAG_YES)) {
				software.setDeleteStaffCode(staffCode);
				software.setDeleteDate(sysdate);
			}

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(staffCode, Constants.cOM_OP_FUNCTION_SOFTWARE_SETTING, Constants.COM_OP_OPERATION_CREATE, Function.toString(software));

			//////////////////////////////////// 登録
			return licenseDAO.insertSoftware(staffCode, software);
		} catch (SQLException e) {
			throw new SysException("ソフトウェアの作成に失敗しました", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#updateSoftware(java.lang.String, jp.co.ctcg.easset.dto.license.Software)
	 */
	public void updateSoftware(String staffCode, Software software) throws AppException{
		LicenseDAO licenseDAO = new LicenseDAO();
		try {

			StringBuffer errMsg = new StringBuffer();
			//////////////////////////////////// 項目定義バリデーション
			int status = 1;

			// ヘッダ
			errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_SOFTWARE, "NEA_SOFTWARE", software, status, null));

			//	同一名チェック
			List<Software> list = licenseDAO.selectSoftwareListCheckList(software.getSoftwareMakerId(), software.getSoftwareName(), false, false);
			if(list.size() > 0){
				boolean flag = false;
				for(int i = 0; i < list.size(); i++){
					Software item = list.get(i);
					//	同一IDではなく、同一名（もしくは類似名）データが存在?
					if(item.getSoftwareId().compareTo(software.getSoftwareId()) != 0){
						flag = true;
						break;
					}
				}
				if(flag) errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "同一名（もしくは類似名）のソフトウェアが既に存在するため保存できません。"));
			}


			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 固定値セット
			//	更新者・更新日
			Date sysdate = new Date();
			software.setUpdateStaffCode(staffCode);
			software.setUpdateDate(sysdate);

			// 削除フラグ・削除実行者・削除日
			software.setDeleteFlag(Function.nvl(software.getDeleteFlag(), Constants.FLAG_NO));
			if(software.getDeleteFlag().equals(Constants.FLAG_YES)) {
				software.setDeleteStaffCode(staffCode);
				software.setDeleteDate(sysdate);
			}

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(staffCode, Constants.cOM_OP_FUNCTION_SOFTWARE_SETTING, Constants.COM_OP_OPERATION_UPDATE, Function.toString(software));

			//////////////////////////////////// 登録
			licenseDAO.updateSoftware(staffCode, software);
		} catch (SQLException e) {
			throw new SysException("ソフトウェアのアップデートに失敗しました", e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.elicense.session.LicenseSession#bulkApplyApLicense(java.lang.String, java.lang.String, java.util.List)
	 */
	public void bulkApplyApLicense(String loginStaffCode, String accessLevel, List<License> apLicenseList) throws AppException {
		//////////////////////////////////// バリデーション
		StringBuffer errMsg = new StringBuffer();
		for(int i = 0; i < apLicenseList.size(); i++) {
			License apLicenseListData = apLicenseList.get(i);
			License apLicense = getLicense(apLicenseListData.getLicenseId(), true, true);

			// バージョンチェック
			if(apLicenseListData.getVersion().intValue() != apLicense.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, apLicenseListData.getLicenseId(), Msg.ERR_MSG_VER_LIST));
			}

			// ステータスチェック(未申請・差戻しのみ可)
			if(!Function.nvl(apLicenseListData.getApStatus(), "").equals(Constants.AP_STATUS_LICENSE_NOAPPLY)
				&& !Function.nvl(apLicenseListData.getApStatus(), "").equals(Constants.AP_STATUS_LICENSE_SENDBACK)) {
				// ステータスエラーは即座にスロー
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG_STAT_LIST, Constants.AP_STATUS_NAME_LICENSE_NOAPPLY + "、" + Constants.AP_STATUS_NAME_LICENSE_SENDBACK));
			}

			//////////////////////////////////// 更新
			if(errMsg.length() == 0) {
				try {
					applyApLicense(loginStaffCode, accessLevel, apLicense);
				} catch (AppException e) {
					String errMessage = e.getMessage();
					errMessage = Msg.getBindMessage(Msg.ERR_MSG, apLicense.getLicenseId() + "の処理中に以下のエラーが発生しました。") + errMessage;
					throw new AppException(errMessage);
				}
			}
		}

		if(errMsg.length() > 0) { // エラーあり
			throw new AppException(errMsg.toString());
		}

		//////////////////////////////////// 申請
		License apLicenseFirst = apLicenseList.get(0);
		Long eappId = callEappService(apLicenseFirst); // e申請連携

		for(int i = 0; i < apLicenseList.size(); i++) {
			String assetId = apLicenseList.get(i).getLicenseId();
			License apLicense = getLicense(assetId, true, true);

			// e申請ID・ステータスを更新
			apLicense.setEappId(eappId);
			apLicense.setApStatus(Constants.AP_STATUS_LICENSE_APPLY);
			try {
				updateLicense(loginStaffCode, accessLevel, apLicense, true, false, false, null, null);
			} catch (AppException e) {
				String errMessage = e.getMessage();
				errMessage = Msg.getBindMessage(Msg.ERR_MSG, apLicense.getLicenseId() + "の処理中に以下のエラーが発生しました。") + errMessage;
				throw new AppException(errMessage);
			}

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME_AP_PREFIX + HIST_ENTITY_NAME, assetId, HIST_OPERATION_APPLY, null);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.elicense.session.LicenseSession#bulkDeleteApLicense(java.lang.String, java.lang.String, java.util.List)
	 */
	public void bulkDeleteApLicense(String loginStaffCode, String accessLevel, List<License> apLicenseList) throws AppException {
		for(int i = 0; i < apLicenseList.size(); i++) {
			License apLicense = apLicenseList.get(i);

			//////////////////////////////////// バリデーション
			// ステータスチェック(未申請・差戻しのみ可)
			if(!Function.nvl(apLicense.getApStatus(), "").equals(Constants.AP_STATUS_LICENSE_NOAPPLY)
				&& !Function.nvl(apLicense.getApStatus(), "").equals(Constants.AP_STATUS_LICENSE_SENDBACK)) {
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG_STAT_LIST, Constants.AP_STATUS_NAME_LICENSE_NOAPPLY + "、" + Constants.AP_STATUS_NAME_LICENSE_SENDBACK));
			}

			//////////////////////////////////// 更新
			try {
				deleteLicense(loginStaffCode, accessLevel, apLicense, true);
			} catch (AppException e) {
				String errMessage = e.getMessage();
				errMessage = Msg.getBindMessage(Msg.ERR_MSG, apLicense.getLicenseId() + "の処理中に以下のエラーが発生しました。") + errMessage;
				throw new AppException(errMessage);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.elicense.session.LicenseSession#bulkCreateLicense(java.lang.String, java.lang.String, java.util.List)
	 */
	public void bulkCreateLicense(String loginStaffCode, String accessLevel, List<License> apLicenseList) throws AppException {
		StringBuffer errMsg = new StringBuffer();
		for(int i = 0; i < apLicenseList.size(); i++) {
			License apLicenseListData = apLicenseList.get(i);
			License apLicense = getLicense(apLicenseListData.getLicenseId(), true, true);

			//////////////////////////////////// バリデーション
			// バージョンチェック
			if(apLicenseListData.getVersion().intValue() != apLicense.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, apLicenseListData.getLicenseId(), Msg.ERR_MSG_VER_LIST));
			}

			// ステータスチェック(申請中のみ可)
			if(!Function.nvl(apLicenseListData.getApStatus(), "").equals(Constants.AP_STATUS_LICENSE_APPLY)) {
				// ステータスエラーは即座にスロー
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG_STAT_LIST, Constants.AP_STATUS_NAME_LICENSE_APPLY));
			}

			//////////////////////////////////// 更新
			if(errMsg.length() == 0) {
				try {
					createLicense(loginStaffCode, accessLevel, apLicense, false);
				} catch (AppException e) {
					String errMessage = e.getMessage();
					errMessage = Msg.getBindMessage(Msg.ERR_MSG, apLicenseListData.getLicenseId() + "の処理中に以下のエラーが発生しました。") + errMessage;
					throw new AppException(errMessage);
				}
			}
		}

		if(errMsg.length() > 0) { // エラーあり
			throw new AppException(errMsg.toString());
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#getLicenseAllocList(java.lang.String, java.lang.Long)
	 */
	public List<LicenseAlloc> getLicenseAllocList(Long assetLineOsId) {
		LicenseDAO licenseDAO = new LicenseDAO();
		try {
			List<LicenseAlloc> ret = null;
			if(assetLineOsId == null) {
				ret = new ArrayList<LicenseAlloc>();
			} else {
				LicenseAlloc obj =  new LicenseAlloc();
				obj.setAssetLineOsId(assetLineOsId);

				ret = licenseDAO.selectLicenseAllocList(obj);
			}
			return ret;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス割当の検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#createLicenseAlloc(java.lang.String, java.lang.String, java.lang.String, long, java.util.List)
	 */
	public void createLicenseAlloc(String loginStaffCode, String accessLevel, String assetId, long assetLineOsId, List<String> licenseIdList) throws AppException {
		List<LicenseAlloc> allocList = new ArrayList<LicenseAlloc>();
		for(int i = 0; i < licenseIdList.size(); i++) {
			LicenseAlloc alloc = new LicenseAlloc();
			alloc.setAssetId(assetId);
			alloc.setAssetLineOsId(assetLineOsId);
			alloc.setLicenseId(licenseIdList.get(i));
			allocList.add(alloc);
		}

		createLicenseAlloc(loginStaffCode, accessLevel, allocList);
	}

	/**
	 * ライセンス割当
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param licenseAllocList 割当情報 assetId, assetLineOsId, licenseIdを使用
	 * @throws AppException
	 */
	private void createLicenseAlloc(String loginStaffCode, String accessLevel, List<LicenseAlloc> licenseAllocList) throws AppException {
		LicenseDAO licenseDAO = new LicenseDAO();
		try {

			////////////////////////////////////// 割当
			StringBuffer errMsg = new StringBuffer();
			HashMap<Long, HashSet<String>> upgradeLicenseIdMap = new HashMap<Long, HashSet<String>>(); // アップグレードライセンスチェック用
			HashMap<Long, String> assetIdMap = new HashMap<Long, String>(); // アップグレードライセンスチェック用

			for(int i = 0; i < licenseAllocList.size(); i++){
				LicenseAlloc licenseAlloc = licenseAllocList.get(i);
				Long licenseAllocOsId = licenseAlloc.getAssetLineOsId();

				if(!upgradeLicenseIdMap.containsKey(licenseAllocOsId)) {
					upgradeLicenseIdMap.put(licenseAllocOsId, new HashSet<String>());
					assetIdMap.put(licenseAllocOsId, licenseAlloc.getAssetId());
				}

				String licenseId = licenseAlloc.getLicenseId();

				StringBuffer rowErrMsg = new StringBuffer();

				////////////////////////////////////// テーブルロック
				//	ライセンステーブルロック
				License license = getLicense(licenseId, true, false);
				//	ライセンス_数量管理明細情報取得
				List<LicenseLineQty> licenseLineQtyList = licenseDAO.selectLicenseLineQtyForAlloc(licenseId, licenseAlloc.getAssetId(), license.getUseType(), license.getLicQuantityType());

				// アップグレードライセンス処理用
				if(Function.nvl(license.getLicUpgradeFlag(), "").equals(Constants.FLAG_YES)) {
					upgradeLicenseIdMap.get(licenseAllocOsId).add(license.getLicenseId());
				}

				/////////////////////////////////////// バリデーション
				//	有効数 > 消費数存在?
				if(licenseLineQtyList.size() <= 0){
					rowErrMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "情報機器等:" + licenseAlloc.getAssetId() + "に対して、ライセンス:" + licenseId + "は割当できません。原因としては以下が考えられます。\n・ライセンスの有効数が不足\n・情報機器等の保有部署がライセンスの使用許諾範囲外"));
				}

				//	現割当情報取得
				LicenseAlloc param =  new LicenseAlloc();
				param.setLicenseId(licenseId);
				param.setAssetId(licenseAlloc.getAssetId());
				param.setAssetLineOsId(licenseAlloc.getAssetLineOsId());
				List<LicenseAlloc> licenseAllocOldList = licenseDAO.selectLicenseAllocList(param);
				//	既に割当？
				if(licenseAllocOldList.size() > 0){
					rowErrMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "情報機器等:" + licenseAlloc.getAssetId() + "に対して、ライセンス:" + licenseId + "は既に割当られています。"));
				}

				//	エラーあり
				if(rowErrMsg.length() > 0){
					errMsg.append(rowErrMsg);
				}

				if(errMsg.length() > 0) {
					continue; // エラー発生が分かった時点で後続処理は行わない。
				}

				LicenseLineQty licenseLineQty = licenseLineQtyList.get(0);

				Date sysdate = new Date();
				//////////////////////////////////// ライセンス数量管理明細更新
				licenseLineQty.setUpdateDate(sysdate);
				licenseLineQty.setUpdateStaffCode(loginStaffCode);
				licenseLineQty.setLicLineUseQuantity(licenseLineQty.getLicLineUseQuantity() + 1);
				licenseDAO.updateLicenseLineQty(loginStaffCode, accessLevel, licenseLineQty);

				//////////////////////////////////// ライセンス割当作成
				licenseAlloc.setCreateStaffCode(loginStaffCode);
				licenseAlloc.setCreateDate(sysdate);
				licenseAlloc.setUpdateStaffCode(loginStaffCode);
				licenseAlloc.setUpdateDate(sysdate);

				licenseAlloc.setLicenseLineQtyId(licenseLineQty.getLicenseLineQtyId()); // ライセンス数量管理明細ID設定

				licenseDAO.insertLicenseAlloc(loginStaffCode, accessLevel, licenseAlloc);

				//////////////////////////////////// ライセンス更新（バージョン更新・履歴作成）
				if(i == (licenseAllocList.size() - 1) || !Function.nvl(licenseAllocList.get(i + 1).getLicenseId(), "").equals(licenseId)) {
					updateLicense(loginStaffCode, accessLevel, license, false, HIST_OPERATION_CREATE_LICENSE_ALLOC);
				}
			}

			if(errMsg.length() > 0) { // エラーあり
				throw new AppException(errMsg.toString());
			}

			////////////////////////////////////// アップグレードライセンスのチェック
			if(upgradeLicenseIdMap.size() > 0) {
				errMsg = new StringBuffer();

				Set<Entry<Long, HashSet<String>>> set = upgradeLicenseIdMap.entrySet();
				for(Iterator<Entry<Long, HashSet<String>>> iterOsId = set.iterator(); iterOsId.hasNext();) {
					Entry<Long, HashSet<String>> entry = iterOsId.next();
					Long assetLineOsId = entry.getKey();
					HashSet<String> upgLicenseIdSet = entry.getValue();

					if(entry.getValue().size() == 0) continue; // アップグレードライセンス無し

					///////////////// 情報機器の割当情報取得
					List<LicenseAlloc> allocList = getLicenseAllocList(assetLineOsId);
					HashSet<String> allocLicenseIdSet = new HashSet<String>();
					for(int i = 0; i < allocList.size(); i++) {
						allocLicenseIdSet.add(allocList.get(i).getLicenseId());
					}

					HashSet<String> upgMarkIdSet = new HashSet<String>(); // アップグレード済として使用されているアップグレード元ライセンス

					///////////////// アップグレード関連処理
					for(Iterator<String> iter = upgLicenseIdSet.iterator(); iter.hasNext();) {
						String licenseId = iter.next();
						boolean upgAllocOk = false; // アップグレード割当可判定

						///////////////// 割り当てられているライセンスにアップグレード元(他のアップグレード先割当無し)が含まれているか確認
						List<LicenseLineUpg> upgSrcList = licenseDAO.selectLicenseLineUpgSrc(licenseId);
						if(upgSrcList != null && upgSrcList.size() > 0) {
							for(int i = 0; i < upgSrcList.size(); i++) {
								String srcLincenseId = upgSrcList.get(i).getLicenseId();

								if(allocLicenseIdSet.contains(srcLincenseId)) { // アップグレード元ライセンスの割当あり
									if(upgMarkIdSet.contains(srcLincenseId)) {
										// 他アップグレード先ライセンスで割り当て済
										break;
									} else {
										upgAllocOk = true;
										List<LicenseLineUpg> upgDstList = licenseDAO.selectLicenseLineUpgDst(srcLincenseId);
										for(int j = 0; j < upgDstList.size(); j++) {
											String dstLicenseId = upgDstList.get(j).getUpgradeLicenseId();
											if(!dstLicenseId.equals(licenseId) && allocLicenseIdSet.contains(dstLicenseId)) { // 自分以外のアップグレード先割当あり
												upgAllocOk = false;
												break;
											}
										}
									}
									upgMarkIdSet.add(srcLincenseId);
								}
							}
						}

						///////////////// 上記にアップグレード元がない場合はエラー
						if(!upgAllocOk) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "情報機器等:" + assetIdMap.get(assetLineOsId) + "に対して、アップグレードライセンス:" + licenseId + "を割り当てるには、先にアップグレード元ライセンスを割り当ててください。"));
						}
					}

					if(errMsg.length() > 0) { // エラーあり
						throw new AppException(errMsg.toString());
					}
				}
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス割当の作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#deleteLicenseAlloc(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.license.LicenseAlloc)
	 */
	public void deleteLicenseAlloc(String loginStaffCode, String accessLevel, LicenseAlloc licenseAlloc ) {
		LicenseDAO licenseDAO = new LicenseDAO();

		try {
			//	ライセンステーブルロック
			License lisence = getLicense(licenseAlloc.getLicenseId(), true, false);
			if(lisence != null){

				licenseDAO.callDeleteLicenseAlloc(loginStaffCode, licenseAlloc.getLicenseId(), licenseAlloc.getLicenseLineQtyId(), licenseAlloc.getAssetId(), licenseAlloc.getAssetLineOsId());

				//////////////////////////////////// 履歴作成
				histSession.createHistData(HIST_ENTITY_NAME, licenseAlloc.getLicenseId(), HIST_OPERATION_DELETE_LICENSE_ALLOC, "消費数");
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス割当の削除"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#deleteLicenseAllocAllByLic(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void deleteLicenseAllocAllByLic(String loginStaffCode, String accessLevel, String licenseId) {
		LicenseDAO licenseDAO = new LicenseDAO();

		try {
			//	ライセンステーブルロック
			License lisence = getLicense(licenseId, true, false);
			if(lisence != null){
				LicenseAlloc param = new LicenseAlloc();
				param.setLicenseId(licenseId);
				List<LicenseAlloc> licenseAllocList = licenseDAO.selectLicenseAllocList(param);

				for(int i = 0; i < licenseAllocList.size(); i++) {
					LicenseAlloc licenseAlloc = licenseAllocList.get(i);
					licenseDAO.callDeleteLicenseAlloc(loginStaffCode, licenseAlloc.getLicenseId(), licenseAlloc.getLicenseLineQtyId(), licenseAlloc.getAssetId(), licenseAlloc.getAssetLineOsId());
				}

				//////////////////////////////////// 履歴作成
				histSession.createHistData(HIST_ENTITY_NAME, licenseId, HIST_OPERATION_DELETE_LICENSE_ALLOC, "消費数");
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス割当の削除"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#deleteLicenseAllocAllByAstLineOs(java.lang.String, java.lang.String, java.lang.String, long)
	 */
	public void deleteLicenseAllocAllByAstLineOs(String loginStaffCode, String accessLevel, String assetId, long assetLineOsId) {
		LicenseDAO licenseDAO = new LicenseDAO();

		try {
			LicenseAlloc param = new LicenseAlloc();
			param.setAssetId(assetId);
			param.setAssetLineOsId(assetLineOsId);
			List<LicenseAlloc> licenseAllocList = licenseDAO.selectLicenseAllocList(param);

			for(int i = 0; i < licenseAllocList.size(); i++) {
				//	ライセンステーブルロック
				LicenseAlloc licenseAlloc = licenseAllocList.get(i);
				License lisence = getLicense(licenseAlloc.getLicenseId(), true, false);
				if(lisence != null){
					licenseDAO.callDeleteLicenseAlloc(loginStaffCode, licenseAlloc.getLicenseId(), licenseAlloc.getLicenseLineQtyId(), licenseAlloc.getAssetId(), licenseAlloc.getAssetLineOsId());

					//////////////////////////////////// 履歴作成
					histSession.createHistData(HIST_ENTITY_NAME, licenseAlloc.getLicenseId(), HIST_OPERATION_DELETE_LICENSE_ALLOC, "消費数");
				}
			}


		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス割当の削除"), e);
		}
	}

	/**
	 * 登録申請作成時の取得申請明細バリデーション
	 * @param obj 登録申請データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateApGeTanLineLic(License obj) {
		StringBuffer errMsg = new StringBuffer();

		// 取得申請明細 <-> 登録申請の整合性チェック
		if(!Function.nvl(obj.getGetApplicationId(), "").equals("") && obj.getApGetTanLineLicId() != null) {
			// 取得申請取得(&ロック)
			ApGetTan apGetTanObj = apGetTanSession.getApGetTan(obj.getGetApplicationId(), true);
			// 取得申請の該当明細確認
			if(obj.getApGetTanLineLicId() != null) {
				errMsg = new StringBuffer(Msg.getBindMessage(Msg.ERR_MSG, "該当する取得申請明細が見つかりません。"));
				if(apGetTanObj != null) {
					List<ApGetTanLineLic> list = apGetTanObj.getApGetTanLineLicList();
					if(list != null && list.size() > 0) {
						for(int i = 0; i < list.size(); i++) {
							ApGetTanLineLic lineLicObj = list.get(i);
							Long lineLicId = Function.nvl(lineLicObj.getApGetTanLineLicId(), Long.valueOf(-1));

							// 取得申請に明細あり
							if(lineLicId.longValue() == obj.getApGetTanLineLicId()) {
								// 取得数と登録数の比較
								int qty = Function.nvl(lineLicObj.getQuantity(), Integer.valueOf(0)).intValue();
								int regQty = Function.nvl(lineLicObj.getRegistQuantity(), Integer.valueOf(0)).intValue();

								if(qty > regQty) {
									errMsg = new StringBuffer(); // 登録可能
									break;
								} else {
									errMsg = new StringBuffer(Msg.getBindMessage(Msg.ERR_MSG, "取得申請明細の取得数を超えてしまうのでこれ以上登録できません。")); // 残数量無
									break;
								}
							}
						}
					}
				}
			}
		}

		return errMsg.toString();
	}

	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @param isAp true:ライセンス登録申請,false:ライセンス
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateLicense(String loginStaffCode, String accessLevel, License obj, boolean isAp, Set<String> updatePropSet) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 修正権限バリデーション
		if(!Function.nvl(obj.getLicenseId(), "").equals("")) { // 修正
			if(!isEditableLicense(loginStaffCode, accessLevel, obj.getLicenseId(), isAp)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_EDIT_AUTH));
				return errMsg.toString();
			}
		}

		//////////////////////////////////// 項目定義バリデーション
		int apStatus;
		String itemDefName;

		if(isAp) { // 登録申請
			apStatus = obj.getApStatus() == null ? null : Integer.valueOf(obj.getApStatus());

			if(!Function.isAccessLevelAdmin(accessLevel)) { // 全社権限ではない場合
				apStatus += 4;
			}

			itemDefName = Constants.CATEGORY_CODE_ITEM_DEF_AP_LICENSE;
		} else { // ライセンス
			apStatus = Function.nvl(obj.getDeleteFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO) ? 1 : 2;

			if(!Function.isAccessLevelAdmin(accessLevel)) { // 全社権限ではない場合
				apStatus += 2;
			}

			itemDefName = Constants.CATEGORY_CODE_ITEM_DEF_LICENSE;
		}


		// ヘッダ
		errMsg.append(masterSession.validateItemDef(itemDefName, "NEA_" + (isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + "LICENSE", obj, apStatus, updatePropSet));

		if(updatePropSet == null){
			// 明細
			if(!isAp) {
				List<LicenseLineQty> qtyLineRental = obj.getLicenseLineQtyRental();
				if(qtyLineRental != null && qtyLineRental.size() != 0) {
					for(int i = 1; i < qtyLineRental.size(); i++) {
						LicenseLineQty qtyObj = qtyLineRental.get(i);
						errMsg.append(masterSession.validateItemDef(itemDefName, "NEA_" + (isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + "LICENSE_LINE_QTY", qtyObj, apStatus, null));
					}
				}
			}
		}

		// 添付明細
		if(isAp) {
			List<ApLicenseLineAtt> attLine = obj.getApLicenseLineAtt();
			if(attLine != null && attLine.size() > 0) {
				for(int i = 0; i < attLine.size(); i++) {
					ApLicenseLineAtt attObj = attLine.get(i);
					errMsg.append(masterSession.validateItemDef(itemDefName, "NEA_AP_LICENSE_LINE_ATT", attObj, apStatus, null));
				}
			}
		}

		//////////////////////////////////// 条件付必須バリデーション
		// 保有数
		if(Function.nvl(obj.getLicQuantityType(), "").equals(Constants.LIC_QUANTITY_TYPE_LIMIT)) {
			if(obj.getLicQuantity() == null) {
				if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("licQuantity"))){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "ライセンス・資産-保有数"));
				}
			}
		}

		// 消費数
		if(!isAp) {
			if(!Function.nvl(obj.getLicLicenseType(), "").equals("")) {
				CodeName licenseType = masterSession.getCodeName(Constants.CATEGORY_CODE_LICENSE_TYPE, obj.getLicLicenseType(), null, null);
				if(licenseType != null) {
					if(Function.nvl(licenseType.getValue2(), "").equals(Constants.FLAG_NO)) { // 割当不可
						if(obj.getLicUseQuantity() == null) {
							if(updatePropSet == null){
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "ライセンス・資産-消費数"));
							}
						}
					}
				}
			}
		}

		// タームライセンス契約区分/期間
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("licTermFlag"))){
			if(Function.nvl(obj.getLicTermFlag(), "").equals(Constants.FLAG_YES)) {
				if(Function.nvl(obj.getTrmContractType(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "ライセンス・資産-タームライセンス-契約区分"));
				}
				if(obj.getTrmStartDate() == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "ライセンス・資産-タームライセンス-期間(自)"));
				}
				if(obj.getTrmEndDate() == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "ライセンス・資産-タームライセンス-期間(至)"));
				}
			}
		}
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("trmParentLicenseId"))){
			// タームライセンス更新元ライセンス
			if(Function.nvl(obj.getTrmContractType(), "").equals(Constants.LICENSE_TERM_CONTRACT_TYPE_UPDATE)) {
				if(Function.nvl(obj.getTrmParentLicenseId(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "ライセンス・資産-タームライセンス-更新元ライセンス"));
				}
			}
		}
		// 使用許諾
		if(!isAp) {
			if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("useType")) || (updatePropSet != null && updatePropSet.contains("useSectionCode"))){
				if(Function.nvl(obj.getUseType(), "").equals(Constants.LICENSE_USE_TYPE_COMPANY)) {
					if(Function.nvl(obj.getUseCompanyCode(), "").equals("")) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "保有・使用許諾-使用許諾会社"));
					}
				} else if(Function.nvl(obj.getUseType(), "").equals(Constants.LICENSE_USE_TYPE_SECTION)) {
					if(Function.nvl(obj.getUseSectionCode(), "").equals("")) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "保有・使用許諾-使用許諾部署"));
					}
				}
			}
		}

		// 貸出明細
		if(!isAp) {
			if(Function.nvl(obj.getUseRentalFlag(), "").equals(Constants.FLAG_YES)) {
				List<LicenseLineQty> qtyLineRental = obj.getLicenseLineQtyRental();
				if(qtyLineRental == null || qtyLineRental.size() == 0) { // １行目は許諾部署に対する明細なので無視
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "保有・使用許諾-貸出", "1件以上"));
				}
			}
		}

		// 添付明細
		if(isAp) {
			List<ApLicenseLineAtt> attLine = obj.getApLicenseLineAtt();
			String getApplicationId = Function.nvl(obj.getGetApplicationId(), "");
			// 取得申請書番号が無い場合は必須
			if(getApplicationId.equals("") && (attLine == null || attLine.size() == 0)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "添付明細", "1件以上"));
			}
		}

		//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)
		// 申請者
		//	一括更新項目以外のバリデーションなので一括更新時以外のときのみチェック
		if(updatePropSet == null){
			if(!Function.nvl(obj.getApStaffCode(), "").equals("")) {
				// 登録申請では無い場合（ライセンス照会/修正）申請会社が空白の場合がある。その場合は保有会社を使用。
				String companyCode = Function.nvl(obj.getApCompanyCode(), "");
				if(companyCode.equals("")) companyCode = Function.nvl(obj.getHolCompanyCode(), "");

				if(!isAp || Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_LICENSE_APPROVE)) {
					// ライセンス登録・承認済は退職社員OK
					if(masterSession.getStaff(companyCode, obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				} else {
					// 承認済以外は退職社員NG
					if(masterSession.getStaffValid(companyCode, obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				}
			}
		}

		// 保守契約担当者(退職社員OK)
		//	一括更新以外と一括更新で保守契約担当者を更新時にチェック
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("mntContractStaffCode"))){
			if(!Function.nvl(obj.getMntContractStaffCode(), "").equals("")) {
				if(masterSession.getStaff(obj.getHolCompanyCode(), obj.getMntContractStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保守契約-保守契約担当者"));
				}
			}
		}

		//////////////////////////////////// マスタ整合性バリデーション2(CSV入力を考慮した整合性チェック)
		// 親ライセンス理番号の整合性
		if(updatePropSet == null){
			if(!Function.nvl(obj.getParentLicenseId(), "").equals("")) {
				License parent = getLicense(obj.getParentLicenseId(), false);
				if(parent == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "親ライセンス管理番号"));
				} else if(Function.nvl(parent.getDeleteFlag(), "").equals(Constants.FLAG_YES)) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "親ライセンス管理番号", "抹消済みのライセンスは指定できません。"));
				}
			}
		}

		// 取得方法 <-> 資産区分の整合性
		//	一括更新以外と一括更新で取得方法を更新時にチェック
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("licAssetType")) || (updatePropSet != null && updatePropSet.contains("licGetType")) ){
			String assetType = Function.nvl(obj.getLicAssetType(), "");
			String getType = Function.nvl(obj.getLicGetType(), "");
			if(!assetType.equals("") && !getType.equals("")) {
				ArrayList<String> valueParamList = new ArrayList<String>();
				valueParamList.add(null); // value1
				valueParamList.add(assetType); // value2
				CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_LICENSE_GET_TYPE, null, null, valueParamList);

				if(cn != null) {
					if(!Function.nvl(cn.getInternalCode(), "").equals(getType)) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ライセンス・資産-取得方法", "資産区分「" + obj.getLicAssetTypeName() + "」では取得方法「" + obj.getLicGetTypeName() + "」は設定できません。"));
					}
				}
			}
		}

		// 更新元タームライセンス理番号の整合性
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("trmParentLicenseId")) ){
			if(!Function.nvl(obj.getTrmParentLicenseId(), "").equals("")) {
				License parent = getLicense(obj.getTrmParentLicenseId(), false);
				if(parent == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "ライセンス・資産-タームライセンス-更新元ライセンス"));
				} else if(Function.nvl(parent.getLicTermFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ライセンス・資産-タームライセンス-更新元ライセンス", "タームライセンス以外のライセンスが指定されています。"));
				} else if(!isEditableLicense(loginStaffCode, accessLevel, parent.getLicenseId(), false)) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ライセンス・資産-タームライセンス-更新元ライセンス", "修正権限の無い(管理対象外の)ライセンスが指定されています。"));
				}
			}
		}

		// 保有部署
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("holSectionCode")) ){
			if(!Function.nvl(obj.getHolSectionCode(), "").equals("")) {
				Section sec = masterSession.getSection(obj.getHolCompanyCode(), obj.getHolSectionCode(), obj.getHolSectionYear());
				if(sec == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有・使用許諾-保有部署"));
				}
			}
		}

		// 資産管理担当者(退職社員OK)
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("holStaffCode")) ){
			if(!Function.nvl(obj.getHolStaffCode(), "").equals("")) {
				if(masterSession.getStaff(null, obj.getHolStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有・使用許諾-資産管理担当者"));
				}
			}
		}

		// 使用許諾部署
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("useSectionCode")) ){
			if(!Function.nvl(obj.getUseSectionCode(), "").equals("")) {
				Section sec = masterSession.getSection(obj.getUseCompanyCode(), obj.getUseSectionCode(), obj.getUseSectionYear());
				if(sec == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有・使用許諾-使用許諾部署"));
				}
			}
		}

		//////////////////////////////////// その他バリデーション
		// 日付範囲チェック タームライセンス期間From > ターム期間To
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("trmStartDate")) || (updatePropSet != null && updatePropSet.contains("trmEndDate"))){
			if(obj.getTrmStartDate() != null && obj.getTrmEndDate() != null) {
				if(obj.getTrmStartDate().compareTo(obj.getTrmEndDate()) > 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "ライセンス・資産-タームライセンス-期間(至)", "期間(自)"));
				}
			}
		}

		// 日付範囲チェック タームライセンス期限通知日 > ターム期間To
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("trmAlertDate")) || (updatePropSet != null && updatePropSet.contains("trmEndDate"))){
			if(obj.getTrmAlertDate() != null && obj.getTrmEndDate() != null) {
				if(obj.getTrmAlertDate().compareTo(obj.getTrmEndDate()) > 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MAX_VALUE, "ライセンス・資産-タームライセンス-期限通知日", "期間(至)"));
				}
			}
		}

		// 日付範囲チェック 保守契約期間From > 保守契約期間To
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("mntContractStartDate")) || (updatePropSet != null && updatePropSet.contains("mntContractEndDate"))){
			if(obj.getMntContractStartDate() != null && obj.getMntContractEndDate() != null) {
				if(obj.getMntContractStartDate().compareTo(obj.getMntContractEndDate()) > 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "保守契約-契約期間(至)", "契約期間(自)"));
				}
			}
		}

		return errMsg.toString();
	}

	/**
	 * バリデーション(数量整合性)
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 更新予定データオブジェクト
	 * @param objObj 更新前(現)データオブジェクト(更新時のみ)
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateLicenseQuantity(String loginStaffCode, String accessLevel, License obj, License oldObj, Set<String> updatePropSet) {
		StringBuffer errMsg = new StringBuffer();

		LicenseLineQty useQtyObj = obj.getLicenseQtyUse(); // 許諾情報取得
		List<LicenseLineQty> rentalQtyList = obj.getLicenseLineQtyRental(); // 貸出明細
		List<LicenseLineUpg> upgSrcList = obj.getLicenseLineUpgSrc(); // アップグレード元明細
		List<LicenseLineUpg> upgDstList = obj.getLicenseLineUpgDst(); // アップグレード先明細

		//////////////////////////////////// 一括更新時は(数量整合性)チェックは行わない。
		if(updatePropSet != null){
			return errMsg.toString();
		}

		//////////////////////////////////// 更新時変更制御
		if(oldObj != null) {
			// 消費済貸出明細の削除
			List<LicenseLineQty> rentalQtyOldList = oldObj.getLicenseLineQtyRental();
			if(rentalQtyOldList != null && rentalQtyOldList.size() > 0) {
				for(int i = 0; i < rentalQtyOldList.size(); i++) {
					LicenseLineQty oldQtyRow = rentalQtyOldList.get(i);
					if(Function.nvl(oldQtyRow.getLicLineUseQuantity(), Long.valueOf(0)).longValue() > 0) { // 消費済
						// 更新後明細に含まれているかを確認
						boolean isDelete = true;
						for(int j = 0; j < rentalQtyList.size(); j++) {
							LicenseLineQty newQtyRow = rentalQtyList.get(j);
							if(newQtyRow.getLicenseLineQtyId().longValue() == oldQtyRow.getLicenseLineQtyId().longValue()) { // 更新後明細にも
								isDelete = false;
								break;
							}
						}

						if(isDelete) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "保有・使用許諾-貸出", "割当済(消費数：" + oldQtyRow.getLicLineUseQuantity().longValue() + ")の貸出明細は削除できません。(" + oldQtyRow.getUseSectionName() + ")"));
						}
					}
				}
			}

			// 割当可(マシンライセンス) -> 割当不可(ユーザライセンス)に変更しようとしているが既に消費されている
			if(!Function.nvl(obj.getLicLicenseType(), "").equals("") && !Function.nvl(oldObj.getLicLicenseType(), "").equals("")
				&& !Function.nvl(obj.getLicLicenseType(), "").equals(Function.nvl(oldObj.getLicLicenseType(), ""))) { // 変更有
				CodeName licenseTypeNew = masterSession.getCodeName(Constants.CATEGORY_CODE_LICENSE_TYPE, obj.getLicLicenseType(), null, null);
				CodeName licenseTypeOld = masterSession.getCodeName(Constants.CATEGORY_CODE_LICENSE_TYPE, oldObj.getLicLicenseType(), null, null);
				if(licenseTypeNew != null && licenseTypeOld != null) { // 割当可 -> 割当不可
					if(Function.nvl(licenseTypeNew.getValue2(), "").equals(Constants.FLAG_NO)
						&& Function.nvl(licenseTypeOld.getValue2(), "").equals(Constants.FLAG_YES)
							) {
						long useQty = Function.nvl(oldObj.getLicUseQuantity(), Long.valueOf(0)).longValue();
						if(useQty > 0) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ライセンス・資産-ライセンス形態", "既に割当済(消費数：" + useQty + ")のため「" + obj.getLicLicenseTypeName() + "」には変更できません。変更するには全ての割当を解除してください。"));
						}
					}
				}
			}
		}

		//////////////////////////////////// 明細重複/自身を設定
		// 貸出明細重複/自身を設定
		HashSet<String> sectionSet = new HashSet<String>();
		List<String> dupSectionList = new ArrayList<String>();
		boolean isSelfExists = false;
		if(rentalQtyList != null && rentalQtyList.size() > 0) {
			for(int i = 0; i < rentalQtyList.size(); i++) {
				LicenseLineQty row = rentalQtyList.get(i);
				String key = row.getUseCompanyCode() + "_" + row.getUseSectionCode() + "_" + row.getUseSectionYear().intValue();
				if(sectionSet.contains(key)) { // 重複有
					dupSectionList.add(row.getUseSectionName());
				}

				sectionSet.add(key);

				if(Function.nvl(obj.getUseCompanyCode(), "").equals(Function.nvl(row.getUseCompanyCode(), ""))
					&& Function.nvl(obj.getUseSectionCode(), "").equals(Function.nvl(row.getUseSectionCode(), ""))
					&& Function.nvl(obj.getUseSectionYear(), Integer.valueOf(0)).intValue() == Function.nvl(row.getUseSectionYear(), Integer.valueOf(0)).intValue()) {
					isSelfExists = true; // 自身を登録している
				}
			}
		}
		if(dupSectionList.size() > 0) {// 重複エラー
			StringBuffer sectionNameList = new StringBuffer();
			for(int i = 0; i < dupSectionList.size(); i++) {
				if(i != 0) sectionNameList.append("、");
				sectionNameList.append(dupSectionList.get(i));
			}
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "保有・使用許諾-貸出", "同じ部署に対する貸出設定は複数件登録できません。(" + sectionNameList.toString() + ")"));
		}
		if(isSelfExists) {// 自身登録エラー
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "保有・使用許諾-貸出", "許諾部署と同様の部署は設定できません。(" + obj.getUseSectionName() + ")"));
		}

		// アップグレード元明細重複/自身を設定/無限複数割当(有限・無限混在)
		HashSet<String> licenseIdSrcSet = new HashSet<String>();
		StringBuffer dupLicenseIdSrcStr = new StringBuffer();
		boolean isSelfExistsSrc = false;
		boolean unlimitedExists = false;
		if(upgSrcList != null && upgSrcList.size() > 0) {
			for(int i = 0; i < upgSrcList.size(); i++) {
				LicenseLineUpg row = upgSrcList.get(i);
				if(licenseIdSrcSet.contains(row.getLicenseId())) { // 重複有
					if(dupLicenseIdSrcStr.length() > 0) dupLicenseIdSrcStr.append("、");
					dupLicenseIdSrcStr.append(row.getLicenseId());
				}
				licenseIdSrcSet.add(row.getLicenseId());

				if(Function.nvl(obj.getLicenseId(), "").equals(Function.nvl(row.getLicenseId(), ""))) isSelfExistsSrc = true; // 自身を登録している

				if(row.getLicUpgradeQuantity() == null) unlimitedExists = true;
			}
		}
		if(dupLicenseIdSrcStr.length() > 0) {// 重複エラー
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード元", "同じライセンスのアップグレード設定は複数件登録できません。(" + dupLicenseIdSrcStr.toString() + ")"));
		}
		if(isSelfExistsSrc) {// 自身登録エラー
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード元", "自身を設定する事はできません。"));
		}
		if(unlimitedExists && upgSrcList != null && upgSrcList.size() > 1) { // 無限複数割当(有限・無限混在)
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード元", "無限割当を行っている場合は、アップグレード元明細は1件しか設定できません。"));
		}

		// アップグレード先明細重複/自身を設定
		HashSet<String> licenseIdDstSet = new HashSet<String>();
		StringBuffer dupLicenseIdDstStr = new StringBuffer();
		boolean isSelfExistsDst = false;
		if(upgDstList != null && upgDstList.size() > 0) {
			for(int i = 0; i < upgDstList.size(); i++) {
				LicenseLineUpg row = upgDstList.get(i);
				if(licenseIdDstSet.contains(row.getUpgradeLicenseId())) { // 重複有
					if(dupLicenseIdDstStr.length() > 0) dupLicenseIdDstStr.append("、");
					dupLicenseIdDstStr.append(row.getUpgradeLicenseId());
				}
				licenseIdDstSet.add(row.getUpgradeLicenseId());

				if(Function.nvl(obj.getLicenseId(), "").equals(Function.nvl(row.getUpgradeLicenseId(), ""))) isSelfExistsDst = true; // 自身を登録している
			}
		}
		if(dupLicenseIdDstStr.length() > 0) {// 重複エラー
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード先", "同じライセンスのアップグレード設定は複数件登録できません。(" + dupLicenseIdDstStr.toString() + ")"));
		}
		if(isSelfExistsDst) {// 自身登録エラー
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード先", "自身を設定する事はできません。"));
		}

		// アップグレード元-先明細重複
		StringBuffer dupLicenseIdBothStr = new StringBuffer();
		for(Iterator<String> iter = licenseIdSrcSet.iterator(); iter.hasNext();) {
			String licenseId = iter.next();
			if(licenseIdDstSet.contains(licenseId)) {
				if(dupLicenseIdBothStr.length() > 0) dupLicenseIdBothStr.append("、");
				dupLicenseIdBothStr.append(licenseId);
			}
		}
		if(dupLicenseIdBothStr.length() > 0) {// 重複エラー
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード", "アップグレード元とアップグレード先に同じライセンスは登録できません。(" + dupLicenseIdBothStr.toString() + ")"));
		}

		//////////////////////////////////// 貸出数オーバー
		boolean isRentalOverError = false;
		if(useQtyObj.getLicLineEnableQuantity() != null && useQtyObj.getLicLineEnableQuantity().longValue() < 0) {
			isRentalOverError = true;
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "保有・使用許諾-貸出", "貸出数合計が有効数を超えています。"));
		}

		//////////////////////////////////// 消費数オーバー(有効数 < 消費数)
		// 貸出明細
		if(rentalQtyList != null && rentalQtyList.size() > 0) {
			for(int i = 0; i < rentalQtyList.size(); i++) {
				LicenseLineQty row = rentalQtyList.get(i);
				long enableQty = Function.nvl(row.getLicLineEnableQuantity(), Long.valueOf(0)).longValue(); // 有効数
				long useQty = Function.nvl(row.getLicLineUseQuantity(), Long.valueOf(0)).longValue(); // 消費数

				if(enableQty < useQty) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "保有・使用許諾-貸出[No" + row.getLineSeq() + "]-貸出数", "貸出数には消費数(割当済数)以上の値しか入力できませ。"));
				}
			}
		}

		// 許諾
		if(!isRentalOverError) {
			if(useQtyObj.getLicLineEnableQuantity() != null) { // 有限
				long enableQty = useQtyObj.getLicLineEnableQuantity().longValue(); // 有効数
				long useQty = Function.nvl(useQtyObj.getLicLineUseQuantity(), Long.valueOf(0)).longValue(); // 消費数

				if(enableQty < useQty) {
					if(rentalQtyList == null || rentalQtyList.size() == 0) { // 貸出明細無し
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ライセンス・資産-有効数", "有効数(" + enableQty + ")が消費数(" +  useQty + ")を下回ってしまいます。"));
					} else {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "保有・使用許諾", "使用許諾部署の有効数(" + enableQty + ")が消費数(" +  useQty + ")を下回ってしまいます。\n※　上記の有効数/消費数は他部署貸出分を引いた数です。"));
					}
				}
			}
		}

		//////////////////////////////////// アップグレード数オーバー
		// アップグレード元
		// アップグレード割当数 > 保有数
		if(Function.nvl(obj.getLicUpgradeFlag(), "").equals(Constants.FLAG_YES)
			&& Function.nvl(obj.getLicQuantityType(), "").equals(Constants.LIC_QUANTITY_TYPE_LIMIT)) { // 有限アップグレードライセンス
			if(upgSrcList != null && upgSrcList.size() > 0) {
				long upgQtyTotal = 0;
				for(int i = 0; i < upgSrcList.size(); i++) {
					long upgQty = Function.nvl(upgSrcList.get(i).getLicUpgradeQuantity(), Long.valueOf(0)).longValue();
					upgQtyTotal += upgQty;
				}

				long licQty = Function.nvl(obj.getLicQuantity(), Long.valueOf(0)).longValue();
				if(upgQtyTotal > licQty) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード元", "アップグレード割当数合計(" + upgQtyTotal + ")が保有数(" +  licQty + ")を超えています。"));
				}
			}
		}

		// アップグレード先
		// アップグレード割当数 > 有効数
		if(obj.getLicEnableQuantity() != null) { // 有効数有限
			if(upgDstList != null && upgDstList.size() > 0) {
				long upgQtyTotal = 0;
				for(int i = 0; i < upgDstList.size(); i++) {
					long upgQty = Function.nvl(upgDstList.get(i).getLicUpgradeQuantity(), Long.valueOf(0)).longValue();
					upgQtyTotal += upgQty;
				}

				long licEnableQty = obj.getLicEnableQuantity().longValue();
				if(upgQtyTotal > licEnableQty) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "アップグレード-アップグレード先", "アップグレード割当数合計(" + upgQtyTotal + ")が有効数(" +  licEnableQty + ")を超えています。"));
				}
			}
		}

		return errMsg.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#isEditableLicense(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public boolean isEditableLicense(String loginStaffCode, String accessLevel, String licenseId, boolean isAp) {
		boolean ret = false;

		License obj = getLicense(licenseId, isAp); // データ取得

		String holCompanyCode = Function.nvl(obj.getHolCompanyCode(), ""); // 保有会社
		String holSectionCode = Function.nvl(obj.getHolSectionCode(), ""); // 保有部署
		int holSectionYear = Function.nvl(obj.getHolSectionYear(), 0); // 保有部署年度
		String apStaffCode = Function.nvl(obj.getApStaffCode(), ""); // 申請者
		String apCreateStaffCode = Function.nvl(obj.getApCreateStaffCode(), ""); // 記票者

		if(Constants.STAFF_CODE_SYSTEM.equals(loginStaffCode)) {
			ret = true; // システム自動処理ユーザ
		} else if(apStaffCode.equals(loginStaffCode)
			|| apCreateStaffCode.equals(loginStaffCode)) {
				ret = true; // 申請者/起票者が自分の機器は修正可能
		} else {
			// 保有部署によるアクセスレベル取得
			if(Function.isAccessLevelAdmin(accessLevel)) { // 全社権限
				String level = masterSession.getAccessLevel(Constants.MENU_ID_LICENSE_SEARCH, loginStaffCode, holCompanyCode, null, 0);
				if(Function.isAccessLevelAdmin(level)) ret = true;
			} else if(Function.isAccessLevelSection(accessLevel)) { // 資産管理担当者/部署長
				String level = masterSession.getAccessLevel(Constants.MENU_ID_LICENSE_SEARCH, loginStaffCode, holCompanyCode, holSectionCode, holSectionYear);
				if(Function.isAccessLevelSection(level)) ret = true;
			}
		}

		// eAsset利用停止会社の全社権限修正許可
		if(!ret && Function.isAccessLevelAdmin(accessLevel)) {
			if(holCompanyCode.equals("")) { // 会社空白
				ret = true;
			} else {
				CodeName holCompany = masterSession.getCodeName(Constants.CATEGORY_CODE_USE_COMPANY, holCompanyCode, null, null);
				if(holCompany == null) ret = true; // eAsset利用停止
			}
		}

		return ret;
	}

	/**
	 * ライセンス割当の移動
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param fromLicenseId 移動元ライセンス
	 * @param toLicenseId 移動先ライセンス
	 * @throws AppException
	 */
	private void changeLicenseAlloc(String loginStaffCode, String accessLevel, String fromLicenseId, String toLicenseId) throws AppException {
		try {
			LicenseDAO dao = new LicenseDAO();

			// ライセンス割当取得
			LicenseAlloc param = new LicenseAlloc();
			param.setLicenseId(fromLicenseId);
			List<LicenseAlloc> allocList = dao.selectLicenseAllocList(param);

			if(allocList != null && allocList.size() > 0) {
				// 移動元のライセンス割当解除
				deleteLicenseAllocAllByLic(loginStaffCode, accessLevel, fromLicenseId);

				// 移動先への割当
				for(int i = 0; i < allocList.size(); i++) {
					LicenseAlloc alloc = allocList.get(i);
					alloc.setLicenseId(toLicenseId);
				}

				createLicenseAlloc(loginStaffCode, accessLevel, allocList);
			}
		} catch(SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス割当切替"), e);
		}

	}

	/**
	 *  数量管理明細使用部署情報更新(許諾部署のみ)
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 更新予定データオブジェクト
	 * @param licenseDAO データアクセス
	 * @throws AppException
	 */
	private void updateLicenseLineQtyUseSection(String loginStaffCode, String accessLevel, License obj, LicenseDAO licenseDAO) throws SQLException {
		try {

			LicenseLineQty licenseLineQty = new LicenseLineQty();


			licenseLineQty.setLicenseId(obj.getLicenseId());
			licenseLineQty.setUseCompanyCode(obj.getUseCompanyCode());
			licenseLineQty.setUseSectionCode(obj.getUseSectionCode());
			licenseLineQty.setUseSectionYear(obj.getUseSectionYear());
			licenseDAO.updateLicenseLineQtyUseSection(loginStaffCode, accessLevel, licenseLineQty);

		} catch(SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス数量管理明細使用部署情報更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LicenseSession#getUpdatePropertyList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<CodeName> getUpdatePropertyList(String loginStaffCode, String accessLevel, String fileId) throws AppException {
		CsvReaderRowHandler handler = null;
		try {
			// ファイル内の有効更新項目取得
			handler = new CsvReaderRowHandler(accessLevel, fileId, License.class, Constants.CATEGORY_CODE_ITEM_DEF_LICENSE);
			String[] inputProps = handler.getInputProps(); // 更新可能プロパティ取得
			HashSet<String> inputPropSet = new HashSet<String>();

			if(inputProps != null && inputProps.length > 0) {
				for(int i = 0; i < inputProps.length; i++) {
					inputPropSet.add(inputProps[i]);
				}
			}

			// DB上の更新項目一覧から、有効なもの意外を除外
			List<CodeName> propList = null;

			propList = masterSession.getDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_LICENSE, null);
			for(int i = (propList.size() - 1); i >= 0; i--) {
				CodeName row = propList.get(i);

				// ファイル内有効更新項目以外
				if(!inputPropSet.contains(row.getValue3())) {
					propList.remove(i);
					continue;
				}
			}

			return propList;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

}