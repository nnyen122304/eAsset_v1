/*===================================================================
 * ファイル名 : Function.java
 * 概要説明   : 共通ユーティリティ関数
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-04-28 1.0  リヨン       新規
 *=================================================================== */
package jp.co.ctcg.easset.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;

import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.SysException;

import org.apache.commons.beanutils.PropertyUtils;

public class Function {

	/**
	 * @brief		readExternalでのLong値取得
	 * @param		Object obj	変換元の値
	 * @return		Long 変換後の値
	 * @author		リヨン
	 * @attention
	 */
	static public Long getExternalLong(Object obj) throws ClassCastException {

		if(obj != null) {
			if(obj instanceof Number) {
				return ((Number)obj).longValue();
			} else {
				throw new ClassCastException("Long数値データではありません。");
			}
		}
		return null;
	}

	/**
	 * 文字列を数値に強制変換(Long)。
	 *
	 * @param sNo     変換する文字列
	 * @return        変換後の数値
	 */
	public static Long getLong(String sNo) {
		try {
			Long l = (Long.valueOf(sNo));
			return l;
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * @brief		readExternalでのInteger値取得
	 * @param		Object obj	変換元の値
	 * @return		Integer 変換後の値
	 * @author		リヨン
	 * @attention
	 */
	static public Integer getExternalInteger(Object obj) throws ClassCastException {

		if(obj != null) {
			if(obj instanceof Number) {
				return ((Number)obj).intValue();
			} else {
				throw new ClassCastException("Integer数値データではありません。");
			}
		}
		return null;
	}


	/**
	 * @brief		readExternalでのDouble値取得
	 * @param		Object obj	変換元の値
	 * @return		Double 変換後の値
	 * @author		リヨン
	 * @attention
	 */
	static public Double getExternalDouble(Object obj) throws ClassCastException {

		if(obj != null) {
			if(obj instanceof Number) {
				return ((Number)obj).doubleValue();
			} else {
				throw new ClassCastException("Double数値データではありません。");
			}
		}
		return null;
	}

	/**
	 * @brief	検索条件で使用するIN句を1000個単位でOR分で生成
	 * @param	columnName	検索対象カラム名
	 *			code 		検索対象値リスト
	 * 			quoteFlag	TRUE:クォーテーションで囲む
	 * 						FALSE:囲まない
	 * @return	検索条件WHERE句
	 */
	static public String getInCondition(String columnName, List<?> list, Boolean quoteFlag) {
		if(list == null) return null;

		StringBuffer conditionStr = new StringBuffer();
		conditionStr.append("(");
		conditionStr.append(columnName + " IN (");

		int size = list.size();
		int codeCount = 0;
		for(int i = 0; i < size; i++) {
			codeCount++;

			String value = list.get(i).toString();
			if(quoteFlag) value = value.replaceAll("'", "''");

			if(codeCount > 1000) { // 1000個単位でIN句をOR結合(ORACLE最大)
				conditionStr.append(") OR " + columnName + " IN (");
				codeCount = 1;
			} else if(codeCount > 1) {
				conditionStr.append(",");
			}
			if(quoteFlag) {
				conditionStr.append("'" + value + "'");
			}
			else {
				conditionStr.append(value);
			}
		}

		conditionStr.append(")");
		conditionStr.append(")");

		return conditionStr.toString();
	}

	/**
	 * @brief	検索条件で使用するIN句とその指定条件をTEMPテーブルを使用した以下のようなサブクエリSQLに変換する
	 *			(columnName IN (select [varchar_value or numeric_value] from nea_temp_serach where search_key = 'xxx'))
	 *			nea_temp_searchにはlistパラメータの値が全て自動的に挿入される。
	 *			（挿入データはsearch_key（自動採番）によりユニークに識別される。）
	 *
	 *			[varchar_value or numeric_value]:listはリスト内のデータ型によりどちらかのカラムが自動的に使用される。
	 *
	 * @param	columnName	検索対象カラム名
	 *			list 		検索対象値リスト(String or Numberのリストが使用可能)
	 *			dao			TEMPテーブルにデータを投入するためのDAO
	 * @return	検索条件WHERE句
	 * @throws SQLException
	 */
	static public String getInConditionByTempTable(String columnName, List<?> list, MasterDAO dao) throws SQLException {
		return getInConditionByTempTable(columnName, list, dao, 0);
	}

	/**
	 * @brief	検索条件で使用するIN句とその指定条件をTEMPテーブルを使用した以下のようなサブクエリSQLに変換する
	 *			(columnName IN (select [varchar_value or numeric_value] from nea_temp_serach where search_key = 'xxx'))
	 *			nea_temp_searchにはlistパラメータの値が全て自動的に挿入される。
	 *			（挿入データはsearch_key（自動採番）によりユニークに識別される。）
	 *
	 *			[varchar_value or numeric_value]:listはリスト内のデータ型によりどちらかのカラムが自動的に使用される。
	 *
	 * @param	columnName	検索対象カラム名
	 *			list 		検索対象値リスト(String or Numberのリストが使用可能)
	 *			dao			TEMPテーブルにデータを投入するためのDAO
	 *			charSize	columnName指定のカラムがchar型（固定長）の場合は、そのサイズを指定
	 * @return	検索条件WHERE句
	 * @throws SQLException
	 */
	static public String getInConditionByTempTable(String columnName, List<?> list, MasterDAO dao, int charSize) throws SQLException {
		if(list == null || list.size() == 0) return null;

		String tempColumnName;
		int jdbcType;
		if(list.get(0) instanceof Number) {
			tempColumnName = "NUMERIC_VALUE";
			jdbcType = java.sql.Types.NUMERIC;
		} else {
			tempColumnName = "VARCHAR_VALUE";
			jdbcType = java.sql.Types.VARCHAR;

			if(charSize != 0) { // 固定長カラムのサイズ指定がある場合固定長サイズにpadding
				tempColumnName = "CASE WHEN LENGTHB(" + tempColumnName + ") < " + charSize + " THEN RPAD(" + tempColumnName + ", " + charSize + ") ELSE " + tempColumnName + " END ";
			}
		}

		// TEMPテーブル内のデータ識別用ユニーク文字列生成
		String searchKey = java.util.UUID.randomUUID().toString();

		int size = list.size();
		for(int i = 0; i < size; i++) {
			Object value = list.get(i);

			// TEMPテーブルに検索条件データを挿入
			dao.insertTempSearch(searchKey, i+1, jdbcType, value);
		}
		StringBuffer conditionStr = new StringBuffer();
		conditionStr.append("(");
		conditionStr.append(columnName + " IN ( SELECT " + tempColumnName + " FROM NEA_TEMP_SEARCH WHERE SEARCH_KEY = '" + searchKey + "' )");
		conditionStr.append(")");

		return conditionStr.toString();
	}

	/**
	 * @brief	検索条件で使用するNOTIN句を1000個単位でOR分で生成
	 * @param	columnName	検索対象カラム名
	 *			code 		検索対象値リスト
	 * 			quoteFlag	TRUE:クォーテーションで囲む
	 * 						FALSE:囲まない
	 * @return	検索条件WHERE句
	 */
	static public String getNotInCondition(String columnName, List<?> list, Boolean quoteFlag) {
		if(list == null) return null;

		StringBuffer conditionStr = new StringBuffer();
		conditionStr.append("(");
		conditionStr.append(columnName + " NOT IN (");

		int size = list.size();
		int codeCount = 0;
		for(int i = 0; i < size; i++) {
			codeCount++;

			String value = list.get(i).toString();
			if(quoteFlag) value = value.replaceAll("'", "''");

			if(codeCount > 1000) { // 1000個単位でIN句をOR結合(ORACLE最大)
				conditionStr.append(") OR " + columnName + " NOT IN (");
				codeCount = 1;
			} else if(codeCount > 1) {
				conditionStr.append(",");
			}
			if(quoteFlag) {
				conditionStr.append("'" + value + "'");
			}
			else {
				conditionStr.append(value);
			}
		}

		conditionStr.append(")");
		conditionStr.append(")");

		return conditionStr.toString();
	}

	/**
	 * @brief	検索条件で使用するIN句を1000個単位でOR分で生成
	 * @param	columnName	検索対象カラム名
	 * @param	list 		検索対象値リスト
	 * @return	検索条件WHERE句
	 */
	static public String getFuzzyOrCondition(String columnName, List<String> list) {
		if(list == null) return null;

		StringBuffer conditionStr = new StringBuffer();
		conditionStr.append("(");

		int size = list.size();
		for(int i = 0; i < size; i++) {

			if(i != 0) conditionStr.append(" OR ");
			String value = list.get(i).toString().replaceAll("'", "''");

			conditionStr.append("NEA_UTIL_PKG.TO_FUZZY_F(" + columnName + ") LIKE NEA_UTIL_PKG.TO_FUZZY_F('%" + value + "%')");
		}

		conditionStr.append(")");

		return conditionStr.toString();
	}

	/**
	 * @brief	部署アクセスレベル制御用SQL条件取得
	 * 			社員番号を指定した場合は 社員 or 部署のどちらかがヒットすれば検索対象
	 * 			社員番号を指定しない場合は 部署のみを検索対象
	 * @param	columnNameStaffCodeList		社員番号検索対象カラム名（複数OR）
	 * @param	columnNameCompanyCodeList	会社コード検索対象カラム名（複数OR）
	 * @param	columnNameSectionCodeList	部署コード検索対象カラム名(複数OR)
	 * @param	columnNameSectionYearList	部署年度検索対象カラム名(複数OR)
	 * @param	companyCode 		検索対象会社コード
	 * @param	staffCode 		検索対象社員番号
	 * @param	sectionCodeList 		検索対象部署コード(複数OR)
	 * @param	sectionYear 		検索対象部署年度
	 * @return	検索条件WHERE句
	 */
	static public String getSectionCondition(List<String> columnNameStaffCodeList, List<String> columnNameCompanyCodeList, List<String> columnNameSectionCodeList, List<String> columnNameSectionYearList, String companyCode, String staffCode, List<String> sectionCodeList, Integer sectionYear) {
		StringBuffer conditionStr = new StringBuffer();
		conditionStr.append("(");

		// 社員番号
		if(columnNameStaffCodeList != null && columnNameStaffCodeList.size() > 0) {
			conditionStr.append("(");
			for(int i = 0; i < columnNameStaffCodeList.size(); i++) {
				if(i > 0) conditionStr.append(" OR ");
				conditionStr.append(columnNameStaffCodeList.get(i) + " = '" + staffCode + "'"); // 社員番号条件
			}
			conditionStr.append(")");

			conditionStr.append(" OR ");
		}

		conditionStr.append("(");

		conditionStr.append("("); // 会社・部署・部署年度
		if(sectionCodeList != null && sectionCodeList.size() > 0) {
			List<String> sectionParamList = new ArrayList<String>();
			for(int i = 0; i < sectionCodeList.size(); i++) {
				sectionParamList.add("('" + companyCode + "','" + sectionCodeList.get(i) + "'," + sectionYear + ")");
			}
			for(int i = 0; i < columnNameSectionCodeList.size(); i++) {
				if(i > 0) conditionStr.append(" OR ");

				conditionStr.append("(");
				conditionStr.append(Function.getInCondition("(" + columnNameCompanyCodeList.get(i) + "," + columnNameSectionCodeList.get(i) + "," + columnNameSectionYearList.get(i) + ")", sectionParamList, false));
				conditionStr.append(")");
			}
		} else {
			for(int i = 0; i < columnNameSectionCodeList.size(); i++) {
				if(i > 0) conditionStr.append(" OR ");

				conditionStr.append("(");
				conditionStr.append(columnNameCompanyCodeList.get(i) + " = '" + companyCode + "'");
				conditionStr.append(")");
			}
		}
		conditionStr.append(")");

		conditionStr.append(")");

		// 会社・部署・部署年度
/*
		conditionStr.append("(");

		conditionStr.append(columnNameCompanyCode + " = " + "'" +  companyCode + "'"); // 会社

		conditionStr.append(" AND ");
		conditionStr.append("("); // 部署
		for(int i = 0; i < columnNameSectionCodeList.size(); i++) {
			if(i > 0) conditionStr.append(" OR ");

			conditionStr.append("(");
			conditionStr.append(Function.getInCondition(columnNameSectionCodeList.get(i), sectionCodeList, true));
			conditionStr.append(")");
		}
		conditionStr.append(")");

		conditionStr.append(" AND ");
		conditionStr.append("("); // 年度
		for(int i = 0; i < columnNameSectionYearList.size(); i++) {
			if(i > 0) conditionStr.append(" AND ");

			conditionStr.append(columnNameSectionYearList.get(i) + " = " + sectionYear);
		}
		conditionStr.append(")");

		conditionStr.append(")");
*/
		conditionStr.append(")");

		return conditionStr.toString();
	}


	/**
	 * @brief	文字列日付からDate型日付取得
	 * @param	strDate		変換対象日付
	 *			formatStr 	日付フォーマット
	 * @return	日付型
	 */
	static public java.util.Date getDateFromStr(String strDate, String formatStr) {
		SimpleDateFormat sdf = new SimpleDateFormat();
	    sdf.setLenient( false );
	    sdf.applyPattern(formatStr);
	    try {
			return  sdf.parse(strDate);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * date の fieldで指定したフィールドに、valを足した値を取得する。
	 * @param date 日付
	 * @param field Calendarの定数フィールド
	 * @param val dateに足す値
	 * @return dateにvalを足した値
	 */
	public static Date dateAdd(Date date, int field, int val) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(field, val);

		return cal.getTime();
	}

	/**
	 * AppExceptionのメッセージを、画面のエラーメッセージ表示する際
	 * 行でデータを区切って表示させる場合に使用する特殊な文字
	 * @return
	 */
	static public String getAppExceptionMsgDivStr() {
		return "$$$$$";
	}

	/**
	 * CSV用改行文字
	 * @return
	 */
	static public String getCsvEnterStr() {
		return "[改行]";
	}

	/**
	 * Oracleのnvl関数と同様
	 * @param value
	 * @param nullValue
	 * @return valueがnullの場合:nullValue、その他の場合:value
	 */
	public static String nvl(String value, String nullValue) {
		if(value == null) {
			return nullValue;
		} else {
			return value;
		}
	}

	/**
	 * Oracleのnvl関数と同様
	 * @param value
	 * @param nullValue
	 * @return valueがnullの場合:nullValue、その他の場合:value
	 */
	public static String nvl(Object value, String nullValue) {
		if(value == null) {
			return nullValue;
		} else {
			return value.toString();
		}
	}

	/**
	 * Oracleのnvl関数と同様
	 * @param value
	 * @param nullValue
	 * @return valueがnullの場合:nullValue、その他の場合:value
	 */
	public static Long nvl(Long value, Long nullValue) {
		if(value == null) {
			return nullValue;
		} else {
			return value;
		}
	}

	/**
	 * Oracleのnvl関数と同様
	 * @param value
	 * @param nullValue
	 * @return valueがnullの場合:nullValue、その他の場合:value
	 */
	public static Integer nvl(Integer value, Integer nullValue) {
		if(value == null) {
			return nullValue;
		} else {
			return value;
		}
	}

	/**
	 * Oracleのnvl関数と同様
	 * @param value
	 * @param nullValue
	 * @return valueがnullの場合:nullValue、その他の場合:value
	 */
	public static Double nvl(Double value, Double nullValue) {
		if(value == null) {
			return nullValue;
		} else {
			return value;
		}
	}

	/**
	 * Oracleのnvl関数と同様
	 * @param value
	 * @param nullValue
	 * @return valueがnullの場合:nullValue、その他の場合:value
	 */
	public static Date nvl(Date value, Date nullValue) {
		if(value == null) {
			return nullValue;
		} else {
			return value;
		}
	}

	/**
	 * 複数検索キーワード(半全角スペース区切り)をリストに変換
	 * @param value 複数検索キーワード(半全角スペース区切り)
	 * @return
	 */
	public static List<String> getPluralList(String value) {
		ArrayList<String> ret = new ArrayList<String>();

		if(!nvl(value, "").equals("")) {
			String s = value.replaceAll("(　|\\s)+", " "); // 全半角スペースの連続を半角スペース一つに置換
			s = s.trim(); // 前後のスペースを除去
			String[] split = s.split("\\s"); // 半角スペースで分割

			for(int i = 0; i < split.length; i++) {
				if(!split[i].equals("")) ret.add(split[i]);
			}
		}

		return ret;
	}

	/**
	 * アクセスレベルが全社権限/ADMINレベルかどうか判別
	 * @param accessLevel アクセスレベル
	 * @return true:全社権限/ADMINレベル false:全社権限/ADMINレベル以外
	 */
	public static boolean isAccessLevelAdmin(String accessLevel) {
		if(accessLevel == null) return false;

		boolean ret = false;
		if(accessLevel.equals(Constants.ACCESS_LEVEL_ADMIN_COMPANY) || accessLevel.equals(Constants.ACCESS_LEVEL_ADMIN_COMPANY_LIMIT) || accessLevel.equals(Constants.ACCESS_LEVEL_ADMIN_SYSTEM)) {
			ret = true;
		}

		return ret;
	}

	/**
	 * アクセスレベルがADMINレベルかどうか判別
	 * @param accessLevel アクセスレベル
	 * @return true:ADMINレベル false:ADMINレベル以外
	 */
	public static boolean isAccessLevelAdminSystem(String accessLevel) {
		if(accessLevel == null) return false;

		boolean ret = false;
		if(accessLevel.equals(Constants.ACCESS_LEVEL_ADMIN_SYSTEM)) {
			ret = true;
		}

		return ret;
	}

	/**
	 * アクセスレベルが全社権限(ADMIN以外)レベルかどうか判別
	 * @param accessLevel アクセスレベル
	 * @return true:全社権限レベル false:全社権限レベル以外
	 */
	public static boolean isAccessLevelAdminCompany(String accessLevel) {
		if(accessLevel == null) return false;

		boolean ret = false;
		if(accessLevel.equals(Constants.ACCESS_LEVEL_ADMIN_COMPANY) || accessLevel.equals(Constants.ACCESS_LEVEL_ADMIN_COMPANY_LIMIT)) {
			ret = true;
		}

		return ret;
	}

	/**
	 * アクセスレベルが部署レベルかどうか判別
	 * @param accessLevel アクセスレベル
	 * @return true:部署レベル false:部署レベル以外
	 */
	public static boolean isAccessLevelSection(String accessLevel) {
		if(accessLevel == null) return false;

		boolean ret = false;
		if(accessLevel.equals(Constants.ACCESS_LEVEL_SECTION_MANAGER)) {
			ret = true;
		}

		return ret;
	}

	/**
	 * アクセスレベルが一般かどうか判別
	 * @param accessLevel アクセスレベル
	 * @return true:一般 false:一般以外
	 */
	public static boolean isAccessLevelGeneral(String accessLevel) {
		if(accessLevel == null) return false;

		boolean ret = false;
		if(accessLevel.equals(Constants.ACCESS_LEVEL_SECTION_GENERAL)) {
			ret = true;
		}

		return ret;
	}

	/**
	 * Itemの内容が変更されているかどうかのチェック
	 * @param obj1 比較するObject1
	 * @param obj2 比較するObject1
	 * @return true:変更されている、false:変更されていない
	 */
	public static boolean isItemChange(Object obj1, Object obj2) {
		// リスト件数による単純比較
		if(obj1 == null && obj2 == null) { // 両方NULLの場合は変更なしと判別
			return false;
		} else if(obj1 == null && obj2 != null) { // どちらか片方だけNULLの場合は変更有と判別
			return true;
		} else if(obj1 != null && obj2 == null) { // どちらか片方だけNULLの場合は変更有と判別
			return true;
		}


		// データ比較
		boolean ret = false;

		try {
			PropertyDescriptor[] d = PropertyUtils.getPropertyDescriptors(obj1.getClass()); // プロパティ定義取得
			List<PropertyDescriptor> checkPropertyList = new ArrayList<PropertyDescriptor>();

			for(int i = 0; i < d.length; i++) {
				String propertyName = d[i].getName();

				// チェック対象外のプロパティは除外
				if(!propertyName.equals("createDate")
					&& !propertyName.equals("createStaffCode")
					&& !propertyName.equals("updateDate")
					&& !propertyName.equals("updateStaffCode")
				) {
					checkPropertyList.add(d[i]);
				}
			}

			for(int j = 0; j < checkPropertyList.size(); j++) {
				Method getter = checkPropertyList.get(j).getReadMethod();

				Object val1 = getter.invoke(obj1, (Object[]) null);
				Object val2 = getter.invoke(obj2, (Object[]) null);

				// 変更判別(変更がある場合は即座にbreak)
				if(val1 == null && val2 != null) {
					ret = true;
					break;
				} else if(val1 != null && val2 == null) {
					ret = true;
					break;
				} else if(val1 != null && val2 != null) {
					if(!val1.equals(val2)) {
						ret = true;
						break;
					}
				}
			}
		} catch (IllegalArgumentException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "新旧項目比較"), e);
		} catch (IllegalAccessException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "新旧項目比較"), e);
		} catch (InvocationTargetException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "新旧項目比較"), e);
		}

		return ret;
	}

	/**
	 * リストの内容が変更されているかどうかのチェック
	 * @param list1 比較するリスト1
	 * @param list2 比較するリスト1
	 * @return true:変更されている、false:変更されていない
	 *         createDate, createStaffCode, updateDate, updateStaffCodeプロパティは自動的に比較対象外
	 */
	public static boolean isListChange(List<?> list1, List<?> list2) {
		return isListChange(list1, list2, null);
	}

	/**
	 * リストの内容が変更されているかどうかのチェック
	 * @param list1 比較するリスト1
	 * @param list2 比較するリスト1
	 * @param excludeProps 比較除外プロパティ
	 * @return true:変更されている、false:変更されていない
	 *         createDate, createStaffCode, updateDate, updateStaffCodeプロパティは自動的に比較対象外
	 */
	public static boolean isListChange(List<?> list1, List<?> list2, String[] excludeProps) {
		// リスト件数による単純比較
		if(list1 == null && list2 == null) { // 両方NULLの場合は変更なしと判別
			return false;
		} else if(list1 == null && list2 != null) { // どちらか片方だけNULLの場合
			if(list2.size() == 0) { // NULLとサイズ0は同じ扱い
				return false;
			} else {
				return true;
			}
		} else if(list1 != null && list2 == null) { // どちらか片方だけNULLの場合
			if(list1.size() == 0) { // NULLとサイズ0は同じ扱い
				return false;
			} else {
				return true;
			}
		} else if(list1.size() != list2.size()) { // 件数が異なる場合は変更有と判別
			return true;
		} else if(list1.size() == 0 && list2.size() == 0) { // 両方0件の場合は変更なしと判別
			return false;
		}

		// 件数が同じ場合のデータ比較
		boolean ret = false;

		try {
			PropertyDescriptor[] d = PropertyUtils.getPropertyDescriptors(list1.get(0).getClass()); // プロパティ定義取得
			List<PropertyDescriptor> checkPropertyList = new ArrayList<PropertyDescriptor>();

			HashSet<String> excludePropSet = new HashSet<String>();

			if(excludeProps != null && excludeProps.length > 0) {
				for(int i = 0; i < excludeProps.length; i++) {
					excludePropSet.add(excludeProps[i]);
				}
			}

			for(int i = 0; i < d.length; i++) {
				String propertyName = d[i].getName();

				// チェック対象外のプロパティは除外
				if(!propertyName.equals("createDate")
					&& !propertyName.equals("createStaffCode")
					&& !propertyName.equals("updateDate")
					&& !propertyName.equals("updateStaffCode")
					&& !excludePropSet.contains(propertyName)
				) {
					checkPropertyList.add(d[i]);
				}
			}

			break_point:
			for(int i = 0; i < list1.size(); i++) {
				Object data1 = list1.get(i);
				Object data2 = list2.get(i);

				for(int j = 0; j < checkPropertyList.size(); j++) {
					Method getter = checkPropertyList.get(j).getReadMethod();

					Object val1 = getter.invoke(data1, (Object[]) null);
					Object val2 = getter.invoke(data2, (Object[]) null);

					// 変更判別(変更がある場合は即座にbreak)
					if(val1 == null && val2 != null) {
						ret = true;
						break break_point;
					} else if(val1 != null && val2 == null) {
						ret = true;
						break break_point;
					} else if(val1 != null && val2 != null) {
						if(!(val1 instanceof Collection) && !val1.equals(val2)) {
							ret = true;
							break break_point;
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "新旧明細比較"), e);
		} catch (IllegalAccessException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "新旧明細比較"), e);
		} catch (InvocationTargetException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "新旧明細比較"), e);
		}

		return ret;
	}

	/**
	 * Date -> Calendar変換
	 * @param date
	 * @return
	 */
	public static final Calendar toCalendar(Date date) {
		if(date == null) return null;

		Calendar c = Calendar.getInstance();
		c.setTime(date);

		return c;
	}

	/**
	 * 数値の桁数を取得
	 * @param num 数値
	 * @return 桁数(小数点は桁としてカウントされる)
	 */
	public static int getNumberSize(Number num) {
		return getNumberSize(num, true);
	}

	/**
	 * 数値の桁数を取得
	 * @param num 数値
	 * @param countDot true 小数点を桁としてカウントする false 小数点を桁としてカウントしない
	 * @return 桁数
	 */
	public static int getNumberSize(Number num, boolean countDot) {
		if(num == null) return 0;

		int ret = 0;

		String numStr = num.toString();
		if(numStr.endsWith(".0")) numStr = numStr.substring(0, numStr.length() - 2);

		if(numStr.indexOf("E") >= 0) {
			String[] esp = numStr.split("E");
			ret = Integer.valueOf(esp[1]) + 1;

			if(esp[0].indexOf(".") >= 0) {
				String[] dotsp = esp[0].split("\\.");
				int dotsplen = dotsp[1].length() + (countDot ? 2 : 1);
				if(dotsplen > ret) ret = dotsplen;
			}

		} else {
			ret = numStr.length();
		}

		return ret;
	}

	/**
	 * yyyymm文字列から年度取得
	 * @param period yyyymm文字列
	 * @return
	 */
	public static final int getYear(String period) {
		if(period == null || period.length() != 6) return 0;

		int ret = Integer.valueOf(period.substring(0, 4)).intValue();

		String month = period.substring(4, 6);
		if(month.equals("01") || month.equals("02") || month.equals("03")) {
			ret--;
		}

		return ret;
	}

	/**
	 * オブジェクトの文字列表現を取得
	 * @param obj オブジェクト
	 * @return オブジェクトの文字列表現
	 */
	public static String toString(Object obj) {
		StringBuffer ret = new StringBuffer();

		try {
			Class<?> cls = obj.getClass();

			Field[] fields = cls.getDeclaredFields();
			for (int i = 0; i < fields.length; i++) {
				Field field = fields[i];
				if (!Modifier.isFinal(field.getModifiers())) {
					field.setAccessible(true);
					Object val = field.get(obj);
					if(val != null && !val.toString().equals("")) {
						if(ret.length() != 0) ret.append(",");
						ret.append(field.getName() + ":" + val.toString());
					}
				}
			}

			while (true) {
				cls = cls.getSuperclass();
				if (Object.class.equals(cls)) {
					break;
				}
				Field[] sFields = cls.getDeclaredFields();
				for (int i = 0; i < sFields.length; i++) {
					Field field = sFields[i];
					if (!Modifier.isFinal(field.getModifiers())) {
						field.setAccessible(true);
						Object val = field.get(obj);
						if(val != null && !val.toString().equals("")) {
							if(ret.length() != 0) ret.append(",");
							ret.append(field.getName() + ":" + val.toString());
						}
					}
				}
			}
		} catch (Exception e) {

		}
		return ret.toString();
	}

	/**
	 * str をバイト単位でカット
	 * @param str 文字列
	 * @param cutLen カット長
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String cutStringByte(String str, int cutLen) {
		try {
			return cutStringByte(str, cutLen, Constants.DB_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * str をバイト単位でカット
	 * @param str 文字列
	 * @param cutLen カット長 ※ UTF8変換で2000バイトを超える場合は、指定値に関係なく強制的に切り捨てられる
	 * @param encode 文字エンコード
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String cutStringByte(String str, int cutLen, String encode) throws UnsupportedEncodingException {
		if(str == null) return str;
		if(cutLen <= 0) return str;

		byte[] b = str.getBytes(encode);
		String ret; // リターン

		if(b.length <= cutLen) {
			ret = str; // 切捨て処理必要なし
		} else {
			// 文字の途中で切れた場合は、
			ret = new String(b, 0, cutLen, encode);
			String s = new String(b, 0, cutLen + 1, encode);

			if(ret.length() != 0 && ret.length() == s.length()) {
				int i = 1;
				do {
					ret = new String(b, 0, cutLen - (i++), encode);
				} while(ret.getBytes(encode).length > cutLen);
			}
		}

		// JDBCのVARCHARマックスサイズ（UTF8で2000）を超えた場合は強制切り捨て
		if(ret.getBytes("UTF8").length > 2000) {
			ret = cutStringByte(ret, 2000, "UTF8");
		}

		return ret;
	}

	/**
	 * 末端組織コードから上位組織コード取得
	 * @param sectionCode 末端組織コード
	 * @return
	 */
	public static String getHigherSectionCode(String sectionCode) {
		if(sectionCode == null || sectionCode.length() < 4) return sectionCode;
		return sectionCode.substring(0, 4) + "00";
	}

	/**
	 * JMSメッセージ送信
	 * @param connectionFactory コネクションファクトリ
	 * @param queue キュー
	 * @param param メッセージパラメータ
	 */
	public static void sendJmsMessage(ConnectionFactory connectionFactory, Queue queue, HashMap<String, Object> param) {
		Connection connection = null;
		Session session = null;
		try {
			connection = connectionFactory.createConnection();
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

			ObjectMessage message = session.createObjectMessage();
			message.setObject(param);

			MessageProducer messageProducer = session.createProducer(queue);
			messageProducer.send(message);
		} catch (JMSException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "バックグラウンド処理メッセージ送信"), e);
		} finally {
			try {
				if(session != null) session.close();
				if(connection != null) connection.close();
			} catch (JMSException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "バックグラウンド処理メッセージ送信"), e);
			}
		}
	}

	/**
	 * ファイルにメッセージ書き込み(追加) ※ 画面改行メッセージ特殊文字は置換される
	 * @param str メッセージ
	 * @param file ファイル
	 */
	public static void appendMessageToFile(File file, String str) {
		str = Function.nvl(str, "");
		str = str.replaceAll("\\$\\$\\$\\$\\$", "\n");

		appendStrToFile(file, str);
	}

	/**
	 * ファイルにメッセージ書き込み(追加)
	 * @param str メッセージ
	 * @param file ファイル
	 */
	public static void appendStrToFile(File file, String str) {
		// 出力先取得
		OutputStreamWriter ow = null;
		try {
			ow = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(file, true), 1024), CsvWriterRowHandler.getCharsetName());
			ow.append(str);

		} catch (UnsupportedEncodingException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ファイル書込"), e);
		} catch (FileNotFoundException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ファイル書込"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ファイル書込"), e);
		} finally {
			if(ow != null) {
				try {
					ow.close();
				} catch (IOException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ファイル書込"), e);
				}
			}
		}
	}

	/**
	 * StackTrace文字列取得
	 * @param e 例外
	 * @return StackTrace文字列
	 */
	public static String getStackTraceStr(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);

		return sw.toString();
	}

	/**
	 * 文字列パディング(左)
	 * @param str 対象文字列
	 * @param strLen パディング後の長さ
	 * @param padChar パディング文字
	 * @return
	 */
	public static String paddingLeft(String str, int strLen, char padChar) {
		return paddingStr(str, strLen, padChar, 1);
	}

	/**
	 * 文字列パディング(右)
	 * @param str 対象文字列
	 * @param strLen パディング後の長さ
	 * @param padChar パディング文字
	 * @return
	 */
	public static String paddingRight(String str, int strLen, char padChar) {
		return paddingStr(str, strLen, padChar, 2);
	}

	/*
	 * 文字列を指定文字を使ってpaddingする。
	 *
	 * @param str 対象文字列
	 * @param strLen 文字数
	 * @param padChar paddingに利用する文字
	 * @param leftOrRight 1:leftpadding 2:rightpadding
	 * @return padding後の文字列
	 */
	private static String paddingStr(String str, int strLen, char padChar, int leftOrRight) {
		if(str == null) return null;

		StringBuffer ret = new StringBuffer(str);
		int padLen = strLen - str.length();

		for(int i = 0; i < padLen; i++) {
			if(leftOrRight == 1) {
				ret.insert(0, padChar);
			} else {
				ret.append(padChar);
			}
		}

		return ret.toString();
	}

	/**
	 * ProPlusの販管原価区分をeAssetの区分に変換
	 * @param costType
	 * @return
	 */
	public static String replaceCostTypePpfsToEa(String costType) {
		if(costType == null) return null;

		if(costType.equals(Constants.COST_TYPE_PPFS_GENKA)) {
			costType = Constants.COST_TYPE_GENKA;
		} else if(costType.equals(Constants.COST_TYPE_PPFS_HANKAN)) {
			costType = Constants.COST_TYPE_HANKAN;
		}

		return costType;
	}

	/**
	 * 未検査キャストの警告を回避するためのキャストメソッド
	 * @param src キャストするオブジェクト
	 * @return キャストされたオブジェクト（キャストするクラスは受取側のクラスで自動判定）
	 */
	// @SuppressWarnings("unchecked")
	// public static <T> T autoCast(Object src) {
	// 	T castedObject = (T) src;
	// 	return castedObject;
	// }
}
