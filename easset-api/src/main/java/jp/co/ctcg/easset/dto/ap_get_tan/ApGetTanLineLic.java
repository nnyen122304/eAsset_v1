/*===================================================================
 * ファイル名 : ApGetTanLineLic.java
 * 概要説明   : 取得申請(有形)_ライセンス明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-18 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_get_tan;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApGetTanLineLic extends LineData {
	private static final long serialVersionUID = 1L;

	private Long apGetTanLineLicId; // ライセンス明細識別番号
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private Long softwareMakerId; // ソフトウエアメーカーID
	private String softwareMakerName; // ソフトウエアメーカー名
	private Long softwareId; // ソフトウエアID
	private String softwareName; // ソフトウエア名
	private Integer quantity; // 数量
	private Integer registQuantity; // 登録数量
	private String licQuantityType; // ライセンス数区分 1:有限,2:無限
	private String licQuantityTypeName; // ライセンス数区分名
	private Long licQuantity; // ライセンス数

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		apGetTanLineLicId = Function.getExternalLong(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		softwareMakerId = Function.getExternalLong(input.readObject());
		softwareMakerName = (String)input.readObject();
		softwareId = Function.getExternalLong(input.readObject());
		softwareName = (String)input.readObject();
		quantity = Function.getExternalInteger(input.readObject());
		registQuantity = Function.getExternalInteger(input.readObject());
		licQuantityType = (String)input.readObject();
		licQuantityTypeName = (String)input.readObject();
		licQuantity = Function.getExternalLong(input.readObject());
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(apGetTanLineLicId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(softwareMakerId);
		output.writeObject(softwareMakerName);
		output.writeObject(softwareId);
		output.writeObject(softwareName);
		output.writeObject(quantity);
		output.writeObject(registQuantity);
		output.writeObject(licQuantityType);
		output.writeObject(licQuantityTypeName);
		output.writeObject(licQuantity);
	}

	/**
	 * apGetTanLineLicIdを取得します。
	 * @return apGetTanLineLicId
	 */
	public Long getApGetTanLineLicId() {
		return apGetTanLineLicId;
	}

	/**
	 * apGetTanLineLicId
	 * @param apGetTanLineLicIdを設定します。
	 */
	public void setApGetTanLineLicId(Long apGetTanLineLicId) {
		this.apGetTanLineLicId = apGetTanLineLicId;
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
	 * quantityを取得します。
	 * @return quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * quantity
	 * @param quantityを設定します。
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * registQuantityを取得します。
	 * @return registQuantity
	 */
	public Integer getRegistQuantity() {
		return registQuantity;
	}

	/**
	 * registQuantity
	 * @param registQuantityを設定します。
	 */
	public void setRegistQuantity(Integer registQuantity) {
		this.registQuantity = registQuantity;
	}

	/**
	 * licQuantityTypeを取得します。
	 * @return licQuantityType
	 */
	public String getLicQuantityType() {
		return licQuantityType;
	}

	/**
	 * licQuantityType
	 * @param licQuantityTypeを設定します。
	 */
	public void setLicQuantityType(String licQuantityType) {
		this.licQuantityType = licQuantityType;
	}

	/**
	 * licQuantityを取得します。
	 * @return licQuantity
	 */
	public Long getLicQuantity() {
		return licQuantity;
	}

	/**
	 * licQuantity
	 * @param licQuantityを設定します。
	 */
	public void setLicQuantity(Long licQuantity) {
		this.licQuantity = licQuantity;
	}

	public String getLicQuantityTypeName() {
		return licQuantityTypeName;
	}

	public void setLicQuantityTypeName(String licQuantityTypeName) {
		this.licQuantityTypeName = licQuantityTypeName;
	}

}
