import {Section} from './section';

/**
 * Sectionノード
 */
export class SectionNode extends Section {

  /**
   * 親部署コード
   */
  parentSectionCode: string;

  /**
   * 項目一覧
   */
  items?: SectionNode[];
}
