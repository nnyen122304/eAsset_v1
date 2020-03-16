/*===================================================================
 * ファイル名 : PpfsStat.java
 * 概要説明   : ProPlus取込ステータスデータクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-24 1.0  リヨン 崔     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ppfs_import;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class PpfsStat implements Serializable {
	private static final long serialVersionUID = 1L;

	private String period; // 会計年月
	private String companyCode; // 会社コード 人事マスタコード
	private String dataType; // データ区分 1:固定資産,2:リース・レンタル,1:固定資産-予測,2:リース・レンタル-予測
	private String dataTypeName; // データ区分名
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private String createStaffName; // 作成者社員名
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String execStatus; // 処理ステータス 1:実行中,2:正常終了,3:異常終了
	private String execStatusName; // 処理ステータス名
	private String schCalcBasePeriod; // 予測計算基準年月
	private Integer schCalcYear; // 予測計算年数
	private Date lastSuccessCreateDate; // 前回成功時：作成日
	private String periodName; // 会計年月

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		period = (String)input.readObject();
		companyCode = (String)input.readObject();
		dataType = (String)input.readObject();
		dataTypeName = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		createStaffName = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		execStatus = (String)input.readObject();
		execStatusName = (String)input.readObject();
		schCalcBasePeriod = (String)input.readObject();
		schCalcYear = Function.getExternalInteger(input.readObject());
		lastSuccessCreateDate = (Date)input.readObject();
		periodName = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(period);
		output.writeObject(companyCode);
		output.writeObject(dataType);
		output.writeObject(dataTypeName);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(createStaffName);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(execStatus);
		output.writeObject(execStatusName);
		output.writeObject(schCalcBasePeriod);
		output.writeObject(schCalcYear);
		output.writeObject(lastSuccessCreateDate);
		output.writeObject(periodName);
	}

	/**
	 * @return the period
	 */
	public String getPeriod() {
		return period;
	}
	/**
	 * @param period the period to set
	 */
	public void setPeriod(String period) {
		this.period = period;
	}
	/**
	 * @return the companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	/**
	 * @param companyCode the companyCode to set
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}
	/**
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}
	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	/**
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the createStaffCode
	 */
	public String getCreateStaffCode() {
		return createStaffCode;
	}
	/**
	 * @param createStaffCode the createStaffCode to set
	 */
	public void setCreateStaffCode(String createStaffCode) {
		this.createStaffCode = createStaffCode;
	}
	/**
	 * @return the updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}
	/**
	 * @param updateDate the updateDate to set
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	/**
	 * @return the updateStaffCode
	 */
	public String getUpdateStaffCode() {
		return updateStaffCode;
	}
	/**
	 * @param updateStaffCode the updateStaffCode to set
	 */
	public void setUpdateStaffCode(String updateStaffCode) {
		this.updateStaffCode = updateStaffCode;
	}
	/**
	 * @return the execStatus
	 */
	public String getExecStatus() {
		return execStatus;
	}
	/**
	 * @param execStatus the execStatus to set
	 */
	public void setExecStatus(String execStatus) {
		this.execStatus = execStatus;
	}
	/**
	 * @return the createStaffName
	 */
	public String getCreateStaffName() {
		return createStaffName;
	}
	/**
	 * @param createStaffName the createStaffName to set
	 */
	public void setCreateStaffName(String createStaffName) {
		this.createStaffName = createStaffName;
	}
	/**
	 * @return the execStatusName
	 */
	public String getExecStatusName() {
		return execStatusName;
	}
	/**
	 * @param execStatusName the execStatusName to set
	 */
	public void setExecStatusName(String execStatusName) {
		this.execStatusName = execStatusName;
	}
	/**
	 * @return the dataTypeName
	 */
	public String getDataTypeName() {
		return dataTypeName;
	}
	/**
	 * @param dataTypeName the dataTypeName to set
	 */
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}

	/**
	 * lastSuccessCreateDateを取得します。
	 * @return lastSuccessCreateDate
	 */
	public Date getLastSuccessCreateDate() {
		return lastSuccessCreateDate;
	}

	/**
	 * lastSuccessCreateDate
	 * @param lastSuccessCreateDateを設定します。
	 */
	public void setLastSuccessCreateDate(Date lastSuccessCreateDate) {
		this.lastSuccessCreateDate = lastSuccessCreateDate;
	}

	/**
	 * periodNameを取得します。
	 * @return periodName
	 */
	public String getPeriodName() {
		return periodName;
	}

	/**
	 * periodName
	 * @param periodNameを設定します。
	 */
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}

	/**
	 * @return the schCalcBasePeriod
	 */
	public String getSchCalcBasePeriod() {
		return schCalcBasePeriod;
	}

	/**
	 * @param schCalcBasePeriod the schCalcBasePeriod to set
	 */
	public void setSchCalcBasePeriod(String schCalcBasePeriod) {
		this.schCalcBasePeriod = schCalcBasePeriod;
	}

	/**
	 * @return the schCalcYear
	 */
	public Integer getSchCalcYear() {
		return schCalcYear;
	}

	/**
	 * @param schCalcYear the schCalcYear to set
	 */
	public void setSchCalcYear(Integer schCalcYear) {
		this.schCalcYear = schCalcYear;
	}

}
