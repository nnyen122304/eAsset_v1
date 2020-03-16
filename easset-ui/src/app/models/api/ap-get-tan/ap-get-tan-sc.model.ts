import { ApGetTan } from './ap-get-tan.model';

/**
 * リファレンス実装用なのでJSDOC形式でコメントを記載していません
 */
export class ApGetTanSC extends ApGetTan {

  /**
   * 申請書番号複数
   */
  applicationIdPlural?: string;

  /**
   * 申請日From
   */
  apDateFrom?: Date;

  /**
   * 申請日To
   */
  apDateTo?: Date;

  /**
   * 登録申請作成完了を除外
   */
  excludeCompleteFlag?: string;

  /**
   * 納入予定日From
   */
  getDeliveryDateFrom?: Date;

  /**
   * 納入予定日To
   */
  getDeliveryDateTo?: Date;

  /**
   * 購入先名
   */
  getPurCompanyName?: string;

  /**
   * 購入コード
   */
  getPurCompanyCode?: string;

  /**
   * 取得金額From
   */
  // tslint:disable-next-line: no-any
  getTotalAmountFrom?: any;

  /**
   * 取得金額To
   */
  // tslint:disable-next-line: no-any
  getTotalAmountTo?: any;

  /**
   * 部署階層検索フラグ
   */
  includeSectionHierarchyFlag?: string;

  /**
   * 資産(機器)名
   */
  astName?: string;

  /**
   * メーカーコード
   */
  astMakerCode?: string;

  /**
   * メーカー
   */
  astMakerName?: string;

  /**
   * メーカー型番
   */
  astMakerModel?: string;

  /**
   * 資産プロジェクト
   */
  astPrjCode?: string;

  /**
   * 資産プロジェクト
   */
  astPrjName?: string;

  /**
   * ソフトウエアメーカーID
   */
  // tslint:disable-next-line: no-any
  softwareMakerId?: any;

  /**
   * ソフトウエアメーカー名
   */
  softwareMakerName?: string;

  /**
   * ソフトウエアID
   */
  // tslint:disable-next-line: no-any
  softwareId?: any;

  /**
   * ソフトウエア名
   */
  softwareName?: string;

  /**
   * 備考
   */
  description?: string;

  /**
   * 故障情報機器管理番号
   */
  failureAssetId?: string;

  /**
   * 故障情報機器管理番号複数
   */
  failureAssetIdPlural?: string;

  /**
   * 明細検索種別 1?: 資産(機器)明細存在、2?: ライセンス明細存在
   */
  lineExistsType?: string;

  /**
   * e申請書類ID複数
   */
  eappIdPlural?: string;

  /**
   * 購入先見積番番号複数
   */
  getPurEstimateNumberPlural?: string;

  /**
   * リース・レンタル見積番番号複数
   */
  getLeReEstimateNumberPlural?: string;
}
