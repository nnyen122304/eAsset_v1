/*===================================================================
 * ファイル名 : ApGetIntLineAtt.java
 * 概要説明   : 取得申請(無形)_添付明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_get_int;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApGetIntLineAtt extends LineData {
	private static final long serialVersionUID = 1L;

	private String applicationId;		// 申請書番号
	private String applicationVersion;	// 申請書バージョン
	private Integer lineSeq;			// 行シーケンス
	private Date createDate;			// 作成日
	private String createStaffCode;		// 作成者社員番号
	private String createStaffName;		// 作成者社員名
	private Date updateDate;			// 更新日
	private String updateStaffCode;		// 更新者社員番号
	private String attFileName;			// 添付ファイル名 表示上のファイル名
	private String attFileId;			// 添付ファイル保存ID ディスク保存時のファイル名
	private String attFileIdTmp;		// 添付ファイル保存ID 保存前の一時ファイル
	private String attComment;			// コメント

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
		applicationVersion = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		createStaffName = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		attFileName = (String)input.readObject();
		attFileId = (String)input.readObject();
		attFileIdTmp = (String)input.readObject();
		attComment = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
		output.writeObject(applicationVersion);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(createStaffName);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(attFileName);
		output.writeObject(attFileId);
		output.writeObject(attFileIdTmp);
		output.writeObject(attComment);
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
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

	public String getCreateStaffName() {
		return createStaffName;
	}

	public void setCreateStaffName(String createStaffName) {
		this.createStaffName = createStaffName;
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

	public String getAttFileIdTmp() {
		return attFileIdTmp;
	}

	public void setAttFileIdTmp(String attFileIdTmp) {
		this.attFileIdTmp = attFileIdTmp;
	}

	public String getAttComment() {
		return attComment;
	}

	public void setAttComment(String attComment) {
		this.attComment = attComment;
	}


}
