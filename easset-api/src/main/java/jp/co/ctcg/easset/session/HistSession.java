/*===================================================================
 * ファイル名 : HistSession.java
 * 概要説明   : 履歴セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-25 1.0  リヨン           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.hist.BulkUpdateHist;
import jp.co.ctcg.easset.rest.conv.DateFormat;
import jp.co.ctcg.easset.dto.hist.Hist;

@Local
@Path("hist")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface HistSession {

	/**
	 * 更新履歴一覧検索
	 * @param entityName 履歴一覧を検索するエンティティ(テーブル)名
	 * @param keyValue キー値
	 * @return 履歴一覧
	 * @throws SQLException
	 */
	@POST
	@Path("getHistList")
	public List<Hist> getHistList(@QueryParam("entityName") String entityName, @QueryParam("keyValue") String keyValue);

	/**
	 * 更新履歴作成
	 * @param entityName 履歴を作成するエンティティ(テーブル)名
	 * @param keyValue キー値
	 * @param operation 操作
	 * @param lineUpdateColumnName 明細修正項目
	 */
	public void createHistData(String entityName, String keyValue, String operation, String lineUpdateColumnName);

	/**
	 * 操作履歴作成
	 * @param staffCode 社員番号
	 * @param function 機能
	 * @param operation 操作
	 * @param description 備考
	 */
	public void createOpLog(String staffCode, String function, String operation, String description);

	/**
	 * 一括更新ログ作成
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @param function 機能
	 * @param execFileId ファイルID
	 * @param updateColumnList 更新プロパティリスト
	 * @return ログID
	 */
	public Long createBulkUpdateLog(String companyCode, String staffCode, String function, String execFileId, List<CodeName> updateColumnList);

	/**
	 * 一括更新ログ更新
	 * @param logId ログID
	 * @param staffCode 社員番号
	 * @param execStatus 実行ステータス
	 * @param execCount データ件数
	 * @param successCount 処理成功件数
	 * @param failureCount 処理失敗件数
	 */
	@POST
	@Path("updateBulkUpdateLog")
	public void updateBulkUpdateLog(@QueryParam("logId") Long logId, @QueryParam("staffCode") String staffCode, @QueryParam("execStatus") String execStatus, @QueryParam("execCount") Integer execCount, @QueryParam("successCount") Integer successCount, @QueryParam("failureCount") Integer failureCount);

	/**
	 * 一括更新履歴一覧検索
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @param function 機能
	 * @param createDateFrom 作成日From
	 * @param createDateTo 作成日To
	 * @param isExecOnly true:実行中のみ検索 false:全て検索
	 * @return 履歴一覧
	 * @throws SQLException
	 */
	@POST
	@Path("getBulkUpdateHistList")
	public List<BulkUpdateHist> getBulkUpdateHistList(@QueryParam("companyCode") String companyCode, @QueryParam("staffCode") String staffCode, @QueryParam("function") String function, @QueryParam("createDateFrom") @DateFormat("yyyy-MM-dd") Date createDateFrom, @QueryParam("createDateTo") @DateFormat("yyyy-MM-dd") Date createDateTo, @QueryParam("isExecOnly") boolean isExecOnly);
	/**
	 * 一括更新履歴検索
	 * @param logId ログID
	 * @return 履歴
	 * @throws SQLException
	 */
	public BulkUpdateHist getBulkUpdateHist(Long logId);
}
