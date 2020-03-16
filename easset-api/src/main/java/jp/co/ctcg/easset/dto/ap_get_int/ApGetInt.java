/*===================================================================
 * ファイル名 : ApGetInt.java
 * 概要説明   : 取得申請(無形)
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_get_int;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApGetInt implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId;					// 申請書番号
	private String applicationVersion;				// 申請書バージョン
	private Date createDate;						// 作成日
	private String createStaffCode;					// 作成者社員番号
	private Date updateDate;						// 更新日
	private String updateStaffCode;					// 更新者社員番号
	private Integer version;						// バージョン
	private Integer displayVersion;					// 改定番号
	private String updateComment;					// 更新コメント
	private Long eappId;							// e申請書類ID
	private String apStatus;						// 申請書ステータス 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下,6:取下げ 汎用マスタ-AP_STATUS_GET_INT
	private String apStatusName;					// 申請書ステータス名
	private Date apDate;							// 申請日
	private String apGetType;						// 取得形態 汎用マスタ-AP_GET_INT_TYPE
	private String apGetTypeName;					// 取得形態名
	private String apGetAmountRange;				// 取得金額範囲 汎用マスタ-AP_GET_INT_AMOUNT_RANGE
	private String apGetAmountRangeName;			// 取得金額範囲名
	private String apGetAmountRangeUseCostSecType;	// 取得金額範囲経費負担部署使用設定
	private String apGetAmountRangeUseLineType;		// 取得金額範囲明細使用設定
	private String apGetAmountRangeUseCostType;		// 取得金額範囲明細資産区分 1:資産、2:費用
	private String specialApproveFlag;				// 稟議書・経営会議等承認済フラグ 0:未承認、1:承認済
	private Date specialApproveDate;				// 稟議書・経営会議等承認日
	private String apCreateStaffCode;				// 起票者社員番号
	private String apCreateStaffName;				// 起票者社員名
	private String apCreateCompanyCode;				// 起票者所属会社コード
	private String apCreateCompanyName;				// 起票者所属会社名
	private String apCreateSectionCode;				// 起票者所属部署コード
	private String apCreateSectionName;				// 起票者所属部署名
	private Integer apCreateSectionYear;			// 起票者所属部署年度
	private String apCreateTel;						// 起票者連絡先TEL
	private String apStaffCode;						// 申請者社員番号
	private String apStaffName;						// 申請者社員名
	private String apCompanyCode;					// 申請会社コード
	private String apCompanyName;					// 申請会社名
	private String apSectionCode;					// 申請部署コード
	private String apSectionName;					// 申請部署名
	private Integer apSectionYear;					// 申請部署年度
	private String apTel;							// 申請者連絡先TEL
	private String apSkipApproveFlag;				// 課長/GL承認不要フラグ 0:要承認、1:承認不要
	private String apNeedCioJudgeFlag;				// 要CIO審査フラグ 0:不要,1:必要
	private String astGetTimeFlag;					// 取得時期 0:その他、1:先行申請
	private String astName;							// 資産名
	private String astCloudType;					// クラウド区分 1:クラウド以外,2:クラウド
	private String astGroupCode;					// 案件グループコード
	private String astGroupName;					// 案件グループ名
	private String astGetReason;					// 取得（再申請）理由
	private Date astCompletePlanDate;				// リリース・検収（納品）・開発完了予定日
	private String astEffectType;					// 費用削減効果・収益獲得効果 1:有,2:無,3:不明
	private Long astEffectAmount;					// 費用削減・収益獲得金額
	private String astEffectDescription;			// 費用削減効果備考
	private String astRentFlag;						// 賃借物件据付費用 0:不要,1:必要
	private String astRentApplicationId;			// リース・レンタル取得申請書番号
	private Integer astRentMonth;					// 契約月数
	private Date astRentDateFrom;					// 賃貸予定期間FROM
	private Date astRentDateTo;						// 賃貸予定期間TO
	private String astRentDescription;				// 賃借物件据付費用備考
	private Long astTotalAmount;					// 資産計上金額合計
	private Long getTotalAmount;					// 取得金額合計
	private String mktCatCategoryCode;				// 分類 市販目的ソフトウェア用 汎用マスタ-AP_GET_INT_MKT_CATEGORY
	private String mktCatCategoryName;				// 分類名
	private String mktCatTaskCode;					// タスク【会計計上区分】 市販目的ソフトウェア用 汎用マスタ-AP_GET_INT_MKT_TASK
	private String mktCatTaskName;					// タスク名
	private String mktProfEstFlag;					// 利益予測フラグ 市販目的ソフトウェア用
	private String mktAstProjectCode;				// 資産プロジェクトコード 市販目的ソフトウェア用
	private String mktAstProjectName;				// 資産プロジェクト名 市販目的ソフトウェア用
	private String mktAstProjectType;				// 資産プロジェクトタイプ 市販目的ソフトウェア用
	private String mktAstProductOutline;			// 製品の概要 市販目的ソフトウェア用
	private String mktAstMarket;					// 市場性 市販目的ソフトウェア用
	private String mktAstPolicy;					// マーケティング方針 市販目的ソフトウェア用
	private String mktDevNextPlanDateFlag;			// 次期開発完了予定日表示フラグ 市販目的ソフトウェア用
	private Date mktDevNextPlanDate;				// 次期開発完了予定日 市販目的ソフトウェア用
	private String attDescription;					// 添付補足
	private String holCompanyCode;					// 保有会社コード
	private String holCompanyName;					// 保有会社名
	private String holSectionCode;					// 保有部署コード
	private String holSectionName;					// 保有部署名
	private Integer holSectionYear;					// 保有部署年度
	private String holStaffCode;					// 無形管理担当者
	private String holStaffName;					// 無形管理担当者名
	private String holStaffCompanyCode;				// 無形管理担当者会社コード
	private String holStaffCompanyName;				// 無形管理担当者会社名
	private String holStaffSectionCode;				// 無形管理担当者部署コード
	private String holStaffSectionName;				// 無形管理担当者部署名
	private Integer holStaffSectionYear;			// 無形管理担当者部署年度
	private String holRepOfficeCode;				// 代表設置場所
	private String holRepOfficeName;				// 代表設置場所名
	private String costType;						// 販売管理費/原価区分 1:販売管理費,2:原価
	private String costDepPrjCode;					// 減価償却プロジェクトコード
	private String costDepPrjName;					// 減価償却プロジェクト名
	private String costDepPrjType;					// 減価償却プロジェクトタイプ
	private String costCompanyCode;					// 資産計上会社コード
	private String costCompanyName;					// 資産計上会社名
	private String costSectionCode;					// 資産計上部課コード
	private Integer costSectionYear;				// 資産計上部課年度
	private String costSectionName;					// 資産計上部課名
	private String descriptionAp;					// 備考_申請者記入欄
	private String descriptionAdmin;				// 備考_受付担当者・管理者記入欄
	private String reportCompleteFlag;				// サービス提供開始報告完了フラグ 0:登録残有り,1:完了
	private Date reminderDate;						// 督促メール送信日付
	private Date approveDate;						// 承認日
	private String activeFlag;						// アクティブフラグ 0:旧バージョン,1:最新バージョン
	private String verUpColumnName;					// 前バージョンからの変更項目
	private String verUpType;						// バージョンアップタイプ

	// ライセンス、ライセンス登録申請用
	private String licAssetType;					// 資産区分
	private String licAssetTypeName;				// 資産区分名

	// 明細
	private List<ApGetIntLineFld> apGetIntLineFldList;			// 資産明細
	private List<ApGetIntLineDevSch> apGetIntLineDevSchList;	// 開発スケジュール明細
	private List<ApGetIntLineOtrCost> apGetIntLineOtrCostList;	// その他費用明細
	private List<ApGetIntLineProfEst> apGetIntLineProfEstList;	// 利益予測明細
	private List<ApGetIntLineAtt> apGetIntLineAttList;			// 添付明細
	private List<ApGetIntLineCostSec> apGetIntLineCostSecList;	// 経費負担部署明細

	private String mktDevSchLineEditFlag1;				// 開発スケジュール明細編集フラグ1
	private String mktDevSchLineEditFlag2;				// 開発スケジュール明細編集フラグ2
	private String mktDevSchLineEditFlag3;				// 開発スケジュール明細編集フラグ3
	private String mktDevSchLineEditFlag4;				// 開発スケジュール明細編集フラグ4
	private String mktDevSchLineEditFlag5;				// 開発スケジュール明細編集フラグ5
	private String mktDevSchLineEditFlag6;				// 開発スケジュール明細編集フラグ6
	private String mktDevSchLineEditFlag7;				// 開発スケジュール明細編集フラグ7
	private String mktDevSchLineEditFlag8;				// 開発スケジュール明細編集フラグ8
	private String mktDevSchLineEditFlag9;				// 開発スケジュール明細編集フラグ9
	private String mktDevSchLineEditFlag10;				// 開発スケジュール明細編集フラグ10

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		applicationVersion = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		version = Function.getExternalInteger(input.readObject());
		displayVersion = Function.getExternalInteger(input.readObject());
		updateComment = (String)input.readObject();
		eappId = Function.getExternalLong(input.readObject());
		apStatus = (String)input.readObject();
		apStatusName = (String)input.readObject();
		apDate = (Date)input.readObject();
		apGetType = (String)input.readObject();
		apGetTypeName = (String)input.readObject();
		apGetAmountRange = (String)input.readObject();
		apGetAmountRangeName = (String)input.readObject();
		apGetAmountRangeUseCostSecType = (String)input.readObject();
		apGetAmountRangeUseLineType = (String)input.readObject();
		apGetAmountRangeUseCostType = (String)input.readObject();
		specialApproveFlag = (String)input.readObject();
		specialApproveDate = (Date)input.readObject();
		apCreateStaffCode = (String)input.readObject();
		apCreateStaffName = (String)input.readObject();
		apCreateCompanyCode = (String)input.readObject();
		apCreateCompanyName = (String)input.readObject();
		apCreateSectionCode = (String)input.readObject();
		apCreateSectionName = (String)input.readObject();
		apCreateSectionYear = Function.getExternalInteger(input.readObject());
		apCreateTel = (String)input.readObject();
		apStaffCode = (String)input.readObject();
		apStaffName = (String)input.readObject();
		apCompanyCode = (String)input.readObject();
		apCompanyName = (String)input.readObject();
		apSectionCode = (String)input.readObject();
		apSectionName = (String)input.readObject();
		apSectionYear = Function.getExternalInteger(input.readObject());
		apTel = (String)input.readObject();
		apSkipApproveFlag = (String)input.readObject();
		apNeedCioJudgeFlag = (String)input.readObject();
		astGetTimeFlag = (String)input.readObject();
		astName = (String)input.readObject();
		astCloudType = (String)input.readObject();
		astGroupCode = (String)input.readObject();
		astGroupName = (String)input.readObject();
		astGetReason = (String)input.readObject();
		astCompletePlanDate = (Date)input.readObject();
		astEffectType = (String)input.readObject();
		astEffectAmount = Function.getExternalLong(input.readObject());
		astEffectDescription = (String)input.readObject();
		astRentFlag = (String)input.readObject();
		astRentApplicationId = (String)input.readObject();
		astRentMonth = Function.getExternalInteger(input.readObject());
		astRentDateFrom = (Date)input.readObject();
		astRentDateTo = (Date)input.readObject();
		astRentDescription = (String)input.readObject();
		astTotalAmount = Function.getExternalLong(input.readObject());
		getTotalAmount = Function.getExternalLong(input.readObject());
		mktCatCategoryCode = (String)input.readObject();
		mktCatCategoryName = (String)input.readObject();
		mktCatTaskCode = (String)input.readObject();
		mktCatTaskName = (String)input.readObject();
		mktProfEstFlag = (String)input.readObject();
		mktAstProjectCode = (String)input.readObject();
		mktAstProjectName = (String)input.readObject();
		mktAstProjectType = (String)input.readObject();
		mktAstProductOutline = (String)input.readObject();
		mktAstMarket = (String)input.readObject();
		mktAstPolicy = (String)input.readObject();
		mktDevNextPlanDateFlag = (String)input.readObject();
		mktDevNextPlanDate = (Date)input.readObject();
		attDescription = (String)input.readObject();
		holCompanyCode = (String)input.readObject();
		holCompanyName = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionName = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holStaffCode = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holStaffCompanyCode = (String)input.readObject();
		holStaffCompanyName = (String)input.readObject();
		holStaffSectionCode = (String)input.readObject();
		holStaffSectionName = (String)input.readObject();
		holStaffSectionYear = Function.getExternalInteger(input.readObject());
		holRepOfficeCode = (String)input.readObject();
		holRepOfficeName = (String)input.readObject();
		costType = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costDepPrjName = (String)input.readObject();
		costDepPrjType = (String)input.readObject();
		costCompanyCode = (String)input.readObject();
		costCompanyName = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		costSectionYear = Function.getExternalInteger(input.readObject());
		costSectionName = (String)input.readObject();
		descriptionAp = (String)input.readObject();
		descriptionAdmin = (String)input.readObject();
		reportCompleteFlag = (String)input.readObject();
		reminderDate = (Date)input.readObject();
		approveDate = (Date)input.readObject();
		activeFlag = (String)input.readObject();
		verUpColumnName = (String)input.readObject();
		verUpType = (String)input.readObject();

		licAssetType = (String)input.readObject();
		licAssetTypeName = (String)input.readObject();

		apGetIntLineFldList = (List<ApGetIntLineFld>)input.readObject();
		apGetIntLineDevSchList = (List<ApGetIntLineDevSch>)input.readObject();
		apGetIntLineOtrCostList = (List<ApGetIntLineOtrCost>)input.readObject();
		apGetIntLineProfEstList = (List<ApGetIntLineProfEst>)input.readObject();
		apGetIntLineAttList = (List<ApGetIntLineAtt>)input.readObject();
		apGetIntLineCostSecList = (List<ApGetIntLineCostSec>)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(applicationVersion);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(version);
		output.writeObject(displayVersion);
		output.writeObject(updateComment);
		output.writeObject(eappId);
		output.writeObject(apStatus);
		output.writeObject(apStatusName);
		output.writeObject(apDate);
		output.writeObject(apGetType);
		output.writeObject(apGetTypeName);
		output.writeObject(apGetAmountRange);
		output.writeObject(apGetAmountRangeName);
		output.writeObject(apGetAmountRangeUseCostSecType);
		output.writeObject(apGetAmountRangeUseLineType);
		output.writeObject(apGetAmountRangeUseCostType);
		output.writeObject(specialApproveFlag);
		output.writeObject(specialApproveDate);
		output.writeObject(apCreateStaffCode);
		output.writeObject(apCreateStaffName);
		output.writeObject(apCreateCompanyCode);
		output.writeObject(apCreateCompanyName);
		output.writeObject(apCreateSectionCode);
		output.writeObject(apCreateSectionName);
		output.writeObject(apCreateSectionYear);
		output.writeObject(apCreateTel);
		output.writeObject(apStaffCode);
		output.writeObject(apStaffName);
		output.writeObject(apCompanyCode);
		output.writeObject(apCompanyName);
		output.writeObject(apSectionCode);
		output.writeObject(apSectionName);
		output.writeObject(apSectionYear);
		output.writeObject(apTel);
		output.writeObject(apSkipApproveFlag);
		output.writeObject(apNeedCioJudgeFlag);
		output.writeObject(astGetTimeFlag);
		output.writeObject(astName);
		output.writeObject(astCloudType);
		output.writeObject(astGroupCode);
		output.writeObject(astGroupName);
		output.writeObject(astGetReason);
		output.writeObject(astCompletePlanDate);
		output.writeObject(astEffectType);
		output.writeObject(astEffectAmount);
		output.writeObject(astEffectDescription);
		output.writeObject(astRentFlag);
		output.writeObject(astRentApplicationId);
		output.writeObject(astRentMonth);
		output.writeObject(astRentDateFrom);
		output.writeObject(astRentDateTo);
		output.writeObject(astRentDescription);
		output.writeObject(astTotalAmount);
		output.writeObject(getTotalAmount);
		output.writeObject(mktCatCategoryCode);
		output.writeObject(mktCatCategoryName);
		output.writeObject(mktCatTaskCode);
		output.writeObject(mktCatTaskName);
		output.writeObject(mktProfEstFlag);
		output.writeObject(mktAstProjectCode);
		output.writeObject(mktAstProjectName);
		output.writeObject(mktAstProjectType);
		output.writeObject(mktAstProductOutline);
		output.writeObject(mktAstMarket);
		output.writeObject(mktAstPolicy);
		output.writeObject(mktDevNextPlanDateFlag);
		output.writeObject(mktDevNextPlanDate);
		output.writeObject(attDescription);
		output.writeObject(holCompanyCode);
		output.writeObject(holCompanyName);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionName);
		output.writeObject(holSectionYear);
		output.writeObject(holStaffCode);
		output.writeObject(holStaffName);
		output.writeObject(holStaffCompanyCode);
		output.writeObject(holStaffCompanyName);
		output.writeObject(holStaffSectionCode);
		output.writeObject(holStaffSectionName);
		output.writeObject(holStaffSectionYear);
		output.writeObject(holRepOfficeCode);
		output.writeObject(holRepOfficeName);
		output.writeObject(costType);
		output.writeObject(costDepPrjCode);
		output.writeObject(costDepPrjName);
		output.writeObject(costDepPrjType);
		output.writeObject(costCompanyCode);
		output.writeObject(costCompanyName);
		output.writeObject(costSectionCode);
		output.writeObject(costSectionYear);
		output.writeObject(costSectionName);
		output.writeObject(descriptionAp);
		output.writeObject(descriptionAdmin);
		output.writeObject(reportCompleteFlag);
		output.writeObject(reminderDate);
		output.writeObject(approveDate);
		output.writeObject(activeFlag);
		output.writeObject(verUpColumnName);
		output.writeObject(verUpType);

		output.writeObject(licAssetType);
		output.writeObject(licAssetTypeName);

		output.writeObject(apGetIntLineFldList);
		output.writeObject(apGetIntLineDevSchList);
		output.writeObject(apGetIntLineOtrCostList);
		output.writeObject(apGetIntLineProfEstList);
		output.writeObject(apGetIntLineAttList);
		output.writeObject(apGetIntLineCostSecList);
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
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

	public String getApGetType() {
		return apGetType;
	}

	public void setApGetType(String apGetType) {
		this.apGetType = apGetType;
	}

	public String getApGetAmountRange() {
		return apGetAmountRange;
	}

	public void setApGetAmountRange(String apGetAmountRange) {
		this.apGetAmountRange = apGetAmountRange;
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

	public String getAstGetTimeFlag() {
		return astGetTimeFlag;
	}

	public void setAstGetTimeFlag(String astGetTimeFlag) {
		this.astGetTimeFlag = astGetTimeFlag;
	}

	public String getAstName() {
		return astName;
	}

	public void setAstName(String astName) {
		this.astName = astName;
	}

	public String getAstCloudType() {
		return astCloudType;
	}

	public void setAstCloudType(String astCloudType) {
		this.astCloudType = astCloudType;
	}

	public String getAstGroupCode() {
		return astGroupCode;
	}

	public void setAstGroupCode(String astGroupCode) {
		this.astGroupCode = astGroupCode;
	}

	public String getAstGetReason() {
		return astGetReason;
	}

	public void setAstGetReason(String astGetReason) {
		this.astGetReason = astGetReason;
	}

	public Date getAstCompletePlanDate() {
		return astCompletePlanDate;
	}

	public void setAstCompletePlanDate(Date astCompletePlanDate) {
		this.astCompletePlanDate = astCompletePlanDate;
	}

	public String getAstEffectType() {
		return astEffectType;
	}

	public void setAstEffectType(String astEffectType) {
		this.astEffectType = astEffectType;
	}

	public Long getAstEffectAmount() {
		return astEffectAmount;
	}

	public void setAstEffectAmount(Long astEffectAmount) {
		this.astEffectAmount = astEffectAmount;
	}

	public String getAstEffectDescription() {
		return astEffectDescription;
	}

	public void setAstEffectDescription(String astEffectDescription) {
		this.astEffectDescription = astEffectDescription;
	}

	public String getAstRentFlag() {
		return astRentFlag;
	}

	public void setAstRentFlag(String astRentFlag) {
		this.astRentFlag = astRentFlag;
	}

	public String getAstRentApplicationId() {
		return astRentApplicationId;
	}

	public void setAstRentApplicationId(String astRentApplicationId) {
		this.astRentApplicationId = astRentApplicationId;
	}

	public Integer getAstRentMonth() {
		return astRentMonth;
	}

	public void setAstRentMonth(Integer astRentMonth) {
		this.astRentMonth = astRentMonth;
	}

	public Date getAstRentDateFrom() {
		return astRentDateFrom;
	}

	public void setAstRentDateFrom(Date astRentDateFrom) {
		this.astRentDateFrom = astRentDateFrom;
	}

	public Date getAstRentDateTo() {
		return astRentDateTo;
	}

	public void setAstRentDateTo(Date astRentDateTo) {
		this.astRentDateTo = astRentDateTo;
	}

	public String getAstRentDescription() {
		return astRentDescription;
	}

	public void setAstRentDescription(String astRentDescription) {
		this.astRentDescription = astRentDescription;
	}

	public Long getAstTotalAmount() {
		return astTotalAmount;
	}

	public void setAstTotalAmount(Long astTotalAmount) {
		this.astTotalAmount = astTotalAmount;
	}

	public Long getGetTotalAmount() {
		return getTotalAmount;
	}

	public void setGetTotalAmount(Long getTotalAmount) {
		this.getTotalAmount = getTotalAmount;
	}

	public String getMktCatCategoryCode() {
		return mktCatCategoryCode;
	}

	public void setMktCatCategoryCode(String mktCatCategoryCode) {
		this.mktCatCategoryCode = mktCatCategoryCode;
	}

	public String getMktCatTaskCode() {
		return mktCatTaskCode;
	}

	public void setMktCatTaskCode(String mktCatTaskCode) {
		this.mktCatTaskCode = mktCatTaskCode;
	}

	public String getMktAstProjectCode() {
		return mktAstProjectCode;
	}

	public void setMktAstProjectCode(String mktAstProjectCode) {
		this.mktAstProjectCode = mktAstProjectCode;
	}

	public String getMktAstProductOutline() {
		return mktAstProductOutline;
	}

	public void setMktAstProductOutline(String mktAstProductOutline) {
		this.mktAstProductOutline = mktAstProductOutline;
	}

	public String getMktAstMarket() {
		return mktAstMarket;
	}

	public void setMktAstMarket(String mktAstMarket) {
		this.mktAstMarket = mktAstMarket;
	}

	public String getMktAstPolicy() {
		return mktAstPolicy;
	}

	public void setMktAstPolicy(String mktAstPolicy) {
		this.mktAstPolicy = mktAstPolicy;
	}

	public Date getMktDevNextPlanDate() {
		return mktDevNextPlanDate;
	}

	public void setMktDevNextPlanDate(Date mktDevNextPlanDate) {
		this.mktDevNextPlanDate = mktDevNextPlanDate;
	}

	public String getAttDescription() {
		return attDescription;
	}

	public void setAttDescription(String attDescription) {
		this.attDescription = attDescription;
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

	public String getHolStaffCompanyCode() {
		return holStaffCompanyCode;
	}

	public void setHolStaffCompanyCode(String holStaffCompanyCode) {
		this.holStaffCompanyCode = holStaffCompanyCode;
	}

	public String getHolStaffSectionCode() {
		return holStaffSectionCode;
	}

	public void setHolStaffSectionCode(String holStaffSectionCode) {
		this.holStaffSectionCode = holStaffSectionCode;
	}

	public Integer getHolStaffSectionYear() {
		return holStaffSectionYear;
	}

	public void setHolStaffSectionYear(Integer holStaffSectionYear) {
		this.holStaffSectionYear = holStaffSectionYear;
	}

	public String getHolRepOfficeCode() {
		return holRepOfficeCode;
	}

	public void setHolRepOfficeCode(String holRepOfficeCode) {
		this.holRepOfficeCode = holRepOfficeCode;
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

	public String getReportCompleteFlag() {
		return reportCompleteFlag;
	}

	public void setReportCompleteFlag(String reportCompleteFlag) {
		this.reportCompleteFlag = reportCompleteFlag;
	}

	public Date getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getVerUpColumnName() {
		return verUpColumnName;
	}

	public void setVerUpColumnName(String verUpColumnName) {
		this.verUpColumnName = verUpColumnName;
	}

	public List<ApGetIntLineOtrCost> getApGetIntLineOtrCostList() {
		return apGetIntLineOtrCostList;
	}

	public void setApGetIntLineOtrCostList(
			List<ApGetIntLineOtrCost> apGetIntLineOtrCostList) {
		this.apGetIntLineOtrCostList = apGetIntLineOtrCostList;
	}

	public List<ApGetIntLineAtt> getApGetIntLineAttList() {
		return apGetIntLineAttList;
	}

	public void setApGetIntLineAttList(List<ApGetIntLineAtt> apGetIntLineAttList) {
		this.apGetIntLineAttList = apGetIntLineAttList;
	}

	public List<ApGetIntLineCostSec> getApGetIntLineCostSecList() {
		return apGetIntLineCostSecList;
	}

	public void setApGetIntLineCostSecList(
			List<ApGetIntLineCostSec> apGetIntLineCostSecList) {
		this.apGetIntLineCostSecList = apGetIntLineCostSecList;
	}

	public String getApStatusName() {
		return apStatusName;
	}

	public void setApStatusName(String apStatusName) {
		this.apStatusName = apStatusName;
	}

	public String getApGetTypeName() {
		return apGetTypeName;
	}

	public void setApGetTypeName(String apGetTypeName) {
		this.apGetTypeName = apGetTypeName;
	}

	public String getApGetAmountRangeName() {
		return apGetAmountRangeName;
	}

	public void setApGetAmountRangeName(String apGetAmountRangeName) {
		this.apGetAmountRangeName = apGetAmountRangeName;
	}

	public String getApCreateStaffName() {
		return apCreateStaffName;
	}

	public void setApCreateStaffName(String apCreateStaffName) {
		this.apCreateStaffName = apCreateStaffName;
	}

	public String getApCreateCompanyName() {
		return apCreateCompanyName;
	}

	public void setApCreateCompanyName(String apCreateCompanyName) {
		this.apCreateCompanyName = apCreateCompanyName;
	}

	public String getApCreateSectionName() {
		return apCreateSectionName;
	}

	public void setApCreateSectionName(String apCreateSectionName) {
		this.apCreateSectionName = apCreateSectionName;
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

	public String getHolStaffName() {
		return holStaffName;
	}

	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}

	public String getHolStaffCompanyName() {
		return holStaffCompanyName;
	}

	public void setHolStaffCompanyName(String holStaffCompanyName) {
		this.holStaffCompanyName = holStaffCompanyName;
	}

	public String getHolStaffSectionName() {
		return holStaffSectionName;
	}

	public void setHolStaffSectionName(String holStaffSectionName) {
		this.holStaffSectionName = holStaffSectionName;
	}

	public String getHolCompanyName() {
		return holCompanyName;
	}

	public void setHolCompanyName(String holCompanyName) {
		this.holCompanyName = holCompanyName;
	}

	public String getHolSectionName() {
		return holSectionName;
	}

	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}

	public String getHolRepOfficeName() {
		return holRepOfficeName;
	}

	public void setHolRepOfficeName(String holRepOfficeName) {
		this.holRepOfficeName = holRepOfficeName;
	}

	public String getAstGroupName() {
		return astGroupName;
	}

	public void setAstGroupName(String astGroupName) {
		this.astGroupName = astGroupName;
	}

	public String getCostDepPrjName() {
		return costDepPrjName;
	}

	public void setCostDepPrjName(String costDepPrjName) {
		this.costDepPrjName = costDepPrjName;
	}

	public String getCostSectionName() {
		return costSectionName;
	}

	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	public String getApCreateTel() {
		return apCreateTel;
	}

	public void setApCreateTel(String apCreateTel) {
		this.apCreateTel = apCreateTel;
	}

	public String getMktCatCategoryName() {
		return mktCatCategoryName;
	}

	public void setMktCatCategoryName(String mktCatCategoryName) {
		this.mktCatCategoryName = mktCatCategoryName;
	}

	public String getMktCatTaskName() {
		return mktCatTaskName;
	}

	public void setMktCatTaskName(String mktCatTaskName) {
		this.mktCatTaskName = mktCatTaskName;
	}

	public String getVerUpType() {
		return verUpType;
	}

	public void setVerUpType(String verUpType) {
		this.verUpType = verUpType;
	}

	public List<ApGetIntLineFld> getApGetIntLineFldList() {
		return apGetIntLineFldList;
	}

	public void setApGetIntLineFldList(List<ApGetIntLineFld> apGetIntLineFldList) {
		this.apGetIntLineFldList = apGetIntLineFldList;
	}

	public String getMktAstProjectName() {
		return mktAstProjectName;
	}

	public void setMktAstProjectName(String mktAstProjectName) {
		this.mktAstProjectName = mktAstProjectName;
	}

	public String getMktAstProjectType() {
		return mktAstProjectType;
	}

	public void setMktAstProjectType(String mktAstProjectType) {
		this.mktAstProjectType = mktAstProjectType;
	}

	public String getApGetAmountRangeUseCostSecType() {
		return apGetAmountRangeUseCostSecType;
	}

	public void setApGetAmountRangeUseCostSecType(
			String apGetAmountRangeUseCostSecType) {
		this.apGetAmountRangeUseCostSecType = apGetAmountRangeUseCostSecType;
	}

	public String getApGetAmountRangeUseLineType() {
		return apGetAmountRangeUseLineType;
	}

	public void setApGetAmountRangeUseLineType(String apGetAmountRangeUseLineType) {
		this.apGetAmountRangeUseLineType = apGetAmountRangeUseLineType;
	}

	public String getApGetAmountRangeUseCostType() {
		return apGetAmountRangeUseCostType;
	}

	public void setApGetAmountRangeUseCostType(String apGetAmountRangeUseCostType) {
		this.apGetAmountRangeUseCostType = apGetAmountRangeUseCostType;
	}

	public String getCostDepPrjType() {
		return costDepPrjType;
	}

	public void setCostDepPrjType(String costDepPrjType) {
		this.costDepPrjType = costDepPrjType;
	}

	public String getCostCompanyName() {
		return costCompanyName;
	}

	public void setCostCompanyName(String costCompanyName) {
		this.costCompanyName = costCompanyName;
	}

	public List<ApGetIntLineDevSch> getApGetIntLineDevSchList() {
		return apGetIntLineDevSchList;
	}

	public void setApGetIntLineDevSchList(
			List<ApGetIntLineDevSch> apGetIntLineDevSchList) {
		this.apGetIntLineDevSchList = apGetIntLineDevSchList;
	}

	public List<ApGetIntLineProfEst> getApGetIntLineProfEstList() {
		return apGetIntLineProfEstList;
	}

	public void setApGetIntLineProfEstList(
			List<ApGetIntLineProfEst> apGetIntLineProfEstList) {
		this.apGetIntLineProfEstList = apGetIntLineProfEstList;
	}

	public String getMktProfEstFlag() {
		return mktProfEstFlag;
	}

	public void setMktProfEstFlag(String mktProfEstFlag) {
		this.mktProfEstFlag = mktProfEstFlag;
	}

	public String getMktDevNextPlanDateFlag() {
		return mktDevNextPlanDateFlag;
	}

	public void setMktDevNextPlanDateFlag(String mktDevNextPlanDateFlag) {
		this.mktDevNextPlanDateFlag = mktDevNextPlanDateFlag;
	}

	public String getMktDevSchLineEditFlag1() {
		return mktDevSchLineEditFlag1;
	}

	public void setMktDevSchLineEditFlag1(String mktDevSchLineEditFlag1) {
		this.mktDevSchLineEditFlag1 = mktDevSchLineEditFlag1;
	}

	public String getMktDevSchLineEditFlag2() {
		return mktDevSchLineEditFlag2;
	}

	public void setMktDevSchLineEditFlag2(String mktDevSchLineEditFlag2) {
		this.mktDevSchLineEditFlag2 = mktDevSchLineEditFlag2;
	}

	public String getMktDevSchLineEditFlag3() {
		return mktDevSchLineEditFlag3;
	}

	public void setMktDevSchLineEditFlag3(String mktDevSchLineEditFlag3) {
		this.mktDevSchLineEditFlag3 = mktDevSchLineEditFlag3;
	}

	public String getMktDevSchLineEditFlag4() {
		return mktDevSchLineEditFlag4;
	}

	public void setMktDevSchLineEditFlag4(String mktDevSchLineEditFlag4) {
		this.mktDevSchLineEditFlag4 = mktDevSchLineEditFlag4;
	}

	public String getMktDevSchLineEditFlag5() {
		return mktDevSchLineEditFlag5;
	}

	public void setMktDevSchLineEditFlag5(String mktDevSchLineEditFlag5) {
		this.mktDevSchLineEditFlag5 = mktDevSchLineEditFlag5;
	}

	public String getMktDevSchLineEditFlag6() {
		return mktDevSchLineEditFlag6;
	}

	public void setMktDevSchLineEditFlag6(String mktDevSchLineEditFlag6) {
		this.mktDevSchLineEditFlag6 = mktDevSchLineEditFlag6;
	}

	public String getMktDevSchLineEditFlag7() {
		return mktDevSchLineEditFlag7;
	}

	public void setMktDevSchLineEditFlag7(String mktDevSchLineEditFlag7) {
		this.mktDevSchLineEditFlag7 = mktDevSchLineEditFlag7;
	}

	public String getMktDevSchLineEditFlag8() {
		return mktDevSchLineEditFlag8;
	}

	public void setMktDevSchLineEditFlag8(String mktDevSchLineEditFlag8) {
		this.mktDevSchLineEditFlag8 = mktDevSchLineEditFlag8;
	}

	public String getMktDevSchLineEditFlag9() {
		return mktDevSchLineEditFlag9;
	}

	public void setMktDevSchLineEditFlag9(String mktDevSchLineEditFlag9) {
		this.mktDevSchLineEditFlag9 = mktDevSchLineEditFlag9;
	}

	public String getMktDevSchLineEditFlag10() {
		return mktDevSchLineEditFlag10;
	}

	public void setMktDevSchLineEditFlag10(String mktDevSchLineEditFlag10) {
		this.mktDevSchLineEditFlag10 = mktDevSchLineEditFlag10;
	}

	public String getLicAssetType() {
		return licAssetType;
	}

	public void setLicAssetType(String licAssetType) {
		this.licAssetType = licAssetType;
	}

	public String getLicAssetTypeName() {
		return licAssetTypeName;
	}

	public void setLicAssetTypeName(String licAssetTypeName) {
		this.licAssetTypeName = licAssetTypeName;
	}

	public Integer getCostSectionYear() {
		return costSectionYear;
	}

	public void setCostSectionYear(Integer costSectionYear) {
		this.costSectionYear = costSectionYear;
	}
}
