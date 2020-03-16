/*===================================================================
 * ファイル名 : InvLine.java
 * 概要説明   : 更新履歴データクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-26 1.0  リヨン 申     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.inv;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class InvLine implements Externalizable {
	private static final long serialVersionUID = 1L;
	private Integer invLineSeq; // 行シーケンス
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String period; // 会計年月
	private String companyCode; // 会社コード
	private String holSectionCode; // 保有部署コード
	private Integer holSectionYear; // 保有部署年度
	private String invAssetType; // 資産区分 1:有形固定資産,2:資産除去費用,3無形固定資産(本勘定),4:無形固定資産(仮勘定),5:リース資産,6:レンタル資産,7:備品等
	private String invStatus; // 棚卸ステータス 1:未実施,2:有,3:無,4:棚卸対象外
	private String invComment; // コメント
	private String astLicId; // 現物管理番号 情報機器管理番号、ライセンス管理番号
	private Long ppId; // 固有番号
	private String astNum; // 資産番号
	private String contractNum; // 契約番号
	private String contractSubNum; // 契約枝番
	private String applicationId; // 取得申請番号
	private String deleteFlag; // 抹消フラグ 0:抹消されていない,1:抹消済
	private Date deleteDate; // 抹消日
	private String astName; // 資産名
	private String holStaffCode; // 資産管理担当者
	private String useStaffCode; // 使用者社員番号
	private String holOfficeCode; // 設置場所 汎用マスタ-OFFICE
	private Integer holOfficeFloor; // 設置場所階数
	private String holOfficeRoomNum; // 部屋番号
	private String holOfficeRackNum; // ラック番号
	private Integer holQuantity; // 数量
	private String invLinkType; // 棚卸連携
	private String invStaffCode; // 棚卸対象社員番号
	private String dscDescription;					// 現物備考
	private String dscAttribute1;	// 管理項目1
	private String dscAttribute2;	// 管理項目2

	private String invStatus1; // 棚卸ステータス
	private String invStatus2; // 棚卸ステータス
	private String invStatusName;	//	棚卸ステータス名 ※ CSVファイル出力用


	private String deleteFlagName; // 抹消名
	private String holStaffName; // 資産管理担当者
	private String useStaffName; // 使用者
	private String holOfficeName; // 設置場所
	private String holSectionName; // 保有部署
	private String skkStatusName; // 償却ステータス

	////////////// 資産
	private String skkshisankbn; // 償却資産区分
	private String skkshisankbnName; // 償却資産区分名
	private String shisanknrkbn; // 資産管理区分
	private String shisanknrkbnName; // 資産管理区分名
	private String stkymd; // 取得日
	private String stkymdF; // 取得日
	private Long stkgkk; // 取得価額(会社帳簿)
	private Long stkgkz; // 取得価額(税法帳簿)
	private Long toBokak; // 当月末帳簿価額(会社帳簿)
	private String shuruicd; // 種類コード
	private String shuruinm; // 種類名称
	private String bunruicd; // 分類コード
	private String bunruinm; // 分類名称
	private String skkhok; // 償却方法(会社帳簿)
	private String skkhokName; // 償却方法名(会計帳簿)
	private String tainenk; // 耐用年数(会社帳簿)
	private String costType; // 原販ﾌﾗｸﾞ(任意項目eAsset追加定義)
	private String costTypeName; // 原販ﾌﾗｸﾞ名(任意項目eAsset追加定義)
	private String depPrjCode; // 償却PJ(任意項目eAsset追加定義)
	private String depPrjName; // 償却PJ名(任意項目eAsset追加定義)
	private String soshiki2cd; // 組織2コード
	private String soshiki2nm; // 組織2名称
	private String itemGroupCode; // 案件ｸﾞﾙｰﾌﾟ(任意項目eAsset追加定義)
	private String itemGroupName; // 案件ｸﾞﾙｰﾌﾟ名(任意項目eAsset追加定義)
	private String setchicd; // 設置場所コード
	private String setchinm; // 設置場所名称
	private String biko1; // 備考1
	private String biko2; // 備考2
	private String purCompanyCode; // 仕入先コード
	private String purCompanyName; // 仕入先名
	private String slipNum; // 伝票番号
	private String oldKeijoymd; // 計上日(最古)
	private String newKeijoymd; // 計上日(最新)
	private String oldKeijoymdF; // 計上日(最古)
	private String newKeijoymdF; // 計上日(最新)

	//	リース
	private String shisannmA; // 資産名称
	private String lastymdC;  // リース開始年月日
	private String lamanryoymdC; // リース満了年月日
	private String lakikanC; // リース期間
	private Long laryoTotalA; // リース料総額
	private Long ikkailaryoA; // 1回当リース料
	private String lakaishacdC; // リース会社名
	private String kykkbnC;	//	契約区分名
	private String latorihikikbnC; // リース取引判定名
	private String bskeijokbnC; // B/S計上区分
	private String niniLd_3cdA; // 任意(台帳)3コード
	private String niniLd_3nmA; // 任意(台帳)3名称
	private String niniLd_6cdA; // 任意(台帳)6コード
	private String niniLd_6nmA; // 任意(台帳)6名称
	private String niniLd_7cdA; // 任意(台帳)7コード
	private String niniLd_7nmA; // 任意(台帳)7名称
	private Long stkgkkA; // 資産計上額
	private Long toBokakA; // 帳簿価額
	private String lakaishacdCName; // リース会社名
	private String shuruicdA; // 種類コード
	private String shuruinmA; // 種類名称
	private String bunruicdA; // 分類コード
	private String bunruinmA; // 分類名称
	private String setchicdA; //	代表設置場所
	private String setchinmA; //	代表設置場所
	private String soshiki2cdA; // 資産計上部課
	private String soshiki2nmA; // 資産計上部課名
	private String biko1A; // 備考1
	private String biko2A; // 備考2
	private String lastymdCF; // リース開始年月日
	private String lamanryoymdCF; // リース満了年月日
	private String kykkbnCName;	//	契約区分名
	private String latorihikikbnCName; // リース取引判定名
	private String bskeijokbnCName; // B/S計上区分
	private String stymdL; // 開始日
	private String stymdLF; // 開始日(フォーマット)
	private String manryoymdL; // 満了日
	private String manryoymdLF; // 満了日(フォーマット)
	private Integer kikanL; // 期間

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		invLineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		period = (String)input.readObject();
		companyCode = (String)input.readObject();
		holSectionCode = (String)input.readObject();
		holSectionYear = Function.getExternalInteger(input.readObject());
		invAssetType = (String)input.readObject();
		invStatus = (String)input.readObject();
		invComment = (String)input.readObject();
		astLicId = (String)input.readObject();
		ppId = Function.getExternalLong(input.readObject());
		astNum = (String)input.readObject();
		contractNum = (String)input.readObject();
		contractSubNum = (String)input.readObject();
		applicationId = (String)input.readObject();
		deleteFlag = (String)input.readObject();
		deleteDate = (Date)input.readObject();
		astName = (String)input.readObject();
		holStaffCode = (String)input.readObject();
		useStaffCode = (String)input.readObject();
		holOfficeCode = (String)input.readObject();
		holOfficeFloor = Function.getExternalInteger(input.readObject());
		holOfficeRoomNum = (String)input.readObject();
		holOfficeRackNum = (String)input.readObject();
		holQuantity = Function.getExternalInteger(input.readObject());
		invLinkType = (String)input.readObject();
		invStaffCode = (String)input.readObject();
		dscDescription = (String)input.readObject();
		dscAttribute1 = (String)input.readObject();
		dscAttribute2 = (String)input.readObject();

		invStatus1 = (String)input.readObject();
		invStatus2 = (String)input.readObject();

		deleteFlagName = (String)input.readObject();
		holStaffName = (String)input.readObject();
		useStaffName = (String)input.readObject();
		holOfficeName = (String)input.readObject();
		holSectionName = (String)input.readObject();
		skkStatusName = (String)input.readObject();

		////////////// 資産
		skkshisankbn = (String)input.readObject();
		skkshisankbnName = (String)input.readObject();
		shisanknrkbn = (String)input.readObject();
		shisanknrkbnName = (String)input.readObject();
		stkymd = (String)input.readObject();
		stkymdF = (String)input.readObject();
		stkgkk = Function.getExternalLong(input.readObject());
		stkgkz = Function.getExternalLong(input.readObject());
		toBokak = Function.getExternalLong(input.readObject());
		shuruicd = (String)input.readObject();
		shuruinm = (String)input.readObject();
		bunruicd = (String)input.readObject();
		bunruinm = (String)input.readObject();
		skkhok = (String)input.readObject();
		skkhokName = (String)input.readObject();
		tainenk = (String)input.readObject(); // Function.getExternalInteger(input.readObject());
		costType = (String)input.readObject();
		costTypeName = (String)input.readObject();
		depPrjCode = (String)input.readObject();
		depPrjName = (String)input.readObject();
		soshiki2cd = (String)input.readObject();
		soshiki2nm = (String)input.readObject();
		itemGroupCode = (String)input.readObject();
		itemGroupName = (String)input.readObject();
		setchicd = (String)input.readObject();
		setchinm = (String)input.readObject();
		biko1 = (String)input.readObject();
		biko2 = (String)input.readObject();
		purCompanyCode = (String)input.readObject();
		purCompanyName = (String)input.readObject();
		slipNum = (String)input.readObject();
		oldKeijoymd = (String)input.readObject();
		newKeijoymd = (String)input.readObject();
		oldKeijoymdF = (String)input.readObject();
		newKeijoymdF = (String)input.readObject();

		//	リース
		shisannmA = (String)input.readObject();
		lastymdC = (String)input.readObject();
		lamanryoymdC = (String)input.readObject();
		lakikanC = (String)input.readObject();
		laryoTotalA = Function.getExternalLong(input.readObject());
		ikkailaryoA = Function.getExternalLong(input.readObject());
		lakaishacdC = (String)input.readObject();
		kykkbnC = (String)input.readObject();
		latorihikikbnC = (String)input.readObject();
		bskeijokbnC = (String)input.readObject();
		niniLd_3cdA = (String)input.readObject();
		niniLd_3nmA= (String)input.readObject();
		niniLd_6cdA = (String)input.readObject();
		niniLd_6nmA = (String)input.readObject();
		niniLd_7cdA = (String)input.readObject();
		niniLd_7nmA = (String)input.readObject();
		stkgkkA = Function.getExternalLong(input.readObject());
		toBokakA = Function.getExternalLong(input.readObject());
		lakaishacdCName = (String)input.readObject();
		shuruicdA = (String)input.readObject();
		shuruinmA = (String)input.readObject();
		bunruicdA = (String)input.readObject();
		bunruinmA = (String)input.readObject();
		setchicdA = (String)input.readObject();
		setchinmA = (String)input.readObject();
		soshiki2cdA = (String)input.readObject();
		soshiki2nmA = (String)input.readObject();
		biko1A = (String)input.readObject();
		biko2A = (String)input.readObject();
		lastymdCF = (String)input.readObject();
		lamanryoymdCF = (String)input.readObject();
		kykkbnCName = (String)input.readObject();
		latorihikikbnCName = (String)input.readObject();
		bskeijokbnCName = (String)input.readObject();
		stymdL = (String)input.readObject();
		stymdLF = (String)input.readObject();
		manryoymdL = (String)input.readObject();
		manryoymdLF = (String)input.readObject();
		kikanL = Function.getExternalInteger(input.readObject());
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(invLineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(period);
		output.writeObject(companyCode);
		output.writeObject(holSectionCode);
		output.writeObject(holSectionYear);
		output.writeObject(invAssetType);
		output.writeObject(invStatus);
		output.writeObject(invComment);
		output.writeObject(astLicId);
		output.writeObject(ppId);
		output.writeObject(astNum);
		output.writeObject(contractNum);
		output.writeObject(contractSubNum);
		output.writeObject(applicationId);
		output.writeObject(deleteFlag);
		output.writeObject(deleteDate);
		output.writeObject(astName);
		output.writeObject(holStaffCode);
		output.writeObject(useStaffCode);
		output.writeObject(holOfficeCode);
		output.writeObject(holOfficeFloor);
		output.writeObject(holOfficeRoomNum);
		output.writeObject(holOfficeRackNum);
		output.writeObject(holQuantity);
		output.writeObject(invLinkType);
		output.writeObject(invStaffCode);
		output.writeObject(dscDescription);
		output.writeObject(dscAttribute1);
		output.writeObject(dscAttribute2);

		output.writeObject(invStatus1);
		output.writeObject(invStatus2);

		output.writeObject(deleteFlagName);
		output.writeObject(holStaffName);
		output.writeObject(useStaffName);
		output.writeObject(holOfficeName);
		output.writeObject(holSectionName);
		output.writeObject(skkStatusName);

		////////////// 資産
		output.writeObject(skkshisankbn);
		output.writeObject(skkshisankbnName);
		output.writeObject(shisanknrkbn);
		output.writeObject(shisanknrkbnName);
		output.writeObject(stkymd);
		output.writeObject(stkymdF);
		output.writeObject(stkgkk);
		output.writeObject(stkgkz);
		output.writeObject(toBokak);
		output.writeObject(shuruicd);
		output.writeObject(shuruinm);
		output.writeObject(bunruicd);
		output.writeObject(bunruinm);
		output.writeObject(skkhok);
		output.writeObject(skkhokName);
		output.writeObject(tainenk);
		output.writeObject(costType);
		output.writeObject(costTypeName);
		output.writeObject(depPrjCode);
		output.writeObject(depPrjName);
		output.writeObject(soshiki2cd);
		output.writeObject(soshiki2nm);
		output.writeObject(itemGroupCode);
		output.writeObject(itemGroupName);
		output.writeObject(setchicd);
		output.writeObject(setchinm);
		output.writeObject(biko1);
		output.writeObject(biko2);
		output.writeObject(purCompanyCode);
		output.writeObject(purCompanyName);
		output.writeObject(slipNum);
		output.writeObject(oldKeijoymd);
		output.writeObject(newKeijoymd);
		output.writeObject(oldKeijoymdF);
		output.writeObject(newKeijoymdF);

		//	リース
		output.writeObject(shisannmA);
		output.writeObject(lastymdC );
		output.writeObject(lamanryoymdC);
		output.writeObject(lakikanC);
		output.writeObject(laryoTotalA);
		output.writeObject(ikkailaryoA);
		output.writeObject(lakaishacdC);
		output.writeObject(kykkbnC);
		output.writeObject(latorihikikbnC);
		output.writeObject(bskeijokbnC);
		output.writeObject(niniLd_3cdA);
		output.writeObject(niniLd_3nmA);
		output.writeObject(niniLd_6cdA);
		output.writeObject(niniLd_6nmA);
		output.writeObject(niniLd_7cdA);
		output.writeObject(niniLd_7nmA);
		output.writeObject(stkgkkA);
		output.writeObject(toBokakA);
		output.writeObject(lakaishacdCName);
		output.writeObject(shuruicdA);
		output.writeObject(shuruinmA);
		output.writeObject(bunruicdA);
		output.writeObject(bunruinmA);
		output.writeObject(setchicdA);
		output.writeObject(setchinmA);
		output.writeObject(soshiki2cdA);
		output.writeObject(soshiki2nmA);
		output.writeObject(biko1A);
		output.writeObject(biko2A);
		output.writeObject(lastymdCF);
		output.writeObject(lamanryoymdCF);
		output.writeObject(kykkbnCName);
		output.writeObject(latorihikikbnCName);
		output.writeObject(bskeijokbnCName);
		output.writeObject(stymdL);
		output.writeObject(stymdLF);
		output.writeObject(manryoymdL);
		output.writeObject(manryoymdLF);
		output.writeObject(kikanL);
	}

	/**
	 * invLineSeqを取得します。
	 * @return invLineSeq
	 */
	public Integer getInvLineSeq() {
		return invLineSeq;
	}

	/**
	 * invLineSeq
	 * @param invLineSeqを設定します。
	 */
	public void setInvLineSeq(Integer invLineSeq) {
		this.invLineSeq = invLineSeq;
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
	 * periodを取得します。
	 * @return period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * period
	 * @param periodを設定します。
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * companyCodeを取得します。
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode
	 * @param companyCodeを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * invAssetTypeを取得します。
	 * @return invAssetType
	 */
	public String getInvAssetType() {
		return invAssetType;
	}

	/**
	 * invAssetType
	 * @param invAssetTypeを設定します。
	 */
	public void setInvAssetType(String invAssetType) {
		this.invAssetType = invAssetType;
	}

	/**
	 * invStatusを取得します。
	 * @return invStatus
	 */
	public String getInvStatus() {
		return invStatus;
	}

	/**
	 * invStatus
	 * @param invStatusを設定します。
	 */
	public void setInvStatus(String invStatus) {
		this.invStatus = invStatus;
	}

	/**
	 * invCommentを取得します。
	 * @return invComment
	 */
	public String getInvComment() {
		return invComment;
	}

	/**
	 * invComment
	 * @param invCommentを設定します。
	 */
	public void setInvComment(String invComment) {
		this.invComment = invComment;
	}

	/**
	 * astLicIdを取得します。
	 * @return astLicId
	 */
	public String getAstLicId() {
		return astLicId;
	}

	/**
	 * astLicId
	 * @param astLicIdを設定します。
	 */
	public void setAstLicId(String astLicId) {
		this.astLicId = astLicId;
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
	 * contractSubNumを取得します。
	 * @return contractSubNum
	 */
	public String getContractSubNum() {
		return contractSubNum;
	}

	/**
	 * contractSubNum
	 * @param contractSubNumを設定します。
	 */
	public void setContractSubNum(String contractSubNum) {
		this.contractSubNum = contractSubNum;
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
	 * deleteFlagNameを取得します。
	 * @return deleteFlagName
	 */
	public String getDeleteFlagName() {
		return deleteFlagName;
	}

	/**
	 * deleteFlagName
	 * @param deleteFlagNameを設定します。
	 */
	public void setDeleteFlagName(String deleteFlagName) {
		this.deleteFlagName = deleteFlagName;
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
	 * skkStatusNameを取得します。
	 * @return skkStatusName
	 */
	public String getSkkStatusName() {
		return skkStatusName;
	}

	/**
	 * skkStatusName
	 * @param skkStatusNameを設定します。
	 */
	public void setSkkStatusName(String skkStatusName) {
		this.skkStatusName = skkStatusName;
	}

	/**
	 * skkshisankbnを取得します。
	 * @return skkshisankbn
	 */
	public String getSkkshisankbn() {
		return skkshisankbn;
	}

	/**
	 * skkshisankbn
	 * @param skkshisankbnを設定します。
	 */
	public void setSkkshisankbn(String skkshisankbn) {
		this.skkshisankbn = skkshisankbn;
	}

	/**
	 * skkshisankbnNameを取得します。
	 * @return skkshisankbnName
	 */
	public String getSkkshisankbnName() {
		return skkshisankbnName;
	}

	/**
	 * skkshisankbnName
	 * @param skkshisankbnNameを設定します。
	 */
	public void setSkkshisankbnName(String skkshisankbnName) {
		this.skkshisankbnName = skkshisankbnName;
	}

	/**
	 * shisanknrkbnを取得します。
	 * @return shisanknrkbn
	 */
	public String getShisanknrkbn() {
		return shisanknrkbn;
	}

	/**
	 * shisanknrkbn
	 * @param shisanknrkbnを設定します。
	 */
	public void setShisanknrkbn(String shisanknrkbn) {
		this.shisanknrkbn = shisanknrkbn;
	}

	/**
	 * shisanknrkbnNameを取得します。
	 * @return shisanknrkbnName
	 */
	public String getShisanknrkbnName() {
		return shisanknrkbnName;
	}

	/**
	 * shisanknrkbnName
	 * @param shisanknrkbnNameを設定します。
	 */
	public void setShisanknrkbnName(String shisanknrkbnName) {
		this.shisanknrkbnName = shisanknrkbnName;
	}

	/**
	 * stkymdを取得します。
	 * @return stkymd
	 */
	public String getStkymd() {
		return stkymd;
	}

	/**
	 * stkymd
	 * @param stkymdを設定します。
	 */
	public void setStkymd(String stkymd) {
		this.stkymd = stkymd;
	}

	/**
	 * stkymdFを取得します。
	 * @return stkymdF
	 */
	public String getStkymdF() {
		return stkymdF;
	}

	/**
	 * stkymdF
	 * @param stkymdFを設定します。
	 */
	public void setStkymdF(String stkymdF) {
		this.stkymdF = stkymdF;
	}



	/**
	 * stkgkzを取得します。
	 * @return stkgkz
	 */
	public Long getStkgkz() {
		return stkgkz;
	}

	/**
	 * stkgkz
	 * @param stkgkzを設定します。
	 */
	public void setStkgkz(Long stkgkz) {
		this.stkgkz = stkgkz;
	}

	/**
	 * toBokakを取得します。
	 * @return toBokak
	 */
	public Long getToBokak() {
		return toBokak;
	}

	/**
	 * toBokak
	 * @param toBokakを設定します。
	 */
	public void setToBokak(Long toBokak) {
		this.toBokak = toBokak;
	}

	/**
	 * shuruicdを取得します。
	 * @return shuruicd
	 */
	public String getShuruicd() {
		return shuruicd;
	}

	/**
	 * shuruicd
	 * @param shuruicdを設定します。
	 */
	public void setShuruicd(String shuruicd) {
		this.shuruicd = shuruicd;
	}

	/**
	 * shuruinmを取得します。
	 * @return shuruinm
	 */
	public String getShuruinm() {
		return shuruinm;
	}

	/**
	 * shuruinm
	 * @param shuruinmを設定します。
	 */
	public void setShuruinm(String shuruinm) {
		this.shuruinm = shuruinm;
	}

	/**
	 * skkhokを取得します。
	 * @return skkhok
	 */
	public String getSkkhok() {
		return skkhok;
	}

	/**
	 * skkhok
	 * @param skkhokを設定します。
	 */
	public void setSkkhok(String skkhok) {
		this.skkhok = skkhok;
	}

	/**
	 * skkhokNameを取得します。
	 * @return skkhokName
	 */
	public String getSkkhokName() {
		return skkhokName;
	}

	/**
	 * skkhokName
	 * @param skkhokNameを設定します。
	 */
	public void setSkkhokName(String skkhokName) {
		this.skkhokName = skkhokName;
	}


	/**
	 * tainenkを取得します。
	 * @return tainenk
	 */
	public String getTainenk() {
		return tainenk;
	}

	/**
	 * tainenk
	 * @param tainenkを設定します。
	 */
	public void setTainenk(String tainenk) {
		this.tainenk = tainenk;
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
	 * costTypeNameを取得します。
	 * @return costTypeName
	 */
	public String getCostTypeName() {
		return costTypeName;
	}

	/**
	 * costTypeName
	 * @param costTypeNameを設定します。
	 */
	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}

	/**
	 * depPrjCodeを取得します。
	 * @return depPrjCode
	 */
	public String getDepPrjCode() {
		return depPrjCode;
	}

	/**
	 * depPrjCode
	 * @param depPrjCodeを設定します。
	 */
	public void setDepPrjCode(String depPrjCode) {
		this.depPrjCode = depPrjCode;
	}

	/**
	 * depPrjNameを取得します。
	 * @return depPrjName
	 */
	public String getDepPrjName() {
		return depPrjName;
	}

	/**
	 * depPrjName
	 * @param depPrjNameを設定します。
	 */
	public void setDepPrjName(String depPrjName) {
		this.depPrjName = depPrjName;
	}

	/**
	 * soshiki2cdを取得します。
	 * @return soshiki2cd
	 */
	public String getSoshiki2cd() {
		return soshiki2cd;
	}

	/**
	 * soshiki2cd
	 * @param soshiki2cdを設定します。
	 */
	public void setSoshiki2cd(String soshiki2cd) {
		this.soshiki2cd = soshiki2cd;
	}

	/**
	 * soshiki2nmを取得します。
	 * @return soshiki2nm
	 */
	public String getSoshiki2nm() {
		return soshiki2nm;
	}

	/**
	 * soshiki2nm
	 * @param soshiki2nmを設定します。
	 */
	public void setSoshiki2nm(String soshiki2nm) {
		this.soshiki2nm = soshiki2nm;
	}

	/**
	 * itemGroupCodeを取得します。
	 * @return itemGroupCode
	 */
	public String getItemGroupCode() {
		return itemGroupCode;
	}

	/**
	 * itemGroupCode
	 * @param itemGroupCodeを設定します。
	 */
	public void setItemGroupCode(String itemGroupCode) {
		this.itemGroupCode = itemGroupCode;
	}

	/**
	 * itemGroupNameを取得します。
	 * @return itemGroupName
	 */
	public String getItemGroupName() {
		return itemGroupName;
	}

	/**
	 * itemGroupName
	 * @param itemGroupNameを設定します。
	 */
	public void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
	}

	/**
	 * setchicdを取得します。
	 * @return setchicd
	 */
	public String getSetchicd() {
		return setchicd;
	}

	/**
	 * setchicd
	 * @param setchicdを設定します。
	 */
	public void setSetchicd(String setchicd) {
		this.setchicd = setchicd;
	}

	/**
	 * setchinmを取得します。
	 * @return setchinm
	 */
	public String getSetchinm() {
		return setchinm;
	}

	/**
	 * setchinm
	 * @param setchinmを設定します。
	 */
	public void setSetchinm(String setchinm) {
		this.setchinm = setchinm;
	}

	/**
	 * biko1を取得します。
	 * @return biko1
	 */
	public String getBiko1() {
		return biko1;
	}

	/**
	 * biko1
	 * @param biko1を設定します。
	 */
	public void setBiko1(String biko1) {
		this.biko1 = biko1;
	}

	/**
	 * biko2を取得します。
	 * @return biko2
	 */
	public String getBiko2() {
		return biko2;
	}

	/**
	 * biko2
	 * @param biko2を設定します。
	 */
	public void setBiko2(String biko2) {
		this.biko2 = biko2;
	}

	/**
	 * shisannmAを取得します。
	 * @return shisannmA
	 */
	public String getShisannmA() {
		return shisannmA;
	}

	/**
	 * shisannmA
	 * @param shisannmAを設定します。
	 */
	public void setShisannmA(String shisannmA) {
		this.shisannmA = shisannmA;
	}

	/**
	 * lastymdCを取得します。
	 * @return lastymdC
	 */
	public String getLastymdC() {
		return lastymdC;
	}

	/**
	 * lastymdC
	 * @param lastymdCを設定します。
	 */
	public void setLastymdC(String lastymdC) {
		this.lastymdC = lastymdC;
	}

	/**
	 * lamanryoymdCを取得します。
	 * @return lamanryoymdC
	 */
	public String getLamanryoymdC() {
		return lamanryoymdC;
	}

	/**
	 * lamanryoymdC
	 * @param lamanryoymdCを設定します。
	 */
	public void setLamanryoymdC(String lamanryoymdC) {
		this.lamanryoymdC = lamanryoymdC;
	}

	/**
	 * lakikanCを取得します。
	 * @return lakikanC
	 */
	public String getLakikanC() {
		return lakikanC;
	}

	/**
	 * lakikanC
	 * @param lakikanCを設定します。
	 */
	public void setLakikanC(String lakikanC) {
		this.lakikanC = lakikanC;
	}

	/**
	 * laryoTotalAを取得します。
	 * @return laryoTotalA
	 */
	public Long getLaryoTotalA() {
		return laryoTotalA;
	}

	/**
	 * laryoTotalA
	 * @param laryoTotalAを設定します。
	 */
	public void setLaryoTotalA(Long laryoTotalA) {
		this.laryoTotalA = laryoTotalA;
	}

	/**
	 * ikkailaryoAを取得します。
	 * @return ikkailaryoA
	 */
	public Long getIkkailaryoA() {
		return ikkailaryoA;
	}

	/**
	 * ikkailaryoA
	 * @param ikkailaryoAを設定します。
	 */
	public void setIkkailaryoA(Long ikkailaryoA) {
		this.ikkailaryoA = ikkailaryoA;
	}

	/**
	 * lakaishacdCを取得します。
	 * @return lakaishacdC
	 */
	public String getLakaishacdC() {
		return lakaishacdC;
	}

	/**
	 * lakaishacdC
	 * @param lakaishacdCを設定します。
	 */
	public void setLakaishacdC(String lakaishacdC) {
		this.lakaishacdC = lakaishacdC;
	}

	/**
	 * kykkbnCを取得します。
	 * @return kykkbnC
	 */
	public String getKykkbnC() {
		return kykkbnC;
	}

	/**
	 * kykkbnC
	 * @param kykkbnCを設定します。
	 */
	public void setKykkbnC(String kykkbnC) {
		this.kykkbnC = kykkbnC;
	}

	/**
	 * latorihikikbnCを取得します。
	 * @return latorihikikbnC
	 */
	public String getLatorihikikbnC() {
		return latorihikikbnC;
	}

	/**
	 * latorihikikbnC
	 * @param latorihikikbnCを設定します。
	 */
	public void setLatorihikikbnC(String latorihikikbnC) {
		this.latorihikikbnC = latorihikikbnC;
	}

	/**
	 * bskeijokbnCを取得します。
	 * @return bskeijokbnC
	 */
	public String getBskeijokbnC() {
		return bskeijokbnC;
	}

	/**
	 * bskeijokbnC
	 * @param bskeijokbnCを設定します。
	 */
	public void setBskeijokbnC(String bskeijokbnC) {
		this.bskeijokbnC = bskeijokbnC;
	}

	/**
	 * niniLd_7nmAを取得します。
	 * @return niniLd_7nmA
	 */
	public String getNiniLd_7nmA() {
		return niniLd_7nmA;
	}

	/**
	 * niniLd_7nmA
	 * @param niniLd_7nmAを設定します。
	 */
	public void setNiniLd_7nmA(String niniLd_7nmA) {
		this.niniLd_7nmA = niniLd_7nmA;
	}

	/**
	 * niniLd_6cdAを取得します。
	 * @return niniLd_6cdA
	 */
	public String getNiniLd_6cdA() {
		return niniLd_6cdA;
	}

	/**
	 * niniLd_6cdA
	 * @param niniLd_6cdAを設定します。
	 */
	public void setNiniLd_6cdA(String niniLd_6cdA) {
		this.niniLd_6cdA = niniLd_6cdA;
	}

	/**
	 * niniLd_6nmAを取得します。
	 * @return niniLd_6nmA
	 */
	public String getNiniLd_6nmA() {
		return niniLd_6nmA;
	}

	/**
	 * niniLd_6nmA
	 * @param niniLd_6nmAを設定します。
	 */
	public void setNiniLd_6nmA(String niniLd_6nmA) {
		this.niniLd_6nmA = niniLd_6nmA;
	}

	/**
	 * niniLd_3cdAを取得します。
	 * @return niniLd_3cdA
	 */
	public String getNiniLd_3cdA() {
		return niniLd_3cdA;
	}

	/**
	 * niniLd_3cdA
	 * @param niniLd_3cdAを設定します。
	 */
	public void setNiniLd_3cdA(String niniLd_3cdA) {
		this.niniLd_3cdA = niniLd_3cdA;
	}

	/**
	 * niniLd_3nmAを取得します。
	 * @return niniLd_3nmA
	 */
	public String getNiniLd_3nmA() {
		return niniLd_3nmA;
	}

	/**
	 * niniLd_3nmA
	 * @param niniLd_3nmAを設定します。
	 */
	public void setNiniLd_3nmA(String niniLd_3nmA) {
		this.niniLd_3nmA = niniLd_3nmA;
	}

	/**
	 * stkgkkAを取得します。
	 * @return stkgkkA
	 */
	public Long getStkgkkA() {
		return stkgkkA;
	}

	/**
	 * stkgkkA
	 * @param stkgkkAを設定します。
	 */
	public void setStkgkkA(Long stkgkkA) {
		this.stkgkkA = stkgkkA;
	}

	/**
	 * toBokakAを取得します。
	 * @return toBokakA
	 */
	public Long getToBokakA() {
		return toBokakA;
	}

	/**
	 * toBokakA
	 * @param toBokakAを設定します。
	 */
	public void setToBokakA(Long toBokakA) {
		this.toBokakA = toBokakA;
	}

	/**
	 * lakaishacdCNameを取得します。
	 * @return lakaishacdCName
	 */
	public String getLakaishacdCName() {
		return lakaishacdCName;
	}

	/**
	 * lakaishacdCName
	 * @param lakaishacdCNameを設定します。
	 */
	public void setLakaishacdCName(String lakaishacdCName) {
		this.lakaishacdCName = lakaishacdCName;
	}

	/**
	 * shuruicdAを取得します。
	 * @return shuruicdA
	 */
	public String getShuruicdA() {
		return shuruicdA;
	}

	/**
	 * shuruicdA
	 * @param shuruicdAを設定します。
	 */
	public void setShuruicdA(String shuruicdA) {
		this.shuruicdA = shuruicdA;
	}

	/**
	 * shuruinmAを取得します。
	 * @return shuruinmA
	 */
	public String getShuruinmA() {
		return shuruinmA;
	}

	/**
	 * shuruinmA
	 * @param shuruinmAを設定します。
	 */
	public void setShuruinmA(String shuruinmA) {
		this.shuruinmA = shuruinmA;
	}

	/**
	 * biko1Aを取得します。
	 * @return biko1A
	 */
	public String getBiko1A() {
		return biko1A;
	}

	/**
	 * biko1A
	 * @param biko1Aを設定します。
	 */
	public void setBiko1A(String biko1A) {
		this.biko1A = biko1A;
	}

	/**
	 * biko2Aを取得します。
	 * @return biko2A
	 */
	public String getBiko2A() {
		return biko2A;
	}

	/**
	 * biko2A
	 * @param biko2Aを設定します。
	 */
	public void setBiko2A(String biko2A) {
		this.biko2A = biko2A;
	}

	/**
	 * setchicdAを取得します。
	 * @return setchicdA
	 */
	public String getSetchicdA() {
		return setchicdA;
	}

	/**
	 * setchicdA
	 * @param setchicdAを設定します。
	 */
	public void setSetchicdA(String setchicdA) {
		this.setchicdA = setchicdA;
	}

	/**
	 * setchinmAを取得します。
	 * @return setchinmA
	 */
	public String getSetchinmA() {
		return setchinmA;
	}

	/**
	 * setchinmA
	 * @param setchinmAを設定します。
	 */
	public void setSetchinmA(String setchinmA) {
		this.setchinmA = setchinmA;
	}

	/**
	 * lastymdCFを取得します。
	 * @return lastymdCF
	 */
	public String getLastymdCF() {
		return lastymdCF;
	}

	/**
	 * lastymdCF
	 * @param lastymdCFを設定します。
	 */
	public void setLastymdCF(String lastymdCF) {
		this.lastymdCF = lastymdCF;
	}

	/**
	 * lamanryoymdCFを取得します。
	 * @return lamanryoymdCF
	 */
	public String getLamanryoymdCF() {
		return lamanryoymdCF;
	}

	/**
	 * lamanryoymdCF
	 * @param lamanryoymdCFを設定します。
	 */
	public void setLamanryoymdCF(String lamanryoymdCF) {
		this.lamanryoymdCF = lamanryoymdCF;
	}

	/**
	 * kykkbnCNameを取得します。
	 * @return kykkbnCName
	 */
	public String getKykkbnCName() {
		return kykkbnCName;
	}

	/**
	 * kykkbnCName
	 * @param kykkbnCNameを設定します。
	 */
	public void setKykkbnCName(String kykkbnCName) {
		this.kykkbnCName = kykkbnCName;
	}

	/**
	 * latorihikikbnCNameを取得します。
	 * @return latorihikikbnCName
	 */
	public String getLatorihikikbnCName() {
		return latorihikikbnCName;
	}

	/**
	 * latorihikikbnCName
	 * @param latorihikikbnCNameを設定します。
	 */
	public void setLatorihikikbnCName(String latorihikikbnCName) {
		this.latorihikikbnCName = latorihikikbnCName;
	}

	/**
	 * bskeijokbnCNameを取得します。
	 * @return bskeijokbnCName
	 */
	public String getBskeijokbnCName() {
		return bskeijokbnCName;
	}

	/**
	 * bskeijokbnCName
	 * @param bskeijokbnCNameを設定します。
	 */
	public void setBskeijokbnCName(String bskeijokbnCName) {
		this.bskeijokbnCName = bskeijokbnCName;
	}

	/**
	 * niniLd_7cdAを取得します。
	 * @return niniLd_7cdA
	 */
	public String getNiniLd_7cdA() {
		return niniLd_7cdA;
	}

	/**
	 * niniLd_7cdA
	 * @param niniLd_7cdAを設定します。
	 */
	public void setNiniLd_7cdA(String niniLd_7cdA) {
		this.niniLd_7cdA = niniLd_7cdA;
	}

	/**
	 * invStatus1を取得します。
	 * @return invStatus1
	 */
	public String getInvStatus1() {
		return invStatus1;
	}

	/**
	 * invStatus1
	 * @param invStatus1を設定します。
	 */
	public void setInvStatus1(String invStatus1) {
		this.invStatus1 = invStatus1;
	}

	/**
	 * invStatus2を取得します。
	 * @return invStatus2
	 */
	public String getInvStatus2() {
		return invStatus2;
	}

	/**
	 * invStatus2
	 * @param invStatus2を設定します。
	 */
	public void setInvStatus2(String invStatus2) {
		this.invStatus2 = invStatus2;
	}

	/**
	 * invLinkTypeを取得します。
	 * @return invLinkType
	 */
	public String getInvLinkType() {
		return invLinkType;
	}

	/**
	 * invLinkType
	 * @param invLinkTypeを設定します。
	 */
	public void setInvLinkType(String invLinkType) {
		this.invLinkType = invLinkType;
	}

	/**
	 * soshiki2cdAを取得します。
	 * @return soshiki2cdA
	 */
	public String getSoshiki2cdA() {
		return soshiki2cdA;
	}

	/**
	 * soshiki2cdA
	 * @param soshiki2cdAを設定します。
	 */
	public void setSoshiki2cdA(String soshiki2cdA) {
		this.soshiki2cdA = soshiki2cdA;
	}

	/**
	 * soshiki2nmAを取得します。
	 * @return soshiki2nmA
	 */
	public String getSoshiki2nmA() {
		return soshiki2nmA;
	}

	/**
	 * soshiki2nmA
	 * @param soshiki2nmAを設定します。
	 */
	public void setSoshiki2nmA(String soshiki2nmA) {
		this.soshiki2nmA = soshiki2nmA;
	}

	/**
	 * invStaffCodeを取得します。
	 * @return invStaffCode
	 */
	public String getInvStaffCode() {
		return invStaffCode;
	}

	/**
	 * invStaffCode
	 * @param invStaffCodeを設定します。
	 */
	public void setInvStaffCode(String invStaffCode) {
		this.invStaffCode = invStaffCode;
	}

	/**
	 * stymdLを取得します。
	 * @return stymdL
	 */
	public String getStymdL() {
		return stymdL;
	}

	/**
	 * stymdL
	 * @param stymdLを設定します。
	 */
	public void setStymdL(String stymdL) {
		this.stymdL = stymdL;
	}

	/**
	 * stymdLFを取得します。
	 * @return stymdLF
	 */
	public String getStymdLF() {
		return stymdLF;
	}

	/**
	 * stymdLF
	 * @param stymdLFを設定します。
	 */
	public void setStymdLF(String stymdLF) {
		this.stymdLF = stymdLF;
	}

	/**
	 * manryoymdLを取得します。
	 * @return manryoymdL
	 */
	public String getManryoymdL() {
		return manryoymdL;
	}

	/**
	 * manryoymdL
	 * @param manryoymdLを設定します。
	 */
	public void setManryoymdL(String manryoymdL) {
		this.manryoymdL = manryoymdL;
	}

	/**
	 * manryoymdLFを取得します。
	 * @return manryoymdLF
	 */
	public String getManryoymdLF() {
		return manryoymdLF;
	}

	/**
	 * manryoymdLF
	 * @param manryoymdLFを設定します。
	 */
	public void setManryoymdLF(String manryoymdLF) {
		this.manryoymdLF = manryoymdLF;
	}

	/**
	 * kikanLを取得します。
	 * @return kikanL
	 */
	public Integer getKikanL() {
		return kikanL;
	}

	/**
	 * kikanL
	 * @param kikanLを設定します。
	 */
	public void setKikanL(Integer kikanL) {
		this.kikanL = kikanL;
	}

	/**
	 * purCompanyCodeを取得します。
	 * @return purCompanyCode
	 */
	public String getPurCompanyCode() {
		return purCompanyCode;
	}

	/**
	 * purCompanyCode
	 * @param purCompanyCodeを設定します。
	 */
	public void setPurCompanyCode(String purCompanyCode) {
		this.purCompanyCode = purCompanyCode;
	}

	/**
	 * purCompanyNameを取得します。
	 * @return purCompanyName
	 */
	public String getPurCompanyName() {
		return purCompanyName;
	}

	/**
	 * purCompanyName
	 * @param purCompanyNameを設定します。
	 */
	public void setPurCompanyName(String purCompanyName) {
		this.purCompanyName = purCompanyName;
	}

	/**
	 * slipNumを取得します。
	 * @return slipNum
	 */
	public String getSlipNum() {
		return slipNum;
	}

	/**
	 * slipNum
	 * @param slipNumを設定します。
	 */
	public void setSlipNum(String slipNum) {
		this.slipNum = slipNum;
	}

	/**
	 * oldKeijoymdを取得します。
	 * @return oldKeijoymd
	 */
	public String getOldKeijoymd() {
		return oldKeijoymd;
	}

	/**
	 * oldKeijoymd
	 * @param oldKeijoymdを設定します。
	 */
	public void setOldKeijoymd(String oldKeijoymd) {
		this.oldKeijoymd = oldKeijoymd;
	}

	/**
	 * newKeijoymdを取得します。
	 * @return newKeijoymd
	 */
	public String getNewKeijoymd() {
		return newKeijoymd;
	}

	/**
	 * newKeijoymd
	 * @param newKeijoymdを設定します。
	 */
	public void setNewKeijoymd(String newKeijoymd) {
		this.newKeijoymd = newKeijoymd;
	}

	/**
	 * oldKeijoymdFを取得します。
	 * @return oldKeijoymdF
	 */
	public String getOldKeijoymdF() {
		return oldKeijoymdF;
	}

	/**
	 * oldKeijoymdF
	 * @param oldKeijoymdFを設定します。
	 */
	public void setOldKeijoymdF(String oldKeijoymdF) {
		this.oldKeijoymdF = oldKeijoymdF;
	}

	/**
	 * newKeijoymdFを取得します。
	 * @return newKeijoymdF
	 */
	public String getNewKeijoymdF() {
		return newKeijoymdF;
	}

	/**
	 * newKeijoymdF
	 * @param newKeijoymdFを設定します。
	 */
	public void setNewKeijoymdF(String newKeijoymdF) {
		this.newKeijoymdF = newKeijoymdF;
	}

	/**
	 * invStatusNameを取得します。
	 * @return invStatusName
	 */
	public String getInvStatusName() {
		return invStatusName;
	}

	/**
	 * invStatusName
	 * @param invStatusNameを設定します。
	 */
	public void setInvStatusName(String invStatusName) {
		this.invStatusName = invStatusName;
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
	 * @return the stkgkk
	 */
	public Long getStkgkk() {
		return stkgkk;
	}

	/**
	 * @param stkgkk the stkgkk to set
	 */
	public void setStkgkk(Long stkgkk) {
		this.stkgkk = stkgkk;
	}

	/**
	 * bunruicdを取得します。
	 * @return bunruicd
	 */
	public String getBunruicd() {
		return bunruicd;
	}

	/**
	 * bunruicd
	 * @param bunruicdを設定します。
	 */
	public void setBunruicd(String bunruicd) {
		this.bunruicd = bunruicd;
	}

	/**
	 * bunruinmを取得します。
	 * @return bunruinm
	 */
	public String getBunruinm() {
		return bunruinm;
	}

	/**
	 * bunruinm
	 * @param bunruinmを設定します。
	 */
	public void setBunruinm(String bunruinm) {
		this.bunruinm = bunruinm;
	}

	/**
	 * bunruicdAを取得します。
	 * @return bunruicdA
	 */
	public String getBunruicdA() {
		return bunruicdA;
	}

	/**
	 * bunruicdA
	 * @param bunruicdAを設定します。
	 */
	public void setBunruicdA(String bunruicdA) {
		this.bunruicdA = bunruicdA;
	}

	/**
	 * bunruinmAを取得します。
	 * @return bunruinmA
	 */
	public String getBunruinmA() {
		return bunruinmA;
	}

	/**
	 * bunruinmA
	 * @param bunruinmAを設定します。
	 */
	public void setBunruinmA(String bunruinmA) {
		this.bunruinmA = bunruinmA;
	}

}
