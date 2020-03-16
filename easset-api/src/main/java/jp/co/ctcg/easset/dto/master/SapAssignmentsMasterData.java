/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 従業員所属マスタデータ
 */
@ToString
public class SapAssignmentsMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String assignmentId;		// アサイメント内部ID
	private String companyCode;			// 会社コード
	private String employeeNumber;		// 社員番号
	private String departmentCode;		// 利益センタ
	private String sectionCode;			// 人事部課コード
	private String primaryFlag;			// 主務兼務フラグ
	private String postCode;			// 役職コード
	private String supervisorCode;		// 上長の社員番号
	private Date effectiveStartDate;	// 開始日付
	private Date effectiveEndDate;		// 終了日付
	private Date updat;					// 最終変更日
	private Date crdat;					// 登録日付
	private String otherFlag;			// 追加フラグ

	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getEmployeeNumber() {
		return employeeNumber;
	}
	public void setEmployeeNumber(String employeeNumber) {
		this.employeeNumber = employeeNumber;
	}
	public String getDepartmentCode() {
		return departmentCode;
	}
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}
	public String getPrimaryFlag() {
		return primaryFlag;
	}
	public void setPrimaryFlag(String primaryFlag) {
		this.primaryFlag = primaryFlag;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getSupervisorCode() {
		return supervisorCode;
	}
	public void setSupervisorCode(String supervisorCode) {
		this.supervisorCode = supervisorCode;
	}
	public Date getEffectiveStartDate() {
		return effectiveStartDate;
	}
	public void setEffectiveStartDate(Date effectiveStartDate) {
		this.effectiveStartDate = effectiveStartDate;
	}
	public Date getEffectiveEndDate() {
		return effectiveEndDate;
	}
	public void setEffectiveEndDate(Date effectiveEndDate) {
		this.effectiveEndDate = effectiveEndDate;
	}
	public Date getUpdat() {
		return updat;
	}
	public void setUpdat(Date updat) {
		this.updat = updat;
	}
	public Date getCrdat() {
		return crdat;
	}
	public void setCrdat(Date crdat) {
		this.crdat = crdat;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public String getAssignmentId() {
		return assignmentId;
	}
	public void setAssignmentId(String assignmentId) {
		this.assignmentId = assignmentId;
	}
	public String getOtherFlag() {
		return otherFlag;
	}
	public void setOtherFlag(String otherFlag) {
		this.otherFlag = otherFlag;
	}
}
