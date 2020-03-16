/*===================================================================
 * ファイル名 : ApEndReSessionBean.java
 * 概要説明   : 取得申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2017-11-06 1.0  申           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import jp.co.ctcg.easset.dao.ApEndReDAO;
import jp.co.ctcg.easset.dto.fld.PpfsUnUpd;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndRe;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndReLineLld;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndReSC;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndReSR;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndReLineAst;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndReLineAtt;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndReLineLic;
import jp.co.ctcg.easset.dto.asset.AssetSC;
import jp.co.ctcg.easset.dto.asset.AssetSR;
import jp.co.ctcg.easset.dto.license.LicenseSC;
import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.dto.lld.PpfsLldSC;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;
import jp.co.ctcg.easset.util.PdfExporter;
import jp.co.ctcg.easset.ws.ApEndReService;
import jp.co.ctcg.easset.ws.ApEndReServiceProxy;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;


import org.apache.commons.beanutils.PropertyUtils;


@Stateless
public class ApEndReSessionBean implements ApEndReSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession; // マスタセッション

	@EJB
	AssetSession assetSession; // 情報機器等セッション

	@EJB
	LicenseSession licenseSession; // ライセンスセッション

	@EJB
	HistSession histSession; // 履歴セッション

	@EJB
	FldSession fldSession; // 固定資産セッション

	@EJB
	LldSession lldSession; // 物件セッション

	private static final String ID_PREFIX = "RH";
	private static final String FILE_SAVE_ENTITY_NAME = "AP_END_RE";

	// 履歴作成用
	private static final String HIST_ENTITY_NAME = "AP_END_LE";
	private static final String HIST_OPERATION_CREATE = "新規作成";
	private static final String HIST_OPERATION_UPDATE = "更新";
	private static final String HIST_OPERATION_DELETE = "削除";
	private static final String HIST_OPERATION_APPLY = "申請";
	private static final String HIST_OPERATION_APPROVE_SUPERIOR = "受付完了";
	private static final String HIST_OPERATION_APPROVE = "承認";
	private static final String HIST_OPERATION_SENDBACK = "差戻し";
	private static final String HIST_OPERATION_REJECT = "却下";
	private static final String HIST_OPERATION_CANCEL_APPLY = "引戻し";
	private static final String HIST_OPERATION_DELETE_EXEC = "自動抹消";

	private static final String MIG_AMOUNT_RANGE_PREFIX = "MIG";


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#searchApEndRe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_re.ApEndReSC)
	 */
	public List<ApEndReSR> searchApEndRe(String loginStaffCode, String accessLevel, ApEndReSC searchParam) {
		try {
			ApEndReDAO apEndReDAO = new ApEndReDAO();
			return apEndReDAO.selectApEndReList(loginStaffCode, accessLevel, searchParam);
		} catch (SQLException e) {
			//	レンタル満了・解約申請検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了・解約申請検索"), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#getApEndRe(java.lang.String)
	 */
	public ApEndRe getApEndRe(String applicationId) {
		return getApEndRe(applicationId, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#getApEndRe(java.lang.String)
	 */
	public ApEndRe getApEndRe(Long eappId) {
		return getApEndRe(eappId, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#getApEndRe(java.lang.String, boolean)
	 */
	public ApEndRe getApEndRe(String applicationId, boolean lockFlag) {
		try {
			ApEndReDAO apEndReDAO = new ApEndReDAO();

			ApEndRe apEndRe = apEndReDAO.selectApEndRe(applicationId, lockFlag); // ヘッダの取得
			if(apEndRe != null) { // データ有
				apEndRe.setApEndReLineAttList(apEndReDAO.selectApEndReLineAtt(applicationId)); // 添付明細取得
				apEndRe.setApEndReLineAstList(apEndReDAO.selectApEndReLineAst(applicationId)); // 資産(機器)明細取得
				apEndRe.setApEndReLineLicList(apEndReDAO.selectApEndReLineLic(applicationId)); // ライセンス明細取得
				apEndRe.setApEndReLineLldList(apEndReDAO.selectApEndReLineLld(applicationId)); // 物件明細取得
			}

			return apEndRe;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了・解約申請取得"), e);
		}
	}

	/**
	 * 申請情報取得
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private ApEndRe getApEndRe(Long eappId, boolean lockFlag) {
		try {
			ApEndReDAO apEndReDAO = new ApEndReDAO();

			ApEndRe apEndRe = apEndReDAO.selectApEndRe(eappId, lockFlag); // ヘッダの取得
			if(apEndRe != null) {
				return getApEndRe(apEndRe.getApplicationId(), lockFlag);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了・解約申請取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#createApEndRe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_re.ApEndRe)
	 */
	public String createApEndRe(String loginStaffCode, String accessLevel, ApEndRe obj) throws AppException {
		return createApEndRe(loginStaffCode, accessLevel, obj, true);
	}

	/**
	 * 不正セット項目値の調整
	 * @param obj 情報機器等データ
	 * @throws AppException
	 */
	private void setPropertyAdjust(ApEndRe obj) throws AppException {

		// 稟議書・経営会議承認済チェックが付いていない場合は、承認日クリア
		if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {
			obj.setSpecialApproveDate(null);
		}

		// 移行データの再レンタル・返却場合は金額はnullのまま
		if(Function.nvl(obj.getApEndReAmountRange(), "").startsWith(MIG_AMOUNT_RANGE_PREFIX) && !Function.nvl(obj.getApEndReType(), "").equals(Constants.AP_END_RE_TYPE_CANCEL)) {
			obj.setTotalAmount(null);
		}
	}

	/**
	 * 金額範囲自動設定
	 * @param obj レンタル満了･解約申請データ
	 * @throws AppException
	 */
	private void setAmounRange(ApEndRe obj) throws AppException {
		// 移行データの場合は範囲を更新しない（移行用の範囲をそのまま）
		if(Function.nvl(obj.getApEndReAmountRange(), "").startsWith(MIG_AMOUNT_RANGE_PREFIX)) return;

		if(!Function.nvl(obj.getTotalAmount(), "").equals("")) {
			List<CodeName> amountRangeList = new ArrayList<CodeName>();
			String parentInternalCode = "";

			//
			if(obj.getApEndReType().equals("1")){
				if(obj.getReFlag().equals("1")){
					parentInternalCode = Constants.AP_END_RE_TYPE_RE;
				}else{
					parentInternalCode = Constants.AP_END_RE_TYPE_RETURN;
				}
			}else{
				parentInternalCode = Constants.AP_END_RE_TYPE_CANCEL;
			}
			amountRangeList = masterSession.getCodeNameListByParent(Constants.CATEGORY_CODE_AP_END_RE_AMOUNT_RANGE, parentInternalCode, obj.getApCompanyCode());


			//	金額範囲の設定
			if(amountRangeList != null && amountRangeList.size() > 0){
				for(int i = 0; i < amountRangeList.size(); i++){
					CodeName amountRange = amountRangeList.get(i);
					// 返却？
					if( Function.nvl(amountRange.getValue2(), "").equals("") && Function.nvl(amountRange.getValue3(), "").equals("")){
						obj.setApEndReAmountRange(amountRange.getInternalCode());
						break;
					}
					//	上限範囲内？
					else if( !Function.nvl(amountRange.getValue3(), "").equals("") && Long.valueOf(amountRange.getValue3()).compareTo(obj.getTotalAmount()) >= 0 ){
						obj.setApEndReAmountRange(amountRange.getInternalCode());
						break;
					}
					//	上限以上の金額範囲？
					else if( Function.nvl(amountRange.getValue3(), "").equals("") ){
						obj.setApEndReAmountRange(amountRange.getInternalCode());
						break;
					}
				}
			}
		}
	}

	/**
	 * レンタル満了・解約申請作成本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj レンタル満了・解約申請データ
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータのレンタル満了・解約申請書番号
	 */
	private String createApEndRe(String loginStaffCode, String accessLevel, ApEndRe obj, boolean isHistCreate) throws AppException {
		try{
			ApEndReDAO apEndReDAO = new ApEndReDAO();

			//////////////////////////////////// 固定値セット
			// 更新日・更新者s
			Date sysdate = new Date(); // システム日付取得
			obj.setCreateDate(sysdate);
			obj.setCreateStaffCode(loginStaffCode);
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);
			obj.setDeleteExecFlag("0");

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			errMsg.append(validateApEndRe(loginStaffCode, accessLevel, obj));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			///////////////////////////////////	金額範囲(残レンタル料/再レンタル金額)の自動設定
			setAmounRange(obj);

			//////////////////////////////////// IDの採番
			String applicationId = masterSession.nextValId(ID_PREFIX);

			//////////////////////////////////// データ作成
			// バージョン・改訂番号
			obj.setVersion(1);
			obj.setDisplayVersion(1);
			obj.setApplicationId(applicationId); // IDセット
			apEndReDAO.insertApEndRe(obj); // ヘッダ作成
			createLine(loginStaffCode, obj, apEndReDAO, applicationId); // 明細データ作成

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				histSession.createHistData(HIST_ENTITY_NAME, applicationId, HIST_OPERATION_CREATE, null);
			}

			return obj.getApplicationId();

		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了・解約申請作成:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了・解約申請作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#updateApEndRe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_re.ApEndRe)
	 */
	public void updateApEndRe(String loginStaffCode, String accessLevel, ApEndRe obj) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateApEndRe(loginStaffCode, accessLevel, obj, true, true, true, null);

		if(Constants.AP_STATUS_END_RE_APPROVE.equals(obj.getApStatus())){
			updatePpSendFlagApEndRe(obj);
		}


	}



	/**
	 * レンタル満了・解約申請更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj レンタル満了・解約申請データ
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isDisplayVersionUpdate 改訂番号更新有無 true:改訂番号をインクリメント false:改訂番号は更新しない
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @param operation 履歴作成時のオペレーション
	 * @throws AppException
	 */
	private void updateApEndRe(String loginStaffCode, String accessLevel, ApEndRe obj, boolean isLineUpdate, boolean isDisplayVersionUpdate, boolean isHistCreate, String operation) throws AppException {
		try {
			ApEndReDAO apEndReDAO = new ApEndReDAO();
			ApEndRe apEndReOld = getApEndRe(obj.getApplicationId(), true); // 現データの取得(データロック)

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
			if(obj.getVersion().intValue() != apEndReOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// バリデーション(登録画面更新の際のみ：連携等による更新時は行わない)
			if(isLineUpdate){
				setAmounRange(obj);
				errMsg.append(validateApEndRe(loginStaffCode, accessLevel, obj));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 登録完了フラグセット
//			obj.setRegistCompleteFlag(this.getRegistCompleteFlag(obj.getApEndReLineAstList(), obj.getApEndReLineLicList()));

			//////////////////////////////////// データ更新
			// バージョン・改訂番号
			obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
			if(isDisplayVersionUpdate) obj.setDisplayVersion(Function.nvl(obj.getDisplayVersion(), Integer.valueOf(1)) + 1);

			apEndReDAO.updateApEndRe(obj);

			if(isLineUpdate) {
				// 明細を一度削除
				deleteLine(loginStaffCode, obj, apEndReDAO);

				createLine(loginStaffCode, obj, apEndReDAO, obj.getApplicationId()); // 明細データ作成
			}

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				StringBuffer lineChangeColumnName = new StringBuffer();
				if(isLineUpdate) {
					// 明細変更確認
					if(Function.isListChange(obj.getApEndReLineLldList(), apEndReOld.getApEndReLineLldList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("物件明細");
					}
					if(Function.isListChange(obj.getApEndReLineAstList(), apEndReOld.getApEndReLineAstList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("資産(機器)明細");
					}
					if(Function.isListChange(obj.getApEndReLineLicList(), apEndReOld.getApEndReLineLicList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("ライセンス明細");
					}
					if(Function.isListChange(obj.getApEndReLineAttList(), apEndReOld.getApEndReLineAttList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("添付明細");
					}
				}

				histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), (operation == null ? HIST_OPERATION_UPDATE : operation), lineChangeColumnName.toString());
			}
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了・解約申請更新:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了・解約申請更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#applyApEndRe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_re.ApEndRe)
	 */
	public String applyApEndRe(String loginStaffCode, String accessLevel, ApEndRe obj) throws AppException {
		//////////////////////////////////// 移行データの場合は申請不可
		if(Function.nvl(obj.getApEndReAmountRange(), "").startsWith(MIG_AMOUNT_RANGE_PREFIX)) {
			throw new AppException("該当データは申請不可能なため、申請書を削除して新規に作成しなおしてください。");
		}

		String ret = null;
		boolean isNew = Function.nvl(obj.getApplicationId(), "").equals(""); // 新規の場合true

		////////////////////////////////////新規 or 更新呼び出し
		if(isNew){
			ret = createApEndRe(loginStaffCode, accessLevel, obj, false);
		}
		else{

			// 更新コメント
			obj.setUpdateComment(null);

			ret = obj.getApplicationId();

			// ステータス更新前バリデーション
			String errMsg = validateApEndRe(loginStaffCode, accessLevel, obj);
			if(errMsg.length() > 0) throw new AppException(errMsg);
		}

		//////////////////////////////////// ステータス更新&ステータス更新後バリデーション
		obj.setApStatus(Constants.AP_STATUS_END_RE_APPLY);
		String errMsg = validateApEndRe(loginStaffCode, accessLevel, obj);
		if(errMsg.length() > 0) throw new AppException(errMsg);

		///////////////////////////////////	金額範囲(再レンタル金額/残レンタル料)の自動設定
		if(!isNew){
		setAmounRange(obj);
		}
		//////////////////////////////////// 申請
		Long eappId = null;

		eappId = callEappService(obj); // e申請連携

		// e申請IDを更新
		obj.setEappId(eappId);
		if(isNew) { // 新規
			updateApEndRe(loginStaffCode, accessLevel, obj, false, false, false, null);
			histSession.createHistData(HIST_ENTITY_NAME, ret, HIST_OPERATION_APPLY, null); // 履歴作成
		} else {
			updateApEndRe(loginStaffCode, accessLevel, obj, true, true, true, HIST_OPERATION_APPLY);
		}

		return ret;

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#deleteApEndRe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_re.ApEndRe)
	 */
	public void deleteApEndRe(String loginStaffCode, String accessLevel, ApEndRe obj) throws AppException {
		try {
			ApEndReDAO apEndReDAO = new ApEndReDAO();

			ApEndRe apEndReOld = apEndReDAO.selectApEndRe(obj.getApplicationId(), true); // 現データの取得

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != apEndReOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新(履歴作成用にバージョンアップ)
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			apEndReOld.setUpdateDate(sysdate);
			apEndReOld.setUpdateStaffCode(loginStaffCode);

			// バージョン
			apEndReOld.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);

			// 更新コメント
			apEndReOld.setUpdateComment(null);

			apEndReDAO.updateApEndRe(apEndReOld);

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), HIST_OPERATION_DELETE, null);


			//////////////////////////////////// データ削除
			apEndReDAO.deleteApEndRe(obj.getApplicationId());
			apEndReDAO.deleteApEndReLineLld(obj.getApplicationId());
			apEndReDAO.deleteApEndReLineAst(obj.getApplicationId());
			apEndReDAO.deleteApEndReLineLic(obj.getApplicationId());
			apEndReDAO.deleteApEndReLineAtt(obj.getApplicationId());

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了・解約申請取得"), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#approveApEndRe(java.lang.Long, java.lang.String)
	 */
	public void approveApEndRe(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndRe apEndRe = getApEndRe(eappId, true);

		CodeName appRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_RE, null, apEndRe.getApCompanyCode(), null);

		// 除売却申請をe申請で承認する会社は承認済に更新
		if(appRoute != null && Function.nvl(appRoute.getValue2(), "").equals(Constants.FLAG_YES)) {
			// ステータス
			apEndRe.setApStatus(Constants.AP_STATUS_END_RE_APPROVE);
			// 更新コメント
			apEndRe.setUpdateComment(null);
			//	承認日
			apEndRe.setApproveDate(new Date());

			updateApEndRe(execStaffCode, null, apEndRe, false, false, true, HIST_OPERATION_APPROVE);

			if(Constants.AP_STATUS_END_RE_APPROVE.equals(apEndRe.getApStatus())){
				updatePpSendFlagApEndRe(apEndRe);
			}
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#approveSuperiorApEndRe(java.lang.Long, java.lang.String)
	 */
	public void approveSuperiorApEndRe(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndRe apEndRe = getApEndRe(eappId, true);

		CodeName appRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_RE, null, apEndRe.getApCompanyCode(), null);
		if(appRoute != null && Function.nvl(appRoute.getValue2(), "").equals(Constants.FLAG_YES)) { // 取得申請をe申請で承認する会社以外は受付承認フラグセット無し
			// 受付承認フラグ
//			apEndRe.setApproveSuperiorFlag(Constants.FLAG_YES);
		}
		// 更新コメント
		apEndRe.setUpdateComment(null);

		updateApEndRe(execStaffCode, null, apEndRe, false, false, true, HIST_OPERATION_APPROVE_SUPERIOR);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#cancelApplyApEndRe(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyApEndRe(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndRe apEndRe = getApEndRe(eappId, true);

		if(apEndRe != null) {
			// ステータス
			apEndRe.setApStatus(Constants.AP_STATUS_END_RE_NOAPPLY);
			// 更新コメント
			apEndRe.setUpdateComment(null);
			apEndRe.setEappId(null); // 書類IDクリア

			updateApEndRe(execStaffCode, null, apEndRe, false, false, true, HIST_OPERATION_CANCEL_APPLY);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#rejectApEndRe(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectApEndRe(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		String histOperation;

		//////////////////////////////////// 更新
		ApEndRe apEndRe = getApEndRe(eappId, true);

		if(apEndRe != null) {
			// ステータス
			if(Function.nvl(rejectType, "").equals(Constants.AP_REJECT_TYPE_REJECT)) { // 却下
				apEndRe.setApStatus(Constants.AP_STATUS_END_RE_REJECT);
				histOperation = HIST_OPERATION_REJECT;
			} else { // 差戻し
				apEndRe.setApStatus(Constants.AP_STATUS_END_RE_SENDBACK);
				apEndRe.setEappId(null); // 書類IDクリア
				histOperation = HIST_OPERATION_SENDBACK;
			}

			// コメント
			apEndRe.setUpdateComment(rejectReason);

			updateApEndRe(execStaffCode, null, apEndRe, false, false, true, histOperation);
		}
	}

	/**
	 * e申請サービス呼び出し
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(ApEndRe obj) throws AppException {
		// e申請WebServiceエンドポイント取得
		CodeName codeNameUrl = new CodeName();
		codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_END_RE, null, null);
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
				CodeName codeNameRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_END_RE_AMOUNT_RANGE, obj.getApEndReAmountRange(), obj.getApCompanyCode(), null);
				// タイトルセット
				String title = codeNameRange.getValue4();
				if(codeNameRange.getValue5() != null) {
					title = title + "\\n" + codeNameRange.getValue5();
				} else {
					title = "\\n" + title;
				}

				// サブタイトル(金額)セット
				String subTitle = Function.nvl(codeNameRange.getValue1(), "");
				if(codeNameRange.getValue6() != null) subTitle = codeNameRange.getValue6() + " " + subTitle;
				if(!Function.nvl(subTitle, "").equals("")) subTitle = "(" + subTitle + ")";

				//////////////////////////////////	タイトルの付加情報セット
				String dffTitle = "";
				String apEndTypeName = "";
				String contractNum = "";
				String endDate = "";
				// 件名付加情報：[申請区分]／[契約番号]／[リース・レンタル会社]
				// 契約番号取得
				if(obj.getApEndReLineLldList() != null && obj.getApEndReLineLldList().size() > 0){
					List<ApEndReLineLld> list = obj.getApEndReLineLldList();
					for(int i = 0; i < list.size(); i++){
						ApEndReLineLld item = list.get(i);
						if(i == 0){
							contractNum = item.getContractNum();
							if(item.getEndDate() != null) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
								endDate = "／" + dateFormat.format(item.getEndDate());
							}
						}
						// 申請区分取得
						if(item.getApEndReLineTypeName() != null){
							if(apEndTypeName.indexOf(item.getApEndReLineTypeName()) < 0){
								if(!apEndTypeName.equals("")){
									apEndTypeName = apEndTypeName + "・";
								}
								apEndTypeName = apEndTypeName + item.getApEndReLineTypeName();
							}
						}
					}
				}
				dffTitle = apEndTypeName + "／" + contractNum + endDate + "／" + obj.getLeCompanyName();

				//////////////////////////////////// 経路設定
				// e申請経路担当情報取得
				List<CodeName> codeNameEappChargeList = new ArrayList<CodeName>();
				codeNameEappChargeList =  masterSession.getCodeNameList(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_RE, null, obj.getApCompanyCode(), null);

				CodeName codeNameEappCharge = codeNameEappChargeList.get(0);
				CodeName codeNameEappCharge2 = null;
				if(codeNameEappChargeList.size() > 1) codeNameEappCharge2 = codeNameEappChargeList.get(1);

				// e申請経路権限情報取得
				List<String> attributeParam = new ArrayList<String>();
				String eappRouteKey = Function.nvl(codeNameRange.getValue8(), "XXXXXXXXXXXX");
				attributeParam.add(eappRouteKey);
				CodeName codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_RE, null, obj.getApCompanyCode(), attributeParam);
				if(codeNameEappRoute == null) codeNameEappRoute = new CodeName();

				// 特殊経路判定
				// 稟議書経営会議承認済
				if(Function.nvl(obj.getSpecialApproveFlag(), "").equals(Constants.FLAG_YES)) {
					attributeParam.clear();
					attributeParam.add(eappRouteKey.substring(0, 2) + Constants.EAPP_ROUTE_AUTH_AP_END_RE_SPECIAL_APPROVE_SUFFIX);

					CodeName cn = new CodeName();
					cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_RE, null, obj.getApCompanyCode(), attributeParam);

					if(cn != null) codeNameEappRoute = cn; // 経営会議承認済用の経路に置き換え
				}

				 // 要CIO審査
				if(Function.nvl(codeNameEappRoute.getValue1(), "").endsWith("-1") && Function.nvl(obj.getApNeedCioJudgeFlag(), "").equals(Constants.FLAG_YES)) {
					eappRouteKey = codeNameEappRoute.getValue1().replaceAll("-1$", "-2"); // 経路をCIO審査込みの経路に変更
					attributeParam.clear();
					attributeParam.add(eappRouteKey);

					codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_RE, null, obj.getApCompanyCode(), attributeParam);

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

				for(int i = 0; i < Constants.MAX_EAPP_ROUTE_COUNT_AP_END_RE; i++) {
					List<String> authDispList;
					List<String> chargeDispList;
					List<String> dispTypeList;

					// 対象経路判別
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
						ApEndReService service = new ApEndReServiceProxy(eappWsEndpoint);
						eappIdStr = service.apply(
								obj.getApplicationId() // applicationId
								, Constants.AP_TYPE_END_RE // applicationType
								, obj.getApCompanyCode() // companyCode
								, obj.getApSectionCode() // apSectionCode
								, obj.getApCreateStaffCode() // apCreateStaffCode
								, obj.getApStaffCode() // apStaffCode
								, obj.getApTel() // apTel
								, title // apTitle
								, subTitle // apSubTitle
								, obj.getApCompanyName() + title.replaceAll("\\\\n", " ") + " " + subTitle + " " + dffTitle // apListTitle
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
	 * 明細データの作成
	 */
	private void createLine(String loginStaffCode, ApEndRe obj, ApEndReDAO apEndReDAO, String applicationId) throws SQLException, IOException {
		Date sysdate = new Date(); // システム日付取得

		//	物件明細作成
		List<ApEndReLineLld> apEndReLineLldList = obj.getApEndReLineLldList();
		if(apEndReLineLldList != null && apEndReLineLldList.size() > 0){
			for(int i = 0; i < apEndReLineLldList.size(); i++){
				ApEndReLineLld row = apEndReLineLldList.get(i);

				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1);	//	行番号のセット

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apEndReDAO.insertApEndReLineLld(row);

			}
		}

		//	情報機器等明細作成
		List<ApEndReLineAst> apEndReLineAstList = obj.getApEndReLineAstList();
		if(apEndReLineAstList != null && apEndReLineAstList.size() > 0){
			for(int i = 0; i < apEndReLineAstList.size(); i++){
				ApEndReLineAst row = apEndReLineAstList.get(i);

				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1);	//	行番号のセット

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apEndReDAO.insertApEndReLineAst(row);

			}
		}

		//	ﾗｲｾﾝｽ明細作成
		List<ApEndReLineLic> apEndReLineLicList = obj.getApEndReLineLicList();
		if(apEndReLineLicList != null && apEndReLineLicList.size() > 0){
			for(int i = 0; i < apEndReLineLicList.size(); i++){
				ApEndReLineLic row = apEndReLineLicList.get(i);

				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1);	//	行番号のセット

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apEndReDAO.insertApEndReLineLic(row);

			}
		}

		// 添付明細作成
		List<ApEndReLineAtt> apEndReLineAttList = obj.getApEndReLineAttList();
		if(apEndReLineAttList != null && apEndReLineAttList.size() > 0) {
			for(int i = 0; i < apEndReLineAttList.size(); i++) {
				ApEndReLineAtt row = apEndReLineAttList.get(i);
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
				apEndReDAO.insertApEndReLineAtt(row);
			}
		}

	}

	/*
	 * 明細データの削除
	 */
	private void deleteLine(String loginStaffCode, ApEndRe obj, ApEndReDAO apEndReDAO) throws SQLException {
		apEndReDAO.deleteApEndReLineLld(obj.getApplicationId());
		apEndReDAO.deleteApEndReLineAst(obj.getApplicationId());
		apEndReDAO.deleteApEndReLineLic(obj.getApplicationId());
		apEndReDAO.deleteApEndReLineAtt(obj.getApplicationId());
	}

	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateApEndRe(String loginStaffCode, String accessLevel, ApEndRe obj) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 項目定義バリデーション
		int apStatus = obj.getApStatus() == null ? null : Integer.valueOf(obj.getApStatus());

		if(!Function.isAccessLevelAdmin(accessLevel)) { // 全社権限ではない場合
			apStatus += 6;	//	?
		}

		// ヘッダ
		errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_RE, "NEA_AP_END_LE", obj, apStatus, null));

		// 明細
		//	物件明細作成
		List<ApEndReLineLld> apEndReLineLldList = obj.getApEndReLineLldList();
		if(apEndReLineLldList != null && apEndReLineLldList.size() > 0){
			for(int i = 0; i < apEndReLineLldList.size(); i++){
				ApEndReLineLld apEndReLineLld = apEndReLineLldList.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_RE, "NEA_AP_END_LE_LINE_LLD", apEndReLineLld, apStatus, null));
			}
		}
		//	情報機器等明細作成
		List<ApEndReLineAst> apEndReLineAstList = obj.getApEndReLineAstList();
		if(apEndReLineAstList != null && apEndReLineAstList.size() > 0){
			for(int i = 0; i < apEndReLineAstList.size(); i++){
				ApEndReLineAst apEndReLineAst = apEndReLineAstList.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_RE, "NEA_AP_END_LE_LINE_AST", apEndReLineAst, apStatus, null));
			}
		}
		//	ﾗｲｾﾝｽ明細作成
		List<ApEndReLineLic> apEndReLineLicList = obj.getApEndReLineLicList();
		if(apEndReLineLicList != null && apEndReLineLicList.size() > 0){
			for(int i = 0; i < apEndReLineLicList.size(); i++){
				ApEndReLineLic apEndReLineLic = apEndReLineLicList.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_RE, "NEA_AP_END_LE_LINE_LIC", apEndReLineLic, apStatus, null));
			}
		}
		// 添付明細作成
		List<ApEndReLineAtt> apEndReLineAttList = obj.getApEndReLineAttList();
		if(apEndReLineAttList != null && apEndReLineAttList.size() > 0){
			for(int i = 0; i < apEndReLineAttList.size(); i++){
				ApEndReLineAtt apEndReLineAtt = apEndReLineAttList.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_RE, "NEA_AP_END_LE_LINE_ATT", apEndReLineAtt, apStatus, null));
			}
		}

		//////////////////////////////////// 条件付必須バリデーション
		String stat = Function.nvl(obj.getApStatus(), ""); // ステータス

		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_END_RE_APPROVE) &&
		   !stat.equals(Constants.AP_STATUS_END_RE_REJECT) &&
		   !stat.equals(Constants.AP_STATUS_END_RE_CANCEL)) {
			// 承認日
			if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) {
				if(obj.getSpecialApproveDate() == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "承認日", "稟議書・経営会議等承認済がチェックされている場合は"));
				}
			}

			// 返却予定日
			if(obj.getReturnFlag().equals("1") || obj.getApEndReType().equals("2")){
				if(Function.nvl(obj.getReturnDate(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "返却予定日", "申請区分が中途解約の場合、もしくは申請対象の物件に返却するものが含まれる場合は"));
				}
			}

			// 申請時物件入力チェック
			if(stat.equals(Constants.AP_STATUS_END_RE_APPLY)) {
				if(apEndReLineLldList != null && apEndReLineLldList.size() > 0){
					for(int i = 0; i < apEndReLineLldList.size(); i++){
						ApEndReLineLld apEndReLineLld = apEndReLineLldList.get(i);
						String rowStr;
						if(Function.nvl(apEndReLineLld.getContractSubNum(), "").equals("")) {
							rowStr = (i + 1) + "行目";
						} else {
							rowStr = "契約枝番-" + Function.nvl(apEndReLineLld.getContractSubNum(), "");
						}

						// 各申請区分毎の入力チェック
						if(Function.nvl(apEndReLineLld.getApEndReLineType(), "").equals("")){
							// 申請区分未選択
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-申請区分", "申請時には"));
						} else {
							// 申請区分選択

							if(Constants.AP_END_RE_TYPE_CANCEL.equals(Function.nvl(obj.getApEndReType(), ""))) {
								// 中途解約
								if(apEndReLineLld.getRemainTerm() == null) { // 残期間
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-残期間", "選択された申請区分では"));
								}
								if(apEndReLineLld.getRemainAmount() == null) { // 残レンタル料
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-残レンタル料", "選択された申請区分では"));
								}
								if(apEndReLineLld.getCancelAmount() == null) { // 中途解約金額
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-中途解約金額", "選択された申請区分では"));
								}
							}

							if(Constants.AP_END_RE_LINE_TYPE_RETURN.equals(Function.nvl(apEndReLineLld.getApEndReLineType(), ""))) {
								// 返却
							} else if(Constants.AP_END_RE_LINE_TYPE_RE.equals(Function.nvl(apEndReLineLld.getApEndReLineType(), ""))) {
								// 再レンタル
								if(apEndReLineLld.getReAmount() == null) { // 再レンタル金額
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-再レンタル金額", "選択された申請区分では"));
								}
							} else if(Constants.AP_END_RE_LINE_TYPE_BUY.equals(Function.nvl(apEndReLineLld.getApEndReLineType(), ""))) {
								// 買取
								if(apEndReLineLld.getPurchaseAmount() == null) { // 買取金額
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-買取金額", "選択された申請区分では"));
								}
							}

							if(apEndReLineLld.getEndDate() == null) { // レンタル満了日
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-レンタル満了日", "選択された申請区分では"));
							}
						}

					}
				}
			}

			// 経費負担部署
			if(obj.getApEndReType().equals("2")) {
				if(Function.nvl(obj.getCostSectionCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "経費負担-経費負担部課"));
				}
			}

		}


		//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)
		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_END_RE_APPROVE)
				&& !stat.equals(Constants.AP_STATUS_END_RE_REJECT)
				&& !stat.equals(Constants.AP_STATUS_END_RE_CANCEL)) {
			// 申請者
			if(!Function.nvl(obj.getApStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_END_RE_APPROVE)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_END_RE_REJECT)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_END_RE_CANCEL)) {
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
		}
		// 経費負担部課コード
		if(obj.getApEndReType().equals("2")) {
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
						errMsg.append("経費負担-経費負担部課 : 当年度の経費負担部課を入力してください。");
						currentYearErrorFlag = true;
					}
				}
				if(!currentYearErrorFlag && masterSession.getCostSection(obj.getCostCompanyCode(), obj.getCostSectionCode(), null, null) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担-経費負担部課"));
				}
			}
		}

		///////////////////////////////		その他バリデーション
		//	申請対象がない？
		if(obj.getApEndReLineLldList() == null || obj.getApEndReLineLldList().size() == 0){
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象を選択してください。"));
		}

// 2013/06/19 SAI 総務部要望によりバリデーション除外
/*
		//	物件内に保有部署が混在する場合
		if(obj.getHolSecErrFlag().equals("1")){
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "同一物件内に複数保有部署の情報機器等が混在するため、申請書を作成出来ません。"));
		}
*/

		////////////////////////////	既に除却申請データあり？もしくは、Ppfs未承認データあり？チェック
		// 承認済・却下・取下げ以外
		if(!stat.equals(Constants.AP_STATUS_END_RE_APPROVE)
				&& !stat.equals(Constants.AP_STATUS_END_RE_REJECT)
				&& !stat.equals(Constants.AP_STATUS_END_RE_CANCEL)) {

			//	既に未申請、申請中の除却申請データある？
			//	資産番号は、除売却申請検索を使用するので文字列
			String astNumPlural = "";
			//	固定資産番号は、Ppfs未承認Viewから取得するのでList
			String koyunoPlural = "";

			if(apEndReLineLldList != null && apEndReLineLldList.size() > 0){
				//	物件情報の資産番号、固有番号取得
				for(int i = 0; i < apEndReLineLldList.size(); i++){
					ApEndReLineLld item = apEndReLineLldList.get(i);
					//	資産番号一覧
					if(!astNumPlural.equals("")){
						astNumPlural = astNumPlural + " " + item.getAstNum();
					}
					else{
						astNumPlural = item.getAstNum();
					}
					//	固有番号一覧
					if(!koyunoPlural.equals("")){
						koyunoPlural = koyunoPlural + " " + item.getPpIdAst() + " " + item.getPpIdContract();
					}
					else{
						koyunoPlural = String.valueOf(item.getPpIdAst() + " " + item.getPpIdContract());
					}
				}

				//	未申請、申請中のレンタル満了･解約申請データ検索
				ApEndReSC apEndReSC = new ApEndReSC();
				//	資産番号複数セット
				apEndReSC.setAstNumPlural(astNumPlural);
				//	ステータスのセット
				apEndReSC.setApStatus( Constants.AP_STATUS_END_RE_NOAPPLY + " " + Constants.AP_STATUS_END_RE_APPLY + " " + Constants.AP_STATUS_END_RE_SENDBACK);
				//	申請対象の申請書番号以外を検索する
				if(!Function.nvl(obj.getApplicationId(), "").equals("")) apEndReSC.setNotApplicationId(obj.getApplicationId());
				List<ApEndReSR> apEndReSRList = searchApEndRe(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, apEndReSC);
				if(apEndReSRList != null && apEndReSRList.size() > 0){
					for(int i = 0; i < apEndReSRList.size(); i++){
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に既に申請処理中の物件が含まれているため処理を継続できません。"));
					}
				}

				//	ProPlusに承認前データが既に存在する？
				List<PpfsUnUpd> ppfsUnUpdList = fldSession.searchPpfsUnUpd(obj.getApCompanyCode(), koyunoPlural);
				if(ppfsUnUpdList != null && ppfsUnUpdList.size() > 0){
					for(int i = 0; i < ppfsUnUpdList.size(); i++){
						PpfsUnUpd ppfsUnUpd = ppfsUnUpdList.get(i);
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に契約情報更新中の契約が含まれているため処理を継続できません。(契約番号：" + ppfsUnUpd.getKykno() + ")"));
					}
				}

			}

		}

		return errMsg.toString();

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#getAstLicByLld(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ApEndRe getAstLicByLld(String loginStaffCode, String accessLevel, String companyCode, String shisanNumPlural) throws AppException {
			ApEndRe apEndRe = new ApEndRe();
			List<ApEndReLineAst> apEndReLineAstList = new ArrayList<ApEndReLineAst>();
			List<ApEndReLineLic> apEndReLineLicList = new ArrayList<ApEndReLineLic>();

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
						ApEndReLineAst apEndReLineAst = new ApEndReLineAst();
						apEndReLineAst.setAssetId(asset.getAssetId());
						apEndReLineAst.setHolCompanyCode(asset.getHolCompanyCode());
						apEndReLineAst.setHolSectionCode(asset.getHolSectionCode());
						apEndReLineAst.setHolSectionName(asset.getHolSectionName());
						apEndReLineAst.setHolSectionYear(asset.getHolSectionYear());
						apEndReLineAst.setHolStaffCode(asset.getHolStaffCode());
						apEndReLineAst.setHolStaffName(asset.getHolStaffName());
						apEndReLineAst.setHolOfficeCode(asset.getHolOfficeCode());
						apEndReLineAst.setHolOfficeName(asset.getHolOfficeName());
						apEndReLineAst.setHolQuantity(asset.getHolQuantity());
						apEndReLineAst.setUseStaffCode(asset.getUseStaffCode());
						apEndReLineAst.setUseStaffName(asset.getUseStaffName());
						apEndReLineAst.setUseStaffCompanyCode(asset.getUseStaffCompanyCode());
						apEndReLineAst.setUseStaffSectionCode(asset.getUseStaffSectionCode());
						apEndReLineAst.setUseStaffSectionYear(asset.getUseStaffSectionYear());
						apEndReLineAst.setUseStaffSectionCode(asset.getUseStaffSectionCode());
						apEndReLineAst.setAstName(asset.getAstName());
						apEndReLineAst.setAstCategory1Code(asset.getAstCategory1Code());
						apEndReLineAst.setAstCategory2Code(asset.getAstCategory2Code());
						apEndReLineAst.setAstSystemSectionDeployFlag(asset.getAstSystemSectionDeployFlag());
						apEndReLineAst.setAstAssetType(asset.getAstAssetType());
						apEndReLineAst.setAstAssetTypeName(asset.getAstAssetTypeName());
						apEndReLineAst.setAstManageType(asset.getAstManageType());
						apEndReLineAst.setAstNum(asset.getShisanNum());
						apEndReLineAst.setPpIdAst(asset.getKoyunoL());
						apEndReLineAst.setApplicationId(asset.getGetApplicationId());
						apEndReLineAst.setContractNum(asset.getContractNum());
						apEndReLineAst.setContractSubNum(asset.getContractEda());
						apEndReLineAst.setDeleteExecFlag("1");
						apEndReLineAstList.add(apEndReLineAst);
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
						ApEndReLineLic apEndReLineLic = new ApEndReLineLic();
						apEndReLineLic.setLicenseId(license.getLicenseId());
						apEndReLineLic.setHolCompanyCode(license.getHolCompanyCode());
						apEndReLineLic.setHolSectionCode(license.getHolSectionCode());
						apEndReLineLic.setHolSectionName(license.getHolSectionName());
						apEndReLineLic.setHolSectionYear(license.getHolSectionYear());
						apEndReLineLic.setHolStaffCode(license.getHolStaffCode());
						apEndReLineLic.setHolStaffName(license.getHolStaffName());
						apEndReLineLic.setSoftwareId(license.getSoftwareId());
						apEndReLineLic.setSoftwareName(license.getSoftwareName());
						apEndReLineLic.setSoftwareMakerId(license.getSoftwareMakerId());
						apEndReLineLic.setSoftwareMakerName(license.getSoftwareMakerName());
						apEndReLineLic.setSoftwareMakerId(license.getSoftwareMakerId());
						apEndReLineLic.setLicAssetType(license.getLicAssetType());
						apEndReLineLic.setLicAssetTypeName(license.getLicAssetTypeName());
						apEndReLineLic.setLicQuantityType(license.getLicQuantityType());
						apEndReLineLic.setLicQuantity(license.getLicQuantity());
						apEndReLineLic.setApplicationId(license.getGetApplicationId());
						apEndReLineLic.setAstNum(license.getShisanNum());
						apEndReLineLic.setPpIdAst(license.getKoyunoL());
						apEndReLineLic.setContractNum(license.getContractNum());
						apEndReLineLic.setContractSubNum(license.getContractEda());
						apEndReLineLic.setDeleteExecFlag("1");
						apEndReLineLicList.add(apEndReLineLic);
					}
				}
			}

			apEndRe.setApEndReLineAstList(apEndReLineAstList);
			apEndRe.setApEndReLineLicList(apEndReLineLicList);

			return apEndRe;

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#createApEndReCsv(String, String, ApEndReSC, Boolean)
	 */
	public String createApEndReCsv(String loginStaffCode, String accessLevel, ApEndReSC obj, Boolean lineOutputFlag) throws AppException {
		try {
			ApEndReDAO apEndRe = new ApEndReDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = apEndRe.createApEndReListCsv(loginStaffCode, accessLevel, obj, lineOutputFlag);

			//////////////////////////////////// 操作ログ作成
			String comOp = "";
				comOp = Constants.COM_OP_FUNCTION_AP_END_RE_SEARCH;
			histSession.createOpLog(loginStaffCode, comOp, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(obj));

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了･解約申請ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "レンタル満了･解約申請ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#createApEndRePdf(java.util.List, java.lang.Boolean)
	 */
	public String createApEndRePdf(List<String> applicationIdList, Boolean lineOutputFlag) throws AppException {

		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();
		reportParam.put("applicationIdWhere", Function.getInCondition("nael.APPLICATION_ID", applicationIdList, true));
		if(lineOutputFlag != null && lineOutputFlag.booleanValue()) reportParam.put("lineOutputFlag", "1");

		// PDF生成
		PdfExporter exporter = new PdfExporter();
		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApEndRe.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#getOtherLldByContract(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
	 */
	public ApEndRe getOtherLldByContract(String loginStaffCode, String accessLevel, String companyCode, Long ppIdContract, String ppIdAstPlural) throws AppException {
			ApEndRe apEndRe = new ApEndRe();
			List<ApEndReLineLld> apEndReLineOtherLldList = new ArrayList<ApEndReLineLld>();

			if(!Function.nvl(ppIdAstPlural, "").equals("") && ppIdContract != null){
				PpfsLldSC searchParam = new PpfsLldSC();
				searchParam.setKoyunoAPlural(ppIdAstPlural);
				apEndReLineOtherLldList = searchLld(loginStaffCode, accessLevel, companyCode, ppIdContract, searchParam);
			}

			apEndRe.setApEndReLineOtherLldList(apEndReLineOtherLldList);

			return apEndRe;

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#getOtherLldByContract(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
	 */
	private List<ApEndReLineLld> searchLld(String loginStaffCode, String accessLevel, String companyCode, Long ppIdContract, PpfsLldSC searchParam) throws AppException {
		try {
			ApEndReDAO apEndReDAO = new ApEndReDAO();
			return apEndReDAO.selectOtherLldList(loginStaffCode, accessLevel, companyCode, ppIdContract, searchParam);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース/レンタル検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#getReturnApEndReList(java.util.Date)
	 */
	public List<ApEndReSR> getReturnApEndReList(Date returnDateTo) throws AppException {
		try {
			ApEndReDAO apEndReDAO = new ApEndReDAO();
			return apEndReDAO.selectReturnApEndReList(returnDateTo);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース/レンタル検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndReSession#deleteExecApEndRe(java.lang.String, java.lang.String)
	 */
	public void deleteExecApEndRe(String applicationId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndRe apEndRe = getApEndRe(applicationId, false);

		if(apEndRe != null) {
			// 抹消処理実行フラグ
			apEndRe.setDeleteExecFlag(Constants.FLAG_YES);

			updateApEndRe(execStaffCode, null, apEndRe, false, false, true, HIST_OPERATION_DELETE_EXEC);
		}
	}
	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#updatePpSendFlagApEndLe(java.lang.String, java.lang.String)
	 */
	public void updatePpSendFlagApEndRe(ApEndRe apEndLe) throws AppException {
		try {
			ApEndReDAO apEndReDAO = new ApEndReDAO();

			List<ApEndReLineLld> ApEndReLineLld = apEndReDAO.selectApEndReLineLld(apEndLe.getApplicationId());
			String contractNum = ApEndReLineLld.get(0).getContractNum();


			List<String> PpsendFlagYes = apEndReDAO.selectPpSendFlagYes(contractNum); //
			List<String> PpsendFlagNo = apEndReDAO.selectPpSendFlagNo(contractNum); //


			if(PpsendFlagYes.size() > 0 && PpsendFlagNo.size() > 0){
				List<String> PpsendFlagNoList = apEndReDAO.selectPpSendFlagNoList(contractNum);

				apEndReDAO.updateApEndRePpSendFlag(PpsendFlagNoList);

				for(int i = 0; i< PpsendFlagNoList.size(); i++){
					histSession.createHistData(HIST_ENTITY_NAME, PpsendFlagNoList.get(i), HIST_OPERATION_UPDATE, null);
				}
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ProPlus連携更新"), e);
		}
	}
}