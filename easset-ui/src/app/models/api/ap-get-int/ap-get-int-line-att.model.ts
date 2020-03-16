export class ApGetIntLineAtt {
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
   * 作成者社員名
   */
  createStaffName?: string;

  /**
   * 更新日
   */
  updateDate?: Date;

  /**
   * 更新者社員番号
   */
  updateStaffCode?: string;

  /**
   * 添付ファイル名 表示上のファイル名
   */
  attFileName?: string;

  /**
   * 添付ファイル保存ID ディスク保存時のファイル名
   */
  attFileId?: string;

  /**
   * 添付ファイル保存ID 保存前の一時ファイル
   */
  attFileIdTmp?: string;

  /**
   * コメント
   */
  attComment?: string;
}
