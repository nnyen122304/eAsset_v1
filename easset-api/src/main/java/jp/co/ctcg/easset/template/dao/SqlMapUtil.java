/*===================================================================
 * ファイル名 : SqlMapUtil.java
 * 概要説明   : SQLマップユーティリティ（テンプレート）
 * 				※開発者変更不可
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-01-14 1.0  リヨン       新規
 *=================================================================== */

package jp.co.ctcg.easset.template.dao;

import java.io.Reader;
import java.nio.charset.Charset;
import java.util.HashMap;

import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.SysException;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

public class SqlMapUtil {

	private static SqlMapClient sqlMap = null;
//	public static SqlMapClient initInstance(String sSqlMapPath) throws SysException{


	static {
		try{
			String cSqlMapConfigPath = "jp/co/ctcg/easset/flex_common/config/SqlMapConfig.xml";
			Resources.setCharset(Charset.forName("UTF-8"));
			Reader reader = Resources.getResourceAsReader (cSqlMapConfigPath);
			sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
		}catch( Exception e ){
			// SqlMap生成エラー:$1
			throw new SysException (MsgManager.getMessage(MsgManager.MSGID100001 , e.toString()));
		}
	}

	/**
	 * @brief		インスタンス初期化（テンプレート）
	 * @param		なし
	 * @return		SqlMapClient	SQLマップインスタンス
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static SqlMapClient initInstance() throws SysException{
//		try{
//			if ( sqlMap == null ){
//				String cSqlMapConfigPath = "jp/co/ctcg/easset/flex_common/config/SqlMapConfig.xml";
//				Resources.setCharset(Charset.forName("UTF-8"));
//				Reader reader = Resources.getResourceAsReader (cSqlMapConfigPath);
//				sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
//			}
//		}catch( Exception e ){
//			// SqlMap生成エラー:$1
//			throw new SysException (MsgManager.getMessage(MsgManager.MSGID100001 , e.toString()));
//		}
		return sqlMap;
	}

	/**
	 * @brief		インスタンス生成（テンプレート）
	 * @param		HashMap<String, String> connectionInfo	ユーザー接続情報
	 * @return		SqlMapClient	SQLマップインスタンス
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static SqlMapClient getSqlMapInstance (HashMap<String, String> connectionInfo) throws SysException {
//		try {
//			//擬似ログイン
//			HashMap<String, String> param = new HashMap<String, String>();
//			param.put("userId",     connectionInfo.get("userId"));
//			param.put("respId",        connectionInfo.get("responsibility"));
//			param.put("applicationId", connectionInfo.get("applicationId"));
//			sqlMap.queryForObject("ebsInitial", param);
//		} catch (SQLException e) {
//			// 擬似ログイン処理に失敗しました。
//			throw new SysException(MsgManager.getMessage(MsgManager.MSGID100002 ), e);
//		}
		return sqlMap;
	}

	/**
	 * @brief		インスタンス生成（テンプレート）
	 * @param		HashMap<String, String> connectionInfo	ユーザー接続情報
	 * @return		SqlMapClient	SQLマップインスタンス
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public static SqlMapClient getSqlMapInstance () throws SysException {
		return sqlMap;
	}
}
