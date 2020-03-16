import { License } from './license.model';

/**
 * ライセンス
 */
export class LicenseSC extends License {
  /**
   * 検索対象（1:自分の管轄部署が保有（使用）部署に設定されている資産(機器)、2:全ての資産(機器)）
   */
  searchScopeType?: string;

  /**
   * 部署アクセス権限用保有部署
   */
  holCompanyCodeALSection?: string;

  /**
   * ライセンス管理番号複数
   */

  licenseIdPlural?: string;

  /**
   * 割当情報機器管理番号
   */
  assetId?: string;

  /**
   * 割当情報機器管理番号複数
   */
  assetIdPlural?: string;

  /**
   * 契約番号複数
   */
  contractNumPlural?: string;

  /**
   * 取得申請書番号複数
   */
  getApplicationIdPlural?: string;

  /**
   * 登録申請書番号複数
   */
  registApplicationIdPlural?: string;

  /**
   * 登録日From
   */
  registDateFrom?: Date;

  /**
   * 登録日To
   */
  registDateTo?: Date;

  /**
   * シリアル番号複数
   */

  licSerialCodePlural?: string;

  /**
   * プロダクトKEY複数
   */
  licProductKeyPlural?: string;

  /**
   * 取得費用From
   */
  licAmountFrom?: number;

  /**
   * 取得費用To
   */
  licAmountTo?: number;

  /**
   * 他会社保有の資産（機器）を検索する
   */
  allCompanyFlag?: string;

  /**
   * 部署階層検索フラグ
   */
  includeSectionHierarchyFlag?: string;

  /**
   * ライセンス形態複数
   */
  licLicenseTypePlural?: string;

  /**
   * タームライセンス期間終了日From
   */
  trmEndDateFrom?: Date;

  /**
   * タームライセンス期間終了日To
   */
  trmEndDateTo?: Date;

  /**
   * 管理項目
   */
  dscAttribute?: string;

  // ライセンス登録申請検索条件
  /**
   * 申請日From
   */
  apDateFrom?: Date;

  /**
   * 申請日To
   */
  apDateTo?: Date;

  /**
   * 登録が完了している申請書を除外
   */
  notCompleteFlag?: string;

  // 割当検索条件
  /**
   * 情報機器保有部署コード
   */
  assetHolSectionCode?: string;

  /**
   * 情報機器保有会社コード
   */
  assetHolCompanyCode?: string;

  /**
   * 情報機器保有部署年度
   */
  assetHolSectionYear?: number;

  /**
   * 明細出力
   */

    // CSVダウンロード
  dowloadLineItem?: string;

  /**
   * タームライセンス期限通知日From
   */
  trmAlertDateFrom?: Date;

  /**
   * タームライセンス期限通知日To
   */
  trmAlertDateTo?: Date;

  /**
   * e申請書類ID複数
   */

  eappIdPlural?: string;

  // 固定資産、物件
  /**
   * 契約枝番複数
   */
  contractEdaPlural?: string;

  /**
   * 資産番号複数
   */
  shisanNumPlural?: string;

  /**
   * 関連資産も同時に検索する
   */
  relationShisanNumFlag?: string;

  /**
   * e申請検索判定フラグ
   */
  searchEapp?: string;
}
