export class ApGetInt {
  /**
   * チェックボックス値
   */
  chk?: boolean;

  /**
   * 申請書番号
   */
  applicationId?: string;

  /**
   * 申請書バージョン
   */
  applicationVersion?: string;

  /**
   * 作成日
   */
  createDate: Date;

  /**
   * 作成者社員番号
   */
  createStaffCode?: string;

  /**
   * 更新日
   */
  updateDate: Date;

  /**
   * 更新者社員番号
   */
  updateStaffCode?: string;

  /**
   * バージョン
   */
  version?: number;

  /**
   * 改定番号
   */
  displayVersion?: number;

  /**
   * 更新コメント
   */
  updateComment?: string;

  /**
   * e申請書類ID
   */
  eappId?: number;

  /**
   * 申請書ステータス 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下,6:取下げ 汎用マスタ-AP_STATUS_GET_INT
   */
  apStatus?: string;

  /**
   * 申請書ステータス名
   */
  apStatusName?: string;

  /**
   * 申請日
   */
  apDate: Date;

  /**
   * 取得形態 汎用マスタ-AP_GET_INT_TYPE
   */
  apGetType?: string;

  /**
   *  取得形態名
   */
  apGetTypeName?: string;

  /**
   * 取得金額範囲 汎用マスタ-AP_GET_INT_AMOUNT_RANGE
   */
  apGetAmountRange?: string;

  /**
   * 取得金額範囲名
   */
  apGetAmountRangeName?: string;

  /**
   * 取得金額範囲経費負担部署使用設定
   */
  apGetAmountRangeUseCostSecType?: string;

  /**
   * 取得金額範囲明細使用設定
   */
  apGetAmountRangeUseLineType?: string;

  /**
   * 取得金額範囲明細資産区分 1:資産、2:費用
   */
  apGetAmountRangeUseCostType?: string;

  /**
   * 稟議書・経営会議等承認済フラグ 0:未承認、1:承認済
   */
  specialApproveFlag?: string;

  /**
   * 稟議書・経営会議等承認日
   */
  specialApproveDate: Date;

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
   * 申請者連絡先TEL
   */
  apTel?: string;

  /**
   * 課長/GL承認不要フラグ 0:要承認、1:承認不要
   */
  apSkipApproveFlag?: string;

  /**
   * 要CIO審査フラグ 0:不要,1:必要
   */
  apNeedCioJudgeFlag?: string;

  /**
   * 取得時期 0:その他、1:先行申請
   */
  astGetTimeFlag?: string;

  /**
   * 資産名
   */
  astName?: string;

  /**
   * クラウド区分 1:クラウド以外,2:クラウド
   */
  astCloudType?: string;

  /**
   * 案件グループコード
   */
  astGroupCode?: string;

  /**
   * 案件グループ名
   */
  astGroupName?: string;

  /**
   * 取得（再申請）理由
   */
  astGetReason?: string;

  /**
   * リリース・検収（納品）・開発完了予定日
   */
  astCompletePlanDate: Date;

  /**
   * 費用削減効果・収益獲得効果 1:有,2:無,3:不明
   */
  astEffectType?: string;

  /**
   * 費用削減・収益獲得金額
   */
  astEffectAmount?: number;

  /**
   * 費用削減効果備考
   */
  astEffectDescription?: string;

  /**
   * 賃借物件据付費用 0:不要,1:必要
   */
  astRentFlag?: string;

  /**
   * リース・レンタル取得申請書番号
   */
  astRentApplicationId?: string;

  /**
   * 契約月数
   */
  astRentMonth?: number;

  /**
   * 賃貸予定期間FROM
   */
  astRentDateFrom: Date;

  /**
   * 賃貸予定期間TO
   */
  astRentDateTo: Date;

  /**
   * 賃借物件据付費用備考
   */
  astRentDescription?: string;

  /**
   * 資産計上金額合計
   */
  astTotalAmount?: number;

  /**
   * 取得金額合計
   */
  getTotalAmount?: number;

  /**
   * 分類 市販目的ソフトウェア用 汎用マスタ-AP_GET_INT_MKT_CATEGORY
   */
  mktCatCategoryCode?: string;

  /**
   * 分類名
   */
  mktCatCategoryName?: string;

  /**
   * タスク【会計計上区分】 市販目的ソフトウェア用 汎用マスタ-AP_GET_INT_MKT_TASK
   */
  mktCatTaskCode?: string;

  /**
   * タスク名
   */
  mktCatTaskName?: string;

  /**
   * 利益予測フラグ
   */
  mktProfEstFlag?: string;

  /**
   * 資産プロジェクトコード 市販目的ソフトウェア用
   */
  mktAstProjectCode?: string;

  /**
   * 資産プロジェクト名 市販目的ソフトウェア用
   */
  mktAstProjectName?: string;

  /**
   * 資産プロジェクトタイプ 市販目的ソフトウェア用
   */
  mktAstProjectType?: string;

  /**
   * 製品の概要 市販目的ソフトウェア用
   */
  mktAstProductOutline?: string;

  /**
   * 市場性 市販目的ソフトウェア用
   */
  mktAstMarket?: string;

  /**
   * マーケティング方針 市販目的ソフトウェア用
   */
  mktAstPolicy?: string;

  /**
   * 次期開発完了予定日表示フラグ 市販目的ソフトウェア用
   */
  mktDevNextPlanDateFlag?: string;

  /**
   * 次期開発完了予定日 市販目的ソフトウェア用
   */
  mktDevNextPlanDate?: Date;

  /**
   * 添付補足
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
   * 無形管理担当者
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
  holStaffSectionYear?: number;

  /**
   * 代表設置場所
   */
  holRepOfficeCode?: string;

  /**
   * 代表設置場所名
   */
  holRepOfficeName?: string;

  /**
   * 販売管理費/原価区分 1:販売管理費,2:原価
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
   * 資産計上部課コード
   */
  costSectionCode?: string;

  /**
   * 資産計上部課年度
   */
  costSectionYear?: number;

  /**
   * 資産計上部課名
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
   * サービス提供開始報告完了フラグ 0:登録残有り,1:完了
   */
  reportCompleteFlag?: string;

  /**
   * 督促メール送信日付
   */
  reminderDate: Date;

  /**
   * 承認日
   */
  approveDate: Date;

  /**
   * アクティブフラグ 0:旧バージョン,1:最新バージョン
   */
  activeFlag?: string;

  /**
   * 前バージョンからの変更項目
   */
  verUpColumnName?: string;

  /**
   * バージョンアップタイプ
   */
  verUpType?: string;

  // ライセンス、ライセンス登録申請用
  /**
   * 資産区分
   */
  licAssetType?: string;

  /**
   * 資産区分名
   */
  licAssetTypeName?: string;

  // 明細
  /**
   * 資産明細
   */
  apGetIntLineFldList: [];

  /**
   * 開発スケジュール明細
   */
  apGetIntLineDevSchList: [];

  /**
   * その他費用明細
   */
  apGetIntLineOtrCostList: [];

  /**
   * 利益予測明細
   */
  apGetIntLineProfEstList: [];

  /**
   * 添付明細
   */
  apGetIntLineAttList: [];

  setData(data) {
    this.applicationId = data.applicationId;
    this.applicationVersion = data.applicationVersion;
    this.createDate = data.createDate;
    this.createStaffCode = data.createStaffCode;
    this.updateDate = data.updateDate;
    this.updateStaffCode = data.updateStaffCode;
    this.version = data.version;
    this.displayVersion = data.displayVersion;
    this.updateComment = data.updateComment;
    this.eappId = data.eappId;
    this.apStatus = data.apStatus;
    this.apStatusName = data.apStatusName;
    this.apDate = data.apDate;
    this.apGetType = data.apGetType;
    this.apGetTypeName = data.apGetTypeName;
    this.apGetAmountRange = data.apGetAmountRange;
    this.apGetAmountRangeName = data.apGetAmountRangeName;
    this.apGetAmountRangeUseCostSecType = data.apGetAmountRangeUseCostSecType;
    this.apGetAmountRangeUseLineType = data.apGetAmountRangeUseLineType;
    this.apGetAmountRangeUseCostType = data.apGetAmountRangeUseCostType;
    this.specialApproveFlag = data.specialApproveFlag;
    this.specialApproveDate = data.specialApproveDate;
    this.apCreateStaffCode = data.apCreateStaffCode;
    this.apCreateStaffName = data.apCreateStaffName;
    this.apCreateCompanyCode = data.apCreateCompanyCode;
    this.apCreateCompanyName = data.apCreateCompanyName;
    this.apCreateSectionCode = data.apCreateSectionCode;
    this.apCreateSectionName = data.apCreateSectionName;
    this.apCreateSectionYear = data.apCreateSectionYear;
    this.apCreateTel = data.apCreateTel;
    this.apStaffCode = data.apStaffCode;
    this.apStaffName = data.apStaffName;
    this.apCompanyCode = data.apCompanyCode;
    this.apCompanyName = data.apCompanyName;
    this.apSectionCode = data.apSectionCode;
    this.apSectionName = data.apSectionName;
    this.apSectionYear = data.apSectionYear;
    this.apTel = data.apTel;
    this.apSkipApproveFlag = data.apSkipApproveFlag;
    this.apNeedCioJudgeFlag = data.apNeedCioJudgeFlag;
    this.astGetTimeFlag = data.astGetTimeFlag;
    this.astName = data.astName;
    this.astCloudType = data.astCloudType;
    this.astGroupCode = data.astGroupCode;
    this.astGroupName = data.astGroupName;
    this.astGetReason = data.astGetReason;
    this.astCompletePlanDate = data.astCompletePlanDate;
    this.astEffectType = data.astEffectType;
    this.astEffectAmount = data.astEffectDescription;
    this.astEffectDescription = data.astEffectDescription;
    this.astRentFlag = data.astRentFlag;
    this.astRentApplicationId = data.astRentApplicationId;
    this.astRentMonth = data.astRentMonth;
    this.astRentDateFrom = data.astRentDateFrom;
    this.astRentDateTo = data.astRentDateTo;
    this.astRentDescription = data.astRentDescription;
    this.astTotalAmount = data.astTotalAmount;
    this.getTotalAmount = data.getTotalAmount;
    this.mktCatCategoryCode = data.mktCatCategoryCode;
    this.mktCatCategoryName = data.mktCatCategoryName;
    this.mktCatTaskCode = data.mktCatTaskCode;
    this.mktCatTaskName = data.mktCatTaskName;
    this.mktProfEstFlag = data.mktProfEstFlag;
    this.mktAstProjectCode = data.mktAstProjectCode;
    this.mktAstProjectName = data.mktAstProjectName;
    this.mktAstProjectType = data.mktAstProjectType;
    this.mktAstProductOutline = data.mktAstProductOutline;
    this.mktAstMarket = data.mktAstMarket;
    this.mktAstPolicy = data.mktAstPolicy;
    this.mktDevNextPlanDateFlag = data.mktDevNextPlanDateFlag;
    this.mktDevNextPlanDate = data.mktDevNextPlanDate;
    this.attDescription = data.attDescription;
    this.holCompanyCode = data.holCompanyCode;
    this.holCompanyName = data.holCompanyName;
    this.holSectionCode = data.holSectionCode;
    this.holSectionName = data.holSectionName;
    this.holSectionYear = data.holSectionYear;
    this.holStaffCode = data.holStaffCode;
    this.holStaffName = data.holStaffName;
    this.holStaffCompanyCode = data.holStaffCompanyCode;
    this.holStaffCompanyName = data.holStaffCompanyName;
    this.holStaffSectionCode = data.holStaffSectionCode;
    this.holStaffSectionName = data.holStaffSectionName;
    this.holStaffSectionYear = data.holStaffSectionYeare;
    this.holRepOfficeCode = data.holRepOfficeCode;
    this.holRepOfficeName = data.holRepOfficeName;
    this.costType = data.costType;
    this.costDepPrjCode = data.costDepPrjCode;
    this.costDepPrjName = data.costDepPrjName;
    this.costDepPrjType = data.costDepPrjType;
    this.costCompanyCode = data.costCompanyCode;
    this.costCompanyName = data.costCompanyName;
    this.costSectionCode = data.costSectionCode;
    this.costSectionYear = data.costSectionYear;
    this.costSectionName = data.costSectionName;
    this.descriptionAp = data.descriptionAp;
    this.descriptionAdmin = data.descriptionAdmin;
    this.reportCompleteFlag = data.reportCompleteFlag;
    this.reminderDate = data.reminderDate;
    this.approveDate = data.approveDate;
    this.activeFlag = data.activeFlag;
    this.verUpColumnName = data.verUpColumnName;
    this.verUpType = data.verUpType;
    this.licAssetType = data.licAssetType;
    this.licAssetTypeName = data.licAssetTypeName;
    this.apGetIntLineFldList = data.apGetIntLineFldList;
    this.apGetIntLineDevSchList = data.apGetIntLineDevSchList;
    this.apGetIntLineOtrCostList = data.apGetIntLineOtrCostList;
    this.apGetIntLineProfEstList = data.apGetIntLineProfEstList;
    this.apGetIntLineAttList = data.apGetIntLineAttList;
    return this;
  }
}
