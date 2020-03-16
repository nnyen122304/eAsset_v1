/*===================================================================
 * ファイル名 : LicenseSR.java
 * 概要説明   : ライセンス検索結果
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

import lombok.ToString;

@ToString(callSuper = true)
public class LicenseSR extends License {
	private static final long serialVersionUID = 1L;

	private String licenseLicenseId;			// 登録したライセンス管理番号(登録申請のみ使用する)

	//	割当情報(機器から検索) ※ CSV作成のみのためFlexに連携する必要なし。GetterSetterは定義する。
	private String assetId;
	private String hwHolCompanyName;
	private String hwHolSectionName;
	private String astUseCompanyName;
	private String astUseSectionName;
	private String astHolStaffCode;
	private String astHolStaffName;
	private String useStaffCode;
	private String useStaffName;
	private String astManageTypeName;
	private Date astCreateDate;
	//	アップグレード情報 ※ CSV作成のみのためFlexに連携する必要なし。GetterSetterは定義する。
	private String upLicenseId;
	private String upSoftwareMakerName;
	private String upSoftwareName;
	private String upLicSerialCode;
	private String upLicProductKey;
	private String upLicLicenseKey;
	private String upLicQuantityTypeName;
	private Integer upLicQuantity;
	private Integer upLicEnableQuantity;
	private Integer upLicUpgradeToQuantity;
	private Integer upLicUpgradeQuantity;

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		licenseLicenseId = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(licenseLicenseId);
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
	 * hwHolCompanyNameを取得します。
	 * @return hwHolCompanyName
	 */
	public String getHwHolCompanyName() {
		return hwHolCompanyName;
	}

	/**
	 * hwHolCompanyName
	 * @param hwHolCompanyNameを設定します。
	 */
	public void setHwHolCompanyName(String hwHolCompanyName) {
		this.hwHolCompanyName = hwHolCompanyName;
	}

	/**
	 * hwHolSectionNameを取得します。
	 * @return hwHolSectionName
	 */
	public String getHwHolSectionName() {
		return hwHolSectionName;
	}

	/**
	 * hwHolSectionName
	 * @param hwHolSectionNameを設定します。
	 */
	public void setHwHolSectionName(String hwHolSectionName) {
		this.hwHolSectionName = hwHolSectionName;
	}

	/**
	 * astUseCompanyNameを取得します。
	 * @return astUseCompanyName
	 */
	public String getAstUseCompanyName() {
		return astUseCompanyName;
	}

	/**
	 * astUseCompanyName
	 * @param astUseCompanyNameを設定します。
	 */
	public void setAstUseCompanyName(String astUseCompanyName) {
		this.astUseCompanyName = astUseCompanyName;
	}

	/**
	 * astUseSectionNameを取得します。
	 * @return astUseSectionName
	 */
	public String getAstUseSectionName() {
		return astUseSectionName;
	}

	/**
	 * astUseSectionName
	 * @param astUseSectionNameを設定します。
	 */
	public void setAstUseSectionName(String astUseSectionName) {
		this.astUseSectionName = astUseSectionName;
	}

	/**
	 * astHolStaffCodeを取得します。
	 * @return astHolStaffCode
	 */
	public String getAstHolStaffCode() {
		return astHolStaffCode;
	}

	/**
	 * astHolStaffCode
	 * @param astHolStaffCodeを設定します。
	 */
	public void setAstHolStaffCode(String astHolStaffCode) {
		this.astHolStaffCode = astHolStaffCode;
	}

	/**
	 * astHolStaffNameを取得します。
	 * @return astHolStaffName
	 */
	public String getAstHolStaffName() {
		return astHolStaffName;
	}

	/**
	 * astHolStaffName
	 * @param astHolStaffNameを設定します。
	 */
	public void setAstHolStaffName(String astHolStaffName) {
		this.astHolStaffName = astHolStaffName;
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
	 * upLicenseIdを取得します。
	 * @return upLicenseId
	 */
	public String getUpLicenseId() {
		return upLicenseId;
	}

	/**
	 * upLicenseId
	 * @param upLicenseIdを設定します。
	 */
	public void setUpLicenseId(String upLicenseId) {
		this.upLicenseId = upLicenseId;
	}

	/**
	 * upSoftwareMakerNameを取得します。
	 * @return upSoftwareMakerName
	 */
	public String getUpSoftwareMakerName() {
		return upSoftwareMakerName;
	}

	/**
	 * upSoftwareMakerName
	 * @param upSoftwareMakerNameを設定します。
	 */
	public void setUpSoftwareMakerName(String upSoftwareMakerName) {
		this.upSoftwareMakerName = upSoftwareMakerName;
	}

	/**
	 * upSoftwareNameを取得します。
	 * @return upSoftwareName
	 */
	public String getUpSoftwareName() {
		return upSoftwareName;
	}

	/**
	 * upSoftwareName
	 * @param upSoftwareNameを設定します。
	 */
	public void setUpSoftwareName(String upSoftwareName) {
		this.upSoftwareName = upSoftwareName;
	}

	/**
	 * upLicSerialCodeを取得します。
	 * @return upLicSerialCode
	 */
	public String getUpLicSerialCode() {
		return upLicSerialCode;
	}

	/**
	 * upLicSerialCode
	 * @param upLicSerialCodeを設定します。
	 */
	public void setUpLicSerialCode(String upLicSerialCode) {
		this.upLicSerialCode = upLicSerialCode;
	}

	/**
	 * upLicProductKeyを取得します。
	 * @return upLicProductKey
	 */
	public String getUpLicProductKey() {
		return upLicProductKey;
	}

	/**
	 * upLicProductKey
	 * @param upLicProductKeyを設定します。
	 */
	public void setUpLicProductKey(String upLicProductKey) {
		this.upLicProductKey = upLicProductKey;
	}

	/**
	 * upLicLicenseKeyを取得します。
	 * @return upLicLicenseKey
	 */
	public String getUpLicLicenseKey() {
		return upLicLicenseKey;
	}

	/**
	 * upLicLicenseKey
	 * @param upLicLicenseKeyを設定します。
	 */
	public void setUpLicLicenseKey(String upLicLicenseKey) {
		this.upLicLicenseKey = upLicLicenseKey;
	}

	/**
	 * upLicQuantityを取得します。
	 * @return upLicQuantity
	 */
	public Integer getUpLicQuantity() {
		return upLicQuantity;
	}

	/**
	 * upLicQuantity
	 * @param upLicQuantityを設定します。
	 */
	public void setUpLicQuantity(Integer upLicQuantity) {
		this.upLicQuantity = upLicQuantity;
	}

	/**
	 * upLicEnableQuantityを取得します。
	 * @return upLicEnableQuantity
	 */
	public Integer getUpLicEnableQuantity() {
		return upLicEnableQuantity;
	}

	/**
	 * upLicEnableQuantity
	 * @param upLicEnableQuantityを設定します。
	 */
	public void setUpLicEnableQuantity(Integer upLicEnableQuantity) {
		this.upLicEnableQuantity = upLicEnableQuantity;
	}

	/**
	 * upLicUpgradeQuantityを取得します。
	 * @return upLicUpgradeQuantity
	 */
	public Integer getUpLicUpgradeQuantity() {
		return upLicUpgradeQuantity;
	}

	/**
	 * upLicUpgradeQuantity
	 * @param upLicUpgradeQuantityを設定します。
	 */
	public void setUpLicUpgradeQuantity(Integer upLicUpgradeQuantity) {
		this.upLicUpgradeQuantity = upLicUpgradeQuantity;
	}

	/**
	 * @return the upLicQuantityTypeName
	 */
	public String getUpLicQuantityTypeName() {
		return upLicQuantityTypeName;
	}

	/**
	 * @param upLicQuantityTypeName the upLicQuantityTypeName to set
	 */
	public void setUpLicQuantityTypeName(String upLicQuantityTypeName) {
		this.upLicQuantityTypeName = upLicQuantityTypeName;
	}

	/**
	 * @return the upLicUpgradeToQuantity
	 */
	public Integer getUpLicUpgradeToQuantity() {
		return upLicUpgradeToQuantity;
	}

	/**
	 * @param upLicUpgradeToQuantity the upLicUpgradeToQuantity to set
	 */
	public void setUpLicUpgradeToQuantity(Integer upLicUpgradeToQuantity) {
		this.upLicUpgradeToQuantity = upLicUpgradeToQuantity;
	}

	/**
	 * @return the licenseLicenseId
	 */
	public String getLicenseLicenseId() {
		return licenseLicenseId;
	}

	/**
	 * @param licenseLicenseId the licenseLicenseId to set
	 */
	public void setLicenseLicenseId(String licenseLicenseId) {
		this.licenseLicenseId = licenseLicenseId;
	}

	public String getAstManageTypeName() {
		return astManageTypeName;
	}

	public void setAstManageTypeName(String astManageTypeName) {
		this.astManageTypeName = astManageTypeName;
	}

	public Date getAstCreateDate() {
		return astCreateDate;
	}

	public void setAstCreateDate(Date astCreateDate) {
		this.astCreateDate = astCreateDate;
	}

}
