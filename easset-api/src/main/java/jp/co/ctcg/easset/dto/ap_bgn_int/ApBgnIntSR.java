/*===================================================================
 * ファイル名 : ApBgnIntSR.java
 * 概要説明   : サービス提供開始報告検索結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_bgn_int;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class ApBgnIntSR extends ApBgnInt {
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
