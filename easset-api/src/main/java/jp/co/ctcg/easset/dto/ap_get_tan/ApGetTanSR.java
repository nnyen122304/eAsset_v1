/*===================================================================
 * ファイル名 : ApGetTanSR.java
 * 概要説明   : 取得申請(有形)検索結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-18 1.0  リヨン 崔     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_get_tan;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class ApGetTanSR extends ApGetTan {
	private static final long serialVersionUID = 1L;

	private String specialApproveFlagName; // 稟議書・経営会議等承認済表示
	private String apSkipApproveFlagName; // 課長/GL承認不要表示
	private String getItemCloudTypeName; // クラウド区分表示
	private String getNeedCioJudgeFlagName; // 要CIO審査表示
	private String getSystemSectionDeployFlagName; // 情報システム部配備表示
	private String getIntraInventoryFlagName; // 社内実地棚卸対象表示
	private String costTypeName; // 販売管理費/原価区分表示
	private String costDepPrjUndecidedFlagName; // 減価償却プロジェクトコード未定表示
	private String lineEditApproveFlagName; // 明細修正許可表示
	private String stopRegistFlagName; // 登録申請停止表示
	private String registCompleteFlagName; // 登録完了表示

	private String astNameOne;  // 資産(機器)名
	private String astMakerNameOne; // メーカー
	private String astMakerModelOne; // メーカー型番
	private String astPrjCodeOne; // 資産プロジェクト
	private String astPrjNameOne; // 資産プロジェクト名
	private String softwareMakerNameOne;  // ソフトウエアメーカー名
	private String softwareNameOne; // ソフトウエア名
	private Integer astLineCount;  // 資産(機器)数量・明細数
	private Integer astQuantitySum;   // 取得数
	private Integer astRegistQuantitySum;  // 資産(機器)数量・登録数
	private Integer astNoRegistQuantitySum;  // 資産(機器)数量・登録不要
	private Integer licLineCount;  // ライセンス情報[合計]
	private Integer licQuantitySum;  // ライセンス数量[合計]：取得数
	private Integer licRegistQuantitySum; // ライセンス数量[合計]：登録数
	private String getCompanyName;  // 取得先
	private String failureAssetIdOne;  // 故障情報機器管理番号
	private String costSectionDistName;				// 経費負担部署配分表示

	private String reoOrderFlagName; // レンタル注文書:発注フラグ名

	/**  ここから下は明細単位ダウンロード用変数 ※Flexの画面では表示しない変数(readExternal, writeExternalには記述不要) **/
	private String getPurCompanyName;
	private String getPurComment;
	private Long getPurAmountSum;
	private String getOtrComment;
	private Long getOtrAmountSum;
	private Integer astLineSeq; // 行番号
	private String astName; // 資産(機器)名
	private String astCategory2Name; // 資産(機器)分類
	private Integer astQuantity; // 数量
	private Integer astRegistQuantity; // 登録数量
	private String astNoRegistFlagName; // 登録不要
	private String astMakerName; // メーカー名
	private String astMakerModel; // メーカー型番
	private String astShapeName; // 筐体/形状
	private String astPrjCode; // 資産プロジェクトコード
	private String astPrjName; // 資産プロジェクト名
	private Integer licLineSeq; // 行番号
	private String softwareMakerName; // ソフトウェアメーカー名
	private String softwareName; // ソフトウェア名
	private Integer licQuantity; // 数量
	private Integer licRegistQuantity; // 登録数量
	private String licQuantityTypeName; // ライセンス数区分
	private Integer licLicQuantity; // ライセンス数
	private Integer failureLineSeq; // 行番号
	private String failureAssetId; // 情報機器管理番号
	private String failureContractNum; // 契約番号
	private String failureAstName; // 資産名
	private String failureAstOir; // OIR
	private String failureAstSerialCode; // シリアル番号
	private String failureUseStaffCode; // 使用者社員番号
	private String failureUseStaffName; // 使用者社員名
	private Integer failureQuantity; // 数量
	private Integer failureRegistQuantity; // 登録数量
	/**  ここまで                                                                                                       **/

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		specialApproveFlagName = (String)input.readObject();
		apSkipApproveFlagName = (String)input.readObject();
		getItemCloudTypeName = (String)input.readObject();
		getNeedCioJudgeFlagName = (String)input.readObject();
		getSystemSectionDeployFlagName = (String)input.readObject();
		getIntraInventoryFlagName = (String)input.readObject();
		costTypeName = (String)input.readObject();
		costDepPrjUndecidedFlagName = (String)input.readObject();
		lineEditApproveFlagName = (String)input.readObject();
		stopRegistFlagName = (String)input.readObject();
		registCompleteFlagName = (String)input.readObject();

		astNameOne = (String)input.readObject();
		astMakerNameOne = (String)input.readObject();
		astMakerModelOne = (String)input.readObject();
		astPrjCodeOne = (String)input.readObject();
		astPrjNameOne = (String)input.readObject();
		softwareMakerNameOne = (String)input.readObject();
		softwareNameOne = (String)input.readObject();
		astLineCount = Function.getExternalInteger(input.readObject());
		astQuantitySum = Function.getExternalInteger(input.readObject());
		astRegistQuantitySum = Function.getExternalInteger(input.readObject());
		astNoRegistQuantitySum = Function.getExternalInteger(input.readObject());
		licLineCount = Function.getExternalInteger(input.readObject());
		licQuantitySum = Function.getExternalInteger(input.readObject());
		licRegistQuantitySum = Function.getExternalInteger(input.readObject());
		getCompanyName = (String)input.readObject();
		failureAssetIdOne = (String)input.readObject();
		costSectionDistName = (String)input.readObject();
		reoOrderFlagName = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(specialApproveFlagName);
		output.writeObject(apSkipApproveFlagName);
		output.writeObject(getItemCloudTypeName);
		output.writeObject(getNeedCioJudgeFlagName);
		output.writeObject(getSystemSectionDeployFlagName);
		output.writeObject(getIntraInventoryFlagName);
		output.writeObject(costTypeName);
		output.writeObject(costDepPrjUndecidedFlagName);
		output.writeObject(lineEditApproveFlagName);
		output.writeObject(stopRegistFlagName);
		output.writeObject(registCompleteFlagName);

		output.writeObject(astNameOne);
		output.writeObject(astMakerNameOne);
		output.writeObject(astMakerModelOne);
		output.writeObject(astPrjCodeOne);
		output.writeObject(astPrjNameOne);
		output.writeObject(softwareMakerNameOne);
		output.writeObject(softwareNameOne);
		output.writeObject(astLineCount);
		output.writeObject(astQuantitySum);
		output.writeObject(astRegistQuantitySum);
		output.writeObject(astNoRegistQuantitySum);
		output.writeObject(licLineCount);
		output.writeObject(licQuantitySum);
		output.writeObject(licRegistQuantitySum);
		output.writeObject(getCompanyName);
		output.writeObject(failureAssetIdOne);
		output.writeObject(costSectionDistName);
		output.writeObject(reoOrderFlagName);

	}

	/**
	 * specialApproveFlagNameを取得します。
	 * @return specialApproveFlagName
	 */
	public String getSpecialApproveFlagName() {
		return specialApproveFlagName;
	}

	/**
	 * specialApproveFlagName
	 * @param specialApproveFlagNameを設定します。
	 */
	public void setSpecialApproveFlagName(String specialApproveFlagName) {
		this.specialApproveFlagName = specialApproveFlagName;
	}

	/**
	 * apSkipApproveFlagNameを取得します。
	 * @return apSkipApproveFlagName
	 */
	public String getApSkipApproveFlagName() {
		return apSkipApproveFlagName;
	}

	/**
	 * apSkipApproveFlagName
	 * @param apSkipApproveFlagNameを設定します。
	 */
	public void setApSkipApproveFlagName(String apSkipApproveFlagName) {
		this.apSkipApproveFlagName = apSkipApproveFlagName;
	}

	/**
	 * getItemCloudTypeNameを取得します。
	 * @return getItemCloudTypeName
	 */
	public String getGetItemCloudTypeName() {
		return getItemCloudTypeName;
	}

	/**
	 * getItemCloudTypeName
	 * @param getItemCloudTypeNameを設定します。
	 */
	public void setGetItemCloudTypeName(String getItemCloudTypeName) {
		this.getItemCloudTypeName = getItemCloudTypeName;
	}

	/**
	 * getNeedCioJudgeFlagNameを取得します。
	 * @return getNeedCioJudgeFlagName
	 */
	public String getGetNeedCioJudgeFlagName() {
		return getNeedCioJudgeFlagName;
	}

	/**
	 * getNeedCioJudgeFlagName
	 * @param getNeedCioJudgeFlagNameを設定します。
	 */
	public void setGetNeedCioJudgeFlagName(String getNeedCioJudgeFlagName) {
		this.getNeedCioJudgeFlagName = getNeedCioJudgeFlagName;
	}

	/**
	 * getSystemSectionDeployFlagNameを取得します。
	 * @return getSystemSectionDeployFlagName
	 */
	public String getGetSystemSectionDeployFlagName() {
		return getSystemSectionDeployFlagName;
	}

	/**
	 * getSystemSectionDeployFlagName
	 * @param getSystemSectionDeployFlagNameを設定します。
	 */
	public void setGetSystemSectionDeployFlagName(
			String getSystemSectionDeployFlagName) {
		this.getSystemSectionDeployFlagName = getSystemSectionDeployFlagName;
	}

	/**
	 * getIntraInventoryFlagNameを取得します。
	 * @return getIntraInventoryFlagName
	 */
	public String getGetIntraInventoryFlagName() {
		return getIntraInventoryFlagName;
	}

	/**
	 * getIntraInventoryFlagName
	 * @param getIntraInventoryFlagNameを設定します。
	 */
	public void setGetIntraInventoryFlagName(String getIntraInventoryFlagName) {
		this.getIntraInventoryFlagName = getIntraInventoryFlagName;
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
	 * costDepPrjUndecidedFlagNameを取得します。
	 * @return costDepPrjUndecidedFlagName
	 */
	public String getCostDepPrjUndecidedFlagName() {
		return costDepPrjUndecidedFlagName;
	}

	/**
	 * costDepPrjUndecidedFlagName
	 * @param costDepPrjUndecidedFlagNameを設定します。
	 */
	public void setCostDepPrjUndecidedFlagName(String costDepPrjUndecidedFlagName) {
		this.costDepPrjUndecidedFlagName = costDepPrjUndecidedFlagName;
	}

	/**
	 * lineEditApproveFlagNameを取得します。
	 * @return lineEditApproveFlagName
	 */
	public String getLineEditApproveFlagName() {
		return lineEditApproveFlagName;
	}

	/**
	 * lineEditApproveFlagName
	 * @param lineEditApproveFlagNameを設定します。
	 */
	public void setLineEditApproveFlagName(String lineEditApproveFlagName) {
		this.lineEditApproveFlagName = lineEditApproveFlagName;
	}

	/**
	 * stopRegistFlagNameを取得します。
	 * @return stopRegistFlagName
	 */
	public String getStopRegistFlagName() {
		return stopRegistFlagName;
	}

	/**
	 * stopRegistFlagName
	 * @param stopRegistFlagNameを設定します。
	 */
	public void setStopRegistFlagName(String stopRegistFlagName) {
		this.stopRegistFlagName = stopRegistFlagName;
	}

	/**
	 * registCompleteFlagNameを取得します。
	 * @return registCompleteFlagName
	 */
	public String getRegistCompleteFlagName() {
		return registCompleteFlagName;
	}

	/**
	 * registCompleteFlagName
	 * @param registCompleteFlagNameを設定します。
	 */
	public void setRegistCompleteFlagName(String registCompleteFlagName) {
		this.registCompleteFlagName = registCompleteFlagName;
	}

	/**
	 * astNameOneを取得します。
	 * @return astNameOne
	 */
	public String getAstNameOne() {
		return astNameOne;
	}

	/**
	 * astNameOne
	 * @param astNameOneを設定します。
	 */
	public void setAstNameOne(String astNameOne) {
		this.astNameOne = astNameOne;
	}

	/**
	 * astMakerNameOneを取得します。
	 * @return astMakerNameOne
	 */
	public String getAstMakerNameOne() {
		return astMakerNameOne;
	}

	/**
	 * astMakerNameOne
	 * @param astMakerNameOneを設定します。
	 */
	public void setAstMakerNameOne(String astMakerNameOne) {
		this.astMakerNameOne = astMakerNameOne;
	}

	/**
	 * astMakerModelOneを取得します。
	 * @return astMakerModelOne
	 */
	public String getAstMakerModelOne() {
		return astMakerModelOne;
	}

	/**
	 * astMakerModelOne
	 * @param astMakerModelOneを設定します。
	 */
	public void setAstMakerModelOne(String astMakerModelOne) {
		this.astMakerModelOne = astMakerModelOne;
	}

	/**
	 * astPrjCodeOneを取得します。
	 * @return astPrjCodeOne
	 */
	public String getAstPrjCodeOne() {
		return astPrjCodeOne;
	}

	/**
	 * astPrjCodeOne
	 * @param astPrjCodeOneを設定します。
	 */
	public void setAstPrjCodeOne(String astPrjCodeOne) {
		this.astPrjCodeOne = astPrjCodeOne;
	}

	/**
	 * astPrjNameOneを取得します。
	 * @return astPrjNameOne
	 */
	public String getAstPrjNameOne() {
		return astPrjNameOne;
	}

	/**
	 * astPrjNameOne
	 * @param astPrjNameOneを設定します。
	 */
	public void setAstPrjNameOne(String astPrjNameOne) {
		this.astPrjNameOne = astPrjNameOne;
	}

	/**
	 * softwareMakerNameOneを取得します。
	 * @return softwareMakerNameOne
	 */
	public String getSoftwareMakerNameOne() {
		return softwareMakerNameOne;
	}

	/**
	 * softwareMakerNameOne
	 * @param softwareMakerNameOneを設定します。
	 */
	public void setSoftwareMakerNameOne(String softwareMakerNameOne) {
		this.softwareMakerNameOne = softwareMakerNameOne;
	}

	/**
	 * softwareNameOneを取得します。
	 * @return softwareNameOne
	 */
	public String getSoftwareNameOne() {
		return softwareNameOne;
	}

	/**
	 * softwareNameOne
	 * @param softwareNameOneを設定します。
	 */
	public void setSoftwareNameOne(String softwareNameOne) {
		this.softwareNameOne = softwareNameOne;
	}

	/**
	 * astLineCountを取得します。
	 * @return astLineCount
	 */
	public Integer getAstLineCount() {
		return astLineCount;
	}

	/**
	 * astLineCount
	 * @param astLineCountを設定します。
	 */
	public void setAstLineCount(Integer astLineCount) {
		this.astLineCount = astLineCount;
	}

	/**
	 * astQuantitySumを取得します。
	 * @return astQuantitySum
	 */
	public Integer getAstQuantitySum() {
		return astQuantitySum;
	}

	/**
	 * astQuantitySum
	 * @param astQuantitySumを設定します。
	 */
	public void setAstQuantitySum(Integer astQuantitySum) {
		this.astQuantitySum = astQuantitySum;
	}

	/**
	 * astRegistQuantitySumを取得します。
	 * @return astRegistQuantitySum
	 */
	public Integer getAstRegistQuantitySum() {
		return astRegistQuantitySum;
	}

	/**
	 * astRegistQuantitySum
	 * @param astRegistQuantitySumを設定します。
	 */
	public void setAstRegistQuantitySum(Integer astRegistQuantitySum) {
		this.astRegistQuantitySum = astRegistQuantitySum;
	}

	/**
	 * astNoRegistQuantitySumを取得します。
	 * @return astNoRegistQuantitySum
	 */
	public Integer getAstNoRegistQuantitySum() {
		return astNoRegistQuantitySum;
	}

	/**
	 * astNoRegistQuantitySum
	 * @param astNoRegistQuantitySumを設定します。
	 */
	public void setAstNoRegistQuantitySum(Integer astNoRegistQuantitySum) {
		this.astNoRegistQuantitySum = astNoRegistQuantitySum;
	}

	/**
	 * licLineCountを取得します。
	 * @return licLineCount
	 */
	public Integer getLicLineCount() {
		return licLineCount;
	}

	/**
	 * licLineCount
	 * @param licLineCountを設定します。
	 */
	public void setLicLineCount(Integer licLineCount) {
		this.licLineCount = licLineCount;
	}

	/**
	 * licQuantitySumを取得します。
	 * @return licQuantitySum
	 */
	public Integer getLicQuantitySum() {
		return licQuantitySum;
	}

	/**
	 * licQuantitySum
	 * @param licQuantitySumを設定します。
	 */
	public void setLicQuantitySum(Integer licQuantitySum) {
		this.licQuantitySum = licQuantitySum;
	}

	/**
	 * licRegistQuantitySumを取得します。
	 * @return licRegistQuantitySum
	 */
	public Integer getLicRegistQuantitySum() {
		return licRegistQuantitySum;
	}

	/**
	 * licRegistQuantitySum
	 * @param licRegistQuantitySumを設定します。
	 */
	public void setLicRegistQuantitySum(Integer licRegistQuantitySum) {
		this.licRegistQuantitySum = licRegistQuantitySum;
	}

	/**
	 * getCompanyNameを取得します。
	 * @return getCompanyName
	 */
	public String getGetCompanyName() {
		return getCompanyName;
	}

	/**
	 * getCompanyName
	 * @param getCompanyNameを設定します。
	 */
	public void setGetCompanyName(String getCompanyName) {
		this.getCompanyName = getCompanyName;
	}

	/**
	 * failureAssetIdOneを取得します。
	 * @return failureAssetIdOne
	 */
	public String getFailureAssetIdOne() {
		return failureAssetIdOne;
	}

	/**
	 * failureAssetIdOne
	 * @param failureAssetIdOneを設定します。
	 */
	public void setFailureAssetIdOne(String failureAssetIdOne) {
		this.failureAssetIdOne = failureAssetIdOne;
	}

	/**
	 * astLineSeqを取得します。
	 * @return astLineSeq
	 */
	public Integer getAstLineSeq() {
		return astLineSeq;
	}

	/**
	 * astLineSeq
	 * @param astLineSeqを設定します。
	 */
	public void setAstLineSeq(Integer astLineSeq) {
		this.astLineSeq = astLineSeq;
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
	 * astCategory2Nameを取得します。
	 * @return astCategory2Name
	 */
	public String getAstCategory2Name() {
		return astCategory2Name;
	}

	/**
	 * astCategory2Name
	 * @param astCategory2Nameを設定します。
	 */
	public void setAstCategory2Name(String astCategory2Name) {
		this.astCategory2Name = astCategory2Name;
	}

	/**
	 * astQuantityを取得します。
	 * @return astQuantity
	 */
	public Integer getAstQuantity() {
		return astQuantity;
	}

	/**
	 * astQuantity
	 * @param astQuantityを設定します。
	 */
	public void setAstQuantity(Integer astQuantity) {
		this.astQuantity = astQuantity;
	}

	/**
	 * astRegistQuantityを取得します。
	 * @return astRegistQuantity
	 */
	public Integer getAstRegistQuantity() {
		return astRegistQuantity;
	}

	/**
	 * astRegistQuantity
	 * @param astRegistQuantityを設定します。
	 */
	public void setAstRegistQuantity(Integer astRegistQuantity) {
		this.astRegistQuantity = astRegistQuantity;
	}

	/**
	 * astMakerNameを取得します。
	 * @return astMakerName
	 */
	public String getAstMakerName() {
		return astMakerName;
	}

	/**
	 * astMakerName
	 * @param astMakerNameを設定します。
	 */
	public void setAstMakerName(String astMakerName) {
		this.astMakerName = astMakerName;
	}

	/**
	 * astMakerModelを取得します。
	 * @return astMakerModel
	 */
	public String getAstMakerModel() {
		return astMakerModel;
	}

	/**
	 * astMakerModel
	 * @param astMakerModelを設定します。
	 */
	public void setAstMakerModel(String astMakerModel) {
		this.astMakerModel = astMakerModel;
	}

	/**
	 * astShapeNameを取得します。
	 * @return astShapeName
	 */
	public String getAstShapeName() {
		return astShapeName;
	}

	/**
	 * astShapeName
	 * @param astShapeNameを設定します。
	 */
	public void setAstShapeName(String astShapeName) {
		this.astShapeName = astShapeName;
	}

	/**
	 * astPrjCodeを取得します。
	 * @return astPrjCode
	 */
	public String getAstPrjCode() {
		return astPrjCode;
	}

	/**
	 * astPrjCode
	 * @param astPrjCodeを設定します。
	 */
	public void setAstPrjCode(String astPrjCode) {
		this.astPrjCode = astPrjCode;
	}

	/**
	 * astPrjNameを取得します。
	 * @return astPrjName
	 */
	public String getAstPrjName() {
		return astPrjName;
	}

	/**
	 * astPrjName
	 * @param astPrjNameを設定します。
	 */
	public void setAstPrjName(String astPrjName) {
		this.astPrjName = astPrjName;
	}

	/**
	 * licLineSeqを取得します。
	 * @return licLineSeq
	 */
	public Integer getLicLineSeq() {
		return licLineSeq;
	}

	/**
	 * licLineSeq
	 * @param licLineSeqを設定します。
	 */
	public void setLicLineSeq(Integer licLineSeq) {
		this.licLineSeq = licLineSeq;
	}

	/**
	 * softwareMakerNameを取得します。
	 * @return softwareMakerName
	 */
	public String getSoftwareMakerName() {
		return softwareMakerName;
	}

	/**
	 * softwareMakerName
	 * @param softwareMakerNameを設定します。
	 */
	public void setSoftwareMakerName(String softwareMakerName) {
		this.softwareMakerName = softwareMakerName;
	}

	/**
	 * softwareNameを取得します。
	 * @return softwareName
	 */
	public String getSoftwareName() {
		return softwareName;
	}

	/**
	 * softwareName
	 * @param softwareNameを設定します。
	 */
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	/**
	 * licQuantityを取得します。
	 * @return licQuantity
	 */
	public Integer getLicQuantity() {
		return licQuantity;
	}

	/**
	 * licQuantity
	 * @param licQuantityを設定します。
	 */
	public void setLicQuantity(Integer licQuantity) {
		this.licQuantity = licQuantity;
	}

	/**
	 * licRegistQuantityを取得します。
	 * @return licRegistQuantity
	 */
	public Integer getLicRegistQuantity() {
		return licRegistQuantity;
	}

	/**
	 * licRegistQuantity
	 * @param licRegistQuantityを設定します。
	 */
	public void setLicRegistQuantity(Integer licRegistQuantity) {
		this.licRegistQuantity = licRegistQuantity;
	}

	/**
	 * licQuantityTypeNameを取得します。
	 * @return licQuantityTypeName
	 */
	public String getLicQuantityTypeName() {
		return licQuantityTypeName;
	}

	/**
	 * licQuantityTypeName
	 * @param licQuantityTypeNameを設定します。
	 */
	public void setLicQuantityTypeName(String licQuantityTypeName) {
		this.licQuantityTypeName = licQuantityTypeName;
	}

	/**
	 * licLicQuantityを取得します。
	 * @return licLicQuantity
	 */
	public Integer getLicLicQuantity() {
		return licLicQuantity;
	}

	/**
	 * licLicQuantity
	 * @param licLicQuantityを設定します。
	 */
	public void setLicLicQuantity(Integer licLicQuantity) {
		this.licLicQuantity = licLicQuantity;
	}

	/**
	 * failureLineSeqを取得します。
	 * @return failureLineSeq
	 */
	public Integer getFailureLineSeq() {
		return failureLineSeq;
	}

	/**
	 * failureLineSeq
	 * @param failureLineSeqを設定します。
	 */
	public void setFailureLineSeq(Integer failureLineSeq) {
		this.failureLineSeq = failureLineSeq;
	}

	/**
	 * failureAssetIdを取得します。
	 * @return failureAssetId
	 */
	public String getFailureAssetId() {
		return failureAssetId;
	}

	/**
	 * failureAssetId
	 * @param failureAssetIdを設定します。
	 */
	public void setFailureAssetId(String failureAssetId) {
		this.failureAssetId = failureAssetId;
	}

	/**
	 * failureContractNumを取得します。
	 * @return failureContractNum
	 */
	public String getFailureContractNum() {
		return failureContractNum;
	}

	/**
	 * failureContractNum
	 * @param failureContractNumを設定します。
	 */
	public void setFailureContractNum(String failureContractNum) {
		this.failureContractNum = failureContractNum;
	}

	/**
	 * failureAstOirを取得します。
	 * @return failureAstOir
	 */
	public String getFailureAstOir() {
		return failureAstOir;
	}

	/**
	 * failureAstOir
	 * @param failureAstOirを設定します。
	 */
	public void setFailureAstOir(String failureAstOir) {
		this.failureAstOir = failureAstOir;
	}

	/**
	 * failureAstSerialCodeを取得します。
	 * @return failureAstSerialCode
	 */
	public String getFailureAstSerialCode() {
		return failureAstSerialCode;
	}

	/**
	 * failureAstSerialCode
	 * @param failureAstSerialCodeを設定します。
	 */
	public void setFailureAstSerialCode(String failureAstSerialCode) {
		this.failureAstSerialCode = failureAstSerialCode;
	}

	/**
	 * failureUseStaffCodeを取得します。
	 * @return failureUseStaffCode
	 */
	public String getFailureUseStaffCode() {
		return failureUseStaffCode;
	}

	/**
	 * failureUseStaffCode
	 * @param failureUseStaffCodeを設定します。
	 */
	public void setFailureUseStaffCode(String failureUseStaffCode) {
		this.failureUseStaffCode = failureUseStaffCode;
	}

	/**
	 * failureUseStaffNameを取得します。
	 * @return failureUseStaffName
	 */
	public String getFailureUseStaffName() {
		return failureUseStaffName;
	}

	/**
	 * failureUseStaffName
	 * @param failureUseStaffNameを設定します。
	 */
	public void setFailureUseStaffName(String failureUseStaffName) {
		this.failureUseStaffName = failureUseStaffName;
	}

	public Integer getFailureQuantity() {
		return failureQuantity;
	}

	public void setFailureQuantity(Integer failureQuantity) {
		this.failureQuantity = failureQuantity;
	}

	public Integer getFailureRegistQuantity() {
		return failureRegistQuantity;
	}

	public void setFailureRegistQuantity(Integer failureRegistQuantity) {
		this.failureRegistQuantity = failureRegistQuantity;
	}

	public String getAstNoRegistFlagName() {
		return astNoRegistFlagName;
	}

	public void setAstNoRegistFlagName(String astNoRegistFlagName) {
		this.astNoRegistFlagName = astNoRegistFlagName;
	}

	public String getGetPurCompanyName() {
		return getPurCompanyName;
	}

	public void setGetPurCompanyName(String getPurCompanyName) {
		this.getPurCompanyName = getPurCompanyName;
	}

	public String getGetPurComment() {
		return getPurComment;
	}

	public void setGetPurComment(String getPurComment) {
		this.getPurComment = getPurComment;
	}

	public Long getGetPurAmountSum() {
		return getPurAmountSum;
	}

	public void setGetPurAmountSum(Long getPurAmountSum) {
		this.getPurAmountSum = getPurAmountSum;
	}

	public String getGetOtrComment() {
		return getOtrComment;
	}

	public void setGetOtrComment(String getOtrComment) {
		this.getOtrComment = getOtrComment;
	}

	public Long getGetOtrAmountSum() {
		return getOtrAmountSum;
	}

	public void setGetOtrAmountSum(Long getOtrAmountSum) {
		this.getOtrAmountSum = getOtrAmountSum;
	}

	public String getFailureAstName() {
		return failureAstName;
	}

	public void setFailureAstName(String failureAstName) {
		this.failureAstName = failureAstName;
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

	/**
	 * reoOrderFlagNameを取得します。
	 * @return reoOrderFlagName
	 */
	public String getReoOrderFlagName() {
		return reoOrderFlagName;
	}

	/**
	 * reoOrderFlagName
	 * @param reoOrderFlagNameを設定します。
	 */
	public void setReoOrderFlagName(String reoOrderFlagName) {
		this.reoOrderFlagName = reoOrderFlagName;
	}

}
