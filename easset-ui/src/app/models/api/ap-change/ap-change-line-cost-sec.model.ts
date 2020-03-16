import { ApChange } from './ap-change.model';

/**
 * 概要説明   : 移動申請
 */
export class ApChangeLineCostSec extends ApChange {
    applicationId?: string; // 申請書番号
	apChangeLineCostType?: string; // 経費負担部課明細区分 1:リース契約,2:レンタル契約,A:移動元,B:移動先
	astNum?: string; // 資産番号 A:移動元,B:移動先のデータは"-"固定
	lineSeq?: number; // 行シーケンス
	createDate?: Date ; // 作成日
	createStaffCode?: string; // 作成者社員番号
	updateDate?: Date; // 更新日
	updateStaffCode?: string; // 更新者社員番号
	costCompanyCode?: string; // 経費負担会社コード
	costSectionCode?: string; // 経費負担部課コード
	costSectionYear?: number; // 経費負担部課年度
	costDist?: number; // 配分(%)

	costCompanyName?: string; // 経費負担会社名
	costSectionName?: string; // 経費負担部課名
}