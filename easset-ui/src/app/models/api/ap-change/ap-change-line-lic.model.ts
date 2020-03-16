import { ApChange } from './ap-change.model';

/**
 * 概要説明   : 移動申請
 */
export class ApChangeLineLic extends ApChange {
    applicationId?: string; // 申請書番号
	lineSeq?: number; // 行シーケンス
	createDate?: Date; // 作成日
	createStaffCode?: string; // 作成者社員番号
	updateDate?: Date; // 更新日
	updateStaffCode?: string; // 更新者社員番号
	licenseId?: string; // ライセンス管理番号
	holCompanyCode?: string; // 保有会社コード
	holSectionCode?: string; // 保有部署コード
	holSectionYear?: number; // 保有部署年度
	holStaffCode?: string; // 資産管理担当者
	softwareMakerId?: number; // ソフトウェアメーカーID
	softwareId?: number; // ソフトウェアID
	licAssetType?: string; // 資産区分(ライセンス) 汎用マスタ-LIC_ASSET_TYPE
	contractNum?: string; // 契約番号
	autoAddFlag?: string; // 自動追加フラグ

	licQuantityType?: string; // ライセンス数タイプ 1:有限,2:無限
	licQuantity?: number; // ライセンス保有数
	ppId?: number; // 固有番号(資産) ProPlusの資産台帳キー
	astNum?: string; // 資産番号
	getApplicationId?: string; // 取得申請書番号
	contractSubNum?: string; // 契約枝番

	holSectionName?: string;  // 保有部署コード
	holStaffName?: string;  // 資産管理担当者
	licAssetTypeName?: string;  // 資産区分名(ライセンス) 汎用マスタ-LIC_ASSET_TYPE
	softwareMakerName?: string; // ソフトウェアメーカー名
	softwareName?: string; // ソフトウェア名
}