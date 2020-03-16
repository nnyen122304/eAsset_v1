/*===================================================================
 * ファイル名 : InvSumSR.java
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

import jp.co.ctcg.easset.rest.MappingUtils;
import lombok.ToString;

@ToString(callSuper = true)
public class InvSumSR extends InvSum {
	private static final long serialVersionUID = 1L;


	private String astNumPluar;							// 登録申請番号
	private String contractNumPluar;							// 登録申請番号
	private String invStaffFlag;		//	棚卸部署結合フラグ

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		super.readExternal(input);
		invStaffFlag = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		super.writeExternal(output);
		output.writeObject(invStaffFlag);

	}

	/**
	 * astNumPluarを取得します。
	 * @return astNumPluar
	 */
	public String getAstNumPluar() {
		return astNumPluar;
	}

	/**
	 * astNumPluar
	 * @param astNumPluarを設定します。
	 */
	public void setAstNumPluar(String astNumPluar) {
		this.astNumPluar = astNumPluar;
	}

	/**
	 * contractNumPluarを取得します。
	 * @return contractNumPluar
	 */
	public String getContractNumPluar() {
		return contractNumPluar;
	}

	/**
	 * contractNumPluar
	 * @param contractNumPluarを設定します。
	 */
	public void setContractNumPluar(String contractNumPluar) {
		this.contractNumPluar = contractNumPluar;
	}

	/**
	 * invStaffFlagを取得します。
	 * @return invStaffFlag
	 */
	public String getInvStaffFlag() {
		return invStaffFlag;
	}

	/**
	 * invStaffFlag
	 * @param invStaffFlagを設定します。
	 */
	public void setInvStaffFlag(String invStaffFlag) {
		this.invStaffFlag = invStaffFlag;
	}

  
	/*
	* 文字列からクラスに変換メソッド
	* @param json 変換json文字列
	* @return InvSumSR.class 該当クラス
	*/
	public static InvSumSR fromString(String json) {
		return MappingUtils.fromJson(json, InvSumSR.class);
	}
}
