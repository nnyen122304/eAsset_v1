/**
 * システム共通定数
 */
// tslint:disable-next-line: no-namespace
export namespace SystemConst {
  /**
   * メニューに関する定数
   */
  export namespace Menu {
    /**
     * リファレンス実装
     */
    export const menuIdRefImpl = '00010';
    /**
     * 情報機器等 ＞ 照会/修正
     */
    export const menuIdAssetSearch = '01010';
    /**
     * 情報機器等 ＞ 一括更新
     */
    export const menuIdAssetBulkUpdate = '01020';
    /**
     * 情報機器等 ＞ 抹消
     */
    export const menuIdAssetDelete = '01030';
    /**
     * ライセンス ＞ 照会/修正
     */
    export const menuIdLicenseSearch = '02010';
    /**
     * ライセンス ＞ 一括更新
     */
    export const menuIdLicenseBulkUpdate = '02020';
    /**
     * ライセンス ＞ 割当
     */
    export const menuIdLicenseAlloc = '02030';
    /**
     * ライセンス ＞ 抹消
     */
    export const menuIdLicenseDelete = '02050';
    /**
     * ライセンス ＞ メーカー管理
     */
    export const menuIdSoftwareSettingMaker = '02060';
    /**
     * ライセンス ＞ ソフトウェア管理
     */
    export const menuIdSoftwareSettingSoftware = '02070';
    /**
     * ライセンス ＞ 管理帳票 ＞ 割当情報(機器から検索)
     */
    export const menuIdLicenseReportAllocAsset = '02080';
    /**
     * ライセンス ＞ 管理帳票 ＞ 割当情報(ライセンスから検索)
     */
    export const menuIdLicenseReportAllocLicense = '02090';
    /**
     * ライセンス ＞ 管理帳票 ＞ アップグレード情報
     */
    export const menuIdLicenseReportUpgrade = '02100';
    /**
     * 管理 ＞ 管理者設定
     */
    export const menuIdRoleSetting = '03010';
    /**
     * 管理 ＞ 資産管理担当者プロファイル
     */
    export const menuIdSectionRoleProfile = '03020';
    /**
     * 管理 ＞ 汎用マスタ管理
     */
    export const menuIdCodeNameSetting = '03030';
    /**
     * 管理 ＞ ProPlusデータ取込
     */
    export const menuIdPpfsImport = '03040';
    /**
     * 申請 ＞ 取得申請 ＞ 新規作成 ＞ 新規取得[購入：有形固定資産/備品(情報機器)]
     */
    export const menuIdApGetTanCreate1 = '04010';
    /**
     * 申請 ＞ 取得申請 ＞ 新規作成 ＞ 新規取得[リース・レンタル]
     */
    export const menuIdApGetTanCreate2 = '04020';
    /**
     * 申請 ＞ 取得申請 ＞ 新規作成 ＞ 借受・譲受・貸出商品・納入前商品
     */
    export const menuIdApGetTanCreate3 = '04030';
    /**
     * 申請 ＞ 取得申請 ＞ 新規作成 ＞ 故障交換[リース・レンタル]
     */
    export const menuIdApGetTanCreate4 = '04040';
    /**
     * 申請 ＞ 取得申請 ＞ コピー(既存申請コピー)
     */
    export const menuIdApGetTanCopy = '04050';
    /**
     * 申請 ＞ 取得申請 ＞ 照会/修正
     */
    export const menuIdApGetTanSearch = '04060';
    /**
     * 申請 ＞ 登録申請 ＞ 情報機器等 ＞ 新規作成
     */
    export const menuIdApAssetCreate1 = '05010';
    /**
     * 申請 ＞ 登録申請 ＞ 情報機器等 ＞ 新規作成(仮想機器)
     */
    export const menuIdApAssetCreate2 = '05020';
    /**
     * 申請 ＞ 登録申請 ＞ 情報機器等 ＞ 照会/修正
     */
    export const menuIdApAssetSearch = '05040';
    /**
     * 申請 ＞ 登録申請 ＞ ライセンス ＞ 新規作成
     */
    export const menuIdApLicenseCreate = '06010';
    /**
     * 申請 ＞ 登録申請 ＞ ライセンス ＞ コピー(既存申請コピー)
     */
    export const menuIdApLicenseCopy = '06020';
    /**
     * 申請 ＞ 登録申請 ＞ ライセンス ＞ 照会/修正
     */
    export const menuIdApLicenseSearch = '06030';
    /**
     * 申請 ＞ 移動申請 ＞ 新規作成(現物情報の移動)
     */
    export const menuIdApChangeCreate1 = '07010';
    /**
     * 申請 ＞ 移動申請 ＞ 照会/修正
     */
    export const menuIdApChangeSearch = '07020';
    /**
     * 申請 ＞ 移動申請 ＞ 新規作成(経費情報の移動)
     */
    export const menuIdApChangeCreate2 = '07030';
    /**
     * 申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ 新規作成 ＞ 社内使用ソフトウェア
     */
    export const menuIdApGetIntCreate1 = '08010';
    /**
     * 申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ 新規作成 ＞ 市販目的ソフトウェア
     */
    export const menuIdApGetIntCreate2 = '08020';
    /**
     * 申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ 新規作成 ＞ 長期前払費用 ＞ その他無形固定資産
     */
    export const menuIdApGetIntCreate3 = '08030';
    /**
     * 申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ 追加および変更
     */
    export const menuIdApGetIntEdit1 = '08040';
    /**
     * 申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ 変更(財経承認のみ)
     */
    export const menuIdApGetIntEdit2 = '08050';
    /**
     * 申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ コピー(既存申請コピー)
     */
    export const menuIdApGetIntCopy = '08060';
    /**
     * 申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ 照会/修正
     */
    export const menuIdApGetIntSearch = '08070';
    /**
     * 申請 ＞ サービス提供開始報告(無形固定資産) ＞ 新規作成
     */
    export const menuIdApBgnIntCreate = '09010';
    /**
     * 申請 ＞ サービス提供開始報告(無形固定資産) ＞ 照会/修正
     */
    export const menuIdApBgnIntSearch = '09020';
    /**
     * 申請 ＞ 除売却申請 ＞ 有形固定資産 ＞ 新規作成
     */
    export const menuIdApEndTanCreate = '10010';
    /**
     * 申請 ＞ 除売却申請 ＞ 有形固定資産 ＞ 照会/修正
     */
    export const menuIdApEndTanSearch = '10020';
    /**
     * 申請 ＞ 除売却申請 ＞ 無形固定資産 ＞ 新規作成
     */
    export const menuIdApEndIntCreate = '11010';
    /**
     * 申請 ＞ 除売却申請 ＞ 無形固定資産 ＞ 除却報告
     */
    export const menuIdApEndIntDeleteReport = '11020';
    /**
     * 申請 ＞ 除売却申請 ＞ 無形固定資産 ＞ 照会/修正
     */
    export const menuIdApEndIntSearch = '11030';
    /**
     * 申請 ＞ リース満了/再リース/中途解約申請 ＞ 新規作成
     */
    export const menuIdApEndLeCreate = '12010';
    /**
     * 申請 ＞ リース満了/再リース/中途解約申請 ＞ 照会/修正
     */
    export const menuIdApEndLeSearch = '12020';
    /**
     * 固定資産 ＞ 有形固定資産 ＞ 照会
     */
    export const menuIdFldTanSearch = '13010';
    /**
     * 固定資産 ＞ 無形固定資産 ＞ 照会/管理項目修正
     */
    export const menuIdFldIntSearch = '13020';
    /**
     * 固定資産 ＞ 無形固定資産 ＞ 管理項目一括更新
     */
    export const menuIdFldIntBulkUpdate = '13030';
    /**
     * 固定資産 ＞ 管理帳票
     */
    export const menuIdFldReport = '13040';
    /**
     * リース/レンタル ＞ 照会
     */
    export const menuIdLldSearch = '14010';
    /**
     * リース/レンタル ＞ 管理帳票
     */
    export const menuIdLldReport = '14020';
    /**
     * 申請 ＞ レンタル満了/再レンタル/中途解約申請 ＞ 新規作成
     */
    export const menuIdApEndReCreate = '15010';
    /**
     * 申請 ＞ レンタル満了/再レンタル/中途解約申請 ＞ 照会/修正
     */
    export const menuIdApEndReSearch = '15020';
    /**
     * 棚卸 ＞ 棚卸データ作成
     */
    export const menuIdInvCreate = '18010';
    /**
     * 棚卸 ＞ 棚卸
     */
    export const menuIdInv = '18020';
    /**
     * 棚卸 ＞ 一括更新
     */
    export const menuIdInvBulkUpdate = '18030';
    /**
     * 期首移行 ＞ 経費負担部課精査データ作成
     */
    export const menuIdCostScrCreate = '19010';
    /**
     * 期首移行 ＞ 経費負担部課精査
     */
    export const menuIdCostScr = '19020';
    /**
     * サイトマップ
     */
    export const menuIdSiteMap = '99010';

    /**
     * e申請からリンクした際の仮想メニューID
     */
    export namespace Eapp {
      /**
       * リファレンス実装
       */
      export const menuIdRefImplEapp = '00098';
      /**
       * 申請 ＞ 取得申請
       */
      export const menuIdApGetTanEapp = '04098';
      /**
       * 申請 ＞ 登録申請 ＞ 情報機器等
       */
      export const menuIdApAssetEapp = '05098';
      /**
       * 申請 ＞ 登録申請 ＞ ライセンス
       */
      export const menuIdApLicenseEapp = '06098';
      /**
       * 申請 ＞ 移動申請
       */
      export const menuIdApChangeEapp = '07098';
      /**
       * 申請 ＞ 取得申請(無形固定資産/長期前払費用)
       */
      export const menuIdApGetIntEapp = '08098';
      /**
       * 申請 ＞ サービス提供開始報告(無形固定資産)
       */
      export const menuIdApBgnIntEapp = '09098';
      /**
       * 申請 ＞ 除売却申請 ＞ 有形固定資産
       */
      export const menuIdApEndTanEapp = '10098';
      /**
       * 申請 ＞ 除売却申請 ＞ 無形固定資産
       */
      export const menuIdApEndIntEapp = '11098';
      /**
       * 申請 ＞ リース満了/再リース/中途解約申請
       */
      export const menuIdApEndLeEapp = '12098';
      /**
       * 申請 ＞ レンタル満了/再レンタル/中途解約申請
       */
      export const menuIdApEndReEapp = '15098';
      /**
       * 棚卸 ＞ 棚卸
       */
      export const menuIdInvEapp = '18098';
    }

    /**
     * 別Window参照からリンクした際の仮想メニューID・フレームタイトル
     */
    export namespace Ref {
      /**
       * リファレンス実装
       */
      export const menuIdRefImplRef = '00099';
      /**
       * リファレンス実装
       */
      export const frameTitleRefImplRef = 'リファレンス実装-別Window参照用';
      /**
       * 情報機器等 ＞ 照会
       */
      export const menuIdAssetRef = '01099';
      /**
       * 情報機器等 ＞ 照会
       */
      export const frameTitleAssetRef = '情報機器等 ＞ 照会';
      /**
       * ライセンス ＞ 照会
       */
      export const menuIdLicenseRef = '02099';
      /**
       * ライセンス ＞ 照会
       */
      export const frameTitleLicenseRef = 'ライセンス ＞ 照会';
      /**
       * 申請 ＞ 取得申請 ＞ 照会
       */
      export const menuIdApGetTanRef = '04099';
      /**
       * 申請 ＞ 取得申請 ＞ 照会
       */
      export const frameTitleApGetTanRef = '申請 ＞ 取得申請 ＞ 照会';
      /**
       * 申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ 照会
       */
      export const menuIdApGetIntRef = '08099';
      /**
       * 申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ 照会
       */
      export const frameTitleApGetIntRef = '申請 ＞ 取得申請(無形固定資産/長期前払費用) ＞ 照会';
      /**
       * 固定資産 ＞ 照会
       */
      export const menuIdFldRef = '13098';
      /**
       * 固定資産 ＞ 照会
       */
      export const frameTitleFldRef = '固定資産 ＞ 照会';
      /**
       * 固定資産 ＞ 無形固定資産/長期前払費用 ＞ 照会
       */
      export const menuIdFldAppRef = '13099';
      /**
       * 固定資産 ＞ 無形固定資産/長期前払費用 ＞ 照会
       */
      export const frameTitleFldAppRef = '固定資産 ＞ 無形固定資産/長期前払費用 ＞ 照会';
      /**
       * リース/レンタル ＞ 照会
       */
      export const menuIdLldRef = '14098';
      /**
       * リース/レンタル ＞ 照会
       */
      export const frameTitleLldRef = 'リース/レンタル ＞ 照会';
      /**
       * 棚卸 ＞ 棚卸
       */
      export const menuIdInvRef = '18099';
      /**
       * 棚卸 ＞ 棚卸
       */
      export const frameTitleInvRef = '棚卸 ＞ 棚卸';
    }
  }

  /**
   * ユニーク定数
   */
  export namespace Identifiers {
    /**
     * 20文字絞込用定数
     */
    export const filterString = 'BTRRESHTGAFRMSAYVUOLBDRRPOXURQNMOJTQAXVO';
  }

  /**
   * 取得形態(無形)
   */
  export namespace ApGetTypeInt {
    /**
     * 社内使用ソフトウェア
     */
    export const apGetTypeInt1 = '1';
    /**
     * 市販目的ソフトウェア
     */
    export const apGetTypeInt2 = '2';
    /**
     * 研究開発
     */
    export const apGetTypeInt3 = '3';
  }

  /**
   * プロジェクトカテゴリ区分
   */
  export namespace ProjectCategory {
    /**
     * 減価償却プロジェクト
     */
    export const projectCategoryCostDep = '1';
    /**
     * 資産プロジェクト
     */
    export const projectCategoryAst = '2';
  }

  /**
   * サービス提供開始報告タイプ
   */
  export namespace ApBgnType {
    /**
     * 社内使用ソフトウェア
     */
    export const apBgnTypeInt1 = '1';
    /**
     * 市販目的ソフトウェア
     */
    export const apBgnTypeInt2 = '2';
    /**
     * 研究開発
     */
    export const apBgnTypeInt3 = '3';
  }

  /**
   * サービス提供開始報告(無形固定資産)
   */
  export namespace ApStatBgnInt {
    /**
     * 未申請
     */
    export const apStatBgnIntNoApply = '1';
    /**
     * 申請中
     */
    export const apStatBgnIntApply = '2';
    /**
     * 承認済
     */
    export const apStatBgnIntApprove = '3';
    /**
     * 差戻し
     */
    export const apStatBgnIntSendBack = '4';
    /**
     * 却下
     */
    export const apStatBgnIntReject = '5';
    /**
     *  取下げ
     */
    export const apStatBgnIntCancel = '6';
    /**
     *  未申請
     */
    export const apStatNameBgnIntNoApply = '未申請';
  }

  export namespace CategoryCodeItemDefPpfsFld {
    /**
     * 固定資産検索結果(無形):資産単位
     */
    export const categoryCodeItemDefPpfsFldSrInt = 'ITEM_DEF_PPFS_FLD_SR_INT';
    /**
     * 固定資産検索結果(無形):取得申請単位
     */
    export const categoryCodeItemDefPpfsFldSrIntApp = 'ITEM_DEF_PPFS_FLD_SR_INT_APP';
  }

  export namespace CategoryCodeItemCostScr {
    /**
     * 固定資産検索結果(無形):資産単位
     */
    export const itemDefCostScrLineFldTan = 'ITEM_DEF_COST_SCR_LINE_FLD_TAN';
    /**
     * 固定資産検索結果(無形):取得申請単位
     */
    export const itemDefCostScrLineFldInt = 'ITEM_DEF_COST_SCR_LINE_FLD_INT';

    export const itemDefCostScrLineLeRe = 'ITEM_DEF_COST_SCR_LINE_LE_RE';
  }
  // 申請書注意書き
  export namespace CategoryCodeApAttention {
    export const apAttentionBgnInt1 = 'AP_ATTENTION_BGN_INT1';
    export const apAttentionBgnInt2 = 'AP_ATTENTION_BGN_INT2';
    export const apAttentionBgnInt3 = 'AP_ATTENTION_BGN_INT3';
  }

  // 申請書注意書き
  export namespace CategoryApGetInt {
    export const apGetTypeInt1 = '社内使用ソフトウェア';
    export const apGetTypeInt2 = '市販目的ソフトウェア';
    export const apGetTypeInt3 = '長期前払費用/その他無形固定資産';
  }

  /**
   * 取得申請ステータス
   */
  export namespace ApStatGetTan {
    /**
     * 未申請
     */
    export const apStatGetTanNoApply = '1';
    /**
     * 申請中
     */
    export const apStatGetTanApply = '2';
    /**
     * 承認済
     */
    export const apStatGetTanApprove = '3';
    /**
     * 差戻し
     */
    export const apStatGetTanSendBack = '4';
    /**
     * 却下
     */
    export const apStatGetTanReject = '5';
    /**
     * 取下げ
     */
    export const apStatGetTanCancel = '6';
    /**
     * 未申請
     */
    export const apStatNameGetTanNoApply = '未申請';

  }

  /**
   * レンタル会社-発注区分
   */
  export namespace lePoType {
    /**
     * 発注無
     */
    export const lePoTypeNo = '1';
    /**
     * 現場発注
     */
    export const lePoTypeSpot = '2';
    /**
     * 主管部発注
     */
    export const lePoTypeSec = '3';
  }

  /**
   * 取得形態(有形)
   */
  export namespace apGetType {
    /**
     * 固定資産/備品
     */
    export const apGetTypeTan = '3';
    /**
     * リース
     */
    export const apgetTypeLease = '2';
    /**
     * レンタル
     */
    export const apGetTypeRental = '1';
    /**
     * 故障交換
     */
    export const apGetTypeReplace = '4';
    /**
     * 借受
     */
    export const apGetTypeKariuke = '6';
    /**
     * 譲受
     */
    export const apGetTypeTake = '7';
    /**
     * 貸出商品
     */
    export const apGetTypeRentalGoods = '9';
    /**
     * 納入前商品
     */
    export const apGetTypePreDeliverGoods = 'A';
  }
}
