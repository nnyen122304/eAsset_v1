/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;
import java.util.Date;

import lombok.ToString;

/**
 * 仕入先マスタデータ
 */
@ToString
public class SapVendorsMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String vendorID;		// 仕入先コード
	private String vendorGroup;		// 仕入先グループ
	private String accountGroup;	// 勘定グループ
	private String vendorName;		// 仕入先名称
	private String companyCD;		// 会社コード
	private String vendorStatus;	// 取引状態区分
	private Date createDate;		// 登録日
	private Date lastUpdateDate;	// 最終更新日
	private String enabledFlag;		// 有効フラグ

	public String getVendorID() {
		return vendorID;
	}
	public void setVendorID(String vendorID) {
		this.vendorID = vendorID;
	}
	public String getVendorGroup() {
		return vendorGroup;
	}
	public void setVendorGroup(String vendorGroup) {
		this.vendorGroup = vendorGroup;
	}
	public String getAccountGroup() {
		return accountGroup;
	}
	public void setAccountGroup(String accountGroup) {
		this.accountGroup = accountGroup;
	}
	public String getVendorName() {
		return vendorName;
	}
	public void setVendorName(String vendorName) {
		this.vendorName = vendorName;
	}
	public String getCompanyCD() {
		return companyCD;
	}
	public void setCompanyCD(String companyCD) {
		this.companyCD = companyCD;
	}
	public String getVendorStatus() {
		return vendorStatus;
	}
	public void setVendorStatus(String vendorStatus) {
		this.vendorStatus = vendorStatus;
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
