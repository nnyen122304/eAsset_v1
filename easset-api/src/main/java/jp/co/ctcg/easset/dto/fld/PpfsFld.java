/*===================================================================
 * ファイル名 : PpfsFld.java
 * 概要説明   : 固定資産(照会・管理項目修正)
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-14 1.0  リヨン 李     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.fld;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

import jp.co.ctcg.easset.dto.asset.AssetSR;
import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.util.Function;
import lombok.ToString;

@ToString
public class PpfsFld implements Externalizable {
	private static final long serialVersionUID = 1L;

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

	private Long ksskkruigkk; // 期首償却累計額(会社帳簿)
	private Long ksskkruigkz; // 期首償却累計額(税法帳簿)
	private Long ksskkruigkL3; // 期首償却累計額(第3帳簿)
	private Long ksskkruigkL4; // 期首償却累計額(第4帳簿)
	private Long ksskkruigkL5; // 期首償却累計額(第5帳簿)
	private Long ksskkruigkL6; // 期首償却累計額(第6帳簿)
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


	//	情報機器一覧
	private List<AssetSR> assetList;
	//	ﾗｲｾﾝｽ一覧
	private List<LicenseSR> licenseList;

	//	除却債務
	private String saimuhfcdR; // 債務配賦コード
	private String saimuhfnmR; // 債務配賦名称
	private String saimukeijoymdRF; // 債務計上年月日
	private String saimurikoymdRF; // 債務履行年月日

	private Long mitsumorigkWaribikimaekR; // 見積価額(割引前)(会社帳簿)
	private Double waribikiritsukR; // 割引率(会社帳簿)
	private Long keijogkWaribikigokR; // 計上価額(割引後)(会社帳簿)
	private Long kssaimubokakR; // 期首債務帳簿価額(会社帳簿)
	private Long risokucalckisogkkR; // 利息計算基礎額(会社帳簿)
	private Long toSaimubokakR; // 当月末債務帳簿価額(会社帳簿)
	private Long toSaimuzogenbokakR; // 債務増減帳簿価額(会社帳簿)
	private Long toTorisokugkkR; // 当期利息額(当月累計)(会社帳簿)
	private Long ksrisokuruigkkR; // 期首利息累計額(会社帳簿)

	private Long mitsumorigkWaribikimaezR; // 見積価額(割引前)(税法帳簿)
	private Double waribikiritsuzR; // 割引率(税法帳簿)
	private Long keijogkWaribikigozR; // 計上価額(割引後)(税法帳簿)
	private Long kssaimubokazR; // 期首債務帳簿価額(税法帳簿)
	private Long risokucalckisogkzR; // 利息計算基礎額(税法帳簿)
	private Long toSaimubokazR; // 当月末債務帳簿価額(税法帳簿)
	private Long toSaimuzogenbokazR; // 債務増減帳簿価額(税法帳簿)
	private Long toTorisokugkzR; // 当期利息額(当月累計)(税法帳簿)
	private Long ksrisokuruigkzR; // 期首利息累計額(税法帳簿)

	private Long mitsumorigkWaribikimaeL3R; // 見積価額(割引前)(第3帳簿)
	private Double waribikiritsuL3R; // 割引率(第3帳簿)
	private Long keijogkWaribikigoL3R; // 計上価額(割引後)(第3帳簿)
	private Long kssaimubokaL3R; // 期首債務帳簿価額(第3帳簿)
	private Long risokucalckisogkL3R; // 利息計算基礎額(第3帳簿)
	private Long toSaimubokaL3R; // 当月末債務帳簿価額(第3帳簿)
	private Long toSaimuzogenbokaL3R; // 債務増減帳簿価額(第3帳簿)
	private Long toTorisokugkL3R; // 当期利息額(当月累計)(第3帳簿)
	private Long ksrisokuruigkL3R; // 期首利息累計額(第3帳簿)

	private Long mitsumorigkWaribikimaeL4R; // 見積価額(割引前)(第4帳簿)
	private Double waribikiritsuL4R; // 割引率(第4帳簿)
	private Long keijogkWaribikigoL4R; // 計上価額(割引後)(第4帳簿)
	private Long kssaimubokaL4R; // 期首債務帳簿価額(第4帳簿)
	private Long risokucalckisogkL4R; // 利息計算基礎額(第4帳簿)
	private Long toSaimubokaL4R; // 当月末債務帳簿価額(第4帳簿)
	private Long toSaimuzogenbokaL4R; // 債務増減帳簿価額(第4帳簿)
	private Long toTorisokugkL4R; // 当期利息額(当月累計)(第4帳簿)
	private Long ksrisokuruigkL4R; // 期首利息累計額(第4帳簿)

	private Long mitsumorigkWaribikimaeL5R; // 見積価額(割引前)(第5帳簿)
	private Double waribikiritsuL5R; // 割引率(第5帳簿)
	private Long keijogkWaribikigoL5R; // 計上価額(割引後)(第5帳簿)
	private Long kssaimubokaL5R; // 期首債務帳簿価額(第5帳簿)
	private Long risokucalckisogkL5R; // 利息計算基礎額(第5帳簿)
	private Long toSaimubokaL5R; // 当月末債務帳簿価額(第5帳簿)
	private Long toSaimuzogenbokaL5R; // 債務増減帳簿価額(第5帳簿)
	private Long toTorisokugkL5R; // 当期利息額(当月累計)(第5帳簿)
	private Long ksrisokuruigkL5R; // 期首利息累計額(第5帳簿)

	private Long mitsumorigkWaribikimaeL6R; // 見積価額(割引前)(第6帳簿)
	private Double waribikiritsuL6R; // 割引率(第6帳簿)
	private Long keijogkWaribikigoL6R; // 計上価額(割引後)(第6帳簿)
	private Long kssaimubokaL6R; // 期首債務帳簿価額(第6帳簿)
	private Long risokucalckisogkL6R; // 利息計算基礎額(第6帳簿)
	private Long toSaimubokaL6R; // 当月末債務帳簿価額(第6帳簿)
	private Long toSaimuzogenbokaL6R; // 債務増減帳簿価額(第6帳簿)
	private Long toTorisokugkL6R; // 当期利息額(当月累計)(第6帳簿)
	private Long ksrisokuruigkL6R; // 期首利息累計額(第6帳簿)

	/*
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
	*/

	@SuppressWarnings("unchecked")
	public void readExternal(ObjectInput input) throws IOException, ClassNotFoundException {
		//	固定資産
		koyuno = Function.getExternalLong(input.readObject());
		kaishacd = (String)input.readObject();
		kaishanm = (String)input.readObject();
		oya = (String)input.readObject();
		eda = (String)input.readObject();
		shisanknrkbn = (String)input.readObject();
		shisanjotaikbn = (String)input.readObject();
		shisannmcd = (String)input.readObject();
		shisannm = (String)input.readObject();
		shisannmk = (String)input.readObject();
		soshiki1cd = (String)input.readObject();
		soshiki1nm = (String)input.readObject();
		soshiki2cd = (String)input.readObject();
		soshiki2nm = (String)input.readObject();
		soshiki3cd = (String)input.readObject();
		soshiki3nm = (String)input.readObject();
		soshiki4cd = (String)input.readObject();
		soshiki4nm = (String)input.readObject();
		setchicd = (String)input.readObject();
		setchinm = (String)input.readObject();
		skkhihfcd = (String)input.readObject();
		hfnm = (String)input.readObject();
		laryohfcd = (String)input.readObject();
		laryohfnm = (String)input.readObject();
		shuruicd = (String)input.readObject();
		shuruinm = (String)input.readObject();
		kozocd = (String)input.readObject();
		kozonm = (String)input.readObject();
		bunruicd = (String)input.readObject();
		bunruinm = (String)input.readObject();
		cpkeijoymd = (String)input.readObject();
		furikaeymd = (String)input.readObject();
		furikaegoOya = (String)input.readObject();
		furikaegoEda = (String)input.readObject();
		furikaegoShisanNum = (String)input.readObject();
		stkymd = (String)input.readObject();
		kadoymd = (String)input.readObject();
		jbkymd = (String)input.readObject();
		idoymd = (String)input.readObject();
		suryo = Function.getExternalInteger(input.readObject());
		suryoTani = (String)input.readObject();
		menseki = Function.getExternalInteger(input.readObject());
		mensekiTani = (String)input.readObject();
		chukokbn = (String)input.readObject();
		tosaimukeijokbn = (String)input.readObject();
		jksaimukeijokbn = (String)input.readObject();
		stkgkk = Function.getExternalLong(input.readObject());
		askstkgkk = Function.getExternalLong(input.readObject());
		skkhok = (String)input.readObject();
		tainenk = Function.getExternalInteger(input.readObject());
		skkritsuk = Function.getExternalDouble(input.readObject());
		skkmmsuk = Function.getExternalInteger(input.readObject());
		zanzonmmsuk = Function.getExternalInteger(input.readObject());
		ksbokak = Function.getExternalLong(input.readObject());
		skkcalckisogkk = Function.getExternalLong(input.readObject());
		zanzonritsuk = Function.getExternalDouble(input.readObject());
		zanzongkk = Function.getExternalLong(input.readObject());
		ksftskkruigkk = Function.getExternalLong(input.readObject());
		kszkskkruigkk = Function.getExternalLong(input.readObject());
		kstkskkruigkk = Function.getExternalLong(input.readObject());
		zenToftskkgkk = Function.getExternalLong(input.readObject());
		zenTozkskkgkk = Function.getExternalLong(input.readObject());
		zenTotkskkgkk = Function.getExternalLong(input.readObject());
		zenToniniskkgkk = Function.getExternalLong(input.readObject());
		toToftskkgkk = Function.getExternalLong(input.readObject());
		toTozkskkgkk = Function.getExternalLong(input.readObject());
		toTotkskkgkk = Function.getExternalLong(input.readObject());
		toToniniskkgkk = Function.getExternalLong(input.readObject());
		toBokak = Function.getExternalLong(input.readObject());
		zogenbokak = Function.getExternalLong(input.readObject());
		zkskkritsuk = Function.getExternalDouble(input.readObject());
		yukyustymk = (String)input.readObject();
		yukyufkymk = (String)input.readObject();
		shonencalckbnk = (String)input.readObject();
		skkkanryoflgk = (String)input.readObject();
		skkkirikaeyyk = (String)input.readObject();
		zanzonskkstyyk = (String)input.readObject();
		zanzonskkmmsuk = Function.getExternalInteger(input.readObject());
		famitsumorihkymdk = (String)input.readObject();
		mitsumorihkgobokak = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenk = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkmmsuk = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkritsuk = Function.getExternalLong(input.readObject());
		stkgkz = Function.getExternalLong(input.readObject());
		askstkgkz = Function.getExternalLong(input.readObject());
		skkhoz = (String)input.readObject();
		tainenz = Function.getExternalInteger(input.readObject());
		skkritsuz = Function.getExternalDouble(input.readObject());
		skkmmsuz = Function.getExternalInteger(input.readObject());
		zanzonmmsuz = Function.getExternalInteger(input.readObject());
		ksbokaz = Function.getExternalLong(input.readObject());
		skkcalckisogkz = Function.getExternalLong(input.readObject());
		zanzonritsuz = Function.getExternalDouble(input.readObject());
		zanzongkz = Function.getExternalLong(input.readObject());
		ksftskkruigkz = Function.getExternalLong(input.readObject());
		kszkskkruigkz = Function.getExternalLong(input.readObject());
		kstkskkruigkz = Function.getExternalLong(input.readObject());
		zenToftskkgkz = Function.getExternalLong(input.readObject());
		zenTozkskkgkz = Function.getExternalLong(input.readObject());
		zenTotkskkgkz = Function.getExternalLong(input.readObject());
		zenToniniskkgkz = Function.getExternalLong(input.readObject());
		toToftskkgkz = Function.getExternalLong(input.readObject());
		toTozkskkgkz = Function.getExternalLong(input.readObject());
		toTotkskkgkz = Function.getExternalLong(input.readObject());
		toToniniskkgkz = Function.getExternalLong(input.readObject());
		toBokaz = Function.getExternalLong(input.readObject());
		zogenbokaz = Function.getExternalLong(input.readObject());
		zkskkritsuz = Function.getExternalDouble(input.readObject());
		yukyustymz = (String)input.readObject();
		yukyufkymz = (String)input.readObject();
		shonencalckbnz = (String)input.readObject();
		skkkanryoflgz = (String)input.readObject();
		skkkirikaeyyz = (String)input.readObject();
		zanzonskkstyyz = (String)input.readObject();
		zanzonskkmmsuz = Function.getExternalInteger(input.readObject());
		famitsumorihkymdz = (String)input.readObject();
		mitsumorihkgobokaz = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenz = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkmmsuz = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkritsuz = Function.getExternalLong(input.readObject());
		stkgkL3 = Function.getExternalLong(input.readObject());
		askstkgkL3 = Function.getExternalLong(input.readObject());
		skkhoL3 = (String)input.readObject();
		tainenL3 = Function.getExternalInteger(input.readObject());
		skkritsuL3 = Function.getExternalDouble(input.readObject());
		skkmmsuL3 = Function.getExternalInteger(input.readObject());
		zanzonmmsuL3 = Function.getExternalInteger(input.readObject());
		ksbokaL3 = Function.getExternalLong(input.readObject());
		skkcalckisogkL3 = Function.getExternalLong(input.readObject());
		zanzonritsuL3 = Function.getExternalDouble(input.readObject());
		zanzongkL3 = Function.getExternalLong(input.readObject());
		ksftskkruigkL3 = Function.getExternalLong(input.readObject());
		kszkskkruigkL3 = Function.getExternalLong(input.readObject());
		kstkskkruigkL3 = Function.getExternalLong(input.readObject());
		zenToftskkgkL3 = Function.getExternalLong(input.readObject());
		zenTozkskkgkL3 = Function.getExternalLong(input.readObject());
		zenTotkskkgkL3 = Function.getExternalLong(input.readObject());
		zenToniniskkgkL3 = Function.getExternalLong(input.readObject());
		toToftskkgkL3 = Function.getExternalLong(input.readObject());
		toTozkskkgkL3 = Function.getExternalLong(input.readObject());
		toTotkskkgkL3 = Function.getExternalLong(input.readObject());
		toToniniskkgkL3 = Function.getExternalLong(input.readObject());
		toBokaL3 = Function.getExternalLong(input.readObject());
		zogenbokaL3 = Function.getExternalLong(input.readObject());
		zkskkritsuL3 = Function.getExternalDouble(input.readObject());
		yukyustymL3 = (String)input.readObject();
		yukyufkymL3 = (String)input.readObject();
		shonencalckbnL3 = (String)input.readObject();
		skkkanryoflgL3 = (String)input.readObject();
		skkkirikaeyyL3 = (String)input.readObject();
		zanzonskkstyyL3 = (String)input.readObject();
		zanzonskkmmsuL3 = Function.getExternalInteger(input.readObject());
		famitsumorihkymdL3 = (String)input.readObject();
		mitsumorihkgobokaL3 = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenL3 = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkmmsuL3 = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkritsuL3 = Function.getExternalLong(input.readObject());
		stkgkL4 = Function.getExternalLong(input.readObject());
		askstkgkL4 = Function.getExternalLong(input.readObject());
		skkhoL4 = (String)input.readObject();
		tainenL4 = Function.getExternalInteger(input.readObject());
		skkritsuL4 = Function.getExternalDouble(input.readObject());
		skkmmsuL4 = Function.getExternalInteger(input.readObject());
		zanzonmmsuL4 = Function.getExternalInteger(input.readObject());
		ksbokaL4 = Function.getExternalLong(input.readObject());
		skkcalckisogkL4 = Function.getExternalLong(input.readObject());
		zanzonritsuL4 = Function.getExternalDouble(input.readObject());
		zanzongkL4 = Function.getExternalLong(input.readObject());
		ksftskkruigkL4 = Function.getExternalLong(input.readObject());
		kszkskkruigkL4 = Function.getExternalLong(input.readObject());
		kstkskkruigkL4 = Function.getExternalLong(input.readObject());
		zenToftskkgkL4 = Function.getExternalLong(input.readObject());
		zenTozkskkgkL4 = Function.getExternalLong(input.readObject());
		zenTotkskkgkL4 = Function.getExternalLong(input.readObject());
		zenToniniskkgkL4 = Function.getExternalLong(input.readObject());
		toToftskkgkL4 = Function.getExternalLong(input.readObject());
		toTozkskkgkL4 = Function.getExternalLong(input.readObject());
		toTotkskkgkL4 = Function.getExternalLong(input.readObject());
		toToniniskkgkL4 = Function.getExternalLong(input.readObject());
		toBokaL4 = Function.getExternalLong(input.readObject());
		zogenbokaL4 = Function.getExternalLong(input.readObject());
		zkskkritsuL4 = Function.getExternalDouble(input.readObject());
		yukyustymL4 = (String)input.readObject();
		yukyufkymL4 = (String)input.readObject();
		shonencalckbnL4 = (String)input.readObject();
		skkkanryoflgL4 = (String)input.readObject();
		skkkirikaeyyL4 = (String)input.readObject();
		zanzonskkstyyL4 = (String)input.readObject();
		zanzonskkmmsuL4 = Function.getExternalInteger(input.readObject());
		famitsumorihkymdL4 = (String)input.readObject();
		mitsumorihkgobokaL4 = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenL4 = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkmmsuL4 = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkritsuL4 = Function.getExternalLong(input.readObject());
		stkgkL5 = Function.getExternalLong(input.readObject());
		askstkgkL5 = Function.getExternalLong(input.readObject());
		skkhoL5 = (String)input.readObject();
		tainenL5 = Function.getExternalInteger(input.readObject());
		skkritsuL5 = Function.getExternalDouble(input.readObject());
		skkmmsuL5 = Function.getExternalInteger(input.readObject());
		zanzonmmsuL5 = Function.getExternalInteger(input.readObject());
		ksbokaL5 = Function.getExternalLong(input.readObject());
		skkcalckisogkL5 = Function.getExternalLong(input.readObject());
		zanzonritsuL5 = Function.getExternalDouble(input.readObject());
		zanzongkL5 = Function.getExternalLong(input.readObject());
		ksftskkruigkL5 = Function.getExternalLong(input.readObject());
		kszkskkruigkL5 = Function.getExternalLong(input.readObject());
		kstkskkruigkL5 = Function.getExternalLong(input.readObject());
		zenToftskkgkL5 = Function.getExternalLong(input.readObject());
		zenTozkskkgkL5 = Function.getExternalLong(input.readObject());
		zenTotkskkgkL5 = Function.getExternalLong(input.readObject());
		zenToniniskkgkL5 = Function.getExternalLong(input.readObject());
		toToftskkgkL5 = Function.getExternalLong(input.readObject());
		toTozkskkgkL5 = Function.getExternalLong(input.readObject());
		toTotkskkgkL5 = Function.getExternalLong(input.readObject());
		toToniniskkgkL5 = Function.getExternalLong(input.readObject());
		toBokaL5 = Function.getExternalLong(input.readObject());
		zogenbokaL5 = Function.getExternalLong(input.readObject());
		zkskkritsuL5 = Function.getExternalDouble(input.readObject());
		yukyustymL5 = (String)input.readObject();
		yukyufkymL5 = (String)input.readObject();
		shonencalckbnL5 = (String)input.readObject();
		skkkanryoflgL5 = (String)input.readObject();
		skkkirikaeyyL5 = (String)input.readObject();
		zanzonskkstyyL5 = (String)input.readObject();
		zanzonskkmmsuL5 = Function.getExternalInteger(input.readObject());
		famitsumorihkymdL5 = (String)input.readObject();
		mitsumorihkgobokaL5 = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenL5 = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkmmsuL5 = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkritsuL5 = Function.getExternalLong(input.readObject());
		stkgkL6 = Function.getExternalLong(input.readObject());
		askstkgkL6 = Function.getExternalLong(input.readObject());
		skkhoL6 = (String)input.readObject();
		tainenL6 = Function.getExternalInteger(input.readObject());
		skkritsuL6 = Function.getExternalDouble(input.readObject());
		skkmmsuL6 = Function.getExternalInteger(input.readObject());
		zanzonmmsuL6 = Function.getExternalInteger(input.readObject());
		ksbokaL6 = Function.getExternalLong(input.readObject());
		skkcalckisogkL6 = Function.getExternalLong(input.readObject());
		zanzonritsuL6 = Function.getExternalDouble(input.readObject());
		zanzongkL6 = Function.getExternalLong(input.readObject());
		ksftskkruigkL6 = Function.getExternalLong(input.readObject());
		kszkskkruigkL6 = Function.getExternalLong(input.readObject());
		kstkskkruigkL6 = Function.getExternalLong(input.readObject());
		zenToftskkgkL6 = Function.getExternalLong(input.readObject());
		zenTozkskkgkL6 = Function.getExternalLong(input.readObject());
		zenTotkskkgkL6 = Function.getExternalLong(input.readObject());
		zenToniniskkgkL6 = Function.getExternalLong(input.readObject());
		toToftskkgkL6 = Function.getExternalLong(input.readObject());
		toTozkskkgkL6 = Function.getExternalLong(input.readObject());
		toTotkskkgkL6 = Function.getExternalLong(input.readObject());
		toToniniskkgkL6 = Function.getExternalLong(input.readObject());
		toBokaL6 = Function.getExternalLong(input.readObject());
		zogenbokaL6 = Function.getExternalLong(input.readObject());
		zkskkritsuL6 = Function.getExternalDouble(input.readObject());
		yukyustymL6 = (String)input.readObject();
		yukyufkymL6 = (String)input.readObject();
		shonencalckbnL6 = (String)input.readObject();
		skkkanryoflgL6 = (String)input.readObject();
		skkkirikaeyyL6 = (String)input.readObject();
		zanzonskkstyyL6 = (String)input.readObject();
		zanzonskkmmsuL6 = Function.getExternalInteger(input.readObject());
		famitsumorihkymdL6 = (String)input.readObject();
		mitsumorihkgobokaL6 = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenL6 = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkmmsuL6 = Function.getExternalLong(input.readObject());
		mitsumorihkmaeskkritsuL6 = Function.getExternalLong(input.readObject());
		konyucd = (String)input.readObject();
		konyunm = (String)input.readObject();
		kashicd = (String)input.readObject();
		kashinm = (String)input.readObject();
		cpOya = (String)input.readObject();
		cpEda = (String)input.readObject();
		cpShisanNum = (String)input.readObject();
		knrbunruicd = (String)input.readObject();
		biko1 = (String)input.readObject();
		biko2 = (String)input.readObject();
		stkringino = (String)input.readObject();
		stktekiyo = (String)input.readObject();
		askcd = (String)input.readObject();
		asknm = (String)input.readObject();
		askkbn = (String)input.readObject();
		askgk = Function.getExternalLong(input.readObject());
		ksaskzan = Function.getExternalLong(input.readObject());
		ksaskninyogk = Function.getExternalLong(input.readObject());
		askninyogk = Function.getExternalLong(input.readObject());
		askkisogk = Function.getExternalLong(input.readObject());
		askzanzongk = Function.getExternalLong(input.readObject());
		kaiteiaskgk = Function.getExternalLong(input.readObject());
		skkchokagk = Function.getExternalLong(input.readObject());
		skkfusokugk = Function.getExternalLong(input.readObject());
		tkcd = (String)input.readObject();
		tknm = (String)input.readObject();
		tkkbn = (String)input.readObject();
		tkritsuBunshi = Function.getExternalDouble(input.readObject());
		tkritsuBunbo = Function.getExternalDouble(input.readObject());
		beppyotaishokbn = (String)input.readObject();
		skkshisankbn = (String)input.readObject();
		aitekanjocd = (String)input.readObject();
		aitekanjonm = (String)input.readObject();
		aitehojokamokucd = (String)input.readObject();
		aitehojokamokunm = (String)input.readObject();
		gappeiukekbn = (String)input.readObject();
		genshistkymd = (String)input.readObject();
		groupcd = (String)input.readObject();
		groupnm = (String)input.readObject();
		shinariocd = (String)input.readObject();
		shinarionm = (String)input.readObject();
		shuyoshisankbn = (String)input.readObject();
		niniLd_1cd = (String)input.readObject();
		niniLd_1nm = (String)input.readObject();
		niniLd_2cd = (String)input.readObject();
		niniLd_2nm = (String)input.readObject();
		niniLd_3cd = (String)input.readObject();
		niniLd_3nm = (String)input.readObject();
		niniLd_4cd = (String)input.readObject();
		niniLd_4nm = (String)input.readObject();
		niniLd_5cd = (String)input.readObject();
		niniLd_5nm = (String)input.readObject();
		niniLd_6cd = (String)input.readObject();
		niniLd_6nm = (String)input.readObject();
		niniLd_7cd = (String)input.readObject();
		niniLd_7nm = (String)input.readObject();
		niniLd_8cd = (String)input.readObject();
		niniLd_8nm = (String)input.readObject();
		niniLd_9cd = (String)input.readObject();
		niniLd_9nm = (String)input.readObject();
		niniLd_10cd = (String)input.readObject();
		niniLd_10nm = (String)input.readObject();
		niniLd_11cd = (String)input.readObject();
		niniLd_11nm = (String)input.readObject();
		niniLd_12cd = (String)input.readObject();
		niniLd_12nm = (String)input.readObject();
		niniLd_13cd = (String)input.readObject();
		niniLd_13nm = (String)input.readObject();
		niniLd_14cd = (String)input.readObject();
		niniLd_14nm = (String)input.readObject();
		niniLd_15cd = (String)input.readObject();
		niniLd_15nm = (String)input.readObject();
		niniLd_16cd = (String)input.readObject();
		niniLd_16nm = (String)input.readObject();
		niniLd_17cd = (String)input.readObject();
		niniLd_17nm = (String)input.readObject();
		niniLd_18cd = (String)input.readObject();
		niniLd_18nm = (String)input.readObject();
		niniLd_19cd = (String)input.readObject();
		niniLd_19nm = (String)input.readObject();
		niniLd_20cd = (String)input.readObject();
		niniLd_20nm = (String)input.readObject();
		toshinkkyy = Function.getExternalLong(input.readObject());
		toshinkkchicd = (String)input.readObject();
		toshinkkchinm = (String)input.readObject();
		toshinkkshurui = (String)input.readObject();
		toshinkkstkgk = Function.getExternalLong(input.readObject());
		toshinkktainen = Function.getExternalInteger(input.readObject());
		toshinkkZkskkritsu = Function.getExternalDouble(input.readObject());
		tothzeicd = (String)input.readObject();
		tothzeinm = (String)input.readObject();
		totkreiritsuBunshi = Function.getExternalDouble(input.readObject());
		totkreiritsuBunbo = Function.getExternalDouble(input.readObject());
		zenshinkkyy = (String)input.readObject();
		zenshinkkchicd = (String)input.readObject();
		zenshinkkchinm = (String)input.readObject();
		zenshinkkshurui = (String)input.readObject();
		zenshinkkstkgk = Function.getExternalLong(input.readObject());
		zenshinkktainen = Function.getExternalDouble(input.readObject());
		zenshinkkZkskkritsu = Function.getExternalDouble(input.readObject());
		zenthzeicd = (String)input.readObject();
		zenthzeinm = (String)input.readObject();
		zentkreiritsuBunshi = Function.getExternalDouble(input.readObject());
		zentkreiritsuBunbo = Function.getExternalDouble(input.readObject());
		zenrironboka = Function.getExternalLong(input.readObject());
		zenhyokagk = Function.getExternalLong(input.readObject());
		ykshinkkyy = Function.getExternalLong(input.readObject());
		ykshinkkchicd = (String)input.readObject();
		ykshinkkchinm = (String)input.readObject();
		ykshinkkshurui = (String)input.readObject();
		ykshinkkstkgk = Function.getExternalLong(input.readObject());
		ykshinkktainen = Function.getExternalDouble(input.readObject());
		ykshinkkZkskkritsu = Function.getExternalDouble(input.readObject());
		ykthzeicd = (String)input.readObject();
		ykthzeinm = (String)input.readObject();
		yktkreiritsuBunshi = Function.getExternalDouble(input.readObject());
		yktkreiritsuBunbo = Function.getExternalDouble(input.readObject());
		lastshinkkyy = (String)input.readObject();
		togensonkbnk = (String)input.readObject();
		ksgensonruigkk = Function.getExternalLong(input.readObject());
		togensongkk = Function.getExternalLong(input.readObject());
		skkcalczanzongkk = Function.getExternalLong(input.readObject());
		gensonbokak = Function.getExternalLong(input.readObject());
		gensonmaetainenk = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuk = Function.getExternalInteger(input.readObject());
		gensonymdk = (String)input.readObject();
		genshistkgkk = Function.getExternalLong(input.readObject());
		kaiteistkgkk = Function.getExternalLong(input.readObject());
		kaiteitainenk = Function.getExternalInteger(input.readObject());
		kaiteiymdk = (String)input.readObject();
		yusenzogenkbnk = (String)input.readObject();
		togensonkbnz = (String)input.readObject();
		ksgensonruigkz = Function.getExternalLong(input.readObject());
		togensongkz = Function.getExternalLong(input.readObject());
		skkcalczanzongkz = Function.getExternalLong(input.readObject());
		gensonbokaz = Function.getExternalLong(input.readObject());
		gensonmaetainenz = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuz = Function.getExternalInteger(input.readObject());
		gensonymdz = (String)input.readObject();
		genshistkgkz = Function.getExternalLong(input.readObject());
		kaiteistkgkz = Function.getExternalLong(input.readObject());
		kaiteitainenz = Function.getExternalInteger(input.readObject());
		kaiteiymdz = (String)input.readObject();
		yusenzogenkbnz = (String)input.readObject();
		togensonkbnL3 = (String)input.readObject();
		ksgensonruigkL3 = Function.getExternalLong(input.readObject());
		togensongkL3 = Function.getExternalLong(input.readObject());
		skkcalczanzongkL3 = Function.getExternalLong(input.readObject());
		gensonbokaL3 = Function.getExternalLong(input.readObject());
		gensonmaetainenL3 = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuL3 = Function.getExternalInteger(input.readObject());
		gensonymdL3 = (String)input.readObject();
		genshistkgkL3 = Function.getExternalLong(input.readObject());
		kaiteistkgkL3 = Function.getExternalLong(input.readObject());
		kaiteitainenL3 = Function.getExternalInteger(input.readObject());
		kaiteiymdL3 = (String)input.readObject();
		yusenzogenkbnL3 = (String)input.readObject();
		togensonkbnL4 = (String)input.readObject();
		ksgensonruigkL4 = Function.getExternalLong(input.readObject());
		togensongkL4 = Function.getExternalLong(input.readObject());
		skkcalczanzongkL4 = Function.getExternalLong(input.readObject());
		gensonbokaL4 = Function.getExternalLong(input.readObject());
		gensonmaetainenL4 = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuL4 = Function.getExternalInteger(input.readObject());
		gensonymdL4 = (String)input.readObject();
		genshistkgkL4 = Function.getExternalLong(input.readObject());
		kaiteistkgkL4 = Function.getExternalLong(input.readObject());
		kaiteitainenL4 = Function.getExternalInteger(input.readObject());
		kaiteiymdL4 = (String)input.readObject();
		yusenzogenkbnL4 = (String)input.readObject();
		togensonkbnL5 = (String)input.readObject();
		ksgensonruigkL5 = Function.getExternalLong(input.readObject());
		togensongkL5 = Function.getExternalLong(input.readObject());
		skkcalczanzongkL5 = Function.getExternalLong(input.readObject());
		gensonbokaL5 = Function.getExternalLong(input.readObject());
		gensonmaetainenL5 = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuL5 = Function.getExternalInteger(input.readObject());
		gensonymdL5 = (String)input.readObject();
		genshistkgkL5 = Function.getExternalLong(input.readObject());
		kaiteistkgkL5 = Function.getExternalLong(input.readObject());
		kaiteitainenL5 = Function.getExternalInteger(input.readObject());
		kaiteiymdL5 = (String)input.readObject();
		yusenzogenkbnL5 = (String)input.readObject();
		togensonkbnL6 = (String)input.readObject();
		ksgensonruigkL6 = Function.getExternalLong(input.readObject());
		togensongkL6 = Function.getExternalLong(input.readObject());
		skkcalczanzongkL6 = Function.getExternalLong(input.readObject());
		gensonbokaL6 = Function.getExternalLong(input.readObject());
		gensonmaetainenL6 = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuL6 = Function.getExternalInteger(input.readObject());
		gensonymdL6 = (String)input.readObject();
		genshistkgkL6 = Function.getExternalLong(input.readObject());
		kaiteistkgkL6 = Function.getExternalLong(input.readObject());
		kaiteitainenL6 = Function.getExternalInteger(input.readObject());
		kaiteiymdL6 = (String)input.readObject();
		yusenzogenkbnL6 = (String)input.readObject();
		updkaisu = Function.getExternalLong(input.readObject());
		updkaishacd = (String)input.readObject();
		updid = (String)input.readObject();
		updymd = (String)input.readObject();
		updtime = (String)input.readObject();
		routeType = (String)input.readObject();
		routeTypeName = (String)input.readObject();
		purCompanyCode = (String)input.readObject();
		purCompanyName = (String)input.readObject();
		itemGroupCode = (String)input.readObject();
		itemGroupName = (String)input.readObject();
		manageSectionCode = (String)input.readObject();
		manageSectionName = (String)input.readObject();
		astPrjCode = (String)input.readObject();
		astPrjName = (String)input.readObject();
		depPrjCode = (String)input.readObject();
		depPrjName = (String)input.readObject();
		costType = (String)input.readObject();
		costTypeName = (String)input.readObject();
		applicationId = (String)input.readObject();
		slipNum = (String)input.readObject();
		interestFlag = (String)input.readObject();
		interestFlagName = (String)input.readObject();
		apStaff = (String)input.readObject();
		inventoryFlag = (String)input.readObject();
		inventoryFlagName = (String)input.readObject();
		disposePlanCode = (String)input.readObject();
		disposePlanName = (String)input.readObject();
		taxAdjustCode = (String)input.readObject();
		taxAdjustName = (String)input.readObject();
		contractBranchNum = (String)input.readObject();
		chargeType = (String)input.readObject();
		chargeTypeName = (String)input.readObject();
		disposeReasonCode = (String)input.readObject();
		disposeReasonName = (String)input.readObject();
		disposeSectionCode = (String)input.readObject();
		disposeSectionName = (String)input.readObject();
		calcType = (String)input.readObject();
		calcTypeName = (String)input.readObject();
		upperSectionCode = (String)input.readObject();
		upperSectionName = (String)input.readObject();
		addUpPlanDate = (String)input.readObject();
		addUpPlanDateName = (String)input.readObject();
		paTemplateCode = (String)input.readObject();
		paTemplateName = (String)input.readObject();
		upperAccountCode = (String)input.readObject();
		upperAccountName = (String)input.readObject();
		jgzzReconRef = (String)input.readObject();
		expenseType = (String)input.readObject();
		expenseName = (String)input.readObject();

		ksskkruigkk = Function.getExternalLong(input.readObject());
		ksskkruigkz = Function.getExternalLong(input.readObject());
		ksskkruigkL3 = Function.getExternalLong(input.readObject());
		ksskkruigkL4= Function.getExternalLong(input.readObject());
		ksskkruigkL5 = Function.getExternalLong(input.readObject());
		ksskkruigkL6 = Function.getExternalLong(input.readObject());
		toToskkgkk = Function.getExternalLong(input.readObject());
		toToskkgkz = Function.getExternalLong(input.readObject());
		toToskkgkL3 = Function.getExternalLong(input.readObject());
		toToskkgkL4= Function.getExternalLong(input.readObject());
		toToskkgkL5 = Function.getExternalLong(input.readObject());
		toToskkgkL6 = Function.getExternalLong(input.readObject());
		toSkkgkk = Function.getExternalLong(input.readObject());
		toSkkgkz = Function.getExternalLong(input.readObject());
		toSkkgkL3 = Function.getExternalLong(input.readObject());
		toSkkgkL4= Function.getExternalLong(input.readObject());
		toSkkgkL5 = Function.getExternalLong(input.readObject());
		toSkkgkL6 = Function.getExternalLong(input.readObject());

		shisanknrkbnName = (String)input.readObject();
		shisanjotaikbnName = (String)input.readObject();
		togensonkbnkName = (String)input.readObject();

		shisanNum = (String)input.readObject();

		cpkeijoymdF = (String)input.readObject();
		furikaeymdF = (String)input.readObject();
		stkymdF = (String)input.readObject();
		kadoymdF = (String)input.readObject();
		jbkymdF = (String)input.readObject();
		idoymdF = (String)input.readObject();
		genshistkymdF = (String)input.readObject();
		gensonymdkF = (String)input.readObject();
		kaiteiymdkF = (String)input.readObject();
		gensonymdzF = (String)input.readObject();
		kaiteiymdzF = (String)input.readObject();
		gensonymdL3F = (String)input.readObject();
		kaiteiymdL3F = (String)input.readObject();
		gensonymdL4F = (String)input.readObject();
		kaiteiymdL4F = (String)input.readObject();
		gensonymdL5F = (String)input.readObject();
		kaiteiymdL5F = (String)input.readObject();
		gensonymdL6F = (String)input.readObject();
		kaiteiymdL6F = (String)input.readObject();
		updymdF = (String)input.readObject();
		addUpPlanDateF = (String)input.readObject();

		yukyustymkF = (String)input.readObject();
		yukyustymzF = (String)input.readObject();
		yukyustymL3F = (String)input.readObject();
		yukyustymL4F = (String)input.readObject();
		yukyustymL5F = (String)input.readObject();
		yukyustymL6F = (String)input.readObject();

		yukyufkymkF = (String)input.readObject();
		yukyufkymzF = (String)input.readObject();
		yukyufkymL3F = (String)input.readObject();
		yukyufkymL4F = (String)input.readObject();
		yukyufkymL5F = (String)input.readObject();
		yukyufkymL6F = (String)input.readObject();

		chukokbnName = (String)input.readObject();
		skkshisankbnName = (String)input.readObject();
		askkbnName = (String)input.readObject();
		tkkbnName = (String)input.readObject();
		shuyoshisankbnName = (String)input.readObject();
		gappeiukekbnName = (String)input.readObject();
		jksaimukeijokbnName = (String)input.readObject();

		skkhokName = (String)input.readObject();
		skkhozName = (String)input.readObject();
		skkhoL3Name = (String)input.readObject();
		skkhoL4Name = (String)input.readObject();
		skkhoL5Name = (String)input.readObject();
		skkhoL6Name = (String)input.readObject();

		shonencalckbnkName = (String)input.readObject();
		shonencalckbnzName = (String)input.readObject();
		shonencalckbnL3Name = (String)input.readObject();
		shonencalckbnL4Name = (String)input.readObject();
		shonencalckbnL5Name = (String)input.readObject();
		shonencalckbnL6Name = (String)input.readObject();

		skkkanryoflgkName = (String)input.readObject();
		skkkanryoflgzName = (String)input.readObject();
		skkkanryoflgL3Name = (String)input.readObject();
		skkkanryoflgL4Name = (String)input.readObject();
		skkkanryoflgL5Name = (String)input.readObject();
		skkkanryoflgL6Name = (String)input.readObject();

		assetList = (List<AssetSR>)input.readObject();
		licenseList = (List<LicenseSR>)input.readObject();

		//	債務除却
		saimuhfcdR = (String)input.readObject();
		saimuhfnmR = (String)input.readObject();
		saimukeijoymdRF = (String)input.readObject();
		saimurikoymdRF = (String)input.readObject();

		mitsumorigkWaribikimaekR = Function.getExternalLong(input.readObject());
		waribikiritsukR = Function.getExternalDouble(input.readObject());
		keijogkWaribikigokR = Function.getExternalLong(input.readObject());
		kssaimubokakR = Function.getExternalLong(input.readObject());
		risokucalckisogkkR = Function.getExternalLong(input.readObject());
		toSaimubokakR = Function.getExternalLong(input.readObject());
		toSaimuzogenbokakR = Function.getExternalLong(input.readObject());
		toTorisokugkkR = Function.getExternalLong(input.readObject());
		ksrisokuruigkkR = Function.getExternalLong(input.readObject());

		mitsumorigkWaribikimaezR = Function.getExternalLong(input.readObject());
		waribikiritsuzR = Function.getExternalDouble(input.readObject());
		keijogkWaribikigozR = Function.getExternalLong(input.readObject());
		kssaimubokazR = Function.getExternalLong(input.readObject());
		risokucalckisogkzR = Function.getExternalLong(input.readObject());
		toSaimubokazR = Function.getExternalLong(input.readObject());
		toSaimuzogenbokazR = Function.getExternalLong(input.readObject());
		toTorisokugkzR = Function.getExternalLong(input.readObject());
		ksrisokuruigkzR = Function.getExternalLong(input.readObject());

		mitsumorigkWaribikimaeL3R = Function.getExternalLong(input.readObject());
		waribikiritsuL3R = Function.getExternalDouble(input.readObject());
		keijogkWaribikigoL3R = Function.getExternalLong(input.readObject());
		kssaimubokaL3R = Function.getExternalLong(input.readObject());
		risokucalckisogkL3R = Function.getExternalLong(input.readObject());
		toSaimubokaL3R = Function.getExternalLong(input.readObject());
		toSaimuzogenbokaL3R = Function.getExternalLong(input.readObject());
		toTorisokugkL3R = Function.getExternalLong(input.readObject());
		ksrisokuruigkL3R = Function.getExternalLong(input.readObject());

		mitsumorigkWaribikimaeL4R = Function.getExternalLong(input.readObject());
		waribikiritsuL4R = Function.getExternalDouble(input.readObject());
		keijogkWaribikigoL4R = Function.getExternalLong(input.readObject());
		kssaimubokaL4R = Function.getExternalLong(input.readObject());
		risokucalckisogkL4R = Function.getExternalLong(input.readObject());
		toSaimubokaL4R = Function.getExternalLong(input.readObject());
		toSaimuzogenbokaL4R = Function.getExternalLong(input.readObject());
		toTorisokugkL4R = Function.getExternalLong(input.readObject());
		ksrisokuruigkL4R = Function.getExternalLong(input.readObject());

		mitsumorigkWaribikimaeL5R = Function.getExternalLong(input.readObject());
		waribikiritsuL5R = Function.getExternalDouble(input.readObject());
		keijogkWaribikigoL5R = Function.getExternalLong(input.readObject());
		kssaimubokaL5R = Function.getExternalLong(input.readObject());
		risokucalckisogkL5R = Function.getExternalLong(input.readObject());
		toSaimubokaL5R = Function.getExternalLong(input.readObject());
		toSaimuzogenbokaL5R = Function.getExternalLong(input.readObject());
		toTorisokugkL5R = Function.getExternalLong(input.readObject());
		ksrisokuruigkL5R = Function.getExternalLong(input.readObject());

		mitsumorigkWaribikimaeL6R = Function.getExternalLong(input.readObject());
		waribikiritsuL6R = Function.getExternalDouble(input.readObject());
		keijogkWaribikigoL6R = Function.getExternalLong(input.readObject());
		kssaimubokaL6R = Function.getExternalLong(input.readObject());
		risokucalckisogkL6R = Function.getExternalLong(input.readObject());
		toSaimubokaL6R = Function.getExternalLong(input.readObject());
		toSaimuzogenbokaL6R = Function.getExternalLong(input.readObject());
		toTorisokugkL6R = Function.getExternalLong(input.readObject());
		ksrisokuruigkL6R = Function.getExternalLong(input.readObject());

		/* 除去債務不要項目
		koyunoR = Function.getExternalLong(input.readObject());
		kaishacdR = (String)input.readObject();
		kaishanmR = (String)input.readObject();
		oyaR = (String)input.readObject();
		edaR = (String)input.readObject();
		shisanknrkbnR = (String)input.readObject();
		shisanjotaikbnR = (String)input.readObject();
		shisannmcdR = (String)input.readObject();
		shisannmR = (String)input.readObject();
		shisannmkR = (String)input.readObject();
		soshiki1cdR = (String)input.readObject();
		soshiki1nmR = (String)input.readObject();
		soshiki2cdR = (String)input.readObject();
		soshiki2nmR = (String)input.readObject();
		soshiki3cdR = (String)input.readObject();
		soshiki3nmR = (String)input.readObject();
		soshiki4cdR = (String)input.readObject();
		soshiki4nmR = (String)input.readObject();
		setchicdR = (String)input.readObject();
		setchinmR = (String)input.readObject();
		skkhihfcdR = (String)input.readObject();
		hfnmR = (String)input.readObject();
		shuruicdR = (String)input.readObject();
		shuruinmR = (String)input.readObject();
		kozocdR = (String)input.readObject();
		kozonmR = (String)input.readObject();
		bunruicdR = (String)input.readObject();
		bunruinmR = (String)input.readObject();
		saimukeijoymdR = (String)input.readObject();
		saimurikoymdR = (String)input.readObject();
		kadoymdR = (String)input.readObject();
		jbkymdR = (String)input.readObject();
		idoymdR = (String)input.readObject();
		suryoR = Function.getExternalInteger(input.readObject());
		suryoTaniR = (String)input.readObject();
		mensekiR = Function.getExternalDouble(input.readObject());
		mensekiTaniR = (String)input.readObject();
		tosaimukeijokbnR = (String)input.readObject();
		jksaimukeijokbnR = (String)input.readObject();
		mitsumorihkymdkR = (String)input.readObject();
		famitsumorihkymdkR = (String)input.readObject();
		mitsumorihkgobokakR = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenkR = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkmmsukR = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkritsukR = Function.getExternalDouble(input.readObject());
		tomitsumorihkchoseigkkR = Function.getExternalLong(input.readObject());
		stkgkkR = Function.getExternalLong(input.readObject());
		skkhokR = (String)input.readObject();
		tainenkR = Function.getExternalInteger(input.readObject());
		skkritsukR = Function.getExternalDouble(input.readObject());
		skkmmsukR = Function.getExternalInteger(input.readObject());
		zanzonmmsukR = Function.getExternalInteger(input.readObject());
		ksbokakR = Function.getExternalLong(input.readObject());
		skkcalckisogkkR = Function.getExternalLong(input.readObject());
		zanzonritsukR = Function.getExternalDouble(input.readObject());
		zanzongkkR = Function.getExternalLong(input.readObject());
		ksftskkruigkkR = Function.getExternalLong(input.readObject());
		kszkskkruigkkR = Function.getExternalLong(input.readObject());
		toToftskkgkkR = Function.getExternalLong(input.readObject());
		toTozkskkgkkR = Function.getExternalLong(input.readObject());
		toToniniskkgkkR = Function.getExternalLong(input.readObject());
		toBokakR = Function.getExternalLong(input.readObject());
		zogenbokakR = Function.getExternalLong(input.readObject());
		zkskkritsukR = Function.getExternalDouble(input.readObject());
		yukyustymkR = (String)input.readObject();
		yukyufkymkR = (String)input.readObject();
		shonencalckbnkR = (String)input.readObject();
		skkkanryoflgkR = (String)input.readObject();
		skkkirikaeyykR = (String)input.readObject();
		zanzonskkstyykR = (String)input.readObject();
		mitsumorihkymdzR = (String)input.readObject();
		famitsumorihkymdzR = (String)input.readObject();
		mitsumorihkgobokazR = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenzR = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkmmsuzR = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkritsuzR = Function.getExternalDouble(input.readObject());
		tomitsumorihkchoseigkzR = Function.getExternalLong(input.readObject());
		stkgkzR = Function.getExternalLong(input.readObject());
		skkhozR = (String)input.readObject();
		tainenzR = Function.getExternalInteger(input.readObject());
		skkritsuzR = Function.getExternalDouble(input.readObject());
		skkmmsuzR = Function.getExternalInteger(input.readObject());
		zanzonmmsuzR = Function.getExternalInteger(input.readObject());
		ksbokazR = Function.getExternalLong(input.readObject());
		skkcalckisogkzR = Function.getExternalLong(input.readObject());
		zanzonritsuzR = Function.getExternalDouble(input.readObject());
		zanzongkzR = Function.getExternalLong(input.readObject());
		ksftskkruigkzR = Function.getExternalLong(input.readObject());
		kszkskkruigkzR = Function.getExternalLong(input.readObject());
		toToftskkgkzR = Function.getExternalLong(input.readObject());
		toTozkskkgkzR = Function.getExternalLong(input.readObject());
		toToniniskkgkzR = Function.getExternalLong(input.readObject());
		toBokazR = Function.getExternalLong(input.readObject());
		zogenbokazR = Function.getExternalLong(input.readObject());
		zkskkritsuzR = Function.getExternalDouble(input.readObject());
		yukyustymzR = (String)input.readObject();
		yukyufkymzR = (String)input.readObject();
		shonencalckbnzR = (String)input.readObject();
		skkkanryoflgzR = (String)input.readObject();
		skkkirikaeyyzR = (String)input.readObject();
		zanzonskkstyyzR = (String)input.readObject();
		mitsumorihkymdL3R = (String)input.readObject();
		famitsumorihkymdL3R = (String)input.readObject();
		mitsumorihkgobokaL3R = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenL3R = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkmmsuL3R = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkritsuL3R = Function.getExternalDouble(input.readObject());
		tomitsumorihkchoseigkL3R = Function.getExternalLong(input.readObject());
		stkgkL3R = Function.getExternalLong(input.readObject());
		skkhoL3R = (String)input.readObject();
		tainenL3R = Function.getExternalInteger(input.readObject());
		skkritsuL3R = Function.getExternalDouble(input.readObject());
		skkmmsuL3R = Function.getExternalInteger(input.readObject());
		zanzonmmsuL3R = Function.getExternalInteger(input.readObject());
		ksbokaL3R = Function.getExternalLong(input.readObject());
		skkcalckisogkL3R = Function.getExternalLong(input.readObject());
		zanzonritsuL3R = Function.getExternalDouble(input.readObject());
		zanzongkL3R = Function.getExternalLong(input.readObject());
		ksftskkruigkL3R = Function.getExternalLong(input.readObject());
		kszkskkruigkL3R = Function.getExternalLong(input.readObject());
		toToftskkgkL3R = Function.getExternalLong(input.readObject());
		toTozkskkgkL3R = Function.getExternalLong(input.readObject());
		toToniniskkgkL3R = Function.getExternalLong(input.readObject());
		toBokaL3R = Function.getExternalLong(input.readObject());
		zogenbokaL3R = Function.getExternalLong(input.readObject());
		zkskkritsuL3R = Function.getExternalDouble(input.readObject());
		yukyustymL3R = (String)input.readObject();
		yukyufkymL3R = (String)input.readObject();
		shonencalckbnL3R = (String)input.readObject();
		skkkanryoflgL3R = (String)input.readObject();
		skkkirikaeyyL3R = (String)input.readObject();
		zanzonskkstyyL3R = (String)input.readObject();
		mitsumorihkymdL4R = (String)input.readObject();
		famitsumorihkymdL4R = (String)input.readObject();
		mitsumorihkgobokaL4R = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenL4R = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkmmsuL4R = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkritsuL4R = Function.getExternalDouble(input.readObject());
		tomitsumorihkchoseigkL4R = Function.getExternalLong(input.readObject());
		stkgkL4R = Function.getExternalLong(input.readObject());
		skkhoL4R = (String)input.readObject();
		tainenL4R = Function.getExternalInteger(input.readObject());
		skkritsuL4R = Function.getExternalDouble(input.readObject());
		skkmmsuL4R = Function.getExternalInteger(input.readObject());
		zanzonmmsuL4R = Function.getExternalInteger(input.readObject());
		ksbokaL4R = Function.getExternalLong(input.readObject());
		skkcalckisogkL4R = Function.getExternalLong(input.readObject());
		zanzonritsuL4R = Function.getExternalDouble(input.readObject());
		zanzongkL4R = Function.getExternalLong(input.readObject());
		ksftskkruigkL4R = Function.getExternalLong(input.readObject());
		kszkskkruigkL4R = Function.getExternalLong(input.readObject());
		toToftskkgkL4R = Function.getExternalLong(input.readObject());
		toTozkskkgkL4R = Function.getExternalLong(input.readObject());
		toToniniskkgkL4R = Function.getExternalLong(input.readObject());
		toBokaL4R = Function.getExternalLong(input.readObject());
		zogenbokaL4R = Function.getExternalLong(input.readObject());
		zkskkritsuL4R = Function.getExternalDouble(input.readObject());
		yukyustymL4R = (String)input.readObject();
		yukyufkymL4R = (String)input.readObject();
		shonencalckbnL4R = (String)input.readObject();
		skkkanryoflgL4R = (String)input.readObject();
		skkkirikaeyyL4R = (String)input.readObject();
		zanzonskkstyyL4R = (String)input.readObject();
		mitsumorihkymdL5R = (String)input.readObject();
		famitsumorihkymdL5R = (String)input.readObject();
		mitsumorihkgobokaL5R = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenL5R = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkmmsuL5R = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkritsuL5R = Function.getExternalDouble(input.readObject());
		tomitsumorihkchoseigkL5R = Function.getExternalLong(input.readObject());
		stkgkL5R = Function.getExternalLong(input.readObject());
		skkhoL5R = (String)input.readObject();
		tainenL5R = Function.getExternalInteger(input.readObject());
		skkritsuL5R = Function.getExternalDouble(input.readObject());
		skkmmsuL5R = Function.getExternalInteger(input.readObject());
		zanzonmmsuL5R = Function.getExternalInteger(input.readObject());
		ksbokaL5R = Function.getExternalLong(input.readObject());
		skkcalckisogkL5R = Function.getExternalLong(input.readObject());
		zanzonritsuL5R = Function.getExternalDouble(input.readObject());
		zanzongkL5R = Function.getExternalLong(input.readObject());
		ksftskkruigkL5R = Function.getExternalLong(input.readObject());
		kszkskkruigkL5R = Function.getExternalLong(input.readObject());
		toToftskkgkL5R = Function.getExternalLong(input.readObject());
		toTozkskkgkL5R = Function.getExternalLong(input.readObject());
		toToniniskkgkL5R = Function.getExternalLong(input.readObject());
		toBokaL5R = Function.getExternalLong(input.readObject());
		zogenbokaL5R = Function.getExternalLong(input.readObject());
		zkskkritsuL5R = Function.getExternalDouble(input.readObject());
		yukyustymL5R = (String)input.readObject();
		yukyufkymL5R = (String)input.readObject();
		shonencalckbnL5R = (String)input.readObject();
		skkkanryoflgL5R = (String)input.readObject();
		skkkirikaeyyL5R = (String)input.readObject();
		zanzonskkstyyL5R = (String)input.readObject();
		mitsumorihkymdL6R = (String)input.readObject();
		famitsumorihkymdL6R = (String)input.readObject();
		mitsumorihkgobokaL6R = Function.getExternalLong(input.readObject());
		mitsumorihkmaetainenL6R = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkmmsuL6R = Function.getExternalInteger(input.readObject());
		mitsumorihkmaeskkritsuL6R = Function.getExternalDouble(input.readObject());
		tomitsumorihkchoseigkL6R = Function.getExternalLong(input.readObject());
		stkgkL6R = Function.getExternalLong(input.readObject());
		skkhoL6R = (String)input.readObject();
		tainenL6R = Function.getExternalInteger(input.readObject());
		skkritsuL6R = Function.getExternalDouble(input.readObject());
		skkmmsuL6R = Function.getExternalInteger(input.readObject());
		zanzonmmsuL6R = Function.getExternalInteger(input.readObject());
		ksbokaL6R = Function.getExternalLong(input.readObject());
		skkcalckisogkL6R = Function.getExternalLong(input.readObject());
		zanzonritsuL6R = Function.getExternalDouble(input.readObject());
		zanzongkL6R = Function.getExternalLong(input.readObject());
		ksftskkruigkL6R = Function.getExternalLong(input.readObject());
		kszkskkruigkL6R = Function.getExternalLong(input.readObject());
		toToftskkgkL6R = Function.getExternalLong(input.readObject());
		toTozkskkgkL6R = Function.getExternalLong(input.readObject());
		toToniniskkgkL6R = Function.getExternalLong(input.readObject());
		toBokaL6R = Function.getExternalLong(input.readObject());
		zogenbokaL6R = Function.getExternalLong(input.readObject());
		zkskkritsuL6R = Function.getExternalDouble(input.readObject());
		yukyustymL6R = (String)input.readObject();
		yukyufkymL6R = (String)input.readObject();
		shonencalckbnL6R = (String)input.readObject();
		skkkanryoflgL6R = (String)input.readObject();
		skkkirikaeyyL6R = (String)input.readObject();
		zanzonskkstyyL6R = (String)input.readObject();
		konyucdR = (String)input.readObject();
		konyunmR = (String)input.readObject();
		kashicdR = (String)input.readObject();
		kashinmR = (String)input.readObject();
		knrbunruicdR = (String)input.readObject();
		biko1R = (String)input.readObject();
		biko2R = (String)input.readObject();
		stkringinoR = (String)input.readObject();
		stktekiyoR = (String)input.readObject();
		groupcdR = (String)input.readObject();
		groupnmR = (String)input.readObject();
		shinariocdR = (String)input.readObject();
		shinarionmR = (String)input.readObject();
		shuyoshisankbnR = (String)input.readObject();
		niniLd_1cdR = (String)input.readObject();
		niniLd_1nmR = (String)input.readObject();
		niniLd_2cdR = (String)input.readObject();
		niniLd_2nmR = (String)input.readObject();
		niniLd_3cdR = (String)input.readObject();
		niniLd_3nmR = (String)input.readObject();
		niniLd_4cdR = (String)input.readObject();
		niniLd_4nmR = (String)input.readObject();
		niniLd_5cdR = (String)input.readObject();
		niniLd_5nmR = (String)input.readObject();
		niniLd_6cdR = (String)input.readObject();
		niniLd_6nmR = (String)input.readObject();
		niniLd_7cdR = (String)input.readObject();
		niniLd_7nmR = (String)input.readObject();
		niniLd_8cdR = (String)input.readObject();
		niniLd_8nmR = (String)input.readObject();
		niniLd_9cdR = (String)input.readObject();
		niniLd_9nmR = (String)input.readObject();
		niniLd_10cdR = (String)input.readObject();
		niniLd_10nmR = (String)input.readObject();
		niniLd_11cdR = (String)input.readObject();
		niniLd_11nmR = (String)input.readObject();
		niniLd_12cdR = (String)input.readObject();
		niniLd_12nmR = (String)input.readObject();
		niniLd_13cdR = (String)input.readObject();
		niniLd_13nmR = (String)input.readObject();
		niniLd_14cdR = (String)input.readObject();
		niniLd_14nmR = (String)input.readObject();
		niniLd_15cdR = (String)input.readObject();
		niniLd_15nmR = (String)input.readObject();
		niniLd_16cdR = (String)input.readObject();
		niniLd_16nmR = (String)input.readObject();
		niniLd_17cdR = (String)input.readObject();
		niniLd_17nmR = (String)input.readObject();
		niniLd_18cdR = (String)input.readObject();
		niniLd_18nmR = (String)input.readObject();
		niniLd_19cdR = (String)input.readObject();
		niniLd_19nmR = (String)input.readObject();
		niniLd_20cdR = (String)input.readObject();
		niniLd_20nmR = (String)input.readObject();
		toshinkkyyR = Function.getExternalLong(input.readObject());
		ksgensonruigkkR = Function.getExternalLong(input.readObject());
		togensongkkR = Function.getExternalLong(input.readObject());
		skkcalczanzongkkR = Function.getExternalLong(input.readObject());
		gensonbokakR = Function.getExternalLong(input.readObject());
		gensonmaetainenkR = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsukR = Function.getExternalInteger(input.readObject());
		gensonymdkR = (String)input.readObject();
		genshistkgkkR = Function.getExternalLong(input.readObject());
		kaiteistkgkkR = Function.getExternalLong(input.readObject());
		kaiteitainenkR = Function.getExternalInteger(input.readObject());
		kaiteiymdkR = (String)input.readObject();
		yusenzogenkbnkR = (String)input.readObject();
		ksgensonruigkzR = Function.getExternalLong(input.readObject());
		togensongkzR = Function.getExternalLong(input.readObject());
		skkcalczanzongkzR = Function.getExternalLong(input.readObject());
		gensonbokazR = Function.getExternalLong(input.readObject());
		gensonmaetainenzR = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuzR = Function.getExternalInteger(input.readObject());
		gensonymdzR = (String)input.readObject();
		genshistkgkzR = Function.getExternalLong(input.readObject());
		kaiteistkgkzR = Function.getExternalLong(input.readObject());
		kaiteitainenzR = Function.getExternalInteger(input.readObject());
		kaiteiymdzR = (String)input.readObject();
		yusenzogenkbnzR = (String)input.readObject();
		ksgensonruigkL3R = Function.getExternalLong(input.readObject());
		togensongkL3R = Function.getExternalLong(input.readObject());
		skkcalczanzongkL3R = Function.getExternalLong(input.readObject());
		gensonbokaL3R = Function.getExternalLong(input.readObject());
		gensonmaetainenL3R = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuL3R = Function.getExternalInteger(input.readObject());
		gensonymdL3R = (String)input.readObject();
		genshistkgkL3R = Function.getExternalLong(input.readObject());
		kaiteistkgkL3R = Function.getExternalLong(input.readObject());
		kaiteitainenL3R = Function.getExternalInteger(input.readObject());
		kaiteiymdL3R = (String)input.readObject();
		yusenzogenkbnL3R = (String)input.readObject();
		ksgensonruigkL4R = Function.getExternalLong(input.readObject());
		togensongkL4R = Function.getExternalLong(input.readObject());
		skkcalczanzongkL4R = Function.getExternalLong(input.readObject());
		gensonbokaL4R = Function.getExternalLong(input.readObject());
		gensonmaetainenL4R = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuL4R = Function.getExternalInteger(input.readObject());
		gensonymdL4R = (String)input.readObject();
		genshistkgkL4R = Function.getExternalLong(input.readObject());
		kaiteistkgkL4R = Function.getExternalLong(input.readObject());
		kaiteitainenL4R = Function.getExternalInteger(input.readObject());
		kaiteiymdL4R = (String)input.readObject();
		yusenzogenkbnL4R = (String)input.readObject();
		ksgensonruigkL5R = Function.getExternalLong(input.readObject());
		togensongkL5R = Function.getExternalLong(input.readObject());
		skkcalczanzongkL5R = Function.getExternalLong(input.readObject());
		gensonbokaL5R = Function.getExternalLong(input.readObject());
		gensonmaetainenL5R = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuL5R = Function.getExternalInteger(input.readObject());
		gensonymdL5R = (String)input.readObject();
		genshistkgkL5R = Function.getExternalLong(input.readObject());
		kaiteistkgkL5R = Function.getExternalLong(input.readObject());
		kaiteitainenL5R = Function.getExternalInteger(input.readObject());
		kaiteiymdL5R = (String)input.readObject();
		yusenzogenkbnL5R = (String)input.readObject();
		ksgensonruigkL6R = Function.getExternalLong(input.readObject());
		togensongkL6R = Function.getExternalLong(input.readObject());
		skkcalczanzongkL6R = Function.getExternalLong(input.readObject());
		gensonbokaL6R = Function.getExternalLong(input.readObject());
		gensonmaetainenL6R = Function.getExternalInteger(input.readObject());
		gensonmaeskkmmsuL6R = Function.getExternalInteger(input.readObject());
		gensonymdL6R = (String)input.readObject();
		genshistkgkL6R = Function.getExternalLong(input.readObject());
		kaiteistkgkL6R = Function.getExternalLong(input.readObject());
		kaiteitainenL6R = Function.getExternalInteger(input.readObject());
		kaiteiymdL6R = (String)input.readObject();
		yusenzogenkbnL6R = (String)input.readObject();
		updidR = (String)input.readObject();
		updymdR = (String)input.readObject();

		kadoymdRF = (String)input.readObject();
		jbkymdRF = (String)input.readObject();
		idoymdRF = (String)input.readObject();

		mitsumorihkymdkRF = (String)input.readObject();
		mitsumorihkymdzRF = (String)input.readObject();
		mitsumorihkymdL3RF = (String)input.readObject();
		mitsumorihkymdL4RF = (String)input.readObject();
		mitsumorihkymdL5RF = (String)input.readObject();
		mitsumorihkymdL6RF = (String)input.readObject();

		famitsumorihkymdkRF = (String)input.readObject();
		famitsumorihkymdzRF = (String)input.readObject();
		famitsumorihkymdL3RF = (String)input.readObject();
		famitsumorihkymdL4RF = (String)input.readObject();
		famitsumorihkymdL5RF = (String)input.readObject();
		famitsumorihkymdL6RF = (String)input.readObject();
		*/

	}

	public void writeExternal(ObjectOutput output) throws IOException {
		//	固定資産
		output.writeObject(koyuno);
		output.writeObject(kaishacd);
		output.writeObject(kaishanm);
		output.writeObject(oya);
		output.writeObject(eda);
		output.writeObject(shisanknrkbn);
		output.writeObject(shisanjotaikbn);
		output.writeObject(shisannmcd);
		output.writeObject(shisannm);
		output.writeObject(shisannmk);
		output.writeObject(soshiki1cd);
		output.writeObject(soshiki1nm);
		output.writeObject(soshiki2cd);
		output.writeObject(soshiki2nm);
		output.writeObject(soshiki3cd);
		output.writeObject(soshiki3nm);
		output.writeObject(soshiki4cd);
		output.writeObject(soshiki4nm);
		output.writeObject(setchicd);
		output.writeObject(setchinm);
		output.writeObject(skkhihfcd);
		output.writeObject(hfnm);
		output.writeObject(laryohfcd);
		output.writeObject(laryohfnm);
		output.writeObject(shuruicd);
		output.writeObject(shuruinm);
		output.writeObject(kozocd);
		output.writeObject(kozonm);
		output.writeObject(bunruicd);
		output.writeObject(bunruinm);
		output.writeObject(cpkeijoymd);
		output.writeObject(furikaeymd);
		output.writeObject(furikaegoOya);
		output.writeObject(furikaegoEda);
		output.writeObject(furikaegoShisanNum);
		output.writeObject(stkymd);
		output.writeObject(kadoymd);
		output.writeObject(jbkymd);
		output.writeObject(idoymd);
		output.writeObject(suryo);
		output.writeObject(suryoTani);
		output.writeObject(menseki);
		output.writeObject(mensekiTani);
		output.writeObject(chukokbn);
		output.writeObject(tosaimukeijokbn);
		output.writeObject(jksaimukeijokbn);
		output.writeObject(stkgkk);
		output.writeObject(askstkgkk);
		output.writeObject(skkhok);
		output.writeObject(tainenk);
		output.writeObject(skkritsuk);
		output.writeObject(skkmmsuk);
		output.writeObject(zanzonmmsuk);
		output.writeObject(ksbokak);
		output.writeObject(skkcalckisogkk);
		output.writeObject(zanzonritsuk);
		output.writeObject(zanzongkk);
		output.writeObject(ksftskkruigkk);
		output.writeObject(kszkskkruigkk);
		output.writeObject(kstkskkruigkk);
		output.writeObject(zenToftskkgkk);
		output.writeObject(zenTozkskkgkk);
		output.writeObject(zenTotkskkgkk);
		output.writeObject(zenToniniskkgkk);
		output.writeObject(toToftskkgkk);
		output.writeObject(toTozkskkgkk);
		output.writeObject(toTotkskkgkk);
		output.writeObject(toToniniskkgkk);
		output.writeObject(toBokak);
		output.writeObject(zogenbokak);
		output.writeObject(zkskkritsuk);
		output.writeObject(yukyustymk);
		output.writeObject(yukyufkymk);
		output.writeObject(shonencalckbnk);
		output.writeObject(skkkanryoflgk);
		output.writeObject(skkkirikaeyyk);
		output.writeObject(zanzonskkstyyk);
		output.writeObject(zanzonskkmmsuk);
		output.writeObject(famitsumorihkymdk);
		output.writeObject(mitsumorihkgobokak);
		output.writeObject(mitsumorihkmaetainenk);
		output.writeObject(mitsumorihkmaeskkmmsuk);
		output.writeObject(mitsumorihkmaeskkritsuk);
		output.writeObject(stkgkz);
		output.writeObject(askstkgkz);
		output.writeObject(skkhoz);
		output.writeObject(tainenz);
		output.writeObject(skkritsuz);
		output.writeObject(skkmmsuz);
		output.writeObject(zanzonmmsuz);
		output.writeObject(ksbokaz);
		output.writeObject(skkcalckisogkz);
		output.writeObject(zanzonritsuz);
		output.writeObject(zanzongkz);
		output.writeObject(ksftskkruigkz);
		output.writeObject(kszkskkruigkz);
		output.writeObject(kstkskkruigkz);
		output.writeObject(zenToftskkgkz);
		output.writeObject(zenTozkskkgkz);
		output.writeObject(zenTotkskkgkz);
		output.writeObject(zenToniniskkgkz);
		output.writeObject(toToftskkgkz);
		output.writeObject(toTozkskkgkz);
		output.writeObject(toTotkskkgkz);
		output.writeObject(toToniniskkgkz);
		output.writeObject(toBokaz);
		output.writeObject(zogenbokaz);
		output.writeObject(zkskkritsuz);
		output.writeObject(yukyustymz);
		output.writeObject(yukyufkymz);
		output.writeObject(shonencalckbnz);
		output.writeObject(skkkanryoflgz);
		output.writeObject(skkkirikaeyyz);
		output.writeObject(zanzonskkstyyz);
		output.writeObject(zanzonskkmmsuz);
		output.writeObject(famitsumorihkymdz);
		output.writeObject(mitsumorihkgobokaz);
		output.writeObject(mitsumorihkmaetainenz);
		output.writeObject(mitsumorihkmaeskkmmsuz);
		output.writeObject(mitsumorihkmaeskkritsuz);
		output.writeObject(stkgkL3);
		output.writeObject(askstkgkL3);
		output.writeObject(skkhoL3);
		output.writeObject(tainenL3);
		output.writeObject(skkritsuL3);
		output.writeObject(skkmmsuL3);
		output.writeObject(zanzonmmsuL3);
		output.writeObject(ksbokaL3);
		output.writeObject(skkcalckisogkL3);
		output.writeObject(zanzonritsuL3);
		output.writeObject(zanzongkL3);
		output.writeObject(ksftskkruigkL3);
		output.writeObject(kszkskkruigkL3);
		output.writeObject(kstkskkruigkL3);
		output.writeObject(zenToftskkgkL3);
		output.writeObject(zenTozkskkgkL3);
		output.writeObject(zenTotkskkgkL3);
		output.writeObject(zenToniniskkgkL3);
		output.writeObject(toToftskkgkL3);
		output.writeObject(toTozkskkgkL3);
		output.writeObject(toTotkskkgkL3);
		output.writeObject(toToniniskkgkL3);
		output.writeObject(toBokaL3);
		output.writeObject(zogenbokaL3);
		output.writeObject(zkskkritsuL3);
		output.writeObject(yukyustymL3);
		output.writeObject(yukyufkymL3);
		output.writeObject(shonencalckbnL3);
		output.writeObject(skkkanryoflgL3);
		output.writeObject(skkkirikaeyyL3);
		output.writeObject(zanzonskkstyyL3);
		output.writeObject(zanzonskkmmsuL3);
		output.writeObject(famitsumorihkymdL3);
		output.writeObject(mitsumorihkgobokaL3);
		output.writeObject(mitsumorihkmaetainenL3);
		output.writeObject(mitsumorihkmaeskkmmsuL3);
		output.writeObject(mitsumorihkmaeskkritsuL3);
		output.writeObject(stkgkL4);
		output.writeObject(askstkgkL4);
		output.writeObject(skkhoL4);
		output.writeObject(tainenL4);
		output.writeObject(skkritsuL4);
		output.writeObject(skkmmsuL4);
		output.writeObject(zanzonmmsuL4);
		output.writeObject(ksbokaL4);
		output.writeObject(skkcalckisogkL4);
		output.writeObject(zanzonritsuL4);
		output.writeObject(zanzongkL4);
		output.writeObject(ksftskkruigkL4);
		output.writeObject(kszkskkruigkL4);
		output.writeObject(kstkskkruigkL4);
		output.writeObject(zenToftskkgkL4);
		output.writeObject(zenTozkskkgkL4);
		output.writeObject(zenTotkskkgkL4);
		output.writeObject(zenToniniskkgkL4);
		output.writeObject(toToftskkgkL4);
		output.writeObject(toTozkskkgkL4);
		output.writeObject(toTotkskkgkL4);
		output.writeObject(toToniniskkgkL4);
		output.writeObject(toBokaL4);
		output.writeObject(zogenbokaL4);
		output.writeObject(zkskkritsuL4);
		output.writeObject(yukyustymL4);
		output.writeObject(yukyufkymL4);
		output.writeObject(shonencalckbnL4);
		output.writeObject(skkkanryoflgL4);
		output.writeObject(skkkirikaeyyL4);
		output.writeObject(zanzonskkstyyL4);
		output.writeObject(zanzonskkmmsuL4);
		output.writeObject(famitsumorihkymdL4);
		output.writeObject(mitsumorihkgobokaL4);
		output.writeObject(mitsumorihkmaetainenL4);
		output.writeObject(mitsumorihkmaeskkmmsuL4);
		output.writeObject(mitsumorihkmaeskkritsuL4);
		output.writeObject(stkgkL5);
		output.writeObject(askstkgkL5);
		output.writeObject(skkhoL5);
		output.writeObject(tainenL5);
		output.writeObject(skkritsuL5);
		output.writeObject(skkmmsuL5);
		output.writeObject(zanzonmmsuL5);
		output.writeObject(ksbokaL5);
		output.writeObject(skkcalckisogkL5);
		output.writeObject(zanzonritsuL5);
		output.writeObject(zanzongkL5);
		output.writeObject(ksftskkruigkL5);
		output.writeObject(kszkskkruigkL5);
		output.writeObject(kstkskkruigkL5);
		output.writeObject(zenToftskkgkL5);
		output.writeObject(zenTozkskkgkL5);
		output.writeObject(zenTotkskkgkL5);
		output.writeObject(zenToniniskkgkL5);
		output.writeObject(toToftskkgkL5);
		output.writeObject(toTozkskkgkL5);
		output.writeObject(toTotkskkgkL5);
		output.writeObject(toToniniskkgkL5);
		output.writeObject(toBokaL5);
		output.writeObject(zogenbokaL5);
		output.writeObject(zkskkritsuL5);
		output.writeObject(yukyustymL5);
		output.writeObject(yukyufkymL5);
		output.writeObject(shonencalckbnL5);
		output.writeObject(skkkanryoflgL5);
		output.writeObject(skkkirikaeyyL5);
		output.writeObject(zanzonskkstyyL5);
		output.writeObject(zanzonskkmmsuL5);
		output.writeObject(famitsumorihkymdL5);
		output.writeObject(mitsumorihkgobokaL5);
		output.writeObject(mitsumorihkmaetainenL5);
		output.writeObject(mitsumorihkmaeskkmmsuL5);
		output.writeObject(mitsumorihkmaeskkritsuL5);
		output.writeObject(stkgkL6);
		output.writeObject(askstkgkL6);
		output.writeObject(skkhoL6);
		output.writeObject(tainenL6);
		output.writeObject(skkritsuL6);
		output.writeObject(skkmmsuL6);
		output.writeObject(zanzonmmsuL6);
		output.writeObject(ksbokaL6);
		output.writeObject(skkcalckisogkL6);
		output.writeObject(zanzonritsuL6);
		output.writeObject(zanzongkL6);
		output.writeObject(ksftskkruigkL6);
		output.writeObject(kszkskkruigkL6);
		output.writeObject(kstkskkruigkL6);
		output.writeObject(zenToftskkgkL6);
		output.writeObject(zenTozkskkgkL6);
		output.writeObject(zenTotkskkgkL6);
		output.writeObject(zenToniniskkgkL6);
		output.writeObject(toToftskkgkL6);
		output.writeObject(toTozkskkgkL6);
		output.writeObject(toTotkskkgkL6);
		output.writeObject(toToniniskkgkL6);
		output.writeObject(toBokaL6);
		output.writeObject(zogenbokaL6);
		output.writeObject(zkskkritsuL6);
		output.writeObject(yukyustymL6);
		output.writeObject(yukyufkymL6);
		output.writeObject(shonencalckbnL6);
		output.writeObject(skkkanryoflgL6);
		output.writeObject(skkkirikaeyyL6);
		output.writeObject(zanzonskkstyyL6);
		output.writeObject(zanzonskkmmsuL6);
		output.writeObject(famitsumorihkymdL6);
		output.writeObject(mitsumorihkgobokaL6);
		output.writeObject(mitsumorihkmaetainenL6);
		output.writeObject(mitsumorihkmaeskkmmsuL6);
		output.writeObject(mitsumorihkmaeskkritsuL6);
		output.writeObject(konyucd);
		output.writeObject(konyunm);
		output.writeObject(kashicd);
		output.writeObject(kashinm);
		output.writeObject(cpOya);
		output.writeObject(cpEda);
		output.writeObject(cpShisanNum);
		output.writeObject(knrbunruicd);
		output.writeObject(biko1);
		output.writeObject(biko2);
		output.writeObject(stkringino);
		output.writeObject(stktekiyo);
		output.writeObject(askcd);
		output.writeObject(asknm);
		output.writeObject(askkbn);
		output.writeObject(askgk);
		output.writeObject(ksaskzan);
		output.writeObject(ksaskninyogk);
		output.writeObject(askninyogk);
		output.writeObject(askkisogk);
		output.writeObject(askzanzongk);
		output.writeObject(kaiteiaskgk);
		output.writeObject(skkchokagk);
		output.writeObject(skkfusokugk);
		output.writeObject(tkcd);
		output.writeObject(tknm);
		output.writeObject(tkkbn);
		output.writeObject(tkritsuBunshi);
		output.writeObject(tkritsuBunbo);
		output.writeObject(beppyotaishokbn);
		output.writeObject(skkshisankbn);
		output.writeObject(aitekanjocd);
		output.writeObject(aitekanjonm);
		output.writeObject(aitehojokamokucd);
		output.writeObject(aitehojokamokunm);
		output.writeObject(gappeiukekbn);
		output.writeObject(genshistkymd);
		output.writeObject(groupcd);
		output.writeObject(groupnm);
		output.writeObject(shinariocd);
		output.writeObject(shinarionm);
		output.writeObject(shuyoshisankbn);
		output.writeObject(niniLd_1cd);
		output.writeObject(niniLd_1nm);
		output.writeObject(niniLd_2cd);
		output.writeObject(niniLd_2nm);
		output.writeObject(niniLd_3cd);
		output.writeObject(niniLd_3nm);
		output.writeObject(niniLd_4cd);
		output.writeObject(niniLd_4nm);
		output.writeObject(niniLd_5cd);
		output.writeObject(niniLd_5nm);
		output.writeObject(niniLd_6cd);
		output.writeObject(niniLd_6nm);
		output.writeObject(niniLd_7cd);
		output.writeObject(niniLd_7nm);
		output.writeObject(niniLd_8cd);
		output.writeObject(niniLd_8nm);
		output.writeObject(niniLd_9cd);
		output.writeObject(niniLd_9nm);
		output.writeObject(niniLd_10cd);
		output.writeObject(niniLd_10nm);
		output.writeObject(niniLd_11cd);
		output.writeObject(niniLd_11nm);
		output.writeObject(niniLd_12cd);
		output.writeObject(niniLd_12nm);
		output.writeObject(niniLd_13cd);
		output.writeObject(niniLd_13nm);
		output.writeObject(niniLd_14cd);
		output.writeObject(niniLd_14nm);
		output.writeObject(niniLd_15cd);
		output.writeObject(niniLd_15nm);
		output.writeObject(niniLd_16cd);
		output.writeObject(niniLd_16nm);
		output.writeObject(niniLd_17cd);
		output.writeObject(niniLd_17nm);
		output.writeObject(niniLd_18cd);
		output.writeObject(niniLd_18nm);
		output.writeObject(niniLd_19cd);
		output.writeObject(niniLd_19nm);
		output.writeObject(niniLd_20cd);
		output.writeObject(niniLd_20nm);
		output.writeObject(toshinkkyy);
		output.writeObject(toshinkkchicd);
		output.writeObject(toshinkkchinm);
		output.writeObject(toshinkkshurui);
		output.writeObject(toshinkkstkgk);
		output.writeObject(toshinkktainen);
		output.writeObject(toshinkkZkskkritsu);
		output.writeObject(tothzeicd);
		output.writeObject(tothzeinm);
		output.writeObject(totkreiritsuBunshi);
		output.writeObject(totkreiritsuBunbo);
		output.writeObject(zenshinkkyy);
		output.writeObject(zenshinkkchicd);
		output.writeObject(zenshinkkchinm);
		output.writeObject(zenshinkkshurui);
		output.writeObject(zenshinkkstkgk);
		output.writeObject(zenshinkktainen);
		output.writeObject(zenshinkkZkskkritsu);
		output.writeObject(zenthzeicd);
		output.writeObject(zenthzeinm);
		output.writeObject(zentkreiritsuBunshi);
		output.writeObject(zentkreiritsuBunbo);
		output.writeObject(zenrironboka);
		output.writeObject(zenhyokagk);
		output.writeObject(ykshinkkyy);
		output.writeObject(ykshinkkchicd);
		output.writeObject(ykshinkkchinm);
		output.writeObject(ykshinkkshurui);
		output.writeObject(ykshinkkstkgk);
		output.writeObject(ykshinkktainen);
		output.writeObject(ykshinkkZkskkritsu);
		output.writeObject(ykthzeicd);
		output.writeObject(ykthzeinm);
		output.writeObject(yktkreiritsuBunshi);
		output.writeObject(yktkreiritsuBunbo);
		output.writeObject(lastshinkkyy);
		output.writeObject(togensonkbnk);
		output.writeObject(ksgensonruigkk);
		output.writeObject(togensongkk);
		output.writeObject(skkcalczanzongkk);
		output.writeObject(gensonbokak);
		output.writeObject(gensonmaetainenk);
		output.writeObject(gensonmaeskkmmsuk);
		output.writeObject(gensonymdk);
		output.writeObject(genshistkgkk);
		output.writeObject(kaiteistkgkk);
		output.writeObject(kaiteitainenk);
		output.writeObject(kaiteiymdk);
		output.writeObject(yusenzogenkbnk);
		output.writeObject(togensonkbnz);
		output.writeObject(ksgensonruigkz);
		output.writeObject(togensongkz);
		output.writeObject(skkcalczanzongkz);
		output.writeObject(gensonbokaz);
		output.writeObject(gensonmaetainenz);
		output.writeObject(gensonmaeskkmmsuz);
		output.writeObject(gensonymdz);
		output.writeObject(genshistkgkz);
		output.writeObject(kaiteistkgkz);
		output.writeObject(kaiteitainenz);
		output.writeObject(kaiteiymdz);
		output.writeObject(yusenzogenkbnz);
		output.writeObject(togensonkbnL3);
		output.writeObject(ksgensonruigkL3);
		output.writeObject(togensongkL3);
		output.writeObject(skkcalczanzongkL3);
		output.writeObject(gensonbokaL3);
		output.writeObject(gensonmaetainenL3);
		output.writeObject(gensonmaeskkmmsuL3);
		output.writeObject(gensonymdL3);
		output.writeObject(genshistkgkL3);
		output.writeObject(kaiteistkgkL3);
		output.writeObject(kaiteitainenL3);
		output.writeObject(kaiteiymdL3);
		output.writeObject(yusenzogenkbnL3);
		output.writeObject(togensonkbnL4);
		output.writeObject(ksgensonruigkL4);
		output.writeObject(togensongkL4);
		output.writeObject(skkcalczanzongkL4);
		output.writeObject(gensonbokaL4);
		output.writeObject(gensonmaetainenL4);
		output.writeObject(gensonmaeskkmmsuL4);
		output.writeObject(gensonymdL4);
		output.writeObject(genshistkgkL4);
		output.writeObject(kaiteistkgkL4);
		output.writeObject(kaiteitainenL4);
		output.writeObject(kaiteiymdL4);
		output.writeObject(yusenzogenkbnL4);
		output.writeObject(togensonkbnL5);
		output.writeObject(ksgensonruigkL5);
		output.writeObject(togensongkL5);
		output.writeObject(skkcalczanzongkL5);
		output.writeObject(gensonbokaL5);
		output.writeObject(gensonmaetainenL5);
		output.writeObject(gensonmaeskkmmsuL5);
		output.writeObject(gensonymdL5);
		output.writeObject(genshistkgkL5);
		output.writeObject(kaiteistkgkL5);
		output.writeObject(kaiteitainenL5);
		output.writeObject(kaiteiymdL5);
		output.writeObject(yusenzogenkbnL5);
		output.writeObject(togensonkbnL6);
		output.writeObject(ksgensonruigkL6);
		output.writeObject(togensongkL6);
		output.writeObject(skkcalczanzongkL6);
		output.writeObject(gensonbokaL6);
		output.writeObject(gensonmaetainenL6);
		output.writeObject(gensonmaeskkmmsuL6);
		output.writeObject(gensonymdL6);
		output.writeObject(genshistkgkL6);
		output.writeObject(kaiteistkgkL6);
		output.writeObject(kaiteitainenL6);
		output.writeObject(kaiteiymdL6);
		output.writeObject(yusenzogenkbnL6);
		output.writeObject(updkaisu);
		output.writeObject(updkaishacd);
		output.writeObject(updid);
		output.writeObject(updymd);
		output.writeObject(updtime);
		output.writeObject(routeType);
		output.writeObject(routeTypeName);
		output.writeObject(purCompanyCode);
		output.writeObject(purCompanyName);
		output.writeObject(itemGroupCode);
		output.writeObject(itemGroupName);
		output.writeObject(manageSectionCode);
		output.writeObject(manageSectionName);
		output.writeObject(astPrjCode);
		output.writeObject(astPrjName);
		output.writeObject(depPrjCode);
		output.writeObject(depPrjName);
		output.writeObject(costType);
		output.writeObject(costTypeName);
		output.writeObject(applicationId);
		output.writeObject(slipNum);
		output.writeObject(interestFlag);
		output.writeObject(interestFlagName);
		output.writeObject(apStaff);
		output.writeObject(inventoryFlag);
		output.writeObject(inventoryFlagName);
		output.writeObject(disposePlanCode);
		output.writeObject(disposePlanName);
		output.writeObject(taxAdjustCode);
		output.writeObject(taxAdjustName);
		output.writeObject(contractBranchNum);
		output.writeObject(chargeType);
		output.writeObject(chargeTypeName);
		output.writeObject(disposeReasonCode);
		output.writeObject(disposeReasonName);
		output.writeObject(disposeSectionCode);
		output.writeObject(disposeSectionName);
		output.writeObject(calcType);
		output.writeObject(calcTypeName);
		output.writeObject(upperSectionCode);
		output.writeObject(upperSectionName);
		output.writeObject(addUpPlanDate);
		output.writeObject(addUpPlanDateName);
		output.writeObject(paTemplateCode);
		output.writeObject(paTemplateName);
		output.writeObject(upperAccountCode);
		output.writeObject(upperAccountName);
		output.writeObject(jgzzReconRef);
		output.writeObject(expenseType);
		output.writeObject(expenseName);

		output.writeObject(ksskkruigkk);
		output.writeObject(ksskkruigkz);
		output.writeObject(ksskkruigkL3);
		output.writeObject(ksskkruigkL4);
		output.writeObject(ksskkruigkL5);
		output.writeObject(ksskkruigkL6);
		output.writeObject(toToskkgkk);
		output.writeObject(toToskkgkz);
		output.writeObject(toToskkgkL3);
		output.writeObject(toToskkgkL4);
		output.writeObject(toToskkgkL5);
		output.writeObject(toToskkgkL6);
		output.writeObject(toSkkgkk);
		output.writeObject(toSkkgkz);
		output.writeObject(toSkkgkL3);
		output.writeObject(toSkkgkL4);
		output.writeObject(toSkkgkL5);
		output.writeObject(toSkkgkL6);

		output.writeObject(shisanknrkbnName);
		output.writeObject(shisanjotaikbnName);
		output.writeObject(togensonkbnkName);


		output.writeObject(shisanNum);

		output.writeObject(cpkeijoymdF);
		output.writeObject(furikaeymdF);
		output.writeObject(stkymdF);
		output.writeObject(kadoymdF);
		output.writeObject(jbkymdF);
		output.writeObject(idoymdF);
		output.writeObject(genshistkymdF);
		output.writeObject(gensonymdkF);
		output.writeObject(kaiteiymdkF);
		output.writeObject(gensonymdzF);
		output.writeObject(kaiteiymdzF);
		output.writeObject(gensonymdL3F);
		output.writeObject(kaiteiymdL3F);
		output.writeObject(gensonymdL4F);
		output.writeObject(kaiteiymdL4F);
		output.writeObject(gensonymdL5F);
		output.writeObject(kaiteiymdL5F);
		output.writeObject(gensonymdL6F);
		output.writeObject(kaiteiymdL6F);
		output.writeObject(updymdF);
		output.writeObject(addUpPlanDateF);

		output.writeObject(yukyustymkF);
		output.writeObject(yukyustymzF);
		output.writeObject(yukyustymL3F);
		output.writeObject(yukyustymL4F);
		output.writeObject(yukyustymL5F);
		output.writeObject(yukyustymL6F);

		output.writeObject(yukyufkymkF);
		output.writeObject(yukyufkymzF);
		output.writeObject(yukyufkymL3F);
		output.writeObject(yukyufkymL4F);
		output.writeObject(yukyufkymL5F);
		output.writeObject(yukyufkymL6F);

		output.writeObject(chukokbnName);
		output.writeObject(skkshisankbnName);
		output.writeObject(askkbnName);
		output.writeObject(tkkbnName);
		output.writeObject(shuyoshisankbnName);
		output.writeObject(gappeiukekbnName);
		output.writeObject(jksaimukeijokbnName);

		output.writeObject(skkhokName);
		output.writeObject(skkhozName);
		output.writeObject(skkhoL3Name);
		output.writeObject(skkhoL4Name);
		output.writeObject(skkhoL5Name);
		output.writeObject(skkhoL6Name);

		output.writeObject(shonencalckbnkName);
		output.writeObject(shonencalckbnzName);
		output.writeObject(shonencalckbnL3Name);
		output.writeObject(shonencalckbnL4Name);
		output.writeObject(shonencalckbnL5Name);
		output.writeObject(shonencalckbnL6Name);

		output.writeObject(skkkanryoflgkName);
		output.writeObject(skkkanryoflgzName);
		output.writeObject(skkkanryoflgL3Name);
		output.writeObject(skkkanryoflgL4Name);
		output.writeObject(skkkanryoflgL5Name);
		output.writeObject(skkkanryoflgL6Name);

		output.writeObject(assetList);
		output.writeObject(licenseList);


		//	除却債務
		output.writeObject(saimuhfcdR);
		output.writeObject(saimuhfnmR);
		output.writeObject(saimukeijoymdRF);
		output.writeObject(saimurikoymdRF);

		output.writeObject(mitsumorigkWaribikimaekR);
		output.writeObject(waribikiritsukR);
		output.writeObject(keijogkWaribikigokR);
		output.writeObject(kssaimubokakR);
		output.writeObject(risokucalckisogkkR);
		output.writeObject(toSaimubokakR);
		output.writeObject(toSaimuzogenbokakR);
		output.writeObject(toTorisokugkkR);
		output.writeObject(ksrisokuruigkkR);

		output.writeObject(mitsumorigkWaribikimaezR);
		output.writeObject(waribikiritsuzR);
		output.writeObject(keijogkWaribikigozR);
		output.writeObject(kssaimubokazR);
		output.writeObject(risokucalckisogkzR);
		output.writeObject(toSaimubokazR);
		output.writeObject(toSaimuzogenbokazR);
		output.writeObject(toTorisokugkzR);
		output.writeObject(ksrisokuruigkzR);

		output.writeObject(mitsumorigkWaribikimaeL3R);
		output.writeObject(waribikiritsuL3R);
		output.writeObject(keijogkWaribikigoL3R);
		output.writeObject(kssaimubokaL3R);
		output.writeObject(risokucalckisogkL3R);
		output.writeObject(toSaimubokaL3R);
		output.writeObject(toSaimuzogenbokaL3R);
		output.writeObject(toTorisokugkL3R);
		output.writeObject(ksrisokuruigkL3R);

		output.writeObject(mitsumorigkWaribikimaeL4R);
		output.writeObject(waribikiritsuL4R);
		output.writeObject(keijogkWaribikigoL4R);
		output.writeObject(kssaimubokaL4R);
		output.writeObject(risokucalckisogkL4R);
		output.writeObject(toSaimubokaL4R);
		output.writeObject(toSaimuzogenbokaL4R);
		output.writeObject(toTorisokugkL4R);
		output.writeObject(ksrisokuruigkL4R);

		output.writeObject(mitsumorigkWaribikimaeL5R);
		output.writeObject(waribikiritsuL5R);
		output.writeObject(keijogkWaribikigoL5R);
		output.writeObject(kssaimubokaL5R);
		output.writeObject(risokucalckisogkL5R);
		output.writeObject(toSaimubokaL5R);
		output.writeObject(toSaimuzogenbokaL5R);
		output.writeObject(toTorisokugkL5R);
		output.writeObject(ksrisokuruigkL5R);

		output.writeObject(mitsumorigkWaribikimaeL6R);
		output.writeObject(waribikiritsuL6R);
		output.writeObject(keijogkWaribikigoL6R);
		output.writeObject(kssaimubokaL6R);
		output.writeObject(risokucalckisogkL6R);
		output.writeObject(toSaimubokaL6R);
		output.writeObject(toSaimuzogenbokaL6R);
		output.writeObject(toTorisokugkL6R);
		output.writeObject(ksrisokuruigkL6R);

		/* 除去債務不要項目
		output.writeObject(koyunoR);
		output.writeObject(kaishacdR);
		output.writeObject(kaishanmR);
		output.writeObject(oyaR);
		output.writeObject(edaR);
		output.writeObject(shisanknrkbnR);
		output.writeObject(shisanjotaikbnR);
		output.writeObject(shisannmcdR);
		output.writeObject(shisannmR);
		output.writeObject(shisannmkR);
		output.writeObject(soshiki1cdR);
		output.writeObject(soshiki1nmR);
		output.writeObject(soshiki2cdR);
		output.writeObject(soshiki2nmR);
		output.writeObject(soshiki3cdR);
		output.writeObject(soshiki3nmR);
		output.writeObject(soshiki4cdR);
		output.writeObject(soshiki4nmR);
		output.writeObject(setchicdR);
		output.writeObject(setchinmR);
		output.writeObject(skkhihfcdR);
		output.writeObject(hfnmR);
		output.writeObject(shuruicdR);
		output.writeObject(shuruinmR);
		output.writeObject(kozocdR);
		output.writeObject(kozonmR);
		output.writeObject(bunruicdR);
		output.writeObject(bunruinmR);
		output.writeObject(saimukeijoymdR);
		output.writeObject(saimurikoymdR);
		output.writeObject(kadoymdR);
		output.writeObject(jbkymdR);
		output.writeObject(idoymdR);
		output.writeObject(suryoR);
		output.writeObject(suryoTaniR);
		output.writeObject(mensekiR);
		output.writeObject(mensekiTaniR);
		output.writeObject(tosaimukeijokbnR);
		output.writeObject(jksaimukeijokbnR);
		output.writeObject(mitsumorihkymdkR);
		output.writeObject(famitsumorihkymdkR);
		output.writeObject(mitsumorihkgobokakR);
		output.writeObject(mitsumorihkmaetainenkR);
		output.writeObject(mitsumorihkmaeskkmmsukR);
		output.writeObject(mitsumorihkmaeskkritsukR);
		output.writeObject(tomitsumorihkchoseigkkR);
		output.writeObject(stkgkkR);
		output.writeObject(skkhokR);
		output.writeObject(tainenkR);
		output.writeObject(skkritsukR);
		output.writeObject(skkmmsukR);
		output.writeObject(zanzonmmsukR);
		output.writeObject(ksbokakR);
		output.writeObject(skkcalckisogkkR);
		output.writeObject(zanzonritsukR);
		output.writeObject(zanzongkkR);
		output.writeObject(ksftskkruigkkR);
		output.writeObject(kszkskkruigkkR);
		output.writeObject(toToftskkgkkR);
		output.writeObject(toTozkskkgkkR);
		output.writeObject(toToniniskkgkkR);
		output.writeObject(toBokakR);
		output.writeObject(zogenbokakR);
		output.writeObject(zkskkritsukR);
		output.writeObject(yukyustymkR);
		output.writeObject(yukyufkymkR);
		output.writeObject(shonencalckbnkR);
		output.writeObject(skkkanryoflgkR);
		output.writeObject(skkkirikaeyykR);
		output.writeObject(zanzonskkstyykR);
		output.writeObject(mitsumorihkymdzR);
		output.writeObject(famitsumorihkymdzR);
		output.writeObject(mitsumorihkgobokazR);
		output.writeObject(mitsumorihkmaetainenzR);
		output.writeObject(mitsumorihkmaeskkmmsuzR);
		output.writeObject(mitsumorihkmaeskkritsuzR);
		output.writeObject(tomitsumorihkchoseigkzR);
		output.writeObject(stkgkzR);
		output.writeObject(skkhozR);
		output.writeObject(tainenzR);
		output.writeObject(skkritsuzR);
		output.writeObject(skkmmsuzR);
		output.writeObject(zanzonmmsuzR);
		output.writeObject(ksbokazR);
		output.writeObject(skkcalckisogkzR);
		output.writeObject(zanzonritsuzR);
		output.writeObject(zanzongkzR);
		output.writeObject(ksftskkruigkzR);
		output.writeObject(kszkskkruigkzR);
		output.writeObject(toToftskkgkzR);
		output.writeObject(toTozkskkgkzR);
		output.writeObject(toToniniskkgkzR);
		output.writeObject(toBokazR);
		output.writeObject(zogenbokazR);
		output.writeObject(zkskkritsuzR);
		output.writeObject(yukyustymzR);
		output.writeObject(yukyufkymzR);
		output.writeObject(shonencalckbnzR);
		output.writeObject(skkkanryoflgzR);
		output.writeObject(skkkirikaeyyzR);
		output.writeObject(zanzonskkstyyzR);
		output.writeObject(mitsumorihkymdL3R);
		output.writeObject(famitsumorihkymdL3R);
		output.writeObject(mitsumorihkgobokaL3R);
		output.writeObject(mitsumorihkmaetainenL3R);
		output.writeObject(mitsumorihkmaeskkmmsuL3R);
		output.writeObject(mitsumorihkmaeskkritsuL3R);
		output.writeObject(tomitsumorihkchoseigkL3R);
		output.writeObject(stkgkL3R);
		output.writeObject(skkhoL3R);
		output.writeObject(tainenL3R);
		output.writeObject(skkritsuL3R);
		output.writeObject(skkmmsuL3R);
		output.writeObject(zanzonmmsuL3R);
		output.writeObject(ksbokaL3R);
		output.writeObject(skkcalckisogkL3R);
		output.writeObject(zanzonritsuL3R);
		output.writeObject(zanzongkL3R);
		output.writeObject(ksftskkruigkL3R);
		output.writeObject(kszkskkruigkL3R);
		output.writeObject(toToftskkgkL3R);
		output.writeObject(toTozkskkgkL3R);
		output.writeObject(toToniniskkgkL3R);
		output.writeObject(toBokaL3R);
		output.writeObject(zogenbokaL3R);
		output.writeObject(zkskkritsuL3R);
		output.writeObject(yukyustymL3R);
		output.writeObject(yukyufkymL3R);
		output.writeObject(shonencalckbnL3R);
		output.writeObject(skkkanryoflgL3R);
		output.writeObject(skkkirikaeyyL3R);
		output.writeObject(zanzonskkstyyL3R);
		output.writeObject(mitsumorihkymdL4R);
		output.writeObject(famitsumorihkymdL4R);
		output.writeObject(mitsumorihkgobokaL4R);
		output.writeObject(mitsumorihkmaetainenL4R);
		output.writeObject(mitsumorihkmaeskkmmsuL4R);
		output.writeObject(mitsumorihkmaeskkritsuL4R);
		output.writeObject(tomitsumorihkchoseigkL4R);
		output.writeObject(stkgkL4R);
		output.writeObject(skkhoL4R);
		output.writeObject(tainenL4R);
		output.writeObject(skkritsuL4R);
		output.writeObject(skkmmsuL4R);
		output.writeObject(zanzonmmsuL4R);
		output.writeObject(ksbokaL4R);
		output.writeObject(skkcalckisogkL4R);
		output.writeObject(zanzonritsuL4R);
		output.writeObject(zanzongkL4R);
		output.writeObject(ksftskkruigkL4R);
		output.writeObject(kszkskkruigkL4R);
		output.writeObject(toToftskkgkL4R);
		output.writeObject(toTozkskkgkL4R);
		output.writeObject(toToniniskkgkL4R);
		output.writeObject(toBokaL4R);
		output.writeObject(zogenbokaL4R);
		output.writeObject(zkskkritsuL4R);
		output.writeObject(yukyustymL4R);
		output.writeObject(yukyufkymL4R);
		output.writeObject(shonencalckbnL4R);
		output.writeObject(skkkanryoflgL4R);
		output.writeObject(skkkirikaeyyL4R);
		output.writeObject(zanzonskkstyyL4R);
		output.writeObject(mitsumorihkymdL5R);
		output.writeObject(famitsumorihkymdL5R);
		output.writeObject(mitsumorihkgobokaL5R);
		output.writeObject(mitsumorihkmaetainenL5R);
		output.writeObject(mitsumorihkmaeskkmmsuL5R);
		output.writeObject(mitsumorihkmaeskkritsuL5R);
		output.writeObject(tomitsumorihkchoseigkL5R);
		output.writeObject(stkgkL5R);
		output.writeObject(skkhoL5R);
		output.writeObject(tainenL5R);
		output.writeObject(skkritsuL5R);
		output.writeObject(skkmmsuL5R);
		output.writeObject(zanzonmmsuL5R);
		output.writeObject(ksbokaL5R);
		output.writeObject(skkcalckisogkL5R);
		output.writeObject(zanzonritsuL5R);
		output.writeObject(zanzongkL5R);
		output.writeObject(ksftskkruigkL5R);
		output.writeObject(kszkskkruigkL5R);
		output.writeObject(toToftskkgkL5R);
		output.writeObject(toTozkskkgkL5R);
		output.writeObject(toToniniskkgkL5R);
		output.writeObject(toBokaL5R);
		output.writeObject(zogenbokaL5R);
		output.writeObject(zkskkritsuL5R);
		output.writeObject(yukyustymL5R);
		output.writeObject(yukyufkymL5R);
		output.writeObject(shonencalckbnL5R);
		output.writeObject(skkkanryoflgL5R);
		output.writeObject(skkkirikaeyyL5R);
		output.writeObject(zanzonskkstyyL5R);
		output.writeObject(mitsumorihkymdL6R);
		output.writeObject(famitsumorihkymdL6R);
		output.writeObject(mitsumorihkgobokaL6R);
		output.writeObject(mitsumorihkmaetainenL6R);
		output.writeObject(mitsumorihkmaeskkmmsuL6R);
		output.writeObject(mitsumorihkmaeskkritsuL6R);
		output.writeObject(tomitsumorihkchoseigkL6R);
		output.writeObject(stkgkL6R);
		output.writeObject(skkhoL6R);
		output.writeObject(tainenL6R);
		output.writeObject(skkritsuL6R);
		output.writeObject(skkmmsuL6R);
		output.writeObject(zanzonmmsuL6R);
		output.writeObject(ksbokaL6R);
		output.writeObject(skkcalckisogkL6R);
		output.writeObject(zanzonritsuL6R);
		output.writeObject(zanzongkL6R);
		output.writeObject(ksftskkruigkL6R);
		output.writeObject(kszkskkruigkL6R);
		output.writeObject(toToftskkgkL6R);
		output.writeObject(toTozkskkgkL6R);
		output.writeObject(toToniniskkgkL6R);
		output.writeObject(toBokaL6R);
		output.writeObject(zogenbokaL6R);
		output.writeObject(zkskkritsuL6R);
		output.writeObject(yukyustymL6R);
		output.writeObject(yukyufkymL6R);
		output.writeObject(shonencalckbnL6R);
		output.writeObject(skkkanryoflgL6R);
		output.writeObject(skkkirikaeyyL6R);
		output.writeObject(zanzonskkstyyL6R);
		output.writeObject(konyucdR);
		output.writeObject(konyunmR);
		output.writeObject(kashicdR);
		output.writeObject(kashinmR);
		output.writeObject(knrbunruicdR);
		output.writeObject(biko1R);
		output.writeObject(biko2R);
		output.writeObject(stkringinoR);
		output.writeObject(stktekiyoR);
		output.writeObject(groupcdR);
		output.writeObject(groupnmR);
		output.writeObject(shinariocdR);
		output.writeObject(shinarionmR);
		output.writeObject(shuyoshisankbnR);
		output.writeObject(niniLd_1cdR);
		output.writeObject(niniLd_1nmR);
		output.writeObject(niniLd_2cdR);
		output.writeObject(niniLd_2nmR);
		output.writeObject(niniLd_3cdR);
		output.writeObject(niniLd_3nmR);
		output.writeObject(niniLd_4cdR);
		output.writeObject(niniLd_4nmR);
		output.writeObject(niniLd_5cdR);
		output.writeObject(niniLd_5nmR);
		output.writeObject(niniLd_6cdR);
		output.writeObject(niniLd_6nmR);
		output.writeObject(niniLd_7cdR);
		output.writeObject(niniLd_7nmR);
		output.writeObject(niniLd_8cdR);
		output.writeObject(niniLd_8nmR);
		output.writeObject(niniLd_9cdR);
		output.writeObject(niniLd_9nmR);
		output.writeObject(niniLd_10cdR);
		output.writeObject(niniLd_10nmR);
		output.writeObject(niniLd_11cdR);
		output.writeObject(niniLd_11nmR);
		output.writeObject(niniLd_12cdR);
		output.writeObject(niniLd_12nmR);
		output.writeObject(niniLd_13cdR);
		output.writeObject(niniLd_13nmR);
		output.writeObject(niniLd_14cdR);
		output.writeObject(niniLd_14nmR);
		output.writeObject(niniLd_15cdR);
		output.writeObject(niniLd_15nmR);
		output.writeObject(niniLd_16cdR);
		output.writeObject(niniLd_16nmR);
		output.writeObject(niniLd_17cdR);
		output.writeObject(niniLd_17nmR);
		output.writeObject(niniLd_18cdR);
		output.writeObject(niniLd_18nmR);
		output.writeObject(niniLd_19cdR);
		output.writeObject(niniLd_19nmR);
		output.writeObject(niniLd_20cdR);
		output.writeObject(niniLd_20nmR);
		output.writeObject(toshinkkyyR);
		output.writeObject(ksgensonruigkkR);
		output.writeObject(togensongkkR);
		output.writeObject(skkcalczanzongkkR);
		output.writeObject(gensonbokakR);
		output.writeObject(gensonmaetainenkR);
		output.writeObject(gensonmaeskkmmsukR);
		output.writeObject(gensonymdkR);
		output.writeObject(genshistkgkkR);
		output.writeObject(kaiteistkgkkR);
		output.writeObject(kaiteitainenkR);
		output.writeObject(kaiteiymdkR);
		output.writeObject(yusenzogenkbnkR);
		output.writeObject(ksgensonruigkzR);
		output.writeObject(togensongkzR);
		output.writeObject(skkcalczanzongkzR);
		output.writeObject(gensonbokazR);
		output.writeObject(gensonmaetainenzR);
		output.writeObject(gensonmaeskkmmsuzR);
		output.writeObject(gensonymdzR);
		output.writeObject(genshistkgkzR);
		output.writeObject(kaiteistkgkzR);
		output.writeObject(kaiteitainenzR);
		output.writeObject(kaiteiymdzR);
		output.writeObject(yusenzogenkbnzR);
		output.writeObject(ksgensonruigkL3R);
		output.writeObject(togensongkL3R);
		output.writeObject(skkcalczanzongkL3R);
		output.writeObject(gensonbokaL3R);
		output.writeObject(gensonmaetainenL3R);
		output.writeObject(gensonmaeskkmmsuL3R);
		output.writeObject(gensonymdL3R);
		output.writeObject(genshistkgkL3R);
		output.writeObject(kaiteistkgkL3R);
		output.writeObject(kaiteitainenL3R);
		output.writeObject(kaiteiymdL3R);
		output.writeObject(yusenzogenkbnL3R);
		output.writeObject(ksgensonruigkL4R);
		output.writeObject(togensongkL4R);
		output.writeObject(skkcalczanzongkL4R);
		output.writeObject(gensonbokaL4R);
		output.writeObject(gensonmaetainenL4R);
		output.writeObject(gensonmaeskkmmsuL4R);
		output.writeObject(gensonymdL4R);
		output.writeObject(genshistkgkL4R);
		output.writeObject(kaiteistkgkL4R);
		output.writeObject(kaiteitainenL4R);
		output.writeObject(kaiteiymdL4R);
		output.writeObject(yusenzogenkbnL4R);
		output.writeObject(ksgensonruigkL5R);
		output.writeObject(togensongkL5R);
		output.writeObject(skkcalczanzongkL5R);
		output.writeObject(gensonbokaL5R);
		output.writeObject(gensonmaetainenL5R);
		output.writeObject(gensonmaeskkmmsuL5R);
		output.writeObject(gensonymdL5R);
		output.writeObject(genshistkgkL5R);
		output.writeObject(kaiteistkgkL5R);
		output.writeObject(kaiteitainenL5R);
		output.writeObject(kaiteiymdL5R);
		output.writeObject(yusenzogenkbnL5R);
		output.writeObject(ksgensonruigkL6R);
		output.writeObject(togensongkL6R);
		output.writeObject(skkcalczanzongkL6R);
		output.writeObject(gensonbokaL6R);
		output.writeObject(gensonmaetainenL6R);
		output.writeObject(gensonmaeskkmmsuL6R);
		output.writeObject(gensonymdL6R);
		output.writeObject(genshistkgkL6R);
		output.writeObject(kaiteistkgkL6R);
		output.writeObject(kaiteitainenL6R);
		output.writeObject(kaiteiymdL6R);
		output.writeObject(yusenzogenkbnL6R);
		output.writeObject(updidR);
		output.writeObject(updymdR);

		output.writeObject(kadoymdRF);
		output.writeObject(jbkymdRF);
		output.writeObject(idoymdRF);

		output.writeObject(mitsumorihkymdkRF);
		output.writeObject(mitsumorihkymdzRF);
		output.writeObject(mitsumorihkymdL3RF);
		output.writeObject(mitsumorihkymdL4RF);
		output.writeObject(mitsumorihkymdL5RF);
		output.writeObject(mitsumorihkymdL6RF);

		output.writeObject(famitsumorihkymdkRF);
		output.writeObject(famitsumorihkymdzRF);
		output.writeObject(famitsumorihkymdL3RF);
		output.writeObject(famitsumorihkymdL4RF);
		output.writeObject(famitsumorihkymdL5RF);
		output.writeObject(famitsumorihkymdL6RF);

		*/

	}

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
	 * @return the famitsumorihkymdk
	 */
	public String getFamitsumorihkymdk() {
		return famitsumorihkymdk;
	}

	/**
	 * @param famitsumorihkymdk the famitsumorihkymdk to set
	 */
	public void setFamitsumorihkymdk(String famitsumorihkymdk) {
		this.famitsumorihkymdk = famitsumorihkymdk;
	}

	/**
	 * @return the mitsumorihkgobokak
	 */
	public Long getMitsumorihkgobokak() {
		return mitsumorihkgobokak;
	}

	/**
	 * @param mitsumorihkgobokak the mitsumorihkgobokak to set
	 */
	public void setMitsumorihkgobokak(Long mitsumorihkgobokak) {
		this.mitsumorihkgobokak = mitsumorihkgobokak;
	}

	/**
	 * @return the mitsumorihkmaetainenk
	 */
	public Long getMitsumorihkmaetainenk() {
		return mitsumorihkmaetainenk;
	}

	/**
	 * @param mitsumorihkmaetainenk the mitsumorihkmaetainenk to set
	 */
	public void setMitsumorihkmaetainenk(Long mitsumorihkmaetainenk) {
		this.mitsumorihkmaetainenk = mitsumorihkmaetainenk;
	}

	/**
	 * @return the mitsumorihkmaeskkmmsuk
	 */
	public Long getMitsumorihkmaeskkmmsuk() {
		return mitsumorihkmaeskkmmsuk;
	}

	/**
	 * @param mitsumorihkmaeskkmmsuk the mitsumorihkmaeskkmmsuk to set
	 */
	public void setMitsumorihkmaeskkmmsuk(Long mitsumorihkmaeskkmmsuk) {
		this.mitsumorihkmaeskkmmsuk = mitsumorihkmaeskkmmsuk;
	}

	/**
	 * @return the mitsumorihkmaeskkritsuk
	 */
	public Long getMitsumorihkmaeskkritsuk() {
		return mitsumorihkmaeskkritsuk;
	}

	/**
	 * @param mitsumorihkmaeskkritsuk the mitsumorihkmaeskkritsuk to set
	 */
	public void setMitsumorihkmaeskkritsuk(Long mitsumorihkmaeskkritsuk) {
		this.mitsumorihkmaeskkritsuk = mitsumorihkmaeskkritsuk;
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
	 * @return the famitsumorihkymdz
	 */
	public String getFamitsumorihkymdz() {
		return famitsumorihkymdz;
	}

	/**
	 * @param famitsumorihkymdz the famitsumorihkymdz to set
	 */
	public void setFamitsumorihkymdz(String famitsumorihkymdz) {
		this.famitsumorihkymdz = famitsumorihkymdz;
	}

	/**
	 * @return the mitsumorihkgobokaz
	 */
	public Long getMitsumorihkgobokaz() {
		return mitsumorihkgobokaz;
	}

	/**
	 * @param mitsumorihkgobokaz the mitsumorihkgobokaz to set
	 */
	public void setMitsumorihkgobokaz(Long mitsumorihkgobokaz) {
		this.mitsumorihkgobokaz = mitsumorihkgobokaz;
	}

	/**
	 * @return the mitsumorihkmaetainenz
	 */
	public Long getMitsumorihkmaetainenz() {
		return mitsumorihkmaetainenz;
	}

	/**
	 * @param mitsumorihkmaetainenz the mitsumorihkmaetainenz to set
	 */
	public void setMitsumorihkmaetainenz(Long mitsumorihkmaetainenz) {
		this.mitsumorihkmaetainenz = mitsumorihkmaetainenz;
	}

	/**
	 * @return the mitsumorihkmaeskkmmsuz
	 */
	public Long getMitsumorihkmaeskkmmsuz() {
		return mitsumorihkmaeskkmmsuz;
	}

	/**
	 * @param mitsumorihkmaeskkmmsuz the mitsumorihkmaeskkmmsuz to set
	 */
	public void setMitsumorihkmaeskkmmsuz(Long mitsumorihkmaeskkmmsuz) {
		this.mitsumorihkmaeskkmmsuz = mitsumorihkmaeskkmmsuz;
	}

	/**
	 * @return the mitsumorihkmaeskkritsuz
	 */
	public Long getMitsumorihkmaeskkritsuz() {
		return mitsumorihkmaeskkritsuz;
	}

	/**
	 * @param mitsumorihkmaeskkritsuz the mitsumorihkmaeskkritsuz to set
	 */
	public void setMitsumorihkmaeskkritsuz(Long mitsumorihkmaeskkritsuz) {
		this.mitsumorihkmaeskkritsuz = mitsumorihkmaeskkritsuz;
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
	 * @return the famitsumorihkymdL3
	 */
	public String getFamitsumorihkymdL3() {
		return famitsumorihkymdL3;
	}

	/**
	 * @param famitsumorihkymdL3 the famitsumorihkymdL3 to set
	 */
	public void setFamitsumorihkymdL3(String famitsumorihkymdL3) {
		this.famitsumorihkymdL3 = famitsumorihkymdL3;
	}

	/**
	 * @return the mitsumorihkgobokaL3
	 */
	public Long getMitsumorihkgobokaL3() {
		return mitsumorihkgobokaL3;
	}

	/**
	 * @param mitsumorihkgobokaL3 the mitsumorihkgobokaL3 to set
	 */
	public void setMitsumorihkgobokaL3(Long mitsumorihkgobokaL3) {
		this.mitsumorihkgobokaL3 = mitsumorihkgobokaL3;
	}

	/**
	 * @return the mitsumorihkmaetainenL3
	 */
	public Long getMitsumorihkmaetainenL3() {
		return mitsumorihkmaetainenL3;
	}

	/**
	 * @param mitsumorihkmaetainenL3 the mitsumorihkmaetainenL3 to set
	 */
	public void setMitsumorihkmaetainenL3(Long mitsumorihkmaetainenL3) {
		this.mitsumorihkmaetainenL3 = mitsumorihkmaetainenL3;
	}

	/**
	 * @return the mitsumorihkmaeskkmmsuL3
	 */
	public Long getMitsumorihkmaeskkmmsuL3() {
		return mitsumorihkmaeskkmmsuL3;
	}

	/**
	 * @param mitsumorihkmaeskkmmsuL3 the mitsumorihkmaeskkmmsuL3 to set
	 */
	public void setMitsumorihkmaeskkmmsuL3(Long mitsumorihkmaeskkmmsuL3) {
		this.mitsumorihkmaeskkmmsuL3 = mitsumorihkmaeskkmmsuL3;
	}

	/**
	 * @return the mitsumorihkmaeskkritsuL3
	 */
	public Long getMitsumorihkmaeskkritsuL3() {
		return mitsumorihkmaeskkritsuL3;
	}

	/**
	 * @param mitsumorihkmaeskkritsuL3 the mitsumorihkmaeskkritsuL3 to set
	 */
	public void setMitsumorihkmaeskkritsuL3(Long mitsumorihkmaeskkritsuL3) {
		this.mitsumorihkmaeskkritsuL3 = mitsumorihkmaeskkritsuL3;
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
	 * @return the famitsumorihkymdL4
	 */
	public String getFamitsumorihkymdL4() {
		return famitsumorihkymdL4;
	}

	/**
	 * @param famitsumorihkymdL4 the famitsumorihkymdL4 to set
	 */
	public void setFamitsumorihkymdL4(String famitsumorihkymdL4) {
		this.famitsumorihkymdL4 = famitsumorihkymdL4;
	}

	/**
	 * @return the mitsumorihkgobokaL4
	 */
	public Long getMitsumorihkgobokaL4() {
		return mitsumorihkgobokaL4;
	}

	/**
	 * @param mitsumorihkgobokaL4 the mitsumorihkgobokaL4 to set
	 */
	public void setMitsumorihkgobokaL4(Long mitsumorihkgobokaL4) {
		this.mitsumorihkgobokaL4 = mitsumorihkgobokaL4;
	}

	/**
	 * @return the mitsumorihkmaetainenL4
	 */
	public Long getMitsumorihkmaetainenL4() {
		return mitsumorihkmaetainenL4;
	}

	/**
	 * @param mitsumorihkmaetainenL4 the mitsumorihkmaetainenL4 to set
	 */
	public void setMitsumorihkmaetainenL4(Long mitsumorihkmaetainenL4) {
		this.mitsumorihkmaetainenL4 = mitsumorihkmaetainenL4;
	}

	/**
	 * @return the mitsumorihkmaeskkmmsuL4
	 */
	public Long getMitsumorihkmaeskkmmsuL4() {
		return mitsumorihkmaeskkmmsuL4;
	}

	/**
	 * @param mitsumorihkmaeskkmmsuL4 the mitsumorihkmaeskkmmsuL4 to set
	 */
	public void setMitsumorihkmaeskkmmsuL4(Long mitsumorihkmaeskkmmsuL4) {
		this.mitsumorihkmaeskkmmsuL4 = mitsumorihkmaeskkmmsuL4;
	}

	/**
	 * @return the mitsumorihkmaeskkritsuL4
	 */
	public Long getMitsumorihkmaeskkritsuL4() {
		return mitsumorihkmaeskkritsuL4;
	}

	/**
	 * @param mitsumorihkmaeskkritsuL4 the mitsumorihkmaeskkritsuL4 to set
	 */
	public void setMitsumorihkmaeskkritsuL4(Long mitsumorihkmaeskkritsuL4) {
		this.mitsumorihkmaeskkritsuL4 = mitsumorihkmaeskkritsuL4;
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
	 * @return the famitsumorihkymdL5
	 */
	public String getFamitsumorihkymdL5() {
		return famitsumorihkymdL5;
	}

	/**
	 * @param famitsumorihkymdL5 the famitsumorihkymdL5 to set
	 */
	public void setFamitsumorihkymdL5(String famitsumorihkymdL5) {
		this.famitsumorihkymdL5 = famitsumorihkymdL5;
	}

	/**
	 * @return the mitsumorihkgobokaL5
	 */
	public Long getMitsumorihkgobokaL5() {
		return mitsumorihkgobokaL5;
	}

	/**
	 * @param mitsumorihkgobokaL5 the mitsumorihkgobokaL5 to set
	 */
	public void setMitsumorihkgobokaL5(Long mitsumorihkgobokaL5) {
		this.mitsumorihkgobokaL5 = mitsumorihkgobokaL5;
	}

	/**
	 * @return the mitsumorihkmaetainenL5
	 */
	public Long getMitsumorihkmaetainenL5() {
		return mitsumorihkmaetainenL5;
	}

	/**
	 * @param mitsumorihkmaetainenL5 the mitsumorihkmaetainenL5 to set
	 */
	public void setMitsumorihkmaetainenL5(Long mitsumorihkmaetainenL5) {
		this.mitsumorihkmaetainenL5 = mitsumorihkmaetainenL5;
	}

	/**
	 * @return the mitsumorihkmaeskkmmsuL5
	 */
	public Long getMitsumorihkmaeskkmmsuL5() {
		return mitsumorihkmaeskkmmsuL5;
	}

	/**
	 * @param mitsumorihkmaeskkmmsuL5 the mitsumorihkmaeskkmmsuL5 to set
	 */
	public void setMitsumorihkmaeskkmmsuL5(Long mitsumorihkmaeskkmmsuL5) {
		this.mitsumorihkmaeskkmmsuL5 = mitsumorihkmaeskkmmsuL5;
	}

	/**
	 * @return the mitsumorihkmaeskkritsuL5
	 */
	public Long getMitsumorihkmaeskkritsuL5() {
		return mitsumorihkmaeskkritsuL5;
	}

	/**
	 * @param mitsumorihkmaeskkritsuL5 the mitsumorihkmaeskkritsuL5 to set
	 */
	public void setMitsumorihkmaeskkritsuL5(Long mitsumorihkmaeskkritsuL5) {
		this.mitsumorihkmaeskkritsuL5 = mitsumorihkmaeskkritsuL5;
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
	 * @return the famitsumorihkymdL6
	 */
	public String getFamitsumorihkymdL6() {
		return famitsumorihkymdL6;
	}

	/**
	 * @param famitsumorihkymdL6 the famitsumorihkymdL6 to set
	 */
	public void setFamitsumorihkymdL6(String famitsumorihkymdL6) {
		this.famitsumorihkymdL6 = famitsumorihkymdL6;
	}

	/**
	 * @return the mitsumorihkgobokaL6
	 */
	public Long getMitsumorihkgobokaL6() {
		return mitsumorihkgobokaL6;
	}

	/**
	 * @param mitsumorihkgobokaL6 the mitsumorihkgobokaL6 to set
	 */
	public void setMitsumorihkgobokaL6(Long mitsumorihkgobokaL6) {
		this.mitsumorihkgobokaL6 = mitsumorihkgobokaL6;
	}

	/**
	 * @return the mitsumorihkmaetainenL6
	 */
	public Long getMitsumorihkmaetainenL6() {
		return mitsumorihkmaetainenL6;
	}

	/**
	 * @param mitsumorihkmaetainenL6 the mitsumorihkmaetainenL6 to set
	 */
	public void setMitsumorihkmaetainenL6(Long mitsumorihkmaetainenL6) {
		this.mitsumorihkmaetainenL6 = mitsumorihkmaetainenL6;
	}

	/**
	 * @return the mitsumorihkmaeskkmmsuL6
	 */
	public Long getMitsumorihkmaeskkmmsuL6() {
		return mitsumorihkmaeskkmmsuL6;
	}

	/**
	 * @param mitsumorihkmaeskkmmsuL6 the mitsumorihkmaeskkmmsuL6 to set
	 */
	public void setMitsumorihkmaeskkmmsuL6(Long mitsumorihkmaeskkmmsuL6) {
		this.mitsumorihkmaeskkmmsuL6 = mitsumorihkmaeskkmmsuL6;
	}

	/**
	 * @return the mitsumorihkmaeskkritsuL6
	 */
	public Long getMitsumorihkmaeskkritsuL6() {
		return mitsumorihkmaeskkritsuL6;
	}

	/**
	 * @param mitsumorihkmaeskkritsuL6 the mitsumorihkmaeskkritsuL6 to set
	 */
	public void setMitsumorihkmaeskkritsuL6(Long mitsumorihkmaeskkritsuL6) {
		this.mitsumorihkmaeskkritsuL6 = mitsumorihkmaeskkritsuL6;
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
	 * @return the assetList
	 */
	public List<AssetSR> getAssetList() {
		return assetList;
	}

	/**
	 * @param assetList the assetList to set
	 */
	public void setAssetList(List<AssetSR> assetList) {
		this.assetList = assetList;
	}

	/**
	 * @return the licenseList
	 */
	public List<LicenseSR> getLicenseList() {
		return licenseList;
	}

	/**
	 * @param licenseList the licenseList to set
	 */
	public void setLicenseList(List<LicenseSR> licenseList) {
		this.licenseList = licenseList;
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
