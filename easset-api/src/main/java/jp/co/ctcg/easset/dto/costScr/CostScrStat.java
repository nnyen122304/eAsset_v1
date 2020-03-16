/*===================================================================
 * ファイル名 : CostScrStat.java
 * 概要説明   : 経費負担部課精査状況データクラス
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

import lombok.ToString;

@ToString
public class CostScrStat implements Externalizable {

	private String period; // 会計年月
	private String companyCode; // 会社コード
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String createStatus; // データ作成ステータス 1:実行中,2:正常終了,3:異常終了
	private Date createStartDate; // データ作成処理開始日
	private Date createEndDate; // データ作成処理終了日
	private String createExecStaffCode; // データ作成実行者社員番号
	private String scrSectionUpdateStaffCode; // 精査担当部署設定社員番号
	private Date scrSectionUpdateDate; // 精査担当部署設定日
	private Date openDate; // 各部メニューOPEN処理実行日
	private String openStaffCode; // 各部メニューOPEN処理実行者社員番号
	private Date closeDate; // 各部メニューCLOSE処理実行日
	private String closeStaffCode; // 各部メニューCLOSE処理実行者社員番号
	private String sendStaffCode; // メール送信実行者社員番号
	private String sendStatus; // メール送信ステータス
	private Date sendStartDate; // メール送信開始日
	private Date sendEndDate; // メール送信終了日
	private Date lastSuccessCreateStartDate; // 前回成功時：データ作成処理開始日
	private Date lastSuccessCreateEndDate; // 前回成功時：データ作成処理終了日
	private String lastSuccessExecStaffCode; // 前回成功時：データ作成実行者社員番号

	private String createStatusName; // データ作成ステータス名
	private String sendStatusName; // メール送信ステータス名

	private String createExecStaffName; // 作成者名
	private String scrSectionUpdateStaffName; // 精査担当部署設定者社員名
	private String openStaffName; // 各部メニューOPEN処理実行者名
	private String closeStaffName; // 各部メニューCLOSE処理実行者名
	private String sendStaffName; // メール送信実行者社員名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		period = (String)input.readObject();
		companyCode = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		createStatus = (String)input.readObject();
		createStartDate = (Date)input.readObject();
		createEndDate = (Date)input.readObject();
		createExecStaffCode = (String)input.readObject();
		scrSectionUpdateStaffCode = (String)input.readObject();
		scrSectionUpdateDate = (Date)input.readObject();
		openDate = (Date)input.readObject();
		openStaffCode = (String)input.readObject();
		closeDate = (Date)input.readObject();
		closeStaffCode = (String)input.readObject();
		sendStaffCode = (String)input.readObject();
		sendStatus = (String)input.readObject();
		sendStartDate = (Date)input.readObject();
		sendEndDate = (Date)input.readObject();
		lastSuccessCreateStartDate = (Date)input.readObject();
		lastSuccessCreateEndDate = (Date)input.readObject();
		lastSuccessExecStaffCode = (String)input.readObject();

		createStatusName = (String)input.readObject();
		sendStatusName = (String)input.readObject();

		createExecStaffName = (String)input.readObject();
		scrSectionUpdateStaffName = (String)input.readObject();
		openStaffName = (String)input.readObject();
		closeStaffName = (String)input.readObject();
		sendStaffName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(period);
		output.writeObject(companyCode);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(createStatus);
		output.writeObject(createStartDate);
		output.writeObject(createEndDate);
		output.writeObject(createExecStaffCode);
		output.writeObject(scrSectionUpdateStaffCode);
		output.writeObject(scrSectionUpdateDate);
		output.writeObject(openDate);
		output.writeObject(openStaffCode);
		output.writeObject(closeDate);
		output.writeObject(closeStaffCode);
		output.writeObject(sendStaffCode);
		output.writeObject(sendStatus);
		output.writeObject(sendStartDate);
		output.writeObject(sendEndDate);
		output.writeObject(lastSuccessCreateStartDate);
		output.writeObject(lastSuccessCreateEndDate);
		output.writeObject(lastSuccessExecStaffCode);

		output.writeObject(createStatusName);
		output.writeObject(sendStatusName);

		output.writeObject(createExecStaffName);
		output.writeObject(scrSectionUpdateStaffName);
		output.writeObject(openStaffName);
		output.writeObject(closeStaffName);
		output.writeObject(sendStaffName);

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

	public String getCreateStatus() {
		return createStatus;
	}

	public void setCreateStatus(String createStatus) {
		this.createStatus = createStatus;
	}

	public Date getCreateStartDate() {
		return createStartDate;
	}

	public void setCreateStartDate(Date createStartDate) {
		this.createStartDate = createStartDate;
	}

	public Date getCreateEndDate() {
		return createEndDate;
	}

	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}

	public String getCreateExecStaffCode() {
		return createExecStaffCode;
	}

	public void setCreateExecStaffCode(String createExecStaffCode) {
		this.createExecStaffCode = createExecStaffCode;
	}

	public Date getOpenDate() {
		return openDate;
	}

	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}

	public String getOpenStaffCode() {
		return openStaffCode;
	}

	public void setOpenStaffCode(String openStaffCode) {
		this.openStaffCode = openStaffCode;
	}

	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}

	public String getCloseStaffCode() {
		return closeStaffCode;
	}

	public void setCloseStaffCode(String closeStaffCode) {
		this.closeStaffCode = closeStaffCode;
	}

	public Date getLastSuccessCreateStartDate() {
		return lastSuccessCreateStartDate;
	}

	public void setLastSuccessCreateStartDate(Date lastSuccessCreateStartDate) {
		this.lastSuccessCreateStartDate = lastSuccessCreateStartDate;
	}

	public Date getLastSuccessCreateEndDate() {
		return lastSuccessCreateEndDate;
	}

	public void setLastSuccessCreateEndDate(Date lastSuccessCreateEndDate) {
		this.lastSuccessCreateEndDate = lastSuccessCreateEndDate;
	}

	public String getLastSuccessExecStaffCode() {
		return lastSuccessExecStaffCode;
	}

	public void setLastSuccessExecStaffCode(String lastSuccessExecStaffCode) {
		this.lastSuccessExecStaffCode = lastSuccessExecStaffCode;
	}

	public String getCreateStatusName() {
		return createStatusName;
	}

	public void setCreateStatusName(String createStatusName) {
		this.createStatusName = createStatusName;
	}

	public String getCreateExecStaffName() {
		return createExecStaffName;
	}

	public void setCreateExecStaffName(String createExecStaffName) {
		this.createExecStaffName = createExecStaffName;
	}

	public String getOpenStaffName() {
		return openStaffName;
	}

	public void setOpenStaffName(String openStaffName) {
		this.openStaffName = openStaffName;
	}

	public String getCloseStaffName() {
		return closeStaffName;
	}

	public void setCloseStaffName(String closeStaffName) {
		this.closeStaffName = closeStaffName;
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

	public String getSendStaffCode() {
		return sendStaffCode;
	}

	public void setSendStaffCode(String sendStaffCode) {
		this.sendStaffCode = sendStaffCode;
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus;
	}

	public Date getSendStartDate() {
		return sendStartDate;
	}

	public void setSendStartDate(Date sendStartDate) {
		this.sendStartDate = sendStartDate;
	}

	public Date getSendEndDate() {
		return sendEndDate;
	}

	public void setSendEndDate(Date sendEndDate) {
		this.sendEndDate = sendEndDate;
	}

	public String getSendStatusName() {
		return sendStatusName;
	}

	public void setSendStatusName(String sendStatusName) {
		this.sendStatusName = sendStatusName;
	}

	public String getScrSectionUpdateStaffName() {
		return scrSectionUpdateStaffName;
	}

	public void setScrSectionUpdateStaffName(String scrSectionUpdateStaffName) {
		this.scrSectionUpdateStaffName = scrSectionUpdateStaffName;
	}

	public String getSendStaffName() {
		return sendStaffName;
	}

	public void setSendStaffName(String sendStaffName) {
		this.sendStaffName = sendStaffName;
	}

}
