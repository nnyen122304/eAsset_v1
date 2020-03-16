import { Asset } from './asset.model';

export class AssetSC extends Asset {

  /**
   * 検索対象（1?:自分が使用者となっている資産(機器)、2?:全ての資産(機器)
   */
  searchScopeType?: string;

  /**
   * 部署アクセス権限用保有部署
   */
  holCompanyCodeALSection?: string;

  /**
   * 情報機器管理番号複数
   */
  assetIdPlural?: string;

  /**
   * 親情報機器管理番号複数
   */
  parentAssetIdPlural?: string;

  /**
   * 関連（親子）データも同時に検索する
   */
  levelAssetFlag?: string;

  /**
   * 取得申請書番号複数
   */
  getApplicationIdPlural?: string;

  /**
   * 登録申請書番号複数
   */
  registApplicationIdPlural?: string;

  /**
   * 契約番号複数
   */
  contractNumPlural?: string;

  /**
   * 登録日From
   */
  registDateFrom?: Date;

  /**
   * 登録日To
   */
  registDateTo?: Date;

  /**
   * 更新日From
   */
  updateDateFrom?: Date;

  /**
   * 更新日To
   */
  updateDateTo?: Date;

  /**
   * 抹消日From
   */
  deleteDateFrom?: Date;

  /**
   * 抹消日To
   */
  deleteDateTo?: Date;

  /**
   * 資産番号複数
   */
  shisanNumPlural?: string;

  /**
   * 資産（機器）分類複数
   */
  astCategory2Plural?: string;

  /**
   * シリアル番号複数
   */
  astSerialCodePlural?: string;

  /**
   * OIR番号複数
   */
  astOirPlural?: string;

  /**
   * 資産区分複数
   */
  astAssetTypePlural?: string;

  /**
   * 管理区分複数
   */
  astManageTypePlural?: string;

  /**
   * 他会社保有の資産（機器）を検索する
   */
  allCompanyFlag?: string;

  /**
   * 部署階層検索フラグ
   */
  includeSectionHierarchyFlag?: string;

  /**
   * 資設置（使用）場所複数
   */
  holOfficePlural?: string;

  /**
   * MACアドレス1
   */
  netMacAddress1?: string;

  /**
   * MACアドレス2
   */
  netMacAddress2?: string;

  /**
   * MACアドレス3
   */
  netMacAddress3?: string;

  /**
   * MACアドレス4
   */
  netMacAddress4?: string;

  /**
   * MACアドレス5
   */
  netMacAddress5?: string;

  /**
   * MACアドレス6
   */
  netMacAddress6?: string;

  /**
   * IPアドレス1
   */
  netIpAddress1?: string;

  /**
   * IPアドレス2
   */
  netIpAddress2?: string;

  /**
   * IPアドレス3
   */
  netIpAddress3?: string;

  /**
   * IPアドレス4
   */
  netIpAddress4?: string;

  /**
   * ホスト名複数
   */
  netHostNamePlural?: string;

  /**
   * 管理項目
   */
  dscAttribute?: string;

  /**
   * シール発行（0：未発行、1：発行済）
   */
  invSealIssueStatus?: string;

  /**
   * シール発行日From
   */
  invSealIssueDateFrom?: Date;

  /**
   * シール発行日To
   */
  invSealIssueDateTo?: Date;

  /**
   * 棚卸実施状況（0：未実施、1：実施済）
   */
  invFlag?: string;

  /**
   * 検索対象期間From
   */
  invSearchDateFrom?: Date;

  /**
   * 検索対象期間To
   */
  invSearchDateTo?: Date;

  /**
   * 保守契約開始日From
   */
  mntContractStartDateFrom?: Date;

  /**
   * 保守契約開始日To
   */
  mntContractStartDateTo?: Date;

  /**
   * 保守契約終了日From
   */
  mntContractEndDateFrom?: Date;

  /**
   * 保守契約終了日To
   */
  mntContractEndDateTo?: Date;

  /**
   * 情報機器登録申請検索条件
   */
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

  /**
   * e申請書類ID複数
   */
  eappIdPlural?: string;

  /**
   * CSVダウンロード
   */
  /**
   * 明細出力
   */
  dowloadLineItem?: string;

  /**
   * e申請検索判定フラグ
   */
  searchEapp?: string;

  /**
   * 固定資産、物件
   */
  /**
   * 契約枝番複数
   */
  contractEdaPlural?: string;
  /**
   * 関連資産も同時に検索する
   */
  relationShisanNumFlag?: string;
}
