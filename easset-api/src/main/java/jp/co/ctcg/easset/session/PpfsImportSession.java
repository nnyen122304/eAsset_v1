/*===================================================================
 * ファイル名 : PpfsImportSession.java
 * 概要説明   : ProPlusデータ取込EJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-24 1.0  リヨン           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import jp.co.ctcg.easset.dto.ppfs_import.PpfsStat;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("ppfsImport")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface PpfsImportSession {

	/**
	 * ProPlus処理年月取得(リアルタイム)
	 * @param companyCode 会社コード
	 * @return ProPlus処理年月
	 */
	@POST
	@Path("getPpfsCurrentPeriodRT")
	public String getPpfsCurrentPeriodRT(@QueryParam("companyCode") String companyCode);

	/**
	 * ProPlusデータ取込ステータス一覧取得
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル nullの場合は両方
	 * @return ProPlusデータ取込ステータス一覧
	 */
	@POST
	@Path("getPpfsStatList")
	public List<PpfsStat> getPpfsStatList(@QueryParam("companyCode") String companyCode, @QueryParam("dataType") String dataType);

	/**
	 * ProPlusデータ取込実行
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル nullの場合は両方
	 * @param execStaffCode 実行者社員番号
	 * @param schCreateFlag スケジュール作成フラグ 0:未作成、1:作成
	 */
	@POST
	@Path("callPpfsImport")
	public void callPpfsImport(@QueryParam("companyCode") String companyCode, @QueryParam("dataType") String dataType, @QueryParam("execStaffCode") String execStaffCode, @QueryParam("schCreateFlag") String schCreateFlag) throws AppException;

	/**
	 * ProPlusデータ取込実行(各種台帳・実績)
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル
	 * @param execStaffCode 実行者社員番号
	 */
	public void ppfsImport(String companyCode, String dataType, String execStaffCode);

	/**
	 * ProPlusデータ取込実行(予測)
	 * @param companyCode 会社コード
	 * @param dataType 3:固定資産-予測、4:リース・レンタル-予測
	 * @param execStaffCode 実行者社員番号
	 */
	public void ppfsImportSch(String companyCode, String dataType, String execStaffCode);
}
