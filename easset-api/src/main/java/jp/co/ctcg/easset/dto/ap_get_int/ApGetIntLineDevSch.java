/*===================================================================
 * ファイル名 : ApGetIntLineDevSch.java
 * 概要説明   : 取得申請(無形)_開発スケジュール明細
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
public class ApGetIntLineDevSch extends LineData {
	private static final long serialVersionUID = 1L;

	private String applicationId;		// 申請書番号
	private String applicationVersion;	// 申請書バージョン
	private Integer lineSeq;			// 行シーケンス
	private Date createDate;			// 作成日
	private String createStaffCode;		// 作成者社員番号
	private Date updateDate;			// 更新日
	private String updateStaffCode;		// 更新者社員番号
	private Date devPeriodFrom;			// 実施期日FROM
	private Date devPeriodTo;			// 実施期日TO
	private Double totalPeopleCount;	// 総要員数
	private Double devProperCount;		// 社員数
	private Double devEntrustCount;		// 業務委託
	private Long devAmount;				// 開発経費
	private String devSchCode;			// 開発スケジュールコード
	private String devSchName;			// 開発スケジュール名
	private String lineEditFlag;		// 行編集フラグ

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
		applicationVersion = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		devPeriodFrom = (Date)input.readObject();
		devPeriodTo = (Date)input.readObject();
		totalPeopleCount = Function.getExternalDouble(input.readObject());
		devProperCount = Function.getExternalDouble(input.readObject());
		devEntrustCount = Function.getExternalDouble(input.readObject());
		devAmount = Function.getExternalLong(input.readObject());
		devSchCode = (String)input.readObject();
		devSchName = (String)input.readObject();
		lineEditFlag = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
		output.writeObject(applicationVersion);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(devPeriodFrom);
		output.writeObject(devPeriodTo);
		output.writeObject(totalPeopleCount);
		output.writeObject(devProperCount);
		output.writeObject(devEntrustCount);
		output.writeObject(devAmount);
		output.writeObject(devSchCode);
		output.writeObject(devSchName);
		output.writeObject(lineEditFlag);
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

	public Date getDevPeriodFrom() {
		return devPeriodFrom;
	}

	public void setDevPeriodFrom(Date devPeriodFrom) {
		this.devPeriodFrom = devPeriodFrom;
	}

	public Date getDevPeriodTo() {
		return devPeriodTo;
	}

	public void setDevPeriodTo(Date devPeriodTo) {
		this.devPeriodTo = devPeriodTo;
	}

	public Double getDevProperCount() {
		return devProperCount;
	}

	public void setDevProperCount(Double devProperCount) {
		this.devProperCount = devProperCount;
	}

	public Double getDevEntrustCount() {
		return devEntrustCount;
	}

	public void setDevEntrustCount(Double devEntrustCount) {
		this.devEntrustCount = devEntrustCount;
	}

	public Long getDevAmount() {
		return devAmount;
	}

	public void setDevAmount(Long devAmount) {
		this.devAmount = devAmount;
	}

	public String getDevSchCode() {
		return devSchCode;
	}

	public void setDevSchCode(String devSchCode) {
		this.devSchCode = devSchCode;
	}

	public String getDevSchName() {
		return devSchName;
	}

	public void setDevSchName(String devSchName) {
		this.devSchName = devSchName;
	}

	public String getLineEditFlag() {
		return lineEditFlag;
	}

	public void setLineEditFlag(String lineEditFlag) {
		this.lineEditFlag = lineEditFlag;
	}

	public Double getTotalPeopleCount() {
		return totalPeopleCount;
	}

	public void setTotalPeopleCount(Double totalPeopleCount) {
		this.totalPeopleCount = totalPeopleCount;
	}

}
