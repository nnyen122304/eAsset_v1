/**
 * 汎用マスタ設定項目検索
 */

export class CodeNameSetItem {

  /**
   * カテゴリコード
   */
  categoryCode?: string;

  /**
   * 項目シーケンス
   */
  itemSeq?: number;

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
   * 項目名
   */
  itemName?: string;

  /**
   * 項目入力種別 TXT:テキスト,TXTA:テキストエリア,NUM:数値,CMB:コンボボックス
   */
  itemInputType?: string;

  /**
   * 注釈
   */
  itemComment?: string;

  /**
   * 項目幅
   */
  width?: number;

  /**
   * 項目高
   */
  height?: number;

  /**
   * 項目表示専用設定 true:表示専用、false:以外
   */
  displayOnly?: string;

  /**
   * 項目読取専用設定 true:読取専用、false:以外
   */
  readOnly?: string;

  /**
   * 項目必須設定 true:必須、false:以外
   */
  required?: string;

  /**
   * 項目最大入力文字数
   */
  maxChars?: number;

  /**
   * 項目入力文字制限
   */
  restrict?: string;

  /**
   * 項目編集ロック true:ロック、false:以外
   */
  editLock?: string;

  /**
   * コンボボックス用データ
   */
  cmbData?: string;

  /**
   * コンボボックス用ラベル
   */
  cmbLabel?: string;

}
