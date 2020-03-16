package jp.co.ctcg.easset.util;

import java.beans.PropertyDescriptor;
import java.io.Writer;
import java.text.Format;
import java.util.HashSet;
import java.util.Set;

import com.ibatis.sqlmap.client.event.RowHandler;
import org.apache.commons.beanutils.PropertyUtils;

/**
 * 結果行をWriterに出力するRowHandler
 * @author sai
 *
 */
public class WriterRowHandler implements RowHandler {
	private Writer outputWriter; // データ出力先

	private String[] outputProps; // データ出力対照プロパティ
	private Format[] outputPropFormats; // データ出力対照プロパティフォーマット
	private int propLength; // outputProps配列の長さ

	private String propSeparator; // 列区切り文字
	private String rowSeparator; // 行区切り文字
	private String propSeparatorReplace; // 列区切り文字置換文字（データに区切文字が含まれていた場合、この文字に置換）
	private String rowSeparatorReplace; // 行区切り文字置換文字（データに区切文字が含まれていた場合、この文字に置換）

	private String[] replaceSourceStr; // 置換対象文字列（複数指定可）
	private String[] replaceStr; // 置換対象文字列に置き換わる文字列を指定

	private long rowCount = 0; // 出力行数
	private Set<String> existsPropSet = new HashSet<String>();

	/**
	 *
	 * @param outputWriter 結果行データ出力先
	 * @param outputProps 出力プロパティ名
	 * @param outputPropFormats 出力プロパティフォーマット
	 * @param propSeparator Writer出力時のプロパティ（列）セパレータ文字
	 * @param rowSeparator Writer出力時の行セパレータ文字
	 * @param propSeparatorReplace 列セパレータ置換文字（データに区切文字が含まれていた場合、この文字に置換）
	 * @param rowSeparatorReplace 行セパレータ置換文字（データに区切文字が含まれていた場合、この文字に置換）
	 * @param replaceSourceStr 置換対象文字列（複数指定可）
	 * @param replaceStr 置換対象文字列に置き換わる文字列を指定
	 */
	public WriterRowHandler(Writer outputWriter, String[] outputProps, Format[] outputPropFormats, String propSeparator, String rowSeparator, String propSeparatorReplace, String rowSeparatorReplace, String[] replaceSourceStr, String[] replaceStr) {
		setOutputOption(outputWriter, outputProps, outputPropFormats, propSeparator, rowSeparator, propSeparatorReplace, rowSeparatorReplace, replaceSourceStr, replaceStr);
	}

	/**
	 * デフォルトコンストラクタ
	 * パラメータなしでインスタンス化された場合、setOutputOptionにより出力オプションを指定する必要がある。
	 */
	protected WriterRowHandler() {

	}

	/**
	 * 出力用のオプションセット
	 * @param outputWriter 結果行データ出力先
	 * @param outputProps 出力プロパティ名
	 * @param outputPropFormats 出力プロパティフォーマット
	 * @param propSeparator Writer出力時のプロパティ（列）セパレータ文字
	 * @param rowSeparator Writer出力時の行セパレータ文字
	 * @param propSeparatorReplace 列セパレータ置換文字（データに区切文字が含まれていた場合、この文字に置換）
	 * @param rowSeparatorReplace 行セパレータ置換文字（データに区切文字が含まれていた場合、この文字に置換）
	 * @param replaceSourceStr 置換対象文字列（複数指定可）
	 * @param replaceStr 置換対象文字列に置き換わる文字列を指定
	 */
	protected void setOutputOption(Writer outputWriter, String[] outputProps, Format[] outputPropFormats, String propSeparator, String rowSeparator, String propSeparatorReplace, String rowSeparatorReplace, String[] replaceSourceStr, String[] replaceStr) {
		this.outputWriter = outputWriter;
		this.outputProps = outputProps;
		this.outputPropFormats = outputPropFormats;
		this.propLength = outputProps.length;
		this.propSeparator = propSeparator;
		this.rowSeparator = rowSeparator;
		this.propSeparatorReplace = propSeparatorReplace;
		this.rowSeparatorReplace = rowSeparatorReplace;
		this.replaceSourceStr = replaceSourceStr;
		this.replaceStr = replaceStr;
	}

	/*
	 * (非 Javadoc)
	 * @see com.ibatis.sqlmap.client.event.RowHandler#handleRow(java.lang.Object)
	 */
	public void handleRow(Object valueObject) {
		if(rowCount == 0) { // 1回目のhandleでデータクラスサポートプロパティを取得
			PropertyDescriptor[] pd = PropertyUtils.getPropertyDescriptors(valueObject);
			for(int i = 0; i < pd.length; i++) {
				existsPropSet.add(pd[i].getName());
			}
		}

		try {
			StringBuffer rowStr = new StringBuffer();

			// 行文字列の作成
			for(int i = 0; i < propLength; i++) {
				if(existsPropSet.contains(outputProps[i])) { // データクラスサポートプロパティ
					Object val = PropertyUtils.getProperty(valueObject, outputProps[i]);

					if(val != null) {
						String strVal;

						// フォーマット
						if(outputPropFormats != null && outputPropFormats[i] != null) {
							strVal = outputPropFormats[i].format(val);
						} else {
							strVal = val.toString();
						}

						// 区切り文字エスケープ
						if(propSeparatorReplace != null && !propSeparatorReplace.equals("")) {
							strVal = strVal.replaceAll(propSeparator, propSeparatorReplace);
						}
						if(rowSeparatorReplace != null && !rowSeparatorReplace.equals("")) {
							strVal = strVal.replaceAll("\r\n", rowSeparatorReplace).replaceAll("\r", rowSeparatorReplace).replaceAll("\n", rowSeparatorReplace);
						}

						if(replaceSourceStr != null && replaceStr != null) {
							for(int j = 0; j < replaceSourceStr.length; j++) {
								// 指定文字エスケープ
								strVal = strVal.replaceAll(replaceSourceStr[j], replaceStr[j]);
							}
						}

						if(strVal.indexOf(",") >= 0) {
							rowStr.append("\"" + strVal + "\"");
						} else {
							rowStr.append(strVal);
						}
					}
				}

				// 列区切り文字追加
				if(i < propLength - 1) rowStr.append(propSeparator);
			}

			// 行区切り文字追加
			rowStr.append(rowSeparator);

			// 行文字列の出力
			outputWriter.write(rowStr.toString());

			rowCount++;
		} catch (Exception e) {
//			try {
//				outputWriter.close();
//			} catch (IOException ioe) {
//				throw new RuntimeException(e);
//			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * 結果行数を取得する。
	 * @return 結果行数
	 */
	public long getRowCount() {
		return rowCount;
	}
}
