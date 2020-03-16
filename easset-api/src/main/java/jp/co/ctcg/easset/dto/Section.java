/*===================================================================
 * ファイル名 : Section.java
 * 概要説明   : 人事部課データクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-26 1.0  リヨン 申     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class Section implements Serializable {
	private static final long serialVersionUID = 1L;

	private String companyCode;			// 会社コード
	private String sectionCode;		// 部署コード
	private String parentSectionCode;	// 親部署コード
	private String sectionName;		// 部署名
	private String sectionShortName;		// 部署名(短縮)
	private Integer sectionYear;		// 部初年度
	private String costFlag;			//	販売管理費/原価区分 1:原価、2:販売管理費
	private String companyName;		//	会社名
	private String companyOfficialName;	//	会社名称

	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
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
	 * @return the parentSectionCode
	 */
	public String getParentSectionCode() {
		return parentSectionCode;
	}
	/**
	 * @param parentSectionCode the parentSectionCode to set
	 */
	public void setParentSectionCode(String parentSectionCode) {
		this.parentSectionCode = parentSectionCode;
	}
	/**
	 * @return the sectionYear
	 */
	public Integer getSectionYear() {
		return sectionYear;
	}
	/**
	 * @param sectionYear the sectionYear to set
	 */
	public void setSectionYear(Integer sectionYear) {
		this.sectionYear = sectionYear;
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
	/**
	 * @return the sectionShortName
	 */
	public String getSectionShortName() {
		return sectionShortName;
	}
	/**
	 * @param sectionShortName the sectionShortName to set
	 */
	public void setSectionShortName(String sectionShortName) {
		this.sectionShortName = sectionShortName;
	}
	/**
	 * costFlagを取得します。
	 * @return costFlag
	 */
	public String getCostFlag() {
		return costFlag;
	}
	/**
	 * costFlag
	 * @param costFlagを設定します。
	 */
	public void setCostFlag(String costFlag) {
		this.costFlag = costFlag;
	}
	/**
	 * companyNameを取得します。
	 * @return companyName
	 */
	public String getCompanyName() {
		return companyName;
	}
	/**
	 * companyName
	 * @param companyNameを設定します。
	 */
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	/**
	 * companyOfficialNameを取得します。
	 * @return companyOfficialName
	 */
	public String getCompanyOfficialName() {
		return companyOfficialName;
	}
	/**
	 * companyOfficialName
	 * @param companyOfficialNameを設定します。
	 */
	public void setCompanyOfficialName(String companyOfficialName) {
		this.companyOfficialName = companyOfficialName;
	}

}
