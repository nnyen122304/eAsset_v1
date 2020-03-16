/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

@ToString
public class UserCompanyMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId; //
	private String startDate; //
	private String officeCode; //
	private String companyCode; //
	private String divisionCode; //
	private String titleCode; //
	private String tel1; //
	private String tel2; //
	private String fax; //
	private String extension; //
	private String domainId; //
	private String transferCompany; //
	private String postCode; //
	private String jobtypeCode; //
	private String additionalFlg; //
	private String floorInfo; //
	private String endDate; //
	private Long updateUserId; //
	private Date editDate; //

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getOfficeCode() {
		return officeCode;
	}
	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getDivisionCode() {
		return divisionCode;
	}
	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}
	public String getTitleCode() {
		return titleCode;
	}
	public void setTitleCode(String titleCode) {
		this.titleCode = titleCode;
	}
	public String getTel1() {
		return tel1;
	}
	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}
	public String getTel2() {
		return tel2;
	}
	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	public String getDomainId() {
		return domainId;
	}
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}
	public String getTransferCompany() {
		return transferCompany;
	}
	public void setTransferCompany(String transferCompany) {
		this.transferCompany = transferCompany;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}
	public String getJobtypeCode() {
		return jobtypeCode;
	}
	public void setJobtypeCode(String jobtypeCode) {
		this.jobtypeCode = jobtypeCode;
	}
	public String getAdditionalFlg() {
		return additionalFlg;
	}
	public void setAdditionalFlg(String additionalFlg) {
		this.additionalFlg = additionalFlg;
	}
	public String getFloorInfo() {
		return floorInfo;
	}
	public void setFloorInfo(String floorInfo) {
		this.floorInfo = floorInfo;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public Long getUpdateUserId() {
		return updateUserId;
	}
	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}

}
