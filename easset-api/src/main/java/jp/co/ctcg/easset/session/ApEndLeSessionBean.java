/*===================================================================
 * ファイル名 : ApEndLeSessionBean.java
 * 概要説明   : 取得申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-12-21 1.0  高山           新規
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

import jp.co.ctcg.easset.dao.ApEndLeDAO;
import jp.co.ctcg.easset.dto.fld.PpfsUnUpd;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLe;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeLineLld;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeSC;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeSR;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeLineAst;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeLineAtt;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeLineLic;
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
import jp.co.ctcg.easset.ws.ApEndLeService;
import jp.co.ctcg.easset.ws.ApEndLeServiceProxy;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;


import org.apache.commons.beanutils.PropertyUtils;


@Stateless
public class ApEndLeSessionBean implements ApEndLeSession {

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

	private static final String ID_PREFIX = "LH";
	private static final String FILE_SAVE_ENTITY_NAME = "AP_END_LE";

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
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#searchApEndLe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_le.ApEndLeSC)
	 */
	public List<ApEndLeSR> searchApEndLe(String loginStaffCode, String accessLevel, ApEndLeSC searchParam) {
		try {
			ApEndLeDAO apEndLeDAO = new ApEndLeDAO();
			return apEndLeDAO.selectApEndLeList(loginStaffCode, accessLevel, searchParam);
		} catch (SQLException e) {
			//	リース満了・解約申請検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ﾘｰｽ満了・解約申請検索"), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#getApEndLe(java.lang.String)
	 */
	public ApEndLe getApEndLe(String applicationId) {
		return getApEndLe(applicationId, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#getApEndLe(java.lang.String)
	 */
	public ApEndLe getApEndLe(Long eappId) {
		return getApEndLe(eappId, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#getApEndLe(java.lang.String, boolean)
	 */
	public ApEndLe getApEndLe(String applicationId, boolean lockFlag) {
		try {
			ApEndLeDAO apEndLeDAO = new ApEndLeDAO();

			ApEndLe apEndLe = apEndLeDAO.selectApEndLe(applicationId, lockFlag); // ヘッダの取得
			if(apEndLe != null) { // データ有
				apEndLe.setApEndLeLineAttList(apEndLeDAO.selectApEndLeLineAtt(applicationId)); // 添付明細取得
				apEndLe.setApEndLeLineAstList(apEndLeDAO.selectApEndLeLineAst(applicationId)); // 資産(機器)明細取得
				apEndLe.setApEndLeLineLicList(apEndLeDAO.selectApEndLeLineLic(applicationId)); // ライセンス明細取得
				apEndLe.setApEndLeLineLldList(apEndLeDAO.selectApEndLeLineLld(applicationId)); // 物件明細取得
			}

			return apEndLe;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ﾘｰｽ満了・解約申請取得"), e);
		}
	}

	/**
	 * 申請情報取得
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private ApEndLe getApEndLe(Long eappId, boolean lockFlag) {
		try {
			ApEndLeDAO apEndLeDAO = new ApEndLeDAO();

			ApEndLe apEndLe = apEndLeDAO.selectApEndLe(eappId, lockFlag); // ヘッダの取得
			if(apEndLe != null) {
				return getApEndLe(apEndLe.getApplicationId(), lockFlag);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース満了・解約申請取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#createApEndLe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_le.ApEndLe)
	 */
	public String createApEndLe(String loginStaffCode, String accessLevel, ApEndLe obj) throws AppException {
		return createApEndLe(loginStaffCode, accessLevel, obj, true);
	}

	/**
	 * 不正セット項目値の調整
	 * @param obj 情報機器等データ
	 * @throws AppException
	 */
	private void setPropertyAdjust(ApEndLe obj) throws AppException {

		// 稟議書・経営会議承認済チェックが付いていない場合は、承認日クリア
		if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {
			obj.setSpecialApproveDate(null);
		}

		// 移行データの再リース・返却場合は金額はnullのまま
		if(Function.nvl(obj.getApEndLeAmountRange(), "").startsWith(MIG_AMOUNT_RANGE_PREFIX) && !Function.nvl(obj.getApEndLeType(), "").equals(Constants.AP_END_LE_TYPE_CANCEL)) {
			obj.setTotalAmount(null);
		}
	}

	/**
	 * 金額範囲自動設定
	 * @param obj リース満了･解約申請データ
	 * @throws AppException
	 */
	private void setAmounRange(ApEndLe obj) throws AppException {
		// 移行データの場合は範囲を更新しない（移行用の範囲をそのまま）
		if(Function.nvl(obj.getApEndLeAmountRange(), "").startsWith(MIG_AMOUNT_RANGE_PREFIX)) return;

		if(!Function.nvl(obj.getTotalAmount(), "").equals("")) {
			List<CodeName> amountRangeList = new ArrayList<CodeName>();
			String parentInternalCode = "";

			//
			if(obj.getApEndLeType().equals("1")){
				if(obj.getReFlag().equals("1")){
					parentInternalCode = Constants.AP_END_LE_TYPE_RE;
				}else{
					parentInternalCode = Constants.AP_END_LE_TYPE_RETURN;
				}
			}else{
				parentInternalCode = Constants.AP_END_LE_TYPE_CANCEL;
			}
			amountRangeList = masterSession.getCodeNameListByParent(Constants.CATEGORY_CODE_AP_END_LE_AMOUNT_RANGE, parentInternalCode, obj.getApCompanyCode());


			//	金額範囲の設定
			if(amountRangeList != null && amountRangeList.size() > 0){
				for(int i = 0; i < amountRangeList.size(); i++){
					CodeName amountRange = amountRangeList.get(i);
					// 返却？
					if( Function.nvl(amountRange.getValue2(), "").equals("") && Function.nvl(amountRange.getValue3(), "").equals("")){
						obj.setApEndLeAmountRange(amountRange.getInternalCode());
						break;
					}
					//	上限範囲内？
					else if( !Function.nvl(amountRange.getValue3(), "").equals("") && Long.valueOf(amountRange.getValue3()).compareTo(obj.getTotalAmount()) >= 0 ){
						obj.setApEndLeAmountRange(amountRange.getInternalCode());
						break;
					}
					//	上限以上の金額範囲？
					else if( Function.nvl(amountRange.getValue3(), "").equals("") ){
						obj.setApEndLeAmountRange(amountRange.getInternalCode());
						break;
					}
				}
			}
		}
	}

	/**
	 * ﾘｰｽ満了・解約申請作成本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj ﾘｰｽ満了・解約申請データ
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータのﾘｰｽ満了・解約申請書番号
	 */
	private String createApEndLe(String loginStaffCode, String accessLevel, ApEndLe obj, boolean isHistCreate) throws AppException {
		try{
			ApEndLeDAO apEndLeDAO = new ApEndLeDAO();

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

			errMsg.append(validateApEndLe(loginStaffCode, accessLevel, obj));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			///////////////////////////////////	金額範囲(残リース料/再リース金額)の自動設定
			setAmounRange(obj);

			//////////////////////////////////// IDの採番
			String applicationId = masterSession.nextValId(ID_PREFIX);

			//////////////////////////////////// データ作成
			// バージョン・改訂番号
			obj.setVersion(1);
			obj.setDisplayVersion(1);
			obj.setApplicationId(applicationId); // IDセット
			apEndLeDAO.insertApEndLe(obj); // ヘッダ作成
			createLine(loginStaffCode, obj, apEndLeDAO, applicationId); // 明細データ作成

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				histSession.createHistData(HIST_ENTITY_NAME, applicationId, HIST_OPERATION_CREATE, null);
			}

			return obj.getApplicationId();

		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース満了・解約申請作成:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース満了・解約申請作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#updateApEndLe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_le.ApEndLe)
	 */
	public void updateApEndLe(String loginStaffCode, String accessLevel, ApEndLe obj) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateApEndLe(loginStaffCode, accessLevel, obj, true, true, true, null);

		if(Constants.AP_STATUS_END_LE_APPROVE.equals(obj.getApStatus())){
			updatePpSendFlagApEndLe(obj);
		}

	}



	/**
	 * リース満了・解約申請更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj リース満了・解約申請データ
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isDisplayVersionUpdate 改訂番号更新有無 true:改訂番号をインクリメント false:改訂番号は更新しない
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @param operation 履歴作成時のオペレーション
	 * @throws AppException
	 */
	private void updateApEndLe(String loginStaffCode, String accessLevel, ApEndLe obj, boolean isLineUpdate, boolean isDisplayVersionUpdate, boolean isHistCreate, String operation) throws AppException {
		try {
			ApEndLeDAO apEndLeDAO = new ApEndLeDAO();
			ApEndLe apEndLeOld = getApEndLe(obj.getApplicationId(), true); // 現データの取得(データロック)

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
			if(obj.getVersion().intValue() != apEndLeOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// バリデーション(登録画面更新の際のみ：連携等による更新時は行わない)
			if(isLineUpdate){
				setAmounRange(obj);
				errMsg.append(validateApEndLe(loginStaffCode, accessLevel, obj));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 登録完了フラグセット
//			obj.setRegistCompleteFlag(this.getRegistCompleteFlag(obj.getApEndLeLineAstList(), obj.getApEndLeLineLicList()));

			//////////////////////////////////// データ更新
			// バージョン・改訂番号
			obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
			if(isDisplayVersionUpdate) obj.setDisplayVersion(Function.nvl(obj.getDisplayVersion(), Integer.valueOf(1)) + 1);

			apEndLeDAO.updateApEndLe(obj);

			if(isLineUpdate) {
				// 明細を一度削除
				deleteLine(loginStaffCode, obj, apEndLeDAO);

				createLine(loginStaffCode, obj, apEndLeDAO, obj.getApplicationId()); // 明細データ作成
			}

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				StringBuffer lineChangeColumnName = new StringBuffer();
				if(isLineUpdate) {
					// 明細変更確認
					if(Function.isListChange(obj.getApEndLeLineLldList(), apEndLeOld.getApEndLeLineLldList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("物件明細");
					}
					if(Function.isListChange(obj.getApEndLeLineAstList(), apEndLeOld.getApEndLeLineAstList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("資産(機器)明細");
					}
					if(Function.isListChange(obj.getApEndLeLineLicList(), apEndLeOld.getApEndLeLineLicList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("ライセンス明細");
					}
					if(Function.isListChange(obj.getApEndLeLineAttList(), apEndLeOld.getApEndLeLineAttList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("添付明細");
					}
				}

				histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), (operation == null ? HIST_OPERATION_UPDATE : operation), lineChangeColumnName.toString());
			}
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース満了・解約申請更新:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース満了・解約申請更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#applyApEndLe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_le.ApEndLe)
	 */
	public String applyApEndLe(String loginStaffCode, String accessLevel, ApEndLe obj) throws AppException {
		//////////////////////////////////// 移行データの場合は申請不可
		if(Function.nvl(obj.getApEndLeAmountRange(), "").startsWith(MIG_AMOUNT_RANGE_PREFIX)) {
			throw new AppException("該当データは申請不可能なため、申請書を削除して新規に作成しなおしてください。");
		}

		String ret = null;
		boolean isNew = Function.nvl(obj.getApplicationId(), "").equals(""); // 新規の場合true

		////////////////////////////////////新規 or 更新呼び出し
		if(isNew){
			ret = createApEndLe(loginStaffCode, accessLevel, obj, false);
		}
		else{

			// 更新コメント
			obj.setUpdateComment(null);

			ret = obj.getApplicationId();

			// ステータス更新前バリデーション
			String errMsg = validateApEndLe(loginStaffCode, accessLevel, obj);
			if(errMsg.length() > 0) throw new AppException(errMsg);
		}

		//////////////////////////////////// ステータス更新&ステータス更新後バリデーション
		obj.setApStatus(Constants.AP_STATUS_END_LE_APPLY);
		String errMsg = validateApEndLe(loginStaffCode, accessLevel, obj);
		if(errMsg.length() > 0) throw new AppException(errMsg);

		///////////////////////////////////	金額範囲(再リース金額/残リース料)の自動設定
		if(!isNew){
		setAmounRange(obj);
		}
		//////////////////////////////////// 申請
		Long eappId = null;

		eappId = callEappService(obj); // e申請連携

		// e申請IDを更新
		obj.setEappId(eappId);
		if(isNew) { // 新規
			updateApEndLe(loginStaffCode, accessLevel, obj, false, false, false, null);
			histSession.createHistData(HIST_ENTITY_NAME, ret, HIST_OPERATION_APPLY, null); // 履歴作成
		} else {
			updateApEndLe(loginStaffCode, accessLevel, obj, true, true, true, HIST_OPERATION_APPLY);
		}

		return ret;

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#deleteApEndLe(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_end_le.ApEndLe)
	 */
	public void deleteApEndLe(String loginStaffCode, String accessLevel, ApEndLe obj) throws AppException {
		try {
			ApEndLeDAO apEndLeDAO = new ApEndLeDAO();

			ApEndLe apEndLeOld = apEndLeDAO.selectApEndLe(obj.getApplicationId(), true); // 現データの取得

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != apEndLeOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新(履歴作成用にバージョンアップ)
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			apEndLeOld.setUpdateDate(sysdate);
			apEndLeOld.setUpdateStaffCode(loginStaffCode);

			// バージョン
			apEndLeOld.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);

			// 更新コメント
			apEndLeOld.setUpdateComment(null);

			apEndLeDAO.updateApEndLe(apEndLeOld);

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), HIST_OPERATION_DELETE, null);


			//////////////////////////////////// データ削除
			apEndLeDAO.deleteApEndLe(obj.getApplicationId());
			apEndLeDAO.deleteApEndLeLineLld(obj.getApplicationId());
			apEndLeDAO.deleteApEndLeLineAst(obj.getApplicationId());
			apEndLeDAO.deleteApEndLeLineLic(obj.getApplicationId());
			apEndLeDAO.deleteApEndLeLineAtt(obj.getApplicationId());

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース満了・解約申請取得"), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#approveApEndLe(java.lang.Long, java.lang.String)
	 */
	public void approveApEndLe(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndLe apEndLe = getApEndLe(eappId, true);

		CodeName appRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_LE, null, apEndLe.getApCompanyCode(), null);

		// 除売却申請をe申請で承認する会社は承認済に更新
		if(appRoute != null && Function.nvl(appRoute.getValue2(), "").equals(Constants.FLAG_YES)) {
			// ステータス
			apEndLe.setApStatus(Constants.AP_STATUS_END_LE_APPROVE);
			// 更新コメント
			apEndLe.setUpdateComment(null);
			//	承認日
			apEndLe.setApproveDate(new Date());

			updateApEndLe(execStaffCode, null, apEndLe, false, false, true, HIST_OPERATION_APPROVE);

			if(Constants.AP_STATUS_END_LE_APPROVE.equals(apEndLe.getApStatus())){
				updatePpSendFlagApEndLe(apEndLe);
			}

		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#approveSuperiorApEndLe(java.lang.Long, java.lang.String)
	 */
	public void approveSuperiorApEndLe(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndLe apEndLe = getApEndLe(eappId, true);

		CodeName appRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_LE, null, apEndLe.getApCompanyCode(), null);
		if(appRoute != null && Function.nvl(appRoute.getValue2(), "").equals(Constants.FLAG_YES)) { // 取得申請をe申請で承認する会社以外は受付承認フラグセット無し
			// 受付承認フラグ
//			apEndLe.setApproveSuperiorFlag(Constants.FLAG_YES);
		}
		// 更新コメント
		apEndLe.setUpdateComment(null);

		updateApEndLe(execStaffCode, null, apEndLe, false, false, true, HIST_OPERATION_APPROVE_SUPERIOR);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#cancelApplyApEndLe(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyApEndLe(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndLe apEndLe = getApEndLe(eappId, true);

		if(apEndLe != null) {
			// ステータス
			apEndLe.setApStatus(Constants.AP_STATUS_END_LE_NOAPPLY);
			// 更新コメント
			apEndLe.setUpdateComment(null);
			apEndLe.setEappId(null); // 書類IDクリア

			updateApEndLe(execStaffCode, null, apEndLe, false, false, true, HIST_OPERATION_CANCEL_APPLY);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#rejectApEndLe(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectApEndLe(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		String histOperation;

		//////////////////////////////////// 更新
		ApEndLe apEndLe = getApEndLe(eappId, true);

		if(apEndLe != null) {
			// ステータス
			if(Function.nvl(rejectType, "").equals(Constants.AP_REJECT_TYPE_REJECT)) { // 却下
				apEndLe.setApStatus(Constants.AP_STATUS_END_LE_REJECT);
				histOperation = HIST_OPERATION_REJECT;
			} else { // 差戻し
				apEndLe.setApStatus(Constants.AP_STATUS_END_LE_SENDBACK);
				apEndLe.setEappId(null); // 書類IDクリア
				histOperation = HIST_OPERATION_SENDBACK;
			}

			// コメント
			apEndLe.setUpdateComment(rejectReason);

			updateApEndLe(execStaffCode, null, apEndLe, false, false, true, histOperation);
		}
	}

	/**
	 * e申請サービス呼び出し
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(ApEndLe obj) throws AppException {
		// e申請WebServiceエンドポイント取得
		CodeName codeNameUrl = new CodeName();
		codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_END_LE, null, null);
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
				CodeName codeNameRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_END_LE_AMOUNT_RANGE, obj.getApEndLeAmountRange(), obj.getApCompanyCode(), null);
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
				if(obj.getApEndLeLineLldList() != null && obj.getApEndLeLineLldList().size() > 0){
					List<ApEndLeLineLld> list = obj.getApEndLeLineLldList();
					for(int i = 0; i < list.size(); i++){
						ApEndLeLineLld item = list.get(i);
						if(i == 0){
							contractNum = item.getContractNum();
							if(item.getEndDate() != null) {
								SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
								endDate = "／" + dateFormat.format(item.getEndDate());
							}
						}
						// 申請区分取得
						if(item.getApEndLeLineTypeName() != null){
							if(apEndTypeName.indexOf(item.getApEndLeLineTypeName()) < 0){
								if(!apEndTypeName.equals("")){
									apEndTypeName = apEndTypeName + "・";
								}
								apEndTypeName = apEndTypeName + item.getApEndLeLineTypeName();
							}
						}
					}
				}
				dffTitle = apEndTypeName + "／" + contractNum + endDate + "／" + obj.getLeCompanyName();

				//////////////////////////////////// 経路設定
				// e申請経路担当情報取得
				List<CodeName> codeNameEappChargeList = new ArrayList<CodeName>();
				codeNameEappChargeList =  masterSession.getCodeNameList(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_LE, null, obj.getApCompanyCode(), null);

				CodeName codeNameEappCharge = codeNameEappChargeList.get(0);
				CodeName codeNameEappCharge2 = null;
				if(codeNameEappChargeList.size() > 1) codeNameEappCharge2 = codeNameEappChargeList.get(1);

				// e申請経路権限情報取得
				List<String> attributeParam = new ArrayList<String>();
				String eappRouteKey = Function.nvl(codeNameRange.getValue8(), "XXXXXXXXXXXX");
				attributeParam.add(eappRouteKey);
				CodeName codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_LE, null, obj.getApCompanyCode(), attributeParam);
				if(codeNameEappRoute == null) codeNameEappRoute = new CodeName();

				// 特殊経路判定
				// 稟議書経営会議承認済
				if(Function.nvl(obj.getSpecialApproveFlag(), "").equals(Constants.FLAG_YES)) {
					attributeParam.clear();
					attributeParam.add(eappRouteKey.substring(0, 2) + Constants.EAPP_ROUTE_AUTH_AP_END_LE_SPECIAL_APPROVE_SUFFIX);

					CodeName cn = new CodeName();
					cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_LE, null, obj.getApCompanyCode(), attributeParam);

					if(cn != null) codeNameEappRoute = cn; // 経営会議承認済用の経路に置き換え
				}

				 // 要CIO審査
				if(Function.nvl(codeNameEappRoute.getValue1(), "").endsWith("-1") && Function.nvl(obj.getApNeedCioJudgeFlag(), "").equals(Constants.FLAG_YES)) {
					eappRouteKey = codeNameEappRoute.getValue1().replaceAll("-1$", "-2"); // 経路をCIO審査込みの経路に変更
					attributeParam.clear();
					attributeParam.add(eappRouteKey);

					codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_LE, null, obj.getApCompanyCode(), attributeParam);

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

				for(int i = 0; i < Constants.MAX_EAPP_ROUTE_COUNT_AP_END_LE; i++) {
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
						ApEndLeService service = new ApEndLeServiceProxy(eappWsEndpoint);
						eappIdStr = service.apply(
								obj.getApplicationId() // applicationId
								, Constants.AP_TYPE_END_LE // applicationType
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
	private void createLine(String loginStaffCode, ApEndLe obj, ApEndLeDAO apEndLeDAO, String applicationId) throws SQLException, IOException {
		Date sysdate = new Date(); // システム日付取得

		//	物件明細作成
		List<ApEndLeLineLld> apEndLeLineLldList = obj.getApEndLeLineLldList();
		if(apEndLeLineLldList != null && apEndLeLineLldList.size() > 0){
			for(int i = 0; i < apEndLeLineLldList.size(); i++){
				ApEndLeLineLld row = apEndLeLineLldList.get(i);

				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1);	//	行番号のセット

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apEndLeDAO.insertApEndLeLineLld(row);

			}
		}

		//	情報機器等明細作成
		List<ApEndLeLineAst> apEndLeLineAstList = obj.getApEndLeLineAstList();
		if(apEndLeLineAstList != null && apEndLeLineAstList.size() > 0){
			for(int i = 0; i < apEndLeLineAstList.size(); i++){
				ApEndLeLineAst row = apEndLeLineAstList.get(i);

				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1);	//	行番号のセット

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apEndLeDAO.insertApEndLeLineAst(row);

			}
		}

		//	ﾗｲｾﾝｽ明細作成
		List<ApEndLeLineLic> apEndLeLineLicList = obj.getApEndLeLineLicList();
		if(apEndLeLineLicList != null && apEndLeLineLicList.size() > 0){
			for(int i = 0; i < apEndLeLineLicList.size(); i++){
				ApEndLeLineLic row = apEndLeLineLicList.get(i);

				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1);	//	行番号のセット

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apEndLeDAO.insertApEndLeLineLic(row);

			}
		}

		// 添付明細作成
		List<ApEndLeLineAtt> apEndLeLineAttList = obj.getApEndLeLineAttList();
		if(apEndLeLineAttList != null && apEndLeLineAttList.size() > 0) {
			for(int i = 0; i < apEndLeLineAttList.size(); i++) {
				ApEndLeLineAtt row = apEndLeLineAttList.get(i);
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
				apEndLeDAO.insertApEndLeLineAtt(row);
			}
		}

	}

	/*
	 * 明細データの削除
	 */
	private void deleteLine(String loginStaffCode, ApEndLe obj, ApEndLeDAO apEndLeDAO) throws SQLException {
		apEndLeDAO.deleteApEndLeLineLld(obj.getApplicationId());
		apEndLeDAO.deleteApEndLeLineAst(obj.getApplicationId());
		apEndLeDAO.deleteApEndLeLineLic(obj.getApplicationId());
		apEndLeDAO.deleteApEndLeLineAtt(obj.getApplicationId());
	}

	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateApEndLe(String loginStaffCode, String accessLevel, ApEndLe obj) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 項目定義バリデーション
		int apStatus = obj.getApStatus() == null ? null : Integer.valueOf(obj.getApStatus());

		if(!Function.isAccessLevelAdmin(accessLevel)) { // 全社権限ではない場合
			apStatus += 6;	//	?
		}

		// ヘッダ
		errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_LE, "NEA_AP_END_LE", obj, apStatus, null));

		// 明細
		//	物件明細作成
		List<ApEndLeLineLld> apEndLeLineLldList = obj.getApEndLeLineLldList();
		if(apEndLeLineLldList != null && apEndLeLineLldList.size() > 0){
			for(int i = 0; i < apEndLeLineLldList.size(); i++){
				ApEndLeLineLld apEndLeLineLld = apEndLeLineLldList.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_LE, "NEA_AP_END_LE_LINE_LLD", apEndLeLineLld, apStatus, null));
			}
		}
		//	情報機器等明細作成
		List<ApEndLeLineAst> apEndLeLineAstList = obj.getApEndLeLineAstList();
		if(apEndLeLineAstList != null && apEndLeLineAstList.size() > 0){
			for(int i = 0; i < apEndLeLineAstList.size(); i++){
				ApEndLeLineAst apEndLeLineAst = apEndLeLineAstList.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_LE, "NEA_AP_END_LE_LINE_AST", apEndLeLineAst, apStatus, null));
			}
		}
		//	ﾗｲｾﾝｽ明細作成
		List<ApEndLeLineLic> apEndLeLineLicList = obj.getApEndLeLineLicList();
		if(apEndLeLineLicList != null && apEndLeLineLicList.size() > 0){
			for(int i = 0; i < apEndLeLineLicList.size(); i++){
				ApEndLeLineLic apEndLeLineLic = apEndLeLineLicList.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_LE, "NEA_AP_END_LE_LINE_LIC", apEndLeLineLic, apStatus, null));
			}
		}
		// 添付明細作成
		List<ApEndLeLineAtt> apEndLeLineAttList = obj.getApEndLeLineAttList();
		if(apEndLeLineAttList != null && apEndLeLineAttList.size() > 0){
			for(int i = 0; i < apEndLeLineAttList.size(); i++){
				ApEndLeLineAtt apEndLeLineAtt = apEndLeLineAttList.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_END_LE, "NEA_AP_END_LE_LINE_ATT", apEndLeLineAtt, apStatus, null));
			}
		}

		//////////////////////////////////// 条件付必須バリデーション
		String stat = Function.nvl(obj.getApStatus(), ""); // ステータス

		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_END_LE_APPROVE) &&
		   !stat.equals(Constants.AP_STATUS_END_LE_REJECT) &&
		   !stat.equals(Constants.AP_STATUS_END_LE_CANCEL)) {
			// 承認日
			if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) {
				if(obj.getSpecialApproveDate() == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "承認日", "稟議書・経営会議等承認済がチェックされている場合は"));
				}
			}

			// 返却予定日
			if(obj.getReturnFlag().equals("1") || obj.getApEndLeType().equals("2")){
				if(Function.nvl(obj.getReturnDate(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "返却予定日", "申請区分が中途解約の場合、もしくは申請対象の物件に返却するものが含まれる場合は"));
				}
			}

			// 申請時物件入力チェック
			if(stat.equals(Constants.AP_STATUS_END_LE_APPLY)) {
				if(apEndLeLineLldList != null && apEndLeLineLldList.size() > 0){
					for(int i = 0; i < apEndLeLineLldList.size(); i++){
						ApEndLeLineLld apEndLeLineLld = apEndLeLineLldList.get(i);
						String rowStr;
						if(Function.nvl(apEndLeLineLld.getContractSubNum(), "").equals("")) {
							rowStr = (i + 1) + "行目";
						} else {
							rowStr = "契約枝番-" + Function.nvl(apEndLeLineLld.getContractSubNum(), "");
						}

						// 各申請区分毎の入力チェック
						if(Function.nvl(apEndLeLineLld.getApEndLeLineType(), "").equals("")){
							// 申請区分未選択
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-申請区分", "申請時には"));
						} else {
							// 申請区分選択

							if(Constants.AP_END_LE_TYPE_CANCEL.equals(Function.nvl(obj.getApEndLeType(), ""))) {
								// 中途解約
								if(apEndLeLineLld.getRemainTerm() == null) { // 残期間
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-残期間", "選択された申請区分では"));
								}
								if(apEndLeLineLld.getRemainAmount() == null) { // 残リース料
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-残リース料", "選択された申請区分では"));
								}
								if(apEndLeLineLld.getCancelAmount() == null) { // 中途解約金額
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-中途解約金額", "選択された申請区分では"));
								}
							}

							if(Constants.AP_END_LE_LINE_TYPE_RETURN.equals(Function.nvl(apEndLeLineLld.getApEndLeLineType(), ""))) {
								// 返却
							} else if(Constants.AP_END_LE_LINE_TYPE_RE.equals(Function.nvl(apEndLeLineLld.getApEndLeLineType(), ""))) {
								// 再リース
								if(apEndLeLineLld.getReAmount() == null) { // 再リース金額
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-再リース金額", "選択された申請区分では"));
								}
							} else if(Constants.AP_END_LE_LINE_TYPE_BUY.equals(Function.nvl(apEndLeLineLld.getApEndLeLineType(), ""))) {
								// 買取
								if(apEndLeLineLld.getPurchaseAmount() == null) { // 買取金額
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-買取金額", "選択された申請区分では"));
								}
							}

							if(apEndLeLineLld.getEndDate() == null) { // リース満了日
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "申請対象-物件情報[" + rowStr + "]-リース満了日", "選択された申請区分では"));
							}
						}

					}
				}
			}

			// 経費負担部署
			if(obj.getApEndLeType().equals("2")) {
				if(Function.nvl(obj.getCostSectionCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "経費負担-経費負担部課"));
				}
			}

		}


		//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)
		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_END_LE_APPROVE)
				&& !stat.equals(Constants.AP_STATUS_END_LE_REJECT)
				&& !stat.equals(Constants.AP_STATUS_END_LE_CANCEL)) {
			// 申請者
			if(!Function.nvl(obj.getApStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_END_LE_APPROVE)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_END_LE_REJECT)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_END_LE_CANCEL)) {
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
		if(obj.getApEndLeType().equals("2")) {
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
		if(obj.getApEndLeLineLldList() == null || obj.getApEndLeLineLldList().size() == 0){
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
		if(!stat.equals(Constants.AP_STATUS_END_LE_APPROVE)
				&& !stat.equals(Constants.AP_STATUS_END_LE_REJECT)
				&& !stat.equals(Constants.AP_STATUS_END_LE_CANCEL)) {

			//	既に未申請、申請中の除却申請データある？
			//	資産番号は、除売却申請検索を使用するので文字列
			String astNumPlural = "";
			//	固定資産番号は、Ppfs未承認Viewから取得するのでList
			String koyunoPlural = "";

			if(apEndLeLineLldList != null && apEndLeLineLldList.size() > 0){
				//	物件情報の資産番号、固有番号取得
				for(int i = 0; i < apEndLeLineLldList.size(); i++){
					ApEndLeLineLld item = apEndLeLineLldList.get(i);
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

				//	未申請、申請中のリース満了･解約申請データ検索
				ApEndLeSC apEndLeSC = new ApEndLeSC();
				//	資産番号複数セット
				apEndLeSC.setAstNumPlural(astNumPlural);
				//	ステータスのセット
				apEndLeSC.setApStatus( Constants.AP_STATUS_END_LE_NOAPPLY + " " + Constants.AP_STATUS_END_LE_APPLY + " " + Constants.AP_STATUS_END_LE_SENDBACK);
				//	申請対象の申請書番号以外を検索する
				if(!Function.nvl(obj.getApplicationId(), "").equals("")) apEndLeSC.setNotApplicationId(obj.getApplicationId());
				List<ApEndLeSR> apEndLeSRList = searchApEndLe(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, apEndLeSC);
				if(apEndLeSRList != null && apEndLeSRList.size() > 0){
					for(int i = 0; i < apEndLeSRList.size(); i++){
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
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#getAstLicByLld(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ApEndLe getAstLicByLld(String loginStaffCode, String accessLevel, String companyCode, String shisanNumPlural) throws AppException {
			ApEndLe apEndLe = new ApEndLe();
			List<ApEndLeLineAst> apEndLeLineAstList = new ArrayList<ApEndLeLineAst>();
			List<ApEndLeLineLic> apEndLeLineLicList = new ArrayList<ApEndLeLineLic>();

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
						ApEndLeLineAst apEndLeLineAst = new ApEndLeLineAst();
						apEndLeLineAst.setAssetId(asset.getAssetId());
						apEndLeLineAst.setHolCompanyCode(asset.getHolCompanyCode());
						apEndLeLineAst.setHolSectionCode(asset.getHolSectionCode());
						apEndLeLineAst.setHolSectionName(asset.getHolSectionName());
						apEndLeLineAst.setHolSectionYear(asset.getHolSectionYear());
						apEndLeLineAst.setHolStaffCode(asset.getHolStaffCode());
						apEndLeLineAst.setHolStaffName(asset.getHolStaffName());
						apEndLeLineAst.setHolOfficeCode(asset.getHolOfficeCode());
						apEndLeLineAst.setHolOfficeName(asset.getHolOfficeName());
						apEndLeLineAst.setHolQuantity(asset.getHolQuantity());
						apEndLeLineAst.setUseStaffCode(asset.getUseStaffCode());
						apEndLeLineAst.setUseStaffName(asset.getUseStaffName());
						apEndLeLineAst.setUseStaffCompanyCode(asset.getUseStaffCompanyCode());
						apEndLeLineAst.setUseStaffSectionCode(asset.getUseStaffSectionCode());
						apEndLeLineAst.setUseStaffSectionYear(asset.getUseStaffSectionYear());
						apEndLeLineAst.setUseStaffSectionCode(asset.getUseStaffSectionCode());
						apEndLeLineAst.setAstName(asset.getAstName());
						apEndLeLineAst.setAstCategory1Code(asset.getAstCategory1Code());
						apEndLeLineAst.setAstCategory2Code(asset.getAstCategory2Code());
						apEndLeLineAst.setAstSystemSectionDeployFlag(asset.getAstSystemSectionDeployFlag());
						apEndLeLineAst.setAstAssetType(asset.getAstAssetType());
						apEndLeLineAst.setAstAssetTypeName(asset.getAstAssetTypeName());
						apEndLeLineAst.setAstManageType(asset.getAstManageType());
						apEndLeLineAst.setAstNum(asset.getShisanNum());
						apEndLeLineAst.setPpIdAst(asset.getKoyunoL());
						apEndLeLineAst.setApplicationId(asset.getGetApplicationId());
						apEndLeLineAst.setContractNum(asset.getContractNum());
						apEndLeLineAst.setContractSubNum(asset.getContractEda());
						apEndLeLineAst.setDeleteExecFlag("1");
						apEndLeLineAstList.add(apEndLeLineAst);
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
						ApEndLeLineLic apEndLeLineLic = new ApEndLeLineLic();
						apEndLeLineLic.setLicenseId(license.getLicenseId());
						apEndLeLineLic.setHolCompanyCode(license.getHolCompanyCode());
						apEndLeLineLic.setHolSectionCode(license.getHolSectionCode());
						apEndLeLineLic.setHolSectionName(license.getHolSectionName());
						apEndLeLineLic.setHolSectionYear(license.getHolSectionYear());
						apEndLeLineLic.setHolStaffCode(license.getHolStaffCode());
						apEndLeLineLic.setHolStaffName(license.getHolStaffName());
						apEndLeLineLic.setSoftwareId(license.getSoftwareId());
						apEndLeLineLic.setSoftwareName(license.getSoftwareName());
						apEndLeLineLic.setSoftwareMakerId(license.getSoftwareMakerId());
						apEndLeLineLic.setSoftwareMakerName(license.getSoftwareMakerName());
						apEndLeLineLic.setSoftwareMakerId(license.getSoftwareMakerId());
						apEndLeLineLic.setLicAssetType(license.getLicAssetType());
						apEndLeLineLic.setLicAssetTypeName(license.getLicAssetTypeName());
						apEndLeLineLic.setLicQuantityType(license.getLicQuantityType());
						apEndLeLineLic.setLicQuantity(license.getLicQuantity());
						apEndLeLineLic.setApplicationId(license.getGetApplicationId());
						apEndLeLineLic.setAstNum(license.getShisanNum());
						apEndLeLineLic.setPpIdAst(license.getKoyunoL());
						apEndLeLineLic.setContractNum(license.getContractNum());
						apEndLeLineLic.setContractSubNum(license.getContractEda());
						apEndLeLineLic.setDeleteExecFlag("1");
						apEndLeLineLicList.add(apEndLeLineLic);
					}
				}
			}

			apEndLe.setApEndLeLineAstList(apEndLeLineAstList);
			apEndLe.setApEndLeLineLicList(apEndLeLineLicList);

			return apEndLe;

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#createApEndLeCsv(String, String, ApEndLeSC, Boolean)
	 */
	public String createApEndLeCsv(String loginStaffCode, String accessLevel, ApEndLeSC obj, Boolean lineOutputFlag) throws AppException {
		try {
			ApEndLeDAO apEndLe = new ApEndLeDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = apEndLe.createApEndLeListCsv(loginStaffCode, accessLevel, obj, lineOutputFlag);

			//////////////////////////////////// 操作ログ作成
			String comOp = "";
				comOp = Constants.COM_OP_FUNCTION_AP_END_LE_SEARCH;
			histSession.createOpLog(loginStaffCode, comOp, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(obj));

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース満了･解約申請ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース満了･解約申請ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#createApEndLePdf(java.util.List, java.lang.Boolean)
	 */
	public String createApEndLePdf(List<String> applicationIdList, Boolean lineOutputFlag) throws AppException {

		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();
		reportParam.put("applicationIdWhere", Function.getInCondition("nael.APPLICATION_ID", applicationIdList, true));
		if(lineOutputFlag != null && lineOutputFlag.booleanValue()) reportParam.put("lineOutputFlag", "1");

		// PDF生成
		PdfExporter exporter = new PdfExporter();
		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApEndLe.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#getOtherLldByContract(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
	 */
	public ApEndLe getOtherLldByContract(String loginStaffCode, String accessLevel, String companyCode, Long ppIdContract, String ppIdAstPlural) throws AppException {
			ApEndLe apEndLe = new ApEndLe();
			List<ApEndLeLineLld> apEndLeLineOtherLldList = new ArrayList<ApEndLeLineLld>();

			if(!Function.nvl(ppIdAstPlural, "").equals("") && ppIdContract != null){
				PpfsLldSC searchParam = new PpfsLldSC();
				searchParam.setKoyunoAPlural(ppIdAstPlural);
				apEndLeLineOtherLldList = searchLld(loginStaffCode, accessLevel, companyCode, ppIdContract, searchParam);
			}

			apEndLe.setApEndLeLineOtherLldList(apEndLeLineOtherLldList);

			return apEndLe;

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#getOtherLldByContract(java.lang.String, java.lang.String, java.lang.String, java.lang.Long, java.lang.String)
	 */
	private List<ApEndLeLineLld> searchLld(String loginStaffCode, String accessLevel, String companyCode, Long ppIdContract, PpfsLldSC searchParam) throws AppException {
		try {
			ApEndLeDAO apEndLeDAO = new ApEndLeDAO();
			return apEndLeDAO.selectOtherLldList(loginStaffCode, accessLevel, companyCode, ppIdContract, searchParam);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース/レンタル検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#getReturnApEndLeList(java.util.Date)
	 */
	public List<ApEndLeSR> getReturnApEndLeList(Date returnDateTo) throws AppException {
		try {
			ApEndLeDAO apEndLeDAO = new ApEndLeDAO();
			return apEndLeDAO.selectReturnApEndLeList(returnDateTo);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース/レンタル検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#deleteExecApEndLe(java.lang.String, java.lang.String)
	 */
	public void deleteExecApEndLe(String applicationId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApEndLe apEndLe = getApEndLe(applicationId, false);

		if(apEndLe != null) {
			// 抹消処理実行フラグ
			apEndLe.setDeleteExecFlag(Constants.FLAG_YES);

			updateApEndLe(execStaffCode, null, apEndLe, false, false, true, HIST_OPERATION_DELETE_EXEC);
		}
	}
	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApEndLeSession#updatePpSendFlagApEndLe(java.lang.String, java.lang.String)
	 */
	public void updatePpSendFlagApEndLe(ApEndLe apEndLe) throws AppException {
		try {
			ApEndLeDAO apEndLeDAO = new ApEndLeDAO();

			List<ApEndLeLineLld> ApEndLeLineLld = apEndLeDAO.selectApEndLeLineLld(apEndLe.getApplicationId());
			String contractNum = ApEndLeLineLld.get(0).getContractNum();


			List<String> PpsendFlagYes = apEndLeDAO.selectPpSendFlagYes(contractNum); //
			List<String> PpsendFlagNo = apEndLeDAO.selectPpSendFlagNo(contractNum); //


			if(PpsendFlagYes.size() > 0 && PpsendFlagNo.size() > 0){
				List<String> PpsendFlagNoList = apEndLeDAO.selectPpSendFlagNoList(contractNum);

				apEndLeDAO.updateApEndLePpSendFlag(PpsendFlagNoList);

				for(int i = 0; i< PpsendFlagNoList.size(); i++){
					histSession.createHistData(HIST_ENTITY_NAME, PpsendFlagNoList.get(i), HIST_OPERATION_UPDATE, null);
				}
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ProPlus連携更新"), e);
		}
	}

}