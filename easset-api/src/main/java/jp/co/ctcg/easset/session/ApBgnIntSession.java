/*===================================================================
 * ファイル名 : ApBgnIntSession.java
 * 概要説明   : サービス提供開始報告セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnInt;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntLineFld;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntSC;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("ap-bgn-int")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ApBgnIntSession {

	/**
	 * サービス提供開始報告検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("searchApBgnInt")
	public List<ApBgnIntSR> searchApBgnInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApBgnIntSC searchParam);

	/**
	 * 検索結果CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return CSVファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApBgnIntCsv")
	public String createApBgnIntCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApBgnIntSC searchParam);

	/**
	 * サービス提供開始報告検索
	 * @param applicationId 取得申請書番号
	 * @return サービス提供開始報告データ
	 */
	@POST
	@Path("getApBgnInt")
	public ApBgnInt getApBgnInt(@QueryParam("applicationId") String applicationId);

	/**
	 * サービス提供開始報告取得
	 * @param eappId e申請番号
	 * @return サービス提供開始報告データ
	 */
	@POST
	@Path("getApBgnIntEapp")
	public ApBgnInt getApBgnInt(@QueryParam("eappId") Long eappId);

	/**
	 * サービス提供開始報告取得
	 * @param applicationId 申請書番号
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return サービス提供開始報告データ
	 */
	public ApBgnInt getApBgnInt(String applicationId, boolean lockFlag);

	/**
	 * 取得申請書(無形)からサービス提供開始報告データ取得
	 * @param apGetIntId 取得申請書番号
	 * @param apGetIntVersion 取得申請書バージョン
	 * @return サービス提供開始報告データ
	 * @return
	 */
	@POST
	@Path("getApBgnIntByApGetInt")
	public ApBgnInt getApBgnIntByApGetInt(@QueryParam("apGetIntId") String apGetIntId, @QueryParam("apGetIntVersion") String apGetIntVersion);

	/**
	 * 取得申請書番号からProPlusの資産明細情報取得
	 * @param companyCode 会社コード
	 * @param applicationId 取得申請書番号
	 * @param lineType 明細区分 1:申請時,2:承認時追加分
	 * @return サービス提供開始報告データ
	 * @return
	 */
	@POST
	@Path("getPpfsFldList")
	public List<ApBgnIntLineFld> getPpfsFldList(@QueryParam("companyCode") String companyCode, @QueryParam("applicationId") String applicationId, @QueryParam("lineType") String lineType);

	/**
	 * サービス提供開始報告作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj サービス提供開始報告データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータのサービス提供開始報告書番号
	 */
	@POST
	@Path("createApBgnInt")
	public String createApBgnInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApBgnInt obj) throws AppException;

	/**
	 * サービス提供開始報告更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj サービス提供開始報告データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("updateApBgnInt")
	public void updateApBgnInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApBgnInt obj) throws AppException;

	/**
	 * サービス提供開始報告承認依頼
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj サービス提供開始報告データ
	 * @param isNew 新規作成フラグ(true:新規作成,false:更新)
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成・更新したデータのサービス提供開始報告書番号
	 */
	@PUT
	@Path("applyApBgnInt")
	public String applyApBgnInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApBgnInt obj, @QueryParam("isNew") Boolean isNew) throws AppException;

	/**
	 * サービス提供開始報告削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj サービス提供開始報告データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@POST
	@Path("deleteApBgnInt")
	public void deleteApBgnInt(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApBgnInt obj) throws AppException;

	/**
	 * 印刷用PDF生成
	 * @param applicationList 印刷対象申請書一覧
	 * @return PDFファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApBgnIntPdf")
	public String createApBgnIntPdf(List<String> applicationList);

	/**
	 * 履歴印刷用PDF生成
	 * @param applicationId 印刷対象申請書番号
	 * @param version バージョン
	 * @return PDFファイルID(一時領域に作成される)
	 */
	public String createApBgnIntHistPdf(String applicationId, Integer version);

	/**
	 * 承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveApBgnInt")
	public void approveApBgnInt(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApBgnInt")
	public void cancelApplyApBgnInt(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 差戻し/却下
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("rejectApBgnInt")
	public void rejectApBgnInt(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;
}
