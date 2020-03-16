/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 会社マスタデータ
 */
@ToString
public class CompanyMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String officeCode; //
	private String companyCode; //
	private String officeNameKanji; //
	private String officeNameKana; //
	private String officeNameEng; //
	private String companyNameKanji; //
	private String companyPopKanji; //
	private String companyNameKana; //
	private String companyPopKana; //
	private String companyNameEng; //
	private String companyPopEng; //
	private String zipCode; //
	private String addressKanji1; //
	private String addressKanji2; //
	private String addressKanji3; //
	private String addressKana1; //
	private String addressKana2; //
	private String addressKana3; //
	private String addressEng1; //
	private String addressEng2; //
	private String addressEng3; //
	private String telRepresentative; //
	private String representativeKanji; //
	private String representativeKana; //
	private String representativeEng; //
	private String useFlg; //
	private Date editDate; //

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
	public String getOfficeNameKanji() {
		return officeNameKanji;
	}
	public void setOfficeNameKanji(String officeNameKanji) {
		this.officeNameKanji = officeNameKanji;
	}
	public String getOfficeNameKana() {
		return officeNameKana;
	}
	public void setOfficeNameKana(String officeNameKana) {
		this.officeNameKana = officeNameKana;
	}
	public String getOfficeNameEng() {
		return officeNameEng;
	}
	public void setOfficeNameEng(String officeNameEng) {
		this.officeNameEng = officeNameEng;
	}
	public String getCompanyNameKanji() {
		return companyNameKanji;
	}
	public void setCompanyNameKanji(String companyNameKanji) {
		this.companyNameKanji = companyNameKanji;
	}
	public String getCompanyPopKanji() {
		return companyPopKanji;
	}
	public void setCompanyPopKanji(String companyPopKanji) {
		this.companyPopKanji = companyPopKanji;
	}
	public String getCompanyNameKana() {
		return companyNameKana;
	}
	public void setCompanyNameKana(String companyNameKana) {
		this.companyNameKana = companyNameKana;
	}
	public String getCompanyPopKana() {
		return companyPopKana;
	}
	public void setCompanyPopKana(String companyPopKana) {
		this.companyPopKana = companyPopKana;
	}
	public String getCompanyNameEng() {
		return companyNameEng;
	}
	public void setCompanyNameEng(String companyNameEng) {
		this.companyNameEng = companyNameEng;
	}
	public String getCompanyPopEng() {
		return companyPopEng;
	}
	public void setCompanyPopEng(String companyPopEng) {
		this.companyPopEng = companyPopEng;
	}
	public String getZipCode() {
		return zipCode;
	}
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	public String getAddressKanji1() {
		return addressKanji1;
	}
	public void setAddressKanji1(String addressKanji1) {
		this.addressKanji1 = addressKanji1;
	}
	public String getAddressKanji2() {
		return addressKanji2;
	}
	public void setAddressKanji2(String addressKanji2) {
		this.addressKanji2 = addressKanji2;
	}
	public String getAddressKanji3() {
		return addressKanji3;
	}
	public void setAddressKanji3(String addressKanji3) {
		this.addressKanji3 = addressKanji3;
	}
	public String getAddressKana1() {
		return addressKana1;
	}
	public void setAddressKana1(String addressKana1) {
		this.addressKana1 = addressKana1;
	}
	public String getAddressKana2() {
		return addressKana2;
	}
	public void setAddressKana2(String addressKana2) {
		this.addressKana2 = addressKana2;
	}
	public String getAddressKana3() {
		return addressKana3;
	}
	public void setAddressKana3(String addressKana3) {
		this.addressKana3 = addressKana3;
	}
	public String getAddressEng1() {
		return addressEng1;
	}
	public void setAddressEng1(String addressEng1) {
		this.addressEng1 = addressEng1;
	}
	public String getAddressEng2() {
		return addressEng2;
	}
	public void setAddressEng2(String addressEng2) {
		this.addressEng2 = addressEng2;
	}
	public String getAddressEng3() {
		return addressEng3;
	}
	public void setAddressEng3(String addressEng3) {
		this.addressEng3 = addressEng3;
	}
	public String getTelRepresentative() {
		return telRepresentative;
	}
	public void setTelRepresentative(String telRepresentative) {
		this.telRepresentative = telRepresentative;
	}
	public String getRepresentativeKanji() {
		return representativeKanji;
	}
	public void setRepresentativeKanji(String representativeKanji) {
		this.representativeKanji = representativeKanji;
	}
	public String getRepresentativeKana() {
		return representativeKana;
	}
	public void setRepresentativeKana(String representativeKana) {
		this.representativeKana = representativeKana;
	}
	public String getRepresentativeEng() {
		return representativeEng;
	}
	public void setRepresentativeEng(String representativeEng) {
		this.representativeEng = representativeEng;
	}
	public String getUseFlg() {
		return useFlg;
	}
	public void setUseFlg(String useFlg) {
		this.useFlg = useFlg;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}



}
