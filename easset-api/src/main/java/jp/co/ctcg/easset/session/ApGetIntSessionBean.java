/*===================================================================
 * ファイル名 : ApGetIntSessionBean.java
 * 概要説明   : 取得申請(無形)セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Calendar;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import jp.co.ctcg.easset.dao.ApGetIntDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.LovDataEx;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetInt;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineFld;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineDevSch;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineOtrCost;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineProfEst;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineAtt;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntLineCostSec;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntSC;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntSR;
import jp.co.ctcg.easset.dto.fld.PpfsFldApp;
import jp.co.ctcg.easset.dto.fld.PpfsFldSR;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;
import jp.co.ctcg.easset.util.PdfExporter;
import jp.co.ctcg.easset.ws.ApGetIntService;
import jp.co.ctcg.easset.ws.ApGetIntServiceProxy;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;

import org.apache.commons.beanutils.PropertyUtils;


@Stateless
public class ApGetIntSessionBean implements ApGetIntSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession;

	@EJB
	FldSession fldSession;

	@EJB
	HistSession histSession;

	@EJB
	SendMailSession sendMailSession;

	private static final String ID_PREFIX = "AS";
	private static final String FILE_SAVE_ENTITY_NAME = "AP_GET_INT";

	// 履歴作成用
	private static final String HIST_ENTITY_NAME = "AP_GET_INT";
	private static final String HIST_OPERATION_CREATE = "新規作成";
	private static final String HIST_OPERATION_UPDATE = "更新";
	private static final String HIST_OPERATION_DELETE = "削除";
	private static final String HIST_OPERATION_APPLY = "申請";
	private static final String HIST_OPERATION_APPROVE = "承認";
	private static final String HIST_OPERATION_SENDBACK = "差戻し";
	private static final String HIST_OPERATION_REJECT = "却下";
	private static final String HIST_OPERATION_CANCEL_APPLY = "引戻し";
	private static final String HIST_OPERATION_REMIND = "督促";

	// メールテンプレート変数
	private static final String MAIL_TEMPLATE_VAR_AP_STAFF_NAME = "\\$\\{申請者名\\}";
	private static final String MAIL_TEMPLATE_VAR_APPLICATION_ID = "\\$\\{申請書番号\\}";
	private static final String MAIL_TEMPLATE_VAR_AST_NAME = "\\$\\{資産名\\}";
	private static final String MAIL_TEMPLATE_VAR_ET_TOTAL_AMOUNT = "\\$\\{取得金額\\}";
	private static final String MAIL_TEMPLATE_VAR_COMPLETE_PLAN_DATE = "\\$\\{リリース・検収予定日\\}";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#searchApGetInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_int.ApGetIntSC)
	 */
	public List<ApGetIntSR> searchApGetInt(String loginStaffCode, String accessLevel, ApGetIntSC searchParam) {
		try {
			ApGetIntDAO apGetIntDAO = new ApGetIntDAO();
			return apGetIntDAO.selectApGetIntList(loginStaffCode, accessLevel, searchParam);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#createApGetIntCsv(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_int.ApGetIntSC, java.lang.Boolean)
	 */
	public String createApGetIntCsv(String loginStaffCode, String accessLevel, ApGetIntSC searchParam, Boolean lineOutputFlag) {
		try {
			ApGetIntDAO apGetIntDAO = new ApGetIntDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = apGetIntDAO.createApGetIntListCsv(loginStaffCode, accessLevel, searchParam, lineOutputFlag);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_GET_INT_SEARCH, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",lineOutputFlag:" + lineOutputFlag + "," + Function.toString(searchParam));

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#getApGetInt(java.lang.String, java.lang.String)
	 */
	public ApGetInt getApGetInt(String applicationId, String applicationVersion) {
		return getApGetInt(applicationId, applicationVersion, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#getApGetInt(java.lang.String)
	 */
	public ApGetInt getApGetInt(Long eappId) {
		return getApGetInt(eappId, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#getApGetInt(java.lang.String, java.lang.String, boolean)
	 */
	public ApGetInt getApGetInt(String applicationId, String applicationVersion, boolean lockFlag) {
		try {
			ApGetIntDAO apGetIntDAO = new ApGetIntDAO();

			ApGetInt apGetInt = apGetIntDAO.selectApGetInt(applicationId, applicationVersion, lockFlag); // ヘッダの取得
			if(apGetInt != null) { // データ有
				List<ApGetIntLineFld> fldList = apGetIntDAO.selectApGetIntLineFld(applicationId, apGetInt.getApplicationVersion());	// 資産明細取得
				apGetInt.setApGetIntLineFldList(fldList);
				List<ApGetIntLineDevSch> devSchList = apGetIntDAO.selectApGetIntLineDevSch(applicationId, apGetInt.getApplicationVersion());	// 開発スケジュール明細取得
				if(devSchList != null){
					for(int i = 0; i < devSchList.size(); i++){
						ApGetIntLineDevSch devSchData = devSchList.get(i);
						String devSchCode = Function.nvl(devSchData.getDevSchCode(), "");
						String editFlag = "0";
						if(devSchCode.equals("1"))
							editFlag = apGetInt.getMktDevSchLineEditFlag1();
						else if(devSchCode.equals("2"))
							editFlag = apGetInt.getMktDevSchLineEditFlag2();
						else if(devSchCode.equals("3"))
							editFlag = apGetInt.getMktDevSchLineEditFlag3();
						else if(devSchCode.equals("4"))
							editFlag = apGetInt.getMktDevSchLineEditFlag4();
						else if(devSchCode.equals("5"))
							editFlag = apGetInt.getMktDevSchLineEditFlag5();
						else if(devSchCode.equals("6"))
							editFlag = apGetInt.getMktDevSchLineEditFlag6();
						else if(devSchCode.equals("7"))
							editFlag = apGetInt.getMktDevSchLineEditFlag7();
						else if(devSchCode.equals("8"))
							editFlag = apGetInt.getMktDevSchLineEditFlag8();
						else if(devSchCode.equals("9"))
							editFlag = apGetInt.getMktDevSchLineEditFlag9();
						else if(devSchCode.equals("10"))
							editFlag = apGetInt.getMktDevSchLineEditFlag10();
						devSchData.setLineEditFlag(editFlag);
						devSchList.set(i, devSchData);
					}
				}
				apGetInt.setApGetIntLineDevSchList(devSchList);
				apGetInt.setApGetIntLineOtrCostList(apGetIntDAO.selectApGetIntLineOtrCost(applicationId, apGetInt.getApplicationVersion()));	// その他費用明細取得
				apGetInt.setApGetIntLineProfEstList(apGetIntDAO.selectApGetIntLineProfEst(applicationId, apGetInt.getApplicationVersion()));	// 利益予測明細取得
				apGetInt.setApGetIntLineAttList(apGetIntDAO.selectApGetIntLineAtt(applicationId, apGetInt.getApplicationVersion()));			// 添付明細取得
				apGetInt.setApGetIntLineCostSecList(apGetIntDAO.selectApGetIntLineCostSec(applicationId, apGetInt.getApplicationVersion()));	// 経費負担明細取得

				// ライセンス、ライセンス登録申請用
				// ライセンス資産区分
				String licAssetType = Constants.LICENSE_ASSET_TYPE_BIHIN;	// 備品・雑費

				// 市販目的ソフトウェア
				if(Function.nvl(apGetInt.getApGetType(), "").equals(Constants.AP_GET_TYPE_INT2)){
					if(Function.nvl(apGetInt.getMktCatTaskCode(), "").equals(Constants.AP_GET_INT_MKT_TASK1)){	// ソフトウェア仮
						// 無形固定資産
						licAssetType = Constants.LICENSE_ASSET_TYPE_INTAN;
					}
				}
				else{
					String addUpType = Constants.ADD_TYPE2;					// 費用

					if(fldList != null){
						for(int i = 0; i < fldList.size(); i++){
							ApGetIntLineFld fldData = fldList.get(i);
							if(Function.nvl(fldData.getAstAddUpType(), "").equals(Constants.ADD_TYPE1)){		// 資産
								addUpType = Constants.ADD_TYPE1;
								break;
							}
						}
					}

					// 資産
					if(addUpType.equals(Constants.ADD_TYPE1)){
						// 無形固定資産
						licAssetType = Constants.LICENSE_ASSET_TYPE_INTAN;
					}
				}
				apGetInt.setLicAssetType(licAssetType);
				CodeName codeName = masterSession.getCodeName(Constants.CATEGORY_CODE_LICENSE_ASSET_TYPE, licAssetType, null, null);
				if(codeName != null){
					apGetInt.setLicAssetTypeName(codeName.getValue1());
				}
			}

			return apGetInt;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)取得"), e);
		}
	}

	/**
	 * 申請情報取得
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private ApGetInt getApGetInt(Long eappId, boolean lockFlag) {
		try {
			ApGetIntDAO apGetIntDAO = new ApGetIntDAO();

			ApGetInt apGetInt = apGetIntDAO.selectApGetInt(eappId, lockFlag); // ヘッダの取得
			if(apGetInt != null) {
				return getApGetInt(apGetInt.getApplicationId(), apGetInt.getApplicationVersion(), lockFlag);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)取得"), e);
		}
	}

	/**
	 * 不正セット項目値の調整
	 * @param obj 情報機器等データ
	 * @throws AppException
	 */
	private void setPropertyAdjust(ApGetInt obj) throws AppException {

		// 取得形態
		String apGetType = Function.nvl(obj.getApGetType(), "");

		// フラグがNULLの場合デフォルトセット
		if(obj.getSpecialApproveFlag() == null) obj.setSpecialApproveFlag(Constants.FLAG_NO);
		if(obj.getApSkipApproveFlag() == null) obj.setApSkipApproveFlag(Constants.FLAG_NO);
		if(obj.getApNeedCioJudgeFlag() == null) obj.setApNeedCioJudgeFlag(Constants.FLAG_NO);
		if(obj.getReportCompleteFlag() == null) obj.setReportCompleteFlag(Constants.FLAG_NO);
		if(obj.getActiveFlag() == null) obj.setActiveFlag(Constants.FLAG_YES);
		if(obj.getVerUpType() == null) obj.setVerUpType("0");

		// 稟議書・経営会議承認済チェックが付いていない場合は、承認日クリア
		if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {
			obj.setSpecialApproveDate(null);
		}

		// 市販目的ソフトウェアに応じた情報のクリア/セット
		if(apGetType.equals(Constants.AP_GET_TYPE_INT2)){
			obj.setAstCloudType(null);					// クラウド区分
			obj.setAstGroupCode(null);					// 案件グループ
			obj.setAstGetReason(null);					// 取得(再申請)理由

			if(!Function.nvl(obj.getMktProfEstFlag(), "").equals("1")) {
				obj.setApGetIntLineProfEstList(null);	// 販売・利益予測明細
			}

			if(!Function.nvl(obj.getMktDevNextPlanDateFlag(), "").equals("1")) {
				obj.setMktDevNextPlanDate(null);		// 次期開発完了予定日
			}
			obj.setApGetIntLineFldList(null);			// 資産明細

			obj.setHolRepOfficeCode(null);			// 代表設置場所

			if(Function.nvl(obj.getMktCatTaskCode(), "").equals(Constants.AP_GET_INT_MKT_TASK1)) { // 会計計上区分が”ソフトウェア仮”
				obj.setAstTotalAmount(obj.getGetTotalAmount());
			} else { // 会計計上区分が”ソフトウェア仮”以外
				obj.setAstTotalAmount(Long.valueOf(0));
			}
		}
		else{
			obj.setMktCatCategoryCode(null);			// 分類
			obj.setMktCatTaskCode(null);				// タスク【会計計上区分】
			obj.setMktAstProjectCode(null);				// 資産プロジェクトコード
			obj.setMktAstProductOutline(null);			// 製品の概要
			obj.setMktAstMarket(null);					// 市場性
			obj.setMktAstPolicy(null);					// マーケティング方針
			obj.setMktDevNextPlanDate(null);			// 次期開発完了予定日

			obj.setApGetIntLineDevSchList(null);		// 開発スケジュール明細
			obj.setApGetIntLineOtrCostList(null);		// その他費用明細
			obj.setApGetIntLineProfEstList(null);		// 販売・利益予測明細
		}

		// 社内使用ソフトウェア以外に応じた情報のクリア
		if(apGetType.equals(Constants.AP_GET_TYPE_INT1)){
			if(!Function.nvl(obj.getApGetAmountRangeUseCostType(), "").equals("1")) {
				obj.setAstEffectType(null);				// 費用削減効果／収益獲得効果
				obj.setAstEffectDescription(null);		// 費用削減効果／収益獲得効果 備考
			}

			if(!Function.nvl(obj.getAstEffectType(), "").equals("1")) {
				obj.setAstEffectAmount(null);			// 費用削減・収益獲得金額
			}
		}
		else{
			obj.setAstGetTimeFlag(null);				// 取得時期

			obj.setAstEffectType(null);					// 費用削減効果／収益獲得効果
			obj.setAstEffectAmount(null);				// 費用削減・収益獲得金額
			obj.setAstEffectDescription(null);			// 費用削減効果／収益獲得効果 備考
		}

		// 長期前払費用/その他無形固定資産に応じた情報のクリア
		if(apGetType.equals(Constants.AP_GET_TYPE_INT3)){
			if(!Function.nvl(obj.getAstRentFlag(), "").equals("1")) {
				obj.setAstRentApplicationId(null);		// リース・レンタル取得申請書番号
				obj.setAstRentMonth(null);				// 契約月数
				obj.setAstRentDateFrom(null);			// 賃貸予定期間FROM
				obj.setAstRentDateTo(null);				// 賃貸予定期間TO
				obj.setAstRentDescription(null);		// 賃借物件据付費用備考
			}
		}
		else{
			obj.setAstRentFlag(null);					// 賃借物件据付費用
			obj.setAstRentApplicationId(null);			// リース・レンタル取得申請書番号
			obj.setAstRentMonth(null);					// 契約月数
			obj.setAstRentDateFrom(null);				// 賃貸予定期間FROM
			obj.setAstRentDateTo(null);					// 賃貸予定期間TO
			obj.setAstRentDescription(null);			// 賃借物件据付費用備考
		}


		// 取得形態・金額に応じた経費負担情報のクリア
		String apGetAmountRangeCode = Function.nvl(obj.getApGetAmountRange(), "");
		if(!apGetAmountRangeCode.equals("")) {
			CodeName apGetAmountRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_GET_INT_AMOUNT_RANGE, obj.getApGetAmountRange(), obj.getApCompanyCode(), null);
			if(apGetAmountRange != null) {
				if(Function.nvl(apGetAmountRange.getValue7(), "").equals("0")) { // 未使用
					obj.setCostType(null);
					obj.setCostDepPrjCode(null);
					obj.setCostDepPrjName(null);
					obj.setApGetIntLineCostSecList(new ArrayList<ApGetIntLineCostSec>());
				} else if(Function.nvl(apGetAmountRange.getValue7(), "").equals("2")) { // 部課のみ使用
					obj.setCostType(null);
					obj.setCostDepPrjCode(null);
					obj.setCostDepPrjName(null);
				} else if(Function.nvl(apGetAmountRange.getValue7(), "").equals("3")) { // 販管/原価区分のみ使用
					obj.setApGetIntLineCostSecList(new ArrayList<ApGetIntLineCostSec>());
				}
			}

			// 原価以外はプロジェクト項目クリア
/*
			if(!Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)) {
				obj.setCostDepPrjCode(null);
				obj.setCostDepPrjName(null);
			}
*/
		} else {
			obj.setCostType(null);
			obj.setCostDepPrjCode(null);
			obj.setCostDepPrjName(null);
			obj.setApGetIntLineCostSecList(new ArrayList<ApGetIntLineCostSec>());
		}


		// 資産明細
		List<ApGetIntLineFld> fldList = obj.getApGetIntLineFldList();
		if(fldList != null && fldList.size() > 0) {
			for(int i = 0; i < fldList.size(); i++) {
				ApGetIntLineFld row = fldList.get(i);

				// 社内使用ソフトウェア(分類：ライセンス)
				if(apGetType.equals(Constants.AP_GET_TYPE_INT1) && Function.nvl(row.getAstCategoryType(), "").equals(Constants.AP_GET_INT_LINE_AST_CATEGORY_TYPE_LIC)){
					// 期間ライセンス以外は期間(年)項目クリア
					if(!Function.nvl(row.getAstTermFlag(), "").equals("1")){
						row.setAstTermYear(null);
					}
				}
				else{
					row.setAstTermFlag(null);
					row.setAstTermYear(null);
				}

				// 分類が自社製作
				if(Function.nvl(row.getAstCategoryType(), "").equals(Constants.AP_GET_INT_LINE_AST_CATEGORY_TYPE_OWN)){
					row.setAstPurCompanyCode(null);			// 仕入先
					row.setAstEstimateNumber(null);			// 見積番号
				}
				else{
					row.setAstPrjCode(null);				// 資産ﾌﾟﾛｼﾞｪｸﾄｺｰﾄﾞ
				}
				fldList.set(i, row);
			}
			obj.setApGetIntLineFldList(fldList);
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#createApGetInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_int.ApGetInt)
	 */
	public String createApGetInt(String loginStaffCode, String accessLevel, ApGetInt obj) throws AppException {
		return createApGetInt(loginStaffCode, accessLevel, obj, true);
	}

	/**
	 * 取得申請作成本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの取得申請書番号
	 */
	private String createApGetInt(String loginStaffCode, String accessLevel, ApGetInt obj, boolean isHistCreate) throws AppException {
		try {
			ApGetIntDAO apGetIntDAO = new ApGetIntDAO();

			//////////////////////////////////// 固定値セット
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setCreateDate(sysdate);
			obj.setCreateStaffCode(loginStaffCode);
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			errMsg.append(validateApGetInt(loginStaffCode, accessLevel, obj, null));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ作成
			// バージョン・改訂番号
			obj.setVersion(1);
			obj.setDisplayVersion(1);

			// アクティブフラグ
			obj.setActiveFlag("1");

			String applicationId = Function.nvl(obj.getApplicationId(), "");
			if(applicationId.equals("")){
				//////////////////////////////////// IDの採番
				applicationId = masterSession.nextValId(ID_PREFIX);
				obj.setApplicationId(applicationId); // IDセット
				obj.setApplicationVersion("01"); // バージョンセット
			}

			//////////////////////////////////// 旧バージョンの取得申請書を無効にする
			// バージョンアップタイプ　1:追加および変更,2:変更(財経承認のみ)
			String verUpType = Function.nvl(obj.getVerUpType(), "");
			if(verUpType.equals("1") || verUpType.equals("2")){
				int versionNum = (Integer.valueOf(Function.nvl(obj.getApplicationVersion(), "0"))).intValue();
				if(versionNum > 1){
					versionNum--;
					String applicationVersion = Integer.toString(versionNum);
					if(applicationVersion.length() < 2){
						applicationVersion = "0" + applicationVersion;
					}
					ApGetInt apGetIntData = getApGetInt(applicationId, applicationVersion);
					if(apGetIntData != null){
						// 新ver申請のversion（内部ロック処理で使用）は旧ver申請から引き継ぐ
						// (新ver作成 -> 新ver削除 -> 再度新ver作成を行った場合の同一applicationVersionでの重複回避のため)
						obj.setVersion(Function.nvl(apGetIntData.getVersion(), Integer.valueOf(1)) + 1);

						apGetIntData.setUpdateDate(sysdate);
						apGetIntData.setUpdateStaffCode(loginStaffCode);
						apGetIntData.setActiveFlag("0");
						apGetIntDAO.updateApGetInt(apGetIntData);
					}
				}
			}

			apGetIntDAO.insertApGetInt(obj); // ヘッダ作成
			createLine(loginStaffCode, obj, apGetIntDAO, applicationId); // 明細データ作成

			//////////////////////////////////// 前回バージョンからの変更項目セット
			updateVerUpColumnName(obj, apGetIntDAO);

			// 申請ステータスが承認済の場合、資産紐付け情報作成
			String apStatus = Function.nvl(obj.getApStatus(), "");
			if(apStatus.equals(Constants.AP_STATUS_GET_INT_APPROVE)){
				PpfsFldApp fldAppData = fldSession.getFldApp(loginStaffCode, accessLevel, obj.getApCompanyCode(), obj.getApplicationId());
				if(fldAppData == null){
					fldAppData = new PpfsFldApp();
				}

				 // 本勘定固定資産が無い場合は資産紐付け情報を更新する
				List<PpfsFldSR> ppfsListH = fldAppData.getPpfsListH();
				if(ppfsListH == null || ppfsListH.size() == 0){
					fldAppData.setApplicationId(obj.getApplicationId());
					fldAppData.setApGetType(obj.getApGetType());
					fldAppData.setHolCompanyCode(obj.getHolCompanyCode());
					fldAppData.setHolSectionCode(obj.getHolSectionCode());
					fldAppData.setHolSectionYear(obj.getHolSectionYear());
					fldAppData.setHolStaffCode(obj.getHolStaffCode());
					fldAppData.setHolStaffCompanyCode(obj.getHolStaffCompanyCode());
					fldAppData.setHolStaffSectionCode(obj.getHolStaffSectionCode());
					fldAppData.setHolStaffSectionYear(obj.getHolStaffSectionYear());
					fldSession.updateFldApp(Constants.STAFF_CODE_SYSTEM, accessLevel, fldAppData);
				}
			}

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				String histId = obj.getApplicationId() + " " + obj.getApplicationVersion();
				histSession.createHistData(HIST_ENTITY_NAME, histId, HIST_OPERATION_CREATE, null);
			}

			return obj.getApplicationId() + " " + obj.getApplicationVersion();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)作成:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#updateApGetInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_int.ApGetInt)
	 */
	public void updateApGetInt(String loginStaffCode, String accessLevel, ApGetInt obj) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateApGetInt(loginStaffCode, accessLevel, obj, true, true, true, true, null);
	}

	/**
	 * 取得申請更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isFldAppUpdate 資産紐付け更新有無  true:更新する false:更新しない
	 * @param isDisplayVersionUpdate 改訂番号更新有無 true:改訂番号をインクリメント false:改訂番号は更新しない
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @param operation 履歴作成時のオペレーション
	 * @throws AppException
	 */
	private void updateApGetInt(String loginStaffCode, String accessLevel, ApGetInt obj, boolean isLineUpdate, boolean isFldAppUpdate, boolean isDisplayVersionUpdate, boolean isHistCreate, String operation) throws AppException {
		try {
			ApGetIntDAO apGetIntDAO = new ApGetIntDAO();
			ApGetInt apGetIntOld = getApGetInt(obj.getApplicationId(), obj.getApplicationVersion(), true); // 現データの取得(データロック)

			//////////////////////////////////// 固定値セット
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != apGetIntOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// バリデーション(登録画面更新の際のみ：連携等による更新時は行わない)
			if(isLineUpdate) errMsg.append(validateApGetInt(loginStaffCode, accessLevel, obj, apGetIntOld));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新
			// バージョン・改訂番号
			obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
			if(isDisplayVersionUpdate) obj.setDisplayVersion(Function.nvl(obj.getDisplayVersion(), Integer.valueOf(1)) + 1);

			apGetIntDAO.updateApGetInt(obj);

			if(isLineUpdate) {
				// 明細を一度削除
				deleteLine(loginStaffCode, obj, apGetIntDAO);

				createLine(loginStaffCode, obj, apGetIntDAO, obj.getApplicationId()); // 明細データ作成
			}

			// 申請ステータスが承認済の場合、資産紐付け情報作成
			String apStatus = Function.nvl(obj.getApStatus(), "");
			if(apStatus.equals(Constants.AP_STATUS_GET_INT_APPROVE) && isFldAppUpdate){
				PpfsFldApp fldAppData = fldSession.getFldApp(loginStaffCode, accessLevel, obj.getApCompanyCode(), obj.getApplicationId());
				if(fldAppData == null){
					fldAppData = new PpfsFldApp();
				}

				 // 本勘定固定資産が無い場合は資産紐付け情報を更新する
				List<PpfsFldSR> ppfsListH = fldAppData.getPpfsListH();
				if(ppfsListH == null || ppfsListH.size() == 0){
					fldAppData.setApplicationId(obj.getApplicationId());
					fldAppData.setApGetType(obj.getApGetType());
					fldAppData.setHolCompanyCode(obj.getHolCompanyCode());
					fldAppData.setHolSectionCode(obj.getHolSectionCode());
					fldAppData.setHolSectionYear(obj.getHolSectionYear());
					fldAppData.setHolStaffCode(obj.getHolStaffCode());
					fldAppData.setHolStaffCompanyCode(obj.getHolStaffCompanyCode());
					fldAppData.setHolStaffSectionCode(obj.getHolStaffSectionCode());
					fldAppData.setHolStaffSectionYear(obj.getHolStaffSectionYear());
					fldSession.updateFldApp(Constants.STAFF_CODE_SYSTEM, accessLevel, fldAppData);
				}
			}

			//////////////////////////////////// 前回バージョンからの変更項目セット
			updateVerUpColumnName(obj, apGetIntDAO);

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				String lineChangeColumnName = null;
				if(isLineUpdate) {
					// 明細変更項目取得
					lineChangeColumnName = getLineChangeColumnName(obj, apGetIntOld);
				}

				String histId = obj.getApplicationId() + " " + obj.getApplicationVersion();
				histSession.createHistData(HIST_ENTITY_NAME, histId, (operation == null ? HIST_OPERATION_UPDATE : operation), lineChangeColumnName);
			}
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)更新:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#updateApGetInt(jp.co.ctcg.easset.dto.ap_get_int.ApGetInt)
	 */
	public void updateApGetInt(ApGetInt obj) throws SQLException {
		ApGetIntDAO apGetIntDAO = new ApGetIntDAO();
		apGetIntDAO.updateApGetInt(obj);
	}

	/**
	 * 前回バージョンからの変更項目セット
	 * @param obj 取得申請データ
	 * @param apGetIntDAO DAO
	 * @throws SQLException
	 */
	private void updateVerUpColumnName(ApGetInt obj, ApGetIntDAO apGetIntDAO) throws SQLException {
		if(!Function.nvl(obj.getVerUpType(), "").equals(Constants.AP_GET_INT_VER_UP_TYPE_NONE)) {
			// 旧バージョンデータ取得
			DecimalFormat df = new DecimalFormat("00");
			String oldVersion = df.format((Integer.parseInt(obj.getApplicationVersion()) - 1));
			ApGetInt objOldVer = getApGetInt(obj.getApplicationId(), oldVersion, true); // 旧Verデータの取得

			String verUpColHead = null;
			String verUpColLine = null;
			// ヘッダ変更項目取得
			verUpColHead = apGetIntDAO.selectVerUpColumnName(HIST_ENTITY_NAME, obj.getApplicationId(), oldVersion, obj.getApplicationVersion());
			// 明細変更項目取得
			verUpColLine = getLineChangeColumnName(obj, objOldVer);

			if(verUpColHead != null || verUpColLine != null) {
				StringBuffer verUpCol = new StringBuffer();
				verUpCol.append(Function.nvl(verUpColHead, ""));
				if(verUpCol.length() != 0 && !Function.nvl(verUpColLine, "").equals("")) verUpCol.append(",");
				verUpCol.append(Function.nvl(verUpColLine, ""));

				obj.setVerUpColumnName(verUpCol.toString()); // 項目セット

				apGetIntDAO.updateApGetInt(obj); // 更新
			}
		}
	}

	/**
	 * 明細変更項目取得
	 * @param obj 変更後データ
	 * @param objOld 変更前データ
	 * @return 明細変更項目
	 */
	private String getLineChangeColumnName(ApGetInt obj, ApGetInt objOld) {
		StringBuffer lineChangeColumnName = new StringBuffer();

		// 明細変更確認
		// 前回バージョンとの比較時も使用するためapplicationVersionは明示的に比較から除外
		if(Function.isListChange(obj.getApGetIntLineFldList(), objOld.getApGetIntLineFldList(), new String[]{"applicationVersion"})) {
			if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
			lineChangeColumnName.append("資産明細");
		}
		if(Function.isListChange(obj.getApGetIntLineDevSchList(), objOld.getApGetIntLineDevSchList(), new String[]{"applicationVersion"})) {
			if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
			lineChangeColumnName.append("開発スケジュール明細");
		}
		if(Function.isListChange(obj.getApGetIntLineOtrCostList(), objOld.getApGetIntLineOtrCostList(), new String[]{"applicationVersion"})) {
			if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
			lineChangeColumnName.append("その他費用明細");
		}
		if(Function.isListChange(obj.getApGetIntLineProfEstList(), objOld.getApGetIntLineProfEstList(), new String[]{"applicationVersion"})) {
			if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
			lineChangeColumnName.append("利益予測明細");
		}
		if(Function.isListChange(obj.getApGetIntLineAttList(), objOld.getApGetIntLineAttList(), new String[]{"applicationVersion"})) {
			if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
			lineChangeColumnName.append("添付明細");
		}
		if(Function.isListChange(obj.getApGetIntLineCostSecList(), objOld.getApGetIntLineCostSecList(), new String[]{"applicationVersion"})) {
			if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
			lineChangeColumnName.append("経費負担部署明細");
		}

		return lineChangeColumnName.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#applyApGetInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_int.ApGetInt, java.lang.Boolean)
	 */
	public String applyApGetInt(String loginStaffCode, String accessLevel, ApGetInt obj, Boolean isNew) throws AppException {
		String ret = null;

		//////////////////////////////////// 新規 or 更新呼び出し
		if(isNew) { // 新規
			ret = createApGetInt(loginStaffCode, accessLevel, obj, false);
		} else { // 更新
			// 更新コメント
			obj.setUpdateComment(null);

			ret = obj.getApplicationId();

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			// ステータス更新前バリデーション
			String errMsg = validateApGetInt(loginStaffCode, accessLevel, obj, null);
			if(errMsg.length() > 0) throw new AppException(errMsg);
		}

		//////////////////////////////////// ステータス更新&ステータス更新後バリデーション
		obj.setApStatus(Constants.AP_STATUS_GET_INT_APPLY);
		String errMsg = validateApGetInt(loginStaffCode, accessLevel, obj, null);
		if(errMsg.length() > 0) throw new AppException(errMsg);

		//////////////////////////////////// 申請
		Long eappId = null;
		eappId = callEappService(obj); // e申請連携

		// e申請IDを更新
		obj.setEappId(eappId);

		if(isNew) { // 新規
			updateApGetInt(loginStaffCode, accessLevel, obj, false, true, false, false, null);

			String histId = obj.getApplicationId() + " " + obj.getApplicationVersion();
			histSession.createHistData(HIST_ENTITY_NAME, histId, HIST_OPERATION_APPLY, null); // 履歴作成
		} else {
			updateApGetInt(loginStaffCode, accessLevel, obj, true, true, true, true, HIST_OPERATION_APPLY);
		}

		return ret;
	}

	/**
	 * e申請サービス呼び出し
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(ApGetInt obj) throws AppException {
		// e申請WebServiceエンドポイント取得
		CodeName codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_GET_INT, null, null);
		String eappWsEndpoint = codeNameUrl.getValue1(); // e申請WebSerivceエンドポイント
		String eAssetUrl = codeNameUrl.getValue2(); // e申請インラインフレーム画面表示用のeAssetUrl
		String eappStopMessage = codeNameUrl.getValue3(); // e申請との連携停止期間中のエラーメッセージ

		Long eappId = null;

		if(!Function.nvl(eappWsEndpoint, "").equals("")) { // e申請WebServiceエンドポイントが空白(PG検証用)の場合は連携無し
			// e申請との連携停止期間中のエラーメッセージ
			if(!eappWsEndpoint.startsWith("http")){
				throw new AppException(eappStopMessage);
			}

			try {
				eAssetUrl += "&amp;companyCode=" + obj.getApCompanyCode();
				eAssetUrl += "&amp;param2="; // e申請から書類IDが指定される

				// 金額範囲マスタ取得
				CodeName codeNameRange;
				codeNameRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_GET_INT_AMOUNT_RANGE, obj.getApGetAmountRange(), obj.getApCompanyCode(), null);

				// タイトルセット
				String title = codeNameRange.getValue4();
				if(codeNameRange.getValue5() != null) {
					title = title + "\\n" + codeNameRange.getValue5();
				} else {
					title = "\\n" + title;
				}

				// サブタイトル(金額)セット
				String subTitle = Function.nvl(obj.getApGetAmountRangeName(), "");
				if(codeNameRange.getValue6() != null) subTitle = codeNameRange.getValue6() + " " + subTitle;
				if(!Function.nvl(subTitle, "").equals("")) subTitle = "(" + subTitle + ")";

				//////////////////////////////////// 経路設定
				// e申請経路担当情報取得
				List<CodeName> codeNameEappChargeList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_GET_INT, null, obj.getApCompanyCode(), null);
				CodeName codeNameEappCharge = codeNameEappChargeList.get(0);
				CodeName codeNameEappCharge2 = null;
				if(codeNameEappChargeList.size() > 1) codeNameEappCharge2 = codeNameEappChargeList.get(1);

				// e申請経路権限情報取得
				List<String> attributeParam = new ArrayList<String>();
				String eappRouteKey = Function.nvl(codeNameRange.getValue8(), "XXXXXXXXXXXX");
				attributeParam.add(eappRouteKey);
				CodeName codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_INT, null, obj.getApCompanyCode(), attributeParam);

				if(codeNameEappRoute == null) codeNameEappRoute = new CodeName();

				// 特殊経路判定
				// 稟議書経営会議承認済
				if(Function.nvl(obj.getSpecialApproveFlag(), "").equals(Constants.FLAG_YES)) {
					attributeParam.clear();
					attributeParam.add(eappRouteKey.substring(0, 2) + Constants.EAPP_ROUTE_AUTH_AP_GET_INT_SPECIAL_APPROVE_SUFFIX);
					CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_INT, null, obj.getApCompanyCode(), attributeParam);
					if(cn != null) codeNameEappRoute = cn; // 経営会議承認済用の経路に置き換え
				}

				// 特殊経路判定
				// 財経承認のみ
				if(Function.nvl(obj.getVerUpType(), "").equals("2")) {
					attributeParam.clear();
					attributeParam.add(eappRouteKey.substring(0, 2) + Constants.EAPP_ROUTE_AUTH_AP_GET_INT_VER_UP_TYPE2_APPROVE_SUFFIX);
					CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_INT, null, obj.getApCompanyCode(), attributeParam);
					if(cn != null) codeNameEappRoute = cn; // 財経承認のみ用の経路に置き換え
				}

				 // 要CIO審査
				if(Function.nvl(codeNameEappRoute.getValue1(), "").endsWith("-1") && Function.nvl(obj.getApNeedCioJudgeFlag(), "").equals(Constants.FLAG_YES)) {
					eappRouteKey = codeNameEappRoute.getValue1().replaceAll("-1$", "-2"); // 経路をCIO審査込みの経路に変更
					attributeParam.clear();
					attributeParam.add(eappRouteKey);
					codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_INT, null, obj.getApCompanyCode(), attributeParam);

					// 申請部署が情報システム部門かどうかを判別
					ArrayList<String> param = new ArrayList<String>();
					param.add(null); // value1
					param.add(Function.nvl(obj.getApSectionYear(), -1).toString()); // value2
					param.add(null); // value3
					param.add(Function.getHigherSectionCode(Function.nvl(obj.getApSectionCode(), "XXXXXX"))); // value4
					param.add("1"); // value5
					CodeName systemSection = masterSession.getCodeName(Constants.CATEGORY_CODE_LONG_TIME_SECTION, null, obj.getApCompanyCode(), param);

					// 情報システム部門の場合、情シス担当役員 = 担当役員のため
					// 担当役員承認が必要な場合の審査ステップを一つ除外
					if(systemSection != null) {
						codeNameEappRoute.setValue17(null);
					}
				}

				// 減価償却プロジェクトコード入力有の場合の経路を追加
				if(!Function.nvl(obj.getCostDepPrjCode(), "").equals("")) {
					attributeParam.clear();
					attributeParam.add(Constants.EAPP_ROUTE_AUTH_AP_GET_INT_PROJECT);
					CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_INT, null, obj.getApCompanyCode(), attributeParam);
					if(cn != null) {
						for(int i = 0; i < Constants.MAX_EAPP_ROUTE_COUNT_AP_GET_INT; i++) {
							Object val = PropertyUtils.getProperty(cn, "value" + (i + 3)); // 申請経路はvalue3～
							if(val != null) PropertyUtils.setProperty(codeNameEappRoute, "value" + (i + 3), val);
						}
					}
				}

				// 課長承認不要
				if(Function.nvl(obj.getApSkipApproveFlag(), "").equals(Constants.FLAG_YES)) {
					codeNameEappRoute.setValue3(null); // 課長承認経路削除
				}

				// 経路有無判定
				boolean routeExists = false;

				// 経路パラメータセット
				List<String> applyRouteAuthDispList = new ArrayList<String>();
				List<String> applyRouteChargeDispList = new ArrayList<String>();
				List<String> applyRouteDispTypeList = new ArrayList<String>();
				List<String> approveRouteAuthDispList = new ArrayList<String>();
				List<String> approveRouteChargeDispList = new ArrayList<String>();
				List<String> approveRouteDispTypeList = new ArrayList<String>();
				List<String> acceptRouteAuthDispList = new ArrayList<String>();
				List<String> acceptRouteChargeDispList = new ArrayList<String>();
				List<String> acceptRouteDispTypeList = new ArrayList<String>();

				for(int i = 0; i < Constants.MAX_EAPP_ROUTE_COUNT_AP_GET_INT; i++) {
					List<String> authDispList;
					List<String> chargeDispList;
					List<String> dispTypeList;

					// 対象経路判別
					if(i <= 2) {									// 申請部経路
						authDispList = applyRouteAuthDispList;
						chargeDispList = applyRouteChargeDispList;
						dispTypeList = applyRouteDispTypeList;
					} else if((3 <= i && i <= 6) || 12 <= i) {// 受付経路
						authDispList = acceptRouteAuthDispList;
						chargeDispList = acceptRouteChargeDispList;
						dispTypeList = acceptRouteDispTypeList;
					} else {										// 承認経路
						authDispList = approveRouteAuthDispList;
						chargeDispList = approveRouteChargeDispList;
						dispTypeList = approveRouteDispTypeList;
					}

					Object auth = PropertyUtils.getProperty(codeNameEappRoute, "value" + (i + 3)); // 申請経路はvalue3～
					if(!Function.nvl(auth,"").equals("")) {
						Object charge = null;
						authDispList.add(auth.toString());

						if(auth.toString().equals(Constants.EAPP_ROUTE_DISP_TYPE_CONFIRM_NAME)) { // 確認経路
							charge = PropertyUtils.getProperty(codeNameEappCharge, "value" + (i + 3)); // 申請経路はvalue3～
							dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_CONFIRM);
						} else if(auth.toString().equals(Constants.EAPP_ROUTE_DISP_TYPE_APPROVE2_NAME)) { // 承認経路2
							charge = PropertyUtils.getProperty(codeNameEappCharge2, "value" + (i + 3)); // 申請経路はvalue3～
							dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_APPROVE2);
						} else { // 承認・受付経路
							charge = PropertyUtils.getProperty(codeNameEappCharge, "value" + (i + 3)); // 申請経路はvalue3～
							dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_APPROVE);
						}

						chargeDispList.add(charge.toString());

						routeExists = true;
					} else { // 経路無し
						authDispList.add(null);
						chargeDispList.add(null);
						dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_NONE);
					}
				}

				if(routeExists) {
					//////////////////////////////////// e申請サービス呼び出し
					String eappIdStr = null;
					try {
						ApGetIntService service = new ApGetIntServiceProxy(eappWsEndpoint);
						eappIdStr = service.apply(
								obj.getApplicationId()								// applicationId
								, obj.getApplicationVersion()						// applicationVersion
								, Constants.AP_TYPE_GET_INT							// applicationType
								, obj.getApCompanyCode()							// companyCode
								, obj.getApSectionCode()							// apSectionCode
								, obj.getApCreateStaffCode()						// apCreateStaffCode
								, obj.getApStaffCode()								// apStaffCode
								, obj.getApTel()									// apTel
								, title												// apTitle
								, subTitle											// apSubTitle
								, title.replaceAll("\\\\n", " ") + " " + subTitle	// apListTitle
								, eAssetUrl											// eAssetUrl
								, applyRouteAuthDispList.toArray(new String[0])		// applyRouteAuthDispArray
								, applyRouteChargeDispList.toArray(new String[0])	// applyRouteChargeDispArray
								, applyRouteDispTypeList.toArray(new String[0])		// applyRouteDispTypeArray
								, approveRouteAuthDispList.toArray(new String[0])	// approveRouteAuthDispArray
								, approveRouteChargeDispList.toArray(new String[0])	// approveRouteChargeDispArray
								, approveRouteDispTypeList.toArray(new String[0])	// approveRouteDispTypeArray
								, acceptRouteAuthDispList.toArray(new String[0])	// acceptRouteAuthDispArray
								, acceptRouteChargeDispList.toArray(new String[0])	// acceptRouteChargeDispArray
								, acceptRouteDispTypeList.toArray(new String[0])	// acceptRouteDispTypeArray
								, (Function.nvl(obj.getSpecialApproveFlag(), "").equals(Constants.FLAG_YES) ? Function.toCalendar(obj.getSpecialApproveDate()) : null) // specialApproveDate
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
							Long getEAppIdService = eAssetService.getEAppId(obj.getApplicationId());
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

			} catch (IllegalAccessException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請連携パラメータ作成"), e);
			} catch (InvocationTargetException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請連携パラメータ作成"), e);
			} catch (NoSuchMethodException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請連携パラメータ作成"), e);
			}
		}

		return eappId;
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#deleteApGetInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_int.ApGetInt)
	 */
	public void deleteApGetInt(String loginStaffCode, String accessLevel, ApGetInt obj) throws AppException {
		try {
			ApGetIntDAO apGetIntDAO = new ApGetIntDAO();
			ApGetInt apGetIntOld = getApGetInt(obj.getApplicationId(), obj.getApplicationVersion(), true); // 現データの取得(データロック)

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != apGetIntOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新(履歴作成用にバージョンアップ)
			// 現データを更新に使用
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			apGetIntOld.setUpdateDate(sysdate);
			apGetIntOld.setUpdateStaffCode(loginStaffCode);

			// バージョン・改訂番号
			apGetIntOld.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
			apGetIntOld.setDisplayVersion(Function.nvl(obj.getDisplayVersion(), Integer.valueOf(1)) + 1);

			// 更新コメント
			apGetIntOld.setUpdateComment(null);

			apGetIntDAO.updateApGetInt(apGetIntOld);

			//////////////////////////////////// 履歴作成
			String histId = obj.getApplicationId() + " " + obj.getApplicationVersion();
			histSession.createHistData(HIST_ENTITY_NAME, histId, HIST_OPERATION_DELETE, null);

			//////////////////////////////////// データ削除
			apGetIntDAO.deleteApGetInt(obj.getApplicationId(), obj.getApplicationVersion());
			deleteLine(loginStaffCode, obj, apGetIntDAO);

			// バージョンアップタイプ　1:追加および変更,2:変更(財経承認のみ)
			String verUpType = Function.nvl(obj.getVerUpType(), "");
			if(verUpType.equals("1") || verUpType.equals("2")){
				 // 旧バージョンの取得申請書を有効にする
				int versionNum = (Integer.valueOf(Function.nvl(obj.getApplicationVersion(), "0"))).intValue();
				if(versionNum > 1){
					versionNum--;
					String applicationVersion = Integer.toString(versionNum);
					if(applicationVersion.length() < 2){
						applicationVersion = "0" + applicationVersion;
					}
					ApGetInt apGetIntData = getApGetInt(obj.getApplicationId(), applicationVersion);
					if(apGetIntData != null){
						// 旧ver申請のversion（内部ロック処理で使用）は新verと旧verの大きいほうで更新する
						// (新ver作成 -> 新ver削除 -> 再度新ver作成を行った場合の同一applicationVersionでの重複回避のため)
						int oldVer = Function.nvl(apGetIntData.getVersion(), Integer.valueOf(1));
						int delVer = Function.nvl(apGetIntOld.getVersion(), Integer.valueOf(1));

						if(delVer > oldVer) oldVer = delVer;
						apGetIntData.setVersion(Integer.valueOf(oldVer));

						apGetIntData.setUpdateDate(sysdate);
						apGetIntData.setUpdateStaffCode(loginStaffCode);
						apGetIntData.setActiveFlag("1");
						apGetIntDAO.updateApGetInt(apGetIntData);
					}
				}
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請(無形)削除"), e);
		}
	}

	/*
	 * 明細データの作成
	 */
	private void createLine(String loginStaffCode, ApGetInt obj, ApGetIntDAO apGetIntDAO, String applicationId) throws SQLException, IOException {
		Date sysdate = new Date(); // システム日付取得

		// 資産明細作成
		List<ApGetIntLineFld> fldList = obj.getApGetIntLineFldList();
		if(fldList != null && fldList.size() > 0) {
			for(int i = 0; i < fldList.size(); i++) {
				ApGetIntLineFld row = fldList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setApplicationVersion(obj.getApplicationVersion()); // バージョンのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apGetIntDAO.insertApGetIntLineFld(row);
			}
		}

		// 開発スケジュール明細作成
		List<ApGetIntLineDevSch> devSchList = obj.getApGetIntLineDevSchList();
		if(devSchList != null && devSchList.size() > 0) {
			for(int i = 0; i < devSchList.size(); i++) {
				ApGetIntLineDevSch row = devSchList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setApplicationVersion(obj.getApplicationVersion()); // バージョンのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apGetIntDAO.insertApGetIntLineDevSch(row);
			}
		}

		// その他費用明細作成
		List<ApGetIntLineOtrCost> otrCostList = obj.getApGetIntLineOtrCostList();
		if(otrCostList != null && otrCostList.size() > 0) {
			for(int i = 0; i < otrCostList.size(); i++) {
				ApGetIntLineOtrCost row = otrCostList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setApplicationVersion(obj.getApplicationVersion()); // バージョンのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apGetIntDAO.insertApGetIntLineOtrCost(row);
			}
		}

		// 利益予測明細作成
		List<ApGetIntLineProfEst> profEstList = obj.getApGetIntLineProfEstList();
		if(profEstList != null && profEstList.size() > 0) {
			for(int i = 0; i < profEstList.size(); i++) {
				ApGetIntLineProfEst row = profEstList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setApplicationVersion(obj.getApplicationVersion()); // バージョンのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apGetIntDAO.insertApGetIntLineProfEst(row);
			}
		}

		// 添付明細作成
		List<ApGetIntLineAtt> attList = obj.getApGetIntLineAttList();
		if(attList != null && attList.size() > 0) {
			for(int i = 0; i < attList.size(); i++) {
				ApGetIntLineAtt row = attList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setApplicationVersion(obj.getApplicationVersion()); // バージョンのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				if(row.getAttFileId() == null) { // 一時領域にのみファイルが存在する（保存前）
					// 一時領域 ⇒ 本領域にファイルコピー
					masterSession.fileCommit(row.getAttFileIdTmp(), FILE_SAVE_ENTITY_NAME, applicationId);
					row.setAttFileId(row.getAttFileIdTmp());
				}

				apGetIntDAO.insertApGetIntLineAtt(row);
			}
		}

		// 経費負担部署明細作成
		List<ApGetIntLineCostSec> costSecList = obj.getApGetIntLineCostSecList();
		if(costSecList != null && costSecList.size() > 0) {
			for(int i = 0; i < costSecList.size(); i++) {
				ApGetIntLineCostSec row = costSecList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setApplicationVersion(obj.getApplicationVersion()); // バージョンのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apGetIntDAO.insertApGetIntLineCostSec(row);
			}
		}
	}

	/*
	 * 明細データの削除
	 */
	private void deleteLine(String loginStaffCode, ApGetInt obj, ApGetIntDAO apGetIntDAO) throws SQLException {
		apGetIntDAO.deleteApGetIntLineFld(obj.getApplicationId(), obj.getApplicationVersion());
		apGetIntDAO.deleteApGetIntLineDevSch(obj.getApplicationId(), obj.getApplicationVersion());
		apGetIntDAO.deleteApGetIntLineOtrCost(obj.getApplicationId(), obj.getApplicationVersion());
		apGetIntDAO.deleteApGetIntLineProfEst(obj.getApplicationId(), obj.getApplicationVersion());
		apGetIntDAO.deleteApGetIntLineAtt(obj.getApplicationId(), obj.getApplicationVersion());
		apGetIntDAO.deleteApGetIntLineCostSec(obj.getApplicationId(), obj.getApplicationVersion());
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#createApGetIntPdf(java.util.List, java.lang.Boolean)
	 */
	public String createApGetIntPdf(List<ApGetInt> applicationList, Boolean lineOutputFlag) {
		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();

		// OBJ => SQL用のID,Versionリストに変換
		List<String> applicationIdVerList = new ArrayList<String>();
		for(Iterator<ApGetInt> iter = applicationList.iterator(); iter.hasNext();) {
			ApGetInt obj = iter.next();
			applicationIdVerList.add("('" + obj.getApplicationId() + "','" + obj.getApplicationVersion() + "')");
		}

		reportParam.put("applicationIdWhere", Function.getInCondition("(nagi.APPLICATION_ID, nagi.APPLICATION_VERSION)", applicationIdVerList, false));
		if(lineOutputFlag != null && lineOutputFlag.booleanValue()) reportParam.put("lineOutputFlag", "1");

		// PDF生成
		PdfExporter exporter = new PdfExporter();

		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApGetInt.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#createApGetIntHistPdf(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public String createApGetIntHistPdf(String applicationId, String applicationVersion, Integer version) {
		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();

		reportParam.put("applicationIdWhere", "nagi.APPLICATION_ID = '" + applicationId + "' AND nagi.APPLICATION_VERSION = '" + applicationVersion + "'");
		reportParam.put("version", version);
		reportParam.put("lineOutputFlag", "1");

		// PDF生成
		PdfExporter exporter = new PdfExporter();

		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApGetInt.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#approveApGetInt(java.lang.Long, java.lang.String)
	 */
	public void approveApGetInt(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApGetInt apGetInt = getApGetInt(eappId, true);

		CodeName appRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_GET_INT, null, apGetInt.getApCompanyCode(), null);

		// 取得申請をe申請で承認する会社は承認済に更新
		if(appRoute != null && Function.nvl(appRoute.getValue2(), "").equals(Constants.FLAG_YES)) {
			// ステータス
			apGetInt.setApStatus(Constants.AP_STATUS_GET_INT_APPROVE);
			// 更新コメント
			apGetInt.setUpdateComment(null);
			// 承認日
			apGetInt.setApproveDate(new Date());

			updateApGetInt(execStaffCode, null, apGetInt, false, true, false, true, HIST_OPERATION_APPROVE);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#cancelApplyApGetInt(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyApGetInt(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApGetInt apGetInt = getApGetInt(eappId, true);

		if(apGetInt != null) {
			// ステータス
			apGetInt.setApStatus(Constants.AP_STATUS_GET_INT_NOAPPLY);
			// 更新コメント
			apGetInt.setUpdateComment(null);
			apGetInt.setEappId(null); // 書類IDクリア

			updateApGetInt(execStaffCode, null, apGetInt, false, true, false, true, HIST_OPERATION_CANCEL_APPLY);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#rejectApGetInt(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectApGetInt(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		String histOperation;

		//////////////////////////////////// 更新
		ApGetInt apGetInt = getApGetInt(eappId, true);

		if(apGetInt != null) {
			// ステータス
			if(Function.nvl(rejectType, "").equals(Constants.AP_REJECT_TYPE_REJECT)) { // 却下
				apGetInt.setApStatus(Constants.AP_STATUS_GET_INT_REJECT);
				histOperation = HIST_OPERATION_REJECT;
			} else { // 差戻し
				apGetInt.setApStatus(Constants.AP_STATUS_GET_INT_SENDBACK);
				apGetInt.setEappId(null); // 書類IDクリア
				histOperation = HIST_OPERATION_SENDBACK;
			}

			// コメント
			apGetInt.setUpdateComment(rejectReason);

			updateApGetInt(execStaffCode, null, apGetInt, false, true, false, true, histOperation);
		}
	}

	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateApGetInt(String loginStaffCode, String accessLevel, ApGetInt obj, ApGetInt objOld) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 項目定義バリデーション
		int apStatus = obj.getApStatus() == null ? null : Integer.valueOf(obj.getApStatus());
		String apGetType = Function.nvl(obj.getApGetType(), "");// 取得形態

		if(!Function.isAccessLevelAdmin(accessLevel)) {			// 全社権限ではない場合
			apStatus += 6;
		}
		if(apGetType.equals(Constants.AP_GET_TYPE_INT2)) {		// 市販目的ソフトウェア
			apStatus += 24;
		}
		if(Function.nvl(obj.getVerUpType(), "").equals("2")) {	// 変更(財経承認のみ)
			apStatus += 12;
		}

		// ヘッダ
		errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, "NEA_AP_GET_INT", obj, apStatus, null));

		// 明細
		List<ApGetIntLineFld> fldLine = obj.getApGetIntLineFldList();
		if(fldLine != null && fldLine.size() > 0) {
			for(int i = 0; i < fldLine.size(); i++) {
				ApGetIntLineFld fldObj = fldLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, "NEA_AP_GET_INT_LINE_FLD", fldObj, apStatus, null));
			}
		}

		List<ApGetIntLineDevSch> devSchLine = obj.getApGetIntLineDevSchList();
		if(devSchLine != null && devSchLine.size() > 0) {
			for(int i = 0; i < devSchLine.size(); i++) {
				ApGetIntLineDevSch devSchObj = devSchLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, "NEA_AP_GET_INT_LINE_DEV_SCH", devSchObj, apStatus, null));
			}
		}

		List<ApGetIntLineOtrCost> otrCostLine = obj.getApGetIntLineOtrCostList();
		if(otrCostLine != null && otrCostLine.size() > 0) {
			for(int i = 0; i < otrCostLine.size(); i++) {
				ApGetIntLineOtrCost otrCostObj = otrCostLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, "NEA_AP_GET_INT_LINE_OTR_COST", otrCostObj, apStatus, null));
			}
		}

		List<ApGetIntLineProfEst> profEstLine = obj.getApGetIntLineProfEstList();
		if(profEstLine != null && profEstLine.size() > 0) {
			for(int i = 0; i < profEstLine.size(); i++) {
				ApGetIntLineProfEst profEstObj = profEstLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, "NEA_AP_GET_INT_LINE_PROF_EST", profEstObj, apStatus, null));
			}
		}

		List<ApGetIntLineAtt> attLine = obj.getApGetIntLineAttList();
		if(attLine != null && attLine.size() > 0) {
			for(int i = 0; i < attLine.size(); i++) {
				ApGetIntLineAtt attObj = attLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, "NEA_AP_GET_INT_LINE_ATT", attObj, apStatus, null));
			}
		}

		List<ApGetIntLineCostSec> costSecLine = obj.getApGetIntLineCostSecList();
		if(costSecLine != null && costSecLine.size() > 0) {
			for(int i = 0; i < costSecLine.size(); i++) {
				ApGetIntLineCostSec costSecObj = costSecLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_INT, "NEA_AP_GET_INT_LINE_COST_SEC", costSecObj, apStatus, null));
			}
		}

		//////////////////////////////////// 条件付必須バリデーション
		String stat = Function.nvl(obj.getApStatus(), ""); // ステータス

		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_GET_INT_APPROVE) &&
		   !stat.equals(Constants.AP_STATUS_GET_INT_REJECT) &&
		   !stat.equals(Constants.AP_STATUS_GET_INT_CANCEL)) {
			// 承認日
			if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) {
				if(obj.getSpecialApproveDate() == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "承認日", "稟議書・経営会議等承認済がチェックされている場合は"));
				}
			}

			// 案件グループ
			if(Function.nvl(obj.getAstCloudType(), "").equals(Constants.CLOUD_TYPE_CLOUD)) {
				if(Function.nvl(obj.getAstGroupCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "資産概要-案件グループ", "クラウド案件の場合は"));
				}
			}

			// 資産明細
			if(apGetType.equals(Constants.AP_GET_TYPE_INT1) || apGetType.equals(Constants.AP_GET_TYPE_INT3)){
				for(int i = 0; i < fldLine.size(); i++) {
					ApGetIntLineFld fldObj = fldLine.get(i);
					// ライセンス
					if(Function.nvl(fldObj.getAstCategoryType(), "").equals(Constants.AP_GET_INT_LINE_AST_CATEGORY_TYPE_LIC)){
						// 期間ライセンス
						if(Function.nvl(fldObj.getAstTermFlag(), "").equals(Constants.FLAG_YES)) {
							if(fldObj.getAstTermYear() == null) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "資産明細[No" + fldObj.getLineSeq() + "]-期間(年)"));
							}
						}
					}
					// ライセンス OR 外部委託
					if(Function.nvl(fldObj.getAstCategoryType(), "").equals(Constants.AP_GET_INT_LINE_AST_CATEGORY_TYPE_LIC)
						|| Function.nvl(fldObj.getAstCategoryCode(), "").equals(Constants.AP_GET_INT_LINE_AST_CATEGORY_CODE_OUT)){
						if(Function.nvl(fldObj.getAstPurCompanyName(), "").equals("") ) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "資産明細[No" + fldObj.getLineSeq() + "]-仕入先"));
						}
					}
				}
			}

			String useCostSecFlag = Function.nvl(obj.getApGetAmountRangeUseCostSecType(), "");

			// 販管／原価区分
			if(useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_BOTH)
				|| useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_TYPE)) {
				if(Function.nvl(obj.getCostType(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "経費負担-販売管理費/原価区分"));
				}

				// 原価でプロジェクトコードの指定無し（未定でもない）
/*
				if(Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)
					&& Function.nvl(obj.getCostDepPrjCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "経費負担-減価償却プロジェクトコード"));
				}
*/
			}
			// 経費負担部署
			if(useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_BOTH)
				|| useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_SEC)) {
				if(costSecLine == null || costSecLine.size() == 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "経費負担-経費負担部署", "1件以上"));
				}

				if(useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_BOTH)) {
					if(Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)) {
						if(costSecLine != null && costSecLine.size() > 1) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-経費負担部署", "販売管理費/原価区分に\"原価\"を指定した場合1件しか入力できません。"));
						}
					}
				}
			}

		}

		// 明細件数(市販目的ソフトウェア以外)
		if(!apGetType.equals(Constants.AP_GET_TYPE_INT2) && (fldLine == null || fldLine.size() == 0)) {
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "資産明細", "1件以上"));
		}

		//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)

		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_GET_INT_APPROVE) &&
		   !stat.equals(Constants.AP_STATUS_GET_INT_REJECT) &&
		   !stat.equals(Constants.AP_STATUS_GET_INT_CANCEL)) {

			// 申請者
			if(!Function.nvl(obj.getApStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_INT_APPROVE)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_INT_REJECT)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_INT_CANCEL)) {
					// 承認済・却下・取下げは退職社員OK
					if(masterSession.getStaff(obj.getApCompanyCode(), obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				} else {
					// 承認済・却下・取下げ以外は退職社員NG
					if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				}
			}

			// 案件グループ
			if(!Function.nvl(obj.getAstGroupCode(), "").equals("")) {
				Map<String, Object> lovParam = new HashMap<String, Object>();
				lovParam.put("companyCode", obj.getApCompanyCode());
				if(masterSession.getLovData("selectPpfs_Group_LOV", lovParam, obj.getAstGroupCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "取得-案件グループ"));
				}
			}
			// 減価償却プロジェクトコード
			if(!Function.nvl(obj.getCostDepPrjCode(), "").equals("")) {
				Map<String, Object> lovParam = new HashMap<String, Object>();
				lovParam.put("companyCode", obj.getApCompanyCode());
				lovParam.put("prjCategory", Constants.PROJECT_GATEGORY_COST_DEP);
				LovDataEx lovData = masterSession.getLovData("selectProject_LOV", lovParam, obj.getCostDepPrjCode());
				if(lovData == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担-減価償却プロジェクトコード"));
				}
/*
				else{
					//	CTC、CTCTのみ対象
					if(Function.nvl(obj.getApCompanyCode(), "").equals("001") || Function.nvl(obj.getApCompanyCode(), "").equals("017")){
						//	間接？
						if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_INDIRECTION)){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-減価償却プロジェクトコード", "間接プロジェクトが選択されています。"));
						}
						//	資産？
						if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_ASSET)){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-減価償却プロジェクトコード", "資産プロジェクトが選択されています。"));
						}
					}
				}
*/
			}

			// カレント年度取得
			CodeName currentYearCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
			int currentYear = Integer.valueOf(currentYearCodeName.getValue1());

			// 経費負担部課コード
			if(costSecLine != null && costSecLine.size() > 0) {
				for(int i = 0; i < costSecLine.size(); i++) {
					ApGetIntLineCostSec costSecObj = costSecLine.get(i);
					if(!Function.nvl(costSecObj.getCostSectionCode(), "").equals("")) {
						boolean currentYearErrorFlag = false;
						// 未申請・申請中
						if(stat.equals(Constants.AP_STATUS_BGN_INT_NOAPPLY) ||
						   stat.equals(Constants.AP_STATUS_BGN_INT_APPLY)) {
							Integer costSectionYear = costSecObj.getCostSectionYear();
							if(costSectionYear == null || costSectionYear.intValue() != currentYear) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "経費負担-経費負担部署[No" + costSecObj.getLineSeq() + "]-経費負担部課 : 当年度の経費負担部課を入力してください。"));
								currentYearErrorFlag = true;
							}
						}
						if(!currentYearErrorFlag && masterSession.getCostSection(costSecObj.getCostCompanyCode(), costSecObj.getCostSectionCode(), null, null) == null) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担-経費負担部署[No" + costSecObj.getLineSeq() + "]-経費負担部課"));
						}
					}
				}
			}
			// 資産計上部課コード
			if(!Function.nvl(obj.getCostSectionCode(), "").equals("")) {
				boolean currentYearErrorFlag = false;
				// 未申請・申請中
				if(stat.equals(Constants.AP_STATUS_BGN_INT_NOAPPLY) ||
				   stat.equals(Constants.AP_STATUS_BGN_INT_APPLY)) {
					Integer costSectionYear = obj.getCostSectionYear();
					if(costSectionYear == null || costSectionYear.intValue() != currentYear) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "経費負担-資産計上部課コード : 当年度の経費負担部課を入力してください。"));
						currentYearErrorFlag = true;
					}
				}
				if(!currentYearErrorFlag && masterSession.getCostSection(obj.getCostCompanyCode(), obj.getCostSectionCode(), null, null) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担-資産計上部課コード"));
				}
			}
			// 資産プロジェクトコード
			// 市販目的ソフトウェア
			if(apGetType.equals(Constants.AP_GET_TYPE_INT2)){
				if(!Function.nvl(obj.getMktAstProjectCode(), "").equals("")) {
					Map<String, Object> lovParam = new HashMap<String, Object>();
					lovParam.put("companyCode", obj.getApCompanyCode());
					lovParam.put("prjCategory", Constants.PROJECT_GATEGORY_AST);
					LovDataEx lovData = masterSession.getLovData("selectProject_LOV", lovParam, obj.getMktAstProjectCode());
					if(lovData == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "資産概要-資産プロジェクトコード"));
					}
/*
					else{
						//	CTC、CTCTのみ対象
						if(Function.nvl(obj.getApCompanyCode(), "").equals("001") || Function.nvl(obj.getApCompanyCode(), "").equals("017")){
							//	間接？
							if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_INDIRECTION)){
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産概要-資産プロジェクトコード", "間接プロジェクトが選択されています。"));
							}
							//	契約？
							if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_CONTRACT)){
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産概要-資産プロジェクトコード", "契約プロジェクトが選択されています。"));
							}
						}
					}
*/
				}
			}
			// 資産明細
			if(fldLine != null && fldLine.size() > 0) {
				Map<String, Object> lovParam = new HashMap<String, Object>();
				lovParam.put("companyCode", obj.getApCompanyCode());
				lovParam.put("prjCategory", Constants.PROJECT_GATEGORY_AST);
				for(int i = 0; i < fldLine.size(); i++) {
					ApGetIntLineFld fldObj = fldLine.get(i);
					if(!Function.nvl(fldObj.getAstPrjCode(), "").equals("")) {
						LovDataEx lovData = masterSession.getLovData("selectProject_LOV", lovParam, fldObj.getAstPrjCode());
						if(lovData == null) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "資産明細[No" + fldObj.getLineSeq() + "]-資産プロジェクトコード"));
						}
/*
						else{
							//	CTC、CTCTのみ対象
							if(Function.nvl(obj.getApCompanyCode(), "").equals("001") || Function.nvl(obj.getApCompanyCode(), "").equals("017")){
								//	間接？
								if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_INDIRECTION)){
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産明細[No" + fldObj.getLineSeq() + "]-資産プロジェクトコード", "間接プロジェクトが選択されています。"));
								}
								//	契約？
								if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_CONTRACT)){
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産明細[No" + fldObj.getLineSeq() + "]-資産プロジェクトコード", "契約プロジェクトが選択されています。"));
								}
							}
						}
*/
					}
				}
			}
		}

		// 無形管理担当者
		if(!Function.nvl(obj.getHolStaffCode(), "").equals("")) {
			if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_INT_APPROVE)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_INT_REJECT)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_INT_CANCEL)) {
					// 承認済・却下・取下げは退職社員OK
				// 承認済・却下・取下げ以外は退職社員NG
				if(masterSession.getStaff(obj.getApCompanyCode(), obj.getHolStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有-無形管理担当者"));
				}
			} else {
				// 承認済・却下・取下げ以外は退職社員NG
				if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getHolStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有-無形管理担当者"));
				}
			}
		}


		//////////////////////////////////// その他バリデーション

		// 社内使用ソフトウェア
		if(apGetType.equals(Constants.AP_GET_TYPE_INT1)){
			// 費用削減効果・収益獲得効果
			String astEffectType = Function.nvl(obj.getAstEffectType(), "");

			if(astEffectType.equals("1")) {										// 1:有
				if(obj.getAstEffectAmount() == null){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "費用削減効果・収益獲得効果-金額"));
				}
			}
			else if(astEffectType.equals("2") || astEffectType.equals("3")){	// 2:無,3:不明
				if(Function.nvl(obj.getAstEffectDescription(), "").equals("")){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "費用削減効果・収益獲得効果-備考"));
				}
			}
		}


		// 市販目的ソフトウェア
		if(apGetType.equals(Constants.AP_GET_TYPE_INT2)){

			// 明細件数
			if((devSchLine == null || devSchLine.size() == 0)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "開発スケジュール/開発要員計画明細", "1件以上"));
			}
			else{
				boolean detailInputFlag = false;
				for(int i = 0; i < devSchLine.size(); i++){
					ApGetIntLineDevSch devSchObj = devSchLine.get(i);

					//	明細の項目全て入力?
					if(devSchObj.getDevPeriodFrom() != null &&		// 実施期日FROM
							devSchObj.getDevPeriodTo() != null &&		// 実施期日TO
							devSchObj.getDevProperCount() != null &&		// 社員数
							devSchObj.getDevEntrustCount() != null &&	// 業務委託
							devSchObj.getDevAmount() != null){			// 開発経費
						detailInputFlag = true;
					}
					//	明細の項目どれか一つでも入力?
					else if(devSchObj.getDevPeriodFrom() != null ||		// 実施期日FROM
							devSchObj.getDevPeriodTo() != null ||		// 実施期日TO
							devSchObj.getDevProperCount() != null ||	// 社員数
							devSchObj.getDevEntrustCount() != null ||	// 業務委託
							devSchObj.getDevAmount() != null)			// 開発経費
					{
						//	実施期日FROM
						if(Function.nvl(devSchObj.getDevPeriodFrom(), "").equals("")){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "開発スケジュール/開発要員計画明細[" + (i+1) + "行目]-実地期間[開始日]" ));
						}
						// 実施期日TO
						if(Function.nvl(devSchObj.getDevPeriodTo(), "").equals("")){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "開発スケジュール/開発要員計画明細[" + (i+1) + "行目]-実地期間[終了日]" ));
						}
						// 社員数
						if(Function.nvl(devSchObj.getDevProperCount(), "").equals("")){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "開発スケジュール/開発要員計画明細[" + (i+1) + "行目]-要員数(人月)[社員]" ));
						}
						// 業務委託
						if(Function.nvl(devSchObj.getDevEntrustCount(), "").equals("")){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "開発スケジュール/開発要員計画明細[" + (i+1) + "行目]-要員数(人月)[業務委託]" ));
						}
						// 開発経費
						if(Function.nvl(devSchObj.getDevAmount(), "").equals("")){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "開発スケジュール/開発要員計画明細[" + (i+1) + "行目]-開発経費(円)" ));
						}
						detailInputFlag = true;
					}

					if(devSchObj.getDevPeriodFrom() != null && devSchObj.getDevPeriodTo() != null) {
						if(devSchObj.getDevPeriodFrom().compareTo(devSchObj.getDevPeriodTo()) > 0) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "開発スケジュール/開発要員計画明細[" + (i+1) + "行目]-実地期間[終了日]", "実地期間[開始日]"));
						}
					}
				}

				if(detailInputFlag == false){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "開発スケジュール/開発要員計画", "いずれかのフェーズは"));
				}

			}

			// 次期開発完了予定日
			if(Function.nvl(obj.getMktDevNextPlanDateFlag(), "").equals("1")){
				if(obj.getMktDevNextPlanDate() == null){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "開発予定-次期開発完了予定日"));
				}
			}
		}

		// 日付範囲チェック 賃貸予定期間FROM > 賃貸予定期間TO
		if(obj.getAstRentDateFrom() != null && obj.getAstRentDateTo() != null) {
			if(obj.getAstRentDateFrom().compareTo(obj.getAstRentDateTo()) > 0) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "賃借物件据付費用-賃貸予定期間TO", "賃貸予定期間FROM"));
			}
		}

		// 経費負担部課重複・配分合計
		if(costSecLine != null && costSecLine.size() > 0) {
			Set<String> sectionCodeSet = new HashSet<String>();
			int distTotal = 0;
			double adjustNum = 10;
			boolean isDup = false; // 重複フラグ
			for(int i = 0; i < costSecLine.size(); i++) {
				ApGetIntLineCostSec costSecObj = costSecLine.get(i);
				if(sectionCodeSet.contains(costSecObj.getCostSectionCode())) { // 経費負担部課の重複設定有
					isDup = true;
				}
				sectionCodeSet.add(costSecObj.getCostSectionCode());
				distTotal += (int) (Function.nvl(costSecObj.getCostDist(), Double.valueOf(0)).doubleValue() * adjustNum);
			}

			if(isDup) { // 重複有
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-経費負担部署", "経費負担部課コードが重複しています。"));
			}
			if(distTotal != (100 * adjustNum)) { // 配分合計 != 100
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-経費負担部署", "配分の合計が100%になっていません。"));
			}
		}

		// 金額範囲
		if(!Function.isAccessLevelAdmin(accessLevel) && !Function.nvl(obj.getApGetAmountRange(), "").equals("")) { // 一般権限の場合
			// 取得金額範囲 != 取得金額
			CodeName amountRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_GET_INT_AMOUNT_RANGE, obj.getApGetAmountRange(), obj.getApCompanyCode(), null);

			if(amountRange != null && obj.getGetTotalAmount() != null) {
				Long fromAmount = amountRange.getValue2() == null ? null : Long.valueOf(amountRange.getValue2());
				Long toAmount = amountRange.getValue3() == null ? null : Long.valueOf(amountRange.getValue3());

				if(fromAmount != null && fromAmount.longValue() > obj.getGetTotalAmount().longValue()) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "取得金額範囲", "取得金額合計[" + new DecimalFormat("#,##0").format(obj.getGetTotalAmount()) + "]が取得金額範囲[" + obj.getApGetAmountRangeName() + "]外です。"));
				} else if(toAmount != null && toAmount.longValue() < obj.getGetTotalAmount().longValue()) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "取得金額範囲", "取得金額合計[" + new DecimalFormat("#,##0").format(obj.getGetTotalAmount()) + "]が取得金額範囲[" + obj.getApGetAmountRangeName() + "]外です。"));
				}

			}
		}

		// 申請中
		if(stat.equals(Constants.AP_STATUS_GET_INT_APPLY) && objOld != null) {
			// 変更前後が申請中の場合
			if(Function.nvl(objOld.getApStatus(), "").equals(Constants.AP_STATUS_GET_INT_APPLY)) {
				// 総額が変更されている
				long newAmount = Function.nvl(obj.getGetTotalAmount(), Long.valueOf(0)).longValue();
				long oldAmount = Function.nvl(objOld.getGetTotalAmount(), Long.valueOf(0)).longValue();
				if(newAmount != oldAmount) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "取得金額合計", "申請中のデータに対して取得金額合計は変更できません。(" + new DecimalFormat("#,##0").format(oldAmount) +  " から " + new DecimalFormat("#,##0").format(newAmount) +  " へ変更されました。)"));
				}
			}
		}


		return errMsg.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#validateWarningApGetInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_int.ApGetInt)
	 */
	public String validateWarningApGetInt(String loginStaffCode, String accessLevel, ApGetInt obj) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 金額範囲整合性確認
		if(Function.isAccessLevelAdmin(accessLevel) && !Function.nvl(obj.getApGetAmountRange(), "").equals("")) {
			// 取得金額範囲 != 取得金額
			CodeName amountRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_GET_INT_AMOUNT_RANGE, obj.getApGetAmountRange(), obj.getApCompanyCode(), null);

			if(amountRange != null && obj.getGetTotalAmount() != null) {
				Long fromAmount = amountRange.getValue2() == null ? null : Long.valueOf(amountRange.getValue2());
				Long toAmount = amountRange.getValue3() == null ? null : Long.valueOf(amountRange.getValue3());

				if(fromAmount != null && fromAmount.longValue() > obj.getGetTotalAmount().longValue()) {
					errMsg.append("取得金額合計[" + new DecimalFormat("#,##0").format(obj.getGetTotalAmount()) + "]が取得金額範囲[" + obj.getApGetAmountRangeName() + "]外ですがよろしいですか？");
				} else if(toAmount != null && toAmount.longValue() < obj.getGetTotalAmount().longValue()) {
					errMsg.append("取得金額合計[" + new DecimalFormat("#,##0").format(obj.getGetTotalAmount()) + "]が取得金額範囲[" + obj.getApGetAmountRangeName() + "]外ですがよろしいですか？");
				}
			}
		}

		return errMsg.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#calcAstRentDateTo(java.lang.Long, java.util.Date)
	 */
	public String calcAstRentDateTo(Long astRentMonth, Date astRentDateFrom) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date astRentDateTo = Function.dateAdd(astRentDateFrom, Calendar.MONTH, astRentMonth.intValue());
		//	日付計算結果から1日ずらす
		astRentDateTo = Function.dateAdd(astRentDateTo, Calendar.DATE, -1);

		return df.format(astRentDateTo);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#calcAstRentDateFrom(java.lang.Long, java.util.Date)
	 */
	public String calcAstRentDateFrom(Long astRentMonth, Date astRentDatTo) {

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		Date astRentDateFrom = Function.dateAdd(astRentDatTo, Calendar.MONTH, (astRentMonth.intValue() * -1));
		//	日付計算結果から1日ずらす
		astRentDateFrom = Function.dateAdd(astRentDateFrom, Calendar.DATE, 1);

		return df.format(astRentDateFrom);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetIntSession#remindApGetInt(java.lang.String, java.lang.String, java.util.List)
	 */
	public void remindApGetInt(String loginStaffCode, String accessLevel, List<ApGetInt> applicationList) throws AppException {
		//////////////////////////////////// バリデーション
		StringBuffer errMsg = new StringBuffer();
		for(Iterator<ApGetInt> iter = applicationList.iterator(); iter.hasNext();) {
			ApGetInt apGetIntListData = iter.next();
			ApGetInt apGetInt = getApGetInt(apGetIntListData.getApplicationId(), apGetIntListData.getApplicationVersion(), true);

			// バージョンチェック
			if(apGetIntListData.getVersion().intValue() != apGetInt.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, apGetIntListData.getApplicationId(), Msg.ERR_MSG_VER_LIST));
			}

			// ステータスチェック(承認済のみ可)
			if(!Function.nvl(apGetInt.getApStatus(), "").equals(Constants.AP_STATUS_GET_INT_APPROVE)) {
				// ステータスエラーは即座にスロー
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG_STAT_LIST, Constants.AP_STATUS_NAME_GET_INT_APPROVE));
			}

			// サービス提供開始報告済
			if(Function.nvl(apGetInt.getReportCompleteFlag(), "").equals(Constants.FLAG_YES)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, apGetIntListData.getApplicationId(), "サービス提供開始報告済のため、処理を行えません。"));
			}

			// 市販目的ソフトウェア以外で、計上区分が資産の明細無し
			if(!Function.nvl(apGetInt.getApGetType(), "").equals(Constants.AP_GET_TYPE_INT2)) {
				if(Function.nvl(apGetInt.getAstTotalAmount(), Long.valueOf(0)).longValue() == 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, apGetIntListData.getApplicationId(), "資産計上予定ではないため、処理を行えません。"));
				}
			}
		}

		if(errMsg.length() > 0) { // エラーあり
			throw new AppException(errMsg.toString());
		}

		//////////////////////////////////// 督促メール送信&データ更新
		User loginStaff = masterSession.getStaff(null, loginStaffCode); // 処理実行者

		StringBuffer reportMailBody = new StringBuffer(); // 処理結果用メール本文
		StringBuffer reportMailBodyError = new StringBuffer(); // 例外発生時のエラー文字列

		DecimalFormat dcf = new DecimalFormat();
		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");
		for(Iterator<ApGetInt> iter = applicationList.iterator(); iter.hasNext();) {
			ApGetInt apGetIntListData = iter.next();
			ApGetInt apGetInt = getApGetInt(apGetIntListData.getApplicationId(), apGetIntListData.getApplicationVersion(), true);

			boolean mailSuccess = true;
			////////// メール送信
			try {
				// 宛先取得
				User apStaff = masterSession.getStaff(null, apGetInt.getApStaffCode()); // 申請者
				User holStaff = masterSession.getStaff(null, apGetInt.getHolStaffCode()); // 無形管理担当者

				List<String> toList = new ArrayList<String>();
				if(apStaff != null) toList.add(apStaff.getMailAddress());
				if(holStaff != null) toList.add(holStaff.getMailAddress());

				// 件名・本文取得
				CodeName mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_REMIND_GET_INT + apGetInt.getApGetType(), null, apGetInt.getApCompanyCode(), null);
				String subject = mailTemplate.getValue1();
				String body = mailTemplate.getValue2();

				// 予約語置換
				String getAmountStr = "";
				if(apGetInt.getGetTotalAmount() != null) getAmountStr = dcf.format(apGetInt.getGetTotalAmount());
				String completePlanDateStr = "";
				if(apGetInt.getAstCompletePlanDate() != null) completePlanDateStr = dtf.format(apGetInt.getAstCompletePlanDate());

				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AP_STAFF_NAME, Function.nvl(apGetInt.getApStaffName(), ""));
				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_APPLICATION_ID, Function.nvl(apGetInt.getApplicationId(), ""));
				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AST_NAME, Function.nvl(apGetInt.getAstName(), ""));
				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_ET_TOTAL_AMOUNT, getAmountStr);
				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_COMPLETE_PLAN_DATE, completePlanDateStr);

				body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_STAFF_NAME, Function.nvl(apGetInt.getApStaffName(), ""));
				body = body.replaceAll(MAIL_TEMPLATE_VAR_APPLICATION_ID, Function.nvl(apGetInt.getApplicationId(), ""));
				body = body.replaceAll(MAIL_TEMPLATE_VAR_AST_NAME, Function.nvl(apGetInt.getAstName(), ""));
				body = body.replaceAll(MAIL_TEMPLATE_VAR_ET_TOTAL_AMOUNT, getAmountStr);
				body = body.replaceAll(MAIL_TEMPLATE_VAR_COMPLETE_PLAN_DATE, completePlanDateStr);

				// 送信
				sendMailSession.sendMail(loginStaff.getMailAddress(), toList, null, subject, body);

				////////// データ更新
				apGetInt.setReminderDate(new Date());
				updateApGetInt(loginStaffCode, accessLevel, apGetInt, false, false, false, true, HIST_OPERATION_REMIND);
			} catch (Exception e) {
				mailSuccess = false;
				reportMailBodyError.append("---------------------------------------------------------------");
				reportMailBodyError.append("申請書番号：" + apGetInt.getApplicationId() + "\n");
				reportMailBodyError.append(Function.getStackTraceStr(e));
				reportMailBodyError.append("\n");
				Logging.error(e.getMessage(), e);
			}

			reportMailBody.append(apGetInt.getApplicationId());
			reportMailBody.append("　" + apGetInt.getApStaffCode() + "　" + apGetInt.getApStaffName());
			reportMailBody.append("　" + apGetInt.getHolStaffCode() + "　" + apGetInt.getHolStaffName());
			if(!mailSuccess) reportMailBody.append("　※送信失敗");
			reportMailBody.append("\n");
		}

		//////////////////////////////////// 処理結果メール送信（処理者）
		StringBuffer body = new StringBuffer();
		body.append("申請書番号　申請者　　　　　　無形管理担当者\n");
		body.append("---------------------------------------------------------------\n");
		body.append(reportMailBody);

		if(reportMailBodyError.length() > 0) {
			body.append("\n以下送信失敗時のエラーメッセージ\n");
		}

		sendMailSession.sendMail(loginStaff.getMailAddress(), loginStaff.getMailAddress(), null, "無形固定資産取得申請-督促メール処理結果", body.toString());
	}
}
