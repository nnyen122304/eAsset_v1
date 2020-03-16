/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;

import lombok.ToString;

/**
 * 部課マスタデータ
 */
@ToString
public class NewDivisionMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String companyCode; //
	private String divisionCode; //
	private String parentDivisionCode; //
	private String divisionKanji; //
	private String viewFlg; //

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
	public String getParentDivisionCode() {
		return parentDivisionCode;
	}
	public void setParentDivisionCode(String parentDivisionCode) {
		this.parentDivisionCode = parentDivisionCode;
	}
	public String getDivisionKanji() {
		return divisionKanji;
	}
	public void setDivisionKanji(String divisionKanji) {
		this.divisionKanji = divisionKanji;
	}
	public String getViewFlg() {
		return viewFlg;
	}
	public void setViewFlg(String viewFlg) {
		this.viewFlg = viewFlg;
	}


}
