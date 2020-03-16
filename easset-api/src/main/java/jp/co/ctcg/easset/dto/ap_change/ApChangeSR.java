/*===================================================================
 * ファイル名 : ApGetTanSR.java
 * 概要説明   : 移動申請検索結果
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
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class ApChangeSR extends ApChange {
	private static final long serialVersionUID = 1L;

	private String assetId; // 情報機器管理番号
	private String astName; // 資産(機器)名

	private String licenseId; // ライセンス管理番号
	private Integer softwareId; // ソフトウェアID
	private String softwareName; // ソフトウェア名

	private String astNum; // 資産番号
	private String astNameFld; // 資産名
	private String contractNum; // 契約番号
	private String contractSubNum; // 契約枝番
	private String astNameContract; // 物件名

	private String costTypeName; // 販売管理費/原価区分名

	private String costSectionNameOld; // 移動元経費負担部署
	private String holCompanyNameOld; // 移動元保有会社
	private String holSectionNameOld; // 移動元保有部署

	private String costSectionNameNew; // 移動先経費負担部署


	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		assetId = (String)input.readObject();
		astName = (String)input.readObject();

		licenseId = (String)input.readObject();
		softwareId = Function.getExternalInteger(input.readObject());
		softwareName = (String)input.readObject();

		astNum = (String)input.readObject();
		astNameFld = (String)input.readObject();
		contractNum = (String)input.readObject();
		contractSubNum = (String)input.readObject();
		astNameContract = (String)input.readObject();

		costTypeName = (String)input.readObject();

		costSectionNameOld = (String)input.readObject();

		holCompanyNameOld = (String)input.readObject();
		holSectionNameOld = (String)input.readObject();

		costSectionNameNew = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(assetId);
		output.writeObject(astName);

		output.writeObject(licenseId);
		output.writeObject(softwareId);
		output.writeObject(softwareName);

		output.writeObject(astNum);
		output.writeObject(astNameFld);
		output.writeObject(contractNum);
		output.writeObject(contractSubNum);
		output.writeObject(astNameContract);

		output.writeObject(costTypeName);

		output.writeObject(costSectionNameOld);

		output.writeObject(holCompanyNameOld);
		output.writeObject(holSectionNameOld);

		output.writeObject(costSectionNameNew);

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
	 * astNameを取得します。
	 * @return astName
	 */
	public String getAstName() {
		return astName;
	}

	/**
	 * astName
	 * @param astNameを設定します。
	 */
	public void setAstName(String astName) {
		this.astName = astName;
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
	 * softwareIdを取得します。
	 * @return softwareId
	 */
	public Integer getSoftwareId() {
		return softwareId;
	}

	/**
	 * softwareId
	 * @param softwareIdを設定します。
	 */
	public void setSoftwareId(Integer softwareId) {
		this.softwareId = softwareId;
	}

	/**
	 * costTypeNameを取得します。
	 * @return costTypeName
	 */
	public String getCostTypeName() {
		return costTypeName;
	}

	/**
	 * costTypeName
	 * @param costTypeNameを設定します。
	 */
	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	/**
	 * holSectionNameOldを取得します。
	 * @return holSectionNameOld
	 */
	public String getHolSectionNameOld() {
		return holSectionNameOld;
	}

	/**
	 * holSectionNameOld
	 * @param holSectionNameOldを設定します。
	 */
	public void setHolSectionNameOld(String holSectionNameOld) {
		this.holSectionNameOld = holSectionNameOld;
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
	 * costSectionNameOldを取得します。
	 * @return costSectionNameOld
	 */
	public String getCostSectionNameOld() {
		return costSectionNameOld;
	}

	/**
	 * costSectionNameOld
	 * @param costSectionNameOldを設定します。
	 */
	public void setCostSectionNameOld(String costSectionNameOld) {
		this.costSectionNameOld = costSectionNameOld;
	}

	/**
	 * costSectionNameNewを取得します。
	 * @return costSectionNameNew
	 */
	public String getCostSectionNameNew() {
		return costSectionNameNew;
	}

	/**
	 * costSectionNameNew
	 * @param costSectionNameNewを設定します。
	 */
	public void setCostSectionNameNew(String costSectionNameNew) {
		this.costSectionNameNew = costSectionNameNew;
	}

	public String getHolCompanyNameOld() {
		return holCompanyNameOld;
	}

	public void setHolCompanyNameOld(String holCompanyNameOld) {
		this.holCompanyNameOld = holCompanyNameOld;
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
	 * @return the astNameFld
	 */
	public String getAstNameFld() {
		return astNameFld;
	}

	/**
	 * @param astNameFld the astNameFld to set
	 */
	public void setAstNameFld(String astNameFld) {
		this.astNameFld = astNameFld;
	}

	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}

	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
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
	 * @return the astNameContract
	 */
	public String getAstNameContract() {
		return astNameContract;
	}

	/**
	 * @param astNameContract the astNameContract to set
	 */
	public void setAstNameContract(String astNameContract) {
		this.astNameContract = astNameContract;
	}

}
