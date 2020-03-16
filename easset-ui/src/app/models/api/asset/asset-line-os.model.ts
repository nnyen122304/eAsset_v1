export class AssetLineOs {
  /**
   * 情報機器等_OS明細ID NEA_ASSET_LINE_OS_Sシーケンス
   */
  assetLineOsId?: number;

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
   * 登録申請番号
   */
  assetId?: string;

  /**
   * 行シーケンス
   */
  lineSeq?: number;

  /**
   * OS名 情報機器等と同項目
   */
  osName?: string;

  /**
   * OSコメント 情報機器等と同項目
   */
  osDescription?: string;
}
