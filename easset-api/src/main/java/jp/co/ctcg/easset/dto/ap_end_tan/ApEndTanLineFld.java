/*===================================================================
 * ファイル名 : ApChangeLineFld.java
 * 概要説明   : 固定資産除売却申請_資産明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-26 1.0  李           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_tan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApEndTanLineFld implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private Long ppId; // 固有番号 ProPlusの資産台帳キー
	private String getApplicationId; // 取得申請書番号
	private Date astDate; // 計上日
	private String astNum; // 資産番号
	private String astName; // 資産名
	private String astClass; // 種類
	private Long astGetAmount; // 取得価額
	private Long astDefBookAmount; // 帳簿価額
	private Long astBookAmount; // 除却時帳簿価額
	private Long salesAmount; // 売却額
	private String costType; // 販売管理費/原価区分 1:販売管理費,2:原価
	private String costDepPrjCode; // 減価償却プロジェクトコード
	private String costSectionCode; // 資産計上負担部課コード
	private String itemGroupCode; // 案件グループコード
	private String holRepOfficeCode; // 代表設置場所
	private String costDistCode; // 配賦コード
	private String holCompanyCode; // 保有会社コード(無形)
	private String holSectionCode; // 保有部署コード(無形)
	private Integer holSectionYear; // 保有部署年度(無形)
	private String holStaffCode; // 無形管理担当者
	private String ppTransFlag; // ProPlus連携フラグ 0:未連携,1:連携済
	private String tempFlag; // 仮勘定フラグ 0:本勘定,1:仮勘定

	//	名称
	private String astClassName; // 種類名
	private String costTypeName; // 販売管理費/原価区分
	private String costDepPrjName; // 減価償却プロジェクト名
	private String costSectionName; // 資産計上負担部課名
	private String itemGroupName; // 案件グループ名
	private String holRepOfficeName; // 代表設置場所名
	private String costDistName; // 配賦名
	private String holSectionName;	//	保有部署名
	private String holStaffName; // 無形管理担当者名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		ppId = Function.getExternalLong(input.readObject());
		getApplicationId = (String)input.readObject();
		astDate = (Date)input.readObject();
		astNum = (String)input.readObject();
		astName = (String)input.readObject();
		astClass = (String)input.readObject();
		astGetAmount = Function.getExternalLong(input.readObject());
		astDefBookAmount = Function.getExternalLong(input.readObject());
		astBookAmount = Function.getExternalLong(input.readObject());
		salesAmount = Function.getExternalLong(input.readObject());
		costType = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		itemGroupCode = (String)input.readObject();
		holRepOfficeCode = (String)input.readObject();
		costDistCode = (String)input.readObject();
		holCompanyCode = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holStaffCode = (String)input.readObject();
		ppTransFlag = (String)input.readObject();
		tempFlag = (String)input.readObject();

		//	名称
		astClassName = (String)input.readObject();
		costTypeName = (String)input.readObject();
		costDepPrjName = (String)input.readObject();
		costSectionName = (String)input.readObject();
		itemGroupName = (String)input.readObject();
		holRepOfficeName = (String)input.readObject();
		costDistName = (String)input.readObject();
		holSectionName = (String)input.readObject();
		holStaffName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(ppId);
		output.writeObject(getApplicationId);
		output.writeObject(astDate);
		output.writeObject(astNum);
		output.writeObject(astName);
		output.writeObject(astClass);
		output.writeObject(astGetAmount);
		output.writeObject(astDefBookAmount);
		output.writeObject(astBookAmount);
		output.writeObject(salesAmount);
		output.writeObject(costType);
		output.writeObject(costDepPrjCode);
		output.writeObject(costSectionCode);
		output.writeObject(itemGroupCode);
		output.writeObject(holRepOfficeCode);
		output.writeObject(costDistCode);
		output.writeObject(holCompanyCode);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(holStaffCode);
		output.writeObject(ppTransFlag);
		output.writeObject(tempFlag);

		//	名称
		output.writeObject(astClassName);
		output.writeObject(costTypeName);
		output.writeObject(costDepPrjName);
		output.writeObject(costSectionName);
		output.writeObject(itemGroupName);
		output.writeObject(holRepOfficeName);
		output.writeObject(costDistName);
		output.writeObject(holSectionName);
		output.writeObject(holStaffName);

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
	 * ppIdを取得します。
	 * @return ppId
	 */
	public Long getPpId() {
		return ppId;
	}

	/**
	 * ppId
	 * @param ppIdを設定します。
	 */
	public void setPpId(Long ppId) {
		this.ppId = ppId;
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
	 * astDateを取得します。
	 * @return astDate
	 */
	public Date getAstDate() {
		return astDate;
	}

	/**
	 * astDate
	 * @param astDateを設定します。
	 */
	public void setAstDate(Date astDate) {
		this.astDate = astDate;
	}

	/**
	 * astNumを取得します。
	 * @return astNum
	 */
	public String getAstNum() {
		return astNum;
	}

	/**
	 * astNum
	 * @param astNumを設定します。
	 */
	public void setAstNum(String astNum) {
		this.astNum = astNum;
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
	 * astClassを取得します。
	 * @return astClass
	 */
	public String getAstClass() {
		return astClass;
	}

	/**
	 * astClass
	 * @param astClassを設定します。
	 */
	public void setAstClass(String astClass) {
		this.astClass = astClass;
	}

	/**
	 * astGetAmountを取得します。
	 * @return astGetAmount
	 */
	public Long getAstGetAmount() {
		return astGetAmount;
	}

	/**
	 * astGetAmount
	 * @param astGetAmountを設定します。
	 */
	public void setAstGetAmount(Long astGetAmount) {
		this.astGetAmount = astGetAmount;
	}

	/**
	 * astDefBookAmountを取得します。
	 * @return astDefBookAmount
	 */
	public Long getAstDefBookAmount() {
		return astDefBookAmount;
	}

	/**
	 * astDefBookAmount
	 * @param astDefBookAmountを設定します。
	 */
	public void setAstDefBookAmount(Long astDefBookAmount) {
		this.astDefBookAmount = astDefBookAmount;
	}

	/**
	 * astBookAmountを取得します。
	 * @return astBookAmount
	 */
	public Long getAstBookAmount() {
		return astBookAmount;
	}

	/**
	 * astBookAmount
	 * @param astBookAmountを設定します。
	 */
	public void setAstBookAmount(Long astBookAmount) {
		this.astBookAmount = astBookAmount;
	}

	/**
	 * salesAmountを取得します。
	 * @return salesAmount
	 */
	public Long getSalesAmount() {
		return salesAmount;
	}

	/**
	 * salesAmount
	 * @param salesAmountを設定します。
	 */
	public void setSalesAmount(Long salesAmount) {
		this.salesAmount = salesAmount;
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
	 * itemGroupCodeを取得します。
	 * @return itemGroupCode
	 */
	public String getItemGroupCode() {
		return itemGroupCode;
	}

	/**
	 * itemGroupCode
	 * @param itemGroupCodeを設定します。
	 */
	public void setItemGroupCode(String itemGroupCode) {
		this.itemGroupCode = itemGroupCode;
	}

	/**
	 * holRepOfficeCodeを取得します。
	 * @return holRepOfficeCode
	 */
	public String getHolRepOfficeCode() {
		return holRepOfficeCode;
	}

	/**
	 * holRepOfficeCode
	 * @param holRepOfficeCodeを設定します。
	 */
	public void setHolRepOfficeCode(String holRepOfficeCode) {
		this.holRepOfficeCode = holRepOfficeCode;
	}

	/**
	 * costDistCodeを取得します。
	 * @return costDistCode
	 */
	public String getCostDistCode() {
		return costDistCode;
	}

	/**
	 * costDistCode
	 * @param costDistCodeを設定します。
	 */
	public void setCostDistCode(String costDistCode) {
		this.costDistCode = costDistCode;
	}

	/**
	 * costSectionCodeを取得します。
	 * @return costSectionCode
	 */
	public String getCostSectionCode() {
		return costSectionCode;
	}

	/**
	 * costSectionCode
	 * @param costSectionCodeを設定します。
	 */
	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
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
	 * holSectionYearを取得します。
	 * @return holSectionYear
	 */
	public Integer getHolSectionYear() {
		return holSectionYear;
	}

	/**
	 * holSectionYear
	 * @param holSectionYearを設定します。
	 */
	public void setHolSectionYear(Integer holSectionYear) {
		this.holSectionYear = holSectionYear;
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
	 * astClassNameを取得します。
	 * @return astClassName
	 */
	public String getAstClassName() {
		return astClassName;
	}

	/**
	 * astClassName
	 * @param astClassNameを設定します。
	 */
	public void setAstClassName(String astClassName) {
		this.astClassName = astClassName;
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
	 * itemGroupNameを取得します。
	 * @return itemGroupName
	 */
	public String getItemGroupName() {
		return itemGroupName;
	}

	/**
	 * itemGroupName
	 * @param itemGroupNameを設定します。
	 */
	public void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
	}

	/**
	 * holRepOfficeNameを取得します。
	 * @return holRepOfficeName
	 */
	public String getHolRepOfficeName() {
		return holRepOfficeName;
	}

	/**
	 * holRepOfficeName
	 * @param holRepOfficeNameを設定します。
	 */
	public void setHolRepOfficeName(String holRepOfficeName) {
		this.holRepOfficeName = holRepOfficeName;
	}

	/**
	 * costDistNameを取得します。
	 * @return costDistName
	 */
	public String getCostDistName() {
		return costDistName;
	}

	/**
	 * costDistName
	 * @param costDistNameを設定します。
	 */
	public void setCostDistName(String costDistName) {
		this.costDistName = costDistName;
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
	 * ppTransFlagを取得します。
	 * @return ppTransFlag
	 */
	public String getPpTransFlag() {
		return ppTransFlag;
	}

	/**
	 * ppTransFlag
	 * @param ppTransFlagを設定します。
	 */
	public void setPpTransFlag(String ppTransFlag) {
		this.ppTransFlag = ppTransFlag;
	}

	/**
	 * tempFlagを取得します。
	 * @return tempFlag
	 */
	public String getTempFlag() {
		return tempFlag;
	}

	/**
	 * tempFlag
	 * @param tempFlagを設定します。
	 */
	public void setTempFlag(String tempFlag) {
		this.tempFlag = tempFlag;
	}

	/**
	 * @return the holSectionName
	 */
	public String getHolSectionName() {
		return holSectionName;
	}

	/**
	 * @param holSectionName the holSectionName to set
	 */
	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}

}
