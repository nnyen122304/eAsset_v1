/**
 * 人事部課データクラス
 */
export class Section {

  /**
   *  会社コード
   */
  companyCode?: string;

  /**
   *  部署コード
   */
  sectionCode?: string;

  /**
   *  親部署コード
   */
  parentSectionCode?: string;

  /**
   *  部署名
   */
  sectionName?: string;

  /**
   *  部署名(短縮)
   */
  sectionShortName?: string;

  /**
   *  部初年度
   */
  sectionYear?: number;

  /**
   *  販売管理費/原価区分 1:原価、2:販売管理費
   */
  costFlag?: string;

  /**
   *  会社名
   */
  companyName?: string;

  /**
   *  会社名称
   */
  companyOfficialName?: string;

}
