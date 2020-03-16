/*===================================================================
 * ファイル名 : PpfsUnUpd.java
 * 概要説明   : Ppfs未承認データ一覧取得
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-11-13 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.fld;

import lombok.ToString;

@ToString
public class PpfsUnUpd  {
	// private static final long serialVersionUID = 1L;

	private String kaishacd; // 会社コード
	private String shorikbn; // 処理区分
	private String kihonnm; // 処理区分名称
	private String shoninkbn; // 承認区分
	private String koyuno; // 固有番号
	private String oya; // 資産番号・親
	private String eda; // 資産番号・枝
	private String kykno; // 契約番号
	private String senkoentrykbn; // 先行登録区分
	private String karientrykbn; // 仮登録区分
	/**
	 * kaishacdを取得します。
	 * @return kaishacd
	 */
	public String getKaishacd() {
		return kaishacd;
	}
	/**
	 * kaishacd
	 * @param kaishacdを設定します。
	 */
	public void setKaishacd(String kaishacd) {
		this.kaishacd = kaishacd;
	}
	/**
	 * shorikbnを取得します。
	 * @return shorikbn
	 */
	public String getShorikbn() {
		return shorikbn;
	}
	/**
	 * shorikbn
	 * @param shorikbnを設定します。
	 */
	public void setShorikbn(String shorikbn) {
		this.shorikbn = shorikbn;
	}
	/**
	 * kihonnmを取得します。
	 * @return kihonnm
	 */
	public String getKihonnm() {
		return kihonnm;
	}
	/**
	 * kihonnm
	 * @param kihonnmを設定します。
	 */
	public void setKihonnm(String kihonnm) {
		this.kihonnm = kihonnm;
	}
	/**
	 * shoninkbnを取得します。
	 * @return shoninkbn
	 */
	public String getShoninkbn() {
		return shoninkbn;
	}
	/**
	 * shoninkbn
	 * @param shoninkbnを設定します。
	 */
	public void setShoninkbn(String shoninkbn) {
		this.shoninkbn = shoninkbn;
	}
	/**
	 * koyunoを取得します。
	 * @return koyuno
	 */
	public String getKoyuno() {
		return koyuno;
	}
	/**
	 * koyuno
	 * @param koyunoを設定します。
	 */
	public void setKoyuno(String koyuno) {
		this.koyuno = koyuno;
	}
	/**
	 * oyaを取得します。
	 * @return oya
	 */
	public String getOya() {
		return oya;
	}
	/**
	 * oya
	 * @param oyaを設定します。
	 */
	public void setOya(String oya) {
		this.oya = oya;
	}
	/**
	 * edaを取得します。
	 * @return eda
	 */
	public String getEda() {
		return eda;
	}
	/**
	 * eda
	 * @param edaを設定します。
	 */
	public void setEda(String eda) {
		this.eda = eda;
	}
	/**
	 * kyknoを取得します。
	 * @return kykno
	 */
	public String getKykno() {
		return kykno;
	}
	/**
	 * kykno
	 * @param kyknoを設定します。
	 */
	public void setKykno(String kykno) {
		this.kykno = kykno;
	}
	/**
	 * senkoentrykbnを取得します。
	 * @return senkoentrykbn
	 */
	public String getSenkoentrykbn() {
		return senkoentrykbn;
	}
	/**
	 * senkoentrykbn
	 * @param senkoentrykbnを設定します。
	 */
	public void setSenkoentrykbn(String senkoentrykbn) {
		this.senkoentrykbn = senkoentrykbn;
	}
	/**
	 * karientrykbnを取得します。
	 * @return karientrykbn
	 */
	public String getKarientrykbn() {
		return karientrykbn;
	}
	/**
	 * karientrykbn
	 * @param karientrykbnを設定します。
	 */
	public void setKarientrykbn(String karientrykbn) {
		this.karientrykbn = karientrykbn;
	}



}
