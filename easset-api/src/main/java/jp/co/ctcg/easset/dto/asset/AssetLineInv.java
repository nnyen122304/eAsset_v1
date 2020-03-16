/*===================================================================
 * ファイル名 : AssetLineInv.java
 * 概要説明   : 情報機器等_棚卸明細
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
public class AssetLineInv extends LineData {

	private static final long serialVersionUID = 1L;

	private String assetId;				// 情報機器管理番号
	private Integer lineSeq;			// 行シーケンス
	private Date createDate;			// 作成日
	private String createStaffCode;		// 作成者社員番号
	private Date updateDate;			// 更新日
	private String updateStaffCode;		// 更新者社員番号
	private Date invDate;				// 棚卸実施日
	private String invOfficeName;		// オフィス 棚卸実施時点のオフィス
	private String invComment;			// コメント

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);		// 行データスーパークラスの処理

		assetId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		invDate = (Date)input.readObject();
		invOfficeName = (String)input.readObject();
		invComment = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);		// 行データスーパークラスの処理

		output.writeObject(assetId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(invDate);
		output.writeObject(invOfficeName);
		output.writeObject(invComment);
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
	 * invDateを取得します。
	 * @return invDate
	 */
	public Date getInvDate() {
		return invDate;
	}

	/**
	 * invDate
	 * @param invDateを設定します。
	 */
	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}

	/**
	 * invOfficeNameを取得します。
	 * @return invOfficeName
	 */
	public String getInvOfficeName() {
		return invOfficeName;
	}

	/**
	 * invOfficeName
	 * @param invOfficeNameを設定します。
	 */
	public void setInvOfficeName(String invOfficeName) {
		this.invOfficeName = invOfficeName;
	}

	/**
	 * invCommentを取得します。
	 * @return invComment
	 */
	public String getInvComment() {
		return invComment;
	}

	/**
	 * invComment
	 * @param invCommentを設定します。
	 */
	public void setInvComment(String invComment) {
		this.invComment = invComment;
	}

}
