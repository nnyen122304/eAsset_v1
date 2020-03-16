/*===================================================================
 * ファイル名 : FileDownloadServlet.java
 * 概要説明   : ファイルダウンロード用Servlet
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-05-17 1.0  崔           新規
 *=================================================================== */

package jp.co.ctcg.easset.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.session.MasterSession;

//import org.apache.catalina.connector.ClientAbortException;

public class FileDownloadServlet extends HttpServlet {
	private String requestCharacterEncoding; // リクエストエンコード
	private String fileNameCharacterEncoding; // ダウンロードファイル名エンコード

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private MasterSession masterSession;
	private static final String CATEGORY_CODE_FILE_SAVE_ROOT_PATH = "FILE_SAVE_ROOT_PATH";

	/* (非 Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		requestCharacterEncoding = config.getInitParameter("requestCharacterEncoding");
		fileNameCharacterEncoding = config.getInitParameter("fileNameCharacterEncoding");
	}

	/* (非 Javadoc)
	 * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		BufferedInputStream in = null;
		BufferedOutputStream out = null;
		try {
			if(requestCharacterEncoding != null) req.setCharacterEncoding(requestCharacterEncoding);

			// fileId, filePathからファイル取得
			String filePath = req.getParameter("filePath");
			File file;

			if(filePath == null || filePath.equals("")) { // 一時保存領域からダウンロード
				CodeName codeName = masterSession.getCodeName(CATEGORY_CODE_FILE_SAVE_ROOT_PATH, CATEGORY_CODE_FILE_SAVE_ROOT_PATH, null, null);
				String path = codeName.getValue2();
//				ServletContext rootContext = this.getServletContext().getContext("/");
//				file = new File(rootContext.getRealPath("download/" + req.getParameter("fileId")));
				file = new File(path + "/" + req.getParameter("fileId"));
			} else { // 本保存領域からダウンロード
				file = new File(filePath + "/" + req.getParameter("fileId"));
			}

			//HTTPヘッダの出力

			// contentType
			String contentType = req.getParameter("contentType");
			if(contentType == null) contentType = "application/octet-stream";
			res.setContentType(contentType);

			String contentDisposition = req.getParameter("contentDisposition");
			if(contentDisposition == null) contentDisposition = "attachment";

			// fileName
			String fileName = null;
			if(contentDisposition.equals("attachment")) {
				fileName = req.getParameter("fileName");
				if(fileName == null) file.getName();

				if(fileNameCharacterEncoding != null) {
					byte[] fileNameB = fileName.getBytes(fileNameCharacterEncoding);
					fileName = new String(fileNameB, "ISO-8859-1");
				}

				contentDisposition += "; filename=\"" + fileName + "\"";
			}
			res.setHeader("Content-disposition", contentDisposition);

			res.setContentLength((int)file.length());
			res.setHeader("Expires", "0");
			res.setHeader("Cache-Control", "must-revalidate, post-check=0,pre-check=0");
			res.setHeader("Pragma", "private");

			in = new BufferedInputStream(new FileInputStream(file));
			out = new BufferedOutputStream(res.getOutputStream());

			byte buf[]=new byte[1024];
			int len;
			while((len=in.read(buf))!=-1){
				out.write(buf,0,len);
			}
		// } catch (ClientAbortException e) {
			//●ダウンロード処理中にダウンロードダイアログの「キャンセル」が
			//クリックされた場合の例外。
			//●ただし、ダウンロードダイアログが表示されているバックグラウンドで
			//ブラウザへのダウンロードが行われていることに留意すること。
			//●つまり小さいファイルでは、ダイアログが表示される時には、ダウンロード
			//処理は完了し、サーブレットは終了してしまっており、SocketException
			//も発生しないということです。
		} catch (Exception e) {
			e.printStackTrace();
			if(!res.isCommitted()) {
				//ファイルダウンロード用のHTTPヘッダをリセットします。
				res.reset();
				res.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR , e.toString());
			}
		} finally {
			if (in != null) {
				in.close();
			}
			if (out != null) {
				out.flush();
				out.close();
			}
		}
		return;
	}


}
