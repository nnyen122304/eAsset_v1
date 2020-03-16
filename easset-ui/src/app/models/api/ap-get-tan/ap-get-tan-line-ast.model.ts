export class ApGetTanLineAst {

  /**
   * 資産(機器)明細識別番号
   */
  apGetTanLineAstId?: number;

  /**
   * 作成日
   */
  createDate?: Date;

  /**
   * 作成者社員番号
   */
  createStaffCode?: string;

  /**
   * 更新日
   */
  updateDate?: Date;

  /**
   * 更新者社員番号
   */
  updateStaffCode?: string;

  /**
   * 申請書番号
   */
  applicationId?: string;

  /**
   * 行シーケンス
   */
  lineSeq?: number;

  /**
   * 資産(機器)名
   */
  astName?: string;

  /**
   * 資産(機器)大分類
   */
  astCategory1Code?: string;

  /**
   * 資産(機器)大分類名
   */
  astCategory1Name?: string;

  /**
   * 資産(機器)小分類
   */
  astCategory2Code?: string;

  /**
   * 資産(機器)小分類名
   */
  astCategory2Name?: string;

  /**
   * 資産(機器)数量管理区分
   */
  astQuantityManageType?: string;

  /**
   * 資産(機器)シンクライアント区分
   */
  astThinClientType?: string;

  /**
   *  数量
   */
  quantity?: number;

  /**
   * 登録数量
   */
  registQuantity?: number;

  /**
   * メーカーコード
   */
  astMakerCode?: string;

  /**
   * メーカー名
   */
  astMakerName?: string;

  /**
   * メーカー型番
   */
  astMakerModel?: string;

  /**
   * 筐体/形状
   */
  astShapeCode?: string;

  /**
   * 筐体/形状名
   */
  astShapeName?: string;

  /**
   * 資産プロジェクトコード
   */
  astPrjCode?: string;

  /**
   * 資産プロジェクト名
   */
  astPrjName?: string;

  /**
   * 資産プロジェクトタイプ
   */
  astPrjType?: string;

  /**
   * 故障交換対象：情報機器管理番号
   */
  failureAssetId?: string;

  /**
   * 故障交換対象：契約番号
   */
  failureContractNum?: string;

  /**
   * 故障交換対象：OIR
   */
  failureAstOir?: string;

  /**
   * 故障交換対象：シリアル番号
   */
  failureAstSerialCode?: string;

  /**
   * 故障交換対象：使用者社員番号
   */
  failureUseStaffCode?: string;

  /**
   * 自動登録フラグ 0:手動登録,1:自動登録
   */
  autoRegistFlag?: string;

  /**
   * 登録不要フラグ 0:登録必要,1:登録不要
   */
  noRegistFlag?: string;

  /**
   * シール発行フラグ 0:シール発行不要,1:シール発行必要
   */
  sealIssueFlag?: string;

  /**
   * 備品フラグ 0:備品以外,1:備品
   */
  equipmentFlag?: string;

  /**
   * レンタル会社型番
   */
  astModelCode?: string;

  /**
   * 月単価
   */
  astGetLeMonthAmount?: number;

  /**
   * 注文書用資産(機器)名
   */
  reoAstName?: string;

  /**
   * レンタル注文書:約款
   */
  reoAgreement?: string;

  /**
   * レンタル注文書:価格表
   */
  reoPriceList?: string;

  /**
   * レンタル注文書:契約月数
   */
  astGetLeContractMonth?: number;

  /**
   * 故障交換対象：使用者社員名
   */
  failureUseStaffName?: string;
}
