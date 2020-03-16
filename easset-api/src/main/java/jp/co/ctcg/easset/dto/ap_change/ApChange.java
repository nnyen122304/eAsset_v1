/*===================================================================
 * ファイル名 : ApChange.java
 * 概要説明   : 移動申請
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-11-02 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_change;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;
import java.util.List;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApChange implements Externalizable {
	private static final long serialVersionUID = 1L;

	private String applicationId; // 申請書番号
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private Integer version; // バージョン
	private String updateComment; // 更新コメント
	private Long eappId; // e申請書類ID
	private String apStatus; // 申請書ステータス 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下 汎用マスタ-AP_STATUS_CHANGE
	private Date apDate; // 申請日
	private String apChangeType; // 移動申請区分 1:情報機器等,2:ライセンス,3:無形固定資産(現物),A:リース・レンタル,B:有形固定資産,C:無形固定資産
	private String apChangeTypeTanFlag; // 移動申請区分-有形フラグ 0:有形固定資産の変更を含まない,1:有形固定資産の変更を含む
	private String apChangeTypeInt1Flag; // 移動申請区分-無形固定資産(社内使用SW/長前/その他)フラグ 0:該当無形固定資産の変更を含まない,1:該当無形固定資産の変更を含む
	private String apChangeTypeInt2Flag; // 移動申請区分-無形固定資産(市販目的SW)フラグ 0:該当無形固定資産の変更を含まない,1:該当無形固定資産の変更を含む
	private String apChangeTypeEquipFlag; // 移動申請区分-備品フラグ 0:備品の変更を含まない,1:備品の変更を含む
	private String apChangeTypeThinClFlag; // 移動申請区分-情シス配備シンクライアントフラグ 0:情シス配備シンクライアントの変更を含まない,1:情シス配備シンクライアントの変更を含む
	private String apChangeTypeTakePcFlag; // 移動申請区分-情シス配備持ち出し専用機器フラグ 0:情シス配備持ち出し専用機器の変更を含まない,1:情シス配備持ち出し専用機器の変更を含む
	private String apChangeTypeLeaseFlag; // 移動申請区分-リースフラグ 0:リースの変更を含まない,1:リースの変更を含む
	private String apChangeTypeRentalFlag; // 移動申請区分-レンタルフラグ 0:レンタルの変更を含まない,1:レンタルの変更を含む
	private String apChangeTypeLicenseFlag; // 移動申請区分-ライセンスフラグ 0:ライセンスの変更を含まない,1:ライセンスの変更を含む
	private String apChangeTypeCostTypeFlag; // 移動申請区分-販売管理費／原価区分(プロジェクトコード)フラグ 0:販売管理費／原価区分(プロジェクトコード)の変更を含まない,1:販売管理費／原価区分(プロジェクトコード)の変更を含む
	private String apCreateStaffCode; // 起票者社員番号
	private String apCreateCompanyCode; // 起票者所属会社コード
	private String apCreateSectionCode; // 起票者所属部署コード
	private Integer apCreateSectionYear; // 起票者所属部署年度
	private String apStaffCode; // 申請者社員番号
	private String apCompanyCode; // 申請会社コード
	private String apSectionCode; // 申請部署コード
	private Integer apSectionYear; // 申請部署年度
	private String apTel; // 連絡先TEL
	private Date chgScheduleDate; // 移動予定日
	private String chgSchedulePeriod; // 移動予定年月度(経費負担部課変更時)
	private String chgDescription; // 移動理由／備考
	private String holCompanyCode; // 変更後：保有会社コード
	private String holSectionCode; // 変更後：保有部署コード
	private Integer holSectionYear; // 変更後：保有部署年度
	private String holSectionWithFlag; // 変更後：使用部署同時更新フラグ 0:同時更新を行わない,1:同時更新を行う。
	private String holStaffCode; // 変更後：資産管理担当者
	private String holOfficeCode; // 変更後：設置場所 汎用マスタ-OFFICE
	private Integer holOfficeFloor; // 変更後：設置場所階数
	private String holOfficeRoomNum; // 変更後：部屋番号
	private String holOfficeRackNum; // 変更後：ラック番号
	private String useStaffCode; // 変更後：使用者社員番号
	private String useStaffCompanyCode; // 変更後：使用者所属会社コード
	private String useStaffSectionCode; // 変更後：使用者所属部署コード
	private Integer useStaffSectionYear; // 変更後：使用者所属部署年度
	private String intHolStaffCode; // 変更後：無形管理担当者社員番号
	private String intHolStaffCompanyCode; // 変更後：無形管理担当者所属会社コード
	private String intHolStaffSectionCode; // 変更後：無形管理担当者所属部署コード
	private Integer intHolStaffSectionYear; // 変更後：無形管理担当者所属部署年度
	private String holRepOfficeCode; // 変更後：代表設置場所
	private String costType; // 変更後：販売管理費/原価区分 1:販売管理費,2:原価
	private String costDepPrjCode; // 変更後：減価償却プロジェクトコード
	private String costCompanyCode; // 変更後：資産計上会社コード
	private String costSectionCode; // 変更後：資産計上部課コード
	private Integer costSectionYear; // 変更後：資産計上部課年度
	private String apprHolStaffCodeOld; // 移動元承認者：資産管理担当者
	private String apprCostStaffCodeOld; // 移動元承認者：経費負担部課課長
	private String apprCostStaffSkipFlagOld; // 移動元承認者：経費負担部課課長スキップ
	private String apprSuperiorStaffCodeOld; // 移動元承認者：部長
	private String apprHolStaffCodeNew; // 移動先承認者：資産管理担当者
	private String apprCostStaffCodeNew; // 移動先承認者：経費負担部課課長
	private String apprCostStaffSkipFlagNew; // 移動先承認者：経費負担部課課長スキップ
	private String apprSuperiorStaffCodeNew; // 移動先承認者：部長
	private Date approveDate; // 承認日

	private String apStaffName; // 申請者名
	private String apStatusName; // ステータス名
	private String holStaffName; // 資産管理担当者
	private String holOfficeName; // 設置場所名
	private String apChangeTypeName; // 申請区分名
	private String useStaffName; // 使用者名
	private String intHolStaffName; // 無形管理担当者名
	private String apCompanyName; // 申請会社名
	private String apSectionName; // 申請所属部署名
	private String apCreateCompanyName; // 起票者所属会社名
	private String apCreateSectionName; // 起票者所属部署名
	private String apCreateStaffName; // 起票者社員名
	private String holCompanyName; // 変更後：保有会社名
	private String holSectionName; // 変更後：保有部署名
	private String apprHolStaffNameOld; // 移動元承認者：資産管理担当者
	private String apprCostStaffNameOld; // 移動元承認者：経費負担部課課長名
	private String apprSuperiorStaffNameOld; // 移動元承認者：部長名
	private String apprHolStaffNameNew; // 移動先承認者：資産管理担当者
	private String apprCostStaffNameNew; // 移動先承認者：経費負担部課課長
	private String apprSuperiorStaffNameNew; // 移動先承認者：部長名
	private String useStaffCompanyName; // 変更後：使用者会社名
	private String useStaffSectionName; // 変更後：使用者所属部署名
	private String intHolStaffCompanyName; // 変更後：無形管理担当者会社名
	private String intHolStaffSectionName; // 変更後：無形管理担当者所属部署名
	private String costDepPrjName;  // 変更後：減価償却プロジェクト名
	private String costDepPrjType; // 変更後：減価償却タイプ
	private String chgSchedulePeriodName;  // 移動予定年月度(経費負担部課変更時)名
	private String costSectionName; // 資産計上部課名
	private String holRepOfficeName; // 代表設置場所
	private String apCreateTel; // 起票者:連絡先TEL

	//	明細
	private List<ApChangeLineFld> apChangeLineFldTan;  // 有形固定資産明細
	private List<ApChangeLineFld> apChangeLineFldInt;  // 無形固定資産明細
	private List<ApChangeLineContract> apChangeLineContractLease;  // 契約明細(リース)
	private List<ApChangeLineContract> apChangeLineContractRental;  // 契約明細(レンタル)
	private List<ApChangeLineAst> apChangeLineAst; // 資産(機器)明細
	private List<ApChangeLineLic> apChangeLineLic; // ライセンス明細
	private List<ApChangeLineCostSec> apChangeLineCostSecOld; // 経費負担部課明細(移動元)
	private List<ApChangeLineCostSec> apChangeLineCostSecNew; // 経費負担部課明細(移動先)

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		applicationId = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		version = Function.getExternalInteger(input.readObject());
		updateComment = (String)input.readObject();
		eappId = Function.getExternalLong(input.readObject());
		apStatus = (String)input.readObject();
		apDate = (Date)input.readObject();
		apChangeType = (String)input.readObject();
		apChangeTypeTanFlag = (String)input.readObject();
		apChangeTypeInt1Flag = (String)input.readObject();
		apChangeTypeInt2Flag = (String)input.readObject();
		apChangeTypeEquipFlag = (String)input.readObject();
		apChangeTypeThinClFlag = (String)input.readObject();
		apChangeTypeTakePcFlag = (String)input.readObject();
		apChangeTypeLeaseFlag = (String)input.readObject();
		apChangeTypeRentalFlag = (String)input.readObject();
		apChangeTypeLicenseFlag = (String)input.readObject();
		apChangeTypeCostTypeFlag = (String)input.readObject();
		apCreateStaffCode = (String)input.readObject();
		apCreateCompanyCode = (String)input.readObject();
		apCreateSectionCode = (String)input.readObject();
		apCreateSectionYear = Function.getExternalInteger(input.readObject());
		apStaffCode = (String)input.readObject();
		apCompanyCode = (String)input.readObject();
		apSectionCode = (String)input.readObject();
		apSectionYear = Function.getExternalInteger(input.readObject());
		apTel = (String)input.readObject();
		chgScheduleDate = (Date)input.readObject();
		chgSchedulePeriod = (String)input.readObject();
		chgDescription = (String)input.readObject();
		holCompanyCode = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		holSectionWithFlag = (String)input.readObject();
		holStaffCode = (String)input.readObject();
		holOfficeCode = (String)input.readObject();
		holOfficeFloor = Function.getExternalInteger(input.readObject());
		holOfficeRoomNum = (String)input.readObject();
		holOfficeRackNum = (String)input.readObject();
		useStaffCode = (String)input.readObject();
		useStaffCompanyCode = (String)input.readObject();
		useStaffSectionCode = (String)input.readObject();
		useStaffSectionYear = Function.getExternalInteger(input.readObject());
		intHolStaffCode = (String)input.readObject();
		intHolStaffCompanyCode = (String)input.readObject();
		intHolStaffSectionCode = (String)input.readObject();
		intHolStaffSectionYear = Function.getExternalInteger(input.readObject());
		holRepOfficeCode = (String)input.readObject();
		costType = (String)input.readObject();
		costDepPrjCode = (String)input.readObject();
		costCompanyCode = (String)input.readObject();
		costSectionCode = (String)input.readObject();
		costSectionYear = Function.getExternalInteger(input.readObject());
		apprHolStaffCodeOld = (String)input.readObject();
		apprCostStaffCodeOld = (String)input.readObject();
		apprCostStaffSkipFlagOld = (String)input.readObject();
		apprSuperiorStaffCodeOld = (String)input.readObject();
		apprHolStaffCodeNew = (String)input.readObject();
		apprCostStaffCodeNew = (String)input.readObject();
		apprCostStaffSkipFlagNew = (String)input.readObject();
		apprSuperiorStaffCodeNew = (String)input.readObject();
		approveDate = (Date)input.readObject();

		apStaffName = (String)input.readObject();
		apStatusName = (String)input.readObject();
		holStaffName = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		apChangeTypeName = (String)input.readObject();
		useStaffName = (String)input.readObject();
		intHolStaffName = (String)input.readObject();
		apCompanyName = (String)input.readObject();
		apSectionName = (String)input.readObject();
		apCreateCompanyName = (String)input.readObject();
		apCreateSectionName = (String)input.readObject();
		apCreateStaffName = (String)input.readObject();
		holCompanyName = (String)input.readObject();
		holSectionName = (String)input.readObject();
		apprHolStaffNameOld = (String)input.readObject();
		apprCostStaffNameOld = (String)input.readObject();
		apprSuperiorStaffNameOld = (String)input.readObject();
		apprHolStaffNameNew = (String)input.readObject();
		apprCostStaffNameNew = (String)input.readObject();
		apprSuperiorStaffNameNew = (String)input.readObject();
		useStaffCompanyName = (String)input.readObject();
		useStaffSectionName = (String)input.readObject();
		intHolStaffCompanyName = (String)input.readObject();
		intHolStaffSectionName = (String)input.readObject();
		costDepPrjName = (String)input.readObject();
		costDepPrjType = (String)input.readObject();
		chgSchedulePeriodName = (String)input.readObject();
		costSectionName = (String)input.readObject();
		holRepOfficeName = (String)input.readObject();
		apCreateTel = (String)input.readObject();

		apChangeLineFldTan = (List<ApChangeLineFld>)input.readObject();
		apChangeLineFldInt = (List<ApChangeLineFld>)input.readObject();
		apChangeLineContractLease = (List<ApChangeLineContract>)input.readObject();
		apChangeLineContractRental = (List<ApChangeLineContract>)input.readObject();
		apChangeLineAst = (List<ApChangeLineAst>)input.readObject();
		apChangeLineLic = (List<ApChangeLineLic>)input.readObject();
		apChangeLineCostSecOld = (List<ApChangeLineCostSec>)input.readObject();
		apChangeLineCostSecNew = (List<ApChangeLineCostSec>)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(applicationId);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(version);
		output.writeObject(updateComment);
		output.writeObject(eappId);
		output.writeObject(apStatus);
		output.writeObject(apDate);
		output.writeObject(apChangeType);
		output.writeObject(apChangeTypeTanFlag);
		output.writeObject(apChangeTypeInt1Flag);
		output.writeObject(apChangeTypeInt2Flag);
		output.writeObject(apChangeTypeEquipFlag);
		output.writeObject(apChangeTypeThinClFlag);
		output.writeObject(apChangeTypeTakePcFlag);
		output.writeObject(apChangeTypeLeaseFlag);
		output.writeObject(apChangeTypeRentalFlag);
		output.writeObject(apChangeTypeLicenseFlag);
		output.writeObject(apChangeTypeCostTypeFlag);
		output.writeObject(apCreateStaffCode);
		output.writeObject(apCreateCompanyCode);
		output.writeObject(apCreateSectionCode);
		output.writeObject(apCreateSectionYear);
		output.writeObject(apStaffCode);
		output.writeObject(apCompanyCode);
		output.writeObject(apSectionCode);
		output.writeObject(apSectionYear);
		output.writeObject(apTel);
		output.writeObject(chgScheduleDate);
		output.writeObject(chgSchedulePeriod);
		output.writeObject(chgDescription);
		output.writeObject(holCompanyCode);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(holSectionWithFlag);
		output.writeObject(holStaffCode);
		output.writeObject(holOfficeCode);
		output.writeObject(holOfficeFloor);
		output.writeObject(holOfficeRoomNum);
		output.writeObject(holOfficeRackNum);
		output.writeObject(useStaffCode);
		output.writeObject(useStaffCompanyCode);
		output.writeObject(useStaffSectionCode);
		output.writeObject(useStaffSectionYear);
		output.writeObject(intHolStaffCode);
		output.writeObject(intHolStaffCompanyCode);
		output.writeObject(intHolStaffSectionCode);
		output.writeObject(intHolStaffSectionYear);
		output.writeObject(holRepOfficeCode);
		output.writeObject(costType);
		output.writeObject(costDepPrjCode);
		output.writeObject(costCompanyCode);
		output.writeObject(costSectionCode);
		output.writeObject(costSectionYear);
		output.writeObject(apprHolStaffCodeOld);
		output.writeObject(apprCostStaffCodeOld);
		output.writeObject(apprCostStaffSkipFlagOld);
		output.writeObject(apprSuperiorStaffCodeOld);
		output.writeObject(apprHolStaffCodeNew);
		output.writeObject(apprCostStaffCodeNew);
		output.writeObject(apprCostStaffSkipFlagNew);
		output.writeObject(apprSuperiorStaffCodeNew);
		output.writeObject(approveDate);

		output.writeObject(apStaffName);
		output.writeObject(apStatusName);
		output.writeObject(holStaffName);
		output.writeObject(holOfficeName);
		output.writeObject(apChangeTypeName);
		output.writeObject(useStaffName);
		output.writeObject(intHolStaffName);
		output.writeObject(apCompanyName);
		output.writeObject(apSectionName);
		output.writeObject(apCreateCompanyName);
		output.writeObject(apCreateSectionName);
		output.writeObject(apCreateStaffName);
		output.writeObject(holCompanyName);
		output.writeObject(holSectionName);
		output.writeObject(apprHolStaffNameOld);
		output.writeObject(apprCostStaffNameOld);
		output.writeObject(apprSuperiorStaffNameOld);
		output.writeObject(apprHolStaffNameNew);
		output.writeObject(apprCostStaffNameNew);
		output.writeObject(apprSuperiorStaffNameNew);
		output.writeObject(useStaffCompanyName);
		output.writeObject(useStaffSectionName);
		output.writeObject(intHolStaffCompanyName);
		output.writeObject(intHolStaffSectionName);
		output.writeObject(costDepPrjName);
		output.writeObject(costDepPrjType);
		output.writeObject(chgSchedulePeriodName);
		output.writeObject(costSectionName);
		output.writeObject(holRepOfficeName);
		output.writeObject(apCreateTel);

		output.writeObject(apChangeLineFldTan);
		output.writeObject(apChangeLineFldInt);
		output.writeObject(apChangeLineContractLease);
		output.writeObject(apChangeLineContractRental);
		output.writeObject(apChangeLineAst);
		output.writeObject(apChangeLineLic);
		output.writeObject(apChangeLineCostSecOld);
		output.writeObject(apChangeLineCostSecNew);

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
	 * apChangeTypeを取得します。
	 * @return apChangeType
	 */
	public String getApChangeType() {
		return apChangeType;
	}

	/**
	 * apChangeType
	 * @param apChangeTypeを設定します。
	 */
	public void setApChangeType(String apChangeType) {
		this.apChangeType = apChangeType;
	}

	/**
	 * apChangeTypeTanFlagを取得します。
	 * @return apChangeTypeTanFlag
	 */
	public String getApChangeTypeTanFlag() {
		return apChangeTypeTanFlag;
	}

	/**
	 * apChangeTypeTanFlag
	 * @param apChangeTypeTanFlagを設定します。
	 */
	public void setApChangeTypeTanFlag(String apChangeTypeTanFlag) {
		this.apChangeTypeTanFlag = apChangeTypeTanFlag;
	}

	/**
	 * apChangeTypeEquipFlagを取得します。
	 * @return apChangeTypeEquipFlag
	 */
	public String getApChangeTypeEquipFlag() {
		return apChangeTypeEquipFlag;
	}

	/**
	 * apChangeTypeEquipFlag
	 * @param apChangeTypeEquipFlagを設定します。
	 */
	public void setApChangeTypeEquipFlag(String apChangeTypeEquipFlag) {
		this.apChangeTypeEquipFlag = apChangeTypeEquipFlag;
	}

	/**
	 * apChangeTypeLeaseFlagを取得します。
	 * @return apChangeTypeLeaseFlag
	 */
	public String getApChangeTypeLeaseFlag() {
		return apChangeTypeLeaseFlag;
	}

	/**
	 * apChangeTypeLeaseFlag
	 * @param apChangeTypeLeaseFlagを設定します。
	 */
	public void setApChangeTypeLeaseFlag(String apChangeTypeLeaseFlag) {
		this.apChangeTypeLeaseFlag = apChangeTypeLeaseFlag;
	}

	/**
	 * apChangeTypeRentalFlagを取得します。
	 * @return apChangeTypeRentalFlag
	 */
	public String getApChangeTypeRentalFlag() {
		return apChangeTypeRentalFlag;
	}

	/**
	 * apChangeTypeRentalFlag
	 * @param apChangeTypeRentalFlagを設定します。
	 */
	public void setApChangeTypeRentalFlag(String apChangeTypeRentalFlag) {
		this.apChangeTypeRentalFlag = apChangeTypeRentalFlag;
	}

	/**
	 * apChangeTypeLicenseFlagを取得します。
	 * @return apChangeTypeLicenseFlag
	 */
	public String getApChangeTypeLicenseFlag() {
		return apChangeTypeLicenseFlag;
	}

	/**
	 * apChangeTypeLicenseFlag
	 * @param apChangeTypeLicenseFlagを設定します。
	 */
	public void setApChangeTypeLicenseFlag(String apChangeTypeLicenseFlag) {
		this.apChangeTypeLicenseFlag = apChangeTypeLicenseFlag;
	}

	/**
	 * apChangeTypeCostTypeFlagを取得します。
	 * @return apChangeTypeCostTypeFlag
	 */
	public String getApChangeTypeCostTypeFlag() {
		return apChangeTypeCostTypeFlag;
	}

	/**
	 * apChangeTypeCostTypeFlag
	 * @param apChangeTypeCostTypeFlagを設定します。
	 */
	public void setApChangeTypeCostTypeFlag(String apChangeTypeCostTypeFlag) {
		this.apChangeTypeCostTypeFlag = apChangeTypeCostTypeFlag;
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
	 * chgScheduleDateを取得します。
	 * @return chgScheduleDate
	 */
	public Date getChgScheduleDate() {
		return chgScheduleDate;
	}

	/**
	 * chgScheduleDate
	 * @param chgScheduleDateを設定します。
	 */
	public void setChgScheduleDate(Date chgScheduleDate) {
		this.chgScheduleDate = chgScheduleDate;
	}

	/**
	 * chgSchedulePeriodを取得します。
	 * @return chgSchedulePeriod
	 */
	public String getChgSchedulePeriod() {
		return chgSchedulePeriod;
	}

	/**
	 * chgSchedulePeriod
	 * @param chgSchedulePeriodを設定します。
	 */
	public void setChgSchedulePeriod(String chgSchedulePeriod) {
		this.chgSchedulePeriod = chgSchedulePeriod;
	}

	/**
	 * chgDescriptionを取得します。
	 * @return chgDescription
	 */
	public String getChgDescription() {
		return chgDescription;
	}

	/**
	 * chgDescription
	 * @param chgDescriptionを設定します。
	 */
	public void setChgDescription(String chgDescription) {
		this.chgDescription = chgDescription;
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
	 * holSectionWithFlagを取得します。
	 * @return holSectionWithFlag
	 */
	public String getHolSectionWithFlag() {
		return holSectionWithFlag;
	}

	/**
	 * holSectionWithFlag
	 * @param holSectionWithFlagを設定します。
	 */
	public void setHolSectionWithFlag(String holSectionWithFlag) {
		this.holSectionWithFlag = holSectionWithFlag;
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
	 * apprHolStaffCodeOldを取得します。
	 * @return apprHolStaffCodeOld
	 */
	public String getApprHolStaffCodeOld() {
		return apprHolStaffCodeOld;
	}

	/**
	 * apprHolStaffCodeOld
	 * @param apprHolStaffCodeOldを設定します。
	 */
	public void setApprHolStaffCodeOld(String apprHolStaffCodeOld) {
		this.apprHolStaffCodeOld = apprHolStaffCodeOld;
	}

	/**
	 * apprCostStaffCodeOldを取得します。
	 * @return apprCostStaffCodeOld
	 */
	public String getApprCostStaffCodeOld() {
		return apprCostStaffCodeOld;
	}

	/**
	 * apprCostStaffCodeOld
	 * @param apprCostStaffCodeOldを設定します。
	 */
	public void setApprCostStaffCodeOld(String apprCostStaffCodeOld) {
		this.apprCostStaffCodeOld = apprCostStaffCodeOld;
	}

	/**
	 * apprSuperiorStaffCodeOldを取得します。
	 * @return apprSuperiorStaffCodeOld
	 */
	public String getApprSuperiorStaffCodeOld() {
		return apprSuperiorStaffCodeOld;
	}

	/**
	 * apprSuperiorStaffCodeOld
	 * @param apprSuperiorStaffCodeOldを設定します。
	 */
	public void setApprSuperiorStaffCodeOld(String apprSuperiorStaffCodeOld) {
		this.apprSuperiorStaffCodeOld = apprSuperiorStaffCodeOld;
	}

	/**
	 * apprHolStaffCodeNewを取得します。
	 * @return apprHolStaffCodeNew
	 */
	public String getApprHolStaffCodeNew() {
		return apprHolStaffCodeNew;
	}

	/**
	 * apprHolStaffCodeNew
	 * @param apprHolStaffCodeNewを設定します。
	 */
	public void setApprHolStaffCodeNew(String apprHolStaffCodeNew) {
		this.apprHolStaffCodeNew = apprHolStaffCodeNew;
	}

	/**
	 * apprCostStaffCodeNewを取得します。
	 * @return apprCostStaffCodeNew
	 */
	public String getApprCostStaffCodeNew() {
		return apprCostStaffCodeNew;
	}

	/**
	 * apprCostStaffCodeNew
	 * @param apprCostStaffCodeNewを設定します。
	 */
	public void setApprCostStaffCodeNew(String apprCostStaffCodeNew) {
		this.apprCostStaffCodeNew = apprCostStaffCodeNew;
	}

	/**
	 * apprSuperiorStaffCodeNewを取得します。
	 * @return apprSuperiorStaffCodeNew
	 */
	public String getApprSuperiorStaffCodeNew() {
		return apprSuperiorStaffCodeNew;
	}

	/**
	 * apprSuperiorStaffCodeNew
	 * @param apprSuperiorStaffCodeNewを設定します。
	 */
	public void setApprSuperiorStaffCodeNew(String apprSuperiorStaffCodeNew) {
		this.apprSuperiorStaffCodeNew = apprSuperiorStaffCodeNew;
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
	 * apChangeTypeNameを取得します。
	 * @return apChangeTypeName
	 */
	public String getApChangeTypeName() {
		return apChangeTypeName;
	}

	/**
	 * apChangeTypeName
	 * @param apChangeTypeNameを設定します。
	 */
	public void setApChangeTypeName(String apChangeTypeName) {
		this.apChangeTypeName = apChangeTypeName;
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
	 * apprCostStaffNameOldを取得します。
	 * @return apprCostStaffNameOld
	 */
	public String getApprCostStaffNameOld() {
		return apprCostStaffNameOld;
	}

	/**
	 * apprCostStaffNameOld
	 * @param apprCostStaffNameOldを設定します。
	 */
	public void setApprCostStaffNameOld(String apprCostStaffNameOld) {
		this.apprCostStaffNameOld = apprCostStaffNameOld;
	}

	/**
	 * apprCostStaffNameNewを取得します。
	 * @return apprCostStaffNameNew
	 */
	public String getApprCostStaffNameNew() {
		return apprCostStaffNameNew;
	}

	/**
	 * apprCostStaffNameNew
	 * @param apprCostStaffNameNewを設定します。
	 */
	public void setApprCostStaffNameNew(String apprCostStaffNameNew) {
		this.apprCostStaffNameNew = apprCostStaffNameNew;
	}

	/**
	 * apprSuperiorStaffNameOldを取得します。
	 * @return apprSuperiorStaffNameOld
	 */
	public String getApprSuperiorStaffNameOld() {
		return apprSuperiorStaffNameOld;
	}

	/**
	 * apprSuperiorStaffNameOld
	 * @param apprSuperiorStaffNameOldを設定します。
	 */
	public void setApprSuperiorStaffNameOld(String apprSuperiorStaffNameOld) {
		this.apprSuperiorStaffNameOld = apprSuperiorStaffNameOld;
	}

	/**
	 * apprSuperiorStaffNameNewを取得します。
	 * @return apprSuperiorStaffNameNew
	 */
	public String getApprSuperiorStaffNameNew() {
		return apprSuperiorStaffNameNew;
	}

	/**
	 * apprSuperiorStaffNameNew
	 * @param apprSuperiorStaffNameNewを設定します。
	 */
	public void setApprSuperiorStaffNameNew(String apprSuperiorStaffNameNew) {
		this.apprSuperiorStaffNameNew = apprSuperiorStaffNameNew;
	}

	/**
	 * useStaffCompanyNameを取得します。
	 * @return useStaffCompanyName
	 */
	public String getUseStaffCompanyName() {
		return useStaffCompanyName;
	}

	/**
	 * useStaffCompanyName
	 * @param useStaffCompanyNameを設定します。
	 */
	public void setUseStaffCompanyName(String useStaffCompanyName) {
		this.useStaffCompanyName = useStaffCompanyName;
	}

	/**
	 * useStaffSectionNameを取得します。
	 * @return useStaffSectionName
	 */
	public String getUseStaffSectionName() {
		return useStaffSectionName;
	}

	/**
	 * useStaffSectionName
	 * @param useStaffSectionNameを設定します。
	 */
	public void setUseStaffSectionName(String useStaffSectionName) {
		this.useStaffSectionName = useStaffSectionName;
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
	 * chgSchedulePeriodNameを取得します。
	 * @return chgSchedulePeriodName
	 */
	public String getChgSchedulePeriodName() {
		return chgSchedulePeriodName;
	}

	/**
	 * chgSchedulePeriodName
	 * @param chgSchedulePeriodNameを設定します。
	 */
	public void setChgSchedulePeriodName(String chgSchedulePeriodName) {
		this.chgSchedulePeriodName = chgSchedulePeriodName;
	}

	/**
	 * apChangeLineContractLeaseを取得します。
	 * @return apChangeLineContractLease
	 */
	public List<ApChangeLineContract> getApChangeLineContractLease() {
		return apChangeLineContractLease;
	}

	/**
	 * apChangeLineContractLease
	 * @param apChangeLineContractLeaseを設定します。
	 */
	public void setApChangeLineContractLease(
			List<ApChangeLineContract> apChangeLineContractLease) {
		this.apChangeLineContractLease = apChangeLineContractLease;
	}

	/**
	 * apChangeLineContractRentalを取得します。
	 * @return apChangeLineContractRental
	 */
	public List<ApChangeLineContract> getApChangeLineContractRental() {
		return apChangeLineContractRental;
	}

	/**
	 * apChangeLineContractRental
	 * @param apChangeLineContractRentalを設定します。
	 */
	public void setApChangeLineContractRental(
			List<ApChangeLineContract> apChangeLineContractRental) {
		this.apChangeLineContractRental = apChangeLineContractRental;
	}

	/**
	 * apChangeLineAstを取得します。
	 * @return apChangeLineAst
	 */
	public List<ApChangeLineAst> getApChangeLineAst() {
		return apChangeLineAst;
	}

	/**
	 * apChangeLineAst
	 * @param apChangeLineAstを設定します。
	 */
	public void setApChangeLineAst(List<ApChangeLineAst> apChangeLineAst) {
		this.apChangeLineAst = apChangeLineAst;
	}

	/**
	 * apChangeLineLicを取得します。
	 * @return apChangeLineLic
	 */
	public List<ApChangeLineLic> getApChangeLineLic() {
		return apChangeLineLic;
	}

	/**
	 * apChangeLineLic
	 * @param apChangeLineLicを設定します。
	 */
	public void setApChangeLineLic(List<ApChangeLineLic> apChangeLineLic) {
		this.apChangeLineLic = apChangeLineLic;
	}

	/**
	 * apChangeLineCostSecOldを取得します。
	 * @return apChangeLineCostSecOld
	 */
	public List<ApChangeLineCostSec> getApChangeLineCostSecOld() {
		return apChangeLineCostSecOld;
	}

	/**
	 * apChangeLineCostSecOld
	 * @param apChangeLineCostSecOldを設定します。
	 */
	public void setApChangeLineCostSecOld(
			List<ApChangeLineCostSec> apChangeLineCostSecOld) {
		this.apChangeLineCostSecOld = apChangeLineCostSecOld;
	}

	/**
	 * apChangeLineCostSecNewを取得します。
	 * @return apChangeLineCostSecNew
	 */
	public List<ApChangeLineCostSec> getApChangeLineCostSecNew() {
		return apChangeLineCostSecNew;
	}

	/**
	 * apChangeLineCostSecNew
	 * @param apChangeLineCostSecNewを設定します。
	 */
	public void setApChangeLineCostSecNew(
			List<ApChangeLineCostSec> apChangeLineCostSecNew) {
		this.apChangeLineCostSecNew = apChangeLineCostSecNew;
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

	public String getApprHolStaffNameOld() {
		return apprHolStaffNameOld;
	}

	public void setApprHolStaffNameOld(String apprHolStaffNameOld) {
		this.apprHolStaffNameOld = apprHolStaffNameOld;
	}

	public String getApprHolStaffNameNew() {
		return apprHolStaffNameNew;
	}

	public void setApprHolStaffNameNew(String apprHolStaffNameNew) {
		this.apprHolStaffNameNew = apprHolStaffNameNew;
	}

	/**
	 * @return the apChangeTypeThinClFlag
	 */
	public String getApChangeTypeThinClFlag() {
		return apChangeTypeThinClFlag;
	}

	/**
	 * @param apChangeTypeThinClFlag the apChangeTypeThinClFlag to set
	 */
	public void setApChangeTypeThinClFlag(String apChangeTypeThinClFlag) {
		this.apChangeTypeThinClFlag = apChangeTypeThinClFlag;
	}

	/**
	 * @return the apChangeTypeTakePcFlag
	 */
	public String getApChangeTypeTakePcFlag() {
		return apChangeTypeTakePcFlag;
	}

	/**
	 * @param apChangeTypeTakePcFlag the apChangeTypeTakePcFlag to set
	 */
	public void setApChangeTypeTakePcFlag(String apChangeTypeTakePcFlag) {
		this.apChangeTypeTakePcFlag = apChangeTypeTakePcFlag;
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
	 * @return the apChangeTypeInt1Flag
	 */
	public String getApChangeTypeInt1Flag() {
		return apChangeTypeInt1Flag;
	}

	/**
	 * @param apChangeTypeInt1Flag the apChangeTypeInt1Flag to set
	 */
	public void setApChangeTypeInt1Flag(String apChangeTypeInt1Flag) {
		this.apChangeTypeInt1Flag = apChangeTypeInt1Flag;
	}

	/**
	 * @return the apChangeTypeInt2Flag
	 */
	public String getApChangeTypeInt2Flag() {
		return apChangeTypeInt2Flag;
	}

	/**
	 * @param apChangeTypeInt2Flag the apChangeTypeInt2Flag to set
	 */
	public void setApChangeTypeInt2Flag(String apChangeTypeInt2Flag) {
		this.apChangeTypeInt2Flag = apChangeTypeInt2Flag;
	}

	/**
	 * @return the intHolStaffCode
	 */
	public String getIntHolStaffCode() {
		return intHolStaffCode;
	}

	/**
	 * @param intHolStaffCode the intHolStaffCode to set
	 */
	public void setIntHolStaffCode(String intHolStaffCode) {
		this.intHolStaffCode = intHolStaffCode;
	}

	/**
	 * @return the intHolStaffCompanyCode
	 */
	public String getIntHolStaffCompanyCode() {
		return intHolStaffCompanyCode;
	}

	/**
	 * @param intHolStaffCompanyCode the intHolStaffCompanyCode to set
	 */
	public void setIntHolStaffCompanyCode(String intHolStaffCompanyCode) {
		this.intHolStaffCompanyCode = intHolStaffCompanyCode;
	}

	/**
	 * @return the intHolStaffSectionCode
	 */
	public String getIntHolStaffSectionCode() {
		return intHolStaffSectionCode;
	}

	/**
	 * @param intHolStaffSectionCode the intHolStaffSectionCode to set
	 */
	public void setIntHolStaffSectionCode(String intHolStaffSectionCode) {
		this.intHolStaffSectionCode = intHolStaffSectionCode;
	}

	/**
	 * @return the intHolStaffSectionYear
	 */
	public Integer getIntHolStaffSectionYear() {
		return intHolStaffSectionYear;
	}

	/**
	 * @param intHolStaffSectionYear the intHolStaffSectionYear to set
	 */
	public void setIntHolStaffSectionYear(Integer intHolStaffSectionYear) {
		this.intHolStaffSectionYear = intHolStaffSectionYear;
	}

	/**
	 * @return the holRepOfficeCode
	 */
	public String getHolRepOfficeCode() {
		return holRepOfficeCode;
	}

	/**
	 * @param holRepOfficeCode the holRepOfficeCode to set
	 */
	public void setHolRepOfficeCode(String holRepOfficeCode) {
		this.holRepOfficeCode = holRepOfficeCode;
	}

	/**
	 * @return the costCompanyCode
	 */
	public String getCostCompanyCode() {
		return costCompanyCode;
	}

	/**
	 * @param costCompanyCode the costCompanyCode to set
	 */
	public void setCostCompanyCode(String costCompanyCode) {
		this.costCompanyCode = costCompanyCode;
	}

	/**
	 * @return the costSectionCode
	 */
	public String getCostSectionCode() {
		return costSectionCode;
	}

	/**
	 * @param costSectionCode the costSectionCode to set
	 */
	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
	}

	/**
	 * @return the apprCostStaffSkipFlagOld
	 */
	public String getApprCostStaffSkipFlagOld() {
		return apprCostStaffSkipFlagOld;
	}

	/**
	 * @param apprCostStaffSkipFlagOld the apprCostStaffSkipFlagOld to set
	 */
	public void setApprCostStaffSkipFlagOld(String apprCostStaffSkipFlagOld) {
		this.apprCostStaffSkipFlagOld = apprCostStaffSkipFlagOld;
	}

	/**
	 * @return the apprCostStaffSkipFlagNew
	 */
	public String getApprCostStaffSkipFlagNew() {
		return apprCostStaffSkipFlagNew;
	}

	/**
	 * @param apprCostStaffSkipFlagNew the apprCostStaffSkipFlagNew to set
	 */
	public void setApprCostStaffSkipFlagNew(String apprCostStaffSkipFlagNew) {
		this.apprCostStaffSkipFlagNew = apprCostStaffSkipFlagNew;
	}

	/**
	 * @return the intHolStaffName
	 */
	public String getIntHolStaffName() {
		return intHolStaffName;
	}

	/**
	 * @param intHolStaffName the intHolStaffName to set
	 */
	public void setIntHolStaffName(String intHolStaffName) {
		this.intHolStaffName = intHolStaffName;
	}

	/**
	 * @return the apChangeLineFldTan
	 */
	public List<ApChangeLineFld> getApChangeLineFldTan() {
		return apChangeLineFldTan;
	}

	/**
	 * @param apChangeLineFldTan the apChangeLineFldTan to set
	 */
	public void setApChangeLineFldTan(List<ApChangeLineFld> apChangeLineFldTan) {
		this.apChangeLineFldTan = apChangeLineFldTan;
	}

	/**
	 * @return the apChangeLineFldInt
	 */
	public List<ApChangeLineFld> getApChangeLineFldInt() {
		return apChangeLineFldInt;
	}

	/**
	 * @param apChangeLineFldInt the apChangeLineFldInt to set
	 */
	public void setApChangeLineFldInt(List<ApChangeLineFld> apChangeLineFldInt) {
		this.apChangeLineFldInt = apChangeLineFldInt;
	}

	/**
	 * @return the intHolStaffCompanyName
	 */
	public String getIntHolStaffCompanyName() {
		return intHolStaffCompanyName;
	}

	/**
	 * @param intHolStaffCompanyName the intHolStaffCompanyName to set
	 */
	public void setIntHolStaffCompanyName(String intHolStaffCompanyName) {
		this.intHolStaffCompanyName = intHolStaffCompanyName;
	}

	/**
	 * @return the intHolStaffSectionName
	 */
	public String getIntHolStaffSectionName() {
		return intHolStaffSectionName;
	}

	/**
	 * @param intHolStaffSectionName the intHolStaffSectionName to set
	 */
	public void setIntHolStaffSectionName(String intHolStaffSectionName) {
		this.intHolStaffSectionName = intHolStaffSectionName;
	}

	public String getCostSectionName() {
		return costSectionName;
	}

	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	public String getHolRepOfficeName() {
		return holRepOfficeName;
	}

	public void setHolRepOfficeName(String holRepOfficeName) {
		this.holRepOfficeName = holRepOfficeName;
	}

	public Integer getCostSectionYear() {
		return costSectionYear;
	}

	public void setCostSectionYear(Integer costSectionYear) {
		this.costSectionYear = costSectionYear;
	}

}
