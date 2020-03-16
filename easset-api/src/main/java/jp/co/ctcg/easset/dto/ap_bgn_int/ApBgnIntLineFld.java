/*===================================================================
 * ファイル名 : ApBgnIntLineFld.java
 * 概要説明   : サービス提供開始報告_資産明細
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_bgn_int;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.dto.LineData;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class ApBgnIntLineFld extends LineData {
	private static final long serialVersionUID = 1L;

	private String applicationId;		// 申請書番号
	private String lineType;			// 明細区分 1:申請時,2:承認時追加分
	private Integer lineSeq;			// 行シーケンス 明細区分毎のシーケンス
	private Date createDate;			// 作成日
	private String createStaffCode;		// 作成者社員番号
	private Date updateDate;			// 更新日
	private String updateStaffCode;		// 更新者社員番号
	private Long ppId;					// 固有番号 ProPlusの資産台帳キー
	private Date astDate;				// 計上日
	private String astNum;				// 資産番号
	private String astName;				// 資産名
	private Long astGetAmount;			// 取得価額 仮勘定の税法帳簿取得価額
	private Long astAmount;				// 資産計上額 仮勘定の会社帳簿取得価額
	private String astSectionCode;		// 資産計上部課
	private String astSectionName;		// 資産計上部課名
	private String astRouteFlag;		// 経路フラグ
	private String astRouteName;		// 経路フラグ名
	private String astPurCompanyCode;	// 仕入先コード
	private String astPurCompanyName;	// 仕入先名
	private String astSlipNum;			// 伝票番号
	private String astPrjCode;			// 資産プロジェクトコード
	private String astPrjName;			// 資産プロジェクト名

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input); // 行データスーパークラスの処理

		applicationId = (String)input.readObject();
		lineType = (String)input.readObject();
		lineSeq = Function.getExternalInteger(input.readObject());
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		ppId = Function.getExternalLong(input.readObject());
		astDate = (Date)input.readObject();
		astNum = (String)input.readObject();
		astName = (String)input.readObject();
		astGetAmount = Function.getExternalLong(input.readObject());
		astAmount = Function.getExternalLong(input.readObject());
		astSectionCode = (String)input.readObject();
		astSectionName = (String)input.readObject();
		astRouteFlag = (String)input.readObject();
		astRouteName = (String)input.readObject();
		astPurCompanyCode = (String)input.readObject();
		astPurCompanyName = (String)input.readObject();
		astSlipNum = (String)input.readObject();
		astPrjCode = (String)input.readObject();
		astPrjName = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output); // 行データスーパークラスの処理

		output.writeObject(applicationId);
		output.writeObject(lineType);
		output.writeObject(lineSeq);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(ppId);
		output.writeObject(astDate);
		output.writeObject(astNum);
		output.writeObject(astName);
		output.writeObject(astGetAmount);
		output.writeObject(astAmount);
		output.writeObject(astSectionCode);
		output.writeObject(astSectionName);
		output.writeObject(astRouteFlag);
		output.writeObject(astRouteName);
		output.writeObject(astPurCompanyCode);
		output.writeObject(astPurCompanyName);
		output.writeObject(astSlipNum);
		output.writeObject(astPrjCode);
		output.writeObject(astPrjName);
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getLineType() {
		return lineType;
	}

	public void setLineType(String lineType) {
		this.lineType = lineType;
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

	public Long getPpId() {
		return ppId;
	}

	public void setPpId(Long ppId) {
		this.ppId = ppId;
	}

	public Date getAstDate() {
		return astDate;
	}

	public void setAstDate(Date astDate) {
		this.astDate = astDate;
	}

	public String getAstNum() {
		return astNum;
	}

	public void setAstNum(String astNum) {
		this.astNum = astNum;
	}

	public String getAstName() {
		return astName;
	}

	public void setAstName(String astName) {
		this.astName = astName;
	}

	public Long getAstGetAmount() {
		return astGetAmount;
	}

	public void setAstGetAmount(Long astGetAmount) {
		this.astGetAmount = astGetAmount;
	}

	public Long getAstAmount() {
		return astAmount;
	}

	public void setAstAmount(Long astAmount) {
		this.astAmount = astAmount;
	}

	public String getAstSectionCode() {
		return astSectionCode;
	}

	public void setAstSectionCode(String astSectionCode) {
		this.astSectionCode = astSectionCode;
	}

	public String getAstRouteFlag() {
		return astRouteFlag;
	}

	public void setAstRouteFlag(String astRouteFlag) {
		this.astRouteFlag = astRouteFlag;
	}

	public String getAstPurCompanyCode() {
		return astPurCompanyCode;
	}

	public void setAstPurCompanyCode(String astPurCompanyCode) {
		this.astPurCompanyCode = astPurCompanyCode;
	}

	public String getAstSlipNum() {
		return astSlipNum;
	}

	public void setAstSlipNum(String astSlipNum) {
		this.astSlipNum = astSlipNum;
	}

	public String getAstPrjCode() {
		return astPrjCode;
	}

	public void setAstPrjCode(String astPrjCode) {
		this.astPrjCode = astPrjCode;
	}

	public String getAstPrjName() {
		return astPrjName;
	}

	public void setAstPrjName(String astPrjName) {
		this.astPrjName = astPrjName;
	}

	public String getAstSectionName() {
		return astSectionName;
	}

	public void setAstSectionName(String astSectionName) {
		this.astSectionName = astSectionName;
	}

	public String getAstPurCompanyName() {
		return astPurCompanyName;
	}

	public void setAstPurCompanyName(String astPurCompanyName) {
		this.astPurCompanyName = astPurCompanyName;
	}

	public String getAstRouteName() {
		return astRouteName;
	}

	public void setAstRouteName(String astRouteName) {
		this.astRouteName = astRouteName;
	}

}
