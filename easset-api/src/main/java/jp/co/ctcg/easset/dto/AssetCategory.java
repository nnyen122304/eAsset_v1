/*===================================================================
 * ファイル名 : NewDivision.java
 * 概要説明   : 部署ツリーデータクラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-26 1.0  リヨン 申     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class AssetCategory implements Serializable {
	private static final long serialVersionUID = 1L;

	private String categorySegment;		// 分類種別（ASSET_CATEGORY1：大分類、ASSET_CATEGORY2：小分類）
	private String categoryCode;		// 分類コード
	private String categoryName;		// 分類名
	private String parentCategoryCode;	// 親分類コード
	private String parentCategoryName;	// 親分類名
	private String childCategoryValue2;	// 小分類-数量管理
	private String childCategoryValue3;	// 小分類-シンクライアント
	private String childCategoryValue4;	// 小分類-シール発行有無
	private String childCategoryValue5;	// 小分類-シリアル管理

	public String getCategorySegment() {
		return categorySegment;
	}
	public void setCategorySegment(String categorySegment) {
		this.categorySegment = categorySegment;
	}
	public String getCategoryCode() {
		return categoryCode;
	}
	public void setCategoryCode(String categoryCode) {
		this.categoryCode = categoryCode;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getParentCategoryCode() {
		return parentCategoryCode;
	}
	public void setParentCategoryCode(String parentCategoryCode) {
		this.parentCategoryCode = parentCategoryCode;
	}
	public String getParentCategoryName() {
		return parentCategoryName;
	}
	public void setParentCategoryName(String parentCategoryName) {
		this.parentCategoryName = parentCategoryName;
	}
	/**
	 * childCategoryValue2を取得します。
	 * @return childCategoryValue2
	 */
	public String getChildCategoryValue2() {
		return childCategoryValue2;
	}
	/**
	 * childCategoryValue2
	 * @param childCategoryValue2を設定します。
	 */
	public void setChildCategoryValue2(String childCategoryValue2) {
		this.childCategoryValue2 = childCategoryValue2;
	}
	/**
	 * childCategoryValue4を取得します。
	 * @return childCategoryValue4
	 */
	public String getChildCategoryValue4() {
		return childCategoryValue4;
	}
	/**
	 * childCategoryValue4
	 * @param childCategoryValue4を設定します。
	 */
	public void setChildCategoryValue4(String childCategoryValue4) {
		this.childCategoryValue4 = childCategoryValue4;
	}
	public String getChildCategoryValue5() {
		return childCategoryValue5;
	}
	public void setChildCategoryValue5(String childCategoryValue5) {
		this.childCategoryValue5 = childCategoryValue5;
	}
	/**
	 * @return the childCategoryValue3
	 */
	public String getChildCategoryValue3() {
		return childCategoryValue3;
	}
	/**
	 * @param childCategoryValue3 the childCategoryValue3 to set
	 */
	public void setChildCategoryValue3(String childCategoryValue3) {
		this.childCategoryValue3 = childCategoryValue3;
	}

}
