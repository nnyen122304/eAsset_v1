import { License } from './license.model';

/**
 * ライセンス
 */
export class LicenseSR extends License {
  /**
   * 登録したライセンス管理番号(登録申請のみ使用する)
   */
  licenseLicenseId?: string;

  /**
   * チェックボックス値(FLEXの画面のみ使用する)
   */
  chk?: boolean;
}
