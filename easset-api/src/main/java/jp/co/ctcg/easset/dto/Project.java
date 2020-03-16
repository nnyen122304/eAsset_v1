/*===================================================================
 * ファイル名 : User.java
 * 概要説明   : ユーザー情報
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-06 1.0  リヨン 崔　  新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.Serializable;

import lombok.ToString;

@ToString
public class Project implements Serializable {
	private static final long serialVersionUID = 1L;

	private String projectCode; // プロジェクトコード
	private String projectName; // プロジェクト名
	private String projectType; // プロジェクトタイプ
	/**
	 * @return the projectCode
	 */
	public String getProjectCode() {
		return projectCode;
	}
	/**
	 * @param projectCode the projectCode to set
	 */
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
	/**
	 * @return the projectName
	 */
	public String getProjectName() {
		return projectName;
	}
	/**
	 * @param projectName the projectName to set
	 */
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	/**
	 * @return the projectType
	 */
	public String getProjectType() {
		return projectType;
	}
	/**
	 * @param projectType the projectType to set
	 */
	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

}
