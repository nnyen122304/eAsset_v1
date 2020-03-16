/*===================================================================
 * ファイル名 : AppSessionBean.java
 * 概要説明   : セッションEJB定義（テンプレート）
 * 				※開発者による変更不可
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-01-14 1.0  リヨン       新規
 *=================================================================== */

package jp.co.ctcg.easset.template.session;

import java.sql.SQLException;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import jp.co.ctcg.easset.template.dao.AppDAO;
import jp.co.ctcg.easset.template.dto.UserInfo;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.SysException;

@Stateless
public class AppSessionBean implements AppSession {

	@Resource
	SessionContext context;

	/**
	 * @brief		ユーザー情報取得（テンプレート）
	 * @param		HashMap<String, String> connectionInfo	ユーザー接続情報
	 * 				（本パラメータは固定で第１パラメータにセットする）
	 * @return		UserInfo	ユーザー情報の番号
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public UserInfo getUserInfo(HashMap<String, String> connectionInfo,String sessionId) throws SysException {
		AppDAO appDao = new AppDAO();

		try {
			return appDao.getUserInfo(connectionInfo,sessionId);
		} catch (SQLException e) {
			// ログインユーザー情報の取得に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID100003), e);

		}
	}

	/**
	 * @brief		Flex側メッセージ出力（テンプレート）
	 * @param		String msg	メッセージ
	 * 				int level	出力レベル
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public void loggingMessage(String msg, int level) throws SysException {
		// String message = "";

		// message += "Flex:";
		// message += msg;

		try {
			switch(level) {
				case	0:
					Logging.debug(msg);
					break;
				case	1:
					Logging.fatal(msg);
					break;
				case	2:
					Logging.error(msg);
					break;
				case	3:
					Logging.warning(msg);
					break;
				case	4:
					Logging.info(msg);
					break;
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @brief		EBSホームURL情報取得（テンプレート）
	 * @param		HashMap<String, String> connectionInfo	ユーザー接続情報
	 * @return		String EBSホームURL
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public String getEBSHomeURL(HashMap<String, String> connectionInfo) throws SysException {
		AppDAO appDao = new AppDAO();

		try {
			return appDao.getEBSHomeURL(connectionInfo);
		} catch (SQLException e) {
			// SQLの実行に失敗しました。（SQL名：$1）
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID100017, "XXSCM_NEXTMI_HOME"), e);
		}
	}
}
