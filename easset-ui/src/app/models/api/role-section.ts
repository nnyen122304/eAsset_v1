/**
 * ユーザー権限_部署(資産管理担当者)権限検索
 */
export class RoleSection {

  /**
   *  社員番号
   */
  staffCode?: string;

  /**
   *  権限付与会社コード
   */
  companyCode?: string;

  /**
   *  権限付与部署コード
   */
  sectionCode?: string;

  /**
   *  権限付与部署年度
   */
  sectionYear?: number;

  /**
   *  作成日
   */
  createDate?: Date;

  /**
   *  作成者社員番号
   */
  createStaffCode?: string;

  /**
   *  更新日
   */
  updateDate?: Date;

  /**
   *  更新者社員番号 0:公開する,1:公開しない
   */
  updateStaffCode?: string;

  /**
   *  公開フラグ
   */
  publicFlag?: string;

  /**
   *  公開コメント
   */
  publicComment?: string;

  /**
   *  社員名
   */
  staffName?: string;

  /**
   *  作成者社員名
   */
  createStaffName?: string;

  /**
   * 更新者社員名
   */
  updateStaffName?: string;

  /**
   *  公開フラグ名
   */
  publicFlagName?: string;

  /**
   *  権限部署名
   */
  sectionName?: string;


  /**
   *  権限コード
   */
  roleCode?: string;

  /**
   *  ドメインID
   */
  domainId?: string;

  /**
   *  退職日
   */
  retiredDay?: string;

  // ダウンロード用項目

  /**
   *  権限者メールアドレス
   */
  mailAddress?: string;
}
