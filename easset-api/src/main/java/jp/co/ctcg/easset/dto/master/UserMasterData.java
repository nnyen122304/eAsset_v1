/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

@ToString
public class UserMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long userId; //
	private String staffCode; //
	private String nameKanji; //
	private String nameKana; //
	private String nameEng; //
	private String mailAddress; //
	private String nickName; //
	private String account; //
	private String fullName; //
	private String password; //
	private String retireplanDay; //
	private String retiredDay; //
	private String companyCode; //
	private String staffAttribute; //
	private String registrationDay; //
	private String teldivCode; //
	private String manualdivCode; //
	private String message; //
	private Long updateUserId; //
	private Date editDate; //
	private String gaijiFlg; //
	private String maildivCode; //
	private String doubleStaffCode; //
	private String question; //
	private String answer; //
	private String passphrase1; //
	private String passphrase2; //
	private String autoForward; //
	private String eipaccess; //
	private String webMail; //
	private String telSearch; //
	private String telProtectFlg; //
	private String accessFlg; //

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getStaffCode() {
		return staffCode;
	}
	public void setStaffCode(String staffCode) {
		this.staffCode = staffCode;
	}
	public String getNameKanji() {
		return nameKanji;
	}
	public void setNameKanji(String nameKanji) {
		this.nameKanji = nameKanji;
	}
	public String getNameKana() {
		return nameKana;
	}
	public void setNameKana(String nameKana) {
		this.nameKana = nameKana;
	}
	public String getNameEng() {
		return nameEng;
	}
	public void setNameEng(String nameEng) {
		this.nameEng = nameEng;
	}
	public String getMailAddress() {
		return mailAddress;
	}
	public void setMailAddress(String mailAddress) {
		this.mailAddress = mailAddress;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAccount() {
		return account;
	}
	public void setAccount(String account) {
		this.account = account;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRetireplanDay() {
		return retireplanDay;
	}
	public void setRetireplanDay(String retireplanDay) {
		this.retireplanDay = retireplanDay;
	}
	public String getRetiredDay() {
		return retiredDay;
	}
	public void setRetiredDay(String retiredDay) {
		this.retiredDay = retiredDay;
	}
	public String getCompanyCode() {
		return companyCode;
	}
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	public String getStaffAttribute() {
		return staffAttribute;
	}
	public void setStaffAttribute(String staffAttribute) {
		this.staffAttribute = staffAttribute;
	}
	public String getRegistrationDay() {
		return registrationDay;
	}
	public void setRegistrationDay(String registrationDay) {
		this.registrationDay = registrationDay;
	}
	public String getTeldivCode() {
		return teldivCode;
	}
	public void setTeldivCode(String teldivCode) {
		this.teldivCode = teldivCode;
	}
	public String getManualdivCode() {
		return manualdivCode;
	}
	public void setManualdivCode(String manualdivCode) {
		this.manualdivCode = manualdivCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
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
	public String getGaijiFlg() {
		return gaijiFlg;
	}
	public void setGaijiFlg(String gaijiFlg) {
		this.gaijiFlg = gaijiFlg;
	}
	public String getMaildivCode() {
		return maildivCode;
	}
	public void setMaildivCode(String maildivCode) {
		this.maildivCode = maildivCode;
	}
	public String getDoubleStaffCode() {
		return doubleStaffCode;
	}
	public void setDoubleStaffCode(String doubleStaffCode) {
		this.doubleStaffCode = doubleStaffCode;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getPassphrase1() {
		return passphrase1;
	}
	public void setPassphrase1(String passphrase1) {
		this.passphrase1 = passphrase1;
	}
	public String getPassphrase2() {
		return passphrase2;
	}
	public void setPassphrase2(String passphrase2) {
		this.passphrase2 = passphrase2;
	}
	public String getAutoForward() {
		return autoForward;
	}
	public void setAutoForward(String autoForward) {
		this.autoForward = autoForward;
	}
	public String getEipaccess() {
		return eipaccess;
	}
	public void setEipaccess(String eipaccess) {
		this.eipaccess = eipaccess;
	}
	public String getWebMail() {
		return webMail;
	}
	public void setWebMail(String webMail) {
		this.webMail = webMail;
	}
	public String getTelSearch() {
		return telSearch;
	}
	public void setTelSearch(String telSearch) {
		this.telSearch = telSearch;
	}
	public String getTelProtectFlg() {
		return telProtectFlg;
	}
	public void setTelProtectFlg(String telProtectFlg) {
		this.telProtectFlg = telProtectFlg;
	}
	public String getAccessFlg() {
		return accessFlg;
	}
	public void setAccessFlg(String accessFlg) {
		this.accessFlg = accessFlg;
	}


}
