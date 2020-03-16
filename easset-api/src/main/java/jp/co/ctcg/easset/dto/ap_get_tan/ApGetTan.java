/*===================================================================
 * ファイル名 : ApGetTan.java
 * 概要説明   : 取得申請(有形)
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-18 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_get_tan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApGetTan implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private Integer version; // バージョン
	private Integer displayVersion; // 改定番号
	private String updateComment; // 更新コメント
	private Long eappId; // e申請書類ID
	private String apStatus; // 申請書ステータス 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下,6:取下げ
	private String apStatusName;  // 申請書ステータス名
	private Date apDate; // 申請日
	private String apGetType; // 取得形態
	private String apGetTypeName;  // 取得形態名
	private String apGetAmountRange; // 取得金額範囲
	private String apGetAmountRangeName;  // 取得金額範囲名
	private String apGetAmountRangeUseCostSecType; // 取得金額範囲経費負担部署使用設定
	private String apGetAmountRangeUseLineType; // 取得金額範囲明細使用設定
	private String specialApproveFlag; // 稟議書・経営会議等承認済フラグ 0:未承認、1:承認済
	private Date specialApproveDate; // 稟議書・経営会議等承認日
	private String apCreateStaffCode; // 起票者社員番号
	private String apCreateStaffName; // 起票者社員名
	private String apCreateCompanyCode; // 起票者所属会社コード
	private String apCreateCompanyName; // 起票者所属会社名
	private String apCreateSectionCode; // 起票者所属部署コード
	private String apCreateSectionName; // 起票者所属部署名
	private Integer apCreateSectionYear; // 起票者所属部署年度
	private String apStaffCode; // 申請者社員番号
	private String apStaffName; // 申請者社員名
	private String apCompanyCode; // 申請会社コード
	private String apCompanyName; // 申請会社名
	private String apSectionCode; // 申請部署コード
	private String apSectionName;  // 申請部署名
	private Integer apSectionYear; // 申請部署年度
	private String apTel; // 連絡先TEL
	private String apSkipApproveFlag; // 課長/GL承認不要フラグ 0:要承認、1:承認不要
	private String apRegistStaffCode; // 登録申請担当者社員番号
	private String apRegistStaffName; // 登録申請担当者社員名
	private String apRegistCompanyCode; // 登録申請担当者所属会社コード
	private String apRegistCompanyName; // 登録申請担当者所属会社名
	private String apRegistSectionCode; // 登録申請担当者所属部署コード
	private String apRegistSectionName; // 登録申請担当者所属部署名
	private Integer apRegistSectionYear; // 登録申請担当者所属部署年度
	private String getPurposeCode; // 取得目的コード
	private String getPurposeName; // 取得目的名
	private String getReason; // 取得理由
	private String getItemName; // 案件名
	private String getItemCloudType; // クラウド区分 1:クラウド以外,2:クラウド
	private String getItemGroupCode; // 案件グループコード
	private String getItemGroupName; // 案件グループ名
	private Date getDeliveryDate; // 納入予定日
	private Date getReturnDate; // 返却予定日
	private String getNeedCioJudgeFlag; // 要CIO審査フラグ 0:不要,1:必要
	private String getSystemSectionDeployFlag; // 情報システム部配備フラグ 0:情報システム部配備以外,1:情報システム部配備
	private String getIntraInventoryFlag; // 社内実地棚卸対象フラグ 0:対象外,1:対象
	private String getLeReCompanyCode; // リース/レンタル会社コード
	private String getLeReCompanyName;  // リース/レンタル会社名
	private String getLeReEstimateNumber; // リース/レンタル見積番号
	private String getLeReComment; // リース/レンタルコメント
	private Long getLeReMonthAmount; // リース/レンタル月額
	private Integer getLeReCount; // リース/レンタル月数
	private Long getLeReTotalAmount; // リース/レンタル総額
	private String getLeEappId; // リース書類ID(e申請リース見積依頼書)
	private Long getTotalAmount; // 取得金額合計
	private String attDescription; // 添付補足
	private String holCompanyCode; // 保有会社コード
	private String holCompanyName; // 保有会社名
	private String holSectionCode; // 保有部署コード
	private String holSectionName; // 保有部署名
	private Integer holSectionYear; // 保有部署年度
	private String holStaffCode; // 資産管理担当者
	private String holStaffName; // 資産管理担当者名
	private String holOfficeCode; // 設置場所
	private String holOfficeName; // 設置場所名
	private String holOfficeOutsideFlag; // 設置場所社外フラグ
	private Integer holOfficeFloor; // 設置場所階数
	private String holOfficeOutsidePcId; // 社外常設PC申請書番号
	private String holOfficeDescription; // 設置場所補足
	private String costType; // 販売管理費/原価区分 1:販売管理費,2:原価
	private String costDepPrjUndecidedFlag; // 減価償却プロジェクトコード未定フラグ 0:確定,1:未定
	private String costDepPrjCode; // 減価償却プロジェクトコード
	private String costDepPrjName; // 減価償却プロジェクト名
	private String costDepPrjType; // 減価償却プロジェクトタイプ
	private String descriptionAp; // 備考_申請者記入欄
	private String descriptionAdmin; // 備考_受付担当者・管理者記入欄
	private String lineEditApproveFlag; // 明細修正許可フラグ 0:許可しない,1:許可する
	private String stopRegistFlag; // 登録申請停止フラグ 0:停止しない,1:停止する
	private String registCompleteFlag; // 登録完了フラグ 0:登録残有り,1:完了
	private String approveSuperiorFlag; // 受付承認フラグ 0:未承認,1:承認済
	private Date approveDate; // 承認日
	private String apCreateTel; // 起票者:連絡先TEL
	private String costCompanyCode; // 資産計上会社コード
	private String costSectionCode; // 資産計上部課コード
	private Integer costSectionYear; // 資産計上部課年度
	private String costSectionName; // 資産計上部課名

	private Long getLeReMonthLessAmount; // リース/レンタル1ヶ月未満の金額
	private String reoReceptNumber; // レンタル注文書:受付番号
	private String reoOrderType; // レンタル注文書:注文書区分 1:推奨機,2:推奨機以外,3:フラットレンタル,4:シンクライアント
	private String reoAstStaffCode; // レンタル注文書:資産管理担当者社員コード
	private String reoAstCompanyCode; // レンタル注文書:資産管理担当者所属会社コード
	private String reoAstSectionCode; // レンタル注文書:資産管理担当者所属部署コード
	private Integer reoAstSectionYear; // レンタル注文書:資産管理担当者所属部署年度
	private String reoAstTel; // レンタル注文書:資産管理担当者電話番号
	private String reoAstFax; // レンタル注文書:資産管理担当者FAX番号
	private String reoAstMailAddress; // レンタル注文書:資産管理担当者メールアドレス
	private Date reoUseHopeStartDate; // レンタル注文書:使用希望開始日
	private String reoInvAddress; // レンタル注文書:請求書送付先担当者住所
	private String reoInvTel; // レンタル注文書:請求書送付先担当者電話番号
	private String reoInvFax; // レンタル注文書:請求書送付先担当者FAX番号
	private String reoInvMailAddress; // レンタル注文書:請求書送付先担当者メールアドレス
	private String reoDlvStaffCode; // レンタル注文書:納品先担当者社員コード
	private String reoDlvCompanyCode; // レンタル注文書:納品先担当者所属会社コード
	private String reoDlvSectionCode; // レンタル注文書:納品先担当者所属部署コード
	private Integer reoDlvSectionYear; // レンタル注文書:納品先担当者所属部署年度
	private String reoDlvAddress; // レンタル注文書:納品先担当者住所
	private String reoDlvTel; // レンタル注文書:納品先担当者電話番号
	private String reoDlvFax; // レンタル注文書:納品先担当者FAX番号
	private String reoDlvMailAddress; // レンタル注文書:納品先担当者メールアドレス
	private String reoDescription; // レンタル注文書:備考
	private Date reoOrderDate; // レンタル注文書:発注日
	private String reoOrderFlag; // レンタル注文書:発注フラグ 0:未発注、1:発注
	private String reoInvStaffInputType; // レンタル注文書:請求書送付先入力タイプ 1:自動指定、2:手入力
	private String reoInvStaffName; // レンタル注文書:請求書送付先担当者社員名
	private String reoInvCompanyName; // レンタル注文書:請求書送付先担当者所属会社名
	private String reoInvSectionName; // レンタル注文書:請求書送付先担当者所属部署名
	private String reoInvCompanyOfficailName;  // レンタル注文書:請求書送付先担当者所属会社名称
	private String reoDisuseFlag; // レンタル注文書:発注抑止フラグ
	private Integer getLeReDateCount; // リース/レンタル日数
	private String reoAstOir; // 現機種OIR

	private String reoOrderTypeName; // レンタル注文書：注文書区分名
	private String reoAstStaffName; // レンタル注文書:資産管理担当者名
	private String reoDlvStaffName; // レンタル注文書:納品担当者名
	private String reoAstCompanyName; // レンタル注文書:資産管理担当者会社名
	private String reoDlvCompanyName; // レンタル注文書:納品担当者会社名
	private String reoAstSectionName; // レンタル注文書:資産管理担当者所属部署名
	private String reoDlvSectionName; // レンタル注文書:納品担当者所属部署名

	// 明細
	private List<ApGetTanLinePur> apGetTanLinePurList; // 購入明細
	private List<ApGetTanLineOtrCost> apGetTanLineOtrCostList; // その他費用明細
	private List<ApGetTanLineAtt> apGetTanLineAttList; // 添付明細
	private List<ApGetTanLineCostSec> apGetTanLineCostSecList; // 経費負担部署明細
	private List<ApGetTanLineAst> apGetTanLineAstList; // 資産(機器)明細
	private List<ApGetTanLineLic> apGetTanLineLicList; // ライセンス明細

	private String getPurEstimateNumber;	//	購入先見積番号

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		version = Function.getExternalInteger(input.readObject());
		displayVersion = Function.getExternalInteger(input.readObject());
		updateComment = (String)input.readObject();
		eappId = Function.getExternalLong(input.readObject());
		apStatus = (String)input.readObject();
		apStatusName = (String)input.readObject();
		apDate = (Date)input.readObject();
		apGetType = (String)input.readObject();
		apGetTypeName = (String)input.readObject();
		apGetAmountRange = (String)input.readObject();
		apGetAmountRangeName = (String)input.readObject();
		apGetAmountRangeUseCostSecType = (String)input.readObject();
		apGetAmountRangeUseLineType = (String)input.readObject();
		specialApproveFlag = (String)input.readObject();
		specialApproveDate = (Date)input.readObject();
		apCreateStaffCode = (String)input.readObject();
		apCreateStaffName = (String)input.readObject();
		apCreateCompanyCode = (String)input.readObject();
		apCreateCompanyName = (String)input.readObject();
		apCreateSectionCode = (String)input.readObject();
		apCreateSectionName = (String)input.readObject();
		apCreateSectionYear = Function.getExternalInteger(input.readObject());
		apStaffCode = (String)input.readObject();
		apStaffName = (String)input.readObject();
		apCompanyCode = (String)input.readObject();
		apCompanyName = (String)input.readObject();
		apSectionCode = (String)input.readObject();
		apSectionName = (String)input.readObject();
		apSectionYear = Function.getExternalInteger(input.readObject());
		apTel = (String)input.readObject();
		apSkipApproveFlag = (String)input.readObject();
		apRegistStaffCode = (String)input.readObject();
		apRegistStaffName = (String)input.readObject();
		apRegistCompanyCode = (String)input.readObject();
		apRegistCompanyName = (String)input.readObject();
		apRegistSectionCode = (String)input.readObject();
		apRegistSectionName = (String)input.readObject();
		apRegistSectionYear = Function.getExternalInteger(input.readObject());
		getPurposeCode = (String)input.readObject();
		getPurposeName = (String)input.readObject();
		getReason = (String)input.readObject();
		getItemName = (String)input.readObject();
		getItemCloudType = (String)input.readObject();
		getItemGroupCode = (String)input.readObject();
		getItemGroupName = (String)input.readObject();
		getDeliveryDate = (Date)input.readObject();
		getReturnDate = (Date)input.readObject();
		getNeedCioJudgeFlag = (String)input.readObject();
		getSystemSectionDeployFlag = (String)input.readObject();
		getIntraInventoryFlag = (String)input.readObject();
		getLeReCompanyCode = (String)input.readObject();
		getLeReCompanyName = (String)input.readObject();
		getLeReEstimateNumber = (String)input.readObject();
		getLeReComment = (String)input.readObject();
		getLeReMonthAmount = Function.getExternalLong(input.readObject());
		getLeReCount = Function.getExternalInteger(input.readObject());
		getLeReTotalAmount = Function.getExternalLong(input.readObject());
		getLeEappId = (String)input.readObject();
		getTotalAmount = Function.getExternalLong(input.readObject());
		attDescription = (String)input.readObject();
		holCompanyCode = (String)input.readObject();
		holCompanyName = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionName = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holStaffCode = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holOfficeCode = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		holOfficeOutsideFlag = (String)input.readObject();
		holOfficeFloor = Function.getExternalInteger(input.readObject());
		holOfficeOutsidePcId = (String)input.readObject();
		holOfficeDescription = (String)input.readObject();
		costType = (String)input.readObject();
		costDepPrjUndecidedFlag = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costDepPrjName = (String)input.readObject();
		costDepPrjType = (String)input.readObject();
		descriptionAp = (String)input.readObject();
		descriptionAdmin = (String)input.readObject();
		lineEditApproveFlag = (String)input.readObject();
		stopRegistFlag = (String)input.readObject();
		registCompleteFlag = (String)input.readObject();
		approveSuperiorFlag = (String)input.readObject();
		approveDate = (Date)input.readObject();
		apCreateTel = (String)input.readObject();
		costCompanyCode = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		costSectionYear = Function.getExternalInteger(input.readObject());
		costSectionName = (String)input.readObject();

		getLeReMonthLessAmount = Function.getExternalLong(input.readObject());
		reoReceptNumber = (String)input.readObject();
		reoOrderType = (String)input.readObject();
		reoAstStaffCode = (String)input.readObject();
		reoAstCompanyCode = (String)input.readObject();
		reoAstSectionCode = (String)input.readObject();
		reoAstSectionYear = Function.getExternalInteger(input.readObject());
		reoAstTel = (String)input.readObject();
		reoAstFax = (String)input.readObject();
		reoAstMailAddress = (String)input.readObject();
		reoUseHopeStartDate = (Date)input.readObject();
		reoInvAddress = (String)input.readObject();
		reoInvTel = (String)input.readObject();
		reoInvFax = (String)input.readObject();
		reoInvMailAddress = (String)input.readObject();
		reoDlvStaffCode = (String)input.readObject();
		reoDlvCompanyCode = (String)input.readObject();
		reoDlvSectionCode = (String)input.readObject();
		reoDlvSectionYear = Function.getExternalInteger(input.readObject());
		reoDlvAddress = (String)input.readObject();
		reoDlvTel = (String)input.readObject();
		reoDlvFax = (String)input.readObject();
		reoDlvMailAddress = (String)input.readObject();
		reoDescription = (String)input.readObject();
		reoOrderDate = (Date)input.readObject();
		reoOrderFlag = (String)input.readObject();
		reoInvStaffInputType = (String)input.readObject();
		reoInvStaffName = (String)input.readObject();
		reoInvCompanyName = (String)input.readObject();
		reoInvSectionName = (String)input.readObject();
		reoInvCompanyOfficailName = (String)input.readObject();
		reoDisuseFlag = (String)input.readObject();
		getLeReDateCount = Function.getExternalInteger(input.readObject());
		reoAstOir = (String)input.readObject();

		reoOrderTypeName = (String)input.readObject();
		reoAstStaffName = (String)input.readObject();
		reoDlvStaffName = (String)input.readObject();
		reoAstCompanyName = (String)input.readObject();
		reoDlvCompanyName = (String)input.readObject();
		reoAstSectionName = (String)input.readObject();
		reoDlvSectionName = (String)input.readObject();

		apGetTanLinePurList = (List<ApGetTanLinePur>)input.readObject();
		apGetTanLineOtrCostList = (List<ApGetTanLineOtrCost>)input.readObject();
		apGetTanLineAttList = (List<ApGetTanLineAtt>)input.readObject();
		apGetTanLineCostSecList = (List<ApGetTanLineCostSec>)input.readObject();
		apGetTanLineAstList = (List<ApGetTanLineAst>)input.readObject();
		apGetTanLineLicList = (List<ApGetTanLineLic>)input.readObject();

		getPurEstimateNumber = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(version);
		output.writeObject(displayVersion);
		output.writeObject(updateComment);
		output.writeObject(eappId);
		output.writeObject(apStatus);
		output.writeObject(apStatusName);
		output.writeObject(apDate);
		output.writeObject(apGetType);
		output.writeObject(apGetTypeName);
		output.writeObject(apGetAmountRange);
		output.writeObject(apGetAmountRangeName);
		output.writeObject(apGetAmountRangeUseCostSecType);
		output.writeObject(apGetAmountRangeUseLineType);
		output.writeObject(specialApproveFlag);
		output.writeObject(specialApproveDate);
		output.writeObject(apCreateStaffCode);
		output.writeObject(apCreateStaffName);
		output.writeObject(apCreateCompanyCode);
		output.writeObject(apCreateCompanyName);
		output.writeObject(apCreateSectionCode);
		output.writeObject(apCreateSectionName);
		output.writeObject(apCreateSectionYear);
		output.writeObject(apStaffCode);
		output.writeObject(apStaffName);
		output.writeObject(apCompanyCode);
		output.writeObject(apCompanyName);
		output.writeObject(apSectionCode);
		output.writeObject(apSectionName);
		output.writeObject(apSectionYear);
		output.writeObject(apTel);
		output.writeObject(apSkipApproveFlag);
		output.writeObject(apRegistStaffCode);
		output.writeObject(apRegistStaffName);
		output.writeObject(apRegistCompanyCode);
		output.writeObject(apRegistCompanyName);
		output.writeObject(apRegistSectionCode);
		output.writeObject(apRegistSectionName);
		output.writeObject(apRegistSectionYear);
		output.writeObject(getPurposeCode);
		output.writeObject(getPurposeName);
		output.writeObject(getReason);
		output.writeObject(getItemName);
		output.writeObject(getItemCloudType);
		output.writeObject(getItemGroupCode);
		output.writeObject(getItemGroupName);
		output.writeObject(getDeliveryDate);
		output.writeObject(getReturnDate);
		output.writeObject(getNeedCioJudgeFlag);
		output.writeObject(getSystemSectionDeployFlag);
		output.writeObject(getIntraInventoryFlag);
		output.writeObject(getLeReCompanyCode);
		output.writeObject(getLeReCompanyName);
		output.writeObject(getLeReEstimateNumber);
		output.writeObject(getLeReComment);
		output.writeObject(getLeReMonthAmount);
		output.writeObject(getLeReCount);
		output.writeObject(getLeReTotalAmount);
		output.writeObject(getLeEappId);
		output.writeObject(getTotalAmount);
		output.writeObject(attDescription);
		output.writeObject(holCompanyCode);
		output.writeObject(holCompanyName);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionName);
		output.writeObject(holSectionYear);
		output.writeObject(holStaffCode);
		output.writeObject(holStaffName);
		output.writeObject(holOfficeCode);
		output.writeObject(holOfficeName);
		output.writeObject(holOfficeOutsideFlag);
		output.writeObject(holOfficeFloor);
		output.writeObject(holOfficeOutsidePcId);
		output.writeObject(holOfficeDescription);
		output.writeObject(costType);
		output.writeObject(costDepPrjUndecidedFlag);
		output.writeObject(costDepPrjCode);
		output.writeObject(costDepPrjName);
		output.writeObject(costDepPrjType);
		output.writeObject(descriptionAp);
		output.writeObject(descriptionAdmin);
		output.writeObject(lineEditApproveFlag);
		output.writeObject(stopRegistFlag);
		output.writeObject(registCompleteFlag);
		output.writeObject(approveSuperiorFlag);
		output.writeObject(approveDate);
		output.writeObject(apCreateTel);
		output.writeObject(costCompanyCode);
		output.writeObject(costSectionCode);
		output.writeObject(costSectionYear);
		output.writeObject(costSectionName);

		output.writeObject(getLeReMonthLessAmount);
		output.writeObject(reoReceptNumber);
		output.writeObject(reoOrderType);
		output.writeObject(reoAstStaffCode);
		output.writeObject(reoAstCompanyCode);
		output.writeObject(reoAstSectionCode);
		output.writeObject(reoAstSectionYear);
		output.writeObject(reoAstTel);
		output.writeObject(reoAstFax);
		output.writeObject(reoAstMailAddress);
		output.writeObject(reoUseHopeStartDate);
		output.writeObject(reoInvAddress);
		output.writeObject(reoInvTel);
		output.writeObject(reoInvFax);
		output.writeObject(reoInvMailAddress);
		output.writeObject(reoDlvStaffCode);
		output.writeObject(reoDlvCompanyCode);
		output.writeObject(reoDlvSectionCode);
		output.writeObject(reoDlvSectionYear);
		output.writeObject(reoDlvAddress);
		output.writeObject(reoDlvTel);
		output.writeObject(reoDlvFax);
		output.writeObject(reoDlvMailAddress);
		output.writeObject(reoDescription);
		output.writeObject(reoOrderDate);
		output.writeObject(reoOrderFlag);
		output.writeObject(reoInvStaffInputType);
		output.writeObject(reoInvStaffName);
		output.writeObject(reoInvCompanyName);
		output.writeObject(reoInvSectionName);
		output.writeObject(reoInvCompanyOfficailName);
		output.writeObject(reoDisuseFlag);
		output.writeObject(getLeReDateCount);
		output.writeObject(reoAstOir);

		output.writeObject(reoOrderTypeName);
		output.writeObject(reoAstStaffName);
		output.writeObject(reoDlvStaffName);
		output.writeObject(reoAstCompanyName);
		output.writeObject(reoDlvCompanyName);
		output.writeObject(reoAstSectionName);
		output.writeObject(reoDlvSectionName);

		output.writeObject(apGetTanLinePurList);
		output.writeObject(apGetTanLineOtrCostList);
		output.writeObject(apGetTanLineAttList);
		output.writeObject(apGetTanLineCostSecList);
		output.writeObject(apGetTanLineAstList);
		output.writeObject(apGetTanLineLicList);

		output.writeObject(getPurEstimateNumber);


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
	 * versionを取得します。
	 * @return version
	 */
	public Integer getVersion() {
		return version;
	}

	/**
	 * version
	 * @param versionを設定します。
	 */
	public void setVersion(Integer version) {
		this.version = version;
	}

	/**
	 * displayVersionを取得します。
	 * @return displayVersion
	 */
	public Integer getDisplayVersion() {
		return displayVersion;
	}

	/**
	 * displayVersion
	 * @param displayVersionを設定します。
	 */
	public void setDisplayVersion(Integer displayVersion) {
		this.displayVersion = displayVersion;
	}

	/**
	 * updateCommentを取得します。
	 * @return updateComment
	 */
	public String getUpdateComment() {
		return updateComment;
	}

	/**
	 * updateComment
	 * @param updateCommentを設定します。
	 */
	public void setUpdateComment(String updateComment) {
		this.updateComment = updateComment;
	}

	/**
	 * eappIdを取得します。
	 * @return eappId
	 */
	public Long getEappId() {
		return eappId;
	}

	/**
	 * eappId
	 * @param eappIdを設定します。
	 */
	public void setEappId(Long eappId) {
		this.eappId = eappId;
	}

	/**
	 * apStatusを取得します。
	 * @return apStatus
	 */
	public String getApStatus() {
		return apStatus;
	}

	/**
	 * apStatus
	 * @param apStatusを設定します。
	 */
	public void setApStatus(String apStatus) {
		this.apStatus = apStatus;
	}

	/**
	 * apStatusNameを取得します。
	 * @return apStatusName
	 */
	public String getApStatusName() {
		return apStatusName;
	}

	/**
	 * apStatusName
	 * @param apStatusNameを設定します。
	 */
	public void setApStatusName(String apStatusName) {
		this.apStatusName = apStatusName;
	}

	/**
	 * apDateを取得します。
	 * @return apDate
	 */
	public Date getApDate() {
		return apDate;
	}

	/**
	 * apDate
	 * @param apDateを設定します。
	 */
	public void setApDate(Date apDate) {
		this.apDate = apDate;
	}

	/**
	 * apGetTypeを取得します。
	 * @return apGetType
	 */
	public String getApGetType() {
		return apGetType;
	}

	/**
	 * apGetType
	 * @param apGetTypeを設定します。
	 */
	public void setApGetType(String apGetType) {
		this.apGetType = apGetType;
	}

	/**
	 * apGetTypeNameを取得します。
	 * @return apGetTypeName
	 */
	public String getApGetTypeName() {
		return apGetTypeName;
	}

	/**
	 * apGetTypeName
	 * @param apGetTypeNameを設定します。
	 */
	public void setApGetTypeName(String apGetTypeName) {
		this.apGetTypeName = apGetTypeName;
	}

	/**
	 * apGetAmountRangeを取得します。
	 * @return apGetAmountRange
	 */
	public String getApGetAmountRange() {
		return apGetAmountRange;
	}

	/**
	 * apGetAmountRange
	 * @param apGetAmountRangeを設定します。
	 */
	public void setApGetAmountRange(String apGetAmountRange) {
		this.apGetAmountRange = apGetAmountRange;
	}

	/**
	 * apGetAmountRangeNameを取得します。
	 * @return apGetAmountRangeName
	 */
	public String getApGetAmountRangeName() {
		return apGetAmountRangeName;
	}

	/**
	 * apGetAmountRangeName
	 * @param apGetAmountRangeNameを設定します。
	 */
	public void setApGetAmountRangeName(String apGetAmountRangeName) {
		this.apGetAmountRangeName = apGetAmountRangeName;
	}

	/**
	 * apGetAmountRangeUseCostSecTypeを取得します。
	 * @return apGetAmountRangeUseCostSecType
	 */
	public String getApGetAmountRangeUseCostSecType() {
		return apGetAmountRangeUseCostSecType;
	}

	/**
	 * apGetAmountRangeUseCostSecType
	 * @param apGetAmountRangeUseCostSecTypeを設定します。
	 */
	public void setApGetAmountRangeUseCostSecType(
			String apGetAmountRangeUseCostSecType) {
		this.apGetAmountRangeUseCostSecType = apGetAmountRangeUseCostSecType;
	}

	/**
	 * apGetAmountRangeUseLineTypeを取得します。
	 * @return apGetAmountRangeUseLineType
	 */
	public String getApGetAmountRangeUseLineType() {
		return apGetAmountRangeUseLineType;
	}

	/**
	 * apGetAmountRangeUseLineType
	 * @param apGetAmountRangeUseLineTypeを設定します。
	 */
	public void setApGetAmountRangeUseLineType(String apGetAmountRangeUseLineType) {
		this.apGetAmountRangeUseLineType = apGetAmountRangeUseLineType;
	}

	/**
	 * specialApproveFlagを取得します。
	 * @return specialApproveFlag
	 */
	public String getSpecialApproveFlag() {
		return specialApproveFlag;
	}

	/**
	 * specialApproveFlag
	 * @param specialApproveFlagを設定します。
	 */
	public void setSpecialApproveFlag(String specialApproveFlag) {
		this.specialApproveFlag = specialApproveFlag;
	}

	/**
	 * specialApproveDateを取得します。
	 * @return specialApproveDate
	 */
	public Date getSpecialApproveDate() {
		return specialApproveDate;
	}

	/**
	 * specialApproveDate
	 * @param specialApproveDateを設定します。
	 */
	public void setSpecialApproveDate(Date specialApproveDate) {
		this.specialApproveDate = specialApproveDate;
	}

	/**
	 * apCreateStaffCodeを取得します。
	 * @return apCreateStaffCode
	 */
	public String getApCreateStaffCode() {
		return apCreateStaffCode;
	}

	/**
	 * apCreateStaffCode
	 * @param apCreateStaffCodeを設定します。
	 */
	public void setApCreateStaffCode(String apCreateStaffCode) {
		this.apCreateStaffCode = apCreateStaffCode;
	}

	/**
	 * apCreateStaffNameを取得します。
	 * @return apCreateStaffName
	 */
	public String getApCreateStaffName() {
		return apCreateStaffName;
	}

	/**
	 * apCreateStaffName
	 * @param apCreateStaffNameを設定します。
	 */
	public void setApCreateStaffName(String apCreateStaffName) {
		this.apCreateStaffName = apCreateStaffName;
	}

	/**
	 * apCreateCompanyCodeを取得します。
	 * @return apCreateCompanyCode
	 */
	public String getApCreateCompanyCode() {
		return apCreateCompanyCode;
	}

	/**
	 * apCreateCompanyCode
	 * @param apCreateCompanyCodeを設定します。
	 */
	public void setApCreateCompanyCode(String apCreateCompanyCode) {
		this.apCreateCompanyCode = apCreateCompanyCode;
	}

	/**
	 * apCreateCompanyNameを取得します。
	 * @return apCreateCompanyName
	 */
	public String getApCreateCompanyName() {
		return apCreateCompanyName;
	}

	/**
	 * apCreateCompanyName
	 * @param apCreateCompanyNameを設定します。
	 */
	public void setApCreateCompanyName(String apCreateCompanyName) {
		this.apCreateCompanyName = apCreateCompanyName;
	}

	/**
	 * apCreateSectionCodeを取得します。
	 * @return apCreateSectionCode
	 */
	public String getApCreateSectionCode() {
		return apCreateSectionCode;
	}

	/**
	 * apCreateSectionCode
	 * @param apCreateSectionCodeを設定します。
	 */
	public void setApCreateSectionCode(String apCreateSectionCode) {
		this.apCreateSectionCode = apCreateSectionCode;
	}

	/**
	 * apCreateSectionNameを取得します。
	 * @return apCreateSectionName
	 */
	public String getApCreateSectionName() {
		return apCreateSectionName;
	}

	/**
	 * apCreateSectionName
	 * @param apCreateSectionNameを設定します。
	 */
	public void setApCreateSectionName(String apCreateSectionName) {
		this.apCreateSectionName = apCreateSectionName;
	}

	/**
	 * apCreateSectionYearを取得します。
	 * @return apCreateSectionYear
	 */
	public Integer getApCreateSectionYear() {
		return apCreateSectionYear;
	}

	/**
	 * apCreateSectionYear
	 * @param apCreateSectionYearを設定します。
	 */
	public void setApCreateSectionYear(Integer apCreateSectionYear) {
		this.apCreateSectionYear = apCreateSectionYear;
	}

	/**
	 * apStaffCodeを取得します。
	 * @return apStaffCode
	 */
	public String getApStaffCode() {
		return apStaffCode;
	}

	/**
	 * apStaffCode
	 * @param apStaffCodeを設定します。
	 */
	public void setApStaffCode(String apStaffCode) {
		this.apStaffCode = apStaffCode;
	}

	/**
	 * apStaffNameを取得します。
	 * @return apStaffName
	 */
	public String getApStaffName() {
		return apStaffName;
	}

	/**
	 * apStaffName
	 * @param apStaffNameを設定します。
	 */
	public void setApStaffName(String apStaffName) {
		this.apStaffName = apStaffName;
	}

	/**
	 * apCompanyCodeを取得します。
	 * @return apCompanyCode
	 */
	public String getApCompanyCode() {
		return apCompanyCode;
	}

	/**
	 * apCompanyCode
	 * @param apCompanyCodeを設定します。
	 */
	public void setApCompanyCode(String apCompanyCode) {
		this.apCompanyCode = apCompanyCode;
	}

	/**
	 * apCompanyNameを取得します。
	 * @return apCompanyName
	 */
	public String getApCompanyName() {
		return apCompanyName;
	}

	/**
	 * apCompanyName
	 * @param apCompanyNameを設定します。
	 */
	public void setApCompanyName(String apCompanyName) {
		this.apCompanyName = apCompanyName;
	}

	/**
	 * apSectionCodeを取得します。
	 * @return apSectionCode
	 */
	public String getApSectionCode() {
		return apSectionCode;
	}

	/**
	 * apSectionCode
	 * @param apSectionCodeを設定します。
	 */
	public void setApSectionCode(String apSectionCode) {
		this.apSectionCode = apSectionCode;
	}

	/**
	 * apSectionNameを取得します。
	 * @return apSectionName
	 */
	public String getApSectionName() {
		return apSectionName;
	}

	/**
	 * apSectionName
	 * @param apSectionNameを設定します。
	 */
	public void setApSectionName(String apSectionName) {
		this.apSectionName = apSectionName;
	}

	/**
	 * apSectionYearを取得します。
	 * @return apSectionYear
	 */
	public Integer getApSectionYear() {
		return apSectionYear;
	}

	/**
	 * apSectionYear
	 * @param apSectionYearを設定します。
	 */
	public void setApSectionYear(Integer apSectionYear) {
		this.apSectionYear = apSectionYear;
	}

	/**
	 * apTelを取得します。
	 * @return apTel
	 */
	public String getApTel() {
		return apTel;
	}

	/**
	 * apTel
	 * @param apTelを設定します。
	 */
	public void setApTel(String apTel) {
		this.apTel = apTel;
	}

	/**
	 * apSkipApproveFlagを取得します。
	 * @return apSkipApproveFlag
	 */
	public String getApSkipApproveFlag() {
		return apSkipApproveFlag;
	}

	/**
	 * apSkipApproveFlag
	 * @param apSkipApproveFlagを設定します。
	 */
	public void setApSkipApproveFlag(String apSkipApproveFlag) {
		this.apSkipApproveFlag = apSkipApproveFlag;
	}

	/**
	 * apRegistStaffCodeを取得します。
	 * @return apRegistStaffCode
	 */
	public String getApRegistStaffCode() {
		return apRegistStaffCode;
	}

	/**
	 * apRegistStaffCode
	 * @param apRegistStaffCodeを設定します。
	 */
	public void setApRegistStaffCode(String apRegistStaffCode) {
		this.apRegistStaffCode = apRegistStaffCode;
	}

	/**
	 * apRegistStaffNameを取得します。
	 * @return apRegistStaffName
	 */
	public String getApRegistStaffName() {
		return apRegistStaffName;
	}

	/**
	 * apRegistStaffName
	 * @param apRegistStaffNameを設定します。
	 */
	public void setApRegistStaffName(String apRegistStaffName) {
		this.apRegistStaffName = apRegistStaffName;
	}

	/**
	 * apRegistCompanyCodeを取得します。
	 * @return apRegistCompanyCode
	 */
	public String getApRegistCompanyCode() {
		return apRegistCompanyCode;
	}

	/**
	 * apRegistCompanyCode
	 * @param apRegistCompanyCodeを設定します。
	 */
	public void setApRegistCompanyCode(String apRegistCompanyCode) {
		this.apRegistCompanyCode = apRegistCompanyCode;
	}

	/**
	 * apRegistCompanyNameを取得します。
	 * @return apRegistCompanyName
	 */
	public String getApRegistCompanyName() {
		return apRegistCompanyName;
	}

	/**
	 * apRegistCompanyName
	 * @param apRegistCompanyNameを設定します。
	 */
	public void setApRegistCompanyName(String apRegistCompanyName) {
		this.apRegistCompanyName = apRegistCompanyName;
	}

	/**
	 * apRegistSectionCodeを取得します。
	 * @return apRegistSectionCode
	 */
	public String getApRegistSectionCode() {
		return apRegistSectionCode;
	}

	/**
	 * apRegistSectionCode
	 * @param apRegistSectionCodeを設定します。
	 */
	public void setApRegistSectionCode(String apRegistSectionCode) {
		this.apRegistSectionCode = apRegistSectionCode;
	}

	/**
	 * apRegistSectionNameを取得します。
	 * @return apRegistSectionName
	 */
	public String getApRegistSectionName() {
		return apRegistSectionName;
	}

	/**
	 * apRegistSectionName
	 * @param apRegistSectionNameを設定します。
	 */
	public void setApRegistSectionName(String apRegistSectionName) {
		this.apRegistSectionName = apRegistSectionName;
	}

	/**
	 * apRegistSectionYearを取得します。
	 * @return apRegistSectionYear
	 */
	public Integer getApRegistSectionYear() {
		return apRegistSectionYear;
	}

	/**
	 * apRegistSectionYear
	 * @param apRegistSectionYearを設定します。
	 */
	public void setApRegistSectionYear(Integer apRegistSectionYear) {
		this.apRegistSectionYear = apRegistSectionYear;
	}

	/**
	 * getPurposeCodeを取得します。
	 * @return getPurposeCode
	 */
	public String getGetPurposeCode() {
		return getPurposeCode;
	}

	/**
	 * getPurposeCode
	 * @param getPurposeCodeを設定します。
	 */
	public void setGetPurposeCode(String getPurposeCode) {
		this.getPurposeCode = getPurposeCode;
	}

	/**
	 * getPurposeNameを取得します。
	 * @return getPurposeName
	 */
	public String getGetPurposeName() {
		return getPurposeName;
	}

	/**
	 * getPurposeName
	 * @param getPurposeNameを設定します。
	 */
	public void setGetPurposeName(String getPurposeName) {
		this.getPurposeName = getPurposeName;
	}

	/**
	 * getReasonを取得します。
	 * @return getReason
	 */
	public String getGetReason() {
		return getReason;
	}

	/**
	 * getReason
	 * @param getReasonを設定します。
	 */
	public void setGetReason(String getReason) {
		this.getReason = getReason;
	}

	/**
	 * getItemNameを取得します。
	 * @return getItemName
	 */
	public String getGetItemName() {
		return getItemName;
	}

	/**
	 * getItemName
	 * @param getItemNameを設定します。
	 */
	public void setGetItemName(String getItemName) {
		this.getItemName = getItemName;
	}

	/**
	 * getItemCloudTypeを取得します。
	 * @return getItemCloudType
	 */
	public String getGetItemCloudType() {
		return getItemCloudType;
	}

	/**
	 * getItemCloudType
	 * @param getItemCloudTypeを設定します。
	 */
	public void setGetItemCloudType(String getItemCloudType) {
		this.getItemCloudType = getItemCloudType;
	}

	/**
	 * getItemGroupCodeを取得します。
	 * @return getItemGroupCode
	 */
	public String getGetItemGroupCode() {
		return getItemGroupCode;
	}

	/**
	 * getItemGroupCode
	 * @param getItemGroupCodeを設定します。
	 */
	public void setGetItemGroupCode(String getItemGroupCode) {
		this.getItemGroupCode = getItemGroupCode;
	}

	/**
	 * getItemGroupNameを取得します。
	 * @return getItemGroupName
	 */
	public String getGetItemGroupName() {
		return getItemGroupName;
	}

	/**
	 * getItemGroupName
	 * @param getItemGroupNameを設定します。
	 */
	public void setGetItemGroupName(String getItemGroupName) {
		this.getItemGroupName = getItemGroupName;
	}

	/**
	 * getDeliveryDateを取得します。
	 * @return getDeliveryDate
	 */
	public Date getGetDeliveryDate() {
		return getDeliveryDate;
	}

	/**
	 * getDeliveryDate
	 * @param getDeliveryDateを設定します。
	 */
	public void setGetDeliveryDate(Date getDeliveryDate) {
		this.getDeliveryDate = getDeliveryDate;
	}

	/**
	 * getReturnDateを取得します。
	 * @return getReturnDate
	 */
	public Date getGetReturnDate() {
		return getReturnDate;
	}

	/**
	 * getReturnDate
	 * @param getReturnDateを設定します。
	 */
	public void setGetReturnDate(Date getReturnDate) {
		this.getReturnDate = getReturnDate;
	}

	/**
	 * getNeedCioJudgeFlagを取得します。
	 * @return getNeedCioJudgeFlag
	 */
	public String getGetNeedCioJudgeFlag() {
		return getNeedCioJudgeFlag;
	}

	/**
	 * getNeedCioJudgeFlag
	 * @param getNeedCioJudgeFlagを設定します。
	 */
	public void setGetNeedCioJudgeFlag(String getNeedCioJudgeFlag) {
		this.getNeedCioJudgeFlag = getNeedCioJudgeFlag;
	}

	/**
	 * getSystemSectionDeployFlagを取得します。
	 * @return getSystemSectionDeployFlag
	 */
	public String getGetSystemSectionDeployFlag() {
		return getSystemSectionDeployFlag;
	}

	/**
	 * getSystemSectionDeployFlag
	 * @param getSystemSectionDeployFlagを設定します。
	 */
	public void setGetSystemSectionDeployFlag(String getSystemSectionDeployFlag) {
		this.getSystemSectionDeployFlag = getSystemSectionDeployFlag;
	}

	/**
	 * getIntraInventoryFlagを取得します。
	 * @return getIntraInventoryFlag
	 */
	public String getGetIntraInventoryFlag() {
		return getIntraInventoryFlag;
	}

	/**
	 * getIntraInventoryFlag
	 * @param getIntraInventoryFlagを設定します。
	 */
	public void setGetIntraInventoryFlag(String getIntraInventoryFlag) {
		this.getIntraInventoryFlag = getIntraInventoryFlag;
	}

	/**
	 * getLeReCompanyCodeを取得します。
	 * @return getLeReCompanyCode
	 */
	public String getGetLeReCompanyCode() {
		return getLeReCompanyCode;
	}

	/**
	 * getLeReCompanyCode
	 * @param getLeReCompanyCodeを設定します。
	 */
	public void setGetLeReCompanyCode(String getLeReCompanyCode) {
		this.getLeReCompanyCode = getLeReCompanyCode;
	}

	/**
	 * getLeReCompanyNameを取得します。
	 * @return getLeReCompanyName
	 */
	public String getGetLeReCompanyName() {
		return getLeReCompanyName;
	}

	/**
	 * getLeReCompanyName
	 * @param getLeReCompanyNameを設定します。
	 */
	public void setGetLeReCompanyName(String getLeReCompanyName) {
		this.getLeReCompanyName = getLeReCompanyName;
	}

	/**
	 * getLeReEstimateNumberを取得します。
	 * @return getLeReEstimateNumber
	 */
	public String getGetLeReEstimateNumber() {
		return getLeReEstimateNumber;
	}

	/**
	 * getLeReEstimateNumber
	 * @param getLeReEstimateNumberを設定します。
	 */
	public void setGetLeReEstimateNumber(String getLeReEstimateNumber) {
		this.getLeReEstimateNumber = getLeReEstimateNumber;
	}

	/**
	 * getLeReCommentを取得します。
	 * @return getLeReComment
	 */
	public String getGetLeReComment() {
		return getLeReComment;
	}

	/**
	 * getLeReComment
	 * @param getLeReCommentを設定します。
	 */
	public void setGetLeReComment(String getLeReComment) {
		this.getLeReComment = getLeReComment;
	}

	/**
	 * getLeReMonthAmountを取得します。
	 * @return getLeReMonthAmount
	 */
	public Long getGetLeReMonthAmount() {
		return getLeReMonthAmount;
	}

	/**
	 * getLeReMonthAmount
	 * @param getLeReMonthAmountを設定します。
	 */
	public void setGetLeReMonthAmount(Long getLeReMonthAmount) {
		this.getLeReMonthAmount = getLeReMonthAmount;
	}

	/**
	 * getLeReCountを取得します。
	 * @return getLeReCount
	 */
	public Integer getGetLeReCount() {
		return getLeReCount;
	}

	/**
	 * getLeReCount
	 * @param getLeReCountを設定します。
	 */
	public void setGetLeReCount(Integer getLeReCount) {
		this.getLeReCount = getLeReCount;
	}

	/**
	 * getLeReTotalAmountを取得します。
	 * @return getLeReTotalAmount
	 */
	public Long getGetLeReTotalAmount() {
		return getLeReTotalAmount;
	}

	/**
	 * getLeReTotalAmount
	 * @param getLeReTotalAmountを設定します。
	 */
	public void setGetLeReTotalAmount(Long getLeReTotalAmount) {
		this.getLeReTotalAmount = getLeReTotalAmount;
	}

	/**
	 * getLeEappIdを取得します。
	 * @return getLeEappId
	 */
	public String getGetLeEappId() {
		return getLeEappId;
	}

	/**
	 * getLeEappId
	 * @param getLeEappIdを設定します。
	 */
	public void setGetLeEappId(String getLeEappId) {
		this.getLeEappId = getLeEappId;
	}

	/**
	 * getTotalAmountを取得します。
	 * @return getTotalAmount
	 */
	public Long getGetTotalAmount() {
		return getTotalAmount;
	}

	/**
	 * getTotalAmount
	 * @param getTotalAmountを設定します。
	 */
	public void setGetTotalAmount(Long getTotalAmount) {
		this.getTotalAmount = getTotalAmount;
	}

	/**
	 * attDescriptionを取得します。
	 * @return attDescription
	 */
	public String getAttDescription() {
		return attDescription;
	}

	/**
	 * attDescription
	 * @param attDescriptionを設定します。
	 */
	public void setAttDescription(String attDescription) {
		this.attDescription = attDescription;
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
	 * holCompanyNameを取得します。
	 * @return holCompanyName
	 */
	public String getHolCompanyName() {
		return holCompanyName;
	}

	/**
	 * holCompanyName
	 * @param holCompanyNameを設定します。
	 */
	public void setHolCompanyName(String holCompanyName) {
		this.holCompanyName = holCompanyName;
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
	 * holOfficeOutsideFlagを取得します。
	 * @return holOfficeOutsideFlag
	 */
	public String getHolOfficeOutsideFlag() {
		return holOfficeOutsideFlag;
	}

	/**
	 * holOfficeOutsideFlag
	 * @param holOfficeOutsideFlagを設定します。
	 */
	public void setHolOfficeOutsideFlag(String holOfficeOutsideFlag) {
		this.holOfficeOutsideFlag = holOfficeOutsideFlag;
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
	 * holOfficeOutsidePcIdを取得します。
	 * @return holOfficeOutsidePcId
	 */
	public String getHolOfficeOutsidePcId() {
		return holOfficeOutsidePcId;
	}

	/**
	 * holOfficeOutsidePcId
	 * @param holOfficeOutsidePcIdを設定します。
	 */
	public void setHolOfficeOutsidePcId(String holOfficeOutsidePcId) {
		this.holOfficeOutsidePcId = holOfficeOutsidePcId;
	}

	/**
	 * holOfficeDescriptionを取得します。
	 * @return holOfficeDescription
	 */
	public String getHolOfficeDescription() {
		return holOfficeDescription;
	}

	/**
	 * holOfficeDescription
	 * @param holOfficeDescriptionを設定します。
	 */
	public void setHolOfficeDescription(String holOfficeDescription) {
		this.holOfficeDescription = holOfficeDescription;
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
	 * costDepPrjUndecidedFlagを取得します。
	 * @return costDepPrjUndecidedFlag
	 */
	public String getCostDepPrjUndecidedFlag() {
		return costDepPrjUndecidedFlag;
	}

	/**
	 * costDepPrjUndecidedFlag
	 * @param costDepPrjUndecidedFlagを設定します。
	 */
	public void setCostDepPrjUndecidedFlag(String costDepPrjUndecidedFlag) {
		this.costDepPrjUndecidedFlag = costDepPrjUndecidedFlag;
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
	 * costDepPrjTypeを取得します。
	 * @return costDepPrjType
	 */
	public String getCostDepPrjType() {
		return costDepPrjType;
	}

	/**
	 * costDepPrjType
	 * @param costDepPrjTypeを設定します。
	 */
	public void setCostDepPrjType(String costDepPrjType) {
		this.costDepPrjType = costDepPrjType;
	}

	/**
	 * descriptionApを取得します。
	 * @return descriptionAp
	 */
	public String getDescriptionAp() {
		return descriptionAp;
	}

	/**
	 * descriptionAp
	 * @param descriptionApを設定します。
	 */
	public void setDescriptionAp(String descriptionAp) {
		this.descriptionAp = descriptionAp;
	}

	/**
	 * descriptionAdminを取得します。
	 * @return descriptionAdmin
	 */
	public String getDescriptionAdmin() {
		return descriptionAdmin;
	}

	/**
	 * descriptionAdmin
	 * @param descriptionAdminを設定します。
	 */
	public void setDescriptionAdmin(String descriptionAdmin) {
		this.descriptionAdmin = descriptionAdmin;
	}

	/**
	 * lineEditApproveFlagを取得します。
	 * @return lineEditApproveFlag
	 */
	public String getLineEditApproveFlag() {
		return lineEditApproveFlag;
	}

	/**
	 * lineEditApproveFlag
	 * @param lineEditApproveFlagを設定します。
	 */
	public void setLineEditApproveFlag(String lineEditApproveFlag) {
		this.lineEditApproveFlag = lineEditApproveFlag;
	}

	/**
	 * stopRegistFlagを取得します。
	 * @return stopRegistFlag
	 */
	public String getStopRegistFlag() {
		return stopRegistFlag;
	}

	/**
	 * stopRegistFlag
	 * @param stopRegistFlagを設定します。
	 */
	public void setStopRegistFlag(String stopRegistFlag) {
		this.stopRegistFlag = stopRegistFlag;
	}

	/**
	 * registCompleteFlagを取得します。
	 * @return registCompleteFlag
	 */
	public String getRegistCompleteFlag() {
		return registCompleteFlag;
	}

	/**
	 * registCompleteFlag
	 * @param registCompleteFlagを設定します。
	 */
	public void setRegistCompleteFlag(String registCompleteFlag) {
		this.registCompleteFlag = registCompleteFlag;
	}

	/**
	 * approveSuperiorFlagを取得します。
	 * @return approveSuperiorFlag
	 */
	public String getApproveSuperiorFlag() {
		return approveSuperiorFlag;
	}

	/**
	 * approveSuperiorFlag
	 * @param approveSuperiorFlagを設定します。
	 */
	public void setApproveSuperiorFlag(String approveSuperiorFlag) {
		this.approveSuperiorFlag = approveSuperiorFlag;
	}

	/**
	 * approveDateを取得します。
	 * @return approveDate
	 */
	public Date getApproveDate() {
		return approveDate;
	}

	/**
	 * approveDate
	 * @param approveDateを設定します。
	 */
	public void setApproveDate(Date approveDate) {
		this.approveDate = approveDate;
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
	 * costCompanyCodeを取得します。
	 * @return costCompanyCode
	 */
	public String getCostCompanyCode() {
		return costCompanyCode;
	}

	/**
	 * costCompanyCode
	 * @param costCompanyCodeを設定します。
	 */
	public void setCostCompanyCode(String costCompanyCode) {
		this.costCompanyCode = costCompanyCode;
	}

	/**
	 * costSectionCodeを取得します。
	 * @return costSectionCode
	 */
	public String getCostSectionCode() {
		return costSectionCode;
	}

	/**
	 * costSectionCode
	 * @param costSectionCodeを設定します。
	 */
	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
	}

	/**
	 * costSectionNameを取得します。
	 * @return costSectionName
	 */
	public String getCostSectionName() {
		return costSectionName;
	}

	/**
	 * costSectionName
	 * @param costSectionNameを設定します。
	 */
	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	/**
	 * getLeReMonthLessAmountを取得します。
	 * @return getLeReMonthLessAmount
	 */
	public Long getGetLeReMonthLessAmount() {
		return getLeReMonthLessAmount;
	}

	/**
	 * getLeReMonthLessAmount
	 * @param getLeReMonthLessAmountを設定します。
	 */
	public void setGetLeReMonthLessAmount(Long getLeReMonthLessAmount) {
		this.getLeReMonthLessAmount = getLeReMonthLessAmount;
	}

	/**
	 * reoReceptNumberを取得します。
	 * @return reoReceptNumber
	 */
	public String getReoReceptNumber() {
		return reoReceptNumber;
	}

	/**
	 * reoReceptNumber
	 * @param reoReceptNumberを設定します。
	 */
	public void setReoReceptNumber(String reoReceptNumber) {
		this.reoReceptNumber = reoReceptNumber;
	}

	/**
	 * reoOrderTypeを取得します。
	 * @return reoOrderType
	 */
	public String getReoOrderType() {
		return reoOrderType;
	}

	/**
	 * reoOrderType
	 * @param reoOrderTypeを設定します。
	 */
	public void setReoOrderType(String reoOrderType) {
		this.reoOrderType = reoOrderType;
	}

	/**
	 * reoAstStaffCodeを取得します。
	 * @return reoAstStaffCode
	 */
	public String getReoAstStaffCode() {
		return reoAstStaffCode;
	}

	/**
	 * reoAstStaffCode
	 * @param reoAstStaffCodeを設定します。
	 */
	public void setReoAstStaffCode(String reoAstStaffCode) {
		this.reoAstStaffCode = reoAstStaffCode;
	}

	/**
	 * reoAstCompanyCodeを取得します。
	 * @return reoAstCompanyCode
	 */
	public String getReoAstCompanyCode() {
		return reoAstCompanyCode;
	}

	/**
	 * reoAstCompanyCode
	 * @param reoAstCompanyCodeを設定します。
	 */
	public void setReoAstCompanyCode(String reoAstCompanyCode) {
		this.reoAstCompanyCode = reoAstCompanyCode;
	}

	/**
	 * reoAstSectionCodeを取得します。
	 * @return reoAstSectionCode
	 */
	public String getReoAstSectionCode() {
		return reoAstSectionCode;
	}

	/**
	 * reoAstSectionCode
	 * @param reoAstSectionCodeを設定します。
	 */
	public void setReoAstSectionCode(String reoAstSectionCode) {
		this.reoAstSectionCode = reoAstSectionCode;
	}

	/**
	 * reoAstSectionYearを取得します。
	 * @return reoAstSectionYear
	 */
	public Integer getReoAstSectionYear() {
		return reoAstSectionYear;
	}

	/**
	 * reoAstSectionYear
	 * @param reoAstSectionYearを設定します。
	 */
	public void setReoAstSectionYear(Integer reoAstSectionYear) {
		this.reoAstSectionYear = reoAstSectionYear;
	}

	/**
	 * reoAstTelを取得します。
	 * @return reoAstTel
	 */
	public String getReoAstTel() {
		return reoAstTel;
	}

	/**
	 * reoAstTel
	 * @param reoAstTelを設定します。
	 */
	public void setReoAstTel(String reoAstTel) {
		this.reoAstTel = reoAstTel;
	}

	/**
	 * reoAstFaxを取得します。
	 * @return reoAstFax
	 */
	public String getReoAstFax() {
		return reoAstFax;
	}

	/**
	 * reoAstFax
	 * @param reoAstFaxを設定します。
	 */
	public void setReoAstFax(String reoAstFax) {
		this.reoAstFax = reoAstFax;
	}

	/**
	 * reoAstMailAddressを取得します。
	 * @return reoAstMailAddress
	 */
	public String getReoAstMailAddress() {
		return reoAstMailAddress;
	}

	/**
	 * reoAstMailAddress
	 * @param reoAstMailAddressを設定します。
	 */
	public void setReoAstMailAddress(String reoAstMailAddress) {
		this.reoAstMailAddress = reoAstMailAddress;
	}

	/**
	 * reoUseHopeStartDateを取得します。
	 * @return reoUseHopeStartDate
	 */
	public Date getReoUseHopeStartDate() {
		return reoUseHopeStartDate;
	}

	/**
	 * reoUseHopeStartDate
	 * @param reoUseHopeStartDateを設定します。
	 */
	public void setReoUseHopeStartDate(Date reoUseHopeStartDate) {
		this.reoUseHopeStartDate = reoUseHopeStartDate;
	}

	/**
	 * reoInvAddressを取得します。
	 * @return reoInvAddress
	 */
	public String getReoInvAddress() {
		return reoInvAddress;
	}

	/**
	 * reoInvAddress
	 * @param reoInvAddressを設定します。
	 */
	public void setReoInvAddress(String reoInvAddress) {
		this.reoInvAddress = reoInvAddress;
	}

	/**
	 * reoInvTelを取得します。
	 * @return reoInvTel
	 */
	public String getReoInvTel() {
		return reoInvTel;
	}

	/**
	 * reoInvTel
	 * @param reoInvTelを設定します。
	 */
	public void setReoInvTel(String reoInvTel) {
		this.reoInvTel = reoInvTel;
	}

	/**
	 * reoInvFaxを取得します。
	 * @return reoInvFax
	 */
	public String getReoInvFax() {
		return reoInvFax;
	}

	/**
	 * reoInvFax
	 * @param reoInvFaxを設定します。
	 */
	public void setReoInvFax(String reoInvFax) {
		this.reoInvFax = reoInvFax;
	}

	/**
	 * reoInvMailAddressを取得します。
	 * @return reoInvMailAddress
	 */
	public String getReoInvMailAddress() {
		return reoInvMailAddress;
	}

	/**
	 * reoInvMailAddress
	 * @param reoInvMailAddressを設定します。
	 */
	public void setReoInvMailAddress(String reoInvMailAddress) {
		this.reoInvMailAddress = reoInvMailAddress;
	}

	/**
	 * reoDlvStaffCodeを取得します。
	 * @return reoDlvStaffCode
	 */
	public String getReoDlvStaffCode() {
		return reoDlvStaffCode;
	}

	/**
	 * reoDlvStaffCode
	 * @param reoDlvStaffCodeを設定します。
	 */
	public void setReoDlvStaffCode(String reoDlvStaffCode) {
		this.reoDlvStaffCode = reoDlvStaffCode;
	}

	/**
	 * reoDlvCompanyCodeを取得します。
	 * @return reoDlvCompanyCode
	 */
	public String getReoDlvCompanyCode() {
		return reoDlvCompanyCode;
	}

	/**
	 * reoDlvCompanyCode
	 * @param reoDlvCompanyCodeを設定します。
	 */
	public void setReoDlvCompanyCode(String reoDlvCompanyCode) {
		this.reoDlvCompanyCode = reoDlvCompanyCode;
	}

	/**
	 * reoDlvSectionCodeを取得します。
	 * @return reoDlvSectionCode
	 */
	public String getReoDlvSectionCode() {
		return reoDlvSectionCode;
	}

	/**
	 * reoDlvSectionCode
	 * @param reoDlvSectionCodeを設定します。
	 */
	public void setReoDlvSectionCode(String reoDlvSectionCode) {
		this.reoDlvSectionCode = reoDlvSectionCode;
	}

	/**
	 * reoDlvSectionYearを取得します。
	 * @return reoDlvSectionYear
	 */
	public Integer getReoDlvSectionYear() {
		return reoDlvSectionYear;
	}

	/**
	 * reoDlvSectionYear
	 * @param reoDlvSectionYearを設定します。
	 */
	public void setReoDlvSectionYear(Integer reoDlvSectionYear) {
		this.reoDlvSectionYear = reoDlvSectionYear;
	}

	/**
	 * reoDlvAddressを取得します。
	 * @return reoDlvAddress
	 */
	public String getReoDlvAddress() {
		return reoDlvAddress;
	}

	/**
	 * reoDlvAddress
	 * @param reoDlvAddressを設定します。
	 */
	public void setReoDlvAddress(String reoDlvAddress) {
		this.reoDlvAddress = reoDlvAddress;
	}

	/**
	 * reoDlvTelを取得します。
	 * @return reoDlvTel
	 */
	public String getReoDlvTel() {
		return reoDlvTel;
	}

	/**
	 * reoDlvTel
	 * @param reoDlvTelを設定します。
	 */
	public void setReoDlvTel(String reoDlvTel) {
		this.reoDlvTel = reoDlvTel;
	}

	/**
	 * reoDlvFaxを取得します。
	 * @return reoDlvFax
	 */
	public String getReoDlvFax() {
		return reoDlvFax;
	}

	/**
	 * reoDlvFax
	 * @param reoDlvFaxを設定します。
	 */
	public void setReoDlvFax(String reoDlvFax) {
		this.reoDlvFax = reoDlvFax;
	}

	/**
	 * reoDlvMailAddressを取得します。
	 * @return reoDlvMailAddress
	 */
	public String getReoDlvMailAddress() {
		return reoDlvMailAddress;
	}

	/**
	 * reoDlvMailAddress
	 * @param reoDlvMailAddressを設定します。
	 */
	public void setReoDlvMailAddress(String reoDlvMailAddress) {
		this.reoDlvMailAddress = reoDlvMailAddress;
	}

	/**
	 * reoDescriptionを取得します。
	 * @return reoDescription
	 */
	public String getReoDescription() {
		return reoDescription;
	}

	/**
	 * reoDescription
	 * @param reoDescriptionを設定します。
	 */
	public void setReoDescription(String reoDescription) {
		this.reoDescription = reoDescription;
	}

	/**
	 * reoOrderDateを取得します。
	 * @return reoOrderDate
	 */
	public Date getReoOrderDate() {
		return reoOrderDate;
	}

	/**
	 * reoOrderDate
	 * @param reoOrderDateを設定します。
	 */
	public void setReoOrderDate(Date reoOrderDate) {
		this.reoOrderDate = reoOrderDate;
	}

	/**
	 * reoOrderFlagを取得します。
	 * @return reoOrderFlag
	 */
	public String getReoOrderFlag() {
		return reoOrderFlag;
	}

	/**
	 * reoOrderFlag
	 * @param reoOrderFlagを設定します。
	 */
	public void setReoOrderFlag(String reoOrderFlag) {
		this.reoOrderFlag = reoOrderFlag;
	}

	/**
	 * reoInvStaffInputTypeを取得します。
	 * @return reoInvStaffInputType
	 */
	public String getReoInvStaffInputType() {
		return reoInvStaffInputType;
	}

	/**
	 * reoInvStaffInputType
	 * @param reoInvStaffInputTypeを設定します。
	 */
	public void setReoInvStaffInputType(String reoInvStaffInputType) {
		this.reoInvStaffInputType = reoInvStaffInputType;
	}

	/**
	 * reoInvStaffNameを取得します。
	 * @return reoInvStaffName
	 */
	public String getReoInvStaffName() {
		return reoInvStaffName;
	}

	/**
	 * reoInvStaffName
	 * @param reoInvStaffNameを設定します。
	 */
	public void setReoInvStaffName(String reoInvStaffName) {
		this.reoInvStaffName = reoInvStaffName;
	}

	/**
	 * reoInvCompanyNameを取得します。
	 * @return reoInvCompanyName
	 */
	public String getReoInvCompanyName() {
		return reoInvCompanyName;
	}

	/**
	 * reoInvCompanyName
	 * @param reoInvCompanyNameを設定します。
	 */
	public void setReoInvCompanyName(String reoInvCompanyName) {
		this.reoInvCompanyName = reoInvCompanyName;
	}

	/**
	 * reoInvSectionNameを取得します。
	 * @return reoInvSectionName
	 */
	public String getReoInvSectionName() {
		return reoInvSectionName;
	}

	/**
	 * reoInvSectionName
	 * @param reoInvSectionNameを設定します。
	 */
	public void setReoInvSectionName(String reoInvSectionName) {
		this.reoInvSectionName = reoInvSectionName;
	}

	/**
	 * reoOrderTypeNameを取得します。
	 * @return reoOrderTypeName
	 */
	public String getReoOrderTypeName() {
		return reoOrderTypeName;
	}

	/**
	 * reoOrderTypeName
	 * @param reoOrderTypeNameを設定します。
	 */
	public void setReoOrderTypeName(String reoOrderTypeName) {
		this.reoOrderTypeName = reoOrderTypeName;
	}

	/**
	 * reoAstStaffNameを取得します。
	 * @return reoAstStaffName
	 */
	public String getReoAstStaffName() {
		return reoAstStaffName;
	}

	/**
	 * reoAstStaffName
	 * @param reoAstStaffNameを設定します。
	 */
	public void setReoAstStaffName(String reoAstStaffName) {
		this.reoAstStaffName = reoAstStaffName;
	}

	/**
	 * reoDlvStaffNameを取得します。
	 * @return reoDlvStaffName
	 */
	public String getReoDlvStaffName() {
		return reoDlvStaffName;
	}

	/**
	 * reoDlvStaffName
	 * @param reoDlvStaffNameを設定します。
	 */
	public void setReoDlvStaffName(String reoDlvStaffName) {
		this.reoDlvStaffName = reoDlvStaffName;
	}

	/**
	 * reoAstCompanyNameを取得します。
	 * @return reoAstCompanyName
	 */
	public String getReoAstCompanyName() {
		return reoAstCompanyName;
	}

	/**
	 * reoAstCompanyName
	 * @param reoAstCompanyNameを設定します。
	 */
	public void setReoAstCompanyName(String reoAstCompanyName) {
		this.reoAstCompanyName = reoAstCompanyName;
	}

	/**
	 * reoDlvCompanyNameを取得します。
	 * @return reoDlvCompanyName
	 */
	public String getReoDlvCompanyName() {
		return reoDlvCompanyName;
	}

	/**
	 * reoDlvCompanyName
	 * @param reoDlvCompanyNameを設定します。
	 */
	public void setReoDlvCompanyName(String reoDlvCompanyName) {
		this.reoDlvCompanyName = reoDlvCompanyName;
	}

	/**
	 * reoAstSectionNameを取得します。
	 * @return reoAstSectionName
	 */
	public String getReoAstSectionName() {
		return reoAstSectionName;
	}

	/**
	 * reoAstSectionName
	 * @param reoAstSectionNameを設定します。
	 */
	public void setReoAstSectionName(String reoAstSectionName) {
		this.reoAstSectionName = reoAstSectionName;
	}

	/**
	 * reoDlvSectionNameを取得します。
	 * @return reoDlvSectionName
	 */
	public String getReoDlvSectionName() {
		return reoDlvSectionName;
	}

	/**
	 * reoDlvSectionName
	 * @param reoDlvSectionNameを設定します。
	 */
	public void setReoDlvSectionName(String reoDlvSectionName) {
		this.reoDlvSectionName = reoDlvSectionName;
	}

	/**
	 * apGetTanLinePurListを取得します。
	 * @return apGetTanLinePurList
	 */
	public List<ApGetTanLinePur> getApGetTanLinePurList() {
		return apGetTanLinePurList;
	}

	/**
	 * apGetTanLinePurList
	 * @param apGetTanLinePurListを設定します。
	 */
	public void setApGetTanLinePurList(List<ApGetTanLinePur> apGetTanLinePurList) {
		this.apGetTanLinePurList = apGetTanLinePurList;
	}

	/**
	 * apGetTanLineOtrCostListを取得します。
	 * @return apGetTanLineOtrCostList
	 */
	public List<ApGetTanLineOtrCost> getApGetTanLineOtrCostList() {
		return apGetTanLineOtrCostList;
	}

	/**
	 * apGetTanLineOtrCostList
	 * @param apGetTanLineOtrCostListを設定します。
	 */
	public void setApGetTanLineOtrCostList(
			List<ApGetTanLineOtrCost> apGetTanLineOtrCostList) {
		this.apGetTanLineOtrCostList = apGetTanLineOtrCostList;
	}

	/**
	 * apGetTanLineAttListを取得します。
	 * @return apGetTanLineAttList
	 */
	public List<ApGetTanLineAtt> getApGetTanLineAttList() {
		return apGetTanLineAttList;
	}

	/**
	 * apGetTanLineAttList
	 * @param apGetTanLineAttListを設定します。
	 */
	public void setApGetTanLineAttList(List<ApGetTanLineAtt> apGetTanLineAttList) {
		this.apGetTanLineAttList = apGetTanLineAttList;
	}

	/**
	 * apGetTanLineCostSecListを取得します。
	 * @return apGetTanLineCostSecList
	 */
	public List<ApGetTanLineCostSec> getApGetTanLineCostSecList() {
		return apGetTanLineCostSecList;
	}

	/**
	 * apGetTanLineCostSecList
	 * @param apGetTanLineCostSecListを設定します。
	 */
	public void setApGetTanLineCostSecList(
			List<ApGetTanLineCostSec> apGetTanLineCostSecList) {
		this.apGetTanLineCostSecList = apGetTanLineCostSecList;
	}

	/**
	 * apGetTanLineAstListを取得します。
	 * @return apGetTanLineAstList
	 */
	public List<ApGetTanLineAst> getApGetTanLineAstList() {
		return apGetTanLineAstList;
	}

	/**
	 * apGetTanLineAstList
	 * @param apGetTanLineAstListを設定します。
	 */
	public void setApGetTanLineAstList(List<ApGetTanLineAst> apGetTanLineAstList) {
		this.apGetTanLineAstList = apGetTanLineAstList;
	}

	/**
	 * apGetTanLineLicListを取得します。
	 * @return apGetTanLineLicList
	 */
	public List<ApGetTanLineLic> getApGetTanLineLicList() {
		return apGetTanLineLicList;
	}

	/**
	 * apGetTanLineLicList
	 * @param apGetTanLineLicListを設定します。
	 */
	public void setApGetTanLineLicList(List<ApGetTanLineLic> apGetTanLineLicList) {
		this.apGetTanLineLicList = apGetTanLineLicList;
	}

	/**
	 * getPurEstimateNumberを取得します。
	 * @return getPurEstimateNumber
	 */
	public String getGetPurEstimateNumber() {
		return getPurEstimateNumber;
	}

	/**
	 * getPurEstimateNumber
	 * @param getPurEstimateNumberを設定します。
	 */
	public void setGetPurEstimateNumber(String getPurEstimateNumber) {
		this.getPurEstimateNumber = getPurEstimateNumber;
	}

	/**
	 * reoInvCompanyOfficailNameを取得します。
	 * @return reoInvCompanyOfficailName
	 */
	public String getReoInvCompanyOfficailName() {
		return reoInvCompanyOfficailName;
	}

	/**
	 * reoInvCompanyOfficailName
	 * @param reoInvCompanyOfficailNameを設定します。
	 */
	public void setReoInvCompanyOfficailName(String reoInvCompanyOfficailName) {
		this.reoInvCompanyOfficailName = reoInvCompanyOfficailName;
	}


	/**
	 * reoDisuseFlagを取得します。
	 * @return reoDisuseFlag
	 */
	public String getReoDisuseFlag() {
		return reoDisuseFlag;
	}

	/**
	 * reoDisuseFlag
	 * @param reoDisuseFlagを設定します。
	 */
	public void setReoDisuseFlag(String reoDisuseFlag) {
		this.reoDisuseFlag = reoDisuseFlag;
	}

	/**
	 * getLeReDateCountを取得します。
	 * @return getLeReDateCount
	 */
	public Integer getGetLeReDateCount() {
		return getLeReDateCount;
	}

	/**
	 * getLeReDateCount
	 * @param getLeReDateCountを設定します。
	 */
	public void setGetLeReDateCount(Integer getLeReDateCount) {
		this.getLeReDateCount = getLeReDateCount;
	}

	public String getReoAstOir() {
		return reoAstOir;
	}

	public void setReoAstOir(String reoAstOir) {
		this.reoAstOir = reoAstOir;
	}

	public Integer getCostSectionYear() {
		return costSectionYear;
	}

	public void setCostSectionYear(Integer costSectionYear) {
		this.costSectionYear = costSectionYear;
	}

}
