/*===================================================================
 * ファイル名 : ApEndTanSC.java
 * 概要説明   : 除売却申請検索条件
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-26 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_tan;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import lombok.ToString;

@ToString(callSuper = true)
public class ApEndTanSC extends ApEndTan {
	private static final long serialVersionUID = 1L;

	private Date apDateFrom;	//	申請日(From)
	private Date apDateTo;	//	申請日(To)
	private Date reportDateFrom;	//	除却報告日(From)
	private Date reportDateTo;	//	除却報告日(To)
	private String applicationIdPlural; // 申請書番号複数
	private String eappIdPlural; // e申請書類ID複数
	//	資産
	private String costType;	//	販売管理費/原価区分 1:販売管理費,2:原価
	private String costDepPrjCode;	//	減価償却プロジェクトコード
	private String costDepPrjName;	//	減価償却プロジェクト名

	//	管理番号
	private String assetIdPlural;	//	情報機器管理番号複数
	private String licenseIdPlural;	//	ﾗｲｾﾝｽ管理番号複数
	private String getApplicationId;	//	取得申請書番号
	private String getApplicationIdPlural;	//	取得申請書番号複数
	private String astNumPlural;	//	資産番号複数

	//	現物情報
	private String holCompanyCode;	///	保有部署コード
	private String holCompanyName;	///	保有部署名
	private String holSectionCode;	//	保有/使用部署コード
	private String holSectionName;	//	保有/使用部署名
	private String includeSectionHierarchyFlag; // 部署階層検索フラグ
	private String holStaffCode;	//	資産管理担当者コード
	private String holStaffName;	//	資産管理担当者
	private String holStaffCodeI;	//	無形管理担当者社員番号
	private String holStaffNameI;	//	無形管理担当者名

	private Date disposeDateTo;		//	廃棄・売却予定日(To)

	//	バリデーション用(readExternal, writeExternalには定義しない)
	private String notApplicationId;

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		apDateFrom = (Date)input.readObject();
		apDateTo = (Date)input.readObject();
		reportDateFrom = (Date)input.readObject();
		reportDateTo = (Date)input.readObject();
		applicationIdPlural = (String)input.readObject();
		eappIdPlural = (String)input.readObject();

		//	資産
		costType = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costDepPrjName = (String)input.readObject();

//		管理番号
		assetIdPlural = (String)input.readObject();
		licenseIdPlural = (String)input.readObject();
		getApplicationId = (String)input.readObject();
		getApplicationIdPlural = (String)input.readObject();
		astNumPlural = (String)input.readObject();

		//	現物情報
		holCompanyCode = (String)input.readObject();
		holCompanyName = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionName = (String)input.readObject();
		includeSectionHierarchyFlag = (String)input.readObject();
		holStaffCode = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holStaffCodeI = (String)input.readObject();
		holStaffNameI = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(apDateFrom);
		output.writeObject(apDateTo);
		output.writeObject(reportDateFrom);
		output.writeObject(reportDateTo);
		output.writeObject(applicationIdPlural);
		output.writeObject(eappIdPlural);

		//	資産
		output.writeObject(costType);
		output.writeObject(costDepPrjCode);
		output.writeObject(costDepPrjName);

//		管理番号
		output.writeObject(assetIdPlural);
		output.writeObject(licenseIdPlural);
		output.writeObject(getApplicationId);
		output.writeObject(getApplicationIdPlural);
		output.writeObject(astNumPlural);

		//	現物情報
		output.writeObject(holCompanyCode);
		output.writeObject(holCompanyName);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionName);
		output.writeObject(includeSectionHierarchyFlag);
		output.writeObject(holStaffCode);
		output.writeObject(holStaffName);
		output.writeObject(holStaffCodeI);
		output.writeObject(holStaffNameI);

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
	 * reportDateFromを取得します。
	 * @return reportDateFrom
	 */
	public Date getReportDateFrom() {
		return reportDateFrom;
	}

	/**
	 * reportDateFrom
	 * @param reportDateFromを設定します。
	 */
	public void setReportDateFrom(Date reportDateFrom) {
		this.reportDateFrom = reportDateFrom;
	}

	/**
	 * reportDateToを取得します。
	 * @return reportDateTo
	 */
	public Date getReportDateTo() {
		return reportDateTo;
	}

	/**
	 * reportDateTo
	 * @param reportDateToを設定します。
	 */
	public void setReportDateTo(Date reportDateTo) {
		this.reportDateTo = reportDateTo;
	}

	/**
	 * costTypeを取得します。
	 * @return costType
	 */
	public String getCostType() {
		return costType;
	}

	/**
	 * costType
	 * @param costTypeを設定します。
	 */
	public void setCostType(String costType) {
		this.costType = costType;
	}

	/**
	 * costDepPrjCodeを取得します。
	 * @return costDepPrjCode
	 */
	public String getCostDepPrjCode() {
		return costDepPrjCode;
	}

	/**
	 * costDepPrjCode
	 * @param costDepPrjCodeを設定します。
	 */
	public void setCostDepPrjCode(String costDepPrjCode) {
		this.costDepPrjCode = costDepPrjCode;
	}

	/**
	 * costDepPrjNameを取得します。
	 * @return costDepPrjName
	 */
	public String getCostDepPrjName() {
		return costDepPrjName;
	}

	/**
	 * costDepPrjName
	 * @param costDepPrjNameを設定します。
	 */
	public void setCostDepPrjName(String costDepPrjName) {
		this.costDepPrjName = costDepPrjName;
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
	 * getApplicationIdを取得します。
	 * @return getApplicationId
	 */
	public String getGetApplicationId() {
		return getApplicationId;
	}

	/**
	 * getApplicationId
	 * @param getApplicationIdを設定します。
	 */
	public void setGetApplicationId(String getApplicationId) {
		this.getApplicationId = getApplicationId;
	}

	/**
	 * getApplicationIdPluralを取得します。
	 * @return getApplicationIdPlural
	 */
	public String getGetApplicationIdPlural() {
		return getApplicationIdPlural;
	}

	/**
	 * getApplicationIdPlural
	 * @param getApplicationIdPluralを設定します。
	 */
	public void setGetApplicationIdPlural(String getApplicationIdPlural) {
		this.getApplicationIdPlural = getApplicationIdPlural;
	}

	/**
	 * astNumPluralを取得します。
	 * @return astNumPlural
	 */
	public String getAstNumPlural() {
		return astNumPlural;
	}

	/**
	 * astNumPlural
	 * @param astNumPluralを設定します。
	 */
	public void setAstNumPlural(String astNumPlural) {
		this.astNumPlural = astNumPlural;
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
	 * holCompanyNameを取得します。
	 * @return holCompanyName
	 */
	public String getHolCompanyName() {
		return holCompanyName;
	}

	/**
	 * holCompanyName
	 * @param holCompanyNameを設定します。
	 */
	public void setHolCompanyName(String holCompanyName) {
		this.holCompanyName = holCompanyName;
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
	 * notApplicationIdを取得します。
	 * @return notApplicationId
	 */
	public String getNotApplicationId() {
		return notApplicationId;
	}

	/**
	 * notApplicationId
	 * @param notApplicationIdを設定します。
	 */
	public void setNotApplicationId(String notApplicationId) {
		this.notApplicationId = notApplicationId;
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
	 * holStaffCodeIを取得します。
	 * @return holStaffCodeI
	 */
	public String getHolStaffCodeI() {
		return holStaffCodeI;
	}

	/**
	 * holStaffCodeI
	 * @param holStaffCodeIを設定します。
	 */
	public void setHolStaffCodeI(String holStaffCodeI) {
		this.holStaffCodeI = holStaffCodeI;
	}

	/**
	 * holStaffNameIを取得します。
	 * @return holStaffNameI
	 */
	public String getHolStaffNameI() {
		return holStaffNameI;
	}

	/**
	 * holStaffNameI
	 * @param holStaffNameIを設定します。
	 */
	public void setHolStaffNameI(String holStaffNameI) {
		this.holStaffNameI = holStaffNameI;
	}

	public Date getDisposeDateTo() {
		return disposeDateTo;
	}

	public void setDisposeDateTo(Date disposeDateTo) {
		this.disposeDateTo = disposeDateTo;
	}

}
