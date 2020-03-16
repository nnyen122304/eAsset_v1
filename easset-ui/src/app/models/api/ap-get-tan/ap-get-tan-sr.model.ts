import { ApGetTan } from './ap-get-tan.model';
import { Inject, LOCALE_ID } from '@angular/core';
import { formatDate } from '@angular/common';

/**
 * リファレンス実装用なのでJSDOC形式でコメントを記載していません
 */
export class ApGetTanSR extends ApGetTan {

  /**
   * 稟議書・経営会議等承認済表示
   */
  specialApproveFlagName?: string;

  /**
   * 課長/GL承認不要表示
   */
  apSkipApproveFlagName?: string;

  /**
   * クラウド区分表示
   */
  getItemCloudTypeName?: string;

  /**
   * 要CIO審査表示
   */
  getNeedCioJudgeFlagName?: string;

  /**
   * 情報システム部配備表示
   */
  getSystemSectionDeployFlagName?: string;

  /**
   * 社内実地棚卸対象表示
   */
  getIntraInventoryFlagName?: string;

  /**
   * 販売管理費/原価区分名
   */
  costTypeName?: string;

  /**
   * 減価償却プロジェクトコード未定表示
   */
  costDepPrjUndecidedFlagName?: string;

  /**
   * 明細修正許可表示
   */
  lineEditApproveFlagName?: string;

  /**
   * 登録申請停止表示
   */
  stopRegistFlagName?: string;

  /**
   * 登録完了表示
   */
  registCompleteFlagName?: string;

  /**
   * 資産(機器)名
   */
  astNameOne?: string;

  /**
   * メーカー
   */
  astMakerNameOne?: string;

  /**
   * メーカー型番
   */
  astMakerModelOne?: string;

  /**
   * 資産プロジェクト
   */
  astPrjCodeOne?: string;

  /**
   * 資産プロジェクト名
   */
  astPrjNameOne?: string;

  /**
   * ソフトウエアメーカー名
   */
  softwareMakerNameOne?: string;

  /**
   * ソフトウエア名
   */
  softwareNameOne?: string;

  /**
   * 資産(機器)数量・明細数
   */
    // tslint:disable-next-line: no-any
  astLineCount?: any;

  /**
   * 取得数
   */
    // tslint:disable-next-line: no-any
  astQuantitySum?: any;

  /**
   * 資産(機器)数量・登録数
   */
    // tslint:disable-next-line: no-any
  astRegistQuantitySum?: any;

  /**
   * 資産(機器)数量・登録不要
   */
    // tslint:disable-next-line: no-any
  astNoRegistQuantitySum?: any;

  /**
   * ライセンス情報[合計]
   */
    // tslint:disable-next-line: no-any
  licLineCount?: any;

  /**
   * ライセンス数量[合計]：取得数
   */
    // tslint:disable-next-line: no-any
  licQuantitySum?: any;

  /**
   * ライセンス数量[合計]：登録数
   */
    // tslint:disable-next-line: no-any
  licRegistQuantitySum?: any;

  /**
   * 取得先
   */
  getCompanyName?: string;

  /**
   * 故障情報機器管理番号
   */
  failureAssetIdOne?: string;

  /**
   * 経費負担部署配分表示名
   */
  costSectionDistName?: string;

  /**
   * レンタル注文書?: 発注フラグ名
   */
  reoOrderFlagName?: string;

  /**
   * vi check box
   */
  sel?: boolean;
}
