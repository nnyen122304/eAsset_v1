import { ApChange } from './ap-change.model';

/**
 * 概要説明   : 移動申請
 */
export class ApChangeLineContract extends ApChange {
    applicationId?: string; // 申請書番号
	apChangeLineContractType?: string; // 契約明細区分 1:リース契約,2:レンタル契約
	lineSeq?: number; // 行シーケンス
	createDate?: Date; // 作成日
	createStaffCode?: string; // 作成者社員番号
	updateDate?: Date; // 更新日
	updateStaffCode?: string; // 更新者社員番号
	contractCompanyCode?: string; // 契約会社コード
	contractNum?: string; // 契約番号
	contractStartDate?: Date; // 契約開始日
	contractEndDate?: Date; // 契約終了日
	remainAmount?: number; // 残金額
	monthAmount?: number; // 月額
	costType?: string; // 販売管理費/原価区分 1:販売管理費,2:原価
	costDepPrjCode?: string; // 減価償却プロジェクトコード

	costTypeName?: string; // 販売管理費/原価区分名
	costDepPrjName?: string;  // 減価償却プロジェクト名
	costSectionName?: string; // 経費負担部署
	contractCompanyName?: string; // 契約会社名

	assetId?: string;		//	情報機器管理番号
	licenseId?: string;	//	ライセンス管理番号

	//	1.5
	contractSubNum?: string; // 契約枝番
	astNum?: string; // 資産番号
	astName?: string; // 物件名
	remainTerm?: number; // 残期間
	astClass?: string; // 種類
	costSectionCode?: string; // 資産計上部課コード
	itemGroupCode?: string; // 案件グループコード
	holRepOfficeCode?: string; // 代表設置場所
	costDistCode?: string; // 配賦コード
	ppIdContract?: number; // 固有番号(契約) ProPlusの契約台帳キー
	ppIdAst?: number; // 固有番号(物件) ProPlusの物件台帳キー
	ppTransFlag?: string; // ProPlus連携フラグ 0:未連携,1:連携済

	astClassName?: string; // 種類名
	itemGroupName?: string; // 案件グループ名
	holRepOfficeName?: string; // 代表設置場所名
	costDistName?: string; // 配賦名
}