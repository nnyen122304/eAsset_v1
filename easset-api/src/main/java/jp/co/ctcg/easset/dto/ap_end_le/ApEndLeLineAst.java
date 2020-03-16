/*===================================================================
 * ファイル名 : ApEndLeLineAst.java
 * 概要説明   : リース満了・解約申請
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-12-21  1.0  高山        新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_le;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApEndLeLineAst extends LineData {
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
	private String astNum; // 資産(機器)番号
	private String astName; // 資産(機器)名
	private String astCategory1Code; // 資産(機器)大分類 汎用マスタ-ASSET_CATEGORY1
	private String astCategory2Code; // 資産(機器)小分類 汎用マスタ-ASSET_CATEGORY2
	private String astSystemSectionDeployFlag; // 情報システム部配備フラグ
	private String astAssetType; // 資産区分 汎用マスタ-ASSET_TYPE
	private String astManageType; // 管理区分 汎用マスタ-ASSET_MANAGE_TYPE
	private Long ppIdAst; // 固有番号(物件) ProPlusの物件台帳キー
	private String contractNum; // 契約番号
	private String contractSubNum; // 契約枝番
	private String deleteExecFlag; // 抹消実行フラグ 0:抹消実行無し,1:抹消実行有り

	//	名称
	private String holSectionName; // 保有部署名
	private String holStaffName; // 保有者名
	private String holOfficeName; // 設置場所
	private String useStaffName; // 使用者
	private String astAssetTypeName; // 資産区分

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

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
		astNum = (String)input.readObject();
		astName = (String)input.readObject();
		astCategory1Code = (String)input.readObject();
		astCategory2Code = (String)input.readObject();
		astSystemSectionDeployFlag = (String)input.readObject();
		astAssetType = (String)input.readObject();
		astManageType = (String)input.readObject();
		ppIdAst = Function.getExternalLong(input.readObject());
		contractNum = (String)input.readObject();
		contractSubNum = (String)input.readObject();
		deleteExecFlag = (String)input.readObject();

		//	名称
		holSectionName = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		useStaffName = (String)input.readObject();
		astAssetTypeName = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

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
		output.writeObject(astNum);
		output.writeObject(astName);
		output.writeObject(astCategory1Code);
		output.writeObject(astCategory2Code);
		output.writeObject(astSystemSectionDeployFlag);
		output.writeObject(astAssetType);
		output.writeObject(astManageType);
		output.writeObject(ppIdAst);
		output.writeObject(contractNum);
		output.writeObject(contractSubNum);
		output.writeObject(deleteExecFlag);

		//	名称
		output.writeObject(holSectionName);
		output.writeObject(holStaffName);
		output.writeObject(holOfficeName);
		output.writeObject(useStaffName);
		output.writeObject(astAssetTypeName);

	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public Integer getLineSeq() {
		return lineSeq;
	}

	public void setLineSeq(Integer lineSeq) {
		this.lineSeq = lineSeq;
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

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getHolCompanyCode() {
		return holCompanyCode;
	}

	public void setHolCompanyCode(String holCompanyCode) {
		this.holCompanyCode = holCompanyCode;
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

	public String getHolStaffCode() {
		return holStaffCode;
	}

	public void setHolStaffCode(String holStaffCode) {
		this.holStaffCode = holStaffCode;
	}

	public String getHolOfficeCode() {
		return holOfficeCode;
	}

	public void setHolOfficeCode(String holOfficeCode) {
		this.holOfficeCode = holOfficeCode;
	}

	public Integer getHolQuantity() {
		return holQuantity;
	}

	public void setHolQuantity(Integer holQuantity) {
		this.holQuantity = holQuantity;
	}

	public String getUseStaffCode() {
		return useStaffCode;
	}

	public void setUseStaffCode(String useStaffCode) {
		this.useStaffCode = useStaffCode;
	}

	public String getUseStaffCompanyCode() {
		return useStaffCompanyCode;
	}

	public void setUseStaffCompanyCode(String useStaffCompanyCode) {
		this.useStaffCompanyCode = useStaffCompanyCode;
	}

	public String getUseStaffSectionCode() {
		return useStaffSectionCode;
	}

	public void setUseStaffSectionCode(String useStaffSectionCode) {
		this.useStaffSectionCode = useStaffSectionCode;
	}

	public Integer getUseStaffSectionYear() {
		return useStaffSectionYear;
	}

	public void setUseStaffSectionYear(Integer useStaffSectionYear) {
		this.useStaffSectionYear = useStaffSectionYear;
	}

	public String getAstName() {
		return astName;
	}

	public void setAstName(String astName) {
		this.astName = astName;
	}

	public String getAstCategory1Code() {
		return astCategory1Code;
	}

	public void setAstCategory1Code(String astCategory1Code) {
		this.astCategory1Code = astCategory1Code;
	}

	public String getAstCategory2Code() {
		return astCategory2Code;
	}

	public void setAstCategory2Code(String astCategory2Code) {
		this.astCategory2Code = astCategory2Code;
	}

	public String getAstSystemSectionDeployFlag() {
		return astSystemSectionDeployFlag;
	}

	public void setAstSystemSectionDeployFlag(String astSystemSectionDeployFlag) {
		this.astSystemSectionDeployFlag = astSystemSectionDeployFlag;
	}

	public String getAstAssetType() {
		return astAssetType;
	}

	public void setAstAssetType(String astAssetType) {
		this.astAssetType = astAssetType;
	}

	public String getAstManageType() {
		return astManageType;
	}

	public void setAstManageType(String astManageType) {
		this.astManageType = astManageType;
	}

	public Long getPpIdAst() {
		return ppIdAst;
	}

	public void setPpIdAst(Long ppIdAst) {
		this.ppIdAst = ppIdAst;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getContractSubNum() {
		return contractSubNum;
	}

	public void setContractSubNum(String contractSubNum) {
		this.contractSubNum = contractSubNum;
	}

	public String getDeleteExecFlag() {
		return deleteExecFlag;
	}

	public void setDeleteExecFlag(String deleteExecFlag) {
		this.deleteExecFlag = deleteExecFlag;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getHolSectionName() {
		return holSectionName;
	}

	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}

	public String getHolStaffName() {
		return holStaffName;
	}

	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}

	public String getHolOfficeName() {
		return holOfficeName;
	}

	public void setHolOfficeName(String holOfficeName) {
		this.holOfficeName = holOfficeName;
	}

	public String getUseStaffName() {
		return useStaffName;
	}

	public void setUseStaffName(String useStaffName) {
		this.useStaffName = useStaffName;
	}

	public String getAstAssetTypeName() {
		return astAssetTypeName;
	}

	public void setAstAssetTypeName(String astAssetTypeName) {
		this.astAssetTypeName = astAssetTypeName;
	}

	public String getAstNum() {
		return astNum;
	}

	public void setAstNum(String astNum) {
		this.astNum = astNum;
	}

}