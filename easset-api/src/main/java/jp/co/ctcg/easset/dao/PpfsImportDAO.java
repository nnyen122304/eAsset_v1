/*===================================================================
 * ファイル名 : MasterDAO.java
 * 概要説明   : マスタ用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2011-10-06 1.0  リヨン        新規
 *=================================================================== */

package jp.co.ctcg.easset.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.co.ctcg.easset.dto.ppfs_import.PpfsStat;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;

import com.ibatis.sqlmap.client.SqlMapClient;

public class PpfsImportDAO {

	/**
	 * ProPlus処理年月(リアルタイム)取得
	 * @param companyCode 会社コード
	 * @return ProPlus処理年月
	 */
	public String getPpfsCurrentPeriodRT(String companyCode) throws  SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("companyCode", companyCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (String) sqlMap.queryForObject("selectPpfsCurrentPeriodRT_PPI", param);
	}

	/**
	 * ProPlusデータ取込ステータス一覧取得
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル nullの場合は両方
	 * @return ProPlusデータ取込ステータス一覧
	 */
	@SuppressWarnings("unchecked")
	public List<PpfsStat> selectPpfsStatList(String companyCode, String dataType) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("dataType", dataType);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		return (List<PpfsStat>) sqlMap.queryForList("selectPpfsStatList_PPI", sqlParam);
	}

	/**
	 * ProPlusデータ取込実行(台帳・実績)
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル
	 * @param execStaffCode 実行者社員番号
	 */
	public void callPpfsImport(String companyCode, String dataType, String execStaffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("dataType", dataType);
		sqlParam.put("execStaffCode", execStaffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("callPpfsImport_PPI", sqlParam);
	}

	/**
	 * ProPlusデータ取込実行(予測)
	 * @param companyCode 会社コード
	 * @param dataType 3:固定資産-予測、4:リース・レンタル-予測
	 * @param execStaffCode 実行者社員番号
	 */
	public void callPpfsImportSch(String companyCode, String dataType, String execStaffCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("dataType", dataType);
		sqlParam.put("execStaffCode", execStaffCode);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("callPpfsImportSch_PPI", sqlParam);
	}

	/**
	 * ProPlusデータ取込実行
	 * @param companyCode 会社コード
	 * @param dataType 1:固定資産、2:リース・レンタル、3:固定資産-予測、4:リース・レンタル-予測
	 * @param execStaffCode 実行者社員番号
	 * @param execStatus 実行ステータス 1:実行中、2:正常終了、3:異常終了
	 */
	public void callUpdatePpfsStatus(String companyCode, String dataType, String execStaffCode, String execStatus) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

		Map<String, Object> sqlParam = new HashMap<String, Object>();
		sqlParam.put("companyCode", companyCode);
		sqlParam.put("dataType", dataType);
		sqlParam.put("execStaffCode", execStaffCode);
		sqlParam.put("execStatus", execStatus);

		//	SQLマップに定義されているSQLの呼び出し及び実行
		sqlMap.update("callUpdatePpfsStatus_PPI", sqlParam);
	}
}
