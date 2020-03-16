/*===================================================================
 * ファイル名 : InvSession.java
 * 概要説明   : 棚卸セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2013-03-19 1.0  李            新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.io.File;
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

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.inv.InvLine;
import jp.co.ctcg.easset.dto.inv.InvStat;
import jp.co.ctcg.easset.dto.inv.InvSumSC;
import jp.co.ctcg.easset.dto.inv.InvSumSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("inv")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface InvSession {

	/**
	 * 棚卸データ作成取得
	 * @param companyCode 会社コード
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("searchInvStat")
	public List<InvStat> searchInvStat(@QueryParam("companyCode") String companyCode);

	/**
	 * 棚卸集約検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("searchInvSum")
	public List<InvSumSR> searchInvSum (@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, InvSumSC searchParam);

	/**
	 * 棚卸集約検索
	 * @param eappId e申請書類ID
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("searchInvSumEapp")
	public List<InvSumSR> searchInvSum (@QueryParam("eappId") Long eappId);

	/**
	 * 棚卸検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("searchInvLine")
	public List<InvLine> searchInvLine(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, InvSumSR searchParam, @QueryParam("searchScopeType") String searchScopeType);

	/**
	 * 棚卸更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param list    棚卸データ一覧
	 * @return
	 * @throws SQLException
	 */
	@PUT
    @Path("updateInvLine")
	public void updateInvLine(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("invSumSR") InvSumSR invSumSR, List<InvLine> list) throws AppException;

	/**
	 * 最新会計年月判定
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("getInvHistPeriodFlag")
	public boolean getInvHistPeriodFlag (@QueryParam("companyCode") String companyCode, @QueryParam("period") String period);

	/**
	 * 棚卸報告
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param list    棚卸データ一覧
	 * @return
	 * @throws SQLException
	 */
	@PUT
	@Path("reportInvSum")
	public void reportInvSum(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<InvSumSR> list) throws AppException;

	/**
	 * 督促メール
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param list    棚卸データ一覧
	 * @return
	 * @throws SQLException
	 */
	@PUT
	@Path("remindInv")
	public void remindInv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<InvSumSR> list) throws AppException;

	/**
	 * 棚卸集約情報CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param searchParam    棚卸検索条件
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("createInvSumCsv")
	public String createInvSumCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, InvSumSC searchParam);

	/**
	 * 実査表印刷
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param list    棚卸データ一覧
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("createInvPdf")
	public String createInvPdf(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<InvSumSR> list) throws AppException;

	/**
	 * 承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveInv")
	public void approveInv(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * ｲﾝﾎﾟｰﾄ
	 * @param fileId ファイルID
	 */
	@POST
	@Path("getInvLineByCsv")
	public List<InvLine> getInvLineByCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId, InvSumSR obj) throws AppException;

	/**
	 * エクスポート
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("createInvLineCsv")
	public String createInvLineCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, InvSumSR searchParam, @QueryParam("searchScopeType") String searchScopeType);

	/**
	 * 明細ダウンロード
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("createInvLineListCsv")
	public String createInvLineListCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<InvSumSR> list, @QueryParam("searchScopeType") String searchScopeType);

	/**
	 * 棚卸データ作成実行呼び出し
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @param execStaffCode 実行者社員番号
	 * @param overwriteFlag データ上書きフラグ
	 */
	@POST
	@Path("callCreateInvData")
	public void callCreateInvData(@QueryParam("companyCode") String companyCode, @QueryParam("period") String period, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("overwriteFlag") Boolean overwriteFlag) throws AppException;

	/**
	 * 棚卸データ作成実行
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @param execStaffCode 実行者社員番号
	 * @param overwriteFlag データ上書きフラグ
	 */
	public void createInvData(String companyCode, String period, String execStaffCode, Boolean overwriteFlag);

	/**
	 * 棚卸データ公開
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @param publicTypeArray 公開タイプ指定（有形～備品等）
	 * @param sendExecArray メール送信指定（有形～備品等）
	 * @param execStaffCode 実行者社員番号
	 */
	@POST
	@Path("publicInvData")
	public void publicInvData(@QueryParam("companyCode") String companyCode, @QueryParam("period") String period, @QueryParam("publicTypeArray") String[] publicTypeArray, @QueryParam("sendExecArray") Boolean[] sendExecArray, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 棚卸データメール送信
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @param sendExecArray メール送信指定（有形～備品等）
	 * @param execStaffCode 実行者社員番号
	 */
	public void sendPublicInvMail(String companyCode, String period, Boolean[] sendExecArray, String execStaffCode);

	/**
	 * 引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	public void cancelApplyInv(Long eappId, String execStaffCode) throws AppException;

	/**
	 * 棚卸差戻
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 * @param rejectType 却下区分 1:差戻し、2:却下
	 * @param rejectReason 却下事由
	 */
	public void rejectInv(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException;

	/**
	 * 棚卸完了報告部署とユーザー所属確認
	 * @param user ログインユーザ情報
	 * @param accessLevel アクセスレベル
	 * @param list 棚卸完了報告対象一覧
	 */
	@POST
	@Path("checkReportInvUserSection")
	public boolean checkReportInvUserSection(@QueryParam("user") User user, @QueryParam("accessLevel") String accessLevel, List<InvSumSR> list);

	/**
	 * 情報機器棚卸明細データ作成実行
	 * @param execStaffCode 実行者社員番号
	 * @param list 棚卸集約情報一覧
	 * @param overwriteFlag データ上書きフラグ
	 */
	public void updateAssetInvData(String execStaffCode, List<InvSumSR> list);

	/**
	 * 棚卸一括更新定型CSVダウンロード
	 */
	@POST
	@Path("createInvTemplateCsv")
	public String createInvTemplateCsv(@QueryParam("companyCode") String companyCode, @QueryParam("period") String period);

	/**
	 * 情報機器等一括更新処理
	 * @param loginStaffCode ログイン社員番号
	 * @param companyCode 会社コード
	 * @param accessLevel アクセスレベル
	 * @param fileId ファイルID
	 * @param execFile
	 * @param updatePropertyList
	 * @param logId
	 * @param period
	 * @throws AppException
	 */
	public void updateInvBulk(String loginStaffCode, String companyCode, String accessLevel, String fileId, File execFile, List<CodeName> updatePropertyList, Long logId, String period);

	/**
	 * 情報機器等一括更新処理
	 * @param loginStaffCode ログイン社員番号
	 * @param companyCode 会社コード
	 * @param accessLevel アクセスレベル
	 * @param fileId ファイルID
	 * @throws AppException
	 */
	@POST
	@Path("callUpdateInvBulk")
	public void callUpdateInvBulk(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("companyCode") String companyCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId, @QueryParam("period") String period) throws AppException;

	/**
	 * 情報機器等一括更新の一行処理
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param rowCt 処理対象のカレント行
	 * @param updateObj 更新対象
	 * @throws AppException
	 */
	public void updateInvBulkRow(String loginStaffCode, String accessLevel, int rowCt, InvLine updateObj, boolean isHist) throws AppException;

}
