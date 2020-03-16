/*===================================================================
 * ファイル名 : ApGetTanLineCostSec.java
 * 概要説明   : 取得申請(有形)_経費負担部課明細
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
public class ApGetTanLineCostSec extends LineData {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String costCompanyCode; // 経費負担会社コード
	private String costSectionCode; // 経費負担部課コード
	private Integer costSectionYear; // 経費負担部課年度
	private Double costDist; // 配分

	private String costCompanyName; // 経費負担会社名
	private String costSectionName; // 経費負担部課名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
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
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
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

	public String getCostCompanyName() {
		return costCompanyName;
	}

	public void setCostCompanyName(String costCompanyName) {
		this.costCompanyName = costCompanyName;
	}

	public String getCostSectionName() {
		return costSectionName;
	}

	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	public Integer getCostSectionYear() {
		return costSectionYear;
	}

	public void setCostSectionYear(Integer costSectionYear) {
		this.costSectionYear = costSectionYear;
	}

}
