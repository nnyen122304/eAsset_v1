/*===================================================================
 * ファイル名 : HistSessionBean.java
 * 概要説明   : 履歴セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import jp.co.ctcg.easset.dao.HistDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.hist.BulkUpdateHist;
import jp.co.ctcg.easset.dto.hist.Hist;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.SysException;

@Stateless
public class HistSessionBean implements HistSession {

	@Resource
	SessionContext context;

	private static HashMap<String, String> KEY_COLUMN_MAP = new HashMap<String, String>();
	private static HashMap<String, String> CATEGORY_CODE_STATUS_MAP = new HashMap<String, String>();
	private static HashMap<String, String> CATEGORY_CODE_ITEM_DEF_MAP = new HashMap<String, String>();

	static {
		// キーカラム情報
		KEY_COLUMN_MAP.put("AP_GET_TAN", "APPLICATION_ID");
		KEY_COLUMN_MAP.put("AP_CHANGE", "APPLICATION_ID");
		KEY_COLUMN_MAP.put("AP_ASSET", "ASSET_ID");
		KEY_COLUMN_MAP.put("AP_LICENSE", "LICENSE_ID");
		KEY_COLUMN_MAP.put("ASSET", "ASSET_ID");
		KEY_COLUMN_MAP.put("LICENSE", "LICENSE_ID");
		KEY_COLUMN_MAP.put("AP_END_TAN", "APPLICATION_ID");
		KEY_COLUMN_MAP.put("INT_EXT", "APPLICATION_ID HOL_COMPANY_CODE");
		KEY_COLUMN_MAP.put("AP_GET_INT", "APPLICATION_ID APPLICATION_VERSION");
		KEY_COLUMN_MAP.put("AP_BGN_INT", "APPLICATION_ID");
		KEY_COLUMN_MAP.put("AP_END_LE", "APPLICATION_ID");
		KEY_COLUMN_MAP.put("AP_END_RE", "APPLICATION_ID");

		// ステータスカテゴリコード情報
		CATEGORY_CODE_STATUS_MAP.put("AP_GET_TAN", "AP_STATUS_GET_TAN");
		CATEGORY_CODE_STATUS_MAP.put("AP_CHANGE", "AP_STATUS_CHANGE");
		CATEGORY_CODE_STATUS_MAP.put("AP_ASSET", "AP_STATUS_ASSET");
		CATEGORY_CODE_STATUS_MAP.put("AP_LICENSE", "AP_STATUS_LICENSE");
		CATEGORY_CODE_STATUS_MAP.put("AP_END_TAN", "AP_STATUS_END_TAN");
		CATEGORY_CODE_STATUS_MAP.put("AP_GET_INT", "AP_STATUS_GET_INT");
		CATEGORY_CODE_STATUS_MAP.put("AP_BGN_INT", "AP_STATUS_BGN_INT");
		CATEGORY_CODE_STATUS_MAP.put("AP_END_LE", "AP_STATUS_END_LE");
		CATEGORY_CODE_STATUS_MAP.put("AP_END_RE", "AP_STATUS_END_RE");

		// 項目定義カテゴリコード情報
		CATEGORY_CODE_ITEM_DEF_MAP.put("AP_GET_TAN", "ITEM_DEF_AP_GET_TAN");
		CATEGORY_CODE_ITEM_DEF_MAP.put("AP_CHANGE", "ITEM_DEF_AP_CHANGE");
		CATEGORY_CODE_ITEM_DEF_MAP.put("AP_ASSET", "ITEM_DEF_AP_ASSET");
		CATEGORY_CODE_ITEM_DEF_MAP.put("AP_LICENSE", "ITEM_DEF_AP_LICENSE");
		CATEGORY_CODE_ITEM_DEF_MAP.put("ASSET", "ITEM_DEF_ASSET");
		CATEGORY_CODE_ITEM_DEF_MAP.put("LICENSE", "ITEM_DEF_LICENSE");
		CATEGORY_CODE_ITEM_DEF_MAP.put("AP_END_TAN", "ITEM_DEF_AP_END_TAN");
		CATEGORY_CODE_ITEM_DEF_MAP.put("INT_EXT", "ITEM_DEF_PPFS_FLD_SR_INT_APP");
		CATEGORY_CODE_ITEM_DEF_MAP.put("AP_GET_INT", "ITEM_DEF_AP_GET_INT");
		CATEGORY_CODE_ITEM_DEF_MAP.put("AP_BGN_INT", "ITEM_DEF_AP_BGN_INT");
		CATEGORY_CODE_ITEM_DEF_MAP.put("AP_END_LE", "ITEM_DEF_AP_END_LE");
		CATEGORY_CODE_ITEM_DEF_MAP.put("AP_END_RE", "ITEM_DEF_AP_END_RE");

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.HistSession#getHistList(java.lang.String, java.lang.String)
	 */
	public List<Hist> getHistList(String entityName, String keyValue) {
		try {
			HistDAO histDao = new HistDAO();
			return histDao.selectHistList(entityName, KEY_COLUMN_MAP.get(entityName), keyValue, CATEGORY_CODE_STATUS_MAP.get(entityName));
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "履歴検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.HistSession#createHist(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void createHistData(String entityName, String keyValue, String operation, String lineUpdateColumnName) {
		try {
			HistDAO histDao = new HistDAO();
			histDao.callCreateHistData(entityName, keyValue, operation, CATEGORY_CODE_ITEM_DEF_MAP.get(entityName), lineUpdateColumnName);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "履歴作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.HistSession#createOpLog(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void createOpLog(String staffCode, String function, String operation, String description) {
		try {
			HistDAO histDao = new HistDAO();
			histDao.insertComOp(staffCode, function, operation, description);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "操作ログ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.HistSession#createBulkUpdateLog(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // 新規トランザクション
	public Long createBulkUpdateLog(String companyCode, String staffCode, String function, String execFileId, List<CodeName> updateColumnList) {
		try {
			HistDAO histDao = new HistDAO();
			return histDao.insertBulkUpdateLog(companyCode, staffCode, function, execFileId, updateColumnList);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "一括更新ログ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.HistSession#updateBulkUpdateLog(java.lang.Long, java.lang.String, java.lang.String, java.lang.Integer, java.lang.Integer, java.lang.Integer)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // 新規トランザクション
	public void updateBulkUpdateLog(Long logId, String staffCode, String execStatus, Integer execCount, Integer successCount, Integer failureCount) {
		try {
			HistDAO histDao = new HistDAO();
			histDao.updateBulkUpdateLog(logId, staffCode, execStatus, execCount, successCount, failureCount);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "一括更新ログ更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.HistSession#getBulkUpdateHistList(java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.util.Date, boolean)
	 */
	public List<BulkUpdateHist> getBulkUpdateHistList(String companyCode, String staffCode, String function, Date createDateFrom, Date createDateTo, boolean isExecOnly) {
		try {
			HistDAO histDao = new HistDAO();
			return histDao.selectBulkUpdateHistList(companyCode, staffCode, function, createDateFrom, createDateTo, isExecOnly);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "一括更新履歴検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.HistSession#getBulkUpdateHist(java.lang.Long)
	 */
	public BulkUpdateHist getBulkUpdateHist(Long logId) {
		try {
			HistDAO histDao = new HistDAO();
			return histDao.selectBulkUpdateHist(logId);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "一括更新履歴検索"), e);
		}
	}

}