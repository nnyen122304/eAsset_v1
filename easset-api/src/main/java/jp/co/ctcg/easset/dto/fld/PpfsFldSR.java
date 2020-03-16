/*===================================================================
 * ファイル名 : PpfsFldSR.java
 * 概要説明   : 固定資産(照会・管理項目修正)検索結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-14 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.fld;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;


@ToString(callSuper = true)
public class PpfsFldSR extends PpfsFld {
	private static final long serialVersionUID = 1L;

	private String kadoymdL;	//	稼動(予定)日/除売却日
	private String slipAstPtjCode;	//	伝票番号/資産番号
	private String purCompanyNameL;	//	仕入先(検索結果用)
	private String furikaeNum;	//	振替資産番号

	private String keijoymdL;	//	計上日

	private String holStaffCode;	//	無形管理担当者コード
	private String holStaffName;	//	無形管理担当者名

	private String holCompanyCode;	//	無形保有会社コード
	private String holSectionCode;	//	無形保有部署コード
	private Integer holSectionYear;	//	無形保有部署年度
	private String holSectionName;	//	無形保有部署
	private String holSectionNameL;	//	保有部署（一覧表示用）


	// ダウンロードのみで使用
	private String holCompanyName;	//	無形保有会社名
	private String dscAttribute1; // 管理項目1
	private String dscAttribute2; // 管理項目2
	private String dscAttribute3; // 管理項目3
	private String dscAttribute4; // 管理項目4
	private String dscAttribute5; // 管理項目5
	private String dscAttribute6; // 管理項目6
	private String dscAttribute7; // 管理項目7
	private String dscAttribute8; // 管理項目8
	private String dscAttribute9; // 管理項目9
	private String dscAttribute10; // 管理項目10
	private String dscAttribute11; // 管理項目11
	private String dscAttribute12; // 管理項目12
	private String dscAttribute13; // 管理項目13
	private String dscAttribute14; // 管理項目14
	private String dscAttribute15; // 管理項目15
	private String dscAttribute16; // 管理項目16
	private String dscAttribute17; // 管理項目17
	private String dscAttribute18; // 管理項目18
	private String dscAttribute19; // 管理項目19
	private String dscAttribute20; // 管理項目20

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		kadoymdL = (String)input.readObject();
		slipAstPtjCode = (String)input.readObject();
		purCompanyNameL = (String)input.readObject();
		furikaeNum = (String)input.readObject();
		keijoymdL = (String)input.readObject();

		holStaffCode = (String)input.readObject();
		holStaffName = (String)input.readObject();

		holCompanyCode = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holSectionName = (String)input.readObject();
		holSectionNameL = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(kadoymdL);
		output.writeObject(slipAstPtjCode);
		output.writeObject(purCompanyNameL);
		output.writeObject(furikaeNum);
		output.writeObject(keijoymdL);

		output.writeObject(holStaffCode);
		output.writeObject(holStaffName);

		output.writeObject(holCompanyCode);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(holSectionName);
		output.writeObject(holSectionNameL);

	}

	/**
	 * kadoymdLを取得します。
	 * @return kadoymdL
	 */
	public String getKadoymdL() {
		return kadoymdL;
	}

	/**
	 * kadoymdL
	 * @param kadoymdLを設定します。
	 */
	public void setKadoymdL(String kadoymdL) {
		this.kadoymdL = kadoymdL;
	}

	/**
	 * slipAstPtjCodeを取得します。
	 * @return slipAstPtjCode
	 */
	public String getSlipAstPtjCode() {
		return slipAstPtjCode;
	}

	/**
	 * slipAstPtjCode
	 * @param slipAstPtjCodeを設定します。
	 */
	public void setSlipAstPtjCode(String slipAstPtjCode) {
		this.slipAstPtjCode = slipAstPtjCode;
	}

	/**
	 * purCompanyNameLを取得します。
	 * @return purCompanyNameL
	 */
	public String getPurCompanyNameL() {
		return purCompanyNameL;
	}

	/**
	 * purCompanyNameL
	 * @param purCompanyNameLを設定します。
	 */
	public void setPurCompanyNameL(String purCompanyNameL) {
		this.purCompanyNameL = purCompanyNameL;
	}

	/**
	 * furikaeNumを取得します。
	 * @return furikaeNum
	 */
	public String getFurikaeNum() {
		return furikaeNum;
	}

	/**
	 * furikaeNum
	 * @param furikaeNumを設定します。
	 */
	public void setFurikaeNum(String furikaeNum) {
		this.furikaeNum = furikaeNum;
	}

	/**
	 * keijoymdLを取得します。
	 * @return keijoymdL
	 */
	public String getKeijoymdL() {
		return keijoymdL;
	}

	/**
	 * keijoymdL
	 * @param keijoymdLを設定します。
	 */
	public void setKeijoymdL(String keijoymdL) {
		this.keijoymdL = keijoymdL;
	}


	/**
	 * holStaffCodeを取得します。
	 * @return holStaffCode
	 */
	public String getHolStaffCode() {
		return holStaffCode;
	}

	/**
	 * holStaffCode
	 * @param holStaffCodeを設定します。
	 */
	public void setHolStaffCode(String holStaffCode) {
		this.holStaffCode = holStaffCode;
	}

	/**
	 * holStaffNameを取得します。
	 * @return holStaffName
	 */
	public String getHolStaffName() {
		return holStaffName;
	}

	/**
	 * holStaffName
	 * @param holStaffNameを設定します。
	 */
	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}

	/**
	 * holSectionNameを取得します。
	 * @return holSectionName
	 */
	public String getHolSectionName() {
		return holSectionName;
	}

	/**
	 * holSectionName
	 * @param holSectionNameを設定します。
	 */
	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}

	/**
	 * holCompanyCodeを取得します。
	 * @return holCompanyCode
	 */
	public String getHolCompanyCode() {
		return holCompanyCode;
	}

	/**
	 * holCompanyCode
	 * @param holCompanyCodeを設定します。
	 */
	public void setHolCompanyCode(String holCompanyCode) {
		this.holCompanyCode = holCompanyCode;
	}

	/**
	 * holSectionCodeを取得します。
	 * @return holSectionCode
	 */
	public String getHolSectionCode() {
		return holSectionCode;
	}

	/**
	 * holSectionCode
	 * @param holSectionCodeを設定します。
	 */
	public void setHolSectionCode(String holSectionCode) {
		this.holSectionCode = holSectionCode;
	}

	/**
	 * holSectionYearを取得します。
	 * @return holSectionYear
	 */
	public Integer getHolSectionYear() {
		return holSectionYear;
	}

	/**
	 * holSectionYear
	 * @param holSectionYearを設定します。
	 */
	public void setHolSectionYear(Integer holSectionYear) {
		this.holSectionYear = holSectionYear;
	}

	/**
	 * @return the dscAttribute1
	 */
	public String getDscAttribute1() {
		return dscAttribute1;
	}

	/**
	 * @param dscAttribute1 the dscAttribute1 to set
	 */
	public void setDscAttribute1(String dscAttribute1) {
		this.dscAttribute1 = dscAttribute1;
	}

	/**
	 * @return the dscAttribute2
	 */
	public String getDscAttribute2() {
		return dscAttribute2;
	}

	/**
	 * @param dscAttribute2 the dscAttribute2 to set
	 */
	public void setDscAttribute2(String dscAttribute2) {
		this.dscAttribute2 = dscAttribute2;
	}

	/**
	 * @return the dscAttribute3
	 */
	public String getDscAttribute3() {
		return dscAttribute3;
	}

	/**
	 * @param dscAttribute3 the dscAttribute3 to set
	 */
	public void setDscAttribute3(String dscAttribute3) {
		this.dscAttribute3 = dscAttribute3;
	}

	/**
	 * @return the dscAttribute4
	 */
	public String getDscAttribute4() {
		return dscAttribute4;
	}

	/**
	 * @param dscAttribute4 the dscAttribute4 to set
	 */
	public void setDscAttribute4(String dscAttribute4) {
		this.dscAttribute4 = dscAttribute4;
	}

	/**
	 * @return the dscAttribute5
	 */
	public String getDscAttribute5() {
		return dscAttribute5;
	}

	/**
	 * @param dscAttribute5 the dscAttribute5 to set
	 */
	public void setDscAttribute5(String dscAttribute5) {
		this.dscAttribute5 = dscAttribute5;
	}

	/**
	 * @return the dscAttribute6
	 */
	public String getDscAttribute6() {
		return dscAttribute6;
	}

	/**
	 * @param dscAttribute6 the dscAttribute6 to set
	 */
	public void setDscAttribute6(String dscAttribute6) {
		this.dscAttribute6 = dscAttribute6;
	}

	/**
	 * @return the dscAttribute7
	 */
	public String getDscAttribute7() {
		return dscAttribute7;
	}

	/**
	 * @param dscAttribute7 the dscAttribute7 to set
	 */
	public void setDscAttribute7(String dscAttribute7) {
		this.dscAttribute7 = dscAttribute7;
	}

	/**
	 * @return the dscAttribute8
	 */
	public String getDscAttribute8() {
		return dscAttribute8;
	}

	/**
	 * @param dscAttribute8 the dscAttribute8 to set
	 */
	public void setDscAttribute8(String dscAttribute8) {
		this.dscAttribute8 = dscAttribute8;
	}

	/**
	 * @return the dscAttribute9
	 */
	public String getDscAttribute9() {
		return dscAttribute9;
	}

	/**
	 * @param dscAttribute9 the dscAttribute9 to set
	 */
	public void setDscAttribute9(String dscAttribute9) {
		this.dscAttribute9 = dscAttribute9;
	}

	/**
	 * @return the dscAttribute10
	 */
	public String getDscAttribute10() {
		return dscAttribute10;
	}

	/**
	 * @param dscAttribute10 the dscAttribute10 to set
	 */
	public void setDscAttribute10(String dscAttribute10) {
		this.dscAttribute10 = dscAttribute10;
	}

	/**
	 * @return the dscAttribute11
	 */
	public String getDscAttribute11() {
		return dscAttribute11;
	}

	/**
	 * @param dscAttribute11 the dscAttribute11 to set
	 */
	public void setDscAttribute11(String dscAttribute11) {
		this.dscAttribute11 = dscAttribute11;
	}

	/**
	 * @return the dscAttribute12
	 */
	public String getDscAttribute12() {
		return dscAttribute12;
	}

	/**
	 * @param dscAttribute12 the dscAttribute12 to set
	 */
	public void setDscAttribute12(String dscAttribute12) {
		this.dscAttribute12 = dscAttribute12;
	}

	/**
	 * @return the dscAttribute13
	 */
	public String getDscAttribute13() {
		return dscAttribute13;
	}

	/**
	 * @param dscAttribute13 the dscAttribute13 to set
	 */
	public void setDscAttribute13(String dscAttribute13) {
		this.dscAttribute13 = dscAttribute13;
	}

	/**
	 * @return the dscAttribute14
	 */
	public String getDscAttribute14() {
		return dscAttribute14;
	}

	/**
	 * @param dscAttribute14 the dscAttribute14 to set
	 */
	public void setDscAttribute14(String dscAttribute14) {
		this.dscAttribute14 = dscAttribute14;
	}

	/**
	 * @return the dscAttribute15
	 */
	public String getDscAttribute15() {
		return dscAttribute15;
	}

	/**
	 * @param dscAttribute15 the dscAttribute15 to set
	 */
	public void setDscAttribute15(String dscAttribute15) {
		this.dscAttribute15 = dscAttribute15;
	}

	/**
	 * @return the dscAttribute16
	 */
	public String getDscAttribute16() {
		return dscAttribute16;
	}

	/**
	 * @param dscAttribute16 the dscAttribute16 to set
	 */
	public void setDscAttribute16(String dscAttribute16) {
		this.dscAttribute16 = dscAttribute16;
	}

	/**
	 * @return the dscAttribute17
	 */
	public String getDscAttribute17() {
		return dscAttribute17;
	}

	/**
	 * @param dscAttribute17 the dscAttribute17 to set
	 */
	public void setDscAttribute17(String dscAttribute17) {
		this.dscAttribute17 = dscAttribute17;
	}

	/**
	 * @return the dscAttribute18
	 */
	public String getDscAttribute18() {
		return dscAttribute18;
	}

	/**
	 * @param dscAttribute18 the dscAttribute18 to set
	 */
	public void setDscAttribute18(String dscAttribute18) {
		this.dscAttribute18 = dscAttribute18;
	}

	/**
	 * @return the dscAttribute19
	 */
	public String getDscAttribute19() {
		return dscAttribute19;
	}

	/**
	 * @param dscAttribute19 the dscAttribute19 to set
	 */
	public void setDscAttribute19(String dscAttribute19) {
		this.dscAttribute19 = dscAttribute19;
	}

	/**
	 * @return the dscAttribute20
	 */
	public String getDscAttribute20() {
		return dscAttribute20;
	}

	/**
	 * @param dscAttribute20 the dscAttribute20 to set
	 */
	public void setDscAttribute20(String dscAttribute20) {
		this.dscAttribute20 = dscAttribute20;
	}

	public String getHolSectionNameL() {
		return holSectionNameL;
	}

	public void setHolSectionNameL(String holSectionNameL) {
		this.holSectionNameL = holSectionNameL;
	}

	/**
	 * holCompanyNameを取得します。
	 * @return holCompanyName
	 */
	public String getHolCompanyName() {
		return holCompanyName;
	}

	/**
	 * holCompanyName
	 * @param holCompanyNameを設定します。
	 */
	public void setHolCompanyName(String holCompanyName) {
		this.holCompanyName = holCompanyName;
	}

}
