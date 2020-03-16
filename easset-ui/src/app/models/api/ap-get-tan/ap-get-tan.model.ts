import { ApGetTanLinePur } from './ap-get-tan-line-pur.model';
import { ApGetTanLineOtrCost } from './ap-get-tan-line-otr-cost.model';
import { ApGetTanLineAtt } from './ap-get-tan-line-att.model';
import { ApGetTanLineCostSec } from './ap-get-tan-line-cost-sec.model';
import { ApGetTanLineAst } from './ap-get-tan-line-ast.model';
import { ApGetTanLineLic } from './ap-get-tan-line-lic.model';

/**
 * リファレンス実装用なのでJSDOC形式でコメントを記載していません
 */
export class ApGetTan {

  /**
   * 申請書番号
   */
  applicationId?: string;

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
  // tslint:disable-next-line: no-any
  version?: any;

  /**
   * 改定番号
   */
  // tslint:disable-next-line: no-any
  displayVersion?: any;

  /**
   * 更新コメント
   */
  updateComment?: string;

  /**
   * e申請書類ID
   */
  // tslint:disable-next-line: no-any
  eappId?: any;

  /**
   * 申請書ステータス 1?: 未申請,2?: 申請中,3?: 承認済,4?: 差戻し,5?: 却下,6?: 取下げ
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
   * 取得形態
   */
  apGetType?: string;

  /**
   * 取得形態名
   */
  apGetTypeName?: string;

  /**
   * 取得金額範囲
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
   * 稟議書・経営会議等承認済フラグ 0?: 未承認、1?: 承認済
   */
  specialApproveFlag?: string;

  /**
   * 稟議書・経営会議等承認日
   */
  specialApproveDate?: Date;

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
  // tslint:disable-next-line: no-any
  apCreateSectionYear?: any;

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
   * 申請部署名
   */
  apSectionName?: string;

  /**
   * 申請部署年度
   */
  // tslint:disable-next-line: no-any
  apSectionYear?: any;

  /**
   * 連絡先TEL
   */
  apTel?: string;

  /**
   * 課長/GL承認不要フラグ 0?: 要承認、1?: 承認不要
   */
  apSkipApproveFlag?: string;

  /**
   * 登録申請担当者社員番号
   */
  apRegistStaffCode?: string;

  /**
   * 登録申請担当者社員名
   */
  apRegistStaffName?: string;

  /**
   * 登録申請担当者所属会社コード
   */
  apRegistCompanyCode?: string;

  /**
   * 登録申請担当者所属会社名
   */
  apRegistCompanyName?: string;

  /**
   * 登録申請担当者所属部署コード
   */
  apRegistSectionCode?: string;

  /**
   * 登録申請担当者所属部署名
   */
  apRegistSectionName?: string;

  /**
   * 登録申請担当者所属部署年度
   */
  // tslint:disable-next-line: no-any
  apRegistSectionYear?: any;

  /**
   * 取得目的コード
   */
  getPurposeCode?: string;

  /**
   * 取得目的名
   */
  getPurposeName?: string;

  /**
   * 取得理由
   */
  getReason?: string;

  /**
   * 案件名
   */
  getItemName?: string;

  /**
   * クラウド区分 1?: クラウド以外,2?: クラウド
   */
  getItemCloudType?: string;

  /**
   * 案件グループコード
   */
  getItemGroupCode?: string;

  /**
   * 案件グループ名
   */
  getItemGroupName?: string;

  /**
   * 納入予定日
   */
  getDeliveryDate?: Date;

  /**
   * 返却予定日
   */
  getReturnDate?: Date;

  /**
   * 要CIO審査フラグ 0?: 不要,1?: 必要
   */
  getNeedCioJudgeFlag?: string;

  /**
   * 情報システム部配備フラグ 0?: 情報システム部配備以外,1?: 情報システム部配備
   */
  getSystemSectionDeployFlag?: string;

  /**
   * 社内実地棚卸対象フラグ 0?: 対象外,1?: 対象
   */
  getIntraInventoryFlag?: string;

  /**
   * リース/レンタル会社コード
   */
  getLeReCompanyCode?: string;

  /**
   * リース/レンタル会社名
   */
  getLeReCompanyName?: string;

  /**
   * リース/レンタル見積番号
   */
  getLeReEstimateNumber?: string;

  /**
   * リース/レンタルコメント
   */
  getLeReComment?: string;

  /**
   * リース/レンタル月額
   */
  // tslint:disable-next-line: no-any
  getLeReMonthAmount?: any;

  /**
   * リース/レンタル月数
   */
  // tslint:disable-next-line: no-any
  getLeReCount?: any;

  /**
   * リース/レンタル総額
   */
  // tslint:disable-next-line: no-any
  getLeReTotalAmount?: any;

  /**
   * リース書類ID(e申請リース見積依頼書)
   */
  getLeEappId?: string;

  /**
   * 取得金額合計
   */
  // tslint:disable-next-line: no-any
  getTotalAmount?: any;

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
  // tslint:disable-next-line: no-any
  holSectionYear?: any;

  /**
   * 資産管理担当者
   */
  holStaffCode?: string;

  /**
   * 資産管理担当者名
   */
  holStaffName?: string;

  /**
   * 設置場所
   */
  holOfficeCode?: string;

  /**
   * 設置場所名
   */
  holOfficeName?: string;

  /**
   * 設置場所社外フラグ
   */
  holOfficeOutsideFlag?: string;

  /**
   * 設置場所階数
   */
  // tslint:disable-next-line: no-any
  holOfficeFloor?: any;

  /**
   * 社外常設PC申請書番号
   */
  holOfficeOutsidePcId?: string;

  /**
   * 設置場所補足
   */
  holOfficeDescription?: string;

  /**
   * 販売管理費/原価区分 1?: 販売管理費,2?: 原価
   */
  costType?: string;

  /**
   * 減価償却プロジェクトコード未定フラグ 0?: 確定,1?: 未定
   */
  costDepPrjUndecidedFlag?: string;

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
   * 備考_申請者記入欄
   */
  descriptionAp?: string;

  /**
   * 備考_受付担当者・管理者記入欄
   */
  descriptionAdmin?: string;

  /**
   * 明細修正許可フラグ 0?: 許可しない,1?: 許可する
   */
  lineEditApproveFlag?: string;

  /**
   * 登録申請停止フラグ 0?: 停止しない,1?: 停止する
   */
  stopRegistFlag?: string;

  /**
   * 登録完了フラグ 0?: 登録残有り,1?: 完了
   */
  registCompleteFlag?: string;

  /**
   * 受付承認フラグ 0?: 未承認,1?: 承認済
   */
  approveSuperiorFlag?: string;

  /**
   * 承認日
   */
  approveDate?: Date;

  /**
   * 起票者?: 連絡先TEL
   */
  // tslint:disable-next-line: no-any
  apCreateTel?: any;

  /**
   * 資産計上会社コード
   */
  costCompanyCode?: string;

  /**
   * 資産計上部課コード
   */
  costSectionCode?: string;

  /**
   * 資産計上部課年度
   */
  // tslint:disable-next-line: no-any
  costSectionYear?: any;

  /**
   * 資産計上部課名
   */
  costSectionName?: string;

  /**
   * リース/レンタル1ヶ月未満の金額
   */
  // tslint:disable-next-line: no-any
  getLeReMonthLessAmount?: any;

  /**
   * レンタル注文書?: 受付番号
   */
  reoReceptNumber?: string;

  /**
   * レンタル注文書?: 注文書区分 1?: 推奨機,2?: 推奨機以外,3?: フラットレンタル,4?: シンクライアント
   */
  reoOrderType?: string;

  /**
   * レンタル注文書?: 資産管理担当者社員コード
   */
  reoAstStaffCode?: string;

  /**
   * レンタル注文書?: 資産管理担当者所属会社コード
   */
  reoAstCompanyCode?: string;

  /**
   * レンタル注文書?: 資産管理担当者所属部署コード
   */
  reoAstSectionCode?: string;

  /**
   * レンタル注文書?: 資産管理担当者所属部署年度
   */
  // tslint:disable-next-line: no-any
  reoAstSectionYear?: any;

  /**
   * レンタル注文書?: 資産管理担当者電話番号
   */
  reoAstTel?: string;

  /**
   * レンタル注文書?: 資産管理担当者FAX番号
   */
  reoAstFax?: string;

  /**
   * レンタル注文書?: 資産管理担当者メールアドレス
   */
  reoAstMailAddress?: string;

  /**
   * レンタル注文書?: 使用希望開始日
   */
  reoUseHopeStartDate?: Date;

  /**
   * レンタル注文書?: 請求書送付先担当者住所
   */
  reoInvAddress?: string;

  /**
   * レンタル注文書?: 請求書送付先担当者電話番号
   */
  reoInvTel?: string;

  /**
   * レンタル注文書?: 請求書送付先担当者FAX番号
   */
  reoInvFax?: string;

  /**
   * レンタル注文書?: 請求書送付先担当者メールアドレス
   */
  reoInvMailAddress?: string;

  /**
   * レンタル注文書?: 納品先担当者社員コード
   */
  reoDlvStaffCode?: string;

  /**
   * レンタル注文書?: 納品先担当者所属会社コード
   */
  reoDlvCompanyCode?: string;

  /**
   * レンタル注文書?: 納品先担当者所属部署コード
   */
  reoDlvSectionCode?: string;

  /**
   * レンタル注文書?: 納品先担当者所属部署年度
   */
  // tslint:disable-next-line: no-any
  reoDlvSectionYear?: any;

  /**
   * レンタル注文書?: 納品先担当者住所
   */
  reoDlvAddress?: string;

  /**
   * レンタル注文書?: 納品先担当者電話番号
   */
  reoDlvTel?: string;

  /**
   * レンタル注文書?: 納品先担当者FAX番号
   */
  reoDlvFax?: string;

  /**
   * レンタル注文書?: 納品先担当者メールアドレス
   */
  reoDlvMailAddress?: string;

  /**
   * レンタル注文書?: 備考
   */
  reoDescription?: string;

  /**
   * レンタル注文書?: 発注日
   */
  reoOrderDate?: Date;

  /**
   * レンタル注文書?: 発注フラグ 0?: 未発注、1?: 発注
   */
  reoOrderFlag?: string;

  /**
   * レンタル注文書?: 請求書送付先入力タイプ 1?: 自動指定、2?: 手入力
   */
  reoInvStaffInputType?: string;

  /**
   * レンタル注文書?: 請求書送付先担当者社員名
   */
  reoInvStaffName?: string;

  /**
   * レンタル注文書?: 請求書送付先担当者所属会社名
   */
  reoInvCompanyName?: string;

  /**
   * レンタル注文書?: 請求書送付先担当者所属部署名
   */
  reoInvSectionName?: string;

  /**
   * レンタル注文書?: 請求書送付先担当者所属会社名称
   */
  reoInvCompanyOfficailName?: string;

  /**
   * レンタル注文書?: 発注抑止フラグ
   */
  reoDisuseFlag?: string;

  /**
   * リース/レンタル日数
   */
  // tslint:disable-next-line: no-any
  getLeReDateCount?: any;

  /**
   * 現機種OIR番号
   */
  reoAstOir?: string;

  /**
   * レンタル注文書?: 注文書区分名
   */
  reoOrderTypeName?: string;

  /**
   * レンタル注文書?: 資産管理担当者名
   */
  reoAstStaffName?: string;

  /**
   * レンタル注文書?: 納品担当者名
   */
  reoDlvStaffName?: string;

  /**
   * レンタル注文書?: 資産管理担当者会社名
   */
  reoAstCompanyName?: string;

  /**
   * レンタル注文書?: 納品担当者会社名
   */
  reoDlvCompanyName?: string;

  /**
   * レンタル注文書?: 資産管理担当者所属部署名
   */
  reoAstSectionName?: string;

  /**
   * レンタル注文書?: 納品担当者所属部署名
   */
  reoDlvSectionName?: string;

  /**
   * 購入明細
   */
  apGetTanLinePurList?: ApGetTanLinePur[];

  /**
   * その他費用明細
   */
  apGetTanLineOtrCostList?: ApGetTanLineOtrCost[];

  /**
   * 添付明細
   */
  apGetTanLineAttList?: ApGetTanLineAtt[];

  /**
   * 経費負担部署明細
   */
  apGetTanLineCostSecList?: ApGetTanLineCostSec[];

  /**
   * 資産(機器)明細
   */
  apGetTanLineAstList?: ApGetTanLineAst[];

  /**
   * ライセンス明細
   */
  apGetTanLineLicList?: ApGetTanLineLic[];

  /**
   * 見積もり番号
   */
  getPurEstimateNumber?: string;

  /**
   * 見積もりフラグ
   */
  estimateFlag?: string;
}
