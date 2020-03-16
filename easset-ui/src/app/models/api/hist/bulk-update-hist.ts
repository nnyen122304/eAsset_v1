/**
 * 一括更新履歴データクラス
 */
export class BulkUpdateHist {

  /**
   *  ログID
   */
  logId?: number;

  /**
   *  作成日
   */
  createDate?: Date;

  /**
   *  作成者社員番号
   */
  createStaffCode?: string;

  /**
   *  作成者名
   */
  createStaffName?: string;

  /**
   *  更新日
   */
  updateDate?: Date;

  /**
   *  更新者社員番号
   */
  updateStaffCode?: string;

  /**
   *  機能 ASSET：情報機器等一括更新、LICENSE:ライセンス一括更新
   */
  function?: string;

  /**
   *  処理ステータス ファイル読込中、更新中、終了
   */
  execStatus?: string;

  /**
   *  処理ファイルID
   */
  fileId?: string;

  /**
   *  処理件数
   */
  execCount?: number;

  /**
   *  処理成功件数
   */
  successCount?: number;

  /**
   *  処理失敗件数
   */
  failureCount?: number;

  /**
   *  更新項目
   */
  updateColumn?: string;

  /**
   *  更新項目名
   */
  updateColumnName?: string;

}
