/**
 * ユーザー
 */
export class User {
  /**
   * アカウント
   */
  account?: string;
  /**
   * 社員番号
   */
  staffCode?: string;
  /**
   * 氏名
   */
  name?: string;
  /**
   * 会社コード
   */
  companyCode?: string;
  /**
   *  会社名称
   */
  companyName?: string;
  /**
   * 所属部署コード
   */
  sectionCode?: string;
  /**
   * 所属部署名称
   */
  sectionName?: string;
  /**
   * 電話番号
   */
  tel1?: string;
  /**
   * オフィスコード
   */
  officeCode?: string;
  /**
   * オフィス名
   */
  officeName?: string;
  /**
   * メールアドレス
   */
  mailAddress?: string;
  /**
   * FAX番号
   */
  fax?: string;
  /**
   * 住所
   */
  address?: string;
  /**
   * 電話番号(内線なし)
   */
  tel?: string;

  /**
   * 社員検索表示所属部署名称
   */
  lovDispName?: string;
}
