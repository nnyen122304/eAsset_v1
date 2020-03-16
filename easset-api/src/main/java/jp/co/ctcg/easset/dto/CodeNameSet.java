/*===================================================================
 * ファイル名 : CodeNameSetting.java
 * 概要説明   : 汎用マスタ情報
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-02-29 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

@ToString
public class CodeNameSet implements Serializable {
	private static final long serialVersionUID = 1L;

	private String categoryCode; // カテゴリコード
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String useCompanyCode; // 使用可能会社コード 全G会社共通マスタの場合は"000"、その他は使用可能会社をスペース区切りで設定（空白の場合は全G会社使用可）
	private String setCompanyCode; // 設定可能会社コード 各社がそれぞれマスタを管理する場合は"000",それ以外は主管理会社の会社コードを設定
	private String setRoleCode; // 設定可能権限コード 複数権限可能な場合はスペース区切り
	private String parentCategoryCode; // 親カテゴリコード
	private String lineAddEnableFlag; // 行追加有効フラグ 0:行追加不可,1:行追加可
	private String lineDelEnableFlag; // 行削除有効フラグ 0:行追加不可,1:行追加可
	private String description; // 備考

	private String setCompanyName; // 設定可能会社名
	private String createStaffName; // 作成者名
	private String updateStaffName; // 最終更新者

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		categoryCode = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		useCompanyCode = (String)input.readObject();
		setCompanyCode = (String)input.readObject();
		setRoleCode = (String)input.readObject();
		parentCategoryCode = (String)input.readObject();
		lineAddEnableFlag = (String)input.readObject();
		lineDelEnableFlag = (String)input.readObject();
		description = (String)input.readObject();

		setCompanyName = (String)input.readObject();
		createStaffName = (String)input.readObject();
		updateStaffName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		output.writeObject(categoryCode);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(useCompanyCode);
		output.writeObject(setCompanyCode);
		output.writeObject(setRoleCode);
		output.writeObject(parentCategoryCode);
		output.writeObject(lineAddEnableFlag);
		output.writeObject(lineDelEnableFlag);
		output.writeObject(description);

		output.writeObject(setCompanyName);
		output.writeObject(createStaffName);
		output.writeObject(updateStaffName);

	}

	/**
	 * categoryCodeを取得します。
	 * @return categoryCode
	 */
	public String getCategoryCode() {
		return categoryCode;
	}

	/**
	 * categoryCode
	 * @param categoryCodeを設定します。
	 */
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
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
	 * useCompanyCodeを取得します。
	 * @return useCompanyCode
	 */
	public String getUseCompanyCode() {
		return useCompanyCode;
	}

	/**
	 * useCompanyCode
	 * @param useCompanyCodeを設定します。
	 */
	public void setUseCompanyCode(String useCompanyCode) {
		this.useCompanyCode = useCompanyCode;
	}

	/**
	 * setCompanyCodeを取得します。
	 * @return setCompanyCode
	 */
	public String getSetCompanyCode() {
		return setCompanyCode;
	}

	/**
	 * setCompanyCode
	 * @param setCompanyCodeを設定します。
	 */
	public void setSetCompanyCode(String setCompanyCode) {
		this.setCompanyCode = setCompanyCode;
	}

	/**
	 * setRoleCodeを取得します。
	 * @return setRoleCode
	 */
	public String getSetRoleCode() {
		return setRoleCode;
	}

	/**
	 * setRoleCode
	 * @param setRoleCodeを設定します。
	 */
	public void setSetRoleCode(String setRoleCode) {
		this.setRoleCode = setRoleCode;
	}

	/**
	 * parentCategoryCodeを取得します。
	 * @return parentCategoryCode
	 */
	public String getParentCategoryCode() {
		return parentCategoryCode;
	}

	/**
	 * parentCategoryCode
	 * @param parentCategoryCodeを設定します。
	 */
	public void setParentCategoryCode(String parentCategoryCode) {
		this.parentCategoryCode = parentCategoryCode;
	}

	/**
	 * lineAddEnableFlagを取得します。
	 * @return lineAddEnableFlag
	 */
	public String getLineAddEnableFlag() {
		return lineAddEnableFlag;
	}

	/**
	 * lineAddEnableFlag
	 * @param lineAddEnableFlagを設定します。
	 */
	public void setLineAddEnableFlag(String lineAddEnableFlag) {
		this.lineAddEnableFlag = lineAddEnableFlag;
	}

	/**
	 * lineDelEnableFlagを取得します。
	 * @return lineDelEnableFlag
	 */
	public String getLineDelEnableFlag() {
		return lineDelEnableFlag;
	}

	/**
	 * lineDelEnableFlag
	 * @param lineDelEnableFlagを設定します。
	 */
	public void setLineDelEnableFlag(String lineDelEnableFlag) {
		this.lineDelEnableFlag = lineDelEnableFlag;
	}

	/**
	 * descriptionを取得します。
	 * @return description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * description
	 * @param descriptionを設定します。
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * setCompanyNameを取得します。
	 * @return setCompanyName
	 */
	public String getSetCompanyName() {
		return setCompanyName;
	}

	/**
	 * setCompanyName
	 * @param setCompanyNameを設定します。
	 */
	public void setSetCompanyName(String setCompanyName) {
		this.setCompanyName = setCompanyName;
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
	 * updateStaffNameを取得します。
	 * @return updateStaffName
	 */
	public String getUpdateStaffName() {
		return updateStaffName;
	}

	/**
	 * updateStaffName
	 * @param updateStaffNameを設定します。
	 */
	public void setUpdateStaffName(String updateStaffName) {
		this.updateStaffName = updateStaffName;
	}

}
