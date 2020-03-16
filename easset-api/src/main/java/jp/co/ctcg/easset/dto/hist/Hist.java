/*===================================================================
 * ファイル名 : Hist.java
 * 概要説明   : 更新履歴データクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-26 1.0  リヨン 申     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.hist;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

@ToString
public class Hist implements Serializable {
	private static final long serialVersionUID = 1L;

	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String updateStaffName; // 更新者名
	private String operation; // 操作
	private String apStatus; // ステータス
	private String apStatusName; // ステータス名
	private String updateColumnName; // 更新項目名
	private String updateComment; // コメント
	private Integer version; // バージョン
	private String key; // キー

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the updateStaffCode
	 */
	public String getUpdateStaffCode() {
		return updateStaffCode;
	}
	/**
	 * @param updateStaffCode the updateStaffCode to set
	 */
	public void setUpdateStaffCode(String updateStaffCode) {
		this.updateStaffCode = updateStaffCode;
	}
	/**
	 * @return the updateStaffName
	 */
	public String getUpdateStaffName() {
		return updateStaffName;
	}
	/**
	 * @param updateStaffName the updateStaffName to set
	 */
	public void setUpdateStaffName(String updateStaffName) {
		this.updateStaffName = updateStaffName;
	}
	/**
	 * @return the operation
	 */
	public String getOperation() {
		return operation;
	}
	/**
	 * @param operation the operation to set
	 */
	public void setOperation(String operation) {
		this.operation = operation;
	}
	/**
	 * @return the apStatus
	 */
	public String getApStatus() {
		return apStatus;
	}
	/**
	 * @param apStatus the apStatus to set
	 */
	public void setApStatus(String apStatus) {
		this.apStatus = apStatus;
	}
	/**
	 * @return the apStatusName
	 */
	public String getApStatusName() {
		return apStatusName;
	}
	/**
	 * @param apStatusName the apStatusName to set
	 */
	public void setApStatusName(String apStatusName) {
		this.apStatusName = apStatusName;
	}
	/**
	 * @return the updateColumnName
	 */
	public String getUpdateColumnName() {
		return updateColumnName;
	}
	/**
	 * @param updateColumnName the updateColumnName to set
	 */
	public void setUpdateColumnName(String updateColumnName) {
		this.updateColumnName = updateColumnName;
	}
	/**
	 * @return the updateComment
	 */
	public String getUpdateComment() {
		return updateComment;
	}
	/**
	 * @param updateComment the updateComment to set
	 */
	public void setUpdateComment(String updateComment) {
		this.updateComment = updateComment;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}


}
