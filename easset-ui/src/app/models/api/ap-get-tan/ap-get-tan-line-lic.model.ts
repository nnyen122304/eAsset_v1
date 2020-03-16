export class ApGetTanLineLic {

  /**
   * ライセンス明細識別番号
   */
  apGetTanLineLicId?: number;

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
   * 申請書番号
   */
  applicationId?: string;

  /**
   * 行シーケンス
   */
  lineSeq?: number;

  /**
   * ソフトウエアメーカーID
   */
  softwareMakerId?: number;

  /**
   * ソフトウエアメーカー名
   */
  softwareMakerName?: string;

  /**
   * ソフトウエアID
   */
  softwareId?: number;

  /**
   * ソフトウエア名
   */
  softwareName?: string;

  /**
   * 数量
   */
  quantity?: number;

  /**
   * 登録数量
   */
  registQuantity?: number;

  /**
   * ライセンス数区分 1:有限,2:無限
   */
  licQuantityType?: string;

  /**
   * ライセンス数区分名
   */
  licQuantityTypeName?: string;

  /**
   * ライセンス数
   */
  licQuantity?: number;
}
