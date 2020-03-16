/*===================================================================
 * ファイル名 : LineData.java
 * 概要説明   : 行データのスーパークラス
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-24 1.0  リヨン 崔     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;

import lombok.ToString;

@ToString
public class LineData implements Serializable {
	private static final long serialVersionUID = 1L;

//	private Integer lineSeq; // 明細行番号

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
//		lineSeq = Function.getExternalInteger(input.readObject());
	}

	public void writeExternal(ObjectOutput output) throws IOException {
//		output.writeObject(lineSeq);
	}

//	/**
//	 * @return the no
//	 */
//	public Integer getNo() {
//		return lineSeq;
//	}
//
//	/**
//	 * @param no the no to set
//	 */
//	public void setNo(Integer no) {
//		this.lineSeq = no;
//	}
}
