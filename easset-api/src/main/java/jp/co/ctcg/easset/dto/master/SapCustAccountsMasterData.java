/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 顧客マスタデータ
 */
@ToString
public class SapCustAccountsMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String custID;			// 得意先コード
	private String custGroup;		// 得意先グループ
	private String accountGroup;	// 勘定グループ
	private String custName;		// 得意先名称
	private String companyCD;		// 会社コード
	private String custStatus;		// 取引状態区分
	private Date createDate;		// 登録日
	private Date lastUpdateDate;	// 最終更新日
	private String enabledFlag;		// 有効フラグ

	public String getCustID() {
		return custID;
	}
	public void setCustID(String custID) {
		this.custID = custID;
	}
	public String getCustGroup() {
		return custGroup;
	}
	public void setCustGroup(String custGroup) {
		this.custGroup = custGroup;
	}
	public String getAccountGroup() {
		return accountGroup;
	}
	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCompanyCD() {
		return companyCD;
	}
	public void setCompanyCD(String companyCD) {
		this.companyCD = companyCD;
	}
	public String getCustStatus() {
		return custStatus;
	}
	public void setCustStatus(String custStatus) {
		this.custStatus = custStatus;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}
