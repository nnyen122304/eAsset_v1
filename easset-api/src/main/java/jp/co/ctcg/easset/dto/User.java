/*===================================================================
 * ファイル名 : User.java
 * 概要説明   : ユーザー情報
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-06 1.0  リヨン 崔　  新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import jp.co.ctcg.easset.rest.MappingUtils;
import lombok.ToString;

@ToString
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private String account;			// アカウント
	private String staffCode;		// 社員番号
	private String name;			// 氏名
	private String companyCode;		// 会社コード
	private String companyName;		// 会社名称
	private String sectionCode;		// 所属部署コード
	private String sectionName;		// 所属部署名称
	private String tel1;			// 電話番号
	private String officeCode;		// オフィスコード
	private String officeName;		// オフィス名
	private String mailAddress;		// メールアドレス
	private String fax;				// FAX番号
	private String address;			// 住所
	private String tel;				// 電話番号(内線なし)

	private String lovDispName;		// 社員検索表示所属部署名称

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		account = (String)input.readObject();
		staffCode = (String)input.readObject();
		name = (String)input.readObject();
		companyCode = (String)input.readObject();
		companyName = (String)input.readObject();
		sectionCode = (String)input.readObject();
		sectionName = (String)input.readObject();
		tel1 = (String)input.readObject();
		officeCode = (String)input.readObject();
		officeName = (String)input.readObject();
		mailAddress = (String)input.readObject();
		fax = (String)input.readObject();
		address = (String)input.readObject();
		tel = (String)input.readObject();

		lovDispName = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(account);
		output.writeObject(staffCode);
		output.writeObject(name);
		output.writeObject(companyCode);
		output.writeObject(companyName);
		output.writeObject(sectionCode);
		output.writeObject(sectionName);
		output.writeObject(tel1);
		output.writeObject(officeCode);
		output.writeObject(officeName);
		output.writeObject(mailAddress);
		output.writeObject(fax);
		output.writeObject(address);
		output.writeObject(tel);

		output.writeObject(lovDispName);
	}

	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSectionCode() {
		return sectionCode;
	}
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	public String getSectionName() {
		return sectionName;
	}
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	public String getLovDispName() {
		return lovDispName;
	}
	public void setLovDispName(String lovDispName) {
		this.lovDispName = lovDispName;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getOfficeCode() {
		return officeCode;
	}

	public void setOfficeCode(String officeCode) {
		this.officeCode = officeCode;
	}

	public String getOfficeName() {
		return officeName;
	}

	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * @return the mailAddress
	 */
	public String getMailAddress() {
		return mailAddress;
	}

	/**
	 * @param mailAddress the mailAddress to set
	 */
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}

	/**
	 * faxを取得します。
	 * @return fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * fax
	 * @param faxを設定します。
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * addressを取得します。
	 * @return address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * address
	 * @param addressを設定します。
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * telを取得します。
	 * @return tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * tel
	 * @param telを設定します。
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/*
	* 文字列からクラスに変換メソッド
	* @param json 変換json文字列
	* @return User.class 該当クラス
	*/
	public static User fromString(String json) {
		return MappingUtils.fromJson(json, User.class);
	}
}
