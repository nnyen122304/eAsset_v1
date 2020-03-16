/*===================================================================
 * ファイル名 : LldSession.java
 * 概要説明   : リース・レンタル契約情報セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-25 1.0  李           新規
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

import jp.co.ctcg.easset.dto.lld.PpfsLld;
import jp.co.ctcg.easset.dto.lld.PpfsLldSC;
import jp.co.ctcg.easset.dto.lld.PpfsLldSR;
import jp.co.ctcg.easset.template.utils.AppException;

@Local
@Path("lld")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface LldSession {

	/**
	 * リース・レンタル契約情報一覧検索
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param searchParam    検索条件
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("searchLld")
	public List<PpfsLldSR> searchLld(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, PpfsLldSC searchParam);

	/**
	 * リース・レンタル契約CSV作成
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param outputPropList 出力対象項目名
	 * @param searchParam    検索条件
	 * @param itemDef ダウンロード定義
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("createLldCsv")
	public String createLldCsv(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("outputPropList") List<String> outputPropList, PpfsLldSC searchParam, @QueryParam("itemDef") String itemDef);

	/**
	 * リース・レンタル契約情報_取得
	 * @param koyuno 固有番号
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("getLld")
	public PpfsLld getLld(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("koyunoA") Long koyunoA);

	/**
	 * リース・レンタル契約情報照会取得(物件と紐付かない契約)
	 * @param koyuno 固有番号
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("getLldC")
	public PpfsLld getLldC(@QueryParam("loginStaffCode") String loginStaffCode,  @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, @QueryParam("koyunoC") Long koyunoC);

	/**
	 * ProPlus物件更新チェック
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param obj リース・レンタル契約検索結果一覧
	 * @return Ppfs未承認データ一覧
	 * @throws SQLException
	 */
	@PUT
	@Path("checkPpfsLldUpdate")
	public void checkPpfsLldUpdate(@QueryParam("loginStaffCode") String loginStaffCode, @QueryParam("accessLevel") String accessLevel, @QueryParam("companyCode") String companyCode, List<PpfsLldSR> obj) throws AppException;
}
