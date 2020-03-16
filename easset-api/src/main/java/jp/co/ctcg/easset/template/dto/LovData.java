/*===================================================================
 * ファイル名 : LovData.java
 * 概要説明   : LOVデータ格納用データクラス
 * 				※開発者による変更不可
  * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-01-26 1.0  リヨン       新規
 *=================================================================== */
package jp.co.ctcg.easset.template.dto;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class LovData implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	String code;
	String name;
	String description;
	String value1;

	/**
	 * codeを取得します。
	 * @return code
	 */
	public String getCode() {
	    return code;
	}
	/**
	 * codeを設定します。
	 * @param code code
	 */
	public void setCode(String code) {
	    this.code = code;
	}
	/**
	 * nameを取得します。
	 * @return name
	 */
	public String getName() {
	    return name;
	}
	/**
	 * nameを設定します。
	 * @param name name
	 */
	public void setName(String name) {
	    this.name = name;
	}
	/**
	 * descriptionを取得します。
	 * @return description
	 */
	public String getDescription() {
	    return description;
	}
	/**
	 * descriptionを設定します。
	 * @param description description
	 */
	public void setDescription(String description) {
	    this.description = description;
	}
	/**
	 * value1を取得します。
	 * @return value1
	 */
	public String getValue1() {
		return value1;
	}
	/**
	 * value1
	 * @param value1を設定します。
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}

}
