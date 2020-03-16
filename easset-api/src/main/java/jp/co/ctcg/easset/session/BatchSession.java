/*===================================================================
 * ファイル名 : BatchSession.java
 * 概要説明   : バッチ処理セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-02-23 1.0  リヨン           新規
 *=================================================================== */

package jp.co.ctcg.easset.session;

import java.sql.SQLException;
import java.util.List;

import javax.ejb.Local;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.asset.Asset;
import jp.co.ctcg.easset.dto.license.License;
import jp.co.ctcg.easset.template.utils.AppException;


@Local
@Path("batch")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public interface BatchSession {

	/**
	 * バッチ処理メイン
	 * @param args バッチ実行時の引数
	 * @return
	 * @throws SQLException
	 */
	@POST
	@Path("batchMain")
	public String batchMain(String[] args);

	/**
	 * タームライセンス期限通知処理
	 * @param batchInfo バッチ処理ステータス
	 * @return 管理者向けメール文字列
	 */
	public String termAlert(CodeName batchInfo);

	/**
	 * タームライセンス自動抹消
	 * @param batchInfo バッチ処理ステータス
	 * @return 管理者向けメール文字列
	 * @throws AppException
	 */
	public String termDelete(CodeName batchInfo) throws AppException;

	/**
	 * タームライセンス自動抹消(抹消部：新規トランザクション)
	 * @param batchInfo バッチ処理ステータス
	 * @param licenseList 抹消対象ライセンス
	 * @throws AppException
	 */
	public void termDeleteChild(CodeName batchInfo, List<License> licenseList) throws AppException;

	/**
	 * eGuardAD同期処理
	 * @param batchInfo バッチ処理ステータス
	 * @return 管理者向けメール文字列
	 */
	public String eGuardSync(CodeName batchInfo);

	/**
	 * eGuardAD同期処理(Ldap処理部：新規トランザクション)
	 * @param obj 情報機器データ
	 * @return eGuardAD同期結果 0:処理無し,1:追加,2:削除
	 */
	public int eGuardSyncChild(Asset obj);

	/**
	 * ProPlusデータ取込処理
	 * @param batchInfo バッチ処理ステータス
	 * @return 管理者向けメール文字列
	 */
	public String ppfsImport(CodeName batchInfo);

	/**
	 * ProPlusデータ取込処理(取込処理コール部：新規トランザクション)
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル
	 */
	public void ppfsImportChild(String companyCode, String dataType) throws AppException;

	/**
	 * 除売却申請自動抹消
	 * @param batchInfo バッチ処理ステータス
	 * @return 管理者向けメール文字列
	 * @throws AppException
	 */
	public String apEndTanDelete(CodeName batchInfo) throws AppException;

	/**
	 * リース満了・解約申請自動抹消
	 * @param batchInfo バッチ処理ステータス
	 * @return 管理者向けメール文字列
	 * @throws AppException
	 */
	public String apEndLeDelete(CodeName batchInfo) throws AppException;

	/**
	 * Knightマスタインポート
	 * @param batchInfo バッチ処理ステータス
	 * @return 管理者向けメール文字列
	 * @throws AppException
	 */
	public String knightMasterImport(CodeName batchInfo) throws AppException;

	/**
	 * 次期シスマスタインポート
	 * @param batchInfo バッチ処理ステータス
	 * @return 管理者向けメール文字列
	 * @throws AppException
	 */
	public String sapMasterImport(CodeName batchInfo) throws AppException;

	/**
	 * レンタル買取申請資産区分変更
	 * @param batchInfo バッチ処理ステータス
	 * @return 管理者向けメール文字列
	 * @throws AppException
	 */
	public String apEndReAssetTypeChange(CodeName batchInfo) throws AppException;
}
