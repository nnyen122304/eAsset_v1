/*===================================================================
 * ファイル名 : ApBgnInt.java
 * 概要説明   : サービス提供開始報告
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-10-15 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_bgn_int;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApBgnInt implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId;					// 申請書番号
	private Date createDate;						// 作成日
	private String createStaffCode;					// 作成者社員番号
	private Date updateDate;						// 更新日
	private String updateStaffCode;					// 更新者社員番号
	private String applicationVersion;				// 申請書バージョン
	private Integer version;						// バージョン
	private Integer displayVersion;					// 改定番号
	private String updateComment;					// 更新コメント
	private Long eappId;							// e申請書類ID
	private String apStatus;						// 申請書ステータス 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下,6:取下げ 汎用マスタ-AP_STATUS_BGN_INT
	private String apStatusName;					// 申請書ステータス名
	private Date apDate;							// 申請日
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
	private String apGetType;						// 取得形態 汎用マスタ-AP_GET_INT_TYPE
	private String apGetTypeName;					// 取得形態名
	private String astName;							// 資産名
	private String astPrjCode;						// 資産プロジェクトコード
	private String astPrjName;						// 資産プロジェクト名
	private String astPrjType;						// 資産プロジェクトタイプ
	private String astCloudType;					// クラウド区分 1:クラウド以外,2:クラウド
	private Integer astPrjLife;						// プロジェクトライフ
	private String astGroupCode;					// 案件グループコード
	private String astGroupName;					// 案件グループ名
	private String astMachineCode;					// 機種コード
	private String astProductCode;					// プロダクトコード
	private Long astRealAmount;						// 資産計上額合計(実際額)
	private Long astAppAmount;						// 資産計上額合計(申請額)
	private Long getRealAmount;						// 取得価額合計(実際額)
	private Long getAppAmount;						// 取得価額合計(申請額)
	private Date astCompleteDate;					// 開発完了日
	private Date astReleaseDate;					// リリース日・販売(サービス)開始日
	private String mktCatCategoryCode;				// 分類 市販目的ソフトウェア用 汎用マスタ-AP_GET_INT_MKT_CATEGORY
	private String mktCatCategoryName;				// 分類名
	private String astDescription;					// 証憑の内容説明・内容・成果
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
	private Date approveDate;						// 承認日

	// 明細
	private List<ApBgnIntLineFld> apBgnIntLineFldList;			// 資産明細
	private List<ApBgnIntLineProfEst> apBgnIntLineProfEstList;	// 利益予測明細
	private List<ApBgnIntLineAtt> apBgnIntLineAttList;			// 添付明細
	private List<ApBgnIntLineCostSec> apBgnIntLineCostSecList;	// 経費負担部署明細

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		applicationId = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		applicationVersion = (String)input.readObject();
		version = Function.getExternalInteger(input.readObject());
		displayVersion = Function.getExternalInteger(input.readObject());
		updateComment = (String)input.readObject();
		eappId = Function.getExternalLong(input.readObject());
		apStatus = (String)input.readObject();
		apStatusName = (String)input.readObject();
		apDate = (Date)input.readObject();
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
		apGetType = (String)input.readObject();
		apGetTypeName = (String)input.readObject();
		astName = (String)input.readObject();
		astPrjCode = (String)input.readObject();
		astPrjName = (String)input.readObject();
		astPrjType = (String)input.readObject();
		astCloudType = (String)input.readObject();
		astPrjLife = Function.getExternalInteger(input.readObject());
		astGroupCode = (String)input.readObject();
		astGroupName = (String)input.readObject();
		astMachineCode = (String)input.readObject();
		astProductCode = (String)input.readObject();
		astRealAmount = Function.getExternalLong(input.readObject());
		astAppAmount = Function.getExternalLong(input.readObject());
		getRealAmount = Function.getExternalLong(input.readObject());
		getAppAmount = Function.getExternalLong(input.readObject());
		astCompleteDate = (Date)input.readObject();
		astReleaseDate = (Date)input.readObject();
		mktCatCategoryCode = (String)input.readObject();
		mktCatCategoryName = (String)input.readObject();
		astDescription = (String)input.readObject();
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
		approveDate = (Date)input.readObject();

		apBgnIntLineFldList = (List<ApBgnIntLineFld>)input.readObject();
		apBgnIntLineProfEstList = (List<ApBgnIntLineProfEst>)input.readObject();
		apBgnIntLineAttList = (List<ApBgnIntLineAtt>)input.readObject();
		apBgnIntLineCostSecList = (List<ApBgnIntLineCostSec>)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		output.writeObject(applicationId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(applicationVersion);
		output.writeObject(version);
		output.writeObject(displayVersion);
		output.writeObject(updateComment);
		output.writeObject(eappId);
		output.writeObject(apStatus);
		output.writeObject(apStatusName);
		output.writeObject(apDate);
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
		output.writeObject(apGetType);
		output.writeObject(apGetTypeName);
		output.writeObject(astName);
		output.writeObject(astPrjCode);
		output.writeObject(astPrjName);
		output.writeObject(astPrjType);
		output.writeObject(astCloudType);
		output.writeObject(astPrjLife);
		output.writeObject(astGroupCode);
		output.writeObject(astGroupName);
		output.writeObject(astMachineCode);
		output.writeObject(astProductCode);
		output.writeObject(astRealAmount);
		output.writeObject(astAppAmount);
		output.writeObject(getRealAmount);
		output.writeObject(getAppAmount);
		output.writeObject(astCompleteDate);
		output.writeObject(astReleaseDate);
		output.writeObject(mktCatCategoryCode);
		output.writeObject(mktCatCategoryName);
		output.writeObject(astDescription);
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
		output.writeObject(approveDate);

		output.writeObject(apBgnIntLineFldList);
		output.writeObject(apBgnIntLineProfEstList);
		output.writeObject(apBgnIntLineAttList);
		output.writeObject(apBgnIntLineCostSecList);
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

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
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

	public String getApStatusName() {
		return apStatusName;
	}

	public void setApStatusName(String apStatusName) {
		this.apStatusName = apStatusName;
	}

	public Date getApDate() {
		return apDate;
	}

	public void setApDate(Date apDate) {
		this.apDate = apDate;
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

	public String getApGetType() {
		return apGetType;
	}

	public void setApGetType(String apGetType) {
		this.apGetType = apGetType;
	}

	public String getApGetTypeName() {
		return apGetTypeName;
	}

	public void setApGetTypeName(String apGetTypeName) {
		this.apGetTypeName = apGetTypeName;
	}

	public String getAstName() {
		return astName;
	}

	public void setAstName(String astName) {
		this.astName = astName;
	}

	public String getAstPrjCode() {
		return astPrjCode;
	}

	public void setAstPrjCode(String astPrjCode) {
		this.astPrjCode = astPrjCode;
	}

	public String getAstPrjName() {
		return astPrjName;
	}

	public void setAstPrjName(String astPrjName) {
		this.astPrjName = astPrjName;
	}

	public String getAstPrjType() {
		return astPrjType;
	}

	public void setAstPrjType(String astPrjType) {
		this.astPrjType = astPrjType;
	}

	public String getAstCloudType() {
		return astCloudType;
	}

	public void setAstCloudType(String astCloudType) {
		this.astCloudType = astCloudType;
	}

	public Integer getAstPrjLife() {
		return astPrjLife;
	}

	public void setAstPrjLife(Integer astPrjLife) {
		this.astPrjLife = astPrjLife;
	}

	public String getAstGroupCode() {
		return astGroupCode;
	}

	public void setAstGroupCode(String astGroupCode) {
		this.astGroupCode = astGroupCode;
	}

	public String getAstGroupName() {
		return astGroupName;
	}

	public void setAstGroupName(String astGroupName) {
		this.astGroupName = astGroupName;
	}

	public String getAstMachineCode() {
		return astMachineCode;
	}

	public void setAstMachineCode(String astMachineCode) {
		this.astMachineCode = astMachineCode;
	}

	public String getAstProductCode() {
		return astProductCode;
	}

	public void setAstProductCode(String astProductCode) {
		this.astProductCode = astProductCode;
	}

	public Long getAstRealAmount() {
		return astRealAmount;
	}

	public void setAstRealAmount(Long astRealAmount) {
		this.astRealAmount = astRealAmount;
	}

	public Long getAstAppAmount() {
		return astAppAmount;
	}

	public void setAstAppAmount(Long astAppAmount) {
		this.astAppAmount = astAppAmount;
	}

	public Long getGetRealAmount() {
		return getRealAmount;
	}

	public void setGetRealAmount(Long getRealAmount) {
		this.getRealAmount = getRealAmount;
	}

	public Date getAstCompleteDate() {
		return astCompleteDate;
	}

	public void setAstCompleteDate(Date astCompleteDate) {
		this.astCompleteDate = astCompleteDate;
	}

	public Date getAstReleaseDate() {
		return astReleaseDate;
	}

	public void setAstReleaseDate(Date astReleaseDate) {
		this.astReleaseDate = astReleaseDate;
	}

	public String getMktCatCategoryCode() {
		return mktCatCategoryCode;
	}

	public void setMktCatCategoryCode(String mktCatCategoryCode) {
		this.mktCatCategoryCode = mktCatCategoryCode;
	}

	public String getMktCatCategoryName() {
		return mktCatCategoryName;
	}

	public void setMktCatCategoryName(String mktCatCategoryName) {
		this.mktCatCategoryName = mktCatCategoryName;
	}

	public String getAstDescription() {
		return astDescription;
	}

	public void setAstDescription(String astDescription) {
		this.astDescription = astDescription;
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

	public String getHolStaffName() {
		return holStaffName;
	}

	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}

	public String getHolStaffCompanyCode() {
		return holStaffCompanyCode;
	}

	public void setHolStaffCompanyCode(String holStaffCompanyCode) {
		this.holStaffCompanyCode = holStaffCompanyCode;
	}

	public String getHolStaffCompanyName() {
		return holStaffCompanyName;
	}

	public void setHolStaffCompanyName(String holStaffCompanyName) {
		this.holStaffCompanyName = holStaffCompanyName;
	}

	public String getHolStaffSectionCode() {
		return holStaffSectionCode;
	}

	public void setHolStaffSectionCode(String holStaffSectionCode) {
		this.holStaffSectionCode = holStaffSectionCode;
	}

	public String getHolStaffSectionName() {
		return holStaffSectionName;
	}

	public void setHolStaffSectionName(String holStaffSectionName) {
		this.holStaffSectionName = holStaffSectionName;
	}

	public Integer getHolStaffSectionYear() {
		return holStaffSectionYear;
	}

	public void setHolStaffSectionYear(Integer holStaffSectionYear) {
		this.holStaffSectionYear = holStaffSectionYear;
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

	public String getCostDepPrjType() {
		return costDepPrjType;
	}

	public void setCostDepPrjType(String costDepPrjType) {
		this.costDepPrjType = costDepPrjType;
	}

	public String getCostCompanyCode() {
		return costCompanyCode;
	}

	public void setCostCompanyCode(String costCompanyCode) {
		this.costCompanyCode = costCompanyCode;
	}

	public String getCostCompanyName() {
		return costCompanyName;
	}

	public void setCostCompanyName(String costCompanyName) {
		this.costCompanyName = costCompanyName;
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

	public Date getApproveDate() {
		return approveDate;
	}

	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	public List<ApBgnIntLineFld> getApBgnIntLineFldList() {
		return apBgnIntLineFldList;
	}

	public void setApBgnIntLineFldList(List<ApBgnIntLineFld> apBgnIntLineFldList) {
		this.apBgnIntLineFldList = apBgnIntLineFldList;
	}

	public List<ApBgnIntLineProfEst> getApBgnIntLineProfEstList() {
		return apBgnIntLineProfEstList;
	}

	public void setApBgnIntLineProfEstList(
			List<ApBgnIntLineProfEst> apBgnIntLineProfEstList) {
		this.apBgnIntLineProfEstList = apBgnIntLineProfEstList;
	}

	public List<ApBgnIntLineAtt> getApBgnIntLineAttList() {
		return apBgnIntLineAttList;
	}

	public void setApBgnIntLineAttList(List<ApBgnIntLineAtt> apBgnIntLineAttList) {
		this.apBgnIntLineAttList = apBgnIntLineAttList;
	}

	public List<ApBgnIntLineCostSec> getApBgnIntLineCostSecList() {
		return apBgnIntLineCostSecList;
	}

	public void setApBgnIntLineCostSecList(
			List<ApBgnIntLineCostSec> apBgnIntLineCostSecList) {
		this.apBgnIntLineCostSecList = apBgnIntLineCostSecList;
	}

	public String getHolRepOfficeCode() {
		return holRepOfficeCode;
	}

	public void setHolRepOfficeCode(String holRepOfficeCode) {
		this.holRepOfficeCode = holRepOfficeCode;
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

	public Long getGetAppAmount() {
		return getAppAmount;
	}

	public void setGetAppAmount(Long getAppAmount) {
		this.getAppAmount = getAppAmount;
	}

	public String getHolRepOfficeName() {
		return holRepOfficeName;
	}

	public void setHolRepOfficeName(String holRepOfficeName) {
		this.holRepOfficeName = holRepOfficeName;
	}

	public String getApCreateTel() {
		return apCreateTel;
	}

	public void setApCreateTel(String apCreateTel) {
		this.apCreateTel = apCreateTel;
	}

	public Integer getCostSectionYear() {
		return costSectionYear;
	}

	public void setCostSectionYear(Integer costSectionYear) {
		this.costSectionYear = costSectionYear;
	}
}
