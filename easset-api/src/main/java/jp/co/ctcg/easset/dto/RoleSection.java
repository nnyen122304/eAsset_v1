/*===================================================================
 * ファイル名 : RoleSection.java
 * 概要説明   : ユーザー権限_部署(資産管理担当者)権限検索
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-01-10 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

@ToString
public class RoleSection implements Serializable {
	private static final long serialVersionUID = 1L;

	private String staffCode; // 社員番号
	private String companyCode; // 権限付与会社コード
	private String sectionCode; // 権限付与部署コード
	private Integer sectionYear; // 権限付与部署年度
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号 0:公開する,1:公開しない
	private String publicFlag; // 公開フラグ
	private String publicComment; // 公開コメント

	private String staffName;	//	社員名
	private String createStaffName;	//	作成者社員名
	private String updateStaffName;	//	更新者社員名
	private String publicFlagName;	//	公開フラグ名
	private String sectionName;	//	権限部署名

	private String roleCode; // 権限コード
	private String domainId;	//	ドメインID
	private String retiredDay;	//	退職日

	//	ダウンロード用項目
	private String mailAddress;	//	権限者メールアドレス

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		staffCode = (String)input.readObject();
		companyCode = (String)input.readObject();
		sectionCode = (String)input.readObject();
		sectionYear = (Integer)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		publicFlag = (String)input.readObject();
		publicComment = (String)input.readObject();

		staffName = (String)input.readObject();
		createStaffName = (String)input.readObject();
		updateStaffName = (String)input.readObject();
		publicFlagName = (String)input.readObject();
		sectionName = (String)input.readObject();

		roleCode = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(staffCode);
		output.writeObject(companyCode);
		output.writeObject(sectionCode);
		output.writeObject(sectionYear);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(publicFlag);
		output.writeObject(publicComment);

		output.writeObject(staffName);
		output.writeObject(createStaffName);
		output.writeObject(updateStaffName);
		output.writeObject(publicFlagName);
		output.writeObject(sectionName);

		output.writeObject(roleCode);

	}

	/**
	 * staffCodeを取得します。
	 * @return staffCode
	 */
	public String getStaffCode() {
		return staffCode;
	}

	/**
	 * staffCode
	 * @param staffCodeを設定します。
	 */
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}

	/**
	 * companyCodeを取得します。
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode
	 * @param companyCodeを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * sectionCodeを取得します。
	 * @return sectionCode
	 */
	public String getSectionCode() {
		return sectionCode;
	}

	/**
	 * sectionCode
	 * @param sectionCodeを設定します。
	 */
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	/**
	 * sectionYearを取得します。
	 * @return sectionYear
	 */
	public Integer getSectionYear() {
		return sectionYear;
	}

	/**
	 * sectionYear
	 * @param sectionYearを設定します。
	 */
	public void setSectionYear(Integer sectionYear) {
		this.sectionYear = sectionYear;
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
	 * publicFlagを取得します。
	 * @return publicFlag
	 */
	public String getPublicFlag() {
		return publicFlag;
	}

	/**
	 * publicFlag
	 * @param publicFlagを設定します。
	 */
	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}

	/**
	 * publicCommentを取得します。
	 * @return publicComment
	 */
	public String getPublicComment() {
		return publicComment;
	}

	/**
	 * publicComment
	 * @param publicCommentを設定します。
	 */
	public void setPublicComment(String publicComment) {
		this.publicComment = publicComment;
	}

	/**
	 * createStaffNameを取得します。
	 * @return createStaffName
	 */
	public String getCreateStaffName() {
		return createStaffName;
	}

	/**
	 * createStaffName
	 * @param createStaffNameを設定します。
	 */
	public void setCreateStaffName(String createStaffName) {
		this.createStaffName = createStaffName;
	}

	/**
	 * updateStaffNameを取得します。
	 * @return updateStaffName
	 */
	public String getUpdateStaffName() {
		return updateStaffName;
	}

	/**
	 * updateStaffName
	 * @param updateStaffNameを設定します。
	 */
	public void setUpdateStaffName(String updateStaffName) {
		this.updateStaffName = updateStaffName;
	}

	/**
	 * publicFlagNameを取得します。
	 * @return publicFlagName
	 */
	public String getPublicFlagName() {
		return publicFlagName;
	}

	/**
	 * publicFlagName
	 * @param publicFlagNameを設定します。
	 */
	public void setPublicFlagName(String publicFlagName) {
		this.publicFlagName = publicFlagName;
	}

	/**
	 * sectionNameを取得します。
	 * @return sectionName
	 */
	public String getSectionName() {
		return sectionName;
	}

	/**
	 * sectionName
	 * @param sectionNameを設定します。
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	/**
	 * staffNameを取得します。
	 * @return staffName
	 */
	public String getStaffName() {
		return staffName;
	}

	/**
	 * staffName
	 * @param staffNameを設定します。
	 */
	public void setStaffName(String staffName) {
		this.staffName = staffName;
	}

	/**
	 * mailAddressを取得します。
	 * @return mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * mailAddress
	 * @param mailAddressを設定します。
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * @return the roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * @param roleCode the roleCode to set
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	/**
	 * domainIdを取得します。
	 * @return domainId
	 */
	public String getDomainId() {
		return domainId;
	}

	/**
	 * domainId
	 * @param domainIdを設定します。
	 */
	public void setDomainId(String domainId) {
		this.domainId = domainId;
	}

	/**
	 * retiredDayを取得します。
	 * @return retiredDay
	 */
	public String getRetiredDay() {
		return retiredDay;
	}

	/**
	 * retiredDay
	 * @param retiredDayを設定します。
	 */
	public void setRetiredDay(String retiredDay) {
		this.retiredDay = retiredDay;
	}

}
