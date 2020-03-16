/*===================================================================
 * ファイル名 : ApGetTanLineAtt.java
 * 概要説明   : 取得申請(有形)_添付明細
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
public class ApGetTanLineAtt extends LineData {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private String createStaffName; // 作成者社員名
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String attFileName; // 添付ファイル名 表示上のファイル名
	private String attFileId; // 添付ファイル保存ID ディスク保存時のファイル名(シーケンスで採番)
	private String attFileIdTmp; // 添付ファイル保存ID 保存前の一時ファイル
	private String attFileComment; // 添付ファイルコメント

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		createStaffName = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		attFileName = (String)input.readObject();
		attFileId = (String)input.readObject();
		attFileIdTmp = (String)input.readObject();
		attFileComment = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(createStaffName);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(attFileName);
		output.writeObject(attFileId);
		output.writeObject(attFileIdTmp);
		output.writeObject(attFileComment);
	}

	/**
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the lineSeq
	 */
	public Integer getLineSeq() {
		return lineSeq;
	}

	/**
	 * @param lineSeq the lineSeq to set
	 */
	public void setLineSeq(Integer lineSeq) {
		this.lineSeq = lineSeq;
	}

	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * @return the createStaffCode
	 */
	public String getCreateStaffCode() {
		return createStaffCode;
	}

	/**
	 * @param createStaffCode the createStaffCode to set
	 */
	public void setCreateStaffCode(String createStaffCode) {
		this.createStaffCode = createStaffCode;
	}

	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * @return the updateStaffCode
	 */
	public String getUpdateStaffCode() {
		return updateStaffCode;
	}

	/**
	 * @param updateStaffCode the updateStaffCode to set
	 */
	public void setUpdateStaffCode(String updateStaffCode) {
		this.updateStaffCode = updateStaffCode;
	}

	/**
	 * @return the attFileName
	 */
	public String getAttFileName() {
		return attFileName;
	}

	/**
	 * @param attFileName the attFileName to set
	 */
	public void setAttFileName(String attFileName) {
		this.attFileName = attFileName;
	}

	/**
	 * @return the attFileId
	 */
	public String getAttFileId() {
		return attFileId;
	}

	/**
	 * @param attFileId the attFileId to set
	 */
	public void setAttFileId(String attFileId) {
		this.attFileId = attFileId;
	}

	/**
	 * @return the attFileIdTmp
	 */
	public String getAttFileIdTmp() {
		return attFileIdTmp;
	}

	/**
	 * @param attFileIdTmp the attFileIdTmp to set
	 */
	public void setAttFileIdTmp(String attFileIdTmp) {
		this.attFileIdTmp = attFileIdTmp;
	}

	/**
	 * @return the createStaffName
	 */
	public String getCreateStaffName() {
		return createStaffName;
	}

	/**
	 * @param createStaffName the createStaffName to set
	 */
	public void setCreateStaffName(String createStaffName) {
		this.createStaffName = createStaffName;
	}

	/**
	 * attFileCommentを取得します。
	 * @return attFileComment
	 */
	public String getAttFileComment() {
		return attFileComment;
	}

	/**
	 * attFileComment
	 * @param attFileCommentを設定します。
	 */
	public void setAttFileComment(String attFileComment) {
		this.attFileComment = attFileComment;
	}

}
