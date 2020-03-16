/*===================================================================
 * ファイル名 : CsvWriterRowHandler.java
 * 概要説明   : 検索結果 -> CSVファイル作成用RowHandler
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-04-06 1.0  崔            新規
 *=================================================================== */
package jp.co.ctcg.easset.util;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.Format;

import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.SysException;

/**
 * 使用方法
 *
 * String headerRow = "紐付番号,支払期日,請求書番号,・・・"; // CSVタイトル行作成
 * String[] outputProps = new String[]{"invoiceId", "dueDate", "invoiceNum", ・・・} // CSV出力対象プロパティ指定
 * Format[] outputPropFormats = new Format[]{null, new SimpleDateFormat("yyyy/MM/dd"), null, ・・・} // CSV出力対象プロパティ指定
 *
 * CsvWriterRowHandler handler = null;
 * try {
 *   handler = new CsvWriterRowHandler(connectionInfo, headerRow, outputProps);
 *
 *   SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance(connectionInfo);
 *
 *   sqlMap.queryWithRowHandler("SQL名", handler);
 * } catch (...) {
 *   ...
 * } finally {
 *   if(handler != null) handler.close(); // ファイルクローズ
 * }
 * String fileId = handler.getCsvFileId(); // 作成されたファイルID（ダウンロードURL）
 * File csvFile = handler.getCsvFile(); // 作成されたファイル
 *
 *
 * @author z1g7092
 *
 */
public class CsvWriterRowHandler extends FileWriterRowHandler {
	// ファイル保存・ダウンロード関連ルックアップタイプ
	private static final String CATEGORY_CODE_FILE_SAVE_ROOT_PATH = "FILE_SAVE_ROOT_PATH"; // ファイル保存先

	// 区切文字
	private static final String DATA_SEPARATOR_COLUMN = ","; // 列
	private static final String DATA_SEPARATOR_ROW = "\n"; // 行
	private static final String DATA_SEPARATOR_ESCAPE_COLUMN = ","; // 列
	private static final String DATA_SEPARATOR_ESCAPE_ROW = Function.getCsvEnterStr(); // 行

	// 指定文字エスケープ
	private static final String[] DATA_SEPARATOR = {"\""};
	private static final String[] DATA_SEPARATOR_ESCAPE = {"\"\""}; // ダブルクォーテーションを２つに置換

	private static final String FILE_EXTENSION = ".csv"; // ファイル拡張子

	/**
	 *
	 * @param connectionInfo ユーザー接続情報
	 * @param headerRow タイトル行。指定するとデータ行の前に指定文字列が出力される。（nullを指定すると出力されない。）
	 * @param outputProps 出力プロパティ名
	 * @param outputPropFormats 出力プロパティフォーマット
	 * @throws IOException
	 */
	public CsvWriterRowHandler(String headerRow, String[] outputProps, Format[] outputPropFormats) throws IOException {
		super(createCsvFile(), getCharsetName(), headerRow, outputProps, outputPropFormats, DATA_SEPARATOR_COLUMN, DATA_SEPARATOR_ROW, DATA_SEPARATOR_ESCAPE_COLUMN, DATA_SEPARATOR_ESCAPE_ROW, DATA_SEPARATOR, DATA_SEPARATOR_ESCAPE);
	}

	/**
	 * 作成したファイルのダウンロード用参照IDを取得する
	 * @return
	 * @throws IOException
	 */
	public String getFileId() throws IOException {
		return getFile().getName();
	}

	/*
	 * CSV保存先の空ファイルを生成する。
	 */
	private static File createCsvFile() {
		try {
			MasterDAO masterDao = new MasterDAO();
			CodeName codeName = masterDao.selectCodeNameList(CATEGORY_CODE_FILE_SAVE_ROOT_PATH, CATEGORY_CODE_FILE_SAVE_ROOT_PATH, null, null, null, true).get(0);

			String fileSavePath = codeName.getValue2(); // 一時ファイル保存先

			return File.createTempFile("NEA_", FILE_EXTENSION, new File(fileSavePath));

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "CSV保存先取得"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "CSV保存先取得"), e);
		}
	}

	/*
	 * CSV保存Charsetを取得する。
	 */
	public static String getCharsetName() {
		try {
			MasterDAO masterDao = new MasterDAO();
			CodeName codeName = masterDao.selectCodeNameList(CATEGORY_CODE_FILE_SAVE_ROOT_PATH, CATEGORY_CODE_FILE_SAVE_ROOT_PATH, null, null, null, true).get(0);

			return codeName.getValue3();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "CSV保存Charset取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.util.FileWriterRowHandler#closeWriter()
	 */
	public void close() {
		try {
			super.close();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "CSVファイルクローズ"), e);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		close();
	}
}
