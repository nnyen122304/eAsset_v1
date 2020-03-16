import { ApGetInt } from './ap-get-int.model';

export class ApGetIntSR extends ApGetInt {
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

/**
 * 行シーケンス
 */
 fldLineSeq?: number;

 /**
  * 分類 汎用マスタ-AP_GET_INT_AST_CATEGORY
  */
fldAstCategoryCode?: string;

/**
 * 分類名
 */
fldAstCategoryName?: string;

/**
 * 分類タイプ
 */
fldAstCategoryType?: string;

/**
 * 期間ライセンスフラグ 0:その他,1:期間ライセンス
 */
fldAstTermFlag?: string;

/**
 * 期間ライセンスフラグ名
 */
fldAstTermFlagName?: string;

/**
 * 期間(年)
 */
fldAstTermYear?: number;

/**
 * 資産名
 */
fldAstName?: string;

/**
 * 資産プロジェクトコード
 */
fldAstPrjCode?: string;

/**
 * 資産プロジェクト名
 */
fldAstPrjName?: string;

/**
 * 資産プロジェクトタイプ
 */
fldAstPrjType?: string;

/**
 * 仕入先コード MI顧客コード
 */
fldAstPurCompanyCode?: string;

/**
 * 仕入先名
 */
fldAstPurCompanyName?: string;

/**
 * 見積書番号
 */
fldAstEstimateNumber?: string;

/**
 * 単価
 */
fldAstUnitAmount?: number;

/**
 * 数量/人月
 */
fldAstQuantity?: number;

/**
 * 取得金額
 */
fldAstGetAmount?: number;

/**
 * 計上区分 1:資産,2:費用
 */
fldAstAddUpType?: string;

/**
 * 計上区分名
 */
fldAstAddUpTypeName?: string;

/**
 * 計上区分 資産フラグ
 */
fldAstAddUpType1Flag?: string;

/**
 * 計上区分 費用フラグ
 */
fldAstAddUpType2Flag?: string;

/**
 * コメント
 */
fldAstComment?: string;

/**
 * 処理科目 汎用マスタ-AP_INT_ACCOUNT
 */
fldAstAccountCode?: string;

/**
 * 処理科目名
 */
fldAstAccountName?: string;

 /**
  * vi check box
  */
  sel?: boolean;
}

