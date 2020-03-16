import {ApBgnInt} from './ap-bgn-int.model';

export class ApBgnIntSR extends ApBgnInt {
  /**
   * 指定部署の配下部署も検索に含める
   */
  includeSectionHierarchyFlag?: string;

  /**
   * リリース日(TO)
   */
  astReleaseDateTo?: Date;

  /**
   * 稟議書・経営会議等承認済表示
   */
  specialApproveFlagName?: string;

  /**
   * 課長/GL承認不要表示
   */
  apSkipApproveFlagName?: string;

  /**
   * 要CIO審査表示
   */
  apNeedCioJudgeFlagName?: string;

  /**
   * 取得時期名 (その他、先行申請)
   */
  astGetTimeFlagName?: string;

  /**
   * クラウド区分表示
   */
  astCloudTypeName?: string;

  /**
   * 費用削減効果・収益獲得効果 1:有,2:無,3:不明
   */
  astEffectTypeName?: string;

  /**
   * 賃借物件据付費用 0:不要,1:必要
   */
  astRentFlagName?: string;

  /**
   * 販売管理費/原価区分表示
   */
  costTypeName?: string;

  /**
   * サービス提供開始報告完了フラグ 0:登録残有り,1:完了
   */
  reportCompleteFlagName?: string;

  /**
   * リリース日
   */
  releaseDate?: Date;

  /**
   * 計上区分名
   */
  addUpTypeName?: string;

  /**
   * ライセンス登録数
   */
  licenseRegistCount?: number;

  /**
   * 経費負担部署配分表示
   */
  costSectionDistName?: string;

  sel?: boolean;
}
