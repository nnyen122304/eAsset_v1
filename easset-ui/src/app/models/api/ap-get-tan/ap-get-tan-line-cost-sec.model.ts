export class ApGetTanLineCostSec {

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
   * 経費負担会社コード
   */
  costCompanyCode: string;

  /**
   * 経費負担部課コード
   */
  costSectionCode: string;

  /**
   * 経費負担部課年度
   */
  costSectionYear: number;

  /**
   * 配分
   */
  costDist: number;

  /**
   * 経費負担会社名
   */
  costCompanyName: string;

  /**
   * 経費負担部課名
   */
  costSectionName: string;
}
