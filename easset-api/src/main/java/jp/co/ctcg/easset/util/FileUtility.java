/**
 * @(#)FileUtility.java  2002/9/11
 */
package jp.co.ctcg.easset.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

import com.ice.tar.TarEntry;
import com.ice.tar.TarInputStream;

/**
 * File関連ユーティリティクラス
 *
 * @author sai
 */
public class FileUtility {

	/**
	 * aTargetで指定したディレクトリ内（サブディレクトリ内も含む）の、全てのファイル、ディレクトリをリストで取得します。
	 *
	 * @param aTarget ディレクトリ
	 * @return 全ファイルのリスト
	 * @throws IOException
	 */
	public static List<File> listAllFiles(File aTarget) throws IOException {
		return listAllFiles(aTarget, null);
	}

	/**
	 * aTargetで指定したディレクトリ内（サブディレクトリ内も含む）の、全てのファイル、ディレクトリをリストで取得します。
	 *
	 * @param aTarget ディレクトリ
	 * @param aFilter ファイルを取得する際のフィルタ
	 * @return 全ファイルのリスト
	 * @throws IOException
	 */
	public static List<File> listAllFiles(File aTarget, FileFilter aFilter) throws IOException {
		if (aTarget == null) return null;
		if (aTarget.isFile()) return null;

		// ディレクトリ内を調べる
		File[] subDirectories = aTarget.listFiles(getDirectoryFilter());
		// サブディレクトリ
		File[] files = null; // フィルタされたファイル、ディレクトリ一覧

		if (aFilter == null) {
			files = aTarget.listFiles();
		} else {
			files = aTarget.listFiles(aFilter);
		}

		ArrayList<File> nameList = new ArrayList<File>();

		// ファイルをリストに追加
		for (int i = 0; i < files.length; i++) {
			nameList.add(files[i]);
		}

		// サブディレクトリを再起的に調べる
		for (int i = 0; i < subDirectories.length; i++) {
			List<File> sl = listAllFiles(subDirectories[i], aFilter);
			Iterator<File> iter = sl.iterator();

			while (iter.hasNext()) {
				nameList.add((File)iter.next());
			}
		}

		return nameList;
	}

	/**
	 * aTargetで指定したディレクトリ内（カレントディレクトリ内）の、全てのファイル、ディレクトリをリストで取得します。
	 *
	 * @param aTarget ディレクトリ
	 * @param aFilter ファイルを取得する際のフィルタ
	 * @return 全ファイルのリスト
	 * @throws IOException
	 */
	public static List<File> listAllFilesCurrent(File aTarget, FileFilter aFilter) throws IOException {
		if (aTarget == null) return null;
		if (aTarget.isFile()) return null;

		File[] files = null; // フィルタされたファイル、ディレクトリ一覧

		if (aFilter == null) {
			files = aTarget.listFiles();
		} else {
			files = aTarget.listFiles(aFilter);
		}

		ArrayList<File> nameList = new ArrayList<File>();

		// ファイルをリストに追加
		for (int i = 0; i < files.length; i++) {
			nameList.add(files[i]);
		}
		return nameList;
	}

	/**
	 * FileのComparatorオブジェクトを取得します。 並び順は ファイル > ディレクトリです。
	 * あとは名称をアスキーコード順で比較してソートします。
	 *
	 * @return FileのComparatorオブジェクト
	 */
	public static Comparator<File> getFileNameComparator() {
		Comparator<File> c = new Comparator<File>() {

			public int compare(File o1, File o2) {
				File f1 = null, f2 = null;
				int ret = -2;

				try {
					f1 = (File) o1;
				} catch (ClassCastException cce) {
					// 第1パラメタがFileじゃない
					ret = 1;
				}
				if (f1 == null) ret = 1;

				try {
					f2 = (File) o2;
				} catch (ClassCastException cce) {
					// 第2パラメタがFileじゃない
					if (ret == -2) {
						ret = -1;
					} else {
						ret = 0;
					}
				}
				if (f2 == null) {
					if (ret == -2) {
						ret = -1;
					} else {
						ret = 0;
					}
				}

				if (ret == -2) {
					ret = f1.getParent().compareTo(f2.getParent());
					if (ret == 0) {
						if (f1.isDirectory() && !f2.isDirectory()) {
							ret = -1;
						} else if (!f1.isDirectory() && f2.isDirectory()) {
							ret = 1;
						} else {
							ret = f1.getName().compareTo(f2.getName());
						}
					}
				}

				return ret;
			}

		};

		return c;
	}

	/**
	 * aFiltersを連鎖させるフィルタを取得する。
	 *
	 * @param aFilters 連鎖させるフィルタ
	 * @param aChainType true:and false:or
	 * @return aFiltersを連鎖して実行するフィルタ
	 */
	public static FileFilter getChainFilter(final FileFilter[] aFilters, final boolean aChainType) {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				if (aFilters == null) return false;
				boolean ret = false;
				if (aChainType) ret = true;

				for (int i = 0; i < aFilters.length; i++) {
					boolean result = aFilters[i].accept(pathname);

					if (aChainType && !result) { // and 連鎖で一度でもfalseがあれば
						ret = false;
						break;
					} else if (!aChainType && result) { // or 連鎖で一度でもtrueがあれば
						ret = true;
						break;
					}
				}

				return ret;
			}
		};

		return ret;
	}

	/**
	 * Directoryだけを取得するFileFilterです。
	 *
	 * @return Directoryだけを取得するFileFilter
	 */
	public static FileFilter getDirectoryFilter() {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				boolean ret = false;
				if (pathname == null) return false;
				if (pathname.isDirectory()) ret = true;

				return ret;
			}
		};

		return ret;
	}

	/**
	 * Fileだけを取得するFileFilterです。
	 *
	 * @return Fileだけを取得するFileFilter
	 */
	public static FileFilter getFileFilter() {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				boolean ret = false;
				if (pathname == null) return false;
				if (pathname.isFile()) ret = true;

				return ret;
			}
		};

		return ret;
	}

	/**
	 * 名前のFileFilterです。（前後方あいまい）
	 *
	 * @param aName 検索するファイル名
	 * @param aIgnoreCase true:大小文字区別なし false:大小文字区別あり
	 * @return 名前のFileFilter
	 */
	public static FileFilter getNameFilter(final String aName, final boolean aIgnoreCase) {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				boolean ret = false;
				if (pathname == null || aName == null) return false;
				if (aName.equals("")) return true;
				String fileName = pathname.getName();
				String searchName = aName;
				if (aIgnoreCase) {
					fileName = fileName.toUpperCase();
					searchName = aName.toUpperCase();
				}

				if (fileName.indexOf(searchName) != -1) ret = true;

				return ret;
			}
		};

		return ret;
	}


	/**
	 * 拡張子のFileFilterです。
	 *
	 * @param exts 対象拡張子一覧
	 * @param isInclude true : exts拡張子ファイルを取得 false : exts拡張子以外のファイルを取得
	 * @return 名前のFileFilter
	 */
	public static FileFilter getExtFilter(final String[] exts, final boolean isInclude) {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				if(exts == null) return false;
				if(pathname == null) return false;
				if(pathname.isDirectory()) return false;
				String fileName = pathname.getName();
				String ext = getExt(fileName);

				for(int i = 0; i < exts.length; i++) {
					if(ext.equals(exts[i])) {
						return isInclude;
					}
				}

				if(!isInclude) return true;
				return false;
			}
		};

		return ret;
	}

	/**
	 * 名前のFileFilterです。（後方あいまい）
	 *
	 * @param aName 検索するファイル名
	 * @param aIgnoreCase true:大小文字区別なし false:大小文字区別あり
	 * @return 名前のFileFilter
	 */
	public static FileFilter getStartsWithNameFilter(final String aName, final boolean aIgnoreCase) {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				boolean ret = false;
				if (pathname == null || aName == null) return false;
				if (aName.equals("")) return true;
				String fileName = pathname.getName();
				String searchName = aName;
				if (aIgnoreCase) {
					fileName = fileName.toUpperCase();
					searchName = aName.toUpperCase();
				}

				if (fileName.startsWith(searchName)) ret = true;

				return ret;
			}
		};

		return ret;
	}

	/**
	 * 名前のFileFilterです。（前方あいまい）
	 *
	 * @param aName 検索するファイル名
	 * @param aIgnoreCase true:大小文字区別なし false:大小文字区別あり
	 * @return 名前のFileFilter
	 */
	public static FileFilter getEndsWithNameFilter(final String aName, final boolean aIgnoreCase) {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				boolean ret = false;
				if (pathname == null || aName == null) return false;
				if (aName.equals("")) return true;
				String fileName = pathname.getName();
				String searchName = aName;
				if (aIgnoreCase) {
					fileName = fileName.toUpperCase();
					searchName = aName.toUpperCase();
				}

				if (fileName.endsWith(searchName)) ret = true;

				return ret;
			}
		};

		return ret;
	}

	/**
	 * ファイル更新日付のFileFilterです。
	 *
	 * @param aDateFrom 検索日付（自）
	 * @param aDateTo 検索日付（至）
	 * @return ファイル更新日付のFileFilter
	 */
	public static FileFilter getDateFilter(final Date aDateFrom, final Date aDateTo) {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				boolean ret = true;
				if (pathname == null) return false;
				Date updateDate = new Date(pathname.lastModified());

				if (aDateFrom != null && updateDate.compareTo(aDateFrom) < 0) ret = false;
				if (ret && aDateTo != null && updateDate.compareTo(aDateTo) > 0) ret = false;

				return ret;
			}
		};

		return ret;
	}

	/**
	 * ファイルサイズのFileFilterです。
	 *
	 * @param aSizeFrom 検索サイズ（自）
	 * @param aSizeTo 検索サイズ（至）
	 * @return ファイルサイズのFileFilter
	 */
	public static FileFilter getSizeFilter(final long aSizeFrom, final long aSizeTo) {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				boolean ret = true;
				if (pathname == null || pathname.isDirectory()) return false;

				long size = pathname.length();

				if (size < aSizeFrom) ret = false;
				if (size > aSizeTo) ret = false;

				return ret;
			}
		};

		return ret;
	}

	/**
	 * File内容のFileFilterです。
	 *
	 * @param aContents 検索するファイルの内容
	 * @param aIgnoreCase true:大小文字区別なし false:大小文字区別あり
	 * @return File内容のFileFilter
	 */
	public static FileFilter getContentsFilter(final String aContents, final boolean aIgnoreCase) {
		FileFilter ret = new FileFilter() {
			public boolean accept(File pathname) {
				boolean ret = false;
				if (pathname == null || pathname.isDirectory() || aContents == null) return false;
				if (aContents.equals("")) return true;

				// ファイルコンテンツの取得
				StringBuffer fileContentsBuf = new StringBuffer();
				BufferedReader reader = null;
				try {
					reader = new BufferedReader(new InputStreamReader(new FileInputStream(pathname), "JISAutoDetect"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					return ret;
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return ret;
				} finally {
					try {
						if(reader != null) reader.close();
					} catch (IOException e) {
						e.printStackTrace();
						return ret;
					}
				}
				char[] c = new char[2048];
				int len;
				try {
					len = reader.read(c);
					while (len > 0) {
						fileContentsBuf.append(c, 0, len);
						len = reader.read(c);
					}
				} catch (IOException e1) {
					e1.printStackTrace();
					return ret;
				} finally {
					try {
						reader.close();
					} catch (IOException e2) {
						e2.printStackTrace();
						return ret;
					}
				}

				String searchContents = aContents;
				String fileContents = fileContentsBuf.toString();
				if (aIgnoreCase) {
					fileContents = fileContents.toUpperCase();
					searchContents = aContents.toUpperCase();
				}

				if (fileContents.indexOf(searchContents) != -1) ret = true;

				return ret;
			}
		};

		return ret;
	}

	/**
	 * ディレクトリを削除します。 ディレクトリ内の全てのファイル、ディレクトリも削除されます。（階層的）
	 *
	 * @param aFile 削除するファイル
	 */
	public static boolean deleteAll(File aFile) {
		return deleteAll(aFile, null);
	}

	/**
	 * ディレクトリを削除します。 ディレクトリ内の全てのファイル、ディレクトリも削除されます。（階層的）
	 *
	 * @param aFile 削除するファイル
	 * @param aFilter 削除対象を絞るフィルタ（fileがディレクトリの場合のみ）
	 */
	public static boolean deleteAll(File aFile, FileFilter aFilter) {
		boolean ret = true;
		if (aFile == null || !aFile.exists()) return false;
		if (aFile.isDirectory()) {
			File[] files = aFile.listFiles(aFilter);

			if (files != null) {
				for (int i = 0; i < files.length; i++) {
					ret = deleteAll(files[i], aFilter);
					if (!ret) break;
				}
			}
		}
		if (ret) ret = aFile.delete();
		return ret;
	}

	/**
	 * aSourceを aDestにコピーします。
	 *
	 * @param aSource コピー元フォルダ、ファイル
	 * @param aDest コピー先フォルダ
	 * @param aIsReplace 上書きフラグ true:上書き false:上書きしない
	 * @return 成功:true 失敗:false
	 */
	public static boolean copy(File aSource, File aDest, boolean aIsReplace) throws IOException {
		return copy(aSource, aDest, aIsReplace, null);
	}

	/**
	 * aSourceを aDestにコピーします。
	 *
	 * @param aSource コピー元フォルダ、ファイル
	 * @param aDest コピー先フォルダ
	 * @param aIsReplace 上書きフラグ true:上書き false:上書きしない
	 * @param aFilter コピー対象を絞るFileFilter（sourceがディレクトリの場合のみ）
	 * @return 成功:true 失敗:false
	 */
	public static boolean copy(File aSource, File aDest, boolean aIsReplace, FileFilter aFilter) throws IOException {
		if (aSource == null || !aSource.exists()) return false;
		if (aDest == null) return false;

		boolean ret = true;
		List<File> sourceFile;
		File parent = aSource.getParentFile();

		if (aSource.isFile()) { // ターゲットがファイル
			sourceFile = new ArrayList<File>();
		} else { // ターゲットがディレクトリ
			sourceFile = listAllFiles(aSource, aFilter);
		}
		sourceFile.add(aSource);

		// まずはディレクトリを作成
		for (Iterator<File> iter = sourceFile.iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			if (file.isDirectory()) {
				String copyName = file.getAbsolutePath().substring(parent.getAbsolutePath().length() + 1);
				File copyDir = new File(aDest.getAbsolutePath() + File.separator + copyName);
				if (!copyDir.exists() && !copyDir.mkdirs()) {
					ret = false;
					break;
				}
			}
		}

		if (!ret) return ret;

		// ファイルをコピー
		for (Iterator<File> iter = sourceFile.iterator(); iter.hasNext();) {
			File file = (File) iter.next();
			if (file.isFile()) {
				String copyName = file.getAbsolutePath().substring(parent.getAbsolutePath().length() + 1);
				File copyFile = null;
				if(aDest.isDirectory()) {
					copyFile = new File(aDest.getAbsolutePath() + File.separator + copyName);
				} else {
					copyFile = aDest;
				}

				if (copyFile.exists()) {
					if (!aIsReplace) {
						ret = false;
						break;
					}
				}

				if (!file.canRead()) { // 読み込み不可
					ret = false;
					break;
				}
				if (copyFile.exists() && !copyFile.canWrite()) { // 上書き不可
					ret = false;
					break;
				}
				BufferedInputStream in = null;
				BufferedOutputStream out = null;

				try {
					in = new BufferedInputStream(new FileInputStream(file));
					out = new BufferedOutputStream(new FileOutputStream(copyFile));
					byte[] b = new byte[2048];
					for (int len = in.read(b); len > 0;) {
						out.write(b, 0, len);
						len = in.read(b);
					}
				} finally {
					if (out != null) out.close();
					if (in != null) in.close();
				}
			}
		}

		return ret;
	}

	/**
	 * aSourceを aDestに移動します。
	 *
	 * @param aSource 移動元フォルダ、ファイル
	 * @param aDest 移動先フォルダ
	 * @param aIsReplace 上書きフラグ true:上書き false:上書きしない
	 * @return 成功:true 失敗:false
	 */
	public static boolean move(File aSource, File aDest, boolean aIsReplace) throws IOException {
		return move(aSource, aDest, aIsReplace, null);
	}

	/**
	 * aSourceを aDestに移動します。
	 *
	 * @param aSource 移動元フォルダ、ファイル
	 * @param aDest 移動先フォルダ
	 * @param aIsReplace 上書きフラグ true:上書き false:上書きしない
	 * @param aFilter 移動対象を絞るFileFilter（sourceがディレクトリの場合のみ）
	 * @return 成功:true 失敗:false
	 */
	public static boolean move(File aSource, File aDest, boolean aIsReplace, FileFilter aFilter) throws IOException {
		if (aSource == null || aDest == null) return false;
		boolean ret = true;

		// 移動元、移動先が同ディレクトリもしくは移動先が移動元に含まれる場合には、
		// エラーとする。
		if (aDest.getAbsolutePath().startsWith(aSource.getAbsolutePath())) {
			ret = false;
		}

		if (ret) {
			ret = copy(aSource, aDest, aIsReplace, aFilter);
		}

		if (ret) {
			deleteAll(aSource, aFilter);
		}
		return ret;
	}

	/**
	* 文字列filenameから拡張子を取得して返します。拡張子が無い場合は
	* 空文字列を返します。
	*/
	public static String getExt(String filename) {
		if(filename == null) return null;
		String ext = "";
		int pos = filename.lastIndexOf(".");
		if (pos > 0) {
			ext = filename.substring(pos + 1, filename.length());
		}
		return ext;
	}

	/**
	* 文字列filenameから拡張子をはずした名前を取得して返します。
	*/
	public static String deleteExt(String filename) {
		if(filename == null) return null;
		String ret = filename;
		int pos = filename.lastIndexOf(".");
		if (pos > 0) {
			ret = filename.substring(0, pos);
		}
		return ret;
	}

	  /**
	  * Directoryを生成する。
	  *
	  * @param fileName Directory名
	  */
	  public static void createDirectory(String dir) throws Exception{

	    File f = new File(dir);

	    if (!f.exists()) {
	      if (!f.mkdirs()) {
	        throw new Exception("フォルダの作成に失敗しました。");
	      }
	    }
	  }


	  /**
	  * Fileを作成する。
	  *
	  * @param stream ファイルInputStream
	  * @param fileName 作成するファイル名
	  */
	  public static void createFile(InputStream stream, String fileName) throws Exception {

	    try {
	    //Directoryを生成する。
	    int index = fileName.lastIndexOf("/");
	    if(0 < index){
	      String Directry = fileName.substring(0, index);
	      createDirectory(Directry);
	    }

	     //既存ファイルを削除する。
	     deleteFile(fileName);

	     //ファイルを保存する。
	     OutputStream bos = new FileOutputStream(fileName);
	     int bytesRead = 0;
	     byte[] buffer = new byte[8192];
	     while ((bytesRead = stream.read(buffer, 0, 8192)) != -1) {
	      bos.write(buffer, 0, bytesRead);
	     }
	     bos.close();
	     stream.close();
	    } catch (IOException e) {
	     throw new Exception("ファイルの作成に失敗しました。", e);
	    }
	  }


	  /**
	  * Fileを作成する。
	  *
	  * @param fileName ファイル名
	  * @param msg ファイル内容
	  */
	  public static void createFile(String fileName, String msg) throws Exception {
	    try {
	      //Directory生成
	      int index = fileName.lastIndexOf("/");
	      if(0 < index){
	        String Directry = fileName.substring(0, index);
	        createDirectory(Directry);
	      }

	      FileOutputStream fos = new FileOutputStream(fileName);
	      OutputStreamWriter osw = new OutputStreamWriter(fos);
	      BufferedWriter bw = new BufferedWriter(osw);
	      bw.write(msg);
	      bw.close();
	      osw.close();
	      fos.close();
	    } catch (IOException e) {
	     throw new Exception("ファイルの作成に失敗しました。", e);
	    }
	  }


	  /**
	  * Fileを削除する。
	  *
	  * @param fileName ファイル名
	  */
	  public static void deleteFile(String fileName) {

	    File f = new File(fileName);

	    if (f.exists()) {
	      f.delete();
	    }
	  }

	  /**
	   * フォルダを削除する
	   * @param folderName String
	   * @return int  フォルダが空でない場合は1を返す。
	   */
	  public static int deleteFolder(String folderName) throws Exception {

	    String[] ls_FileList = new File(folderName).list(); // 内部ファイルが配列で落ちる。

	    try{
	      if(ls_FileList == null){
	        throw new Exception("フォルダの削除に失敗しました。");
	      }

	      if(ls_FileList.length <= 0){ // 空なら
	        deleteFile(folderName);
	        return 0;
	      }
	      else{
	        return 1;
	      }
	    }
	    catch (Exception e) {
	      throw new Exception("フォルダの削除に失敗しました。", e);
	    }
	  }

	  /**
	   * ファイルコピー
	   * @param inName String
	   * @param outName String
	   * @return int
	   */
	  public static void copyFile(String inName, String outName) throws
	  Exception {

	    try{
	      BufferedInputStream is = new BufferedInputStream(new FileInputStream(inName));
	      BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outName));

	      int b;    // the byte read from the file
	      while ((b = is.read()) != -1) {
	        os.write(b);
	      }
	      is.close();
	      os.close();

	    } catch (IOException ioe) {
	      System.out.println(ioe.getMessage());
	      throw new Exception("ファイルのコピーに失敗しました。", ioe);
	    }
	  }

	  /**
	   * ファイル移動
	   * @param FromFileName String
	   * @param ToFileName String
	   * @return int
	   */
	  public static void moveFile(String fromFileName, String toFileName) throws
	      Exception {
	    int pos;
	    String fromRoot;
	    String toRoot;
	    boolean copyDeleteFlg = false;

	    if(fromFileName.equals(toFileName))
	      return;

	    //  UNIXファイルシステム対応
	    pos = fromFileName.indexOf("/", 1);
	    if(pos < 0) {
	      fromRoot = "/";
	    }
	    else {
	      fromRoot = fromFileName.substring(0, pos);
	    }
	    pos = toFileName.indexOf("/", 1);
	    if(pos < 0) {
	      toRoot = "/";
	    }
	    else {
	      toRoot = toFileName.substring(0, pos);
	    }
	    if(!fromRoot.equals(toRoot)) {
	      //  ＤＩＳＫデバイス（システムルートフォルダ）取得
	      File[] roots = File.listRoots();
	      for(int i=0; i < roots.length; i++) {
	        //  デバイスが違う場合移動コマンドが失敗するのでコピー→削除にする。
	        if(roots[i].toString().equals(fromRoot) || roots[i].toString().equals(toRoot)){
	          copyDeleteFlg = true;
	        }
	      }
	    }

	    if(copyDeleteFlg) {
	      try {
	        copyFile(fromFileName, toFileName);
	      }
	      catch (Exception ex) {
	        throw new Exception("ファイルの移動に失敗しました。", ex);
	      }
	      deleteFile(fromFileName);
	    }
	    else {
	      try{
	        File ff = new File(fromFileName);
	        File tf = new File(toFileName);

	        // 同じファイルシステム内でしかファイルのrename(移動)が出来ない。
	        if(!ff.renameTo(tf)){
	          throw new Exception("ファイルの移動に失敗しました。");
	        }
	      }
	      catch (Exception e) {
	        System.out.println(e.getMessage());
	          throw new Exception("ファイルの移動に失敗しました。", e);
	      }
	    }
	  }

	  /**
	   * ファイル一覧の取得
	   * @param DirectoryName String
	   * @param Filter String
	   * @return FileList
	   */
	  public static List<String> getFileList(String directoryName, String filter) throws
	      Exception {

	        File fp = new File(directoryName);
	        String[] fileList = fp.list();
	        List<String> rFileList = new ArrayList<String>();

	        for( int i = 0 ; i < fileList.length ; i ++ ){
	          if(fileList[i].indexOf(filter) >= 0){
	            rFileList.add(fileList[i]);
	          }
	        }

	        return rFileList;
	  }

	  /**
	   * ZIPファイルを解凍し、解凍後のファイル名を返す。
	   * @param fileName String
	   * @return retFileName String
	   */
	  public static String unGzipFile(String fileName) throws
	      Exception, Exception {

	    String retFileName = null;
	    InputStream in = null;
	    OutputStream out = null;

	    int len = fileName.lastIndexOf(".gz");
	    if (len > 0 && len == fileName.length() - 3) {
	      in = new GZIPInputStream(new FileInputStream(fileName));
	      retFileName = fileName.substring(0,len);
	      out = new BufferedOutputStream(new FileOutputStream(retFileName));
	      byte[] buf = new byte[4096];
	      int i;
	      while( (i = in.read(buf)) != -1 ){
	          out.write(buf, 0, i);
	      }
	      in.close();
	      out.close();
	    }
	    return retFileName;
	  }

	  /**
	   * tarファイルをtarファイルと同一ディレクトリに解凍する。
	   * @param fileName String
	   * @return retFileName String
	   */
	  public static List<String> tarX(String filePath, String fileName) throws Exception {

	    List<String> rFileList = new ArrayList<String>();

	/*
	    TarInputStream tin = new TarInputStream(new FileInputStream(fileName));
	    TarEntry tarEnt = tin.getNextEntry();
	    while (tarEnt != null) {
	      //名前を取得
	      rFileList.add(tarEnt.getName());
	      //サイズを取得
	      int size = (int)tarEnt.getSize();
	      ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
	      tin.copyEntryContents(bos);
	      byte[] data = bos.toByteArray();
	      DataInputStream dis = new DataInputStream(new ByteArrayInputStream(data));
	      //データ入力ストリームが取得できたら、通常の方法で読むだけです。
	      dis.close();
	      tarEnt = tin.getNextEntry();
	    }
	    tin.close();
	*/

	    OutputStream out = null;

	    TarInputStream tin = new TarInputStream(new FileInputStream(fileName));
	    TarEntry tarEnt = null;
	    while ((tarEnt = tin.getNextEntry()) != null) {
	      //名前を取得
	      String name = tarEnt.getName();
	      rFileList.add(name);
	      String newName = filePath + "/" + name;

	      //サイズを取得
	      int size = (int)tarEnt.getSize();
	      out = new BufferedOutputStream(new FileOutputStream(newName));
	      byte[] buf = new byte[size];
	      int i;
	      while ((i = tin.read(buf)) != -1) {
	        out.write(buf, 0, i);
	      }
	    }
	    tin.close();
	    out.close();

	    return rFileList;
	  }

	public static List<File> sortTimestamp(final List<File> files) {

	    if ((files == null) || (files.size() == 0)) {
	        return null;
	    }

	    // タイムスタンプ＋ファイル名のリストを生成する
	    List<String> fileList = new ArrayList<String>();
	    for ( int i = 0 ; i < files.size() ; i++ ) {
	    	File file = files.get(i);
	        fileList.add(String.valueOf(1000000000000000000L + file.lastModified()) + "_" + file.getAbsolutePath());
	    }

	    // タイムスタンプ＋ファイル名のリストをソートする（文字列比較で古いファイルから昇順に並ぶ）
	    Collections.sort(fileList);

	    // ソートされたリストからファイルの配列を生成する
	    List<File> retFiles = new ArrayList<File>();
	    for ( int i = 0 ; i < fileList.size() ; i++ ) {
	        retFiles.add(new File(((String)fileList.get(i)).substring(20)));
	    }
	    return retFiles;
	}
}