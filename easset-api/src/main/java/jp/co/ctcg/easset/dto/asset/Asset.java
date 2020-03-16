/*===================================================================
 * ファイル名 : Asset.java
 * 概要説明   : 情報機器等登録申請
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-02 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.asset;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class Asset implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String assetId;							// 登録申請番号
	private Date createDate;						// 作成日
	private String createStaffCode;					// 作成者社員番号
	private Date updateDate;						// 更新日
	private String updateStaffCode;					// 更新者社員番号
	private Integer version;						// バージョン
	private Long apGetTanLineAstId;					// 取得申請紐付け
	private Integer apGetTanLineAstLineSeq;			// 取得申請明細No
	private String updateComment;					// 更新コメント
	private Long eappId;							// e申請書類ID
	private String apStatus;						// 申請書ステータス 1:未申請,2:申請中,3:登録完了,4:差戻し 汎用マスタ-AP_STATUS_ASSET
	private String apStatusName;					// 申請書ステータス名
	private Date apDate;							// 申請日
	private String apCreateStaffCode;				// 起票者社員番号
	private String apCreateStaffName;				// 起票者氏名
	private String apCreateCompanyCode;				// 起票者所属会社コード
	private String apCreateCompanyName;				// 起票者所属会社名
	private String apCreateSectionCode;				// 起票者所属部署コード
	private Integer apCreateSectionYear;			// 起票者所属部署年度
	private String apCreateSectionName;				// 起票者所属部署名
	private String apStaffCode;						// 申請者社員番号
	private String apStaffName;						// 申請者氏名
	private String apCompanyCode;					// 申請会社コード
	private String apCompanyName;					// 申請会社名
	private String apSectionCode;					// 申請部署コード
	private Integer apSectionYear;					// 申請部署年度
	private String apSectionName;					// 申請部署名
	private String apTel;							// 連絡先TEL
	private String apOfficeName;					// 申請者オフィス
	private String astName;							// 資産(機器)名 情報機器等と同項目
	private String astCategory1Code;				// 資産(機器)大分類 情報機器等と同項目
	private String astCategory1Name;				// 資産(機器)大分類名 情報機器等と同項目
	private String astCategory2Code;				// 資産(機器)小分類 情報機器等と同項目
	private String astCategory2Name;				// 資産(機器)小分類名 情報機器等と同項目
	private String astQuantityManageType;			// 資産(機器)数量管理区分
	private String astSerialManageType;				// 資産(機器)シリアル番号管理区分
	private String astShapeCode;					// 筐体/形状 情報機器等と同項目
	private String astShapeName;					// 筐体/形状名 情報機器等と同項目
	private String astMakerCode;					// メーカーコード 情報機器等と同項目
	private String astMakerName;					// メーカー名 情報機器等と同項目
	private String astShopName;						// 販売元名 情報機器等と同項目
	private String astMakerModel;					// メーカー型番 情報機器等と同項目
	private String astSerialCode;					// シリアル番号 情報機器等と同項目
	private Date astGuaranteeTermDate;				// 保証期限 情報機器等と同項目
	private String astGetType;						// 取得形態 情報機器等と同項目
	private String astGetTypeName;					// 取得形態名 情報機器等と同項目
	private String astSystemSectionDeployFlag;		// 情報システム部配備フラグ 情報機器等と同項目
	private String astSystemSectionDeployFlagName;	// 情報システム部配備フラグ名 情報機器等と同項目
	private String astAssetType;					// 資産区分 情報機器等と同項目
	private String astAssetTypeName;				// 資産区分名 情報機器等と同項目
	private String astHolderCode;					// 資産保有者コード 情報機器等と同項目
	private String astHolderName;					// 資産保有者名 情報機器等と同項目
	private String astManageType;					// 管理区分 情報機器等と同項目
	private String astManageTypeName;				// 管理区分名 情報機器等と同項目
	private String astOirEnable;					// OIR番号有効フラグ 情報機器等と同項目
	private String astOir;							// OIR番号 情報機器等と同項目
	private Date astManageEndDate;					// 管理終了日 情報機器等と同項目
	private String holCompanyCode;					// 保有会社コード 情報機器等と同項目
	private String holCompanyName;					// 保有会社名 情報機器等と同項目
	private String holSectionCode;					// 保有部署コード 情報機器等と同項目
	private Integer holSectionYear;					// 保有部署年度 情報機器等と同項目
	private String holSectionName;					// 保有部署名 情報機器等と同項目
	private String holStaffCode;					// 資産管理担当者 情報機器等と同項目
	private String holStaffName;					// 資産管理担当者氏名 情報機器等と同項目
	private String holOfficeCode;					// 設置場所 情報機器等と同項目
	private String holOfficeName;					// 設置場所名 情報機器等と同項目
	private Integer holOfficeFloor;					// 設置場所階数 情報機器等と同項目
	private String holOfficeRoomNum;				// 部屋番号 情報機器等と同項目
	private String holOfficeRackNum;				// ラック番号 情報機器等と同項目
	private String holPurposeCode;					// 使用目的 情報機器等と同項目
	private String holPurposeName;					// 使用目的名 情報機器等と同項目
	private String holOfficeDescription;			// 設置場所補足 情報機器等と同項目
	private String holGetStaffCode;					// 取得(設置)者社員番号 情報機器等と同項目
	private String holGetStaffName;					// 取得(設置)者氏名 情報機器等と同項目
	private String holGetCompanyCode;				// 取得(設置)者所属会社コード 情報機器等と同項目
	private String holGetCompanyName;				// 取得(設置)者所属会社名 情報機器等と同項目
	private String holGetSectionCode;				// 取得(設置)者所属部署コード 情報機器等と同項目
	private Integer holGetSectionYear;				// 取得(設置)者所属部署年度 情報機器等と同項目
	private String holGetSectionName;				// 取得(設置)者所属部署名 情報機器等と同項目
	private Date holGetDate;						// 取得(設置)日 情報機器等と同項目
	private Integer holQuantity;					// 数量 情報機器等と同項目
	private String useCompanyCode;					// 使用会社コード 情報機器等と同項目
	private String useCompanyName;					// 使用会社名 情報機器等と同項目
	private String useSectionCode;					// 使用部署コード 情報機器等と同項目
	private Integer useSectionYear;					// 使用部署年度 情報機器等と同項目
	private String useSectionName;					// 使用部署名 情報機器等と同項目
	private String useStaffCode;					// 使用者社員番号 情報機器等と同項目
	private String useStaffName;					// 使用者氏名 情報機器等と同項目
	private String useStaffCompanyCode;				// 使用者所属会社コード 情報機器等と同項目
	private String useStaffCompanyName;				// 使用者所属会社名 情報機器等と同項目
	private String useStaffSectionCode;				// 使用者所属部署コード 情報機器等と同項目
	private Integer useStaffSectionYear;			// 使用者所属部署年度 情報機器等と同項目
	private String useStaffSectionName;				// 使用者所属部署名 情報機器等と同項目
	private Date useStartDate;						// 使用開始日 情報機器等と同項目
	private String useCommonFlag;					// 共有ユーザーフラグ 情報機器等と同項目
	private String useCommonFlagName;				// 共有ユーザーフラグ名 情報機器等と同項目
	private String netHostName;						// ホスト名 情報機器等と同項目
	private String netEguardPermitType;				// eGuard接続許可タイプ 情報機器等と同項目
	private String mntContractCode;					// 保守契約番号 情報機器等と同項目
	private String mntContractCompanyName;			// 保守契約先 情報機器等と同項目
	private Date mntContractStartDate;				// 保守契約期間FROM 情報機器等と同項目
	private Date mntContractEndDate;				// 保守契約期間TO 情報機器等と同項目
	private Long mntContractAmount;					// 保守契約金額 情報機器等と同項目
	private String mntContractRegistCode;			// 保守契約ユーザー登録番号 情報機器等と同項目
	private Date mntContractRegistDate;				// 保守契約登録日 情報機器等と同項目
	private String mntContractStaffCode;			// 保守契約担当者社員番号 情報機器等と同項目
	private String mntContractStaffName;			// 保守契約担当者氏名 情報機器等と同項目
	private String mntContractServiceLevel;			// 保守契約サービスレベル
	private String mntContractDescription;			// 保守契約備考
	private String mntContractCode2;				// 保守契約番号2
	private String mntContractCompanyName2;			// 保守契約先2
	private Date mntContractStartDate2;				// 保守契約期間FROM2
	private Date mntContractEndDate2;				// 保守契約期間TO2
	private Long mntContractAmount2;				// 保守契約金額2
	private String mntContractRegistCode2;			// 保守契約ユーザー登録番号2
	private Date mntContractRegistDate2;			// 保守契約登録日2
	private String mntContractStaffCode2;			// 保守契約担当者社員番号2
	private String mntContractStaffName2;			// 保守契約担当者氏名2
	private String mntContractServiceLevel2;		// 保守契約サービスレベル2
	private String mntContractDescription2;			// 保守契約備考2
	private String mntContractCode3;				// 保守契約番号3
	private String mntContractCompanyName3;			// 保守契約先3
	private Date mntContractStartDate3;				// 保守契約期間FROM3
	private Date mntContractEndDate3;				// 保守契約期間TO3
	private Long mntContractAmount3;				// 保守契約金額3
	private String mntContractRegistCode3;			// 保守契約ユーザー登録番号3
	private Date mntContractRegistDate3;			// 保守契約登録日3
	private String mntContractStaffCode3;			// 保守契約担当者社員番号3
	private String mntContractStaffName3;			// 保守契約担当者氏名3
	private String mntContractServiceLevel3;		// 保守契約サービスレベル3
	private String mntContractDescription3;			// 保守契約備考3
	private String dscDescription;					// 備考 情報機器等と同項目
	private String dscAttribute1;					// 管理項目1 情報機器等と同項目
	private String dscAttribute2;					// 管理項目2 情報機器等と同項目
	private String dscAttribute3;					// 管理項目3 情報機器等と同項目
	private String dscAttribute4;					// 管理項目4 情報機器等と同項目
	private String dscAttribute5;					// 管理項目5 情報機器等と同項目
	private String dscAttribute6;					// 管理項目6 情報機器等と同項目
	private String dscAttribute7;					// 管理項目7 情報機器等と同項目
	private String dscAttribute8;					// 管理項目8 情報機器等と同項目
	private String dscAttribute9;					// 管理項目9 情報機器等と同項目
	private String dscAttribute10;					// 管理項目10 情報機器等と同項目
	private String dscAttribute11;					// 管理項目11 情報機器等と同項目
	private String dscAttribute12;					// 管理項目12 情報機器等と同項目
	private String dscAttribute13;					// 管理項目13 情報機器等と同項目
	private String dscAttribute14;					// 管理項目14 情報機器等と同項目
	private String dscAttribute15;					// 管理項目15 情報機器等と同項目
	private String dscAttribute16;					// 管理項目16 情報機器等と同項目
	private String dscAttribute17;					// 管理項目17 情報機器等と同項目
	private String dscAttribute18;					// 管理項目18 情報機器等と同項目
	private String dscAttribute19;					// 管理項目19 情報機器等と同項目
	private String dscAttribute20;					// 管理項目20 情報機器等と同項目
	private String dscFailureAssetId;				// 故障交換元情報機器管理番号 情報機器等と同項目
	private String invInCompanyActualFlag;			// 社内実地棚卸対象フラグ 情報機器等と同項目
	private String invInCompanyActualFlagName;		// 社内実地棚卸対象フラグ名 情報機器等と同項目
	private String invBarcode;						// バーコード情報 情報機器等と同項目
	private String invSealIssueFlag;				// シール発行対象フラグ 情報機器等と同項目
	private String invSealIssueFlagName;			// シール発行対象フラグ名 情報機器等と同項目
	private Date invSealIssueDate;					// シール発行日 情報機器等と同項目
	private String invSealIssueDescription;			// シール発行補足 情報機器等と同項目
	private String deleteFlag;						// 抹消フラグ 情報機器等と同項目
	private Date deleteDate;						// 抹消日 情報機器等と同項目
	private String deleteReason;					// 抹消理由 情報機器等と同項目
	private String getApplicationId;				// 取得申請書番号 情報機器等と同項目
	private String registApplicationId;				// 登録申請番号 情報機器等と同項目
	private String contractNum;						// 契約番号 情報機器等と同項目
	private String dreamsNum;						// DREAMS番号 情報機器等と同項目
	private String parentAssetId;					// 親情報機器管理番号 情報機器等と同項目

	private String apCreateTel;						// 起票者:連絡先TEL

	// 固定資産、物件
	private String contractEda;						// 契約枝番
	private String shisanNum;						// 資産番号
	private Long koyunoL;							// 物件の固有番号(画面に表示しない)
	private Long koyunoF;							// 資産の固有番号(画面に表示しない)

	//	明細
	private List<AssetLineComUsr> assetLineComUsr;	// 情報機器等登録申請_共有ユーザー明細
	private List<AssetLineOs> assetLineOs;			// 情報機器等登録申請_OS明細
	private List<AssetLineNetwork> assetLineNetwork;// 情報機器等登録申請_ネットワーク明細
	private List<AssetLineInv> assetLineInv;		// 情報機器等_棚卸明細

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		assetId = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		version = Function.getExternalInteger(input.readObject());
		apGetTanLineAstId = Function.getExternalLong(input.readObject());
		apGetTanLineAstLineSeq = Function.getExternalInteger(input.readObject());
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
		astName = (String)input.readObject();
		astCategory1Code = (String)input.readObject();
		astCategory1Name = (String)input.readObject();
		astCategory2Code = (String)input.readObject();
		astCategory2Name = (String)input.readObject();
		astQuantityManageType = (String)input.readObject();
		astSerialManageType = (String)input.readObject();
		astShapeCode = (String)input.readObject();
		astShapeName = (String)input.readObject();
		astMakerCode = (String)input.readObject();
		astMakerName = (String)input.readObject();
		astShopName = (String)input.readObject();
		astMakerModel = (String)input.readObject();
		astSerialCode = (String)input.readObject();
		astGuaranteeTermDate = (Date)input.readObject();
		astGetType = (String)input.readObject();
		astGetTypeName = (String)input.readObject();
		astSystemSectionDeployFlag = (String)input.readObject();
		astSystemSectionDeployFlagName = (String)input.readObject();
		astAssetType = (String)input.readObject();
		astAssetTypeName = (String)input.readObject();
		astHolderCode = (String)input.readObject();
		astHolderName = (String)input.readObject();
		astManageType = (String)input.readObject();
		astManageTypeName = (String)input.readObject();
		astOirEnable = (String)input.readObject();
		astOir = (String)input.readObject();
		astManageEndDate = (Date)input.readObject();
		holCompanyCode = (String)input.readObject();
		holCompanyName = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holSectionName = (String)input.readObject();
		holStaffCode = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holOfficeCode = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		holOfficeFloor = Function.getExternalInteger(input.readObject());
		holOfficeRoomNum = (String)input.readObject();
		holOfficeRackNum = (String)input.readObject();
		holPurposeCode = (String)input.readObject();
		holPurposeName = (String)input.readObject();
		holOfficeDescription = (String)input.readObject();
		holGetStaffCode = (String)input.readObject();
		holGetStaffName = (String)input.readObject();
		holGetCompanyCode = (String)input.readObject();
		holGetCompanyName = (String)input.readObject();
		holGetSectionCode = (String)input.readObject();
		holGetSectionYear = Function.getExternalInteger(input.readObject());
		holGetSectionName = (String)input.readObject();
		holGetDate = (Date)input.readObject();
		holQuantity = Function.getExternalInteger(input.readObject());
		useCompanyCode = (String)input.readObject();
		useCompanyName = (String)input.readObject();
		useSectionCode = (String)input.readObject();
		useSectionYear = Function.getExternalInteger(input.readObject());
		useSectionName = (String)input.readObject();
		useStaffCode = (String)input.readObject();
		useStaffName = (String)input.readObject();
		useStaffCompanyCode = (String)input.readObject();
		useStaffCompanyName = (String)input.readObject();
		useStaffSectionCode = (String)input.readObject();
		useStaffSectionYear = Function.getExternalInteger(input.readObject());
		useStaffSectionName = (String)input.readObject();
		useStartDate = (Date)input.readObject();
		useCommonFlag = (String)input.readObject();
		useCommonFlagName = (String)input.readObject();
		netHostName = (String)input.readObject();
		netEguardPermitType = (String)input.readObject();
		mntContractCode = (String)input.readObject();
		mntContractCompanyName = (String)input.readObject();
		mntContractStartDate = (Date)input.readObject();
		mntContractEndDate = (Date)input.readObject();
		mntContractAmount = Function.getExternalLong(input.readObject());
		mntContractRegistCode = (String)input.readObject();
		mntContractRegistDate = (Date)input.readObject();
		mntContractStaffCode = (String)input.readObject();
		mntContractStaffName = (String)input.readObject();
		mntContractServiceLevel = (String)input.readObject();
		mntContractDescription = (String)input.readObject();
		mntContractCode2 = (String)input.readObject();
		mntContractCompanyName2 = (String)input.readObject();
		mntContractStartDate2 = (Date)input.readObject();
		mntContractEndDate2 = (Date)input.readObject();
		mntContractAmount2 = Function.getExternalLong(input.readObject());
		mntContractRegistCode2 = (String)input.readObject();
		mntContractRegistDate2 = (Date)input.readObject();
		mntContractStaffCode2 = (String)input.readObject();
		mntContractStaffName3 = (String)input.readObject();
		mntContractServiceLevel2 = (String)input.readObject();
		mntContractDescription2 = (String)input.readObject();
		mntContractCode3 = (String)input.readObject();
		mntContractCompanyName3 = (String)input.readObject();
		mntContractStartDate3 = (Date)input.readObject();
		mntContractEndDate3 = (Date)input.readObject();
		mntContractAmount3 = Function.getExternalLong(input.readObject());
		mntContractRegistCode3 = (String)input.readObject();
		mntContractRegistDate3 = (Date)input.readObject();
		mntContractStaffCode3 = (String)input.readObject();
		mntContractStaffName3 = (String)input.readObject();
		mntContractServiceLevel3 = (String)input.readObject();
		mntContractDescription3 = (String)input.readObject();
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
		dscFailureAssetId = (String)input.readObject();
		invInCompanyActualFlag = (String)input.readObject();
		invInCompanyActualFlagName = (String)input.readObject();
		invBarcode = (String)input.readObject();
		invSealIssueFlag = (String)input.readObject();
		invSealIssueFlagName = (String)input.readObject();
		invSealIssueDate = (Date)input.readObject();
		invSealIssueDescription = (String)input.readObject();
		deleteFlag = (String)input.readObject();
		deleteDate = (Date)input.readObject();
		deleteReason = (String)input.readObject();
		getApplicationId = (String)input.readObject();
		registApplicationId = (String)input.readObject();
		contractNum = (String)input.readObject();
		dreamsNum = (String)input.readObject();
		parentAssetId = (String)input.readObject();

		apCreateTel = (String)input.readObject();

		// 固定資産、物件
		contractEda = (String)input.readObject();
		shisanNum = (String)input.readObject();
		koyunoL = Function.getExternalLong(input.readObject());
		koyunoF = Function.getExternalLong(input.readObject());

		//	明細
		assetLineComUsr = (List<AssetLineComUsr>)input.readObject();
		assetLineOs = (List<AssetLineOs>)input.readObject();
		assetLineNetwork = (List<AssetLineNetwork>)input.readObject();
		assetLineInv = (List<AssetLineInv>)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(assetId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(version);
		output.writeObject(apGetTanLineAstId);
		output.writeObject(apGetTanLineAstLineSeq);
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
		output.writeObject(astName);
		output.writeObject(astCategory1Code);
		output.writeObject(astCategory1Name);
		output.writeObject(astCategory2Code);
		output.writeObject(astCategory2Name);
		output.writeObject(astQuantityManageType);
		output.writeObject(astSerialManageType);
		output.writeObject(astShapeCode);
		output.writeObject(astShapeName);
		output.writeObject(astMakerCode);
		output.writeObject(astMakerName);
		output.writeObject(astShopName);
		output.writeObject(astMakerModel);
		output.writeObject(astSerialCode);
		output.writeObject(astGuaranteeTermDate);
		output.writeObject(astGetType);
		output.writeObject(astGetTypeName);
		output.writeObject(astSystemSectionDeployFlag);
		output.writeObject(astSystemSectionDeployFlagName);
		output.writeObject(astAssetType);
		output.writeObject(astAssetTypeName);
		output.writeObject(astHolderCode);
		output.writeObject(astHolderName);
		output.writeObject(astManageType);
		output.writeObject(astManageTypeName);
		output.writeObject(astOirEnable);
		output.writeObject(astOir);
		output.writeObject(astManageEndDate);
		output.writeObject(holCompanyCode);
		output.writeObject(holCompanyName);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(holSectionName);
		output.writeObject(holStaffCode);
		output.writeObject(holStaffName);
		output.writeObject(holOfficeCode);
		output.writeObject(holOfficeName);
		output.writeObject(holOfficeFloor);
		output.writeObject(holOfficeRoomNum);
		output.writeObject(holOfficeRackNum);
		output.writeObject(holPurposeCode);
		output.writeObject(holPurposeName);
		output.writeObject(holOfficeDescription);
		output.writeObject(holGetStaffCode);
		output.writeObject(holGetStaffName);
		output.writeObject(holGetCompanyCode);
		output.writeObject(holGetCompanyName);
		output.writeObject(holGetSectionCode);
		output.writeObject(holGetSectionYear);
		output.writeObject(holGetSectionName);
		output.writeObject(holGetDate);
		output.writeObject(holQuantity);
		output.writeObject(useCompanyCode);
		output.writeObject(useCompanyName);
		output.writeObject(useSectionCode);
		output.writeObject(useSectionYear);
		output.writeObject(useSectionName);
		output.writeObject(useStaffCode);
		output.writeObject(useStaffName);
		output.writeObject(useStaffCompanyCode);
		output.writeObject(useStaffCompanyName);
		output.writeObject(useStaffSectionCode);
		output.writeObject(useStaffSectionYear);
		output.writeObject(useStaffSectionName);
		output.writeObject(useStartDate);
		output.writeObject(useCommonFlag);
		output.writeObject(useCommonFlagName);
		output.writeObject(netHostName);
		output.writeObject(netEguardPermitType);
		output.writeObject(mntContractCode);
		output.writeObject(mntContractCompanyName);
		output.writeObject(mntContractStartDate);
		output.writeObject(mntContractEndDate);
		output.writeObject(mntContractAmount);
		output.writeObject(mntContractRegistCode);
		output.writeObject(mntContractRegistDate);
		output.writeObject(mntContractStaffCode);
		output.writeObject(mntContractStaffName);
		output.writeObject(mntContractServiceLevel);
		output.writeObject(mntContractDescription);
		output.writeObject(mntContractCode2);
		output.writeObject(mntContractCompanyName2);
		output.writeObject(mntContractStartDate2);
		output.writeObject(mntContractEndDate2);
		output.writeObject(mntContractAmount2);
		output.writeObject(mntContractRegistCode2);
		output.writeObject(mntContractRegistDate2);
		output.writeObject(mntContractStaffCode2);
		output.writeObject(mntContractStaffName2);
		output.writeObject(mntContractServiceLevel2);
		output.writeObject(mntContractDescription2);
		output.writeObject(mntContractCode3);
		output.writeObject(mntContractCompanyName3);
		output.writeObject(mntContractStartDate3);
		output.writeObject(mntContractEndDate3);
		output.writeObject(mntContractAmount3);
		output.writeObject(mntContractRegistCode3);
		output.writeObject(mntContractRegistDate3);
		output.writeObject(mntContractStaffCode3);
		output.writeObject(mntContractStaffName3);
		output.writeObject(mntContractServiceLevel3);
		output.writeObject(mntContractDescription3);
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
		output.writeObject(dscFailureAssetId);
		output.writeObject(invInCompanyActualFlag);
		output.writeObject(invInCompanyActualFlagName);
		output.writeObject(invBarcode);
		output.writeObject(invSealIssueFlag);
		output.writeObject(invSealIssueFlagName);
		output.writeObject(invSealIssueDate);
		output.writeObject(invSealIssueDescription);
		output.writeObject(deleteFlag);
		output.writeObject(deleteDate);
		output.writeObject(deleteReason);
		output.writeObject(getApplicationId);
		output.writeObject(registApplicationId);
		output.writeObject(contractNum);
		output.writeObject(dreamsNum);
		output.writeObject(parentAssetId);

		output.writeObject(apCreateTel);

		// 固定資産、物件
		output.writeObject(contractEda);
		output.writeObject(shisanNum);
		output.writeObject(koyunoL);
		output.writeObject(koyunoF);

		//	明細
		output.writeObject(assetLineComUsr);
		output.writeObject(assetLineOs);
		output.writeObject(assetLineNetwork);
		output.writeObject(assetLineInv);
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
	 * apOfficeNameを取得します。
	 * @return apOfficeName
	 */
	public String getApOfficeName() {
		return apOfficeName;
	}

	/**
	 * apOfficeName
	 * @param apOfficeNameを設定します。
	 */
	public void setApOfficeName(String apOfficeName) {
		this.apOfficeName = apOfficeName;
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
	 * astShapeCodeを取得します。
	 * @return astShapeCode
	 */
	public String getAstShapeCode() {
		return astShapeCode;
	}

	/**
	 * astShapeCode
	 * @param astShapeCodeを設定します。
	 */
	public void setAstShapeCode(String astShapeCode) {
		this.astShapeCode = astShapeCode;
	}

	/**
	 * astMakerCodeを取得します。
	 * @return astMakerCode
	 */
	public String getAstMakerCode() {
		return astMakerCode;
	}

	/**
	 * astMakerCode
	 * @param astMakerCodeを設定します。
	 */
	public void setAstMakerCode(String astMakerCode) {
		this.astMakerCode = astMakerCode;
	}

	/**
	 * astMakerNameを取得します。
	 * @return astMakerName
	 */
	public String getAstMakerName() {
		return astMakerName;
	}

	/**
	 * astMakerName
	 * @param astMakerNameを設定します。
	 */
	public void setAstMakerName(String astMakerName) {
		this.astMakerName = astMakerName;
	}

	/**
	 * astShopNameを取得します。
	 * @return astShopName
	 */
	public String getAstShopName() {
		return astShopName;
	}

	/**
	 * astShopName
	 * @param astShopNameを設定します。
	 */
	public void setAstShopName(String astShopName) {
		this.astShopName = astShopName;
	}

	/**
	 * astMakerModelを取得します。
	 * @return astMakerModel
	 */
	public String getAstMakerModel() {
		return astMakerModel;
	}

	/**
	 * astMakerModel
	 * @param astMakerModelを設定します。
	 */
	public void setAstMakerModel(String astMakerModel) {
		this.astMakerModel = astMakerModel;
	}

	/**
	 * astSerialCodeを取得します。
	 * @return astSerialCode
	 */
	public String getAstSerialCode() {
		return astSerialCode;
	}

	/**
	 * astSerialCode
	 * @param astSerialCodeを設定します。
	 */
	public void setAstSerialCode(String astSerialCode) {
		this.astSerialCode = astSerialCode;
	}

	/**
	 * astGuaranteeTermDateを取得します。
	 * @return astGuaranteeTermDate
	 */
	public Date getAstGuaranteeTermDate() {
		return astGuaranteeTermDate;
	}

	/**
	 * astGuaranteeTermDate
	 * @param astGuaranteeTermDateを設定します。
	 */
	public void setAstGuaranteeTermDate(Date astGuaranteeTermDate) {
		this.astGuaranteeTermDate = astGuaranteeTermDate;
	}

	/**
	 * astGetTypeを取得します。
	 * @return astGetType
	 */
	public String getAstGetType() {
		return astGetType;
	}

	/**
	 * astGetType
	 * @param astGetTypeを設定します。
	 */
	public void setAstGetType(String astGetType) {
		this.astGetType = astGetType;
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
	 * astHolderCodeを取得します。
	 * @return astHolderCode
	 */
	public String getAstHolderCode() {
		return astHolderCode;
	}

	/**
	 * astHolderCode
	 * @param astHolderCodeを設定します。
	 */
	public void setAstHolderCode(String astHolderCode) {
		this.astHolderCode = astHolderCode;
	}

	/**
	 * astHolderNameを取得します。
	 * @return astHolderName
	 */
	public String getAstHolderName() {
		return astHolderName;
	}

	/**
	 * astHolderName
	 * @param astHolderNameを設定します。
	 */
	public void setAstHolderName(String astHolderName) {
		this.astHolderName = astHolderName;
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
	 * astOirを取得します。
	 * @return astOir
	 */
	public String getAstOir() {
		return astOir;
	}

	/**
	 * astOir
	 * @param astOirを設定します。
	 */
	public void setAstOir(String astOir) {
		this.astOir = astOir;
	}

	/**
	 * astManageEndDateを取得します。
	 * @return astManageEndDate
	 */
	public Date getAstManageEndDate() {
		return astManageEndDate;
	}

	/**
	 * astManageEndDate
	 * @param astManageEndDateを設定します。
	 */
	public void setAstManageEndDate(Date astManageEndDate) {
		this.astManageEndDate = astManageEndDate;
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
	 * holOfficeRoomNumを取得します。
	 * @return holOfficeRoomNum
	 */
	public String getHolOfficeRoomNum() {
		return holOfficeRoomNum;
	}

	/**
	 * holOfficeRoomNum
	 * @param holOfficeRoomNumを設定します。
	 */
	public void setHolOfficeRoomNum(String holOfficeRoomNum) {
		this.holOfficeRoomNum = holOfficeRoomNum;
	}

	/**
	 * holOfficeRackNumを取得します。
	 * @return holOfficeRackNum
	 */
	public String getHolOfficeRackNum() {
		return holOfficeRackNum;
	}

	/**
	 * holOfficeRackNum
	 * @param holOfficeRackNumを設定します。
	 */
	public void setHolOfficeRackNum(String holOfficeRackNum) {
		this.holOfficeRackNum = holOfficeRackNum;
	}

	/**
	 * holPurposeCodeを取得します。
	 * @return holPurposeCode
	 */
	public String getHolPurposeCode() {
		return holPurposeCode;
	}

	/**
	 * holPurposeCode
	 * @param holPurposeCodeを設定します。
	 */
	public void setHolPurposeCode(String holPurposeCode) {
		this.holPurposeCode = holPurposeCode;
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
	 * holGetStaffCodeを取得します。
	 * @return holGetStaffCode
	 */
	public String getHolGetStaffCode() {
		return holGetStaffCode;
	}

	/**
	 * holGetStaffCode
	 * @param holGetStaffCodeを設定します。
	 */
	public void setHolGetStaffCode(String holGetStaffCode) {
		this.holGetStaffCode = holGetStaffCode;
	}

	/**
	 * holGetCompanyCodeを取得します。
	 * @return holGetCompanyCode
	 */
	public String getHolGetCompanyCode() {
		return holGetCompanyCode;
	}

	/**
	 * holGetCompanyCode
	 * @param holGetCompanyCodeを設定します。
	 */
	public void setHolGetCompanyCode(String holGetCompanyCode) {
		this.holGetCompanyCode = holGetCompanyCode;
	}

	/**
	 * holGetSectionCodeを取得します。
	 * @return holGetSectionCode
	 */
	public String getHolGetSectionCode() {
		return holGetSectionCode;
	}

	/**
	 * holGetSectionCode
	 * @param holGetSectionCodeを設定します。
	 */
	public void setHolGetSectionCode(String holGetSectionCode) {
		this.holGetSectionCode = holGetSectionCode;
	}

	/**
	 * holGetSectionYearを取得します。
	 * @return holGetSectionYear
	 */
	public Integer getHolGetSectionYear() {
		return holGetSectionYear;
	}

	/**
	 * holGetSectionYear
	 * @param holGetSectionYearを設定します。
	 */
	public void setHolGetSectionYear(Integer holGetSectionYear) {
		this.holGetSectionYear = holGetSectionYear;
	}

	/**
	 * holGetDateを取得します。
	 * @return holGetDate
	 */
	public Date getHolGetDate() {
		return holGetDate;
	}

	/**
	 * holGetDate
	 * @param holGetDateを設定します。
	 */
	public void setHolGetDate(Date holGetDate) {
		this.holGetDate = holGetDate;
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
	 * useCompanyCodeを取得します。
	 * @return useCompanyCode
	 */
	public String getUseCompanyCode() {
		return useCompanyCode;
	}

	/**
	 * useCompanyCode
	 * @param useCompanyCodeを設定します。
	 */
	public void setUseCompanyCode(String useCompanyCode) {
		this.useCompanyCode = useCompanyCode;
	}

	/**
	 * useSectionCodeを取得します。
	 * @return useSectionCode
	 */
	public String getUseSectionCode() {
		return useSectionCode;
	}

	/**
	 * useSectionCode
	 * @param useSectionCodeを設定します。
	 */
	public void setUseSectionCode(String useSectionCode) {
		this.useSectionCode = useSectionCode;
	}

	/**
	 * useSectionYearを取得します。
	 * @return useSectionYear
	 */
	public Integer getUseSectionYear() {
		return useSectionYear;
	}

	/**
	 * useSectionYear
	 * @param useSectionYearを設定します。
	 */
	public void setUseSectionYear(Integer useSectionYear) {
		this.useSectionYear = useSectionYear;
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
	 * useStartDateを取得します。
	 * @return useStartDate
	 */
	public Date getUseStartDate() {
		return useStartDate;
	}

	/**
	 * useStartDate
	 * @param useStartDateを設定します。
	 */
	public void setUseStartDate(Date useStartDate) {
		this.useStartDate = useStartDate;
	}

	/**
	 * useCommonFlagを取得します。
	 * @return useCommonFlag
	 */
	public String getUseCommonFlag() {
		return useCommonFlag;
	}

	/**
	 * useCommonFlag
	 * @param useCommonFlagを設定します。
	 */
	public void setUseCommonFlag(String useCommonFlag) {
		this.useCommonFlag = useCommonFlag;
	}

	/**
	 * netHostNameを取得します。
	 * @return netHostName
	 */
	public String getNetHostName() {
		return netHostName;
	}

	/**
	 * netHostName
	 * @param netHostNameを設定します。
	 */
	public void setNetHostName(String netHostName) {
		this.netHostName = netHostName;
	}

	/**
	 * netEguardPermitTypeを取得します。
	 * @return netEguardPermitType
	 */
	public String getNetEguardPermitType() {
		return netEguardPermitType;
	}

	/**
	 * netEguardPermitType
	 * @param netEguardPermitTypeを設定します。
	 */
	public void setNetEguardPermitType(String netEguardPermitType) {
		this.netEguardPermitType = netEguardPermitType;
	}

	/**
	 * mntContractCodeを取得します。
	 * @return mntContractCode
	 */
	public String getMntContractCode() {
		return mntContractCode;
	}

	/**
	 * mntContractCode
	 * @param mntContractCodeを設定します。
	 */
	public void setMntContractCode(String mntContractCode) {
		this.mntContractCode = mntContractCode;
	}

	/**
	 * mntContractCompanyNameを取得します。
	 * @return mntContractCompanyName
	 */
	public String getMntContractCompanyName() {
		return mntContractCompanyName;
	}

	/**
	 * mntContractCompanyName
	 * @param mntContractCompanyNameを設定します。
	 */
	public void setMntContractCompanyName(String mntContractCompanyName) {
		this.mntContractCompanyName = mntContractCompanyName;
	}

	/**
	 * mntContractStartDateを取得します。
	 * @return mntContractStartDate
	 */
	public Date getMntContractStartDate() {
		return mntContractStartDate;
	}

	/**
	 * mntContractStartDate
	 * @param mntContractStartDateを設定します。
	 */
	public void setMntContractStartDate(Date mntContractStartDate) {
		this.mntContractStartDate = mntContractStartDate;
	}

	/**
	 * mntContractEndDateを取得します。
	 * @return mntContractEndDate
	 */
	public Date getMntContractEndDate() {
		return mntContractEndDate;
	}

	/**
	 * mntContractEndDate
	 * @param mntContractEndDateを設定します。
	 */
	public void setMntContractEndDate(Date mntContractEndDate) {
		this.mntContractEndDate = mntContractEndDate;
	}

	/**
	 * mntContractAmountを取得します。
	 * @return mntContractAmount
	 */
	public Long getMntContractAmount() {
		return mntContractAmount;
	}

	/**
	 * mntContractAmount
	 * @param mntContractAmountを設定します。
	 */
	public void setMntContractAmount(Long mntContractAmount) {
		this.mntContractAmount = mntContractAmount;
	}

	/**
	 * mntContractRegistCodeを取得します。
	 * @return mntContractRegistCode
	 */
	public String getMntContractRegistCode() {
		return mntContractRegistCode;
	}

	/**
	 * mntContractRegistCode
	 * @param mntContractRegistCodeを設定します。
	 */
	public void setMntContractRegistCode(String mntContractRegistCode) {
		this.mntContractRegistCode = mntContractRegistCode;
	}

	/**
	 * mntContractRegistDateを取得します。
	 * @return mntContractRegistDate
	 */
	public Date getMntContractRegistDate() {
		return mntContractRegistDate;
	}

	/**
	 * mntContractRegistDate
	 * @param mntContractRegistDateを設定します。
	 */
	public void setMntContractRegistDate(Date mntContractRegistDate) {
		this.mntContractRegistDate = mntContractRegistDate;
	}

	/**
	 * mntContractStaffCodeを取得します。
	 * @return mntContractStaffCode
	 */
	public String getMntContractStaffCode() {
		return mntContractStaffCode;
	}

	/**
	 * mntContractStaffCode
	 * @param mntContractStaffCodeを設定します。
	 */
	public void setMntContractStaffCode(String mntContractStaffCode) {
		this.mntContractStaffCode = mntContractStaffCode;
	}

	/**
	 * dscDescriptionを取得します。
	 * @return dscDescription
	 */
	public String getDscDescription() {
		return dscDescription;
	}

	/**
	 * dscDescription
	 * @param dscDescriptionを設定します。
	 */
	public void setDscDescription(String dscDescription) {
		this.dscDescription = dscDescription;
	}

	/**
	 * dscAttribute1を取得します。
	 * @return dscAttribute1
	 */
	public String getDscAttribute1() {
		return dscAttribute1;
	}

	/**
	 * dscAttribute1
	 * @param dscAttribute1を設定します。
	 */
	public void setDscAttribute1(String dscAttribute1) {
		this.dscAttribute1 = dscAttribute1;
	}

	/**
	 * dscAttribute2を取得します。
	 * @return dscAttribute2
	 */
	public String getDscAttribute2() {
		return dscAttribute2;
	}

	/**
	 * dscAttribute2
	 * @param dscAttribute2を設定します。
	 */
	public void setDscAttribute2(String dscAttribute2) {
		this.dscAttribute2 = dscAttribute2;
	}

	/**
	 * dscAttribute3を取得します。
	 * @return dscAttribute3
	 */
	public String getDscAttribute3() {
		return dscAttribute3;
	}

	/**
	 * dscAttribute3
	 * @param dscAttribute3を設定します。
	 */
	public void setDscAttribute3(String dscAttribute3) {
		this.dscAttribute3 = dscAttribute3;
	}

	/**
	 * dscAttribute4を取得します。
	 * @return dscAttribute4
	 */
	public String getDscAttribute4() {
		return dscAttribute4;
	}

	/**
	 * dscAttribute4
	 * @param dscAttribute4を設定します。
	 */
	public void setDscAttribute4(String dscAttribute4) {
		this.dscAttribute4 = dscAttribute4;
	}

	/**
	 * dscAttribute5を取得します。
	 * @return dscAttribute5
	 */
	public String getDscAttribute5() {
		return dscAttribute5;
	}

	/**
	 * dscAttribute5
	 * @param dscAttribute5を設定します。
	 */
	public void setDscAttribute5(String dscAttribute5) {
		this.dscAttribute5 = dscAttribute5;
	}

	/**
	 * dscAttribute6を取得します。
	 * @return dscAttribute6
	 */
	public String getDscAttribute6() {
		return dscAttribute6;
	}

	/**
	 * dscAttribute6
	 * @param dscAttribute6を設定します。
	 */
	public void setDscAttribute6(String dscAttribute6) {
		this.dscAttribute6 = dscAttribute6;
	}

	/**
	 * dscAttribute7を取得します。
	 * @return dscAttribute7
	 */
	public String getDscAttribute7() {
		return dscAttribute7;
	}

	/**
	 * dscAttribute7
	 * @param dscAttribute7を設定します。
	 */
	public void setDscAttribute7(String dscAttribute7) {
		this.dscAttribute7 = dscAttribute7;
	}

	/**
	 * dscAttribute8を取得します。
	 * @return dscAttribute8
	 */
	public String getDscAttribute8() {
		return dscAttribute8;
	}

	/**
	 * dscAttribute8
	 * @param dscAttribute8を設定します。
	 */
	public void setDscAttribute8(String dscAttribute8) {
		this.dscAttribute8 = dscAttribute8;
	}

	/**
	 * dscAttribute9を取得します。
	 * @return dscAttribute9
	 */
	public String getDscAttribute9() {
		return dscAttribute9;
	}

	/**
	 * dscAttribute9
	 * @param dscAttribute9を設定します。
	 */
	public void setDscAttribute9(String dscAttribute9) {
		this.dscAttribute9 = dscAttribute9;
	}

	/**
	 * dscAttribute10を取得します。
	 * @return dscAttribute10
	 */
	public String getDscAttribute10() {
		return dscAttribute10;
	}

	/**
	 * dscAttribute10
	 * @param dscAttribute10を設定します。
	 */
	public void setDscAttribute10(String dscAttribute10) {
		this.dscAttribute10 = dscAttribute10;
	}

	/**
	 * dscAttribute11を取得します。
	 * @return dscAttribute11
	 */
	public String getDscAttribute11() {
		return dscAttribute11;
	}

	/**
	 * dscAttribute11
	 * @param dscAttribute11を設定します。
	 */
	public void setDscAttribute11(String dscAttribute11) {
		this.dscAttribute11 = dscAttribute11;
	}

	/**
	 * dscAttribute12を取得します。
	 * @return dscAttribute12
	 */
	public String getDscAttribute12() {
		return dscAttribute12;
	}

	/**
	 * dscAttribute12
	 * @param dscAttribute12を設定します。
	 */
	public void setDscAttribute12(String dscAttribute12) {
		this.dscAttribute12 = dscAttribute12;
	}

	/**
	 * dscAttribute13を取得します。
	 * @return dscAttribute13
	 */
	public String getDscAttribute13() {
		return dscAttribute13;
	}

	/**
	 * dscAttribute13
	 * @param dscAttribute13を設定します。
	 */
	public void setDscAttribute13(String dscAttribute13) {
		this.dscAttribute13 = dscAttribute13;
	}

	/**
	 * dscAttribute14を取得します。
	 * @return dscAttribute14
	 */
	public String getDscAttribute14() {
		return dscAttribute14;
	}

	/**
	 * dscAttribute14
	 * @param dscAttribute14を設定します。
	 */
	public void setDscAttribute14(String dscAttribute14) {
		this.dscAttribute14 = dscAttribute14;
	}

	/**
	 * dscAttribute15を取得します。
	 * @return dscAttribute15
	 */
	public String getDscAttribute15() {
		return dscAttribute15;
	}

	/**
	 * dscAttribute15
	 * @param dscAttribute15を設定します。
	 */
	public void setDscAttribute15(String dscAttribute15) {
		this.dscAttribute15 = dscAttribute15;
	}

	/**
	 * dscAttribute16を取得します。
	 * @return dscAttribute16
	 */
	public String getDscAttribute16() {
		return dscAttribute16;
	}

	/**
	 * dscAttribute16
	 * @param dscAttribute16を設定します。
	 */
	public void setDscAttribute16(String dscAttribute16) {
		this.dscAttribute16 = dscAttribute16;
	}

	/**
	 * dscAttribute17を取得します。
	 * @return dscAttribute17
	 */
	public String getDscAttribute17() {
		return dscAttribute17;
	}

	/**
	 * dscAttribute17
	 * @param dscAttribute17を設定します。
	 */
	public void setDscAttribute17(String dscAttribute17) {
		this.dscAttribute17 = dscAttribute17;
	}

	/**
	 * dscAttribute18を取得します。
	 * @return dscAttribute18
	 */
	public String getDscAttribute18() {
		return dscAttribute18;
	}

	/**
	 * dscAttribute18
	 * @param dscAttribute18を設定します。
	 */
	public void setDscAttribute18(String dscAttribute18) {
		this.dscAttribute18 = dscAttribute18;
	}

	/**
	 * dscAttribute19を取得します。
	 * @return dscAttribute19
	 */
	public String getDscAttribute19() {
		return dscAttribute19;
	}

	/**
	 * dscAttribute19
	 * @param dscAttribute19を設定します。
	 */
	public void setDscAttribute19(String dscAttribute19) {
		this.dscAttribute19 = dscAttribute19;
	}

	/**
	 * dscAttribute20を取得します。
	 * @return dscAttribute20
	 */
	public String getDscAttribute20() {
		return dscAttribute20;
	}

	/**
	 * dscAttribute20
	 * @param dscAttribute20を設定します。
	 */
	public void setDscAttribute20(String dscAttribute20) {
		this.dscAttribute20 = dscAttribute20;
	}

	/**
	 * dscFailureAssetIdを取得します。
	 * @return dscFailureAssetId
	 */
	public String getDscFailureAssetId() {
		return dscFailureAssetId;
	}

	/**
	 * dscFailureAssetId
	 * @param dscFailureAssetIdを設定します。
	 */
	public void setDscFailureAssetId(String dscFailureAssetId) {
		this.dscFailureAssetId = dscFailureAssetId;
	}

	/**
	 * invInCompanyActualFlagを取得します。
	 * @return invInCompanyActualFlag
	 */
	public String getInvInCompanyActualFlag() {
		return invInCompanyActualFlag;
	}

	/**
	 * invInCompanyActualFlag
	 * @param invInCompanyActualFlagを設定します。
	 */
	public void setInvInCompanyActualFlag(String invInCompanyActualFlag) {
		this.invInCompanyActualFlag = invInCompanyActualFlag;
	}

	/**
	 * invBarcodeを取得します。
	 * @return invBarcode
	 */
	public String getInvBarcode() {
		return invBarcode;
	}

	/**
	 * invBarcode
	 * @param invBarcodeを設定します。
	 */
	public void setInvBarcode(String invBarcode) {
		this.invBarcode = invBarcode;
	}

	/**
	 * invSealIssueFlagを取得します。
	 * @return invSealIssueFlag
	 */
	public String getInvSealIssueFlag() {
		return invSealIssueFlag;
	}

	/**
	 * invSealIssueFlag
	 * @param invSealIssueFlagを設定します。
	 */
	public void setInvSealIssueFlag(String invSealIssueFlag) {
		this.invSealIssueFlag = invSealIssueFlag;
	}

	/**
	 * invSealIssueDateを取得します。
	 * @return invSealIssueDate
	 */
	public Date getInvSealIssueDate() {
		return invSealIssueDate;
	}

	/**
	 * invSealIssueDate
	 * @param invSealIssueDateを設定します。
	 */
	public void setInvSealIssueDate(Date invSealIssueDate) {
		this.invSealIssueDate = invSealIssueDate;
	}

	/**
	 * invSealIssueDescriptionを取得します。
	 * @return invSealIssueDescription
	 */
	public String getInvSealIssueDescription() {
		return invSealIssueDescription;
	}

	/**
	 * invSealIssueDescription
	 * @param invSealIssueDescriptionを設定します。
	 */
	public void setInvSealIssueDescription(String invSealIssueDescription) {
		this.invSealIssueDescription = invSealIssueDescription;
	}

	/**
	 * deleteFlagを取得します。
	 * @return deleteFlag
	 */
	public String getDeleteFlag() {
		return deleteFlag;
	}

	/**
	 * deleteFlag
	 * @param deleteFlagを設定します。
	 */
	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	/**
	 * deleteDateを取得します。
	 * @return deleteDate
	 */
	public Date getDeleteDate() {
		return deleteDate;
	}

	/**
	 * deleteDate
	 * @param deleteDateを設定します。
	 */
	public void setDeleteDate(Date deleteDate) {
		this.deleteDate = deleteDate;
	}

	/**
	 * deleteReasonを取得します。
	 * @return deleteReason
	 */
	public String getDeleteReason() {
		return deleteReason;
	}

	/**
	 * deleteReason
	 * @param deleteReasonを設定します。
	 */
	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
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
	 * registApplicationIdを取得します。
	 * @return registApplicationId
	 */
	public String getRegistApplicationId() {
		return registApplicationId;
	}

	/**
	 * registApplicationId
	 * @param registApplicationIdを設定します。
	 */
	public void setRegistApplicationId(String registApplicationId) {
		this.registApplicationId = registApplicationId;
	}

	/**
	 * contractNumを取得します。
	 * @return contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}

	/**
	 * contractNum
	 * @param contractNumを設定します。
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	/**
	 * dreamsNumを取得します。
	 * @return dreamsNum
	 */
	public String getDreamsNum() {
		return dreamsNum;
	}

	/**
	 * dreamsNum
	 * @param dreamsNumを設定します。
	 */
	public void setDreamsNum(String dreamsNum) {
		this.dreamsNum = dreamsNum;
	}

	/**
	 * parentAssetIdを取得します。
	 * @return parentAssetId
	 */
	public String getParentAssetId() {
		return parentAssetId;
	}

	/**
	 * parentAssetId
	 * @param parentAssetIdを設定します。
	 */
	public void setParentAssetId(String parentAssetId) {
		this.parentAssetId = parentAssetId;
	}

	public String getAstCategory1Name() {
		return astCategory1Name;
	}

	public void setAstCategory1Name(String astCategory1Name) {
		this.astCategory1Name = astCategory1Name;
	}

	public String getAstCategory2Name() {
		return astCategory2Name;
	}

	public void setAstCategory2Name(String astCategory2Name) {
		this.astCategory2Name = astCategory2Name;
	}

	public String getAstShapeName() {
		return astShapeName;
	}

	public void setAstShapeName(String astShapeName) {
		this.astShapeName = astShapeName;
	}

	public String getAstGetTypeName() {
		return astGetTypeName;
	}

	public void setAstGetTypeName(String astGetTypeName) {
		this.astGetTypeName = astGetTypeName;
	}

	public String getAstAssetTypeName() {
		return astAssetTypeName;
	}

	public void setAstAssetTypeName(String astAssetTypeName) {
		this.astAssetTypeName = astAssetTypeName;
	}

	public String getAstManageTypeName() {
		return astManageTypeName;
	}

	public void setAstManageTypeName(String astManageTypeName) {
		this.astManageTypeName = astManageTypeName;
	}

	public String getHolCompanyName() {
		return holCompanyName;
	}

	public void setHolCompanyName(String holCompanyName) {
		this.holCompanyName = holCompanyName;
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

	public String getHolPurposeName() {
		return holPurposeName;
	}

	public void setHolPurposeName(String holPurposeName) {
		this.holPurposeName = holPurposeName;
	}

	public String getHolGetStaffName() {
		return holGetStaffName;
	}

	public void setHolGetStaffName(String holGetStaffName) {
		this.holGetStaffName = holGetStaffName;
	}

	public String getHolGetCompanyName() {
		return holGetCompanyName;
	}

	public void setHolGetCompanyName(String holGetCompanyName) {
		this.holGetCompanyName = holGetCompanyName;
	}

	public String getHolGetSectionName() {
		return holGetSectionName;
	}

	public void setHolGetSectionName(String holGetSectionName) {
		this.holGetSectionName = holGetSectionName;
	}

	public String getUseCompanyName() {
		return useCompanyName;
	}

	public void setUseCompanyName(String useCompanyName) {
		this.useCompanyName = useCompanyName;
	}

	public String getUseSectionName() {
		return useSectionName;
	}

	public void setUseSectionName(String useSectionName) {
		this.useSectionName = useSectionName;
	}

	public String getUseStaffName() {
		return useStaffName;
	}

	public void setUseStaffName(String useStaffName) {
		this.useStaffName = useStaffName;
	}

	public String getUseStaffCompanyName() {
		return useStaffCompanyName;
	}

	public void setUseStaffCompanyName(String useStaffCompanyName) {
		this.useStaffCompanyName = useStaffCompanyName;
	}

	public String getUseStaffSectionName() {
		return useStaffSectionName;
	}

	public void setUseStaffSectionName(String useStaffSectionName) {
		this.useStaffSectionName = useStaffSectionName;
	}

	public String getMntContractStaffName() {
		return mntContractStaffName;
	}

	public void setMntContractStaffName(String mntContractStaffName) {
		this.mntContractStaffName = mntContractStaffName;
	}

	public String getMntContractServiceLevel() {
		return mntContractServiceLevel;
	}

	public void setMntContractServiceLevel(String mntContractServiceLevel) {
		this.mntContractServiceLevel = mntContractServiceLevel;
	}

	public String getMntContractDescription() {
		return mntContractDescription;
	}

	public void setMntContractDescription(String mntContractDescription) {
		this.mntContractDescription = mntContractDescription;
	}

	public String getMntContractCode2() {
		return mntContractCode2;
	}

	public void setMntContractCode2(String mntContractCode2) {
		this.mntContractCode2 = mntContractCode2;
	}

	public String getMntContractCompanyName2() {
		return mntContractCompanyName2;
	}

	public void setMntContractCompanyName2(String mntContractCompanyName2) {
		this.mntContractCompanyName2 = mntContractCompanyName2;
	}

	public Date getMntContractStartDate2() {
		return mntContractStartDate2;
	}

	public void setMntContractStartDate2(Date mntContractStartDate2) {
		this.mntContractStartDate2 = mntContractStartDate2;
	}

	public Date getMntContractEndDate2() {
		return mntContractEndDate2;
	}

	public void setMntContractEndDate2(Date mntContractEndDate2) {
		this.mntContractEndDate2 = mntContractEndDate2;
	}

	public Long getMntContractAmount2() {
		return mntContractAmount2;
	}

	public void setMntContractAmount2(Long mntContractAmount2) {
		this.mntContractAmount2 = mntContractAmount2;
	}

	public String getMntContractRegistCode2() {
		return mntContractRegistCode2;
	}

	public void setMntContractRegistCode2(String mntContractRegistCode2) {
		this.mntContractRegistCode2 = mntContractRegistCode2;
	}

	public Date getMntContractRegistDate2() {
		return mntContractRegistDate2;
	}

	public void setMntContractRegistDate2(Date mntContractRegistDate2) {
		this.mntContractRegistDate2 = mntContractRegistDate2;
	}

	public String getMntContractStaffCode2() {
		return mntContractStaffCode2;
	}

	public void setMntContractStaffCode2(String mntContractStaffCode2) {
		this.mntContractStaffCode2 = mntContractStaffCode2;
	}

	public String getMntContractStaffName2() {
		return mntContractStaffName2;
	}

	public void setMntContractStaffName2(String mntContractStaffName2) {
		this.mntContractStaffName2 = mntContractStaffName2;
	}

	public String getMntContractServiceLevel2() {
		return mntContractServiceLevel2;
	}

	public void setMntContractServiceLevel2(String mntContractServiceLevel2) {
		this.mntContractServiceLevel2 = mntContractServiceLevel2;
	}

	public String getMntContractDescription2() {
		return mntContractDescription2;
	}

	public void setMntContractDescription2(String mntContractDescription2) {
		this.mntContractDescription2 = mntContractDescription2;
	}

	public String getMntContractCode3() {
		return mntContractCode3;
	}

	public void setMntContractCode3(String mntContractCode3) {
		this.mntContractCode3 = mntContractCode3;
	}

	public String getMntContractCompanyName3() {
		return mntContractCompanyName3;
	}

	public void setMntContractCompanyName3(String mntContractCompanyName3) {
		this.mntContractCompanyName3 = mntContractCompanyName3;
	}

	public Date getMntContractStartDate3() {
		return mntContractStartDate3;
	}

	public void setMntContractStartDate3(Date mntContractStartDate3) {
		this.mntContractStartDate3 = mntContractStartDate3;
	}

	public Date getMntContractEndDate3() {
		return mntContractEndDate3;
	}

	public void setMntContractEndDate3(Date mntContractEndDate3) {
		this.mntContractEndDate3 = mntContractEndDate3;
	}

	public Long getMntContractAmount3() {
		return mntContractAmount3;
	}

	public void setMntContractAmount3(Long mntContractAmount3) {
		this.mntContractAmount3 = mntContractAmount3;
	}

	public String getMntContractRegistCode3() {
		return mntContractRegistCode3;
	}

	public void setMntContractRegistCode3(String mntContractRegistCode3) {
		this.mntContractRegistCode3 = mntContractRegistCode3;
	}

	public Date getMntContractRegistDate3() {
		return mntContractRegistDate3;
	}

	public void setMntContractRegistDate3(Date mntContractRegistDate3) {
		this.mntContractRegistDate3 = mntContractRegistDate3;
	}

	public String getMntContractStaffCode3() {
		return mntContractStaffCode3;
	}

	public void setMntContractStaffCode3(String mntContractStaffCode3) {
		this.mntContractStaffCode3 = mntContractStaffCode3;
	}

	public String getMntContractStaffName3() {
		return mntContractStaffName3;
	}

	public void setMntContractStaffName3(String mntContractStaffName3) {
		this.mntContractStaffName3 = mntContractStaffName3;
	}

	public String getMntContractServiceLevel3() {
		return mntContractServiceLevel3;
	}

	public void setMntContractServiceLevel3(String mntContractServiceLevel3) {
		this.mntContractServiceLevel3 = mntContractServiceLevel3;
	}

	public String getMntContractDescription3() {
		return mntContractDescription3;
	}

	public void setMntContractDescription3(String mntContractDescription3) {
		this.mntContractDescription3 = mntContractDescription3;
	}

	/**
	 * assetLineComUsrを取得します。
	 * @return assetLineComUsr
	 */
	public List<AssetLineComUsr> getAssetLineComUsr() {
		return assetLineComUsr;
	}

	/**
	 * assetLineComUsr
	 * @param assetLineComUsrを設定します。
	 */
	public void setAssetLineComUsr(List<AssetLineComUsr> assetLineComUsr) {
		this.assetLineComUsr = assetLineComUsr;
	}

	/**
	 * assetLineOsを取得します。
	 * @return assetLineOs
	 */
	public List<AssetLineOs> getAssetLineOs() {
		return assetLineOs;
	}

	/**
	 * assetLineOs
	 * @param assetLineOsを設定します。
	 */
	public void setAssetLineOs(List<AssetLineOs> assetLineOs) {
		this.assetLineOs = assetLineOs;
	}

	/**
	 * assetLineNetworkを取得します。
	 * @return assetLineNetwork
	 */
	public List<AssetLineNetwork> getAssetLineNetwork() {
		return assetLineNetwork;
	}

	/**
	 * assetLineNetwork
	 * @param assetLineNetworkを設定します。
	 */
	public void setAssetLineNetwork(List<AssetLineNetwork> assetLineNetwork) {
		this.assetLineNetwork = assetLineNetwork;
	}

	/**
	 * assetLineInvを取得します。
	 * @return assetLineInv
	 */
	public List<AssetLineInv> getAssetLineInv() {
		return assetLineInv;
	}

	/**
	 * assetLineInv
	 * @param assetLineInvを設定します。
	 */
	public void setAssetLineInv(List<AssetLineInv> assetLineInv) {
		this.assetLineInv = assetLineInv;
	}

	public Long getApGetTanLineAstId() {
		return apGetTanLineAstId;
	}

	public void setApGetTanLineAstId(Long apGetTanLineAstId) {
		this.apGetTanLineAstId = apGetTanLineAstId;
	}

	public String getApStatusName() {
		return apStatusName;
	}

	public void setApStatusName(String apStatusName) {
		this.apStatusName = apStatusName;
	}

	public String getApCreateStaffName() {
		return apCreateStaffName;
	}

	public void setApCreateStaffName(String apCreateStaffName) {
		this.apCreateStaffName = apCreateStaffName;
	}

	public String getApCreateCompanyName() {
		return apCreateCompanyName;
	}

	public void setApCreateCompanyName(String apCreateCompanyName) {
		this.apCreateCompanyName = apCreateCompanyName;
	}

	public String getApCreateSectionName() {
		return apCreateSectionName;
	}

	public void setApCreateSectionName(String apCreateSectionName) {
		this.apCreateSectionName = apCreateSectionName;
	}

	public String getApStaffName() {
		return apStaffName;
	}

	public void setApStaffName(String apStaffName) {
		this.apStaffName = apStaffName;
	}

	public String getApCompanyName() {
		return apCompanyName;
	}

	public void setApCompanyName(String apCompanyName) {
		this.apCompanyName = apCompanyName;
	}

	public String getApSectionName() {
		return apSectionName;
	}

	public void setApSectionName(String apSectionName) {
		this.apSectionName = apSectionName;
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

	public String getAstOirEnable() {
		return astOirEnable;
	}

	public void setAstOirEnable(String astOirEnable) {
		this.astOirEnable = astOirEnable;
	}

	public String getAstSystemSectionDeployFlagName() {
		return astSystemSectionDeployFlagName;
	}

	public void setAstSystemSectionDeployFlagName(
			String astSystemSectionDeployFlagName) {
		this.astSystemSectionDeployFlagName = astSystemSectionDeployFlagName;
	}

	public String getUseCommonFlagName() {
		return useCommonFlagName;
	}

	public void setUseCommonFlagName(String useCommonFlagName) {
		this.useCommonFlagName = useCommonFlagName;
	}

	public String getInvInCompanyActualFlagName() {
		return invInCompanyActualFlagName;
	}

	public void setInvInCompanyActualFlagName(String invInCompanyActualFlagName) {
		this.invInCompanyActualFlagName = invInCompanyActualFlagName;
	}

	public String getInvSealIssueFlagName() {
		return invSealIssueFlagName;
	}

	public void setInvSealIssueFlagName(String invSealIssueFlagName) {
		this.invSealIssueFlagName = invSealIssueFlagName;
	}

	public Integer getApGetTanLineAstLineSeq() {
		return apGetTanLineAstLineSeq;
	}

	public void setApGetTanLineAstLineSeq(Integer apGetTanLineAstLineSeq) {
		this.apGetTanLineAstLineSeq = apGetTanLineAstLineSeq;
	}

	/**
	 * @return the astQuantityManageType
	 */
	public String getAstQuantityManageType() {
		return astQuantityManageType;
	}

	/**
	 * @param astQuantityManageType the astQuantityManageType to set
	 */
	public void setAstQuantityManageType(String astQuantityManageType) {
		this.astQuantityManageType = astQuantityManageType;
	}

	/**
	 * @return the astSerialManageType
	 */
	public String getAstSerialManageType() {
		return astSerialManageType;
	}

	/**
	 * @param astSerialManageType the astSerialManageType to set
	 */
	public void setAstSerialManageType(String astSerialManageType) {
		this.astSerialManageType = astSerialManageType;
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



}
