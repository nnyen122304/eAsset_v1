/*===================================================================
 * ファイル名 : ApEndLeSR.java
 * 概要説明   : リース満了・解約申請検索結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-12-21  1.0   高山         新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_le;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;


import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class ApEndLeSR extends ApEndLe {
	private static final long serialVersionUID = 1L;

	private Long reAmount; // 再リース金額
	private Long purchaseAmount; // 買取金額
	private Long remainAmount; // 残リース料
	private Long cancelAmount; // 中途解約金額
	private String apEndLeLineType; // 物件申請区分 1:返却、2：再リース、3:買取　汎用マスタ-AP_END_LE_LINE_TYPE
	private String apEndLeLineTypeName; // 物件申請区分名
	private String astNameLld; // 物件情報(代表)_物件名
	private Date endDate; // 物件情報(代表)_リース満了日
	private String astClass; // 物件情報(代表)_種類
	private String astClassName; // 物件情報(代表)_種類名
	private String costSectionCodeLld; // 物件情報(代表) 資産計上負担部課コード
	private String costSectionNameLld; // 物件情報(代表) 資産計上負担部課名
	private String astNameAst; // 機器情報(代表)_資産(機器)名称
	private String holSectionCodeAst; // 機器情報(代表)_保有部署コード
	private String holSectionNameAst; // 機器情報(代表)_保有部署名
	private String holStaffCodeAst; // 機器情報(代表)_資産管理担当者コード
	private String holStaffNameAst; // 機器情報(代表)_資産管理担当者名
	private Long holQuantity; // 機器情報（代表）_数量
	private Long softwareMakerId; // ﾗｲｾﾝｽ情報(代表)_メーカーID
	private String softwareMakerName; // ﾗｲｾﾝｽ情報(代表)_メーカー名
	private Long softwareId; // ﾗｲｾﾝｽ情報(代表)_ソフトウェアID
	private String softwareName; // ﾗｲｾﾝｽ情報(代表)_ソフトウェア名
	private String holSectionCodeLic; // ﾗｲｾﾝｽ情報(代表)_保有部署コード
	private String holSectionNameLic; // ﾗｲｾﾝｽ情報(代表)_保有部署名
	private String holStaffCodeLic; // ﾗｲｾﾝｽ情報(代表)_資産管理担当者コード
	private String holStaffNameLic; // ﾗｲｾﾝｽ情報(代表)_資産管理担当者名
	private Long licQuantity; // ﾗｲｾﾝｽ情報(代表)_ﾗｲｾﾝｽ数
	private String costTypeName; // 販売管理費/原価区分名

	// 抹消バッチで使用
	private String astId;			// 情報機器管理番号 or ライセンス管理番号
	private String holStaffCode;	// 資産管理担当者コード
	private String holStaffName;	// 資産管理担当者名
	private String astType;			// 現物区分(ASSET:情報機器等、LICENNSE:ライセンス)

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		reAmount = Function.getExternalLong(input.readObject());
		purchaseAmount = Function.getExternalLong(input.readObject());
		remainAmount = Function.getExternalLong(input.readObject());
		cancelAmount = Function.getExternalLong(input.readObject());
		apEndLeLineType = (String)input.readObject();
		apEndLeLineTypeName = (String)input.readObject();
		astNameLld = (String)input.readObject();
		endDate = (Date)input.readObject();
		astClass = (String)input.readObject();
		astClassName = (String)input.readObject();
		costSectionCodeLld = (String)input.readObject();
		costSectionNameLld = (String)input.readObject();
		astNameAst = (String)input.readObject();
		holSectionCodeAst = (String)input.readObject();
		holSectionNameAst = (String)input.readObject();
		holStaffCodeAst = (String)input.readObject();
		holStaffNameAst = (String)input.readObject();
		holQuantity =  Function.getExternalLong(input.readObject());
		softwareMakerId = Function.getExternalLong(input.readObject());
		softwareMakerName = (String)input.readObject();
		softwareId = Function.getExternalLong(input.readObject());
		softwareName = (String)input.readObject();
		holSectionCodeLic = (String)input.readObject();
		holSectionNameLic = (String)input.readObject();
		holStaffCodeLic = (String)input.readObject();
		holStaffNameLic = (String)input.readObject();
		licQuantity = Function.getExternalLong(input.readObject());
		costTypeName = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(reAmount);
		output.writeObject(purchaseAmount);
		output.writeObject(remainAmount);
		output.writeObject(cancelAmount);
		output.writeObject(apEndLeLineType);
		output.writeObject(apEndLeLineTypeName);
		output.writeObject(astNameLld);
		output.writeObject(endDate);
		output.writeObject(astClass);
		output.writeObject(astClassName);
		output.writeObject(costSectionCodeLld);
		output.writeObject(costSectionNameLld);
		output.writeObject(astNameAst);
		output.writeObject(holSectionCodeAst);
		output.writeObject(holSectionNameAst);
		output.writeObject(holStaffCodeAst);
		output.writeObject(holStaffNameAst);
		output.writeObject(holQuantity);
		output.writeObject(softwareMakerId);
		output.writeObject(softwareMakerName);
		output.writeObject(softwareId);
		output.writeObject(softwareName);
		output.writeObject(holSectionCodeLic);
		output.writeObject(holSectionNameLic);
		output.writeObject(holStaffCodeLic);
		output.writeObject(holStaffNameLic);
		output.writeObject(licQuantity);
		output.writeObject(costTypeName);

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

	public String getAstNameLld() {
		return astNameLld;
	}

	public void setAstNameLld(String astNameLld) {
		this.astNameLld = astNameLld;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getAstClass() {
		return astClass;
	}

	public void setAstClass(String astClass) {
		this.astClass = astClass;
	}

	public String getAstNameAst() {
		return astNameAst;
	}

	public void setAstNameAst(String astNameAst) {
		this.astNameAst = astNameAst;
	}

	public String getHolSectionCodeAst() {
		return holSectionCodeAst;
	}

	public void setHolSectionCodeAst(String holSectionCodeAst) {
		this.holSectionCodeAst = holSectionCodeAst;
	}

	public String getHolSectionNameAst() {
		return holSectionNameAst;
	}

	public void setHolSectionNameAst(String holSectionNameAst) {
		this.holSectionNameAst = holSectionNameAst;
	}

	public String getHolStaffCodeAst() {
		return holStaffCodeAst;
	}

	public void setHolStaffCodeAst(String holStaffCodeAst) {
		this.holStaffCodeAst = holStaffCodeAst;
	}

	public String getHolStaffNameAst() {
		return holStaffNameAst;
	}

	public void setHolStaffNameAst(String holStaffNameAst) {
		this.holStaffNameAst = holStaffNameAst;
	}

	public Long getSoftwareMakerId() {
		return softwareMakerId;
	}

	public void setSoftwareMakerId(Long softwareMakerId) {
		this.softwareMakerId = softwareMakerId;
	}

	public String getSoftwareMakerName() {
		return softwareMakerName;
	}

	public void setSoftwareMakerName(String softwareMakerName) {
		this.softwareMakerName = softwareMakerName;
	}

	public Long getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(Long softwareId) {
		this.softwareId = softwareId;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getHolSectionCodeLic() {
		return holSectionCodeLic;
	}

	public void setHolSectionCodeLic(String holSectionCodeLic) {
		this.holSectionCodeLic = holSectionCodeLic;
	}

	public String getHolSectionNameLic() {
		return holSectionNameLic;
	}

	public void setHolSectionNameLic(String holSectionNameLic) {
		this.holSectionNameLic = holSectionNameLic;
	}

	public String getHolStaffCodeLic() {
		return holStaffCodeLic;
	}

	public void setHolStaffCodeLic(String holStaffCodeLic) {
		this.holStaffCodeLic = holStaffCodeLic;
	}

	public String getHolStaffNameLic() {
		return holStaffNameLic;
	}

	public void setHolStaffNameLic(String holStaffNameLic) {
		this.holStaffNameLic = holStaffNameLic;
	}

	public Long getLicQuantity() {
		return licQuantity;
	}

	public void setLicQuantity(Long licQuantity) {
		this.licQuantity = licQuantity;
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

	public String getApEndLeLineType() {
		return apEndLeLineType;
	}

	public void setApEndLeLineType(String apEndLeLineType) {
		this.apEndLeLineType = apEndLeLineType;
	}

	public String getApEndLeLineTypeName() {
		return apEndLeLineTypeName;
	}

	public void setApEndLeLineTypeName(String apEndLeLineTypeName) {
		this.apEndLeLineTypeName = apEndLeLineTypeName;
	}

	public String getCostSectionCodeLld() {
		return costSectionCodeLld;
	}

	public void setCostSectionCodeLld(String costSectionCodeLld) {
		this.costSectionCodeLld = costSectionCodeLld;
	}

	public String getCostSectionNameLld() {
		return costSectionNameLld;
	}

	public void setCostSectionNameLld(String costSectionNameLld) {
		this.costSectionNameLld = costSectionNameLld;
	}

	public String getAstId() {
		return astId;
	}

	public void setAstId(String astId) {
		this.astId = astId;
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

	public String getAstType() {
		return astType;
	}

	public void setAstType(String astType) {
		this.astType = astType;
	}

	public Long getPurchaseAmount() {
		return purchaseAmount;
	}

	public void setPurchaseAmount(Long purchaseAmount) {
		this.purchaseAmount = purchaseAmount;
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

	public Long getHolQuantity() {
		return holQuantity;
	}

	public void setHolQuantity(Long holQuantity) {
		this.holQuantity = holQuantity;
	}

	public Long getCancelAmount() {
		return cancelAmount;
	}

	public void setCancelAmount(Long cancelAmount) {
		this.cancelAmount = cancelAmount;
	}

}
