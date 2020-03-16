import { ApChange } from './ap-change.model';

/**
 * 概要説明   : 移動申請
 */
export class ApChangeLineFld extends ApChange {
    applicationId?: string; // 申請書番号
	apChangeLineFldType?: string; // 資産明細区分 1:有形固定資産,2:無形固定資産
	lineSeq?: number; // 行シーケンス
	createDate?: Date; // 作成日
	createStaffCode?: string; // 作成者社員番号
	updateDate?: Date; // 更新日
	updateStaffCode?: string; // 更新者社員番号
	ppId?: number; // 固有番号 ProPlusの資産台帳キー
	getApplicationId?: string; // 取得申請書番号
	astDate?: Date; // 計上日
	astNum?: string; // 資産番号
	astName?: string; // 資産名
	astClass?: string; // 種類
	astGetAmount?: number; // 取得価額
	astBookAmount?: number; // 帳簿価額
	costType?: string; // 販売管理費/原価区分 1:販売管理費,2:原価
	costDepPrjCode?: string; // 減価償却プロジェクトコード
	costSectionCode?: string; // 資産計上負担部課コード
	itemGroupCode?: string; // 案件グループコード
	holRepOfficeCode?: string; // 代表設置場所
	costDistCode?: string; // 配賦コード
	holCompanyCode?: string; // 保有会社コード(無形)
	holSectionCode?: string; // 保有部署コード(無形)
	holSectionYear?: number; // 保有部署年度(無形)
	holStaffCode?: string; // 無形管理担当者
	ppTransFlag?: string; // ProPlus連携フラグ 0:未連携,1:連携済
	tempFlag?: string; // 仮勘定フラグ 0:本勘定,1:仮勘定

	//	名称
	astClassName?: string; // 種類名
	costTypeName?: string; // 販売管理費/原価区分
	costDepPrjName?: string; // 減価償却プロジェクト名
	costSectionName?: string; // 資産計上負担部課名
	itemGroupName?: string; // 案件グループ名
	holRepOfficeName?: string; // 代表設置場所名
	costDistName?: string; // 配賦名
	holStaffName?: string; // 無形管理担当者名
	holSectionName?: string; // 保有部署名(無形)
}