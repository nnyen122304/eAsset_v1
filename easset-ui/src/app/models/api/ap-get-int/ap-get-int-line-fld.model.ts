export class ApGetIntLineFld {
  /**
   * 申請書番号
   */
  applicationId?: string;

  /**
   * 申請書バージョン
   */
  applicationVersion?: string;

  /**
   * 行シーケンス
   */
  lineSeq?: number;

  /**
   * 作成日
   */
  createDate?: Date;

  /**
   * 作成者社員番号
   */
  createStaffCode?: string;

  /**
   * 更新日
   */
  updateDate?: Date;

  /**
   * 更新者社員番号
   */
  updateStaffCode?: string;

  /**
   * 分類 汎用マスタ-AP_GET_INT_AST_CATEGORY
   */
  astCategoryCode?: string;

  /**
   * 分類名
   */
  astCategoryName?: string;

  /**
   * 分類タイプ
   */
  astCategoryType?: string;

  /**
   * 期間ライセンスフラグ 0:その他,1:期間ライセンス
   */
  astTermFlag?: string;

  /**
   * 期間(年)
   */
  astTermYear?: number;

  /**
   * 資産名
   */
  astName?: string;

  /**
   * 資産プロジェクトコード
   */
  astPrjCode?: string;

  /**
   * 資産プロジェクト名
   */
  astPrjName?: string;

  /**
   * 資産プロジェクトタイプ
   */
  astPrjType?: string;

  /**
   * 仕入先コード MI顧客コード
   */
  astPurCompanyCode?: string;

  /**
   * 仕入先名
   */
  astPurCompanyName?: string;

  /**
   * 見積番号
   */
  astEstimateNumber?: string;

  /**
   * 単価
   */
  astUnitAmount?: number;

  /**
   * 数量/人月
   */
  astQuantity?: number;

  /**
   * 取得金額
   */
  astGetAmount?: number;

  /**
   * 計上区分 1:資産,2:費用
   */
  astAddUpType?: string;

  /**
   * 計上区分 資産フラグ
   */
  astAddUpType1Flag?: string;

  /**
   * 計上区分 費用フラグ
   */
  astAddUpType2Flag?: string;

  /**
   * コメント
   */
  astComment?: string;

  /**
   * 処理科目 汎用マスタ-AP_INT_ACCOUNT
   */
  astAccountCode?: string;

  /**
   * 処理科目名
   */
  astAccountName?: string;
}
