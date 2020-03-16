/*===================================================================
 * ファイル名 : ApChangeSC.java
 * 概要説明   : 取得申請(有形)検索結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-04 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_change;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import lombok.ToString;

@ToString(callSuper = true)
public class ApChangeSC extends ApChange {
	private static final long serialVersionUID = 1L;

	private String applicationIdPlural; // 申請書番号複数
	private Date apDateFrom; // 申請日From
	private Date apDateTo; // 申請日To
	private String assetId; // 情報機器管理番号
	private String assetIdPlural; // 情報機器管理番号複数
	private String licenseId; // ライセンス管理番号
	private String licenseIdPlural; //  ライセンス管理番号複数
	private String contractNum;  // 契約番号
	private String contractNumPlural; // 契約番号複数
	private String apprCostStaffCode; // 経費負担部課課長
	private String apprCostStaffName; // 経費負担部課課長名
	private String includeSectionHierarchyFlag; // 部署階層検索フラグ

	private String astAssetType; // 資産区分
	private String astAssetTypeName; // 資産区分名
	private String astManageType; // 管理区分
	private String astManageTypeName; // 管理区分名

	private String searchTarget;  // 検索対象

	private String eappIdPlural; // e申請書類ID複数

	private String contractSubNum;  // 契約枝番
	private String contractSubNumPlural; // 契約枝番複数
	private String astNum;  // 資産番号
	private String astNumPlural; // 資産番号複数

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		applicationIdPlural = (String)input.readObject();
		apDateFrom = (Date)input.readObject();
		apDateTo = (Date)input.readObject();
		assetId = (String)input.readObject();
		assetIdPlural = (String)input.readObject();
		licenseId = (String)input.readObject();
		licenseIdPlural = (String)input.readObject();
		contractNum = (String)input.readObject();
		contractNumPlural = (String)input.readObject();
		apprCostStaffCode = (String)input.readObject();
		apprCostStaffName = (String)input.readObject();
		includeSectionHierarchyFlag = (String)input.readObject();

		astAssetType = (String)input.readObject();
		astAssetTypeName = (String)input.readObject();
		astManageType = (String)input.readObject();
		astManageTypeName = (String)input.readObject();

		searchTarget = (String)input.readObject();

		eappIdPlural = (String)input.readObject();

		contractSubNum = (String)input.readObject();
		contractSubNumPlural = (String)input.readObject();
		astNum = (String)input.readObject();
		astNumPlural = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(applicationIdPlural);
		output.writeObject(apDateFrom);
		output.writeObject(apDateTo);
		output.writeObject(assetId);
		output.writeObject(assetIdPlural);
		output.writeObject(licenseId);
		output.writeObject(licenseIdPlural);
		output.writeObject(contractNum);
		output.writeObject(contractNumPlural);
		output.writeObject(apprCostStaffCode);
		output.writeObject(apprCostStaffName);
		output.writeObject(includeSectionHierarchyFlag);

		output.writeObject(astAssetType);
		output.writeObject(astAssetTypeName);
		output.writeObject(astManageType);
		output.writeObject(astManageTypeName);

		output.writeObject(searchTarget);

		output.writeObject(eappIdPlural);

		output.writeObject(contractSubNum);
		output.writeObject(contractSubNumPlural);
		output.writeObject(astNum);
		output.writeObject(astNumPlural);
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
	 * apDateFromを取得します。
	 * @return apDateFrom
	 */
	public Date getApDateFrom() {
		return apDateFrom;
	}

	/**
	 * apDateFrom
	 * @param apDateFromを設定します。
	 */
	public void setApDateFrom(Date apDateFrom) {
		this.apDateFrom = apDateFrom;
	}

	/**
	 * apDateToを取得します。
	 * @return apDateTo
	 */
	public Date getApDateTo() {
		return apDateTo;
	}

	/**
	 * apDateTo
	 * @param apDateToを設定します。
	 */
	public void setApDateTo(Date apDateTo) {
		this.apDateTo = apDateTo;
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
	 * contractNumを取得します。
	 * @return contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}

	/**
	 * contractNum
	 * @param contractNumを設定します。
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	/**
	 * contractNumPluralを取得します。
	 * @return contractNumPlural
	 */
	public String getContractNumPlural() {
		return contractNumPlural;
	}

	/**
	 * contractNumPlural
	 * @param contractNumPluralを設定します。
	 */
	public void setContractNumPlural(String contractNumPlural) {
		this.contractNumPlural = contractNumPlural;
	}

	/**
	 * apprCostStaffCodeを取得します。
	 * @return apprCostStaffCode
	 */
	public String getApprCostStaffCode() {
		return apprCostStaffCode;
	}

	/**
	 * apprCostStaffCode
	 * @param apprCostStaffCodeを設定します。
	 */
	public void setApprCostStaffCode(String apprCostStaffCode) {
		this.apprCostStaffCode = apprCostStaffCode;
	}

	/**
	 * apprCostStaffNameを取得します。
	 * @return apprCostStaffName
	 */
	public String getApprCostStaffName() {
		return apprCostStaffName;
	}

	/**
	 * apprCostStaffName
	 * @param apprCostStaffNameを設定します。
	 */
	public void setApprCostStaffName(String apprCostStaffName) {
		this.apprCostStaffName = apprCostStaffName;
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
	 * astAssetTypeを取得します。
	 * @return astAssetType
	 */
	public String getAstAssetType() {
		return astAssetType;
	}

	/**
	 * astAssetType
	 * @param astAssetTypeを設定します。
	 */
	public void setAstAssetType(String astAssetType) {
		this.astAssetType = astAssetType;
	}

	/**
	 * astAssetTypeNameを取得します。
	 * @return astAssetTypeName
	 */
	public String getAstAssetTypeName() {
		return astAssetTypeName;
	}

	/**
	 * astAssetTypeName
	 * @param astAssetTypeNameを設定します。
	 */
	public void setAstAssetTypeName(String astAssetTypeName) {
		this.astAssetTypeName = astAssetTypeName;
	}

	/**
	 * astManageTypeを取得します。
	 * @return astManageType
	 */
	public String getAstManageType() {
		return astManageType;
	}

	/**
	 * astManageType
	 * @param astManageTypeを設定します。
	 */
	public void setAstManageType(String astManageType) {
		this.astManageType = astManageType;
	}

	/**
	 * astManageTypeNameを取得します。
	 * @return astManageTypeName
	 */
	public String getAstManageTypeName() {
		return astManageTypeName;
	}

	/**
	 * astManageTypeName
	 * @param astManageTypeNameを設定します。
	 */
	public void setAstManageTypeName(String astManageTypeName) {
		this.astManageTypeName = astManageTypeName;
	}

	/**
	 * searchTargetを取得します。
	 * @return searchTarget
	 */
	public String getSearchTarget() {
		return searchTarget;
	}

	/**
	 * searchTarget
	 * @param searchTargetを設定します。
	 */
	public void setSearchTarget(String searchTarget) {
		this.searchTarget = searchTarget;
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
	 * @return the contractSubNum
	 */
	public String getContractSubNum() {
		return contractSubNum;
	}

	/**
	 * @param contractSubNum the contractSubNum to set
	 */
	public void setContractSubNum(String contractSubNum) {
		this.contractSubNum = contractSubNum;
	}

	/**
	 * @return the contractSubNumPlural
	 */
	public String getContractSubNumPlural() {
		return contractSubNumPlural;
	}

	/**
	 * @param contractSubNumPlural the contractSubNumPlural to set
	 */
	public void setContractSubNumPlural(String contractSubNumPlural) {
		this.contractSubNumPlural = contractSubNumPlural;
	}

	/**
	 * @return the astNum
	 */
	public String getAstNum() {
		return astNum;
	}

	/**
	 * @param astNum the astNum to set
	 */
	public void setAstNum(String astNum) {
		this.astNum = astNum;
	}

	/**
	 * @return the astNumPlural
	 */
	public String getAstNumPlural() {
		return astNumPlural;
	}

	/**
	 * @param astNumPlural the astNumPlural to set
	 */
	public void setAstNumPlural(String astNumPlural) {
		this.astNumPlural = astNumPlural;
	}


}
