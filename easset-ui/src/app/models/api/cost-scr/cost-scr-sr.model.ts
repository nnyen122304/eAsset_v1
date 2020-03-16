import { CostScr } from './cost-scr.model';

export class CostScrSR extends CostScr {
  /**
   * 精査種別
   */
  scrTypeName: string;

  /**
   * 当年度：経費負担部課名
   */
  costSectionName: string;

  /**
   * 前年度：経費負担部課名
   */
  costSectionNameOld: string;

  /**
   * 精査担当部署名
   */
  scrSectionName: string;

  /**
   * 完了(取消)処理実行者社員番号
   */
  compExecStaffName: string;

  /**
   * 精査完了名
   */
  compFlagName: string;

  /**
   * 精査状況：未
   */
  costScrCountUndecided: number;

  /**
   * 精査状況：OK
   */
  costScrCountOK: number;

  /**
   * 精査状況：NG
   */
  costScrCountNG: number;

  /**
   * 精査状況：計
   */
  costScrCountTotal: number;

  /**
   * 移動申請：未
   */
  apChangeCountUnApply: number;

  /**
   * 移動申請：申請中
   */
  apChangeCountApply: number;

  /**
   * 移動申請：承認済
   */
  apChangeCountApprove: number;

  /**
   * 移動申請：計
   */
  apChangeCountTotal: number;
}
