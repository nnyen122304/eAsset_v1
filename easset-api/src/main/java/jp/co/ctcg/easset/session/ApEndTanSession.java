/*===================================================================
 * ファイル名 : ApEndTanSession.java
 * 概要説明   : 固定資産除売却申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-26 1.0  李            新規
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

import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTan;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanLineFld;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanSC;
import jp.co.ctcg.easset.dto.ap_end_tan.ApEndTanSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("ap-end-tan")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ApEndTanSession {

	/**
	 * 固定資産除売却申請検索
	 * @param loginStaffCode ログイン社員番号
	 *		  accessLevel    アクセスレベル
	 *		  searchParam    検索条件
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("searchApEndTan")
	public List<ApEndTanSR> searchApEndTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndTanSC searchParam);

	/**
	 * 固定資産除売却申請取得
	 * @param applicationId 移動申請書番号
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("getApEndTan")
	public ApEndTan getApEndTan(@QueryParam("applicationId") String applicationId);

	/**
	 * 固定資産除売却申請取得(e申請書類)
	 * @param eappId 移動申請書番号
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("getApEndTanEapp")
	public ApEndTan getApEndTan(@QueryParam("eappId") Long eappId);

	/**
	 *  固定資産除売却申請作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 固定資産除売却申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@POST
	@Path("createApEndTan")
	public String createApEndTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndTan obj) throws AppException;

	/**
	 *  固定資産除売却申請更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 固定資産除売却申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 除売却申請書番号
	 */
	@PUT
	@Path("updateApEndTan")
	public void updateApEndTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndTan obj) throws AppException;

	/**
	 * 固定資産除売却申請削除
	 * @param eappId 移動申請書番号
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("deleteApEndTan")
	public void deleteApEndTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndTan obj) throws AppException;

	/**
	 * 固定資産除売却申請
	 * @param eappId 移動申請書番号
	 * @return 検索結果
	 * @throws SQLException
	 */
	@PUT
	@Path("applyApEndTan")
	public String applyApEndTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndTan obj) throws AppException;

	/**
	 * 固定資産除売却申請-除売却報告
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 固定資産除売却申請データ
	 * @return 検索結果
	 * @throws SQLException
	 */
	@PUT
	@Path("reportApEndTan")
	public void reportApEndTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndTan obj) throws AppException;

	/**
	 * 固定資産除売却申請CSV作成
	 * @param loginStaffCode ログイン社員番号
	 *		  accessLevel    アクセスレベル
	 *		  searchParam    検索条件
	 * @return CSVのDownLoadID
	 * @throws SQLException
	 */
	@POST
	@Path("createApEndTanCsv")
	public String createApEndTanCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApEndTanSC obj) throws AppException;

	/**
	 * 固定資産除売却申請書PDF作成
	 * @param applicationIdList 申請書番号リスト
	 *		  lineOutputFlag    lineOutputFlag
	 * @return PDFのDownLoadID
	 * @throws SQLException
	 */
	@POST
	@Path("createApEndTanPdf")
	public String createApEndTanPdf(List<String> applicationIdList, @QueryParam("lineOutputFlag") Boolean lineOutputFlag) throws AppException;

	/**
	 * 除却紐付情報機器、ﾗｲｾﾝｽ検索
	 * @param loginStaffCode ログイン社員番号
	 *		  accessLevel    アクセスレベル
	 *		  companyCode    会社コード
	 *		  shisanNumPlural    資産番号
	 * @return 除却申請
	 * @throws SQLException
	 */
	@POST
	@Path("getAstLicByFld")
	public ApEndTan getAstLicByFld(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("shisanNumPlural") String shisanNumPlural) throws AppException;

	/**
	 * 督促メール送信
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param applicationList メール送信対象申請書一覧
	 * @return
	 */
	@POST
	@Path("remindApEndTan")
	public void remindApEndTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel,List<ApEndTan> applicationList) throws AppException;

	/**
	 * 引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApEndTan")
	public void cancelApplyApEndTan(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 差戻し/却下
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("rejectApEndTan")
	public void rejectApEndTan(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;

	/**
	 * 承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveApEndTan")
	public void approveApEndTan(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 償却費予測結果取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @param returnDate 返却予定日
	 * @param list 除却対象の固定資産一覧
	 */
	@POST
	@Path("getFldYskCalc")
	public List<ApEndTanLineFld> getFldYskCalc(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("period") String period, @QueryParam("returnDate") Date returnDate, List<ApEndTanLineFld> list) throws AppException;

	/**
	 * 除売却申請の抹消処理を実行
	 * @param applicationId 申請書番号
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("deleteExecApEndTan")
	public void deleteExecApEndTan(@QueryParam("applicationId") String applicationId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;
}
