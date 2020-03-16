package jp.co.ctcg.easset.util;

/**
 * 共通定数
 * @author z1g7092
 */
public class Constants {

	// DBキャラセット
	public static final String DB_CHARSET = "MS932";

	// カテゴリコード:各種マスタ
	public static final String CATEGORY_CODE_ASSET_CATEGORY1 = "ASSET_CATEGORY1"; // 資産大分類
	public static final String CATEGORY_CODE_ASSET_CATEGORY2 = "ASSET_CATEGORY2"; // 資産小分類
	public static final String CATEGORY_CODE_ASSET_MAKER = "ASSET_MAKER"; // メーカー
	public static final String CATEGORY_CODE_ASSET_SHAPE = "ASSET_SHAPE"; // 筐体/形状
	public static final String CATEGORY_CODE_ASSET_GET_TYPE = "ASSET_GET_TYPE"; // 取得形態(資産)
	public static final String CATEGORY_CODE_ASSET_TYPE = "ASSET_TYPE"; // 資産区分
	public static final String CATEGORY_CODE_AP_GET_AMOUNT_RANGE = "AP_GET_AMOUNT_RANGE"; // 取得申請金額範囲
	public static final String CATEGORY_CODE_AP_GET_INT_AMOUNT_RANGE = "AP_GET_INT_AMOUNT_RANGE"; // 取得申請(無形)金額範囲
	public static final String CATEGORY_CODE_OUTER_SITE_URL = "OUTER_SITE_URL"; // 旧eAssetURL
	public static final String CATEGORY_CODE_LEASE_RENTAL_CUSTOMER = "LEASE_RENTAL_CUSTOMER"; // リース・レンタル会社
	public static final String CATEGORY_CODE_ASSET_MANAGE_TYPE = "ASSET_MANAGE_TYPE"; // 管理区分
	public static final String CATEGORY_CODE_USE_COMPANY = "USE_COMPANY"; // システム利用会社
	public static final String CATEGORY_CODE_OFFICE = "OFFICE"; // 設置場所
	public static final String CATEGORY_CODE_ASSET_USE_PURPOSE = "ASSET_USE_PURPOSE"; // 使用目的
	public static final String CATEGORY_CODE_CURRENT_YEAR = "CURRENT_YEAR"; // カレント年度
	public static final String CATEGORY_CODE_FLAG_YN = "FLAG_YN"; // フラグYN
	public static final String CATEGORY_CODE_FLAG_AN = "FLAG_AN"; // フラグ有り、無し
	public static final String CATEGORY_CODE_LICENSE_ASSET_TYPE = "LICENSE_ASSET_TYPE"; // ライセンス資産区分
	public static final String CATEGORY_CODE_LICENSE_GET_TYPE = "LICENSE_GET_TYPE"; // ライセンス取得方法
	public static final String CATEGORY_CODE_SOFTWARE_MEDIA_TYPE = "SOFTWARE_MEDIA_TYPE"; // メディア形態
	public static final String CATEGORY_CODE_LICENSE_TYPE = "LICENSE_TYPE"; // ライセンス区分
	public static final String CATEGORY_CODE_LICENSE_VOLUME_TYPE = "LICENSE_VOLUME_TYPE"; // ライセンスボリュームタイプ
	public static final String CATEGORY_CODE_LICENSE_QUANTITY_TYPE = "LICENSE_QUANTITY_TYPE"; // ライセンス数制限
	public static final String CATEGORY_CODE_LICENSE_USE_TYPE = "LICENSE_USE_TYPE"; // ライセンス使用許諾区分
	public static final String CATEGORY_CODE_BATCH_STATUS = "BATCH_STATUS"; // バッチステータス
	public static final String CATEGORY_CODE_BATCH_MAIL_TEMPLATE = "BATCH_MAIL_TEMPLATE"; // バッチメールテンプレート
	public static final String CATEGORY_CODE_LICENSE_TERM_CONTRACT_TYPE = "LICENSE_TERM_CONTRACT_TYPE"; // ライセンスターム契約区分
	public static final String CATEGORY_CODE_LONG_TIME_SECTION = "LONG_TIME_SECTION"; // 永年部課
	public static final String CATEGORY_CODE_STOP_AP_CHANGE = "STOP_AP_CHANGE"; // 移動申請停止指定
	public static final String CATEGORY_CODE_AP_END_INT_AMOUNT_RANGE = "AP_END_INT_AMOUNT_RANGE"; // 除却申請金額範囲(無形)
	public static final String CATEGORY_CODE_AP_END_TAN_AMOUNT_RANGE = "AP_END_TAN_AMOUNT_RANGE"; // 除却申請金額範囲(有形)
	public static final String CATEGORY_CODE_AP_END_LE_AMOUNT_RANGE = "AP_END_LE_AMOUNT_RANGE"; // リース満了･解約申請金額範囲
	public static final String CATEGORY_CODE_AP_END_RE_AMOUNT_RANGE = "AP_END_RE_AMOUNT_RANGE"; // レンタル満了･解約申請金額範囲
	public static final String CATEGORY_CODE_PPFS_LLD_KOJOGK_TYPE = "PPFS_LLD_KOJOGK_TYPE"; // リース契約控除額項目
	public static final String CATEGORY_CODE_AP_END_LE_TYPE = "AP_END_LE_TYPE"; // リース満了・解除申請区分
	public static final String CATEGORY_CODE_AP_END_RE_TYPE = "AP_END_RE_TYPE"; // レンタル満了･解約申請区分
	public static final String CATEGORY_CODE_SCR_TYPE = "SCR_TYPE"; // 精査種別
	public static final String CATEGORY_CODE_MENU_ACCESS_CONTROL = "MENU_ACCESS_CONTROL"; // メニュー制御
	public static final String CATEGORY_CODE_BATCH_KNIGHT_PATH = "BATCH_KNIGHT_PATH"; // knight連携パス
	public static final String CATEGORY_CODE_BATCH_SAP_PATH = "BATCH_SAP_PATH"; // 次期シス連携パス

	// カテゴリコード:項目定義
	public static final String CATEGORY_CODE_ITEM_DEF_AP_GET_TAN = "ITEM_DEF_AP_GET_TAN";
	public static final String CATEGORY_CODE_ITEM_DEF_AP_GET_INT = "ITEM_DEF_AP_GET_INT";
	public static final String CATEGORY_CODE_ITEM_DEF_AP_BGN_INT = "ITEM_DEF_AP_BGN_INT";
	public static final String CATEGORY_CODE_ITEM_DEF_AP_CHANGE = "ITEM_DEF_AP_CHANGE";
	public static final String CATEGORY_CODE_ITEM_DEF_AP_ASSET = "ITEM_DEF_AP_ASSET";
	public static final String CATEGORY_CODE_ITEM_DEF_AP_LICENSE = "ITEM_DEF_AP_LICENSE";
	public static final String CATEGORY_CODE_ITEM_DEF_ASSET = "ITEM_DEF_ASSET";
	public static final String CATEGORY_CODE_ITEM_DEF_LICENSE = "ITEM_DEF_LICENSE";
	public static final String CATEGORY_CODE_ITEM_DEF_SOFTWARE_MAKER = "ITEM_DEF_SOFTWARE_MAKER";
	public static final String CATEGORY_CODE_ITEM_DEF_SOFTWARE = "ITEM_DEF_SOFTWARE";
	public static final String CATEGORY_CODE_ITEM_DEF_ROLE_ADMIN = "ITEM_DEF_ROLE_ADMIN";
	public static final String CATEGORY_CODE_ITEM_DEF_ROLE_SECTION = "ITEM_DEF_ROLE_SECTION";
	public static final String CATEGORY_CODE_ITEM_DEF_ROLE_SECTION_PROFILE = "ITEM_DEF_ROLE_SECTION_PROFILE";
	public static final String CATEGORY_CODE_ITEM_DEF_AP_END_TAN = "ITEM_DEF_AP_END_TAN";
	public static final String CATEGORY_CODE_ITEM_DEF_AP_END_INT = "ITEM_DEF_AP_END_INT";
	public static final String CATEGORY_CODE_ITEM_DEF_AP_END_LE = "ITEM_DEF_AP_END_LE";
	public static final String CATEGORY_CODE_ITEM_DEF_AP_END_RE = "ITEM_DEF_AP_END_RE";
	public static final String CATEGORY_CODE_ITEM_DEF_INV_SUM = "ITEM_DEF_INV_SUM";
	public static final String CATEGORY_CODE_ITEM_DEF_INV_LINE_FLD = "ITEM_DEF_INV_LINE_FLD";
	public static final String CATEGORY_CODE_ITEM_DEF_INV_LINE_INT = "ITEM_DEF_INV_LINE_INT";
	public static final String CATEGORY_CODE_ITEM_DEF_INV_LINE_INT_S = "ITEM_DEF_INV_LINE_INT_S";
	public static final String CATEGORY_CODE_ITEM_DEF_INV_LINE_LE = "ITEM_DEF_INV_LINE_LE";
	public static final String CATEGORY_CODE_ITEM_DEF_INV_LINE_RE = "ITEM_DEF_INV_LINE_RE";
	public static final String CATEGORY_CODE_ITEM_DEF_INV_LINE_EQUIP = "ITEM_DEF_INV_LINE_EQUIP";
	public static final String CATEGORY_CODE_ITEM_DEF_COST_SCR = "ITEM_DEF_COST_SCR";
	public static final String CATEGORY_CODE_ITEM_DEF_COST_SCR_LINE_FLD_TAN = "ITEM_DEF_COST_SCR_LINE_FLD_TAN";
	public static final String CATEGORY_CODE_ITEM_DEF_COST_SCR_LINE_FLD_INT = "ITEM_DEF_COST_SCR_LINE_FLD_INT";
	public static final String CATEGORY_CODE_ITEM_DEF_COST_SCR_LINE_LE_RE = "ITEM_DEF_COST_SCR_LINE_LE_RE";


	// カテゴリコード:項目定義-固定資産・契約ダウンロード
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_FLD = "ITEM_DEF_PPFS_FLD"; // 固定資産台帳
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_FLD_PL = "ITEM_DEF_PPFS_FLD_PL"; // 固定資産償却費・社内金利明細
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_FLD_PL_SCH = "ITEM_DEF_PPFS_FLD_PL_SCH"; // 固定資産償却費・社内金利スケジュール
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_FLD_JOIN_SG_TAN = "ITEM_DEF_PPFS_FLD_JOIN_SG_TAN"; // 固定資産現物紐付(有形)
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_FLD_JOIN_SG_INT = "ITEM_DEF_PPFS_FLD_JOIN_SG_INT"; // 固定資産現物紐付(無形)
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_FLD_JOIN_KH = "ITEM_DEF_PPFS_FLD_JOIN_KH"; // 固定資産仮勘定-本勘定紐付
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_TAN = "ITEM_DEF_PPFS_FLD_SR_TAN"; // 固定資産検索結果(有形)
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_INT = "ITEM_DEF_PPFS_FLD_SR_INT"; // 固定資産検索結果(無形):資産単位
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_FLD_SR_INT_APP = "ITEM_DEF_PPFS_FLD_SR_INT_APP"; // 固定資産検索結果(無形):取得申請単位

	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_LLD = "ITEM_DEF_PPFS_LLD"; // リース・レンタル台帳
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PAY = "ITEM_DEF_PPFS_LLD_PAY"; // リース・レンタル請求明細
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PL = "ITEM_DEF_PPFS_LLD_PL"; // リース・レンタル賃借料・償却費・社内金利他明細
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PL_SCH = "ITEM_DEF_PPFS_LLD_PL_SCH"; // リース賃借料・償却費・社内金利スケジュール
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_LLD_BS_SCH = "ITEM_DEF_PPFS_LLD_BS_SCH"; // リース元本返済スケジュール
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_LLD_JOIN_SG = "ITEM_DEF_PPFS_LLD_JOIN_SG"; // リース・レンタル現物紐付
	public static final String CATEGORY_CODE_ITEM_DEF_PPFS_LLD_SR = "ITEM_DEF_PPFS_LLD_SR"; // リース・レンタル検索結果

	// カテゴリコード:WS連携URL
	public static final String CATEGORY_CODE_WS_URL_EAPP = "WS_URL_EAPP";
	public static final String INTERNAL_CODE_WS_URL_AP_GET_TAN = "AP_GET_TAN";
	public static final String INTERNAL_CODE_WS_URL_AP_GET_INT = "AP_GET_INT";
	public static final String INTERNAL_CODE_WS_URL_AP_BGN_INT = "AP_BGN_INT";
	public static final String INTERNAL_CODE_WS_URL_AP_CHANGE = "AP_CHANGE";
	public static final String INTERNAL_CODE_WS_URL_AP_ASSET = "AP_ASSET";
	public static final String INTERNAL_CODE_WS_URL_AP_LICENSE = "AP_LICENSE";
	public static final String INTERNAL_CODE_WS_URL_AP_END_LE = "AP_END_LE";
	public static final String INTERNAL_CODE_WS_URL_AP_END_RE = "AP_END_RE";
	public static final String INTERNAL_CODE_WS_URL_AP_END_TAN = "AP_END_TAN";
	public static final String INTERNAL_CODE_WS_URL_AP_END_INT = "AP_END_INT";
	public static final String INTERNAL_CODE_WS_URL_INV = "INV";
	public static final String INTERNAL_CODE_WS_URL_EAPP_ID_RESTORE = "EAPP_ID_RESTORE";

	// カテゴリコード:e申請経路
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_GET_TAN = "EAPP_ROUTE_CHARGE_AP_GET_TAN";
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_GET_INT = "EAPP_ROUTE_CHARGE_AP_GET_INT";
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_BGN_INT = "EAPP_ROUTE_CHARGE_AP_BGN_INT";
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_CHANGE = "EAPP_ROUTE_CHARGE_AP_CHANGE";
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_ASSET = "EAPP_ROUTE_CHARGE_AP_ASSET";
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_LICENSE = "EAPP_ROUTE_CHARGE_AP_LICENSE";
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_TAN = "EAPP_ROUTE_CHARGE_AP_END_TAN";
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_INT = "EAPP_ROUTE_CHARGE_AP_END_INT";
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_LE = "EAPP_ROUTE_CHARGE_AP_END_LE";
	public static final String CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_END_RE = "EAPP_ROUTE_CHARGE_AP_END_RE";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_TAN = "EAPP_ROUTE_AUTH_AP_GET_TAN";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_INT = "EAPP_ROUTE_AUTH_AP_GET_INT";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_BGN_INT = "EAPP_ROUTE_AUTH_AP_BGN_INT";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_CHANGE = "EAPP_ROUTE_AUTH_AP_CHANGE";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_ASSET = "EAPP_ROUTE_AUTH_AP_ASSET";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_LICENSE = "EAPP_ROUTE_AUTH_AP_LICENSE";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_TAN = "EAPP_ROUTE_AUTH_AP_END_TAN";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_INT= "EAPP_ROUTE_AUTH_AP_END_INT";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_LE = "EAPP_ROUTE_AUTH_AP_END_LE";
	public static final String CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_END_RE = "EAPP_ROUTE_AUTH_AP_END_RE";

	// カテゴリコード:管理者EMAIL
	public static final String CATEGORY_CODE_SYSTEM_ADMIN_EMAIL = "SYSTEM_ADMIN_EMAIL";

	// カテゴリコード:ProPlus取込データ区分
	public static final String CATEGORY_CODE_PPFS_IMPORT_DATA_TYPE = "PPFS_IMPORT_DATA_TYPE";

	// カテゴリコード:督促メール
	public static final String CATEGORY_CODE_AP_REMIND_GET_INT = "AP_REMIND_GET_INT";
	public static final String CATEGORY_CODE_AP_REMIND_END_INT = "AP_REMIND_END_INT";
	public static final String CATEGORY_CODE_AP_REMIND_INV = "AP_REMIND_INV";
	public static final String CATEGORY_CODE_AP_REMIND_COST_SCR = "AP_REMIND_COST_SCR";

	// カテゴリコード:除売却報告
	public static final String CATEGORY_CODE_AP_REPORT_END_INT = "AP_REPORT_END_INT";

	// カテゴリコード:依頼メール
	public static final String CATEGORY_CODE_AP_REQUEST_INV = "AP_REQUEST_INV";

	// カテゴリコード:依頼メール(経費負担部課精査)
	public static final String CATEGORY_CODE_AP_REQUEST_COST_SCR = "AP_REQUEST_COST_SCR";

	// カテゴリコード:注文書各種設定
	public static final String CATEGORY_CODE_REO_ORDER_SET = "REO_ORDER_SET";

	// カテゴリコード:注文書自社宛先保存先ディレクトリ取得
	public static final String CATEGORY_CODE_EASSET_PRINT_RESOURCE_PATH = "EASSET_PRINT_RESOURCE_PATH";

	// カテゴリコード:取得申請承認報告メール
	public static final String CATEGORY_CODE_AP_APPROVAL_GET_TAN = "AP_APPROVAL_GET_TAN";

	// カテゴリコード:リース/レンタル判定
	public static final String CATEGORY_CODE_PPFS_RENTAL_HANTEI_TORIHIKIKBN = "PPFS_RENTAL_HANTEI_TORIHIKIKBN";
	public static final String CATEGORY_CODE_PPFS_RENTAL_HANTEI_BUNRUICODE = "PPFS_RENTAL_HANTEI_BUNRUICODE";


	// ライセンス数区分
	public static final String LIC_QUANTITY_TYPE_LIMIT = "1"; // 有限
	public static final String LIC_QUANTITY_TYPE_UNLIMITED = "2"; // 無限
	public static final String LIC_QUANTITY_TYPE_NAME_LIMIT = "有限"; // 有限
	public static final String LIC_QUANTITY_TYPE_NAME_UNLIMITED = "無限"; // 無限

	// 申請ステータス
	// 取得申請
	public static final String AP_STATUS_GET_TAN_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_GET_TAN_APPLY = "2";		//	申請中
	public static final String AP_STATUS_GET_TAN_APPROVE = "3";		//	承認
	public static final String AP_STATUS_GET_TAN_SENDBACK = "4";	//	差戻し
	public static final String AP_STATUS_GET_TAN_REJECT = "5";		//	却下
	public static final String AP_STATUS_GET_TAN_CANCEL = "6";		//	取下げ

	// 取得申請(無形固定資産/長期前払費用)
	public static final String AP_STATUS_GET_INT_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_GET_INT_APPLY = "2";		//	申請中
	public static final String AP_STATUS_GET_INT_APPROVE = "3";		//	承認
	public static final String AP_STATUS_GET_INT_SENDBACK = "4";	//	差戻し
	public static final String AP_STATUS_GET_INT_REJECT = "5";		//	却下
	public static final String AP_STATUS_GET_INT_CANCEL = "6";		//	取下げ

	public static final String AP_STATUS_NAME_GET_INT_APPROVE = "承認済";		//	承認

	// サービス提供開始報告
	public static final String AP_STATUS_BGN_INT_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_BGN_INT_APPLY = "2";		//	申請中
	public static final String AP_STATUS_BGN_INT_APPROVE = "3";		//	承認
	public static final String AP_STATUS_BGN_INT_SENDBACK = "4";	//	差戻し
	public static final String AP_STATUS_BGN_INT_REJECT = "5";		//	却下
	public static final String AP_STATUS_BGN_INT_CANCEL = "6";		//	取下げ

	// 移動申請
	public static final String AP_STATUS_CHANGE_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_CHANGE_APPLY = "2";		//	申請中
	public static final String AP_STATUS_CHANGE_APPROVE = "3";		//	承認
	public static final String AP_STATUS_CHANGE_SENDBACK = "4";		//	差戻し
	public static final String AP_STATUS_CHANGE_REJECT = "5";		//	却下
	// 情報機器等登録申請
	public static final String AP_STATUS_ASSET_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_ASSET_APPLY = "2";		//	申請中
	public static final String AP_STATUS_ASSET_APPROVE = "3";		//	承認
	public static final String AP_STATUS_ASSET_SENDBACK = "4";		//	差戻し

	public static final String AP_STATUS_NAME_ASSET_NOAPPLY = "未申請";		//	未申請
	public static final String AP_STATUS_NAME_ASSET_APPLY = "申請中";		//	未申請
	public static final String AP_STATUS_NAME_ASSET_SENDBACK = "未申請(再)";		//	差戻し

	// ライセンス登録申請
	public static final String AP_STATUS_LICENSE_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_LICENSE_APPLY = "2";		//	申請中
	public static final String AP_STATUS_LICENSE_APPROVE = "3";		//	承認
	public static final String AP_STATUS_LICENSE_SENDBACK = "4";		//	差戻し

	public static final String AP_STATUS_NAME_LICENSE_NOAPPLY = "未申請";		//	未申請
	public static final String AP_STATUS_NAME_LICENSE_APPLY = "申請中";		//	未申請
	public static final String AP_STATUS_NAME_LICENSE_SENDBACK = "未申請(再)";		//	差戻し

	// 差戻し・却下区分
	public static final String AP_REJECT_TYPE_SENDBACK = "1"; // 差戻し
	public static final String AP_REJECT_TYPE_REJECT = "2"; // 却下

	// 除売却申請
	public static final String AP_STATUS_END_TAN_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_END_TAN_APPLY = "2";		//	申請中
	public static final String AP_STATUS_END_TAN_APPROVE = "3";		//	承認
	public static final String AP_STATUS_END_TAN_SENDBACK = "4";		//	差戻し
	public static final String AP_STATUS_END_TAN_REJECT = "5";		//	却下
	public static final String AP_STATUS_END_TAN_CANCEL = "6";		//	取下げ

	public static final String AP_STATUS_NAME_END_TAN_APPROVE = "承認済";		//	承認

	// ﾘｰｽ満了・解約申請
	public static final String AP_STATUS_END_LE_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_END_LE_APPLY = "2";		//	申請中
	public static final String AP_STATUS_END_LE_APPROVE = "3";		//	承認
	public static final String AP_STATUS_END_LE_SENDBACK = "4";		//	差戻し
	public static final String AP_STATUS_END_LE_REJECT = "5";		//	却下
	public static final String AP_STATUS_END_LE_CANCEL = "6";		//	取下げ

	// レンタル満了･解約申請
	public static final String AP_STATUS_END_RE_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_END_RE_APPLY = "2";		//	申請中
	public static final String AP_STATUS_END_RE_APPROVE = "3";		//	承認
	public static final String AP_STATUS_END_RE_SENDBACK = "4";		//	差戻し
	public static final String AP_STATUS_END_RE_REJECT = "5";		//	却下
	public static final String AP_STATUS_END_RE_CANCEL = "6";		//	取下げ

	// 棚卸
	public static final String AP_STATUS_INV_NOAPPLY = "1";		//	未申請
	public static final String AP_STATUS_INV_TAN_APPLY = "2";		//	申請中
	public static final String AP_STATUS_INV_TAN_APPROVE = "3";		//	承認
	public static final String AP_STATUS_INV_SENDBACK = "4";	//	差戻し

	public static final String AP_STATUS_NAME_INV_NOAPPLY = "未申請";		//	未申請
	public static final String AP_STATUS_NAME_INV_TAN_APPLY = "申請中";		//	申請中
	public static final String AP_STATUS_NAME_INV_TAN_APPROVE = "承認";		//	承認
	public static final String AP_STATUS_NAME_INV_SENDBACK = "未申請(再)";		//	承認

	// 申請書区分
	public static final String AP_TYPE_GET_TAN = "01"; // 取得申請(有形)
	public static final String AP_TYPE_ASSET = "02"; // 情報機器等登録申請
	public static final String AP_TYPE_LICENSE = "03"; // ライセンス登録申請
	public static final String AP_TYPE_CHANGE = "04"; // 移動申請
	public static final String AP_TYPE_GET_INT = "05"; // 取得申請(無形)
	public static final String AP_TYPE_BGN_INT = "06"; // サービス提供開始報告
	public static final String AP_TYPE_END_TAN = "07"; // 除売却申請
	public static final String AP_TYPE_END_LE = "08"; // リース満了･解約申請
	public static final String AP_TYPE_INV = "09"; // 棚卸
	public static final String AP_TYPE_END_RE = "10"; // レンタル満了･解約申請

	// 申請書タイトル
	public static final String AP_TITLE_GET_TAN_REPLACE = "故障交換申請書"; // 故障交換申請書
	public static final String AP_TITLE_ASSET = "情報機器等登録申請書"; // 情報機器等登録申請
	public static final String AP_TITLE_LICENSE = "ライセンス登録申請書"; // ライセンス登録申請
	public static final String AP_TITLE_CHANGE = "移動申請書"; // 移動申請
	public static final String AP_TITLE_GET_INT = "取得申請書(無形固定資産/長期前払費用)"; // 取得申請書(無形固定資産/長期前払費用)
	public static final String AP_TITLE_BGN_INT = "サービス提供開始報告"; // サービス提供開始報告
	public static final String AP_TITLE_INV = "棚卸完了報告"; // 棚卸

	// フラグ
	public static final String FLAG_YES = "1";
	public static final String FLAG_NO = "0";

	// 申請経路最大数
	public static final int MAX_EAPP_ROUTE_COUNT_AP_GET_TAN = 19;
	public static final int MAX_EAPP_ROUTE_COUNT_AP_GET_INT = 17;
	public static final int MAX_EAPP_ROUTE_COUNT_AP_BGN_INT = 4;
	public static final int MAX_EAPP_ROUTE_COUNT_AP_CHANGE = 22;
	public static final int MAX_EAPP_ROUTE_COUNT_AP_END_LE = 14;
	public static final int MAX_EAPP_ROUTE_COUNT_AP_END_TAN = 14;
	public static final int MAX_EAPP_ROUTE_COUNT_AP_END_INT = 11;
	public static final int MAX_EAPP_ROUTE_COUNT_AP_END_RE = 14;

	// 申請経路：表示区分
	public static final String EAPP_ROUTE_DISP_TYPE_NONE = "0";
	public static final String EAPP_ROUTE_DISP_TYPE_APPROVE = "1";
	public static final String EAPP_ROUTE_DISP_TYPE_CONFIRM = "2";
	public static final String EAPP_ROUTE_DISP_TYPE_APPROVE2 = "3";
	public static final String EAPP_ROUTE_DISP_TYPE_CONFIRM_NAME = "確認";
	public static final String EAPP_ROUTE_DISP_TYPE_APPROVE2_NAME = "決定2";


	// 経営会議承認済みe申請経路設定
	public static final String EAPP_ROUTE_AUTH_AP_GET_TAN_SPECIAL_APPROVE_SUFFIX = "-R1";
	public static final String EAPP_ROUTE_AUTH_AP_GET_TAN_THIN_CLIENT = "AA-01";
	public static final String EAPP_ROUTE_AUTH_AP_GET_TAN_PROJECT = "AA-02";
	public static final String EAPP_ROUTE_AUTH_AP_GET_TAN_REPLACE = "ZZ-ZZ";

	// 経営会議承認済みe申請経路設定
	public static final String EAPP_ROUTE_AUTH_AP_GET_INT_SPECIAL_APPROVE_SUFFIX = "-R1";
	public static final String EAPP_ROUTE_AUTH_AP_GET_INT_VER_UP_TYPE2_APPROVE_SUFFIX = "-U1";
	public static final String EAPP_ROUTE_AUTH_AP_GET_INT_THIN_CLIENT = "AA-01";
	public static final String EAPP_ROUTE_AUTH_AP_GET_INT_PROJECT = "AA-02";
	public static final String EAPP_ROUTE_AUTH_AP_GET_INT_REPLACE = "ZZ-ZZ";

	// 経営会議承認済みe申請経路設定
	public static final String EAPP_ROUTE_AUTH_AP_BGN_INT_SPECIAL_APPROVE_SUFFIX = "-R1";
	public static final String EAPP_ROUTE_AUTH_AP_BGN_INT_THIN_CLIENT = "AA-01";
	public static final String EAPP_ROUTE_AUTH_AP_BGN_INT_PROJECT = "AA-02";
	public static final String EAPP_ROUTE_AUTH_AP_BGN_INT_REPLACE = "ZZ-ZZ";

	// 経営会議承認済みe申請経路設定 (除売却固定資産_有形)
	public static final String EAPP_ROUTE_AUTH_AP_END_TAN_SPECIAL_APPROVE_SUFFIX = "-R1";
	public static final String EAPP_ROUTE_AUTH_AP_END_TAN_THIN_CLIENT = "AA-01";
	public static final String EAPP_ROUTE_AUTH_AP_END_TAN_PROJECT = "AA-02";
	public static final String EAPP_ROUTE_AUTH_AP_END_TAN_REPLACE = "ZZ-ZZ";

	// 経営会議承認済みe申請経路設定 (除売却固定資産_無形)
	public static final String EAPP_ROUTE_AUTH_AP_END_INT_SPECIAL_APPROVE_SUFFIX = "-R1";
	public static final String EAPP_ROUTE_AUTH_AP_END_INT_THIN_CLIENT = "AA-01";
	public static final String EAPP_ROUTE_AUTH_AP_END_INT_PROJECT = "AA-02";
	public static final String EAPP_ROUTE_AUTH_AP_END_INT_REPLACE = "ZZ-ZZ";

	// 経営会議承認済みe申請経路設定 (リース満了･解約)
	public static final String EAPP_ROUTE_AUTH_AP_END_LE_SPECIAL_APPROVE_SUFFIX = "-R1";
	public static final String EAPP_ROUTE_AUTH_AP_END_LE_THIN_CLIENT = "AA-01";
	public static final String EAPP_ROUTE_AUTH_AP_END_LE_PROJECT = "AA-02";
	public static final String EAPP_ROUTE_AUTH_AP_END_LE_REPLACE = "ZZ-ZZ";

	// 経営会議承認済みe申請経路設定 (レンタル満了･解約)
	public static final String EAPP_ROUTE_AUTH_AP_END_RE_SPECIAL_APPROVE_SUFFIX = "-R1";
	public static final String EAPP_ROUTE_AUTH_AP_END_RE_THIN_CLIENT = "AA-01";
	public static final String EAPP_ROUTE_AUTH_AP_END_RE_PROJECT = "AA-02";
	public static final String EAPP_ROUTE_AUTH_AP_END_RE_REPLACE = "ZZ-ZZ";

	// 移動申請経路：使用者/無形管理担当者ラベル
	public static final String EAPP_ROUTE_CHARGE_AP_CHANGE_USE_IHOL_STAFF = "使用者/無形管理担当者";
	public static final String EAPP_ROUTE_CHARGE_AP_CHANGE_USE_STAFF = "使用者";
	public static final String EAPP_ROUTE_CHARGE_AP_CHANGE_IHOL_STAFF = "無形管理担当者";

	// e申請ルート削除判別用
	public static final String EAPP_ROUTE_DELETE = "-";

	// 項目定義のステータス毎の制限設定開始VALUEポジション
	public static final int ITEM_DEF_STAT_POS = 12;
	public static final String ITEM_DEF_READ_ONLY = "0";
	public static final String ITEM_DEF_REQUIRED = "2";

	// 取得形態
	public static final String ASSET_GET_TYPE_TAN = "02"; // 取得形態(固定資産)
	public static final String ASSET_GET_TYPE_LEASE = "03"; // 取得形態(リース)
	public static final String ASSET_GET_TYPE_RENTAL = "04"; // 取得形態(レンタル)
	public static final String ASSET_GET_TYPE_VM = "09"; // 取得形態(仮想機器)

	// 資産区分
	public static final String ASSET_TYPE_VM = "V"; // 資産区分(仮想機器)

	// 部課コードサイズ
	public static final int SECTION_CODE_LENGTH = 6; // 人事部課
	public static final int COST_SECTION_CODE_LENGTH = 10; // 経費負担部課

	//	取得申請書
	public static final int APPLICATION_ID_LENGTH = 9;

	// アクセス権限
	public static final String ACCESS_LEVEL_SECTION_GENERAL = "C"; // 一般
	public static final String ACCESS_LEVEL_SECTION_MANAGER = "B"; // 部署長・資産管理担当者
	public static final String ACCESS_LEVEL_ADMIN_COMPANY = "A"; // 全社権限
	public static final String ACCESS_LEVEL_ADMIN_COMPANY_LIMIT = "A*"; // 全社権限一部制限
	public static final String ACCESS_LEVEL_ADMIN_SYSTEM = "S"; // 全社権限(システム管理者)

	// クラウド区分
	public static final String CLOUD_TYPE_CLOUD = "2"; // クラウド

	// 取得形態（有形）
	public static final String AP_GET_TYPE_TAN = "3"; // 固定資産/備品
	public static final String AP_GET_TYPE_LEASE = "2"; // リース
	public static final String AP_GET_TYPE_RENTAL = "1"; // レンタル
	public static final String AP_GET_TYPE_REPLACE = "4"; // 故障交換
	public static final String AP_GET_TYPE_KARIUKE = "6"; // 借受
	public static final String AP_GET_TYPE_TAKE = "7"; // 譲受
	public static final String AP_GET_TYPE_RENTAL_GOODS = "9"; // 貸出商品
	public static final String AP_GET_TYPE_PRE_DELIVER_GOODS = "A"; // 納入前商品

	// 取得形態（無形）
	public static final String AP_GET_TYPE_INT1 = "1"; // 社内使用ソフトウェア
	public static final String AP_GET_TYPE_INT2 = "2"; // 市販目的ソフトウェア
	public static final String AP_GET_TYPE_INT3 = "3"; // 長期前払費用/その他無形固定資産

	// 市販目的SW：会計計上区分
	public static final String AP_GET_INT_MKT_TASK1 = "1"; // ソフトウェア仮
	public static final String AP_GET_INT_MKT_TASK2 = "2"; // 販売管理費
	public static final String AP_GET_INT_MKT_TASK3 = "3"; // 研究開発費

	// サービス提供開始報告タイプ
	public static final String AP_BGN_TYPE_INT1 = "1"; // 社内使用ソフトウェア
	public static final String AP_BGN_TYPE_INT2 = "2"; // 市販目的ソフトウェア
	public static final String AP_BGN_TYPE_INT3 = "3"; // 研究開発

	//	計上区分
	public static final String ADD_TYPE1 = "1"; // 資産
	public static final String ADD_TYPE2 = "2"; // 費用

	// 取得申請金額範囲に応じた経費負担項目使用
	public static final String AP_GET_AMOUNT_RANGE_USE_COST_SEC_BOTH = "1"; // 両方
	public static final String AP_GET_AMOUNT_RANGE_USE_COST_SEC_SEC = "2"; // 部課使用
	public static final String AP_GET_AMOUNT_RANGE_USE_COST_SEC_TYPE= "3"; // 販管/原価区分使用

	// 販売管理費/原価区分
	public static final String COST_TYPE_HANKAN = "1"; // 販売管理費
	public static final String COST_TYPE_GENKA = "2"; // 原価
	public static final String COST_TYPE_PPFS_HANKAN = "2"; // 販売管理費
	public static final String COST_TYPE_PPFS_GENKA = "1"; // 原価

	public static final String ASSET_MANAGE_TYPE_VALUE2_SECTION_DEPLOY_EXCEPT  = "0"; // 情シス配備以外
	public static final String ASSET_MANAGE_TYPE_VALUE2_THINCLIENT = "1"; // シンクライアント
	public static final String ASSET_MANAGE_TYPE_VALUE2_TAKEN_PC = "2"; // 持ち出しPC

	// メニューID
	public static final String MENU_ID_ASSET_SEARCH = "01-01"; // 情報機器
	public static final String MENU_ID_LICENSE_SEARCH = "02-01"; // ライセンス
	public static final String MENU_ID_FLD_INT_SEARCH = "13-02"; // 無形固定資産

	// 最大バイト数
	public static final int MAX_CHAR_SIZE_DELETE_REASON = 1024; // 抹消理由
	public static final int MAX_CHAR_SIZE_CODE_NAME_VALUE = 2048; // 汎用マスタVALUE
	public static final int MAX_CHAR_SIZE_COM_OP_DESCRIPTION = 2048; // 汎用ログ適用
	public static final int MAX_CHAR_SIZE_INV_LINE_COMMENT = 128; // 棚卸コメント
	public static final int MAX_CHAR_SIZE_COST_SCR_LINE_COMMENT = 1024; // 経費負担部課精査コメント

	// ライセンス資産区分
	public static final String LICENSE_ASSET_TYPE_BIHIN = "A";	// 備品・雑費
	public static final String LICENSE_ASSET_TYPE_INTAN = "B";	// 無形固定資産
	public static final String LICENSE_ASSET_TYPE_FREE = "C";	// フリーウェア
	public static final String LICENSE_ASSET_TYPE_TAN = "D";	// 有形固定資産
	public static final String LICENSE_ASSET_TYPE_LEASE = "E";	// リース
	public static final String LICENSE_ASSET_TYPE_RENTAL = "F";	// レンタル

	// ライセンス許諾区分
	public static final String LICENSE_USE_TYPE_SECTION = "1"; // 部署限定
	public static final String LICENSE_USE_TYPE_COMPANY = "2"; // 会社限定
	public static final String LICENSE_USE_TYPE_ALL_COMPANY = "3"; // 全Ｇ会社利用

	// タームライセンス契約区分
	public static final String LICENSE_TERM_CONTRACT_TYPE_NEW = "1"; // 新規
	public static final String LICENSE_TERM_CONTRACT_TYPE_UPDATE = "2"; // 更新

	// ライセンス数量管理明細区分
	public static final String LICENSE_LINE_QTY_TYPE_USE = "1"; // 許諾
	public static final String LICENSE_LINE_QTY_TYPE_RENTAL = "2"; // 貸出

	public static final String OIR_DEFAULT = "99999999"; // OIRデフォルト値

	public static final String STAFF_CODE_SYSTEM = "eAsset"; // システム自動処理実行時の擬似社員番号

	// 検索対象
	public static final String SEARCH_SCOPE_TYPE_EDIT_ONLY = "1"; // 管轄分
	public static final String SEARCH_SCOPE_TYPE_ALL = "2"; // すべて

	// 移動申請区分
	public static final String AP_CHANGE_TYPE_AST = "1"; // 情報機器
	public static final String AP_CHANGE_TYPE_LIC = "2"; // ライセンス
	public static final String AP_CHANGE_TYPE_INT_EA = "3"; // 無形固定資産（現物）
	public static final String AP_CHANGE_TYPE_LE_RE = "A"; // リース・レンタル
	public static final String AP_CHANGE_TYPE_TAN = "B"; // 有形固定資産
	public static final String AP_CHANGE_TYPE_INT = "C"; // 無形固定資産

	// 移動申請使用区分
	public static final String AP_CHANGE_USE_TYPE_NONE = "0"; // 未使用
	public static final String AP_CHANGE_USE_TYPE_ALL = "1"; // 使用
	public static final String AP_CHANGE_USE_TYPE_SYS_DEPLOY = "2"; // 情シス配備のみ使用

	// リース満了・解約申請_申請区分
	public static final String AP_END_LE_TYPE_RE = "11"; // 再リース
	public static final String AP_END_LE_TYPE_RETURN = "12"; // 返却
	public static final String AP_END_LE_TYPE_CANCEL = "2"; // 中途解約
	public static final String AP_END_LE_TYPE_RE_RETURN = "1"; // 再リース/返却

	// リース満了・解約申請_物件明細申請区分
	public static final String AP_END_LE_LINE_TYPE_RETURN = "1";	// 返却
	public static final String AP_END_LE_LINE_TYPE_RE = "2";		// 再リース
	public static final String AP_END_LE_LINE_TYPE_BUY = "3";		// 買取

	// レンタル満了･解約申請_申請区分
	public static final String AP_END_RE_TYPE_RE = "11"; // 再レンタル
	public static final String AP_END_RE_TYPE_RETURN = "12"; // 返却
	public static final String AP_END_RE_TYPE_CANCEL = "2"; // 中途解約
	public static final String AP_END_RE_TYPE_RE_RETURN = "1"; // 再レンタル/返却

	// レンタル満了･解約申請_物件明細申請区分
	public static final String AP_END_RE_LINE_TYPE_RETURN = "1";	// 返却
	public static final String AP_END_RE_LINE_TYPE_RE = "2";		// 再レンタル
	public static final String AP_END_RE_LINE_TYPE_BUY = "3";		// 買取

	// 共通操作ログ用-機能
	public static final String COM_OP_FUNCTION_ROLE_SETTING_ADMIN = "管理者設定(全社権限)";
	public static final String COM_OP_FUNCTION_ROLE_SETTING_SECTION = "管理者設定(資産管理担当者)";
	public static final String COM_OP_FUNCTION_SECTION_ROLE_PROFILE = "資産管理担当者プロファイル";
	public static final String COM_OP_FUNCTION_CODE_NAME_SETTING = "汎用マスタ管理";
	public static final String cOM_OP_FUNCTION_SOFTWARE_MAKER_SETTING = "ソフトウェアメーカー管理";
	public static final String cOM_OP_FUNCTION_SOFTWARE_SETTING = "ソフトウェア管理";

	public static final String COM_OP_FUNCTION_AP_GET_TAN_SEARCH = "取得申請-照会/修正";
	public static final String COM_OP_FUNCTION_AP_GET_TAN_LINE_AST = "取得申請-資産(機器)明細";
	public static final String COM_OP_FUNCTION_AP_GET_TAN_LINE_LIC = "取得申請-ライセンス明細";
	public static final String COM_OP_FUNCTION_AP_GET_TAN_MASTER = "取得申請-入力可能マスタ";
	public static final String COM_OP_FUNCTION_AP_GET_INT_SEARCH = "取得申請(無形固定資産/長期前払費用)-照会/修正";
	public static final String COM_OP_FUNCTION_AP_GET_INT_LINE_AST = "取得申請(無形固定資産/長期前払費用)-資産明細";
	public static final String COM_OP_FUNCTION_AP_GET_INT_MASTER = "取得申請(無形固定資産/長期前払費用)-入力可能マスタ";
	public static final String COM_OP_FUNCTION_AP_BGN_INT_SEARCH = "サービス提供開始報告-照会/修正";
	public static final String COM_OP_FUNCTION_AP_BGN_INT_LINE_AST = "サービス提供開始報告-資産明細";
	public static final String COM_OP_FUNCTION_AP_BGN_INT_MASTER = "サービス提供開始報告-入力可能マスタ";
	public static final String COM_OP_FUNCTION_AP_ASSET_SEARCH = "情報機器等登録申請-照会/修正";
	public static final String COM_OP_FUNCTION_AP_ASSET_BULK_CREATE = "情報機器等登録申請-一括作成";
	public static final String COM_OP_FUNCTION_AP_ASSET_VM = "情報機器等登録申請(仮想機器)-一括作成";
	public static final String COM_OP_FUNCTION_AP_LICENSE_SEARCH = "ライセンス登録申請-照会/修正";
	public static final String COM_OP_FUNCTION_AP_LICENSE_BULK_CREATE = "ライセンス登録申請-一括作成";
	public static final String COM_OP_FUNCTION_AP_CHANGE_SEARCH = "移動申請-照会/修正";
	public static final String COM_OP_FUNCTION_ASSET_SEARCH = "情報機器等-照会/修正";
	public static final String COM_OP_FUNCTION_ASSET_MASTER = "情報機器等-入力可能マスタ";
	public static final String COM_OP_FUNCTION_LICENSE_SEARCH = "ライセンス-照会/修正";
	public static final String COM_OP_FUNCTION_LICENSE_MASTER = "ライセンス-入力可能マスタ";
	public static final String COM_OP_FUNCTION_FLD_SEARCH = "固定資産-照会/管理帳票";
	public static final String COM_OP_FUNCTION_FLD_MASTER = "固定資産-入力可能マスタ";
	public static final String COM_OP_FUNCTION_LLD_SEARCH = "リース/レンタル-照会/管理帳票";
	public static final String COM_OP_FUNCTION_LLD_MASTER = "リース/レンタル-入力可能マスタ";
	public static final String COM_OP_FUNCTION_AP_END_TAN_SEARCH = "除売却申請-有形固定資産-照会/修正";
	public static final String COM_OP_FUNCTION_AP_END_INT_SEARCH = "除売却申請-無形固定資産-照会/修正";
	public static final String COM_OP_FUNCTION_AP_END_LE_SEARCH = "リース満了･解約申請-照会/修正";
	public static final String COM_OP_FUNCTION_AP_END_RE_SEARCH = "レンタル満了･解約申請-照会/修正";
	public static final String COM_OP_FUNCTION_COST_SCR_MASTER = "経費負担部課精査データ作成-入力可能マスタ";
	public static final String COM_OP_FUNCTION_COST_SCR_SUM = "経費負担部課精査-集計";
	public static final String COM_OP_FUNCTION_COST_SCR_LINE = "経費負担部課精査-明細";

	public static final String COM_OP_FUNCTION_LICENSE_REPORT_ALLOC_ASSET = "ライセンス-管理帳票-割当情報(機器から検索)";
	public static final String COM_OP_FUNCTION_LICENSE_REPORT_ALLOC_LICENSE = "ライセンス-管理帳票-割当情報(ライセンスから検索)";
	public static final String COM_OP_FUNCTION_LICENSE_REPORT_UPGRADE = "ライセンス-管理帳票-アップグレード情報";

	// 共通操作ログ用-操作
	public static final String COM_OP_OPERATION_DOWNLOAD = "ダウンロード";
	public static final String COM_OP_OPERATION_CREATE = "追加";
	public static final String COM_OP_OPERATION_UPDATE = "更新";
	public static final String COM_OP_OPERATION_DELETE = "削除";
	public static final String COM_OP_OPERATION_NEW = "新規作成";

	// 一括更新ログ用-操作
	public static final String BULK_UPDATE_STATUS_READ = "ファイル読込中";
	public static final String BULK_UPDATE_STATUS_UPDATE = "更新中";
	public static final String BULK_UPDATE_STATUS_END = "終了";
	public static final String BULK_UPDATE_STATUS_CANCEL_REQUEST = "終了中";

	// ProPlus取込ステータス
	public static final String PPFS_IMPORT_EXEC_STATUS_PROCESSING = "1"; // 実行中
	public static final String PPFS_IMPORT_EXEC_STATUS_SUCCESS = "2"; // 正常終了
	public static final String PPFS_IMPORT_EXEC_STATUS_ERROR = "3"; // 異常終了

	// ProPlus取込データ区分
	public static final String PPFS_IMPORT_DATA_TYPE_FLD = "1"; // 固定資産
	public static final String PPFS_IMPORT_DATA_TYPE_LLD = "2"; // リース・レンタル
	public static final String PPFS_IMPORT_DATA_TYPE_FLD_SCH = "3"; // 固定資産-予測取込
	public static final String PPFS_IMPORT_DATA_TYPE_LLD_SCH = "4"; // リース・レンタル-予測取込

	public static final String PPFS_SUPPORT_AST_CODE = "00001"; // 機器紐付コード
	public static final String PPFS_SUPPORT_COST_SEC_HIST = "00002"; // 経費負担部課履歴紐付コード

	// 棚卸データ作成ステータス
	public static final String INV_CREATE_STATUS_PROCESSING = "1"; // 実行中
	public static final String INV_CREATE_STATUS_SUCCESS = "2"; // 正常終了
	public static final String INV_CREATE_STATUS_ERROR = "3"; // 異常終了

	// 無形取得申請バージョンアップタイプ
	public static final String AP_GET_INT_VER_UP_TYPE_NONE = "0"; // 未バージョンアップ

	public static final String PPFS_FLD_TAN = "1";	//	有形
	public static final String PPFS_FLD_INT = "2";	//	無形

	// 償却資産区分
	public static final String PPFS_FLD_SKKSHISANKBN_TAN1 = "1"; // 有形
	public static final String PPFS_FLD_SKKSHISANKBN_TAN2 = "3"; // 無形
	public static final String PPFS_FLD_SKKSHISANKBN_INT1 = "2"; // 生物
	public static final String PPFS_FLD_SKKSHISANKBN_INT2 = "4"; // 繰延資産

	// ProPlusソフトウェア判別
	// 社内使用SW
	public static final String PPFS_FLD_SHURUICD_INT_SW1 = "PPFS_FLD_SHURUICD_INT_SW1";
	// 市販目的SW
	public static final String PPFS_FLD_SHURUICD_INT_SW2 = "PPFS_FLD_SHURUICD_INT_SW2";

	// スケジュール最大表示期間
	public static final int MAX_PPFS_SCH_CALC_YEAR = 10;

	// ProPlusマスタコードの長さ
	public static final int PPFS_MS01_CD_LEN = 2; // 種類
	public static final int PPFS_MS03_CD_LEN = 6; // 分類
	public static final int PPFS_MS07_CD_LEN = 7; // 設置場所
	public static final int PPFS_GROUP_CD_LEN = 10; // 案件グループ
	public static final char PPFS_MS_CD_PAD_CHAR = '0'; //

	// 固定資産金額境界
	public static final long ASSET_AMOUNT_BORDER = 200000;

	// 移動申請明細区分
	public static final String AP_CHANGE_LINE_CONTRACT_TYPE_LEASE = "1"; // リース
	public static final String AP_CHANGE_LINE_CONTRACT_TYPE_RENTAL = "2"; // レンタル

	public static final String AP_CHANGE_LINE_FLD_TYPE_TAN = "3"; // 有形
	public static final String AP_CHANGE_LINE_FLD_TYPE_INT = "4"; // 無形

	// リース取引判定
	public static final String PPFS_LLD_LATORIHIKIKBN_RENTAL = "3"; // レンタル

	//リース/レンタル判定
	public static final String PPFS_RENTAL_HANTEI_LEASE = "0"; // リース
	public static final String PPFS_RENTAL_HANTEI_RENTAL = "1"; // レンタル

	// 取得申請(無形)明細分類カテゴリ
	public static final String AP_GET_INT_LINE_AST_CATEGORY_TYPE_LIC = "1"; // ライセンス
	public static final String AP_GET_INT_LINE_AST_CATEGORY_TYPE_OWN = "2"; // 自社製作

	// 取得申請(無形)明細分類
	public static final String AP_GET_INT_LINE_AST_CATEGORY_CODE_OUT = "3"; // 外部委託

	public static final String INV_UNKNOWN_SECTION_CODE = "Z1";		//	保有部署不明
	public static final String INV_UNKNOWN_ASSET = "Z2";		//	資産情報不明

	public static final String INV_ASSET_TYPE_FLD_TAN = "1";		//	有形固定資産
	public static final String INV_ASSET_TYPE_FLD_RO = "2";		//	資産除去費用
	public static final String INV_ASSET_TYPE_FLD_INT = "3";		//	無形固定資産(本勘定)
	public static final String INV_ASSET_TYPE_FLD_INT_S = "4";		//	無形固定資産(仮勘定)
	public static final String INV_ASSET_TYPE_LE = "5";		//	リース資産
	public static final String INV_ASSET_TYPE_RE = "6";		//	レンタル資産
	public static final String INV_ASSET_TYPE_EQUIP = "7";		//	備品等

	public static final String INV_STATUS_NAME_UNDECIDED = "未実地";		//	未実地
	public static final String INV_STATUS_NAME_OWN = "有";		//	有
	public static final String INV_STATUS_NAME_NOT_OWN = "無";		//	無
	public static final String INV_STATUS_NAME_NOT_INV = "棚卸対象外";		//	棚卸対象外

	public static final String INV_STATUS_UNDECIDED = "1";		//	未実地
	public static final String INV_STATUS_OWN = "2";		//	有
	public static final String INV_STATUS_NOT_OWN = "3";		//	無
	public static final String INV_STATUS_NOT_INV = "4";		//	棚卸対象外

	public static final String INV_STATUS_YES = "Yes";		//	無
	public static final String INV_STATUS_NO = "No";		//	棚卸対象外

	public static final String INV_MAIL_UN_SEND = "1";		//	未送信
	public static final String INV_MAIL_SENDING = "2";		//	送信中
	public static final String INV_MAIL_SEND = "3";			//	送信済

	public static final String INV_LINK_TYPE_JOIN = "1"; // 紐付有り
	public static final String INV_LINK_TYPE_UNKNOWN_SECTION_CODE= "2"; // 保有部署不明
	public static final String INV_LINK_TYPE_UNKNOWN_ASSET = "3"; // 資産情報不明

	// 権限コード
	public static final String ROLE_CODE_SECTION_SUPERIOR = "B02"; // 部署長

	public static final String PROJECT_INDIRECTION = "IN";	//	間接プロジェクト
	public static final String PROJECT_CONTRACT = "CN";		//	契約プロジェクト
	public static final String PROJECT_ASSET = "FA";		//	資産プロジェクト

	public static final String REO_ORDER_TYPE_RECOMMEND = "01";	//	推奨機
	public static final String REO_ORDER_TYPE_RECOMMEND_OTHER = "02";	//	推奨機以外
	public static final String REO_ORDER_TYPE_FLAT_RENTAL = "03";	// フラットレンタル
	public static final String REO_ORDER_TYPE_THINCLIENT = "04";	//	シンクライアント

	// 受付番号最大サイズ
	public static final int REO_RECEPT_LENGTH = 4; // 人事部課

	//	レンタル会社-発注区分
	public static final String LE_PO_TYPE_NO = "1";	//	発注無
	public static final String LE_PO_TYPE_SPOT = "2";	//	現場発注
	public static final String LE_PO_TYPE_SEC = "3";	//	主管部発注

	public static final String SCR_STATUS_UNDECIDED = "1";		//	未実地
	public static final String SCR_STATUS_OK = "2";		//	OK
	public static final String SCR_STATUS_NG = "3";		//	NG

	public static final String SCR_STATUS_NAME_UNDECIDED = "未実地";		//	未実地
	public static final String SCR_STATUS_NAME_OK = "OK";		//	OK
	public static final String SCR_STATUS_NAME_NG = "要対応";		//	NG

	public static final String SCR_TYPE_FLD_TAN = "1";		//	有形固定資産
	public static final String SCR_TYPE_FLD_INT = "2";		//	無形固定資産
	public static final String SCR_TYPE_LE = "3";		//	リース
	public static final String SCR_TYPE_RE = "4";		//	レンタル

	// 経費負担部課精査データ作成ステータス
	public static final String COST_SCR_CREATE_STATUS_PROCESSING = "1"; // 実行中
	public static final String COST_SCR_CREATE_STATUS_SUCCESS = "2"; // 正常終了
	public static final String COST_SCR_CREATE_STATUS_ERROR = "3"; // 異常終了

	// 経費負担部課精査データ作成メール送信ステータス
	public static final String COST_SCR_SEND_MAIL_STATUS_UN_SEND = "1"; // 未送信
	public static final String COST_SCR_SEND_MAIL_STATUS_SEND = "2"; // 正常終了
	public static final String COST_SCR_SEND_MAIL_STATUS_SEND_ERROR = "3"; // 異常終了

	// 最大経費負担部課精査更新エラー数
	public static final int MAX_COST_SCR_SECTION_UPDATE_ERROR_COUNT = 10;

	// カテゴリコード:経費負担部課精査：作成・公開制御
	public static final String CATEGORY_CODE_COST_SCR_CONTROL = "COST_SCR_CONTROL";

	// プロジェクトカテゴリ区分
	public static final String PROJECT_GATEGORY_COST_DEP = "1"; // 減価償却プロジェクト
	public static final String PROJECT_GATEGORY_AST = "2"; // 資産プロジェクト

}
