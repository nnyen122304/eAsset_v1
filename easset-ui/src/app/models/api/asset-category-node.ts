import {AssetCategory} from './asset-category';

/**
 * AssetCategoryノード
 */
export class AssetCategoryNode extends AssetCategory {

  /**
   * 親部署コード
   */
  parentAssetCategoryCode: string;

  /**
   * 項目一覧
   */
  items?: AssetCategoryNode[];
}
