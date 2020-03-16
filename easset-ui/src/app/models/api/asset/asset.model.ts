import { AssetLineComUsr } from './asset-line-com-usr.model';
import { AssetLineOs } from './asset-line-os.model';
import { AssetLineNetwork } from './asset-line-network.model';
import { AssetLineInv } from './asset-line-Inv.model';

export class Asset {

  /**
   * 登録申請番号
   */
  assetId?: string;

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
  apGetTanLineAstId?: number;

  /**
   * 取得申請明細No
   */
  apGetTanLineAstLineSeq?: number;

  /**
   * 更新コメント
   */
  updateComment?: string;

  /**
   * e申請書類ID
   */
  eappId?: number;

  /**
   * 申請書ステータス 1?:未申請,2?:申請中,3?:登録完了,4?:差戻し 汎用マスタ-AP_STATUS_ASSET
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
   *  申請者オフィス
   */
  apOfficeName?: string;

  /**
   * 資産(機器)名 情報機器等と同項目
   */
  astName?: string;

  /**
   * 資産(機器)大分類 情報機器等と同項目
   */
  astCategory1Code?: string;

  /**
   * 資産(機器)大分類名 情報機器等と同項目
   */
  astCategory1Name?: string;

  /**
   * 資産(機器)小分類 情報機器等と同項目
   */
  astCategory2Code?: string;

  /**
   * 資産(機器)小分類名 情報機器等と同項目
   */
  astCategory2Name?: string;

  /**
   * 資産(機器)数量管理区分
   */
  astQuantityManageType?: string;

  /**
   * 資産(機器)シリアル番号管理区分
   */
  astSerialManageType?: string;

  /**
   * 筐体/形状 情報機器等と同項目
   */
  astShapeCode?: string;

  /**
   * 筐体/形状名 情報機器等と同項目
   */
  astShapeName?: string;

  /**
   *  メーカーコード 情報機器等と同項目
   */
  astMakerCode?: string;

  /**
   * メーカー名 情報機器等と同項目
   */
  astMakerName?: string;

  /**
   * 販売元名 情報機器等と同項目
   */
  astShopName?: string;

  /**
   * メーカー型番 情報機器等と同項目
   */
  astMakerModel?: string;

  /**
   * シリアル番号 情報機器等と同項目
   */
  astSerialCode?: string;

  /**
   * 保証期限 情報機器等と同項目
   */
  astGuaranteeTermDate?: Date;

  /**
   * 取得形態 情報機器等と同項目
   */
  astGetType?: string;

  /**
   * 取得形態名 情報機器等と同項目
   */
  astGetTypeName?: string;

  /**
   * 情報システム部配備フラグ 情報機器等と同項目
   */
  astSystemSectionDeployFlag?: string;

  /**
   * 情報システム部配備フラグ名 情報機器等と同項目
   */
  astSystemSectionDeployFlagName?: string;

  /**
   * 資産区分 情報機器等と同項目
   */
  astAssetType?: string;

  /**
   * 資産区分名 情報機器等と同項目
   */
  astAssetTypeName?: string;

  /**
   * 資産保有者コード 情報機器等と同項目
   */
  astHolderCode?: string;

  /**
   * 資産保有者名 情報機器等と同項目
   */
  astHolderName?: string;

  /**
   * 管理区分 情報機器等と同項目
   */
  astManageType?: string;

  /**
   * 管理区分名 情報機器等と同項目
   */
  astManageTypeName?: string;

  /**
   * OIR番号有効フラグ 情報機器等と同項目
   */
  astOirEnable?: string;

  /**
   * OIR番号 情報機器等と同項目
   */
  astOir?: string;

  /**
   * 管理終了日 情報機器等と同項目
   */
  astManageEndDate?: Date;

  /**
   *  保有会社コード 情報機器等と同項目
   */
  holCompanyCode?: string;

  /**
   * 保有会社名 情報機器等と同項目
   */
  holCompanyName?: string;

  /**
   * 保有部署コード 情報機器等と同項目
   */
  holSectionCode?: string;

  /**
   * 保有部署年度 情報機器等と同項目
   */
  holSectionYear?: number;

  /**
   * 保有部署名 情報機器等と同項目
   */
  holSectionName?: string;

  /**
   * 資産管理担当者 情報機器等と同項目
   */
  holStaffCode?: string;

  /**
   * 資産管理担当者氏名 情報機器等と同項目
   */
  holStaffName?: string;

  /**
   * 設置場所 情報機器等と同項目
   */
  holOfficeCode?: string;

  /**
   * 設置場所名 情報機器等と同項目
   */
  holOfficeName?: string;

  /**
   * 設置場所階数 情報機器等と同項目
   */
  holOfficeFloor?: number;

  /**
   * 部屋番号 情報機器等と同項目
   */
  holOfficeRoomNum?: string;

  /**
   * ラック番号 情報機器等と同項目
   */
  holOfficeRackNum?: string;

  /**
   * 使用目的 情報機器等と同項目
   */
  holPurposeCode?: string;

  /**
   * 使用目的名 情報機器等と同項目
   */
  holPurposeName?: string;

  /**
   * 設置場所補足 情報機器等と同項目
   */
  holOfficeDescription?: string;

  /**
   * 取得(設置)者社員番号 情報機器等と同項目
   */
  holGetStaffCode?: string;

  /**
   * 取得(設置)者氏名 情報機器等と同項目
   */
  holGetStaffName?: string;

  /**
   * 取得(設置)者所属会社コード 情報機器等と同項目
   */
  holGetCompanyCode?: string;

  /**
   * 取得(設置)者所属会社名 情報機器等と同項目
   */
  holGetCompanyName?: string;

  /**
   * 取得(設置)者所属部署コード 情報機器等と同項目
   */
  holGetSectionCode?: string;

  /**
   * 取得(設置)者所属部署年度 情報機器等と同項目
   */
  holGetSectionYear?: number;

  /**
   * 取得(設置)者所属部署名 情報機器等と同項目
   */
  holGetSectionName?: string;

  /**
   * 取得(設置)日 情報機器等と同項目
   */
  holGetDate?: Date;

  /**
   * 数量 情報機器等と同項目
   */
  holQuantity?: number;

  /**
   * 使用会社コード 情報機器等と同項目
   */
  useCompanyCode?: string;

  /**
   * 使用会社名 情報機器等と同項目
   */
  useCompanyName?: string;

  /**
   * 使用部署コード 情報機器等と同項目
   */
  useSectionCode?: string;

  /**
   * 使用部署年度 情報機器等と同項目
   */
  useSectionYear?: number;

  /**
   * 使用部署名 情報機器等と同項目
   */
  useSectionName?: string;

  /**
   *  使用者社員番号 情報機器等と同項目
   */
  useStaffCode?: string;

  /**
   * 使用者氏名 情報機器等と同項目
   */
  useStaffName?: string;

  /**
   * 使用者所属会社コード 情報機器等と同項目
   */
  useStaffCompanyCode?: string;

  /**
   * 使用者所属会社名 情報機器等と同項目
   */
  useStaffCompanyName?: string;

  /**
   * 使用者所属部署コード 情報機器等と同項目
   */
  useStaffSectionCode?: string;

  /**
   * 使用者所属部署年度 情報機器等と同項目
   */
  useStaffSectionYear?: number;

  /**
   * 使用者所属部署名 情報機器等と同項目
   */
  useStaffSectionName?: string;

  /**
   * 使用開始日 情報機器等と同項目
   */
  useStartDate?: Date;

  /**
   * 共有ユーザーフラグ 情報機器等と同項目
   */
  useCommonFlag?: string;

  /**
   * 共有ユーザーフラグ名 情報機器等と同項目
   */
  useCommonFlagName?: string;

  /**
   *  ホスト名 情報機器等と同項目
   */
  netHostName?: string;

  /**
   * eGuard接続許可タイプ 情報機器等と同項目
   */
  netEguardPermitType?: string;

  /**
   * 保守契約番号 情報機器等と同項目
   */
  mntContractCode?: string;

  /**
   * 保守契約先 情報機器等と同項目
   */
  mntContractCompanyName?: string;

  /**
   * 保守契約期間FROM 情報機器等と同項目
   */
  mntContractStartDate?: Date;

  /**
   * 保守契約期間TO 情報機器等と同項目
   */
  mntContractEndDate?: Date;

  /**
   * 保守契約金額 情報機器等と同項目
   */
  mntContractAmount?: number;

  /**
   * 保守契約ユーザー登録番号 情報機器等と同項目
   */
  mntContractRegistCode?: string;

  /**
   * 保守契約登録日 情報機器等と同項目
   */
  mntContractRegistDate?: Date;

  /**
   * 保守契約担当者社員番号 情報機器等と同項目
   */
  mntContractStaffCode?: string;

  /**
   * 情報機器等と同項目
   */
  mntContractStaffName?: string;

  /**
   * 保守契約サービスレベル
   */
  mntContractServiceLevel?: string;

  /**
   * 保守契約備考
   */
  mntContractDescription?: string;

  /**
   * 保守契約番号2
   */
  mntContractCode2?: string;

  /**
   * 保守契約先2
   */
  mntContractCompanyName2?: string;

  /**
   * 保守契約期間FROM2
   */
  mntContractStartDate2?: Date;

  /**
   * 保守契約期間TO2
   */
  mntContractEndDate2?: Date;

  /**
   * 保守契約金額2
   */
  mntContractAmount2?: number;

  /**
   * 保守契約ユーザー登録番号2
   */
  mntContractRegistCode2?: string;

  /**
   * 保守契約登録日2
   */
  mntContractRegistDate2?: Date;

  /**
   * 保守契約担当者社員番号2
   */
  mntContractStaffCode2?: string;

  /**
   * 保守契約担当者氏名2
   */
  mntContractStaffName2?: string;

  /**
   * 保守契約サービスレベル2
   */
  mntContractServiceLevel2?: string;

  /**
   * 保守契約備考2
   */
  mntContractDescription2?: string;

  /**
   * 保守契約番号3
   */
  mntContractCode3?: string;

  /**
   * 保守契約先3
   */
  mntContractCompanyName3?: string;

  /**
   * 保守契約期間FROM3
   */
  mntContractStartDate3?: Date;

  /**
   * 保守契約期間TO3
   */
  mntContractEndDate3?: Date;

  /**
   * 保守契約金額3
   */
  mntContractAmount3?: number;

  /**
   * 保守契約ユーザー登録番号3
   */
  mntContractRegistCode3?: string;

  /**
   * 保守契約登録日3
   */
  mntContractRegistDate3?: Date;

  /**
   * 保守契約担当者社員番号3
   */
  mntContractStaffCode3?: string;

  /**
   * 保守契約担当者氏名3
   */
  mntContractStaffName3?: string;

  /**
   * 保守契約サービスレベル3
   */
  mntContractServiceLevel3?: string;

  /**
   * 保守契約備考3
   */
  mntContractDescription3?: string;

  /**
   * 備考 情報機器等と同項目
   */
  dscDescription?: string;

  /**
   * 管理項目1 情報機器等と同項目
   */
  dscAttribute1?: string;

  /**
   * 管理項目2 情報機器等と同項目
   */
  dscAttribute2?: string;

  /**
   * 管理項目3 情報機器等と同項目
   */
  dscAttribute3?: string;

  /**
   * 管理項目4 情報機器等と同項目
   */
  dscAttribute4?: string;

  /**
   *  管理項目5 情報機器等と同項目
   */
  dscAttribute5?: string;

  /**
   * 管理項目6 情報機器等と同項目
   */
  dscAttribute6?: string;

  /**
   * 管理項目7 情報機器等と同項目
   */
  dscAttribute7?: string;

  /**
   * 管理項目8 情報機器等と同項目
   */
  dscAttribute8?: string;

  /**
   *  管理項目9 情報機器等と同項目
   */
  dscAttribute9?: string;

  /**
   * 管理項目10 情報機器等と同項目
   */
  dscAttribute10?: string;

  /**
   *  管理項目11 情報機器等と同項目
   */
  dscAttribute11?: string;

  /**
   * 管理項目12 情報機器等と同項目
   */
  dscAttribute12?: string;

  /**
   *  管理項目13 情報機器等と同項目
   */
  dscAttribute13?: string;

  /**
   *  管理項目14 情報機器等と同項目
   */
  dscAttribute14?: string;

  /**
   *  管理項目15 情報機器等と同項目
   */
  dscAttribute15?: string;

  /**
   * 管理項目16 情報機器等と同項目
   */
  dscAttribute16?: string;

  /**
   *  管理項目17 情報機器等と同項目
   */
  dscAttribute17?: string;

  /**
   * 管理項目18 情報機器等と同項目
   */
  dscAttribute18?: string;

  /**
   * 管理項目19 情報機器等と同項目
   */
  dscAttribute19?: string;

  /**
   * 管理項目20 情報機器等と同項目
   */
  dscAttribute20?: string;

  /**
   * 故障交換元情報機器管理番号 情報機器等と同項目
   */
  dscFailureAssetId?: string;

  /**
   * 社内実地棚卸対象フラグ 情報機器等と同項目
   */
  invInCompanyActualFlag?: string;

  /**
   * 社内実地棚卸対象フラグ名 情報機器等と同項目
   */
  invInCompanyActualFlagName?: string;

  /**
   * バーコード情報 情報機器等と同項目
   */
  invBarcode?: string;

  /**
   * シール発行対象フラグ 情報機器等と同項目
   */
  invSealIssueFlag?: string;

  /**
   * シール発行対象フラグ名 情報機器等と同項目
   */
  invSealIssueFlagName?: string;

  /**
   * シール発行日 情報機器等と同項目
   */
  invSealIssueDate?: Date;

  /**
   * シール発行補足 情報機器等と同項目
   */
  invSealIssueDescription?: string;

  /**
   * 抹消フラグ 情報機器等と同項目
   */
  deleteFlag?: string;

  /**
   * 抹消日 情報機器等と同項目
   */
  deleteDate?: Date;

  /**
   * 抹消理由 情報機器等と同項目
   */
  deleteReason?: string;

  /**
   * 取得申請書番号 情報機器等と同項目
   */
  getApplicationId?: string;

  /**
   * 登録申請番号 情報機器等と同項目
   */
  registApplicationId?: string;

  /**
   * 契約番号 情報機器等と同項目
   */
  contractNum?: string;

  /**
   * DREAMS番号 情報機器等と同項目
   */
  dreamsNum?: string;

  /**
   * 親情報機器管理番号 情報機器等と同項目
   */
  parentAssetId?: string;

  /**
   *  起票者?:連絡先TEL
   */
  apCreateTel?: string;

  /**
   * 固定資産、物件
   */
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
   * vi check box
   */
  sel?: boolean;

  /**
   * 明細
   */
  /**
   * 情報機器等登録申請_共有ユーザー明細
   */
  assetLineComUsr?: AssetLineComUsr[];

  /**
   * 情報機器等登録申請_OS明細
   */
  assetLineOs?: AssetLineOs[];

  /**
   * 情報機器等登録申請_ネットワーク明細
   */
  assetLineNetwork?: AssetLineNetwork[];

  /**
   * 情報機器等_棚卸明細
   */
  assetLineInv?: AssetLineInv[];
}
