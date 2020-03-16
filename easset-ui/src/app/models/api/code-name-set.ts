/**
 * 汎用マスタ情報
 */
export class CodeNameSet {

  /**
   * カテゴリコード
   */
  categoryCode?: string;

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
   * 使用可能会社コード 全G会社共通マスタの場合は"000"、その他は使用可能会社をスペース区切りで設定（空白の場合は全G会社使用可）
   */
  useCompanyCode?: string;

  /**
   * 設定可能会社コード 各社がそれぞれマスタを管理する場合は"000",それ以外は主管理会社の会社コードを設定
   */
  setCompanyCode?: string;
  /**
   * 設定可能権限コード 複数権限可能な場合はスペース区切り
   */
  setRoleCode?: string;

  /**
   * 親カテゴリコード
   */
  parentCategoryCode?: string;

  /**
   * 行追加有効フラグ 0:行追加不可,1:行追加可
   */
  lineAddEnableFlag?: string;

  /**
   * 行削除有効フラグ 0:行追加不可,1:行追加可
   */
  lineDelEnableFlag?: string;

  /**
   * 備考
   */
  description?: string;

  /**
   * 設定可能会社名
   */
  setCompanyName?: string;

  /**
   * 作成者名
   */
  createStaffName?: string;

  /**
   * 最終更新者
   */
  updateStaffName?: string;
}
