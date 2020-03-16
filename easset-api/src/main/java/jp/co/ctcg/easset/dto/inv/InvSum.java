/*===================================================================
 * ファイル名 : InvSum.java
 * 概要説明   : 更新履歴データクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-26 1.0  リヨン 申     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.inv;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class InvSum implements Externalizable {
	private static final long serialVersionUID = 1L;
	private String period; // 会計年月
	private String companyCode; // 会社コード
	private String holSectionCode; // 保有部署コード
	private Integer holSectionYear; // 保有部署年度
	private String invAssetType; // 資産区分 1:有形固定資産,2:資産除去費用,3:無形固定資産(本勘定),4:無形固定資産(仮勘定),5:リース資産,6:レンタル資産,7:備品等
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String apStatus; // 申請ステータス
	private Date apDate; // 申請日
	private String apStaffCode; // 申請者社員番号
	private Date approveDate; // 承認日
	private String approveStaffCode; // 承認者社員番号
	private Long eappId; // e申請書類ID
	private Date reminderDate; // 督促メール送信日付
	private Long invCountUndecided; // 棚卸状況：未
	private Long invCountOwn; // 棚卸状況：済有
	private Long invCountNotOwn; // 棚卸状況：済無
	private Long invCountTotal; // 棚卸状況：計
	private Long assetCount; // 資産計数
	private String invStaffCode; // 棚卸対象者

	private String holSectionName;
	private String apStaffName;
	private String approveStaffName;
	private String invAssetTypeName;
	private String apStatusName;


	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		period = (String)input.readObject();
		companyCode = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		invAssetType = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		apStatus = (String)input.readObject();
		apDate = (Date)input.readObject();
		apStaffCode = (String)input.readObject();
		approveDate = (Date)input.readObject();
		approveStaffCode = (String)input.readObject();
		eappId = Function.getExternalLong(input.readObject());
		reminderDate = (Date)input.readObject();
		invCountUndecided = Function.getExternalLong(input.readObject());
		invCountOwn = Function.getExternalLong(input.readObject());
		invCountNotOwn = Function.getExternalLong(input.readObject());
		invCountTotal = Function.getExternalLong(input.readObject());
		assetCount = Function.getExternalLong(input.readObject());
		invStaffCode = (String)input.readObject();

		holSectionName = (String)input.readObject();
		apStaffName = (String)input.readObject();
		approveStaffName = (String)input.readObject();
		invAssetTypeName = (String)input.readObject();
		apStatusName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(period);
		output.writeObject(companyCode);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(invAssetType);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(apStatus);
		output.writeObject(apDate);
		output.writeObject(apStaffCode);
		output.writeObject(approveDate);
		output.writeObject(approveStaffCode);
		output.writeObject(eappId);
		output.writeObject(reminderDate);
		output.writeObject(invCountUndecided);
		output.writeObject(invCountOwn);
		output.writeObject(invCountNotOwn);
		output.writeObject(invCountTotal);
		output.writeObject(assetCount);
		output.writeObject(invStaffCode);

		output.writeObject(holSectionName);
		output.writeObject(apStaffName);
		output.writeObject(approveStaffName);
		output.writeObject(invAssetTypeName);
		output.writeObject(apStatusName);

	}

	/**
	 * periodを取得します。
	 * @return period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * period
	 * @param periodを設定します。
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * companyCodeを取得します。
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode
	 * @param companyCodeを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * holSectionCodeを取得します。
	 * @return holSectionCode
	 */
	public String getHolSectionCode() {
		return holSectionCode;
	}

	/**
	 * holSectionCode
	 * @param holSectionCodeを設定します。
	 */
	public void setHolSectionCode(String holSectionCode) {
		this.holSectionCode = holSectionCode;
	}

	/**
	 * holSectionYearを取得します。
	 * @return holSectionYear
	 */
	public Integer getHolSectionYear() {
		return holSectionYear;
	}

	/**
	 * holSectionYear
	 * @param holSectionYearを設定します。
	 */
	public void setHolSectionYear(Integer holSectionYear) {
		this.holSectionYear = holSectionYear;
	}

	/**
	 * invAssetTypeを取得します。
	 * @return invAssetType
	 */
	public String getInvAssetType() {
		return invAssetType;
	}

	/**
	 * invAssetType
	 * @param invAssetTypeを設定します。
	 */
	public void setInvAssetType(String invAssetType) {
		this.invAssetType = invAssetType;
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
	 * apStatusを取得します。
	 * @return apStatus
	 */
	public String getApStatus() {
		return apStatus;
	}

	/**
	 * apStatus
	 * @param apStatusを設定します。
	 */
	public void setApStatus(String apStatus) {
		this.apStatus = apStatus;
	}

	/**
	 * apDateを取得します。
	 * @return apDate
	 */
	public Date getApDate() {
		return apDate;
	}

	/**
	 * apDate
	 * @param apDateを設定します。
	 */
	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}

	/**
	 * apStaffCodeを取得します。
	 * @return apStaffCode
	 */
	public String getApStaffCode() {
		return apStaffCode;
	}

	/**
	 * apStaffCode
	 * @param apStaffCodeを設定します。
	 */
	public void setApStaffCode(String apStaffCode) {
		this.apStaffCode = apStaffCode;
	}

	/**
	 * approveDateを取得します。
	 * @return approveDate
	 */
	public Date getApproveDate() {
		return approveDate;
	}

	/**
	 * approveDate
	 * @param approveDateを設定します。
	 */
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
	}

	/**
	 * approveStaffCodeを取得します。
	 * @return approveStaffCode
	 */
	public String getApproveStaffCode() {
		return approveStaffCode;
	}

	/**
	 * approveStaffCode
	 * @param approveStaffCodeを設定します。
	 */
	public void setApproveStaffCode(String approveStaffCode) {
		this.approveStaffCode = approveStaffCode;
	}

	/**
	 * eappIdを取得します。
	 * @return eappId
	 */
	public Long getEappId() {
		return eappId;
	}

	/**
	 * eappId
	 * @param eappIdを設定します。
	 */
	public void setEappId(Long eappId) {
		this.eappId = eappId;
	}

	/**
	 * reminderDateを取得します。
	 * @return reminderDate
	 */
	public Date getReminderDate() {
		return reminderDate;
	}

	/**
	 * reminderDate
	 * @param reminderDateを設定します。
	 */
	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

	/**
	 * invCountUndecidedを取得します。
	 * @return invCountUndecided
	 */
	public Long getInvCountUndecided() {
		return invCountUndecided;
	}

	/**
	 * invCountUndecided
	 * @param invCountUndecidedを設定します。
	 */
	public void setInvCountUndecided(Long invCountUndecided) {
		this.invCountUndecided = invCountUndecided;
	}

	/**
	 * invCountOwnを取得します。
	 * @return invCountOwn
	 */
	public Long getInvCountOwn() {
		return invCountOwn;
	}

	/**
	 * invCountOwn
	 * @param invCountOwnを設定します。
	 */
	public void setInvCountOwn(Long invCountOwn) {
		this.invCountOwn = invCountOwn;
	}

	/**
	 * invCountNotOwnを取得します。
	 * @return invCountNotOwn
	 */
	public Long getInvCountNotOwn() {
		return invCountNotOwn;
	}

	/**
	 * invCountNotOwn
	 * @param invCountNotOwnを設定します。
	 */
	public void setInvCountNotOwn(Long invCountNotOwn) {
		this.invCountNotOwn = invCountNotOwn;
	}

	/**
	 * invCountTotalを取得します。
	 * @return invCountTotal
	 */
	public Long getInvCountTotal() {
		return invCountTotal;
	}

	/**
	 * invCountTotal
	 * @param invCountTotalを設定します。
	 */
	public void setInvCountTotal(Long invCountTotal) {
		this.invCountTotal = invCountTotal;
	}

	/**
	 * assetCountを取得します。
	 * @return assetCount
	 */
	public Long getAssetCount() {
		return assetCount;
	}

	/**
	 * assetCount
	 * @param assetCountを設定します。
	 */
	public void setAssetCount(Long assetCount) {
		this.assetCount = assetCount;
	}

	/**
	 * holSectionNameを取得します。
	 * @return holSectionName
	 */
	public String getHolSectionName() {
		return holSectionName;
	}

	/**
	 * holSectionName
	 * @param holSectionNameを設定します。
	 */
	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}

	/**
	 * apStaffNameを取得します。
	 * @return apStaffName
	 */
	public String getApStaffName() {
		return apStaffName;
	}

	/**
	 * apStaffName
	 * @param apStaffNameを設定します。
	 */
	public void setApStaffName(String apStaffName) {
		this.apStaffName = apStaffName;
	}

	/**
	 * approveStaffNameを取得します。
	 * @return approveStaffName
	 */
	public String getApproveStaffName() {
		return approveStaffName;
	}

	/**
	 * approveStaffName
	 * @param approveStaffNameを設定します。
	 */
	public void setApproveStaffName(String approveStaffName) {
		this.approveStaffName = approveStaffName;
	}

	/**
	 * invAssetTypeNameを取得します。
	 * @return invAssetTypeName
	 */
	public String getInvAssetTypeName() {
		return invAssetTypeName;
	}

	/**
	 * invAssetTypeName
	 * @param invAssetTypeNameを設定します。
	 */
	public void setInvAssetTypeName(String invAssetTypeName) {
		this.invAssetTypeName = invAssetTypeName;
	}

	/**
	 * apStatusNameを取得します。
	 * @return apStatusName
	 */
	public String getApStatusName() {
		return apStatusName;
	}

	/**
	 * apStatusName
	 * @param apStatusNameを設定します。
	 */
	public void setApStatusName(String apStatusName) {
		this.apStatusName = apStatusName;
	}

	/**
	 * invStaffCodeを取得します。
	 * @return invStaffCode
	 */
	public String getInvStaffCode() {
		return invStaffCode;
	}

	/**
	 * invStaffCode
	 * @param invStaffCodeを設定します。
	 */
	public void setInvStaffCode(String invStaffCode) {
		this.invStaffCode = invStaffCode;
	}

}
