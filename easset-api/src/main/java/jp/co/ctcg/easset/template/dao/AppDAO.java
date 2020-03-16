/*===================================================================
 * ファイル名 : AppDAO.java
 * 概要説明   : DAO定義（テンプレート）
 * 				※開発者変更不可
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-01-14 1.0  リヨン       新規
 *=================================================================== */

package jp.co.ctcg.easset.template.dao;

import java.sql.SQLException;
import java.util.HashMap;

import jp.co.ctcg.easset.template.dto.UserInfo;

import com.ibatis.sqlmap.client.SqlMapClient;

public class AppDAO {

	/**
	 * @brief		ユーザー情報取得（テンプレート）
	 * @param		HashMap<String, String> connectionInfo	ユーザー接続情報
	 * @return		UserInfo	ユーザー情報
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public UserInfo getUserInfo(HashMap<String, String> connectionInfo, String sessionId) throws SQLException {

		// eAsset用にダミーを返す。
		return new UserInfo();

//		UserInfo ui = new UserInfo();
////		SqlMapClient sqlMap = SqlMapUtil.initInstance((String)connectionInfo.get("sqlMapConfigPath"));
//		SqlMapClient sqlMap = SqlMapUtil.initInstance();
//
//		HashMap<String, String> param = new HashMap<String, String>();
//		param.put("sessionId", sessionId);
//		param.put("result",   new String());
//		param.put("respId",   new String());
//		param.put("respName", new String());
//		param.put("userId",   new String());
//		param.put("userName", new String());
//		param.put("applId",   new String());
//		sqlMap.queryForObject("selectUserInfo", param);
//
////		System.out.println(param.get("result"));
//
//		if ( ((String)param.get("result")).compareTo("0") == 0 ){
//			ui.setSessionId((String)param.get("sessionId"));
//			ui.setRespId((String)param.get("respId"));
//			ui.setRespName((String)param.get("respName"));
//			ui.setUserId((String)param.get("userId"));
//			ui.setUserName((String)param.get("userName"));
//			ui.setApplId((String)param.get("applId"));
//		}else{
//			ui = null;
//		}
	}

	/**
	 * @brief		EBSホームURL情報取得（テンプレート）
	 * @param		HashMap<String, String> connectionInfo	ユーザー接続情報
	 * @return		String EBSホームURL
	 * @author		リヨン
	 * @attention	テンプレートなので編集不可
	 */
	public String getEBSHomeURL(HashMap<String, String> connectionInfo) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance(connectionInfo);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (String)sqlMap.queryForObject("ebsHomeURL");

	}
}
