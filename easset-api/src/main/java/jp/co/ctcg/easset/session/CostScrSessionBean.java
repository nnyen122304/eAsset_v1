/*===================================================================
 * ファイル名 : CostScrSessionBean.java
 * 概要説明   : 経費負担部課精査セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2015-01-28 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.sql.SQLException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import org.apache.commons.lang3.StringUtils;

import jp.co.ctcg.easset.dao.CostScrDAO;
import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.RoleSection;
import jp.co.ctcg.easset.dto.Section;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.costScr.CostScr;
import jp.co.ctcg.easset.dto.costScr.CostScrLine;
import jp.co.ctcg.easset.dto.costScr.CostScrStat;
import jp.co.ctcg.easset.dto.costScr.CostScrSC;
import jp.co.ctcg.easset.dto.costScr.CostScrSR;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.mdb.CreateCostScrDataMDBean;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvReaderRowHandler;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;

@Stateless
public class CostScrSessionBean implements CostScrSession {

	private static final int ERROR_CODE_DUP_EXEC = 20001; // 重複実行時エラーコード
	private static final int ERROR_CODE_NOWAIT = 54; // 重複実行時エラーコード(FOR UPDATE NOWAIT)
	private static final String COST_SCR_CREATE_APR_MONTH = "04"; // 経費負担部課精査データ作成可能月

	@Resource(mappedName = "java:/jms/queue/CreateCostScrDataQueue")
	private Queue createCostScrDataQueue; // データ作成処理実行用キュー

	@Resource(mappedName = "java:/jms/CreateCostScrDataQueueFactory" )
	private ConnectionFactory createCostScrDataQueueFactory; // データ作成処理実行用キュー接続ファクトリ

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession; // マスタセッション

	@EJB
	SendMailSession sendMailSession;

	@EJB
	HistSession histSession; // 履歴セッション

	// メールテンプレート変数
	private static final String MAIL_TEMPLATE_VAR_TO_SCR_SECTION_NAME = "\\$\\{精査担当部署\\}";

	/*
	 *
	 */
	public List<CostScrStat> searchCostScrStatList(String loginStaffCode, String accessLevel, String companyCode) {
		try {
			CostScrDAO costScrDao = new CostScrDAO();
			return costScrDao.selectCostScrStatList(loginStaffCode, accessLevel, companyCode, false);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課精査状況取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#publicCostScrData(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public void publicCostScrData(String loginStaffCode, String accessLevel, String companyCode, String period, String publicCode, boolean isSendMail) throws AppException {
		try {
			CostScrDAO costScrDao = new CostScrDAO();		// 公開情報取得
			CostScrStat obj = costScrDao.selectCostScrStat(loginStaffCode, accessLevel, companyCode, period, false);
			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer();

			if(obj != null){
				if(Function.nvl(obj.getCreateStatus(), "").equals(Constants.COST_SCR_CREATE_STATUS_ERROR)){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "データ作成ステータス", "該当年月のデータ作成処理が正常終了していないため処理を継続できません。"));
				}else if(Function.nvl(obj.getCreateStatus(), "").equals(Constants.COST_SCR_CREATE_STATUS_PROCESSING)){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "データ作成ステータス", "該当年月のデータ作成処理が正常終了していないため処理を継続できません。"));
				}
			}

			if(errMsg.length() > 0) throw new AppException(errMsg.toString());
			// 各部メニュー公開状況取得
			List<CodeName> codeNameList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_COST_SCR_CONTROL, null, companyCode, null);

			// 精査担当部署一覧取得
			// 精査担当部署一覧検索条件設定
			CostScrSC searchParam = new CostScrSC();
			searchParam.setCompanyCode(companyCode);
			searchParam.setPeriod(period);
			List<CostScrSR> list = costScrDao.selectCostScrList(loginStaffCode, accessLevel, companyCode, searchParam);
			HashMap<String, String> codeNameMap = new HashMap<String, String>();
			if(codeNameList != null && codeNameList.size() > 0){
				for(Iterator<CodeName> iter = codeNameList.iterator(); iter.hasNext();){
					CodeName codeName = iter.next();
					// リース・レンタル？
					if(codeName.getValue1().indexOf(Constants.SCR_TYPE_LE) > -1 && codeName.getValue1().indexOf(Constants.SCR_TYPE_RE) > -1 ){
						codeNameMap.put(Constants.SCR_TYPE_LE, codeName.getValue4());
						codeNameMap.put(Constants.SCR_TYPE_RE, codeName.getValue4());
					}else{
						codeNameMap.put(codeName.getValue1(), codeName.getValue4());
					}
				}
			}
			if(list != null && list.size() > 0){
				for(Iterator<CostScrSR> iter = list.iterator(); iter.hasNext();){
					CostScrSR costScrSR = iter.next();
					// 公開対象精査種別？
					if(Function.nvl(codeNameMap.get(costScrSR.getScrType()), "").equals(Constants.FLAG_YES)){
						// 精査担当部署設定なし？
						if(Function.nvl(costScrSR.getScrSectionCode(), "").equals("")){
							// UATユーザー要望にてチェックを除外
//							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "各部精査を行う精査種別にて、精査担当部署が設定されていない経費負担部課精査データが存在するため処理を継続できません。\n各部メニューをOPENする前に、精査担当部署設定を行ってください。"));
							break;
						}
					}
				}
			}

			if(errMsg.length() > 0) throw new AppException(errMsg.toString());


			if(obj != null){
				//	精査担当部署依頼メール送信？
				if(isSendMail){
					// 依頼メール送信者設定
					obj.setSendStaffCode(loginStaffCode);
					// メール送信日設定
					obj.setSendStartDate(new Date());
					boolean errorMail = false;
					try{
						sendCostScrMail(loginStaffCode, accessLevel, list, false);
					}catch(Exception e){
						// 異常終了
						obj.setSendStatus(Constants.COST_SCR_SEND_MAIL_STATUS_SEND_ERROR);
						Logging.error(e.getMessage(), e);
						errorMail = true;
					}
					// メール送信異常終了していない？
					if(!errorMail){
						obj.setSendStatus(Constants.COST_SCR_SEND_MAIL_STATUS_SEND);
						// メール送信終了日設定
						obj.setSendEndDate(new Date());
					}
				}
				Date sysdate = new Date();

				// 経費負担部課状況更新
				// OPEN?
				if(publicCode.equals(Constants.FLAG_YES)){
					obj.setOpenDate(sysdate);
					obj.setOpenStaffCode(loginStaffCode);
				// CLOSE
				}else{
					obj.setCloseDate(sysdate);
					obj.setCloseStaffCode(loginStaffCode);
				}
				// 更新日、更新者設定
				obj.setUpdateDate(sysdate);
				obj.setUpdateStaffCode(loginStaffCode);

				costScrDao.updateCostScrStat(obj);
			}

			// メニュー表示/非表示更新
			codeNameList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_MENU_ACCESS_CONTROL, null, companyCode, null);
			if(codeNameList != null && codeNameList.size() > 0){
				masterSession.updateAccessControl(loginStaffCode, companyCode, codeNameList.get(0).getValue3(), publicCode);
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課精査状況取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#searchCostScrSum(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.costScr.CostScrSumSC)
	 */
	public List<CostScrSR> searchCostScr(String loginStaffCode, String accessLevel, String companyCode, CostScrSC searchParam) {
		try {
			CostScrDAO costScrDao = new CostScrDAO();
			return costScrDao.selectCostScrList(loginStaffCode, accessLevel, companyCode, searchParam);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#updateCostScr(java.lang.String, java.lang.String, java.util.List, java.lang.String)
	 */
	public void updateCostScr(String loginStaffCode, String accessLevel, List<CostScrSR> list, String compFlag) {
		try {
			CostScrDAO costScrDao = new CostScrDAO();
			if(list != null && list.size() > 0){
				Date sysdate = new Date(); // システム日付取得
				for(Iterator<CostScrSR> iter = list.iterator(); iter.hasNext();){
					CostScrSR obj = iter.next();
					////////////////////////////////////固定値セット
					//	更新日・更新者
					obj.setUpdateDate(sysdate);
					obj.setUpdateStaffCode(loginStaffCode);
					// 精査完了フラグ
					obj.setCompFlag(compFlag);
					// 精査完了(取消)日、精査完了(取消)者
					obj.setCompExecDate(sysdate);
					obj.setCompExecStaffCode(loginStaffCode);

					costScrDao.updateCostScr(obj, false);
				}
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課精査状況更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#sarchCostScrLine(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.costScr.CostScrSumSC)
	 */
	public List<CostScrLine> searchCostScrLine(String loginStaffCode, String accessLevel, String companyCode, CostScrSR searchParam) {
		try {
			CostScrDAO costScrDao = new CostScrDAO();
			return costScrDao.selectCostScrLine(loginStaffCode, accessLevel, companyCode, searchParam);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課精査明細検索"), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#createInvSumCsv(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.costScr.CostScrSumSC)
	 */
	public String createCostScrCsv(String loginStaffCode, String accessLevel, String companyCode, CostScrSC searchParam){
		try {
			CostScrDAO costScrDao = new CostScrDAO();
			CsvWriterRowHandler handler = costScrDao.createCostScrCsv(loginStaffCode, accessLevel, companyCode, searchParam);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_COST_SCR_SUM, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam));

			return handler.getFileId();

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課精査集約ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課精査集約ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#createCostScrLineListCsv(java.lang.String, java.lang.String, java.util.List)
	 */
	public String createCostScrLineListCsv(String loginStaffCode, String accessLevel, List<CostScrSR> list){
		try {
			CostScrDAO costScrDao = new CostScrDAO();
			CsvWriterRowHandler handler = null;
			handler = costScrDao.createCostScrLineListCsv(loginStaffCode, accessLevel, list);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_COST_SCR_LINE, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + list.toString());

			return handler.getFileId();

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "明細ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "明細ダウンロード"), e);
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#updateCostScrLine(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void updateCostScrLine(String loginStaffCode, String accessLevel, String companyCode, CostScrSR costScrSR, List<CostScrLine> list) throws AppException {
		try {
			CostScrDAO costScrDao = new CostScrDAO();

			// StringBuffer errMsg = new StringBuffer();

			CostScrSC searchParam = new CostScrSC();
			searchParam.setCompanyCode(companyCode);
			searchParam.setCostSectionCode(costScrSR.getCostSectionCode());
			searchParam.setCostSectionCodeOld(costScrSR.getCostSectionCodeOld());
			searchParam.setScrType(costScrSR.getScrType());
			searchParam.setPeriod(costScrSR.getPeriod());
			List<CostScrSR> costScrSrOldList = costScrDao.selectCostScrList(loginStaffCode, accessLevel, companyCode, searchParam);
			if(costScrSrOldList == null){
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG, "経費負担部課精査データが最新ではありません。"));
			}

			if(list != null && list.size() > 0){
				Date sysdate = new Date(); // システム日付取得
				for(Iterator<CostScrLine> iter = list.iterator(); iter.hasNext();){
					CostScrLine obj = iter.next();
					////////////////////////////////////固定値セット
					//	更新日・更新者
					obj.setUpdateDate(sysdate);
					obj.setUpdateStaffCode(loginStaffCode);
					// 精査実施日、精査実施者
					obj.setScrDate(sysdate);
					obj.setScrStaffCode(loginStaffCode);

					costScrDao.updateCostScrLine(obj);
				}
				// 経費負担部課精査実施状況更新
				// 精査実施日、精査実施者
				costScrSR.setScrDate(sysdate);
				costScrSR.setScrStaffCode(loginStaffCode);
				// 更新日、更新者
				costScrSR.setUpdateDate(sysdate);
				costScrSR.setUpdateStaffCode(loginStaffCode);
				costScrDao.updateCostScr(costScrSR, false);
			}
		} catch (UnsupportedEncodingException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課明細データ更新"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課明細データ更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#createCostScrLineCsv(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.costScr.CostScrSumSR)
	 */
	public String createCostScrLineCsv(String loginStaffCode, String accessLevel, String CompanyCode, CostScrSR searchParam) {
		try {
			CostScrDAO costScrDao = new CostScrDAO();

			CsvWriterRowHandler handler = costScrDao.createCostScrLineCsv(loginStaffCode, accessLevel, CompanyCode, searchParam);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_COST_SCR_LINE, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel+ "," + Function.toString(searchParam));

			return handler.getFileId();

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "エクスポート"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "エクスポート"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#getScrLineByCsv(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.costScr.CostScrSumSR)
	 */
	public List<CostScrLine> getScrLineByCsv(String loginStaffCode, String accessLevel, String fileId, CostScrSR obj) throws AppException {
		StringBuffer headerRow = new StringBuffer();
		List<String> inputPropsList = new ArrayList<String>();
		List<Format> inputFormatsList = new ArrayList<Format>();
		String categoryCode = "";
		int headerRowCt = 6;

		CsvReaderRowHandler handler = null;
		try {
			List<CostScrLine> list =  new ArrayList<CostScrLine>();
			// 有形固定資産
			if(obj.getScrType().equals(Constants.SCR_TYPE_FLD_TAN)){
				categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_COST_SCR_LINE_FLD_TAN;
			}
			//	無形固定資産
			else if(obj.getScrType().equals(Constants.SCR_TYPE_FLD_INT)){
				categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_COST_SCR_LINE_FLD_INT;
			}
			// リース・レンタル
			else if(obj.getScrType().equals(Constants.SCR_TYPE_LE) || obj.getScrType().equals(Constants.SCR_TYPE_RE)){
				categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_COST_SCR_LINE_LE_RE;
			}

			new MasterDAO().setCsvDef(categoryCode, accessLevel, headerRow, inputPropsList, inputFormatsList);
			String[] strArray = {"scrStatusName", "scrComment", "astNum"}; // 取込対象項目指定
			Format[] formatArray = {null, null, null}; // 取込対象項目フォーマット指定
			handler = new CsvReaderRowHandler(fileId, headerRowCt, CostScrLine.class, strArray, formatArray);
			CostScrLine row = null;
			StringBuffer errorMessage = new StringBuffer(); // 全行エラーメッセージ
			int rowCt = headerRowCt;

			do {
				// 行データ取得
				row = (CostScrLine) handler.handleRow();
				if(row == null) break; // 行データが取得できない場合は終了

				rowCt++;
				String rowNumStr = "[" + rowCt + "行目] ";

				StringBuffer rowErrorMessage = new StringBuffer(); // 一行エラーメッセージ

				////////////////////////////////////////// 読み込みエラー判定
				if(handler.getRowStatus() == CsvReaderRowHandler.ROW_STATUS_ERROR) { // CSV読み込み時のエラー有
					rowErrorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr, handler.getRowErrorMessage()));
				}

				// OK?
				if(Function.nvl(row.getScrStatusName(), "").equals(Constants.SCR_STATUS_NAME_OK)){
					row.setScrStatus(Constants.SCR_STATUS_OK);
					row.setScrStatus1(Constants.FLAG_YES);
					row.setScrStatus2(Constants.FLAG_NO);
				}
				// NG
				else if(Function.nvl(row.getScrStatusName(), "").equals(Constants.SCR_STATUS_NAME_NG)){
					row.setScrStatus(Constants.SCR_STATUS_NG);
					row.setScrStatus1(Constants.FLAG_NO);
					row.setScrStatus2(Constants.FLAG_YES);
				// 「OK」「NG」以外の文字入力　※空白は対象外
				}else if(!Function.nvl(row.getScrStatusName(), "").equals("")){
					rowErrorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "「精査結果」に想定外の文字が入力されているため処理を中断しました。\n精査結果に入力できる文字は、以下2パターンですので、訂正して再度インポートしてください。\n\n・OK (半角大文字)\n・要対応\n"));
				}
				else{
					row.setScrStatus(Constants.SCR_STATUS_UNDECIDED);
					row.setScrStatus1(Constants.FLAG_NO);
					row.setScrStatus2(Constants.FLAG_NO);
				}

				////////////////////////////////////////// 行登録
				list.add(row);

				if(rowErrorMessage.length() > 0) { // 対象行でエラー発生
					errorMessage.append(rowErrorMessage.toString());
					break;
				}

			}while(true);

			if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

			return list;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "インポート"), e);
		} finally {
			if(handler != null) handler.close();
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#callCreateCostScrData(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public void callCreateCostScrData(String companyCode, String period, String execStaffCode, Boolean overwriteFlag) throws AppException {
		CostScrDAO costScrDao = new CostScrDAO();

		try {
			// 4月ではない？
			if(!period.substring(4, 6).equals(COST_SCR_CREATE_APR_MONTH)){
				throw new AppException("会計月が" + COST_SCR_CREATE_APR_MONTH + "月以外の場合、経費負担部課精査データを作成することができません。\n");
			}
			if(!overwriteFlag.booleanValue()) {
				List<CostScrStat> statList = costScrDao.selectCostScrStatList(execStaffCode, null, companyCode, false);
				for(int i = 0; i < statList.size(); i++) {
					CostScrStat stat = statList.get(i);
					if(stat.getPeriod().equals(period) && stat.getLastSuccessCreateStartDate() != null) { // 処理対象年月
						throw new AppException(period.substring(0, 4) + "/" + period.substring(4, 6) + "の経費負担部課精査データは既に作成されています。\n「作成済の場合は再作成する」にチェックをつけて処理を実行してください。");
					}
				}
			}

			// 各部公開？
			List<CodeName> list = masterSession.getCodeNameList(Constants.CATEGORY_CODE_MENU_ACCESS_CONTROL, null, companyCode, null);
			if(list != null && list.size() > 0){
				CodeName codeName = list.get(0);
				// 各部メニュー公開？
				if(Function.nvl(codeName.getValue2(), "").equals(Constants.FLAG_YES)){
					throw new AppException("各部メニューをOPENしているため、経費負担部課精査データを作成することができません。\nデータを再作成するには各部メニューをCLOSEしてください。\n\n※　データを再作成すると精査実施内容はクリアされます。");
				}
			}

			costScrDao.callUpdateCostScrStatus(companyCode, period, execStaffCode, Constants.COST_SCR_CREATE_STATUS_PROCESSING);
		} catch (SQLException e) {
			if(e.getErrorCode() == ERROR_CODE_DUP_EXEC || e.getErrorCode() == ERROR_CODE_NOWAIT) {
				throw new AppException(period.substring(0, 4) + "/" + period.substring(4, 6) + "の経費負担部課精査データ作成処理は既に実行中です。");
			} else {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取込ステータス更新"), e);
			}
		}
		//////////////////////////////////// メッセージ送信
		// メッセージパラメータ作成
		HashMap<String, Object> param = new HashMap<String, Object>();

		param.put("functionName", CreateCostScrDataMDBean.FUNCTION_CREATE);
		param.put("companyCode", companyCode);
		param.put("period", period);
		param.put("execStaffCode", execStaffCode);
		param.put("overwriteFlag", overwriteFlag);

		Function.sendJmsMessage(createCostScrDataQueueFactory, createCostScrDataQueue, param);

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#createCostScrData(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public void createCostScrData(String companyCode, String period, String execStaffCode, Boolean overwriteFlag) {
		CostScrDAO costScrDao = new CostScrDAO();
		try {
			costScrDao.callCreateCostScrData(companyCode, period, execStaffCode);
			costScrDao.callUpdateCostScrStatus(companyCode, period, execStaffCode, Constants.COST_SCR_CREATE_STATUS_SUCCESS);
		} catch (Exception e) {
			handleDataCreateError(companyCode, period, execStaffCode, e);
		}
	}

	/**
	 * 経費負担部課精査データ作成処理のエラーハンドリング
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @param execStaffCode 実行者社員番号
	 * @param e
	 */
	private void handleDataCreateError(String companyCode, String period, String execStaffCode, Exception e) {
		CostScrDAO costScrDao = new CostScrDAO();
		e.printStackTrace();
		Logging.error("経費負担部課精査データ作成実行処理に失敗しました。", e);
		try {
			costScrDao.callUpdateCostScrStatus(companyCode, period, execStaffCode, Constants.COST_SCR_CREATE_STATUS_ERROR);
		} catch (SQLException e2) {
			e.printStackTrace();
			Logging.error("経費負担部課精査データ異常終了更新処理に失敗しました。", e);
		}
	}

	/**
	 * 経費負担部課精査データ依頼・督促メール送信
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param list メール送信対象集約リスト
	 * @param isRemind true:督促メール、false:公開メール
	 * @throws AppException
	 */
	private void sendCostScrMail(String loginStaffCode, String accessLevel, List<CostScrSR> list, boolean isRemind) throws SQLException, AppException{
		//////////////////////////////////// 督促メール送信&データ更新
		User loginStaff = masterSession.getStaff(null, loginStaffCode); // 処理実行者

		StringBuffer reportMailBody = new StringBuffer(); // 処理結果用メール本文
		StringBuffer reportMailBodyError = new StringBuffer(); // 例外発生時のエラー文字列

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");

		// 可変メール送信属性
		List<String> toList = new ArrayList<String>();
		List<String> ccList = new ArrayList<String>();
		String subject = null; // 件名
		String body = null; // 本文
		String retiredUserList = ""; // 退職者一覧
		boolean mailSuccess = true; // メール送信成功フラグ
		HashMap<String, String> sendMap = new HashMap<String, String>();
		Date sysdate = new Date();
		CostScrDAO costScrDao = new CostScrDAO();
		MasterDAO masterDao = new MasterDAO();

		// メールテンプレート取得
		String templateCategory;
		if(isRemind) {
			templateCategory = Constants.CATEGORY_CODE_AP_REMIND_COST_SCR;
		} else {
			templateCategory = Constants.CATEGORY_CODE_AP_REQUEST_COST_SCR;
		}
		CodeName mailTemplate = masterSession.getCodeName(templateCategory, null, list.get(0).getCompanyCode(), null);
		String from = Function.nvl(mailTemplate.getValue1(), "");	//	メール用固定アドレス取得
		String bcc = Function.nvl(mailTemplate.getValue2(), "");	//	メール用固定アドレス取得
		List<String> bccList = new ArrayList<String>();
		bccList.add(bcc);
		String returnTo = Function.nvl(mailTemplate.getValue6(), "");	//	メール用固定アドレス取得

		List<CodeName> codeNameList = masterDao.selectCodeNameList(Constants.CATEGORY_CODE_COST_SCR_CONTROL, null, list.get(0).getCompanyCode(), null, null, true);
		HashMap<String, String> codeNameMap = new HashMap<String, String>();
		if(codeNameList != null && codeNameList.size() > 0){
			for(int i = 0; i < codeNameList.size(); i++){
				CodeName codeName = codeNameList.get(i);
				// リース・レンタル？
				if(codeName.getValue1().equals(Constants.SCR_TYPE_LE + " " + Constants.SCR_TYPE_RE)){
					codeNameMap.put(Constants.SCR_TYPE_LE, codeName.getValue4()); // 精査種別、各部公開状況を格納
					codeNameMap.put(Constants.SCR_TYPE_RE, codeName.getValue4()); // 精査種別、各部公開状況を格納
				}else{
					codeNameMap.put(codeName.getValue1(), codeName.getValue4()); // 精査種別、各部公開状況を格納
				}
			}
		}

		////////////	精査部署別メール送信
		int sectionCount = 0;
		int mailSuccessCount = 0;
		for(int i = 0; i < list.size(); i++){
			CostScrSR obj = list.get(i);
			// 各部データ公開部署チェック
			if(codeNameMap.containsKey(obj.getScrType())){
				String publicCode = codeNameMap.get(obj.getScrType());
				// 各部公開しない精査種別？
				if(publicCode.equals(Constants.FLAG_NO)){
					// 督促？
					if(isRemind){
						throw new AppException("各部にデータを公開していない精査種別が選択されているため、処理を中断しました。");
					// 依頼メール？
					}else{
						continue; // 以降の処理は行わない
					}
				}
			}
			//　メール未送信部署チェック
			if(!sendMap.containsKey(obj.getScrSectionCode())){
				try {
					subject = mailTemplate.getValue3();
					body = Function.nvl(mailTemplate.getValue4(), "");
					body = Function.nvl(body, "") + Function.nvl(mailTemplate.getValue5(), "");
					//	精査部署の送信先宛先を取得(部署長・資産管理担当者)
					List<RoleSection> userList;

					if(!Function.nvl(obj.getScrSectionCode(),"").equals("")) { // 精査担当部署が設定されている場合資産管理担当者、上長取得
						userList = masterSession.getUserRoleSectionAllList(obj.getCompanyCode(), Function.nvl(obj.getScrSectionCode(),""), obj.getScrSectionYear());
					} else { // 精査担当部署設定がない場合は資産管理担当者、上長リストは空のリスト
						userList = new ArrayList<RoleSection>();
					}

					if(userList != null && userList.size() > 0){
						for(int j = 0; j < userList.size(); j++){
							RoleSection user = userList.get(j);
							// 宛先アドレス
							if(!Function.nvl(user.getMailAddress(), "").equals("")) {
								if(!Function.nvl(user.getDomainId(), "").equals("") //	ドメインIDない？
								&& Function.nvl(user.getRetiredDay(), "").equals("") //	退職者？
								){
									if(Function.nvl(user.getRoleCode(), "").equals(Constants.ROLE_CODE_SECTION_SUPERIOR)) {
										ccList.add(user.getMailAddress());
									} else {
										toList.add(user.getMailAddress());
									}
								}
							}
							//	退職者一覧文章作成
							if(!Function.nvl(user.getRetiredDay(), "").equals("")){
								if(!Function.nvl(retiredUserList, "").equals("")){
									retiredUserList = retiredUserList + ",";
								}
								retiredUserList = retiredUserList + user.getStaffCode() + " " + user.getStaffName();
							}
						}
					}

					// 予約語置換
					subject = subject.replaceAll(MAIL_TEMPLATE_VAR_TO_SCR_SECTION_NAME, Function.nvl(obj.getScrSectionName(), "(部署未設定)"));	//	精査担当部署
					body = body.replaceAll(MAIL_TEMPLATE_VAR_TO_SCR_SECTION_NAME, Function.nvl(obj.getScrSectionName(), "(部署未設定)"));	//	精査担当部署

					// 経費負担部課精査状況更新
					if(isRemind){
						obj.setReminderDate(sysdate); // 督促メール送信美セット
						obj.setUpdateStaffCode(loginStaffCode);
						obj.setUpdateDate(sysdate);
						costScrDao.updateCostScr(obj, false);
					}
					// TOチェック
					if(toList == null || toList.size() == 0){
						throw new AppException("メール送信先(資産管理担当者)の取得に失敗しました。");
					}
					//	メール送信
					sendMailSession.sendMail(from, toList, ccList, bccList,  subject, body);
				} catch (Exception e) {
					mailSuccess = false;
					reportMailBodyError.append("---------------------------------------------------------------");
					reportMailBodyError.append("\n");
					reportMailBodyError.append("精査担当部署：" + Function.nvl(obj.getScrSectionName(), "(部署未設定)") + "\n");
					if(e instanceof AppException) { // アプリケーションエラー（想定エラー）
						reportMailBodyError.append(e.getMessage()); // 例外トレースは表示しない
					} else {
						reportMailBodyError.append(Function.getStackTraceStr(e));
					}
					reportMailBodyError.append("\n");
					Logging.error(e.getMessage(), e);
				}
				StringBuffer sendLog = new StringBuffer(); // 送信部署ログ出力用
				//	全体処理結果用本文作成
				sectionCount++; // 処理件数
				if(!mailSuccess){
					reportMailBody.append("※失敗");
					sendLog.append("※失敗");
				}
				else{
					reportMailBody.append("成功");
					sendLog.append("成功");
					mailSuccessCount++; // 送信成功件数
				}

				reportMailBody.append("　" + Function.nvl(obj.getScrSectionName(), "(部署未設定)"));
				sendLog.append("　" + Function.nvl(obj.getScrSectionName(), "(部署未設定)"));

				// メール送信除外者
				if(!Function.nvl(retiredUserList, "").equals("")){
					reportMailBody.append("　(送信除外-" + retiredUserList + ")");
					sendLog.append("　(送信除外-" + retiredUserList + ")");
				}

				//	システムログ出力
				if(i == 0){
					Logging.info("経費負担部課精査-" + (isRemind ? "督促" : "依頼") + "メール送信結果" + dtf.format(new Date()));
					Logging.info("送信結果　精査担当部署　(メール送信除外)");
					Logging.info("---------------------------------------------------------------");
				}
				Logging.info(sendLog.toString());

				reportMailBody.append("\n");

				//	メール送信成功フラグリセット
				mailSuccess = true;

				//	件名、本文、宛先クリア
				subject = null;
				body = null;
				toList =  new ArrayList<String>();
				ccList =  new ArrayList<String>();
				retiredUserList = "";

				// メール送信精査担当部署格納
				sendMap.put(obj.getScrSectionCode(), obj.getScrSectionCode());
			}
		}

		//////////////////////////////////// 全体処理結果通知
		//	特定メーリングリストの取得
		//	全体処理結果本文作成
		StringBuffer allBody = new StringBuffer();
		//	本文ヘッダ
		allBody.append("実行者:　" + loginStaffCode + " " + loginStaff.getName() + "\n");
		allBody.append("処理件数:　" + sectionCount + " 件　(成功：　" + mailSuccessCount + " 件、失敗：　" + (sectionCount - mailSuccessCount) + " 件)" + "\n\n");
		//	本文明細
		allBody.append("送信結果　精査担当部署　(送信除外者)\n");
		allBody.append("---------------------------------------------------------------\n");
		allBody.append(reportMailBody.toString());
		if(reportMailBodyError.length() > 0) {
			allBody.append("\n以下送信失敗時のエラーメッセージ\n");
			allBody.append(reportMailBodyError);
		}
		// 管理者ML宛にメール送信
		CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_SYSTEM_ADMIN_EMAIL, null, null, null);
		// 送信結果メール送信
		if(cn != null){
			String systemAdminMl = cn.getValue1();
			sendMailSession.sendMail(systemAdminMl, returnTo, null, "経費負担部課精査-" + (isRemind ? "督促" : "依頼") + "メール送信結果" + dtf.format(new Date()), allBody.toString());
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#createScrPossibleInputMasterCsv(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public String createScrPossibleInputMasterCsv(String loginStaffCode, String accessLevel, String companyCode, List<String> propertyList) {
		try {
			CostScrDAO costScrDao = new CostScrDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = costScrDao.createScrPossibleInputMasterList(companyCode, accessLevel, propertyList);

			//////////////////////////////////// 操作ログ作成
			StringBuffer propStr = new StringBuffer();
			if(propertyList != null) {
				for(int i = 0; i < propertyList.size(); i++) {
					if(propStr.length() > 0) propStr.append(" ");
					propStr.append(propertyList.get(i));
				}
			}
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_COST_SCR_MASTER, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",outputProperty:" + propStr.toString());

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#updateCostScrSectionByCsv(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateCostScrSectionByCsv(String loginStaffCode, String accessLevel, String companyCode, String period, String fileId) throws AppException {
		StringBuffer headerRow = new StringBuffer();
		List<String> inputPropsList = new ArrayList<String>();
		List<Format> inputFormatsList = new ArrayList<Format>();
		int headerRowCt = 3;

		CsvReaderRowHandler handler = null;
		try {
			CostScrDAO costScrDao = new CostScrDAO();

			CostScrStat obj = costScrDao.selectCostScrStat(loginStaffCode, accessLevel, companyCode, period, false);
			//////////////////////////////////// バリデーション
			StringBuffer errorMessage = new StringBuffer(); // 全行エラーメッセージ

			if(obj != null){
				if(Function.nvl(obj.getCreateStatus(), "").equals(Constants.COST_SCR_CREATE_STATUS_ERROR)){
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "データ作成ステータス", "該当年月のデータ作成処理が正常終了していないため処理を継続できません。"));
				}else if(Function.nvl(obj.getCreateStatus(), "").equals(Constants.COST_SCR_CREATE_STATUS_PROCESSING)){
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "データ作成ステータス", "該当年月のデータ作成処理が正常終了していないため処理を継続できません。"));
				}
			}

			if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

			new MasterDAO().setCsvDef(Constants.CATEGORY_CODE_ITEM_DEF_COST_SCR, accessLevel, headerRow, inputPropsList, inputFormatsList);
			handler = new CsvReaderRowHandler(fileId, headerRowCt, CostScrSR.class, (String[]) inputPropsList.toArray(new String[0]), (Format[]) inputFormatsList.toArray(new Format[0]));
			CostScrSR row = null;
			int rowCt = headerRowCt;
			int errorCt = 0; // エラーカウント数
			// 年度取得
			int currentYear = Integer.valueOf(masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null).getValue1()); // カレント年度

			Date sysdate = new Date();

			do {
				// 行データ取得
				row = (CostScrSR) handler.handleRow();
				if(row == null) break; // 行データが取得できない場合は終了

				rowCt++;
				String rowNumStr = "[" + rowCt + "行目] ";

				////////////////////////////////////////// 読み込みエラー判定
				if(handler.getRowStatus() == CsvReaderRowHandler.ROW_STATUS_ERROR) { // CSV読み込み時のエラー有
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr, handler.getRowErrorMessage()));
					if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());
				}

				//////////////////////////// 不正チェック
				// 精査担当部署
				if(!Function.nvl(row.getScrSectionCode(), "").equals("")){
					// Excel 0落ち対応
					String scrSectionCode = StringUtils.leftPad(row.getScrSectionCode(), Constants.SECTION_CODE_LENGTH, "0");
					if(masterSession.getSection(companyCode, scrSectionCode, currentYear) == null) {
						if(errorMessage.length() > 0){
							errorMessage.append("$$$$$"); // エラーポップアップ画面改行文字
						}
						errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "精査担当部署" + rowNumStr ));
						errorCt ++;
					}
					else{
						row.setScrSectionCode(scrSectionCode);
						row.setScrSectionYear(currentYear);
					}
				}
				else{
					// 精査担当部署が空白
					row.setScrSectionCode(null);
					row.setScrSectionYear(null);
				}
				// 10回エラーが存在した場合処理終了
				if(errorCt >= Constants.MAX_COST_SCR_SECTION_UPDATE_ERROR_COUNT){
					break;
				}
				// 当年度：経費負担部課
				if(!Function.nvl(row.getCostSectionCode(), "").equals("")){
					// Excel 0落ち対応
					/*
					String costSectionCode = StringUtils.leftPad(row.getCostSectionCode(), Constants.COST_SECTION_CODE_LENGTH, "0");
					if(masterSession.getCostSection(companyCode, costSectionCode, null, null) == null) {
						if(errorMessage.length() > 0){
							errorMessage.append("$$$$$"); // エラーポップアップ画面改行文字
						}
						errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担部課-当年度部課" + rowNumStr ));
						errorCt ++;
					}
					else{
*/
						row.setCostSectionCode(StringUtils.leftPad(row.getCostSectionCode(), Constants.COST_SECTION_CODE_LENGTH, "0"));
//					}
				}
				else{
					if(errorMessage.length() > 0){
						errorMessage.append("$$$$$"); // エラーポップアップ画面改行文字
					}
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担部課-当年度部課" + rowNumStr ));
					errorCt ++;
				}

				// 10回エラーが存在した場合処理終了
				if(errorCt >= Constants.MAX_COST_SCR_SECTION_UPDATE_ERROR_COUNT){
					break;
				}

				// 前年度：経費負担部課
				if(!Function.nvl(row.getCostSectionCodeOld(), "").equals("")){
					if(!row.getCostSectionCodeOld().equals("-")){
						// Excel 0落ち対応
/*
						String costSectionCodeOld = StringUtils.leftPad(row.getCostSectionCodeOld(), Constants.COST_SECTION_CODE_LENGTH, "0");
						if(masterSession.getCostSection(companyCode, costSectionCodeOld, null, null) == null) {
							if(errorMessage.length() > 0){
								errorMessage.append("$$$$$"); // エラーポップアップ画面改行文字
							}
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担部課-前年度部課" + rowNumStr ));
							errorCt ++;
						}
						else{
*/
							row.setCostSectionCodeOld(StringUtils.leftPad(row.getCostSectionCodeOld(), Constants.COST_SECTION_CODE_LENGTH, "0"));
//						}
					}
				}
				else{
					if(errorMessage.length() > 0){
						errorMessage.append("$$$$$"); // エラーポップアップ画面改行文字
					}
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担部課-前年度部課" + rowNumStr ));
					errorCt ++;
				}
				// 10回エラーが存在した場合処理終了
				if(errorCt >= Constants.MAX_COST_SCR_SECTION_UPDATE_ERROR_COUNT){
					break;
				}

				// 名称⇒コード変換
				// 精査種別コード変換
				if(!Function.nvl(row.getScrTypeName(), "").equals("")){
					String scrType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_SCR_TYPE, null, row.getScrTypeName());
					if(!Function.nvl(scrType, "").equals("")){
						row.setScrType(scrType);
					}
					else{
						if(errorMessage.length() > 0){
							errorMessage.append("$$$$$"); // エラーポップアップ画面改行文字
						}
						errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "精査種別" + rowNumStr ));
						errorCt ++;
					}
				}
				else{
					if(errorMessage.length() > 0){
						errorMessage.append("$$$$$"); // エラーポップアップ画面改行文字
					}
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "精査種別" + rowNumStr ));
					errorCt ++;
				}
				// 10回エラーが存在した場合処理終了
				if(errorCt >= Constants.MAX_COST_SCR_SECTION_UPDATE_ERROR_COUNT){
					break;
				}
				// 更新条件設定
				row.setCompanyCode(companyCode);
				row.setPeriod(period);
				// 精査担当設定日、設定者
				row.setScrSectionUpdateDate(sysdate);
				row.setScrSectionUpdateStaffCode(loginStaffCode);
				//	更新日、更新者設定
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				////////////////////////////////////////// 行更新
				costScrDao.updateCostScr(row, true);


			}while(true);
			// 10回エラーが存在した場合処理終了
			if(errorCt >= Constants.MAX_COST_SCR_SECTION_UPDATE_ERROR_COUNT){
				errorMessage.append("$$$$$"); // エラーポップアップ画面改行文字
				errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "エラー件数が10件を超えたため処理を中断しました。"));
			}

			if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

			// 経費負担部課精査データ作成取得
			CostScrStat costScrStat = costScrDao.selectCostScrStat(loginStaffCode, accessLevel, companyCode, period, true);
			if(costScrStat != null){
				// 更新日、更新者設定
				costScrStat.setUpdateDate(sysdate);
				costScrStat.setUpdateStaffCode(loginStaffCode);
				// 精査担当者設定日、設定者
				costScrStat.setScrSectionUpdateStaffCode(loginStaffCode);
				costScrStat.setScrSectionUpdateDate(sysdate);
				// 経費負担部課精査データ作成更新
				costScrDao.updateCostScrStat(costScrStat);
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "精査部署設定更新"), e);
		} finally {
			if(handler != null) handler.close();
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#remindCostScr(java.lang.String, java.lang.String, java.util.List)
	 */
	public void remindCostScr(String loginStaffCode, String accessLevel, List<CostScrSR> list) throws AppException{
		try{
			sendCostScrMail(loginStaffCode, accessLevel, list, true);
		}catch(SQLException e){
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課精査-共通：作成・公開取得処理"), e);
		}catch(AppException e){
			throw new AppException(e.getMessage());
		}catch(Exception e){
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "督促メール送信処理"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#getSectionByName(java.lang.String, java.lang.String, java.lang.String, java.lang.String, )
	 */
	public Section getSectionByName(String loginStaffCode, String accessLevel, String companyCode, String sectionName) throws AppException{
		try{
			Section retSection = null;

			String inSectionName = Function.nvl(sectionName, "").trim();
			if(!inSectionName.equals("")) {
				// 年度取得
				int currentYear = Integer.valueOf(masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null).getValue1()); // カレント年度

				List<Section> sectionList = masterSession.searchSection(loginStaffCode, accessLevel, companyCode, sectionName, currentYear);

				if(sectionList != null) {
					for(int i = 0; i < sectionList.size(); i++) {
						Section sectionData = sectionList.get(i);
						if(inSectionName.equals(sectionData.getSectionName())) {
							retSection = sectionData;
							break;
						}
					}
				}
			}
			return retSection;
		}catch(Exception e){
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "人事部課検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#updateScrSection(java.lang.String, java.util.List)
	 */
	public void updateScrSection(String loginStaffCode, List<CostScrSR> list) throws AppException{
		try{
			if(list != null) {
				// 年度取得
				int currentYear = Integer.valueOf(masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null).getValue1()); // カレント年度
				Date sysdate = new Date();
				CostScrDAO costScrDao = new CostScrDAO();

				for(int i = 0; i < list.size(); i++) {
					CostScrSR obj = list.get(i);

					CostScr costScr = new CostScr();
					// 更新日、更新者設定
					costScr.setUpdateDate(sysdate);
					costScr.setUpdateStaffCode(loginStaffCode);
					// 精査担当部署
					costScr.setScrSectionCode(obj.getScrSectionCode());
					costScr.setScrSectionYear(currentYear);
					// 精査担当者設定日、設定者
					costScr.setScrSectionUpdateStaffCode(loginStaffCode);
					costScr.setScrSectionUpdateDate(sysdate);

					costScr.setPeriod(obj.getPeriod());
					costScr.setCompanyCode(obj.getCompanyCode());
					costScr.setCostSectionCode(obj.getCostSectionCode());
					costScr.setCostSectionCodeOld(obj.getCostSectionCodeOld());
					costScr.setScrType(obj.getScrType());

					// 経費負担部課精査データ履歴作成更新
					costScrDao.insertCostScrHistory(costScr);

					// 経費負担部課精査データ更新
					costScrDao.updateCostScr(costScr, true);
				}
			}
		}catch(Exception e){
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "精査担当部署更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.CostScrSession#requestCostScr(java.lang.String, java.lang.String, java.util.List)
	 */
	public void requestCostScr(String loginStaffCode, String accessLevel, List<CostScrSR> list) throws AppException{
		try{
			sendCostScrMail(loginStaffCode, accessLevel, list, false);
		}catch(SQLException e){
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課精査-共通：作成・公開取得処理"), e);
		}catch(AppException e){
			throw new AppException(e.getMessage());
		}catch(Exception e){
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "督促メール送信処理"), e);
		}
	}

}