/*===================================================================
 * ファイル名 : PpfsLldSR.java
 * 概要説明   : リース・レンタル情報照会_契約、物品共通検索結果条件
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-25 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.lld;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString(callSuper = true)
public class PpfsLldSR extends PpfsLld {
	private static final long serialVersionUID = 1L;



	/*******  契約情報   ********/


	/*******  物品情報   ********/
	private String holSectionNameLA;	//	保有部署（一覧表示用）

	/*******  共通   ********/
	private String stymdL; // 開始日
	private String stymdLF; // 開始日(フォーマット)
	private String manryoymdL; // 満了日
	private String manryoymdLF; // 満了日(フォーマット)
	private Integer kikanL; // 期間
	private Long laryoTotalL; // 契約金額
	private Long ikkailaryoL; // 均等金額
	private String kaiykymdL; // 解約日
	private String kaiykymdLF; // 解約日(フォーマット)
	private String leaseRentalHantei; // リースレンタル判定

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		super.readExternal(input);

		/*******  契約情報   ********/

		/*******  物品情報   ********/
		holSectionNameLA = (String)input.readObject();

		/*******  共通   ********/
		stymdL = (String)input.readObject();
		stymdLF = (String)input.readObject();
		manryoymdL = (String)input.readObject();
		manryoymdLF = (String)input.readObject();
		kikanL = Function.getExternalInteger(input.readObject());
		laryoTotalL = Function.getExternalLong(input.readObject());
		ikkailaryoL = Function.getExternalLong(input.readObject());
		kaiykymdL = (String)input.readObject();
		kaiykymdLF = (String)input.readObject();
		leaseRentalHantei = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		super.writeExternal(output);

		/*******  契約情報   ********/

		/*******  物品情報   ********/
		output.writeObject(holSectionNameLA);

		/*******  共通   ********/
		output.writeObject(stymdL);
		output.writeObject(stymdLF);
		output.writeObject(manryoymdL);
		output.writeObject(manryoymdLF);
		output.writeObject(kikanL);
		output.writeObject(laryoTotalL);
		output.writeObject(ikkailaryoL);
		output.writeObject(kaiykymdL);
		output.writeObject(kaiykymdLF);
		output.writeObject(leaseRentalHantei);

	}

	/**
	 * @return the stymdLF
	 */
	public String getStymdLF() {
		return stymdLF;
	}

	/**
	 * @param stymdLF the stymdLF to set
	 */
	public void setStymdLF(String stymdLF) {
		this.stymdLF = stymdLF;
	}

	/**
	 * @return the manryoymdLF
	 */
	public String getManryoymdLF() {
		return manryoymdLF;
	}

	/**
	 * @param manryoymdLF the manryoymdLF to set
	 */
	public void setManryoymdLF(String manryoymdLF) {
		this.manryoymdLF = manryoymdLF;
	}

	/**
	 * @return the kikanL
	 */
	public Integer getKikanL() {
		return kikanL;
	}

	/**
	 * @param kikanL the kikanL to set
	 */
	public void setKikanL(Integer kikanL) {
		this.kikanL = kikanL;
	}

	/**
	 * @return the stymdL
	 */
	public String getStymdL() {
		return stymdL;
	}

	/**
	 * @param stymdL the stymdL to set
	 */
	public void setStymdL(String stymdL) {
		this.stymdL = stymdL;
	}

	/**
	 * @return the manryoymdL
	 */
	public String getManryoymdL() {
		return manryoymdL;
	}

	/**
	 * @param manryoymdL the manryoymdL to set
	 */
	public void setManryoymdL(String manryoymdL) {
		this.manryoymdL = manryoymdL;
	}

	public Long getLaryoTotalL() {
		return laryoTotalL;
	}

	public void setLaryoTotalL(Long laryoTotalL) {
		this.laryoTotalL = laryoTotalL;
	}

	public Long getIkkailaryoL() {
		return ikkailaryoL;
	}

	public void setIkkailaryoL(Long ikkailaryoL) {
		this.ikkailaryoL = ikkailaryoL;
	}

	public String getHolSectionNameLA() {
		return holSectionNameLA;
	}

	public void setHolSectionNameLA(String holSectionNameLA) {
		this.holSectionNameLA = holSectionNameLA;
	}

	/**
	 * @return the kaiykymdL
	 */
	public String getKaiykymdL() {
		return kaiykymdL;
	}

	/**
	 * @param kaiykymdL the kaiykymdL to set
	 */
	public void setKaiykymdL(String kaiykymdL) {
		this.kaiykymdL = kaiykymdL;
	}

	/**
	 * @return the kaiykymdLF
	 */
	public String getKaiykymdLF() {
		return kaiykymdLF;
	}

	/**
	 * @param kaiykymdLF the kaiykymdLF to set
	 */
	public void setKaiykymdLF(String kaiykymdLF) {
		this.kaiykymdLF = kaiykymdLF;
	}

	/**
	 * @return the leaseRentalHantei
	 */
	public String getLeaseRentalHantei() {
		return leaseRentalHantei;
	}

	/**
	 * @param leaseRentalHantei the leaseRentalHantei to set
	 */
	public void setLeaseRentalHantei(String leaseRentalHantei) {
		this.leaseRentalHantei = leaseRentalHantei;
	}



}
