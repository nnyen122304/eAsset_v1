/*===================================================================
 * ファイル名 : ApEndTanSR.java
 * 概要説明   : 除売却申請検索結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-26 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_tan;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class ApEndTanSR extends ApEndTan {
	private static final long serialVersionUID = 1L;

	private String astName;	//	代表資産名
	private Date astDate;	//計上日
	private String costDistCode;	//	配賦コード
	private String costDistName;	//	配賦名
	private String astNameAst;	//	資産名情報機器
	private String holSectionCodeAst;	//	保有部署コード_情報機器
	private String holSectionNameAst;	//	保有部署名_情報機器
	private String holStaffCodeAst;	//	資産管理担当者コード_情報機器
	private String holStaffNameAst;	//	資産管理担当者_情報機器
	private Long softwareMakerId; // ソフトウェアメーカーID
	private Long softwareId; // ソフトウェアID
	private String softwareMakerName; // ソフトウェアメーカー
	private String softwareName; // ソフトウェア
	private String holSectionCodeLic;	//	保有部署コード_ﾗｲｾﾝｽ
	private String holSectionNameLic;	//	保有部署名_ﾗｲｾﾝｽ
	private String holStaffCodeLic;	//	資産管理担当者コード_ﾗｲｾﾝｽ
	private String holStaffNameLic;	//	資産管理担当者_ﾗｲｾﾝｽ
	private Long holQuantity; // 数量
	private Long licQuantity; // ライセンス保有数
	private String costSectionCodeL; // 資産計上部課コード(固定資産)
	private String costSectionNameL; // 資産計上部課名(固定資産)

	private String holSectionCodeI;	//	保有部署コード_無形
	private String holSectionNameI;	//	保有部署名_無形
	private String holStaffCodeI;	//	無形管理担当者コード_無形
	private String holStaffNameI;	//	無形管理担当者_無形
	private String getApplicationId;	//	取得申請書番号

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		astName = (String)input.readObject();
		astDate = (Date)input.readObject();
		costDistCode = (String)input.readObject();
		costDistName = (String)input.readObject();
		astNameAst = (String)input.readObject();
		holSectionCodeAst = (String)input.readObject();
		holSectionNameAst = (String)input.readObject();
		holStaffCodeAst = (String)input.readObject();
		holStaffNameAst = (String)input.readObject();
		softwareMakerId = Function.getExternalLong(input.readObject());
		softwareId = Function.getExternalLong(input.readObject());
		softwareMakerName = (String)input.readObject();
		softwareName = (String)input.readObject();
		holSectionCodeLic = (String)input.readObject();
		holSectionNameLic = (String)input.readObject();
		holStaffCodeLic = (String)input.readObject();
		holStaffNameLic = (String)input.readObject();
		holQuantity =  Function.getExternalLong(input.readObject());
		licQuantity =  Function.getExternalLong(input.readObject());
		costSectionCodeL = (String)input.readObject();
		costSectionNameL = (String)input.readObject();

		holSectionCodeI = (String)input.readObject();
		holSectionNameI = (String)input.readObject();
		holStaffCodeI = (String)input.readObject();
		holStaffNameI = (String)input.readObject();
		getApplicationId = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(astName);
		output.writeObject(astDate);
		output.writeObject(costDistCode);
		output.writeObject(costDistName);
		output.writeObject(astNameAst);
		output.writeObject(holSectionCodeAst);
		output.writeObject(holSectionNameAst);
		output.writeObject(holStaffCodeAst);
		output.writeObject(holStaffNameAst);
		output.writeObject(softwareMakerId);
		output.writeObject(softwareId);
		output.writeObject(softwareMakerName);
		output.writeObject(softwareName);
		output.writeObject(holSectionCodeLic);
		output.writeObject(holSectionNameLic);
		output.writeObject(holStaffCodeLic);
		output.writeObject(holStaffNameLic);
		output.writeObject(holQuantity);
		output.writeObject(licQuantity);
		output.writeObject(costSectionCodeL);
		output.writeObject(costSectionNameL);

		output.writeObject(holSectionCodeI);
		output.writeObject(holSectionNameI);
		output.writeObject(holStaffCodeI);
		output.writeObject(holStaffNameI);
		output.writeObject(getApplicationId);
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
	 * costDistCodeを取得します。
	 * @return costDistCode
	 */
	public String getCostDistCode() {
		return costDistCode;
	}

	/**
	 * costDistCode
	 * @param costDistCodeを設定します。
	 */
	public void setCostDistCode(String costDistCode) {
		this.costDistCode = costDistCode;
	}

	/**
	 * astNameAstを取得します。
	 * @return astNameAst
	 */
	public String getAstNameAst() {
		return astNameAst;
	}

	/**
	 * astNameAst
	 * @param astNameAstを設定します。
	 */
	public void setAstNameAst(String astNameAst) {
		this.astNameAst = astNameAst;
	}

	/**
	 * holSectionCodeAstを取得します。
	 * @return holSectionCodeAst
	 */
	public String getHolSectionCodeAst() {
		return holSectionCodeAst;
	}

	/**
	 * holSectionCodeAst
	 * @param holSectionCodeAstを設定します。
	 */
	public void setHolSectionCodeAst(String holSectionCodeAst) {
		this.holSectionCodeAst = holSectionCodeAst;
	}

	/**
	 * holSectionNameAstを取得します。
	 * @return holSectionNameAst
	 */
	public String getHolSectionNameAst() {
		return holSectionNameAst;
	}

	/**
	 * holSectionNameAst
	 * @param holSectionNameAstを設定します。
	 */
	public void setHolSectionNameAst(String holSectionNameAst) {
		this.holSectionNameAst = holSectionNameAst;
	}

	/**
	 * holStaffCodeAstを取得します。
	 * @return holStaffCodeAst
	 */
	public String getHolStaffCodeAst() {
		return holStaffCodeAst;
	}

	/**
	 * holStaffCodeAst
	 * @param holStaffCodeAstを設定します。
	 */
	public void setHolStaffCodeAst(String holStaffCodeAst) {
		this.holStaffCodeAst = holStaffCodeAst;
	}

	/**
	 * holStaffNameAstを取得します。
	 * @return holStaffNameAst
	 */
	public String getHolStaffNameAst() {
		return holStaffNameAst;
	}

	/**
	 * holStaffNameAst
	 * @param holStaffNameAstを設定します。
	 */
	public void setHolStaffNameAst(String holStaffNameAst) {
		this.holStaffNameAst = holStaffNameAst;
	}

	/**
	 * softwareMakerIdを取得します。
	 * @return softwareMakerId
	 */
	public Long getSoftwareMakerId() {
		return softwareMakerId;
	}

	/**
	 * softwareMakerId
	 * @param softwareMakerIdを設定します。
	 */
	public void setSoftwareMakerId(Long softwareMakerId) {
		this.softwareMakerId = softwareMakerId;
	}

	/**
	 * softwareIdを取得します。
	 * @return softwareId
	 */
	public Long getSoftwareId() {
		return softwareId;
	}

	/**
	 * softwareId
	 * @param softwareIdを設定します。
	 */
	public void setSoftwareId(Long softwareId) {
		this.softwareId = softwareId;
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
	 * holSectionCodeLicを取得します。
	 * @return holSectionCodeLic
	 */
	public String getHolSectionCodeLic() {
		return holSectionCodeLic;
	}

	/**
	 * holSectionCodeLic
	 * @param holSectionCodeLicを設定します。
	 */
	public void setHolSectionCodeLic(String holSectionCodeLic) {
		this.holSectionCodeLic = holSectionCodeLic;
	}

	/**
	 * holSectionNameLicを取得します。
	 * @return holSectionNameLic
	 */
	public String getHolSectionNameLic() {
		return holSectionNameLic;
	}

	/**
	 * holSectionNameLic
	 * @param holSectionNameLicを設定します。
	 */
	public void setHolSectionNameLic(String holSectionNameLic) {
		this.holSectionNameLic = holSectionNameLic;
	}

	/**
	 * holStaffCodeLicを取得します。
	 * @return holStaffCodeLic
	 */
	public String getHolStaffCodeLic() {
		return holStaffCodeLic;
	}

	/**
	 * holStaffCodeLic
	 * @param holStaffCodeLicを設定します。
	 */
	public void setHolStaffCodeLic(String holStaffCodeLic) {
		this.holStaffCodeLic = holStaffCodeLic;
	}

	/**
	 * holStaffNameLicを取得します。
	 * @return holStaffNameLic
	 */
	public String getHolStaffNameLic() {
		return holStaffNameLic;
	}

	/**
	 * holStaffNameLic
	 * @param holStaffNameLicを設定します。
	 */
	public void setHolStaffNameLic(String holStaffNameLic) {
		this.holStaffNameLic = holStaffNameLic;
	}

	/**
	 * holQuantityを取得します。
	 * @return holQuantity
	 */
	public Long getHolQuantity() {
		return holQuantity;
	}

	/**
	 * holQuantity
	 * @param holQuantityを設定します。
	 */
	public void setHolQuantity(Long holQuantity) {
		this.holQuantity = holQuantity;
	}

	/**
	 * licQuantityを取得します。
	 * @return licQuantity
	 */
	public Long getLicQuantity() {
		return licQuantity;
	}

	/**
	 * licQuantity
	 * @param licQuantityを設定します。
	 */
	public void setLicQuantity(Long licQuantity) {
		this.licQuantity = licQuantity;
	}

	/**
	 * astDateを取得します。
	 * @return astDate
	 */
	public Date getAstDate() {
		return astDate;
	}

	/**
	 * astDate
	 * @param astDateを設定します。
	 */
	public void setAstDate(Date astDate) {
		this.astDate = astDate;
	}

	/**
	 * costDistNameを取得します。
	 * @return costDistName
	 */
	public String getCostDistName() {
		return costDistName;
	}

	/**
	 * costDistName
	 * @param costDistNameを設定します。
	 */
	public void setCostDistName(String costDistName) {
		this.costDistName = costDistName;
	}

	/**
	 * costSectionNameLを取得します。
	 * @return costSectionNameL
	 */
	public String getCostSectionNameL() {
		return costSectionNameL;
	}

	/**
	 * costSectionNameL
	 * @param costSectionNameLを設定します。
	 */
	public void setCostSectionNameL(String costSectionNameL) {
		this.costSectionNameL = costSectionNameL;
	}

	/**
	 * @return the costSectionCodeL
	 */
	public String getCostSectionCodeL() {
		return costSectionCodeL;
	}

	/**
	 * @param costSectionCodeL the costSectionCodeL to set
	 */
	public void setCostSectionCodeL(String costSectionCodeL) {
		this.costSectionCodeL = costSectionCodeL;
	}

	/**
	 * @return the holSectionCodeI
	 */
	public String getHolSectionCodeI() {
		return holSectionCodeI;
	}

	/**
	 * @param holSectionCodeI the holSectionCodeI to set
	 */
	public void setHolSectionCodeI(String holSectionCodeI) {
		this.holSectionCodeI = holSectionCodeI;
	}

	/**
	 * @return the holSectionNameI
	 */
	public String getHolSectionNameI() {
		return holSectionNameI;
	}

	/**
	 * @param holSectionNameI the holSectionNameI to set
	 */
	public void setHolSectionNameI(String holSectionNameI) {
		this.holSectionNameI = holSectionNameI;
	}

	/**
	 * @return the holStaffCodeI
	 */
	public String getHolStaffCodeI() {
		return holStaffCodeI;
	}

	/**
	 * @param holStaffCodeI the holStaffCodeI to set
	 */
	public void setHolStaffCodeI(String holStaffCodeI) {
		this.holStaffCodeI = holStaffCodeI;
	}

	/**
	 * @return the holStaffNameI
	 */
	public String getHolStaffNameI() {
		return holStaffNameI;
	}

	/**
	 * @param holStaffNameI the holStaffNameI to set
	 */
	public void setHolStaffNameI(String holStaffNameI) {
		this.holStaffNameI = holStaffNameI;
	}

	/**
	 * @return the getApplicationId
	 */
	public String getGetApplicationId() {
		return getApplicationId;
	}

	/**
	 * @param getApplicationId the getApplicationId to set
	 */
	public void setGetApplicationId(String getApplicationId) {
		this.getApplicationId = getApplicationId;
	}



}
