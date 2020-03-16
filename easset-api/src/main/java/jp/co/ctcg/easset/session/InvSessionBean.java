/*===================================================================
 * ファイル名 : InvSessionBean.java
 * 概要説明   : 棚卸セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2013-03-19 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.DecimalFormat;
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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import jp.co.ctcg.easset.dao.InvDAO;
import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.RoleSection;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.asset.Asset;
import jp.co.ctcg.easset.dto.asset.AssetLineInv;
import jp.co.ctcg.easset.dto.hist.BulkUpdateHist;
import jp.co.ctcg.easset.dto.inv.InvLine;
import jp.co.ctcg.easset.dto.inv.InvStat;
import jp.co.ctcg.easset.dto.inv.InvSumSC;
import jp.co.ctcg.easset.dto.inv.InvSumSR;
import jp.co.ctcg.easset.dto.ppfs_import.PpfsStat;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.mdb.BulkUpdateMDBean;
import jp.co.ctcg.easset.mdb.CreateInvDataMDBean;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvReaderRowHandler;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;
import jp.co.ctcg.easset.util.PdfExporter;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;
import jp.co.ctcg.easset.ws.InvService;
import jp.co.ctcg.easset.ws.InvServiceProxy;

@Stateless
public class InvSessionBean implements InvSession {

	private static final int ERROR_CODE_DUP_EXEC = 20001; // 重複実行時エラーコード
	private static final int ERROR_CODE_NOWAIT = 54; // 重複実行時エラーコード(FOR UPDATE NOWAIT)
	private static final String HIST_ENTITY_NAME = "INV";
	private static final String ERROR_MSG_REQUIRED = "1行目のコメント : 必須入力です。";
	private static final String ERROR_MSG_BYTE = "1行目のコメント : 入力可能な文字数は半角で 128 文字までです。";
	private static final String ERROR_MSG_REQUIRED_REPLACE = "コメント : 棚卸状況が”無”の場合は必須入力です。";
	private static final String ERROR_MSG_BYTE_REPLACE = "コメント : 入力可能な文字数は半角で 128 文字までです。";

	private static final String HIST_OPERATION_ASSET_UPDATE = "更新(棚卸連携)";

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession; // マスタセッション

	@EJB
	SendMailSession sendMailSession;

	@EJB
	AssetSession assetSession; // 情報機器等セッション

	@EJB
	PpfsImportSession ppfsImportSession; // ProPlus取込セッション

	@EJB
	HistSession histSession; // 履歴セッション

	@EJB
	InvSession childInvSession; // 棚卸セッション（別トランザクション実行用）

	@Resource(mappedName = "java:/jms/queue/CreateInvDataQueue")
	private Queue createInvDataQueue; // データ作成処理実行用キュー

	@Resource(mappedName = "java:/jms/CreateInvDataQueueFactory" )
	private ConnectionFactory createInvDataQueueFactory; // データ作成処理実行用キュー接続ファクトリ

	@Resource(mappedName = "java:/jms/queue/UpdateAssetInvDataQueue")
	private Queue updateAssetInvDataQueue; // データ作成処理実行用キュー

	@Resource(mappedName = "java:/jms/UpdateAssetInvDataQueueFactory" )
	private ConnectionFactory updateAssetInvDataQueueFactory; // データ作成処理実行用キュー接続ファクトリ

	@Resource(mappedName = "java:/jms/queue/BulkUpdateQueue")
	private Queue bulkUpdateQueue; // 一括更新実行用キュー

	@Resource(mappedName = "java:/jms/BulkUpdateQueueFactory" )
	private ConnectionFactory bulkUpdateQueueFactory; // 一括更新実行用キュー接続ファクトリ

	// メールテンプレート変数
	private static final String MAIL_TEMPLATE_VAR_HOL_SECTION = "\\$\\{保有部署\\}";
//	private static final String MAIL_TEMPLATE_VAR_TO_STAFF_NAME = "\\$\\{宛先担当者名\\}";
	private static final String MAIL_TEMPLATE_VAR_PERIOD = "\\$\\{会計年月\\}";
	private static final String MAIL_TEMPLATE_VAR_INV_ASSET_TYPE_LIST = "\\$\\{資産区分一覧\\}";
	private static final String MAIL_TEMPLATE_VAR_INV_ASSET_TYPE_BODY = "\\$\\{資産区分本文\\}";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#getInvSumCurrentPeriodList(java.lang.String)
	 */
	public List<InvStat> searchInvStat(String companyCode) {
		try {
			InvDAO invDao = new InvDAO();
			return invDao.selectInvStatList(companyCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸データ作成取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#searchInvSum(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.inv.InvSumSC)
	 */
	public List<InvSumSR> searchInvSum (String loginStaffCode, String accessLevel, String companyCode, InvSumSC searchParam){
		try {
			InvDAO invDao = new InvDAO();
			//	会計年月は最新？
			boolean isHist = getInvHistPeriodFlag(companyCode, searchParam.getPeriod());
			return invDao.selectInvSumList(loginStaffCode, accessLevel, companyCode, searchParam, isHist, false);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸集約情報検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#searchInvSum(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.inv.InvSumSC)
	 */
	public List<InvSumSR> searchInvSum (Long eappId){
		try {
			InvDAO invDao = new InvDAO();
			//	最新の棚卸データ？
			List<InvSumSR> list = invDao.selectInvSumList(eappId, false, false);
			if(list == null || list.size() == 0){
				//	過去の棚卸データから取得
				list = invDao.selectInvSumList(eappId, true, false);
			}
			return list;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸集約情報検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#getInvHistPeriodFlag(java.lang.String, java.lang.String)
	 */
	public boolean getInvHistPeriodFlag (String companyCode, String period){
		try {
			InvDAO invDao = new InvDAO();
			boolean isHist = false;
			// 最新の棚卸年月取得
			String maxPeriod = invDao.selectCurrentInvPeriod(companyCode);
			// 最新の取込年月と検索条件の年月比較
			if(!Function.nvl(maxPeriod, "").equals(period)) {
				isHist = true;
			}
			return isHist;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "最新の棚卸会計年月取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#searchInvLine(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.inv.InvSumSC)
	 */
	public List<InvLine> searchInvLine(String loginStaffCode, String accessLevel, InvSumSR searchParam, String searchScopeType){
		try{
			InvDAO invDao = new InvDAO();
			//	会計年月は最新？
			boolean isHist = getInvHistPeriodFlag(searchParam.getCompanyCode(), searchParam.getPeriod());
			return invDao.selectInvLineList(loginStaffCode, accessLevel, searchParam, searchScopeType, isHist);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸データ検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#updateInvLine(java.lang.String, java.lang.String, java.util.List)
	 */
	public void updateInvLine(String loginStaffCode, String accessLevel, InvSumSR invSumSR, List<InvLine> list) throws AppException {
		updateInvLine(loginStaffCode, accessLevel, invSumSR, list, false, true);
  }
  
	/**
	 * 棚卸明細更新
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param invSumSR 更新対象集約データ
	 * @param list 棚卸明細一覧
	 * @param isHist 履歴フラグ
	 * @param lockFlag 更新行ロックフラグ
	 * @throws AppException
	 */
	private void updateInvLine(String loginStaffCode, String accessLevel, InvSumSR invSumSR, List<InvLine> list, boolean isHist, boolean lockFlag) throws AppException {
		try{
			InvDAO invDao = new InvDAO();

			//	他のユーザー更新チェック
			//	棚卸データ集約情報更新
			InvSumSC searchParam = new InvSumSC();
			//	集約情報検索条件セット
			searchParam.setCompanyCode(invSumSR.getCompanyCode());
			searchParam.setPeriod(invSumSR.getPeriod());
			searchParam.setHolSectionCode(invSumSR.getHolSectionCode());
			searchParam.setHolSectionYear(invSumSR.getHolSectionYear());
			searchParam.setInvAssetType(invSumSR.getInvAssetType());
			List<InvSumSR> invSumOldList = invDao.selectInvSumList(loginStaffCode, accessLevel, invSumSR.getCompanyCode(), searchParam, isHist, lockFlag);	//	現データ取得ロック
			if(invSumOldList == null || invSumOldList.size() == 0){
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG, "棚卸データが最新ではありません。"));
			}
			else{
				if(!Function.isAccessLevelGeneral(accessLevel)) { // 一般権限以外？
					//	更新日チェック
					if(invSumOldList.get(0).getUpdateDate().compareTo(invSumSR.getUpdateDate()) > 0){
						throw new AppException(MsgManager.getMessage(MsgManager.MSGID200040));
					}
				}
			}

			//////////////////////////////////// 一括更新用バリデーション
			StringBuffer errMsg = new StringBuffer();

			//////////////////////////////////// 条件付必須バリデーション
			// 明細の必須チェック
			if(list != null && list.size() > 0){
				for(int i = 0; i < list.size(); i++){
					InvLine obj = list.get(i);
					//	棚卸：無？
					if(obj.getInvStatus().equals(Constants.INV_STATUS_NOT_OWN)){
						if(Function.nvl(obj.getInvComment(), "").equals("")){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, (i +1) + "行目のコメント"));
						}
					}

					if(!Function.nvl(obj.getInvComment(), "").equals("")){
						// コメント最大サイズチェック
						if(Function.nvl(obj.getInvComment(), "").getBytes(Constants.DB_CHARSET).length > Constants.MAX_CHAR_SIZE_INV_LINE_COMMENT) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MAX_SIZE, (i +1) + "行目のコメント", new DecimalFormat().format(Constants.MAX_CHAR_SIZE_INV_LINE_COMMENT)));
						}
					}
				}
			}

			if(errMsg.length() > 0) throw new AppException(errMsg.toString());

			if(list != null && list.size() > 0){
				Date sysdate = new Date(); // システム日付取得

				for(Iterator<InvLine> iter = list.iterator(); iter.hasNext();){
					InvLine obj = iter.next();
					////////////////////////////////////固定値セット
					//	更新日・更新者
					obj.setUpdateDate(sysdate);
					obj.setUpdateStaffCode(loginStaffCode);

					invDao.updateInvLine(obj, isHist);	//	棚卸データ更新
				}
				//	棚卸集約状況更新
				updateInvSumState(loginStaffCode, accessLevel, invSumSR, isHist);
			}

		} catch (UnsupportedEncodingException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸データ更新"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸データ更新"), e);
		}
	}
	/**
	 * 棚卸集約データ棚卸実施状況更新
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param invSumSR 更新対象集約データ
	 * @throws AppException
	 */
	private void updateInvSumState(String loginStaffCode, String accessLevel, InvSumSR invSumSR, boolean isHist) {
		try{
			InvDAO invDao = new InvDAO();

			invDao.callUpdateInvSumState(
				invSumSR.getCompanyCode()
				, invSumSR.getPeriod()
				, invSumSR.getHolSectionCode()
				, invSumSR.getHolSectionYear()
				, invSumSR.getInvAssetType()
				, (Function.isAccessLevelGeneral(accessLevel) ? loginStaffCode : null) // 一般の場合のみパラメータ指定
				, loginStaffCode
				, invSumSR.getApStatus()
				, isHist
			);

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸集約データ棚卸実施状況更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#reportInvSum(java.lang.String, java.lang.String, java.util.List)
	 */
	public void reportInvSum(String loginStaffCode, String accessLevel, List<InvSumSR> list) throws AppException {

		if(list != null && list.size() > 0){
			InvSumSR item =  list.get(0);
			//	会計年月最新チェック
			if( !getInvHistPeriodFlag(item.getCompanyCode(), item.getPeriod()) ){

				//	ステータスチェック
				for(int i = 0; i < list.size(); i++){
					InvSumSR obj = list.get(i);
					if(!Function.nvl(obj.getApStatus(), "").equals(Constants.AP_STATUS_INV_NOAPPLY)
					&& !Function.nvl(obj.getApStatus(), "").equals(Constants.AP_STATUS_INV_SENDBACK)){
						// ステータスエラーは即座にスロー
						throw new AppException(Msg.getBindMessage(Msg.ERR_MSG_STAT_LIST, Constants.AP_STATUS_NAME_INV_NOAPPLY + "、" +  Constants.AP_STATUS_NAME_INV_SENDBACK));
					}
				}

				Long eappId = callEappService(loginStaffCode, item); // e申請連携
				Date sysdate = new Date(); // システム日付取得
				for(int i = 0; i<list.size(); i++){
					//////////////////////////////////// 申請
					InvSumSR obj = list.get(i);
					////////////////////////////////////固定値セット
					obj.setApStatus(Constants.AP_STATUS_INV_TAN_APPLY);	//	ステータス"申請中"セット
					//	申請者・申請日
					obj.setApStaffCode(loginStaffCode);
					obj.setApDate(sysdate);
					obj.setEappId(eappId);	// e申請IDを更新
					updateInvSum(loginStaffCode, accessLevel, obj);	//	更新
				}
			}
			else{
				throw new AppException("最新の会計年月ではないため棚卸報告できません。" );
			}
		}
	}
	/**
	 * e申請サービス呼び出し
	 * @param loginStaffCode ログイン社員番号
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(String loginStaffCode, InvSumSR obj) throws AppException {
		// e申請WebServiceエンドポイント取得
		CodeName codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_INV, null, null);
		String eappWsEndpoint = codeNameUrl.getValue1(); // e申請WebSerivceエンドポイント
		String eAssetUrl = codeNameUrl.getValue2(); // e申請インラインフレーム画面表示用のeAssetUrl
		String eappStopMessage = codeNameUrl.getValue3(); // e申請との連携停止期間中のエラーメッセージ

		//	ログインユーザー情報取得
		User user = masterSession.getStaff(obj.getCompanyCode(), loginStaffCode);

		Long eappId = null;

		if(!Function.nvl(eappWsEndpoint, "").equals("")) { // e申請WebServiceエンドポイントが空白(PG検証用)の場合は連携無し
			// e申請との連携停止期間中のエラーメッセージ
			if(!eappWsEndpoint.startsWith("http")){
				throw new AppException(eappStopMessage);
			}

			eAssetUrl += "&amp;companyCode=" + obj.getCompanyCode();
			eAssetUrl += "&amp;param2="; // e申請から書類IDが指定される

			//////////////////////////////////// e申請サービス呼び出し
			String eappIdStr = null;

			try {
				InvService service = new InvServiceProxy(eappWsEndpoint);
				eappIdStr = service.apply(
						  Constants.AP_TYPE_INV
						, user.getCompanyCode() // companyCode
						, user.getSectionCode() // apSectionCode
						, loginStaffCode // apCreateStaffCode
						, loginStaffCode // apStaffCode
						, Function.nvl(user.getTel1(), "-")// apTel
						, "\\n" + Constants.AP_TITLE_INV  // apTitle
						, "(会計年月:" + obj.getPeriod().substring(0, 4) + "-" + obj.getPeriod().substring(4, 6) + ")" // apSubTitle
						, user.getCompanyName() + Constants.AP_TITLE_INV + "(会計年月:" + obj.getPeriod().substring(0, 4) + "-" + obj.getPeriod().substring(4, 6) + ")" // apListTitle
						, eAssetUrl // eAssetUrl
				);
			} catch (SocketException e){
				try{
					//	ログ出力
					Logging.warning(e.getMessage(), e);
					//	WLSのURLを取得する。
					codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_EAPP_ID_RESTORE, null, null);
					eappWsEndpoint = codeNameUrl.getValue1(); // e申請WebSerivceエンドポイント
					EAssetService eAssetService = new EAssetServiceProxy(eappWsEndpoint);
					//	e申請書類ID取得
					Long getEAppIdService = eAssetService.getEAppId(obj.getHolSectionCode());
					//	nullの場合エラー
					if(Function.nvl(getEAppIdService, "").equals("")){
						throw new AppException("e申請との連携処理中に予期せぬエラーが発生しました。\nその申請の「書類ID」と「書類管理番号」をeAsset管理者宛へご連絡下さい。\n", e);
					}else{
						eappIdStr = String.valueOf(getEAppIdService);
					}
				} catch (Exception exception) {
					Logging.error(e.getMessage(), e);
					throw new AppException("e申請との連携処理中に予期せぬエラーが発生しました。\nその申請の「書類ID」と「書類管理番号」をeAsset管理者宛へご連絡下さい。\n", e);
				}
			} catch (Exception e) {
				Logging.error(e.getMessage(), e);
				throw new AppException("e申請連携処理中に予期せぬエラーが発生しました。\n", e);
			}
			if(Function.nvl(eappIdStr, "").equals("")) { // レスポンス無しエラー
				throw new AppException("e申請連携処理中に予期せぬエラーが発生しました。\n\n(e申請書類IDが空白です)。");
			} else if(eappIdStr.startsWith("e")) { // e申請側アプリケーションエラー
				throw new AppException("e申請連携処理中に以下のエラーが発生しました。\n" + eappIdStr.substring(1));
			} else {
				eappId = Long.valueOf(eappIdStr);
			}
		}
		return eappId;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#checkReportInvUserSection(jp.co.ctcg.easset.dto.User, java.lang.String, java.util.List)
	 */
	public boolean checkReportInvUserSection(User user, String accessLevel, List<InvSumSR> list){
		boolean flag = false;
		int currentYear = Integer.valueOf(masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null).getValue1()); // カレント年度
		//	親部課コード取得
		String parentSectionCode = masterSession.searchParentSectionCode(user.getCompanyCode(), user.getSectionCode(), currentYear);

		if(list != null && list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				InvSumSR invSumSR = list.get(i);
				//	所属部署と保有部署が異なる?
				if( !Function.nvl(invSumSR.getHolSectionCode(), "").equals(parentSectionCode)){
					flag = true;
					break;
				}
			}
		}

		return flag;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#remindInv(java.lang.String, java.lang.String, java.util.List)
	 */
	public void remindInv(String loginStaffCode, String accessLevel, List<InvSumSR> list) throws AppException{
		try{
			//////////////////////////////////// バリデーション
			//	棚卸データ作成情報取得
			InvDAO invDAO = new InvDAO();
			InvStat invStatCurrent = invDAO.selectInvStat(list.get(0).getCompanyCode(), list.get(0).getPeriod(), true);

			//	督促メール対象の資産区分をHashMapに保持
			HashMap<String, Object> invAssetTypeMap = new HashMap<String, Object>();
			for(Iterator<InvSumSR> iter = list.iterator(); iter.hasNext();){
				InvSumSR invSumSR = iter.next();
				invAssetTypeMap.put(invSumSR.getInvAssetType(), invSumSR.getInvAssetType());
			}
			String errorMsg = "";
			//	有形固定資産
			if(invAssetTypeMap.containsKey(Constants.INV_ASSET_TYPE_FLD_TAN)){
				//	送信していない？
				if(!Function.nvl(invStatCurrent.getFldTanSendStatus(), "").equals(Constants.INV_MAIL_SEND)){
					if(errorMsg.length() > 0){
						errorMsg = errorMsg + ",";
					}
						errorMsg = errorMsg + "「有形固定資産」";
				}
			}
			//	資産除去費用
			if(invAssetTypeMap.containsKey(Constants.INV_ASSET_TYPE_FLD_RO)){
				//	送信していない？
				if(!Function.nvl(invStatCurrent.getFldTanSendStatus(), "").equals(Constants.INV_MAIL_SEND)){
					if(errorMsg.length() > 0){
						errorMsg = errorMsg + ",";
					}
					errorMsg = errorMsg + "「資産除去費用」";
				}
			}
			//	無形固定資産(本勘定),無形固定資産(仮勘定)
			if(invAssetTypeMap.containsKey(Constants.INV_ASSET_TYPE_FLD_INT)
			 || invAssetTypeMap.containsKey(Constants.INV_ASSET_TYPE_FLD_INT_S)
			){
				//	送信していない？
				if(!Function.nvl(invStatCurrent.getFldIntSendStatus(), "").equals(Constants.INV_MAIL_SEND)){
					if(errorMsg.length() > 0){
						errorMsg = errorMsg + ",";
					}
					errorMsg = errorMsg + "「無形固定資産」";
				}
			}
			//	リース
			if(invAssetTypeMap.containsKey(Constants.INV_ASSET_TYPE_LE)){
				//	送信していない？
				if(!Function.nvl(invStatCurrent.getLeSendStatus(), "").equals(Constants.INV_MAIL_SEND)){
					if(errorMsg.length() > 0){
						errorMsg = errorMsg + ",";
					}
					errorMsg = errorMsg + "「リース資産」";
				}
			}
			//	レンタル
			if(invAssetTypeMap.containsKey(Constants.INV_ASSET_TYPE_RE)){
				//	送信していない？
				if(!Function.nvl(invStatCurrent.getReSendStatus(), "").equals(Constants.INV_MAIL_SEND)){
					if(errorMsg.length() > 0){
						errorMsg = errorMsg + ",";
					}
					errorMsg = errorMsg + "「レンタル資産」";
				}
			}
			//	備品
			if(invAssetTypeMap.containsKey(Constants.INV_ASSET_TYPE_EQUIP)){
				//	送信していない？
				if(!Function.nvl(invStatCurrent.getEquipSendStatus(), "").equals(Constants.INV_MAIL_SEND)){
					if(errorMsg.length() > 0){
						errorMsg = errorMsg + ",";
					}
					errorMsg = errorMsg + "「備品等」";
				}
			}

			//	エラーあり？
			if(errorMsg.length() > 0){
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG, "資産区分" + errorMsg + "は棚卸を依頼していないため、督促メールを送信することができません。"));
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "督促メール送信"), e);
		}

		//////////////////////////////////// メール送信
		sendInvMail(loginStaffCode, accessLevel, list, true);
	}

	/**
	 * 棚卸データ公開・督促メール送信
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel アクセスレベル
	 * @param list メール送信対象集約リスト
	 * @param isRemind true:督促メール、false:公開メール
	 * @throws AppException
	 */
	private void sendInvMail(String loginStaffCode, String accessLevel, List<InvSumSR> list, boolean isRemind) {
		//////////////////////////////////// 督促メール送信&データ更新
		StringBuffer reportMailBody = new StringBuffer(); // 処理結果用メール本文
		StringBuffer reportMailBodyError = new StringBuffer(); // 例外発生時のエラー文字列
		String invAssetTypeName = "";	//	資産区分一覧用

		SimpleDateFormat dtf = new SimpleDateFormat("yyyy/MM/dd");

		Date sysdate = new Date();

		// メールテンプレート取得
		String templateCategory;
		if(isRemind) {
			templateCategory = Constants.CATEGORY_CODE_AP_REMIND_INV;
		} else {
			templateCategory = Constants.CATEGORY_CODE_AP_REQUEST_INV;
		}
		CodeName mailTemplate = masterSession.getCodeName(templateCategory, null, list.get(0).getCompanyCode(), null);
		String from = Function.nvl(mailTemplate.getValue1(), "");	//	メール用固定アドレス取得

		// 可変メール送信属性
		List<String> toList = new ArrayList<String>();
		List<String> ccList = new ArrayList<String>();
//		String toStaffName = "";
		String subject = null; // 件名
		String body = null; // 本文
		String invAssetTypeBody = ""; // 資産区分本文
		String invAssetTypeList = ""; // 資産区分一覧
		String retiredUserList = ""; // 退職者一覧
		boolean mailSuccess = true; // メール送信成功フラグ

		////////////	保有部署別メール送信
		int sectionCount = 0;
		int mailSuccessCount = 0;
		for(int i = 0; i < list.size(); i++){
			InvSumSR obj = list.get(i);
			try {

				//	全体処理結果用メール本文ヘッダの資産管理区分情報
				String typeName = obj.getInvAssetTypeName().replaceAll("\\(仮勘定\\)", "").replaceAll("\\(本勘定\\)", "");
				if(!invAssetTypeName.equals("")){
					if(invAssetTypeName.indexOf(typeName) < 0) invAssetTypeName = invAssetTypeName + "," + typeName;
				}
				else{
					invAssetTypeName = typeName;
				}

				if(subject == null){
					subject = Function.nvl(mailTemplate.getValue2(), "");	//	件名
					body = Function.nvl(mailTemplate.getValue3(), "");	//	本文

					//	保有部署の送信先宛先を取得(部署長・資産管理担当者)
					List<RoleSection> userList = masterSession.getUserRoleSectionAllList(obj.getCompanyCode(), obj.getHolSectionCode(), obj.getHolSectionYear());
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

						// 宛先名称
/*
						if(toStaffName.length() > 0){
							toStaffName = toStaffName + ", ";
						}
						toStaffName = toStaffName + user.getStaffName() + "様";	//	宛先担当者名
*/
					}

					// 予約語置換
					subject = subject.replaceAll(MAIL_TEMPLATE_VAR_HOL_SECTION, Function.nvl(obj.getHolSectionName(), ""));	//	保有部署
					subject = subject.replaceAll(MAIL_TEMPLATE_VAR_PERIOD, obj.getPeriod().substring(0, 4) + "年" + obj.getPeriod().substring(4, 6) + "月度");	//	会計年月
//					subject = subject.replaceAll(MAIL_TEMPLATE_VAR_TO_STAFF_NAME, Function.nvl(toStaffName, ""));	//	宛先担当者名
					body = body.replaceAll(MAIL_TEMPLATE_VAR_HOL_SECTION, Function.nvl(obj.getHolSectionName(), ""));	//	保有部署
					body = body.replaceAll(MAIL_TEMPLATE_VAR_PERIOD, obj.getPeriod().substring(0, 4) + "年" + obj.getPeriod().substring(4, 6) + "月度");	//	会計年月
//					body = body.replaceAll(MAIL_TEMPLATE_VAR_TO_STAFF_NAME, Function.nvl(toStaffName, ""));	//	宛先担当者名

				}

				////////////////////////	資産区分の本文作成
				//	有形
				if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_TAN)){
					invAssetTypeList = invAssetTypeList + (invAssetTypeList.equals("") ? "" : ",") + "有形固定資産";
					if(!Function.nvl(mailTemplate.getValue4(), "").equals("")) {
						invAssetTypeBody = invAssetTypeBody + mailTemplate.getValue4() + "\n";
					}
				}
				//	除去費用
				else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_RO)){
					invAssetTypeList = invAssetTypeList + (invAssetTypeList.equals("") ? "" : ",") + "資産除去費用";
					if(!Function.nvl(mailTemplate.getValue5(), "").equals("")) {
						invAssetTypeBody = invAssetTypeBody + mailTemplate.getValue5()+ "\n";
					}
				}
				//	無形固定資産(本勘定)
				else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT)
					||  obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT_S)){
					if(invAssetTypeBody.indexOf("無形固定資産") < 0) invAssetTypeList = invAssetTypeList + (invAssetTypeList.equals("") ? "" : ",") + "無形固定資産";
					if(!Function.nvl(mailTemplate.getValue6(), "").equals("")) {
						if(invAssetTypeBody.indexOf(mailTemplate.getValue6()) < 0) invAssetTypeBody = invAssetTypeBody + mailTemplate.getValue6()+ "\n";
					}
				}
				//	リース
				else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_LE)){
					invAssetTypeList = invAssetTypeList + (invAssetTypeList.equals("") ? "" : ",") + "リース資産";
					if(!Function.nvl(mailTemplate.getValue7(), "").equals("")) {
						invAssetTypeBody = invAssetTypeBody + mailTemplate.getValue7()+ "\n";
					}
				}
				//	レンタル
				else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_RE)){
					invAssetTypeList = invAssetTypeList + (invAssetTypeList.equals("") ? "" : ",") + "レンタル資産";
					if(!Function.nvl(mailTemplate.getValue8(), "").equals("")) {
						invAssetTypeBody = invAssetTypeBody + mailTemplate.getValue8()+ "\n";
					}
				}
				//	備品
				else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_EQUIP)){
					invAssetTypeList = invAssetTypeList + (invAssetTypeList.equals("") ? "" : ",") + "備品等";
					if(!Function.nvl(mailTemplate.getValue9(), "").equals("")) {
						invAssetTypeBody = invAssetTypeBody + mailTemplate.getValue9()+ "\n";
					}
				}

				//	集約情報更新
				if(isRemind) {
					////////////////////////////////////固定値セット
					obj.setReminderDate(sysdate);	//	督促メール送信日
					updateInvSum(loginStaffCode, accessLevel, obj);
				}

				//	次回保有部署が違う？
				if(i == (list.size() - 1) || ( i < (list.size() - 1) && !obj.getHolSectionCode().equals(list.get(i+1).getHolSectionCode()))){
					if(mailSuccess) {
						//	本文に資産区分本文組込
						subject = subject.replaceAll(MAIL_TEMPLATE_VAR_INV_ASSET_TYPE_LIST, Function.nvl(invAssetTypeList, ""));	//	資産区分リスト
						subject = subject.replaceAll(MAIL_TEMPLATE_VAR_INV_ASSET_TYPE_BODY, Function.nvl(invAssetTypeBody, ""));	//	資産区分本文
						body = body.replaceAll(MAIL_TEMPLATE_VAR_INV_ASSET_TYPE_LIST, Function.nvl(invAssetTypeList, ""));	//	資産区分リスト
						body = body.replaceAll(MAIL_TEMPLATE_VAR_INV_ASSET_TYPE_BODY, Function.nvl(invAssetTypeBody, ""));	//	資産区分本文
						//	メール送信
						sendMailSession.sendMail(from, toList, ccList, subject, body);
					}
				}

			} catch (Exception e) {
				mailSuccess = false;
				reportMailBodyError.append("---------------------------------------------------------------");
				reportMailBodyError.append("保有部署：" + obj.getHolSectionName() + "\n");
				reportMailBodyError.append("資産区分：" + obj.getInvAssetTypeName() + "\n");
				reportMailBodyError.append(Function.getStackTraceStr(e));
				reportMailBodyError.append("\n");
				Logging.error(e.getMessage(), e);
			}

			//	全体処理結果用本文作成
			if(i == (list.size() - 1) || ( i < (list.size() - 1) && !obj.getHolSectionCode().equals(list.get(i+1).getHolSectionCode()))){
				sectionCount++; // 処理件数

				if(!mailSuccess){
					reportMailBody.append("※送信失敗");
				}
				else{
					reportMailBody.append("送信成功");
					mailSuccessCount++; // 送信成功件数
				}

				//	退職者一覧を追加
				if(!Function.nvl(retiredUserList, "").equals("")){
					reportMailBody.append("　　　有　　");
				}
				else{
					reportMailBody.append("　　　‐　　");
				}

				reportMailBody.append("　" + obj.getHolSectionName());
				//	退職者一覧を追加
				if(!Function.nvl(retiredUserList, "").equals("")){
					reportMailBody.append("　(" + retiredUserList + ")");
				}

				reportMailBody.append("\n");

				//	メール送信成功フラグリセット
				mailSuccess = true;

				//	あて先、件名、本文リセット
				toList = new ArrayList<String>();
				ccList = new ArrayList<String>();
//				toStaffName = "";
				subject = null;
				body = null;
				invAssetTypeBody = "";
				invAssetTypeList = "";
				retiredUserList = "";

			}
		}

		//////////////////////////////////// 全体処理結果通知
		//	特定メーリングリストの取得
		//	全体処理結果本文作成
		StringBuffer allBody = new StringBuffer();
		//	本文ヘッダ
		allBody.append("資産区分:　" + invAssetTypeName + "\n");
		allBody.append("処理件数:　" + sectionCount + " 件　(成功：　" + mailSuccessCount + " 件、失敗：　" + (sectionCount - mailSuccessCount) + " 件)" + "\n\n");
		//	本文明細
		allBody.append("送信結果　退職者有無　保有部署　(メール送信除外)\n");
		allBody.append("---------------------------------------------------------------\n");
		allBody.append(reportMailBody);
		if(reportMailBodyError.length() > 0) {
			allBody.append("\n以下送信失敗時のエラーメッセージ\n");
			allBody.append(reportMailBodyError);
		}
		// 送信結果メール送信
		if(from != null){
			sendMailSession.sendMail(from, from, null, "棚卸-" + (isRemind ? "督促" : "依頼") + "メール送信結果" + dtf.format(new Date()), allBody.toString());
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#createInvSumCsv(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.inv.InvSumSC)
	 */
	public String createInvSumCsv(String loginStaffCode, String accessLevel, String companyCode, InvSumSC searchParam){
		try {
			InvDAO invDao = new InvDAO();
			//	最新の年月？
			boolean isHist = getInvHistPeriodFlag(companyCode, searchParam.getPeriod());
			CsvWriterRowHandler handler = invDao.createInvSumCsv(loginStaffCode, accessLevel, companyCode, searchParam, isHist);

			return handler.getFileId();

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸集約ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸集約ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#createInvPdf(java.lang.String, java.lang.String, java.util.List)
	 */
	public String createInvPdf(String loginStaffCode, String accessLevel, List<InvSumSR> list) throws AppException{
		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();
		//////////////////////////////// 部署パラメータ
		List<String> sectionCodeList = new ArrayList<String>();

		StringBuffer section = new StringBuffer();

		for(Iterator<InvSumSR> iter = list.iterator(); iter.hasNext();){
			InvSumSR obj = iter.next();
			//	部署長・資産管理担当者？
			if(Function.isAccessLevelSection(accessLevel)){
				if(section.length() == 0){
					section.append("( (");
				}
				else{
					section.append(" OR (");
				}
				//	棚卸対象者データ？
				if(Function.nvl(obj.getInvStaffFlag(), "").equals(Constants.FLAG_YES)){
					section.append("(nil.INV_STAFF_CODE = '" + loginStaffCode + "') AND ");
				}
					section.append(
									  "(nil.COMPANY_CODE,nil.HOL_SECTION_CODE,nil.HOL_SECTION_YEAR) IN "
									+ "(( '"+ obj.getCompanyCode() + "', '" + obj.getHolSectionCode() + "', " + obj.getHolSectionYear() + ")) "
									);
					section.append(")");
			}
			else {
				sectionCodeList.add(obj.getHolSectionCode());
			}
		}

		//	部署長・資産管理担当者？
		if(Function.isAccessLevelSection(accessLevel)){
			section.append(")");
		}
		else{
			List<String> companyColumnList = new ArrayList<String>();	// 会社カラム
			companyColumnList.add("nil.COMPANY_CODE");
			List<String> sectionColumnList = new ArrayList<String>(); // 部署カラム
			sectionColumnList.add("nil.HOL_SECTION_CODE");
			List<String> sectionYearColumnList = new ArrayList<String>(); // 部署年度カラム
			sectionYearColumnList.add("nil.HOL_SECTION_YEAR");
			section.append(Function.getSectionCondition(null, companyColumnList, sectionColumnList, sectionYearColumnList, list.get(0).getCompanyCode(), null, sectionCodeList, list.get(0).getHolSectionYear()));
		}
		reportParam.put("holSectionWhere", section.toString());

		reportParam.put("period", list.get(0).getPeriod());
		reportParam.put("companyCode", list.get(0).getCompanyCode());
		///////////////////////////	資産側の履歴テーブル参照フラグ
		String code = "";
		//	固定資産、資産除却費、無形固定資産(本勘定),無形固定資産(仮勘定)
		if(
			list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_TAN)
		|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_RO)
		|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT)
		|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT_S)
		){
			code = Constants.PPFS_IMPORT_DATA_TYPE_FLD;
		}
		//	リース・レンタル契約照会
		else if(list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_LE)
			|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_RE)){
			code = Constants.PPFS_IMPORT_DATA_TYPE_LLD;
		}
		if(!Function.nvl(code, "").equals("")){
			// 最新の取込年月取得
			String maxPeriod = "";
			List<PpfsStat> importStatList = ppfsImportSession.getPpfsStatList(list.get(0).getCompanyCode(), code);
			for(Iterator<PpfsStat> iter = importStatList.iterator(); iter.hasNext();) {
				PpfsStat stat = iter.next();
				if(stat.getLastSuccessCreateDate() != null) {
					maxPeriod = stat.getPeriod();
					break;
				}
			}
			// 最新の取込年月と検索条件の年月比較
			if(!Function.nvl(maxPeriod, "").equals(Function.nvl(list.get(0).getPeriod(), ""))){
				//	固定資産、資産除却費、無形固定資産(本勘定),無形固定資産(仮勘定)
				if(
				   list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_TAN)
				|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_RO)
				|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT)
				|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT_S)
				){
					reportParam.put("histTablePrefixLdFld", "H_");
				}
				//	リース・レンタル契約照会
				else if(list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_LE)
					|| list.get(0).getInvAssetType().equals(Constants.INV_ASSET_TYPE_RE)){
					reportParam.put("histTablePrefixLdLld", "H_");
				}
			}
		}

		/////////////////////////	棚卸側の履歴テーブル参照フラグ
		//	履歴参照？
		if(getInvHistPeriodFlag(list.get(0).getCompanyCode(), list.get(0).getPeriod())){
			reportParam.put("histTablePrefixLdInv", "H_");
		}

		//	一般？
		if(Function.isAccessLevelGeneral(accessLevel)){
			reportParam.put("invStaffWhere", "  AND nil.INV_STAFF_CODE = '" + loginStaffCode + "' ");
		}

		// PDF生成
		PdfExporter exporter = new PdfExporter();
		try {
			if(list != null && list.size() > 0){
				InvSumSR obj = list.get(0);
				String jasperPass = "jp/co/ctcg/easset/report/";
				//	有形固定資産
				if(Function.nvl(obj.getInvAssetType(), "").equals(Constants.INV_ASSET_TYPE_FLD_TAN)
				){
					jasperPass = jasperPass + "InvTan.jasper";
				}
				//	資産除去費用
				else if(Function.nvl(obj.getInvAssetType(), "").equals(Constants.INV_ASSET_TYPE_FLD_RO)){
					jasperPass = jasperPass + "InvRo.jasper";
				}
				//	無形固定資産(本勘定)
				else if(Function.nvl(obj.getInvAssetType(), "").equals(Constants.INV_ASSET_TYPE_FLD_INT)){
					jasperPass = jasperPass + "InvInt.jasper";
				}
				//	無形固定資産(仮勘定)
				else if(Function.nvl(obj.getInvAssetType(), "").equals(Constants.INV_ASSET_TYPE_FLD_INT_S)){
					jasperPass = jasperPass + "InvIntS.jasper";
				}
				//	リース
				else if(Function.nvl(obj.getInvAssetType(), "").equals(Constants.INV_ASSET_TYPE_LE)
				){
					jasperPass = jasperPass + "InvLe.jasper";
				}
				//	PDF作成
				exporter.exportPdf(jasperPass, reportParam);
			}
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "実査表印刷用データ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#approveInv(java.lang.Long, java.lang.String)
	 */
	public void approveInv(Long eappId, String execStaffCode) throws AppException{
		try{
			InvDAO invDao = new InvDAO();
			//////////////////////////////////// 更新
			List<InvSumSR> list = invDao.selectInvSumList(eappId, false, true);	//	行ロック

			if(list == null || list.size() == 0){
				throw new AppException(MsgManager.getMessage(MsgManager.MSGID200041, "承認対象の棚卸データが最新ではありません。"));
			}

			Date sysdate = new Date(); // システム日付取得

			for(Iterator<InvSumSR> iter = list.iterator(); iter.hasNext();){
				InvSumSR item = iter.next();

				////////////////////////////////////固定値セット
				// ステータスセット
				item.setApStatus(Constants.AP_STATUS_INV_TAN_APPROVE);
				//	承認者・承認日
				item.setApproveStaffCode(execStaffCode);
				item.setApproveDate(new Date());
				// 更新日・更新者
				item.setUpdateDate(sysdate);
				item.setUpdateStaffCode(execStaffCode);
				invDao.updateInvSum(item);	//	棚卸集約情報更新

			}

			//////////////////////////////////// メッセージ送信
			// メッセージパラメータ作成
			HashMap<String, Object> param = new HashMap<String, Object>();
			param.put("list", list);
			param.put("execStaffCode", execStaffCode);

			Function.sendJmsMessage(updateAssetInvDataQueueFactory, updateAssetInvDataQueue, param);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#createInvData(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public void updateAssetInvData(String execStaffCode, List<InvSumSR> list) {
		try {
			//////////////////////////////////// 更新
			//	会計年月集計日取得
			Date lastSuccessCreateDate = null;
			List<InvStat> invStatList = searchInvStat(list.get(0).getCompanyCode());
			for(Iterator<InvStat> iter = invStatList.iterator(); iter.hasNext();){
				InvStat invStat = iter.next();
				//	承認対象の棚卸集約会計年月と棚卸データ作成会計年月が一緒
				if(Function.nvl(invStat.getPeriod(), "").equals(list.get(0).getPeriod())){
					//	会計年月集計日取得
					lastSuccessCreateDate = invStat.getLastSuccessCreateEndDate();
					break;
				}
			}

			Date sysdate = new Date(); // システム日付取得
			for(Iterator<InvSumSR> iter = list.iterator(); iter.hasNext();){
				InvSumSR item = iter.next();
				List<InvLine> invLineList = searchInvLine(execStaffCode, null, item, null);
				for(Iterator<InvLine> iter2 = invLineList.iterator(); iter2.hasNext();){
					InvLine invLine = iter2.next();
					//	棚卸ステータスが「有」、「無」以外？
					if(!invLine.getInvStatus().equals(Constants.INV_STATUS_OWN) && !invLine.getInvStatus().equals(Constants.INV_STATUS_NOT_OWN)){
						continue;	//	以降の処理スキップ
					}
					//	管理番号が情報機器等番号のときのみ連携
					if(Function.nvl(invLine.getAstLicId(), "").indexOf("H") > -1){
						//	情報機器棚卸明細連携
						boolean updateFlag = updateAsset(invLine, lastSuccessCreateDate, sysdate, true);
						if(!updateFlag){
							continue;
						}
					}
				}
			}

		} catch (Exception e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#cancelApplyInv(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyInv(Long eappId, String execStaffCode) throws AppException {
		try{
			InvDAO invDao = new InvDAO();
			//////////////////////////////////// 更新
			List<InvSumSR> list = invDao.selectInvSumList(eappId, false, true);	//	行ロック
			Date sysdate = new Date(); // システム日付取得

			if(list != null && list.size() > 0){
				for(Iterator<InvSumSR> iter = list.iterator(); iter.hasNext();){
					InvSumSR item = iter.next();
					////////////////////////////////////固定値セット
					// ステータスセット
					item.setApStatus(Constants.AP_STATUS_INV_NOAPPLY);
					// 更新日・更新者
					item.setUpdateDate(sysdate);
					item.setUpdateStaffCode(execStaffCode);
					item.setEappId(null); // 書類IDクリア
					invDao.updateInvSum(item);	//	棚卸集約情報更新
				}
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#rejectInv(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectInv(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		try{
			InvDAO invDao = new InvDAO();
			//////////////////////////////////// 更新
			List<InvSumSR> list = invDao.selectInvSumList(eappId, false, true);	//	行ロック
			Date sysdate = new Date(); // システム日付取得

			for(Iterator<InvSumSR> iter = list.iterator(); iter.hasNext();){
				InvSumSR item = iter.next();
				////////////////////////////////////固定値セット
				// ステータスセット
				item.setApStatus(Constants.AP_STATUS_INV_SENDBACK);
				// 更新日・更新者
				item.setUpdateDate(sysdate);
				item.setUpdateStaffCode(execStaffCode);
				invDao.updateInvSum(item);	//	棚卸集約情報更新
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸更新"), e);
		}
	}

	/**
	 * 棚卸集約情報更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 棚卸集約データ
	 * @throws AppException
	 */
	private void updateInvSum(String loginStaffCode, String accessLevel, InvSumSR obj) throws AppException {
		try{
			InvDAO invDao = new InvDAO();
			StringBuffer errMsg = new StringBuffer();
			//	棚卸データ集約情報更新
			InvSumSC searchParam = new InvSumSC();
			//	集約情報検索条件セット
			searchParam.setCompanyCode(obj.getCompanyCode());
			searchParam.setPeriod(obj.getPeriod());
			searchParam.setHolSectionCode(obj.getHolSectionCode());
			searchParam.setHolSectionYear(obj.getHolSectionYear());
			searchParam.setInvAssetType(obj.getInvAssetType());
			List<InvSumSR> listInvSumOldList = invDao.selectInvSumList(loginStaffCode, accessLevel, obj.getCompanyCode(), searchParam, false, true);	//	行ロック

			//////////////////////////////////// バリデーション
			// バージョンチェック
			if(listInvSumOldList != null && listInvSumOldList.size() > 0 && listInvSumOldList.get(0).getUpdateDate().compareTo(obj.getUpdateDate()) > 0) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER_LIST));
			}

			//////////////////////////////	固定値設定
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			invDao.updateInvSum(obj);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸集約情報更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#createInvLineCsv(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.inv.InvSumSR)
	 */
	public String createInvLineCsv(String loginStaffCode, String accessLevel, InvSumSR searchParam, String searchScopeType) {
		try {
			InvDAO invDao = new InvDAO();
			//	最新の年月？
			boolean isHist = getInvHistPeriodFlag(searchParam.getCompanyCode(), searchParam.getPeriod());
			CsvWriterRowHandler handler = invDao.createInvLineCsv(loginStaffCode, accessLevel, searchParam, searchScopeType, isHist);

			return handler.getFileId();

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "エクスポート"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "エクスポート"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#createInvLineListCsv(java.lang.String, java.lang.String, java.util.List)
	 */
	public String createInvLineListCsv(String loginStaffCode, String accessLevel, List<InvSumSR> list, String searchScopeType){
		try {
			InvDAO invDao = new InvDAO();
			//	最新の年月？
			boolean isHist = getInvHistPeriodFlag(list.get(0).getCompanyCode(), list.get(0).getPeriod());
			CsvWriterRowHandler handler = invDao.createInvLineListCsv(loginStaffCode, accessLevel, list, searchScopeType, isHist);

			return handler.getFileId();

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "明細ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "明細ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#getInvLineByCsv(java.lang.String)
	 */
	public List<InvLine> getInvLineByCsv(String loginStaffCode, String accessLevel, String fileId, InvSumSR obj) throws AppException {
		StringBuffer headerRow = new StringBuffer();
		List<String> inputPropsList = new ArrayList<String>();
		List<Format> inputFormatsList = new ArrayList<Format>();
		String categoryCode = "";
		int headerRowCt = 3;

		CsvReaderRowHandler handler = null;
		try {
			List<InvLine> list = new ArrayList<InvLine>();
			//	有形固定資産、資産除去費用
			if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_TAN)
			|| obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_RO)
			){
				categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_FLD;
			}
			//	無形固定資産(本勘定)
			else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT)){
				categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_INT;
			}
			//	無形固定資産(仮勘定)
			else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_FLD_INT_S)){
				categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_INT_S;
			}
			//	リース資産
			else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_RE)){
				categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_RE;
			}
			//	レンタル資産
			else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_LE)){
				categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_LE;
			}
			//	備品等
			else if(obj.getInvAssetType().equals(Constants.INV_ASSET_TYPE_EQUIP)){
				categoryCode = Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_EQUIP;
			}
			new MasterDAO().setCsvDef(categoryCode, accessLevel, headerRow, inputPropsList, inputFormatsList);

			handler = new CsvReaderRowHandler(fileId, headerRowCt, InvLine.class, (String[]) inputPropsList.toArray(new String[0]), (Format[]) inputFormatsList.toArray(new Format[0]));
			InvLine row = null;
			StringBuffer errorMessage = new StringBuffer(); // 全行エラーメッセージ
			int rowCt = headerRowCt;

			do {
				// 行データ取得
				row = (InvLine) handler.handleRow();
				if(row == null) break; // 行データが取得できない場合は終了

				rowCt++;
				String rowNumStr = "[" + rowCt + "行目] ";

				StringBuffer rowErrorMessage = new StringBuffer(); // 一行エラーメッセージ

				////////////////////////////////////////// 読み込みエラー判定
				if(handler.getRowStatus() == CsvReaderRowHandler.ROW_STATUS_ERROR) { // CSV読み込み時のエラー有
					rowErrorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr, handler.getRowErrorMessage()));
				}

				//	有？
				if(Function.nvl(row.getInvStatusName(), "").equals(Constants.INV_STATUS_NAME_OWN)){
					row.setInvStatus(Constants.INV_STATUS_OWN);
					row.setInvStatus1(Constants.FLAG_YES);
					row.setInvStatus2(Constants.FLAG_NO);
				}
				//	無？
				else if(Function.nvl(row.getInvStatusName(), "").equals(Constants.INV_STATUS_NAME_NOT_OWN)){
					row.setInvStatus(Constants.INV_STATUS_NOT_OWN);
					row.setInvStatus1(Constants.FLAG_NO);
					row.setInvStatus2(Constants.FLAG_YES);
				}
				else{
					row.setInvStatus(Constants.INV_STATUS_UNDECIDED);
					row.setInvStatus1(Constants.FLAG_NO);
					row.setInvStatus2(Constants.FLAG_NO);
				}

				////////////////////////////////////////// 行登録
				list.add(row);

				if(rowErrorMessage.length() > 0) { // 対象行でエラー発生
					errorMessage.append(rowErrorMessage.toString());
				}

			} while(true);

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
	 * @see jp.co.ctcg.easset.session.InvSession#callCreateInvData(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public void callCreateInvData(String companyCode, String period, String execStaffCode, Boolean overwriteFlag) throws AppException {
		//////////////////////////////////// ステータス作成
		InvDAO invDAO = new InvDAO();

		if(!overwriteFlag.booleanValue()) {
			List<InvStat> statList = searchInvStat(companyCode);
			for(int i = 0; i < statList.size(); i++) {
				InvStat stat = statList.get(i);
				if(stat.getPeriod().equals(period) && stat.getLastSuccessCreateStartDate() != null) { // 処理対象年月
					throw new AppException(period.substring(0, 4) + "/" + period.substring(4, 6) + "の棚卸データは既に作成されています。\n「作成済の場合は再作成する」にチェックをつけて処理を実行してください。");
				}
			}
		}

		try {
			invDAO.callUpdateInvStatus(companyCode, period, execStaffCode, Constants.INV_CREATE_STATUS_PROCESSING);
		} catch (SQLException e) {
			if(e.getErrorCode() == ERROR_CODE_DUP_EXEC || e.getErrorCode() == ERROR_CODE_NOWAIT) {
				throw new AppException(period.substring(0, 4) + "/" + period.substring(4, 6) + "の棚卸データ作成処理は既に実行中です。");
			} else {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取込ステータス更新"), e);
			}
		}

		//////////////////////////////////// メッセージ送信
		// メッセージパラメータ作成
		HashMap<String, Object> param = new HashMap<String, Object>();

		param.put("functionName", CreateInvDataMDBean.FUNCTION_CREATE);
		param.put("companyCode", companyCode);
		param.put("period", period);
		param.put("execStaffCode", execStaffCode);
		param.put("overwriteFlag", overwriteFlag);

		Function.sendJmsMessage(createInvDataQueueFactory, createInvDataQueue, param);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#createInvData(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public void createInvData(String companyCode, String period, String execStaffCode, Boolean overwriteFlag) {
		InvDAO invDAO = new InvDAO();
		try {
			invDAO.callCreateInvData(companyCode, period, execStaffCode);
			invDAO.callUpdateInvStatus(companyCode, period, execStaffCode, Constants.INV_CREATE_STATUS_SUCCESS);
		} catch (Exception e) {
			handleDataCreateError(companyCode, period, execStaffCode, e);
		}
	}

	/**
	 * 棚卸データ作成処理のエラーハンドリング
	 * @param companyCode 会社コード
	 * @param period 会計年月
	 * @param execStaffCode 実行者社員番号
	 * @param e
	 */
	private void handleDataCreateError(String companyCode, String period, String execStaffCode, Exception e) {
		InvDAO invDAO = new InvDAO();

		StringBuffer mailBody = new StringBuffer();

		e.printStackTrace();

		mailBody.append("------- 実行パラメータ -------\n");
		mailBody.append("会社コード:" + companyCode + "\n");
		mailBody.append("会計年月:" + period + "\n");
		mailBody.append("実行者社員番号:" + execStaffCode + "\n");

		mailBody.append("\n");

		mailBody.append("------- データ作成処理実行時例外 -------\n");
		mailBody.append(Function.getStackTraceStr(e));

		try {
			invDAO.callUpdateInvStatus(companyCode, period, execStaffCode, Constants.INV_CREATE_STATUS_ERROR);
		} catch (Exception e2) {
			e2.printStackTrace();

			mailBody.append("\n");
			mailBody.append("------- データ作成ステータス更新時例外 -------\n");
			mailBody.append(Function.getStackTraceStr(e2));
		}

		// 管理者ML宛にメール送信
		CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_SYSTEM_ADMIN_EMAIL, null, null, null);
		if(cn != null) {
			String systemAdminMl = cn.getValue1();
			sendMailSession.sendMail(systemAdminMl, systemAdminMl, null, "棚卸データ作成エラー通知", mailBody.toString());
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#publicInvData(java.lang.String, java.lang.String, java.lang.String[], java.lang.Boolean[], java.lang.String)
	 */
	public void publicInvData(String companyCode, String period, String[] publicTypeArray, Boolean[] sendExecArray, String execStaffCode) throws AppException {
		InvDAO invDAO = new InvDAO();
		try {
			InvStat obj = invDAO.selectInvStat(companyCode, period, true);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer();

			// カレント年月のデータ作成ステータス
			if(Function.nvl(obj.getCreateStatus(), "").equals(Constants.INV_CREATE_STATUS_ERROR)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "データ作成ステータス", "該当年月のデータ作成処理が正常終了していないため処理を継続できません。"));
			} else if(Function.nvl(obj.getCreateStatus(), "").equals(Constants.INV_CREATE_STATUS_PROCESSING)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "データ作成ステータス", "該当年月のデータ作成処理が実行中のため処理を継続できません。"));
			}

			if(errMsg.length() > 0) throw new AppException(errMsg.toString());

			// 全年月のデータ作成ステータス
			List<InvStat> invStatList = searchInvStat(companyCode);
			for(int i = 0; i < invStatList.size(); i++) {
				InvStat row = invDAO.selectInvStat(companyCode, invStatList.get(i).getPeriod(), true);
				if(Function.nvl(row.getCreateStatus(), "").equals(Constants.INV_CREATE_STATUS_PROCESSING)) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "データ作成ステータス", "データ作成処理が実行中の年月があるため処理を継続できません。"));
					break;
				}
			}

			if(errMsg.length() > 0) throw new AppException(errMsg.toString());

			// メール送信ステータス
			if(sendExecArray[0].booleanValue() && Function.nvl(obj.getFldTanSendStatus(), "").equals(Constants.INV_MAIL_SENDING)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "有形固定資産", "該当年月のメール送信処理が既に実行中です。"));
			}
			if(sendExecArray[1].booleanValue() && Function.nvl(obj.getFldRoSendStatus(), "").equals(Constants.INV_MAIL_SENDING)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産除去費用", "該当年月のメール送信処理が既に実行中です。"));
			}
			if(sendExecArray[2].booleanValue() && Function.nvl(obj.getFldIntSendStatus(), "").equals(Constants.INV_MAIL_SENDING)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "無形固定資産", "該当年月のメール送信処理が既に実行中です。"));
			}
			if(sendExecArray[3].booleanValue() && Function.nvl(obj.getLeSendStatus(), "").equals(Constants.INV_MAIL_SENDING)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "リース", "該当年月のメール送信処理が既に実行中です。"));
			}
			if(sendExecArray[4].booleanValue() && Function.nvl(obj.getReSendStatus(), "").equals(Constants.INV_MAIL_SENDING)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "レンタル", "該当年月のメール送信処理が既に実行中です。"));
			}
			if(sendExecArray[5].booleanValue() && Function.nvl(obj.getEquipSendStatus(), "").equals(Constants.INV_MAIL_SENDING)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "備品等", "該当年月のメール送信処理が既に実行中です。"));
			}

			if(errMsg.length() > 0) throw new AppException(errMsg.toString());

			//////////////////////////////////// 公開区分更新
			// 公開区分設定
			obj.setFldTanPublicType(publicTypeArray[0]); // 有形
			obj.setFldRoPublicType(publicTypeArray[1]); // 除去費用
			obj.setFldIntPublicType(publicTypeArray[2]); // 無形
			obj.setLePublicType(publicTypeArray[3]); // リース
			obj.setRePublicType(publicTypeArray[4]); // レンタル
			obj.setEquipPublicType(publicTypeArray[5]); // 備品等

			// メール送信設定
			boolean isMailSend = false;
			if(sendExecArray[0].booleanValue()) {
				isMailSend = true;
				obj.setFldTanSendStartDate(new Date());
				obj.setFldTanSendEndDate(null);
				obj.setFldTanSendStatus(Constants.INV_MAIL_SENDING);
			}
			if(sendExecArray[1].booleanValue()) {
				isMailSend = true;
				obj.setFldRoSendStartDate(new Date());
				obj.setFldRoSendEndDate(null);
				obj.setFldRoSendStatus(Constants.INV_MAIL_SENDING);
			}
			if(sendExecArray[2].booleanValue()) {
				isMailSend = true;
				obj.setFldIntSendStartDate(new Date());
				obj.setFldIntSendEndDate(null);
				obj.setFldIntSendStatus(Constants.INV_MAIL_SENDING);
			}
			if(sendExecArray[3].booleanValue()) {
				isMailSend = true;
				obj.setLeSendStartDate(new Date());
				obj.setLeSendEndDate(null);
				obj.setLeSendStatus(Constants.INV_MAIL_SENDING);
			}
			if(sendExecArray[4].booleanValue()) {
				isMailSend = true;
				obj.setReSendStartDate(new Date());
				obj.setReSendEndDate(null);
				obj.setReSendStatus(Constants.INV_MAIL_SENDING);
			}
			if(sendExecArray[5].booleanValue()) {
				isMailSend = true;
				obj.setEquipSendStartDate(new Date());
				obj.setEquipSendEndDate(null);
				obj.setEquipSendStatus(Constants.INV_MAIL_SENDING);
			}

			// 更新者設定
			obj.setUpdateDate(new Date());
			obj.setUpdateStaffCode(execStaffCode);

			// 更新
			invDAO.updateInvStat(obj);


			//////////////////////////////////// カレント年月データ入替
			invDAO.callSwitchCurrentInvData(companyCode);

			//////////////////////////////////// メッセージ送信(メール送信呼び出し)
			if(isMailSend) {
				// メッセージパラメータ作成
				HashMap<String, Object> param = new HashMap<String, Object>();

				param.put("functionName", CreateInvDataMDBean.FUNCTION_SENDMAIL);
				param.put("companyCode", companyCode);
				param.put("period", period);
				param.put("sendExecArray", sendExecArray);
				param.put("execStaffCode", execStaffCode);

				Function.sendJmsMessage(createInvDataQueueFactory, createInvDataQueue, param);
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸データ公開区分更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#sendPublicInvMail(java.lang.String, java.lang.String, java.lang.Boolean[], java.lang.String)
	 */
	public void sendPublicInvMail(String companyCode, String period, Boolean[] sendExecArray, String execStaffCode) {
		try {
			InvDAO invDAO = new InvDAO();

			//////////////////////////////////// 送信対象検索
			InvSumSC searchParam = new InvSumSC();

			searchParam.setCompanyCode(companyCode);
			searchParam.setPeriod(period);

			// 資産区分
			StringBuffer invAssetType = new StringBuffer();
			if(sendExecArray[0].booleanValue()) {
				invAssetType.append(Constants.INV_ASSET_TYPE_FLD_TAN + " ");
			}
			if(sendExecArray[1].booleanValue()) {
				invAssetType.append(Constants.INV_ASSET_TYPE_FLD_RO + " ");
			}
			if(sendExecArray[2].booleanValue()) {
				invAssetType.append(Constants.INV_ASSET_TYPE_FLD_INT + " ");
				invAssetType.append(Constants.INV_ASSET_TYPE_FLD_INT_S + " ");
			}
			if(sendExecArray[3].booleanValue()) {
				invAssetType.append(Constants.INV_ASSET_TYPE_LE + " ");
			}
			if(sendExecArray[4].booleanValue()) {
				invAssetType.append(Constants.INV_ASSET_TYPE_RE + " ");
			}
			if(sendExecArray[5].booleanValue()) {
				invAssetType.append(Constants.INV_ASSET_TYPE_EQUIP + " ");
			}
			searchParam.setInvAssetType(invAssetType.toString());

			// 送信対象集約データ取得
			List<InvSumSR> sumList = invDAO.selectInvSumList(Constants.STAFF_CODE_SYSTEM, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, companyCode, searchParam, false, true);

			// 集約データから保有部署不明、資産情報不明を除外（メールを送信しないため）
			int size = sumList.size();
			for(int i = size - 1; i >= 0; i--) {
				InvSumSR invSum = sumList.get(i);

				if(Function.nvl(invSum.getHolSectionCode(), "").equals(Constants.INV_UNKNOWN_SECTION_CODE)
					|| Function.nvl(invSum.getHolSectionCode(), "").equals(Constants.INV_UNKNOWN_ASSET)) {
					sumList.remove(i);
				}
			}

			//////////////////////////////////// メール送信
			sendInvMail(execStaffCode, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, sumList, false);

			//////////////////////////////////// メール送信ステータス更新
			InvStat obj = invDAO.selectInvStat(companyCode, period, true);

			// メール送信設定
			if(sendExecArray[0].booleanValue()) {
				obj.setFldTanSendEndDate(new Date());
				obj.setFldTanSendStatus(Constants.INV_MAIL_SEND);
			}
			if(sendExecArray[1].booleanValue()) {
				obj.setFldRoSendEndDate(new Date());
				obj.setFldRoSendStatus(Constants.INV_MAIL_SEND);
			}
			if(sendExecArray[2].booleanValue()) {
				obj.setFldIntSendEndDate(new Date());
				obj.setFldIntSendStatus(Constants.INV_MAIL_SEND);
			}
			if(sendExecArray[3].booleanValue()) {
				obj.setLeSendEndDate(new Date());
				obj.setLeSendStatus(Constants.INV_MAIL_SEND);
			}
			if(sendExecArray[4].booleanValue()) {
				obj.setReSendEndDate(new Date());
				obj.setReSendStatus(Constants.INV_MAIL_SEND);
			}
			if(sendExecArray[5].booleanValue()) {
				obj.setEquipSendEndDate(new Date());
				obj.setEquipSendStatus(Constants.INV_MAIL_SEND);
			}

			// 更新者設定
			obj.setUpdateDate(new Date());
			obj.setUpdateStaffCode(execStaffCode);

			// 更新
			invDAO.updateInvStat(obj);

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸依頼メール送信"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#createInvTemplateCsv()
	 */
	public String createInvTemplateCsv(String companyCode, String period){
		try {
			InvDAO invDao = new InvDAO();
			boolean isHist = getInvHistPeriodFlag(companyCode, period);
			CsvWriterRowHandler handler = invDao.createInvTemplateCsv(companyCode, period, isHist);

			return handler.getFileId();

		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "明細ダウンロード"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "明細ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#callUpdateInvBulk(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void callUpdateInvBulk(String loginStaffCode, String companyCode, String accessLevel, String fileId, String period) throws AppException {
		//////////////////////////////////// 一括更新実行ログの作成
		List<CodeName> codeNameList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_ITEM_DEF_INV_LINE_FLD, null, null, null);
		List<CodeName> updatePropertyList = new ArrayList<CodeName>();
		for(int i = 0; i < codeNameList.size(); i ++){
			CodeName codeName = codeNameList.get(i);
			if(Function.nvl(codeName.getValue4(),"").equals("invStatusName")){
				codeName.setValue3("invStatus,invComment");
				codeName.setValue5(period); // 会計年月を設定
				updatePropertyList.add(codeName);
			}
		}
		Long logId = histSession.createBulkUpdateLog(companyCode, loginStaffCode, HIST_ENTITY_NAME, fileId, updatePropertyList);
		BulkUpdateHist hist = histSession.getBulkUpdateHist(logId); // カレントログ取得

		//////////////////////////////////// 対象ファイルを一時領域⇒保存領域へコピー
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		File execFile = masterSession.fileCommit(fileId, HIST_ENTITY_NAME + "_BULK_UPDATE", df.format(hist.getCreateDate()) + "_" + loginStaffCode);
		File successFile = new File(execFile.getAbsolutePath() + "_S");
		File failureFile = new File(execFile.getAbsolutePath() + "_F");
		File errorFile = new File(execFile.getAbsolutePath() + "_E");
		try {
			successFile.createNewFile();
			failureFile.createNewFile();
			errorFile.createNewFile();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "一括更新用ファイル生成"), e);
		}

		//////////////////////////////////// メッセージ送信
		// メッセージパラメータ作成
		HashMap<String, Object> param = new HashMap<String, Object>();

		param.put("functionName", BulkUpdateMDBean.FUNCTION_INV);
		param.put("loginStaffCode", loginStaffCode);
		param.put("companyCode", companyCode);
		param.put("accessLevel", accessLevel);
		param.put("fileId", fileId);
		param.put("execFile", execFile);
		param.put("updatePropertyList", updatePropertyList);
		param.put("logId", logId);
		param.put("period", period);


		Function.sendJmsMessage(bulkUpdateQueueFactory, bulkUpdateQueue, param);

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.InvSession#updateInvBulk(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.io.File, java.util.List, java.lang.Long)
	 */
	public void updateInvBulk(String loginStaffCode, String companyCode, String accessLevel, String fileId, File execFile, List<CodeName> updatePropertyList, Long logId, String period) {
		// ログ更新用件数
		Integer execCount = null;
		Integer successCount = null;
		Integer failureCount = null;
		// ログファイル出力用
		File successFile = new File(execFile.getAbsolutePath() + "_S");
		File failureFile = new File(execFile.getAbsolutePath() + "_F");
		File errorFile = new File(execFile.getAbsolutePath() + "_E");
		BufferedReader execFileReader = null;

		try {
			if(updatePropertyList == null || updatePropertyList.size() == 0) throw new AppException("更新対象項目が指定されていません。");
			//////////////////////////////////// CSV読込
			List<InvLine> updateInvList = new ArrayList<InvLine>();
			boolean isHist = getInvHistPeriodFlag(companyCode, period); // 棚卸公開年月か判断
			int headerRowCt = setInvListByCsv(loginStaffCode, companyCode, accessLevel, fileId, updateInvList, updatePropertyList, logId, period, isHist);

			//////////////////////////////////// 一括更新実行ログの取得
			BulkUpdateHist logHist = histSession.getBulkUpdateHist(logId);

			if(!Function.nvl(logHist.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) { // ファイル読み込み中に中断されていない

				if(updateInvList.size() == 0) throw new AppException("更新対象データが入力されていません。");

				//////////////////////////////////// ファイル読込設定
				execFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(execFile), CsvReaderRowHandler.getCharsetName()));
				StringBuffer headerStr = new StringBuffer();
				for(int i = 0; i < headerRowCt; i++) {
					headerStr.append(execFileReader.readLine() + "\n");
				}

				int skipCount = 0; // エラー再実行時に重複実行しないため、処理スキップする件数

				if(logHist.getSuccessCount() == null && logHist.getFailureCount() == null) { // 初回実行
					//////////////////////////////////// 一括更新実行ログの更新
					execCount = Integer.valueOf(updateInvList.size());
					successCount = Integer.valueOf(0);
					failureCount = Integer.valueOf(0);
					histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_UPDATE, execCount, successCount, failureCount);

					//////////////////////////////////// 成功、失敗ファイルにヘッダ出力
					Function.appendStrToFile(successFile, headerStr.toString());
					Function.appendStrToFile(failureFile, headerStr.toString());
				} else { // エラー発生により再実行
					if(Function.nvl(logHist.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_END)) {
						return; // 処理済であれば終了
					}

					skipCount = Function.nvl(logHist.getSuccessCount(), Integer.valueOf(0)).intValue() + Function.nvl(logHist.getFailureCount(), Integer.valueOf(0)).intValue();

					execCount = logHist.getExecCount();
					successCount = Function.nvl(logHist.getSuccessCount(), Integer.valueOf(0));
					failureCount = Function.nvl(logHist.getFailureCount(), Integer.valueOf(0));
				}
				//////////////////////////////////// 登録
				int rowCt = headerRowCt;

				try {
					//////////////////////////////////// 登録(件数分)
					for(int i = 0; i < updateInvList.size(); i++) {
						//////////////////////////////////// 中断リクエストされていないかどうか確認
						BulkUpdateHist log = histSession.getBulkUpdateHist(logId);
						if(Function.nvl(log.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) break; // 中断

						//////////////////////////////////// 1件処理
						rowCt++;
						String execFileRowStr = execFileReader.readLine(); // 対象ファイル一行読み込み（成功・失敗ファイル保存用）

						if(i < skipCount) {
							continue; // エラー前に実行済みのためスキップ
						}

						InvLine updateObj = updateInvList.get(i); // 更新内容取得

						try {
							//////////////////////////////////// 1件更新
							childInvSession.updateInvBulkRow(loginStaffCode, accessLevel, rowCt, updateObj, isHist);
			
							successCount = Integer.valueOf(successCount.intValue() + 1);
							//////////////////////////////////// 一括更新実行ログの更新
							histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_UPDATE, execCount, successCount, failureCount);
							//////////////////////////////////// 実行データ保存
							Function.appendStrToFile(successFile, execFileRowStr + "\n");
						} catch(Exception e) {
							failureCount = Integer.valueOf(failureCount.intValue() + 1);

							if(failureCount.intValue() == 1) { // 先頭エラーメッセージ表示
								StringBuffer errorMessage = new StringBuffer();
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "更新中に以下のエラーが発生しました。"));
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "※ 更新項目に指定されていない項目がエラーとなっている場合は、該当項目も事前に(同時に)修正する必要があります。"));

								Function.appendMessageToFile(errorFile, errorMessage.toString() + "\n");
							}

							String rowNumStr; // エラー表示用行識別
							rowNumStr = "[" + rowCt + "行目(失敗データファイル " + (headerRowCt + failureCount.intValue()) + "行目):" + updateObj.getAstLicId() + "] "; // エラー表示用行識別に情報機器管理番号付加

							StringBuffer errorMessage = new StringBuffer();
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "以下のエラーがあります。"));
							String errorStr = e.getMessage();
							errorStr = errorStr.replace(ERROR_MSG_BYTE, ERROR_MSG_BYTE_REPLACE);
							errorStr = errorStr.replace(ERROR_MSG_REQUIRED, ERROR_MSG_REQUIRED_REPLACE);
							errorMessage.append(errorStr);

							Function.appendMessageToFile(errorFile, errorMessage.toString() + "\n");

							//////////////////////////////////// 一括更新実行ログの更新
							histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_UPDATE, execCount, successCount, failureCount);
							//////////////////////////////////// 実行データ保存
							Function.appendStrToFile(failureFile, execFileRowStr + "\n");
						}
					}
				} catch (IllegalArgumentException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸更新"), e);
				}

			}

		} catch (Exception e) {
			StringBuffer errorMessage = new StringBuffer();
			errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "ファイル読み込み中に以下のエラーが発生したため、処理を中断しました。"));
			errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "※ 下記表示されているエラー行以外も含め、全ての一括更新対象が更新されていませんので、対象ファイルのエラー行を削除もしくは、修正後再度一括更新を行ってください。"));
			errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "※ 更新項目に指定されていない項目がエラーとなっている場合は、該当項目も事前に(同時に)修正する必要があります。"));
			errorMessage.append("\n");
			errorMessage.append(e.getMessage() + "\n");

			if(!(e instanceof AppException)) errorMessage.append(Function.getStackTraceStr(e) + "\n");

			Function.appendMessageToFile(errorFile, errorMessage.toString() + "\n");
		} finally {
			//////////////////////////////////// 一括更新実行ログの更新
			histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_END, execCount, successCount, failureCount);

			if(execFileReader != null) {
				try {
					execFileReader.close();
				} catch (IOException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸一括更新"), e);
				}
			}

		}
	}
	/**
	 * CSVファイルから棚卸セット
	 * @param accessLevel アクセスレベル
	 * @param fileId CSVファイル参照
	 * @param invObj 登録申請画面入力項目値
	 * @param list 棚卸情報をセットするリスト
	 * @param updatePropertyList 更新項目一覧(nullは全て)
	 * @param logId 一括更新ログID(nullは一括更新以外)
	 * @return ヘッダ行数
	 * @throws AppException
	 */
	private int setInvListByCsv(String loginStaffCode, String companyCode, String accessLevel, String fileId,List<InvLine> list, List<CodeName> updatePropertyList, Long logId, String period, boolean isHist) throws AppException {
		CsvReaderRowHandler handler = null;
		int headerRowCt = 3;
		try {
			InvDAO invDao = new InvDAO();
			String[] strArray = {"astLicId", "invStatusName", "invComment"}; // 取込対象項目指定
			Format[] formatArray = {null, null, null}; // 取込対象項目フォーマット指定
			handler = new CsvReaderRowHandler(fileId, headerRowCt, InvLine.class, strArray, formatArray);
			InvLine row = null;
			StringBuffer errorMessage = new StringBuffer(); // 全行エラーメッセージ
			int rowCt = headerRowCt; // ファイル行カウンタ
			int headerCt = handler.getHeaderRowCount();

			do {
				//////////////////////////////////// 中断リクエストされていないかどうか確認
				if(logId != null) {
					BulkUpdateHist log = histSession.getBulkUpdateHist(logId);
					if(Function.nvl(log.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) break; // 中断
				}
				//////////////////////////////////// 1件処理
				rowCt++;
				String rowNumStr = "[" + rowCt + "行目] ";

				row = (InvLine)handler.handleRow();
				if(row == null) break; // 行データが取得できない場合は終了
				// 管理番号空白
				if(Function.nvl(row.getAstLicId(), "").equals("")){
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "管理番号" + rowNumStr ));
					continue;
				}
				InvLine obj = null;
				//	現状の棚卸明細データ取得
				row.setPeriod(period);
				row.setCompanyCode(companyCode);
				List<InvLine> invLineList = invDao.selectInvLineOnlyList(loginStaffCode, accessLevel, row, true, isHist);
				if(invLineList != null && invLineList.size() > 0){
					obj = invLineList.get(0);
				}
				// CSVの内容を棚卸明細データに設定
				if(obj != null){
					/////////////////// 不正項目チェック
					if(!Function.nvl(row.getInvStatusName(), "").equals("")){
						// 	有、無フォーマットチェック
						if(!row.getInvStatusName().equals(Constants.INV_STATUS_NAME_OWN)
						&& !row.getInvStatusName().equals(Constants.INV_STATUS_NAME_NOT_OWN)
						){
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, "棚卸状況" + rowNumStr, row.getInvStatusName()));
							continue;
						}else{
							// 有？
							if(Function.nvl(row.getInvStatusName(), "").equals(Constants.INV_STATUS_NAME_OWN)){
								obj.setInvStatus(Constants.INV_STATUS_OWN);
							}
							else if(Function.nvl(row.getInvStatusName(), "").equals(Constants.INV_STATUS_NAME_NOT_OWN)){
								obj.setInvStatus(Constants.INV_STATUS_NOT_OWN);
							}
						}
					}
					else{
						obj.setInvStatus(Constants.INV_STATUS_UNDECIDED); // 空白の場合、未実施に更新
					}
					obj.setInvComment(row.getInvComment());	//	コメント
					list.add(obj); // 更新対象棚卸明細格納
				}
				else{
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "管理番号"+ rowNumStr + " : 指定会計年月の棚卸データに、該当資産は存在しません。(" + row.getAstLicId() + ")" ));
					continue;
				}

				if(handler.getRowStatus() == CsvReaderRowHandler.ROW_STATUS_ERROR) { // CSV読み込み時のエラー有
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + handler.getRowErrorMessage()));
				}
				//////////////////////////////////// 一括更新の場合読み込み件数をログ出力
				if(logId != null) {
					histSession.updateBulkUpdateLog(logId, null, Constants.BULK_UPDATE_STATUS_READ, rowCt - headerCt, null, null);
				}
			}while(true);

			if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

			return handler.getHeaderRowCount();
		} catch (SQLException e){
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "一括入力CSVファイル読込"), e);
		} finally {
			if(handler != null) handler.close();
		}

	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // 新規トランザクション
	public void updateInvBulkRow(String loginStaffCode, String accessLevel, int rowCt, InvLine updateObj, boolean isHist) throws AppException, SysException {
		InvDAO invDao = new InvDAO();
		//////////////////////////////////// 一括更新用バリデーション
		StringBuffer errMsg = new StringBuffer();
		try{

			//////////////////////////////////// 更新
			if(errMsg.length() > 0) throw new AppException(errMsg.toString());

			// 棚卸集計日取得
			Date lastSuccessCreateDate = null;
			if(lastSuccessCreateDate == null){
				List<InvStat> invStatList = searchInvStat(updateObj.getCompanyCode());
				for(Iterator<InvStat> iter = invStatList.iterator(); iter.hasNext();){
					InvStat invStat = iter.next();
					//	承認対象の棚卸集約会計年月と棚卸データ作成会計年月が一緒
					if(Function.nvl(invStat.getPeriod(), "").equals(updateObj.getPeriod())){
						//	会計年月集計日取得
						lastSuccessCreateDate = invStat.getLastSuccessCreateEndDate();
						break;
					}
				}
			}

			// 機器番号が重複して存在する場合を考慮して更新対象を管理番号で再取得する。（重複している場合updateObjには先頭1件の情報がセットされている）
			List<InvLine> updateList = invDao.selectInvLineOnlyList(loginStaffCode, accessLevel, updateObj, true, isHist);
			for(int i = 0; i < updateList.size(); i++) {
				InvLine row = updateList.get(i);
				// 再取得明細にCSV内容セット
				row.setInvStatus(updateObj.getInvStatus());
				row.setInvComment(updateObj.getInvComment());

				// 集約情報取得
				InvSumSC invSumSc = new InvSumSC();
				invSumSc.setCompanyCode(row.getCompanyCode());
				if(Constants.INV_LINK_TYPE_UNKNOWN_SECTION_CODE.equals(row.getInvLinkType())) { // 保有不明
					invSumSc.setInvLinkType(Constants.INV_LINK_TYPE_UNKNOWN_SECTION_CODE);
				} else if(Constants.INV_LINK_TYPE_UNKNOWN_ASSET.equals(row.getInvLinkType())) { // 資産情報不明
					invSumSc.setInvLinkType(Constants.INV_LINK_TYPE_UNKNOWN_ASSET);
				} else { // 不明以外
					invSumSc.setHolSectionCode(row.getHolSectionCode());
					invSumSc.setHolSectionYear(row.getHolSectionYear());
				}
				invSumSc.setInvAssetType(row.getInvAssetType());
				invSumSc.setPeriod(row.getPeriod());
				List<InvSumSR> invSumSrList = invDao.selectInvSumList(loginStaffCode, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, row.getCompanyCode(), invSumSc, isHist, false);

				if(invSumSrList != null && invSumSrList.size() > 0){
					InvSumSR invSumSr = invSumSrList.get(0);
					// 棚卸明細、棚卸集約情報更新
					updateInvLine(loginStaffCode, accessLevel, invSumSr, updateList, isHist, true);
					// 承認済み？
					if(invSumSr.getApStatus().equals(Constants.AP_STATUS_INV_TAN_APPROVE)){
						//	管理番号が情報機器等番号のときのみ連携
						if(Function.nvl(row.getAstLicId(), "").indexOf("H") > -1){
							// 情報機器更新
							boolean updateFlag = updateAsset(row, lastSuccessCreateDate, new Date(), false);
							if(!updateFlag){
								throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等取得"));
							}
						}
					}
				}
				else{
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸集約情報取得"));
				}
			}

		}catch (SQLException e){
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "棚卸更新"));
		}
	}

	/**
	 * 情報機器更新連携
	 * @param invLine 棚卸明細
	 * @param lastSuccessCreateDate 棚卸集計日
	 * @param sysdate:システム日付
	 * @return 情報機器更新有無
	 * @throws AppException,SQLException
	 */
	private boolean updateAsset(InvLine invLine, Date lastSuccessCreateDate, Date sysdate, boolean isAddFlag) throws SQLException, AppException{
		// 情報機器明細履歴作成
		//	情報機器棚卸明細連携
		Asset asset = assetSession.getAsset(invLine.getAstLicId(), false);	//	情報機器等情報取得
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy/MM/dd 00:00:00");
		if(asset == null){
			return false;	//	機器情報がなければ以降の更新無
		}
		List<AssetLineInv> assetLineInvList = asset.getAssetLineInv();
		if(assetLineInvList == null){
			assetLineInvList = new ArrayList<AssetLineInv>();
		}
		if(assetLineInvList.size() == 0 || isAddFlag){
			AssetLineInv assetLineInv = new AssetLineInv();
			//////////	固定値セット
			assetLineInv.setAssetId(asset.getAssetId());
			//	作成者・作成日
			assetLineInv.setCreateDate(sysdate);
			assetLineInv.setCreateStaffCode(Constants.STAFF_CODE_SYSTEM);
			//	更新者・更新日
			assetLineInv.setUpdateDate(sysdate);
			assetLineInv.setUpdateStaffCode(Constants.STAFF_CODE_SYSTEM);
			//	棚卸明細データセット
			assetLineInv.setInvDate(lastSuccessCreateDate);	//	棚卸会計年月集計日
			assetLineInv.setInvOfficeName(invLine.getHolOfficeName() + (Function.nvl(invLine.getHolOfficeFloor(), "").equals("") ? "" : " " + invLine.getHolOfficeFloor() + "F"));	//	棚卸データ作成設置場所名
			assetLineInv.setInvComment((invLine.getInvStatus().equals(Constants.INV_STATUS_OWN) ? "有" : "無") + (Function.nvl(invLine.getInvComment(), "").equals("") ? "" : " " + invLine.getInvComment() ));//	コメント
			//	行番号セット
			if(assetLineInvList != null && assetLineInvList.size() > 0){
				assetLineInv.setLineSeq(assetLineInvList.size() + 1);
			}
			else{
				assetLineInv.setLineSeq(1);
			}
			assetLineInvList.add(assetLineInv);
			asset.setAssetLineInv(assetLineInvList);	//	情報機器等棚卸明細入れなおし
			assetSession.updateAsset(Constants.STAFF_CODE_SYSTEM, null, asset, false, HIST_OPERATION_ASSET_UPDATE, false);	//	情報機器等更新
		}
		else{
			boolean isUpdate = false;
			for(int i = 0; i < assetLineInvList.size(); i++){
				AssetLineInv item = assetLineInvList.get(i);
				// 棚卸集計日が一致？
				if(fmt.format(item.getInvDate()).equals(fmt.format(lastSuccessCreateDate))){
					//	更新者・更新日
					item.setUpdateDate(sysdate);
					item.setUpdateStaffCode(Constants.STAFF_CODE_SYSTEM);
					//	棚卸明細データセット
					item.setInvOfficeName(invLine.getHolOfficeName() + (Function.nvl(invLine.getHolOfficeFloor(), "").equals("") ? "" : " " + invLine.getHolOfficeFloor() + "F"));	//	棚卸データ作成設置場所名
					item.setInvComment((invLine.getInvStatus().equals(Constants.INV_STATUS_OWN) ? "有" : "無") + (Function.nvl(invLine.getInvComment(), "").equals("") ? "" : " " + invLine.getInvComment() ));//	コメント
					isUpdate = true;
				}
			}
			// 更新対象がない？
			if(!isUpdate){
				updateAsset(invLine, lastSuccessCreateDate, sysdate, true);
			}
			else{
				asset.setAssetLineInv(assetLineInvList);	//	情報機器等棚卸明細入れなおし
				assetSession.updateAsset(Constants.STAFF_CODE_SYSTEM, null, asset, false, HIST_OPERATION_ASSET_UPDATE, false);	//	情報機器等更新
			}
		}
		return true;
	}


}