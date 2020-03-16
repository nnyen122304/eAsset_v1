/*===================================================================
 * ファイル名 : BatchSessionBean.java
 * 概要説明   : バッチ処理セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-02-23 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.FileUtility;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLe;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeSR;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndReSR;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTan;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineAst;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineLic;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanSC;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanSR;
import jp.co.ctcg.easset.dto.asset.Asset;
import jp.co.ctcg.easset.dto.asset.AssetSC;
import jp.co.ctcg.easset.dto.asset.AssetSR;
import jp.co.ctcg.easset.dto.license.License;
import jp.co.ctcg.easset.dto.license.LicenseSC;
import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.dto.master.CompanyMasterData;
import jp.co.ctcg.easset.dto.master.DivisionMasterData;
import jp.co.ctcg.easset.dto.master.NewDivisionMasterData;
import jp.co.ctcg.easset.dto.master.SapAccountMasterData;
import jp.co.ctcg.easset.dto.master.SapAssignmentsMasterData;
import jp.co.ctcg.easset.dto.master.SapCustAccountsMasterData;
import jp.co.ctcg.easset.dto.master.SapExpDeptMasterData;
import jp.co.ctcg.easset.dto.master.SapVendorsMasterData;
import jp.co.ctcg.easset.dto.master.UserCompanyMasterData;
import jp.co.ctcg.easset.dto.master.UserMasterData;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;

@Stateless
public class BatchSessionBean implements BatchSession {


	@EJB
	MasterSession masterSession;			// マスタセッション

	@EJB
	LicenseSession licenseSession;			// ライセンスセッション

	@EJB
	AssetSession assetSession;				// 情報機器等セッション

	@EJB
	PpfsImportSession ppfsImportSession;	// ProPlus取込セッション

	@EJB
	ApEndTanSession apEndTanSession; 		// 固定資産除売却申請セッション

	@EJB
	ApEndLeSession apEndLeSession;			// リース満了・解約申請申請セッション

	@EJB
	ApEndReSession apEndReSession;			// レンタル買取申請申請セッション

	@EJB
	BatchSession batchSession;				// バッチセッション(個々のバッチを別トランザクションで実行)

	@EJB
	BatchSession childBatchSession;			// バッチセッション(バッチ内で個別トランザクションが必要な場合に使用)

	@EJB
	SendMailSession sendMailSession;		// メール送信セッション

	// バッチ種別
	private static final String BATCH_TYPE_DUMMY = "00";
	private static final String BATCH_TYPE_TERM_ALERT = "01";				// タームライセンス期間終了アラート
	private static final String BATCH_TYPE_TERM_DELETE = "02";				// タームライセンス期間終了自動抹消
	private static final String BATCH_TYPE_EGUARD_SYNC = "03";				// eGuard同期
	private static final String BATCH_TYPE_PPFS_IMPORT = "04";				// ProPlus取込
	private static final String BATCH_TYPE_AP_END_TAN_DELETE = "05";		// 固定資産除売却申請自動抹消
	private static final String BATCH_TYPE_AP_END_LE_DELETE = "06";			// リース満了・解約申請自動抹消
	private static final String BATCH_TYPE_KNIGHT_MASTER_IMPORT = "07";		// 人事マスタインポート
	private static final String BATCH_TYPE_SAP_ASSIGNMENTS_IMPORT = "08";	// 次期シスマスタインポート(従業員所属マスタ)
	private static final String BATCH_TYPE_SAP_ACCOUNT_IMPORT = "09";		// 次期シスマスタインポート(勘定科目マスタ)
	private static final String BATCH_TYPE_SAP_CUST_ACCOUNTS_IMPORT = "10";	// 次期シスマスタインポート(顧客マスタ)
	private static final String BATCH_TYPE_SAP_VENDORS_IMPORT = "11";		// 次期シスマスタインポート(仕入先マスタ)
	private static final String BATCH_TYPE_SAP_EXP_DEPT_IMPORT = "12";		// 次期シスマスタインポート(経費負担部課マスタ)
	private static final String BATCH_TYPE_AP_END_RE_ASSET_TYPE_CHANGE = "13";	// レンタル買取申請資産区分変更

	// メールテンプレート変数
	private static final String MAIL_TEMPLATE_VAR_NAME = "\\$\\{資産管理担当者\\}";
	private static final String MAIL_TEMPLATE_VAR_LIC_INFO = "\\$\\{ライセンス情報\\}";
	private static final String MAIL_TEMPLATE_VAR_LICENSE_ID = "\\$\\{ライセンス管理番号\\}";
	private static final String MAIL_TEMPLATE_VAR_SOFTWARE_MAKER_NAME = "\\$\\{メーカー\\}";
	private static final String MAIL_TEMPLATE_VAR_SOFTWARE_NAME = "\\$\\{ソフトウェア\\}";
	private static final String MAIL_TEMPLATE_VAR_TERM_START_DATE = "\\$\\{開始日\\}";
	private static final String MAIL_TEMPLATE_VAR_TERM_END_DATE = "\\$\\{終了日\\}";
	private static final String MAIL_TEMPLATE_VAR_TO_NAME = "\\$\\{宛名\\}";
	private static final String MAIL_TEMPLATE_VAR_AP_END_TAN = "\\$\\{抹消情報\\}";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#batchMain(java.lang.String[])
	 */
	public String batchMain(String[] args){
		try {
			// パラメータをセットに保存
			HashSet<String> argsSet = new HashSet<String>();
			if(args != null) {
				for(int i = 0; i < args.length; i++) {
					argsSet.add(args[i]);
				}
			}

			List<CodeName> batchInfoList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_BATCH_STATUS, null, "", null);

			if(batchInfoList != null){

				boolean sendAdminMailFlag = false;
				String addSubjectStr = "";
				StringBuffer adminMailText = new StringBuffer();
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				for(int i = 0; i < batchInfoList.size(); i++){
					CodeName batchInfo = (CodeName)batchInfoList.get(i);
					String batchType = Function.nvl(batchInfo.getInternalCode(), "");
					if(argsSet.contains(batchType)) { // メールヘッダ文言
						Logging.info("★" + batchInfo.getValue1() + "処理開始");
						Logging.info("batchType:" + batchType);
						adminMailText.append("**********************************\n");
						adminMailText.append("★" + batchInfo.getValue1() + "処理\n\n");
					}

					try {
						if(batchType.equals(BATCH_TYPE_TERM_ALERT) && argsSet.contains(batchType)){
							// タームライセンス期限通知
							adminMailText.append(batchSession.termAlert(batchInfo));
							adminMailText.append("\n\n\n");
						} else if(batchType.equals(BATCH_TYPE_TERM_DELETE) && argsSet.contains(batchType)){
							// タームライセンス自動抹消
							adminMailText.append(batchSession.termDelete(batchInfo));
							adminMailText.append("\n\n\n");
						} else if(batchType.equals(BATCH_TYPE_EGUARD_SYNC) && argsSet.contains(batchType)){
							// eGuard同期
							adminMailText.append(batchSession.eGuardSync(batchInfo));
							adminMailText.append("\n\n\n");
						} else if(batchType.equals(BATCH_TYPE_PPFS_IMPORT) && argsSet.contains(batchType)){
							// ProPlusデータ取込
							adminMailText.append(batchSession.ppfsImport(batchInfo));
							adminMailText.append("\n\n\n");
						} else if(batchType.equals(BATCH_TYPE_AP_END_TAN_DELETE) && argsSet.contains(batchType)){
							// 固定資産除売却申請自動抹消
							adminMailText.append(batchSession.apEndTanDelete(batchInfo));
							adminMailText.append("\n\n\n");
						} else if(batchType.equals(BATCH_TYPE_AP_END_LE_DELETE) && argsSet.contains(batchType)){
							// リース満了・解約申請自動抹消
							adminMailText.append(batchSession.apEndLeDelete(batchInfo));
							adminMailText.append("\n\n\n");
						} else if(batchType.equals(BATCH_TYPE_KNIGHT_MASTER_IMPORT) && argsSet.contains(batchType)){
							// 人事マスタインポート
							adminMailText.append(batchSession.knightMasterImport(batchInfo));
							adminMailText.append("\n\n\n");
							sendAdminMailFlag = true;
							addSubjectStr = batchInfo.getValue1();
						} else if((batchType.equals(BATCH_TYPE_SAP_ASSIGNMENTS_IMPORT) ||
								   batchType.equals(BATCH_TYPE_SAP_ACCOUNT_IMPORT) ||
								   batchType.equals(BATCH_TYPE_SAP_CUST_ACCOUNTS_IMPORT) ||
								   batchType.equals(BATCH_TYPE_SAP_VENDORS_IMPORT) ||
								   batchType.equals(BATCH_TYPE_SAP_EXP_DEPT_IMPORT)) &&
								   argsSet.contains(batchType)){
							// 次期シスマスタインポート
							adminMailText.append(batchSession.sapMasterImport(batchInfo));
							adminMailText.append("\n\n\n");
							sendAdminMailFlag = true;
							addSubjectStr = batchInfo.getValue1();
						} else if(batchType.equals(BATCH_TYPE_AP_END_RE_ASSET_TYPE_CHANGE) && argsSet.contains(batchType)){
							// レンタル買取申請資産区分変更
							adminMailText.append(batchSession.apEndReAssetTypeChange(batchInfo));
							adminMailText.append("\n\n\n");
						}
					} catch (Exception e) {
						adminMailText.append(batchInfo.getValue1() + "処理中に以下のエラーが発生しました。\n\n");
						adminMailText.append(e.getMessage());
						adminMailText.append(Function.getStackTraceStr(e));
					}
				}

				// バッチ実行処理内容をADMINへメール送信
				if(adminMailText.length() != 0){
					String fromTo = null;
					String cc = null;
					// メールテンプレート取得
					if(sendAdminMailFlag) {
						CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_SYSTEM_ADMIN_EMAIL, null, null, null);
						fromTo = cn.getValue1();
					}
					else {
						CodeName mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_BATCH_MAIL_TEMPLATE, BATCH_TYPE_DUMMY, null, null);
						fromTo = mailTemplate.getValue2();
						cc = mailTemplate.getValue3();
					}
					String adminMailSubject = "バッチ処理結果";
					if(!addSubjectStr.equals("")) {
						adminMailSubject += "-" + addSubjectStr;
					}
					adminMailSubject += "(" + df.format(new Date()) + ")";

					sendMailSession.sendMail(fromTo, fromTo, cc, adminMailSubject, adminMailText.toString());
				}
			}
		} catch (Exception e) {
			throw new SysException("バッチ処理エラー", e);
		}
		return "SUCCESS";
	}

	/**
	 * バッチ情報バックアップ設定
	 * @param batchInfo バッチ情報
	 * @param adminMailText メール本文
	 */
	private void setBatchInfoHist(CodeName batchInfo, String adminMailText) {
		batchInfo.setValue12(batchInfo.getValue11());
		batchInfo.setValue11(batchInfo.getValue10());
		batchInfo.setValue10(batchInfo.getValue9());
		batchInfo.setValue9(batchInfo.getValue8());
		batchInfo.setValue8(batchInfo.getValue7());
		batchInfo.setValue7(batchInfo.getValue6());
		batchInfo.setValue6(batchInfo.getValue5());
		batchInfo.setValue5(batchInfo.getValue4());
		batchInfo.setValue4(Function.cutStringByte(adminMailText, Constants.MAX_CHAR_SIZE_CODE_NAME_VALUE));
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#termAlert(jp.co.ctcg.easset.dto.CodeName)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String termAlert(CodeName batchInfo) {
		StringBuffer adminMailText = new StringBuffer();

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		batchInfo.setValue2(dtf.format(new Date())); // 開始時刻セット

		// 対象ライセンスの検索
		LicenseSC licenseSc = new LicenseSC();
		licenseSc.setSearchScopeType(Constants.SEARCH_SCOPE_TYPE_ALL);
		licenseSc.setDeleteFlag(Constants.FLAG_NO);
		licenseSc.setLicTermFlag(Constants.FLAG_YES);
		String lastExecDateStr = Function.nvl(batchInfo.getValue3(), "");
		if(!lastExecDateStr.equals("")){
			Date d = Function.getDateFromStr(lastExecDateStr.substring(0, 10), "yyyy/MM/dd");
			d = Function.dateAdd(d, Calendar.DATE, 1); // 前回バッチ実行日の翌日
			licenseSc.setTrmAlertDateFrom(d);
		}
		licenseSc.setTrmAlertDateTo(Function.getDateFromStr(batchInfo.getValue2().substring(0, 10), "yyyy/MM/dd"));

		List<LicenseSR> licenseList = licenseSession.searchLicense(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, licenseSc, false);

		// メールテンプレート取得
		CodeName mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_BATCH_MAIL_TEMPLATE, BATCH_TYPE_TERM_ALERT, null, null);
		String from = mailTemplate.getValue2();
		String cc = mailTemplate.getValue3();
		String subject = mailTemplate.getValue4();
		String bodyTemplate = mailTemplate.getValue5(); // 本文
		String bodyLicInfoTemplate = mailTemplate.getValue6(); // 本文（ライセンス情報：繰り返し部）

		StringBuffer bodyLicInfo = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		for(int i = 0; i < licenseList.size(); i++){
			LicenseSR licenseData = (LicenseSR)licenseList.get(i);
			String holStaffCode = Function.nvl(licenseData.getHolStaffCode(), "");
			String holStaffName = Function.nvl(licenseData.getHolStaffName(), "");

			try{
				// ライセンス情報部のメール予約後置換
				String bodyLicInfoRow = bodyLicInfoTemplate.replaceAll(MAIL_TEMPLATE_VAR_LICENSE_ID, Function.nvl(licenseData.getLicenseId(), ""));
				bodyLicInfoRow = bodyLicInfoRow.replaceAll(MAIL_TEMPLATE_VAR_SOFTWARE_MAKER_NAME,  Function.nvl(licenseData.getSoftwareMakerName(), ""));
				bodyLicInfoRow = bodyLicInfoRow.replaceAll(MAIL_TEMPLATE_VAR_SOFTWARE_NAME,  Function.nvl(licenseData.getSoftwareName(), ""));
				bodyLicInfoRow = bodyLicInfoRow.replaceAll(MAIL_TEMPLATE_VAR_TERM_START_DATE,  df.format(licenseData.getTrmStartDate()));
				bodyLicInfoRow = bodyLicInfoRow.replaceAll(MAIL_TEMPLATE_VAR_TERM_END_DATE,  df.format(licenseData.getTrmEndDate()));

				bodyLicInfo.append(bodyLicInfoRow + "\n");

				// 最終行もしくは、次行と資産管理担当者が異なる場合メール送信
				if(i == licenseList.size() - 1 || !Function.nvl(licenseList.get(i + 1).getHolStaffCode(), "").equals(holStaffCode)) {
					// 資産管理担当者社員情報取得
					User holStaffData = masterSession.getStaffValid(null, holStaffCode);
					if(holStaffData != null){
						// 本文のメール予約後置換
						String body = bodyTemplate.replaceAll(MAIL_TEMPLATE_VAR_NAME, holStaffName);
						body = body.replaceAll(MAIL_TEMPLATE_VAR_LIC_INFO, bodyLicInfo.toString());

						sendMailSession.sendMail(from, holStaffData.getMailAddress(), cc, subject, body);
					}
				}

				// 管理者向けメール本文
				adminMailText.append("　" + holStaffCode + " " + holStaffName + " " + licenseData.getLicenseId() + "\n");
				Logging.info("　" + holStaffCode + " " + holStaffName + " " + licenseData.getLicenseId() + "\n");
			} catch(Exception e){
				Logging.error("　" + holStaffCode + " " + holStaffName + " " + licenseData.getLicenseId() + "(※エラー発生)", e);
				adminMailText.append("　" + holStaffCode + " " + holStaffName + " " + licenseData.getLicenseId() + "(※エラー発生)\n");
			} finally {
				if(i == licenseList.size() - 1 || !Function.nvl(licenseList.get(i + 1).getHolStaffCode(), "").equals(holStaffCode)) {
					bodyLicInfo = new StringBuffer();
				}
			}
		}

		// 終了日セット
		batchInfo.setValue3(dtf.format(new Date()));

		StringBuffer adminMailHeader = new StringBuffer();
		adminMailHeader.append("　実行時間：" + batchInfo.getValue2() + " - " + batchInfo.getValue3() + "\n");
		adminMailHeader.append("　処理件数：" + licenseList.size() + "件\n\n");
		adminMailHeader.append("　資産管理担当者　 管理番号\n");
		adminMailHeader.append("　------------------------------------\n");
		adminMailText.insert(0, adminMailHeader.toString());

		// 実行内容履歴・実行内容セット
		setBatchInfoHist(batchInfo, adminMailText.toString());

		// バッチ処理ステータス更新
		masterSession.updateCodeName(Constants.STAFF_CODE_SYSTEM, batchInfo);

		return adminMailText.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#termDelete(jp.co.ctcg.easset.dto.CodeName)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String termDelete(CodeName batchInfo) throws AppException {
		StringBuffer adminMailText = new StringBuffer();

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		batchInfo.setValue2(dtf.format(new Date())); // 開始時刻セット

		// 対象ライセンスの検索
		LicenseSC licenseSc = new LicenseSC();
		licenseSc.setSearchScopeType(Constants.SEARCH_SCOPE_TYPE_ALL);
		licenseSc.setDeleteFlag(Constants.FLAG_NO);
		licenseSc.setLicTermFlag(Constants.FLAG_YES);
		licenseSc.setTrmEndDateTo(Function.dateAdd(Function.getDateFromStr(batchInfo.getValue2().substring(0, 10), "yyyy/MM/dd"), Calendar.DATE, -1));

		List<LicenseSR> licenseList = licenseSession.searchLicense(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, licenseSc, false);

		// メールテンプレート取得
		CodeName mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_BATCH_MAIL_TEMPLATE, BATCH_TYPE_TERM_DELETE, null, null);
		String from = mailTemplate.getValue2();
		String cc = mailTemplate.getValue3();
		String subject = mailTemplate.getValue4();
		String bodyTemplate = mailTemplate.getValue5(); // 本文
		String bodyLicInfoTemplate = mailTemplate.getValue6(); // 本文（ライセンス情報：繰り返し部）

		StringBuffer bodyLicInfo = new StringBuffer();
		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		ArrayList<License> deleteLicList = new ArrayList<License>(); // 抹消対象
		for(int i = 0; i < licenseList.size(); i++){
			LicenseSR licenseData = (LicenseSR)licenseList.get(i);
			deleteLicList.add(licenseData);
		}

		// 抹消処理
		if(deleteLicList.size() != 0){
			childBatchSession.termDeleteChild(batchInfo, deleteLicList);
		}

		// メール送信
		for(int i = 0; i < licenseList.size(); i++){
			LicenseSR licenseData = (LicenseSR)licenseList.get(i);

			String holStaffCode = Function.nvl(licenseData.getHolStaffCode(), "");
			String holStaffName = Function.nvl(licenseData.getHolStaffName(), "");

			try{
				// ライセンス情報部のメール予約後置換
				String bodyLicInfoRow = bodyLicInfoTemplate.replaceAll(MAIL_TEMPLATE_VAR_LICENSE_ID, Function.nvl(licenseData.getLicenseId(), ""));
				bodyLicInfoRow = bodyLicInfoRow.replaceAll(MAIL_TEMPLATE_VAR_SOFTWARE_MAKER_NAME,  Function.nvl(licenseData.getSoftwareMakerName(), ""));
				bodyLicInfoRow = bodyLicInfoRow.replaceAll(MAIL_TEMPLATE_VAR_SOFTWARE_NAME,  Function.nvl(licenseData.getSoftwareName(), ""));
				bodyLicInfoRow = bodyLicInfoRow.replaceAll(MAIL_TEMPLATE_VAR_TERM_START_DATE,  df.format(licenseData.getTrmStartDate()));
				bodyLicInfoRow = bodyLicInfoRow.replaceAll(MAIL_TEMPLATE_VAR_TERM_END_DATE,  df.format(licenseData.getTrmEndDate()));

				bodyLicInfo.append(bodyLicInfoRow + "\n");

				// 最終行もしくは、次行と資産管理担当者が異なる場合メール送信
				if(i == licenseList.size() - 1 || !Function.nvl(licenseList.get(i + 1).getHolStaffCode(), "").equals(holStaffCode)) {
					// 資産管理担当者社員情報取得
					User holStaffData = masterSession.getStaffValid(null, holStaffCode);
					if(holStaffData != null){
						// 本文のメール予約後置換
						String body = bodyTemplate.replaceAll(MAIL_TEMPLATE_VAR_NAME, holStaffName);
						body = body.replaceAll(MAIL_TEMPLATE_VAR_LIC_INFO, bodyLicInfo.toString());

						sendMailSession.sendMail(from, holStaffData.getMailAddress(), cc, subject, body);
					}
				}

				// 管理者向けメール本文
				adminMailText.append("　" + holStaffCode + " " + holStaffName + " " + licenseData.getLicenseId() + "\n");
				Logging.info("　" + holStaffCode + " " + holStaffName + " " + licenseData.getLicenseId() + "\n");
			} catch(Exception e){
				Logging.error("　" + holStaffCode + " " + holStaffName + " " + licenseData.getLicenseId() + "(※メール送信エラー発生)", e);
				adminMailText.append("　" + holStaffCode + " " + holStaffName + " " + licenseData.getLicenseId() + "(※メール送信エラー発生)\n");
			} finally {
				if(i == licenseList.size() - 1 || !Function.nvl(licenseList.get(i + 1).getHolStaffCode(), "").equals(holStaffCode)) {
					bodyLicInfo = new StringBuffer();
				}
			}
		}

		// 終了日セット
		batchInfo.setValue3(dtf.format(new Date()));

		StringBuffer adminMailHeader = new StringBuffer();
		adminMailHeader.append("　実行時間：" + batchInfo.getValue2() + " - " + batchInfo.getValue3() + "\n");
		adminMailHeader.append("　処理件数：" + licenseList.size() + "件\n\n");
		adminMailHeader.append("　資産管理担当者　 ライセンス管理番号\n");
		adminMailHeader.append("　------------------------------------\n");

		adminMailText.insert(0, adminMailHeader.toString());

		// 実行内容履歴・実行内容セット
		setBatchInfoHist(batchInfo, adminMailText.toString());

		// バッチ処理ステータス更新
		masterSession.updateCodeName(Constants.STAFF_CODE_SYSTEM, batchInfo);

		return adminMailText.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#termDeleteChild(jp.co.ctcg.easset.dto.CodeName, java.util.List)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void termDeleteChild(CodeName batchInfo, List<License> licenseList) throws AppException {
		licenseSession.deleteLicenseLogical(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, Function.getDateFromStr(batchInfo.getValue2().substring(0, 10), "yyyy/MM/dd"), "期間終了により自動抹消",  licenseList);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#eGuardSync(jp.co.ctcg.easset.dto.CodeName)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String eGuardSync(CodeName batchInfo) {
		StringBuffer adminMailText = new StringBuffer();

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		batchInfo.setValue2(dtf.format(new Date())); // 開始時刻セット

		//////////////////////////////////// 対象情報機器の検索
		String lastExecDateStr = Function.nvl(batchInfo.getValue3(), "");

		// 前回バッチ実行以降に更新があった情報機器等
		AssetSC assetSc = new AssetSC();
		assetSc.setSearchScopeType(Constants.SEARCH_SCOPE_TYPE_ALL);
		if(!lastExecDateStr.equals("")){
			assetSc.setUpdateDateFrom(Function.getDateFromStr(lastExecDateStr.substring(0, 10), "yyyy/MM/dd"));
		}
		List<AssetSR> assetList = assetSession.searchAsset(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, assetSc, false);

		// 抹消日が到来した情報機器等
		assetSc = new AssetSC();
		assetSc.setSearchScopeType(Constants.SEARCH_SCOPE_TYPE_ALL);
		if(!lastExecDateStr.equals("")){
			assetSc.setDeleteDateFrom(Function.getDateFromStr(lastExecDateStr.substring(0, 10), "yyyy/MM/dd"));
		}
		assetSc.setDeleteDateTo(Function.getDateFromStr(batchInfo.getValue2().substring(0, 10), "yyyy/MM/dd"));
		List<AssetSR> deleteAssetList = assetSession.searchAsset(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, assetSc, false);


		// リストマージ
		assetList.addAll(deleteAssetList);

		//////////////////////////////////// eGuard同期
		int execCount = 0;
		for(int i = 0; i < assetList.size(); i++){
			try{
				AssetSR assetData = (AssetSR)assetList.get(i);
				Asset obj = assetSession.getAsset(assetData.getAssetId(), false);

				int syncFlag = childBatchSession.eGuardSyncChild(obj);
				if(syncFlag == 1) { // 追加
					execCount++;
					adminMailText.append("　" + "追加　　　　" + obj.getAssetId() + "\n");
					Logging.info("　" + "追加　　　　" + obj.getAssetId() + "\n");
				} else if(syncFlag == 2) { // 削除
					execCount++;
					adminMailText.append("　" + "削除　　　　" + obj.getAssetId() + "\n");
					Logging.info("　" + "削除　　　　" + obj.getAssetId() + "\n");
				}
			} catch(Exception e){
				Logging.error("　" + "　　　　　　" + assetList.get(i).getAssetId() + "(※エラー発生)\n", e);
				adminMailText.append("　" + "　　　　　　" + assetList.get(i).getAssetId() + "(※エラー発生)\n");
			}
		}

		// 終了日セット
		batchInfo.setValue3(dtf.format(new Date()));

		StringBuffer adminMailHeader = new StringBuffer();
		adminMailHeader.append("　実行時間：" + batchInfo.getValue2() + " - " + batchInfo.getValue3() + "\n");
		adminMailHeader.append("　処理件数：" + execCount + "件\n\n");
		adminMailHeader.append("　追加/削除　 情報機器管理番号\n");
		adminMailHeader.append("　------------------------------------\n");
		adminMailText.insert(0, adminMailHeader.toString());

		// 実行内容履歴・実行内容セット
		setBatchInfoHist(batchInfo, adminMailText.toString());

		// バッチ処理ステータス更新
		masterSession.updateCodeName(Constants.STAFF_CODE_SYSTEM, batchInfo);

		return adminMailText.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#ppfsImport(jp.co.ctcg.easset.dto.CodeName)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String ppfsImport(CodeName batchInfo) {
		StringBuffer adminMailText = new StringBuffer();

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		batchInfo.setValue2(dtf.format(new Date())); // 開始時刻セット

		//////////////////////////////////// 対象処理会社
		List<CodeName> companyListAll = masterSession.getCodeNameList(Constants.CATEGORY_CODE_USE_COMPANY, null, null, null);
		List<CodeName> companyList = new ArrayList<CodeName>();
		for(int i = 0; i < companyListAll.size(); i++) {
			CodeName cName = companyListAll.get(i);
			if(cName.getValue7() != null) { // ProPlus会社コード設定
				companyList.add(cName);
			}
		}

		//////////////////////////////////// eGuard同期
		// int execCount = 0;
		for(int i = 0; i < companyList.size(); i++){
			CodeName cName = companyList.get(i);
			for(int j = 0; j < 2; j++) {
				String dataTypeName = (j == 0 ? "固定資産" : "リース・レンタル");
				try{
					// execCount++;
					if(j == 0) {
						childBatchSession.ppfsImportChild(cName.getInternalCode(), Constants.PPFS_IMPORT_DATA_TYPE_FLD);
					} else {
						childBatchSession.ppfsImportChild(cName.getInternalCode(), Constants.PPFS_IMPORT_DATA_TYPE_LLD);
					}
					adminMailText.append("　" + cName.getValue1() + "　　　　　" + dataTypeName + "\n");
					Logging.info("　" + cName.getValue1() + "　　　　　" + dataTypeName + "\n");

				} catch(AppException e){
					Logging.warning("　" + cName.getValue1() + "　　　　　" + dataTypeName + "(※既に実行中のため未実行)\n", e);
					adminMailText.append("　" + cName.getValue1() + "　　　　　" + dataTypeName + "(※既に実行中のため未実行)\n");
				} catch(Exception e){
					Logging.error("　" + cName.getValue1() + "　　　　　" + dataTypeName + "(※エラー発生)\n", e);
					adminMailText.append("　" + cName.getValue1() + "　　　　　" + dataTypeName + "(※エラー発生)\n");
				}
			}
		}

		// 終了日セット
		batchInfo.setValue3(dtf.format(new Date()));

		StringBuffer adminMailHeader = new StringBuffer();
		adminMailHeader.append("　起動時間：" + batchInfo.getValue2() + " - " + batchInfo.getValue3() + "\n");
		adminMailHeader.append("　　　　　　※　処理時間は各会社の「ProPlusデータ取込」実行ログを参照\n");
		adminMailHeader.append("　取込起動会社　　 取込対象\n");
		adminMailHeader.append("　------------------------------------\n");

		adminMailText.insert(0, adminMailHeader.toString());

		// 実行内容履歴・実行内容セット
		setBatchInfoHist(batchInfo, adminMailText.toString());

		// バッチ処理ステータス更新
		masterSession.updateCodeName(Constants.STAFF_CODE_SYSTEM, batchInfo);

		return adminMailText.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#eGuardSyncChild(jp.co.ctcg.easset.dto.asset.Asset)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public int eGuardSyncChild(Asset obj) {
		return assetSession.syncEGuardAsset(obj);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#ppfsImportChild(java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void ppfsImportChild(String companyCode, String dataType) throws AppException {
		ppfsImportSession.callPpfsImport(companyCode, dataType, Constants.STAFF_CODE_SYSTEM, Constants.FLAG_NO);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#apEndTanDelete(jp.co.ctcg.easset.dto.CodeName)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String apEndTanDelete(CodeName batchInfo) throws AppException {

		HashMap<String, String> mailMap = new HashMap<String, String>();	// メール送信情報
		StringBuffer adminMailText = new StringBuffer();

		// 開始時刻セット
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		batchInfo.setValue2(dtf.format(new Date()));

		// 対象の申請一覧を取得
		ApEndTanSC apEndTanSc = new ApEndTanSC();
		apEndTanSc.setApStatus(Constants.AP_STATUS_END_TAN_APPROVE);
		apEndTanSc.setDisposeDateTo(Function.getDateFromStr(batchInfo.getValue2().substring(0, 10), "yyyy/MM/dd"));
		apEndTanSc.setDeleteExecFlag(Constants.FLAG_NO);

		List<ApEndTanSR> apEndTanList = apEndTanSession.searchApEndTan(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, apEndTanSc);

		if(apEndTanList != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date deleteDate = Function.getDateFromStr(batchInfo.getValue2().substring(0, 10), "yyyy/MM/dd");

			for(int i = 0; i < apEndTanList.size(); i++){
				ApEndTanSR aetLine = (ApEndTanSR)apEndTanList.get(i);

				String applicationId = Function.nvl(aetLine.getApplicationId(), "         ");
				String deleteReason = "固定資産除売却日到来に伴い自動抹消（除売却申請書番号：" + applicationId + "）";

				ApEndTan aetData = apEndTanSession.getApEndTan(applicationId);

				String apStaffCode = aetData.getApStaffCode();
				String apStaffName = aetData.getApStaffName();
				String disposeDateStr = df.format(aetData.getDisposeDate());

				// 抹消処理
				// 資産(機器)明細
				List<ApEndTanLineAst> aetAstList = aetData.getApEndTanLineAstList();
				if(aetAstList != null){
					ArrayList<Asset> deleteAstList = new ArrayList<Asset>(); // 抹消対象
					for(int j = 0; j < aetAstList.size(); j++){
						ApEndTanLineAst aetAstLine = (ApEndTanLineAst)aetAstList.get(j);
						Asset assetData = assetSession.getAsset(aetAstLine.getAssetId(), false);
						if(assetData != null){
							AssetSR assetSRData = new AssetSR();
							assetSRData.setAssetId(assetData.getAssetId());
							assetSRData.setVersion(assetData.getVersion());
							deleteAstList.add(assetSRData);

							String assetId = Function.nvl(assetData.getAssetId(), "        ");
							String holStaffCode = assetData.getHolStaffCode();
							String deleteInfo = applicationId + "　　"
							                  + assetId + "　　　"
							                  + disposeDateStr + "　　　"
							                  + apStaffCode + "　"
							                  + apStaffName;
							mailMap = setMailMap(mailMap, holStaffCode, deleteInfo);
							mailMap = setMailMap(mailMap, apStaffCode, deleteInfo);
							mailMap = setMailMap(mailMap, Constants.STAFF_CODE_SYSTEM, "　" + deleteInfo);
						}
					}
					assetSession.deleteAssetLogical(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, deleteDate, deleteReason,  deleteAstList);
				}

				// ライセンス明細
				List<ApEndTanLineLic> aetLicList = aetData.getApEndTanLineLicList();
				if(aetLicList != null){
					ArrayList<License> deleteLicList = new ArrayList<License>(); // 抹消対象
					for(int k = 0; k < aetLicList.size(); k++){
						ApEndTanLineLic aetLicLine = (ApEndTanLineLic)aetLicList.get(k);
						License licenseData = licenseSession.getLicense(aetLicLine.getLicenseId(), false);
						if(licenseData != null){
							LicenseSR licenseSRData = new LicenseSR();
							licenseSRData.setLicenseId(licenseData.getLicenseId());
							licenseSRData.setVersion(licenseData.getVersion());
							deleteLicList.add(licenseSRData);

							String licensetId = Function.nvl(licenseData.getLicenseId(), "        ");
							String holStaffCode = licenseData.getHolStaffCode();
							String deleteInfo = applicationId + "　　"
							                  + licensetId + "　　　"
							                  + disposeDateStr + "　　　"
							                  + apStaffCode + "　"
							                  + apStaffName;
							mailMap = setMailMap(mailMap, holStaffCode, deleteInfo);
							mailMap = setMailMap(mailMap, apStaffCode, deleteInfo);
							mailMap = setMailMap(mailMap, Constants.STAFF_CODE_SYSTEM, "　" + deleteInfo);
						}
					}
					licenseSession.deleteLicenseLogical(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, deleteDate, deleteReason,  deleteLicList);
				}
				apEndTanSession.deleteExecApEndTan(applicationId, Constants.STAFF_CODE_SYSTEM);
			}
		}

		// メールテンプレート取得
		CodeName mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_BATCH_MAIL_TEMPLATE, BATCH_TYPE_AP_END_TAN_DELETE, null, null);
		String from = mailTemplate.getValue2();
		String subject = mailTemplate.getValue4();
		String bodyTemplate = mailTemplate.getValue5(); // 本文

		for(Iterator<?> iter = mailMap.entrySet().iterator(); iter.hasNext();) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> row = (Map.Entry<String, String>) iter.next();
			String staffCode = (String)row.getKey();
			String deleteInfo = (String)row.getValue();

			if(!staffCode.equals(Constants.STAFF_CODE_SYSTEM)){
				try{
					// メール送信先社員情報取得
					User staffData = masterSession.getStaffValid(null, staffCode);

					// 本文のメール予約後置換
					String body = bodyTemplate.replaceAll(MAIL_TEMPLATE_VAR_TO_NAME, staffData.getName());
					body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_END_TAN, deleteInfo);

					sendMailSession.sendMail(from, staffData.getMailAddress(), null, subject, body);

					Logging.info("　" + staffCode + ":　" + deleteInfo + "\n");

				} catch(Exception e){
					Logging.error("　" + staffCode + ":　(※メール送信エラー発生)", e);
					adminMailText.append("\n　" + staffCode + ":　(※メール送信エラー発生)");
				}

			}
		}

		// 終了時刻セット
		batchInfo.setValue3(dtf.format(new Date()));

		StringBuffer adminMailHeader = new StringBuffer();
		adminMailHeader.append("　実行時間：" + batchInfo.getValue2() + " - " + batchInfo.getValue3() + "\n");
		int deleteCount = 0;
		String adminDeleteInfo  = mailMap.get(Constants.STAFF_CODE_SYSTEM);
		if(adminDeleteInfo != null){
			String[] deleteArry = adminDeleteInfo.split("\n");
			deleteCount = deleteArry.length;
		}
		adminMailHeader.append("　処理件数：" + deleteCount + "件\n\n");
		if(deleteCount > 0){
			adminMailHeader.append("　申請書番号　　管理番号　　廃棄・返却日　　　　申請者\n");
			adminMailHeader.append("　--------------------------------------------------------------------\n");
		}
		if(adminDeleteInfo != null){
			adminMailHeader.append(adminDeleteInfo + "\n");
		}
		adminMailText.insert(0, adminMailHeader.toString());

		// 実行内容履歴・実行内容セット
		setBatchInfoHist(batchInfo, adminMailText.toString());

		// バッチ処理ステータス更新
		masterSession.updateCodeName(Constants.STAFF_CODE_SYSTEM, batchInfo);

/*
除売却申請の廃棄・返却日到来に伴う自動抹消に関するお知らせ




崔　平根　様

除売却申請による情報機器等・ライセンスの廃棄・返却日が過ぎましたので、
自動的に抹消されましたことをお知らせいたします。

申請書番号　　管理番号　　廃棄・返却日　　　　申請者
--------------------------------------------------------------------
FS1200001　　S1200300　　　2012/11/21　　　1G7092　崔　平根
FS1200001　　S1200301　　　2012/11/21　　　1G7092　崔　平根
FS1200001　　H1200300　　　2012/11/21　　　1G7092　崔　平根
FS1200002　　H1200301　　　2012/11/21　　　1G7092　崔　平根
FS1200003　　H1200302　　　2012/11/21　　　1G7092　崔　平根
--------------------------------------------------------------------

以上、よろしくお願いいたします。
*/
		return adminMailText.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#apEndLeDelete(jp.co.ctcg.easset.dto.CodeName)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String apEndLeDelete(CodeName batchInfo) throws AppException {

		HashMap<String, String> mailMap = new HashMap<String, String>();	// メール送信情報
		StringBuffer adminMailText = new StringBuffer();

		// 開始時刻セット
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		batchInfo.setValue2(dtf.format(new Date()));

		// 抹消対象の申請一覧を取得
		List<ApEndLeSR> apEndLeList = apEndLeSession.getReturnApEndLeList(Function.getDateFromStr(batchInfo.getValue2().substring(0, 10), "yyyy/MM/dd"));

		if(apEndLeList != null){
			SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
			Date deleteDate = Function.getDateFromStr(batchInfo.getValue2().substring(0, 10), "yyyy/MM/dd");

			for(int i = 0; i < apEndLeList.size(); i++){
				ApEndLeSR aelLine = (ApEndLeSR)apEndLeList.get(i);

				String applicationId = Function.nvl(aelLine.getApplicationId(), "         ");
				String apEndLeLineType = Function.nvl(aelLine.getApEndLeLineType(), "");
				String deleteExecFlag = Function.nvl(aelLine.getDeleteExecFlag(), "0");
				String astType = Function.nvl(aelLine.getAstType(), "");
				char appIdC1 = applicationId.charAt(0);

				// 物件明細申請区分が買取(情報機器)、返却（抹消フラグが'1'）
				if((apEndLeLineType.equals(Constants.AP_END_LE_LINE_TYPE_BUY) &&
					astType.equals("ASSET")) ||
				   (apEndLeLineType.equals(Constants.AP_END_LE_LINE_TYPE_RETURN) &&
				    deleteExecFlag.equals("1"))){

					String astId = Function.nvl(aelLine.getAstId(), "        ");
					boolean execFlag = false;

					// 買取(情報機器)、返却（抹消フラグが'1'）
					if(apEndLeLineType.equals(Constants.AP_END_LE_LINE_TYPE_BUY)){
						Long purchaseAmount = aelLine.getPurchaseAmount();
						long pAmount = 0;
						if(purchaseAmount != null){
							pAmount = purchaseAmount.longValue();
						}

						// 買い取り金額により備品、固定資産に分かれる
						Asset assetData = assetSession.getAsset(astId, false);
						if(assetData != null){

							String value5 = null;
							if(appIdC1 == 'L'){
								if(pAmount >= Constants.ASSET_AMOUNT_BORDER) {
									value5 = "1";
								} else {
									value5 = "2";
								}
							}else{
									if(pAmount >= Constants.ASSET_AMOUNT_BORDER) {
										value5 = "3";
									} else {
										value5 = "4";
								}
							}

							ArrayList<String> param = new ArrayList<String>();
							param.add(null); // value1
							param.add(null); // value2
							param.add(null); // value3
							param.add(null); // value4
							param.add(value5); // value5
							List<CodeName> CodeNameList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_ASSET_TYPE, null, null, param);

							if(CodeNameList != null && CodeNameList.size() > 0) {
								CodeName codeNameData = CodeNameList.get(0);
								assetData.setAstGetType(codeNameData.getValue2());
								assetData.setAstAssetType(codeNameData.getInternalCode());
								// 情報機器等更新(取得形態、資産区分)
								assetSession.updateAsset(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, assetData, false, null);
								execFlag = true;
							}
						}
					}
					// 返却（抹消フラグが'1'）
					else{

						String deleteReason;

						if(appIdC1 == 'L'){
							deleteReason = "返却日到来に伴い自動抹消（リース申請書番号：" + applicationId + "）";
						}else{
							deleteReason = "返却日到来に伴い自動抹消（レンタル申請書番号：" + applicationId + "）";
						}

						if(astType.equals("ASSET")){
							Asset assetData = assetSession.getAsset(astId, false);
							if(assetData != null){
								AssetSR assetSRData = new AssetSR();
								assetSRData.setAssetId(assetData.getAssetId());
								assetSRData.setVersion(assetData.getVersion());

								ArrayList<Asset> deleteAstList = new ArrayList<Asset>(); // 情報機器等抹消対象
								deleteAstList.add(assetSRData);

								// 情報機器等抹消処理
								assetSession.deleteAssetLogical(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, deleteDate, deleteReason,  deleteAstList);
								execFlag = true;
							}
						}
						else{
							License licenseData = licenseSession.getLicense(astId, false);
							if(licenseData != null){
								LicenseSR licenseSRData = new LicenseSR();
								licenseSRData.setLicenseId(licenseData.getLicenseId());
								licenseSRData.setVersion(licenseData.getVersion());

								ArrayList<License> deleteLicList = new ArrayList<License>(); // ライセンス抹消対象
								deleteLicList.add(licenseSRData);

								// ライセンス抹消処理
								licenseSession.deleteLicenseLogical(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, deleteDate, deleteReason,  deleteLicList);
								execFlag = true;
							}
						}
					}

					if(execFlag){
						String apEndLeLineTypeName = Function.nvl(aelLine.getApEndLeLineTypeName(), "　　");
						String returnDateStr = df.format(aelLine.getReturnDate());
						String apStaffCode = aelLine.getApStaffCode();
						String apStaffName = aelLine.getApStaffName();
						String holStaffCode = aelLine.getHolStaffCode();

						String deleteInfo = applicationId + "　　"
										  + apEndLeLineTypeName +"　　"
										  + astId + "　　"
										  + returnDateStr + "　　"
										  + apStaffCode + "　"
										  + apStaffName;

						mailMap = setMailMap(mailMap, holStaffCode, deleteInfo);
						mailMap = setMailMap(mailMap, apStaffCode, deleteInfo);
						mailMap = setMailMap(mailMap, Constants.STAFF_CODE_SYSTEM, "　" + deleteInfo);
					}
				}

				ApEndLe apEndLe = apEndLeSession.getApEndLe(applicationId, false);
				if(Function.nvl(apEndLe.getDeleteExecFlag(), "0").equals("0")){
					apEndLeSession.deleteExecApEndLe(applicationId, Constants.STAFF_CODE_SYSTEM);
				}
			}
		}

		// メールテンプレート取得
		CodeName mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_BATCH_MAIL_TEMPLATE, BATCH_TYPE_AP_END_LE_DELETE, null, null);
		String from = mailTemplate.getValue2();
		String subject = mailTemplate.getValue4();
		String bodyTemplate = mailTemplate.getValue5(); // 本文

		for(Iterator<?> iter = mailMap.entrySet().iterator(); iter.hasNext();) {
			@SuppressWarnings("unchecked")
			Map.Entry<String, String> row = (Map.Entry<String, String>) iter.next();
			String staffCode = (String)row.getKey();
			String deleteInfo = (String)row.getValue();

			if(!staffCode.equals(Constants.STAFF_CODE_SYSTEM)){
				try{
					// メール送信先社員情報取得
					User staffData = masterSession.getStaffValid(null, staffCode);

					// 本文のメール予約後置換
					String body = bodyTemplate.replaceAll(MAIL_TEMPLATE_VAR_TO_NAME, staffData.getName());
					body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_END_TAN, deleteInfo);

					sendMailSession.sendMail(from, staffData.getMailAddress(), null, subject, body);

					Logging.info("　" + staffCode + ":　" + deleteInfo + "\n");

				} catch(Exception e){
					Logging.error("　" + staffCode + ":　(※メール送信エラー発生)", e);
					adminMailText.append("\n　" + staffCode + ":　(※メール送信エラー発生)");
				}

			}
		}

		// 終了時刻セット
		batchInfo.setValue3(dtf.format(new Date()));

		StringBuffer adminMailHeader = new StringBuffer();
		adminMailHeader.append("　実行時間：" + batchInfo.getValue2() + " - " + batchInfo.getValue3() + "\n");
		int deleteCount = 0;
		String adminDeleteInfo  = mailMap.get(Constants.STAFF_CODE_SYSTEM);
		if(adminDeleteInfo != null){
			String[] deleteArry = adminDeleteInfo.split("\n");
			deleteCount = deleteArry.length;
		}
		adminMailHeader.append("　処理件数：" + deleteCount + "件\n\n");
		if(deleteCount > 0){
			adminMailHeader.append("　申請書番号　　区分　　管理番号　　返却日　　　　　申請者\n");
			adminMailHeader.append("　--------------------------------------------------------------------\n");
		}
		if(adminDeleteInfo != null){
			adminMailHeader.append(adminDeleteInfo + "\n");
		}
		adminMailText.insert(0, adminMailHeader.toString());

		// 実行内容履歴・実行内容セット
		setBatchInfoHist(batchInfo, adminMailText.toString());

		// バッチ処理ステータス更新
		masterSession.updateCodeName(Constants.STAFF_CODE_SYSTEM, batchInfo);

/*
リース満了・解約申請の返却日到来に伴う自動抹消に関するお知らせ




崔　平根　様

リース満了・解約申請による情報機器等・ライセンスの返却日が過ぎましたので、
自動的に抹消されましたことをお知らせいたします。

申請書番号　　区分　　管理番号　　返却日　　　　　申請者
-----------------------------------------------------------------------
LH1200001　　買取　　S1200300　　2012/11/21　　1G7092　崔　平根
LH1200001　　返却　　S1200301　　2012/11/21　　1G7092　崔　平根
LH1200001　　返却　　H1200300　　2012/11/21　　1G7092　崔　平根
LH1200002　　返却　　H1200301　　2012/11/21　　1G7092　崔　平根
LH1200003　　返却　　H1200302　　2012/11/21　　1G7092　崔　平根
-----------------------------------------------------------------------

以上、よろしくお願いいたします。
*/
		return adminMailText.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#apEndReAssetTypeChange(jp.co.ctcg.easset.dto.CodeName)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String apEndReAssetTypeChange(CodeName batchInfo) throws AppException {

		StringBuffer adminMailText = new StringBuffer();
		int updateCount = 0;

		// 開始時刻セット
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		batchInfo.setValue2(dtf.format(new Date()));

		// 買取対象の申請一覧を取得
		List<ApEndReSR> apEndReList = apEndReSession.getReturnApEndReList(Function.getDateFromStr(batchInfo.getValue2().substring(0, 10), "yyyy/MM/dd"));

		if(apEndReList != null){
			for(int i = 0; i < apEndReList.size(); i++){
				ApEndReSR aerLine = (ApEndReSR)apEndReList.get(i);

				String applicationId = Function.nvl(aerLine.getApplicationId(), "         ");
				SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				String returnDateStr = df.format(aerLine.getReturnDate());

				Long purchaseAmount = aerLine.getPurchaseAmount();
				long pAmount = 0;
				if(purchaseAmount != null){
					pAmount = purchaseAmount.longValue();
				}
				String value6 = null;
				if(pAmount >= Constants.ASSET_AMOUNT_BORDER) {
					value6 = "3";
				} else {
					value6 = "4";
				}

				ArrayList<String> param = new ArrayList<String>();
				param.add(null); // value1
				param.add(null); // value2
				param.add(null); // value3
				param.add(null); // value4
				param.add(null); // value5
				param.add(value6); // value6
				List<CodeName> CodeNameList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_ASSET_TYPE, null, null, param);

				if(CodeNameList != null && CodeNameList.size() > 0) {
					String assetId = Function.nvl(aerLine.getAstId(), "");
					Asset assetData = assetSession.getAsset(assetId, false);
					if(assetData != null){
						CodeName codeNameData = CodeNameList.get(0);
						assetData.setAstGetType(codeNameData.getValue2());
						assetData.setAstAssetType(codeNameData.getInternalCode());
						// 情報機器等更新(取得形態、資産区分)
						assetSession.updateAsset(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, assetData, false, null);

						// 管理者向けメール本文
						adminMailText.append("　" + applicationId + "　　" + returnDateStr + "　　" + assetId + "　　" + Function.nvl(codeNameData.getValue1(), "") + "\n");
						Logging.info("　" + applicationId + "　　" + returnDateStr + "　　" + assetId + "　　" + Function.nvl(codeNameData.getValue1(), "") + "\n");
						updateCount++;
					}
				}
			}
		}

		// 終了時刻セット
		batchInfo.setValue3(dtf.format(new Date()));

		StringBuffer adminMailHeader = new StringBuffer();
		adminMailHeader.append("　実行時間：" + batchInfo.getValue2() + " - " + batchInfo.getValue3() + "\n");
		adminMailHeader.append("　処理件数：" + updateCount+ "件\n\n");
		adminMailHeader.append("　　申請書番号　　買取日　　　　管理番号　　資産区分\n");
		adminMailHeader.append("　--------------------------------------------------------------------\n");

		adminMailText.insert(0, adminMailHeader.toString());

		// 実行内容履歴・実行内容セット
		setBatchInfoHist(batchInfo, adminMailText.toString());

		// バッチ処理ステータス更新
		masterSession.updateCodeName(Constants.STAFF_CODE_SYSTEM, batchInfo);

		return adminMailText.toString();
	}


	// ファイル名
	private static  String CompanyMasterFileName = "eAssetCompanyMaster";
	private static  String DivisionMasterFileName = "eAssetDivisionMaster";
	private static  String NewDivisionMasterFileName = "eAssetNewDivisionMaster";
	private static  String UserCompanyFileName = "eAssetUserCompany";
	private static  String UserFileName = "eAssetUser";
	// ファイル拡張子
	private static  String GzFileExtension = ".tar.gz";
	private static  String CsvFileExtension = ".csv";
	private static  String TarFileExtension = ".tar";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#knightMasterImport(jp.co.ctcg.easset.dto.CodeName)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String knightMasterImport(CodeName batchInfo) throws AppException {

		StringBuffer adminMailText = new StringBuffer();

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		batchInfo.setValue2(dtf.format(new Date())); // 開始時刻セット

		CodeName pathInfo = masterSession.getCodeName(Constants.CATEGORY_CODE_BATCH_KNIGHT_PATH, "0", null, null);
		String filePath = pathInfo.getValue1();
		Logging.info("filePath:"+filePath);

		// 会社マスタ
		adminMailText.append(importCompanyMaster(filePath));

		// 部署マスタ
		adminMailText.append("　------------------------------------\n");
		adminMailText.append(importDivisionMaster(filePath));

		// 組織マスタ
		adminMailText.append("　------------------------------------\n");
		adminMailText.append(importNewDivisionMaster(filePath));

		// ユーザー所属マスタ
		adminMailText.append("　------------------------------------\n");
		adminMailText.append(importUserCompanyMaster(filePath));

		// ユーザーマスタ
		adminMailText.append("　------------------------------------\n");
		adminMailText.append(importUserMaster(filePath));

		// 終了日セット
		batchInfo.setValue3(dtf.format(new Date()));

		StringBuffer adminMailHeader = new StringBuffer();
		adminMailHeader.append("　起動時間：" + batchInfo.getValue2() + " - " + batchInfo.getValue3() + "\n");
		adminMailHeader.append("　------------------------------------\n");

		adminMailText.insert(0, adminMailHeader.toString());

		// 実行内容履歴・実行内容セット
		setBatchInfoHist(batchInfo, adminMailText.toString());

		// バッチ処理ステータス更新
		masterSession.updateCodeName(Constants.STAFF_CODE_SYSTEM, batchInfo);

		return adminMailText.toString();
	}

	/**
	 * 会社マスタファイル取込
	 */
	private String importCompanyMaster(String filePath){
		String returnStr = "■　会社マスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

		try{
			// zipファイル解凍
			String fileName = unZipCsvFile(filePath, CompanyMasterFileName);
			FileInputStream fileReader = new FileInputStream(fileName);
			InputStreamReader inReader = new InputStreamReader(fileReader, "Windows-31J");
			BufferedReader bufferedReader = new BufferedReader(inReader);

			List<CompanyMasterData> companyList = new ArrayList<CompanyMasterData>();
			String inString;

			// 1行ずつ読み込み
			while ( (inString = bufferedReader.readLine()) != null) {
				row++;
				int columnCount = 0;
				int sIndex = 0;
				int eIndex = 0;
				CompanyMasterData companyMasterData = new CompanyMasterData();
				for(int i = 0; i < 27; i++){
					eIndex = inString.indexOf(",\"", sIndex);
					if(eIndex != -1){
						if(eIndex - sIndex >= 2){
							String setValue = inString.substring(sIndex + 1, eIndex - 1);
							switch (columnCount) {
								case 0:
									companyMasterData.setOfficeCode(setValue);
									break;
								case 1:
									companyMasterData.setCompanyCode(setValue);
									break;
								case 2:
									companyMasterData.setOfficeNameKanji(setValue);
									break;
								case 3:
									companyMasterData.setOfficeNameKana(setValue);
									break;
								case 4:
									companyMasterData.setOfficeNameEng(setValue);
									break;
								case 5:
									companyMasterData.setCompanyNameKanji(setValue);
									break;
								case 6:
									companyMasterData.setCompanyPopKanji(setValue);
									break;
								case 7:
									companyMasterData.setCompanyNameKana(setValue);
									break;
								case 8:
									companyMasterData.setCompanyPopKana(setValue);
									break;
								case 9:
									companyMasterData.setCompanyNameEng(setValue);
									break;
								case 10:
									companyMasterData.setCompanyPopEng(setValue);
									break;
								case 11:
									companyMasterData.setZipCode(setValue);
									break;
								case 12:
									companyMasterData.setAddressKanji1(setValue);
									break;
								case 13:
									companyMasterData.setAddressKanji2(setValue);
									break;
								case 14:
									companyMasterData.setAddressKanji3(setValue);
									break;
								case 15:
									companyMasterData.setAddressKana1(setValue);
									break;
								case 16:
									companyMasterData.setAddressKana2(setValue);
									break;
								case 17:
									companyMasterData.setAddressKana3(setValue);
									break;
								case 18:
									companyMasterData.setAddressEng1(setValue);
									break;
								case 19:
									companyMasterData.setAddressEng2(setValue);
									break;
								case 20:
									companyMasterData.setAddressEng3(setValue);
									break;
								case 21:
									companyMasterData.setTelRepresentative(setValue);
									break;
								case 22:
									companyMasterData.setRepresentativeKanji(setValue);
									break;
								case 23:
									companyMasterData.setRepresentativeKana(setValue);
									break;
								case 24:
									companyMasterData.setRepresentativeEng(setValue);
									break;
								case 25:
									companyMasterData.setUseFlg(setValue);
									break;
							}
						}
						columnCount++;
						sIndex = eIndex + 1;
					}
					else{
						//	最終列
						if(i == 26 ){
							companyMasterData.setEditDate(Function.getDateFromStr(inString.substring(sIndex + 1, inString.length() - 1), "yyyy-MM-dd HH:mm:ss"));
						}
					}
				}
				companyList.add(companyMasterData);
			}
			bufferedReader.close();
	        fileReader.close();
			deleteImportFile(filePath, CompanyMasterFileName);

			masterSession.insertCompanyMaster(companyList);

		} catch (FileNotFoundException ex){
			errorMsg = "取込対象のファイルがありません。\n";
		}catch(Exception ex){
			errorMsg = "会社マスタの取込処理に失敗しました。\n" + ex + "\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			returnStr += "　取込件数：" + row + "件\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	/**
	 * 部署マスタファイル取込
	 */
	public String importDivisionMaster(String filePath){
		String returnStr = "■　部署マスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

		try{
			// zipファイル解凍
			String fileName = unZipCsvFile(filePath, DivisionMasterFileName);
			FileInputStream fileReader = new FileInputStream(fileName);
			InputStreamReader inReader = new InputStreamReader(fileReader, "Windows-31J");
			BufferedReader bufferedReader = new BufferedReader(inReader);
			String inString;
			List<DivisionMasterData> divisionList = new ArrayList<DivisionMasterData>();

			// 1行ずつ読み込み
			while ( (inString = bufferedReader.readLine()) != null) {
				row++;
				int columnCount = 0;
				int sIndex = 0;
				int eIndex = 0;
				DivisionMasterData divisionMasterData = new DivisionMasterData();
				for(int i = 0; i < 7; i++){
					eIndex = inString.indexOf(",\"", sIndex);
					if(eIndex != -1){
						if(eIndex - sIndex >= 2){
							String setValue = inString.substring(sIndex + 1, eIndex - 1);
							switch (columnCount) {
								case 0:
									divisionMasterData.setCompanyCode(setValue);
									break;
								case 1:
									divisionMasterData.setDivisionCode(setValue);
									break;
								case 2:
									divisionMasterData.setDivisionKanji(setValue);
									break;
								case 3:
									divisionMasterData.setDivisionKana(setValue);
									break;
								case 4:
									divisionMasterData.setDivisionEng(setValue);
									break;
								case 5:
									divisionMasterData.setDisporder(Integer.valueOf(setValue));
									break;

							}
						}
						columnCount++;
						sIndex = eIndex + 1;
					}
					else{
						//	最終列
						if(i == 6 ){
							divisionMasterData.setEditDate(Function.getDateFromStr(inString.substring(sIndex + 1, inString.length() - 1), "yyyy-MM-dd HH:mm:ss"));
						}
					}
				}
				divisionList.add(divisionMasterData);
			}
			bufferedReader.close();
	        fileReader.close();
			deleteImportFile(filePath, DivisionMasterFileName);

			masterSession.insertDivisionMaster(divisionList);

		} catch (FileNotFoundException ex){
			errorMsg = "取込対象のファイルがありません。\n";
		}catch(Exception ex){
			errorMsg = "部署マスタの取込処理に失敗しました。\n" + ex + "\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			returnStr += "　取込件数：" + row + "件\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	/**
	 * 組織マスタファイル取込
	 */
	public String importNewDivisionMaster(String filePath){
		String returnStr = "■　組織マスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

		try{
			// zipファイル解凍
			String fileName = unZipCsvFile(filePath, NewDivisionMasterFileName);
			FileInputStream fileReader = new FileInputStream(fileName);
			InputStreamReader inReader = new InputStreamReader(fileReader, "Windows-31J");
			BufferedReader bufferedReader = new BufferedReader(inReader);
			String inString;
			List<NewDivisionMasterData> newDivisionList = new ArrayList<NewDivisionMasterData>();

			// 1行ずつ読み込み
			while ( (inString = bufferedReader.readLine()) != null) {
				row++;
				int columnCount = 0;
				int sIndex = 0;
				int eIndex = 0;
				NewDivisionMasterData newDivisionMasterData = new NewDivisionMasterData();
				for(int i = 0; i < 5; i++){
					eIndex = inString.indexOf(",\"", sIndex);
					if(eIndex != -1){
						if(eIndex - sIndex >= 2){
							String setValue = inString.substring(sIndex + 1, eIndex - 1);
							switch (columnCount) {
								case 0:
									newDivisionMasterData.setCompanyCode(setValue);
									break;
								case 1:
									newDivisionMasterData.setDivisionCode(setValue);
									break;
								case 2:
									newDivisionMasterData.setParentDivisionCode(setValue);
									break;
								case 3:
									newDivisionMasterData.setDivisionKanji(setValue);
									break;
							}
						}
						columnCount++;
						sIndex = eIndex + 1;
					}
					else{
						//	最終列
						if(i == 4 ){
							newDivisionMasterData.setViewFlg(inString.substring(sIndex + 1, inString.length() - 1));
						}
					}
				}
				newDivisionList.add(newDivisionMasterData);
			}
			bufferedReader.close();
	        fileReader.close();
			deleteImportFile(filePath, NewDivisionMasterFileName);

			masterSession.insertNewDivisionMaster(newDivisionList);

		} catch (FileNotFoundException ex){
			errorMsg = "取込対象のファイルがありません。\n";
		}catch(Exception ex){
			errorMsg = "組織マスタの取込処理に失敗しました。\n" + ex + "\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			returnStr += "　取込件数：" + row + "件\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	/**
	 * ユーザー所属マスタファイル取込
	 */
	public String importUserCompanyMaster(String filePath){
		String returnStr = "■　ユーザー所属マスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

		try{
			// zipファイル解凍
			String fileName = unZipCsvFile(filePath, UserCompanyFileName);
			FileInputStream fileReader = new FileInputStream(fileName);
			InputStreamReader inReader = new InputStreamReader(fileReader, "Windows-31J");
			BufferedReader bufferedReader = new BufferedReader(inReader);
			String inString;
			List<UserCompanyMasterData> userCompanyList = new ArrayList<UserCompanyMasterData>();

			// 1行ずつ読み込み
			while ( (inString = bufferedReader.readLine()) != null) {
				row++;
				int columnCount = 0;
				int sIndex = 0;
				int eIndex = 0;
				UserCompanyMasterData userCompanyMasterData = new UserCompanyMasterData();
				for(int i = 0; i < 19; i++){
					eIndex = inString.indexOf(",\"", sIndex);
					if(eIndex != -1){
						if(eIndex - sIndex >= 2){
							String setValue = inString.substring(sIndex + 1, eIndex - 1);
							switch (columnCount) {
								case 0:
									userCompanyMasterData.setUserId(Function.getLong(setValue));
									break;
								case 1:
									userCompanyMasterData.setStartDate(setValue);
									break;
								case 2:
									userCompanyMasterData.setOfficeCode(setValue);
									break;
								case 3:
									userCompanyMasterData.setCompanyCode(setValue);
									break;
								case 4:
									userCompanyMasterData.setDivisionCode(setValue);
									break;
								case 5:
									userCompanyMasterData.setTitleCode(setValue);
									break;
								case 6:
									userCompanyMasterData.setTel1(setValue);
									break;
								case 7:
									userCompanyMasterData.setTel2(setValue);
									break;
								case 8:
									userCompanyMasterData.setFax(setValue);
									break;
								case 9:
									userCompanyMasterData.setExtension(setValue);
									break;
								case 10:
									userCompanyMasterData.setDomainId(setValue);
									break;
								case 11:
									userCompanyMasterData.setTransferCompany(setValue);
									break;
								case 12:
									userCompanyMasterData.setPostCode(setValue);
									break;
								case 13:
									userCompanyMasterData.setJobtypeCode(setValue);
									break;
								case 14:
									userCompanyMasterData.setAdditionalFlg(setValue);
									break;
								case 15:
									userCompanyMasterData.setFloorInfo(setValue);
									break;
								case 16:
									userCompanyMasterData.setEndDate(setValue);
									break;
								case 17:
									userCompanyMasterData.setUpdateUserId(Function.getLong(setValue));
									break;

							}
						}
						columnCount++;
						sIndex = eIndex + 1;
					}
					else{
						//	最終列
						if(i == 18 ){
							userCompanyMasterData.setEditDate(Function.getDateFromStr(inString.substring(sIndex + 1, inString.length() - 1), "yyyy-MM-dd HH:mm:ss"));
						}
					}
				}
				userCompanyList.add(userCompanyMasterData);
			}
			bufferedReader.close();
	        fileReader.close();
			deleteImportFile(filePath, UserCompanyFileName);

			returnStr += masterSession.insertUserCompanyMaster(userCompanyList);

		} catch (FileNotFoundException ex){
			errorMsg = "取込対象のファイルがありません。\n";
		}catch(Exception ex){
			errorMsg = "ユーザー所属マスタの取込処理に失敗しました。\n" + ex + "\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			returnStr += "　取込件数：" + row + "件\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	/**
	 * ユーザーマスタファイル取込
	 */
	public String importUserMaster(String filePath){
		String returnStr = "■　ユーザーマスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

		try{
			// zipファイル解凍
			String fileName = unZipCsvFile(filePath, UserFileName);
			FileInputStream fileReader = new FileInputStream(fileName);
			InputStreamReader inReader = new InputStreamReader(fileReader, "Windows-31J");
			BufferedReader bufferedReader = new BufferedReader(inReader);
			String inString;
			List<UserMasterData> userList = new ArrayList<UserMasterData>();

			// 1行ずつ読み込み
			while ( (inString = bufferedReader.readLine()) != null) {
				row++;
				int columnCount = 0;
				int sIndex = 0;
				int eIndex = 0;
				UserMasterData userMasterData = new UserMasterData();
				for(int i = 0; i < 33; i++){
					eIndex = inString.indexOf(",\"", sIndex);
					if(eIndex != -1){
						if(eIndex - sIndex >= 2){
							String setValue = inString.substring(sIndex + 1, eIndex - 1);
							switch (columnCount) {
							case 0:
								userMasterData.setUserId(Function.getLong(setValue));
								break;
							case 1:
								userMasterData.setStaffCode(setValue);
								break;
							case 2:
								userMasterData.setNameKanji(setValue);
								break;
							case 3:
								userMasterData.setNameKana(setValue);
								break;
							case 4:
								userMasterData.setNameEng(setValue);
								break;
							case 5:
								userMasterData.setMailAddress(setValue);
								break;
							case 6:
								userMasterData.setNickName(setValue);
								break;
							case 7:
								userMasterData.setAccount(setValue);
								break;
							case 8:
								userMasterData.setFullName(setValue);
								break;
							case 9:
								userMasterData.setPassword(setValue);
								break;
							case 10:
								userMasterData.setRetireplanDay(setValue);
								break;
							case 11:
								userMasterData.setRetiredDay(setValue);
								break;
							case 12:
								userMasterData.setCompanyCode(setValue);
								break;
							case 13:
								userMasterData.setStaffAttribute(setValue);
								break;
							case 14:
								userMasterData.setRegistrationDay(setValue);
								break;
							case 15:
								userMasterData.setTeldivCode(setValue);
								break;
							case 16:
								userMasterData.setManualdivCode(setValue);
								break;
							case 17:
								userMasterData.setMessage(setValue);
								break;
							case 18:
								userMasterData.setUpdateUserId(Function.getLong(setValue));
								break;
							case 19:
								userMasterData.setEditDate(Function.getDateFromStr(setValue, "yyyy-MM-dd HH:mm:ss"));
								break;
							case 20:
								userMasterData.setGaijiFlg(setValue);
								break;
							case 21:
								userMasterData.setMaildivCode(setValue);
								break;
							case 22:
								userMasterData.setDoubleStaffCode(setValue);
								break;
							case 23:
								userMasterData.setQuestion(setValue);
								break;
							case 24:
								userMasterData.setAnswer(setValue);
								break;
							case 25:
								userMasterData.setPassphrase1(setValue);
								break;
							case 26:
								userMasterData.setPassphrase2(setValue);
								break;
							case 27:
								userMasterData.setAutoForward(setValue);
								break;
							case 28:
								userMasterData.setEipaccess(setValue);
								break;
							case 29:
								userMasterData.setWebMail(setValue);
								break;
							case 30:
								userMasterData.setTelSearch(setValue);
								break;
							case 31:
								userMasterData.setTelProtectFlg(setValue);
								break;

							}
						}
						columnCount++;
						sIndex = eIndex + 1;
					}
					else{
						//	最終列
						if(i == 32 ){
							userMasterData.setAccessFlg(inString.substring(sIndex + 1, inString.length() - 1));
						}
					}
				}
				userList.add(userMasterData);
			}
			bufferedReader.close();
	        fileReader.close();
			deleteImportFile(filePath, UserFileName);

			returnStr += masterSession.insertUserMaster(userList);

		} catch (FileNotFoundException ex){
			errorMsg = "取込対象のファイルがありません。\n";
		}catch(Exception ex){
			errorMsg = "ユーザーマスタの取込処理に失敗しました。\n" + ex + "\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			returnStr += "　取込件数：" + row + "件\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	// ファイル名
	private static  String SapAssignmentsFileName = "IF_ERP01_FMB_0401";
	private static  String SapAccountFileName = "AccountsTitle";
	private static  String SapCustAccountsFileName = "Customer";
	private static  String SapVendorsFileName = "Vendor";
	private static  String SapExpDeptFileName = "KeihiFutan";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.BatchSession#sapMasterImport(jp.co.ctcg.easset.dto.CodeName)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String sapMasterImport(CodeName batchInfo) throws AppException {
		StringBuffer adminMailText = new StringBuffer();

		try {
			SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			batchInfo.setValue2(dtf.format(new Date())); // 開始時刻セット

			CodeName pathInfo = masterSession.getCodeName(Constants.CATEGORY_CODE_BATCH_SAP_PATH, "0", null, null);
			String filePath = pathInfo.getValue1();

			String batchType = Function.nvl(batchInfo.getInternalCode(), "");
			if(batchType.equals(BATCH_TYPE_SAP_ASSIGNMENTS_IMPORT)){
				// 従業員所属マスタ
				adminMailText.append(importSapAssignmentsMaster(filePath));
			}
			else if(batchType.equals(BATCH_TYPE_SAP_ACCOUNT_IMPORT)){
				// 勘定科目マスタ
				adminMailText.append(importSapAccountMaster(filePath));
			}
			else if(batchType.equals(BATCH_TYPE_SAP_CUST_ACCOUNTS_IMPORT)){
				// 顧客マスタ
				adminMailText.append(importSapCustAccountsMaster(filePath));
			}
			else if(batchType.equals(BATCH_TYPE_SAP_VENDORS_IMPORT)){
				// 仕入先マスタ
				adminMailText.append(importSapVendorsMaster(filePath));
			}
			else if(batchType.equals(BATCH_TYPE_SAP_EXP_DEPT_IMPORT)){
				// 経費負担部課マスタ
				adminMailText.append(importSapExpDeptMaster(filePath));
			}

			// 終了日セット
			batchInfo.setValue3(dtf.format(new Date()));

			StringBuffer adminMailHeader = new StringBuffer();
			adminMailHeader.append("　起動時間：" + batchInfo.getValue2() + " - " + batchInfo.getValue3() + "\n");
			adminMailHeader.append("　------------------------------------\n");

			adminMailText.insert(0, adminMailHeader.toString());

			// 実行内容履歴・実行内容セット
			setBatchInfoHist(batchInfo, adminMailText.toString());

			// バッチ処理ステータス更新
			masterSession.updateCodeName(Constants.STAFF_CODE_SYSTEM, batchInfo);
		} catch (Exception ex){
			adminMailText.append(ex + "\n");
		}
		return adminMailText.toString();
	}

	/**
	 * 従業員所属マスタファイル取込
	 */
	public String importSapAssignmentsMaster(String filePath){
		String returnStr = "■　従業員所属マスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

        File fp = new File(filePath);
        List<File> sortFileList = null;
		try{
			List<File> fileList = (List<File>)FileUtility.listAllFilesCurrent(fp, FileUtility.getNameFilter(SapAssignmentsFileName, true));
			sortFileList = FileUtility.sortTimestamp(fileList);
		}catch(Exception ex){
			returnStr += "連携ファイルの取得でエラーが発生しました。\n" + ex + "\n";
			Logging.error(returnStr);
			return returnStr;
		}

		if(sortFileList != null && sortFileList.size() > 0) {
			for(int i = 0; i < sortFileList.size(); i++) {
				File tgFile = sortFileList.get(i);
				String tgFileName = tgFile.getPath();
				returnStr += "　取込ファイル：" + tgFile.getName() + "\n";
				String tarFileName = "";
				String csvfileName = "";

				try{
					// 圧縮ファイル解凍
					tarFileName = FileUtility.unGzipFile(tgFileName);
					List<String> tarFileList = FileUtility.tarX(filePath, tarFileName);
					csvfileName = filePath + "/" + (String)tarFileList.get(0);

					FileInputStream fileReader = new FileInputStream(csvfileName);
					InputStreamReader inReader = new InputStreamReader(fileReader, "UTF-8");
					BufferedReader bufferedReader = new BufferedReader(inReader);
					String inString;
					List<SapAssignmentsMasterData> assignmentsList = new ArrayList<SapAssignmentsMasterData>();

					// 1行ずつ読み込み
					while ( (inString = bufferedReader.readLine()) != null) {
						row++;
						int columnCount = 0;
						int sIndex = 0;
						int eIndex = 0;
						SapAssignmentsMasterData assignmentsData = new SapAssignmentsMasterData();
						assignmentsData.setOtherFlag("0");
						for(int j = 0; j < 12; j++){
							eIndex = inString.indexOf(",\"", sIndex);
							if(eIndex != -1){
								if(eIndex - sIndex >= 2){
									String setValue = inString.substring(sIndex + 1, eIndex - 1);
									switch (columnCount) {
										case 0:	// アサイメント内部ID
											assignmentsData.setAssignmentId(setValue);
											break;
										case 1:	// 会社コード
											assignmentsData.setCompanyCode(setValue);
											break;
										case 2:	// 社員番号
											assignmentsData.setEmployeeNumber(setValue);
											break;
										case 3:	// 利益センタ
											assignmentsData.setDepartmentCode(setValue);
											break;
										case 4:	// 人事部課コード
											assignmentsData.setSectionCode(setValue);
											break;
										case 5:	// 主務兼務フラグ
											assignmentsData.setPrimaryFlag(setValue);
											break;
										case 6:	// 役職コード
											assignmentsData.setPostCode(setValue);
											break;
										case 7:	// 上長の社員番号
											assignmentsData.setSupervisorCode(setValue);
											break;
										case 8:	// 開始日付
											assignmentsData.setEffectiveStartDate(Function.getDateFromStr(setValue, "yyyy/MM/dd HH:mm:ss"));
											break;
										case 9:	// 終了日付
											assignmentsData.setEffectiveEndDate(Function.getDateFromStr(setValue, "yyyy/MM/dd HH:mm:ss"));
											break;
										case 10:	// 最終変更日
											assignmentsData.setUpdat(Function.getDateFromStr(setValue, "yyyy/MM/dd HH:mm:ss"));
											break;
									}
								}
								columnCount++;
								sIndex = eIndex + 1;
							}
							else{
								//	最終列
								if(j == 11){	// 登録日付
									assignmentsData.setCrdat(Function.getDateFromStr(inString.substring(sIndex + 1, inString.length() - 1), "yyyy/MM/dd HH:mm:ss"));
								}
							}
						}
						assignmentsList.add(assignmentsData);
					}
					bufferedReader.close();
			        fileReader.close();

			        // マスタテーブル更新
			        masterSession.mergeSapAssignmentsMaster(assignmentsList);

				} catch (Exception ex){
					errorMsg = "従業員所属マスタの取込処理に失敗しました。\n" + ex + "\n";
				} finally {
					try{
						// ファイル削除
						if(!csvfileName.equals("")){
							FileUtility.deleteFile(csvfileName);
						}
						if(!tarFileName.equals("")){
							FileUtility.deleteFile(tarFileName);
						}
						// 圧縮ファイルバックアップ
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
						FileUtility.moveFile(tgFileName, filePath + "_backup/" + tgFile.getName() + "."+ sdf1.format(new Date()));
						returnStr += "　取込件数：" + row + "件\n";
						row = 0;
					}catch(Exception ex){
						errorMsg = "連携ファイルのバックアップでエラーが発生しました。\n" + ex + "\n";
					}
				}

				if(!errorMsg.equals("")){
					break;
				}
			}
		}
		else {
			errorMsg = "取込対象のファイルがありません。\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "\n　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	/**
	 * 勘定科目マスタファイル取込
	 */
	public String importSapAccountMaster(String filePath){
		String returnStr = "■　勘定科目マスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

        File fp = new File(filePath);
        List<File> sortFileList = null;
		try{
			List<File> fileList = (List<File>)FileUtility.listAllFilesCurrent(fp, FileUtility.getNameFilter(SapAccountFileName, true));
			sortFileList = FileUtility.sortTimestamp(fileList);
		}catch(Exception ex){
			returnStr += "連携ファイルの取得でエラーが発生しました。\n" + ex + "\n";
			Logging.error(returnStr);
			return returnStr;
		}

		if(sortFileList != null && sortFileList.size() > 0) {
			for(int i = 0; i < sortFileList.size(); i++) {
				File tgFile = sortFileList.get(i);
				String tgFileName = tgFile.getPath();
				returnStr += "　取込ファイル：" + tgFile.getName() + "\n";
				String tarFileName = "";
				String csvfileName = "";

				try{
					// 圧縮ファイル解凍
					tarFileName = FileUtility.unGzipFile(tgFileName);
					List<String> tarFileList = FileUtility.tarX(filePath, tarFileName);
					csvfileName = filePath + "/" + (String)tarFileList.get(0);

					FileInputStream fileReader = new FileInputStream(csvfileName);
					InputStreamReader inReader = new InputStreamReader(fileReader, "UTF-8");
					BufferedReader bufferedReader = new BufferedReader(inReader);
					String inString;
					List<SapAccountMasterData> accountList = new ArrayList<SapAccountMasterData>();

					// 1行ずつ読み込み
					while ( (inString = bufferedReader.readLine()) != null) {
						row++;
						int columnCount = 0;
						int sIndex = 0;
						int eIndex = 0;
						SapAccountMasterData accountData = new SapAccountMasterData();
						for(int j = 0; j < 3; j++){
							eIndex = inString.indexOf(",\"", sIndex);
							if(eIndex != -1){
								if(eIndex - sIndex >= 2){
									String setValue = inString.substring(sIndex + 1, eIndex - 1);
									switch (columnCount) {
										case 0:	// 本科目コード
											accountData.setAccountCD(setValue);
											break;
										case 1:	// 本科目名称
											accountData.setAccountName(setValue);
											break;
									}
								}
								columnCount++;
								sIndex = eIndex + 1;
							}
							else{
								//	最終列
								if(j == 2){	// 有効フラグ
									accountData.setEnabledFlag(inString.substring(sIndex + 1, inString.length() - 1));
								}
							}
						}
						accountList.add(accountData);
					}
					bufferedReader.close();
			        fileReader.close();

			        // マスタテーブル更新
			        masterSession.mergeSapAccountMaster(accountList);

				} catch (Exception ex){
					errorMsg = "勘定科目マスタの取込処理に失敗しました。\n" + ex + "\n";
				} finally {
					try{
						// ファイル削除
						if(!csvfileName.equals("")){
							FileUtility.deleteFile(csvfileName);
						}
						if(!tarFileName.equals("")){
							FileUtility.deleteFile(tarFileName);
						}
						// 圧縮ファイルバックアップ
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
						FileUtility.moveFile(tgFileName, filePath + "_backup/" + tgFile.getName() + "."+ sdf1.format(new Date()));
						returnStr += "　取込件数：" + row + "件\n";
						row = 0;
					}catch(Exception ex){
						errorMsg = "連携ファイルのバックアップでエラーが発生しました。\n" + ex + "\n";
					}
				}

				if(!errorMsg.equals("")){
					break;
				}
			}
		}
		else {
			errorMsg = "取込対象のファイルがありません。\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "\n　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	/**
	 * 顧客マスタファイル取込
	 */
	public String importSapCustAccountsMaster(String filePath){
		String returnStr = "■　顧客マスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

        File fp = new File(filePath);
        List<File> sortFileList = null;
		try{
			List<File> fileList = (List<File>)FileUtility.listAllFilesCurrent(fp, FileUtility.getNameFilter(SapCustAccountsFileName, true));
			sortFileList = FileUtility.sortTimestamp(fileList);
		}catch(Exception ex){
			returnStr += "連携ファイルの取得でエラーが発生しました。\n" + ex + "\n";
			Logging.error(returnStr);
			return returnStr;
		}

		if(sortFileList != null && sortFileList.size() > 0) {
			for(int i = 0; i < sortFileList.size(); i++) {
				File tgFile = sortFileList.get(i);
				String tgFileName = tgFile.getPath();
				returnStr += "　取込ファイル：" + tgFile.getName() + "\n";
				String tarFileName = "";
				String csvfileName = "";

				try{
					// 圧縮ファイル解凍
					tarFileName = FileUtility.unGzipFile(tgFileName);
					List<String> tarFileList = FileUtility.tarX(filePath, tarFileName);
					csvfileName = filePath + "/" + (String)tarFileList.get(0);

					FileInputStream fileReader = new FileInputStream(csvfileName);
					InputStreamReader inReader = new InputStreamReader(fileReader, "UTF-8");
					BufferedReader bufferedReader = new BufferedReader(inReader);
					String inString;
					List<SapCustAccountsMasterData> custAccountsList = new ArrayList<SapCustAccountsMasterData>();

					// 1行ずつ読み込み
					while ( (inString = bufferedReader.readLine()) != null) {
						row++;
						int columnCount = 0;
						int sIndex = 0;
						int eIndex = 0;
						SapCustAccountsMasterData custAccountsData = new SapCustAccountsMasterData();
						for(int j = 0; j < 9; j++){
							if(j < 7){
								eIndex = inString.indexOf("\",\"", sIndex)+1;
							}
							else {
								eIndex = inString.indexOf(",\"", sIndex);
							}
							if(eIndex != -1){
								if(eIndex - sIndex >= 2){
									String setValue = inString.substring(sIndex + 1, eIndex - 1);
									switch (columnCount) {
										case 0:	// 得意先コード
											custAccountsData.setCustID(setValue);
											break;
										case 1:	// 得意先グループ
											custAccountsData.setCustGroup(setValue);
											break;
										case 2:	// 勘定グループ
											custAccountsData.setAccountGroup(setValue);
											break;
										case 3:	// 得意先名称
											custAccountsData.setCustName(setValue);
											break;
										case 4:	// 会社コード
											custAccountsData.setCompanyCD(setValue);
											break;
										case 5:	// 取引状態区分
											custAccountsData.setCustStatus(setValue);
											break;
										case 6:	// 登録日
											custAccountsData.setCreateDate(Function.getDateFromStr(setValue, "yyyyMMdd"));
											break;
										case 7:	// 最終更新日
											custAccountsData.setLastUpdateDate(Function.getDateFromStr(setValue, "yyyyMMddHHmmss"));
											break;
									}
								}
								columnCount++;
								sIndex = eIndex + 1;
							}
							else{
								//	最終列
								if(j == 8){	// 有効フラグ
									custAccountsData.setEnabledFlag(inString.substring(sIndex + 1, inString.length() - 1));
								}
							}
						}
						custAccountsList.add(custAccountsData);
					}
					bufferedReader.close();
			        fileReader.close();

			        // マスタテーブル更新
			        masterSession.mergeSapCustAccountsMaster(custAccountsList);

				} catch (Exception ex){
					errorMsg = "顧客マスタの取込処理に失敗しました。\n" + ex + "\n";
				} finally {
					try{
						// ファイル削除
						if(!csvfileName.equals("")){
							FileUtility.deleteFile(csvfileName);
						}
						if(!tarFileName.equals("")){
							FileUtility.deleteFile(tarFileName);
						}
						// 圧縮ファイルバックアップ
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
						FileUtility.moveFile(tgFileName, filePath + "_backup/" + tgFile.getName() + "."+ sdf1.format(new Date()));
						returnStr += "　取込件数：" + row + "件\n";
						row = 0;
					}catch(Exception ex){
						errorMsg = "連携ファイルのバックアップでエラーが発生しました。\n" + ex + "\n";
					}
				}

				if(!errorMsg.equals("")){
					break;
				}
			}
		}
		else {
			errorMsg = "取込対象のファイルがありません。\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "\n　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	/**
	 * 仕入先マスタファイル取込
	 */
	public String importSapVendorsMaster(String filePath){
		String returnStr = "■　仕入先マスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

        File fp = new File(filePath);
        List<File> sortFileList = null;
		try{
			List<File> fileList = (List<File>)FileUtility.listAllFilesCurrent(fp, FileUtility.getNameFilter(SapVendorsFileName, true));
			sortFileList = FileUtility.sortTimestamp(fileList);
		}catch(Exception ex){
			returnStr += "連携ファイルの取得でエラーが発生しました。\n" + ex + "\n";
			Logging.error(returnStr);
			return returnStr;
		}

		if(sortFileList != null && sortFileList.size() > 0) {
			for(int i = 0; i < sortFileList.size(); i++) {
				File tgFile = sortFileList.get(i);
				String tgFileName = tgFile.getPath();
				returnStr += "　取込ファイル：" + tgFile.getName() + "\n";
				String tarFileName = "";
				String csvfileName = "";

				try{
					// 圧縮ファイル解凍
					tarFileName = FileUtility.unGzipFile(tgFileName);
					List<String> tarFileList = FileUtility.tarX(filePath, tarFileName);
					csvfileName = filePath + "/" + (String)tarFileList.get(0);

					FileInputStream fileReader = new FileInputStream(csvfileName);
					InputStreamReader inReader = new InputStreamReader(fileReader, "UTF-8");
					BufferedReader bufferedReader = new BufferedReader(inReader);
					String inString;
					List<SapVendorsMasterData> vendorsList = new ArrayList<SapVendorsMasterData>();

					// 1行ずつ読み込み
					while ( (inString = bufferedReader.readLine()) != null) {
						row++;
						int columnCount = 0;
						int sIndex = 0;
						int eIndex = 0;
						SapVendorsMasterData vendorsData = new SapVendorsMasterData();
						for(int j = 0; j < 9; j++){
							if(j < 7){
								eIndex = inString.indexOf("\",\"", sIndex)+1;
							}
							else {
								eIndex = inString.indexOf(",\"", sIndex);
							}
							if(eIndex != -1){
								if(eIndex - sIndex >= 2){
									String setValue = inString.substring(sIndex + 1, eIndex - 1);
									switch (columnCount) {
										case 0:	// 仕入先コード
											vendorsData.setVendorID(setValue);
											break;
										case 1:	// 仕入先グループ
											vendorsData.setVendorGroup(setValue);
											break;
										case 2:	// 勘定グループ
											vendorsData.setAccountGroup(setValue);
											break;
										case 3:	// 仕入先名称
											vendorsData.setVendorName(setValue);
											break;
										case 4:	// 会社コード
											vendorsData.setCompanyCD(setValue);
											break;
										case 5:	// 取引状態区分
											vendorsData.setVendorStatus(setValue);
											break;
										case 6:	// 登録日
											vendorsData.setCreateDate(Function.getDateFromStr(setValue, "yyyyMMdd"));
											break;
										case 7:	// 最終更新日
											vendorsData.setLastUpdateDate(Function.getDateFromStr(setValue, "yyyyMMddHHmmss"));
											break;
									}
								}
								columnCount++;
								sIndex = eIndex + 1;
							}
							else{
								//	最終列
								if(j == 8){	// 有効フラグ
									vendorsData.setEnabledFlag(inString.substring(sIndex + 1, inString.length() - 1));
								}
							}
						}
						vendorsList.add(vendorsData);
					}
					bufferedReader.close();
			        fileReader.close();

			        // マスタテーブル更新
			        masterSession.mergeSapVendorsMaster(vendorsList);

				} catch (Exception ex){
					errorMsg = "仕入先マスタの取込処理に失敗しました。\n" + ex + "\n";
				} finally {
					try{
						// ファイル削除
						if(!csvfileName.equals("")){
							FileUtility.deleteFile(csvfileName);
						}
						if(!tarFileName.equals("")){
							FileUtility.deleteFile(tarFileName);
						}
						// 圧縮ファイルバックアップ
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
						FileUtility.moveFile(tgFileName, filePath + "_backup/" + tgFile.getName() + "."+ sdf1.format(new Date()));
						returnStr += "　取込件数：" + row + "件\n";
						row = 0;
					}catch(Exception ex){
						errorMsg = "連携ファイルのバックアップでエラーが発生しました。\n" + ex + "\n";
					}
				}

				if(!errorMsg.equals("")){
					break;
				}
			}
		}
		else {
			errorMsg = "取込対象のファイルがありません。\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "\n　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	/**
	 * 経費負担部課マスタファイル取込
	 */
	public String importSapExpDeptMaster(String filePath){
		String returnStr = "■　経費負担部課マスタ\n";
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String startDateStr = dtf.format(new Date()); // 開始時刻セット
		String errorMsg = "";
		long row = 0;

		File fp = new File(filePath);
        List<File> sortFileList = null;
		try{
			List<File> fileList = (List<File>)FileUtility.listAllFilesCurrent(fp, FileUtility.getNameFilter(SapExpDeptFileName, true));
			sortFileList = FileUtility.sortTimestamp(fileList);
		}catch(Exception ex){
			returnStr += "連携ファイルの取得でエラーが発生しました。\n" + ex + "\n";
			Logging.error(returnStr);
			return returnStr;
		}

		if(sortFileList != null && sortFileList.size() > 0) {
			for(int i = 0; i < sortFileList.size(); i++) {
				File tgFile = sortFileList.get(i);
				String tgFileName = tgFile.getPath();
				returnStr += "　取込ファイル：" + tgFile.getName() + "\n";
				String tarFileName = "";
				String csvfileName = "";

				try{
					// 圧縮ファイル解凍
					tarFileName = FileUtility.unGzipFile(tgFileName);
					List<String> tarFileList = FileUtility.tarX(filePath, tarFileName);
					csvfileName = filePath + "/" + (String)tarFileList.get(0);

					FileInputStream fileReader = new FileInputStream(csvfileName);
					InputStreamReader inReader = new InputStreamReader(fileReader, "UTF-8");
					BufferedReader bufferedReader = new BufferedReader(inReader);
					String inString;
					List<SapExpDeptMasterData> expDeptList = new ArrayList<SapExpDeptMasterData>();

					// 1行ずつ読み込み
					while ( (inString = bufferedReader.readLine()) != null) {
						row++;
						int columnCount = 0;
						int sIndex = 0;
						int eIndex = 0;
						SapExpDeptMasterData aexpDeptData = new SapExpDeptMasterData();
						for(int j = 0; j < 12; j++){
							if(j < 10){
								eIndex = inString.indexOf("\",\"", sIndex)+1;
							}
							else {
								eIndex = inString.indexOf(",\"", sIndex);
							}
							if(eIndex != -1){
								if(eIndex - sIndex >= 2){
									String setValue = inString.substring(sIndex + 1, eIndex - 1);
									switch (columnCount) {
										case 0:	// 会社コード
											aexpDeptData.setCompanyCD(setValue);
											break;
										case 1:	// 経費負担部課コード
											aexpDeptData.setDeptCD(setValue);
											break;
										case 2:	// 経費負担部課名称
											aexpDeptData.setDeptName(setValue);
											break;
										case 3:	// 集計フラグ
											aexpDeptData.setSummaryFlag(setValue);
											break;
										case 4:	// 原価販管フラグ
											aexpDeptData.setCostFlag(setValue);
											break;
										case 5:	// 有効開始日
											aexpDeptData.setStartDate(Function.getDateFromStr(setValue, "yyyy/MM/dd"));
											if(aexpDeptData.getStartDate() == null) {
												aexpDeptData.setStartDate(Function.getDateFromStr("1900/01/01", "yyyy/MM/dd"));
											}
											break;
										case 6:	// 有効終了日
											aexpDeptData.setEndDate(Function.getDateFromStr(setValue, "yyyy/MM/dd"));
											break;
										case 7:	// 有効フラグ
											aexpDeptData.setEnabledFlag(setValue);
											break;
										case 8:	// ソートキー
											aexpDeptData.setSortKey(setValue);
											break;
										case 9:	// 最終更新日
											aexpDeptData.setLastUpdateDate(Function.getDateFromStr(setValue, "yyyy/MM/dd"));
											break;
										case 10:// 部課口フラグ
											aexpDeptData.setSectionFlag(setValue);
											break;
									}
								}
								columnCount++;
								sIndex = eIndex + 1;
							}
							else{
								//	最終列
								if(j == 11){	// 組織階層区分
									aexpDeptData.setCategoryCode(inString.substring(sIndex + 1, inString.length() - 1));
								}
							}
						}
						expDeptList.add(aexpDeptData);
					}
					bufferedReader.close();
			        fileReader.close();

			        // マスタテーブル更新
			        masterSession.mergeSapExpDeptMaster(expDeptList);

				} catch (Exception ex){
					errorMsg = "経費負担部課マスタの取込処理に失敗しました。\n" + ex + "\n";
				} finally {
					try{
						// ファイル削除
						if(!csvfileName.equals("")){
							FileUtility.deleteFile(csvfileName);
						}
						if(!tarFileName.equals("")){
							FileUtility.deleteFile(tarFileName);
						}
						// 圧縮ファイルバックアップ
						SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMddHHmmss");
						FileUtility.moveFile(tgFileName, filePath + "_backup/" + tgFile.getName() + "."+ sdf1.format(new Date()));
						returnStr += "　取込件数：" + row + "件\n";
						row = 0;
					}catch(Exception ex){
						errorMsg = "連携ファイルのバックアップでエラーが発生しました。\n" + ex + "\n";
					}
				}

				if(!errorMsg.equals("")){
					break;
				}
			}
		}
		else {
			errorMsg = "取込対象のファイルがありません。\n";
		}

		if(errorMsg.equals("")){
			String endDateStr = dtf.format(new Date()); // 開始時刻セット
			returnStr += "\n　処理時間：" + startDateStr + " - " + endDateStr + "\n";
			Logging.info(returnStr);
		}
		else{
			returnStr += errorMsg;
			Logging.error(returnStr);
		}
		return returnStr;
	}

	/**
	 * ファイル解凍処理
	 * @throws Exception ファイル解凍エラー時にスローされる。
	 */
	public String unZipCsvFile(String filePath, String fileName) throws FileNotFoundException, Exception{
		String csvfileName = "";
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		String tarFileName = FileUtility.unGzipFile(filePath + "/" + fileName  + "_"+ sdf1.format(new Date()) + GzFileExtension);
		List<String> tarFileList = FileUtility.tarX(filePath, tarFileName);
		//	CSVファイル読み込み
		csvfileName = filePath + "/" + (String)tarFileList.get(0);
		return csvfileName;
	}

	/**
	 * ファイル削除処理
	 * @throws Exception ファイル削除エラー時にスローされる。
	 */
	public void deleteImportFile(String filePath, String fileName) throws Exception{
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
		//	zipファイル削除
		FileUtility.deleteFile(filePath + "/" + fileName + "_" + sdf1.format(new Date()) + GzFileExtension);
		//	tarファイル削除
		FileUtility.deleteFile(filePath + "/" + fileName + "_" + sdf1.format(new Date()) + TarFileExtension);
		//	csvファイル削除
		FileUtility.deleteFile(filePath + "/" + fileName + CsvFileExtension);
	}

	/**
	 * 引数のMapにkeyとvalueが重複しなければ追加する
	 * @param mapData Mapデータ
	 * @param key キー
	 * @param value 値
	 */
	private HashMap<String, String> setMailMap(HashMap<String, String> mapData, String key, String value) {

		String valueData = mapData.get(key);
		if(valueData == null){
			mapData.put(key, value);
		}
		else{
			String[] valueArry = valueData.split("\n");
			int i = 0;
			for(; i < valueArry.length; i++){
				if(value.equals(valueArry[i])){
					break;
				}
			}
			if(i == valueArry.length){
				mapData.put(key, valueData + "\n" + value);
			}
		}
		return mapData;
	}

}