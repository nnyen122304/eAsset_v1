/*===================================================================
 * ファイル名 : AssetSR.java
 * 概要説明   : 情報機器検索結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-11 1.0  リヨン 申     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.asset;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class AssetSR extends Asset {
	private static final long serialVersionUID = 1L;

	private String assetAssetId;			// 登録した情報機器管理番号(登録申請のみ使用する)
	private String osNameOne;				// OS名[代表]
	private String netMacAddressOne;		// MACアドレス[代表]
	private String netIpAddressOne;			// IPアドレス[代表]

	// リース・レンタル情報
	private Date leaseStartDate;			// リース開始日
	private Date leaseEndDate;			// リース満了日
	private Date rentalStartDate;			// レンタル開始日
	private Date rentalEndDate;			// レンタル満了日

	// 経費情報
	private String costSectionCode; 		// 経費負担部課コード
	private String costSectionName;			// 経費負担部課名称
	private String costDepPrjCode; 			// 減価償却プロジェクトコード
	private String costDepPrjName; 			// 減価償却プロジェクト名

	// ダウンロード用
	// 共有ユーザー明細
	private Long comLineSeq;				// 行シーケンス
	private String comStaffCode;			// 共有者社員番号
	private String comStaffName;			// 共有者氏名
	private String comCompanyCode;			// 共有者所属会社コード
	private String comCompanyName;			// 共有者所属会社名
	private String comSectionCode;			// 共有者所属部署コード
	private String comSectionName;			// 共有者所属部署名
	private Date comStartDate;				// 使用開始日
	// OS明細
	private Long osLineSeq;					// 行シーケンス
	private String osName;					// OS名
	private String osDescription;			// OSコメント
	// ネットワーク明細
	private Long netLineSeq;				// 行シーケンス
	private String netMacAddress;			// MACアドレス
	private String netIpAddress;			// IPアドレス
	private String netDescription;			// ネットワークコメント
	// 棚卸明細
	private Long invLineSeq;				// 行シーケンス
	private Date invDate;					// 棚卸実施日
	private String invOfficeName;			// オフィス 棚卸実施時点のオフィス
	private String invComment;				// コメント

	//	割当情報(機器から検索) ※ CSV作成のみのためFlexに連携する必要なし。GetterSetterは定義する。
	private String licenseId;
	private String softwareMakerName;
	private String softwareName;
	private String swHolCompanyName;
	private String swHolSectionName;
	private String useTypeName;
	private String licUseCompanyName;
	private String licUseSectionName;

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		assetAssetId = (String)input.readObject();
		osNameOne = (String)input.readObject();
		netMacAddressOne = (String)input.readObject();
		netIpAddressOne = (String)input.readObject();

		leaseStartDate = (Date)input.readObject();
		leaseEndDate = (Date)input.readObject();
		rentalStartDate = (Date)input.readObject();
		rentalEndDate = (Date)input.readObject();

		costSectionCode = (String)input.readObject();
		costSectionName = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costDepPrjName = (String)input.readObject();

		comLineSeq = Function.getExternalLong(input.readObject());
		comStaffCode = (String)input.readObject();
		comStaffName = (String)input.readObject();
		comCompanyCode = (String)input.readObject();
		comCompanyName = (String)input.readObject();
		comSectionCode = (String)input.readObject();
		comSectionName = (String)input.readObject();
		comStartDate = (Date)input.readObject();

		osLineSeq = Function.getExternalLong(input.readObject());
		osName = (String)input.readObject();
		osDescription = (String)input.readObject();

		netLineSeq = Function.getExternalLong(input.readObject());
		netMacAddress = (String)input.readObject();
		netIpAddress = (String)input.readObject();
		netDescription = (String)input.readObject();

		invLineSeq = Function.getExternalLong(input.readObject());
		invDate = (Date)input.readObject();
		invOfficeName = (String)input.readObject();
		invComment = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(assetAssetId);
		output.writeObject(osNameOne);
		output.writeObject(netMacAddressOne);
		output.writeObject(netIpAddressOne);

		output.writeObject(leaseStartDate);
		output.writeObject(leaseEndDate);
		output.writeObject(rentalStartDate);
		output.writeObject(rentalEndDate);

		output.writeObject(costSectionCode);
		output.writeObject(costSectionName);
		output.writeObject(costDepPrjCode);
		output.writeObject(costDepPrjName);

		output.writeObject(comLineSeq);
		output.writeObject(comStaffCode);
		output.writeObject(comStaffName);
		output.writeObject(comCompanyCode);
		output.writeObject(comCompanyName);
		output.writeObject(comSectionCode);
		output.writeObject(comSectionName);
		output.writeObject(comStartDate);

		output.writeObject(osLineSeq);
		output.writeObject(osName);
		output.writeObject(osDescription);

		output.writeObject(netLineSeq);
		output.writeObject(netMacAddress);
		output.writeObject(netIpAddress);
		output.writeObject(netDescription);

		output.writeObject(invLineSeq);
		output.writeObject(invDate);
		output.writeObject(invOfficeName);
		output.writeObject(invComment);

	}

	public String getAssetAssetId() {
		return assetAssetId;
	}

	public void setAssetAssetId(String assetAssetId) {
		this.assetAssetId = assetAssetId;
	}

	/**
	 * @return the osNameOne
	 */
	public String getOsNameOne() {
		return osNameOne;
	}

	/**
	 * @param osNameOne the osNameOne to set
	 */
	public void setOsNameOne(String osNameOne) {
		this.osNameOne = osNameOne;
	}

	public String getNetMacAddressOne() {
		return netMacAddressOne;
	}

	public void setNetMacAddressOne(String netMacAddressOne) {
		this.netMacAddressOne = netMacAddressOne;
	}

	public String getNetIpAddressOne() {
		return netIpAddressOne;
	}

	public void setNetIpAddressOne(String netIpAddressOne) {
		this.netIpAddressOne = netIpAddressOne;
	}

	public Long getComLineSeq() {
		return comLineSeq;
	}

	public void setComLineSeq(Long comLineSeq) {
		this.comLineSeq = comLineSeq;
	}

	public String getComStaffCode() {
		return comStaffCode;
	}

	public void setComStaffCode(String comStaffCode) {
		this.comStaffCode = comStaffCode;
	}

	public String getComStaffName() {
		return comStaffName;
	}

	public void setComStaffName(String comStaffName) {
		this.comStaffName = comStaffName;
	}

	public String getComCompanyCode() {
		return comCompanyCode;
	}

	public void setComCompanyCode(String comCompanyCode) {
		this.comCompanyCode = comCompanyCode;
	}

	public String getComCompanyName() {
		return comCompanyName;
	}

	public void setComCompanyName(String comCompanyName) {
		this.comCompanyName = comCompanyName;
	}

	public String getComSectionCode() {
		return comSectionCode;
	}

	public void setComSectionCode(String comSectionCode) {
		this.comSectionCode = comSectionCode;
	}

	public String getComSectionName() {
		return comSectionName;
	}

	public void setComSectionName(String comSectionName) {
		this.comSectionName = comSectionName;
	}

	public Date getComStartDate() {
		return comStartDate;
	}

	public void setComStartDate(Date comStartDate) {
		this.comStartDate = comStartDate;
	}

	public Long getOsLineSeq() {
		return osLineSeq;
	}

	public void setOsLineSeq(Long osLineSeq) {
		this.osLineSeq = osLineSeq;
	}

	public String getOsName() {
		return osName;
	}

	public void setOsName(String osName) {
		this.osName = osName;
	}

	public String getOsDescription() {
		return osDescription;
	}

	public void setOsDescription(String osDescription) {
		this.osDescription = osDescription;
	}

	public Long getNetLineSeq() {
		return netLineSeq;
	}

	public void setNetLineSeq(Long netLineSeq) {
		this.netLineSeq = netLineSeq;
	}

	public String getNetMacAddress() {
		return netMacAddress;
	}

	public void setNetMacAddress(String netMacAddress) {
		this.netMacAddress = netMacAddress;
	}

	public String getNetIpAddress() {
		return netIpAddress;
	}

	public void setNetIpAddress(String netIpAddress) {
		this.netIpAddress = netIpAddress;
	}

	public String getNetDescription() {
		return netDescription;
	}

	public void setNetDescription(String netDescription) {
		this.netDescription = netDescription;
	}

	public Long getInvLineSeq() {
		return invLineSeq;
	}

	public void setInvLineSeq(Long invLineSeq) {
		this.invLineSeq = invLineSeq;
	}

	public Date getInvDate() {
		return invDate;
	}

	public void setInvDate(Date invDate) {
		this.invDate = invDate;
	}

	public String getInvOfficeName() {
		return invOfficeName;
	}

	public void setInvOfficeName(String invOfficeName) {
		this.invOfficeName = invOfficeName;
	}

	/**
	 * licenseIdを取得します。
	 * @return licenseId
	 */
	public String getLicenseId() {
		return licenseId;
	}

	/**
	 * licenseId
	 * @param licenseIdを設定します。
	 */
	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
	}

	/**
	 * softwareMakerNameを取得します。
	 * @return softwareMakerName
	 */
	public String getSoftwareMakerName() {
		return softwareMakerName;
	}

	/**
	 * softwareMakerName
	 * @param softwareMakerNameを設定します。
	 */
	public void setSoftwareMakerName(String softwareMakerName) {
		this.softwareMakerName = softwareMakerName;
	}

	/**
	 * softwareNameを取得します。
	 * @return softwareName
	 */
	public String getSoftwareName() {
		return softwareName;
	}

	/**
	 * softwareName
	 * @param softwareNameを設定します。
	 */
	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	/**
	 * swHolCompanyNameを取得します。
	 * @return swHolCompanyName
	 */
	public String getSwHolCompanyName() {
		return swHolCompanyName;
	}

	/**
	 * swHolCompanyName
	 * @param swHolCompanyNameを設定します。
	 */
	public void setSwHolCompanyName(String swHolCompanyName) {
		this.swHolCompanyName = swHolCompanyName;
	}

	/**
	 * swHolSectionNameを取得します。
	 * @return swHolSectionName
	 */
	public String getSwHolSectionName() {
		return swHolSectionName;
	}

	/**
	 * swHolSectionName
	 * @param swHolSectionNameを設定します。
	 */
	public void setSwHolSectionName(String swHolSectionName) {
		this.swHolSectionName = swHolSectionName;
	}

	/**
	 * useTypeNameを取得します。
	 * @return useTypeName
	 */
	public String getUseTypeName() {
		return useTypeName;
	}

	/**
	 * useTypeName
	 * @param useTypeNameを設定します。
	 */
	public void setUseTypeName(String useTypeName) {
		this.useTypeName = useTypeName;
	}

	/**
	 * licUseCompanyNameを取得します。
	 * @return licUseCompanyName
	 */
	public String getLicUseCompanyName() {
		return licUseCompanyName;
	}

	/**
	 * licUseCompanyName
	 * @param licUseCompanyNameを設定します。
	 */
	public void setLicUseCompanyName(String licUseCompanyName) {
		this.licUseCompanyName = licUseCompanyName;
	}

	/**
	 * licUseSectionNameを取得します。
	 * @return licUseSectionName
	 */
	public String getLicUseSectionName() {
		return licUseSectionName;
	}

	/**
	 * licUseSectionName
	 * @param licUseSectionNameを設定します。
	 */
	public void setLicUseSectionName(String licUseSectionName) {
		this.licUseSectionName = licUseSectionName;
	}

	public String getCostSectionCode() {
		return costSectionCode;
	}

	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
	}

	public String getCostSectionName() {
		return costSectionName;
	}

	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	public String getCostDepPrjCode() {
		return costDepPrjCode;
	}

	public void setCostDepPrjCode(String costDepPrjCode) {
		this.costDepPrjCode = costDepPrjCode;
	}

	public String getCostDepPrjName() {
		return costDepPrjName;
	}

	public void setCostDepPrjName(String costDepPrjName) {
		this.costDepPrjName = costDepPrjName;
	}

	public Date getLeaseStartDate() {
		return leaseStartDate;
	}

	public void setLeaseStartDate(Date leaseStartDate) {
		this.leaseStartDate = leaseStartDate;
	}

	public Date getLeaseEndDate() {
		return leaseEndDate;
	}

	public void setLeaseEndDate(Date leaseEndDate) {
		this.leaseEndDate = leaseEndDate;
	}

	public Date getRentalStartDate() {
		return rentalStartDate;
	}

	public void setRentalStartDate(Date rentalStartDate) {
		this.rentalStartDate = rentalStartDate;
	}

	public Date getRentalEndDate() {
		return rentalEndDate;
	}

	public void setRentalEndDate(Date rentalEndDate) {
		this.rentalEndDate = rentalEndDate;
	}

	public String getInvComment() {
		return invComment;
	}

	public void setInvComment(String invComment) {
		this.invComment = invComment;
	}

}
