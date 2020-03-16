/*===================================================================
 * ファイル名 : ApEndTanLineAtt.java
 * 概要説明   : 固定資産除売却申請_添付明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-26 1.0  李           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_tan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApEndTanLineAtt implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String attFileName; // 添付ファイル名 表示上のファイル名
	private String attFileId; // 添付ファイル保存ID ディスク保存時のファイル名
	private String attFileComment; // 添付ファイルコメント

	private String attFileIdTmp; // 添付ファイル保存ID 保存前の一時ファイル
	private String createStaffName;	// 作成者社員名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		attFileName = (String)input.readObject();
		attFileId = (String)input.readObject();
		attFileComment = (String)input.readObject();

		attFileIdTmp = (String)input.readObject();
		createStaffName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(attFileName);
		output.writeObject(attFileId);
		output.writeObject(attFileComment);

		output.writeObject(attFileIdTmp);
		output.writeObject(createStaffName);

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
	 * attFileNameを取得します。
	 * @return attFileName
	 */
	public String getAttFileName() {
		return attFileName;
	}

	/**
	 * attFileName
	 * @param attFileNameを設定します。
	 */
	public void setAttFileName(String attFileName) {
		this.attFileName = attFileName;
	}

	/**
	 * attFileIdを取得します。
	 * @return attFileId
	 */
	public String getAttFileId() {
		return attFileId;
	}

	/**
	 * attFileId
	 * @param attFileIdを設定します。
	 */
	public void setAttFileId(String attFileId) {
		this.attFileId = attFileId;
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

	/**
	 * attFileIdTmpを取得します。
	 * @return attFileIdTmp
	 */
	public String getAttFileIdTmp() {
		return attFileIdTmp;
	}

	/**
	 * attFileIdTmp
	 * @param attFileIdTmpを設定します。
	 */
	public void setAttFileIdTmp(String attFileIdTmp) {
		this.attFileIdTmp = attFileIdTmp;
	}

	/**
	 * createStaffNameを取得します。
	 * @return createStaffName
	 */
	public String getCreateStaffName() {
		return createStaffName;
	}

	/**
	 * createStaffName
	 * @param createStaffNameを設定します。
	 */
	public void setCreateStaffName(String createStaffName) {
		this.createStaffName = createStaffName;
	}



}
