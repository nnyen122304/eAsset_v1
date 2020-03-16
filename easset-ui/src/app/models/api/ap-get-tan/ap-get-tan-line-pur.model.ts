export class ApGetTanLinePur {
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
   * 購入会社コード
   */
  getPurCompanyCode: string;

  /**
   * 購入会社名
   */
  getPurCompanyName: string;

  /**
   * 購入見積書番号
   */
  getPurEstimateNumbe: string;

  /**
   * 購入金額
   */
  getPurAmount: number;

  /**
   * 購入コメント
   */
  getPurComment: string;
}
