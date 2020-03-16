import { InvStat } from './inv-stat';

/**
 * 棚卸データ作成情報拡張クラス
 */
export class InvStatEx extends InvStat {

  /**
   *  会計年月
   */
  periodFormated?: string;

  /**
   *  作成日
   */
  createDateFormated?: string;

  /**
   *  更新日
   */
  updateDateFormated?: string;

  /**
   *  データ作成処理開始日
   */
  createStartDateFormated?: string;

  /**
   *  データ作成処理終了日
   */
  createEndDateFormated?: string;

  /**
   *  依頼メール送信開始日-有形固定資産
   */
  fldTanSendStartDateFormated?: string;

  /**
   *  依頼メール送信開始日-有形固定資産
   */
  fldTanSendEndDateFormated?: string;

  /**
   *  メール送信-有形固定資産
   */
  fldTanMail?: boolean;

  /**
   *  依頼メール送信開始日-除去費用
   */
  fldRoSendStartDateFormated?: string;

  /**
   *  依頼メール送信終了日-除去費用
   */
  fldRoSendEndDateFormated?: string;

  /**
   *  メール送信-除去費用
   */
  fldRoMail?: boolean;

  /**
   *  依頼メール送信開始日-無形固定資産
   */
  fldIntSendStartDateFormated?: string;

  /**
   *  依頼メール送信終了日-無形固定資産
   */
  fldIntSendEndDateFormated?: string;

  /**
   *  メール送信-無形固定資産
   */
  fldIntMail?: boolean;

  /**
   *  依頼メール送信開始日-リース
   */
  leSendStartDateFormated?: string;

  /**
   *  メール送信-リース
   */
  leMail?: boolean;

  /**
   *  依頼メール送信開始日-レンタル
   */
  reSendStartDateFormated?: string;

  /**
   *  依頼メール送信終了日-レンタル
   */
  reSendEndDateFormated?: string;

  /**
   *  メール送信-レンタル
   */
  reMail?: boolean;

  /**
   *  依頼メール送信開始日-備品等
   */
  equipSendStartDateFormated?: string;

  /**
   *  依頼メール送信終了日-備品等
   */
  equipSendEndDateFormated?: string;

  /**
   *  メール送信-備品等
   */
  equipMail?: boolean;

  /**
   *  前回成功時：データ作成処理開始日
   */
  lastSuccessCreateStartDateFormated?: string;

  /**
   *  前回成功時：データ作成処理終了日
   */
  lastSuccessCreateEndDateFormated?: string;


}
