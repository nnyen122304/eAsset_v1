/*===================================================================
 * ファイル名 : SendMailSessionBean.java
 * 概要説明   : 履歴セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-2-21 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.mail.MessagingException;
import javax.mail.Session;

import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.MessageCreator;

@Stateless
public class SendMailSessionBean implements SendMailSession {

	@Resource
	SessionContext context;

	@Resource(name = "java:/mail/eAsset")
	private Session mailSession;

	private static final String KEYWORD_RELEASE = "send_to_real_address!!!";
	private static final String KEYWORD_TEST = "send_to_test_address";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.SendMailSession#sendMail(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void sendMail(String from, String to, String cc, String subject, String body) {
		ArrayList<String> toList = new ArrayList<String>();
		if(!Function.nvl(to, "").equals("")) toList.add(to);

		ArrayList<String> ccList = new ArrayList<String>();
		if(!Function.nvl(cc, "").equals("")) ccList.add(cc);

		sendMail(from, toList, ccList, null, subject, body, null);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.SendMailSession#sendMail(java.lang.String, java.util.List, java.util.List, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void sendMail(String from, List<String> toList, List<String> ccList, String subject, String body) {
		sendMail(from, toList, ccList, null, subject, body, null);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.SendMailSession#sendMail(java.lang.String, java.util.List, java.util.List, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void sendMail(String from, List<String> toList, List<String> ccList, String subject, String body, File[] attachFiles) {
		sendMail(from, toList, ccList, null, subject, body, attachFiles);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.SendMailSession#sendMail(java.lang.String, java.util.List, java.util.List, java.util.List, java.lang.String, java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void sendMail(String from, List<String> toList, List<String> ccList, List<String> bccList, String subject, String body) {
		sendMail(from, toList, ccList, bccList, subject, body, null);
	}

	/**
	 * メール送信
	 * @param from メール送信元
	 * @param toList TO
	 * @param ccList CC
	 * @param bccList BCC
	 * @param subject 件名
	 * @param body 本文
	 * @param attachFiles 添付
	 */
	private void sendMail(String from, List<String> toList, List<String> ccList, List<String> bccList, String subject, String body, File[] attachFiles) {
		// メッセージ送信オブジェクト
		MessageCreator msgCreator = new MessageCreator(mailSession);

		//////////////////////////////////// メールの送信元を設定
		msgCreator.setFrom(from);

		//////////////////////////////////// メールの宛先を設定
		// メールセッション環境設定取得
		String envSendKeyword = Function.nvl(mailSession.getProperty("mail.from"), "");
		if(envSendKeyword.equals(KEYWORD_RELEASE)) { // 本番設定
			// 本来の送信先をセット
			if(toList != null) {
				for (Iterator<String> iter = toList.iterator(); iter.hasNext(); ) {
					msgCreator.addTo(iter.next());
				}
			}
			if(ccList != null) {
				for (Iterator<String> iter = ccList.iterator(); iter.hasNext(); ) {
					msgCreator.addCC(iter.next());
				}
			}
			if(bccList != null) {
				for (Iterator<String> iter = bccList.iterator(); iter.hasNext(); ) {
					msgCreator.addBCC(iter.next());
				}
			}
		} else if(envSendKeyword.startsWith(KEYWORD_TEST)) { // テスト用設定
			// テスト用送信先をセット
			String[] testAddressArray = envSendKeyword.split(",");
			if(testAddressArray.length < 2) throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "メール送信") + "JavaMailセッションのFROM設定が不正です。");

			msgCreator.setFrom("");
			for(int i = 1; i < testAddressArray.length; i++) {
				msgCreator.addTo(testAddressArray[i]);
				if(i == 1) msgCreator.setFrom(testAddressArray[i]);
			}

			// 本来の送信先を本文に追記
			body = body + "\n\n※このメールはテスト用の送信先に配信されています。\n";
			body = body + "本来の配信先は以下になります。\n";
			body = body + "FROM:" + from + "\n";
			if(toList != null) {
				for (Iterator<String> iter = toList.iterator(); iter.hasNext(); ) {
					body = body + "TO:" + iter.next() + "\n";
				}
			}
			if(ccList != null) {
				for (Iterator<String> iter = ccList.iterator(); iter.hasNext(); ) {
					body = body + "CC:" + iter.next() + "\n";
				}
			}
			if(bccList != null) {
				for (Iterator<String> iter = bccList.iterator(); iter.hasNext(); ) {
					body = body + "BCC:" + iter.next() + "\n";
				}
			}
		} else {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "メール送信") + "JavaMailセッションのFROM設定が不正です。");
		}

		//////////////////////////////////// メールの件名を設定
		msgCreator.setSubject("【eAsset】" + subject);

		//////////////////////////////////// メールの本文を設定
		StringBuffer msgPrefix = new StringBuffer();
		msgPrefix.append("******************************************************************************\n");
		msgPrefix.append("　このメールは、eAssetシステムで処理された内容に従って送信されています。\n");
		msgPrefix.append("******************************************************************************\n\n");

		msgCreator.setText(msgPrefix.toString() + body + "\n\n");

		//////////////////////////////////// 添付ファイルを設定
		if(attachFiles != null && attachFiles.length > 0) {
			msgCreator.addFiles(attachFiles);
		}

		//////////////////////////////////// メールを送信
		try {
			msgCreator.send();
		} catch (MessagingException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "メール送信"), e);
		}
	}
}