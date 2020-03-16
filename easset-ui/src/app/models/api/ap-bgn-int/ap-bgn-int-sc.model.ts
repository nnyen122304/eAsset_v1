import {ApBgnInt} from './ap-bgn-int.model';

export class ApBgnIntSC extends ApBgnInt {
  /**
   * 申請書番号複数
   */
  applicationIdPlural?: string;

  /**
   * e申請書類ID複数
   */
  eappIdPlural?: string;

  /**
   * 申請日From
   */
  apDateFrom?: Date;

  /**
   * 申請日To
   */
  apDateTo?: Date;

  /**
   * 資産明細:資産番号
   */
  astNumFld?: string;

  /**
   * 資産明細:資産番号複数
   */
  astNumFldPlural?: string;

  /**
   * 開発完了日From
   */
  astCompleteDateFrom?: Date;

  /**
   * 開発完了日To
   */
  astCompleteDateTo?: Date;

  /**
   * リリース日From
   */
  astReleaseDateFrom?: Date;

  /**
   * リリース日To
   */
  astReleaseDateTo?: Date;

  /**
   * 部署階層検索フラグ
   */
  includeSectionHierarchyFlag?: string;
}
