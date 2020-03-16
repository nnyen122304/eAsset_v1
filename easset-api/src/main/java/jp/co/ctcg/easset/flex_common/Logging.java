/*===================================================================
 * ファイル名 : Logging.java
 * 概要説明   : ログ出力クラス（テンプレート）
 * 				※開発者による変更不可
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-01-14 1.0  リヨン       新規
 *=================================================================== */

package jp.co.ctcg.easset.flex_common;

import org.apache.commons.logging.*;

public final class Logging {

	/////////////////////////////////////////////////////////////////////////
	// ここのパスを変更してください！
	/////////////////////////////////////////////////////////////////////////
	protected static Log log = LogFactory.getLog("jp.co.ctcg.easset");


	/**
	 * @brief		INFO レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void info(String msg) {
		log.info(msg);
	}

	/**
	 * @brief		WARNING レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void warning(String msg) {
		log.warn(msg);
	}

	/**
	 * @brief		FATAL レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void fatal(String msg) {
		log.fatal(msg);
	}

	/**
	 * @brief		DEBUG レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void debug(String msg) {
		log.debug(msg);
	}

	/**
	 * @brief		ERROR レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void error(String msg) {
		log.error(msg);
	}

	/**
	 * @brief		INFO レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * 				Throwable t		例外
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void info(String msg, Throwable t) {
		log.info(msg, t);
	}

	/**
	 * @brief		WARNING レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * 				Throwable t		例外
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void warning(String msg, Throwable t) {
		log.warn(msg, t);
	}

	/**
	 * @brief		FATAL レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * 				Throwable t		例外
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void fatal(String msg, Throwable t) {
		log.fatal(msg, t);
	}

	/**
	 * @brief		DEBUG レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * 				Throwable t		例外
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void debug(String msg, Throwable t) {
		log.debug(msg, t);
	}

	/**
	 * @brief		ERROR レベルメッセージのログを出力
	 * @param		String msg		メッセージ
	 * 				Throwable t		例外
	 * @return		なし
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static void error(String msg, Throwable t) {
		log.error(msg, t);
	}
}
