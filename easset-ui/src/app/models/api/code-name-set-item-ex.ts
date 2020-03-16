import { CodeNameSetItem } from './code-name-set-item';

/**
 * 汎用マスタ設定項目検索拡張クラス
 */
export class CodeNameSetItemEx extends CodeNameSetItem {

  /**
   * コンボボックス用フォーマット済みデータ
   */
  cmbDataFormated?: {
    data?: string | number,
    label?: string
  }[];

}
