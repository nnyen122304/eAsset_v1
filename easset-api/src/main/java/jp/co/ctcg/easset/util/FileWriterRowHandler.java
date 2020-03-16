package jp.co.ctcg.easset.util;

import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.Format;

/**
 * 結果行をファイルに出力するRowHandler
 * @author sai
 *
 */
public class FileWriterRowHandler extends WriterRowHandler implements Closeable {
	private static final int DEFAULT_BUFFER_SIZE = 1024;

	private OutputStreamWriter outputWriter;

	private File file;

	/**
	 *
	 * @param saveFile データ出力先ファイル
	 * @param charsetName ファイル出力文字Charset名
	 * @param headerRow タイトル行。指定するとデータ行の前に指定文字列が出力される。（nullを指定すると出力されない。）
	 * @param outputPropFormats 出力プロパティフォーマット
	 * @param propSeparator Writer出力時のプロパティ（列）セパレータ文字
	 * @param rowSeparator Writer出力時の行セパレータ文字
	 * @param propSeparatorReplace 列セパレータ置換文字（データに区切文字が含まれていた場合、この文字に置換）
	 * @param rowSeparatorReplace 行セパレータ置換文字（データに区切文字が含まれていた場合、この文字に置換）
	 * @param replaceSourceStr 置換対象文字列（複数指定可）
	 * @param replaceStr 置換対象文字列に置き換わる文字列を指定
	 * @throws IOException
	 */
	public FileWriterRowHandler(File saveFile, String charsetName, String headerRow, String[] outputProps, Format[] outputPropFormats, String propSeparator, String rowSeparator, String propSeparatorReplace, String rowSeparatorReplace, String[] replaceSourceStr, String[] replaceStr) throws IOException {
		this(saveFile, charsetName, headerRow, outputProps, outputPropFormats, propSeparator, rowSeparator, propSeparatorReplace, rowSeparatorReplace, replaceSourceStr, replaceStr, DEFAULT_BUFFER_SIZE);
	}

	/**
	 *
	 * @param saveFile データ出力先ファイル
	 * @param charsetName ファイル出力文字Charset名
	 * @param headerRow タイトル行。指定するとデータ行の前に指定文字列が出力される。（nullを指定すると出力されない。）
	 * @param outputPropFormats 出力プロパティフォーマット
	 * @param propSeparator Writer出力時のプロパティ（列）セパレータ文字
	 * @param rowSeparator Writer出力時の行セパレータ文字
	 * @param propSeparatorReplace 列セパレータ置換文字（データに区切文字が含まれていた場合、この文字に置換）
	 * @param rowSeparatorReplace 行セパレータ置換文字（データに区切文字が含まれていた場合、この文字に置換）
	 * @param replaceSourceStr 置換対象文字列（複数指定可）
	 * @param replaceStr 置換対象文字列に置き換わる文字列を指定
	 * @param bufferSize ファイル出力時のバッファサイズ
	 * @throws IOException
	 */
	public FileWriterRowHandler(File saveFile, String charsetName, String headerRow, String[] outputProps, Format[] outputPropFormats, String propSeparator, String rowSeparator, String propSeparatorReplace, String rowSeparatorReplace, String[] replaceSourceStr, String[] replaceStr, int bufferSize) throws IOException {
		// 出力先取得
		FileOutputStream fout = new FileOutputStream(saveFile);
		BufferedOutputStream bout = new BufferedOutputStream(fout, bufferSize);
		outputWriter = new OutputStreamWriter(bout, charsetName);

		// タイトル行出力
		if(headerRow != null) {
			outputWriter.write(headerRow);
			outputWriter.write(rowSeparator);
		}

		super.setOutputOption(outputWriter, outputProps, outputPropFormats, propSeparator, rowSeparator, propSeparatorReplace, rowSeparatorReplace, replaceSourceStr, replaceStr);

		file = saveFile;
	}

	/**
	 * 出力先ファイルストリームのclose
	 * @throws IOException
	 * @throws IOException
	 */
	@Override
	public void close() throws IOException {
		try {
			outputWriter.flush();
		} finally {
			outputWriter.close();
		}
	}

	/**
	 * 出力ファイルを取得する。
	 * @return the file
	 */
	public File getFile() {
		return file;
	}

	/* (非 Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	protected void finalize() throws Throwable {

		if(outputWriter != null) {
			outputWriter.close();
		}
	}
}
