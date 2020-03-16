import { User } from './api/user';

/**
 * セッション情報
 */
export class SessionInfo {
  /**
   * ログインユーザー
   */
  loginUser: User;
  /**
   * ログイン会社コード
   */
  loginCompanyCode: string;
  /**
   * ログイン会社名
   */
  loginCompanyName: string;
  /**
   * ログイン権限コード一覧
   */
  loginRoleCodeList: string[];
  /**
   * ログイン権限名
   */
  loginRoleName: string;
  /**
   * 選択されているメニュー
   */
  currentMenuId: string;
  /**
   * 選択されているメニューのアクセスレベル
   */
  currentAccessLevel: string;
  /**
   * 検索結果最大表示件数
   */
  maxSearchRowCount: number;
  /**
   * カレント年度
   */
  currentYear: number;
  /**
   * ファイル保存ルートパス
   */
  fileSaveRootPath: string;
}
