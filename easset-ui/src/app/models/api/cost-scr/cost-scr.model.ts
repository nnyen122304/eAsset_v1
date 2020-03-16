export class CostScr {
  /**
   *  会計年月
   */
  period?: string;

  /**
   *   会社コード
   */
  companyCode?: string;

  /**
   *  当年度：経費負担部課コード
   */
  costSectionCode?: string;

  /**
   *   前年度：経費負担部課コード
   */
  costSectionCodeOld?: string;

  /**
   *  精査種別 1:有形固定資産,2:無形固定資産,3:リース,4:レンタル,5:シンクライアント(情シス配備),6:持出専用機器(情シス配備)
   */
  scrType?: string;

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
   *  精査担当部署コード
   */
  scrSectionCode?: string;

  /**
   *  精査担当部署年度
   */
  scrSectionYear?: number;

  /**
   *  完了フラグ 0:未完了,1:完了
   */
  compFlag?: string;

  /**
   *   完了処理実行日
   */
  compExecDate?: Date;

  /**
   *  完了処理実行者社員番号
   */
  compExecStaffCode?: string;

  /**
   *  精査実施日
   */
  scrDate?: Date;

  /**
   *  精査実施者社員番号
   */
  scrStaffCode?: string;

  /**
   *  精査担当部署設定更新者社員番号
   */
  scrSectionUpdateStaffCode?: string;

  /**
   * 精査担当部署設定更新日
   */
  scrSectionUpdateDate?: Date;

  /**
   *  督促メール送信日付
   */
  reminderDate?: Date;

  /**
   *  表示用値：セル選択判定
   */
  sel?: boolean;
}
