/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 経費負担部課マスタデータ
 */
@ToString
public class SapExpDeptMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String companyCD;		// 会社コード
	private String deptCD;			// 経費負担部課コード
	private String deptName;		// 経費負担部課名称
	private String summaryFlag;		// 集計フラグ
	private String costFlag;		// 原価販管フラグ
	private Date startDate;			// 有効開始日
	private Date endDate;			// 有効終了日
	private String enabledFlag;		// 有効フラグ
	private String sortKey;			// ソートキー
	private Date lastUpdateDate;	// 最終更新日
	private String sectionFlag;		// 部課口フラグ
	private String categoryCode;	// 組織階層区分

	public String getCompanyCD() {
		return companyCD;
	}
	public void setCompanyCD(String companyCD) {
		this.companyCD = companyCD;
	}
	public String getDeptCD() {
		return deptCD;
	}
	public void setDeptCD(String deptCD) {
		this.deptCD = deptCD;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getSummaryFlag() {
		return summaryFlag;
	}
	public void setSummaryFlag(String summaryFlag) {
		this.summaryFlag = summaryFlag;
	}
	public String getCostFlag() {
		return costFlag;
	}
	public void setCostFlag(String costFlag) {
		this.costFlag = costFlag;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public String getSortKey() {
		return sortKey;
	}
	public void setSortKey(String sortKey) {
		this.sortKey = sortKey;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	public String getSectionFlag() {
		return sectionFlag;
	}
	public void setSectionFlag(String sectionFlag) {
		this.sectionFlag = sectionFlag;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}

}
