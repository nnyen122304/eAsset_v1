import {InvSum} from './inv-sum';
/**
 * 棚卸一覧検索条件クラス
 */
export class InvSumSC extends InvSum {

  /**
   *  部署構造を含むフラグ
   */
  includeSectionHierarchyFlag?: string;

  /**
   *  棚卸実施
   */
  invImplementation?: string;

  /**
   *  検索条件
   */
  searchScopeType?: string;

}
