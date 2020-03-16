package jp.co.ctcg.easset.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.catalina.connector.ClientAbortException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.session.MasterSession;


public class FileUploadServlet extends HttpServlet {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private MasterSession masterSession;
	private static final String CATEGORY_CODE_FILE_SAVE_ROOT_PATH = "FILE_SAVE_ROOT_PATH";

	@SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		//(1)アップロードファイルを格納するPATHを取得
//		String path = getServletContext().getContext("/").getRealPath("download");
		CodeName codeName = masterSession.getCodeName(CATEGORY_CODE_FILE_SAVE_ROOT_PATH, CATEGORY_CODE_FILE_SAVE_ROOT_PATH, null, null);
		String path = codeName.getValue2();

		//(2)ServletFileUploadオブジェクトを生成
		DiskFileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);

		//(3)アップロードする際の基準値を設定
		factory.setSizeThreshold(1024);
		upload.setSizeMax(-1);
		upload.setHeaderEncoding("UTF-8");
		req.setCharacterEncoding("UTF-8");
		String fileName = "";
		StringBuffer returnFileName = new StringBuffer("");
		PrintWriter out = null;
		try {
			//	一時領域にファイル置く
			//(4)ファイルデータ(FileItemオブジェクト)を取得し、
			// Listオブジェクトとして返す
			List<FileItem> list = upload.parseRequest(req);
			//(5)ファイルデータ(FileItemオブジェクト)を順に処理
			Iterator<FileItem> iterator = list.iterator();
			while(iterator.hasNext()){
				//FileItem fItem = (FileItem)iterator.next();
				DiskFileItem dFileItem = (DiskFileItem)iterator.next();
				File filed = dFileItem.getStoreLocation();
				//(6)ファイルデータの場合、if内を実行
				if(!(dFileItem.isFormField())){
					//attachFileId = insertAttachFile(fItem);
					//(7)PATH名を除くファイル名のみを取得
					//fileName = dFileItem.getName();
					fileName = filed.getName();
					if((fileName != null) && (!fileName.equals(""))){
						//(8)PATH名を除くファイル名のみを取得
						//fileName=(new File(fileName)).getName();
						//(9)ファイルデータを指定されたファイルに書き出し
						//fItem.write(new File(path + "/" + filed.getName()));
						dFileItem.write(new File(path + "/" + fileName));
						if(returnFileName.length() > 0) returnFileName.append(",");
						returnFileName.append(fileName);

					}
				}
			}

			//	レスポンスヘッダセット
			res.setHeader("Expires", "0");
			res.setHeader("Cache-Control", "must-revalidate, post-check=0,pre-check=0");
			res.setHeader("Pragma", "private");
			res.setHeader("Content-Type", "application/json");

			//	結果をセット
			out = res.getWriter();
	        //out.print(String.valueOf(attachFileId));
	        out.print("{\"value\": \"" + returnFileName.toString() + "\"}");

		// }catch (ClientAbortException e) {
		// 	//	キャンセル処理
		}catch (Exception e) {
			e.printStackTrace();
			//ファイルアップロード用のHTTPヘッダをリセットします。
			if(!res.isCommitted()) {
				res.reset();
				res.sendError(HttpURLConnection.HTTP_INTERNAL_ERROR , e.toString());
			}
		} finally {
			if (out != null) {
				out.flush();
				out.close();
			}
		}
	}
}

