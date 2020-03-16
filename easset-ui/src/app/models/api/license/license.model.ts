import { LicenseLineQty } from './license-line-qty.model';
import { LicenseLineUpg } from './license-line-upg.model';
import { ApLicenseLineAtt } from './ap-license-line-att.model';

/**
 * ライセンス
 */
export class License {
  /**
   * check box
   */
  sel?: boolean;

  /**
   * ライセンス管理番号
   */
  licenseId?: string;

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
   * バージョン
   */
  version?: number;

  /**
   * 取得申請紐付け
   */
  apGetTanLineLicId?: number;

  /**
   * 取得申請明細No
   */
  apGetTanLineLicLineSeq?: number;

  /**
   * 更新コメント
   */
  updateComment?: string;

  /**
   * e申請書類ID
   */
  eappId?: number;

  /**
   * 申請書ステータス 1:未申請,2:申請中,3:登録完了,4:差戻し 汎用マスタ-AP_STATUS_ASSET
   */
  apStatus?: string;

  /**
   * 申請書ステータス名
   */
  apStatusName?: string;

  /**
   * 申請日
   */
  apDate?: Date;

  /**
   * 起票者社員番号
   */
  apCreateStaffCode?: string;

  /**
   * 起票者氏名
   */
  apCreateStaffName?: string;

  /**
   * 起票者所属会社コード
   */
  apCreateCompanyCode?: string;

  /**
   * 起票者所属会社名
   */
  apCreateCompanyName?: string;

  /**
   * 起票者所属部署コード
   */
  apCreateSectionCode?: string;

  /**
   * 起票者所属部署年度
   */
  apCreateSectionYear?: number;

  /**
   * 起票者所属部署名
   */
  apCreateSectionName?: string;

  /**
   * 申請者社員番号
   */
  apStaffCode?: string;

  /**
   * 申請者氏名
   */
  apStaffName?: string;

  /**
   * 申請会社コード
   */
  apCompanyCode?: string;

  /**
   * 申請会社名
   */
  apCompanyName?: string;

  /**
   * 申請部署コード
   */
  apSectionCode?: string;

  /**
   * 申請部署年度
   */
  apSectionYear?: number;

  /**
   * 申請部署名
   */
  apSectionName?: string;

  /**
   * 連絡先TEL
   */
  apTel?: string;

  /**
   * 申請者オフィス
   */
  apOfficeName?: string;

  /**
   * ソフトウェアメーカー名
   */
  softwareMakerName?: string;

  /**
   * ソフトウェア名
   */
  softwareName?: string;

  /**
   * ソフトウェアメーカーID
   */
  softwareMakerId?: number;

  /**
   * ソフトウェアID
   */
  softwareId?: number;

  /**
   * シリアル番号
   */
  licSerialCode?: string;

  /**
   * プロダクトKEY
   */
  licProductKey?: string;

  /**
   * ライセンスキー
   */
  licLicenseKey?: string;

  /**
   * メディア形態
   */
  licMediaType?: string;

  /**
   * メディア形態名
   */
  licMediaTypeName?: string;

  /**
   * 資産区分
   */
  licAssetType?: string;

  /**
   * 資産区分名
   */
  licAssetTypeName?: string;

  /**
   * 取得形態
   */
  licGetType?: string;

  /**
   * 取得形態名
   */
  licGetTypeName?: string;

  /**
   * ハードウェアバンドルフラグ
   */
  licHrdBandleFlag?: string;

  /**
   * ハードウェアバンドルフラグ名
   */
  licHrdBandleFlagName?: string;

  /**
   * 販売元
   */
  licShopName?: string;

  /**
   * 取得費用
   */
  licAmount?: number;

  /**
   * ライセンス形態
   */
  licLicenseType?: string;

  /**
   * ライセンス形態名
   */
  licLicenseTypeName?: string;

  /**
   * 割当可能フラグ
   */
  enableAllocFlag?: string;

  /**
   * アップグレードライセンスフラグ
   */
  licUpgradeFlag?: string;

  /**
   * アップグレードライセンスフラグ名
   */
  licUpgradeFlagName?: string;

  /**
   * タームライセンスフラグ
   */
  licTermFlag?: string;

  /**
   * タームライセンスフラグ名
   */
  licTermFlagName?: string;

  /**
   * ボリュームタイプ
   */
  licVolumeType?: string;

  /**
   * ボリュームタイプ名
   */
  licVolumeTypeName?: string;

  /**
   * 使用許諾書有無フラグ
   */
  licUseConsentFlag?: string;

  /**
   * 使用許諾書有無フラグ名
   */
  licUseConsentFlagName?: string;

  /**
   * ダウングレード許諾有無フラグ
   */
  licDowngradeConsentFlag?: string;

  /**
   * ダウングレード許諾有無フラグ名
   */
  licDowngradeConsentFlagName?: string;

  /**
   * ライセンス数タイプ
   */
  licQuantityType?: string;

  /**
   * ライセンス数タイプ名
   */
  licQuantityTypeName?: string;

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

  /**
   * ライセンスアップグレード数
   */
  licUpgradeToQuantity?: number;

  /**
   * ライセンス使用状況管理ファイルID
   */
  licUseFileId?: string;

  /**
   * ライセンス使用状況管理ファイルID 保存前の一時ファイル
   */
  licUseFileIdTmp?: string;

  /**
   * ライセンス使用状況管理ファイル名
   */
  licUseFileName?: string;

  /**
   * タームライセンス：契約区分
   */
  trmContractType?: string;

  /**
   * タームライセンス：契約区分名
   */
  trmContractTypeName?: string;

  /**
   * タームライセンス：親ライセンス管理番号
   */
  trmParentLicenseId?: string;

  /**
   * タームライセンス：開始日
   */
  trmStartDate?: Date;

  /**
   * タームライセンス：終了日
   */
  trmEndDate?: Date;

  /**
   * タームライセンス：期限通知日
   */
  trmAlertDate?: Date;

  /**
   * タームライセンス：自動割当切替
   */
  trmAutoAllocFlag?: string;

  /**
   * タームライセンス：自動割当切替名
   */
  trmAutoAllocFlagName?: string;

  /**
   * 保有会社コード
   */
  holCompanyCode?: string;

  /**
   * 保有会社名
   */
  holCompanyName?: string;

  /**
   * 保有部署コード
   */
  holSectionCode?: string;

  /**
   * 保有部署年度
   */
  holSectionYear?: number;

  /**
   * 保有部署名
   */
  holSectionName?: string;

  /**
   * 資産管理担当者
   */
  holStaffCode?: string;

  /**
   * 資産管理担当者氏名
   */
  holStaffName?: string;

  /**
   * 使用許諾区分
   */
  useType?: string;

  /**
   * 使用許諾区分名
   */
  useTypeName?: string;

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
   * 他部署への貸出
   */
  useRentalFlag?: string;

  /**
   * 他部署への貸出名
   */
  useRentalFlagName?: string;

  /**
   * 保守契約番号
   */
  mntContractCode?: string;

  /**
   * 保守契約先
   */
  mntContractCompanyName?: string;

  /**
   * 保守契約期間FROM
   */
  mntContractStartDate?: Date;

  /**
   * 保守契約期間TO
   */
  mntContractEndDate?: Date;

  /**
   * 保守契約金額
   */
  mntContractAmount?: number;

  /**
   * 保守契約ユーザー登録番号
   */
  mntContractRegistCode?: string;

  /**
   * 保守契約登録日
   */
  mntContractRegistDate?: Date;

  /**
   * 保守契約担当者社員番号
   */
  mntContractStaffCode?: string;

  /**
   * 保守契約担当者氏名
   */
  mntContractStaffName?: string;

  /**
   * 備考
   */
  dscDescription?: string;

  /**
   * 管理項目1
   */
  dscAttribute1?: string;

  /**
   * 管理項目2
   */
  dscAttribute2?: string;

  /**
   * 管理項目3
   */
  dscAttribute3?: string;

  /**
   * 管理項目4
   */
  dscAttribute4?: string;

  /**
   * 管理項目5
   */
  dscAttribute5?: string;

  /**
   * 管理項目6
   */
  dscAttribute6?: string;

  /**
   * 管理項目7
   */
  dscAttribute7?: string;

  /**
   * 管理項目8
   */
  dscAttribute8?: string;

  /**
   * 管理項目9
   */
  dscAttribute9?: string;

  /**
   * 管理項目10
   */
  dscAttribute10?: string;

  /**
   * 管理項目11
   */
  dscAttribute11?: string;

  /**
   * 管理項目12
   */
  dscAttribute12?: string;

  /**
   * 管理項目13
   */
  dscAttribute13?: string;

  /**
   * 管理項目14
   */
  dscAttribute14?: string;

  /**
   * 管理項目15
   */
  dscAttribute15?: string;

  /**
   * 管理項目16
   */
  dscAttribute16?: string;

  /**
   * 管理項目17
   */
  dscAttribute17?: string;

  /**
   * 管理項目18
   */
  dscAttribute18?: string;

  /**
   * 管理項目19
   */
  dscAttribute19?: string;

  /**
   * 管理項目20
   */
  dscAttribute20?: string;

  /**
   * 抹消フラグ
   */
  deleteFlag?: string;

  /**
   * 抹消日
   */
  deleteDate?: Date;

  /**
   * 抹消理由
   */
  deleteReason?: string;

  /**
   * 取得申請書番号
   */
  getApplicationId?: string;

  /**
   * 取得申請(無形)バージョン
   */
  getApplicationVersion?: string;

  /**
   * 登録申請番号
   */
  registApplicationId?: string;

  /**
   * 契約番号
   */
  contractNum?: string;

  /**
   * 親ライセンス管理番号
   */
  parentLicenseId?: string;

  /**
   * 起票者:連絡先TEL
   */
  apCreateTel?: string;

  /**
   * 添付補足
   */
  attDescription?: string;

  // 固定資産、物件
  /**
   * 契約枝番
   */
  contractEda?: string;

  /**
   * 資産番号
   */
  shisanNum?: string;

  /**
   * 物件の固有番号(画面に表示しない)
   */
  koyunoL?: number;

  /**
   * 資産の固有番号(画面に表示しない)
   */
  koyunoF?: number;

  /**
   * 資産区分(1:有形資産、2:無形資産、3:生物、 4:繰延資産)
   */
  shisanKbn?: string;

  // 明細
  /**
   * ライセンス_数量管理明細_貸出
   */
  licenseLineQtyRental?: LicenseLineQty[];

  /**
   * ライセンス_アップグレード元明細
   */
  licenseLineUpgSrc?: LicenseLineUpg[];

  /**
   * ライセンス_アップグレード先明細
   */
  licenseLineUpgDst?: LicenseLineUpg[];

  /**
   * ライセンス登録申請_添付明細
   */
  apLicenseLineAtt?: ApLicenseLineAtt[];

  // 許諾（Flexへの連携無し）
  /**
   * ライセンス_数量管理明細_許諾
   */
  licenseQtyUse?: LicenseLineQty;
}
