/*===================================================================
 * ファイル名 : CostSection.java
 * 概要説明   : 部課
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-26 1.0  リヨン 崔　  新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class CostSection implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private String sectionCode; // 部課コード
	private String sectionName; // 部課名

	/**
	 * @return the sectionCode
	 */
	public String getSectionCode() {
		return sectionCode;
	}
	/**
	 * @param sectionCode the sectionCode to set
	 */
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	/**
	 * @return the sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}
	/**
	 * @param sectionName the sectionName to set
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
}
