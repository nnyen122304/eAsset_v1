export class CostScrLine {
  /**
   * 行シーケンス
   */
  scrLineSeq: number;

  /**
   * 作成日
   */
  createDate: Date;

  /**
   * 作成者社員番号
   */
  createStaffCode: string;

  /**
   * 更新日
   */
  updateDate: Date;

  /**
   * 更新者社員番号
   */
  updateStaffCode: string;

  /**
   * 会計年月
   */
  period: string;

  /**
   * 会社コード
   */
  companyCode: string;

  /**
   * 精査種別 1:有形固定資産,2:無形固定資産,3:リース,4:レンタル
   */
  scrType: string;

  /**
   * 精査ステータス 1:未実施,2:OK,3:NG
   */
  scrStatus: string;

  /**
   * コメント
   */
  scrComment: string;

  /**
   * 資産番号 有・無・リ・レ
   */
  astNum: string;

  /**
   * 契約番号 リ・レ
   */
  contractNum: string;

  /**
   * 契約枝番 リ・レ
   */
  contractSubNum: string;

  /**
   * 取得申請番号 有・無・リ・レ
   */
  applicationId: string;

  /**
   * 当年度：経費負担会社コード 有・無・リ・レ
   */
  costCompanyCode: string;

  /**
   * 当年度：経費負担部課コード 有・無・リ・レ
   */
  costSectionCode: string;

  /**
   * 当年度：配賦コード 有・無・リ・レ
   */
  costDistCode: string;

  /**
   * 当年度：販売管理費/原価区分 有・無・リ・レ 1:原価,2:販売管理費
   */
  costType: string;

  /**
   * 当年度：減価償却プロジェクトコード  有・無・リ・レ
   */
  costDepPrjCode: string;

  /**
   * 前年度：経費負担部課会社コード 有・無・リ・レ
   */
  costCompanyCodeOld: string;

  /**
   * 前年度：経費負担部課コード 有・無・リ・レ
   */
  costSectionCodeOld: string;

  /**
   * 前年度：配賦コード 有・無・リ・レ
   */
  costDistCodeOld: string;

  /**
   * オリックス区分 リ・レ 1:オリックス以外、2:オリックス3月請求有、3:オリックス4月以降契約
   */
  orixType: string;

  /**
   * 契約・固有番号 リ・レ
   */
  lldPpIdContract: number;

  /**
   * 物件・固有番号 リ・レ
   */
  lldPpIdAst: number;

  /**
   * 契約状態区分 リ・レ 1:通常、2:契約中途解約済、3:物件中途解約済、4:満了済、5:移行済、6:取消済
   */
  lldContractStatusType: string;

  /**
   * 物件状態区分 リ・レ 1:通常、2:契約中途解約済、3:物件中途解約済、4:満了済、5:移行済、6:取消済
   */
  lldAstStatusType: string;

  /**
   * リース・レンタル会社コード リ・レ
   */
  lldLeReCompanyCode: string;

  /**
   * リース・レンタル会社名 リ・レ
   */
  lldLeReCompanyName: string;

  /**
   * 物件名 リ・レ
   */
  lldAstName: string;

  /**
   * 契約区分 リ・レ 1:基本リース、2:再リース
   */
  lldContractType: string;

  /**
   * 開始日 リ・レ
   */
  lldContractStartDate: string;

  /**
   * 満了日 リ・レ
   */
  lldContractEndDate: string;

  /**
   * 期間(ヵ月) リ・レ
   */
  lldPeriodMonth: number;

  /**
   * 契約金額 リ・レ
   */
  lldContractAmount: number;

  /**
   * 均等金額 リ・レ
   */
  lldMonthAmount: number;

  /**
   * 資産・固有番号 有・無
   */
  fldPpId: number;

  /**
   * 資産管理区分 有・無 1:建設仮勘定、2:通常資産、3:少額資産、4:一括償却資産、5:簿外資産、6:研究開発費、8:リース資産(オンバランス)、A:除去費用
   */
  fldAstManageType: string;

  /**
   * 資産状態区分 有・無 1:通常､2:経費振替済、3:固定資産振替済、4:除却済、5:売却済､7:満了除却済、8:清算済
   */
  fldAstStatusType: string;

  /**
   * 仕入先コード 有・無
   */
  fldPurCompanyCode: string;

  /**
   * 仕入先名 有・無
   */
  fldPurCompanyName: string;

  /**
   * 資産名 有・無
   */
  fldAstName: string;

  /**
   * 中古区分 有・無 1:通常資産、2:中古資産
   */
  fldOldType: string;

  /**
   * 稼働日 有・無
   */
  fldStartDate: string;

  /**
   * 除売却日 有・無
   */
  fldEndDate: string;

  /**
   * 耐用年数 有・無
   */
  fldUsefulYear: number;

  /**
   * 取得価額 有・無
   */
  fldAstGetAmount: number;

  /**
   * 帳簿価額(4月末) 有・無
   */
  fldAstBookAmount: number;

  /**
   * 情報機器管理番号 有・リ・レ
   */
  assetId: string;

  /**
   * 資産(機器)名 有・リ・レ
   */
  astName: string;

  /**
   * メーカー型番  有・リ・レ
   */
  astMakerModel: string;

  /**
   * シリアル番号  有・リ・レ
   */
  astSerialCode: string;

  /**
   * OIR番号 有・リ・レ
   */
  astOir: string;

  /**
   * 管理区分 有・リ・レ 汎用マスタ-ASSET_MANAGE_TYPE
   */
  astManageType: string;

  /**
   * 当年度：使用者社員番号 有・リ・レ
   */
  useStaffCode: string;

  /**
   * 当年度：使用者所属会社コード 有・リ・レ
   */
  useStaffCompanyCode: string;

  /**
   * 当年度：使用者所属部署コード 有・リ・レ
   */
  useStaffSectionCode: string;

  /**
   * 当年度：使用者所属部署年度 有・リ・レ
   */
  useStaffSectionYear: string;

  /**
   * 前年度：使用者社員番号 有・リ・レ
   */
  useStaffCodeOld: string;

  /**
   * 前年度：使用者所属会社コード 有・リ・レ
   */
  useStaffCompanyCodeOld: string;

  /**
   * 前年度：使用者所属部署コード 有・リ・レ
   */
  useStaffSectionCodeOld: string;

  /**
   * 前年度：使用者所属部署年度 有・リ・レ
   */
  useStaffSectionYearOld: string;

  /**
   * 当年度：無形管理担当者社員番号 無
   */
  holStaffCode: string;

  /**
   * 当年度：無形管理担当者会社コード 無
   */
  holStaffCompanyCode: string;

  /**
   * 当年度：無形管理担当者部署コード 無
   */
  holStaffSectionCode: string;

  /**
   * 当年度：無形管理担当者部署年度 無
   */
  holStaffSectionYear: string;

  /**
   * 前年度：無形管理担当者社員番号 無
   */
  holStaffCodeOld: string;

  /**
   * 前年度：無形管理担当者会社コード 無
   */
  holStaffCompanyCodeOld: string;

  /**
   * 前年度：無形管理担当者部署コード 無
   */
  holStaffSectionCodeOld: string;

  /**
   * 前年度：無形管理担当者部署年度 無
   */
  holStaffSectionYearOld: string;

  /**
   * 精査担当部署設定更新日
   */
  scrDate: Date;

  /**
   * 精査担当部署更新者社員番号
   */
  scrStaffCode: string;

  /**
   * selScrStatus1
   */
  selScrStatus1?: boolean;

  /**
   * 精査ステータス1：OK
   */
  scrStatus1?: string;

  /**
   * 精査ステータス2：OK
   */
  selScrStatus2?: boolean;

  /**
   * 精査ステータス：NG
   */
  scrStatus2?: string | boolean;

  /**
   * 精査ステータス名 ※ CSVファイル出力用
   */
  scrStatusName: string;

  /**
   * 契約金額 ※ CSVファイル出力用：ハイフン表示
   */
  lldContractAmountStr: string;

  /**
   * 当年度：配賦セット
   */
  costDistSet: string;
  /**
   * 前年度：配賦セット
   */
  costDistSetOld: string;

  /**
   * 販管・原価区分名
   */
  costTypeName: string;

  /**
   * 当年度：経費負担部課会社名
   */
  costCompanyName: string;

  /**
   * 前年度：経費負担部課会社名
   */
  costCompanyNameOld: string;

  /**
   * 当年度：使用者所属会社名
   */
  useStaffCompanyName: string;

  /**
   * 前年度：使用者所属会社名
   */
  useStaffCompanyNameOld: string;

  /**
   * 当年度：無形固定資産管理者所属会社
   */
  holStaffCompanyName: string;

  /**
   * 前年度：無形固定資産管理者所属会社
   */
  holStaffCompanyNameOld: string;

  /**
   * 減価償却プロジェクト名
   */
  costDepPrjName: string;

  /**
   * 開始日(日付フォーマット)
   */
  lldContractStartDateF: string;

  /**
   * 満了日(日付フォーマット)
   */
  lldContractEndDateF: string;

  /**
   * 稼働日(日付フォーマット)
   */
  fldStartDateF: string;

  /**
   * 売却日(日付フォーマット)
   */
  fldEndDateF: string;

  /**
   * 当年度：経費負担部課名
   */
  costSectionName: string;

  /**
   * 前年度：経費負担部課名
   */
  costSectionNameOld: string;

  /**
   * 当年度：使用者名
   */
  useStaffName: string;

  /**
   * 前年度：使用者名
   */
  useStaffNameOld: string;

  /**
   * 当年度：無形無形固定資産管理者名
   */
  holStaffName: string;

  /**
   * 前年度：無形無形固定資産管理者名
   */
  holStaffNameOld: string;

  /**
   * 当年度：使用者所属部署名
   */
  useStaffSectionName: string;

  /**
   * 前年度：使用者所属部署名
   */
  useStaffSectionNameOld: string;

  /**
   * 当年度：無形固定資産管理部署名
   */
  holStaffSectionName: string;

  /**
   * 前年度：無形固定資産管理部署名
   */
  holStaffSectionNameOld: string;

  /**
   * 管理区分名
   */
  astManageTypeName: string;

  /**
   * 契約状態区分名
   */
  lldContractStatusTypeName: string;

  /**
   * 物件状態区分名
   */
  lldAstStatusTypeName: string;

  /**
   * 契約区分名
   */
  lldContractTypeName: string;

  /**
   * 中古区分名
   */
  fldOldTypeName: string;

  /**
   * 資産管理区分名
   */
  fldAstManageTypeName: string;

  /**
   * 資産状態区分名
   */
  fldAstStatusTypeName: string;

  /**
   * 移動申請:申請書番号
   */
  apChangeApplicationId: string;

  /**
   * 移動申請：申請ステータス
   */
  apChangeApStatus: string;

  /**
   * 移動申請：申請日
   */
  apChangeApDate: Date;

  /**
   * 移動申請：申請者社員番号
   */
  apChangeApStaffCode: string;

  /**
   * 移動申請：販管/原価区分
   */
  apChangeCostType: string;

  /**
   * 移動申請：減価償却プロジェクトコード
   */
  apChangeCostDepPrjCode: string;

  /**
   * 移動申請：資産計上部課会社コード
   */
  apChangeCostCompanyCode: string;

  /**
   * 移動申請：資産計上部課コード
   */
  apChangeCostSectionCode: string;

  /**
   * 移動申請：申請ステータス名
   */
  apChangeApStatusName: string;

  /**
   * 移動申請：申請者社員名
   */
  apChangeApStaffName: string;

  /**
   * 移動申請：販管/原価区分名
   */
  apChangeCostTypeName: string;

  /**
   * 移動申請：減価償却プロジェクト名
   */
  apChangeCostDepPrjName: string;

  /**
   * 移動申請：資産計上部課名
   */
  apChangeCostSectionName: string;

  /**
   * 移動申請：経費負担部課配分
   */
  apChangeCostSectionNameDist: string;

  /**
   * 精査部署コード
   */
  scrSectionCode: string;

  /**
   * 精査部署名
   */
  scrSectionName: string;
}
