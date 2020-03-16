package jp.co.ctcg.easset.util;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import jp.ac.wakhok.tomoharu.csv.CSVTokenizer;
import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 結果行をWriterに出力するRowHandler
 * @author sai
 *
 */
public class CsvReaderRowHandler implements Closeable {
	// ファイル保存・ダウンロード関連ルックアップタイプ
	private static final String CATEGORY_CODE_FILE_SAVE_ROOT_PATH = "FILE_SAVE_ROOT_PATH"; // ファイル保存先

	private BufferedReader inputReader; // データ読込先

	private int headerRowCount; // ヘッダ行数
	private Class<?> rowClass; // 行クラス
	private Map<String, Class<?>> propTypeMap = new HashMap<String, Class<?>>(); // プロパティ設定保存Map
	private String[] inputPropTitles = new String[0]; // データタイトル(ファイルから読み込み)
	private String[] inputProps = new String[0]; // データ入力対照プロパティ
	private Format[] inputPropFormats = new Format[0]; // データ入力対照プロパティフォーマット

	// 区切文字
	private static final String DATA_SEPARATOR_ESCAPE_ROW = Function.getCsvEnterStr().replaceAll("\\[", "\\\\[").replaceAll("\\]", "\\\\]"); // 行

	private long rowCount = 0; // 入力行数
	private int rowStatus; // 行読み込み時のステータス 0:正常,1:エラー有
	private StringBuffer rowErrorMessage; // 行読み込み時のエラーメッセージ

	// 読込ステータス
	public static final int ROW_STATUS_SUCCESS = 0;
	public static final int ROW_STATUS_ERROR = -1;

	private String rowStrTemp = null; // handleId使用時に行データを保存

	/**
	 *
	 * @param fileId 読込ファイル
	 * @param headerRowCount ヘッダ行数
	 * @param rowClass 行データを格納するデータ型
	 * @param inputProps 入力プロパティ名
	 * @param inputPropFormats 入力プロパティフォーマット
	 * @throws AppException
	 */
	public CsvReaderRowHandler(String fileId, int headerRowCount, Class<?> rowClass, String[] inputProps, Format[] inputPropFormats) throws AppException {
		try {
			MasterDAO masterDao = new MasterDAO();
			CodeName codeName = masterDao.selectCodeNameList(CATEGORY_CODE_FILE_SAVE_ROOT_PATH, CATEGORY_CODE_FILE_SAVE_ROOT_PATH, null, null, null, true).get(0);

			String fileSavePath = codeName.getValue2(); // 一時ファイル保存先

			this.inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileSavePath + "/" + fileId), getCharsetName()));

			for(int i = 0; i < headerRowCount; i++) {
				String rowStr = inputReader.readLine(); // ヘッダ行スキップ

				if(i == (headerRowCount - 1)) { // タイトル取得(エラーメッセージ用)
					List<String> titleList = new ArrayList<String>();

					CSVTokenizer tokenizer = new CSVTokenizer(rowStr);
					while(tokenizer.hasMoreTokens()) {
						titleList.add(tokenizer.nextToken());
					}

					this.inputPropTitles = titleList.toArray(inputPropTitles);
				}
			}

			this.headerRowCount = headerRowCount;
			this.rowClass = rowClass;
			PropertyDescriptor[] propDec = PropertyUtils.getPropertyDescriptors(rowClass);
			for(int i = 0; i < propDec.length; i++) {
				propTypeMap.put(propDec[i].getName(), propDec[i].getPropertyType());
			}

			this.inputProps = inputProps;
			this.inputPropFormats = inputPropFormats;
		} catch (Exception e) {
			throw new AppException(MsgManager.getMessage(MsgManager.MSGID200041, "CSVファイル読込"), e);
		}

	}

	/**
	 *
	 * @param accessLevel アクセスレベル
	 * @param fileId 読込ファイル
	 * @param rowClass 行データを格納するデータ型
	 * @param itemDef 項目定義
	 * @throws AppException
	 */
	public CsvReaderRowHandler(String accessLevel, String fileId, Class<?> rowClass, String itemDef) throws AppException {
		this(accessLevel, fileId, rowClass, itemDef, null, 0);
	}

	/**
	 *
	 * @param accessLevel アクセスレベル
	 * @param fileId 読込ファイル
	 * @param rowClass 行データを格納するデータ型
	 * @param itemDef 項目定義
	 * @param skipRowCount ファイル内で無視する先頭行数
	 * @throws AppException
	 */
	public CsvReaderRowHandler(String accessLevel, String fileId, Class<?> rowClass, String itemDef, int skipRowCount) throws AppException {
		this(accessLevel, fileId, rowClass, itemDef, null, skipRowCount);
	}

	/**
	 *
	 * @param accessLevel アクセスレベル
	 * @param fileId 読込ファイル
	 * @param rowClass 行データを格納するデータ型
	 * @param itemDef 項目定義
	 * @param updatePropertyList 更新項目一覧
	 * @throws AppException
	 */
	public CsvReaderRowHandler(String accessLevel, String fileId, Class<?> rowClass, String itemDef, List<CodeName> updatePropertyList) throws AppException {
		this(accessLevel, fileId, rowClass, itemDef, updatePropertyList, 0);
	}

	/**
	 *
	 * @param accessLevel アクセスレベル
	 * @param fileId 読込ファイル
	 * @param rowClass 行データを格納するデータ型
	 * @param itemDef 項目定義
	 * @param updatePropertyList 更新項目一覧
	 * @param skipRowCount ファイル内で無視する先頭行数
	 * @throws AppException
	 */
	public CsvReaderRowHandler(String accessLevel, String fileId, Class<?> rowClass, String itemDef, List<CodeName> updatePropertyList, int skipRowCount) throws AppException {
		try {
			MasterDAO masterDao = new MasterDAO();

			/////////////////////////////////////////// 項目定義取得
			// プロパティ情報取得
			StringBuffer header = new StringBuffer();
			List<String> propList = new ArrayList<String>();
			List<Format> propFormatList = new ArrayList<Format>();
			masterDao.setCsvDefForUpload(itemDef, accessLevel, header, propList, propFormatList);

			// ヘッダ文字列をヘッダ項目Mapに変換
			Map<String, Integer> propTitleMap = new HashMap<String, Integer>();
			if(header.length() > 0) {
				String categoryStr = header.substring(0, header.indexOf("\n"));
				String propStr = header.substring(header.indexOf("\n") + 1, header.length());

				CSVTokenizer categoryStrTokenizer = new CSVTokenizer(categoryStr);
				CSVTokenizer propStrTokenizer = new CSVTokenizer(propStr);

				int idx = 0;
				String lastCategoryStr = "";
				while(propStrTokenizer.hasMoreTokens()) {
					String prop = propStrTokenizer.nextToken();
					String cate = categoryStrTokenizer.nextToken();
					if(cate.equals("")) cate = lastCategoryStr;

					String key = cate + "_" + prop;

					lastCategoryStr = cate;

					propTitleMap.put(key, idx);
					idx++;
				}
			}

			/////////////////////////////////////////// CSVファイルヘッダ情報取得
			CodeName codeName = masterDao.selectCodeNameList(CATEGORY_CODE_FILE_SAVE_ROOT_PATH, CATEGORY_CODE_FILE_SAVE_ROOT_PATH, null, null, null, true).get(0);

			String fileSavePath = codeName.getValue2(); // 一時ファイル保存先

			this.inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileSavePath + "/" + fileId), getCharsetName()));

			// スキップ行
			for(int i = 0; i < skipRowCount; i++) {
				inputReader.readLine();
			}

			// ヘッダ行
			String firstLine = inputReader.readLine();

			String fileCategoryStr;
			String filePropStr;
			if(firstLine.startsWith(",")) {
				fileCategoryStr = firstLine; // カテゴリ行読込
				filePropStr = inputReader.readLine(); // プロパティ行読込
				this.headerRowCount = 2;
			} else { // エクセル編集により最初の行が自動削除（カテゴリが全て空白）されている場合対応
				fileCategoryStr = ""; // カテゴリ行読込
				filePropStr = firstLine; // プロパティ行読込
				this.headerRowCount = 1;
			}

			CSVTokenizer fileCategoryStrTokenizer = new CSVTokenizer(fileCategoryStr);
			CSVTokenizer filePropStrTokenizer = new CSVTokenizer(filePropStr);

			List<String> titleList = new ArrayList<String>();
			List<String> inputPropList = new ArrayList<String>();
			List<Format> inputPropFormatList = new ArrayList<Format>();

			// 更新対象項目をセットに保存
			HashSet<String> updatePropSet = null;
			if(updatePropertyList != null) {
				updatePropSet = new HashSet<String>();
				for(int i = 0; i < updatePropertyList.size(); i++) {
					updatePropSet.add(updatePropertyList.get(i).getValue3());
				}
			}

			// 読込対象項目設定
			String lastCategoryStr = "";
			while(filePropStrTokenizer.hasMoreTokens()) {
				String prop = filePropStrTokenizer.nextToken();

				String cate = lastCategoryStr;
				if(fileCategoryStrTokenizer.hasMoreTokens()) {
					cate = fileCategoryStrTokenizer.nextToken();
					if(cate.equals("")) cate = lastCategoryStr;
				}

				String key = cate + "_" + prop;

				if(propTitleMap.containsKey(key)) { // 読込可項目
					int idx = propTitleMap.get(key).intValue();

					String property = propList.get(idx);
					if(updatePropSet == null || updatePropSet.contains(property)) { // 全項目が更新対象 or 更新対象に選択されている
						titleList.add(prop);
						inputPropList.add(propList.get(idx));
						inputPropFormatList.add(propFormatList.get(idx));
					} else { // 読込不要項目
						titleList.add(null);
						inputPropList.add(null);
						inputPropFormatList.add(null);
					}
				} else { // 読込不可項目
					titleList.add(null);
					inputPropList.add(null);
					inputPropFormatList.add(null);
				}

				lastCategoryStr = cate;
			}

			this.inputPropTitles = titleList.toArray(inputPropTitles);
			this.inputProps = inputPropList.toArray(inputProps);
			this.inputPropFormats = inputPropFormatList.toArray(inputPropFormats);

			this.rowClass = rowClass;
			PropertyDescriptor[] propDec = PropertyUtils.getPropertyDescriptors(rowClass);
			for(int i = 0; i < propDec.length; i++) {
				propTypeMap.put(propDec[i].getName(), propDec[i].getPropertyType());
			}

		} catch (Exception e) {
			throw new AppException(MsgManager.getMessage(MsgManager.MSGID200041, "CSVファイル読込"), e);
		}

	}

	/**
	 * 一行データ識別ID読み込み
	 * @return データ識別ID
	 * @throws AppException
	 */
	public String handleId() throws AppException {
		// ステータス初期化
		rowStatus = ROW_STATUS_SUCCESS;
		rowErrorMessage = new StringBuffer();

		// 行文字列の読込
		try {
			rowStrTemp = inputReader.readLine();
			if(rowStrTemp == null) return null; // データ終了

			CSVTokenizer tokenizer = new CSVTokenizer(rowStrTemp);

			String ret = null;
			while(tokenizer.hasMoreTokens()) {
				ret = tokenizer.nextToken();
				break;
			}

			return ret;
		} catch (Exception e) {
			throw new AppException(MsgManager.getMessage(MsgManager.MSGID200041, (headerRowCount + (rowCount + 1)) + "行目 - データ識別番号読込"), e);
		}
	}

	/**
	 * データ一行読み込み
	 * @return 読み込んだ値をセットしたデータクラス
	 * @throws IOException
	 * @throws ParseException
	 */
	public Object handleRow() throws AppException {
		return handleRow(null);
	}

	/**
	 * データ一行読み込み
	 * @param defaultObj 読み込みデータセット対象オブジェクト
	 * @return 読み込んだ値をセットしたデータクラス
	 * @throws IOException
	 * @throws ParseException
	 */
	public Object handleRow(Object defaultObj) throws AppException {
		// ステータス初期化
		rowStatus = ROW_STATUS_SUCCESS;
		rowErrorMessage = new StringBuffer();

		// 行文字列の読込
		String currentPropName = ""; // カレント処理列
		try {
			String rowStr = null;

			if(rowStrTemp == null) {
				rowStr = inputReader.readLine();
			} else {
				rowStr = rowStrTemp;
				rowStrTemp = null;
			}
			if(rowStr == null) return null; // データ終了

			rowCount++;

			Object ret = null;
			if(defaultObj == null) {
				ret = rowClass.getDeclaredConstructor().newInstance();
			} else {
				ret = defaultObj;
			}
			CSVTokenizer tokenizer = new CSVTokenizer(rowStr);

			int colCt = 0;
			while(tokenizer.hasMoreTokens()) {
				String colValue = tokenizer.nextToken();
				int colStatus = ROW_STATUS_SUCCESS; // カラム単位のステータス

				if(colCt >= inputProps.length) break; // CSV項目数 > 指定プロパティ数

				String prop = inputProps[colCt];
				currentPropName = inputPropTitles[colCt];
				Object propValue = null;

				if(inputProps[colCt] != null) {
					if(colValue != null && !colValue.equals("")) {
						Format format = inputPropFormats[colCt];
						if(format != null) {
							try {
								propValue = format.parseObject(colValue);
							} catch (ParseException e) {
								rowStatus = ROW_STATUS_ERROR;
								colStatus = ROW_STATUS_ERROR;
								if(rowErrorMessage.length() > 0) rowErrorMessage.append(",");
								rowErrorMessage.append(currentPropName + "：解析エラー(" + colValue + ")");
							}
						} else {
							// 改行文字エスケープ
							colValue = colValue.replaceAll(DATA_SEPARATOR_ESCAPE_ROW, "\n");
							propValue = colValue;
						}

						// 数値型クラス調整
						if(propTypeMap.containsKey(prop)) {
							Class<?> cls = propTypeMap.get(prop);
							try {
								if(cls.getCanonicalName().equals("java.lang.Integer")) {
									propValue = Integer.valueOf(propValue.toString());
								} else if(cls.getCanonicalName().equals("java.lang.Long")) {
									propValue = Long.valueOf(propValue.toString());
								} else if(cls.getCanonicalName().equals("java.lang.Float")) {
									propValue = Float.valueOf(propValue.toString());
								} else if(cls.getCanonicalName().equals("java.lang.Double")) {
									propValue = Double.valueOf(propValue.toString());
								}
							} catch (NumberFormatException e) {
								rowStatus = ROW_STATUS_ERROR;
								colStatus = ROW_STATUS_ERROR;
								if(rowErrorMessage.length() > 0) rowErrorMessage.append(",");
								rowErrorMessage.append(currentPropName + "：不正な数値(" + colValue + ")");
							}
						}
					}

					if(colStatus == ROW_STATUS_SUCCESS) { // カラム読み込みでエラーが発生していない場合のみセット
						PropertyUtils.setProperty(ret, prop, propValue);
					}

				}

				colCt++;
			}

			return ret;
		} catch (Exception e) {
			throw new AppException(MsgManager.getMessage(MsgManager.MSGID200041, (headerRowCount + rowCount) + "行目 - " + currentPropName + " のデータ読込"), e);
		}
	}

	/**
	 * 結果行数を取得する。
	 * @return 結果行数
	 */
	public long getRowCount() {
		return rowCount;
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
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "CSV読込Charset取得"), e);
		}
	}

	/**
	 *
	 */
	@Override
	public void close() {
		try {
			inputReader.close();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "CSVファイルクローズ"), e);
		}
	}

	@Override
	protected void finalize() throws Throwable {
		close();
	}

	/**
	 * 行データ読込時のステータスを取得
	 * @return ROW_STATUS_SUCCESS:成功、ROW_STATUS_ERROR:エラー(失敗)
	 */
	public int getRowStatus() {
		return rowStatus;
	}

	/**
	 * 行データ読込時のメッセージを取得(エラー時)
	 * @return
	 */
	public String getRowErrorMessage() {
		return rowErrorMessage.toString();
	}

	/**
	 * ファイル上の項目一覧取得
	 * ただし読込不可項目は除外
	 * @return ファイル上の項目一覧取得
	 */
	public String[] getInputProps() {
		List<String> list = new ArrayList<String>();

		for(int i = 0; i < inputProps.length; i++) {
			if(inputProps[i] != null) list.add(inputProps[i]);
		}

		return list.toArray(inputProps);
	}

	/**
	 * ファイル上の項目フォーマット一覧取得
	 * ただし読込不可項目は除外
	 * @return ファイル上の項目フォーマット一覧取得
	 */
	public Format[] getInputPropFormats() {
		List<Format> list = new ArrayList<Format>();

		for(int i = 0; i < inputProps.length; i++) {
			if(inputProps[i] != null) list.add(inputPropFormats[i]);
		}

		return list.toArray(inputPropFormats);
	}

	/**
	 * @return the headerRowCount
	 */
	public int getHeaderRowCount() {
		return headerRowCount;
	}
}
