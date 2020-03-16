/*===================================================================
 * ファイル名 : CostScrLine.java
 * 概要説明   : 経費負担部課精査明細データクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2015-01-28 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.costScr;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class CostScrLine implements Externalizable {
	private Long scrLineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String period; // 会計年月
	private String companyCode; // 会社コード
	private String scrType; // 精査種別 1:有形固定資産,2:無形固定資産,3:リース,4:レンタル
	private String scrStatus; // 精査ステータス 1:未実施,2:OK,3:NG
	private String scrComment; // コメント
	private String astNum; // 資産番号 有・無・リ・レ
	private String contractNum; // 契約番号 リ・レ
	private String contractSubNum; // 契約枝番 リ・レ
	private String applicationId; // 取得申請番号 有・無・リ・レ
	private String costCompanyCode; // 当年度：経費負担会社コード 有・無・リ・レ
	private String costSectionCode; // 当年度：経費負担部課コード 有・無・リ・レ
	private String costDistCode; // 当年度：配賦コード 有・無・リ・レ
	private String costType; // 当年度：販売管理費/原価区分 有・無・リ・レ 1:原価,2:販売管理費
	private String costDepPrjCode; // 当年度：減価償却プロジェクトコード  有・無・リ・レ
	private String costCompanyCodeOld; // 前年度：経費負担部課会社コード 有・無・リ・レ
	private String costSectionCodeOld; // 前年度：経費負担部課コード 有・無・リ・レ
	private String costDistCodeOld; // 前年度：配賦コード 有・無・リ・レ
	private String orixType; // オリックス区分 リ・レ 1:オリックス以外、2:オリックス3月請求有、3:オリックス4月以降契約
	private Long lldPpIdContract; // 契約・固有番号 リ・レ
	private Long lldPpIdAst; // 物件・固有番号 リ・レ
	private String lldContractStatusType; // 契約状態区分 リ・レ 1:通常、2:契約中途解約済、3:物件中途解約済、4:満了済、5:移行済、6:取消済
	private String lldAstStatusType; // 物件状態区分 リ・レ 1:通常、2:契約中途解約済、3:物件中途解約済、4:満了済、5:移行済、6:取消済
	private String lldLeReCompanyCode; // リース・レンタル会社コード リ・レ
	private String lldLeReCompanyName; // リース・レンタル会社名 リ・レ
	private String lldAstName; // 物件名 リ・レ
	private String lldContractType; // 契約区分 リ・レ 1:基本リース、2:再リース
	private String lldContractStartDate; // 開始日 リ・レ
	private String lldContractEndDate; // 満了日 リ・レ
	private Integer lldPeriodMonth; // 期間(ヵ月) リ・レ
	private Double lldContractAmount; // 契約金額 リ・レ
	private Double lldMonthAmount; // 均等金額 リ・レ
	private Long fldPpId; // 資産・固有番号 有・無
	private String fldAstManageType; // 資産管理区分 有・無 1:建設仮勘定、2:通常資産、3:少額資産、4:一括償却資産、5:簿外資産、6:研究開発費、8:リース資産(オンバランス)、A:除去費用
	private String fldAstStatusType; // 資産状態区分 有・無 1:通常､2:経費振替済、3:固定資産振替済、4:除却済、5:売却済､7:満了除却済、8:清算済
	private String fldPurCompanyCode; // 仕入先コード 有・無
	private String fldPurCompanyName; // 仕入先名 有・無
	private String fldAstName; // 資産名 有・無
	private String fldOldType; // 中古区分 有・無 1:通常資産、2:中古資産
	private String fldStartDate; // 稼働日 有・無
	private String fldEndDate; // 除売却日 有・無
	private Integer fldUsefulYear; // 耐用年数 有・無
	private Double fldAstGetAmount; // 取得価額 有・無
	private Double fldAstBookAmount; // 帳簿価額(4月末) 有・無
	private String assetId; // 情報機器管理番号 有・リ・レ
	private String astName; // 資産(機器)名 有・リ・レ
	private String astMakerModel; // メーカー型番  有・リ・レ
	private String astSerialCode; // シリアル番号  有・リ・レ
	private String astOir; // OIR番号 有・リ・レ
	private String astManageType; // 管理区分 有・リ・レ 汎用マスタ-ASSET_MANAGE_TYPE
	private String useStaffCode; // 当年度：使用者社員番号 有・リ・レ
	private String useStaffCompanyCode; // 当年度：使用者所属会社コード 有・リ・レ
	private String useStaffSectionCode; // 当年度：使用者所属部署コード 有・リ・レ
	private String useStaffSectionYear; // 当年度：使用者所属部署年度 有・リ・レ
	private String useStaffCodeOld; // 前年度：使用者社員番号 有・リ・レ
	private String useStaffCompanyCodeOld; // 前年度：使用者所属会社コード 有・リ・レ
	private String useStaffSectionCodeOld; // 前年度：使用者所属部署コード 有・リ・レ
	private String useStaffSectionYearOld; // 前年度：使用者所属部署年度 有・リ・レ
	private String holStaffCode; // 当年度：無形管理担当者社員番号 無
	private String holStaffCompanyCode; // 当年度：無形管理担当者会社コード 無
	private String holStaffSectionCode; // 当年度：無形管理担当者部署コード 無
	private String holStaffSectionYear; // 当年度：無形管理担当者部署年度 無
	private String holStaffCodeOld; // 前年度：無形管理担当者社員番号 無
	private String holStaffCompanyCodeOld; // 前年度：無形管理担当者会社コード 無
	private String holStaffSectionCodeOld; // 前年度：無形管理担当者部署コード 無
	private String holStaffSectionYearOld; // 前年度：無形管理担当者部署年度 無
	private Date scrDate; // 精査担当部署設定更新日
	private String scrStaffCode; // 精査担当部署更新者社員番号

	private String scrStatus1; // 精査ステータス：OK
	private String scrStatus2; // 精査ステータス：NG
	private String scrStatusName; // 精査ステータス名 ※ CSVファイル出力用

	private String lldContractAmountStr; // 契約金額 ※ CSVファイル出力用：ハイフン表示

	private String costDistSet; // 当年度：配賦セット
	private String costDistSetOld; // 前年度：配賦セット

	private String costTypeName; // 販管・原価区分名

	private String costCompanyName; // 当年度：経費負担部課会社名
	private String costCompanyNameOld; // 前年度：経費負担部課会社名
	private String useStaffCompanyName; // 当年度：使用者所属会社名
	private String useStaffCompanyNameOld; // 前年度：使用者所属会社名
	private String holStaffCompanyName; // 当年度：無形固定資産管理者所属会社
	private String holStaffCompanyNameOld; // 前年度：無形固定資産管理者所属会社

	private String costDepPrjName; // 減価償却プロジェクト名

	private String lldContractStartDateF; // 開始日(日付フォーマット)
	private String lldContractEndDateF; // 満了日(日付フォーマット)
	private String fldStartDateF; // 稼働日(日付フォーマット)
	private String fldEndDateF; // 売却日(日付フォーマット)

	private String costSectionName; // 当年度：経費負担部課名
	private String costSectionNameOld; // 前年度：経費負担部課名

	private String useStaffName; // 当年度：使用者名
	private String useStaffNameOld; // 前年度：使用者名
	private String holStaffName; // 当年度：無形無形固定資産管理者名
	private String holStaffNameOld; // 前年度：無形無形固定資産管理者名

	private String useStaffSectionName; // 当年度：使用者所属部署名
	private String useStaffSectionNameOld; // 前年度：使用者所属部署名
	private String holStaffSectionName; // 当年度：無形固定資産管理部署名
	private String holStaffSectionNameOld; // 前年度：無形固定資産管理部署名

	private String astManageTypeName; // 管理区分名

	private String lldContractStatusTypeName; // 契約状態区分名
	private String lldAstStatusTypeName; // 物件状態区分名
	private String lldContractTypeName; // 契約区分名

	private String fldOldTypeName; // 中古区分名
	private String fldAstManageTypeName; // 資産管理区分名
	private String fldAstStatusTypeName; // 資産状態区分名

	private String apChangeApplicationId; // 移動申請:申請書番号
	private String apChangeApStatus; // 移動申請：申請ステータス
	private Date apChangeApDate; // 移動申請：申請日
	private String apChangeApStaffCode; // 移動申請：申請者社員番号
	private String apChangeCostType;  // 移動申請：販管/原価区分
	private String apChangeCostDepPrjCode;  // 移動申請：減価償却プロジェクトコード
	private String apChangeCostCompanyCode; // 移動申請：資産計上部課会社コード
	private String apChangeCostSectionCode; // 移動申請：資産計上部課コード

	private String apChangeApStatusName;  // 移動申請：申請ステータス名
	private String apChangeApStaffName; // 移動申請：申請者社員名
	private String apChangeCostTypeName; // 移動申請：販管/原価区分名
	private String apChangeCostDepPrjName; // 移動申請：減価償却プロジェクト名
	private String apChangeCostSectionName; // 移動申請：資産計上部課名
	private String apChangeCostSectionNameDist; // 移動申請：経費負担部課配分

	private String scrSectionCode; // 精査部署コード
	private String scrSectionName; // 精査部署名


	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		scrLineSeq = Function.getExternalLong(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		period = (String)input.readObject();
		companyCode = (String)input.readObject();
		scrType = (String)input.readObject();
		scrStatus = (String)input.readObject();
		scrComment = (String)input.readObject();
		astNum = (String)input.readObject();
		contractNum = (String)input.readObject();
		contractSubNum = (String)input.readObject();
		applicationId = (String)input.readObject();
		costCompanyCode = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		costDistCode = (String)input.readObject();
		costType = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costCompanyCodeOld = (String)input.readObject();
		costSectionCodeOld = (String)input.readObject();
		costDistCodeOld = (String)input.readObject();
		orixType = (String)input.readObject();
		lldPpIdContract = Function.getExternalLong(input.readObject());
		lldPpIdAst = Function.getExternalLong(input.readObject());
		lldContractStatusType = (String)input.readObject();
		lldAstStatusType = (String)input.readObject();
		lldLeReCompanyCode = (String)input.readObject();
		lldLeReCompanyName = (String)input.readObject();
		lldAstName = (String)input.readObject();
		lldContractType = (String)input.readObject();
		lldContractStartDate = (String)input.readObject();
		lldContractEndDate = (String)input.readObject();
		lldPeriodMonth = Function.getExternalInteger(input.readObject());
		lldContractAmount = Function.getExternalDouble(input.readObject());
		lldMonthAmount = Function.getExternalDouble(input.readObject());
		fldPpId = Function.getExternalLong(input.readObject());
		fldAstManageType = (String)input.readObject();
		fldAstStatusType = (String)input.readObject();
		fldPurCompanyCode = (String)input.readObject();
		fldPurCompanyName = (String)input.readObject();
		fldAstName = (String)input.readObject();
		fldOldType = (String)input.readObject();
		fldStartDate = (String)input.readObject();
		fldEndDate = (String)input.readObject();
		fldUsefulYear = Function.getExternalInteger(input.readObject());
		fldAstGetAmount = Function.getExternalDouble(input.readObject());
		fldAstBookAmount = Function.getExternalDouble(input.readObject());
		assetId = (String)input.readObject();
		astName = (String)input.readObject();
		astMakerModel = (String)input.readObject();
		astSerialCode = (String)input.readObject();
		astOir = (String)input.readObject();
		astManageType = (String)input.readObject();
		useStaffCode = (String)input.readObject();
		useStaffCompanyCode = (String)input.readObject();
		useStaffSectionCode = (String)input.readObject();
		useStaffSectionYear = (String)input.readObject();
		useStaffCodeOld = (String)input.readObject();
		useStaffCompanyCodeOld = (String)input.readObject();
		useStaffSectionCodeOld = (String)input.readObject();
		useStaffSectionYearOld = (String)input.readObject();
		holStaffCode = (String)input.readObject();
		holStaffCompanyCode = (String)input.readObject();
		holStaffSectionCode = (String)input.readObject();
		holStaffSectionYear = (String)input.readObject();
		holStaffCodeOld = (String)input.readObject();
		holStaffCompanyCodeOld = (String)input.readObject();
		holStaffSectionCodeOld = (String)input.readObject();
		holStaffSectionYearOld = (String)input.readObject();
		scrDate = (Date)input.readObject();
		scrStaffCode = (String)input.readObject();

		scrStatus1 = (String)input.readObject();
		scrStatus2 = (String)input.readObject();

		costDistSet = (String)input.readObject();
		costDistSetOld = (String)input.readObject();

		costTypeName = (String)input.readObject();

		costCompanyName = (String)input.readObject();
		costCompanyNameOld = (String)input.readObject();
		useStaffCompanyName = (String)input.readObject();
		useStaffCompanyNameOld = (String)input.readObject();
		holStaffCompanyName = (String)input.readObject();
		holStaffCompanyNameOld = (String)input.readObject();

		costDepPrjName = (String)input.readObject();

		lldContractStartDateF = (String)input.readObject();
		lldContractEndDateF = (String)input.readObject();
		fldStartDateF = (String)input.readObject();
		fldEndDateF = (String)input.readObject();

		costSectionName = (String)input.readObject();
		costSectionNameOld = (String)input.readObject();

		useStaffName = (String)input.readObject();
		useStaffNameOld = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holStaffNameOld = (String)input.readObject();

		useStaffSectionName = (String)input.readObject();
		useStaffSectionNameOld = (String)input.readObject();
		holStaffSectionName = (String)input.readObject();
		holStaffSectionNameOld = (String)input.readObject();

		astManageTypeName = (String)input.readObject();

		lldContractStatusTypeName = (String)input.readObject();
		lldAstStatusTypeName = (String)input.readObject();
		lldContractTypeName = (String)input.readObject();

		fldOldTypeName = (String)input.readObject();
		fldAstManageTypeName = (String)input.readObject();
		fldAstStatusTypeName = (String)input.readObject();

		apChangeApplicationId = (String)input.readObject();
		apChangeApStatus = (String)input.readObject();
		apChangeApDate = (Date)input.readObject();
		apChangeApStaffCode = (String)input.readObject();
		apChangeCostType = (String)input.readObject();
		apChangeCostDepPrjCode = (String)input.readObject();
		apChangeCostCompanyCode = (String)input.readObject();
		apChangeCostSectionCode = (String)input.readObject();

		apChangeApStatusName = (String)input.readObject();
		apChangeApStaffName = (String)input.readObject();
		apChangeCostTypeName = (String)input.readObject();
		apChangeCostDepPrjName = (String)input.readObject();
		apChangeCostSectionName = (String)input.readObject();
		apChangeCostSectionNameDist = (String)input.readObject();

		scrSectionCode = (String)input.readObject();
		scrSectionName = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(scrLineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(period);
		output.writeObject(companyCode);
		output.writeObject(scrType);
		output.writeObject(scrStatus);
		output.writeObject(scrComment);
		output.writeObject(astNum);
		output.writeObject(contractNum);
		output.writeObject(contractSubNum);
		output.writeObject(applicationId);
		output.writeObject(costCompanyCode);
		output.writeObject(costSectionCode);
		output.writeObject(costDistCode);
		output.writeObject(costType);
		output.writeObject(costDepPrjCode);
		output.writeObject(costCompanyCodeOld);
		output.writeObject(costSectionCodeOld);
		output.writeObject(costDistCodeOld);
		output.writeObject(orixType);
		output.writeObject(lldPpIdContract);
		output.writeObject(lldPpIdAst);
		output.writeObject(lldContractStatusType);
		output.writeObject(lldAstStatusType);
		output.writeObject(lldLeReCompanyCode);
		output.writeObject(lldLeReCompanyName);
		output.writeObject(lldAstName);
		output.writeObject(lldContractType);
		output.writeObject(lldContractStartDate);
		output.writeObject(lldContractEndDate);
		output.writeObject(lldPeriodMonth);
		output.writeObject(lldContractAmount);
		output.writeObject(lldMonthAmount);
		output.writeObject(fldPpId);
		output.writeObject(fldAstManageType);
		output.writeObject(fldAstStatusType);
		output.writeObject(fldPurCompanyCode);
		output.writeObject(fldPurCompanyName);
		output.writeObject(fldAstName);
		output.writeObject(fldOldType);
		output.writeObject(fldStartDate);
		output.writeObject(fldEndDate);
		output.writeObject(fldUsefulYear);
		output.writeObject(fldAstGetAmount);
		output.writeObject(fldAstBookAmount);
		output.writeObject(assetId);
		output.writeObject(astName);
		output.writeObject(astMakerModel);
		output.writeObject(astSerialCode);
		output.writeObject(astOir);
		output.writeObject(astManageType);
		output.writeObject(useStaffCode);
		output.writeObject(useStaffCompanyCode);
		output.writeObject(useStaffSectionCode);
		output.writeObject(useStaffSectionYear);
		output.writeObject(useStaffCodeOld);
		output.writeObject(useStaffCompanyCodeOld);
		output.writeObject(useStaffSectionCodeOld);
		output.writeObject(useStaffSectionYearOld);
		output.writeObject(holStaffCode);
		output.writeObject(holStaffCompanyCode);
		output.writeObject(holStaffSectionCode);
		output.writeObject(holStaffSectionYear);
		output.writeObject(holStaffCodeOld);
		output.writeObject(holStaffCompanyCodeOld);
		output.writeObject(holStaffSectionCodeOld);
		output.writeObject(holStaffSectionYearOld);
		output.writeObject(scrDate);
		output.writeObject(scrStaffCode);

		output.writeObject(scrStatus1);
		output.writeObject(scrStatus2);

		output.writeObject(costDistSet);
		output.writeObject(costDistSetOld);

		output.writeObject(costTypeName);

		output.writeObject(costCompanyName);
		output.writeObject(costCompanyNameOld);
		output.writeObject(useStaffCompanyName);
		output.writeObject(useStaffCompanyNameOld);
		output.writeObject(holStaffCompanyName);
		output.writeObject(holStaffCompanyNameOld);

		output.writeObject(costDepPrjName);

		output.writeObject(lldContractStartDateF);
		output.writeObject(lldContractEndDateF);
		output.writeObject(fldStartDateF);
		output.writeObject(fldEndDateF);

		output.writeObject(costSectionName);
		output.writeObject(costSectionNameOld);

		output.writeObject(useStaffName);
		output.writeObject(useStaffNameOld);
		output.writeObject(holStaffName);
		output.writeObject(holStaffNameOld);

		output.writeObject(useStaffSectionName);
		output.writeObject(useStaffSectionNameOld);
		output.writeObject(holStaffSectionName);
		output.writeObject(holStaffSectionNameOld);

		output.writeObject(astManageTypeName);

		output.writeObject(lldContractStatusTypeName);
		output.writeObject(lldAstStatusTypeName);
		output.writeObject(lldContractTypeName);

		output.writeObject(fldOldTypeName);
		output.writeObject(fldAstManageTypeName);
		output.writeObject(fldAstStatusTypeName);

		output.writeObject(apChangeApplicationId);
		output.writeObject(apChangeApStatus);
		output.writeObject(apChangeApDate);
		output.writeObject(apChangeApStaffCode);
		output.writeObject(apChangeCostType);
		output.writeObject(apChangeCostDepPrjCode);
		output.writeObject(apChangeCostCompanyCode);
		output.writeObject(apChangeCostSectionCode);

		output.writeObject(apChangeApStatusName);
		output.writeObject(apChangeApStaffName);
		output.writeObject(apChangeCostTypeName);
		output.writeObject(apChangeCostDepPrjName);
		output.writeObject(apChangeCostSectionName);
		output.writeObject(apChangeCostSectionNameDist);
		output.writeObject(scrSectionCode);
		output.writeObject(scrSectionName);

	}

	public Long getScrLineSeq() {
		return scrLineSeq;
	}

	public void setScrLineSeq(Long scrLineSeq) {
		this.scrLineSeq = scrLineSeq;
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

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getScrType() {
		return scrType;
	}

	public void setScrType(String scrType) {
		this.scrType = scrType;
	}

	public String getScrStatus() {
		return scrStatus;
	}

	public void setScrStatus(String scrStatus) {
		this.scrStatus = scrStatus;
	}

	public String getScrComment() {
		return scrComment;
	}

	public void setScrComment(String scrComment) {
		this.scrComment = scrComment;
	}

	public String getAstNum() {
		return astNum;
	}

	public void setAstNum(String astNum) {
		this.astNum = astNum;
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

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getCostCompanyCode() {
		return costCompanyCode;
	}

	public void setCostCompanyCode(String costCompanyCode) {
		this.costCompanyCode = costCompanyCode;
	}

	public String getCostSectionCode() {
		return costSectionCode;
	}

	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
	}

	public String getCostDistCode() {
		return costDistCode;
	}

	public void setCostDistCode(String costDistCode) {
		this.costDistCode = costDistCode;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public String getCostDepPrjCode() {
		return costDepPrjCode;
	}

	public void setCostDepPrjCode(String costDepPrjCode) {
		this.costDepPrjCode = costDepPrjCode;
	}

	public String getCostCompanyCodeOld() {
		return costCompanyCodeOld;
	}

	public void setCostCompanyCodeOld(String costCompanyCodeOld) {
		this.costCompanyCodeOld = costCompanyCodeOld;
	}

	public String getCostSectionCodeOld() {
		return costSectionCodeOld;
	}

	public void setCostSectionCodeOld(String costSectionCodeOld) {
		this.costSectionCodeOld = costSectionCodeOld;
	}

	public String getCostDistCodeOld() {
		return costDistCodeOld;
	}

	public void setCostDistCodeOld(String costDistCodeOld) {
		this.costDistCodeOld = costDistCodeOld;
	}

	public Long getLldPpIdContract() {
		return lldPpIdContract;
	}

	public void setLldPpIdContract(Long lldPpIdContract) {
		this.lldPpIdContract = lldPpIdContract;
	}

	public Long getLldPpIdAst() {
		return lldPpIdAst;
	}

	public void setLldPpIdAst(Long lldPpIdAst) {
		this.lldPpIdAst = lldPpIdAst;
	}

	public String getLldContractStatusType() {
		return lldContractStatusType;
	}

	public void setLldContractStatusType(String lldContractStatusType) {
		this.lldContractStatusType = lldContractStatusType;
	}

	public String getLldAstStatusType() {
		return lldAstStatusType;
	}

	public void setLldAstStatusType(String lldAstStatusType) {
		this.lldAstStatusType = lldAstStatusType;
	}

	public String getLldLeReCompanyCode() {
		return lldLeReCompanyCode;
	}

	public void setLldLeReCompanyCode(String lldLeReCompanyCode) {
		this.lldLeReCompanyCode = lldLeReCompanyCode;
	}

	public String getLldLeReCompanyName() {
		return lldLeReCompanyName;
	}

	public void setLldLeReCompanyName(String lldLeReCompanyName) {
		this.lldLeReCompanyName = lldLeReCompanyName;
	}

	public String getLldAstName() {
		return lldAstName;
	}

	public void setLldAstName(String lldAstName) {
		this.lldAstName = lldAstName;
	}

	public String getLldContractType() {
		return lldContractType;
	}

	public void setLldContractType(String lldContractType) {
		this.lldContractType = lldContractType;
	}

	public String getLldContractStartDate() {
		return lldContractStartDate;
	}

	public void setLldContractStartDate(String lldContractStartDate) {
		this.lldContractStartDate = lldContractStartDate;
	}

	public String getLldContractEndDate() {
		return lldContractEndDate;
	}

	public void setLldContractEndDate(String lldContractEndDate) {
		this.lldContractEndDate = lldContractEndDate;
	}

	public Integer getLldPeriodMonth() {
		return lldPeriodMonth;
	}

	public void setLldPeriodMonth(Integer lldPeriodMonth) {
		this.lldPeriodMonth = lldPeriodMonth;
	}

	public Double getLldContractAmount() {
		return lldContractAmount;
	}

	public void setLldContractAmount(Double lldContractAmount) {
		this.lldContractAmount = lldContractAmount;
	}

	public Double getLldMonthAmount() {
		return lldMonthAmount;
	}

	public void setLldMonthAmount(Double lldMonthAmount) {
		this.lldMonthAmount = lldMonthAmount;
	}

	public Long getFldPpId() {
		return fldPpId;
	}

	public void setFldPpId(Long fldPpId) {
		this.fldPpId = fldPpId;
	}

	public String getFldAstManageType() {
		return fldAstManageType;
	}

	public void setFldAstManageType(String fldAstManageType) {
		this.fldAstManageType = fldAstManageType;
	}

	public String getFldAstStatusType() {
		return fldAstStatusType;
	}

	public void setFldAstStatusType(String fldAstStatusType) {
		this.fldAstStatusType = fldAstStatusType;
	}

	public String getFldPurCompanyCode() {
		return fldPurCompanyCode;
	}

	public void setFldPurCompanyCode(String fldPurCompanyCode) {
		this.fldPurCompanyCode = fldPurCompanyCode;
	}

	public String getFldPurCompanyName() {
		return fldPurCompanyName;
	}

	public void setFldPurCompanyName(String fldPurCompanyName) {
		this.fldPurCompanyName = fldPurCompanyName;
	}

	public String getFldAstName() {
		return fldAstName;
	}

	public void setFldAstName(String fldAstName) {
		this.fldAstName = fldAstName;
	}

	public String getFldOldType() {
		return fldOldType;
	}

	public void setFldOldType(String fldOldType) {
		this.fldOldType = fldOldType;
	}

	public String getFldStartDate() {
		return fldStartDate;
	}

	public void setFldStartDate(String fldStartDate) {
		this.fldStartDate = fldStartDate;
	}

	public String getFldEndDate() {
		return fldEndDate;
	}

	public void setFldEndDate(String fldEndDate) {
		this.fldEndDate = fldEndDate;
	}

	public Integer getFldUsefulYear() {
		return fldUsefulYear;
	}

	public void setFldUsefulYear(Integer fldUsefulYear) {
		this.fldUsefulYear = fldUsefulYear;
	}

	public Double getFldAstGetAmount() {
		return fldAstGetAmount;
	}

	public void setFldAstGetAmount(Double fldAstGetAmount) {
		this.fldAstGetAmount = fldAstGetAmount;
	}

	public Double getFldAstBookAmount() {
		return fldAstBookAmount;
	}

	public void setFldAstBookAmount(Double fldAstBookAmount) {
		this.fldAstBookAmount = fldAstBookAmount;
	}

	public String getAssetId() {
		return assetId;
	}

	public void setAssetId(String assetId) {
		this.assetId = assetId;
	}

	public String getAstName() {
		return astName;
	}

	public void setAstName(String astName) {
		this.astName = astName;
	}

	public String getAstMakerModel() {
		return astMakerModel;
	}

	public void setAstMakerModel(String astMakerModel) {
		this.astMakerModel = astMakerModel;
	}

	public String getAstSerialCode() {
		return astSerialCode;
	}

	public void setAstSerialCode(String astSerialCode) {
		this.astSerialCode = astSerialCode;
	}

	public String getAstOir() {
		return astOir;
	}

	public void setAstOir(String astOir) {
		this.astOir = astOir;
	}

	public String getAstManageType() {
		return astManageType;
	}

	public void setAstManageType(String astManageType) {
		this.astManageType = astManageType;
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

	public String getUseStaffSectionYear() {
		return useStaffSectionYear;
	}

	public void setUseStaffSectionYear(String useStaffSectionYear) {
		this.useStaffSectionYear = useStaffSectionYear;
	}

	public String getUseStaffCodeOld() {
		return useStaffCodeOld;
	}

	public void setUseStaffCodeOld(String useStaffCodeOld) {
		this.useStaffCodeOld = useStaffCodeOld;
	}

	public String getUseStaffCompanyCodeOld() {
		return useStaffCompanyCodeOld;
	}

	public void setUseStaffCompanyCodeOld(String useStaffCompanyCodeOld) {
		this.useStaffCompanyCodeOld = useStaffCompanyCodeOld;
	}

	public String getUseStaffSectionCodeOld() {
		return useStaffSectionCodeOld;
	}

	public void setUseStaffSectionCodeOld(String useStaffSectionCodeOld) {
		this.useStaffSectionCodeOld = useStaffSectionCodeOld;
	}

	public String getUseStaffSectionYearOld() {
		return useStaffSectionYearOld;
	}

	public void setUseStaffSectionYearOld(String useStaffSectionYearOld) {
		this.useStaffSectionYearOld = useStaffSectionYearOld;
	}

	public String getHolStaffCode() {
		return holStaffCode;
	}

	public void setHolStaffCode(String holStaffCode) {
		this.holStaffCode = holStaffCode;
	}

	public String getHolStaffCompanyCode() {
		return holStaffCompanyCode;
	}

	public void setHolStaffCompanyCode(String holStaffCompanyCode) {
		this.holStaffCompanyCode = holStaffCompanyCode;
	}

	public String getHolStaffSectionCode() {
		return holStaffSectionCode;
	}

	public void setHolStaffSectionCode(String holStaffSectionCode) {
		this.holStaffSectionCode = holStaffSectionCode;
	}

	public String getHolStaffSectionYear() {
		return holStaffSectionYear;
	}

	public void setHolStaffSectionYear(String holStaffSectionYear) {
		this.holStaffSectionYear = holStaffSectionYear;
	}

	public String getHolStaffCodeOld() {
		return holStaffCodeOld;
	}

	public void setHolStaffCodeOld(String holStaffCodeOld) {
		this.holStaffCodeOld = holStaffCodeOld;
	}

	public String getHolStaffCompanyCodeOld() {
		return holStaffCompanyCodeOld;
	}

	public void setHolStaffCompanyCodeOld(String holStaffCompanyCodeOld) {
		this.holStaffCompanyCodeOld = holStaffCompanyCodeOld;
	}

	public String getHolStaffSectionCodeOld() {
		return holStaffSectionCodeOld;
	}

	public void setHolStaffSectionCodeOld(String holStaffSectionCodeOld) {
		this.holStaffSectionCodeOld = holStaffSectionCodeOld;
	}

	public String getHolStaffSectionYearOld() {
		return holStaffSectionYearOld;
	}

	public void setHolStaffSectionYearOld(String holStaffSectionYearOld) {
		this.holStaffSectionYearOld = holStaffSectionYearOld;
	}

	public String getCostDepPrjName() {
		return costDepPrjName;
	}

	public void setCostDepPrjName(String costDepPrjName) {
		this.costDepPrjName = costDepPrjName;
	}

	public String getLldContractStartDateF() {
		return lldContractStartDateF;
	}

	public void setLldContractStartDateF(String lldContractStartDateF) {
		this.lldContractStartDateF = lldContractStartDateF;
	}

	public String getLldContractEndDateF() {
		return lldContractEndDateF;
	}

	public void setLldContractEndDateF(String lldContractEndDateF) {
		this.lldContractEndDateF = lldContractEndDateF;
	}

	public String getFldStartDateF() {
		return fldStartDateF;
	}

	public void setFldStartDateF(String fldStartDateF) {
		this.fldStartDateF = fldStartDateF;
	}

	public String getFldEndDateF() {
		return fldEndDateF;
	}

	public void setFldEndDateF(String fldEndDateF) {
		this.fldEndDateF = fldEndDateF;
	}

	public String getCostSectionName() {
		return costSectionName;
	}

	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	public String getCostSectionNameOld() {
		return costSectionNameOld;
	}

	public void setCostSectionNameOld(String costSectionNameOld) {
		this.costSectionNameOld = costSectionNameOld;
	}

	public String getUseStaffName() {
		return useStaffName;
	}

	public void setUseStaffName(String useStaffName) {
		this.useStaffName = useStaffName;
	}

	public String getUseStaffNameOld() {
		return useStaffNameOld;
	}

	public void setUseStaffNameOld(String useStaffNameOld) {
		this.useStaffNameOld = useStaffNameOld;
	}

	public String getHolStaffName() {
		return holStaffName;
	}

	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}

	public String getHolStaffNameOld() {
		return holStaffNameOld;
	}

	public void setHolStaffNameOld(String holStaffNameOld) {
		this.holStaffNameOld = holStaffNameOld;
	}

	public String getUseStaffSectionName() {
		return useStaffSectionName;
	}

	public void setUseStaffSectionName(String useStaffSectionName) {
		this.useStaffSectionName = useStaffSectionName;
	}

	public String getUseStaffSectionNameOld() {
		return useStaffSectionNameOld;
	}

	public void setUseStaffSectionNameOld(String useStaffSectionNameOld) {
		this.useStaffSectionNameOld = useStaffSectionNameOld;
	}

	public String getHolStaffSectionName() {
		return holStaffSectionName;
	}

	public void setHolStaffSectionName(String holStaffSectionName) {
		this.holStaffSectionName = holStaffSectionName;
	}

	public String getHolStaffSectionNameOld() {
		return holStaffSectionNameOld;
	}

	public void setHolStaffSectionNameOld(String holStaffSectionNameOld) {
		this.holStaffSectionNameOld = holStaffSectionNameOld;
	}

	public String getScrStatus1() {
		return scrStatus1;
	}

	public void setScrStatus1(String scrStatus1) {
		this.scrStatus1 = scrStatus1;
	}

	public String getScrStatus2() {
		return scrStatus2;
	}

	public void setScrStatus2(String scrStatus2) {
		this.scrStatus2 = scrStatus2;
	}

	public String getCostTypeName() {
		return costTypeName;
	}

	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	public String getCostCompanyName() {
		return costCompanyName;
	}

	public void setCostCompanyName(String costCompanyName) {
		this.costCompanyName = costCompanyName;
	}

	public String getCostCompanyNameOld() {
		return costCompanyNameOld;
	}

	public void setCostCompanyNameOld(String costCompanyNameOld) {
		this.costCompanyNameOld = costCompanyNameOld;
	}

	public String getUseStaffCompanyName() {
		return useStaffCompanyName;
	}

	public void setUseStaffCompanyName(String useStaffCompanyName) {
		this.useStaffCompanyName = useStaffCompanyName;
	}

	public String getUseStaffCompanyNameOld() {
		return useStaffCompanyNameOld;
	}

	public void setUseStaffCompanyNameOld(String useStaffCompanyNameOld) {
		this.useStaffCompanyNameOld = useStaffCompanyNameOld;
	}

	public String getHolStaffCompanyName() {
		return holStaffCompanyName;
	}

	public void setHolStaffCompanyName(String holStaffCompanyName) {
		this.holStaffCompanyName = holStaffCompanyName;
	}

	public String getHolStaffCompanyNameOld() {
		return holStaffCompanyNameOld;
	}

	public void setHolStaffCompanyNameOld(String holStaffCompanyNameOld) {
		this.holStaffCompanyNameOld = holStaffCompanyNameOld;
	}

	public String getLldContractStatusTypeName() {
		return lldContractStatusTypeName;
	}

	public void setLldContractStatusTypeName(String lldContractStatusTypeName) {
		this.lldContractStatusTypeName = lldContractStatusTypeName;
	}

	public String getLldAstStatusTypeName() {
		return lldAstStatusTypeName;
	}

	public void setLldAstStatusTypeName(String lldAstStatusTypeName) {
		this.lldAstStatusTypeName = lldAstStatusTypeName;
	}

	public String getLldContractTypeName() {
		return lldContractTypeName;
	}

	public void setLldContractTypeName(String lldContractTypeName) {
		this.lldContractTypeName = lldContractTypeName;
	}

	public String getFldOldTypeName() {
		return fldOldTypeName;
	}

	public void setFldOldTypeName(String fldOldTypeName) {
		this.fldOldTypeName = fldOldTypeName;
	}

	public String getFldAstManageTypeName() {
		return fldAstManageTypeName;
	}

	public void setFldAstManageTypeName(String fldAstManageTypeName) {
		this.fldAstManageTypeName = fldAstManageTypeName;
	}

	public String getFldAstStatusTypeName() {
		return fldAstStatusTypeName;
	}

	public void setFldAstStatusTypeName(String fldAstStatusTypeName) {
		this.fldAstStatusTypeName = fldAstStatusTypeName;
	}

	public String getScrStatusName() {
		return scrStatusName;
	}

	public void setScrStatusName(String scrStatusName) {
		this.scrStatusName = scrStatusName;
	}

	public String getCostDistSet() {
		return costDistSet;
	}

	public void setCostDistSet(String costDistSet) {
		this.costDistSet = costDistSet;
	}

	public String getCostDistSetOld() {
		return costDistSetOld;
	}

	public void setCostDistSetOld(String costDistSetOld) {
		this.costDistSetOld = costDistSetOld;
	}

	public String getApChangeApplicationId() {
		return apChangeApplicationId;
	}

	public void setApChangeApplicationId(String apChangeApplicationId) {
		this.apChangeApplicationId = apChangeApplicationId;
	}

	public String getApChangeApStatus() {
		return apChangeApStatus;
	}

	public void setApChangeApStatus(String apChangeApStatus) {
		this.apChangeApStatus = apChangeApStatus;
	}

	public Date getApChangeApDate() {
		return apChangeApDate;
	}

	public void setApChangeApDate(Date apChangeApDate) {
		this.apChangeApDate = apChangeApDate;
	}

	public String getApChangeApStaffCode() {
		return apChangeApStaffCode;
	}

	public void setApChangeApStaffCode(String apChangeApStaffCode) {
		this.apChangeApStaffCode = apChangeApStaffCode;
	}

	public String getApChangeCostType() {
		return apChangeCostType;
	}

	public void setApChangeCostType(String apChangeCostType) {
		this.apChangeCostType = apChangeCostType;
	}

	public String getApChangeCostDepPrjCode() {
		return apChangeCostDepPrjCode;
	}

	public void setApChangeCostDepPrjCode(String apChangeCostDepPrjCode) {
		this.apChangeCostDepPrjCode = apChangeCostDepPrjCode;
	}

	public String getApChangeCostSectionCode() {
		return apChangeCostSectionCode;
	}

	public void setApChangeCostSectionCode(String apChangeCostSectionCode) {
		this.apChangeCostSectionCode = apChangeCostSectionCode;
	}

	public String getApChangeCostCompanyCode() {
		return apChangeCostCompanyCode;
	}

	public void setApChangeCostCompanyCode(String apChangeCostCompanyCode) {
		this.apChangeCostCompanyCode = apChangeCostCompanyCode;
	}


	public String getApChangeApStatusName() {
		return apChangeApStatusName;
	}

	public void setApChangeApStatusName(String apChangeApStatusName) {
		this.apChangeApStatusName = apChangeApStatusName;
	}

	public String getApChangeApStaffName() {
		return apChangeApStaffName;
	}

	public void setApChangeApStaffName(String apChangeApStaffName) {
		this.apChangeApStaffName = apChangeApStaffName;
	}

	public String getApChangeCostTypeName() {
		return apChangeCostTypeName;
	}

	public void setApChangeCostTypeName(String apChangeCostTypeName) {
		this.apChangeCostTypeName = apChangeCostTypeName;
	}

	public String getApChangeCostDepPrjName() {
		return apChangeCostDepPrjName;
	}

	public void setApChangeCostDepPrjName(String apChangeCostDepPrjName) {
		this.apChangeCostDepPrjName = apChangeCostDepPrjName;
	}

	public String getApChangeCostSectionName() {
		return apChangeCostSectionName;
	}

	public void setApChangeCostSectionName(String apChangeCostSectionName) {
		this.apChangeCostSectionName = apChangeCostSectionName;
	}

	public String getApChangeCostSectionNameDist() {
		return apChangeCostSectionNameDist;
	}

	public void setApChangeCostSectionNameDist(String apChangeCostSectionNameDist) {
		this.apChangeCostSectionNameDist = apChangeCostSectionNameDist;
	}

	public String getAstManageTypeName() {
		return astManageTypeName;
	}

	public void setAstManageTypeName(String astManageTypeName) {
		this.astManageTypeName = astManageTypeName;
	}

	public String getLldContractAmountStr() {
		return lldContractAmountStr;
	}

	public void setLldContractAmountStr(String lldContractAmountStr) {
		this.lldContractAmountStr = lldContractAmountStr;
	}

	public String getOrixType() {
		return orixType;
	}

	public void setOrixType(String orixType) {
		this.orixType = orixType;
	}

	public Date getScrDate() {
		return scrDate;
	}

	public void setScrDate(Date scrDate) {
		this.scrDate = scrDate;
	}

	public String getScrStaffCode() {
		return scrStaffCode;
	}

	public void setScrStaffCode(String scrStaffCode) {
		this.scrStaffCode = scrStaffCode;
	}

	public String getScrSectionCode() {
		return scrSectionCode;
	}

	public void setScrSectionCode(String scrSectionCode) {
		this.scrSectionCode = scrSectionCode;
	}

	public String getScrSectionName() {
		return scrSectionName;
	}

	public void setScrSectionName(String scrSectionName) {
		this.scrSectionName = scrSectionName;
	}

}
