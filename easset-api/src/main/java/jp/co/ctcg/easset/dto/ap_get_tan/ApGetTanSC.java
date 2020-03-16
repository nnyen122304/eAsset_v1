/*===================================================================
 * ファイル名 : ApGetTanSR.java
 * 概要説明   : 取得申請(有形)検索結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-18 1.0  リヨン 崔     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_get_tan;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class ApGetTanSC extends ApGetTan {
	private static final long serialVersionUID = 1L;

	private String applicationIdPlural; // 申請書番号複数
	private Date apDateFrom; // 申請日From
	private Date apDateTo; // 申請日To
	private String excludeCompleteFlag; // 登録申請作成完了を除外

	private Date getDeliveryDateFrom; // 納入予定日From
	private Date getDeliveryDateTo; // 納入予定日To
	private String getPurCompanyName; // 購入先名
	private Long getTotalAmountFrom;  // 取得金額From
	private Long getTotalAmountTo;  // 取得金額From

	private String includeSectionHierarchyFlag; // 部署階層検索フラグ

	private String astName;  // 資産(機器)名
	private String astMakerCode; // メーカーコード
	private String astMakerName; // メーカー
	private String astMakerModel; // メーカー型番
	private String astPrjCode; // 資産プロジェクト
	private String astPrjName; // 資産プロジェクト

	private Long softwareMakerId;  // ソフトウエアメーカーID
	private String softwareMakerName;  // ソフトウエアメーカー名
	private Long softwareId; // ソフトウエアID
	private String softwareName; // ソフトウエア名

	private String description;  // 備考

	private String failureAssetId;  // 故障情報機器管理番号
	private String failureAssetIdPlural;  // 故障情報機器管理番号複数

	private String lineExistsType; // 明細検索種別 1:資産(機器)明細存在、2:ライセンス明細存在

	private String eappIdPlural; // e申請書類ID複数

	private String getPurEstimateNumberPlural; // 購入先見積番番号複数
	private String getLeReEstimateNumberPlural; // リース・レンタル見積番番号複数

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		applicationIdPlural = (String)input.readObject();
		apDateFrom = (Date)input.readObject();
		apDateTo = (Date)input.readObject();
		excludeCompleteFlag = (String)input.readObject();

		getDeliveryDateFrom = (Date)input.readObject();
		getDeliveryDateTo = (Date)input.readObject();
		getPurCompanyName = (String)input.readObject();
		getTotalAmountFrom = Function.getExternalLong(input.readObject());
		getTotalAmountTo = Function.getExternalLong(input.readObject());

		includeSectionHierarchyFlag = (String)input.readObject();

		astName = (String)input.readObject();
		astMakerCode = (String)input.readObject();
		astMakerName = (String)input.readObject();
		astMakerModel = (String)input.readObject();
		astPrjCode = (String)input.readObject();
		astPrjName = (String)input.readObject();

		softwareMakerId = Function.getExternalLong(input.readObject());
		softwareMakerName = (String)input.readObject();
		softwareId = Function.getExternalLong(input.readObject());
		softwareName = (String)input.readObject();

		description = (String)input.readObject();

		failureAssetId = (String)input.readObject();
		failureAssetIdPlural = (String)input.readObject();

		lineExistsType = (String)input.readObject();

		eappIdPlural = (String)input.readObject();

		getPurEstimateNumberPlural = (String)input.readObject();
		getLeReEstimateNumberPlural = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(applicationIdPlural);
		output.writeObject(apDateFrom);
		output.writeObject(apDateTo);
		output.writeObject(excludeCompleteFlag);

		output.writeObject(getDeliveryDateFrom);
		output.writeObject(getDeliveryDateTo);
		output.writeObject(getPurCompanyName);
		output.writeObject(getTotalAmountFrom);
		output.writeObject(getTotalAmountTo);

		output.writeObject(includeSectionHierarchyFlag);

		output.writeObject(astName);
		output.writeObject(astMakerCode);
		output.writeObject(astMakerName);
		output.writeObject(astMakerModel);
		output.writeObject(astPrjCode);
		output.writeObject(astPrjName);

		output.writeObject(softwareMakerId);
		output.writeObject(softwareMakerName);
		output.writeObject(softwareId);
		output.writeObject(softwareName);

		output.writeObject(description);

		output.writeObject(failureAssetId);
		output.writeObject(failureAssetIdPlural);

		output.writeObject(lineExistsType);

		output.writeObject(eappIdPlural);

		output.writeObject(getPurEstimateNumberPlural);
		output.writeObject(getLeReEstimateNumberPlural);

	}

	/**
	 * @return the applicationIdPlural
	 */
	public String getApplicationIdPlural() {
		return applicationIdPlural;
	}

	/**
	 * @param applicationIdPlural the applicationIdPlural to set
	 */
	public void setApplicationIdPlural(String applicationIdPlural) {
		this.applicationIdPlural = applicationIdPlural;
	}

	/**
	 * @return the apDateFrom
	 */
	public Date getApDateFrom() {
		return apDateFrom;
	}

	/**
	 * @param apDateFrom the apDateFrom to set
	 */
	public void setApDateFrom(Date apDateFrom) {
		this.apDateFrom = apDateFrom;
	}

	/**
	 * @return the apDateTo
	 */
	public Date getApDateTo() {
		return apDateTo;
	}

	/**
	 * @param apDateTo the apDateTo to set
	 */
	public void setApDateTo(Date apDateTo) {
		this.apDateTo = apDateTo;
	}

	/**
	 * @return the excludeCompleteFlag
	 */
	public String getExcludeCompleteFlag() {
		return excludeCompleteFlag;
	}

	/**
	 * @param excludeCompleteFlag the excludeCompleteFlag to set
	 */
	public void setExcludeCompleteFlag(String excludeCompleteFlag) {
		this.excludeCompleteFlag = excludeCompleteFlag;
	}

	/**
	 * @return the getDeliveryDateFrom
	 */
	public Date getGetDeliveryDateFrom() {
		return getDeliveryDateFrom;
	}

	/**
	 * @param getDeliveryDateFrom the getDeliveryDateFrom to set
	 */
	public void setGetDeliveryDateFrom(Date getDeliveryDateFrom) {
		this.getDeliveryDateFrom = getDeliveryDateFrom;
	}

	/**
	 * @return the getDeliveryDateTo
	 */
	public Date getGetDeliveryDateTo() {
		return getDeliveryDateTo;
	}

	/**
	 * @param getDeliveryDateTo the getDeliveryDateTo to set
	 */
	public void setGetDeliveryDateTo(Date getDeliveryDateTo) {
		this.getDeliveryDateTo = getDeliveryDateTo;
	}

	/**
	 * @return the getPurCompanyName
	 */
	public String getGetPurCompanyName() {
		return getPurCompanyName;
	}

	/**
	 * @param getPurCompanyName the getPurCompanyName to set
	 */
	public void setGetPurCompanyName(String getPurCompanyName) {
		this.getPurCompanyName = getPurCompanyName;
	}

	/**
	 * @return the getTotalAmountFrom
	 */
	public Long getGetTotalAmountFrom() {
		return getTotalAmountFrom;
	}

	/**
	 * @param getTotalAmountFrom the getTotalAmountFrom to set
	 */
	public void setGetTotalAmountFrom(Long getTotalAmountFrom) {
		this.getTotalAmountFrom = getTotalAmountFrom;
	}

	/**
	 * @return the getTotalAmountTo
	 */
	public Long getGetTotalAmountTo() {
		return getTotalAmountTo;
	}

	/**
	 * @param getTotalAmountTo the getTotalAmountTo to set
	 */
	public void setGetTotalAmountTo(Long getTotalAmountTo) {
		this.getTotalAmountTo = getTotalAmountTo;
	}

	/**
	 * @return the includeSectionHierarchyFlag
	 */
	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	/**
	 * @param includeSectionHierarchyFlag the includeSectionHierarchyFlag to set
	 */
	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}

	/**
	 * @return the astName
	 */
	public String getAstName() {
		return astName;
	}

	/**
	 * @param astName the astName to set
	 */
	public void setAstName(String astName) {
		this.astName = astName;
	}

	/**
	 * @return the astMakerCode
	 */
	public String getAstMakerCode() {
		return astMakerCode;
	}

	/**
	 * @param astMakerCode the astMakerCode to set
	 */
	public void setAstMakerCode(String astMakerCode) {
		this.astMakerCode = astMakerCode;
	}

	/**
	 * @return the astMakerName
	 */
	public String getAstMakerName() {
		return astMakerName;
	}

	/**
	 * @param astMakerName the astMakerName to set
	 */
	public void setAstMakerName(String astMakerName) {
		this.astMakerName = astMakerName;
	}

	/**
	 * @return the astMakerModel
	 */
	public String getAstMakerModel() {
		return astMakerModel;
	}

	/**
	 * @param astMakerModel the astMakerModel to set
	 */
	public void setAstMakerModel(String astMakerModel) {
		this.astMakerModel = astMakerModel;
	}

	/**
	 * @return the astPrjCode
	 */
	public String getAstPrjCode() {
		return astPrjCode;
	}

	/**
	 * @param astPrjCode the astPrjCode to set
	 */
	public void setAstPrjCode(String astPrjCode) {
		this.astPrjCode = astPrjCode;
	}

	/**
	 * @return the failureAssetId
	 */
	public String getFailureAssetId() {
		return failureAssetId;
	}

	/**
	 * @param failureAssetId the failureAssetId to set
	 */
	public void setFailureAssetId(String failureAssetId) {
		this.failureAssetId = failureAssetId;
	}

	/**
	 * @return the failureAssetIdPlural
	 */
	public String getFailureAssetIdPlural() {
		return failureAssetIdPlural;
	}

	/**
	 * @param failureAssetIdPlural the failureAssetIdPlural to set
	 */
	public void setFailureAssetIdPlural(String failureAssetIdPlural) {
		this.failureAssetIdPlural = failureAssetIdPlural;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the softwareMakerId
	 */
	public Long getSoftwareMakerId() {
		return softwareMakerId;
	}

	/**
	 * @param softwareMakerId the softwareMakerId to set
	 */
	public void setSoftwareMakerId(Long softwareMakerId) {
		this.softwareMakerId = softwareMakerId;
	}

	/**
	 * @return the softwareMakerName
	 */
	public String getSoftwareMakerName() {
		return softwareMakerName;
	}

	/**
	 * @param softwareMakerName the softwareMakerName to set
	 */
	public void setSoftwareMakerName(String softwareMakerName) {
		this.softwareMakerName = softwareMakerName;
	}

	/**
	 * @return the softwareId
	 */
	public Long getSoftwareId() {
		return softwareId;
	}

	/**
	 * @param softwareId the softwareId to set
	 */
	public void setSoftwareId(Long softwareId) {
		this.softwareId = softwareId;
	}

	/**
	 * @return the softwareName
	 */
	public String getSoftwareName() {
		return softwareName;
	}

	/**
	 * @param softwareName the softwareName to set
	 */
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	/**
	 * @return the lineExistsType
	 */
	public String getLineExistsType() {
		return lineExistsType;
	}

	/**
	 * @param lineExistsType the lineExistsType to set
	 */
	public void setLineExistsType(String lineExistsType) {
		this.lineExistsType = lineExistsType;
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
	 * getPurEstimateNumberPluralを取得します。
	 * @return getPurEstimateNumberPlural
	 */
	public String getGetPurEstimateNumberPlural() {
		return getPurEstimateNumberPlural;
	}

	/**
	 * getPurEstimateNumberPlural
	 * @param getPurEstimateNumberPluralを設定します。
	 */
	public void setGetPurEstimateNumberPlural(String getPurEstimateNumberPlural) {
		this.getPurEstimateNumberPlural = getPurEstimateNumberPlural;
	}

	/**
	 * getLeReEstimateNumberPluralを取得します。
	 * @return getLeReEstimateNumberPlural
	 */
	public String getGetLeReEstimateNumberPlural() {
		return getLeReEstimateNumberPlural;
	}

	/**
	 * getLeReEstimateNumberPlural
	 * @param getLeReEstimateNumberPluralを設定します。
	 */
	public void setGetLeReEstimateNumberPlural(String getLeReEstimateNumberPlural) {
		this.getLeReEstimateNumberPlural = getLeReEstimateNumberPlural;
	}

	/**
	 * astPrjNameを取得します。
	 * @return astPrjName
	 */
	public String getAstPrjName() {
		return astPrjName;
	}

	/**
	 * astPrjName
	 * @param astPrjNameを設定します。
	 */
	public void setAstPrjName(String astPrjName) {
		this.astPrjName = astPrjName;
	}

}
