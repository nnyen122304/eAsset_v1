/*===================================================================
 * ファイル名 : ApBgnIntLineProfEst.java
 * 概要説明   : サービス提供開始報告_利益予測明細
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

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApBgnIntLineProfEst extends LineData {
	private static final long serialVersionUID = 1L;

	private String applicationId;		// 申請書番号
	private Integer lineSeq;			// 行シーケンス
	private Date createDate;			// 作成日
	private String createStaffCode;		// 作成者社員番号
	private Date updateDate;			// 更新日
	private String updateStaffCode;		// 更新者社員番号
	private Long profSalesQuantity;		// 販売本数
	private Long profSalesAmount;		// 売上
	private Long profCostAmount;		// 売上原価
	private Long profGrossAmount;		// 粗利益

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		profSalesQuantity = Function.getExternalLong(input.readObject());
		profSalesAmount = Function.getExternalLong(input.readObject());
		profCostAmount = Function.getExternalLong(input.readObject());
		profGrossAmount = Function.getExternalLong(input.readObject());
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(profSalesQuantity);
		output.writeObject(profSalesAmount);
		output.writeObject(profCostAmount);
		output.writeObject(profGrossAmount);
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
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

	public Long getProfSalesQuantity() {
		return profSalesQuantity;
	}

	public void setProfSalesQuantity(Long profSalesQuantity) {
		this.profSalesQuantity = profSalesQuantity;
	}

	public Long getProfSalesAmount() {
		return profSalesAmount;
	}

	public void setProfSalesAmount(Long profSalesAmount) {
		this.profSalesAmount = profSalesAmount;
	}

	public Long getProfCostAmount() {
		return profCostAmount;
	}

	public void setProfCostAmount(Long profCostAmount) {
		this.profCostAmount = profCostAmount;
	}

	public Long getProfGrossAmount() {
		return profGrossAmount;
	}

	public void setProfGrossAmount(Long profGrossAmount) {
		this.profGrossAmount = profGrossAmount;
	}

}
