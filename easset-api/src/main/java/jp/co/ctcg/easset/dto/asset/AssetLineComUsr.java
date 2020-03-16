/*===================================================================
 * ファイル名 : AssetLineComUsr.java
 * 概要説明   : 情報機器等登録申請_共有ユーザー明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-02 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.asset;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class AssetLineComUsr extends LineData {
	private static final long serialVersionUID = 1L;

	private String assetId;				// 登録申請番号
	private Integer lineSeq;			// 行シーケンス
	private Date createDate;			// 作成日
	private String createStaffCode;		// 作成者社員番号
	private Date updateDate;			// 更新日
	private String updateStaffCode;		// 更新者社員番号
	private String comStaffCode;		// 共有者社員番号 情報機器等と同項目
	private String comStaffName;		// 共有者氏名 情報機器等と同項目
	private String comCompanyCode;		// 共有者所属会社コード 情報機器等と同項目
	private String comCompanyName;		// 共有者所属会社名 情報機器等と同項目
	private String comSectionCode;		// 共有者所属部署コード 情報機器等と同項目
	private String comSectionName;		// 共有者所属部署名 情報機器等と同項目
	private Date comStartDate;			// 使用開始日 情報機器等と同項目

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);		// 行データスーパークラスの処理

		assetId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		comStaffCode = (String)input.readObject();
		comStaffName = (String)input.readObject();
		comCompanyCode = (String)input.readObject();
		comCompanyName = (String)input.readObject();
		comSectionCode = (String)input.readObject();
		comSectionName = (String)input.readObject();
		comStartDate = (Date)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);		// 行データスーパークラスの処理

		output.writeObject(assetId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(comStaffCode);
		output.writeObject(comStaffName);
		output.writeObject(comCompanyCode);
		output.writeObject(comCompanyName);
		output.writeObject(comSectionCode);
		output.writeObject(comSectionName);
		output.writeObject(comStartDate);
	}

	/**
	 * assetIdを取得します。
	 * @return assetId
	 */
	public String getAssetId() {
		return assetId;
	}

	/**
	 * assetId
	 * @param assetIdを設定します。
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
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
	 * comStaffCodeを取得します。
	 * @return comStaffCode
	 */
	public String getComStaffCode() {
		return comStaffCode;
	}

	/**
	 * comStaffCode
	 * @param comStaffCodeを設定します。
	 */
	public void setComStaffCode(String comStaffCode) {
		this.comStaffCode = comStaffCode;
	}

	/**
	 * comStaffNameを取得します。
	 * @return comStaffName
	 */
	public String getComStaffName() {
		return comStaffName;
	}

	/**
	 * comStaffName
	 * @param comStaffNameを設定します。
	 */
	public void setComStaffName(String comStaffName) {
		this.comStaffName = comStaffName;
	}

	/**
	 * comCompanyCodeを取得します。
	 * @return comCompanyCode
	 */
	public String getComCompanyCode() {
		return comCompanyCode;
	}

	/**
	 * comCompanyCode
	 * @param comCompanyCodeを設定します。
	 */
	public void setComCompanyCode(String comCompanyCode) {
		this.comCompanyCode = comCompanyCode;
	}

	/**
	 * comCompanyNameを取得します。
	 * @return comCompanyName
	 */
	public String getComCompanyName() {
		return comCompanyName;
	}

	/**
	 * comCompanyName
	 * @param comCompanyNameを設定します。
	 */
	public void setComCompanyName(String comCompanyName) {
		this.comCompanyName = comCompanyName;
	}

	/**
	 * comSectionCodeを取得します。
	 * @return comSectionCode
	 */
	public String getComSectionCode() {
		return comSectionCode;
	}

	/**
	 * comSectionCode
	 * @param comSectionCodeを設定します。
	 */
	public void setComSectionCode(String comSectionCode) {
		this.comSectionCode = comSectionCode;
	}

	/**
	 * comSectionNameを取得します。
	 * @return comSectionName
	 */
	public String getComSectionName() {
		return comSectionName;
	}

	/**
	 * comSectionName
	 * @param comSectionNameを設定します。
	 */
	public void setComSectionName(String comSectionName) {
		this.comSectionName = comSectionName;
	}

	/**
	 * comStartDateを取得します。
	 * @return comStartDate
	 */
	public Date getComStartDate() {
		return comStartDate;
	}

	/**
	 * comStartDate
	 * @param comStartDateを設定します。
	 */
	public void setComStartDate(Date comStartDate) {
		this.comStartDate = comStartDate;
	}

}
