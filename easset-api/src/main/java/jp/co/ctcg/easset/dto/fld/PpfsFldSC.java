/*===================================================================
 * ファイル名 : PpfsFldSC.java
 * 概要説明   : 固定資産(照会・管理項目修正)検索条件
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
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class PpfsFldSC extends PpfsFld {
	private static final long serialVersionUID = 1L;

	//	資産
	private String searchScopeType;					// 検索対象（1:自分の管轄部署が保有（使用）部署に設定されている資産(機器)、2:全ての資産(機器)）

	private Date stkymdFrom; // 取得日(From)
	private Date stkymdTo; // 取得日(To)
	private Date kadoymdFrom; // 稼働日(From)
	private Date kadoymdTo; // 稼働日(To)
	private Date jbkymdFrom; // 除却日(From)
	private Date jbkymdTo; // 除却日(To)
	private String shuruinmPlural; // 種類_複数
	private String bunruinmPlural; // 分類_複数
	private String setchinmPlural; // 代表設置場所_複数
	private String itemGroupNamePlural; // 案件グループ_複数
	private String slipNumPlural; // 伝票番号_複数
	private String applicationIdPlural; // 取得申請番号_複数

	//	管理番号
	private String assetId; // 情報機器管理番号
	private String assetIdPlural; // 情報機器管理番号_複数
	private String licenseId; // ﾗｲｾﾝｽ管理番号
	private String licenseIdPlural; // ﾗｲｾﾝｽ管理番号_複数
	private String shisanNumPlural; // 資産番号_複数

	private String holSectionCode; // 保有部署コード
	private Integer holSectionYear; // 保有部署年度
	private String holSectionName; // 保有部署名

	private String costSectionCode0; // 経費負担部署コード0（複数）
	private String costSectionName0; // 経費負担部署名0（複数）
	private String costSectionCode1; // 経費負担部署コード1（複数）
	private String costSectionName1; // 経費負担部署名1（複数）
	private String costSectionCode2; // 経費負担部署コード2（複数）
	private String costSectionName2; // 経費負担部署名2（複数）
	private String costSectionCode3; // 経費負担部署コード3（複数）
	private String costSectionName3; // 経費負担部署名3（複数）
	private String costSectionCode4; // 経費負担部署コード4（複数）
	private String costSectionName4; // 経費負担部署名4（複数）
	private String costSectionCode5; // 経費負担部署コード5（複数）
	private String costSectionName5; // 経費負担部署名5（複数）
	private String costSectionCode6; // 経費負担部署コード6（複数）
	private String costSectionName6; // 経費負担部署名6（複数）
	private String costSectionCode7; // 経費負担部署コード7（複数）
	private String costSectionName7; // 経費負担部署名7（複数）
	private String costSectionCode8; // 経費負担部署コード8（複数）
	private String costSectionName8; // 経費負担部署名8（複数）
	private String costSectionCode9; // 経費負担部署コード9（複数）
	private String costSectionName9; // 経費負担部署名9（複数）

	//	現物情報(無形拡張情報)の検索条件
	private String holStaffCodeI; // 無形管理担当者コード
	private String holStaffNameI; // 無形管理担当者名
	private String holStaffCompanyCodeI; // 無形管理担当者会社コード
	private String holStaffSectionCodeI; // 無形管理担当者部署コード
	private Integer holStaffSectionYearI; // 無形管理担当者部署年度
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

	private String dscAttribute;					// 管理項目

	//	現物情報(有形固定)の検索条件
	private String holStaffCodeT;					// 資産管理担当者 情報機器等と同項目
	private String holStaffNameT;					// 資産管理担当者氏名 情報機器等と同項目
	private String holOfficeCode; // 設置場所 汎用マスタ-OFFICE
	private Integer holOfficeFloor; // 設置場所階数
	private String holOfficeRoomNum; // 部屋番号
	private String holOfficeRackNum; // ラック番号
	private String useStaffCode; // 使用者社員番号
	private String useStaffCompanyCode; // 使用者所属会社コード
	private String useStaffSectionCode; // 使用者所属部署コード
	private Integer useStaffSectionYear; // 使用者所属部署年度
	private String holOfficePlural;					// 資設置（使用）場所複数

	private String includeSectionHierarchyFlag;		// 部署階層検索フラグ
	private String holOfficeName;					// 設置場所名
	private String useStaffName;					// 使用者氏名

	private String relationShisanNumFlag;			// 関連資産も同時に検索する

	private String astSystemSectionDeployFlag;		// 情報システム部配備フラグ 情報機器等と同項目
	private String astManageTypePlural;				// 管理区分複数

	/****** 管理帳票用 ******/
	private String reportDistType; // 配賦
	private String reportSchUnit; // スケジュール表示単位
	private String reportSchCalcBasePeriod; // スケジュール計算基準年月
	private Integer reportSchCalcYear; // スケジュール計算年数
	private String reportLedgerType; // 出力帳簿(情報)
	private String reportPeriod; // 会計年月
	private String reportPeriodFrom; // 出力期間(From)
	private String reportPeriodTo; // 出力期間(To)

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);
		searchScopeType = (String)input.readObject();

		stkymdFrom = (Date)input.readObject();
		stkymdTo = (Date)input.readObject();
		kadoymdFrom = (Date)input.readObject();
		kadoymdTo = (Date)input.readObject();
		jbkymdFrom = (Date)input.readObject();
		jbkymdTo = (Date)input.readObject();
		shuruinmPlural = (String)input.readObject();
		bunruinmPlural = (String)input.readObject();
		setchinmPlural = (String)input.readObject();
		itemGroupNamePlural = (String)input.readObject();
		slipNumPlural = (String)input.readObject();
		applicationIdPlural = (String)input.readObject();

		assetId = (String)input.readObject();
		assetIdPlural = (String)input.readObject();
		licenseId = (String)input.readObject();
		licenseIdPlural = (String)input.readObject();
		shisanNumPlural = (String)input.readObject();

		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holSectionName = (String)input.readObject();

		costSectionCode0 = (String)input.readObject();
		costSectionName0 = (String)input.readObject();
		costSectionCode1 = (String)input.readObject();
		costSectionName1 = (String)input.readObject();
		costSectionCode2 = (String)input.readObject();
		costSectionName2 = (String)input.readObject();
		costSectionCode3 = (String)input.readObject();
		costSectionName3 = (String)input.readObject();
		costSectionCode4 = (String)input.readObject();
		costSectionName4 = (String)input.readObject();
		costSectionCode5 = (String)input.readObject();
		costSectionName5 = (String)input.readObject();
		costSectionCode6 = (String)input.readObject();
		costSectionName6 = (String)input.readObject();
		costSectionCode7 = (String)input.readObject();
		costSectionName7 = (String)input.readObject();
		costSectionCode8 = (String)input.readObject();
		costSectionName8 = (String)input.readObject();
		costSectionCode9 = (String)input.readObject();
		costSectionName9 = (String)input.readObject();

//		現物情報(無形拡張情報)の検索条件
		holStaffCodeI = (String)input.readObject();
		holStaffNameI = (String)input.readObject();
		holStaffCompanyCodeI = (String)input.readObject();
		holStaffSectionCodeI = (String)input.readObject();
		holStaffSectionYearI = Function.getExternalInteger(input.readObject());
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

		dscAttribute = (String)input.readObject();

		//	現物情報(有形固定)の検索条件
		holStaffCodeT = (String)input.readObject();
		holStaffNameT = (String)input.readObject();
		holOfficeCode = (String)input.readObject();
		holOfficeFloor = Function.getExternalInteger(input.readObject());
		holOfficeRoomNum = (String)input.readObject();
		holOfficeRackNum = (String)input.readObject();
		useStaffCode = (String)input.readObject();
		useStaffCompanyCode = (String)input.readObject();
		useStaffSectionCode = (String)input.readObject();
		useStaffSectionYear = Function.getExternalInteger(input.readObject());
		holOfficePlural = (String)input.readObject();

		includeSectionHierarchyFlag = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		useStaffName = (String)input.readObject();

		relationShisanNumFlag = (String)input.readObject();

		astSystemSectionDeployFlag = (String)input.readObject();
		astManageTypePlural = (String)input.readObject();

		/****** 管理帳票用 ******/
		reportDistType = (String)input.readObject();
		reportSchUnit = (String)input.readObject();
		reportSchCalcBasePeriod = (String)input.readObject();
		reportSchCalcYear = Function.getExternalInteger(input.readObject());
		reportLedgerType = (String)input.readObject();
		reportPeriod = (String)input.readObject();
		reportPeriodFrom = (String)input.readObject();
		reportPeriodTo = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);
		output.writeObject(searchScopeType);

		output.writeObject(stkymdFrom);
		output.writeObject(stkymdTo);
		output.writeObject(kadoymdFrom);
		output.writeObject(kadoymdTo);
		output.writeObject(jbkymdFrom);
		output.writeObject(jbkymdTo);
		output.writeObject(shuruinmPlural);
		output.writeObject(bunruinmPlural);
		output.writeObject(setchinmPlural);
		output.writeObject(itemGroupNamePlural);
		output.writeObject(slipNumPlural);
		output.writeObject(applicationIdPlural);

		output.writeObject(assetId);
		output.writeObject(assetIdPlural);
		output.writeObject(licenseId);
		output.writeObject(licenseIdPlural);
		output.writeObject(shisanNumPlural);

		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(holSectionName);

		output.writeObject(costSectionCode0);
		output.writeObject(costSectionName0);
		output.writeObject(costSectionCode1);
		output.writeObject(costSectionName1);
		output.writeObject(costSectionCode2);
		output.writeObject(costSectionName2);
		output.writeObject(costSectionCode3);
		output.writeObject(costSectionName3);
		output.writeObject(costSectionCode4);
		output.writeObject(costSectionName4);
		output.writeObject(costSectionCode5);
		output.writeObject(costSectionName5);
		output.writeObject(costSectionCode6);
		output.writeObject(costSectionName6);
		output.writeObject(costSectionCode7);
		output.writeObject(costSectionName7);
		output.writeObject(costSectionCode8);
		output.writeObject(costSectionName8);
		output.writeObject(costSectionCode9);
		output.writeObject(costSectionName9);

//		現物情報(無形拡張情報)の検索条件
		output.writeObject(holStaffCodeI);
		output.writeObject(holStaffNameI);
		output.writeObject(holStaffCompanyCodeI);
		output.writeObject(holStaffSectionCodeI);
		output.writeObject(holStaffSectionYearI);
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


		output.writeObject(dscAttribute);

		//	現物情報(有形固定)の検索条件
		output.writeObject(holStaffCodeT);
		output.writeObject(holStaffNameT);
		output.writeObject(holOfficeCode);
		output.writeObject(holOfficeFloor);
		output.writeObject(holOfficeRoomNum);
		output.writeObject(holOfficeRackNum);
		output.writeObject(useStaffCode);
		output.writeObject(useStaffCompanyCode );
		output.writeObject(useStaffSectionCode);
		output.writeObject(useStaffSectionYear);
		output.writeObject(holOfficePlural);

		output.writeObject(includeSectionHierarchyFlag);
		output.writeObject(holOfficeName);
		output.writeObject(useStaffName);

		output.writeObject(relationShisanNumFlag);

		output.writeObject(astSystemSectionDeployFlag);
		output.writeObject(astManageTypePlural);

		/****** 管理帳票用 ******/
		output.writeObject(reportDistType);
		output.writeObject(reportSchUnit);
		output.writeObject(reportSchCalcBasePeriod);
		output.writeObject(reportSchCalcYear);
		output.writeObject(reportLedgerType);
		output.writeObject(reportPeriod);
		output.writeObject(reportPeriodFrom);
		output.writeObject(reportPeriodTo);

	}

	/**
	 * stkymdFromを取得します。
	 * @return stkymdFrom
	 */
	public Date getStkymdFrom() {
		return stkymdFrom;
	}

	/**
	 * stkymdFrom
	 * @param stkymdFromを設定します。
	 */
	public void setStkymdFrom(Date stkymdFrom) {
		this.stkymdFrom = stkymdFrom;
	}

	/**
	 * stkymdToを取得します。
	 * @return stkymdTo
	 */
	public Date getStkymdTo() {
		return stkymdTo;
	}

	/**
	 * stkymdTo
	 * @param stkymdToを設定します。
	 */
	public void setStkymdTo(Date stkymdTo) {
		this.stkymdTo = stkymdTo;
	}

	/**
	 * kadoymdFromを取得します。
	 * @return kadoymdFrom
	 */
	public Date getKadoymdFrom() {
		return kadoymdFrom;
	}

	/**
	 * kadoymdFrom
	 * @param kadoymdFromを設定します。
	 */
	public void setKadoymdFrom(Date kadoymdFrom) {
		this.kadoymdFrom = kadoymdFrom;
	}

	/**
	 * kadoymdToを取得します。
	 * @return kadoymdTo
	 */
	public Date getKadoymdTo() {
		return kadoymdTo;
	}

	/**
	 * kadoymdTo
	 * @param kadoymdToを設定します。
	 */
	public void setKadoymdTo(Date kadoymdTo) {
		this.kadoymdTo = kadoymdTo;
	}

	/**
	 * jbkymdFromを取得します。
	 * @return jbkymdFrom
	 */
	public Date getJbkymdFrom() {
		return jbkymdFrom;
	}

	/**
	 * jbkymdFrom
	 * @param jbkymdFromを設定します。
	 */
	public void setJbkymdFrom(Date jbkymdFrom) {
		this.jbkymdFrom = jbkymdFrom;
	}

	/**
	 * jbkymdToを取得します。
	 * @return jbkymdTo
	 */
	public Date getJbkymdTo() {
		return jbkymdTo;
	}

	/**
	 * jbkymdTo
	 * @param jbkymdToを設定します。
	 */
	public void setJbkymdTo(Date jbkymdTo) {
		this.jbkymdTo = jbkymdTo;
	}

	/**
	 * searchScopeTypeを取得します。
	 * @return searchScopeType
	 */
	public String getSearchScopeType() {
		return searchScopeType;
	}

	/**
	 * searchScopeType
	 * @param searchScopeTypeを設定します。
	 */
	public void setSearchScopeType(String searchScopeType) {
		this.searchScopeType = searchScopeType;
	}

	/**
	 * assetIdを取得します。
	 * @return assetId
	 */
	public String getAssetId() {
		return assetId;
	}

	/**
	 * assetId
	 * @param assetIdを設定します。
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}


	/**
	 * licenseIdを取得します。
	 * @return licenseId
	 */
	public String getLicenseId() {
		return licenseId;
	}

	/**
	 * licenseId
	 * @param licenseIdを設定します。
	 */
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
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
	 * holStaffCodeIを取得します。
	 * @return holStaffCodeI
	 */
	public String getHolStaffCodeI() {
		return holStaffCodeI;
	}

	/**
	 * holStaffCodeI
	 * @param holStaffCodeIを設定します。
	 */
	public void setHolStaffCodeI(String holStaffCodeI) {
		this.holStaffCodeI = holStaffCodeI;
	}

	/**
	 * holStaffCompanyCodeIを取得します。
	 * @return holStaffCompanyCodeI
	 */
	public String getHolStaffCompanyCodeI() {
		return holStaffCompanyCodeI;
	}

	/**
	 * holStaffCompanyCodeI
	 * @param holStaffCompanyCodeIを設定します。
	 */
	public void setHolStaffCompanyCodeI(String holStaffCompanyCodeI) {
		this.holStaffCompanyCodeI = holStaffCompanyCodeI;
	}

	/**
	 * holStaffSectionCodeIを取得します。
	 * @return holStaffSectionCodeI
	 */
	public String getHolStaffSectionCodeI() {
		return holStaffSectionCodeI;
	}

	/**
	 * holStaffSectionCodeI
	 * @param holStaffSectionCodeIを設定します。
	 */
	public void setHolStaffSectionCodeI(String holStaffSectionCodeI) {
		this.holStaffSectionCodeI = holStaffSectionCodeI;
	}

	/**
	 * holStaffSectionYearIを取得します。
	 * @return holStaffSectionYearI
	 */
	public Integer getHolStaffSectionYearI() {
		return holStaffSectionYearI;
	}

	/**
	 * holStaffSectionYearI
	 * @param holStaffSectionYearIを設定します。
	 */
	public void setHolStaffSectionYearI(Integer holStaffSectionYearI) {
		this.holStaffSectionYearI = holStaffSectionYearI;
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
	 * dscAttributeを取得します。
	 * @return dscAttribute
	 */
	public String getDscAttribute() {
		return dscAttribute;
	}

	/**
	 * dscAttribute
	 * @param dscAttributeを設定します。
	 */
	public void setDscAttribute(String dscAttribute) {
		this.dscAttribute = dscAttribute;
	}

	/**
	 * holStaffCodeTを取得します。
	 * @return holStaffCodeT
	 */
	public String getHolStaffCodeT() {
		return holStaffCodeT;
	}

	/**
	 * holStaffCodeT
	 * @param holStaffCodeTを設定します。
	 */
	public void setHolStaffCodeT(String holStaffCodeT) {
		this.holStaffCodeT = holStaffCodeT;
	}

	/**
	 * holStaffNameTを取得します。
	 * @return holStaffNameT
	 */
	public String getHolStaffNameT() {
		return holStaffNameT;
	}

	/**
	 * holStaffNameT
	 * @param holStaffNameTを設定します。
	 */
	public void setHolStaffNameT(String holStaffNameT) {
		this.holStaffNameT = holStaffNameT;
	}

	/**
	 * holOfficeCodeを取得します。
	 * @return holOfficeCode
	 */
	public String getHolOfficeCode() {
		return holOfficeCode;
	}

	/**
	 * holOfficeCode
	 * @param holOfficeCodeを設定します。
	 */
	public void setHolOfficeCode(String holOfficeCode) {
		this.holOfficeCode = holOfficeCode;
	}

	/**
	 * holOfficeFloorを取得します。
	 * @return holOfficeFloor
	 */
	public Integer getHolOfficeFloor() {
		return holOfficeFloor;
	}

	/**
	 * holOfficeFloor
	 * @param holOfficeFloorを設定します。
	 */
	public void setHolOfficeFloor(Integer holOfficeFloor) {
		this.holOfficeFloor = holOfficeFloor;
	}

	/**
	 * holOfficeRoomNumを取得します。
	 * @return holOfficeRoomNum
	 */
	public String getHolOfficeRoomNum() {
		return holOfficeRoomNum;
	}

	/**
	 * holOfficeRoomNum
	 * @param holOfficeRoomNumを設定します。
	 */
	public void setHolOfficeRoomNum(String holOfficeRoomNum) {
		this.holOfficeRoomNum = holOfficeRoomNum;
	}

	/**
	 * holOfficeRackNumを取得します。
	 * @return holOfficeRackNum
	 */
	public String getHolOfficeRackNum() {
		return holOfficeRackNum;
	}

	/**
	 * holOfficeRackNum
	 * @param holOfficeRackNumを設定します。
	 */
	public void setHolOfficeRackNum(String holOfficeRackNum) {
		this.holOfficeRackNum = holOfficeRackNum;
	}

	/**
	 * useStaffCodeを取得します。
	 * @return useStaffCode
	 */
	public String getUseStaffCode() {
		return useStaffCode;
	}

	/**
	 * useStaffCode
	 * @param useStaffCodeを設定します。
	 */
	public void setUseStaffCode(String useStaffCode) {
		this.useStaffCode = useStaffCode;
	}

	/**
	 * useStaffCompanyCodeを取得します。
	 * @return useStaffCompanyCode
	 */
	public String getUseStaffCompanyCode() {
		return useStaffCompanyCode;
	}

	/**
	 * useStaffCompanyCode
	 * @param useStaffCompanyCodeを設定します。
	 */
	public void setUseStaffCompanyCode(String useStaffCompanyCode) {
		this.useStaffCompanyCode = useStaffCompanyCode;
	}

	/**
	 * useStaffSectionCodeを取得します。
	 * @return useStaffSectionCode
	 */
	public String getUseStaffSectionCode() {
		return useStaffSectionCode;
	}

	/**
	 * useStaffSectionCode
	 * @param useStaffSectionCodeを設定します。
	 */
	public void setUseStaffSectionCode(String useStaffSectionCode) {
		this.useStaffSectionCode = useStaffSectionCode;
	}

	/**
	 * useStaffSectionYearを取得します。
	 * @return useStaffSectionYear
	 */
	public Integer getUseStaffSectionYear() {
		return useStaffSectionYear;
	}

	/**
	 * useStaffSectionYear
	 * @param useStaffSectionYearを設定します。
	 */
	public void setUseStaffSectionYear(Integer useStaffSectionYear) {
		this.useStaffSectionYear = useStaffSectionYear;
	}

	/**
	 * holOfficePluralを取得します。
	 * @return holOfficePlural
	 */
	public String getHolOfficePlural() {
		return holOfficePlural;
	}

	/**
	 * holOfficePlural
	 * @param holOfficePluralを設定します。
	 */
	public void setHolOfficePlural(String holOfficePlural) {
		this.holOfficePlural = holOfficePlural;
	}

	/**
	 * includeSectionHierarchyFlagを取得します。
	 * @return includeSectionHierarchyFlag
	 */
	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	/**
	 * includeSectionHierarchyFlag
	 * @param includeSectionHierarchyFlagを設定します。
	 */
	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}

	/**
	 * holOfficeNameを取得します。
	 * @return holOfficeName
	 */
	public String getHolOfficeName() {
		return holOfficeName;
	}

	/**
	 * holOfficeName
	 * @param holOfficeNameを設定します。
	 */
	public void setHolOfficeName(String holOfficeName) {
		this.holOfficeName = holOfficeName;
	}

	/**
	 * useStaffNameを取得します。
	 * @return useStaffName
	 */
	public String getUseStaffName() {
		return useStaffName;
	}

	/**
	 * useStaffName
	 * @param useStaffNameを設定します。
	 */
	public void setUseStaffName(String useStaffName) {
		this.useStaffName = useStaffName;
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
	 * shuruinmPluralを取得します。
	 * @return shuruinmPlural
	 */
	public String getShuruinmPlural() {
		return shuruinmPlural;
	}

	/**
	 * shuruinmPlural
	 * @param shuruinmPluralを設定します。
	 */
	public void setShuruinmPlural(String shuruinmPlural) {
		this.shuruinmPlural = shuruinmPlural;
	}

	/**
	 * bunruinmPluralを取得します。
	 * @return bunruinmPlural
	 */
	public String getBunruinmPlural() {
		return bunruinmPlural;
	}

	/**
	 * bunruinmPlural
	 * @param bunruinmPluralを設定します。
	 */
	public void setBunruinmPlural(String bunruinmPlural) {
		this.bunruinmPlural = bunruinmPlural;
	}

	/**
	 * setchinmPluralを取得します。
	 * @return setchinmPlural
	 */
	public String getSetchinmPlural() {
		return setchinmPlural;
	}

	/**
	 * setchinmPlural
	 * @param setchinmPluralを設定します。
	 */
	public void setSetchinmPlural(String setchinmPlural) {
		this.setchinmPlural = setchinmPlural;
	}

	/**
	 * itemGroupNamePluralを取得します。
	 * @return itemGroupNamePlural
	 */
	public String getItemGroupNamePlural() {
		return itemGroupNamePlural;
	}

	/**
	 * itemGroupNamePlural
	 * @param itemGroupNamePluralを設定します。
	 */
	public void setItemGroupNamePlural(String itemGroupNamePlural) {
		this.itemGroupNamePlural = itemGroupNamePlural;
	}

	/**
	 * slipNumPluralを取得します。
	 * @return slipNumPlural
	 */
	public String getSlipNumPlural() {
		return slipNumPlural;
	}

	/**
	 * slipNumPlural
	 * @param slipNumPluralを設定します。
	 */
	public void setSlipNumPlural(String slipNumPlural) {
		this.slipNumPlural = slipNumPlural;
	}

	/**
	 * applicationIdPluralを取得します。
	 * @return applicationIdPlural
	 */
	public String getApplicationIdPlural() {
		return applicationIdPlural;
	}

	/**
	 * applicationIdPlural
	 * @param applicationIdPluralを設定します。
	 */
	public void setApplicationIdPlural(String applicationIdPlural) {
		this.applicationIdPlural = applicationIdPlural;
	}

	/**
	 * assetIdPluralを取得します。
	 * @return assetIdPlural
	 */
	public String getAssetIdPlural() {
		return assetIdPlural;
	}

	/**
	 * assetIdPlural
	 * @param assetIdPluralを設定します。
	 */
	public void setAssetIdPlural(String assetIdPlural) {
		this.assetIdPlural = assetIdPlural;
	}

	/**
	 * licenseIdPluralを取得します。
	 * @return licenseIdPlural
	 */
	public String getLicenseIdPlural() {
		return licenseIdPlural;
	}

	/**
	 * licenseIdPlural
	 * @param licenseIdPluralを設定します。
	 */
	public void setLicenseIdPlural(String licenseIdPlural) {
		this.licenseIdPlural = licenseIdPlural;
	}

	/**
	 * shisanNumPluralを取得します。
	 * @return shisanNumPlural
	 */
	public String getShisanNumPlural() {
		return shisanNumPlural;
	}

	/**
	 * shisanNumPlural
	 * @param shisanNumPluralを設定します。
	 */
	public void setShisanNumPlural(String shisanNumPlural) {
		this.shisanNumPlural = shisanNumPlural;
	}

	/**
	 * holStaffNameIを取得します。
	 * @return holStaffNameI
	 */
	public String getHolStaffNameI() {
		return holStaffNameI;
	}

	/**
	 * holStaffNameI
	 * @param holStaffNameIを設定します。
	 */
	public void setHolStaffNameI(String holStaffNameI) {
		this.holStaffNameI = holStaffNameI;
	}

	/**
	 * @return the reportDistType
	 */
	public String getReportDistType() {
		return reportDistType;
	}

	/**
	 * @param reportDistType the reportDistType to set
	 */
	public void setReportDistType(String reportDistType) {
		this.reportDistType = reportDistType;
	}

	/**
	 * @return the reportSchUnit
	 */
	public String getReportSchUnit() {
		return reportSchUnit;
	}

	/**
	 * @param reportSchUnit the reportSchUnit to set
	 */
	public void setReportSchUnit(String reportSchUnit) {
		this.reportSchUnit = reportSchUnit;
	}

	/**
	 * @return the reportLedgerType
	 */
	public String getReportLedgerType() {
		return reportLedgerType;
	}

	/**
	 * @param reportLedgerType the reportLedgerType to set
	 */
	public void setReportLedgerType(String reportLedgerType) {
		this.reportLedgerType = reportLedgerType;
	}

	/**
	 * @return the reportPeriodFrom
	 */
	public String getReportPeriodFrom() {
		return reportPeriodFrom;
	}

	/**
	 * @param reportPeriodFrom the reportPeriodFrom to set
	 */
	public void setReportPeriodFrom(String reportPeriodFrom) {
		this.reportPeriodFrom = reportPeriodFrom;
	}

	/**
	 * @return the reportPeriodTo
	 */
	public String getReportPeriodTo() {
		return reportPeriodTo;
	}

	/**
	 * @param reportPeriodTo the reportPeriodTo to set
	 */
	public void setReportPeriodTo(String reportPeriodTo) {
		this.reportPeriodTo = reportPeriodTo;
	}

	/**
	 * @return the reportPeriod
	 */
	public String getReportPeriod() {
		return reportPeriod;
	}

	/**
	 * @param reportPeriod the reportPeriod to set
	 */
	public void setReportPeriod(String reportPeriod) {
		this.reportPeriod = reportPeriod;
	}

	/**
	 * @return the reportSchCalcBasePeriod
	 */
	public String getReportSchCalcBasePeriod() {
		return reportSchCalcBasePeriod;
	}

	/**
	 * @param reportSchCalcBasePeriod the reportSchCalcBasePeriod to set
	 */
	public void setReportSchCalcBasePeriod(String reportSchCalcBasePeriod) {
		this.reportSchCalcBasePeriod = reportSchCalcBasePeriod;
	}

	/**
	 * @return the reportSchCalcYear
	 */
	public Integer getReportSchCalcYear() {
		return reportSchCalcYear;
	}

	/**
	 * @param reportSchCalcYear the reportSchCalcYear to set
	 */
	public void setReportSchCalcYear(Integer reportSchCalcYear) {
		this.reportSchCalcYear = reportSchCalcYear;
	}

	/**
	 * @return the relationShisanNumFlag
	 */
	public String getRelationShisanNumFlag() {
		return relationShisanNumFlag;
	}

	/**
	 * @param relationShisanNumFlag the relationShisanNumFlag to set
	 */
	public void setRelationShisanNumFlag(String relationShisanNumFlag) {
		this.relationShisanNumFlag = relationShisanNumFlag;
	}

	/**
	 * @return the astSystemSectionDeployFlag
	 */
	public String getAstSystemSectionDeployFlag() {
		return astSystemSectionDeployFlag;
	}

	/**
	 * @param astSystemSectionDeployFlag the astSystemSectionDeployFlag to set
	 */
	public void setAstSystemSectionDeployFlag(String astSystemSectionDeployFlag) {
		this.astSystemSectionDeployFlag = astSystemSectionDeployFlag;
	}

	/**
	 * @return the astManageTypePlural
	 */
	public String getAstManageTypePlural() {
		return astManageTypePlural;
	}

	/**
	 * @param astManageTypePlural the astManageTypePlural to set
	 */
	public void setAstManageTypePlural(String astManageTypePlural) {
		this.astManageTypePlural = astManageTypePlural;
	}

	public String getCostSectionCode0() {
		return costSectionCode0;
	}

	public void setCostSectionCode0(String costSectionCode0) {
		this.costSectionCode0 = costSectionCode0;
	}

	public String getCostSectionName0() {
		return costSectionName0;
	}

	public void setCostSectionName0(String costSectionName0) {
		this.costSectionName0 = costSectionName0;
	}

	public String getCostSectionCode1() {
		return costSectionCode1;
	}

	public void setCostSectionCode1(String costSectionCode1) {
		this.costSectionCode1 = costSectionCode1;
	}

	public String getCostSectionName1() {
		return costSectionName1;
	}

	public void setCostSectionName1(String costSectionName1) {
		this.costSectionName1 = costSectionName1;
	}

	public String getCostSectionCode2() {
		return costSectionCode2;
	}

	public void setCostSectionCode2(String costSectionCode2) {
		this.costSectionCode2 = costSectionCode2;
	}

	public String getCostSectionName2() {
		return costSectionName2;
	}

	public void setCostSectionName2(String costSectionName2) {
		this.costSectionName2 = costSectionName2;
	}

	public String getCostSectionCode3() {
		return costSectionCode3;
	}

	public void setCostSectionCode3(String costSectionCode3) {
		this.costSectionCode3 = costSectionCode3;
	}

	public String getCostSectionName3() {
		return costSectionName3;
	}

	public void setCostSectionName3(String costSectionName3) {
		this.costSectionName3 = costSectionName3;
	}

	public String getCostSectionCode4() {
		return costSectionCode4;
	}

	public void setCostSectionCode4(String costSectionCode4) {
		this.costSectionCode4 = costSectionCode4;
	}

	public String getCostSectionName4() {
		return costSectionName4;
	}

	public void setCostSectionName4(String costSectionName4) {
		this.costSectionName4 = costSectionName4;
	}

	public String getCostSectionCode5() {
		return costSectionCode5;
	}

	public void setCostSectionCode5(String costSectionCode5) {
		this.costSectionCode5 = costSectionCode5;
	}

	public String getCostSectionName5() {
		return costSectionName5;
	}

	public void setCostSectionName5(String costSectionName5) {
		this.costSectionName5 = costSectionName5;
	}

	public String getCostSectionCode6() {
		return costSectionCode6;
	}

	public void setCostSectionCode6(String costSectionCode6) {
		this.costSectionCode6 = costSectionCode6;
	}

	public String getCostSectionName6() {
		return costSectionName6;
	}

	public void setCostSectionName6(String costSectionName6) {
		this.costSectionName6 = costSectionName6;
	}

	public String getCostSectionCode7() {
		return costSectionCode7;
	}

	public void setCostSectionCode7(String costSectionCode7) {
		this.costSectionCode7 = costSectionCode7;
	}

	public String getCostSectionName7() {
		return costSectionName7;
	}

	public void setCostSectionName7(String costSectionName7) {
		this.costSectionName7 = costSectionName7;
	}

	public String getCostSectionCode8() {
		return costSectionCode8;
	}

	public void setCostSectionCode8(String costSectionCode8) {
		this.costSectionCode8 = costSectionCode8;
	}

	public String getCostSectionName8() {
		return costSectionName8;
	}

	public void setCostSectionName8(String costSectionName8) {
		this.costSectionName8 = costSectionName8;
	}

	public String getCostSectionCode9() {
		return costSectionCode9;
	}

	public void setCostSectionCode9(String costSectionCode9) {
		this.costSectionCode9 = costSectionCode9;
	}

	public String getCostSectionName9() {
		return costSectionName9;
	}

	public void setCostSectionName9(String costSectionName9) {
		this.costSectionName9 = costSectionName9;
	}

}
