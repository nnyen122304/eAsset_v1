/*===================================================================
 * ファイル名 : ApChangeSessionBean.java
 * 概要説明   : 移動申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.SocketException;
import java.sql.SQLException;
import java.text.DecimalFormat;
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

import jp.co.ctcg.easset.dao.ApChangeDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.LovDataEx;
import jp.co.ctcg.easset.dto.ap_change.ApChange;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineAst;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineContract;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineCostSec;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineFld;
import jp.co.ctcg.easset.dto.ap_change.ApChangeLineLic;
import jp.co.ctcg.easset.dto.ap_change.ApChangeSC;
import jp.co.ctcg.easset.dto.ap_change.ApChangeSR;
import jp.co.ctcg.easset.dto.asset.Asset;
import jp.co.ctcg.easset.dto.asset.AssetSC;
import jp.co.ctcg.easset.dto.asset.AssetSR;
import jp.co.ctcg.easset.dto.fld.PpfsFldApp;
import jp.co.ctcg.easset.dto.fld.PpfsFldSC;
import jp.co.ctcg.easset.dto.fld.PpfsFldSR;
import jp.co.ctcg.easset.dto.fld.PpfsUnUpd;
import jp.co.ctcg.easset.dto.license.License;
import jp.co.ctcg.easset.dto.license.LicenseSC;
import jp.co.ctcg.easset.dto.license.LicenseSR;
import jp.co.ctcg.easset.dto.lld.PpfsLldSC;
import jp.co.ctcg.easset.dto.lld.PpfsLldSR;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;
import jp.co.ctcg.easset.util.PdfExporter;
import jp.co.ctcg.easset.ws.ApChangeService;
import jp.co.ctcg.easset.ws.ApChangeServiceProxy;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;

import org.apache.commons.beanutils.PropertyUtils;

@Stateless
public class ApChangeSessionBean implements ApChangeSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession;

	@EJB
	HistSession histSession;

	@EJB
	AssetSession assetSession;

	@EJB
	LicenseSession licenseSession;

	@EJB
	FldSession fldSession;

	@EJB
	LldSession lldSession;

	private static final String LINE_COST_TYPE_LEASE = "1";
	private static final String LINE_COST_TYPE_RENTAL = "2";
	private static final String LINE_COST_TYPE_TAN = "3";
	private static final String LINE_COST_TYPE_INT = "4";
	private static final String LINE_COST_TYPE_OLD = "A";
	private static final String LINE_COST_TYPE_NEW = "B";
	private static final String ID_PREFIX_AST = "MH";
	private static final String ID_PREFIX_LIC = "MS";

	// 履歴作成用
	private static final String HIST_ENTITY_NAME = "AP_CHANGE";
	private static final String HIST_OPERATION_CREATE = "新規作成";
	private static final String HIST_OPERATION_UPDATE = "更新";
	private static final String HIST_OPERATION_DELETE = "削除";
	private static final String HIST_OPERATION_APPLY = "申請";
	private static final String HIST_OPERATION_APPROVE = "承認";
	private static final String HIST_OPERATION_SEND_BACK = "差戻し";
	private static final String HIST_OPERATION_REJECT = "却下";
	private static final String HIST_OPERATION_CANCEL_APPLY = "引戻し";

	private static final String HIST_OPERATION_UPDATE_TARGET = "移動申請承認";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#searchApChange(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_change.ApChangeSC)
	 */
	public List<ApChangeSR> searchApChange(String loginStaffCode, String accessLevel, ApChangeSC searchParam) {
		try {
			ApChangeDAO apChangeDAO = new ApChangeDAO();

			return apChangeDAO.selectApChangeList(loginStaffCode, accessLevel, searchParam);

		} catch (SQLException e) {
			//	移動申請検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#createApChangeCsv(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_change.ApChangeSC)
	 */
	public String createApChangeCsv(String loginStaffCode, String accessLevel, ApChangeSC searchParam) {
		try {
			ApChangeDAO apChangeDAO = new ApChangeDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = apChangeDAO.createApChangeListCsv(loginStaffCode, accessLevel, searchParam);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_CHANGE_SEARCH, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam));

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#getApGetTan(java.lang.String)
	 */
	public ApChange getApChange(String applicationId) {
		return getApChange(applicationId, false);
	}
	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#getApChange(java.lang.Long)
	 */
	public ApChange getApChange(Long eappId) {
		return getApChange(eappId, false);
	}

	/**
	 * 申請情報取得
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private ApChange getApChange(Long eappId, boolean lockFlag) {
		try {
			ApChangeDAO apChangeDAO = new ApChangeDAO();

			ApChange apChange = apChangeDAO.selectApChange(eappId, lockFlag); // ヘッダの取得

			if(apChange != null) {
				return getApChange(apChange.getApplicationId(), lockFlag);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請取得"), e);
		}
	}

	/**
	 * 申請情報取得
	 * @param applicationId 申請書番号
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private ApChange getApChange(String applicationId, boolean lockFlag ){
		try{

			ApChangeDAO apChangeDAO = new ApChangeDAO();

			ApChange apChange = apChangeDAO.selectApChange(applicationId, lockFlag);
			if(apChange != null) {
				//	有形固定資産明細取得
				List<ApChangeLineFld> apChangeLineFldTanList = apChangeDAO.selectApChangeLineFld(applicationId, LINE_COST_TYPE_TAN);
				//	有形固定資産明細の経費負担部署取得
				for(int i = 0; i < apChangeLineFldTanList.size(); i++){
					ApChangeLineFld fld = apChangeLineFldTanList.get(i);
					if(fld.getAstNum() != null){
						//	経費負担部署取得
						List<ApChangeLineCostSec> fldCostSecList = apChangeDAO.selectApChangeLineCostSec(applicationId, LINE_COST_TYPE_TAN, fld.getAstNum());
						setCostSecDistName(fld, fldCostSecList); // 経費負担配分名称セット
						fld.setApChangeLineCostSecList(fldCostSecList);
					}
				}
				apChange.setApChangeLineFldTan(apChangeLineFldTanList);

				//	無形固定資産明細取得
				List<ApChangeLineFld> apChangeLineFldIntList = apChangeDAO.selectApChangeLineFld(applicationId, LINE_COST_TYPE_INT);
				//	無形固定資産明細の経費負担部署取得
				for(int i = 0; i < apChangeLineFldIntList.size(); i++){
					ApChangeLineFld fld = apChangeLineFldIntList.get(i);
					if(fld.getAstNum() != null){
						//	経費負担部署取得
						List<ApChangeLineCostSec> fldCostSecList = apChangeDAO.selectApChangeLineCostSec(applicationId, LINE_COST_TYPE_INT, fld.getAstNum());
						setCostSecDistName(fld, fldCostSecList); // 経費負担配分名称セット
						fld.setApChangeLineCostSecList(fldCostSecList);
					}
				}
				apChange.setApChangeLineFldInt(apChangeLineFldIntList);

				//	リース契約明細取得
				List<ApChangeLineContract> apChangeLineContractLeaseList = apChangeDAO.selectApChangeLineContract(applicationId, LINE_COST_TYPE_LEASE);
				//	リース契約明細の経費負担部署取得
				for(int i = 0; i < apChangeLineContractLeaseList.size(); i++){
					ApChangeLineContract leaseContract = apChangeLineContractLeaseList.get(i);
					if(leaseContract.getAstNum() != null){
						//	経費負担部署取得
						List<ApChangeLineCostSec> leaseCostSecList = apChangeDAO.selectApChangeLineCostSec(applicationId, LINE_COST_TYPE_LEASE, leaseContract.getAstNum());
						setCostSecDistName(leaseContract, leaseCostSecList); // 経費負担配分名称セット
						leaseContract.setApChangeLineCostSecList(leaseCostSecList);
					}
				}
				apChange.setApChangeLineContractLease(apChangeLineContractLeaseList);

				//	レンタル契約明細取得
				List<ApChangeLineContract> apChangeLineContractRentalList = apChangeDAO.selectApChangeLineContract(applicationId, LINE_COST_TYPE_RENTAL);
				//	レンタル契約明細の経費負担部署取得
				for(int i = 0; i < apChangeLineContractRentalList.size(); i++){
					ApChangeLineContract rentalContract = apChangeLineContractRentalList.get(i);
					if(rentalContract.getAstNum() != null){
						//	経費負担部署取得
						List<ApChangeLineCostSec> rentalCostSecList = apChangeDAO.selectApChangeLineCostSec(applicationId, LINE_COST_TYPE_RENTAL, rentalContract.getAstNum());
						setCostSecDistName(rentalContract, rentalCostSecList); // 経費負担配分名称セット
						rentalContract.setApChangeLineCostSecList(rentalCostSecList);
					}
				}
				apChange.setApChangeLineContractRental(apChangeLineContractRentalList);

				apChange.setApChangeLineAst(apChangeDAO.selectApChangeLineAst(applicationId));
				apChange.setApChangeLineLic(apChangeDAO.selectApChangeLineLic(applicationId));
				apChange.setApChangeLineCostSecOld(apChangeDAO.selectApChangeLineCostSec(applicationId, LINE_COST_TYPE_OLD, null));
				apChange.setApChangeLineCostSecNew(apChangeDAO.selectApChangeLineCostSec(applicationId, LINE_COST_TYPE_NEW, null));
			}

			return apChange;

		} catch (SQLException e) {
			//	移動申請検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請検索"), e);
		}
	}

	/**
	 * 資産情報に経費負担配分名称セット
	 * @param fld 資産
	 * @param costSecList
	 */
	private void setCostSecDistName(ApChangeLineFld fld, List<ApChangeLineCostSec> costSecList) {
		StringBuffer costSectionNames = new StringBuffer();
		StringBuffer costSectionCode = new StringBuffer();

		setCostSecDistNameBody(costSectionNames, costSectionCode,costSecList);

		fld.setCostSectionName(costSectionNames.toString());
		fld.setCostSectionCode(costSectionCode.toString());
	}

	/**
	 * 契約情報に経費負担配分名称セット
	 * @param contract 物件
	 * @param costSecList
	 */
	private void setCostSecDistName(ApChangeLineContract contract, List<ApChangeLineCostSec> costSecList) {
		StringBuffer costSectionNames = new StringBuffer();
		StringBuffer costSectionCode = new StringBuffer();

		setCostSecDistNameBody(costSectionNames, costSectionCode,costSecList);

		contract.setCostSectionName(costSectionNames.toString());
		contract.setCostSectionCode(costSectionCode.toString());
	}

	/*
	 * 経費負担配分名称セット
	 */
	private void setCostSecDistNameBody(StringBuffer costSectionNames, StringBuffer costSectionCode, List<ApChangeLineCostSec> costSecList) {
		Double costDist = 0d;
		DecimalFormat df = new DecimalFormat("##0.0");
		for(int j = 0; j < costSecList.size(); j++){
			ApChangeLineCostSec rentalCostSec = costSecList.get(j);
			if(costSectionNames.length() > 0){
				costSectionNames.append(",");
			}
			//	一番大きい配分の経費負担部課コード取得
			if(rentalCostSec.getCostDist().compareTo(costDist) > 0){
				costDist = rentalCostSec.getCostDist();
				costSectionCode.setLength(0);
				costSectionCode.append(rentalCostSec.getCostSectionCode());
			}
			costSectionNames.append(rentalCostSec.getCostSectionName() + " " + df.format(rentalCostSec.getCostDist()) + "%");
		}
	}

	/**
	 * 不正セット項目値の調整
	 * @param obj 移動申請データ
	 * @throws AppException
	 */
	private void setPropertyAdjust(ApChange obj) throws AppException {
		if(Function.nvl(obj.getApChangeTypeTanFlag(), "").equals("")) obj.setApChangeTypeTanFlag(Constants.FLAG_NO);
		if(Function.nvl(obj.getApChangeTypeInt1Flag(), "").equals("")) obj.setApChangeTypeInt1Flag(Constants.FLAG_NO);
		if(Function.nvl(obj.getApChangeTypeInt2Flag(), "").equals("")) obj.setApChangeTypeInt2Flag(Constants.FLAG_NO);
		if(Function.nvl(obj.getApChangeTypeThinClFlag(), "").equals("")) obj.setApChangeTypeThinClFlag(Constants.FLAG_NO);
		if(Function.nvl(obj.getApChangeTypeTakePcFlag(), "").equals("")) obj.setApChangeTypeTakePcFlag(Constants.FLAG_NO);
		if(Function.nvl(obj.getApChangeTypeLeaseFlag(), "").equals("")) obj.setApChangeTypeLeaseFlag(Constants.FLAG_NO);
		if(Function.nvl(obj.getApChangeTypeRentalFlag(), "").equals("")) obj.setApChangeTypeRentalFlag(Constants.FLAG_NO);
		if(Function.nvl(obj.getApChangeTypeLicenseFlag(), "").equals("")) obj.setApChangeTypeLicenseFlag(Constants.FLAG_NO);
		if(Function.nvl(obj.getApChangeTypeCostTypeFlag(), "").equals("")) obj.setApChangeTypeCostTypeFlag(Constants.FLAG_NO);

		if(obj.getApChangeTypeTanFlag().equals(Constants.FLAG_NO)
			&& obj.getApChangeTypeInt1Flag().equals(Constants.FLAG_NO)
			&& obj.getApChangeTypeInt2Flag().equals(Constants.FLAG_NO)
			&& obj.getApChangeTypeEquipFlag().equals(Constants.FLAG_NO)
			&& obj.getApChangeTypeThinClFlag().equals(Constants.FLAG_NO)
			&& obj.getApChangeTypeTakePcFlag().equals(Constants.FLAG_NO)
			&& obj.getApChangeTypeLeaseFlag().equals(Constants.FLAG_NO)
			&& obj.getApChangeTypeRentalFlag().equals(Constants.FLAG_NO)
			&& obj.getApChangeTypeLicenseFlag().equals(Constants.FLAG_NO)
			&& obj.getApChangeTypeCostTypeFlag().equals(Constants.FLAG_NO)
			) {
			// 申請区分がいずれにも当てはまらない場合、備品に設定
			obj.setApChangeTypeEquipFlag(Constants.FLAG_YES);
		}

		// 原価以外はプロジェクト項目クリア
		if(!Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)) {
			obj.setCostDepPrjCode(null);
			obj.setCostDepPrjName(null);
		}

		// 課長承認不要チェックが付いている場合は、課長値クリア
		if(Function.nvl(obj.getApprCostStaffSkipFlagOld(), "").equals(Constants.FLAG_YES)) {
			obj.setApprCostStaffCodeOld(null);
			obj.setApprCostStaffNameOld(null);
		}
		if(Function.nvl(obj.getApprCostStaffSkipFlagNew(), "").equals(Constants.FLAG_YES)) {
			obj.setApprCostStaffCodeNew(null);
			obj.setApprCostStaffNameNew(null);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#createApChange(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_change.ApChange)
	 */
	public String createApChange(String loginStaffCode, String accessLevel, ApChange obj) throws AppException {
		return createApChange(loginStaffCode, accessLevel, obj, true);
	}

	/**
	 * 移動申請作成本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 移動申請データ
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの移動申請書番号
	 */
	private String createApChange(String loginStaffCode, String accessLevel, ApChange obj, boolean isHistCreate) throws AppException {
		try {
			ApChangeDAO apChangeDAO = new ApChangeDAO();

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

			errMsg.append(validateApChange(loginStaffCode, accessLevel, obj));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// IDの採番
			String idPrefix = ID_PREFIX_AST; // MH～の番号体系

			// ライセンス、無形固定資産は"MS～"の番号体系
			if(Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_LIC)
				|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT_EA)
				|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT)
			) {
				idPrefix = ID_PREFIX_LIC;
			}

			String applicationId = masterSession.nextValId(idPrefix);

			//////////////////////////////////// データ作成
			// バージョン
			obj.setVersion(1);

			obj.setApplicationId(applicationId); // IDセット
			apChangeDAO.insertApChange(obj);	//	ヘッダ作成
			createLine(loginStaffCode, obj, apChangeDAO, applicationId);	//	明細作成

			////////////////////////////////////履歴作成

			//	履歴作成
			if(isHistCreate) {
				histSession.createHistData(HIST_ENTITY_NAME, applicationId, HIST_OPERATION_CREATE, null);
			}

			return obj.getApplicationId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#updateApChange(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_change.ApChange)
	 */
	public void updateApChange(String loginStaffCode, String accessLevel, ApChange obj) throws AppException {
		// 更新コメント
		obj.setUpdateComment(null);

		updateApChange(loginStaffCode, accessLevel, obj, true, true);
	}

	/**
	 * 移動申請更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 移動申請データ
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException
	 */
	private void updateApChange(String loginStaffCode, String accessLevel, ApChange obj, boolean isLineUpdate, boolean isHistCreate) throws AppException {
		updateApChange(loginStaffCode, accessLevel, obj, isLineUpdate, isHistCreate, null);
	}

	/**
	 * 移動申請更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 移動申請データ
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @param operation 履歴作成時のオペレーション
	 * @throws AppException
	 */
	private void updateApChange(String loginStaffCode, String accessLevel, ApChange obj, boolean isLineUpdate, boolean isHistCreate, String operation) throws AppException {
		try {

			ApChangeDAO apChangeDAO = new ApChangeDAO();
			ApChange apChangeOld = getApChange(obj.getApplicationId(), true); // 現データの取得

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
			if(obj.getVersion().intValue() != apChangeOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// バリデーション(登録画面更新の際のみ：連携等による更新時は行わない)
			if(isLineUpdate) errMsg.append(validateApChange(loginStaffCode, accessLevel, obj));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新
			// バージョン
			obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);

			apChangeDAO.updateApChange(obj);

			if(isLineUpdate) {
				// 明細を一度削除
				apChangeDAO.deleteApChangeLineFld(obj.getApplicationId());
				apChangeDAO.deleteApChangeLineContract(obj.getApplicationId());
				apChangeDAO.deleteApChangeLineAst(obj.getApplicationId());
				apChangeDAO.deleteApChangeLineLic(obj.getApplicationId());
				apChangeDAO.deleteApChangeLineCostSec(obj.getApplicationId());

				createLine(loginStaffCode, obj ,apChangeDAO, obj.getApplicationId()); // 明細データ作成
			}

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {

				StringBuffer lineChangeColumnName = new StringBuffer();
				if(isLineUpdate) {
					// 明細変更確認
					if(Function.isListChange(obj.getApChangeLineFldTan(), apChangeOld.getApChangeLineFldTan())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("有形固定明細");
					}
					if(Function.isListChange(obj.getApChangeLineFldInt(), apChangeOld.getApChangeLineFldInt())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("無形固定明細");
					}
					if(Function.isListChange(obj.getApChangeLineContractLease(), apChangeOld.getApChangeLineContractLease())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("リース契約明細");
					}
					if(Function.isListChange(obj.getApChangeLineContractRental(), apChangeOld.getApChangeLineContractRental())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("レンタル契約明細");
					}
					if(Function.isListChange(obj.getApChangeLineAst(), apChangeOld.getApChangeLineAst())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("情報機器等明細");
					}
					if(Function.isListChange(obj.getApChangeLineLic(), apChangeOld.getApChangeLineLic())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("ライセンス明細");
					}
					if(Function.isListChange(obj.getApChangeLineCostSecNew(), apChangeOld.getApChangeLineCostSecNew())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("経費負担部課明細");
					}
				}

				histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), (operation == null ? HIST_OPERATION_UPDATE : operation), lineChangeColumnName.toString());
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#applyApChange(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_change.ApChange)
	 */
	public String applyApChange(String loginStaffCode, String accessLevel, ApChange obj) throws AppException {
		String ret = null;
		boolean isNew = Function.nvl(obj.getApplicationId(), "").equals(""); // 新規の場合true

		////////////////////////////////////新規 or 更新呼び出し
		if(isNew) { // 新規
			ret = createApChange(loginStaffCode, accessLevel, obj, false);
		} else { // 更新
			// 更新コメント
			obj.setUpdateComment(null);

			ret = obj.getApplicationId();

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			// ステータス更新前バリデーション
			String errMsg = validateApChange(loginStaffCode, accessLevel, obj);
			if(errMsg.length() > 0) throw new AppException(errMsg);
		}

		//////////////////////////////////// ステータス更新&ステータス更新後バリデーション
		obj.setApStatus(Constants.AP_STATUS_CHANGE_APPLY);
		String errMsg = validateApChange(loginStaffCode, accessLevel, obj);
		if(errMsg.length() > 0) throw new AppException(errMsg);

		////////////////////////////////////申請
		Long eappId = callEappService(obj); // e申請連携

		boolean isAutoApprove = false;
		if(Function.nvl(obj.getApStatus(), "").equals(Constants.AP_STATUS_CHANGE_APPROVE)) { // 自動承認対象かどうかを判別
			isAutoApprove = true;
			obj.setApStatus(Constants.AP_STATUS_CHANGE_APPLY); // 一度ステータスを申請中に戻す
		}

		// e申請IDを更新
		obj.setEappId(eappId);

		if(isNew) { // 新規
			updateApChange(loginStaffCode, accessLevel, obj, false, false);
			histSession.createHistData(HIST_ENTITY_NAME, ret, HIST_OPERATION_APPLY, null); // 履歴作成
		} else {
			updateApChange(loginStaffCode, accessLevel, obj, true, true, HIST_OPERATION_APPLY);
		}


		//////////////////////////////////// 自動承認
		if(isAutoApprove) {
			approveApChange(Constants.STAFF_CODE_SYSTEM, obj);
		}

		return ret;

	}

	/**
	 * e申請サービス呼び出し
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(ApChange obj) throws AppException{

		// e申請WebServiceエンドポイント取得
		CodeName codeName = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_CHANGE, null, null);
		String eappWsEndpoint = codeName.getValue1(); // e申請WebSerivceエンドポイント
		String eAssetUrl = codeName.getValue2(); // e申請淫乱フレーム画面表示用のeAssetUrl
		String eappStopMessage = codeName.getValue3(); // e申請との連携停止期間中のエラーメッセージ

		List<ApChangeLineAst> astList = obj.getApChangeLineAst();

		Long eappId = null;

		if(!Function.nvl(eappWsEndpoint, "").equals("")) { // e申請WebServiceエンドポイントが空白(PG検証用)の場合は連携無し
			// e申請との連携停止期間中のエラーメッセージ
			if(!eappWsEndpoint.startsWith("http")){
				throw new AppException(eappStopMessage);
			}

			try {
				eAssetUrl += "&amp;companyCode=" + obj.getApCompanyCode();
				eAssetUrl += "&amp;param2="; // e申請から書類IDが指定される

				//////////////////////////////////// 変更項目の取得
				List<String> changeItemList = new ArrayList<String>();

				if(!Function.nvl(obj.getHolCompanyCode(), "").equals("")) changeItemList.add("01"); // 保有部署
				if(!Function.nvl(obj.getHolOfficeCode(), "").equals("")) changeItemList.add("02"); // 個別設置場所
				if(!Function.nvl(obj.getHolStaffCode(), "").equals("")) changeItemList.add("03"); // 資産管理担当者
				if(!Function.nvl(obj.getUseStaffCode(), "").equals("")) changeItemList.add("04"); // 使用者
				if(!Function.nvl(obj.getIntHolStaffCode(), "").equals("")) changeItemList.add("05"); // 無形管理担当者

				if(!Function.nvl(obj.getCostType(), "").equals("")) changeItemList.add("06"); // 販管/原価区分(プロジェクトコード)
				if(!Function.nvl(obj.getCostCompanyCode(), "").equals("")) changeItemList.add("07"); // 経費負担部署
				if(!Function.nvl(obj.getHolRepOfficeCode(), "").equals("")) changeItemList.add("08"); // 代表設置場所

				//////////////////////////////////// 変更対象種別の取得
				Set<String> changeObjectTypeList = new HashSet<String>();

				// 備品
				if(Function.nvl(obj.getApChangeTypeEquipFlag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("01");

				// SunRay・新シンクライアント/持ち出しPC（情シス配備）
				if(Function.nvl(obj.getApChangeTypeThinClFlag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("02");
				if(Function.nvl(obj.getApChangeTypeTakePcFlag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("03");

				// ライセンス
				if(Function.nvl(obj.getApChangeTypeLicenseFlag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("04");
				// 有形固定資産
				if(Function.nvl(obj.getApChangeTypeTanFlag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("05");

				// 1.5次フェーズにて以下SunRay・シンクラでのリース・レンタル経路スキップ廃止
				// リース
				if(Function.nvl(obj.getApChangeTypeLeaseFlag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("06");
				// レンタル
				if(Function.nvl(obj.getApChangeTypeRentalFlag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("07");
/*
				// SunRay・新シンクライアント/持ち出しPC（情シス配備）で
				// 機器明細が全てSunRay・新シンクライアント/持ち出しPC（情シス配備）の場合
				// リース・レンタルの承認経路は無し。
				boolean isLeReRouteSkip = false; // リース・レンタル経路スキップフラグ

				if(Function.nvl(obj.getApChangeTypeThinClFlag(), "").equals(Constants.FLAG_YES)
					|| Function.nvl(obj.getApChangeTypeTakePcFlag(), "").equals(Constants.FLAG_YES)) {

					if(astList != null && astList.size() > 0) {
						for(int i = 0; i < astList.size(); i++) {
							ApChangeLineAst astObj = astList.get(i);
							if(Function.nvl(astObj.getAssetId(), "").equals("")) continue; // レンタルの情報機器と紐付け無い明細は判別除外

							isLeReRouteSkip = false;
							// 情シス配備の場合
							if(Function.nvl(astObj.getAstSystemSectionDeployFlag(), "").equals(Constants.FLAG_YES) && !Function.nvl(astObj.getAstManageType(), "").equals("")) {
								CodeName astManageType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, astObj.getAstManageType(), null, null);
								if(astManageType != null) {
									String manageCode = Function.nvl(astManageType.getValue2(), "");

									if(manageCode.equals(Constants.ASSET_MANAGE_TYPE_VALUE2_THINCLIENT)) { // シンクライアント
										isLeReRouteSkip = true;
									} else if(manageCode.equals(Constants.ASSET_MANAGE_TYPE_VALUE2_TAKEN_PC)) { // 持ち出しPC
										isLeReRouteSkip = true;
									}
								}
							}

							if(!isLeReRouteSkip) {
								break; // 一件でも情シス配備以外があればリース・レンタル経路有り
							}
						}
					}
				}

				if(!isLeReRouteSkip) {
					// リース
					if(Function.nvl(obj.getApChangeTypeLeaseFlag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("06");
					// レンタル
					if(Function.nvl(obj.getApChangeTypeRentalFlag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("07");
				}
*/

				// 無形固定資産(社内使用SW/長前/その他無形)
				if(Function.nvl(obj.getApChangeTypeInt1Flag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("08");
				// 無形固定資産(市販目的SW)
				if(Function.nvl(obj.getApChangeTypeInt2Flag(), "").equals(Constants.FLAG_YES)) changeObjectTypeList.add("09");

				//////////////////////////////////// タイトルの付加情報セット
				String dffTitle = "";
				//	メーカー
				String maker = "";
				Integer quantity = 0;
				boolean isCpuFlag = false;	//	全体の明細がCPUか判定フラグ
				//	情シス配備判別
				if(astList != null && astList.size() > 0){
					for(int i = 0; i < astList.size(); i++) {
						ApChangeLineAst row = astList.get(i);
						if(Function.nvl(row.getAssetId(), "").equals("")) continue; // レンタルの情報機器と紐付け無い明細は判別除外

						//	資産機器分類:大分類 CPU本体明細？
						boolean isCpuLineFlag = false;	//	1つの明細がCPUか判定フラグ
						//	資産機器分類:大分類 CPU本体明細？
						CodeName codeNameAssetCategory1 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY1, row.getAstCategory1Code(), null, null);
						if(codeNameAssetCategory1.getValue2().equals(Constants.FLAG_YES)){
							//	付加情報リセット
							if( !isCpuFlag ){
								maker = "";
								quantity = 0;
							}
							isCpuLineFlag = true;
							isCpuFlag = true;
						}

						if( isCpuLineFlag	//	CPU明細？
						|| (!isCpuFlag && !isCpuLineFlag)	//	CPU明細が存在しない？
						){
							//	情報機器等情報取得
							Asset assetInfo = assetSession.getAsset(row.getAssetId(), false);
							//	メーカー
							if(!Function.nvl(assetInfo.getAstMakerName(), "").equals("")){
								if( !maker.equals("") && !maker.equals(assetInfo.getAstMakerName()) ){
									maker = "混在";
								}
								else{
									maker = assetInfo.getAstMakerName();
								}
							}
							//	数量
							if(!Function.nvl(assetInfo.getHolQuantity(), "").equals("")){
								quantity = assetInfo.getHolQuantity() + quantity;
							}
						}
					}

					//	変更項目チェック
					String changeItem = "";
					 // 保有部署
					if(!Function.nvl(obj.getHolCompanyCode(), "").equals("")){
						if(changeItem.length() > 0){
							changeItem = changeItem + "・";
						}
						changeItem = changeItem + "保部";
					}
					// 資産管理担当者
					if(!Function.nvl(obj.getHolStaffCode(), "").equals("")){
						if(changeItem.length() > 0){
							changeItem = changeItem + "・";
						}
						changeItem = changeItem + "資担";
					}
					 // 個別設置場所
					if(!Function.nvl(obj.getHolOfficeCode(), "").equals("")){
						if(changeItem.length() > 0){
							changeItem = changeItem + "・";
						}
						changeItem = changeItem + "個設";
					}
					 // 使用者
					if(!Function.nvl(obj.getUseStaffCode(), "").equals("")){
						if(changeItem.length() > 0){
							changeItem = changeItem + "・";
						}
						changeItem = changeItem + "使用";
					}
					 // 無形管理担当者
					if(!Function.nvl(obj.getIntHolStaffCode(), "").equals("")){
						if(changeItem.length() > 0){
							changeItem = changeItem + "・";
						}
						changeItem = changeItem + "無担";
					}
					 // 販管/原価区分(プロジェクトコード)
					if(!Function.nvl(obj.getCostType(), "").equals("")){
						if(changeItem.length() > 0){
							changeItem = changeItem + "・";
						}
						changeItem = changeItem + "販原";
					}
					 // 経費負担部署
					if(!Function.nvl(obj.getCostCompanyCode(), "").equals("")){
						if(changeItem.length() > 0){
							changeItem = changeItem + "・";
						}
						changeItem = changeItem + "経部";
					}
					 // 代表設置場所
					if(!Function.nvl(obj.getHolRepOfficeCode(), "").equals("")){
						if(changeItem.length() > 0){
							changeItem = changeItem + "・";
						}
						changeItem = changeItem + "代設";
					}

					//	[メーカー]／[台数]／[移動予定日]／ [変更項目]
					String dateStr;
					if(obj.getChgScheduleDate() != null) {
						SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
						dateStr = dateFormat.format(obj.getChgScheduleDate());
					} else {
						dateStr = obj.getChgSchedulePeriod();
						if(dateStr != null) dateStr = dateStr.substring(0, 3) + "/" + dateStr.substring(4, 5);
					}
					dffTitle = Function.nvl(maker, "") + "／" + quantity + "／" + Function.nvl(dateStr, "") + "／" +  Function.nvl(changeItem, "");
				}

				//////////////////////////////////// 経路の取得
				if(changeItemList.size() == 0 || changeObjectTypeList.size() == 0) {
					throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請変更項目・対象種別の取得"));
				}

				boolean routeExists = false; // 申請経路有無判別
				boolean confirmOnly = true; // 確認経路のみ（自動承認対象）

				// e申請経路担当情報取得
				CodeName codeNameEappCharge = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_CHANGE, null, obj.getApCompanyCode(), null);

				// e申請経路権限情報取得
				ApChangeDAO dao = new ApChangeDAO();
				CodeName codeNameEappRoute = dao.selectApChangeEappRoute(obj.getApCompanyCode(), (String[]) changeItemList.toArray(new String[0]), (String[]) changeObjectTypeList.toArray(new String[0]));

				// 経路パラメータセット
				List<String> oldRouteAuthDispList = new ArrayList<String>();
				List<String> oldRouteChargeDispList = new ArrayList<String>();
				List<String> oldRouteDispTypeList = new ArrayList<String>();
				List<String> newRouteAuthDispList = new ArrayList<String>();
				List<String> newRouteChargeDispList = new ArrayList<String>();
				List<String> newRouteDispTypeList = new ArrayList<String>();
				List<String> approveRouteAuthDispList = new ArrayList<String>();
				List<String> approveRouteChargeDispList = new ArrayList<String>();
				List<String> approveRouteDispTypeList = new ArrayList<String>();
				List<String> acceptRouteAuthDispList = new ArrayList<String>();
				List<String> acceptRouteChargeDispList = new ArrayList<String>();
				List<String> acceptRouteDispTypeList = new ArrayList<String>();

				for(int i = 0; i < Constants.MAX_EAPP_ROUTE_COUNT_AP_CHANGE; i++) {
					List<String> authDispList;
					List<String> chargeDispList;
					List<String> dispTypeList;

					// 対象経路判別
					if(i <= 2) { // 移動元経路
						authDispList = oldRouteAuthDispList;
						chargeDispList = oldRouteChargeDispList;
						dispTypeList = oldRouteDispTypeList;
					} else if(3 <= i && i <= 6) { // 移動先経路
						authDispList = newRouteAuthDispList;
						chargeDispList = newRouteChargeDispList;
						dispTypeList = newRouteDispTypeList;
					} else if((7 <= i && i <= 12) || (14 <= i && i <= 16) || (18 <= i && i <= 21)) {// 受付経路
						authDispList = acceptRouteAuthDispList;
						chargeDispList = acceptRouteChargeDispList;
						dispTypeList = acceptRouteDispTypeList;
					} else { // 承認経路
						authDispList = approveRouteAuthDispList;
						chargeDispList = approveRouteChargeDispList;
						dispTypeList = approveRouteDispTypeList;
					}

					Object authOrg = PropertyUtils.getProperty(codeNameEappRoute, "value" + (i + 3)); // 申請経路はvalue3～
					Object auth = PropertyUtils.getProperty(codeNameEappRoute, "value" + (i + 3)); // 申請経路はvalue3～

					// 移動先/移動元は権限に社員番号をセット
					if(!Function.nvl(auth,"").equals("")) {
						if(i == 0) {
							auth = Function.nvl(obj.getApprHolStaffCodeOld(), ""); // 移動元：資産管理担当者
						}
						if(i == 1) {
							auth = Function.nvl(obj.getApprCostStaffCodeOld(), ""); // 移動元：経費負担部課長
						}
						if(i == 2) {
							auth = Function.nvl(obj.getApprSuperiorStaffCodeOld(), ""); // 移動元：部長
						}
						if(i == 3) {
							if(Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT_EA)) { // 無形固定資産(現物)
								auth = Function.nvl(obj.getIntHolStaffCode(), ""); // 移動先：無形管理担当者
							} else {
								auth = Function.nvl(obj.getUseStaffCode(), ""); // 移動先：使用者
							}
						}
						if(i == 4) {
							auth = Function.nvl(obj.getApprHolStaffCodeNew(), ""); // 移動先：資産管理担当者
						}
						if(i == 5) {
							auth = Function.nvl(obj.getApprCostStaffCodeNew(), ""); // 移動先：経費負担部課長
						}
						if(i == 6) {
							auth = Function.nvl(obj.getApprSuperiorStaffCodeNew(), ""); // 移動先：部長
						}

						// 資産管理担当者 移動元 = 移動先
						if(i == 0 && Function.nvl(obj.getApprHolStaffCodeNew(), "").equals(Function.nvl(obj.getApprHolStaffCodeOld(), ""))) {
							auth = null;
						}
						// 経費負担部課長 移動元 = 移動先
						if(i == 1 && Function.nvl(obj.getApprCostStaffCodeNew(), "").equals(Function.nvl(obj.getApprCostStaffCodeOld(), ""))) {
							auth = null;
						}
						// 経費負担部課長 移動元 課長 = 部長
						if(i == 1 && Function.nvl(obj.getApprSuperiorStaffCodeOld(), "").equals(Function.nvl(obj.getApprCostStaffCodeOld(), ""))) {
							auth = null;
						}
						// 部長 移動元 = 移動先
						if(i == 2 && Function.nvl(obj.getApprSuperiorStaffCodeNew(), "").equals(Function.nvl(obj.getApprSuperiorStaffCodeOld(), ""))) {
							auth = null;
						}

						// 経費負担部課長 移動先 課長 = 部長
						if(i == 5 && Function.nvl(obj.getApprSuperiorStaffCodeNew(), "").equals(Function.nvl(obj.getApprCostStaffCodeNew(), ""))) {
							auth = null;
						}
					}

					if(!Function.nvl(auth,"").equals("")) {
						String charge = PropertyUtils.getProperty(codeNameEappCharge, "value" + (i + 3)).toString(); // 申請経路はvalue3～

						// 無形管理担当者/使用者のラベルの切替
						if(Constants.EAPP_ROUTE_CHARGE_AP_CHANGE_USE_IHOL_STAFF.equals(charge)) {
							if(Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT_EA)) { // 無形固定資産(現物)
								charge = Constants.EAPP_ROUTE_CHARGE_AP_CHANGE_IHOL_STAFF;
							} else {
								charge = Constants.EAPP_ROUTE_CHARGE_AP_CHANGE_USE_STAFF;
							}
						}

						authDispList.add(auth.toString());
						chargeDispList.add(charge);
						if(authOrg.toString().equals(Constants.EAPP_ROUTE_DISP_TYPE_CONFIRM_NAME)) { // 確認経路
							dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_CONFIRM);
						} else { // 承認・受付経路
							dispTypeList.add(Constants.EAPP_ROUTE_DISP_TYPE_APPROVE);
							confirmOnly = false;
						}

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
						ApChangeService service = new ApChangeServiceProxy(eappWsEndpoint);
						eappIdStr = service.apply(
								obj.getApplicationId() // applicationId
								, Constants.AP_TYPE_CHANGE // applicationType
								, obj.getApCompanyCode() // companyCode
								, obj.getApSectionCode() // apSectionCode
								, obj.getApCreateStaffCode() // apCreateStaffCode
								, obj.getApStaffCode() // apStaffCode
								, obj.getApTel() // apTel
								, "\\n" + Constants.AP_TITLE_CHANGE // apTitle
								, "(" + obj.getApChangeTypeName() + ")" // apSubTitle
								, obj.getApCompanyName() + Constants.AP_TITLE_CHANGE + "(" + obj.getApChangeTypeName() + ")" + " " + dffTitle // apListTitle
								, eAssetUrl // eAssetUrl
								, oldRouteAuthDispList.toArray(new String[0]) // oldRouteAuthDispArray
								, oldRouteChargeDispList.toArray(new String[0]) // oldRouteChargeDispArray
								, oldRouteDispTypeList.toArray(new String[0]) // oldRouteDispTypeArray
								, newRouteAuthDispList.toArray(new String[0]) // newRouteAuthDispArray
								, newRouteChargeDispList.toArray(new String[0]) // newRouteChargeDispArray
								, newRouteDispTypeList.toArray(new String[0]) // newRouteDispTypeArray
								, approveRouteAuthDispList.toArray(new String[0]) // approveRouteAuthDispArray
								, approveRouteChargeDispList.toArray(new String[0]) // approveRouteChargeDispArray
								, approveRouteDispTypeList.toArray(new String[0]) // approveRouteDispTypeArray
								, acceptRouteAuthDispList.toArray(new String[0]) // acceptRouteAuthDispArray
								, acceptRouteChargeDispList.toArray(new String[0]) // acceptRouteChargeDispArray
								, acceptRouteDispTypeList.toArray(new String[0]) // acceptRouteDispTypeArray
						);
					} catch(SocketException e){
						try{
							//	ログ出力
							Logging.warning(e.getMessage(), e);
							//	WLSのURLを取得する。
							codeName = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_EAPP_ID_RESTORE, null, null);
							eappWsEndpoint = codeName.getValue1(); // e申請WebSerivceエンドポイント
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
						throw new AppException("e申請との連携処理中に予期せぬエラーが発生しました。\n画面下の「保存」ボタンを押してデータ保存した後\ne申請を開いて申請情報が作成されているか、ご確認下さい。\n\ne申請に申請情報が作成されていた場合、\nその申請の「書類ID」と「書類管理番号」をeAsset管理者宛へご連絡下さい。\n", e);
					}

					if(Function.nvl(eappIdStr, "").equals("")) { // レスポンス無しエラー
						throw new AppException("e申請連携処理中に予期せぬエラーが発生しました。\n作業状態を保存するには「保存」ボタンをクリックしてください。\n\n(e申請書類IDが空白です)。");
					} else if(eappIdStr.startsWith("e")) { // e申請側アプリケーションエラー
						throw new AppException("e申請連携処理中に以下のエラーが発生しました。\n作業状態を保存するには「保存」ボタンをクリックしてください。\n" + eappIdStr.substring(1));
					} else {
						eappId = Long.valueOf(eappIdStr);
					}
				}

				if(!routeExists || confirmOnly) { // 経路無し or 確認経路のみ
					obj.setApStatus(Constants.AP_STATUS_CHANGE_APPROVE); // 自動承認対象のためステータスを承認済みにセット
				}

			} catch (IllegalAccessException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請連携パラメータ作成"), e);
			} catch (InvocationTargetException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請連携パラメータ作成"), e);
			} catch (NoSuchMethodException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請連携パラメータ作成"), e);
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "e申請承認経路取得"), e);
			}
		}

		return eappId;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#deleteApChange(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_change.ApChange)
	 */
	public void deleteApChange(String loginStaffCode, String accessLevel, ApChange obj) throws AppException {
		try {
			ApChangeDAO apChangeDAO = new ApChangeDAO();

			ApChange apChangeOld = getApChange(obj.getApplicationId(), true); // 現データの取得

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != apChangeOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新(履歴作成用にバージョンアップ)
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			apChangeOld.setUpdateDate(sysdate);
			apChangeOld.setUpdateStaffCode(loginStaffCode);

			// バージョン
			apChangeOld.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);

			// 更新コメント
			apChangeOld.setUpdateComment(null);

			apChangeDAO.updateApChange(apChangeOld);

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), HIST_OPERATION_DELETE, null);


			//////////////////////////////////// データ削除
			apChangeDAO.deleteApChange(obj.getApplicationId());
			apChangeDAO.deleteApChangeLineFld(obj.getApplicationId());
			apChangeDAO.deleteApChangeLineContract(obj.getApplicationId());
			apChangeDAO.deleteApChangeLineAst(obj.getApplicationId());
			apChangeDAO.deleteApChangeLineLic(obj.getApplicationId());
			apChangeDAO.deleteApChangeLineCostSec(obj.getApplicationId());

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "移動申請削除"), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#approveApChange(java.lang.Long, java.lang.String)
	 */
	public void approveApChange(Long eappId, String execStaffCode) throws AppException {
		ApChange obj = getApChange(eappId, true);

		// e申請書類IDで申請が取得できれば承認処理を実行
		// ※　承認経路が確認のみ場合、書類IDがeAssetに連携される前に
		// 　　この承認処理が呼び出されてしまうためスルーする
		if(obj != null) approveApChange(execStaffCode, obj, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#approveApChange(java.lang.String, jp.co.ctcg.easset.dto.ap_change.ApChange)
	 */
	public void approveApChange(String loginStaffCode, ApChange obj) throws AppException {
		approveApChange(loginStaffCode, obj, true);
	}

	/**
	 *
	 * @param loginStaffCode ログイン者社員番号
	 * @param obj データオブジェクト
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @throws AppException
	 */
	private void approveApChange(String loginStaffCode, ApChange obj, boolean isLineUpdate) throws AppException {
		// ステータス
		obj.setApStatus(Constants.AP_STATUS_CHANGE_APPROVE);
		// 更新コメント
		obj.setUpdateComment(null);
		//	承認日セット
		Date sysdate = new Date();
		obj.setApproveDate(sysdate);

		updateApChange(loginStaffCode, null, obj, isLineUpdate, false);

		// 現物申請
		if(Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_AST)
				|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_LIC)
				|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT_EA)) {

			//////////////////////////////////// 情報機器等の更新
			for(int i = 0; i < obj.getApChangeLineAst().size(); i++){
				ApChangeLineAst item = obj.getApChangeLineAst().get(i);
				//	機器管理番号が空白はスキップ
				if(!Function.nvl(item.getAssetId(), "").equals("")){
					// 情報機器取得
					Asset asset = assetSession.getAsset(item.getAssetId(), false);
					if(asset != null){
						//	データの反映
						//	保有部署
						if(obj.getHolSectionCode() != null && !obj.getHolSectionCode().equals("")){
							asset.setHolCompanyCode(obj.getHolCompanyCode());
							asset.setHolSectionCode(obj.getHolSectionCode());
							asset.setHolSectionYear(obj.getHolSectionYear());
							//	使用部署も更新?
							if(obj.getHolSectionWithFlag().equals(Constants.FLAG_YES)){
								asset.setUseCompanyCode(obj.getHolCompanyCode());
								asset.setUseSectionCode(obj.getHolSectionCode());
								asset.setUseSectionYear(obj.getHolSectionYear());
							}
						}
						//	資産管理担当者
						if(obj.getHolStaffCode() != null && !obj.getHolStaffCode().equals("")){
							asset.setHolStaffCode(obj.getHolStaffCode());
						}
						//	設置(使用)場所
						if(obj.getHolOfficeCode() != null && !obj.getHolOfficeCode().equals("")){
							asset.setHolOfficeCode(obj.getHolOfficeCode());
							asset.setHolOfficeFloor(obj.getHolOfficeFloor());
							asset.setHolOfficeRoomNum(obj.getHolOfficeRoomNum());
							asset.setHolOfficeRackNum(obj.getHolOfficeRackNum());
						}
						//	使用者
						if(obj.getUseStaffCode() != null && !obj.getUseStaffCode().equals("")){
							asset.setUseStaffCompanyCode(obj.getUseStaffCompanyCode());
							asset.setUseStaffSectionCode(obj.getUseStaffSectionCode());
							asset.setUseStaffSectionYear(obj.getUseStaffSectionYear());
							asset.setUseStaffCode(obj.getUseStaffCode());
						}
						//	情報機器更新
						assetSession.updateAsset(loginStaffCode, null, asset, false, HIST_OPERATION_UPDATE_TARGET);
					}
				}
			}

			//////////////////////////////////// ライセンスの更新
			for(int i = 0; i < obj.getApChangeLineLic().size(); i++){
				ApChangeLineLic item = obj.getApChangeLineLic().get(i);
				//	ライセンス番号が空白はスキップ
				if(item.getLicenseId() != null){
					//	ライセンスの取得
					License license = licenseSession.getLicense(item.getLicenseId(), false);
					//	データの反映
					//	保有部署
					if(obj.getHolSectionCode() != null && !obj.getHolSectionCode().equals("")){
						license.setHolCompanyCode(obj.getHolCompanyCode());
						license.setHolSectionCode(obj.getHolSectionCode());
						license.setHolSectionYear(obj.getHolSectionYear());
						//	使用部署更新指定があり、ライセンスの使用許諾区分が”部署限定”の場合
						if(obj.getHolSectionWithFlag().equals(Constants.FLAG_YES) && Function.nvl(license.getUseType(), "").equals(Constants.LICENSE_USE_TYPE_SECTION)){
							license.setUseCompanyCode(obj.getHolCompanyCode());
							license.setUseSectionCode(obj.getHolSectionCode());
							license.setUseSectionYear(obj.getHolSectionYear());
						}
					}
					//	資産管理担当者
					if(obj.getHolStaffCode() != null && !obj.getHolStaffCode().equals("")){
						license.setHolStaffCode(obj.getHolStaffCode());
					}
					//	ライセンスの更新
					licenseSession.updateLicense(loginStaffCode, null, license, false, HIST_OPERATION_UPDATE_TARGET);
				}
			}

			//////////////////////////////////// 無形固定資産付加情報の更新
			HashSet<String> execIdSet = new HashSet<String>();
			for(int i = 0; i < obj.getApChangeLineFldInt().size(); i++){
				ApChangeLineFld item = obj.getApChangeLineFldInt().get(i);
				//	申請書番号が空白はスキップ
				if(item.getGetApplicationId() != null && !execIdSet.contains(item.getGetApplicationId())){
					//	付加情報の取得
					PpfsFldApp fldApp = fldSession.getFldApp(null, null, obj.getApCompanyCode(), item.getGetApplicationId());

					if(fldApp == null) { // 既存データ無の場合は固有番号で取得
						fldApp = fldSession.getFldAppKoyu(null, null, obj.getApCompanyCode(), item.getPpId());
					}

					//	データの反映
					//	保有部署
					if(obj.getHolSectionCode() != null && !obj.getHolSectionCode().equals("")){
						fldApp.setHolCompanyCode(obj.getHolCompanyCode());
						fldApp.setHolSectionCode(obj.getHolSectionCode());
						fldApp.setHolSectionYear(obj.getHolSectionYear());
					}
					//	無形管理担当者
					if(obj.getIntHolStaffCode() != null && !obj.getIntHolStaffCode().equals("")){
						fldApp.setHolStaffCompanyCode(obj.getIntHolStaffCompanyCode());
						fldApp.setHolStaffSectionCode(obj.getIntHolStaffSectionCode());
						fldApp.setHolStaffSectionYear(obj.getIntHolStaffSectionYear());
						fldApp.setHolStaffCode(obj.getIntHolStaffCode());
					}
					//	無形固定資産付加情報の更新
					fldSession.updateFldApp(loginStaffCode, null, fldApp, HIST_OPERATION_UPDATE_TARGET);

					execIdSet.add(item.getApplicationId());
				}
			}
		}

		//////////////////////////////////// 履歴作成
		histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), HIST_OPERATION_APPROVE, null);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#cancelApplyApChange(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyApChange(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApChange apChange = getApChange(eappId, true);

		if(apChange != null) {
			// ステータス
			apChange.setApStatus(Constants.AP_STATUS_GET_TAN_NOAPPLY);
			// 更新コメント
			apChange.setUpdateComment(null);
			apChange.setEappId(null); // 書類IDクリア

			updateApChange(execStaffCode, null, apChange, false, false);

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME, apChange.getApplicationId(), HIST_OPERATION_CANCEL_APPLY, null);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#rejectApChange(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectApChange(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		ApChange apChange = getApChange(eappId, true);

		if(apChange != null) {
			rejectApChange(execStaffCode, apChange, rejectType, rejectReason, false);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#rejectApChange(java.lang.String, jp.co.ctcg.easset.dto.ap_change.ApChange, java.lang.String, java.lang.String)
	 */
	public void rejectApChange(String loginStaffCode, ApChange obj, String rejectType, String rejectReason) throws AppException {
		rejectApChange(loginStaffCode, obj, rejectType, rejectReason, true);
	}

	/**
	 *
	 * @param loginStaffCode ログイン者社員番号
	 * @param obj データオブジェクト
	 * @param rejectType 却下区分 1:差戻し、2:却下
	 * @param rejectReason 却下事由
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @throws AppException
	 */
	private void rejectApChange(String loginStaffCode, ApChange obj, String rejectType, String rejectReason, boolean isLineUpdate) throws AppException {

		String histType = "";
		if(Function.nvl(rejectType, "").equals(Constants.AP_REJECT_TYPE_REJECT)) { // 却下
			//	却下
			obj.setApStatus(Constants.AP_STATUS_CHANGE_REJECT);
			histType = HIST_OPERATION_REJECT;
		} else {
			//	差戻し
			obj.setApStatus(Constants.AP_STATUS_CHANGE_SENDBACK);
			obj.setEappId(null); // 書類IDクリア
			histType = HIST_OPERATION_SEND_BACK;
		}

		// 更新コメント
		obj.setUpdateComment(rejectReason);

		// 更新
		updateApChange(loginStaffCode, null, obj, isLineUpdate, false);

		//////////////////////////////////// 履歴作成
		histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), histType, null);

	}

	/**
	 * 印刷用PDF生成
	 * @param applicationIdList 印刷対象申請書番号一覧
	 * @return PDFファイルID(一時領域に作成される)
	 */
	public String createApChangePdf(List<String> applicationIdList){
		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();

		reportParam.put("applicationIdWhere", Function.getInCondition("APPLICATION_ID", applicationIdList, true));
		reportParam.put("nacApplicationIdWhere", Function.getInCondition("nac.APPLICATION_ID", applicationIdList, true));

		// PDF生成
		PdfExporter exporter = new PdfExporter();

		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApChange.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}
	}


	/*
	 * 明細データの作成
	 */
	private void createLine(String loginStaffCode, ApChange obj, ApChangeDAO apChangeDAO, String applicationId) throws SQLException {
		Date sysdate = new Date(); // システム日付取得

		// カレント年度取得
		CodeName currentYearCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
		Integer currentYear = Integer.valueOf(currentYearCodeName.getValue1());

		List<ApChangeLineCostSec> firstCostSecList = null; // 固定資産 or リース or レンタル1件目の経費負担設定
		boolean setFirstCostSecList = false;

		//	資産明細(有形固定資産)
		List<ApChangeLineFld> lineFldTanList = obj.getApChangeLineFldTan();
		if(lineFldTanList != null){
			for(int i = 0; i<lineFldTanList.size(); i++){
				ApChangeLineFld lineFldTan = lineFldTanList.get(i);

				lineFldTan.setApplicationId(obj.getApplicationId()); // IDのセット
				lineFldTan.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				lineFldTan.setCreateDate(Function.nvl(lineFldTan.getCreateDate(), sysdate));
				lineFldTan.setCreateStaffCode(Function.nvl(lineFldTan.getCreateStaffCode(), loginStaffCode));
				lineFldTan.setUpdateDate(sysdate);
				lineFldTan.setUpdateStaffCode(loginStaffCode);
				//	資産明細の作成
				apChangeDAO.insertApChangeLineFld(lineFldTan);

				//	資産明細(有形固定資産)の経費負担部課作成
				if(lineFldTan.getApChangeLineCostSecList() != null){
					List<ApChangeLineCostSec> apChangeLineCostSecList = lineFldTan.getApChangeLineCostSecList();
					if(apChangeLineCostSecList.size() > 0 && !setFirstCostSecList) {
						firstCostSecList = new ArrayList<ApChangeLineCostSec>();
					}
					for(int j = 0; j < apChangeLineCostSecList.size(); j++){
						ApChangeLineCostSec lineCostSec = apChangeLineCostSecList.get(j);
						lineCostSec.setApplicationId(lineFldTan.getApplicationId());
						lineCostSec.setApChangeLineCostType(LINE_COST_TYPE_TAN);
						lineCostSec.setAstNum(lineFldTan.getAstNum());
						lineCostSec.setLineSeq(j + 1);
						lineCostSec.setCreateDate(lineFldTan.getCreateDate());
						lineCostSec.setCreateStaffCode(lineFldTan.getCreateStaffCode());
						lineCostSec.setUpdateDate(sysdate);
						lineCostSec.setUpdateStaffCode(loginStaffCode);
						lineCostSec.setCostCompanyCode(obj.getApCompanyCode());

						//	経費負担部課年度設定
						if(masterSession.isMICostSection(lineCostSec.getCostCompanyCode(), lineCostSec.getCostSectionCode())) {
							lineCostSec.setCostSectionYear(null);
						}
						else if(lineCostSec.getCostSectionYear() == null) {
							lineCostSec.setCostSectionYear(currentYear);
						}

						apChangeDAO.insertApChangeLineCostSec(lineCostSec);

						if(!setFirstCostSecList) firstCostSecList.add(lineCostSec); // 変更前用に保存
					}
					if(apChangeLineCostSecList.size() > 0) {
						setFirstCostSecList = true;
					}
				}
			}
		}

		//	資産明細(無形固定資産)
		List<ApChangeLineFld> lineFldIntList = obj.getApChangeLineFldInt();
		if(lineFldIntList != null){
			for(int i = 0; i<lineFldIntList.size(); i++){
				ApChangeLineFld lineFldInt = lineFldIntList.get(i);

				lineFldInt.setApplicationId(obj.getApplicationId()); // IDのセット
				lineFldInt.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				lineFldInt.setCreateDate(Function.nvl(lineFldInt.getCreateDate(), sysdate));
				lineFldInt.setCreateStaffCode(Function.nvl(lineFldInt.getCreateStaffCode(), loginStaffCode));
				lineFldInt.setUpdateDate(sysdate);
				lineFldInt.setUpdateStaffCode(loginStaffCode);
				//	資産明細の作成
				apChangeDAO.insertApChangeLineFld(lineFldInt);

				//	資産明細(有形固定資産)の経費負担部課作成
				if(lineFldInt.getApChangeLineCostSecList() != null){
					List<ApChangeLineCostSec> apChangeLineCostSecList = lineFldInt.getApChangeLineCostSecList();
					if(apChangeLineCostSecList.size() > 0 && !setFirstCostSecList) {
						firstCostSecList = new ArrayList<ApChangeLineCostSec>();
					}
					for(int j = 0; j < apChangeLineCostSecList.size(); j++){
						ApChangeLineCostSec lineCostSec = apChangeLineCostSecList.get(j);
						lineCostSec.setApplicationId(lineFldInt.getApplicationId());
						lineCostSec.setApChangeLineCostType(LINE_COST_TYPE_INT);
						lineCostSec.setAstNum(lineFldInt.getAstNum());
						lineCostSec.setLineSeq(j + 1);
						lineCostSec.setCreateDate(lineFldInt.getCreateDate());
						lineCostSec.setCreateStaffCode(lineFldInt.getCreateStaffCode());
						lineCostSec.setUpdateDate(sysdate);
						lineCostSec.setUpdateStaffCode(loginStaffCode);
						lineCostSec.setCostCompanyCode(obj.getApCompanyCode());

						//	経費負担部課年度設定
						if(masterSession.isMICostSection(lineCostSec.getCostCompanyCode(), lineCostSec.getCostSectionCode())) {
							lineCostSec.setCostSectionYear(null);
						}
						else if(lineCostSec.getCostSectionYear() == null) {
							lineCostSec.setCostSectionYear(currentYear);
						}

						apChangeDAO.insertApChangeLineCostSec(lineCostSec);

						if(!setFirstCostSecList) firstCostSecList.add(lineCostSec); // 変更前用に保存
					}
					if(apChangeLineCostSecList.size() > 0) {
						setFirstCostSecList = true;
					}
				}
			}
		}

		//	契約明細(リース)
		List<ApChangeLineContract> lineContractLeaseList = obj.getApChangeLineContractLease();
		if(lineContractLeaseList != null){
			for(int i = 0; i<lineContractLeaseList.size(); i++){
				ApChangeLineContract lineContractLease = lineContractLeaseList.get(i);

				lineContractLease.setApplicationId(obj.getApplicationId()); // IDのセット
				lineContractLease.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				lineContractLease.setCreateDate(Function.nvl(lineContractLease.getCreateDate(), sysdate));
				lineContractLease.setCreateStaffCode(Function.nvl(lineContractLease.getCreateStaffCode(), loginStaffCode));
				lineContractLease.setUpdateDate(sysdate);
				lineContractLease.setUpdateStaffCode(loginStaffCode);
				//	契約明細の作成
				apChangeDAO.insertApChangeLineContract(lineContractLease);

				//	契約明細(リース)の経費負担部課作成
				if(lineContractLease.getApChangeLineCostSecList() != null){
					List<ApChangeLineCostSec> apChangeLineCostSecList = lineContractLease.getApChangeLineCostSecList();
					if(apChangeLineCostSecList.size() > 0 && !setFirstCostSecList) {
						firstCostSecList = new ArrayList<ApChangeLineCostSec>();
					}
					for(int j = 0; j < apChangeLineCostSecList.size(); j++){
						ApChangeLineCostSec lineCostSec = apChangeLineCostSecList.get(j);
						lineCostSec.setApplicationId(lineContractLease.getApplicationId());
						lineCostSec.setApChangeLineCostType(LINE_COST_TYPE_LEASE);
						lineCostSec.setAstNum(lineContractLease.getAstNum());
						lineCostSec.setLineSeq(j + 1);
						lineCostSec.setCreateDate(lineContractLease.getCreateDate());
						lineCostSec.setCreateStaffCode(lineContractLease.getCreateStaffCode());
						lineCostSec.setUpdateDate(sysdate);
						lineCostSec.setUpdateStaffCode(loginStaffCode);
						lineCostSec.setCostCompanyCode(obj.getApCompanyCode());

						//	経費負担部課年度設定
						if(masterSession.isMICostSection(lineCostSec.getCostCompanyCode(), lineCostSec.getCostSectionCode())) {
							lineCostSec.setCostSectionYear(null);
						}
						else if(lineCostSec.getCostSectionYear() == null) {
							lineCostSec.setCostSectionYear(currentYear);
						}

						apChangeDAO.insertApChangeLineCostSec(lineCostSec);

						if(!setFirstCostSecList) firstCostSecList.add(lineCostSec); // 変更前用に保存
					}
					if(apChangeLineCostSecList.size() > 0) {
						setFirstCostSecList = true;
					}
				}
			}
		}

		//	契約明細(レンタル)
		List<ApChangeLineContract> lineContractRentalList = obj.getApChangeLineContractRental();
		if(lineContractRentalList != null){
			for(int i = 0; i<lineContractRentalList.size(); i++){
				ApChangeLineContract lineContractRental = lineContractRentalList.get(i);

				lineContractRental.setApplicationId(obj.getApplicationId()); // IDのセット
				lineContractRental.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				lineContractRental.setCreateDate(Function.nvl(lineContractRental.getCreateDate(), sysdate));
				lineContractRental.setCreateStaffCode(Function.nvl(lineContractRental.getCreateStaffCode(), loginStaffCode));
				lineContractRental.setUpdateDate(sysdate);
				lineContractRental.setUpdateStaffCode(loginStaffCode);

				apChangeDAO.insertApChangeLineContract(lineContractRental);

				//	契約明細(レンタル)の経費負担部課作成
				if(lineContractRental.getApChangeLineCostSecList() != null){
					List<ApChangeLineCostSec> apChangeLineCostSecList = lineContractRental.getApChangeLineCostSecList();
					if(apChangeLineCostSecList.size() > 0 && !setFirstCostSecList) {
						firstCostSecList = new ArrayList<ApChangeLineCostSec>();
					}
					for(int j = 0; j < apChangeLineCostSecList.size(); j++){
						ApChangeLineCostSec lineCostSec = apChangeLineCostSecList.get(j);
						lineCostSec.setApplicationId(lineContractRental.getApplicationId());
						lineCostSec.setApChangeLineCostType(LINE_COST_TYPE_RENTAL);
						lineCostSec.setAstNum(lineContractRental.getAstNum());
						lineCostSec.setLineSeq(j + 1);
						lineCostSec.setCreateDate(lineContractRental.getCreateDate());
						lineCostSec.setCreateStaffCode(lineContractRental.getCreateStaffCode());
						lineCostSec.setUpdateDate(sysdate);
						lineCostSec.setUpdateStaffCode(loginStaffCode);
						lineCostSec.setCostCompanyCode(obj.getApCompanyCode());

						//	経費負担部課年度設定
						if(masterSession.isMICostSection(lineCostSec.getCostCompanyCode(), lineCostSec.getCostSectionCode())) {
							lineCostSec.setCostSectionYear(null);
						}
						else if(lineCostSec.getCostSectionYear() == null) {
							lineCostSec.setCostSectionYear(currentYear);
						}

						apChangeDAO.insertApChangeLineCostSec(lineCostSec);

						if(!setFirstCostSecList) firstCostSecList.add(lineCostSec); // 変更前用に保存
					}
					if(apChangeLineCostSecList.size() > 0) {
						setFirstCostSecList = true;
					}
				}
			}
		}

		//	資産(機器)明細
		List<ApChangeLineAst> lineAstList = obj.getApChangeLineAst();
		if(lineAstList != null){
			for(int i = 0; i<lineAstList.size(); i++){
				ApChangeLineAst lineAst = lineAstList.get(i);

				lineAst.setApplicationId(obj.getApplicationId()); // IDのセット
				lineAst.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				lineAst.setCreateDate(Function.nvl(lineAst.getCreateDate(), sysdate));
				lineAst.setCreateStaffCode(Function.nvl(lineAst.getCreateStaffCode(), loginStaffCode));
				lineAst.setUpdateDate(sysdate);
				lineAst.setUpdateStaffCode(loginStaffCode);

				lineAst.setAutoAddFlag(Function.nvl(lineAst.getAutoAddFlag(), Constants.FLAG_NO)); // 自動追加フラグ

				apChangeDAO.insertApChangeLineAst(lineAst);
			}
		}

		//	ライセンス明細
		List<ApChangeLineLic> lineLicList = obj.getApChangeLineLic();
		if(lineLicList != null){
			for(int i = 0; i<lineLicList.size(); i++){
				ApChangeLineLic lineLic = lineLicList.get(i);

				lineLic.setApplicationId(obj.getApplicationId()); // IDのセット
				lineLic.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				lineLic.setCreateDate(Function.nvl(lineLic.getCreateDate(), sysdate));
				lineLic.setCreateStaffCode(Function.nvl(lineLic.getCreateStaffCode(), loginStaffCode));
				lineLic.setUpdateDate(sysdate);
				lineLic.setUpdateStaffCode(loginStaffCode);

				lineLic.setAutoAddFlag(Function.nvl(lineLic.getAutoAddFlag(), Constants.FLAG_NO)); // 自動追加フラグ

				apChangeDAO.insertApChangeLineLic(lineLic);
			}
		}

		//	経費負担部課明細
		List<ApChangeLineCostSec> lineCostSecNewList = obj.getApChangeLineCostSecNew();
		if(lineCostSecNewList != null){

			for(int i = 0; i<lineCostSecNewList.size(); i++){
				ApChangeLineCostSec lineCostSecNew = lineCostSecNewList.get(i);

				lineCostSecNew.setApplicationId(obj.getApplicationId()); // IDのセット
				lineCostSecNew.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				lineCostSecNew.setCreateDate(Function.nvl(lineCostSecNew.getCreateDate(), sysdate));
				lineCostSecNew.setCreateStaffCode(Function.nvl(lineCostSecNew.getCreateStaffCode(), loginStaffCode));
				lineCostSecNew.setUpdateDate(sysdate);
				lineCostSecNew.setUpdateStaffCode(loginStaffCode);

				//	経費負担部課明細区分、契約番号設定
				lineCostSecNew.setApChangeLineCostType("B");
				lineCostSecNew.setAstNum("-");

				//	経費負担部課年度設定
				if(masterSession.isMICostSection(lineCostSecNew.getCostCompanyCode(), lineCostSecNew.getCostSectionCode())) {
					lineCostSecNew.setCostSectionYear(null);
				}
				else if(lineCostSecNew.getCostSectionYear() == null) {
					lineCostSecNew.setCostSectionYear(currentYear);
				}

				apChangeDAO.insertApChangeLineCostSec(lineCostSecNew);
			}
		}

		//	現経費負担部課明細(変更前)
		if(setFirstCostSecList) obj.setApChangeLineCostSecOld(firstCostSecList); // 資産・契約がある場合は契約１件目の経費負担をセット
		List<ApChangeLineCostSec> lineCostSecOldList = obj.getApChangeLineCostSecOld();

		if(lineCostSecOldList != null){

			for(int i = 0; i<lineCostSecOldList.size(); i++){
				ApChangeLineCostSec lineCostSecOld = lineCostSecOldList.get(i);

				lineCostSecOld.setApplicationId(obj.getApplicationId()); // IDのセット
				lineCostSecOld.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				lineCostSecOld.setCreateDate(Function.nvl(lineCostSecOld.getCreateDate(), sysdate));
				lineCostSecOld.setCreateStaffCode(Function.nvl(lineCostSecOld.getCreateStaffCode(), loginStaffCode));
				lineCostSecOld.setUpdateDate(sysdate);
				lineCostSecOld.setUpdateStaffCode(loginStaffCode);

				//	経費負担部課明細区分、契約番号設定
				lineCostSecOld.setApChangeLineCostType("A");
				lineCostSecOld.setAstNum("-");

				//	経費負担部課年度設定
				if(masterSession.isMICostSection(lineCostSecOld.getCostCompanyCode(), lineCostSecOld.getCostSectionCode())) {
					lineCostSecOld.setCostSectionYear(null);
				}
				else if(lineCostSecOld.getCostSectionYear() == null) {
					lineCostSecOld.setCostSectionYear(currentYear);
				}

				apChangeDAO.insertApChangeLineCostSec(lineCostSecOld);
			}
		}
	}

	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateApChange(String loginStaffCode, String accessLevel, ApChange obj) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 項目定義バリデーション
		int apStatus = obj.getApStatus() == null ? null : Integer.valueOf(obj.getApStatus());

		if(!Function.isAccessLevelAdmin(accessLevel)) { // 全社権限ではない場合
			apStatus += 5;
		}

		// ヘッダ
		errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_CHANGE, "NEA_AP_CHANGE", obj, apStatus, null));

		// 経費負担部課明細
		List<ApChangeLineCostSec> costSecNewLine = obj.getApChangeLineCostSecNew();

		if(costSecNewLine != null && costSecNewLine.size() > 0) {
			for(int i = 0; i < costSecNewLine.size(); i++) {
				ApChangeLineCostSec costSecObj = costSecNewLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_CHANGE, "NEA_AP_CHANGE_LINE_COST_SEC", costSecObj, apStatus, null));
			}
		}

		String stat = Function.nvl(obj.getApStatus(), ""); // ステータス
		// 承認済・却下以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_CHANGE_APPROVE)
			&& !stat.equals(Constants.AP_STATUS_CHANGE_REJECT)) {

			//////////////////////////////////// 条件付必須バリデーション
			if(Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_AST)
					|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_LIC)
					|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT_EA)) {
				// 現物
				// 移動予定日
				if(obj.getChgScheduleDate() == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "移動日-移動予定日"));
				}
			} else {
				// 経費
				// 移動年月度
				if(Function.nvl(obj.getChgSchedulePeriod(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "移動日-経費変更年月度"));
				}
			}

			//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)
			// 申請者
			if(!Function.nvl(obj.getApStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_APPROVE) || Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_REJECT)) {
					// 承認済・却下は退職社員OK
					if(masterSession.getStaff(obj.getApCompanyCode(), obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				} else {
					// 承認済・却下以外は退職社員NG
					if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getApStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者"));
					}
				}
			}
			// 使用者
			if(!Function.nvl(obj.getUseStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_APPROVE) || Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_REJECT)) {
					// 承認済・却下は退職社員OK
					if(masterSession.getStaff(null, obj.getUseStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "変更項目-使用者"));
					}
				} else {
					// 承認済・却下以外は退職社員NG
					if(masterSession.getStaffValid(null, obj.getUseStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "変更項目-使用者"));
					}
				}
			}
			// 無形管理担当者
			if(!Function.nvl(obj.getIntHolStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_APPROVE) || Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_REJECT)) {
					// 承認済・却下は退職社員OK
					if(masterSession.getStaff(obj.getIntHolStaffCompanyCode(), obj.getIntHolStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "変更項目-無形管理担当者"));
					}
				} else {
					// 承認済・却下以外は退職社員NG
					if(masterSession.getStaffValid(obj.getIntHolStaffCompanyCode(), obj.getIntHolStaffCode()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "変更項目-無形管理担当者"));
					}
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
				//	CTCのみ対象
				if(Function.nvl(obj.getApCompanyCode(), "").equals("001")){
					// 移動申請区分が"無形"以外の場合、プロジェクトの有効期限、ステータスのチェックを行う
					if(!Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT_EA)
					&& !Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT)){
/*
						//	有効ステータスチェック
						lovParam = new HashMap<String, Object>();	// パラメータリセット
						lovParam.put("companyCode", obj.getApCompanyCode());
						lovParam.put("statusLimit", "1");	//	有効ステータス条件ON
						lovData = masterSession.getLovData("selectProject_LOV", lovParam, obj.getCostDepPrjCode());
						if(lovData == null){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-減価償却プロジェクトコード", "有効なステータスのプロジェクトを選択してください。"));
						}
*/
						//	有効期限チェック
						lovParam = new HashMap<String, Object>();	// パラメータリセット
						lovParam.put("companyCode", obj.getApCompanyCode());
						lovParam.put("prjCategory", Constants.PROJECT_GATEGORY_COST_DEP);
						String apDateStr = (obj.getApDate() == null) ? null : new SimpleDateFormat("yyyy/MM/dd").format(obj.getApDate());
						lovParam.put("searchDateFrom", apDateStr); // 開始日
						lovParam.put("searchDateTo", apDateStr); // 終了日
						lovData = masterSession.getLovData("selectProject_LOV", lovParam, obj.getCostDepPrjCode());
						if(lovData == null){
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-減価償却プロジェクトコード", "選択したプロジェクトの有効期限が切れています。\n再度プロジェクトを選択してください。"));
						}
					}
				}
			}

			// カレント年度取得
			CodeName currentYearCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
			int currentYear = Integer.valueOf(currentYearCodeName.getValue1());

			// 経費負担部課コード
			if(costSecNewLine != null && costSecNewLine.size() > 0) {
				for(int i = 0; i < costSecNewLine.size(); i++) {
					ApChangeLineCostSec costSecObj = costSecNewLine.get(i);
					if(!Function.nvl(costSecObj.getCostSectionCode(), "").equals("")) {
						boolean currentYearErrorFlag = false;
						// 未申請・申請中
						if(stat.equals(Constants.AP_STATUS_BGN_INT_NOAPPLY) ||
						   stat.equals(Constants.AP_STATUS_BGN_INT_APPLY)) {
							Integer costSectionYear = costSecObj.getCostSectionYear();
							if(costSectionYear == null || costSectionYear.intValue() != currentYear) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "変更項目-経費負担部署[No" + costSecObj.getLineSeq() + "]-経費負担部課 : 当年度の経費負担部課を入力してください。"));
								currentYearErrorFlag = true;
							}
						}
						if(!currentYearErrorFlag && masterSession.getCostSection(costSecObj.getCostCompanyCode(), costSecObj.getCostSectionCode(), null, null) == null) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "変更項目-経費負担部課[No" + costSecObj.getLineSeq() + "]-経費負担部課"));
						}
					}
				}
			}
			// 担当部承認者-現担当部-経費負担部課長/G
			if(!Function.nvl(obj.getApprCostStaffCodeOld(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_APPROVE) || Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_REJECT)) {
					// 承認済・却下は退職社員OK
					if(masterSession.getStaff(obj.getApCompanyCode(), obj.getApprCostStaffCodeOld()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "担当部承認者-現担当部-経費負担部課長/GL"));
					}
				} else {
					// 承認済・却下以外は退職社員NG
					if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getApprCostStaffCodeOld()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "担当部承認者-現担当部-経費負担部課長/GL"));
					}
				}
			}
			// 担当部承認者-現担当部-部長
			if(!Function.nvl(obj.getApprSuperiorStaffCodeOld(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_APPROVE) || Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_REJECT)) {
					// 承認済・却下は退職社員OK
					if(masterSession.getStaff(obj.getApCompanyCode(), obj.getApprSuperiorStaffCodeOld()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "担当部承認者-現担当部-部長"));
					}
				} else {
					// 承認済・却下以外は退職社員NG
					if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getApprSuperiorStaffCodeOld()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "担当部承認者-現担当部-部長"));
					}
				}
			}
			// 担当部承認者-新担当部-経費負担部課長/GL
			if(!Function.nvl(obj.getApprCostStaffCodeNew(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_APPROVE) || Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_REJECT)) {
					// 承認済・却下は退職社員OK
					if(masterSession.getStaff(obj.getApCompanyCode(), obj.getApprCostStaffCodeNew()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "担当部承認者-新担当部-経費負担部課長/GL"));
					}
				} else {
					// 承認済・却下以外は退職社員NG
					if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getApprCostStaffCodeNew()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "担当部承認者-新担当部-経費負担部課長/GL"));
					}
				}
			}
			// 担当部承認者-新担当部-部長
			if(!Function.nvl(obj.getApprSuperiorStaffCodeNew(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_APPROVE) || Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_CHANGE_REJECT)) {
					// 承認済・却下は退職社員OK
					if(masterSession.getStaff(obj.getApCompanyCode(), obj.getApprSuperiorStaffCodeNew()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "担当部承認者-新担当部-部長"));
					}
				} else {
					// 承認済・却下以外は退職社員NG
					if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getApprSuperiorStaffCodeNew()) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "担当部承認者-新担当部-部長"));
					}
				}
			}

			//////////////////////////////////// その他バリデーション
			if(Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_AST)
					|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_LIC)
					|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT_EA)) {
				// 現物

				// 移動対象が選択されていない
				if((obj.getApChangeLineAst() == null || obj.getApChangeLineAst().size() == 0)
					&& (obj.getApChangeLineLic() == null || obj.getApChangeLineLic().size() == 0)
					&& (obj.getApChangeLineFldInt() == null || obj.getApChangeLineFldInt().size() == 0)
					) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "移動対象を選択してください。"));
				}
				// 変更項目が入力されていない
				if(Function.nvl(obj.getHolCompanyCode(), "").equals("")
					&& Function.nvl(obj.getHolOfficeCode(), "").equals("")
					&& Function.nvl(obj.getHolStaffCode(), "").equals("")
					&& Function.nvl(obj.getUseStaffCode(), "").equals("")
					&& Function.nvl(obj.getIntHolStaffCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "変更項目を入力してください。"));
				}

			} else {
				// 経費

				// 経費変更年月度の妥当性チェック
				String schPeriod = Function.nvl(obj.getChgSchedulePeriod(), "");
				if(!schPeriod.equals("")) {
					Map<String, Object> lovParam = new HashMap<String, Object>();
					lovParam.put("companyCode", obj.getApCompanyCode());
					if(masterSession.getLovData("selectCurrentYearPeriod_LOV", lovParam, schPeriod) == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "移動日-経費変更年月度"));
					}
				}

				// 移動対象が選択されていない
				if((obj.getApChangeLineFldTan() == null || obj.getApChangeLineFldTan().size() == 0)
					&& (obj.getApChangeLineFldInt() == null || obj.getApChangeLineFldInt().size() == 0)
					&& (obj.getApChangeLineContractLease() == null || obj.getApChangeLineContractLease().size() == 0)
					&& (obj.getApChangeLineContractRental() == null || obj.getApChangeLineContractRental().size() == 0)
					) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "移動対象を選択してください。"));
				}
				// 変更項目が入力されていない
				if(Function.nvl(obj.getCostType(), "").equals("")
					&& Function.nvl(obj.getCostCompanyCode(), "").equals("")
					&& Function.nvl(obj.getHolRepOfficeCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "変更項目を入力してください。"));
				}

				// 仮勘定・本勘定混在
				List<ApChangeLineFld> fldList = new ArrayList<ApChangeLineFld>();
				fldList.addAll(obj.getApChangeLineFldTan());
				fldList.addAll(obj.getApChangeLineFldInt());

				if(fldList != null && fldList.size() > 0) {
					String lastTempFlag = "";
					for(int i = 0; i < fldList.size(); i++) {
						ApChangeLineFld fldObj = fldList.get(i);

						if(!lastTempFlag.equals("") && !Function.nvl(fldObj.getTempFlag(), "").equals(lastTempFlag)) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "本勘定と仮勘定の資産を同じ申請書に含める事はできません。"));
							break;
						}

						lastTempFlag = Function.nvl(fldObj.getTempFlag(), "");
					}
				}
			}

			// 経費負担部課重複・配分合計
			//	変更後
			if(costSecNewLine != null && costSecNewLine.size() > 0) {
				Set<String> sectionCodeSet = new HashSet<String>();
				int distTotal = 0;
				double adjustNum = 10;
				boolean isDup = false; // 重複フラグ
				for(int i = 0; i < costSecNewLine.size(); i++) {
					ApChangeLineCostSec costSecObj = costSecNewLine.get(i);
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

				if(Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)) {
					if(costSecNewLine != null && costSecNewLine.size() > 1) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-経費負担部署", "販売管理費/原価区分に\"原価\"を指定した場合1件しか入力できません。"));
					}
				}
			}

			//////////////////////////////////// 申請中バリデーション
			// 現物申請の申請中チェック
			if(Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_AST)
				|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_LIC)
				|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT_EA)) {

				// 申請中の機器存在チェック
				List<ApChangeLineAst> astList = obj.getApChangeLineAst();
				if(astList != null && astList.size() > 0) {
					String assetIdPlural = "";
					Set<String> assetIdSet = new HashSet<String>();

					// 申請している情報機器管理番号を取得
					for(int i = 0; i < astList.size(); i++) {
						ApChangeLineAst astObj = astList.get(i);
						if(!Function.nvl(astObj.getAssetId(), "").equals("")) {
							assetIdPlural += " " + astObj.getAssetId();
							assetIdSet.add(astObj.getAssetId());
						}
					}

					// 同一機器申請中の移動申請を検索
					ApChangeSC searchParam = new ApChangeSC();
					searchParam.setApChangeType(Constants.AP_CHANGE_TYPE_AST + " " + Constants.AP_CHANGE_TYPE_LIC + " " + Constants.AP_CHANGE_TYPE_INT_EA); // 現物申請
					searchParam.setAssetIdPlural(assetIdPlural);
					searchParam.setApStatus(Constants.AP_STATUS_CHANGE_NOAPPLY + " " + Constants.AP_STATUS_CHANGE_APPLY + " " + Constants.AP_STATUS_CHANGE_SENDBACK); // 未申請・申請中・未申請(再)

					List<ApChangeSR> searchList = searchApChange(loginStaffCode, null, searchParam);
					if(searchList != null && searchList.size() > 0) {
						for(int i = 0; i < searchList.size(); i++) {
							String applicationId = searchList.get(i).getApplicationId();
							// 同一機器を申請している移動申請が存在する
							if(!applicationId.equals(Function.nvl(obj.getApplicationId(), ""))) {
								ApChange apc = getApChange(applicationId);
								// 同一機器の情報機器管理番号を取得
								List<ApChangeLineAst> dupAstList = apc.getApChangeLineAst();

								for(int j = 0; j < dupAstList.size(); j++) {
									String dupAssetId = dupAstList.get(j).getAssetId();
									if(assetIdSet.contains(dupAssetId)) {
										errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に既に現物情報の移動申請処理中の情報機器等が含まれているため処理を継続できません。(情報機器管理番号：" + dupAssetId + ")"));
									}
								}
							}
						}
					}
				}

				// 申請中のライセンス存在チェック
				List<ApChangeLineLic> licList = obj.getApChangeLineLic();
				if(licList != null && licList.size() > 0) {
					String licenseIdPlural = "";
					Set<String> licenseIdSet = new HashSet<String>();

					// 申請している情報ライセンス管理番号を取得
					for(int i = 0; i < licList.size(); i++) {
						ApChangeLineLic licObj = licList.get(i);
						if(!Function.nvl(licObj.getLicenseId(), "").equals("")) {
							licenseIdPlural += " " + licObj.getLicenseId();
							licenseIdSet.add(licObj.getLicenseId());
						}
					}

					// 同一ライセンス申請中の移動申請を検索
					ApChangeSC searchParam = new ApChangeSC();
					searchParam.setApChangeType(Constants.AP_CHANGE_TYPE_AST + " " + Constants.AP_CHANGE_TYPE_LIC + " " + Constants.AP_CHANGE_TYPE_INT_EA); // 現物申請
					searchParam.setLicenseIdPlural(licenseIdPlural);
					searchParam.setApStatus(Constants.AP_STATUS_CHANGE_NOAPPLY + " " + Constants.AP_STATUS_CHANGE_APPLY + " " + Constants.AP_STATUS_CHANGE_SENDBACK); // 未申請・申請中・未申請(再)

					List<ApChangeSR> searchList = searchApChange(loginStaffCode, null, searchParam);
					if(searchList != null && searchList.size() > 0) {
						for(int i = 0; i < searchList.size(); i++) {
							String applicationId = searchList.get(i).getApplicationId();
							// 同一ライセンスを申請している移動申請が存在する
							if(!applicationId.equals(Function.nvl(obj.getApplicationId(), ""))) {
								ApChange apc = getApChange(applicationId);
								// 同一ライセンスの情報ライセンス管理番号を取得
								List<ApChangeLineLic> dupLicList = apc.getApChangeLineLic();

								for(int j = 0; j < dupLicList.size(); j++) {
									String dupLicenseId = dupLicList.get(j).getLicenseId();
									if(licenseIdSet.contains(dupLicenseId)) {
										errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に既に現物情報の移動申請処理中のライセンスが含まれているため処理を継続できません。(ライセンス管理番号：" + dupLicenseId + ")"));
									}
								}
							}
						}
					}
				}

				// 申請中の資産存在チェック
				List<ApChangeLineFld> fldList = obj.getApChangeLineFldInt();

				if(fldList != null && fldList.size() > 0) {
					String astNumPlural = "";
					Set<String> astNumSet = new HashSet<String>();

					// 申請している資産番号を取得
					for(int i = 0; i < fldList.size(); i++) {
						ApChangeLineFld fldObj = fldList.get(i);
						if(!Function.nvl(fldObj.getAstNum(), "").equals("")) {
							astNumPlural += " " + fldObj.getAstNum();
							astNumSet.add(fldObj.getAstNum());
						}
					}

					// 同一資産申請中の移動申請を検索
					ApChangeSC searchParam = new ApChangeSC();
					searchParam.setApChangeType(Constants.AP_CHANGE_TYPE_AST + " " + Constants.AP_CHANGE_TYPE_LIC + " " + Constants.AP_CHANGE_TYPE_INT_EA); // 現物申請
					searchParam.setAstNumPlural(astNumPlural);
					searchParam.setApStatus(Constants.AP_STATUS_CHANGE_NOAPPLY + " " + Constants.AP_STATUS_CHANGE_APPLY + " " + Constants.AP_STATUS_CHANGE_SENDBACK); // 未申請・申請中・未申請(再)

					List<ApChangeSR> searchList = searchApChange(loginStaffCode, null, searchParam);
					if(searchList != null && searchList.size() > 0) {
						for(int i = 0; i < searchList.size(); i++) {
							String applicationId = searchList.get(i).getApplicationId();
							// 同一資産を申請している移動申請が存在する
							if(!applicationId.equals(Function.nvl(obj.getApplicationId(), ""))) {
								ApChange apc = getApChange(applicationId);
								// 同一資産の資産番号を取得
								List<ApChangeLineFld> dupFldList = apc.getApChangeLineFldInt();

								for(int j = 0; j < dupFldList.size(); j++) {
									String dupAstNum = dupFldList.get(j).getAstNum();
									if(astNumSet.contains(dupAstNum)) {
										errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に既に現物情報の移動申請処理中の資産が含まれているため処理を継続できません。(資産番号：" + dupAstNum + ")"));
									}
								}
							}
						}
					}
				}
			}

			// 経費申請の申請中チェック
			if(Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_LE_RE)
				|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_TAN)
				|| Function.nvl(obj.getApChangeType(), "").equals(Constants.AP_CHANGE_TYPE_INT)) {

				String koyunoPlural = ""; // ProPlus更新中確認用

				// 申請中の資産存在チェック
				List<ApChangeLineFld> fldList = new ArrayList<ApChangeLineFld>();
				fldList.addAll(obj.getApChangeLineFldTan());
				fldList.addAll(obj.getApChangeLineFldInt());

				if(fldList != null && fldList.size() > 0) {
					String astNumPlural = "";
					Set<String> astNumSet = new HashSet<String>();

					// 申請している資産番号を取得
					for(int i = 0; i < fldList.size(); i++) {
						ApChangeLineFld fldObj = fldList.get(i);
						koyunoPlural += " " + fldObj.getPpId();
						if(!Function.nvl(fldObj.getAstNum(), "").equals("")) {
							astNumPlural += " " + fldObj.getAstNum();
							astNumSet.add(fldObj.getAstNum());
						}
					}

					// 同一資産申請中の移動申請を検索
					ApChangeSC searchParam = new ApChangeSC();
					searchParam.setApChangeType(Constants.AP_CHANGE_TYPE_LE_RE + " " + Constants.AP_CHANGE_TYPE_TAN + " " + Constants.AP_CHANGE_TYPE_INT); // 経費申請
					searchParam.setAstNumPlural(astNumPlural);
					searchParam.setApStatus(Constants.AP_STATUS_CHANGE_NOAPPLY + " " + Constants.AP_STATUS_CHANGE_APPLY + " " + Constants.AP_STATUS_CHANGE_SENDBACK); // 未申請・申請中・未申請(再)

					List<ApChangeSR> searchList = searchApChange(loginStaffCode, null, searchParam);
					if(searchList != null && searchList.size() > 0) {
						for(int i = 0; i < searchList.size(); i++) {
							String applicationId = searchList.get(i).getApplicationId();
							// 同一資産を申請している移動申請が存在する
							if(!applicationId.equals(Function.nvl(obj.getApplicationId(), ""))) {
								ApChange apc = getApChange(applicationId);
								// 同一資産の資産番号を取得
								List<ApChangeLineFld> dupFldList = apc.getApChangeLineFldTan();
								dupFldList.addAll(apc.getApChangeLineFldInt());

								for(int j = 0; j < dupFldList.size(); j++) {
									String dupAstNum = dupFldList.get(j).getAstNum();
									if(astNumSet.contains(dupAstNum)) {
										errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に既に経費情報の移動申請処理中の資産が含まれているため処理を継続できません。(資産番号：" + dupAstNum + ")"));
									}
								}
							}
						}
					}
				}

				// 申請中の契約存在チェック
				List<ApChangeLineContract> contractList = new ArrayList<ApChangeLineContract>();
				contractList.addAll(obj.getApChangeLineContractLease());
				contractList.addAll(obj.getApChangeLineContractRental());

				if(contractList != null && contractList.size() > 0) {
					String astNumPlural = "";
					Set<String> astNumSet = new HashSet<String>();

					// 申請している契約番号を取得
					for(int i = 0; i < contractList.size(); i++) {
						ApChangeLineContract contractObj = contractList.get(i);
						koyunoPlural += " " + contractObj.getPpIdAst() + " " + contractObj.getPpIdContract();
						if(!Function.nvl(contractObj.getAstNum(), "").equals("")) {
							astNumPlural += " " + contractObj.getAstNum();
							astNumSet.add(contractObj.getAstNum());
						}
					}

					// 同一契約申請中の移動申請を検索
					ApChangeSC searchParam = new ApChangeSC();
					searchParam.setApChangeType(Constants.AP_CHANGE_TYPE_LE_RE + " " + Constants.AP_CHANGE_TYPE_TAN + " " + Constants.AP_CHANGE_TYPE_INT); // 経費申請
					searchParam.setAstNumPlural(astNumPlural);
					searchParam.setApStatus(Constants.AP_STATUS_CHANGE_NOAPPLY + " " + Constants.AP_STATUS_CHANGE_APPLY + " " + Constants.AP_STATUS_CHANGE_SENDBACK); // 未申請・申請中・未申請(再)

					List<ApChangeSR> searchList = searchApChange(loginStaffCode, null, searchParam);
					if(searchList != null && searchList.size() > 0) {
						for(int i = 0; i < searchList.size(); i++) {
							String applicationId = searchList.get(i).getApplicationId();
							// 同一契約を申請している移動申請が存在する
							if(!applicationId.equals(Function.nvl(obj.getApplicationId(), ""))) {
								ApChange apc = getApChange(applicationId);
								// 同一契約の契約番号を取得
								List<ApChangeLineContract> dupContractList = apc.getApChangeLineContractLease();
								dupContractList.addAll(apc.getApChangeLineContractRental());

								for(int j = 0; j < dupContractList.size(); j++) {
									String dupAstNum = dupContractList.get(j).getAstNum();
									if(astNumSet.contains(dupAstNum)) {
										errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に既に経費情報の移動申請処理中の物件が含まれているため処理を継続できません。(契約番号,契約枝番：" + dupContractList.get(j).getContractNum() + "," + dupContractList.get(j).getContractSubNum() + ")"));
									}
								}
							}
						}
					}
				}

				//////////////////////////////////// ProPlus承認中バリデーション
				List<PpfsUnUpd> ppfsUnUpdList = fldSession.searchPpfsUnUpd(obj.getApCompanyCode(), koyunoPlural);
				if(ppfsUnUpdList != null && ppfsUnUpdList.size() > 0){
					for(int i = 0; i < ppfsUnUpdList.size(); i++){
						PpfsUnUpd ppfsUnUpd = ppfsUnUpdList.get(i);
						if(ppfsUnUpd.getKykno() == null) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に資産情報更新中の資産が含まれているため処理を継続できません。(資産番号：" + ppfsUnUpd.getOya() + ppfsUnUpd.getEda() + ")"));
						} else {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "申請対象に契約情報更新中の物件が含まれているため処理を継続できません。(契約番号：" + ppfsUnUpd.getKykno() + ")"));
						}
					}
				}
			}

			//////////////////////////////////// 情シス配備混在バリデーション
			//	情報機器等の管理区分チェック
			List<ApChangeLineAst> astList = obj.getApChangeLineAst();
			if(astList != null && astList.size() > 0) {
				boolean isError = false;
				String beforeManageCode = null;	//	一つ前の情シス配備制御の値
				for(int i = 0; i < astList.size(); i++) {
					ApChangeLineAst astObj = astList.get(i);

					if(Function.nvl(astObj.getAssetId(), "").equals("")) continue; // 機器番号が存在しない明細は判別除外

					String manageCode = "";
					//	管理区分あり？
					if( !Function.nvl(astObj.getAstManageType(), "").equals("") ) {
						CodeName astManageType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, astObj.getAstManageType(), null, null);
						if(astManageType != null) {
							manageCode = Function.nvl(astManageType.getValue2(), "");
						}
					}
					else{
					//	空白の場合、情シス配備以外を設定
						manageCode = Constants.ASSET_MANAGE_TYPE_VALUE2_SECTION_DEPLOY_EXCEPT;
					}
					//	一つ前の情シス配備制御の値と現在の情シス配備制御の値が違う？
					if( !Function.nvl(beforeManageCode,"").equals("") && !manageCode.equals(beforeManageCode) ){
						isError = true;
						break;
					}
					beforeManageCode = manageCode;
				}
				if(isError){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "情報システム部配備の”シンクライアント/SunRay”および”持ち出し専用機器”は、他の情報機器等と同時には申請できません。"));
				}
			}

			//////////////////////////////////// 移動申請停止バリデーション
			CodeName apChangeStop = masterSession.getCodeName(Constants.CATEGORY_CODE_STOP_AP_CHANGE, null, obj.getApCompanyCode(), null);
			if(apChangeStop != null) {
				String stopHolSectionFlag = Function.nvl(apChangeStop.getValue2(), ""); // 保有部署変更停止
				String stopHolStaffFlag = Function.nvl(apChangeStop.getValue3(), ""); // 資産管理担当者変更停止
				String stopHolOfficeFlag = Function.nvl(apChangeStop.getValue4(), ""); // 個別設置場所変更停止
				String stopUseStaffFlag = Function.nvl(apChangeStop.getValue5(), ""); // 使用者変更停止
				String stopIntHolStaffFlag = Function.nvl(apChangeStop.getValue6(), ""); // 無形管理担当者変更停止
				String stopHolRepOfficeFlag = Function.nvl(apChangeStop.getValue7(), ""); // 代表設置場所変更停止
				String stopCostTypeFlag = Function.nvl(apChangeStop.getValue8(), ""); // 販管・原価区分変更停止
				String stopCostSectionFlag = Function.nvl(apChangeStop.getValue9(), ""); // 経費負担部署変更停止

				boolean isStop = false;
				if(stopHolSectionFlag.equals(Constants.FLAG_YES) && !Function.nvl(obj.getHolCompanyCode(), "").equals("")) {
					isStop = true;
				}
				if(stopHolStaffFlag.equals(Constants.FLAG_YES) && !Function.nvl(obj.getHolStaffCode(), "").equals("")) {
					isStop = true;
				}
				if(stopHolOfficeFlag.equals(Constants.FLAG_YES) && !Function.nvl(obj.getHolOfficeCode(), "").equals("")) {
					isStop = true;
				}
				if(stopUseStaffFlag.equals(Constants.FLAG_YES) && !Function.nvl(obj.getUseStaffCode(), "").equals("")) {
					isStop = true;
				}
				if(stopIntHolStaffFlag.equals(Constants.FLAG_YES) && !Function.nvl(obj.getIntHolStaffCode(), "").equals("")) {
					isStop = true;
				}
				if(stopCostTypeFlag.equals(Constants.FLAG_YES) && !Function.nvl(obj.getCostType(), "").equals("")) {
					isStop = true;
				}
				if(stopCostSectionFlag.equals(Constants.FLAG_YES) && !Function.nvl(obj.getCostCompanyCode(), "").equals("")) {
					isStop = true;
				}
				if(stopHolRepOfficeFlag.equals(Constants.FLAG_YES) && !Function.nvl(obj.getHolRepOfficeCode(), "").equals("")) {
					isStop = true;
				}

				if(isStop) {
					errMsg.append(apChangeStop.getValue1());
				}
			}
		}

		return errMsg.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#getAstLicByFld(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ApChange getAstLicByFld(String loginStaffCode, String accessLevel, String companyCode, String shisanNumPlural) {
		ApChange apChange = new ApChange();
		List<ApChangeLineAst> apChangeLineAstList = new ArrayList<ApChangeLineAst>();
		List<ApChangeLineLic> apChangeLineLicList = new ArrayList<ApChangeLineLic>();

		if(!Function.nvl(shisanNumPlural, "").equals("")){
			//	情報機器検索
			AssetSC searchParam = new AssetSC();
			searchParam.setShisanNumPlural(shisanNumPlural);
			searchParam.setHolCompanyCode(companyCode);
			searchParam.setDeleteFlag(Constants.FLAG_NO); // 抹消済みは除外
			List<AssetSR> assetList = assetSession.searchAsset(loginStaffCode, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, searchParam, false);

			//	型変換
			if(assetList != null && assetList.size() > 0){
				for(int i = 0; i < assetList.size(); i++){
					AssetSR asset = assetList.get(i);
					ApChangeLineAst apChangeLineAst = new ApChangeLineAst();
					apChangeLineAst.setAssetId(asset.getAssetId());
					apChangeLineAst.setHolCompanyCode(asset.getHolCompanyCode());
					apChangeLineAst.setHolSectionCode(asset.getHolSectionCode());
					apChangeLineAst.setHolSectionName(asset.getHolSectionName());
					apChangeLineAst.setHolSectionYear(asset.getHolSectionYear());
					apChangeLineAst.setHolStaffCode(asset.getHolStaffCode());
					apChangeLineAst.setHolStaffName(asset.getHolStaffName());
					apChangeLineAst.setHolOfficeCode(asset.getHolOfficeCode());
					apChangeLineAst.setHolOfficeName(asset.getHolOfficeName());
					apChangeLineAst.setHolQuantity(asset.getHolQuantity());
					apChangeLineAst.setUseStaffCode(asset.getUseStaffCode());
					apChangeLineAst.setUseStaffName(asset.getUseStaffName());
					apChangeLineAst.setUseStaffCompanyCode(asset.getUseStaffCompanyCode());
					apChangeLineAst.setUseStaffSectionYear(asset.getUseStaffSectionYear());
					apChangeLineAst.setUseStaffSectionCode(asset.getUseStaffSectionCode());
					apChangeLineAst.setAstName(asset.getAstName());
					apChangeLineAst.setAstCategory1Code(asset.getAstCategory1Code());
					apChangeLineAst.setAstCategory2Code(asset.getAstCategory2Code());
					apChangeLineAst.setAstSystemSectionDeployFlag(asset.getAstSystemSectionDeployFlag());
					apChangeLineAst.setAstAssetType(asset.getAstAssetType());
					apChangeLineAst.setAstAssetTypeName(asset.getAstAssetTypeName());
					apChangeLineAst.setAstManageType(asset.getAstManageType());
					apChangeLineAst.setAstNum(asset.getShisanNum());
					apChangeLineAst.setPpId(Function.nvl(asset.getKoyunoL(), asset.getKoyunoF()));
					apChangeLineAst.setGetApplicationId(asset.getGetApplicationId());
					apChangeLineAst.setContractNum(asset.getContractNum());
					apChangeLineAst.setContractSubNum(asset.getContractEda());

					apChangeLineAstList.add(apChangeLineAst);
				}
			}

			//	ﾗｲｾﾝｽ検索
			LicenseSC searchParam2 = new LicenseSC();
			searchParam2.setShisanNumPlural(shisanNumPlural);
			searchParam2.setHolCompanyCode(companyCode);
			searchParam2.setDeleteFlag(Constants.FLAG_NO); // 抹消済みは除外
			List<LicenseSR> licenseList = licenseSession.searchLicense(loginStaffCode, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, searchParam2, false);

			//	型変換
			if(licenseList != null && licenseList.size() > 0){
				for(int i = 0; i < licenseList.size(); i++){
					LicenseSR license = licenseList.get(i);
					ApChangeLineLic apChangeLineLic = new ApChangeLineLic();
					apChangeLineLic.setLicenseId(license.getLicenseId());
					apChangeLineLic.setHolCompanyCode(license.getHolCompanyCode());
					apChangeLineLic.setHolSectionCode(license.getHolSectionCode());
					apChangeLineLic.setHolSectionName(license.getHolSectionName());
					apChangeLineLic.setHolSectionYear(license.getHolSectionYear());
					apChangeLineLic.setHolStaffCode(license.getHolStaffCode());
					apChangeLineLic.setHolStaffName(license.getHolStaffName());
					apChangeLineLic.setSoftwareId(license.getSoftwareId());
					apChangeLineLic.setSoftwareName(license.getSoftwareName());
					apChangeLineLic.setSoftwareMakerId(license.getSoftwareMakerId());
					apChangeLineLic.setSoftwareMakerName(license.getSoftwareMakerName());
					apChangeLineLic.setSoftwareMakerId(license.getSoftwareMakerId());
					apChangeLineLic.setLicAssetType(license.getLicAssetType());
					apChangeLineLic.setLicAssetTypeName(license.getLicAssetTypeName());
					apChangeLineLic.setLicQuantityType(license.getLicQuantityType());
					apChangeLineLic.setLicQuantity(license.getLicQuantity());
					apChangeLineLic.setGetApplicationId(license.getGetApplicationId());
					apChangeLineLic.setContractNum(license.getContractNum());
					apChangeLineLic.setContractSubNum(license.getContractEda());
					apChangeLineLic.setAstNum(license.getShisanNum());
					apChangeLineLic.setPpId(Function.nvl(license.getKoyunoL(), license.getKoyunoF()));

					apChangeLineLicList.add(apChangeLineLic);
				}
			}
		}

		apChange.setApChangeLineAst(apChangeLineAstList);
		apChange.setApChangeLineLic(apChangeLineLicList);

		return apChange;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#getFldContractByAst(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ApChange getFldContractByAst(String loginStaffCode, String accessLevel, String companyCode, String assetIdPlural) {
		return getFldContractByAstLic(loginStaffCode, accessLevel, companyCode, assetIdPlural, 1);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#getFldContractByLic(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public ApChange getFldContractByLic(String loginStaffCode, String accessLevel, String companyCode, String licenseIdPlural) {
		return getFldContractByAstLic(loginStaffCode, accessLevel, companyCode, licenseIdPlural, 2);
	}

	/**
	 * 機器・ﾗｲｾﾝｽから紐付資産・物件取得
	 * @param loginStaffCode ログイン社員番号
	 * @param accessLevel    アクセスレベル
	 * @param companyCode    会社コード
	 * @param idPlural 情報機器・ライセンス管理番号（スペース区切り）
	 * @param assetOrLicense 1:asset, 2:license
	 * @return 資産、物件明細を格納した移動申請Obj
	 * @throws AppException
	 */
	private ApChange getFldContractByAstLic(String loginStaffCode, String accessLevel, String companyCode, String idPlural, int assetOrLicense) {
		ApChange apChange = new ApChange();
		List<ApChangeLineFld> apChangeLineFldTanList = new ArrayList<ApChangeLineFld>();
		List<ApChangeLineFld> apChangeLineFldIntList = new ArrayList<ApChangeLineFld>();
		List<ApChangeLineContract> apChangeLineContractLeaseList = new ArrayList<ApChangeLineContract>();
		List<ApChangeLineContract> apChangeLineContractRentalList = new ArrayList<ApChangeLineContract>();

		if(!Function.nvl(idPlural, "").equals("")){
			//	固定資産検索
			PpfsFldSC searchParamFld = new PpfsFldSC();
			if(assetOrLicense == 1) {
				searchParamFld.setAssetIdPlural(idPlural);
			} else {
				searchParamFld.setLicenseIdPlural(idPlural);
			}
			searchParamFld.setShisanknrkbn("1 2 3 4 5 6 A"); // Fリース除外
			searchParamFld.setShisanjotaikbn("1"); // 状態区分:通常
			List<PpfsFldSR> fldList = fldSession.searchFld(loginStaffCode, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, companyCode, searchParamFld);

			//	型変換
			if(fldList != null && fldList.size() > 0){
				for(int i = 0; i < fldList.size(); i++){
					PpfsFldSR fld = fldList.get(i);
					ApChangeLineFld apChangeLineFld = new ApChangeLineFld();

					apChangeLineFld.setPpId(fld.getKoyuno());
					apChangeLineFld.setGetApplicationId(fld.getApplicationId());
					apChangeLineFld.setAstDate(Function.getDateFromStr(fld.getKeijoymdL(), "yyyy/MM/dd"));
					apChangeLineFld.setAstNum(fld.getShisanNum());
					apChangeLineFld.setAstName(fld.getShisannm());
					apChangeLineFld.setAstClass(fld.getShuruicd());
					apChangeLineFld.setAstClassName(fld.getShuruinm());
					apChangeLineFld.setAstGetAmount(fld.getStkgkk());
					apChangeLineFld.setAstBookAmount(fld.getToBokak());
					apChangeLineFld.setCostType(Function.replaceCostTypePpfsToEa(fld.getCostType()));
					apChangeLineFld.setCostTypeName(fld.getCostTypeName());
					apChangeLineFld.setCostDepPrjCode(fld.getDepPrjCode());
					apChangeLineFld.setCostDepPrjName(fld.getDepPrjName());
					apChangeLineFld.setCostSectionCode(fld.getSoshiki2cd());
					apChangeLineFld.setCostSectionName(fld.getSoshiki2nm());
					apChangeLineFld.setItemGroupCode(fld.getItemGroupCode());
					apChangeLineFld.setItemGroupName(fld.getItemGroupName());
					apChangeLineFld.setHolRepOfficeCode(fld.getSetchicd());
					apChangeLineFld.setHolRepOfficeName(fld.getSetchinm());
					apChangeLineFld.setCostDistCode(fld.getSkkhihfcd());
					apChangeLineFld.setCostDistName(fld.getHfnm());
					apChangeLineFld.setHolCompanyCode(fld.getHolCompanyCode());
					apChangeLineFld.setHolSectionCode(fld.getHolSectionCode());
					apChangeLineFld.setHolSectionYear(fld.getHolSectionYear());
					apChangeLineFld.setHolStaffCode(fld.getHolStaffCode());
					apChangeLineFld.setHolSectionName(fld.getHolSectionName());

					apChangeLineFld.setPpTransFlag(Constants.FLAG_NO);

					if(Function.nvl(fld.getShisanknrkbn(), "").equals("1")) { // 仮勘定
						apChangeLineFld.setTempFlag(Constants.FLAG_YES);
					} else {
						apChangeLineFld.setTempFlag(Constants.FLAG_NO);
					}

					// 有形 or 無形
					if(Function.nvl(fld.getSkkshisankbn(), "").equals(Constants.PPFS_FLD_SKKSHISANKBN_TAN1)
						|| Function.nvl(fld.getSkkshisankbn(), "").equals(Constants.PPFS_FLD_SKKSHISANKBN_TAN2)) {
						apChangeLineFld.setApChangeLineFldType(Constants.AP_CHANGE_LINE_FLD_TYPE_TAN);
						apChangeLineFldTanList.add(apChangeLineFld);
					} else {
						apChangeLineFld.setApChangeLineFldType(Constants.AP_CHANGE_LINE_FLD_TYPE_INT);
						apChangeLineFldIntList.add(apChangeLineFld);
					}
				}
			}

			//	契約検索
			PpfsLldSC searchParamLld = new PpfsLldSC();
			if(assetOrLicense == 1) {
				searchParamLld.setAssetIdPlural(idPlural);
			} else {
				searchParamLld.setLicenseIdPlural(idPlural);
			}
			searchParamLld.setKykjotaikbnC("1 3"); // 契約状態区分:通常/物件中途解約済み
			searchParamLld.setShisanjotaikbnA("1"); // 契約状態区分:通常
			List<PpfsLldSR> lldList = lldSession.searchLld(loginStaffCode, Constants.ACCESS_LEVEL_ADMIN_SYSTEM, companyCode, searchParamLld);

			//	型変換
			if(lldList != null && lldList.size() > 0){
				for(int i = 0; i < lldList.size(); i++){
					PpfsLldSR lld = lldList.get(i);
					ApChangeLineContract apChangeLineContract = new ApChangeLineContract();

					apChangeLineContract.setContractCompanyCode(companyCode);
					apChangeLineContract.setContractNum(lld.getKyknoA());
					apChangeLineContract.setContractStartDate(Function.getDateFromStr(lld.getStymdLF(), "yyyy/MM/dd"));
					apChangeLineContract.setContractEndDate(Function.getDateFromStr(lld.getManryoymdLF(), "yyyy/MM/dd"));
					apChangeLineContract.setRemainAmount(lld.getMikeikalaryoPayA());
					apChangeLineContract.setMonthAmount(lld.getIkkailaryoA());
					apChangeLineContract.setCostType(Function.replaceCostTypePpfsToEa(lld.getNiniLd_7cdA()));
					apChangeLineContract.setCostTypeName(lld.getNiniLd_7nmA());
					apChangeLineContract.setCostDepPrjCode(lld.getNiniLd_6cdA());
					apChangeLineContract.setCostDepPrjName(lld.getNiniLd_6nmA());
					apChangeLineContract.setContractSubNum(lld.getNiniLd_17cdA());
					apChangeLineContract.setAstNum(lld.getShisanNumA());
					apChangeLineContract.setAstName(lld.getShisannmA());
//					apChangeLineContract.setRemainTerm(null); // 未使用
					apChangeLineContract.setAstClass(lld.getShuruicdA());
					apChangeLineContract.setAstClassName(lld.getShuruinmA());
					apChangeLineContract.setCostSectionCode(lld.getSoshiki2cdA());
					apChangeLineContract.setCostSectionName(lld.getSoshiki2nmA());
					apChangeLineContract.setItemGroupCode(lld.getNiniLd_3cdA());
					apChangeLineContract.setItemGroupName(lld.getNiniLd_3nmA());
					apChangeLineContract.setHolRepOfficeCode(lld.getSetchicdA());
					apChangeLineContract.setHolRepOfficeName(lld.getSetchinmA());
					apChangeLineContract.setCostDistCode(lld.getLaryohfcdA());
					apChangeLineContract.setCostDistName(lld.getLaryohfnmA());
					apChangeLineContract.setPpIdContract(lld.getKoyunoC());
					apChangeLineContract.setPpIdAst(lld.getKoyunoA());

					apChangeLineContract.setPpTransFlag(Constants.FLAG_NO);

					// リース or レンタル
					if(Function.nvl(lld.getLatorihikikbnC(), "").equals(Constants.PPFS_LLD_LATORIHIKIKBN_RENTAL)) {
						apChangeLineContract.setApChangeLineContractType(Constants.AP_CHANGE_LINE_CONTRACT_TYPE_RENTAL);
						apChangeLineContractRentalList.add(apChangeLineContract);
					} else {
						apChangeLineContract.setApChangeLineContractType(Constants.AP_CHANGE_LINE_CONTRACT_TYPE_LEASE);
						apChangeLineContractLeaseList.add(apChangeLineContract);
					}
				}
			}
		}

		// 配賦情報セット
		setFldCostSec(loginStaffCode, accessLevel, companyCode, apChangeLineFldTanList);
		setFldCostSec(loginStaffCode, accessLevel, companyCode, apChangeLineFldIntList);
		setContractCostSec(loginStaffCode, accessLevel, companyCode, apChangeLineContractLeaseList);
		setContractCostSec(loginStaffCode, accessLevel, companyCode, apChangeLineContractRentalList);

		// 戻り値設定
		apChange.setApChangeLineFldTan(apChangeLineFldTanList);
		apChange.setApChangeLineFldInt(apChangeLineFldIntList);
		apChange.setApChangeLineContractLease(apChangeLineContractLeaseList);
		apChange.setApChangeLineContractRental(apChangeLineContractRentalList);

		return apChange;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#setFldCostSec(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public List<ApChangeLineFld> setFldCostSec(String loginStaffCode, String accessLevel, String companyCode, List<ApChangeLineFld> fldList) {
		try {
			ApChangeDAO apChangeDAO = new ApChangeDAO();

			for(int i = 0; i < fldList.size(); i++) {
				ApChangeLineFld row = fldList.get(i);
				List<ApChangeLineCostSec> costSecList = null;
				if(!Function.nvl(row.getCostDistCode(), "").equals("")) { // 配賦有り
					costSecList = apChangeDAO.selectApChangeLineCostSecDist(companyCode, row.getCostDistCode());
				} else { // 配賦無
					costSecList = new ArrayList<ApChangeLineCostSec>();
					ApChangeLineCostSec cost = new ApChangeLineCostSec();
					cost.setCostSectionCode(row.getCostSectionCode());
					cost.setCostSectionName(row.getCostSectionName());
					cost.setCostDist(100d);
					costSecList.add(cost);
				}

				setCostSecDistName(row, costSecList); // 経費負担配分名称セット
				row.setApChangeLineCostSecList(costSecList); // 経費負担リストセット
			}
			return fldList;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "資産配賦情報セット"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApChangeSession#setContractCostSec(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public List<ApChangeLineContract> setContractCostSec(String loginStaffCode, String accessLevel, String companyCode, List<ApChangeLineContract> contractList) {
		try {
			ApChangeDAO apChangeDAO = new ApChangeDAO();

			for(int i = 0; i < contractList.size(); i++) {
				ApChangeLineContract row = contractList.get(i);
				List<ApChangeLineCostSec> costSecList = null;
				if(!Function.nvl(row.getCostDistCode(), "").equals("")) { // 配賦有り
					costSecList = apChangeDAO.selectApChangeLineCostSecDist(companyCode, row.getCostDistCode());
				} else { // 配賦無
					costSecList = new ArrayList<ApChangeLineCostSec>();
					ApChangeLineCostSec cost = new ApChangeLineCostSec();
					cost.setCostSectionCode(row.getCostSectionCode());
					cost.setCostSectionName(row.getCostSectionName());
					cost.setCostDist(100d);
					costSecList.add(cost);
				}
				setCostSecDistName(row, costSecList); // 経費負担配分名称セット
				row.setApChangeLineCostSecList(costSecList); // 経費負担リストセット
			}
			return contractList;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "物件配賦情報セット"), e);
		}
	}

}