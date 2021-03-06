/*===================================================================
 * ファイル名 : ApChangeLineLic.java
 * 概要説明   : 移動申請_ライセンス明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-02 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_change;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApChangeLineLic implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String licenseId; // ライセンス管理番号
	private String holCompanyCode; // 保有会社コード
	private String holSectionCode; // 保有部署コード
	private Integer holSectionYear; // 保有部署年度
	private String holStaffCode; // 資産管理担当者
	private Long softwareMakerId; // ソフトウェアメーカーID
	private Long softwareId; // ソフトウェアID
	private String licAssetType; // 資産区分(ライセンス) 汎用マスタ-LIC_ASSET_TYPE
	private String contractNum; // 契約番号
	private String autoAddFlag; // 自動追加フラグ

	private String licQuantityType; // ライセンス数タイプ 1:有限,2:無限
	private Long licQuantity; // ライセンス保有数
	private Long ppId; // 固有番号(資産) ProPlusの資産台帳キー
	private String astNum; // 資産番号
	private String getApplicationId; // 取得申請書番号
	private String contractSubNum; // 契約枝番

	private String holSectionName;  // 保有部署コード
	private String holStaffName;  // 資産管理担当者
	private String licAssetTypeName;  // 資産区分名(ライセンス) 汎用マスタ-LIC_ASSET_TYPE
	private String softwareMakerName; // ソフトウェアメーカー名
	private String softwareName; // ソフトウェア名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		licenseId = (String)input.readObject();
		holCompanyCode = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holStaffCode = (String)input.readObject();
		softwareMakerId = Function.getExternalLong(input.readObject());
		softwareId = Function.getExternalLong(input.readObject());
		licAssetType = (String)input.readObject();
		contractNum = (String)input.readObject();
		autoAddFlag = (String)input.readObject();

		licQuantityType = (String)input.readObject();
		licQuantity = Function.getExternalLong(input.readObject());
		ppId = Function.getExternalLong(input.readObject());
		astNum = (String)input.readObject();
		getApplicationId = (String)input.readObject();
		contractSubNum = (String)input.readObject();

		holSectionName = (String)input.readObject();
		holStaffName = (String)input.readObject();
		licAssetTypeName = (String)input.readObject();
		softwareMakerName = (String)input.readObject();
		softwareName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(licenseId);
		output.writeObject(holCompanyCode);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(holStaffCode);
		output.writeObject(softwareMakerId);
		output.writeObject(softwareId);
		output.writeObject(licAssetType);
		output.writeObject(contractNum);
		output.writeObject(autoAddFlag);

		output.writeObject(licQuantityType);
		output.writeObject(licQuantity);
		output.writeObject(ppId);
		output.writeObject(astNum);
		output.writeObject(getApplicationId);
		output.writeObject(contractSubNum);

		output.writeObject(holSectionName);
		output.writeObject(holStaffName);
		output.writeObject(licAssetTypeName);
		output.writeObject(softwareMakerName);
		output.writeObject(softwareName);

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
	 * lineSeqを取得します。
	 * @return lineSeq
	 */
	public Integer getLineSeq() {
		return lineSeq;
	}

	/**
	 * lineSeq
	 * @param lineSeqを設定します。
	 */
	public void setLineSeq(Integer lineSeq) {
		this.lineSeq = lineSeq;
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
	 * softwareMakerIdを取得します。
	 * @return softwareMakerId
	 */
	public Long getSoftwareMakerId() {
		return softwareMakerId;
	}

	/**
	 * softwareMakerId
	 * @param softwareMakerIdを設定します。
	 */
	public void setSoftwareMakerId(Long softwareMakerId) {
		this.softwareMakerId = softwareMakerId;
	}

	/**
	 * softwareIdを取得します。
	 * @return softwareId
	 */
	public Long getSoftwareId() {
		return softwareId;
	}

	/**
	 * softwareId
	 * @param softwareIdを設定します。
	 */
	public void setSoftwareId(Long softwareId) {
		this.softwareId = softwareId;
	}

	/**
	 * licAssetTypeを取得します。
	 * @return licAssetType
	 */
	public String getLicAssetType() {
		return licAssetType;
	}

	/**
	 * licAssetType
	 * @param licAssetTypeを設定します。
	 */
	public void setLicAssetType(String licAssetType) {
		this.licAssetType = licAssetType;
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
	 * licAssetTypeNameを取得します。
	 * @return licAssetTypeName
	 */
	public String getLicAssetTypeName() {
		return licAssetTypeName;
	}

	/**
	 * licAssetTypeName
	 * @param licAssetTypeNameを設定します。
	 */
	public void setLicAssetTypeName(String licAssetTypeName) {
		this.licAssetTypeName = licAssetTypeName;
	}

	/**
	 * softwareMakerIdNameを取得します。
	 * @return softwareMakerIdName
	 */
	public String getSoftwareMakerName() {
		return softwareMakerName;
	}

	/**
	 * softwareMakerIdName
	 * @param softwareMakerIdNameを設定します。
	 */
	public void setSoftwareMakerName(String softwareMakerName) {
		this.softwareMakerName = softwareMakerName;
	}

	/**
	 * softwareNameを取得します。
	 * @return softwareName
	 */
	public String getSoftwareName() {
		return softwareName;
	}

	/**
	 * softwareName
	 * @param softwareNameを設定します。
	 */
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	/**
	 * @return the autoAddFlag
	 */
	public String getAutoAddFlag() {
		return autoAddFlag;
	}

	/**
	 * @param autoAddFlag the autoAddFlag to set
	 */
	public void setAutoAddFlag(String autoAddFlag) {
		this.autoAddFlag = autoAddFlag;
	}

	public String getLicQuantityType() {
		return licQuantityType;
	}

	public void setLicQuantityType(String licQuantityType) {
		this.licQuantityType = licQuantityType;
	}

	public Long getLicQuantity() {
		return licQuantity;
	}

	public void setLicQuantity(Long licQuantity) {
		this.licQuantity = licQuantity;
	}

	public Long getPpId() {
		return ppId;
	}

	public void setPpId(Long ppId) {
		this.ppId = ppId;
	}

	public String getAstNum() {
		return astNum;
	}

	public void setAstNum(String astNum) {
		this.astNum = astNum;
	}

	public String getGetApplicationId() {
		return getApplicationId;
	}

	public void setGetApplicationId(String getApplicationId) {
		this.getApplicationId = getApplicationId;
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

}
