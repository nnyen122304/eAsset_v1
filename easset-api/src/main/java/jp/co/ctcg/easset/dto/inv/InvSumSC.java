/*===================================================================
 * ファイル名 : InvSumSC.java
 * 概要説明   : 更新履歴データクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-26 1.0  リヨン 申     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.inv;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import lombok.ToString;

@ToString(callSuper = true)
public class InvSumSC extends InvSum {
	private static final long serialVersionUID = 1L;

	private String includeSectionHierarchyFlag;
	private String invImplementation;
	private String searchScopeType;

	private String invLinkType; // 棚卸連携 1：紐付有り,2：保有部署不明,3：資産情報不明

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input);
		includeSectionHierarchyFlag = (String)input.readObject();
		invImplementation = (String)input.readObject();
		searchScopeType = (String)input.readObject();
		invLinkType = (String)input.readObject();
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output);
		output.writeObject(includeSectionHierarchyFlag);
		output.writeObject(invImplementation);
		output.writeObject(searchScopeType);
		output.writeObject(invLinkType);
	}

	/**
	 * includeSectionHierarchyFlagを取得します。
	 * @return includeSectionHierarchyFlag
	 */
	public String getIncludeSectionHierarchyFlag() {
		return includeSectionHierarchyFlag;
	}

	/**
	 * includeSectionHierarchyFlag
	 * @param includeSectionHierarchyFlagを設定します。
	 */
	public void setIncludeSectionHierarchyFlag(String includeSectionHierarchyFlag) {
		this.includeSectionHierarchyFlag = includeSectionHierarchyFlag;
	}

	/**
	 * invImplementationを取得します。
	 * @return invImplementation
	 */
	public String getInvImplementation() {
		return invImplementation;
	}

	/**
	 * invImplementation
	 * @param invImplementationを設定します。
	 */
	public void setInvImplementation(String invImplementation) {
		this.invImplementation = invImplementation;
	}

	/**
	 * searchScopeTypeを取得します。
	 * @return searchScopeType
	 */
	public String getSearchScopeType() {
		return searchScopeType;
	}

	/**
	 * searchScopeType
	 * @param searchScopeTypeを設定します。
	 */
	public void setSearchScopeType(String searchScopeType) {
		this.searchScopeType = searchScopeType;
	}

	/**
	 * @return the invLinkType
	 */
	public String getInvLinkType() {
		return invLinkType;
	}

	/**
	 * @param invLinkType the invLinkType to set
	 */
	public void setInvLinkType(String invLinkType) {
		this.invLinkType = invLinkType;
	}

}
