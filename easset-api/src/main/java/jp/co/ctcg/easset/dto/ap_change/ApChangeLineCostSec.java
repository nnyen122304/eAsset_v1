/*===================================================================
 * ファイル名 : ApChangeLineCostSec.java
 * 概要説明   : 移動申請_経費負担部課明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-02 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_change;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApChangeLineCostSec implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private String apChangeLineCostType; // 経費負担部課明細区分 1:リース契約,2:レンタル契約,A:移動元,B:移動先
	private String astNum; // 資産番号 A:移動元,B:移動先のデータは"-"固定
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String costCompanyCode; // 経費負担会社コード
	private String costSectionCode; // 経費負担部課コード
	private Integer costSectionYear; // 経費負担部課年度
	private Double costDist; // 配分(%)

	private String costCompanyName; // 経費負担会社名
	private String costSectionName; // 経費負担部課名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		apChangeLineCostType = (String)input.readObject();
		astNum = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		costCompanyCode = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		costSectionYear = Function.getExternalInteger(input.readObject());
		costDist = Function.getExternalDouble(input.readObject());

		costCompanyName = (String)input.readObject();
		costSectionName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(apChangeLineCostType);
		output.writeObject(astNum);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(costCompanyCode);
		output.writeObject(costSectionCode);
		output.writeObject(costSectionYear);
		output.writeObject(costDist);

		output.writeObject(costCompanyName);
		output.writeObject(costSectionName);

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
	 * apChangeLineCostTypeを取得します。
	 * @return apChangeLineCostType
	 */
	public String getApChangeLineCostType() {
		return apChangeLineCostType;
	}

	/**
	 * apChangeLineCostType
	 * @param apChangeLineCostTypeを設定します。
	 */
	public void setApChangeLineCostType(String apChangeLineCostType) {
		this.apChangeLineCostType = apChangeLineCostType;
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
	 * costDistを取得します。
	 * @return costDist
	 */
	public Double getCostDist() {
		return costDist;
	}

	/**
	 * costDist
	 * @param costDistを設定します。
	 */
	public void setCostDist(Double costDist) {
		this.costDist = costDist;
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
	 * @return the astNum
	 */
	public String getAstNum() {
		return astNum;
	}

	/**
	 * @param astNum the astNum to set
	 */
	public void setAstNum(String astNum) {
		this.astNum = astNum;
	}

	public Integer getCostSectionYear() {
		return costSectionYear;
	}

	public void setCostSectionYear(Integer costSectionYear) {
		this.costSectionYear = costSectionYear;
	}

}
