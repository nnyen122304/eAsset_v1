/*===================================================================
 * ファイル名 : CostScr.java
 * 概要説明   : 経費負担部課精査集約データクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2015-01-28 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.costScr;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class CostScr implements Externalizable {
	private String period; // 会計年月
	private String companyCode; // 会社コード
	private String costSectionCode; // 当年度：経費負担部課コード
	private String costSectionCodeOld; // 前年度：経費負担部課コード
	private String scrType; // 精査種別 1:有形固定資産,2:無形固定資産,3:リース,4:レンタル,5:シンクライアント(情シス配備),6:持出専用機器(情シス配備)
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String scrSectionCode; // 精査担当部署コード
	private Integer scrSectionYear; // 精査担当部署年度
	private String compFlag; // 完了フラグ 0:未完了,1:完了
	private Date compExecDate; // 完了処理実行日
	private String compExecStaffCode; // 完了処理実行者社員番号
	private Date scrDate; // 精査実施日
	private String scrStaffCode; // 精査実施者社員番号
	private String scrSectionUpdateStaffCode; // 精査担当部署設定更新者社員番号
	private Date scrSectionUpdateDate; // 精査担当部署設定更新日
	private Date reminderDate; // 督促メール送信日付

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		period = (String)input.readObject();
		companyCode = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		costSectionCodeOld = (String)input.readObject();
		scrType = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		scrSectionCode = (String)input.readObject();
		scrSectionYear = Function.getExternalInteger(input.readObject());
		compFlag = (String)input.readObject();
		compExecDate = (Date)input.readObject();
		compExecStaffCode = (String)input.readObject();
		scrDate = (Date)input.readObject();
		scrStaffCode = (String)input.readObject();
		scrSectionUpdateStaffCode = (String)input.readObject();
		scrSectionUpdateDate = (Date)input.readObject();
		reminderDate = (Date)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(period);
		output.writeObject(companyCode);
		output.writeObject(costSectionCode);
		output.writeObject(costSectionCodeOld);
		output.writeObject(scrType);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(scrSectionCode);
		output.writeObject(scrSectionYear);
		output.writeObject(compFlag);
		output.writeObject(compExecDate);
		output.writeObject(compExecStaffCode);
		output.writeObject(scrDate);
		output.writeObject(scrStaffCode);
		output.writeObject(scrSectionUpdateStaffCode);
		output.writeObject(scrSectionUpdateDate);
		output.writeObject("reminderDate");
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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

	public String getCostSectionCode() {
		return costSectionCode;
	}

	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
	}

	public String getCostSectionCodeOld() {
		return costSectionCodeOld;
	}

	public void setCostSectionCodeOld(String costSectionCodeOld) {
		this.costSectionCodeOld = costSectionCodeOld;
	}

	public String getScrType() {
		return scrType;
	}

	public void setScrType(String scrType) {
		this.scrType = scrType;
	}

	public String getCompFlag() {
		return compFlag;
	}

	public void setCompFlag(String compFlag) {
		this.compFlag = compFlag;
	}

	public Date getCompExecDate() {
		return compExecDate;
	}

	public void setCompExecDate(Date compExecDate) {
		this.compExecDate = compExecDate;
	}

	public String getCompExecStaffCode() {
		return compExecStaffCode;
	}

	public void setCompExecStaffCode(String compExecStaffCode) {
		this.compExecStaffCode = compExecStaffCode;
	}

	public Date getScrDate() {
		return scrDate;
	}

	public void setScrDate(Date scrDate) {
		this.scrDate = scrDate;
	}

	public String getScrStaffCode() {
		return scrStaffCode;
	}

	public void setScrStaffCode(String scrStaffCode) {
		this.scrStaffCode = scrStaffCode;
	}

	public String getScrSectionCode() {
		return scrSectionCode;
	}

	public void setScrSectionCode(String scrSectionCode) {
		this.scrSectionCode = scrSectionCode;
	}

	public Integer getScrSectionYear() {
		return scrSectionYear;
	}

	public void setScrSectionYear(Integer scrSectionYear) {
		this.scrSectionYear = scrSectionYear;
	}

	public String getScrSectionUpdateStaffCode() {
		return scrSectionUpdateStaffCode;
	}

	public void setScrSectionUpdateStaffCode(String scrSectionUpdateStaffCode) {
		this.scrSectionUpdateStaffCode = scrSectionUpdateStaffCode;
	}

	public Date getScrSectionUpdateDate() {
		return scrSectionUpdateDate;
	}

	public void setScrSectionUpdateDate(Date scrSectionUpdateDate) {
		this.scrSectionUpdateDate = scrSectionUpdateDate;
	}

	public Date getReminderDate() {
		return reminderDate;
	}

	public void setReminderDate(Date reminderDate) {
		this.reminderDate = reminderDate;
	}

}
