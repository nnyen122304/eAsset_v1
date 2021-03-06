/*===================================================================
 * ファイル名 : PpfsLldSRRep.java
 * 概要説明   : リース・レンタル管理帳票用検索結果
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-11-08 1.0  リヨン 崔     新規
 *=================================================================== */

package jp.co.ctcg.easset.dto.lld;

import java.util.Date;
import lombok.ToString;

@ToString(callSuper = true)
public class PpfsLldSRRep extends PpfsLldFldSR {
	private static final long serialVersionUID = 1L;

	// 共通
	private String periodName; // 会計年月
	private String reportKey1; // レポートキー1
	private String reportKey2; // レポートキー2
	private String reportKey3; // レポートキー3


	//////////////////////  請求明細、賃借料・償却費・社内金利他明細　共通
	private Long amount; // 金額

	//////////////////////  請求明細
	private Long taxAmount; // 税額
	private String seikyuno; // 請求管理№
	private Integer rentalsu; // 請求回数
	private String rental1stymd; // レンタル初回開始日
	private String rental1stymdF; // レンタル初回開始日(フォーマット)
	private String rentalstymd; // レンタル開始日
	private String rentalstymdF; // レンタル開始日(フォーマット)
	private String rentaledymd; // レンタル終了日
	private String rentaledymdF; // レンタル終了日(フォーマット)
	private String rentalkikanm; // レンタル期間（月）
	private String rentalkikand; // レンタル期間（日）
	private String rentalkikanmall; // 総レンタル期間（月）
	private String rentalkikandall; // 総レンタル期間（日）
	private String seikyust; // 請求期間（自）
	private String seikyustF; // 請求期間（自）(フォーマット)
	private String seikyued; // 請求期間（至）
	private String seikyuedF; // 請求期間（至）(フォーマット)
	private String name; // 物件名
	private String katacode; // 型コード
	private String kataban; // 型番
	private String oir; // ＯＩＲ
	private String serialno; // シリアル№
	private Integer suryo; // 数量
	private String seikyucd; // 請求先コード
	private String seikyunm; // 請求先会社名
	private String seikyujigyonm; // 請求先事業所名
	private String seikyutantocde; // 請求先担当者コード
	private String seikyubusyonm; // 請求先部署名
	private String seikyutantonm; // 請求先担当者名
	private String souhukaishanm; // 送付先会社名
	private String souhujigyonm; // 送付先事業所名
	private String souhubusyonm; // 送付先部署名
	private String souhubutantonm; // 送付先担当者名
	private String rentecbasecd; // ﾚﾝﾃｯｸﾍﾞｰｽｺｰﾄﾞ
	private String rentecplacdnm; // ﾚﾝﾃｯｸ 拠点名
	private String johokikikanrino; // 情報機器管理番号
	private String supliercd; // 仕入先コード
	private String supliersite; // 仕入先サイト
	private String seikyubgroup; // 請求グループ
	private String skkpj; // 償却PJ
	private String tyutoflg; // 中途解約フラグ

	//////////////////////  賃借料・償却費・社内金利他明細
	private String accountName; // 勘定科目
	private Double distRate; // 配賦率
	private Long amountNoDist; // 配賦前金額
	private String costSectionCode; // 経費負担部課コード
	private String costSectionName; // 経費負担部課名
	private String distCode; // 配賦コード
	private String distName; // 配賦名

	//////////////////////  賃借料・償却費・社内金利スケジュール
	private Long amount_l; // リース料
	private Long amount_s; // 資産計上額
	private Long amount1_k; // 期首未経過金額/簿価-1年目
	private Long amount1_1; // 金額1-1年目
	private Long amount1_2; // 金額2-1年目
	private Long amount1_3; // 金額3-1年目
	private Long amount1_4; // 金額4-1年目
	private Long amount1_5; // 金額5-1年目
	private Long amount1_6; // 金額6-1年目
	private Long amount1_7; // 金額7-1年目
	private Long amount1_8; // 金額8-1年目
	private Long amount1_9; // 金額9-1年目
	private Long amount1_10; // 金額10-1年目
	private Long amount1_11; // 金額11-1年目
	private Long amount1_12; // 金額12-1年目
	private Long amount1_n; // 任意償却額-1年目
	private Long amount1_z; // 増加償却額-1年目
	private Long amount1_t; // 特別償却額-1年目
	private Long amount1_g; // 減損償却額-1年目
	private Long amount2_k; // 期首未経過金額/簿価-2年目
	private Long amount2_1; // 金額1-2年目
	private Long amount2_2; // 金額2-2年目
	private Long amount2_3; // 金額3-2年目
	private Long amount2_4; // 金額4-2年目
	private Long amount2_5; // 金額5-2年目
	private Long amount2_6; // 金額6-2年目
	private Long amount2_7; // 金額7-2年目
	private Long amount2_8; // 金額8-2年目
	private Long amount2_9; // 金額9-2年目
	private Long amount2_10; // 金額10-2年目
	private Long amount2_11; // 金額11-2年目
	private Long amount2_12; // 金額12-2年目
	private Long amount2_n; // 任意償却額-2年目
	private Long amount2_z; // 増加償却額-2年目
	private Long amount2_t; // 特別償却額-2年目
	private Long amount2_g; // 減損償却額-2年目
	private Long amount3_k; // 期首未経過金額/簿価-3年目
	private Long amount3_1; // 金額1-3年目
	private Long amount3_2; // 金額2-3年目
	private Long amount3_3; // 金額3-3年目
	private Long amount3_4; // 金額4-3年目
	private Long amount3_5; // 金額5-3年目
	private Long amount3_6; // 金額6-3年目
	private Long amount3_7; // 金額7-3年目
	private Long amount3_8; // 金額8-3年目
	private Long amount3_9; // 金額9-3年目
	private Long amount3_10; // 金額10-3年目
	private Long amount3_11; // 金額11-3年目
	private Long amount3_12; // 金額12-3年目
	private Long amount3_n; // 任意償却額-3年目
	private Long amount3_z; // 増加償却額-3年目
	private Long amount3_t; // 特別償却額-3年目
	private Long amount3_g; // 減損償却額-3年目
	private Long amount4_k; // 期首未経過金額/簿価-4年目
	private Long amount4_1; // 金額1-4年目
	private Long amount4_2; // 金額2-4年目
	private Long amount4_3; // 金額3-4年目
	private Long amount4_4; // 金額4-4年目
	private Long amount4_5; // 金額5-4年目
	private Long amount4_6; // 金額6-4年目
	private Long amount4_7; // 金額7-4年目
	private Long amount4_8; // 金額8-4年目
	private Long amount4_9; // 金額9-4年目
	private Long amount4_10; // 金額10-4年目
	private Long amount4_11; // 金額11-4年目
	private Long amount4_12; // 金額12-4年目
	private Long amount4_n; // 任意償却額-4年目
	private Long amount4_z; // 増加償却額-4年目
	private Long amount4_t; // 特別償却額-4年目
	private Long amount4_g; // 減損償却額-4年目
	private Long amount5_k; // 期首未経過金額/簿価-5年目
	private Long amount5_1; // 金額1-5年目
	private Long amount5_2; // 金額2-5年目
	private Long amount5_3; // 金額3-5年目
	private Long amount5_4; // 金額4-5年目
	private Long amount5_5; // 金額5-5年目
	private Long amount5_6; // 金額6-5年目
	private Long amount5_7; // 金額7-5年目
	private Long amount5_8; // 金額8-5年目
	private Long amount5_9; // 金額9-5年目
	private Long amount5_10; // 金額10-5年目
	private Long amount5_11; // 金額11-5年目
	private Long amount5_12; // 金額12-5年目
	private Long amount5_n; // 任意償却額-5年目
	private Long amount5_z; // 増加償却額-5年目
	private Long amount5_t; // 特別償却額-5年目
	private Long amount5_g; // 減損償却額-5年目
	private Long amount6_k; // 期首未経過金額/簿価-6年目
	private Long amount6_1; // 金額1-6年目
	private Long amount6_2; // 金額2-6年目
	private Long amount6_3; // 金額3-6年目
	private Long amount6_4; // 金額4-6年目
	private Long amount6_5; // 金額5-6年目
	private Long amount6_6; // 金額6-6年目
	private Long amount6_7; // 金額7-6年目
	private Long amount6_8; // 金額8-6年目
	private Long amount6_9; // 金額9-6年目
	private Long amount6_10; // 金額10-6年目
	private Long amount6_11; // 金額11-6年目
	private Long amount6_12; // 金額12-6年目
	private Long amount6_n; // 任意償却額-6年目
	private Long amount6_z; // 増加償却額-6年目
	private Long amount6_t; // 特別償却額-6年目
	private Long amount6_g; // 減損償却額-6年目
	private Long amount7_k; // 期首未経過金額/簿価-7年目
	private Long amount7_1; // 金額1-7年目
	private Long amount7_2; // 金額2-7年目
	private Long amount7_3; // 金額3-7年目
	private Long amount7_4; // 金額4-7年目
	private Long amount7_5; // 金額5-7年目
	private Long amount7_6; // 金額6-7年目
	private Long amount7_7; // 金額7-7年目
	private Long amount7_8; // 金額8-7年目
	private Long amount7_9; // 金額9-7年目
	private Long amount7_10; // 金額10-7年目
	private Long amount7_11; // 金額11-7年目
	private Long amount7_12; // 金額12-7年目
	private Long amount7_n; // 任意償却額-7年目
	private Long amount7_z; // 増加償却額-7年目
	private Long amount7_t; // 特別償却額-7年目
	private Long amount7_g; // 減損償却額-7年目
	private Long amount8_k; // 期首未経過金額/簿価-8年目
	private Long amount8_1; // 金額1-8年目
	private Long amount8_2; // 金額2-8年目
	private Long amount8_3; // 金額3-8年目
	private Long amount8_4; // 金額4-8年目
	private Long amount8_5; // 金額5-8年目
	private Long amount8_6; // 金額6-8年目
	private Long amount8_7; // 金額7-8年目
	private Long amount8_8; // 金額8-8年目
	private Long amount8_9; // 金額9-8年目
	private Long amount8_10; // 金額10-8年目
	private Long amount8_11; // 金額11-8年目
	private Long amount8_12; // 金額12-8年目
	private Long amount8_n; // 任意償却額-8年目
	private Long amount8_z; // 増加償却額-8年目
	private Long amount8_t; // 特別償却額-8年目
	private Long amount8_g; // 減損償却額-8年目
	private Long amount9_k; // 期首未経過金額/簿価-9年目
	private Long amount9_1; // 金額1-9年目
	private Long amount9_2; // 金額2-9年目
	private Long amount9_3; // 金額3-9年目
	private Long amount9_4; // 金額4-9年目
	private Long amount9_5; // 金額5-9年目
	private Long amount9_6; // 金額6-9年目
	private Long amount9_7; // 金額7-9年目
	private Long amount9_8; // 金額8-9年目
	private Long amount9_9; // 金額9-9年目
	private Long amount9_10; // 金額10-9年目
	private Long amount9_11; // 金額11-9年目
	private Long amount9_12; // 金額12-9年目
	private Long amount9_n; // 任意償却額-9年目
	private Long amount9_z; // 増加償却額-9年目
	private Long amount9_t; // 特別償却額-9年目
	private Long amount9_g; // 減損償却額-9年目
	private Long amount10_k; // 期首未経過金額/簿価-10年目
	private Long amount10_1; // 金額1-10年目
	private Long amount10_2; // 金額2-10年目
	private Long amount10_3; // 金額3-10年目
	private Long amount10_4; // 金額4-10年目
	private Long amount10_5; // 金額5-10年目
	private Long amount10_6; // 金額6-10年目
	private Long amount10_7; // 金額7-10年目
	private Long amount10_8; // 金額8-10年目
	private Long amount10_9; // 金額9-10年目
	private Long amount10_10; // 金額10-10年目
	private Long amount10_11; // 金額11-10年目
	private Long amount10_12; // 金額12-10年目
	private Long amount10_n; // 任意償却額-10年目
	private Long amount10_z; // 増加償却額-10年目
	private Long amount10_t; // 特別償却額-10年目
	private Long amount10_g; // 減損償却額-10年目

	//////////////////////  契約/物件-現物紐付リスト(有形)
	private String astNameGen; // 現物情報：資産名
	private String holCompanyNameGen; // 現物情報：保有会社
	private String holSectionNameGen; // 現物情報：保有部署
	private String holStaffCodeGen; // 現物情報：資産管理担当者社員番号
	private String holStaffNameGen; // 現物情報：資産管理担当者名
	private String useStaffCodeGen; // 現物情報：使用者社員番号
	private String useStaffNameGen; // 現物情報：使用者名
	private String holOfficeNameGen; // 現物情報：設置場所
	private Integer holOfficeFloorGen; // 現物情報：設置場所階数
	private Integer holQuantityGen; // 現物情報：数量
	private Date deleteDateGen; // 現物情報：抹消日
	public String getPeriodName() {
		return periodName;
	}
	public void setPeriodName(String periodName) {
		this.periodName = periodName;
	}
	public String getReportKey1() {
		return reportKey1;
	}
	public void setReportKey1(String reportKey1) {
		this.reportKey1 = reportKey1;
	}
	public String getReportKey2() {
		return reportKey2;
	}
	public void setReportKey2(String reportKey2) {
		this.reportKey2 = reportKey2;
	}
	public String getReportKey3() {
		return reportKey3;
	}
	public void setReportKey3(String reportKey3) {
		this.reportKey3 = reportKey3;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public Long getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(Long taxAmount) {
		this.taxAmount = taxAmount;
	}
	public String getSeikyuno() {
		return seikyuno;
	}
	public void setSeikyuno(String seikyuno) {
		this.seikyuno = seikyuno;
	}
	public Integer getRentalsu() {
		return rentalsu;
	}
	public void setRentalsu(Integer rentalsu) {
		this.rentalsu = rentalsu;
	}
	public String getRental1stymd() {
		return rental1stymd;
	}
	public void setRental1stymd(String rental1stymd) {
		this.rental1stymd = rental1stymd;
	}
	public String getRentalstymd() {
		return rentalstymd;
	}
	public void setRentalstymd(String rentalstymd) {
		this.rentalstymd = rentalstymd;
	}
	public String getRentaledymd() {
		return rentaledymd;
	}
	public void setRentaledymd(String rentaledymd) {
		this.rentaledymd = rentaledymd;
	}
	public String getRentalkikanm() {
		return rentalkikanm;
	}
	public void setRentalkikanm(String rentalkikanm) {
		this.rentalkikanm = rentalkikanm;
	}
	public String getRentalkikand() {
		return rentalkikand;
	}
	public void setRentalkikand(String rentalkikand) {
		this.rentalkikand = rentalkikand;
	}
	public String getRentalkikanmall() {
		return rentalkikanmall;
	}
	public void setRentalkikanmall(String rentalkikanmall) {
		this.rentalkikanmall = rentalkikanmall;
	}
	public String getRentalkikandall() {
		return rentalkikandall;
	}
	public void setRentalkikandall(String rentalkikandall) {
		this.rentalkikandall = rentalkikandall;
	}
	public String getSeikyust() {
		return seikyust;
	}
	public void setSeikyust(String seikyust) {
		this.seikyust = seikyust;
	}
	public String getSeikyued() {
		return seikyued;
	}
	public void setSeikyued(String seikyued) {
		this.seikyued = seikyued;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getKatacode() {
		return katacode;
	}
	public void setKatacode(String katacode) {
		this.katacode = katacode;
	}
	public String getKataban() {
		return kataban;
	}
	public void setKataban(String kataban) {
		this.kataban = kataban;
	}
	public String getOir() {
		return oir;
	}
	public void setOir(String oir) {
		this.oir = oir;
	}
	public String getSerialno() {
		return serialno;
	}
	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}
	public Integer getSuryo() {
		return suryo;
	}
	public void setSuryo(Integer suryo) {
		this.suryo = suryo;
	}
	public String getSeikyucd() {
		return seikyucd;
	}
	public void setSeikyucd(String seikyucd) {
		this.seikyucd = seikyucd;
	}
	public String getSeikyunm() {
		return seikyunm;
	}
	public void setSeikyunm(String seikyunm) {
		this.seikyunm = seikyunm;
	}
	public String getSeikyujigyonm() {
		return seikyujigyonm;
	}
	public void setSeikyujigyonm(String seikyujigyonm) {
		this.seikyujigyonm = seikyujigyonm;
	}
	public String getSeikyutantocde() {
		return seikyutantocde;
	}
	public void setSeikyutantocde(String seikyutantocde) {
		this.seikyutantocde = seikyutantocde;
	}
	public String getSeikyubusyonm() {
		return seikyubusyonm;
	}
	public void setSeikyubusyonm(String seikyubusyonm) {
		this.seikyubusyonm = seikyubusyonm;
	}
	public String getSeikyutantonm() {
		return seikyutantonm;
	}
	public void setSeikyutantonm(String seikyutantonm) {
		this.seikyutantonm = seikyutantonm;
	}
	public String getSouhukaishanm() {
		return souhukaishanm;
	}
	public void setSouhukaishanm(String souhukaishanm) {
		this.souhukaishanm = souhukaishanm;
	}
	public String getSouhujigyonm() {
		return souhujigyonm;
	}
	public void setSouhujigyonm(String souhujigyonm) {
		this.souhujigyonm = souhujigyonm;
	}
	public String getSouhubusyonm() {
		return souhubusyonm;
	}
	public void setSouhubusyonm(String souhubusyonm) {
		this.souhubusyonm = souhubusyonm;
	}
	public String getSouhubutantonm() {
		return souhubutantonm;
	}
	public void setSouhubutantonm(String souhubutantonm) {
		this.souhubutantonm = souhubutantonm;
	}
	public String getRentecbasecd() {
		return rentecbasecd;
	}
	public void setRentecbasecd(String rentecbasecd) {
		this.rentecbasecd = rentecbasecd;
	}
	public String getRentecplacdnm() {
		return rentecplacdnm;
	}
	public void setRentecplacdnm(String rentecplacdnm) {
		this.rentecplacdnm = rentecplacdnm;
	}
	public String getJohokikikanrino() {
		return johokikikanrino;
	}
	public void setJohokikikanrino(String johokikikanrino) {
		this.johokikikanrino = johokikikanrino;
	}
	public String getSupliercd() {
		return supliercd;
	}
	public void setSupliercd(String supliercd) {
		this.supliercd = supliercd;
	}
	public String getSupliersite() {
		return supliersite;
	}
	public void setSupliersite(String supliersite) {
		this.supliersite = supliersite;
	}
	public String getSeikyubgroup() {
		return seikyubgroup;
	}
	public void setSeikyubgroup(String seikyubgroup) {
		this.seikyubgroup = seikyubgroup;
	}
	public String getSkkpj() {
		return skkpj;
	}
	public void setSkkpj(String skkpj) {
		this.skkpj = skkpj;
	}
	public String getTyutoflg() {
		return tyutoflg;
	}
	public void setTyutoflg(String tyutoflg) {
		this.tyutoflg = tyutoflg;
	}
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public Double getDistRate() {
		return distRate;
	}
	public void setDistRate(Double distRate) {
		this.distRate = distRate;
	}
	public Long getAmountNoDist() {
		return amountNoDist;
	}
	public void setAmountNoDist(Long amountNoDist) {
		this.amountNoDist = amountNoDist;
	}
	public String getCostSectionCode() {
		return costSectionCode;
	}
	public void setCostSectionCode(String costSectionCode) {
		this.costSectionCode = costSectionCode;
	}
	public String getCostSectionName() {
		return costSectionName;
	}
	public void setCostSectionName(String costSectionName) {
		this.costSectionName = costSectionName;
	}
	public String getDistCode() {
		return distCode;
	}
	public void setDistCode(String distCode) {
		this.distCode = distCode;
	}
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	public Long getAmount_l() {
		return amount_l;
	}
	public void setAmount_l(Long amount_l) {
		this.amount_l = amount_l;
	}
	public Long getAmount_s() {
		return amount_s;
	}
	public void setAmount_s(Long amount_s) {
		this.amount_s = amount_s;
	}
	public Long getAmount1_k() {
		return amount1_k;
	}
	public void setAmount1_k(Long amount1_k) {
		this.amount1_k = amount1_k;
	}
	public Long getAmount1_1() {
		return amount1_1;
	}
	public void setAmount1_1(Long amount1_1) {
		this.amount1_1 = amount1_1;
	}
	public Long getAmount1_2() {
		return amount1_2;
	}
	public void setAmount1_2(Long amount1_2) {
		this.amount1_2 = amount1_2;
	}
	public Long getAmount1_3() {
		return amount1_3;
	}
	public void setAmount1_3(Long amount1_3) {
		this.amount1_3 = amount1_3;
	}
	public Long getAmount1_4() {
		return amount1_4;
	}
	public void setAmount1_4(Long amount1_4) {
		this.amount1_4 = amount1_4;
	}
	public Long getAmount1_5() {
		return amount1_5;
	}
	public void setAmount1_5(Long amount1_5) {
		this.amount1_5 = amount1_5;
	}
	public Long getAmount1_6() {
		return amount1_6;
	}
	public void setAmount1_6(Long amount1_6) {
		this.amount1_6 = amount1_6;
	}
	public Long getAmount1_7() {
		return amount1_7;
	}
	public void setAmount1_7(Long amount1_7) {
		this.amount1_7 = amount1_7;
	}
	public Long getAmount1_8() {
		return amount1_8;
	}
	public void setAmount1_8(Long amount1_8) {
		this.amount1_8 = amount1_8;
	}
	public Long getAmount1_9() {
		return amount1_9;
	}
	public void setAmount1_9(Long amount1_9) {
		this.amount1_9 = amount1_9;
	}
	public Long getAmount1_10() {
		return amount1_10;
	}
	public void setAmount1_10(Long amount1_10) {
		this.amount1_10 = amount1_10;
	}
	public Long getAmount1_11() {
		return amount1_11;
	}
	public void setAmount1_11(Long amount1_11) {
		this.amount1_11 = amount1_11;
	}
	public Long getAmount1_12() {
		return amount1_12;
	}
	public void setAmount1_12(Long amount1_12) {
		this.amount1_12 = amount1_12;
	}
	public Long getAmount2_k() {
		return amount2_k;
	}
	public void setAmount2_k(Long amount2_k) {
		this.amount2_k = amount2_k;
	}
	public Long getAmount2_1() {
		return amount2_1;
	}
	public void setAmount2_1(Long amount2_1) {
		this.amount2_1 = amount2_1;
	}
	public Long getAmount2_2() {
		return amount2_2;
	}
	public void setAmount2_2(Long amount2_2) {
		this.amount2_2 = amount2_2;
	}
	public Long getAmount2_3() {
		return amount2_3;
	}
	public void setAmount2_3(Long amount2_3) {
		this.amount2_3 = amount2_3;
	}
	public Long getAmount2_4() {
		return amount2_4;
	}
	public void setAmount2_4(Long amount2_4) {
		this.amount2_4 = amount2_4;
	}
	public Long getAmount2_5() {
		return amount2_5;
	}
	public void setAmount2_5(Long amount2_5) {
		this.amount2_5 = amount2_5;
	}
	public Long getAmount2_6() {
		return amount2_6;
	}
	public void setAmount2_6(Long amount2_6) {
		this.amount2_6 = amount2_6;
	}
	public Long getAmount2_7() {
		return amount2_7;
	}
	public void setAmount2_7(Long amount2_7) {
		this.amount2_7 = amount2_7;
	}
	public Long getAmount2_8() {
		return amount2_8;
	}
	public void setAmount2_8(Long amount2_8) {
		this.amount2_8 = amount2_8;
	}
	public Long getAmount2_9() {
		return amount2_9;
	}
	public void setAmount2_9(Long amount2_9) {
		this.amount2_9 = amount2_9;
	}
	public Long getAmount2_10() {
		return amount2_10;
	}
	public void setAmount2_10(Long amount2_10) {
		this.amount2_10 = amount2_10;
	}
	public Long getAmount2_11() {
		return amount2_11;
	}
	public void setAmount2_11(Long amount2_11) {
		this.amount2_11 = amount2_11;
	}
	public Long getAmount2_12() {
		return amount2_12;
	}
	public void setAmount2_12(Long amount2_12) {
		this.amount2_12 = amount2_12;
	}
	public Long getAmount3_k() {
		return amount3_k;
	}
	public void setAmount3_k(Long amount3_k) {
		this.amount3_k = amount3_k;
	}
	public Long getAmount3_1() {
		return amount3_1;
	}
	public void setAmount3_1(Long amount3_1) {
		this.amount3_1 = amount3_1;
	}
	public Long getAmount3_2() {
		return amount3_2;
	}
	public void setAmount3_2(Long amount3_2) {
		this.amount3_2 = amount3_2;
	}
	public Long getAmount3_3() {
		return amount3_3;
	}
	public void setAmount3_3(Long amount3_3) {
		this.amount3_3 = amount3_3;
	}
	public Long getAmount3_4() {
		return amount3_4;
	}
	public void setAmount3_4(Long amount3_4) {
		this.amount3_4 = amount3_4;
	}
	public Long getAmount3_5() {
		return amount3_5;
	}
	public void setAmount3_5(Long amount3_5) {
		this.amount3_5 = amount3_5;
	}
	public Long getAmount3_6() {
		return amount3_6;
	}
	public void setAmount3_6(Long amount3_6) {
		this.amount3_6 = amount3_6;
	}
	public Long getAmount3_7() {
		return amount3_7;
	}
	public void setAmount3_7(Long amount3_7) {
		this.amount3_7 = amount3_7;
	}
	public Long getAmount3_8() {
		return amount3_8;
	}
	public void setAmount3_8(Long amount3_8) {
		this.amount3_8 = amount3_8;
	}
	public Long getAmount3_9() {
		return amount3_9;
	}
	public void setAmount3_9(Long amount3_9) {
		this.amount3_9 = amount3_9;
	}
	public Long getAmount3_10() {
		return amount3_10;
	}
	public void setAmount3_10(Long amount3_10) {
		this.amount3_10 = amount3_10;
	}
	public Long getAmount3_11() {
		return amount3_11;
	}
	public void setAmount3_11(Long amount3_11) {
		this.amount3_11 = amount3_11;
	}
	public Long getAmount3_12() {
		return amount3_12;
	}
	public void setAmount3_12(Long amount3_12) {
		this.amount3_12 = amount3_12;
	}
	public Long getAmount4_k() {
		return amount4_k;
	}
	public void setAmount4_k(Long amount4_k) {
		this.amount4_k = amount4_k;
	}
	public Long getAmount4_1() {
		return amount4_1;
	}
	public void setAmount4_1(Long amount4_1) {
		this.amount4_1 = amount4_1;
	}
	public Long getAmount4_2() {
		return amount4_2;
	}
	public void setAmount4_2(Long amount4_2) {
		this.amount4_2 = amount4_2;
	}
	public Long getAmount4_3() {
		return amount4_3;
	}
	public void setAmount4_3(Long amount4_3) {
		this.amount4_3 = amount4_3;
	}
	public Long getAmount4_4() {
		return amount4_4;
	}
	public void setAmount4_4(Long amount4_4) {
		this.amount4_4 = amount4_4;
	}
	public Long getAmount4_5() {
		return amount4_5;
	}
	public void setAmount4_5(Long amount4_5) {
		this.amount4_5 = amount4_5;
	}
	public Long getAmount4_6() {
		return amount4_6;
	}
	public void setAmount4_6(Long amount4_6) {
		this.amount4_6 = amount4_6;
	}
	public Long getAmount4_7() {
		return amount4_7;
	}
	public void setAmount4_7(Long amount4_7) {
		this.amount4_7 = amount4_7;
	}
	public Long getAmount4_8() {
		return amount4_8;
	}
	public void setAmount4_8(Long amount4_8) {
		this.amount4_8 = amount4_8;
	}
	public Long getAmount4_9() {
		return amount4_9;
	}
	public void setAmount4_9(Long amount4_9) {
		this.amount4_9 = amount4_9;
	}
	public Long getAmount4_10() {
		return amount4_10;
	}
	public void setAmount4_10(Long amount4_10) {
		this.amount4_10 = amount4_10;
	}
	public Long getAmount4_11() {
		return amount4_11;
	}
	public void setAmount4_11(Long amount4_11) {
		this.amount4_11 = amount4_11;
	}
	public Long getAmount4_12() {
		return amount4_12;
	}
	public void setAmount4_12(Long amount4_12) {
		this.amount4_12 = amount4_12;
	}
	public Long getAmount5_k() {
		return amount5_k;
	}
	public void setAmount5_k(Long amount5_k) {
		this.amount5_k = amount5_k;
	}
	public Long getAmount5_1() {
		return amount5_1;
	}
	public void setAmount5_1(Long amount5_1) {
		this.amount5_1 = amount5_1;
	}
	public Long getAmount5_2() {
		return amount5_2;
	}
	public void setAmount5_2(Long amount5_2) {
		this.amount5_2 = amount5_2;
	}
	public Long getAmount5_3() {
		return amount5_3;
	}
	public void setAmount5_3(Long amount5_3) {
		this.amount5_3 = amount5_3;
	}
	public Long getAmount5_4() {
		return amount5_4;
	}
	public void setAmount5_4(Long amount5_4) {
		this.amount5_4 = amount5_4;
	}
	public Long getAmount5_5() {
		return amount5_5;
	}
	public void setAmount5_5(Long amount5_5) {
		this.amount5_5 = amount5_5;
	}
	public Long getAmount5_6() {
		return amount5_6;
	}
	public void setAmount5_6(Long amount5_6) {
		this.amount5_6 = amount5_6;
	}
	public Long getAmount5_7() {
		return amount5_7;
	}
	public void setAmount5_7(Long amount5_7) {
		this.amount5_7 = amount5_7;
	}
	public Long getAmount5_8() {
		return amount5_8;
	}
	public void setAmount5_8(Long amount5_8) {
		this.amount5_8 = amount5_8;
	}
	public Long getAmount5_9() {
		return amount5_9;
	}
	public void setAmount5_9(Long amount5_9) {
		this.amount5_9 = amount5_9;
	}
	public Long getAmount5_10() {
		return amount5_10;
	}
	public void setAmount5_10(Long amount5_10) {
		this.amount5_10 = amount5_10;
	}
	public Long getAmount5_11() {
		return amount5_11;
	}
	public void setAmount5_11(Long amount5_11) {
		this.amount5_11 = amount5_11;
	}
	public Long getAmount5_12() {
		return amount5_12;
	}
	public void setAmount5_12(Long amount5_12) {
		this.amount5_12 = amount5_12;
	}
	public Long getAmount6_k() {
		return amount6_k;
	}
	public void setAmount6_k(Long amount6_k) {
		this.amount6_k = amount6_k;
	}
	public Long getAmount6_1() {
		return amount6_1;
	}
	public void setAmount6_1(Long amount6_1) {
		this.amount6_1 = amount6_1;
	}
	public Long getAmount6_2() {
		return amount6_2;
	}
	public void setAmount6_2(Long amount6_2) {
		this.amount6_2 = amount6_2;
	}
	public Long getAmount6_3() {
		return amount6_3;
	}
	public void setAmount6_3(Long amount6_3) {
		this.amount6_3 = amount6_3;
	}
	public Long getAmount6_4() {
		return amount6_4;
	}
	public void setAmount6_4(Long amount6_4) {
		this.amount6_4 = amount6_4;
	}
	public Long getAmount6_5() {
		return amount6_5;
	}
	public void setAmount6_5(Long amount6_5) {
		this.amount6_5 = amount6_5;
	}
	public Long getAmount6_6() {
		return amount6_6;
	}
	public void setAmount6_6(Long amount6_6) {
		this.amount6_6 = amount6_6;
	}
	public Long getAmount6_7() {
		return amount6_7;
	}
	public void setAmount6_7(Long amount6_7) {
		this.amount6_7 = amount6_7;
	}
	public Long getAmount6_8() {
		return amount6_8;
	}
	public void setAmount6_8(Long amount6_8) {
		this.amount6_8 = amount6_8;
	}
	public Long getAmount6_9() {
		return amount6_9;
	}
	public void setAmount6_9(Long amount6_9) {
		this.amount6_9 = amount6_9;
	}
	public Long getAmount6_10() {
		return amount6_10;
	}
	public void setAmount6_10(Long amount6_10) {
		this.amount6_10 = amount6_10;
	}
	public Long getAmount6_11() {
		return amount6_11;
	}
	public void setAmount6_11(Long amount6_11) {
		this.amount6_11 = amount6_11;
	}
	public Long getAmount6_12() {
		return amount6_12;
	}
	public void setAmount6_12(Long amount6_12) {
		this.amount6_12 = amount6_12;
	}
	public Long getAmount6_n() {
		return amount6_n;
	}
	public void setAmount6_n(Long amount6_n) {
		this.amount6_n = amount6_n;
	}
	public Long getAmount6_z() {
		return amount6_z;
	}
	public void setAmount6_z(Long amount6_z) {
		this.amount6_z = amount6_z;
	}
	public Long getAmount6_t() {
		return amount6_t;
	}
	public void setAmount6_t(Long amount6_t) {
		this.amount6_t = amount6_t;
	}
	public Long getAmount6_g() {
		return amount6_g;
	}
	public void setAmount6_g(Long amount6_g) {
		this.amount6_g = amount6_g;
	}
	public Long getAmount7_k() {
		return amount7_k;
	}
	public void setAmount7_k(Long amount7_k) {
		this.amount7_k = amount7_k;
	}
	public Long getAmount7_1() {
		return amount7_1;
	}
	public void setAmount7_1(Long amount7_1) {
		this.amount7_1 = amount7_1;
	}
	public Long getAmount7_2() {
		return amount7_2;
	}
	public void setAmount7_2(Long amount7_2) {
		this.amount7_2 = amount7_2;
	}
	public Long getAmount7_3() {
		return amount7_3;
	}
	public void setAmount7_3(Long amount7_3) {
		this.amount7_3 = amount7_3;
	}
	public Long getAmount7_4() {
		return amount7_4;
	}
	public void setAmount7_4(Long amount7_4) {
		this.amount7_4 = amount7_4;
	}
	public Long getAmount7_5() {
		return amount7_5;
	}
	public void setAmount7_5(Long amount7_5) {
		this.amount7_5 = amount7_5;
	}
	public Long getAmount7_6() {
		return amount7_6;
	}
	public void setAmount7_6(Long amount7_6) {
		this.amount7_6 = amount7_6;
	}
	public Long getAmount7_7() {
		return amount7_7;
	}
	public void setAmount7_7(Long amount7_7) {
		this.amount7_7 = amount7_7;
	}
	public Long getAmount7_8() {
		return amount7_8;
	}
	public void setAmount7_8(Long amount7_8) {
		this.amount7_8 = amount7_8;
	}
	public Long getAmount7_9() {
		return amount7_9;
	}
	public void setAmount7_9(Long amount7_9) {
		this.amount7_9 = amount7_9;
	}
	public Long getAmount7_10() {
		return amount7_10;
	}
	public void setAmount7_10(Long amount7_10) {
		this.amount7_10 = amount7_10;
	}
	public Long getAmount7_11() {
		return amount7_11;
	}
	public void setAmount7_11(Long amount7_11) {
		this.amount7_11 = amount7_11;
	}
	public Long getAmount7_12() {
		return amount7_12;
	}
	public void setAmount7_12(Long amount7_12) {
		this.amount7_12 = amount7_12;
	}
	public Long getAmount7_n() {
		return amount7_n;
	}
	public void setAmount7_n(Long amount7_n) {
		this.amount7_n = amount7_n;
	}
	public Long getAmount7_z() {
		return amount7_z;
	}
	public void setAmount7_z(Long amount7_z) {
		this.amount7_z = amount7_z;
	}
	public Long getAmount7_t() {
		return amount7_t;
	}
	public void setAmount7_t(Long amount7_t) {
		this.amount7_t = amount7_t;
	}
	public Long getAmount7_g() {
		return amount7_g;
	}
	public void setAmount7_g(Long amount7_g) {
		this.amount7_g = amount7_g;
	}
	public Long getAmount8_k() {
		return amount8_k;
	}
	public void setAmount8_k(Long amount8_k) {
		this.amount8_k = amount8_k;
	}
	public Long getAmount8_1() {
		return amount8_1;
	}
	public void setAmount8_1(Long amount8_1) {
		this.amount8_1 = amount8_1;
	}
	public Long getAmount8_2() {
		return amount8_2;
	}
	public void setAmount8_2(Long amount8_2) {
		this.amount8_2 = amount8_2;
	}
	public Long getAmount8_3() {
		return amount8_3;
	}
	public void setAmount8_3(Long amount8_3) {
		this.amount8_3 = amount8_3;
	}
	public Long getAmount8_4() {
		return amount8_4;
	}
	public void setAmount8_4(Long amount8_4) {
		this.amount8_4 = amount8_4;
	}
	public Long getAmount8_5() {
		return amount8_5;
	}
	public void setAmount8_5(Long amount8_5) {
		this.amount8_5 = amount8_5;
	}
	public Long getAmount8_6() {
		return amount8_6;
	}
	public void setAmount8_6(Long amount8_6) {
		this.amount8_6 = amount8_6;
	}
	public Long getAmount8_7() {
		return amount8_7;
	}
	public void setAmount8_7(Long amount8_7) {
		this.amount8_7 = amount8_7;
	}
	public Long getAmount8_8() {
		return amount8_8;
	}
	public void setAmount8_8(Long amount8_8) {
		this.amount8_8 = amount8_8;
	}
	public Long getAmount8_9() {
		return amount8_9;
	}
	public void setAmount8_9(Long amount8_9) {
		this.amount8_9 = amount8_9;
	}
	public Long getAmount8_10() {
		return amount8_10;
	}
	public void setAmount8_10(Long amount8_10) {
		this.amount8_10 = amount8_10;
	}
	public Long getAmount8_11() {
		return amount8_11;
	}
	public void setAmount8_11(Long amount8_11) {
		this.amount8_11 = amount8_11;
	}
	public Long getAmount8_12() {
		return amount8_12;
	}
	public void setAmount8_12(Long amount8_12) {
		this.amount8_12 = amount8_12;
	}
	public Long getAmount8_n() {
		return amount8_n;
	}
	public void setAmount8_n(Long amount8_n) {
		this.amount8_n = amount8_n;
	}
	public Long getAmount8_z() {
		return amount8_z;
	}
	public void setAmount8_z(Long amount8_z) {
		this.amount8_z = amount8_z;
	}
	public Long getAmount8_t() {
		return amount8_t;
	}
	public void setAmount8_t(Long amount8_t) {
		this.amount8_t = amount8_t;
	}
	public Long getAmount8_g() {
		return amount8_g;
	}
	public void setAmount8_g(Long amount8_g) {
		this.amount8_g = amount8_g;
	}
	public Long getAmount9_k() {
		return amount9_k;
	}
	public void setAmount9_k(Long amount9_k) {
		this.amount9_k = amount9_k;
	}
	public Long getAmount9_1() {
		return amount9_1;
	}
	public void setAmount9_1(Long amount9_1) {
		this.amount9_1 = amount9_1;
	}
	public Long getAmount9_2() {
		return amount9_2;
	}
	public void setAmount9_2(Long amount9_2) {
		this.amount9_2 = amount9_2;
	}
	public Long getAmount9_3() {
		return amount9_3;
	}
	public void setAmount9_3(Long amount9_3) {
		this.amount9_3 = amount9_3;
	}
	public Long getAmount9_4() {
		return amount9_4;
	}
	public void setAmount9_4(Long amount9_4) {
		this.amount9_4 = amount9_4;
	}
	public Long getAmount9_5() {
		return amount9_5;
	}
	public void setAmount9_5(Long amount9_5) {
		this.amount9_5 = amount9_5;
	}
	public Long getAmount9_6() {
		return amount9_6;
	}
	public void setAmount9_6(Long amount9_6) {
		this.amount9_6 = amount9_6;
	}
	public Long getAmount9_7() {
		return amount9_7;
	}
	public void setAmount9_7(Long amount9_7) {
		this.amount9_7 = amount9_7;
	}
	public Long getAmount9_8() {
		return amount9_8;
	}
	public void setAmount9_8(Long amount9_8) {
		this.amount9_8 = amount9_8;
	}
	public Long getAmount9_9() {
		return amount9_9;
	}
	public void setAmount9_9(Long amount9_9) {
		this.amount9_9 = amount9_9;
	}
	public Long getAmount9_10() {
		return amount9_10;
	}
	public void setAmount9_10(Long amount9_10) {
		this.amount9_10 = amount9_10;
	}
	public Long getAmount9_11() {
		return amount9_11;
	}
	public void setAmount9_11(Long amount9_11) {
		this.amount9_11 = amount9_11;
	}
	public Long getAmount9_12() {
		return amount9_12;
	}
	public void setAmount9_12(Long amount9_12) {
		this.amount9_12 = amount9_12;
	}
	public Long getAmount9_n() {
		return amount9_n;
	}
	public void setAmount9_n(Long amount9_n) {
		this.amount9_n = amount9_n;
	}
	public Long getAmount9_z() {
		return amount9_z;
	}
	public void setAmount9_z(Long amount9_z) {
		this.amount9_z = amount9_z;
	}
	public Long getAmount9_t() {
		return amount9_t;
	}
	public void setAmount9_t(Long amount9_t) {
		this.amount9_t = amount9_t;
	}
	public Long getAmount9_g() {
		return amount9_g;
	}
	public void setAmount9_g(Long amount9_g) {
		this.amount9_g = amount9_g;
	}
	public Long getAmount10_k() {
		return amount10_k;
	}
	public void setAmount10_k(Long amount10_k) {
		this.amount10_k = amount10_k;
	}
	public Long getAmount10_1() {
		return amount10_1;
	}
	public void setAmount10_1(Long amount10_1) {
		this.amount10_1 = amount10_1;
	}
	public Long getAmount10_2() {
		return amount10_2;
	}
	public void setAmount10_2(Long amount10_2) {
		this.amount10_2 = amount10_2;
	}
	public Long getAmount10_3() {
		return amount10_3;
	}
	public void setAmount10_3(Long amount10_3) {
		this.amount10_3 = amount10_3;
	}
	public Long getAmount10_4() {
		return amount10_4;
	}
	public void setAmount10_4(Long amount10_4) {
		this.amount10_4 = amount10_4;
	}
	public Long getAmount10_5() {
		return amount10_5;
	}
	public void setAmount10_5(Long amount10_5) {
		this.amount10_5 = amount10_5;
	}
	public Long getAmount10_6() {
		return amount10_6;
	}
	public void setAmount10_6(Long amount10_6) {
		this.amount10_6 = amount10_6;
	}
	public Long getAmount10_7() {
		return amount10_7;
	}
	public void setAmount10_7(Long amount10_7) {
		this.amount10_7 = amount10_7;
	}
	public Long getAmount10_8() {
		return amount10_8;
	}
	public void setAmount10_8(Long amount10_8) {
		this.amount10_8 = amount10_8;
	}
	public Long getAmount10_9() {
		return amount10_9;
	}
	public void setAmount10_9(Long amount10_9) {
		this.amount10_9 = amount10_9;
	}
	public Long getAmount10_10() {
		return amount10_10;
	}
	public void setAmount10_10(Long amount10_10) {
		this.amount10_10 = amount10_10;
	}
	public Long getAmount10_11() {
		return amount10_11;
	}
	public void setAmount10_11(Long amount10_11) {
		this.amount10_11 = amount10_11;
	}
	public Long getAmount10_12() {
		return amount10_12;
	}
	public void setAmount10_12(Long amount10_12) {
		this.amount10_12 = amount10_12;
	}
	public Long getAmount10_n() {
		return amount10_n;
	}
	public void setAmount10_n(Long amount10_n) {
		this.amount10_n = amount10_n;
	}
	public Long getAmount10_z() {
		return amount10_z;
	}
	public void setAmount10_z(Long amount10_z) {
		this.amount10_z = amount10_z;
	}
	public Long getAmount10_t() {
		return amount10_t;
	}
	public void setAmount10_t(Long amount10_t) {
		this.amount10_t = amount10_t;
	}
	public Long getAmount10_g() {
		return amount10_g;
	}
	public void setAmount10_g(Long amount10_g) {
		this.amount10_g = amount10_g;
	}
	public String getAstNameGen() {
		return astNameGen;
	}
	public void setAstNameGen(String astNameGen) {
		this.astNameGen = astNameGen;
	}
	public String getHolCompanyNameGen() {
		return holCompanyNameGen;
	}
	public void setHolCompanyNameGen(String holCompanyNameGen) {
		this.holCompanyNameGen = holCompanyNameGen;
	}
	public String getHolSectionNameGen() {
		return holSectionNameGen;
	}
	public void setHolSectionNameGen(String holSectionNameGen) {
		this.holSectionNameGen = holSectionNameGen;
	}
	public String getHolStaffCodeGen() {
		return holStaffCodeGen;
	}
	public void setHolStaffCodeGen(String holStaffCodeGen) {
		this.holStaffCodeGen = holStaffCodeGen;
	}
	public String getHolStaffNameGen() {
		return holStaffNameGen;
	}
	public void setHolStaffNameGen(String holStaffNameGen) {
		this.holStaffNameGen = holStaffNameGen;
	}
	public String getUseStaffCodeGen() {
		return useStaffCodeGen;
	}
	public void setUseStaffCodeGen(String useStaffCodeGen) {
		this.useStaffCodeGen = useStaffCodeGen;
	}
	public String getUseStaffNameGen() {
		return useStaffNameGen;
	}
	public void setUseStaffNameGen(String useStaffNameGen) {
		this.useStaffNameGen = useStaffNameGen;
	}
	public String getHolOfficeNameGen() {
		return holOfficeNameGen;
	}
	public void setHolOfficeNameGen(String holOfficeNameGen) {
		this.holOfficeNameGen = holOfficeNameGen;
	}
	public Integer getHolOfficeFloorGen() {
		return holOfficeFloorGen;
	}
	public void setHolOfficeFloorGen(Integer holOfficeFloorGen) {
		this.holOfficeFloorGen = holOfficeFloorGen;
	}
	public Integer getHolQuantityGen() {
		return holQuantityGen;
	}
	public void setHolQuantityGen(Integer holQuantityGen) {
		this.holQuantityGen = holQuantityGen;
	}
	public Date getDeleteDateGen() {
		return deleteDateGen;
	}
	public void setDeleteDateGen(Date deleteDateGen) {
		this.deleteDateGen = deleteDateGen;
	}
	/**
	 * @return the rental1stymdF
	 */
	public String getRental1stymdF() {
		return rental1stymdF;
	}
	/**
	 * @param rental1stymdF the rental1stymdF to set
	 */
	public void setRental1stymdF(String rental1stymdF) {
		this.rental1stymdF = rental1stymdF;
	}
	/**
	 * @return the rentalstymdF
	 */
	public String getRentalstymdF() {
		return rentalstymdF;
	}
	/**
	 * @param rentalstymdF the rentalstymdF to set
	 */
	public void setRentalstymdF(String rentalstymdF) {
		this.rentalstymdF = rentalstymdF;
	}
	/**
	 * @return the rentaledymdF
	 */
	public String getRentaledymdF() {
		return rentaledymdF;
	}
	/**
	 * @param rentaledymdF the rentaledymdF to set
	 */
	public void setRentaledymdF(String rentaledymdF) {
		this.rentaledymdF = rentaledymdF;
	}
	/**
	 * @return the seikyustF
	 */
	public String getSeikyustF() {
		return seikyustF;
	}
	/**
	 * @param seikyustF the seikyustF to set
	 */
	public void setSeikyustF(String seikyustF) {
		this.seikyustF = seikyustF;
	}
	/**
	 * @return the seikyuedF
	 */
	public String getSeikyuedF() {
		return seikyuedF;
	}
	/**
	 * @param seikyuedF the seikyuedF to set
	 */
	public void setSeikyuedF(String seikyuedF) {
		this.seikyuedF = seikyuedF;
	}
	/**
	 * @return the amount1_n
	 */
	public Long getAmount1_n() {
		return amount1_n;
	}
	/**
	 * @param amount1_n the amount1_n to set
	 */
	public void setAmount1_n(Long amount1_n) {
		this.amount1_n = amount1_n;
	}
	/**
	 * @return the amount1_z
	 */
	public Long getAmount1_z() {
		return amount1_z;
	}
	/**
	 * @param amount1_z the amount1_z to set
	 */
	public void setAmount1_z(Long amount1_z) {
		this.amount1_z = amount1_z;
	}
	/**
	 * @return the amount1_t
	 */
	public Long getAmount1_t() {
		return amount1_t;
	}
	/**
	 * @param amount1_t the amount1_t to set
	 */
	public void setAmount1_t(Long amount1_t) {
		this.amount1_t = amount1_t;
	}
	/**
	 * @return the amount1_g
	 */
	public Long getAmount1_g() {
		return amount1_g;
	}
	/**
	 * @param amount1_g the amount1_g to set
	 */
	public void setAmount1_g(Long amount1_g) {
		this.amount1_g = amount1_g;
	}
	/**
	 * @return the amount2_n
	 */
	public Long getAmount2_n() {
		return amount2_n;
	}
	/**
	 * @param amount2_n the amount2_n to set
	 */
	public void setAmount2_n(Long amount2_n) {
		this.amount2_n = amount2_n;
	}
	/**
	 * @return the amount2_z
	 */
	public Long getAmount2_z() {
		return amount2_z;
	}
	/**
	 * @param amount2_z the amount2_z to set
	 */
	public void setAmount2_z(Long amount2_z) {
		this.amount2_z = amount2_z;
	}
	/**
	 * @return the amount2_t
	 */
	public Long getAmount2_t() {
		return amount2_t;
	}
	/**
	 * @param amount2_t the amount2_t to set
	 */
	public void setAmount2_t(Long amount2_t) {
		this.amount2_t = amount2_t;
	}
	/**
	 * @return the amount2_g
	 */
	public Long getAmount2_g() {
		return amount2_g;
	}
	/**
	 * @param amount2_g the amount2_g to set
	 */
	public void setAmount2_g(Long amount2_g) {
		this.amount2_g = amount2_g;
	}
	/**
	 * @return the amount3_n
	 */
	public Long getAmount3_n() {
		return amount3_n;
	}
	/**
	 * @param amount3_n the amount3_n to set
	 */
	public void setAmount3_n(Long amount3_n) {
		this.amount3_n = amount3_n;
	}
	/**
	 * @return the amount3_z
	 */
	public Long getAmount3_z() {
		return amount3_z;
	}
	/**
	 * @param amount3_z the amount3_z to set
	 */
	public void setAmount3_z(Long amount3_z) {
		this.amount3_z = amount3_z;
	}
	/**
	 * @return the amount3_t
	 */
	public Long getAmount3_t() {
		return amount3_t;
	}
	/**
	 * @param amount3_t the amount3_t to set
	 */
	public void setAmount3_t(Long amount3_t) {
		this.amount3_t = amount3_t;
	}
	/**
	 * @return the amount3_g
	 */
	public Long getAmount3_g() {
		return amount3_g;
	}
	/**
	 * @param amount3_g the amount3_g to set
	 */
	public void setAmount3_g(Long amount3_g) {
		this.amount3_g = amount3_g;
	}
	/**
	 * @return the amount4_n
	 */
	public Long getAmount4_n() {
		return amount4_n;
	}
	/**
	 * @param amount4_n the amount4_n to set
	 */
	public void setAmount4_n(Long amount4_n) {
		this.amount4_n = amount4_n;
	}
	/**
	 * @return the amount4_z
	 */
	public Long getAmount4_z() {
		return amount4_z;
	}
	/**
	 * @param amount4_z the amount4_z to set
	 */
	public void setAmount4_z(Long amount4_z) {
		this.amount4_z = amount4_z;
	}
	/**
	 * @return the amount4_t
	 */
	public Long getAmount4_t() {
		return amount4_t;
	}
	/**
	 * @param amount4_t the amount4_t to set
	 */
	public void setAmount4_t(Long amount4_t) {
		this.amount4_t = amount4_t;
	}
	/**
	 * @return the amount4_g
	 */
	public Long getAmount4_g() {
		return amount4_g;
	}
	/**
	 * @param amount4_g the amount4_g to set
	 */
	public void setAmount4_g(Long amount4_g) {
		this.amount4_g = amount4_g;
	}
	/**
	 * @return the amount5_n
	 */
	public Long getAmount5_n() {
		return amount5_n;
	}
	/**
	 * @param amount5_n the amount5_n to set
	 */
	public void setAmount5_n(Long amount5_n) {
		this.amount5_n = amount5_n;
	}
	/**
	 * @return the amount5_z
	 */
	public Long getAmount5_z() {
		return amount5_z;
	}
	/**
	 * @param amount5_z the amount5_z to set
	 */
	public void setAmount5_z(Long amount5_z) {
		this.amount5_z = amount5_z;
	}
	/**
	 * @return the amount5_t
	 */
	public Long getAmount5_t() {
		return amount5_t;
	}
	/**
	 * @param amount5_t the amount5_t to set
	 */
	public void setAmount5_t(Long amount5_t) {
		this.amount5_t = amount5_t;
	}
	/**
	 * @return the amount5_g
	 */
	public Long getAmount5_g() {
		return amount5_g;
	}
	/**
	 * @param amount5_g the amount5_g to set
	 */
	public void setAmount5_g(Long amount5_g) {
		this.amount5_g = amount5_g;
	}


}
