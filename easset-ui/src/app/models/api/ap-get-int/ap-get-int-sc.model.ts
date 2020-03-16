import { ApGetInt } from './ap-get-int.model';

export class ApGetIntSC extends ApGetInt {
/**
 * 申請書番号複数
 */
applicationIdPlural?: string;

/**
 * 旧Verも含めて検索フラグ
 */
oldVerFlag?: string;

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
 * 計上区分
 */
addUpType?: string;

/**
 * ﾘﾘｰｽ/検収(納品)予定日From
 */
astCompletePlanDateFrom?: Date;

/**
 * ﾘﾘｰｽ/検収(納品)予定日To
 */
astCompletePlanDateTo?: Date;

/**
 * リリース督促メール送信フラグ
 */
reminderFlag?: string;

/**
 * 督促メール送信日付From
 */
reminderDateFrom?: Date;

/**
 * 督促メール送信日付To
 */
reminderDateTo?: Date;

/**
 * 取得金額From
 */
getTotalAmountFrom?: number;

/**
 * 取得金額To
 */
getTotalAmountTo?: number;

/**
 * 部署階層検索フラグ
 */
includeSectionHierarchyFlag?: string;

/**
 * 分類
 */
astCategoryCodeFld?: string;

/**
 * 資産名
 */
astNameFld?: string;

/**
 * 資産プロジェクトコード
 */
astPrjCodeFld?: string;

/**
 * コメント
 */
astCommentFld?: string;

/**
 * 備考
 */
description?: string;

sel: boolean;
}
