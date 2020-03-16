/*===================================================================
 * ファイル名 : AssetLineOs.java
 * 概要説明   : 情報機器等登録申請_OS明細
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
public class AssetLineOs extends LineData {

	private static final long serialVersionUID = 1L;

	private Long assetLineOsId;			// 情報機器等_OS明細ID NEA_ASSET_LINE_OS_Sシーケンス
	private Date createDate;			// 作成日
	private String createStaffCode;		// 作成者社員番号
	private Date updateDate;			// 更新日
	private String updateStaffCode;		// 更新者社員番号
	private String assetId;				// 登録申請番号
	private Integer lineSeq;			// 行シーケンス
	private String osName;				// OS名 情報機器等と同項目
	private String osDescription;		// OSコメント 情報機器等と同項目

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);		// 行データスーパークラスの処理

		assetLineOsId = Function.getExternalLong(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		assetId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		osName = (String)input.readObject();
		osDescription = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);		// 行データスーパークラスの処理

		output.writeObject(assetLineOsId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(assetId);
		output.writeObject(lineSeq);
		output.writeObject(osName);
		output.writeObject(osDescription);
	}

	/**
	 * assetLineOsIdを取得します。
	 * @return assetLineOsId
	 */
	public Long getAssetLineOsId() {
		return assetLineOsId;
	}

	/**
	 * assetLineOsId
	 * @param assetLineOsIdを設定します。
	 */
	public void setAssetLineOsId(Long assetLineOsId) {
		this.assetLineOsId = assetLineOsId;
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
	 * osNameを取得します。
	 * @return osName
	 */
	public String getOsName() {
		return osName;
	}

	/**
	 * osName
	 * @param osNameを設定します。
	 */
	public void setOsName(String osName) {
		this.osName = osName;
	}

	/**
	 * osDescriptionを取得します。
	 * @return osDescription
	 */
	public String getOsDescription() {
		return osDescription;
	}

	/**
	 * osDescription
	 * @param osDescriptionを設定します。
	 */
	public void setOsDescription(String osDescription) {
		this.osDescription = osDescription;
	}

}
