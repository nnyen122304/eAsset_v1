/*===================================================================
 * ファイル名 : PpfsFldApp.java
 * 概要説明   : 固定資産照会(取得申請単位)
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-14 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.fld;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class PpfsFldApp implements Externalizable {
	private static final long serialVersionUID = 1L;
	private String applicationId; // 申請書番号
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private Integer version; // バージョン
	private String apGetType; // 取得形態 汎用マスタ-AP_GET_INT_TYPE
	private String holCompanyCode; // 保有会社コード
	private String holSectionCode; // 保有部署コード
	private Integer holSectionYear; // 保有部署年度
	private String holStaffCode; // 無形管理担当者
	private String holStaffCompanyCode; // 無形管理担当者会社コード
	private String holStaffSectionCode; // 無形管理担当者部署コード
	private Integer holStaffSectionYear; // 無形管理担当者部署年度
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

	private List<PpfsFldSR> ppfsListK; // 仮勘定固定資産一覧
	private List<PpfsFldSR> ppfsListH; // 本勘定固定資産一覧

	private String holCompanyName;	// 保有会社
	private String holSectionName;	//	保有部署
	private String holStaffName;	// 無形管理担当者
	private String holStaffCompanyName;	// 無形管理担当者会社
	private String holStaffSectionName;	//	無形管理所属部署

	private List<LicenseSR> licenseList; // ﾗｲｾﾝｽ一覧

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		version = Function.getExternalInteger(input.readObject());
		apGetType = (String)input.readObject();
		holCompanyCode = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holStaffCode = (String)input.readObject();
		holStaffCompanyCode = (String)input.readObject();
		holStaffSectionCode = (String)input.readObject();
		holStaffSectionYear = Function.getExternalInteger(input.readObject());
		dscAttribute1 = (String)input.readObject();
		dscAttribute2 = (String)input.readObject();
		dscAttribute3 = (String)input.readObject();
		dscAttribute4 = (String)input.readObject();
		dscAttribute5 = (String)input.readObject();
		dscAttribute6 = (String)input.readObject();
		dscAttribute7 = (String)input.readObject();
		dscAttribute8 = (String)input.readObject();
		dscAttribute9 = (String)input.readObject();
		dscAttribute10 = (String)input.readObject();
		dscAttribute11 = (String)input.readObject();
		dscAttribute12 = (String)input.readObject();
		dscAttribute13 = (String)input.readObject();
		dscAttribute14 = (String)input.readObject();
		dscAttribute15 = (String)input.readObject();
		dscAttribute16 = (String)input.readObject();
		dscAttribute17 = (String)input.readObject();
		dscAttribute18 = (String)input.readObject();
		dscAttribute19 = (String)input.readObject();
		dscAttribute20 = (String)input.readObject();


		ppfsListK = (List<PpfsFldSR>)input.readObject();
		ppfsListH = (List<PpfsFldSR>)input.readObject();

		holCompanyName = (String)input.readObject();
		holSectionName = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holStaffCompanyName = (String)input.readObject();
		holStaffSectionName = (String)input.readObject();

		licenseList = (List<LicenseSR>)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(version);
		output.writeObject(apGetType);
		output.writeObject(holCompanyCode);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(holStaffCode);
		output.writeObject(holStaffCompanyCode);
		output.writeObject(holStaffSectionCode);
		output.writeObject(holStaffSectionYear);
		output.writeObject(dscAttribute1);
		output.writeObject(dscAttribute2);
		output.writeObject(dscAttribute3);
		output.writeObject(dscAttribute4);
		output.writeObject(dscAttribute5);
		output.writeObject(dscAttribute6);
		output.writeObject(dscAttribute7);
		output.writeObject(dscAttribute8);
		output.writeObject(dscAttribute9);
		output.writeObject(dscAttribute10);
		output.writeObject(dscAttribute11);
		output.writeObject(dscAttribute12);
		output.writeObject(dscAttribute13);
		output.writeObject(dscAttribute14);
		output.writeObject(dscAttribute15);
		output.writeObject(dscAttribute16);
		output.writeObject(dscAttribute17);
		output.writeObject(dscAttribute18);
		output.writeObject(dscAttribute19);
		output.writeObject(dscAttribute20);

		output.writeObject(ppfsListK);
		output.writeObject(ppfsListH);

		output.writeObject(holCompanyName);
		output.writeObject(holSectionName);

		output.writeObject(holStaffName);
		output.writeObject(holStaffCompanyName);
		output.writeObject(holStaffSectionName);

		output.writeObject(licenseList);

	}

	/**
	 * applicationIdを取得します。
	 * @return applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * applicationId
	 * @param applicationIdを設定します。
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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
	 * versionを取得します。
	 * @return version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * version
	 * @param versionを設定します。
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * apGetTypeを取得します。
	 * @return apGetType
	 */
	public String getApGetType() {
		return apGetType;
	}

	/**
	 * apGetType
	 * @param apGetTypeを設定します。
	 */
	public void setApGetType(String apGetType) {
		this.apGetType = apGetType;
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
	 * holStaffCompanyCodeを取得します。
	 * @return holStaffCompanyCode
	 */
	public String getHolStaffCompanyCode() {
		return holStaffCompanyCode;
	}

	/**
	 * holStaffCompanyCode
	 * @param holStaffCompanyCodeを設定します。
	 */
	public void setHolStaffCompanyCode(String holStaffCompanyCode) {
		this.holStaffCompanyCode = holStaffCompanyCode;
	}

	/**
	 * holStaffSectionCodeを取得します。
	 * @return holStaffSectionCode
	 */
	public String getHolStaffSectionCode() {
		return holStaffSectionCode;
	}

	/**
	 * holStaffSectionCode
	 * @param holStaffSectionCodeを設定します。
	 */
	public void setHolStaffSectionCode(String holStaffSectionCode) {
		this.holStaffSectionCode = holStaffSectionCode;
	}

	/**
	 * holStaffSectionYearを取得します。
	 * @return holStaffSectionYear
	 */
	public Integer getHolStaffSectionYear() {
		return holStaffSectionYear;
	}

	/**
	 * holStaffSectionYear
	 * @param holStaffSectionYearを設定します。
	 */
	public void setHolStaffSectionYear(Integer holStaffSectionYear) {
		this.holStaffSectionYear = holStaffSectionYear;
	}

	/**
	 * dscAttribute1を取得します。
	 * @return dscAttribute1
	 */
	public String getDscAttribute1() {
		return dscAttribute1;
	}

	/**
	 * dscAttribute1
	 * @param dscAttribute1を設定します。
	 */
	public void setDscAttribute1(String dscAttribute1) {
		this.dscAttribute1 = dscAttribute1;
	}

	/**
	 * dscAttribute2を取得します。
	 * @return dscAttribute2
	 */
	public String getDscAttribute2() {
		return dscAttribute2;
	}

	/**
	 * dscAttribute2
	 * @param dscAttribute2を設定します。
	 */
	public void setDscAttribute2(String dscAttribute2) {
		this.dscAttribute2 = dscAttribute2;
	}

	/**
	 * dscAttribute3を取得します。
	 * @return dscAttribute3
	 */
	public String getDscAttribute3() {
		return dscAttribute3;
	}

	/**
	 * dscAttribute3
	 * @param dscAttribute3を設定します。
	 */
	public void setDscAttribute3(String dscAttribute3) {
		this.dscAttribute3 = dscAttribute3;
	}

	/**
	 * dscAttribute4を取得します。
	 * @return dscAttribute4
	 */
	public String getDscAttribute4() {
		return dscAttribute4;
	}

	/**
	 * dscAttribute4
	 * @param dscAttribute4を設定します。
	 */
	public void setDscAttribute4(String dscAttribute4) {
		this.dscAttribute4 = dscAttribute4;
	}

	/**
	 * dscAttribute5を取得します。
	 * @return dscAttribute5
	 */
	public String getDscAttribute5() {
		return dscAttribute5;
	}

	/**
	 * dscAttribute5
	 * @param dscAttribute5を設定します。
	 */
	public void setDscAttribute5(String dscAttribute5) {
		this.dscAttribute5 = dscAttribute5;
	}

	/**
	 * dscAttribute6を取得します。
	 * @return dscAttribute6
	 */
	public String getDscAttribute6() {
		return dscAttribute6;
	}

	/**
	 * dscAttribute6
	 * @param dscAttribute6を設定します。
	 */
	public void setDscAttribute6(String dscAttribute6) {
		this.dscAttribute6 = dscAttribute6;
	}

	/**
	 * dscAttribute7を取得します。
	 * @return dscAttribute7
	 */
	public String getDscAttribute7() {
		return dscAttribute7;
	}

	/**
	 * dscAttribute7
	 * @param dscAttribute7を設定します。
	 */
	public void setDscAttribute7(String dscAttribute7) {
		this.dscAttribute7 = dscAttribute7;
	}

	/**
	 * dscAttribute8を取得します。
	 * @return dscAttribute8
	 */
	public String getDscAttribute8() {
		return dscAttribute8;
	}

	/**
	 * dscAttribute8
	 * @param dscAttribute8を設定します。
	 */
	public void setDscAttribute8(String dscAttribute8) {
		this.dscAttribute8 = dscAttribute8;
	}

	/**
	 * dscAttribute9を取得します。
	 * @return dscAttribute9
	 */
	public String getDscAttribute9() {
		return dscAttribute9;
	}

	/**
	 * dscAttribute9
	 * @param dscAttribute9を設定します。
	 */
	public void setDscAttribute9(String dscAttribute9) {
		this.dscAttribute9 = dscAttribute9;
	}

	/**
	 * dscAttribute10を取得します。
	 * @return dscAttribute10
	 */
	public String getDscAttribute10() {
		return dscAttribute10;
	}

	/**
	 * dscAttribute10
	 * @param dscAttribute10を設定します。
	 */
	public void setDscAttribute10(String dscAttribute10) {
		this.dscAttribute10 = dscAttribute10;
	}

	/**
	 * dscAttribute11を取得します。
	 * @return dscAttribute11
	 */
	public String getDscAttribute11() {
		return dscAttribute11;
	}

	/**
	 * dscAttribute11
	 * @param dscAttribute11を設定します。
	 */
	public void setDscAttribute11(String dscAttribute11) {
		this.dscAttribute11 = dscAttribute11;
	}

	/**
	 * dscAttribute12を取得します。
	 * @return dscAttribute12
	 */
	public String getDscAttribute12() {
		return dscAttribute12;
	}

	/**
	 * dscAttribute12
	 * @param dscAttribute12を設定します。
	 */
	public void setDscAttribute12(String dscAttribute12) {
		this.dscAttribute12 = dscAttribute12;
	}

	/**
	 * dscAttribute13を取得します。
	 * @return dscAttribute13
	 */
	public String getDscAttribute13() {
		return dscAttribute13;
	}

	/**
	 * dscAttribute13
	 * @param dscAttribute13を設定します。
	 */
	public void setDscAttribute13(String dscAttribute13) {
		this.dscAttribute13 = dscAttribute13;
	}

	/**
	 * dscAttribute14を取得します。
	 * @return dscAttribute14
	 */
	public String getDscAttribute14() {
		return dscAttribute14;
	}

	/**
	 * dscAttribute14
	 * @param dscAttribute14を設定します。
	 */
	public void setDscAttribute14(String dscAttribute14) {
		this.dscAttribute14 = dscAttribute14;
	}

	/**
	 * dscAttribute15を取得します。
	 * @return dscAttribute15
	 */
	public String getDscAttribute15() {
		return dscAttribute15;
	}

	/**
	 * dscAttribute15
	 * @param dscAttribute15を設定します。
	 */
	public void setDscAttribute15(String dscAttribute15) {
		this.dscAttribute15 = dscAttribute15;
	}

	/**
	 * dscAttribute16を取得します。
	 * @return dscAttribute16
	 */
	public String getDscAttribute16() {
		return dscAttribute16;
	}

	/**
	 * dscAttribute16
	 * @param dscAttribute16を設定します。
	 */
	public void setDscAttribute16(String dscAttribute16) {
		this.dscAttribute16 = dscAttribute16;
	}

	/**
	 * dscAttribute17を取得します。
	 * @return dscAttribute17
	 */
	public String getDscAttribute17() {
		return dscAttribute17;
	}

	/**
	 * dscAttribute17
	 * @param dscAttribute17を設定します。
	 */
	public void setDscAttribute17(String dscAttribute17) {
		this.dscAttribute17 = dscAttribute17;
	}

	/**
	 * dscAttribute18を取得します。
	 * @return dscAttribute18
	 */
	public String getDscAttribute18() {
		return dscAttribute18;
	}

	/**
	 * dscAttribute18
	 * @param dscAttribute18を設定します。
	 */
	public void setDscAttribute18(String dscAttribute18) {
		this.dscAttribute18 = dscAttribute18;
	}

	/**
	 * dscAttribute19を取得します。
	 * @return dscAttribute19
	 */
	public String getDscAttribute19() {
		return dscAttribute19;
	}

	/**
	 * dscAttribute19
	 * @param dscAttribute19を設定します。
	 */
	public void setDscAttribute19(String dscAttribute19) {
		this.dscAttribute19 = dscAttribute19;
	}

	/**
	 * dscAttribute20を取得します。
	 * @return dscAttribute20
	 */
	public String getDscAttribute20() {
		return dscAttribute20;
	}

	/**
	 * dscAttribute20
	 * @param dscAttribute20を設定します。
	 */
	public void setDscAttribute20(String dscAttribute20) {
		this.dscAttribute20 = dscAttribute20;
	}

	/**
	 * ppfsListKを取得します。
	 * @return ppfsListK
	 */
	public List<PpfsFldSR> getPpfsListK() {
		return ppfsListK;
	}

	/**
	 * ppfsListK
	 * @param ppfsListKを設定します。
	 */
	public void setPpfsListK(List<PpfsFldSR> ppfsListK) {
		this.ppfsListK = ppfsListK;
	}

	/**
	 * ppfsListHを取得します。
	 * @return ppfsListH
	 */
	public List<PpfsFldSR> getPpfsListH() {
		return ppfsListH;
	}

	/**
	 * ppfsListH
	 * @param ppfsListHを設定します。
	 */
	public void setPpfsListH(List<PpfsFldSR> ppfsListH) {
		this.ppfsListH = ppfsListH;
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
	 * holStaffCompanyNameを取得します。
	 * @return holStaffCompanyName
	 */
	public String getHolStaffCompanyName() {
		return holStaffCompanyName;
	}

	/**
	 * holStaffCompanyName
	 * @param holStaffCompanyNameを設定します。
	 */
	public void setHolStaffCompanyName(String holStaffCompanyName) {
		this.holStaffCompanyName = holStaffCompanyName;
	}

	/**
	 * holStaffSectionNameを取得します。
	 * @return holStaffSectionName
	 */
	public String getHolStaffSectionName() {
		return holStaffSectionName;
	}

	/**
	 * holStaffSectionName
	 * @param holStaffSectionNameを設定します。
	 */
	public void setHolStaffSectionName(String holStaffSectionName) {
		this.holStaffSectionName = holStaffSectionName;
	}

	/**
	 * licenseListを取得します。
	 * @return licenseList
	 */
	public List<LicenseSR> getLicenseList() {
		return licenseList;
	}

	/**
	 * licenseList
	 * @param licenseListを設定します。
	 */
	public void setLicenseList(List<LicenseSR> licenseList) {
		this.licenseList = licenseList;
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

}
