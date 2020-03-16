/*===================================================================
 * ファイル名 : ExcelExporter.java
 * 概要説明   : Excelファイル作成用
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2013-10-08 1.0  李            新規
 *=================================================================== */
package jp.co.ctcg.easset.util;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.dao.SqlMapUtil;
import jp.co.ctcg.easset.template.utils.SysException;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.fill.JRFileVirtualizer;
import net.sf.jasperreports.engine.util.JRLoader;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 *
 * @author z1g7092
 *
 */
public class ExcelExporter {
	public static final String SUBREPORT_PARAM_PREFIX = "SUBREPORT_"; // サブレポートパラメータとして識別する接頭語

	// ファイル保存・ダウンロード関連ルックアップタイプ
	private static final String CATEGORY_CODE_FILE_SAVE_ROOT_PATH = "FILE_SAVE_ROOT_PATH"; // ファイル保存先

	private static final int FILE_VIRTUALIZER_MAX_SIZE = 100;

	private static final String FILE_EXTENSION = ".xls"; // ファイル拡張子

	// private static final String FILE_SECURITY_PASSWORD = "EASSET_PASS_10O3284;LasdfadsfIUE4r54ithoirewtvgfmdsagfdsafSAF;AISLFJEliad098ea;lhbvplaAFDSAFAFUHYQREWVGFAKJBVFAD"; // PDFファイルセキュリティ解除パスワード

	private File xlsFile; // 出力EXCELファイル

	private static Map<String, JasperReport> reportObjectCache = new HashMap<String, JasperReport>(); // レポートオブジェクトのキャッシュ用

	/**
	 * 指定したレポートを実行してPDFファイルを作成し、作成されたファイルのダウンロード用参照IDを戻す。
	 *
	 * @param reportName パッケージ,拡張子付きのフルパス 例) jp/co/ctcg/.../ReportFile.jasper
	 * @param reportParam レポート実行時パラメータ
	 * @return
	 * @throws IOException
	 */
	public void exportExcel(String reportName, Map<String, Object> reportParam) throws IOException {
		Connection conn = null;
		try {
			SqlMapClient sqlMap = SqlMapUtil.getSqlMapInstance();

			MasterDAO masterDao = new MasterDAO();
			CodeName codeName = masterDao.selectCodeNameList(CATEGORY_CODE_FILE_SAVE_ROOT_PATH, CATEGORY_CODE_FILE_SAVE_ROOT_PATH, null, null, null, true).get(0);

			String fileSavePath = codeName.getValue2(); // 一時ファイル保存先

			// レポート生成
			if(reportParam == null) reportParam = new HashMap<String, Object>();
			reportParam.put(JRParameter.REPORT_VIRTUALIZER, new JRFileVirtualizer(FILE_VIRTUALIZER_MAX_SIZE, fileSavePath));

			// パラメータにサブレポートが含まれていればオブジェクトとして再セット
			for(Iterator<String> iter = reportParam.keySet().iterator(); iter.hasNext();) {
				String key = iter.next();

				if(key.startsWith(SUBREPORT_PARAM_PREFIX)) {
					String reportClassPath = (String) reportParam.get(key);
					reportParam.put(key, getReportObject(reportClassPath));
				}
			}

			conn = sqlMap.getDataSource().getConnection();
			JasperPrint print = JasperFillManager.fillReport(getReportObject(reportName), reportParam, conn);

			// PDF保存先の空ファイルを生成する。
			xlsFile = File.createTempFile("NEA__", FILE_EXTENSION, new File(fileSavePath));

			// PDFファイル生成
            JRXlsExporter   exporterXLS = new JRXlsExporter();
            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, print); // 出力オブジェクトの設定
            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_FILE, xlsFile); // 出力先の設定
			exporterXLS.setParameter(JRXlsExporterParameter.IGNORE_PAGE_MARGINS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
            exporterXLS.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE); // セルの書式適用

            exporterXLS.exportReport(); // 出力の実行

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "XLS生成(DBアクセス)"), e);
		} catch (JRException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "XLS生成(レポート生成)"), e);
		} finally {
			if(conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "XLS生成(DB接続リリース)"), e);
				}
			}
		}
	}

	/**
	 * 作成したファイルのダウンロード用参照IDを取得する
	 * @return
	 * @throws IOException
	 */
	public String getFileId() throws IOException {
		return xlsFile.getName();
	}

	/**
	 * 出力ファイルを取得する。
	 * @return the file
	 */
	public File getFile() {
		return xlsFile;
	}

	/**
	 * レポートオブジェクトを取得する
	 * @param reportClassPath レポートクラスパス
	 * @return レポートオブジェクト（一度取得したレポートオブジェクトはキャッシュされる）
	 * @throws JRException
	 */
	private static JasperReport getReportObject(String reportClassPath) throws JRException {
		if(!reportObjectCache.containsKey(reportClassPath)) { // レポートオブジェクトがキャッシュにない場合はロード
			reportObjectCache.put(reportClassPath, (JasperReport) JRLoader.loadObject(PdfExporter.class.getClassLoader().getResourceAsStream(reportClassPath)));
		}

		return reportObjectCache.get(reportClassPath);
	}
}
