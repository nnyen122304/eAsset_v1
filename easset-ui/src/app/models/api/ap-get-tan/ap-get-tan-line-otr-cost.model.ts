export class ApGetTanLineOtrCost {

  /**
   * 申請書番号
   */
  applicationId: string;

  /**
   * 行シーケンス
   */
  lineSeq: number;

  /**
   * 作成日
   */
  createDate: Date;

  /**
   * 作成者社員番号
   */
  createStaffCode: string;

  /**
   * 更新日
   */
  updateDate: Date;

  /**
   * 更新者社員番号
   */
  updateStaffCode: string;

  /**
   * 金額
   */
  getOtrAmount: number;

  /**
   * コメント
   */
  getOtrComment: string;
}
