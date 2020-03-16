/*===================================================================
 * ファイル名 : ApChangeLineContract.java
 * 概要説明   : 移動申請_契約明細
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
import java.util.List;

import jp.co.ctcg.easset.rest.MappingUtils;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApChangeLineContract implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private String apChangeLineContractType; // 契約明細区分 1:リース契約,2:レンタル契約
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String contractCompanyCode; // 契約会社コード
	private String contractNum; // 契約番号
	private Date contractStartDate; // 契約開始日
	private Date contractEndDate; // 契約終了日
	private Long remainAmount; // 残金額
	private Long monthAmount; // 月額
	private String costType; // 販売管理費/原価区分 1:販売管理費,2:原価
	private String costDepPrjCode; // 減価償却プロジェクトコード

	private String costTypeName; // 販売管理費/原価区分名
	private String costDepPrjName;  // 減価償却プロジェクト名
	private String costSectionName; // 経費負担部署
	private String contractCompanyName; // 契約会社名

	private String assetId;		//	情報機器管理番号
	private String licenseId;	//	ライセンス管理番号

	//	1.5
	private String contractSubNum; // 契約枝番
	private String astNum; // 資産番号
	private String astName; // 物件名
	private Integer remainTerm; // 残期間
	private String astClass; // 種類
	private String costSectionCode; // 資産計上部課コード
	private String itemGroupCode; // 案件グループコード
	private String holRepOfficeCode; // 代表設置場所
	private String costDistCode; // 配賦コード
	private Long ppIdContract; // 固有番号(契約) ProPlusの契約台帳キー
	private Long ppIdAst; // 固有番号(物件) ProPlusの物件台帳キー
	private String ppTransFlag; // ProPlus連携フラグ 0:未連携,1:連携済

	private String astClassName; // 種類名
	private String itemGroupName; // 案件グループ名
	private String holRepOfficeName; // 代表設置場所名
	private String costDistName; // 配賦名

	private List<ApChangeLineCostSec> apChangeLineCostSecList;

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		apChangeLineContractType = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		contractCompanyCode = (String)input.readObject();
		contractNum = (String)input.readObject();
		contractStartDate = (Date)input.readObject();
		contractEndDate = (Date)input.readObject();
		remainAmount = Function.getExternalLong(input.readObject());
		monthAmount = Function.getExternalLong(input.readObject());
		costType = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();

		costTypeName = (String)input.readObject();
		costDepPrjName = (String)input.readObject();
		costSectionName = (String)input.readObject();
		contractCompanyName = (String)input.readObject();

		assetId = (String)input.readObject();
		licenseId = (String)input.readObject();

		contractSubNum = (String)input.readObject();
		astNum = (String)input.readObject();
		astName = (String)input.readObject();
		remainTerm = Function.getExternalInteger(input.readObject());
		astClass = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		itemGroupCode = (String)input.readObject();
		holRepOfficeCode = (String)input.readObject();
		costDistCode = (String)input.readObject();
		ppIdContract = Function.getExternalLong(input.readObject());
		ppIdAst = Function.getExternalLong(input.readObject());
		ppTransFlag = (String)input.readObject();

		astClassName = (String)input.readObject();
		itemGroupName = (String)input.readObject();
		holRepOfficeName = (String)input.readObject();
		costDistName = (String)input.readObject();

		apChangeLineCostSecList = (List<ApChangeLineCostSec>)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(apChangeLineContractType);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(contractCompanyCode);
		output.writeObject(contractNum);
		output.writeObject(contractStartDate);
		output.writeObject(contractEndDate);
		output.writeObject(remainAmount);
		output.writeObject(monthAmount);
		output.writeObject(costType);
		output.writeObject(costDepPrjCode);

		output.writeObject(costTypeName);
		output.writeObject(costDepPrjName);
		output.writeObject(costSectionName);
		output.writeObject(contractCompanyName);

		output.writeObject(assetId);
		output.writeObject(licenseId);

		output.writeObject(contractSubNum);
		output.writeObject(astNum);
		output.writeObject(astName);
		output.writeObject(remainTerm);
		output.writeObject(astClass);
		output.writeObject(costSectionCode);
		output.writeObject(itemGroupCode);
		output.writeObject(holRepOfficeCode);
		output.writeObject(costDistCode);
		output.writeObject(ppIdContract);
		output.writeObject(ppIdAst);
		output.writeObject(ppTransFlag);

		output.writeObject(astClassName);
		output.writeObject(itemGroupName);
		output.writeObject(holRepOfficeName);
		output.writeObject(costDistName);

		output.writeObject(apChangeLineCostSecList);

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
	 * apChangeLineContractTypeを取得します。
	 * @return apChangeLineContractType
	 */
	public String getApChangeLineContractType() {
		return apChangeLineContractType;
	}

	/**
	 * apChangeLineContractType
	 * @param apChangeLineContractTypeを設定します。
	 */
	public void setApChangeLineContractType(String apChangeLineContractType) {
		this.apChangeLineContractType = apChangeLineContractType;
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
	 * contractStartDateを取得します。
	 * @return contractStartDate
	 */
	public Date getContractStartDate() {
		return contractStartDate;
	}

	/**
	 * contractStartDate
	 * @param contractStartDateを設定します。
	 */
	public void setContractStartDate(Date contractStartDate) {
		this.contractStartDate = contractStartDate;
	}

	/**
	 * contractEndDateを取得します。
	 * @return contractEndDate
	 */
	public Date getContractEndDate() {
		return contractEndDate;
	}

	/**
	 * contractEndDate
	 * @param contractEndDateを設定します。
	 */
	public void setContractEndDate(Date contractEndDate) {
		this.contractEndDate = contractEndDate;
	}

	/**
	 * remainAmountを取得します。
	 * @return remainAmount
	 */
	public Long getRemainAmount() {
		return remainAmount;
	}

	/**
	 * remainAmount
	 * @param remainAmountを設定します。
	 */
	public void setRemainAmount(Long remainAmount) {
		this.remainAmount = remainAmount;
	}

	/**
	 * monthAmountを取得します。
	 * @return monthAmount
	 */
	public Long getMonthAmount() {
		return monthAmount;
	}

	/**
	 * monthAmount
	 * @param monthAmountを設定します。
	 */
	public void setMonthAmount(Long monthAmount) {
		this.monthAmount = monthAmount;
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
	 * costSectionNameを取得します。
	 * @return costSectionName
	 */
	public String getCostSectionName() {
		return costSectionName;
	}

	/**
	 * costSectionName
	 * @param costSectionNameを設定します。
	 */
	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	/**
	 * contractCompanyCodeを取得します。
	 * @return contractCompanyCode
	 */
	public String getContractCompanyCode() {
		return contractCompanyCode;
	}

	/**
	 * contractCompanyCode
	 * @param contractCompanyCodeを設定します。
	 */
	public void setContractCompanyCode(String contractCompanyCode) {
		this.contractCompanyCode = contractCompanyCode;
	}

	/**
	 * contractCompanyNameを取得します。
	 * @return contractCompanyName
	 */
	public String getContractCompanyName() {
		return contractCompanyName;
	}

	/**
	 * contractCompanyName
	 * @param contractCompanyNameを設定します。
	 */
	public void setContractCompanyName(String contractCompanyName) {
		this.contractCompanyName = contractCompanyName;
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
	 * apChangeLineCostSecListを取得します。
	 * @return apChangeLineCostSecList
	 */
	public List<ApChangeLineCostSec> getApChangeLineCostSecList() {
		return apChangeLineCostSecList;
	}

	/**
	 * apChangeLineCostSecList
	 * @param apChangeLineCostSecListを設定します。
	 */
	public void setApChangeLineCostSecList(
			List<ApChangeLineCostSec> apChangeLineCostSecList) {
		this.apChangeLineCostSecList = apChangeLineCostSecList;
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
	 * @return the remainTerm
	 */
	public Integer getRemainTerm() {
		return remainTerm;
	}

	/**
	 * @param remainTerm the remainTerm to set
	 */
	public void setRemainTerm(Integer remainTerm) {
		this.remainTerm = remainTerm;
	}

	/**
	 * @return the astClass
	 */
	public String getAstClass() {
		return astClass;
	}

	/**
	 * @param astClass the astClass to set
	 */
	public void setAstClass(String astClass) {
		this.astClass = astClass;
	}

	/**
	 * @return the costSectionCode
	 */
	public String getCostSectionCode() {
		return costSectionCode;
	}

	/**
	 * @param costSectionCode the costSectionCode to set
	 */
	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
	}

	/**
	 * @return the itemGroupCode
	 */
	public String getItemGroupCode() {
		return itemGroupCode;
	}

	/**
	 * @param itemGroupCode the itemGroupCode to set
	 */
	public void setItemGroupCode(String itemGroupCode) {
		this.itemGroupCode = itemGroupCode;
	}

	/**
	 * @return the holRepOfficeCode
	 */
	public String getHolRepOfficeCode() {
		return holRepOfficeCode;
	}

	/**
	 * @param holRepOfficeCode the holRepOfficeCode to set
	 */
	public void setHolRepOfficeCode(String holRepOfficeCode) {
		this.holRepOfficeCode = holRepOfficeCode;
	}

	/**
	 * @return the costDistCode
	 */
	public String getCostDistCode() {
		return costDistCode;
	}

	/**
	 * @param costDistCode the costDistCode to set
	 */
	public void setCostDistCode(String costDistCode) {
		this.costDistCode = costDistCode;
	}

	/**
	 * @return the ppIdContract
	 */
	public Long getPpIdContract() {
		return ppIdContract;
	}

	/**
	 * @param ppIdContract the ppIdContract to set
	 */
	public void setPpIdContract(Long ppIdContract) {
		this.ppIdContract = ppIdContract;
	}

	/**
	 * @return the ppIdAst
	 */
	public Long getPpIdAst() {
		return ppIdAst;
	}

	/**
	 * @param ppIdAst the ppIdAst to set
	 */
	public void setPpIdAst(Long ppIdAst) {
		this.ppIdAst = ppIdAst;
	}

	/**
	 * @return the ppTransFlag
	 */
	public String getPpTransFlag() {
		return ppTransFlag;
	}

	/**
	 * @param ppTransFlag the ppTransFlag to set
	 */
	public void setPpTransFlag(String ppTransFlag) {
		this.ppTransFlag = ppTransFlag;
	}

	/**
	 * @return the astClassName
	 */
	public String getAstClassName() {
		return astClassName;
	}

	/**
	 * @param astClassName the astClassName to set
	 */
	public void setAstClassName(String astClassName) {
		this.astClassName = astClassName;
	}

	/**
	 * @return the itemGroupName
	 */
	public String getItemGroupName() {
		return itemGroupName;
	}

	/**
	 * @param itemGroupName the itemGroupName to set
	 */
	public void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
	}

	/**
	 * @return the holRepOfficeName
	 */
	public String getHolRepOfficeName() {
		return holRepOfficeName;
	}

	/**
	 * @param holRepOfficeName the holRepOfficeName to set
	 */
	public void setHolRepOfficeName(String holRepOfficeName) {
		this.holRepOfficeName = holRepOfficeName;
	}

	/**
	 * @return the costDistName
	 */
	public String getCostDistName() {
		return costDistName;
	}

	/**
	 * @param costDistName the costDistName to set
	 */
	public void setCostDistName(String costDistName) {
		this.costDistName = costDistName;
	}

   /*
	 * 文字列からクラスに変換メソッド
	 * @param json 変換json文字列
	 * @return ApChangeLineContract.class 該当クラス
	 */
	public static ApChangeLineContract fromString(String json) {
		return MappingUtils.fromJson(json, ApChangeLineContract.class);
	}
}
