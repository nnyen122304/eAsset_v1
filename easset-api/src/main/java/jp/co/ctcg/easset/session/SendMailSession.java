/*===================================================================
 * ファイル名 : SendMailSession.java
 * 概要説明   : 履歴セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-02-21 1.0  リヨン           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.io.File;
import java.util.List;

import javax.ejb.Local;

@Local
public interface SendMailSession {

	/**
	 * メール送信
	 * @param from メール送信元
	 * @param to TO
	 * @param cc CC
	 * @param subject 件名
	 * @param body 本文
	 */
	public void sendMail(String from, String to, String cc, String subject, String body);

	/**
	 * メール送信
	 * @param from メール送信元
	 * @param toList TO(複数)
	 * @param ccList CC(複数)
	 * @param subject 件名
	 * @param body 本文
	 */
	public void sendMail(String from, List<String> toList, List<String> ccList, String subject, String body);

	/**
	 * メール送信
	 * @param from メール送信元
	 * @param toList TO(複数)
	 * @param ccList CC(複数)
	 * @param subject 件名
	 * @param body 本文
	 * @param attachFiles 添付ファイル
	 */
	public void sendMail(String from, List<String> toList, List<String> ccList, String subject, String body, File[] attachFiles);

	/**
	 * メール送信
	 * @param from メール送信元
	 * @param toList TO(複数)
	 * @param ccList CC(複数)
	 * @param bccList BCC(複数)
	 * @param subject 件名
	 * @param body 本文
	 */
	public void sendMail(String from, List<String> toList, List<String> ccList, List<String> bccList, String subject, String body);


}
