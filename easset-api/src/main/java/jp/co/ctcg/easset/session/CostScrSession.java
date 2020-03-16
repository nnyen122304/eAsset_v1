/*===================================================================
 * ファイル名 : CostScrSession.java
 * 概要説明   : 経費負担部課精査セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2015-01-28 1.0  リヨン           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.co.ctcg.easset.dto.Section;
import jp.co.ctcg.easset.dto.costScr.CostScrLine;
import jp.co.ctcg.easset.dto.costScr.CostScrStat;
import jp.co.ctcg.easset.dto.costScr.CostScrSC;
import jp.co.ctcg.easset.dto.costScr.CostScrSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("cost-scr")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface CostScrSession {

	/**
	 * 経費負担部課精査状況一覧検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam 検索条件
	 * @return
	 */
	@POST
	@Path("searchCostScrStatList")
	public List<CostScrStat> searchCostScrStatList(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode);

	/**
	 * 経費負担部課精査状況検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam 検索条件
	 * @return
	 */
	@POST
	@Path("searchCostScr")
	public List<CostScrSR> searchCostScr(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, CostScrSC searchParam);

	/**
	 * 経費負担部課精査状況更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param obj 検索結果
	 * @param compFlag 精査完了フラグ
	 * @return
	 */
	@PUT
	@Path("updateCostScr")
	public void updateCostScr(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<CostScrSR> list, @QueryParam("compFlag") String compFlag);

	/**
	 * 経費負担部課明細検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return パラメータ
	 * @throws
	 */
	@POST
	@Path("searchCostScrLine")
	public List<CostScrLine> searchCostScrLine(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, CostScrSR searchParam);

	/**
	 * 経費負担部課精査状況CSVファイル作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode 会社コード
	 * @param searchParam 検索条件
	 * @return
	 */
	@POST
	@Path("createCostScrCsv")
	public String createCostScrCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, CostScrSC searchParam);

	/**
	 * 明細ダウンロード
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws
	 */
	@POST
	@Path("createCostScrLineListCsv")
	public String createCostScrLineListCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("loginStaffCode") String accessLevel, List<CostScrSR> list);

	/**
	 * 経費負担部課明細データ更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param list    更新対象一覧
	 * @return
	 * @throws
	 */
	@PUT
	@Path("updateCostScrLine")
	public void updateCostScrLine(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("costScrSR") CostScrSR costScrSR, List<CostScrLine> list) throws AppException;

	/**
	 * 経費負担部課明細エクスポートCSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param searchParam
	 * @return
	 * @throws
	 */
	@POST
	@Path("createCostScrLineCsv")
	public String createCostScrLineCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String CompanyCode, CostScrSR searchParam);

	/**
	 * 経費負担部課明細インポート
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param fileId         ファイルID
	 * @param obj
	 * @return
	 * @throws AppException
	 */
	@POST
	@Path("getScrLineByCsv")
	public List<CostScrLine> getScrLineByCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("fileId") String fileId, CostScrSR obj) throws AppException;

	/**
	 * 経費負担部課明細データ作成
	 * @param companyCode    会社コード
	 * @param period 会計年月
	 * @param execStaffCode 実行者
	 * @param overwriteFlag データ上書きフラグ
	 * @return
	 * @throws AppException
	 */
	@POST
	@Path("callCreateCostScrData")
	public void callCreateCostScrData(@QueryParam("companyCode") String companyCode, @QueryParam("period") String period, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("overwriteFlag") Boolean overwriteFlag) throws AppException;

	/**
	 * 経費負担部課明細データ作成
	 * @param companyCode    会社コード
	 * @param period 会計年月
	 * @param execStaffCode 実行者
	 * @param overwriteFlag データ上書きフラグ
	 * @return
	 * @throws AppException
	 */
	@POST
	@Path("createCostScrData")
	public void createCostScrData(@QueryParam("companyCode") String companyCode, @QueryParam("period") String period, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("overwriteFlag") Boolean overwriteFlag);

	/**
	 * 経費負担部課明細データ作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param period         会計年月
	 * @param publicCode     各部OPEN/CLOSE
	 * @param isSendMail     依頼メール送信？
	 * @return
	 * @throws AppException
	 */
	@PUT
	@Path("publicCostScrData")
	public void publicCostScrData(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("period") String period, @QueryParam("publicCode") String publicCode, @QueryParam("isSendMail") boolean isSendMail) throws AppException;

	/**
	 * ファイル・直入力可能マスタ値CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param propertyList 項目名一覧
	 * @return CSVファイル識別ID
	 */
	@POST
	@Path("createScrPossibleInputMasterCsv")
	public String createScrPossibleInputMasterCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("propertyList") List<String> propertyList);

	/**
	 * 精査担当者設定更新
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param fileId ファイルID
	 * @return なし
	 */
	@POST
	@Path("updateCostScrSectionByCsv")
	public void updateCostScrSectionByCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("period") String period, @QueryParam("fileId") String fileId) throws AppException;

	/**
	 * 督促メール送信
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param list    督促メール対象一覧
	 * @return
	 * @throws
	 */
	@PUT
	@Path("remindCostScr")
	public void remindCostScr(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<CostScrSR> list) throws AppException;

	/**
	 * 部課名から人事部課データ検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param sectionName    部課名
	 * @return
	 * @throws
	 */
	@GET
	@Path("getSectionByName")
	public Section getSectionByName(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("sectionName") String sectionName) throws AppException;

	/**
	 * 精査担当部署更新
	 * @param loginStaffCode ログイン社員番号
	 * @param list    更新対象一覧
	 * @return
	 * @throws
	 */
	@PUT
	@Path("updateScrSection")
	public void updateScrSection(@QueryParam("loginStaffCode") String loginStaffCode, List<CostScrSR> list) throws AppException;

	/**
	 * 依頼メール送信
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param list    依頼メール対象一覧
	 * @return
	 * @throws
	 */
	@PUT
	@Path("requestCostScr")
	public void requestCostScr(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, List<CostScrSR> list) throws AppException;
}