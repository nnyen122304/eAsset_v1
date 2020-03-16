/*===================================================================
 * ファイル名 : CostScrSR.java
 * 概要説明   : 経費負担部課精査集約データ検索結果クラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2015-01-28 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.costScr;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import jp.co.ctcg.easset.rest.MappingUtils;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class CostScrSR extends CostScr {
	// ダウンロードログで使用するためtoStringをオーバーライド
	@Override
	public String toString() {
		return Function.toString(this);
	}

	private static final long serialVersionUID = 1L;

	private String scrTypeName; // 精査種別

	private String costSectionName; // 当年度：経費負担部課名
	private String costSectionNameOld; // 前年度：経費負担部課名

	private String scrSectionName; // 精査担当部署名

	private String compExecStaffName; // 完了(取消)処理実行者社員番号

	private String compFlagName; // 精査完了名

	private Long costScrCountUndecided; // 精査状況：未
	private Long costScrCountOK; // 精査状況：OK
	private Long costScrCountNG; // 精査状況：NG
	private Long costScrCountTotal; // 精査状況：計

	private Long apChangeCountUnApply; // 移動申請：未
	private Long apChangeCountApply; // 移動申請：申請中
	private Long apChangeCountApprove; // 移動申請：承認済
	private Long apChangeCountTotal; // 移動申請：計

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input);

		scrTypeName = (String)input.readObject();
		costSectionName = (String)input.readObject();
		costSectionNameOld = (String)input.readObject();
		scrSectionName = (String)input.readObject();
		compExecStaffName = (String)input.readObject();
		compFlagName = (String)input.readObject();
		costScrCountUndecided = Function.getExternalLong(input.readObject());
		costScrCountOK = Function.getExternalLong(input.readObject());
		costScrCountNG = Function.getExternalLong(input.readObject());
		costScrCountTotal = Function.getExternalLong(input.readObject());
		apChangeCountUnApply = Function.getExternalLong(input.readObject());
		apChangeCountApply = Function.getExternalLong(input.readObject());
		apChangeCountApprove = Function.getExternalLong(input.readObject());
		apChangeCountTotal = Function.getExternalLong(input.readObject());
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output);

		output.writeObject(scrTypeName);
		output.writeObject(costSectionName);
		output.writeObject(costSectionNameOld);
		output.writeObject(scrSectionName);
		output.writeObject(compExecStaffName);
		output.writeObject(compFlagName);
		output.writeObject(costScrCountUndecided);
		output.writeObject(costScrCountOK);
		output.writeObject(costScrCountNG);
		output.writeObject(costScrCountTotal);
		output.writeObject(apChangeCountUnApply);
		output.writeObject(apChangeCountApply);
		output.writeObject(apChangeCountApprove);
		output.writeObject(apChangeCountTotal);
	}

	public String getScrTypeName() {
		return scrTypeName;
	}

	public void setScrTypeName(String scrTypeName) {
		this.scrTypeName = scrTypeName;
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

	public String getCompExecStaffName() {
		return compExecStaffName;
	}

	public void setCompExecStaffName(String compExecStaffName) {
		this.compExecStaffName = compExecStaffName;
	}

	public String getCompFlagName() {
		return compFlagName;
	}

	public void setCompFlagName(String compFlagName) {
		this.compFlagName = compFlagName;
	}

	public Long getCostScrCountUndecided() {
		return costScrCountUndecided;
	}

	public void setCostScrCountUndecided(Long costScrCountUndecided) {
		this.costScrCountUndecided = costScrCountUndecided;
	}

	public Long getCostScrCountOK() {
		return costScrCountOK;
	}

	public void setCostScrCountOK(Long costScrCountOK) {
		this.costScrCountOK = costScrCountOK;
	}

	public Long getCostScrCountNG() {
		return costScrCountNG;
	}

	public void setCostScrCountNG(Long costScrCountNG) {
		this.costScrCountNG = costScrCountNG;
	}

	public Long getCostScrCountTotal() {
		return costScrCountTotal;
	}

	public void setCostScrCountTotal(Long costScrCountTotal) {
		this.costScrCountTotal = costScrCountTotal;
	}

	public Long getApChangeCountUnApply() {
		return apChangeCountUnApply;
	}

	public void setApChangeCountUnApply(Long apChangeCountUnApply) {
		this.apChangeCountUnApply = apChangeCountUnApply;
	}

	public Long getApChangeCountApply() {
		return apChangeCountApply;
	}

	public void setApChangeCountApply(Long apChangeCountApply) {
		this.apChangeCountApply = apChangeCountApply;
	}

	public Long getApChangeCountApprove() {
		return apChangeCountApprove;
	}

	public void setApChangeCountApprove(Long apChangeCountApprove) {
		this.apChangeCountApprove = apChangeCountApprove;
	}

	public Long getApChangeCountTotal() {
		return apChangeCountTotal;
	}

	public void setApChangeCountTotal(Long apChangeCountTotal) {
		this.apChangeCountTotal = apChangeCountTotal;
	}

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getScrSectionName() {
		return scrSectionName;
	}

	public void setScrSectionName(String scrSectionName) {
		this.scrSectionName = scrSectionName;
	}

	/*
	* 文字列からクラスに変換メソッド
	* @param json 変換json文字列
	* @return CostScrSR.class 該当クラス
	*/
	public static CostScrSR fromString(String json) {
		return MappingUtils.fromJson(json, CostScrSR.class);
	}

}
