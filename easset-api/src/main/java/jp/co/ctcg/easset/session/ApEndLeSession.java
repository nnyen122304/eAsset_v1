/*===================================================================
 * ファイル名 : ApEndLeSession.java
 * 概要説明   : リース満了・解約申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-12-21  1.0  高山           新規
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

import jp.co.ctcg.easset.dto.ap_end_le.ApEndLe;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeSC;
import jp.co.ctcg.easset.dto.ap_end_le.ApEndLeSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("ap-end-le")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ApEndLeSession {

	/**
	 * リース満了・解約申請検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("searchApEndLe")
	public List<ApEndLeSR> searchApEndLe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndLeSC searchParam);

	/**
	 * リース満了・解約申請CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param lineOutputFlag false:申請書単位,true:物件明細単位
	 * @return CSVのDownLoadID
	 * @throws SQLException
	 */
	@POST
	@Path("createApEndLeCsv")
	public String createApEndLeCsv(@QueryParam("QueryParam") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndLeSC obj, @QueryParam("lineOutputFlag") Boolean lineOutputFlag) throws AppException;

	/**
	 * リース満了・解約申請検索
	 * @param applicationId リース満了・解約申請書番号
	 * @return リース満了・解約申請データ
	 */
	@POST
	@Path("getApEndLe")
	public ApEndLe getApEndLe(@QueryParam("applicationId") String applicationId);

	/**
	 * リース満了・解約申請検索
	 * @param eappId リース満了・解約申請書番号
	 * @return リース満了・解約申請データ
	 */
	@POST
	@Path("getApEndLeEapp")
	public ApEndLe getApEndLe(@QueryParam("eappId") Long eappId);

	/**
	 * 申請情報取得
	 * @param applicationId 申請書番号
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	public ApEndLe getApEndLe(String applicationId, boolean lockFlag);

	/**
	 * リース満了・解約申請作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj リース満了・解約申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータのリース満了・解約申請書番号
	 */
	@POST
	@Path("createApEndLe")
	public String createApEndLe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndLe obj) throws AppException;

	/**
	 * リース満了・解約申請更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj リース満了・解約申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("updateApEndLe")
	public void updateApEndLe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndLe obj) throws AppException;

	/**
	 * リース満了・解約申請承認依頼
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj リース満了・解約申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成・更新したデータのリース満了・解約申請書番号
	 */
	@PUT
	@Path("applyApEndLe")
	public String applyApEndLe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndLe obj) throws AppException;

	/**
	 * リース満了・解約申請削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj リース満了・解約申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@POST
	@Path("deleteApEndLe")
	public void deleteApEndLe(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndLe obj) throws AppException;

	/**
	 * 印刷用PDF生成
	 * @param applicationIdList 印刷対象申請書番号一覧
	 * @param lineOutputFlag 明細を印刷物に出力するかどうか true:する false:しない
	 * @return PDFファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApEndLePdf")
	public String createApEndLePdf(List<String> applicationIdList, @QueryParam("lineOutputFlag") Boolean lineOutputFlag) throws AppException;

	/**
	 * 承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveApEndLe")
	public void approveApEndLe(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApEndLe")
	public void cancelApplyApEndLe(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 差戻し/却下
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("rejectApEndLe")
	public void rejectApEndLe(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;

	/**
	 * 物件紐付情報機器、ﾗｲｾﾝｽ検索
	 * @param loginStaffCode ログイン社員番号
	 *		  accessLevel    アクセスレベル
	 *		  companyCode    会社コード
	 *		  shisanNumPlural    資産番号
	 * @return リース満了・解約申請
	 * @throws SQLException
	 */
	@POST
	@Path("getAstLicByLld")
	public ApEndLe getAstLicByLld(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("shisanNumPlural") String shisanNumPlural) throws AppException;


	/**
	 * 同一契約 他物件情報検索
	 * @param loginStaffCode ログイン社員番号
	 *		  accessLevel    アクセスレベル
	 *		  companyCode    会社コード
	 *		  koyunoC        固有番号（契約）
	 *		  koyunoAPlural  固有番号（物件）
	 * @return リース満了・解約申請
	 * @throws SQLException
	 */
	@POST
	@Path("getOtherLldByContract")
	public ApEndLe getOtherLldByContract(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("ppIdContract") Long ppIdContract, @QueryParam("ppIdAstPlural") String ppIdAstPlural) throws AppException;

	/**
	 * 返却予定日の過ぎたリース満了・解約申請リストを取得
	 * @param returnDateTo 基準日
	 * @return リース満了・解約申請リスト
	 */
	@POST
	@Path("getReturnApEndLeList")
	public List<ApEndLeSR> getReturnApEndLeList(@QueryParam("returnDateTo") Date returnDateTo) throws AppException;

	/**
	 * リース満了・解約申請の抹消処理を実行
	 * @param applicationId 申請書番号
	 * @param execStaffCode 処理実行者
	 */
	@POST
	@Path("deleteExecApEndLe")
	public void deleteExecApEndLe(@QueryParam("applicationId") String applicationId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * リース満了・解約申請のProPlus連携区分を更新
	 * @param apEndLe リース満了・解約申請データ
	 */
	public void updatePpSendFlagApEndLe(ApEndLe apEndLe) throws AppException ;
}
