/*===================================================================
 * ファイル名 : ApGetTanLineOtrCost.java
 * 概要説明   : 取得申請(有形)_その他費用明細
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
public class ApGetTanLineOtrCost extends LineData {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private Long getOtrAmount; // 金額
	private String getOtrComment; // コメント

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		getOtrAmount = Function.getExternalLong(input.readObject());
		getOtrComment = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(getOtrAmount);
		output.writeObject(getOtrComment);
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
	 * getOtrAmountを取得します。
	 * @return getOtrAmount
	 */
	public Long getGetOtrAmount() {
		return getOtrAmount;
	}

	/**
	 * getOtrAmount
	 * @param getOtrAmountを設定します。
	 */
	public void setGetOtrAmount(Long getOtrAmount) {
		this.getOtrAmount = getOtrAmount;
	}

	/**
	 * getOtrCommentを取得します。
	 * @return getOtrComment
	 */
	public String getGetOtrComment() {
		return getOtrComment;
	}

	/**
	 * getOtrComment
	 * @param getOtrCommentを設定します。
	 */
	public void setGetOtrComment(String getOtrComment) {
		this.getOtrComment = getOtrComment;
	}

}
