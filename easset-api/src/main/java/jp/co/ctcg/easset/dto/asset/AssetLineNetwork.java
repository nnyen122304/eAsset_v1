/*===================================================================
 * ファイル名 : AssetLineNetwork.java
 * 概要説明   : 情報機器等登録申請_ネットワーク明細
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
public class AssetLineNetwork extends LineData {
	////////////////////////////////// MAC・IPアドレスgetter
	public String getNetMacAddress() {
		String address1 = netMacAddress1;
		String address2 = netMacAddress2;
		String address3 = netMacAddress3;
		String address4 = netMacAddress4;
		String address5 = netMacAddress5;
		String address6 = netMacAddress6;
		if(address1 == null) address1 = "";
		if(address2 == null) address2 = "";
		if(address3 == null) address3 = "";
		if(address4 == null) address4 = "";
		if(address5 == null) address5 = "";
		if(address6 == null) address6 = "";

		String ret = address1 + "-" + address2 + "-" + address3 + "-" + address4 + "-" + address5 + "-" + address6;
		if(ret.equals("-----")) ret = null;
		return ret;
	}
	public String getNetIpAddress() {
		String address1 = netIpAddress1;
		String address2 = netIpAddress2;
		String address3 = netIpAddress3;
		String address4 = netIpAddress4;
		if(address1 == null) address1 = "";
		if(address2 == null) address2 = "";
		if(address3 == null) address3 = "";
		if(address4 == null) address4 = "";

		String ret = address1 + "." + address2 + "." + address3 + "." + address4;
		if(ret.equals("...")) ret = null;
		return ret;
	}

	////////////////////////////////// MAC・IPアドレスsetter
	public void setNetMacAddress(String address) {
		String[] split;

		if(address == null) {
			split = new String[0];
		} else {
			split = address.replace(":", "-").split("-"); // :表記は-表記に置換分割
		}

		netMacAddress1 = null;
		netMacAddress2 = null;
		netMacAddress3 = null;
		netMacAddress4 = null;
		netMacAddress5 = null;
		netMacAddress6 = null;
		if(split.length > 0 && split[0] != null && !split[0].equals("")) netMacAddress1 = split[0].toUpperCase();
		if(split.length > 1 && split[1] != null && !split[1].equals("")) netMacAddress2 = split[1].toUpperCase();
		if(split.length > 2 && split[2] != null && !split[2].equals("")) netMacAddress3 = split[2].toUpperCase();
		if(split.length > 3 && split[3] != null && !split[3].equals("")) netMacAddress4 = split[3].toUpperCase();
		if(split.length > 4 && split[4] != null && !split[4].equals("")) netMacAddress5 = split[4].toUpperCase();
		if(split.length > 5 && split[5] != null && !split[5].equals("")) netMacAddress6 = split[5].toUpperCase();
	}
	public void setNetIpAddress(String address) {
		String[] split;

		if(address == null) {
			split = new String[0];
		} else {
			split = address.split("\\.");
		}

		netIpAddress1 = null;
		netIpAddress2 = null;
		netIpAddress3 = null;
		netIpAddress4 = null;
		if(split.length > 0 && split[0] != null && !split[0].equals("")) netIpAddress1 = split[0];
		if(split.length > 1 && split[1] != null && !split[1].equals("")) netIpAddress2 = split[1];
		if(split.length > 2 && split[2] != null && !split[2].equals("")) netIpAddress3 = split[2];
		if(split.length > 3 && split[3] != null && !split[3].equals("")) netIpAddress4 = split[3];
	}

	////////////////////////////////// 以下通常のdto定義
	private static final long serialVersionUID = 1L;

	private String assetId;				// 登録申請番号
	private Integer lineSeq;			// 行シーケンス
	private Date createDate;			// 作成日
	private String createStaffCode;		// 作成者社員番号
	private Date updateDate;			// 更新日
	private String updateStaffCode;		// 更新者社員番号
	private String netMacAddress1;		// MACアドレス1
	private String netMacAddress2;		// MACアドレス2
	private String netMacAddress3;		// MACアドレス3
	private String netMacAddress4;		// MACアドレス4
	private String netMacAddress5;		// MACアドレス5
	private String netMacAddress6;		// MACアドレス6
	private String netIpAddress1;		// IPアドレス1
	private String netIpAddress2;		// IPアドレス2
	private String netIpAddress3;		// IPアドレス3
	private String netIpAddress4;		// IPアドレス4
	private String netDescription;		// ネットワークコメント

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);		// 行データスーパークラスの処理

		assetId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		netMacAddress1 = (String)input.readObject();
		netMacAddress2 = (String)input.readObject();
		netMacAddress3 = (String)input.readObject();
		netMacAddress4 = (String)input.readObject();
		netMacAddress5 = (String)input.readObject();
		netMacAddress6 = (String)input.readObject();
		netIpAddress1 = (String)input.readObject();
		netIpAddress2 = (String)input.readObject();
		netIpAddress3 = (String)input.readObject();
		netIpAddress4 = (String)input.readObject();
		netDescription = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);		// 行データスーパークラスの処理

		output.writeObject(assetId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(netMacAddress1);
		output.writeObject(netMacAddress2);
		output.writeObject(netMacAddress3);
		output.writeObject(netMacAddress4);
		output.writeObject(netMacAddress5);
		output.writeObject(netMacAddress6);
		output.writeObject(netIpAddress1);
		output.writeObject(netIpAddress2);
		output.writeObject(netIpAddress3);
		output.writeObject(netIpAddress4);
		output.writeObject(netDescription);
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
	 * netDescriptionを取得します。
	 * @return netDescription
	 */
	public String getNetDescription() {
		return netDescription;
	}

	/**
	 * netDescription
	 * @param netDescriptionを設定します。
	 */
	public void setNetDescription(String netDescription) {
		this.netDescription = netDescription;
	}
	public String getNetMacAddress1() {
		return netMacAddress1;
	}
	public void setNetMacAddress1(String netMacAddress1) {
		this.netMacAddress1 = netMacAddress1;
	}
	public String getNetMacAddress2() {
		return netMacAddress2;
	}
	public void setNetMacAddress2(String netMacAddress2) {
		this.netMacAddress2 = netMacAddress2;
	}
	public String getNetMacAddress3() {
		return netMacAddress3;
	}
	public void setNetMacAddress3(String netMacAddress3) {
		this.netMacAddress3 = netMacAddress3;
	}
	public String getNetMacAddress4() {
		return netMacAddress4;
	}
	public void setNetMacAddress4(String netMacAddress4) {
		this.netMacAddress4 = netMacAddress4;
	}
	public String getNetMacAddress5() {
		return netMacAddress5;
	}
	public void setNetMacAddress5(String netMacAddress5) {
		this.netMacAddress5 = netMacAddress5;
	}
	public String getNetMacAddress6() {
		return netMacAddress6;
	}
	public void setNetMacAddress6(String netMacAddress6) {
		this.netMacAddress6 = netMacAddress6;
	}
	public String getNetIpAddress1() {
		return netIpAddress1;
	}
	public void setNetIpAddress1(String netIpAddress1) {
		this.netIpAddress1 = netIpAddress1;
	}
	public String getNetIpAddress2() {
		return netIpAddress2;
	}
	public void setNetIpAddress2(String netIpAddress2) {
		this.netIpAddress2 = netIpAddress2;
	}
	public String getNetIpAddress3() {
		return netIpAddress3;
	}
	public void setNetIpAddress3(String netIpAddress3) {
		this.netIpAddress3 = netIpAddress3;
	}
	public String getNetIpAddress4() {
		return netIpAddress4;
	}
	public void setNetIpAddress4(String netIpAddress4) {
		this.netIpAddress4 = netIpAddress4;
	}

}
