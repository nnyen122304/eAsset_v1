/*===================================================================
 * ファイル名 : AssetSessionBean.java
 * 概要説明   : 情報機器等、情報機器等登録申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
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
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.jms.ConnectionFactory;
import javax.jms.Queue;

import jp.co.ctcg.easset.dao.AssetDAO;
import jp.co.ctcg.easset.dao.EGuardLdapDAO;
import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.LovDataEx;
import jp.co.ctcg.easset.dto.Section;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineAst;
import jp.co.ctcg.easset.dto.asset.Asset;
import jp.co.ctcg.easset.dto.asset.AssetLineComUsr;
import jp.co.ctcg.easset.dto.asset.AssetLineInv;
import jp.co.ctcg.easset.dto.asset.AssetLineNetwork;
import jp.co.ctcg.easset.dto.asset.AssetLineOs;
import jp.co.ctcg.easset.dto.asset.AssetSC;
import jp.co.ctcg.easset.dto.asset.AssetSR;
import jp.co.ctcg.easset.dto.hist.BulkUpdateHist;
import jp.co.ctcg.easset.dto.license.LicenseAlloc;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.mdb.BulkUpdateMDBean;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvReaderRowHandler;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;
import jp.co.ctcg.easset.ws.ApAssetService;
import jp.co.ctcg.easset.ws.ApAssetServiceProxy;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;

import org.apache.commons.lang3.StringUtils;

import org.apache.commons.beanutils.PropertyUtils;

@Stateless
public class AssetSessionBean implements AssetSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession; // マスタセッション

	@EJB
	HistSession histSession; // 履歴セッション

	@EJB
	ApGetTanSession apGetTanSession; // 取得申請セッション

	@EJB
	LicenseSession licenseSession; // ライセンスセッション

	@EJB
	AssetSession childAssetSession; // 情報機器等セッション（別トランザクション実行用）

	@Resource(mappedName = "java:/jms/queue/BulkUpdateQueue")
	private Queue bulkUpdateQueue; // 一括更新実行用キュー

	@Resource(mappedName = "java:/jms/BulkUpdateQueueFactory" )
	private ConnectionFactory bulkUpdateQueueFactory; // 一括更新実行用キュー接続ファクトリ

	private static final String ID_PREFIX = "H";

	// 履歴作成用
	private static final String HIST_ENTITY_NAME = "ASSET";
	private static final String HIST_OPERATION_CREATE = "新規作成";
	private static final String HIST_OPERATION_UPDATE = "更新";
	private static final String HIST_OPERATION_DELETE = "抹消";
	private static final String HIST_OPERATION_RESTORE = "抹消取消";
	private static final String HIST_OPERATION_APPLY = "申請";
	private static final String HIST_OPERATION_RESERVE = "作業保留";
	private static final String HIST_OPERATION_SENDBACK = "差戻し";
	private static final String HIST_OPERATION_CANCEL_APPLY = "引戻し";
	private static final String HIST_OPERATION_CREATE_ASSET = "情報機器等新規登録";

	// 取得申請側の履歴
	private static final String HIST_OPERATION_CREATE_AP_ASSET = "登録申請作成(資産)";
	private static final String HIST_OPERATION_DELETE_AP_ASSET = "登録申請削除(資産)";

	// 登録申請用
	private static final String HIST_ENTITY_NAME_AP_PREFIX = "AP_";
	private static final String ID_PREFIX_AP_SUFFIX = "W";


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#searchAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.AssetSC, boolean)
	 */
	public List<AssetSR> searchAsset(String loginStaffCode, String accessLevel, AssetSC searchParam, boolean isAp) {
		try {
			AssetDAO assetDAO = new AssetDAO();
			replacePluralSearchCondition(searchParam);
			return assetDAO.selectAssetList(loginStaffCode, accessLevel, searchParam, isAp);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等検索" + (isAp ? "(登録申請)" : "")), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createAssetCsv(java.lang.String, java.lang.String, java.util.List, jp.co.ctcg.easset.dto.asset.AssetSC, boolean)
	 */
	public String createAssetCsv(String loginStaffCode, String accessLevel, List<String> outputPropList, AssetSC searchParam, boolean isAp) {
		try {
			AssetDAO assetDAO = new AssetDAO();
			replacePluralSearchCondition(searchParam);

			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = assetDAO.createAssetListCsv(loginStaffCode, accessLevel, outputPropList, searchParam, isAp);

			//////////////////////////////////// 操作ログ作成
			StringBuffer propStr = new StringBuffer();
			if(outputPropList != null) {
				for(int i = 0; i < outputPropList.size(); i++) {
					if(propStr.length() > 0) propStr.append(" ");
					propStr.append(outputPropList.get(i));
				}
			}
			histSession.createOpLog(loginStaffCode, (isAp ? Constants.COM_OP_FUNCTION_AP_ASSET_SEARCH : Constants.COM_OP_FUNCTION_ASSET_SEARCH), Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam) + ",outputProperty:" + propStr.toString());

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器" + (isAp ? "(登録申請)" : "") + "ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器" + (isAp ? "(登録申請)" : "") + "ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createAllocAssetCsv(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.AssetSC)
	 */
	public String createAllocAssetCsv(String loginStaffCode, String accessLevel, AssetSC searchParam) {
		try {
			AssetDAO assetDAO = new AssetDAO();
			replacePluralSearchCondition(searchParam);

			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = assetDAO.createAllocAssetListCsv(loginStaffCode, accessLevel, searchParam);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_LICENSE_REPORT_ALLOC_ASSET, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam));

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "割当情報(機器から検索)" + "ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "割当情報(機器から検索)" + "ダウンロード"), e);
		}
	}

	/**
	 * 検索条件の名称複数入力項目の名称をコードに変換する。
	 * @param searchParam
	 */
	private void replacePluralSearchCondition(AssetSC searchParam) {
		// 資産分類 名称⇒コード
		List<String> astCategory2PluralList = Function.getPluralList(searchParam.getAstCategory2Plural());
		if(astCategory2PluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < astCategory2PluralList.size(); i++) {
				if(!Function.nvl(astCategory2PluralList.get(i), "").equals("")) {
					String internalCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, null, astCategory2PluralList.get(i));
					if(!Function.nvl(internalCode, "").equals("")) {
						replaceStr.append(internalCode + " ");
					} else {
						replaceStr.append("--------NOT_EXISTS--------" + " ");
					}
				}
			}

			searchParam.setAstCategory2Plural(replaceStr.toString());
		}

		// 資産区分 名称⇒コード
		List<String> astAssetTypePluralList = Function.getPluralList(searchParam.getAstAssetTypePlural());
		if(astAssetTypePluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < astAssetTypePluralList.size(); i++) {
				if(!Function.nvl(astAssetTypePluralList.get(i), "").equals("")) {
					String internalCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_TYPE, null, astAssetTypePluralList.get(i));
					if(!Function.nvl(internalCode, "").equals("")) {
						replaceStr.append(internalCode + " ");
					} else {
						replaceStr.append("--------NOT_EXISTS--------" + " ");
					}
				}
			}

			searchParam.setAstAssetTypePlural(replaceStr.toString());
		}

		// 管理区分 名称⇒コード
		List<String> astManageTypePluralList = Function.getPluralList(searchParam.getAstManageTypePlural());
		if(astManageTypePluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < astManageTypePluralList.size(); i++) {
				if(!Function.nvl(astManageTypePluralList.get(i), "").equals("")) {
					String internalCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, null, astManageTypePluralList.get(i));
					if(!Function.nvl(internalCode, "").equals("")) {
						replaceStr.append(internalCode + " ");
					} else {
						replaceStr.append("--------NOT_EXISTS--------" + " ");
					}
				}
			}

			searchParam.setAstManageTypePlural(replaceStr.toString());
		}

		// オフィス 名称⇒コード
		List<String> holOfficePluralList = Function.getPluralList(searchParam.getHolOfficePlural());
		if(holOfficePluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < holOfficePluralList.size(); i++) {
				if(!Function.nvl(holOfficePluralList.get(i), "").equals("")) {
					String internalCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_OFFICE, null, holOfficePluralList.get(i), true);
					if(!Function.nvl(internalCode, "").equals("")) {
						replaceStr.append(internalCode + " ");
					} else {
						replaceStr.append("--------NOT_EXISTS--------" + " ");
					}
				}
			}

			searchParam.setHolOfficePlural(replaceStr.toString());
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#getAsset(java.lang.String, boolean)
	 */
	public Asset getAsset(String assetId, boolean isAp) {
		return getAsset(assetId, false, isAp);
	}

	/**
	 * 情報機器等取得
	 * @param assetId 情報機器管理番号
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return 情報機器等
	 */
	private Asset getAsset(String assetId, boolean lockFlag, boolean isAp){
		try{

			AssetDAO assetDAO = new AssetDAO();

			Asset asset = assetDAO.selectAsset(assetId, lockFlag, isAp);

			if(asset != null){
				// 明細取得
				asset.setAssetLineComUsr(assetDAO.selectAssetLineComUsr(assetId, isAp));		// 情報機器等登録申請_共有ユーザー明細
				asset.setAssetLineOs(assetDAO.selectAssetLineOs(assetId, isAp));				// 情報機器等登録申請_OS明細
				asset.setAssetLineNetwork(assetDAO.selectAssetLineNetwork(assetId, isAp));		// 情報機器等登録申請_ネットワーク明細
				if(!isAp) asset.setAssetLineInv(assetDAO.selectAssetLineInv(assetId, isAp));	// 情報機器等_棚卸明細
			}

			return asset;

		} catch (SQLException e) {
			//	情報機器等検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等取得" + (isAp ? "(登録申請)" : "")), e);
		}

	}

	/**
	 * 不正セット項目値の調整
	 * @param obj 情報機器等データ
	 * @param isAp ture:情報機器等登録申請,false:情報機器等
	 */
	private void setPropertyAdjust(Asset obj, boolean isAp) {
		// フラグがNULLの0をセット
		if(obj.getAstSystemSectionDeployFlag() == null) obj.setAstSystemSectionDeployFlag(Constants.FLAG_NO);
		if(obj.getUseCommonFlag() == null) obj.setUseCommonFlag(Constants.FLAG_NO);
		if(obj.getInvInCompanyActualFlag() == null) obj.setInvInCompanyActualFlag(Constants.FLAG_NO);
		if(obj.getInvSealIssueFlag() == null) obj.setInvSealIssueFlag(Constants.FLAG_NO);
		if(obj.getDeleteFlag() == null) obj.setDeleteFlag(Constants.FLAG_NO);

		// 資産保有者がオリックスではない場合OIRクリア
		if(!Function.nvl(obj.getAstHolderCode(), "").equals("")) {
			CodeName leReCompany = masterSession.getCodeName(Constants.CATEGORY_CODE_LEASE_RENTAL_CUSTOMER, obj.getAstHolderCode(), obj.getHolCompanyCode(), null);

			if(leReCompany != null && !Function.nvl(leReCompany.getValue7(), "").equals(Constants.FLAG_YES)) {
				obj.setAstOir(null);
			}
		} else {
			obj.setAstOir(null);
		}

		// 共有ユーザー無しの場合、共有ユーザー明細クリア
		if(!Function.nvl(obj.getUseCommonFlag(), "").equals(Constants.FLAG_YES)) {
			obj.setAssetLineComUsr(new ArrayList<AssetLineComUsr>());
		}

		// シール発行対象外の場合発行日・ホソククリア
		if(!Function.nvl(obj.getInvSealIssueFlag(), "").equals(Constants.FLAG_YES)) {
			obj.setInvSealIssueDate(null);
			obj.setInvSealIssueDescription(null);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean)
	 */
	public String createAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp) throws AppException {
		return createAsset(loginStaffCode, accessLevel, obj, isAp, true);
	}

	/**
	 * 情報機器等作成本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 情報機器等データ
	 * @param isAp ture:情報機器等登録申請,false:情報機器等
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの情報機器番号
	 */
	private String createAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp, boolean isHistCreate) throws AppException {
		try {
			AssetDAO assetDAO = new AssetDAO();

			//////////////////////////////////// 他エンティティ更新
			if(!isAp) { // 情報機器等新規登録
				// 登録申請を更新
				obj.setApStatus(Constants.AP_STATUS_ASSET_APPROVE);
				updateAsset(loginStaffCode, accessLevel, obj, true, true, true, HIST_OPERATION_CREATE_ASSET, true, null);
			}

			//////////////////////////////////// 固定値セット
			if(!isAp) { // 情報機器等新規登録
				// 登録申請番号セット＆情報機器管理番号初期化
				obj.setRegistApplicationId(obj.getAssetId());
				obj.setAssetId(null);
			}

			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setCreateDate(sysdate);
			obj.setCreateStaffCode(loginStaffCode);
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			// 不正セット項目値の調整
			setPropertyAdjust(obj, isAp);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			if(isAp) { // 登録申請
				errMsg.append(validateApGeTanLineAst(obj));
			}

			// バリデーション
			errMsg.append(validateAsset(loginStaffCode, accessLevel, obj, null, isAp,null));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// IDの採番
			String assetId = masterSession.nextValId(ID_PREFIX + (isAp ? ID_PREFIX_AP_SUFFIX : ""));

			//////////////////////////////////// 情報機器登録特殊セット
			if(!isAp) {
				// ホスト名が空白の場合は情報機器管理番号をセット
				if(Function.nvl(obj.getNetHostName(), "").equals("")) obj.setNetHostName(assetId);
			}

			//////////////////////////////////// データ作成
			obj.setVersion(1); // バージョンセット
			obj.setAssetId(assetId); // IDセット

			assetDAO.insertAsset(obj, isAp); // ヘッダ作成
			createLine(loginStaffCode, obj, assetDAO, assetId, isAp); // 明細データ作成

			////////////////////////////////////履歴作成
			//	履歴作成
			if(isHistCreate) {
				histSession.createHistData((isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + HIST_ENTITY_NAME, assetId, HIST_OPERATION_CREATE, null);
			}

			//////////////////////////////////// 他エンティティ更新
			if(isAp) { // 登録申請
				if(obj.getApGetTanLineAstId() != null) {
					// 取得申請の登録情報更新
					apGetTanSession.updateApGetTanLineAstRegist(loginStaffCode, obj.getGetApplicationId(), obj.getApGetTanLineAstId(), Function.nvl(obj.getHolQuantity(), Integer.valueOf(1)).intValue(), HIST_OPERATION_CREATE_AP_ASSET);
				}
			} else { // 情報機器等新規登録
				//////////////////////////////////// eGuardAD作成
				try {
					createEGuardAsset(obj);
				} catch (Exception ex) {
					// eGuardAD連携エラーは無視する。
				}
			}

			return obj.getAssetId();

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等作成" + (isAp ? "(登録申請)" : "")), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#updateAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean)
	 */
	public void updateAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateAsset(loginStaffCode, accessLevel, obj, isAp, true, true, null, true, null);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#updateAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean, java.lang.String)
	 */
	public void updateAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp, Set<String> updatePropSet) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateAsset(loginStaffCode, accessLevel, obj, isAp, true, true, null, true, updatePropSet);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#updateAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean, java.lang.String)
	 */
	public void updateAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp, String operation) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateAsset(loginStaffCode, accessLevel, obj, isAp, false, true, operation, true, null);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#updateAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean, java.lang.String, boolean)
	 */
	public void updateAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp, String operation, boolean isValidate) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateAsset(loginStaffCode, accessLevel, obj, isAp, true, true, operation, isValidate, null);
	}

	/**
	 * 情報機器等更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 情報機器等データ
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @param isValidate バリデーションチェックフラグ
	 * @throws AppException
	 */
	private void updateAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp, boolean isLineUpdate, boolean isHistCreate, String operation, boolean isValidate, Set<String> updatePropSet) throws AppException {
		try {
			AssetDAO assetDAO = new AssetDAO();

			Asset assetOld = getAsset(obj.getAssetId(), true, isAp); // 現データの取得&ロック

			//////////////////////////////////// 固定値セット
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			// 不正セット項目値の調整
			setPropertyAdjust(obj, isAp);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != assetOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// バリデーション(登録画面更新の際のみ：連携等による更新時は行わない)
			if(isLineUpdate && isValidate) errMsg.append(validateAsset(loginStaffCode, accessLevel, obj, assetOld, isAp,  updatePropSet));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新
			obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1); // バージョンセット

			assetDAO.updateAsset(obj, isAp); // ヘッダ更新

			// 抹消、抹消取消時は明細を更新しない
			if(isLineUpdate){
				// 明細を一度削除
				assetDAO.deleteAssetLineComUsr(obj.getAssetId(), isAp);
				assetDAO.deleteAssetLineOs(obj.getAssetId(), isAp);
				assetDAO.deleteAssetLineNetwork(obj.getAssetId(), isAp);
				if(!isAp)
					assetDAO.deleteAssetLineInv(obj.getAssetId(), isAp);

				createLine(loginStaffCode, obj ,assetDAO, obj.getAssetId(), isAp); // 明細データ作成
			}

			////////////////////////////////////履歴作成
			if(isHistCreate) {

				StringBuffer lineChangeColumnName = new StringBuffer();
				if(isLineUpdate){
					// 明細変更確認
					if(Function.isListChange(obj.getAssetLineComUsr(), assetOld.getAssetLineComUsr())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("共有ユーザー明細");
					}
					if(Function.isListChange(obj.getAssetLineOs(), assetOld.getAssetLineOs())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("OS明細");
					}
					if(Function.isListChange(obj.getAssetLineNetwork(), assetOld.getAssetLineNetwork())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("ネットワーク明細");
					}
					if(!isAp && Function.isListChange(obj.getAssetLineInv(), assetOld.getAssetLineInv())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("棚卸明細");
					}
				}

				histSession.createHistData((isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + HIST_ENTITY_NAME, obj.getAssetId(), Function.nvl(operation, HIST_OPERATION_UPDATE), lineChangeColumnName.toString());
			}

			//////////////////////////////////// 他エンティティ更新
			if(!isAp) { // 情報機器等
				//////////////////////////////////// eGuardAD作成
				if(!Function.nvl(obj.getAstCategory2Code(), "").equals(Function.nvl(assetOld.getAstCategory2Code(), ""))) { // 資産分類変更有り
					try {
						syncEGuardAsset(obj);
					} catch (Exception ex) {
						// eGuardAD連携エラーは無視する。
					}
				}
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#deleteAssetLogical(java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.util.List)
	 */
	public void deleteAssetLogical(String loginStaffCode, String accessLevel, Date deleteDate, String deleteReason,  List<Asset> assetList) throws AppException {
		try {

			AssetDAO assetDAO = new AssetDAO();

			//////////////////////////////////// バリデーション
			// 抹消日必須
			StringBuffer errMsg = new StringBuffer();
			if(deleteDate == null) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "抹消日"));
			}
			// 抹消理由必須
			if(Function.nvl(deleteReason, "").equals("")) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "抹消理由"));
			}
			// 抹消理由最大サイズ
			if(Function.nvl(deleteReason, "").getBytes(Constants.DB_CHARSET).length > Constants.MAX_CHAR_SIZE_DELETE_REASON) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MAX_SIZE, "抹消理由", new DecimalFormat().format(Constants.MAX_CHAR_SIZE_DELETE_REASON)));
			}

			if(errMsg.length() > 0) { // エラーあり
				throw new AppException(errMsg.toString());
			}

			errMsg = new StringBuffer();
			for(int i = 0; i < assetList.size(); i++){
				AssetSR assetData = (AssetSR)assetList.get(i);
				String assetId = assetData.getAssetId();

				Asset assetOld = getAsset(assetId, true, false); // 現データの取得

				if(assetOld != null){

					// バージョンチェック
					if(assetData.getVersion().intValue() != assetOld.getVersion().intValue()) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, assetData.getAssetId(), Msg.ERR_MSG_VER_LIST));
					}

					// 修正権限チェック
					if(!isEditableAsset(loginStaffCode, accessLevel, assetOld.getAssetId(), false)) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, assetData.getAssetId(), Msg.ERR_MSG_EDIT_AUTH));
					}

					// 一般権限
					if(!Function.isAccessLevelAdmin(accessLevel)) {
						// 資産取得形態がリース/固定資産の場合
						if(Function.nvl(assetOld.getAstGetType(), "").equals(Constants.ASSET_GET_TYPE_LEASE)
							|| Function.nvl(assetOld.getAstGetType(), "").equals(Constants.ASSET_GET_TYPE_TAN)) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, assetData.getAssetId(), "取得形態が " + assetOld.getAstGetTypeName() + " のため、各部担当者は直接抹消できません。"));
						}

						// 管理区分が抹消不可
						if(!Function.nvl(assetOld.getAstManageType(), "").equals("")) {
							CodeName astManageType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, assetOld.getAstManageType(), null, null);
							if(astManageType != null && Function.nvl(astManageType.getValue3(), "").equals(Constants.FLAG_YES)) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, assetData.getAssetId(), "管理区分が " + assetOld.getAstManageTypeName() + " のため、各部担当者は直接抹消できません。"));
							}
						}
					}
				}
			}

			if(errMsg.length() > 0) { // エラーあり
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 更新
			for(int i = 0; i < assetList.size(); i++) {
				assetDAO.callDeleteAssetLogical(loginStaffCode, assetList.get(i).getAssetId(), deleteDate, deleteReason);
			}

			//////////////////////////////////// eGuardAD削除
			if(new Date().compareTo(deleteDate) >= 0) { // 抹消日が未来日ではない
				for(int i = 0; i < assetList.size(); i++) {
					try {
						deleteEGuardAsset(assetList.get(i));
					} catch (Exception e) {
						// eGuardAD連携エラーは無視する。
					}
				}
			}
		} catch (UnsupportedEncodingException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等抹消"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等抹消"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#deleteAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean)
	 */
	public void deleteAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp) throws AppException {
		try {
			 AssetDAO assetDAO = new AssetDAO();

			 Asset assetOld = getAsset(obj.getAssetId(), true, isAp); // 現データの取得

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != assetOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// 修正権限チェック
			if(!isEditableAsset(loginStaffCode, accessLevel, obj.getAssetId(), isAp)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_EDIT_AUTH));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新(履歴作成用にバージョンアップ)
			// 現データを更新に使用
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			assetOld.setUpdateDate(sysdate);
			assetOld.setUpdateStaffCode(loginStaffCode);

			// バージョン
			assetOld.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);

			// 更新コメント
			assetOld.setUpdateComment(null);

			assetDAO.updateAsset(assetOld, isAp);

			//////////////////////////////////// 履歴作成
			histSession.createHistData((isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + HIST_ENTITY_NAME, obj.getAssetId(), HIST_OPERATION_DELETE, null);

			//////////////////////////////////// 他エンティティ更新
			if(isAp) { // 登録申請
					if(obj.getApGetTanLineAstId() != null) {
						// 取得申請の登録情報更新
						apGetTanSession.updateApGetTanLineAstRegist(loginStaffCode, obj.getGetApplicationId(), obj.getApGetTanLineAstId(), - Function.nvl(obj.getHolQuantity(), Integer.valueOf(1)).intValue(), HIST_OPERATION_DELETE_AP_ASSET);
					}
			}

			//////////////////////////////////// データ削除
			assetDAO.deleteAsset(obj.getAssetId(), isAp);
			assetDAO.deleteAssetLineComUsr(obj.getAssetId(), isAp);
			assetDAO.deleteAssetLineOs(obj.getAssetId(), isAp);
			assetDAO.deleteAssetLineNetwork(obj.getAssetId(), isAp);
			if(!isAp) assetDAO.deleteAssetLineInv(obj.getAssetId(), isAp);

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等削除" + (isAp ? "(登録申請)" : "")), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#restoreAsset(java.lang.String, java.lang.String, java.lang.String)
	 */
	public void restoreAsset(String loginStaffCode, String accessLevel, Asset obj) throws AppException {
		Asset objOld = getAsset(obj.getAssetId(), true, false); // 現データの取得

		/////////////////////////////////////// バリデーション
		StringBuffer errMsg = new StringBuffer();
		// バージョンチェック
		if(obj.getVersion().intValue() != objOld.getVersion().intValue()) {
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
		}

		// 修正権限チェック
		if(!isEditableAsset(loginStaffCode, accessLevel, obj.getAssetId(), false)) {
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_EDIT_AUTH));
		}

		//	部署年度チェック
		CodeName currentYearCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
		int currentYear = Integer.valueOf(currentYearCodeName.getValue1());
		if(currentYear != obj.getHolSectionYear() ||  currentYear != obj.getUseSectionYear() ) {
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "保有(使用)部署に過去年度の部署が設定されているため、抹消取消できません。"));
		}

		// 一般権限
		if(!Function.isAccessLevelAdmin(accessLevel)) {
			// 資産取得形態がリース/固定資産の場合
			if(!Function.nvl(obj.getAstGetType(), "").equals("")) {
				if(Function.nvl(obj.getAstGetType(), "").equals(Constants.ASSET_GET_TYPE_LEASE)
						|| Function.nvl(obj.getAstGetType(), "").equals(Constants.ASSET_GET_TYPE_TAN)) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "取得形態が " + obj.getAstGetTypeName() + " のため、各部担当者は直接抹消取消できません。"));
					}
			}

			// 管理区分が抹消不可
			if(!Function.nvl(obj.getAstManageType(), "").equals("")) {
				CodeName astManageType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, obj.getAstManageType(), null, null);
				if(astManageType != null && Function.nvl(astManageType.getValue3(), "").equals(Constants.FLAG_YES)) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "管理区分が " + obj.getAstManageTypeName() + " のため、各部担当者は直接抹消取消できません。"));
				}
			}
		}

		//	エラーあり
		if(errMsg.length() > 0){
			throw new AppException(errMsg.toString());
		}

		/////////////////////////////////// 更新
		obj.setDeleteFlag(Constants.FLAG_NO);
		obj.setDeleteDate(null);
		obj.setDeleteReason(null);

		// 更新コメント
		obj.setUpdateComment(null);

		updateAsset(loginStaffCode, accessLevel, obj, false, HIST_OPERATION_RESTORE);

		//////////////////////////////////// eGuardAD作成
		try {
			createEGuardAsset(obj);
		} catch (Exception ex) {
			// eGuardAD連携エラーは無視する。
		}
	}


	/*
	 * 明細データの作成
	 */
	private void createLine(String loginStaffCode, Asset obj, AssetDAO assetDAO, String assetId, boolean isAp) throws SQLException {
		Date sysdate = new Date(); // システム日付取得
		//	共有ユーザー明細
		List<AssetLineComUsr> lineComUsrList = obj.getAssetLineComUsr();
		if(lineComUsrList != null){
			for(int i = 0; i < lineComUsrList.size(); i++) {
				AssetLineComUsr row = lineComUsrList.get(i);
				row.setAssetId(obj.getAssetId()); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);
				assetDAO.insertAssetLineComUsr(row, isAp);
			}
		}

		//	OS明細作成
		List<AssetLineOs> lineOs = obj.getAssetLineOs();
		if(lineOs != null){
			for(int i = 0; i < lineOs.size(); i++) {
				AssetLineOs row = lineOs.get(i);
				row.setAssetId(obj.getAssetId()); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)
				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				if(!isAp && row.getAssetLineOsId() == null) { // 機器の場合はOS明細IDを採番
					row.setAssetLineOsId(masterSession.nextVal("NEA_ASSET_LINE_OS_S")); // 明細ID新規
				}

				assetDAO.insertAssetLineOs(row, isAp);
			}
		}

		//	ネットワーク明細作成
		List<AssetLineNetwork> lineNetwork = obj.getAssetLineNetwork();
		if(lineNetwork != null){
			for(int i = 0; i < lineNetwork.size(); i++) {
				AssetLineNetwork row = lineNetwork.get(i);
				row.setAssetId(obj.getAssetId()); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)
				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				assetDAO.insertAssetLineNetwork(row, isAp);
			}
		}

		if(!isAp) { // 棚卸明細は登録申請には無い
			//	棚卸明細作成作成
			List<AssetLineInv> lineInv = obj.getAssetLineInv();
			if(lineInv != null){
				for(int i = 0; i < lineInv.size(); i++) {
					AssetLineInv row = lineInv.get(i);
					row.setAssetId(obj.getAssetId()); // IDのセット
					row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

					// 更新日・更新者
					row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
					row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
					row.setUpdateDate(sysdate);
					row.setUpdateStaffCode(loginStaffCode);

					assetDAO.insertAssetLineInv(row, isAp);
				}
			}
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#applyApAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset)
	 */
	public String applyApAsset(String loginStaffCode, String accessLevel, Asset obj) throws AppException {
		String ret = null;
		boolean isNew = Function.nvl(obj.getAssetId(), "").equals(""); // 新規の場合true

		//////////////////////////////////// 新規 or 更新呼び出し
		if(isNew) { // 新規
			ret = createAsset(loginStaffCode, accessLevel, obj, true, false);
		} else { // 更新
			// 更新コメント
			obj.setUpdateComment(null);

			ret = obj.getAssetId();

			Asset apAsset = getAsset(obj.getAssetId(), true, true);

			// 不正セット項目値の調整
			setPropertyAdjust(obj, true);

			// バリデーション
			StringBuffer errMsg = new StringBuffer();
			// バージョンチェック
			if(obj.getVersion().intValue() != apAsset.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, obj.getAssetId(), Msg.ERR_MSG_VER));
			}
			// ステータス更新前バリデーション
			errMsg.append(validateAsset(loginStaffCode, accessLevel, obj, null, true, null));
			if(errMsg.length() > 0) throw new AppException(errMsg.toString());
		}

		//////////////////////////////////// ステータス更新&ステータス更新後バリデーション
		obj.setApStatus(Constants.AP_STATUS_ASSET_APPLY);
		String errMsg = validateAsset(loginStaffCode, accessLevel, obj, null, true, null);
		if(errMsg.length() > 0) throw new AppException(errMsg);

		//////////////////////////////////// 申請
		Long eappId = null;
		if(!Function.nvl(obj.getAstAssetType(), "").equals(Constants.ASSET_TYPE_VM)) { // 仮想機器以外
			eappId = callEappService(obj); // e申請連携
		}

		// e申請IDを更新
		obj.setEappId(eappId);

		if(isNew) { // 新規
			updateAsset(loginStaffCode, accessLevel, obj, true, false, false, null, true, null);
			histSession.createHistData(HIST_ENTITY_NAME_AP_PREFIX + HIST_ENTITY_NAME, ret, HIST_OPERATION_APPLY, null); // 履歴作成
		} else {
			updateAsset(loginStaffCode, accessLevel, obj, true, true, true, HIST_OPERATION_APPLY, true, null);
		}

		//////////////////////////////////// 自動承認・機器登録（仮想機器）
		if(Function.nvl(obj.getAstAssetType(), "").equals(Constants.ASSET_TYPE_VM)) {
			createAsset(Constants.STAFF_CODE_SYSTEM, accessLevel, obj, false); // 機器登録
		}

		return ret;
	}

	/**
	 * e申請サービス呼び出し
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(Asset obj) throws AppException {
		// e申請WebServiceエンドポイント取得
		CodeName codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_ASSET, null, null);
		String eappWsEndpoint = codeNameUrl.getValue1(); // e申請WebSerivceエンドポイント
		String eAssetUrl = codeNameUrl.getValue2(); // e申請インラインフレーム画面表示用のeAssetUrl
		String eappStopMessage = codeNameUrl.getValue3(); // e申請との連携停止期間中のエラーメッセージ

		Long eappId = null;

		if(!Function.nvl(eappWsEndpoint, "").equals("")) { // e申請WebServiceエンドポイントが空白(PG検証用)の場合は連携無し
			// e申請との連携停止期間中のエラーメッセージ
			if(!eappWsEndpoint.startsWith("http")){
				throw new AppException(eappStopMessage);
			}

			eAssetUrl += "&amp;companyCode=" + obj.getApCompanyCode();
			eAssetUrl += "&amp;param2="; // e申請から書類IDが指定される

			//////////////////////////////////// 経路設定
			// e申請経路担当情報取得
			CodeName codeNameEappCharge = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_ASSET, null, obj.getApCompanyCode(), null);

			// e申請経路権限情報取得
			CodeName codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_ASSET, null, obj.getApCompanyCode(), null);

			CodeName codeNameUseCompany = masterSession.getCodeName(Constants.CATEGORY_CODE_USE_COMPANY, obj.getApCompanyCode(), null, null);
			//	シンクライアントe申請をおこなわない会社？
			if(codeNameUseCompany != null && Function.nvl(codeNameUseCompany.getValue9(), "").equals(Constants.FLAG_NO)) {
				if(!Function.nvl(obj.getAstManageType(), "").equals("")){
					//	シンクライアントか確認
					CodeName codeNameAstManageType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, obj.getAstManageType(), null, null);
					if(codeNameAstManageType != null){
						//	シンクライアントの申請の場合、e申請との連携は行わない。
						if(Function.nvl(codeNameAstManageType.getValue2(), "").equals("1") && Function.nvl(obj.getAstSystemSectionDeployFlag(), "").equals(Constants.FLAG_YES)){
							codeNameEappRoute = null;
						}
					}
				}
			}

			if(codeNameEappRoute != null) { // 経路有り

				// 経路パラメータセット

				//////////////////////////////////// e申請サービス呼び出し
				String eappIdStr = null;
				try {
					ApAssetService service = new ApAssetServiceProxy(eappWsEndpoint);
					eappIdStr = service.apply(
							obj.getAssetId() // applicationId
							, Constants.AP_TYPE_ASSET // applicationType
							, obj.getApCompanyCode() // companyCode
							, obj.getApSectionCode() // apSectionCode
							, obj.getApCreateStaffCode() // apCreateStaffCode
							, obj.getApStaffCode() // apStaffCode
							, obj.getApTel() // apTel
							, "\\n" + Constants.AP_TITLE_ASSET // apTitle
							, null // apSubTitle
							, obj.getApCompanyName() + Constants.AP_TITLE_ASSET // apListTitle
							, eAssetUrl // eAssetUrl
							, codeNameEappRoute.getValue3() // acceptRouteAuthDisp
							, codeNameEappCharge.getValue3() // acceptRouteChargeDisp
							, "1" // acceptRouteDispType
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
						Long getEAppIdService = eAssetService.getEAppId(obj.getAssetId());
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
		}

		return eappId;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#approveApAsset(java.lang.Long, java.lang.String)
	 */
	public void approveApAsset(Long eappId, String execStaffCode) throws AppException {
		AssetSC searchParam = new AssetSC();
		searchParam.setEappId(eappId);

		// 対象登録申請取得
		List<AssetSR> list = searchAsset(execStaffCode, null, searchParam, true);

		for(int i = 0; i < list.size(); i++) {
			Asset asset = list.get(i);

			if(Constants.AP_STATUS_ASSET_APPLY.equals(asset.getApStatus())) { // 申請中であれば差戻しを行う
				// ステータス
				asset.setApStatus(Constants.AP_STATUS_ASSET_SENDBACK);
				// 更新コメント
				asset.setUpdateComment(null);

				updateAsset(execStaffCode, null, asset, true, false, true, HIST_OPERATION_RESERVE, true, null);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#cancelApplyApAsset(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyApAsset(Long eappId, String execStaffCode) throws AppException {
		AssetSC searchParam = new AssetSC();
		searchParam.setEappId(Function.nvl(eappId,Long.valueOf(-1)));

		// 対象登録申請取得
		List<AssetSR> list = searchAsset(execStaffCode, null, searchParam, true);

		for(int i = 0; i < list.size(); i++) {
			Asset asset = list.get(i);

			if(Constants.AP_STATUS_ASSET_APPLY.equals(asset.getApStatus())) { // 申請中であれば引戻しを行う
				// ステータス
				asset.setApStatus(Constants.AP_STATUS_ASSET_NOAPPLY);
				// 更新コメント
				asset.setUpdateComment(null);
				asset.setEappId(null); // 書類IDクリア

				updateAsset(execStaffCode, null, asset, true, false, true, HIST_OPERATION_CANCEL_APPLY, true, null);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#rejectApAsset(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectApAsset(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		AssetSC searchParam = new AssetSC();
		searchParam.setEappId(Function.nvl(eappId,Long.valueOf(-1)));

		// 対象登録申請取得
		List<AssetSR> list = searchAsset(execStaffCode, null, searchParam, true);

		for(int i = 0; i < list.size(); i++) {
			Asset asset = list.get(i);

			if(Constants.AP_STATUS_ASSET_APPLY.equals(asset.getApStatus())) { // 申請中であれば引戻しを行う
				// ステータス
				asset.setApStatus(Constants.AP_STATUS_ASSET_SENDBACK);
				// 更新コメント
				asset.setUpdateComment(rejectReason);
				asset.setEappId(null); // 書類IDクリア

				updateAsset(execStaffCode, null, asset, true, false, true, HIST_OPERATION_SENDBACK, true, null);
			}
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#searchApAssetEapp(java.lang.Long, java.lang.String)
	 */
	public List<AssetSR> searchApAssetEapp(Long eappId, String execStaffCode) {

		AssetSC searchParam = new AssetSC();
		searchParam.setEappId(eappId);
		searchParam.setSearchEapp("1");
		// 対象登録申請取得
		return (List<AssetSR>)searchAsset(execStaffCode, null, searchParam, true);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#getApAssetByApGetTan(java.lang.Long)
	 */
	public Asset getApAssetByApGetTan(Long apGetTanLineAstId) {
		try{
			AssetDAO assetDAO = new AssetDAO();

			Asset apAsset = assetDAO.selectApAssetByApGetTan(apGetTanLineAstId);

			// 故障交換の場合交換元の情報をセット
			if(!Function.nvl(apAsset.getDscFailureAssetId(), "").equals("")) {
				Asset failureAsset = getAsset(apAsset.getDscFailureAssetId(), false); // 交換元の情報取得

				// 資産区分情報
				apAsset.setAstGetType(failureAsset.getAstGetType());
				apAsset.setAstGetTypeName(failureAsset.getAstGetTypeName());
				apAsset.setAstAssetType(failureAsset.getAstAssetType());
				apAsset.setAstAssetTypeName(failureAsset.getAstAssetTypeName());
				apAsset.setAstManageType(failureAsset.getAstManageType());
				apAsset.setAstManageTypeName(failureAsset.getAstManageTypeName());
				apAsset.setAstHolderCode(failureAsset.getAstHolderCode());
				apAsset.setAstHolderName(failureAsset.getAstHolderName());
				apAsset.setAstOirEnable(failureAsset.getAstOirEnable());

				// 設置場所・使用目的
				apAsset.setHolOfficeCode(failureAsset.getHolOfficeCode());
				apAsset.setHolOfficeName(failureAsset.getHolOfficeName());
				apAsset.setHolOfficeFloor(failureAsset.getHolOfficeFloor());
				apAsset.setHolOfficeRoomNum(failureAsset.getHolOfficeRoomNum());
				apAsset.setHolOfficeRackNum(failureAsset.getHolOfficeRackNum());
				apAsset.setHolPurposeCode(failureAsset.getHolPurposeCode());
				apAsset.setHolPurposeName(failureAsset.getHolPurposeName());
				apAsset.setHolOfficeDescription(failureAsset.getHolOfficeDescription());

				// 社内実地棚卸対象・シール発行対象
				apAsset.setInvInCompanyActualFlag(failureAsset.getInvInCompanyActualFlag());
				apAsset.setInvInCompanyActualFlagName(failureAsset.getInvInCompanyActualFlagName());
				apAsset.setInvSealIssueFlag(failureAsset.getInvSealIssueFlag());
				apAsset.setInvSealIssueFlagName(failureAsset.getInvSealIssueFlagName());
			}

			return apAsset;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請明細データの取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createApAssetCsvByApGetTan(java.lang.String, java.lang.String, java.util.List)
	 */
	public String createApAssetCsvByApGetTan(String loginStaffCode, String accessLevel, List<ApGetTan> apGetTanList) {
		CsvWriterRowHandler handler = null;
		try {
			//////////////////////////////////// ファイル作成
			StringBuffer headerRow = new StringBuffer();
			List<String> propList = new ArrayList<String>();
			List<Format> propFormatList = new ArrayList<Format>();

			new MasterDAO().setCsvDefForUpload(Constants.CATEGORY_CODE_ITEM_DEF_AP_ASSET, accessLevel, headerRow, propList, propFormatList);

			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) propList.toArray(new String[0]), (Format[]) propFormatList.toArray(new Format[0]));

			StringBuffer applicationIdStr = new StringBuffer(); // 操作ログ用
			for(int i = 0; i < apGetTanList.size(); i++) {
				// 取得申請資産(機器)明細取得
				ApGetTan apGetTan = apGetTanSession.getApGetTan(apGetTanList.get(i).getApplicationId());

				// 操作ログ用
				if(applicationIdStr.length() > 0) applicationIdStr.append(" ");
				applicationIdStr.append(apGetTan.getApplicationId());

				List<ApGetTanLineAst> apGetTanLineAstList = apGetTan.getApGetTanLineAstList();

				for(int j = 0; j < apGetTanLineAstList.size(); j++) {
					ApGetTanLineAst apGetTanLineAst = apGetTanLineAstList.get(j);
					if(Function.nvl(apGetTanLineAst.getNoRegistFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) { // 要登録
						// 登録残数取得
						int registQty = Function.nvl(apGetTanLineAst.getQuantity(), Integer.valueOf(0)).intValue() - Function.nvl(apGetTanLineAst.getRegistQuantity(), Integer.valueOf(0)).intValue();

						if(registQty > 0) {
							Asset apAsset = getApAssetByApGetTan(apGetTanLineAst.getApGetTanLineAstId());
							List<Asset> apAssetList = new ArrayList<Asset>();

							int loopCt = (registQty - apAsset.getHolQuantity().intValue()) + 1;

							for(int k = 0; k < loopCt; k++) { // 登録残数量分
								apAssetList.add(apAsset);
							}

							for(int k = 0; k < apAssetList.size(); k++) {
								handler.handleRow(apAssetList.get(k)); // 行出力
							}
						}
					}
				}
			}

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_ASSET_BULK_CREATE, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",applicationId:" + applicationIdStr);

			return handler.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "登録申請作成用CSVファイル作成"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "登録申請作成用CSVファイル作成"), e);
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createApAssetPossibleInputMasterCsv(java.lang.String, java.lang.String)
	 */
	public String createAssetPossibleInputMasterCsv(String loginStaffCode, String accessLevel, String companyCode, List<String> propertyList) {
		try {
			AssetDAO assetDAO = new AssetDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = assetDAO.createAssetPossibleInputMasterList(companyCode, accessLevel, propertyList);

			//////////////////////////////////// 操作ログ作成
			StringBuffer propStr = new StringBuffer();
			if(propertyList != null) {
				for(int i = 0; i < propertyList.size(); i++) {
					if(propStr.length() > 0) propStr.append(" ");
					propStr.append(propertyList.get(i));
				}
			}
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_ASSET_MASTER, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",outputProperty:" + propStr.toString());

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createApAssetCompanyMoveInputMasterCsv(java.lang.String, java.lang.String)
	 */
	public String createAssetCompanyMoveInputMasterCsv(String loginStaffCode, String accessLevel, String companyCode) {
		try {
			AssetDAO assetDAO = new AssetDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = assetDAO.createAssetCompanyMoveInputMasterList(companyCode);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_ASSET_MASTER, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel);

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createApAssetBulk(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean)
	 */
	public List<String> createApAssetBulk(String loginStaffCode, String accessLevel, String fileId, Asset obj, boolean isVM) throws AppException {
		//////////////////////////////////// CSV読込
		List<Asset> createApAssetList = new ArrayList<Asset>();
		int headerRowCt = setAssetListByCsv(accessLevel, null, fileId, true, false, obj, isVM, createApAssetList, null, null);

		if(createApAssetList.size() == 0) throw new AppException("登録対象データが入力されていません。");

		//////////////////////////////////// 登録
		List<String> assetIdList = new ArrayList<String>();

		StringBuffer errorMessage = new StringBuffer();
		int rowCt = headerRowCt;
		for(int i = 0; i < createApAssetList.size(); i++) {
			rowCt++;
			String rowNumStr = "[" + rowCt + "行目] ";

			try {
				String assetId = createAsset(loginStaffCode, accessLevel, createApAssetList.get(i), true);
				assetIdList.add(assetId);
			} catch(AppException e) {
				errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "以下のエラーがあります。"));
				errorMessage.append(e.getMessage());
			}
		}

		if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

		return assetIdList;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#callUpdateAssetBulk(java.lang.String, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void callUpdateAssetBulk(String companyCode, String loginStaffCode, String accessLevel, String fileId, List<CodeName> updatePropertyList) throws AppException {
		//////////////////////////////////// 一括更新実行ログの作成
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

		param.put("functionName", BulkUpdateMDBean.FUNCTION_ASSET);
		param.put("loginStaffCode", loginStaffCode);
		param.put("companyCode", companyCode);
		param.put("accessLevel", accessLevel);
		param.put("fileId", fileId);
		param.put("execFile", execFile);
		param.put("updatePropertyList", updatePropertyList);
		param.put("logId", logId);

		Function.sendJmsMessage(bulkUpdateQueueFactory, bulkUpdateQueue, param);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#updateAssetBulk(java.lang.String, java.lang.String, java.lang.String, java.io.File, java.util.List, java.lang.Long)
	 */
	public void updateAssetBulk(String loginStaffCode, String accessLevel, String companyCode, String fileId, File execFile, List<CodeName> updatePropertyList, Long logId) {
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
			boolean isLineUpdate = Function.nvl(updatePropertyList.get(0).getValue1(), "").equals("NEA_ASSET") ? false : true;

			List<Asset> updateAssetList = new ArrayList<Asset>();
			int headerRowCt = setAssetListByCsv(accessLevel, companyCode, fileId, false, isLineUpdate, null, false, updateAssetList, updatePropertyList, logId);

			//////////////////////////////////// 一括更新実行ログの取得
			BulkUpdateHist logHist = histSession.getBulkUpdateHist(logId);

			if(!Function.nvl(logHist.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) { // ファイル読み込み中に中断されていない

				if(updateAssetList.size() == 0) throw new AppException("更新対象データが入力されていません。");

				//////////////////////////////////// ファイル読込設定
				execFileReader = new BufferedReader(new InputStreamReader(new FileInputStream(execFile), CsvReaderRowHandler.getCharsetName()));
				StringBuffer headerStr = new StringBuffer();
				for(int i = 0; i < headerRowCt; i++) {
					headerStr.append(execFileReader.readLine() + "\n");
				}

				int skipCount = 0; // エラー再実行時に重複実行しないため、処理スキップする件数

				if(logHist.getSuccessCount() == null && logHist.getFailureCount() == null) { // 初回実行
					//////////////////////////////////// 一括更新実行ログの更新
					execCount = Integer.valueOf(updateAssetList.size());
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
					// 読み込み対象プロパティのgetter/setter取得
					List<Method> readMethodList = new ArrayList<Method>();
					List<Method> writeMethodList = new ArrayList<Method>();
					HashSet<String> updatePropSet = new HashSet<String>();
					Asset a = new Asset(); /// リフレクション用のダミー
					if(!isLineUpdate) { // ヘッダ項目更新
						// 選択プロパティのgetter/setter設定
						for(int i = 0; i < updatePropertyList.size(); i++) {
							String prop = updatePropertyList.get(i).getValue3();
							updatePropSet.add(prop);
							PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(a, prop);
							readMethodList.add(PropertyUtils.getReadMethod(pd));
							writeMethodList.add(PropertyUtils.getWriteMethod(pd));

							// 追加更新プロパティの取得
							String addPropStr = Function.nvl(updatePropertyList.get(i).getValue16(), "");
							if(!addPropStr.equals("")) {
								String[] addProps = addPropStr.split(" ");
								for(int j = 0; j < addProps.length; j++) {
									updatePropSet.add(addProps[j]);
									pd = PropertyUtils.getPropertyDescriptor(a, addProps[j]);
									readMethodList.add(PropertyUtils.getReadMethod(pd));
									writeMethodList.add(PropertyUtils.getWriteMethod(pd));
								}
							}
						}
					} else { // 明細項目更新
						// 明細プロパティのgetter/setter設定
						String linePropName = "";

						if(Function.nvl(updatePropertyList.get(0).getValue1(), "").equals("NEA_ASSET_LINE_COM_USR")) {
							linePropName = "assetLineComUsr";

							// 共有ユーザの場合は共有ユーザーフラグも
							updatePropSet.add("useCommonFlag");
							PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(a, "useCommonFlag");
							readMethodList.add(PropertyUtils.getReadMethod(pd));
							writeMethodList.add(PropertyUtils.getWriteMethod(pd));
						} else if(Function.nvl(updatePropertyList.get(0).getValue1(), "").equals("NEA_ASSET_LINE_OS")) {
							linePropName = "assetLineOs";
						} else if(Function.nvl(updatePropertyList.get(0).getValue1(), "").equals("NEA_ASSET_LINE_NETWORK")) {
							linePropName = "assetLineNetwork";
						} else if(Function.nvl(updatePropertyList.get(0).getValue1(), "").equals("NEA_ASSET_LINE_INV")) {
							linePropName = "assetLineInv";
						}

						updatePropSet.add(linePropName);
						PropertyDescriptor pd = PropertyUtils.getPropertyDescriptor(a, linePropName);
						readMethodList.add(PropertyUtils.getReadMethod(pd));
						writeMethodList.add(PropertyUtils.getWriteMethod(pd));
					}

					Method[] readMethods = readMethodList.toArray(new Method[0]);
					Method[] writeMethods = writeMethodList.toArray(new Method[0]);

					// 移動申請区分取得
					List<CodeName> useCompanyList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_USE_COMPANY, null, null, null);
					HashMap<String, String> apChangeTypeMap = new HashMap<String, String>();
					for(int i = 0; i < useCompanyList.size(); i++) {
						CodeName row = useCompanyList.get(i);
						apChangeTypeMap.put(row.getInternalCode(), row.getValue4());
					}

					//////////////////////////////////// 登録(件数分)
					boolean lastStat = true; // 前行処理結果 true:成功 false:失敗
					for(int i = 0; i < updateAssetList.size(); i++) {
						//////////////////////////////////// 中断リクエストされていないかどうか確認
						BulkUpdateHist log = histSession.getBulkUpdateHist(logId);
						if(Function.nvl(log.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) break; // 中断

						//////////////////////////////////// 1件処理
						rowCt++;
						String execFileRowStr = execFileReader.readLine(); // 対象ファイル一行読み込み（成功・失敗ファイル保存用）

						if(i < skipCount) {
							continue; // エラー前に実行済みのためスキップ
						}

						Asset updateObj = updateAssetList.get(i); // 更新内容取得
						if(Function.nvl(updateObj.getAssetId(), "").equals("")) {
							if(lastStat) { // 前行処理が成功の場合は成功
								successCount = Integer.valueOf(successCount.intValue() + 1);
								Function.appendStrToFile(successFile, execFileRowStr + "\n");
							} else {
								failureCount = Integer.valueOf(failureCount.intValue() + 1);
								Function.appendStrToFile(failureFile, execFileRowStr + "\n");
							}
							continue; // 更新スキップ
						}

						try {
							//////////////////////////////////// 1件更新
							childAssetSession.updateAssetBulkRow(loginStaffCode, accessLevel, rowCt, updateObj, isLineUpdate, updatePropSet, apChangeTypeMap, readMethods, writeMethods);

							lastStat = true;
							successCount = Integer.valueOf(successCount.intValue() + 1);
							//////////////////////////////////// 一括更新実行ログの更新
							histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_UPDATE, execCount, successCount, failureCount);
							//////////////////////////////////// 実行データ保存
							Function.appendStrToFile(successFile, execFileRowStr + "\n");
						} catch(Exception e) {
							lastStat = false;
							failureCount = Integer.valueOf(failureCount.intValue() + 1);

							if(failureCount.intValue() == 1) { // 先頭エラーメッセージ表示
								StringBuffer errorMessage = new StringBuffer();
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "更新中に以下のエラーが発生しました。"));
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, "※ 更新項目に指定されていない項目がエラーとなっている場合は、該当項目も事前に(同時に)修正する必要があります。"));

								Function.appendMessageToFile(errorFile, errorMessage.toString() + "\n");
							}

							String rowNumStr; // エラー表示用行識別
							if(isLineUpdate) { // 明細更新の場合は行数の特定が困難なため情報機器管理番号のみ
								rowNumStr = "[" + updateObj.getAssetId() + "] "; // エラー表示用行識別に情報機器管理番号付加
							} else {
								rowNumStr = "[" + rowCt + "行目(失敗データファイル " + (headerRowCt + failureCount.intValue()) + "行目):" + updateObj.getAssetId() + "] "; // エラー表示用行識別に情報機器管理番号付加
							}

							StringBuffer errorMessage = new StringBuffer();
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "以下のエラーがあります。"));
							errorMessage.append(e.getMessage());

							Function.appendMessageToFile(errorFile, errorMessage.toString() + "\n");

							//////////////////////////////////// 一括更新実行ログの更新
							histSession.updateBulkUpdateLog(logId, loginStaffCode, Constants.BULK_UPDATE_STATUS_UPDATE, execCount, successCount, failureCount);
							//////////////////////////////////// 実行データ保存
							Function.appendStrToFile(failureFile, execFileRowStr + "\n");
						}
					}
				} catch (IllegalArgumentException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等更新"), e);
				} catch (IllegalAccessException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等更新"), e);
				} catch (InvocationTargetException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等更新"), e);
				} catch (NoSuchMethodException e) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等更新"), e);
				}
			}

		} catch(Exception e) {
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
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等更新"), e);
				}
			}

		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#updateAssetBulkRow(java.lang.String, java.lang.String, int, jp.co.ctcg.easset.dto.asset.Asset, boolean, java.util.Set, java.util.Map, java.lang.reflect.Method[], java.lang.reflect.Method[])
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // 新規トランザクション
	public void updateAssetBulkRow(String loginStaffCode, String accessLevel, int rowCt, Asset updateObj, boolean isLineUpdate, Set<String> updatePropSet, Map<String, String> apChangeTypeMap, Method[] readMethods, Method[] writeMethods) throws AppException {
		Asset obj = getAsset(updateObj.getAssetId(), true, false); // 更新対象取得

		//////////////////////////////////// 一括更新用バリデーション
		StringBuffer errMsg = new StringBuffer();
		// 抹消済
		if(Function.nvl(obj.getDeleteFlag(), "").equals(Constants.FLAG_YES)) {
			errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "抹消済の情報機器等は更新できません。"));
		}
		// 契約リンク有・契約番号更新有
		if(!Function.nvl(obj.getContractEda(), "").equals("")) {
			if(updatePropSet.contains("contractNum")) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "リース・レンタル契約紐付が存在する情報機器等の、契約番号は更新できません。"));
			}
		}
		// 資産分類(PC系<->PC系以外)
		if(updatePropSet.contains("astCategory1Code")) {
			if(!Function.nvl(obj.getAstCategory1Code(), "").equals("")
				&& !Function.nvl(updateObj.getAstCategory1Code(), "").equals("")) {
				CodeName assetCategory1Old = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY1, obj.getAstCategory1Code(), null, null);
				CodeName assetCategory1New = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY1, updateObj.getAstCategory1Code(), null, null);
				if(assetCategory1Old != null && assetCategory1New != null) {
					if(!Function.nvl(assetCategory1Old.getValue2(), "").equals(Function.nvl(assetCategory1New.getValue2(), ""))) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "資産(機器)分類を「" + obj.getAstCategory2Name() + "」から「" + updateObj.getAstCategory2Name() + "」へは更新できません。"));
					}
				}
			}
		}
		// 移動申請項目
		if(updatePropSet.contains("holSectionCode")
			|| updatePropSet.contains("holStaffCode")
			|| updatePropSet.contains("holOfficeCode")
			|| updatePropSet.contains("useStaffCode")) {
			if(!Function.isAccessLevelAdmin(accessLevel)) { // 一般・部署権限
				// 移動申請使用区分:使用
				if(Function.nvl(apChangeTypeMap.get(obj.getHolCompanyCode()), "").equals(Constants.AP_CHANGE_USE_TYPE_ALL)) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "保有部署、資産管理担当者、設置場所、使用者を更新するには、移動申請を行う必要があります。"));
				}
				// 移動申請使用区分:情シス配備のみ使用
				if(Function.nvl(apChangeTypeMap.get(obj.getHolCompanyCode()), "").equals(Constants.AP_CHANGE_USE_TYPE_SYS_DEPLOY)
					&& Function.nvl(obj.getAstSystemSectionDeployFlag(), "").equals(Constants.FLAG_YES)) {
					String astManageType = Function.nvl(obj.getAstManageType(), "");
					if(!astManageType.equals("")) {
						CodeName manageType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, astManageType, null, null);
						if(manageType != null
							&& (Function.nvl(manageType.getValue2(), "").equals(Constants.ASSET_MANAGE_TYPE_VALUE2_THINCLIENT) // シンクライアント
								|| Function.nvl(manageType.getValue2(), "").equals(Constants.ASSET_MANAGE_TYPE_VALUE2_TAKEN_PC) // 持ち出しPC
								)
							) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "管理区分が「" + obj.getAstManageTypeName() + "」の情報機器等に対して保有部署、資産管理担当者、設置場所、使用者を更新するには、移動申請を行う必要があります。"));
						}
					}
				}
			}
		}
		// 一般権限・社内実地棚卸し・棚卸し更新有
		if(Function.nvl(obj.getInvInCompanyActualFlag(), "").equals(Constants.FLAG_YES)) {
			if(!Function.isAccessLevelAdmin(accessLevel) && updatePropSet.contains("assetLineInv")) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "社内実地棚卸対象の情報機器等に対して、棚卸実施情報は更新できません。"));
			}
		}

		if(errMsg.length() > 0) throw new AppException(errMsg.toString());

		//////////////////////////////////// 更新
		// 更新項目のコピー
		try {
			for(int j = 0; j < writeMethods.length; j++) {
				writeMethods[j].invoke(obj, readMethods[j].invoke(updateObj, (Object[]) null));
			}
		} catch (IllegalArgumentException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等更新"), e);
		} catch (IllegalAccessException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等更新"), e);
		} catch (InvocationTargetException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "情報機器等更新"), e);
		}

		updateAsset(loginStaffCode, accessLevel, obj, false, updatePropSet);
	}

	/**
	 * CSVファイルから情報機器等情報セット
	 * @param accessLevel アクセスレベル
	 * @param companyCode 会社コード
	 * @param fileId CSVファイル参照
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @param isLineUpdate 明細更新
	 * @param apAssetObj 登録申請画面入力項目値
	 * @param isVM true:VM登録申請 false:左記以外
	 * @param assetList 情報機器等情報をセットするリスト
	 * @param updatePropertyList 更新項目一覧(nullは全て)
	 * @param logId 一括更新ログID(nullは一括更新以外)
	 * @return ヘッダ行数
	 * @throws AppException
	 */
	private int setAssetListByCsv(String accessLevel, String companyCode, String fileId, boolean isAp, boolean isLineUpdate, Asset apAssetObj, boolean isVM, List<Asset> assetList, List<CodeName> updatePropertyList, Long logId) throws AppException {
		CsvReaderRowHandler handler = null;

		try {
			AssetDAO assetDAO = new AssetDAO();

			if(isAp) {
				handler = new CsvReaderRowHandler(accessLevel, fileId, AssetSR.class, Constants.CATEGORY_CODE_ITEM_DEF_AP_ASSET);
			} else {
				handler = new CsvReaderRowHandler(accessLevel, fileId, AssetSR.class, Constants.CATEGORY_CODE_ITEM_DEF_ASSET, updatePropertyList);
			}

			AssetSR row = null;
			StringBuffer errorMessage = new StringBuffer(); // 全行エラーメッセージ
			int headerCt = handler.getHeaderRowCount();
			int rowCt = headerCt; // ファイル行カウンタ

			String flagYesName = masterSession.getCodeName(Constants.CATEGORY_CODE_FLAG_YN, Constants.FLAG_YES, null, null).getValue1(); // フラグ名
			int currentYear = Integer.valueOf(masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null).getValue1()); // カレント年度
			boolean isUpdateHolCompany = false; // 保有会社更新有
			boolean isUpdateHolSection = false; // 保有部署更新有
			boolean isUpdateUseSection = false; // 使用部署更新有
			boolean isUpdateUseStaff = false; // 使用者更新有

			// 部署更新指定判別用
			if(updatePropertyList != null) {
				for(int i = 0; i < updatePropertyList.size(); i++) {
					String prop = Function.nvl(updatePropertyList.get(i).getValue3(), "");
					if("holCompanyName".equals(prop)) isUpdateHolCompany = true;
					if("holSectionCode".equals(prop)) isUpdateHolSection = true;
					if("useSectionCode".equals(prop)) isUpdateUseSection = true;
					if("useStaffCode".equals(prop)) isUpdateUseStaff = true;

				}
			} else {
				isUpdateHolSection = true;
				isUpdateUseSection = true;
			}

			do {
				//////////////////////////////////// 中断リクエストされていないかどうか確認
				if(logId != null) {
					BulkUpdateHist log = histSession.getBulkUpdateHist(logId);
					if(Function.nvl(log.getExecStatus(), "").equals(Constants.BULK_UPDATE_STATUS_CANCEL_REQUEST)) break; // 中断
				}

				//////////////////////////////////// 1件処理
				rowCt++;
				String rowNumStr = "[" + rowCt + "行目] ";

				AssetSR oldObj = null;

				// 行データ取得
				if(isAp) { // 登録申請
					row = (AssetSR) handler.handleRow();
					if(row == null) break; // 行データが取得できない場合は終了

					if(!isVM && (Function.nvl(row.getGetApplicationId(), "").equals("") || row.getApGetTanLineAstLineSeq() == null)) {
						continue; // VM申請以外で取得申請書番号,明細行Noが未入力の場合はスキップ
					}
				} else { // 情報機器等
					// 更新対象データ取得
					String assetId = handler.handleId();
					if(Function.nvl(assetId, "").equals("")) break; // 行データが取得できない場合は終了

					AssetSC param = new AssetSC();
					param.setAssetIdPlural(assetId);
					List<AssetSR> targetList = searchAsset(Constants.STAFF_CODE_SYSTEM, accessLevel, param, false);
					if(targetList.size() == 0) {
						errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "情報機器管理番号", assetId));
						continue; // 情報機器管理番号が不正な場合はスキップ
					}

					oldObj = searchAsset(Constants.STAFF_CODE_SYSTEM, accessLevel, param, false).get(0); // 変更前オブジェクト保持用

					row = (AssetSR) handler.handleRow(targetList.get(0));
					if(row == null) break; // 行データが取得できない場合は終了

					rowNumStr = "[" + rowCt + "行目:" + row.getAssetId() + "] "; // エラー表示用行識別に情報機器管理番号付加
				}

				// 取得申請紐付セット(仮想機器以外)
				if(isAp) { // 登録申請
					if(!isVM) {
						Long apGetTanLineAstId = assetDAO.selectApGetTanLineAstId(row.getGetApplicationId(), row.getApGetTanLineAstLineSeq());
						row.setApGetTanLineAstId(apGetTanLineAstId);

						String failureAssetId = assetDAO.selectApGetTanFailureAssetId(row.getGetApplicationId(), row.getApGetTanLineAstLineSeq());
						row.setDscFailureAssetId(failureAssetId);
					}

					// 申請情報セット
					row.setApStatus(apAssetObj.getApStatus());
					row.setApDate(apAssetObj.getApDate());

					row.setApCreateStaffCode(apAssetObj.getApCreateStaffCode());
					row.setApCreateCompanyCode(apAssetObj.getApCreateCompanyCode());
					row.setApCreateSectionCode(apAssetObj.getApCreateSectionCode());
					row.setApCreateSectionYear(apAssetObj.getApCreateSectionYear());

					row.setApStaffCode(apAssetObj.getApStaffCode());
					row.setApCompanyCode(apAssetObj.getApCompanyCode());
					row.setApSectionCode(apAssetObj.getApSectionCode());
					row.setApSectionYear(apAssetObj.getApSectionYear());

					row.setApTel(apAssetObj.getApTel());
					row.setApOfficeName(apAssetObj.getApOfficeName());
				}

				// 資産(機器)分類マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getAstCategory2Name(), "").equals(Function.nvl(row.getAstCategory2Name(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setAstCategory2Code(null);
					row.setAstCategory1Code(null);
					row.setAstCategory1Name(null);
					if(!Function.nvl(row.getAstCategory2Name(), "").equals("")) {
						String assetCategory2 = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, null, row.getAstCategory2Name());
						String assetCategory1 = masterSession.getCodeNameParentIdByName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, null, row.getAstCategory2Name());

						if(assetCategory2 == null || assetCategory1 == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "資産・機器-資産(機器)分類", row.getAstCategory2Name()));
						} else {
							row.setAstCategory2Code(assetCategory2);

							CodeName astCategory1 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY1, assetCategory1, null, null);
							row.setAstCategory1Code(astCategory1.getInternalCode());
							row.setAstCategory1Name(astCategory1.getValue1());
						}
					}
				}

				// 筐体/形状マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getAstShapeName(), "").equals(Function.nvl(row.getAstShapeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setAstShapeCode(null);
					if(!Function.nvl(row.getAstShapeName(), "").equals("")) {
						String shapeCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_SHAPE, null, row.getAstShapeName());
						if(shapeCode == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "資産・機器-筐体/形状", row.getAstShapeName()));
						} else {
							row.setAstShapeCode(shapeCode);
						}
					}
				}

				// メーカーマスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getAstMakerName(), "").equals(Function.nvl(row.getAstMakerName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setAstMakerCode(null);
					if(!Function.nvl(row.getAstMakerName(), "").equals("")) {
						String makerCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_MAKER, null, row.getAstMakerName());
						if(makerCode == null) {  // 対応するマスタが見つからない場合は手入力扱い

						} else {
							row.setAstMakerCode(makerCode);
						}
					}
				}

				// 取得形態マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getAstGetTypeName(), "").equals(Function.nvl(row.getAstGetTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setAstGetType(null);
					if(!Function.nvl(row.getAstGetTypeName(), "").equals("")) {
						String astGetType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_GET_TYPE, null, row.getAstGetTypeName());
						if(astGetType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "資産・機器-取得形態", row.getAstGetTypeName()));
						} else {
							row.setAstGetType(astGetType);
						}

						if(isAp && isVM) {
							// 仮想機器申請の場合は取得形態の変更不可("仮想機器"固定)
							if(!Function.nvl(row.getAstGetType(), "").equals(Constants.ASSET_GET_TYPE_VM)) {
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "資産・機器-取得形態", row.getAstGetTypeName()));
							}
						}
					}
				}

				// 情報システム部配備
				if(Function.nvl(row.getAstSystemSectionDeployFlagName(), "").toUpperCase().equals(flagYesName.toUpperCase())) {
					row.setAstSystemSectionDeployFlag(Constants.FLAG_YES);
				} else {
					row.setAstSystemSectionDeployFlag(Constants.FLAG_NO);
				}

				// 資産区分マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getAstAssetTypeName(), "").equals(Function.nvl(row.getAstAssetTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setAstAssetType(null);
					if(!Function.nvl(row.getAstAssetTypeName(), "").equals("")) {
						String astAssetType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_TYPE, null, row.getAstAssetTypeName());
						if(astAssetType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "資産・機器-資産区分", row.getAstAssetTypeName()));
						} else {
							row.setAstAssetType(astAssetType);
						}
					}
				}

				// 管理区分マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getAstManageTypeName(), "").equals(Function.nvl(row.getAstManageTypeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setAstManageType(null);
					if(!Function.nvl(row.getAstManageTypeName(), "").equals("")) {
						String astManageType = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, null, row.getAstManageTypeName());
						if(astManageType == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "資産・機器-管理区分", row.getAstManageTypeName()));
						} else {
							row.setAstManageType(astManageType);
						}
					}
				}

				// 保有会社
				if(oldObj == null || !Function.nvl(oldObj.getHolCompanyName(), "").equals(Function.nvl(row.getHolCompanyName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setHolCompanyCode(null);
					if(!Function.nvl(row.getHolCompanyName(), "").equals("")) {
						String holCompanyCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_USE_COMPANY, null, row.getHolCompanyName());
						if(holCompanyCode == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "保有・設置-保有会社", row.getHolCompanyName()));
						} else {
							row.setHolCompanyCode(holCompanyCode);
						}
					}
				}

				// 保有部署年度
				if(isAp) { // 登録申請
					row.setHolSectionYear(row.getApSectionYear());
				} else {
					if(isUpdateHolSection) row.setHolSectionYear(currentYear);
				}

				// 保有部署コード0抜け対応
				if(!Function.nvl(row.getHolSectionCode(), "").equals("") && Function.nvl(row.getHolSectionCode(), "").length() < Constants.SECTION_CODE_LENGTH) {
					row.setHolSectionCode(StringUtils.leftPad(row.getHolSectionCode(), Constants.SECTION_CODE_LENGTH, "0"));
				}

				// 保有部署 <-> 資産管理担当者の整合性
				if(oldObj == null
					|| !Function.nvl(oldObj.getHolSectionCode(), "").equals(Function.nvl(row.getHolSectionCode(), ""))
					|| Function.nvl(oldObj.getHolSectionYear(), Integer.valueOf(0)).intValue() != Function.nvl(row.getHolSectionYear(), Integer.valueOf(0)).intValue()
					|| !Function.nvl(oldObj.getHolStaffCode(), "").equals(Function.nvl(row.getHolStaffCode(), ""))
					|| !Function.nvl(oldObj.getHolCompanyCode(), "").equals(Function.nvl(row.getHolCompanyCode(), ""))
				) { // 登録申請 or 一括更新で項目変更有り
					if(!Function.nvl(row.getHolSectionCode(), "").equals("")
						&& !Function.nvl(row.getHolStaffCode(), "").equals("") ) {
						HashMap<String, Object> param = new HashMap<String, Object>();

						param.put("companyCode", Function.nvl(row.getHolCompanyCode(), ""));
						param.put("sectionCode", row.getHolSectionCode());

						LovDataEx data = masterSession.getLovData("selectHolStaff_LOV", param, row.getHolStaffCode());
						if(data == null) {
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr + "保有・設置-資産管理担当者", "指定された資産管理担当者は、該当保有部署の担当ではありません。"));
						}
					}
				}

				// 設置場所
				if(oldObj == null || !Function.nvl(oldObj.getHolOfficeName(), "").equals(Function.nvl(row.getHolOfficeName(), "")) || isUpdateHolCompany) { // 登録申請 or 一括更新で項目変更有り or 保有会社変更有り
					row.setHolOfficeCode(null);
					if(!Function.nvl(row.getHolOfficeName(), "").equals("")) {
						String holOfficeCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_OFFICE, row.getHolCompanyCode(), row.getHolOfficeName()); // 1.91次対応するためパラメータに会社コード追加
						if(holOfficeCode == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "保有・設置-設置(使用)場所", row.getHolOfficeName()));
						} else {
							row.setHolOfficeCode(holOfficeCode);
						}
					}
				}

				// 資産保有者マスタ値セット
				if(oldObj == null || !Function.nvl(oldObj.getAstHolderName(), "").equals(Function.nvl(row.getAstHolderName(), "")) || isUpdateHolCompany) { // 登録申請 or 一括更新で項目変更有り or 保有会社変更有り
					row.setAstHolderCode(null);
					if(!Function.nvl(row.getAstHolderName(), "").equals("")) {
						String astHolderCode;
						if(isAp) {
							astHolderCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LEASE_RENTAL_CUSTOMER, row.getApCompanyCode(), row.getAstHolderName());
						} else {
							astHolderCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_LEASE_RENTAL_CUSTOMER, row.getHolCompanyCode(), row.getAstHolderName());
						}

						if(astHolderCode == null) {  // 対応するマスタが見つからない場合は手入力扱い
							if(isUpdateHolCompany && !Function.nvl(oldObj.getAstHolderName(), "").equals(Function.nvl(row.getAstHolderName(), ""))) {
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "資産・機器-資産保有者", row.getAstHolderName()));
							}
						} else {
							row.setAstHolderCode(astHolderCode);
						}
					}
				}

				// 使用目的
				if(oldObj == null || !Function.nvl(oldObj.getHolPurposeName(), "").equals(Function.nvl(row.getHolPurposeName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setHolPurposeCode(null);
					if(!Function.nvl(row.getHolPurposeName(), "").equals("")) {
						String holPurposeCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_USE_PURPOSE, null, row.getHolPurposeName());
						if(holPurposeCode == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "保有・設置-使用目的", row.getHolPurposeName()));
						} else {
							row.setHolPurposeCode(holPurposeCode);
						}
					}
				}

				// 取得/設置者
				if(oldObj == null || !Function.nvl(oldObj.getHolGetStaffCode(), "").equals(Function.nvl(row.getHolGetStaffCode(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setHolGetStaffName(null);
					row.setHolGetCompanyCode(null);
					row.setHolGetCompanyName(null);
					row.setHolGetSectionCode(null);
					row.setHolGetSectionName(null);
					row.setHolGetSectionYear(null);
					if(!Function.nvl(row.getHolGetStaffCode(), "").equals("")) {
						User staff = masterSession.getStaff(null, row.getHolGetStaffCode());
						if(staff != null) {
							row.setHolGetStaffName(staff.getName());
							row.setHolGetCompanyCode(staff.getCompanyCode());
							row.setHolGetCompanyName(staff.getCompanyName());
							row.setHolGetSectionCode(staff.getSectionCode());
							row.setHolGetSectionName(staff.getSectionName());
							row.setHolGetSectionYear(currentYear);
						}
					}
				}

				// 数量整合性確認
				if(oldObj == null
					|| (oldObj.getHolQuantity() == null && row.getHolQuantity() != null)
					|| (oldObj.getHolQuantity() != null && row.getHolQuantity() == null)
					|| (oldObj.getHolQuantity() != null && row.getHolQuantity() != null && oldObj.getHolQuantity().intValue() != row.getHolQuantity().intValue())
				) { // 登録申請 or 一括更新で項目変更有り
					if(!Function.nvl(row.getAstCategory2Code(), "").equals("")) {
						CodeName assetCategory2 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, row.getAstCategory2Code(), null, null);
						if(assetCategory2 != null && Function.nvl(assetCategory2.getValue2(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {
							if(Function.nvl(row.getHolQuantity(), Integer.valueOf(0)).intValue() != 1) {
								// 固体管理なのに数量に1以外が指定されている場合エラー
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "保有・設置-数量", row.getHolQuantity() + " ※ 該当資産(機器)分類では1以外の指定はできません"));
							}
						}
					}
				}

				// 保有会社変更時、使用会社と一致しない場合はエラーとする
				if(isUpdateHolCompany && !Function.nvl(row.getHolCompanyName(), "").equals(Function.nvl(row.getUseCompanyName(), ""))) {
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "保有会社(" + Function.nvl(row.getHolCompanyName(), "") + ")と使用会社(" + Function.nvl(row.getUseCompanyName(), "") + ")が一致しません"));
				}

				// 保有会社変更時、変更前の保有会社がログイン会社と一致しない場合はエラーとする
				if(isUpdateHolCompany && !Function.nvl(oldObj.getHolCompanyCode(), "").equals(Function.nvl(companyCode, ""))) {
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "他事業会社の情報機器は変更できません"));
				}

				// 使用会社
				if(oldObj == null || !Function.nvl(oldObj.getUseCompanyName(), "").equals(Function.nvl(row.getUseCompanyName(), ""))) { // 登録申請 or 一括更新で項目変更有り
					row.setUseCompanyCode(null);
					if(!Function.nvl(row.getUseCompanyName(), "").equals("")) {
						String useCompanyCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_USE_COMPANY, null, row.getUseCompanyName());
						if(useCompanyCode == null) { // 対応するマスタが見つからない場合はエラー
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "使用-使用会社", row.getUseCompanyName()));
						} else {
							row.setUseCompanyCode(useCompanyCode);
						}
					}
				}

				// 使用部署年度
				if(isAp) {
					row.setUseSectionYear(row.getApSectionYear());
				} else {
					if(isUpdateUseSection) row.setUseSectionYear(currentYear);
				}

				// 使用部署コード0抜け対応
				if(!Function.nvl(row.getUseSectionCode(), "").equals("") && Function.nvl(row.getUseSectionCode(), "").length() < Constants.SECTION_CODE_LENGTH) {
					row.setUseSectionCode(StringUtils.leftPad(row.getUseSectionCode(), Constants.SECTION_CODE_LENGTH, "0"));
				}

				// 使用者
				if(oldObj == null ||
				   !Function.nvl(oldObj.getUseStaffCode(), "").equals(Function.nvl(row.getUseStaffCode(), "")) ||
				   isUpdateUseStaff) { // 登録申請 or 一括更新で項目変更有り
					row.setUseStaffName(null);
					row.setUseStaffCompanyCode(null);
					row.setUseStaffCompanyName(null);
					row.setUseStaffSectionCode(null);
					row.setUseStaffSectionName(null);
					row.setUseStaffSectionYear(null);
					if(!Function.nvl(row.getUseStaffCode(), "").equals("")) {
						User staff = masterSession.getStaff(null, row.getUseStaffCode());
						if(staff != null) {
							row.setUseStaffName(staff.getName());
							row.setUseStaffCompanyCode(staff.getCompanyCode());
							row.setUseStaffCompanyName(staff.getCompanyName());
							row.setUseStaffSectionCode(staff.getSectionCode());
							row.setUseStaffSectionName(staff.getSectionName());
							row.setUseStaffSectionYear(currentYear);
						}
					}
				}

				// 社内実地棚卸対象
				if(Function.nvl(row.getInvInCompanyActualFlagName(), "").toUpperCase().equals(flagYesName.toUpperCase())) {
					row.setInvInCompanyActualFlag(Constants.FLAG_YES);
				} else {
					row.setInvInCompanyActualFlag(Constants.FLAG_NO);
				}

				// シール発行
				if(Function.nvl(row.getInvSealIssueFlagName(), "").toUpperCase().equals(flagYesName.toUpperCase())) {
					row.setInvSealIssueFlag(Constants.FLAG_YES);
				} else {
					row.setInvSealIssueFlag(Constants.FLAG_NO);
				}

				//////////////////////////////////// 明細項目のセット
				// 共有ユーザ
				List<AssetLineComUsr> comUsrlist = new ArrayList<AssetLineComUsr>();
				row.setUseCommonFlag(Constants.FLAG_NO);
				if(!Function.nvl(row.getComStaffCode(), "").equals("") || !Function.nvl(row.getComStaffName(), "").equals("")
						|| !Function.nvl(row.getComCompanyName(), "").equals("")
						|| !Function.nvl(row.getComSectionCode(), "").equals("") || !Function.nvl(row.getComSectionName(), "").equals("")
						|| !Function.nvl(row.getComStartDate(), "").equals("")) {
					AssetLineComUsr lineComUsr = new AssetLineComUsr();

					if(!Function.nvl(row.getComStaffCode(), "").equals("")) {
						// 社員番号が設定されている場合は氏名・所属会社・部署を取得
						User staff = masterSession.getStaff(null, row.getComStaffCode());
						if(staff != null) {
							row.setComStaffName(staff.getName());
							row.setComCompanyCode(staff.getCompanyCode());
							row.setComCompanyName(staff.getCompanyName());
							row.setComSectionCode(staff.getSectionCode());
							row.setComSectionName(staff.getSectionName());
						}
					} else {
						row.setComCompanyCode(null);
						row.setComSectionCode(null);
					}


					lineComUsr.setComStaffCode(row.getComStaffCode());
					lineComUsr.setComStaffName(row.getComStaffName());
					lineComUsr.setComCompanyCode(row.getComCompanyCode());
					lineComUsr.setComCompanyName(row.getComCompanyName());
					lineComUsr.setComSectionCode(row.getComSectionCode());
					lineComUsr.setComSectionName(row.getComSectionName());
					lineComUsr.setComStartDate(row.getComStartDate());

					comUsrlist.add(lineComUsr);

					row.setUseCommonFlag(Constants.FLAG_YES);
				} else {
					row.setUseCommonFlag(Constants.FLAG_NO);
				}
				row.setAssetLineComUsr(comUsrlist);

				// OS
				List<AssetLineOs> osList = new ArrayList<AssetLineOs>();
				if(!Function.nvl(row.getOsName(), "").equals("") || !Function.nvl(row.getOsDescription(), "").equals("")) {
					AssetLineOs lineOs = new AssetLineOs();

					lineOs.setOsName(row.getOsName());
					lineOs.setOsDescription(row.getOsDescription());

					osList.add(lineOs);
				}
				row.setAssetLineOs(osList);

				// ネットワーク
				List<AssetLineNetwork> networkList = new ArrayList<AssetLineNetwork>();
				if(!Function.nvl(row.getNetMacAddress(), "").equals("") || !Function.nvl(row.getNetIpAddress(), "").equals("") || !Function.nvl(row.getNetDescription(), "").equals("")) {
					AssetLineNetwork lineNetwork = new AssetLineNetwork();

					if(!Function.nvl(row.getNetMacAddress(), "").equals("")) row.setNetMacAddress(row.getNetMacAddress().toUpperCase()); // MACアドレスは大文字に変換

					lineNetwork.setNetMacAddress(row.getNetMacAddress());
					lineNetwork.setNetIpAddress(row.getNetIpAddress());
					lineNetwork.setNetDescription(row.getNetDescription());

					networkList.add(lineNetwork);
				}
				row.setAssetLineNetwork(networkList);

				// 棚卸
				List<AssetLineInv> invList = new ArrayList<AssetLineInv>();
				if(row.getInvDate() != null|| !Function.nvl(row.getInvOfficeName(), "").equals("")) {
					AssetLineInv lineInv = new AssetLineInv();

					lineInv.setInvDate(row.getInvDate());
					lineInv.setInvOfficeName(row.getInvOfficeName());

					invList.add(lineInv);
				}
				row.setAssetLineInv(invList);

				//////////////////////////////////// 取得申請との整合性チェック
				if(isAp) { // 登録申請
	 				if(row.getApGetTanLineAstId() != null) { // 取得申請明細リンク
						Asset asset = getApAssetByApGetTan(row.getApGetTanLineAstId());

						// 一般権限の変更不可項目チェック
						if(!Function.isAccessLevelAdmin(accessLevel)) {
							// 取得形態
							if(!Function.nvl(row.getAstGetType(), "").equals(Function.nvl(asset.getAstGetType(), ""))) {
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr + "資産・機器-取得形態", "取得形態には「" + asset.getAstGetTypeName() + "」以外は指定できません。"));
							}

							// 管理区分
							if(!Function.nvl(row.getAstManageType(), "").equals(Function.nvl(asset.getAstManageType(), ""))) {
								errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr + "資産・機器-管理区分", "管理区分は指定できません。"));
							}
						}

						// 保有会社
						if(!Function.nvl(row.getHolCompanyCode(), "").equals(Function.nvl(asset.getHolCompanyCode(), ""))) {
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr + "保有・設置-保有会社", "保有会社には「" + asset.getHolCompanyName() + "」以外は指定できません。"));
						}

						// 使用会社
						if(!Function.nvl(row.getUseCompanyCode(), "").equals(Function.nvl(asset.getUseCompanyCode(), ""))) {
							errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr + "使用-使用会社", "使用会社には「" + asset.getUseCompanyName() + "」以外は指定できません。"));
						}
					}
				}

				assetList.add(row); // リターンに追加

				if(handler.getRowStatus() == CsvReaderRowHandler.ROW_STATUS_ERROR) { // CSV読み込み時のエラー有
					errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + handler.getRowErrorMessage()));
				}

				//////////////////////////////////// 一括更新の場合読み込み件数をログ出力
				if(logId != null) {
					histSession.updateBulkUpdateLog(logId, null, Constants.BULK_UPDATE_STATUS_READ, rowCt - headerCt, null, null);
				}
			} while(true);

			if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

			if(!isAp && isLineUpdate) { // 明細更新の場合
				// 同一情報機器の入力が複数件存在する場合は1件目に全て集約する
				HashMap<String, Asset> objMap = new HashMap<String, Asset>();

				for(int i = 0; i < assetList.size(); i++) {
					Asset currentObj = assetList.get(i);

					if(!objMap.containsKey(currentObj.getAssetId())) {
						objMap.put(currentObj.getAssetId(), currentObj);
					} else {
						Asset firstObj = objMap.get(currentObj.getAssetId());

						// 明細を１件目に追加
						if(currentObj.getAssetLineComUsr().size() > 0) firstObj.getAssetLineComUsr().addAll(currentObj.getAssetLineComUsr());
						if(currentObj.getAssetLineOs().size() > 0) firstObj.getAssetLineOs().addAll(currentObj.getAssetLineOs());
						if(currentObj.getAssetLineNetwork().size() > 0) firstObj.getAssetLineNetwork().addAll(currentObj.getAssetLineNetwork());
						if(currentObj.getAssetLineInv().size() > 0) firstObj.getAssetLineInv().addAll(currentObj.getAssetLineInv());

						currentObj.setAssetId(""); // １件目以外は更新を行わないマーキングとして情報機器管理番号をクリア
					}
				}
			}

			return handler.getHeaderRowCount();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "一括入力CSVファイル読込"), e);
		} finally {
			if(handler != null) handler.close();
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#applyCreateApAssetBulk(java.lang.String, java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean)
	 */
	public List<String> applyCreateApAssetBulk(String loginStaffCode, String accessLevel, String fileId, Asset obj, boolean isVM) throws AppException {
		//////////////////////////////////// 一括作成
		List<String> assetIdList = createApAssetBulk(loginStaffCode, accessLevel, fileId, obj, isVM);

		//////////////////////////////////// バリデーション(申請中ステータスでの)
		StringBuffer errorMessage = new StringBuffer();
		for(int i = 0; i < assetIdList.size(); i++) {
			String rowNumStr = "[" + (i + 1) + "件目] ";

			String assetId = assetIdList.get(i);
			Asset apAsset = getAsset(assetId, true, true);

			apAsset.setApStatus(Constants.AP_STATUS_ASSET_APPLY);
			String rowErrMsg = validateAsset(loginStaffCode, accessLevel, apAsset, null, true, null);
			if(rowErrMsg.length() > 0) {
				errorMessage.append(Msg.getBindMessage(Msg.ERR_MSG, rowNumStr + "以下のエラーがあります。"));
				errorMessage.append(rowErrMsg);
			}
		}

		if(errorMessage.length() > 0) throw new AppException(errorMessage.toString()); // バリデーションエラー有

		//////////////////////////////////// 更新
		Long eappId = null;
		if(!isVM) {
			obj.setAssetId(assetIdList.get(0));
			//	シンクライアントか判断するための値をセット
			Asset apAsset = getAsset(assetIdList.get(0), false, true);
			if(apAsset != null){
				obj.setAstManageType(apAsset.getAstManageType());
				obj.setAstSystemSectionDeployFlag(apAsset.getAstSystemSectionDeployFlag());
			}
			eappId = callEappService(obj); // e申請連携
		}

		for(int i = 0; i < assetIdList.size(); i++) {
			String assetId = assetIdList.get(i);
			Asset apAsset = getAsset(assetId, true, true);

			// e申請ID・ステータスを更新
			apAsset.setEappId(eappId);
			apAsset.setApStatus(Constants.AP_STATUS_ASSET_APPLY);

			updateAsset(loginStaffCode, accessLevel, apAsset, true, false, false, null, true, null);

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME_AP_PREFIX + HIST_ENTITY_NAME, assetId, HIST_OPERATION_APPLY, null);

			//////////////////////////////////// 自動承認・機器登録（仮想機器）
			if(isVM) {
				createAsset(Constants.STAFF_CODE_SYSTEM, accessLevel, apAsset, false); // 機器登録
			}
		}

		return assetIdList;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#bulkApplyApAsset(java.lang.String, java.lang.String, java.util.List)
	 */
	public void bulkApplyApAsset(String loginStaffCode, String accessLevel, List<Asset> apAssetList) throws AppException {
		//////////////////////////////////// バリデーション
		boolean isVMExists = false; // 仮想機器存在チェック
		boolean isNotVMExists = false; // 仮想機器以外存在チェック

		StringBuffer errMsg = new StringBuffer();
		for(int i = 0; i < apAssetList.size(); i++) {
			Asset apAssetListData = apAssetList.get(i);
			Asset apAsset = getAsset(apAssetListData.getAssetId(), true, true);

			// バージョンチェック
			if(apAssetListData.getVersion().intValue() != apAsset.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, apAssetListData.getAssetId(), Msg.ERR_MSG_VER_LIST));
			}

			// ステータスチェック(未申請・差戻しのみ可)
			if(!Function.nvl(apAssetListData.getApStatus(), "").equals(Constants.AP_STATUS_ASSET_NOAPPLY)
				&& !Function.nvl(apAssetListData.getApStatus(), "").equals(Constants.AP_STATUS_ASSET_SENDBACK)) {
				// ステータスエラーは即座にスロー
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG_STAT_LIST, Constants.AP_STATUS_NAME_ASSET_NOAPPLY + "、" + Constants.AP_STATUS_NAME_ASSET_SENDBACK));
			}

			// 仮想機器かそれ以外かの判別
			if(Function.nvl(apAsset.getAstAssetType(), "").equals(Constants.ASSET_TYPE_VM)) {
				isVMExists = true;
			} else {
				isNotVMExists = true;
			}
		}

		// 仮想機器とそうではないものが混在していた場合エラー
		if(isVMExists && isNotVMExists) {
			throw new AppException(Msg.getBindMessage(Msg.ERR_MSG, "仮想機器とそうではないものを、同時に申請できません。"));
		}

		if(errMsg.length() > 0) { // エラーあり
			throw new AppException(errMsg.toString());
		}

		//////////////////////////////////// 申請
		Asset apAssetFirst = apAssetList.get(0);
		Long eappId = null;
		if(!isVMExists) { // 仮想機器以外
			eappId = callEappService(apAssetFirst); // e申請連携
		}

		for(int i = 0; i < apAssetList.size(); i++) {
			String assetId = apAssetList.get(i).getAssetId();
			Asset apAsset = getAsset(assetId, true, true);

			// e申請ID・ステータスを更新
			apAsset.setEappId(eappId);
			apAsset.setApStatus(Constants.AP_STATUS_ASSET_APPLY);

			try {
				updateAsset(loginStaffCode, accessLevel, apAsset, true, false, false, null, true, null);
			} catch (AppException e) {
				String errMessage = e.getMessage();
				errMessage = Msg.getBindMessage(Msg.ERR_MSG, assetId + "の処理中に以下のエラーが発生しました。") + errMessage;
				throw new AppException(errMessage);
			}

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME_AP_PREFIX + HIST_ENTITY_NAME, assetId, HIST_OPERATION_APPLY, null);

			//////////////////////////////////// 自動承認・機器登録（仮想機器）
			if(isVMExists) {
				try {
					createAsset(Constants.STAFF_CODE_SYSTEM, accessLevel, apAsset, false); // 機器登録
				} catch (AppException e) {
					String errMessage = e.getMessage();
					errMessage = Msg.getBindMessage(Msg.ERR_MSG, assetId + "の仮想機器登録処理中に以下のエラーが発生しました。") + errMessage;
					throw new AppException(errMessage);
				}
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#bulkDeleteApAsset(java.lang.String, java.lang.String, java.util.List)
	 */
	public void bulkDeleteApAsset(String loginStaffCode, String accessLevel, List<Asset> apAssetList) throws AppException {
		for(int i = 0; i < apAssetList.size(); i++) {
			Asset apAsset = apAssetList.get(i);

			//////////////////////////////////// バリデーション
			// ステータスチェック(未申請・差戻しのみ可)
			if(!Function.nvl(apAsset.getApStatus(), "").equals(Constants.AP_STATUS_ASSET_NOAPPLY)
				&& !Function.nvl(apAsset.getApStatus(), "").equals(Constants.AP_STATUS_ASSET_SENDBACK)) {
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG_STAT_LIST, Constants.AP_STATUS_NAME_ASSET_NOAPPLY + "、" + Constants.AP_STATUS_NAME_ASSET_SENDBACK));
			}

			//////////////////////////////////// 更新
			try {
				deleteAsset(loginStaffCode, accessLevel, apAsset, true);
			} catch (AppException e) {
				String errMessage = e.getMessage();
				errMessage = Msg.getBindMessage(Msg.ERR_MSG, apAsset.getAssetId() + "の処理中に以下のエラーが発生しました。") + errMessage;
				throw new AppException(errMessage);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#bulkCreateAsset(java.lang.String, java.lang.String, java.util.List)
	 */
	public void bulkCreateAsset(String loginStaffCode, String accessLevel, List<Asset> apAssetList) throws AppException {
		StringBuffer errMsg = new StringBuffer();
		for(int i = 0; i < apAssetList.size(); i++) {
			Asset apAssetListData = apAssetList.get(i);
			Asset apAsset = getAsset(apAssetListData.getAssetId(), true, true);

			//////////////////////////////////// バリデーション
			// バージョンチェック
			if(apAssetListData.getVersion().intValue() != apAsset.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, apAssetListData.getAssetId(), Msg.ERR_MSG_VER_LIST));
			}

			// ステータスチェック(申請中のみ可)
			if(!Function.nvl(apAssetListData.getApStatus(), "").equals(Constants.AP_STATUS_ASSET_APPLY)) {
				// ステータスエラーは即座にスロー
				throw new AppException(Msg.getBindMessage(Msg.ERR_MSG_STAT_LIST, Constants.AP_STATUS_NAME_ASSET_APPLY));
			}

			//////////////////////////////////// 更新
			if(errMsg.length() == 0) {
				try {
					createAsset(loginStaffCode, accessLevel, apAsset, false);
				} catch (AppException e) {
					String errMessage = e.getMessage();
					errMessage = Msg.getBindMessage(Msg.ERR_MSG, apAssetListData.getAssetId() + "の処理中に以下のエラーが発生しました。") + errMessage;
					throw new AppException(errMessage);
				}
			}
		}

		if(errMsg.length() > 0) { // エラーあり
			throw new AppException(errMsg.toString());
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#getApAssetVMByAsset(java.lang.String)
	 */
	public Asset getApAssetVMByAsset(String assetId) {
		Asset obj = getAsset(assetId, false);

		if(obj != null) {
			String flagNoName = masterSession.getCodeName(Constants.CATEGORY_CODE_FLAG_YN, Constants.FLAG_NO, null, null).getValue1();

			//////////////////////////////////// クリア項目
			obj.setAssetId(null);
			obj.setGetApplicationId(null);
			obj.setContractNum(null); // 契約番号
			obj.setDreamsNum(null); // DREAMS番号
			obj.setAstSystemSectionDeployFlag(Constants.FLAG_NO); // 情報システム部配備
			obj.setAstSystemSectionDeployFlagName(flagNoName);
			obj.setAstHolderCode(null); // 資産保有者
			obj.setAstHolderName(null);
			obj.setAstOir(null); // OIR
			obj.setAstOirEnable(null);
			obj.setAstManageType(null); // 管理区分
			obj.setAstManageTypeName(null);
			obj.setUseCommonFlag(Constants.FLAG_NO); // 共有ユーザー
			obj.setUseCommonFlagName(flagNoName);
			obj.setMntContractCode(null); // 保守契約情報
			obj.setMntContractCompanyName(null);
			obj.setMntContractStartDate(null);
			obj.setMntContractEndDate(null);
			obj.setMntContractAmount(null);
			obj.setMntContractRegistCode(null);
			obj.setMntContractRegistDate(null);
			obj.setMntContractStaffCode(null);
			obj.setMntContractStaffName(null);
			obj.setNetHostName(null); // ホスト名
			obj.setDscAttribute11(null); // 管理項目(管理者用)
			obj.setDscAttribute12(null);
			obj.setDscAttribute13(null);
			obj.setDscAttribute14(null);
			obj.setDscAttribute15(null);
			obj.setDscAttribute16(null);
			obj.setDscAttribute17(null);
			obj.setDscAttribute18(null);
			obj.setDscAttribute19(null);
			obj.setDscAttribute20(null);
			obj.setInvInCompanyActualFlag(Constants.FLAG_NO); // 社内実地棚卸対象
			obj.setInvInCompanyActualFlagName(flagNoName);
			obj.setInvSealIssueFlag(Constants.FLAG_NO); // シール発行情報
			obj.setInvSealIssueFlagName(flagNoName);
			obj.setInvSealIssueDate(null);
			obj.setInvSealIssueDescription(null);
			obj.setInvBarcode(null); // バーコード情報
			obj.setDscFailureAssetId(null); // 故障交換元


			// 明細クリア
			obj.setAssetLineComUsr(new ArrayList<AssetLineComUsr>());
			obj.setAssetLineOs(new ArrayList<AssetLineOs>());
			obj.setAssetLineNetwork(new ArrayList<AssetLineNetwork>());
			obj.setAssetLineInv(null);

			//////////////////////////////////// 自動セット項目
			// 親情報機器管理番号
			obj.setParentAssetId(assetId);

			// 取得形態
			CodeName astGetType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_GET_TYPE, Constants.ASSET_GET_TYPE_VM, null, null);
			obj.setAstGetType(astGetType.getInternalCode());
			obj.setAstGetTypeName(astGetType.getValue1());

			// 資産区分
			CodeName astAssetType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_TYPE, Constants.ASSET_TYPE_VM, null, null);
			obj.setAstAssetType(astAssetType.getInternalCode());
			obj.setAstAssetTypeName(astAssetType.getValue1());

			// 数量
			obj.setHolQuantity(1);
		}

		return obj;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createApAssetVMCsvByAsset(java.lang.String, java.lang.String, java.util.List)
	 */
	public String createApAssetVMCsvByAsset(String loginStaffCode, String accessLevel, List<Asset> assetList) {
		CsvWriterRowHandler handler = null;
		try {
			StringBuffer headerRow = new StringBuffer();
			List<String> propList = new ArrayList<String>();
			List<Format> propFormatList = new ArrayList<Format>();

			new MasterDAO().setCsvDefForUpload(Constants.CATEGORY_CODE_ITEM_DEF_AP_ASSET, accessLevel, headerRow, propList, propFormatList);

			handler = new CsvWriterRowHandler(headerRow.toString(), (String[]) propList.toArray(new String[0]), (Format[]) propFormatList.toArray(new Format[0]));

			StringBuffer assetIdList = new StringBuffer();
			for(int i = 0; i < assetList.size(); i++) {
				// 取得申請資産(機器)明細取得
				Asset asset = getApAssetVMByAsset(assetList.get(i).getAssetId());

				// 操作ログ用
				if(assetIdList.length() > 0) assetIdList.append(" ");
				assetIdList.append(assetList.get(i).getAssetId());

				handler.handleRow(asset); // 行出力
			}

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_ASSET_VM, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",assetId:" + assetIdList);

			return handler.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "登録申請作成用CSVファイル作成"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "登録申請作成用CSVファイル作成"), e);
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#validateWarningAsset(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.asset.Asset, boolean)
	 */
	public String validateWarningAsset(String loginStaffCode, String accessLevel, Asset obj, boolean isAp) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 重複確認
		AssetDAO assetDAO = new AssetDAO();
		try {
			// 情報機器等データ重複チェック
			AssetSR dupAsset = assetDAO.selectDuplicateAsset(obj, false);
			StringBuffer dupProps = new StringBuffer();
			if(dupAsset != null) {
				if(dupAsset.getAstOir() != null) {
					if(dupProps.length() > 0) dupProps.append(",");
					dupProps.append("OIR");
				}
				if(dupAsset.getAstSerialCode() != null) {
					if(dupProps.length() > 0) dupProps.append(",");
					dupProps.append("シリアル番号");
				}
				if(dupAsset.getNetMacAddress() != null) {
					if(dupProps.length() > 0) dupProps.append(",");
					dupProps.append("MACアドレス");
				}
				if(dupAsset.getNetIpAddress() != null) {
					if(dupProps.length() > 0) dupProps.append(",");
					dupProps.append("IPアドレス");
				}
				if(dupAsset.getNetHostName() != null) {
					if(dupProps.length() > 0) dupProps.append(",");
					dupProps.append("ホスト名");
				}

				if(dupProps.length() > 0) errMsg.append("指定された " + dupProps.toString() + " の情報機器等");
			}

			if(isAp) {
				AssetSR dupApAsset = assetDAO.selectDuplicateAsset(obj, true);

				// 情報機器等登録申請データ重複チェック
				StringBuffer dupApProps = new StringBuffer();
				if(dupApAsset != null) {
					if(dupApAsset.getAstOir() != null) {
						if(dupApProps.length() > 0) dupApProps.append(",");
						dupApProps.append("OIR");
					}
					if(dupApAsset.getAstSerialCode() != null) {
						if(dupApProps.length() > 0) dupApProps.append(",");
						dupApProps.append("シリアル番号");
					}
					if(dupApAsset.getNetMacAddress() != null) {
						if(dupApProps.length() > 0) dupApProps.append(",");
						dupApProps.append("MACアドレス");
					}
					if(dupApAsset.getNetIpAddress() != null) {
						if(dupApProps.length() > 0) dupApProps.append(",");
						dupApProps.append("IPアドレス");
					}
					if(dupApAsset.getNetHostName() != null) {
						if(dupApProps.length() > 0) dupApProps.append(",");
						dupApProps.append("ホスト名");
					}

					if(dupApProps.length() > 0) {
						if(errMsg.length() > 0) errMsg.append("と、");
						errMsg.append("指定された " + dupApProps.toString() + " の情報機器等登録申請(申請中)");
					}
				}
			}

			if(errMsg.length() > 0) errMsg.append("が既に存在しますがよろしいですか？");

			return errMsg.toString();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "登重複データチェック"), e);
		}
	}

	/**
	 * 登録申請作成時の取得申請明細バリデーション
	 * @param obj 登録申請データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateApGeTanLineAst(Asset obj) {
		StringBuffer errMsg = new StringBuffer();

		// 取得申請明細 <-> 登録申請の整合性チェック
		if(!Function.nvl(obj.getGetApplicationId(), "").equals("")) {
			errMsg = new StringBuffer(Msg.getBindMessage(Msg.ERR_MSG, "該当する取得申請明細が見つかりません。"));
			// 取得申請取得(&ロック)
			ApGetTan apGetTanObj = apGetTanSession.getApGetTan(obj.getGetApplicationId(), true);
			// 取得申請の該当明細確認
			if(obj.getApGetTanLineAstId() != null) {
				if(apGetTanObj != null) {
					List<ApGetTanLineAst> list = apGetTanObj.getApGetTanLineAstList();
					if(list != null && list.size() > 0) {
						for(int i = 0; i < list.size(); i++) {
							ApGetTanLineAst lineAstObj = list.get(i);
							Long lineAstId = Function.nvl(lineAstObj.getApGetTanLineAstId(),Long.valueOf(-1));

							// 取得申請に明細あり
							if(lineAstId.longValue() == obj.getApGetTanLineAstId()) {
								// 登録不要かどうかの判別
								if(Function.nvl(lineAstObj.getNoRegistFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {
									// 取得数と登録数の比較
									int qty = Function.nvl(lineAstObj.getQuantity(), Integer.valueOf(0)).intValue();
									int regQty = Function.nvl(lineAstObj.getRegistQuantity(), Integer.valueOf(0)).intValue();
									int holQty = Function.nvl(obj.getHolQuantity(), Integer.valueOf(0)).intValue();

									if(qty - regQty >= holQty) {
										errMsg = new StringBuffer(); // 登録可能
										break;
									} else {
										errMsg = new StringBuffer(Msg.getBindMessage(Msg.ERR_MSG, "取得申請明細の取得数を超えてしまうのでこれ以上登録できません。")); // 残数量無
										break;
									}
								}
							}
						}
					}
				}
			}
		}

		return errMsg.toString();
	}

	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @param objOld 変更前データオブジェクト(新規の場合はnullを指定)：割当済OS明細のチェック、一部項目変更判別に使用
	 * @param isAp true:情報機器等登録申請,false:情報機器等
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateAsset(String loginStaffCode, String accessLevel, Asset obj, Asset objOld, boolean isAp, Set<String> updatePropSet) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 修正権限バリデーション
		if(!Function.nvl(obj.getAssetId(), "").equals("")) { // 修正
			if(!isEditableAsset(loginStaffCode, accessLevel, obj.getAssetId(), isAp)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_EDIT_AUTH));
				return errMsg.toString();
			}
		}

		//////////////////////////////////// 項目定義バリデーション
		int apStatus;
		String itemDefName;

		if(isAp) { // 登録申請
			apStatus = obj.getApStatus() == null ? null : Integer.valueOf(obj.getApStatus());

			if(!Function.isAccessLevelAdmin(accessLevel)) { // 全社権限ではない場合
				apStatus += 4;
			}

			itemDefName = Constants.CATEGORY_CODE_ITEM_DEF_AP_ASSET;
		} else { // 情報機器
			apStatus = Function.nvl(obj.getDeleteFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO) ? 1 : 2;

			if(!Function.isAccessLevelAdmin(accessLevel)) { // 全社権限ではない場合
				apStatus += 2;
			}

			itemDefName = Constants.CATEGORY_CODE_ITEM_DEF_ASSET;
		}


		// ヘッダ
		errMsg.append(masterSession.validateItemDef(itemDefName, "NEA_" + (isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + "ASSET", obj, apStatus, updatePropSet));

		// 明細
		List<AssetLineComUsr> comUsrLine = obj.getAssetLineComUsr();
		if(comUsrLine != null && comUsrLine.size() > 0) {
			for(int i = 0; i < comUsrLine.size(); i++) {
				AssetLineComUsr comUsrObj = comUsrLine.get(i);
				errMsg.append(masterSession.validateItemDef(itemDefName, "NEA_" + (isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + "ASSET_LINE_COM_USR", comUsrObj, apStatus, null));
			}
		}
		List<AssetLineOs> osLine = obj.getAssetLineOs();
		if(osLine != null && osLine.size() > 0) {
			for(int i = 0; i < osLine.size(); i++) {
				AssetLineOs osObj = osLine.get(i);
				errMsg.append(masterSession.validateItemDef(itemDefName, "NEA_" + (isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + "ASSET_LINE_OS", osObj, apStatus, null));
			}
		}
		List<AssetLineNetwork> networkLine = obj.getAssetLineNetwork();
		if(networkLine != null && networkLine.size() > 0) {
			for(int i = 0; i < networkLine.size(); i++) {
				AssetLineNetwork networkObj = networkLine.get(i);
				errMsg.append(masterSession.validateItemDef(itemDefName, "NEA_" + (isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + "ASSET_LINE_NETWORK", networkObj, apStatus, null));
			}
		}
		if(!isAp) {
			List<AssetLineInv> invLine = obj.getAssetLineInv();
			if(invLine != null && invLine.size() > 0) {
				for(int i = 0; i < invLine.size(); i++) {
					AssetLineInv invObj = invLine.get(i);
					errMsg.append(masterSession.validateItemDef(itemDefName, "NEA_" + (isAp ? HIST_ENTITY_NAME_AP_PREFIX : "") + "ASSET_LINE_INV", invObj, apStatus, null));
				}
			}
		}

		//////////////////////////////////// 条件付必須バリデーション
		// 資産カテゴリ取得
		if(!Function.nvl(obj.getAstCategory2Code(), "").equals("")) {
			CodeName astCategory2 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, obj.getAstCategory2Code(), null, null);

			if(astCategory2 != null) {
				// 資産カテゴリが数量管理の場合
				if(!Function.nvl(astCategory2.getValue2(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) {
					if(updatePropSet == null
					|| (updatePropSet != null && updatePropSet.contains("astMakerName"))
					|| (updatePropSet != null && updatePropSet.contains("astCategory2Code"))
					){
						// メーカー
						if(Function.nvl(obj.getAstMakerName(), "").equals("")) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "資産・機器-メーカー", "該当資産(機器)分類では"));
						}
					}
					// メーカー型番
					if(updatePropSet == null
					|| (updatePropSet != null && updatePropSet.contains("astMakerModel"))
					|| (updatePropSet != null && updatePropSet.contains("astCategory2Code"))
					){
						if(Function.nvl(obj.getAstMakerModel(), "").equals("")) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "資産・機器-メーカー型番", "該当資産(機器)分類では"));
						}
					}
				}

				// 資産カテゴリがシリアル番号管理の場合
				if(Function.nvl(astCategory2.getValue5(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) {
					if(updatePropSet == null
					|| (updatePropSet != null && updatePropSet.contains("astSerialCode"))
					|| (updatePropSet != null && updatePropSet.contains("astCategory2Code"))
					){
						// シリアル番号
						if(Function.nvl(obj.getAstSerialCode(), "").equals("")) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "資産・機器-シリアル番号", "該当資産(機器)分類では"));
						}
					}
				}
			}
		}

		// リースレンタル会社取得
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("astHolderCode")) || (updatePropSet != null && updatePropSet.contains("astOir")) ){
			if(!Function.nvl(obj.getAstHolderCode(), "").equals("")) {
				CodeName leReCompany = masterSession.getCodeName(Constants.CATEGORY_CODE_LEASE_RENTAL_CUSTOMER, obj.getAstHolderCode(), obj.getHolCompanyCode(), null);

				// 資産保有者がオリックスの場合
				if(leReCompany != null && Function.nvl(leReCompany.getValue7(), "").equals(Constants.FLAG_YES)) {
					// OIR
					if(Function.nvl(obj.getAstOir(), "").equals("")) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "資産・機器-OIR", "該当資産保有者では"));
					}
				}
			}
		}

		// 共有ユーザ有りの場合
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("assetLineComUsr")) ){
			if(Function.nvl(obj.getUseCommonFlag(), "").equals(Constants.FLAG_YES)) {
				if(comUsrLine == null || comUsrLine.size() == 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "使用-共有ユーザー", "一件以上"));
				} else {
					for(int i = 0; i < comUsrLine.size(); i++) {
						AssetLineComUsr comUsrObj = comUsrLine.get(i);
						if(Function.nvl(comUsrObj.getComStaffCode(), "").equals("") && Function.nvl(comUsrObj.getComStaffName(), "").equals("")) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "使用-共有ユーザー" + comUsrObj.getLineSeq() + "]", "社員番号もしくは氏名を入力してください。"));
						}
					}
				}
			}
		}

		// ネットワークのIP or MACどちらかは要入力
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("assetLineNetwork")) ){
			if(networkLine != null && networkLine.size() > 0) {
				for(int i = 0; i < networkLine.size(); i++) {
					AssetLineNetwork networkObj = networkLine.get(i);
					if(Function.nvl(networkObj.getNetMacAddress(), "").equals("") && Function.nvl(networkObj.getNetIpAddress(), "").equals("")) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ＯＳ・ネットワーク-ネットワーク[No" + networkObj.getLineSeq() + "]", "MACアドレスかIPアドレスのどちらかは入力してください。"));
					}
				}
			}
		}

		//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)
		// 申請者
		if(isAp) {
			if(!Function.nvl(obj.getApStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_ASSET_APPROVE)) {
					// 承認済は退職社員OK
					if(masterSession.getStaff(obj.getApCompanyCode(), obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				} else {
					// 承認済以外は退職社員NG
					if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				}
			}
		}

		// 取得・設置者(退職社員OK)
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("holGetStaffCode")) ){
			if(!Function.nvl(obj.getHolGetStaffCode(), "").equals("")) {
				if(masterSession.getStaff(null, obj.getHolGetStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有・設置-取得(設置)者"));
				}
			}
		}

		// 使用者
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("useStaffCode")) ){
			if(!Function.nvl(obj.getUseStaffCode(), "").equals("")) {
				if(masterSession.getStaffValid(null, obj.getUseStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "使用-使用者"));
				}
			}
		}

		// 共有ユーザ
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("assetLineComUsr")) ){
			if(comUsrLine != null && comUsrLine.size() > 0) {
				for(int i = 0; i < comUsrLine.size(); i++) {
					AssetLineComUsr comUsrObj = comUsrLine.get(i);
					if(!Function.nvl(comUsrObj.getComStaffCode(), "").equals("")) {
						if(masterSession.getStaffValid(null, comUsrObj.getComStaffCode()) == null) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "使用-共有ユーザー[No" + comUsrObj.getLineSeq() + "]-社員番号"));
						}
					}
				}
			}
		}

		// 保守契約担当者(退職社員OK)
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("mntContractStaffCode")) ){
			if(!Function.nvl(obj.getMntContractStaffCode(), "").equals("")) {
				if(masterSession.getStaff(obj.getHolCompanyCode(), obj.getMntContractStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保守契約-保守契約担当者"));
				}
			}
		}
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("mntContractStaffCode2")) ){
			if(!Function.nvl(obj.getMntContractStaffCode2(), "").equals("")) {
				if(masterSession.getStaff(obj.getHolCompanyCode(), obj.getMntContractStaffCode2()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保守契約-保守契約担当者(2)"));
				}
			}
		}
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("mntContractStaffCode3")) ){
			if(!Function.nvl(obj.getMntContractStaffCode3(), "").equals("")) {
				if(masterSession.getStaff(obj.getHolCompanyCode(), obj.getMntContractStaffCode3()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保守契約-保守契約担当者(3)"));
				}
			}
		}

		//////////////////////////////////// マスタ整合性バリデーション2(CSV入力を考慮した整合性チェック)
		// 親情報機器管理番号の整合性
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("parentAssetId")) ){
			if(!Function.nvl(obj.getParentAssetId(), "").equals("")) {
				Asset parent = getAsset(obj.getParentAssetId(), false);
				if(parent == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "親情報機器管理番号"));
				} else if(Function.nvl(parent.getDeleteFlag(), "").equals(Constants.FLAG_YES)) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "親情報機器管理番号", "抹消済みの資産(機器)は指定できません。"));
				}
			}
		}

		// 取得形態 <-> 資産区分の整合性
		if(isAp
			|| (
				objOld != null
				&&(
					!Function.nvl(objOld.getAstAssetType(), "").equals(Function.nvl(obj.getAstAssetType(), ""))
					|| !Function.nvl(objOld.getAstGetType(), "").equals(Function.nvl(obj.getAstGetType(), ""))
				)

			)
		) { // 登録申請 or 項目変更有
			String assetType = Function.nvl(obj.getAstAssetType(), "");
			String assetGetType = Function.nvl(obj.getAstGetType(), "");
			if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("astAssetType")) || (updatePropSet != null && updatePropSet.contains("astGetType")) ){
				if(!assetType.equals("") && !assetGetType.equals("")) {
					ArrayList<String> valueParamList = new ArrayList<String>();
					valueParamList.add(null); // value1
					valueParamList.add(assetGetType); // value2
					CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_TYPE, assetType, null, valueParamList);

					if(cn == null) {
						valueParamList.clear();
						valueParamList.add(null); // value1
						valueParamList.add(null); // value2
						valueParamList.add(assetGetType); // value3
						cn = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_TYPE, assetType, null, valueParamList);
					}
					if(cn == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産・機器-資産区分", "取得形態「" + obj.getAstGetTypeName() + "」では資産区分「" + obj.getAstAssetTypeName() + "」は設定できません。"));
					}
				}
			}
		}

		// 保有部署
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("holSectionCode")) ){
			if(!Function.nvl(obj.getHolSectionCode(), "").equals("")) {
				Section sec = masterSession.getSection(obj.getHolCompanyCode(), obj.getHolSectionCode(), obj.getHolSectionYear());
				if(sec == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有・設置-保有部署"));
				}
			}
		}

		// 資産管理担当者(退職社員OK)
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("holStaffCode")) ){
			if(!Function.nvl(obj.getHolStaffCode(), "").equals("")) {
				if(masterSession.getStaff(null, obj.getHolStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "保有・設置-資産管理担当者"));
				}
			}
		}

		// 使用部署
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("useSectionCode")) ){
			if(!Function.nvl(obj.getUseSectionCode(), "").equals("")) {
				Section sec = masterSession.getSection(obj.getUseCompanyCode(), obj.getUseSectionCode(), obj.getUseSectionYear());
				if(sec == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "使用-使用部署"));
				}
			}
		}

		//////////////////////////////////// その他バリデーション
		// IP/MACアドレス正規表現チェック
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("assetLineNetwork")) ){
			if(networkLine != null && networkLine.size() > 0) {
				for(int i = 0; i < networkLine.size(); i++) {
					AssetLineNetwork networkObj = networkLine.get(i);

					String netMacAddress = Function.nvl(networkObj.getNetMacAddress(), "");
					if(!netMacAddress.equals("")) {
						String val = "([0-9A-F][0-9A-F])";
						String sep = "-";
						if(!netMacAddress.matches(val + sep + val + sep + val + sep + val + sep + val + sep + val)) {
							if(networkObj.getLineSeq() != null) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "ＯＳ・ネットワーク-ネットワーク[No" + networkObj.getLineSeq() + "]-MACアドレス"));
							} else {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, "ＯＳ・ネットワーク-ネットワーク-MACアドレス", netMacAddress));
							}
						}
					}
					String netIpAddress = Function.nvl(networkObj.getNetIpAddress(), "");
					if(!netIpAddress.equals("")) {
						String val = "(\\d|[01]?\\d\\d|2[0-4]\\d|25[0-5])";
						String sep = "\\.";
						if(!netIpAddress.matches(val + sep + val + sep + val + sep + val)) {
							if(networkObj.getLineSeq() != null) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "ＯＳ・ネットワーク-ネットワーク[No" + networkObj.getLineSeq() + "]-IPアドレス"));
							} else {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, "ＯＳ・ネットワーク-ネットワーク-IPアドレス", netIpAddress));
							}
						}
					}
				}
			}
		}

		// 割当済OS明細が削除されていた場合はエラー
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("assetLineOs")) ){
			if(!isAp && objOld != null) { // 登録申請ではなく変更前情報有
				List<AssetLineOs> osLineOld = objOld.getAssetLineOs();
				if(osLineOld != null && osLineOld.size() > 0) { // 変更前情報にOS明細有り
					// 変更後明細にも該当OS明細があるか判定
					for(int i = 0; i < osLineOld.size(); i++) {
						boolean isOk = false; // 判定

						AssetLineOs osOld = osLineOld.get(i);
						Long assetLineOsIdOld = osOld.getAssetLineOsId();

						// 変更後明細にも該当OS明細があるか判定
						if(osLine != null && osLine.size() > 0) {
							for(int j = 0; j < osLine.size(); j++) {
								AssetLineOs osNew = osLine.get(j);
								Long assetLineOsIdNew = Function.nvl(osNew.getAssetLineOsId(),Long.valueOf(-1));

								if(assetLineOsIdOld.longValue() == assetLineOsIdNew.longValue()) { // 変更後にも同明細有
									isOk = true;
									break;
								}
							}
						}


						if(!isOk) { // OS明細が削除されている
							List<LicenseAlloc> allocList = licenseSession.getLicenseAllocList(assetLineOsIdOld);
							if(allocList == null || allocList.size() == 0) { // 変更前情報にライセンス割当があるかチェック
								isOk = true; // 割当無し
							}
						}

						if(!isOk) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "ＯＳ・ネットワーク-ＯＳ[No" + osOld.getLineSeq() + "]", "ライセンス割当済のＯＳ明細は削除できません。再度読み込み直して編集してください。"));
						}
					}

				}
			}
		}

		// 日付範囲チェック 保守契約期間From > 保守契約期間To
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("mntContractStartDate")) || (updatePropSet != null && updatePropSet.contains("mntContractEndDate"))){
			if(obj.getMntContractStartDate() != null && obj.getMntContractEndDate() != null) {
				if(obj.getMntContractStartDate().compareTo(obj.getMntContractEndDate()) > 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "保守契約-契約期間(至)", "契約期間(自)"));
				}
			}
		}
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("mntContractStartDate2")) || (updatePropSet != null && updatePropSet.contains("mntContractEndDate2"))){
			if(obj.getMntContractStartDate2() != null && obj.getMntContractEndDate2() != null) {
				if(obj.getMntContractStartDate2().compareTo(obj.getMntContractEndDate2()) > 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "保守契約-契約期間(2)(至)", "契約期間(2)(自)"));
				}
			}
		}
		if(updatePropSet == null || (updatePropSet != null && updatePropSet.contains("mntContractStartDate3")) || (updatePropSet != null && updatePropSet.contains("mntContractEndDate3"))){
			if(obj.getMntContractStartDate3() != null && obj.getMntContractEndDate3() != null) {
				if(obj.getMntContractStartDate3().compareTo(obj.getMntContractEndDate3()) > 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "保守契約-契約期間(3)(至)", "契約期間(3)(自)"));
				}
			}
		}

		// 親情報機器 = 情報機器はエラー
/*
		if(!Function.nvl(obj.getAssetId(), "").equals("") && !Function.nvl(obj.getParentAssetId(), "").equals("")) {
			if(obj.getAssetId().equals(obj.getParentAssetId())) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "親情報機器管理番号", "自身の番号は指定できません。"));
			}
		}
*/
		return errMsg.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#isEditableAsset(java.lang.String, java.lang.String, java.lang.String, boolean)
	 */
	public boolean isEditableAsset(String loginStaffCode, String accessLevel, String assetId, boolean isAp) {
		boolean ret = false;

		Asset obj = getAsset(assetId, isAp); // データ取得

		String holCompanyCode = Function.nvl(obj.getHolCompanyCode(), ""); // 保有会社
		String holSectionCode = Function.nvl(obj.getHolSectionCode(), ""); // 保有部署
		int holSectionYear = Function.nvl(obj.getHolSectionYear(), 0); // 保有部署年度
		String useCompanyCode = Function.nvl(obj.getUseCompanyCode(), ""); // 使用会社
		String useSectionCode = Function.nvl(obj.getUseSectionCode(), ""); // 使用部署
		int useSectionYear = Function.nvl(obj.getUseSectionYear(), 0); // 使用部署年度
		String useStaffCode = Function.nvl(obj.getUseStaffCode(), ""); // 使用者
		String apStaffCode = Function.nvl(obj.getApStaffCode(), ""); // 申請者
		String apCreateStaffCode = Function.nvl(obj.getApCreateStaffCode(), ""); // 記票者

		if(Constants.STAFF_CODE_SYSTEM.equals(loginStaffCode)) {
			ret = true; // システム自動処理ユーザ
		} else if(useStaffCode.equals(loginStaffCode)
			|| apStaffCode.equals(loginStaffCode)
			|| apCreateStaffCode.equals(loginStaffCode)) {
			ret = true; // 使用者/申請者/起票者が自分の機器は修正可能
		} else {
			// 保有会社/部署によるアクセスレベル取得
			if(Function.isAccessLevelAdmin(accessLevel)) { // 全社権限
				String level = masterSession.getAccessLevel(Constants.MENU_ID_ASSET_SEARCH, loginStaffCode, holCompanyCode, null, 0);
				if(Function.isAccessLevelAdmin(level)) {
					ret = true;
				}
			} else if(Function.isAccessLevelSection(accessLevel)) { // 資産管理担当者/部署長
				String level = masterSession.getAccessLevel(Constants.MENU_ID_ASSET_SEARCH, loginStaffCode, holCompanyCode, holSectionCode, holSectionYear);
				if(Function.isAccessLevelSection(level)) ret = true;
			}

			// 保有部署で権限が無ければ使用部署でもアクセスレベル取得
			if(!ret) {
				if(Function.isAccessLevelAdmin(accessLevel)) { // 全社権限
					String level = masterSession.getAccessLevel(Constants.MENU_ID_ASSET_SEARCH, loginStaffCode, useCompanyCode, null, 0);
					if(Function.isAccessLevelAdmin(level)) ret = true;
				} else if(Function.isAccessLevelSection(accessLevel)) { // 資産管理担当者/部署長
					String level = masterSession.getAccessLevel(Constants.MENU_ID_ASSET_SEARCH, loginStaffCode, useCompanyCode, useSectionCode, useSectionYear);
					if(Function.isAccessLevelSection(level)) ret = true;
				}
			}

			// eAsset利用停止会社の全社権限修正許可
			if(!ret && Function.isAccessLevelAdmin(accessLevel)) {
				if(holCompanyCode.equals("")) { // 会社空白
					ret = true;
				} else {
					CodeName holCompany = masterSession.getCodeName(Constants.CATEGORY_CODE_USE_COMPANY, holCompanyCode, null, null);
					if(holCompany == null) ret = true; // eAsset利用停止
				}

				if(!ret) {
					if(useCompanyCode.equals("")) { // 会社空白
						ret = true;
					} else {
						CodeName useCompany = masterSession.getCodeName(Constants.CATEGORY_CODE_USE_COMPANY, useCompanyCode, null, null);
						if(useCompany == null) ret = true; // eAsset利用停止
					}
				}
			}
		}

		return ret;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#getUpdatePropertyList(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<CodeName> getUpdatePropertyList(String loginStaffCode, String accessLevel, String fileId) throws AppException {
		CsvReaderRowHandler handler = null;
		try {
			// ファイル内の有効更新項目取得
			handler = new CsvReaderRowHandler(accessLevel, fileId, Asset.class, Constants.CATEGORY_CODE_ITEM_DEF_ASSET);
			String[] inputProps = handler.getInputProps(); // 更新可能プロパティ取得
			HashSet<String> inputPropSet = new HashSet<String>();

			if(inputProps != null && inputProps.length > 0) {
				for(int i = 0; i < inputProps.length; i++) {
					inputPropSet.add(inputProps[i]);
				}
			}

			// DB上の更新項目一覧から、有効なもの意外を除外
			List<CodeName> propList = null;

			// 共有ユーザー明細項目
			propList = masterSession.getDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_ASSET, "NEA_ASSET_LINE_COM_USR");
			for(int i = (propList.size() - 1); i >= 0; i--) {
				CodeName row = propList.get(i);

				// ファイル内有効更新項目以外
				if(!inputPropSet.contains(row.getValue3())) {
					propList.remove(i);
					continue;
				}

			}

			// OS明細項目
			if(propList == null || propList.size() == 0) {
				propList = masterSession.getDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_ASSET, "NEA_ASSET_LINE_OS");
				for(int i = (propList.size() - 1); i >= 0; i--) {
					CodeName row = propList.get(i);

					// ファイル内有効更新項目以外
					if(!inputPropSet.contains(row.getValue3())) {
						propList.remove(i);
						continue;
					}

				}
			}

			// ネットワーク明細項目
			if(propList == null || propList.size() == 0) {
				propList = masterSession.getDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_ASSET, "NEA_ASSET_LINE_NETWORK");
				for(int i = (propList.size() - 1); i >= 0; i--) {
					CodeName row = propList.get(i);

					// ファイル内有効更新項目以外
					if(!inputPropSet.contains(row.getValue3())) {
						propList.remove(i);
						continue;
					}

				}
			}

			// 棚卸し明細項目
			if(propList == null || propList.size() == 0) {
				propList = masterSession.getDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_ASSET, "NEA_ASSET_LINE_INV");
				for(int i = (propList.size() - 1); i >= 0; i--) {
					CodeName row = propList.get(i);

					// ファイル内有効更新項目以外
					if(!inputPropSet.contains(row.getValue3())) {
						propList.remove(i);
						continue;
					}

				}
			}

			// ヘッダ項目
			if(propList == null || propList.size() == 0) {
				propList = masterSession.getDownloadItemList(Constants.CATEGORY_CODE_ITEM_DEF_ASSET, null);
				for(int i = (propList.size() - 1); i >= 0; i--) {
					CodeName row = propList.get(i);

					// ファイル内有効更新項目以外
					if(!inputPropSet.contains(row.getValue3())) {
						propList.remove(i);
						continue;
					}
				}
			}

			return propList;
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#createEGuardAsset(jp.co.ctcg.easset.dto.asset.Asset)
	 */
	public boolean createEGuardAsset(Asset obj) {
		boolean ret = false;

		if(obj != null && !Function.nvl(obj.getAstCategory2Code(), "").equals("")) {
			// eGuard連携小分類かどうか判別
			List<String> param = new ArrayList<String>();
			param.add(null); // value1
			param.add(null); // value2
			param.add(null); // value3
			param.add(null); // value4
			param.add(null); // value5
			param.add(Constants.FLAG_YES); // value6
			CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, obj.getAstCategory2Code(), null, param);

			if(cn != null) ret = new EGuardLdapDAO().createAssetId(obj.getAssetId());
		}

		return ret;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#deleteEGuardAsset(jp.co.ctcg.easset.dto.asset.Asset)
	 */
	public boolean deleteEGuardAsset(Asset obj) {
		boolean ret = false;

		if(obj != null) {
			ret = new EGuardLdapDAO().deleteAssetId(obj.getAssetId());
		}

		return ret;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.AssetSession#syncEGuardAsset(jp.co.ctcg.easset.dto.asset.Asset)
	 */
	public int syncEGuardAsset(Asset obj) {
		int ret = 0;

		if(obj != null) {
			// 抹消されているかどうか判別（抹消日も考慮）
			if(Function.nvl(obj.getDeleteFlag(), "").equals(Constants.FLAG_YES) && (obj.getDeleteDate() == null || new Date().compareTo(obj.getDeleteDate()) >= 0)) {
				if(deleteEGuardAsset(obj)) ret = 2;
			} else {
				// eGuard連携小分類かどうか判別
				List<String> param = new ArrayList<String>();
				param.add(null); // value1
				param.add(null); // value2
				param.add(null); // value3
				param.add(null); // value4
				param.add(null); // value5
				param.add(Constants.FLAG_YES); // value6
				CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, obj.getAstCategory2Code(), null, param);

				if(cn != null) { // 連携対象
					if(createEGuardAsset(obj)) ret = 1;
				} else {
					if(deleteEGuardAsset(obj)) ret = 2;
				}
			}
		}

		return ret;
	}
}