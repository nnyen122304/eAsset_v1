/*
 * 作成日: 2014/01/16
 */
package jp.co.ctcg.easset.dto.master;

import java.io.Serializable;

import lombok.ToString;

/**
 * 勘定科目マスタデータ
 */
@ToString
public class SapAccountMasterData implements Serializable {
	private static final long serialVersionUID = 1L;

	private String accountCD;			// 本科目コード
	private String accountName;			// 本科目名称
	private String enabledFlag;			// 有効フラグ
	private String lastUpdateDate;		// 最終更新日付

	public String getAccountCD() {
		return accountCD;
	}
	public void setAccountCD(String accountCD) {
		this.accountCD = accountCD;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getEnabledFlag() {
		return enabledFlag;
	}
	public void setEnabledFlag(String enabledFlag) {
		this.enabledFlag = enabledFlag;
	}
	public String getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setLastUpdateDate(String lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
}
