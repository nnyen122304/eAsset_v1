/**
 * ProPlus取込ステータスデータクラス
 */
export class PpfsStat {

  /**
   * 会計年月
   */
  period?: string;

  /**
   * 会計年月名
   */
  periodName?: string;

  /**
   * 会社コード 人事マスタコード
   */
  companyCode?: string;

  /**
   * データ区分 1:固定資産,2:リース・レンタル,1:固定資産-予測,2:リース・レンタル-予測
   */
  dataType?: string;

  /**
   * データ区分名
   */
  dataTypeName?: string;

  /**
   * 作成日
   */
  createDate?: Date;

  /**
   * 作成者社員番号
   */
  createStaffCode?: string;

  /**
   * 作成者社員名
   */
  createStaffName?: string;

  /**
   * 更新日
   */
  updateDate?: Date;

  /**
   * 更新者社員番号
   */
  updateStaffCode?: string;

  /**
   * 処理ステータス 1:実行中,2:正常終了,3:異常終了
   */
  execStatus?: string;

  /**
   *  処理ステータス名
   */
  execStatusName?: string;

  /**
   * 予測計算基準年月
   */
  schCalcBasePeriod?: string;

  /**
   * 予測計算年数
   */
  schCalcYear?: number;

  /**
   * 前回成功時：作成日
   */
  lastSuccessCreateDate?: Date;

}
