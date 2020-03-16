/*===================================================================
 * ファイル名 : ApBgnIntSessionBean.java
 * 概要説明   : サービス提供開始報告(無形)セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2012-09-11 1.0  申           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import org.apache.commons.beanutils.PropertyUtils;

import jp.co.ctcg.easset.dao.ApBgnIntDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.LovDataEx;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnInt;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntLineFld;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntLineProfEst;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntLineAtt;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntLineCostSec;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntSC;
import jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntSR;
import jp.co.ctcg.easset.dto.ap_get_int.ApGetInt;
import jp.co.ctcg.easset.dto.fld.PpfsFldSC;
import jp.co.ctcg.easset.dto.fld.PpfsFldSR;
import jp.co.ctcg.easset.dto.hist.Hist;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;
import jp.co.ctcg.easset.util.PdfExporter;
import jp.co.ctcg.easset.ws.ApBgnIntService;
import jp.co.ctcg.easset.ws.ApBgnIntServiceProxy;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;


@Stateless
public class ApBgnIntSessionBean implements ApBgnIntSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession;

	@EJB
	ApGetIntSession apGetIntSession;

	@EJB
	FldSession fldSession;

	@EJB
	HistSession histSession;

	private static final String FILE_SAVE_ENTITY_NAME = "AP_BGN_INT";

	// 履歴作成用
	private static final String HIST_ENTITY_NAME = "AP_BGN_INT";
	private static final String HIST_OPERATION_CREATE = "新規作成";
	private static final String HIST_OPERATION_UPDATE = "更新";
	private static final String HIST_OPERATION_DELETE = "削除";
	private static final String HIST_OPERATION_APPLY = "申請";
	private static final String HIST_OPERATION_APPROVE = "承認";
	private static final String HIST_OPERATION_SENDBACK = "差戻し";
	private static final String HIST_OPERATION_REJECT = "却下";
	private static final String HIST_OPERATION_CANCEL_APPLY = "引戻し";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#searchApBgnInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntSC)
	 */
	public List<ApBgnIntSR> searchApBgnInt(String loginStaffCode, String accessLevel, ApBgnIntSC searchParam) {
		try {
			ApBgnIntDAO apBgnIntDAO = new ApBgnIntDAO();
			return apBgnIntDAO.selectApBgnIntList(loginStaffCode, accessLevel, searchParam);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#createApBgnIntCsv(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnIntSC)
	 */
	public String createApBgnIntCsv(String loginStaffCode, String accessLevel, ApBgnIntSC searchParam) {
		try {
			ApBgnIntDAO apBgnIntDAO = new ApBgnIntDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = apBgnIntDAO.createApBgnIntListCsv(loginStaffCode, accessLevel, searchParam);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_BGN_INT_SEARCH, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam));

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#getApBgnInt(java.lang.String)
	 */
	public ApBgnInt getApBgnInt(String applicationId) {
		return getApBgnInt(applicationId, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#getApBgnInt(java.lang.String)
	 */
	public ApBgnInt getApBgnInt(Long eappId) {
		return getApBgnInt(eappId, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#getApBgnInt(java.lang.String, boolean)
	 */
	public ApBgnInt getApBgnInt(String applicationId, boolean lockFlag) {
		try {
			ApBgnIntDAO apBgnIntDAO = new ApBgnIntDAO();

			ApBgnInt apBgnInt = apBgnIntDAO.selectApBgnInt(applicationId, lockFlag); // ヘッダの取得
			if(apBgnInt != null) { // データ有
				apBgnInt.setApBgnIntLineFldList(apBgnIntDAO.selectApBgnIntLineFld(applicationId));			// 資産明細取得
				apBgnInt.setApBgnIntLineProfEstList(apBgnIntDAO.selectApBgnIntLineProfEst(applicationId));	// 利益予測明細取得
				apBgnInt.setApBgnIntLineAttList(apBgnIntDAO.selectApBgnIntLineAtt(applicationId));			// 添付明細取得
				apBgnInt.setApBgnIntLineCostSecList(apBgnIntDAO.selectApBgnIntLineCostSec(applicationId));	// 経費負担明細取得
			}

			return apBgnInt;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)データの取得"), e);
		}
	}

	/**
	 * 申請情報取得
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private ApBgnInt getApBgnInt(Long eappId, boolean lockFlag) {
		try {
			ApBgnIntDAO apBgnIntDAO = new ApBgnIntDAO();

			ApBgnInt apBgnInt = apBgnIntDAO.selectApBgnInt(eappId, lockFlag); // ヘッダの取得
			if(apBgnInt != null) {
				return getApBgnInt(apBgnInt.getApplicationId(), lockFlag);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)データの取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#getApBgnIntByApGetInt(java.lang.String, java.lang.String)
	 */
	public ApBgnInt getApBgnIntByApGetInt(String apGetIntId, String apGetIntVersion) {
		try{
			ApBgnIntDAO apBgnIntDAO = new ApBgnIntDAO();

			ApBgnInt apBgnInt = apBgnIntDAO.selectApBgnIntByApGetInt(apGetIntId, apGetIntVersion);
			if(apBgnInt != null) { // データ有
				apBgnInt.setApBgnIntLineFldList(getPpfsFldList(apBgnInt.getApCompanyCode(), apGetIntId, "1"));										// 資産明細取得
				apBgnInt.setApBgnIntLineProfEstList(apBgnIntDAO.selectApBgnIntLineProfEstByApGetInt(apGetIntId, apGetIntVersion));	// 利益予測明細取得
				apBgnInt.setApBgnIntLineAttList(apBgnIntDAO.selectApBgnIntLineAttByApGetInt(apGetIntId, apGetIntVersion));			// 添付明細取得
				apBgnInt.setApBgnIntLineCostSecList(apBgnIntDAO.selectApBgnIntLineCostSecByApGetInt(apGetIntId, apGetIntVersion));	// 経費負担明細取得
			}

			return apBgnInt;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告データの取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#getPpfsFldList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<ApBgnIntLineFld> getPpfsFldList(String companyCode, String applicationId, String lineType) {
		try{
			List<ApBgnIntLineFld> fldList = new ArrayList<ApBgnIntLineFld>();

			PpfsFldSC searchParam = new PpfsFldSC();

			searchParam.setSkkshisankbn(Constants.PPFS_FLD_SKKSHISANKBN_INT1 + " " + Constants.PPFS_FLD_SKKSHISANKBN_INT2);										// 無形
			searchParam.setShisanknrkbn("1");										// 仮勘定
			searchParam.setShisanjotaikbn("1");										// 通常
			searchParam.setApplicationIdPlural(applicationId);						// 取得申請書番号

			List<PpfsFldSR> ppfsFldList = fldSession.searchFld(null, null, companyCode, searchParam);
			if(ppfsFldList != null){
				for(int i = 0; i < ppfsFldList.size(); i++){
					PpfsFldSR ppfsFldData = ppfsFldList.get(i);

					ApBgnIntLineFld fldData = new ApBgnIntLineFld();
					fldData.setApplicationId(applicationId);						// 申請書番号
					fldData.setLineType(lineType);									// 明細区分 1:申請時,2:承認時追加分
					fldData.setPpId(ppfsFldData.getKoyuno());						// 固有番号 ProPlusの資産台帳キー
					fldData.setAstDate(Function.getDateFromStr(ppfsFldData.getKeijoymdL(), "yyyy/MM/dd"));	// 計上日
					fldData.setAstNum(ppfsFldData.getShisanNum());					// 資産番号
					fldData.setAstName(ppfsFldData.getShisannm());					// 資産名
					fldData.setAstGetAmount(ppfsFldData.getStkgkz());				// 取得価額 仮勘定の税法帳簿取得価額
					fldData.setAstAmount(ppfsFldData.getStkgkk());					// 資産計上額 仮勘定の会社帳簿取得価額
					fldData.setAstSectionCode(ppfsFldData.getSoshiki2cd());			// 資産計上部課
					fldData.setAstSectionName(ppfsFldData.getSoshiki2nm());			// 資産計上部課名
					fldData.setAstRouteFlag(ppfsFldData.getRouteType());			// 経路フラグ
					fldData.setAstRouteName(ppfsFldData.getRouteTypeName());		// 経路フラグ名
					fldData.setAstPurCompanyCode(ppfsFldData.getPurCompanyCode());	// 仕入先コード
					fldData.setAstPurCompanyName(ppfsFldData.getPurCompanyName());	// 仕入先名
					fldData.setAstSlipNum(ppfsFldData.getSlipNum());				// 伝票番号
					fldData.setAstPrjCode(ppfsFldData.getAstPrjCode());				// 資産プロジェクトコード
					fldData.setAstPrjName(ppfsFldData.getAstPrjName());				// 資産プロジェクト名

					fldList.add(fldData);
				}
			}
			return fldList;
		} catch (Exception e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告:資産明細データの取得"), e);
		}
	}

	/**
	 * 不正セット項目値の調整
	 * @param obj 情報機器等データ
	 * @throws AppException
	 */
	private void setPropertyAdjust(ApBgnInt obj) throws AppException {

		// 取得形態
		String apGetType = Function.nvl(obj.getApGetType(), "");
		// 分類
		String mktCatCategoryCode = Function.nvl(obj.getMktCatCategoryCode(), "");

		String apBgnType = "";
		if(apGetType.equals(Constants.AP_GET_TYPE_INT1)){
			apBgnType = Constants.AP_BGN_TYPE_INT1;		// 社内使用ソフトウェア
		}
		else if(apGetType.equals(Constants.AP_GET_TYPE_INT2)){
			if(mktCatCategoryCode.equals("6")){
				apBgnType = Constants.AP_BGN_TYPE_INT3;	// 研究開発
			}
			else{
				apBgnType = Constants.AP_BGN_TYPE_INT2;	// 市販目的ソフトウェア
			}
		}

		// フラグがNULLの場合デフォルトセット
		if(obj.getApSkipApproveFlag() == null) obj.setApSkipApproveFlag(Constants.FLAG_NO);

		// 社内使用ソフトウェアに応じた情報のクリア
		if(apBgnType.equals(Constants.AP_BGN_TYPE_INT1)){
			obj.setMktCatCategoryCode(null);					// 分類

			if(!Function.nvl(obj.getAstCloudType(), "").equals(Constants.CLOUD_TYPE_CLOUD)){
				obj.setAstPrjLife(null);						// プロジェクトライフ
			}
		}
		else{
			obj.setAstCloudType(null);							// クラウド区分
			obj.setAstGroupCode(null);							// 案件グループ
			obj.setAstPrjLife(null);							// プロジェクトライフ
			obj.setHolRepOfficeCode(null);						// 代表設置場所
		}

		// 市販目的ソフトウェアに応じた情報のクリア
		if(apBgnType.equals(Constants.AP_BGN_TYPE_INT2)){
			obj.setAstAppAmount(null);							// 資産計上額合計(申請額)
			obj.setAstDescription(null);						// 証憑の内容説明・内容・成果

			// 保守
			if(mktCatCategoryCode.equals("3")){
				obj.setApBgnIntLineFldList(null);				// 資産明細
				obj.setApBgnIntLineProfEstList(null);			// 見込販売計画明細
			}
		}
		else{
			obj.setAstMachineCode(null);						// 機種コード
			obj.setAstProductCode(null);						// プロダクトコード
			obj.setApBgnIntLineProfEstList(null);				// 見込販売計画明細
		}

		// 研究開発に応じた情報のクリア
		if(apBgnType.equals(Constants.AP_BGN_TYPE_INT3)){
			obj.setApBgnIntLineFldList(null);					// 資産明細
			obj.setCostType(null);
			obj.setCostDepPrjCode(null);
			obj.setCostDepPrjName(null);
			obj.setCostCompanyCode(null);
			obj.setCostCompanyName(null);
			obj.setCostSectionCode(null);
			obj.setCostSectionYear(null);
			obj.setCostSectionName(null);
			obj.setApBgnIntLineCostSecList(new ArrayList<ApBgnIntLineCostSec>());
		}
		else{
			// 原価以外はプロジェクト項目クリア
			if(!Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)) {
				obj.setCostDepPrjCode(null);
				obj.setCostDepPrjName(null);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#createApBgnInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnInt)
	 */
	public String createApBgnInt(String loginStaffCode, String accessLevel, ApBgnInt obj) throws AppException {
		return createApBgnInt(loginStaffCode, accessLevel, obj, true);
	}

	/**
	 * サービス提供開始報告作成本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj サービス提供開始報告データ
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータのサービス提供開始報告書番号
	 */
	private String createApBgnInt(String loginStaffCode, String accessLevel, ApBgnInt obj, boolean isHistCreate) throws AppException {
		try {
			ApBgnIntDAO apBgnIntDAO = new ApBgnIntDAO();

			//////////////////////////////////// 固定値セット
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setCreateDate(sysdate);
			obj.setCreateStaffCode(loginStaffCode);
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			errMsg.append(validateApBgnInt(loginStaffCode, accessLevel, obj));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ作成
			// バージョン・改訂番号
			int version = 1;
			List<Hist> histList = histSession.getHistList(HIST_ENTITY_NAME, obj.getApplicationId());
			if(histList != null && histList.size() > 0){
				Hist histData = (Hist)histList.get(0);
				Integer verNum = histData.getVersion();
				if(verNum != null){
					version = verNum.intValue() + 1;
				}
			}
			obj.setVersion(version);
			obj.setDisplayVersion(1);

			apBgnIntDAO.insertApBgnInt(obj); // ヘッダ作成
			createLine(loginStaffCode, obj, apBgnIntDAO); // 明細データ作成

			 // 取得申請(無形)のサービス提供開始報告完了フラグを完了に更新する
			String applicationId = obj.getApplicationId();
			String applicationVersion = obj.getApplicationVersion();
			ApGetInt apGetIntData = apGetIntSession.getApGetInt(applicationId, applicationVersion);
			if(apGetIntData != null){
				apGetIntData.setUpdateDate(sysdate);
				apGetIntData.setUpdateStaffCode(loginStaffCode);
				apGetIntData.setReportCompleteFlag("1");
				apGetIntSession.updateApGetInt(apGetIntData);
			}

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				String histId = obj.getApplicationId();
				histSession.createHistData(HIST_ENTITY_NAME, histId, HIST_OPERATION_CREATE, null);
			}

			return obj.getApplicationId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)作成:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#updateApBgnInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnInt)
	 */
	public void updateApBgnInt(String loginStaffCode, String accessLevel, ApBgnInt obj) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateApBgnInt(loginStaffCode, accessLevel, obj, true, true, true, null);
	}

	/**
	 * サービス提供開始報告更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj サービス提供開始報告データ
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isDisplayVersionUpdate 改訂番号更新有無 true:改訂番号をインクリメント false:改訂番号は更新しない
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @param operation 履歴作成時のオペレーション
	 * @throws AppException
	 */
	private void updateApBgnInt(String loginStaffCode, String accessLevel, ApBgnInt obj, boolean isLineUpdate, boolean isDisplayVersionUpdate, boolean isHistCreate, String operation) throws AppException {
		try {
			ApBgnIntDAO apBgnIntDAO = new ApBgnIntDAO();
			ApBgnInt apBgnIntOld = getApBgnInt(obj.getApplicationId(), true); // 現データの取得(データロック)

			//////////////////////////////////// 固定値セット
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != apBgnIntOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// バリデーション(登録画面更新の際のみ：連携等による更新時は行わない)
			if(isLineUpdate) errMsg.append(validateApBgnInt(loginStaffCode, accessLevel, obj));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新
			// バージョン・改訂番号
			obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
			if(isDisplayVersionUpdate) obj.setDisplayVersion(Function.nvl(obj.getDisplayVersion(), Integer.valueOf(1)) + 1);

			apBgnIntDAO.updateApBgnInt(obj);

			if(isLineUpdate) {
				// 明細を一度削除
				deleteLine(loginStaffCode, obj, apBgnIntDAO);

				createLine(loginStaffCode, obj, apBgnIntDAO); // 明細データ作成
			}

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				StringBuffer lineChangeColumnName = new StringBuffer();
				if(isLineUpdate) {
					// 明細変更確認
					if(Function.isListChange(obj.getApBgnIntLineFldList(), apBgnIntOld.getApBgnIntLineFldList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("資産明細");
					}
					if(Function.isListChange(obj.getApBgnIntLineProfEstList(), apBgnIntOld.getApBgnIntLineProfEstList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("利益予測明細");
					}
					if(Function.isListChange(obj.getApBgnIntLineAttList(), apBgnIntOld.getApBgnIntLineAttList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("添付明細");
					}
					if(Function.isListChange(obj.getApBgnIntLineCostSecList(), apBgnIntOld.getApBgnIntLineCostSecList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("経費負担部署明細");
					}
				}

				String histId = obj.getApplicationId();
				histSession.createHistData(HIST_ENTITY_NAME, histId, (operation == null ? HIST_OPERATION_UPDATE : operation), lineChangeColumnName.toString());
			}
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)更新:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#applyApBgnInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_tan.ApBgnInt, java.lang.Boolean)
	 */
	public String applyApBgnInt(String loginStaffCode, String accessLevel, ApBgnInt obj, Boolean isNew) throws AppException {
		String ret = null;

		//////////////////////////////////// 新規 or 更新呼び出し
		if(isNew) { // 新規
			ret = createApBgnInt(loginStaffCode, accessLevel, obj, false);
		} else { // 更新
			// 更新コメント
			obj.setUpdateComment(null);

			ret = obj.getApplicationId();

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			// ステータス更新前バリデーション
			String errMsg = validateApBgnInt(loginStaffCode, accessLevel, obj);
			if(errMsg.length() > 0) throw new AppException(errMsg);
		}

		//////////////////////////////////// ステータス更新&ステータス更新後バリデーション
		obj.setApStatus(Constants.AP_STATUS_BGN_INT_APPLY);
		String errMsg = validateApBgnInt(loginStaffCode, accessLevel, obj);
		if(errMsg.length() > 0) throw new AppException(errMsg);

		//////////////////////////////////// 申請
		Long eappId = null;
		eappId = callEappService(obj); // e申請連携

		// e申請IDを更新
		obj.setEappId(eappId);

		if(isNew) { // 新規
			updateApBgnInt(loginStaffCode, accessLevel, obj, false, false, false, null);

			String histId = obj.getApplicationId();
			histSession.createHistData(HIST_ENTITY_NAME, histId, HIST_OPERATION_APPLY, null); // 履歴作成
		} else {
			updateApBgnInt(loginStaffCode, accessLevel, obj, true, true, true, HIST_OPERATION_APPLY);
		}

		return ret;
	}

	/**
	 * e申請サービス呼び出し
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(ApBgnInt obj) throws AppException {
		// e申請WebServiceエンドポイント取得
		CodeName codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_BGN_INT, null, null);
		String eappWsEndpoint = codeNameUrl.getValue1(); // e申請WebSerivceエンドポイント
		String eAssetUrl = codeNameUrl.getValue2(); // e申請インラインフレーム画面表示用のeAssetUrl
		String eappStopMessage = codeNameUrl.getValue3(); // e申請との連携停止期間中のエラーメッセージ

		Long eappId = null;

		if(!Function.nvl(eappWsEndpoint, "").equals("")) { // e申請WebServiceエンドポイントが空白(PG検証用)の場合は連携無し
			// e申請との連携停止期間中のエラーメッセージ
			if(!eappWsEndpoint.startsWith("http")){
				throw new AppException(eappStopMessage);
			}

			try {
				eAssetUrl += "&amp;companyCode=" + obj.getApCompanyCode();
				eAssetUrl += "&amp;param2="; // e申請から書類IDが指定される

				//////////////////////////////////// 経路設定
				// e申請経路担当情報取得
				List<CodeName> codeNameEappChargeList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_BGN_INT, null, obj.getApCompanyCode(), null);
				CodeName codeNameEappCharge = codeNameEappChargeList.get(0);
				CodeName codeNameEappCharge2 = null;
				if(codeNameEappChargeList.size() > 1) codeNameEappCharge2 = codeNameEappChargeList.get(1);

				// e申請経路権限情報取得
				List<String> attributeParam = new ArrayList<String>();
				attributeParam.add(obj.getApGetType());
				CodeName codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_BGN_INT, null, obj.getApCompanyCode(), attributeParam);

				if(codeNameEappRoute == null) codeNameEappRoute = new CodeName();

				// 課長承認不要
				if(Function.nvl(obj.getApSkipApproveFlag(), "").equals(Constants.FLAG_YES)) {
					codeNameEappRoute.setValue3(null); // 課長承認経路削除
				}

				// 経路有無判定
				boolean routeExists = false;

				// 経路パラメータセット
				List<String> applyRouteAuthDispList = new ArrayList<String>();
				List<String> applyRouteChargeDispList = new ArrayList<String>();
				List<String> applyRouteDispTypeList = new ArrayList<String>();
				List<String> approveRouteAuthDispList = new ArrayList<String>();
				List<String> approveRouteChargeDispList = new ArrayList<String>();
				List<String> approveRouteDispTypeList = new ArrayList<String>();
				List<String> acceptRouteAuthDispList = new ArrayList<String>();
				List<String> acceptRouteChargeDispList = new ArrayList<String>();
				List<String> acceptRouteDispTypeList = new ArrayList<String>();

				for(int i = 0; i < Constants.MAX_EAPP_ROUTE_COUNT_AP_BGN_INT; i++) {
					List<String> authDispList;
					List<String> chargeDispList;
					List<String> dispTypeList;

					// 対象経路判別
					if(i <= 1) {								// 申請部経路
						authDispList = applyRouteAuthDispList;
						chargeDispList = applyRouteChargeDispList;
						dispTypeList = applyRouteDispTypeList;
					} else if(2 <= i && i <= 3) {				// 受付経路
						authDispList = acceptRouteAuthDispList;
						chargeDispList = acceptRouteChargeDispList;
						dispTypeList = acceptRouteDispTypeList;
					} else {									// 承認経路
						authDispList = approveRouteAuthDispList;
						chargeDispList = approveRouteChargeDispList;
						dispTypeList = approveRouteDispTypeList;
					}

					Object auth = PropertyUtils.getProperty(codeNameEappRoute, "value" + (i + 3)); // 申請経路はvalue3～
					if(!Function.nvl(auth,"").equals("")) {
						Object charge = null;
						authDispList.add(auth.toString());

						if(auth.toString().equals(Constants.EAPP_ROUTE_DISP_TYPE_CONFIRM_NAME)) { // 確認経路
							charge = PropertyUtils.getProperty(codeNameEappCharge, "value" + (i + 3)); // 申請経路はvalue3～
							dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_CONFIRM);
						} else if(auth.toString().equals(Constants.EAPP_ROUTE_DISP_TYPE_APPROVE2_NAME)) { // 承認経路2
							charge = PropertyUtils.getProperty(codeNameEappCharge2, "value" + (i + 3)); // 申請経路はvalue3～
							dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_APPROVE2);
						} else { // 承認・受付経路
							charge = PropertyUtils.getProperty(codeNameEappCharge, "value" + (i + 3)); // 申請経路はvalue3～
							dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_APPROVE);
						}

						chargeDispList.add(charge.toString());

						routeExists = true;
					} else { // 経路無し
						authDispList.add(null);
						chargeDispList.add(null);
						dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_NONE);
					}
				}

				if(routeExists) {
					//////////////////////////////////// e申請サービス呼び出し
					String eappIdStr = null;
					try {
						ApBgnIntService service = new ApBgnIntServiceProxy(eappWsEndpoint);
						eappIdStr = service.apply(
								obj.getApplicationId()												// applicationId
								, Constants.AP_TYPE_BGN_INT											// applicationType
								, obj.getApCompanyCode()											// companyCode
								, obj.getApSectionCode()											// apSectionCode
								, obj.getApCreateStaffCode()										// apCreateStaffCode
								, obj.getApStaffCode()												// apStaffCode
								, obj.getApTel()													// apTel
								, "\\n" + Constants.AP_TITLE_BGN_INT								// apTitle
								, "(" + obj.getApGetTypeName() + ")"								// apSubTitle
								, Constants.AP_TITLE_BGN_INT + " (" + obj.getApGetTypeName() + ")"	// apListTitle
								, eAssetUrl															// eAssetUrl
								, applyRouteAuthDispList.toArray(new String[0])						// applyRouteAuthDispArray
								, applyRouteChargeDispList.toArray(new String[0])					// applyRouteChargeDispArray
								, applyRouteDispTypeList.toArray(new String[0])						// applyRouteDispTypeArray
								, approveRouteAuthDispList.toArray(new String[0])					// approveRouteAuthDispArray
								, approveRouteChargeDispList.toArray(new String[0])					// approveRouteChargeDispArray
								, approveRouteDispTypeList.toArray(new String[0])					// approveRouteDispTypeArray
								, acceptRouteAuthDispList.toArray(new String[0])					// acceptRouteAuthDispArray
								, acceptRouteChargeDispList.toArray(new String[0])					// acceptRouteChargeDispArray
								, acceptRouteDispTypeList.toArray(new String[0])					// acceptRouteDispTypeArray
								, null																// specialApproveDate
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
							Long getEAppIdService = eAssetService.getEAppId(obj.getApplicationId());
							//	nullの場合エラー
							if(Function.nvl(getEAppIdService, "").equals("")){
								throw new AppException("e申請との連携処理中に予期せぬエラーが発生しました。\n画面下の「保存」ボタンを押してデータ保存した後\ne申請を開いて申請情報が作成されているか、ご確認下さい。\n\ne申請に申請情報が作成されていた場合、\nその申請の「書類ID」と「書類管理番号」をeAsset管理者宛へご連絡下さい。\n", e);
							}else{
								eappIdStr = String.valueOf(getEAppIdService);
							}
						} catch (Exception exception) {
							Logging.error(e.getMessage(), e);
							throw new AppException("e申請との連携処理中に予期せぬエラーが発生しました。\n画面下の「保存」ボタンを押してデータ保存した後\ne申請を開いて申請情報が作成されているか、ご確認下さい。\n\ne申請に申請情報が作成されていた場合、\nその申請の「書類ID」と「書類管理番号」をeAsset管理者宛へご連絡下さい。\n", exception);
						}
					} catch (Exception e) {
						Logging.error(e.getMessage(), e);
						throw new AppException("e申請連携処理中に予期せぬエラーが発生しました。\n作業状態を保存するには「保存」ボタンをクリックしてください。\n", e);
					}

					if(Function.nvl(eappIdStr, "").equals("")) { // レスポンス無しエラー
						throw new AppException("e申請連携処理中に予期せぬエラーが発生しました。\n作業状態を保存するには「保存」ボタンをクリックしてください。\n\n(e申請書類IDが空白です)。");
					} else if(eappIdStr.startsWith("e")) { // e申請側アプリケーションエラー
						throw new AppException("e申請連携処理中に以下のエラーが発生しました。\n作業状態を保存するには「保存」ボタンをクリックしてください。\n" + eappIdStr.substring(1));
					} else {
						eappId = Long.valueOf(eappIdStr);
					}
				}

			} catch (IllegalAccessException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請連携パラメータ作成"), e);
			} catch (InvocationTargetException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請連携パラメータ作成"), e);
			} catch (NoSuchMethodException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請連携パラメータ作成"), e);
			}
		}

		return eappId;
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#deleteApBgnInt(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_bgn_int.ApBgnInt)
	 */
	public void deleteApBgnInt(String loginStaffCode, String accessLevel, ApBgnInt obj) throws AppException {
		try {
			ApBgnIntDAO apBgnIntDAO = new ApBgnIntDAO();
			ApBgnInt apBgnIntOld = getApBgnInt(obj.getApplicationId(), true); // 現データの取得(データロック)

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != apBgnIntOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新(履歴作成用にバージョンアップ)
			// 現データを更新に使用
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			apBgnIntOld.setUpdateDate(sysdate);
			apBgnIntOld.setUpdateStaffCode(loginStaffCode);

			// バージョン・改訂番号
			apBgnIntOld.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
			apBgnIntOld.setDisplayVersion(Function.nvl(obj.getDisplayVersion(), Integer.valueOf(1)) + 1);

			// 更新コメント
			apBgnIntOld.setUpdateComment(null);

			apBgnIntDAO.updateApBgnInt(apBgnIntOld);

			//////////////////////////////////// 履歴作成
			String histId = obj.getApplicationId();
			histSession.createHistData(HIST_ENTITY_NAME, histId, HIST_OPERATION_DELETE, null);

			//////////////////////////////////// データ削除
			apBgnIntDAO.deleteApBgnInt(obj.getApplicationId());
			deleteLine(loginStaffCode, obj, apBgnIntDAO);

			 // 取得申請(無形)のサービス提供開始報告完了フラグを未登録に更新する
			String applicationId = obj.getApplicationId();
			String applicationVersion = obj.getApplicationVersion();
			ApGetInt apGetIntData = apGetIntSession.getApGetInt(applicationId, applicationVersion);
			if(apGetIntData != null){
				apGetIntData.setUpdateDate(sysdate);
				apGetIntData.setUpdateStaffCode(loginStaffCode);
				apGetIntData.setReportCompleteFlag("0");
				apGetIntSession.updateApGetInt(apGetIntData);
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "サービス提供開始報告(無形)削除"), e);
		}
	}

	/*
	 * 明細データの作成
	 */
	private void createLine(String loginStaffCode, ApBgnInt obj, ApBgnIntDAO apBgnIntDAO) throws SQLException, IOException {
		Date sysdate = new Date(); // システム日付取得

		// 資産明細作成
		List<ApBgnIntLineFld> fldList = obj.getApBgnIntLineFldList();
		if(fldList != null && fldList.size() > 0) {
			int lineType1Seq = 1;
			int lineType2Seq = 1;
			for(int i = 0; i < fldList.size(); i++) {
				ApBgnIntLineFld row = fldList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				if(Function.nvl(row.getLineType(), "1").equals("1")){
					row.setLineType("1");
					row.setLineSeq(lineType1Seq++); // 行シーケンスの振り直し(念のため)
				}
				else{
					row.setLineType("2");
					row.setLineSeq(lineType2Seq++); // 行シーケンスの振り直し(念のため)
				}

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apBgnIntDAO.insertApBgnIntLineFld(row);
			}
		}

		// 利益予測明細作成
		List<ApBgnIntLineProfEst> profEstList = obj.getApBgnIntLineProfEstList();
		if(profEstList != null && profEstList.size() > 0) {
			for(int i = 0; i < profEstList.size(); i++) {
				ApBgnIntLineProfEst row = profEstList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apBgnIntDAO.insertApBgnIntLineProfEst(row);
			}
		}

		// 添付明細作成
		List<ApBgnIntLineAtt> attList = obj.getApBgnIntLineAttList();
		if(attList != null && attList.size() > 0) {
			for(int i = 0; i < attList.size(); i++) {
				ApBgnIntLineAtt row = attList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				if(row.getAttFileId() == null) { // 一時領域にのみファイルが存在する（保存前）
					// 一時領域 ⇒ 本領域にファイルコピー
					masterSession.fileCommit(row.getAttFileIdTmp(), FILE_SAVE_ENTITY_NAME, obj.getApplicationId());
					row.setAttFileId(row.getAttFileIdTmp());
				}

				apBgnIntDAO.insertApBgnIntLineAtt(row);
			}
		}

		// 経費負担部署明細作成
		List<ApBgnIntLineCostSec> costSecList = obj.getApBgnIntLineCostSecList();
		if(costSecList != null && costSecList.size() > 0) {
			for(int i = 0; i < costSecList.size(); i++) {
				ApBgnIntLineCostSec row = costSecList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apBgnIntDAO.insertApBgnIntLineCostSec(row);
			}
		}
	}

	/*
	 * 明細データの削除
	 */
	private void deleteLine(String loginStaffCode, ApBgnInt obj, ApBgnIntDAO apBgnIntDAO) throws SQLException {
		apBgnIntDAO.deleteApBgnIntLineFld(obj.getApplicationId());
		apBgnIntDAO.deleteApBgnIntLineProfEst(obj.getApplicationId());
		apBgnIntDAO.deleteApBgnIntLineAtt(obj.getApplicationId());
		apBgnIntDAO.deleteApBgnIntLineCostSec(obj.getApplicationId());
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#createApBgnIntPdf(java.util.List)
	 */
	public String createApBgnIntPdf(List<String> applicationList) {
		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();

		reportParam.put("applicationIdWhere", Function.getInCondition("nabi.APPLICATION_ID", applicationList, true));

		// PDF生成
		PdfExporter exporter = new PdfExporter();

		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApBgnInt.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#createApBgnIntHistPdf(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public String createApBgnIntHistPdf(String applicationId, Integer version) {
		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();

		reportParam.put("applicationIdWhere", "nabi.APPLICATION_ID = '" + applicationId + "'");
		reportParam.put("version", version);
		reportParam.put("lineOutputFlag", "1");

		// PDF生成
		PdfExporter exporter = new PdfExporter();

		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApBgnInt.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#approveApBgnInt(java.lang.Long, java.lang.String)
	 */
	public void approveApBgnInt(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApBgnInt apBgnInt = getApBgnInt(eappId, true);

		CodeName appRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_BGN_INT, null, apBgnInt.getApCompanyCode(), null);

		// サービス提供開始報告をe申請で承認する会社は承認済に更新
		if(appRoute != null && Function.nvl(appRoute.getValue2(), "").equals(Constants.FLAG_YES)) {
			// ステータス
			apBgnInt.setApStatus(Constants.AP_STATUS_BGN_INT_APPROVE);
			// 更新コメント
			apBgnInt.setUpdateComment(null);
			// 承認日
			apBgnInt.setApproveDate(new Date());

			updateApBgnInt(execStaffCode, null, apBgnInt, false, false, true, HIST_OPERATION_APPROVE);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#cancelApplyApBgnInt(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyApBgnInt(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApBgnInt apBgnInt = getApBgnInt(eappId, true);

		if(apBgnInt != null) {
			// ステータス
			apBgnInt.setApStatus(Constants.AP_STATUS_BGN_INT_NOAPPLY);
			// 更新コメント
			apBgnInt.setUpdateComment(null);
			apBgnInt.setEappId(null); // 書類IDクリア

			updateApBgnInt(execStaffCode, null, apBgnInt, false, false, true, HIST_OPERATION_CANCEL_APPLY);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApBgnIntSession#rejectApBgnInt(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectApBgnInt(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		String histOperation;

		//////////////////////////////////// 更新
		ApBgnInt apBgnInt = getApBgnInt(eappId, true);

		if(apBgnInt != null) {
			// ステータス
			if(Function.nvl(rejectType, "").equals(Constants.AP_REJECT_TYPE_REJECT)) { // 却下
				apBgnInt.setApStatus(Constants.AP_STATUS_BGN_INT_REJECT);
				histOperation = HIST_OPERATION_REJECT;
			} else { // 差戻し
				apBgnInt.setApStatus(Constants.AP_STATUS_BGN_INT_SENDBACK);
				apBgnInt.setEappId(null); // 書類IDクリア
				histOperation = HIST_OPERATION_SENDBACK;
			}

			// コメント
			apBgnInt.setUpdateComment(rejectReason);

			updateApBgnInt(execStaffCode, null, apBgnInt, false, false, true, histOperation);
		}
	}

	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 * @throws AppException
	 */
	private String validateApBgnInt(String loginStaffCode, String accessLevel, ApBgnInt obj) throws AppException {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 項目定義バリデーション
		// 取得形態
		String apGetType = Function.nvl(obj.getApGetType(), "");
		// 分類
		String mktCatCategoryCode = Function.nvl(obj.getMktCatCategoryCode(), "");

		String apBgnType = "";
		if(apGetType.equals(Constants.AP_GET_TYPE_INT1)){
			apBgnType = Constants.AP_BGN_TYPE_INT1;		// 社内使用ソフトウェア
		}
		else if(apGetType.equals(Constants.AP_GET_TYPE_INT2)){
			if(mktCatCategoryCode.equals("6")){
				apBgnType = Constants.AP_BGN_TYPE_INT3;	// 研究開発
			}
			else{
				apBgnType = Constants.AP_BGN_TYPE_INT2;	// 市販目的ソフトウェア
			}
		}

		int apStatus = obj.getApStatus() == null ? null : Integer.valueOf(obj.getApStatus());
		if(!Function.isAccessLevelAdmin(accessLevel)) {			// 全社権限ではない場合
			apStatus += 6;
		}
		if(apBgnType.equals(Constants.AP_BGN_TYPE_INT2)) {		// 市販目的ソフトウェア
			apStatus += 12;
		}
		else if(apBgnType.equals(Constants.AP_BGN_TYPE_INT3)) {	// 研究開発
			apStatus += 24;
		}

		// ヘッダ
		errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_BGN_INT, "NEA_AP_BGN_INT", obj, apStatus, null));

		// 明細
		List<ApBgnIntLineFld> fldLine = obj.getApBgnIntLineFldList();
		if(fldLine != null && fldLine.size() > 0) {
			for(int i = 0; i < fldLine.size(); i++) {
				ApBgnIntLineFld fldObj = fldLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_BGN_INT, "NEA_AP_BGN_INT_LINE_FLD", fldObj, apStatus, null));
			}
		}

		List<ApBgnIntLineProfEst> profEstLine = obj.getApBgnIntLineProfEstList();
		if(profEstLine != null && profEstLine.size() > 0) {
			for(int i = 0; i < profEstLine.size(); i++) {
				ApBgnIntLineProfEst profEstObj = profEstLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_BGN_INT, "NEA_AP_BGN_INT_LINE_PROF_EST", profEstObj, apStatus, null));
			}
		}

		List<ApBgnIntLineAtt> attLine = obj.getApBgnIntLineAttList();
		if(attLine != null && attLine.size() > 0) {
			for(int i = 0; i < attLine.size(); i++) {
				ApBgnIntLineAtt attObj = attLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_BGN_INT, "NEA_AP_BGN_INT_LINE_ATT", attObj, apStatus, null));
			}
		}

		List<ApBgnIntLineCostSec> costSecLine = obj.getApBgnIntLineCostSecList();
		if(costSecLine != null && costSecLine.size() > 0) {
			for(int i = 0; i < costSecLine.size(); i++) {
				ApBgnIntLineCostSec costSecObj = costSecLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_BGN_INT, "NEA_AP_BGN_INT_LINE_COST_SEC", costSecObj, apStatus, null));
			}
		}

		//////////////////////////////////// 条件付必須バリデーション
		String stat = Function.nvl(obj.getApStatus(), ""); // ステータス

		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_BGN_INT_APPROVE) &&
		   !stat.equals(Constants.AP_STATUS_BGN_INT_REJECT) &&
		   !stat.equals(Constants.AP_STATUS_BGN_INT_CANCEL)) {

			// 案件グループ
			if(Function.nvl(obj.getAstCloudType(), "").equals(Constants.CLOUD_TYPE_CLOUD)) {
				if(Function.nvl(obj.getAstGroupCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "資産概要-案件グループ", "クラウド案件の場合は"));
				}
			}

			// 販管／原価区分
			if(!apBgnType.equals(Constants.AP_BGN_TYPE_INT3) &&
			   Function.nvl(obj.getCostType(), "").equals("")) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "経費負担-販売管理費/原価区分"));
			}

			// 原価でプロジェクトコードの指定無し（未定でもない）
			if(Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)
				&& Function.nvl(obj.getCostDepPrjCode(), "").equals("")) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "経費負担-減価償却プロジェクトコード"));
			}

			// 経費負担部署
			if(!apBgnType.equals(Constants.AP_BGN_TYPE_INT3) &&
			   (costSecLine == null || costSecLine.size() == 0)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "経費負担-経費負担部署", "1件以上"));
			}

			if(Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)) {
				if(costSecLine != null && costSecLine.size() > 1) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-経費負担部署", "販売管理費/原価区分に\"原価\"を指定した場合1件しか入力できません。"));
				}
			}
		}

		//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)

		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_BGN_INT_APPROVE) &&
		   !stat.equals(Constants.AP_STATUS_BGN_INT_REJECT) &&
		   !stat.equals(Constants.AP_STATUS_BGN_INT_CANCEL)) {

			// 申請者
			if(!Function.nvl(obj.getApStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_BGN_INT_APPROVE) ||
				   Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_BGN_INT_REJECT) ||
				   Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_BGN_INT_CANCEL)) {
					// 承認済・却下・取下げは退職社員OK
					if(masterSession.getStaff(obj.getApCompanyCode(), obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				} else {
					// 承認済・却下・取下げ以外は退職社員NG
					if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				}
			}

			// 資産プロジェクトコード
			if(!Function.nvl(obj.getAstPrjCode(), "").equals("")) {
				Map<String, Object> lovParam = new HashMap<String, Object>();
				lovParam.put("companyCode", obj.getApCompanyCode());
				lovParam.put("prjCategory", Constants.PROJECT_GATEGORY_AST);
				LovDataEx lovData = masterSession.getLovData("selectProject_LOV", lovParam, obj.getAstPrjCode());
				if(lovData == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "資産概要-資産プロジェクトコード"));
				}
/*
				else{
					//	CTC、CTCTのみ対象
					if(Function.nvl(obj.getApCompanyCode(), "").equals("001") || Function.nvl(obj.getApCompanyCode(), "").equals("017")){
						//	間接？
						if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_INDIRECTION)){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産概要-資産プロジェクトコード", "間接プロジェクトが選択されています。"));
						}
						//	契約？
						if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_CONTRACT)){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産概要-資産プロジェクトコード", "契約プロジェクトが選択されています。"));
						}
					}
				}
*/
			}
			// 案件グループ
			if(!Function.nvl(obj.getAstGroupCode(), "").equals("")) {
				Map<String, Object> lovParam = new HashMap<String, Object>();
				lovParam.put("companyCode", obj.getApCompanyCode());
				if(masterSession.getLovData("selectPpfs_Group_LOV", lovParam, obj.getAstGroupCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "取得-案件グループ"));
				}
			}
			// 減価償却プロジェクトコード
			if(!Function.nvl(obj.getCostDepPrjCode(), "").equals("")) {
				Map<String, Object> lovParam = new HashMap<String, Object>();
				lovParam.put("companyCode", obj.getApCompanyCode());
				lovParam.put("prjCategory", Constants.PROJECT_GATEGORY_COST_DEP);
				LovDataEx lovData = masterSession.getLovData("selectProject_LOV", lovParam, obj.getCostDepPrjCode());
				if(lovData == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担-減価償却プロジェクトコード"));
				}
/*
				else{
					//	CTC、CTCTのみ対象
					if(Function.nvl(obj.getApCompanyCode(), "").equals("001") || Function.nvl(obj.getApCompanyCode(), "").equals("017")){
						//	間接？
						if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_INDIRECTION)){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-減価償却プロジェクトコード", "間接プロジェクトが選択されています。"));
						}
						//	資産？
						if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_ASSET)){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-減価償却プロジェクトコード", "資産プロジェクトが選択されています。"));
						}
					}
				}
*/
			}

			// カレント年度取得
			CodeName currentYearCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
			int currentYear = Integer.valueOf(currentYearCodeName.getValue1());

			// 経費負担部課コード
			if(costSecLine != null && costSecLine.size() > 0) {
				for(int i = 0; i < costSecLine.size(); i++) {
					ApBgnIntLineCostSec costSecObj = costSecLine.get(i);
					if(!Function.nvl(costSecObj.getCostSectionCode(), "").equals("")) {
						boolean currentYearErrorFlag = false;
						// 未申請・申請中
						if(stat.equals(Constants.AP_STATUS_BGN_INT_NOAPPLY) ||
						   stat.equals(Constants.AP_STATUS_BGN_INT_APPLY)) {
							Integer costSectionYear = costSecObj.getCostSectionYear();
							if(costSectionYear == null || costSectionYear.intValue() != currentYear) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "経費負担-経費負担部署[No" + costSecObj.getLineSeq() + "]-経費負担部課 : 当年度の経費負担部課を入力してください。"));
								currentYearErrorFlag = true;
							}
						}
						if(!currentYearErrorFlag && masterSession.getCostSection(costSecObj.getCostCompanyCode(), costSecObj.getCostSectionCode(), null, null) == null) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担-経費負担部署[No" + costSecObj.getLineSeq() + "]-経費負担部課"));
						}
					}
				}
			}
			// 資産計上部課コード
			if(!Function.nvl(obj.getCostSectionCode(), "").equals("")) {
				boolean currentYearErrorFlag = false;
				// 未申請・申請中
				if(stat.equals(Constants.AP_STATUS_BGN_INT_NOAPPLY) ||
				   stat.equals(Constants.AP_STATUS_BGN_INT_APPLY)) {
					Integer costSectionYear = obj.getCostSectionYear();
					if(costSectionYear == null || costSectionYear.intValue() != currentYear) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "経費負担-資産計上部課コード : 当年度の経費負担部課を入力してください。"));
						currentYearErrorFlag = true;
					}
				}
				if(!currentYearErrorFlag && masterSession.getCostSection(obj.getCostCompanyCode(), obj.getCostSectionCode(), null, null) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "経費負担-資産計上部課コード"));
				}
			}
		}

		// 無形管理担当者
		if(!Function.nvl(obj.getHolStaffCode(), "").equals("")) {
			if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_BGN_INT_APPROVE) ||
			   Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_BGN_INT_REJECT) ||
			   Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_BGN_INT_CANCEL)) {
					// 承認済・却下・取下げは退職社員OK
				// 承認済・却下・取下げ以外は退職社員NG
				if(masterSession.getStaff(obj.getApCompanyCode(), obj.getHolStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有-無形管理担当者"));
				}
			} else {
				// 承認済・却下・取下げ以外は退職社員NG
				if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getHolStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有-無形管理担当者"));
				}
			}
		}

		//////////////////////////////////// その他バリデーション

		// 社内使用ソフトウェア
		if(apGetType.equals(Constants.AP_GET_TYPE_INT1)){
		}

		// 市販目的ソフトウェア
		if(apGetType.equals(Constants.AP_GET_TYPE_INT2)){

		}

		// 経費負担部課重複・配分合計
		if(costSecLine != null && costSecLine.size() > 0) {
			Set<String> sectionCodeSet = new HashSet<String>();
			int distTotal = 0;
			double adjustNum = 10;
			boolean isDup = false; // 重複フラグ
			for(int i = 0; i < costSecLine.size(); i++) {
				ApBgnIntLineCostSec costSecObj = costSecLine.get(i);
				if(sectionCodeSet.contains(costSecObj.getCostSectionCode())) { // 経費負担部課の重複設定有
					isDup = true;
				}
				sectionCodeSet.add(costSecObj.getCostSectionCode());
				distTotal += (int) (Function.nvl(costSecObj.getCostDist(), Double.valueOf(0)).doubleValue() * adjustNum);
			}

			if(isDup) { // 重複有
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-経費負担部署", "経費負担部課コードが重複しています。"));
			}
			if(distTotal != (100 * adjustNum)) { // 配分合計 != 100
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-経費負担部署", "配分の合計が100%になっていません。"));
			}
		}

		// eAssetとProPlusの資産数をチェック
		if(!fldSession.checkFldCount(obj.getApplicationId())){
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "eAssetの資産情報が最新ではありません。\n管理者にお問合せください。"));
		}

		return errMsg.toString();
	}
}