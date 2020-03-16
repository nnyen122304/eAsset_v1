/*===================================================================
 * ファイル名 : ApEndTan.java
 * 概要説明   : 除売却申請
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
import java.util.List;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApEndTan implements Externalizable {
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
	private String apStatus; // 申請書ステータス 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下,6:取下げ 汎用マスタ-AP_STATUS_END_TAN
	private Date apDate; // 申請日
	private String apFldType; // 有形・無形区分 1:有形,2:無形　汎用マスタ-AP_FLD_TYPE
	private String apEndTanType; // 除売却区分 1:廃棄,2:売却　汎用マスタ-AP_END_LE_TYPE
	private String apEndTanAmountRange; // 金額範囲 汎用マスタ-AP_END_TAN_AMOUNT_RANGE
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
	private String apReasonCode; // 除却理由区分 ProPlus除却理由区分
	private String apReason; // 申請理由
	private String disposeCompanyCode; // 廃棄・売却先会社コード MI顧客マスタコード
	private String disposeCompanyName; // 廃棄・売却先会社名
	private String disposeCompanyCharge; // 廃棄・売却先担当者
	private String disposeCompanyTel; // 廃棄・売却先TEL
	private Date disposeDate; // 廃棄・売却予定日
	private String attDescription; // 添付補足
	private String costCompanyCode; // 経費負担会社コード
	private String costSectionCode; // 経費負担部課コード
	private Integer costSectionYear; // 経費負担部課年度
	private String descriptionAp; // 備考_申請者記入欄
	private String descriptionAdmin; // 備考_受付担当者・管理者記入欄
	private Long totalBookAmount; // 除却時簿価合計
	private Long totalSalesAmount; // 売却金額合計
	private Date approveDate; // 承認日
	private String reportFlag; // 除却報告フラグ 0:未,1:済
	private Date reportDate; // 除却報告日
	private String deleteExecFlag; // 抹消処理実行フラグ 0:未実行,1:実行済
	private Date reminderDate; // 督促メール送信日付

	private String apCreateTel; // 起票者:連絡先TEL

	//	名称項目(ヘッダのみ)
	private String apStatusName;	//	ステータス名
	private String apFldTypeName;
	private String apEndTanTypeName;	//	除売却区分名
	private String apEndTanAmountRangeName;	//	除却時簿価範囲名
	private String apCreateStaffName;	//	起票者社員名
	private String apCreateSectionName;	//	起票者所属部署名
	private String apCreateCompanyName; // 起票者所属会社名
	private String apStaffName; // 申請者社員名
	private String apCompanyName; // 申請会社名
	private String apSectionName; // 申請部署名
	private String apReasonName; // 除売却理由区分
	private String costCompanyName; // 経費負担会社名
	private String costSectionName; // 経費負担部課名

	//	検索・検索結果共通項目
	private String astNum;	//	資産番号
	private String itemGroupCode;	//	案件グループコード
	private String itemGroupName;	//	案件名
	private String holRepOfficeCode;	//	代表設置場所コード
	private String holRepOfficeName;	//	代表設置場所名
	private String assetId;	//	情報機器管理番号
	private String holOfficeCode;	//	個別設置(使用)場所コード
	private String holOfficeName;	//	個別設置(使用)場所
	private String useStaffCode;	//	使用者コード
	private String useStaffName;	//	使用者
	private String licenseId;	//	ﾗｲｾﾝｽ管理番号

	//	明細
	List<ApEndTanLineFld> apEndTanLineFldList;	//	固定資産除売却申請_資産明細
	List<ApEndTanLineAst> apEndTanLineAstList;	//	固定資産除売却申請_資産(機器)明細
	List<ApEndTanLineLic> apEndTanLineLicList;	//	固定資産除売却申請_ライセンス明細
	List<ApEndTanLineAtt> apEndTanLineAttList;	//	固定資産除売却申請_添付明細

	//	その他
	private String apEndTanAmountRangeUseCostSecType; // 除却金額範囲経費負担部署使用設定
	private String apEndTanAmountRangeUseLineType; // 除却金額範囲明細使用設定

	//	CVSDL用項目
	private String reportFlagName;	//	除売却報告名称

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
		apFldType = (String)input.readObject();
		apEndTanType = (String)input.readObject();
		apEndTanAmountRange = (String)input.readObject();
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
		apReasonCode = (String)input.readObject();
		apReason = (String)input.readObject();
		disposeCompanyCode = (String)input.readObject();
		disposeCompanyName = (String)input.readObject();
		disposeCompanyCharge = (String)input.readObject();
		disposeCompanyTel = (String)input.readObject();
		disposeDate = (Date)input.readObject();
		attDescription = (String)input.readObject();
		costCompanyCode = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		costSectionYear = Function.getExternalInteger(input.readObject());
		descriptionAp = (String)input.readObject();
		descriptionAdmin = (String)input.readObject();
		totalBookAmount = Function.getExternalLong(input.readObject());
		totalSalesAmount = Function.getExternalLong(input.readObject());
		approveDate = (Date)input.readObject();
		reportFlag = (String)input.readObject();
		reportDate = (Date)input.readObject();
		deleteExecFlag = (String)input.readObject();
		reminderDate = (Date)input.readObject();

		apCreateTel = (String)input.readObject();

//		名称項目(ヘッダのみ)
		apStatusName = (String)input.readObject();
		apFldTypeName = (String)input.readObject();
		apEndTanTypeName = (String)input.readObject();
		apEndTanAmountRangeName = (String)input.readObject();
		apCreateStaffName = (String)input.readObject();
		apCreateSectionName = (String)input.readObject();
		apCreateCompanyName = (String)input.readObject();
		apStaffName = (String)input.readObject();
		apCompanyName = (String)input.readObject();
		apSectionName = (String)input.readObject();
		apReasonName = (String)input.readObject();
		costCompanyName = (String)input.readObject();
		costSectionName = (String)input.readObject();

//		検索・検索結果共通項目
		astNum = (String)input.readObject();
		itemGroupCode = (String)input.readObject();
		itemGroupName = (String)input.readObject();
		holRepOfficeCode = (String)input.readObject();
		holRepOfficeName = (String)input.readObject();
		assetId = (String)input.readObject();
		holOfficeCode = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		useStaffCode = (String)input.readObject();
		useStaffName = (String)input.readObject();
		licenseId = (String)input.readObject();

		//	明細
		apEndTanLineFldList = (List<ApEndTanLineFld>)input.readObject();
		apEndTanLineAstList = (List<ApEndTanLineAst>)input.readObject();
		apEndTanLineLicList = (List<ApEndTanLineLic>)input.readObject();
		apEndTanLineAttList = (List<ApEndTanLineAtt>)input.readObject();

		//	その他
		apEndTanAmountRangeUseCostSecType = (String)input.readObject();
		apEndTanAmountRangeUseLineType = (String)input.readObject();

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
		output.writeObject(apFldType);
		output.writeObject(apEndTanType);
		output.writeObject(apEndTanAmountRange);
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
		output.writeObject(apReasonCode);
		output.writeObject(apReason);
		output.writeObject(disposeCompanyCode);
		output.writeObject(disposeCompanyName);
		output.writeObject(disposeCompanyCharge);
		output.writeObject(disposeCompanyTel);
		output.writeObject(disposeDate);
		output.writeObject(attDescription);
		output.writeObject(costCompanyCode);
		output.writeObject(costSectionCode);
		output.writeObject(costSectionYear);
		output.writeObject(descriptionAp);
		output.writeObject(descriptionAdmin);
		output.writeObject(totalBookAmount);
		output.writeObject(totalSalesAmount);
		output.writeObject(approveDate);
		output.writeObject(reportFlag);
		output.writeObject(reportDate);
		output.writeObject(deleteExecFlag);
		output.writeObject(reminderDate);

		output.writeObject(apCreateTel);

		//	名称項目(ヘッダ)
		output.writeObject(apStatusName);
		output.writeObject(apFldTypeName);
		output.writeObject(apEndTanTypeName);
		output.writeObject(apEndTanAmountRangeName);
		output.writeObject(apCreateStaffName);
		output.writeObject(apCreateSectionName);
		output.writeObject(apCreateCompanyName);
		output.writeObject(apStaffName);
		output.writeObject(apCompanyName);
		output.writeObject(apSectionName);
		output.writeObject(apReasonName);
		output.writeObject(costCompanyName);
		output.writeObject(costSectionName);

		//		検索・検索結果共通項目
		output.writeObject(astNum);
		output.writeObject(itemGroupCode);
		output.writeObject(itemGroupName);
		output.writeObject(holRepOfficeCode);
		output.writeObject(holRepOfficeName);
		output.writeObject(assetId);
		output.writeObject(holOfficeCode);
		output.writeObject(holOfficeName);
		output.writeObject(useStaffCode);
		output.writeObject(useStaffName);
		output.writeObject(licenseId);

		//	明細
		output.writeObject(apEndTanLineFldList);
		output.writeObject(apEndTanLineAstList);
		output.writeObject(apEndTanLineLicList);
		output.writeObject(apEndTanLineAttList);

		//	その他
		output.writeObject(apEndTanAmountRangeUseCostSecType);
		output.writeObject(apEndTanAmountRangeUseLineType);

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
	 * versionを取得します。
	 * @return version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * version
	 * @param versionを設定します。
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * displayVersionを取得します。
	 * @return displayVersion
	 */
	public Integer getDisplayVersion() {
		return displayVersion;
	}

	/**
	 * displayVersion
	 * @param displayVersionを設定します。
	 */
	public void setDisplayVersion(Integer displayVersion) {
		this.displayVersion = displayVersion;
	}

	/**
	 * updateCommentを取得します。
	 * @return updateComment
	 */
	public String getUpdateComment() {
		return updateComment;
	}

	/**
	 * updateComment
	 * @param updateCommentを設定します。
	 */
	public void setUpdateComment(String updateComment) {
		this.updateComment = updateComment;
	}

	/**
	 * eappIdを取得します。
	 * @return eappId
	 */
	public Long getEappId() {
		return eappId;
	}

	/**
	 * eappId
	 * @param eappIdを設定します。
	 */
	public void setEappId(Long eappId) {
		this.eappId = eappId;
	}

	/**
	 * apStatusを取得します。
	 * @return apStatus
	 */
	public String getApStatus() {
		return apStatus;
	}

	/**
	 * apStatus
	 * @param apStatusを設定します。
	 */
	public void setApStatus(String apStatus) {
		this.apStatus = apStatus;
	}

	/**
	 * apDateを取得します。
	 * @return apDate
	 */
	public Date getApDate() {
		return apDate;
	}

	/**
	 * apDate
	 * @param apDateを設定します。
	 */
	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}

	/**
	 * apFldTypeを取得します。
	 * @return apFldType
	 */
	public String getApFldType() {
		return apFldType;
	}

	/**
	 * apFldType
	 * @param apFldTypeを設定します。
	 */
	public void setApFldType(String apFldType) {
		this.apFldType = apFldType;
	}

	/**
	 * apEndTanTypeを取得します。
	 * @return apEndTanType
	 */
	public String getApEndTanType() {
		return apEndTanType;
	}

	/**
	 * apEndTanType
	 * @param apEndTanTypeを設定します。
	 */
	public void setApEndTanType(String apEndTanType) {
		this.apEndTanType = apEndTanType;
	}

	/**
	 * apEndTanAmountRangeを取得します。
	 * @return apEndTanAmountRange
	 */
	public String getApEndTanAmountRange() {
		return apEndTanAmountRange;
	}

	/**
	 * apEndTanAmountRange
	 * @param apEndTanAmountRangeを設定します。
	 */
	public void setApEndTanAmountRange(String apEndTanAmountRange) {
		this.apEndTanAmountRange = apEndTanAmountRange;
	}

	/**
	 * specialApproveFlagを取得します。
	 * @return specialApproveFlag
	 */
	public String getSpecialApproveFlag() {
		return specialApproveFlag;
	}

	/**
	 * specialApproveFlag
	 * @param specialApproveFlagを設定します。
	 */
	public void setSpecialApproveFlag(String specialApproveFlag) {
		this.specialApproveFlag = specialApproveFlag;
	}

	/**
	 * specialApproveDateを取得します。
	 * @return specialApproveDate
	 */
	public Date getSpecialApproveDate() {
		return specialApproveDate;
	}

	/**
	 * specialApproveDate
	 * @param specialApproveDateを設定します。
	 */
	public void setSpecialApproveDate(Date specialApproveDate) {
		this.specialApproveDate = specialApproveDate;
	}

	/**
	 * apCreateStaffCodeを取得します。
	 * @return apCreateStaffCode
	 */
	public String getApCreateStaffCode() {
		return apCreateStaffCode;
	}

	/**
	 * apCreateStaffCode
	 * @param apCreateStaffCodeを設定します。
	 */
	public void setApCreateStaffCode(String apCreateStaffCode) {
		this.apCreateStaffCode = apCreateStaffCode;
	}

	/**
	 * apCreateCompanyCodeを取得します。
	 * @return apCreateCompanyCode
	 */
	public String getApCreateCompanyCode() {
		return apCreateCompanyCode;
	}

	/**
	 * apCreateCompanyCode
	 * @param apCreateCompanyCodeを設定します。
	 */
	public void setApCreateCompanyCode(String apCreateCompanyCode) {
		this.apCreateCompanyCode = apCreateCompanyCode;
	}

	/**
	 * apCreateSectionCodeを取得します。
	 * @return apCreateSectionCode
	 */
	public String getApCreateSectionCode() {
		return apCreateSectionCode;
	}

	/**
	 * apCreateSectionCode
	 * @param apCreateSectionCodeを設定します。
	 */
	public void setApCreateSectionCode(String apCreateSectionCode) {
		this.apCreateSectionCode = apCreateSectionCode;
	}

	/**
	 * apCreateSectionYearを取得します。
	 * @return apCreateSectionYear
	 */
	public Integer getApCreateSectionYear() {
		return apCreateSectionYear;
	}

	/**
	 * apCreateSectionYear
	 * @param apCreateSectionYearを設定します。
	 */
	public void setApCreateSectionYear(Integer apCreateSectionYear) {
		this.apCreateSectionYear = apCreateSectionYear;
	}

	/**
	 * apStaffCodeを取得します。
	 * @return apStaffCode
	 */
	public String getApStaffCode() {
		return apStaffCode;
	}

	/**
	 * apStaffCode
	 * @param apStaffCodeを設定します。
	 */
	public void setApStaffCode(String apStaffCode) {
		this.apStaffCode = apStaffCode;
	}

	/**
	 * apCompanyCodeを取得します。
	 * @return apCompanyCode
	 */
	public String getApCompanyCode() {
		return apCompanyCode;
	}

	/**
	 * apCompanyCode
	 * @param apCompanyCodeを設定します。
	 */
	public void setApCompanyCode(String apCompanyCode) {
		this.apCompanyCode = apCompanyCode;
	}

	/**
	 * apSectionCodeを取得します。
	 * @return apSectionCode
	 */
	public String getApSectionCode() {
		return apSectionCode;
	}

	/**
	 * apSectionCode
	 * @param apSectionCodeを設定します。
	 */
	public void setApSectionCode(String apSectionCode) {
		this.apSectionCode = apSectionCode;
	}

	/**
	 * apSectionYearを取得します。
	 * @return apSectionYear
	 */
	public Integer getApSectionYear() {
		return apSectionYear;
	}

	/**
	 * apSectionYear
	 * @param apSectionYearを設定します。
	 */
	public void setApSectionYear(Integer apSectionYear) {
		this.apSectionYear = apSectionYear;
	}

	/**
	 * apTelを取得します。
	 * @return apTel
	 */
	public String getApTel() {
		return apTel;
	}

	/**
	 * apTel
	 * @param apTelを設定します。
	 */
	public void setApTel(String apTel) {
		this.apTel = apTel;
	}

	/**
	 * apSkipApproveFlagを取得します。
	 * @return apSkipApproveFlag
	 */
	public String getApSkipApproveFlag() {
		return apSkipApproveFlag;
	}

	/**
	 * apSkipApproveFlag
	 * @param apSkipApproveFlagを設定します。
	 */
	public void setApSkipApproveFlag(String apSkipApproveFlag) {
		this.apSkipApproveFlag = apSkipApproveFlag;
	}

	/**
	 * apNeedCioJudgeFlagを取得します。
	 * @return apNeedCioJudgeFlag
	 */
	public String getApNeedCioJudgeFlag() {
		return apNeedCioJudgeFlag;
	}

	/**
	 * apNeedCioJudgeFlag
	 * @param apNeedCioJudgeFlagを設定します。
	 */
	public void setApNeedCioJudgeFlag(String apNeedCioJudgeFlag) {
		this.apNeedCioJudgeFlag = apNeedCioJudgeFlag;
	}

	/**
	 * apReasonCodeを取得します。
	 * @return apReasonCode
	 */
	public String getApReasonCode() {
		return apReasonCode;
	}

	/**
	 * apReasonCode
	 * @param apReasonCodeを設定します。
	 */
	public void setApReasonCode(String apReasonCode) {
		this.apReasonCode = apReasonCode;
	}

	/**
	 * apReasonを取得します。
	 * @return apReason
	 */
	public String getApReason() {
		return apReason;
	}

	/**
	 * apReason
	 * @param apReasonを設定します。
	 */
	public void setApReason(String apReason) {
		this.apReason = apReason;
	}

	/**
	 * disposeCompanyCodeを取得します。
	 * @return disposeCompanyCode
	 */
	public String getDisposeCompanyCode() {
		return disposeCompanyCode;
	}

	/**
	 * disposeCompanyCode
	 * @param disposeCompanyCodeを設定します。
	 */
	public void setDisposeCompanyCode(String disposeCompanyCode) {
		this.disposeCompanyCode = disposeCompanyCode;
	}

	/**
	 * disposeCompanyNameを取得します。
	 * @return disposeCompanyName
	 */
	public String getDisposeCompanyName() {
		return disposeCompanyName;
	}

	/**
	 * disposeCompanyName
	 * @param disposeCompanyNameを設定します。
	 */
	public void setDisposeCompanyName(String disposeCompanyName) {
		this.disposeCompanyName = disposeCompanyName;
	}

	/**
	 * disposeCompanyChargeを取得します。
	 * @return disposeCompanyCharge
	 */
	public String getDisposeCompanyCharge() {
		return disposeCompanyCharge;
	}

	/**
	 * disposeCompanyCharge
	 * @param disposeCompanyChargeを設定します。
	 */
	public void setDisposeCompanyCharge(String disposeCompanyCharge) {
		this.disposeCompanyCharge = disposeCompanyCharge;
	}

	/**
	 * disposeCompanyTelを取得します。
	 * @return disposeCompanyTel
	 */
	public String getDisposeCompanyTel() {
		return disposeCompanyTel;
	}

	/**
	 * disposeCompanyTel
	 * @param disposeCompanyTelを設定します。
	 */
	public void setDisposeCompanyTel(String disposeCompanyTel) {
		this.disposeCompanyTel = disposeCompanyTel;
	}

	/**
	 * disposeDateを取得します。
	 * @return disposeDate
	 */
	public Date getDisposeDate() {
		return disposeDate;
	}

	/**
	 * disposeDate
	 * @param disposeDateを設定します。
	 */
	public void setDisposeDate(Date disposeDate) {
		this.disposeDate = disposeDate;
	}

	/**
	 * attDescriptionを取得します。
	 * @return attDescription
	 */
	public String getAttDescription() {
		return attDescription;
	}

	/**
	 * attDescription
	 * @param attDescriptionを設定します。
	 */
	public void setAttDescription(String attDescription) {
		this.attDescription = attDescription;
	}

	/**
	 * costCompanyCodeを取得します。
	 * @return costCompanyCode
	 */
	public String getCostCompanyCode() {
		return costCompanyCode;
	}

	/**
	 * costCompanyCode
	 * @param costCompanyCodeを設定します。
	 */
	public void setCostCompanyCode(String costCompanyCode) {
		this.costCompanyCode = costCompanyCode;
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
	 * descriptionApを取得します。
	 * @return descriptionAp
	 */
	public String getDescriptionAp() {
		return descriptionAp;
	}

	/**
	 * descriptionAp
	 * @param descriptionApを設定します。
	 */
	public void setDescriptionAp(String descriptionAp) {
		this.descriptionAp = descriptionAp;
	}

	/**
	 * descriptionAdminを取得します。
	 * @return descriptionAdmin
	 */
	public String getDescriptionAdmin() {
		return descriptionAdmin;
	}

	/**
	 * descriptionAdmin
	 * @param descriptionAdminを設定します。
	 */
	public void setDescriptionAdmin(String descriptionAdmin) {
		this.descriptionAdmin = descriptionAdmin;
	}

	/**
	 * totalBookAmountを取得します。
	 * @return totalBookAmount
	 */
	public Long getTotalBookAmount() {
		return totalBookAmount;
	}

	/**
	 * totalBookAmount
	 * @param totalBookAmountを設定します。
	 */
	public void setTotalBookAmount(Long totalBookAmount) {
		this.totalBookAmount = totalBookAmount;
	}

	/**
	 * totalSalesAmountを取得します。
	 * @return totalSalesAmount
	 */
	public Long getTotalSalesAmount() {
		return totalSalesAmount;
	}

	/**
	 * totalSalesAmount
	 * @param totalSalesAmountを設定します。
	 */
	public void setTotalSalesAmount(Long totalSalesAmount) {
		this.totalSalesAmount = totalSalesAmount;
	}

	/**
	 * approveDateを取得します。
	 * @return approveDate
	 */
	public Date getApproveDate() {
		return approveDate;
	}

	/**
	 * approveDate
	 * @param approveDateを設定します。
	 */
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * reportFlagを取得します。
	 * @return reportFlag
	 */
	public String getReportFlag() {
		return reportFlag;
	}

	/**
	 * reportFlag
	 * @param reportFlagを設定します。
	 */
	public void setReportFlag(String reportFlag) {
		this.reportFlag = reportFlag;
	}

	/**
	 * reportDateを取得します。
	 * @return reportDate
	 */
	public Date getReportDate() {
		return reportDate;
	}

	/**
	 * reportDate
	 * @param reportDateを設定します。
	 */
	public void setReportDate(Date reportDate) {
		this.reportDate = reportDate;
	}

	/**
	 * deleteExecFlagを取得します。
	 * @return deleteExecFlag
	 */
	public String getDeleteExecFlag() {
		return deleteExecFlag;
	}

	/**
	 * deleteExecFlag
	 * @param deleteExecFlagを設定します。
	 */
	public void setDeleteExecFlag(String deleteExecFlag) {
		this.deleteExecFlag = deleteExecFlag;
	}

	/**
	 * apCreateTelを取得します。
	 * @return apCreateTel
	 */
	public String getApCreateTel() {
		return apCreateTel;
	}

	/**
	 * apCreateTel
	 * @param apCreateTelを設定します。
	 */
	public void setApCreateTel(String apCreateTel) {
		this.apCreateTel = apCreateTel;
	}

	/**
	 * apEndTanLineFldListを取得します。
	 * @return apEndTanLineFldList
	 */
	public List<ApEndTanLineFld> getApEndTanLineFldList() {
		return apEndTanLineFldList;
	}

	/**
	 * apEndTanLineFldList
	 * @param apEndTanLineFldListを設定します。
	 */
	public void setApEndTanLineFldList(List<ApEndTanLineFld> apEndTanLineFldList) {
		this.apEndTanLineFldList = apEndTanLineFldList;
	}

	/**
	 * apEndTanLineAstListを取得します。
	 * @return apEndTanLineAstList
	 */
	public List<ApEndTanLineAst> getApEndTanLineAstList() {
		return apEndTanLineAstList;
	}

	/**
	 * apEndTanLineAstList
	 * @param apEndTanLineAstListを設定します。
	 */
	public void setApEndTanLineAstList(List<ApEndTanLineAst> apEndTanLineAstList) {
		this.apEndTanLineAstList = apEndTanLineAstList;
	}

	/**
	 * apEndTanLineLicListを取得します。
	 * @return apEndTanLineLicList
	 */
	public List<ApEndTanLineLic> getApEndTanLineLicList() {
		return apEndTanLineLicList;
	}

	/**
	 * apEndTanLineLicList
	 * @param apEndTanLineLicListを設定します。
	 */
	public void setApEndTanLineLicList(List<ApEndTanLineLic> apEndTanLineLicList) {
		this.apEndTanLineLicList = apEndTanLineLicList;
	}

	/**
	 * apEndTanLineAttListを取得します。
	 * @return apEndTanLineAttList
	 */
	public List<ApEndTanLineAtt> getApEndTanLineAttList() {
		return apEndTanLineAttList;
	}

	/**
	 * apEndTanLineAttList
	 * @param apEndTanLineAttListを設定します。
	 */
	public void setApEndTanLineAttList(List<ApEndTanLineAtt> apEndTanLineAttList) {
		this.apEndTanLineAttList = apEndTanLineAttList;
	}

	/**
	 * apStatusNameを取得します。
	 * @return apStatusName
	 */
	public String getApStatusName() {
		return apStatusName;
	}

	/**
	 * apStatusName
	 * @param apStatusNameを設定します。
	 */
	public void setApStatusName(String apStatusName) {
		this.apStatusName = apStatusName;
	}

	/**
	 * apEndTanTypeNameを取得します。
	 * @return apEndTanTypeName
	 */
	public String getApEndTanTypeName() {
		return apEndTanTypeName;
	}

	/**
	 * apEndTanTypeName
	 * @param apEndTanTypeNameを設定します。
	 */
	public void setApEndTanTypeName(String apEndTanTypeName) {
		this.apEndTanTypeName = apEndTanTypeName;
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
	 * holOfficeCodeを取得します。
	 * @return holOfficeCode
	 */
	public String getHolOfficeCode() {
		return holOfficeCode;
	}

	/**
	 * holOfficeCode
	 * @param holOfficeCodeを設定します。
	 */
	public void setHolOfficeCode(String holOfficeCode) {
		this.holOfficeCode = holOfficeCode;
	}

	/**
	 * holOfficeNameを取得します。
	 * @return holOfficeName
	 */
	public String getHolOfficeName() {
		return holOfficeName;
	}

	/**
	 * holOfficeName
	 * @param holOfficeNameを設定します。
	 */
	public void setHolOfficeName(String holOfficeName) {
		this.holOfficeName = holOfficeName;
	}

	/**
	 * useStaffCodeを取得します。
	 * @return useStaffCode
	 */
	public String getUseStaffCode() {
		return useStaffCode;
	}

	/**
	 * useStaffCode
	 * @param useStaffCodeを設定します。
	 */
	public void setUseStaffCode(String useStaffCode) {
		this.useStaffCode = useStaffCode;
	}

	/**
	 * useStaffNameを取得します。
	 * @return useStaffName
	 */
	public String getUseStaffName() {
		return useStaffName;
	}

	/**
	 * useStaffName
	 * @param useStaffNameを設定します。
	 */
	public void setUseStaffName(String useStaffName) {
		this.useStaffName = useStaffName;
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
	 * apEndTanAmountRangeNameを取得します。
	 * @return apEndTanAmountRangeName
	 */
	public String getApEndTanAmountRangeName() {
		return apEndTanAmountRangeName;
	}

	/**
	 * apEndTanAmountRangeName
	 * @param apEndTanAmountRangeNameを設定します。
	 */
	public void setApEndTanAmountRangeName(String apEndTanAmountRangeName) {
		this.apEndTanAmountRangeName = apEndTanAmountRangeName;
	}

	/**
	 * apEndTanAmountRangeUseCostSecTypeを取得します。
	 * @return apEndTanAmountRangeUseCostSecType
	 */
	public String getApEndTanAmountRangeUseCostSecType() {
		return apEndTanAmountRangeUseCostSecType;
	}

	/**
	 * apEndTanAmountRangeUseCostSecType
	 * @param apEndTanAmountRangeUseCostSecTypeを設定します。
	 */
	public void setApEndTanAmountRangeUseCostSecType(
			String apEndTanAmountRangeUseCostSecType) {
		this.apEndTanAmountRangeUseCostSecType = apEndTanAmountRangeUseCostSecType;
	}

	/**
	 * apEndTanAmountRangeUseLineTypeを取得します。
	 * @return apEndTanAmountRangeUseLineType
	 */
	public String getApEndTanAmountRangeUseLineType() {
		return apEndTanAmountRangeUseLineType;
	}

	/**
	 * apEndTanAmountRangeUseLineType
	 * @param apEndTanAmountRangeUseLineTypeを設定します。
	 */
	public void setApEndTanAmountRangeUseLineType(
			String apEndTanAmountRangeUseLineType) {
		this.apEndTanAmountRangeUseLineType = apEndTanAmountRangeUseLineType;
	}

	/**
	 * apCreateStaffNameを取得します。
	 * @return apCreateStaffName
	 */
	public String getApCreateStaffName() {
		return apCreateStaffName;
	}

	/**
	 * apCreateStaffName
	 * @param apCreateStaffNameを設定します。
	 */
	public void setApCreateStaffName(String apCreateStaffName) {
		this.apCreateStaffName = apCreateStaffName;
	}

	/**
	 * apCreateSectionNameを取得します。
	 * @return apCreateSectionName
	 */
	public String getApCreateSectionName() {
		return apCreateSectionName;
	}

	/**
	 * apCreateSectionName
	 * @param apCreateSectionNameを設定します。
	 */
	public void setApCreateSectionName(String apCreateSectionName) {
		this.apCreateSectionName = apCreateSectionName;
	}

	/**
	 * apStaffNameを取得します。
	 * @return apStaffName
	 */
	public String getApStaffName() {
		return apStaffName;
	}

	/**
	 * apStaffName
	 * @param apStaffNameを設定します。
	 */
	public void setApStaffName(String apStaffName) {
		this.apStaffName = apStaffName;
	}

	/**
	 * apCreateCompanyNameを取得します。
	 * @return apCreateCompanyName
	 */
	public String getApCreateCompanyName() {
		return apCreateCompanyName;
	}

	/**
	 * apCreateCompanyName
	 * @param apCreateCompanyNameを設定します。
	 */
	public void setApCreateCompanyName(String apCreateCompanyName) {
		this.apCreateCompanyName = apCreateCompanyName;
	}

	/**
	 * apCompanyNameを取得します。
	 * @return apCompanyName
	 */
	public String getApCompanyName() {
		return apCompanyName;
	}

	/**
	 * apCompanyName
	 * @param apCompanyNameを設定します。
	 */
	public void setApCompanyName(String apCompanyName) {
		this.apCompanyName = apCompanyName;
	}

	/**
	 * apSectionNameを取得します。
	 * @return apSectionName
	 */
	public String getApSectionName() {
		return apSectionName;
	}

	/**
	 * apSectionName
	 * @param apSectionNameを設定します。
	 */
	public void setApSectionName(String apSectionName) {
		this.apSectionName = apSectionName;
	}

	/**
	 * apReasonNameを取得します。
	 * @return apReasonName
	 */
	public String getApReasonName() {
		return apReasonName;
	}

	/**
	 * apReasonName
	 * @param apReasonNameを設定します。
	 */
	public void setApReasonName(String apReasonName) {
		this.apReasonName = apReasonName;
	}

	/**
	 * costCompanyNameを取得します。
	 * @return costCompanyName
	 */
	public String getCostCompanyName() {
		return costCompanyName;
	}

	/**
	 * costCompanyName
	 * @param costCompanyNameを設定します。
	 */
	public void setCostCompanyName(String costCompanyName) {
		this.costCompanyName = costCompanyName;
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
	 * apFldTypeNameを取得します。
	 * @return apFldTypeName
	 */
	public String getApFldTypeName() {
		return apFldTypeName;
	}

	/**
	 * apFldTypeName
	 * @param apFldTypeNameを設定します。
	 */
	public void setApFldTypeName(String apFldTypeName) {
		this.apFldTypeName = apFldTypeName;
	}

	/**
	 * reminderDateを取得します。
	 * @return reminderDate
	 */
	public Date getReminderDate() {
		return reminderDate;
	}

	/**
	 * reminderDate
	 * @param reminderDateを設定します。
	 */
	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	/**
	 * reportFlagNameを取得します。
	 * @return reportFlagName
	 */
	public String getReportFlagName() {
		return reportFlagName;
	}

	/**
	 * reportFlagName
	 * @param reportFlagNameを設定します。
	 */
	public void setReportFlagName(String reportFlagName) {
		this.reportFlagName = reportFlagName;
	}

	public Integer getCostSectionYear() {
		return costSectionYear;
	}

	public void setCostSectionYear(Integer costSectionYear) {
		this.costSectionYear = costSectionYear;
	}



}
