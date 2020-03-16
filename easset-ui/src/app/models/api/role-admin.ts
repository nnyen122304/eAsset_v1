/**
 * ユーザー権限_全社権限検索
 */
export class RoleAdmin {
  /**
   *  社員番号
   */
  staffCode?: string;

  /**
   *  権限コード S00:ADMIN,A00:資産管理者,A01:資産管理者(総務),A02:資産管理者(財経),A03:資産管理者(情シス)
   */
  roleCode?: string;

  /**
   *  権限付与会社コード
   */
  companyCode?: string;

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
   *  更新者社員番号
   */
  updateStaffCode?: string;

  /**
   *  社員名
   */

  staffName?: string;

  /**
   *  作成者社員名
   */
  createStaffName?: string;

  /**
   *  更新者名
   */
  updateStaffName?: string;

  // ダウンロード用項目

  /**
   *  権限者メールアドレス
   */
  mailAddress?: string;

}
