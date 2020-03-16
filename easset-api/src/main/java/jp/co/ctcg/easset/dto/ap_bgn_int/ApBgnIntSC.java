/*===================================================================
 * ファイル名 : ApBgnIntSR.java
 * 概要説明   : サービス提供開始報告検索条件
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

import lombok.ToString;

@ToString(callSuper = true)
public class ApBgnIntSC extends ApBgnInt {
	private static final long serialVersionUID = 1L;

	// 申請
	private String applicationIdPlural;				// 申請書番号複数
	private String eappIdPlural;					// e申請書類ID複数
	private Date apDateFrom;						// 申請日From
	private Date apDateTo;							// 申請日To

	// 取得
	private String astNumFld;						// 資産明細:資産番号
	private String astNumFldPlural;					// 資産明細:資産番号複数
	private Date astCompleteDateFrom;				// 開発完了日From
	private Date astCompleteDateTo;					// 開発完了日To
	private Date astReleaseDateFrom;				// リリース日From
	private Date astReleaseDateTo;					// リリース日To

	// 保有
	private String includeSectionHierarchyFlag;		// 部署階層検索フラグ

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		// 申請
		applicationIdPlural = (String)input.readObject();
		eappIdPlural = (String)input.readObject();
		apDateFrom = (Date)input.readObject();
		apDateTo = (Date)input.readObject();

		// 取得
		astNumFld = (String)input.readObject();
		astNumFldPlural = (String)input.readObject();
		astCompleteDateFrom = (Date)input.readObject();
		astCompleteDateTo = (Date)input.readObject();
		astReleaseDateFrom = (Date)input.readObject();
		astReleaseDateTo = (Date)input.readObject();

		// 保有
		includeSectionHierarchyFlag = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		// 申請
		output.writeObject(applicationIdPlural);
		output.writeObject(eappIdPlural);
		output.writeObject(apDateFrom);
		output.writeObject(apDateTo);

		// 取得
		output.writeObject(astNumFld);
		output.writeObject(astNumFldPlural);
		output.writeObject(astCompleteDateFrom);
		output.writeObject(astCompleteDateTo);
		output.writeObject(astReleaseDateFrom);
		output.writeObject(astReleaseDateTo);

		// 保有
		output.writeObject(includeSectionHierarchyFlag);
	}

	public String getApplicationIdPlural() {
		return applicationIdPlural;
	}

	public void setApplicationIdPlural(String applicationIdPlural) {
		this.applicationIdPlural = applicationIdPlural;
	}

	public String getEappIdPlural() {
		return eappIdPlural;
	}

	public void setEappIdPlural(String eappIdPlural) {
		this.eappIdPlural = eappIdPlural;
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

	public String getAstNumFld() {
		return astNumFld;
	}

	public void setAstNumFld(String astNumFld) {
		this.astNumFld = astNumFld;
	}

	public String getAstNumFldPlural() {
		return astNumFldPlural;
	}

	public void setAstNumFldPlural(String astNumFldPlural) {
		this.astNumFldPlural = astNumFldPlural;
	}

	public Date getAstCompleteDateFrom() {
		return astCompleteDateFrom;
	}

	public void setAstCompleteDateFrom(Date astCompleteDateFrom) {
		this.astCompleteDateFrom = astCompleteDateFrom;
	}

	public Date getAstCompleteDateTo() {
		return astCompleteDateTo;
	}

	public void setAstCompleteDateTo(Date astCompleteDateTo) {
		this.astCompleteDateTo = astCompleteDateTo;
	}

	public Date getAstReleaseDateFrom() {
		return astReleaseDateFrom;
	}

	public void setAstReleaseDateFrom(Date astReleaseDateFrom) {
		this.astReleaseDateFrom = astReleaseDateFrom;
	}

	public Date getAstReleaseDateTo() {
		return astReleaseDateTo;
	}

	public void setAstReleaseDateTo(Date astReleaseDateTo) {
		this.astReleaseDateTo = astReleaseDateTo;
	}

	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}

}
