import { ApChange } from './ap-change.model';

/**
 * 概要説明   : 移動申請
 */
export class ApChangeSR extends ApChange {

    assetId?: string; // 情報機器管理番号
	astName?: string; // 資産(機器)名

	licenseId?: string; // ライセンス管理番号
	softwareId?: number; // ソフトウェアID
	softwareName?: string; // ソフトウェア名

	astNum?: string; // 資産番号
	astNameFld?: string; // 資産名
	contractNum?: string; // 契約番号
	contractSubNum?: string; // 契約枝番
	astNameContract?: string; // 物件名

	costTypeName?: string; // 販売管理費/原価区分名

	costSectionNameOld?: string; // 移動元経費負担部署
	holCompanyNameOld?: string; // 移動元保有会社
	holSectionNameOld?: string; // 移動元保有部署

	costSectionNameNew?: string; // 移動先経費負担部署

}
