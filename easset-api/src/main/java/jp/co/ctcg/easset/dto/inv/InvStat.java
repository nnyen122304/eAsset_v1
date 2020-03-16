/*===================================================================
 * ファイル名 : InvStat.java
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

import lombok.ToString;

@ToString
public class InvStat implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String period; // 会計年月
	private String companyCode; // 会社コード
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号 データ作成実行者
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String createStatus; // データ作成ステータス 1:実行中,2:異常終了,3:正常終了
	private Date createStartDate; // データ作成処理開始日
	private Date createEndDate; // データ作成処理終了日
	private String createExecStaffCode; // データ作成実行者社員番号
	private String fldTanPublicType; // 公開タイプ-有形固定資産 1:公開しない,2:公開する,3:未取込
	private String fldTanSendStatus; // 依頼メール送信ステータス-有形固定資産 1:未送信,2:送信中,3:送信済
	private Date fldTanSendStartDate; // 依頼メール送信開始日-有形固定資産
	private Date fldTanSendEndDate; // 依頼メール送信終了日-有形固定資産
	private String fldRoPublicType; // 公開タイプ-除去費用 1:公開しない,2:公開する,3:未取込
	private String fldRoSendStatus; // 依頼メール送信ステータス-除去費用 1:未送信,2:送信中,3:送信済
	private Date fldRoSendStartDate; // 依頼メール送信開始日-除去費用
	private Date fldRoSendEndDate; // 依頼メール送信終了日-除去費用
	private String fldIntPublicType; // 公開タイプ-無形固定資産 1:公開しない,2:公開する,3:未取込
	private String fldIntSendStatus; // 依頼メール送信ステータス-無形固定資産 1:未送信,2:送信中,3:送信済
	private Date fldIntSendStartDate; // 依頼メール送信開始日-無形固定資産
	private Date fldIntSendEndDate; // 依頼メール送信終了日-無形固定資産
	private String lePublicType; // 公開タイプ-リース 1:公開しない,2:公開する,3:未取込
	private String leSendStatus; // 依頼メール送信ステータス-リース 1:未送信,2:送信中,3:送信済
	private Date leSendStartDate; // 依頼メール送信開始日-リース
	private Date leSendEndDate; // 依頼メール送信終了日-リース
	private String rePublicType; // 公開タイプ-レンタル 1:公開しない,2:公開する,3:未取込
	private String reSendStatus; // 依頼メール送信ステータス-レンタル 1:未送信,2:送信中,3:送信済
	private Date reSendStartDate; // 依頼メール送信開始日-レンタル
	private Date reSendEndDate; // 依頼メール送信終了日-レンタル
	private String equipPublicType; // 公開タイプ-備品等 1:公開しない,2:公開する,3:未取込
	private String equipSendStatus; // 依頼メール送信ステータス-備品等 1:未送信,2:送信中,3:送信済
	private Date equipSendStartDate; // 依頼メール送信開始日-備品等
	private Date equipSendEndDate; // 依頼メール送信終了日-備品等
	private Date lastSuccessCreateStartDate; // 前回成功時：データ作成処理開始日
	private Date lastSuccessCreateEndDate; // 前回成功時：データ作成処理終了日
	private String lastSuccessExecStaffCode; // 前回成功時：データ作成実行者社員番号

	private String createStatusName; // データ作成ステータス名
	private String createExecStaffName; // データ作成実行者名

	private String fldTanPublicTypeName; // 公開タイプ名-有形固定資産
	private String fldRoPublicTypeName; // 公開タイプ名-除去費用
	private String fldIntPublicTypeName; // 公開タイプ名-無形固定資産
	private String lePublicTypeName; // 公開タイプ名-リース
	private String rePublicTypeName; // 公開タイプ名-レンタル
	private String equipPublicTypeName; // 公開タイプ名-備品等

	private String fldTanSendStatusName; // 依頼メール送信ステータス名-有形固定資産
	private String fldRoSendStatusName; // 依頼メール送信ステータス名-除去費用
	private String fldIntSendStatusName; // 依頼メール送信ステータス名-無形固定資産
	private String leSendStatusName; // 依頼メール送信ステータス名-リース
	private String reSendStatusName; // 依頼メール送信ステータス名-レンタル
	private String equipSendStatusName; // 依頼メール送信ステータス名-備品等

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
		fldTanPublicType = (String)input.readObject();
		fldTanSendStatus = (String)input.readObject();
		fldTanSendStartDate = (Date)input.readObject();
		fldTanSendEndDate = (Date)input.readObject();
		fldRoPublicType = (String)input.readObject();
		fldRoSendStatus = (String)input.readObject();
		fldRoSendStartDate = (Date)input.readObject();
		fldRoSendEndDate = (Date)input.readObject();
		fldIntPublicType = (String)input.readObject();
		fldIntSendStatus = (String)input.readObject();
		fldIntSendStartDate = (Date)input.readObject();
		fldIntSendEndDate = (Date)input.readObject();
		lePublicType = (String)input.readObject();
		leSendStatus = (String)input.readObject();
		leSendStartDate = (Date)input.readObject();
		leSendEndDate = (Date)input.readObject();
		rePublicType = (String)input.readObject();
		reSendStatus = (String)input.readObject();
		reSendStartDate = (Date)input.readObject();
		reSendEndDate = (Date)input.readObject();
		equipPublicType = (String)input.readObject();
		equipSendStatus = (String)input.readObject();
		equipSendStartDate = (Date)input.readObject();
		equipSendEndDate = (Date)input.readObject();
		lastSuccessCreateStartDate = (Date)input.readObject();
		lastSuccessCreateEndDate = (Date)input.readObject();
		lastSuccessExecStaffCode = (String)input.readObject();

		createStatusName = (String)input.readObject();
		createExecStaffName = (String)input.readObject();

		fldTanPublicTypeName = (String)input.readObject();
		fldRoPublicTypeName = (String)input.readObject();
		fldIntPublicTypeName = (String)input.readObject();
		lePublicTypeName = (String)input.readObject();
		rePublicTypeName = (String)input.readObject();
		equipPublicTypeName = (String)input.readObject();

		fldTanSendStatusName = (String)input.readObject();
		fldRoSendStatusName = (String)input.readObject();
		fldIntSendStatusName = (String)input.readObject();
		leSendStatusName = (String)input.readObject();
		reSendStatusName = (String)input.readObject();
		equipSendStatusName = (String)input.readObject();
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
		output.writeObject(fldTanPublicType);
		output.writeObject(fldTanSendStatus);
		output.writeObject(fldTanSendStartDate);
		output.writeObject(fldTanSendEndDate);
		output.writeObject(fldRoPublicType);
		output.writeObject(fldRoSendStatus);
		output.writeObject(fldRoSendStartDate);
		output.writeObject(fldRoSendEndDate);
		output.writeObject(fldIntPublicType);
		output.writeObject(fldIntSendStatus);
		output.writeObject(fldIntSendStartDate);
		output.writeObject(fldIntSendEndDate);
		output.writeObject(lePublicType);
		output.writeObject(leSendStatus);
		output.writeObject(leSendStartDate);
		output.writeObject(leSendEndDate);
		output.writeObject(rePublicType);
		output.writeObject(reSendStatus);
		output.writeObject(reSendStartDate);
		output.writeObject(reSendEndDate);
		output.writeObject(equipPublicType);
		output.writeObject(equipSendStatus);
		output.writeObject(equipSendStartDate);
		output.writeObject(equipSendEndDate);
		output.writeObject(lastSuccessCreateStartDate);
		output.writeObject(lastSuccessCreateEndDate);
		output.writeObject(lastSuccessExecStaffCode);

		output.writeObject(createStatusName);
		output.writeObject(createExecStaffName);

		output.writeObject(fldTanPublicTypeName);
		output.writeObject(fldRoPublicTypeName);
		output.writeObject(fldIntPublicTypeName);
		output.writeObject(lePublicTypeName);
		output.writeObject(rePublicTypeName);
		output.writeObject(equipPublicTypeName);

		output.writeObject(fldTanSendStatusName);
		output.writeObject(fldRoSendStatusName);
		output.writeObject(fldIntSendStatusName);
		output.writeObject(leSendStatusName);
		output.writeObject(reSendStatusName);
		output.writeObject(equipSendStatusName);
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
	 * createStatusを取得します。
	 * @return createStatus
	 */
	public String getCreateStatus() {
		return createStatus;
	}

	/**
	 * createStatus
	 * @param createStatusを設定します。
	 */
	public void setCreateStatus(String createStatus) {
		this.createStatus = createStatus;
	}

	/**
	 * createStartDateを取得します。
	 * @return createStartDate
	 */
	public Date getCreateStartDate() {
		return createStartDate;
	}

	/**
	 * createStartDate
	 * @param createStartDateを設定します。
	 */
	public void setCreateStartDate(Date createStartDate) {
		this.createStartDate = createStartDate;
	}

	/**
	 * createEndDateを取得します。
	 * @return createEndDate
	 */
	public Date getCreateEndDate() {
		return createEndDate;
	}

	/**
	 * createEndDate
	 * @param createEndDateを設定します。
	 */
	public void setCreateEndDate(Date createEndDate) {
		this.createEndDate = createEndDate;
	}

	/**
	 * createExecStaffCodeを取得します。
	 * @return createExecStaffCode
	 */
	public String getCreateExecStaffCode() {
		return createExecStaffCode;
	}

	/**
	 * createExecStaffCode
	 * @param createExecStaffCodeを設定します。
	 */
	public void setCreateExecStaffCode(String createExecStaffCode) {
		this.createExecStaffCode = createExecStaffCode;
	}

	/**
	 * fldTanPublicTypeを取得します。
	 * @return fldTanPublicType
	 */
	public String getFldTanPublicType() {
		return fldTanPublicType;
	}

	/**
	 * fldTanPublicType
	 * @param fldTanPublicTypeを設定します。
	 */
	public void setFldTanPublicType(String fldTanPublicType) {
		this.fldTanPublicType = fldTanPublicType;
	}

	/**
	 * fldTanSendStatusを取得します。
	 * @return fldTanSendStatus
	 */
	public String getFldTanSendStatus() {
		return fldTanSendStatus;
	}

	/**
	 * fldTanSendStatus
	 * @param fldTanSendStatusを設定します。
	 */
	public void setFldTanSendStatus(String fldTanSendStatus) {
		this.fldTanSendStatus = fldTanSendStatus;
	}

	/**
	 * fldTanSendStartDateを取得します。
	 * @return fldTanSendStartDate
	 */
	public Date getFldTanSendStartDate() {
		return fldTanSendStartDate;
	}

	/**
	 * fldTanSendStartDate
	 * @param fldTanSendStartDateを設定します。
	 */
	public void setFldTanSendStartDate(Date fldTanSendStartDate) {
		this.fldTanSendStartDate = fldTanSendStartDate;
	}

	/**
	 * fldTanSendEndDateを取得します。
	 * @return fldTanSendEndDate
	 */
	public Date getFldTanSendEndDate() {
		return fldTanSendEndDate;
	}

	/**
	 * fldTanSendEndDate
	 * @param fldTanSendEndDateを設定します。
	 */
	public void setFldTanSendEndDate(Date fldTanSendEndDate) {
		this.fldTanSendEndDate = fldTanSendEndDate;
	}

	/**
	 * fldRoPublicTypeを取得します。
	 * @return fldRoPublicType
	 */
	public String getFldRoPublicType() {
		return fldRoPublicType;
	}

	/**
	 * fldRoPublicType
	 * @param fldRoPublicTypeを設定します。
	 */
	public void setFldRoPublicType(String fldRoPublicType) {
		this.fldRoPublicType = fldRoPublicType;
	}

	/**
	 * fldRoSendStatusを取得します。
	 * @return fldRoSendStatus
	 */
	public String getFldRoSendStatus() {
		return fldRoSendStatus;
	}

	/**
	 * fldRoSendStatus
	 * @param fldRoSendStatusを設定します。
	 */
	public void setFldRoSendStatus(String fldRoSendStatus) {
		this.fldRoSendStatus = fldRoSendStatus;
	}

	/**
	 * fldRoSendStartDateを取得します。
	 * @return fldRoSendStartDate
	 */
	public Date getFldRoSendStartDate() {
		return fldRoSendStartDate;
	}

	/**
	 * fldRoSendStartDate
	 * @param fldRoSendStartDateを設定します。
	 */
	public void setFldRoSendStartDate(Date fldRoSendStartDate) {
		this.fldRoSendStartDate = fldRoSendStartDate;
	}

	/**
	 * fldRoSendEndDateを取得します。
	 * @return fldRoSendEndDate
	 */
	public Date getFldRoSendEndDate() {
		return fldRoSendEndDate;
	}

	/**
	 * fldRoSendEndDate
	 * @param fldRoSendEndDateを設定します。
	 */
	public void setFldRoSendEndDate(Date fldRoSendEndDate) {
		this.fldRoSendEndDate = fldRoSendEndDate;
	}

	/**
	 * fldIntPublicTypeを取得します。
	 * @return fldIntPublicType
	 */
	public String getFldIntPublicType() {
		return fldIntPublicType;
	}

	/**
	 * fldIntPublicType
	 * @param fldIntPublicTypeを設定します。
	 */
	public void setFldIntPublicType(String fldIntPublicType) {
		this.fldIntPublicType = fldIntPublicType;
	}

	/**
	 * fldIntSendStatusを取得します。
	 * @return fldIntSendStatus
	 */
	public String getFldIntSendStatus() {
		return fldIntSendStatus;
	}

	/**
	 * fldIntSendStatus
	 * @param fldIntSendStatusを設定します。
	 */
	public void setFldIntSendStatus(String fldIntSendStatus) {
		this.fldIntSendStatus = fldIntSendStatus;
	}

	/**
	 * fldIntSendStartDateを取得します。
	 * @return fldIntSendStartDate
	 */
	public Date getFldIntSendStartDate() {
		return fldIntSendStartDate;
	}

	/**
	 * fldIntSendStartDate
	 * @param fldIntSendStartDateを設定します。
	 */
	public void setFldIntSendStartDate(Date fldIntSendStartDate) {
		this.fldIntSendStartDate = fldIntSendStartDate;
	}

	/**
	 * fldIntSendEndDateを取得します。
	 * @return fldIntSendEndDate
	 */
	public Date getFldIntSendEndDate() {
		return fldIntSendEndDate;
	}

	/**
	 * fldIntSendEndDate
	 * @param fldIntSendEndDateを設定します。
	 */
	public void setFldIntSendEndDate(Date fldIntSendEndDate) {
		this.fldIntSendEndDate = fldIntSendEndDate;
	}

	/**
	 * lePublicTypeを取得します。
	 * @return lePublicType
	 */
	public String getLePublicType() {
		return lePublicType;
	}

	/**
	 * lePublicType
	 * @param lePublicTypeを設定します。
	 */
	public void setLePublicType(String lePublicType) {
		this.lePublicType = lePublicType;
	}

	/**
	 * leSendStatusを取得します。
	 * @return leSendStatus
	 */
	public String getLeSendStatus() {
		return leSendStatus;
	}

	/**
	 * leSendStatus
	 * @param leSendStatusを設定します。
	 */
	public void setLeSendStatus(String leSendStatus) {
		this.leSendStatus = leSendStatus;
	}

	/**
	 * leSendStartDateを取得します。
	 * @return leSendStartDate
	 */
	public Date getLeSendStartDate() {
		return leSendStartDate;
	}

	/**
	 * leSendStartDate
	 * @param leSendStartDateを設定します。
	 */
	public void setLeSendStartDate(Date leSendStartDate) {
		this.leSendStartDate = leSendStartDate;
	}

	/**
	 * leSendEndDateを取得します。
	 * @return leSendEndDate
	 */
	public Date getLeSendEndDate() {
		return leSendEndDate;
	}

	/**
	 * leSendEndDate
	 * @param leSendEndDateを設定します。
	 */
	public void setLeSendEndDate(Date leSendEndDate) {
		this.leSendEndDate = leSendEndDate;
	}

	/**
	 * rePublicTypeを取得します。
	 * @return rePublicType
	 */
	public String getRePublicType() {
		return rePublicType;
	}

	/**
	 * rePublicType
	 * @param rePublicTypeを設定します。
	 */
	public void setRePublicType(String rePublicType) {
		this.rePublicType = rePublicType;
	}

	/**
	 * reSendStatusを取得します。
	 * @return reSendStatus
	 */
	public String getReSendStatus() {
		return reSendStatus;
	}

	/**
	 * reSendStatus
	 * @param reSendStatusを設定します。
	 */
	public void setReSendStatus(String reSendStatus) {
		this.reSendStatus = reSendStatus;
	}

	/**
	 * reSendStartDateを取得します。
	 * @return reSendStartDate
	 */
	public Date getReSendStartDate() {
		return reSendStartDate;
	}

	/**
	 * reSendStartDate
	 * @param reSendStartDateを設定します。
	 */
	public void setReSendStartDate(Date reSendStartDate) {
		this.reSendStartDate = reSendStartDate;
	}

	/**
	 * reSendEndDateを取得します。
	 * @return reSendEndDate
	 */
	public Date getReSendEndDate() {
		return reSendEndDate;
	}

	/**
	 * reSendEndDate
	 * @param reSendEndDateを設定します。
	 */
	public void setReSendEndDate(Date reSendEndDate) {
		this.reSendEndDate = reSendEndDate;
	}

	/**
	 * equipPublicTypeを取得します。
	 * @return equipPublicType
	 */
	public String getEquipPublicType() {
		return equipPublicType;
	}

	/**
	 * equipPublicType
	 * @param equipPublicTypeを設定します。
	 */
	public void setEquipPublicType(String equipPublicType) {
		this.equipPublicType = equipPublicType;
	}

	/**
	 * equipSendStatusを取得します。
	 * @return equipSendStatus
	 */
	public String getEquipSendStatus() {
		return equipSendStatus;
	}

	/**
	 * equipSendStatus
	 * @param equipSendStatusを設定します。
	 */
	public void setEquipSendStatus(String equipSendStatus) {
		this.equipSendStatus = equipSendStatus;
	}

	/**
	 * equipSendStartDateを取得します。
	 * @return equipSendStartDate
	 */
	public Date getEquipSendStartDate() {
		return equipSendStartDate;
	}

	/**
	 * equipSendStartDate
	 * @param equipSendStartDateを設定します。
	 */
	public void setEquipSendStartDate(Date equipSendStartDate) {
		this.equipSendStartDate = equipSendStartDate;
	}

	/**
	 * equipSendEndDateを取得します。
	 * @return equipSendEndDate
	 */
	public Date getEquipSendEndDate() {
		return equipSendEndDate;
	}

	/**
	 * equipSendEndDate
	 * @param equipSendEndDateを設定します。
	 */
	public void setEquipSendEndDate(Date equipSendEndDate) {
		this.equipSendEndDate = equipSendEndDate;
	}

	/**
	 * lastSuccessCreateStartDateを取得します。
	 * @return lastSuccessCreateStartDate
	 */
	public Date getLastSuccessCreateStartDate() {
		return lastSuccessCreateStartDate;
	}

	/**
	 * lastSuccessCreateStartDate
	 * @param lastSuccessCreateStartDateを設定します。
	 */
	public void setLastSuccessCreateStartDate(Date lastSuccessCreateStartDate) {
		this.lastSuccessCreateStartDate = lastSuccessCreateStartDate;
	}

	/**
	 * lastSuccessCreateEndDateを取得します。
	 * @return lastSuccessCreateEndDate
	 */
	public Date getLastSuccessCreateEndDate() {
		return lastSuccessCreateEndDate;
	}

	/**
	 * lastSuccessCreateEndDate
	 * @param lastSuccessCreateEndDateを設定します。
	 */
	public void setLastSuccessCreateEndDate(Date lastSuccessCreateEndDate) {
		this.lastSuccessCreateEndDate = lastSuccessCreateEndDate;
	}

	/**
	 * lastSuccessExecStaffCodeを取得します。
	 * @return lastSuccessExecStaffCode
	 */
	public String getLastSuccessExecStaffCode() {
		return lastSuccessExecStaffCode;
	}

	/**
	 * lastSuccessExecStaffCode
	 * @param lastSuccessExecStaffCodeを設定します。
	 */
	public void setLastSuccessExecStaffCode(String lastSuccessExecStaffCode) {
		this.lastSuccessExecStaffCode = lastSuccessExecStaffCode;
	}

	/**
	 * @return the createExecStaffName
	 */
	public String getCreateExecStaffName() {
		return createExecStaffName;
	}

	/**
	 * @param createExecStaffName the createExecStaffName to set
	 */
	public void setCreateExecStaffName(String createExecStaffName) {
		this.createExecStaffName = createExecStaffName;
	}

	/**
	 * @return the fldTanPublicTypeName
	 */
	public String getFldTanPublicTypeName() {
		return fldTanPublicTypeName;
	}

	/**
	 * @param fldTanPublicTypeName the fldTanPublicTypeName to set
	 */
	public void setFldTanPublicTypeName(String fldTanPublicTypeName) {
		this.fldTanPublicTypeName = fldTanPublicTypeName;
	}

	/**
	 * @return the fldRoPublicTypeName
	 */
	public String getFldRoPublicTypeName() {
		return fldRoPublicTypeName;
	}

	/**
	 * @param fldRoPublicTypeName the fldRoPublicTypeName to set
	 */
	public void setFldRoPublicTypeName(String fldRoPublicTypeName) {
		this.fldRoPublicTypeName = fldRoPublicTypeName;
	}

	/**
	 * @return the fldIntPublicTypeName
	 */
	public String getFldIntPublicTypeName() {
		return fldIntPublicTypeName;
	}

	/**
	 * @param fldIntPublicTypeName the fldIntPublicTypeName to set
	 */
	public void setFldIntPublicTypeName(String fldIntPublicTypeName) {
		this.fldIntPublicTypeName = fldIntPublicTypeName;
	}

	/**
	 * @return the lePublicTypeName
	 */
	public String getLePublicTypeName() {
		return lePublicTypeName;
	}

	/**
	 * @param lePublicTypeName the lePublicTypeName to set
	 */
	public void setLePublicTypeName(String lePublicTypeName) {
		this.lePublicTypeName = lePublicTypeName;
	}

	/**
	 * @return the rePublicTypeName
	 */
	public String getRePublicTypeName() {
		return rePublicTypeName;
	}

	/**
	 * @param rePublicTypeName the rePublicTypeName to set
	 */
	public void setRePublicTypeName(String rePublicTypeName) {
		this.rePublicTypeName = rePublicTypeName;
	}

	/**
	 * @return the equipPublicTypeName
	 */
	public String getEquipPublicTypeName() {
		return equipPublicTypeName;
	}

	/**
	 * @param equipPublicTypeName the equipPublicTypeName to set
	 */
	public void setEquipPublicTypeName(String equipPublicTypeName) {
		this.equipPublicTypeName = equipPublicTypeName;
	}

	/**
	 * @return the fldTanSendStatusName
	 */
	public String getFldTanSendStatusName() {
		return fldTanSendStatusName;
	}

	/**
	 * @param fldTanSendStatusName the fldTanSendStatusName to set
	 */
	public void setFldTanSendStatusName(String fldTanSendStatusName) {
		this.fldTanSendStatusName = fldTanSendStatusName;
	}

	/**
	 * @return the fldRoSendStatusName
	 */
	public String getFldRoSendStatusName() {
		return fldRoSendStatusName;
	}

	/**
	 * @param fldRoSendStatusName the fldRoSendStatusName to set
	 */
	public void setFldRoSendStatusName(String fldRoSendStatusName) {
		this.fldRoSendStatusName = fldRoSendStatusName;
	}

	/**
	 * @return the fldIntSendStatusName
	 */
	public String getFldIntSendStatusName() {
		return fldIntSendStatusName;
	}

	/**
	 * @param fldIntSendStatusName the fldIntSendStatusName to set
	 */
	public void setFldIntSendStatusName(String fldIntSendStatusName) {
		this.fldIntSendStatusName = fldIntSendStatusName;
	}

	/**
	 * @return the leSendStatusName
	 */
	public String getLeSendStatusName() {
		return leSendStatusName;
	}

	/**
	 * @param leSendStatusName the leSendStatusName to set
	 */
	public void setLeSendStatusName(String leSendStatusName) {
		this.leSendStatusName = leSendStatusName;
	}

	/**
	 * @return the reSendStatusName
	 */
	public String getReSendStatusName() {
		return reSendStatusName;
	}

	/**
	 * @param reSendStatusName the reSendStatusName to set
	 */
	public void setReSendStatusName(String reSendStatusName) {
		this.reSendStatusName = reSendStatusName;
	}

	/**
	 * @return the equipSendStatusName
	 */
	public String getEquipSendStatusName() {
		return equipSendStatusName;
	}

	/**
	 * @param equipSendStatusName the equipSendStatusName to set
	 */
	public void setEquipSendStatusName(String equipSendStatusName) {
		this.equipSendStatusName = equipSendStatusName;
	}

	/**
	 * @return the createStatusName
	 */
	public String getCreateStatusName() {
		return createStatusName;
	}

	/**
	 * @param createStatusName the createStatusName to set
	 */
	public void setCreateStatusName(String createStatusName) {
		this.createStatusName = createStatusName;
	}

}
