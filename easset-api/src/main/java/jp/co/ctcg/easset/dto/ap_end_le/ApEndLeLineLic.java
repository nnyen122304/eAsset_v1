/*===================================================================
 * ファイル名 : ApEndLeLineLic.java
 * 概要説明   : リース満了・解約申請
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-12-21  1.0  高山         新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_le;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApEndLeLineLic extends LineData {
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
	private String licQuantityType; // ライセンス数タイプ 1:有限,2:無限
	private Long licQuantity; // ライセンス保有数
	private Long ppIdAst; // 固有番号(物件) ProPlusの物件台帳キー
	private String astNum; // 資産番号
	private String contractNum; // 契約番号
	private String contractSubNum; // 契約枝番
	private String deleteExecFlag; // 抹消実行フラグ 0:抹消実行無し,1:抹消実行有り

	//	名称
	private String holSectionName;	//	保有部署名
	private String holStaffName;	//	保有者名
	private String licAssetTypeName;	//	資産区分
	private String softwareMakerName;	//	ソフトウェアメーカー名
	private String softwareName;	//	ソフトウェア名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

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
		licQuantityType = (String)input.readObject();
		licQuantity = Function.getExternalLong(input.readObject());
		ppIdAst = Function.getExternalLong(input.readObject());
		astNum = (String)input.readObject();
		contractNum = (String)input.readObject();
		contractSubNum = (String)input.readObject();
		deleteExecFlag = (String)input.readObject();

		//	名称
		holSectionName = (String)input.readObject();
		holStaffName = (String)input.readObject();
		licAssetTypeName = (String)input.readObject();
		softwareMakerName = (String)input.readObject();
		softwareName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

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
		output.writeObject(licQuantityType);
		output.writeObject(licQuantity);
		output.writeObject(ppIdAst);
		output.writeObject(astNum);
		output.writeObject(contractNum);
		output.writeObject(contractSubNum);
		output.writeObject(deleteExecFlag);

		//	名称
		output.writeObject(holSectionName);
		output.writeObject(holStaffName);
		output.writeObject(licAssetTypeName);
		output.writeObject(softwareMakerName);
		output.writeObject(softwareName);

	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getHolCompanyCode() {
		return holCompanyCode;
	}

	public void setHolCompanyCode(String holCompanyCode) {
		this.holCompanyCode = holCompanyCode;
	}

	public String getHolSectionCode() {
		return holSectionCode;
	}

	public void setHolSectionCode(String holSectionCode) {
		this.holSectionCode = holSectionCode;
	}

	public Integer getHolSectionYear() {
		return holSectionYear;
	}

	public void setHolSectionYear(Integer holSectionYear) {
		this.holSectionYear = holSectionYear;
	}

	public String getHolStaffCode() {
		return holStaffCode;
	}

	public void setHolStaffCode(String holStaffCode) {
		this.holStaffCode = holStaffCode;
	}

	public Long getSoftwareMakerId() {
		return softwareMakerId;
	}

	public void setSoftwareMakerId(Long softwareMakerId) {
		this.softwareMakerId = softwareMakerId;
	}

	public Long getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(Long softwareId) {
		this.softwareId = softwareId;
	}

	public String getLicAssetType() {
		return licAssetType;
	}

	public void setLicAssetType(String licAssetType) {
		this.licAssetType = licAssetType;
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

	public Long getPpIdAst() {
		return ppIdAst;
	}

	public void setPpIdAst(Long ppIdAst) {
		this.ppIdAst = ppIdAst;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getContractSubNum() {
		return contractSubNum;
	}

	public void setContractSubNum(String contractSubNum) {
		this.contractSubNum = contractSubNum;
	}

	public String getDeleteExecFlag() {
		return deleteExecFlag;
	}

	public void setDeleteExecFlag(String deleteExecFlag) {
		this.deleteExecFlag = deleteExecFlag;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getHolSectionName() {
		return holSectionName;
	}

	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}

	public String getHolStaffName() {
		return holStaffName;
	}

	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}

	public String getLicAssetTypeName() {
		return licAssetTypeName;
	}

	public void setLicAssetTypeName(String licAssetTypeName) {
		this.licAssetTypeName = licAssetTypeName;
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

	public String getAstNum() {
		return astNum;
	}

	public void setAstNum(String astNum) {
		this.astNum = astNum;
	}
}