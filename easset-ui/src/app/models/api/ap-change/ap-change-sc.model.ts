import { ApChange } from './ap-change.model';

/**
 * 概要説明   : 移動申請
 */
export class ApChangeSC extends ApChange {

    applicationIdPlural?: string; // 申請書番号複数
    apDateFrom?: Date; // 申請日From
    apDateTo?: Date; // 申請日To
    assetId?: string; // 情報機器管理番号
    assetIdPlural?: string; // 情報機器管理番号複数
    licenseId?: string; // ライセンス管理番号
    licenseIdPlural?: string; //  ライセンス管理番号複数
    contractNum?: string;  // 契約番号
    contractNumPlural?: string; // 契約番号複数
    apprCostStaffCode?: string; // 経費負担部課課長
    apprCostStaffName?: string; // 経費負担部課課長名
    includeSectionHierarchyFlag?: string; // 部署階層検索フラグ

    astAssetType?: string; // 資産区分
    astAssetTypeName?: string; // 資産区分名
    astManageType?: string; // 管理区分
    astManageTypeName?: string; // 管理区分名

    searchTarget?: string;  // 検索対象

    eappIdPlural?: string; // e申請書類ID複数

    contractSubNum?: string;  // 契約枝番
    contractSubNumPlural?: string; // 契約枝番複数
    astNum?: string;  // 資産番号
    astNumPlural?: string; // 資産番号複数
}