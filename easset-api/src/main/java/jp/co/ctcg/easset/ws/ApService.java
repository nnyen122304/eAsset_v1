/*===================================================================
 * ファイル名 : ApService.java
 * 概要説明   : 申請機能のe申請からコールされるサービス（承認・却下）
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-20 1.0  リヨン         新規
 *=================================================================== */

package jp.co.ctcg.easset.ws;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import jp.co.ctcg.easset.session.ApChangeSession;
import jp.co.ctcg.easset.session.ApEndLeSession;
import jp.co.ctcg.easset.session.ApEndReSession;
import jp.co.ctcg.easset.session.ApEndTanSession;
import jp.co.ctcg.easset.session.ApGetIntSession;
import jp.co.ctcg.easset.session.ApGetTanSession;
import jp.co.ctcg.easset.session.AssetSession;
import jp.co.ctcg.easset.session.ApBgnIntSession;
import jp.co.ctcg.easset.session.InvSession;
import jp.co.ctcg.easset.session.LicenseSession;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.util.Constants;

@Local
@Stateless
@WebService(name = "services/ApService", targetNamespace = "http://ws.easset.ctcg.co.jp")
public class ApService {
	@EJB
	ApChangeSession apChangeSession;
	@EJB
	ApEndLeSession apEndLeSession;
	@EJB
	ApEndReSession apEndReSession;
	@EJB
	ApEndTanSession apEndTanSession;
	@EJB
	ApGetTanSession apGetTanSession;
	@EJB
	ApGetIntSession apGetIntSession;
	@EJB
	ApBgnIntSession apBgnIntSession;
	@EJB
	AssetSession assetSession;
	@EJB
	InvSession invSession;
	@EJB
	LicenseSession licenseSession;


	/**
	 * 承認
	 * @param eApDocId e申請書類ID
	 * @param applicationType 申請書区分 01:取得申請(有形)、02:情報機器等登録申請、03:ライセンス登録申請、04:移動申請、05:取得申請(無形)、06:サービス提供開始報告
	 * 									 07:除売却申請、08:リース満了･解約申請、09:棚卸、10:レンタル買取申請
	 * @param execStaffCode 実行者社員番号
	 */
	@WebMethod
	public void approve(@WebParam(name="eApDocId") Long eApDocId, @WebParam(name="applicationType") String applicationType, @WebParam(name="execStaffCode") String execStaffCode) {
		try {
			if(applicationType.equals(Constants.AP_TYPE_GET_TAN)) {			// 取得申請(有形)
				apGetTanSession.approveApGetTan(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_ASSET)) {	// 情報機器等登録申請
				assetSession.approveApAsset(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_LICENSE)) {	// ライセンス登録申請
				licenseSession.approveApLicense(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_CHANGE)) {	// 移動申請
				apChangeSession.approveApChange(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_GET_INT)) {	// 取得申請(無形)
				apGetIntSession.approveApGetInt(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_BGN_INT)) {	// サービス提供開始報告
				apBgnIntSession.approveApBgnInt(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_END_TAN)) {	// 除売却申請
				apEndTanSession.approveApEndTan(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_END_LE)) {	// リース満了･解約申請
				apEndLeSession.approveApEndLe(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_INV)){	//	棚卸
				invSession.approveInv(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_END_RE)) {	// レンタル買取申請
				apEndReSession.approveApEndRe(eApDocId, execStaffCode);
			}
		} catch (AppException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			StringWriter sw = null;
			try {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				throw new RuntimeException(sw.toString());
			} finally {
				if(sw != null)
					try {
						sw.close();
					} catch (IOException e1) {
						throw new RuntimeException(e1);
					}
			}
		}
	}

	/**
	 * 上長承認
	 * @param eApDocId e申請書類ID
	 * @param applicationType 申請書区分 01:取得申請(有形)、02:情報機器等登録申請、03:ライセンス登録申請、04:移動申請、05:取得申請(無形)、06:サービス提供開始報告
	 * 									 07:除売却申請、08:リース満了･解約申請、09:棚卸、10:レンタル買取申請
	 * @param execStaffCode 実行者社員番号
	 */
	@WebMethod
	public void approveSuperior(@WebParam(name="eApDocId") Long eApDocId, @WebParam(name="applicationType") String applicationType, @WebParam(name="execStaffCode") String execStaffCode) {
		try {
			if(applicationType.equals(Constants.AP_TYPE_GET_TAN)) {			// 取得申請(有形)
				apGetTanSession.approveSuperiorApGetTan(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_ASSET)) {	// 情報機器等登録申請
			} else if(applicationType.equals(Constants.AP_TYPE_LICENSE)) {	// ライセンス登録申請
			} else if(applicationType.equals(Constants.AP_TYPE_CHANGE)) {	// 移動申請
			} else if(applicationType.equals(Constants.AP_TYPE_GET_INT)) {	// 取得申請(無形)
			} else if(applicationType.equals(Constants.AP_TYPE_BGN_INT)) {	// サービス提供開始報告
			} else if(applicationType.equals(Constants.AP_TYPE_END_TAN)) {	// 除売却申請
			} else if(applicationType.equals(Constants.AP_TYPE_END_LE)) {	// リース満了･解約申請
			} else if(applicationType.equals(Constants.AP_TYPE_INV)){	//	棚卸
			} else if(applicationType.equals(Constants.AP_TYPE_END_RE)) {	// レンタル買取申請
			}
		} catch (AppException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			StringWriter sw = null;
			try {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				throw new RuntimeException(sw.toString());
			} finally {
				if(sw != null)
					try {
						sw.close();
					} catch (IOException e1) {
						throw new RuntimeException(e1);
					}
			}
		}
	}

	/**
	 * 引戻し
	 * @param eApDocId e申請書類ID
	 * @param applicationType 申請書区分 01:取得申請(有形)、02:情報機器等登録申請、03:ライセンス登録申請、04:移動申請、05:取得申請(無形)、06:サービス提供開始報告
	 * 									 07:除売却申請、08:リース満了･解約申請、09:棚卸、10:レンタル買取申請
	 * @param execStaffCode 実行者社員番号
	 */
	@WebMethod
	public void cancelApply(@WebParam(name="eApDocId") Long eApDocId, @WebParam(name="applicationType") String applicationType, @WebParam(name="execStaffCode") String execStaffCode) {
		try {
			if(applicationType.equals(Constants.AP_TYPE_GET_TAN)) {			// 取得申請(有形)
				apGetTanSession.cancelApplyApGetTan(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_ASSET)) {	// 情報機器等登録申請
				assetSession.cancelApplyApAsset(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_LICENSE)) {	// ライセンス登録申請
				licenseSession.cancelApplyApLicense(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_CHANGE)) {	// 移動申請
				apChangeSession.cancelApplyApChange(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_GET_INT)) {	// 取得申請(無形)
				apGetIntSession.cancelApplyApGetInt(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_BGN_INT)) {	// サービス提供開始報告
				apBgnIntSession.cancelApplyApBgnInt(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_END_TAN)) {	// 除売却申請
				apEndTanSession.cancelApplyApEndTan(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_END_LE)) {	// リース満了･解約申請
				apEndLeSession.cancelApplyApEndLe(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_INV)){	//	棚卸
				invSession.cancelApplyInv(eApDocId, execStaffCode);
			} else if(applicationType.equals(Constants.AP_TYPE_END_RE)) {	// レンタル買取申請
				apEndReSession.cancelApplyApEndRe(eApDocId, execStaffCode);
			}
		} catch (AppException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			StringWriter sw = null;
			try {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				throw new RuntimeException(sw.toString());
			} finally {
				if(sw != null)
					try {
						sw.close();
					} catch (IOException e1) {
						throw new RuntimeException(e1);
					}
			}
		}
	}

	/**
	 * 却下・差戻し
	 * @param eApDocId e申請書類ID
	 * @param applicationType 申請書区分 01:取得申請(有形)、02:情報機器等登録申請、03:ライセンス登録申請、04:移動申請、05:取得申請(無形)、06:サービス提供開始報告
	 * 									 07:除売却申請、08:リース満了･解約申請、09:棚卸、10:レンタル買取申請
	 * @param execStaffCode 実行者社員番号
	 * @param rejectType 却下区分 1:差戻し、2:却下
	 * @param rejectReason 却下事由
	 */
	@WebMethod
	public void reject(@WebParam(name="eApDocId") Long eApDocId, @WebParam(name="applicationType") String applicationType, @WebParam(name="execStaffCode") String execStaffCode, @WebParam(name="rejectType") String rejectType, @WebParam(name="rejectReason") String rejectReason) {
		try {
			if(applicationType.equals(Constants.AP_TYPE_GET_TAN)) {			// 取得申請(有形)
				apGetTanSession.rejectApGetTan(eApDocId, execStaffCode, rejectType, rejectReason);
			} else if(applicationType.equals(Constants.AP_TYPE_ASSET)) {	// 情報機器等登録申請
				assetSession.rejectApAsset(eApDocId, execStaffCode, rejectType, rejectReason);
			} else if(applicationType.equals(Constants.AP_TYPE_LICENSE)) {	// ライセンス登録申請
				licenseSession.rejectApLicense(eApDocId, execStaffCode, rejectType, rejectReason);
			} else if(applicationType.equals(Constants.AP_TYPE_CHANGE)) {	// 移動申請
				apChangeSession.rejectApChange(eApDocId, execStaffCode, rejectType, rejectReason);
			} else if(applicationType.equals(Constants.AP_TYPE_GET_INT)) {	// 取得申請(無形)
				apGetIntSession.rejectApGetInt(eApDocId, execStaffCode, rejectType, rejectReason);
			} else if(applicationType.equals(Constants.AP_TYPE_BGN_INT)) {	// サービス提供開始報告
				apBgnIntSession.rejectApBgnInt(eApDocId, execStaffCode, rejectType, rejectReason);
			} else if(applicationType.equals(Constants.AP_TYPE_END_TAN)) {	// 除売却申請
				apEndTanSession.rejectApEndTan(eApDocId, execStaffCode, rejectType, rejectReason);
			} else if(applicationType.equals(Constants.AP_TYPE_END_LE)) {	// リース満了･解約申請
				apEndLeSession.rejectApEndLe(eApDocId, execStaffCode, rejectType, rejectReason);
			} else if(applicationType.equals(Constants.AP_TYPE_INV)){	//	棚卸
				invSession.rejectInv(eApDocId, execStaffCode, rejectType, rejectReason);
			} else if(applicationType.equals(Constants.AP_TYPE_END_RE)) {	// レンタル買取申請
				apEndReSession.rejectApEndRe(eApDocId, execStaffCode, rejectType, rejectReason);
			}
		} catch (AppException e) {
			throw new RuntimeException(e);
		} catch (Exception e) {
			StringWriter sw = null;
			try {
				sw = new StringWriter();
				e.printStackTrace(new PrintWriter(sw));
				throw new RuntimeException(sw.toString());
			} finally {
				if(sw != null)
					try {
						sw.close();
					} catch (IOException e1) {
						throw new RuntimeException(e1);
					}
			}
		}
	}
}
