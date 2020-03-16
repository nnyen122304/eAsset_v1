/*===================================================================
 * ファイル名 : ApGetTanLineAst.java
 * 概要説明   : 取得申請(有形)_資産(機器)明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-18 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_get_tan;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApGetTanLineAst extends LineData {
	private static final long serialVersionUID = 1L;

	private Long apGetTanLineAstId; // 資産(機器)明細識別番号
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private String astName; // 資産(機器)名
	private String astCategory1Code; // 資産(機器)大分類
	private String astCategory1Name; // 資産(機器)大分類名
	private String astCategory2Code; // 資産(機器)小分類
	private String astCategory2Name; // 資産(機器)小分類名
	private String astQuantityManageType;			// 資産(機器)数量管理区分
	private String astThinClientType;			// 資産(機器)シンクライアント区分
	private Integer quantity; // 数量
	private Integer registQuantity; // 登録数量
	private String astMakerCode; // メーカーコード
	private String astMakerName; // メーカー名
	private String astMakerModel; // メーカー型番
	private String astShapeCode; // 筐体/形状
	private String astShapeName; // 筐体/形状名
	private String astPrjCode; // 資産プロジェクトコード
	private String astPrjName; // 資産プロジェクト名
	private String astPrjType; // 資産プロジェクトタイプ
	private String failureAssetId; // 故障交換対象：情報機器管理番号
	private String failureContractNum; // 故障交換対象：契約番号
	private String failureAstOir; // 故障交換対象：OIR
	private String failureAstSerialCode; // 故障交換対象：シリアル番号
	private String failureUseStaffCode; // 故障交換対象：使用者社員番号
	private String autoRegistFlag; // 自動登録フラグ 0:手動登録,1:自動登録
	private String noRegistFlag; // 登録不要フラグ 0:登録必要,1:登録不要
	private String sealIssueFlag; // シール発行フラグ 0:シール発行不要,1:シール発行必要
	private String equipmentFlag; // 備品フラグ 0:備品以外,1:備品
	private String astModelCode; // レンタル会社型番
	private Long astGetLeMonthAmount; // 月単価
	private String reoAstName; // 注文書用資産(機器)名
	private String reoAgreement; // レンタル注文書:約款
	private String reoPriceList; // レンタル注文書:価格表
	private Integer astGetLeContractMonth; // レンタル注文書:契約月数

	private String failureUseStaffName; // 故障交換対象：使用者社員名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		apGetTanLineAstId = Function.getExternalLong(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		astName = (String)input.readObject();
		astCategory1Code = (String)input.readObject();
		astCategory1Name = (String)input.readObject();
		astCategory2Code = (String)input.readObject();
		astCategory2Name = (String)input.readObject();
		astQuantityManageType = (String)input.readObject();
		astThinClientType = (String)input.readObject();
		quantity = Function.getExternalInteger(input.readObject());
		registQuantity = Function.getExternalInteger(input.readObject());
		astMakerCode = (String)input.readObject();
		astMakerName = (String)input.readObject();
		astMakerModel = (String)input.readObject();
		astShapeCode = (String)input.readObject();
		astShapeName = (String)input.readObject();
		astPrjCode = (String)input.readObject();
		astPrjName = (String)input.readObject();
		astPrjType = (String)input.readObject();
		failureAssetId = (String)input.readObject();
		failureContractNum = (String)input.readObject();
		failureAstOir = (String)input.readObject();
		failureAstSerialCode = (String)input.readObject();
		failureUseStaffCode = (String)input.readObject();
		autoRegistFlag = (String)input.readObject();
		noRegistFlag = (String)input.readObject();
		sealIssueFlag = (String)input.readObject();
		equipmentFlag = (String)input.readObject();
		astModelCode = (String)input.readObject();
		astGetLeMonthAmount = Function.getExternalLong(input.readObject());
		reoAstName = (String)input.readObject();
		reoAgreement = (String)input.readObject();
		reoPriceList = (String)input.readObject();
		astGetLeContractMonth = Function.getExternalInteger(input.readObject());

		failureUseStaffName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(apGetTanLineAstId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(astName);
		output.writeObject(astCategory1Code);
		output.writeObject(astCategory1Name);
		output.writeObject(astCategory2Code);
		output.writeObject(astCategory2Name);
		output.writeObject(astQuantityManageType);
		output.writeObject(astThinClientType);
		output.writeObject(quantity);
		output.writeObject(registQuantity);
		output.writeObject(astMakerCode);
		output.writeObject(astMakerName);
		output.writeObject(astMakerModel);
		output.writeObject(astShapeCode);
		output.writeObject(astShapeName);
		output.writeObject(astPrjCode);
		output.writeObject(astPrjName);
		output.writeObject(astPrjType);
		output.writeObject(failureAssetId);
		output.writeObject(failureContractNum);
		output.writeObject(failureAstOir);
		output.writeObject(failureAstSerialCode);
		output.writeObject(failureUseStaffCode);
		output.writeObject(autoRegistFlag);
		output.writeObject(noRegistFlag);
		output.writeObject(sealIssueFlag);
		output.writeObject(equipmentFlag);
		output.writeObject(astModelCode);
		output.writeObject(astGetLeMonthAmount);
		output.writeObject(reoAstName);
		output.writeObject(reoAgreement);
		output.writeObject(reoPriceList);
		output.writeObject(astGetLeContractMonth);

		output.writeObject(failureUseStaffName);

	}

	/**
	 * apGetTanLineAstIdを取得します。
	 * @return apGetTanLineAstId
	 */
	public Long getApGetTanLineAstId() {
		return apGetTanLineAstId;
	}

	/**
	 * apGetTanLineAstId
	 * @param apGetTanLineAstIdを設定します。
	 */
	public void setApGetTanLineAstId(Long apGetTanLineAstId) {
		this.apGetTanLineAstId = apGetTanLineAstId;
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
	 * astCategory1Codeを取得します。
	 * @return astCategory1Code
	 */
	public String getAstCategory1Code() {
		return astCategory1Code;
	}

	/**
	 * astCategory1Code
	 * @param astCategory1Codeを設定します。
	 */
	public void setAstCategory1Code(String astCategory1Code) {
		this.astCategory1Code = astCategory1Code;
	}

	/**
	 * astCategory2Codeを取得します。
	 * @return astCategory2Code
	 */
	public String getAstCategory2Code() {
		return astCategory2Code;
	}

	/**
	 * astCategory2Code
	 * @param astCategory2Codeを設定します。
	 */
	public void setAstCategory2Code(String astCategory2Code) {
		this.astCategory2Code = astCategory2Code;
	}

	/**
	 * quantityを取得します。
	 * @return quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * quantity
	 * @param quantityを設定します。
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * registQuantityを取得します。
	 * @return registQuantity
	 */
	public Integer getRegistQuantity() {
		return registQuantity;
	}

	/**
	 * registQuantity
	 * @param registQuantityを設定します。
	 */
	public void setRegistQuantity(Integer registQuantity) {
		this.registQuantity = registQuantity;
	}

	/**
	 * astMakerCodeを取得します。
	 * @return astMakerCode
	 */
	public String getAstMakerCode() {
		return astMakerCode;
	}

	/**
	 * astMakerCode
	 * @param astMakerCodeを設定します。
	 */
	public void setAstMakerCode(String astMakerCode) {
		this.astMakerCode = astMakerCode;
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
	 * astShapeCodeを取得します。
	 * @return astShapeCode
	 */
	public String getAstShapeCode() {
		return astShapeCode;
	}

	/**
	 * astShapeCode
	 * @param astShapeCodeを設定します。
	 */
	public void setAstShapeCode(String astShapeCode) {
		this.astShapeCode = astShapeCode;
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
	 * @return the failureContractNum
	 */
	public String getFailureContractNum() {
		return failureContractNum;
	}

	/**
	 * @param failureContractNum the failureContractNum to set
	 */
	public void setFailureContractNum(String failureContractNum) {
		this.failureContractNum = failureContractNum;
	}

	/**
	 * @return the failureAstOir
	 */
	public String getFailureAstOir() {
		return failureAstOir;
	}

	/**
	 * @param failureAstOir the failureAstOir to set
	 */
	public void setFailureAstOir(String failureAstOir) {
		this.failureAstOir = failureAstOir;
	}

	/**
	 * @return the failureAstSerialCode
	 */
	public String getFailureAstSerialCode() {
		return failureAstSerialCode;
	}

	/**
	 * @param failureAstSerialCode the failureAstSerialCode to set
	 */
	public void setFailureAstSerialCode(String failureAstSerialCode) {
		this.failureAstSerialCode = failureAstSerialCode;
	}

	/**
	 * @return the failureUseStaffCode
	 */
	public String getFailureUseStaffCode() {
		return failureUseStaffCode;
	}

	/**
	 * @param failureUseStaffCode the failureUseStaffCode to set
	 */
	public void setFailureUseStaffCode(String failureUseStaffCode) {
		this.failureUseStaffCode = failureUseStaffCode;
	}

	/**
	 * autoRegistFlagを取得します。
	 * @return autoRegistFlag
	 */
	public String getAutoRegistFlag() {
		return autoRegistFlag;
	}

	/**
	 * autoRegistFlag
	 * @param autoRegistFlagを設定します。
	 */
	public void setAutoRegistFlag(String autoRegistFlag) {
		this.autoRegistFlag = autoRegistFlag;
	}

	/**
	 * noRegistFlagを取得します。
	 * @return noRegistFlag
	 */
	public String getNoRegistFlag() {
		return noRegistFlag;
	}

	/**
	 * noRegistFlag
	 * @param noRegistFlagを設定します。
	 */
	public void setNoRegistFlag(String noRegistFlag) {
		this.noRegistFlag = noRegistFlag;
	}

	/**
	 * sealIssueFlagを取得します。
	 * @return sealIssueFlag
	 */
	public String getSealIssueFlag() {
		return sealIssueFlag;
	}

	/**
	 * sealIssueFlag
	 * @param sealIssueFlagを設定します。
	 */
	public void setSealIssueFlag(String sealIssueFlag) {
		this.sealIssueFlag = sealIssueFlag;
	}

	/**
	 * equipmentFlagを取得します。
	 * @return equipmentFlag
	 */
	public String getEquipmentFlag() {
		return equipmentFlag;
	}

	/**
	 * equipmentFlag
	 * @param equipmentFlagを設定します。
	 */
	public void setEquipmentFlag(String equipmentFlag) {
		this.equipmentFlag = equipmentFlag;
	}

	public String getAstCategory1Name() {
		return astCategory1Name;
	}

	public void setAstCategory1Name(String astCategory1Name) {
		this.astCategory1Name = astCategory1Name;
	}

	public String getAstCategory2Name() {
		return astCategory2Name;
	}

	public void setAstCategory2Name(String astCategory2Name) {
		this.astCategory2Name = astCategory2Name;
	}

	public String getAstShapeName() {
		return astShapeName;
	}

	public void setAstShapeName(String astShapeName) {
		this.astShapeName = astShapeName;
	}

	public String getAstPrjName() {
		return astPrjName;
	}

	public void setAstPrjName(String astPrjName) {
		this.astPrjName = astPrjName;
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

	/**
	 * @return the astPrjType
	 */
	public String getAstPrjType() {
		return astPrjType;
	}

	/**
	 * @param astPrjType the astPrjType to set
	 */
	public void setAstPrjType(String astPrjType) {
		this.astPrjType = astPrjType;
	}

	/**
	 * @return the astQuantityManageType
	 */
	public String getAstQuantityManageType() {
		return astQuantityManageType;
	}

	/**
	 * @param astQuantityManageType the astQuantityManageType to set
	 */
	public void setAstQuantityManageType(String astQuantityManageType) {
		this.astQuantityManageType = astQuantityManageType;
	}

	/**
	 * @return the astThinClientType
	 */
	public String getAstThinClientType() {
		return astThinClientType;
	}

	/**
	 * @param astThinClientType the astThinClientType to set
	 */
	public void setAstThinClientType(String astThinClientType) {
		this.astThinClientType = astThinClientType;
	}

	/**
	 * astModelCodeを取得します。
	 * @return astModelCode
	 */
	public String getAstModelCode() {
		return astModelCode;
	}

	/**
	 * astModelCode
	 * @param astModelCodeを設定します。
	 */
	public void setAstModelCode(String astModelCode) {
		this.astModelCode = astModelCode;
	}

	/**
	 * astGetLeMonthAmountを取得します。
	 * @return astGetLeMonthAmount
	 */
	public Long getAstGetLeMonthAmount() {
		return astGetLeMonthAmount;
	}

	/**
	 * astGetLeMonthAmount
	 * @param astGetLeMonthAmountを設定します。
	 */
	public void setAstGetLeMonthAmount(Long astGetLeMonthAmount) {
		this.astGetLeMonthAmount = astGetLeMonthAmount;
	}

	/**
	 * reoAstNameを取得します。
	 * @return reoAstName
	 */
	public String getReoAstName() {
		return reoAstName;
	}

	/**
	 * reoAstName
	 * @param reoAstNameを設定します。
	 */
	public void setReoAstName(String reoAstName) {
		this.reoAstName = reoAstName;
	}

	/**
	 * reoAgreementを取得します。
	 * @return reoAgreement
	 */
	public String getReoAgreement() {
		return reoAgreement;
	}

	/**
	 * reoAgreement
	 * @param reoAgreementを設定します。
	 */
	public void setReoAgreement(String reoAgreement) {
		this.reoAgreement = reoAgreement;
	}

	/**
	 * reoPriceListを取得します。
	 * @return reoPriceList
	 */
	public String getReoPriceList() {
		return reoPriceList;
	}

	/**
	 * reoPriceList
	 * @param reoPriceListを設定します。
	 */
	public void setReoPriceList(String reoPriceList) {
		this.reoPriceList = reoPriceList;
	}

	/**
	 * astGetLeContractMonthを取得します。
	 * @return astGetLeContractMonth
	 */
	public Integer getAstGetLeContractMonth() {
		return astGetLeContractMonth;
	}

	/**
	 * astGetLeContractMonth
	 * @param astGetLeContractMonthを設定します。
	 */
	public void setAstGetLeContractMonth(Integer astGetLeContractMonth) {
		this.astGetLeContractMonth = astGetLeContractMonth;
	}

}
