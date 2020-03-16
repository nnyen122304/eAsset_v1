/*===================================================================
 * ファイル名 : LicenseSC.java
 * 概要説明   : ライセンス検索条件
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-29 1.0  リヨン 申     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.license;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class LicenseSC extends License {

	private static final long serialVersionUID = 1L;

	private String searchScopeType;					// 検索対象（1:自分の管轄部署が保有（使用）部署に設定されている資産(機器)、2:全ての資産(機器)）
	private String holCompanyCodeALSection;			// 部署アクセス権限用保有部署

	private String licenseIdPlural;					// ライセンス管理番号複数
	private String assetId;							// 割当情報機器管理番号
	private String assetIdPlural;					// 割当情報機器管理番号複数
	private String contractNumPlural;				// 契約番号複数
	private String getApplicationIdPlural;			// 取得申請書番号複数
	private String registApplicationIdPlural;		// 登録申請書番号複数
	private Date registDateFrom;					// 登録日From
	private Date registDateTo;						// 登録日To

	private String licSerialCodePlural;				// シリアル番号複数
	private String licProductKeyPlural;				// プロダクトKEY複数
	private Long licAmountFrom;						// 取得費用From
	private Long licAmountTo;						// 取得費用To

	private String allCompanyFlag;					// 他会社保有の資産（機器）を検索する
	private String includeSectionHierarchyFlag;		// 部署階層検索フラグ

	private String licLicenseTypePlural;				// ライセンス形態複数

	private Date trmEndDateFrom;					// タームライセンス期間終了日From
	private Date trmEndDateTo;						// タームライセンス期間終了日To

	private String dscAttribute;					// 管理項目

	// ライセンス登録申請検索条件
	private Date apDateFrom;						// 申請日From
	private Date apDateTo;							// 申請日To
	private String notCompleteFlag;					// 登録が完了している申請書を除外

	// 割当検索条件
	private String assetHolSectionCode;				// 情報機器保有部署コード
	private String assetHolCompanyCode;				// 情報機器保有会社コード
	private Integer assetHolSectionYear;			// 情報機器保有部署年度

	// CSVダウンロード
	private String dowloadLineItem;					// 明細出力

	private Date trmAlertDateFrom;					// タームライセンス期限通知日From
	private Date trmAlertDateTo;					// タームライセンス期限通知日To

	private String eappIdPlural;					 // e申請書類ID複数

	// 固定資産、物件
	private String contractEdaPlural;				// 契約枝番複数
	private String shisanNumPlural;					// 資産番号複数
	private String relationShisanNumFlag;			// 関連資産も同時に検索する

	//	e申請検索判定フラグ
	private String searchEapp;


	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		searchScopeType = (String)input.readObject();
		holCompanyCodeALSection = (String)input.readObject();

		licenseIdPlural = (String)input.readObject();
		assetId = (String)input.readObject();
		assetIdPlural = (String)input.readObject();
		contractNumPlural = (String)input.readObject();
		getApplicationIdPlural = (String)input.readObject();
		registApplicationIdPlural = (String)input.readObject();
		registDateFrom = (Date)input.readObject();
		registDateTo = (Date)input.readObject();

		licSerialCodePlural = (String)input.readObject();
		licProductKeyPlural = (String)input.readObject();
		licAmountFrom = Function.getExternalLong(input.readObject());
		licAmountTo = Function.getExternalLong(input.readObject());

		allCompanyFlag = (String)input.readObject();
		includeSectionHierarchyFlag = (String)input.readObject();

		licLicenseTypePlural = (String)input.readObject();

		trmEndDateFrom = (Date)input.readObject();
		trmEndDateTo = (Date)input.readObject();

		dscAttribute = (String)input.readObject();

		apDateFrom = (Date)input.readObject();
		apDateTo = (Date)input.readObject();
		notCompleteFlag = (String)input.readObject();

		assetHolSectionCode = (String)input.readObject();
		assetHolCompanyCode = (String)input.readObject();
		assetHolSectionYear = Function.getExternalInteger(input.readObject());

		dowloadLineItem = (String)input.readObject();

		trmAlertDateFrom = (Date)input.readObject();
		trmAlertDateTo = (Date)input.readObject();

		eappIdPlural = (String)input.readObject();

		// 固定資産、物件
		contractEdaPlural = (String)input.readObject();
		shisanNumPlural = (String)input.readObject();
		relationShisanNumFlag = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(searchScopeType);
		output.writeObject(holCompanyCodeALSection);

		output.writeObject(licenseIdPlural);
		output.writeObject(assetId);
		output.writeObject(assetIdPlural);
		output.writeObject(contractNumPlural);
		output.writeObject(getApplicationIdPlural);
		output.writeObject(registApplicationIdPlural);
		output.writeObject(registDateFrom);
		output.writeObject(registDateTo);

		output.writeObject(licSerialCodePlural);
		output.writeObject(licProductKeyPlural);
		output.writeObject(licAmountFrom);
		output.writeObject(licAmountTo);

		output.writeObject(allCompanyFlag);
		output.writeObject(includeSectionHierarchyFlag);

		output.writeObject(licLicenseTypePlural);

		output.writeObject(trmEndDateFrom);
		output.writeObject(trmEndDateTo);

		output.writeObject(dscAttribute);

		output.writeObject(apDateFrom);
		output.writeObject(apDateTo);
		output.writeObject(notCompleteFlag);

		output.writeObject(assetHolSectionCode);
		output.writeObject(assetHolCompanyCode);
		output.writeObject(assetHolSectionYear);

		output.writeObject(dowloadLineItem);

		output.writeObject(trmAlertDateFrom);
		output.writeObject(trmAlertDateTo);

		output.writeObject(eappIdPlural);

		// 固定資産、物件
		output.writeObject(contractEdaPlural);
		output.writeObject(shisanNumPlural);
		output.writeObject(relationShisanNumFlag);
	}

	public String getSearchScopeType() {
		return searchScopeType;
	}

	public void setSearchScopeType(String searchScopeType) {
		this.searchScopeType = searchScopeType;
	}

	public String getHolCompanyCodeALSection() {
		return holCompanyCodeALSection;
	}

	public void setHolCompanyCodeALSection(String holCompanyCodeALSection) {
		this.holCompanyCodeALSection = holCompanyCodeALSection;
	}

	public String getLicenseIdPlural() {
		return licenseIdPlural;
	}

	public void setLicenseIdPlural(String licenseIdPlural) {
		this.licenseIdPlural = licenseIdPlural;
	}

	public String getAssetIdPlural() {
		return assetIdPlural;
	}

	public void setAssetIdPlural(String assetIdPlural) {
		this.assetIdPlural = assetIdPlural;
	}

	public String getContractNumPlural() {
		return contractNumPlural;
	}

	public void setContractNumPlural(String contractNumPlural) {
		this.contractNumPlural = contractNumPlural;
	}

	public String getGetApplicationIdPlural() {
		return getApplicationIdPlural;
	}

	public void setGetApplicationIdPlural(String getApplicationIdPlural) {
		this.getApplicationIdPlural = getApplicationIdPlural;
	}

	public String getRegistApplicationIdPlural() {
		return registApplicationIdPlural;
	}

	public void setRegistApplicationIdPlural(String registApplicationIdPlural) {
		this.registApplicationIdPlural = registApplicationIdPlural;
	}

	public Date getRegistDateFrom() {
		return registDateFrom;
	}

	public void setRegistDateFrom(Date registDateFrom) {
		this.registDateFrom = registDateFrom;
	}

	public Date getRegistDateTo() {
		return registDateTo;
	}

	public void setRegistDateTo(Date registDateTo) {
		this.registDateTo = registDateTo;
	}

	public String getLicSerialCodePlural() {
		return licSerialCodePlural;
	}

	public void setLicSerialCodePlural(String licSerialCodePlural) {
		this.licSerialCodePlural = licSerialCodePlural;
	}

	public String getLicProductKeyPlural() {
		return licProductKeyPlural;
	}

	public void setLicProductKeyPlural(String licProductKeyPlural) {
		this.licProductKeyPlural = licProductKeyPlural;
	}

	public Long getLicAmountFrom() {
		return licAmountFrom;
	}

	public void setLicAmountFrom(Long licAmountFrom) {
		this.licAmountFrom = licAmountFrom;
	}

	public Long getLicAmountTo() {
		return licAmountTo;
	}

	public void setLicAmountTo(Long licAmountTo) {
		this.licAmountTo = licAmountTo;
	}

	public String getAllCompanyFlag() {
		return allCompanyFlag;
	}

	public void setAllCompanyFlag(String allCompanyFlag) {
		this.allCompanyFlag = allCompanyFlag;
	}

	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}

	public String getDscAttribute() {
		return dscAttribute;
	}

	public void setDscAttribute(String dscAttribute) {
		this.dscAttribute = dscAttribute;
	}

	public Date getTrmEndDateFrom() {
		return trmEndDateFrom;
	}

	public void setTrmEndDateFrom(Date trmEndDateFrom) {
		this.trmEndDateFrom = trmEndDateFrom;
	}

	public Date getTrmEndDateTo() {
		return trmEndDateTo;
	}

	public void setTrmEndDateTo(Date trmEndDateTo) {
		this.trmEndDateTo = trmEndDateTo;
	}

	public String getDowloadLineItem() {
		return dowloadLineItem;
	}

	public void setDowloadLineItem(String dowloadLineItem) {
		this.dowloadLineItem = dowloadLineItem;
	}

	public Date getApDateFrom() {
		return apDateFrom;
	}

	public void setApDateFrom(Date apDateFrom) {
		this.apDateFrom = apDateFrom;
	}

	public Date getApDateTo() {
		return apDateTo;
	}

	public void setApDateTo(Date apDateTo) {
		this.apDateTo = apDateTo;
	}

	public String getNotCompleteFlag() {
		return notCompleteFlag;
	}

	public void setNotCompleteFlag(String notCompleteFlag) {
		this.notCompleteFlag = notCompleteFlag;
	}

	/**
	 * assetHolSectionCodeを取得します。
	 * @return assetHolSectionCode
	 */
	public String getAssetHolSectionCode() {
		return assetHolSectionCode;
	}

	/**
	 * assetHolSectionCode
	 * @param assetHolSectionCodeを設定します。
	 */
	public void setAssetHolSectionCode(String assetHolSectionCode) {
		this.assetHolSectionCode = assetHolSectionCode;
	}

	/**
	 * assetHolCompanyCodeを取得します。
	 * @return assetHolCompanyCode
	 */
	public String getAssetHolCompanyCode() {
		return assetHolCompanyCode;
	}

	/**
	 * assetHolCompanyCode
	 * @param assetHolCompanyCodeを設定します。
	 */
	public void setAssetHolCompanyCode(String assetHolCompanyCode) {
		this.assetHolCompanyCode = assetHolCompanyCode;
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
	 * assetHolSectionYearを取得します。
	 * @return assetHolSectionYear
	 */
	public Integer getAssetHolSectionYear() {
		return assetHolSectionYear;
	}

	/**
	 * assetHolSectionYear
	 * @param assetHolSectionYearを設定します。
	 */
	public void setAssetHolSectionYear(Integer assetHolSectionYear) {
		this.assetHolSectionYear = assetHolSectionYear;
	}

	public Date getTrmAlertDateFrom() {
		return trmAlertDateFrom;
	}

	public void setTrmAlertDateFrom(Date trmAlertDateFrom) {
		this.trmAlertDateFrom = trmAlertDateFrom;
	}

	public Date getTrmAlertDateTo() {
		return trmAlertDateTo;
	}

	public void setTrmAlertDateTo(Date trmAlertDateTo) {
		this.trmAlertDateTo = trmAlertDateTo;
	}

	public String getLicLicenseTypePlural() {
		return licLicenseTypePlural;
	}

	public void setLicLicenseTypePlural(String licLicenseTypePlural) {
		this.licLicenseTypePlural = licLicenseTypePlural;
	}

	/**
	 * eappIdPluralを取得します。
	 * @return eappIdPlural
	 */
	public String getEappIdPlural() {
		return eappIdPlural;
	}

	/**
	 * eappIdPlural
	 * @param eappIdPluralを設定します。
	 */
	public void setEappIdPlural(String eappIdPlural) {
		this.eappIdPlural = eappIdPlural;
	}

	/**
	 * searchEappを取得します。
	 * @return searchEapp
	 */
	public String getSearchEapp() {
		return searchEapp;
	}

	/**
	 * searchEapp
	 * @param searchEappを設定します。
	 */
	public void setSearchEapp(String searchEapp) {
		this.searchEapp = searchEapp;
	}

	public String getContractEdaPlural() {
		return contractEdaPlural;
	}

	public void setContractEdaPlural(String contractEdaPlural) {
		this.contractEdaPlural = contractEdaPlural;
	}

	public String getRelationShisanNumFlag() {
		return relationShisanNumFlag;
	}

	public void setRelationShisanNumFlag(String relationShisanNumFlag) {
		this.relationShisanNumFlag = relationShisanNumFlag;
	}

	public String getShisanNumPlural() {
		return shisanNumPlural;
	}

	public void setShisanNumPlural(String shisanNumPlural) {
		this.shisanNumPlural = shisanNumPlural;
	}

}
