/*===================================================================
 * ファイル名 : LicenseAlloc.java
 * 概要説明   : ソフトウェアデータクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-25 1.0  リヨン 崔     新規
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
public class LicenseAlloc implements Serializable {
	private static final long serialVersionUID = 1L;

	private String licenseId; // ライセンス管理番号
	private Long licenseLineQtyId; // ライセンス数量管理明細ID
	private String assetId; // 情報機器管理番号
	private Long assetLineOsId; // 情報機器等_OS明細ID
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号

	private Integer lineSeq;	//	行番号
	private String osName;	//	OS名
	private String softwareMakerName;	//	ソフトウェアメーカー名
	private String softwareName;	//	ソフトウェア名
	private String licLicenseKey;	//	ライセンスキー

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		licenseId = (String)input.readObject();
		licenseLineQtyId = Function.getExternalLong(input.readObject());
		assetId = (String)input.readObject();
		assetLineOsId = Function.getExternalLong(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();

		lineSeq = Function.getExternalInteger(input.readObject());
		osName = (String)input.readObject();
		softwareMakerName = (String)input.readObject();
		softwareName = (String)input.readObject();
		licLicenseKey = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		output.writeObject(licenseId);
		output.writeObject(licenseLineQtyId);
		output.writeObject(assetId);
		output.writeObject(assetLineOsId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);

		output.writeObject(lineSeq);
		output.writeObject(osName);
		output.writeObject(softwareMakerName);
		output.writeObject(softwareName);
		output.writeObject(licLicenseKey);


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
	 * licenseLineQtyIdを取得します。
	 * @return licenseLineQtyId
	 */
	public Long getLicenseLineQtyId() {
		return licenseLineQtyId;
	}

	/**
	 * licenseLineQtyId
	 * @param licenseLineQtyIdを設定します。
	 */
	public void setLicenseLineQtyId(Long licenseLineQtyId) {
		this.licenseLineQtyId = licenseLineQtyId;
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
	 * assetLineOsIdを取得します。
	 * @return assetLineOsId
	 */
	public Long getAssetLineOsId() {
		return assetLineOsId;
	}

	/**
	 * assetLineOsId
	 * @param assetLineOsIdを設定します。
	 */
	public void setAssetLineOsId(Long assetLineOsId) {
		this.assetLineOsId = assetLineOsId;
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
	 * osNameを取得します。
	 * @return osName
	 */
	public String getOsName() {
		return osName;
	}

	/**
	 * osName
	 * @param osNameを設定します。
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}

	/**
	 * softwareMakerNameを取得します。
	 * @return softwareMakerName
	 */
	public String getSoftwareMakerName() {
		return softwareMakerName;
	}

	/**
	 * softwareMakerName
	 * @param softwareMakerNameを設定します。
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
	 * licLicenseKeyを取得します。
	 * @return licLicenseKey
	 */
	public String getLicLicenseKey() {
		return licLicenseKey;
	}

	/**
	 * licLicenseKey
	 * @param licLicenseKeyを設定します。
	 */
	public void setLicLicenseKey(String licLicenseKey) {
		this.licLicenseKey = licLicenseKey;
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

}
