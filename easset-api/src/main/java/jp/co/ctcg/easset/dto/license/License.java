/*===================================================================
 * ファイル名 : License.java
 * 概要説明   : ライセンス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-25 1.0  リヨン 申     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.license;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class License implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String licenseId;							// ライセンス管理番号
	private Date createDate;							// 作成日
	private String createStaffCode;						// 作成者社員番号
	private Date updateDate;							// 更新日
	private String updateStaffCode;						// 更新者社員番号
	private Integer version;							// バージョン
	private Long apGetTanLineLicId;						// 取得申請紐付け
	private Integer apGetTanLineLicLineSeq;				// 取得申請明細No
	private String updateComment;						// 更新コメント
	private Long eappId;								// e申請書類ID
	private String apStatus;							// 申請書ステータス 1:未申請,2:申請中,3:登録完了,4:差戻し 汎用マスタ-AP_STATUS_ASSET
	private String apStatusName;						// 申請書ステータス名
	private Date apDate;								// 申請日
	private String apCreateStaffCode;					// 起票者社員番号
	private String apCreateStaffName;					// 起票者氏名
	private String apCreateCompanyCode;					// 起票者所属会社コード
	private String apCreateCompanyName;					// 起票者所属会社名
	private String apCreateSectionCode;					// 起票者所属部署コード
	private Integer apCreateSectionYear;				// 起票者所属部署年度
	private String apCreateSectionName;					// 起票者所属部署名
	private String apStaffCode;							// 申請者社員番号
	private String apStaffName;							// 申請者氏名
	private String apCompanyCode;						// 申請会社コード
	private String apCompanyName;						// 申請会社名
	private String apSectionCode;						// 申請部署コード
	private Integer apSectionYear;						// 申請部署年度
	private String apSectionName;						// 申請部署名
	private String apTel;								// 連絡先TEL
	private String apOfficeName;						// 申請者オフィス
	private String softwareMakerName;					// ソフトウェアメーカー名
	private String softwareName;						// ソフトウェア名
	private Long softwareMakerId;						// ソフトウェアメーカーID
	private Long softwareId;							// ソフトウェアID
	private String licSerialCode;						// シリアル番号
	private String licProductKey;						// プロダクトKEY
	private String licLicenseKey;						// ライセンスキー
	private String licMediaType;						// メディア形態
	private String licMediaTypeName;					// メディア形態名
	private String licAssetType;						// 資産区分
	private String licAssetTypeName;					// 資産区分名
	private String licGetType;							// 取得形態
	private String licGetTypeName;						// 取得形態名
	private String licHrdBandleFlag;					// ハードウェアバンドルフラグ
	private String licHrdBandleFlagName;				// ハードウェアバンドルフラグ名
	private String licShopName;							// 販売元
	private Long licAmount;								// 取得費用
	private String licLicenseType;						// ライセンス形態
	private String licLicenseTypeName;					// ライセンス形態名
	private String enableAllocFlag;						// 割当可能フラグ
	private String licUpgradeFlag;						// アップグレードライセンスフラグ
	private String licUpgradeFlagName;					// アップグレードライセンスフラグ名
	private String licTermFlag;							// タームライセンスフラグ
	private String licTermFlagName;						// タームライセンスフラグ名
	private String licVolumeType;						// ボリュームタイプ
	private String licVolumeTypeName;					// ボリュームタイプ名
	private String licUseConsentFlag;					// 使用許諾書有無フラグ
	private String licUseConsentFlagName;				// 使用許諾書有無フラグ名
	private String licDowngradeConsentFlag;				// ダウングレード許諾有無フラグ
	private String licDowngradeConsentFlagName;			// ダウングレード許諾有無フラグ名
	private String licQuantityType;						// ライセンス数タイプ
	private String licQuantityTypeName;					// ライセンス数タイプ名
	private Long licQuantity;							// ライセンス保有数
	private Long licEnableQuantity;						// ライセンス有効数
	private Long licUseQuantity;						// ライセンス消費数
	private Long licUpgradeToQuantity;					// ライセンスアップグレード数
	private String licUseFileId;						// ライセンス使用状況管理ファイルID
	private String licUseFileIdTmp;						// ライセンス使用状況管理ファイルID 保存前の一時ファイル
	private String licUseFileName;						// ライセンス使用状況管理ファイル名
	private String trmContractType;						// タームライセンス：契約区分
	private String trmContractTypeName;					// タームライセンス：契約区分名
	private String trmParentLicenseId;					// タームライセンス：親ライセンス管理番号
	private Date trmStartDate;							// タームライセンス：開始日
	private Date trmEndDate;							// タームライセンス：終了日
	private Date trmAlertDate;							// タームライセンス：期限通知日
	private String trmAutoAllocFlag;					// タームライセンス：自動割当切替
	private String trmAutoAllocFlagName;				// タームライセンス：自動割当切替名
	private String holCompanyCode;						// 保有会社コード
	private String holCompanyName;						// 保有会社名
	private String holSectionCode;						// 保有部署コード
	private Integer holSectionYear;						// 保有部署年度
	private String holSectionName;						// 保有部署名
	private String holStaffCode;						// 資産管理担当者
	private String holStaffName;						// 資産管理担当者氏名
	private String useType;								// 使用許諾区分
	private String useTypeName;							// 使用許諾区分名
	private String useCompanyCode;						// 使用会社コード
	private String useCompanyName;						// 使用会社名
	private String useSectionCode;						// 使用部署コード
	private Integer useSectionYear;						// 使用部署年度
	private String useSectionName;						// 使用部署名
	private String useRentalFlag;						// 他部署への貸出
	private String useRentalFlagName;					// 他部署への貸出名
	private String mntContractCode;						// 保守契約番号
	private String mntContractCompanyName;				// 保守契約先
	private Date mntContractStartDate;					// 保守契約期間FROM
	private Date mntContractEndDate;					// 保守契約期間TO
	private Long mntContractAmount;						// 保守契約金額
	private String mntContractRegistCode;				// 保守契約ユーザー登録番号
	private Date mntContractRegistDate;					// 保守契約登録日
	private String mntContractStaffCode;				// 保守契約担当者社員番号
	private String mntContractStaffName;				// 保守契約担当者氏名
	private String dscDescription;						// 備考
	private String dscAttribute1;						// 管理項目1
	private String dscAttribute2;						// 管理項目2
	private String dscAttribute3;						// 管理項目3
	private String dscAttribute4;						// 管理項目4
	private String dscAttribute5;						// 管理項目5
	private String dscAttribute6;						// 管理項目6
	private String dscAttribute7;						// 管理項目7
	private String dscAttribute8;						// 管理項目8
	private String dscAttribute9;						// 管理項目9
	private String dscAttribute10;						// 管理項目10
	private String dscAttribute11;						// 管理項目11
	private String dscAttribute12;						// 管理項目12
	private String dscAttribute13;						// 管理項目13
	private String dscAttribute14;						// 管理項目14
	private String dscAttribute15;						// 管理項目15
	private String dscAttribute16;						// 管理項目16
	private String dscAttribute17;						// 管理項目17
	private String dscAttribute18;						// 管理項目18
	private String dscAttribute19;						// 管理項目19
	private String dscAttribute20;						// 管理項目20
	private String deleteFlag;							// 抹消フラグ
	private Date deleteDate;							// 抹消日
	private String deleteReason;						// 抹消理由
	private String getApplicationId;					// 取得申請書番号
	private String getApplicationVersion;				// 取得申請(無形)バージョン
	private String registApplicationId;					// 登録申請番号
	private String contractNum;							// 契約番号
	private String parentLicenseId;						// 親ライセンス管理番号
	private String apCreateTel;							// 起票者:連絡先TEL
	private String attDescription;						// 添付補足

	// 固定資産、物件
	private String contractEda;							// 契約枝番
	private String shisanNum;							// 資産番号
	private Long koyunoL;								// 物件の固有番号(画面に表示しない)
	private Long koyunoF;								// 資産の固有番号(画面に表示しない)
	private String shisanKbn;							// 資産区分(1:有形資産、2:無形資産、3:生物、 4:繰延資産)

	//	明細
	private List<LicenseLineQty> licenseLineQtyRental;	// ライセンス_数量管理明細_貸出
	private List<LicenseLineUpg> licenseLineUpgSrc;		// ライセンス_アップグレード元明細
	private List<LicenseLineUpg> licenseLineUpgDst;		// ライセンス_アップグレード先明細
	private List<ApLicenseLineAtt> apLicenseLineAtt;	// ライセンス登録申請_添付明細

	//	許諾（Flexへの連携無し）
	private LicenseLineQty licenseQtyUse;				// ライセンス_数量管理明細_許諾

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		licenseId = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		version = Function.getExternalInteger(input.readObject());
		apGetTanLineLicId = Function.getExternalLong(input.readObject());
		apGetTanLineLicLineSeq = Function.getExternalInteger(input.readObject());
		updateComment = (String)input.readObject();
		eappId = Function.getExternalLong(input.readObject());
		apStatus = (String)input.readObject();
		apStatusName = (String)input.readObject();
		apDate = (Date)input.readObject();
		apCreateStaffCode = (String)input.readObject();
		apCreateStaffName = (String)input.readObject();
		apCreateCompanyCode = (String)input.readObject();
		apCreateCompanyName = (String)input.readObject();
		apCreateSectionCode = (String)input.readObject();
		apCreateSectionYear = Function.getExternalInteger(input.readObject());
		apCreateSectionName = (String)input.readObject();
		apStaffCode = (String)input.readObject();
		apStaffName = (String)input.readObject();
		apCompanyCode = (String)input.readObject();
		apCompanyName = (String)input.readObject();
		apSectionCode = (String)input.readObject();
		apSectionYear = Function.getExternalInteger(input.readObject());
		apSectionName = (String)input.readObject();
		apTel = (String)input.readObject();
		apOfficeName = (String)input.readObject();
		softwareMakerName = (String)input.readObject();
		softwareName = (String)input.readObject();
		softwareMakerId = Function.getExternalLong(input.readObject());
		softwareId = Function.getExternalLong(input.readObject());
		licSerialCode = (String)input.readObject();
		licProductKey = (String)input.readObject();
		licLicenseKey = (String)input.readObject();
		licMediaType = (String)input.readObject();
		licMediaTypeName = (String)input.readObject();
		licAssetType = (String)input.readObject();
		licAssetTypeName = (String)input.readObject();
		licGetType = (String)input.readObject();
		licGetTypeName = (String)input.readObject();
		licHrdBandleFlag = (String)input.readObject();
		licHrdBandleFlagName = (String)input.readObject();
		licShopName = (String)input.readObject();
		licAmount = Function.getExternalLong(input.readObject());
		licLicenseType = (String)input.readObject();
		licLicenseTypeName = (String)input.readObject();
		enableAllocFlag = (String)input.readObject();
		licUpgradeFlag = (String)input.readObject();
		licUpgradeFlagName = (String)input.readObject();
		licTermFlag = (String)input.readObject();
		licTermFlagName = (String)input.readObject();
		licVolumeType = (String)input.readObject();
		licVolumeTypeName = (String)input.readObject();
		licUseConsentFlag = (String)input.readObject();
		licUseConsentFlagName = (String)input.readObject();
		licDowngradeConsentFlag = (String)input.readObject();
		licDowngradeConsentFlagName = (String)input.readObject();
		licQuantityType = (String)input.readObject();
		licQuantityTypeName = (String)input.readObject();
		licQuantity = Function.getExternalLong(input.readObject());
		licEnableQuantity = Function.getExternalLong(input.readObject());
		licUseQuantity = Function.getExternalLong(input.readObject());
		licUpgradeToQuantity = Function.getExternalLong(input.readObject());
		licUseFileId = (String)input.readObject();
		licUseFileIdTmp = (String)input.readObject();
		licUseFileName = (String)input.readObject();
		trmContractType = (String)input.readObject();
		trmContractTypeName = (String)input.readObject();
		trmParentLicenseId = (String)input.readObject();
		trmStartDate = (Date)input.readObject();
		trmEndDate = (Date)input.readObject();
		trmAlertDate = (Date)input.readObject();
		trmAutoAllocFlag = (String)input.readObject();
		trmAutoAllocFlagName = (String)input.readObject();
		holCompanyCode = (String)input.readObject();
		holCompanyName = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holSectionName = (String)input.readObject();
		holStaffCode = (String)input.readObject();
		holStaffName = (String)input.readObject();
		useType = (String)input.readObject();
		useTypeName = (String)input.readObject();
		useCompanyCode = (String)input.readObject();
		useCompanyName = (String)input.readObject();
		useSectionCode = (String)input.readObject();
		useSectionYear = Function.getExternalInteger(input.readObject());
		useSectionName = (String)input.readObject();
		useRentalFlag = (String)input.readObject();
		useRentalFlagName = (String)input.readObject();
		mntContractCode = (String)input.readObject();
		mntContractCompanyName = (String)input.readObject();
		mntContractStartDate = (Date)input.readObject();
		mntContractEndDate = (Date)input.readObject();
		mntContractAmount = Function.getExternalLong(input.readObject());
		mntContractRegistCode = (String)input.readObject();
		mntContractRegistDate = (Date)input.readObject();
		mntContractStaffCode = (String)input.readObject();
		mntContractStaffName = (String)input.readObject();
		dscDescription = (String)input.readObject();
		dscAttribute1 = (String)input.readObject();
		dscAttribute2 = (String)input.readObject();
		dscAttribute3 = (String)input.readObject();
		dscAttribute4 = (String)input.readObject();
		dscAttribute5 = (String)input.readObject();
		dscAttribute6 = (String)input.readObject();
		dscAttribute7 = (String)input.readObject();
		dscAttribute8 = (String)input.readObject();
		dscAttribute9 = (String)input.readObject();
		dscAttribute10 = (String)input.readObject();
		dscAttribute11 = (String)input.readObject();
		dscAttribute12 = (String)input.readObject();
		dscAttribute13 = (String)input.readObject();
		dscAttribute14 = (String)input.readObject();
		dscAttribute15 = (String)input.readObject();
		dscAttribute16 = (String)input.readObject();
		dscAttribute17 = (String)input.readObject();
		dscAttribute18 = (String)input.readObject();
		dscAttribute19 = (String)input.readObject();
		dscAttribute20 = (String)input.readObject();
		deleteFlag = (String)input.readObject();
		deleteDate = (Date)input.readObject();
		deleteReason = (String)input.readObject();
		getApplicationId = (String)input.readObject();
		getApplicationVersion = (String)input.readObject();
		registApplicationId = (String)input.readObject();
		contractNum = (String)input.readObject();
		parentLicenseId = (String)input.readObject();
		apCreateTel = (String)input.readObject();
		attDescription = (String)input.readObject();

		// 固定資産、物件
		contractEda = (String)input.readObject();
		shisanNum = (String)input.readObject();
		koyunoL = Function.getExternalLong(input.readObject());
		koyunoF = Function.getExternalLong(input.readObject());
		shisanKbn = (String)input.readObject();

		licenseLineQtyRental = (List<LicenseLineQty>)input.readObject();
		licenseLineUpgSrc = (List<LicenseLineUpg>)input.readObject();
		licenseLineUpgDst = (List<LicenseLineUpg>)input.readObject();
		apLicenseLineAtt = (List<ApLicenseLineAtt>)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		output.writeObject(licenseId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(version);
		output.writeObject(apGetTanLineLicId);
		output.writeObject(apGetTanLineLicLineSeq);
		output.writeObject(updateComment);
		output.writeObject(eappId);
		output.writeObject(apStatus);
		output.writeObject(apStatusName);
		output.writeObject(apDate);
		output.writeObject(apCreateStaffCode);
		output.writeObject(apCreateStaffName);
		output.writeObject(apCreateCompanyCode);
		output.writeObject(apCreateCompanyName);
		output.writeObject(apCreateSectionCode);
		output.writeObject(apCreateSectionYear);
		output.writeObject(apCreateSectionName);
		output.writeObject(apStaffCode);
		output.writeObject(apStaffName);
		output.writeObject(apCompanyCode);
		output.writeObject(apCompanyName);
		output.writeObject(apSectionCode);
		output.writeObject(apSectionYear);
		output.writeObject(apSectionName);
		output.writeObject(apTel);
		output.writeObject(apOfficeName);
		output.writeObject(softwareMakerName);
		output.writeObject(softwareName);
		output.writeObject(softwareMakerId);
		output.writeObject(softwareId);
		output.writeObject(licSerialCode);
		output.writeObject(licProductKey);
		output.writeObject(licLicenseKey);
		output.writeObject(licMediaType);
		output.writeObject(licMediaTypeName);
		output.writeObject(licAssetType);
		output.writeObject(licAssetTypeName);
		output.writeObject(licGetType);
		output.writeObject(licGetTypeName);
		output.writeObject(licHrdBandleFlag);
		output.writeObject(licHrdBandleFlagName);
		output.writeObject(licShopName);
		output.writeObject(licAmount);
		output.writeObject(licLicenseType);
		output.writeObject(licLicenseTypeName);
		output.writeObject(enableAllocFlag);
		output.writeObject(licUpgradeFlag);
		output.writeObject(licUpgradeFlagName);
		output.writeObject(licTermFlag);
		output.writeObject(licTermFlagName);
		output.writeObject(licVolumeType);
		output.writeObject(licVolumeTypeName);
		output.writeObject(licUseConsentFlag);
		output.writeObject(licUseConsentFlagName);
		output.writeObject(licDowngradeConsentFlag);
		output.writeObject(licDowngradeConsentFlagName);
		output.writeObject(licQuantityType);
		output.writeObject(licQuantityTypeName);
		output.writeObject(licQuantity);
		output.writeObject(licEnableQuantity);
		output.writeObject(licUseQuantity);
		output.writeObject(licUpgradeToQuantity);
		output.writeObject(licUseFileId);
		output.writeObject(licUseFileIdTmp);
		output.writeObject(licUseFileName);
		output.writeObject(trmContractType);
		output.writeObject(trmContractTypeName);
		output.writeObject(trmParentLicenseId);
		output.writeObject(trmStartDate);
		output.writeObject(trmEndDate);
		output.writeObject(trmAlertDate);
		output.writeObject(trmAutoAllocFlag);
		output.writeObject(trmAutoAllocFlagName);
		output.writeObject(holCompanyCode);
		output.writeObject(holCompanyName);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(holSectionName);
		output.writeObject(holStaffCode);
		output.writeObject(holStaffName);
		output.writeObject(useType);
		output.writeObject(useTypeName);
		output.writeObject(useCompanyCode);
		output.writeObject(useCompanyName);
		output.writeObject(useSectionCode);
		output.writeObject(useSectionYear);
		output.writeObject(useSectionName);
		output.writeObject(useRentalFlag);
		output.writeObject(useRentalFlagName);
		output.writeObject(mntContractCode);
		output.writeObject(mntContractCompanyName);
		output.writeObject(mntContractStartDate);
		output.writeObject(mntContractEndDate);
		output.writeObject(mntContractAmount);
		output.writeObject(mntContractRegistCode);
		output.writeObject(mntContractRegistDate);
		output.writeObject(mntContractStaffCode);
		output.writeObject(mntContractStaffName);
		output.writeObject(dscDescription);
		output.writeObject(dscAttribute1);
		output.writeObject(dscAttribute2);
		output.writeObject(dscAttribute3);
		output.writeObject(dscAttribute4);
		output.writeObject(dscAttribute5);
		output.writeObject(dscAttribute6);
		output.writeObject(dscAttribute7);
		output.writeObject(dscAttribute8);
		output.writeObject(dscAttribute9);
		output.writeObject(dscAttribute10);
		output.writeObject(dscAttribute11);
		output.writeObject(dscAttribute12);
		output.writeObject(dscAttribute13);
		output.writeObject(dscAttribute14);
		output.writeObject(dscAttribute15);
		output.writeObject(dscAttribute16);
		output.writeObject(dscAttribute17);
		output.writeObject(dscAttribute18);
		output.writeObject(dscAttribute19);
		output.writeObject(dscAttribute20);
		output.writeObject(deleteFlag);
		output.writeObject(deleteDate);
		output.writeObject(deleteReason);
		output.writeObject(getApplicationId);
		output.writeObject(getApplicationVersion);
		output.writeObject(registApplicationId);
		output.writeObject(contractNum);
		output.writeObject(parentLicenseId);
		output.writeObject(apCreateTel);
		output.writeObject(attDescription);

		// 固定資産、物件
		output.writeObject(contractEda);
		output.writeObject(shisanNum);
		output.writeObject(koyunoL);
		output.writeObject(koyunoF);
		output.writeObject(shisanKbn);

		output.writeObject(licenseLineQtyRental);
		output.writeObject(licenseLineUpgSrc);
		output.writeObject(licenseLineUpgDst);
		output.writeObject(apLicenseLineAtt);
	}

	public String getLicenseId() {
		return licenseId;
	}

	public void setLicenseId(String licenseId) {
		this.licenseId = licenseId;
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

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Long getSoftwareMakerId() {
		return softwareMakerId;
	}

	public void setSoftwareMakerId(Long softwareMakerId) {
		this.softwareMakerId = softwareMakerId;
	}

	public String getSoftwareMakerName() {
		return softwareMakerName;
	}

	public void setSoftwareMakerName(String softwareMakerName) {
		this.softwareMakerName = softwareMakerName;
	}

	public Long getSoftwareId() {
		return softwareId;
	}

	public void setSoftwareId(Long softwareId) {
		this.softwareId = softwareId;
	}

	public String getSoftwareName() {
		return softwareName;
	}

	public void setSoftwareName(String softwareName) {
		this.softwareName = softwareName;
	}

	public String getLicSerialCode() {
		return licSerialCode;
	}

	public void setLicSerialCode(String licSerialCode) {
		this.licSerialCode = licSerialCode;
	}

	public String getLicProductKey() {
		return licProductKey;
	}

	public void setLicProductKey(String licProductKey) {
		this.licProductKey = licProductKey;
	}

	public String getLicLicenseKey() {
		return licLicenseKey;
	}

	public void setLicLicenseKey(String licLicenseKey) {
		this.licLicenseKey = licLicenseKey;
	}

	public String getLicMediaType() {
		return licMediaType;
	}

	public void setLicMediaType(String licMediaType) {
		this.licMediaType = licMediaType;
	}

	public String getLicAssetType() {
		return licAssetType;
	}

	public void setLicAssetType(String licAssetType) {
		this.licAssetType = licAssetType;
	}

	public String getLicAssetTypeName() {
		return licAssetTypeName;
	}

	public void setLicAssetTypeName(String licAssetTypeName) {
		this.licAssetTypeName = licAssetTypeName;
	}

	public String getLicGetType() {
		return licGetType;
	}

	public void setLicGetType(String licGetType) {
		this.licGetType = licGetType;
	}

	public String getLicGetTypeName() {
		return licGetTypeName;
	}

	public void setLicGetTypeName(String licGetTypeName) {
		this.licGetTypeName = licGetTypeName;
	}

	public String getLicHrdBandleFlag() {
		return licHrdBandleFlag;
	}

	public void setLicHrdBandleFlag(String licHrdBandleFlag) {
		this.licHrdBandleFlag = licHrdBandleFlag;
	}

	public String getLicHrdBandleFlagName() {
		return licHrdBandleFlagName;
	}

	public void setLicHrdBandleFlagName(String licHrdBandleFlagName) {
		this.licHrdBandleFlagName = licHrdBandleFlagName;
	}

	public String getLicShopName() {
		return licShopName;
	}

	public void setLicShopName(String licShopName) {
		this.licShopName = licShopName;
	}

	public Long getLicAmount() {
		return licAmount;
	}

	public void setLicAmount(Long licAmount) {
		this.licAmount = licAmount;
	}

	public String getLicLicenseType() {
		return licLicenseType;
	}

	public void setLicLicenseType(String licLicenseType) {
		this.licLicenseType = licLicenseType;
	}

	public String getLicLicenseTypeName() {
		return licLicenseTypeName;
	}

	public void setLicLicenseTypeName(String licLicenseTypeName) {
		this.licLicenseTypeName = licLicenseTypeName;
	}

	public String getLicUpgradeFlag() {
		return licUpgradeFlag;
	}

	public void setLicUpgradeFlag(String licUpgradeFlag) {
		this.licUpgradeFlag = licUpgradeFlag;
	}

	public String getLicUpgradeFlagName() {
		return licUpgradeFlagName;
	}

	public void setLicUpgradeFlagName(String licUpgradeFlagName) {
		this.licUpgradeFlagName = licUpgradeFlagName;
	}

	public String getLicTermFlag() {
		return licTermFlag;
	}

	public void setLicTermFlag(String licTermFlag) {
		this.licTermFlag = licTermFlag;
	}

	public String getLicTermFlagName() {
		return licTermFlagName;
	}

	public void setLicTermFlagName(String licTermFlagName) {
		this.licTermFlagName = licTermFlagName;
	}

	public String getLicVolumeType() {
		return licVolumeType;
	}

	public void setLicVolumeType(String licVolumeType) {
		this.licVolumeType = licVolumeType;
	}

	public String getLicUseConsentFlag() {
		return licUseConsentFlag;
	}

	public void setLicUseConsentFlag(String licUseConsentFlag) {
		this.licUseConsentFlag = licUseConsentFlag;
	}

	public String getLicDowngradeConsentFlag() {
		return licDowngradeConsentFlag;
	}

	public void setLicDowngradeConsentFlag(String licDowngradeConsentFlag) {
		this.licDowngradeConsentFlag = licDowngradeConsentFlag;
	}

	public String getLicQuantityType() {
		return licQuantityType;
	}

	public void setLicQuantityType(String licQuantityType) {
		this.licQuantityType = licQuantityType;
	}

	/**
	 * @return the licQuantityTypeName
	 */
	public String getLicQuantityTypeName() {
		return licQuantityTypeName;
	}

	/**
	 * @param licQuantityTypeName the licQuantityTypeName to set
	 */
	public void setLicQuantityTypeName(String licQuantityTypeName) {
		this.licQuantityTypeName = licQuantityTypeName;
	}

	public Long getLicQuantity() {
		return licQuantity;
	}

	public void setLicQuantity(Long licQuantity) {
		this.licQuantity = licQuantity;
	}

	public Long getLicEnableQuantity() {
		return licEnableQuantity;
	}

	public void setLicEnableQuantity(Long licEnableQuantity) {
		this.licEnableQuantity = licEnableQuantity;
	}

	public Long getLicUseQuantity() {
		return licUseQuantity;
	}

	public void setLicUseQuantity(Long licUseQuantity) {
		this.licUseQuantity = licUseQuantity;
	}

	public String getLicUseFileId() {
		return licUseFileId;
	}

	public void setLicUseFileId(String licUseFileId) {
		this.licUseFileId = licUseFileId;
	}

	public String getTrmContractType() {
		return trmContractType;
	}

	public void setTrmContractType(String trmContractType) {
		this.trmContractType = trmContractType;
	}

	public String getTrmParentLicenseId() {
		return trmParentLicenseId;
	}

	public void setTrmParentLicenseId(String trmParentLicenseId) {
		this.trmParentLicenseId = trmParentLicenseId;
	}

	public Date getTrmStartDate() {
		return trmStartDate;
	}

	public void setTrmStartDate(Date trmStartDate) {
		this.trmStartDate = trmStartDate;
	}

	public Date getTrmEndDate() {
		return trmEndDate;
	}

	public void setTrmEndDate(Date trmEndDate) {
		this.trmEndDate = trmEndDate;
	}

	public Date getTrmAlertDate() {
		return trmAlertDate;
	}

	public void setTrmAlertDate(Date trmAlertDate) {
		this.trmAlertDate = trmAlertDate;
	}

	public String getTrmAutoAllocFlag() {
		return trmAutoAllocFlag;
	}

	public void setTrmAutoAllocFlag(String trmAutoAllocFlag) {
		this.trmAutoAllocFlag = trmAutoAllocFlag;
	}

	public String getHolCompanyCode() {
		return holCompanyCode;
	}

	public void setHolCompanyCode(String holCompanyCode) {
		this.holCompanyCode = holCompanyCode;
	}

	public String getHolCompanyName() {
		return holCompanyName;
	}

	public void setHolCompanyName(String holCompanyName) {
		this.holCompanyName = holCompanyName;
	}

	public String getHolSectionCode() {
		return holSectionCode;
	}

	public void setHolSectionCode(String holSectionCode) {
		this.holSectionCode = holSectionCode;
	}

	public Integer getHolSectionYear() {
		return holSectionYear;
	}

	public void setHolSectionYear(Integer holSectionYear) {
		this.holSectionYear = holSectionYear;
	}

	public String getHolSectionName() {
		return holSectionName;
	}

	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}

	public String getHolStaffCode() {
		return holStaffCode;
	}

	public void setHolStaffCode(String holStaffCode) {
		this.holStaffCode = holStaffCode;
	}

	public String getHolStaffName() {
		return holStaffName;
	}

	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}

	public String getUseType() {
		return useType;
	}

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getUseCompanyCode() {
		return useCompanyCode;
	}

	public void setUseCompanyCode(String useCompanyCode) {
		this.useCompanyCode = useCompanyCode;
	}

	public String getUseCompanyName() {
		return useCompanyName;
	}

	public void setUseCompanyName(String useCompanyName) {
		this.useCompanyName = useCompanyName;
	}

	public String getUseSectionCode() {
		return useSectionCode;
	}

	public void setUseSectionCode(String useSectionCode) {
		this.useSectionCode = useSectionCode;
	}

	public Integer getUseSectionYear() {
		return useSectionYear;
	}

	public void setUseSectionYear(Integer useSectionYear) {
		this.useSectionYear = useSectionYear;
	}

	public String getUseSectionName() {
		return useSectionName;
	}

	public void setUseSectionName(String useSectionName) {
		this.useSectionName = useSectionName;
	}

	public String getUseRentalFlag() {
		return useRentalFlag;
	}

	public void setUseRentalFlag(String useRentalFlag) {
		this.useRentalFlag = useRentalFlag;
	}

	/**
	 * @return the useRentalFlagName
	 */
	public String getUseRentalFlagName() {
		return useRentalFlagName;
	}

	/**
	 * @param useRentalFlagName the useRentalFlagName to set
	 */
	public void setUseRentalFlagName(String useRentalFlagName) {
		this.useRentalFlagName = useRentalFlagName;
	}

	public String getMntContractCode() {
		return mntContractCode;
	}

	public void setMntContractCode(String mntContractCode) {
		this.mntContractCode = mntContractCode;
	}

	public String getMntContractCompanyName() {
		return mntContractCompanyName;
	}

	public void setMntContractCompanyName(String mntContractCompanyName) {
		this.mntContractCompanyName = mntContractCompanyName;
	}

	public Date getMntContractStartDate() {
		return mntContractStartDate;
	}

	public void setMntContractStartDate(Date mntContractStartDate) {
		this.mntContractStartDate = mntContractStartDate;
	}

	public Date getMntContractEndDate() {
		return mntContractEndDate;
	}

	public void setMntContractEndDate(Date mntContractEndDate) {
		this.mntContractEndDate = mntContractEndDate;
	}

	public Long getMntContractAmount() {
		return mntContractAmount;
	}

	public void setMntContractAmount(Long mntContractAmount) {
		this.mntContractAmount = mntContractAmount;
	}

	public String getMntContractRegistCode() {
		return mntContractRegistCode;
	}

	public void setMntContractRegistCode(String mntContractRegistCode) {
		this.mntContractRegistCode = mntContractRegistCode;
	}

	public Date getMntContractRegistDate() {
		return mntContractRegistDate;
	}

	public void setMntContractRegistDate(Date mntContractRegistDate) {
		this.mntContractRegistDate = mntContractRegistDate;
	}

	public String getMntContractStaffCode() {
		return mntContractStaffCode;
	}

	public void setMntContractStaffCode(String mntContractStaffCode) {
		this.mntContractStaffCode = mntContractStaffCode;
	}

	public String getMntContractStaffName() {
		return mntContractStaffName;
	}

	public void setMntContractStaffName(String mntContractStaffName) {
		this.mntContractStaffName = mntContractStaffName;
	}

	public String getDscDescription() {
		return dscDescription;
	}

	public void setDscDescription(String dscDescription) {
		this.dscDescription = dscDescription;
	}

	public String getDscAttribute1() {
		return dscAttribute1;
	}

	public void setDscAttribute1(String dscAttribute1) {
		this.dscAttribute1 = dscAttribute1;
	}

	public String getDscAttribute2() {
		return dscAttribute2;
	}

	public void setDscAttribute2(String dscAttribute2) {
		this.dscAttribute2 = dscAttribute2;
	}

	public String getDscAttribute3() {
		return dscAttribute3;
	}

	public void setDscAttribute3(String dscAttribute3) {
		this.dscAttribute3 = dscAttribute3;
	}

	public String getDscAttribute4() {
		return dscAttribute4;
	}

	public void setDscAttribute4(String dscAttribute4) {
		this.dscAttribute4 = dscAttribute4;
	}

	public String getDscAttribute5() {
		return dscAttribute5;
	}

	public void setDscAttribute5(String dscAttribute5) {
		this.dscAttribute5 = dscAttribute5;
	}

	public String getDscAttribute6() {
		return dscAttribute6;
	}

	public void setDscAttribute6(String dscAttribute6) {
		this.dscAttribute6 = dscAttribute6;
	}

	public String getDscAttribute7() {
		return dscAttribute7;
	}

	public void setDscAttribute7(String dscAttribute7) {
		this.dscAttribute7 = dscAttribute7;
	}

	public String getDscAttribute8() {
		return dscAttribute8;
	}

	public void setDscAttribute8(String dscAttribute8) {
		this.dscAttribute8 = dscAttribute8;
	}

	public String getDscAttribute9() {
		return dscAttribute9;
	}

	public void setDscAttribute9(String dscAttribute9) {
		this.dscAttribute9 = dscAttribute9;
	}

	public String getDscAttribute10() {
		return dscAttribute10;
	}

	public void setDscAttribute10(String dscAttribute10) {
		this.dscAttribute10 = dscAttribute10;
	}

	public String getDscAttribute11() {
		return dscAttribute11;
	}

	public void setDscAttribute11(String dscAttribute11) {
		this.dscAttribute11 = dscAttribute11;
	}

	public String getDscAttribute12() {
		return dscAttribute12;
	}

	public void setDscAttribute12(String dscAttribute12) {
		this.dscAttribute12 = dscAttribute12;
	}

	public String getDscAttribute13() {
		return dscAttribute13;
	}

	public void setDscAttribute13(String dscAttribute13) {
		this.dscAttribute13 = dscAttribute13;
	}

	public String getDscAttribute14() {
		return dscAttribute14;
	}

	public void setDscAttribute14(String dscAttribute14) {
		this.dscAttribute14 = dscAttribute14;
	}

	public String getDscAttribute15() {
		return dscAttribute15;
	}

	public void setDscAttribute15(String dscAttribute15) {
		this.dscAttribute15 = dscAttribute15;
	}

	public String getDscAttribute16() {
		return dscAttribute16;
	}

	public void setDscAttribute16(String dscAttribute16) {
		this.dscAttribute16 = dscAttribute16;
	}

	public String getDscAttribute17() {
		return dscAttribute17;
	}

	public void setDscAttribute17(String dscAttribute17) {
		this.dscAttribute17 = dscAttribute17;
	}

	public String getDscAttribute18() {
		return dscAttribute18;
	}

	public void setDscAttribute18(String dscAttribute18) {
		this.dscAttribute18 = dscAttribute18;
	}

	public String getDscAttribute19() {
		return dscAttribute19;
	}

	public void setDscAttribute19(String dscAttribute19) {
		this.dscAttribute19 = dscAttribute19;
	}

	public String getDscAttribute20() {
		return dscAttribute20;
	}

	public void setDscAttribute20(String dscAttribute20) {
		this.dscAttribute20 = dscAttribute20;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Date getDeleteDate() {
		return deleteDate;
	}

	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getGetApplicationId() {
		return getApplicationId;
	}

	public void setGetApplicationId(String getApplicationId) {
		this.getApplicationId = getApplicationId;
	}

	public String getRegistApplicationId() {
		return registApplicationId;
	}

	public void setRegistApplicationId(String registApplicationId) {
		this.registApplicationId = registApplicationId;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getParentLicenseId() {
		return parentLicenseId;
	}

	public void setParentLicenseId(String parentLicenseId) {
		this.parentLicenseId = parentLicenseId;
	}

	public String getLicMediaTypeName() {
		return licMediaTypeName;
	}

	public void setLicMediaTypeName(String licMediaTypeName) {
		this.licMediaTypeName = licMediaTypeName;
	}

	public String getLicVolumeTypeName() {
		return licVolumeTypeName;
	}

	public void setLicVolumeTypeName(String licVolumeTypeName) {
		this.licVolumeTypeName = licVolumeTypeName;
	}

	/**
	 * @return the apGetTanLineLicId
	 */
	public Long getApGetTanLineLicId() {
		return apGetTanLineLicId;
	}

	/**
	 * @param apGetTanLineLicId the apGetTanLineLicId to set
	 */
	public void setApGetTanLineLicId(Long apGetTanLineLicId) {
		this.apGetTanLineLicId = apGetTanLineLicId;
	}

	/**
	 * @return the apGetTanLineLicLineSeq
	 */
	public Integer getApGetTanLineLicLineSeq() {
		return apGetTanLineLicLineSeq;
	}

	/**
	 * @param apGetTanLineLicLineSeq the apGetTanLineLicLineSeq to set
	 */
	public void setApGetTanLineLicLineSeq(Integer apGetTanLineLicLineSeq) {
		this.apGetTanLineLicLineSeq = apGetTanLineLicLineSeq;
	}

	/**
	 * @return the updateComment
	 */
	public String getUpdateComment() {
		return updateComment;
	}

	/**
	 * @param updateComment the updateComment to set
	 */
	public void setUpdateComment(String updateComment) {
		this.updateComment = updateComment;
	}

	/**
	 * @return the eappId
	 */
	public Long getEappId() {
		return eappId;
	}

	/**
	 * @param eappId the eappId to set
	 */
	public void setEappId(Long eappId) {
		this.eappId = eappId;
	}

	/**
	 * @return the apStatus
	 */
	public String getApStatus() {
		return apStatus;
	}

	/**
	 * @param apStatus the apStatus to set
	 */
	public void setApStatus(String apStatus) {
		this.apStatus = apStatus;
	}

	/**
	 * @return the apStatusName
	 */
	public String getApStatusName() {
		return apStatusName;
	}

	/**
	 * @param apStatusName the apStatusName to set
	 */
	public void setApStatusName(String apStatusName) {
		this.apStatusName = apStatusName;
	}

	/**
	 * @return the apDate
	 */
	public Date getApDate() {
		return apDate;
	}

	/**
	 * @param apDate the apDate to set
	 */
	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}

	/**
	 * @return the apCreateStaffCode
	 */
	public String getApCreateStaffCode() {
		return apCreateStaffCode;
	}

	/**
	 * @param apCreateStaffCode the apCreateStaffCode to set
	 */
	public void setApCreateStaffCode(String apCreateStaffCode) {
		this.apCreateStaffCode = apCreateStaffCode;
	}

	/**
	 * @return the apCreateStaffName
	 */
	public String getApCreateStaffName() {
		return apCreateStaffName;
	}

	/**
	 * @param apCreateStaffName the apCreateStaffName to set
	 */
	public void setApCreateStaffName(String apCreateStaffName) {
		this.apCreateStaffName = apCreateStaffName;
	}

	/**
	 * @return the apCreateCompanyCode
	 */
	public String getApCreateCompanyCode() {
		return apCreateCompanyCode;
	}

	/**
	 * @param apCreateCompanyCode the apCreateCompanyCode to set
	 */
	public void setApCreateCompanyCode(String apCreateCompanyCode) {
		this.apCreateCompanyCode = apCreateCompanyCode;
	}

	/**
	 * @return the apCreateCompanyName
	 */
	public String getApCreateCompanyName() {
		return apCreateCompanyName;
	}

	/**
	 * @param apCreateCompanyName the apCreateCompanyName to set
	 */
	public void setApCreateCompanyName(String apCreateCompanyName) {
		this.apCreateCompanyName = apCreateCompanyName;
	}

	/**
	 * @return the apCreateSectionCode
	 */
	public String getApCreateSectionCode() {
		return apCreateSectionCode;
	}

	/**
	 * @param apCreateSectionCode the apCreateSectionCode to set
	 */
	public void setApCreateSectionCode(String apCreateSectionCode) {
		this.apCreateSectionCode = apCreateSectionCode;
	}

	/**
	 * @return the apCreateSectionYear
	 */
	public Integer getApCreateSectionYear() {
		return apCreateSectionYear;
	}

	/**
	 * @param apCreateSectionYear the apCreateSectionYear to set
	 */
	public void setApCreateSectionYear(Integer apCreateSectionYear) {
		this.apCreateSectionYear = apCreateSectionYear;
	}

	/**
	 * @return the apCreateSectionName
	 */
	public String getApCreateSectionName() {
		return apCreateSectionName;
	}

	/**
	 * @param apCreateSectionName the apCreateSectionName to set
	 */
	public void setApCreateSectionName(String apCreateSectionName) {
		this.apCreateSectionName = apCreateSectionName;
	}

	/**
	 * @return the apStaffCode
	 */
	public String getApStaffCode() {
		return apStaffCode;
	}

	/**
	 * @param apStaffCode the apStaffCode to set
	 */
	public void setApStaffCode(String apStaffCode) {
		this.apStaffCode = apStaffCode;
	}

	/**
	 * @return the apStaffName
	 */
	public String getApStaffName() {
		return apStaffName;
	}

	/**
	 * @param apStaffName the apStaffName to set
	 */
	public void setApStaffName(String apStaffName) {
		this.apStaffName = apStaffName;
	}

	/**
	 * @return the apCompanyCode
	 */
	public String getApCompanyCode() {
		return apCompanyCode;
	}

	/**
	 * @param apCompanyCode the apCompanyCode to set
	 */
	public void setApCompanyCode(String apCompanyCode) {
		this.apCompanyCode = apCompanyCode;
	}

	/**
	 * @return the apCompanyName
	 */
	public String getApCompanyName() {
		return apCompanyName;
	}

	/**
	 * @param apCompanyName the apCompanyName to set
	 */
	public void setApCompanyName(String apCompanyName) {
		this.apCompanyName = apCompanyName;
	}

	/**
	 * @return the apSectionCode
	 */
	public String getApSectionCode() {
		return apSectionCode;
	}

	/**
	 * @param apSectionCode the apSectionCode to set
	 */
	public void setApSectionCode(String apSectionCode) {
		this.apSectionCode = apSectionCode;
	}

	/**
	 * @return the apSectionYear
	 */
	public Integer getApSectionYear() {
		return apSectionYear;
	}

	/**
	 * @param apSectionYear the apSectionYear to set
	 */
	public void setApSectionYear(Integer apSectionYear) {
		this.apSectionYear = apSectionYear;
	}

	/**
	 * @return the apSectionName
	 */
	public String getApSectionName() {
		return apSectionName;
	}

	/**
	 * @param apSectionName the apSectionName to set
	 */
	public void setApSectionName(String apSectionName) {
		this.apSectionName = apSectionName;
	}

	/**
	 * @return the apTel
	 */
	public String getApTel() {
		return apTel;
	}

	/**
	 * @param apTel the apTel to set
	 */
	public void setApTel(String apTel) {
		this.apTel = apTel;
	}

	public String getApOfficeName() {
		return apOfficeName;
	}

	public void setApOfficeName(String apOfficeName) {
		this.apOfficeName = apOfficeName;
	}

	public List<LicenseLineQty> getLicenseLineQtyRental() {
		return licenseLineQtyRental;
	}

	public void setLicenseLineQtyRental(List<LicenseLineQty> licenseLineQtyRental) {
		this.licenseLineQtyRental = licenseLineQtyRental;
	}

	public List<LicenseLineUpg> getLicenseLineUpgSrc() {
		return licenseLineUpgSrc;
	}

	public void setLicenseLineUpgSrc(List<LicenseLineUpg> licenseLineUpgSrc) {
		this.licenseLineUpgSrc = licenseLineUpgSrc;
	}

	public List<LicenseLineUpg> getLicenseLineUpgDst() {
		return licenseLineUpgDst;
	}

	public void setLicenseLineUpgDst(List<LicenseLineUpg> licenseLineUpgDst) {
		this.licenseLineUpgDst = licenseLineUpgDst;
	}

	public Long getLicUpgradeToQuantity() {
		return licUpgradeToQuantity;
	}

	public void setLicUpgradeToQuantity(Long licUpgradeToQuantity) {
		this.licUpgradeToQuantity = licUpgradeToQuantity;
	}

	/**
	 * @return the licUseFileName
	 */
	public String getLicUseFileName() {
		return licUseFileName;
	}

	/**
	 * @param licUseFileName the licUseFileName to set
	 */
	public void setLicUseFileName(String licUseFileName) {
		this.licUseFileName = licUseFileName;
	}

	/**
	 * @return the licUseFileIdTmp
	 */
	public String getLicUseFileIdTmp() {
		return licUseFileIdTmp;
	}

	/**
	 * @param licUseFileIdTmp the licUseFileIdTmp to set
	 */
	public void setLicUseFileIdTmp(String licUseFileIdTmp) {
		this.licUseFileIdTmp = licUseFileIdTmp;
	}

	/**
	 * @return the enableAllocFlag
	 */
	public String getEnableAllocFlag() {
		return enableAllocFlag;
	}

	/**
	 * @param enableAllocFlag the enableAllocFlag to set
	 */
	public void setEnableAllocFlag(String enableAllocFlag) {
		this.enableAllocFlag = enableAllocFlag;
	}

	/**
	 * @return the licUseConsentFlagName
	 */
	public String getLicUseConsentFlagName() {
		return licUseConsentFlagName;
	}

	/**
	 * @param licUseConsentFlagName the licUseConsentFlagName to set
	 */
	public void setLicUseConsentFlagName(String licUseConsentFlagName) {
		this.licUseConsentFlagName = licUseConsentFlagName;
	}

	/**
	 * @return the licDowngradeConsentFlagName
	 */
	public String getLicDowngradeConsentFlagName() {
		return licDowngradeConsentFlagName;
	}

	/**
	 * @param licDowngradeConsentFlagName the licDowngradeConsentFlagName to set
	 */
	public void setLicDowngradeConsentFlagName(String licDowngradeConsentFlagName) {
		this.licDowngradeConsentFlagName = licDowngradeConsentFlagName;
	}

	/**
	 * @return the trmContractTypeName
	 */
	public String getTrmContractTypeName() {
		return trmContractTypeName;
	}

	/**
	 * @param trmContractTypeName the trmContractTypeName to set
	 */
	public void setTrmContractTypeName(String trmContractTypeName) {
		this.trmContractTypeName = trmContractTypeName;
	}

	/**
	 * @return the trmAutoAllocFlagName
	 */
	public String getTrmAutoAllocFlagName() {
		return trmAutoAllocFlagName;
	}

	/**
	 * @param trmAutoAllocFlagName the trmAutoAllocFlagName to set
	 */
	public void setTrmAutoAllocFlagName(String trmAutoAllocFlagName) {
		this.trmAutoAllocFlagName = trmAutoAllocFlagName;
	}

	/**
	 * @return the useTypeName
	 */
	public String getUseTypeName() {
		return useTypeName;
	}

	/**
	 * @param useTypeName the useTypeName to set
	 */
	public void setUseTypeName(String useTypeName) {
		this.useTypeName = useTypeName;
	}

	/**
	 * @return the licenseQtyUse
	 */
	public LicenseLineQty getLicenseQtyUse() {
		return licenseQtyUse;
	}

	/**
	 * @param licenseQtyUse the licenseQtyUse to set
	 */
	public void setLicenseQtyUse(LicenseLineQty licenseQtyUse) {
		this.licenseQtyUse = licenseQtyUse;
	}

	/**
	 * apCreateTelを取得します。
	 * @return apCreateTel
	 */
	public String getApCreateTel() {
		return apCreateTel;
	}

	/**
	 * apCreateTel
	 * @param apCreateTelを設定します。
	 */
	public void setApCreateTel(String apCreateTel) {
		this.apCreateTel = apCreateTel;
	}

	/**
	 * shisanNumを取得します。
	 * @return shisanNum
	 */
	public String getShisanNum() {
		return shisanNum;
	}

	/**
	 * shisanNum
	 * @param shisanNumを設定します。
	 */
	public void setShisanNum(String shisanNum) {
		this.shisanNum = shisanNum;
	}

	public String getContractEda() {
		return contractEda;
	}

	public void setContractEda(String contractEda) {
		this.contractEda = contractEda;
	}

	public Long getKoyunoL() {
		return koyunoL;
	}

	public void setKoyunoL(Long koyunoL) {
		this.koyunoL = koyunoL;
	}

	public Long getKoyunoF() {
		return koyunoF;
	}

	public void setKoyunoF(Long koyunoF) {
		this.koyunoF = koyunoF;
	}

	public String getShisanKbn() {
		return shisanKbn;
	}

	public void setShisanKbn(String shisanKbn) {
		this.shisanKbn = shisanKbn;
	}

	public String getGetApplicationVersion() {
		return getApplicationVersion;
	}

	public void setGetApplicationVersion(String getApplicationVersion) {
		this.getApplicationVersion = getApplicationVersion;
	}

	public List<ApLicenseLineAtt> getApLicenseLineAtt() {
		return apLicenseLineAtt;
	}

	public void setApLicenseLineAtt(List<ApLicenseLineAtt> apLicenseLineAtt) {
		this.apLicenseLineAtt = apLicenseLineAtt;
	}

	public String getAttDescription() {
		return attDescription;
	}

	public void setAttDescription(String attDescription) {
		this.attDescription = attDescription;
	}
}
