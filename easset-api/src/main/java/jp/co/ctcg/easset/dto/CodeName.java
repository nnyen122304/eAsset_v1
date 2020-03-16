/*===================================================================
 * ファイル名 : CodeName.java
 * 概要説明   : ユーザー情報
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-06 1.0  リヨン 崔　  新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

@ToString
public class CodeName implements Serializable {
	private static final long serialVersionUID = 1L;

	private String categoryCode; // カテゴリコード
	private String internalCode; // 識別コード
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String companyCode; // 会社コード 全社共通マスタ：000固定,複数会社共通マスタ：会社コードのスペース区切
	private String parentCategoryCode; // 親カテゴリコード
	private String parentInternalCode; // 親識別コード
	private Integer sortNumber; // ソート番号
	private String deleteFlag; // 削除フラグ 0:有効、1:無効（削除）
	private String description; // 備考
	private String value1; // 値1
	private String value2; // 値2
	private String value3; // 値3
	private String value4; // 値4
	private String value5; // 値5
	private String value6; // 値6
	private String value7; // 値7
	private String value8; // 値8
	private String value9; // 値9
	private String value10; // 値10
	private String value11; // 値11
	private String value12; // 値12
	private String value13; // 値13
	private String value14; // 値14
	private String value15; // 値15
	private String value16; // 値16
	private String value17; // 値17
	private String value18; // 値18
	private String value19; // 値19
	private String value20; // 値20
	private String value21; // 値21
	private String value22; // 値22
	private String value23; // 値23
	private String value24; // 値24
	private String value25; // 値25
	private String value26; // 値26
	private String value27; // 値27
	private String value28; // 値28
	private String value29; // 値29
	private String value30; // 値30
	private String value31; // 値31
	private String value32; // 値32
	private String value33; // 値33
	private String value34; // 値34
	private String value35; // 値35
	private String value36; // 値36
	private String value37; // 値37
	private String value38; // 値38
	private String value39; // 値39
	private String value40; // 値40
	private String value41; // 値41
	private String value42; // 値42
	private String value43; // 値43
	private String value44; // 値44
	private String value45; // 値45
	private String value46; // 値46
	private String value47; // 値47
	private String value48; // 値48
	private String value49; // 値49
	private String value50; // 値50
	private String value51; // 値51
	private String value52; // 値52
	private String value53; // 値53
	private String value54; // 値54
	private String value55; // 値55
	private String value56; // 値56
	private String value57; // 値57
	private String value58; // 値58
	private String value59; // 値59
	private String value60; // 値60

	private String deleteFlagName;	//	削除フラグ名
	private String parentInternalName;	//	親値
	private String companyName;	//	会社名
	private Integer parentSortNumber;	//	親番号

	/**
	 * categoryCodeを取得します。
	 * @return categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}
	/**
	 * categoryCode
	 * @param categoryCodeを設定します。
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	/**
	 * internalCodeを取得します。
	 * @return internalCode
	 */
	public String getInternalCode() {
		return internalCode;
	}
	/**
	 * internalCode
	 * @param internalCodeを設定します。
	 */
	public void setInternalCode(String internalCode) {
		this.internalCode = internalCode;
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
	 * parentCategoryCodeを取得します。
	 * @return parentCategoryCode
	 */
	public String getParentCategoryCode() {
		return parentCategoryCode;
	}
	/**
	 * parentCategoryCode
	 * @param parentCategoryCodeを設定します。
	 */
	public void setParentCategoryCode(String parentCategoryCode) {
		this.parentCategoryCode = parentCategoryCode;
	}
	/**
	 * parentInternalCodeを取得します。
	 * @return parentInternalCode
	 */
	public String getParentInternalCode() {
		return parentInternalCode;
	}
	/**
	 * parentInternalCode
	 * @param parentInternalCodeを設定します。
	 */
	public void setParentInternalCode(String parentInternalCode) {
		this.parentInternalCode = parentInternalCode;
	}
	/**
	 * sortNumberを取得します。
	 * @return sortNumber
	 */
	public Integer getSortNumber() {
		return sortNumber;
	}
	/**
	 * sortNumber
	 * @param sortNumberを設定します。
	 */
	public void setSortNumber(Integer sortNumber) {
		this.sortNumber = sortNumber;
	}
	/**
	 * deleteFlagを取得します。
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}
	/**
	 * deleteFlag
	 * @param deleteFlagを設定します。
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}
	/**
	 * descriptionを取得します。
	 * @return description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * description
	 * @param descriptionを設定します。
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * value1を取得します。
	 * @return value1
	 */
	public String getValue1() {
		return value1;
	}
	/**
	 * value1
	 * @param value1を設定します。
	 */
	public void setValue1(String value1) {
		this.value1 = value1;
	}
	/**
	 * value2を取得します。
	 * @return value2
	 */
	public String getValue2() {
		return value2;
	}
	/**
	 * value2
	 * @param value2を設定します。
	 */
	public void setValue2(String value2) {
		this.value2 = value2;
	}
	/**
	 * value3を取得します。
	 * @return value3
	 */
	public String getValue3() {
		return value3;
	}
	/**
	 * value3
	 * @param value3を設定します。
	 */
	public void setValue3(String value3) {
		this.value3 = value3;
	}
	/**
	 * value4を取得します。
	 * @return value4
	 */
	public String getValue4() {
		return value4;
	}
	/**
	 * value4
	 * @param value4を設定します。
	 */
	public void setValue4(String value4) {
		this.value4 = value4;
	}
	/**
	 * value5を取得します。
	 * @return value5
	 */
	public String getValue5() {
		return value5;
	}
	/**
	 * value5
	 * @param value5を設定します。
	 */
	public void setValue5(String value5) {
		this.value5 = value5;
	}
	/**
	 * value6を取得します。
	 * @return value6
	 */
	public String getValue6() {
		return value6;
	}
	/**
	 * value6
	 * @param value6を設定します。
	 */
	public void setValue6(String value6) {
		this.value6 = value6;
	}
	/**
	 * value7を取得します。
	 * @return value7
	 */
	public String getValue7() {
		return value7;
	}
	/**
	 * value7
	 * @param value7を設定します。
	 */
	public void setValue7(String value7) {
		this.value7 = value7;
	}
	/**
	 * value8を取得します。
	 * @return value8
	 */
	public String getValue8() {
		return value8;
	}
	/**
	 * value8
	 * @param value8を設定します。
	 */
	public void setValue8(String value8) {
		this.value8 = value8;
	}
	/**
	 * value9を取得します。
	 * @return value9
	 */
	public String getValue9() {
		return value9;
	}
	/**
	 * value9
	 * @param value9を設定します。
	 */
	public void setValue9(String value9) {
		this.value9 = value9;
	}
	/**
	 * value10を取得します。
	 * @return value10
	 */
	public String getValue10() {
		return value10;
	}
	/**
	 * value10
	 * @param value10を設定します。
	 */
	public void setValue10(String value10) {
		this.value10 = value10;
	}
	/**
	 * value11を取得します。
	 * @return value11
	 */
	public String getValue11() {
		return value11;
	}
	/**
	 * value11
	 * @param value11を設定します。
	 */
	public void setValue11(String value11) {
		this.value11 = value11;
	}
	/**
	 * value12を取得します。
	 * @return value12
	 */
	public String getValue12() {
		return value12;
	}
	/**
	 * value12
	 * @param value12を設定します。
	 */
	public void setValue12(String value12) {
		this.value12 = value12;
	}
	/**
	 * value13を取得します。
	 * @return value13
	 */
	public String getValue13() {
		return value13;
	}
	/**
	 * value13
	 * @param value13を設定します。
	 */
	public void setValue13(String value13) {
		this.value13 = value13;
	}
	/**
	 * value14を取得します。
	 * @return value14
	 */
	public String getValue14() {
		return value14;
	}
	/**
	 * value14
	 * @param value14を設定します。
	 */
	public void setValue14(String value14) {
		this.value14 = value14;
	}
	/**
	 * value15を取得します。
	 * @return value15
	 */
	public String getValue15() {
		return value15;
	}
	/**
	 * value15
	 * @param value15を設定します。
	 */
	public void setValue15(String value15) {
		this.value15 = value15;
	}
	/**
	 * value16を取得します。
	 * @return value16
	 */
	public String getValue16() {
		return value16;
	}
	/**
	 * value16
	 * @param value16を設定します。
	 */
	public void setValue16(String value16) {
		this.value16 = value16;
	}
	/**
	 * value17を取得します。
	 * @return value17
	 */
	public String getValue17() {
		return value17;
	}
	/**
	 * value17
	 * @param value17を設定します。
	 */
	public void setValue17(String value17) {
		this.value17 = value17;
	}
	/**
	 * value18を取得します。
	 * @return value18
	 */
	public String getValue18() {
		return value18;
	}
	/**
	 * value18
	 * @param value18を設定します。
	 */
	public void setValue18(String value18) {
		this.value18 = value18;
	}
	/**
	 * value19を取得します。
	 * @return value19
	 */
	public String getValue19() {
		return value19;
	}
	/**
	 * value19
	 * @param value19を設定します。
	 */
	public void setValue19(String value19) {
		this.value19 = value19;
	}
	/**
	 * value20を取得します。
	 * @return value20
	 */
	public String getValue20() {
		return value20;
	}
	/**
	 * value20
	 * @param value20を設定します。
	 */
	public void setValue20(String value20) {
		this.value20 = value20;
	}
	/**
	 * value21を取得します。
	 * @return value21
	 */
	public String getValue21() {
		return value21;
	}
	/**
	 * value21
	 * @param value21を設定します。
	 */
	public void setValue21(String value21) {
		this.value21 = value21;
	}
	/**
	 * value22を取得します。
	 * @return value22
	 */
	public String getValue22() {
		return value22;
	}
	/**
	 * value22
	 * @param value22を設定します。
	 */
	public void setValue22(String value22) {
		this.value22 = value22;
	}
	/**
	 * value23を取得します。
	 * @return value23
	 */
	public String getValue23() {
		return value23;
	}
	/**
	 * value23
	 * @param value23を設定します。
	 */
	public void setValue23(String value23) {
		this.value23 = value23;
	}
	/**
	 * value24を取得します。
	 * @return value24
	 */
	public String getValue24() {
		return value24;
	}
	/**
	 * value24
	 * @param value24を設定します。
	 */
	public void setValue24(String value24) {
		this.value24 = value24;
	}
	/**
	 * value25を取得します。
	 * @return value25
	 */
	public String getValue25() {
		return value25;
	}
	/**
	 * value25
	 * @param value25を設定します。
	 */
	public void setValue25(String value25) {
		this.value25 = value25;
	}
	/**
	 * value26を取得します。
	 * @return value26
	 */
	public String getValue26() {
		return value26;
	}
	/**
	 * value26
	 * @param value26を設定します。
	 */
	public void setValue26(String value26) {
		this.value26 = value26;
	}
	/**
	 * value27を取得します。
	 * @return value27
	 */
	public String getValue27() {
		return value27;
	}
	/**
	 * value27
	 * @param value27を設定します。
	 */
	public void setValue27(String value27) {
		this.value27 = value27;
	}
	/**
	 * value28を取得します。
	 * @return value28
	 */
	public String getValue28() {
		return value28;
	}
	/**
	 * value28
	 * @param value28を設定します。
	 */
	public void setValue28(String value28) {
		this.value28 = value28;
	}
	/**
	 * value29を取得します。
	 * @return value29
	 */
	public String getValue29() {
		return value29;
	}
	/**
	 * value29
	 * @param value29を設定します。
	 */
	public void setValue29(String value29) {
		this.value29 = value29;
	}
	/**
	 * value30を取得します。
	 * @return value30
	 */
	public String getValue30() {
		return value30;
	}
	/**
	 * value30
	 * @param value30を設定します。
	 */
	public void setValue30(String value30) {
		this.value30 = value30;
	}
	/**
	 * value31を取得します。
	 * @return value31
	 */
	public String getValue31() {
		return value31;
	}
	/**
	 * value31
	 * @param value31を設定します。
	 */
	public void setValue31(String value31) {
		this.value31 = value31;
	}
	/**
	 * value32を取得します。
	 * @return value32
	 */
	public String getValue32() {
		return value32;
	}
	/**
	 * value32
	 * @param value32を設定します。
	 */
	public void setValue32(String value32) {
		this.value32 = value32;
	}
	/**
	 * value33を取得します。
	 * @return value33
	 */
	public String getValue33() {
		return value33;
	}
	/**
	 * value33
	 * @param value33を設定します。
	 */
	public void setValue33(String value33) {
		this.value33 = value33;
	}
	/**
	 * value34を取得します。
	 * @return value34
	 */
	public String getValue34() {
		return value34;
	}
	/**
	 * value34
	 * @param value34を設定します。
	 */
	public void setValue34(String value34) {
		this.value34 = value34;
	}
	/**
	 * value35を取得します。
	 * @return value35
	 */
	public String getValue35() {
		return value35;
	}
	/**
	 * value35
	 * @param value35を設定します。
	 */
	public void setValue35(String value35) {
		this.value35 = value35;
	}
	/**
	 * value36を取得します。
	 * @return value36
	 */
	public String getValue36() {
		return value36;
	}
	/**
	 * value36
	 * @param value36を設定します。
	 */
	public void setValue36(String value36) {
		this.value36 = value36;
	}
	/**
	 * value37を取得します。
	 * @return value37
	 */
	public String getValue37() {
		return value37;
	}
	/**
	 * value37
	 * @param value37を設定します。
	 */
	public void setValue37(String value37) {
		this.value37 = value37;
	}
	/**
	 * value38を取得します。
	 * @return value38
	 */
	public String getValue38() {
		return value38;
	}
	/**
	 * value38
	 * @param value38を設定します。
	 */
	public void setValue38(String value38) {
		this.value38 = value38;
	}
	/**
	 * value39を取得します。
	 * @return value39
	 */
	public String getValue39() {
		return value39;
	}
	/**
	 * value39
	 * @param value39を設定します。
	 */
	public void setValue39(String value39) {
		this.value39 = value39;
	}
	/**
	 * value40を取得します。
	 * @return value40
	 */
	public String getValue40() {
		return value40;
	}
	/**
	 * value40
	 * @param value40を設定します。
	 */
	public void setValue40(String value40) {
		this.value40 = value40;
	}
	/**
	 * deleteFlagNameを取得します。
	 * @return deleteFlagName
	 */
	public String getDeleteFlagName() {
		return deleteFlagName;
	}
	/**
	 * deleteFlagName
	 * @param deleteFlagNameを設定します。
	 */
	public void setDeleteFlagName(String deleteFlagName) {
		this.deleteFlagName = deleteFlagName;
	}

	/**
	 * parentInternalNameを取得します。
	 * @return parentInternalName
	 */
	public String getParentInternalName() {
		return parentInternalName;
	}
	/**
	 * parentInternalName
	 * @param parentInternalNameを設定します。
	 */
	public void setParentInternalName(String parentInternalName) {
		this.parentInternalName = parentInternalName;
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
	 * parentSortNumberを取得します。
	 * @return parentSortNumber
	 */
	public Integer getParentSortNumber() {
		return parentSortNumber;
	}
	/**
	 * parentSortNumber
	 * @param parentSortNumberを設定します。
	 */
	public void setParentSortNumber(Integer parentSortNumber) {
		this.parentSortNumber = parentSortNumber;
	}
	public String getValue41() {
		return value41;
	}
	public void setValue41(String value41) {
		this.value41 = value41;
	}
	public String getValue42() {
		return value42;
	}
	public void setValue42(String value42) {
		this.value42 = value42;
	}
	public String getValue43() {
		return value43;
	}
	public void setValue43(String value43) {
		this.value43 = value43;
	}
	public String getValue44() {
		return value44;
	}
	public void setValue44(String value44) {
		this.value44 = value44;
	}
	public String getValue45() {
		return value45;
	}
	public void setValue45(String value45) {
		this.value45 = value45;
	}
	public String getValue46() {
		return value46;
	}
	public void setValue46(String value46) {
		this.value46 = value46;
	}
	public String getValue47() {
		return value47;
	}
	public void setValue47(String value47) {
		this.value47 = value47;
	}
	public String getValue48() {
		return value48;
	}
	public void setValue48(String value48) {
		this.value48 = value48;
	}
	public String getValue49() {
		return value49;
	}
	public void setValue49(String value49) {
		this.value49 = value49;
	}
	public String getValue50() {
		return value50;
	}
	public void setValue50(String value50) {
		this.value50 = value50;
	}
	public String getValue51() {
		return value51;
	}
	public void setValue51(String value51) {
		this.value51 = value51;
	}
	public String getValue52() {
		return value52;
	}
	public void setValue52(String value52) {
		this.value52 = value52;
	}
	public String getValue53() {
		return value53;
	}
	public void setValue53(String value53) {
		this.value53 = value53;
	}
	public String getValue54() {
		return value54;
	}
	public void setValue54(String value54) {
		this.value54 = value54;
	}
	public String getValue55() {
		return value55;
	}
	public void setValue55(String value55) {
		this.value55 = value55;
	}
	public String getValue56() {
		return value56;
	}
	public void setValue56(String value56) {
		this.value56 = value56;
	}
	public String getValue57() {
		return value57;
	}
	public void setValue57(String value57) {
		this.value57 = value57;
	}
	public String getValue58() {
		return value58;
	}
	public void setValue58(String value58) {
		this.value58 = value58;
	}
	public String getValue59() {
		return value59;
	}
	public void setValue59(String value59) {
		this.value59 = value59;
	}
	public String getValue60() {
		return value60;
	}
	public void setValue60(String value60) {
		this.value60 = value60;
	}

}
