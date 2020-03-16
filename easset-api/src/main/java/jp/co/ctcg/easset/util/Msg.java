package jp.co.ctcg.easset.util;

public class Msg {
	// 共通エラーメッセージ

	public static final String ERR_MSG = "$1";
	public static final String ERR_MSG_EDIT_AUTH = "修正権限がありません。";
	public static final String ERR_MSG_VER = "画面編集中に、他者によりデータが更新されたため、内容を反映できません。\n再度、検索からやり直してください。"; // バージョン
	public static final String ERR_MSG_VER_LIST = "一覧表示後に、他者によりデータが更新されたため、内容を反映できません。\n再度、検索からやり直してください。"; // バージョン
	public static final String ERR_MSG_STAT_LIST = "選択可能なステータスは $1 です。"; // バージョン
	public static final String ERR_MSG_ITEM = "$1 : $2"; //
	public static final String ERR_MSG_ITEM_MAX_SIZE = "$1 : 入力可能な文字数は半角で $2 文字までです。"; // 最大文字数
	public static final String ERR_MSG_ITEM_MIN_VALUE = "$1 : $2 以上の値を入力してください。"; // 最小値
	public static final String ERR_MSG_ITEM_MAX_VALUE = "$1 : $2 以下の値を入力してください。"; // 最大値
	public static final String ERR_MSG_ITEM_RESTRICT = "$1 : 不正な値が入力されました。"; // 最大値
	public static final String ERR_MSG_ITEM_RESTRICT_DISP_VAL = "$1 : 不正な値が入力されました。($2)"; // 最大値
	public static final String ERR_MSG_ITEM_REQUIRED = "$1 : 必須入力です。"; // 必須
	public static final String ERR_MSG_ITEM_REQUIRED_CONDITIONAL = "$1 : $2必須入力です。"; // 条件付必須

	/**
	 * メッセージにパラメータをバインドして取得
	 * @param sMsg メッセージ
	 * @param param メッセージパラメータ
	 * @return
	 */
	public static String getBindMessage(String sMsg, String... param){
		String sResult;
		String sSimbol = "$";
		String sMark;
		sResult = sMsg;
		for ( int i = 0; i < param.length; i++ ){
			sMark = sSimbol + Integer.toString(i+1);
			while( sResult.indexOf(sMark) >= 0 ){
				sResult = sResult.replace(sMark,Function.nvl(param[i], ""));
			}
		}
		return sResult + Function.getAppExceptionMsgDivStr();
	}
}
