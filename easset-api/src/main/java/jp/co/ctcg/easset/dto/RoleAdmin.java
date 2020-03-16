/*===================================================================
 * ファイル名 : RoleAdmin.java
 * 概要説明   : ユーザー権限_全社権限検索
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
public class RoleAdmin implements Serializable {
	private static final long serialVersionUID = 1L;

	private String staffCode; // 社員番号
	private String roleCode; // 権限コード S00:ADMIN,A00:資産管理者,A01:資産管理者(総務),A02:資産管理者(財経),A03:資産管理者(情シス)
	private String companyCode; // 権限付与会社コード
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号

	private String staffName;	//	社員名
	private String createStaffName;	//	作成者社員名
	private String updateStaffName;	//	更新者名

	//	ダウンロード用項目
	private String mailAddress;	//	権限者メールアドレス

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		staffCode = (String)input.readObject();
		roleCode = (String)input.readObject();
		companyCode = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();

		staffName = (String)input.readObject();
		createStaffName = (String)input.readObject();
		updateStaffName = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(staffCode);
		output.writeObject(roleCode);
		output.writeObject(companyCode);
		output.writeObject(createDate);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);

		output.writeObject(staffName);
		output.writeObject(createStaffName);
		output.writeObject(updateStaffName);

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
	 * roleCodeを取得します。
	 * @return roleCode
	 */
	public String getRoleCode() {
		return roleCode;
	}

	/**
	 * roleCode
	 * @param roleCodeを設定します。
	 */
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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

}
