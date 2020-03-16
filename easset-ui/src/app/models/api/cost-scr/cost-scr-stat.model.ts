export class CostScrStat {
  /**
   * 会計年月
   */
  period: string;

  /**
   * 会社コード
   */
  companyCode: string;

  /**
   * 作成日
   */
  createDate: Date;

  /**
   * 作成者社員番号
   */
  createStaffCode: string;

  /**
   * 更新日
   */
  updateDate: Date;

  /**
   * 更新者社員番号
   */
  updateStaffCode: string;

  /**
   * データ作成ステータス 1:実行中,2:正常終了,3:異常終了
   */
  createStatus: string;

  /**
   * データ作成処理開始日
   */
  createStartDate: Date;

  /**
   * データ作成処理終了日
   */
  createEndDate: Date;

  /**
   * データ作成実行者社員番号
   */
  createExecStaffCode: string;

  /**
   * 精査担当部署設定社員番号
   */
  scrSectionUpdateStaffCode: string;

  /**
   * 精査担当部署設定日
   */
  scrSectionUpdateDate: Date;

  /**
   * 各部メニューOPEN処理実行日
   */
  openDate: Date;

  /**
   * 各部メニューOPEN処理実行者社員番号
   */
  openStaffCode: string;

  /**
   * 各部メニューCLOSE処理実行日
   */
  closeDate: Date;

  /**
   * 各部メニューCLOSE処理実行者社員番号
   */
  closeStaffCode: string;

  /**
   * メール送信実行者社員番号
   */
  sendStaffCode: string;

  /**
   * メール送信ステータス
   */
  sendStatus: string;

  /**
   * メール送信開始日
   */
  sendStartDate: Date;

  /**
   * メール送信終了日
   */
  sendEndDate: Date;
  /**
   * 前回成功時：データ作成処理開始日
   */
  lastSuccessCreateStartDate: Date;

  /**
   * 前回成功時：データ作成処理終了日
   */
  lastSuccessCreateEndDate: Date;

  /**
   * 前回成功時：データ作成実行者社員番号
   */
  lastSuccessExecStaffCode: string;

  /**
   * データ作成ステータス名
   */
  createStatusName: string;

  /**
   * メール送信ステータス名
   */
  sendStatusName: string;

  /**
   * 作成者名
   */
  createExecStaffName: string;

  /**
   * 精査担当部署設定者社員名
   */
  scrSectionUpdateStaffName: string;

  /**
   * 各部メニューOPEN処理実行者名
   */
  openStaffName: string;
}
