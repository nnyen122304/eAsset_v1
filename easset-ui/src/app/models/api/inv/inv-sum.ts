/**
 * 棚卸一覧情報クラス
 */
export class InvSum {

  /**
   *  会計年月
   */
  period?: string;

  /**
   *  会社コード
   */
  companyCode?: string;

  /**
   *  保有部署コード
   */
  holSectionCode?: string;

  /**
   *  保有部署年度
   */
  holSectionYear?: number;

  /**
   *  資産区分 1:有形固定資産,2:資産除去費用,3:無形固定資産(本勘定),4:無形固定資産(仮勘定),5:リース資産,6:レンタル資産,7:備品等
   */
  invAssetType?: string;

  /**
   *  作成日
   */
  createDate?: Date;

  /**
   *  作成者社員番号
   */
  createStaffCode?: string;

  /**
   *  作成者社員番号
   */
  updateDate?: Date;

  /**
   *  更新者社員番号
   */
  updateStaffCode?: string;

  /**
   *  申請ステータス
   */
  apStatus?: string;

  /**
   *  申請日
   */
   apDate?: Date;

  /**
   *  申請者社員番号
   */
  apStaffCode?: string;

  /**
   *  承認日
   */
  approveDate?: Date;


  /**
   *  承認者社員番号
   */
  approveStaffCode?: string;

  /**
   *  e申請書類ID
   */
  eappId?: number;

  /**
   *  督促メール送信日付
   */
  reminderDate?: Date;

  /**
   *  棚卸状況：未
   */
  invCountUndecided?: number;

  /**
   *  棚卸状況：済有
   */
  invCountOwn?: number;

  /**
   *  棚卸状況：済無
   */
  invCountNotOwn?: number;

  /**
   *  棚卸状況：計
   */
  invCountTotal?: number;

  /**
   *  資産計数
   */
  assetCount?: number;

  /**
   *  棚卸対象者
   */
  invStaffCode?: string;

  /**
   *  表示用値：部署名
   */
  holSectionName?: string;

  /**
   *  表示用値：社員名
   */
  apStaffName?: string;

  /**
   *  表示用値：許可社員名
   */
  approveStaffName?: string;

  /**
   *  表示用値：資産種類
   */
  invAssetTypeName?: string;

  /**
   *  表示用値：ステータス
   */
  apStatusName?: string;

  /**
   *  表示用値：セル選択判定
   */
  sel?: boolean;

}
