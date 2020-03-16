import { ApChangeLineContract } from './ap-change-line-contract.model';
import { ApChangeLineFld } from './ap-change-line-fld.model';
import { ApChangeLineAst } from './ap-change-line-ast.model';
import { ApChangeLineLic } from './ap-change-line-lic.model';
import { ApChangeLineCostSec } from './ap-change-line-cost-sec.model';

/**
 * 概要説明   : 移動申請
 */
export class ApChange {
    /**
     * 申請書番号
     */
    applicationId?: string;
    /**
     * 作成日
     */
    createDate?: Date;
    /**
     * 作成者社員番号
     */
    createStaffCode?: string;
    /**
     * 更新日
     */
    updateDate?: Date;
    /**
     * 更新者社員番号
     */
    updateStaffCode?: string;
    /**
     * バージョン
     */
    version?: number;
    /**
     * 更新コメント
     */
    updateComment?: string;
    /**
     * e申請書類ID
     */
    eappId?: number;
    /**
     * 申請書ステータス 1:未申請,2:申請中,3:承認済,4:差戻し,5:却下 汎用マスタ-AP_STATUS_CHANGE
     */
    apStatus?: string;
    /**
     * 申請書番号
     */
    apDate?: Date;
    /**
     * 移動申請区分 1:情報機器等,2:ライセンス,3:無形固定資産(現物),A:リース・レンタル,B:有形固定資産,C:無形固定資産
     */

    apChangeType?: string;
    /**
     * 移動申請区分-有形フラグ 0:有形固定資産の変更を含まない,1:有形固定資産の変更を含む
     */
    apChangeTypeTanFlag?: string;
    /**
     * 移動申請区分-無形固定資産(社内使用SW/長前/その他)フラグ 0:該当無形固定資産の変更を含まない,1:該当無形固定資産の変更を含む
     */
    apChangeTypeInt1Flag?: string;
    /**
     * 移動申請区分-無形固定資産(市販目的SW)フラグ 0:該当無形固定資産の変更を含まない,1:該当無形固定資産の変更を含む
     */
    apChangeTypeInt2Flag?: string;
    /**
     * 移動申請区分-備品フラグ 0:備品の変更を含まない,1:備品の変更を含む
     */
    apChangeTypeEquipFlag?: string;
    /**
     * 移動申請区分-情シス配備シンクライアントフラグ 0:情シス配備シンクライアントの変更を含まない,1:情シス配備シンクライアントの変更を含む
     */
    apChangeTypeThinClFlag?: string;
    /**
     * 移動申請区分-情シス配備持ち出し専用機器フラグ 0:情シス配備持ち出し専用機器の変更を含まない,1:情シス配備持ち出し専用機器の変更を含む
     */
    apChangeTypeTakePcFlag?: string;
    /**
     * 移動申請区分-リースフラグ 0:リースの変更を含まない,1:リースの変更を含む
     */
    apChangeTypeLeaseFlag?: string;
    /**
     * 移動申請区分-レンタルフラグ 0:レンタルの変更を含まない,1:レンタルの変更を含む
     */
    apChangeTypeRentalFlag?: string;
    /**
     * 移動申請区分-ライセンスフラグ 0:ライセンスの変更を含まない,1:ライセンスの変更を含む
     */
    apChangeTypeLicenseFlag?: string;
    /**
     * 移動申請区分-販売管理費／原価区分(プロジェクトコード)フラグ 0:販売管理費／原価区分(プロジェクトコード)の変更を含まない,1:販売管理費／原価区分(プロジェクトコード)の変更を含む
     */
    apChangeTypeCostTypeFlag?: string;
    /**
     * 起票者社員番号
     */
    apCreateStaffCode?: string;
    /**
     * 起票者所属会社コード
     */
    apCreateCompanyCode?: string;
    /**
     * 起票者所属部署コード
     */
    apCreateSectionCode?: string;
    /**
     * 起票者所属部署年度
     */
    apCreateSectionYear?: number;
    /**
     * 申請者社員番号
     */
    apStaffCode?: string;
    /**
     * 申請会社コード
     */
    apCompanyCode?: string;
    /**
     * 申請部署コード
     */
    apSectionCode?: string;
    /**
     * 申請部署年度
     */
    apSectionYear?: number;
    /**
     * 連絡先TEL
     */
    apTel?: string;
    /**
     * 移動予定日
     */
    chgScheduleDate?: Date;
    /**
     * 移動予定年月度(経費負担部課変更時)
     */
    chgSchedulePeriod?: string;
    /**
     * 移動理由／備考
     */
    chgDescription?: string;
    /**
     * 変更後：保有会社コード
     */
    holCompanyCode?: string;
    /**
     * 変更後：保有部署コード
     */
    holSectionCode?: string;
    /**
     * 変更後：保有部署年度
     */
    holSectionYear?: number;
    /**
     * 変更後：使用部署同時更新フラグ 0:同時更新を行わない,1:同時更新を行う。
     */
    holSectionWithFlag?: string;
    /**
     * 変更後：資産管理担当者
     */
    holStaffCode?: string;
    /**
     * 変更後：設置場所 汎用マスタ-OFFICE
     */
    holOfficeCode?: string;
    /**
     * 変更後：設置場所階数
     */
    holOfficeFloor?: number;
    /**
     * 変更後：部屋番号
     */
    holOfficeRoomNum?: string;
    /**
     * 変更後：ラック番号
     */
    holOfficeRackNum?: string;
    /**
     * 変更後：使用者社員番号
     */
    useStaffCode?: string;
    /**
     * 変更後：使用者所属会社コード
     */
    useStaffCompanyCode?: string;
    /**
     * 変更後：使用者所属部署コード
     */
    useStaffSectionCode?: string;
    /**
     * 変更後：使用者所属部署年度
     */
    useStaffSectionYear?: number;
    /**
     * 変更後：無形管理担当者社員番号
     */
    intHolStaffCode?: string;
    /**
     * 変更後：無形管理担当者所属会社コード
     */
    intHolStaffCompanyCode?: string;
    /**
     * 変更後：無形管理担当者所属部署コード
     */
    intHolStaffSectionCode?: string;
    /**
     * 変更後：無形管理担当者所属部署年度
     */
    intHolStaffSectionYear?: number;
    /**
     * 変更後：代表設置場所
     */
    holRepOfficeCode?: string;
    /**
     * 変更後：販売管理費/原価区分 1:販売管理費,2:原価
     */
    costType?: string;
    /**
     * 変更後：減価償却プロジェクトコード
     */

     costDepPrjCode?: string;
    /**
     * 変更後：資産計上会社コード
     */
    costCompanyCode?: string;
    /**
     * 変更後：資産計上部課コード
     */
    costSectionCode?: string;
    /**
     * 変更後：資産計上部課年度
     */
    costSectionYear?: number;
    /**
     * 移動元承認者：資産管理担当者
     */
    apprHolStaffCodeOld?: string;
    /**
     * 移動元承認者：経費負担部課課長
     */
    apprCostStaffCodeOld?: string;
    /**
     * 移動元承認者：経費負担部課課長スキップ
     */
    apprCostStaffSkipFlagOld?: string;
    /**
     * 移動元承認者：部長
     */
    apprSuperiorStaffCodeOld?: string;
    /**
     * 移動先承認者：資産管理担当者
     */
    apprHolStaffCodeNew?: string;
    /**
     * 移動先承認者：経費負担部課課長
     */
    apprCostStaffCodeNew?: string;
    /**
     * 移動先承認者：経費負担部課課長スキップ
     */
    apprCostStaffSkipFlagNew?: string;
    /**
     * 移動先承認者：部長
     */
    apprSuperiorStaffCodeNew?: string;
    /**
     * 承認日
     */
    approveDate?: Date;
    /**
     * 申請者名
     */

    apStaffName?: string;
    /**
     * ステータス名
     */
    apStatusName?: string;
    /**
     * 資産管理担当者
     */
    holStaffName?: string;
    /**
     * 設置場所名
     */
    holOfficeName?: string;
    /**
     * 申請区分名
     */
    apChangeTypeName?: string;
    /**
     * 使用者名
     */
    useStaffName?: string;
    /**
     * 無形管理担当者名
     */
    intHolStaffName?: string;
    /**
     * 申請会社名
     */
    apCompanyName?: string;
    /**
     * 申請所属部署名
     */
    apSectionName?: string;
    /**
     * 起票者所属会社名
     */
    apCreateCompanyName?: string;
    /**
     * 起票者所属部署名
     */
    apCreateSectionName?: string;
    /**
     * 起票者社員名
     */
    apCreateStaffName?: string;
    /**
     * 変更後：保有会社名
     */
    holCompanyName?: string;
    /**
     * 変更後：保有部署名
     */
    holSectionName?: string;
    /**
     * 移動元承認者：資産管理担当者
     */
    apprHolStaffNameOld?: string;
    /**
     * 移動元承認者：経費負担部課課長名
     */
    apprCostStaffNameOld?: string;
    /**
     * 移動元承認者：部長名
     */
    apprSuperiorStaffNameOld?: string;
    /**
     * 移動先承認者：資産管理担当者
     */
    apprHolStaffNameNew?: string;
    /**
     * 移動先承認者：経費負担部課課長
     */
    apprCostStaffNameNew?: string;
    /**
     * 移動先承認者：部長名
     */
    apprSuperiorStaffNameNew?: string;
    /**
     * 変更後：使用者会社名
     */
    useStaffCompanyName?: string;
    /**
     * 変更後：使用者所属部署名
     */
    useStaffSectionName?: string;
    /**
     * 変更後：無形管理担当者会社名
     */
    intHolStaffCompanyName?: string;
    /**
     * 変更後：無形管理担当者所属部署名
     */
    intHolStaffSectionName?: string;
    /**
     * 変更後：減価償却プロジェクト名
     */
    costDepPrjName?: string;
    /**
     * 変更後：減価償却タイプ
     */
    costDepPrjType?: string;
    /**
     * 移動予定年月度(経費負担部課変更時)名
     */
    chgSchedulePeriodName?: string;
    /**
     * 資産計上部課名
     */
    costSectionName?: string;
    /**
     * 代表設置場所
     */
    holRepOfficeName?: string;
    /**
     * 起票者:連絡先TEL
     */
    apCreateTel?: string;

    apChangeLineFldTan?: ApChangeLineFld[]; // 有形固定資産明細
	apChangeLineFldInt?: ApChangeLineFld[]; // 無形固定資産明細
	apChangeLineContractLease?: ApChangeLineContract[]; // 契約明細(リース)
	apChangeLineContractRental?: ApChangeLineContract[]; // 契約明細(レンタル)
	apChangeLineAst?: ApChangeLineAst[]; // 資産(機器)明細
	apChangeLineLic?: ApChangeLineLic[]; // ライセンス明細
	apChangeLineCostSecOld?: ApChangeLineCostSec[]; // 経費負担部課明細(移動元)
	apChangeLineCostSecNew?: ApChangeLineCostSec[]; // 経費負担部課明細(移動先)
}
