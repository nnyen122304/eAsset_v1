/*===================================================================
 * ファイル名 : CodeNameSetItem.java
 * 概要説明   : 汎用マスタ設定項目検索
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

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class CodeNameSetItem implements Serializable {
	private static final long serialVersionUID = 1L;

	private String categoryCode; // カテゴリコード
	private Integer itemSeq; // 項目シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String itemName; // 項目名
	private String itemInputType; // 項目入力種別 TXT:テキスト,TXTA:テキストエリア,NUM:数値,CMB:コンボボックス
	private String itemComment; // 注釈
	private Long width; // 項目幅
	private Long height; // 項目高
	private String displayOnly; // 項目表示専用設定 true:表示専用、false:以外
	private String readOnly; // 項目読取専用設定 true:読取専用、false:以外
	private String required; // 項目必須設定 true:必須、false:以外
	private Long maxChars; // 項目最大入力文字数
	private String restrict; // 項目入力文字制限
	private String editLock; // 項目編集ロック true:ロック、false:以外
	private String cmbData; // コンボボックス用データ
	private String cmbLabel; // コンボボックス用ラベル

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		categoryCode = (String)input.readObject();
		itemSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		itemName = (String)input.readObject();
		itemInputType = (String)input.readObject();
		itemComment = (String)input.readObject();
		width = Function.getExternalLong(input.readObject());
		height = Function.getExternalLong(input.readObject());
		displayOnly = (String)input.readObject();
		readOnly = (String)input.readObject();
		required = (String)input.readObject();
		maxChars = Function.getExternalLong(input.readObject());
		restrict = (String)input.readObject();
		editLock = (String)input.readObject();
		cmbData = (String)input.readObject();
		cmbLabel = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		output.writeObject(categoryCode);
		output.writeObject(itemSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(itemName);
		output.writeObject(itemInputType);
		output.writeObject(itemComment);
		output.writeObject(width);
		output.writeObject(height);
		output.writeObject(displayOnly);
		output.writeObject(readOnly);
		output.writeObject(required);
		output.writeObject(maxChars);
		output.writeObject(restrict);
		output.writeObject(editLock);
		output.writeObject(cmbData);
		output.writeObject(cmbLabel);

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
	 * itemSeqを取得します。
	 * @return itemSeq
	 */
	public Integer getItemSeq() {
		return itemSeq;
	}

	/**
	 * itemSeq
	 * @param itemSeqを設定します。
	 */
	public void setItemSeq(Integer itemSeq) {
		this.itemSeq = itemSeq;
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
	 * itemNameを取得します。
	 * @return itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * itemName
	 * @param itemNameを設定します。
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * itemInputTypeを取得します。
	 * @return itemInputType
	 */
	public String getItemInputType() {
		return itemInputType;
	}

	/**
	 * itemInputType
	 * @param itemInputTypeを設定します。
	 */
	public void setItemInputType(String itemInputType) {
		this.itemInputType = itemInputType;
	}

	/**
	 * itemCommentを取得します。
	 * @return itemComment
	 */
	public String getItemComment() {
		return itemComment;
	}

	/**
	 * itemComment
	 * @param itemCommentを設定します。
	 */
	public void setItemComment(String itemComment) {
		this.itemComment = itemComment;
	}

	/**
	 * widthを取得します。
	 * @return width
	 */
	public Long getWidth() {
		return width;
	}

	/**
	 * width
	 * @param widthを設定します。
	 */
	public void setWidth(Long width) {
		this.width = width;
	}

	/**
	 * heightを取得します。
	 * @return height
	 */
	public Long getHeight() {
		return height;
	}

	/**
	 * height
	 * @param heightを設定します。
	 */
	public void setHeight(Long height) {
		this.height = height;
	}

	/**
	 * displayOnlyを取得します。
	 * @return displayOnly
	 */
	public String getDisplayOnly() {
		return displayOnly;
	}

	/**
	 * displayOnly
	 * @param displayOnlyを設定します。
	 */
	public void setDisplayOnly(String displayOnly) {
		this.displayOnly = displayOnly;
	}

	/**
	 * readOnlyを取得します。
	 * @return readOnly
	 */
	public String getReadOnly() {
		return readOnly;
	}

	/**
	 * readOnly
	 * @param readOnlyを設定します。
	 */
	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	/**
	 * requiredを取得します。
	 * @return required
	 */
	public String getRequired() {
		return required;
	}

	/**
	 * required
	 * @param requiredを設定します。
	 */
	public void setRequired(String required) {
		this.required = required;
	}

	/**
	 * maxCharsを取得します。
	 * @return maxChars
	 */
	public Long getMaxChars() {
		return maxChars;
	}

	/**
	 * maxChars
	 * @param maxCharsを設定します。
	 */
	public void setMaxChars(Long maxChars) {
		this.maxChars = maxChars;
	}

	/**
	 * restrictを取得します。
	 * @return restrict
	 */
	public String getRestrict() {
		return restrict;
	}

	/**
	 * restrict
	 * @param restrictを設定します。
	 */
	public void setRestrict(String restrict) {
		this.restrict = restrict;
	}

	/**
	 * editLockを取得します。
	 * @return editLock
	 */
	public String getEditLock() {
		return editLock;
	}

	/**
	 * editLock
	 * @param editLockを設定します。
	 */
	public void setEditLock(String editLock) {
		this.editLock = editLock;
	}

	/**
	 * cmbDataを取得します。
	 * @return cmbData
	 */
	public String getCmbData() {
		return cmbData;
	}

	/**
	 * cmbData
	 * @param cmbDataを設定します。
	 */
	public void setCmbData(String cmbData) {
		this.cmbData = cmbData;
	}

	/**
	 * cmbLabelを取得します。
	 * @return cmbLabel
	 */
	public String getCmbLabel() {
		return cmbLabel;
	}

	/**
	 * cmbLabel
	 * @param cmbLabelを設定します。
	 */
	public void setCmbLabel(String cmbLabel) {
		this.cmbLabel = cmbLabel;
	}

}
