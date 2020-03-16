/*===================================================================
 * ファイル名 : ApEndLe.java
 * 概要説明   : リース満了・解約申請
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-12-21  1.0  高山         新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_le;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApEndLe implements Externalizable {
	private static final long serialVersionUID = 1L;


	private String applicationId; // 申請書番号
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private Integer version; // バージョン
	private Integer displayVersion; // 改定番号
	private String updateComment; // 更新コメント
	private Long eappId; // e申請書類ID
	private String apStatus; // 申請書ステータス 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下,6:取下げ 汎用マスタ-AP_STATUS_END_LE
	private Date apDate; // 申請日
	private String apEndLeType; // 申請区分 1:再リース/返却,2:中途解約　汎用マスタ-AP_END_LE_TYPE
	private String apEndLeAmountRange; // 金額範囲 汎用マスタ-AP_END_LE_AMOUNT_RANGE
	private String specialApproveFlag; // 稟議書・経営会議等承認済フラグ 0:未承認、1:承認済
	private Date specialApproveDate; // 稟議書・経営会議等承認日
	private String apCreateStaffCode; // 起票者社員番号
	private String apCreateCompanyCode; // 起票者所属会社コード
	private String apCreateSectionCode; // 起票者所属部署コード
	private Integer apCreateSectionYear; // 起票者所属部署年度
	private String apStaffCode; // 申請者社員番号
	private String apCompanyCode; // 申請会社コード
	private String apSectionCode; // 申請部署コード
	private Integer apSectionYear; // 申請部署年度
	private String apTel; // 申請者連絡先TEL
	private String apSkipApproveFlag; // 課長/GL承認不要フラグ 0:要承認、1:承認不要
	private String apNeedCioJudgeFlag; // 要CIO審査フラグ 0:不要,1:必要
	private String apReason; // 申請理由
	private String leCompanyCode; // リース会社 MI顧客マスタコード
	private Date returnDate; // 返却予定日
	private String attDescription; // 添付補足
	private String costCompanyCode; // 経費負担会社コード
	private String costSectionCode; // 経費負担部課コード
	private Integer costSectionYear; // 経費負担部課年度
	private String descriptionAp; // 備考_申請者記入欄
	private String descriptionAdmin; // 備考_受付担当者・管理者記入欄
	private Long totalAmount; // 申請金額 再リース:再リース金額合計,中途解約:残リース料合計
	private Long totalReAmount; // 再リース金額合計
	private Long totalPurchaseAmount; // 買取金額合計
	private Long totalRemainAmount; // 残リース料合計
	private Long totalCancelAmount; // 中途解約金額合計
	private Date approveDate; // 承認日
	private String deleteExecFlag; // 抹消処理実行フラグ 0:未実行,1:実行済
	private String ppSendFlag; // ProPlus連携区分 0:しない,1:する

	private String apCreateTel; // 起票者:連絡先TEL

	// 物件申請区分(バリデーションor金額範囲選択用)
	private String blankFlag; // 未選択フラグ 0:区分未選択物件無 1:区分未選択物件有
	private String returnFlag; // 返却フラグ 0:返却物件無,1:返却物件有
	private String reFlag; // 再リースフラグ 0:再リース物件無,1:再リース物件有
	private String holSecErrFlag; // 保有部署混在フラグ 0:物件内に保有部署混在無 1:物件内に保有部署混在有

	//	名称項目(ヘッダのみ)
	private String apStatusName;	//	ステータス名
	private String ppSendFlagName; // ProPlus連携区分
	private String apEndLeTypeName;	//	申請区分名
	private String apEndLeAmountRangeName;	//	金額範囲名
	private String apCreateStaffName;	//	起票者社員名
	private String apCreateSectionName;	//	起票者所属部署名
	private String apCreateCompanyName; // 起票者所属会社名
	private String apStaffName; // 申請者社員名
	private String apMailAddress; // 申請者メールアドレス
	private String apCompanyName; // 申請会社名
	private String apSectionName; // 申請部署名
	private String apReasonName; // 申請理由区分
	private String leCompanyName; // リース会社名
	private String costCompanyName; // 経費負担会社名
	private String costSectionName; // 経費負担部課名

	//	検索・検索結果共通項目
	private String holRepOfficeCode; // 代表設置場所コード
	private String holRepOfficeName; // 代表設置場所
	private String itemGroupCode; // 案件グループコード
	private String itemGroupName; // 案件グループ
	private String costType; // 販管/原価区分
	private String costDepPrjCode; // 減価償却プロジェクトコード
	private String costDepPrjName; // 減価償却プロジェクト
	private String contractNum; // 契約番号
	private String contractSubNum; // 契約枝番
	private String assetId; // 情報機器管理番号
	private String licenseId; // ﾗｲｾﾝｽ管理番号
	private String holOfficeCode; // 設置(使用)場所コード
	private String holOfficeName; // 設置(使用)場所
	private String useStaffCode; // 使用者コード
	private String useStaffName; // 使用者名
	private String astNum; // 資産番号


	//	明細
	List<ApEndLeLineLld> apEndLeLineLldList;	// 物件明細
	List<ApEndLeLineAst> apEndLeLineAstList;	// 資産(機器)明細
	List<ApEndLeLineLic> apEndLeLineLicList;	// ライセンス明細
	List<ApEndLeLineLld> apEndLeLineOtherLldList;	// 同一契約他物件明細
	List<ApEndLeLineAtt> apEndLeLineAttList;	// 添付明細

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		version = Function.getExternalInteger(input.readObject());
		displayVersion = Function.getExternalInteger(input.readObject());
		updateComment = (String)input.readObject();
		eappId = Function.getExternalLong(input.readObject());
		apStatus = (String)input.readObject();
		apDate = (Date)input.readObject();
		apEndLeType = (String)input.readObject();
		apEndLeAmountRange = (String)input.readObject();
		specialApproveFlag = (String)input.readObject();
		specialApproveDate = (Date)input.readObject();
		apCreateStaffCode = (String)input.readObject();
		apCreateCompanyCode = (String)input.readObject();
		apCreateSectionCode = (String)input.readObject();
		apCreateSectionYear = Function.getExternalInteger(input.readObject());
		apStaffCode = (String)input.readObject();
		apCompanyCode = (String)input.readObject();
		apSectionCode = (String)input.readObject();
		apSectionYear = Function.getExternalInteger(input.readObject());
		apTel = (String)input.readObject();
		apSkipApproveFlag = (String)input.readObject();
		apNeedCioJudgeFlag = (String)input.readObject();
		apReason = (String)input.readObject();
		leCompanyCode = (String)input.readObject();
		returnDate = (Date)input.readObject();
		attDescription = (String)input.readObject();
		costCompanyCode = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		costSectionYear = Function.getExternalInteger(input.readObject());
		descriptionAp = (String)input.readObject();
		descriptionAdmin = (String)input.readObject();
		totalAmount = Function.getExternalLong(input.readObject());
		totalReAmount = Function.getExternalLong(input.readObject());
		totalPurchaseAmount = Function.getExternalLong(input.readObject());
		totalRemainAmount = Function.getExternalLong(input.readObject());
		totalCancelAmount = Function.getExternalLong(input.readObject());
		approveDate = (Date)input.readObject();
		deleteExecFlag = (String)input.readObject();
		ppSendFlag = (String)input.readObject();

		apCreateTel = (String)input.readObject();

		// 物件申請区分(バリデーションor金額範囲選択用)
		blankFlag = (String)input.readObject();
		returnFlag = (String)input.readObject();
		reFlag = (String)input.readObject();
		holSecErrFlag = (String)input.readObject();

		//	名称項目(ヘッダのみ)
		apStatusName = (String)input.readObject();
		ppSendFlagName = (String)input.readObject();
		apEndLeTypeName = (String)input.readObject();
		apEndLeAmountRangeName = (String)input.readObject();
		apCreateStaffName = (String)input.readObject();
		apCreateSectionName = (String)input.readObject();
		apCreateCompanyName = (String)input.readObject();
		apStaffName = (String)input.readObject();
		apMailAddress = (String)input.readObject();
		apCompanyName = (String)input.readObject();
		apSectionName = (String)input.readObject();
		apReasonName = (String)input.readObject();
		leCompanyName = (String)input.readObject();
		costCompanyName = (String)input.readObject();
		costSectionName = (String)input.readObject();

		//	検索・検索結果共通項目
		holRepOfficeCode = (String)input.readObject();
		holRepOfficeName = (String)input.readObject();
		itemGroupCode = (String)input.readObject();
		itemGroupName = (String)input.readObject();
		costType = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costDepPrjName = (String)input.readObject();
		contractNum = (String)input.readObject();
		contractSubNum = (String)input.readObject();
		assetId = (String)input.readObject();
		licenseId = (String)input.readObject();
		holOfficeCode = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		useStaffCode = (String)input.readObject();
		useStaffName = (String)input.readObject();
		astNum = (String)input.readObject();

		//	明細
		apEndLeLineLldList = (List<ApEndLeLineLld>)input.readObject();
		apEndLeLineAstList = (List<ApEndLeLineAst>)input.readObject();
		apEndLeLineLicList = (List<ApEndLeLineLic>)input.readObject();
		apEndLeLineOtherLldList = (List<ApEndLeLineLld>)input.readObject();
		apEndLeLineAttList = (List<ApEndLeLineAtt>)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(version);
		output.writeObject(displayVersion);
		output.writeObject(updateComment);
		output.writeObject(eappId);
		output.writeObject(apStatus);
		output.writeObject(apDate);
		output.writeObject(apEndLeType);
		output.writeObject(apEndLeAmountRange);
		output.writeObject(specialApproveFlag);
		output.writeObject(specialApproveDate);
		output.writeObject(apCreateStaffCode);
		output.writeObject(apCreateCompanyCode);
		output.writeObject(apCreateSectionCode);
		output.writeObject(apCreateSectionYear);
		output.writeObject(apStaffCode);
		output.writeObject(apCompanyCode);
		output.writeObject(apSectionCode);
		output.writeObject(apSectionYear);
		output.writeObject(apTel);
		output.writeObject(apSkipApproveFlag);
		output.writeObject(apNeedCioJudgeFlag);
		output.writeObject(apReason);
		output.writeObject(leCompanyCode);
		output.writeObject(returnDate);
		output.writeObject(attDescription);
		output.writeObject(costCompanyCode);
		output.writeObject(costSectionCode);
		output.writeObject(costSectionYear);
		output.writeObject(descriptionAp);
		output.writeObject(descriptionAdmin);
		output.writeObject(totalAmount);
		output.writeObject(totalReAmount);
		output.writeObject(totalPurchaseAmount);
		output.writeObject(totalRemainAmount);
		output.writeObject(totalCancelAmount);
		output.writeObject(approveDate);
		output.writeObject(deleteExecFlag);
		output.writeObject(ppSendFlag);

		output.writeObject(apCreateTel);

		// 物件申請区分(バリデーションor金額範囲選択用)
		output.writeObject(blankFlag);
		output.writeObject(returnFlag);
		output.writeObject(reFlag);
		output.writeObject(holSecErrFlag);

		//	名称項目(ヘッダ)
		output.writeObject(apStatusName);
		output.writeObject(ppSendFlagName);
		output.writeObject(apEndLeTypeName);
		output.writeObject(apEndLeAmountRangeName);
		output.writeObject(apCreateStaffName);
		output.writeObject(apCreateSectionName);
		output.writeObject(apCreateCompanyName);
		output.writeObject(apStaffName);
		output.writeObject(apMailAddress);
		output.writeObject(apCompanyName);
		output.writeObject(apSectionName);
		output.writeObject(apReasonName);
		output.writeObject(leCompanyName);
		output.writeObject(costCompanyName);
		output.writeObject(costSectionName);

		//	検索・検索結果共通項目
		output.writeObject(holRepOfficeCode);
		output.writeObject(holRepOfficeName);
		output.writeObject(itemGroupCode);
		output.writeObject(itemGroupName);
		output.writeObject(costType);
		output.writeObject(costDepPrjCode);
		output.writeObject(costDepPrjName);
		output.writeObject(contractNum);
		output.writeObject(contractSubNum);
		output.writeObject(assetId);
		output.writeObject(licenseId);
		output.writeObject(holOfficeCode);
		output.writeObject(holOfficeName);
		output.writeObject(useStaffCode);
		output.writeObject(useStaffName);
		output.writeObject(astNum);

		//	明細
		output.writeObject(apEndLeLineLldList);
		output.writeObject(apEndLeLineAstList);
		output.writeObject(apEndLeLineLicList);
		output.writeObject(apEndLeLineOtherLldList);
		output.writeObject(apEndLeLineAttList);
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getDisplayVersion() {
		return displayVersion;
	}

	public void setDisplayVersion(Integer displayVersion) {
		this.displayVersion = displayVersion;
	}

	public String getUpdateComment() {
		return updateComment;
	}

	public void setUpdateComment(String updateComment) {
		this.updateComment = updateComment;
	}

	public Long getEappId() {
		return eappId;
	}

	public void setEappId(Long eappId) {
		this.eappId = eappId;
	}

	public String getApStatus() {
		return apStatus;
	}

	public void setApStatus(String apStatus) {
		this.apStatus = apStatus;
	}

	public Date getApDate() {
		return apDate;
	}

	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}

	public String getApEndLeType() {
		return apEndLeType;
	}

	public void setApEndLeType(String apEndLeType) {
		this.apEndLeType = apEndLeType;
	}

	public String getApEndLeAmountRange() {
		return apEndLeAmountRange;
	}

	public void setApEndLeAmountRange(String apEndLeAmountRange) {
		this.apEndLeAmountRange = apEndLeAmountRange;
	}

	public String getSpecialApproveFlag() {
		return specialApproveFlag;
	}

	public void setSpecialApproveFlag(String specialApproveFlag) {
		this.specialApproveFlag = specialApproveFlag;
	}

	public Date getSpecialApproveDate() {
		return specialApproveDate;
	}

	public void setSpecialApproveDate(Date specialApproveDate) {
		this.specialApproveDate = specialApproveDate;
	}

	public String getApCreateStaffCode() {
		return apCreateStaffCode;
	}

	public void setApCreateStaffCode(String apCreateStaffCode) {
		this.apCreateStaffCode = apCreateStaffCode;
	}

	public String getApCreateCompanyCode() {
		return apCreateCompanyCode;
	}

	public void setApCreateCompanyCode(String apCreateCompanyCode) {
		this.apCreateCompanyCode = apCreateCompanyCode;
	}

	public String getApCreateSectionCode() {
		return apCreateSectionCode;
	}

	public void setApCreateSectionCode(String apCreateSectionCode) {
		this.apCreateSectionCode = apCreateSectionCode;
	}

	public Integer getApCreateSectionYear() {
		return apCreateSectionYear;
	}

	public void setApCreateSectionYear(Integer apCreateSectionYear) {
		this.apCreateSectionYear = apCreateSectionYear;
	}

	public String getApStaffCode() {
		return apStaffCode;
	}

	public void setApStaffCode(String apStaffCode) {
		this.apStaffCode = apStaffCode;
	}

	public String getApCompanyCode() {
		return apCompanyCode;
	}

	public void setApCompanyCode(String apCompanyCode) {
		this.apCompanyCode = apCompanyCode;
	}

	public String getApSectionCode() {
		return apSectionCode;
	}

	public void setApSectionCode(String apSectionCode) {
		this.apSectionCode = apSectionCode;
	}

	public Integer getApSectionYear() {
		return apSectionYear;
	}

	public void setApSectionYear(Integer apSectionYear) {
		this.apSectionYear = apSectionYear;
	}

	public String getApTel() {
		return apTel;
	}

	public void setApTel(String apTel) {
		this.apTel = apTel;
	}

	public String getApSkipApproveFlag() {
		return apSkipApproveFlag;
	}

	public void setApSkipApproveFlag(String apSkipApproveFlag) {
		this.apSkipApproveFlag = apSkipApproveFlag;
	}

	public String getApNeedCioJudgeFlag() {
		return apNeedCioJudgeFlag;
	}

	public void setApNeedCioJudgeFlag(String apNeedCioJudgeFlag) {
		this.apNeedCioJudgeFlag = apNeedCioJudgeFlag;
	}

	public String getApReason() {
		return apReason;
	}

	public void setApReason(String apReason) {
		this.apReason = apReason;
	}

	public String getLeCompanyCode() {
		return leCompanyCode;
	}

	public void setLeCompanyCode(String leCompanyCode) {
		this.leCompanyCode = leCompanyCode;
	}

	public Date getReturnDate() {
		return returnDate;
	}

	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}

	public String getCostCompanyCode() {
		return costCompanyCode;
	}

	public void setCostCompanyCode(String costCompanyCode) {
		this.costCompanyCode = costCompanyCode;
	}

	public String getCostSectionCode() {
		return costSectionCode;
	}

	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
	}

	public String getDescriptionAp() {
		return descriptionAp;
	}

	public void setDescriptionAp(String descriptionAp) {
		this.descriptionAp = descriptionAp;
	}

	public String getDescriptionAdmin() {
		return descriptionAdmin;
	}

	public void setDescriptionAdmin(String descriptionAdmin) {
		this.descriptionAdmin = descriptionAdmin;
	}

	public Long getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Long totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getDeleteExecFlag() {
		return deleteExecFlag;
	}

	public void setDeleteExecFlag(String deleteExecFlag) {
		this.deleteExecFlag = deleteExecFlag;
	}

	public String getPpSendFlag() {
		return ppSendFlag;
	}

	public void setPpSendFlag(String ppSendFlag) {
		this.ppSendFlag = ppSendFlag;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getApCreateTel() {
		return apCreateTel;
	}

	public void setApCreateTel(String apCreateTel) {
		this.apCreateTel = apCreateTel;
	}

	public String getApStatusName() {
		return apStatusName;
	}

	public void setApStatusName(String apStatusName) {
		this.apStatusName = apStatusName;
	}

	public String getPpSendFlagName() {
		return ppSendFlagName;
	}

	public void setPpSendFlagName(String ppSendFlagName) {
		this.ppSendFlagName = ppSendFlagName;
	}

	public String getApEndLeTypeName() {
		return apEndLeTypeName;
	}

	public void setApEndLeTypeName(String apEndLeTypeName) {
		this.apEndLeTypeName = apEndLeTypeName;
	}

	public String getApEndLeAmountRangeName() {
		return apEndLeAmountRangeName;
	}

	public void setApEndLeAmountRangeName(String apEndLeAmountRangeName) {
		this.apEndLeAmountRangeName = apEndLeAmountRangeName;
	}

	public String getApCreateStaffName() {
		return apCreateStaffName;
	}

	public void setApCreateStaffName(String apCreateStaffName) {
		this.apCreateStaffName = apCreateStaffName;
	}

	public String getApCreateSectionName() {
		return apCreateSectionName;
	}

	public void setApCreateSectionName(String apCreateSectionName) {
		this.apCreateSectionName = apCreateSectionName;
	}

	public String getApCreateCompanyName() {
		return apCreateCompanyName;
	}

	public void setApCreateCompanyName(String apCreateCompanyName) {
		this.apCreateCompanyName = apCreateCompanyName;
	}

	public String getApStaffName() {
		return apStaffName;
	}

	public void setApStaffName(String apStaffName) {
		this.apStaffName = apStaffName;
	}

	public String getApCompanyName() {
		return apCompanyName;
	}

	public void setApCompanyName(String apCompanyName) {
		this.apCompanyName = apCompanyName;
	}

	public String getApSectionName() {
		return apSectionName;
	}

	public void setApSectionName(String apSectionName) {
		this.apSectionName = apSectionName;
	}

	public String getApReasonName() {
		return apReasonName;
	}

	public void setApReasonName(String apReasonName) {
		this.apReasonName = apReasonName;
	}

	public String getCostCompanyName() {
		return costCompanyName;
	}

	public void setCostCompanyName(String costCompanyName) {
		this.costCompanyName = costCompanyName;
	}

	public String getCostSectionName() {
		return costSectionName;
	}

	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	public List<ApEndLeLineLld> getApEndLeLineLldList() {
		return apEndLeLineLldList;
	}

	public void setApEndLeLineLldList(List<ApEndLeLineLld> apEndLeLineLldList) {
		this.apEndLeLineLldList = apEndLeLineLldList;
	}

	public List<ApEndLeLineAst> getApEndLeLineAstList() {
		return apEndLeLineAstList;
	}

	public void setApEndLeLineAstList(List<ApEndLeLineAst> apEndLeLineAstList) {
		this.apEndLeLineAstList = apEndLeLineAstList;
	}

	public List<ApEndLeLineLic> getApEndLeLineLicList() {
		return apEndLeLineLicList;
	}

	public void setApEndLeLineLicList(List<ApEndLeLineLic> apEndLeLineLicList) {
		this.apEndLeLineLicList = apEndLeLineLicList;
	}

	public List<ApEndLeLineAtt> getApEndLeLineAttList() {
		return apEndLeLineAttList;
	}

	public void setApEndLeLineAttList(List<ApEndLeLineAtt> apEndLeLineAttList) {
		this.apEndLeLineAttList = apEndLeLineAttList;
	}

	public String getHolRepOfficeCode() {
		return holRepOfficeCode;
	}

	public void setHolRepOfficeCode(String holRepOfficeCode) {
		this.holRepOfficeCode = holRepOfficeCode;
	}

	public String getHolRepOfficeName() {
		return holRepOfficeName;
	}

	public void setHolRepOfficeName(String holRepOfficeName) {
		this.holRepOfficeName = holRepOfficeName;
	}

	public String getItemGroupCode() {
		return itemGroupCode;
	}

	public void setItemGroupCode(String itemGroupCode) {
		this.itemGroupCode = itemGroupCode;
	}

	public String getItemGroupName() {
		return itemGroupName;
	}

	public void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
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

	public String getCostDepPrjName() {
		return costDepPrjName;
	}

	public void setCostDepPrjName(String costDepPrjName) {
		this.costDepPrjName = costDepPrjName;
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

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	public String getHolOfficeCode() {
		return holOfficeCode;
	}

	public void setHolOfficeCode(String holOfficeCode) {
		this.holOfficeCode = holOfficeCode;
	}

	public String getHolOfficeName() {
		return holOfficeName;
	}

	public void setHolOfficeName(String holOfficeName) {
		this.holOfficeName = holOfficeName;
	}

	public String getUseStaffCode() {
		return useStaffCode;
	}

	public void setUseStaffCode(String useStaffCode) {
		this.useStaffCode = useStaffCode;
	}

	public String getUseStaffName() {
		return useStaffName;
	}

	public void setUseStaffName(String useStaffName) {
		this.useStaffName = useStaffName;
	}

	public String getLeCompanyName() {
		return leCompanyName;
	}

	public void setLeCompanyName(String leCompanyName) {
		this.leCompanyName = leCompanyName;
	}

	public String getAstNum() {
		return astNum;
	}

	public void setAstNum(String astNum) {
		this.astNum = astNum;
	}

	public List<ApEndLeLineLld> getApEndLeLineOtherLldList() {
		return apEndLeLineOtherLldList;
	}

	public void setApEndLeLineOtherLldList(
			List<ApEndLeLineLld> apEndLeLineOtherLldList) {
		this.apEndLeLineOtherLldList = apEndLeLineOtherLldList;
	}

	public String getReturnFlag() {
		return returnFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	public String getReFlag() {
		return reFlag;
	}

	public void setReFlag(String reFlag) {
		this.reFlag = reFlag;
	}

	public String getAttDescription() {
		return attDescription;
	}

	public void setAttDescription(String attDescription) {
		this.attDescription = attDescription;
	}

	public String getBlankFlag() {
		return blankFlag;
	}

	public void setBlankFlag(String blankFlag) {
		this.blankFlag = blankFlag;
	}

	public String getHolSecErrFlag() {
		return holSecErrFlag;
	}

	public void setHolSecErrFlag(String holSecErrFlag) {
		this.holSecErrFlag = holSecErrFlag;
	}

	public Integer getCostSectionYear() {
		return costSectionYear;
	}

	public void setCostSectionYear(Integer costSectionYear) {
		this.costSectionYear = costSectionYear;
	}

	public String getApMailAddress() {
		return apMailAddress;
	}

	public void setApMailAddress(String apMailAddress) {
		this.apMailAddress = apMailAddress;
	}

	public Long getTotalReAmount() {
		return totalReAmount;
	}

	public void setTotalReAmount(Long totalReAmount) {
		this.totalReAmount = totalReAmount;
	}

	public Long getTotalPurchaseAmount() {
		return totalPurchaseAmount;
	}

	public void setTotalPurchaseAmount(Long totalPurchaseAmount) {
		this.totalPurchaseAmount = totalPurchaseAmount;
	}

	public Long getTotalRemainAmount() {
		return totalRemainAmount;
	}

	public void setTotalRemainAmount(Long totalRemainAmount) {
		this.totalRemainAmount = totalRemainAmount;
	}

	public Long getTotalCancelAmount() {
		return totalCancelAmount;
	}

	public void setTotalCancelAmount(Long totalCancelAmount) {
		this.totalCancelAmount = totalCancelAmount;
	}

}