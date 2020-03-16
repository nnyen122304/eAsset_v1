export class AssetLineInv {
  /**
   * 情報機器管理番号
   */
  assetId?: string;

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
   * 棚卸実施日
   */
  invDate?: Date;

  /**
   * オフィス 棚卸実施時点のオフィス
   */
  invOfficeNamel?: string;

  /**
   * コメント
   */
  invComment?: string;
}

