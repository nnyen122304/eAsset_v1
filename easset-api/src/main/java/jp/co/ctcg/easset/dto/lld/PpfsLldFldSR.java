/*===================================================================
 * ファイル名 : PpfsLldFldSR.java
 * 概要説明   : リース・レンタル台帳（固定資産情報付加）
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-11-07 1.0  リヨン 崔     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.lld;

import lombok.ToString;

@ToString(callSuper = true)
public class PpfsLldFldSR extends PpfsLldSR {
	private static final long serialVersionUID = 1L;

	//////////////////////////////////////////// 以下固定資産(PpfsFld + PpfsSR)のプロパティをコピー

	//////////// PpfsFld
	//	固有資産
	private Long koyuno; // 固有番号
	private String kaishacd; // 会社コード
	private String kaishanm; // 会社名称
	private String oya; // 資産番号・親
	private String eda; // 資産番号・枝
	private String shisanknrkbn; // 資産管理区分
	private String shisanjotaikbn; // 資産状態区分(固定資産)
	private String shisannmcd; // 資産名称コード
	private String shisannm; // 資産名称
	private String shisannmk; // 資産名称カナ
	private String soshiki1cd; // 組織1コード
	private String soshiki1nm; // 組織1名称
	private String soshiki2cd; // 組織2コード
	private String soshiki2nm; // 組織2名称
	private String soshiki3cd; // 組織3コード
	private String soshiki3nm; // 組織3名称
	private String soshiki4cd; // 組織4コード
	private String soshiki4nm; // 組織4名称
	private String setchicd; // 設置場所コード
	private String setchinm; // 設置場所名称
	private String skkhihfcd; // 償却費配賦コード
	private String hfnm; // 償却費配賦名称
	private String laryohfcd; // リース料配賦コード
	private String laryohfnm; // リース料配賦名称
	private String shuruicd; // 種類コード
	private String shuruinm; // 種類名称
	private String kozocd; // 構造用途コード
	private String kozonm; // 構造用途名称
	private String bunruicd; // 分類コード
	private String bunruinm; // 分類名称
	private String cpkeijoymd; // 建仮計上年月日
	private String furikaeymd; // 振替年月日
	private String furikaegoOya; // 振替後資産番号・親
	private String furikaegoEda; // 振替後資産番号・枝
	private String furikaegoShisanNum; // 振替後資産番号
	private String stkymd; // 取得年月日
	private String kadoymd; // 稼働年月日
	private String jbkymd; // 除売却年月日
	private String idoymd; // 移動年月日
	private Integer suryo; // 数量
	private String suryoTani; // 数量単位
	private Integer menseki; // 面積
	private String mensekiTani; // 面積単位
	private String chukokbn; // 中古区分
	private String tosaimukeijokbn; // 当期債務計上区分
	private String jksaimukeijokbn; // 除去債務計上区分
	private Long stkgkk; // 取得価額(会社帳簿)
	private Long askstkgkk; // 圧縮後取得価額(会社帳簿)
	private String skkhok; // 償却方法(会社帳簿)
	private Integer tainenk; // 耐用年数(会社帳簿)
	private Double skkritsuk; // 償却率(会社帳簿)
	private Integer skkmmsuk; // 償却月数(会社帳簿)
	private Integer zanzonmmsuk; // 残存月数(会社帳簿)
	private Long ksbokak; // 期首帳簿価額(会社帳簿)
	private Long skkcalckisogkk; // 償却計算基礎額(会社帳簿)
	private Double zanzonritsuk; // 残存率(会社帳簿)
	private Long zanzongkk; // 残存価額(会社帳簿)
	private Long ksftskkruigkk; // 期首普通償却累計額(会社帳簿)
	private Long kszkskkruigkk; // 期首増加償却累計額(会社帳簿)
	private Long kstkskkruigkk; // 期首特別償却累計額(会社帳簿)
	private Long zenToftskkgkk; // 当期普通償却額(前月累計)(会社帳簿)
	private Long zenTozkskkgkk; // 当期増加償却額(前月累計)(会社帳簿)
	private Long zenTotkskkgkk; // 当期特別償却額(前月累計)(会社帳簿)
	private Long zenToniniskkgkk; // 当期任意償却額(前月)(会社帳簿)
	private Long toToftskkgkk; // 当期普通償却額(当月累計)(会社帳簿)
	private Long toTozkskkgkk; // 当期増加償却額(当月累計)(会社帳簿)
	private Long toTotkskkgkk; // 当期特別償却額(当月累計)(会社帳簿)
	private Long toToniniskkgkk; // 当期任意償却額(当月)(会社帳簿)
	private Long toBokak; // 当月末帳簿価額(会社帳簿)
	private Long zogenbokak; // 増減帳簿価額(会社帳簿)
	private Double zkskkritsuk; // 増加償却率(会社帳簿)
	private String yukyustymk; // 遊休開始年月(会社帳簿)
	private String yukyufkymk; // 遊休復帰年月(会社帳簿)
	private String shonencalckbnk; // 初年度計算区分(会社帳簿)
	private String skkkanryoflgk; // 償却完了フラグ(会社帳簿)
	private String skkkirikaeyyk; // 償却切替年度(会社帳簿)
	private String zanzonskkstyyk; // 残存償却開始年度(会社帳簿)
	private Integer zanzonskkmmsuk; // 残存償却月数(会社帳簿)
	private String famitsumorihkymdk; // 固定資産見積変更年月日(会社帳簿)
	private Long mitsumorihkgobokak; // 見積変更後簿価(会社帳簿)
	private Long mitsumorihkmaetainenk; // 見積変更前耐用年数(会社帳簿)
	private Long mitsumorihkmaeskkmmsuk; // 見積変更前償却月数(会社帳簿)
	private Long mitsumorihkmaeskkritsuk; // 見積変更前償却率(会社帳簿)
	private Long stkgkz; // 取得価額(税法帳簿)
	private Long askstkgkz; // 圧縮後取得価額(税法帳簿)
	private String skkhoz; // 償却方法(税法帳簿)
	private Integer tainenz; // 耐用年数(税法帳簿)
	private Double skkritsuz; // 償却率(税法帳簿)
	private Integer skkmmsuz; // 償却月数(税法帳簿)
	private Integer zanzonmmsuz; // 残存月数(税法帳簿)
	private Long ksbokaz; // 期首帳簿価額(税法帳簿)
	private Long skkcalckisogkz; // 償却計算基礎額(税法帳簿)
	private Double zanzonritsuz; // 残存率(税法帳簿)
	private Long zanzongkz; // 残存価額(税法帳簿)
	private Long ksftskkruigkz; // 期首普通償却累計額(税法帳簿)
	private Long kszkskkruigkz; // 期首増加償却累計額(税法帳簿)
	private Long kstkskkruigkz; // 期首特別償却累計額(税法帳簿)
	private Long zenToftskkgkz; // 当期普通償却額(前月累計)(税法帳簿)
	private Long zenTozkskkgkz; // 当期増加償却額(前月累計)(税法帳簿)
	private Long zenTotkskkgkz; // 当期特別償却額(前月累計)(税法帳簿)
	private Long zenToniniskkgkz; // 当期任意償却額(前月)(税法帳簿)
	private Long toToftskkgkz; // 当期普通償却額(当月累計)(税法帳簿)
	private Long toTozkskkgkz; // 当期増加償却額(当月累計)(税法帳簿)
	private Long toTotkskkgkz; // 当期特別償却額(当月累計)(税法帳簿)
	private Long toToniniskkgkz; // 当期任意償却額(当月)(税法帳簿)
	private Long toBokaz; // 当月末帳簿価額(税法帳簿)
	private Long zogenbokaz; // 増減帳簿価額(税法帳簿)
	private Double zkskkritsuz; // 増加償却率(税法帳簿)
	private String yukyustymz; // 遊休開始年月(税法帳簿)
	private String yukyufkymz; // 遊休復帰年月(税法帳簿)
	private String shonencalckbnz; // 初年度計算区分(税法帳簿)
	private String skkkanryoflgz; // 償却完了(税法帳簿)
	private String skkkirikaeyyz; // 償却切替年度(税法帳簿)
	private String zanzonskkstyyz; // 残存償却開始年度(税法帳簿)
	private Integer zanzonskkmmsuz; // 残存償却月数(税法帳簿)
	private String famitsumorihkymdz; // 固定資産見積変更年月日(税法帳簿)
	private Long mitsumorihkgobokaz; // 見積変更後簿価(税法帳簿)
	private Long mitsumorihkmaetainenz; // 見積変更前耐用年数(税法帳簿)
	private Long mitsumorihkmaeskkmmsuz; // 見積変更前償却月数(税法帳簿)
	private Long mitsumorihkmaeskkritsuz; // 見積変更前償却率(税法帳簿)
	private Long stkgkL3; // 取得価額(第3帳簿)
	private Long askstkgkL3; // 圧縮後取得価額(第3帳簿)
	private String skkhoL3; // 償却方法(第3帳簿)
	private Integer tainenL3; // 耐用年数(第3帳簿)
	private Double skkritsuL3; // 償却率(第3帳簿)
	private Integer skkmmsuL3; // 償却月数(第3帳簿)
	private Integer zanzonmmsuL3; // 残存月数(第3帳簿)
	private Long ksbokaL3; // 期首帳簿価額(第3帳簿)
	private Long skkcalckisogkL3; // 償却計算基礎額(第3帳簿)
	private Double zanzonritsuL3; // 残存率(第3帳簿)
	private Long zanzongkL3; // 残存価額(第3帳簿)
	private Long ksftskkruigkL3; // 期首普通償却累計額(第3帳簿)
	private Long kszkskkruigkL3; // 期首増加償却累計額(第3帳簿)
	private Long kstkskkruigkL3; // 期首特別償却累計額(第3帳簿)
	private Long zenToftskkgkL3; // 当期普通償却額(前月累計)(第3帳簿)
	private Long zenTozkskkgkL3; // 当期増加償却額(前月累計)(第3帳簿)
	private Long zenTotkskkgkL3; // 当期特別償却額(前月累計)(第3帳簿)
	private Long zenToniniskkgkL3; // 当期任意償却額(前月)(第3帳簿)
	private Long toToftskkgkL3; // 当期普通償却額(当月累計)(第3帳簿)
	private Long toTozkskkgkL3; // 当期増加償却額(当月累計)(第3帳簿)
	private Long toTotkskkgkL3; // 当期特別償却額(当月累計)(第3帳簿)
	private Long toToniniskkgkL3; // 当期任意償却額(当月)(第3帳簿)
	private Long toBokaL3; // 当月末帳簿価額(第3帳簿)
	private Long zogenbokaL3; // 増減帳簿価額(第3帳簿)
	private Double zkskkritsuL3; // 増加償却率(第3帳簿)
	private String yukyustymL3; // 遊休開始年月(第3帳簿)
	private String yukyufkymL3; // 遊休復帰年月(第3帳簿)
	private String shonencalckbnL3; // 初年度計算区分(第3帳簿)
	private String skkkanryoflgL3; // 償却完了(第3帳簿)
	private String skkkirikaeyyL3; // 償却切替年度(第3帳簿)
	private String zanzonskkstyyL3; // 残存償却開始年度(第3帳簿)
	private Integer zanzonskkmmsuL3; // 残存償却月数(第3帳簿)
	private String famitsumorihkymdL3; // 固定資産見積変更年月日(第3帳簿)
	private Long mitsumorihkgobokaL3; // 見積変更後簿価(第3帳簿)
	private Long mitsumorihkmaetainenL3; // 見積変更前耐用年数(第3帳簿)
	private Long mitsumorihkmaeskkmmsuL3; // 見積変更前償却月数(第3帳簿)
	private Long mitsumorihkmaeskkritsuL3; // 見積変更前償却率(第3帳簿)
	private Long stkgkL4; // 取得価額(第4帳簿)
	private Long askstkgkL4; // 圧縮後取得価額(第4帳簿)
	private String skkhoL4; // 償却方法(第4帳簿)
	private Integer tainenL4; // 耐用年数(第4帳簿)
	private Double skkritsuL4; // 償却率(第4帳簿)
	private Integer skkmmsuL4; // 償却月数(第4帳簿)
	private Integer zanzonmmsuL4; // 残存月数(第4帳簿)
	private Long ksbokaL4; // 期首帳簿価額(第4帳簿)
	private Long skkcalckisogkL4; // 償却計算基礎額(第4帳簿)
	private Double zanzonritsuL4; // 残存率(第4帳簿)
	private Long zanzongkL4; // 残存価額(第4帳簿)
	private Long ksftskkruigkL4; // 期首普通償却累計額(第4帳簿)
	private Long kszkskkruigkL4; // 期首増加償却累計額(第4帳簿)
	private Long kstkskkruigkL4; // 期首特別償却累計額(第4帳簿)
	private Long zenToftskkgkL4; // 当期普通償却額(前月累計)(第4帳簿)
	private Long zenTozkskkgkL4; // 当期増加償却額(前月累計)(第4帳簿)
	private Long zenTotkskkgkL4; // 当期特別償却額(前月累計)(第4帳簿)
	private Long zenToniniskkgkL4; // 当期任意償却額(前月)(第4帳簿)
	private Long toToftskkgkL4; // 当期普通償却額(当月累計)(第4帳簿)
	private Long toTozkskkgkL4; // 当期増加償却額(当月累計)(第4帳簿)
	private Long toTotkskkgkL4; // 当期特別償却額(当月累計)(第4帳簿)
	private Long toToniniskkgkL4; // 当期任意償却額(当月)(第4帳簿)
	private Long toBokaL4; // 当月末帳簿価額(第4帳簿)
	private Long zogenbokaL4; // 増減帳簿価額(第4帳簿)
	private Double zkskkritsuL4; // 増加償却率(第4帳簿)
	private String yukyustymL4; // 遊休開始年月(第4帳簿)
	private String yukyufkymL4; // 遊休復帰年月(第4帳簿)
	private String shonencalckbnL4; // 初年度計算区分(第4帳簿)
	private String skkkanryoflgL4; // 償却完了(第4帳簿)
	private String skkkirikaeyyL4; // 償却切替年度(第4帳簿)
	private String zanzonskkstyyL4; // 残存償却開始年度(第4帳簿)
	private Integer zanzonskkmmsuL4; // 残存償却月数(第4帳簿)
	private String famitsumorihkymdL4; // 固定資産見積変更年月日(第4帳簿)
	private Long mitsumorihkgobokaL4; // 見積変更後簿価(第4帳簿)
	private Long mitsumorihkmaetainenL4; // 見積変更前耐用年数(第4帳簿)
	private Long mitsumorihkmaeskkmmsuL4; // 見積変更前償却月数(第4帳簿)
	private Long mitsumorihkmaeskkritsuL4; // 見積変更前償却率(第4帳簿)
	private Long stkgkL5; // 取得価額(第5帳簿)
	private Long askstkgkL5; // 圧縮後取得価額(第5帳簿)
	private String skkhoL5; // 償却方法(第5帳簿)
	private Integer tainenL5; // 耐用年数(第5帳簿)
	private Double skkritsuL5; // 償却率(第5帳簿)
	private Integer skkmmsuL5; // 償却月数(第5帳簿)
	private Integer zanzonmmsuL5; // 残存月数(第5帳簿)
	private Long ksbokaL5; // 期首帳簿価額(第5帳簿)
	private Long skkcalckisogkL5; // 償却計算基礎額(第5帳簿)
	private Double zanzonritsuL5; // 残存率(第5帳簿)
	private Long zanzongkL5; // 残存価額(第5帳簿)
	private Long ksftskkruigkL5; // 期首普通償却累計額(第5帳簿)
	private Long kszkskkruigkL5; // 期首増加償却累計額(第5帳簿)
	private Long kstkskkruigkL5; // 期首特別償却累計額(第5帳簿)
	private Long zenToftskkgkL5; // 当期普通償却額(前月累計)(第5帳簿)
	private Long zenTozkskkgkL5; // 当期増加償却額(前月累計)(第5帳簿)
	private Long zenTotkskkgkL5; // 当期特別償却額(前月累計)(第5帳簿)
	private Long zenToniniskkgkL5; // 当期任意償却額(前月)(第5帳簿)
	private Long toToftskkgkL5; // 当期普通償却額(当月累計)(第5帳簿)
	private Long toTozkskkgkL5; // 当期増加償却額(当月累計)(第5帳簿)
	private Long toTotkskkgkL5; // 当期特別償却額(当月累計)(第5帳簿)
	private Long toToniniskkgkL5; // 当期任意償却額(当月)(第5帳簿)
	private Long toBokaL5; // 当月末帳簿価額(第5帳簿)
	private Long zogenbokaL5; // 増減帳簿価額(第5帳簿)
	private Double zkskkritsuL5; // 増加償却率(第5帳簿)
	private String yukyustymL5; // 遊休開始年月(第5帳簿)
	private String yukyufkymL5; // 遊休復帰年月(第5帳簿)
	private String shonencalckbnL5; // 初年度計算区分(第5帳簿)
	private String skkkanryoflgL5; // 償却完了(第5帳簿)
	private String skkkirikaeyyL5; // 償却切替年度(第5帳簿)
	private String zanzonskkstyyL5; // 残存償却開始年度(第5帳簿)
	private Integer zanzonskkmmsuL5; // 残存償却月数(第5帳簿)
	private String famitsumorihkymdL5; // 固定資産見積変更年月日(第5帳簿)
	private Long mitsumorihkgobokaL5; // 見積変更後簿価(第5帳簿)
	private Long mitsumorihkmaetainenL5; // 見積変更前耐用年数(第5帳簿)
	private Long mitsumorihkmaeskkmmsuL5; // 見積変更前償却月数(第5帳簿)
	private Long mitsumorihkmaeskkritsuL5; // 見積変更前償却率(第5帳簿)
	private Long stkgkL6; // 取得価額(第6帳簿)
	private Long askstkgkL6; // 圧縮後取得価額(第6帳簿)
	private String skkhoL6; // 償却方法(第6帳簿)
	private Integer tainenL6; // 耐用年数(第6帳簿)
	private Double skkritsuL6; // 償却率(第6帳簿)
	private Integer skkmmsuL6; // 償却月数(第6帳簿)
	private Integer zanzonmmsuL6; // 残存月数(第6帳簿)
	private Long ksbokaL6; // 期首帳簿価額(第6帳簿)
	private Long skkcalckisogkL6; // 償却計算基礎額(第6帳簿)
	private Double zanzonritsuL6; // 残存率(第6帳簿)
	private Long zanzongkL6; // 残存価額(第6帳簿)
	private Long ksftskkruigkL6; // 期首普通償却累計額(第6帳簿)
	private Long kszkskkruigkL6; // 期首増加償却累計額(第6帳簿)
	private Long kstkskkruigkL6; // 期首特別償却累計額(第6帳簿)
	private Long zenToftskkgkL6; // 当期普通償却額(前月累計)(第6帳簿)
	private Long zenTozkskkgkL6; // 当期増加償却額(前月累計)(第6帳簿)
	private Long zenTotkskkgkL6; // 当期特別償却額(前月累計)(第6帳簿)
	private Long zenToniniskkgkL6; // 当期任意償却額(前月)(第6帳簿)
	private Long toToftskkgkL6; // 当期普通償却額(当月累計)(第6帳簿)
	private Long toTozkskkgkL6; // 当期増加償却額(当月累計)(第6帳簿)
	private Long toTotkskkgkL6; // 当期特別償却額(当月累計)(第6帳簿)
	private Long toToniniskkgkL6; // 当期任意償却額(当月)(第6帳簿)
	private Long toBokaL6; // 当月末帳簿価額(第6帳簿)
	private Long zogenbokaL6; // 増減帳簿価額(第6帳簿)
	private Double zkskkritsuL6; // 増加償却率(第6帳簿)
	private String yukyustymL6; // 遊休開始年月(第6帳簿)
	private String yukyufkymL6; // 遊休復帰年月(第6帳簿)
	private String shonencalckbnL6; // 初年度計算区分(第6帳簿)
	private String skkkanryoflgL6; // 償却完了(第6帳簿)
	private String skkkirikaeyyL6; // 償却切替年度(第6帳簿)
	private String zanzonskkstyyL6; // 残存償却開始年度(第6帳簿)
	private Integer zanzonskkmmsuL6; // 残存償却月数(第6帳簿)
	private String famitsumorihkymdL6; // 固定資産見積変更年月日(第6帳簿)
	private Long mitsumorihkgobokaL6; // 見積変更後簿価(第6帳簿)
	private Long mitsumorihkmaetainenL6; // 見積変更前耐用年数(第6帳簿)
	private Long mitsumorihkmaeskkmmsuL6; // 見積変更前償却月数(第6帳簿)
	private Long mitsumorihkmaeskkritsuL6; // 見積変更前償却率(第6帳簿)
	private String konyucd; // 購入先コード
	private String konyunm; // 購入先名称
	private String kashicd; // 貸出先コード
	private String kashinm; // 貸出先名称
	private String cpOya; // 建仮資産番号・親
	private String cpEda; // 建仮資産番号・枝
	private String cpShisanNum; // 建仮資産番号
	private String knrbunruicd; // 管理分類コード
	private String biko1; // 備考1
	private String biko2; // 備考2
	private String stkringino; // 取得時稟議決裁番号
	private String stktekiyo; // 取得時摘要
	private String askcd; // 圧縮コード
	private String asknm; // 圧縮名称
	private String askkbn; // 圧縮区分
	private Long askgk; // 圧縮額
	private Long ksaskzan; // 期首圧縮残高
	private Long ksaskninyogk; // 期首圧縮認容額
	private Long askninyogk; // 圧縮認容額
	private Long askkisogk; // 圧縮基礎額
	private Long askzanzongk; // 圧縮残存価額
	private Long kaiteiaskgk; // 改定後圧縮額
	private Long skkchokagk; // 償却超過額
	private Long skkfusokugk; // 償却不足額
	private String tkcd; // 特償コード
	private String tknm; // 特償名称
	private String tkkbn; // 特別償却区分
	private Double tkritsuBunshi; // 特償/割増率(分子)
	private Double tkritsuBunbo; // 特償/割増率(分母)
	private String beppyotaishokbn; // 別表対象区分
	private String skkshisankbn; // 償却資産区分
	private String aitekanjocd; // 相手勘定科目コード
	private String aitekanjonm; // 相手勘定科目名称
	private String aitehojokamokucd; // 相手補助科目コード
	private String aitehojokamokunm; // 相手補助科目名称
	private String gappeiukekbn; // 合併受入区分
	private String genshistkymd; // 原始取得年月日
	private String groupcd; // グループコード
	private String groupnm; // グループ名称
	private String shinariocd; // シナリオコード
	private String shinarionm; // シナリオ名称
	private String shuyoshisankbn; // 主要資産区分
	private String niniLd_1cd; // 任意(台帳)1コード
	private String niniLd_1nm; // 任意(台帳)1名称
	private String niniLd_2cd; // 任意(台帳)2コード
	private String niniLd_2nm; // 任意(台帳)2名称
	private String niniLd_3cd; // 任意(台帳)3コード
	private String niniLd_3nm; // 任意(台帳)3名称
	private String niniLd_4cd; // 任意(台帳)4コード
	private String niniLd_4nm; // 任意(台帳)4名称
	private String niniLd_5cd; // 任意(台帳)5コード
	private String niniLd_5nm; // 任意(台帳)5名称
	private String niniLd_6cd; // 任意(台帳)6コード
	private String niniLd_6nm; // 任意(台帳)6名称
	private String niniLd_7cd; // 任意(台帳)7コード
	private String niniLd_7nm; // 任意(台帳)7名称
	private String niniLd_8cd; // 任意(台帳)8コード
	private String niniLd_8nm; // 任意(台帳)8名称
	private String niniLd_9cd; // 任意(台帳)9コード
	private String niniLd_9nm; // 任意(台帳)9名称
	private String niniLd_10cd; // 任意(台帳)10コード
	private String niniLd_10nm; // 任意(台帳)10名称
	private String niniLd_11cd; // 任意(台帳)11コード
	private String niniLd_11nm; // 任意(台帳)11名称
	private String niniLd_12cd; // 任意(台帳)12コード
	private String niniLd_12nm; // 任意(台帳)12名称
	private String niniLd_13cd; // 任意(台帳)13コード
	private String niniLd_13nm; // 任意(台帳)13名称
	private String niniLd_14cd; // 任意(台帳)14コード
	private String niniLd_14nm; // 任意(台帳)14名称
	private String niniLd_15cd; // 任意(台帳)15コード
	private String niniLd_15nm; // 任意(台帳)15名称
	private String niniLd_16cd; // 任意(台帳)16コード
	private String niniLd_16nm; // 任意(台帳)16名称
	private String niniLd_17cd; // 任意(台帳)17コード
	private String niniLd_17nm; // 任意(台帳)17名称
	private String niniLd_18cd; // 任意(台帳)18コード
	private String niniLd_18nm; // 任意(台帳)18名称
	private String niniLd_19cd; // 任意(台帳)19コード
	private String niniLd_19nm; // 任意(台帳)19名称
	private String niniLd_20cd; // 任意(台帳)20コード
	private String niniLd_20nm; // 任意(台帳)20名称
	private Long toshinkkyy; // 当年申告年
	private String toshinkkchicd; // 当年申告地コード
	private String toshinkkchinm; // 当年申告地名称
	private String toshinkkshurui; // 当年申告種類
	private Long toshinkkstkgk; // 当年申告取得価額
	private Integer toshinkktainen; // 当年申告耐用年数
	private Double toshinkkZkskkritsu; // 当年申告増加償却率
	private String tothzeicd; // 当年特例非課税コード
	private String tothzeinm; // 当年特例非課税名称
	private Double totkreiritsuBunshi; // 当年特例率(分子)
	private Double totkreiritsuBunbo; // 当年特例率(分母)
	private String zenshinkkyy; // 前年申告年
	private String zenshinkkchicd; // 前年申告地コード
	private String zenshinkkchinm; // 前年申告地名称
	private String zenshinkkshurui; // 前年申告種類
	private Long zenshinkkstkgk; // 前年申告取得価額
	private Double zenshinkktainen; // 前年申告耐用年数
	private Double zenshinkkZkskkritsu; // 前年申告増加償却率
	private String zenthzeicd; // 前年特例非課税コード
	private String zenthzeinm; // 前年特例非課税名称
	private Double zentkreiritsuBunshi; // 前年特例率(分子)
	private Double zentkreiritsuBunbo; // 前年特例率(分母)
	private Long zenrironboka; // 前年1月1日帳簿価額
	private Long zenhyokagk; // 前年評価額
	private Long ykshinkkyy; // 翌年申告年
	private String ykshinkkchicd; // 翌年申告地コード
	private String ykshinkkchinm; // 翌年申告地名称
	private String ykshinkkshurui; // 翌年申告種類
	private Long ykshinkkstkgk; // 翌年申告取得価額
	private Double ykshinkktainen; // 翌年申告耐用年数
	private Double ykshinkkZkskkritsu; // 翌年申告増加償却率
	private String ykthzeicd; // 翌年特例非課税コード
	private String ykthzeinm; // 翌年特例非課税名称
	private Double yktkreiritsuBunshi; // 翌年特例率(分子)
	private Double yktkreiritsuBunbo; // 翌年特例率(分母)
	private String lastshinkkyy; // 最終申告年
	private String togensonkbnk; // 当期減損区分(会社帳簿)
	private Long ksgensonruigkk; // 期首減損累計額(会社帳簿)
	private Long togensongkk; // 当期減損額(会社帳簿)
	private Long skkcalczanzongkk; // 償却計算残存価額(会社帳簿)
	private Long gensonbokak; // 減損後帳簿価額(会社帳簿)
	private Integer gensonmaetainenk; // 減損前耐用年数(会社帳簿)
	private Integer gensonmaeskkmmsuk; // 減損前償却月数(会社帳簿)
	private String gensonymdk; // 減損評価年月日(会社帳簿)
	private Long genshistkgkk; // 原始取得価額(会社帳簿)
	private Long kaiteistkgkk; // 改定後取得価額(会社帳簿)
	private Integer kaiteitainenk; // 改定前耐用年数(会社帳簿)
	private String kaiteiymdk; // 改定年月日(会社帳簿)
	private String yusenzogenkbnk; // 優先増減区分(会社帳簿)
	private String togensonkbnz; // 当期減損区分(税法帳簿)
	private Long ksgensonruigkz; // 期首減損累計額(税法帳簿)
	private Long togensongkz; // 当期減損額(税法帳簿)
	private Long skkcalczanzongkz; // 償却計算残存価額(税法帳簿)
	private Long gensonbokaz; // 減損後帳簿価額(税法帳簿)
	private Integer gensonmaetainenz; // 減損前耐用年数(税法帳簿)
	private Integer gensonmaeskkmmsuz; // 減損前償却月数(税法帳簿)
	private String gensonymdz; // 減損評価年月日(税法帳簿)
	private Long genshistkgkz; // 原始取得価額(税法帳簿)
	private Long kaiteistkgkz; // 改定後取得価額(税法帳簿)
	private Integer kaiteitainenz; // 改定前耐用年数(税法帳簿)
	private String kaiteiymdz; // 改定年月日(税法帳簿)
	private String yusenzogenkbnz; // 優先増減区分(税法帳簿)
	private String togensonkbnL3; // 当期減損区分(第3帳簿)
	private Long ksgensonruigkL3; // 期首減損累計額(第3帳簿)
	private Long togensongkL3; // 当期減損額(第3帳簿)
	private Long skkcalczanzongkL3; // 償却計算残存価額(第3帳簿)
	private Long gensonbokaL3; // 減損後帳簿価額(第3帳簿)
	private Integer gensonmaetainenL3; // 減損前耐用年数(第3帳簿)
	private Integer gensonmaeskkmmsuL3; // 減損前償却月数(第3帳簿)
	private String gensonymdL3; // 減損評価年月日(第3帳簿)
	private Long genshistkgkL3; // 原始取得価額(第3帳簿)
	private Long kaiteistkgkL3; // 改定後取得価額(第3帳簿)
	private Integer kaiteitainenL3; // 改定前耐用年数(第3帳簿)
	private String kaiteiymdL3; // 改定年月日(第3帳簿)
	private String yusenzogenkbnL3; // 優先増減区分(第3帳簿)
	private String togensonkbnL4; // 当期減損区分(第4帳簿)
	private Long ksgensonruigkL4; // 期首減損累計額(第4帳簿)
	private Long togensongkL4; // 当期減損額(第4帳簿)
	private Long skkcalczanzongkL4; // 償却計算残存価額(第4帳簿)
	private Long gensonbokaL4; // 減損後帳簿価額(第4帳簿)
	private Integer gensonmaetainenL4; // 減損前耐用年数(第4帳簿)
	private Integer gensonmaeskkmmsuL4; // 減損前償却月数(第4帳簿)
	private String gensonymdL4; // 減損評価年月日(第4帳簿)
	private Long genshistkgkL4; // 原始取得価額(第4帳簿)
	private Long kaiteistkgkL4; // 改定後取得価額(第4帳簿)
	private Integer kaiteitainenL4; // 改定前耐用年数(第4帳簿)
	private String kaiteiymdL4; // 改定年月日(第4帳簿)
	private String yusenzogenkbnL4; // 優先増減区分(第4帳簿)
	private String togensonkbnL5; // 当期減損区分(第5帳簿)
	private Long ksgensonruigkL5; // 期首減損累計額(第5帳簿)
	private Long togensongkL5; // 当期減損額(第5帳簿)
	private Long skkcalczanzongkL5; // 償却計算残存価額(第5帳簿)
	private Long gensonbokaL5; // 減損後帳簿価額(第5帳簿)
	private Integer gensonmaetainenL5; // 減損前耐用年数(第5帳簿)
	private Integer gensonmaeskkmmsuL5; // 減損前償却月数(第5帳簿)
	private String gensonymdL5; // 減損評価年月日(第5帳簿)
	private Long genshistkgkL5; // 原始取得価額(第5帳簿)
	private Long kaiteistkgkL5; // 改定後取得価額(第5帳簿)
	private Integer kaiteitainenL5; // 改定前耐用年数(第5帳簿)
	private String kaiteiymdL5; // 改定年月日(第5帳簿)
	private String yusenzogenkbnL5; // 優先増減区分(第5帳簿)
	private String togensonkbnL6; // 当期減損区分(第6帳簿)
	private Long ksgensonruigkL6; // 期首減損累計額(第6帳簿)
	private Long togensongkL6; // 当期減損額(第6帳簿)
	private Long skkcalczanzongkL6; // 償却計算残存価額(第6帳簿)
	private Long gensonbokaL6; // 減損後帳簿価額(第6帳簿)
	private Integer gensonmaetainenL6; // 減損前耐用年数(第6帳簿)
	private Integer gensonmaeskkmmsuL6; // 減損前償却月数(第6帳簿)
	private String gensonymdL6; // 減損評価年月日(第6帳簿)
	private Long genshistkgkL6; // 原始取得価額(第6帳簿)
	private Long kaiteistkgkL6; // 改定後取得価額(第6帳簿)
	private Integer kaiteitainenL6; // 改定前耐用年数(第6帳簿)
	private String kaiteiymdL6; // 改定年月日(第6帳簿)
	private String yusenzogenkbnL6; // 優先増減区分(第6帳簿)
	private Long updkaisu; // 更新回数
	private String updkaishacd; // 更新会社コード
	private String updid; // 更新者ID
	private String updymd; // 更新年月日
	private String updtime; // 更新時間
	private String routeType; // 経路ﾌﾗｸﾞ(任意項目eAsset追加定義)
	private String routeTypeName; // 経路ﾌﾗｸﾞ名(任意項目eAsset追加定義)
	private String purCompanyCode; // 仕入先(任意項目eAsset追加定義)
	private String purCompanyName; // 仕入先名(任意項目eAsset追加定義)
	private String itemGroupCode; // 案件ｸﾞﾙｰﾌﾟ(任意項目eAsset追加定義)
	private String itemGroupName; // 案件ｸﾞﾙｰﾌﾟ名(任意項目eAsset追加定義)
	private String manageSectionCode; // 管理部課(任意項目eAsset追加定義)
	private String manageSectionName; // 管理部課名(任意項目eAsset追加定義)
	private String astPrjCode; // 資産PJ(任意項目eAsset追加定義)
	private String astPrjName; // 資産PJ名(任意項目eAsset追加定義)
	private String depPrjCode; // 償却PJ(任意項目eAsset追加定義)
	private String depPrjName; // 償却PJ名(任意項目eAsset追加定義)
	private String costType; // 原販ﾌﾗｸﾞ(任意項目eAsset追加定義)
	private String costTypeName; // 原販ﾌﾗｸﾞ名(任意項目eAsset追加定義)
	private String applicationId; // 申請番号(任意項目eAsset追加定義)
	private String slipNum; // 伝票番号(任意項目eAsset追加定義)
	private String interestFlag; // 金利対象外(任意項目eAsset追加定義)
	private String interestFlagName; // 金利対象外名(任意項目eAsset追加定義)
	private String apStaff; // 申請者(任意項目eAsset追加定義)
	private String inventoryFlag; // 棚卸対象外(任意項目eAsset追加定義)
	private String inventoryFlagName; // 棚卸対象外名(任意項目eAsset追加定義)
	private String disposePlanCode; // 除売却見込(任意項目eAsset追加定義)
	private String disposePlanName; // 除売却見込名(任意項目eAsset追加定義)
	private String taxAdjustCode; // 税務調整(任意項目eAsset追加定義)
	private String taxAdjustName; // 税務調整名(任意項目eAsset追加定義)
	private String contractBranchNum; // 契約枝番(任意項目eAsset追加定義)
	private String chargeType; // 担当者区分(任意項目eAsset追加定義)
	private String chargeTypeName; // 担当者区分名(任意項目eAsset追加定義)
	private String disposeReasonCode; // 処分理由(任意項目eAsset追加定義)
	private String disposeReasonName; // 処分理由名(任意項目eAsset追加定義)
	private String disposeSectionCode; // 除売却部課(任意項目eAsset追加定義)
	private String disposeSectionName; // 除売却部課名(任意項目eAsset追加定義)
	private String calcType; // 集計区分(任意項目eAsset追加定義)
	private String calcTypeName; // 集計区分名(任意項目eAsset追加定義)
	private String upperSectionCode; // 上流部課(任意項目eAsset追加定義)
	private String upperSectionName; // 上流部課名(任意項目eAsset追加定義)
	private String addUpPlanDate; // 計上予定日(任意項目eAsset追加定義)
	private String addUpPlanDateName; // 計上予定日名(任意項目eAsset追加定義)
	private String paTemplateCode; // PAﾃﾝﾌﾟﾚｰﾄ(任意項目eAsset追加定義)
	private String paTemplateName; // PAﾃﾝﾌﾟﾚｰﾄ名(任意項目eAsset追加定義)
	private String upperAccountCode; // 上流勘定(任意項目eAsset追加定義)
	private String upperAccountName; // 上流勘定名(任意項目eAsset追加定義)
	private String jgzzReconRef; // 消込参照(任意項目eAsset追加定義)
	private String expenseType; // 経費ﾌﾗｸﾞ(任意項目eAsset追加定義)
	private String expenseName; // 経費ﾌﾗｸﾞ名(任意項目eAsset追加定義)

	private Long ksskkruigkk; // 機種償却累計額(会社帳簿)
	private Long ksskkruigkz; // 機種償却累計額(税法帳簿)
	private Long ksskkruigkL3; // 機種償却累計額(第3帳簿)
	private Long ksskkruigkL4; // 機種償却累計額(第4帳簿)
	private Long ksskkruigkL5; // 機種償却累計額(第5帳簿)
	private Long ksskkruigkL6; // 機種償却累計額(第6帳簿)
	private Long toToskkgkk; // 当期償却額(会社帳簿)
	private Long toToskkgkz; // 当期償却額(税法帳簿)
	private Long toToskkgkL3; // 当期償却額(第3帳簿)
	private Long toToskkgkL4; // 当期償却額(第4帳簿)
	private Long toToskkgkL5; // 当期償却額(第5帳簿)
	private Long toToskkgkL6; // 当期償却額(第6帳簿)
	private Long toSkkgkk; // 当月償却額(会社帳簿)
	private Long toSkkgkz; // 当月償却額(税法帳簿)
	private Long toSkkgkL3; // 当月償却額(第3帳簿)
	private Long toSkkgkL4; // 当月償却額(第4帳簿)
	private Long toSkkgkL5; // 当月償却額(第5帳簿)
	private Long toSkkgkL6; // 当月償却額(第6帳簿)

	private String shisanknrkbnName; // 資産管理区分
	private String shisanjotaikbnName; // 資産状態区分
	private String togensonkbnkName; // 減損区分

	//	除却債務
	private Long koyunoR; // 固有番号
	private String kaishacdR; // 会社コード
	private String kaishanmR; // 会社名称
	private String oyaR; // 資産番号・親
	private String edaR; // 資産番号・枝
	private String shisanknrkbnR; // 資産管理区分
	private String shisanjotaikbnR; // 資産状態区分(固定資産)
	private String shisannmcdR; // 資産名称コード
	private String shisannmR; // 資産名称
	private String shisannmkR; // 資産名称カナ
	private String soshiki1cdR; // 組織1コード
	private String soshiki1nmR; // 組織1名称
	private String soshiki2cdR; // 組織2コード
	private String soshiki2nmR; // 組織2名称
	private String soshiki3cdR; // 組織3コード
	private String soshiki3nmR; // 組織3名称
	private String soshiki4cdR; // 組織4コード
	private String soshiki4nmR; // 組織4名称
	private String setchicdR; // 設置場所コード
	private String setchinmR; // 設置場所名称
	private String skkhihfcdR; // 配賦コード
	private String hfnmR; // 配賦名称
	private String saimuhfcdR; // 債務配賦コード
	private String saimuhfnmR; // 債務配賦名称
	private String shuruicdR; // 種類コード
	private String shuruinmR; // 種類名称
	private String kozocdR; // 構造用途コード
	private String kozonmR; // 構造用途名称
	private String bunruicdR; // 分類コード
	private String bunruinmR; // 分類名称
	private String saimukeijoymdR; // 債務計上年月日
	private String saimurikoymdR; // 債務履行年月日
	private String kadoymdR; // 稼働年月日
	private String jbkymdR; // 除売却年月日
	private String idoymdR; // 移動年月日
	private Integer suryoR; // 数量
	private String suryoTaniR; // 数量単位
	private Double mensekiR; // 面積
	private String mensekiTaniR; // 面積単位
	private String tosaimukeijokbnR; // 当期債務計上区分
	private String jksaimukeijokbnR; // 除去債務計上区分
	private Long mitsumorigkWaribikimaekR; // 見積価額(割引前)(会社帳簿)
	private Double waribikiritsukR; // 割引率(会社帳簿)
	private Long keijogkWaribikigokR; // 計上価額(割引後)(会社帳簿)
	private Long kssaimubokakR; // 期首債務帳簿価額(会社帳簿)
	private Long risokucalckisogkkR; // 利息計算基礎額(会社帳簿)
	private Long toSaimubokakR; // 当月末債務帳簿価額(会社帳簿)
	private Long toSaimuzogenbokakR; // 債務増減帳簿価額(会社帳簿)
	private Long toTorisokugkkR; // 当期利息額(当月累計)(会社帳簿)
	private Long ksrisokuruigkkR; // 期首利息累計額(会社帳簿)
	private String mitsumorihkymdkR; // 除去債務見積変更日(会社帳簿)
	private String famitsumorihkymdkR; // 固定資産見積変更日(会社帳簿)
	private Long mitsumorihkgobokakR; // 見積変更後帳簿価額(会社帳簿)
	private Integer mitsumorihkmaetainenkR; // 見積変更前耐用年数(会社帳簿)
	private Integer mitsumorihkmaeskkmmsukR; // 見積変更前償却月数(会社帳簿)
	private Double mitsumorihkmaeskkritsukR; // 見積変更前償却率(会社帳簿)
	private Long tomitsumorihkchoseigkkR; // 当期見積変更調整額(会社帳簿)
	private Long stkgkkR; // 取得価額(会社帳簿)
	private String skkhokR; // 償却方法(会社帳簿)
	private Integer tainenkR; // 耐用年数(会社帳簿)
	private Double skkritsukR; // 償却率(会社帳簿)
	private Integer skkmmsukR; // 償却月数(会社帳簿)
	private Integer zanzonmmsukR; // 残存月数(会社帳簿)
	private Long ksbokakR; // 期首帳簿価額(会社帳簿)
	private Long skkcalckisogkkR; // 償却計算基礎額(会社帳簿)
	private Double zanzonritsukR; // 残存率(会社帳簿)
	private Long zanzongkkR; // 残存価額(会社帳簿)
	private Long ksftskkruigkkR; // 期首普通償却累計額(会社帳簿)
	private Long kszkskkruigkkR; // 期首増加償却累計額(会社帳簿)
	private Long toToftskkgkkR; // 当期普通償却額(当月累計)(会社帳簿)
	private Long toTozkskkgkkR; // 当期増加償却額(当月累計)(会社帳簿)
	private Long toToniniskkgkkR; // 当期任意償却額(当月)(会社帳簿)
	private Long toBokakR; // 当月末帳簿価額(会社帳簿)
	private Long zogenbokakR; // 増減帳簿価額(会社帳簿)
	private Double zkskkritsukR; // 増加償却率(会社帳簿)
	private String yukyustymkR; // 遊休開始年月(会社帳簿)
	private String yukyufkymkR; // 遊休復帰年月(会社帳簿)
	private String shonencalckbnkR; // 初年度計算区分(会社帳簿)
	private String skkkanryoflgkR; // 償却完了フラグ(会社帳簿)
	private String skkkirikaeyykR; // 償却切替年度(会社帳簿)
	private String zanzonskkstyykR; // 残存償却開始年度(会社帳簿)
	private Long mitsumorigkWaribikimaezR; // 見積価額(割引前)(税法帳簿)
	private Double waribikiritsuzR; // 割引率(税法帳簿)
	private Long keijogkWaribikigozR; // 計上価額(割引後)(税法帳簿)
	private Long kssaimubokazR; // 期首債務帳簿価額(税法帳簿)
	private Long risokucalckisogkzR; // 利息計算基礎額(税法帳簿)
	private Long toSaimubokazR; // 当月末債務帳簿価額(税法帳簿)
	private Long toSaimuzogenbokazR; // 債務増減帳簿価額(税法帳簿)
	private Long toTorisokugkzR; // 当期利息額(当月累計)(税法帳簿)
	private Long ksrisokuruigkzR; // 期首利息累計額(税法帳簿)
	private String mitsumorihkymdzR; // 除去債務見積変更日(税法帳簿)
	private String famitsumorihkymdzR; // 固定資産見積変更日(税法帳簿)
	private Long mitsumorihkgobokazR; // 見積変更後帳簿価額(税法帳簿)
	private Integer mitsumorihkmaetainenzR; // 見積変更前耐用年数(税法帳簿)
	private Integer mitsumorihkmaeskkmmsuzR; // 見積変更前償却月数(税法帳簿)
	private Double mitsumorihkmaeskkritsuzR; // 見積変更前償却率(税法帳簿)
	private Long tomitsumorihkchoseigkzR; // 当期見積変更調整額(税法帳簿)
	private Long stkgkzR; // 取得価額(税法帳簿)
	private String skkhozR; // 償却方法(税法帳簿)
	private Integer tainenzR; // 耐用年数(税法帳簿)
	private Double skkritsuzR; // 償却率(税法帳簿)
	private Integer skkmmsuzR; // 償却月数(税法帳簿)
	private Integer zanzonmmsuzR; // 残存月数(税法帳簿)
	private Long ksbokazR; // 期首帳簿価額(税法帳簿)
	private Long skkcalckisogkzR; // 償却計算基礎額(税法帳簿)
	private Double zanzonritsuzR; // 残存率(税法帳簿)
	private Long zanzongkzR; // 残存価額(税法帳簿)
	private Long ksftskkruigkzR; // 期首普通償却累計額(税法帳簿)
	private Long kszkskkruigkzR; // 期首増加償却累計額(税法帳簿)
	private Long toToftskkgkzR; // 当期普通償却額(当月累計)(税法帳簿)
	private Long toTozkskkgkzR; // 当期増加償却額(当月累計)(税法帳簿)
	private Long toToniniskkgkzR; // 当期任意償却額(当月)(税法帳簿)
	private Long toBokazR; // 当月末帳簿価額(税法帳簿)
	private Long zogenbokazR; // 増減帳簿価額(税法帳簿)
	private Double zkskkritsuzR; // 増加償却率(税法帳簿)
	private String yukyustymzR; // 遊休開始年月(税法帳簿)
	private String yukyufkymzR; // 遊休復帰年月(税法帳簿)
	private String shonencalckbnzR; // 初年度計算区分(税法帳簿)
	private String skkkanryoflgzR; // 償却完了(税法帳簿)
	private String skkkirikaeyyzR; // 償却切替年度(税法帳簿)
	private String zanzonskkstyyzR; // 残存償却開始年度(税法帳簿)
	private Long mitsumorigkWaribikimaeL3R; // 見積価額(割引前)(第3帳簿)
	private Double waribikiritsuL3R; // 割引率(第3帳簿)
	private Long keijogkWaribikigoL3R; // 計上価額(割引後)(第3帳簿)
	private Long kssaimubokaL3R; // 期首債務帳簿価額(第3帳簿)
	private Long risokucalckisogkL3R; // 利息計算基礎額(第3帳簿)
	private Long toSaimubokaL3R; // 当月末債務帳簿価額(第3帳簿)
	private Long toSaimuzogenbokaL3R; // 債務増減帳簿価額(第3帳簿)
	private Long toTorisokugkL3R; // 当期利息額(当月累計)(第3帳簿)
	private Long ksrisokuruigkL3R; // 期首利息累計額(第3帳簿)
	private String mitsumorihkymdL3R; // 除去債務見積変更日(第3帳簿)
	private String famitsumorihkymdL3R; // 固定資産見積変更日(第3帳簿)
	private Long mitsumorihkgobokaL3R; // 見積変更後帳簿価額(第3帳簿)
	private Integer mitsumorihkmaetainenL3R; // 見積変更前耐用年数(第3帳簿)
	private Integer mitsumorihkmaeskkmmsuL3R; // 見積変更前償却月数(第3帳簿)
	private Double mitsumorihkmaeskkritsuL3R; // 見積変更前償却率(第3帳簿)
	private Long tomitsumorihkchoseigkL3R; // 当期見積変更調整額(第3帳簿)
	private Long stkgkL3R; // 取得価額(第3帳簿)
	private String skkhoL3R; // 償却方法(第3帳簿)
	private Integer tainenL3R; // 耐用年数(第3帳簿)
	private Double skkritsuL3R; // 償却率(第3帳簿)
	private Integer skkmmsuL3R; // 償却月数(第3帳簿)
	private Integer zanzonmmsuL3R; // 残存月数(第3帳簿)
	private Long ksbokaL3R; // 期首帳簿価額(第3帳簿)
	private Long skkcalckisogkL3R; // 償却計算基礎額(第3帳簿)
	private Double zanzonritsuL3R; // 残存率(第3帳簿)
	private Long zanzongkL3R; // 残存価額(第3帳簿)
	private Long ksftskkruigkL3R; // 期首普通償却累計額(第3帳簿)
	private Long kszkskkruigkL3R; // 期首増加償却累計額(第3帳簿)
	private Long toToftskkgkL3R; // 当期普通償却額(当月累計)(第3帳簿)
	private Long toTozkskkgkL3R; // 当期増加償却額(当月累計)(第3帳簿)
	private Long toToniniskkgkL3R; // 当期任意償却額(当月)(第3帳簿)
	private Long toBokaL3R; // 当月末帳簿価額(第3帳簿)
	private Long zogenbokaL3R; // 増減帳簿価額(第3帳簿)
	private Double zkskkritsuL3R; // 増加償却率(第3帳簿)
	private String yukyustymL3R; // 遊休開始年月(第3帳簿)
	private String yukyufkymL3R; // 遊休復帰年月(第3帳簿)
	private String shonencalckbnL3R; // 初年度計算区分(第3帳簿)
	private String skkkanryoflgL3R; // 償却完了(第3帳簿)
	private String skkkirikaeyyL3R; // 償却切替年度(第3帳簿)
	private String zanzonskkstyyL3R; // 残存償却開始年度(第3帳簿)
	private Long mitsumorigkWaribikimaeL4R; // 見積価額(割引前)(第4帳簿)
	private Double waribikiritsuL4R; // 割引率(第4帳簿)
	private Long keijogkWaribikigoL4R; // 計上価額(割引後)(第4帳簿)
	private Long kssaimubokaL4R; // 期首債務帳簿価額(第4帳簿)
	private Long risokucalckisogkL4R; // 利息計算基礎額(第4帳簿)
	private Long toSaimubokaL4R; // 当月末債務帳簿価額(第4帳簿)
	private Long toSaimuzogenbokaL4R; // 債務増減帳簿価額(第4帳簿)
	private Long toTorisokugkL4R; // 当期利息額(当月累計)(第4帳簿)
	private Long ksrisokuruigkL4R; // 期首利息累計額(第4帳簿)
	private String mitsumorihkymdL4R; // 除去債務見積変更日(第4帳簿)
	private String famitsumorihkymdL4R; // 固定資産見積変更日(第4帳簿)
	private Long mitsumorihkgobokaL4R; // 見積変更後帳簿価額(第4帳簿)
	private Integer mitsumorihkmaetainenL4R; // 見積変更前耐用年数(第4帳簿)
	private Integer mitsumorihkmaeskkmmsuL4R; // 見積変更前償却月数(第4帳簿)
	private Double mitsumorihkmaeskkritsuL4R; // 見積変更前償却率(第4帳簿)
	private Long tomitsumorihkchoseigkL4R; // 当期見積変更調整額(第4帳簿)
	private Long stkgkL4R; // 取得価額(第4帳簿)
	private String skkhoL4R; // 償却方法(第4帳簿)
	private Integer tainenL4R; // 耐用年数(第4帳簿)
	private Double skkritsuL4R; // 償却率(第4帳簿)
	private Integer skkmmsuL4R; // 償却月数(第4帳簿)
	private Integer zanzonmmsuL4R; // 残存月数(第4帳簿)
	private Long ksbokaL4R; // 期首帳簿価額(第4帳簿)
	private Long skkcalckisogkL4R; // 償却計算基礎額(第4帳簿)
	private Double zanzonritsuL4R; // 残存率(第4帳簿)
	private Long zanzongkL4R; // 残存価額(第4帳簿)
	private Long ksftskkruigkL4R; // 期首普通償却累計額(第4帳簿)
	private Long kszkskkruigkL4R; // 期首増加償却累計額(第4帳簿)
	private Long toToftskkgkL4R; // 当期普通償却額(当月累計)(第4帳簿)
	private Long toTozkskkgkL4R; // 当期増加償却額(当月累計)(第4帳簿)
	private Long toToniniskkgkL4R; // 当期任意償却額(当月)(第4帳簿)
	private Long toBokaL4R; // 当月末帳簿価額(第4帳簿)
	private Long zogenbokaL4R; // 増減帳簿価額(第4帳簿)
	private Double zkskkritsuL4R; // 増加償却率(第4帳簿)
	private String yukyustymL4R; // 遊休開始年月(第4帳簿)
	private String yukyufkymL4R; // 遊休復帰年月(第4帳簿)
	private String shonencalckbnL4R; // 初年度計算区分(第4帳簿)
	private String skkkanryoflgL4R; // 償却完了(第4帳簿)
	private String skkkirikaeyyL4R; // 償却切替年度(第4帳簿)
	private String zanzonskkstyyL4R; // 残存償却開始年度(第4帳簿)
	private Long mitsumorigkWaribikimaeL5R; // 見積価額(割引前)(第5帳簿)
	private Double waribikiritsuL5R; // 割引率(第5帳簿)
	private Long keijogkWaribikigoL5R; // 計上価額(割引後)(第5帳簿)
	private Long kssaimubokaL5R; // 期首債務帳簿価額(第5帳簿)
	private Long risokucalckisogkL5R; // 利息計算基礎額(第5帳簿)
	private Long toSaimubokaL5R; // 当月末債務帳簿価額(第5帳簿)
	private Long toSaimuzogenbokaL5R; // 債務増減帳簿価額(第5帳簿)
	private Long toTorisokugkL5R; // 当期利息額(当月累計)(第5帳簿)
	private Long ksrisokuruigkL5R; // 期首利息累計額(第5帳簿)
	private String mitsumorihkymdL5R; // 除去債務見積変更日(第5帳簿)
	private String famitsumorihkymdL5R; // 固定資産見積変更日(第5帳簿)
	private Long mitsumorihkgobokaL5R; // 見積変更後帳簿価額(第5帳簿)
	private Integer mitsumorihkmaetainenL5R; // 見積変更前耐用年数(第5帳簿)
	private Integer mitsumorihkmaeskkmmsuL5R; // 見積変更前償却月数(第5帳簿)
	private Double mitsumorihkmaeskkritsuL5R; // 見積変更前償却率(第5帳簿)
	private Long tomitsumorihkchoseigkL5R; // 当期見積変更調整額(第5帳簿)
	private Long stkgkL5R; // 取得価額(第5帳簿)
	private String skkhoL5R; // 償却方法(第5帳簿)
	private Integer tainenL5R; // 耐用年数(第5帳簿)
	private Double skkritsuL5R; // 償却率(第5帳簿)
	private Integer skkmmsuL5R; // 償却月数(第5帳簿)
	private Integer zanzonmmsuL5R; // 残存月数(第5帳簿)
	private Long ksbokaL5R; // 期首帳簿価額(第5帳簿)
	private Long skkcalckisogkL5R; // 償却計算基礎額(第5帳簿)
	private Double zanzonritsuL5R; // 残存率(第5帳簿)
	private Long zanzongkL5R; // 残存価額(第5帳簿)
	private Long ksftskkruigkL5R; // 期首普通償却累計額(第5帳簿)
	private Long kszkskkruigkL5R; // 期首増加償却累計額(第5帳簿)
	private Long toToftskkgkL5R; // 当期普通償却額(当月累計)(第5帳簿)
	private Long toTozkskkgkL5R; // 当期増加償却額(当月累計)(第5帳簿)
	private Long toToniniskkgkL5R; // 当期任意償却額(当月)(第5帳簿)
	private Long toBokaL5R; // 当月末帳簿価額(第5帳簿)
	private Long zogenbokaL5R; // 増減帳簿価額(第5帳簿)
	private Double zkskkritsuL5R; // 増加償却率(第5帳簿)
	private String yukyustymL5R; // 遊休開始年月(第5帳簿)
	private String yukyufkymL5R; // 遊休復帰年月(第5帳簿)
	private String shonencalckbnL5R; // 初年度計算区分(第5帳簿)
	private String skkkanryoflgL5R; // 償却完了(第5帳簿)
	private String skkkirikaeyyL5R; // 償却切替年度(第5帳簿)
	private String zanzonskkstyyL5R; // 残存償却開始年度(第5帳簿)
	private Long mitsumorigkWaribikimaeL6R; // 見積価額(割引前)(第6帳簿)
	private Double waribikiritsuL6R; // 割引率(第6帳簿)
	private Long keijogkWaribikigoL6R; // 計上価額(割引後)(第6帳簿)
	private Long kssaimubokaL6R; // 期首債務帳簿価額(第6帳簿)
	private Long risokucalckisogkL6R; // 利息計算基礎額(第6帳簿)
	private Long toSaimubokaL6R; // 当月末債務帳簿価額(第6帳簿)
	private Long toSaimuzogenbokaL6R; // 債務増減帳簿価額(第6帳簿)
	private Long toTorisokugkL6R; // 当期利息額(当月累計)(第6帳簿)
	private Long ksrisokuruigkL6R; // 期首利息累計額(第6帳簿)
	private String mitsumorihkymdL6R; // 除去債務見積変更日(第6帳簿)
	private String famitsumorihkymdL6R; // 固定資産見積変更日(第6帳簿)
	private Long mitsumorihkgobokaL6R; // 見積変更後帳簿価額(第6帳簿)
	private Integer mitsumorihkmaetainenL6R; // 見積変更前耐用年数(第6帳簿)
	private Integer mitsumorihkmaeskkmmsuL6R; // 見積変更前償却月数(第6帳簿)
	private Double mitsumorihkmaeskkritsuL6R; // 見積変更前償却率(第6帳簿)
	private Long tomitsumorihkchoseigkL6R; // 当期見積変更調整額(第6帳簿)
	private Long stkgkL6R; // 取得価額(第6帳簿)
	private String skkhoL6R; // 償却方法(第6帳簿)
	private Integer tainenL6R; // 耐用年数(第6帳簿)
	private Double skkritsuL6R; // 償却率(第6帳簿)
	private Integer skkmmsuL6R; // 償却月数(第6帳簿)
	private Integer zanzonmmsuL6R; // 残存月数(第6帳簿)
	private Long ksbokaL6R; // 期首帳簿価額(第6帳簿)
	private Long skkcalckisogkL6R; // 償却計算基礎額(第6帳簿)
	private Double zanzonritsuL6R; // 残存率(第6帳簿)
	private Long zanzongkL6R; // 残存価額(第6帳簿)
	private Long ksftskkruigkL6R; // 期首普通償却累計額(第6帳簿)
	private Long kszkskkruigkL6R; // 期首増加償却累計額(第6帳簿)
	private Long toToftskkgkL6R; // 当期普通償却額(当月累計)(第6帳簿)
	private Long toTozkskkgkL6R; // 当期増加償却額(当月累計)(第6帳簿)
	private Long toToniniskkgkL6R; // 当期任意償却額(当月)(第6帳簿)
	private Long toBokaL6R; // 当月末帳簿価額(第6帳簿)
	private Long zogenbokaL6R; // 増減帳簿価額(第6帳簿)
	private Double zkskkritsuL6R; // 増加償却率(第6帳簿)
	private String yukyustymL6R; // 遊休開始年月(第6帳簿)
	private String yukyufkymL6R; // 遊休復帰年月(第6帳簿)
	private String shonencalckbnL6R; // 初年度計算区分(第6帳簿)
	private String skkkanryoflgL6R; // 償却完了(第6帳簿)
	private String skkkirikaeyyL6R; // 償却切替年度(第6帳簿)
	private String zanzonskkstyyL6R; // 残存償却開始年度(第6帳簿)
	private String konyucdR; // 購入先コード
	private String konyunmR; // 購入先名称
	private String kashicdR; // 貸出先コード
	private String kashinmR; // 貸出先名称
	private String knrbunruicdR; // 管理分類コード
	private String biko1R; // 備考1
	private String biko2R; // 備考2
	private String stkringinoR; // 取得時稟議決裁番号
	private String stktekiyoR; // 取得時摘要
	private String groupcdR; // グループコード
	private String groupnmR; // グループ名称
	private String shinariocdR; // シナリオコード
	private String shinarionmR; // シナリオ名称
	private String shuyoshisankbnR; // 主要資産区分
	private String niniLd_1cdR; // 任意(台帳)1コード
	private String niniLd_1nmR; // 任意(台帳)1名称
	private String niniLd_2cdR; // 任意(台帳)2コード
	private String niniLd_2nmR; // 任意(台帳)2名称
	private String niniLd_3cdR; // 任意(台帳)3コード
	private String niniLd_3nmR; // 任意(台帳)3名称
	private String niniLd_4cdR; // 任意(台帳)4コード
	private String niniLd_4nmR; // 任意(台帳)4名称
	private String niniLd_5cdR; // 任意(台帳)5コード
	private String niniLd_5nmR; // 任意(台帳)5名称
	private String niniLd_6cdR; // 任意(台帳)6コード
	private String niniLd_6nmR; // 任意(台帳)6名称
	private String niniLd_7cdR; // 任意(台帳)7コード
	private String niniLd_7nmR; // 任意(台帳)7名称
	private String niniLd_8cdR; // 任意(台帳)8コード
	private String niniLd_8nmR; // 任意(台帳)8名称
	private String niniLd_9cdR; // 任意(台帳)9コード
	private String niniLd_9nmR; // 任意(台帳)9名称
	private String niniLd_10cdR; // 任意(台帳)10コード
	private String niniLd_10nmR; // 任意(台帳)10名称
	private String niniLd_11cdR; // 任意(台帳)11コード
	private String niniLd_11nmR; // 任意(台帳)11名称
	private String niniLd_12cdR; // 任意(台帳)12コード
	private String niniLd_12nmR; // 任意(台帳)12名称
	private String niniLd_13cdR; // 任意(台帳)13コード
	private String niniLd_13nmR; // 任意(台帳)13名称
	private String niniLd_14cdR; // 任意(台帳)14コード
	private String niniLd_14nmR; // 任意(台帳)14名称
	private String niniLd_15cdR; // 任意(台帳)15コード
	private String niniLd_15nmR; // 任意(台帳)15名称
	private String niniLd_16cdR; // 任意(台帳)16コード
	private String niniLd_16nmR; // 任意(台帳)16名称
	private String niniLd_17cdR; // 任意(台帳)17コード
	private String niniLd_17nmR; // 任意(台帳)17名称
	private String niniLd_18cdR; // 任意(台帳)18コード
	private String niniLd_18nmR; // 任意(台帳)18名称
	private String niniLd_19cdR; // 任意(台帳)19コード
	private String niniLd_19nmR; // 任意(台帳)19名称
	private String niniLd_20cdR; // 任意(台帳)20コード
	private String niniLd_20nmR; // 任意(台帳)20名称
	private Long toshinkkyyR; // 当年申告年
	private Long ksgensonruigkkR; // 期首減損累計額(会社帳簿)
	private Long togensongkkR; // 当期減損額(会社帳簿)
	private Long skkcalczanzongkkR; // 償却計算残存価額(会社帳簿)
	private Long gensonbokakR; // 減損後帳簿価額(会社帳簿)
	private Integer gensonmaetainenkR; // 減損前耐用年数(会社帳簿)
	private Integer gensonmaeskkmmsukR; // 減損前償却月数(会社帳簿)
	private String gensonymdkR; // 減損評価年月日(会社帳簿)
	private Long genshistkgkkR; // 原始取得価額(会社帳簿)
	private Long kaiteistkgkkR; // 改定後取得価額(会社帳簿)
	private Integer kaiteitainenkR; // 改定前耐用年数(会社帳簿)
	private String kaiteiymdkR; // 改定年月日(会社帳簿)
	private String yusenzogenkbnkR; // 優先増減区分(会社帳簿)
	private Long ksgensonruigkzR; // 期首減損累計額(税法帳簿)
	private Long togensongkzR; // 当期減損額(税法帳簿)
	private Long skkcalczanzongkzR; // 償却計算残存価額(税法帳簿)
	private Long gensonbokazR; // 減損後帳簿価額(税法帳簿)
	private Integer gensonmaetainenzR; // 減損前耐用年数(税法帳簿)
	private Integer gensonmaeskkmmsuzR; // 減損前償却月数(税法帳簿)
	private String gensonymdzR; // 減損評価年月日(税法帳簿)
	private Long genshistkgkzR; // 原始取得価額(税法帳簿)
	private Long kaiteistkgkzR; // 改定後取得価額(税法帳簿)
	private Integer kaiteitainenzR; // 改定前耐用年数(税法帳簿)
	private String kaiteiymdzR; // 改定年月日(税法帳簿)
	private String yusenzogenkbnzR; // 優先増減区分(税法帳簿)
	private Long ksgensonruigkL3R; // 期首減損累計額(第3帳簿)
	private Long togensongkL3R; // 当期減損額(第3帳簿)
	private Long skkcalczanzongkL3R; // 償却計算残存価額(第3帳簿)
	private Long gensonbokaL3R; // 減損後帳簿価額(第3帳簿)
	private Integer gensonmaetainenL3R; // 減損前耐用年数(第3帳簿)
	private Integer gensonmaeskkmmsuL3R; // 減損前償却月数(第3帳簿)
	private String gensonymdL3R; // 減損評価年月日(第3帳簿)
	private Long genshistkgkL3R; // 原始取得価額(第3帳簿)
	private Long kaiteistkgkL3R; // 改定後取得価額(第3帳簿)
	private Integer kaiteitainenL3R; // 改定前耐用年数(第3帳簿)
	private String kaiteiymdL3R; // 改定年月日(第3帳簿)
	private String yusenzogenkbnL3R; // 優先増減区分(第3帳簿)
	private Long ksgensonruigkL4R; // 期首減損累計額(第4帳簿)
	private Long togensongkL4R; // 当期減損額(第4帳簿)
	private Long skkcalczanzongkL4R; // 償却計算残存価額(第4帳簿)
	private Long gensonbokaL4R; // 減損後帳簿価額(第4帳簿)
	private Integer gensonmaetainenL4R; // 減損前耐用年数(第4帳簿)
	private Integer gensonmaeskkmmsuL4R; // 減損前償却月数(第4帳簿)
	private String gensonymdL4R; // 減損評価年月日(第4帳簿)
	private Long genshistkgkL4R; // 原始取得価額(第4帳簿)
	private Long kaiteistkgkL4R; // 改定後取得価額(第4帳簿)
	private Integer kaiteitainenL4R; // 改定前耐用年数(第4帳簿)
	private String kaiteiymdL4R; // 改定年月日(第4帳簿)
	private String yusenzogenkbnL4R; // 優先増減区分(第4帳簿)
	private Long ksgensonruigkL5R; // 期首減損累計額(第5帳簿)
	private Long togensongkL5R; // 当期減損額(第5帳簿)
	private Long skkcalczanzongkL5R; // 償却計算残存価額(第5帳簿)
	private Long gensonbokaL5R; // 減損後帳簿価額(第5帳簿)
	private Integer gensonmaetainenL5R; // 減損前耐用年数(第5帳簿)
	private Integer gensonmaeskkmmsuL5R; // 減損前償却月数(第5帳簿)
	private String gensonymdL5R; // 減損評価年月日(第5帳簿)
	private Long genshistkgkL5R; // 原始取得価額(第5帳簿)
	private Long kaiteistkgkL5R; // 改定後取得価額(第5帳簿)
	private Integer kaiteitainenL5R; // 改定前耐用年数(第5帳簿)
	private String kaiteiymdL5R; // 改定年月日(第5帳簿)
	private String yusenzogenkbnL5R; // 優先増減区分(第5帳簿)
	private Long ksgensonruigkL6R; // 期首減損累計額(第6帳簿)
	private Long togensongkL6R; // 当期減損額(第6帳簿)
	private Long skkcalczanzongkL6R; // 償却計算残存価額(第6帳簿)
	private Long gensonbokaL6R; // 減損後帳簿価額(第6帳簿)
	private Integer gensonmaetainenL6R; // 減損前耐用年数(第6帳簿)
	private Integer gensonmaeskkmmsuL6R; // 減損前償却月数(第6帳簿)
	private String gensonymdL6R; // 減損評価年月日(第6帳簿)
	private Long genshistkgkL6R; // 原始取得価額(第6帳簿)
	private Long kaiteistkgkL6R; // 改定後取得価額(第6帳簿)
	private Integer kaiteitainenL6R; // 改定前耐用年数(第6帳簿)
	private String kaiteiymdL6R; // 改定年月日(第6帳簿)
	private String yusenzogenkbnL6R; // 優先増減区分(第6帳簿)
	private String updidR; // ユーザID
	private String updymdR; // 更新時間

	private String shisanNum; // 資産番号

	private String cpkeijoymdF; // 建仮計上年月日(フォーマット日付)
	private String furikaeymdF; // 振替年月日(フォーマット日付)
	private String stkymdF; // 取得年月日(フォーマット日付)
	private String kadoymdF; // 稼働年月日(フォーマット日付)
	private String jbkymdF; // 除売却年月日(フォーマット日付)
	private String idoymdF; // 移動年月日(フォーマット日付)
	private String genshistkymdF; // 原始取得年月日(フォーマット日付)
	private String gensonymdkF; // 減損評価年月日(会社帳簿)(フォーマット日付)
	private String kaiteiymdkF; // 改定年月日(会社帳簿)(フォーマット日付)
	private String gensonymdzF; // 減損評価年月日(税法帳簿)(フォーマット日付)
	private String kaiteiymdzF; // 改定年月日(税法帳簿)(フォーマット日付)
	private String gensonymdL3F; // 減損評価年月日(第3帳簿)(フォーマット日付)
	private String kaiteiymdL3F; // 改定年月日(第3帳簿)(フォーマット日付)
	private String gensonymdL4F; // 減損評価年月日(第4帳簿)(フォーマット日付)
	private String kaiteiymdL4F; // 改定年月日(第4帳簿)(フォーマット日付)
	private String gensonymdL5F; // 減損評価年月日(第5帳簿)(フォーマット日付)
	private String kaiteiymdL5F; // 改定年月日(第5帳簿)(フォーマット日付)
	private String gensonymdL6F; // 減損評価年月日(第6帳簿)(フォーマット日付)
	private String kaiteiymdL6F; // 改定年月日(第6帳簿)(フォーマット日付)
	private String updymdF; // 更新年月日(フォーマット日付)
	private String addUpPlanDateF; // 計上予定日(任意項目eAsset追加定義)(フォーマット日付)

	private String yukyustymkF; // 遊休開始年月(会社帳簿)
	private String yukyustymzF; // 遊休開始年月(税法帳簿)
	private String yukyustymL3F; // 遊休開始年月(第3帳簿)
	private String yukyustymL4F; // 遊休開始年月(第4帳簿)
	private String yukyustymL5F; // 遊休開始年月(第5帳簿)
	private String yukyustymL6F; // 遊休開始年月(第6帳簿)

	private String yukyufkymkF; // 遊休開始年月(会社帳簿)
	private String yukyufkymzF; // 遊休開始年月(税法帳簿)
	private String yukyufkymL3F; // 遊休復帰年月(第3帳簿)
	private String yukyufkymL4F; // 遊休復帰年月(第4帳簿)
	private String yukyufkymL5F; // 遊休復帰年月(第5帳簿)
	private String yukyufkymL6F; // 遊休復帰年月(第6帳簿)

	private String saimukeijoymdRF; // 債務計上年月日
	private String saimurikoymdRF; // 債務履行年月日
	private String kadoymdRF; // 稼働年月日
	private String jbkymdRF; // 除売却年月日
	private String idoymdRF; // 移動年月日

	private String mitsumorihkymdkRF; // 除去債務見積変更日(会社帳簿)
	private String mitsumorihkymdzRF; // 除去債務見積変更日(税法帳簿)
	private String mitsumorihkymdL3RF; // 除去債務見積変更日(第3帳簿)
	private String mitsumorihkymdL4RF; // 除去債務見積変更日(第4帳簿)
	private String mitsumorihkymdL5RF; // 除去債務見積変更日(第5帳簿)
	private String mitsumorihkymdL6RF;	 // 除去債務見積変更日(第6帳簿)

	private String famitsumorihkymdkRF;  // 固定資産見積変更日(会社帳簿)
	private String famitsumorihkymdzRF;  // 固定資産見積変更日(税法帳簿)
	private String famitsumorihkymdL3RF; // 固定資産見積変更日(第3帳簿)
	private String famitsumorihkymdL4RF; // 固定資産見積変更日(第4帳簿)
	private String famitsumorihkymdL5RF; // 固定資産見積変更日(第5帳簿)
	private String famitsumorihkymdL6RF; // 固定資産見積変更日(第6帳簿)

	private String chukokbnName; // 中古区分名
	private String skkshisankbnName; // 償却資産区分名
	private String askkbnName; // 圧縮区分名
	private String tkkbnName; // 特別償却区分名
	private String shuyoshisankbnName; // 主要資産区分名
	private String gappeiukekbnName; // 合併受入資産名
	private String jksaimukeijokbnName; // 除去債務計上区分

	private String skkhokName; // 償却方法名(会計帳簿)
	private String skkhozName; // 償却方法名(税法帳簿)
	private String skkhoL3Name; // 償却方法名(第3帳簿)
	private String skkhoL4Name; // 償却方法名(第4帳簿)
	private String skkhoL5Name; // 償却方法名(第5帳簿)
	private String skkhoL6Name; // 償却方法名(第6帳簿)

	private String  shonencalckbnkName; // 初年度計算区分名(会計帳簿)
	private String  shonencalckbnzName; // 初年度計算区分名(税法帳簿)
	private String  shonencalckbnL3Name; // 初年度計算区分名(第3帳簿)
	private String  shonencalckbnL4Name; // 初年度計算区分名(第4帳簿)
	private String  shonencalckbnL5Name; // 初年度計算区分名(第5帳簿)
	private String  shonencalckbnL6Name; // 初年度計算区分名(第6帳簿)

	private String  skkkanryoflgkName; // 償却完了フラグ名(会計帳簿)
	private String  skkkanryoflgzName; // 償却完了フラグ名(税法帳簿)
	private String  skkkanryoflgL3Name; // 償却完了フラグ名(第3帳簿)
	private String  skkkanryoflgL4Name; // 償却完了フラグ名(第4帳簿)
	private String  skkkanryoflgL5Name; // 償却完了フラグ名(第5帳簿)
	private String  skkkanryoflgL6Name; // 償却完了フラグ名(第6帳簿)

	//////////// PpfsFldSR
	private String kadoymdL;	//	稼動(予定)日/除売却日
	private String slipAstPtjCode;	//	伝票番号/資産番号
	private String purCompanyNameL;	//	仕入先(検索結果用)
	private String furikaeNum;	//	振替資産番号

	private String keijoymdL;	//	計上日

	private String holStaffCode;	//	無形管理担当者コード
	private String holStaffName;	//	無形管理担当者名

	private String holCompanyCode;	//	保有会社コード
	private String holSectionCode;	//	保有部署コード
	private Integer holSectionYear;	//	保有部署年度
	private String holSectionName;	//	保有部署


	// ダウンロードのみで使用
	private String dscAttribute1; // 管理項目1
	private String dscAttribute2; // 管理項目2
	private String dscAttribute3; // 管理項目3
	private String dscAttribute4; // 管理項目4
	private String dscAttribute5; // 管理項目5
	private String dscAttribute6; // 管理項目6
	private String dscAttribute7; // 管理項目7
	private String dscAttribute8; // 管理項目8
	private String dscAttribute9; // 管理項目9
	private String dscAttribute10; // 管理項目10
	private String dscAttribute11; // 管理項目11
	private String dscAttribute12; // 管理項目12
	private String dscAttribute13; // 管理項目13
	private String dscAttribute14; // 管理項目14
	private String dscAttribute15; // 管理項目15
	private String dscAttribute16; // 管理項目16
	private String dscAttribute17; // 管理項目17
	private String dscAttribute18; // 管理項目18
	private String dscAttribute19; // 管理項目19
	private String dscAttribute20; // 管理項目20
	/**
	 * @return the koyuno
	 */
	public Long getKoyuno() {
		return koyuno;
	}
	/**
	 * @param koyuno the koyuno to set
	 */
	public void setKoyuno(Long koyuno) {
		this.koyuno = koyuno;
	}
	/**
	 * @return the kaishacd
	 */
	public String getKaishacd() {
		return kaishacd;
	}
	/**
	 * @param kaishacd the kaishacd to set
	 */
	public void setKaishacd(String kaishacd) {
		this.kaishacd = kaishacd;
	}
	/**
	 * @return the kaishanm
	 */
	public String getKaishanm() {
		return kaishanm;
	}
	/**
	 * @param kaishanm the kaishanm to set
	 */
	public void setKaishanm(String kaishanm) {
		this.kaishanm = kaishanm;
	}
	/**
	 * @return the oya
	 */
	public String getOya() {
		return oya;
	}
	/**
	 * @param oya the oya to set
	 */
	public void setOya(String oya) {
		this.oya = oya;
	}
	/**
	 * @return the eda
	 */
	public String getEda() {
		return eda;
	}
	/**
	 * @param eda the eda to set
	 */
	public void setEda(String eda) {
		this.eda = eda;
	}
	/**
	 * @return the shisanknrkbn
	 */
	public String getShisanknrkbn() {
		return shisanknrkbn;
	}
	/**
	 * @param shisanknrkbn the shisanknrkbn to set
	 */
	public void setShisanknrkbn(String shisanknrkbn) {
		this.shisanknrkbn = shisanknrkbn;
	}
	/**
	 * @return the shisanjotaikbn
	 */
	public String getShisanjotaikbn() {
		return shisanjotaikbn;
	}
	/**
	 * @param shisanjotaikbn the shisanjotaikbn to set
	 */
	public void setShisanjotaikbn(String shisanjotaikbn) {
		this.shisanjotaikbn = shisanjotaikbn;
	}
	/**
	 * @return the shisannmcd
	 */
	public String getShisannmcd() {
		return shisannmcd;
	}
	/**
	 * @param shisannmcd the shisannmcd to set
	 */
	public void setShisannmcd(String shisannmcd) {
		this.shisannmcd = shisannmcd;
	}
	/**
	 * @return the shisannm
	 */
	public String getShisannm() {
		return shisannm;
	}
	/**
	 * @param shisannm the shisannm to set
	 */
	public void setShisannm(String shisannm) {
		this.shisannm = shisannm;
	}
	/**
	 * @return the shisannmk
	 */
	public String getShisannmk() {
		return shisannmk;
	}
	/**
	 * @param shisannmk the shisannmk to set
	 */
	public void setShisannmk(String shisannmk) {
		this.shisannmk = shisannmk;
	}
	/**
	 * @return the soshiki1cd
	 */
	public String getSoshiki1cd() {
		return soshiki1cd;
	}
	/**
	 * @param soshiki1cd the soshiki1cd to set
	 */
	public void setSoshiki1cd(String soshiki1cd) {
		this.soshiki1cd = soshiki1cd;
	}
	/**
	 * @return the soshiki1nm
	 */
	public String getSoshiki1nm() {
		return soshiki1nm;
	}
	/**
	 * @param soshiki1nm the soshiki1nm to set
	 */
	public void setSoshiki1nm(String soshiki1nm) {
		this.soshiki1nm = soshiki1nm;
	}
	/**
	 * @return the soshiki2cd
	 */
	public String getSoshiki2cd() {
		return soshiki2cd;
	}
	/**
	 * @param soshiki2cd the soshiki2cd to set
	 */
	public void setSoshiki2cd(String soshiki2cd) {
		this.soshiki2cd = soshiki2cd;
	}
	/**
	 * @return the soshiki2nm
	 */
	public String getSoshiki2nm() {
		return soshiki2nm;
	}
	/**
	 * @param soshiki2nm the soshiki2nm to set
	 */
	public void setSoshiki2nm(String soshiki2nm) {
		this.soshiki2nm = soshiki2nm;
	}
	/**
	 * @return the soshiki3cd
	 */
	public String getSoshiki3cd() {
		return soshiki3cd;
	}
	/**
	 * @param soshiki3cd the soshiki3cd to set
	 */
	public void setSoshiki3cd(String soshiki3cd) {
		this.soshiki3cd = soshiki3cd;
	}
	/**
	 * @return the soshiki3nm
	 */
	public String getSoshiki3nm() {
		return soshiki3nm;
	}
	/**
	 * @param soshiki3nm the soshiki3nm to set
	 */
	public void setSoshiki3nm(String soshiki3nm) {
		this.soshiki3nm = soshiki3nm;
	}
	/**
	 * @return the soshiki4cd
	 */
	public String getSoshiki4cd() {
		return soshiki4cd;
	}
	/**
	 * @param soshiki4cd the soshiki4cd to set
	 */
	public void setSoshiki4cd(String soshiki4cd) {
		this.soshiki4cd = soshiki4cd;
	}
	/**
	 * @return the soshiki4nm
	 */
	public String getSoshiki4nm() {
		return soshiki4nm;
	}
	/**
	 * @param soshiki4nm the soshiki4nm to set
	 */
	public void setSoshiki4nm(String soshiki4nm) {
		this.soshiki4nm = soshiki4nm;
	}
	/**
	 * @return the setchicd
	 */
	public String getSetchicd() {
		return setchicd;
	}
	/**
	 * @param setchicd the setchicd to set
	 */
	public void setSetchicd(String setchicd) {
		this.setchicd = setchicd;
	}
	/**
	 * @return the setchinm
	 */
	public String getSetchinm() {
		return setchinm;
	}
	/**
	 * @param setchinm the setchinm to set
	 */
	public void setSetchinm(String setchinm) {
		this.setchinm = setchinm;
	}
	/**
	 * @return the skkhihfcd
	 */
	public String getSkkhihfcd() {
		return skkhihfcd;
	}
	/**
	 * @param skkhihfcd the skkhihfcd to set
	 */
	public void setSkkhihfcd(String skkhihfcd) {
		this.skkhihfcd = skkhihfcd;
	}
	/**
	 * @return the hfnm
	 */
	public String getHfnm() {
		return hfnm;
	}
	/**
	 * @param hfnm the hfnm to set
	 */
	public void setHfnm(String hfnm) {
		this.hfnm = hfnm;
	}
	/**
	 * @return the laryohfcd
	 */
	public String getLaryohfcd() {
		return laryohfcd;
	}
	/**
	 * @param laryohfcd the laryohfcd to set
	 */
	public void setLaryohfcd(String laryohfcd) {
		this.laryohfcd = laryohfcd;
	}
	/**
	 * @return the laryohfnm
	 */
	public String getLaryohfnm() {
		return laryohfnm;
	}
	/**
	 * @param laryohfnm the laryohfnm to set
	 */
	public void setLaryohfnm(String laryohfnm) {
		this.laryohfnm = laryohfnm;
	}
	/**
	 * @return the shuruicd
	 */
	public String getShuruicd() {
		return shuruicd;
	}
	/**
	 * @param shuruicd the shuruicd to set
	 */
	public void setShuruicd(String shuruicd) {
		this.shuruicd = shuruicd;
	}
	/**
	 * @return the shuruinm
	 */
	public String getShuruinm() {
		return shuruinm;
	}
	/**
	 * @param shuruinm the shuruinm to set
	 */
	public void setShuruinm(String shuruinm) {
		this.shuruinm = shuruinm;
	}
	/**
	 * @return the kozocd
	 */
	public String getKozocd() {
		return kozocd;
	}
	/**
	 * @param kozocd the kozocd to set
	 */
	public void setKozocd(String kozocd) {
		this.kozocd = kozocd;
	}
	/**
	 * @return the kozonm
	 */
	public String getKozonm() {
		return kozonm;
	}
	/**
	 * @param kozonm the kozonm to set
	 */
	public void setKozonm(String kozonm) {
		this.kozonm = kozonm;
	}
	/**
	 * @return the bunruicd
	 */
	public String getBunruicd() {
		return bunruicd;
	}
	/**
	 * @param bunruicd the bunruicd to set
	 */
	public void setBunruicd(String bunruicd) {
		this.bunruicd = bunruicd;
	}
	/**
	 * @return the bunruinm
	 */
	public String getBunruinm() {
		return bunruinm;
	}
	/**
	 * @param bunruinm the bunruinm to set
	 */
	public void setBunruinm(String bunruinm) {
		this.bunruinm = bunruinm;
	}
	/**
	 * @return the cpkeijoymd
	 */
	public String getCpkeijoymd() {
		return cpkeijoymd;
	}
	/**
	 * @param cpkeijoymd the cpkeijoymd to set
	 */
	public void setCpkeijoymd(String cpkeijoymd) {
		this.cpkeijoymd = cpkeijoymd;
	}
	/**
	 * @return the furikaeymd
	 */
	public String getFurikaeymd() {
		return furikaeymd;
	}
	/**
	 * @param furikaeymd the furikaeymd to set
	 */
	public void setFurikaeymd(String furikaeymd) {
		this.furikaeymd = furikaeymd;
	}
	/**
	 * @return the furikaegoOya
	 */
	public String getFurikaegoOya() {
		return furikaegoOya;
	}
	/**
	 * @param furikaegoOya the furikaegoOya to set
	 */
	public void setFurikaegoOya(String furikaegoOya) {
		this.furikaegoOya = furikaegoOya;
	}
	/**
	 * @return the furikaegoEda
	 */
	public String getFurikaegoEda() {
		return furikaegoEda;
	}
	/**
	 * @param furikaegoEda the furikaegoEda to set
	 */
	public void setFurikaegoEda(String furikaegoEda) {
		this.furikaegoEda = furikaegoEda;
	}
	/**
	 * @return the stkymd
	 */
	public String getStkymd() {
		return stkymd;
	}
	/**
	 * @param stkymd the stkymd to set
	 */
	public void setStkymd(String stkymd) {
		this.stkymd = stkymd;
	}
	/**
	 * @return the kadoymd
	 */
	public String getKadoymd() {
		return kadoymd;
	}
	/**
	 * @param kadoymd the kadoymd to set
	 */
	public void setKadoymd(String kadoymd) {
		this.kadoymd = kadoymd;
	}
	/**
	 * @return the jbkymd
	 */
	public String getJbkymd() {
		return jbkymd;
	}
	/**
	 * @param jbkymd the jbkymd to set
	 */
	public void setJbkymd(String jbkymd) {
		this.jbkymd = jbkymd;
	}
	/**
	 * @return the idoymd
	 */
	public String getIdoymd() {
		return idoymd;
	}
	/**
	 * @param idoymd the idoymd to set
	 */
	public void setIdoymd(String idoymd) {
		this.idoymd = idoymd;
	}
	/**
	 * @return the suryo
	 */
	public Integer getSuryo() {
		return suryo;
	}
	/**
	 * @param suryo the suryo to set
	 */
	public void setSuryo(Integer suryo) {
		this.suryo = suryo;
	}
	/**
	 * @return the suryoTani
	 */
	public String getSuryoTani() {
		return suryoTani;
	}
	/**
	 * @param suryoTani the suryoTani to set
	 */
	public void setSuryoTani(String suryoTani) {
		this.suryoTani = suryoTani;
	}
	/**
	 * @return the menseki
	 */
	public Integer getMenseki() {
		return menseki;
	}
	/**
	 * @param menseki the menseki to set
	 */
	public void setMenseki(Integer menseki) {
		this.menseki = menseki;
	}
	/**
	 * @return the mensekiTani
	 */
	public String getMensekiTani() {
		return mensekiTani;
	}
	/**
	 * @param mensekiTani the mensekiTani to set
	 */
	public void setMensekiTani(String mensekiTani) {
		this.mensekiTani = mensekiTani;
	}
	/**
	 * @return the chukokbn
	 */
	public String getChukokbn() {
		return chukokbn;
	}
	/**
	 * @param chukokbn the chukokbn to set
	 */
	public void setChukokbn(String chukokbn) {
		this.chukokbn = chukokbn;
	}
	/**
	 * @return the tosaimukeijokbn
	 */
	public String getTosaimukeijokbn() {
		return tosaimukeijokbn;
	}
	/**
	 * @param tosaimukeijokbn the tosaimukeijokbn to set
	 */
	public void setTosaimukeijokbn(String tosaimukeijokbn) {
		this.tosaimukeijokbn = tosaimukeijokbn;
	}
	/**
	 * @return the jksaimukeijokbn
	 */
	public String getJksaimukeijokbn() {
		return jksaimukeijokbn;
	}
	/**
	 * @param jksaimukeijokbn the jksaimukeijokbn to set
	 */
	public void setJksaimukeijokbn(String jksaimukeijokbn) {
		this.jksaimukeijokbn = jksaimukeijokbn;
	}
	/**
	 * @return the stkgkk
	 */
	public Long getStkgkk() {
		return stkgkk;
	}
	/**
	 * @param stkgkk the stkgkk to set
	 */
	public void setStkgkk(Long stkgkk) {
		this.stkgkk = stkgkk;
	}
	/**
	 * @return the askstkgkk
	 */
	public Long getAskstkgkk() {
		return askstkgkk;
	}
	/**
	 * @param askstkgkk the askstkgkk to set
	 */
	public void setAskstkgkk(Long askstkgkk) {
		this.askstkgkk = askstkgkk;
	}
	/**
	 * @return the skkhok
	 */
	public String getSkkhok() {
		return skkhok;
	}
	/**
	 * @param skkhok the skkhok to set
	 */
	public void setSkkhok(String skkhok) {
		this.skkhok = skkhok;
	}
	/**
	 * @return the tainenk
	 */
	public Integer getTainenk() {
		return tainenk;
	}
	/**
	 * @param tainenk the tainenk to set
	 */
	public void setTainenk(Integer tainenk) {
		this.tainenk = tainenk;
	}
	/**
	 * @return the skkritsuk
	 */
	public Double getSkkritsuk() {
		return skkritsuk;
	}
	/**
	 * @param skkritsuk the skkritsuk to set
	 */
	public void setSkkritsuk(Double skkritsuk) {
		this.skkritsuk = skkritsuk;
	}
	/**
	 * @return the skkmmsuk
	 */
	public Integer getSkkmmsuk() {
		return skkmmsuk;
	}
	/**
	 * @param skkmmsuk the skkmmsuk to set
	 */
	public void setSkkmmsuk(Integer skkmmsuk) {
		this.skkmmsuk = skkmmsuk;
	}
	/**
	 * @return the zanzonmmsuk
	 */
	public Integer getZanzonmmsuk() {
		return zanzonmmsuk;
	}
	/**
	 * @param zanzonmmsuk the zanzonmmsuk to set
	 */
	public void setZanzonmmsuk(Integer zanzonmmsuk) {
		this.zanzonmmsuk = zanzonmmsuk;
	}
	/**
	 * @return the ksbokak
	 */
	public Long getKsbokak() {
		return ksbokak;
	}
	/**
	 * @param ksbokak the ksbokak to set
	 */
	public void setKsbokak(Long ksbokak) {
		this.ksbokak = ksbokak;
	}
	/**
	 * @return the skkcalckisogkk
	 */
	public Long getSkkcalckisogkk() {
		return skkcalckisogkk;
	}
	/**
	 * @param skkcalckisogkk the skkcalckisogkk to set
	 */
	public void setSkkcalckisogkk(Long skkcalckisogkk) {
		this.skkcalckisogkk = skkcalckisogkk;
	}
	/**
	 * @return the zanzonritsuk
	 */
	public Double getZanzonritsuk() {
		return zanzonritsuk;
	}
	/**
	 * @param zanzonritsuk the zanzonritsuk to set
	 */
	public void setZanzonritsuk(Double zanzonritsuk) {
		this.zanzonritsuk = zanzonritsuk;
	}
	/**
	 * @return the zanzongkk
	 */
	public Long getZanzongkk() {
		return zanzongkk;
	}
	/**
	 * @param zanzongkk the zanzongkk to set
	 */
	public void setZanzongkk(Long zanzongkk) {
		this.zanzongkk = zanzongkk;
	}
	/**
	 * @return the ksftskkruigkk
	 */
	public Long getKsftskkruigkk() {
		return ksftskkruigkk;
	}
	/**
	 * @param ksftskkruigkk the ksftskkruigkk to set
	 */
	public void setKsftskkruigkk(Long ksftskkruigkk) {
		this.ksftskkruigkk = ksftskkruigkk;
	}
	/**
	 * @return the kszkskkruigkk
	 */
	public Long getKszkskkruigkk() {
		return kszkskkruigkk;
	}
	/**
	 * @param kszkskkruigkk the kszkskkruigkk to set
	 */
	public void setKszkskkruigkk(Long kszkskkruigkk) {
		this.kszkskkruigkk = kszkskkruigkk;
	}
	/**
	 * @return the kstkskkruigkk
	 */
	public Long getKstkskkruigkk() {
		return kstkskkruigkk;
	}
	/**
	 * @param kstkskkruigkk the kstkskkruigkk to set
	 */
	public void setKstkskkruigkk(Long kstkskkruigkk) {
		this.kstkskkruigkk = kstkskkruigkk;
	}
	/**
	 * @return the zenToftskkgkk
	 */
	public Long getZenToftskkgkk() {
		return zenToftskkgkk;
	}
	/**
	 * @param zenToftskkgkk the zenToftskkgkk to set
	 */
	public void setZenToftskkgkk(Long zenToftskkgkk) {
		this.zenToftskkgkk = zenToftskkgkk;
	}
	/**
	 * @return the zenTozkskkgkk
	 */
	public Long getZenTozkskkgkk() {
		return zenTozkskkgkk;
	}
	/**
	 * @param zenTozkskkgkk the zenTozkskkgkk to set
	 */
	public void setZenTozkskkgkk(Long zenTozkskkgkk) {
		this.zenTozkskkgkk = zenTozkskkgkk;
	}
	/**
	 * @return the zenTotkskkgkk
	 */
	public Long getZenTotkskkgkk() {
		return zenTotkskkgkk;
	}
	/**
	 * @param zenTotkskkgkk the zenTotkskkgkk to set
	 */
	public void setZenTotkskkgkk(Long zenTotkskkgkk) {
		this.zenTotkskkgkk = zenTotkskkgkk;
	}
	/**
	 * @return the zenToniniskkgkk
	 */
	public Long getZenToniniskkgkk() {
		return zenToniniskkgkk;
	}
	/**
	 * @param zenToniniskkgkk the zenToniniskkgkk to set
	 */
	public void setZenToniniskkgkk(Long zenToniniskkgkk) {
		this.zenToniniskkgkk = zenToniniskkgkk;
	}
	/**
	 * @return the toToftskkgkk
	 */
	public Long getToToftskkgkk() {
		return toToftskkgkk;
	}
	/**
	 * @param toToftskkgkk the toToftskkgkk to set
	 */
	public void setToToftskkgkk(Long toToftskkgkk) {
		this.toToftskkgkk = toToftskkgkk;
	}
	/**
	 * @return the toTozkskkgkk
	 */
	public Long getToTozkskkgkk() {
		return toTozkskkgkk;
	}
	/**
	 * @param toTozkskkgkk the toTozkskkgkk to set
	 */
	public void setToTozkskkgkk(Long toTozkskkgkk) {
		this.toTozkskkgkk = toTozkskkgkk;
	}
	/**
	 * @return the toTotkskkgkk
	 */
	public Long getToTotkskkgkk() {
		return toTotkskkgkk;
	}
	/**
	 * @param toTotkskkgkk the toTotkskkgkk to set
	 */
	public void setToTotkskkgkk(Long toTotkskkgkk) {
		this.toTotkskkgkk = toTotkskkgkk;
	}
	/**
	 * @return the toToniniskkgkk
	 */
	public Long getToToniniskkgkk() {
		return toToniniskkgkk;
	}
	/**
	 * @param toToniniskkgkk the toToniniskkgkk to set
	 */
	public void setToToniniskkgkk(Long toToniniskkgkk) {
		this.toToniniskkgkk = toToniniskkgkk;
	}
	/**
	 * @return the toBokak
	 */
	public Long getToBokak() {
		return toBokak;
	}
	/**
	 * @param toBokak the toBokak to set
	 */
	public void setToBokak(Long toBokak) {
		this.toBokak = toBokak;
	}
	/**
	 * @return the zogenbokak
	 */
	public Long getZogenbokak() {
		return zogenbokak;
	}
	/**
	 * @param zogenbokak the zogenbokak to set
	 */
	public void setZogenbokak(Long zogenbokak) {
		this.zogenbokak = zogenbokak;
	}
	/**
	 * @return the zkskkritsuk
	 */
	public Double getZkskkritsuk() {
		return zkskkritsuk;
	}
	/**
	 * @param zkskkritsuk the zkskkritsuk to set
	 */
	public void setZkskkritsuk(Double zkskkritsuk) {
		this.zkskkritsuk = zkskkritsuk;
	}
	/**
	 * @return the yukyustymk
	 */
	public String getYukyustymk() {
		return yukyustymk;
	}
	/**
	 * @param yukyustymk the yukyustymk to set
	 */
	public void setYukyustymk(String yukyustymk) {
		this.yukyustymk = yukyustymk;
	}
	/**
	 * @return the yukyufkymk
	 */
	public String getYukyufkymk() {
		return yukyufkymk;
	}
	/**
	 * @param yukyufkymk the yukyufkymk to set
	 */
	public void setYukyufkymk(String yukyufkymk) {
		this.yukyufkymk = yukyufkymk;
	}
	/**
	 * @return the shonencalckbnk
	 */
	public String getShonencalckbnk() {
		return shonencalckbnk;
	}
	/**
	 * @param shonencalckbnk the shonencalckbnk to set
	 */
	public void setShonencalckbnk(String shonencalckbnk) {
		this.shonencalckbnk = shonencalckbnk;
	}
	/**
	 * @return the skkkanryoflgk
	 */
	public String getSkkkanryoflgk() {
		return skkkanryoflgk;
	}
	/**
	 * @param skkkanryoflgk the skkkanryoflgk to set
	 */
	public void setSkkkanryoflgk(String skkkanryoflgk) {
		this.skkkanryoflgk = skkkanryoflgk;
	}
	/**
	 * @return the skkkirikaeyyk
	 */
	public String getSkkkirikaeyyk() {
		return skkkirikaeyyk;
	}
	/**
	 * @param skkkirikaeyyk the skkkirikaeyyk to set
	 */
	public void setSkkkirikaeyyk(String skkkirikaeyyk) {
		this.skkkirikaeyyk = skkkirikaeyyk;
	}
	/**
	 * @return the zanzonskkstyyk
	 */
	public String getZanzonskkstyyk() {
		return zanzonskkstyyk;
	}
	/**
	 * @param zanzonskkstyyk the zanzonskkstyyk to set
	 */
	public void setZanzonskkstyyk(String zanzonskkstyyk) {
		this.zanzonskkstyyk = zanzonskkstyyk;
	}
	/**
	 * @return the zanzonskkmmsuk
	 */
	public Integer getZanzonskkmmsuk() {
		return zanzonskkmmsuk;
	}
	/**
	 * @param zanzonskkmmsuk the zanzonskkmmsuk to set
	 */
	public void setZanzonskkmmsuk(Integer zanzonskkmmsuk) {
		this.zanzonskkmmsuk = zanzonskkmmsuk;
	}
	/**
	 * @return the stkgkz
	 */
	public Long getStkgkz() {
		return stkgkz;
	}
	/**
	 * @param stkgkz the stkgkz to set
	 */
	public void setStkgkz(Long stkgkz) {
		this.stkgkz = stkgkz;
	}
	/**
	 * @return the askstkgkz
	 */
	public Long getAskstkgkz() {
		return askstkgkz;
	}
	/**
	 * @param askstkgkz the askstkgkz to set
	 */
	public void setAskstkgkz(Long askstkgkz) {
		this.askstkgkz = askstkgkz;
	}
	/**
	 * @return the skkhoz
	 */
	public String getSkkhoz() {
		return skkhoz;
	}
	/**
	 * @param skkhoz the skkhoz to set
	 */
	public void setSkkhoz(String skkhoz) {
		this.skkhoz = skkhoz;
	}
	/**
	 * @return the tainenz
	 */
	public Integer getTainenz() {
		return tainenz;
	}
	/**
	 * @param tainenz the tainenz to set
	 */
	public void setTainenz(Integer tainenz) {
		this.tainenz = tainenz;
	}
	/**
	 * @return the skkritsuz
	 */
	public Double getSkkritsuz() {
		return skkritsuz;
	}
	/**
	 * @param skkritsuz the skkritsuz to set
	 */
	public void setSkkritsuz(Double skkritsuz) {
		this.skkritsuz = skkritsuz;
	}
	/**
	 * @return the skkmmsuz
	 */
	public Integer getSkkmmsuz() {
		return skkmmsuz;
	}
	/**
	 * @param skkmmsuz the skkmmsuz to set
	 */
	public void setSkkmmsuz(Integer skkmmsuz) {
		this.skkmmsuz = skkmmsuz;
	}
	/**
	 * @return the zanzonmmsuz
	 */
	public Integer getZanzonmmsuz() {
		return zanzonmmsuz;
	}
	/**
	 * @param zanzonmmsuz the zanzonmmsuz to set
	 */
	public void setZanzonmmsuz(Integer zanzonmmsuz) {
		this.zanzonmmsuz = zanzonmmsuz;
	}
	/**
	 * @return the ksbokaz
	 */
	public Long getKsbokaz() {
		return ksbokaz;
	}
	/**
	 * @param ksbokaz the ksbokaz to set
	 */
	public void setKsbokaz(Long ksbokaz) {
		this.ksbokaz = ksbokaz;
	}
	/**
	 * @return the skkcalckisogkz
	 */
	public Long getSkkcalckisogkz() {
		return skkcalckisogkz;
	}
	/**
	 * @param skkcalckisogkz the skkcalckisogkz to set
	 */
	public void setSkkcalckisogkz(Long skkcalckisogkz) {
		this.skkcalckisogkz = skkcalckisogkz;
	}
	/**
	 * @return the zanzonritsuz
	 */
	public Double getZanzonritsuz() {
		return zanzonritsuz;
	}
	/**
	 * @param zanzonritsuz the zanzonritsuz to set
	 */
	public void setZanzonritsuz(Double zanzonritsuz) {
		this.zanzonritsuz = zanzonritsuz;
	}
	/**
	 * @return the zanzongkz
	 */
	public Long getZanzongkz() {
		return zanzongkz;
	}
	/**
	 * @param zanzongkz the zanzongkz to set
	 */
	public void setZanzongkz(Long zanzongkz) {
		this.zanzongkz = zanzongkz;
	}
	/**
	 * @return the ksftskkruigkz
	 */
	public Long getKsftskkruigkz() {
		return ksftskkruigkz;
	}
	/**
	 * @param ksftskkruigkz the ksftskkruigkz to set
	 */
	public void setKsftskkruigkz(Long ksftskkruigkz) {
		this.ksftskkruigkz = ksftskkruigkz;
	}
	/**
	 * @return the kszkskkruigkz
	 */
	public Long getKszkskkruigkz() {
		return kszkskkruigkz;
	}
	/**
	 * @param kszkskkruigkz the kszkskkruigkz to set
	 */
	public void setKszkskkruigkz(Long kszkskkruigkz) {
		this.kszkskkruigkz = kszkskkruigkz;
	}
	/**
	 * @return the kstkskkruigkz
	 */
	public Long getKstkskkruigkz() {
		return kstkskkruigkz;
	}
	/**
	 * @param kstkskkruigkz the kstkskkruigkz to set
	 */
	public void setKstkskkruigkz(Long kstkskkruigkz) {
		this.kstkskkruigkz = kstkskkruigkz;
	}
	/**
	 * @return the zenToftskkgkz
	 */
	public Long getZenToftskkgkz() {
		return zenToftskkgkz;
	}
	/**
	 * @param zenToftskkgkz the zenToftskkgkz to set
	 */
	public void setZenToftskkgkz(Long zenToftskkgkz) {
		this.zenToftskkgkz = zenToftskkgkz;
	}
	/**
	 * @return the zenTozkskkgkz
	 */
	public Long getZenTozkskkgkz() {
		return zenTozkskkgkz;
	}
	/**
	 * @param zenTozkskkgkz the zenTozkskkgkz to set
	 */
	public void setZenTozkskkgkz(Long zenTozkskkgkz) {
		this.zenTozkskkgkz = zenTozkskkgkz;
	}
	/**
	 * @return the zenTotkskkgkz
	 */
	public Long getZenTotkskkgkz() {
		return zenTotkskkgkz;
	}
	/**
	 * @param zenTotkskkgkz the zenTotkskkgkz to set
	 */
	public void setZenTotkskkgkz(Long zenTotkskkgkz) {
		this.zenTotkskkgkz = zenTotkskkgkz;
	}
	/**
	 * @return the zenToniniskkgkz
	 */
	public Long getZenToniniskkgkz() {
		return zenToniniskkgkz;
	}
	/**
	 * @param zenToniniskkgkz the zenToniniskkgkz to set
	 */
	public void setZenToniniskkgkz(Long zenToniniskkgkz) {
		this.zenToniniskkgkz = zenToniniskkgkz;
	}
	/**
	 * @return the toToftskkgkz
	 */
	public Long getToToftskkgkz() {
		return toToftskkgkz;
	}
	/**
	 * @param toToftskkgkz the toToftskkgkz to set
	 */
	public void setToToftskkgkz(Long toToftskkgkz) {
		this.toToftskkgkz = toToftskkgkz;
	}
	/**
	 * @return the toTozkskkgkz
	 */
	public Long getToTozkskkgkz() {
		return toTozkskkgkz;
	}
	/**
	 * @param toTozkskkgkz the toTozkskkgkz to set
	 */
	public void setToTozkskkgkz(Long toTozkskkgkz) {
		this.toTozkskkgkz = toTozkskkgkz;
	}
	/**
	 * @return the toTotkskkgkz
	 */
	public Long getToTotkskkgkz() {
		return toTotkskkgkz;
	}
	/**
	 * @param toTotkskkgkz the toTotkskkgkz to set
	 */
	public void setToTotkskkgkz(Long toTotkskkgkz) {
		this.toTotkskkgkz = toTotkskkgkz;
	}
	/**
	 * @return the toToniniskkgkz
	 */
	public Long getToToniniskkgkz() {
		return toToniniskkgkz;
	}
	/**
	 * @param toToniniskkgkz the toToniniskkgkz to set
	 */
	public void setToToniniskkgkz(Long toToniniskkgkz) {
		this.toToniniskkgkz = toToniniskkgkz;
	}
	/**
	 * @return the toBokaz
	 */
	public Long getToBokaz() {
		return toBokaz;
	}
	/**
	 * @param toBokaz the toBokaz to set
	 */
	public void setToBokaz(Long toBokaz) {
		this.toBokaz = toBokaz;
	}
	/**
	 * @return the zogenbokaz
	 */
	public Long getZogenbokaz() {
		return zogenbokaz;
	}
	/**
	 * @param zogenbokaz the zogenbokaz to set
	 */
	public void setZogenbokaz(Long zogenbokaz) {
		this.zogenbokaz = zogenbokaz;
	}
	/**
	 * @return the zkskkritsuz
	 */
	public Double getZkskkritsuz() {
		return zkskkritsuz;
	}
	/**
	 * @param zkskkritsuz the zkskkritsuz to set
	 */
	public void setZkskkritsuz(Double zkskkritsuz) {
		this.zkskkritsuz = zkskkritsuz;
	}
	/**
	 * @return the yukyustymz
	 */
	public String getYukyustymz() {
		return yukyustymz;
	}
	/**
	 * @param yukyustymz the yukyustymz to set
	 */
	public void setYukyustymz(String yukyustymz) {
		this.yukyustymz = yukyustymz;
	}
	/**
	 * @return the yukyufkymz
	 */
	public String getYukyufkymz() {
		return yukyufkymz;
	}
	/**
	 * @param yukyufkymz the yukyufkymz to set
	 */
	public void setYukyufkymz(String yukyufkymz) {
		this.yukyufkymz = yukyufkymz;
	}
	/**
	 * @return the shonencalckbnz
	 */
	public String getShonencalckbnz() {
		return shonencalckbnz;
	}
	/**
	 * @param shonencalckbnz the shonencalckbnz to set
	 */
	public void setShonencalckbnz(String shonencalckbnz) {
		this.shonencalckbnz = shonencalckbnz;
	}
	/**
	 * @return the skkkanryoflgz
	 */
	public String getSkkkanryoflgz() {
		return skkkanryoflgz;
	}
	/**
	 * @param skkkanryoflgz the skkkanryoflgz to set
	 */
	public void setSkkkanryoflgz(String skkkanryoflgz) {
		this.skkkanryoflgz = skkkanryoflgz;
	}
	/**
	 * @return the skkkirikaeyyz
	 */
	public String getSkkkirikaeyyz() {
		return skkkirikaeyyz;
	}
	/**
	 * @param skkkirikaeyyz the skkkirikaeyyz to set
	 */
	public void setSkkkirikaeyyz(String skkkirikaeyyz) {
		this.skkkirikaeyyz = skkkirikaeyyz;
	}
	/**
	 * @return the zanzonskkstyyz
	 */
	public String getZanzonskkstyyz() {
		return zanzonskkstyyz;
	}
	/**
	 * @param zanzonskkstyyz the zanzonskkstyyz to set
	 */
	public void setZanzonskkstyyz(String zanzonskkstyyz) {
		this.zanzonskkstyyz = zanzonskkstyyz;
	}
	/**
	 * @return the zanzonskkmmsuz
	 */
	public Integer getZanzonskkmmsuz() {
		return zanzonskkmmsuz;
	}
	/**
	 * @param zanzonskkmmsuz the zanzonskkmmsuz to set
	 */
	public void setZanzonskkmmsuz(Integer zanzonskkmmsuz) {
		this.zanzonskkmmsuz = zanzonskkmmsuz;
	}
	/**
	 * @return the stkgkL3
	 */
	public Long getStkgkL3() {
		return stkgkL3;
	}
	/**
	 * @param stkgkL3 the stkgkL3 to set
	 */
	public void setStkgkL3(Long stkgkL3) {
		this.stkgkL3 = stkgkL3;
	}
	/**
	 * @return the askstkgkL3
	 */
	public Long getAskstkgkL3() {
		return askstkgkL3;
	}
	/**
	 * @param askstkgkL3 the askstkgkL3 to set
	 */
	public void setAskstkgkL3(Long askstkgkL3) {
		this.askstkgkL3 = askstkgkL3;
	}
	/**
	 * @return the skkhoL3
	 */
	public String getSkkhoL3() {
		return skkhoL3;
	}
	/**
	 * @param skkhoL3 the skkhoL3 to set
	 */
	public void setSkkhoL3(String skkhoL3) {
		this.skkhoL3 = skkhoL3;
	}
	/**
	 * @return the tainenL3
	 */
	public Integer getTainenL3() {
		return tainenL3;
	}
	/**
	 * @param tainenL3 the tainenL3 to set
	 */
	public void setTainenL3(Integer tainenL3) {
		this.tainenL3 = tainenL3;
	}
	/**
	 * @return the skkritsuL3
	 */
	public Double getSkkritsuL3() {
		return skkritsuL3;
	}
	/**
	 * @param skkritsuL3 the skkritsuL3 to set
	 */
	public void setSkkritsuL3(Double skkritsuL3) {
		this.skkritsuL3 = skkritsuL3;
	}
	/**
	 * @return the skkmmsuL3
	 */
	public Integer getSkkmmsuL3() {
		return skkmmsuL3;
	}
	/**
	 * @param skkmmsuL3 the skkmmsuL3 to set
	 */
	public void setSkkmmsuL3(Integer skkmmsuL3) {
		this.skkmmsuL3 = skkmmsuL3;
	}
	/**
	 * @return the zanzonmmsuL3
	 */
	public Integer getZanzonmmsuL3() {
		return zanzonmmsuL3;
	}
	/**
	 * @param zanzonmmsuL3 the zanzonmmsuL3 to set
	 */
	public void setZanzonmmsuL3(Integer zanzonmmsuL3) {
		this.zanzonmmsuL3 = zanzonmmsuL3;
	}
	/**
	 * @return the ksbokaL3
	 */
	public Long getKsbokaL3() {
		return ksbokaL3;
	}
	/**
	 * @param ksbokaL3 the ksbokaL3 to set
	 */
	public void setKsbokaL3(Long ksbokaL3) {
		this.ksbokaL3 = ksbokaL3;
	}
	/**
	 * @return the skkcalckisogkL3
	 */
	public Long getSkkcalckisogkL3() {
		return skkcalckisogkL3;
	}
	/**
	 * @param skkcalckisogkL3 the skkcalckisogkL3 to set
	 */
	public void setSkkcalckisogkL3(Long skkcalckisogkL3) {
		this.skkcalckisogkL3 = skkcalckisogkL3;
	}
	/**
	 * @return the zanzonritsuL3
	 */
	public Double getZanzonritsuL3() {
		return zanzonritsuL3;
	}
	/**
	 * @param zanzonritsuL3 the zanzonritsuL3 to set
	 */
	public void setZanzonritsuL3(Double zanzonritsuL3) {
		this.zanzonritsuL3 = zanzonritsuL3;
	}
	/**
	 * @return the zanzongkL3
	 */
	public Long getZanzongkL3() {
		return zanzongkL3;
	}
	/**
	 * @param zanzongkL3 the zanzongkL3 to set
	 */
	public void setZanzongkL3(Long zanzongkL3) {
		this.zanzongkL3 = zanzongkL3;
	}
	/**
	 * @return the ksftskkruigkL3
	 */
	public Long getKsftskkruigkL3() {
		return ksftskkruigkL3;
	}
	/**
	 * @param ksftskkruigkL3 the ksftskkruigkL3 to set
	 */
	public void setKsftskkruigkL3(Long ksftskkruigkL3) {
		this.ksftskkruigkL3 = ksftskkruigkL3;
	}
	/**
	 * @return the kszkskkruigkL3
	 */
	public Long getKszkskkruigkL3() {
		return kszkskkruigkL3;
	}
	/**
	 * @param kszkskkruigkL3 the kszkskkruigkL3 to set
	 */
	public void setKszkskkruigkL3(Long kszkskkruigkL3) {
		this.kszkskkruigkL3 = kszkskkruigkL3;
	}
	/**
	 * @return the kstkskkruigkL3
	 */
	public Long getKstkskkruigkL3() {
		return kstkskkruigkL3;
	}
	/**
	 * @param kstkskkruigkL3 the kstkskkruigkL3 to set
	 */
	public void setKstkskkruigkL3(Long kstkskkruigkL3) {
		this.kstkskkruigkL3 = kstkskkruigkL3;
	}
	/**
	 * @return the zenToftskkgkL3
	 */
	public Long getZenToftskkgkL3() {
		return zenToftskkgkL3;
	}
	/**
	 * @param zenToftskkgkL3 the zenToftskkgkL3 to set
	 */
	public void setZenToftskkgkL3(Long zenToftskkgkL3) {
		this.zenToftskkgkL3 = zenToftskkgkL3;
	}
	/**
	 * @return the zenTozkskkgkL3
	 */
	public Long getZenTozkskkgkL3() {
		return zenTozkskkgkL3;
	}
	/**
	 * @param zenTozkskkgkL3 the zenTozkskkgkL3 to set
	 */
	public void setZenTozkskkgkL3(Long zenTozkskkgkL3) {
		this.zenTozkskkgkL3 = zenTozkskkgkL3;
	}
	/**
	 * @return the zenTotkskkgkL3
	 */
	public Long getZenTotkskkgkL3() {
		return zenTotkskkgkL3;
	}
	/**
	 * @param zenTotkskkgkL3 the zenTotkskkgkL3 to set
	 */
	public void setZenTotkskkgkL3(Long zenTotkskkgkL3) {
		this.zenTotkskkgkL3 = zenTotkskkgkL3;
	}
	/**
	 * @return the zenToniniskkgkL3
	 */
	public Long getZenToniniskkgkL3() {
		return zenToniniskkgkL3;
	}
	/**
	 * @param zenToniniskkgkL3 the zenToniniskkgkL3 to set
	 */
	public void setZenToniniskkgkL3(Long zenToniniskkgkL3) {
		this.zenToniniskkgkL3 = zenToniniskkgkL3;
	}
	/**
	 * @return the toToftskkgkL3
	 */
	public Long getToToftskkgkL3() {
		return toToftskkgkL3;
	}
	/**
	 * @param toToftskkgkL3 the toToftskkgkL3 to set
	 */
	public void setToToftskkgkL3(Long toToftskkgkL3) {
		this.toToftskkgkL3 = toToftskkgkL3;
	}
	/**
	 * @return the toTozkskkgkL3
	 */
	public Long getToTozkskkgkL3() {
		return toTozkskkgkL3;
	}
	/**
	 * @param toTozkskkgkL3 the toTozkskkgkL3 to set
	 */
	public void setToTozkskkgkL3(Long toTozkskkgkL3) {
		this.toTozkskkgkL3 = toTozkskkgkL3;
	}
	/**
	 * @return the toTotkskkgkL3
	 */
	public Long getToTotkskkgkL3() {
		return toTotkskkgkL3;
	}
	/**
	 * @param toTotkskkgkL3 the toTotkskkgkL3 to set
	 */
	public void setToTotkskkgkL3(Long toTotkskkgkL3) {
		this.toTotkskkgkL3 = toTotkskkgkL3;
	}
	/**
	 * @return the toToniniskkgkL3
	 */
	public Long getToToniniskkgkL3() {
		return toToniniskkgkL3;
	}
	/**
	 * @param toToniniskkgkL3 the toToniniskkgkL3 to set
	 */
	public void setToToniniskkgkL3(Long toToniniskkgkL3) {
		this.toToniniskkgkL3 = toToniniskkgkL3;
	}
	/**
	 * @return the toBokaL3
	 */
	public Long getToBokaL3() {
		return toBokaL3;
	}
	/**
	 * @param toBokaL3 the toBokaL3 to set
	 */
	public void setToBokaL3(Long toBokaL3) {
		this.toBokaL3 = toBokaL3;
	}
	/**
	 * @return the zogenbokaL3
	 */
	public Long getZogenbokaL3() {
		return zogenbokaL3;
	}
	/**
	 * @param zogenbokaL3 the zogenbokaL3 to set
	 */
	public void setZogenbokaL3(Long zogenbokaL3) {
		this.zogenbokaL3 = zogenbokaL3;
	}
	/**
	 * @return the zkskkritsuL3
	 */
	public Double getZkskkritsuL3() {
		return zkskkritsuL3;
	}
	/**
	 * @param zkskkritsuL3 the zkskkritsuL3 to set
	 */
	public void setZkskkritsuL3(Double zkskkritsuL3) {
		this.zkskkritsuL3 = zkskkritsuL3;
	}
	/**
	 * @return the yukyustymL3
	 */
	public String getYukyustymL3() {
		return yukyustymL3;
	}
	/**
	 * @param yukyustymL3 the yukyustymL3 to set
	 */
	public void setYukyustymL3(String yukyustymL3) {
		this.yukyustymL3 = yukyustymL3;
	}
	/**
	 * @return the yukyufkymL3
	 */
	public String getYukyufkymL3() {
		return yukyufkymL3;
	}
	/**
	 * @param yukyufkymL3 the yukyufkymL3 to set
	 */
	public void setYukyufkymL3(String yukyufkymL3) {
		this.yukyufkymL3 = yukyufkymL3;
	}
	/**
	 * @return the shonencalckbnL3
	 */
	public String getShonencalckbnL3() {
		return shonencalckbnL3;
	}
	/**
	 * @param shonencalckbnL3 the shonencalckbnL3 to set
	 */
	public void setShonencalckbnL3(String shonencalckbnL3) {
		this.shonencalckbnL3 = shonencalckbnL3;
	}
	/**
	 * @return the skkkanryoflgL3
	 */
	public String getSkkkanryoflgL3() {
		return skkkanryoflgL3;
	}
	/**
	 * @param skkkanryoflgL3 the skkkanryoflgL3 to set
	 */
	public void setSkkkanryoflgL3(String skkkanryoflgL3) {
		this.skkkanryoflgL3 = skkkanryoflgL3;
	}
	/**
	 * @return the skkkirikaeyyL3
	 */
	public String getSkkkirikaeyyL3() {
		return skkkirikaeyyL3;
	}
	/**
	 * @param skkkirikaeyyL3 the skkkirikaeyyL3 to set
	 */
	public void setSkkkirikaeyyL3(String skkkirikaeyyL3) {
		this.skkkirikaeyyL3 = skkkirikaeyyL3;
	}
	/**
	 * @return the zanzonskkstyyL3
	 */
	public String getZanzonskkstyyL3() {
		return zanzonskkstyyL3;
	}
	/**
	 * @param zanzonskkstyyL3 the zanzonskkstyyL3 to set
	 */
	public void setZanzonskkstyyL3(String zanzonskkstyyL3) {
		this.zanzonskkstyyL3 = zanzonskkstyyL3;
	}
	/**
	 * @return the zanzonskkmmsuL3
	 */
	public Integer getZanzonskkmmsuL3() {
		return zanzonskkmmsuL3;
	}
	/**
	 * @param zanzonskkmmsuL3 the zanzonskkmmsuL3 to set
	 */
	public void setZanzonskkmmsuL3(Integer zanzonskkmmsuL3) {
		this.zanzonskkmmsuL3 = zanzonskkmmsuL3;
	}
	/**
	 * @return the stkgkL4
	 */
	public Long getStkgkL4() {
		return stkgkL4;
	}
	/**
	 * @param stkgkL4 the stkgkL4 to set
	 */
	public void setStkgkL4(Long stkgkL4) {
		this.stkgkL4 = stkgkL4;
	}
	/**
	 * @return the askstkgkL4
	 */
	public Long getAskstkgkL4() {
		return askstkgkL4;
	}
	/**
	 * @param askstkgkL4 the askstkgkL4 to set
	 */
	public void setAskstkgkL4(Long askstkgkL4) {
		this.askstkgkL4 = askstkgkL4;
	}
	/**
	 * @return the skkhoL4
	 */
	public String getSkkhoL4() {
		return skkhoL4;
	}
	/**
	 * @param skkhoL4 the skkhoL4 to set
	 */
	public void setSkkhoL4(String skkhoL4) {
		this.skkhoL4 = skkhoL4;
	}
	/**
	 * @return the tainenL4
	 */
	public Integer getTainenL4() {
		return tainenL4;
	}
	/**
	 * @param tainenL4 the tainenL4 to set
	 */
	public void setTainenL4(Integer tainenL4) {
		this.tainenL4 = tainenL4;
	}
	/**
	 * @return the skkritsuL4
	 */
	public Double getSkkritsuL4() {
		return skkritsuL4;
	}
	/**
	 * @param skkritsuL4 the skkritsuL4 to set
	 */
	public void setSkkritsuL4(Double skkritsuL4) {
		this.skkritsuL4 = skkritsuL4;
	}
	/**
	 * @return the skkmmsuL4
	 */
	public Integer getSkkmmsuL4() {
		return skkmmsuL4;
	}
	/**
	 * @param skkmmsuL4 the skkmmsuL4 to set
	 */
	public void setSkkmmsuL4(Integer skkmmsuL4) {
		this.skkmmsuL4 = skkmmsuL4;
	}
	/**
	 * @return the zanzonmmsuL4
	 */
	public Integer getZanzonmmsuL4() {
		return zanzonmmsuL4;
	}
	/**
	 * @param zanzonmmsuL4 the zanzonmmsuL4 to set
	 */
	public void setZanzonmmsuL4(Integer zanzonmmsuL4) {
		this.zanzonmmsuL4 = zanzonmmsuL4;
	}
	/**
	 * @return the ksbokaL4
	 */
	public Long getKsbokaL4() {
		return ksbokaL4;
	}
	/**
	 * @param ksbokaL4 the ksbokaL4 to set
	 */
	public void setKsbokaL4(Long ksbokaL4) {
		this.ksbokaL4 = ksbokaL4;
	}
	/**
	 * @return the skkcalckisogkL4
	 */
	public Long getSkkcalckisogkL4() {
		return skkcalckisogkL4;
	}
	/**
	 * @param skkcalckisogkL4 the skkcalckisogkL4 to set
	 */
	public void setSkkcalckisogkL4(Long skkcalckisogkL4) {
		this.skkcalckisogkL4 = skkcalckisogkL4;
	}
	/**
	 * @return the zanzonritsuL4
	 */
	public Double getZanzonritsuL4() {
		return zanzonritsuL4;
	}
	/**
	 * @param zanzonritsuL4 the zanzonritsuL4 to set
	 */
	public void setZanzonritsuL4(Double zanzonritsuL4) {
		this.zanzonritsuL4 = zanzonritsuL4;
	}
	/**
	 * @return the zanzongkL4
	 */
	public Long getZanzongkL4() {
		return zanzongkL4;
	}
	/**
	 * @param zanzongkL4 the zanzongkL4 to set
	 */
	public void setZanzongkL4(Long zanzongkL4) {
		this.zanzongkL4 = zanzongkL4;
	}
	/**
	 * @return the ksftskkruigkL4
	 */
	public Long getKsftskkruigkL4() {
		return ksftskkruigkL4;
	}
	/**
	 * @param ksftskkruigkL4 the ksftskkruigkL4 to set
	 */
	public void setKsftskkruigkL4(Long ksftskkruigkL4) {
		this.ksftskkruigkL4 = ksftskkruigkL4;
	}
	/**
	 * @return the kszkskkruigkL4
	 */
	public Long getKszkskkruigkL4() {
		return kszkskkruigkL4;
	}
	/**
	 * @param kszkskkruigkL4 the kszkskkruigkL4 to set
	 */
	public void setKszkskkruigkL4(Long kszkskkruigkL4) {
		this.kszkskkruigkL4 = kszkskkruigkL4;
	}
	/**
	 * @return the kstkskkruigkL4
	 */
	public Long getKstkskkruigkL4() {
		return kstkskkruigkL4;
	}
	/**
	 * @param kstkskkruigkL4 the kstkskkruigkL4 to set
	 */
	public void setKstkskkruigkL4(Long kstkskkruigkL4) {
		this.kstkskkruigkL4 = kstkskkruigkL4;
	}
	/**
	 * @return the zenToftskkgkL4
	 */
	public Long getZenToftskkgkL4() {
		return zenToftskkgkL4;
	}
	/**
	 * @param zenToftskkgkL4 the zenToftskkgkL4 to set
	 */
	public void setZenToftskkgkL4(Long zenToftskkgkL4) {
		this.zenToftskkgkL4 = zenToftskkgkL4;
	}
	/**
	 * @return the zenTozkskkgkL4
	 */
	public Long getZenTozkskkgkL4() {
		return zenTozkskkgkL4;
	}
	/**
	 * @param zenTozkskkgkL4 the zenTozkskkgkL4 to set
	 */
	public void setZenTozkskkgkL4(Long zenTozkskkgkL4) {
		this.zenTozkskkgkL4 = zenTozkskkgkL4;
	}
	/**
	 * @return the zenTotkskkgkL4
	 */
	public Long getZenTotkskkgkL4() {
		return zenTotkskkgkL4;
	}
	/**
	 * @param zenTotkskkgkL4 the zenTotkskkgkL4 to set
	 */
	public void setZenTotkskkgkL4(Long zenTotkskkgkL4) {
		this.zenTotkskkgkL4 = zenTotkskkgkL4;
	}
	/**
	 * @return the zenToniniskkgkL4
	 */
	public Long getZenToniniskkgkL4() {
		return zenToniniskkgkL4;
	}
	/**
	 * @param zenToniniskkgkL4 the zenToniniskkgkL4 to set
	 */
	public void setZenToniniskkgkL4(Long zenToniniskkgkL4) {
		this.zenToniniskkgkL4 = zenToniniskkgkL4;
	}
	/**
	 * @return the toToftskkgkL4
	 */
	public Long getToToftskkgkL4() {
		return toToftskkgkL4;
	}
	/**
	 * @param toToftskkgkL4 the toToftskkgkL4 to set
	 */
	public void setToToftskkgkL4(Long toToftskkgkL4) {
		this.toToftskkgkL4 = toToftskkgkL4;
	}
	/**
	 * @return the toTozkskkgkL4
	 */
	public Long getToTozkskkgkL4() {
		return toTozkskkgkL4;
	}
	/**
	 * @param toTozkskkgkL4 the toTozkskkgkL4 to set
	 */
	public void setToTozkskkgkL4(Long toTozkskkgkL4) {
		this.toTozkskkgkL4 = toTozkskkgkL4;
	}
	/**
	 * @return the toTotkskkgkL4
	 */
	public Long getToTotkskkgkL4() {
		return toTotkskkgkL4;
	}
	/**
	 * @param toTotkskkgkL4 the toTotkskkgkL4 to set
	 */
	public void setToTotkskkgkL4(Long toTotkskkgkL4) {
		this.toTotkskkgkL4 = toTotkskkgkL4;
	}
	/**
	 * @return the toToniniskkgkL4
	 */
	public Long getToToniniskkgkL4() {
		return toToniniskkgkL4;
	}
	/**
	 * @param toToniniskkgkL4 the toToniniskkgkL4 to set
	 */
	public void setToToniniskkgkL4(Long toToniniskkgkL4) {
		this.toToniniskkgkL4 = toToniniskkgkL4;
	}
	/**
	 * @return the toBokaL4
	 */
	public Long getToBokaL4() {
		return toBokaL4;
	}
	/**
	 * @param toBokaL4 the toBokaL4 to set
	 */
	public void setToBokaL4(Long toBokaL4) {
		this.toBokaL4 = toBokaL4;
	}
	/**
	 * @return the zogenbokaL4
	 */
	public Long getZogenbokaL4() {
		return zogenbokaL4;
	}
	/**
	 * @param zogenbokaL4 the zogenbokaL4 to set
	 */
	public void setZogenbokaL4(Long zogenbokaL4) {
		this.zogenbokaL4 = zogenbokaL4;
	}
	/**
	 * @return the zkskkritsuL4
	 */
	public Double getZkskkritsuL4() {
		return zkskkritsuL4;
	}
	/**
	 * @param zkskkritsuL4 the zkskkritsuL4 to set
	 */
	public void setZkskkritsuL4(Double zkskkritsuL4) {
		this.zkskkritsuL4 = zkskkritsuL4;
	}
	/**
	 * @return the yukyustymL4
	 */
	public String getYukyustymL4() {
		return yukyustymL4;
	}
	/**
	 * @param yukyustymL4 the yukyustymL4 to set
	 */
	public void setYukyustymL4(String yukyustymL4) {
		this.yukyustymL4 = yukyustymL4;
	}
	/**
	 * @return the yukyufkymL4
	 */
	public String getYukyufkymL4() {
		return yukyufkymL4;
	}
	/**
	 * @param yukyufkymL4 the yukyufkymL4 to set
	 */
	public void setYukyufkymL4(String yukyufkymL4) {
		this.yukyufkymL4 = yukyufkymL4;
	}
	/**
	 * @return the shonencalckbnL4
	 */
	public String getShonencalckbnL4() {
		return shonencalckbnL4;
	}
	/**
	 * @param shonencalckbnL4 the shonencalckbnL4 to set
	 */
	public void setShonencalckbnL4(String shonencalckbnL4) {
		this.shonencalckbnL4 = shonencalckbnL4;
	}
	/**
	 * @return the skkkanryoflgL4
	 */
	public String getSkkkanryoflgL4() {
		return skkkanryoflgL4;
	}
	/**
	 * @param skkkanryoflgL4 the skkkanryoflgL4 to set
	 */
	public void setSkkkanryoflgL4(String skkkanryoflgL4) {
		this.skkkanryoflgL4 = skkkanryoflgL4;
	}
	/**
	 * @return the skkkirikaeyyL4
	 */
	public String getSkkkirikaeyyL4() {
		return skkkirikaeyyL4;
	}
	/**
	 * @param skkkirikaeyyL4 the skkkirikaeyyL4 to set
	 */
	public void setSkkkirikaeyyL4(String skkkirikaeyyL4) {
		this.skkkirikaeyyL4 = skkkirikaeyyL4;
	}
	/**
	 * @return the zanzonskkstyyL4
	 */
	public String getZanzonskkstyyL4() {
		return zanzonskkstyyL4;
	}
	/**
	 * @param zanzonskkstyyL4 the zanzonskkstyyL4 to set
	 */
	public void setZanzonskkstyyL4(String zanzonskkstyyL4) {
		this.zanzonskkstyyL4 = zanzonskkstyyL4;
	}
	/**
	 * @return the zanzonskkmmsuL4
	 */
	public Integer getZanzonskkmmsuL4() {
		return zanzonskkmmsuL4;
	}
	/**
	 * @param zanzonskkmmsuL4 the zanzonskkmmsuL4 to set
	 */
	public void setZanzonskkmmsuL4(Integer zanzonskkmmsuL4) {
		this.zanzonskkmmsuL4 = zanzonskkmmsuL4;
	}
	/**
	 * @return the stkgkL5
	 */
	public Long getStkgkL5() {
		return stkgkL5;
	}
	/**
	 * @param stkgkL5 the stkgkL5 to set
	 */
	public void setStkgkL5(Long stkgkL5) {
		this.stkgkL5 = stkgkL5;
	}
	/**
	 * @return the askstkgkL5
	 */
	public Long getAskstkgkL5() {
		return askstkgkL5;
	}
	/**
	 * @param askstkgkL5 the askstkgkL5 to set
	 */
	public void setAskstkgkL5(Long askstkgkL5) {
		this.askstkgkL5 = askstkgkL5;
	}
	/**
	 * @return the skkhoL5
	 */
	public String getSkkhoL5() {
		return skkhoL5;
	}
	/**
	 * @param skkhoL5 the skkhoL5 to set
	 */
	public void setSkkhoL5(String skkhoL5) {
		this.skkhoL5 = skkhoL5;
	}
	/**
	 * @return the tainenL5
	 */
	public Integer getTainenL5() {
		return tainenL5;
	}
	/**
	 * @param tainenL5 the tainenL5 to set
	 */
	public void setTainenL5(Integer tainenL5) {
		this.tainenL5 = tainenL5;
	}
	/**
	 * @return the skkritsuL5
	 */
	public Double getSkkritsuL5() {
		return skkritsuL5;
	}
	/**
	 * @param skkritsuL5 the skkritsuL5 to set
	 */
	public void setSkkritsuL5(Double skkritsuL5) {
		this.skkritsuL5 = skkritsuL5;
	}
	/**
	 * @return the skkmmsuL5
	 */
	public Integer getSkkmmsuL5() {
		return skkmmsuL5;
	}
	/**
	 * @param skkmmsuL5 the skkmmsuL5 to set
	 */
	public void setSkkmmsuL5(Integer skkmmsuL5) {
		this.skkmmsuL5 = skkmmsuL5;
	}
	/**
	 * @return the zanzonmmsuL5
	 */
	public Integer getZanzonmmsuL5() {
		return zanzonmmsuL5;
	}
	/**
	 * @param zanzonmmsuL5 the zanzonmmsuL5 to set
	 */
	public void setZanzonmmsuL5(Integer zanzonmmsuL5) {
		this.zanzonmmsuL5 = zanzonmmsuL5;
	}
	/**
	 * @return the ksbokaL5
	 */
	public Long getKsbokaL5() {
		return ksbokaL5;
	}
	/**
	 * @param ksbokaL5 the ksbokaL5 to set
	 */
	public void setKsbokaL5(Long ksbokaL5) {
		this.ksbokaL5 = ksbokaL5;
	}
	/**
	 * @return the skkcalckisogkL5
	 */
	public Long getSkkcalckisogkL5() {
		return skkcalckisogkL5;
	}
	/**
	 * @param skkcalckisogkL5 the skkcalckisogkL5 to set
	 */
	public void setSkkcalckisogkL5(Long skkcalckisogkL5) {
		this.skkcalckisogkL5 = skkcalckisogkL5;
	}
	/**
	 * @return the zanzonritsuL5
	 */
	public Double getZanzonritsuL5() {
		return zanzonritsuL5;
	}
	/**
	 * @param zanzonritsuL5 the zanzonritsuL5 to set
	 */
	public void setZanzonritsuL5(Double zanzonritsuL5) {
		this.zanzonritsuL5 = zanzonritsuL5;
	}
	/**
	 * @return the zanzongkL5
	 */
	public Long getZanzongkL5() {
		return zanzongkL5;
	}
	/**
	 * @param zanzongkL5 the zanzongkL5 to set
	 */
	public void setZanzongkL5(Long zanzongkL5) {
		this.zanzongkL5 = zanzongkL5;
	}
	/**
	 * @return the ksftskkruigkL5
	 */
	public Long getKsftskkruigkL5() {
		return ksftskkruigkL5;
	}
	/**
	 * @param ksftskkruigkL5 the ksftskkruigkL5 to set
	 */
	public void setKsftskkruigkL5(Long ksftskkruigkL5) {
		this.ksftskkruigkL5 = ksftskkruigkL5;
	}
	/**
	 * @return the kszkskkruigkL5
	 */
	public Long getKszkskkruigkL5() {
		return kszkskkruigkL5;
	}
	/**
	 * @param kszkskkruigkL5 the kszkskkruigkL5 to set
	 */
	public void setKszkskkruigkL5(Long kszkskkruigkL5) {
		this.kszkskkruigkL5 = kszkskkruigkL5;
	}
	/**
	 * @return the kstkskkruigkL5
	 */
	public Long getKstkskkruigkL5() {
		return kstkskkruigkL5;
	}
	/**
	 * @param kstkskkruigkL5 the kstkskkruigkL5 to set
	 */
	public void setKstkskkruigkL5(Long kstkskkruigkL5) {
		this.kstkskkruigkL5 = kstkskkruigkL5;
	}
	/**
	 * @return the zenToftskkgkL5
	 */
	public Long getZenToftskkgkL5() {
		return zenToftskkgkL5;
	}
	/**
	 * @param zenToftskkgkL5 the zenToftskkgkL5 to set
	 */
	public void setZenToftskkgkL5(Long zenToftskkgkL5) {
		this.zenToftskkgkL5 = zenToftskkgkL5;
	}
	/**
	 * @return the zenTozkskkgkL5
	 */
	public Long getZenTozkskkgkL5() {
		return zenTozkskkgkL5;
	}
	/**
	 * @param zenTozkskkgkL5 the zenTozkskkgkL5 to set
	 */
	public void setZenTozkskkgkL5(Long zenTozkskkgkL5) {
		this.zenTozkskkgkL5 = zenTozkskkgkL5;
	}
	/**
	 * @return the zenTotkskkgkL5
	 */
	public Long getZenTotkskkgkL5() {
		return zenTotkskkgkL5;
	}
	/**
	 * @param zenTotkskkgkL5 the zenTotkskkgkL5 to set
	 */
	public void setZenTotkskkgkL5(Long zenTotkskkgkL5) {
		this.zenTotkskkgkL5 = zenTotkskkgkL5;
	}
	/**
	 * @return the zenToniniskkgkL5
	 */
	public Long getZenToniniskkgkL5() {
		return zenToniniskkgkL5;
	}
	/**
	 * @param zenToniniskkgkL5 the zenToniniskkgkL5 to set
	 */
	public void setZenToniniskkgkL5(Long zenToniniskkgkL5) {
		this.zenToniniskkgkL5 = zenToniniskkgkL5;
	}
	/**
	 * @return the toToftskkgkL5
	 */
	public Long getToToftskkgkL5() {
		return toToftskkgkL5;
	}
	/**
	 * @param toToftskkgkL5 the toToftskkgkL5 to set
	 */
	public void setToToftskkgkL5(Long toToftskkgkL5) {
		this.toToftskkgkL5 = toToftskkgkL5;
	}
	/**
	 * @return the toTozkskkgkL5
	 */
	public Long getToTozkskkgkL5() {
		return toTozkskkgkL5;
	}
	/**
	 * @param toTozkskkgkL5 the toTozkskkgkL5 to set
	 */
	public void setToTozkskkgkL5(Long toTozkskkgkL5) {
		this.toTozkskkgkL5 = toTozkskkgkL5;
	}
	/**
	 * @return the toTotkskkgkL5
	 */
	public Long getToTotkskkgkL5() {
		return toTotkskkgkL5;
	}
	/**
	 * @param toTotkskkgkL5 the toTotkskkgkL5 to set
	 */
	public void setToTotkskkgkL5(Long toTotkskkgkL5) {
		this.toTotkskkgkL5 = toTotkskkgkL5;
	}
	/**
	 * @return the toToniniskkgkL5
	 */
	public Long getToToniniskkgkL5() {
		return toToniniskkgkL5;
	}
	/**
	 * @param toToniniskkgkL5 the toToniniskkgkL5 to set
	 */
	public void setToToniniskkgkL5(Long toToniniskkgkL5) {
		this.toToniniskkgkL5 = toToniniskkgkL5;
	}
	/**
	 * @return the toBokaL5
	 */
	public Long getToBokaL5() {
		return toBokaL5;
	}
	/**
	 * @param toBokaL5 the toBokaL5 to set
	 */
	public void setToBokaL5(Long toBokaL5) {
		this.toBokaL5 = toBokaL5;
	}
	/**
	 * @return the zogenbokaL5
	 */
	public Long getZogenbokaL5() {
		return zogenbokaL5;
	}
	/**
	 * @param zogenbokaL5 the zogenbokaL5 to set
	 */
	public void setZogenbokaL5(Long zogenbokaL5) {
		this.zogenbokaL5 = zogenbokaL5;
	}
	/**
	 * @return the zkskkritsuL5
	 */
	public Double getZkskkritsuL5() {
		return zkskkritsuL5;
	}
	/**
	 * @param zkskkritsuL5 the zkskkritsuL5 to set
	 */
	public void setZkskkritsuL5(Double zkskkritsuL5) {
		this.zkskkritsuL5 = zkskkritsuL5;
	}
	/**
	 * @return the yukyustymL5
	 */
	public String getYukyustymL5() {
		return yukyustymL5;
	}
	/**
	 * @param yukyustymL5 the yukyustymL5 to set
	 */
	public void setYukyustymL5(String yukyustymL5) {
		this.yukyustymL5 = yukyustymL5;
	}
	/**
	 * @return the yukyufkymL5
	 */
	public String getYukyufkymL5() {
		return yukyufkymL5;
	}
	/**
	 * @param yukyufkymL5 the yukyufkymL5 to set
	 */
	public void setYukyufkymL5(String yukyufkymL5) {
		this.yukyufkymL5 = yukyufkymL5;
	}
	/**
	 * @return the shonencalckbnL5
	 */
	public String getShonencalckbnL5() {
		return shonencalckbnL5;
	}
	/**
	 * @param shonencalckbnL5 the shonencalckbnL5 to set
	 */
	public void setShonencalckbnL5(String shonencalckbnL5) {
		this.shonencalckbnL5 = shonencalckbnL5;
	}
	/**
	 * @return the skkkanryoflgL5
	 */
	public String getSkkkanryoflgL5() {
		return skkkanryoflgL5;
	}
	/**
	 * @param skkkanryoflgL5 the skkkanryoflgL5 to set
	 */
	public void setSkkkanryoflgL5(String skkkanryoflgL5) {
		this.skkkanryoflgL5 = skkkanryoflgL5;
	}
	/**
	 * @return the skkkirikaeyyL5
	 */
	public String getSkkkirikaeyyL5() {
		return skkkirikaeyyL5;
	}
	/**
	 * @param skkkirikaeyyL5 the skkkirikaeyyL5 to set
	 */
	public void setSkkkirikaeyyL5(String skkkirikaeyyL5) {
		this.skkkirikaeyyL5 = skkkirikaeyyL5;
	}
	/**
	 * @return the zanzonskkstyyL5
	 */
	public String getZanzonskkstyyL5() {
		return zanzonskkstyyL5;
	}
	/**
	 * @param zanzonskkstyyL5 the zanzonskkstyyL5 to set
	 */
	public void setZanzonskkstyyL5(String zanzonskkstyyL5) {
		this.zanzonskkstyyL5 = zanzonskkstyyL5;
	}
	/**
	 * @return the zanzonskkmmsuL5
	 */
	public Integer getZanzonskkmmsuL5() {
		return zanzonskkmmsuL5;
	}
	/**
	 * @param zanzonskkmmsuL5 the zanzonskkmmsuL5 to set
	 */
	public void setZanzonskkmmsuL5(Integer zanzonskkmmsuL5) {
		this.zanzonskkmmsuL5 = zanzonskkmmsuL5;
	}
	/**
	 * @return the stkgkL6
	 */
	public Long getStkgkL6() {
		return stkgkL6;
	}
	/**
	 * @param stkgkL6 the stkgkL6 to set
	 */
	public void setStkgkL6(Long stkgkL6) {
		this.stkgkL6 = stkgkL6;
	}
	/**
	 * @return the askstkgkL6
	 */
	public Long getAskstkgkL6() {
		return askstkgkL6;
	}
	/**
	 * @param askstkgkL6 the askstkgkL6 to set
	 */
	public void setAskstkgkL6(Long askstkgkL6) {
		this.askstkgkL6 = askstkgkL6;
	}
	/**
	 * @return the skkhoL6
	 */
	public String getSkkhoL6() {
		return skkhoL6;
	}
	/**
	 * @param skkhoL6 the skkhoL6 to set
	 */
	public void setSkkhoL6(String skkhoL6) {
		this.skkhoL6 = skkhoL6;
	}
	/**
	 * @return the tainenL6
	 */
	public Integer getTainenL6() {
		return tainenL6;
	}
	/**
	 * @param tainenL6 the tainenL6 to set
	 */
	public void setTainenL6(Integer tainenL6) {
		this.tainenL6 = tainenL6;
	}
	/**
	 * @return the skkritsuL6
	 */
	public Double getSkkritsuL6() {
		return skkritsuL6;
	}
	/**
	 * @param skkritsuL6 the skkritsuL6 to set
	 */
	public void setSkkritsuL6(Double skkritsuL6) {
		this.skkritsuL6 = skkritsuL6;
	}
	/**
	 * @return the skkmmsuL6
	 */
	public Integer getSkkmmsuL6() {
		return skkmmsuL6;
	}
	/**
	 * @param skkmmsuL6 the skkmmsuL6 to set
	 */
	public void setSkkmmsuL6(Integer skkmmsuL6) {
		this.skkmmsuL6 = skkmmsuL6;
	}
	/**
	 * @return the zanzonmmsuL6
	 */
	public Integer getZanzonmmsuL6() {
		return zanzonmmsuL6;
	}
	/**
	 * @param zanzonmmsuL6 the zanzonmmsuL6 to set
	 */
	public void setZanzonmmsuL6(Integer zanzonmmsuL6) {
		this.zanzonmmsuL6 = zanzonmmsuL6;
	}
	/**
	 * @return the ksbokaL6
	 */
	public Long getKsbokaL6() {
		return ksbokaL6;
	}
	/**
	 * @param ksbokaL6 the ksbokaL6 to set
	 */
	public void setKsbokaL6(Long ksbokaL6) {
		this.ksbokaL6 = ksbokaL6;
	}
	/**
	 * @return the skkcalckisogkL6
	 */
	public Long getSkkcalckisogkL6() {
		return skkcalckisogkL6;
	}
	/**
	 * @param skkcalckisogkL6 the skkcalckisogkL6 to set
	 */
	public void setSkkcalckisogkL6(Long skkcalckisogkL6) {
		this.skkcalckisogkL6 = skkcalckisogkL6;
	}
	/**
	 * @return the zanzonritsuL6
	 */
	public Double getZanzonritsuL6() {
		return zanzonritsuL6;
	}
	/**
	 * @param zanzonritsuL6 the zanzonritsuL6 to set
	 */
	public void setZanzonritsuL6(Double zanzonritsuL6) {
		this.zanzonritsuL6 = zanzonritsuL6;
	}
	/**
	 * @return the zanzongkL6
	 */
	public Long getZanzongkL6() {
		return zanzongkL6;
	}
	/**
	 * @param zanzongkL6 the zanzongkL6 to set
	 */
	public void setZanzongkL6(Long zanzongkL6) {
		this.zanzongkL6 = zanzongkL6;
	}
	/**
	 * @return the ksftskkruigkL6
	 */
	public Long getKsftskkruigkL6() {
		return ksftskkruigkL6;
	}
	/**
	 * @param ksftskkruigkL6 the ksftskkruigkL6 to set
	 */
	public void setKsftskkruigkL6(Long ksftskkruigkL6) {
		this.ksftskkruigkL6 = ksftskkruigkL6;
	}
	/**
	 * @return the kszkskkruigkL6
	 */
	public Long getKszkskkruigkL6() {
		return kszkskkruigkL6;
	}
	/**
	 * @param kszkskkruigkL6 the kszkskkruigkL6 to set
	 */
	public void setKszkskkruigkL6(Long kszkskkruigkL6) {
		this.kszkskkruigkL6 = kszkskkruigkL6;
	}
	/**
	 * @return the kstkskkruigkL6
	 */
	public Long getKstkskkruigkL6() {
		return kstkskkruigkL6;
	}
	/**
	 * @param kstkskkruigkL6 the kstkskkruigkL6 to set
	 */
	public void setKstkskkruigkL6(Long kstkskkruigkL6) {
		this.kstkskkruigkL6 = kstkskkruigkL6;
	}
	/**
	 * @return the zenToftskkgkL6
	 */
	public Long getZenToftskkgkL6() {
		return zenToftskkgkL6;
	}
	/**
	 * @param zenToftskkgkL6 the zenToftskkgkL6 to set
	 */
	public void setZenToftskkgkL6(Long zenToftskkgkL6) {
		this.zenToftskkgkL6 = zenToftskkgkL6;
	}
	/**
	 * @return the zenTozkskkgkL6
	 */
	public Long getZenTozkskkgkL6() {
		return zenTozkskkgkL6;
	}
	/**
	 * @param zenTozkskkgkL6 the zenTozkskkgkL6 to set
	 */
	public void setZenTozkskkgkL6(Long zenTozkskkgkL6) {
		this.zenTozkskkgkL6 = zenTozkskkgkL6;
	}
	/**
	 * @return the zenTotkskkgkL6
	 */
	public Long getZenTotkskkgkL6() {
		return zenTotkskkgkL6;
	}
	/**
	 * @param zenTotkskkgkL6 the zenTotkskkgkL6 to set
	 */
	public void setZenTotkskkgkL6(Long zenTotkskkgkL6) {
		this.zenTotkskkgkL6 = zenTotkskkgkL6;
	}
	/**
	 * @return the zenToniniskkgkL6
	 */
	public Long getZenToniniskkgkL6() {
		return zenToniniskkgkL6;
	}
	/**
	 * @param zenToniniskkgkL6 the zenToniniskkgkL6 to set
	 */
	public void setZenToniniskkgkL6(Long zenToniniskkgkL6) {
		this.zenToniniskkgkL6 = zenToniniskkgkL6;
	}
	/**
	 * @return the toToftskkgkL6
	 */
	public Long getToToftskkgkL6() {
		return toToftskkgkL6;
	}
	/**
	 * @param toToftskkgkL6 the toToftskkgkL6 to set
	 */
	public void setToToftskkgkL6(Long toToftskkgkL6) {
		this.toToftskkgkL6 = toToftskkgkL6;
	}
	/**
	 * @return the toTozkskkgkL6
	 */
	public Long getToTozkskkgkL6() {
		return toTozkskkgkL6;
	}
	/**
	 * @param toTozkskkgkL6 the toTozkskkgkL6 to set
	 */
	public void setToTozkskkgkL6(Long toTozkskkgkL6) {
		this.toTozkskkgkL6 = toTozkskkgkL6;
	}
	/**
	 * @return the toTotkskkgkL6
	 */
	public Long getToTotkskkgkL6() {
		return toTotkskkgkL6;
	}
	/**
	 * @param toTotkskkgkL6 the toTotkskkgkL6 to set
	 */
	public void setToTotkskkgkL6(Long toTotkskkgkL6) {
		this.toTotkskkgkL6 = toTotkskkgkL6;
	}
	/**
	 * @return the toToniniskkgkL6
	 */
	public Long getToToniniskkgkL6() {
		return toToniniskkgkL6;
	}
	/**
	 * @param toToniniskkgkL6 the toToniniskkgkL6 to set
	 */
	public void setToToniniskkgkL6(Long toToniniskkgkL6) {
		this.toToniniskkgkL6 = toToniniskkgkL6;
	}
	/**
	 * @return the toBokaL6
	 */
	public Long getToBokaL6() {
		return toBokaL6;
	}
	/**
	 * @param toBokaL6 the toBokaL6 to set
	 */
	public void setToBokaL6(Long toBokaL6) {
		this.toBokaL6 = toBokaL6;
	}
	/**
	 * @return the zogenbokaL6
	 */
	public Long getZogenbokaL6() {
		return zogenbokaL6;
	}
	/**
	 * @param zogenbokaL6 the zogenbokaL6 to set
	 */
	public void setZogenbokaL6(Long zogenbokaL6) {
		this.zogenbokaL6 = zogenbokaL6;
	}
	/**
	 * @return the zkskkritsuL6
	 */
	public Double getZkskkritsuL6() {
		return zkskkritsuL6;
	}
	/**
	 * @param zkskkritsuL6 the zkskkritsuL6 to set
	 */
	public void setZkskkritsuL6(Double zkskkritsuL6) {
		this.zkskkritsuL6 = zkskkritsuL6;
	}
	/**
	 * @return the yukyustymL6
	 */
	public String getYukyustymL6() {
		return yukyustymL6;
	}
	/**
	 * @param yukyustymL6 the yukyustymL6 to set
	 */
	public void setYukyustymL6(String yukyustymL6) {
		this.yukyustymL6 = yukyustymL6;
	}
	/**
	 * @return the yukyufkymL6
	 */
	public String getYukyufkymL6() {
		return yukyufkymL6;
	}
	/**
	 * @param yukyufkymL6 the yukyufkymL6 to set
	 */
	public void setYukyufkymL6(String yukyufkymL6) {
		this.yukyufkymL6 = yukyufkymL6;
	}
	/**
	 * @return the shonencalckbnL6
	 */
	public String getShonencalckbnL6() {
		return shonencalckbnL6;
	}
	/**
	 * @param shonencalckbnL6 the shonencalckbnL6 to set
	 */
	public void setShonencalckbnL6(String shonencalckbnL6) {
		this.shonencalckbnL6 = shonencalckbnL6;
	}
	/**
	 * @return the skkkanryoflgL6
	 */
	public String getSkkkanryoflgL6() {
		return skkkanryoflgL6;
	}
	/**
	 * @param skkkanryoflgL6 the skkkanryoflgL6 to set
	 */
	public void setSkkkanryoflgL6(String skkkanryoflgL6) {
		this.skkkanryoflgL6 = skkkanryoflgL6;
	}
	/**
	 * @return the skkkirikaeyyL6
	 */
	public String getSkkkirikaeyyL6() {
		return skkkirikaeyyL6;
	}
	/**
	 * @param skkkirikaeyyL6 the skkkirikaeyyL6 to set
	 */
	public void setSkkkirikaeyyL6(String skkkirikaeyyL6) {
		this.skkkirikaeyyL6 = skkkirikaeyyL6;
	}
	/**
	 * @return the zanzonskkstyyL6
	 */
	public String getZanzonskkstyyL6() {
		return zanzonskkstyyL6;
	}
	/**
	 * @param zanzonskkstyyL6 the zanzonskkstyyL6 to set
	 */
	public void setZanzonskkstyyL6(String zanzonskkstyyL6) {
		this.zanzonskkstyyL6 = zanzonskkstyyL6;
	}
	/**
	 * @return the zanzonskkmmsuL6
	 */
	public Integer getZanzonskkmmsuL6() {
		return zanzonskkmmsuL6;
	}
	/**
	 * @param zanzonskkmmsuL6 the zanzonskkmmsuL6 to set
	 */
	public void setZanzonskkmmsuL6(Integer zanzonskkmmsuL6) {
		this.zanzonskkmmsuL6 = zanzonskkmmsuL6;
	}
	/**
	 * @return the konyucd
	 */
	public String getKonyucd() {
		return konyucd;
	}
	/**
	 * @param konyucd the konyucd to set
	 */
	public void setKonyucd(String konyucd) {
		this.konyucd = konyucd;
	}
	/**
	 * @return the konyunm
	 */
	public String getKonyunm() {
		return konyunm;
	}
	/**
	 * @param konyunm the konyunm to set
	 */
	public void setKonyunm(String konyunm) {
		this.konyunm = konyunm;
	}
	/**
	 * @return the kashicd
	 */
	public String getKashicd() {
		return kashicd;
	}
	/**
	 * @param kashicd the kashicd to set
	 */
	public void setKashicd(String kashicd) {
		this.kashicd = kashicd;
	}
	/**
	 * @return the kashinm
	 */
	public String getKashinm() {
		return kashinm;
	}
	/**
	 * @param kashinm the kashinm to set
	 */
	public void setKashinm(String kashinm) {
		this.kashinm = kashinm;
	}
	/**
	 * @return the cpOya
	 */
	public String getCpOya() {
		return cpOya;
	}
	/**
	 * @param cpOya the cpOya to set
	 */
	public void setCpOya(String cpOya) {
		this.cpOya = cpOya;
	}
	/**
	 * @return the cpEda
	 */
	public String getCpEda() {
		return cpEda;
	}
	/**
	 * @param cpEda the cpEda to set
	 */
	public void setCpEda(String cpEda) {
		this.cpEda = cpEda;
	}
	/**
	 * @return the knrbunruicd
	 */
	public String getKnrbunruicd() {
		return knrbunruicd;
	}
	/**
	 * @param knrbunruicd the knrbunruicd to set
	 */
	public void setKnrbunruicd(String knrbunruicd) {
		this.knrbunruicd = knrbunruicd;
	}
	/**
	 * @return the biko1
	 */
	public String getBiko1() {
		return biko1;
	}
	/**
	 * @param biko1 the biko1 to set
	 */
	public void setBiko1(String biko1) {
		this.biko1 = biko1;
	}
	/**
	 * @return the biko2
	 */
	public String getBiko2() {
		return biko2;
	}
	/**
	 * @param biko2 the biko2 to set
	 */
	public void setBiko2(String biko2) {
		this.biko2 = biko2;
	}
	/**
	 * @return the stkringino
	 */
	public String getStkringino() {
		return stkringino;
	}
	/**
	 * @param stkringino the stkringino to set
	 */
	public void setStkringino(String stkringino) {
		this.stkringino = stkringino;
	}
	/**
	 * @return the stktekiyo
	 */
	public String getStktekiyo() {
		return stktekiyo;
	}
	/**
	 * @param stktekiyo the stktekiyo to set
	 */
	public void setStktekiyo(String stktekiyo) {
		this.stktekiyo = stktekiyo;
	}
	/**
	 * @return the askcd
	 */
	public String getAskcd() {
		return askcd;
	}
	/**
	 * @param askcd the askcd to set
	 */
	public void setAskcd(String askcd) {
		this.askcd = askcd;
	}
	/**
	 * @return the asknm
	 */
	public String getAsknm() {
		return asknm;
	}
	/**
	 * @param asknm the asknm to set
	 */
	public void setAsknm(String asknm) {
		this.asknm = asknm;
	}
	/**
	 * @return the askkbn
	 */
	public String getAskkbn() {
		return askkbn;
	}
	/**
	 * @param askkbn the askkbn to set
	 */
	public void setAskkbn(String askkbn) {
		this.askkbn = askkbn;
	}
	/**
	 * @return the askgk
	 */
	public Long getAskgk() {
		return askgk;
	}
	/**
	 * @param askgk the askgk to set
	 */
	public void setAskgk(Long askgk) {
		this.askgk = askgk;
	}
	/**
	 * @return the ksaskzan
	 */
	public Long getKsaskzan() {
		return ksaskzan;
	}
	/**
	 * @param ksaskzan the ksaskzan to set
	 */
	public void setKsaskzan(Long ksaskzan) {
		this.ksaskzan = ksaskzan;
	}
	/**
	 * @return the ksaskninyogk
	 */
	public Long getKsaskninyogk() {
		return ksaskninyogk;
	}
	/**
	 * @param ksaskninyogk the ksaskninyogk to set
	 */
	public void setKsaskninyogk(Long ksaskninyogk) {
		this.ksaskninyogk = ksaskninyogk;
	}
	/**
	 * @return the askninyogk
	 */
	public Long getAskninyogk() {
		return askninyogk;
	}
	/**
	 * @param askninyogk the askninyogk to set
	 */
	public void setAskninyogk(Long askninyogk) {
		this.askninyogk = askninyogk;
	}
	/**
	 * @return the askkisogk
	 */
	public Long getAskkisogk() {
		return askkisogk;
	}
	/**
	 * @param askkisogk the askkisogk to set
	 */
	public void setAskkisogk(Long askkisogk) {
		this.askkisogk = askkisogk;
	}
	/**
	 * @return the askzanzongk
	 */
	public Long getAskzanzongk() {
		return askzanzongk;
	}
	/**
	 * @param askzanzongk the askzanzongk to set
	 */
	public void setAskzanzongk(Long askzanzongk) {
		this.askzanzongk = askzanzongk;
	}
	/**
	 * @return the kaiteiaskgk
	 */
	public Long getKaiteiaskgk() {
		return kaiteiaskgk;
	}
	/**
	 * @param kaiteiaskgk the kaiteiaskgk to set
	 */
	public void setKaiteiaskgk(Long kaiteiaskgk) {
		this.kaiteiaskgk = kaiteiaskgk;
	}
	/**
	 * @return the skkchokagk
	 */
	public Long getSkkchokagk() {
		return skkchokagk;
	}
	/**
	 * @param skkchokagk the skkchokagk to set
	 */
	public void setSkkchokagk(Long skkchokagk) {
		this.skkchokagk = skkchokagk;
	}
	/**
	 * @return the skkfusokugk
	 */
	public Long getSkkfusokugk() {
		return skkfusokugk;
	}
	/**
	 * @param skkfusokugk the skkfusokugk to set
	 */
	public void setSkkfusokugk(Long skkfusokugk) {
		this.skkfusokugk = skkfusokugk;
	}
	/**
	 * @return the tkcd
	 */
	public String getTkcd() {
		return tkcd;
	}
	/**
	 * @param tkcd the tkcd to set
	 */
	public void setTkcd(String tkcd) {
		this.tkcd = tkcd;
	}
	/**
	 * @return the tknm
	 */
	public String getTknm() {
		return tknm;
	}
	/**
	 * @param tknm the tknm to set
	 */
	public void setTknm(String tknm) {
		this.tknm = tknm;
	}
	/**
	 * @return the tkkbn
	 */
	public String getTkkbn() {
		return tkkbn;
	}
	/**
	 * @param tkkbn the tkkbn to set
	 */
	public void setTkkbn(String tkkbn) {
		this.tkkbn = tkkbn;
	}
	/**
	 * @return the tkritsuBunshi
	 */
	public Double getTkritsuBunshi() {
		return tkritsuBunshi;
	}
	/**
	 * @param tkritsuBunshi the tkritsuBunshi to set
	 */
	public void setTkritsuBunshi(Double tkritsuBunshi) {
		this.tkritsuBunshi = tkritsuBunshi;
	}
	/**
	 * @return the tkritsuBunbo
	 */
	public Double getTkritsuBunbo() {
		return tkritsuBunbo;
	}
	/**
	 * @param tkritsuBunbo the tkritsuBunbo to set
	 */
	public void setTkritsuBunbo(Double tkritsuBunbo) {
		this.tkritsuBunbo = tkritsuBunbo;
	}
	/**
	 * @return the beppyotaishokbn
	 */
	public String getBeppyotaishokbn() {
		return beppyotaishokbn;
	}
	/**
	 * @param beppyotaishokbn the beppyotaishokbn to set
	 */
	public void setBeppyotaishokbn(String beppyotaishokbn) {
		this.beppyotaishokbn = beppyotaishokbn;
	}
	/**
	 * @return the skkshisankbn
	 */
	public String getSkkshisankbn() {
		return skkshisankbn;
	}
	/**
	 * @param skkshisankbn the skkshisankbn to set
	 */
	public void setSkkshisankbn(String skkshisankbn) {
		this.skkshisankbn = skkshisankbn;
	}
	/**
	 * @return the aitekanjocd
	 */
	public String getAitekanjocd() {
		return aitekanjocd;
	}
	/**
	 * @param aitekanjocd the aitekanjocd to set
	 */
	public void setAitekanjocd(String aitekanjocd) {
		this.aitekanjocd = aitekanjocd;
	}
	/**
	 * @return the aitekanjonm
	 */
	public String getAitekanjonm() {
		return aitekanjonm;
	}
	/**
	 * @param aitekanjonm the aitekanjonm to set
	 */
	public void setAitekanjonm(String aitekanjonm) {
		this.aitekanjonm = aitekanjonm;
	}
	/**
	 * @return the aitehojokamokucd
	 */
	public String getAitehojokamokucd() {
		return aitehojokamokucd;
	}
	/**
	 * @param aitehojokamokucd the aitehojokamokucd to set
	 */
	public void setAitehojokamokucd(String aitehojokamokucd) {
		this.aitehojokamokucd = aitehojokamokucd;
	}
	/**
	 * @return the aitehojokamokunm
	 */
	public String getAitehojokamokunm() {
		return aitehojokamokunm;
	}
	/**
	 * @param aitehojokamokunm the aitehojokamokunm to set
	 */
	public void setAitehojokamokunm(String aitehojokamokunm) {
		this.aitehojokamokunm = aitehojokamokunm;
	}
	/**
	 * @return the gappeiukekbn
	 */
	public String getGappeiukekbn() {
		return gappeiukekbn;
	}
	/**
	 * @param gappeiukekbn the gappeiukekbn to set
	 */
	public void setGappeiukekbn(String gappeiukekbn) {
		this.gappeiukekbn = gappeiukekbn;
	}
	/**
	 * @return the genshistkymd
	 */
	public String getGenshistkymd() {
		return genshistkymd;
	}
	/**
	 * @param genshistkymd the genshistkymd to set
	 */
	public void setGenshistkymd(String genshistkymd) {
		this.genshistkymd = genshistkymd;
	}
	/**
	 * @return the groupcd
	 */
	public String getGroupcd() {
		return groupcd;
	}
	/**
	 * @param groupcd the groupcd to set
	 */
	public void setGroupcd(String groupcd) {
		this.groupcd = groupcd;
	}
	/**
	 * @return the groupnm
	 */
	public String getGroupnm() {
		return groupnm;
	}
	/**
	 * @param groupnm the groupnm to set
	 */
	public void setGroupnm(String groupnm) {
		this.groupnm = groupnm;
	}
	/**
	 * @return the shinariocd
	 */
	public String getShinariocd() {
		return shinariocd;
	}
	/**
	 * @param shinariocd the shinariocd to set
	 */
	public void setShinariocd(String shinariocd) {
		this.shinariocd = shinariocd;
	}
	/**
	 * @return the shinarionm
	 */
	public String getShinarionm() {
		return shinarionm;
	}
	/**
	 * @param shinarionm the shinarionm to set
	 */
	public void setShinarionm(String shinarionm) {
		this.shinarionm = shinarionm;
	}
	/**
	 * @return the shuyoshisankbn
	 */
	public String getShuyoshisankbn() {
		return shuyoshisankbn;
	}
	/**
	 * @param shuyoshisankbn the shuyoshisankbn to set
	 */
	public void setShuyoshisankbn(String shuyoshisankbn) {
		this.shuyoshisankbn = shuyoshisankbn;
	}
	/**
	 * @return the niniLd_1cd
	 */
	public String getNiniLd_1cd() {
		return niniLd_1cd;
	}
	/**
	 * @param niniLd_1cd the niniLd_1cd to set
	 */
	public void setNiniLd_1cd(String niniLd_1cd) {
		this.niniLd_1cd = niniLd_1cd;
	}
	/**
	 * @return the niniLd_1nm
	 */
	public String getNiniLd_1nm() {
		return niniLd_1nm;
	}
	/**
	 * @param niniLd_1nm the niniLd_1nm to set
	 */
	public void setNiniLd_1nm(String niniLd_1nm) {
		this.niniLd_1nm = niniLd_1nm;
	}
	/**
	 * @return the niniLd_2cd
	 */
	public String getNiniLd_2cd() {
		return niniLd_2cd;
	}
	/**
	 * @param niniLd_2cd the niniLd_2cd to set
	 */
	public void setNiniLd_2cd(String niniLd_2cd) {
		this.niniLd_2cd = niniLd_2cd;
	}
	/**
	 * @return the niniLd_2nm
	 */
	public String getNiniLd_2nm() {
		return niniLd_2nm;
	}
	/**
	 * @param niniLd_2nm the niniLd_2nm to set
	 */
	public void setNiniLd_2nm(String niniLd_2nm) {
		this.niniLd_2nm = niniLd_2nm;
	}
	/**
	 * @return the niniLd_3cd
	 */
	public String getNiniLd_3cd() {
		return niniLd_3cd;
	}
	/**
	 * @param niniLd_3cd the niniLd_3cd to set
	 */
	public void setNiniLd_3cd(String niniLd_3cd) {
		this.niniLd_3cd = niniLd_3cd;
	}
	/**
	 * @return the niniLd_3nm
	 */
	public String getNiniLd_3nm() {
		return niniLd_3nm;
	}
	/**
	 * @param niniLd_3nm the niniLd_3nm to set
	 */
	public void setNiniLd_3nm(String niniLd_3nm) {
		this.niniLd_3nm = niniLd_3nm;
	}
	/**
	 * @return the niniLd_4cd
	 */
	public String getNiniLd_4cd() {
		return niniLd_4cd;
	}
	/**
	 * @param niniLd_4cd the niniLd_4cd to set
	 */
	public void setNiniLd_4cd(String niniLd_4cd) {
		this.niniLd_4cd = niniLd_4cd;
	}
	/**
	 * @return the niniLd_4nm
	 */
	public String getNiniLd_4nm() {
		return niniLd_4nm;
	}
	/**
	 * @param niniLd_4nm the niniLd_4nm to set
	 */
	public void setNiniLd_4nm(String niniLd_4nm) {
		this.niniLd_4nm = niniLd_4nm;
	}
	/**
	 * @return the niniLd_5cd
	 */
	public String getNiniLd_5cd() {
		return niniLd_5cd;
	}
	/**
	 * @param niniLd_5cd the niniLd_5cd to set
	 */
	public void setNiniLd_5cd(String niniLd_5cd) {
		this.niniLd_5cd = niniLd_5cd;
	}
	/**
	 * @return the niniLd_5nm
	 */
	public String getNiniLd_5nm() {
		return niniLd_5nm;
	}
	/**
	 * @param niniLd_5nm the niniLd_5nm to set
	 */
	public void setNiniLd_5nm(String niniLd_5nm) {
		this.niniLd_5nm = niniLd_5nm;
	}
	/**
	 * @return the niniLd_6cd
	 */
	public String getNiniLd_6cd() {
		return niniLd_6cd;
	}
	/**
	 * @param niniLd_6cd the niniLd_6cd to set
	 */
	public void setNiniLd_6cd(String niniLd_6cd) {
		this.niniLd_6cd = niniLd_6cd;
	}
	/**
	 * @return the niniLd_6nm
	 */
	public String getNiniLd_6nm() {
		return niniLd_6nm;
	}
	/**
	 * @param niniLd_6nm the niniLd_6nm to set
	 */
	public void setNiniLd_6nm(String niniLd_6nm) {
		this.niniLd_6nm = niniLd_6nm;
	}
	/**
	 * @return the niniLd_7cd
	 */
	public String getNiniLd_7cd() {
		return niniLd_7cd;
	}
	/**
	 * @param niniLd_7cd the niniLd_7cd to set
	 */
	public void setNiniLd_7cd(String niniLd_7cd) {
		this.niniLd_7cd = niniLd_7cd;
	}
	/**
	 * @return the niniLd_7nm
	 */
	public String getNiniLd_7nm() {
		return niniLd_7nm;
	}
	/**
	 * @param niniLd_7nm the niniLd_7nm to set
	 */
	public void setNiniLd_7nm(String niniLd_7nm) {
		this.niniLd_7nm = niniLd_7nm;
	}
	/**
	 * @return the niniLd_8cd
	 */
	public String getNiniLd_8cd() {
		return niniLd_8cd;
	}
	/**
	 * @param niniLd_8cd the niniLd_8cd to set
	 */
	public void setNiniLd_8cd(String niniLd_8cd) {
		this.niniLd_8cd = niniLd_8cd;
	}
	/**
	 * @return the niniLd_8nm
	 */
	public String getNiniLd_8nm() {
		return niniLd_8nm;
	}
	/**
	 * @param niniLd_8nm the niniLd_8nm to set
	 */
	public void setNiniLd_8nm(String niniLd_8nm) {
		this.niniLd_8nm = niniLd_8nm;
	}
	/**
	 * @return the niniLd_9cd
	 */
	public String getNiniLd_9cd() {
		return niniLd_9cd;
	}
	/**
	 * @param niniLd_9cd the niniLd_9cd to set
	 */
	public void setNiniLd_9cd(String niniLd_9cd) {
		this.niniLd_9cd = niniLd_9cd;
	}
	/**
	 * @return the niniLd_9nm
	 */
	public String getNiniLd_9nm() {
		return niniLd_9nm;
	}
	/**
	 * @param niniLd_9nm the niniLd_9nm to set
	 */
	public void setNiniLd_9nm(String niniLd_9nm) {
		this.niniLd_9nm = niniLd_9nm;
	}
	/**
	 * @return the niniLd_10cd
	 */
	public String getNiniLd_10cd() {
		return niniLd_10cd;
	}
	/**
	 * @param niniLd_10cd the niniLd_10cd to set
	 */
	public void setNiniLd_10cd(String niniLd_10cd) {
		this.niniLd_10cd = niniLd_10cd;
	}
	/**
	 * @return the niniLd_10nm
	 */
	public String getNiniLd_10nm() {
		return niniLd_10nm;
	}
	/**
	 * @param niniLd_10nm the niniLd_10nm to set
	 */
	public void setNiniLd_10nm(String niniLd_10nm) {
		this.niniLd_10nm = niniLd_10nm;
	}
	/**
	 * @return the niniLd_11cd
	 */
	public String getNiniLd_11cd() {
		return niniLd_11cd;
	}
	/**
	 * @param niniLd_11cd the niniLd_11cd to set
	 */
	public void setNiniLd_11cd(String niniLd_11cd) {
		this.niniLd_11cd = niniLd_11cd;
	}
	/**
	 * @return the niniLd_11nm
	 */
	public String getNiniLd_11nm() {
		return niniLd_11nm;
	}
	/**
	 * @param niniLd_11nm the niniLd_11nm to set
	 */
	public void setNiniLd_11nm(String niniLd_11nm) {
		this.niniLd_11nm = niniLd_11nm;
	}
	/**
	 * @return the niniLd_12cd
	 */
	public String getNiniLd_12cd() {
		return niniLd_12cd;
	}
	/**
	 * @param niniLd_12cd the niniLd_12cd to set
	 */
	public void setNiniLd_12cd(String niniLd_12cd) {
		this.niniLd_12cd = niniLd_12cd;
	}
	/**
	 * @return the niniLd_12nm
	 */
	public String getNiniLd_12nm() {
		return niniLd_12nm;
	}
	/**
	 * @param niniLd_12nm the niniLd_12nm to set
	 */
	public void setNiniLd_12nm(String niniLd_12nm) {
		this.niniLd_12nm = niniLd_12nm;
	}
	/**
	 * @return the niniLd_13cd
	 */
	public String getNiniLd_13cd() {
		return niniLd_13cd;
	}
	/**
	 * @param niniLd_13cd the niniLd_13cd to set
	 */
	public void setNiniLd_13cd(String niniLd_13cd) {
		this.niniLd_13cd = niniLd_13cd;
	}
	/**
	 * @return the niniLd_13nm
	 */
	public String getNiniLd_13nm() {
		return niniLd_13nm;
	}
	/**
	 * @param niniLd_13nm the niniLd_13nm to set
	 */
	public void setNiniLd_13nm(String niniLd_13nm) {
		this.niniLd_13nm = niniLd_13nm;
	}
	/**
	 * @return the niniLd_14cd
	 */
	public String getNiniLd_14cd() {
		return niniLd_14cd;
	}
	/**
	 * @param niniLd_14cd the niniLd_14cd to set
	 */
	public void setNiniLd_14cd(String niniLd_14cd) {
		this.niniLd_14cd = niniLd_14cd;
	}
	/**
	 * @return the niniLd_14nm
	 */
	public String getNiniLd_14nm() {
		return niniLd_14nm;
	}
	/**
	 * @param niniLd_14nm the niniLd_14nm to set
	 */
	public void setNiniLd_14nm(String niniLd_14nm) {
		this.niniLd_14nm = niniLd_14nm;
	}
	/**
	 * @return the niniLd_15cd
	 */
	public String getNiniLd_15cd() {
		return niniLd_15cd;
	}
	/**
	 * @param niniLd_15cd the niniLd_15cd to set
	 */
	public void setNiniLd_15cd(String niniLd_15cd) {
		this.niniLd_15cd = niniLd_15cd;
	}
	/**
	 * @return the niniLd_15nm
	 */
	public String getNiniLd_15nm() {
		return niniLd_15nm;
	}
	/**
	 * @param niniLd_15nm the niniLd_15nm to set
	 */
	public void setNiniLd_15nm(String niniLd_15nm) {
		this.niniLd_15nm = niniLd_15nm;
	}
	/**
	 * @return the niniLd_16cd
	 */
	public String getNiniLd_16cd() {
		return niniLd_16cd;
	}
	/**
	 * @param niniLd_16cd the niniLd_16cd to set
	 */
	public void setNiniLd_16cd(String niniLd_16cd) {
		this.niniLd_16cd = niniLd_16cd;
	}
	/**
	 * @return the niniLd_16nm
	 */
	public String getNiniLd_16nm() {
		return niniLd_16nm;
	}
	/**
	 * @param niniLd_16nm the niniLd_16nm to set
	 */
	public void setNiniLd_16nm(String niniLd_16nm) {
		this.niniLd_16nm = niniLd_16nm;
	}
	/**
	 * @return the niniLd_17cd
	 */
	public String getNiniLd_17cd() {
		return niniLd_17cd;
	}
	/**
	 * @param niniLd_17cd the niniLd_17cd to set
	 */
	public void setNiniLd_17cd(String niniLd_17cd) {
		this.niniLd_17cd = niniLd_17cd;
	}
	/**
	 * @return the niniLd_17nm
	 */
	public String getNiniLd_17nm() {
		return niniLd_17nm;
	}
	/**
	 * @param niniLd_17nm the niniLd_17nm to set
	 */
	public void setNiniLd_17nm(String niniLd_17nm) {
		this.niniLd_17nm = niniLd_17nm;
	}
	/**
	 * @return the niniLd_18cd
	 */
	public String getNiniLd_18cd() {
		return niniLd_18cd;
	}
	/**
	 * @param niniLd_18cd the niniLd_18cd to set
	 */
	public void setNiniLd_18cd(String niniLd_18cd) {
		this.niniLd_18cd = niniLd_18cd;
	}
	/**
	 * @return the niniLd_18nm
	 */
	public String getNiniLd_18nm() {
		return niniLd_18nm;
	}
	/**
	 * @param niniLd_18nm the niniLd_18nm to set
	 */
	public void setNiniLd_18nm(String niniLd_18nm) {
		this.niniLd_18nm = niniLd_18nm;
	}
	/**
	 * @return the niniLd_19cd
	 */
	public String getNiniLd_19cd() {
		return niniLd_19cd;
	}
	/**
	 * @param niniLd_19cd the niniLd_19cd to set
	 */
	public void setNiniLd_19cd(String niniLd_19cd) {
		this.niniLd_19cd = niniLd_19cd;
	}
	/**
	 * @return the niniLd_19nm
	 */
	public String getNiniLd_19nm() {
		return niniLd_19nm;
	}
	/**
	 * @param niniLd_19nm the niniLd_19nm to set
	 */
	public void setNiniLd_19nm(String niniLd_19nm) {
		this.niniLd_19nm = niniLd_19nm;
	}
	/**
	 * @return the niniLd_20cd
	 */
	public String getNiniLd_20cd() {
		return niniLd_20cd;
	}
	/**
	 * @param niniLd_20cd the niniLd_20cd to set
	 */
	public void setNiniLd_20cd(String niniLd_20cd) {
		this.niniLd_20cd = niniLd_20cd;
	}
	/**
	 * @return the niniLd_20nm
	 */
	public String getNiniLd_20nm() {
		return niniLd_20nm;
	}
	/**
	 * @param niniLd_20nm the niniLd_20nm to set
	 */
	public void setNiniLd_20nm(String niniLd_20nm) {
		this.niniLd_20nm = niniLd_20nm;
	}
	/**
	 * @return the toshinkkyy
	 */
	public Long getToshinkkyy() {
		return toshinkkyy;
	}
	/**
	 * @param toshinkkyy the toshinkkyy to set
	 */
	public void setToshinkkyy(Long toshinkkyy) {
		this.toshinkkyy = toshinkkyy;
	}
	/**
	 * @return the toshinkkchicd
	 */
	public String getToshinkkchicd() {
		return toshinkkchicd;
	}
	/**
	 * @param toshinkkchicd the toshinkkchicd to set
	 */
	public void setToshinkkchicd(String toshinkkchicd) {
		this.toshinkkchicd = toshinkkchicd;
	}
	/**
	 * @return the toshinkkchinm
	 */
	public String getToshinkkchinm() {
		return toshinkkchinm;
	}
	/**
	 * @param toshinkkchinm the toshinkkchinm to set
	 */
	public void setToshinkkchinm(String toshinkkchinm) {
		this.toshinkkchinm = toshinkkchinm;
	}
	/**
	 * @return the toshinkkshurui
	 */
	public String getToshinkkshurui() {
		return toshinkkshurui;
	}
	/**
	 * @param toshinkkshurui the toshinkkshurui to set
	 */
	public void setToshinkkshurui(String toshinkkshurui) {
		this.toshinkkshurui = toshinkkshurui;
	}
	/**
	 * @return the toshinkkstkgk
	 */
	public Long getToshinkkstkgk() {
		return toshinkkstkgk;
	}
	/**
	 * @param toshinkkstkgk the toshinkkstkgk to set
	 */
	public void setToshinkkstkgk(Long toshinkkstkgk) {
		this.toshinkkstkgk = toshinkkstkgk;
	}
	/**
	 * @return the toshinkktainen
	 */
	public Integer getToshinkktainen() {
		return toshinkktainen;
	}
	/**
	 * @param toshinkktainen the toshinkktainen to set
	 */
	public void setToshinkktainen(Integer toshinkktainen) {
		this.toshinkktainen = toshinkktainen;
	}
	/**
	 * @return the toshinkkZkskkritsu
	 */
	public Double getToshinkkZkskkritsu() {
		return toshinkkZkskkritsu;
	}
	/**
	 * @param toshinkkZkskkritsu the toshinkkZkskkritsu to set
	 */
	public void setToshinkkZkskkritsu(Double toshinkkZkskkritsu) {
		this.toshinkkZkskkritsu = toshinkkZkskkritsu;
	}
	/**
	 * @return the tothzeicd
	 */
	public String getTothzeicd() {
		return tothzeicd;
	}
	/**
	 * @param tothzeicd the tothzeicd to set
	 */
	public void setTothzeicd(String tothzeicd) {
		this.tothzeicd = tothzeicd;
	}
	/**
	 * @return the tothzeinm
	 */
	public String getTothzeinm() {
		return tothzeinm;
	}
	/**
	 * @param tothzeinm the tothzeinm to set
	 */
	public void setTothzeinm(String tothzeinm) {
		this.tothzeinm = tothzeinm;
	}
	/**
	 * @return the totkreiritsuBunshi
	 */
	public Double getTotkreiritsuBunshi() {
		return totkreiritsuBunshi;
	}
	/**
	 * @param totkreiritsuBunshi the totkreiritsuBunshi to set
	 */
	public void setTotkreiritsuBunshi(Double totkreiritsuBunshi) {
		this.totkreiritsuBunshi = totkreiritsuBunshi;
	}
	/**
	 * @return the totkreiritsuBunbo
	 */
	public Double getTotkreiritsuBunbo() {
		return totkreiritsuBunbo;
	}
	/**
	 * @param totkreiritsuBunbo the totkreiritsuBunbo to set
	 */
	public void setTotkreiritsuBunbo(Double totkreiritsuBunbo) {
		this.totkreiritsuBunbo = totkreiritsuBunbo;
	}
	/**
	 * @return the zenshinkkyy
	 */
	public String getZenshinkkyy() {
		return zenshinkkyy;
	}
	/**
	 * @param zenshinkkyy the zenshinkkyy to set
	 */
	public void setZenshinkkyy(String zenshinkkyy) {
		this.zenshinkkyy = zenshinkkyy;
	}
	/**
	 * @return the zenshinkkchicd
	 */
	public String getZenshinkkchicd() {
		return zenshinkkchicd;
	}
	/**
	 * @param zenshinkkchicd the zenshinkkchicd to set
	 */
	public void setZenshinkkchicd(String zenshinkkchicd) {
		this.zenshinkkchicd = zenshinkkchicd;
	}
	/**
	 * @return the zenshinkkchinm
	 */
	public String getZenshinkkchinm() {
		return zenshinkkchinm;
	}
	/**
	 * @param zenshinkkchinm the zenshinkkchinm to set
	 */
	public void setZenshinkkchinm(String zenshinkkchinm) {
		this.zenshinkkchinm = zenshinkkchinm;
	}
	/**
	 * @return the zenshinkkshurui
	 */
	public String getZenshinkkshurui() {
		return zenshinkkshurui;
	}
	/**
	 * @param zenshinkkshurui the zenshinkkshurui to set
	 */
	public void setZenshinkkshurui(String zenshinkkshurui) {
		this.zenshinkkshurui = zenshinkkshurui;
	}
	/**
	 * @return the zenshinkkstkgk
	 */
	public Long getZenshinkkstkgk() {
		return zenshinkkstkgk;
	}
	/**
	 * @param zenshinkkstkgk the zenshinkkstkgk to set
	 */
	public void setZenshinkkstkgk(Long zenshinkkstkgk) {
		this.zenshinkkstkgk = zenshinkkstkgk;
	}
	/**
	 * @return the zenshinkktainen
	 */
	public Double getZenshinkktainen() {
		return zenshinkktainen;
	}
	/**
	 * @param zenshinkktainen the zenshinkktainen to set
	 */
	public void setZenshinkktainen(Double zenshinkktainen) {
		this.zenshinkktainen = zenshinkktainen;
	}
	/**
	 * @return the zenshinkkZkskkritsu
	 */
	public Double getZenshinkkZkskkritsu() {
		return zenshinkkZkskkritsu;
	}
	/**
	 * @param zenshinkkZkskkritsu the zenshinkkZkskkritsu to set
	 */
	public void setZenshinkkZkskkritsu(Double zenshinkkZkskkritsu) {
		this.zenshinkkZkskkritsu = zenshinkkZkskkritsu;
	}
	/**
	 * @return the zenthzeicd
	 */
	public String getZenthzeicd() {
		return zenthzeicd;
	}
	/**
	 * @param zenthzeicd the zenthzeicd to set
	 */
	public void setZenthzeicd(String zenthzeicd) {
		this.zenthzeicd = zenthzeicd;
	}
	/**
	 * @return the zenthzeinm
	 */
	public String getZenthzeinm() {
		return zenthzeinm;
	}
	/**
	 * @param zenthzeinm the zenthzeinm to set
	 */
	public void setZenthzeinm(String zenthzeinm) {
		this.zenthzeinm = zenthzeinm;
	}
	/**
	 * @return the zentkreiritsuBunshi
	 */
	public Double getZentkreiritsuBunshi() {
		return zentkreiritsuBunshi;
	}
	/**
	 * @param zentkreiritsuBunshi the zentkreiritsuBunshi to set
	 */
	public void setZentkreiritsuBunshi(Double zentkreiritsuBunshi) {
		this.zentkreiritsuBunshi = zentkreiritsuBunshi;
	}
	/**
	 * @return the zentkreiritsuBunbo
	 */
	public Double getZentkreiritsuBunbo() {
		return zentkreiritsuBunbo;
	}
	/**
	 * @param zentkreiritsuBunbo the zentkreiritsuBunbo to set
	 */
	public void setZentkreiritsuBunbo(Double zentkreiritsuBunbo) {
		this.zentkreiritsuBunbo = zentkreiritsuBunbo;
	}
	/**
	 * @return the zenrironboka
	 */
	public Long getZenrironboka() {
		return zenrironboka;
	}
	/**
	 * @param zenrironboka the zenrironboka to set
	 */
	public void setZenrironboka(Long zenrironboka) {
		this.zenrironboka = zenrironboka;
	}
	/**
	 * @return the zenhyokagk
	 */
	public Long getZenhyokagk() {
		return zenhyokagk;
	}
	/**
	 * @param zenhyokagk the zenhyokagk to set
	 */
	public void setZenhyokagk(Long zenhyokagk) {
		this.zenhyokagk = zenhyokagk;
	}
	/**
	 * @return the ykshinkkyy
	 */
	public Long getYkshinkkyy() {
		return ykshinkkyy;
	}
	/**
	 * @param ykshinkkyy the ykshinkkyy to set
	 */
	public void setYkshinkkyy(Long ykshinkkyy) {
		this.ykshinkkyy = ykshinkkyy;
	}
	/**
	 * @return the ykshinkkchicd
	 */
	public String getYkshinkkchicd() {
		return ykshinkkchicd;
	}
	/**
	 * @param ykshinkkchicd the ykshinkkchicd to set
	 */
	public void setYkshinkkchicd(String ykshinkkchicd) {
		this.ykshinkkchicd = ykshinkkchicd;
	}
	/**
	 * @return the ykshinkkchinm
	 */
	public String getYkshinkkchinm() {
		return ykshinkkchinm;
	}
	/**
	 * @param ykshinkkchinm the ykshinkkchinm to set
	 */
	public void setYkshinkkchinm(String ykshinkkchinm) {
		this.ykshinkkchinm = ykshinkkchinm;
	}
	/**
	 * @return the ykshinkkshurui
	 */
	public String getYkshinkkshurui() {
		return ykshinkkshurui;
	}
	/**
	 * @param ykshinkkshurui the ykshinkkshurui to set
	 */
	public void setYkshinkkshurui(String ykshinkkshurui) {
		this.ykshinkkshurui = ykshinkkshurui;
	}
	/**
	 * @return the ykshinkkstkgk
	 */
	public Long getYkshinkkstkgk() {
		return ykshinkkstkgk;
	}
	/**
	 * @param ykshinkkstkgk the ykshinkkstkgk to set
	 */
	public void setYkshinkkstkgk(Long ykshinkkstkgk) {
		this.ykshinkkstkgk = ykshinkkstkgk;
	}
	/**
	 * @return the ykshinkktainen
	 */
	public Double getYkshinkktainen() {
		return ykshinkktainen;
	}
	/**
	 * @param ykshinkktainen the ykshinkktainen to set
	 */
	public void setYkshinkktainen(Double ykshinkktainen) {
		this.ykshinkktainen = ykshinkktainen;
	}
	/**
	 * @return the ykshinkkZkskkritsu
	 */
	public Double getYkshinkkZkskkritsu() {
		return ykshinkkZkskkritsu;
	}
	/**
	 * @param ykshinkkZkskkritsu the ykshinkkZkskkritsu to set
	 */
	public void setYkshinkkZkskkritsu(Double ykshinkkZkskkritsu) {
		this.ykshinkkZkskkritsu = ykshinkkZkskkritsu;
	}
	/**
	 * @return the ykthzeicd
	 */
	public String getYkthzeicd() {
		return ykthzeicd;
	}
	/**
	 * @param ykthzeicd the ykthzeicd to set
	 */
	public void setYkthzeicd(String ykthzeicd) {
		this.ykthzeicd = ykthzeicd;
	}
	/**
	 * @return the ykthzeinm
	 */
	public String getYkthzeinm() {
		return ykthzeinm;
	}
	/**
	 * @param ykthzeinm the ykthzeinm to set
	 */
	public void setYkthzeinm(String ykthzeinm) {
		this.ykthzeinm = ykthzeinm;
	}
	/**
	 * @return the yktkreiritsuBunshi
	 */
	public Double getYktkreiritsuBunshi() {
		return yktkreiritsuBunshi;
	}
	/**
	 * @param yktkreiritsuBunshi the yktkreiritsuBunshi to set
	 */
	public void setYktkreiritsuBunshi(Double yktkreiritsuBunshi) {
		this.yktkreiritsuBunshi = yktkreiritsuBunshi;
	}
	/**
	 * @return the yktkreiritsuBunbo
	 */
	public Double getYktkreiritsuBunbo() {
		return yktkreiritsuBunbo;
	}
	/**
	 * @param yktkreiritsuBunbo the yktkreiritsuBunbo to set
	 */
	public void setYktkreiritsuBunbo(Double yktkreiritsuBunbo) {
		this.yktkreiritsuBunbo = yktkreiritsuBunbo;
	}
	/**
	 * @return the lastshinkkyy
	 */
	public String getLastshinkkyy() {
		return lastshinkkyy;
	}
	/**
	 * @param lastshinkkyy the lastshinkkyy to set
	 */
	public void setLastshinkkyy(String lastshinkkyy) {
		this.lastshinkkyy = lastshinkkyy;
	}
	/**
	 * @return the togensonkbnk
	 */
	public String getTogensonkbnk() {
		return togensonkbnk;
	}
	/**
	 * @param togensonkbnk the togensonkbnk to set
	 */
	public void setTogensonkbnk(String togensonkbnk) {
		this.togensonkbnk = togensonkbnk;
	}
	/**
	 * @return the ksgensonruigkk
	 */
	public Long getKsgensonruigkk() {
		return ksgensonruigkk;
	}
	/**
	 * @param ksgensonruigkk the ksgensonruigkk to set
	 */
	public void setKsgensonruigkk(Long ksgensonruigkk) {
		this.ksgensonruigkk = ksgensonruigkk;
	}
	/**
	 * @return the togensongkk
	 */
	public Long getTogensongkk() {
		return togensongkk;
	}
	/**
	 * @param togensongkk the togensongkk to set
	 */
	public void setTogensongkk(Long togensongkk) {
		this.togensongkk = togensongkk;
	}
	/**
	 * @return the skkcalczanzongkk
	 */
	public Long getSkkcalczanzongkk() {
		return skkcalczanzongkk;
	}
	/**
	 * @param skkcalczanzongkk the skkcalczanzongkk to set
	 */
	public void setSkkcalczanzongkk(Long skkcalczanzongkk) {
		this.skkcalczanzongkk = skkcalczanzongkk;
	}
	/**
	 * @return the gensonbokak
	 */
	public Long getGensonbokak() {
		return gensonbokak;
	}
	/**
	 * @param gensonbokak the gensonbokak to set
	 */
	public void setGensonbokak(Long gensonbokak) {
		this.gensonbokak = gensonbokak;
	}
	/**
	 * @return the gensonmaetainenk
	 */
	public Integer getGensonmaetainenk() {
		return gensonmaetainenk;
	}
	/**
	 * @param gensonmaetainenk the gensonmaetainenk to set
	 */
	public void setGensonmaetainenk(Integer gensonmaetainenk) {
		this.gensonmaetainenk = gensonmaetainenk;
	}
	/**
	 * @return the gensonmaeskkmmsuk
	 */
	public Integer getGensonmaeskkmmsuk() {
		return gensonmaeskkmmsuk;
	}
	/**
	 * @param gensonmaeskkmmsuk the gensonmaeskkmmsuk to set
	 */
	public void setGensonmaeskkmmsuk(Integer gensonmaeskkmmsuk) {
		this.gensonmaeskkmmsuk = gensonmaeskkmmsuk;
	}
	/**
	 * @return the gensonymdk
	 */
	public String getGensonymdk() {
		return gensonymdk;
	}
	/**
	 * @param gensonymdk the gensonymdk to set
	 */
	public void setGensonymdk(String gensonymdk) {
		this.gensonymdk = gensonymdk;
	}
	/**
	 * @return the genshistkgkk
	 */
	public Long getGenshistkgkk() {
		return genshistkgkk;
	}
	/**
	 * @param genshistkgkk the genshistkgkk to set
	 */
	public void setGenshistkgkk(Long genshistkgkk) {
		this.genshistkgkk = genshistkgkk;
	}
	/**
	 * @return the kaiteistkgkk
	 */
	public Long getKaiteistkgkk() {
		return kaiteistkgkk;
	}
	/**
	 * @param kaiteistkgkk the kaiteistkgkk to set
	 */
	public void setKaiteistkgkk(Long kaiteistkgkk) {
		this.kaiteistkgkk = kaiteistkgkk;
	}
	/**
	 * @return the kaiteitainenk
	 */
	public Integer getKaiteitainenk() {
		return kaiteitainenk;
	}
	/**
	 * @param kaiteitainenk the kaiteitainenk to set
	 */
	public void setKaiteitainenk(Integer kaiteitainenk) {
		this.kaiteitainenk = kaiteitainenk;
	}
	/**
	 * @return the kaiteiymdk
	 */
	public String getKaiteiymdk() {
		return kaiteiymdk;
	}
	/**
	 * @param kaiteiymdk the kaiteiymdk to set
	 */
	public void setKaiteiymdk(String kaiteiymdk) {
		this.kaiteiymdk = kaiteiymdk;
	}
	/**
	 * @return the yusenzogenkbnk
	 */
	public String getYusenzogenkbnk() {
		return yusenzogenkbnk;
	}
	/**
	 * @param yusenzogenkbnk the yusenzogenkbnk to set
	 */
	public void setYusenzogenkbnk(String yusenzogenkbnk) {
		this.yusenzogenkbnk = yusenzogenkbnk;
	}
	/**
	 * @return the togensonkbnz
	 */
	public String getTogensonkbnz() {
		return togensonkbnz;
	}
	/**
	 * @param togensonkbnz the togensonkbnz to set
	 */
	public void setTogensonkbnz(String togensonkbnz) {
		this.togensonkbnz = togensonkbnz;
	}
	/**
	 * @return the ksgensonruigkz
	 */
	public Long getKsgensonruigkz() {
		return ksgensonruigkz;
	}
	/**
	 * @param ksgensonruigkz the ksgensonruigkz to set
	 */
	public void setKsgensonruigkz(Long ksgensonruigkz) {
		this.ksgensonruigkz = ksgensonruigkz;
	}
	/**
	 * @return the togensongkz
	 */
	public Long getTogensongkz() {
		return togensongkz;
	}
	/**
	 * @param togensongkz the togensongkz to set
	 */
	public void setTogensongkz(Long togensongkz) {
		this.togensongkz = togensongkz;
	}
	/**
	 * @return the skkcalczanzongkz
	 */
	public Long getSkkcalczanzongkz() {
		return skkcalczanzongkz;
	}
	/**
	 * @param skkcalczanzongkz the skkcalczanzongkz to set
	 */
	public void setSkkcalczanzongkz(Long skkcalczanzongkz) {
		this.skkcalczanzongkz = skkcalczanzongkz;
	}
	/**
	 * @return the gensonbokaz
	 */
	public Long getGensonbokaz() {
		return gensonbokaz;
	}
	/**
	 * @param gensonbokaz the gensonbokaz to set
	 */
	public void setGensonbokaz(Long gensonbokaz) {
		this.gensonbokaz = gensonbokaz;
	}
	/**
	 * @return the gensonmaetainenz
	 */
	public Integer getGensonmaetainenz() {
		return gensonmaetainenz;
	}
	/**
	 * @param gensonmaetainenz the gensonmaetainenz to set
	 */
	public void setGensonmaetainenz(Integer gensonmaetainenz) {
		this.gensonmaetainenz = gensonmaetainenz;
	}
	/**
	 * @return the gensonmaeskkmmsuz
	 */
	public Integer getGensonmaeskkmmsuz() {
		return gensonmaeskkmmsuz;
	}
	/**
	 * @param gensonmaeskkmmsuz the gensonmaeskkmmsuz to set
	 */
	public void setGensonmaeskkmmsuz(Integer gensonmaeskkmmsuz) {
		this.gensonmaeskkmmsuz = gensonmaeskkmmsuz;
	}
	/**
	 * @return the gensonymdz
	 */
	public String getGensonymdz() {
		return gensonymdz;
	}
	/**
	 * @param gensonymdz the gensonymdz to set
	 */
	public void setGensonymdz(String gensonymdz) {
		this.gensonymdz = gensonymdz;
	}
	/**
	 * @return the genshistkgkz
	 */
	public Long getGenshistkgkz() {
		return genshistkgkz;
	}
	/**
	 * @param genshistkgkz the genshistkgkz to set
	 */
	public void setGenshistkgkz(Long genshistkgkz) {
		this.genshistkgkz = genshistkgkz;
	}
	/**
	 * @return the kaiteistkgkz
	 */
	public Long getKaiteistkgkz() {
		return kaiteistkgkz;
	}
	/**
	 * @param kaiteistkgkz the kaiteistkgkz to set
	 */
	public void setKaiteistkgkz(Long kaiteistkgkz) {
		this.kaiteistkgkz = kaiteistkgkz;
	}
	/**
	 * @return the kaiteitainenz
	 */
	public Integer getKaiteitainenz() {
		return kaiteitainenz;
	}
	/**
	 * @param kaiteitainenz the kaiteitainenz to set
	 */
	public void setKaiteitainenz(Integer kaiteitainenz) {
		this.kaiteitainenz = kaiteitainenz;
	}
	/**
	 * @return the kaiteiymdz
	 */
	public String getKaiteiymdz() {
		return kaiteiymdz;
	}
	/**
	 * @param kaiteiymdz the kaiteiymdz to set
	 */
	public void setKaiteiymdz(String kaiteiymdz) {
		this.kaiteiymdz = kaiteiymdz;
	}
	/**
	 * @return the yusenzogenkbnz
	 */
	public String getYusenzogenkbnz() {
		return yusenzogenkbnz;
	}
	/**
	 * @param yusenzogenkbnz the yusenzogenkbnz to set
	 */
	public void setYusenzogenkbnz(String yusenzogenkbnz) {
		this.yusenzogenkbnz = yusenzogenkbnz;
	}
	/**
	 * @return the togensonkbnL3
	 */
	public String getTogensonkbnL3() {
		return togensonkbnL3;
	}
	/**
	 * @param togensonkbnL3 the togensonkbnL3 to set
	 */
	public void setTogensonkbnL3(String togensonkbnL3) {
		this.togensonkbnL3 = togensonkbnL3;
	}
	/**
	 * @return the ksgensonruigkL3
	 */
	public Long getKsgensonruigkL3() {
		return ksgensonruigkL3;
	}
	/**
	 * @param ksgensonruigkL3 the ksgensonruigkL3 to set
	 */
	public void setKsgensonruigkL3(Long ksgensonruigkL3) {
		this.ksgensonruigkL3 = ksgensonruigkL3;
	}
	/**
	 * @return the togensongkL3
	 */
	public Long getTogensongkL3() {
		return togensongkL3;
	}
	/**
	 * @param togensongkL3 the togensongkL3 to set
	 */
	public void setTogensongkL3(Long togensongkL3) {
		this.togensongkL3 = togensongkL3;
	}
	/**
	 * @return the skkcalczanzongkL3
	 */
	public Long getSkkcalczanzongkL3() {
		return skkcalczanzongkL3;
	}
	/**
	 * @param skkcalczanzongkL3 the skkcalczanzongkL3 to set
	 */
	public void setSkkcalczanzongkL3(Long skkcalczanzongkL3) {
		this.skkcalczanzongkL3 = skkcalczanzongkL3;
	}
	/**
	 * @return the gensonbokaL3
	 */
	public Long getGensonbokaL3() {
		return gensonbokaL3;
	}
	/**
	 * @param gensonbokaL3 the gensonbokaL3 to set
	 */
	public void setGensonbokaL3(Long gensonbokaL3) {
		this.gensonbokaL3 = gensonbokaL3;
	}
	/**
	 * @return the gensonmaetainenL3
	 */
	public Integer getGensonmaetainenL3() {
		return gensonmaetainenL3;
	}
	/**
	 * @param gensonmaetainenL3 the gensonmaetainenL3 to set
	 */
	public void setGensonmaetainenL3(Integer gensonmaetainenL3) {
		this.gensonmaetainenL3 = gensonmaetainenL3;
	}
	/**
	 * @return the gensonmaeskkmmsuL3
	 */
	public Integer getGensonmaeskkmmsuL3() {
		return gensonmaeskkmmsuL3;
	}
	/**
	 * @param gensonmaeskkmmsuL3 the gensonmaeskkmmsuL3 to set
	 */
	public void setGensonmaeskkmmsuL3(Integer gensonmaeskkmmsuL3) {
		this.gensonmaeskkmmsuL3 = gensonmaeskkmmsuL3;
	}
	/**
	 * @return the gensonymdL3
	 */
	public String getGensonymdL3() {
		return gensonymdL3;
	}
	/**
	 * @param gensonymdL3 the gensonymdL3 to set
	 */
	public void setGensonymdL3(String gensonymdL3) {
		this.gensonymdL3 = gensonymdL3;
	}
	/**
	 * @return the genshistkgkL3
	 */
	public Long getGenshistkgkL3() {
		return genshistkgkL3;
	}
	/**
	 * @param genshistkgkL3 the genshistkgkL3 to set
	 */
	public void setGenshistkgkL3(Long genshistkgkL3) {
		this.genshistkgkL3 = genshistkgkL3;
	}
	/**
	 * @return the kaiteistkgkL3
	 */
	public Long getKaiteistkgkL3() {
		return kaiteistkgkL3;
	}
	/**
	 * @param kaiteistkgkL3 the kaiteistkgkL3 to set
	 */
	public void setKaiteistkgkL3(Long kaiteistkgkL3) {
		this.kaiteistkgkL3 = kaiteistkgkL3;
	}
	/**
	 * @return the kaiteitainenL3
	 */
	public Integer getKaiteitainenL3() {
		return kaiteitainenL3;
	}
	/**
	 * @param kaiteitainenL3 the kaiteitainenL3 to set
	 */
	public void setKaiteitainenL3(Integer kaiteitainenL3) {
		this.kaiteitainenL3 = kaiteitainenL3;
	}
	/**
	 * @return the kaiteiymdL3
	 */
	public String getKaiteiymdL3() {
		return kaiteiymdL3;
	}
	/**
	 * @param kaiteiymdL3 the kaiteiymdL3 to set
	 */
	public void setKaiteiymdL3(String kaiteiymdL3) {
		this.kaiteiymdL3 = kaiteiymdL3;
	}
	/**
	 * @return the yusenzogenkbnL3
	 */
	public String getYusenzogenkbnL3() {
		return yusenzogenkbnL3;
	}
	/**
	 * @param yusenzogenkbnL3 the yusenzogenkbnL3 to set
	 */
	public void setYusenzogenkbnL3(String yusenzogenkbnL3) {
		this.yusenzogenkbnL3 = yusenzogenkbnL3;
	}
	/**
	 * @return the togensonkbnL4
	 */
	public String getTogensonkbnL4() {
		return togensonkbnL4;
	}
	/**
	 * @param togensonkbnL4 the togensonkbnL4 to set
	 */
	public void setTogensonkbnL4(String togensonkbnL4) {
		this.togensonkbnL4 = togensonkbnL4;
	}
	/**
	 * @return the ksgensonruigkL4
	 */
	public Long getKsgensonruigkL4() {
		return ksgensonruigkL4;
	}
	/**
	 * @param ksgensonruigkL4 the ksgensonruigkL4 to set
	 */
	public void setKsgensonruigkL4(Long ksgensonruigkL4) {
		this.ksgensonruigkL4 = ksgensonruigkL4;
	}
	/**
	 * @return the togensongkL4
	 */
	public Long getTogensongkL4() {
		return togensongkL4;
	}
	/**
	 * @param togensongkL4 the togensongkL4 to set
	 */
	public void setTogensongkL4(Long togensongkL4) {
		this.togensongkL4 = togensongkL4;
	}
	/**
	 * @return the skkcalczanzongkL4
	 */
	public Long getSkkcalczanzongkL4() {
		return skkcalczanzongkL4;
	}
	/**
	 * @param skkcalczanzongkL4 the skkcalczanzongkL4 to set
	 */
	public void setSkkcalczanzongkL4(Long skkcalczanzongkL4) {
		this.skkcalczanzongkL4 = skkcalczanzongkL4;
	}
	/**
	 * @return the gensonbokaL4
	 */
	public Long getGensonbokaL4() {
		return gensonbokaL4;
	}
	/**
	 * @param gensonbokaL4 the gensonbokaL4 to set
	 */
	public void setGensonbokaL4(Long gensonbokaL4) {
		this.gensonbokaL4 = gensonbokaL4;
	}
	/**
	 * @return the gensonmaetainenL4
	 */
	public Integer getGensonmaetainenL4() {
		return gensonmaetainenL4;
	}
	/**
	 * @param gensonmaetainenL4 the gensonmaetainenL4 to set
	 */
	public void setGensonmaetainenL4(Integer gensonmaetainenL4) {
		this.gensonmaetainenL4 = gensonmaetainenL4;
	}
	/**
	 * @return the gensonmaeskkmmsuL4
	 */
	public Integer getGensonmaeskkmmsuL4() {
		return gensonmaeskkmmsuL4;
	}
	/**
	 * @param gensonmaeskkmmsuL4 the gensonmaeskkmmsuL4 to set
	 */
	public void setGensonmaeskkmmsuL4(Integer gensonmaeskkmmsuL4) {
		this.gensonmaeskkmmsuL4 = gensonmaeskkmmsuL4;
	}
	/**
	 * @return the gensonymdL4
	 */
	public String getGensonymdL4() {
		return gensonymdL4;
	}
	/**
	 * @param gensonymdL4 the gensonymdL4 to set
	 */
	public void setGensonymdL4(String gensonymdL4) {
		this.gensonymdL4 = gensonymdL4;
	}
	/**
	 * @return the genshistkgkL4
	 */
	public Long getGenshistkgkL4() {
		return genshistkgkL4;
	}
	/**
	 * @param genshistkgkL4 the genshistkgkL4 to set
	 */
	public void setGenshistkgkL4(Long genshistkgkL4) {
		this.genshistkgkL4 = genshistkgkL4;
	}
	/**
	 * @return the kaiteistkgkL4
	 */
	public Long getKaiteistkgkL4() {
		return kaiteistkgkL4;
	}
	/**
	 * @param kaiteistkgkL4 the kaiteistkgkL4 to set
	 */
	public void setKaiteistkgkL4(Long kaiteistkgkL4) {
		this.kaiteistkgkL4 = kaiteistkgkL4;
	}
	/**
	 * @return the kaiteitainenL4
	 */
	public Integer getKaiteitainenL4() {
		return kaiteitainenL4;
	}
	/**
	 * @param kaiteitainenL4 the kaiteitainenL4 to set
	 */
	public void setKaiteitainenL4(Integer kaiteitainenL4) {
		this.kaiteitainenL4 = kaiteitainenL4;
	}
	/**
	 * @return the kaiteiymdL4
	 */
	public String getKaiteiymdL4() {
		return kaiteiymdL4;
	}
	/**
	 * @param kaiteiymdL4 the kaiteiymdL4 to set
	 */
	public void setKaiteiymdL4(String kaiteiymdL4) {
		this.kaiteiymdL4 = kaiteiymdL4;
	}
	/**
	 * @return the yusenzogenkbnL4
	 */
	public String getYusenzogenkbnL4() {
		return yusenzogenkbnL4;
	}
	/**
	 * @param yusenzogenkbnL4 the yusenzogenkbnL4 to set
	 */
	public void setYusenzogenkbnL4(String yusenzogenkbnL4) {
		this.yusenzogenkbnL4 = yusenzogenkbnL4;
	}
	/**
	 * @return the togensonkbnL5
	 */
	public String getTogensonkbnL5() {
		return togensonkbnL5;
	}
	/**
	 * @param togensonkbnL5 the togensonkbnL5 to set
	 */
	public void setTogensonkbnL5(String togensonkbnL5) {
		this.togensonkbnL5 = togensonkbnL5;
	}
	/**
	 * @return the ksgensonruigkL5
	 */
	public Long getKsgensonruigkL5() {
		return ksgensonruigkL5;
	}
	/**
	 * @param ksgensonruigkL5 the ksgensonruigkL5 to set
	 */
	public void setKsgensonruigkL5(Long ksgensonruigkL5) {
		this.ksgensonruigkL5 = ksgensonruigkL5;
	}
	/**
	 * @return the togensongkL5
	 */
	public Long getTogensongkL5() {
		return togensongkL5;
	}
	/**
	 * @param togensongkL5 the togensongkL5 to set
	 */
	public void setTogensongkL5(Long togensongkL5) {
		this.togensongkL5 = togensongkL5;
	}
	/**
	 * @return the skkcalczanzongkL5
	 */
	public Long getSkkcalczanzongkL5() {
		return skkcalczanzongkL5;
	}
	/**
	 * @param skkcalczanzongkL5 the skkcalczanzongkL5 to set
	 */
	public void setSkkcalczanzongkL5(Long skkcalczanzongkL5) {
		this.skkcalczanzongkL5 = skkcalczanzongkL5;
	}
	/**
	 * @return the gensonbokaL5
	 */
	public Long getGensonbokaL5() {
		return gensonbokaL5;
	}
	/**
	 * @param gensonbokaL5 the gensonbokaL5 to set
	 */
	public void setGensonbokaL5(Long gensonbokaL5) {
		this.gensonbokaL5 = gensonbokaL5;
	}
	/**
	 * @return the gensonmaetainenL5
	 */
	public Integer getGensonmaetainenL5() {
		return gensonmaetainenL5;
	}
	/**
	 * @param gensonmaetainenL5 the gensonmaetainenL5 to set
	 */
	public void setGensonmaetainenL5(Integer gensonmaetainenL5) {
		this.gensonmaetainenL5 = gensonmaetainenL5;
	}
	/**
	 * @return the gensonmaeskkmmsuL5
	 */
	public Integer getGensonmaeskkmmsuL5() {
		return gensonmaeskkmmsuL5;
	}
	/**
	 * @param gensonmaeskkmmsuL5 the gensonmaeskkmmsuL5 to set
	 */
	public void setGensonmaeskkmmsuL5(Integer gensonmaeskkmmsuL5) {
		this.gensonmaeskkmmsuL5 = gensonmaeskkmmsuL5;
	}
	/**
	 * @return the gensonymdL5
	 */
	public String getGensonymdL5() {
		return gensonymdL5;
	}
	/**
	 * @param gensonymdL5 the gensonymdL5 to set
	 */
	public void setGensonymdL5(String gensonymdL5) {
		this.gensonymdL5 = gensonymdL5;
	}
	/**
	 * @return the genshistkgkL5
	 */
	public Long getGenshistkgkL5() {
		return genshistkgkL5;
	}
	/**
	 * @param genshistkgkL5 the genshistkgkL5 to set
	 */
	public void setGenshistkgkL5(Long genshistkgkL5) {
		this.genshistkgkL5 = genshistkgkL5;
	}
	/**
	 * @return the kaiteistkgkL5
	 */
	public Long getKaiteistkgkL5() {
		return kaiteistkgkL5;
	}
	/**
	 * @param kaiteistkgkL5 the kaiteistkgkL5 to set
	 */
	public void setKaiteistkgkL5(Long kaiteistkgkL5) {
		this.kaiteistkgkL5 = kaiteistkgkL5;
	}
	/**
	 * @return the kaiteitainenL5
	 */
	public Integer getKaiteitainenL5() {
		return kaiteitainenL5;
	}
	/**
	 * @param kaiteitainenL5 the kaiteitainenL5 to set
	 */
	public void setKaiteitainenL5(Integer kaiteitainenL5) {
		this.kaiteitainenL5 = kaiteitainenL5;
	}
	/**
	 * @return the kaiteiymdL5
	 */
	public String getKaiteiymdL5() {
		return kaiteiymdL5;
	}
	/**
	 * @param kaiteiymdL5 the kaiteiymdL5 to set
	 */
	public void setKaiteiymdL5(String kaiteiymdL5) {
		this.kaiteiymdL5 = kaiteiymdL5;
	}
	/**
	 * @return the yusenzogenkbnL5
	 */
	public String getYusenzogenkbnL5() {
		return yusenzogenkbnL5;
	}
	/**
	 * @param yusenzogenkbnL5 the yusenzogenkbnL5 to set
	 */
	public void setYusenzogenkbnL5(String yusenzogenkbnL5) {
		this.yusenzogenkbnL5 = yusenzogenkbnL5;
	}
	/**
	 * @return the togensonkbnL6
	 */
	public String getTogensonkbnL6() {
		return togensonkbnL6;
	}
	/**
	 * @param togensonkbnL6 the togensonkbnL6 to set
	 */
	public void setTogensonkbnL6(String togensonkbnL6) {
		this.togensonkbnL6 = togensonkbnL6;
	}
	/**
	 * @return the ksgensonruigkL6
	 */
	public Long getKsgensonruigkL6() {
		return ksgensonruigkL6;
	}
	/**
	 * @param ksgensonruigkL6 the ksgensonruigkL6 to set
	 */
	public void setKsgensonruigkL6(Long ksgensonruigkL6) {
		this.ksgensonruigkL6 = ksgensonruigkL6;
	}
	/**
	 * @return the togensongkL6
	 */
	public Long getTogensongkL6() {
		return togensongkL6;
	}
	/**
	 * @param togensongkL6 the togensongkL6 to set
	 */
	public void setTogensongkL6(Long togensongkL6) {
		this.togensongkL6 = togensongkL6;
	}
	/**
	 * @return the skkcalczanzongkL6
	 */
	public Long getSkkcalczanzongkL6() {
		return skkcalczanzongkL6;
	}
	/**
	 * @param skkcalczanzongkL6 the skkcalczanzongkL6 to set
	 */
	public void setSkkcalczanzongkL6(Long skkcalczanzongkL6) {
		this.skkcalczanzongkL6 = skkcalczanzongkL6;
	}
	/**
	 * @return the gensonbokaL6
	 */
	public Long getGensonbokaL6() {
		return gensonbokaL6;
	}
	/**
	 * @param gensonbokaL6 the gensonbokaL6 to set
	 */
	public void setGensonbokaL6(Long gensonbokaL6) {
		this.gensonbokaL6 = gensonbokaL6;
	}
	/**
	 * @return the gensonmaetainenL6
	 */
	public Integer getGensonmaetainenL6() {
		return gensonmaetainenL6;
	}
	/**
	 * @param gensonmaetainenL6 the gensonmaetainenL6 to set
	 */
	public void setGensonmaetainenL6(Integer gensonmaetainenL6) {
		this.gensonmaetainenL6 = gensonmaetainenL6;
	}
	/**
	 * @return the gensonmaeskkmmsuL6
	 */
	public Integer getGensonmaeskkmmsuL6() {
		return gensonmaeskkmmsuL6;
	}
	/**
	 * @param gensonmaeskkmmsuL6 the gensonmaeskkmmsuL6 to set
	 */
	public void setGensonmaeskkmmsuL6(Integer gensonmaeskkmmsuL6) {
		this.gensonmaeskkmmsuL6 = gensonmaeskkmmsuL6;
	}
	/**
	 * @return the gensonymdL6
	 */
	public String getGensonymdL6() {
		return gensonymdL6;
	}
	/**
	 * @param gensonymdL6 the gensonymdL6 to set
	 */
	public void setGensonymdL6(String gensonymdL6) {
		this.gensonymdL6 = gensonymdL6;
	}
	/**
	 * @return the genshistkgkL6
	 */
	public Long getGenshistkgkL6() {
		return genshistkgkL6;
	}
	/**
	 * @param genshistkgkL6 the genshistkgkL6 to set
	 */
	public void setGenshistkgkL6(Long genshistkgkL6) {
		this.genshistkgkL6 = genshistkgkL6;
	}
	/**
	 * @return the kaiteistkgkL6
	 */
	public Long getKaiteistkgkL6() {
		return kaiteistkgkL6;
	}
	/**
	 * @param kaiteistkgkL6 the kaiteistkgkL6 to set
	 */
	public void setKaiteistkgkL6(Long kaiteistkgkL6) {
		this.kaiteistkgkL6 = kaiteistkgkL6;
	}
	/**
	 * @return the kaiteitainenL6
	 */
	public Integer getKaiteitainenL6() {
		return kaiteitainenL6;
	}
	/**
	 * @param kaiteitainenL6 the kaiteitainenL6 to set
	 */
	public void setKaiteitainenL6(Integer kaiteitainenL6) {
		this.kaiteitainenL6 = kaiteitainenL6;
	}
	/**
	 * @return the kaiteiymdL6
	 */
	public String getKaiteiymdL6() {
		return kaiteiymdL6;
	}
	/**
	 * @param kaiteiymdL6 the kaiteiymdL6 to set
	 */
	public void setKaiteiymdL6(String kaiteiymdL6) {
		this.kaiteiymdL6 = kaiteiymdL6;
	}
	/**
	 * @return the yusenzogenkbnL6
	 */
	public String getYusenzogenkbnL6() {
		return yusenzogenkbnL6;
	}
	/**
	 * @param yusenzogenkbnL6 the yusenzogenkbnL6 to set
	 */
	public void setYusenzogenkbnL6(String yusenzogenkbnL6) {
		this.yusenzogenkbnL6 = yusenzogenkbnL6;
	}
	/**
	 * @return the updkaisu
	 */
	public Long getUpdkaisu() {
		return updkaisu;
	}
	/**
	 * @param updkaisu the updkaisu to set
	 */
	public void setUpdkaisu(Long updkaisu) {
		this.updkaisu = updkaisu;
	}
	/**
	 * @return the updkaishacd
	 */
	public String getUpdkaishacd() {
		return updkaishacd;
	}
	/**
	 * @param updkaishacd the updkaishacd to set
	 */
	public void setUpdkaishacd(String updkaishacd) {
		this.updkaishacd = updkaishacd;
	}
	/**
	 * @return the updid
	 */
	public String getUpdid() {
		return updid;
	}
	/**
	 * @param updid the updid to set
	 */
	public void setUpdid(String updid) {
		this.updid = updid;
	}
	/**
	 * @return the updymd
	 */
	public String getUpdymd() {
		return updymd;
	}
	/**
	 * @param updymd the updymd to set
	 */
	public void setUpdymd(String updymd) {
		this.updymd = updymd;
	}
	/**
	 * @return the updtime
	 */
	public String getUpdtime() {
		return updtime;
	}
	/**
	 * @param updtime the updtime to set
	 */
	public void setUpdtime(String updtime) {
		this.updtime = updtime;
	}
	/**
	 * @return the routeType
	 */
	public String getRouteType() {
		return routeType;
	}
	/**
	 * @param routeType the routeType to set
	 */
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}
	/**
	 * @return the routeTypeName
	 */
	public String getRouteTypeName() {
		return routeTypeName;
	}
	/**
	 * @param routeTypeName the routeTypeName to set
	 */
	public void setRouteTypeName(String routeTypeName) {
		this.routeTypeName = routeTypeName;
	}
	/**
	 * @return the purCompanyCode
	 */
	public String getPurCompanyCode() {
		return purCompanyCode;
	}
	/**
	 * @param purCompanyCode the purCompanyCode to set
	 */
	public void setPurCompanyCode(String purCompanyCode) {
		this.purCompanyCode = purCompanyCode;
	}
	/**
	 * @return the purCompanyName
	 */
	public String getPurCompanyName() {
		return purCompanyName;
	}
	/**
	 * @param purCompanyName the purCompanyName to set
	 */
	public void setPurCompanyName(String purCompanyName) {
		this.purCompanyName = purCompanyName;
	}
	/**
	 * @return the itemGroupCode
	 */
	public String getItemGroupCode() {
		return itemGroupCode;
	}
	/**
	 * @param itemGroupCode the itemGroupCode to set
	 */
	public void setItemGroupCode(String itemGroupCode) {
		this.itemGroupCode = itemGroupCode;
	}
	/**
	 * @return the itemGroupName
	 */
	public String getItemGroupName() {
		return itemGroupName;
	}
	/**
	 * @param itemGroupName the itemGroupName to set
	 */
	public void setItemGroupName(String itemGroupName) {
		this.itemGroupName = itemGroupName;
	}
	/**
	 * @return the manageSectionCode
	 */
	public String getManageSectionCode() {
		return manageSectionCode;
	}
	/**
	 * @param manageSectionCode the manageSectionCode to set
	 */
	public void setManageSectionCode(String manageSectionCode) {
		this.manageSectionCode = manageSectionCode;
	}
	/**
	 * @return the manageSectionName
	 */
	public String getManageSectionName() {
		return manageSectionName;
	}
	/**
	 * @param manageSectionName the manageSectionName to set
	 */
	public void setManageSectionName(String manageSectionName) {
		this.manageSectionName = manageSectionName;
	}
	/**
	 * @return the astPrjCode
	 */
	public String getAstPrjCode() {
		return astPrjCode;
	}
	/**
	 * @param astPrjCode the astPrjCode to set
	 */
	public void setAstPrjCode(String astPrjCode) {
		this.astPrjCode = astPrjCode;
	}
	/**
	 * @return the astPrjName
	 */
	public String getAstPrjName() {
		return astPrjName;
	}
	/**
	 * @param astPrjName the astPrjName to set
	 */
	public void setAstPrjName(String astPrjName) {
		this.astPrjName = astPrjName;
	}
	/**
	 * @return the depPrjCode
	 */
	public String getDepPrjCode() {
		return depPrjCode;
	}
	/**
	 * @param depPrjCode the depPrjCode to set
	 */
	public void setDepPrjCode(String depPrjCode) {
		this.depPrjCode = depPrjCode;
	}
	/**
	 * @return the depPrjName
	 */
	public String getDepPrjName() {
		return depPrjName;
	}
	/**
	 * @param depPrjName the depPrjName to set
	 */
	public void setDepPrjName(String depPrjName) {
		this.depPrjName = depPrjName;
	}
	/**
	 * @return the costType
	 */
	public String getCostType() {
		return costType;
	}
	/**
	 * @param costType the costType to set
	 */
	public void setCostType(String costType) {
		this.costType = costType;
	}
	/**
	 * @return the costTypeName
	 */
	public String getCostTypeName() {
		return costTypeName;
	}
	/**
	 * @param costTypeName the costTypeName to set
	 */
	public void setCostTypeName(String costTypeName) {
		this.costTypeName = costTypeName;
	}
	/**
	 * @return the applicationId
	 */
	public String getApplicationId() {
		return applicationId;
	}
	/**
	 * @param applicationId the applicationId to set
	 */
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	/**
	 * @return the slipNum
	 */
	public String getSlipNum() {
		return slipNum;
	}
	/**
	 * @param slipNum the slipNum to set
	 */
	public void setSlipNum(String slipNum) {
		this.slipNum = slipNum;
	}
	/**
	 * @return the interestFlag
	 */
	public String getInterestFlag() {
		return interestFlag;
	}
	/**
	 * @param interestFlag the interestFlag to set
	 */
	public void setInterestFlag(String interestFlag) {
		this.interestFlag = interestFlag;
	}
	/**
	 * @return the interestFlagName
	 */
	public String getInterestFlagName() {
		return interestFlagName;
	}
	/**
	 * @param interestFlagName the interestFlagName to set
	 */
	public void setInterestFlagName(String interestFlagName) {
		this.interestFlagName = interestFlagName;
	}
	/**
	 * @return the apStaff
	 */
	public String getApStaff() {
		return apStaff;
	}
	/**
	 * @param apStaff the apStaff to set
	 */
	public void setApStaff(String apStaff) {
		this.apStaff = apStaff;
	}
	/**
	 * @return the inventoryFlag
	 */
	public String getInventoryFlag() {
		return inventoryFlag;
	}
	/**
	 * @param inventoryFlag the inventoryFlag to set
	 */
	public void setInventoryFlag(String inventoryFlag) {
		this.inventoryFlag = inventoryFlag;
	}
	/**
	 * @return the inventoryFlagName
	 */
	public String getInventoryFlagName() {
		return inventoryFlagName;
	}
	/**
	 * @param inventoryFlagName the inventoryFlagName to set
	 */
	public void setInventoryFlagName(String inventoryFlagName) {
		this.inventoryFlagName = inventoryFlagName;
	}
	/**
	 * @return the disposePlanCode
	 */
	public String getDisposePlanCode() {
		return disposePlanCode;
	}
	/**
	 * @param disposePlanCode the disposePlanCode to set
	 */
	public void setDisposePlanCode(String disposePlanCode) {
		this.disposePlanCode = disposePlanCode;
	}
	/**
	 * @return the disposePlanName
	 */
	public String getDisposePlanName() {
		return disposePlanName;
	}
	/**
	 * @param disposePlanName the disposePlanName to set
	 */
	public void setDisposePlanName(String disposePlanName) {
		this.disposePlanName = disposePlanName;
	}
	/**
	 * @return the taxAdjustCode
	 */
	public String getTaxAdjustCode() {
		return taxAdjustCode;
	}
	/**
	 * @param taxAdjustCode the taxAdjustCode to set
	 */
	public void setTaxAdjustCode(String taxAdjustCode) {
		this.taxAdjustCode = taxAdjustCode;
	}
	/**
	 * @return the taxAdjustName
	 */
	public String getTaxAdjustName() {
		return taxAdjustName;
	}
	/**
	 * @param taxAdjustName the taxAdjustName to set
	 */
	public void setTaxAdjustName(String taxAdjustName) {
		this.taxAdjustName = taxAdjustName;
	}
	/**
	 * @return the contractBranchNum
	 */
	public String getContractBranchNum() {
		return contractBranchNum;
	}
	/**
	 * @param contractBranchNum the contractBranchNum to set
	 */
	public void setContractBranchNum(String contractBranchNum) {
		this.contractBranchNum = contractBranchNum;
	}
	/**
	 * @return the chargeType
	 */
	public String getChargeType() {
		return chargeType;
	}
	/**
	 * @param chargeType the chargeType to set
	 */
	public void setChargeType(String chargeType) {
		this.chargeType = chargeType;
	}
	/**
	 * @return the chargeTypeName
	 */
	public String getChargeTypeName() {
		return chargeTypeName;
	}
	/**
	 * @param chargeTypeName the chargeTypeName to set
	 */
	public void setChargeTypeName(String chargeTypeName) {
		this.chargeTypeName = chargeTypeName;
	}
	/**
	 * @return the disposeReasonCode
	 */
	public String getDisposeReasonCode() {
		return disposeReasonCode;
	}
	/**
	 * @param disposeReasonCode the disposeReasonCode to set
	 */
	public void setDisposeReasonCode(String disposeReasonCode) {
		this.disposeReasonCode = disposeReasonCode;
	}
	/**
	 * @return the disposeReasonName
	 */
	public String getDisposeReasonName() {
		return disposeReasonName;
	}
	/**
	 * @param disposeReasonName the disposeReasonName to set
	 */
	public void setDisposeReasonName(String disposeReasonName) {
		this.disposeReasonName = disposeReasonName;
	}
	/**
	 * @return the disposeSectionCode
	 */
	public String getDisposeSectionCode() {
		return disposeSectionCode;
	}
	/**
	 * @param disposeSectionCode the disposeSectionCode to set
	 */
	public void setDisposeSectionCode(String disposeSectionCode) {
		this.disposeSectionCode = disposeSectionCode;
	}
	/**
	 * @return the disposeSectionName
	 */
	public String getDisposeSectionName() {
		return disposeSectionName;
	}
	/**
	 * @param disposeSectionName the disposeSectionName to set
	 */
	public void setDisposeSectionName(String disposeSectionName) {
		this.disposeSectionName = disposeSectionName;
	}
	/**
	 * @return the calcType
	 */
	public String getCalcType() {
		return calcType;
	}
	/**
	 * @param calcType the calcType to set
	 */
	public void setCalcType(String calcType) {
		this.calcType = calcType;
	}
	/**
	 * @return the calcTypeName
	 */
	public String getCalcTypeName() {
		return calcTypeName;
	}
	/**
	 * @param calcTypeName the calcTypeName to set
	 */
	public void setCalcTypeName(String calcTypeName) {
		this.calcTypeName = calcTypeName;
	}
	/**
	 * @return the upperSectionCode
	 */
	public String getUpperSectionCode() {
		return upperSectionCode;
	}
	/**
	 * @param upperSectionCode the upperSectionCode to set
	 */
	public void setUpperSectionCode(String upperSectionCode) {
		this.upperSectionCode = upperSectionCode;
	}
	/**
	 * @return the upperSectionName
	 */
	public String getUpperSectionName() {
		return upperSectionName;
	}
	/**
	 * @param upperSectionName the upperSectionName to set
	 */
	public void setUpperSectionName(String upperSectionName) {
		this.upperSectionName = upperSectionName;
	}
	/**
	 * @return the addUpPlanDate
	 */
	public String getAddUpPlanDate() {
		return addUpPlanDate;
	}
	/**
	 * @param addUpPlanDate the addUpPlanDate to set
	 */
	public void setAddUpPlanDate(String addUpPlanDate) {
		this.addUpPlanDate = addUpPlanDate;
	}
	/**
	 * @return the addUpPlanDateName
	 */
	public String getAddUpPlanDateName() {
		return addUpPlanDateName;
	}
	/**
	 * @param addUpPlanDateName the addUpPlanDateName to set
	 */
	public void setAddUpPlanDateName(String addUpPlanDateName) {
		this.addUpPlanDateName = addUpPlanDateName;
	}
	/**
	 * @return the paTemplateCode
	 */
	public String getPaTemplateCode() {
		return paTemplateCode;
	}
	/**
	 * @param paTemplateCode the paTemplateCode to set
	 */
	public void setPaTemplateCode(String paTemplateCode) {
		this.paTemplateCode = paTemplateCode;
	}
	/**
	 * @return the paTemplateName
	 */
	public String getPaTemplateName() {
		return paTemplateName;
	}
	/**
	 * @param paTemplateName the paTemplateName to set
	 */
	public void setPaTemplateName(String paTemplateName) {
		this.paTemplateName = paTemplateName;
	}
	/**
	 * @return the upperAccountCode
	 */
	public String getUpperAccountCode() {
		return upperAccountCode;
	}
	/**
	 * @param upperAccountCode the upperAccountCode to set
	 */
	public void setUpperAccountCode(String upperAccountCode) {
		this.upperAccountCode = upperAccountCode;
	}
	/**
	 * @return the upperAccountName
	 */
	public String getUpperAccountName() {
		return upperAccountName;
	}
	/**
	 * @param upperAccountName the upperAccountName to set
	 */
	public void setUpperAccountName(String upperAccountName) {
		this.upperAccountName = upperAccountName;
	}
	/**
	 * @return the jgzzReconRef
	 */
	public String getJgzzReconRef() {
		return jgzzReconRef;
	}
	/**
	 * @param jgzzReconRef the jgzzReconRef to set
	 */
	public void setJgzzReconRef(String jgzzReconRef) {
		this.jgzzReconRef = jgzzReconRef;
	}
	/**
	 * @return the expenseType
	 */
	public String getExpenseType() {
		return expenseType;
	}
	/**
	 * @param expenseType the expenseType to set
	 */
	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}
	/**
	 * @return the expenseName
	 */
	public String getExpenseName() {
		return expenseName;
	}
	/**
	 * @param expenseName the expenseName to set
	 */
	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}
	/**
	 * @return the shisanknrkbnName
	 */
	public String getShisanknrkbnName() {
		return shisanknrkbnName;
	}
	/**
	 * @param shisanknrkbnName the shisanknrkbnName to set
	 */
	public void setShisanknrkbnName(String shisanknrkbnName) {
		this.shisanknrkbnName = shisanknrkbnName;
	}
	/**
	 * @return the shisanjotaikbnName
	 */
	public String getShisanjotaikbnName() {
		return shisanjotaikbnName;
	}
	/**
	 * @param shisanjotaikbnName the shisanjotaikbnName to set
	 */
	public void setShisanjotaikbnName(String shisanjotaikbnName) {
		this.shisanjotaikbnName = shisanjotaikbnName;
	}
	/**
	 * @return the togensonkbnkName
	 */
	public String getTogensonkbnkName() {
		return togensonkbnkName;
	}
	/**
	 * @param togensonkbnkName the togensonkbnkName to set
	 */
	public void setTogensonkbnkName(String togensonkbnkName) {
		this.togensonkbnkName = togensonkbnkName;
	}
	/**
	 * @return the koyunoR
	 */
	public Long getKoyunoR() {
		return koyunoR;
	}
	/**
	 * @param koyunoR the koyunoR to set
	 */
	public void setKoyunoR(Long koyunoR) {
		this.koyunoR = koyunoR;
	}
	/**
	 * @return the kaishacdR
	 */
	public String getKaishacdR() {
		return kaishacdR;
	}
	/**
	 * @param kaishacdR the kaishacdR to set
	 */
	public void setKaishacdR(String kaishacdR) {
		this.kaishacdR = kaishacdR;
	}
	/**
	 * @return the kaishanmR
	 */
	public String getKaishanmR() {
		return kaishanmR;
	}
	/**
	 * @param kaishanmR the kaishanmR to set
	 */
	public void setKaishanmR(String kaishanmR) {
		this.kaishanmR = kaishanmR;
	}
	/**
	 * @return the oyaR
	 */
	public String getOyaR() {
		return oyaR;
	}
	/**
	 * @param oyaR the oyaR to set
	 */
	public void setOyaR(String oyaR) {
		this.oyaR = oyaR;
	}
	/**
	 * @return the edaR
	 */
	public String getEdaR() {
		return edaR;
	}
	/**
	 * @param edaR the edaR to set
	 */
	public void setEdaR(String edaR) {
		this.edaR = edaR;
	}
	/**
	 * @return the shisanknrkbnR
	 */
	public String getShisanknrkbnR() {
		return shisanknrkbnR;
	}
	/**
	 * @param shisanknrkbnR the shisanknrkbnR to set
	 */
	public void setShisanknrkbnR(String shisanknrkbnR) {
		this.shisanknrkbnR = shisanknrkbnR;
	}
	/**
	 * @return the shisanjotaikbnR
	 */
	public String getShisanjotaikbnR() {
		return shisanjotaikbnR;
	}
	/**
	 * @param shisanjotaikbnR the shisanjotaikbnR to set
	 */
	public void setShisanjotaikbnR(String shisanjotaikbnR) {
		this.shisanjotaikbnR = shisanjotaikbnR;
	}
	/**
	 * @return the shisannmcdR
	 */
	public String getShisannmcdR() {
		return shisannmcdR;
	}
	/**
	 * @param shisannmcdR the shisannmcdR to set
	 */
	public void setShisannmcdR(String shisannmcdR) {
		this.shisannmcdR = shisannmcdR;
	}
	/**
	 * @return the shisannmR
	 */
	public String getShisannmR() {
		return shisannmR;
	}
	/**
	 * @param shisannmR the shisannmR to set
	 */
	public void setShisannmR(String shisannmR) {
		this.shisannmR = shisannmR;
	}
	/**
	 * @return the shisannmkR
	 */
	public String getShisannmkR() {
		return shisannmkR;
	}
	/**
	 * @param shisannmkR the shisannmkR to set
	 */
	public void setShisannmkR(String shisannmkR) {
		this.shisannmkR = shisannmkR;
	}
	/**
	 * @return the soshiki1cdR
	 */
	public String getSoshiki1cdR() {
		return soshiki1cdR;
	}
	/**
	 * @param soshiki1cdR the soshiki1cdR to set
	 */
	public void setSoshiki1cdR(String soshiki1cdR) {
		this.soshiki1cdR = soshiki1cdR;
	}
	/**
	 * @return the soshiki1nmR
	 */
	public String getSoshiki1nmR() {
		return soshiki1nmR;
	}
	/**
	 * @param soshiki1nmR the soshiki1nmR to set
	 */
	public void setSoshiki1nmR(String soshiki1nmR) {
		this.soshiki1nmR = soshiki1nmR;
	}
	/**
	 * @return the soshiki2cdR
	 */
	public String getSoshiki2cdR() {
		return soshiki2cdR;
	}
	/**
	 * @param soshiki2cdR the soshiki2cdR to set
	 */
	public void setSoshiki2cdR(String soshiki2cdR) {
		this.soshiki2cdR = soshiki2cdR;
	}
	/**
	 * @return the soshiki2nmR
	 */
	public String getSoshiki2nmR() {
		return soshiki2nmR;
	}
	/**
	 * @param soshiki2nmR the soshiki2nmR to set
	 */
	public void setSoshiki2nmR(String soshiki2nmR) {
		this.soshiki2nmR = soshiki2nmR;
	}
	/**
	 * @return the soshiki3cdR
	 */
	public String getSoshiki3cdR() {
		return soshiki3cdR;
	}
	/**
	 * @param soshiki3cdR the soshiki3cdR to set
	 */
	public void setSoshiki3cdR(String soshiki3cdR) {
		this.soshiki3cdR = soshiki3cdR;
	}
	/**
	 * @return the soshiki3nmR
	 */
	public String getSoshiki3nmR() {
		return soshiki3nmR;
	}
	/**
	 * @param soshiki3nmR the soshiki3nmR to set
	 */
	public void setSoshiki3nmR(String soshiki3nmR) {
		this.soshiki3nmR = soshiki3nmR;
	}
	/**
	 * @return the soshiki4cdR
	 */
	public String getSoshiki4cdR() {
		return soshiki4cdR;
	}
	/**
	 * @param soshiki4cdR the soshiki4cdR to set
	 */
	public void setSoshiki4cdR(String soshiki4cdR) {
		this.soshiki4cdR = soshiki4cdR;
	}
	/**
	 * @return the soshiki4nmR
	 */
	public String getSoshiki4nmR() {
		return soshiki4nmR;
	}
	/**
	 * @param soshiki4nmR the soshiki4nmR to set
	 */
	public void setSoshiki4nmR(String soshiki4nmR) {
		this.soshiki4nmR = soshiki4nmR;
	}
	/**
	 * @return the setchicdR
	 */
	public String getSetchicdR() {
		return setchicdR;
	}
	/**
	 * @param setchicdR the setchicdR to set
	 */
	public void setSetchicdR(String setchicdR) {
		this.setchicdR = setchicdR;
	}
	/**
	 * @return the setchinmR
	 */
	public String getSetchinmR() {
		return setchinmR;
	}
	/**
	 * @param setchinmR the setchinmR to set
	 */
	public void setSetchinmR(String setchinmR) {
		this.setchinmR = setchinmR;
	}
	/**
	 * @return the skkhihfcdR
	 */
	public String getSkkhihfcdR() {
		return skkhihfcdR;
	}
	/**
	 * @param skkhihfcdR the skkhihfcdR to set
	 */
	public void setSkkhihfcdR(String skkhihfcdR) {
		this.skkhihfcdR = skkhihfcdR;
	}
	/**
	 * @return the hfnmR
	 */
	public String getHfnmR() {
		return hfnmR;
	}
	/**
	 * @param hfnmR the hfnmR to set
	 */
	public void setHfnmR(String hfnmR) {
		this.hfnmR = hfnmR;
	}
	/**
	 * @return the saimuhfcdR
	 */
	public String getSaimuhfcdR() {
		return saimuhfcdR;
	}
	/**
	 * @param saimuhfcdR the saimuhfcdR to set
	 */
	public void setSaimuhfcdR(String saimuhfcdR) {
		this.saimuhfcdR = saimuhfcdR;
	}
	/**
	 * @return the saimuhfnmR
	 */
	public String getSaimuhfnmR() {
		return saimuhfnmR;
	}
	/**
	 * @param saimuhfnmR the saimuhfnmR to set
	 */
	public void setSaimuhfnmR(String saimuhfnmR) {
		this.saimuhfnmR = saimuhfnmR;
	}
	/**
	 * @return the shuruicdR
	 */
	public String getShuruicdR() {
		return shuruicdR;
	}
	/**
	 * @param shuruicdR the shuruicdR to set
	 */
	public void setShuruicdR(String shuruicdR) {
		this.shuruicdR = shuruicdR;
	}
	/**
	 * @return the shuruinmR
	 */
	public String getShuruinmR() {
		return shuruinmR;
	}
	/**
	 * @param shuruinmR the shuruinmR to set
	 */
	public void setShuruinmR(String shuruinmR) {
		this.shuruinmR = shuruinmR;
	}
	/**
	 * @return the kozocdR
	 */
	public String getKozocdR() {
		return kozocdR;
	}
	/**
	 * @param kozocdR the kozocdR to set
	 */
	public void setKozocdR(String kozocdR) {
		this.kozocdR = kozocdR;
	}
	/**
	 * @return the kozonmR
	 */
	public String getKozonmR() {
		return kozonmR;
	}
	/**
	 * @param kozonmR the kozonmR to set
	 */
	public void setKozonmR(String kozonmR) {
		this.kozonmR = kozonmR;
	}
	/**
	 * @return the bunruicdR
	 */
	public String getBunruicdR() {
		return bunruicdR;
	}
	/**
	 * @param bunruicdR the bunruicdR to set
	 */
	public void setBunruicdR(String bunruicdR) {
		this.bunruicdR = bunruicdR;
	}
	/**
	 * @return the bunruinmR
	 */
	public String getBunruinmR() {
		return bunruinmR;
	}
	/**
	 * @param bunruinmR the bunruinmR to set
	 */
	public void setBunruinmR(String bunruinmR) {
		this.bunruinmR = bunruinmR;
	}
	/**
	 * @return the saimukeijoymdR
	 */
	public String getSaimukeijoymdR() {
		return saimukeijoymdR;
	}
	/**
	 * @param saimukeijoymdR the saimukeijoymdR to set
	 */
	public void setSaimukeijoymdR(String saimukeijoymdR) {
		this.saimukeijoymdR = saimukeijoymdR;
	}
	/**
	 * @return the saimurikoymdR
	 */
	public String getSaimurikoymdR() {
		return saimurikoymdR;
	}
	/**
	 * @param saimurikoymdR the saimurikoymdR to set
	 */
	public void setSaimurikoymdR(String saimurikoymdR) {
		this.saimurikoymdR = saimurikoymdR;
	}
	/**
	 * @return the kadoymdR
	 */
	public String getKadoymdR() {
		return kadoymdR;
	}
	/**
	 * @param kadoymdR the kadoymdR to set
	 */
	public void setKadoymdR(String kadoymdR) {
		this.kadoymdR = kadoymdR;
	}
	/**
	 * @return the jbkymdR
	 */
	public String getJbkymdR() {
		return jbkymdR;
	}
	/**
	 * @param jbkymdR the jbkymdR to set
	 */
	public void setJbkymdR(String jbkymdR) {
		this.jbkymdR = jbkymdR;
	}
	/**
	 * @return the idoymdR
	 */
	public String getIdoymdR() {
		return idoymdR;
	}
	/**
	 * @param idoymdR the idoymdR to set
	 */
	public void setIdoymdR(String idoymdR) {
		this.idoymdR = idoymdR;
	}
	/**
	 * @return the suryoR
	 */
	public Integer getSuryoR() {
		return suryoR;
	}
	/**
	 * @param suryoR the suryoR to set
	 */
	public void setSuryoR(Integer suryoR) {
		this.suryoR = suryoR;
	}
	/**
	 * @return the suryoTaniR
	 */
	public String getSuryoTaniR() {
		return suryoTaniR;
	}
	/**
	 * @param suryoTaniR the suryoTaniR to set
	 */
	public void setSuryoTaniR(String suryoTaniR) {
		this.suryoTaniR = suryoTaniR;
	}
	/**
	 * @return the mensekiR
	 */
	public Double getMensekiR() {
		return mensekiR;
	}
	/**
	 * @param mensekiR the mensekiR to set
	 */
	public void setMensekiR(Double mensekiR) {
		this.mensekiR = mensekiR;
	}
	/**
	 * @return the mensekiTaniR
	 */
	public String getMensekiTaniR() {
		return mensekiTaniR;
	}
	/**
	 * @param mensekiTaniR the mensekiTaniR to set
	 */
	public void setMensekiTaniR(String mensekiTaniR) {
		this.mensekiTaniR = mensekiTaniR;
	}
	/**
	 * @return the tosaimukeijokbnR
	 */
	public String getTosaimukeijokbnR() {
		return tosaimukeijokbnR;
	}
	/**
	 * @param tosaimukeijokbnR the tosaimukeijokbnR to set
	 */
	public void setTosaimukeijokbnR(String tosaimukeijokbnR) {
		this.tosaimukeijokbnR = tosaimukeijokbnR;
	}
	/**
	 * @return the jksaimukeijokbnR
	 */
	public String getJksaimukeijokbnR() {
		return jksaimukeijokbnR;
	}
	/**
	 * @param jksaimukeijokbnR the jksaimukeijokbnR to set
	 */
	public void setJksaimukeijokbnR(String jksaimukeijokbnR) {
		this.jksaimukeijokbnR = jksaimukeijokbnR;
	}
	/**
	 * @return the mitsumorigkWaribikimaekR
	 */
	public Long getMitsumorigkWaribikimaekR() {
		return mitsumorigkWaribikimaekR;
	}
	/**
	 * @param mitsumorigkWaribikimaekR the mitsumorigkWaribikimaekR to set
	 */
	public void setMitsumorigkWaribikimaekR(Long mitsumorigkWaribikimaekR) {
		this.mitsumorigkWaribikimaekR = mitsumorigkWaribikimaekR;
	}
	/**
	 * @return the waribikiritsukR
	 */
	public Double getWaribikiritsukR() {
		return waribikiritsukR;
	}
	/**
	 * @param waribikiritsukR the waribikiritsukR to set
	 */
	public void setWaribikiritsukR(Double waribikiritsukR) {
		this.waribikiritsukR = waribikiritsukR;
	}
	/**
	 * @return the keijogkWaribikigokR
	 */
	public Long getKeijogkWaribikigokR() {
		return keijogkWaribikigokR;
	}
	/**
	 * @param keijogkWaribikigokR the keijogkWaribikigokR to set
	 */
	public void setKeijogkWaribikigokR(Long keijogkWaribikigokR) {
		this.keijogkWaribikigokR = keijogkWaribikigokR;
	}
	/**
	 * @return the kssaimubokakR
	 */
	public Long getKssaimubokakR() {
		return kssaimubokakR;
	}
	/**
	 * @param kssaimubokakR the kssaimubokakR to set
	 */
	public void setKssaimubokakR(Long kssaimubokakR) {
		this.kssaimubokakR = kssaimubokakR;
	}
	/**
	 * @return the risokucalckisogkkR
	 */
	public Long getRisokucalckisogkkR() {
		return risokucalckisogkkR;
	}
	/**
	 * @param risokucalckisogkkR the risokucalckisogkkR to set
	 */
	public void setRisokucalckisogkkR(Long risokucalckisogkkR) {
		this.risokucalckisogkkR = risokucalckisogkkR;
	}
	/**
	 * @return the toSaimubokakR
	 */
	public Long getToSaimubokakR() {
		return toSaimubokakR;
	}
	/**
	 * @param toSaimubokakR the toSaimubokakR to set
	 */
	public void setToSaimubokakR(Long toSaimubokakR) {
		this.toSaimubokakR = toSaimubokakR;
	}
	/**
	 * @return the toSaimuzogenbokakR
	 */
	public Long getToSaimuzogenbokakR() {
		return toSaimuzogenbokakR;
	}
	/**
	 * @param toSaimuzogenbokakR the toSaimuzogenbokakR to set
	 */
	public void setToSaimuzogenbokakR(Long toSaimuzogenbokakR) {
		this.toSaimuzogenbokakR = toSaimuzogenbokakR;
	}
	/**
	 * @return the toTorisokugkkR
	 */
	public Long getToTorisokugkkR() {
		return toTorisokugkkR;
	}
	/**
	 * @param toTorisokugkkR the toTorisokugkkR to set
	 */
	public void setToTorisokugkkR(Long toTorisokugkkR) {
		this.toTorisokugkkR = toTorisokugkkR;
	}
	/**
	 * @return the ksrisokuruigkkR
	 */
	public Long getKsrisokuruigkkR() {
		return ksrisokuruigkkR;
	}
	/**
	 * @param ksrisokuruigkkR the ksrisokuruigkkR to set
	 */
	public void setKsrisokuruigkkR(Long ksrisokuruigkkR) {
		this.ksrisokuruigkkR = ksrisokuruigkkR;
	}
	/**
	 * @return the mitsumorihkymdkR
	 */
	public String getMitsumorihkymdkR() {
		return mitsumorihkymdkR;
	}
	/**
	 * @param mitsumorihkymdkR the mitsumorihkymdkR to set
	 */
	public void setMitsumorihkymdkR(String mitsumorihkymdkR) {
		this.mitsumorihkymdkR = mitsumorihkymdkR;
	}
	/**
	 * @return the famitsumorihkymdkR
	 */
	public String getFamitsumorihkymdkR() {
		return famitsumorihkymdkR;
	}
	/**
	 * @param famitsumorihkymdkR the famitsumorihkymdkR to set
	 */
	public void setFamitsumorihkymdkR(String famitsumorihkymdkR) {
		this.famitsumorihkymdkR = famitsumorihkymdkR;
	}
	/**
	 * @return the mitsumorihkgobokakR
	 */
	public Long getMitsumorihkgobokakR() {
		return mitsumorihkgobokakR;
	}
	/**
	 * @param mitsumorihkgobokakR the mitsumorihkgobokakR to set
	 */
	public void setMitsumorihkgobokakR(Long mitsumorihkgobokakR) {
		this.mitsumorihkgobokakR = mitsumorihkgobokakR;
	}
	/**
	 * @return the mitsumorihkmaetainenkR
	 */
	public Integer getMitsumorihkmaetainenkR() {
		return mitsumorihkmaetainenkR;
	}
	/**
	 * @param mitsumorihkmaetainenkR the mitsumorihkmaetainenkR to set
	 */
	public void setMitsumorihkmaetainenkR(Integer mitsumorihkmaetainenkR) {
		this.mitsumorihkmaetainenkR = mitsumorihkmaetainenkR;
	}
	/**
	 * @return the mitsumorihkmaeskkmmsukR
	 */
	public Integer getMitsumorihkmaeskkmmsukR() {
		return mitsumorihkmaeskkmmsukR;
	}
	/**
	 * @param mitsumorihkmaeskkmmsukR the mitsumorihkmaeskkmmsukR to set
	 */
	public void setMitsumorihkmaeskkmmsukR(Integer mitsumorihkmaeskkmmsukR) {
		this.mitsumorihkmaeskkmmsukR = mitsumorihkmaeskkmmsukR;
	}
	/**
	 * @return the mitsumorihkmaeskkritsukR
	 */
	public Double getMitsumorihkmaeskkritsukR() {
		return mitsumorihkmaeskkritsukR;
	}
	/**
	 * @param mitsumorihkmaeskkritsukR the mitsumorihkmaeskkritsukR to set
	 */
	public void setMitsumorihkmaeskkritsukR(Double mitsumorihkmaeskkritsukR) {
		this.mitsumorihkmaeskkritsukR = mitsumorihkmaeskkritsukR;
	}
	/**
	 * @return the tomitsumorihkchoseigkkR
	 */
	public Long getTomitsumorihkchoseigkkR() {
		return tomitsumorihkchoseigkkR;
	}
	/**
	 * @param tomitsumorihkchoseigkkR the tomitsumorihkchoseigkkR to set
	 */
	public void setTomitsumorihkchoseigkkR(Long tomitsumorihkchoseigkkR) {
		this.tomitsumorihkchoseigkkR = tomitsumorihkchoseigkkR;
	}
	/**
	 * @return the stkgkkR
	 */
	public Long getStkgkkR() {
		return stkgkkR;
	}
	/**
	 * @param stkgkkR the stkgkkR to set
	 */
	public void setStkgkkR(Long stkgkkR) {
		this.stkgkkR = stkgkkR;
	}
	/**
	 * @return the skkhokR
	 */
	public String getSkkhokR() {
		return skkhokR;
	}
	/**
	 * @param skkhokR the skkhokR to set
	 */
	public void setSkkhokR(String skkhokR) {
		this.skkhokR = skkhokR;
	}
	/**
	 * @return the tainenkR
	 */
	public Integer getTainenkR() {
		return tainenkR;
	}
	/**
	 * @param tainenkR the tainenkR to set
	 */
	public void setTainenkR(Integer tainenkR) {
		this.tainenkR = tainenkR;
	}
	/**
	 * @return the skkritsukR
	 */
	public Double getSkkritsukR() {
		return skkritsukR;
	}
	/**
	 * @param skkritsukR the skkritsukR to set
	 */
	public void setSkkritsukR(Double skkritsukR) {
		this.skkritsukR = skkritsukR;
	}
	/**
	 * @return the skkmmsukR
	 */
	public Integer getSkkmmsukR() {
		return skkmmsukR;
	}
	/**
	 * @param skkmmsukR the skkmmsukR to set
	 */
	public void setSkkmmsukR(Integer skkmmsukR) {
		this.skkmmsukR = skkmmsukR;
	}
	/**
	 * @return the zanzonmmsukR
	 */
	public Integer getZanzonmmsukR() {
		return zanzonmmsukR;
	}
	/**
	 * @param zanzonmmsukR the zanzonmmsukR to set
	 */
	public void setZanzonmmsukR(Integer zanzonmmsukR) {
		this.zanzonmmsukR = zanzonmmsukR;
	}
	/**
	 * @return the ksbokakR
	 */
	public Long getKsbokakR() {
		return ksbokakR;
	}
	/**
	 * @param ksbokakR the ksbokakR to set
	 */
	public void setKsbokakR(Long ksbokakR) {
		this.ksbokakR = ksbokakR;
	}
	/**
	 * @return the skkcalckisogkkR
	 */
	public Long getSkkcalckisogkkR() {
		return skkcalckisogkkR;
	}
	/**
	 * @param skkcalckisogkkR the skkcalckisogkkR to set
	 */
	public void setSkkcalckisogkkR(Long skkcalckisogkkR) {
		this.skkcalckisogkkR = skkcalckisogkkR;
	}
	/**
	 * @return the zanzonritsukR
	 */
	public Double getZanzonritsukR() {
		return zanzonritsukR;
	}
	/**
	 * @param zanzonritsukR the zanzonritsukR to set
	 */
	public void setZanzonritsukR(Double zanzonritsukR) {
		this.zanzonritsukR = zanzonritsukR;
	}
	/**
	 * @return the zanzongkkR
	 */
	public Long getZanzongkkR() {
		return zanzongkkR;
	}
	/**
	 * @param zanzongkkR the zanzongkkR to set
	 */
	public void setZanzongkkR(Long zanzongkkR) {
		this.zanzongkkR = zanzongkkR;
	}
	/**
	 * @return the ksftskkruigkkR
	 */
	public Long getKsftskkruigkkR() {
		return ksftskkruigkkR;
	}
	/**
	 * @param ksftskkruigkkR the ksftskkruigkkR to set
	 */
	public void setKsftskkruigkkR(Long ksftskkruigkkR) {
		this.ksftskkruigkkR = ksftskkruigkkR;
	}
	/**
	 * @return the kszkskkruigkkR
	 */
	public Long getKszkskkruigkkR() {
		return kszkskkruigkkR;
	}
	/**
	 * @param kszkskkruigkkR the kszkskkruigkkR to set
	 */
	public void setKszkskkruigkkR(Long kszkskkruigkkR) {
		this.kszkskkruigkkR = kszkskkruigkkR;
	}
	/**
	 * @return the toToftskkgkkR
	 */
	public Long getToToftskkgkkR() {
		return toToftskkgkkR;
	}
	/**
	 * @param toToftskkgkkR the toToftskkgkkR to set
	 */
	public void setToToftskkgkkR(Long toToftskkgkkR) {
		this.toToftskkgkkR = toToftskkgkkR;
	}
	/**
	 * @return the toTozkskkgkkR
	 */
	public Long getToTozkskkgkkR() {
		return toTozkskkgkkR;
	}
	/**
	 * @param toTozkskkgkkR the toTozkskkgkkR to set
	 */
	public void setToTozkskkgkkR(Long toTozkskkgkkR) {
		this.toTozkskkgkkR = toTozkskkgkkR;
	}
	/**
	 * @return the toToniniskkgkkR
	 */
	public Long getToToniniskkgkkR() {
		return toToniniskkgkkR;
	}
	/**
	 * @param toToniniskkgkkR the toToniniskkgkkR to set
	 */
	public void setToToniniskkgkkR(Long toToniniskkgkkR) {
		this.toToniniskkgkkR = toToniniskkgkkR;
	}
	/**
	 * @return the toBokakR
	 */
	public Long getToBokakR() {
		return toBokakR;
	}
	/**
	 * @param toBokakR the toBokakR to set
	 */
	public void setToBokakR(Long toBokakR) {
		this.toBokakR = toBokakR;
	}
	/**
	 * @return the zogenbokakR
	 */
	public Long getZogenbokakR() {
		return zogenbokakR;
	}
	/**
	 * @param zogenbokakR the zogenbokakR to set
	 */
	public void setZogenbokakR(Long zogenbokakR) {
		this.zogenbokakR = zogenbokakR;
	}
	/**
	 * @return the zkskkritsukR
	 */
	public Double getZkskkritsukR() {
		return zkskkritsukR;
	}
	/**
	 * @param zkskkritsukR the zkskkritsukR to set
	 */
	public void setZkskkritsukR(Double zkskkritsukR) {
		this.zkskkritsukR = zkskkritsukR;
	}
	/**
	 * @return the yukyustymkR
	 */
	public String getYukyustymkR() {
		return yukyustymkR;
	}
	/**
	 * @param yukyustymkR the yukyustymkR to set
	 */
	public void setYukyustymkR(String yukyustymkR) {
		this.yukyustymkR = yukyustymkR;
	}
	/**
	 * @return the yukyufkymkR
	 */
	public String getYukyufkymkR() {
		return yukyufkymkR;
	}
	/**
	 * @param yukyufkymkR the yukyufkymkR to set
	 */
	public void setYukyufkymkR(String yukyufkymkR) {
		this.yukyufkymkR = yukyufkymkR;
	}
	/**
	 * @return the shonencalckbnkR
	 */
	public String getShonencalckbnkR() {
		return shonencalckbnkR;
	}
	/**
	 * @param shonencalckbnkR the shonencalckbnkR to set
	 */
	public void setShonencalckbnkR(String shonencalckbnkR) {
		this.shonencalckbnkR = shonencalckbnkR;
	}
	/**
	 * @return the skkkanryoflgkR
	 */
	public String getSkkkanryoflgkR() {
		return skkkanryoflgkR;
	}
	/**
	 * @param skkkanryoflgkR the skkkanryoflgkR to set
	 */
	public void setSkkkanryoflgkR(String skkkanryoflgkR) {
		this.skkkanryoflgkR = skkkanryoflgkR;
	}
	/**
	 * @return the skkkirikaeyykR
	 */
	public String getSkkkirikaeyykR() {
		return skkkirikaeyykR;
	}
	/**
	 * @param skkkirikaeyykR the skkkirikaeyykR to set
	 */
	public void setSkkkirikaeyykR(String skkkirikaeyykR) {
		this.skkkirikaeyykR = skkkirikaeyykR;
	}
	/**
	 * @return the zanzonskkstyykR
	 */
	public String getZanzonskkstyykR() {
		return zanzonskkstyykR;
	}
	/**
	 * @param zanzonskkstyykR the zanzonskkstyykR to set
	 */
	public void setZanzonskkstyykR(String zanzonskkstyykR) {
		this.zanzonskkstyykR = zanzonskkstyykR;
	}
	/**
	 * @return the mitsumorigkWaribikimaezR
	 */
	public Long getMitsumorigkWaribikimaezR() {
		return mitsumorigkWaribikimaezR;
	}
	/**
	 * @param mitsumorigkWaribikimaezR the mitsumorigkWaribikimaezR to set
	 */
	public void setMitsumorigkWaribikimaezR(Long mitsumorigkWaribikimaezR) {
		this.mitsumorigkWaribikimaezR = mitsumorigkWaribikimaezR;
	}
	/**
	 * @return the waribikiritsuzR
	 */
	public Double getWaribikiritsuzR() {
		return waribikiritsuzR;
	}
	/**
	 * @param waribikiritsuzR the waribikiritsuzR to set
	 */
	public void setWaribikiritsuzR(Double waribikiritsuzR) {
		this.waribikiritsuzR = waribikiritsuzR;
	}
	/**
	 * @return the keijogkWaribikigozR
	 */
	public Long getKeijogkWaribikigozR() {
		return keijogkWaribikigozR;
	}
	/**
	 * @param keijogkWaribikigozR the keijogkWaribikigozR to set
	 */
	public void setKeijogkWaribikigozR(Long keijogkWaribikigozR) {
		this.keijogkWaribikigozR = keijogkWaribikigozR;
	}
	/**
	 * @return the kssaimubokazR
	 */
	public Long getKssaimubokazR() {
		return kssaimubokazR;
	}
	/**
	 * @param kssaimubokazR the kssaimubokazR to set
	 */
	public void setKssaimubokazR(Long kssaimubokazR) {
		this.kssaimubokazR = kssaimubokazR;
	}
	/**
	 * @return the risokucalckisogkzR
	 */
	public Long getRisokucalckisogkzR() {
		return risokucalckisogkzR;
	}
	/**
	 * @param risokucalckisogkzR the risokucalckisogkzR to set
	 */
	public void setRisokucalckisogkzR(Long risokucalckisogkzR) {
		this.risokucalckisogkzR = risokucalckisogkzR;
	}
	/**
	 * @return the toSaimubokazR
	 */
	public Long getToSaimubokazR() {
		return toSaimubokazR;
	}
	/**
	 * @param toSaimubokazR the toSaimubokazR to set
	 */
	public void setToSaimubokazR(Long toSaimubokazR) {
		this.toSaimubokazR = toSaimubokazR;
	}
	/**
	 * @return the toSaimuzogenbokazR
	 */
	public Long getToSaimuzogenbokazR() {
		return toSaimuzogenbokazR;
	}
	/**
	 * @param toSaimuzogenbokazR the toSaimuzogenbokazR to set
	 */
	public void setToSaimuzogenbokazR(Long toSaimuzogenbokazR) {
		this.toSaimuzogenbokazR = toSaimuzogenbokazR;
	}
	/**
	 * @return the toTorisokugkzR
	 */
	public Long getToTorisokugkzR() {
		return toTorisokugkzR;
	}
	/**
	 * @param toTorisokugkzR the toTorisokugkzR to set
	 */
	public void setToTorisokugkzR(Long toTorisokugkzR) {
		this.toTorisokugkzR = toTorisokugkzR;
	}
	/**
	 * @return the ksrisokuruigkzR
	 */
	public Long getKsrisokuruigkzR() {
		return ksrisokuruigkzR;
	}
	/**
	 * @param ksrisokuruigkzR the ksrisokuruigkzR to set
	 */
	public void setKsrisokuruigkzR(Long ksrisokuruigkzR) {
		this.ksrisokuruigkzR = ksrisokuruigkzR;
	}
	/**
	 * @return the mitsumorihkymdzR
	 */
	public String getMitsumorihkymdzR() {
		return mitsumorihkymdzR;
	}
	/**
	 * @param mitsumorihkymdzR the mitsumorihkymdzR to set
	 */
	public void setMitsumorihkymdzR(String mitsumorihkymdzR) {
		this.mitsumorihkymdzR = mitsumorihkymdzR;
	}
	/**
	 * @return the famitsumorihkymdzR
	 */
	public String getFamitsumorihkymdzR() {
		return famitsumorihkymdzR;
	}
	/**
	 * @param famitsumorihkymdzR the famitsumorihkymdzR to set
	 */
	public void setFamitsumorihkymdzR(String famitsumorihkymdzR) {
		this.famitsumorihkymdzR = famitsumorihkymdzR;
	}
	/**
	 * @return the mitsumorihkgobokazR
	 */
	public Long getMitsumorihkgobokazR() {
		return mitsumorihkgobokazR;
	}
	/**
	 * @param mitsumorihkgobokazR the mitsumorihkgobokazR to set
	 */
	public void setMitsumorihkgobokazR(Long mitsumorihkgobokazR) {
		this.mitsumorihkgobokazR = mitsumorihkgobokazR;
	}
	/**
	 * @return the mitsumorihkmaetainenzR
	 */
	public Integer getMitsumorihkmaetainenzR() {
		return mitsumorihkmaetainenzR;
	}
	/**
	 * @param mitsumorihkmaetainenzR the mitsumorihkmaetainenzR to set
	 */
	public void setMitsumorihkmaetainenzR(Integer mitsumorihkmaetainenzR) {
		this.mitsumorihkmaetainenzR = mitsumorihkmaetainenzR;
	}
	/**
	 * @return the mitsumorihkmaeskkmmsuzR
	 */
	public Integer getMitsumorihkmaeskkmmsuzR() {
		return mitsumorihkmaeskkmmsuzR;
	}
	/**
	 * @param mitsumorihkmaeskkmmsuzR the mitsumorihkmaeskkmmsuzR to set
	 */
	public void setMitsumorihkmaeskkmmsuzR(Integer mitsumorihkmaeskkmmsuzR) {
		this.mitsumorihkmaeskkmmsuzR = mitsumorihkmaeskkmmsuzR;
	}
	/**
	 * @return the mitsumorihkmaeskkritsuzR
	 */
	public Double getMitsumorihkmaeskkritsuzR() {
		return mitsumorihkmaeskkritsuzR;
	}
	/**
	 * @param mitsumorihkmaeskkritsuzR the mitsumorihkmaeskkritsuzR to set
	 */
	public void setMitsumorihkmaeskkritsuzR(Double mitsumorihkmaeskkritsuzR) {
		this.mitsumorihkmaeskkritsuzR = mitsumorihkmaeskkritsuzR;
	}
	/**
	 * @return the tomitsumorihkchoseigkzR
	 */
	public Long getTomitsumorihkchoseigkzR() {
		return tomitsumorihkchoseigkzR;
	}
	/**
	 * @param tomitsumorihkchoseigkzR the tomitsumorihkchoseigkzR to set
	 */
	public void setTomitsumorihkchoseigkzR(Long tomitsumorihkchoseigkzR) {
		this.tomitsumorihkchoseigkzR = tomitsumorihkchoseigkzR;
	}
	/**
	 * @return the stkgkzR
	 */
	public Long getStkgkzR() {
		return stkgkzR;
	}
	/**
	 * @param stkgkzR the stkgkzR to set
	 */
	public void setStkgkzR(Long stkgkzR) {
		this.stkgkzR = stkgkzR;
	}
	/**
	 * @return the skkhozR
	 */
	public String getSkkhozR() {
		return skkhozR;
	}
	/**
	 * @param skkhozR the skkhozR to set
	 */
	public void setSkkhozR(String skkhozR) {
		this.skkhozR = skkhozR;
	}
	/**
	 * @return the tainenzR
	 */
	public Integer getTainenzR() {
		return tainenzR;
	}
	/**
	 * @param tainenzR the tainenzR to set
	 */
	public void setTainenzR(Integer tainenzR) {
		this.tainenzR = tainenzR;
	}
	/**
	 * @return the skkritsuzR
	 */
	public Double getSkkritsuzR() {
		return skkritsuzR;
	}
	/**
	 * @param skkritsuzR the skkritsuzR to set
	 */
	public void setSkkritsuzR(Double skkritsuzR) {
		this.skkritsuzR = skkritsuzR;
	}
	/**
	 * @return the skkmmsuzR
	 */
	public Integer getSkkmmsuzR() {
		return skkmmsuzR;
	}
	/**
	 * @param skkmmsuzR the skkmmsuzR to set
	 */
	public void setSkkmmsuzR(Integer skkmmsuzR) {
		this.skkmmsuzR = skkmmsuzR;
	}
	/**
	 * @return the zanzonmmsuzR
	 */
	public Integer getZanzonmmsuzR() {
		return zanzonmmsuzR;
	}
	/**
	 * @param zanzonmmsuzR the zanzonmmsuzR to set
	 */
	public void setZanzonmmsuzR(Integer zanzonmmsuzR) {
		this.zanzonmmsuzR = zanzonmmsuzR;
	}
	/**
	 * @return the ksbokazR
	 */
	public Long getKsbokazR() {
		return ksbokazR;
	}
	/**
	 * @param ksbokazR the ksbokazR to set
	 */
	public void setKsbokazR(Long ksbokazR) {
		this.ksbokazR = ksbokazR;
	}
	/**
	 * @return the skkcalckisogkzR
	 */
	public Long getSkkcalckisogkzR() {
		return skkcalckisogkzR;
	}
	/**
	 * @param skkcalckisogkzR the skkcalckisogkzR to set
	 */
	public void setSkkcalckisogkzR(Long skkcalckisogkzR) {
		this.skkcalckisogkzR = skkcalckisogkzR;
	}
	/**
	 * @return the zanzonritsuzR
	 */
	public Double getZanzonritsuzR() {
		return zanzonritsuzR;
	}
	/**
	 * @param zanzonritsuzR the zanzonritsuzR to set
	 */
	public void setZanzonritsuzR(Double zanzonritsuzR) {
		this.zanzonritsuzR = zanzonritsuzR;
	}
	/**
	 * @return the zanzongkzR
	 */
	public Long getZanzongkzR() {
		return zanzongkzR;
	}
	/**
	 * @param zanzongkzR the zanzongkzR to set
	 */
	public void setZanzongkzR(Long zanzongkzR) {
		this.zanzongkzR = zanzongkzR;
	}
	/**
	 * @return the ksftskkruigkzR
	 */
	public Long getKsftskkruigkzR() {
		return ksftskkruigkzR;
	}
	/**
	 * @param ksftskkruigkzR the ksftskkruigkzR to set
	 */
	public void setKsftskkruigkzR(Long ksftskkruigkzR) {
		this.ksftskkruigkzR = ksftskkruigkzR;
	}
	/**
	 * @return the kszkskkruigkzR
	 */
	public Long getKszkskkruigkzR() {
		return kszkskkruigkzR;
	}
	/**
	 * @param kszkskkruigkzR the kszkskkruigkzR to set
	 */
	public void setKszkskkruigkzR(Long kszkskkruigkzR) {
		this.kszkskkruigkzR = kszkskkruigkzR;
	}
	/**
	 * @return the toToftskkgkzR
	 */
	public Long getToToftskkgkzR() {
		return toToftskkgkzR;
	}
	/**
	 * @param toToftskkgkzR the toToftskkgkzR to set
	 */
	public void setToToftskkgkzR(Long toToftskkgkzR) {
		this.toToftskkgkzR = toToftskkgkzR;
	}
	/**
	 * @return the toTozkskkgkzR
	 */
	public Long getToTozkskkgkzR() {
		return toTozkskkgkzR;
	}
	/**
	 * @param toTozkskkgkzR the toTozkskkgkzR to set
	 */
	public void setToTozkskkgkzR(Long toTozkskkgkzR) {
		this.toTozkskkgkzR = toTozkskkgkzR;
	}
	/**
	 * @return the toToniniskkgkzR
	 */
	public Long getToToniniskkgkzR() {
		return toToniniskkgkzR;
	}
	/**
	 * @param toToniniskkgkzR the toToniniskkgkzR to set
	 */
	public void setToToniniskkgkzR(Long toToniniskkgkzR) {
		this.toToniniskkgkzR = toToniniskkgkzR;
	}
	/**
	 * @return the toBokazR
	 */
	public Long getToBokazR() {
		return toBokazR;
	}
	/**
	 * @param toBokazR the toBokazR to set
	 */
	public void setToBokazR(Long toBokazR) {
		this.toBokazR = toBokazR;
	}
	/**
	 * @return the zogenbokazR
	 */
	public Long getZogenbokazR() {
		return zogenbokazR;
	}
	/**
	 * @param zogenbokazR the zogenbokazR to set
	 */
	public void setZogenbokazR(Long zogenbokazR) {
		this.zogenbokazR = zogenbokazR;
	}
	/**
	 * @return the zkskkritsuzR
	 */
	public Double getZkskkritsuzR() {
		return zkskkritsuzR;
	}
	/**
	 * @param zkskkritsuzR the zkskkritsuzR to set
	 */
	public void setZkskkritsuzR(Double zkskkritsuzR) {
		this.zkskkritsuzR = zkskkritsuzR;
	}
	/**
	 * @return the yukyustymzR
	 */
	public String getYukyustymzR() {
		return yukyustymzR;
	}
	/**
	 * @param yukyustymzR the yukyustymzR to set
	 */
	public void setYukyustymzR(String yukyustymzR) {
		this.yukyustymzR = yukyustymzR;
	}
	/**
	 * @return the yukyufkymzR
	 */
	public String getYukyufkymzR() {
		return yukyufkymzR;
	}
	/**
	 * @param yukyufkymzR the yukyufkymzR to set
	 */
	public void setYukyufkymzR(String yukyufkymzR) {
		this.yukyufkymzR = yukyufkymzR;
	}
	/**
	 * @return the shonencalckbnzR
	 */
	public String getShonencalckbnzR() {
		return shonencalckbnzR;
	}
	/**
	 * @param shonencalckbnzR the shonencalckbnzR to set
	 */
	public void setShonencalckbnzR(String shonencalckbnzR) {
		this.shonencalckbnzR = shonencalckbnzR;
	}
	/**
	 * @return the skkkanryoflgzR
	 */
	public String getSkkkanryoflgzR() {
		return skkkanryoflgzR;
	}
	/**
	 * @param skkkanryoflgzR the skkkanryoflgzR to set
	 */
	public void setSkkkanryoflgzR(String skkkanryoflgzR) {
		this.skkkanryoflgzR = skkkanryoflgzR;
	}
	/**
	 * @return the skkkirikaeyyzR
	 */
	public String getSkkkirikaeyyzR() {
		return skkkirikaeyyzR;
	}
	/**
	 * @param skkkirikaeyyzR the skkkirikaeyyzR to set
	 */
	public void setSkkkirikaeyyzR(String skkkirikaeyyzR) {
		this.skkkirikaeyyzR = skkkirikaeyyzR;
	}
	/**
	 * @return the zanzonskkstyyzR
	 */
	public String getZanzonskkstyyzR() {
		return zanzonskkstyyzR;
	}
	/**
	 * @param zanzonskkstyyzR the zanzonskkstyyzR to set
	 */
	public void setZanzonskkstyyzR(String zanzonskkstyyzR) {
		this.zanzonskkstyyzR = zanzonskkstyyzR;
	}
	/**
	 * @return the mitsumorigkWaribikimaeL3R
	 */
	public Long getMitsumorigkWaribikimaeL3R() {
		return mitsumorigkWaribikimaeL3R;
	}
	/**
	 * @param mitsumorigkWaribikimaeL3R the mitsumorigkWaribikimaeL3R to set
	 */
	public void setMitsumorigkWaribikimaeL3R(Long mitsumorigkWaribikimaeL3R) {
		this.mitsumorigkWaribikimaeL3R = mitsumorigkWaribikimaeL3R;
	}
	/**
	 * @return the waribikiritsuL3R
	 */
	public Double getWaribikiritsuL3R() {
		return waribikiritsuL3R;
	}
	/**
	 * @param waribikiritsuL3R the waribikiritsuL3R to set
	 */
	public void setWaribikiritsuL3R(Double waribikiritsuL3R) {
		this.waribikiritsuL3R = waribikiritsuL3R;
	}
	/**
	 * @return the keijogkWaribikigoL3R
	 */
	public Long getKeijogkWaribikigoL3R() {
		return keijogkWaribikigoL3R;
	}
	/**
	 * @param keijogkWaribikigoL3R the keijogkWaribikigoL3R to set
	 */
	public void setKeijogkWaribikigoL3R(Long keijogkWaribikigoL3R) {
		this.keijogkWaribikigoL3R = keijogkWaribikigoL3R;
	}
	/**
	 * @return the kssaimubokaL3R
	 */
	public Long getKssaimubokaL3R() {
		return kssaimubokaL3R;
	}
	/**
	 * @param kssaimubokaL3R the kssaimubokaL3R to set
	 */
	public void setKssaimubokaL3R(Long kssaimubokaL3R) {
		this.kssaimubokaL3R = kssaimubokaL3R;
	}
	/**
	 * @return the risokucalckisogkL3R
	 */
	public Long getRisokucalckisogkL3R() {
		return risokucalckisogkL3R;
	}
	/**
	 * @param risokucalckisogkL3R the risokucalckisogkL3R to set
	 */
	public void setRisokucalckisogkL3R(Long risokucalckisogkL3R) {
		this.risokucalckisogkL3R = risokucalckisogkL3R;
	}
	/**
	 * @return the toSaimubokaL3R
	 */
	public Long getToSaimubokaL3R() {
		return toSaimubokaL3R;
	}
	/**
	 * @param toSaimubokaL3R the toSaimubokaL3R to set
	 */
	public void setToSaimubokaL3R(Long toSaimubokaL3R) {
		this.toSaimubokaL3R = toSaimubokaL3R;
	}
	/**
	 * @return the toSaimuzogenbokaL3R
	 */
	public Long getToSaimuzogenbokaL3R() {
		return toSaimuzogenbokaL3R;
	}
	/**
	 * @param toSaimuzogenbokaL3R the toSaimuzogenbokaL3R to set
	 */
	public void setToSaimuzogenbokaL3R(Long toSaimuzogenbokaL3R) {
		this.toSaimuzogenbokaL3R = toSaimuzogenbokaL3R;
	}
	/**
	 * @return the toTorisokugkL3R
	 */
	public Long getToTorisokugkL3R() {
		return toTorisokugkL3R;
	}
	/**
	 * @param toTorisokugkL3R the toTorisokugkL3R to set
	 */
	public void setToTorisokugkL3R(Long toTorisokugkL3R) {
		this.toTorisokugkL3R = toTorisokugkL3R;
	}
	/**
	 * @return the ksrisokuruigkL3R
	 */
	public Long getKsrisokuruigkL3R() {
		return ksrisokuruigkL3R;
	}
	/**
	 * @param ksrisokuruigkL3R the ksrisokuruigkL3R to set
	 */
	public void setKsrisokuruigkL3R(Long ksrisokuruigkL3R) {
		this.ksrisokuruigkL3R = ksrisokuruigkL3R;
	}
	/**
	 * @return the mitsumorihkymdL3R
	 */
	public String getMitsumorihkymdL3R() {
		return mitsumorihkymdL3R;
	}
	/**
	 * @param mitsumorihkymdL3R the mitsumorihkymdL3R to set
	 */
	public void setMitsumorihkymdL3R(String mitsumorihkymdL3R) {
		this.mitsumorihkymdL3R = mitsumorihkymdL3R;
	}
	/**
	 * @return the famitsumorihkymdL3R
	 */
	public String getFamitsumorihkymdL3R() {
		return famitsumorihkymdL3R;
	}
	/**
	 * @param famitsumorihkymdL3R the famitsumorihkymdL3R to set
	 */
	public void setFamitsumorihkymdL3R(String famitsumorihkymdL3R) {
		this.famitsumorihkymdL3R = famitsumorihkymdL3R;
	}
	/**
	 * @return the mitsumorihkgobokaL3R
	 */
	public Long getMitsumorihkgobokaL3R() {
		return mitsumorihkgobokaL3R;
	}
	/**
	 * @param mitsumorihkgobokaL3R the mitsumorihkgobokaL3R to set
	 */
	public void setMitsumorihkgobokaL3R(Long mitsumorihkgobokaL3R) {
		this.mitsumorihkgobokaL3R = mitsumorihkgobokaL3R;
	}
	/**
	 * @return the mitsumorihkmaetainenL3R
	 */
	public Integer getMitsumorihkmaetainenL3R() {
		return mitsumorihkmaetainenL3R;
	}
	/**
	 * @param mitsumorihkmaetainenL3R the mitsumorihkmaetainenL3R to set
	 */
	public void setMitsumorihkmaetainenL3R(Integer mitsumorihkmaetainenL3R) {
		this.mitsumorihkmaetainenL3R = mitsumorihkmaetainenL3R;
	}
	/**
	 * @return the mitsumorihkmaeskkmmsuL3R
	 */
	public Integer getMitsumorihkmaeskkmmsuL3R() {
		return mitsumorihkmaeskkmmsuL3R;
	}
	/**
	 * @param mitsumorihkmaeskkmmsuL3R the mitsumorihkmaeskkmmsuL3R to set
	 */
	public void setMitsumorihkmaeskkmmsuL3R(Integer mitsumorihkmaeskkmmsuL3R) {
		this.mitsumorihkmaeskkmmsuL3R = mitsumorihkmaeskkmmsuL3R;
	}
	/**
	 * @return the mitsumorihkmaeskkritsuL3R
	 */
	public Double getMitsumorihkmaeskkritsuL3R() {
		return mitsumorihkmaeskkritsuL3R;
	}
	/**
	 * @param mitsumorihkmaeskkritsuL3R the mitsumorihkmaeskkritsuL3R to set
	 */
	public void setMitsumorihkmaeskkritsuL3R(Double mitsumorihkmaeskkritsuL3R) {
		this.mitsumorihkmaeskkritsuL3R = mitsumorihkmaeskkritsuL3R;
	}
	/**
	 * @return the tomitsumorihkchoseigkL3R
	 */
	public Long getTomitsumorihkchoseigkL3R() {
		return tomitsumorihkchoseigkL3R;
	}
	/**
	 * @param tomitsumorihkchoseigkL3R the tomitsumorihkchoseigkL3R to set
	 */
	public void setTomitsumorihkchoseigkL3R(Long tomitsumorihkchoseigkL3R) {
		this.tomitsumorihkchoseigkL3R = tomitsumorihkchoseigkL3R;
	}
	/**
	 * @return the stkgkL3R
	 */
	public Long getStkgkL3R() {
		return stkgkL3R;
	}
	/**
	 * @param stkgkL3R the stkgkL3R to set
	 */
	public void setStkgkL3R(Long stkgkL3R) {
		this.stkgkL3R = stkgkL3R;
	}
	/**
	 * @return the skkhoL3R
	 */
	public String getSkkhoL3R() {
		return skkhoL3R;
	}
	/**
	 * @param skkhoL3R the skkhoL3R to set
	 */
	public void setSkkhoL3R(String skkhoL3R) {
		this.skkhoL3R = skkhoL3R;
	}
	/**
	 * @return the tainenL3R
	 */
	public Integer getTainenL3R() {
		return tainenL3R;
	}
	/**
	 * @param tainenL3R the tainenL3R to set
	 */
	public void setTainenL3R(Integer tainenL3R) {
		this.tainenL3R = tainenL3R;
	}
	/**
	 * @return the skkritsuL3R
	 */
	public Double getSkkritsuL3R() {
		return skkritsuL3R;
	}
	/**
	 * @param skkritsuL3R the skkritsuL3R to set
	 */
	public void setSkkritsuL3R(Double skkritsuL3R) {
		this.skkritsuL3R = skkritsuL3R;
	}
	/**
	 * @return the skkmmsuL3R
	 */
	public Integer getSkkmmsuL3R() {
		return skkmmsuL3R;
	}
	/**
	 * @param skkmmsuL3R the skkmmsuL3R to set
	 */
	public void setSkkmmsuL3R(Integer skkmmsuL3R) {
		this.skkmmsuL3R = skkmmsuL3R;
	}
	/**
	 * @return the zanzonmmsuL3R
	 */
	public Integer getZanzonmmsuL3R() {
		return zanzonmmsuL3R;
	}
	/**
	 * @param zanzonmmsuL3R the zanzonmmsuL3R to set
	 */
	public void setZanzonmmsuL3R(Integer zanzonmmsuL3R) {
		this.zanzonmmsuL3R = zanzonmmsuL3R;
	}
	/**
	 * @return the ksbokaL3R
	 */
	public Long getKsbokaL3R() {
		return ksbokaL3R;
	}
	/**
	 * @param ksbokaL3R the ksbokaL3R to set
	 */
	public void setKsbokaL3R(Long ksbokaL3R) {
		this.ksbokaL3R = ksbokaL3R;
	}
	/**
	 * @return the skkcalckisogkL3R
	 */
	public Long getSkkcalckisogkL3R() {
		return skkcalckisogkL3R;
	}
	/**
	 * @param skkcalckisogkL3R the skkcalckisogkL3R to set
	 */
	public void setSkkcalckisogkL3R(Long skkcalckisogkL3R) {
		this.skkcalckisogkL3R = skkcalckisogkL3R;
	}
	/**
	 * @return the zanzonritsuL3R
	 */
	public Double getZanzonritsuL3R() {
		return zanzonritsuL3R;
	}
	/**
	 * @param zanzonritsuL3R the zanzonritsuL3R to set
	 */
	public void setZanzonritsuL3R(Double zanzonritsuL3R) {
		this.zanzonritsuL3R = zanzonritsuL3R;
	}
	/**
	 * @return the zanzongkL3R
	 */
	public Long getZanzongkL3R() {
		return zanzongkL3R;
	}
	/**
	 * @param zanzongkL3R the zanzongkL3R to set
	 */
	public void setZanzongkL3R(Long zanzongkL3R) {
		this.zanzongkL3R = zanzongkL3R;
	}
	/**
	 * @return the ksftskkruigkL3R
	 */
	public Long getKsftskkruigkL3R() {
		return ksftskkruigkL3R;
	}
	/**
	 * @param ksftskkruigkL3R the ksftskkruigkL3R to set
	 */
	public void setKsftskkruigkL3R(Long ksftskkruigkL3R) {
		this.ksftskkruigkL3R = ksftskkruigkL3R;
	}
	/**
	 * @return the kszkskkruigkL3R
	 */
	public Long getKszkskkruigkL3R() {
		return kszkskkruigkL3R;
	}
	/**
	 * @param kszkskkruigkL3R the kszkskkruigkL3R to set
	 */
	public void setKszkskkruigkL3R(Long kszkskkruigkL3R) {
		this.kszkskkruigkL3R = kszkskkruigkL3R;
	}
	/**
	 * @return the toToftskkgkL3R
	 */
	public Long getToToftskkgkL3R() {
		return toToftskkgkL3R;
	}
	/**
	 * @param toToftskkgkL3R the toToftskkgkL3R to set
	 */
	public void setToToftskkgkL3R(Long toToftskkgkL3R) {
		this.toToftskkgkL3R = toToftskkgkL3R;
	}
	/**
	 * @return the toTozkskkgkL3R
	 */
	public Long getToTozkskkgkL3R() {
		return toTozkskkgkL3R;
	}
	/**
	 * @param toTozkskkgkL3R the toTozkskkgkL3R to set
	 */
	public void setToTozkskkgkL3R(Long toTozkskkgkL3R) {
		this.toTozkskkgkL3R = toTozkskkgkL3R;
	}
	/**
	 * @return the toToniniskkgkL3R
	 */
	public Long getToToniniskkgkL3R() {
		return toToniniskkgkL3R;
	}
	/**
	 * @param toToniniskkgkL3R the toToniniskkgkL3R to set
	 */
	public void setToToniniskkgkL3R(Long toToniniskkgkL3R) {
		this.toToniniskkgkL3R = toToniniskkgkL3R;
	}
	/**
	 * @return the toBokaL3R
	 */
	public Long getToBokaL3R() {
		return toBokaL3R;
	}
	/**
	 * @param toBokaL3R the toBokaL3R to set
	 */
	public void setToBokaL3R(Long toBokaL3R) {
		this.toBokaL3R = toBokaL3R;
	}
	/**
	 * @return the zogenbokaL3R
	 */
	public Long getZogenbokaL3R() {
		return zogenbokaL3R;
	}
	/**
	 * @param zogenbokaL3R the zogenbokaL3R to set
	 */
	public void setZogenbokaL3R(Long zogenbokaL3R) {
		this.zogenbokaL3R = zogenbokaL3R;
	}
	/**
	 * @return the zkskkritsuL3R
	 */
	public Double getZkskkritsuL3R() {
		return zkskkritsuL3R;
	}
	/**
	 * @param zkskkritsuL3R the zkskkritsuL3R to set
	 */
	public void setZkskkritsuL3R(Double zkskkritsuL3R) {
		this.zkskkritsuL3R = zkskkritsuL3R;
	}
	/**
	 * @return the yukyustymL3R
	 */
	public String getYukyustymL3R() {
		return yukyustymL3R;
	}
	/**
	 * @param yukyustymL3R the yukyustymL3R to set
	 */
	public void setYukyustymL3R(String yukyustymL3R) {
		this.yukyustymL3R = yukyustymL3R;
	}
	/**
	 * @return the yukyufkymL3R
	 */
	public String getYukyufkymL3R() {
		return yukyufkymL3R;
	}
	/**
	 * @param yukyufkymL3R the yukyufkymL3R to set
	 */
	public void setYukyufkymL3R(String yukyufkymL3R) {
		this.yukyufkymL3R = yukyufkymL3R;
	}
	/**
	 * @return the shonencalckbnL3R
	 */
	public String getShonencalckbnL3R() {
		return shonencalckbnL3R;
	}
	/**
	 * @param shonencalckbnL3R the shonencalckbnL3R to set
	 */
	public void setShonencalckbnL3R(String shonencalckbnL3R) {
		this.shonencalckbnL3R = shonencalckbnL3R;
	}
	/**
	 * @return the skkkanryoflgL3R
	 */
	public String getSkkkanryoflgL3R() {
		return skkkanryoflgL3R;
	}
	/**
	 * @param skkkanryoflgL3R the skkkanryoflgL3R to set
	 */
	public void setSkkkanryoflgL3R(String skkkanryoflgL3R) {
		this.skkkanryoflgL3R = skkkanryoflgL3R;
	}
	/**
	 * @return the skkkirikaeyyL3R
	 */
	public String getSkkkirikaeyyL3R() {
		return skkkirikaeyyL3R;
	}
	/**
	 * @param skkkirikaeyyL3R the skkkirikaeyyL3R to set
	 */
	public void setSkkkirikaeyyL3R(String skkkirikaeyyL3R) {
		this.skkkirikaeyyL3R = skkkirikaeyyL3R;
	}
	/**
	 * @return the zanzonskkstyyL3R
	 */
	public String getZanzonskkstyyL3R() {
		return zanzonskkstyyL3R;
	}
	/**
	 * @param zanzonskkstyyL3R the zanzonskkstyyL3R to set
	 */
	public void setZanzonskkstyyL3R(String zanzonskkstyyL3R) {
		this.zanzonskkstyyL3R = zanzonskkstyyL3R;
	}
	/**
	 * @return the mitsumorigkWaribikimaeL4R
	 */
	public Long getMitsumorigkWaribikimaeL4R() {
		return mitsumorigkWaribikimaeL4R;
	}
	/**
	 * @param mitsumorigkWaribikimaeL4R the mitsumorigkWaribikimaeL4R to set
	 */
	public void setMitsumorigkWaribikimaeL4R(Long mitsumorigkWaribikimaeL4R) {
		this.mitsumorigkWaribikimaeL4R = mitsumorigkWaribikimaeL4R;
	}
	/**
	 * @return the waribikiritsuL4R
	 */
	public Double getWaribikiritsuL4R() {
		return waribikiritsuL4R;
	}
	/**
	 * @param waribikiritsuL4R the waribikiritsuL4R to set
	 */
	public void setWaribikiritsuL4R(Double waribikiritsuL4R) {
		this.waribikiritsuL4R = waribikiritsuL4R;
	}
	/**
	 * @return the keijogkWaribikigoL4R
	 */
	public Long getKeijogkWaribikigoL4R() {
		return keijogkWaribikigoL4R;
	}
	/**
	 * @param keijogkWaribikigoL4R the keijogkWaribikigoL4R to set
	 */
	public void setKeijogkWaribikigoL4R(Long keijogkWaribikigoL4R) {
		this.keijogkWaribikigoL4R = keijogkWaribikigoL4R;
	}
	/**
	 * @return the kssaimubokaL4R
	 */
	public Long getKssaimubokaL4R() {
		return kssaimubokaL4R;
	}
	/**
	 * @param kssaimubokaL4R the kssaimubokaL4R to set
	 */
	public void setKssaimubokaL4R(Long kssaimubokaL4R) {
		this.kssaimubokaL4R = kssaimubokaL4R;
	}
	/**
	 * @return the risokucalckisogkL4R
	 */
	public Long getRisokucalckisogkL4R() {
		return risokucalckisogkL4R;
	}
	/**
	 * @param risokucalckisogkL4R the risokucalckisogkL4R to set
	 */
	public void setRisokucalckisogkL4R(Long risokucalckisogkL4R) {
		this.risokucalckisogkL4R = risokucalckisogkL4R;
	}
	/**
	 * @return the toSaimubokaL4R
	 */
	public Long getToSaimubokaL4R() {
		return toSaimubokaL4R;
	}
	/**
	 * @param toSaimubokaL4R the toSaimubokaL4R to set
	 */
	public void setToSaimubokaL4R(Long toSaimubokaL4R) {
		this.toSaimubokaL4R = toSaimubokaL4R;
	}
	/**
	 * @return the toSaimuzogenbokaL4R
	 */
	public Long getToSaimuzogenbokaL4R() {
		return toSaimuzogenbokaL4R;
	}
	/**
	 * @param toSaimuzogenbokaL4R the toSaimuzogenbokaL4R to set
	 */
	public void setToSaimuzogenbokaL4R(Long toSaimuzogenbokaL4R) {
		this.toSaimuzogenbokaL4R = toSaimuzogenbokaL4R;
	}
	/**
	 * @return the toTorisokugkL4R
	 */
	public Long getToTorisokugkL4R() {
		return toTorisokugkL4R;
	}
	/**
	 * @param toTorisokugkL4R the toTorisokugkL4R to set
	 */
	public void setToTorisokugkL4R(Long toTorisokugkL4R) {
		this.toTorisokugkL4R = toTorisokugkL4R;
	}
	/**
	 * @return the ksrisokuruigkL4R
	 */
	public Long getKsrisokuruigkL4R() {
		return ksrisokuruigkL4R;
	}
	/**
	 * @param ksrisokuruigkL4R the ksrisokuruigkL4R to set
	 */
	public void setKsrisokuruigkL4R(Long ksrisokuruigkL4R) {
		this.ksrisokuruigkL4R = ksrisokuruigkL4R;
	}
	/**
	 * @return the mitsumorihkymdL4R
	 */
	public String getMitsumorihkymdL4R() {
		return mitsumorihkymdL4R;
	}
	/**
	 * @param mitsumorihkymdL4R the mitsumorihkymdL4R to set
	 */
	public void setMitsumorihkymdL4R(String mitsumorihkymdL4R) {
		this.mitsumorihkymdL4R = mitsumorihkymdL4R;
	}
	/**
	 * @return the famitsumorihkymdL4R
	 */
	public String getFamitsumorihkymdL4R() {
		return famitsumorihkymdL4R;
	}
	/**
	 * @param famitsumorihkymdL4R the famitsumorihkymdL4R to set
	 */
	public void setFamitsumorihkymdL4R(String famitsumorihkymdL4R) {
		this.famitsumorihkymdL4R = famitsumorihkymdL4R;
	}
	/**
	 * @return the mitsumorihkgobokaL4R
	 */
	public Long getMitsumorihkgobokaL4R() {
		return mitsumorihkgobokaL4R;
	}
	/**
	 * @param mitsumorihkgobokaL4R the mitsumorihkgobokaL4R to set
	 */
	public void setMitsumorihkgobokaL4R(Long mitsumorihkgobokaL4R) {
		this.mitsumorihkgobokaL4R = mitsumorihkgobokaL4R;
	}
	/**
	 * @return the mitsumorihkmaetainenL4R
	 */
	public Integer getMitsumorihkmaetainenL4R() {
		return mitsumorihkmaetainenL4R;
	}
	/**
	 * @param mitsumorihkmaetainenL4R the mitsumorihkmaetainenL4R to set
	 */
	public void setMitsumorihkmaetainenL4R(Integer mitsumorihkmaetainenL4R) {
		this.mitsumorihkmaetainenL4R = mitsumorihkmaetainenL4R;
	}
	/**
	 * @return the mitsumorihkmaeskkmmsuL4R
	 */
	public Integer getMitsumorihkmaeskkmmsuL4R() {
		return mitsumorihkmaeskkmmsuL4R;
	}
	/**
	 * @param mitsumorihkmaeskkmmsuL4R the mitsumorihkmaeskkmmsuL4R to set
	 */
	public void setMitsumorihkmaeskkmmsuL4R(Integer mitsumorihkmaeskkmmsuL4R) {
		this.mitsumorihkmaeskkmmsuL4R = mitsumorihkmaeskkmmsuL4R;
	}
	/**
	 * @return the mitsumorihkmaeskkritsuL4R
	 */
	public Double getMitsumorihkmaeskkritsuL4R() {
		return mitsumorihkmaeskkritsuL4R;
	}
	/**
	 * @param mitsumorihkmaeskkritsuL4R the mitsumorihkmaeskkritsuL4R to set
	 */
	public void setMitsumorihkmaeskkritsuL4R(Double mitsumorihkmaeskkritsuL4R) {
		this.mitsumorihkmaeskkritsuL4R = mitsumorihkmaeskkritsuL4R;
	}
	/**
	 * @return the tomitsumorihkchoseigkL4R
	 */
	public Long getTomitsumorihkchoseigkL4R() {
		return tomitsumorihkchoseigkL4R;
	}
	/**
	 * @param tomitsumorihkchoseigkL4R the tomitsumorihkchoseigkL4R to set
	 */
	public void setTomitsumorihkchoseigkL4R(Long tomitsumorihkchoseigkL4R) {
		this.tomitsumorihkchoseigkL4R = tomitsumorihkchoseigkL4R;
	}
	/**
	 * @return the stkgkL4R
	 */
	public Long getStkgkL4R() {
		return stkgkL4R;
	}
	/**
	 * @param stkgkL4R the stkgkL4R to set
	 */
	public void setStkgkL4R(Long stkgkL4R) {
		this.stkgkL4R = stkgkL4R;
	}
	/**
	 * @return the skkhoL4R
	 */
	public String getSkkhoL4R() {
		return skkhoL4R;
	}
	/**
	 * @param skkhoL4R the skkhoL4R to set
	 */
	public void setSkkhoL4R(String skkhoL4R) {
		this.skkhoL4R = skkhoL4R;
	}
	/**
	 * @return the tainenL4R
	 */
	public Integer getTainenL4R() {
		return tainenL4R;
	}
	/**
	 * @param tainenL4R the tainenL4R to set
	 */
	public void setTainenL4R(Integer tainenL4R) {
		this.tainenL4R = tainenL4R;
	}
	/**
	 * @return the skkritsuL4R
	 */
	public Double getSkkritsuL4R() {
		return skkritsuL4R;
	}
	/**
	 * @param skkritsuL4R the skkritsuL4R to set
	 */
	public void setSkkritsuL4R(Double skkritsuL4R) {
		this.skkritsuL4R = skkritsuL4R;
	}
	/**
	 * @return the skkmmsuL4R
	 */
	public Integer getSkkmmsuL4R() {
		return skkmmsuL4R;
	}
	/**
	 * @param skkmmsuL4R the skkmmsuL4R to set
	 */
	public void setSkkmmsuL4R(Integer skkmmsuL4R) {
		this.skkmmsuL4R = skkmmsuL4R;
	}
	/**
	 * @return the zanzonmmsuL4R
	 */
	public Integer getZanzonmmsuL4R() {
		return zanzonmmsuL4R;
	}
	/**
	 * @param zanzonmmsuL4R the zanzonmmsuL4R to set
	 */
	public void setZanzonmmsuL4R(Integer zanzonmmsuL4R) {
		this.zanzonmmsuL4R = zanzonmmsuL4R;
	}
	/**
	 * @return the ksbokaL4R
	 */
	public Long getKsbokaL4R() {
		return ksbokaL4R;
	}
	/**
	 * @param ksbokaL4R the ksbokaL4R to set
	 */
	public void setKsbokaL4R(Long ksbokaL4R) {
		this.ksbokaL4R = ksbokaL4R;
	}
	/**
	 * @return the skkcalckisogkL4R
	 */
	public Long getSkkcalckisogkL4R() {
		return skkcalckisogkL4R;
	}
	/**
	 * @param skkcalckisogkL4R the skkcalckisogkL4R to set
	 */
	public void setSkkcalckisogkL4R(Long skkcalckisogkL4R) {
		this.skkcalckisogkL4R = skkcalckisogkL4R;
	}
	/**
	 * @return the zanzonritsuL4R
	 */
	public Double getZanzonritsuL4R() {
		return zanzonritsuL4R;
	}
	/**
	 * @param zanzonritsuL4R the zanzonritsuL4R to set
	 */
	public void setZanzonritsuL4R(Double zanzonritsuL4R) {
		this.zanzonritsuL4R = zanzonritsuL4R;
	}
	/**
	 * @return the zanzongkL4R
	 */
	public Long getZanzongkL4R() {
		return zanzongkL4R;
	}
	/**
	 * @param zanzongkL4R the zanzongkL4R to set
	 */
	public void setZanzongkL4R(Long zanzongkL4R) {
		this.zanzongkL4R = zanzongkL4R;
	}
	/**
	 * @return the ksftskkruigkL4R
	 */
	public Long getKsftskkruigkL4R() {
		return ksftskkruigkL4R;
	}
	/**
	 * @param ksftskkruigkL4R the ksftskkruigkL4R to set
	 */
	public void setKsftskkruigkL4R(Long ksftskkruigkL4R) {
		this.ksftskkruigkL4R = ksftskkruigkL4R;
	}
	/**
	 * @return the kszkskkruigkL4R
	 */
	public Long getKszkskkruigkL4R() {
		return kszkskkruigkL4R;
	}
	/**
	 * @param kszkskkruigkL4R the kszkskkruigkL4R to set
	 */
	public void setKszkskkruigkL4R(Long kszkskkruigkL4R) {
		this.kszkskkruigkL4R = kszkskkruigkL4R;
	}
	/**
	 * @return the toToftskkgkL4R
	 */
	public Long getToToftskkgkL4R() {
		return toToftskkgkL4R;
	}
	/**
	 * @param toToftskkgkL4R the toToftskkgkL4R to set
	 */
	public void setToToftskkgkL4R(Long toToftskkgkL4R) {
		this.toToftskkgkL4R = toToftskkgkL4R;
	}
	/**
	 * @return the toTozkskkgkL4R
	 */
	public Long getToTozkskkgkL4R() {
		return toTozkskkgkL4R;
	}
	/**
	 * @param toTozkskkgkL4R the toTozkskkgkL4R to set
	 */
	public void setToTozkskkgkL4R(Long toTozkskkgkL4R) {
		this.toTozkskkgkL4R = toTozkskkgkL4R;
	}
	/**
	 * @return the toToniniskkgkL4R
	 */
	public Long getToToniniskkgkL4R() {
		return toToniniskkgkL4R;
	}
	/**
	 * @param toToniniskkgkL4R the toToniniskkgkL4R to set
	 */
	public void setToToniniskkgkL4R(Long toToniniskkgkL4R) {
		this.toToniniskkgkL4R = toToniniskkgkL4R;
	}
	/**
	 * @return the toBokaL4R
	 */
	public Long getToBokaL4R() {
		return toBokaL4R;
	}
	/**
	 * @param toBokaL4R the toBokaL4R to set
	 */
	public void setToBokaL4R(Long toBokaL4R) {
		this.toBokaL4R = toBokaL4R;
	}
	/**
	 * @return the zogenbokaL4R
	 */
	public Long getZogenbokaL4R() {
		return zogenbokaL4R;
	}
	/**
	 * @param zogenbokaL4R the zogenbokaL4R to set
	 */
	public void setZogenbokaL4R(Long zogenbokaL4R) {
		this.zogenbokaL4R = zogenbokaL4R;
	}
	/**
	 * @return the zkskkritsuL4R
	 */
	public Double getZkskkritsuL4R() {
		return zkskkritsuL4R;
	}
	/**
	 * @param zkskkritsuL4R the zkskkritsuL4R to set
	 */
	public void setZkskkritsuL4R(Double zkskkritsuL4R) {
		this.zkskkritsuL4R = zkskkritsuL4R;
	}
	/**
	 * @return the yukyustymL4R
	 */
	public String getYukyustymL4R() {
		return yukyustymL4R;
	}
	/**
	 * @param yukyustymL4R the yukyustymL4R to set
	 */
	public void setYukyustymL4R(String yukyustymL4R) {
		this.yukyustymL4R = yukyustymL4R;
	}
	/**
	 * @return the yukyufkymL4R
	 */
	public String getYukyufkymL4R() {
		return yukyufkymL4R;
	}
	/**
	 * @param yukyufkymL4R the yukyufkymL4R to set
	 */
	public void setYukyufkymL4R(String yukyufkymL4R) {
		this.yukyufkymL4R = yukyufkymL4R;
	}
	/**
	 * @return the shonencalckbnL4R
	 */
	public String getShonencalckbnL4R() {
		return shonencalckbnL4R;
	}
	/**
	 * @param shonencalckbnL4R the shonencalckbnL4R to set
	 */
	public void setShonencalckbnL4R(String shonencalckbnL4R) {
		this.shonencalckbnL4R = shonencalckbnL4R;
	}
	/**
	 * @return the skkkanryoflgL4R
	 */
	public String getSkkkanryoflgL4R() {
		return skkkanryoflgL4R;
	}
	/**
	 * @param skkkanryoflgL4R the skkkanryoflgL4R to set
	 */
	public void setSkkkanryoflgL4R(String skkkanryoflgL4R) {
		this.skkkanryoflgL4R = skkkanryoflgL4R;
	}
	/**
	 * @return the skkkirikaeyyL4R
	 */
	public String getSkkkirikaeyyL4R() {
		return skkkirikaeyyL4R;
	}
	/**
	 * @param skkkirikaeyyL4R the skkkirikaeyyL4R to set
	 */
	public void setSkkkirikaeyyL4R(String skkkirikaeyyL4R) {
		this.skkkirikaeyyL4R = skkkirikaeyyL4R;
	}
	/**
	 * @return the zanzonskkstyyL4R
	 */
	public String getZanzonskkstyyL4R() {
		return zanzonskkstyyL4R;
	}
	/**
	 * @param zanzonskkstyyL4R the zanzonskkstyyL4R to set
	 */
	public void setZanzonskkstyyL4R(String zanzonskkstyyL4R) {
		this.zanzonskkstyyL4R = zanzonskkstyyL4R;
	}
	/**
	 * @return the mitsumorigkWaribikimaeL5R
	 */
	public Long getMitsumorigkWaribikimaeL5R() {
		return mitsumorigkWaribikimaeL5R;
	}
	/**
	 * @param mitsumorigkWaribikimaeL5R the mitsumorigkWaribikimaeL5R to set
	 */
	public void setMitsumorigkWaribikimaeL5R(Long mitsumorigkWaribikimaeL5R) {
		this.mitsumorigkWaribikimaeL5R = mitsumorigkWaribikimaeL5R;
	}
	/**
	 * @return the waribikiritsuL5R
	 */
	public Double getWaribikiritsuL5R() {
		return waribikiritsuL5R;
	}
	/**
	 * @param waribikiritsuL5R the waribikiritsuL5R to set
	 */
	public void setWaribikiritsuL5R(Double waribikiritsuL5R) {
		this.waribikiritsuL5R = waribikiritsuL5R;
	}
	/**
	 * @return the keijogkWaribikigoL5R
	 */
	public Long getKeijogkWaribikigoL5R() {
		return keijogkWaribikigoL5R;
	}
	/**
	 * @param keijogkWaribikigoL5R the keijogkWaribikigoL5R to set
	 */
	public void setKeijogkWaribikigoL5R(Long keijogkWaribikigoL5R) {
		this.keijogkWaribikigoL5R = keijogkWaribikigoL5R;
	}
	/**
	 * @return the kssaimubokaL5R
	 */
	public Long getKssaimubokaL5R() {
		return kssaimubokaL5R;
	}
	/**
	 * @param kssaimubokaL5R the kssaimubokaL5R to set
	 */
	public void setKssaimubokaL5R(Long kssaimubokaL5R) {
		this.kssaimubokaL5R = kssaimubokaL5R;
	}
	/**
	 * @return the risokucalckisogkL5R
	 */
	public Long getRisokucalckisogkL5R() {
		return risokucalckisogkL5R;
	}
	/**
	 * @param risokucalckisogkL5R the risokucalckisogkL5R to set
	 */
	public void setRisokucalckisogkL5R(Long risokucalckisogkL5R) {
		this.risokucalckisogkL5R = risokucalckisogkL5R;
	}
	/**
	 * @return the toSaimubokaL5R
	 */
	public Long getToSaimubokaL5R() {
		return toSaimubokaL5R;
	}
	/**
	 * @param toSaimubokaL5R the toSaimubokaL5R to set
	 */
	public void setToSaimubokaL5R(Long toSaimubokaL5R) {
		this.toSaimubokaL5R = toSaimubokaL5R;
	}
	/**
	 * @return the toSaimuzogenbokaL5R
	 */
	public Long getToSaimuzogenbokaL5R() {
		return toSaimuzogenbokaL5R;
	}
	/**
	 * @param toSaimuzogenbokaL5R the toSaimuzogenbokaL5R to set
	 */
	public void setToSaimuzogenbokaL5R(Long toSaimuzogenbokaL5R) {
		this.toSaimuzogenbokaL5R = toSaimuzogenbokaL5R;
	}
	/**
	 * @return the toTorisokugkL5R
	 */
	public Long getToTorisokugkL5R() {
		return toTorisokugkL5R;
	}
	/**
	 * @param toTorisokugkL5R the toTorisokugkL5R to set
	 */
	public void setToTorisokugkL5R(Long toTorisokugkL5R) {
		this.toTorisokugkL5R = toTorisokugkL5R;
	}
	/**
	 * @return the ksrisokuruigkL5R
	 */
	public Long getKsrisokuruigkL5R() {
		return ksrisokuruigkL5R;
	}
	/**
	 * @param ksrisokuruigkL5R the ksrisokuruigkL5R to set
	 */
	public void setKsrisokuruigkL5R(Long ksrisokuruigkL5R) {
		this.ksrisokuruigkL5R = ksrisokuruigkL5R;
	}
	/**
	 * @return the mitsumorihkymdL5R
	 */
	public String getMitsumorihkymdL5R() {
		return mitsumorihkymdL5R;
	}
	/**
	 * @param mitsumorihkymdL5R the mitsumorihkymdL5R to set
	 */
	public void setMitsumorihkymdL5R(String mitsumorihkymdL5R) {
		this.mitsumorihkymdL5R = mitsumorihkymdL5R;
	}
	/**
	 * @return the famitsumorihkymdL5R
	 */
	public String getFamitsumorihkymdL5R() {
		return famitsumorihkymdL5R;
	}
	/**
	 * @param famitsumorihkymdL5R the famitsumorihkymdL5R to set
	 */
	public void setFamitsumorihkymdL5R(String famitsumorihkymdL5R) {
		this.famitsumorihkymdL5R = famitsumorihkymdL5R;
	}
	/**
	 * @return the mitsumorihkgobokaL5R
	 */
	public Long getMitsumorihkgobokaL5R() {
		return mitsumorihkgobokaL5R;
	}
	/**
	 * @param mitsumorihkgobokaL5R the mitsumorihkgobokaL5R to set
	 */
	public void setMitsumorihkgobokaL5R(Long mitsumorihkgobokaL5R) {
		this.mitsumorihkgobokaL5R = mitsumorihkgobokaL5R;
	}
	/**
	 * @return the mitsumorihkmaetainenL5R
	 */
	public Integer getMitsumorihkmaetainenL5R() {
		return mitsumorihkmaetainenL5R;
	}
	/**
	 * @param mitsumorihkmaetainenL5R the mitsumorihkmaetainenL5R to set
	 */
	public void setMitsumorihkmaetainenL5R(Integer mitsumorihkmaetainenL5R) {
		this.mitsumorihkmaetainenL5R = mitsumorihkmaetainenL5R;
	}
	/**
	 * @return the mitsumorihkmaeskkmmsuL5R
	 */
	public Integer getMitsumorihkmaeskkmmsuL5R() {
		return mitsumorihkmaeskkmmsuL5R;
	}
	/**
	 * @param mitsumorihkmaeskkmmsuL5R the mitsumorihkmaeskkmmsuL5R to set
	 */
	public void setMitsumorihkmaeskkmmsuL5R(Integer mitsumorihkmaeskkmmsuL5R) {
		this.mitsumorihkmaeskkmmsuL5R = mitsumorihkmaeskkmmsuL5R;
	}
	/**
	 * @return the mitsumorihkmaeskkritsuL5R
	 */
	public Double getMitsumorihkmaeskkritsuL5R() {
		return mitsumorihkmaeskkritsuL5R;
	}
	/**
	 * @param mitsumorihkmaeskkritsuL5R the mitsumorihkmaeskkritsuL5R to set
	 */
	public void setMitsumorihkmaeskkritsuL5R(Double mitsumorihkmaeskkritsuL5R) {
		this.mitsumorihkmaeskkritsuL5R = mitsumorihkmaeskkritsuL5R;
	}
	/**
	 * @return the tomitsumorihkchoseigkL5R
	 */
	public Long getTomitsumorihkchoseigkL5R() {
		return tomitsumorihkchoseigkL5R;
	}
	/**
	 * @param tomitsumorihkchoseigkL5R the tomitsumorihkchoseigkL5R to set
	 */
	public void setTomitsumorihkchoseigkL5R(Long tomitsumorihkchoseigkL5R) {
		this.tomitsumorihkchoseigkL5R = tomitsumorihkchoseigkL5R;
	}
	/**
	 * @return the stkgkL5R
	 */
	public Long getStkgkL5R() {
		return stkgkL5R;
	}
	/**
	 * @param stkgkL5R the stkgkL5R to set
	 */
	public void setStkgkL5R(Long stkgkL5R) {
		this.stkgkL5R = stkgkL5R;
	}
	/**
	 * @return the skkhoL5R
	 */
	public String getSkkhoL5R() {
		return skkhoL5R;
	}
	/**
	 * @param skkhoL5R the skkhoL5R to set
	 */
	public void setSkkhoL5R(String skkhoL5R) {
		this.skkhoL5R = skkhoL5R;
	}
	/**
	 * @return the tainenL5R
	 */
	public Integer getTainenL5R() {
		return tainenL5R;
	}
	/**
	 * @param tainenL5R the tainenL5R to set
	 */
	public void setTainenL5R(Integer tainenL5R) {
		this.tainenL5R = tainenL5R;
	}
	/**
	 * @return the skkritsuL5R
	 */
	public Double getSkkritsuL5R() {
		return skkritsuL5R;
	}
	/**
	 * @param skkritsuL5R the skkritsuL5R to set
	 */
	public void setSkkritsuL5R(Double skkritsuL5R) {
		this.skkritsuL5R = skkritsuL5R;
	}
	/**
	 * @return the skkmmsuL5R
	 */
	public Integer getSkkmmsuL5R() {
		return skkmmsuL5R;
	}
	/**
	 * @param skkmmsuL5R the skkmmsuL5R to set
	 */
	public void setSkkmmsuL5R(Integer skkmmsuL5R) {
		this.skkmmsuL5R = skkmmsuL5R;
	}
	/**
	 * @return the zanzonmmsuL5R
	 */
	public Integer getZanzonmmsuL5R() {
		return zanzonmmsuL5R;
	}
	/**
	 * @param zanzonmmsuL5R the zanzonmmsuL5R to set
	 */
	public void setZanzonmmsuL5R(Integer zanzonmmsuL5R) {
		this.zanzonmmsuL5R = zanzonmmsuL5R;
	}
	/**
	 * @return the ksbokaL5R
	 */
	public Long getKsbokaL5R() {
		return ksbokaL5R;
	}
	/**
	 * @param ksbokaL5R the ksbokaL5R to set
	 */
	public void setKsbokaL5R(Long ksbokaL5R) {
		this.ksbokaL5R = ksbokaL5R;
	}
	/**
	 * @return the skkcalckisogkL5R
	 */
	public Long getSkkcalckisogkL5R() {
		return skkcalckisogkL5R;
	}
	/**
	 * @param skkcalckisogkL5R the skkcalckisogkL5R to set
	 */
	public void setSkkcalckisogkL5R(Long skkcalckisogkL5R) {
		this.skkcalckisogkL5R = skkcalckisogkL5R;
	}
	/**
	 * @return the zanzonritsuL5R
	 */
	public Double getZanzonritsuL5R() {
		return zanzonritsuL5R;
	}
	/**
	 * @param zanzonritsuL5R the zanzonritsuL5R to set
	 */
	public void setZanzonritsuL5R(Double zanzonritsuL5R) {
		this.zanzonritsuL5R = zanzonritsuL5R;
	}
	/**
	 * @return the zanzongkL5R
	 */
	public Long getZanzongkL5R() {
		return zanzongkL5R;
	}
	/**
	 * @param zanzongkL5R the zanzongkL5R to set
	 */
	public void setZanzongkL5R(Long zanzongkL5R) {
		this.zanzongkL5R = zanzongkL5R;
	}
	/**
	 * @return the ksftskkruigkL5R
	 */
	public Long getKsftskkruigkL5R() {
		return ksftskkruigkL5R;
	}
	/**
	 * @param ksftskkruigkL5R the ksftskkruigkL5R to set
	 */
	public void setKsftskkruigkL5R(Long ksftskkruigkL5R) {
		this.ksftskkruigkL5R = ksftskkruigkL5R;
	}
	/**
	 * @return the kszkskkruigkL5R
	 */
	public Long getKszkskkruigkL5R() {
		return kszkskkruigkL5R;
	}
	/**
	 * @param kszkskkruigkL5R the kszkskkruigkL5R to set
	 */
	public void setKszkskkruigkL5R(Long kszkskkruigkL5R) {
		this.kszkskkruigkL5R = kszkskkruigkL5R;
	}
	/**
	 * @return the toToftskkgkL5R
	 */
	public Long getToToftskkgkL5R() {
		return toToftskkgkL5R;
	}
	/**
	 * @param toToftskkgkL5R the toToftskkgkL5R to set
	 */
	public void setToToftskkgkL5R(Long toToftskkgkL5R) {
		this.toToftskkgkL5R = toToftskkgkL5R;
	}
	/**
	 * @return the toTozkskkgkL5R
	 */
	public Long getToTozkskkgkL5R() {
		return toTozkskkgkL5R;
	}
	/**
	 * @param toTozkskkgkL5R the toTozkskkgkL5R to set
	 */
	public void setToTozkskkgkL5R(Long toTozkskkgkL5R) {
		this.toTozkskkgkL5R = toTozkskkgkL5R;
	}
	/**
	 * @return the toToniniskkgkL5R
	 */
	public Long getToToniniskkgkL5R() {
		return toToniniskkgkL5R;
	}
	/**
	 * @param toToniniskkgkL5R the toToniniskkgkL5R to set
	 */
	public void setToToniniskkgkL5R(Long toToniniskkgkL5R) {
		this.toToniniskkgkL5R = toToniniskkgkL5R;
	}
	/**
	 * @return the toBokaL5R
	 */
	public Long getToBokaL5R() {
		return toBokaL5R;
	}
	/**
	 * @param toBokaL5R the toBokaL5R to set
	 */
	public void setToBokaL5R(Long toBokaL5R) {
		this.toBokaL5R = toBokaL5R;
	}
	/**
	 * @return the zogenbokaL5R
	 */
	public Long getZogenbokaL5R() {
		return zogenbokaL5R;
	}
	/**
	 * @param zogenbokaL5R the zogenbokaL5R to set
	 */
	public void setZogenbokaL5R(Long zogenbokaL5R) {
		this.zogenbokaL5R = zogenbokaL5R;
	}
	/**
	 * @return the zkskkritsuL5R
	 */
	public Double getZkskkritsuL5R() {
		return zkskkritsuL5R;
	}
	/**
	 * @param zkskkritsuL5R the zkskkritsuL5R to set
	 */
	public void setZkskkritsuL5R(Double zkskkritsuL5R) {
		this.zkskkritsuL5R = zkskkritsuL5R;
	}
	/**
	 * @return the yukyustymL5R
	 */
	public String getYukyustymL5R() {
		return yukyustymL5R;
	}
	/**
	 * @param yukyustymL5R the yukyustymL5R to set
	 */
	public void setYukyustymL5R(String yukyustymL5R) {
		this.yukyustymL5R = yukyustymL5R;
	}
	/**
	 * @return the yukyufkymL5R
	 */
	public String getYukyufkymL5R() {
		return yukyufkymL5R;
	}
	/**
	 * @param yukyufkymL5R the yukyufkymL5R to set
	 */
	public void setYukyufkymL5R(String yukyufkymL5R) {
		this.yukyufkymL5R = yukyufkymL5R;
	}
	/**
	 * @return the shonencalckbnL5R
	 */
	public String getShonencalckbnL5R() {
		return shonencalckbnL5R;
	}
	/**
	 * @param shonencalckbnL5R the shonencalckbnL5R to set
	 */
	public void setShonencalckbnL5R(String shonencalckbnL5R) {
		this.shonencalckbnL5R = shonencalckbnL5R;
	}
	/**
	 * @return the skkkanryoflgL5R
	 */
	public String getSkkkanryoflgL5R() {
		return skkkanryoflgL5R;
	}
	/**
	 * @param skkkanryoflgL5R the skkkanryoflgL5R to set
	 */
	public void setSkkkanryoflgL5R(String skkkanryoflgL5R) {
		this.skkkanryoflgL5R = skkkanryoflgL5R;
	}
	/**
	 * @return the skkkirikaeyyL5R
	 */
	public String getSkkkirikaeyyL5R() {
		return skkkirikaeyyL5R;
	}
	/**
	 * @param skkkirikaeyyL5R the skkkirikaeyyL5R to set
	 */
	public void setSkkkirikaeyyL5R(String skkkirikaeyyL5R) {
		this.skkkirikaeyyL5R = skkkirikaeyyL5R;
	}
	/**
	 * @return the zanzonskkstyyL5R
	 */
	public String getZanzonskkstyyL5R() {
		return zanzonskkstyyL5R;
	}
	/**
	 * @param zanzonskkstyyL5R the zanzonskkstyyL5R to set
	 */
	public void setZanzonskkstyyL5R(String zanzonskkstyyL5R) {
		this.zanzonskkstyyL5R = zanzonskkstyyL5R;
	}
	/**
	 * @return the mitsumorigkWaribikimaeL6R
	 */
	public Long getMitsumorigkWaribikimaeL6R() {
		return mitsumorigkWaribikimaeL6R;
	}
	/**
	 * @param mitsumorigkWaribikimaeL6R the mitsumorigkWaribikimaeL6R to set
	 */
	public void setMitsumorigkWaribikimaeL6R(Long mitsumorigkWaribikimaeL6R) {
		this.mitsumorigkWaribikimaeL6R = mitsumorigkWaribikimaeL6R;
	}
	/**
	 * @return the waribikiritsuL6R
	 */
	public Double getWaribikiritsuL6R() {
		return waribikiritsuL6R;
	}
	/**
	 * @param waribikiritsuL6R the waribikiritsuL6R to set
	 */
	public void setWaribikiritsuL6R(Double waribikiritsuL6R) {
		this.waribikiritsuL6R = waribikiritsuL6R;
	}
	/**
	 * @return the keijogkWaribikigoL6R
	 */
	public Long getKeijogkWaribikigoL6R() {
		return keijogkWaribikigoL6R;
	}
	/**
	 * @param keijogkWaribikigoL6R the keijogkWaribikigoL6R to set
	 */
	public void setKeijogkWaribikigoL6R(Long keijogkWaribikigoL6R) {
		this.keijogkWaribikigoL6R = keijogkWaribikigoL6R;
	}
	/**
	 * @return the kssaimubokaL6R
	 */
	public Long getKssaimubokaL6R() {
		return kssaimubokaL6R;
	}
	/**
	 * @param kssaimubokaL6R the kssaimubokaL6R to set
	 */
	public void setKssaimubokaL6R(Long kssaimubokaL6R) {
		this.kssaimubokaL6R = kssaimubokaL6R;
	}
	/**
	 * @return the risokucalckisogkL6R
	 */
	public Long getRisokucalckisogkL6R() {
		return risokucalckisogkL6R;
	}
	/**
	 * @param risokucalckisogkL6R the risokucalckisogkL6R to set
	 */
	public void setRisokucalckisogkL6R(Long risokucalckisogkL6R) {
		this.risokucalckisogkL6R = risokucalckisogkL6R;
	}
	/**
	 * @return the toSaimubokaL6R
	 */
	public Long getToSaimubokaL6R() {
		return toSaimubokaL6R;
	}
	/**
	 * @param toSaimubokaL6R the toSaimubokaL6R to set
	 */
	public void setToSaimubokaL6R(Long toSaimubokaL6R) {
		this.toSaimubokaL6R = toSaimubokaL6R;
	}
	/**
	 * @return the toSaimuzogenbokaL6R
	 */
	public Long getToSaimuzogenbokaL6R() {
		return toSaimuzogenbokaL6R;
	}
	/**
	 * @param toSaimuzogenbokaL6R the toSaimuzogenbokaL6R to set
	 */
	public void setToSaimuzogenbokaL6R(Long toSaimuzogenbokaL6R) {
		this.toSaimuzogenbokaL6R = toSaimuzogenbokaL6R;
	}
	/**
	 * @return the toTorisokugkL6R
	 */
	public Long getToTorisokugkL6R() {
		return toTorisokugkL6R;
	}
	/**
	 * @param toTorisokugkL6R the toTorisokugkL6R to set
	 */
	public void setToTorisokugkL6R(Long toTorisokugkL6R) {
		this.toTorisokugkL6R = toTorisokugkL6R;
	}
	/**
	 * @return the ksrisokuruigkL6R
	 */
	public Long getKsrisokuruigkL6R() {
		return ksrisokuruigkL6R;
	}
	/**
	 * @param ksrisokuruigkL6R the ksrisokuruigkL6R to set
	 */
	public void setKsrisokuruigkL6R(Long ksrisokuruigkL6R) {
		this.ksrisokuruigkL6R = ksrisokuruigkL6R;
	}
	/**
	 * @return the mitsumorihkymdL6R
	 */
	public String getMitsumorihkymdL6R() {
		return mitsumorihkymdL6R;
	}
	/**
	 * @param mitsumorihkymdL6R the mitsumorihkymdL6R to set
	 */
	public void setMitsumorihkymdL6R(String mitsumorihkymdL6R) {
		this.mitsumorihkymdL6R = mitsumorihkymdL6R;
	}
	/**
	 * @return the famitsumorihkymdL6R
	 */
	public String getFamitsumorihkymdL6R() {
		return famitsumorihkymdL6R;
	}
	/**
	 * @param famitsumorihkymdL6R the famitsumorihkymdL6R to set
	 */
	public void setFamitsumorihkymdL6R(String famitsumorihkymdL6R) {
		this.famitsumorihkymdL6R = famitsumorihkymdL6R;
	}
	/**
	 * @return the mitsumorihkgobokaL6R
	 */
	public Long getMitsumorihkgobokaL6R() {
		return mitsumorihkgobokaL6R;
	}
	/**
	 * @param mitsumorihkgobokaL6R the mitsumorihkgobokaL6R to set
	 */
	public void setMitsumorihkgobokaL6R(Long mitsumorihkgobokaL6R) {
		this.mitsumorihkgobokaL6R = mitsumorihkgobokaL6R;
	}
	/**
	 * @return the mitsumorihkmaetainenL6R
	 */
	public Integer getMitsumorihkmaetainenL6R() {
		return mitsumorihkmaetainenL6R;
	}
	/**
	 * @param mitsumorihkmaetainenL6R the mitsumorihkmaetainenL6R to set
	 */
	public void setMitsumorihkmaetainenL6R(Integer mitsumorihkmaetainenL6R) {
		this.mitsumorihkmaetainenL6R = mitsumorihkmaetainenL6R;
	}
	/**
	 * @return the mitsumorihkmaeskkmmsuL6R
	 */
	public Integer getMitsumorihkmaeskkmmsuL6R() {
		return mitsumorihkmaeskkmmsuL6R;
	}
	/**
	 * @param mitsumorihkmaeskkmmsuL6R the mitsumorihkmaeskkmmsuL6R to set
	 */
	public void setMitsumorihkmaeskkmmsuL6R(Integer mitsumorihkmaeskkmmsuL6R) {
		this.mitsumorihkmaeskkmmsuL6R = mitsumorihkmaeskkmmsuL6R;
	}
	/**
	 * @return the mitsumorihkmaeskkritsuL6R
	 */
	public Double getMitsumorihkmaeskkritsuL6R() {
		return mitsumorihkmaeskkritsuL6R;
	}
	/**
	 * @param mitsumorihkmaeskkritsuL6R the mitsumorihkmaeskkritsuL6R to set
	 */
	public void setMitsumorihkmaeskkritsuL6R(Double mitsumorihkmaeskkritsuL6R) {
		this.mitsumorihkmaeskkritsuL6R = mitsumorihkmaeskkritsuL6R;
	}
	/**
	 * @return the tomitsumorihkchoseigkL6R
	 */
	public Long getTomitsumorihkchoseigkL6R() {
		return tomitsumorihkchoseigkL6R;
	}
	/**
	 * @param tomitsumorihkchoseigkL6R the tomitsumorihkchoseigkL6R to set
	 */
	public void setTomitsumorihkchoseigkL6R(Long tomitsumorihkchoseigkL6R) {
		this.tomitsumorihkchoseigkL6R = tomitsumorihkchoseigkL6R;
	}
	/**
	 * @return the stkgkL6R
	 */
	public Long getStkgkL6R() {
		return stkgkL6R;
	}
	/**
	 * @param stkgkL6R the stkgkL6R to set
	 */
	public void setStkgkL6R(Long stkgkL6R) {
		this.stkgkL6R = stkgkL6R;
	}
	/**
	 * @return the skkhoL6R
	 */
	public String getSkkhoL6R() {
		return skkhoL6R;
	}
	/**
	 * @param skkhoL6R the skkhoL6R to set
	 */
	public void setSkkhoL6R(String skkhoL6R) {
		this.skkhoL6R = skkhoL6R;
	}
	/**
	 * @return the tainenL6R
	 */
	public Integer getTainenL6R() {
		return tainenL6R;
	}
	/**
	 * @param tainenL6R the tainenL6R to set
	 */
	public void setTainenL6R(Integer tainenL6R) {
		this.tainenL6R = tainenL6R;
	}
	/**
	 * @return the skkritsuL6R
	 */
	public Double getSkkritsuL6R() {
		return skkritsuL6R;
	}
	/**
	 * @param skkritsuL6R the skkritsuL6R to set
	 */
	public void setSkkritsuL6R(Double skkritsuL6R) {
		this.skkritsuL6R = skkritsuL6R;
	}
	/**
	 * @return the skkmmsuL6R
	 */
	public Integer getSkkmmsuL6R() {
		return skkmmsuL6R;
	}
	/**
	 * @param skkmmsuL6R the skkmmsuL6R to set
	 */
	public void setSkkmmsuL6R(Integer skkmmsuL6R) {
		this.skkmmsuL6R = skkmmsuL6R;
	}
	/**
	 * @return the zanzonmmsuL6R
	 */
	public Integer getZanzonmmsuL6R() {
		return zanzonmmsuL6R;
	}
	/**
	 * @param zanzonmmsuL6R the zanzonmmsuL6R to set
	 */
	public void setZanzonmmsuL6R(Integer zanzonmmsuL6R) {
		this.zanzonmmsuL6R = zanzonmmsuL6R;
	}
	/**
	 * @return the ksbokaL6R
	 */
	public Long getKsbokaL6R() {
		return ksbokaL6R;
	}
	/**
	 * @param ksbokaL6R the ksbokaL6R to set
	 */
	public void setKsbokaL6R(Long ksbokaL6R) {
		this.ksbokaL6R = ksbokaL6R;
	}
	/**
	 * @return the skkcalckisogkL6R
	 */
	public Long getSkkcalckisogkL6R() {
		return skkcalckisogkL6R;
	}
	/**
	 * @param skkcalckisogkL6R the skkcalckisogkL6R to set
	 */
	public void setSkkcalckisogkL6R(Long skkcalckisogkL6R) {
		this.skkcalckisogkL6R = skkcalckisogkL6R;
	}
	/**
	 * @return the zanzonritsuL6R
	 */
	public Double getZanzonritsuL6R() {
		return zanzonritsuL6R;
	}
	/**
	 * @param zanzonritsuL6R the zanzonritsuL6R to set
	 */
	public void setZanzonritsuL6R(Double zanzonritsuL6R) {
		this.zanzonritsuL6R = zanzonritsuL6R;
	}
	/**
	 * @return the zanzongkL6R
	 */
	public Long getZanzongkL6R() {
		return zanzongkL6R;
	}
	/**
	 * @param zanzongkL6R the zanzongkL6R to set
	 */
	public void setZanzongkL6R(Long zanzongkL6R) {
		this.zanzongkL6R = zanzongkL6R;
	}
	/**
	 * @return the ksftskkruigkL6R
	 */
	public Long getKsftskkruigkL6R() {
		return ksftskkruigkL6R;
	}
	/**
	 * @param ksftskkruigkL6R the ksftskkruigkL6R to set
	 */
	public void setKsftskkruigkL6R(Long ksftskkruigkL6R) {
		this.ksftskkruigkL6R = ksftskkruigkL6R;
	}
	/**
	 * @return the kszkskkruigkL6R
	 */
	public Long getKszkskkruigkL6R() {
		return kszkskkruigkL6R;
	}
	/**
	 * @param kszkskkruigkL6R the kszkskkruigkL6R to set
	 */
	public void setKszkskkruigkL6R(Long kszkskkruigkL6R) {
		this.kszkskkruigkL6R = kszkskkruigkL6R;
	}
	/**
	 * @return the toToftskkgkL6R
	 */
	public Long getToToftskkgkL6R() {
		return toToftskkgkL6R;
	}
	/**
	 * @param toToftskkgkL6R the toToftskkgkL6R to set
	 */
	public void setToToftskkgkL6R(Long toToftskkgkL6R) {
		this.toToftskkgkL6R = toToftskkgkL6R;
	}
	/**
	 * @return the toTozkskkgkL6R
	 */
	public Long getToTozkskkgkL6R() {
		return toTozkskkgkL6R;
	}
	/**
	 * @param toTozkskkgkL6R the toTozkskkgkL6R to set
	 */
	public void setToTozkskkgkL6R(Long toTozkskkgkL6R) {
		this.toTozkskkgkL6R = toTozkskkgkL6R;
	}
	/**
	 * @return the toToniniskkgkL6R
	 */
	public Long getToToniniskkgkL6R() {
		return toToniniskkgkL6R;
	}
	/**
	 * @param toToniniskkgkL6R the toToniniskkgkL6R to set
	 */
	public void setToToniniskkgkL6R(Long toToniniskkgkL6R) {
		this.toToniniskkgkL6R = toToniniskkgkL6R;
	}
	/**
	 * @return the toBokaL6R
	 */
	public Long getToBokaL6R() {
		return toBokaL6R;
	}
	/**
	 * @param toBokaL6R the toBokaL6R to set
	 */
	public void setToBokaL6R(Long toBokaL6R) {
		this.toBokaL6R = toBokaL6R;
	}
	/**
	 * @return the zogenbokaL6R
	 */
	public Long getZogenbokaL6R() {
		return zogenbokaL6R;
	}
	/**
	 * @param zogenbokaL6R the zogenbokaL6R to set
	 */
	public void setZogenbokaL6R(Long zogenbokaL6R) {
		this.zogenbokaL6R = zogenbokaL6R;
	}
	/**
	 * @return the zkskkritsuL6R
	 */
	public Double getZkskkritsuL6R() {
		return zkskkritsuL6R;
	}
	/**
	 * @param zkskkritsuL6R the zkskkritsuL6R to set
	 */
	public void setZkskkritsuL6R(Double zkskkritsuL6R) {
		this.zkskkritsuL6R = zkskkritsuL6R;
	}
	/**
	 * @return the yukyustymL6R
	 */
	public String getYukyustymL6R() {
		return yukyustymL6R;
	}
	/**
	 * @param yukyustymL6R the yukyustymL6R to set
	 */
	public void setYukyustymL6R(String yukyustymL6R) {
		this.yukyustymL6R = yukyustymL6R;
	}
	/**
	 * @return the yukyufkymL6R
	 */
	public String getYukyufkymL6R() {
		return yukyufkymL6R;
	}
	/**
	 * @param yukyufkymL6R the yukyufkymL6R to set
	 */
	public void setYukyufkymL6R(String yukyufkymL6R) {
		this.yukyufkymL6R = yukyufkymL6R;
	}
	/**
	 * @return the shonencalckbnL6R
	 */
	public String getShonencalckbnL6R() {
		return shonencalckbnL6R;
	}
	/**
	 * @param shonencalckbnL6R the shonencalckbnL6R to set
	 */
	public void setShonencalckbnL6R(String shonencalckbnL6R) {
		this.shonencalckbnL6R = shonencalckbnL6R;
	}
	/**
	 * @return the skkkanryoflgL6R
	 */
	public String getSkkkanryoflgL6R() {
		return skkkanryoflgL6R;
	}
	/**
	 * @param skkkanryoflgL6R the skkkanryoflgL6R to set
	 */
	public void setSkkkanryoflgL6R(String skkkanryoflgL6R) {
		this.skkkanryoflgL6R = skkkanryoflgL6R;
	}
	/**
	 * @return the skkkirikaeyyL6R
	 */
	public String getSkkkirikaeyyL6R() {
		return skkkirikaeyyL6R;
	}
	/**
	 * @param skkkirikaeyyL6R the skkkirikaeyyL6R to set
	 */
	public void setSkkkirikaeyyL6R(String skkkirikaeyyL6R) {
		this.skkkirikaeyyL6R = skkkirikaeyyL6R;
	}
	/**
	 * @return the zanzonskkstyyL6R
	 */
	public String getZanzonskkstyyL6R() {
		return zanzonskkstyyL6R;
	}
	/**
	 * @param zanzonskkstyyL6R the zanzonskkstyyL6R to set
	 */
	public void setZanzonskkstyyL6R(String zanzonskkstyyL6R) {
		this.zanzonskkstyyL6R = zanzonskkstyyL6R;
	}
	/**
	 * @return the konyucdR
	 */
	public String getKonyucdR() {
		return konyucdR;
	}
	/**
	 * @param konyucdR the konyucdR to set
	 */
	public void setKonyucdR(String konyucdR) {
		this.konyucdR = konyucdR;
	}
	/**
	 * @return the konyunmR
	 */
	public String getKonyunmR() {
		return konyunmR;
	}
	/**
	 * @param konyunmR the konyunmR to set
	 */
	public void setKonyunmR(String konyunmR) {
		this.konyunmR = konyunmR;
	}
	/**
	 * @return the kashicdR
	 */
	public String getKashicdR() {
		return kashicdR;
	}
	/**
	 * @param kashicdR the kashicdR to set
	 */
	public void setKashicdR(String kashicdR) {
		this.kashicdR = kashicdR;
	}
	/**
	 * @return the kashinmR
	 */
	public String getKashinmR() {
		return kashinmR;
	}
	/**
	 * @param kashinmR the kashinmR to set
	 */
	public void setKashinmR(String kashinmR) {
		this.kashinmR = kashinmR;
	}
	/**
	 * @return the knrbunruicdR
	 */
	public String getKnrbunruicdR() {
		return knrbunruicdR;
	}
	/**
	 * @param knrbunruicdR the knrbunruicdR to set
	 */
	public void setKnrbunruicdR(String knrbunruicdR) {
		this.knrbunruicdR = knrbunruicdR;
	}
	/**
	 * @return the biko1R
	 */
	public String getBiko1R() {
		return biko1R;
	}
	/**
	 * @param biko1R the biko1R to set
	 */
	public void setBiko1R(String biko1R) {
		this.biko1R = biko1R;
	}
	/**
	 * @return the biko2R
	 */
	public String getBiko2R() {
		return biko2R;
	}
	/**
	 * @param biko2R the biko2R to set
	 */
	public void setBiko2R(String biko2R) {
		this.biko2R = biko2R;
	}
	/**
	 * @return the stkringinoR
	 */
	public String getStkringinoR() {
		return stkringinoR;
	}
	/**
	 * @param stkringinoR the stkringinoR to set
	 */
	public void setStkringinoR(String stkringinoR) {
		this.stkringinoR = stkringinoR;
	}
	/**
	 * @return the stktekiyoR
	 */
	public String getStktekiyoR() {
		return stktekiyoR;
	}
	/**
	 * @param stktekiyoR the stktekiyoR to set
	 */
	public void setStktekiyoR(String stktekiyoR) {
		this.stktekiyoR = stktekiyoR;
	}
	/**
	 * @return the groupcdR
	 */
	public String getGroupcdR() {
		return groupcdR;
	}
	/**
	 * @param groupcdR the groupcdR to set
	 */
	public void setGroupcdR(String groupcdR) {
		this.groupcdR = groupcdR;
	}
	/**
	 * @return the groupnmR
	 */
	public String getGroupnmR() {
		return groupnmR;
	}
	/**
	 * @param groupnmR the groupnmR to set
	 */
	public void setGroupnmR(String groupnmR) {
		this.groupnmR = groupnmR;
	}
	/**
	 * @return the shinariocdR
	 */
	public String getShinariocdR() {
		return shinariocdR;
	}
	/**
	 * @param shinariocdR the shinariocdR to set
	 */
	public void setShinariocdR(String shinariocdR) {
		this.shinariocdR = shinariocdR;
	}
	/**
	 * @return the shinarionmR
	 */
	public String getShinarionmR() {
		return shinarionmR;
	}
	/**
	 * @param shinarionmR the shinarionmR to set
	 */
	public void setShinarionmR(String shinarionmR) {
		this.shinarionmR = shinarionmR;
	}
	/**
	 * @return the shuyoshisankbnR
	 */
	public String getShuyoshisankbnR() {
		return shuyoshisankbnR;
	}
	/**
	 * @param shuyoshisankbnR the shuyoshisankbnR to set
	 */
	public void setShuyoshisankbnR(String shuyoshisankbnR) {
		this.shuyoshisankbnR = shuyoshisankbnR;
	}
	/**
	 * @return the niniLd_1cdR
	 */
	public String getNiniLd_1cdR() {
		return niniLd_1cdR;
	}
	/**
	 * @param niniLd_1cdR the niniLd_1cdR to set
	 */
	public void setNiniLd_1cdR(String niniLd_1cdR) {
		this.niniLd_1cdR = niniLd_1cdR;
	}
	/**
	 * @return the niniLd_1nmR
	 */
	public String getNiniLd_1nmR() {
		return niniLd_1nmR;
	}
	/**
	 * @param niniLd_1nmR the niniLd_1nmR to set
	 */
	public void setNiniLd_1nmR(String niniLd_1nmR) {
		this.niniLd_1nmR = niniLd_1nmR;
	}
	/**
	 * @return the niniLd_2cdR
	 */
	public String getNiniLd_2cdR() {
		return niniLd_2cdR;
	}
	/**
	 * @param niniLd_2cdR the niniLd_2cdR to set
	 */
	public void setNiniLd_2cdR(String niniLd_2cdR) {
		this.niniLd_2cdR = niniLd_2cdR;
	}
	/**
	 * @return the niniLd_2nmR
	 */
	public String getNiniLd_2nmR() {
		return niniLd_2nmR;
	}
	/**
	 * @param niniLd_2nmR the niniLd_2nmR to set
	 */
	public void setNiniLd_2nmR(String niniLd_2nmR) {
		this.niniLd_2nmR = niniLd_2nmR;
	}
	/**
	 * @return the niniLd_3cdR
	 */
	public String getNiniLd_3cdR() {
		return niniLd_3cdR;
	}
	/**
	 * @param niniLd_3cdR the niniLd_3cdR to set
	 */
	public void setNiniLd_3cdR(String niniLd_3cdR) {
		this.niniLd_3cdR = niniLd_3cdR;
	}
	/**
	 * @return the niniLd_3nmR
	 */
	public String getNiniLd_3nmR() {
		return niniLd_3nmR;
	}
	/**
	 * @param niniLd_3nmR the niniLd_3nmR to set
	 */
	public void setNiniLd_3nmR(String niniLd_3nmR) {
		this.niniLd_3nmR = niniLd_3nmR;
	}
	/**
	 * @return the niniLd_4cdR
	 */
	public String getNiniLd_4cdR() {
		return niniLd_4cdR;
	}
	/**
	 * @param niniLd_4cdR the niniLd_4cdR to set
	 */
	public void setNiniLd_4cdR(String niniLd_4cdR) {
		this.niniLd_4cdR = niniLd_4cdR;
	}
	/**
	 * @return the niniLd_4nmR
	 */
	public String getNiniLd_4nmR() {
		return niniLd_4nmR;
	}
	/**
	 * @param niniLd_4nmR the niniLd_4nmR to set
	 */
	public void setNiniLd_4nmR(String niniLd_4nmR) {
		this.niniLd_4nmR = niniLd_4nmR;
	}
	/**
	 * @return the niniLd_5cdR
	 */
	public String getNiniLd_5cdR() {
		return niniLd_5cdR;
	}
	/**
	 * @param niniLd_5cdR the niniLd_5cdR to set
	 */
	public void setNiniLd_5cdR(String niniLd_5cdR) {
		this.niniLd_5cdR = niniLd_5cdR;
	}
	/**
	 * @return the niniLd_5nmR
	 */
	public String getNiniLd_5nmR() {
		return niniLd_5nmR;
	}
	/**
	 * @param niniLd_5nmR the niniLd_5nmR to set
	 */
	public void setNiniLd_5nmR(String niniLd_5nmR) {
		this.niniLd_5nmR = niniLd_5nmR;
	}
	/**
	 * @return the niniLd_6cdR
	 */
	public String getNiniLd_6cdR() {
		return niniLd_6cdR;
	}
	/**
	 * @param niniLd_6cdR the niniLd_6cdR to set
	 */
	public void setNiniLd_6cdR(String niniLd_6cdR) {
		this.niniLd_6cdR = niniLd_6cdR;
	}
	/**
	 * @return the niniLd_6nmR
	 */
	public String getNiniLd_6nmR() {
		return niniLd_6nmR;
	}
	/**
	 * @param niniLd_6nmR the niniLd_6nmR to set
	 */
	public void setNiniLd_6nmR(String niniLd_6nmR) {
		this.niniLd_6nmR = niniLd_6nmR;
	}
	/**
	 * @return the niniLd_7cdR
	 */
	public String getNiniLd_7cdR() {
		return niniLd_7cdR;
	}
	/**
	 * @param niniLd_7cdR the niniLd_7cdR to set
	 */
	public void setNiniLd_7cdR(String niniLd_7cdR) {
		this.niniLd_7cdR = niniLd_7cdR;
	}
	/**
	 * @return the niniLd_7nmR
	 */
	public String getNiniLd_7nmR() {
		return niniLd_7nmR;
	}
	/**
	 * @param niniLd_7nmR the niniLd_7nmR to set
	 */
	public void setNiniLd_7nmR(String niniLd_7nmR) {
		this.niniLd_7nmR = niniLd_7nmR;
	}
	/**
	 * @return the niniLd_8cdR
	 */
	public String getNiniLd_8cdR() {
		return niniLd_8cdR;
	}
	/**
	 * @param niniLd_8cdR the niniLd_8cdR to set
	 */
	public void setNiniLd_8cdR(String niniLd_8cdR) {
		this.niniLd_8cdR = niniLd_8cdR;
	}
	/**
	 * @return the niniLd_8nmR
	 */
	public String getNiniLd_8nmR() {
		return niniLd_8nmR;
	}
	/**
	 * @param niniLd_8nmR the niniLd_8nmR to set
	 */
	public void setNiniLd_8nmR(String niniLd_8nmR) {
		this.niniLd_8nmR = niniLd_8nmR;
	}
	/**
	 * @return the niniLd_9cdR
	 */
	public String getNiniLd_9cdR() {
		return niniLd_9cdR;
	}
	/**
	 * @param niniLd_9cdR the niniLd_9cdR to set
	 */
	public void setNiniLd_9cdR(String niniLd_9cdR) {
		this.niniLd_9cdR = niniLd_9cdR;
	}
	/**
	 * @return the niniLd_9nmR
	 */
	public String getNiniLd_9nmR() {
		return niniLd_9nmR;
	}
	/**
	 * @param niniLd_9nmR the niniLd_9nmR to set
	 */
	public void setNiniLd_9nmR(String niniLd_9nmR) {
		this.niniLd_9nmR = niniLd_9nmR;
	}
	/**
	 * @return the niniLd_10cdR
	 */
	public String getNiniLd_10cdR() {
		return niniLd_10cdR;
	}
	/**
	 * @param niniLd_10cdR the niniLd_10cdR to set
	 */
	public void setNiniLd_10cdR(String niniLd_10cdR) {
		this.niniLd_10cdR = niniLd_10cdR;
	}
	/**
	 * @return the niniLd_10nmR
	 */
	public String getNiniLd_10nmR() {
		return niniLd_10nmR;
	}
	/**
	 * @param niniLd_10nmR the niniLd_10nmR to set
	 */
	public void setNiniLd_10nmR(String niniLd_10nmR) {
		this.niniLd_10nmR = niniLd_10nmR;
	}
	/**
	 * @return the niniLd_11cdR
	 */
	public String getNiniLd_11cdR() {
		return niniLd_11cdR;
	}
	/**
	 * @param niniLd_11cdR the niniLd_11cdR to set
	 */
	public void setNiniLd_11cdR(String niniLd_11cdR) {
		this.niniLd_11cdR = niniLd_11cdR;
	}
	/**
	 * @return the niniLd_11nmR
	 */
	public String getNiniLd_11nmR() {
		return niniLd_11nmR;
	}
	/**
	 * @param niniLd_11nmR the niniLd_11nmR to set
	 */
	public void setNiniLd_11nmR(String niniLd_11nmR) {
		this.niniLd_11nmR = niniLd_11nmR;
	}
	/**
	 * @return the niniLd_12cdR
	 */
	public String getNiniLd_12cdR() {
		return niniLd_12cdR;
	}
	/**
	 * @param niniLd_12cdR the niniLd_12cdR to set
	 */
	public void setNiniLd_12cdR(String niniLd_12cdR) {
		this.niniLd_12cdR = niniLd_12cdR;
	}
	/**
	 * @return the niniLd_12nmR
	 */
	public String getNiniLd_12nmR() {
		return niniLd_12nmR;
	}
	/**
	 * @param niniLd_12nmR the niniLd_12nmR to set
	 */
	public void setNiniLd_12nmR(String niniLd_12nmR) {
		this.niniLd_12nmR = niniLd_12nmR;
	}
	/**
	 * @return the niniLd_13cdR
	 */
	public String getNiniLd_13cdR() {
		return niniLd_13cdR;
	}
	/**
	 * @param niniLd_13cdR the niniLd_13cdR to set
	 */
	public void setNiniLd_13cdR(String niniLd_13cdR) {
		this.niniLd_13cdR = niniLd_13cdR;
	}
	/**
	 * @return the niniLd_13nmR
	 */
	public String getNiniLd_13nmR() {
		return niniLd_13nmR;
	}
	/**
	 * @param niniLd_13nmR the niniLd_13nmR to set
	 */
	public void setNiniLd_13nmR(String niniLd_13nmR) {
		this.niniLd_13nmR = niniLd_13nmR;
	}
	/**
	 * @return the niniLd_14cdR
	 */
	public String getNiniLd_14cdR() {
		return niniLd_14cdR;
	}
	/**
	 * @param niniLd_14cdR the niniLd_14cdR to set
	 */
	public void setNiniLd_14cdR(String niniLd_14cdR) {
		this.niniLd_14cdR = niniLd_14cdR;
	}
	/**
	 * @return the niniLd_14nmR
	 */
	public String getNiniLd_14nmR() {
		return niniLd_14nmR;
	}
	/**
	 * @param niniLd_14nmR the niniLd_14nmR to set
	 */
	public void setNiniLd_14nmR(String niniLd_14nmR) {
		this.niniLd_14nmR = niniLd_14nmR;
	}
	/**
	 * @return the niniLd_15cdR
	 */
	public String getNiniLd_15cdR() {
		return niniLd_15cdR;
	}
	/**
	 * @param niniLd_15cdR the niniLd_15cdR to set
	 */
	public void setNiniLd_15cdR(String niniLd_15cdR) {
		this.niniLd_15cdR = niniLd_15cdR;
	}
	/**
	 * @return the niniLd_15nmR
	 */
	public String getNiniLd_15nmR() {
		return niniLd_15nmR;
	}
	/**
	 * @param niniLd_15nmR the niniLd_15nmR to set
	 */
	public void setNiniLd_15nmR(String niniLd_15nmR) {
		this.niniLd_15nmR = niniLd_15nmR;
	}
	/**
	 * @return the niniLd_16cdR
	 */
	public String getNiniLd_16cdR() {
		return niniLd_16cdR;
	}
	/**
	 * @param niniLd_16cdR the niniLd_16cdR to set
	 */
	public void setNiniLd_16cdR(String niniLd_16cdR) {
		this.niniLd_16cdR = niniLd_16cdR;
	}
	/**
	 * @return the niniLd_16nmR
	 */
	public String getNiniLd_16nmR() {
		return niniLd_16nmR;
	}
	/**
	 * @param niniLd_16nmR the niniLd_16nmR to set
	 */
	public void setNiniLd_16nmR(String niniLd_16nmR) {
		this.niniLd_16nmR = niniLd_16nmR;
	}
	/**
	 * @return the niniLd_17cdR
	 */
	public String getNiniLd_17cdR() {
		return niniLd_17cdR;
	}
	/**
	 * @param niniLd_17cdR the niniLd_17cdR to set
	 */
	public void setNiniLd_17cdR(String niniLd_17cdR) {
		this.niniLd_17cdR = niniLd_17cdR;
	}
	/**
	 * @return the niniLd_17nmR
	 */
	public String getNiniLd_17nmR() {
		return niniLd_17nmR;
	}
	/**
	 * @param niniLd_17nmR the niniLd_17nmR to set
	 */
	public void setNiniLd_17nmR(String niniLd_17nmR) {
		this.niniLd_17nmR = niniLd_17nmR;
	}
	/**
	 * @return the niniLd_18cdR
	 */
	public String getNiniLd_18cdR() {
		return niniLd_18cdR;
	}
	/**
	 * @param niniLd_18cdR the niniLd_18cdR to set
	 */
	public void setNiniLd_18cdR(String niniLd_18cdR) {
		this.niniLd_18cdR = niniLd_18cdR;
	}
	/**
	 * @return the niniLd_18nmR
	 */
	public String getNiniLd_18nmR() {
		return niniLd_18nmR;
	}
	/**
	 * @param niniLd_18nmR the niniLd_18nmR to set
	 */
	public void setNiniLd_18nmR(String niniLd_18nmR) {
		this.niniLd_18nmR = niniLd_18nmR;
	}
	/**
	 * @return the niniLd_19cdR
	 */
	public String getNiniLd_19cdR() {
		return niniLd_19cdR;
	}
	/**
	 * @param niniLd_19cdR the niniLd_19cdR to set
	 */
	public void setNiniLd_19cdR(String niniLd_19cdR) {
		this.niniLd_19cdR = niniLd_19cdR;
	}
	/**
	 * @return the niniLd_19nmR
	 */
	public String getNiniLd_19nmR() {
		return niniLd_19nmR;
	}
	/**
	 * @param niniLd_19nmR the niniLd_19nmR to set
	 */
	public void setNiniLd_19nmR(String niniLd_19nmR) {
		this.niniLd_19nmR = niniLd_19nmR;
	}
	/**
	 * @return the niniLd_20cdR
	 */
	public String getNiniLd_20cdR() {
		return niniLd_20cdR;
	}
	/**
	 * @param niniLd_20cdR the niniLd_20cdR to set
	 */
	public void setNiniLd_20cdR(String niniLd_20cdR) {
		this.niniLd_20cdR = niniLd_20cdR;
	}
	/**
	 * @return the niniLd_20nmR
	 */
	public String getNiniLd_20nmR() {
		return niniLd_20nmR;
	}
	/**
	 * @param niniLd_20nmR the niniLd_20nmR to set
	 */
	public void setNiniLd_20nmR(String niniLd_20nmR) {
		this.niniLd_20nmR = niniLd_20nmR;
	}
	/**
	 * @return the toshinkkyyR
	 */
	public Long getToshinkkyyR() {
		return toshinkkyyR;
	}
	/**
	 * @param toshinkkyyR the toshinkkyyR to set
	 */
	public void setToshinkkyyR(Long toshinkkyyR) {
		this.toshinkkyyR = toshinkkyyR;
	}
	/**
	 * @return the ksgensonruigkkR
	 */
	public Long getKsgensonruigkkR() {
		return ksgensonruigkkR;
	}
	/**
	 * @param ksgensonruigkkR the ksgensonruigkkR to set
	 */
	public void setKsgensonruigkkR(Long ksgensonruigkkR) {
		this.ksgensonruigkkR = ksgensonruigkkR;
	}
	/**
	 * @return the togensongkkR
	 */
	public Long getTogensongkkR() {
		return togensongkkR;
	}
	/**
	 * @param togensongkkR the togensongkkR to set
	 */
	public void setTogensongkkR(Long togensongkkR) {
		this.togensongkkR = togensongkkR;
	}
	/**
	 * @return the skkcalczanzongkkR
	 */
	public Long getSkkcalczanzongkkR() {
		return skkcalczanzongkkR;
	}
	/**
	 * @param skkcalczanzongkkR the skkcalczanzongkkR to set
	 */
	public void setSkkcalczanzongkkR(Long skkcalczanzongkkR) {
		this.skkcalczanzongkkR = skkcalczanzongkkR;
	}
	/**
	 * @return the gensonbokakR
	 */
	public Long getGensonbokakR() {
		return gensonbokakR;
	}
	/**
	 * @param gensonbokakR the gensonbokakR to set
	 */
	public void setGensonbokakR(Long gensonbokakR) {
		this.gensonbokakR = gensonbokakR;
	}
	/**
	 * @return the gensonmaetainenkR
	 */
	public Integer getGensonmaetainenkR() {
		return gensonmaetainenkR;
	}
	/**
	 * @param gensonmaetainenkR the gensonmaetainenkR to set
	 */
	public void setGensonmaetainenkR(Integer gensonmaetainenkR) {
		this.gensonmaetainenkR = gensonmaetainenkR;
	}
	/**
	 * @return the gensonmaeskkmmsukR
	 */
	public Integer getGensonmaeskkmmsukR() {
		return gensonmaeskkmmsukR;
	}
	/**
	 * @param gensonmaeskkmmsukR the gensonmaeskkmmsukR to set
	 */
	public void setGensonmaeskkmmsukR(Integer gensonmaeskkmmsukR) {
		this.gensonmaeskkmmsukR = gensonmaeskkmmsukR;
	}
	/**
	 * @return the gensonymdkR
	 */
	public String getGensonymdkR() {
		return gensonymdkR;
	}
	/**
	 * @param gensonymdkR the gensonymdkR to set
	 */
	public void setGensonymdkR(String gensonymdkR) {
		this.gensonymdkR = gensonymdkR;
	}
	/**
	 * @return the genshistkgkkR
	 */
	public Long getGenshistkgkkR() {
		return genshistkgkkR;
	}
	/**
	 * @param genshistkgkkR the genshistkgkkR to set
	 */
	public void setGenshistkgkkR(Long genshistkgkkR) {
		this.genshistkgkkR = genshistkgkkR;
	}
	/**
	 * @return the kaiteistkgkkR
	 */
	public Long getKaiteistkgkkR() {
		return kaiteistkgkkR;
	}
	/**
	 * @param kaiteistkgkkR the kaiteistkgkkR to set
	 */
	public void setKaiteistkgkkR(Long kaiteistkgkkR) {
		this.kaiteistkgkkR = kaiteistkgkkR;
	}
	/**
	 * @return the kaiteitainenkR
	 */
	public Integer getKaiteitainenkR() {
		return kaiteitainenkR;
	}
	/**
	 * @param kaiteitainenkR the kaiteitainenkR to set
	 */
	public void setKaiteitainenkR(Integer kaiteitainenkR) {
		this.kaiteitainenkR = kaiteitainenkR;
	}
	/**
	 * @return the kaiteiymdkR
	 */
	public String getKaiteiymdkR() {
		return kaiteiymdkR;
	}
	/**
	 * @param kaiteiymdkR the kaiteiymdkR to set
	 */
	public void setKaiteiymdkR(String kaiteiymdkR) {
		this.kaiteiymdkR = kaiteiymdkR;
	}
	/**
	 * @return the yusenzogenkbnkR
	 */
	public String getYusenzogenkbnkR() {
		return yusenzogenkbnkR;
	}
	/**
	 * @param yusenzogenkbnkR the yusenzogenkbnkR to set
	 */
	public void setYusenzogenkbnkR(String yusenzogenkbnkR) {
		this.yusenzogenkbnkR = yusenzogenkbnkR;
	}
	/**
	 * @return the ksgensonruigkzR
	 */
	public Long getKsgensonruigkzR() {
		return ksgensonruigkzR;
	}
	/**
	 * @param ksgensonruigkzR the ksgensonruigkzR to set
	 */
	public void setKsgensonruigkzR(Long ksgensonruigkzR) {
		this.ksgensonruigkzR = ksgensonruigkzR;
	}
	/**
	 * @return the togensongkzR
	 */
	public Long getTogensongkzR() {
		return togensongkzR;
	}
	/**
	 * @param togensongkzR the togensongkzR to set
	 */
	public void setTogensongkzR(Long togensongkzR) {
		this.togensongkzR = togensongkzR;
	}
	/**
	 * @return the skkcalczanzongkzR
	 */
	public Long getSkkcalczanzongkzR() {
		return skkcalczanzongkzR;
	}
	/**
	 * @param skkcalczanzongkzR the skkcalczanzongkzR to set
	 */
	public void setSkkcalczanzongkzR(Long skkcalczanzongkzR) {
		this.skkcalczanzongkzR = skkcalczanzongkzR;
	}
	/**
	 * @return the gensonbokazR
	 */
	public Long getGensonbokazR() {
		return gensonbokazR;
	}
	/**
	 * @param gensonbokazR the gensonbokazR to set
	 */
	public void setGensonbokazR(Long gensonbokazR) {
		this.gensonbokazR = gensonbokazR;
	}
	/**
	 * @return the gensonmaetainenzR
	 */
	public Integer getGensonmaetainenzR() {
		return gensonmaetainenzR;
	}
	/**
	 * @param gensonmaetainenzR the gensonmaetainenzR to set
	 */
	public void setGensonmaetainenzR(Integer gensonmaetainenzR) {
		this.gensonmaetainenzR = gensonmaetainenzR;
	}
	/**
	 * @return the gensonmaeskkmmsuzR
	 */
	public Integer getGensonmaeskkmmsuzR() {
		return gensonmaeskkmmsuzR;
	}
	/**
	 * @param gensonmaeskkmmsuzR the gensonmaeskkmmsuzR to set
	 */
	public void setGensonmaeskkmmsuzR(Integer gensonmaeskkmmsuzR) {
		this.gensonmaeskkmmsuzR = gensonmaeskkmmsuzR;
	}
	/**
	 * @return the gensonymdzR
	 */
	public String getGensonymdzR() {
		return gensonymdzR;
	}
	/**
	 * @param gensonymdzR the gensonymdzR to set
	 */
	public void setGensonymdzR(String gensonymdzR) {
		this.gensonymdzR = gensonymdzR;
	}
	/**
	 * @return the genshistkgkzR
	 */
	public Long getGenshistkgkzR() {
		return genshistkgkzR;
	}
	/**
	 * @param genshistkgkzR the genshistkgkzR to set
	 */
	public void setGenshistkgkzR(Long genshistkgkzR) {
		this.genshistkgkzR = genshistkgkzR;
	}
	/**
	 * @return the kaiteistkgkzR
	 */
	public Long getKaiteistkgkzR() {
		return kaiteistkgkzR;
	}
	/**
	 * @param kaiteistkgkzR the kaiteistkgkzR to set
	 */
	public void setKaiteistkgkzR(Long kaiteistkgkzR) {
		this.kaiteistkgkzR = kaiteistkgkzR;
	}
	/**
	 * @return the kaiteitainenzR
	 */
	public Integer getKaiteitainenzR() {
		return kaiteitainenzR;
	}
	/**
	 * @param kaiteitainenzR the kaiteitainenzR to set
	 */
	public void setKaiteitainenzR(Integer kaiteitainenzR) {
		this.kaiteitainenzR = kaiteitainenzR;
	}
	/**
	 * @return the kaiteiymdzR
	 */
	public String getKaiteiymdzR() {
		return kaiteiymdzR;
	}
	/**
	 * @param kaiteiymdzR the kaiteiymdzR to set
	 */
	public void setKaiteiymdzR(String kaiteiymdzR) {
		this.kaiteiymdzR = kaiteiymdzR;
	}
	/**
	 * @return the yusenzogenkbnzR
	 */
	public String getYusenzogenkbnzR() {
		return yusenzogenkbnzR;
	}
	/**
	 * @param yusenzogenkbnzR the yusenzogenkbnzR to set
	 */
	public void setYusenzogenkbnzR(String yusenzogenkbnzR) {
		this.yusenzogenkbnzR = yusenzogenkbnzR;
	}
	/**
	 * @return the ksgensonruigkL3R
	 */
	public Long getKsgensonruigkL3R() {
		return ksgensonruigkL3R;
	}
	/**
	 * @param ksgensonruigkL3R the ksgensonruigkL3R to set
	 */
	public void setKsgensonruigkL3R(Long ksgensonruigkL3R) {
		this.ksgensonruigkL3R = ksgensonruigkL3R;
	}
	/**
	 * @return the togensongkL3R
	 */
	public Long getTogensongkL3R() {
		return togensongkL3R;
	}
	/**
	 * @param togensongkL3R the togensongkL3R to set
	 */
	public void setTogensongkL3R(Long togensongkL3R) {
		this.togensongkL3R = togensongkL3R;
	}
	/**
	 * @return the skkcalczanzongkL3R
	 */
	public Long getSkkcalczanzongkL3R() {
		return skkcalczanzongkL3R;
	}
	/**
	 * @param skkcalczanzongkL3R the skkcalczanzongkL3R to set
	 */
	public void setSkkcalczanzongkL3R(Long skkcalczanzongkL3R) {
		this.skkcalczanzongkL3R = skkcalczanzongkL3R;
	}
	/**
	 * @return the gensonbokaL3R
	 */
	public Long getGensonbokaL3R() {
		return gensonbokaL3R;
	}
	/**
	 * @param gensonbokaL3R the gensonbokaL3R to set
	 */
	public void setGensonbokaL3R(Long gensonbokaL3R) {
		this.gensonbokaL3R = gensonbokaL3R;
	}
	/**
	 * @return the gensonmaetainenL3R
	 */
	public Integer getGensonmaetainenL3R() {
		return gensonmaetainenL3R;
	}
	/**
	 * @param gensonmaetainenL3R the gensonmaetainenL3R to set
	 */
	public void setGensonmaetainenL3R(Integer gensonmaetainenL3R) {
		this.gensonmaetainenL3R = gensonmaetainenL3R;
	}
	/**
	 * @return the gensonmaeskkmmsuL3R
	 */
	public Integer getGensonmaeskkmmsuL3R() {
		return gensonmaeskkmmsuL3R;
	}
	/**
	 * @param gensonmaeskkmmsuL3R the gensonmaeskkmmsuL3R to set
	 */
	public void setGensonmaeskkmmsuL3R(Integer gensonmaeskkmmsuL3R) {
		this.gensonmaeskkmmsuL3R = gensonmaeskkmmsuL3R;
	}
	/**
	 * @return the gensonymdL3R
	 */
	public String getGensonymdL3R() {
		return gensonymdL3R;
	}
	/**
	 * @param gensonymdL3R the gensonymdL3R to set
	 */
	public void setGensonymdL3R(String gensonymdL3R) {
		this.gensonymdL3R = gensonymdL3R;
	}
	/**
	 * @return the genshistkgkL3R
	 */
	public Long getGenshistkgkL3R() {
		return genshistkgkL3R;
	}
	/**
	 * @param genshistkgkL3R the genshistkgkL3R to set
	 */
	public void setGenshistkgkL3R(Long genshistkgkL3R) {
		this.genshistkgkL3R = genshistkgkL3R;
	}
	/**
	 * @return the kaiteistkgkL3R
	 */
	public Long getKaiteistkgkL3R() {
		return kaiteistkgkL3R;
	}
	/**
	 * @param kaiteistkgkL3R the kaiteistkgkL3R to set
	 */
	public void setKaiteistkgkL3R(Long kaiteistkgkL3R) {
		this.kaiteistkgkL3R = kaiteistkgkL3R;
	}
	/**
	 * @return the kaiteitainenL3R
	 */
	public Integer getKaiteitainenL3R() {
		return kaiteitainenL3R;
	}
	/**
	 * @param kaiteitainenL3R the kaiteitainenL3R to set
	 */
	public void setKaiteitainenL3R(Integer kaiteitainenL3R) {
		this.kaiteitainenL3R = kaiteitainenL3R;
	}
	/**
	 * @return the kaiteiymdL3R
	 */
	public String getKaiteiymdL3R() {
		return kaiteiymdL3R;
	}
	/**
	 * @param kaiteiymdL3R the kaiteiymdL3R to set
	 */
	public void setKaiteiymdL3R(String kaiteiymdL3R) {
		this.kaiteiymdL3R = kaiteiymdL3R;
	}
	/**
	 * @return the yusenzogenkbnL3R
	 */
	public String getYusenzogenkbnL3R() {
		return yusenzogenkbnL3R;
	}
	/**
	 * @param yusenzogenkbnL3R the yusenzogenkbnL3R to set
	 */
	public void setYusenzogenkbnL3R(String yusenzogenkbnL3R) {
		this.yusenzogenkbnL3R = yusenzogenkbnL3R;
	}
	/**
	 * @return the ksgensonruigkL4R
	 */
	public Long getKsgensonruigkL4R() {
		return ksgensonruigkL4R;
	}
	/**
	 * @param ksgensonruigkL4R the ksgensonruigkL4R to set
	 */
	public void setKsgensonruigkL4R(Long ksgensonruigkL4R) {
		this.ksgensonruigkL4R = ksgensonruigkL4R;
	}
	/**
	 * @return the togensongkL4R
	 */
	public Long getTogensongkL4R() {
		return togensongkL4R;
	}
	/**
	 * @param togensongkL4R the togensongkL4R to set
	 */
	public void setTogensongkL4R(Long togensongkL4R) {
		this.togensongkL4R = togensongkL4R;
	}
	/**
	 * @return the skkcalczanzongkL4R
	 */
	public Long getSkkcalczanzongkL4R() {
		return skkcalczanzongkL4R;
	}
	/**
	 * @param skkcalczanzongkL4R the skkcalczanzongkL4R to set
	 */
	public void setSkkcalczanzongkL4R(Long skkcalczanzongkL4R) {
		this.skkcalczanzongkL4R = skkcalczanzongkL4R;
	}
	/**
	 * @return the gensonbokaL4R
	 */
	public Long getGensonbokaL4R() {
		return gensonbokaL4R;
	}
	/**
	 * @param gensonbokaL4R the gensonbokaL4R to set
	 */
	public void setGensonbokaL4R(Long gensonbokaL4R) {
		this.gensonbokaL4R = gensonbokaL4R;
	}
	/**
	 * @return the gensonmaetainenL4R
	 */
	public Integer getGensonmaetainenL4R() {
		return gensonmaetainenL4R;
	}
	/**
	 * @param gensonmaetainenL4R the gensonmaetainenL4R to set
	 */
	public void setGensonmaetainenL4R(Integer gensonmaetainenL4R) {
		this.gensonmaetainenL4R = gensonmaetainenL4R;
	}
	/**
	 * @return the gensonmaeskkmmsuL4R
	 */
	public Integer getGensonmaeskkmmsuL4R() {
		return gensonmaeskkmmsuL4R;
	}
	/**
	 * @param gensonmaeskkmmsuL4R the gensonmaeskkmmsuL4R to set
	 */
	public void setGensonmaeskkmmsuL4R(Integer gensonmaeskkmmsuL4R) {
		this.gensonmaeskkmmsuL4R = gensonmaeskkmmsuL4R;
	}
	/**
	 * @return the gensonymdL4R
	 */
	public String getGensonymdL4R() {
		return gensonymdL4R;
	}
	/**
	 * @param gensonymdL4R the gensonymdL4R to set
	 */
	public void setGensonymdL4R(String gensonymdL4R) {
		this.gensonymdL4R = gensonymdL4R;
	}
	/**
	 * @return the genshistkgkL4R
	 */
	public Long getGenshistkgkL4R() {
		return genshistkgkL4R;
	}
	/**
	 * @param genshistkgkL4R the genshistkgkL4R to set
	 */
	public void setGenshistkgkL4R(Long genshistkgkL4R) {
		this.genshistkgkL4R = genshistkgkL4R;
	}
	/**
	 * @return the kaiteistkgkL4R
	 */
	public Long getKaiteistkgkL4R() {
		return kaiteistkgkL4R;
	}
	/**
	 * @param kaiteistkgkL4R the kaiteistkgkL4R to set
	 */
	public void setKaiteistkgkL4R(Long kaiteistkgkL4R) {
		this.kaiteistkgkL4R = kaiteistkgkL4R;
	}
	/**
	 * @return the kaiteitainenL4R
	 */
	public Integer getKaiteitainenL4R() {
		return kaiteitainenL4R;
	}
	/**
	 * @param kaiteitainenL4R the kaiteitainenL4R to set
	 */
	public void setKaiteitainenL4R(Integer kaiteitainenL4R) {
		this.kaiteitainenL4R = kaiteitainenL4R;
	}
	/**
	 * @return the kaiteiymdL4R
	 */
	public String getKaiteiymdL4R() {
		return kaiteiymdL4R;
	}
	/**
	 * @param kaiteiymdL4R the kaiteiymdL4R to set
	 */
	public void setKaiteiymdL4R(String kaiteiymdL4R) {
		this.kaiteiymdL4R = kaiteiymdL4R;
	}
	/**
	 * @return the yusenzogenkbnL4R
	 */
	public String getYusenzogenkbnL4R() {
		return yusenzogenkbnL4R;
	}
	/**
	 * @param yusenzogenkbnL4R the yusenzogenkbnL4R to set
	 */
	public void setYusenzogenkbnL4R(String yusenzogenkbnL4R) {
		this.yusenzogenkbnL4R = yusenzogenkbnL4R;
	}
	/**
	 * @return the ksgensonruigkL5R
	 */
	public Long getKsgensonruigkL5R() {
		return ksgensonruigkL5R;
	}
	/**
	 * @param ksgensonruigkL5R the ksgensonruigkL5R to set
	 */
	public void setKsgensonruigkL5R(Long ksgensonruigkL5R) {
		this.ksgensonruigkL5R = ksgensonruigkL5R;
	}
	/**
	 * @return the togensongkL5R
	 */
	public Long getTogensongkL5R() {
		return togensongkL5R;
	}
	/**
	 * @param togensongkL5R the togensongkL5R to set
	 */
	public void setTogensongkL5R(Long togensongkL5R) {
		this.togensongkL5R = togensongkL5R;
	}
	/**
	 * @return the skkcalczanzongkL5R
	 */
	public Long getSkkcalczanzongkL5R() {
		return skkcalczanzongkL5R;
	}
	/**
	 * @param skkcalczanzongkL5R the skkcalczanzongkL5R to set
	 */
	public void setSkkcalczanzongkL5R(Long skkcalczanzongkL5R) {
		this.skkcalczanzongkL5R = skkcalczanzongkL5R;
	}
	/**
	 * @return the gensonbokaL5R
	 */
	public Long getGensonbokaL5R() {
		return gensonbokaL5R;
	}
	/**
	 * @param gensonbokaL5R the gensonbokaL5R to set
	 */
	public void setGensonbokaL5R(Long gensonbokaL5R) {
		this.gensonbokaL5R = gensonbokaL5R;
	}
	/**
	 * @return the gensonmaetainenL5R
	 */
	public Integer getGensonmaetainenL5R() {
		return gensonmaetainenL5R;
	}
	/**
	 * @param gensonmaetainenL5R the gensonmaetainenL5R to set
	 */
	public void setGensonmaetainenL5R(Integer gensonmaetainenL5R) {
		this.gensonmaetainenL5R = gensonmaetainenL5R;
	}
	/**
	 * @return the gensonmaeskkmmsuL5R
	 */
	public Integer getGensonmaeskkmmsuL5R() {
		return gensonmaeskkmmsuL5R;
	}
	/**
	 * @param gensonmaeskkmmsuL5R the gensonmaeskkmmsuL5R to set
	 */
	public void setGensonmaeskkmmsuL5R(Integer gensonmaeskkmmsuL5R) {
		this.gensonmaeskkmmsuL5R = gensonmaeskkmmsuL5R;
	}
	/**
	 * @return the gensonymdL5R
	 */
	public String getGensonymdL5R() {
		return gensonymdL5R;
	}
	/**
	 * @param gensonymdL5R the gensonymdL5R to set
	 */
	public void setGensonymdL5R(String gensonymdL5R) {
		this.gensonymdL5R = gensonymdL5R;
	}
	/**
	 * @return the genshistkgkL5R
	 */
	public Long getGenshistkgkL5R() {
		return genshistkgkL5R;
	}
	/**
	 * @param genshistkgkL5R the genshistkgkL5R to set
	 */
	public void setGenshistkgkL5R(Long genshistkgkL5R) {
		this.genshistkgkL5R = genshistkgkL5R;
	}
	/**
	 * @return the kaiteistkgkL5R
	 */
	public Long getKaiteistkgkL5R() {
		return kaiteistkgkL5R;
	}
	/**
	 * @param kaiteistkgkL5R the kaiteistkgkL5R to set
	 */
	public void setKaiteistkgkL5R(Long kaiteistkgkL5R) {
		this.kaiteistkgkL5R = kaiteistkgkL5R;
	}
	/**
	 * @return the kaiteitainenL5R
	 */
	public Integer getKaiteitainenL5R() {
		return kaiteitainenL5R;
	}
	/**
	 * @param kaiteitainenL5R the kaiteitainenL5R to set
	 */
	public void setKaiteitainenL5R(Integer kaiteitainenL5R) {
		this.kaiteitainenL5R = kaiteitainenL5R;
	}
	/**
	 * @return the kaiteiymdL5R
	 */
	public String getKaiteiymdL5R() {
		return kaiteiymdL5R;
	}
	/**
	 * @param kaiteiymdL5R the kaiteiymdL5R to set
	 */
	public void setKaiteiymdL5R(String kaiteiymdL5R) {
		this.kaiteiymdL5R = kaiteiymdL5R;
	}
	/**
	 * @return the yusenzogenkbnL5R
	 */
	public String getYusenzogenkbnL5R() {
		return yusenzogenkbnL5R;
	}
	/**
	 * @param yusenzogenkbnL5R the yusenzogenkbnL5R to set
	 */
	public void setYusenzogenkbnL5R(String yusenzogenkbnL5R) {
		this.yusenzogenkbnL5R = yusenzogenkbnL5R;
	}
	/**
	 * @return the ksgensonruigkL6R
	 */
	public Long getKsgensonruigkL6R() {
		return ksgensonruigkL6R;
	}
	/**
	 * @param ksgensonruigkL6R the ksgensonruigkL6R to set
	 */
	public void setKsgensonruigkL6R(Long ksgensonruigkL6R) {
		this.ksgensonruigkL6R = ksgensonruigkL6R;
	}
	/**
	 * @return the togensongkL6R
	 */
	public Long getTogensongkL6R() {
		return togensongkL6R;
	}
	/**
	 * @param togensongkL6R the togensongkL6R to set
	 */
	public void setTogensongkL6R(Long togensongkL6R) {
		this.togensongkL6R = togensongkL6R;
	}
	/**
	 * @return the skkcalczanzongkL6R
	 */
	public Long getSkkcalczanzongkL6R() {
		return skkcalczanzongkL6R;
	}
	/**
	 * @param skkcalczanzongkL6R the skkcalczanzongkL6R to set
	 */
	public void setSkkcalczanzongkL6R(Long skkcalczanzongkL6R) {
		this.skkcalczanzongkL6R = skkcalczanzongkL6R;
	}
	/**
	 * @return the gensonbokaL6R
	 */
	public Long getGensonbokaL6R() {
		return gensonbokaL6R;
	}
	/**
	 * @param gensonbokaL6R the gensonbokaL6R to set
	 */
	public void setGensonbokaL6R(Long gensonbokaL6R) {
		this.gensonbokaL6R = gensonbokaL6R;
	}
	/**
	 * @return the gensonmaetainenL6R
	 */
	public Integer getGensonmaetainenL6R() {
		return gensonmaetainenL6R;
	}
	/**
	 * @param gensonmaetainenL6R the gensonmaetainenL6R to set
	 */
	public void setGensonmaetainenL6R(Integer gensonmaetainenL6R) {
		this.gensonmaetainenL6R = gensonmaetainenL6R;
	}
	/**
	 * @return the gensonmaeskkmmsuL6R
	 */
	public Integer getGensonmaeskkmmsuL6R() {
		return gensonmaeskkmmsuL6R;
	}
	/**
	 * @param gensonmaeskkmmsuL6R the gensonmaeskkmmsuL6R to set
	 */
	public void setGensonmaeskkmmsuL6R(Integer gensonmaeskkmmsuL6R) {
		this.gensonmaeskkmmsuL6R = gensonmaeskkmmsuL6R;
	}
	/**
	 * @return the gensonymdL6R
	 */
	public String getGensonymdL6R() {
		return gensonymdL6R;
	}
	/**
	 * @param gensonymdL6R the gensonymdL6R to set
	 */
	public void setGensonymdL6R(String gensonymdL6R) {
		this.gensonymdL6R = gensonymdL6R;
	}
	/**
	 * @return the genshistkgkL6R
	 */
	public Long getGenshistkgkL6R() {
		return genshistkgkL6R;
	}
	/**
	 * @param genshistkgkL6R the genshistkgkL6R to set
	 */
	public void setGenshistkgkL6R(Long genshistkgkL6R) {
		this.genshistkgkL6R = genshistkgkL6R;
	}
	/**
	 * @return the kaiteistkgkL6R
	 */
	public Long getKaiteistkgkL6R() {
		return kaiteistkgkL6R;
	}
	/**
	 * @param kaiteistkgkL6R the kaiteistkgkL6R to set
	 */
	public void setKaiteistkgkL6R(Long kaiteistkgkL6R) {
		this.kaiteistkgkL6R = kaiteistkgkL6R;
	}
	/**
	 * @return the kaiteitainenL6R
	 */
	public Integer getKaiteitainenL6R() {
		return kaiteitainenL6R;
	}
	/**
	 * @param kaiteitainenL6R the kaiteitainenL6R to set
	 */
	public void setKaiteitainenL6R(Integer kaiteitainenL6R) {
		this.kaiteitainenL6R = kaiteitainenL6R;
	}
	/**
	 * @return the kaiteiymdL6R
	 */
	public String getKaiteiymdL6R() {
		return kaiteiymdL6R;
	}
	/**
	 * @param kaiteiymdL6R the kaiteiymdL6R to set
	 */
	public void setKaiteiymdL6R(String kaiteiymdL6R) {
		this.kaiteiymdL6R = kaiteiymdL6R;
	}
	/**
	 * @return the yusenzogenkbnL6R
	 */
	public String getYusenzogenkbnL6R() {
		return yusenzogenkbnL6R;
	}
	/**
	 * @param yusenzogenkbnL6R the yusenzogenkbnL6R to set
	 */
	public void setYusenzogenkbnL6R(String yusenzogenkbnL6R) {
		this.yusenzogenkbnL6R = yusenzogenkbnL6R;
	}
	/**
	 * @return the updidR
	 */
	public String getUpdidR() {
		return updidR;
	}
	/**
	 * @param updidR the updidR to set
	 */
	public void setUpdidR(String updidR) {
		this.updidR = updidR;
	}
	/**
	 * @return the updymdR
	 */
	public String getUpdymdR() {
		return updymdR;
	}
	/**
	 * @param updymdR the updymdR to set
	 */
	public void setUpdymdR(String updymdR) {
		this.updymdR = updymdR;
	}
	/**
	 * @return the shisanNum
	 */
	public String getShisanNum() {
		return shisanNum;
	}
	/**
	 * @param shisanNum the shisanNum to set
	 */
	public void setShisanNum(String shisanNum) {
		this.shisanNum = shisanNum;
	}
	/**
	 * @return the cpkeijoymdF
	 */
	public String getCpkeijoymdF() {
		return cpkeijoymdF;
	}
	/**
	 * @param cpkeijoymdF the cpkeijoymdF to set
	 */
	public void setCpkeijoymdF(String cpkeijoymdF) {
		this.cpkeijoymdF = cpkeijoymdF;
	}
	/**
	 * @return the furikaeymdF
	 */
	public String getFurikaeymdF() {
		return furikaeymdF;
	}
	/**
	 * @param furikaeymdF the furikaeymdF to set
	 */
	public void setFurikaeymdF(String furikaeymdF) {
		this.furikaeymdF = furikaeymdF;
	}
	/**
	 * @return the stkymdF
	 */
	public String getStkymdF() {
		return stkymdF;
	}
	/**
	 * @param stkymdF the stkymdF to set
	 */
	public void setStkymdF(String stkymdF) {
		this.stkymdF = stkymdF;
	}
	/**
	 * @return the kadoymdF
	 */
	public String getKadoymdF() {
		return kadoymdF;
	}
	/**
	 * @param kadoymdF the kadoymdF to set
	 */
	public void setKadoymdF(String kadoymdF) {
		this.kadoymdF = kadoymdF;
	}
	/**
	 * @return the jbkymdF
	 */
	public String getJbkymdF() {
		return jbkymdF;
	}
	/**
	 * @param jbkymdF the jbkymdF to set
	 */
	public void setJbkymdF(String jbkymdF) {
		this.jbkymdF = jbkymdF;
	}
	/**
	 * @return the idoymdF
	 */
	public String getIdoymdF() {
		return idoymdF;
	}
	/**
	 * @param idoymdF the idoymdF to set
	 */
	public void setIdoymdF(String idoymdF) {
		this.idoymdF = idoymdF;
	}
	/**
	 * @return the genshistkymdF
	 */
	public String getGenshistkymdF() {
		return genshistkymdF;
	}
	/**
	 * @param genshistkymdF the genshistkymdF to set
	 */
	public void setGenshistkymdF(String genshistkymdF) {
		this.genshistkymdF = genshistkymdF;
	}
	/**
	 * @return the gensonymdkF
	 */
	public String getGensonymdkF() {
		return gensonymdkF;
	}
	/**
	 * @param gensonymdkF the gensonymdkF to set
	 */
	public void setGensonymdkF(String gensonymdkF) {
		this.gensonymdkF = gensonymdkF;
	}
	/**
	 * @return the kaiteiymdkF
	 */
	public String getKaiteiymdkF() {
		return kaiteiymdkF;
	}
	/**
	 * @param kaiteiymdkF the kaiteiymdkF to set
	 */
	public void setKaiteiymdkF(String kaiteiymdkF) {
		this.kaiteiymdkF = kaiteiymdkF;
	}
	/**
	 * @return the gensonymdzF
	 */
	public String getGensonymdzF() {
		return gensonymdzF;
	}
	/**
	 * @param gensonymdzF the gensonymdzF to set
	 */
	public void setGensonymdzF(String gensonymdzF) {
		this.gensonymdzF = gensonymdzF;
	}
	/**
	 * @return the kaiteiymdzF
	 */
	public String getKaiteiymdzF() {
		return kaiteiymdzF;
	}
	/**
	 * @param kaiteiymdzF the kaiteiymdzF to set
	 */
	public void setKaiteiymdzF(String kaiteiymdzF) {
		this.kaiteiymdzF = kaiteiymdzF;
	}
	/**
	 * @return the gensonymdL3F
	 */
	public String getGensonymdL3F() {
		return gensonymdL3F;
	}
	/**
	 * @param gensonymdL3F the gensonymdL3F to set
	 */
	public void setGensonymdL3F(String gensonymdL3F) {
		this.gensonymdL3F = gensonymdL3F;
	}
	/**
	 * @return the kaiteiymdL3F
	 */
	public String getKaiteiymdL3F() {
		return kaiteiymdL3F;
	}
	/**
	 * @param kaiteiymdL3F the kaiteiymdL3F to set
	 */
	public void setKaiteiymdL3F(String kaiteiymdL3F) {
		this.kaiteiymdL3F = kaiteiymdL3F;
	}
	/**
	 * @return the gensonymdL4F
	 */
	public String getGensonymdL4F() {
		return gensonymdL4F;
	}
	/**
	 * @param gensonymdL4F the gensonymdL4F to set
	 */
	public void setGensonymdL4F(String gensonymdL4F) {
		this.gensonymdL4F = gensonymdL4F;
	}
	/**
	 * @return the kaiteiymdL4F
	 */
	public String getKaiteiymdL4F() {
		return kaiteiymdL4F;
	}
	/**
	 * @param kaiteiymdL4F the kaiteiymdL4F to set
	 */
	public void setKaiteiymdL4F(String kaiteiymdL4F) {
		this.kaiteiymdL4F = kaiteiymdL4F;
	}
	/**
	 * @return the gensonymdL5F
	 */
	public String getGensonymdL5F() {
		return gensonymdL5F;
	}
	/**
	 * @param gensonymdL5F the gensonymdL5F to set
	 */
	public void setGensonymdL5F(String gensonymdL5F) {
		this.gensonymdL5F = gensonymdL5F;
	}
	/**
	 * @return the kaiteiymdL5F
	 */
	public String getKaiteiymdL5F() {
		return kaiteiymdL5F;
	}
	/**
	 * @param kaiteiymdL5F the kaiteiymdL5F to set
	 */
	public void setKaiteiymdL5F(String kaiteiymdL5F) {
		this.kaiteiymdL5F = kaiteiymdL5F;
	}
	/**
	 * @return the gensonymdL6F
	 */
	public String getGensonymdL6F() {
		return gensonymdL6F;
	}
	/**
	 * @param gensonymdL6F the gensonymdL6F to set
	 */
	public void setGensonymdL6F(String gensonymdL6F) {
		this.gensonymdL6F = gensonymdL6F;
	}
	/**
	 * @return the kaiteiymdL6F
	 */
	public String getKaiteiymdL6F() {
		return kaiteiymdL6F;
	}
	/**
	 * @param kaiteiymdL6F the kaiteiymdL6F to set
	 */
	public void setKaiteiymdL6F(String kaiteiymdL6F) {
		this.kaiteiymdL6F = kaiteiymdL6F;
	}
	/**
	 * @return the updymdF
	 */
	public String getUpdymdF() {
		return updymdF;
	}
	/**
	 * @param updymdF the updymdF to set
	 */
	public void setUpdymdF(String updymdF) {
		this.updymdF = updymdF;
	}
	/**
	 * @return the addUpPlanDateF
	 */
	public String getAddUpPlanDateF() {
		return addUpPlanDateF;
	}
	/**
	 * @param addUpPlanDateF the addUpPlanDateF to set
	 */
	public void setAddUpPlanDateF(String addUpPlanDateF) {
		this.addUpPlanDateF = addUpPlanDateF;
	}
	/**
	 * @return the yukyustymkF
	 */
	public String getYukyustymkF() {
		return yukyustymkF;
	}
	/**
	 * @param yukyustymkF the yukyustymkF to set
	 */
	public void setYukyustymkF(String yukyustymkF) {
		this.yukyustymkF = yukyustymkF;
	}
	/**
	 * @return the yukyustymzF
	 */
	public String getYukyustymzF() {
		return yukyustymzF;
	}
	/**
	 * @param yukyustymzF the yukyustymzF to set
	 */
	public void setYukyustymzF(String yukyustymzF) {
		this.yukyustymzF = yukyustymzF;
	}
	/**
	 * @return the yukyustymL3F
	 */
	public String getYukyustymL3F() {
		return yukyustymL3F;
	}
	/**
	 * @param yukyustymL3F the yukyustymL3F to set
	 */
	public void setYukyustymL3F(String yukyustymL3F) {
		this.yukyustymL3F = yukyustymL3F;
	}
	/**
	 * @return the yukyustymL4F
	 */
	public String getYukyustymL4F() {
		return yukyustymL4F;
	}
	/**
	 * @param yukyustymL4F the yukyustymL4F to set
	 */
	public void setYukyustymL4F(String yukyustymL4F) {
		this.yukyustymL4F = yukyustymL4F;
	}
	/**
	 * @return the yukyustymL5F
	 */
	public String getYukyustymL5F() {
		return yukyustymL5F;
	}
	/**
	 * @param yukyustymL5F the yukyustymL5F to set
	 */
	public void setYukyustymL5F(String yukyustymL5F) {
		this.yukyustymL5F = yukyustymL5F;
	}
	/**
	 * @return the yukyustymL6F
	 */
	public String getYukyustymL6F() {
		return yukyustymL6F;
	}
	/**
	 * @param yukyustymL6F the yukyustymL6F to set
	 */
	public void setYukyustymL6F(String yukyustymL6F) {
		this.yukyustymL6F = yukyustymL6F;
	}
	/**
	 * @return the yukyufkymkF
	 */
	public String getYukyufkymkF() {
		return yukyufkymkF;
	}
	/**
	 * @param yukyufkymkF the yukyufkymkF to set
	 */
	public void setYukyufkymkF(String yukyufkymkF) {
		this.yukyufkymkF = yukyufkymkF;
	}
	/**
	 * @return the yukyufkymzF
	 */
	public String getYukyufkymzF() {
		return yukyufkymzF;
	}
	/**
	 * @param yukyufkymzF the yukyufkymzF to set
	 */
	public void setYukyufkymzF(String yukyufkymzF) {
		this.yukyufkymzF = yukyufkymzF;
	}
	/**
	 * @return the yukyufkymL3F
	 */
	public String getYukyufkymL3F() {
		return yukyufkymL3F;
	}
	/**
	 * @param yukyufkymL3F the yukyufkymL3F to set
	 */
	public void setYukyufkymL3F(String yukyufkymL3F) {
		this.yukyufkymL3F = yukyufkymL3F;
	}
	/**
	 * @return the yukyufkymL4F
	 */
	public String getYukyufkymL4F() {
		return yukyufkymL4F;
	}
	/**
	 * @param yukyufkymL4F the yukyufkymL4F to set
	 */
	public void setYukyufkymL4F(String yukyufkymL4F) {
		this.yukyufkymL4F = yukyufkymL4F;
	}
	/**
	 * @return the yukyufkymL5F
	 */
	public String getYukyufkymL5F() {
		return yukyufkymL5F;
	}
	/**
	 * @param yukyufkymL5F the yukyufkymL5F to set
	 */
	public void setYukyufkymL5F(String yukyufkymL5F) {
		this.yukyufkymL5F = yukyufkymL5F;
	}
	/**
	 * @return the yukyufkymL6F
	 */
	public String getYukyufkymL6F() {
		return yukyufkymL6F;
	}
	/**
	 * @param yukyufkymL6F the yukyufkymL6F to set
	 */
	public void setYukyufkymL6F(String yukyufkymL6F) {
		this.yukyufkymL6F = yukyufkymL6F;
	}
	/**
	 * @return the saimukeijoymdRF
	 */
	public String getSaimukeijoymdRF() {
		return saimukeijoymdRF;
	}
	/**
	 * @param saimukeijoymdRF the saimukeijoymdRF to set
	 */
	public void setSaimukeijoymdRF(String saimukeijoymdRF) {
		this.saimukeijoymdRF = saimukeijoymdRF;
	}
	/**
	 * @return the saimurikoymdRF
	 */
	public String getSaimurikoymdRF() {
		return saimurikoymdRF;
	}
	/**
	 * @param saimurikoymdRF the saimurikoymdRF to set
	 */
	public void setSaimurikoymdRF(String saimurikoymdRF) {
		this.saimurikoymdRF = saimurikoymdRF;
	}
	/**
	 * @return the kadoymdRF
	 */
	public String getKadoymdRF() {
		return kadoymdRF;
	}
	/**
	 * @param kadoymdRF the kadoymdRF to set
	 */
	public void setKadoymdRF(String kadoymdRF) {
		this.kadoymdRF = kadoymdRF;
	}
	/**
	 * @return the jbkymdRF
	 */
	public String getJbkymdRF() {
		return jbkymdRF;
	}
	/**
	 * @param jbkymdRF the jbkymdRF to set
	 */
	public void setJbkymdRF(String jbkymdRF) {
		this.jbkymdRF = jbkymdRF;
	}
	/**
	 * @return the idoymdRF
	 */
	public String getIdoymdRF() {
		return idoymdRF;
	}
	/**
	 * @param idoymdRF the idoymdRF to set
	 */
	public void setIdoymdRF(String idoymdRF) {
		this.idoymdRF = idoymdRF;
	}
	/**
	 * @return the mitsumorihkymdkRF
	 */
	public String getMitsumorihkymdkRF() {
		return mitsumorihkymdkRF;
	}
	/**
	 * @param mitsumorihkymdkRF the mitsumorihkymdkRF to set
	 */
	public void setMitsumorihkymdkRF(String mitsumorihkymdkRF) {
		this.mitsumorihkymdkRF = mitsumorihkymdkRF;
	}
	/**
	 * @return the mitsumorihkymdzRF
	 */
	public String getMitsumorihkymdzRF() {
		return mitsumorihkymdzRF;
	}
	/**
	 * @param mitsumorihkymdzRF the mitsumorihkymdzRF to set
	 */
	public void setMitsumorihkymdzRF(String mitsumorihkymdzRF) {
		this.mitsumorihkymdzRF = mitsumorihkymdzRF;
	}
	/**
	 * @return the mitsumorihkymdL3RF
	 */
	public String getMitsumorihkymdL3RF() {
		return mitsumorihkymdL3RF;
	}
	/**
	 * @param mitsumorihkymdL3RF the mitsumorihkymdL3RF to set
	 */
	public void setMitsumorihkymdL3RF(String mitsumorihkymdL3RF) {
		this.mitsumorihkymdL3RF = mitsumorihkymdL3RF;
	}
	/**
	 * @return the mitsumorihkymdL4RF
	 */
	public String getMitsumorihkymdL4RF() {
		return mitsumorihkymdL4RF;
	}
	/**
	 * @param mitsumorihkymdL4RF the mitsumorihkymdL4RF to set
	 */
	public void setMitsumorihkymdL4RF(String mitsumorihkymdL4RF) {
		this.mitsumorihkymdL4RF = mitsumorihkymdL4RF;
	}
	/**
	 * @return the mitsumorihkymdL5RF
	 */
	public String getMitsumorihkymdL5RF() {
		return mitsumorihkymdL5RF;
	}
	/**
	 * @param mitsumorihkymdL5RF the mitsumorihkymdL5RF to set
	 */
	public void setMitsumorihkymdL5RF(String mitsumorihkymdL5RF) {
		this.mitsumorihkymdL5RF = mitsumorihkymdL5RF;
	}
	/**
	 * @return the mitsumorihkymdL6RF
	 */
	public String getMitsumorihkymdL6RF() {
		return mitsumorihkymdL6RF;
	}
	/**
	 * @param mitsumorihkymdL6RF the mitsumorihkymdL6RF to set
	 */
	public void setMitsumorihkymdL6RF(String mitsumorihkymdL6RF) {
		this.mitsumorihkymdL6RF = mitsumorihkymdL6RF;
	}
	/**
	 * @return the famitsumorihkymdkRF
	 */
	public String getFamitsumorihkymdkRF() {
		return famitsumorihkymdkRF;
	}
	/**
	 * @param famitsumorihkymdkRF the famitsumorihkymdkRF to set
	 */
	public void setFamitsumorihkymdkRF(String famitsumorihkymdkRF) {
		this.famitsumorihkymdkRF = famitsumorihkymdkRF;
	}
	/**
	 * @return the famitsumorihkymdzRF
	 */
	public String getFamitsumorihkymdzRF() {
		return famitsumorihkymdzRF;
	}
	/**
	 * @param famitsumorihkymdzRF the famitsumorihkymdzRF to set
	 */
	public void setFamitsumorihkymdzRF(String famitsumorihkymdzRF) {
		this.famitsumorihkymdzRF = famitsumorihkymdzRF;
	}
	/**
	 * @return the famitsumorihkymdL3RF
	 */
	public String getFamitsumorihkymdL3RF() {
		return famitsumorihkymdL3RF;
	}
	/**
	 * @param famitsumorihkymdL3RF the famitsumorihkymdL3RF to set
	 */
	public void setFamitsumorihkymdL3RF(String famitsumorihkymdL3RF) {
		this.famitsumorihkymdL3RF = famitsumorihkymdL3RF;
	}
	/**
	 * @return the famitsumorihkymdL4RF
	 */
	public String getFamitsumorihkymdL4RF() {
		return famitsumorihkymdL4RF;
	}
	/**
	 * @param famitsumorihkymdL4RF the famitsumorihkymdL4RF to set
	 */
	public void setFamitsumorihkymdL4RF(String famitsumorihkymdL4RF) {
		this.famitsumorihkymdL4RF = famitsumorihkymdL4RF;
	}
	/**
	 * @return the famitsumorihkymdL5RF
	 */
	public String getFamitsumorihkymdL5RF() {
		return famitsumorihkymdL5RF;
	}
	/**
	 * @param famitsumorihkymdL5RF the famitsumorihkymdL5RF to set
	 */
	public void setFamitsumorihkymdL5RF(String famitsumorihkymdL5RF) {
		this.famitsumorihkymdL5RF = famitsumorihkymdL5RF;
	}
	/**
	 * @return the famitsumorihkymdL6RF
	 */
	public String getFamitsumorihkymdL6RF() {
		return famitsumorihkymdL6RF;
	}
	/**
	 * @param famitsumorihkymdL6RF the famitsumorihkymdL6RF to set
	 */
	public void setFamitsumorihkymdL6RF(String famitsumorihkymdL6RF) {
		this.famitsumorihkymdL6RF = famitsumorihkymdL6RF;
	}
	/**
	 * @return the chukokbnName
	 */
	public String getChukokbnName() {
		return chukokbnName;
	}
	/**
	 * @param chukokbnName the chukokbnName to set
	 */
	public void setChukokbnName(String chukokbnName) {
		this.chukokbnName = chukokbnName;
	}
	/**
	 * @return the skkshisankbnName
	 */
	public String getSkkshisankbnName() {
		return skkshisankbnName;
	}
	/**
	 * @param skkshisankbnName the skkshisankbnName to set
	 */
	public void setSkkshisankbnName(String skkshisankbnName) {
		this.skkshisankbnName = skkshisankbnName;
	}
	/**
	 * @return the askkbnName
	 */
	public String getAskkbnName() {
		return askkbnName;
	}
	/**
	 * @param askkbnName the askkbnName to set
	 */
	public void setAskkbnName(String askkbnName) {
		this.askkbnName = askkbnName;
	}
	/**
	 * @return the tkkbnName
	 */
	public String getTkkbnName() {
		return tkkbnName;
	}
	/**
	 * @param tkkbnName the tkkbnName to set
	 */
	public void setTkkbnName(String tkkbnName) {
		this.tkkbnName = tkkbnName;
	}
	/**
	 * @return the shuyoshisankbnName
	 */
	public String getShuyoshisankbnName() {
		return shuyoshisankbnName;
	}
	/**
	 * @param shuyoshisankbnName the shuyoshisankbnName to set
	 */
	public void setShuyoshisankbnName(String shuyoshisankbnName) {
		this.shuyoshisankbnName = shuyoshisankbnName;
	}
	/**
	 * @return the gappeiukekbnName
	 */
	public String getGappeiukekbnName() {
		return gappeiukekbnName;
	}
	/**
	 * @param gappeiukekbnName the gappeiukekbnName to set
	 */
	public void setGappeiukekbnName(String gappeiukekbnName) {
		this.gappeiukekbnName = gappeiukekbnName;
	}
	/**
	 * @return the jksaimukeijokbnName
	 */
	public String getJksaimukeijokbnName() {
		return jksaimukeijokbnName;
	}
	/**
	 * @param jksaimukeijokbnName the jksaimukeijokbnName to set
	 */
	public void setJksaimukeijokbnName(String jksaimukeijokbnName) {
		this.jksaimukeijokbnName = jksaimukeijokbnName;
	}
	/**
	 * @return the skkhokName
	 */
	public String getSkkhokName() {
		return skkhokName;
	}
	/**
	 * @param skkhokName the skkhokName to set
	 */
	public void setSkkhokName(String skkhokName) {
		this.skkhokName = skkhokName;
	}
	/**
	 * @return the skkhozName
	 */
	public String getSkkhozName() {
		return skkhozName;
	}
	/**
	 * @param skkhozName the skkhozName to set
	 */
	public void setSkkhozName(String skkhozName) {
		this.skkhozName = skkhozName;
	}
	/**
	 * @return the skkhoL3Name
	 */
	public String getSkkhoL3Name() {
		return skkhoL3Name;
	}
	/**
	 * @param skkhoL3Name the skkhoL3Name to set
	 */
	public void setSkkhoL3Name(String skkhoL3Name) {
		this.skkhoL3Name = skkhoL3Name;
	}
	/**
	 * @return the skkhoL4Name
	 */
	public String getSkkhoL4Name() {
		return skkhoL4Name;
	}
	/**
	 * @param skkhoL4Name the skkhoL4Name to set
	 */
	public void setSkkhoL4Name(String skkhoL4Name) {
		this.skkhoL4Name = skkhoL4Name;
	}
	/**
	 * @return the skkhoL5Name
	 */
	public String getSkkhoL5Name() {
		return skkhoL5Name;
	}
	/**
	 * @param skkhoL5Name the skkhoL5Name to set
	 */
	public void setSkkhoL5Name(String skkhoL5Name) {
		this.skkhoL5Name = skkhoL5Name;
	}
	/**
	 * @return the skkhoL6Name
	 */
	public String getSkkhoL6Name() {
		return skkhoL6Name;
	}
	/**
	 * @param skkhoL6Name the skkhoL6Name to set
	 */
	public void setSkkhoL6Name(String skkhoL6Name) {
		this.skkhoL6Name = skkhoL6Name;
	}
	/**
	 * @return the shonencalckbnkName
	 */
	public String getShonencalckbnkName() {
		return shonencalckbnkName;
	}
	/**
	 * @param shonencalckbnkName the shonencalckbnkName to set
	 */
	public void setShonencalckbnkName(String shonencalckbnkName) {
		this.shonencalckbnkName = shonencalckbnkName;
	}
	/**
	 * @return the shonencalckbnzName
	 */
	public String getShonencalckbnzName() {
		return shonencalckbnzName;
	}
	/**
	 * @param shonencalckbnzName the shonencalckbnzName to set
	 */
	public void setShonencalckbnzName(String shonencalckbnzName) {
		this.shonencalckbnzName = shonencalckbnzName;
	}
	/**
	 * @return the shonencalckbnL3Name
	 */
	public String getShonencalckbnL3Name() {
		return shonencalckbnL3Name;
	}
	/**
	 * @param shonencalckbnL3Name the shonencalckbnL3Name to set
	 */
	public void setShonencalckbnL3Name(String shonencalckbnL3Name) {
		this.shonencalckbnL3Name = shonencalckbnL3Name;
	}
	/**
	 * @return the shonencalckbnL4Name
	 */
	public String getShonencalckbnL4Name() {
		return shonencalckbnL4Name;
	}
	/**
	 * @param shonencalckbnL4Name the shonencalckbnL4Name to set
	 */
	public void setShonencalckbnL4Name(String shonencalckbnL4Name) {
		this.shonencalckbnL4Name = shonencalckbnL4Name;
	}
	/**
	 * @return the shonencalckbnL5Name
	 */
	public String getShonencalckbnL5Name() {
		return shonencalckbnL5Name;
	}
	/**
	 * @param shonencalckbnL5Name the shonencalckbnL5Name to set
	 */
	public void setShonencalckbnL5Name(String shonencalckbnL5Name) {
		this.shonencalckbnL5Name = shonencalckbnL5Name;
	}
	/**
	 * @return the shonencalckbnL6Name
	 */
	public String getShonencalckbnL6Name() {
		return shonencalckbnL6Name;
	}
	/**
	 * @param shonencalckbnL6Name the shonencalckbnL6Name to set
	 */
	public void setShonencalckbnL6Name(String shonencalckbnL6Name) {
		this.shonencalckbnL6Name = shonencalckbnL6Name;
	}
	/**
	 * @return the skkkanryoflgkName
	 */
	public String getSkkkanryoflgkName() {
		return skkkanryoflgkName;
	}
	/**
	 * @param skkkanryoflgkName the skkkanryoflgkName to set
	 */
	public void setSkkkanryoflgkName(String skkkanryoflgkName) {
		this.skkkanryoflgkName = skkkanryoflgkName;
	}
	/**
	 * @return the skkkanryoflgzName
	 */
	public String getSkkkanryoflgzName() {
		return skkkanryoflgzName;
	}
	/**
	 * @param skkkanryoflgzName the skkkanryoflgzName to set
	 */
	public void setSkkkanryoflgzName(String skkkanryoflgzName) {
		this.skkkanryoflgzName = skkkanryoflgzName;
	}
	/**
	 * @return the skkkanryoflgL3Name
	 */
	public String getSkkkanryoflgL3Name() {
		return skkkanryoflgL3Name;
	}
	/**
	 * @param skkkanryoflgL3Name the skkkanryoflgL3Name to set
	 */
	public void setSkkkanryoflgL3Name(String skkkanryoflgL3Name) {
		this.skkkanryoflgL3Name = skkkanryoflgL3Name;
	}
	/**
	 * @return the skkkanryoflgL4Name
	 */
	public String getSkkkanryoflgL4Name() {
		return skkkanryoflgL4Name;
	}
	/**
	 * @param skkkanryoflgL4Name the skkkanryoflgL4Name to set
	 */
	public void setSkkkanryoflgL4Name(String skkkanryoflgL4Name) {
		this.skkkanryoflgL4Name = skkkanryoflgL4Name;
	}
	/**
	 * @return the skkkanryoflgL5Name
	 */
	public String getSkkkanryoflgL5Name() {
		return skkkanryoflgL5Name;
	}
	/**
	 * @param skkkanryoflgL5Name the skkkanryoflgL5Name to set
	 */
	public void setSkkkanryoflgL5Name(String skkkanryoflgL5Name) {
		this.skkkanryoflgL5Name = skkkanryoflgL5Name;
	}
	/**
	 * @return the skkkanryoflgL6Name
	 */
	public String getSkkkanryoflgL6Name() {
		return skkkanryoflgL6Name;
	}
	/**
	 * @param skkkanryoflgL6Name the skkkanryoflgL6Name to set
	 */
	public void setSkkkanryoflgL6Name(String skkkanryoflgL6Name) {
		this.skkkanryoflgL6Name = skkkanryoflgL6Name;
	}
	/**
	 * @return the kadoymdL
	 */
	public String getKadoymdL() {
		return kadoymdL;
	}
	/**
	 * @param kadoymdL the kadoymdL to set
	 */
	public void setKadoymdL(String kadoymdL) {
		this.kadoymdL = kadoymdL;
	}
	/**
	 * @return the slipAstPtjCode
	 */
	public String getSlipAstPtjCode() {
		return slipAstPtjCode;
	}
	/**
	 * @param slipAstPtjCode the slipAstPtjCode to set
	 */
	public void setSlipAstPtjCode(String slipAstPtjCode) {
		this.slipAstPtjCode = slipAstPtjCode;
	}
	/**
	 * @return the purCompanyNameL
	 */
	public String getPurCompanyNameL() {
		return purCompanyNameL;
	}
	/**
	 * @param purCompanyNameL the purCompanyNameL to set
	 */
	public void setPurCompanyNameL(String purCompanyNameL) {
		this.purCompanyNameL = purCompanyNameL;
	}
	/**
	 * @return the furikaeNum
	 */
	public String getFurikaeNum() {
		return furikaeNum;
	}
	/**
	 * @param furikaeNum the furikaeNum to set
	 */
	public void setFurikaeNum(String furikaeNum) {
		this.furikaeNum = furikaeNum;
	}
	/**
	 * @return the keijoymdL
	 */
	public String getKeijoymdL() {
		return keijoymdL;
	}
	/**
	 * @param keijoymdL the keijoymdL to set
	 */
	public void setKeijoymdL(String keijoymdL) {
		this.keijoymdL = keijoymdL;
	}
	/**
	 * @return the holStaffCode
	 */
	public String getHolStaffCode() {
		return holStaffCode;
	}
	/**
	 * @param holStaffCode the holStaffCode to set
	 */
	public void setHolStaffCode(String holStaffCode) {
		this.holStaffCode = holStaffCode;
	}
	/**
	 * @return the holStaffName
	 */
	public String getHolStaffName() {
		return holStaffName;
	}
	/**
	 * @param holStaffName the holStaffName to set
	 */
	public void setHolStaffName(String holStaffName) {
		this.holStaffName = holStaffName;
	}
	/**
	 * @return the holCompanyCode
	 */
	public String getHolCompanyCode() {
		return holCompanyCode;
	}
	/**
	 * @param holCompanyCode the holCompanyCode to set
	 */
	public void setHolCompanyCode(String holCompanyCode) {
		this.holCompanyCode = holCompanyCode;
	}
	/**
	 * @return the holSectionCode
	 */
	public String getHolSectionCode() {
		return holSectionCode;
	}
	/**
	 * @param holSectionCode the holSectionCode to set
	 */
	public void setHolSectionCode(String holSectionCode) {
		this.holSectionCode = holSectionCode;
	}
	/**
	 * @return the holSectionYear
	 */
	public Integer getHolSectionYear() {
		return holSectionYear;
	}
	/**
	 * @param holSectionYear the holSectionYear to set
	 */
	public void setHolSectionYear(Integer holSectionYear) {
		this.holSectionYear = holSectionYear;
	}
	/**
	 * @return the holSectionName
	 */
	public String getHolSectionName() {
		return holSectionName;
	}
	/**
	 * @param holSectionName the holSectionName to set
	 */
	public void setHolSectionName(String holSectionName) {
		this.holSectionName = holSectionName;
	}
	/**
	 * @return the dscAttribute1
	 */
	public String getDscAttribute1() {
		return dscAttribute1;
	}
	/**
	 * @param dscAttribute1 the dscAttribute1 to set
	 */
	public void setDscAttribute1(String dscAttribute1) {
		this.dscAttribute1 = dscAttribute1;
	}
	/**
	 * @return the dscAttribute2
	 */
	public String getDscAttribute2() {
		return dscAttribute2;
	}
	/**
	 * @param dscAttribute2 the dscAttribute2 to set
	 */
	public void setDscAttribute2(String dscAttribute2) {
		this.dscAttribute2 = dscAttribute2;
	}
	/**
	 * @return the dscAttribute3
	 */
	public String getDscAttribute3() {
		return dscAttribute3;
	}
	/**
	 * @param dscAttribute3 the dscAttribute3 to set
	 */
	public void setDscAttribute3(String dscAttribute3) {
		this.dscAttribute3 = dscAttribute3;
	}
	/**
	 * @return the dscAttribute4
	 */
	public String getDscAttribute4() {
		return dscAttribute4;
	}
	/**
	 * @param dscAttribute4 the dscAttribute4 to set
	 */
	public void setDscAttribute4(String dscAttribute4) {
		this.dscAttribute4 = dscAttribute4;
	}
	/**
	 * @return the dscAttribute5
	 */
	public String getDscAttribute5() {
		return dscAttribute5;
	}
	/**
	 * @param dscAttribute5 the dscAttribute5 to set
	 */
	public void setDscAttribute5(String dscAttribute5) {
		this.dscAttribute5 = dscAttribute5;
	}
	/**
	 * @return the dscAttribute6
	 */
	public String getDscAttribute6() {
		return dscAttribute6;
	}
	/**
	 * @param dscAttribute6 the dscAttribute6 to set
	 */
	public void setDscAttribute6(String dscAttribute6) {
		this.dscAttribute6 = dscAttribute6;
	}
	/**
	 * @return the dscAttribute7
	 */
	public String getDscAttribute7() {
		return dscAttribute7;
	}
	/**
	 * @param dscAttribute7 the dscAttribute7 to set
	 */
	public void setDscAttribute7(String dscAttribute7) {
		this.dscAttribute7 = dscAttribute7;
	}
	/**
	 * @return the dscAttribute8
	 */
	public String getDscAttribute8() {
		return dscAttribute8;
	}
	/**
	 * @param dscAttribute8 the dscAttribute8 to set
	 */
	public void setDscAttribute8(String dscAttribute8) {
		this.dscAttribute8 = dscAttribute8;
	}
	/**
	 * @return the dscAttribute9
	 */
	public String getDscAttribute9() {
		return dscAttribute9;
	}
	/**
	 * @param dscAttribute9 the dscAttribute9 to set
	 */
	public void setDscAttribute9(String dscAttribute9) {
		this.dscAttribute9 = dscAttribute9;
	}
	/**
	 * @return the dscAttribute10
	 */
	public String getDscAttribute10() {
		return dscAttribute10;
	}
	/**
	 * @param dscAttribute10 the dscAttribute10 to set
	 */
	public void setDscAttribute10(String dscAttribute10) {
		this.dscAttribute10 = dscAttribute10;
	}
	/**
	 * @return the dscAttribute11
	 */
	public String getDscAttribute11() {
		return dscAttribute11;
	}
	/**
	 * @param dscAttribute11 the dscAttribute11 to set
	 */
	public void setDscAttribute11(String dscAttribute11) {
		this.dscAttribute11 = dscAttribute11;
	}
	/**
	 * @return the dscAttribute12
	 */
	public String getDscAttribute12() {
		return dscAttribute12;
	}
	/**
	 * @param dscAttribute12 the dscAttribute12 to set
	 */
	public void setDscAttribute12(String dscAttribute12) {
		this.dscAttribute12 = dscAttribute12;
	}
	/**
	 * @return the dscAttribute13
	 */
	public String getDscAttribute13() {
		return dscAttribute13;
	}
	/**
	 * @param dscAttribute13 the dscAttribute13 to set
	 */
	public void setDscAttribute13(String dscAttribute13) {
		this.dscAttribute13 = dscAttribute13;
	}
	/**
	 * @return the dscAttribute14
	 */
	public String getDscAttribute14() {
		return dscAttribute14;
	}
	/**
	 * @param dscAttribute14 the dscAttribute14 to set
	 */
	public void setDscAttribute14(String dscAttribute14) {
		this.dscAttribute14 = dscAttribute14;
	}
	/**
	 * @return the dscAttribute15
	 */
	public String getDscAttribute15() {
		return dscAttribute15;
	}
	/**
	 * @param dscAttribute15 the dscAttribute15 to set
	 */
	public void setDscAttribute15(String dscAttribute15) {
		this.dscAttribute15 = dscAttribute15;
	}
	/**
	 * @return the dscAttribute16
	 */
	public String getDscAttribute16() {
		return dscAttribute16;
	}
	/**
	 * @param dscAttribute16 the dscAttribute16 to set
	 */
	public void setDscAttribute16(String dscAttribute16) {
		this.dscAttribute16 = dscAttribute16;
	}
	/**
	 * @return the dscAttribute17
	 */
	public String getDscAttribute17() {
		return dscAttribute17;
	}
	/**
	 * @param dscAttribute17 the dscAttribute17 to set
	 */
	public void setDscAttribute17(String dscAttribute17) {
		this.dscAttribute17 = dscAttribute17;
	}
	/**
	 * @return the dscAttribute18
	 */
	public String getDscAttribute18() {
		return dscAttribute18;
	}
	/**
	 * @param dscAttribute18 the dscAttribute18 to set
	 */
	public void setDscAttribute18(String dscAttribute18) {
		this.dscAttribute18 = dscAttribute18;
	}
	/**
	 * @return the dscAttribute19
	 */
	public String getDscAttribute19() {
		return dscAttribute19;
	}
	/**
	 * @param dscAttribute19 the dscAttribute19 to set
	 */
	public void setDscAttribute19(String dscAttribute19) {
		this.dscAttribute19 = dscAttribute19;
	}
	/**
	 * @return the dscAttribute20
	 */
	public String getDscAttribute20() {
		return dscAttribute20;
	}
	/**
	 * @param dscAttribute20 the dscAttribute20 to set
	 */
	public void setDscAttribute20(String dscAttribute20) {
		this.dscAttribute20 = dscAttribute20;
	}
	public String getFamitsumorihkymdk() {
		return famitsumorihkymdk;
	}
	public void setFamitsumorihkymdk(String famitsumorihkymdk) {
		this.famitsumorihkymdk = famitsumorihkymdk;
	}
	public Long getMitsumorihkgobokak() {
		return mitsumorihkgobokak;
	}
	public void setMitsumorihkgobokak(Long mitsumorihkgobokak) {
		this.mitsumorihkgobokak = mitsumorihkgobokak;
	}
	public Long getMitsumorihkmaetainenk() {
		return mitsumorihkmaetainenk;
	}
	public void setMitsumorihkmaetainenk(Long mitsumorihkmaetainenk) {
		this.mitsumorihkmaetainenk = mitsumorihkmaetainenk;
	}
	public Long getMitsumorihkmaeskkmmsuk() {
		return mitsumorihkmaeskkmmsuk;
	}
	public void setMitsumorihkmaeskkmmsuk(Long mitsumorihkmaeskkmmsuk) {
		this.mitsumorihkmaeskkmmsuk = mitsumorihkmaeskkmmsuk;
	}
	public Long getMitsumorihkmaeskkritsuk() {
		return mitsumorihkmaeskkritsuk;
	}
	public void setMitsumorihkmaeskkritsuk(Long mitsumorihkmaeskkritsuk) {
		this.mitsumorihkmaeskkritsuk = mitsumorihkmaeskkritsuk;
	}
	public String getFamitsumorihkymdz() {
		return famitsumorihkymdz;
	}
	public void setFamitsumorihkymdz(String famitsumorihkymdz) {
		this.famitsumorihkymdz = famitsumorihkymdz;
	}
	public Long getMitsumorihkgobokaz() {
		return mitsumorihkgobokaz;
	}
	public void setMitsumorihkgobokaz(Long mitsumorihkgobokaz) {
		this.mitsumorihkgobokaz = mitsumorihkgobokaz;
	}
	public Long getMitsumorihkmaetainenz() {
		return mitsumorihkmaetainenz;
	}
	public void setMitsumorihkmaetainenz(Long mitsumorihkmaetainenz) {
		this.mitsumorihkmaetainenz = mitsumorihkmaetainenz;
	}
	public Long getMitsumorihkmaeskkmmsuz() {
		return mitsumorihkmaeskkmmsuz;
	}
	public void setMitsumorihkmaeskkmmsuz(Long mitsumorihkmaeskkmmsuz) {
		this.mitsumorihkmaeskkmmsuz = mitsumorihkmaeskkmmsuz;
	}
	public Long getMitsumorihkmaeskkritsuz() {
		return mitsumorihkmaeskkritsuz;
	}
	public void setMitsumorihkmaeskkritsuz(Long mitsumorihkmaeskkritsuz) {
		this.mitsumorihkmaeskkritsuz = mitsumorihkmaeskkritsuz;
	}
	public String getFamitsumorihkymdL3() {
		return famitsumorihkymdL3;
	}
	public void setFamitsumorihkymdL3(String famitsumorihkymdL3) {
		this.famitsumorihkymdL3 = famitsumorihkymdL3;
	}
	public Long getMitsumorihkgobokaL3() {
		return mitsumorihkgobokaL3;
	}
	public void setMitsumorihkgobokaL3(Long mitsumorihkgobokaL3) {
		this.mitsumorihkgobokaL3 = mitsumorihkgobokaL3;
	}
	public Long getMitsumorihkmaetainenL3() {
		return mitsumorihkmaetainenL3;
	}
	public void setMitsumorihkmaetainenL3(Long mitsumorihkmaetainenL3) {
		this.mitsumorihkmaetainenL3 = mitsumorihkmaetainenL3;
	}
	public Long getMitsumorihkmaeskkmmsuL3() {
		return mitsumorihkmaeskkmmsuL3;
	}
	public void setMitsumorihkmaeskkmmsuL3(Long mitsumorihkmaeskkmmsuL3) {
		this.mitsumorihkmaeskkmmsuL3 = mitsumorihkmaeskkmmsuL3;
	}
	public Long getMitsumorihkmaeskkritsuL3() {
		return mitsumorihkmaeskkritsuL3;
	}
	public void setMitsumorihkmaeskkritsuL3(Long mitsumorihkmaeskkritsuL3) {
		this.mitsumorihkmaeskkritsuL3 = mitsumorihkmaeskkritsuL3;
	}
	public String getFamitsumorihkymdL4() {
		return famitsumorihkymdL4;
	}
	public void setFamitsumorihkymdL4(String famitsumorihkymdL4) {
		this.famitsumorihkymdL4 = famitsumorihkymdL4;
	}
	public Long getMitsumorihkgobokaL4() {
		return mitsumorihkgobokaL4;
	}
	public void setMitsumorihkgobokaL4(Long mitsumorihkgobokaL4) {
		this.mitsumorihkgobokaL4 = mitsumorihkgobokaL4;
	}
	public Long getMitsumorihkmaetainenL4() {
		return mitsumorihkmaetainenL4;
	}
	public void setMitsumorihkmaetainenL4(Long mitsumorihkmaetainenL4) {
		this.mitsumorihkmaetainenL4 = mitsumorihkmaetainenL4;
	}
	public Long getMitsumorihkmaeskkmmsuL4() {
		return mitsumorihkmaeskkmmsuL4;
	}
	public void setMitsumorihkmaeskkmmsuL4(Long mitsumorihkmaeskkmmsuL4) {
		this.mitsumorihkmaeskkmmsuL4 = mitsumorihkmaeskkmmsuL4;
	}
	public Long getMitsumorihkmaeskkritsuL4() {
		return mitsumorihkmaeskkritsuL4;
	}
	public void setMitsumorihkmaeskkritsuL4(Long mitsumorihkmaeskkritsuL4) {
		this.mitsumorihkmaeskkritsuL4 = mitsumorihkmaeskkritsuL4;
	}
	public String getFamitsumorihkymdL5() {
		return famitsumorihkymdL5;
	}
	public void setFamitsumorihkymdL5(String famitsumorihkymdL5) {
		this.famitsumorihkymdL5 = famitsumorihkymdL5;
	}
	public Long getMitsumorihkgobokaL5() {
		return mitsumorihkgobokaL5;
	}
	public void setMitsumorihkgobokaL5(Long mitsumorihkgobokaL5) {
		this.mitsumorihkgobokaL5 = mitsumorihkgobokaL5;
	}
	public Long getMitsumorihkmaetainenL5() {
		return mitsumorihkmaetainenL5;
	}
	public void setMitsumorihkmaetainenL5(Long mitsumorihkmaetainenL5) {
		this.mitsumorihkmaetainenL5 = mitsumorihkmaetainenL5;
	}
	public Long getMitsumorihkmaeskkmmsuL5() {
		return mitsumorihkmaeskkmmsuL5;
	}
	public void setMitsumorihkmaeskkmmsuL5(Long mitsumorihkmaeskkmmsuL5) {
		this.mitsumorihkmaeskkmmsuL5 = mitsumorihkmaeskkmmsuL5;
	}
	public Long getMitsumorihkmaeskkritsuL5() {
		return mitsumorihkmaeskkritsuL5;
	}
	public void setMitsumorihkmaeskkritsuL5(Long mitsumorihkmaeskkritsuL5) {
		this.mitsumorihkmaeskkritsuL5 = mitsumorihkmaeskkritsuL5;
	}
	public String getFamitsumorihkymdL6() {
		return famitsumorihkymdL6;
	}
	public void setFamitsumorihkymdL6(String famitsumorihkymdL6) {
		this.famitsumorihkymdL6 = famitsumorihkymdL6;
	}
	public Long getMitsumorihkgobokaL6() {
		return mitsumorihkgobokaL6;
	}
	public void setMitsumorihkgobokaL6(Long mitsumorihkgobokaL6) {
		this.mitsumorihkgobokaL6 = mitsumorihkgobokaL6;
	}
	public Long getMitsumorihkmaetainenL6() {
		return mitsumorihkmaetainenL6;
	}
	public void setMitsumorihkmaetainenL6(Long mitsumorihkmaetainenL6) {
		this.mitsumorihkmaetainenL6 = mitsumorihkmaetainenL6;
	}
	public Long getMitsumorihkmaeskkmmsuL6() {
		return mitsumorihkmaeskkmmsuL6;
	}
	public void setMitsumorihkmaeskkmmsuL6(Long mitsumorihkmaeskkmmsuL6) {
		this.mitsumorihkmaeskkmmsuL6 = mitsumorihkmaeskkmmsuL6;
	}
	public Long getMitsumorihkmaeskkritsuL6() {
		return mitsumorihkmaeskkritsuL6;
	}
	public void setMitsumorihkmaeskkritsuL6(Long mitsumorihkmaeskkritsuL6) {
		this.mitsumorihkmaeskkritsuL6 = mitsumorihkmaeskkritsuL6;
	}
	public Long getToToskkgkk() {
		return toToskkgkk;
	}
	public void setToToskkgkk(Long toToskkgkk) {
		this.toToskkgkk = toToskkgkk;
	}
	public Long getToToskkgkz() {
		return toToskkgkz;
	}
	public void setToToskkgkz(Long toToskkgkz) {
		this.toToskkgkz = toToskkgkz;
	}
	public Long getToToskkgkL3() {
		return toToskkgkL3;
	}
	public void setToToskkgkL3(Long toToskkgkL3) {
		this.toToskkgkL3 = toToskkgkL3;
	}
	public Long getToToskkgkL4() {
		return toToskkgkL4;
	}
	public void setToToskkgkL4(Long toToskkgkL4) {
		this.toToskkgkL4 = toToskkgkL4;
	}
	public Long getToToskkgkL5() {
		return toToskkgkL5;
	}
	public void setToToskkgkL5(Long toToskkgkL5) {
		this.toToskkgkL5 = toToskkgkL5;
	}
	public Long getToToskkgkL6() {
		return toToskkgkL6;
	}
	public void setToToskkgkL6(Long toToskkgkL6) {
		this.toToskkgkL6 = toToskkgkL6;
	}
	public Long getKsskkruigkk() {
		return ksskkruigkk;
	}
	public void setKsskkruigkk(Long ksskkruigkk) {
		this.ksskkruigkk = ksskkruigkk;
	}
	public Long getKsskkruigkz() {
		return ksskkruigkz;
	}
	public void setKsskkruigkz(Long ksskkruigkz) {
		this.ksskkruigkz = ksskkruigkz;
	}
	public Long getKsskkruigkL3() {
		return ksskkruigkL3;
	}
	public void setKsskkruigkL3(Long ksskkruigkL3) {
		this.ksskkruigkL3 = ksskkruigkL3;
	}
	public Long getKsskkruigkL4() {
		return ksskkruigkL4;
	}
	public void setKsskkruigkL4(Long ksskkruigkL4) {
		this.ksskkruigkL4 = ksskkruigkL4;
	}
	public Long getKsskkruigkL5() {
		return ksskkruigkL5;
	}
	public void setKsskkruigkL5(Long ksskkruigkL5) {
		this.ksskkruigkL5 = ksskkruigkL5;
	}
	public Long getKsskkruigkL6() {
		return ksskkruigkL6;
	}
	public void setKsskkruigkL6(Long ksskkruigkL6) {
		this.ksskkruigkL6 = ksskkruigkL6;
	}
	public Long getToSkkgkk() {
		return toSkkgkk;
	}
	public void setToSkkgkk(Long toSkkgkk) {
		this.toSkkgkk = toSkkgkk;
	}
	public Long getToSkkgkz() {
		return toSkkgkz;
	}
	public void setToSkkgkz(Long toSkkgkz) {
		this.toSkkgkz = toSkkgkz;
	}
	public Long getToSkkgkL3() {
		return toSkkgkL3;
	}
	public void setToSkkgkL3(Long toSkkgkL3) {
		this.toSkkgkL3 = toSkkgkL3;
	}
	public Long getToSkkgkL4() {
		return toSkkgkL4;
	}
	public void setToSkkgkL4(Long toSkkgkL4) {
		this.toSkkgkL4 = toSkkgkL4;
	}
	public Long getToSkkgkL5() {
		return toSkkgkL5;
	}
	public void setToSkkgkL5(Long toSkkgkL5) {
		this.toSkkgkL5 = toSkkgkL5;
	}
	public Long getToSkkgkL6() {
		return toSkkgkL6;
	}
	public void setToSkkgkL6(Long toSkkgkL6) {
		this.toSkkgkL6 = toSkkgkL6;
	}
	public String getFurikaegoShisanNum() {
		return furikaegoShisanNum;
	}
	public void setFurikaegoShisanNum(String furikaegoShisanNum) {
		this.furikaegoShisanNum = furikaegoShisanNum;
	}
	public String getCpShisanNum() {
		return cpShisanNum;
	}
	public void setCpShisanNum(String cpShisanNum) {
		this.cpShisanNum = cpShisanNum;
	}
}
