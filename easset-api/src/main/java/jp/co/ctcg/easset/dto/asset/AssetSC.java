/*===================================================================
 * ファイル名 : AssetSC.java
 * 概要説明   : 情報機器検索条件
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

import jp.co.ctcg.easset.rest.MappingUtils;
import lombok.ToString;


@ToString(callSuper = true)
public class AssetSC extends Asset {
	private static final long serialVersionUID = 1L;

	private String searchScopeType;					// 検索対象（1:自分が使用者となっている資産(機器)、2:全ての資産(機器)）
	private String holCompanyCodeALSection;			// 部署アクセス権限用保有部署

	private String assetIdPlural;					// 情報機器管理番号複数
	private String parentAssetIdPlural;				// 親情報機器管理番号複数
	private String levelAssetFlag;					// 関連（親子）データも同時に検索する
	private String getApplicationIdPlural;			// 取得申請書番号複数
	private String registApplicationIdPlural;		// 登録申請書番号複数
	private String contractNumPlural;				// 契約番号複数

	private Date registDateFrom;					// 登録日From
	private Date registDateTo;						// 登録日To
	private Date updateDateFrom;					// 更新日From
	private Date updateDateTo;						// 更新日To
	private Date deleteDateFrom;					// 抹消日From
	private Date deleteDateTo;						// 抹消日To

	private String shisanNumPlural;					// 資産番号複数
	private String astCategory2Plural;				// 資産（機器）分類複数
	private String astSerialCodePlural;				// シリアル番号複数
	private String astOirPlural;					// OIR番号複数
	private String astAssetTypePlural;				// 資産区分複数
	private String astManageTypePlural;				// 管理区分複数

	private String allCompanyFlag;					// 他会社保有の資産（機器）を検索する
	private String includeSectionHierarchyFlag;		// 部署階層検索フラグ
	private String holOfficePlural;					// 資設置（使用）場所複数

	private String netMacAddress1;					// MACアドレス1
	private String netMacAddress2;					// MACアドレス2
	private String netMacAddress3;					// MACアドレス3
	private String netMacAddress4;					// MACアドレス4
	private String netMacAddress5;					// MACアドレス5
	private String netMacAddress6;					// MACアドレス6
	private String netIpAddress1;					// IPアドレス1
	private String netIpAddress2;					// IPアドレス2
	private String netIpAddress3;					// IPアドレス3
	private String netIpAddress4;					// IPアドレス4
	private String netHostNamePlural;				// ホスト名複数

	private String dscAttribute;					// 管理項目

	private String invSealIssueStatus;				// シール発行（0：未発行、1：発行済）
	private Date invSealIssueDateFrom;				// シール発行日From
	private Date invSealIssueDateTo;				// シール発行日To

	private String invFlag;							// 棚卸実施状況（0：未実施、1：実施済）
	private Date invSearchDateFrom;					// 検索対象期間From
	private Date invSearchDateTo;					// 検索対象期間To

	private Date mntContractStartDateFrom;			// 保守契約開始日From
	private Date mntContractStartDateTo;			// 保守契約開始日To
	private Date mntContractEndDateFrom;			// 保守契約終了日From
	private Date mntContractEndDateTo;				// 保守契約終了日To

	// 情報機器登録申請検索条件
	private Date apDateFrom;						// 申請日From
	private Date apDateTo;							// 申請日To
	private String notCompleteFlag;					// 登録が完了している申請書を除外

	private String eappIdPlural;					// e申請書類ID複数

	// CSVダウンロード
	private String dowloadLineItem;					// 明細出力
	//	e申請検索判定フラグ
	private String searchEapp;

	// 固定資産、物件
	private String contractEdaPlural;				// 契約枝番複数
	private String relationShisanNumFlag;			// 関連資産も同時に検索する

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		searchScopeType = (String)input.readObject();
		holCompanyCodeALSection = (String)input.readObject();

		assetIdPlural = (String)input.readObject();
		parentAssetIdPlural = (String)input.readObject();
		levelAssetFlag = (String)input.readObject();
		getApplicationIdPlural = (String)input.readObject();
		registApplicationIdPlural = (String)input.readObject();
		contractNumPlural = (String)input.readObject();

		registDateFrom = (Date)input.readObject();
		registDateTo = (Date)input.readObject();
		updateDateFrom = (Date)input.readObject();
		updateDateTo = (Date)input.readObject();
		deleteDateFrom = (Date)input.readObject();
		deleteDateTo = (Date)input.readObject();

		shisanNumPlural = (String)input.readObject();
		astCategory2Plural = (String)input.readObject();
		astSerialCodePlural = (String)input.readObject();
		astOirPlural = (String)input.readObject();
		astAssetTypePlural = (String)input.readObject();
		astManageTypePlural = (String)input.readObject();

		allCompanyFlag = (String)input.readObject();
		includeSectionHierarchyFlag = (String)input.readObject();
		holOfficePlural = (String)input.readObject();

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
		netHostNamePlural = (String)input.readObject();

		dscAttribute = (String)input.readObject();

		invSealIssueStatus = (String)input.readObject();
		invSealIssueDateFrom = (Date)input.readObject();
		invSealIssueDateTo = (Date)input.readObject();

		invFlag = (String)input.readObject();
		invSearchDateFrom = (Date)input.readObject();
		invSearchDateTo = (Date)input.readObject();

		mntContractStartDateFrom = (Date)input.readObject();
		mntContractStartDateTo = (Date)input.readObject();
		mntContractEndDateFrom = (Date)input.readObject();
		mntContractEndDateTo = (Date)input.readObject();

		apDateFrom = (Date)input.readObject();
		apDateTo = (Date)input.readObject();
		notCompleteFlag = (String)input.readObject();

		eappIdPlural = (String)input.readObject();

		dowloadLineItem = (String)input.readObject();

		// 固定資産、物件
		contractEdaPlural = (String)input.readObject();
		relationShisanNumFlag = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		output.writeObject(searchScopeType);
		output.writeObject(holCompanyCodeALSection);

		output.writeObject(assetIdPlural);
		output.writeObject(parentAssetIdPlural);
		output.writeObject(levelAssetFlag);
		output.writeObject(getApplicationIdPlural);
		output.writeObject(registApplicationIdPlural);
		output.writeObject(contractNumPlural);

		output.writeObject(registDateFrom);
		output.writeObject(registDateTo);
		output.writeObject(updateDateFrom);
		output.writeObject(updateDateTo);
		output.writeObject(deleteDateFrom);
		output.writeObject(deleteDateTo);

		output.writeObject(shisanNumPlural);
		output.writeObject(astCategory2Plural);
		output.writeObject(astSerialCodePlural);
		output.writeObject(astOirPlural);
		output.writeObject(astAssetTypePlural);
		output.writeObject(astManageTypePlural);

		output.writeObject(allCompanyFlag);
		output.writeObject(includeSectionHierarchyFlag);
		output.writeObject(holOfficePlural);

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
		output.writeObject(netHostNamePlural);

		output.writeObject(dscAttribute);

		output.writeObject(invSealIssueStatus);
		output.writeObject(invSealIssueDateFrom);
		output.writeObject(invSealIssueDateTo);

		output.writeObject(invFlag);
		output.writeObject(invSearchDateFrom);
		output.writeObject(invSearchDateTo);

		output.writeObject(mntContractStartDateFrom);
		output.writeObject(mntContractStartDateTo);
		output.writeObject(mntContractEndDateFrom);
		output.writeObject(mntContractEndDateTo);

		output.writeObject(apDateFrom);
		output.writeObject(apDateTo);
		output.writeObject(notCompleteFlag);

		output.writeObject(eappIdPlural);

		output.writeObject(dowloadLineItem);

		// 固定資産、物件
		output.writeObject(contractEdaPlural);
		output.writeObject(relationShisanNumFlag);
	}

	public String getSearchScopeType() {
		return searchScopeType;
	}

	public void setSearchScopeType(String searchScopeType) {
		this.searchScopeType = searchScopeType;
	}

	public String getHolCompanyCodeALSection() {
		return holCompanyCodeALSection;
	}

	public void setHolCompanyCodeALSection(String holCompanyCodeALSection) {
		this.holCompanyCodeALSection = holCompanyCodeALSection;
	}

	public String getAssetIdPlural() {
		return assetIdPlural;
	}

	public void setAssetIdPlural(String assetIdPlural) {
		this.assetIdPlural = assetIdPlural;
	}

	public String getLevelAssetFlag() {
		return levelAssetFlag;
	}

	public void setLevelAssetFlag(String levelAssetFlag) {
		this.levelAssetFlag = levelAssetFlag;
	}

	public String getGetApplicationIdPlural() {
		return getApplicationIdPlural;
	}

	public void setGetApplicationIdPlural(String getApplicationIdPlural) {
		this.getApplicationIdPlural = getApplicationIdPlural;
	}

	public String getRegistApplicationIdPlural() {
		return registApplicationIdPlural;
	}

	public void setRegistApplicationIdPlural(String registApplicationIdPlural) {
		this.registApplicationIdPlural = registApplicationIdPlural;
	}

	public String getContractNumPlural() {
		return contractNumPlural;
	}

	public void setContractNumPlural(String contractNumPlural) {
		this.contractNumPlural = contractNumPlural;
	}

	public Date getRegistDateFrom() {
		return registDateFrom;
	}

	public void setRegistDateFrom(Date registDateFrom) {
		this.registDateFrom = registDateFrom;
	}

	public Date getRegistDateTo() {
		return registDateTo;
	}

	public void setRegistDateTo(Date registDateTo) {
		this.registDateTo = registDateTo;
	}

	/**
	 * shisanNumPluralを取得します。
	 * @return shisanNumPlural
	 */
	public String getShisanNumPlural() {
		return shisanNumPlural;
	}

	/**
	 * shisanNumPlural
	 * @param shisanNumPluralを設定します。
	 */
	public void setShisanNumPlural(String shisanNumPlural) {
		this.shisanNumPlural = shisanNumPlural;
	}

	public String getAstCategory2Plural() {
		return astCategory2Plural;
	}

	public void setAstCategory2Plural(String astCategory2Plural) {
		this.astCategory2Plural = astCategory2Plural;
	}

	public String getAstSerialCodePlural() {
		return astSerialCodePlural;
	}

	public void setAstSerialCodePlural(String astSerialCodePlural) {
		this.astSerialCodePlural = astSerialCodePlural;
	}

	public String getAstOirPlural() {
		return astOirPlural;
	}

	/**
	 * @return the astAssetTypePlural
	 */
	public String getAstAssetTypePlural() {
		return astAssetTypePlural;
	}

	/**
	 * @param astAssetTypePlural the astAssetTypePlural to set
	 */
	public void setAstAssetTypePlural(String astAssetTypePlural) {
		this.astAssetTypePlural = astAssetTypePlural;
	}

	public void setAstOirPlural(String astOirPlural) {
		this.astOirPlural = astOirPlural;
	}

	public String getAllCompanyFlag() {
		return allCompanyFlag;
	}

	public void setAllCompanyFlag(String allCompanyFlag) {
		this.allCompanyFlag = allCompanyFlag;
	}

	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}

	public String getHolOfficePlural() {
		return holOfficePlural;
	}

	public void setHolOfficePlural(String holOfficePlural) {
		this.holOfficePlural = holOfficePlural;
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

	public String getInvSealIssueStatus() {
		return invSealIssueStatus;
	}

	public void setInvSealIssueStatus(String invSealIssueStatus) {
		this.invSealIssueStatus = invSealIssueStatus;
	}

	public Date getInvSealIssueDateFrom() {
		return invSealIssueDateFrom;
	}

	public void setInvSealIssueDateFrom(Date invSealIssueDateFrom) {
		this.invSealIssueDateFrom = invSealIssueDateFrom;
	}

	public Date getInvSealIssueDateTo() {
		return invSealIssueDateTo;
	}

	public void setInvSealIssueDateTo(Date invSealIssueDateTo) {
		this.invSealIssueDateTo = invSealIssueDateTo;
	}

	public String getInvFlag() {
		return invFlag;
	}

	public void setInvFlag(String invFlag) {
		this.invFlag = invFlag;
	}

	public Date getInvSearchDateFrom() {
		return invSearchDateFrom;
	}

	public void setInvSearchDateFrom(Date invSearchDateFrom) {
		this.invSearchDateFrom = invSearchDateFrom;
	}

	public Date getInvSearchDateTo() {
		return invSearchDateTo;
	}

	public void setInvSearchDateTo(Date invSearchDateTo) {
		this.invSearchDateTo = invSearchDateTo;
	}

	public String getDscAttribute() {
		return dscAttribute;
	}

	public void setDscAttribute(String dscAttribute) {
		this.dscAttribute = dscAttribute;
	}

	public String getNetHostNamePlural() {
		return netHostNamePlural;
	}

	public void setNetHostNamePlural(String netHostNamePlural) {
		this.netHostNamePlural = netHostNamePlural;
	}

	public Date getApDateFrom() {
		return apDateFrom;
	}

	public void setApDateFrom(Date apDateFrom) {
		this.apDateFrom = apDateFrom;
	}

	public Date getApDateTo() {
		return apDateTo;
	}

	public void setApDateTo(Date apDateTo) {
		this.apDateTo = apDateTo;
	}

	public String getNotCompleteFlag() {
		return notCompleteFlag;
	}

	public void setNotCompleteFlag(String notCompleteFlag) {
		this.notCompleteFlag = notCompleteFlag;
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

	public String getDowloadLineItem() {
		return dowloadLineItem;
	}

	public void setDowloadLineItem(String dowloadLineItem) {
		this.dowloadLineItem = dowloadLineItem;
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

	/**
	 * @return the updateDateFrom
	 */
	public Date getUpdateDateFrom() {
		return updateDateFrom;
	}

	/**
	 * @param updateDateFrom the updateDateFrom to set
	 */
	public void setUpdateDateFrom(Date updateDateFrom) {
		this.updateDateFrom = updateDateFrom;
	}

	/**
	 * @return the updateDateTo
	 */
	public Date getUpdateDateTo() {
		return updateDateTo;
	}

	/**
	 * @param updateDateTo the updateDateTo to set
	 */
	public void setUpdateDateTo(Date updateDateTo) {
		this.updateDateTo = updateDateTo;
	}

	/**
	 * @return the deleteDateFrom
	 */
	public Date getDeleteDateFrom() {
		return deleteDateFrom;
	}

	/**
	 * @param deleteDateFrom the deleteDateFrom to set
	 */
	public void setDeleteDateFrom(Date deleteDateFrom) {
		this.deleteDateFrom = deleteDateFrom;
	}

	/**
	 * @return the deleteDateTo
	 */
	public Date getDeleteDateTo() {
		return deleteDateTo;
	}

	/**
	 * @param deleteDateTo the deleteDateTo to set
	 */
	public void setDeleteDateTo(Date deleteDateTo) {
		this.deleteDateTo = deleteDateTo;
	}

	/**
	 * eappIdPluralを取得します。
	 * @return eappIdPlural
	 */
	public String getEappIdPlural() {
		return eappIdPlural;
	}

	/**
	 * eappIdPlural
	 * @param eappIdPluralを設定します。
	 */
	public void setEappIdPlural(String eappIdPlural) {
		this.eappIdPlural = eappIdPlural;
	}

	/**
	 * searchEappを取得します。
	 * @return searchEapp
	 */
	public String getSearchEapp() {
		return searchEapp;
	}

	/**
	 * searchEapp
	 * @param searchEappを設定します。
	 */
	public void setSearchEapp(String searchEapp) {
		this.searchEapp = searchEapp;
	}

	public String getRelationShisanNumFlag() {
		return relationShisanNumFlag;
	}

	public void setRelationShisanNumFlag(String relationShisanNumFlag) {
		this.relationShisanNumFlag = relationShisanNumFlag;
	}

	public String getContractEdaPlural() {
		return contractEdaPlural;
	}

	public void setContractEdaPlural(String contractEdaPlural) {
		this.contractEdaPlural = contractEdaPlural;
	}

	/**
	 * @return the parentAssetIdPlural
	 */
	public String getParentAssetIdPlural() {
		return parentAssetIdPlural;
	}

	/**
	 * @param parentAssetIdPlural the parentAssetIdPlural to set
	 */
	public void setParentAssetIdPlural(String parentAssetIdPlural) {
		this.parentAssetIdPlural = parentAssetIdPlural;
	}

	public Date getMntContractStartDateFrom() {
		return mntContractStartDateFrom;
	}

	public void setMntContractStartDateFrom(Date mntContractStartDateFrom) {
		this.mntContractStartDateFrom = mntContractStartDateFrom;
	}

	public Date getMntContractStartDateTo() {
		return mntContractStartDateTo;
	}

	public void setMntContractStartDateTo(Date mntContractStartDateTo) {
		this.mntContractStartDateTo = mntContractStartDateTo;
	}

	public Date getMntContractEndDateFrom() {
		return mntContractEndDateFrom;
	}

	public void setMntContractEndDateFrom(Date mntContractEndDateFrom) {
		this.mntContractEndDateFrom = mntContractEndDateFrom;
	}

	public Date getMntContractEndDateTo() {
		return mntContractEndDateTo;
	}

	public void setMntContractEndDateTo(Date mntContractEndDateTo) {
		this.mntContractEndDateTo = mntContractEndDateTo;
	}

	/*
	 * 文字列からクラスに変換メソッド
	 * @param json 変換json文字列
	 * @return AssetSC.class 該当クラス
	 */
	public static AssetSC fromString(String json) {
		return MappingUtils.fromJson(json, AssetSC.class);
	}
}
