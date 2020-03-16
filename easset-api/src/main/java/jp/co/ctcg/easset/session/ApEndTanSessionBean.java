/*===================================================================
 * ファイル名 : ApEndTanSessionBean.java
 * 概要説明   : 固定資産除売却申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-26 1.0  李            新規
 *=================================================================== */
package jp.co.ctcg.easset.session;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Iterator;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import jp.co.ctcg.easset.dao.ApEndTanDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTan;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineAst;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineAtt;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineFld;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineLic;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanSC;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanSR;
import jp.co.ctcg.easset.dto.ap_end_tan.PpfsYskCalc;
import jp.co.ctcg.easset.dto.asset.AssetSC;
import jp.co.ctcg.easset.dto.asset.AssetSR;
import jp.co.ctcg.easset.dto.fld.PpfsUnUpd;
import jp.co.ctcg.easset.dto.license.LicenseSC;
import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;
import jp.co.ctcg.easset.util.PdfExporter;
import jp.co.ctcg.easset.ws.ApEndTanService;
import jp.co.ctcg.easset.ws.ApEndTanServiceProxy;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;

import org.apache.commons.beanutils.PropertyUtils;

@Stateless
public class ApEndTanSessionBean implements ApEndTanSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession; // マスタセッション

	@EJB
	HistSession histSession; // 履歴セッション

	@EJB
	FldSession fldSession; // 固定資産セッション

	@EJB
	AssetSession assetSession; // 情報機器等セッション

	@EJB
	LicenseSession licenseSession; // ﾗｲｾﾝｽセッション

	@EJB
	SendMailSession sendMailSession;

	private static final String ID_PREFIX_TAN = "FH";
	private static final String ID_PREFIX_INT = "FS";
	private static final String FILE_SAVE_ENTITY_NAME = "AP_END_TAN";

	// 履歴作成用
	private static final String HIST_ENTITY_NAME = "AP_END_TAN";
	private static final String HIST_OPERATION_CREATE = "新規作成";
	private static final String HIST_OPERATION_UPDATE = "更新";
	private static final String HIST_OPERATION_DELETE = "削除";
	private static final String HIST_OPERATION_APPLY = "申請";
	private static final String HIST_OPERATION_APPROVE = "承認";
	private static final String HIST_OPERATION_SENDBACK = "差戻し";
	private static final String HIST_OPERATION_REJECT = "却下";
	private static final String HIST_OPERATION_CANCEL_APPLY = "引戻し";
	private static final String HIST_OPERATION_REMIND = "督促";
	private static final String HIST_OPERATION_REPORT = "除却報告";
	private static final String HIST_OPERATION_DELETE_EXEC = "自動抹消";

	// メールテンプレート変数
	private static final String MAIL_TEMPLATE_VAR_AP_STAFF_NAME = "\\$\\{申請者名\\}";
	private static final String MAIL_TEMPLATE_VAR_APPLICATION_ID = "\\$\\{申請書番号\\}";
	private static final String MAIL_TEMPLATE_VAR_GET_TYPE = "\\$\\{取得形態\\}";
	private static final String MAIL_TEMPLATE_VAR_AP_END_TAN_TYPE_NAME = "\\$\\{除却区分\\}";
	private static final String MAIL_TEMPLATE_VAR_AP_REASON_NAME = "\\$\\{除売却理由区分\\}";
	private static final String MAIL_TEMPLATE_VAR_TOTAL_BOOK_AMOUNT = "\\$\\{除売却時簿価\\}";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#searchApEndTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanSC)
	 */
	public List<ApEndTanSR> searchApEndTan(String loginStaffCode, String accessLevel, ApEndTanSC searchParam) {
		try {
			ApEndTanDAO apEndTanDAO = new ApEndTanDAO();
			return apEndTanDAO.selectApEndTanList(loginStaffCode, accessLevel, searchParam);

		} catch (SQLException e) {
			//	固定資産除売却申請検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産除売却申請検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#getApEndTan(java.lang.String)
	 */
	public ApEndTan getApEndTan(String applicationId) {
		return getApEndTan(applicationId, false);
	}
	/**
	 * 申請情報取得
	 * @param applicationId 申請書番号
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private ApEndTan getApEndTan(String applicationId, boolean lockFlag ){
		try{

			ApEndTanDAO apEndTanDAO = new ApEndTanDAO();

			ApEndTan apEndTan = apEndTanDAO.selectApEndTan(applicationId, lockFlag);
			if(apEndTan != null) {
//				固定資産除売却申請_資産明細取得
				apEndTan.setApEndTanLineFldList(apEndTanDAO.selectApEndTanLineFld(applicationId));
//				固定資産除売却申請_資産(機器)明細取得
				apEndTan.setApEndTanLineAstList(apEndTanDAO.selectApEndTanLineAst(applicationId));
//				固定資産除売却申請_ライセンス明細取得
				apEndTan.setApEndTanLineLicList(apEndTanDAO.selectApEndTanLineLic(applicationId));
//				固定資産除売却申請_添付明細取得
				apEndTan.setApEndTanLineAttList(apEndTanDAO.selectApEndTanLineAtt(applicationId));

			}

			return apEndTan;

		} catch (SQLException e) {
			//	固定資産除売却申請検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産除売却申請検索"), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#getApEndTan(java.lang.Long)
	 */
	public ApEndTan getApEndTan(Long eappId) {
		return getApEndTan(eappId, false);
	}
	/**
	 * 申請情報取得
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private ApEndTan getApEndTan(Long eappId, boolean lockFlag) {
		try {
			ApEndTanDAO apEndTanDAO = new ApEndTanDAO();

			ApEndTan apEndTan = apEndTanDAO.selectApEndTan(eappId, lockFlag); // ヘッダの取得

			if(apEndTan != null) {
				return getApEndTan(apEndTan.getApplicationId(), lockFlag);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産除売却申請取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#createApEndTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_tan.ApEndTan)
	 */
	public String createApEndTan(String loginStaffCode, String accessLevel, ApEndTan obj) throws AppException {
		return createApEndTan(loginStaffCode, accessLevel, obj, true);
	}
	/**
	 * 固定資産除売却申請作成本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの取得申請書番号
	 */
	private String createApEndTan(String loginStaffCode, String accessLevel, ApEndTan obj, boolean isHistCreate) throws AppException {
		try{
			ApEndTanDAO apEndTanDAO = new ApEndTanDAO();

			//////////////////////////////////// 固定値セット
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setCreateDate(sysdate);
			obj.setCreateStaffCode(loginStaffCode);
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);
			//	除却報告デフォルトセット
			obj.setReportFlag(Constants.FLAG_NO);
			//	抹消処理実行フラグ
			obj.setDeleteExecFlag(Constants.FLAG_NO);

			// 不正セット項目値の調整
			setPropertyAdjust(obj);


			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			errMsg.append(validateApEndTan(loginStaffCode, accessLevel, obj, null));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			///////////////////////////////////	金額範囲(除売却時簿価)の自動設定
			setAmounRange(obj);

			//////////////////////////////////// IDの採番
			String applicationId = "";
			//	有形？
			if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
				applicationId = masterSession.nextValId(ID_PREFIX_TAN);
			}
			else{
				applicationId = masterSession.nextValId(ID_PREFIX_INT);
			}

			//////////////////////////////////// データ作成
			// バージョン・改訂番号
			obj.setVersion(1);
			obj.setDisplayVersion(1);
			obj.setApplicationId(applicationId); // IDセット
			apEndTanDAO.insertApEndTan(obj); // ヘッダ作成
			createLine(loginStaffCode, obj, apEndTanDAO, applicationId); // 明細データ作成

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				histSession.createHistData(HIST_ENTITY_NAME, applicationId, HIST_OPERATION_CREATE, null);
			}

			return obj.getApplicationId();

		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産除売却申請作成:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産除売却申請作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#updateApEndTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_tan.ApEndTan)
	 */
	public void updateApEndTan(String loginStaffCode, String accessLevel, ApEndTan obj) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateApEndTan(loginStaffCode, accessLevel, obj, true, true, true, null);

	}
	/**
	 * 除売却申請更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isDisplayVersionUpdate 改訂番号更新有無 true:改訂番号をインクリメント false:改訂番号は更新しない
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @param operation 履歴作成時のオペレーション
	 * @throws AppException
	 */
	private void updateApEndTan(String loginStaffCode, String accessLevel, ApEndTan obj, boolean isLineUpdate, boolean isDisplayVersionUpdate, boolean isHistCreate, String operation) throws AppException {
		try{
			ApEndTanDAO apEndTanDAO = new ApEndTanDAO();

			ApEndTan apEndTanOld = getApEndTan(obj.getApplicationId(), true);

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
			if(obj.getVersion().intValue() != apEndTanOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// バリデーション(登録画面更新の際のみ：連携等による更新時は行わない)
			if(isLineUpdate) {
				setAmounRange(obj); //	金額範囲(除売却時簿価)の自動設定
				errMsg.append(validateApEndTan(loginStaffCode, accessLevel, obj, apEndTanOld));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新
			// バージョン・改訂番号
			obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
			if(isDisplayVersionUpdate) obj.setDisplayVersion(Function.nvl(obj.getDisplayVersion(), Integer.valueOf(1)) + 1);

			if(obj.getReportDate() == null) obj.setReportFlag(Constants.FLAG_NO); // 除却報告日がクリアされた場合は未報告に戻す

			apEndTanDAO.updateApEndTan(obj);

			if(isLineUpdate) {
				// 明細を一度削除
				deleteLine(loginStaffCode, obj, apEndTanDAO);

				createLine(loginStaffCode, obj, apEndTanDAO, obj.getApplicationId()); // 明細データ作成
			}

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				StringBuffer lineChangeColumnName = new StringBuffer();
				if(isLineUpdate) {
					// 明細変更確認
					if(Function.isListChange(obj.getApEndTanLineFldList(), apEndTanOld.getApEndTanLineFldList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("資産明細");
					}
					if(Function.isListChange(obj.getApEndTanLineAstList(), apEndTanOld.getApEndTanLineAstList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("情報機器明細");
					}
					if(Function.isListChange(obj.getApEndTanLineLicList(), apEndTanOld.getApEndTanLineLicList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("ライセンス明細");
					}
					if(Function.isListChange(obj.getApEndTanLineAttList(), apEndTanOld.getApEndTanLineAttList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("添付明細");
					}
				}

				if(isHistCreate) {
					histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), (operation == null ? HIST_OPERATION_UPDATE : operation), lineChangeColumnName.toString());
				}
			}

		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "除売却申請更新:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "除売却申請更新"), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#applyApEndTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_tan.ApEndTan)
	 */
	public String applyApEndTan(String loginStaffCode, String accessLevel, ApEndTan obj) throws AppException {
		String ret = null;
		boolean isNew = Function.nvl(obj.getApplicationId(), "").equals(""); // 新規の場合true

		////////////////////////////////////新規 or 更新呼び出し
		if(isNew){
			ret = createApEndTan(loginStaffCode, accessLevel, obj, false);
		}
		else{

			// 更新コメント
			obj.setUpdateComment(null);

			ret = obj.getApplicationId();

			// ステータス更新前バリデーション
			String errMsg = validateApEndTan(loginStaffCode, accessLevel, obj, null);
			if(errMsg.length() > 0) throw new AppException(errMsg);
		}

		//////////////////////////////////// ステータス更新&ステータス更新後バリデーション
		obj.setApStatus(Constants.AP_STATUS_END_TAN_APPLY);
		String errMsg = validateApEndTan(loginStaffCode, accessLevel, obj, null);
		if(errMsg.length() > 0) throw new AppException(errMsg);

		///////////////////////////////////	金額範囲(除売却時簿価)の自動設定
		setAmounRange(obj);

		//////////////////////////////////// 申請
		Long eappId = null;

		eappId = callEappService(obj); // e申請連携

		// e申請IDを更新
		obj.setEappId(eappId);
		if(isNew) { // 新規
			updateApEndTan(loginStaffCode, accessLevel, obj, false, false, false, null);
			histSession.createHistData(HIST_ENTITY_NAME, ret, HIST_OPERATION_APPLY, null); // 履歴作成
		} else {
			updateApEndTan(loginStaffCode, accessLevel, obj, true, true, true, HIST_OPERATION_APPLY);
		}

		return ret;

	}
	/**
	 * e申請サービス呼び出し
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(ApEndTan obj) throws AppException {
		// e申請WebServiceエンドポイント取得
		CodeName codeNameUrl = new CodeName();
		//	有形
		if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
			codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_END_TAN, null, null);
		}
		else{
			codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_END_INT, null, null);
		}
		String eappWsEndpoint = codeNameUrl.getValue1(); // e申請WebSerivceエンドポイント
		String eAssetUrl = codeNameUrl.getValue2(); // e申請インラインフレーム画面表示用のeAssetUrl
		String eappStopMessage = codeNameUrl.getValue3(); // e申請との連携停止期間中のエラーメッセージ

		Long eappId = null;

		if(!Function.nvl(eappWsEndpoint, "").equals("")) { // e申請WebServiceエンドポイントが空白(PG検証用)の場合は連携無し
			// e申請との連携停止期間中のエラーメッセージ
			if(!eappWsEndpoint.startsWith("http")){
				throw new AppException(eappStopMessage);
			}

			try{
				eAssetUrl += "&amp;companyCode=" + obj.getApCompanyCode();
				eAssetUrl += "&amp;param2="; // e申請から書類IDが指定される

				// 金額範囲マスタ取得
				CodeName codeNameRange;
				//	有形
				if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
					codeNameRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_END_TAN_AMOUNT_RANGE, obj.getApEndTanAmountRange(), obj.getApCompanyCode(), null);
				}
				//	無形
				else{
					codeNameRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_END_INT_AMOUNT_RANGE, obj.getApEndTanAmountRange(), obj.getApCompanyCode(), null);
				}
				// タイトルセット
				String title = codeNameRange.getValue4();
				if(codeNameRange.getValue5() != null) {
					title = title + "\\n" + codeNameRange.getValue5();
				} else {
					title = "\\n" + title;
				}

				// サブタイトル(金額)セット
				String subTitle = Function.nvl(obj.getApEndTanAmountRangeName(), "");
				if(codeNameRange.getValue6() != null) subTitle = codeNameRange.getValue6() + " " + subTitle;
				if(!Function.nvl(subTitle, "").equals("")) subTitle = "(" + subTitle + ")";

				//////////////////////////////////// 経路設定
				// e申請経路担当情報取得
				List<CodeName> codeNameEappChargeList = new ArrayList<CodeName>();
				//	有形
				if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
					codeNameEappChargeList =  masterSession.getCodeNameList(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_TAN, null, obj.getApCompanyCode(), null);
				}
				//	無形
				else{
					codeNameEappChargeList =  masterSession.getCodeNameList(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_INT, null, obj.getApCompanyCode(), null);
				}
				CodeName codeNameEappCharge = codeNameEappChargeList.get(0);
				CodeName codeNameEappCharge2 = null;
				if(codeNameEappChargeList.size() > 1) codeNameEappCharge2 = codeNameEappChargeList.get(1);

				// e申請経路権限情報取得
				List<String> attributeParam = new ArrayList<String>();
				String eappRouteKey = Function.nvl(codeNameRange.getValue8(), "XXXXXXXXXXXX");
				attributeParam.add(eappRouteKey);

				CodeName codeNameEappRoute;
				//	有形
				if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
					codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_TAN, null, obj.getApCompanyCode(), attributeParam);
				}
				//	無形
				else{
					codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_INT, null, obj.getApCompanyCode(), attributeParam);
				}

				if(codeNameEappRoute == null) codeNameEappRoute = new CodeName();

				// 特殊経路判定
				// 稟議書経営会議承認済
				if(Function.nvl(obj.getSpecialApproveFlag(), "").equals(Constants.FLAG_YES)) {
					attributeParam.clear();
					//	有形
					if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
						attributeParam.add(eappRouteKey.substring(0, 2) + Constants.EAPP_ROUTE_AUTH_AP_END_TAN_SPECIAL_APPROVE_SUFFIX);
					}
					//	無形
					else{
						attributeParam.add(eappRouteKey.substring(0, 2) + Constants.EAPP_ROUTE_AUTH_AP_END_INT_SPECIAL_APPROVE_SUFFIX);
					}
					CodeName cn = new CodeName();
					//	有形
					if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
						cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_TAN, null, obj.getApCompanyCode(), attributeParam);
					}
					//	無形
					else{
						cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_INT, null, obj.getApCompanyCode(), attributeParam);
					}
					if(cn != null) codeNameEappRoute = cn; // 経営会議承認済用の経路に置き換え
				}

				 // 要CIO審査
				if(Function.nvl(codeNameEappRoute.getValue1(), "").endsWith("-1") && Function.nvl(obj.getApNeedCioJudgeFlag(), "").equals(Constants.FLAG_YES)) {
					eappRouteKey = codeNameEappRoute.getValue1().replaceAll("-1$", "-2"); // 経路をCIO審査込みの経路に変更
					attributeParam.clear();
					attributeParam.add(eappRouteKey);
					//	有形
					if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
						codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_TAN, null, obj.getApCompanyCode(), attributeParam);
					}
					//	無形
					else{
						codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_INT, null, obj.getApCompanyCode(), attributeParam);
					}

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

				int routeMax = 0;
				//	有形
				if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
					routeMax = Constants.MAX_EAPP_ROUTE_COUNT_AP_END_TAN;
				}
				else{
					routeMax = Constants.MAX_EAPP_ROUTE_COUNT_AP_END_INT;
				}

				for(int i = 0; i < routeMax; i++) {
					List<String> authDispList;
					List<String> chargeDispList;
					List<String> dispTypeList;

					// 対象経路判別
					//	有形
					if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
						if(i <= 2) { // 申請部経路
							authDispList = applyRouteAuthDispList;
							chargeDispList = applyRouteChargeDispList;
							dispTypeList = applyRouteDispTypeList;
						} else if((3 <= i && i <= 5) || (8 <= i && i <= 9) || 13 <= i) {// 受付経路
							authDispList = acceptRouteAuthDispList;
							chargeDispList = acceptRouteChargeDispList;
							dispTypeList = acceptRouteDispTypeList;
						} else { // 承認経路
							authDispList = approveRouteAuthDispList;
							chargeDispList = approveRouteChargeDispList;
							dispTypeList = approveRouteDispTypeList;
						}
					}
					//	無形
					else{
						if(i <= 2) { // 申請部経路
							authDispList = applyRouteAuthDispList;
							chargeDispList = applyRouteChargeDispList;
							dispTypeList = applyRouteDispTypeList;
						} else if((3 <= i && i <= 5) || 9 <= i) {// 受付経路
							authDispList = acceptRouteAuthDispList;
							chargeDispList = acceptRouteChargeDispList;
							dispTypeList = acceptRouteDispTypeList;
						} else { // 承認経路
							authDispList = approveRouteAuthDispList;
							chargeDispList = approveRouteChargeDispList;
							dispTypeList = approveRouteDispTypeList;
						}
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

					//////////////////////////////////// e申請サービス呼び出し
				if(routeExists){
					String eappIdStr = null;
					try {
						ApEndTanService service = new ApEndTanServiceProxy(eappWsEndpoint);
						eappIdStr = service.apply(
								obj.getApplicationId() // applicationId
								, Constants.AP_TYPE_END_TAN // applicationType
								, obj.getApCompanyCode() // companyCode
								, obj.getApSectionCode() // apSectionCode
								, obj.getApCreateStaffCode() // apCreateStaffCode
								, obj.getApStaffCode() // apStaffCode
								, obj.getApTel() // apTel
								, title // apTitle
								, subTitle // apSubTitle
								, obj.getApCompanyName() + title.replaceAll("\\\\n", " ") + " " + subTitle // apListTitle
								, eAssetUrl // eAssetUrl
								, applyRouteAuthDispList.toArray(new String[0]) // applyRouteAuthDispArray
								, applyRouteChargeDispList.toArray(new String[0]) // applyRouteChargeDispArray
								, applyRouteDispTypeList.toArray(new String[0]) // applyRouteDispTypeArray
								, approveRouteAuthDispList.toArray(new String[0]) // approveRouteAuthDispArray
								, approveRouteChargeDispList.toArray(new String[0]) // approveRouteChargeDispArray
								, approveRouteDispTypeList.toArray(new String[0]) // approveRouteDispTypeArray
								, acceptRouteAuthDispList.toArray(new String[0]) // acceptRouteAuthDispArray
								, acceptRouteChargeDispList.toArray(new String[0]) // acceptRouteChargeDispArray
								, acceptRouteDispTypeList.toArray(new String[0]) // acceptRouteDispTypeArray
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
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#deleteApEndTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_tan.ApEndTan)
	 */
	public void deleteApEndTan(String loginStaffCode, String accessLevel, ApEndTan obj) throws AppException {
		try {
			ApEndTanDAO apEndTanDAO = new ApEndTanDAO();

			ApEndTan apEndTanOld = getApEndTan(obj.getApplicationId(), true); // 現データの取得

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != apEndTanOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新(履歴作成用にバージョンアップ)
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			apEndTanOld.setUpdateDate(sysdate);
			apEndTanOld.setUpdateStaffCode(loginStaffCode);

			// バージョン
			apEndTanOld.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);

			// 更新コメント
			apEndTanOld.setUpdateComment(null);

			apEndTanDAO.updateApEndTan(apEndTanOld);

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), HIST_OPERATION_DELETE, null);


			//////////////////////////////////// データ削除
			apEndTanDAO.deleteApEndTan(obj.getApplicationId());
			apEndTanDAO.deleteApEndTanLineFld(obj.getApplicationId());
			apEndTanDAO.deleteApEndTanLineAst(obj.getApplicationId());
			apEndTanDAO.deleteApEndTanLineLic(obj.getApplicationId());
			apEndTanDAO.deleteApEndTanLineAtt(obj.getApplicationId());

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "固定資産除売却申請取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#createApEndTanCsv(String, String, ApEndTanSC)
	 */
	public String createApEndTanCsv(String loginStaffCode, String accessLevel, ApEndTanSC obj) throws AppException {
		try {
			ApEndTanDAO apEndTan = new ApEndTanDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = apEndTan.createApChangeListCsv(loginStaffCode, accessLevel, obj);

			//////////////////////////////////// 操作ログ作成
			String comOp = "";
			//	有形?
			if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
				comOp = Constants.COM_OP_FUNCTION_AP_END_TAN_SEARCH;
			}
			else{
				//	無形?
				comOp = Constants.COM_OP_FUNCTION_AP_END_INT_SEARCH;
			}
			histSession.createOpLog(loginStaffCode, comOp, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(obj));

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#createApEndTanPdf(java.util.List, java.lang.Boolean)
	 */
	public String createApEndTanPdf(List<String> applicationIdList, Boolean lineOutputFlag) throws AppException {

		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();
		reportParam.put("applicationIdWhere", Function.getInCondition("naet.APPLICATION_ID", applicationIdList, true));
//		reportParam.put("naetApplicationIdWhere", Function.getInCondition("naet.APPLICATION_ID", applicationIdList, true));
		if(lineOutputFlag != null && lineOutputFlag.booleanValue()) reportParam.put("lineOutputFlag", "1");

		// PDF生成
		PdfExporter exporter = new PdfExporter();
		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApEndTan.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#getAstLicByFld(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ApEndTan getAstLicByFld(String loginStaffCode, String accessLevel, String companyCode, String shisanNumPlural) throws AppException {
		ApEndTan apEndTan = new ApEndTan();
		List<ApEndTanLineAst> apEndTanLineAstList = new ArrayList<ApEndTanLineAst>();
		List<ApEndTanLineLic> apEndTanLineLicList = new ArrayList<ApEndTanLineLic>();

		if(!Function.nvl(shisanNumPlural, "").equals("")){
			//	情報機器検索
			AssetSC searchParam = new AssetSC();
			searchParam.setShisanNumPlural(shisanNumPlural);
			searchParam.setHolCompanyCode(companyCode);
			searchParam.setDeleteFlag(Constants.FLAG_NO); // 抹消済みは除外
			List<AssetSR> assetList = assetSession.searchAsset(loginStaffCode, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, searchParam, false);

			//	型変換
			if(assetList != null && assetList.size() > 0){
				for(int i = 0; i < assetList.size(); i++){
					AssetSR asset = assetList.get(i);
					ApEndTanLineAst apEndTanLineAst = new ApEndTanLineAst();
					apEndTanLineAst.setAssetId(asset.getAssetId());
					apEndTanLineAst.setHolCompanyCode(asset.getHolCompanyCode());
					apEndTanLineAst.setHolSectionCode(asset.getHolSectionCode());
					apEndTanLineAst.setHolSectionName(asset.getHolSectionName());
					apEndTanLineAst.setHolSectionYear(asset.getHolSectionYear());
					apEndTanLineAst.setHolStaffCode(asset.getHolStaffCode());
					apEndTanLineAst.setHolStaffName(asset.getHolStaffName());
					apEndTanLineAst.setHolOfficeCode(asset.getHolOfficeCode());
					apEndTanLineAst.setHolOfficeName(asset.getHolOfficeName());
					apEndTanLineAst.setHolQuantity(asset.getHolQuantity());
					apEndTanLineAst.setUseStaffCode(asset.getUseStaffCode());
					apEndTanLineAst.setUseStaffName(asset.getUseStaffName());
					apEndTanLineAst.setUseStaffCompanyCode(asset.getUseStaffCompanyCode());
					apEndTanLineAst.setUseStaffSectionYear(asset.getUseStaffSectionYear());
					apEndTanLineAst.setUseStaffSectionCode(asset.getUseStaffSectionCode());
					apEndTanLineAst.setAstName(asset.getAstName());
					apEndTanLineAst.setAstCategory1Code(asset.getAstCategory1Code());
					apEndTanLineAst.setAstCategory2Code(asset.getAstCategory2Code());
					apEndTanLineAst.setAstSystemSectionDeployFlag(asset.getAstSystemSectionDeployFlag());
					apEndTanLineAst.setAstAssetType(asset.getAstAssetType());
					apEndTanLineAst.setAstAssetTypeName(asset.getAstAssetTypeName());
					apEndTanLineAst.setAstManageType(asset.getAstManageType());
					apEndTanLineAst.setAstNum(asset.getShisanNum());
					apEndTanLineAst.setPpId(asset.getKoyunoL());
					apEndTanLineAst.setGetApplicationId(asset.getGetApplicationId());
					apEndTanLineAstList.add(apEndTanLineAst);
				}
			}

			//	ﾗｲｾﾝｽ検索
			LicenseSC searchParam2 = new LicenseSC();
			searchParam2.setShisanNumPlural(shisanNumPlural);
			searchParam2.setHolCompanyCode(companyCode);
			searchParam2.setDeleteFlag(Constants.FLAG_NO); // 抹消済みは除外
			List<LicenseSR> licenseList = licenseSession.searchLicense(loginStaffCode, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, searchParam2, false);
			//	型変換
			if(licenseList != null && licenseList.size() > 0){
				for(int i = 0; i < licenseList.size(); i++){
					LicenseSR license = licenseList.get(i);
					ApEndTanLineLic apEndTanLineLic = new ApEndTanLineLic();
					apEndTanLineLic.setLicenseId(license.getLicenseId());
					apEndTanLineLic.setHolCompanyCode(license.getHolCompanyCode());
					apEndTanLineLic.setHolSectionCode(license.getHolSectionCode());
					apEndTanLineLic.setHolSectionName(license.getHolSectionName());
					apEndTanLineLic.setHolSectionYear(license.getHolSectionYear());
					apEndTanLineLic.setHolStaffCode(license.getHolStaffCode());
					apEndTanLineLic.setHolStaffName(license.getHolStaffName());
					apEndTanLineLic.setSoftwareId(license.getSoftwareId());
					apEndTanLineLic.setSoftwareName(license.getSoftwareName());
					apEndTanLineLic.setSoftwareMakerId(license.getSoftwareMakerId());
					apEndTanLineLic.setSoftwareMakerName(license.getSoftwareMakerName());
					apEndTanLineLic.setSoftwareMakerId(license.getSoftwareMakerId());
					apEndTanLineLic.setLicAssetType(license.getLicAssetType());
					apEndTanLineLic.setLicAssetTypeName(license.getLicAssetTypeName());
					apEndTanLineLic.setLicQuantityType(license.getLicQuantityType());
					apEndTanLineLic.setLicQuantity(license.getLicQuantity());
					apEndTanLineLic.setGetApplicationId(license.getGetApplicationId());
					apEndTanLineLic.setAstNum(license.getShisanNum());
					apEndTanLineLic.setPpId(license.getKoyunoL());
					apEndTanLineLicList.add(apEndTanLineLic);
				}
			}
		}

		apEndTan.setApEndTanLineAstList(apEndTanLineAstList);
		apEndTan.setApEndTanLineLicList(apEndTanLineLicList);

		return apEndTan;
	}

	/*
	 * 明細データの作成
	 */
	private void createLine(String loginStaffCode, ApEndTan obj, ApEndTanDAO apEndTanDAO, String applicationId) throws SQLException, IOException {
		Date sysdate = new Date(); // システム日付取得

		//	固定資産明細作成
		List<ApEndTanLineFld> apEndTanLineFldList = obj.getApEndTanLineFldList();
		if(apEndTanLineFldList != null && apEndTanLineFldList.size() > 0){
			for(int i = 0; i < apEndTanLineFldList.size(); i++){
				ApEndTanLineFld row = apEndTanLineFldList.get(i);

				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1);	//	行番号のセット

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apEndTanDAO.insertApEndTanFld(row);

			}
		}

		//	情報機器等明細作成
		List<ApEndTanLineAst> apEndTanLineAstList = obj.getApEndTanLineAstList();
		if(apEndTanLineAstList != null && apEndTanLineAstList.size() > 0){
			for(int i = 0; i < apEndTanLineAstList.size(); i++){
				ApEndTanLineAst row = apEndTanLineAstList.get(i);

				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1);	//	行番号のセット

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apEndTanDAO.insertApEndTanLineAst(row);

			}
		}

		//	ﾗｲｾﾝｽ明細作成
		List<ApEndTanLineLic> apEndTanLineLicList = obj.getApEndTanLineLicList();
		if(apEndTanLineLicList != null && apEndTanLineLicList.size() > 0){
			for(int i = 0; i < apEndTanLineLicList.size(); i++){
				ApEndTanLineLic row = apEndTanLineLicList.get(i);

				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1);	//	行番号のセット

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apEndTanDAO.insertApEndTanLineLic(row);

			}
		}

		// 添付明細作成
		List<ApEndTanLineAtt> apEndTanLineAttList = obj.getApEndTanLineAttList();
		if(apEndTanLineAttList != null && apEndTanLineAttList.size() > 0) {
			for(int i = 0; i < apEndTanLineAttList.size(); i++) {
				ApEndTanLineAtt row = apEndTanLineAttList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
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
				apEndTanDAO.insertApEndTanLineAtt(row);
			}
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#cancelApplyApGetTan(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyApEndTan(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndTan apEndTan = getApEndTan(eappId, true);

		if(apEndTan != null) {
			// ステータス
			apEndTan.setApStatus(Constants.AP_STATUS_END_TAN_NOAPPLY);
			// 更新コメント
			apEndTan.setUpdateComment(null);
			apEndTan.setEappId(null); // 書類IDクリア

			updateApEndTan(execStaffCode, null, apEndTan, false, false, true, HIST_OPERATION_CANCEL_APPLY);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#rejectApGetTan(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectApEndTan(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		String histOperation;

		//////////////////////////////////// 更新
		ApEndTan apEndTan = getApEndTan(eappId, true);

		if(apEndTan != null) {
			// ステータス
			if(Function.nvl(rejectType, "").equals(Constants.AP_REJECT_TYPE_REJECT)) { // 却下
				apEndTan.setApStatus(Constants.AP_STATUS_END_TAN_REJECT);
				histOperation = HIST_OPERATION_REJECT;
			} else { // 差戻し
				apEndTan.setApStatus(Constants.AP_STATUS_END_TAN_SENDBACK);
				apEndTan.setEappId(null); // 書類IDクリア
				histOperation = HIST_OPERATION_SENDBACK;
			}

			// コメント
			apEndTan.setUpdateComment(rejectReason);

			updateApEndTan(execStaffCode, null, apEndTan, false, false, true, histOperation);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#approveApEndTan(java.lang.Long, java.lang.String)
	 */
	public void approveApEndTan(Long eappId, String execStaffCode) throws AppException{
		//////////////////////////////////// 更新
		ApEndTan apEndTan = getApEndTan(eappId, true);

		CodeName appRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_TAN, null, apEndTan.getApCompanyCode(), null);

		// 除売却申請をe申請で承認する会社は承認済に更新
		if(appRoute != null && Function.nvl(appRoute.getValue2(), "").equals(Constants.FLAG_YES)) {
			// ステータス
			apEndTan.setApStatus(Constants.AP_STATUS_END_TAN_APPROVE);
			// 更新コメント
			apEndTan.setUpdateComment(null);
			//	承認日
			apEndTan.setApproveDate(new Date());

			updateApEndTan(execStaffCode, null, apEndTan, false, false, true, HIST_OPERATION_APPROVE);
		}
	}

	/*
	 * 明細データの削除
	 */
	private void deleteLine(String loginStaffCode, ApEndTan obj, ApEndTanDAO apEndTanDAO) throws SQLException {
		apEndTanDAO.deleteApEndTanLineFld(obj.getApplicationId());
		apEndTanDAO.deleteApEndTanLineAst(obj.getApplicationId());
		apEndTanDAO.deleteApEndTanLineLic(obj.getApplicationId());
		apEndTanDAO.deleteApEndTanLineAtt(obj.getApplicationId());
	}

	/**
	 * 不正セット項目値の調整
	 * @param obj 移動申請データ
	 * @throws AppException
	 */
	private void setPropertyAdjust(ApEndTan obj) throws AppException {
		//	廃棄？
		if(obj.getApEndTanType().equals("1")){
			//	売却関連の項目クリア
			obj.setTotalSalesAmount(null);
			//	固定資産一覧
			if(obj.getApEndTanLineFldList() != null && obj.getApEndTanLineFldList().size() > 0){
				for(int i = 0; i < obj.getApEndTanLineFldList().size(); i++){
					ApEndTanLineFld item = obj.getApEndTanLineFldList().get(i);
					item.setSalesAmount(null);
				}
			}
		}

		// 稟議書・経営会議承認済チェックが付いていない場合は、承認日クリア
		if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {
			obj.setSpecialApproveDate(null);
		}

	}

	/**
	 * 金額範囲自動設定
	 * @param obj 除売却申請データ
	 * @throws AppException
	 */
	private void setAmounRange(ApEndTan obj) throws AppException {
		if(!Function.nvl(obj.getTotalBookAmount(), "").equals("")) {
			List<CodeName> amountRangeList = new ArrayList<CodeName>();
			//	有形
			if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
				amountRangeList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_AP_END_TAN_AMOUNT_RANGE, null, obj.getApCompanyCode(), null);
			}
			//	無形
			else{
				//	1行目の固定資産データを取得
				if(obj.getApEndTanLineFldList() != null && obj.getApEndTanLineFldList().size() > 0) {
					ApEndTanLineFld fld = obj.getApEndTanLineFldList().get(0);
					CodeName codeName = null;
					String parentInternalCode = "";
					// 社内使用？
					codeName = masterSession.getCodeName(Constants.PPFS_FLD_SHURUICD_INT_SW1, fld.getAstClass(), null, null);
					if(codeName == null){
						// 市販？
						codeName = masterSession.getCodeName(Constants.PPFS_FLD_SHURUICD_INT_SW2, fld.getAstClass(), null, null);
						if(codeName == null){
							//	長期前払
							parentInternalCode = Constants.AP_GET_TYPE_INT3;
						}
						else{
							parentInternalCode = Constants.AP_GET_TYPE_INT2;
						}
					}
					else{
						parentInternalCode = Constants.AP_GET_TYPE_INT1;
					}
					amountRangeList = masterSession.getCodeNameListByParent(Constants.CATEGORY_CODE_AP_END_INT_AMOUNT_RANGE, parentInternalCode, obj.getApCompanyCode());
				}
			}
			//	金額範囲の設定
			if(amountRangeList != null && amountRangeList.size() > 0){
				for(int i = 0; i < amountRangeList.size(); i++){
					CodeName amountRange = amountRangeList.get(i);
					//	上限範囲内？
					if( !Function.nvl(amountRange.getValue3(), "").equals("") && Long.valueOf(amountRange.getValue3()).compareTo(obj.getTotalBookAmount()) >= 0 ){
						obj.setApEndTanAmountRange(amountRange.getInternalCode());
						obj.setApEndTanAmountRangeName(amountRange.getValue1());
						break;
					}
					//	上限なしの金額範囲？
					else if( Function.nvl(amountRange.getValue3(), "").equals("") ){
						obj.setApEndTanAmountRange(amountRange.getInternalCode());
						obj.setApEndTanAmountRangeName(amountRange.getValue1());
						break;
					}
				}
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#getFldYskCalc(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.util.List)
	 */
	public List<ApEndTanLineFld> getFldYskCalc(String loginStaffCode, String accessLevel, String companyCode, String period, Date returnDate, List<ApEndTanLineFld> list) throws AppException {

		//	返却予定日が設定されていない場合、除売却時簿価計算をおこなわない（デフォルト値=前月末簿価）
		if(Function.nvl(returnDate, "").equals("")){
			return list;
		}

		int skkyydoFromYear = Integer.valueOf(period.substring(0, 4));	//	会計年度
		int skkyydoFromMonth = Integer.valueOf(period.substring(4));	//	会計月
		//	返却年月
		Calendar returnCalendar = Calendar.getInstance();
		returnCalendar.setTime(returnDate);
		int skkyydoToYear = returnCalendar.get(Calendar.YEAR);	//	返却年度
		int skkyydoToMonth = returnCalendar.get(Calendar.MONTH) + 1;	//	返却月
		boolean skkyydoFlag = false;	//	年度範囲検索フラグ

		//	返却年月が、会計年月の前に設定？ 会計年月と返却年月が同じ？
		if((skkyydoFromYear == skkyydoToYear && skkyydoFromMonth > skkyydoToMonth)
		|| (skkyydoFromYear > skkyydoToYear)
		){
			return list;
		}

		//	会計月数が１，２，３月？
		if(skkyydoFromMonth == 1 || skkyydoFromMonth == 2 || skkyydoFromMonth == 3){
			//	年度を一つ下げる
			skkyydoFromYear = skkyydoFromYear - 1;
		}
		//	返却月数が１，２，３月？
		if(skkyydoToMonth == 1 || skkyydoToMonth == 2 || skkyydoToMonth == 3){
			//	年度を一つ下げる
			skkyydoToYear = skkyydoToYear - 1;
		}
		//	年度が異なる？
		if(skkyydoFromYear < skkyydoToYear){
			skkyydoFlag = true;
		}

		///	固有番号複数条件作成
		String koyunoPlueal = "";
		for(int i = 0; i < list.size(); i++){
			ApEndTanLineFld item = list.get(i);
			if(i == 0){
				koyunoPlueal = "" + item.getPpId();
			}
			else{
				koyunoPlueal = koyunoPlueal + "," + item.getPpId();
			}
		}

		//	償却費予測結果取得
		List<PpfsYskCalc> ppfsYskCalcList = new ArrayList<PpfsYskCalc>();
		if(!skkyydoFlag){
			ppfsYskCalcList = searchPpfsYskCalc(companyCode, skkyydoFromYear, koyunoPlueal, null);
		}
		else{
			//	会計年度範囲指定
			ppfsYskCalcList = searchPpfsYskCalc(companyCode, skkyydoFromYear, skkyydoToYear, koyunoPlueal, null);
		}

//		if(ppfsYskCalcList != null && ppfsYskCalcList.size() > 0){
			//	除売却時簿価計算
			for(int i = 0; i < list.size(); i++){
				ApEndTanLineFld item = list.get(i);
				Long skkTotalAmount = 0L;
				for(int j = 0; j < ppfsYskCalcList.size(); j++){
					PpfsYskCalc item2 = new PpfsYskCalc();
					//	固有番号が一緒？
					if(ppfsYskCalcList.get(j).getKoyuno().equals(item.getPpId())){
						item2 = ppfsYskCalcList.get(j);
						//	年度範囲？
						if(!skkyydoFlag){
							//	[会計年月の月数の次月から3月までの償却費合計金額] - [返却予定月から3月までの償却費合計金額]
							skkTotalAmount = getSkkAmountTotalFrom(skkyydoFromMonth, item2, true) - getSkkAmountTotalFrom(skkyydoToMonth, item2, false);
							continue;
						}
						else{
							//	会計年度と一緒？
							if( skkyydoFromYear == Integer.valueOf(Function.nvl(item2.getSkkyydo(), "0")) ){
								//	会計年月の月数の次月から3月までの償却額合計値計算
								skkTotalAmount = skkTotalAmount + getSkkAmountTotalFrom(skkyydoFromMonth, item2, true);
							}
							//	返却年度と一緒？
							else if( skkyydoToYear == Integer.valueOf(Function.nvl(item2.getSkkyydo(), "0"))){
								//	返却年月の月数の前月までの償却額合計値計算
								skkTotalAmount = skkTotalAmount + getSkkAmountTotalTo(skkyydoToMonth, item2, true);
								continue;
							}
							else{
								//	1年度の償却額合計値計算
								skkTotalAmount = skkTotalAmount + getSkkAmountTotalTo(3, item2, false);
							}
						}
					}
				}

				//	除売却時簿価 = [現在帳簿価額] - [償却額合計値]
				Long astBookAmount = item.getAstDefBookAmount() - skkTotalAmount;
				item.setAstBookAmount(astBookAmount);
			}
//		}

		return list;
	}


	/**
	 * 償却費合計値
	 * @param month 対象月
	 * @param item 償却費予測結果クラス
	 * @param flag 対象月減算フラグ
	 * @return 償却費合計値
	 */
	private Long getSkkAmountTotalFrom(int month, PpfsYskCalc item, boolean flag){
		Long total = 0L;
		//	4月
		if(month == 4){
			//	4月～3月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
					+ item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
					+ item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk1();
			}
		}
		//	5月
		else if(month == 5){
			//	5月～3月までの合計値
			total = item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
					+ item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
					+ item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk2();
			}
		}
		//	6月
		else if(month == 6){
			//	6月～3月までの合計値
			total = item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
			+ item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
			+ item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk3();
			}
		}
		//	7月
		else if(month == 7){
			//	7月～3月までの合計値
			total = item.getFtskkgk4() + item.getFtskkgk5()
				+ item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
				+ item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk4();
			}
		}
		//	8月
		else if(month == 8){
			//	9月～3月までの合計値
			total = item.getFtskkgk5() + item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
			+ item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk5();
			}
		}
		//	9月
		else if(month == 9){
			//	9月～3月までの合計値
			total = item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
			+ item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk6();
			}
		}
		//	10月
		else if(month == 10){
			//	10月～3月までの合計値
			total = item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
			+ item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk7();
			}
		}
		//	11月
		else if(month == 11){
			//	11月～3月までの合計値
			total = item.getFtskkgk8() + item.getFtskkgk9()
			+ item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk8();
			}
		}
		else if(month == 12){
			//	12月～3月までの合計値
			total = item.getFtskkgk9() + item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk9();
			}
		}
		else if(month == 1){
			//	1月～3月までの合計値
			total = item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk10();
			}
		}
		else if(month == 2){
			//	2月～3月までの合計値
			total = item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk11();
			}
		}
		else if(month == 3){
			total = item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk12();
			}
		}
		return total;
	}
	/**
	 * 償却費合計値
	 * @param month 対象月
	 * @param item 償却費予測結果クラス
	 * @param flag 対象月減算フラグ
	 * @return 償却費合計値
	 */
	private Long getSkkAmountTotalTo(int month, PpfsYskCalc item, boolean flag){
		Long total = 0L;
		//	4月
		if(month == 4){
			//	4月までの合計値
			total = item.getFtskkgk1();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk1();
			}
		}
		//	5月
		else if(month == 5){
			//	4月～5月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk2();
			}
		}
		//	6月
		else if(month == 6){
			//	4月～6月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk3();
			}
		}
		//	7月
		else if(month == 7){
			//	4月～7月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk4();
			}
		}
		//	8月
		else if(month == 8){
			//	4月～8月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk5();
			}
		}
		//	9月
		else if(month == 9){
			//	4月～9月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
					+ item.getFtskkgk6();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk6();
			}
		}
		//	10月
		else if(month == 10){
			//	4月～10月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
					+ item.getFtskkgk6() + item.getFtskkgk7();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk7();
			}
		}
		//	11月
		else if(month == 11){
			//	4月～11月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
					+ item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk8();
			}
		}
		else if(month == 12){
			//	4月～12月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
					+ item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk9();
			}
		}
		else if(month == 1){
			//	4月～1月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
					+ item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
					+ item.getFtskkgk10();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk10();
			}
		}
		else if(month == 2){
			//	4月～2月までの合計値
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
					+ item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
					+ item.getFtskkgk10() + item.getFtskkgk11();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk11();
			}
		}
		else if(month == 3){
			total = item.getFtskkgk1() + item.getFtskkgk2() + item.getFtskkgk3() + item.getFtskkgk4() + item.getFtskkgk5()
					+ item.getFtskkgk6() + item.getFtskkgk7() + item.getFtskkgk8() + item.getFtskkgk9()
					+ item.getFtskkgk10() + item.getFtskkgk11() + item.getFtskkgk12();
			//	対象月を償却額合計に含めない？
			if(flag){
				total = total - item.getFtskkgk12();
			}
		}
		return total;
	}

	/**
	 * 償却費予測検索結果一覧
	 * @param companyCode 会社コード
	 * @param skkyydo 償却年度
	 * @param koyunoPlueal 固有番号複数
	 * @param koyuno 固有番号
	 * @return 償却費予測結果一覧
	 */
	private List<PpfsYskCalc> searchPpfsYskCalc(String companyCode, Integer skkyydo, String koyunoPlueal, String koyuno) throws AppException{
		try{
			ApEndTanDAO apEndTanDAO = new ApEndTanDAO();
			return apEndTanDAO.selectPpfsYskCalc(companyCode, skkyydo, koyunoPlueal, koyuno);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "償却費予測結果取得"), e);
		}
	}

	/**
	 * 償却費予測検索結果一覧
	 * @param companyCode 会社コード
	 * @param periodFrom 償却年度(FROM)
	 * @param periodTo 償却年度(To)
	 * @param koyunoPlueal 固有番号複数
	 * @param koyuno 固有番号
	 * @return 償却費予測結果一覧
	 */
	private List<PpfsYskCalc> searchPpfsYskCalc(String companyCode, Integer skkyydoFrom, Integer skkyydoTo, String koyunoPlueal, String koyuno) throws AppException{
		try{
			ApEndTanDAO apEndTanDAO = new ApEndTanDAO();
			return apEndTanDAO.selectPpfsYskCalc(companyCode, skkyydoFrom, skkyydoTo, koyunoPlueal, koyuno);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "償却費予測結果取得"), e);
		}
	}


	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateApEndTan(String loginStaffCode, String accessLevel, ApEndTan obj, ApEndTan objOld) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 項目定義バリデーション
		int apStatus = obj.getApStatus() == null ? null : Integer.valueOf(obj.getApStatus());

		if(!Function.isAccessLevelAdmin(accessLevel)) { // 全社権限ではない場合
			apStatus += 6;	//	?
		}

		String categoryCode = "";
		//	有形？
		if(obj.getApFldType().equals(Constants.PPFS_FLD_TAN)){
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_AP_END_TAN;
		}
		else{
			categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_AP_END_INT;
		}

		// ヘッダ
		errMsg.append(masterSession.validateItemDef(categoryCode, "NEA_AP_END_TAN", obj, apStatus, null));

		///////////////////////	ヘッダ条件付必須

		// 明細
		//	固定資産明細作成
		List<ApEndTanLineFld> apEndTanLineFldList = obj.getApEndTanLineFldList();
		if(apEndTanLineFldList != null && apEndTanLineFldList.size() > 0){
			for(int i = 0; i < apEndTanLineFldList.size(); i++){
				ApEndTanLineFld apEndTanLineFld = apEndTanLineFldList.get(i);
				errMsg.append(masterSession.validateItemDef(categoryCode, "NEA_AP_END_TAN_LINE_FLD", apEndTanLineFld, apStatus, null));
			}
		}
		//	情報機器等明細作成
		List<ApEndTanLineAst> apEndTanLineAstList = obj.getApEndTanLineAstList();
		if(apEndTanLineAstList != null && apEndTanLineAstList.size() > 0){
			for(int i = 0; i < apEndTanLineAstList.size(); i++){
				ApEndTanLineAst apEndTanLineAst = apEndTanLineAstList.get(i);
				errMsg.append(masterSession.validateItemDef(categoryCode, "NEA_AP_END_TAN_LINE_AST", apEndTanLineAst, apStatus, null));
			}
		}
		//	ﾗｲｾﾝｽ明細作成
		List<ApEndTanLineLic> apEndTanLineLicList = obj.getApEndTanLineLicList();
		if(apEndTanLineLicList != null && apEndTanLineLicList.size() > 0){
			for(int i = 0; i < apEndTanLineLicList.size(); i++){
				ApEndTanLineLic apEndTanLineLic = apEndTanLineLicList.get(i);
				errMsg.append(masterSession.validateItemDef(categoryCode, "NEA_AP_END_TAN_LINE_LIC", apEndTanLineLic, apStatus, null));
			}
		}
		// 添付明細作成
		List<ApEndTanLineAtt> apEndTanLineAttList = obj.getApEndTanLineAttList();
		if(apEndTanLineAttList != null && apEndTanLineAttList.size() > 0){
			for(int i = 0; i < apEndTanLineAttList.size(); i++){
				ApEndTanLineAtt apEndTanLineAtt = apEndTanLineAttList.get(i);
				errMsg.append(masterSession.validateItemDef(categoryCode, "NEA_AP_END_TAN_LINE_ATT", apEndTanLineAtt, apStatus, null));
			}
		}

		String stat = Function.nvl(obj.getApStatus(), ""); // ステータス

		//////////////////////////////////// 条件付必須
		//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)
		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_END_TAN_APPROVE)
				&& !stat.equals(Constants.AP_STATUS_END_TAN_REJECT)
				&& !stat.equals(Constants.AP_STATUS_END_TAN_CANCEL)) {

			//	売却？
			if(obj.getApEndTanType().equals("2")){
				//	売却先
				if(Function.nvl(obj.getDisposeCompanyName(), "").equals("")){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "除売却情報-売却先"));
				}
				//	売却価格
				if(Function.nvl(obj.getTotalSalesAmount(), "").equals("")){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "除売却情報-売却価格"));
				}
			}
			// 承認日
			if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) {
				if(obj.getSpecialApproveDate() == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "承認日", "稟議書・経営会議等承認済がチェックされている場合は"));
				}
			}

			// 申請者
			if(!Function.nvl(obj.getApStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_END_TAN_APPROVE)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_END_TAN_REJECT)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_END_TAN_CANCEL)) {
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

			// 資産計上部課コード
			if(!Function.nvl(obj.getCostSectionCode(), "").equals("")) {
				boolean currentYearErrorFlag = false;
				// 未申請・申請中
				if(stat.equals(Constants.AP_STATUS_BGN_INT_NOAPPLY) ||
				   stat.equals(Constants.AP_STATUS_BGN_INT_APPLY)) {
					// カレント年度取得
					CodeName currentYearCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
					int currentYear = Integer.valueOf(currentYearCodeName.getValue1());
					Integer costSectionYear = obj.getCostSectionYear();
					if(costSectionYear == null || costSectionYear.intValue() != currentYear) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "資産計上部課 : 当年度の経費負担部課を入力してください。"));
						currentYearErrorFlag = true;
					}
				}
				if(!currentYearErrorFlag && masterSession.getCostSection(obj.getCostCompanyCode(), obj.getCostSectionCode(), null, null) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "資産計上部課"));
				}
			}
		}

		///////////////////////////////		その他バリデーション
		// 承認済・却下・取下げ以外
		if(!stat.equals(Constants.AP_STATUS_END_TAN_APPROVE)
				&& !stat.equals(Constants.AP_STATUS_END_TAN_REJECT)
				&& !stat.equals(Constants.AP_STATUS_END_TAN_CANCEL)) {
			//	除売却対象がない？
			if(apEndTanLineFldList == null || apEndTanLineFldList.size() == 0){
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "除売却対象を選択してください。"));
			}

			/////////////	固定資産一覧バリデーション
			//	売却額の計算
			Long totalSalesAmountLine = 0L;
			//	資産番号は、除売却申請検索を使用するので文字列
			String astNumPlural = "";
			//	固定資産番号は、Ppfs未承認Viewから取得するのでList
			String koyunoPlural = "";
			//	種類チェック
			boolean astClassFlag = false;
			//	種類チェック
			CodeName beforeCodeName = null;
			//	資産混在チェック
			boolean tempFlagCheck = false;
			//	資産混在チェック用仮勘定フラグ
			String beforeTempFlag = "";
			//	売却区分
			boolean salesFlag = false;

			if(apEndTanLineFldList != null && apEndTanLineFldList.size() > 0){

				for(int i = 0; i < apEndTanLineFldList.size(); i++){
					ApEndTanLineFld item = apEndTanLineFldList.get(i);
					//	資産混在チェック
					if(i == 0){
						beforeTempFlag = item.getTempFlag();
					}
					else{
						//	資産混在？
						if(!Function.nvl(item.getTempFlag(), "").equals(beforeTempFlag)){
							tempFlagCheck = true;;
						}
					}
					//	売却区分且つ、資産区分が仮勘定？
					if(obj.getApEndTanType().equals("2") && item.getTempFlag().equals(Constants.FLAG_YES)){
						salesFlag = true;
					}
					////////////////	資産一覧売却金額合計値計算
					totalSalesAmountLine = totalSalesAmountLine + Function.nvl(item.getSalesAmount(), 0L);

					//	資産番号一覧
					if(!astNumPlural.equals("")){
						astNumPlural = astNumPlural + " " + item.getAstNum();
					}
					else{
						astNumPlural = item.getAstNum();
					}
					//	固有番号一覧
					if(!koyunoPlural.equals("")){
						koyunoPlural = koyunoPlural + " " + item.getPpId();
					}
					else{
						koyunoPlural = String.valueOf(item.getPpId());
					}

					///////////	資産種類チェック
					//	無形固定資産?
					if(obj.getApFldType().equals(Constants.PPFS_FLD_INT)){
						if(!astClassFlag){
							CodeName codeName = new CodeName();
							//	社内使用？
							codeName = masterSession.getCodeName(Constants.PPFS_FLD_SHURUICD_INT_SW1, item.getAstClass(), null, null);
							if(codeName != null ){
								if(i != 0){
									if(beforeCodeName != null){
										//	1つ前のカテゴリーコードが違う？
										if(!codeName.getCategoryCode().equals(beforeCodeName.getCategoryCode())){
											astClassFlag = true;
										}
									}
									else{
										//	1つ前が長期前払費用
										astClassFlag = true;
									}
								}
							}
							else {
								//	市販目的？
								codeName = masterSession.getCodeName(Constants.PPFS_FLD_SHURUICD_INT_SW2, item.getAstClass(), null, null);
								if(codeName != null){
									if(i != 0){
										if(beforeCodeName != null){
											//	1つ前のカテゴリーコードが違う？
											if(!codeName.getCategoryCode().equals(beforeCodeName.getCategoryCode())){
												astClassFlag = true;
											}
										}
										else{
											//	1つ前が長期前払費用
											astClassFlag = true;
										}
									}
								}
								//	長期前払費用？
								else{
									if(i != 0){
										//	1つ前と違う？
										if(beforeCodeName != null){
											astClassFlag = true;
										}
									}
								}
							}
							beforeCodeName = codeName;
						}
					}
				}
			}

			//	資産混在？
			if(tempFlagCheck){
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "本勘定と仮勘定の資産を同じ申請書に含める事はできません。"));
			}
			//	売却区分で仮勘定？
			if(salesFlag){
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "「除売却区分」が”売却”の場合、仮勘定の資産を申請対象に含める事はできません。"));
			}

			//	売却価格
			//	除却申請区分：売却？
			if(obj.getApEndTanType().equals("2")){
				if(apEndTanLineFldList != null && apEndTanLineFldList.size() > 0){
					//	除売却情報の売却額と固定資産の売却額が異なる？
					if( !Function.nvl(obj.getTotalSalesAmount(), "").equals("") && obj.getTotalSalesAmount().compareTo(totalSalesAmountLine) != 0){
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "売却価格が固定資産の売却額の合計金額と異なります。"));
					}
				}
			}

			//	資産が"市販目的ソフトウェア"、"社内使用ソフトウェア"、"長期前払費用/その他無形固定資産除売却申請書"のいずれかが混在する？
			if(astClassFlag){
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "「種類」が社内使用ソフトウェア（市場販売ソフトウェア）の資産は、他種類の資産と同時には申請できません。申請書を分けて作成してください。"));
			}

			////////////////////////////	既に除却申請データあり？もしくは、Ppfs未承認データあり？チェック
			if(apEndTanLineFldList != null && apEndTanLineFldList.size() > 0){
				//	未申請、申請中の除却申請データ検索
				ApEndTanSC apEndTanSC = new ApEndTanSC();
				//	取得申請設定
				if(!Function.nvl(obj.getApplicationId(), "").equals("")) apEndTanSC.setApplicationId(obj.getApplicationId());
				//	資産番号複数セット
				apEndTanSC.setAstNumPlural(astNumPlural);
				//	ステータスのセット
				apEndTanSC.setApStatus( Constants.AP_STATUS_END_TAN_NOAPPLY + " " + Constants.AP_STATUS_END_TAN_APPLY + " " + Constants.AP_STATUS_END_TAN_SENDBACK);
				List<ApEndTanLineFld> apEndTanLineFldListApply = searchApEndTanLineApply(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, apEndTanSC);
				//	既に未申請、申請中の除却申請データある？
				if(apEndTanLineFldListApply != null && apEndTanLineFldListApply.size() > 0){
					for(int i = 0; i < apEndTanLineFldListApply.size(); i++){
						ApEndTanLineFld apEndTanLineFld = apEndTanLineFldListApply.get(i);
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に既に申請処理中の資産が含まれているため処理を継続できません。(資産番号：" + apEndTanLineFld.getAstNum() + ")"));
					}
				}
				//	ProPlusに承認前データが既に存在する？
				List<PpfsUnUpd> ppfsUnUpdList = fldSession.searchPpfsUnUpd(obj.getApCompanyCode(), koyunoPlural);
				if(ppfsUnUpdList != null && ppfsUnUpdList.size() > 0){
					for(int i = 0; i < ppfsUnUpdList.size(); i++){
						PpfsUnUpd ppfsUnUpd = ppfsUnUpdList.get(i);
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に資産情報更新中の資産が含まれているため処理を継続できません。(資産番号：" + ppfsUnUpd.getOya() + ppfsUnUpd.getEda() + ")"));
					}
				}
			}
		}

		// 申請中
		if(stat.equals(Constants.AP_STATUS_END_TAN_APPLY) && objOld != null) {
			// 変更前後が申請中の場合
			if(Function.nvl(objOld.getApStatus(), "").equals(Constants.AP_STATUS_END_TAN_APPLY)) {
				// 金額範囲が変更されている
				String newRange = Function.nvl(obj.getApEndTanAmountRange(), "");
				String oldRange = Function.nvl(objOld.getApEndTanAmountRange(), "");
				if(!newRange.equals(oldRange)) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "除売却時簿価合計", "申請中のデータに対して除売却時簿価合計は、申請回付経路が変更されない範囲内でしか変更できません。(" + Function.nvl(objOld.getApEndTanAmountRangeName(), "") +  " から " + Function.nvl(obj.getApEndTanAmountRangeName(), "") +  " へ変更されました。)"));
				}
			}
		}

		return errMsg.toString();

	}

	/**
	 * 除売却固定資産明細チェック
	 * @param 検索条件
	 * @return 除売却固定資産明細一覧
	 */
	private List<ApEndTanLineFld> searchApEndTanLineApply(String loginStaffCode, String accessLevel, ApEndTanSC searchParam) {
		try{
			ApEndTanDAO apEndTanDAO = new ApEndTanDAO();
			return apEndTanDAO.selectApEndTanLineFldApply(searchParam);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "除売却固定資産明細取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#reportApEndTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_tan.ApEndTan)
	 */
	public void reportApEndTan(String loginStaffCode, String accessLevel, ApEndTan obj) throws AppException {
		//////////////////////////////////// データ更新&報告メール送信
		////////// データ更新
		obj.setReportDate(new Date());
		obj.setReportFlag(Constants.FLAG_YES);
		updateApEndTan(loginStaffCode, accessLevel, obj, true, false, true, HIST_OPERATION_REPORT);

		// 取得形態取得
		String apGetIntTypeName = "";
		if(!Function.nvl(obj.getApEndTanAmountRange(), "").equals("")) {
			CodeName amountRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_END_INT_AMOUNT_RANGE, obj.getApEndTanAmountRange(), obj.getApCompanyCode(), null);

			if(amountRange != null) {
				CodeName getType = masterSession.getCodeName(amountRange.getParentCategoryCode(), amountRange.getParentInternalCode(), obj.getApCompanyCode(), null);
				if(getType != null) {
					apGetIntTypeName = getType.getValue1();
				}
			}
		}

		////////// メール送信
		try {
			DecimalFormat dcf = new DecimalFormat();
			// 件名・本文取得
			CodeName mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_REPORT_END_INT, null, obj.getApCompanyCode(), null);
			// 宛先取得
			//	TO先指定
			List<String> toList = new ArrayList<String>();
			if(mailTemplate.getValue2() != null) toList.add(mailTemplate.getValue3());
			//	CC先指定
			List<String> ccList = new ArrayList<String>();
			User apStaff = masterSession.getStaff(null, obj.getApStaffCode()); // 申請者
			if(apStaff != null) ccList.add(apStaff.getMailAddress());
			User loginStaff = masterSession.getStaff(null, loginStaffCode); // 処理実行者
			if(loginStaff != null) ccList.add(loginStaff.getMailAddress());

			String subject = mailTemplate.getValue1();
			String body = mailTemplate.getValue2();

			// 予約語置換
			//	除売却時簿価合計金額
			String totalBookStr = "";
			if(obj.getTotalBookAmount() != null) totalBookStr = dcf.format(obj.getTotalBookAmount());

			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AP_STAFF_NAME, Function.nvl(obj.getApStaffName(), ""));	//	申請者
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_APPLICATION_ID, Function.nvl(obj.getApplicationId(), ""));	//	申請書番号
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_GET_TYPE, Function.nvl(apGetIntTypeName, ""));	//	取得形態
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AP_END_TAN_TYPE_NAME, Function.nvl(obj.getApEndTanTypeName(), ""));	//	除却区分
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AP_REASON_NAME, obj.getApReasonName());	//	除却理由
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_TOTAL_BOOK_AMOUNT, totalBookStr);	//	除売却時簿価

			body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_STAFF_NAME, Function.nvl(obj.getApStaffName(), ""));	//	申請者
			body = body.replaceAll(MAIL_TEMPLATE_VAR_APPLICATION_ID, Function.nvl(obj.getApplicationId(), ""));	//	申請書番号
			body = body.replaceAll(MAIL_TEMPLATE_VAR_GET_TYPE, Function.nvl(apGetIntTypeName, ""));	//	取得形態
			body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_END_TAN_TYPE_NAME, Function.nvl(obj.getApEndTanTypeName(), ""));	//	除却区分
			body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_REASON_NAME, Function.nvl(obj.getApReasonName(), ""));	//	除却理由
			body = body.replaceAll(MAIL_TEMPLATE_VAR_TOTAL_BOOK_AMOUNT, totalBookStr);	//	除売却時簿価

			// 送信
			sendMailSession.sendMail(loginStaff.getMailAddress(), toList, ccList, subject, body);
		} catch (Exception e) {
			throw new AppException(Msg.getBindMessage(Msg.ERR_MSG, "除売却報告メール送信処理に失敗しました。"));
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#remindApEndTan(java.lang.String, java.lang.String, java.util.List)
	 */
	public void remindApEndTan(String loginStaffCode, String accessLevel,List<ApEndTan> applicationList) throws AppException{
		//////////////////////////////////// バリデーション
		StringBuffer errMsg = new StringBuffer();
		for(Iterator<ApEndTan> iter = applicationList.iterator(); iter.hasNext();) {
			ApEndTan apEndTanListData = iter.next();
			ApEndTan apEndTan = getApEndTan(apEndTanListData.getApplicationId(), true);

			// バージョンチェック
			if(apEndTanListData.getVersion().intValue() != apEndTan.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, apEndTanListData.getApplicationId(), Msg.ERR_MSG_VER_LIST));
			}

			// ステータスチェック(承認済のみ可)
			if(!Function.nvl(apEndTan.getApStatus(), "").equals(Constants.AP_STATUS_END_TAN_APPROVE)) {
				// ステータスエラーは即座にスロー
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG_STAT_LIST, Constants.AP_STATUS_NAME_END_TAN_APPROVE));
			}
			// 除却報告状況チェック
			if(Function.nvl(apEndTan.getReportFlag(), "").equals(Constants.FLAG_YES)) {
				// 除却報告済みは即座にスロー
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG, "除却申請書番号:" + apEndTan.getApplicationId() + "は、除却報告されているためメール送信できません。"));
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
		// SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");
		for(Iterator<ApEndTan> iter = applicationList.iterator(); iter.hasNext();) {
			ApEndTan apEndTanListData = iter.next();
			ApEndTan apEndTan = getApEndTan(apEndTanListData.getApplicationId(), true);
			boolean mailSuccess = true;

			// 取得形態取得
			String apGetIntTypeName = "";
			if(!Function.nvl(apEndTan.getApEndTanAmountRange(), "").equals("")) {
				CodeName amountRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_END_INT_AMOUNT_RANGE, apEndTan.getApEndTanAmountRange(), apEndTan.getApCompanyCode(), null);

				if(amountRange != null) {
					CodeName getType = masterSession.getCodeName(amountRange.getParentCategoryCode(), amountRange.getParentInternalCode(), apEndTan.getApCompanyCode(), null);
					if(getType != null) {
						apGetIntTypeName = getType.getValue1();
					}
				}
			}

			////////// メール送信
			try {
				// 宛先取得
				User apStaff = masterSession.getStaff(null, apEndTan.getApStaffCode()); // 申請者

				List<String> toList = new ArrayList<String>();
				if(apStaff != null) toList.add(apStaff.getMailAddress());

				// 件名・本文取得
				CodeName mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_REMIND_END_INT, null, apEndTan.getApCompanyCode(), null);
				String subject = mailTemplate.getValue1();
				String body = mailTemplate.getValue2();

				// 予約語置換
				//	除売却時簿価合計金額
				String totalBookStr = "";
				if(apEndTan.getTotalBookAmount() != null) totalBookStr = dcf.format(apEndTan.getTotalBookAmount());

				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AP_STAFF_NAME, Function.nvl(apEndTan.getApStaffName(), ""));	//	申請者
				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_APPLICATION_ID, Function.nvl(apEndTan.getApplicationId(), ""));	//	申請書番号
				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_GET_TYPE, Function.nvl(apGetIntTypeName, ""));	//	取得形態
				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AP_END_TAN_TYPE_NAME, Function.nvl(apEndTan .getApEndTanTypeName(), ""));	//	除却区分
				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AP_REASON_NAME, apEndTan.getApReasonName());	//	除却理由
				subject = subject.replaceAll(MAIL_TEMPLATE_VAR_TOTAL_BOOK_AMOUNT, totalBookStr);	//	除売却時簿価

				body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_STAFF_NAME, Function.nvl(apEndTan.getApStaffName(), ""));	//	申請者
				body = body.replaceAll(MAIL_TEMPLATE_VAR_APPLICATION_ID, Function.nvl(apEndTan.getApplicationId(), ""));	//	申請書番号
				body = body.replaceAll(MAIL_TEMPLATE_VAR_GET_TYPE, Function.nvl(apGetIntTypeName, ""));	//	取得形態
				body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_END_TAN_TYPE_NAME, Function.nvl(apEndTan.getApEndTanTypeName(), ""));	//	除却区分
				body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_REASON_NAME, Function.nvl(apEndTan.getApReasonName(), ""));	//	除却理由
				body = body.replaceAll(MAIL_TEMPLATE_VAR_TOTAL_BOOK_AMOUNT, totalBookStr);	//	除売却時簿価

				// 送信
				sendMailSession.sendMail(loginStaff.getMailAddress(), toList, null, subject, body);

				////////// データ更新
				apEndTan.setReminderDate(new Date());
				updateApEndTan(loginStaffCode, accessLevel, apEndTan, false, false, true, HIST_OPERATION_REMIND);

			} catch (Exception e) {
				mailSuccess = false;
				reportMailBodyError.append("---------------------------------------------------------------");
				reportMailBodyError.append("申請書番号：" + apEndTan.getApplicationId() + "\n");
				reportMailBodyError.append(Function.getStackTraceStr(e));
				reportMailBodyError.append("\n");
				Logging.error(e.getMessage(), e);
			}

			reportMailBody.append(apEndTan.getApplicationId());
			reportMailBody.append("　" + apEndTan.getApStaffCode() + "　" + apEndTan.getApStaffName());
			if(!mailSuccess) reportMailBody.append("　※送信失敗");
			reportMailBody.append("\n");

		}

		//////////////////////////////////// 処理結果メール送信（処理者）
		StringBuffer body = new StringBuffer();
		body.append("申請書番号　申請者\n");
		body.append("---------------------------------------------------------------\n");
		body.append(reportMailBody);

		if(reportMailBodyError.length() > 0) {
			body.append("\n以下送信失敗時のエラーメッセージ\n");
		}

		sendMailSession.sendMail(loginStaff.getMailAddress(), loginStaff.getMailAddress(), null, "無形固定資産除売却申請-督促メール処理結果", body.toString());

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndTanSession#deleteExecApEndTan(java.lang.String, java.lang.String)
	 */
	public void deleteExecApEndTan(String applicationId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndTan apEndTan = getApEndTan(applicationId, true);

		if(apEndTan != null) {
			// 抹消処理実行フラグ
			apEndTan.setDeleteExecFlag(Constants.FLAG_YES);

			updateApEndTan(execStaffCode, null, apEndTan, false, false, true, HIST_OPERATION_DELETE_EXEC);
		}
	}

}