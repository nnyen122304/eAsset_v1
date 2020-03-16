//メッセージ Ver.1.0
package jp.co.ctcg.easset.flex_common;

public class MsgManager{

	//  メッセージタイプ
	public static final int iMsgTypeInf = 1;  //  情報
	public static final int iMsgTypeErr = 2;  //  エラー
	public static final int iMsgTypeWrn = 3;   //  警告

	// メッセージID
	/** SqlMap生成エラー:$1 */
	public static final String MSGID100001 = "100001";
	/** 擬似ログイン処理に失敗しました。 */
	public static final String MSGID100002 = "100002";
	/** ログインユーザー情報の取得に失敗しました。 */
	public static final String MSGID100003 = "100003";
	/** $1の登録に失敗しました。管理者にお問合せください。 */
	public static final String MSGID100012 = "100012";
	/** $1の更新に失敗しました。管理者にお問合せください。 */
	public static final String MSGID100013 = "100013";
	/** $1の削除に失敗しました。管理者にお問合せください。 */
	public static final String MSGID100014 = "100014";
	/** EJBの呼び出しに失敗しました。：$1 */
	public static final String MSGID100015 = "100015";
	/** SQLの実行に失敗しました。（SQL名：$1） */
	public static final String MSGID100016 = "100016";
	/** プロファイル情報の取得に失敗しました。（$1） */
	public static final String MSGID100017 = "100017";
	/** 現在は選択できない値が入力されています。再度入力を行ってください。($1) */
	public static final String MSGID200028 = "200028";
	/** 現在は選択できない組み合わせで入力されています。再度入力を行ってください。($1・$2) */
	public static final String MSGID200029 = "200029";
	/** 選択した契約条件内容の組み合わせは使用できません。 */
	public static final String MSGID200038 = "200038";
	/** レコードのロックに失敗しました。 */
	public static final String MSGID200039 = "200039";
	/** この情報は既に他のユーザーによって変更されています。再度検索を行ってください。 */
	public static final String MSGID200040 = "200040";
	/** $1処理に失敗しました。 */
	public static final String MSGID200041 = "200041";

	// メッセージ
	private static final String MSGNM100001 = "SqlMap生成エラー:$1";
	private static final String MSGNM100002 = "擬似ログイン処理に失敗しました。";
	private static final String MSGNM100003 = "ログインユーザー情報の取得に失敗しました。";
	private static final String MSGNM100012 = "$1の登録に失敗しました。管理者にお問合せください。";
	private static final String MSGNM100013 = "$1の更新に失敗しました。管理者にお問合せください。";
	private static final String MSGNM100014 = "$1の削除に失敗しました。管理者にお問合せください。";
	private static final String MSGNM100015 = "EJBの呼び出しに失敗しました。：$1";
	private static final String MSGNM100016 = "SQLの実行に失敗しました。（SQL名：$1）";
	private static final String MSGNM100017 = "プロファイル情報の取得に失敗しました。（$1）";
	private static final String MSGNM200028 = "現在は選択できない値が入力されています。再度入力を行ってください。($1)";
	private static final String MSGNM200029 = "現在は選択できない組み合わせで入力されています。再度入力を行ってください。($1・$2)";
	private static final String MSGNM200038 = "選択した契約条件内容の組み合わせは使用できません。";
	private static final String MSGNM200039 = "レコードのロックに失敗しました。";
	private static final String MSGNM200040 = "この情報は既に他のユーザーによって変更されています。再度検索を行ってください。";
	private static final String MSGNM200041 = "$1処理に失敗しました。";

	public MsgManager()
	{
	}

	/************************************************************************
	 * .Proc    :   メッセージを取得する。
	 * .Note    :
	 * .InOut   :   String sId   メッセージID
	 *              String param 置き換え文字列
	 * .Return  :   なし
	 * .Date    :   2009/11/11
	 * .Auth    :   リヨン
	 ************************************************************************/
	public static String getMessage( String sId, String... param ){
		String sResult;
		//  メッセージの取得
		sResult = cnvMessage( getMsgNm( sId ), param );
		return sResult;
	}

	/************************************************************************
	 * .Proc    :   メッセージを置換する
	 * .Note    :
	 * .InOut   :   String sMsg  メッセージ
	 *              String param 置き換え文字列
	 * .Return  :   なし
	 * .Date    :   2009/11/11
	 * .Auth    :   リヨン
	 ************************************************************************/
	public static String cnvMessage( String sMsg, String param[] ){
		String sResult;
		String sSimbol = "$";
		String sMark;
		sResult = sMsg;
		for ( int i = 0; i < param.length; i++ ){
			sMark = sSimbol + Integer.toString(i+1);
			while( sResult.indexOf(sMark) >= 0 ){
				sResult = sResult.replace(sMark,param[i]);
			}
		}
		return sResult;
	}

	/************************************************************************
	 * .Proc    :   メッセージを取得する。
	 * .Note    :
	 * .InOut   :   String sId  メッセージID
	 * .Return  :   なし
	 * .Date    :   2009/11/11
	 * .Auth    :   リヨン
	 ************************************************************************/
	private static String getMsgNm( String sId ){
		String sResult = "";
		if ( sId == MSGID100001){      //  SqlMap生成エラー:$1
			sResult = MSGNM100001;
		}else if( sId == MSGID100002 ){ //  擬似ログイン処理に失敗しました。
			sResult = MSGNM100002;
		}else if( sId == MSGID100003 ){ //  ログインユーザー情報の取得に失敗しました。
			sResult = MSGNM100003;
		}else if( sId == MSGID100012 ){ //  $1の登録に失敗しました。管理者にお問合せください。
			sResult = MSGNM100012;
		}else if( sId == MSGID100013 ){ //  $1の更新に失敗しました。管理者にお問合せください。
			sResult = MSGNM100013;
		}else if( sId == MSGID100014 ){ //  $1の削除に失敗しました。管理者にお問合せください。
			sResult = MSGNM100014;
		}else if( sId == MSGID100015 ){ //  EJBの呼び出しに失敗しました。：$1
			sResult = MSGNM100015;
		}else if( sId == MSGID100016 ){ //  SQLの実行に失敗しました。（SQL名：$1）
			sResult = MSGNM100016;
		}else if( sId == MSGID100017 ){ //  プロファイル情報の取得に失敗しました。（$1）
			sResult = MSGNM100017;
		}else if( sId == MSGID200028 ){ //  現在は選択できない値が入力されています。再度入力を行ってください。($1)
			sResult = MSGNM200028;
		}else if( sId == MSGID200029 ){ //  現在は選択できない組み合わせで入力されています。再度入力を行ってください。($1・$2)
			sResult = MSGNM200029;
		}else if( sId == MSGID200038 ){ //  選択した契約条件内容の組み合わせは使用できません。
			sResult = MSGNM200038;
		}else if( sId == MSGID200039 ){ //  レコードのロックに失敗しました。
			sResult = MSGNM200039;
		}else if( sId == MSGID200040 ){ //  この情報は既に他のユーザーによって変更されています。再度検索を行ってください。
			sResult = MSGNM200040;
		}else if( sId == MSGID200041 ){ //  $1処理に失敗しました。
			sResult = MSGNM200041;
		}
		return sResult;
	}

	/************************************************************************
	 * .Proc    :   メッセージのタイプ(エラー、警告、情報)を取得。
	 * .Note    :
	 * .InOut   :   String sId  メッセージID
	 * .Return  :   int         メッセージ種別
	 * .Date    :   2009/11/11
	 * .Auth    :   リヨン
	 ************************************************************************/
	// private static int getMsgType( String sId ){
	// 	int iResult = 0;
	// 	if ( sId == MSGID100001){      //  SqlMap生成エラー:$1
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID100002 ){ //  擬似ログイン処理に失敗しました。
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID100003 ){ //  ログインユーザー情報の取得に失敗しました。
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID100012 ){ //  $1の登録に失敗しました。管理者にお問合せください。
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID100013 ){ //  $1の更新に失敗しました。管理者にお問合せください。
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID100014 ){ //  $1の削除に失敗しました。管理者にお問合せください。
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID100015 ){ //  EJBの呼び出しに失敗しました。：$1
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID100016 ){ //  SQLの実行に失敗しました。（SQL名：$1）
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID100017 ){ //  プロファイル情報の取得に失敗しました。（$1）
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID200028 ){ //  現在は選択できない値が入力されています。再度入力を行ってください。($1)
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID200029 ){ //  現在は選択できない組み合わせで入力されています。再度入力を行ってください。($1・$2)
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID200038 ){ //  選択した契約条件内容の組み合わせは使用できません。
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID200039 ){ //  レコードのロックに失敗しました。
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID200040 ){ //  この情報は既に他のユーザーによって変更されています。再度検索を行ってください。
	// 		iResult = iMsgTypeErr;
	// 	}else if( sId == MSGID200041 ){ //  $1処理に失敗しました。
	// 		iResult = iMsgTypeErr;
	// 	}
	// 	return iResult;
	// }
}
