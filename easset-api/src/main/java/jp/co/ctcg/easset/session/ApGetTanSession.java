/*===================================================================
 * ファイル名 : ApGetTanSession.java
 * 概要説明   : 取得申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.io.IOException;
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

import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineAst;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineLic;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanSC;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("apGetTan")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ApGetTanSession {

	/**
	 * 取得申請検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("searchApGetTan")
	public List<ApGetTanSR> searchApGetTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetTanSC searchParam);

	/**
	 * 検索結果CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @param lineOutputFlag false:申請書単位,true:明細単位
	 * @return CSVファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApGetTanCsv")
	public String createApGetTanCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetTanSC searchParam, @QueryParam("lineOutputFlag") Boolean lineOutputFlag);

	/**
	 * 申請情報取得
	 * @param applicationId 取得申請書番号
	 * @return 取得申請データ
	 */
	@POST
	@Path("getApGetTan")
	public ApGetTan getApGetTan(@QueryParam("applicationId") String applicationId);

	/**
	 * 申請情報取得
	 * @param eappId e申請書類ID
	 * @return 取得申請データ
	 */
	@POST
	@Path("getApGetTanEapp")
	public ApGetTan getApGetTan(@QueryParam("eappId") Long eappId);

	/**
	 * 申請情報取得
	 * @param applicationId 申請書番号
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return 取得申請データ
	 */
	public ApGetTan getApGetTan(String applicationId, boolean lockFlag);

	/**
	 * 取得申請作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの取得申請書番号
	 */
	@POST
	@Path("createApGetTan")
	public String createApGetTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetTan obj) throws AppException;

	/**
	 * 取得申請更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("updateApGetTan")
	public void updateApGetTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetTan obj) throws AppException;

	/**
	 * 取得申請承認依頼
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成・更新したデータの取得申請書番号
	 */
	@PUT
	@Path("applyApGetTan")
	public String applyApGetTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetTan obj) throws AppException;

	/**
	 * 取得申請削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@POST
	@Path("deleteApGetTan")
	public void deleteApGetTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetTan obj) throws AppException;

	/**
	 * 入力可能マスタ値CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @return CSVファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createPossibleInputMasterCsv")
	public String createPossibleInputMasterCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel);

	/**
	 * 資産(機器)明細CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param applicationId 申請書番号
	 * @param astLine 資産(機器)明細データ
	 * @return CSVファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApGetTanLineAstCsv")
	public String createApGetTanLineAstCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("applicationId") String applicationId, List<ApGetTanLineAst> astLine);

	/**
	 * ライセンス明細CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param applicationId 申請書番号
	 * @param licLine 資産(機器)明細データ
	 * @return CSVファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApGetTanLineLicCsv")
	public String createApGetTanLineLicCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("applicationId") String applicationId, List<ApGetTanLineLic> licLine);

	/**
	 * CSVファイルから資産(機器)明細取得
	 * @param fileId CSVファイルID(一時領域に作成済の)
	 * @return
	 */
	@POST
	@Path("getApGetTanLineAstByCsv")
	public List<ApGetTanLineAst> getApGetTanLineAstByCsv(@QueryParam("fileId") String fileId) throws AppException;

	/**
	 * CSVファイルからライセンス明細取得
	 * @param fileId CSVファイルID(一時領域に作成済の)
	 * @return
	 */
	@POST
	@Path("getApGetTanLineLicByCsv")
	public List<ApGetTanLineLic> getApGetTanLineLicByCsv(@QueryParam("fileId") String fileId) throws AppException;

	/**
	 * 印刷用PDF生成
	 * @param applicationIdList 印刷対象申請書番号一覧
	 * @param lineOutputFlag 明細を印刷物に出力するかどうか true:する false:しない
	 * @return PDFファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApGetTanPdf")
	public String createApGetTanPdf(List<String> applicationIdList, @QueryParam("lineOutputFlag") Boolean lineOutputFlag);

	/**
	 * 履歴印刷用PDF生成
	 * @param applicationId 印刷対象申請書番号
	 * @param version バージョン
	 * @return PDFファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApGetTanHistPdf")
	public String createApGetTanHistPdf(@QueryParam("applicationId") String applicationId, @QueryParam("version") Integer version);

	/**
	 * 承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveApGetTan")
	public void approveApGetTan(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 上長承認
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveSuperiorApGetTan")
	public void approveSuperiorApGetTan(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApGetTan")
	public void cancelApplyApGetTan(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 差戻し/却下
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("rejectApGetTan")
	public void rejectApGetTan(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;

	/**
	 * 取得申請資産(機器)明細登録数の更新（+ OR -）
	 * @param loginStaffCode ログイン社員番号
	 * @param applicationId 申請書番号
	 * @param apGetTanLineAstId 資産(機器)明細識別ID
	 * @param addRegistQuantity 登録追加数量。マイナス値の場合は減少数量。
	 * @param operation 更新履歴に表示する操作
	 */
	public void updateApGetTanLineAstRegist(String loginStaffCode, String applicationId, Long apGetTanLineAstId, Integer addRegistQuantity, String operation) throws AppException;

	/**
	 * 取得申請ライセンス明細登録数の更新（+ OR -）
	 * @param loginStaffCode ログイン社員番号
	 * @param applicationId 申請書番号
	 * @param apGetTanLineLicId ライセンス明細識別ID
	 * @param addRegistQuantity 登録追加数量。マイナス値の場合は減少数量。
	 * @param operation 更新履歴に表示する操作
	 */
	public void updateApGetTanLineLicRegist(String loginStaffCode, String applicationId, Long apGetTanLineLicId, Integer addRegistQuantity, String operation) throws AppException;

	/**
	 * 警告バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	@POST
	@Path("validateWarningApGetTan")
	public String validateWarningApGetTan(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetTan obj);

	/**
	 * 納品先担当者メール送信
	 * @param obj データオブジェクト
	 * @return なし
	 */
	public void sendReoDlvStaff(ApGetTan obj);

	/**
	 * 印刷用PDF生成
	 * @param companyCode 会社コード
	 * @param applicationId 印刷対象申請書番号
	 * @param reoOrderType 注文書区分コード
	 * @return PDFファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createOrderPdf")
	public String createOrderPdf(@QueryParam("companyCode") String companyCode, @QueryParam("applicationId") String applicationId, @QueryParam("reoOrderType") String reoOrderType) throws AppException, IOException;

	/**
	 * レンタル発注メール送信
	 * @param obj データオブジェクト
	 * @return なし
	 */
	@POST
	@Path("sendReOrder")
	public void sendReOrder(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApGetTan obj) throws AppException;

}
