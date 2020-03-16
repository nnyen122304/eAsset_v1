/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 部署マスタデータ
 */
@ToString
public class DivisionMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String companyCode; //
	private String divisionCode; //
	private String divisionKanji; //
	private String divisionKana; //
	private String divisionEng; //
	private Integer disporder; //
	private Date editDate; //

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
	public String getDivisionKanji() {
		return divisionKanji;
	}
	public void setDivisionKanji(String divisionKanji) {
		this.divisionKanji = divisionKanji;
	}
	public String getDivisionKana() {
		return divisionKana;
	}
	public void setDivisionKana(String divisionKana) {
		this.divisionKana = divisionKana;
	}
	public String getDivisionEng() {
		return divisionEng;
	}
	public void setDivisionEng(String divisionEng) {
		this.divisionEng = divisionEng;
	}
	public Integer getDisporder() {
		return disporder;
	}
	public void setDisporder(Integer disporder) {
		this.disporder = disporder;
	}
	public Date getEditDate() {
		return editDate;
	}
	public void setEditDate(Date editDate) {
		this.editDate = editDate;
	}



}
