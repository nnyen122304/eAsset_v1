/*===================================================================
 * ファイル名 : LicenseLineUpg.java
 * 概要説明   : ライセンス_アップグレード明細情報
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-01-26 1.0  リヨン 申    新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.license;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class LicenseLineUpg implements Serializable {
	private static final long serialVersionUID = 1L;

	private String licenseId;				// ライセンス管理番号
	private String upgradeLicenseId;		// アップグレードライセンス管理番号
	private Integer lineSeq;				// 行シーケンス
	private Date createDate;				// 作成日
	private String createStaffCode;			// 作成者社員番号
	private Date updateDate;				// 更新日
	private String updateStaffCode;			// 更新者社員番号
	private Long licUpgradeQuantity;		// アップグレード数

	private String softwareMakerName;		// ソフトウェアメーカー名
	private String softwareName;			// ソフトウェア名
	private String licLicenseKey;			// ライセンスキー
	private Long licQuantity;				// ライセンス保有数
	private Long licEnableQuantity;			// ライセンス有効数
	private Long licUseQuantity;			// ライセンス消費数

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		licenseId = (String)input.readObject();
		upgradeLicenseId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		licUpgradeQuantity = Function.getExternalLong(input.readObject());

		softwareMakerName = (String)input.readObject();
		softwareName = (String)input.readObject();
		licLicenseKey = (String)input.readObject();
		licQuantity = Function.getExternalLong(input.readObject());
		licEnableQuantity = Function.getExternalLong(input.readObject());
		licUseQuantity = Function.getExternalLong(input.readObject());
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		output.writeObject(licenseId);
		output.writeObject(upgradeLicenseId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(licUpgradeQuantity);

		output.writeObject(softwareMakerName);
		output.writeObject(softwareName);
		output.writeObject(licLicenseKey);
		output.writeObject(licQuantity);
		output.writeObject(licEnableQuantity);
		output.writeObject(licUseQuantity);
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getUpgradeLicenseId() {
		return upgradeLicenseId;
	}

	public void setUpgradeLicenseId(String upgradeLicenseId) {
		this.upgradeLicenseId = upgradeLicenseId;
	}

	public Integer getLineSeq() {
		return lineSeq;
	}

	public void setLineSeq(Integer lineSeq) {
		this.lineSeq = lineSeq;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateStaffCode() {
		return createStaffCode;
	}

	public void setCreateStaffCode(String createStaffCode) {
		this.createStaffCode = createStaffCode;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getUpdateStaffCode() {
		return updateStaffCode;
	}

	public void setUpdateStaffCode(String updateStaffCode) {
		this.updateStaffCode = updateStaffCode;
	}

	public Long getLicUpgradeQuantity() {
		return licUpgradeQuantity;
	}

	public void setLicUpgradeQuantity(Long licUpgradeQuantity) {
		this.licUpgradeQuantity = licUpgradeQuantity;
	}

	public String getSoftwareMakerName() {
		return softwareMakerName;
	}

	public void setSoftwareMakerName(String softwareMakerName) {
		this.softwareMakerName = softwareMakerName;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getLicLicenseKey() {
		return licLicenseKey;
	}

	public void setLicLicenseKey(String licLicenseKey) {
		this.licLicenseKey = licLicenseKey;
	}

	public Long getLicQuantity() {
		return licQuantity;
	}

	public void setLicQuantity(Long licQuantity) {
		this.licQuantity = licQuantity;
	}

	public Long getLicEnableQuantity() {
		return licEnableQuantity;
	}

	public void setLicEnableQuantity(Long licEnableQuantity) {
		this.licEnableQuantity = licEnableQuantity;
	}

	public Long getLicUseQuantity() {
		return licUseQuantity;
	}

	public void setLicUseQuantity(Long licUseQuantity) {
		this.licUseQuantity = licUseQuantity;
	}

}
