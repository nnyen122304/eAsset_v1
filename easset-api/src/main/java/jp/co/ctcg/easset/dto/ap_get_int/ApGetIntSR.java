/*===================================================================
 * ファイル名 : ApGetIntSR.java
 * 概要説明   : 取得申請(無形)検索結果
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

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class ApGetIntSR extends ApGetInt {
	private static final long serialVersionUID = 1L;

	private String specialApproveFlagName;		// 稟議書・経営会議等承認済表示
	private String apSkipApproveFlagName;		// 課長/GL承認不要表示
	private String apNeedCioJudgeFlagName;		// 要CIO審査表示
	private String astGetTimeFlagName;			// 取得時期名 (その他、先行申請)
	private String astCloudTypeName;			// クラウド区分表示
	private String astEffectTypeName;			// 費用削減効果・収益獲得効果 1:有,2:無,3:不明
	private String astRentFlagName;				// 賃借物件据付費用 0:不要,1:必要
	private String costTypeName;				// 販売管理費/原価区分表示
	private String reportCompleteFlagName;		// サービス提供開始報告完了フラグ 0:登録残有り,1:完了
	private Date releaseDate;					// リリース日
	private String addUpTypeName;				// 計上区分名
	private Long licenseRegistCount;			// ライセンス登録数
	private String costSectionDistName;				// 経費負担部署配分表示

	/**  ここから下は明細単位ダウンロード用変数 ※Flexの画面では表示しない変数(readExternal, writeExternalには記述不要) **/
	private Integer fldLineSeq;				// 行シーケンス
	private String fldAstCategoryCode;		// 分類 汎用マスタ-AP_GET_INT_AST_CATEGORY
	private String fldAstCategoryName;		// 分類名
	private String fldAstCategoryType;		// 分類タイプ
	private String fldAstTermFlag;			// 期間ライセンスフラグ 0:その他,1:期間ライセンス
	private String fldAstTermFlagName;			// 期間ライセンスフラグ名
	private Integer fldAstTermYear;			// 期間(年)
	private String fldAstName;				// 資産名
	private String fldAstPrjCode;			// 資産プロジェクトコード
	private String fldAstPrjName;			// 資産プロジェクト名
	private String fldAstPrjType;			// 資産プロジェクトタイプ
	private String fldAstPurCompanyCode;	// 仕入先コード MI顧客コード
	private String fldAstPurCompanyName;	// 仕入先名
	private String fldAstEstimateNumber;	// 見積書番号
	private Long fldAstUnitAmount;			// 単価
	private Integer fldAstQuantity;			// 数量/人月
	private Long fldAstGetAmount;			// 取得金額
	private String fldAstAddUpType;			// 計上区分 1:資産,2:費用
	private String fldAstAddUpTypeName;		// 計上区分名
	private String fldAstAddUpType1Flag;	// 計上区分 資産フラグ
	private String fldAstAddUpType2Flag;	// 計上区分 費用フラグ
	private String fldAstComment;			// コメント
	private String fldAstAccountCode;		// 処理科目 汎用マスタ-AP_INT_ACCOUNT
	private String fldAstAccountName;		// 処理科目名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		specialApproveFlagName = (String)input.readObject();
		apSkipApproveFlagName = (String)input.readObject();
		apNeedCioJudgeFlagName = (String)input.readObject();
		astGetTimeFlagName = (String)input.readObject();
		astCloudTypeName = (String)input.readObject();
		astEffectTypeName = (String)input.readObject();
		astRentFlagName = (String)input.readObject();
		costTypeName = (String)input.readObject();
		reportCompleteFlagName = (String)input.readObject();
		releaseDate = (Date)input.readObject();
		addUpTypeName = (String)input.readObject();
		licenseRegistCount = Function.getExternalLong(input.readObject());
		costSectionDistName = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(specialApproveFlagName);
		output.writeObject(apSkipApproveFlagName);
		output.writeObject(apNeedCioJudgeFlagName);
		output.writeObject(astGetTimeFlagName);
		output.writeObject(astCloudTypeName);
		output.writeObject(astEffectTypeName);
		output.writeObject(astRentFlagName);
		output.writeObject(costTypeName);
		output.writeObject(reportCompleteFlagName);
		output.writeObject(releaseDate);
		output.writeObject(addUpTypeName);
		output.writeObject(licenseRegistCount);
		output.writeObject(costSectionDistName);
	}

	public String getSpecialApproveFlagName() {
		return specialApproveFlagName;
	}

	public void setSpecialApproveFlagName(String specialApproveFlagName) {
		this.specialApproveFlagName = specialApproveFlagName;
	}

	public String getApSkipApproveFlagName() {
		return apSkipApproveFlagName;
	}

	public void setApSkipApproveFlagName(String apSkipApproveFlagName) {
		this.apSkipApproveFlagName = apSkipApproveFlagName;
	}

	public String getApNeedCioJudgeFlagName() {
		return apNeedCioJudgeFlagName;
	}

	public void setApNeedCioJudgeFlagName(String apNeedCioJudgeFlagName) {
		this.apNeedCioJudgeFlagName = apNeedCioJudgeFlagName;
	}

	public String getAstGetTimeFlagName() {
		return astGetTimeFlagName;
	}

	public void setAstGetTimeFlagName(String astGetTimeFlagName) {
		this.astGetTimeFlagName = astGetTimeFlagName;
	}

	public String getAstCloudTypeName() {
		return astCloudTypeName;
	}

	public void setAstCloudTypeName(String astCloudTypeName) {
		this.astCloudTypeName = astCloudTypeName;
	}

	public String getAstEffectTypeName() {
		return astEffectTypeName;
	}

	public void setAstEffectTypeName(String astEffectTypeName) {
		this.astEffectTypeName = astEffectTypeName;
	}

	public String getAstRentFlagName() {
		return astRentFlagName;
	}

	public void setAstRentFlagName(String astRentFlagName) {
		this.astRentFlagName = astRentFlagName;
	}

	public String getCostTypeName() {
		return costTypeName;
	}

	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	public String getReportCompleteFlagName() {
		return reportCompleteFlagName;
	}

	public void setReportCompleteFlagName(String reportCompleteFlagName) {
		this.reportCompleteFlagName = reportCompleteFlagName;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getAddUpTypeName() {
		return addUpTypeName;
	}

	public void setAddUpTypeName(String addUpTypeName) {
		this.addUpTypeName = addUpTypeName;
	}

	public Long getLicenseRegistCount() {
		return licenseRegistCount;
	}

	public void setLicenseRegistCount(Long licenseRegistCount) {
		this.licenseRegistCount = licenseRegistCount;
	}

	public Integer getFldLineSeq() {
		return fldLineSeq;
	}

	public void setFldLineSeq(Integer fldLineSeq) {
		this.fldLineSeq = fldLineSeq;
	}

	public String getFldAstCategoryCode() {
		return fldAstCategoryCode;
	}

	public void setFldAstCategoryCode(String fldAstCategoryCode) {
		this.fldAstCategoryCode = fldAstCategoryCode;
	}

	public String getFldAstCategoryName() {
		return fldAstCategoryName;
	}

	public void setFldAstCategoryName(String fldAstCategoryName) {
		this.fldAstCategoryName = fldAstCategoryName;
	}

	public String getFldAstCategoryType() {
		return fldAstCategoryType;
	}

	public void setFldAstCategoryType(String fldAstCategoryType) {
		this.fldAstCategoryType = fldAstCategoryType;
	}

	public String getFldAstTermFlag() {
		return fldAstTermFlag;
	}

	public void setFldAstTermFlag(String fldAstTermFlag) {
		this.fldAstTermFlag = fldAstTermFlag;
	}

	public Integer getFldAstTermYear() {
		return fldAstTermYear;
	}

	public void setFldAstTermYear(Integer fldAstTermYear) {
		this.fldAstTermYear = fldAstTermYear;
	}

	public String getFldAstName() {
		return fldAstName;
	}

	public void setFldAstName(String fldAstName) {
		this.fldAstName = fldAstName;
	}

	public String getFldAstPrjCode() {
		return fldAstPrjCode;
	}

	public void setFldAstPrjCode(String fldAstPrjCode) {
		this.fldAstPrjCode = fldAstPrjCode;
	}

	public String getFldAstPrjName() {
		return fldAstPrjName;
	}

	public void setFldAstPrjName(String fldAstPrjName) {
		this.fldAstPrjName = fldAstPrjName;
	}

	public String getFldAstPrjType() {
		return fldAstPrjType;
	}

	public void setFldAstPrjType(String fldAstPrjType) {
		this.fldAstPrjType = fldAstPrjType;
	}

	public String getFldAstPurCompanyCode() {
		return fldAstPurCompanyCode;
	}

	public void setFldAstPurCompanyCode(String fldAstPurCompanyCode) {
		this.fldAstPurCompanyCode = fldAstPurCompanyCode;
	}

	public String getFldAstPurCompanyName() {
		return fldAstPurCompanyName;
	}

	public void setFldAstPurCompanyName(String fldAstPurCompanyName) {
		this.fldAstPurCompanyName = fldAstPurCompanyName;
	}

	public String getFldAstEstimateNumber() {
		return fldAstEstimateNumber;
	}

	public void setFldAstEstimateNumber(String fldAstEstimateNumber) {
		this.fldAstEstimateNumber = fldAstEstimateNumber;
	}

	public Long getFldAstUnitAmount() {
		return fldAstUnitAmount;
	}

	public void setFldAstUnitAmount(Long fldAstUnitAmount) {
		this.fldAstUnitAmount = fldAstUnitAmount;
	}

	public Integer getFldAstQuantity() {
		return fldAstQuantity;
	}

	public void setFldAstQuantity(Integer fldAstQuantity) {
		this.fldAstQuantity = fldAstQuantity;
	}

	public Long getFldAstGetAmount() {
		return fldAstGetAmount;
	}

	public void setFldAstGetAmount(Long fldAstGetAmount) {
		this.fldAstGetAmount = fldAstGetAmount;
	}

	public String getFldAstAddUpType() {
		return fldAstAddUpType;
	}

	public void setFldAstAddUpType(String fldAstAddUpType) {
		this.fldAstAddUpType = fldAstAddUpType;
	}

	public String getFldAstAddUpType1Flag() {
		return fldAstAddUpType1Flag;
	}

	public void setFldAstAddUpType1Flag(String fldAstAddUpType1Flag) {
		this.fldAstAddUpType1Flag = fldAstAddUpType1Flag;
	}

	public String getFldAstAddUpType2Flag() {
		return fldAstAddUpType2Flag;
	}

	public void setFldAstAddUpType2Flag(String fldAstAddUpType2Flag) {
		this.fldAstAddUpType2Flag = fldAstAddUpType2Flag;
	}

	public String getFldAstComment() {
		return fldAstComment;
	}

	public void setFldAstComment(String fldAstComment) {
		this.fldAstComment = fldAstComment;
	}

	public String getFldAstAccountCode() {
		return fldAstAccountCode;
	}

	public void setFldAstAccountCode(String fldAstAccountCode) {
		this.fldAstAccountCode = fldAstAccountCode;
	}

	public String getFldAstAccountName() {
		return fldAstAccountName;
	}

	public void setFldAstAccountName(String fldAstAccountName) {
		this.fldAstAccountName = fldAstAccountName;
	}

	/**
	 * @return the fldAstTermFlagName
	 */
	public String getFldAstTermFlagName() {
		return fldAstTermFlagName;
	}

	/**
	 * @param fldAstTermFlagName the fldAstTermFlagName to set
	 */
	public void setFldAstTermFlagName(String fldAstTermFlagName) {
		this.fldAstTermFlagName = fldAstTermFlagName;
	}

	/**
	 * @return the fldAstAddUpTypeName
	 */
	public String getFldAstAddUpTypeName() {
		return fldAstAddUpTypeName;
	}

	/**
	 * @param fldAstAddUpTypeName the fldAstAddUpTypeName to set
	 */
	public void setFldAstAddUpTypeName(String fldAstAddUpTypeName) {
		this.fldAstAddUpTypeName = fldAstAddUpTypeName;
	}

	/**
	 * @return the costSectionDistName
	 */
	public String getCostSectionDistName() {
		return costSectionDistName;
	}

	/**
	 * @param costSectionDistName the costSectionDistName to set
	 */
	public void setCostSectionDistName(String costSectionDistName) {
		this.costSectionDistName = costSectionDistName;
	}

}
