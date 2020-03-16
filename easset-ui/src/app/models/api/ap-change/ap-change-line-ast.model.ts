import { ApChange } from './ap-change.model';

/**
 * 概要説明   : 移動申請
 */
export class ApChangeLineAst extends ApChange {
    applicationId?: string; // 申請書番号
	lineSeq?: number; // 行シーケンス
	createDate?: Date; // 作成日
	createStaffCode?: string; // 作成者社員番号
	updateDate?: Date; // 更新日
	updateStaffCode?: string; // 更新者社員番号
	assetId?: string; // 情報機器管理番号
	holCompanyCode?: string; // 保有会社コード
	holSectionCode?: string; // 保有部署コード
	holSectionYear?: number; // 保有部署年度
	holStaffCode?: string; // 資産管理担当者
	holOfficeCode?: string; // 設置場所 汎用マスタ-OFFICE
	useStaffCode?: string; // 使用者社員番号
	useStaffCompanyCode?: string; // 使用者所属会社コード
	useStaffSectionCode?: string; // 使用者所属部署コード
	useStaffSectionYear?: number; // 使用者所属部署年度
	astName?: string; // 資産(機器)名
	astCategory1Code?: string; // 資産(機器)大分類 汎用マスタ-ASSET_CATEGORY1
	astCategory2Code?: string; // 資産(機器)小分類 汎用マスタ-ASSET_CATEGORY2
	astSystemSectionDeployFlag?: string; // 情報システム部配備フラグ
	astAssetType?: string; // 資産区分 汎用マスタ-ASSET_TYPE
	astManageType?: string; // 管理区分 汎用マスタ-ASSET_MANAGE_TYPE
	contractNum?: string; // 契約番号
	autoAddFlag?: string; // 自動追加フラグ

	holQuantity?: number; // 数量
	ppId?: number; // 固有番号(資産) ProPlusの資産台帳キー
	astNum?: string; // 資産番号
	getApplicationId?: string; // 取得申請書番号
	contractSubNum?: string; // 契約枝番


	holOfficeName?: string;  // 設置場所名
	holSectionName?: string; // 保有部署名
	holStaffName?: string;  // 資産管理担当者名
	useStaffName?: string;  // 使用者名
	astAssetTypeName?: string; // 資産区分名
}
