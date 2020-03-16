import { License } from './license.model';

/**
 * ライセンス
 */
export class LicenseLineUpg extends License {
  /**
   * ライセンス管理番号
   */
  licenseId?: string;

  /**
   * アップグレードライセンス管理番号
   */
  upgradeLicenseId?: string;

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
   * アップグレード数
   */
  licUpgradeQuantity?: number;

  /**
   * ソフトウェアメーカー名
   */

  softwareMakerName?: string;

  /**
   * ソフトウェア名
   */
  softwareName?: string;

  /**
   * ライセンスキー
   */
  licLicenseKey?: string;

  /**
   * ライセンス保有数
   */
  licQuantity?: number;

  /**
   * ライセンス有効数
   */
  licEnableQuantity?: number;

  /**
   * ライセンス消費数
   */
  licUseQuantity?: number;
}
