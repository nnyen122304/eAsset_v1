/*===================================================================
 * ファイル名 : HistDAO.java
 * 概要説明   : 履歴用DAO定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-25 1.0  リヨン       新規
 *=================================================================== */

package jp.co.ctcg.easset.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ibatis.sqlmap.client.SqlMapClient;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.hist.BulkUpdateHist;
import jp.co.ctcg.easset.dto.hist.Hist;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.Function;


public class HistDAO {

	/**
	 * 履歴一覧検索
	 * @param entityName 履歴一覧を検索するエンティティ(テーブル)名
	 * @param keyColumn キーカラム名
	 * @param keyValue キー値
	 * @param statusCategoryCode ステータ表示用スマスタカテゴリ(NULL指定の場合はステータス取得なし)
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<Hist> selectHistList(String entityName, String keyColumn, String keyValue, String statusCategoryCode) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("entityName", "NEA_H_" + entityName);

		// キーカラム、値をリストに格納
		List<Map<String, String>> keyColValueList = new ArrayList<Map<String, String>>();
		String[] keyColumns = keyColumn.split(" ");
		String[] keyValues = keyValue.split(" ");
		for(int i = 0; i < keyColumns.length; i++) {
			if(keyValues.length > i) {
				Map<String, String> row = new HashMap<String, String>();
				row.put("column", keyColumns[i]);
				row.put("value", keyValues[i]);
				keyColValueList.add(row);
			}
		}

		param.put("keyColValueList", keyColValueList);
		param.put("statusCategoryCode", statusCategoryCode);

		return (List<Hist>) sqlMap.queryForList("selectHistList_HIS", param);
	}

	/**
	 * 履歴作成
	 * @param entityName 履歴を作成するエンティティ(テーブル)名
	 * @param keyValue キー値
	 * @param operation 操作
	 * @param itemDefCategoryCode 項目定義カテゴリコード
	 * @param lineUpdateColumnName 明細更新項目名(複数項目はカンマ区切り)
	 * @throws SQLException
	 */
	public void callCreateHistData(String entityName, String keyValue, String operation, String itemDefCategoryCode, String lineUpdateColumnName) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("entityName", "NEA_" + entityName);
		param.put("keyValue", keyValue);
		param.put("operation", operation);
		param.put("itemDefCategoryCode", itemDefCategoryCode);
		param.put("lineUpdateColumnName", lineUpdateColumnName);

		sqlMap.update("callCreateHistData_HIS", param);
	}

	/**
	 * 共通操作ログ作成
	 * @param staffCode 社員番号
	 * @param function 機能
	 * @param operation 操作
	 * @param description 備考
	 * @throws SQLException
	 */
	public void insertComOp(String staffCode, String function, String operation, String description) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		Date sysdate = new Date();
		param.put("createDate", sysdate);
		param.put("createStaffCode", staffCode);
		param.put("updateDate", sysdate);
		param.put("updateStaffCode", staffCode);
		param.put("function", function);
		param.put("operation", operation);
		param.put("description", Function.cutStringByte(description, Constants.MAX_CHAR_SIZE_COM_OP_DESCRIPTION)); // JDBCパラメータサイズ制限

		sqlMap.insert("insertComOp_HIS", param);
	}

	/**
	 * 一括更新ログ作成
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @param function 機能
	 * @param fileId ファイルID
	 * @param updateColumn 更新プロパティ
	 * @param updateColumnName 更新プロパティ名
	 * @return ログID
	 */
	public Long insertBulkUpdateLog(String companyCode, String staffCode, String function, String fileId, List<CodeName> updateColumnList) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		StringBuffer updateColumn = new StringBuffer();
		StringBuffer updateColumnName = new StringBuffer();

		// 項目名設定
		if(updateColumnList != null && updateColumnList.size() > 0) {
			for(int i = 0; i < updateColumnList.size(); i++) {
				CodeName cn = updateColumnList.get(i);

				if(i == 0) {
					if(Function.nvl(cn.getValue1(), "").equals("NEA_ASSET_LINE_COM_USR")) {
						updateColumnName.append("共有ユーザー明細-");
					}
					if(Function.nvl(cn.getValue1(), "").equals("NEA_ASSET_LINE_OS")) {
						updateColumnName.append("OS明細-");
					}
					if(Function.nvl(cn.getValue1(), "").equals("NEA_ASSET_LINE_NETWORK")) {
						updateColumnName.append("ネットワーク明細-");
					}
					if(Function.nvl(cn.getValue1(), "").equals("NEA_ASSET_LINE_INV")) {
						updateColumnName.append("棚卸明細-");
					}
				}

				if(i != 0) {
					updateColumn.append(",");
					updateColumnName.append(",");
				}

				updateColumn.append(cn.getValue3());
				updateColumnName.append(cn.getValue5());
			}
		}

		Date sysdate = new Date();
		param.put("createDate", sysdate);
		param.put("createStaffCode", staffCode);
		param.put("updateDate", sysdate);
		param.put("updateStaffCode", staffCode);
		param.put("companyCode", companyCode);
		param.put("function", function);
		param.put("fileId", fileId);
		param.put("updateColumn", Function.cutStringByte(updateColumn.toString(), Constants.MAX_CHAR_SIZE_COM_OP_DESCRIPTION));
		param.put("updateColumnName", Function.cutStringByte(updateColumnName.toString(), Constants.MAX_CHAR_SIZE_COM_OP_DESCRIPTION));

		return (Long) sqlMap.insert("insertBulkUpdateLog_HIS", param);
	}

	/**
	 * 一括更新ログ更新
	 * @param logId ログID
	 * @param execStatus 実行ステータス
	 * @param execCount データ件数
	 * @param successCount 処理成功件数
	 * @param failureCount 処理失敗件数
	 * @throws SQLException
	 */
	public void updateBulkUpdateLog(Long logId, String staffCode, String execStatus, Integer execCount, Integer successCount, Integer failureCount) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		Date sysdate = new Date();
		param.put("logId", logId);
		param.put("updateDate", sysdate);
		param.put("updateStaffCode", staffCode);
		param.put("execStatus", execStatus);
		param.put("execCount", execCount);
		param.put("successCount", successCount);
		param.put("failureCount", failureCount);

		sqlMap.update("updateBulkUpdateLog_HIS", param);
	}

	/**
	 * 一括更新履歴一覧検索
	 * @param companyCode 会社コード
	 * @param staffCode 社員番号
	 * @param function 機能
	 * @param createDateFrom 作成日From
	 * @param createDateTo 作成日To
	 * @param isExecOnly true:実行中のみ検索 false:全て検索
	 * @return
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	public List<BulkUpdateHist> selectBulkUpdateHistList(String companyCode, String staffCode, String function, Date createDateFrom, Date createDateTo, boolean isExecOnly) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("companyCode", companyCode);
		param.put("staffCode", staffCode);
		param.put("function", function);
		param.put("createDateFrom", createDateFrom);
		param.put("createDateTo", createDateTo);
		if(isExecOnly) param.put("isExecOnly", "1");

		return (List<BulkUpdateHist>) sqlMap.queryForList("selectBulkUpdateHistList_HIS", param);
	}

	/**
	 * 一括更新履歴取得
	 * @param logId ログID
	 * @return
	 * @throws SQLException
	 */
	public BulkUpdateHist selectBulkUpdateHist(Long logId) throws SQLException {
		//	SQLマップ情報生成（お決まりの部分）
		SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();
		Map<String, Object> param = new HashMap<String, Object>();

		param.put("logId", logId);

		return (BulkUpdateHist) sqlMap.queryForObject("selectBulkUpdateHistList_HIS", param);
	}

}
