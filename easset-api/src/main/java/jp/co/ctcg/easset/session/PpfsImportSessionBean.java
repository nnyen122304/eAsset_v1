/*===================================================================
 * ファイル名 : PpfsImportSessionBean.java
 * 概要説明   : マスタセッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-24 1.0  リヨン 崔     新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import jp.co.ctcg.easset.dao.PpfsImportDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.ppfs_import.PpfsStat;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.Function;

@Stateless
public class PpfsImportSessionBean implements PpfsImportSession {

	private static final int ERROR_CODE_DUP_EXEC = 20001; // 重複実行時エラーコード
	private static final int ERROR_CODE_NOWAIT = 54; // 重複実行時エラーコード(FOR UPDATE NOWAIT)

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession;	// マスタセッション

	@EJB
	SendMailSession sendMailSession;	// メール送信セッション

	@Resource(mappedName = "java:/jms/queue/PpfsImportQueue")
	private Queue ppfsImportQueue; // 一括更新実行用キュー

	@Resource(mappedName = "java:/jms/PpfsImportQueueFactory" )
	private ConnectionFactory ppfsImportQueueFactory; // 一括更新実行用キュー接続ファクトリ

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.PpfsImportSession#getPpfsCurrentPeriodRT(java.lang.String)
	 */
	public String getPpfsCurrentPeriodRT(String companyCode) {
		PpfsImportDAO ppfsImportDAO = new PpfsImportDAO();
		try{
			return ppfsImportDAO.getPpfsCurrentPeriodRT(companyCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ProPlus会計年月取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.PpfsImportSession#getPpfsStatList(java.lang.String, java.lang.String)
	 */
	public List<PpfsStat> getPpfsStatList(String companyCode, String dataType) {
		try {
			PpfsImportDAO ppfsImportDAO = new PpfsImportDAO();
			return ppfsImportDAO.selectPpfsStatList(companyCode, dataType);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取込ステータス一覧取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.PpfsImportSession#callPpfsImport(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void callPpfsImport(String companyCode, String dataType, String execStaffCode, String schCreateFlag) throws AppException {
		//////////////////////////////////// ステータス作成
		PpfsImportDAO ppfsImportDAO = new PpfsImportDAO();

		String execDataType = "";
		try {

			if(dataType == null || dataType.equals(Constants.PPFS_IMPORT_DATA_TYPE_FLD)) { // 固定資産
				execDataType = Constants.PPFS_IMPORT_DATA_TYPE_FLD;
				ppfsImportDAO.callUpdatePpfsStatus(companyCode, execDataType, execStaffCode, Constants.PPFS_IMPORT_EXEC_STATUS_PROCESSING);

				if(Function.nvl(schCreateFlag, "").equals(Constants.FLAG_YES)) { // 予測取込
					execDataType = Constants.PPFS_IMPORT_DATA_TYPE_FLD_SCH;
					ppfsImportDAO.callUpdatePpfsStatus(companyCode, execDataType, execStaffCode, Constants.PPFS_IMPORT_EXEC_STATUS_PROCESSING);
				}
			}
			if(dataType == null || dataType.equals(Constants.PPFS_IMPORT_DATA_TYPE_LLD)) { // リース・レンタル
				execDataType = Constants.PPFS_IMPORT_DATA_TYPE_LLD;
				ppfsImportDAO.callUpdatePpfsStatus(companyCode, execDataType, execStaffCode, Constants.PPFS_IMPORT_EXEC_STATUS_PROCESSING);

				if(Function.nvl(schCreateFlag, "").equals(Constants.FLAG_YES)) { // 予測取込
					execDataType = Constants.PPFS_IMPORT_DATA_TYPE_LLD_SCH;
					ppfsImportDAO.callUpdatePpfsStatus(companyCode, execDataType, execStaffCode, Constants.PPFS_IMPORT_EXEC_STATUS_PROCESSING);
				}
			}
		} catch (SQLException e) {
			if(e.getErrorCode() == ERROR_CODE_DUP_EXEC || e.getErrorCode() == ERROR_CODE_NOWAIT) {
				CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_PPFS_IMPORT_DATA_TYPE, execDataType, null, null);
				String dataTypeName = "";
				if(cn != null) {
					dataTypeName = cn.getValue1() + "の";
				}
				throw new AppException(dataTypeName + "取込処理は既に実行中です。");
			} else {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取込ステータス更新"), e);
			}
		}

		//////////////////////////////////// メッセージ送信
		// メッセージパラメータ作成
		HashMap<String, Object> param = new HashMap<String, Object>();

		param.put("companyCode", companyCode);
		param.put("execStaffCode", execStaffCode);
		param.put("schCreateFlag", schCreateFlag);

		if(dataType == null || dataType.equals(Constants.PPFS_IMPORT_DATA_TYPE_FLD)) { // 固定資産
			param.put("dataType", Constants.PPFS_IMPORT_DATA_TYPE_FLD);
			Function.sendJmsMessage(ppfsImportQueueFactory, ppfsImportQueue, param);

			if(Function.nvl(schCreateFlag, "").equals(Constants.FLAG_YES)) { // 予測取込
				param.put("dataType", Constants.PPFS_IMPORT_DATA_TYPE_FLD_SCH);
				Function.sendJmsMessage(ppfsImportQueueFactory, ppfsImportQueue, param);
			}
		}
		if(dataType == null || dataType.equals(Constants.PPFS_IMPORT_DATA_TYPE_LLD)) { // リース・レンタル
			param.put("dataType", Constants.PPFS_IMPORT_DATA_TYPE_LLD);
			Function.sendJmsMessage(ppfsImportQueueFactory, ppfsImportQueue, param);

			if(Function.nvl(schCreateFlag, "").equals(Constants.FLAG_YES)) { // 予測取込
				param.put("dataType", Constants.PPFS_IMPORT_DATA_TYPE_LLD_SCH);
				Function.sendJmsMessage(ppfsImportQueueFactory, ppfsImportQueue, param);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.PpfsImportSession#ppfsImport(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void ppfsImport(String companyCode, String dataType, String execStaffCode) {
		PpfsImportDAO ppfsImportDAO = new PpfsImportDAO();
		try {
			ppfsImportDAO.callPpfsImport(companyCode, dataType, execStaffCode);
			ppfsImportDAO.callUpdatePpfsStatus(companyCode, dataType, execStaffCode, Constants.PPFS_IMPORT_EXEC_STATUS_SUCCESS);
		} catch (Exception e) {
			handleImportError(companyCode, dataType, execStaffCode, e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.PpfsImportSession#ppfsImportSch(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void ppfsImportSch(String companyCode, String dataType, String execStaffCode) {
		PpfsImportDAO ppfsImportDAO = new PpfsImportDAO();
		try {
			ppfsImportDAO.callPpfsImportSch(companyCode, dataType, execStaffCode);
			ppfsImportDAO.callUpdatePpfsStatus(companyCode, dataType, execStaffCode, Constants.PPFS_IMPORT_EXEC_STATUS_SUCCESS);
		} catch (Exception e) {
			handleImportError(companyCode, dataType, execStaffCode, e);
		}
	}

	/**
	 * 取込処理のエラーハンドリング
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル、3:固定資産-予測、4:リース・レンタル-予測
	 * @param execStaffCode 実行者社員番号
	 * @param e
	 */
	private void handleImportError(String companyCode, String dataType, String execStaffCode, Exception e) {
		PpfsImportDAO ppfsImportDAO = new PpfsImportDAO();

		StringBuffer mailBody = new StringBuffer();

		e.printStackTrace();

		mailBody.append("------- 実行パラメータ -------\n");
		mailBody.append("会社コード:" + companyCode + "\n");
		mailBody.append("取込対象:" + dataType + "\n");
		mailBody.append("実行者社員番号:" + execStaffCode + "\n");

		mailBody.append("\n");

		mailBody.append("------- 取込処理実行時例外 -------\n");
		mailBody.append(Function.getStackTraceStr(e));

		try {
			ppfsImportDAO.callUpdatePpfsStatus(companyCode, dataType, execStaffCode, Constants.PPFS_IMPORT_EXEC_STATUS_ERROR);
		} catch (Exception e2) {
			e2.printStackTrace();

			mailBody.append("\n");
			mailBody.append("------- 取込ステータス更新時例外 -------\n");
			mailBody.append(Function.getStackTraceStr(e2));
		}

		// 管理者ML宛にメール送信
		CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_SYSTEM_ADMIN_EMAIL, null, null, null);
		if(cn != null) {
			String systemAdminMl = cn.getValue1();
			sendMailSession.sendMail(systemAdminMl, systemAdminMl, null, "ProPlusデータ取込エラー通知", mailBody.toString());
		}
	}
}