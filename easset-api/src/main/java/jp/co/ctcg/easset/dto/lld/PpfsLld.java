/*===================================================================
 * ファイル名 : PpfsLld.java
 * 概要説明   : リース・レンタル情報照会_契約、物品共通
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-25 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.lld;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import jp.co.ctcg.easset.dto.asset.AssetSR;
import jp.co.ctcg.easset.dto.fld.PpfsFldSupport;
import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class PpfsLld implements Externalizable {
	private static final long serialVersionUID = 1L;

	/*******  契約情報   ********/
	private Long koyunoC; // 固有番号
	private String kaishacdC; // 会社コード
	private String kaishanmC; // 会社名称
	private String lakaishacdC; // リース会社コード
	private String torihikinmC; // 取引先名称
	private String soshiki1cdC; // 組織1コード
	private String soshiki1nmC; // 組織1名称
	private String soshiki2cdC; // 組織2コード
	private String soshiki2nmC; // 組織2名称
	private String soshiki3cdC; // 組織3コード
	private String soshiki3nmC; // 組織3名称
	private String soshiki4cdC; // 組織4コード
	private String soshiki4nmC; // 組織4名称
	private String shuruicdC; // 種類コード
	private String shuruinmC; // 種類名称
	private String kozocdC; // 構造用途コード
	private String kozonmC; // 構造用途名称
	private String bunruicdC; // 分類コード
	private String bunruinmC; // 分類名称
	private String niniLd_1cdC; // 任意(台帳)1コード
	private String niniLd_1nmC; // 任意(台帳)1名称
	private String niniLd_2cdC; // 任意(台帳)2コード
	private String niniLd_2nmC; // 任意(台帳)2名称
	private String niniLd_3cdC; // 任意(台帳)3コード
	private String niniLd_3nmC; // 任意(台帳)3名称
	private String niniLd_4cdC; // 任意(台帳)4コード
	private String niniLd_4nmC; // 任意(台帳)4名称
	private String niniLd_5cdC; // 任意(台帳)5コード
	private String niniLd_5nmC; // 任意(台帳)5名称
	private String niniLd_6cdC; // 任意(台帳)6コード
	private String niniLd_6nmC; // 任意(台帳)6名称
	private String niniLd_7cdC; // 任意(台帳)7コード
	private String niniLd_7nmC; // 任意(台帳)7名称
	private String niniLd_8cdC; // 任意(台帳)8コード
	private String niniLd_8nmC; // 任意(台帳)8名称
	private String niniLd_9cdC; // 任意(台帳)9コード
	private String niniLd_9nmC; // 任意(台帳)9名称
	private String niniLd_10cdC; // 任意(台帳)10コード
	private String niniLd_10nmC; // 任意(台帳)10名称
	private String niniLd_11cdC; // 任意(台帳)11コード
	private String niniLd_11nmC; // 任意(台帳)11名称
	private String niniLd_12cdC; // 任意(台帳)12コード
	private String niniLd_12nmC; // 任意(台帳)12名称
	private String niniLd_13cdC; // 任意(台帳)13コード
	private String niniLd_13nmC; // 任意(台帳)13名称
	private String niniLd_14cdC; // 任意(台帳)14コード
	private String niniLd_14nmC; // 任意(台帳)14名称
	private String niniLd_15cdC; // 任意(台帳)15コード
	private String niniLd_15nmC; // 任意(台帳)15名称
	private String niniLd_16cdC; // 任意(台帳)16コード
	private String niniLd_16nmC; // 任意(台帳)16名称
	private String niniLd_17cdC; // 任意(台帳)17コード
	private String niniLd_17nmC; // 任意(台帳)17名称
	private String niniLd_18cdC; // 任意(台帳)18コード
	private String niniLd_18nmC; // 任意(台帳)18名称
	private String niniLd_19cdC; // 任意(台帳)19コード
	private String niniLd_19nmC; // 任意(台帳)19名称
	private String niniLd_20cdC; // 任意(台帳)20コード
	private String niniLd_20nmC; // 任意(台帳)20名称
	private String kykkbnC; // 契約区分
	private Integer lakaisuC; // リース回数
	private String kyknmC; // 契約名称
	private String kyknmkC; // 契約名称カナ
	private String kyknoC; // 契約番号
	private String zenkyknoC; // 前回契約番号
	private String kykjotaikbnC; // 契約状態区分
	private String kykymdC; // 契約年月日
	private String lastymdC; // リース開始年月日
	private String lamanryoymdC; // リース満了年月日
	private Integer lakikanC; // リース期間
	private String shrhokbnC; // 支払方法区分
	private String shrmanryoymdC; // 支払満了年月日
	private String kaiykymdC; // 解約年月日
	private String shrcyclekbnC; // 支払サイクル区分
	private Long jitsushrkaisuC; // 支払残数
	private Integer shrkaisuC; // 支払回数
	private Long stkgksotogkC; // 取得価額相当額
	private Long shrlaryo1C; // 当期支払リース料
	private Long laryoTotalC; // リース料総額
	private Long kihonsparen1C; // 消費税総額
	private Long maehrlaryoC; // 前払リース料
	private String kintokbnC; // 支払均等払区分
	private Long shokailaryoC; // 初回リース料
	private Long laryoC; // リース料
	private Long mikeikalaryoPayC; // 未経過リース料(約定)
	private Long kihonsparen3C; // 未経過消費税額
	private Long shrlaryo2C; // 年額リース料
	private String latorihikikbnC; // リース取引判定区分
	private Long kojogk1C; // 控除額1
	private Long kojogk2C; // 控除額2
	private Long kojogk3C; // 控除額3
	private Long kojogk4C; // 控除額4
	private String biko1C; // 備考1
	private String biko2C; // 備考2
	private String kykringinoC; // 契約時稟議決裁番号
	private String kyktekiyoC; // 契約時摘要
	private String chukikbnC; // 注記区分
	private String bskeijokbnC; // B/S計上区分
	private Long zansotogkC; // 元本残高相当額(約定)
	private Long updkaisuC; // 更新回数
	private String updkaishacdC; // 更新会社コード
	private String updidC; // 更新者ID
	private String updymdC; // 更新年月日
	private String updtimeC; // 更新時間

	/*******  債務情報   ********/
	private Long zankahoshogkC; // 残価保証額
	private Long mitsumorikonyugkC; // 見積現金購入金額
	private Long keizaitainenC; // 経済的耐用年数
	private String juyoseikbnC; // 重要性判定
	private Double tekiyoritsuPayC; // 適用利子率
	private String kaikeishorikbnC; // 会計処理
	private String shrstymdC; // 支払開始日
	private String zenshrymdC; // 前回支払日
	private Double shohizeiritsuC; // 消費税率
	private Long shokailaryoKomiC; // 初回リース料(税込)
	private Long laryoKomiC; // 支払リース料(税込)
	private Long lastlaryoC; // 最終回リース料
	private Long lastlaryoKomiC; // 最終回リース料(税込)
	private Long hikiotoshistkaisuC; // 自動引落開始回数
	private Long maehrlammsuC; // 前払月数
	private Long zansotogkPayC; // 元本残高(約定)
	private Long zansotogkPayDecC; // 元本残高(約定)(確定)
	private Long mikeikalaryoPayDecC; // 未経過リース料(約定)(確定)
	private Long kihonsparen5C; // 未経過消費税額(約定)(確定)

	/*******  物品情報   ********/
	private Long koyunoA; // 固有番号
	private String kaishacdA; // 会社コード
	private String kaishanmA; // 会社名称
	private String lakaishacdA; // リース会社コード
	private String torihikinmA; // 取引先名称
	private String kyknoA; // 契約番号
	private String kyknmA; // 契約名称
	private String soshiki1cdA; // 組織1コード
	private String soshiki1nmA; // 組織1名称
	private String soshiki2cdA; // 組織2コード
	private String soshiki2nmA; // 組織2名称
	private String soshiki3cdA; // 組織3コード
	private String soshiki3nmA; // 組織3名称
	private String soshiki4cdA; // 組織4コード
	private String soshiki4nmA; // 組織4名称
	private String shuruicdA; // 種類コード
	private String shuruinmA; // 種類名称
	private String kozocdA; // 構造用途コード
	private String kozonmA; // 構造用途名称
	private String bunruicdA; // 分類コード
	private String bunruinmA; // 分類名称
	private String setchicdA; // 設置場所コード
	private String setchinmA; // 設置場所名称
	private String shuyoshisankbnA; // 主要資産区分
	private String groupcdA; // グループコード
	private String shinariocdA; // シナリオコード
	private String niniLd_1cdA; // 任意(台帳)1コード
	private String niniLd_1nmA; // 任意(台帳)1名称
	private String niniLd_2cdA; // 任意(台帳)2コード
	private String niniLd_2nmA; // 任意(台帳)2名称
	private String niniLd_3cdA; // 任意(台帳)3コード
	private String niniLd_3nmA; // 任意(台帳)3名称
	private String niniLd_4cdA; // 任意(台帳)4コード
	private String niniLd_4nmA; // 任意(台帳)4名称
	private String niniLd_5cdA; // 任意(台帳)5コード
	private String niniLd_5nmA; // 任意(台帳)5名称
	private String niniLd_6cdA; // 任意(台帳)6コード
	private String niniLd_6nmA; // 任意(台帳)6名称
	private String niniLd_7cdA; // 任意(台帳)7コード
	private String niniLd_7nmA; // 任意(台帳)7名称
	private String niniLd_8cdA; // 任意(台帳)8コード
	private String niniLd_8nmA; // 任意(台帳)8名称
	private String niniLd_9cdA; // 任意(台帳)9コード
	private String niniLd_9nmA; // 任意(台帳)9名称
	private String niniLd_10cdA; // 任意(台帳)10コード
	private String niniLd_10nmA; // 任意(台帳)10名称
	private String niniLd_11cdA; // 任意(台帳)11コード
	private String niniLd_11nmA; // 任意(台帳)11名称
	private String niniLd_12cdA; // 任意(台帳)12コード
	private String niniLd_12nmA; // 任意(台帳)12名称
	private String niniLd_13cdA; // 任意(台帳)13コード
	private String niniLd_13nmA; // 任意(台帳)13名称
	private String niniLd_14cdA; // 任意(台帳)14コード
	private String niniLd_14nmA; // 任意(台帳)14名称
	private String niniLd_15cdA; // 任意(台帳)15コード
	private String niniLd_15nmA; // 任意(台帳)15名称
	private String niniLd_16cdA; // 任意(台帳)16コード
	private String niniLd_16nmA; // 任意(台帳)16名称
	private String niniLd_17cdA; // 任意(台帳)17コード
	private String niniLd_17nmA; // 任意(台帳)17名称
	private String niniLd_18cdA; // 任意(台帳)18コード
	private String niniLd_18nmA; // 任意(台帳)18名称
	private String niniLd_19cdA; // 任意(台帳)19コード
	private String niniLd_19nmA; // 任意(台帳)19名称
	private String niniLd_20cdA; // 任意(台帳)20コード
	private String niniLd_20nmA; // 任意(台帳)20名称
	private String kykkbnA; // 契約区分
	private Long lakaisuA; // リース回数
	private String oyaA; // 資産番号・親
	private String edaA; // 資産番号・枝
	private String shisanjotaikbnA; // 資産状態区分
	private String shisannmcdA; // 資産名称コード
	private String shisannmA; // 資産名称
	private String shisannmkA; // 資産名称カナ
	private String konyucdA; // 購入先コード
	private Integer suryoA; // 数量
	private String taninmA; // 単位名称
	private String lastymdA; // リース開始年月日
	private String lamanryoymdA; // リース満了年月日
	private String kaiykymdA; // 解約年月日
	private Long bkngkA; // 物件価額
	private Long gensonruigkA; // 減損累計額
	private Long gensontorikuzushizanA; // 減損取崩残高
	private Long mikeikalaryoPayA; // 未経過リース料(約定)
	private Long zansotogkA; // 元本残高相当額
	private Long laryoTotalA; // リース料総額
	private Long maehrlaryoTorikuzushiZanA; // 前払リース料取崩残高
	private String knrbunruicdA; // 管理分類コード
	private String skkhihfcdA; // 償却費配賦コード
	private String skkhihfnmA; // 償却費配賦名称
	private String laryohfcdA; // リース料配賦コード
	private String laryohfnmA; // リース料配賦名称
	private String biko1A; // 備考1
	private String biko2A; // 備考2
	private String ikoyoteiumukbnA; // 移行予定有無区分
	private String shisanknrkbnA; // 資産管理区分
	private Long zansotogkPayA; // 元本残高相当額(約定)
	private Long updkaisuA; // 更新回数
	private String updkaishacdA; // 更新会社コード
	private String updidA; // 更新者ID
	private String updymdA; // 更新年月日
	private String updtimeA; // 更新時間

	/*******  資産別情報   ********/
	private Long ikkailaryoA; // 1回当リース料
	private Long zansotogkPayDecA; // 元本残高(約定)(確定)
	private Long mikeikalaryoPayDecA; // 未経過リース料(約定)(確定)
	private String lagensonymdA; // 減損評価日
	private Long ksgensonruigkA; // 期首減損累計額
	private Long ksgensontorikuzushizanA; // 期首減損取崩残高

	/*******  資産台帳情報   ********/
	private Long stkgkkA; // 資産計上額
	private Long toBokakA; // 帳簿価額
	private Long zanzongkkA; // 残存保証額


	private String shisanNumA; // 資産番号
	private Long rekikanA; // レンタル期間

	//	名称
	//	契約
	private String kykkbnCName;	//	契約区分名
	private String kykjotaikbnCName; //	契約状態区分
	private String kintokbnCName; // 均等払区分名
	private String latorihikikbnCName; // リース取引判定名
	private String bskeijokbnCName; // B/S計上区分
	private String chukikbnCName; // 注記名
	private String lakaishacdCName; // リース会社名
	private String latorihikikbnLRCName; // リース/レンタル区分
	private String shrcyclekbnCName; // 支払サイクル区分
	private String kaikeishorikbnCName; // 会計区分
	private String juyoseikbnCName; // 重要性判定区分

	private String kykymdCF; // 契約年月日
	private String lastymdCF; // リース開始年月日
	private String lamanryoymdCF; // リース満了年月日
	private String shrmanryoymdCF;// 支払満了年月日
	private String kaiykymdCF; // 解約年月日
	private String updymdCF; // 更新年月日
	private String shrstymdCF; // 支払開始日
	private String zenshrymdCF; // 前回支払日

	//	物件
	private String kykkbnAName; //	契約区分名
	private String shisanjotaikbnAName; // 物件状態区分
	private String shisanknrkbnAName; // 物件管理区分
	private String lastymdAF; // リース開始年月日
	private String lamanryoymdAF; // リース満了年月日
	private String kaiykymdAF; // 解約年月日
	private String updymdAF; // 更新年月日
	private String lagensonymdAF; // 減損評価日
	private String niniLd_15cdAF; // レンタル開始日
	private String niniLd_16cdAF; // レンタル終了日

	private List<AssetSR> assetList; // 情報機器一覧
	private List<LicenseSR> licenseList; // ﾗｲｾﾝｽ一覧
	private List<PpfsFldSupport> costSecHistList; // 経費負担部課履歴一覧

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {

		/*******  契約情報   ********/
		koyunoC = Function.getExternalLong(input.readObject());
		kaishacdC = (String)input.readObject();
		kaishanmC = (String)input.readObject();
		lakaishacdC = (String)input.readObject();
		torihikinmC = (String)input.readObject();
		soshiki1cdC = (String)input.readObject();
		soshiki1nmC = (String)input.readObject();
		soshiki2cdC = (String)input.readObject();
		soshiki2nmC = (String)input.readObject();
		soshiki3cdC = (String)input.readObject();
		soshiki3nmC = (String)input.readObject();
		soshiki4cdC = (String)input.readObject();
		soshiki4nmC = (String)input.readObject();
		shuruicdC = (String)input.readObject();
		shuruinmC = (String)input.readObject();
		kozocdC = (String)input.readObject();
		kozonmC = (String)input.readObject();
		bunruicdC = (String)input.readObject();
		bunruinmC = (String)input.readObject();
		niniLd_1cdC = (String)input.readObject();
		niniLd_1nmC = (String)input.readObject();
		niniLd_2cdC = (String)input.readObject();
		niniLd_2nmC = (String)input.readObject();
		niniLd_3cdC = (String)input.readObject();
		niniLd_3nmC = (String)input.readObject();
		niniLd_4cdC = (String)input.readObject();
		niniLd_4nmC = (String)input.readObject();
		niniLd_5cdC = (String)input.readObject();
		niniLd_5nmC = (String)input.readObject();
		niniLd_6cdC = (String)input.readObject();
		niniLd_6nmC = (String)input.readObject();
		niniLd_7cdC = (String)input.readObject();
		niniLd_7nmC = (String)input.readObject();
		niniLd_8cdC = (String)input.readObject();
		niniLd_8nmC = (String)input.readObject();
		niniLd_9cdC = (String)input.readObject();
		niniLd_9nmC = (String)input.readObject();
		niniLd_10cdC = (String)input.readObject();
		niniLd_10nmC = (String)input.readObject();
		niniLd_11cdC = (String)input.readObject();
		niniLd_11nmC = (String)input.readObject();
		niniLd_12cdC = (String)input.readObject();
		niniLd_12nmC = (String)input.readObject();
		niniLd_13cdC = (String)input.readObject();
		niniLd_13nmC = (String)input.readObject();
		niniLd_14cdC = (String)input.readObject();
		niniLd_14nmC = (String)input.readObject();
		niniLd_15cdC = (String)input.readObject();
		niniLd_15nmC = (String)input.readObject();
		niniLd_16cdC = (String)input.readObject();
		niniLd_16nmC = (String)input.readObject();
		niniLd_17cdC = (String)input.readObject();
		niniLd_17nmC = (String)input.readObject();
		niniLd_18cdC = (String)input.readObject();
		niniLd_18nmC = (String)input.readObject();
		niniLd_19cdC = (String)input.readObject();
		niniLd_19nmC = (String)input.readObject();
		niniLd_20cdC = (String)input.readObject();
		niniLd_20nmC = (String)input.readObject();
		kykkbnC = (String)input.readObject();
		lakaisuC = Function.getExternalInteger(input.readObject());
		kyknmC = (String)input.readObject();
		kyknmkC = (String)input.readObject();
		kyknoC = (String)input.readObject();
		zenkyknoC = (String)input.readObject();
		kykjotaikbnC = (String)input.readObject();
		kykymdC = (String)input.readObject();
		lastymdC = (String)input.readObject();
		lamanryoymdC = (String)input.readObject();
		lakikanC = Function.getExternalInteger(input.readObject());
		shrhokbnC = (String)input.readObject();
		shrmanryoymdC = (String)input.readObject();
		kaiykymdC = (String)input.readObject();
		shrcyclekbnC = (String)input.readObject();
		jitsushrkaisuC = Function.getExternalLong(input.readObject());
		shrkaisuC = Function.getExternalInteger(input.readObject());
		stkgksotogkC = Function.getExternalLong(input.readObject());
		shrlaryo1C = Function.getExternalLong(input.readObject());
		laryoTotalC = Function.getExternalLong(input.readObject());
		kihonsparen1C = Function.getExternalLong(input.readObject());
		maehrlaryoC = Function.getExternalLong(input.readObject());
		kintokbnC = (String)input.readObject();
		shokailaryoC = Function.getExternalLong(input.readObject());
		laryoC = Function.getExternalLong(input.readObject());
		mikeikalaryoPayC = Function.getExternalLong(input.readObject());
		kihonsparen3C = Function.getExternalLong(input.readObject());
		shrlaryo2C = Function.getExternalLong(input.readObject());
		latorihikikbnC = (String)input.readObject();
		kojogk1C = Function.getExternalLong(input.readObject());
		kojogk2C = Function.getExternalLong(input.readObject());
		kojogk3C = Function.getExternalLong(input.readObject());
		kojogk4C = Function.getExternalLong(input.readObject());
		biko1C = (String)input.readObject();
		biko2C = (String)input.readObject();
		kykringinoC = (String)input.readObject();
		kyktekiyoC = (String)input.readObject();
		chukikbnC = (String)input.readObject();
		bskeijokbnC = (String)input.readObject();
		zansotogkC = Function.getExternalLong(input.readObject());
		updkaisuC = Function.getExternalLong(input.readObject());
		updkaishacdC = (String)input.readObject();
		updidC = (String)input.readObject();
		updymdC = (String)input.readObject();
		updtimeC = (String)input.readObject();

		/*******  債務情報   ********/
		zankahoshogkC = Function.getExternalLong(input.readObject());
		mitsumorikonyugkC = Function.getExternalLong(input.readObject());
		keizaitainenC = Function.getExternalLong(input.readObject());
		juyoseikbnC = (String)input.readObject();
		tekiyoritsuPayC = Function.getExternalDouble(input.readObject());
		kaikeishorikbnC = (String)input.readObject();
		shrstymdC = (String)input.readObject();
		zenshrymdC = (String)input.readObject();
		shohizeiritsuC = Function.getExternalDouble(input.readObject());
		shokailaryoKomiC = Function.getExternalLong(input.readObject());
		laryoKomiC = Function.getExternalLong(input.readObject());
		lastlaryoC = Function.getExternalLong(input.readObject());
		lastlaryoKomiC = Function.getExternalLong(input.readObject());
		hikiotoshistkaisuC = Function.getExternalLong(input.readObject());
		maehrlammsuC = Function.getExternalLong(input.readObject());
		zansotogkPayC = Function.getExternalLong(input.readObject());
		zansotogkPayDecC = Function.getExternalLong(input.readObject());
		mikeikalaryoPayDecC = Function.getExternalLong(input.readObject());
		kihonsparen5C = Function.getExternalLong(input.readObject());

		/*******  物品情報   ********/
		koyunoA = Function.getExternalLong(input.readObject());
		kaishacdA = (String)input.readObject();
		kaishanmA = (String)input.readObject();
		lakaishacdA = (String)input.readObject();
		torihikinmA = (String)input.readObject();
		kyknoA = (String)input.readObject();
		kyknmA = (String)input.readObject();
		soshiki1cdA = (String)input.readObject();
		soshiki1nmA = (String)input.readObject();
		soshiki2cdA = (String)input.readObject();
		soshiki2nmA = (String)input.readObject();
		soshiki3cdA = (String)input.readObject();
		soshiki3nmA = (String)input.readObject();
		soshiki4cdA = (String)input.readObject();
		soshiki4nmA = (String)input.readObject();
		shuruicdA = (String)input.readObject();
		shuruinmA = (String)input.readObject();
		kozocdA = (String)input.readObject();
		kozonmA = (String)input.readObject();
		bunruicdA = (String)input.readObject();
		bunruinmA = (String)input.readObject();
		setchicdA = (String)input.readObject();
		setchinmA = (String)input.readObject();
		shuyoshisankbnA = (String)input.readObject();
		groupcdA = (String)input.readObject();
		shinariocdA = (String)input.readObject();
		niniLd_1cdA = (String)input.readObject();
		niniLd_1nmA = (String)input.readObject();
		niniLd_2cdA = (String)input.readObject();
		niniLd_2nmA = (String)input.readObject();
		niniLd_3cdA = (String)input.readObject();
		niniLd_3nmA = (String)input.readObject();
		niniLd_4cdA = (String)input.readObject();
		niniLd_4nmA = (String)input.readObject();
		niniLd_5cdA = (String)input.readObject();
		niniLd_5nmA = (String)input.readObject();
		niniLd_6cdA = (String)input.readObject();
		niniLd_6nmA = (String)input.readObject();
		niniLd_7cdA = (String)input.readObject();
		niniLd_7nmA = (String)input.readObject();
		niniLd_8cdA = (String)input.readObject();
		niniLd_8nmA = (String)input.readObject();
		niniLd_9cdA = (String)input.readObject();
		niniLd_9nmA = (String)input.readObject();
		niniLd_10cdA = (String)input.readObject();
		niniLd_10nmA = (String)input.readObject();
		niniLd_11cdA = (String)input.readObject();
		niniLd_11nmA = (String)input.readObject();
		niniLd_12cdA = (String)input.readObject();
		niniLd_12nmA = (String)input.readObject();
		niniLd_13cdA = (String)input.readObject();
		niniLd_13nmA = (String)input.readObject();
		niniLd_14cdA = (String)input.readObject();
		niniLd_14nmA = (String)input.readObject();
		niniLd_15cdA = (String)input.readObject();
		niniLd_15nmA = (String)input.readObject();
		niniLd_16cdA = (String)input.readObject();
		niniLd_16nmA = (String)input.readObject();
		niniLd_17cdA = (String)input.readObject();
		niniLd_17nmA = (String)input.readObject();
		niniLd_18cdA = (String)input.readObject();
		niniLd_18nmA = (String)input.readObject();
		niniLd_19cdA = (String)input.readObject();
		niniLd_19nmA = (String)input.readObject();
		niniLd_20cdA = (String)input.readObject();
		niniLd_20nmA = (String)input.readObject();
		kykkbnA = (String)input.readObject();
		lakaisuA = Function.getExternalLong(input.readObject());
		oyaA = (String)input.readObject();
		edaA = (String)input.readObject();
		shisanjotaikbnA = (String)input.readObject();
		shisannmcdA = (String)input.readObject();
		shisannmA = (String)input.readObject();
		shisannmkA = (String)input.readObject();
		konyucdA = (String)input.readObject();
		suryoA = Function.getExternalInteger(input.readObject());
		taninmA = (String)input.readObject();
		lastymdA = (String)input.readObject();
		lamanryoymdA = (String)input.readObject();
		kaiykymdA = (String)input.readObject();
		bkngkA = Function.getExternalLong(input.readObject());
		gensonruigkA = Function.getExternalLong(input.readObject());
		gensontorikuzushizanA = Function.getExternalLong(input.readObject());
		mikeikalaryoPayA = Function.getExternalLong(input.readObject());
		zansotogkA = Function.getExternalLong(input.readObject());
		laryoTotalA = Function.getExternalLong(input.readObject());
		maehrlaryoTorikuzushiZanA = Function.getExternalLong(input.readObject());
		knrbunruicdA = (String)input.readObject();
		skkhihfcdA = (String)input.readObject();
		skkhihfnmA = (String)input.readObject();
		laryohfcdA = (String)input.readObject();
		laryohfnmA = (String)input.readObject();
		biko1A = (String)input.readObject();
		biko2A = (String)input.readObject();
		ikoyoteiumukbnA = (String)input.readObject();
		shisanknrkbnA = (String)input.readObject();
		zansotogkPayA = Function.getExternalLong(input.readObject());
		updkaisuA = Function.getExternalLong(input.readObject());
		updkaishacdA = (String)input.readObject();
		updidA = (String)input.readObject();
		updymdA = (String)input.readObject();
		updtimeA = (String)input.readObject();

		/*******  資産別情報   ********/
		ikkailaryoA = Function.getExternalLong(input.readObject());
		zansotogkPayDecA = Function.getExternalLong(input.readObject());
		mikeikalaryoPayDecA = Function.getExternalLong(input.readObject());
		lagensonymdA = (String)input.readObject();
		ksgensonruigkA = Function.getExternalLong(input.readObject());
		ksgensontorikuzushizanA = Function.getExternalLong(input.readObject());

		/*******  資産情報   ********/
		stkgkkA = Function.getExternalLong(input.readObject());
		toBokakA = Function.getExternalLong(input.readObject());
		zanzongkkA = Function.getExternalLong(input.readObject());


		shisanNumA = (String)input.readObject();
		rekikanA = Function.getExternalLong(input.readObject());

		//	名称
		//	契約
		kykkbnCName = (String)input.readObject();
		kykjotaikbnCName = (String)input.readObject();
		kintokbnCName = (String)input.readObject();
		latorihikikbnCName = (String)input.readObject();
		bskeijokbnCName = (String)input.readObject();
		chukikbnCName = (String)input.readObject();
		lakaishacdCName = (String)input.readObject();
		latorihikikbnLRCName = (String)input.readObject();
		shrcyclekbnCName = (String)input.readObject();
		kaikeishorikbnCName = (String)input.readObject();
		juyoseikbnCName = (String)input.readObject();

		kykymdCF = (String)input.readObject();
		lastymdCF = (String)input.readObject();
		lamanryoymdCF = (String)input.readObject();
		shrmanryoymdCF = (String)input.readObject();
		kaiykymdCF = (String)input.readObject();
		updymdCF = (String)input.readObject();
		shrstymdCF = (String)input.readObject();
		zenshrymdCF = (String)input.readObject();

		//	物件
		kykkbnAName = (String)input.readObject();
		shisanjotaikbnAName = (String)input.readObject();
		shisanknrkbnAName = (String)input.readObject();
		lastymdAF = (String)input.readObject();
		lamanryoymdAF = (String)input.readObject();
		kaiykymdAF = (String)input.readObject();
		updymdAF = (String)input.readObject();
		lagensonymdAF = (String)input.readObject();
		niniLd_15cdAF = (String)input.readObject();
		niniLd_16cdAF = (String)input.readObject();

		assetList = (List<AssetSR>)input.readObject();
		licenseList = (List<LicenseSR>)input.readObject();
		costSecHistList = (List<PpfsFldSupport>)input.readObject();

	}

	public void writeExternal(ObjectOutput output) throws IOException {

		/*******  契約情報   ********/
		output.writeObject(koyunoC);
		output.writeObject(kaishacdC);
		output.writeObject(kaishanmC);
		output.writeObject(lakaishacdC);
		output.writeObject(torihikinmC);
		output.writeObject(soshiki1cdC);
		output.writeObject(soshiki1nmC);
		output.writeObject(soshiki2cdC);
		output.writeObject(soshiki2nmC);
		output.writeObject(soshiki3cdC);
		output.writeObject(soshiki3nmC);
		output.writeObject(soshiki4cdC);
		output.writeObject(soshiki4nmC);
		output.writeObject(shuruicdC);
		output.writeObject(shuruinmC);
		output.writeObject(kozocdC);
		output.writeObject(kozonmC);
		output.writeObject(bunruicdC);
		output.writeObject(bunruinmC);
		output.writeObject(niniLd_1cdC);
		output.writeObject(niniLd_1nmC);
		output.writeObject(niniLd_2cdC);
		output.writeObject(niniLd_2nmC);
		output.writeObject(niniLd_3cdC);
		output.writeObject(niniLd_3nmC);
		output.writeObject(niniLd_4cdC);
		output.writeObject(niniLd_4nmC);
		output.writeObject(niniLd_5cdC);
		output.writeObject(niniLd_5nmC);
		output.writeObject(niniLd_6cdC);
		output.writeObject(niniLd_6nmC);
		output.writeObject(niniLd_7cdC);
		output.writeObject(niniLd_7nmC);
		output.writeObject(niniLd_8cdC);
		output.writeObject(niniLd_8nmC);
		output.writeObject(niniLd_9cdC);
		output.writeObject(niniLd_9nmC);
		output.writeObject(niniLd_10cdC);
		output.writeObject(niniLd_10nmC);
		output.writeObject(niniLd_11cdC);
		output.writeObject(niniLd_11nmC);
		output.writeObject(niniLd_12cdC);
		output.writeObject(niniLd_12nmC);
		output.writeObject(niniLd_13cdC);
		output.writeObject(niniLd_13nmC);
		output.writeObject(niniLd_14cdC);
		output.writeObject(niniLd_14nmC);
		output.writeObject(niniLd_15cdC);
		output.writeObject(niniLd_15nmC);
		output.writeObject(niniLd_16cdC);
		output.writeObject(niniLd_16nmC);
		output.writeObject(niniLd_17cdC);
		output.writeObject(niniLd_17nmC);
		output.writeObject(niniLd_18cdC);
		output.writeObject(niniLd_18nmC);
		output.writeObject(niniLd_19cdC);
		output.writeObject(niniLd_19nmC);
		output.writeObject(niniLd_20cdC);
		output.writeObject(niniLd_20nmC);
		output.writeObject(kykkbnC);
		output.writeObject(lakaisuC);
		output.writeObject(kyknmC);
		output.writeObject(kyknmkC);
		output.writeObject(kyknoC);
		output.writeObject(zenkyknoC);
		output.writeObject(kykjotaikbnC);
		output.writeObject(kykymdC);
		output.writeObject(lastymdC);
		output.writeObject(lamanryoymdC);
		output.writeObject(lakikanC);
		output.writeObject(shrhokbnC);
		output.writeObject(shrmanryoymdC);
		output.writeObject(kaiykymdC);
		output.writeObject(shrcyclekbnC);
		output.writeObject(jitsushrkaisuC);
		output.writeObject(shrkaisuC);
		output.writeObject(stkgksotogkC);
		output.writeObject(shrlaryo1C);
		output.writeObject(laryoTotalC);
		output.writeObject(kihonsparen1C);
		output.writeObject(maehrlaryoC);
		output.writeObject(kintokbnC);
		output.writeObject(shokailaryoC);
		output.writeObject(laryoC);
		output.writeObject(mikeikalaryoPayC);
		output.writeObject(kihonsparen3C);
		output.writeObject(shrlaryo2C);
		output.writeObject(latorihikikbnC);
		output.writeObject(kojogk1C);
		output.writeObject(kojogk2C);
		output.writeObject(kojogk3C);
		output.writeObject(kojogk4C);
		output.writeObject(biko1C);
		output.writeObject(biko2C);
		output.writeObject(kykringinoC);
		output.writeObject(kyktekiyoC);
		output.writeObject(chukikbnC);
		output.writeObject(bskeijokbnC);
		output.writeObject(zansotogkC);
		output.writeObject(updkaisuC);
		output.writeObject(updkaishacdC);
		output.writeObject(updidC);
		output.writeObject(updymdC);
		output.writeObject(updtimeC);

		/*******  債務別情報   ********/
		output.writeObject(zankahoshogkC);
		output.writeObject(mitsumorikonyugkC);
		output.writeObject(keizaitainenC);
		output.writeObject(juyoseikbnC);
		output.writeObject(tekiyoritsuPayC);
		output.writeObject(kaikeishorikbnC);
		output.writeObject(shrstymdC);
		output.writeObject(zenshrymdC);
		output.writeObject(shohizeiritsuC);
		output.writeObject(shokailaryoKomiC);
		output.writeObject(laryoKomiC);
		output.writeObject(lastlaryoC);
		output.writeObject(lastlaryoKomiC);
		output.writeObject(hikiotoshistkaisuC);
		output.writeObject(maehrlammsuC);
		output.writeObject(zansotogkPayC);
		output.writeObject(zansotogkPayDecC);
		output.writeObject(mikeikalaryoPayDecC);
		output.writeObject(kihonsparen5C);

		/*******  物品情報   ********/
		output.writeObject(koyunoA);
		output.writeObject(kaishacdA);
		output.writeObject(kaishanmA);
		output.writeObject(lakaishacdA);
		output.writeObject(torihikinmA);
		output.writeObject(kyknoA);
		output.writeObject(kyknmA);
		output.writeObject(soshiki1cdA);
		output.writeObject(soshiki1nmA);
		output.writeObject(soshiki2cdA);
		output.writeObject(soshiki2nmA);
		output.writeObject(soshiki3cdA);
		output.writeObject(soshiki3nmA);
		output.writeObject(soshiki4cdA);
		output.writeObject(soshiki4nmA);
		output.writeObject(shuruicdA);
		output.writeObject(shuruinmA);
		output.writeObject(kozocdA);
		output.writeObject(kozonmA);
		output.writeObject(bunruicdA);
		output.writeObject(bunruinmA);
		output.writeObject(setchicdA);
		output.writeObject(setchinmA);
		output.writeObject(shuyoshisankbnA);
		output.writeObject(groupcdA);
		output.writeObject(shinariocdA);
		output.writeObject(niniLd_1cdA);
		output.writeObject(niniLd_1nmA);
		output.writeObject(niniLd_2cdA);
		output.writeObject(niniLd_2nmA);
		output.writeObject(niniLd_3cdA);
		output.writeObject(niniLd_3nmA);
		output.writeObject(niniLd_4cdA);
		output.writeObject(niniLd_4nmA);
		output.writeObject(niniLd_5cdA);
		output.writeObject(niniLd_5nmA);
		output.writeObject(niniLd_6cdA);
		output.writeObject(niniLd_6nmA);
		output.writeObject(niniLd_7cdA);
		output.writeObject(niniLd_7nmA);
		output.writeObject(niniLd_8cdA);
		output.writeObject(niniLd_8nmA);
		output.writeObject(niniLd_9cdA);
		output.writeObject(niniLd_9nmA);
		output.writeObject(niniLd_10cdA);
		output.writeObject(niniLd_10nmA);
		output.writeObject(niniLd_11cdA);
		output.writeObject(niniLd_11nmA);
		output.writeObject(niniLd_12cdA);
		output.writeObject(niniLd_12nmA);
		output.writeObject(niniLd_13cdA);
		output.writeObject(niniLd_13nmA);
		output.writeObject(niniLd_14cdA);
		output.writeObject(niniLd_14nmA);
		output.writeObject(niniLd_15cdA);
		output.writeObject(niniLd_15nmA);
		output.writeObject(niniLd_16cdA);
		output.writeObject(niniLd_16nmA);
		output.writeObject(niniLd_17cdA);
		output.writeObject(niniLd_17nmA);
		output.writeObject(niniLd_18cdA);
		output.writeObject(niniLd_18nmA);
		output.writeObject(niniLd_19cdA);
		output.writeObject(niniLd_19nmA);
		output.writeObject(niniLd_20cdA);
		output.writeObject(niniLd_20nmA);
		output.writeObject(kykkbnA);
		output.writeObject(lakaisuA);
		output.writeObject(oyaA);
		output.writeObject(edaA);
		output.writeObject(shisanjotaikbnA);
		output.writeObject(shisannmcdA);
		output.writeObject(shisannmA);
		output.writeObject(shisannmkA);
		output.writeObject(konyucdA);
		output.writeObject(suryoA);
		output.writeObject(taninmA);
		output.writeObject(lastymdA);
		output.writeObject(lamanryoymdA);
		output.writeObject(kaiykymdA);
		output.writeObject(bkngkA);
		output.writeObject(gensonruigkA);
		output.writeObject(gensontorikuzushizanA);
		output.writeObject(mikeikalaryoPayA);
		output.writeObject(zansotogkA);
		output.writeObject(laryoTotalA);
		output.writeObject(maehrlaryoTorikuzushiZanA);
		output.writeObject(knrbunruicdA);
		output.writeObject(skkhihfcdA);
		output.writeObject(skkhihfnmA);
		output.writeObject(laryohfcdA);
		output.writeObject(laryohfnmA);
		output.writeObject(biko1A);
		output.writeObject(biko2A);
		output.writeObject(ikoyoteiumukbnA);
		output.writeObject(shisanknrkbnA);
		output.writeObject(zansotogkPayA);
		output.writeObject(updkaisuA);
		output.writeObject(updkaishacdA);
		output.writeObject(updidA);
		output.writeObject(updymdA);
		output.writeObject(updtimeA);

		/*******  資産別情報   ********/
		output.writeObject(ikkailaryoA);
		output.writeObject(zansotogkPayDecA);
		output.writeObject(mikeikalaryoPayDecA);
		output.writeObject(lagensonymdA);
		output.writeObject(ksgensonruigkA);
		output.writeObject(ksgensontorikuzushizanA);

		/*******  資産情報   ********/
		output.writeObject(stkgkkA);
		output.writeObject(toBokakA);
		output.writeObject(zanzongkkA);


		output.writeObject(shisanNumA);
		output.writeObject(rekikanA);

		//	名称
		//	契約
		output.writeObject(kykkbnCName);
		output.writeObject(kykjotaikbnCName);
		output.writeObject(kintokbnCName);
		output.writeObject(latorihikikbnCName);
		output.writeObject(bskeijokbnCName);
		output.writeObject(chukikbnCName);
		output.writeObject(lakaishacdCName);
		output.writeObject(latorihikikbnLRCName);
		output.writeObject(shrcyclekbnCName);
		output.writeObject(kaikeishorikbnCName);
		output.writeObject(juyoseikbnCName);

		output.writeObject(kykymdCF);
		output.writeObject(lastymdCF);
		output.writeObject(lamanryoymdCF);
		output.writeObject(shrmanryoymdCF);
		output.writeObject(kaiykymdCF);
		output.writeObject(updymdCF);
		output.writeObject(shrstymdCF);
		output.writeObject(zenshrymdCF);

		//	物件
		output.writeObject(kykkbnAName);
		output.writeObject(shisanjotaikbnAName);
		output.writeObject(shisanknrkbnAName);
		output.writeObject(lastymdAF);
		output.writeObject(lamanryoymdAF);
		output.writeObject(kaiykymdAF);
		output.writeObject(updymdAF);
		output.writeObject(lagensonymdAF);
		output.writeObject(niniLd_15cdAF);
		output.writeObject(niniLd_16cdAF);

		output.writeObject(assetList);
		output.writeObject(licenseList);
		output.writeObject(costSecHistList);


	}

	/**
	 * koyunoCを取得します。
	 * @return koyunoC
	 */
	public Long getKoyunoC() {
		return koyunoC;
	}

	/**
	 * koyunoC
	 * @param koyunoCを設定します。
	 */
	public void setKoyunoC(Long koyunoC) {
		this.koyunoC = koyunoC;
	}

	/**
	 * kaishacdCを取得します。
	 * @return kaishacdC
	 */
	public String getKaishacdC() {
		return kaishacdC;
	}

	/**
	 * kaishacdC
	 * @param kaishacdCを設定します。
	 */
	public void setKaishacdC(String kaishacdC) {
		this.kaishacdC = kaishacdC;
	}

	/**
	 * kaishanmCを取得します。
	 * @return kaishanmC
	 */
	public String getKaishanmC() {
		return kaishanmC;
	}

	/**
	 * kaishanmC
	 * @param kaishanmCを設定します。
	 */
	public void setKaishanmC(String kaishanmC) {
		this.kaishanmC = kaishanmC;
	}

	/**
	 * lakaishacdCを取得します。
	 * @return lakaishacdC
	 */
	public String getLakaishacdC() {
		return lakaishacdC;
	}

	/**
	 * lakaishacdC
	 * @param lakaishacdCを設定します。
	 */
	public void setLakaishacdC(String lakaishacdC) {
		this.lakaishacdC = lakaishacdC;
	}

	/**
	 * torihikinmCを取得します。
	 * @return torihikinmC
	 */
	public String getTorihikinmC() {
		return torihikinmC;
	}

	/**
	 * torihikinmC
	 * @param torihikinmCを設定します。
	 */
	public void setTorihikinmC(String torihikinmC) {
		this.torihikinmC = torihikinmC;
	}

	/**
	 * soshiki1cdCを取得します。
	 * @return soshiki1cdC
	 */
	public String getSoshiki1cdC() {
		return soshiki1cdC;
	}

	/**
	 * soshiki1cdC
	 * @param soshiki1cdCを設定します。
	 */
	public void setSoshiki1cdC(String soshiki1cdC) {
		this.soshiki1cdC = soshiki1cdC;
	}

	/**
	 * soshiki1nmCを取得します。
	 * @return soshiki1nmC
	 */
	public String getSoshiki1nmC() {
		return soshiki1nmC;
	}

	/**
	 * soshiki1nmC
	 * @param soshiki1nmCを設定します。
	 */
	public void setSoshiki1nmC(String soshiki1nmC) {
		this.soshiki1nmC = soshiki1nmC;
	}

	/**
	 * soshiki2cdCを取得します。
	 * @return soshiki2cdC
	 */
	public String getSoshiki2cdC() {
		return soshiki2cdC;
	}

	/**
	 * soshiki2cdC
	 * @param soshiki2cdCを設定します。
	 */
	public void setSoshiki2cdC(String soshiki2cdC) {
		this.soshiki2cdC = soshiki2cdC;
	}

	/**
	 * soshiki2nmCを取得します。
	 * @return soshiki2nmC
	 */
	public String getSoshiki2nmC() {
		return soshiki2nmC;
	}

	/**
	 * soshiki2nmC
	 * @param soshiki2nmCを設定します。
	 */
	public void setSoshiki2nmC(String soshiki2nmC) {
		this.soshiki2nmC = soshiki2nmC;
	}

	/**
	 * soshiki3cdCを取得します。
	 * @return soshiki3cdC
	 */
	public String getSoshiki3cdC() {
		return soshiki3cdC;
	}

	/**
	 * soshiki3cdC
	 * @param soshiki3cdCを設定します。
	 */
	public void setSoshiki3cdC(String soshiki3cdC) {
		this.soshiki3cdC = soshiki3cdC;
	}

	/**
	 * soshiki3nmCを取得します。
	 * @return soshiki3nmC
	 */
	public String getSoshiki3nmC() {
		return soshiki3nmC;
	}

	/**
	 * soshiki3nmC
	 * @param soshiki3nmCを設定します。
	 */
	public void setSoshiki3nmC(String soshiki3nmC) {
		this.soshiki3nmC = soshiki3nmC;
	}

	/**
	 * soshiki4cdCを取得します。
	 * @return soshiki4cdC
	 */
	public String getSoshiki4cdC() {
		return soshiki4cdC;
	}

	/**
	 * soshiki4cdC
	 * @param soshiki4cdCを設定します。
	 */
	public void setSoshiki4cdC(String soshiki4cdC) {
		this.soshiki4cdC = soshiki4cdC;
	}

	/**
	 * soshiki4nmCを取得します。
	 * @return soshiki4nmC
	 */
	public String getSoshiki4nmC() {
		return soshiki4nmC;
	}

	/**
	 * soshiki4nmC
	 * @param soshiki4nmCを設定します。
	 */
	public void setSoshiki4nmC(String soshiki4nmC) {
		this.soshiki4nmC = soshiki4nmC;
	}

	/**
	 * shuruicdCを取得します。
	 * @return shuruicdC
	 */
	public String getShuruicdC() {
		return shuruicdC;
	}

	/**
	 * shuruicdC
	 * @param shuruicdCを設定します。
	 */
	public void setShuruicdC(String shuruicdC) {
		this.shuruicdC = shuruicdC;
	}

	/**
	 * shuruinmCを取得します。
	 * @return shuruinmC
	 */
	public String getShuruinmC() {
		return shuruinmC;
	}

	/**
	 * shuruinmC
	 * @param shuruinmCを設定します。
	 */
	public void setShuruinmC(String shuruinmC) {
		this.shuruinmC = shuruinmC;
	}

	/**
	 * kozocdCを取得します。
	 * @return kozocdC
	 */
	public String getKozocdC() {
		return kozocdC;
	}

	/**
	 * kozocdC
	 * @param kozocdCを設定します。
	 */
	public void setKozocdC(String kozocdC) {
		this.kozocdC = kozocdC;
	}

	/**
	 * kozonmCを取得します。
	 * @return kozonmC
	 */
	public String getKozonmC() {
		return kozonmC;
	}

	/**
	 * kozonmC
	 * @param kozonmCを設定します。
	 */
	public void setKozonmC(String kozonmC) {
		this.kozonmC = kozonmC;
	}

	/**
	 * bunruicdCを取得します。
	 * @return bunruicdC
	 */
	public String getBunruicdC() {
		return bunruicdC;
	}

	/**
	 * bunruicdC
	 * @param bunruicdCを設定します。
	 */
	public void setBunruicdC(String bunruicdC) {
		this.bunruicdC = bunruicdC;
	}

	/**
	 * bunruinmCを取得します。
	 * @return bunruinmC
	 */
	public String getBunruinmC() {
		return bunruinmC;
	}

	/**
	 * bunruinmC
	 * @param bunruinmCを設定します。
	 */
	public void setBunruinmC(String bunruinmC) {
		this.bunruinmC = bunruinmC;
	}

	/**
	 * niniLd_1cdCを取得します。
	 * @return niniLd_1cdC
	 */
	public String getNiniLd_1cdC() {
		return niniLd_1cdC;
	}

	/**
	 * niniLd_1cdC
	 * @param niniLd_1cdCを設定します。
	 */
	public void setNiniLd_1cdC(String niniLd_1cdC) {
		this.niniLd_1cdC = niniLd_1cdC;
	}

	/**
	 * niniLd_1nmCを取得します。
	 * @return niniLd_1nmC
	 */
	public String getNiniLd_1nmC() {
		return niniLd_1nmC;
	}

	/**
	 * niniLd_1nmC
	 * @param niniLd_1nmCを設定します。
	 */
	public void setNiniLd_1nmC(String niniLd_1nmC) {
		this.niniLd_1nmC = niniLd_1nmC;
	}

	/**
	 * niniLd_2cdCを取得します。
	 * @return niniLd_2cdC
	 */
	public String getNiniLd_2cdC() {
		return niniLd_2cdC;
	}

	/**
	 * niniLd_2cdC
	 * @param niniLd_2cdCを設定します。
	 */
	public void setNiniLd_2cdC(String niniLd_2cdC) {
		this.niniLd_2cdC = niniLd_2cdC;
	}

	/**
	 * niniLd_2nmCを取得します。
	 * @return niniLd_2nmC
	 */
	public String getNiniLd_2nmC() {
		return niniLd_2nmC;
	}

	/**
	 * niniLd_2nmC
	 * @param niniLd_2nmCを設定します。
	 */
	public void setNiniLd_2nmC(String niniLd_2nmC) {
		this.niniLd_2nmC = niniLd_2nmC;
	}

	/**
	 * niniLd_3cdCを取得します。
	 * @return niniLd_3cdC
	 */
	public String getNiniLd_3cdC() {
		return niniLd_3cdC;
	}

	/**
	 * niniLd_3cdC
	 * @param niniLd_3cdCを設定します。
	 */
	public void setNiniLd_3cdC(String niniLd_3cdC) {
		this.niniLd_3cdC = niniLd_3cdC;
	}

	/**
	 * niniLd_3nmCを取得します。
	 * @return niniLd_3nmC
	 */
	public String getNiniLd_3nmC() {
		return niniLd_3nmC;
	}

	/**
	 * niniLd_3nmC
	 * @param niniLd_3nmCを設定します。
	 */
	public void setNiniLd_3nmC(String niniLd_3nmC) {
		this.niniLd_3nmC = niniLd_3nmC;
	}

	/**
	 * niniLd_4cdCを取得します。
	 * @return niniLd_4cdC
	 */
	public String getNiniLd_4cdC() {
		return niniLd_4cdC;
	}

	/**
	 * niniLd_4cdC
	 * @param niniLd_4cdCを設定します。
	 */
	public void setNiniLd_4cdC(String niniLd_4cdC) {
		this.niniLd_4cdC = niniLd_4cdC;
	}

	/**
	 * niniLd_4nmCを取得します。
	 * @return niniLd_4nmC
	 */
	public String getNiniLd_4nmC() {
		return niniLd_4nmC;
	}

	/**
	 * niniLd_4nmC
	 * @param niniLd_4nmCを設定します。
	 */
	public void setNiniLd_4nmC(String niniLd_4nmC) {
		this.niniLd_4nmC = niniLd_4nmC;
	}

	/**
	 * niniLd_5cdCを取得します。
	 * @return niniLd_5cdC
	 */
	public String getNiniLd_5cdC() {
		return niniLd_5cdC;
	}

	/**
	 * niniLd_5cdC
	 * @param niniLd_5cdCを設定します。
	 */
	public void setNiniLd_5cdC(String niniLd_5cdC) {
		this.niniLd_5cdC = niniLd_5cdC;
	}

	/**
	 * niniLd_5nmCを取得します。
	 * @return niniLd_5nmC
	 */
	public String getNiniLd_5nmC() {
		return niniLd_5nmC;
	}

	/**
	 * niniLd_5nmC
	 * @param niniLd_5nmCを設定します。
	 */
	public void setNiniLd_5nmC(String niniLd_5nmC) {
		this.niniLd_5nmC = niniLd_5nmC;
	}

	/**
	 * niniLd_6cdCを取得します。
	 * @return niniLd_6cdC
	 */
	public String getNiniLd_6cdC() {
		return niniLd_6cdC;
	}

	/**
	 * niniLd_6cdC
	 * @param niniLd_6cdCを設定します。
	 */
	public void setNiniLd_6cdC(String niniLd_6cdC) {
		this.niniLd_6cdC = niniLd_6cdC;
	}

	/**
	 * niniLd_6nmCを取得します。
	 * @return niniLd_6nmC
	 */
	public String getNiniLd_6nmC() {
		return niniLd_6nmC;
	}

	/**
	 * niniLd_6nmC
	 * @param niniLd_6nmCを設定します。
	 */
	public void setNiniLd_6nmC(String niniLd_6nmC) {
		this.niniLd_6nmC = niniLd_6nmC;
	}

	/**
	 * niniLd_7cdCを取得します。
	 * @return niniLd_7cdC
	 */
	public String getNiniLd_7cdC() {
		return niniLd_7cdC;
	}

	/**
	 * niniLd_7cdC
	 * @param niniLd_7cdCを設定します。
	 */
	public void setNiniLd_7cdC(String niniLd_7cdC) {
		this.niniLd_7cdC = niniLd_7cdC;
	}

	/**
	 * niniLd_7nmCを取得します。
	 * @return niniLd_7nmC
	 */
	public String getNiniLd_7nmC() {
		return niniLd_7nmC;
	}

	/**
	 * niniLd_7nmC
	 * @param niniLd_7nmCを設定します。
	 */
	public void setNiniLd_7nmC(String niniLd_7nmC) {
		this.niniLd_7nmC = niniLd_7nmC;
	}

	/**
	 * niniLd_8cdCを取得します。
	 * @return niniLd_8cdC
	 */
	public String getNiniLd_8cdC() {
		return niniLd_8cdC;
	}

	/**
	 * niniLd_8cdC
	 * @param niniLd_8cdCを設定します。
	 */
	public void setNiniLd_8cdC(String niniLd_8cdC) {
		this.niniLd_8cdC = niniLd_8cdC;
	}

	/**
	 * niniLd_8nmCを取得します。
	 * @return niniLd_8nmC
	 */
	public String getNiniLd_8nmC() {
		return niniLd_8nmC;
	}

	/**
	 * niniLd_8nmC
	 * @param niniLd_8nmCを設定します。
	 */
	public void setNiniLd_8nmC(String niniLd_8nmC) {
		this.niniLd_8nmC = niniLd_8nmC;
	}

	/**
	 * niniLd_9cdCを取得します。
	 * @return niniLd_9cdC
	 */
	public String getNiniLd_9cdC() {
		return niniLd_9cdC;
	}

	/**
	 * niniLd_9cdC
	 * @param niniLd_9cdCを設定します。
	 */
	public void setNiniLd_9cdC(String niniLd_9cdC) {
		this.niniLd_9cdC = niniLd_9cdC;
	}

	/**
	 * niniLd_9nmCを取得します。
	 * @return niniLd_9nmC
	 */
	public String getNiniLd_9nmC() {
		return niniLd_9nmC;
	}

	/**
	 * niniLd_9nmC
	 * @param niniLd_9nmCを設定します。
	 */
	public void setNiniLd_9nmC(String niniLd_9nmC) {
		this.niniLd_9nmC = niniLd_9nmC;
	}

	/**
	 * niniLd_10cdCを取得します。
	 * @return niniLd_10cdC
	 */
	public String getNiniLd_10cdC() {
		return niniLd_10cdC;
	}

	/**
	 * niniLd_10cdC
	 * @param niniLd_10cdCを設定します。
	 */
	public void setNiniLd_10cdC(String niniLd_10cdC) {
		this.niniLd_10cdC = niniLd_10cdC;
	}

	/**
	 * niniLd_10nmCを取得します。
	 * @return niniLd_10nmC
	 */
	public String getNiniLd_10nmC() {
		return niniLd_10nmC;
	}

	/**
	 * niniLd_10nmC
	 * @param niniLd_10nmCを設定します。
	 */
	public void setNiniLd_10nmC(String niniLd_10nmC) {
		this.niniLd_10nmC = niniLd_10nmC;
	}

	/**
	 * niniLd_11cdCを取得します。
	 * @return niniLd_11cdC
	 */
	public String getNiniLd_11cdC() {
		return niniLd_11cdC;
	}

	/**
	 * niniLd_11cdC
	 * @param niniLd_11cdCを設定します。
	 */
	public void setNiniLd_11cdC(String niniLd_11cdC) {
		this.niniLd_11cdC = niniLd_11cdC;
	}

	/**
	 * niniLd_11nmCを取得します。
	 * @return niniLd_11nmC
	 */
	public String getNiniLd_11nmC() {
		return niniLd_11nmC;
	}

	/**
	 * niniLd_11nmC
	 * @param niniLd_11nmCを設定します。
	 */
	public void setNiniLd_11nmC(String niniLd_11nmC) {
		this.niniLd_11nmC = niniLd_11nmC;
	}

	/**
	 * niniLd_12cdCを取得します。
	 * @return niniLd_12cdC
	 */
	public String getNiniLd_12cdC() {
		return niniLd_12cdC;
	}

	/**
	 * niniLd_12cdC
	 * @param niniLd_12cdCを設定します。
	 */
	public void setNiniLd_12cdC(String niniLd_12cdC) {
		this.niniLd_12cdC = niniLd_12cdC;
	}

	/**
	 * niniLd_12nmCを取得します。
	 * @return niniLd_12nmC
	 */
	public String getNiniLd_12nmC() {
		return niniLd_12nmC;
	}

	/**
	 * niniLd_12nmC
	 * @param niniLd_12nmCを設定します。
	 */
	public void setNiniLd_12nmC(String niniLd_12nmC) {
		this.niniLd_12nmC = niniLd_12nmC;
	}

	/**
	 * niniLd_13cdCを取得します。
	 * @return niniLd_13cdC
	 */
	public String getNiniLd_13cdC() {
		return niniLd_13cdC;
	}

	/**
	 * niniLd_13cdC
	 * @param niniLd_13cdCを設定します。
	 */
	public void setNiniLd_13cdC(String niniLd_13cdC) {
		this.niniLd_13cdC = niniLd_13cdC;
	}

	/**
	 * niniLd_13nmCを取得します。
	 * @return niniLd_13nmC
	 */
	public String getNiniLd_13nmC() {
		return niniLd_13nmC;
	}

	/**
	 * niniLd_13nmC
	 * @param niniLd_13nmCを設定します。
	 */
	public void setNiniLd_13nmC(String niniLd_13nmC) {
		this.niniLd_13nmC = niniLd_13nmC;
	}

	/**
	 * niniLd_14cdCを取得します。
	 * @return niniLd_14cdC
	 */
	public String getNiniLd_14cdC() {
		return niniLd_14cdC;
	}

	/**
	 * niniLd_14cdC
	 * @param niniLd_14cdCを設定します。
	 */
	public void setNiniLd_14cdC(String niniLd_14cdC) {
		this.niniLd_14cdC = niniLd_14cdC;
	}

	/**
	 * niniLd_14nmCを取得します。
	 * @return niniLd_14nmC
	 */
	public String getNiniLd_14nmC() {
		return niniLd_14nmC;
	}

	/**
	 * niniLd_14nmC
	 * @param niniLd_14nmCを設定します。
	 */
	public void setNiniLd_14nmC(String niniLd_14nmC) {
		this.niniLd_14nmC = niniLd_14nmC;
	}

	/**
	 * niniLd_15cdCを取得します。
	 * @return niniLd_15cdC
	 */
	public String getNiniLd_15cdC() {
		return niniLd_15cdC;
	}

	/**
	 * niniLd_15cdC
	 * @param niniLd_15cdCを設定します。
	 */
	public void setNiniLd_15cdC(String niniLd_15cdC) {
		this.niniLd_15cdC = niniLd_15cdC;
	}

	/**
	 * niniLd_15nmCを取得します。
	 * @return niniLd_15nmC
	 */
	public String getNiniLd_15nmC() {
		return niniLd_15nmC;
	}

	/**
	 * niniLd_15nmC
	 * @param niniLd_15nmCを設定します。
	 */
	public void setNiniLd_15nmC(String niniLd_15nmC) {
		this.niniLd_15nmC = niniLd_15nmC;
	}

	/**
	 * niniLd_16cdCを取得します。
	 * @return niniLd_16cdC
	 */
	public String getNiniLd_16cdC() {
		return niniLd_16cdC;
	}

	/**
	 * niniLd_16cdC
	 * @param niniLd_16cdCを設定します。
	 */
	public void setNiniLd_16cdC(String niniLd_16cdC) {
		this.niniLd_16cdC = niniLd_16cdC;
	}

	/**
	 * niniLd_16nmCを取得します。
	 * @return niniLd_16nmC
	 */
	public String getNiniLd_16nmC() {
		return niniLd_16nmC;
	}

	/**
	 * niniLd_16nmC
	 * @param niniLd_16nmCを設定します。
	 */
	public void setNiniLd_16nmC(String niniLd_16nmC) {
		this.niniLd_16nmC = niniLd_16nmC;
	}

	/**
	 * niniLd_17cdCを取得します。
	 * @return niniLd_17cdC
	 */
	public String getNiniLd_17cdC() {
		return niniLd_17cdC;
	}

	/**
	 * niniLd_17cdC
	 * @param niniLd_17cdCを設定します。
	 */
	public void setNiniLd_17cdC(String niniLd_17cdC) {
		this.niniLd_17cdC = niniLd_17cdC;
	}

	/**
	 * niniLd_17nmCを取得します。
	 * @return niniLd_17nmC
	 */
	public String getNiniLd_17nmC() {
		return niniLd_17nmC;
	}

	/**
	 * niniLd_17nmC
	 * @param niniLd_17nmCを設定します。
	 */
	public void setNiniLd_17nmC(String niniLd_17nmC) {
		this.niniLd_17nmC = niniLd_17nmC;
	}

	/**
	 * niniLd_18cdCを取得します。
	 * @return niniLd_18cdC
	 */
	public String getNiniLd_18cdC() {
		return niniLd_18cdC;
	}

	/**
	 * niniLd_18cdC
	 * @param niniLd_18cdCを設定します。
	 */
	public void setNiniLd_18cdC(String niniLd_18cdC) {
		this.niniLd_18cdC = niniLd_18cdC;
	}

	/**
	 * niniLd_18nmCを取得します。
	 * @return niniLd_18nmC
	 */
	public String getNiniLd_18nmC() {
		return niniLd_18nmC;
	}

	/**
	 * niniLd_18nmC
	 * @param niniLd_18nmCを設定します。
	 */
	public void setNiniLd_18nmC(String niniLd_18nmC) {
		this.niniLd_18nmC = niniLd_18nmC;
	}

	/**
	 * niniLd_19cdCを取得します。
	 * @return niniLd_19cdC
	 */
	public String getNiniLd_19cdC() {
		return niniLd_19cdC;
	}

	/**
	 * niniLd_19cdC
	 * @param niniLd_19cdCを設定します。
	 */
	public void setNiniLd_19cdC(String niniLd_19cdC) {
		this.niniLd_19cdC = niniLd_19cdC;
	}

	/**
	 * niniLd_19nmCを取得します。
	 * @return niniLd_19nmC
	 */
	public String getNiniLd_19nmC() {
		return niniLd_19nmC;
	}

	/**
	 * niniLd_19nmC
	 * @param niniLd_19nmCを設定します。
	 */
	public void setNiniLd_19nmC(String niniLd_19nmC) {
		this.niniLd_19nmC = niniLd_19nmC;
	}

	/**
	 * niniLd_20cdCを取得します。
	 * @return niniLd_20cdC
	 */
	public String getNiniLd_20cdC() {
		return niniLd_20cdC;
	}

	/**
	 * niniLd_20cdC
	 * @param niniLd_20cdCを設定します。
	 */
	public void setNiniLd_20cdC(String niniLd_20cdC) {
		this.niniLd_20cdC = niniLd_20cdC;
	}

	/**
	 * niniLd_20nmCを取得します。
	 * @return niniLd_20nmC
	 */
	public String getNiniLd_20nmC() {
		return niniLd_20nmC;
	}

	/**
	 * niniLd_20nmC
	 * @param niniLd_20nmCを設定します。
	 */
	public void setNiniLd_20nmC(String niniLd_20nmC) {
		this.niniLd_20nmC = niniLd_20nmC;
	}

	/**
	 * kykkbnCを取得します。
	 * @return kykkbnC
	 */
	public String getKykkbnC() {
		return kykkbnC;
	}

	/**
	 * kykkbnC
	 * @param kykkbnCを設定します。
	 */
	public void setKykkbnC(String kykkbnC) {
		this.kykkbnC = kykkbnC;
	}

	/**
	 * lakaisuCを取得します。
	 * @return lakaisuC
	 */
	public Integer getLakaisuC() {
		return lakaisuC;
	}

	/**
	 * lakaisuC
	 * @param lakaisuCを設定します。
	 */
	public void setLakaisuC(Integer lakaisuC) {
		this.lakaisuC = lakaisuC;
	}

	/**
	 * kyknmCを取得します。
	 * @return kyknmC
	 */
	public String getKyknmC() {
		return kyknmC;
	}

	/**
	 * kyknmC
	 * @param kyknmCを設定します。
	 */
	public void setKyknmC(String kyknmC) {
		this.kyknmC = kyknmC;
	}

	/**
	 * kyknmkCを取得します。
	 * @return kyknmkC
	 */
	public String getKyknmkC() {
		return kyknmkC;
	}

	/**
	 * kyknmkC
	 * @param kyknmkCを設定します。
	 */
	public void setKyknmkC(String kyknmkC) {
		this.kyknmkC = kyknmkC;
	}

	/**
	 * kyknoCを取得します。
	 * @return kyknoC
	 */
	public String getKyknoC() {
		return kyknoC;
	}

	/**
	 * kyknoC
	 * @param kyknoCを設定します。
	 */
	public void setKyknoC(String kyknoC) {
		this.kyknoC = kyknoC;
	}

	/**
	 * zenkyknoCを取得します。
	 * @return zenkyknoC
	 */
	public String getZenkyknoC() {
		return zenkyknoC;
	}

	/**
	 * zenkyknoC
	 * @param zenkyknoCを設定します。
	 */
	public void setZenkyknoC(String zenkyknoC) {
		this.zenkyknoC = zenkyknoC;
	}

	/**
	 * kykjotaikbnCを取得します。
	 * @return kykjotaikbnC
	 */
	public String getKykjotaikbnC() {
		return kykjotaikbnC;
	}

	/**
	 * kykjotaikbnC
	 * @param kykjotaikbnCを設定します。
	 */
	public void setKykjotaikbnC(String kykjotaikbnC) {
		this.kykjotaikbnC = kykjotaikbnC;
	}

	/**
	 * kykymdCを取得します。
	 * @return kykymdC
	 */
	public String getKykymdC() {
		return kykymdC;
	}

	/**
	 * kykymdC
	 * @param kykymdCを設定します。
	 */
	public void setKykymdC(String kykymdC) {
		this.kykymdC = kykymdC;
	}

	/**
	 * lastymdCを取得します。
	 * @return lastymdC
	 */
	public String getLastymdC() {
		return lastymdC;
	}

	/**
	 * lastymdC
	 * @param lastymdCを設定します。
	 */
	public void setLastymdC(String lastymdC) {
		this.lastymdC = lastymdC;
	}

	/**
	 * lamanryoymdCを取得します。
	 * @return lamanryoymdC
	 */
	public String getLamanryoymdC() {
		return lamanryoymdC;
	}

	/**
	 * lamanryoymdC
	 * @param lamanryoymdCを設定します。
	 */
	public void setLamanryoymdC(String lamanryoymdC) {
		this.lamanryoymdC = lamanryoymdC;
	}

	/**
	 * lakikanCを取得します。
	 * @return lakikanC
	 */
	public Integer getLakikanC() {
		return lakikanC;
	}

	/**
	 * lakikanC
	 * @param lakikanCを設定します。
	 */
	public void setLakikanC(Integer lakikanC) {
		this.lakikanC = lakikanC;
	}

	/**
	 * shrhokbnCを取得します。
	 * @return shrhokbnC
	 */
	public String getShrhokbnC() {
		return shrhokbnC;
	}

	/**
	 * shrhokbnC
	 * @param shrhokbnCを設定します。
	 */
	public void setShrhokbnC(String shrhokbnC) {
		this.shrhokbnC = shrhokbnC;
	}

	/**
	 * shrmanryoymdCを取得します。
	 * @return shrmanryoymdC
	 */
	public String getShrmanryoymdC() {
		return shrmanryoymdC;
	}

	/**
	 * shrmanryoymdC
	 * @param shrmanryoymdCを設定します。
	 */
	public void setShrmanryoymdC(String shrmanryoymdC) {
		this.shrmanryoymdC = shrmanryoymdC;
	}

	/**
	 * kaiykymdCを取得します。
	 * @return kaiykymdC
	 */
	public String getKaiykymdC() {
		return kaiykymdC;
	}

	/**
	 * kaiykymdC
	 * @param kaiykymdCを設定します。
	 */
	public void setKaiykymdC(String kaiykymdC) {
		this.kaiykymdC = kaiykymdC;
	}

	/**
	 * shrcyclekbnCを取得します。
	 * @return shrcyclekbnC
	 */
	public String getShrcyclekbnC() {
		return shrcyclekbnC;
	}

	/**
	 * shrcyclekbnC
	 * @param shrcyclekbnCを設定します。
	 */
	public void setShrcyclekbnC(String shrcyclekbnC) {
		this.shrcyclekbnC = shrcyclekbnC;
	}

	/**
	 * jitsushrkaisuCを取得します。
	 * @return jitsushrkaisuC
	 */
	public Long getJitsushrkaisuC() {
		return jitsushrkaisuC;
	}

	/**
	 * jitsushrkaisuC
	 * @param jitsushrkaisuCを設定します。
	 */
	public void setJitsushrkaisuC(Long jitsushrkaisuC) {
		this.jitsushrkaisuC = jitsushrkaisuC;
	}

	/**
	 * shrkaisuCを取得します。
	 * @return shrkaisuC
	 */
	public Integer getShrkaisuC() {
		return shrkaisuC;
	}

	/**
	 * shrkaisuC
	 * @param shrkaisuCを設定します。
	 */
	public void setShrkaisuC(Integer shrkaisuC) {
		this.shrkaisuC = shrkaisuC;
	}

	/**
	 * stkgksotogkCを取得します。
	 * @return stkgksotogkC
	 */
	public Long getStkgksotogkC() {
		return stkgksotogkC;
	}

	/**
	 * stkgksotogkC
	 * @param stkgksotogkCを設定します。
	 */
	public void setStkgksotogkC(Long stkgksotogkC) {
		this.stkgksotogkC = stkgksotogkC;
	}

	/**
	 * shrlaryo1Cを取得します。
	 * @return shrlaryo1C
	 */
	public Long getShrlaryo1C() {
		return shrlaryo1C;
	}

	/**
	 * shrlaryo1C
	 * @param shrlaryo1Cを設定します。
	 */
	public void setShrlaryo1C(Long shrlaryo1C) {
		this.shrlaryo1C = shrlaryo1C;
	}

	/**
	 * laryoTotalCを取得します。
	 * @return laryoTotalC
	 */
	public Long getLaryoTotalC() {
		return laryoTotalC;
	}

	/**
	 * laryoTotalC
	 * @param laryoTotalCを設定します。
	 */
	public void setLaryoTotalC(Long laryoTotalC) {
		this.laryoTotalC = laryoTotalC;
	}

	/**
	 * kihonsparen1Cを取得します。
	 * @return kihonsparen1C
	 */
	public Long getKihonsparen1C() {
		return kihonsparen1C;
	}

	/**
	 * kihonsparen1C
	 * @param kihonsparen1Cを設定します。
	 */
	public void setKihonsparen1C(Long kihonsparen1C) {
		this.kihonsparen1C = kihonsparen1C;
	}

	/**
	 * maehrlaryoCを取得します。
	 * @return maehrlaryoC
	 */
	public Long getMaehrlaryoC() {
		return maehrlaryoC;
	}

	/**
	 * maehrlaryoC
	 * @param maehrlaryoCを設定します。
	 */
	public void setMaehrlaryoC(Long maehrlaryoC) {
		this.maehrlaryoC = maehrlaryoC;
	}

	/**
	 * kintokbnCを取得します。
	 * @return kintokbnC
	 */
	public String getKintokbnC() {
		return kintokbnC;
	}

	/**
	 * kintokbnC
	 * @param kintokbnCを設定します。
	 */
	public void setKintokbnC(String kintokbnC) {
		this.kintokbnC = kintokbnC;
	}

	/**
	 * shokailaryoCを取得します。
	 * @return shokailaryoC
	 */
	public Long getShokailaryoC() {
		return shokailaryoC;
	}

	/**
	 * shokailaryoC
	 * @param shokailaryoCを設定します。
	 */
	public void setShokailaryoC(Long shokailaryoC) {
		this.shokailaryoC = shokailaryoC;
	}

	/**
	 * laryoCを取得します。
	 * @return laryoC
	 */
	public Long getLaryoC() {
		return laryoC;
	}

	/**
	 * laryoC
	 * @param laryoCを設定します。
	 */
	public void setLaryoC(Long laryoC) {
		this.laryoC = laryoC;
	}

	/**
	 * mikeikalaryoPayCを取得します。
	 * @return mikeikalaryoPayC
	 */
	public Long getMikeikalaryoPayC() {
		return mikeikalaryoPayC;
	}

	/**
	 * mikeikalaryoPayC
	 * @param mikeikalaryoPayCを設定します。
	 */
	public void setMikeikalaryoPayC(Long mikeikalaryoPayC) {
		this.mikeikalaryoPayC = mikeikalaryoPayC;
	}

	/**
	 * kihonsparen3Cを取得します。
	 * @return kihonsparen3C
	 */
	public Long getKihonsparen3C() {
		return kihonsparen3C;
	}

	/**
	 * kihonsparen3C
	 * @param kihonsparen3Cを設定します。
	 */
	public void setKihonsparen3C(Long kihonsparen3C) {
		this.kihonsparen3C = kihonsparen3C;
	}

	/**
	 * shrlaryo2Cを取得します。
	 * @return shrlaryo2C
	 */
	public Long getShrlaryo2C() {
		return shrlaryo2C;
	}

	/**
	 * shrlaryo2C
	 * @param shrlaryo2Cを設定します。
	 */
	public void setShrlaryo2C(Long shrlaryo2C) {
		this.shrlaryo2C = shrlaryo2C;
	}

	/**
	 * latorihikikbnCを取得します。
	 * @return latorihikikbnC
	 */
	public String getLatorihikikbnC() {
		return latorihikikbnC;
	}

	/**
	 * latorihikikbnC
	 * @param latorihikikbnCを設定します。
	 */
	public void setLatorihikikbnC(String latorihikikbnC) {
		this.latorihikikbnC = latorihikikbnC;
	}

	/**
	 * kojogk1Cを取得します。
	 * @return kojogk1C
	 */
	public Long getKojogk1C() {
		return kojogk1C;
	}

	/**
	 * kojogk1C
	 * @param kojogk1Cを設定します。
	 */
	public void setKojogk1C(Long kojogk1C) {
		this.kojogk1C = kojogk1C;
	}

	/**
	 * kojogk2Cを取得します。
	 * @return kojogk2C
	 */
	public Long getKojogk2C() {
		return kojogk2C;
	}

	/**
	 * kojogk2C
	 * @param kojogk2Cを設定します。
	 */
	public void setKojogk2C(Long kojogk2C) {
		this.kojogk2C = kojogk2C;
	}

	/**
	 * kojogk3Cを取得します。
	 * @return kojogk3C
	 */
	public Long getKojogk3C() {
		return kojogk3C;
	}

	/**
	 * kojogk3C
	 * @param kojogk3Cを設定します。
	 */
	public void setKojogk3C(Long kojogk3C) {
		this.kojogk3C = kojogk3C;
	}

	/**
	 * kojogk4Cを取得します。
	 * @return kojogk4C
	 */
	public Long getKojogk4C() {
		return kojogk4C;
	}

	/**
	 * kojogk4C
	 * @param kojogk4Cを設定します。
	 */
	public void setKojogk4C(Long kojogk4C) {
		this.kojogk4C = kojogk4C;
	}

	/**
	 * biko1Cを取得します。
	 * @return biko1C
	 */
	public String getBiko1C() {
		return biko1C;
	}

	/**
	 * biko1C
	 * @param biko1Cを設定します。
	 */
	public void setBiko1C(String biko1C) {
		this.biko1C = biko1C;
	}

	/**
	 * biko2Cを取得します。
	 * @return biko2C
	 */
	public String getBiko2C() {
		return biko2C;
	}

	/**
	 * biko2C
	 * @param biko2Cを設定します。
	 */
	public void setBiko2C(String biko2C) {
		this.biko2C = biko2C;
	}

	/**
	 * kykringinoCを取得します。
	 * @return kykringinoC
	 */
	public String getKykringinoC() {
		return kykringinoC;
	}

	/**
	 * kykringinoC
	 * @param kykringinoCを設定します。
	 */
	public void setKykringinoC(String kykringinoC) {
		this.kykringinoC = kykringinoC;
	}

	/**
	 * kyktekiyoCを取得します。
	 * @return kyktekiyoC
	 */
	public String getKyktekiyoC() {
		return kyktekiyoC;
	}

	/**
	 * kyktekiyoC
	 * @param kyktekiyoCを設定します。
	 */
	public void setKyktekiyoC(String kyktekiyoC) {
		this.kyktekiyoC = kyktekiyoC;
	}

	/**
	 * chukikbnCを取得します。
	 * @return chukikbnC
	 */
	public String getChukikbnC() {
		return chukikbnC;
	}

	/**
	 * chukikbnC
	 * @param chukikbnCを設定します。
	 */
	public void setChukikbnC(String chukikbnC) {
		this.chukikbnC = chukikbnC;
	}

	/**
	 * bskeijokbnCを取得します。
	 * @return bskeijokbnC
	 */
	public String getBskeijokbnC() {
		return bskeijokbnC;
	}

	/**
	 * bskeijokbnC
	 * @param bskeijokbnCを設定します。
	 */
	public void setBskeijokbnC(String bskeijokbnC) {
		this.bskeijokbnC = bskeijokbnC;
	}

	/**
	 * zansotogkCを取得します。
	 * @return zansotogkC
	 */
	public Long getZansotogkC() {
		return zansotogkC;
	}

	/**
	 * zansotogkC
	 * @param zansotogkCを設定します。
	 */
	public void setZansotogkC(Long zansotogkC) {
		this.zansotogkC = zansotogkC;
	}

	/**
	 * updkaisuCを取得します。
	 * @return updkaisuC
	 */
	public Long getUpdkaisuC() {
		return updkaisuC;
	}

	/**
	 * updkaisuC
	 * @param updkaisuCを設定します。
	 */
	public void setUpdkaisuC(Long updkaisuC) {
		this.updkaisuC = updkaisuC;
	}

	/**
	 * updkaishacdCを取得します。
	 * @return updkaishacdC
	 */
	public String getUpdkaishacdC() {
		return updkaishacdC;
	}

	/**
	 * updkaishacdC
	 * @param updkaishacdCを設定します。
	 */
	public void setUpdkaishacdC(String updkaishacdC) {
		this.updkaishacdC = updkaishacdC;
	}

	/**
	 * updidCを取得します。
	 * @return updidC
	 */
	public String getUpdidC() {
		return updidC;
	}

	/**
	 * updidC
	 * @param updidCを設定します。
	 */
	public void setUpdidC(String updidC) {
		this.updidC = updidC;
	}

	/**
	 * updymdCを取得します。
	 * @return updymdC
	 */
	public String getUpdymdC() {
		return updymdC;
	}

	/**
	 * updymdC
	 * @param updymdCを設定します。
	 */
	public void setUpdymdC(String updymdC) {
		this.updymdC = updymdC;
	}

	/**
	 * updtimeCを取得します。
	 * @return updtimeC
	 */
	public String getUpdtimeC() {
		return updtimeC;
	}

	/**
	 * updtimeC
	 * @param updtimeCを設定します。
	 */
	public void setUpdtimeC(String updtimeC) {
		this.updtimeC = updtimeC;
	}

	/**
	 * koyunoAを取得します。
	 * @return koyunoA
	 */
	public Long getKoyunoA() {
		return koyunoA;
	}

	/**
	 * koyunoA
	 * @param koyunoAを設定します。
	 */
	public void setKoyunoA(Long koyunoA) {
		this.koyunoA = koyunoA;
	}

	/**
	 * kaishacdAを取得します。
	 * @return kaishacdA
	 */
	public String getKaishacdA() {
		return kaishacdA;
	}

	/**
	 * kaishacdA
	 * @param kaishacdAを設定します。
	 */
	public void setKaishacdA(String kaishacdA) {
		this.kaishacdA = kaishacdA;
	}

	/**
	 * kaishanmAを取得します。
	 * @return kaishanmA
	 */
	public String getKaishanmA() {
		return kaishanmA;
	}

	/**
	 * kaishanmA
	 * @param kaishanmAを設定します。
	 */
	public void setKaishanmA(String kaishanmA) {
		this.kaishanmA = kaishanmA;
	}

	/**
	 * lakaishacdAを取得します。
	 * @return lakaishacdA
	 */
	public String getLakaishacdA() {
		return lakaishacdA;
	}

	/**
	 * lakaishacdA
	 * @param lakaishacdAを設定します。
	 */
	public void setLakaishacdA(String lakaishacdA) {
		this.lakaishacdA = lakaishacdA;
	}

	/**
	 * torihikinmAを取得します。
	 * @return torihikinmA
	 */
	public String getTorihikinmA() {
		return torihikinmA;
	}

	/**
	 * torihikinmA
	 * @param torihikinmAを設定します。
	 */
	public void setTorihikinmA(String torihikinmA) {
		this.torihikinmA = torihikinmA;
	}

	/**
	 * kyknoAを取得します。
	 * @return kyknoA
	 */
	public String getKyknoA() {
		return kyknoA;
	}

	/**
	 * kyknoA
	 * @param kyknoAを設定します。
	 */
	public void setKyknoA(String kyknoA) {
		this.kyknoA = kyknoA;
	}

	/**
	 * kyknmAを取得します。
	 * @return kyknmA
	 */
	public String getKyknmA() {
		return kyknmA;
	}

	/**
	 * kyknmA
	 * @param kyknmAを設定します。
	 */
	public void setKyknmA(String kyknmA) {
		this.kyknmA = kyknmA;
	}

	/**
	 * soshiki1cdAを取得します。
	 * @return soshiki1cdA
	 */
	public String getSoshiki1cdA() {
		return soshiki1cdA;
	}

	/**
	 * soshiki1cdA
	 * @param soshiki1cdAを設定します。
	 */
	public void setSoshiki1cdA(String soshiki1cdA) {
		this.soshiki1cdA = soshiki1cdA;
	}

	/**
	 * soshiki1nmAを取得します。
	 * @return soshiki1nmA
	 */
	public String getSoshiki1nmA() {
		return soshiki1nmA;
	}

	/**
	 * soshiki1nmA
	 * @param soshiki1nmAを設定します。
	 */
	public void setSoshiki1nmA(String soshiki1nmA) {
		this.soshiki1nmA = soshiki1nmA;
	}

	/**
	 * soshiki2cdAを取得します。
	 * @return soshiki2cdA
	 */
	public String getSoshiki2cdA() {
		return soshiki2cdA;
	}

	/**
	 * soshiki2cdA
	 * @param soshiki2cdAを設定します。
	 */
	public void setSoshiki2cdA(String soshiki2cdA) {
		this.soshiki2cdA = soshiki2cdA;
	}

	/**
	 * soshiki2nmAを取得します。
	 * @return soshiki2nmA
	 */
	public String getSoshiki2nmA() {
		return soshiki2nmA;
	}

	/**
	 * soshiki2nmA
	 * @param soshiki2nmAを設定します。
	 */
	public void setSoshiki2nmA(String soshiki2nmA) {
		this.soshiki2nmA = soshiki2nmA;
	}

	/**
	 * soshiki3cdAを取得します。
	 * @return soshiki3cdA
	 */
	public String getSoshiki3cdA() {
		return soshiki3cdA;
	}

	/**
	 * soshiki3cdA
	 * @param soshiki3cdAを設定します。
	 */
	public void setSoshiki3cdA(String soshiki3cdA) {
		this.soshiki3cdA = soshiki3cdA;
	}

	/**
	 * soshiki3nmAを取得します。
	 * @return soshiki3nmA
	 */
	public String getSoshiki3nmA() {
		return soshiki3nmA;
	}

	/**
	 * soshiki3nmA
	 * @param soshiki3nmAを設定します。
	 */
	public void setSoshiki3nmA(String soshiki3nmA) {
		this.soshiki3nmA = soshiki3nmA;
	}

	/**
	 * soshiki4cdAを取得します。
	 * @return soshiki4cdA
	 */
	public String getSoshiki4cdA() {
		return soshiki4cdA;
	}

	/**
	 * soshiki4cdA
	 * @param soshiki4cdAを設定します。
	 */
	public void setSoshiki4cdA(String soshiki4cdA) {
		this.soshiki4cdA = soshiki4cdA;
	}

	/**
	 * soshiki4nmAを取得します。
	 * @return soshiki4nmA
	 */
	public String getSoshiki4nmA() {
		return soshiki4nmA;
	}

	/**
	 * soshiki4nmA
	 * @param soshiki4nmAを設定します。
	 */
	public void setSoshiki4nmA(String soshiki4nmA) {
		this.soshiki4nmA = soshiki4nmA;
	}

	/**
	 * shuruicdAを取得します。
	 * @return shuruicdA
	 */
	public String getShuruicdA() {
		return shuruicdA;
	}

	/**
	 * shuruicdA
	 * @param shuruicdAを設定します。
	 */
	public void setShuruicdA(String shuruicdA) {
		this.shuruicdA = shuruicdA;
	}

	/**
	 * shuruinmAを取得します。
	 * @return shuruinmA
	 */
	public String getShuruinmA() {
		return shuruinmA;
	}

	/**
	 * shuruinmA
	 * @param shuruinmAを設定します。
	 */
	public void setShuruinmA(String shuruinmA) {
		this.shuruinmA = shuruinmA;
	}

	/**
	 * kozocdAを取得します。
	 * @return kozocdA
	 */
	public String getKozocdA() {
		return kozocdA;
	}

	/**
	 * kozocdA
	 * @param kozocdAを設定します。
	 */
	public void setKozocdA(String kozocdA) {
		this.kozocdA = kozocdA;
	}

	/**
	 * kozonmAを取得します。
	 * @return kozonmA
	 */
	public String getKozonmA() {
		return kozonmA;
	}

	/**
	 * kozonmA
	 * @param kozonmAを設定します。
	 */
	public void setKozonmA(String kozonmA) {
		this.kozonmA = kozonmA;
	}

	/**
	 * bunruicdAを取得します。
	 * @return bunruicdA
	 */
	public String getBunruicdA() {
		return bunruicdA;
	}

	/**
	 * bunruicdA
	 * @param bunruicdAを設定します。
	 */
	public void setBunruicdA(String bunruicdA) {
		this.bunruicdA = bunruicdA;
	}

	/**
	 * bunruinmAを取得します。
	 * @return bunruinmA
	 */
	public String getBunruinmA() {
		return bunruinmA;
	}

	/**
	 * bunruinmA
	 * @param bunruinmAを設定します。
	 */
	public void setBunruinmA(String bunruinmA) {
		this.bunruinmA = bunruinmA;
	}

	/**
	 * setchicdAを取得します。
	 * @return setchicdA
	 */
	public String getSetchicdA() {
		return setchicdA;
	}

	/**
	 * setchicdA
	 * @param setchicdAを設定します。
	 */
	public void setSetchicdA(String setchicdA) {
		this.setchicdA = setchicdA;
	}

	/**
	 * setchinmAを取得します。
	 * @return setchinmA
	 */
	public String getSetchinmA() {
		return setchinmA;
	}

	/**
	 * setchinmA
	 * @param setchinmAを設定します。
	 */
	public void setSetchinmA(String setchinmA) {
		this.setchinmA = setchinmA;
	}

	/**
	 * shuyoshisankbnAを取得します。
	 * @return shuyoshisankbnA
	 */
	public String getShuyoshisankbnA() {
		return shuyoshisankbnA;
	}

	/**
	 * shuyoshisankbnA
	 * @param shuyoshisankbnAを設定します。
	 */
	public void setShuyoshisankbnA(String shuyoshisankbnA) {
		this.shuyoshisankbnA = shuyoshisankbnA;
	}

	/**
	 * groupcdAを取得します。
	 * @return groupcdA
	 */
	public String getGroupcdA() {
		return groupcdA;
	}

	/**
	 * groupcdA
	 * @param groupcdAを設定します。
	 */
	public void setGroupcdA(String groupcdA) {
		this.groupcdA = groupcdA;
	}

	/**
	 * shinariocdAを取得します。
	 * @return shinariocdA
	 */
	public String getShinariocdA() {
		return shinariocdA;
	}

	/**
	 * shinariocdA
	 * @param shinariocdAを設定します。
	 */
	public void setShinariocdA(String shinariocdA) {
		this.shinariocdA = shinariocdA;
	}

	/**
	 * niniLd_1cdAを取得します。
	 * @return niniLd_1cdA
	 */
	public String getNiniLd_1cdA() {
		return niniLd_1cdA;
	}

	/**
	 * niniLd_1cdA
	 * @param niniLd_1cdAを設定します。
	 */
	public void setNiniLd_1cdA(String niniLd_1cdA) {
		this.niniLd_1cdA = niniLd_1cdA;
	}

	/**
	 * niniLd_1nmAを取得します。
	 * @return niniLd_1nmA
	 */
	public String getNiniLd_1nmA() {
		return niniLd_1nmA;
	}

	/**
	 * niniLd_1nmA
	 * @param niniLd_1nmAを設定します。
	 */
	public void setNiniLd_1nmA(String niniLd_1nmA) {
		this.niniLd_1nmA = niniLd_1nmA;
	}

	/**
	 * niniLd_2cdAを取得します。
	 * @return niniLd_2cdA
	 */
	public String getNiniLd_2cdA() {
		return niniLd_2cdA;
	}

	/**
	 * niniLd_2cdA
	 * @param niniLd_2cdAを設定します。
	 */
	public void setNiniLd_2cdA(String niniLd_2cdA) {
		this.niniLd_2cdA = niniLd_2cdA;
	}

	/**
	 * niniLd_2nmAを取得します。
	 * @return niniLd_2nmA
	 */
	public String getNiniLd_2nmA() {
		return niniLd_2nmA;
	}

	/**
	 * niniLd_2nmA
	 * @param niniLd_2nmAを設定します。
	 */
	public void setNiniLd_2nmA(String niniLd_2nmA) {
		this.niniLd_2nmA = niniLd_2nmA;
	}

	/**
	 * niniLd_3cdAを取得します。
	 * @return niniLd_3cdA
	 */
	public String getNiniLd_3cdA() {
		return niniLd_3cdA;
	}

	/**
	 * niniLd_3cdA
	 * @param niniLd_3cdAを設定します。
	 */
	public void setNiniLd_3cdA(String niniLd_3cdA) {
		this.niniLd_3cdA = niniLd_3cdA;
	}

	/**
	 * niniLd_3nmAを取得します。
	 * @return niniLd_3nmA
	 */
	public String getNiniLd_3nmA() {
		return niniLd_3nmA;
	}

	/**
	 * niniLd_3nmA
	 * @param niniLd_3nmAを設定します。
	 */
	public void setNiniLd_3nmA(String niniLd_3nmA) {
		this.niniLd_3nmA = niniLd_3nmA;
	}

	/**
	 * niniLd_4cdAを取得します。
	 * @return niniLd_4cdA
	 */
	public String getNiniLd_4cdA() {
		return niniLd_4cdA;
	}

	/**
	 * niniLd_4cdA
	 * @param niniLd_4cdAを設定します。
	 */
	public void setNiniLd_4cdA(String niniLd_4cdA) {
		this.niniLd_4cdA = niniLd_4cdA;
	}

	/**
	 * niniLd_4nmAを取得します。
	 * @return niniLd_4nmA
	 */
	public String getNiniLd_4nmA() {
		return niniLd_4nmA;
	}

	/**
	 * niniLd_4nmA
	 * @param niniLd_4nmAを設定します。
	 */
	public void setNiniLd_4nmA(String niniLd_4nmA) {
		this.niniLd_4nmA = niniLd_4nmA;
	}

	/**
	 * niniLd_5cdAを取得します。
	 * @return niniLd_5cdA
	 */
	public String getNiniLd_5cdA() {
		return niniLd_5cdA;
	}

	/**
	 * niniLd_5cdA
	 * @param niniLd_5cdAを設定します。
	 */
	public void setNiniLd_5cdA(String niniLd_5cdA) {
		this.niniLd_5cdA = niniLd_5cdA;
	}

	/**
	 * niniLd_5nmAを取得します。
	 * @return niniLd_5nmA
	 */
	public String getNiniLd_5nmA() {
		return niniLd_5nmA;
	}

	/**
	 * niniLd_5nmA
	 * @param niniLd_5nmAを設定します。
	 */
	public void setNiniLd_5nmA(String niniLd_5nmA) {
		this.niniLd_5nmA = niniLd_5nmA;
	}

	/**
	 * niniLd_6cdAを取得します。
	 * @return niniLd_6cdA
	 */
	public String getNiniLd_6cdA() {
		return niniLd_6cdA;
	}

	/**
	 * niniLd_6cdA
	 * @param niniLd_6cdAを設定します。
	 */
	public void setNiniLd_6cdA(String niniLd_6cdA) {
		this.niniLd_6cdA = niniLd_6cdA;
	}

	/**
	 * niniLd_6nmAを取得します。
	 * @return niniLd_6nmA
	 */
	public String getNiniLd_6nmA() {
		return niniLd_6nmA;
	}

	/**
	 * niniLd_6nmA
	 * @param niniLd_6nmAを設定します。
	 */
	public void setNiniLd_6nmA(String niniLd_6nmA) {
		this.niniLd_6nmA = niniLd_6nmA;
	}

	/**
	 * niniLd_7cdAを取得します。
	 * @return niniLd_7cdA
	 */
	public String getNiniLd_7cdA() {
		return niniLd_7cdA;
	}

	/**
	 * niniLd_7cdA
	 * @param niniLd_7cdAを設定します。
	 */
	public void setNiniLd_7cdA(String niniLd_7cdA) {
		this.niniLd_7cdA = niniLd_7cdA;
	}

	/**
	 * niniLd_7nmAを取得します。
	 * @return niniLd_7nmA
	 */
	public String getNiniLd_7nmA() {
		return niniLd_7nmA;
	}

	/**
	 * niniLd_7nmA
	 * @param niniLd_7nmAを設定します。
	 */
	public void setNiniLd_7nmA(String niniLd_7nmA) {
		this.niniLd_7nmA = niniLd_7nmA;
	}

	/**
	 * niniLd_8cdAを取得します。
	 * @return niniLd_8cdA
	 */
	public String getNiniLd_8cdA() {
		return niniLd_8cdA;
	}

	/**
	 * niniLd_8cdA
	 * @param niniLd_8cdAを設定します。
	 */
	public void setNiniLd_8cdA(String niniLd_8cdA) {
		this.niniLd_8cdA = niniLd_8cdA;
	}

	/**
	 * niniLd_8nmAを取得します。
	 * @return niniLd_8nmA
	 */
	public String getNiniLd_8nmA() {
		return niniLd_8nmA;
	}

	/**
	 * niniLd_8nmA
	 * @param niniLd_8nmAを設定します。
	 */
	public void setNiniLd_8nmA(String niniLd_8nmA) {
		this.niniLd_8nmA = niniLd_8nmA;
	}

	/**
	 * niniLd_9cdAを取得します。
	 * @return niniLd_9cdA
	 */
	public String getNiniLd_9cdA() {
		return niniLd_9cdA;
	}

	/**
	 * niniLd_9cdA
	 * @param niniLd_9cdAを設定します。
	 */
	public void setNiniLd_9cdA(String niniLd_9cdA) {
		this.niniLd_9cdA = niniLd_9cdA;
	}

	/**
	 * niniLd_9nmAを取得します。
	 * @return niniLd_9nmA
	 */
	public String getNiniLd_9nmA() {
		return niniLd_9nmA;
	}

	/**
	 * niniLd_9nmA
	 * @param niniLd_9nmAを設定します。
	 */
	public void setNiniLd_9nmA(String niniLd_9nmA) {
		this.niniLd_9nmA = niniLd_9nmA;
	}

	/**
	 * niniLd_10cdAを取得します。
	 * @return niniLd_10cdA
	 */
	public String getNiniLd_10cdA() {
		return niniLd_10cdA;
	}

	/**
	 * niniLd_10cdA
	 * @param niniLd_10cdAを設定します。
	 */
	public void setNiniLd_10cdA(String niniLd_10cdA) {
		this.niniLd_10cdA = niniLd_10cdA;
	}

	/**
	 * niniLd_10nmAを取得します。
	 * @return niniLd_10nmA
	 */
	public String getNiniLd_10nmA() {
		return niniLd_10nmA;
	}

	/**
	 * niniLd_10nmA
	 * @param niniLd_10nmAを設定します。
	 */
	public void setNiniLd_10nmA(String niniLd_10nmA) {
		this.niniLd_10nmA = niniLd_10nmA;
	}

	/**
	 * niniLd_11cdAを取得します。
	 * @return niniLd_11cdA
	 */
	public String getNiniLd_11cdA() {
		return niniLd_11cdA;
	}

	/**
	 * niniLd_11cdA
	 * @param niniLd_11cdAを設定します。
	 */
	public void setNiniLd_11cdA(String niniLd_11cdA) {
		this.niniLd_11cdA = niniLd_11cdA;
	}

	/**
	 * niniLd_11nmAを取得します。
	 * @return niniLd_11nmA
	 */
	public String getNiniLd_11nmA() {
		return niniLd_11nmA;
	}

	/**
	 * niniLd_11nmA
	 * @param niniLd_11nmAを設定します。
	 */
	public void setNiniLd_11nmA(String niniLd_11nmA) {
		this.niniLd_11nmA = niniLd_11nmA;
	}

	/**
	 * niniLd_12cdAを取得します。
	 * @return niniLd_12cdA
	 */
	public String getNiniLd_12cdA() {
		return niniLd_12cdA;
	}

	/**
	 * niniLd_12cdA
	 * @param niniLd_12cdAを設定します。
	 */
	public void setNiniLd_12cdA(String niniLd_12cdA) {
		this.niniLd_12cdA = niniLd_12cdA;
	}

	/**
	 * niniLd_12nmAを取得します。
	 * @return niniLd_12nmA
	 */
	public String getNiniLd_12nmA() {
		return niniLd_12nmA;
	}

	/**
	 * niniLd_12nmA
	 * @param niniLd_12nmAを設定します。
	 */
	public void setNiniLd_12nmA(String niniLd_12nmA) {
		this.niniLd_12nmA = niniLd_12nmA;
	}

	/**
	 * niniLd_13cdAを取得します。
	 * @return niniLd_13cdA
	 */
	public String getNiniLd_13cdA() {
		return niniLd_13cdA;
	}

	/**
	 * niniLd_13cdA
	 * @param niniLd_13cdAを設定します。
	 */
	public void setNiniLd_13cdA(String niniLd_13cdA) {
		this.niniLd_13cdA = niniLd_13cdA;
	}

	/**
	 * niniLd_13nmAを取得します。
	 * @return niniLd_13nmA
	 */
	public String getNiniLd_13nmA() {
		return niniLd_13nmA;
	}

	/**
	 * niniLd_13nmA
	 * @param niniLd_13nmAを設定します。
	 */
	public void setNiniLd_13nmA(String niniLd_13nmA) {
		this.niniLd_13nmA = niniLd_13nmA;
	}

	/**
	 * niniLd_14cdAを取得します。
	 * @return niniLd_14cdA
	 */
	public String getNiniLd_14cdA() {
		return niniLd_14cdA;
	}

	/**
	 * niniLd_14cdA
	 * @param niniLd_14cdAを設定します。
	 */
	public void setNiniLd_14cdA(String niniLd_14cdA) {
		this.niniLd_14cdA = niniLd_14cdA;
	}

	/**
	 * niniLd_14nmAを取得します。
	 * @return niniLd_14nmA
	 */
	public String getNiniLd_14nmA() {
		return niniLd_14nmA;
	}

	/**
	 * niniLd_14nmA
	 * @param niniLd_14nmAを設定します。
	 */
	public void setNiniLd_14nmA(String niniLd_14nmA) {
		this.niniLd_14nmA = niniLd_14nmA;
	}

	/**
	 * niniLd_15cdAを取得します。
	 * @return niniLd_15cdA
	 */
	public String getNiniLd_15cdA() {
		return niniLd_15cdA;
	}

	/**
	 * niniLd_15cdA
	 * @param niniLd_15cdAを設定します。
	 */
	public void setNiniLd_15cdA(String niniLd_15cdA) {
		this.niniLd_15cdA = niniLd_15cdA;
	}

	/**
	 * niniLd_15nmAを取得します。
	 * @return niniLd_15nmA
	 */
	public String getNiniLd_15nmA() {
		return niniLd_15nmA;
	}

	/**
	 * niniLd_15nmA
	 * @param niniLd_15nmAを設定します。
	 */
	public void setNiniLd_15nmA(String niniLd_15nmA) {
		this.niniLd_15nmA = niniLd_15nmA;
	}

	/**
	 * niniLd_16cdAを取得します。
	 * @return niniLd_16cdA
	 */
	public String getNiniLd_16cdA() {
		return niniLd_16cdA;
	}

	/**
	 * niniLd_16cdA
	 * @param niniLd_16cdAを設定します。
	 */
	public void setNiniLd_16cdA(String niniLd_16cdA) {
		this.niniLd_16cdA = niniLd_16cdA;
	}

	/**
	 * niniLd_16nmAを取得します。
	 * @return niniLd_16nmA
	 */
	public String getNiniLd_16nmA() {
		return niniLd_16nmA;
	}

	/**
	 * niniLd_16nmA
	 * @param niniLd_16nmAを設定します。
	 */
	public void setNiniLd_16nmA(String niniLd_16nmA) {
		this.niniLd_16nmA = niniLd_16nmA;
	}

	/**
	 * niniLd_17cdAを取得します。
	 * @return niniLd_17cdA
	 */
	public String getNiniLd_17cdA() {
		return niniLd_17cdA;
	}

	/**
	 * niniLd_17cdA
	 * @param niniLd_17cdAを設定します。
	 */
	public void setNiniLd_17cdA(String niniLd_17cdA) {
		this.niniLd_17cdA = niniLd_17cdA;
	}

	/**
	 * niniLd_17nmAを取得します。
	 * @return niniLd_17nmA
	 */
	public String getNiniLd_17nmA() {
		return niniLd_17nmA;
	}

	/**
	 * niniLd_17nmA
	 * @param niniLd_17nmAを設定します。
	 */
	public void setNiniLd_17nmA(String niniLd_17nmA) {
		this.niniLd_17nmA = niniLd_17nmA;
	}

	/**
	 * niniLd_18cdAを取得します。
	 * @return niniLd_18cdA
	 */
	public String getNiniLd_18cdA() {
		return niniLd_18cdA;
	}

	/**
	 * niniLd_18cdA
	 * @param niniLd_18cdAを設定します。
	 */
	public void setNiniLd_18cdA(String niniLd_18cdA) {
		this.niniLd_18cdA = niniLd_18cdA;
	}

	/**
	 * niniLd_18nmAを取得します。
	 * @return niniLd_18nmA
	 */
	public String getNiniLd_18nmA() {
		return niniLd_18nmA;
	}

	/**
	 * niniLd_18nmA
	 * @param niniLd_18nmAを設定します。
	 */
	public void setNiniLd_18nmA(String niniLd_18nmA) {
		this.niniLd_18nmA = niniLd_18nmA;
	}

	/**
	 * niniLd_19cdAを取得します。
	 * @return niniLd_19cdA
	 */
	public String getNiniLd_19cdA() {
		return niniLd_19cdA;
	}

	/**
	 * niniLd_19cdA
	 * @param niniLd_19cdAを設定します。
	 */
	public void setNiniLd_19cdA(String niniLd_19cdA) {
		this.niniLd_19cdA = niniLd_19cdA;
	}

	/**
	 * niniLd_19nmAを取得します。
	 * @return niniLd_19nmA
	 */
	public String getNiniLd_19nmA() {
		return niniLd_19nmA;
	}

	/**
	 * niniLd_19nmA
	 * @param niniLd_19nmAを設定します。
	 */
	public void setNiniLd_19nmA(String niniLd_19nmA) {
		this.niniLd_19nmA = niniLd_19nmA;
	}

	/**
	 * niniLd_20cdAを取得します。
	 * @return niniLd_20cdA
	 */
	public String getNiniLd_20cdA() {
		return niniLd_20cdA;
	}

	/**
	 * niniLd_20cdA
	 * @param niniLd_20cdAを設定します。
	 */
	public void setNiniLd_20cdA(String niniLd_20cdA) {
		this.niniLd_20cdA = niniLd_20cdA;
	}

	/**
	 * niniLd_20nmAを取得します。
	 * @return niniLd_20nmA
	 */
	public String getNiniLd_20nmA() {
		return niniLd_20nmA;
	}

	/**
	 * niniLd_20nmA
	 * @param niniLd_20nmAを設定します。
	 */
	public void setNiniLd_20nmA(String niniLd_20nmA) {
		this.niniLd_20nmA = niniLd_20nmA;
	}

	/**
	 * kykkbnAを取得します。
	 * @return kykkbnA
	 */
	public String getKykkbnA() {
		return kykkbnA;
	}

	/**
	 * kykkbnA
	 * @param kykkbnAを設定します。
	 */
	public void setKykkbnA(String kykkbnA) {
		this.kykkbnA = kykkbnA;
	}

	/**
	 * lakaisuAを取得します。
	 * @return lakaisuA
	 */
	public Long getLakaisuA() {
		return lakaisuA;
	}

	/**
	 * lakaisuA
	 * @param lakaisuAを設定します。
	 */
	public void setLakaisuA(Long lakaisuA) {
		this.lakaisuA = lakaisuA;
	}

	/**
	 * oyaAを取得します。
	 * @return oyaA
	 */
	public String getOyaA() {
		return oyaA;
	}

	/**
	 * oyaA
	 * @param oyaAを設定します。
	 */
	public void setOyaA(String oyaA) {
		this.oyaA = oyaA;
	}

	/**
	 * edaAを取得します。
	 * @return edaA
	 */
	public String getEdaA() {
		return edaA;
	}

	/**
	 * edaA
	 * @param edaAを設定します。
	 */
	public void setEdaA(String edaA) {
		this.edaA = edaA;
	}

	/**
	 * shisanjotaikbnAを取得します。
	 * @return shisanjotaikbnA
	 */
	public String getShisanjotaikbnA() {
		return shisanjotaikbnA;
	}

	/**
	 * shisanjotaikbnA
	 * @param shisanjotaikbnAを設定します。
	 */
	public void setShisanjotaikbnA(String shisanjotaikbnA) {
		this.shisanjotaikbnA = shisanjotaikbnA;
	}

	/**
	 * shisannmcdAを取得します。
	 * @return shisannmcdA
	 */
	public String getShisannmcdA() {
		return shisannmcdA;
	}

	/**
	 * shisannmcdA
	 * @param shisannmcdAを設定します。
	 */
	public void setShisannmcdA(String shisannmcdA) {
		this.shisannmcdA = shisannmcdA;
	}

	/**
	 * shisannmAを取得します。
	 * @return shisannmA
	 */
	public String getShisannmA() {
		return shisannmA;
	}

	/**
	 * shisannmA
	 * @param shisannmAを設定します。
	 */
	public void setShisannmA(String shisannmA) {
		this.shisannmA = shisannmA;
	}

	/**
	 * shisannmkAを取得します。
	 * @return shisannmkA
	 */
	public String getShisannmkA() {
		return shisannmkA;
	}

	/**
	 * shisannmkA
	 * @param shisannmkAを設定します。
	 */
	public void setShisannmkA(String shisannmkA) {
		this.shisannmkA = shisannmkA;
	}

	/**
	 * konyucdAを取得します。
	 * @return konyucdA
	 */
	public String getKonyucdA() {
		return konyucdA;
	}

	/**
	 * konyucdA
	 * @param konyucdAを設定します。
	 */
	public void setKonyucdA(String konyucdA) {
		this.konyucdA = konyucdA;
	}

	/**
	 * suryoAを取得します。
	 * @return suryoA
	 */
	public Integer getSuryoA() {
		return suryoA;
	}

	/**
	 * suryoA
	 * @param suryoAを設定します。
	 */
	public void setSuryoA(Integer suryoA) {
		this.suryoA = suryoA;
	}

	/**
	 * taninmAを取得します。
	 * @return taninmA
	 */
	public String getTaninmA() {
		return taninmA;
	}

	/**
	 * taninmA
	 * @param taninmAを設定します。
	 */
	public void setTaninmA(String taninmA) {
		this.taninmA = taninmA;
	}

	/**
	 * lastymdAを取得します。
	 * @return lastymdA
	 */
	public String getLastymdA() {
		return lastymdA;
	}

	/**
	 * lastymdA
	 * @param lastymdAを設定します。
	 */
	public void setLastymdA(String lastymdA) {
		this.lastymdA = lastymdA;
	}

	/**
	 * lamanryoymdAを取得します。
	 * @return lamanryoymdA
	 */
	public String getLamanryoymdA() {
		return lamanryoymdA;
	}

	/**
	 * lamanryoymdA
	 * @param lamanryoymdAを設定します。
	 */
	public void setLamanryoymdA(String lamanryoymdA) {
		this.lamanryoymdA = lamanryoymdA;
	}

	/**
	 * kaiykymdAを取得します。
	 * @return kaiykymdA
	 */
	public String getKaiykymdA() {
		return kaiykymdA;
	}

	/**
	 * kaiykymdA
	 * @param kaiykymdAを設定します。
	 */
	public void setKaiykymdA(String kaiykymdA) {
		this.kaiykymdA = kaiykymdA;
	}

	/**
	 * bkngkAを取得します。
	 * @return bkngkA
	 */
	public Long getBkngkA() {
		return bkngkA;
	}

	/**
	 * bkngkA
	 * @param bkngkAを設定します。
	 */
	public void setBkngkA(Long bkngkA) {
		this.bkngkA = bkngkA;
	}

	/**
	 * gensonruigkAを取得します。
	 * @return gensonruigkA
	 */
	public Long getGensonruigkA() {
		return gensonruigkA;
	}

	/**
	 * gensonruigkA
	 * @param gensonruigkAを設定します。
	 */
	public void setGensonruigkA(Long gensonruigkA) {
		this.gensonruigkA = gensonruigkA;
	}

	/**
	 * gensontorikuzushizanAを取得します。
	 * @return gensontorikuzushizanA
	 */
	public Long getGensontorikuzushizanA() {
		return gensontorikuzushizanA;
	}

	/**
	 * gensontorikuzushizanA
	 * @param gensontorikuzushizanAを設定します。
	 */
	public void setGensontorikuzushizanA(Long gensontorikuzushizanA) {
		this.gensontorikuzushizanA = gensontorikuzushizanA;
	}

	/**
	 * mikeikalaryoPayAを取得します。
	 * @return mikeikalaryoPayA
	 */
	public Long getMikeikalaryoPayA() {
		return mikeikalaryoPayA;
	}

	/**
	 * mikeikalaryoPayA
	 * @param mikeikalaryoPayAを設定します。
	 */
	public void setMikeikalaryoPayA(Long mikeikalaryoPayA) {
		this.mikeikalaryoPayA = mikeikalaryoPayA;
	}

	/**
	 * zansotogkAを取得します。
	 * @return zansotogkA
	 */
	public Long getZansotogkA() {
		return zansotogkA;
	}

	/**
	 * zansotogkA
	 * @param zansotogkAを設定します。
	 */
	public void setZansotogkA(Long zansotogkA) {
		this.zansotogkA = zansotogkA;
	}

	/**
	 * laryoTotalAを取得します。
	 * @return laryoTotalA
	 */
	public Long getLaryoTotalA() {
		return laryoTotalA;
	}

	/**
	 * laryoTotalA
	 * @param laryoTotalAを設定します。
	 */
	public void setLaryoTotalA(Long laryoTotalA) {
		this.laryoTotalA = laryoTotalA;
	}

	/**
	 * maehrlaryoTorikuzushiZanAを取得します。
	 * @return maehrlaryoTorikuzushiZanA
	 */
	public Long getMaehrlaryoTorikuzushiZanA() {
		return maehrlaryoTorikuzushiZanA;
	}

	/**
	 * maehrlaryoTorikuzushiZanA
	 * @param maehrlaryoTorikuzushiZanAを設定します。
	 */
	public void setMaehrlaryoTorikuzushiZanA(Long maehrlaryoTorikuzushiZanA) {
		this.maehrlaryoTorikuzushiZanA = maehrlaryoTorikuzushiZanA;
	}

	/**
	 * knrbunruicdAを取得します。
	 * @return knrbunruicdA
	 */
	public String getKnrbunruicdA() {
		return knrbunruicdA;
	}

	/**
	 * knrbunruicdA
	 * @param knrbunruicdAを設定します。
	 */
	public void setKnrbunruicdA(String knrbunruicdA) {
		this.knrbunruicdA = knrbunruicdA;
	}

	/**
	 * skkhihfcdAを取得します。
	 * @return skkhihfcdA
	 */
	public String getSkkhihfcdA() {
		return skkhihfcdA;
	}

	/**
	 * skkhihfcdA
	 * @param skkhihfcdAを設定します。
	 */
	public void setSkkhihfcdA(String skkhihfcdA) {
		this.skkhihfcdA = skkhihfcdA;
	}

	/**
	 * skkhihfnmAを取得します。
	 * @return skkhihfnmA
	 */
	public String getSkkhihfnmA() {
		return skkhihfnmA;
	}

	/**
	 * skkhihfnmA
	 * @param skkhihfnmAを設定します。
	 */
	public void setSkkhihfnmA(String skkhihfnmA) {
		this.skkhihfnmA = skkhihfnmA;
	}

	/**
	 * laryohfcdAを取得します。
	 * @return laryohfcdA
	 */
	public String getLaryohfcdA() {
		return laryohfcdA;
	}

	/**
	 * laryohfcdA
	 * @param laryohfcdAを設定します。
	 */
	public void setLaryohfcdA(String laryohfcdA) {
		this.laryohfcdA = laryohfcdA;
	}

	/**
	 * laryohfnmAを取得します。
	 * @return laryohfnmA
	 */
	public String getLaryohfnmA() {
		return laryohfnmA;
	}

	/**
	 * laryohfnmA
	 * @param laryohfnmAを設定します。
	 */
	public void setLaryohfnmA(String laryohfnmA) {
		this.laryohfnmA = laryohfnmA;
	}

	/**
	 * biko1Aを取得します。
	 * @return biko1A
	 */
	public String getBiko1A() {
		return biko1A;
	}

	/**
	 * biko1A
	 * @param biko1Aを設定します。
	 */
	public void setBiko1A(String biko1A) {
		this.biko1A = biko1A;
	}

	/**
	 * biko2Aを取得します。
	 * @return biko2A
	 */
	public String getBiko2A() {
		return biko2A;
	}

	/**
	 * biko2A
	 * @param biko2Aを設定します。
	 */
	public void setBiko2A(String biko2A) {
		this.biko2A = biko2A;
	}

	/**
	 * ikoyoteiumukbnAを取得します。
	 * @return ikoyoteiumukbnA
	 */
	public String getIkoyoteiumukbnA() {
		return ikoyoteiumukbnA;
	}

	/**
	 * ikoyoteiumukbnA
	 * @param ikoyoteiumukbnAを設定します。
	 */
	public void setIkoyoteiumukbnA(String ikoyoteiumukbnA) {
		this.ikoyoteiumukbnA = ikoyoteiumukbnA;
	}

	/**
	 * shisanknrkbnAを取得します。
	 * @return shisanknrkbnA
	 */
	public String getShisanknrkbnA() {
		return shisanknrkbnA;
	}

	/**
	 * shisanknrkbnA
	 * @param shisanknrkbnAを設定します。
	 */
	public void setShisanknrkbnA(String shisanknrkbnA) {
		this.shisanknrkbnA = shisanknrkbnA;
	}

	/**
	 * zansotogkPayAを取得します。
	 * @return zansotogkPayA
	 */
	public Long getZansotogkPayA() {
		return zansotogkPayA;
	}

	/**
	 * zansotogkPayA
	 * @param zansotogkPayAを設定します。
	 */
	public void setZansotogkPayA(Long zansotogkPayA) {
		this.zansotogkPayA = zansotogkPayA;
	}

	/**
	 * updkaisuAを取得します。
	 * @return updkaisuA
	 */
	public Long getUpdkaisuA() {
		return updkaisuA;
	}

	/**
	 * updkaisuA
	 * @param updkaisuAを設定します。
	 */
	public void setUpdkaisuA(Long updkaisuA) {
		this.updkaisuA = updkaisuA;
	}

	/**
	 * updkaishacdAを取得します。
	 * @return updkaishacdA
	 */
	public String getUpdkaishacdA() {
		return updkaishacdA;
	}

	/**
	 * updkaishacdA
	 * @param updkaishacdAを設定します。
	 */
	public void setUpdkaishacdA(String updkaishacdA) {
		this.updkaishacdA = updkaishacdA;
	}

	/**
	 * updidAを取得します。
	 * @return updidA
	 */
	public String getUpdidA() {
		return updidA;
	}

	/**
	 * updidA
	 * @param updidAを設定します。
	 */
	public void setUpdidA(String updidA) {
		this.updidA = updidA;
	}

	/**
	 * updymdAを取得します。
	 * @return updymdA
	 */
	public String getUpdymdA() {
		return updymdA;
	}

	/**
	 * updymdA
	 * @param updymdAを設定します。
	 */
	public void setUpdymdA(String updymdA) {
		this.updymdA = updymdA;
	}

	/**
	 * updtimeAを取得します。
	 * @return updtimeA
	 */
	public String getUpdtimeA() {
		return updtimeA;
	}

	/**
	 * updtimeA
	 * @param updtimeAを設定します。
	 */
	public void setUpdtimeA(String updtimeA) {
		this.updtimeA = updtimeA;
	}

	/**
	 * kykkbnCNameを取得します。
	 * @return kykkbnCName
	 */
	public String getKykkbnCName() {
		return kykkbnCName;
	}

	/**
	 * kykkbnCName
	 * @param kykkbnCNameを設定します。
	 */
	public void setKykkbnCName(String kykkbnCName) {
		this.kykkbnCName = kykkbnCName;
	}

	/**
	 * kykjotaikbnCNameを取得します。
	 * @return kykjotaikbnCName
	 */
	public String getKykjotaikbnCName() {
		return kykjotaikbnCName;
	}

	/**
	 * kykjotaikbnCName
	 * @param kykjotaikbnCNameを設定します。
	 */
	public void setKykjotaikbnCName(String kykjotaikbnCName) {
		this.kykjotaikbnCName = kykjotaikbnCName;
	}

	/**
	 * kintokbnCNameを取得します。
	 * @return kintokbnCName
	 */
	public String getKintokbnCName() {
		return kintokbnCName;
	}

	/**
	 * kintokbnCName
	 * @param kintokbnCNameを設定します。
	 */
	public void setKintokbnCName(String kintokbnCName) {
		this.kintokbnCName = kintokbnCName;
	}

	/**
	 * latorihikikbnCNameを取得します。
	 * @return latorihikikbnCName
	 */
	public String getLatorihikikbnCName() {
		return latorihikikbnCName;
	}

	/**
	 * latorihikikbnCName
	 * @param latorihikikbnCNameを設定します。
	 */
	public void setLatorihikikbnCName(String latorihikikbnCName) {
		this.latorihikikbnCName = latorihikikbnCName;
	}

	/**
	 * bskeijokbnCNameを取得します。
	 * @return bskeijokbnCName
	 */
	public String getBskeijokbnCName() {
		return bskeijokbnCName;
	}

	/**
	 * bskeijokbnCName
	 * @param bskeijokbnCNameを設定します。
	 */
	public void setBskeijokbnCName(String bskeijokbnCName) {
		this.bskeijokbnCName = bskeijokbnCName;
	}

	/**
	 * chukikbnCNameを取得します。
	 * @return chukikbnCName
	 */
	public String getChukikbnCName() {
		return chukikbnCName;
	}

	/**
	 * chukikbnCName
	 * @param chukikbnCNameを設定します。
	 */
	public void setChukikbnCName(String chukikbnCName) {
		this.chukikbnCName = chukikbnCName;
	}

	/**
	 * lastymdAFを取得します。
	 * @return lastymdAF
	 */
	public String getLastymdAF() {
		return lastymdAF;
	}

	/**
	 * lastymdAF
	 * @param lastymdAFを設定します。
	 */
	public void setLastymdAF(String lastymdAF) {
		this.lastymdAF = lastymdAF;
	}

	/**
	 * lamanryoymdAFを取得します。
	 * @return lamanryoymdAF
	 */
	public String getLamanryoymdAF() {
		return lamanryoymdAF;
	}

	/**
	 * lamanryoymdAF
	 * @param lamanryoymdAFを設定します。
	 */
	public void setLamanryoymdAF(String lamanryoymdAF) {
		this.lamanryoymdAF = lamanryoymdAF;
	}

	/**
	 * kaiykymdAFを取得します。
	 * @return kaiykymdAF
	 */
	public String getKaiykymdAF() {
		return kaiykymdAF;
	}

	/**
	 * kaiykymdAF
	 * @param kaiykymdAFを設定します。
	 */
	public void setKaiykymdAF(String kaiykymdAF) {
		this.kaiykymdAF = kaiykymdAF;
	}

	/**
	 * updymdAFを取得します。
	 * @return updymdAF
	 */
	public String getUpdymdAF() {
		return updymdAF;
	}

	/**
	 * updymdAF
	 * @param updymdAFを設定します。
	 */
	public void setUpdymdAF(String updymdAF) {
		this.updymdAF = updymdAF;
	}

	/**
	 * kykymdCFを取得します。
	 * @return kykymdCF
	 */
	public String getKykymdCF() {
		return kykymdCF;
	}

	/**
	 * kykymdCF
	 * @param kykymdCFを設定します。
	 */
	public void setKykymdCF(String kykymdCF) {
		this.kykymdCF = kykymdCF;
	}

	/**
	 * lastymdCFを取得します。
	 * @return lastymdCF
	 */
	public String getLastymdCF() {
		return lastymdCF;
	}

	/**
	 * lastymdCF
	 * @param lastymdCFを設定します。
	 */
	public void setLastymdCF(String lastymdCF) {
		this.lastymdCF = lastymdCF;
	}

	/**
	 * lamanryoymdCFを取得します。
	 * @return lamanryoymdCF
	 */
	public String getLamanryoymdCF() {
		return lamanryoymdCF;
	}

	/**
	 * lamanryoymdCF
	 * @param lamanryoymdCFを設定します。
	 */
	public void setLamanryoymdCF(String lamanryoymdCF) {
		this.lamanryoymdCF = lamanryoymdCF;
	}

	/**
	 * shrmanryoymdCFを取得します。
	 * @return shrmanryoymdCF
	 */
	public String getShrmanryoymdCF() {
		return shrmanryoymdCF;
	}

	/**
	 * shrmanryoymdCF
	 * @param shrmanryoymdCFを設定します。
	 */
	public void setShrmanryoymdCF(String shrmanryoymdCF) {
		this.shrmanryoymdCF = shrmanryoymdCF;
	}

	/**
	 * kaiykymdCFを取得します。
	 * @return kaiykymdCF
	 */
	public String getKaiykymdCF() {
		return kaiykymdCF;
	}

	/**
	 * kaiykymdCF
	 * @param kaiykymdCFを設定します。
	 */
	public void setKaiykymdCF(String kaiykymdCF) {
		this.kaiykymdCF = kaiykymdCF;
	}

	/**
	 * updymdCFを取得します。
	 * @return updymdCF
	 */
	public String getUpdymdCF() {
		return updymdCF;
	}

	/**
	 * updymdCF
	 * @param updymdCFを設定します。
	 */
	public void setUpdymdCF(String updymdCF) {
		this.updymdCF = updymdCF;
	}

	/**
	 * assetListを取得します。
	 * @return assetList
	 */
	public List<AssetSR> getAssetList() {
		return assetList;
	}

	/**
	 * assetList
	 * @param assetListを設定します。
	 */
	public void setAssetList(List<AssetSR> assetList) {
		this.assetList = assetList;
	}

	/**
	 * licenseListを取得します。
	 * @return licenseList
	 */
	public List<LicenseSR> getLicenseList() {
		return licenseList;
	}

	/**
	 * licenseList
	 * @param licenseListを設定します。
	 */
	public void setLicenseList(List<LicenseSR> licenseList) {
		this.licenseList = licenseList;
	}

	/**
	 * shisanjotaikbnANameを取得します。
	 * @return shisanjotaikbnAName
	 */
	public String getShisanjotaikbnAName() {
		return shisanjotaikbnAName;
	}

	/**
	 * shisanjotaikbnAName
	 * @param shisanjotaikbnANameを設定します。
	 */
	public void setShisanjotaikbnAName(String shisanjotaikbnAName) {
		this.shisanjotaikbnAName = shisanjotaikbnAName;
	}

	/**
	 * lakaishacdCNameを取得します。
	 * @return lakaishacdCName
	 */
	public String getLakaishacdCName() {
		return lakaishacdCName;
	}

	/**
	 * lakaishacdCName
	 * @param lakaishacdCNameを設定します。
	 */
	public void setLakaishacdCName(String lakaishacdCName) {
		this.lakaishacdCName = lakaishacdCName;
	}

	/**
	 * shisanNumAを取得します。
	 * @return shisanNumA
	 */
	public String getShisanNumA() {
		return shisanNumA;
	}

	/**
	 * shisanNumA
	 * @param shisanNumAを設定します。
	 */
	public void setShisanNumA(String shisanNumA) {
		this.shisanNumA = shisanNumA;
	}

	/**
	 * costSecHistListを取得します。
	 * @return costSecHistList
	 */
	public List<PpfsFldSupport> getCostSecHistList() {
		return costSecHistList;
	}

	/**
	 * costSecHistList
	 * @param costSecHistListを設定します。
	 */
	public void setCostSecHistList(List<PpfsFldSupport> costSecHistList) {
		this.costSecHistList = costSecHistList;
	}

	/**
	 * latorihikikbnLRCNameを取得します。
	 * @return latorihikikbnLRCName
	 */
	public String getLatorihikikbnLRCName() {
		return latorihikikbnLRCName;
	}

	/**
	 * latorihikikbnLRCName
	 * @param latorihikikbnLRCNameを設定します。
	 */
	public void setLatorihikikbnLRCName(String latorihikikbnLRCName) {
		this.latorihikikbnLRCName = latorihikikbnLRCName;
	}

	/**
	 * kykkbnANameを取得します。
	 * @return kykkbnAName
	 */
	public String getKykkbnAName() {
		return kykkbnAName;
	}

	/**
	 * kykkbnAName
	 * @param kykkbnANameを設定します。
	 */
	public void setKykkbnAName(String kykkbnAName) {
		this.kykkbnAName = kykkbnAName;
	}

	/**
	 * shisanknrkbnANameを取得します。
	 * @return shisanknrkbnAName
	 */
	public String getShisanknrkbnAName() {
		return shisanknrkbnAName;
	}

	/**
	 * shisanknrkbnAName
	 * @param shisanknrkbnANameを設定します。
	 */
	public void setShisanknrkbnAName(String shisanknrkbnAName) {
		this.shisanknrkbnAName = shisanknrkbnAName;
	}

	/**
	 * ikkailaryoAを取得します。
	 * @return ikkailaryoA
	 */
	public Long getIkkailaryoA() {
		return ikkailaryoA;
	}

	/**
	 * ikkailaryoA
	 * @param ikkailaryoAを設定します。
	 */
	public void setIkkailaryoA(Long ikkailaryoA) {
		this.ikkailaryoA = ikkailaryoA;
	}

	/**
	 * zansotogkPayDecAを取得します。
	 * @return zansotogkPayDecA
	 */
	public Long getZansotogkPayDecA() {
		return zansotogkPayDecA;
	}

	/**
	 * zansotogkPayDecA
	 * @param zansotogkPayDecAを設定します。
	 */
	public void setZansotogkPayDecA(Long zansotogkPayDecA) {
		this.zansotogkPayDecA = zansotogkPayDecA;
	}

	/**
	 * mikeikalaryoPayDecAを取得します。
	 * @return mikeikalaryoPayDecA
	 */
	public Long getMikeikalaryoPayDecA() {
		return mikeikalaryoPayDecA;
	}

	/**
	 * mikeikalaryoPayDecA
	 * @param mikeikalaryoPayDecAを設定します。
	 */
	public void setMikeikalaryoPayDecA(Long mikeikalaryoPayDecA) {
		this.mikeikalaryoPayDecA = mikeikalaryoPayDecA;
	}

	/**
	 * lagensonymdAを取得します。
	 * @return lagensonymdA
	 */
	public String getLagensonymdA() {
		return lagensonymdA;
	}

	/**
	 * lagensonymdA
	 * @param lagensonymdAを設定します。
	 */
	public void setLagensonymdA(String lagensonymdA) {
		this.lagensonymdA = lagensonymdA;
	}

	/**
	 * ksgensonruigkAを取得します。
	 * @return ksgensonruigkA
	 */
	public Long getKsgensonruigkA() {
		return ksgensonruigkA;
	}

	/**
	 * ksgensonruigkA
	 * @param ksgensonruigkAを設定します。
	 */
	public void setKsgensonruigkA(Long ksgensonruigkA) {
		this.ksgensonruigkA = ksgensonruigkA;
	}

	/**
	 * ksgensontorikuzushizanAを取得します。
	 * @return ksgensontorikuzushizanA
	 */
	public Long getKsgensontorikuzushizanA() {
		return ksgensontorikuzushizanA;
	}

	/**
	 * ksgensontorikuzushizanA
	 * @param ksgensontorikuzushizanAを設定します。
	 */
	public void setKsgensontorikuzushizanA(Long ksgensontorikuzushizanA) {
		this.ksgensontorikuzushizanA = ksgensontorikuzushizanA;
	}

	/**
	 * lagensonymdAFを取得します。
	 * @return lagensonymdAF
	 */
	public String getLagensonymdAF() {
		return lagensonymdAF;
	}

	/**
	 * lagensonymdAF
	 * @param lagensonymdAFを設定します。
	 */
	public void setLagensonymdAF(String lagensonymdAF) {
		this.lagensonymdAF = lagensonymdAF;
	}

	/**
	 * zankahoshogkCを取得します。
	 * @return zankahoshogkC
	 */
	public Long getZankahoshogkC() {
		return zankahoshogkC;
	}

	/**
	 * zankahoshogkC
	 * @param zankahoshogkCを設定します。
	 */
	public void setZankahoshogkC(Long zankahoshogkC) {
		this.zankahoshogkC = zankahoshogkC;
	}

	/**
	 * mitsumorikonyugkCを取得します。
	 * @return mitsumorikonyugkC
	 */
	public Long getMitsumorikonyugkC() {
		return mitsumorikonyugkC;
	}

	/**
	 * mitsumorikonyugkC
	 * @param mitsumorikonyugkCを設定します。
	 */
	public void setMitsumorikonyugkC(Long mitsumorikonyugkC) {
		this.mitsumorikonyugkC = mitsumorikonyugkC;
	}

	/**
	 * keizaitainenCを取得します。
	 * @return keizaitainenC
	 */
	public Long getKeizaitainenC() {
		return keizaitainenC;
	}

	/**
	 * keizaitainenC
	 * @param keizaitainenCを設定します。
	 */
	public void setKeizaitainenC(Long keizaitainenC) {
		this.keizaitainenC = keizaitainenC;
	}

	/**
	 * juyoseikbnCを取得します。
	 * @return juyoseikbnC
	 */
	public String getJuyoseikbnC() {
		return juyoseikbnC;
	}

	/**
	 * juyoseikbnC
	 * @param juyoseikbnCを設定します。
	 */
	public void setJuyoseikbnC(String juyoseikbnC) {
		this.juyoseikbnC = juyoseikbnC;
	}

	/**
	 * tekiyoritsuPayCを取得します。
	 * @return tekiyoritsuPayC
	 */
	public Double getTekiyoritsuPayC() {
		return tekiyoritsuPayC;
	}

	/**
	 * tekiyoritsuPayC
	 * @param tekiyoritsuPayCを設定します。
	 */
	public void setTekiyoritsuPayC(Double tekiyoritsuPayC) {
		this.tekiyoritsuPayC = tekiyoritsuPayC;
	}

	/**
	 * kaikeishorikbnCを取得します。
	 * @return kaikeishorikbnC
	 */
	public String getKaikeishorikbnC() {
		return kaikeishorikbnC;
	}

	/**
	 * kaikeishorikbnC
	 * @param kaikeishorikbnCを設定します。
	 */
	public void setKaikeishorikbnC(String kaikeishorikbnC) {
		this.kaikeishorikbnC = kaikeishorikbnC;
	}

	/**
	 * shrstymdCを取得します。
	 * @return shrstymdC
	 */
	public String getShrstymdC() {
		return shrstymdC;
	}

	/**
	 * shrstymdC
	 * @param shrstymdCを設定します。
	 */
	public void setShrstymdC(String shrstymdC) {
		this.shrstymdC = shrstymdC;
	}

	/**
	 * zenshrymdCを取得します。
	 * @return zenshrymdC
	 */
	public String getZenshrymdC() {
		return zenshrymdC;
	}

	/**
	 * zenshrymdC
	 * @param zenshrymdCを設定します。
	 */
	public void setZenshrymdC(String zenshrymdC) {
		this.zenshrymdC = zenshrymdC;
	}

	/**
	 * shohizeiritsuCを取得します。
	 * @return shohizeiritsuC
	 */
	public Double getShohizeiritsuC() {
		return shohizeiritsuC;
	}

	/**
	 * shohizeiritsuC
	 * @param shohizeiritsuCを設定します。
	 */
	public void setShohizeiritsuC(Double shohizeiritsuC) {
		this.shohizeiritsuC = shohizeiritsuC;
	}

	/**
	 * shokailaryoKomiCを取得します。
	 * @return shokailaryoKomiC
	 */
	public Long getShokailaryoKomiC() {
		return shokailaryoKomiC;
	}

	/**
	 * shokailaryoKomiC
	 * @param shokailaryoKomiCを設定します。
	 */
	public void setShokailaryoKomiC(Long shokailaryoKomiC) {
		this.shokailaryoKomiC = shokailaryoKomiC;
	}

	/**
	 * lastlaryoCを取得します。
	 * @return lastlaryoC
	 */
	public Long getLastlaryoC() {
		return lastlaryoC;
	}

	/**
	 * lastlaryoC
	 * @param lastlaryoCを設定します。
	 */
	public void setLastlaryoC(Long lastlaryoC) {
		this.lastlaryoC = lastlaryoC;
	}

	/**
	 * lastlaryoKomiCを取得します。
	 * @return lastlaryoKomiC
	 */
	public Long getLastlaryoKomiC() {
		return lastlaryoKomiC;
	}

	/**
	 * lastlaryoKomiC
	 * @param lastlaryoKomiCを設定します。
	 */
	public void setLastlaryoKomiC(Long lastlaryoKomiC) {
		this.lastlaryoKomiC = lastlaryoKomiC;
	}

	/**
	 * hikiotoshistkaisuCを取得します。
	 * @return hikiotoshistkaisuC
	 */
	public Long getHikiotoshistkaisuC() {
		return hikiotoshistkaisuC;
	}

	/**
	 * hikiotoshistkaisuC
	 * @param hikiotoshistkaisuCを設定します。
	 */
	public void setHikiotoshistkaisuC(Long hikiotoshistkaisuC) {
		this.hikiotoshistkaisuC = hikiotoshistkaisuC;
	}

	/**
	 * maehrlammsuCを取得します。
	 * @return maehrlammsuC
	 */
	public Long getMaehrlammsuC() {
		return maehrlammsuC;
	}

	/**
	 * maehrlammsuC
	 * @param maehrlammsuCを設定します。
	 */
	public void setMaehrlammsuC(Long maehrlammsuC) {
		this.maehrlammsuC = maehrlammsuC;
	}

	/**
	 * zansotogkPayDecCを取得します。
	 * @return zansotogkPayDecC
	 */
	public Long getZansotogkPayDecC() {
		return zansotogkPayDecC;
	}

	/**
	 * zansotogkPayDecC
	 * @param zansotogkPayDecCを設定します。
	 */
	public void setZansotogkPayDecC(Long zansotogkPayDecC) {
		this.zansotogkPayDecC = zansotogkPayDecC;
	}

	/**
	 * mikeikalaryoPayDecCを取得します。
	 * @return mikeikalaryoPayDecC
	 */
	public Long getMikeikalaryoPayDecC() {
		return mikeikalaryoPayDecC;
	}

	/**
	 * mikeikalaryoPayDecC
	 * @param mikeikalaryoPayDecCを設定します。
	 */
	public void setMikeikalaryoPayDecC(Long mikeikalaryoPayDecC) {
		this.mikeikalaryoPayDecC = mikeikalaryoPayDecC;
	}

	/**
	 * kihonsparen5Cを取得します。
	 * @return kihonsparen5C
	 */
	public Long getKihonsparen5C() {
		return kihonsparen5C;
	}

	/**
	 * kihonsparen5C
	 * @param kihonsparen5Cを設定します。
	 */
	public void setKihonsparen5C(Long kihonsparen5C) {
		this.kihonsparen5C = kihonsparen5C;
	}

	/**
	 * shrstymdCFを取得します。
	 * @return shrstymdCF
	 */
	public String getShrstymdCF() {
		return shrstymdCF;
	}

	/**
	 * shrstymdCF
	 * @param shrstymdCFを設定します。
	 */
	public void setShrstymdCF(String shrstymdCF) {
		this.shrstymdCF = shrstymdCF;
	}

	/**
	 * zenshrymdCFを取得します。
	 * @return zenshrymdCF
	 */
	public String getZenshrymdCF() {
		return zenshrymdCF;
	}

	/**
	 * zenshrymdCF
	 * @param zenshrymdCFを設定します。
	 */
	public void setZenshrymdCF(String zenshrymdCF) {
		this.zenshrymdCF = zenshrymdCF;
	}

	/**
	 * laryoKomiCを取得します。
	 * @return laryoKomiC
	 */
	public Long getLaryoKomiC() {
		return laryoKomiC;
	}

	/**
	 * laryoKomiC
	 * @param laryoKomiCを設定します。
	 */
	public void setLaryoKomiC(Long laryoKomiC) {
		this.laryoKomiC = laryoKomiC;
	}

	/**
	 * shrcyclekbnCNameを取得します。
	 * @return shrcyclekbnCName
	 */
	public String getShrcyclekbnCName() {
		return shrcyclekbnCName;
	}

	/**
	 * shrcyclekbnCName
	 * @param shrcyclekbnCNameを設定します。
	 */
	public void setShrcyclekbnCName(String shrcyclekbnCName) {
		this.shrcyclekbnCName = shrcyclekbnCName;
	}

	/**
	 * kaikeishorikbnCNameを取得します。
	 * @return kaikeishorikbnCName
	 */
	public String getKaikeishorikbnCName() {
		return kaikeishorikbnCName;
	}

	/**
	 * kaikeishorikbnCName
	 * @param kaikeishorikbnCNameを設定します。
	 */
	public void setKaikeishorikbnCName(String kaikeishorikbnCName) {
		this.kaikeishorikbnCName = kaikeishorikbnCName;
	}

	/**
	 * juyoseikbnCNameを取得します。
	 * @return juyoseikbnCName
	 */
	public String getJuyoseikbnCName() {
		return juyoseikbnCName;
	}

	/**
	 * juyoseikbnCName
	 * @param juyoseikbnCNameを設定します。
	 */
	public void setJuyoseikbnCName(String juyoseikbnCName) {
		this.juyoseikbnCName = juyoseikbnCName;
	}

	/**
	 * niniLd_15cdAFを取得します。
	 * @return niniLd_15cdAF
	 */
	public String getNiniLd_15cdAF() {
		return niniLd_15cdAF;
	}

	/**
	 * niniLd_15cdAF
	 * @param niniLd_15cdAFを設定します。
	 */
	public void setNiniLd_15cdAF(String niniLd_15cdAF) {
		this.niniLd_15cdAF = niniLd_15cdAF;
	}

	/**
	 * niniLd_16cdAFを取得します。
	 * @return niniLd_16cdAF
	 */
	public String getNiniLd_16cdAF() {
		return niniLd_16cdAF;
	}

	/**
	 * niniLd_16cdAF
	 * @param niniLd_16cdAFを設定します。
	 */
	public void setNiniLd_16cdAF(String niniLd_16cdAF) {
		this.niniLd_16cdAF = niniLd_16cdAF;
	}

	/**
	 * zanzongkkAを取得します。
	 * @return zanzongkkA
	 */
	public Long getZanzongkkA() {
		return zanzongkkA;
	}

	/**
	 * zanzongkkA
	 * @param zanzongkkAを設定します。
	 */
	public void setZanzongkkA(Long zanzongkkA) {
		this.zanzongkkA = zanzongkkA;
	}

	/**
	 * rekikanAを取得します。
	 * @return rekikanA
	 */
	public Long getRekikanA() {
		return rekikanA;
	}

	/**
	 * rekikanA
	 * @param rekikanAを設定します。
	 */
	public void setRekikanA(Long rekikanA) {
		this.rekikanA = rekikanA;
	}

	public Long getStkgkkA() {
		return stkgkkA;
	}

	public void setStkgkkA(Long stkgkkA) {
		this.stkgkkA = stkgkkA;
	}

	public Long getToBokakA() {
		return toBokakA;
	}

	public void setToBokakA(Long toBokakA) {
		this.toBokakA = toBokakA;
	}

	/**
	 * @return the zansotogkPayC
	 */
	public Long getZansotogkPayC() {
		return zansotogkPayC;
	}

	/**
	 * @param zansotogkPayC the zansotogkPayC to set
	 */
	public void setZansotogkPayC(Long zansotogkPayC) {
		this.zansotogkPayC = zansotogkPayC;
	}

}
