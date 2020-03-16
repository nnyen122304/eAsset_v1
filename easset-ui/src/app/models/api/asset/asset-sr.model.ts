import { Asset } from './asset.model';

export class AssetSR extends Asset {

  /**
   * 登録した情報機器管理番号(登録申請のみ使用する)
   */
  assetAssetId?: string;

  /**
   * OS名[代表]
   */
  osNameOne?: string;

  /**
   * MACアドレス[代表]
   */
  netMacAddressOne?: string;

  /**
   * IPアドレス[代表]
   */
  netIpAddressOne?: string;

  /**
   * リース・レンタル情報
   */
  /**
   * リース開始日
   */
  leaseStartDate?: Date;

  /**
   *  リース満了日
   */
  leaseEndDate?: Date;

  /**
   *   レンタル開始日
   */
  rentalStartDate?: Date;

  /**
   *   レンタル満了日
   */
  rentalEndDate?: Date;

  /**
   *   経費情報
   */
  /**
   *   経費負担部課コード
   */
  costSectionCode?: string;

  /**
   *   経費負担部課名称
   */
  costSectionName?: string;

  /**
   *   減価償却プロジェクトコード
   */
  costDepPrjCode?: string;

  /**
   *   減価償却プロジェクト名
   */
  costDepPrjName?: string;

  /**
   *   ダウンロード用
   */
  /**
   *   共有ユーザー明細
   */
  /**
   *  行シーケンス
   */
  comLineSeq?: number;

  /**
   * 共有者社員番号
   */
  comStaffCode?: string;

  /**
   *   共有者氏名
   */
  comStaffName?: string;

  /**
   *  共有者所属会社コード
   */
  comCompanyCode?: string;

  /**
   *  共有者所属会社名
   */
  comCompanyName?: string;

  /**
   *   共有者所属部署コード
   */
  comSectionCode?: string;

  /**
   *   共有者所属部署名
   */
  comSectionName?: string;

  /**
   *   使用開始日
   */
  comStartDate?: Date;

  /**
   *   OS明細
   */
  /**
   *   行シーケンス
   */
  osLineSeq?: number;

  /**
   *   OS名
   */
  osName?: string;

  /**
   *  OSコメント
   */
  osDescription?: string;

  /**
   *   ネットワーク明細
   */
  /**
   *   行シーケンス
   */
  netLineSeq?: number;

  /**
   *  MACアドレス
   */
  netMacAddress?: string;

  /**
   *   IPアドレス
   */
  netIpAddress?: string;

  /**
   * ネットワークコメント
   */
  netDescription?: string;

  /**
   *  棚卸明細
   */
  /**
   *   行シーケンス
   */
  invLineSeq?: number;

  /**
   *   棚卸実施日
   */
  invDate?: Date;

  /**
   * オフィス 棚卸実施時点のオフィス
   */
  invOfficeName?: string;

  /**
   *   コメント
   */
  invComment?: string;
  /**
   * vi check box
   */
  sel?: boolean;
}
