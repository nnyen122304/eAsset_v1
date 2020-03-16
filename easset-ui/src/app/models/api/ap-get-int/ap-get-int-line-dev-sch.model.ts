export class ApGetIntLineDevSch {
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
   * 実施期日FROM
   */
  devPeriodFrom?: Date;
  /**
   *  実施期日TO
   */
  devPeriodTo?: Date;
  /**
   * 総要員数
   */
  totalPeopleCount?: number;
  /**
   * 社員数
   */
  devProperCount?: number;
  /**
   * 業務委託
   */
  devEntrustCount?: number;
  /**
   * 開発経費
   */
  devAmount?: number;
  /**
   * 開発スケジュールコード
   */
  devSchCode?: string;
  /**
   * 開発スケジュール名
   */
  devSchName?: string;
  /**
   * 行編集フラグ
   */
  lineEditFlag?: string;
}
