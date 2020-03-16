export class AssetCategory {
  /**
   * 分類種別（ASSET_CATEGORY1：大分類、ASSET_CATEGORY2：小分類）
   */
  categorySegment?: string;
  /**
   * 分類コード
   */
  categoryCode?: string;
  /**
   * 分類名
   */
  categoryName?: string;
  /**
   * 親分類コード
   */
  parentCategoryCode?: string;
  /**
   * 親分類名
   */
  parentCategoryName?: string;
  /**
   * 小分類-数量管理
   */
  childCategoryValue2?: string;
  /**
   * 小分類-シンクライアント
   */
  childCategoryValue3?: string;
  /**
   * 小分類-シール発行有無
   */
  childCategoryValue4?: string;
  /**
   * 小分類-シリアル管理
   */
  childCategoryValue5?: string;
}
