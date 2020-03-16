/*===================================================================
 * ファイル名 : PpfsFldSupport.java
 * 概要説明   : 補助台帳
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-10-22 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.fld;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class PpfsFldSupport implements Externalizable {
	private static final long serialVersionUID = 1L;

	private Long koyuno; // 固有番号
	private String hojoshubetsupk; // 補助台帳種別キー
	private Integer meisaino; // 明細番号
	private String kaishacd; // 会社コード
	private String hojoshubetsucd; // 補助台帳種別コード
	private String hojosparec1; // 補助項目1
	private String hojosparec2; // 補助項目2
	private String hojosparec3; // 補助項目3
	private String hojosparec4; // 補助項目4
	private String hojosparec5; // 補助項目5
	private String hojosparec6; // 補助項目6
	private String hojosparec7; // 補助項目7
	private String hojosparec8; // 補助項目8
	private String hojosparec9; // 補助項目9
	private String hojosparec10; // 補助項目10
	private String hojosparec11; // 補助項目11
	private String hojosparec12; // 補助項目12
	private String hojosparec13; // 補助項目13
	private String hojosparec14; // 補助項目14
	private String hojosparec15; // 補助項目15
	private String hojosparec16; // 補助項目16
	private String hojosparec17; // 補助項目17
	private String hojosparec18; // 補助項目18
	private String hojosparec19; // 補助項目19
	private String hojosparec20; // 補助項目20
	private String syskbn; // システム区分
	private String kihonsparec1; // 基本文字項目1
	private String kihonsparec2; // 基本文字項目2
	private String kihonsparec3; // 基本文字項目3
	private String kihonsparec4; // 基本文字項目4
	private String kihonsparec5; // 基本文字項目5
	private String kihonsparec6; // 基本文字項目6
	private String kihonsparec7; // 基本文字項目7
	private String kihonsparec8; // 基本文字項目8
	private String kihonsparec9; // 基本文字項目9
	private String kihonsparec10; // 基本文字項目10
	private Long kihonsparen1; // 基本数値項目1
	private Long kihonsparen2; // 基本数値項目2
	private Long kihonsparen3; // 基本数値項目3
	private Long kihonsparen4; // 基本数値項目4
	private Long kihonsparen5; // 基本数値項目5
	private Long kihonsparen6; // 基本数値項目6
	private Long kihonsparen7; // 基本数値項目7
	private Long kihonsparen8; // 基本数値項目8
	private Long kihonsparen9; // 基本数値項目9
	private Long kihonsparen10; // 基本数値項目10
	private String addonsparec1; // アドオン文字項目1
	private String addonsparec2; // アドオン文字項目2
	private String addonsparec3; // アドオン文字項目3
	private String addonsparec4; // アドオン文字項目4
	private String addonsparec5; // アドオン文字項目5
	private String addonsparec6; // アドオン文字項目6
	private String addonsparec7; // アドオン文字項目7
	private String addonsparec8; // アドオン文字項目8
	private String addonsparec9; // アドオン文字項目9
	private String addonsparec10; // アドオン文字項目10
	private Long addonsparen1; // アドオン数値項目1
	private Long addonsparen2; // アドオン数値項目2
	private Long addonsparen3; // アドオン数値項目3
	private Long addonsparen4; // アドオン数値項目4
	private Long addonsparen5; // アドオン数値項目5
	private Long addonsparen6; // アドオン数値項目6
	private Long addonsparen7; // アドオン数値項目7
	private Long addonsparen8; // アドオン数値項目8
	private Long addonsparen9; // アドオン数値項目9
	private Long addonsparen10; // アドオン数値項目10
	private Long updkaisu; // 更新回数
	private String updkaishacd; // 更新会社コード
	private String updid; // 更新者ID
	private String updymd; // 更新年月日
	private String updtime; // 更新時間

	//	経費負担部課履歴
	private String costSectionName; // 経費負担部課
	private String periodFrom; // 会計期間(自)
	private String periodTo; // 会計期間(至)

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		koyuno = Function.getExternalLong(input.readObject());
		hojoshubetsupk = (String)input.readObject();
		meisaino = Function.getExternalInteger(input.readObject());
		kaishacd = (String)input.readObject();
		hojoshubetsucd = (String)input.readObject();
		hojosparec1 = (String)input.readObject();
		hojosparec2 = (String)input.readObject();
		hojosparec3 = (String)input.readObject();
		hojosparec4 = (String)input.readObject();
		hojosparec5 = (String)input.readObject();
		hojosparec6 = (String)input.readObject();
		hojosparec7 = (String)input.readObject();
		hojosparec8 = (String)input.readObject();
		hojosparec9 = (String)input.readObject();
		hojosparec10 = (String)input.readObject();
		hojosparec11 = (String)input.readObject();
		hojosparec12 = (String)input.readObject();
		hojosparec13 = (String)input.readObject();
		hojosparec14 = (String)input.readObject();
		hojosparec15 = (String)input.readObject();
		hojosparec16 = (String)input.readObject();
		hojosparec17 = (String)input.readObject();
		hojosparec18 = (String)input.readObject();
		hojosparec19 = (String)input.readObject();
		hojosparec20 = (String)input.readObject();
		syskbn = (String)input.readObject();
		kihonsparec1 = (String)input.readObject();
		kihonsparec2 = (String)input.readObject();
		kihonsparec3 = (String)input.readObject();
		kihonsparec4 = (String)input.readObject();
		kihonsparec5 = (String)input.readObject();
		kihonsparec6 = (String)input.readObject();
		kihonsparec7 = (String)input.readObject();
		kihonsparec8 = (String)input.readObject();
		kihonsparec9 = (String)input.readObject();
		kihonsparec10 = (String)input.readObject();
		kihonsparen1 = Function.getExternalLong(input.readObject());
		kihonsparen2 = Function.getExternalLong(input.readObject());
		kihonsparen3 = Function.getExternalLong(input.readObject());
		kihonsparen4 = Function.getExternalLong(input.readObject());
		kihonsparen5 = Function.getExternalLong(input.readObject());
		kihonsparen6 = Function.getExternalLong(input.readObject());
		kihonsparen7 = Function.getExternalLong(input.readObject());
		kihonsparen8 = Function.getExternalLong(input.readObject());
		kihonsparen9 = Function.getExternalLong(input.readObject());
		kihonsparen10 = Function.getExternalLong(input.readObject());
		addonsparec1 = (String)input.readObject();
		addonsparec2 = (String)input.readObject();
		addonsparec3 = (String)input.readObject();
		addonsparec4 = (String)input.readObject();
		addonsparec5 = (String)input.readObject();
		addonsparec6 = (String)input.readObject();
		addonsparec7 = (String)input.readObject();
		addonsparec8 = (String)input.readObject();
		addonsparec9 = (String)input.readObject();
		addonsparec10 = (String)input.readObject();
		addonsparen1 = Function.getExternalLong(input.readObject());
		addonsparen2 = Function.getExternalLong(input.readObject());
		addonsparen3 = Function.getExternalLong(input.readObject());
		addonsparen4 = Function.getExternalLong(input.readObject());
		addonsparen5 = Function.getExternalLong(input.readObject());
		addonsparen6 = Function.getExternalLong(input.readObject());
		addonsparen7 = Function.getExternalLong(input.readObject());
		addonsparen8 = Function.getExternalLong(input.readObject());
		addonsparen9 = Function.getExternalLong(input.readObject());
		addonsparen10 = Function.getExternalLong(input.readObject());
		updkaisu = Function.getExternalLong(input.readObject());
		updkaishacd = (String)input.readObject();
		updid = (String)input.readObject();
		updymd = (String)input.readObject();
		updtime = (String)input.readObject();

		//	経費負担部課
		costSectionName = (String)input.readObject();
		periodFrom = (String)input.readObject();
		periodTo = (String)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		output.writeObject(koyuno);
		output.writeObject(hojoshubetsupk);
		output.writeObject(meisaino);
		output.writeObject(kaishacd);
		output.writeObject(hojoshubetsucd);
		output.writeObject(hojosparec1);
		output.writeObject(hojosparec2);
		output.writeObject(hojosparec3);
		output.writeObject(hojosparec4);
		output.writeObject(hojosparec5);
		output.writeObject(hojosparec6);
		output.writeObject(hojosparec7);
		output.writeObject(hojosparec8);
		output.writeObject(hojosparec9);
		output.writeObject(hojosparec10);
		output.writeObject(hojosparec11);
		output.writeObject(hojosparec12);
		output.writeObject(hojosparec13);
		output.writeObject(hojosparec14);
		output.writeObject(hojosparec15);
		output.writeObject(hojosparec16);
		output.writeObject(hojosparec17);
		output.writeObject(hojosparec18);
		output.writeObject(hojosparec19);
		output.writeObject(hojosparec20);
		output.writeObject(syskbn);
		output.writeObject(kihonsparec1);
		output.writeObject(kihonsparec2);
		output.writeObject(kihonsparec3);
		output.writeObject(kihonsparec4);
		output.writeObject(kihonsparec5);
		output.writeObject(kihonsparec6);
		output.writeObject(kihonsparec7);
		output.writeObject(kihonsparec8);
		output.writeObject(kihonsparec9);
		output.writeObject(kihonsparec10);
		output.writeObject(kihonsparen1);
		output.writeObject(kihonsparen2);
		output.writeObject(kihonsparen3);
		output.writeObject(kihonsparen4);
		output.writeObject(kihonsparen5);
		output.writeObject(kihonsparen6);
		output.writeObject(kihonsparen7);
		output.writeObject(kihonsparen8);
		output.writeObject(kihonsparen9);
		output.writeObject(kihonsparen10);
		output.writeObject(addonsparec1);
		output.writeObject(addonsparec2);
		output.writeObject(addonsparec3);
		output.writeObject(addonsparec4);
		output.writeObject(addonsparec5);
		output.writeObject(addonsparec6);
		output.writeObject(addonsparec7);
		output.writeObject(addonsparec8);
		output.writeObject(addonsparec9);
		output.writeObject(addonsparec10);
		output.writeObject(addonsparen1);
		output.writeObject(addonsparen2);
		output.writeObject(addonsparen3);
		output.writeObject(addonsparen4);
		output.writeObject(addonsparen5);
		output.writeObject(addonsparen6);
		output.writeObject(addonsparen7);
		output.writeObject(addonsparen8);
		output.writeObject(addonsparen9);
		output.writeObject(addonsparen10);
		output.writeObject(updkaisu);
		output.writeObject(updkaishacd);
		output.writeObject(updid);
		output.writeObject(updymd);
		output.writeObject(updtime);

		//	経費負担部課
		output.writeObject(costSectionName);
		output.writeObject(periodFrom);
		output.writeObject(periodTo);

	}

	/**
	 * koyunoを取得します。
	 * @return koyuno
	 */
	public Long getKoyuno() {
		return koyuno;
	}

	/**
	 * koyuno
	 * @param koyunoを設定します。
	 */
	public void setKoyuno(Long koyuno) {
		this.koyuno = koyuno;
	}

	/**
	 * hojoshubetsupkを取得します。
	 * @return hojoshubetsupk
	 */
	public String getHojoshubetsupk() {
		return hojoshubetsupk;
	}

	/**
	 * hojoshubetsupk
	 * @param hojoshubetsupkを設定します。
	 */
	public void setHojoshubetsupk(String hojoshubetsupk) {
		this.hojoshubetsupk = hojoshubetsupk;
	}

	/**
	 * meisainoを取得します。
	 * @return meisaino
	 */
	public Integer getMeisaino() {
		return meisaino;
	}

	/**
	 * meisaino
	 * @param meisainoを設定します。
	 */
	public void setMeisaino(Integer meisaino) {
		this.meisaino = meisaino;
	}

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
	 * hojoshubetsucdを取得します。
	 * @return hojoshubetsucd
	 */
	public String getHojoshubetsucd() {
		return hojoshubetsucd;
	}

	/**
	 * hojoshubetsucd
	 * @param hojoshubetsucdを設定します。
	 */
	public void setHojoshubetsucd(String hojoshubetsucd) {
		this.hojoshubetsucd = hojoshubetsucd;
	}

	/**
	 * hojosparec1を取得します。
	 * @return hojosparec1
	 */
	public String getHojosparec1() {
		return hojosparec1;
	}

	/**
	 * hojosparec1
	 * @param hojosparec1を設定します。
	 */
	public void setHojosparec1(String hojosparec1) {
		this.hojosparec1 = hojosparec1;
	}

	/**
	 * hojosparec2を取得します。
	 * @return hojosparec2
	 */
	public String getHojosparec2() {
		return hojosparec2;
	}

	/**
	 * hojosparec2
	 * @param hojosparec2を設定します。
	 */
	public void setHojosparec2(String hojosparec2) {
		this.hojosparec2 = hojosparec2;
	}

	/**
	 * hojosparec3を取得します。
	 * @return hojosparec3
	 */
	public String getHojosparec3() {
		return hojosparec3;
	}

	/**
	 * hojosparec3
	 * @param hojosparec3を設定します。
	 */
	public void setHojosparec3(String hojosparec3) {
		this.hojosparec3 = hojosparec3;
	}

	/**
	 * hojosparec4を取得します。
	 * @return hojosparec4
	 */
	public String getHojosparec4() {
		return hojosparec4;
	}

	/**
	 * hojosparec4
	 * @param hojosparec4を設定します。
	 */
	public void setHojosparec4(String hojosparec4) {
		this.hojosparec4 = hojosparec4;
	}

	/**
	 * hojosparec5を取得します。
	 * @return hojosparec5
	 */
	public String getHojosparec5() {
		return hojosparec5;
	}

	/**
	 * hojosparec5
	 * @param hojosparec5を設定します。
	 */
	public void setHojosparec5(String hojosparec5) {
		this.hojosparec5 = hojosparec5;
	}

	/**
	 * hojosparec6を取得します。
	 * @return hojosparec6
	 */
	public String getHojosparec6() {
		return hojosparec6;
	}

	/**
	 * hojosparec6
	 * @param hojosparec6を設定します。
	 */
	public void setHojosparec6(String hojosparec6) {
		this.hojosparec6 = hojosparec6;
	}

	/**
	 * hojosparec7を取得します。
	 * @return hojosparec7
	 */
	public String getHojosparec7() {
		return hojosparec7;
	}

	/**
	 * hojosparec7
	 * @param hojosparec7を設定します。
	 */
	public void setHojosparec7(String hojosparec7) {
		this.hojosparec7 = hojosparec7;
	}

	/**
	 * hojosparec8を取得します。
	 * @return hojosparec8
	 */
	public String getHojosparec8() {
		return hojosparec8;
	}

	/**
	 * hojosparec8
	 * @param hojosparec8を設定します。
	 */
	public void setHojosparec8(String hojosparec8) {
		this.hojosparec8 = hojosparec8;
	}

	/**
	 * hojosparec9を取得します。
	 * @return hojosparec9
	 */
	public String getHojosparec9() {
		return hojosparec9;
	}

	/**
	 * hojosparec9
	 * @param hojosparec9を設定します。
	 */
	public void setHojosparec9(String hojosparec9) {
		this.hojosparec9 = hojosparec9;
	}

	/**
	 * hojosparec10を取得します。
	 * @return hojosparec10
	 */
	public String getHojosparec10() {
		return hojosparec10;
	}

	/**
	 * hojosparec10
	 * @param hojosparec10を設定します。
	 */
	public void setHojosparec10(String hojosparec10) {
		this.hojosparec10 = hojosparec10;
	}

	/**
	 * hojosparec11を取得します。
	 * @return hojosparec11
	 */
	public String getHojosparec11() {
		return hojosparec11;
	}

	/**
	 * hojosparec11
	 * @param hojosparec11を設定します。
	 */
	public void setHojosparec11(String hojosparec11) {
		this.hojosparec11 = hojosparec11;
	}

	/**
	 * hojosparec12を取得します。
	 * @return hojosparec12
	 */
	public String getHojosparec12() {
		return hojosparec12;
	}

	/**
	 * hojosparec12
	 * @param hojosparec12を設定します。
	 */
	public void setHojosparec12(String hojosparec12) {
		this.hojosparec12 = hojosparec12;
	}

	/**
	 * hojosparec13を取得します。
	 * @return hojosparec13
	 */
	public String getHojosparec13() {
		return hojosparec13;
	}

	/**
	 * hojosparec13
	 * @param hojosparec13を設定します。
	 */
	public void setHojosparec13(String hojosparec13) {
		this.hojosparec13 = hojosparec13;
	}

	/**
	 * hojosparec14を取得します。
	 * @return hojosparec14
	 */
	public String getHojosparec14() {
		return hojosparec14;
	}

	/**
	 * hojosparec14
	 * @param hojosparec14を設定します。
	 */
	public void setHojosparec14(String hojosparec14) {
		this.hojosparec14 = hojosparec14;
	}

	/**
	 * hojosparec15を取得します。
	 * @return hojosparec15
	 */
	public String getHojosparec15() {
		return hojosparec15;
	}

	/**
	 * hojosparec15
	 * @param hojosparec15を設定します。
	 */
	public void setHojosparec15(String hojosparec15) {
		this.hojosparec15 = hojosparec15;
	}

	/**
	 * hojosparec16を取得します。
	 * @return hojosparec16
	 */
	public String getHojosparec16() {
		return hojosparec16;
	}

	/**
	 * hojosparec16
	 * @param hojosparec16を設定します。
	 */
	public void setHojosparec16(String hojosparec16) {
		this.hojosparec16 = hojosparec16;
	}

	/**
	 * hojosparec17を取得します。
	 * @return hojosparec17
	 */
	public String getHojosparec17() {
		return hojosparec17;
	}

	/**
	 * hojosparec17
	 * @param hojosparec17を設定します。
	 */
	public void setHojosparec17(String hojosparec17) {
		this.hojosparec17 = hojosparec17;
	}

	/**
	 * hojosparec18を取得します。
	 * @return hojosparec18
	 */
	public String getHojosparec18() {
		return hojosparec18;
	}

	/**
	 * hojosparec18
	 * @param hojosparec18を設定します。
	 */
	public void setHojosparec18(String hojosparec18) {
		this.hojosparec18 = hojosparec18;
	}

	/**
	 * hojosparec19を取得します。
	 * @return hojosparec19
	 */
	public String getHojosparec19() {
		return hojosparec19;
	}

	/**
	 * hojosparec19
	 * @param hojosparec19を設定します。
	 */
	public void setHojosparec19(String hojosparec19) {
		this.hojosparec19 = hojosparec19;
	}

	/**
	 * hojosparec20を取得します。
	 * @return hojosparec20
	 */
	public String getHojosparec20() {
		return hojosparec20;
	}

	/**
	 * hojosparec20
	 * @param hojosparec20を設定します。
	 */
	public void setHojosparec20(String hojosparec20) {
		this.hojosparec20 = hojosparec20;
	}

	/**
	 * syskbnを取得します。
	 * @return syskbn
	 */
	public String getSyskbn() {
		return syskbn;
	}

	/**
	 * syskbn
	 * @param syskbnを設定します。
	 */
	public void setSyskbn(String syskbn) {
		this.syskbn = syskbn;
	}

	/**
	 * kihonsparec1を取得します。
	 * @return kihonsparec1
	 */
	public String getKihonsparec1() {
		return kihonsparec1;
	}

	/**
	 * kihonsparec1
	 * @param kihonsparec1を設定します。
	 */
	public void setKihonsparec1(String kihonsparec1) {
		this.kihonsparec1 = kihonsparec1;
	}

	/**
	 * kihonsparec2を取得します。
	 * @return kihonsparec2
	 */
	public String getKihonsparec2() {
		return kihonsparec2;
	}

	/**
	 * kihonsparec2
	 * @param kihonsparec2を設定します。
	 */
	public void setKihonsparec2(String kihonsparec2) {
		this.kihonsparec2 = kihonsparec2;
	}

	/**
	 * kihonsparec3を取得します。
	 * @return kihonsparec3
	 */
	public String getKihonsparec3() {
		return kihonsparec3;
	}

	/**
	 * kihonsparec3
	 * @param kihonsparec3を設定します。
	 */
	public void setKihonsparec3(String kihonsparec3) {
		this.kihonsparec3 = kihonsparec3;
	}

	/**
	 * kihonsparec4を取得します。
	 * @return kihonsparec4
	 */
	public String getKihonsparec4() {
		return kihonsparec4;
	}

	/**
	 * kihonsparec4
	 * @param kihonsparec4を設定します。
	 */
	public void setKihonsparec4(String kihonsparec4) {
		this.kihonsparec4 = kihonsparec4;
	}

	/**
	 * kihonsparec5を取得します。
	 * @return kihonsparec5
	 */
	public String getKihonsparec5() {
		return kihonsparec5;
	}

	/**
	 * kihonsparec5
	 * @param kihonsparec5を設定します。
	 */
	public void setKihonsparec5(String kihonsparec5) {
		this.kihonsparec5 = kihonsparec5;
	}

	/**
	 * kihonsparec6を取得します。
	 * @return kihonsparec6
	 */
	public String getKihonsparec6() {
		return kihonsparec6;
	}

	/**
	 * kihonsparec6
	 * @param kihonsparec6を設定します。
	 */
	public void setKihonsparec6(String kihonsparec6) {
		this.kihonsparec6 = kihonsparec6;
	}

	/**
	 * kihonsparec7を取得します。
	 * @return kihonsparec7
	 */
	public String getKihonsparec7() {
		return kihonsparec7;
	}

	/**
	 * kihonsparec7
	 * @param kihonsparec7を設定します。
	 */
	public void setKihonsparec7(String kihonsparec7) {
		this.kihonsparec7 = kihonsparec7;
	}

	/**
	 * kihonsparec8を取得します。
	 * @return kihonsparec8
	 */
	public String getKihonsparec8() {
		return kihonsparec8;
	}

	/**
	 * kihonsparec8
	 * @param kihonsparec8を設定します。
	 */
	public void setKihonsparec8(String kihonsparec8) {
		this.kihonsparec8 = kihonsparec8;
	}

	/**
	 * kihonsparec9を取得します。
	 * @return kihonsparec9
	 */
	public String getKihonsparec9() {
		return kihonsparec9;
	}

	/**
	 * kihonsparec9
	 * @param kihonsparec9を設定します。
	 */
	public void setKihonsparec9(String kihonsparec9) {
		this.kihonsparec9 = kihonsparec9;
	}

	/**
	 * kihonsparec10を取得します。
	 * @return kihonsparec10
	 */
	public String getKihonsparec10() {
		return kihonsparec10;
	}

	/**
	 * kihonsparec10
	 * @param kihonsparec10を設定します。
	 */
	public void setKihonsparec10(String kihonsparec10) {
		this.kihonsparec10 = kihonsparec10;
	}

	/**
	 * kihonsparen1を取得します。
	 * @return kihonsparen1
	 */
	public Long getKihonsparen1() {
		return kihonsparen1;
	}

	/**
	 * kihonsparen1
	 * @param kihonsparen1を設定します。
	 */
	public void setKihonsparen1(Long kihonsparen1) {
		this.kihonsparen1 = kihonsparen1;
	}

	/**
	 * kihonsparen2を取得します。
	 * @return kihonsparen2
	 */
	public Long getKihonsparen2() {
		return kihonsparen2;
	}

	/**
	 * kihonsparen2
	 * @param kihonsparen2を設定します。
	 */
	public void setKihonsparen2(Long kihonsparen2) {
		this.kihonsparen2 = kihonsparen2;
	}

	/**
	 * kihonsparen3を取得します。
	 * @return kihonsparen3
	 */
	public Long getKihonsparen3() {
		return kihonsparen3;
	}

	/**
	 * kihonsparen3
	 * @param kihonsparen3を設定します。
	 */
	public void setKihonsparen3(Long kihonsparen3) {
		this.kihonsparen3 = kihonsparen3;
	}

	/**
	 * kihonsparen4を取得します。
	 * @return kihonsparen4
	 */
	public Long getKihonsparen4() {
		return kihonsparen4;
	}

	/**
	 * kihonsparen4
	 * @param kihonsparen4を設定します。
	 */
	public void setKihonsparen4(Long kihonsparen4) {
		this.kihonsparen4 = kihonsparen4;
	}

	/**
	 * kihonsparen5を取得します。
	 * @return kihonsparen5
	 */
	public Long getKihonsparen5() {
		return kihonsparen5;
	}

	/**
	 * kihonsparen5
	 * @param kihonsparen5を設定します。
	 */
	public void setKihonsparen5(Long kihonsparen5) {
		this.kihonsparen5 = kihonsparen5;
	}

	/**
	 * kihonsparen6を取得します。
	 * @return kihonsparen6
	 */
	public Long getKihonsparen6() {
		return kihonsparen6;
	}

	/**
	 * kihonsparen6
	 * @param kihonsparen6を設定します。
	 */
	public void setKihonsparen6(Long kihonsparen6) {
		this.kihonsparen6 = kihonsparen6;
	}

	/**
	 * kihonsparen7を取得します。
	 * @return kihonsparen7
	 */
	public Long getKihonsparen7() {
		return kihonsparen7;
	}

	/**
	 * kihonsparen7
	 * @param kihonsparen7を設定します。
	 */
	public void setKihonsparen7(Long kihonsparen7) {
		this.kihonsparen7 = kihonsparen7;
	}

	/**
	 * kihonsparen8を取得します。
	 * @return kihonsparen8
	 */
	public Long getKihonsparen8() {
		return kihonsparen8;
	}

	/**
	 * kihonsparen8
	 * @param kihonsparen8を設定します。
	 */
	public void setKihonsparen8(Long kihonsparen8) {
		this.kihonsparen8 = kihonsparen8;
	}

	/**
	 * kihonsparen9を取得します。
	 * @return kihonsparen9
	 */
	public Long getKihonsparen9() {
		return kihonsparen9;
	}

	/**
	 * kihonsparen9
	 * @param kihonsparen9を設定します。
	 */
	public void setKihonsparen9(Long kihonsparen9) {
		this.kihonsparen9 = kihonsparen9;
	}

	/**
	 * kihonsparen10を取得します。
	 * @return kihonsparen10
	 */
	public Long getKihonsparen10() {
		return kihonsparen10;
	}

	/**
	 * kihonsparen10
	 * @param kihonsparen10を設定します。
	 */
	public void setKihonsparen10(Long kihonsparen10) {
		this.kihonsparen10 = kihonsparen10;
	}

	/**
	 * addonsparec1を取得します。
	 * @return addonsparec1
	 */
	public String getAddonsparec1() {
		return addonsparec1;
	}

	/**
	 * addonsparec1
	 * @param addonsparec1を設定します。
	 */
	public void setAddonsparec1(String addonsparec1) {
		this.addonsparec1 = addonsparec1;
	}

	/**
	 * addonsparec2を取得します。
	 * @return addonsparec2
	 */
	public String getAddonsparec2() {
		return addonsparec2;
	}

	/**
	 * addonsparec2
	 * @param addonsparec2を設定します。
	 */
	public void setAddonsparec2(String addonsparec2) {
		this.addonsparec2 = addonsparec2;
	}

	/**
	 * addonsparec3を取得します。
	 * @return addonsparec3
	 */
	public String getAddonsparec3() {
		return addonsparec3;
	}

	/**
	 * addonsparec3
	 * @param addonsparec3を設定します。
	 */
	public void setAddonsparec3(String addonsparec3) {
		this.addonsparec3 = addonsparec3;
	}

	/**
	 * addonsparec4を取得します。
	 * @return addonsparec4
	 */
	public String getAddonsparec4() {
		return addonsparec4;
	}

	/**
	 * addonsparec4
	 * @param addonsparec4を設定します。
	 */
	public void setAddonsparec4(String addonsparec4) {
		this.addonsparec4 = addonsparec4;
	}

	/**
	 * addonsparec5を取得します。
	 * @return addonsparec5
	 */
	public String getAddonsparec5() {
		return addonsparec5;
	}

	/**
	 * addonsparec5
	 * @param addonsparec5を設定します。
	 */
	public void setAddonsparec5(String addonsparec5) {
		this.addonsparec5 = addonsparec5;
	}

	/**
	 * addonsparec6を取得します。
	 * @return addonsparec6
	 */
	public String getAddonsparec6() {
		return addonsparec6;
	}

	/**
	 * addonsparec6
	 * @param addonsparec6を設定します。
	 */
	public void setAddonsparec6(String addonsparec6) {
		this.addonsparec6 = addonsparec6;
	}

	/**
	 * addonsparec7を取得します。
	 * @return addonsparec7
	 */
	public String getAddonsparec7() {
		return addonsparec7;
	}

	/**
	 * addonsparec7
	 * @param addonsparec7を設定します。
	 */
	public void setAddonsparec7(String addonsparec7) {
		this.addonsparec7 = addonsparec7;
	}

	/**
	 * addonsparec8を取得します。
	 * @return addonsparec8
	 */
	public String getAddonsparec8() {
		return addonsparec8;
	}

	/**
	 * addonsparec8
	 * @param addonsparec8を設定します。
	 */
	public void setAddonsparec8(String addonsparec8) {
		this.addonsparec8 = addonsparec8;
	}

	/**
	 * addonsparec9を取得します。
	 * @return addonsparec9
	 */
	public String getAddonsparec9() {
		return addonsparec9;
	}

	/**
	 * addonsparec9
	 * @param addonsparec9を設定します。
	 */
	public void setAddonsparec9(String addonsparec9) {
		this.addonsparec9 = addonsparec9;
	}

	/**
	 * addonsparec10を取得します。
	 * @return addonsparec10
	 */
	public String getAddonsparec10() {
		return addonsparec10;
	}

	/**
	 * addonsparec10
	 * @param addonsparec10を設定します。
	 */
	public void setAddonsparec10(String addonsparec10) {
		this.addonsparec10 = addonsparec10;
	}

	/**
	 * addonsparen1を取得します。
	 * @return addonsparen1
	 */
	public Long getAddonsparen1() {
		return addonsparen1;
	}

	/**
	 * addonsparen1
	 * @param addonsparen1を設定します。
	 */
	public void setAddonsparen1(Long addonsparen1) {
		this.addonsparen1 = addonsparen1;
	}

	/**
	 * addonsparen2を取得します。
	 * @return addonsparen2
	 */
	public Long getAddonsparen2() {
		return addonsparen2;
	}

	/**
	 * addonsparen2
	 * @param addonsparen2を設定します。
	 */
	public void setAddonsparen2(Long addonsparen2) {
		this.addonsparen2 = addonsparen2;
	}

	/**
	 * addonsparen3を取得します。
	 * @return addonsparen3
	 */
	public Long getAddonsparen3() {
		return addonsparen3;
	}

	/**
	 * addonsparen3
	 * @param addonsparen3を設定します。
	 */
	public void setAddonsparen3(Long addonsparen3) {
		this.addonsparen3 = addonsparen3;
	}

	/**
	 * addonsparen4を取得します。
	 * @return addonsparen4
	 */
	public Long getAddonsparen4() {
		return addonsparen4;
	}

	/**
	 * addonsparen4
	 * @param addonsparen4を設定します。
	 */
	public void setAddonsparen4(Long addonsparen4) {
		this.addonsparen4 = addonsparen4;
	}

	/**
	 * addonsparen5を取得します。
	 * @return addonsparen5
	 */
	public Long getAddonsparen5() {
		return addonsparen5;
	}

	/**
	 * addonsparen5
	 * @param addonsparen5を設定します。
	 */
	public void setAddonsparen5(Long addonsparen5) {
		this.addonsparen5 = addonsparen5;
	}

	/**
	 * addonsparen6を取得します。
	 * @return addonsparen6
	 */
	public Long getAddonsparen6() {
		return addonsparen6;
	}

	/**
	 * addonsparen6
	 * @param addonsparen6を設定します。
	 */
	public void setAddonsparen6(Long addonsparen6) {
		this.addonsparen6 = addonsparen6;
	}

	/**
	 * addonsparen7を取得します。
	 * @return addonsparen7
	 */
	public Long getAddonsparen7() {
		return addonsparen7;
	}

	/**
	 * addonsparen7
	 * @param addonsparen7を設定します。
	 */
	public void setAddonsparen7(Long addonsparen7) {
		this.addonsparen7 = addonsparen7;
	}

	/**
	 * addonsparen8を取得します。
	 * @return addonsparen8
	 */
	public Long getAddonsparen8() {
		return addonsparen8;
	}

	/**
	 * addonsparen8
	 * @param addonsparen8を設定します。
	 */
	public void setAddonsparen8(Long addonsparen8) {
		this.addonsparen8 = addonsparen8;
	}

	/**
	 * addonsparen9を取得します。
	 * @return addonsparen9
	 */
	public Long getAddonsparen9() {
		return addonsparen9;
	}

	/**
	 * addonsparen9
	 * @param addonsparen9を設定します。
	 */
	public void setAddonsparen9(Long addonsparen9) {
		this.addonsparen9 = addonsparen9;
	}

	/**
	 * addonsparen10を取得します。
	 * @return addonsparen10
	 */
	public Long getAddonsparen10() {
		return addonsparen10;
	}

	/**
	 * addonsparen10
	 * @param addonsparen10を設定します。
	 */
	public void setAddonsparen10(Long addonsparen10) {
		this.addonsparen10 = addonsparen10;
	}

	/**
	 * updkaisuを取得します。
	 * @return updkaisu
	 */
	public Long getUpdkaisu() {
		return updkaisu;
	}

	/**
	 * updkaisu
	 * @param updkaisuを設定します。
	 */
	public void setUpdkaisu(Long updkaisu) {
		this.updkaisu = updkaisu;
	}

	/**
	 * updkaishacdを取得します。
	 * @return updkaishacd
	 */
	public String getUpdkaishacd() {
		return updkaishacd;
	}

	/**
	 * updkaishacd
	 * @param updkaishacdを設定します。
	 */
	public void setUpdkaishacd(String updkaishacd) {
		this.updkaishacd = updkaishacd;
	}

	/**
	 * updidを取得します。
	 * @return updid
	 */
	public String getUpdid() {
		return updid;
	}

	/**
	 * updid
	 * @param updidを設定します。
	 */
	public void setUpdid(String updid) {
		this.updid = updid;
	}

	/**
	 * updymdを取得します。
	 * @return updymd
	 */
	public String getUpdymd() {
		return updymd;
	}

	/**
	 * updymd
	 * @param updymdを設定します。
	 */
	public void setUpdymd(String updymd) {
		this.updymd = updymd;
	}

	/**
	 * updtimeを取得します。
	 * @return updtime
	 */
	public String getUpdtime() {
		return updtime;
	}

	/**
	 * updtime
	 * @param updtimeを設定します。
	 */
	public void setUpdtime(String updtime) {
		this.updtime = updtime;
	}

	/**
	 * costSectionNameを取得します。
	 * @return costSectionName
	 */
	public String getCostSectionName() {
		return costSectionName;
	}

	/**
	 * costSectionName
	 * @param costSectionNameを設定します。
	 */
	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}

	/**
	 * periodFromを取得します。
	 * @return periodFrom
	 */
	public String getPeriodFrom() {
		return periodFrom;
	}

	/**
	 * periodFrom
	 * @param periodFromを設定します。
	 */
	public void setPeriodFrom(String periodFrom) {
		this.periodFrom = periodFrom;
	}

	/**
	 * periodToを取得します。
	 * @return periodTo
	 */
	public String getPeriodTo() {
		return periodTo;
	}

	/**
	 * periodTo
	 * @param periodToを設定します。
	 */
	public void setPeriodTo(String periodTo) {
		this.periodTo = periodTo;
	}

}
