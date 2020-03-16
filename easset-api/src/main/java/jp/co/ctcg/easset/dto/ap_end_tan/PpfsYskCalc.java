/*===================================================================
 * ファイル名 : PpfsYskCalc.java
 * 概要説明   : ProPlus取込：償却費予測結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-11-27 1.0  李           新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.ap_end_tan;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Date;

import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class PpfsYskCalc implements Externalizable {
	private static final long serialVersionUID = 1L;
	private String period; // 会計年月
	private String companyCode; // 会社コード 人事マスタコード
	private Date createDate; // 作成日
	private String createStaffCode; // 作成者社員番号
	private Date updateDate; // 更新日
	private String updateStaffCode; // 更新者社員番号
	private String pkYsk; // 予測台帳キー
	private Long koyuno; // 固有番号
	private String kaishacd; // 会社コード
	private String useridpk; // ユーザIDキー
	private String userid; // ユーザID
	private String chobo; // 帳簿種別
	private String skkyydo; // 償却年度
	private String yskdatakbn; // 予測データ区分
	private String calcshoritaishoflg; // 計算処理対象フラグ
	private String skkgkhfkbn; // 償却額配賦区分
	private String idomotosakikbn; // 移動元先区分
	private String anbunmotosakikbn; // 按分元先区分
	private String oya; // 資産番号・親
	private String eda; // 資産番号・枝
	private String soshiki1pk; // 組織1キー
	private String soshiki1cd; // 組織1コード
	private String soshiki2pk; // 組織2キー
	private String soshiki2cd; // 組織2コード
	private String soshiki3pk; // 組織3キー
	private String soshiki3cd; // 組織3コード
	private String soshiki4pk; // 組織4キー
	private String soshiki4cd; // 組織4コード
	private String shuruipk; // 種類キー
	private String shuruicd; // 種類コード
	private String kozopk; // 構造用途キー
	private String kozocd; // 構造用途コード
	private String bunruipk; // 分類キー
	private String bunruicd; // 分類コード
	private String setchipk; // 設置場所キー
	private String setchicd; // 設置場所コード
	private String niniLd_1pk; // 任意(台帳)1キー
	private String niniLd_1cd; // 任意(台帳)1コード
	private String niniLd_2pk; // 任意(台帳)2キー
	private String niniLd_2cd; // 任意(台帳)2コード
	private String niniLd_3pk; // 任意(台帳)3キー
	private String niniLd_3cd; // 任意(台帳)3コード
	private String niniLd_4pk; // 任意(台帳)4キー
	private String niniLd_4cd; // 任意(台帳)4コード
	private String niniLd_5pk; // 任意(台帳)5キー
	private String niniLd_5cd; // 任意(台帳)5コード
	private String niniLd_6pk; // 任意(台帳)6キー
	private String niniLd_6cd; // 任意(台帳)6コード
	private String niniLd_7pk; // 任意(台帳)7キー
	private String niniLd_7cd; // 任意(台帳)7コード
	private String niniLd_8pk; // 任意(台帳)8キー
	private String niniLd_8cd; // 任意(台帳)8コード
	private String niniLd_9pk; // 任意(台帳)9キー
	private String niniLd_9cd; // 任意(台帳)9コード
	private String niniLd_10pk; // 任意(台帳)10キー
	private String niniLd_10cd; // 任意(台帳)10コード
	private String niniLd_11pk; // 任意(台帳)11キー
	private String niniLd_11cd; // 任意(台帳)11コード
	private String niniLd_12pk; // 任意(台帳)12キー
	private String niniLd_12cd; // 任意(台帳)12コード
	private String niniLd_13pk; // 任意(台帳)13キー
	private String niniLd_13cd; // 任意(台帳)13コード
	private String niniLd_14pk; // 任意(台帳)14キー
	private String niniLd_14cd; // 任意(台帳)14コード
	private String niniLd_15pk; // 任意(台帳)15キー
	private String niniLd_15cd; // 任意(台帳)15コード
	private String niniLd_16pk; // 任意(台帳)16キー
	private String niniLd_16cd; // 任意(台帳)16コード
	private String niniLd_17pk; // 任意(台帳)17キー
	private String niniLd_17cd; // 任意(台帳)17コード
	private String niniLd_18pk; // 任意(台帳)18キー
	private String niniLd_18cd; // 任意(台帳)18コード
	private String niniLd_19pk; // 任意(台帳)19キー
	private String niniLd_19cd; // 任意(台帳)19コード
	private String niniLd_20pk; // 任意(台帳)20キー
	private String niniLd_20cd; // 任意(台帳)20コード
	private Long askstkgk; // 圧縮後取得価額
	private Long ksboka; // 期首帳簿価額
	private Long zkskkgkNen; // 増加償却額(年額)
	private Long tkskkgkNen; // 特別償却額(年額)
	private Long niniskkgkNen; // 任意償却額(年額)
	private Long gensongkNen; // 減損額(年額)
	private Long ftskkgk1; // 普通償却額1
	private Long ftskkgk2; // 普通償却額2
	private Long ftskkgk3; // 普通償却額3
	private Long ftskkgk4; // 普通償却額4
	private Long ftskkgk5; // 普通償却額5
	private Long ftskkgk6; // 普通償却額6
	private Long ftskkgk7; // 普通償却額7
	private Long ftskkgk8; // 普通償却額8
	private Long ftskkgk9; // 普通償却額9
	private Long ftskkgk10; // 普通償却額10
	private Long ftskkgk11; // 普通償却額11
	private Long ftskkgk12; // 普通償却額12
	private Long zkskkgk1; // 増加償却額1
	private Long zkskkgk2; // 増加償却額2
	private Long zkskkgk3; // 増加償却額3
	private Long zkskkgk4; // 増加償却額4
	private Long zkskkgk5; // 増加償却額5
	private Long zkskkgk6; // 増加償却額6
	private Long zkskkgk7; // 増加償却額7
	private Long zkskkgk8; // 増加償却額8
	private Long zkskkgk9; // 増加償却額9
	private Long zkskkgk10; // 増加償却額10
	private Long zkskkgk11; // 増加償却額11
	private Long zkskkgk12; // 増加償却額12
	private Long tkskkgk1; // 特別償却額1
	private Long tkskkgk2; // 特別償却額2
	private Long tkskkgk3; // 特別償却額3
	private Long tkskkgk4; // 特別償却額4
	private Long tkskkgk5; // 特別償却額5
	private Long tkskkgk6; // 特別償却額6
	private Long tkskkgk7; // 特別償却額7
	private Long tkskkgk8; // 特別償却額8
	private Long tkskkgk9; // 特別償却額9
	private Long tkskkgk10; // 特別償却額10
	private Long tkskkgk11; // 特別償却額11
	private Long tkskkgk12; // 特別償却額12
	private Long niniskkgk1; // 任意償却額1
	private Long niniskkgk2; // 任意償却額2
	private Long niniskkgk3; // 任意償却額3
	private Long niniskkgk4; // 任意償却額4
	private Long niniskkgk5; // 任意償却額5
	private Long niniskkgk6; // 任意償却額6
	private Long niniskkgk7; // 任意償却額7
	private Long niniskkgk8; // 任意償却額8
	private Long niniskkgk9; // 任意償却額9
	private Long niniskkgk10; // 任意償却額10
	private Long niniskkgk11; // 任意償却額11
	private Long niniskkgk12; // 任意償却額12
	private Long gensongk1; // 減損額1
	private Long gensongk2; // 減損額2
	private Long gensongk3; // 減損額3
	private Long gensongk4; // 減損額4
	private Long gensongk5; // 減損額5
	private Long gensongk6; // 減損額6
	private Long gensongk7; // 減損額7
	private Long gensongk8; // 減損額8
	private Long gensongk9; // 減損額9
	private Long gensongk10; // 減損額10
	private Long gensongk11; // 減損額11
	private Long gensongk12; // 減損額12
	private String hfpk; // 配賦キー
	private String hfcd; // 配賦コード
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

	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		period = (String)input.readObject();
		companyCode = (String)input.readObject();
		createDate = (Date)input.readObject();
		createStaffCode = (String)input.readObject();
		updateDate = (Date)input.readObject();
		updateStaffCode = (String)input.readObject();
		pkYsk = (String)input.readObject();
		koyuno = Function.getExternalLong(input.readObject());
		kaishacd = (String)input.readObject();
		useridpk = (String)input.readObject();
		userid = (String)input.readObject();
		chobo = (String)input.readObject();
		skkyydo = (String)input.readObject();
		yskdatakbn = (String)input.readObject();
		calcshoritaishoflg = (String)input.readObject();
		skkgkhfkbn = (String)input.readObject();
		idomotosakikbn = (String)input.readObject();
		anbunmotosakikbn = (String)input.readObject();
		oya = (String)input.readObject();
		eda = (String)input.readObject();
		soshiki1pk = (String)input.readObject();
		soshiki1cd = (String)input.readObject();
		soshiki2pk = (String)input.readObject();
		soshiki2cd = (String)input.readObject();
		soshiki3pk = (String)input.readObject();
		soshiki3cd = (String)input.readObject();
		soshiki4pk = (String)input.readObject();
		soshiki4cd = (String)input.readObject();
		shuruipk = (String)input.readObject();
		shuruicd = (String)input.readObject();
		kozopk = (String)input.readObject();
		kozocd = (String)input.readObject();
		bunruipk = (String)input.readObject();
		bunruicd = (String)input.readObject();
		setchipk = (String)input.readObject();
		setchicd = (String)input.readObject();
		niniLd_1pk = (String)input.readObject();
		niniLd_1cd = (String)input.readObject();
		niniLd_2pk = (String)input.readObject();
		niniLd_2cd = (String)input.readObject();
		niniLd_3pk = (String)input.readObject();
		niniLd_3cd = (String)input.readObject();
		niniLd_4pk = (String)input.readObject();
		niniLd_4cd = (String)input.readObject();
		niniLd_5pk = (String)input.readObject();
		niniLd_5cd = (String)input.readObject();
		niniLd_6pk = (String)input.readObject();
		niniLd_6cd = (String)input.readObject();
		niniLd_7pk = (String)input.readObject();
		niniLd_7cd = (String)input.readObject();
		niniLd_8pk = (String)input.readObject();
		niniLd_8cd = (String)input.readObject();
		niniLd_9pk = (String)input.readObject();
		niniLd_9cd = (String)input.readObject();
		niniLd_10pk = (String)input.readObject();
		niniLd_10cd = (String)input.readObject();
		niniLd_11pk = (String)input.readObject();
		niniLd_11cd = (String)input.readObject();
		niniLd_12pk = (String)input.readObject();
		niniLd_12cd = (String)input.readObject();
		niniLd_13pk = (String)input.readObject();
		niniLd_13cd = (String)input.readObject();
		niniLd_14pk = (String)input.readObject();
		niniLd_14cd = (String)input.readObject();
		niniLd_15pk = (String)input.readObject();
		niniLd_15cd = (String)input.readObject();
		niniLd_16pk = (String)input.readObject();
		niniLd_16cd = (String)input.readObject();
		niniLd_17pk = (String)input.readObject();
		niniLd_17cd = (String)input.readObject();
		niniLd_18pk = (String)input.readObject();
		niniLd_18cd = (String)input.readObject();
		niniLd_19pk = (String)input.readObject();
		niniLd_19cd = (String)input.readObject();
		niniLd_20pk = (String)input.readObject();
		niniLd_20cd = (String)input.readObject();
		askstkgk = Function.getExternalLong(input.readObject());
		ksboka = Function.getExternalLong(input.readObject());
		zkskkgkNen = Function.getExternalLong(input.readObject());
		tkskkgkNen = Function.getExternalLong(input.readObject());
		niniskkgkNen = Function.getExternalLong(input.readObject());
		gensongkNen = Function.getExternalLong(input.readObject());
		ftskkgk1 = Function.getExternalLong(input.readObject());
		ftskkgk2 = Function.getExternalLong(input.readObject());
		ftskkgk3 = Function.getExternalLong(input.readObject());
		ftskkgk4 = Function.getExternalLong(input.readObject());
		ftskkgk5 = Function.getExternalLong(input.readObject());
		ftskkgk6 = Function.getExternalLong(input.readObject());
		ftskkgk7 = Function.getExternalLong(input.readObject());
		ftskkgk8 = Function.getExternalLong(input.readObject());
		ftskkgk9 = Function.getExternalLong(input.readObject());
		ftskkgk10 = Function.getExternalLong(input.readObject());
		ftskkgk11 = Function.getExternalLong(input.readObject());
		ftskkgk12 = Function.getExternalLong(input.readObject());
		zkskkgk1 = Function.getExternalLong(input.readObject());
		zkskkgk2 = Function.getExternalLong(input.readObject());
		zkskkgk3 = Function.getExternalLong(input.readObject());
		zkskkgk4 = Function.getExternalLong(input.readObject());
		zkskkgk5 = Function.getExternalLong(input.readObject());
		zkskkgk6 = Function.getExternalLong(input.readObject());
		zkskkgk7 = Function.getExternalLong(input.readObject());
		zkskkgk8 = Function.getExternalLong(input.readObject());
		zkskkgk9 = Function.getExternalLong(input.readObject());
		zkskkgk10 = Function.getExternalLong(input.readObject());
		zkskkgk11 = Function.getExternalLong(input.readObject());
		zkskkgk12 = Function.getExternalLong(input.readObject());
		tkskkgk1 = Function.getExternalLong(input.readObject());
		tkskkgk2 = Function.getExternalLong(input.readObject());
		tkskkgk3 = Function.getExternalLong(input.readObject());
		tkskkgk4 = Function.getExternalLong(input.readObject());
		tkskkgk5 = Function.getExternalLong(input.readObject());
		tkskkgk6 = Function.getExternalLong(input.readObject());
		tkskkgk7 = Function.getExternalLong(input.readObject());
		tkskkgk8 = Function.getExternalLong(input.readObject());
		tkskkgk9 = Function.getExternalLong(input.readObject());
		tkskkgk10 = Function.getExternalLong(input.readObject());
		tkskkgk11 = Function.getExternalLong(input.readObject());
		tkskkgk12 = Function.getExternalLong(input.readObject());
		niniskkgk1 = Function.getExternalLong(input.readObject());
		niniskkgk2 = Function.getExternalLong(input.readObject());
		niniskkgk3 = Function.getExternalLong(input.readObject());
		niniskkgk4 = Function.getExternalLong(input.readObject());
		niniskkgk5 = Function.getExternalLong(input.readObject());
		niniskkgk6 = Function.getExternalLong(input.readObject());
		niniskkgk7 = Function.getExternalLong(input.readObject());
		niniskkgk8 = Function.getExternalLong(input.readObject());
		niniskkgk9 = Function.getExternalLong(input.readObject());
		niniskkgk10 = Function.getExternalLong(input.readObject());
		niniskkgk11 = Function.getExternalLong(input.readObject());
		niniskkgk12 = Function.getExternalLong(input.readObject());
		gensongk1 = Function.getExternalLong(input.readObject());
		gensongk2 = Function.getExternalLong(input.readObject());
		gensongk3 = Function.getExternalLong(input.readObject());
		gensongk4 = Function.getExternalLong(input.readObject());
		gensongk5 = Function.getExternalLong(input.readObject());
		gensongk6 = Function.getExternalLong(input.readObject());
		gensongk7 = Function.getExternalLong(input.readObject());
		gensongk8 = Function.getExternalLong(input.readObject());
		gensongk9 = Function.getExternalLong(input.readObject());
		gensongk10 = Function.getExternalLong(input.readObject());
		gensongk11 = Function.getExternalLong(input.readObject());
		gensongk12 = Function.getExternalLong(input.readObject());
		hfpk = (String)input.readObject();
		hfcd = (String)input.readObject();
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
	}

	public void writeExternal(ObjectOutput output) throws IOException {
		output.writeObject(period);
		output.writeObject(companyCode);
		output.writeObject(createDate);
		output.writeObject(createStaffCode);
		output.writeObject(updateDate);
		output.writeObject(updateStaffCode);
		output.writeObject(pkYsk);
		output.writeObject(koyuno);
		output.writeObject(kaishacd);
		output.writeObject(useridpk);
		output.writeObject(userid);
		output.writeObject(chobo);
		output.writeObject(skkyydo);
		output.writeObject(yskdatakbn);
		output.writeObject(calcshoritaishoflg);
		output.writeObject(skkgkhfkbn);
		output.writeObject(idomotosakikbn);
		output.writeObject(anbunmotosakikbn);
		output.writeObject(oya);
		output.writeObject(eda);
		output.writeObject(soshiki1pk);
		output.writeObject(soshiki1cd);
		output.writeObject(soshiki2pk);
		output.writeObject(soshiki2cd);
		output.writeObject(soshiki3pk);
		output.writeObject(soshiki3cd);
		output.writeObject(soshiki4pk);
		output.writeObject(soshiki4cd);
		output.writeObject(shuruipk);
		output.writeObject(shuruicd);
		output.writeObject(kozopk);
		output.writeObject(kozocd);
		output.writeObject(bunruipk);
		output.writeObject(bunruicd);
		output.writeObject(setchipk);
		output.writeObject(setchicd);
		output.writeObject(niniLd_1pk);
		output.writeObject(niniLd_1cd);
		output.writeObject(niniLd_2pk);
		output.writeObject(niniLd_2cd);
		output.writeObject(niniLd_3pk);
		output.writeObject(niniLd_3cd);
		output.writeObject(niniLd_4pk);
		output.writeObject(niniLd_4cd);
		output.writeObject(niniLd_5pk);
		output.writeObject(niniLd_5cd);
		output.writeObject(niniLd_6pk);
		output.writeObject(niniLd_6cd);
		output.writeObject(niniLd_7pk);
		output.writeObject(niniLd_7cd);
		output.writeObject(niniLd_8pk);
		output.writeObject(niniLd_8cd);
		output.writeObject(niniLd_9pk);
		output.writeObject(niniLd_9cd);
		output.writeObject(niniLd_10pk);
		output.writeObject(niniLd_10cd);
		output.writeObject(niniLd_11pk);
		output.writeObject(niniLd_11cd);
		output.writeObject(niniLd_12pk);
		output.writeObject(niniLd_12cd);
		output.writeObject(niniLd_13pk);
		output.writeObject(niniLd_13cd);
		output.writeObject(niniLd_14pk);
		output.writeObject(niniLd_14cd);
		output.writeObject(niniLd_15pk);
		output.writeObject(niniLd_15cd);
		output.writeObject(niniLd_16pk);
		output.writeObject(niniLd_16cd);
		output.writeObject(niniLd_17pk);
		output.writeObject(niniLd_17cd);
		output.writeObject(niniLd_18pk);
		output.writeObject(niniLd_18cd);
		output.writeObject(niniLd_19pk);
		output.writeObject(niniLd_19cd);
		output.writeObject(niniLd_20pk);
		output.writeObject(niniLd_20cd);
		output.writeObject(askstkgk);
		output.writeObject(ksboka);
		output.writeObject(zkskkgkNen);
		output.writeObject(tkskkgkNen);
		output.writeObject(niniskkgkNen);
		output.writeObject(gensongkNen);
		output.writeObject(ftskkgk1);
		output.writeObject(ftskkgk2);
		output.writeObject(ftskkgk3);
		output.writeObject(ftskkgk4);
		output.writeObject(ftskkgk5);
		output.writeObject(ftskkgk6);
		output.writeObject(ftskkgk7);
		output.writeObject(ftskkgk8);
		output.writeObject(ftskkgk9);
		output.writeObject(ftskkgk10);
		output.writeObject(ftskkgk11);
		output.writeObject(ftskkgk12);
		output.writeObject(zkskkgk1);
		output.writeObject(zkskkgk2);
		output.writeObject(zkskkgk3);
		output.writeObject(zkskkgk4);
		output.writeObject(zkskkgk5);
		output.writeObject(zkskkgk6);
		output.writeObject(zkskkgk7);
		output.writeObject(zkskkgk8);
		output.writeObject(zkskkgk9);
		output.writeObject(zkskkgk10);
		output.writeObject(zkskkgk11);
		output.writeObject(zkskkgk12);
		output.writeObject(tkskkgk1);
		output.writeObject(tkskkgk2);
		output.writeObject(tkskkgk3);
		output.writeObject(tkskkgk4);
		output.writeObject(tkskkgk5);
		output.writeObject(tkskkgk6);
		output.writeObject(tkskkgk7);
		output.writeObject(tkskkgk8);
		output.writeObject(tkskkgk9);
		output.writeObject(tkskkgk10);
		output.writeObject(tkskkgk11);
		output.writeObject(tkskkgk12);
		output.writeObject(niniskkgk1);
		output.writeObject(niniskkgk2);
		output.writeObject(niniskkgk3);
		output.writeObject(niniskkgk4);
		output.writeObject(niniskkgk5);
		output.writeObject(niniskkgk6);
		output.writeObject(niniskkgk7);
		output.writeObject(niniskkgk8);
		output.writeObject(niniskkgk9);
		output.writeObject(niniskkgk10);
		output.writeObject(niniskkgk11);
		output.writeObject(niniskkgk12);
		output.writeObject(gensongk1);
		output.writeObject(gensongk2);
		output.writeObject(gensongk3);
		output.writeObject(gensongk4);
		output.writeObject(gensongk5);
		output.writeObject(gensongk6);
		output.writeObject(gensongk7);
		output.writeObject(gensongk8);
		output.writeObject(gensongk9);
		output.writeObject(gensongk10);
		output.writeObject(gensongk11);
		output.writeObject(gensongk12);
		output.writeObject(hfpk);
		output.writeObject(hfcd);
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
	}

	/**
	 * periodを取得します。
	 * @return period
	 */
	public String getPeriod() {
		return period;
	}

	/**
	 * period
	 * @param periodを設定します。
	 */
	public void setPeriod(String period) {
		this.period = period;
	}

	/**
	 * companyCodeを取得します。
	 * @return companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode
	 * @param companyCodeを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * createDateを取得します。
	 * @return createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * createDate
	 * @param createDateを設定します。
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	/**
	 * createStaffCodeを取得します。
	 * @return createStaffCode
	 */
	public String getCreateStaffCode() {
		return createStaffCode;
	}

	/**
	 * createStaffCode
	 * @param createStaffCodeを設定します。
	 */
	public void setCreateStaffCode(String createStaffCode) {
		this.createStaffCode = createStaffCode;
	}

	/**
	 * updateDateを取得します。
	 * @return updateDate
	 */
	public Date getUpdateDate() {
		return updateDate;
	}

	/**
	 * updateDate
	 * @param updateDateを設定します。
	 */
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	/**
	 * updateStaffCodeを取得します。
	 * @return updateStaffCode
	 */
	public String getUpdateStaffCode() {
		return updateStaffCode;
	}

	/**
	 * updateStaffCode
	 * @param updateStaffCodeを設定します。
	 */
	public void setUpdateStaffCode(String updateStaffCode) {
		this.updateStaffCode = updateStaffCode;
	}

	/**
	 * pkYskを取得します。
	 * @return pkYsk
	 */
	public String getPkYsk() {
		return pkYsk;
	}

	/**
	 * pkYsk
	 * @param pkYskを設定します。
	 */
	public void setPkYsk(String pkYsk) {
		this.pkYsk = pkYsk;
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
	 * useridpkを取得します。
	 * @return useridpk
	 */
	public String getUseridpk() {
		return useridpk;
	}

	/**
	 * useridpk
	 * @param useridpkを設定します。
	 */
	public void setUseridpk(String useridpk) {
		this.useridpk = useridpk;
	}

	/**
	 * useridを取得します。
	 * @return userid
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * userid
	 * @param useridを設定します。
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * choboを取得します。
	 * @return chobo
	 */
	public String getChobo() {
		return chobo;
	}

	/**
	 * chobo
	 * @param choboを設定します。
	 */
	public void setChobo(String chobo) {
		this.chobo = chobo;
	}

	/**
	 * skkyydoを取得します。
	 * @return skkyydo
	 */
	public String getSkkyydo() {
		return skkyydo;
	}

	/**
	 * skkyydo
	 * @param skkyydoを設定します。
	 */
	public void setSkkyydo(String skkyydo) {
		this.skkyydo = skkyydo;
	}

	/**
	 * yskdatakbnを取得します。
	 * @return yskdatakbn
	 */
	public String getYskdatakbn() {
		return yskdatakbn;
	}

	/**
	 * yskdatakbn
	 * @param yskdatakbnを設定します。
	 */
	public void setYskdatakbn(String yskdatakbn) {
		this.yskdatakbn = yskdatakbn;
	}

	/**
	 * calcshoritaishoflgを取得します。
	 * @return calcshoritaishoflg
	 */
	public String getCalcshoritaishoflg() {
		return calcshoritaishoflg;
	}

	/**
	 * calcshoritaishoflg
	 * @param calcshoritaishoflgを設定します。
	 */
	public void setCalcshoritaishoflg(String calcshoritaishoflg) {
		this.calcshoritaishoflg = calcshoritaishoflg;
	}

	/**
	 * skkgkhfkbnを取得します。
	 * @return skkgkhfkbn
	 */
	public String getSkkgkhfkbn() {
		return skkgkhfkbn;
	}

	/**
	 * skkgkhfkbn
	 * @param skkgkhfkbnを設定します。
	 */
	public void setSkkgkhfkbn(String skkgkhfkbn) {
		this.skkgkhfkbn = skkgkhfkbn;
	}

	/**
	 * idomotosakikbnを取得します。
	 * @return idomotosakikbn
	 */
	public String getIdomotosakikbn() {
		return idomotosakikbn;
	}

	/**
	 * idomotosakikbn
	 * @param idomotosakikbnを設定します。
	 */
	public void setIdomotosakikbn(String idomotosakikbn) {
		this.idomotosakikbn = idomotosakikbn;
	}

	/**
	 * anbunmotosakikbnを取得します。
	 * @return anbunmotosakikbn
	 */
	public String getAnbunmotosakikbn() {
		return anbunmotosakikbn;
	}

	/**
	 * anbunmotosakikbn
	 * @param anbunmotosakikbnを設定します。
	 */
	public void setAnbunmotosakikbn(String anbunmotosakikbn) {
		this.anbunmotosakikbn = anbunmotosakikbn;
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
	 * soshiki1pkを取得します。
	 * @return soshiki1pk
	 */
	public String getSoshiki1pk() {
		return soshiki1pk;
	}

	/**
	 * soshiki1pk
	 * @param soshiki1pkを設定します。
	 */
	public void setSoshiki1pk(String soshiki1pk) {
		this.soshiki1pk = soshiki1pk;
	}

	/**
	 * soshiki1cdを取得します。
	 * @return soshiki1cd
	 */
	public String getSoshiki1cd() {
		return soshiki1cd;
	}

	/**
	 * soshiki1cd
	 * @param soshiki1cdを設定します。
	 */
	public void setSoshiki1cd(String soshiki1cd) {
		this.soshiki1cd = soshiki1cd;
	}

	/**
	 * soshiki2pkを取得します。
	 * @return soshiki2pk
	 */
	public String getSoshiki2pk() {
		return soshiki2pk;
	}

	/**
	 * soshiki2pk
	 * @param soshiki2pkを設定します。
	 */
	public void setSoshiki2pk(String soshiki2pk) {
		this.soshiki2pk = soshiki2pk;
	}

	/**
	 * soshiki2cdを取得します。
	 * @return soshiki2cd
	 */
	public String getSoshiki2cd() {
		return soshiki2cd;
	}

	/**
	 * soshiki2cd
	 * @param soshiki2cdを設定します。
	 */
	public void setSoshiki2cd(String soshiki2cd) {
		this.soshiki2cd = soshiki2cd;
	}

	/**
	 * soshiki3pkを取得します。
	 * @return soshiki3pk
	 */
	public String getSoshiki3pk() {
		return soshiki3pk;
	}

	/**
	 * soshiki3pk
	 * @param soshiki3pkを設定します。
	 */
	public void setSoshiki3pk(String soshiki3pk) {
		this.soshiki3pk = soshiki3pk;
	}

	/**
	 * soshiki3cdを取得します。
	 * @return soshiki3cd
	 */
	public String getSoshiki3cd() {
		return soshiki3cd;
	}

	/**
	 * soshiki3cd
	 * @param soshiki3cdを設定します。
	 */
	public void setSoshiki3cd(String soshiki3cd) {
		this.soshiki3cd = soshiki3cd;
	}

	/**
	 * soshiki4pkを取得します。
	 * @return soshiki4pk
	 */
	public String getSoshiki4pk() {
		return soshiki4pk;
	}

	/**
	 * soshiki4pk
	 * @param soshiki4pkを設定します。
	 */
	public void setSoshiki4pk(String soshiki4pk) {
		this.soshiki4pk = soshiki4pk;
	}

	/**
	 * soshiki4cdを取得します。
	 * @return soshiki4cd
	 */
	public String getSoshiki4cd() {
		return soshiki4cd;
	}

	/**
	 * soshiki4cd
	 * @param soshiki4cdを設定します。
	 */
	public void setSoshiki4cd(String soshiki4cd) {
		this.soshiki4cd = soshiki4cd;
	}

	/**
	 * shuruipkを取得します。
	 * @return shuruipk
	 */
	public String getShuruipk() {
		return shuruipk;
	}

	/**
	 * shuruipk
	 * @param shuruipkを設定します。
	 */
	public void setShuruipk(String shuruipk) {
		this.shuruipk = shuruipk;
	}

	/**
	 * shuruicdを取得します。
	 * @return shuruicd
	 */
	public String getShuruicd() {
		return shuruicd;
	}

	/**
	 * shuruicd
	 * @param shuruicdを設定します。
	 */
	public void setShuruicd(String shuruicd) {
		this.shuruicd = shuruicd;
	}

	/**
	 * kozopkを取得します。
	 * @return kozopk
	 */
	public String getKozopk() {
		return kozopk;
	}

	/**
	 * kozopk
	 * @param kozopkを設定します。
	 */
	public void setKozopk(String kozopk) {
		this.kozopk = kozopk;
	}

	/**
	 * kozocdを取得します。
	 * @return kozocd
	 */
	public String getKozocd() {
		return kozocd;
	}

	/**
	 * kozocd
	 * @param kozocdを設定します。
	 */
	public void setKozocd(String kozocd) {
		this.kozocd = kozocd;
	}

	/**
	 * bunruipkを取得します。
	 * @return bunruipk
	 */
	public String getBunruipk() {
		return bunruipk;
	}

	/**
	 * bunruipk
	 * @param bunruipkを設定します。
	 */
	public void setBunruipk(String bunruipk) {
		this.bunruipk = bunruipk;
	}

	/**
	 * bunruicdを取得します。
	 * @return bunruicd
	 */
	public String getBunruicd() {
		return bunruicd;
	}

	/**
	 * bunruicd
	 * @param bunruicdを設定します。
	 */
	public void setBunruicd(String bunruicd) {
		this.bunruicd = bunruicd;
	}

	/**
	 * setchipkを取得します。
	 * @return setchipk
	 */
	public String getSetchipk() {
		return setchipk;
	}

	/**
	 * setchipk
	 * @param setchipkを設定します。
	 */
	public void setSetchipk(String setchipk) {
		this.setchipk = setchipk;
	}

	/**
	 * setchicdを取得します。
	 * @return setchicd
	 */
	public String getSetchicd() {
		return setchicd;
	}

	/**
	 * setchicd
	 * @param setchicdを設定します。
	 */
	public void setSetchicd(String setchicd) {
		this.setchicd = setchicd;
	}

	/**
	 * niniLd_1pkを取得します。
	 * @return niniLd_1pk
	 */
	public String getNiniLd_1pk() {
		return niniLd_1pk;
	}

	/**
	 * niniLd_1pk
	 * @param niniLd_1pkを設定します。
	 */
	public void setNiniLd_1pk(String niniLd_1pk) {
		this.niniLd_1pk = niniLd_1pk;
	}

	/**
	 * niniLd_1cdを取得します。
	 * @return niniLd_1cd
	 */
	public String getNiniLd_1cd() {
		return niniLd_1cd;
	}

	/**
	 * niniLd_1cd
	 * @param niniLd_1cdを設定します。
	 */
	public void setNiniLd_1cd(String niniLd_1cd) {
		this.niniLd_1cd = niniLd_1cd;
	}

	/**
	 * niniLd_2pkを取得します。
	 * @return niniLd_2pk
	 */
	public String getNiniLd_2pk() {
		return niniLd_2pk;
	}

	/**
	 * niniLd_2pk
	 * @param niniLd_2pkを設定します。
	 */
	public void setNiniLd_2pk(String niniLd_2pk) {
		this.niniLd_2pk = niniLd_2pk;
	}

	/**
	 * niniLd_2cdを取得します。
	 * @return niniLd_2cd
	 */
	public String getNiniLd_2cd() {
		return niniLd_2cd;
	}

	/**
	 * niniLd_2cd
	 * @param niniLd_2cdを設定します。
	 */
	public void setNiniLd_2cd(String niniLd_2cd) {
		this.niniLd_2cd = niniLd_2cd;
	}

	/**
	 * niniLd_3pkを取得します。
	 * @return niniLd_3pk
	 */
	public String getNiniLd_3pk() {
		return niniLd_3pk;
	}

	/**
	 * niniLd_3pk
	 * @param niniLd_3pkを設定します。
	 */
	public void setNiniLd_3pk(String niniLd_3pk) {
		this.niniLd_3pk = niniLd_3pk;
	}

	/**
	 * niniLd_3cdを取得します。
	 * @return niniLd_3cd
	 */
	public String getNiniLd_3cd() {
		return niniLd_3cd;
	}

	/**
	 * niniLd_3cd
	 * @param niniLd_3cdを設定します。
	 */
	public void setNiniLd_3cd(String niniLd_3cd) {
		this.niniLd_3cd = niniLd_3cd;
	}

	/**
	 * niniLd_4pkを取得します。
	 * @return niniLd_4pk
	 */
	public String getNiniLd_4pk() {
		return niniLd_4pk;
	}

	/**
	 * niniLd_4pk
	 * @param niniLd_4pkを設定します。
	 */
	public void setNiniLd_4pk(String niniLd_4pk) {
		this.niniLd_4pk = niniLd_4pk;
	}

	/**
	 * niniLd_4cdを取得します。
	 * @return niniLd_4cd
	 */
	public String getNiniLd_4cd() {
		return niniLd_4cd;
	}

	/**
	 * niniLd_4cd
	 * @param niniLd_4cdを設定します。
	 */
	public void setNiniLd_4cd(String niniLd_4cd) {
		this.niniLd_4cd = niniLd_4cd;
	}

	/**
	 * niniLd_5pkを取得します。
	 * @return niniLd_5pk
	 */
	public String getNiniLd_5pk() {
		return niniLd_5pk;
	}

	/**
	 * niniLd_5pk
	 * @param niniLd_5pkを設定します。
	 */
	public void setNiniLd_5pk(String niniLd_5pk) {
		this.niniLd_5pk = niniLd_5pk;
	}

	/**
	 * niniLd_5cdを取得します。
	 * @return niniLd_5cd
	 */
	public String getNiniLd_5cd() {
		return niniLd_5cd;
	}

	/**
	 * niniLd_5cd
	 * @param niniLd_5cdを設定します。
	 */
	public void setNiniLd_5cd(String niniLd_5cd) {
		this.niniLd_5cd = niniLd_5cd;
	}

	/**
	 * niniLd_6pkを取得します。
	 * @return niniLd_6pk
	 */
	public String getNiniLd_6pk() {
		return niniLd_6pk;
	}

	/**
	 * niniLd_6pk
	 * @param niniLd_6pkを設定します。
	 */
	public void setNiniLd_6pk(String niniLd_6pk) {
		this.niniLd_6pk = niniLd_6pk;
	}

	/**
	 * niniLd_6cdを取得します。
	 * @return niniLd_6cd
	 */
	public String getNiniLd_6cd() {
		return niniLd_6cd;
	}

	/**
	 * niniLd_6cd
	 * @param niniLd_6cdを設定します。
	 */
	public void setNiniLd_6cd(String niniLd_6cd) {
		this.niniLd_6cd = niniLd_6cd;
	}

	/**
	 * niniLd_7pkを取得します。
	 * @return niniLd_7pk
	 */
	public String getNiniLd_7pk() {
		return niniLd_7pk;
	}

	/**
	 * niniLd_7pk
	 * @param niniLd_7pkを設定します。
	 */
	public void setNiniLd_7pk(String niniLd_7pk) {
		this.niniLd_7pk = niniLd_7pk;
	}

	/**
	 * niniLd_7cdを取得します。
	 * @return niniLd_7cd
	 */
	public String getNiniLd_7cd() {
		return niniLd_7cd;
	}

	/**
	 * niniLd_7cd
	 * @param niniLd_7cdを設定します。
	 */
	public void setNiniLd_7cd(String niniLd_7cd) {
		this.niniLd_7cd = niniLd_7cd;
	}

	/**
	 * niniLd_8pkを取得します。
	 * @return niniLd_8pk
	 */
	public String getNiniLd_8pk() {
		return niniLd_8pk;
	}

	/**
	 * niniLd_8pk
	 * @param niniLd_8pkを設定します。
	 */
	public void setNiniLd_8pk(String niniLd_8pk) {
		this.niniLd_8pk = niniLd_8pk;
	}

	/**
	 * niniLd_8cdを取得します。
	 * @return niniLd_8cd
	 */
	public String getNiniLd_8cd() {
		return niniLd_8cd;
	}

	/**
	 * niniLd_8cd
	 * @param niniLd_8cdを設定します。
	 */
	public void setNiniLd_8cd(String niniLd_8cd) {
		this.niniLd_8cd = niniLd_8cd;
	}

	/**
	 * niniLd_9pkを取得します。
	 * @return niniLd_9pk
	 */
	public String getNiniLd_9pk() {
		return niniLd_9pk;
	}

	/**
	 * niniLd_9pk
	 * @param niniLd_9pkを設定します。
	 */
	public void setNiniLd_9pk(String niniLd_9pk) {
		this.niniLd_9pk = niniLd_9pk;
	}

	/**
	 * niniLd_9cdを取得します。
	 * @return niniLd_9cd
	 */
	public String getNiniLd_9cd() {
		return niniLd_9cd;
	}

	/**
	 * niniLd_9cd
	 * @param niniLd_9cdを設定します。
	 */
	public void setNiniLd_9cd(String niniLd_9cd) {
		this.niniLd_9cd = niniLd_9cd;
	}

	/**
	 * niniLd_10pkを取得します。
	 * @return niniLd_10pk
	 */
	public String getNiniLd_10pk() {
		return niniLd_10pk;
	}

	/**
	 * niniLd_10pk
	 * @param niniLd_10pkを設定します。
	 */
	public void setNiniLd_10pk(String niniLd_10pk) {
		this.niniLd_10pk = niniLd_10pk;
	}

	/**
	 * niniLd_10cdを取得します。
	 * @return niniLd_10cd
	 */
	public String getNiniLd_10cd() {
		return niniLd_10cd;
	}

	/**
	 * niniLd_10cd
	 * @param niniLd_10cdを設定します。
	 */
	public void setNiniLd_10cd(String niniLd_10cd) {
		this.niniLd_10cd = niniLd_10cd;
	}

	/**
	 * niniLd_11pkを取得します。
	 * @return niniLd_11pk
	 */
	public String getNiniLd_11pk() {
		return niniLd_11pk;
	}

	/**
	 * niniLd_11pk
	 * @param niniLd_11pkを設定します。
	 */
	public void setNiniLd_11pk(String niniLd_11pk) {
		this.niniLd_11pk = niniLd_11pk;
	}

	/**
	 * niniLd_11cdを取得します。
	 * @return niniLd_11cd
	 */
	public String getNiniLd_11cd() {
		return niniLd_11cd;
	}

	/**
	 * niniLd_11cd
	 * @param niniLd_11cdを設定します。
	 */
	public void setNiniLd_11cd(String niniLd_11cd) {
		this.niniLd_11cd = niniLd_11cd;
	}

	/**
	 * niniLd_12pkを取得します。
	 * @return niniLd_12pk
	 */
	public String getNiniLd_12pk() {
		return niniLd_12pk;
	}

	/**
	 * niniLd_12pk
	 * @param niniLd_12pkを設定します。
	 */
	public void setNiniLd_12pk(String niniLd_12pk) {
		this.niniLd_12pk = niniLd_12pk;
	}

	/**
	 * niniLd_12cdを取得します。
	 * @return niniLd_12cd
	 */
	public String getNiniLd_12cd() {
		return niniLd_12cd;
	}

	/**
	 * niniLd_12cd
	 * @param niniLd_12cdを設定します。
	 */
	public void setNiniLd_12cd(String niniLd_12cd) {
		this.niniLd_12cd = niniLd_12cd;
	}

	/**
	 * niniLd_13pkを取得します。
	 * @return niniLd_13pk
	 */
	public String getNiniLd_13pk() {
		return niniLd_13pk;
	}

	/**
	 * niniLd_13pk
	 * @param niniLd_13pkを設定します。
	 */
	public void setNiniLd_13pk(String niniLd_13pk) {
		this.niniLd_13pk = niniLd_13pk;
	}

	/**
	 * niniLd_13cdを取得します。
	 * @return niniLd_13cd
	 */
	public String getNiniLd_13cd() {
		return niniLd_13cd;
	}

	/**
	 * niniLd_13cd
	 * @param niniLd_13cdを設定します。
	 */
	public void setNiniLd_13cd(String niniLd_13cd) {
		this.niniLd_13cd = niniLd_13cd;
	}

	/**
	 * niniLd_14pkを取得します。
	 * @return niniLd_14pk
	 */
	public String getNiniLd_14pk() {
		return niniLd_14pk;
	}

	/**
	 * niniLd_14pk
	 * @param niniLd_14pkを設定します。
	 */
	public void setNiniLd_14pk(String niniLd_14pk) {
		this.niniLd_14pk = niniLd_14pk;
	}

	/**
	 * niniLd_14cdを取得します。
	 * @return niniLd_14cd
	 */
	public String getNiniLd_14cd() {
		return niniLd_14cd;
	}

	/**
	 * niniLd_14cd
	 * @param niniLd_14cdを設定します。
	 */
	public void setNiniLd_14cd(String niniLd_14cd) {
		this.niniLd_14cd = niniLd_14cd;
	}

	/**
	 * niniLd_15pkを取得します。
	 * @return niniLd_15pk
	 */
	public String getNiniLd_15pk() {
		return niniLd_15pk;
	}

	/**
	 * niniLd_15pk
	 * @param niniLd_15pkを設定します。
	 */
	public void setNiniLd_15pk(String niniLd_15pk) {
		this.niniLd_15pk = niniLd_15pk;
	}

	/**
	 * niniLd_15cdを取得します。
	 * @return niniLd_15cd
	 */
	public String getNiniLd_15cd() {
		return niniLd_15cd;
	}

	/**
	 * niniLd_15cd
	 * @param niniLd_15cdを設定します。
	 */
	public void setNiniLd_15cd(String niniLd_15cd) {
		this.niniLd_15cd = niniLd_15cd;
	}

	/**
	 * niniLd_16pkを取得します。
	 * @return niniLd_16pk
	 */
	public String getNiniLd_16pk() {
		return niniLd_16pk;
	}

	/**
	 * niniLd_16pk
	 * @param niniLd_16pkを設定します。
	 */
	public void setNiniLd_16pk(String niniLd_16pk) {
		this.niniLd_16pk = niniLd_16pk;
	}

	/**
	 * niniLd_16cdを取得します。
	 * @return niniLd_16cd
	 */
	public String getNiniLd_16cd() {
		return niniLd_16cd;
	}

	/**
	 * niniLd_16cd
	 * @param niniLd_16cdを設定します。
	 */
	public void setNiniLd_16cd(String niniLd_16cd) {
		this.niniLd_16cd = niniLd_16cd;
	}

	/**
	 * niniLd_17pkを取得します。
	 * @return niniLd_17pk
	 */
	public String getNiniLd_17pk() {
		return niniLd_17pk;
	}

	/**
	 * niniLd_17pk
	 * @param niniLd_17pkを設定します。
	 */
	public void setNiniLd_17pk(String niniLd_17pk) {
		this.niniLd_17pk = niniLd_17pk;
	}

	/**
	 * niniLd_17cdを取得します。
	 * @return niniLd_17cd
	 */
	public String getNiniLd_17cd() {
		return niniLd_17cd;
	}

	/**
	 * niniLd_17cd
	 * @param niniLd_17cdを設定します。
	 */
	public void setNiniLd_17cd(String niniLd_17cd) {
		this.niniLd_17cd = niniLd_17cd;
	}

	/**
	 * niniLd_18pkを取得します。
	 * @return niniLd_18pk
	 */
	public String getNiniLd_18pk() {
		return niniLd_18pk;
	}

	/**
	 * niniLd_18pk
	 * @param niniLd_18pkを設定します。
	 */
	public void setNiniLd_18pk(String niniLd_18pk) {
		this.niniLd_18pk = niniLd_18pk;
	}

	/**
	 * niniLd_18cdを取得します。
	 * @return niniLd_18cd
	 */
	public String getNiniLd_18cd() {
		return niniLd_18cd;
	}

	/**
	 * niniLd_18cd
	 * @param niniLd_18cdを設定します。
	 */
	public void setNiniLd_18cd(String niniLd_18cd) {
		this.niniLd_18cd = niniLd_18cd;
	}

	/**
	 * niniLd_19pkを取得します。
	 * @return niniLd_19pk
	 */
	public String getNiniLd_19pk() {
		return niniLd_19pk;
	}

	/**
	 * niniLd_19pk
	 * @param niniLd_19pkを設定します。
	 */
	public void setNiniLd_19pk(String niniLd_19pk) {
		this.niniLd_19pk = niniLd_19pk;
	}

	/**
	 * niniLd_19cdを取得します。
	 * @return niniLd_19cd
	 */
	public String getNiniLd_19cd() {
		return niniLd_19cd;
	}

	/**
	 * niniLd_19cd
	 * @param niniLd_19cdを設定します。
	 */
	public void setNiniLd_19cd(String niniLd_19cd) {
		this.niniLd_19cd = niniLd_19cd;
	}

	/**
	 * niniLd_20pkを取得します。
	 * @return niniLd_20pk
	 */
	public String getNiniLd_20pk() {
		return niniLd_20pk;
	}

	/**
	 * niniLd_20pk
	 * @param niniLd_20pkを設定します。
	 */
	public void setNiniLd_20pk(String niniLd_20pk) {
		this.niniLd_20pk = niniLd_20pk;
	}

	/**
	 * niniLd_20cdを取得します。
	 * @return niniLd_20cd
	 */
	public String getNiniLd_20cd() {
		return niniLd_20cd;
	}

	/**
	 * niniLd_20cd
	 * @param niniLd_20cdを設定します。
	 */
	public void setNiniLd_20cd(String niniLd_20cd) {
		this.niniLd_20cd = niniLd_20cd;
	}

	/**
	 * askstkgkを取得します。
	 * @return askstkgk
	 */
	public Long getAskstkgk() {
		return askstkgk;
	}

	/**
	 * askstkgk
	 * @param askstkgkを設定します。
	 */
	public void setAskstkgk(Long askstkgk) {
		this.askstkgk = askstkgk;
	}

	/**
	 * ksbokaを取得します。
	 * @return ksboka
	 */
	public Long getKsboka() {
		return ksboka;
	}

	/**
	 * ksboka
	 * @param ksbokaを設定します。
	 */
	public void setKsboka(Long ksboka) {
		this.ksboka = ksboka;
	}

	/**
	 * zkskkgkNenを取得します。
	 * @return zkskkgkNen
	 */
	public Long getZkskkgkNen() {
		return zkskkgkNen;
	}

	/**
	 * zkskkgkNen
	 * @param zkskkgkNenを設定します。
	 */
	public void setZkskkgkNen(Long zkskkgkNen) {
		this.zkskkgkNen = zkskkgkNen;
	}

	/**
	 * tkskkgkNenを取得します。
	 * @return tkskkgkNen
	 */
	public Long getTkskkgkNen() {
		return tkskkgkNen;
	}

	/**
	 * tkskkgkNen
	 * @param tkskkgkNenを設定します。
	 */
	public void setTkskkgkNen(Long tkskkgkNen) {
		this.tkskkgkNen = tkskkgkNen;
	}

	/**
	 * niniskkgkNenを取得します。
	 * @return niniskkgkNen
	 */
	public Long getNiniskkgkNen() {
		return niniskkgkNen;
	}

	/**
	 * niniskkgkNen
	 * @param niniskkgkNenを設定します。
	 */
	public void setNiniskkgkNen(Long niniskkgkNen) {
		this.niniskkgkNen = niniskkgkNen;
	}

	/**
	 * gensongkNenを取得します。
	 * @return gensongkNen
	 */
	public Long getGensongkNen() {
		return gensongkNen;
	}

	/**
	 * gensongkNen
	 * @param gensongkNenを設定します。
	 */
	public void setGensongkNen(Long gensongkNen) {
		this.gensongkNen = gensongkNen;
	}

	/**
	 * ftskkgk1を取得します。
	 * @return ftskkgk1
	 */
	public Long getFtskkgk1() {
		return ftskkgk1;
	}

	/**
	 * ftskkgk1
	 * @param ftskkgk1を設定します。
	 */
	public void setFtskkgk1(Long ftskkgk1) {
		this.ftskkgk1 = ftskkgk1;
	}

	/**
	 * ftskkgk2を取得します。
	 * @return ftskkgk2
	 */
	public Long getFtskkgk2() {
		return ftskkgk2;
	}

	/**
	 * ftskkgk2
	 * @param ftskkgk2を設定します。
	 */
	public void setFtskkgk2(Long ftskkgk2) {
		this.ftskkgk2 = ftskkgk2;
	}

	/**
	 * ftskkgk3を取得します。
	 * @return ftskkgk3
	 */
	public Long getFtskkgk3() {
		return ftskkgk3;
	}

	/**
	 * ftskkgk3
	 * @param ftskkgk3を設定します。
	 */
	public void setFtskkgk3(Long ftskkgk3) {
		this.ftskkgk3 = ftskkgk3;
	}

	/**
	 * ftskkgk4を取得します。
	 * @return ftskkgk4
	 */
	public Long getFtskkgk4() {
		return ftskkgk4;
	}

	/**
	 * ftskkgk4
	 * @param ftskkgk4を設定します。
	 */
	public void setFtskkgk4(Long ftskkgk4) {
		this.ftskkgk4 = ftskkgk4;
	}

	/**
	 * ftskkgk5を取得します。
	 * @return ftskkgk5
	 */
	public Long getFtskkgk5() {
		return ftskkgk5;
	}

	/**
	 * ftskkgk5
	 * @param ftskkgk5を設定します。
	 */
	public void setFtskkgk5(Long ftskkgk5) {
		this.ftskkgk5 = ftskkgk5;
	}

	/**
	 * ftskkgk6を取得します。
	 * @return ftskkgk6
	 */
	public Long getFtskkgk6() {
		return ftskkgk6;
	}

	/**
	 * ftskkgk6
	 * @param ftskkgk6を設定します。
	 */
	public void setFtskkgk6(Long ftskkgk6) {
		this.ftskkgk6 = ftskkgk6;
	}

	/**
	 * ftskkgk7を取得します。
	 * @return ftskkgk7
	 */
	public Long getFtskkgk7() {
		return ftskkgk7;
	}

	/**
	 * ftskkgk7
	 * @param ftskkgk7を設定します。
	 */
	public void setFtskkgk7(Long ftskkgk7) {
		this.ftskkgk7 = ftskkgk7;
	}

	/**
	 * ftskkgk8を取得します。
	 * @return ftskkgk8
	 */
	public Long getFtskkgk8() {
		return ftskkgk8;
	}

	/**
	 * ftskkgk8
	 * @param ftskkgk8を設定します。
	 */
	public void setFtskkgk8(Long ftskkgk8) {
		this.ftskkgk8 = ftskkgk8;
	}

	/**
	 * ftskkgk9を取得します。
	 * @return ftskkgk9
	 */
	public Long getFtskkgk9() {
		return ftskkgk9;
	}

	/**
	 * ftskkgk9
	 * @param ftskkgk9を設定します。
	 */
	public void setFtskkgk9(Long ftskkgk9) {
		this.ftskkgk9 = ftskkgk9;
	}

	/**
	 * ftskkgk10を取得します。
	 * @return ftskkgk10
	 */
	public Long getFtskkgk10() {
		return ftskkgk10;
	}

	/**
	 * ftskkgk10
	 * @param ftskkgk10を設定します。
	 */
	public void setFtskkgk10(Long ftskkgk10) {
		this.ftskkgk10 = ftskkgk10;
	}

	/**
	 * ftskkgk11を取得します。
	 * @return ftskkgk11
	 */
	public Long getFtskkgk11() {
		return ftskkgk11;
	}

	/**
	 * ftskkgk11
	 * @param ftskkgk11を設定します。
	 */
	public void setFtskkgk11(Long ftskkgk11) {
		this.ftskkgk11 = ftskkgk11;
	}

	/**
	 * ftskkgk12を取得します。
	 * @return ftskkgk12
	 */
	public Long getFtskkgk12() {
		return ftskkgk12;
	}

	/**
	 * ftskkgk12
	 * @param ftskkgk12を設定します。
	 */
	public void setFtskkgk12(Long ftskkgk12) {
		this.ftskkgk12 = ftskkgk12;
	}

	/**
	 * zkskkgk1を取得します。
	 * @return zkskkgk1
	 */
	public Long getZkskkgk1() {
		return zkskkgk1;
	}

	/**
	 * zkskkgk1
	 * @param zkskkgk1を設定します。
	 */
	public void setZkskkgk1(Long zkskkgk1) {
		this.zkskkgk1 = zkskkgk1;
	}

	/**
	 * zkskkgk2を取得します。
	 * @return zkskkgk2
	 */
	public Long getZkskkgk2() {
		return zkskkgk2;
	}

	/**
	 * zkskkgk2
	 * @param zkskkgk2を設定します。
	 */
	public void setZkskkgk2(Long zkskkgk2) {
		this.zkskkgk2 = zkskkgk2;
	}

	/**
	 * zkskkgk3を取得します。
	 * @return zkskkgk3
	 */
	public Long getZkskkgk3() {
		return zkskkgk3;
	}

	/**
	 * zkskkgk3
	 * @param zkskkgk3を設定します。
	 */
	public void setZkskkgk3(Long zkskkgk3) {
		this.zkskkgk3 = zkskkgk3;
	}

	/**
	 * zkskkgk4を取得します。
	 * @return zkskkgk4
	 */
	public Long getZkskkgk4() {
		return zkskkgk4;
	}

	/**
	 * zkskkgk4
	 * @param zkskkgk4を設定します。
	 */
	public void setZkskkgk4(Long zkskkgk4) {
		this.zkskkgk4 = zkskkgk4;
	}

	/**
	 * zkskkgk5を取得します。
	 * @return zkskkgk5
	 */
	public Long getZkskkgk5() {
		return zkskkgk5;
	}

	/**
	 * zkskkgk5
	 * @param zkskkgk5を設定します。
	 */
	public void setZkskkgk5(Long zkskkgk5) {
		this.zkskkgk5 = zkskkgk5;
	}

	/**
	 * zkskkgk6を取得します。
	 * @return zkskkgk6
	 */
	public Long getZkskkgk6() {
		return zkskkgk6;
	}

	/**
	 * zkskkgk6
	 * @param zkskkgk6を設定します。
	 */
	public void setZkskkgk6(Long zkskkgk6) {
		this.zkskkgk6 = zkskkgk6;
	}

	/**
	 * zkskkgk7を取得します。
	 * @return zkskkgk7
	 */
	public Long getZkskkgk7() {
		return zkskkgk7;
	}

	/**
	 * zkskkgk7
	 * @param zkskkgk7を設定します。
	 */
	public void setZkskkgk7(Long zkskkgk7) {
		this.zkskkgk7 = zkskkgk7;
	}

	/**
	 * zkskkgk8を取得します。
	 * @return zkskkgk8
	 */
	public Long getZkskkgk8() {
		return zkskkgk8;
	}

	/**
	 * zkskkgk8
	 * @param zkskkgk8を設定します。
	 */
	public void setZkskkgk8(Long zkskkgk8) {
		this.zkskkgk8 = zkskkgk8;
	}

	/**
	 * zkskkgk9を取得します。
	 * @return zkskkgk9
	 */
	public Long getZkskkgk9() {
		return zkskkgk9;
	}

	/**
	 * zkskkgk9
	 * @param zkskkgk9を設定します。
	 */
	public void setZkskkgk9(Long zkskkgk9) {
		this.zkskkgk9 = zkskkgk9;
	}

	/**
	 * zkskkgk10を取得します。
	 * @return zkskkgk10
	 */
	public Long getZkskkgk10() {
		return zkskkgk10;
	}

	/**
	 * zkskkgk10
	 * @param zkskkgk10を設定します。
	 */
	public void setZkskkgk10(Long zkskkgk10) {
		this.zkskkgk10 = zkskkgk10;
	}

	/**
	 * zkskkgk11を取得します。
	 * @return zkskkgk11
	 */
	public Long getZkskkgk11() {
		return zkskkgk11;
	}

	/**
	 * zkskkgk11
	 * @param zkskkgk11を設定します。
	 */
	public void setZkskkgk11(Long zkskkgk11) {
		this.zkskkgk11 = zkskkgk11;
	}

	/**
	 * zkskkgk12を取得します。
	 * @return zkskkgk12
	 */
	public Long getZkskkgk12() {
		return zkskkgk12;
	}

	/**
	 * zkskkgk12
	 * @param zkskkgk12を設定します。
	 */
	public void setZkskkgk12(Long zkskkgk12) {
		this.zkskkgk12 = zkskkgk12;
	}

	/**
	 * tkskkgk1を取得します。
	 * @return tkskkgk1
	 */
	public Long getTkskkgk1() {
		return tkskkgk1;
	}

	/**
	 * tkskkgk1
	 * @param tkskkgk1を設定します。
	 */
	public void setTkskkgk1(Long tkskkgk1) {
		this.tkskkgk1 = tkskkgk1;
	}

	/**
	 * tkskkgk2を取得します。
	 * @return tkskkgk2
	 */
	public Long getTkskkgk2() {
		return tkskkgk2;
	}

	/**
	 * tkskkgk2
	 * @param tkskkgk2を設定します。
	 */
	public void setTkskkgk2(Long tkskkgk2) {
		this.tkskkgk2 = tkskkgk2;
	}

	/**
	 * tkskkgk3を取得します。
	 * @return tkskkgk3
	 */
	public Long getTkskkgk3() {
		return tkskkgk3;
	}

	/**
	 * tkskkgk3
	 * @param tkskkgk3を設定します。
	 */
	public void setTkskkgk3(Long tkskkgk3) {
		this.tkskkgk3 = tkskkgk3;
	}

	/**
	 * tkskkgk4を取得します。
	 * @return tkskkgk4
	 */
	public Long getTkskkgk4() {
		return tkskkgk4;
	}

	/**
	 * tkskkgk4
	 * @param tkskkgk4を設定します。
	 */
	public void setTkskkgk4(Long tkskkgk4) {
		this.tkskkgk4 = tkskkgk4;
	}

	/**
	 * tkskkgk5を取得します。
	 * @return tkskkgk5
	 */
	public Long getTkskkgk5() {
		return tkskkgk5;
	}

	/**
	 * tkskkgk5
	 * @param tkskkgk5を設定します。
	 */
	public void setTkskkgk5(Long tkskkgk5) {
		this.tkskkgk5 = tkskkgk5;
	}

	/**
	 * tkskkgk6を取得します。
	 * @return tkskkgk6
	 */
	public Long getTkskkgk6() {
		return tkskkgk6;
	}

	/**
	 * tkskkgk6
	 * @param tkskkgk6を設定します。
	 */
	public void setTkskkgk6(Long tkskkgk6) {
		this.tkskkgk6 = tkskkgk6;
	}

	/**
	 * tkskkgk7を取得します。
	 * @return tkskkgk7
	 */
	public Long getTkskkgk7() {
		return tkskkgk7;
	}

	/**
	 * tkskkgk7
	 * @param tkskkgk7を設定します。
	 */
	public void setTkskkgk7(Long tkskkgk7) {
		this.tkskkgk7 = tkskkgk7;
	}

	/**
	 * tkskkgk8を取得します。
	 * @return tkskkgk8
	 */
	public Long getTkskkgk8() {
		return tkskkgk8;
	}

	/**
	 * tkskkgk8
	 * @param tkskkgk8を設定します。
	 */
	public void setTkskkgk8(Long tkskkgk8) {
		this.tkskkgk8 = tkskkgk8;
	}

	/**
	 * tkskkgk9を取得します。
	 * @return tkskkgk9
	 */
	public Long getTkskkgk9() {
		return tkskkgk9;
	}

	/**
	 * tkskkgk9
	 * @param tkskkgk9を設定します。
	 */
	public void setTkskkgk9(Long tkskkgk9) {
		this.tkskkgk9 = tkskkgk9;
	}

	/**
	 * tkskkgk10を取得します。
	 * @return tkskkgk10
	 */
	public Long getTkskkgk10() {
		return tkskkgk10;
	}

	/**
	 * tkskkgk10
	 * @param tkskkgk10を設定します。
	 */
	public void setTkskkgk10(Long tkskkgk10) {
		this.tkskkgk10 = tkskkgk10;
	}

	/**
	 * tkskkgk11を取得します。
	 * @return tkskkgk11
	 */
	public Long getTkskkgk11() {
		return tkskkgk11;
	}

	/**
	 * tkskkgk11
	 * @param tkskkgk11を設定します。
	 */
	public void setTkskkgk11(Long tkskkgk11) {
		this.tkskkgk11 = tkskkgk11;
	}

	/**
	 * tkskkgk12を取得します。
	 * @return tkskkgk12
	 */
	public Long getTkskkgk12() {
		return tkskkgk12;
	}

	/**
	 * tkskkgk12
	 * @param tkskkgk12を設定します。
	 */
	public void setTkskkgk12(Long tkskkgk12) {
		this.tkskkgk12 = tkskkgk12;
	}

	/**
	 * niniskkgk1を取得します。
	 * @return niniskkgk1
	 */
	public Long getNiniskkgk1() {
		return niniskkgk1;
	}

	/**
	 * niniskkgk1
	 * @param niniskkgk1を設定します。
	 */
	public void setNiniskkgk1(Long niniskkgk1) {
		this.niniskkgk1 = niniskkgk1;
	}

	/**
	 * niniskkgk2を取得します。
	 * @return niniskkgk2
	 */
	public Long getNiniskkgk2() {
		return niniskkgk2;
	}

	/**
	 * niniskkgk2
	 * @param niniskkgk2を設定します。
	 */
	public void setNiniskkgk2(Long niniskkgk2) {
		this.niniskkgk2 = niniskkgk2;
	}

	/**
	 * niniskkgk3を取得します。
	 * @return niniskkgk3
	 */
	public Long getNiniskkgk3() {
		return niniskkgk3;
	}

	/**
	 * niniskkgk3
	 * @param niniskkgk3を設定します。
	 */
	public void setNiniskkgk3(Long niniskkgk3) {
		this.niniskkgk3 = niniskkgk3;
	}

	/**
	 * niniskkgk4を取得します。
	 * @return niniskkgk4
	 */
	public Long getNiniskkgk4() {
		return niniskkgk4;
	}

	/**
	 * niniskkgk4
	 * @param niniskkgk4を設定します。
	 */
	public void setNiniskkgk4(Long niniskkgk4) {
		this.niniskkgk4 = niniskkgk4;
	}

	/**
	 * niniskkgk5を取得します。
	 * @return niniskkgk5
	 */
	public Long getNiniskkgk5() {
		return niniskkgk5;
	}

	/**
	 * niniskkgk5
	 * @param niniskkgk5を設定します。
	 */
	public void setNiniskkgk5(Long niniskkgk5) {
		this.niniskkgk5 = niniskkgk5;
	}

	/**
	 * niniskkgk6を取得します。
	 * @return niniskkgk6
	 */
	public Long getNiniskkgk6() {
		return niniskkgk6;
	}

	/**
	 * niniskkgk6
	 * @param niniskkgk6を設定します。
	 */
	public void setNiniskkgk6(Long niniskkgk6) {
		this.niniskkgk6 = niniskkgk6;
	}

	/**
	 * niniskkgk7を取得します。
	 * @return niniskkgk7
	 */
	public Long getNiniskkgk7() {
		return niniskkgk7;
	}

	/**
	 * niniskkgk7
	 * @param niniskkgk7を設定します。
	 */
	public void setNiniskkgk7(Long niniskkgk7) {
		this.niniskkgk7 = niniskkgk7;
	}

	/**
	 * niniskkgk8を取得します。
	 * @return niniskkgk8
	 */
	public Long getNiniskkgk8() {
		return niniskkgk8;
	}

	/**
	 * niniskkgk8
	 * @param niniskkgk8を設定します。
	 */
	public void setNiniskkgk8(Long niniskkgk8) {
		this.niniskkgk8 = niniskkgk8;
	}

	/**
	 * niniskkgk9を取得します。
	 * @return niniskkgk9
	 */
	public Long getNiniskkgk9() {
		return niniskkgk9;
	}

	/**
	 * niniskkgk9
	 * @param niniskkgk9を設定します。
	 */
	public void setNiniskkgk9(Long niniskkgk9) {
		this.niniskkgk9 = niniskkgk9;
	}

	/**
	 * niniskkgk10を取得します。
	 * @return niniskkgk10
	 */
	public Long getNiniskkgk10() {
		return niniskkgk10;
	}

	/**
	 * niniskkgk10
	 * @param niniskkgk10を設定します。
	 */
	public void setNiniskkgk10(Long niniskkgk10) {
		this.niniskkgk10 = niniskkgk10;
	}

	/**
	 * niniskkgk11を取得します。
	 * @return niniskkgk11
	 */
	public Long getNiniskkgk11() {
		return niniskkgk11;
	}

	/**
	 * niniskkgk11
	 * @param niniskkgk11を設定します。
	 */
	public void setNiniskkgk11(Long niniskkgk11) {
		this.niniskkgk11 = niniskkgk11;
	}

	/**
	 * niniskkgk12を取得します。
	 * @return niniskkgk12
	 */
	public Long getNiniskkgk12() {
		return niniskkgk12;
	}

	/**
	 * niniskkgk12
	 * @param niniskkgk12を設定します。
	 */
	public void setNiniskkgk12(Long niniskkgk12) {
		this.niniskkgk12 = niniskkgk12;
	}

	/**
	 * gensongk1を取得します。
	 * @return gensongk1
	 */
	public Long getGensongk1() {
		return gensongk1;
	}

	/**
	 * gensongk1
	 * @param gensongk1を設定します。
	 */
	public void setGensongk1(Long gensongk1) {
		this.gensongk1 = gensongk1;
	}

	/**
	 * gensongk2を取得します。
	 * @return gensongk2
	 */
	public Long getGensongk2() {
		return gensongk2;
	}

	/**
	 * gensongk2
	 * @param gensongk2を設定します。
	 */
	public void setGensongk2(Long gensongk2) {
		this.gensongk2 = gensongk2;
	}

	/**
	 * gensongk3を取得します。
	 * @return gensongk3
	 */
	public Long getGensongk3() {
		return gensongk3;
	}

	/**
	 * gensongk3
	 * @param gensongk3を設定します。
	 */
	public void setGensongk3(Long gensongk3) {
		this.gensongk3 = gensongk3;
	}

	/**
	 * gensongk4を取得します。
	 * @return gensongk4
	 */
	public Long getGensongk4() {
		return gensongk4;
	}

	/**
	 * gensongk4
	 * @param gensongk4を設定します。
	 */
	public void setGensongk4(Long gensongk4) {
		this.gensongk4 = gensongk4;
	}

	/**
	 * gensongk5を取得します。
	 * @return gensongk5
	 */
	public Long getGensongk5() {
		return gensongk5;
	}

	/**
	 * gensongk5
	 * @param gensongk5を設定します。
	 */
	public void setGensongk5(Long gensongk5) {
		this.gensongk5 = gensongk5;
	}

	/**
	 * gensongk6を取得します。
	 * @return gensongk6
	 */
	public Long getGensongk6() {
		return gensongk6;
	}

	/**
	 * gensongk6
	 * @param gensongk6を設定します。
	 */
	public void setGensongk6(Long gensongk6) {
		this.gensongk6 = gensongk6;
	}

	/**
	 * gensongk7を取得します。
	 * @return gensongk7
	 */
	public Long getGensongk7() {
		return gensongk7;
	}

	/**
	 * gensongk7
	 * @param gensongk7を設定します。
	 */
	public void setGensongk7(Long gensongk7) {
		this.gensongk7 = gensongk7;
	}

	/**
	 * gensongk8を取得します。
	 * @return gensongk8
	 */
	public Long getGensongk8() {
		return gensongk8;
	}

	/**
	 * gensongk8
	 * @param gensongk8を設定します。
	 */
	public void setGensongk8(Long gensongk8) {
		this.gensongk8 = gensongk8;
	}

	/**
	 * gensongk9を取得します。
	 * @return gensongk9
	 */
	public Long getGensongk9() {
		return gensongk9;
	}

	/**
	 * gensongk9
	 * @param gensongk9を設定します。
	 */
	public void setGensongk9(Long gensongk9) {
		this.gensongk9 = gensongk9;
	}

	/**
	 * gensongk10を取得します。
	 * @return gensongk10
	 */
	public Long getGensongk10() {
		return gensongk10;
	}

	/**
	 * gensongk10
	 * @param gensongk10を設定します。
	 */
	public void setGensongk10(Long gensongk10) {
		this.gensongk10 = gensongk10;
	}

	/**
	 * gensongk11を取得します。
	 * @return gensongk11
	 */
	public Long getGensongk11() {
		return gensongk11;
	}

	/**
	 * gensongk11
	 * @param gensongk11を設定します。
	 */
	public void setGensongk11(Long gensongk11) {
		this.gensongk11 = gensongk11;
	}

	/**
	 * gensongk12を取得します。
	 * @return gensongk12
	 */
	public Long getGensongk12() {
		return gensongk12;
	}

	/**
	 * gensongk12
	 * @param gensongk12を設定します。
	 */
	public void setGensongk12(Long gensongk12) {
		this.gensongk12 = gensongk12;
	}

	/**
	 * hfpkを取得します。
	 * @return hfpk
	 */
	public String getHfpk() {
		return hfpk;
	}

	/**
	 * hfpk
	 * @param hfpkを設定します。
	 */
	public void setHfpk(String hfpk) {
		this.hfpk = hfpk;
	}

	/**
	 * hfcdを取得します。
	 * @return hfcd
	 */
	public String getHfcd() {
		return hfcd;
	}

	/**
	 * hfcd
	 * @param hfcdを設定します。
	 */
	public void setHfcd(String hfcd) {
		this.hfcd = hfcd;
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


}
