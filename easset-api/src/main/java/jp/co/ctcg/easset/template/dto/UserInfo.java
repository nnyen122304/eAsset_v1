/*===================================================================
 * ファイル名 : UserInfo.java
 * 概要説明   : ユーザー情報データクラス（テンプレート）
 * 				※開発者変更不可
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-01-14 1.0  リヨン       新規
 *=================================================================== */

package jp.co.ctcg.easset.template.dto;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class UserInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	String userId;			//	ユーザーID
	String userName;		//	ユーザー名
	String respId;			//	職責
	String respName;		//	職責名
	String sessionId;		//	セッションID
	String applId;			//	アプリケーションID

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getRespId() {
		return respId;
	}
	public void setRespId(String respId) {
		this.respId = respId;
	}
	public String getRespName() {
		return respName;
	}
	public void setRespName(String respName) {
		this.respName = respName;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getApplId() {
		return applId;
	}
	public void setApplId(String applId) {
		this.applId = applId;
	}


}
