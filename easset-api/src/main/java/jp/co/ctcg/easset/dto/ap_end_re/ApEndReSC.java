/*===================================================================
 * ファイル名 : ApEndReSR.java
 * 概要説明   : レンタル満了・解約申請検索条件
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2017-11-06 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_re;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class ApEndReSC extends ApEndRe {
	private static final long serialVersionUID = 1L;

	private String applicationIdPlural; // 申請書番号複数
	private Date apDateFrom; // 申請日From
	private Date apDateTo; // 申請日To
	private String sameContractApStatus; // 同一契約物件申請状態 1.申請書未作成物件有り 2.未申請物件有り 3.申請中物件有り 4.全物件承認済
	private String sameContractApStatusPlural; // 同一契約物件申請状態複数
	private String eappIdPlural; // e申請書類ID複数

	// 申請内容
	private String apEndReTypePlural; // 申請区分複数
	private Date returnDateFrom; // 返却予定日From
	private Date returnDateTo; // 返却予定日To
	private Long totalAmountFrom; // 残レンタル料/再レンタル金額From
	private Long totalAmountTo; // 残レンタル料/再レンタル金額To

	// 管理番号
	private String contractNumPlural; // 契約番号複数
	private String contractSubNumPlural; // 契約枝番複数
	private String astNumPlural; // 資産番号複数
	private String assetIdPlural; // 情報機器管理番号複数
	private String licenseIdPlural; // ﾗｲｾﾝｽ管理番号複数

	// 契約・経費
	private Date endDateFrom; // レンタル満了日From
	private Date endDateTo; // レンタル満了日To

	// 現物情報
	private String holCompanyCode; // 保有会社コード
	private String holCompanyName; // 保有会社
	private String holSectionCode; // 保有/使用部署コード
	private String holSectionName; // 保有/使用部署名
	private String includeSectionHierarchyFlag;	//	部署階層検索フラグ
	private String holStaffCode; // 資産管理担当者コード
	private String holStaffName; // 資産管理担当者

	//	バリデーション用(readExternal, writeExternalには定義しない)
	private String notApplicationId;

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		applicationIdPlural = (String)input.readObject(); // 申請書番号複数
		apDateFrom = (Date)input.readObject(); // 申請日From
		apDateTo = (Date)input.readObject(); // 申請日To
		sameContractApStatus = (String)input.readObject(); // 同一契約物件申請状態
		sameContractApStatusPlural = (String)input.readObject(); // 同一契約物件申請状態複数
		eappIdPlural = (String)input.readObject();

		// 申請内容
		apEndReTypePlural = (String)input.readObject(); // 申請区分複数
		returnDateFrom = (Date)input.readObject(); // 返却予定日From
		returnDateTo = (Date)input.readObject(); // 返却予定日To
		totalAmountFrom = Function.getExternalLong(input.readObject()); // 残レンタル料/再レンタル金額From
		totalAmountTo = Function.getExternalLong(input.readObject()); // 残レンタル料/再レンタル金額To

		// 管理番号
		contractNumPlural = (String)input.readObject(); // 契約番号複数
		contractSubNumPlural = (String)input.readObject(); // 契約枝番複数
		astNumPlural = (String)input.readObject(); // 資産番号複数
		assetIdPlural = (String)input.readObject(); // 情報機器管理番号複数
		licenseIdPlural = (String)input.readObject(); // ﾗｲｾﾝｽ管理番号複数

		// 契約・経費
		endDateFrom = (Date)input.readObject(); // レンタル満了日From
		endDateTo = (Date)input.readObject(); // レンタル満了日To

		// 現物情報
		holCompanyCode = (String)input.readObject(); // 保有会社コード
		holCompanyName = (String)input.readObject(); // 保有会社
		holSectionCode = (String)input.readObject(); // 保有/使用部署コード
		holSectionName = (String)input.readObject(); // 保有/使用部署名
		includeSectionHierarchyFlag = (String)input.readObject();	//	部署階層検索フラグ
		holStaffCode = (String)input.readObject(); // 資産管理担当者コード
		holStaffName = (String)input.readObject(); // 資産管理担当者

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(applicationIdPlural); // 申請書番号複数
		output.writeObject(apDateFrom); // 申請日From
		output.writeObject(apDateTo); // 申請日To
		output.writeObject(sameContractApStatus); // 同一契約物件申請状態
		output.writeObject(sameContractApStatusPlural); // 同一契約物件申請状態複数
		output.writeObject(eappIdPlural);

		// 申請内容
		output.writeObject(apEndReTypePlural); // 申請区分複数
		output.writeObject(returnDateFrom); // 返却予定日From
		output.writeObject(returnDateTo); // 返却予定日To
		output.writeObject(totalAmountFrom); // 残レンタル料/再レンタル金額From
		output.writeObject(totalAmountTo); // 残レンタル料/再レンタル金額To

		// 管理番号
		output.writeObject(contractNumPlural); // 契約番号複数
		output.writeObject(contractSubNumPlural); // 契約枝番複数
		output.writeObject(astNumPlural); // 資産番号複数
		output.writeObject(assetIdPlural); // 情報機器管理番号複数
		output.writeObject(licenseIdPlural); // ﾗｲｾﾝｽ管理番号複数

		// 契約・経費
		output.writeObject(endDateFrom); // レンタル満了日From
		output.writeObject(endDateTo); // レンタル満了日To

		// 現物情報
		output.writeObject(holCompanyCode); // 保有会社コード
		output.writeObject(holCompanyName); // 保有会社
		output.writeObject(holSectionCode); // 保有/使用部署コード
		output.writeObject(holSectionName); // 保有/使用部署名
		output.writeObject(includeSectionHierarchyFlag);	//	部署階層検索フラグ
		output.writeObject(holStaffCode); // 資産管理担当者コード
		output.writeObject(holStaffName); // 資産管理担当者

	}

	public String getApplicationIdPlural() {
		return applicationIdPlural;
	}

	public void setApplicationIdPlural(String applicationIdPlural) {
		this.applicationIdPlural = applicationIdPlural;
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

	public String getSameContractApStatus() {
		return sameContractApStatus;
	}

	public void setSameContractApStatus(String sameContractApStatus) {
		this.sameContractApStatus = sameContractApStatus;
	}

	public Date getReturnDateFrom() {
		return returnDateFrom;
	}

	public void setReturnDateFrom(Date returnDateFrom) {
		this.returnDateFrom = returnDateFrom;
	}

	public Date getReturnDateTo() {
		return returnDateTo;
	}

	public void setReturnDateTo(Date returnDateTo) {
		this.returnDateTo = returnDateTo;
	}

	public Long getTotalAmountFrom() {
		return totalAmountFrom;
	}

	public void setTotalAmountFrom(Long totalAmountFrom) {
		this.totalAmountFrom = totalAmountFrom;
	}

	public Long getTotalAmountTo() {
		return totalAmountTo;
	}

	public void setTotalAmountTo(Long totalAmountTo) {
		this.totalAmountTo = totalAmountTo;
	}

	public String getContractNumPlural() {
		return contractNumPlural;
	}

	public void setContractNumPlural(String contractNumPlural) {
		this.contractNumPlural = contractNumPlural;
	}

	public String getContractSubNumPlural() {
		return contractSubNumPlural;
	}

	public void setContractSubNumPlural(String contractSubNumPlural) {
		this.contractSubNumPlural = contractSubNumPlural;
	}

	public String getAstNumPlural() {
		return astNumPlural;
	}

	public void setAstNumPlural(String astNumPlural) {
		this.astNumPlural = astNumPlural;
	}

	public String getAssetIdPlural() {
		return assetIdPlural;
	}

	public void setAssetIdPlural(String assetIdPlural) {
		this.assetIdPlural = assetIdPlural;
	}

	public String getLicenseIdPlural() {
		return licenseIdPlural;
	}

	public void setLicenseIdPlural(String licenseIdPlural) {
		this.licenseIdPlural = licenseIdPlural;
	}

	public String getHolCompanyCode() {
		return holCompanyCode;
	}

	public void setHolCompanyCode(String holCompanyCode) {
		this.holCompanyCode = holCompanyCode;
	}

	public String getHolCompanyName() {
		return holCompanyName;
	}

	public void setHolCompanyName(String holCompanyName) {
		this.holCompanyName = holCompanyName;
	}

	public String getHolSectionCode() {
		return holSectionCode;
	}

	public void setHolSectionCode(String holSectionCode) {
		this.holSectionCode = holSectionCode;
	}

	public String getHolSectionName() {
		return holSectionName;
	}

	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}

	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}

	public String getHolStaffCode() {
		return holStaffCode;
	}

	public void setHolStaffCode(String holStaffCode) {
		this.holStaffCode = holStaffCode;
	}

	public String getHolStaffName() {
		return holStaffName;
	}

	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getNotApplicationId() {
		return notApplicationId;
	}

	public void setNotApplicationId(String notApplicationId) {
		this.notApplicationId = notApplicationId;
	}

	public String getSameContractApStatusPlural() {
		return sameContractApStatusPlural;
	}

	public void setSameContractApStatusPlural(String sameContractApStatusPlural) {
		this.sameContractApStatusPlural = sameContractApStatusPlural;
	}

	public String getApEndReTypePlural() {
		return apEndReTypePlural;
	}

	public void setApEndReTypePlural(String apEndReTypePlural) {
		this.apEndReTypePlural = apEndReTypePlural;
	}

	public String getEappIdPlural() {
		return eappIdPlural;
	}

	public void setEappIdPlural(String eappIdPlural) {
		this.eappIdPlural = eappIdPlural;
	}

	public Date getEndDateFrom() {
		return endDateFrom;
	}

	public void setEndDateFrom(Date endDateFrom) {
		this.endDateFrom = endDateFrom;
	}

	public Date getEndDateTo() {
		return endDateTo;
	}

	public void setEndDateTo(Date endDateTo) {
		this.endDateTo = endDateTo;
	}

}
