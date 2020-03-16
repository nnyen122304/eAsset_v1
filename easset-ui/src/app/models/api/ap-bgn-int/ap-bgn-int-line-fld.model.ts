/**
 * 概要説明   : サービス提供開始報告_資産明細
 */
export class ApBgnIntLineFld {

  /**
   * 申請書番号
   */
  applicationId?: string;

  /**
   *  明細区分 1:申請時,2:承認時追加分
   */
  lineType?: string;

  /**
   * 行シーケンス 明細区分毎のシーケンス
   */
  lineSeq?: number;

  /**
   * 作成日
   */
  createDate: Date;

  /**
   * 作成者社員番号
   */
  createStaffCode?: string;

  /**
   * 更新日
   */
  updateDate: Date;

  /**
   * 更新者社員番号
   */
  updateStaffCode?: string;

  /**
   * 固有番号 ProPlusの資産台帳キー
   */
  ppId?: number;

  /**
   * 計上日
   */
  astDate: Date;

  /**
   * 資産番号
   */
  astNum?: string;

  /**
   * 資産名
   */
  astName?: string;

  /**
   * 取得価額 仮勘定の税法帳簿取得価額
   */
  astGetAmount?: number;

  /**
   * 資産計上額 仮勘定の会社帳簿取得価額
   */
  astAmount?: number;

  /**
   * 資産計上部課
   */
  astSectionCode?: string;

  /**
   * 資産計上部課名
   */
  astSectionName?: string;

  /**
   * 経路フラグ
   */
  astRouteFlag?: string;

  /**
   * 経路フラグ名
   */
  astRouteName?: string;

  /**
   * 仕入先コード
   */
  astPurCompanyCode?: string;

  /**
   * 仕入先名
   */
  astPurCompanyName?: string;

  /**
   * 伝票番号
   */
  astSlipNum?: string;

  /**
   * 資産プロジェクトコード
   */
  astPrjCode?: string;

  /**
   * 資産プロジェクト名
   */
  astPrjName?: string;
}
