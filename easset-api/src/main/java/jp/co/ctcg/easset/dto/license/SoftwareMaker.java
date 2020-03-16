/*===================================================================
 * ファイル名 : SoftwareMaker.java
 * 概要説明   : ソフトウェアメーカーデータクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-25 1.0  リヨン 崔     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.license;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class SoftwareMaker implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long softwareMakerId; // ソフトウェアメーカーID
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String softwareMakerName; // ソフトウェアメーカー名
	private String apStaffCode; // 申請者社員番号
	private Date apDate; // 申請日
	private String deleteFlag; // 削除フラグ 0:有効データ,1:無効データ(削除済)
	private String deleteStaffCode; // 削除実行社員番号
	private Date deleteDate; // 削除日

	private String createStaffName;	//	作成者
	private String updateStaffName; // 更新者社員名
	private String apStaffName; // 申請者社員名
	private String deleteStaffName;	//	削除実行社員名

	private String deleteFlagName;	//	有効フラグ名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		softwareMakerId = Function.getExternalLong(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		softwareMakerName = (String)input.readObject();
		apStaffCode = (String)input.readObject();
		apDate = (Date)input.readObject();
		deleteFlag = (String)input.readObject();
		deleteStaffCode = (String)input.readObject();
		deleteDate = (Date)input.readObject();

		updateStaffName = (String)input.readObject();
		apStaffName = (String)input.readObject();
		createStaffName = (String)input.readObject();
		deleteStaffName = (String)input.readObject();
		deleteFlagName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(softwareMakerId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(softwareMakerName);
		output.writeObject(apStaffCode);
		output.writeObject(apDate);
		output.writeObject(deleteFlag);
		output.writeObject(deleteStaffCode);
		output.writeObject(deleteDate);

		output.writeObject(updateStaffName);
		output.writeObject(apStaffName);
		output.writeObject(createStaffName);
		output.writeObject(deleteStaffName);
		output.writeObject(deleteFlagName);


	}

	/**
	 * @return the softwareMakerId
	 */
	public Long getSoftwareMakerId() {
		return softwareMakerId;
	}

	/**
	 * @param softwareMakerId the softwareMakerId to set
	 */
	public void setSoftwareMakerId(Long softwareMakerId) {
		this.softwareMakerId = softwareMakerId;
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
	 * @return the updateStaffName
	 */
	public String getUpdateStaffName() {
		return updateStaffName;
	}

	/**
	 * @param updateStaffName the updateStaffName to set
	 */
	public void setUpdateStaffName(String updateStaffName) {
		this.updateStaffName = updateStaffName;
	}

	/**
	 * @return the softwareMakerName
	 */
	public String getSoftwareMakerName() {
		return softwareMakerName;
	}

	/**
	 * @param softwareMakerName the softwareMakerName to set
	 */
	public void setSoftwareMakerName(String softwareMakerName) {
		this.softwareMakerName = softwareMakerName;
	}

	/**
	 * @return the apStaffCode
	 */
	public String getApStaffCode() {
		return apStaffCode;
	}

	/**
	 * @param apStaffCode the apStaffCode to set
	 */
	public void setApStaffCode(String apStaffCode) {
		this.apStaffCode = apStaffCode;
	}

	/**
	 * @return the apStaffName
	 */
	public String getApStaffName() {
		return apStaffName;
	}

	/**
	 * @param apStaffName the apStaffName to set
	 */
	public void setApStaffName(String apStaffName) {
		this.apStaffName = apStaffName;
	}

	/**
	 * @return the apDate
	 */
	public Date getApDate() {
		return apDate;
	}

	/**
	 * @param apDate the apDate to set
	 */
	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}

	/**
	 * @return the deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * @param deleteFlag the deleteFlag to set
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * @return the deleteStaffCode
	 */
	public String getDeleteStaffCode() {
		return deleteStaffCode;
	}

	/**
	 * @param deleteStaffCode the deleteStaffCode to set
	 */
	public void setDeleteStaffCode(String deleteStaffCode) {
		this.deleteStaffCode = deleteStaffCode;
	}

	/**
	 * @return the deleteDate
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * @param deleteDate the deleteDate to set
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
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

	/**
	 * deleteStaffNameを取得します。
	 * @return deleteStaffName
	 */
	public String getDeleteStaffName() {
		return deleteStaffName;
	}

	/**
	 * deleteStaffName
	 * @param deleteStaffNameを設定します。
	 */
	public void setDeleteStaffName(String deleteStaffName) {
		this.deleteStaffName = deleteStaffName;
	}

	/**
	 * deleteFlagNameを取得します。
	 * @return deleteFlagName
	 */
	public String getDeleteFlagName() {
		return deleteFlagName;
	}

	/**
	 * deleteFlagName
	 * @param deleteFlagNameを設定します。
	 */
	public void setDeleteFlagName(String deleteFlagName) {
		this.deleteFlagName = deleteFlagName;
	}

}
