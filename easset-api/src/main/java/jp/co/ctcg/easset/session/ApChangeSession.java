/*===================================================================
 * ファイル名 : ApChangeSession.java
 * 概要説明   : 移動申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン           新規
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

import jp.co.ctcg.easset.dto.ap_change.ApChange;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineContract;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineFld;
import jp.co.ctcg.easset.dto.ap_change.ApChangeSC;
import jp.co.ctcg.easset.dto.ap_change.ApChangeSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("ap-change")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface ApChangeSession {

	/**
	 * 移動申請検索
	 * @param loginStaffCode ログイン社員番号
	 *		  accessLevel    アクセスレベル
	 *		  searchParam    検索条件
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("searchApChange")
	public List<ApChangeSR> searchApChange(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApChangeSC searchParam);

	/**
	 * 検索結果CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return CSVファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApChangeCsv")
	public String createApChangeCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApChangeSC searchParam);

	/**
	 * 移動申請検索
	 * @param applicationId 移動申請書番号
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("getApChange")
	public ApChange getApChange(@QueryParam("applicationId") String applicationId );

	/**
	 * 移動申請検索(e申請)
	 * @param applicationId 移動申請書番号
	 * @return 検索結果
	 * @throws SQLException
	 */
	@POST
	@Path("getApChangeEapp")
	public ApChange getApChange(@QueryParam("eappId") Long eappId );

	/**
	 *  移動申請作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 移動申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの取得申請書番号
	 */
	@POST
	@Path("createApChange")
	public String createApChange(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApChange obj) throws AppException;

	/**
	 *  移動申請更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 移動申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの取得申請書番号
	 */
	@PUT
	@Path("updateApChange")
	public void updateApChange(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApChange obj) throws AppException;

	/**
	 * 移動申請承認依頼
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 移動申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成・更新したデータの移動申請書番号
	 */
	@PUT
	@Path("applyApChange")
	public String applyApChange(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApChange obj) throws AppException;

	/**
	 * 移動申請削除
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 移動申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@POST
	@Path("deleteApChange")
	public void deleteApChange(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, ApChange obj) throws AppException;


	/**
	 * 承認(e申請連携)
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("approveApChange")
	public void approveApChange(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 承認(eAsset画面から)
	 * @param loginStaffCode ログイン社員番号
	 * @param obj 移動申請データ
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("approveApChange")
	public void approveApChange(@QueryParam("loginStaffCode") String loginStaffCode, ApChange obj) throws AppException;

	/**
	 * 引戻し
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("cancelApplyApChange")
	public void cancelApplyApChange(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode) throws AppException;

	/**
	 * 差戻し/却下(e申請連携)
	 * @param eappId e申請ID
	 * @param execStaffCode 処理実行者
	 */
	@PUT
	@Path("rejectApChangeEapp")
	public void rejectApChange(@QueryParam("eappId") Long eappId, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;

	/**
	 * 差戻し/却下(eAsset画面から)
	 * @param loginStaffCode ログイン社員番号
	 * @param obj 移動申請データ
	 * @param rejectType 却下区分
	 * @param rejectReason 却下コメント
	 * @throws AppException バリデーションエラー時に発生
	 * @return なし
	 */
	@PUT
	@Path("rejectApChange")
	public void rejectApChange(@QueryParam("loginStaffCode") String loginStaffCode, ApChange obj, @QueryParam("rejectType") String rejectType, @QueryParam("rejectReason") String rejectReason) throws AppException;

	/**
	 * 印刷用PDF生成
	 * @param applicationIdList 印刷対象申請書番号一覧
	 * @return PDFファイルID(一時領域に作成される)
	 */
	@POST
	@Path("createApChangePdf")
	public String createApChangePdf(@QueryParam("applicationIdList") List<String> applicationIdList);

	/**
	 * 資産・物件から紐付情報機器、ﾗｲｾﾝｽ取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param shisanNumPlural    資産番号（スペース区切り）
	 * @return 情報機器、ライセンス明細を格納した移動申請Obj
	 * @throws SQLException
	 */
	@POST
	@Path("getAstLicByFld")
	public ApChange getAstLicByFld(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("shisanNumPlural") String shisanNumPlural);

	/**
	 * 情報機器から紐付資産・物件取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param assetIdPlural    情報機器管理番号（スペース区切り）
	 * @return 資産、物件明細を格納した移動申請Obj
	 * @throws AppException
	 */
	@POST
	@Path("getFldContractByAst")
	public ApChange getFldContractByAst(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("assetIdPlural") String assetIdPlural);

	/**
	 * ﾗｲｾﾝｽから紐付資産・物件取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param licenseIdPlural    ライセンス管理番号（スペース区切り）
	 * @return 資産、物件明細を格納した移動申請Obj
	 * @throws AppException
	 */
	@POST
	@Path("getFldContractByLic")
	public ApChange getFldContractByLic(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("licenseIdPlural") String licenseIdPlural);

	/**
	 * 配賦設定の有る資産に配分をセットして戻す。
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param fldList 資産リスト
	 * @return
	 */
	@POST
	@Path("setFldCostSec")
	public List<ApChangeLineFld> setFldCostSec(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("contractList") List<ApChangeLineFld> fldList);

	/**
	 * 配賦設定の有る物件に配分をセットして戻す。
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param contractList 物件リスト
	 * @return
	 */
	@POST
	@Path("setContractCostSec")
	public List<ApChangeLineContract> setContractCostSec(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("contractList") List<ApChangeLineContract> contractList);

}
