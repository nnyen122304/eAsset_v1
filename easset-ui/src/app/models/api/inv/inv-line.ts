/**
 * 資産別一覧情報クラス
 */
export class InvLine {

  /**
   *  行シーケンス
   */
  invLineSeq?: number;

  /**
   *  作成日
   */
  createDate?: Date;

  /**
   *  作成者社員番号
   */
  createStaffCode?: string;

  /**
   *  更新日
   */
  updateDate?: Date;

  /**
   *  更新者社員番号
   */
  updateStaffCode?: string;

  /**
   *  会計年月
   */
  period?: string;

  /**
   *  会社コード
   */
  companyCode?: string;

  /**
   *  保有部署コード
   */
  holSectionCode?: string;

  /**
   *  保有部署年度
   */
  holSectionYear?: number;

  /**
   *  資産区分 1:有形固定資産,2:資産除去費用,3無形固定資産(本勘定),4:無形固定資産(仮勘定),5:リース資産,6:レンタル資産,7:備品等
   */
  invAssetType?: string;

  /**
   *  棚卸ステータス 1:未実施,2:有,3:無,4:棚卸対象外
   */
  invStatus?: string;

  /**
   *  コメント
   */
  invComment?: string;

  /**
   *  現物管理番号 情報機器管理番号、ライセンス管理番号
   */
  astLicId?: string;

  /**
   *  固有番号
   */
  ppId?: number;

  /**
   *  資産番号
   */
  astNum?: string;

  /**
   *  契約番号
   */
  contractNum?: string;

  /**
   *  契約枝番
   */
  contractSubNum?: string;

  /**
   *  取得申請番号
   */
  applicationId?: string;

  /**
   *  抹消フラグ 0:抹消されていない,1:抹消済
   */
  deleteFlag?: string;

  /**
   *  抹消日
   */
  deleteDate?: Date;

  /**
   *  資産名
   */
  astName?: string;

  /**
   *  資産管理担当者
   */
  holStaffCode?: string;

  /**
   *  使用者社員番号
   */
  useStaffCode?: string;

  /**
   *  設置場所 汎用マスタ-OFFICE
   */
  holOfficeCode?: string;

  /**
   *  設置場所階数
   */
  holOfficeFloor?: number;

  /**
   *  部屋番号
   */
  holOfficeRoomNum?: string;

  /**
   *  ラック番号
   */
  holOfficeRackNum?: string;

  /**
   *  数量
   */
  holQuantity?: number;

  /**
   *  棚卸連携
   */
  invLinkType?: string;

  /**
   *  棚卸対象社員番号
   */
  invStaffCode?: string;

  /**
   *  現物備考
   */
  dscDescription?: string;

  /**
   *  管理項目1
   */
  dscAttribute1?: string;

  /**
   *  管理項目2
   */
  dscAttribute2?: string;

  /**
   *  棚卸ステータス
   */

  invStatus1?: string;

  /**
   * 棚卸「有」選択状態
   */
  selInvStatus1?: boolean;

  /**
   *  棚卸ステータス
   */
  invStatus2?: string;

  /**
   * 棚卸「無」選択状態
   */
  selInvStatus2?: boolean;

  /**
   *  棚卸ステータス名 ※ CSVファイル出力用
   */
  invStatusName?: string;

  /**
   *  抹消名
   */
  deleteFlagName?: string;

  /**
   *  資産管理担当者
   */
  holStaffName?: string;

  /**
   *  使用者
   */
  useStaffName?: string;

  /**
   *  設置場所
   */
  holOfficeName?: string;

  /**
   *  保有部署
   */
  holSectionName?: string;

  /**
   *  償却ステータス
   */
  skkStatusName?: string;



  ////////////// 資産


  /**
   *  償却資産区分
   */
  skkshisankbn?: string;

  /**
   *  償却資産区分名
   */
  skkshisankbnName?: string;

  /**
   *  資産管理区分
   */
  shisanknrkbn?: string;

  /**
   *  資産管理区分名
   */
  shisanknrkbnName?: string;

  /**
   *  取得日
   */
  stkymd?: string;

  /**
   *  取得日
   */
  stkymdF?: string;

  /**
   *  取得価額(会社帳簿)
   */
  stkgkk?: number;

  /**
   *  取得価額(税法帳簿)
   */
  stkgkz?: number;

  /**
   *  当月末帳簿価額(会社帳簿)
   */
  toBokak?: number;

  /**
   *  種類コード
   */
  shuruicd?: string;

  /**
   *  種類名称
   */
  shuruinm?: string;

  /**
   *  分類コード
   */
  bunruicd?: string;

  /**
   *  分類名称
   */
  bunruinm?: string;

  /**
   *  償却方法(会社帳簿)
   */
  skkhok?: string;

  /**
   *  償却方法名(会計帳簿)
   */
  skkhokName?: string;

  /**
   *  耐用年数(会社帳簿)
   */
  tainenk?: string;

  /**
   *  原販ﾌﾗｸﾞ(任意項目eAsset追加定義)
   */
  costType?: string;

  /**
   *  原販ﾌﾗｸﾞ名(任意項目eAsset追加定義)
   */
  costTypeName?: string;

  /**
   *  償却PJ(任意項目eAsset追加定義)
   */
  depPrjCode?: string;

  /**
   *  償却PJ名(任意項目eAsset追加定義)
   */
  depPrjName?: string;

  /**
   *  組織2コード
   */
  soshiki2cd?: string;

  /**
   *  組織2名称
   */
  soshiki2nm?: string;

  /**
   *  案件ｸﾞﾙｰﾌﾟ(任意項目eAsset追加定義)
   */
  itemGroupCode?: string;

  /**
   *  案件ｸﾞﾙｰﾌﾟ名(任意項目eAsset追加定義)
   */
  itemGroupName?: string;

  /**
   *  設置場所コード
   */
  setchicd?: string;

  /**
   *  設置場所名称
   */
  setchinm?: string;

  /**
   *  備考1
   */
  biko1?: string;

  /**
   *  備考2
   */
  biko2?: string;

  /**
   *  仕入先コード
   */
  purCompanyCode?: string;

  /**
   *  仕入先名
   */
  purCompanyName?: string;

  /**
   *  伝票番号
   */
  slipNum?: string;

  /**
   *  計上日(最古)
   */
  oldKeijoymd?: string;

  /**
   *  計上日(最新)
   */
  newKeijoymd?: string;

  /**
   *  計上日(最古)
   */
  oldKeijoymdF?: string;

  /**
   *  計上日(最新)
   */
  newKeijoymdF?: string;


 // リース


  /**
   *  資産名称
   */
  shisannmA?: string;

  /**
   *  リース開始年月日
   */
  lastymdC?: string;

  /**
   *  リース満了年月日
   */
  lamanryoymdC?: string;

  /**
   *  リース期間
   */
  lakikanC?: string;

  /**
   *  リース料総額
   */
  laryoTotalA?: number;

  /**
   *  1回当リース料
   */
  ikkailaryoA?: number;

  /**
   *  リース会社名
   */
  lakaishacdC?: string;

  /**
   *  契約区分名
   */
  kykkbnC?: string;

  /**
   *  リース取引判定名
   */
  latorihikikbnC?: string;

  /**
   *  B/S計上区分
   */
  bskeijokbnC?: string;

  /**
   *  任意(台帳)3コード
   */
  // tslint:disable-next-line: variable-name
  niniLd_3cdA?: string;

  /**
   *  任意(台帳)3名称
   */
  // tslint:disable-next-line: variable-name
  niniLd_3nmA?: string;

  /**
   *  任意(台帳)6コード
   */
  // tslint:disable-next-line: variable-name
  niniLd_6cdA?: string;

  /**
   *  任意(台帳)6名称
   */
  // tslint:disable-next-line: variable-name
  niniLd_6nmA?: string;

  /**
   *  任意(台帳)7コード
   */
  // tslint:disable-next-line: variable-name
  niniLd_7cdA?: string;

  /**
   *  任意(台帳)7名称
   */
  // tslint:disable-next-line: variable-name
  niniLd_7nmA?: string;

  /**
   *  資産計上額
   */
  stkgkkA?: number;

  /**
   *  帳簿価額
   */
  toBokakA?: number;

  /**
   *  リース会社名
   */
  lakaishacdCName?: string;

  /**
   *  種類コード
   */
  shuruicdA?: string;

  /**
   *  種類名称
   */
  shuruinmA?: string;

  /**
   *  分類コード
   */
  bunruicdA?: string;

  /**
   *  分類名称
   */
  bunruinmA?: string;

  /**
   *  代表設置場所
   */
  setchicdA?: string;

  /**
   *  代表設置場所
   */
  setchinmA?: string;

  /**
   *  資産計上部課
   */
  soshiki2cdA?: string;

  /**
   *  資産計上部課名
   */
  soshiki2nmA?: string;

  /**
   *  備考1
   */
  biko1A?: string;

  /**
   *  備考2
   */
  biko2A?: string;

  /**
   *  リース開始年月日
   */
  lastymdCF?: string;

  /**
   *  リース満了年月日
   */
  lamanryoymdCF?: string;

  /**
   *  契約区分名
   */
  kykkbnCName?: string;

  /**
   *  リース取引判定名
   */
  latorihikikbnCName?: string;

  /**
   *  B/S計上区分
   */
  bskeijokbnCName?: string;

  /**
   *  開始日
   */
  stymdL?: string;

  /**
   *  開始日(フォーマット)
   */
  stymdLF?: string;

  /**
   *  満了日
   */
  manryoymdL?: string;

  /**
   *  満了日(フォーマット)
   */
  manryoymdLF?: string;

  /**
   *  期間
   */
  kikanL?: number;

}
