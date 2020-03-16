/*===================================================================
 * ファイル名 : ApGetIntSR.java
 * 概要説明   : 取得申請(無形)検索条件
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_get_int;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class ApGetIntSC extends ApGetInt {
	private static final long serialVersionUID = 1L;

	// 申請
	private String applicationIdPlural;				// 申請書番号複数
	private String oldVerFlag;						// 旧Verも含めて検索フラグ
	private String eappIdPlural;					// e申請書類ID複数
	private Date apDateFrom;						// 申請日From
	private Date apDateTo;							// 申請日To
	private String addUpType;						// 計上区分

	// 取得
	private Date astCompletePlanDateFrom;			// ﾘﾘｰｽ/検収(納品)予定日From
	private Date astCompletePlanDateTo;				// ﾘﾘｰｽ/検収(納品)予定日To
	private String reminderFlag;					// リリース督促メール送信フラグ
	private Date reminderDateFrom;					// 督促メール送信日付From
	private Date reminderDateTo;					// 督促メール送信日付To
	private Long getTotalAmountFrom;				// 取得金額From
	private Long getTotalAmountTo;					// 取得金額To

	// 保有・設置
	private String includeSectionHierarchyFlag;		// 部署階層検索フラグ

	// 資産明細
	private String astCategoryCodeFld;				// 分類
	private String astNameFld;						// 資産名
	private String astPrjCodeFld;					// 資産プロジェクトコード
	private String astCommentFld;					// コメント

	// 備考
	private String description;						// 備考


	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		// 申請
		applicationIdPlural = (String)input.readObject();
		oldVerFlag = (String)input.readObject();
		eappIdPlural = (String)input.readObject();
		apDateFrom = (Date)input.readObject();
		apDateTo = (Date)input.readObject();
		addUpType = (String)input.readObject();

		// 取得
		astCompletePlanDateFrom = (Date)input.readObject();
		astCompletePlanDateTo = (Date)input.readObject();
		reminderFlag = (String)input.readObject();
		reminderDateFrom = (Date)input.readObject();
		reminderDateTo = (Date)input.readObject();
		getTotalAmountFrom = Function.getExternalLong(input.readObject());
		getTotalAmountTo = Function.getExternalLong(input.readObject());

		// 保有・設置
		includeSectionHierarchyFlag = (String)input.readObject();

		// 資産明細
		astCategoryCodeFld = (String)input.readObject();
		astNameFld = (String)input.readObject();
		astPrjCodeFld = (String)input.readObject();
		astCommentFld = (String)input.readObject();

		// 備考
		description = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		// 申請
		output.writeObject(applicationIdPlural);
		output.writeObject(oldVerFlag);
		output.writeObject(eappIdPlural);
		output.writeObject(apDateFrom);
		output.writeObject(apDateTo);
		output.writeObject(addUpType);

		// 取得
		output.writeObject(astCompletePlanDateFrom);
		output.writeObject(astCompletePlanDateTo);
		output.writeObject(reminderFlag);
		output.writeObject(reminderDateFrom);
		output.writeObject(reminderDateTo);
		output.writeObject(getTotalAmountFrom);
		output.writeObject(getTotalAmountTo);

		// 保有・設置
		output.writeObject(includeSectionHierarchyFlag);

		// 資産明細
		output.writeObject(astCategoryCodeFld);
		output.writeObject(astNameFld);
		output.writeObject(astPrjCodeFld);
		output.writeObject(astCommentFld);

		// 備考
		output.writeObject(description);
	}

	public String getApplicationIdPlural() {
		return applicationIdPlural;
	}

	public void setApplicationIdPlural(String applicationIdPlural) {
		this.applicationIdPlural = applicationIdPlural;
	}

	public String getOldVerFlag() {
		return oldVerFlag;
	}

	public void setOldVerFlag(String oldVerFlag) {
		this.oldVerFlag = oldVerFlag;
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

	public Date getAstCompletePlanDateFrom() {
		return astCompletePlanDateFrom;
	}

	public void setAstCompletePlanDateFrom(Date astCompletePlanDateFrom) {
		this.astCompletePlanDateFrom = astCompletePlanDateFrom;
	}

	public Date getAstCompletePlanDateTo() {
		return astCompletePlanDateTo;
	}

	public void setAstCompletePlanDateTo(Date astCompletePlanDateTo) {
		this.astCompletePlanDateTo = astCompletePlanDateTo;
	}

	public String getReminderFlag() {
		return reminderFlag;
	}

	public void setReminderFlag(String reminderFlag) {
		this.reminderFlag = reminderFlag;
	}

	public Date getReminderDateFrom() {
		return reminderDateFrom;
	}

	public void setReminderDateFrom(Date reminderDateFrom) {
		this.reminderDateFrom = reminderDateFrom;
	}

	public Date getReminderDateTo() {
		return reminderDateTo;
	}

	public void setReminderDateTo(Date reminderDateTo) {
		this.reminderDateTo = reminderDateTo;
	}

	public Long getGetTotalAmountFrom() {
		return getTotalAmountFrom;
	}

	public void setGetTotalAmountFrom(Long getTotalAmountFrom) {
		this.getTotalAmountFrom = getTotalAmountFrom;
	}

	public Long getGetTotalAmountTo() {
		return getTotalAmountTo;
	}

	public void setGetTotalAmountTo(Long getTotalAmountTo) {
		this.getTotalAmountTo = getTotalAmountTo;
	}

	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}

	public String getAstCategoryCodeFld() {
		return astCategoryCodeFld;
	}

	public void setAstCategoryCodeFld(String astCategoryCodeFld) {
		this.astCategoryCodeFld = astCategoryCodeFld;
	}

	public String getAstNameFld() {
		return astNameFld;
	}

	public void setAstNameFld(String astNameFld) {
		this.astNameFld = astNameFld;
	}

	public String getAstPrjCodeFld() {
		return astPrjCodeFld;
	}

	public void setAstPrjCodeFld(String astPrjCodeFld) {
		this.astPrjCodeFld = astPrjCodeFld;
	}

	public String getAstCommentFld() {
		return astCommentFld;
	}

	public void setAstCommentFld(String astCommentFld) {
		this.astCommentFld = astCommentFld;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAddUpType() {
		return addUpType;
	}

	public void setAddUpType(String addUpType) {
		this.addUpType = addUpType;
	}

}
