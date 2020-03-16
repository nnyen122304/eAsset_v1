import { License } from './license.model';

/**
 * ライセンス
 */
export class LicenseLineQty extends License {
  /**
   * ライセンス管理番号
   */
  licenseId?: string;

  /**
   * ライセンス数量管理明細区分 1:許諾部署(1ライセンス：1明細),2:貸出部署(1ライセンス：N明細)
   */
  licenseLineQtyType?: string;

  /**
   * 行シーケンス ライセンス管理番号,ライセンス数量管理明細区分ごとのSEQ
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
   * ライセンス数量管理明細ID
   */
  licenseLineQtyId?: number;

  /**
   * 使用会社コード
   */
  useCompanyCode?: string;

  /**
   * 使用会社名
   */
  useCompanyName?: string;

  /**
   * 使用部署コード
   */
  useSectionCode?: string;

  /**
   * 使用部署年度
   */
  useSectionYear?: number;

  /**
   * 使用部署名
   */
  useSectionName?: string;

  /**
   * ライセンス有効数(貸出数)
   */
  licLineEnableQuantity?: number;

  /**
   * ライセンス消費数
   */
  licLineUseQuantity?: number;
}
