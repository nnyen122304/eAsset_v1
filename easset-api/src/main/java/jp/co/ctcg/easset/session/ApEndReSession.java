/*===================================================================
 * ファイル名 : ApEndReSession.java
 * 概要説明   : レンタル満了･解約申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2017-11-06 1.0  申           新規
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

import jp.co.ctcg.easset.dto.ap_end_re.ApEndRe;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndReSC;
import jp.co.ctcg.easset.dto.ap_end_re.ApEndReSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("ap-end-re")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ApEndReSession {

	/**
	 * レンタル満了・解約申請検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("searchApEndRe")
	public List<ApEndReSR> searchApEndRe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndReSC searchParam);

	/**
	 * レンタル満了・解約申請CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param lineOutputFlag false:申請書単位,true:物件明細単位
	 * @return CSVのDownLoadID
	 * @throws SQLException
	 */
	@POST
	@Path("createApEndReCsv")
	public String createApEndReCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndReSC obj, @QueryParam("lineOutputFlag") Boolean lineOutputFlag) throws AppException;

	/**
	 * レンタル満了・解約申請検索
	 * @param applicationId レンタル満了・解約申請書番号
	 * @return レンタル満了・解約申請データ
	 */
	@POST
	@Path("getApEndRe")
	public ApEndRe getApEndRe(@QueryParam("applicationId") String applicationId);

	/**
	 * レンタル満了・解約申請検索
	 * @param eappId レンタル満了・解約申請書番号
	 * @return レンタル満了・解約申請データ
	 */
	@POST
	@Path("getApEndReEapp")
	public ApEndRe getApEndRe(@QueryParam("eappId") Long eappId);

	/**
	 * 申請情報取得
	 * @param applicationId 申請書番号
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	public ApEndRe getApEndRe(String applicationId, boolean lockFlag);

	/**
	 * レンタル満了・解約申請作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj レンタル満了・解約申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータのレンタル満了・解約申請書番号
	 */
	@POST
	@Path("createApEndRe")
	public String createApEndRe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndRe obj) throws AppException;

	/**
	 * レンタル満了・解約申請更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj レンタル満了・解約申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("updateApEndRe")
	public void updateApEndRe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndRe obj) throws AppException;

	/**
	 * レンタル満了・解約申請承認依頼
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj レンタル満了・解約申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成・更新したデータのレンタル満了・解約申請書番号
	 */
	@PUT
	@Path("applyApEndRe")
	public String applyApEndRe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndRe obj) throws AppException;

	/**
	 * レンタル満了・解約申請削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj レンタル満了・解約申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@POST
	@Path("deleteApEndRe")
	public void deleteApEndRe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndRe obj) throws AppException;

	/**
	 * 印刷用PDF生成
	 * @param applicationIdList 印刷対象申請書番号一覧
	 * @param lineOutputFlag 明細を印刷物に出力するかどうか true:する false:しない
	 * @return PDFファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApEndRePdf")
	public String createApEndRePdf(List<String> applicationIdList, @QueryParam("lineOutputFlag") Boolean lineOutputFlag) throws AppException;

	/**
	 * 承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveApEndRe")
	public void approveApEndRe(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApEndRe")
	public void cancelApplyApEndRe(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 差戻し/却下
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("rejectApEndRe")
	public void rejectApEndRe(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;

	/**
	 * 物件紐付情報機器、ﾗｲｾﾝｽ検索
	 * @param loginStaffCode ログイン社員番号
	 *		  accessLevel    アクセスレベル
	 *		  companyCode    会社コード
	 *		  shisanNumPlural    資産番号
	 * @return レンタル満了・解約申請
	 * @throws SQLException
	 */
	@POST
	@Path("getAstLicByLld")
	public ApEndRe getAstLicByLld(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("shisanNumPlural") String shisanNumPlural) throws AppException;


	/**
	 * 同一契約 他物件情報検索
	 * @param loginStaffCode ログイン社員番号
	 *		  accessLevel    アクセスレベル
	 *		  companyCode    会社コード
	 *		  koyunoC        固有番号（契約）
	 *		  koyunoAPlural  固有番号（物件）
	 * @return レンタル満了・解約申請
	 * @throws SQLException
	 */
	@POST
	@Path("getOtherLldByContract")
	public ApEndRe getOtherLldByContract(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("ppIdContract") Long ppIdContract, @QueryParam("ppIdAstPlural") String ppIdAstPlural) throws AppException;

	/**
	 * 返却予定日の過ぎたレンタル満了・解約申請リストを取得
	 * @param returnDateTo 基準日
	 * @return レンタル満了・解約申請リスト
	 */
	@POST
	@Path("getReturnApEndReList")
	public List<ApEndReSR> getReturnApEndReList(@QueryParam("returnDateTo") Date returnDateTo) throws AppException;

	/**
	 * レンタル満了・解約申請の抹消処理を実行
	 * @param applicationId 申請書番号
	 * @param execStaffCode 処理実行者
	 */
	@POST
	@Path("deleteExecApEndRe")
	public void deleteExecApEndRe(@QueryParam("applicationId") String applicationId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;


	/**
	 * レンタル満了・解約申請のProPlus連携区分を更新
	 * @param apEndLe リース満了・解約申請データ
	 */
	public void updatePpSendFlagApEndRe(ApEndRe apEndRe) throws AppException ;
}
