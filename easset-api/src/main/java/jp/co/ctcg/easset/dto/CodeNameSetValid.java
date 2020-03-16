/*===================================================================
 * ファイル名 : CodeNameSetValid.java
 * 概要説明   : ユーザー情報
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-06 1.0  リヨン 崔　  新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

@ToString
public class CodeNameSetValid implements Serializable {
	private static final long serialVersionUID = 1L;

	private String categoryCode; // カテゴリコード
	private String validTable; // バリデーションテーブル
	private String validColumn; // バリデーションカラム
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String validTableName; // バリデーションテーブル名
	private String validColumnName; // バリデーションカラム名

	/**
	 * categoryCodeを取得します。
	 * @return categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * categoryCode
	 * @param categoryCodeを設定します。
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * validTableを取得します。
	 * @return validTable
	 */
	public String getValidTable() {
		return validTable;
	}
	/**
	 * validTable
	 * @param validTableを設定します。
	 */
	public void setValidTable(String validTable) {
		this.validTable = validTable;
	}
	/**
	 * validColumnを取得します。
	 * @return validColumn
	 */
	public String getValidColumn() {
		return validColumn;
	}
	/**
	 * validColumn
	 * @param validColumnを設定します。
	 */
	public void setValidColumn(String validColumn) {
		this.validColumn = validColumn;
	}
	/**
	 * createDateを取得します。
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * createDate
	 * @param createDateを設定します。
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * createStaffCodeを取得します。
	 * @return createStaffCode
	 */
	public String getCreateStaffCode() {
		return createStaffCode;
	}
	/**
	 * createStaffCode
	 * @param createStaffCodeを設定します。
	 */
	public void setCreateStaffCode(String createStaffCode) {
		this.createStaffCode = createStaffCode;
	}
	/**
	 * updateDateを取得します。
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * updateDate
	 * @param updateDateを設定します。
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * updateStaffCodeを取得します。
	 * @return updateStaffCode
	 */
	public String getUpdateStaffCode() {
		return updateStaffCode;
	}
	/**
	 * updateStaffCode
	 * @param updateStaffCodeを設定します。
	 */
	public void setUpdateStaffCode(String updateStaffCode) {
		this.updateStaffCode = updateStaffCode;
	}
	/**
	 * validTableNameを取得します。
	 * @return validTableName
	 */
	public String getValidTableName() {
		return validTableName;
	}
	/**
	 * validTableName
	 * @param validTableNameを設定します。
	 */
	public void setValidTableName(String validTableName) {
		this.validTableName = validTableName;
	}
	/**
	 * validColumnNameを取得します。
	 * @return validColumnName
	 */
	public String getValidColumnName() {
		return validColumnName;
	}
	/**
	 * validColumnName
	 * @param validColumnNameを設定します。
	 */
	public void setValidColumnName(String validColumnName) {
		this.validColumnName = validColumnName;
	}



}
