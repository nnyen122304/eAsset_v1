import { CostScr } from './cost-scr.model';

export class CostScrSC extends CostScr {
  /**
   * 精査状況
   */
  scrImplementation?: string;

  /**
   * 移動申請状況
   */
  apChangeStatus?: string;

  /**
   * 部署階層検索フラグ
   */
  includeSectionHierarchyFlag?: string;
}
