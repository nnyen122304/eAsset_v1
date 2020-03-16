/*===================================================================
 * ファイル名 : PpfsLld.java
 * 概要説明   : リース・レンタル情報照会_契約、物品共通検索条件
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-25 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.lld;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class PpfsLldSC extends PpfsLld {
	private static final long serialVersionUID = 1L;

	/*****	その他	*******/
	private String searchScopeType;					// 検索対象（1:自分の管轄部署が保有（使用）部署に設定されている資産(機器)、2:全ての資産(機器)）
	private String searchOldKykNoFlag;		//	満了済の再リース元契約番号も検索

	private Date lastymdCFrom; // 開始日(From)
	private Date lastymdCTo; // 開始日(To)
	private Date lamanryoymdCFrom; // 満了日(From)
	private Date lamanryoymdCTo; // 満了日(To)
	private Date kaiykymdAFrom; // 解約日(From)
	private Date kaiykymdATo; // 解約日(To)
	private String shuruinmAPlural; // 種類_複数
	private String bunruinmAPlural; // 分類_複数
	private String setchinmAPlural; // 代表設置場所_複数
	private String itemGroupNameAPlural; // 案件グループ_複数
	private String itemGroupCodeA;
	private String itemGroupNameA;
	private String leaseRentalHantei; // リースレンタル判定_複数

	private String includeSectionHierarchyFlag;		// 部署階層検索フラグ

	private String kykeda; // 契約枝番号
	private String applicationId; // 情報機器番号
	private String assetId; // 情報機器番号
	private String licenseId; // ﾗｲｾﾝｽ管理番号

	private String koyunoAPlural; // 固有番号_複数
	private String kyknoCPlural; // 契約番号_複数
	private String kykedaPlural; // 契約枝番号_複数
	private String shisanNumAPlural; // 資産番号_複数
	private String applicationIdPlural; // 取得申請番号_複数
	private String assetIdPlural; // 情報機器番号_複数
	private String licenseIdPlural; // ﾗｲｾﾝｽ管理番号_複数
	private String holOfficePlural; // 個別設置(使用)場所_複数

	//	現物情報検索条件
	private String holSectionCode; // 保有/使用部署
	private String holSectionName; // 保有/使用部署名
	private String holStaffCode; // 資産管理担当者
	private String holStaffName; // 資産管理担当者名
	private String holOfficeCode; // 個別設置(使用)場所
	private String holOfficeName; // 個別設置(使用)場所名
	private Integer holOfficeFloor; // 個別設置(使用)場所階
	private String useStaffCode; // 使用者
	private String useStaffName; // 使用者名

	//	経費負担検索条件
	private String costType; // 販売管理費/原価区分
	private String costDepPrjCode; // 減価償却ﾌﾟﾛｼﾞｪｸﾄｺｰﾄﾞ
	private String costDepPrjName; // 減価償却ﾌﾟﾛｼﾞｪｸﾄ名

	private String costSectionCode0; // 経費負担部署コード0
	private String costSectionName0; // 経費負担部署名0
	private String costSectionCode1; // 経費負担部署コード1
	private String costSectionName1; // 経費負担部署名1
	private String costSectionCode2; // 経費負担部署コード2
	private String costSectionName2; // 経費負担部署名2
	private String costSectionCode3; // 経費負担部署コード3
	private String costSectionName3; // 経費負担部署名3
	private String costSectionCode4; // 経費負担部署コード4
	private String costSectionName4; // 経費負担部署名4
	private String costSectionCode5; // 経費負担部署コード5
	private String costSectionName5; // 経費負担部署名5
	private String costSectionCode6; // 経費負担部署コード6
	private String costSectionName6; // 経費負担部署名6
	private String costSectionCode7; // 経費負担部署コード7
	private String costSectionName7; // 経費負担部署名7
	private String costSectionCode8; // 経費負担部署コード8
	private String costSectionName8; // 経費負担部署名8
	private String costSectionCode9; // 経費負担部署コード9
	private String costSectionName9; // 経費負担部署名9

	private String astSystemSectionDeployFlag;		// 情報システム部配備フラグ 情報機器等と同項目
	private String astManageTypePlural;				// 管理区分複数

	/****** 管理帳票用 ******/
	private String reportDistType; // 配賦
	private String reportSchUnit; // スケジュール表示単位
	private String reportSchCalcBasePeriod; // スケジュール計算基準年月
	private Integer reportSchCalcYear; // スケジュール計算年数
	private String reportLedgerType; // 出力帳簿(情報)
	private String reportPeriod; // 会計年月
	private String reportPeriodFrom; // 出力期間(From)
	private String reportPeriodTo; // 出力期間(To)
	private String reportLldOutUnit; // 出力単位

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);
		/*****	その他	*******/
		searchScopeType = (String)input.readObject();
		searchOldKykNoFlag = (String)input.readObject();

		lastymdCFrom = (Date)input.readObject();
		lastymdCTo = (Date)input.readObject();
		lamanryoymdCFrom = (Date)input.readObject();
		lamanryoymdCTo = (Date)input.readObject();
		kaiykymdAFrom = (Date)input.readObject();
		kaiykymdATo = (Date)input.readObject();
		shuruinmAPlural = (String)input.readObject();
		bunruinmAPlural = (String)input.readObject();
		setchinmAPlural = (String)input.readObject();
		itemGroupNameAPlural = (String)input.readObject();
		itemGroupCodeA = (String)input.readObject();
		itemGroupNameA = (String)input.readObject();
		leaseRentalHantei = (String)input.readObject();

		includeSectionHierarchyFlag = (String)input.readObject();

		kykeda = (String)input.readObject();
		applicationId = (String)input.readObject();
		assetId = (String)input.readObject();
		licenseId = (String)input.readObject();

		koyunoAPlural = (String)input.readObject();
		kyknoCPlural = (String)input.readObject();
		kykedaPlural = (String)input.readObject();
		shisanNumAPlural = (String)input.readObject();
		applicationIdPlural = (String)input.readObject();
		assetIdPlural = (String)input.readObject();
		licenseIdPlural = (String)input.readObject();
		holOfficePlural = (String)input.readObject();

		//	現物情報検索条件
		holSectionCode = (String)input.readObject();
		holSectionName = (String)input.readObject();
		holStaffCode = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holOfficeCode = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		holOfficeFloor = Function.getExternalInteger(input.readObject());
		useStaffCode = (String)input.readObject();
		useStaffName = (String)input.readObject();

		//	経費負担検索条件
		costType = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costDepPrjName = (String)input.readObject();

		costSectionCode0 = (String)input.readObject();
		costSectionName0 = (String)input.readObject();
		costSectionCode1 = (String)input.readObject();
		costSectionName1 = (String)input.readObject();
		costSectionCode2 = (String)input.readObject();
		costSectionName2 = (String)input.readObject();
		costSectionCode3 = (String)input.readObject();
		costSectionName3 = (String)input.readObject();
		costSectionCode4 = (String)input.readObject();
		costSectionName4 = (String)input.readObject();
		costSectionCode5 = (String)input.readObject();
		costSectionName5 = (String)input.readObject();
		costSectionCode6 = (String)input.readObject();
		costSectionName6 = (String)input.readObject();
		costSectionCode7 = (String)input.readObject();
		costSectionName7 = (String)input.readObject();
		costSectionCode8 = (String)input.readObject();
		costSectionName8 = (String)input.readObject();
		costSectionCode9 = (String)input.readObject();
		costSectionName9 = (String)input.readObject();

		astSystemSectionDeployFlag = (String)input.readObject();
		astManageTypePlural = (String)input.readObject();

		/*******  契約情報   ********/

		/*******  物品情報   ********/

		/****** 管理帳票用 ******/
		reportDistType = (String)input.readObject();
		reportSchUnit = (String)input.readObject();
		reportSchCalcBasePeriod = (String)input.readObject();
		reportSchCalcYear = Function.getExternalInteger(input.readObject());
		reportLedgerType = (String)input.readObject();
		reportPeriod = (String)input.readObject();
		reportPeriodFrom = (String)input.readObject();
		reportPeriodTo = (String)input.readObject();
		reportLldOutUnit = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		/*****	その他	*******/
		output.writeObject(searchScopeType);
		output.writeObject(searchOldKykNoFlag);

		output.writeObject(lastymdCFrom);
		output.writeObject(lastymdCTo);
		output.writeObject(lamanryoymdCFrom);
		output.writeObject(lamanryoymdCTo);
		output.writeObject(kaiykymdAFrom);
		output.writeObject(kaiykymdATo);
		output.writeObject(shuruinmAPlural);
		output.writeObject(bunruinmAPlural);
		output.writeObject(setchinmAPlural);
		output.writeObject(itemGroupNameAPlural);
		output.writeObject(itemGroupCodeA);
		output.writeObject(itemGroupNameA);
		output.writeObject(leaseRentalHantei);

		output.writeObject(includeSectionHierarchyFlag);

		output.writeObject(kykeda);
		output.writeObject(applicationId);
		output.writeObject(assetId);
		output.writeObject(licenseId);

		output.writeObject(koyunoAPlural);
		output.writeObject(kyknoCPlural);
		output.writeObject(kykedaPlural);
		output.writeObject(shisanNumAPlural);
		output.writeObject(applicationIdPlural);
		output.writeObject(assetIdPlural);
		output.writeObject(licenseIdPlural);
		output.writeObject(holOfficePlural);

		//	現物情報検索条件
		output.writeObject(holSectionCode);
		output.writeObject(holSectionName);
		output.writeObject(holStaffCode);
		output.writeObject(holStaffName);
		output.writeObject(holOfficeCode);
		output.writeObject(holOfficeName);
		output.writeObject(holOfficeFloor);
		output.writeObject(useStaffCode);
		output.writeObject(useStaffName);

		//	経費負担検索条件
		output.writeObject(costType);
		output.writeObject(costDepPrjCode);
		output.writeObject(costDepPrjName);

		output.writeObject(costSectionCode0);
		output.writeObject(costSectionName0);
		output.writeObject(costSectionCode1);
		output.writeObject(costSectionName1);
		output.writeObject(costSectionCode2);
		output.writeObject(costSectionName2);
		output.writeObject(costSectionCode3);
		output.writeObject(costSectionName3);
		output.writeObject(costSectionCode4);
		output.writeObject(costSectionName4);
		output.writeObject(costSectionCode5);
		output.writeObject(costSectionName5);
		output.writeObject(costSectionCode6);
		output.writeObject(costSectionName6);
		output.writeObject(costSectionCode7);
		output.writeObject(costSectionName7);
		output.writeObject(costSectionCode8);
		output.writeObject(costSectionName8);
		output.writeObject(costSectionCode9);
		output.writeObject(costSectionName9);

		output.writeObject(astSystemSectionDeployFlag);
		output.writeObject(astManageTypePlural);

		/*******  契約情報   ********/

		/*******  物品情報   ********/

		/****** 管理帳票用 ******/
		output.writeObject(reportDistType);
		output.writeObject(reportSchUnit);
		output.writeObject(reportSchCalcBasePeriod);
		output.writeObject(reportSchCalcYear);
		output.writeObject(reportLedgerType);
		output.writeObject(reportPeriod);
		output.writeObject(reportPeriodFrom);
		output.writeObject(reportPeriodTo);
		output.writeObject(reportLldOutUnit);

	}

	/**
	 * searchScopeTypeを取得します。
	 * @return searchScopeType
	 */
	public String getSearchScopeType() {
		return searchScopeType;
	}

	/**
	 * searchScopeType
	 * @param searchScopeTypeを設定します。
	 */
	public void setSearchScopeType(String searchScopeType) {
		this.searchScopeType = searchScopeType;
	}

	/**
	 * lastymdCFromを取得します。
	 * @return lastymdCFrom
	 */
	public Date getLastymdCFrom() {
		return lastymdCFrom;
	}

	/**
	 * lastymdCFrom
	 * @param lastymdCFromを設定します。
	 */
	public void setLastymdCFrom(Date lastymdCFrom) {
		this.lastymdCFrom = lastymdCFrom;
	}

	/**
	 * lastymdCToを取得します。
	 * @return lastymdCTo
	 */
	public Date getLastymdCTo() {
		return lastymdCTo;
	}

	/**
	 * lastymdCTo
	 * @param lastymdCToを設定します。
	 */
	public void setLastymdCTo(Date lastymdCTo) {
		this.lastymdCTo = lastymdCTo;
	}

	/**
	 * lamanryoymdCFromを取得します。
	 * @return lamanryoymdCFrom
	 */
	public Date getLamanryoymdCFrom() {
		return lamanryoymdCFrom;
	}

	/**
	 * lamanryoymdCFrom
	 * @param lamanryoymdCFromを設定します。
	 */
	public void setLamanryoymdCFrom(Date lamanryoymdCFrom) {
		this.lamanryoymdCFrom = lamanryoymdCFrom;
	}

	/**
	 * lamanryoymdCToを取得します。
	 * @return lamanryoymdCTo
	 */
	public Date getLamanryoymdCTo() {
		return lamanryoymdCTo;
	}

	/**
	 * lamanryoymdCTo
	 * @param lamanryoymdCToを設定します。
	 */
	public void setLamanryoymdCTo(Date lamanryoymdCTo) {
		this.lamanryoymdCTo = lamanryoymdCTo;
	}

	/**
	 * shuruinmAPluralを取得します。
	 * @return shuruinmAPlural
	 */
	public String getShuruinmAPlural() {
		return shuruinmAPlural;
	}

	/**
	 * shuruinmAPlural
	 * @param shuruinmAPluralを設定します。
	 */
	public void setShuruinmAPlural(String shuruinmAPlural) {
		this.shuruinmAPlural = shuruinmAPlural;
	}

	/**
	 * bunruinmAPluralを取得します。
	 * @return bunruinmAPlural
	 */
	public String getBunruinmAPlural() {
		return bunruinmAPlural;
	}

	/**
	 * bunruinmAPlural
	 * @param bunruinmAPluralを設定します。
	 */
	public void setBunruinmAPlural(String bunruinmAPlural) {
		this.bunruinmAPlural = bunruinmAPlural;
	}

	/**
	 * setchinmAPluralを取得します。
	 * @return setchinmAPlural
	 */
	public String getSetchinmAPlural() {
		return setchinmAPlural;
	}

	/**
	 * setchinmAPlural
	 * @param setchinmAPluralを設定します。
	 */
	public void setSetchinmAPlural(String setchinmAPlural) {
		this.setchinmAPlural = setchinmAPlural;
	}

	/**
	 * itemGroupNameAPluralを取得します。
	 * @return itemGroupNameAPlural
	 */
	public String getItemGroupNameAPlural() {
		return itemGroupNameAPlural;
	}

	/**
	 * itemGroupNameAPlural
	 * @param itemGroupNameAPluralを設定します。
	 */
	public void setItemGroupNameAPlural(String itemGroupNameAPlural) {
		this.itemGroupNameAPlural = itemGroupNameAPlural;
	}

	/**
	 * applicationIdPluralを取得します。
	 * @return applicationIdPlural
	 */
	public String getApplicationIdPlural() {
		return applicationIdPlural;
	}

	/**
	 * applicationIdPlural
	 * @param applicationIdPluralを設定します。
	 */
	public void setApplicationIdPlural(String applicationIdPlural) {
		this.applicationIdPlural = applicationIdPlural;
	}

	/**
	 * itemGroupCodeAを取得します。
	 * @return itemGroupCodeA
	 */
	public String getItemGroupCodeA() {
		return itemGroupCodeA;
	}

	/**
	 * itemGroupCodeA
	 * @param itemGroupCodeAを設定します。
	 */
	public void setItemGroupCodeA(String itemGroupCodeA) {
		this.itemGroupCodeA = itemGroupCodeA;
	}

	/**
	 * itemGroupNameAを取得します。
	 * @return itemGroupNameA
	 */
	public String getItemGroupNameA() {
		return itemGroupNameA;
	}

	/**
	 * itemGroupNameA
	 * @param itemGroupNameAを設定します。
	 */
	public void setItemGroupNameA(String itemGroupNameA) {
		this.itemGroupNameA = itemGroupNameA;
	}

	/**
	 * leaseRentalHanteiを取得します。
	 * @return leaseRentalHantei
	 */
	public String getLeaseRentalHantei() {
		return leaseRentalHantei;
	}

	/**
	 * leaseRentalHantei
	 * @param leaseRentalHanteiを設定します。
	 */
	public void setLeaseRentalHantei(String leaseRentalHantei) {
		this.leaseRentalHantei = leaseRentalHantei;
	}

	/**
	 * applicationIdを取得します。
	 * @return applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}

	/**
	 * applicationId
	 * @param applicationIdを設定します。
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * asssetIdを取得します。
	 * @return asssetId
	 */
	public String getAssetId() {
		return assetId;
	}

	/**
	 * asssetId
	 * @param asssetIdを設定します。
	 */
	public void setAssetId(String assetId) {
		this.assetId = assetId;
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
	 * assetIdPluralを取得します。
	 * @return assetIdPlural
	 */
	public String getAssetIdPlural() {
		return assetIdPlural;
	}

	/**
	 * assetIdPlural
	 * @param assetIdPluralを設定します。
	 */
	public void setAssetIdPlural(String assetIdPlural) {
		this.assetIdPlural = assetIdPlural;
	}

	/**
	 * licenseIdPluralを取得します。
	 * @return licenseIdPlural
	 */
	public String getLicenseIdPlural() {
		return licenseIdPlural;
	}

	/**
	 * licenseIdPlural
	 * @param licenseIdPluralを設定します。
	 */
	public void setLicenseIdPlural(String licenseIdPlural) {
		this.licenseIdPlural = licenseIdPlural;
	}

	/**
	 * holSectionCodeを取得します。
	 * @return holSectionCode
	 */
	public String getHolSectionCode() {
		return holSectionCode;
	}

	/**
	 * holSectionCode
	 * @param holSectionCodeを設定します。
	 */
	public void setHolSectionCode(String holSectionCode) {
		this.holSectionCode = holSectionCode;
	}

	/**
	 * holSectionNameを取得します。
	 * @return holSectionName
	 */
	public String getHolSectionName() {
		return holSectionName;
	}

	/**
	 * holSectionName
	 * @param holSectionNameを設定します。
	 */
	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}

	/**
	 * holStaffCodeを取得します。
	 * @return holStaffCode
	 */
	public String getHolStaffCode() {
		return holStaffCode;
	}

	/**
	 * holStaffCode
	 * @param holStaffCodeを設定します。
	 */
	public void setHolStaffCode(String holStaffCode) {
		this.holStaffCode = holStaffCode;
	}

	/**
	 * holStaffNameを取得します。
	 * @return holStaffName
	 */
	public String getHolStaffName() {
		return holStaffName;
	}

	/**
	 * holStaffName
	 * @param holStaffNameを設定します。
	 */
	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}

	/**
	 * holOfficeCodeを取得します。
	 * @return holOfficeCode
	 */
	public String getHolOfficeCode() {
		return holOfficeCode;
	}

	/**
	 * holOfficeCode
	 * @param holOfficeCodeを設定します。
	 */
	public void setHolOfficeCode(String holOfficeCode) {
		this.holOfficeCode = holOfficeCode;
	}

	/**
	 * holOfficeNameを取得します。
	 * @return holOfficeName
	 */
	public String getHolOfficeName() {
		return holOfficeName;
	}

	/**
	 * holOfficeName
	 * @param holOfficeNameを設定します。
	 */
	public void setHolOfficeName(String holOfficeName) {
		this.holOfficeName = holOfficeName;
	}

	/**
	 * holOfficeFloorを取得します。
	 * @return holOfficeFloor
	 */
	public Integer getHolOfficeFloor() {
		return holOfficeFloor;
	}

	/**
	 * holOfficeFloor
	 * @param holOfficeFloorを設定します。
	 */
	public void setHolOfficeFloor(Integer holOfficeFloor) {
		this.holOfficeFloor = holOfficeFloor;
	}

	/**
	 * useStaffCodeを取得します。
	 * @return useStaffCode
	 */
	public String getUseStaffCode() {
		return useStaffCode;
	}

	/**
	 * useStaffCode
	 * @param useStaffCodeを設定します。
	 */
	public void setUseStaffCode(String useStaffCode) {
		this.useStaffCode = useStaffCode;
	}

	/**
	 * useStaffNameを取得します。
	 * @return useStaffName
	 */
	public String getUseStaffName() {
		return useStaffName;
	}

	/**
	 * useStaffName
	 * @param useStaffNameを設定します。
	 */
	public void setUseStaffName(String useStaffName) {
		this.useStaffName = useStaffName;
	}

	/**
	 * holOfficePluralを取得します。
	 * @return holOfficePlural
	 */
	public String getHolOfficePlural() {
		return holOfficePlural;
	}

	/**
	 * holOfficePlural
	 * @param holOfficePluralを設定します。
	 */
	public void setHolOfficePlural(String holOfficePlural) {
		this.holOfficePlural = holOfficePlural;
	}

	/**
	 * shisanNumAPluralを取得します。
	 * @return shisanNumAPlural
	 */
	public String getShisanNumAPlural() {
		return shisanNumAPlural;
	}

	/**
	 * shisanNumAPlural
	 * @param shisanNumAPluralを設定します。
	 */
	public void setShisanNumAPlural(String shisanNumAPlural) {
		this.shisanNumAPlural = shisanNumAPlural;
	}

	/**
	 * kaiykymdAFromを取得します。
	 * @return kaiykymdAFrom
	 */
	public Date getKaiykymdAFrom() {
		return kaiykymdAFrom;
	}

	/**
	 * kaiykymdAFrom
	 * @param kaiykymdAFromを設定します。
	 */
	public void setKaiykymdAFrom(Date kaiykymdAFrom) {
		this.kaiykymdAFrom = kaiykymdAFrom;
	}

	/**
	 * kaiykymdAToを取得します。
	 * @return kaiykymdATo
	 */
	public Date getKaiykymdATo() {
		return kaiykymdATo;
	}

	/**
	 * kaiykymdATo
	 * @param kaiykymdAToを設定します。
	 */
	public void setKaiykymdATo(Date kaiykymdATo) {
		this.kaiykymdATo = kaiykymdATo;
	}

	/**
	 * costTypeを取得します。
	 * @return costType
	 */
	public String getCostType() {
		return costType;
	}

	/**
	 * costType
	 * @param costTypeを設定します。
	 */
	public void setCostType(String costType) {
		this.costType = costType;
	}

	/**
	 * costDepPrjCodeを取得します。
	 * @return costDepPrjCode
	 */
	public String getCostDepPrjCode() {
		return costDepPrjCode;
	}

	/**
	 * costDepPrjCode
	 * @param costDepPrjCodeを設定します。
	 */
	public void setCostDepPrjCode(String costDepPrjCode) {
		this.costDepPrjCode = costDepPrjCode;
	}

	/**
	 * costDepPrjNameを取得します。
	 * @return costDepPrjName
	 */
	public String getCostDepPrjName() {
		return costDepPrjName;
	}

	/**
	 * costDepPrjName
	 * @param costDepPrjNameを設定します。
	 */
	public void setCostDepPrjName(String costDepPrjName) {
		this.costDepPrjName = costDepPrjName;
	}

	/**
	 * includeSectionHierarchyFlagを取得します。
	 * @return includeSectionHierarchyFlag
	 */
	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	/**
	 * includeSectionHierarchyFlag
	 * @param includeSectionHierarchyFlagを設定します。
	 */
	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}


	/**
	 * kykedaを取得します。
	 * @return kykeda
	 */
	public String getKykeda() {
		return kykeda;
	}

	/**
	 * kykeda
	 * @param kykedaを設定します。
	 */
	public void setKykeda(String kykeda) {
		this.kykeda = kykeda;
	}

	/**
	 * kykedaPluralを取得します。
	 * @return kykedaPlural
	 */
	public String getKykedaPlural() {
		return kykedaPlural;
	}

	/**
	 * kykedaPlural
	 * @param kykedaPluralを設定します。
	 */
	public void setKykedaPlural(String kykedaPlural) {
		this.kykedaPlural = kykedaPlural;
	}

	/**
	 * kyknoCPluralを取得します。
	 * @return kyknoCPlural
	 */
	public String getKyknoCPlural() {
		return kyknoCPlural;
	}

	/**
	 * kyknoCPlural
	 * @param kyknoCPluralを設定します。
	 */
	public void setKyknoCPlural(String kyknoCPlural) {
		this.kyknoCPlural = kyknoCPlural;
	}

	/**
	 * searchOldKykNoFlagを取得します。
	 * @return searchOldKykNoFlag
	 */
	public String getSearchOldKykNoFlag() {
		return searchOldKykNoFlag;
	}

	/**
	 * searchOldKykNoFlag
	 * @param searchOldKykNoFlagを設定します。
	 */
	public void setSearchOldKykNoFlag(String searchOldKykNoFlag) {
		this.searchOldKykNoFlag = searchOldKykNoFlag;
	}

	/**
	 * @return the reportDistType
	 */
	public String getReportDistType() {
		return reportDistType;
	}

	/**
	 * @param reportDistType the reportDistType to set
	 */
	public void setReportDistType(String reportDistType) {
		this.reportDistType = reportDistType;
	}

	/**
	 * @return the reportSchUnit
	 */
	public String getReportSchUnit() {
		return reportSchUnit;
	}

	/**
	 * @param reportSchUnit the reportSchUnit to set
	 */
	public void setReportSchUnit(String reportSchUnit) {
		this.reportSchUnit = reportSchUnit;
	}

	/**
	 * @return the reportLedgerType
	 */
	public String getReportLedgerType() {
		return reportLedgerType;
	}

	/**
	 * @param reportLedgerType the reportLedgerType to set
	 */
	public void setReportLedgerType(String reportLedgerType) {
		this.reportLedgerType = reportLedgerType;
	}

	/**
	 * @return the reportPeriodFrom
	 */
	public String getReportPeriodFrom() {
		return reportPeriodFrom;
	}

	/**
	 * @param reportPeriodFrom the reportPeriodFrom to set
	 */
	public void setReportPeriodFrom(String reportPeriodFrom) {
		this.reportPeriodFrom = reportPeriodFrom;
	}

	/**
	 * @return the reportPeriodTo
	 */
	public String getReportPeriodTo() {
		return reportPeriodTo;
	}

	/**
	 * @param reportPeriodTo the reportPeriodTo to set
	 */
	public void setReportPeriodTo(String reportPeriodTo) {
		this.reportPeriodTo = reportPeriodTo;
	}

	/**
	 * @return the reportLldOutUnit
	 */
	public String getReportLldOutUnit() {
		return reportLldOutUnit;
	}

	/**
	 * @param reportLldOutUnit the reportLldOutUnit to set
	 */
	public void setReportLldOutUnit(String reportLldOutUnit) {
		this.reportLldOutUnit = reportLldOutUnit;
	}

	/**
	 * @return the reportPeriod
	 */
	public String getReportPeriod() {
		return reportPeriod;
	}

	/**
	 * @param reportPeriod the reportPeriod to set
	 */
	public void setReportPeriod(String reportPeriod) {
		this.reportPeriod = reportPeriod;
	}

	public String getKoyunoAPlural() {
		return koyunoAPlural;
	}

	public void setKoyunoAPlural(String koyunoAPlural) {
		this.koyunoAPlural = koyunoAPlural;
	}

	/**
	 * @return the reportSchCalcBasePeriod
	 */
	public String getReportSchCalcBasePeriod() {
		return reportSchCalcBasePeriod;
	}

	/**
	 * @param reportSchCalcBasePeriod the reportSchCalcBasePeriod to set
	 */
	public void setReportSchCalcBasePeriod(String reportSchCalcBasePeriod) {
		this.reportSchCalcBasePeriod = reportSchCalcBasePeriod;
	}

	/**
	 * @return the reportSchCalcYear
	 */
	public Integer getReportSchCalcYear() {
		return reportSchCalcYear;
	}

	/**
	 * @param reportSchCalcYear the reportSchCalcYear to set
	 */
	public void setReportSchCalcYear(Integer reportSchCalcYear) {
		this.reportSchCalcYear = reportSchCalcYear;
	}

	/**
	 * @return the astSystemSectionDeployFlag
	 */
	public String getAstSystemSectionDeployFlag() {
		return astSystemSectionDeployFlag;
	}

	/**
	 * @param astSystemSectionDeployFlag the astSystemSectionDeployFlag to set
	 */
	public void setAstSystemSectionDeployFlag(String astSystemSectionDeployFlag) {
		this.astSystemSectionDeployFlag = astSystemSectionDeployFlag;
	}

	/**
	 * @return the astManageTypePlural
	 */
	public String getAstManageTypePlural() {
		return astManageTypePlural;
	}

	/**
	 * @param astManageTypePlural the astManageTypePlural to set
	 */
	public void setAstManageTypePlural(String astManageTypePlural) {
		this.astManageTypePlural = astManageTypePlural;
	}

	public String getCostSectionCode0() {
		return costSectionCode0;
	}

	public void setCostSectionCode0(String costSectionCode0) {
		this.costSectionCode0 = costSectionCode0;
	}

	public String getCostSectionName0() {
		return costSectionName0;
	}

	public void setCostSectionName0(String costSectionName0) {
		this.costSectionName0 = costSectionName0;
	}

	public String getCostSectionCode1() {
		return costSectionCode1;
	}

	public void setCostSectionCode1(String costSectionCode1) {
		this.costSectionCode1 = costSectionCode1;
	}

	public String getCostSectionName1() {
		return costSectionName1;
	}

	public void setCostSectionName1(String costSectionName1) {
		this.costSectionName1 = costSectionName1;
	}

	public String getCostSectionCode2() {
		return costSectionCode2;
	}

	public void setCostSectionCode2(String costSectionCode2) {
		this.costSectionCode2 = costSectionCode2;
	}

	public String getCostSectionName2() {
		return costSectionName2;
	}

	public void setCostSectionName2(String costSectionName2) {
		this.costSectionName2 = costSectionName2;
	}

	public String getCostSectionCode3() {
		return costSectionCode3;
	}

	public void setCostSectionCode3(String costSectionCode3) {
		this.costSectionCode3 = costSectionCode3;
	}

	public String getCostSectionName3() {
		return costSectionName3;
	}

	public void setCostSectionName3(String costSectionName3) {
		this.costSectionName3 = costSectionName3;
	}

	public String getCostSectionCode4() {
		return costSectionCode4;
	}

	public void setCostSectionCode4(String costSectionCode4) {
		this.costSectionCode4 = costSectionCode4;
	}

	public String getCostSectionName4() {
		return costSectionName4;
	}

	public void setCostSectionName4(String costSectionName4) {
		this.costSectionName4 = costSectionName4;
	}

	public String getCostSectionCode5() {
		return costSectionCode5;
	}

	public void setCostSectionCode5(String costSectionCode5) {
		this.costSectionCode5 = costSectionCode5;
	}

	public String getCostSectionName5() {
		return costSectionName5;
	}

	public void setCostSectionName5(String costSectionName5) {
		this.costSectionName5 = costSectionName5;
	}

	public String getCostSectionCode6() {
		return costSectionCode6;
	}

	public void setCostSectionCode6(String costSectionCode6) {
		this.costSectionCode6 = costSectionCode6;
	}

	public String getCostSectionName6() {
		return costSectionName6;
	}

	public void setCostSectionName6(String costSectionName6) {
		this.costSectionName6 = costSectionName6;
	}

	public String getCostSectionCode7() {
		return costSectionCode7;
	}

	public void setCostSectionCode7(String costSectionCode7) {
		this.costSectionCode7 = costSectionCode7;
	}

	public String getCostSectionName7() {
		return costSectionName7;
	}

	public void setCostSectionName7(String costSectionName7) {
		this.costSectionName7 = costSectionName7;
	}

	public String getCostSectionCode8() {
		return costSectionCode8;
	}

	public void setCostSectionCode8(String costSectionCode8) {
		this.costSectionCode8 = costSectionCode8;
	}

	public String getCostSectionName8() {
		return costSectionName8;
	}

	public void setCostSectionName8(String costSectionName8) {
		this.costSectionName8 = costSectionName8;
	}

	public String getCostSectionCode9() {
		return costSectionCode9;
	}

	public void setCostSectionCode9(String costSectionCode9) {
		this.costSectionCode9 = costSectionCode9;
	}

	public String getCostSectionName9() {
		return costSectionName9;
	}

	public void setCostSectionName9(String costSectionName9) {
		this.costSectionName9 = costSectionName9;
	}


}
