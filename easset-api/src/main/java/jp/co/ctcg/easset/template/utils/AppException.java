/*===================================================================
 * ファイル名 : AppException.java
 * 概要説明   : アプリケーション例外クラス（テンプレート）
 * 				※開発者による変更不可
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-01-14 1.0  リヨン       新規
 *=================================================================== */

package jp.co.ctcg.easset.template.utils;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class AppException extends Exception {

	private static final long serialVersionUID = 1L;

	/**
	 * @brief		コンストラクタ
	 * @param		String arg0		エラーメッセージ
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public AppException(String arg0) {
	  super(arg0);
	}

	/**
	 * @brief		コンストラクタ
	 * @param		String arg0		エラーメッセージ
	 * 				Throwable arg1	例外
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public AppException(String arg0, Throwable arg1) {
	  super(arg0, arg1);
	}

	/**
	 *
	 * @return Flex側にExceptionの名前を取得させるため
	 */
	/**
	 * @brief		Flex側にExceptionの名前を取得させるため関数
	 * @param		なし
	 * @return		String	例外名
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public String getExceptionName(){
		return "AppException";

	}

}
