/*===================================================================
 * ファイル名 : ApGetIntLineFld.java
 * 概要説明   : 取得申請(無形)_資産明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_get_int;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApGetIntLineFld extends LineData {
	private static final long serialVersionUID = 1L;

	private String applicationId;			// 申請書番号
	private String applicationVersion;		// 申請書バージョン
	private Integer lineSeq;				// 行シーケンス
	private Date createDate;				// 作成日
	private String createStaffCode;			// 作成者社員番号
	private Date updateDate;				// 更新日
	private String updateStaffCode;			// 更新者社員番号
	private String astCategoryCode;			// 分類 汎用マスタ-AP_GET_INT_AST_CATEGORY
	private String astCategoryName;			// 分類名
	private String astCategoryType;			// 分類タイプ
	private String astTermFlag;				// 期間ライセンスフラグ 0:その他,1:期間ライセンス
	private Integer astTermYear;			// 期間(年)
	private String astName;					// 資産名
	private String astPrjCode;				// 資産プロジェクトコード
	private String astPrjName;				// 資産プロジェクト名
	private String astPrjType;				// 資産プロジェクトタイプ
	private String astPurCompanyCode;		// 仕入先コード MI顧客コード
	private String astPurCompanyName;		// 仕入先名
	private String astEstimateNumber;		// 見積番号
	private Long astUnitAmount;				// 単価
	private Integer astQuantity;			// 数量/人月
	private Long astGetAmount;				// 取得金額
	private String astAddUpType;			// 計上区分 1:資産,2:費用
	private String astAddUpType1Flag;		// 計上区分 資産フラグ
	private String astAddUpType2Flag;		// 計上区分 費用フラグ
	private String astComment;				// コメント
	private String astAccountCode;			// 処理科目 汎用マスタ-AP_INT_ACCOUNT
	private String astAccountName;			// 処理科目名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
		applicationVersion = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		astCategoryCode = (String)input.readObject();
		astCategoryName = (String)input.readObject();
		astCategoryType = (String)input.readObject();
		astTermFlag = (String)input.readObject();
		astTermYear = Function.getExternalInteger(input.readObject());
		astName = (String)input.readObject();
		astPrjCode = (String)input.readObject();
		astPrjName = (String)input.readObject();
		astPrjType = (String)input.readObject();
		astPurCompanyCode = (String)input.readObject();
		astPurCompanyName = (String)input.readObject();
		astEstimateNumber = (String)input.readObject();
		astUnitAmount = Function.getExternalLong(input.readObject());
		astQuantity = Function.getExternalInteger(input.readObject());
		astGetAmount = Function.getExternalLong(input.readObject());
		astAddUpType = (String)input.readObject();
		astAddUpType1Flag = (String)input.readObject();
		astAddUpType2Flag = (String)input.readObject();
		astComment = (String)input.readObject();
		astAccountCode = (String)input.readObject();
		astAccountName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
		output.writeObject(applicationVersion);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(astCategoryCode);
		output.writeObject(astCategoryName);
		output.writeObject(astCategoryType);
		output.writeObject(astTermFlag);
		output.writeObject(astTermYear);
		output.writeObject(astName);
		output.writeObject(astPrjCode);
		output.writeObject(astPrjName);
		output.writeObject(astPrjType);
		output.writeObject(astPurCompanyCode);
		output.writeObject(astPurCompanyName);
		output.writeObject(astEstimateNumber);
		output.writeObject(astUnitAmount);
		output.writeObject(astQuantity);
		output.writeObject(astGetAmount);
		output.writeObject(astAddUpType);
		output.writeObject(astAddUpType1Flag);
		output.writeObject(astAddUpType2Flag);
		output.writeObject(astComment);
		output.writeObject(astAccountCode);
		output.writeObject(astAccountName);
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

	public String getAstCategoryCode() {
		return astCategoryCode;
	}

	public void setAstCategoryCode(String astCategoryCode) {
		this.astCategoryCode = astCategoryCode;
	}

	public String getAstTermFlag() {
		return astTermFlag;
	}

	public void setAstTermFlag(String astTermFlag) {
		this.astTermFlag = astTermFlag;
	}

	public Integer getAstTermYear() {
		return astTermYear;
	}

	public void setAstTermYear(Integer astTermYear) {
		this.astTermYear = astTermYear;
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

	public String getAstPurCompanyCode() {
		return astPurCompanyCode;
	}

	public void setAstPurCompanyCode(String astPurCompanyCode) {
		this.astPurCompanyCode = astPurCompanyCode;
	}

	public String getAstPurCompanyName() {
		return astPurCompanyName;
	}

	public void setAstPurCompanyName(String astPurCompanyName) {
		this.astPurCompanyName = astPurCompanyName;
	}

	public String getAstEstimateNumber() {
		return astEstimateNumber;
	}

	public void setAstEstimateNumber(String astEstimateNumber) {
		this.astEstimateNumber = astEstimateNumber;
	}

	public Long getAstUnitAmount() {
		return astUnitAmount;
	}

	public void setAstUnitAmount(Long astUnitAmount) {
		this.astUnitAmount = astUnitAmount;
	}

	public Integer getAstQuantity() {
		return astQuantity;
	}

	public void setAstQuantity(Integer astQuantity) {
		this.astQuantity = astQuantity;
	}

	public Long getAstGetAmount() {
		return astGetAmount;
	}

	public void setAstGetAmount(Long astGetAmount) {
		this.astGetAmount = astGetAmount;
	}

	public String getAstAddUpType() {
		return astAddUpType;
	}

	public void setAstAddUpType(String astAddUpType) {
		this.astAddUpType = astAddUpType;
	}

	public String getAstComment() {
		return astComment;
	}

	public void setAstComment(String astComment) {
		this.astComment = astComment;
	}

	public String getAstAccountCode() {
		return astAccountCode;
	}

	public void setAstAccountCode(String astAccountCode) {
		this.astAccountCode = astAccountCode;
	}

	public String getAstCategoryName() {
		return astCategoryName;
	}

	public void setAstCategoryName(String astCategoryName) {
		this.astCategoryName = astCategoryName;
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

	public String getAstAccountName() {
		return astAccountName;
	}

	public void setAstAccountName(String astAccountName) {
		this.astAccountName = astAccountName;
	}

	public String getAstCategoryType() {
		return astCategoryType;
	}

	public void setAstCategoryType(String astCategoryType) {
		this.astCategoryType = astCategoryType;
	}

	public String getAstAddUpType1Flag() {
		return astAddUpType1Flag;
	}

	public void setAstAddUpType1Flag(String astAddUpType1Flag) {
		this.astAddUpType1Flag = astAddUpType1Flag;
	}

	public String getAstAddUpType2Flag() {
		return astAddUpType2Flag;
	}

	public void setAstAddUpType2Flag(String astAddUpType2Flag) {
		this.astAddUpType2Flag = astAddUpType2Flag;
	}

}
