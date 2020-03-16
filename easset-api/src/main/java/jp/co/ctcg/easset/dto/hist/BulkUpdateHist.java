/*===================================================================
 * ファイル名 : BulkUpdateHist.java
 * 概要説明   : 一括更新履歴データクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-4-26 1.0  リヨン 崔     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.hist;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

@ToString
public class BulkUpdateHist implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long logId; // ログID
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private String createStaffName; // 作成者名
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String function; // 機能 ASSET：情報機器等一括更新、LICENSE:ライセンス一括更新
	private String execStatus; // 処理ステータス ファイル読込中、更新中、終了
	private String fileId; // 処理ファイルID
	private Integer execCount; // 処理件数
	private Integer successCount; // 処理成功件数
	private Integer failureCount; // 処理失敗件数
	private String updateColumn; // 更新項目
	private String updateColumnName; // 更新項目名
	public Long getLogId() {
		return logId;
	}
	public void setLogId(Long logId) {
		this.logId = logId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateStaffCode() {
		return createStaffCode;
	}
	public void setCreateStaffCode(String createStaffCode) {
		this.createStaffCode = createStaffCode;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public String getUpdateStaffCode() {
		return updateStaffCode;
	}
	public void setUpdateStaffCode(String updateStaffCode) {
		this.updateStaffCode = updateStaffCode;
	}
	public String getFunction() {
		return function;
	}
	public void setFunction(String function) {
		this.function = function;
	}
	public String getExecStatus() {
		return execStatus;
	}
	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}
	public Integer getExecCount() {
		return execCount;
	}
	public void setExecCount(Integer execCount) {
		this.execCount = execCount;
	}
	public Integer getSuccessCount() {
		return successCount;
	}
	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}
	public Integer getFailureCount() {
		return failureCount;
	}
	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}
	public String getUpdateColumn() {
		return updateColumn;
	}
	public void setUpdateColumn(String updateColumn) {
		this.updateColumn = updateColumn;
	}
	public String getUpdateColumnName() {
		return updateColumnName;
	}
	public void setUpdateColumnName(String updateColumnName) {
		this.updateColumnName = updateColumnName;
	}
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	/**
	 * @return the createStaffName
	 */
	public String getCreateStaffName() {
		return createStaffName;
	}
	/**
	 * @param createStaffName the createStaffName to set
	 */
	public void setCreateStaffName(String createStaffName) {
		this.createStaffName = createStaffName;
	}



}
