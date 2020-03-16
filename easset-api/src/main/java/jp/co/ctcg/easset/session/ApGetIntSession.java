/*===================================================================
 * ファイル名 : ApGetIntSession.java
 * 概要説明   : 取得申請(無形)セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.co.ctcg.easset.dto.ap_get_int.ApGetInt;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntSC;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetIntSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("ap-get-int")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ApGetIntSession {

	/**
	 * 取得申請(無形)検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("searchApGetInt")
	public List<ApGetIntSR> searchApGetInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetIntSC searchParam);

	/**
	 * 取得申請(無形)検索結果CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param lineOutputFlag false:申請書単位,true:明細単位
	 * @return CSVファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApGetIntCsv")
	public String createApGetIntCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetIntSC searchParam,  @QueryParam("lineOutputFlag") Boolean lineOutputFlag);

	/**
	 * 取得申請(無形)取得
	 * @param applicationId 取得申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @return 取得申請(無形)データ
	 */
	@POST
	@Path("getApGetInt")
	public ApGetInt getApGetInt(@QueryParam("applicationId") String applicationId, @QueryParam("applicationVersion") String applicationVersion);

	/**
	 * 取得申請(無形)取得
	 * @param eappId e申請書類ID
	 * @return 取得申請(無形)データ
	 */
	@POST
	@Path("getApGetIntEapp")
	public ApGetInt getApGetInt(@QueryParam("eappId") Long eappId);

	/**
	 * 申請情報(無形)取得
	 * @param applicationId 申請書番号
	 * @param applicationVersion 申請書バージョン
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	@POST
	@Path("getApGetIntAppEapp")
	public ApGetInt getApGetInt(@QueryParam("applicationId") String applicationId, @QueryParam("applicationVersion") String applicationVersion, @QueryParam("lockFlag") boolean lockFlag);

	/**
	 * 取得申請(無形)作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請(無形)データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの取得申請書番号
	 */
	@POST
	@Path("createApGetInt")
	public String createApGetInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetInt obj) throws AppException;

	/**
	 * 取得申請(無形)更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請(無形)データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("updateApGetInt")
	public void updateApGetInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetInt obj) throws AppException;

	/**
	 * 取得申請(無形)ヘッダのみ更新
	 * @param obj 取得申請(無形)データ
	 * @throws SQLException 更新エラー時に発生
	 * @return なし
	 */
	public void updateApGetInt(ApGetInt obj) throws SQLException;

	/**
	 * 取得申請(無形)承認依頼
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請(無形)データ
	 * @param isNew 新規作成フラグ(true:新規作成,false:更新)
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成・更新したデータの取得申請書番号
	 */
	@PUT
	@Path("applyApGetInt")
	public String applyApGetInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetInt obj, @QueryParam("isNew") Boolean isNew) throws AppException;

	/**
	 * 取得申請(無形)削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請(無形)データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@POST
	@Path("deleteApGetInt")
	public void deleteApGetInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetInt obj) throws AppException;

	/**
	 * 印刷用PDF生成
	 * @param applicationList 印刷対象申請書一覧
	 * @param lineOutputFlag 明細を印刷物に出力するかどうか true:する false:しない
	 * @return PDFファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApGetIntPdf")
	public String createApGetIntPdf(List<ApGetInt> applicationList, @QueryParam("lineOutputFlag") Boolean lineOutputFlag);

	/**
	 * 履歴印刷用PDF生成
	 * @param applicationId 印刷対象申請書番号
	 * @param applicationVersion 印刷対象申請書バージョン
	 * @param version バージョン
	 * @return PDFファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApGetIntHistPdf")
	public String createApGetIntHistPdf(@QueryParam("applicationId") String applicationId, @QueryParam("applicationVersion") String applicationVersion, @QueryParam("version") Integer version);

	/**
	 * 承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveApGetInt")
	public void approveApGetInt(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApGetInt")
	public void cancelApplyApGetInt(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 差戻し/却下
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApGetInt")
	public void rejectApGetInt(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;

	/**
	 * 警告バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	@PUT
	@Path("validateWarningApGetInt")
	public String validateWarningApGetInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetInt obj);

	/**
	 * 賃貸予定期間の賃貸終了日を取得
	 * @param astRentMonth 契約月数
	 * @param astRentDateFrom 賃貸開始日
	 * @return 賃貸終了日
	 */
	@POST
	@Path("calcAstRentDateTo")
	public String calcAstRentDateTo(@QueryParam("astRentMonth") Long astRentMonth, @QueryParam("astRentDateFrom") Date astRentDateFrom);

	/**
	 * 賃貸予定期間の賃貸開始日を取得
	 * @param astRentMonth 契約月数
	 * @param astRentDateFrom 賃貸終了日
	 * @return 賃貸開始日
	 */
	@POST
	@Path("calcAstRentDateFrom")
	public String calcAstRentDateFrom(@QueryParam("astRentMonth") Long astRentMonth, @QueryParam("astRentDateTo") Date astRentDateTo);

	/**
	 * 督促メール送信
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param applicationList メール送信対象申請書一覧
	 * @return
	 */
	@POST
	@Path("remindApGetInt")
	public void remindApGetInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<ApGetInt> applicationList) throws AppException;

}
