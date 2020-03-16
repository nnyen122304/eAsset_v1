/*===================================================================
 * ファイル名 : ApEndLeLineAtt.java
 * 概要説明   : リース満了・解約申請
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-12-21  1.0   高山       新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_le;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApEndLeLineAtt extends LineData {
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

	//	名称項目
	private String attFileIdTmp; // 添付ファイル保存ID 保存前の一時ファイル
	private String createStaffName; // 作成者社員名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		attFileName = (String)input.readObject();
		attFileId = (String)input.readObject();
		attFileComment = (String)input.readObject();

		//	名称項目
		attFileIdTmp = (String)input.readObject();
		createStaffName = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(attFileName);
		output.writeObject(attFileId);
		output.writeObject(attFileComment);

		//	名称項目
		output.writeObject(attFileIdTmp);
		output.writeObject(createStaffName);
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

	public String getAttFileName() {
		return attFileName;
	}

	public void setAttFileName(String attFileName) {
		this.attFileName = attFileName;
	}

	public String getAttFileId() {
		return attFileId;
	}

	public void setAttFileId(String attFileId) {
		this.attFileId = attFileId;
	}

	public String getAttFileComment() {
		return attFileComment;
	}

	public void setAttFileComment(String attFileComment) {
		this.attFileComment = attFileComment;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public void setCreateStaffName(String createStaffName) {
		this.createStaffName = createStaffName;
	}

	public String getCreateStaffName() {
		return createStaffName;
	}

	public String getAttFileIdTmp() {
		return attFileIdTmp;
	}

	public void setAttFileIdTmp(String attFileIdTmp) {
		this.attFileIdTmp = attFileIdTmp;
	}
}