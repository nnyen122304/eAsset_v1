/*===================================================================
 * ファイル名 : AppSession.java
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

import java.util.HashMap;

import javax.ejb.Local;

import jp.co.ctcg.easset.template.dto.UserInfo;
import jp.co.ctcg.easset.template.utils.SysException;

@Local
public interface AppSession {
	public UserInfo getUserInfo(HashMap<String, String> connectionInfo, String sessionId) throws SysException;
	public void loggingMessage(String msg, int level) throws SysException;
	public String getEBSHomeURL(HashMap<String, String> connectionInfo) throws SysException;
}
