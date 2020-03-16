/*===================================================================
 * ファイル名 : CostScrSC.java
 * 概要説明   : 経費負担部課精査集約データ検索クラス
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

import lombok.ToString;

@ToString(callSuper = true)
public class CostScrSC extends CostScr {

	private static final long serialVersionUID = 1L;
	private String scrImplementation; // 精査状況
	private String apChangeStatus;	//	移動申請状況
	private String includeSectionHierarchyFlag;		// 部署階層検索フラグ

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input);
		scrImplementation = (String)input.readObject();
		apChangeStatus = (String)input.readObject();
		includeSectionHierarchyFlag = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output);
		output.writeObject(scrImplementation);
		output.writeObject(apChangeStatus);
		output.writeObject(includeSectionHierarchyFlag);
	}

	public String getScrImplementation() {
		return scrImplementation;
	}

	public void setScrImplementation(String scrImplementation) {
		this.scrImplementation = scrImplementation;
	}

	public String getApChangeStatus() {
		return apChangeStatus;
	}

	public void setApChangeStatus(String apChangeStatus) {
		this.apChangeStatus = apChangeStatus;
	}

	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}

}
