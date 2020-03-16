import { CodeName } from './code-name';

/**
 * コードネーム拡張クラス
 */
export class CodeNameEx extends CodeName {

  /**
   * 削除フラグ
   */
  deleteFlagFormat?: boolean;

  /**
   * 依頼メール送信チェック
   */
  isSendMail?: boolean;
}
