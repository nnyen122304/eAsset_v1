/**
 * 概要説明   : サービス提供開始報告
 */
export class ApBgnInt {
  /**
   * 申請書番号
   */
  applicationId?: string;

  /**
   * 申請書バージョン
   */
  applicationVersion?: string;

  /**
   *  作成日
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
  version?: {};

  /**
   * 改定番号
   */
  displayVersion?: {};

  /**
   * 更新コメント
   */
  updateComment?: string;

  /**
   * e申請書類ID
   */
  eappId?: {};

  /**
   * 申請書ステータス
   * 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下,6:取下げ 汎用マスタ-AP_STATUS_GET_INT
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
   * 起票者社員名
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
   * 起票者所属部署名
   */
  apCreateSectionName?: string;

  /**
   * 起票者所属部署年度
   */
  apCreateSectionYear?: number;

  /**
   * 起票者連絡先TEL
   */
  apCreateTel?: string;

  /**
   * 申請者社員番号
   */
  apStaffCode?: string;

  /**
   * 申請者社員名
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
   * 申請部署名
   */
  apSectionName?: string;

  /**
   * 申請部署年度
   */
  apSectionYear?: number;

  /**
   *  申請者連絡先TEL
   */
  apTel?: string;

  /**
   * 課長/GL承認不要フラグ
   * 0:要承認、1:承認不要
   */
  apSkipApproveFlag?: string;

  /**
   *  取得形態 汎用マスタ-AP_GET_INT_TYPE
   */
  apGetType?: string;

  /**
   * 取得形態名
   */
  apGetTypeName?: string;

  /**
   * 資産名
   */
  astName?: string;

  /**
   * 資産プロジェクトコード
   */
  astPrjCode?: string;

  /**
   * 資産プロジェクト名
   */
  astPrjName?: string;

  /**
   * 資産プロジェクトタイプ
   */
  astPrjType?: string;

  /**
   * クラウド区分
   * 1:クラウド以外,2:クラウド
   */
  astCloudType?: string;

  /**
   * プロジェクトライフ
   */
  astPrjLife?: string;

  /**
   * 案件グループコード
   */
  astGroupCode?: string;

  /**
   *  案件グループ名
   */
  astGroupName?: string;

  /**
   * 機種コード
   */
  astMachineCode?: string;

  /**
   * プロダクトコード
   */
  astProductCode?: string;

  /**
   * 資産計上額合計(実際額)
   */
  astRealAmount?: number;

  /**
   * 資産計上額合計(申請額)
   */
  astAppAmount?: number;

  /**
   * 取得価額合計(実際額)
   */
  getRealAmount?: number;

  /**
   * 取得価額合計(申請額)
   */
  getAppAmount?: number;

  /**
   * 開発完了日
   */
  astCompleteDate?: Date;

  /**
   * リリース日・販売(サービス)開始日
   */
  astReleaseDate?: Date;

  /**
   * 分類 市販目的ソフトウェア用 汎用マスタ-AP_GET_INT_MKT_CATEGORY
   */
  mktCatCategoryCode?: string;

  /**
   * 分類名
   */
  mktCatCategoryName?: string;

  /**
   * 証憑の内容説明・内容・成果
   */
  astDescription?: string;
  /**
   *  添付補足
   */
  attDescription?: string;

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
   * 保有部署名
   */
  holSectionName?: string;

  /**
   * 保有部署年度
   */
  holSectionYear?: number;

  /**
   *  無形管理担当者
   */
  holStaffCode?: string;

  /**
   * 無形管理担当者名
   */
  holStaffName?: string;

  /**
   * 無形管理担当者会社コード
   */
  holStaffCompanyCode?: string;

  /**
   * 無形管理担当者会社名
   */
  holStaffCompanyName?: string;

  /**
   * 無形管理担当者部署コード
   */
  holStaffSectionCode?: string;

  /**
   * 無形管理担当者部署名
   */
  holStaffSectionName?: string;

  /**
   * 無形管理担当者部署年度
   */
  holStaffSectionYear?: {};

  /**
   * 代表設置場所
   */
  holRepOfficeCode?: string;

  /**
   * 代表設置場所名
   */
  holRepOfficeName?: string;

  /**
   * 販売管理費/原価区分
   * 1:販売管理費,2:原価
   */
  costType?: string;

  /**
   * 減価償却プロジェクトコード
   */
  costDepPrjCode?: string;

  /**
   * 減価償却プロジェクト名
   */
  costDepPrjName?: string;

  /**
   * 減価償却プロジェクトタイプ
   */
  costDepPrjType?: string;

  /**
   * 資産計上会社コード
   */
  costCompanyCode?: string;

  /**
   * 資産計上会社名
   */
  costCompanyName?: string;

  /**
   *  資産計上部課コード
   */
  costSectionCode?: string;

  /**
   * 資産計上部課年度
   */
  costSectionYear?: {};

  /**
   *  資産計上部課名
   */
  costSectionName?: string;

  /**
   * 備考_申請者記入欄
   */
  descriptionAp?: string;

  /**
   * 備考_受付担当者・管理者記入欄
   */
  descriptionAdmin?: string;

  /**
   * 承認日
   */
  approveDate?: Date;

  /**
   * 資産明細
   */
    // tslint:disable-next-line: no-any
  apBgnIntLineFldList?: any[];

  /**
   * 利益予測明細
   */
    // tslint:disable-next-line: no-any
  apBgnIntLineProfEstList?: any[];

  /**
   * 添付明細
   */
    // tslint:disable-next-line: no-any
  apBgnIntLineAttList?: any[];

  /**
   * 経費負担部署明細
   */
    // tslint:disable-next-line: no-any
  apBgnIntLineCostSecList?: any[];
}
