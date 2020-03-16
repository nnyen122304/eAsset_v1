/**
 * メニューITEM
 */
export class MenuItem {
  /**
   * メニューID
   */
  menuId: string;
  /**
   * メニュー名
   */
  name: string;
  /**
   * 画面フレームタイトル
   */
  frameTitle: string;
  /**
   * 機能表示パス
   */
  path: string;
  /**
   * 子メニュー
   */
  items?: MenuItem[];
}
