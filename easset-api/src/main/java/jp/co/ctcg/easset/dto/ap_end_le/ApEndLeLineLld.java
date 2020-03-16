/*===================================================================
 * ファイル名 : ApEndLeLineLld.java
 * 概要説明   : リース満了・解約申請
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-12-21  1.0 高山         新規
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
public class ApEndLeLineLld extends LineData {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String apStatus; // 申請書ステータス 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下,6:取下げ 汎用マスタ-AP_STATUS_END_LE
	private String apEndLeLineType; // 物件申請区分 1:返却、2：再リース、3:買取　汎用マスタ-AP_END_LE_LINE_TYPE
	private String apEndLeLineTypeName; // 物件申請区分名
	private Long ppIdContract; // 固有番号(契約) ProPlusの契約台帳キー
	private Long ppIdAst; // 固有番号(物件) ProPlusの物件台帳キー
	private String contractNum; // 契約番号
	private String contractSubNum; // 契約枝番
	private String astName; // 物件名
	private Date endDate; // リース満了日
	private Long remainAmount; // 残リース料
	private Long monthAmount; // 均等額（ﾘｰｽ月額）
	private Long reAmount; // 再リース金額
	private Long purchaseAmount; // 買取金額
	private Long cancelAmount; // 中途解約金額
	private Integer remainTerm; // 残期間 月数
	private String astClass; // 種類
	private String costType; // 販売管理費/原価区分 1:販売管理費,2:原価
	private String costDepPrjCode; // 減価償却プロジェクトコード
	private String costSectionCode; // 資産計上負担部課コード
	private String itemGroupCode; // 案件グループコード
	private String holRepOfficeCode; // 代表設置場所
	private String costDistCode; // 配賦コード
	private String astNum; // 資産番号
	private String ppTransFlag; // ProPlus連携フラグ 0:未連携,1:連携済
	private String leCompanyCode; // リース会社コード

	//	名称
	private String astClassName; // 種類名
	private String costTypeName; // 販売管理費/原価区分
	private String costDepPrjName; // 減価償却プロジェクト名
	private String costSectionName; // 資産計上負担部課名
	private String itemGroupName; // 案件グループ名
	private String holRepOfficeName; // 代表設置場所名
	private String costDistName; // 配賦名
	private String leCompanyName; // リース会社名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		apStatus = (String)input.readObject();
		apEndLeLineType = (String)input.readObject();
		apEndLeLineTypeName = (String)input.readObject();
		ppIdContract = Function.getExternalLong(input.readObject());
		ppIdAst = Function.getExternalLong(input.readObject());
		contractNum = (String)input.readObject();
		contractSubNum = (String)input.readObject();
		astName = (String)input.readObject();
		endDate = (Date)input.readObject();
		remainAmount = Function.getExternalLong(input.readObject());
		monthAmount = Function.getExternalLong(input.readObject());
		reAmount = Function.getExternalLong(input.readObject());
		purchaseAmount = Function.getExternalLong(input.readObject());
		cancelAmount = Function.getExternalLong(input.readObject());
		remainTerm = Function.getExternalInteger(input.readObject());
		astClass = (String)input.readObject();
		costType = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		itemGroupCode = (String)input.readObject();
		holRepOfficeCode = (String)input.readObject();
		costDistCode = (String)input.readObject();
		astNum = (String)input.readObject();
		ppTransFlag = (String)input.readObject();
		leCompanyCode = (String)input.readObject();

		//	名称
		astClassName = (String)input.readObject();
		costTypeName = (String)input.readObject();
		costDepPrjName = (String)input.readObject();
		costSectionName = (String)input.readObject();
		itemGroupName = (String)input.readObject();
		holRepOfficeName = (String)input.readObject();
		costDistName = (String)input.readObject();
		leCompanyName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(apStatus);
		output.writeObject(apEndLeLineType);
		output.writeObject(apEndLeLineTypeName);
		output.writeObject(ppIdContract);
		output.writeObject(ppIdAst);
		output.writeObject(contractNum);
		output.writeObject(contractSubNum);
		output.writeObject(astName);
		output.writeObject(endDate);
		output.writeObject(remainAmount);
		output.writeObject(monthAmount);
		output.writeObject(reAmount);
		output.writeObject(purchaseAmount);
		output.writeObject(cancelAmount);
		output.writeObject(remainTerm);
		output.writeObject(astClass);
		output.writeObject(costType);
		output.writeObject(costDepPrjCode);
		output.writeObject(costSectionCode);
		output.writeObject(itemGroupCode);
		output.writeObject(holRepOfficeCode);
		output.writeObject(costDistCode);
		output.writeObject(astNum);
		output.writeObject(ppTransFlag);
		output.writeObject(leCompanyCode);

		//	名称
		output.writeObject(astClassName);
		output.writeObject(costTypeName);
		output.writeObject(costDepPrjName);
		output.writeObject(costSectionName);
		output.writeObject(itemGroupName);
		output.writeObject(holRepOfficeName);
		output.writeObject(costDistName);
		output.writeObject(leCompanyName);

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

	public String getApEndLeLineType() {
		return apEndLeLineType;
	}

	public void setApEndLeLineType(String apEndLeLineType) {
		this.apEndLeLineType = apEndLeLineType;
	}

	public Long getPpIdContract() {
		return ppIdContract;
	}

	public void setPpIdContract(Long ppIdContract) {
		this.ppIdContract = ppIdContract;
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

	public String getAstName() {
		return astName;
	}

	public void setAstName(String astName) {
		this.astName = astName;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Long getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(Long remainAmount) {
		this.remainAmount = remainAmount;
	}

	public Long getReAmount() {
		return reAmount;
	}

	public void setReAmount(Long reAmount) {
		this.reAmount = reAmount;
	}

	public Long getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(Long purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
	}

	public Long getCancelAmount() {
		return cancelAmount;
	}

	public void setCancelAmount(Long cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

	public Integer getRemainTerm() {
		return remainTerm;
	}

	public void setRemainTerm(Integer remainTerm) {
		this.remainTerm = remainTerm;
	}

	public String getAstClass() {
		return astClass;
	}

	public void setAstClass(String astClass) {
		this.astClass = astClass;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getCostDepPrjCode() {
		return costDepPrjCode;
	}

	public void setCostDepPrjCode(String costDepPrjCode) {
		this.costDepPrjCode = costDepPrjCode;
	}

	public String getItemGroupCode() {
		return itemGroupCode;
	}

	public void setItemGroupCode(String itemGroupCode) {
		this.itemGroupCode = itemGroupCode;
	}

	public String getHolRepOfficeCode() {
		return holRepOfficeCode;
	}

	public void setHolRepOfficeCode(String holRepOfficeCode) {
		this.holRepOfficeCode = holRepOfficeCode;
	}

	public String getCostDistCode() {
		return costDistCode;
	}

	public void setCostDistCode(String costDistCode) {
		this.costDistCode = costDistCode;
	}

	public String getAstNum() {
		return astNum;
	}

	public void setAstNum(String astNum) {
		this.astNum = astNum;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getAstClassName() {
		return astClassName;
	}

	public void setAstClassName(String astClassName) {
		this.astClassName = astClassName;
	}

	public String getCostTypeName() {
		return costTypeName;
	}

	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	public String getCostDepPrjName() {
		return costDepPrjName;
	}

	public void setCostDepPrjName(String costDepPrjName) {
		this.costDepPrjName = costDepPrjName;
	}

	public String getItemGroupName() {
		return itemGroupName;
	}

	public void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
	}

	public String getHolRepOfficeName() {
		return holRepOfficeName;
	}

	public void setHolRepOfficeName(String holRepOfficeName) {
		this.holRepOfficeName = holRepOfficeName;
	}

	public String getCostDistName() {
		return costDistName;
	}

	public void setCostDistName(String costDistName) {
		this.costDistName = costDistName;
	}

	public String getApEndLeLineTypeName() {
		return apEndLeLineTypeName;
	}

	public void setApEndLeLineTypeName(String apEndLeLineTypeName) {
		this.apEndLeLineTypeName = apEndLeLineTypeName;
	}

	public String getLeCompanyName() {
		return leCompanyName;
	}

	public void setLeCompanyName(String leCompanyName) {
		this.leCompanyName = leCompanyName;
	}

	public Long getMonthAmount() {
		return monthAmount;
	}

	public void setMonthAmount(Long monthAmount) {
		this.monthAmount = monthAmount;
	}

	public String getCostSectionCode() {
		return costSectionCode;
	}

	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
	}

	public String getCostSectionName() {
		return costSectionName;
	}

	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	public String getApStatus() {
		return apStatus;
	}

	public void setApStatus(String apStatus) {
		this.apStatus = apStatus;
	}

	public String getPpTransFlag() {
		return ppTransFlag;
	}

	public void setPpTransFlag(String ppTransFlag) {
		this.ppTransFlag = ppTransFlag;
	}

	public String getLeCompanyCode() {
		return leCompanyCode;
	}

	public void setLeCompanyCode(String leCompanyCode) {
		this.leCompanyCode = leCompanyCode;
	}
}