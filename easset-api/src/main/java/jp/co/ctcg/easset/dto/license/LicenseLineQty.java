/*===================================================================
 * ファイル名 : LicenseLineQty.java
 * 概要説明   : ライセンス_数量管理明細情報
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-01-24 1.0  リヨン 李    新規
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
public class LicenseLineQty implements Serializable {
	private static final long serialVersionUID = 1L;

	private String licenseId;				// ライセンス管理番号
	private String licenseLineQtyType;		// ライセンス数量管理明細区分 1:許諾部署(1ライセンス：1明細),2:貸出部署(1ライセンス：N明細)
	private Integer lineSeq;				// 行シーケンス ライセンス管理番号,ライセンス数量管理明細区分ごとのSEQ
	private Date createDate;				// 作成日
	private String createStaffCode;			// 作成者社員番号
	private Date updateDate;				// 更新日
	private String updateStaffCode;			// 更新者社員番号
	private Long licenseLineQtyId;			// ライセンス数量管理明細ID
	private String useCompanyCode;			// 使用会社コード
	private String useCompanyName;			// 使用会社名
	private String useSectionCode;			// 使用部署コード
	private Integer useSectionYear;			// 使用部署年度
	private String useSectionName;			// 使用部署名
	private Long licLineEnableQuantity;		// ライセンス有効数(貸出数)
	private Long licLineUseQuantity;		// ライセンス消費数

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		licenseId = (String)input.readObject();
		licenseLineQtyType = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		licenseLineQtyId = Function.getExternalLong(input.readObject());
		useCompanyCode = (String)input.readObject();
		useCompanyName = (String)input.readObject();
		useSectionCode = (String)input.readObject();
		useSectionYear = Function.getExternalInteger(input.readObject());
		useSectionName = (String)input.readObject();
		licLineEnableQuantity = Function.getExternalLong(input.readObject());
		licLineUseQuantity = Function.getExternalLong(input.readObject());
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		output.writeObject(licenseId);
		output.writeObject(licenseLineQtyType);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(licenseLineQtyId);
		output.writeObject(useCompanyCode);
		output.writeObject(useCompanyName);
		output.writeObject(useSectionCode);
		output.writeObject(useSectionYear);
		output.writeObject(useSectionName);
		output.writeObject(licLineEnableQuantity);
		output.writeObject(licLineUseQuantity);
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
	 * licenseLineQtyTypeを取得します。
	 * @return licenseLineQtyType
	 */
	public String getLicenseLineQtyType() {
		return licenseLineQtyType;
	}
	/**
	 * licenseLineQtyType
	 * @param licenseLineQtyTypeを設定します。
	 */
	public void setLicenseLineQtyType(String licenseLineQtyType) {
		this.licenseLineQtyType = licenseLineQtyType;
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
	 * useCompanyCodeを取得します。
	 * @return useCompanyCode
	 */
	public String getUseCompanyCode() {
		return useCompanyCode;
	}
	/**
	 * useCompanyCode
	 * @param useCompanyCodeを設定します。
	 */
	public void setUseCompanyCode(String useCompanyCode) {
		this.useCompanyCode = useCompanyCode;
	}
	/**
	 * useSectionCodeを取得します。
	 * @return useSectionCode
	 */
	public String getUseSectionCode() {
		return useSectionCode;
	}
	/**
	 * useSectionCode
	 * @param useSectionCodeを設定します。
	 */
	public void setUseSectionCode(String useSectionCode) {
		this.useSectionCode = useSectionCode;
	}
	/**
	 * useSectionYearを取得します。
	 * @return useSectionYear
	 */
	public Integer getUseSectionYear() {
		return useSectionYear;
	}
	/**
	 * useSectionYear
	 * @param useSectionYearを設定します。
	 */
	public void setUseSectionYear(Integer useSectionYear) {
		this.useSectionYear = useSectionYear;
	}
	/**
	 * licLineEnableQuantityを取得します。
	 * @return licLineEnableQuantity
	 */
	public Long getLicLineEnableQuantity() {
		return licLineEnableQuantity;
	}
	/**
	 * licLineEnableQuantity
	 * @param licLineEnableQuantityを設定します。
	 */
	public void setLicLineEnableQuantity(Long licLineEnableQuantity) {
		this.licLineEnableQuantity = licLineEnableQuantity;
	}
	/**
	 * licLineUseQuantityを取得します。
	 * @return licLineUseQuantity
	 */
	public Long getLicLineUseQuantity() {
		return licLineUseQuantity;
	}
	/**
	 * licLineUseQuantity
	 * @param licLineUseQuantityを設定します。
	 */
	public void setLicLineUseQuantity(Long licLineUseQuantity) {
		this.licLineUseQuantity = licLineUseQuantity;
	}
	public String getUseCompanyName() {
		return useCompanyName;
	}
	public void setUseCompanyName(String useCompanyName) {
		this.useCompanyName = useCompanyName;
	}
	public String getUseSectionName() {
		return useSectionName;
	}
	public void setUseSectionName(String useSectionName) {
		this.useSectionName = useSectionName;
	}


}
