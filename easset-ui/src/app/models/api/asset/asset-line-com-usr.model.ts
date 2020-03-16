export class AssetLineComUsr {

  /**
   * 登録申請番号
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
   * 共有者社員番号 情報機器等と同項目
   */
  comStaffCode?: string;

  /**
   * 共有者氏名 情報機器等と同項目
   */
  comStaffName?: string;

  /**
   * 共有者所属会社コード 情報機器等と同項目
   */
  comCompanyCode?: string;

  /**
   * 共有者所属会社名 情報機器等と同項目
   */
  comCompanyName?: string;

  /**
   * 共有者所属部署コード 情報機器等と同項目
   */
  comSectionCode?: string;

  /**
   * 共有者所属部署名 情報機器等と同項目
   */
  comSectionName?: string;

  /**
   * 使用開始日 情報機器等と同項目
   */
  comStartDate?: Date;
}
