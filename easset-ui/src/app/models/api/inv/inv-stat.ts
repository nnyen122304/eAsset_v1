/**
 * 棚卸データ作成情報クラス
 */
export class InvStat {

  /**
   *  会計年月
   */
  period?: string;

  /**
   *  会計年月名
   */
  periodName?: string;

  /**
   *  会社コード
   */
  companyCode?: string;

  /**
   *  作成日
   */
  createDate?: string;

  /**
   *  作成者社員番号 データ作成実行者
   */
  createStaffCode?: string;

  /**
   *  更新日
   */
  updateDate?: string;

  /**
   *  更新者社員番号
   */
  updateStaffCode?: string;

  /**
   *  データ作成ステータス 1:実行中,2:異常終了,3:正常終了
   */
  createStatus?: string;

  /**
   *  データ作成処理開始日
   */
  createStartDate?: Date;

  /**
   *  データ作成処理終了日
   */
  createEndDate?: Date;

  /**
   *  データ作成実行者社員番号
   */
  createExecStaffCode?: string;

  /**
   *  公開タイプ-有形固定資産 1:公開しない,2:公開する,3:未取込
   */
  fldTanPublicType?: string;

  /**
   *  依頼メール送信ステータス-有形固定資産 1:未送信,2:送信中,3:送信済
   */
  fldTanSendStatus?: string;

  /**
   *  依頼メール送信開始日-有形固定資産
   */
  fldTanSendStartDate?: Date;

  /**
   *  依頼メール送信終了日-有形固定資産
   */
  fldTanSendEndDate?: Date;

  /**
   *  公開タイプ-除去費用 1:公開しない,2:公開する,3:未取込
   */
  fldRoPublicType?: string;

  /**
   *  依頼メール送信ステータス-除去費用 1:未送信,2:送信中,3:送信済
   */
  fldRoSendStatus?: string;

  /**
   *  依頼メール送信開始日-除去費用
   */
  fldRoSendStartDate?: Date;

  /**
   *  依頼メール送信終了日-除去費用
   */
  fldRoSendEndDate?: Date;

  /**
   *  公開タイプ-無形固定資産 1:公開しない,2:公開する,3:未取込
   */
  fldIntPublicType?: string;

  /**
   *  依頼メール送信ステータス-無形固定資産 1:未送信,2:送信中,3:送信済
   */
  fldIntSendStatus?: string;

  /**
   *  依頼メール送信開始日-無形固定資産
   */
  fldIntSendStartDate?: Date;

  /**
   *  依頼メール送信終了日-無形固定資産
   */
  fldIntSendEndDate?: Date;

  /**
   *  公開タイプ-リース 1:公開しない,2:公開する,3:未取込
   */
  lePublicType?: string;

  /**
   *  依頼メール送信ステータス-リース 1:未送信,2:送信中,3:送信済
   */
  leSendStatus?: string;

  /**
   *  依頼メール送信開始日-リース
   */
  leSendStartDate?: Date;

  /**
   *  依頼メール送信終了日-リース
   */
  leSendEndDate?: Date;

  /**
   *  公開タイプ-レンタル 1:公開しない,2:公開する,3:未取込
   */
  rePublicType?: string;

  /**
   *  依頼メール送信ステータス-レンタル 1:未送信,2:送信中,3:送信済
   */
  reSendStatus?: string;

  /**
   *  依頼メール送信開始日-レンタル
   */
  reSendStartDate?: Date;

  /**
   *  依頼メール送信終了日-レンタル
   */
  reSendEndDate?: Date;

  /**
   *  公開タイプ-備品等 1:公開しない,2:公開する,3:未取込
   */
  equipPublicType?: string;

  /**
   *  依頼メール送信ステータス-備品等 1:未送信,2:送信中,3:送信済
   */
  equipSendStatus?: string;

  /**
   *  依頼メール送信開始日-備品等
   */
  equipSendStartDate?: Date;

  /**
   *  依頼メール送信終了日-備品等
   */
  equipSendEndDate?: Date;

  /**
   *  前回成功時：データ作成処理開始日
   */
  lastSuccessCreateStartDate?: Date;

  /**
   *  前回成功時：データ作成処理終了日
   */
  lastSuccessCreateEndDate?: Date;

  /**
   *  前回成功時：データ作成実行者社員番号
   */
  lastSuccessExecStaffCode?: string;

  /**
   *  データ作成ステータス名
   */
  createStatusName?: string;

  /**
   *  データ作成実行者名
   */
  createExecStaffName?: string;

  /**
   *  公開タイプ名-有形固定資産
   */
  fldTanPublicTypeName?: string;

  /**
   *  公開タイプ名-除去費用
   */
  fldRoPublicTypeName?: string;

  /**
   *  公開タイプ名-無形固定資産
   */
  fldIntPublicTypeName?: string;

  /**
   *  公開タイプ名-リース
   */
  lePublicTypeName?: string;

  /**
   *  公開タイプ名-レンタル
   */
  rePublicTypeName?: string;

  /**
   *  公開タイプ名-備品等
   */
  equipPublicTypeName?: string;

  /**
   *  依頼メール送信ステータス名-有形固定資産
   */
  fldTanSendStatusName?: string;

  /**
   *  依頼メール送信ステータス名-除去費用
   */
  fldRoSendStatusName?: string;

  /**
   *  依頼メール送信ステータス名-無形固定資産
   */
  fldIntSendStatusName?: string;

  /**
   *  依頼メール送信ステータス名-リース
   */
  leSendStatusName?: string;

  /**
   *  依頼メール送信ステータス名-レンタル
   */
  reSendStatusName?: string;

  /**
   *  依頼メール送信ステータス名-備品等
   */
  equipSendStatusName?: string;


}
