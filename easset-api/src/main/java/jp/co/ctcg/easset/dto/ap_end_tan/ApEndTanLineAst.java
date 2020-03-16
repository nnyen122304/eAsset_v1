/*===================================================================
 * ファイル名 : ApEndTanLineAst.java
 * 概要説明   : 固定資産除売却申請_資産(機器)明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-26 1.0  李           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_tan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApEndTanLineAst implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Integer lineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String assetId; // 情報機器管理番号
	private String holCompanyCode; // 保有会社コード
	private String holSectionCode; // 保有部署コード
	private Integer holSectionYear; // 保有部署年度
	private String holStaffCode; // 資産管理担当者
	private String holOfficeCode; // 設置場所 汎用マスタ-OFFICE
	private Integer holQuantity; // 数量
	private String useStaffCode; // 使用者社員番号
	private String useStaffCompanyCode; // 使用者所属会社コード
	private String useStaffSectionCode; // 使用者所属部署コード
	private Integer useStaffSectionYear; // 使用者所属部署年度
	private String astName; // 資産(機器)名
	private String astCategory1Code; // 資産(機器)大分類 汎用マスタ-ASSET_CATEGORY1
	private String astCategory2Code; // 資産(機器)小分類 汎用マスタ-ASSET_CATEGORY2
	private String astSystemSectionDeployFlag; // 情報システム部配備フラグ
	private String astAssetType; // 資産区分 汎用マスタ-ASSET_TYPE
	private String astManageType; // 管理区分 汎用マスタ-ASSET_MANAGE_TYPE
	private Long ppId; // 固有番号(資産) ProPlusの資産台帳キー
	private String astNum; // 資産番号
	private String getApplicationId; // 取得申請書番号

	//	名称
	private String holSectionName; // 保有部署名
	private String holStaffName; // 保有者名
	private String holOfficeName; // 設置場所
	private String useStaffName; // 使用者
	private String astAssetTypeName; // 資産区分

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		assetId = (String)input.readObject();
		holCompanyCode = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holStaffCode = (String)input.readObject();
		holOfficeCode = (String)input.readObject();
		holQuantity = Function.getExternalInteger(input.readObject());
		useStaffCode = (String)input.readObject();
		useStaffCompanyCode = (String)input.readObject();
		useStaffSectionCode = (String)input.readObject();
		useStaffSectionYear = Function.getExternalInteger(input.readObject());
		astName = (String)input.readObject();
		astCategory1Code = (String)input.readObject();
		astCategory2Code = (String)input.readObject();
		astSystemSectionDeployFlag = (String)input.readObject();
		astAssetType = (String)input.readObject();
		astManageType = (String)input.readObject();
		ppId = Function.getExternalLong(input.readObject());
		astNum = (String)input.readObject();
		getApplicationId = (String)input.readObject();

		//	名称
		holSectionName = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		useStaffName = (String)input.readObject();
		astAssetTypeName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(assetId);
		output.writeObject(holCompanyCode);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(holStaffCode);
		output.writeObject(holOfficeCode);
		output.writeObject(holQuantity);
		output.writeObject(useStaffCode);
		output.writeObject(useStaffCompanyCode);
		output.writeObject(useStaffSectionCode);
		output.writeObject(useStaffSectionYear);
		output.writeObject(astName);
		output.writeObject(astCategory1Code);
		output.writeObject(astCategory2Code);
		output.writeObject(astSystemSectionDeployFlag);
		output.writeObject(astAssetType);
		output.writeObject(astManageType);
		output.writeObject(ppId);
		output.writeObject(astNum);
		output.writeObject(getApplicationId);

		//	名称
		output.writeObject(holSectionName);
		output.writeObject(holStaffName);
		output.writeObject(holOfficeName);
		output.writeObject(useStaffName);
		output.writeObject(astAssetTypeName);

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
	 * holCompanyCodeを取得します。
	 * @return holCompanyCode
	 */
	public String getHolCompanyCode() {
		return holCompanyCode;
	}

	/**
	 * holCompanyCode
	 * @param holCompanyCodeを設定します。
	 */
	public void setHolCompanyCode(String holCompanyCode) {
		this.holCompanyCode = holCompanyCode;
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
	 * holSectionYearを取得します。
	 * @return holSectionYear
	 */
	public Integer getHolSectionYear() {
		return holSectionYear;
	}

	/**
	 * holSectionYear
	 * @param holSectionYearを設定します。
	 */
	public void setHolSectionYear(Integer holSectionYear) {
		this.holSectionYear = holSectionYear;
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
	 * holQuantityを取得します。
	 * @return holQuantity
	 */
	public Integer getHolQuantity() {
		return holQuantity;
	}

	/**
	 * holQuantity
	 * @param holQuantityを設定します。
	 */
	public void setHolQuantity(Integer holQuantity) {
		this.holQuantity = holQuantity;
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
	 * useStaffCompanyCodeを取得します。
	 * @return useStaffCompanyCode
	 */
	public String getUseStaffCompanyCode() {
		return useStaffCompanyCode;
	}

	/**
	 * useStaffCompanyCode
	 * @param useStaffCompanyCodeを設定します。
	 */
	public void setUseStaffCompanyCode(String useStaffCompanyCode) {
		this.useStaffCompanyCode = useStaffCompanyCode;
	}

	/**
	 * useStaffSectionCodeを取得します。
	 * @return useStaffSectionCode
	 */
	public String getUseStaffSectionCode() {
		return useStaffSectionCode;
	}

	/**
	 * useStaffSectionCode
	 * @param useStaffSectionCodeを設定します。
	 */
	public void setUseStaffSectionCode(String useStaffSectionCode) {
		this.useStaffSectionCode = useStaffSectionCode;
	}

	/**
	 * useStaffSectionYearを取得します。
	 * @return useStaffSectionYear
	 */
	public Integer getUseStaffSectionYear() {
		return useStaffSectionYear;
	}

	/**
	 * useStaffSectionYear
	 * @param useStaffSectionYearを設定します。
	 */
	public void setUseStaffSectionYear(Integer useStaffSectionYear) {
		this.useStaffSectionYear = useStaffSectionYear;
	}

	/**
	 * astNameを取得します。
	 * @return astName
	 */
	public String getAstName() {
		return astName;
	}

	/**
	 * astName
	 * @param astNameを設定します。
	 */
	public void setAstName(String astName) {
		this.astName = astName;
	}

	/**
	 * astCategory1Codeを取得します。
	 * @return astCategory1Code
	 */
	public String getAstCategory1Code() {
		return astCategory1Code;
	}

	/**
	 * astCategory1Code
	 * @param astCategory1Codeを設定します。
	 */
	public void setAstCategory1Code(String astCategory1Code) {
		this.astCategory1Code = astCategory1Code;
	}

	/**
	 * astCategory2Codeを取得します。
	 * @return astCategory2Code
	 */
	public String getAstCategory2Code() {
		return astCategory2Code;
	}

	/**
	 * astCategory2Code
	 * @param astCategory2Codeを設定します。
	 */
	public void setAstCategory2Code(String astCategory2Code) {
		this.astCategory2Code = astCategory2Code;
	}

	/**
	 * astSystemSectionDeployFlagを取得します。
	 * @return astSystemSectionDeployFlag
	 */
	public String getAstSystemSectionDeployFlag() {
		return astSystemSectionDeployFlag;
	}

	/**
	 * astSystemSectionDeployFlag
	 * @param astSystemSectionDeployFlagを設定します。
	 */
	public void setAstSystemSectionDeployFlag(String astSystemSectionDeployFlag) {
		this.astSystemSectionDeployFlag = astSystemSectionDeployFlag;
	}

	/**
	 * astAssetTypeを取得します。
	 * @return astAssetType
	 */
	public String getAstAssetType() {
		return astAssetType;
	}

	/**
	 * astAssetType
	 * @param astAssetTypeを設定します。
	 */
	public void setAstAssetType(String astAssetType) {
		this.astAssetType = astAssetType;
	}

	/**
	 * astManageTypeを取得します。
	 * @return astManageType
	 */
	public String getAstManageType() {
		return astManageType;
	}

	/**
	 * astManageType
	 * @param astManageTypeを設定します。
	 */
	public void setAstManageType(String astManageType) {
		this.astManageType = astManageType;
	}

	/**
	 * ppIdを取得します。
	 * @return ppId
	 */
	public Long getPpId() {
		return ppId;
	}

	/**
	 * ppId
	 * @param ppIdを設定します。
	 */
	public void setPpId(Long ppId) {
		this.ppId = ppId;
	}

	/**
	 * astNumを取得します。
	 * @return astNum
	 */
	public String getAstNum() {
		return astNum;
	}

	/**
	 * astNum
	 * @param astNumを設定します。
	 */
	public void setAstNum(String astNum) {
		this.astNum = astNum;
	}

	/**
	 * getApplicationIdを取得します。
	 * @return getApplicationId
	 */
	public String getGetApplicationId() {
		return getApplicationId;
	}

	/**
	 * getApplicationId
	 * @param getApplicationIdを設定します。
	 */
	public void setGetApplicationId(String getApplicationId) {
		this.getApplicationId = getApplicationId;
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
	 * astAssetTypeNameを取得します。
	 * @return astAssetTypeName
	 */
	public String getAstAssetTypeName() {
		return astAssetTypeName;
	}

	/**
	 * astAssetTypeName
	 * @param astAssetTypeNameを設定します。
	 */
	public void setAstAssetTypeName(String astAssetTypeName) {
		this.astAssetTypeName = astAssetTypeName;
	}



}
