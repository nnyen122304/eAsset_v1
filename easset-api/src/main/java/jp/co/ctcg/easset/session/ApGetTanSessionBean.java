/*===================================================================
 * ファイル名 : ApGetTanSessionBean.java
 * 概要説明   : 取得申請セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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

import org.apache.commons.lang3.StringUtils;

import jp.co.ctcg.easset.dao.ApGetTanDAO;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.LovDataEx;
import jp.co.ctcg.easset.dto.Project;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineAst;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineAtt;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineCostSec;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineLic;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLineOtrCost;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanLinePur;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanSC;
import jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanSR;
import jp.co.ctcg.easset.dto.asset.Asset;
import jp.co.ctcg.easset.flex_common.Logging;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvReaderRowHandler;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.ExcelExporter;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;
import jp.co.ctcg.easset.util.PdfExporter;
import jp.co.ctcg.easset.ws.ApGetService;
import jp.co.ctcg.easset.ws.ApGetServiceProxy;
import jp.co.ctcg.easset.ws.EAssetService;
import jp.co.ctcg.easset.ws.EAssetServiceProxy;

import org.apache.commons.beanutils.PropertyUtils;


@Stateless
public class ApGetTanSessionBean implements ApGetTanSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession;

	@EJB
	AssetSession assetSession;

	@EJB
	LicenseSession licenseSession;

	@EJB
	HistSession histSession;

	@EJB
	ApGetTanSession apGetTanSession;

	@EJB
	SendMailSession sendMailSession;

	private static final String ID_PREFIX = "AH";
	private static final String LINE_AST_SEQ_NAME = "NEA_AP_GET_TAN_LINE_AST_S";
	private static final String LINE_LIC_SEQ_NAME = "NEA_AP_GET_TAN_LINE_LIC_S";
	private static final String FILE_SAVE_ENTITY_NAME = "AP_GET_TAN";

	// 履歴作成用
	private static final String HIST_ENTITY_NAME = "AP_GET_TAN";
	private static final String HIST_OPERATION_CREATE = "新規作成";
	private static final String HIST_OPERATION_UPDATE = "更新";
	private static final String HIST_OPERATION_DELETE = "削除";
	private static final String HIST_OPERATION_APPLY = "申請";
	private static final String HIST_OPERATION_APPROVE_SUPERIOR = "受付完了";
	private static final String HIST_OPERATION_APPROVE = "承認";
	private static final String HIST_OPERATION_SENDBACK = "差戻し";
	private static final String HIST_OPERATION_REJECT = "却下";
	private static final String HIST_OPERATION_CANCEL_APPLY = "引戻し";
	private static final String HIST_OPERATION_RENTAL_ORDER = "レンタル発注";

	// メールテンプレート変数
	private static final String MAIL_TEMPLATE_VAR_HOL_SECTION = "\\$\\{保有部署名\\}";
	private static final String MAIL_TEMPLATE_VAR_RECEPT_NUMBER = "\\$\\{受付番号\\}";
	private static final String MAIL_TEMPLATE_VAR_ORDER_NUMBER = "\\$\\{注文番号\\}";
	private static final String MAIL_TEMPLATE_VAR_LE_ESTIMATE_NUMBER = "\\$\\{レンタル見積書番号\\}";
	private static final String MAIL_TEMPLATE_VAR_ORDER_TO = "\\$\\{注文書の宛先\\}";
	private static final String MAIL_TEMPLATE_VAR_APPLICATION_ID = "\\$\\{取得申請番号\\}";
	private static final String MAIL_TEMPLATE_VAR_HOL_STTAF = "\\$\\{資産管理担当者\\}";
	private static final String MAIL_TEMPLATE_VAR_AP_STAFF = "\\$\\{申請者\\}";
	private static final String MAIL_TEMPLATE_VAR_REO_DLV_STAFF = "\\$\\{納品担当者\\}";
	private static final String MAIL_TEMPLATE_VAR_REO_DLV_MAIN_STAFF = "\\$\\{主管部納品担当者\\}";
	private static final String MAIL_TEMPLATE_VAR_REO_DLV_MAIN_SECTION = "\\$\\{主管部納品担当部署名\\}";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#searchApGetTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanSC)
	 */
	public List<ApGetTanSR> searchApGetTan(String loginStaffCode, String accessLevel, ApGetTanSC searchParam) {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();
			return apGetTanDAO.selectApGetTanList(loginStaffCode, accessLevel, searchParam);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#createApGetTanCsv(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_tan.ApGetTanSC)
	 */
	public String createApGetTanCsv(String loginStaffCode, String accessLevel, ApGetTanSC searchParam, Boolean lineOutputFlag) {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = apGetTanDAO.createApGetTanListCsv(loginStaffCode, accessLevel, searchParam, lineOutputFlag);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_GET_TAN_SEARCH, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",lineOutputFlag:" + lineOutputFlag + "," + Function.toString(searchParam));
			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#getApGetTan(java.lang.String)
	 */
	public ApGetTan getApGetTan(String applicationId) {
		return getApGetTan(applicationId, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#getApGetTan(java.lang.String)
	 */
	public ApGetTan getApGetTan(Long eappId) {
		return getApGetTan(eappId, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#getApGetTan(java.lang.String, boolean)
	 */
	public ApGetTan getApGetTan(String applicationId, boolean lockFlag) {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();

			ApGetTan apGetTan = apGetTanDAO.selectApGetTan(applicationId, lockFlag); // ヘッダの取得
			if(apGetTan != null) { // データ有
				apGetTan.setApGetTanLinePurList(apGetTanDAO.selectApGetTanLinePur(applicationId)); // 購入明細取得
				apGetTan.setApGetTanLineOtrCostList(apGetTanDAO.selectApGetTanLineOtrCost(applicationId)); // その他費用明細取得
				apGetTan.setApGetTanLineAttList(apGetTanDAO.selectApGetTanLineAtt(applicationId)); // 添付明細取得
				apGetTan.setApGetTanLineCostSecList(apGetTanDAO.selectApGetTanLineCostSec(applicationId)); // 経費負担明細取得
				apGetTan.setApGetTanLineAstList(apGetTanDAO.selectApGetTanLineAst(applicationId)); // 資産(機器)明細取得
				apGetTan.setApGetTanLineLicList(apGetTanDAO.selectApGetTanLineLic(applicationId)); // ライセンス明細取得
			}

			return apGetTan;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請取得"), e);
		}
	}

	/**
	 * 申請情報取得
	 * @param eappId e申請書類ID
	 * @param lockFlag データロックフラグ true:データ取得と同時にロック、false:ロックを行わない
	 * @return
	 */
	private ApGetTan getApGetTan(Long eappId, boolean lockFlag) {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();

			ApGetTan apGetTan = apGetTanDAO.selectApGetTan(eappId, lockFlag); // ヘッダの取得
			if(apGetTan != null) {
				return getApGetTan(apGetTan.getApplicationId(), lockFlag);
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請取得"), e);
		}
	}

	/**
	 * 不正セット項目値の調整
	 * @param obj 情報機器等データ
	 * @throws AppException
	 */
	private void setPropertyAdjust(ApGetTan obj) throws AppException {
		// フラグがNULLの場合デフォルトセット
		if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) {
			obj.setSpecialApproveFlag(null);
			obj.setApSkipApproveFlag(null);
			obj.setGetNeedCioJudgeFlag(null);
			obj.setGetSystemSectionDeployFlag(null);
			obj.setGetIntraInventoryFlag(null);
		} else {
			if(obj.getSpecialApproveFlag() == null) obj.setSpecialApproveFlag(Constants.FLAG_NO);
			if(obj.getApSkipApproveFlag() == null) obj.setApSkipApproveFlag(Constants.FLAG_NO);
			if(obj.getGetNeedCioJudgeFlag() == null) obj.setGetNeedCioJudgeFlag(Constants.FLAG_NO);
			if(obj.getGetSystemSectionDeployFlag() == null) obj.setGetSystemSectionDeployFlag(Constants.FLAG_NO);
			if(obj.getGetIntraInventoryFlag() == null) obj.setGetIntraInventoryFlag(Constants.FLAG_NO);
//			if(obj.getCostDepPrjUndecidedFlag() == null) obj.setCostDepPrjUndecidedFlag(Constants.FLAG_NO);
			if(obj.getLineEditApproveFlag() == null) obj.setLineEditApproveFlag(Constants.FLAG_NO);
			if(obj.getStopRegistFlag() == null) obj.setStopRegistFlag(Constants.FLAG_NO);
		}

		// 稟議書・経営会議承認済チェックが付いていない場合は、承認日クリア
		if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {
			obj.setSpecialApproveDate(null);
		}

/*
		// 案件名未入力の場合はクラウド区分クリア
		if(Function.nvl(obj.getGetItemName(), "").equals("")) {
			obj.setGetItemCloudType(null);
			obj.setGetItemGroupCode(null);
			obj.setGetItemGroupName(null);
		}
*/

		// 借受・貸し出し商品・納入前承認以外は返却予定日クリア
		if(!Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_KARIUKE)
			&& !Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_RENTAL_GOODS)
			&& !Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_PRE_DELIVER_GOODS)) {
			obj.setGetReturnDate(null);
		}

		// リース・レンタル以外の場合はリースレンタル関連項目クリア
		if(!Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_LEASE)
			&& !Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_RENTAL)) {
			obj.setGetLeReCompanyCode(null);
			obj.setGetLeReCompanyName(null);
			obj.setGetLeReEstimateNumber(null);
			obj.setGetLeReMonthAmount(null);
			obj.setGetLeReCount(null);
			obj.setGetLeReTotalAmount(null);
			obj.setGetLeReComment(null);
		}

		CodeName leReCompany = new CodeName();
		CodeName reoTypeCodeName = new CodeName();

		// レンタルの場合
		if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_RENTAL)){
			leReCompany = masterSession.getCodeName(Constants.CATEGORY_CODE_LEASE_RENTAL_CUSTOMER, obj.getGetLeReCompanyCode(), obj.getApCompanyCode(), null);

			//	レンタル注文書ではない場合
			if(leReCompany != null && (Function.nvl(leReCompany.getValue10(),"").equals(Constants.FLAG_NO) || Function.nvl(leReCompany.getValue10(),"").equals(""))
			){
				obj.setReoOrderType(null);	//	発注区分クリア
			}
			else{
				//	注文書区分の各種設定を取得
				reoTypeCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_REO_ORDER_SET, obj.getReoOrderType(), obj.getApCompanyCode(), null);
			}

			//	レンタルで注文書を使用しない場合、注文書項目のクリア
			if(Function.nvl(obj.getReoOrderType(), "").equals("")
			|| Function.nvl(reoTypeCodeName.getCategoryCode(), "").equals(Constants.CATEGORY_CODE_REO_ORDER_SET)
			){
				if(Function.nvl(obj.getReoOrderType(), "").equals("")
				|| Function.nvl(reoTypeCodeName.getValue11(), "").equals(Constants.LE_PO_TYPE_NO)){
					obj.setReoOrderFlag(null);
				}
				if(Function.nvl(obj.getReoOrderType(), "").equals("")
				|| Function.nvl(reoTypeCodeName.getValue10(), "").equals(Constants.FLAG_NO)){
					obj.setReoDisuseFlag(null);
					//	資産管理担当者
					obj.setReoAstStaffCode(null);
					obj.setReoAstCompanyCode(null);
					obj.setReoAstSectionCode(null);
					obj.setReoAstSectionYear(null);
					obj.setReoAstTel(null);
					obj.setReoAstFax(null);
					obj.setReoAstMailAddress(null);
					//	使用希望開始日
					obj.setReoUseHopeStartDate(null);
					//	請求書送付先
					obj.setReoInvStaffInputType(null);
					obj.setReoInvStaffName(null);
					obj.setReoInvCompanyName(null);
					obj.setReoInvSectionName(null);
					obj.setReoInvAddress(null);
					obj.setReoInvTel(null);
					obj.setReoInvFax(null);
					obj.setReoInvMailAddress(null);
					obj.setReoInvCompanyOfficailName(null);
					//	納品先
					obj.setReoDlvStaffCode(null);
					obj.setReoDlvCompanyCode(null);
					obj.setReoDlvSectionCode(null);
					obj.setReoDlvSectionYear(null);
					obj.setReoDlvAddress(null);
					obj.setReoDlvTel(null);
					obj.setReoDlvFax(null);
					obj.setReoDlvMailAddress(null);
					//	備考
					obj.setReoDescription(null);
					//	受注日
					obj.setReoOrderDate(null);
					// 現機種OIR番号
					obj.setReoAstOir(null);
				}
				//	資産(機器)明細が見積書参照でない場合、
				if(Function.nvl(obj.getReoOrderType(), "").equals("")
						|| Function.nvl(reoTypeCodeName.getValue7(), "").equals(Constants.FLAG_NO)){
					obj.setGetLeReDateCount(null);
					obj.setGetLeReMonthLessAmount(null);
				}
				if(!Function.nvl(obj.getReoOrderType(), "").equals("")
				){
					if(Function.nvl(obj.getReoDisuseFlag(), "").equals("")) obj.setReoDisuseFlag(Constants.FLAG_NO);	//	発注不要ではない場合、「0」を設定
				}
			}
		}

		//	レンタル以外の場合、発注項目クリア
		if(!Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_RENTAL)){
			obj.setReoOrderType(null);
			obj.setReoOrderFlag(null);
			//	資産管理担当者
			obj.setReoAstStaffCode(null);
			obj.setReoAstCompanyCode(null);
			obj.setReoAstSectionCode(null);
			obj.setReoAstSectionYear(null);
			obj.setReoAstTel(null);
			obj.setReoAstFax(null);
			obj.setReoAstMailAddress(null);
			//	使用希望開始日
			obj.setReoUseHopeStartDate(null);
			//	請求書送付先
			obj.setReoInvStaffInputType(null);
			obj.setReoInvStaffName(null);
			obj.setReoInvCompanyName(null);
			obj.setReoInvSectionName(null);
			obj.setReoInvAddress(null);
			obj.setReoInvTel(null);
			obj.setReoInvFax(null);
			obj.setReoInvMailAddress(null);
			obj.setReoInvCompanyOfficailName(null);
			//	納品先
			obj.setReoDlvStaffCode(null);
			obj.setReoDlvCompanyCode(null);
			obj.setReoDlvSectionCode(null);
			obj.setReoDlvSectionYear(null);
			obj.setReoDlvAddress(null);
			obj.setReoDlvTel(null);
			obj.setReoDlvFax(null);
			obj.setReoDlvMailAddress(null);
			//	備考
			obj.setReoDescription(null);
			//	受注日
			obj.setReoOrderDate(null);
			obj.setGetLeReDateCount(null);
			obj.setGetLeReMonthLessAmount(null);
			obj.setReoDisuseFlag(null);
			// 現機種OIR番号
			obj.setReoAstOir(null);
		}

		// リース以外の場合はリース見積依頼書e申請IDクリア
		if(!Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_LEASE)) {
			obj.setGetLeEappId(null);
		}

		// 設置場所が社外以外の場合社外常設PC申請書番号クリア
		String holOfficeCode = Function.nvl(obj.getHolOfficeCode(), "");
		if(!holOfficeCode.equals("")) {
			CodeName office = masterSession.getCodeName(Constants.CATEGORY_CODE_OFFICE, holOfficeCode, null, null);

			if(office != null && !Function.nvl(office.getValue5(), "").equals(Constants.FLAG_YES)) {
				obj.setHolOfficeOutsidePcId(null);
			}
		} else {
			obj.setHolOfficeOutsidePcId(null);
		}

		// 取得形態・金額に応じた経費負担情報のクリア
		String apGetAmountRangeCode = Function.nvl(obj.getApGetAmountRange(), "");
		if(!apGetAmountRangeCode.equals("")) {
			CodeName apGetAmountRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_GET_AMOUNT_RANGE, obj.getApGetAmountRange(), obj.getApCompanyCode(), null);
			if(apGetAmountRange != null) {
				if(Function.nvl(apGetAmountRange.getValue7(), "").equals("0")) { // 未使用
					obj.setCostType(null);
					obj.setCostDepPrjCode(null);
					obj.setCostDepPrjName(null);
					obj.setCostDepPrjUndecidedFlag(null);
					obj.setApGetTanLineCostSecList(new ArrayList<ApGetTanLineCostSec>());
				} else if(Function.nvl(apGetAmountRange.getValue7(), "").equals("2")) { // 部課のみ使用
					obj.setCostType(null);
					obj.setCostDepPrjCode(null);
					obj.setCostDepPrjName(null);
					obj.setCostDepPrjUndecidedFlag(null);
				} else if(Function.nvl(apGetAmountRange.getValue7(), "").equals("3")) { // 販管/原価区分のみ使用
					obj.setApGetTanLineCostSecList(new ArrayList<ApGetTanLineCostSec>());
				}
			}

			// 原価以外はプロジェクト項目クリア
			if(!Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)) {
				obj.setCostDepPrjCode(null);
				obj.setCostDepPrjName(null);
				obj.setCostDepPrjUndecidedFlag(null);
			}
			// プロジェクトコード未定の場合はプロジェクトコードクリア
			if(Function.nvl(obj.getCostDepPrjUndecidedFlag(), "").equals(Constants.FLAG_YES)) {
				obj.setCostDepPrjCode(null);
				obj.setCostDepPrjName(null);
			}
		} else {
			obj.setCostType(null);
			obj.setCostDepPrjCode(null);
			obj.setCostDepPrjName(null);
			obj.setCostDepPrjUndecidedFlag(null);
			obj.setApGetTanLineCostSecList(new ArrayList<ApGetTanLineCostSec>());
		}

		// 故障交換以外の場合、取得形態・金額に応じた明細情報のクリア
		if(!Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) {
			boolean clearAst = false;
			boolean clearLic = false;

			if(!apGetAmountRangeCode.equals("")) {
				CodeName apGetAmountRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_GET_AMOUNT_RANGE, obj.getApGetAmountRange(), obj.getApCompanyCode(), null);
				if(apGetAmountRange != null) {
					if(Function.nvl(apGetAmountRange.getValue10(), "").equals("0")) { // 未使用
						clearAst = true;
						clearLic = true;
					} else if(Function.nvl(apGetAmountRange.getValue10(), "").equals("2")) { // 資産(機器)明細のみ使用
						clearLic = true;
					} else if(Function.nvl(apGetAmountRange.getValue10(), "").equals("3")) { // ライセンス明細のみ使用
						clearAst = true;
					}

					List<ApGetTanLineAst> list = obj.getApGetTanLineAstList();
					if(list != null && list.size() > 0) {
						for(int i = 0; i < list.size(); i++) {
							ApGetTanLineAst row = list.get(i);
							// 固定資産(購入&機器・ライセンス両明細使用)以外の場合は資産プロジェクト情報クリア
							if(!(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_TAN) && Function.nvl(apGetAmountRange.getValue10(), "").equals("1"))){
								row.setAstPrjCode(null);
								row.setAstPrjName(null);
								row.setAstPrjType(null);
							}
							//	レンタル注文書項目使用しない
							if(Function.nvl(obj.getReoOrderType(), "").equals("")
							|| Function.nvl(reoTypeCodeName.getCategoryCode(), "").equals(Constants.CATEGORY_CODE_REO_ORDER_SET)
							){
								if(Function.nvl(obj.getReoOrderType(), "").equals("")
								|| Function.nvl(reoTypeCodeName.getValue10(), "").equals(Constants.FLAG_NO)){
									row.setAstModelCode(null);
									row.setAstGetLeMonthAmount(null);
									row.setReoAstName(null);
									row.setReoAgreement(null);
									row.setReoPriceList(null);
									row.setAstGetLeContractMonth(null);
								}
							}
						}
					}
				}
			} else {
				clearAst = true;
				clearLic = true;
			}

			StringBuffer errMsg = new StringBuffer();
			if(clearAst) { // 資産(機器)明細クリア
				boolean clearOk = true;

				// 登録済みか同かをチェック
				List<ApGetTanLineAst> list = obj.getApGetTanLineAstList();
				if(list != null && list.size() > 0) {
					for(int i = 0; i < list.size(); i++) {
						ApGetTanLineAst row = list.get(i);
						if(Function.nvl(row.getRegistQuantity(), Integer.valueOf(0)).longValue() > 0) {
							clearOk = false;
							break;
						}
					}
				}

				if(clearOk) { // 登録無しならば明細クリア
					obj.setApGetTanLineAstList(new ArrayList<ApGetTanLineAst>());
				} else { // 登録有りならば明細クリア不可
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "取得形態・取得金額範囲の変更により登録済の資産(機器)明細がクリアされてしまうので、取得形態・取得金額範囲を変更できません。"));
				}
			}
			if(clearLic) { // ライセンス明細クリア
				boolean clearOk = true;

				// 登録済みか同かをチェック
				List<ApGetTanLineLic> list = obj.getApGetTanLineLicList();
				if(list != null && list.size() > 0) {
					for(int i = 0; i < list.size(); i++) {
						ApGetTanLineLic row = list.get(i);
						if(Function.nvl(row.getRegistQuantity(), Integer.valueOf(0)).longValue() > 0) {
							clearOk = false;
							break;
						}
					}
				}

				if(clearOk) { // 登録無しならば明細クリア
					obj.setApGetTanLineLicList(new ArrayList<ApGetTanLineLic>());
				} else { // 登録有りならば明細クリア不可
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "取得形態・取得金額範囲の変更により登録済のライセンス明細がクリアされてしまうので、取得形態・取得金額範囲を変更できません。"));
				}
			}
			if(errMsg.length() > 0) {
				throw new AppException(errMsg.toString());
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#createApGetTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan)
	 */
	public String createApGetTan(String loginStaffCode, String accessLevel, ApGetTan obj) throws AppException {
		return createApGetTan(loginStaffCode, accessLevel, obj, true);
	}

	/**
	 * 取得申請作成本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @throws AppException バリデーションエラー時に発生
	 * @return 作成したデータの取得申請書番号
	 */
	private String createApGetTan(String loginStaffCode, String accessLevel, ApGetTan obj, boolean isHistCreate) throws AppException {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();

			//////////////////////////////////// 固定値セット
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			obj.setCreateDate(sysdate);
			obj.setCreateStaffCode(loginStaffCode);
			obj.setUpdateDate(sysdate);
			obj.setUpdateStaffCode(loginStaffCode);

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			// 受付承認フラグ
			obj.setApproveSuperiorFlag(Constants.FLAG_NO);

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			errMsg.append(validateApGetTan(loginStaffCode, accessLevel, obj));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 登録完了フラグセット
			obj.setRegistCompleteFlag(this.getRegistCompleteFlag(obj.getApGetTanLineAstList(), obj.getApGetTanLineLicList()));

			//////////////////////////////////// IDの採番
			String applicationId = masterSession.nextValId(ID_PREFIX);

			//////////////////////////////////// データ作成
			// バージョン・改訂番号
			obj.setVersion(1);
			obj.setDisplayVersion(1);

			obj.setApplicationId(applicationId); // IDセット
			apGetTanDAO.insertApGetTan(obj); // ヘッダ作成
			createLine(loginStaffCode, obj, apGetTanDAO, applicationId); // 明細データ作成

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				histSession.createHistData(HIST_ENTITY_NAME, applicationId, HIST_OPERATION_CREATE, null);
			}

			//////////////////////////////////// 自動登録処理
			autoRegistApGetTanLineAst(loginStaffCode, accessLevel, obj);

			// 承認完了報告メール
			sendApproveMail(applicationId, null);

			return obj.getApplicationId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請作成:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#updateApGetTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan)
	 */
	public void updateApGetTan(String loginStaffCode, String accessLevel, ApGetTan obj) throws AppException {

		// 更新前の申請ステータス取得
		String oldApStatus = null;
		ApGetTan oldApGetTan = getApGetTan(obj.getApplicationId(), false);
		if(oldApGetTan != null) {
			oldApStatus = oldApGetTan.getApStatus();
		}

		// 更新コメント
		obj.setUpdateComment(null);

		updateApGetTan(loginStaffCode, accessLevel, obj, true, true, true, null);

		//////////////////////////////////// 自動登録処理
		autoRegistApGetTanLineAst(loginStaffCode, accessLevel, obj);

		// 承認完了報告メール
		sendApproveMail(obj.getApplicationId(), oldApStatus);
	}

	/**
	 * 取得申請更新本体
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj 取得申請データ
	 * @param isLineUpdate 明細更新有無 true:明細も更新(delete insert) false:ヘッダのみ更新
	 * @param isDisplayVersionUpdate 改訂番号更新有無 true:改訂番号をインクリメント false:改訂番号は更新しない
	 * @param isHistCreate 履歴作成有無 true:作成、false:未作成
	 * @param operation 履歴作成時のオペレーション
	 * @throws AppException
	 */
	private void updateApGetTan(String loginStaffCode, String accessLevel, ApGetTan obj, boolean isLineUpdate, boolean isDisplayVersionUpdate, boolean isHistCreate, String operation) throws AppException {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();
			ApGetTan apGetTanOld = getApGetTan(obj.getApplicationId(), true); // 現データの取得(データロック)

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
			if(obj.getVersion().intValue() != apGetTanOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// バリデーション(登録画面更新の際のみ：連携等による更新時は行わない)
			if(isLineUpdate) errMsg.append(validateApGetTan(loginStaffCode, accessLevel, obj));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 登録完了フラグセット
			obj.setRegistCompleteFlag(this.getRegistCompleteFlag(obj.getApGetTanLineAstList(), obj.getApGetTanLineLicList()));

			//////////////////////////////////// データ更新
			// バージョン・改訂番号
			obj.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
			if(isDisplayVersionUpdate) obj.setDisplayVersion(Function.nvl(obj.getDisplayVersion(), Integer.valueOf(1)) + 1);

			apGetTanDAO.updateApGetTan(obj);

			if(isLineUpdate) {
				// 明細を一度削除
				deleteLine(loginStaffCode, obj, apGetTanDAO);

				createLine(loginStaffCode, obj, apGetTanDAO, obj.getApplicationId()); // 明細データ作成
			}

			//////////////////////////////////// 履歴作成
			if(isHistCreate) {
				StringBuffer lineChangeColumnName = new StringBuffer();
				if(isLineUpdate) {
					// 明細変更確認
					if(Function.isListChange(obj.getApGetTanLinePurList(), apGetTanOld.getApGetTanLinePurList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("購入先/購入金額明細");
					}
					if(Function.isListChange(obj.getApGetTanLineOtrCostList(), apGetTanOld.getApGetTanLineOtrCostList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("その他費用明細");
					}
					if(Function.isListChange(obj.getApGetTanLineAttList(), apGetTanOld.getApGetTanLineAttList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("添付明細");
					}
					if(Function.isListChange(obj.getApGetTanLineCostSecList(), apGetTanOld.getApGetTanLineCostSecList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("経費負担部署明細");
					}
					if(Function.isListChange(obj.getApGetTanLineAstList(), apGetTanOld.getApGetTanLineAstList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("資産(機器)明細");
					}
					if(Function.isListChange(obj.getApGetTanLineLicList(), apGetTanOld.getApGetTanLineLicList())) {
						if(lineChangeColumnName.length() > 0) lineChangeColumnName.append(",");
						lineChangeColumnName.append("ライセンス明細");
					}
				}

				histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), (operation == null ? HIST_OPERATION_UPDATE : operation), lineChangeColumnName.toString());
			}
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請更新:ファイル保存"), e);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#applyApGetTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan)
	 */
	public String applyApGetTan(String loginStaffCode, String accessLevel, ApGetTan obj) throws AppException {
		String ret = null;
		boolean isNew = Function.nvl(obj.getApplicationId(), "").equals(""); // 新規の場合true

		//////////////////////////////////// 新規 or 更新呼び出し
		if(isNew) { // 新規
			ret = createApGetTan(loginStaffCode, accessLevel, obj, false);
		} else { // 更新
			// 受付承認フラグ
			obj.setApproveSuperiorFlag(Constants.FLAG_NO);
			// 更新コメント
			obj.setUpdateComment(null);

			ret = obj.getApplicationId();

			// 不正セット項目値の調整
			setPropertyAdjust(obj);

			// ステータス更新前バリデーション
			String errMsg = validateApGetTan(loginStaffCode, accessLevel, obj);
			if(errMsg.length() > 0) throw new AppException(errMsg);
		}

		//////////////////////////////////// ステータス更新&ステータス更新後バリデーション
		obj.setApStatus(Constants.AP_STATUS_GET_TAN_APPLY);
		String errMsg = validateApGetTan(loginStaffCode, accessLevel, obj);
		if(errMsg.length() > 0) throw new AppException(errMsg);

		//////////////////////////////////// 申請
		Long eappId = null;

//		if(!Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) { // 故障交換以外はe申請連携
		eappId = callEappService(obj); // e申請連携
//		}
		// e申請IDを更新
		obj.setEappId(eappId);

		if(isNew) { // 新規
			updateApGetTan(loginStaffCode, accessLevel, obj, false, false, false, null);
			histSession.createHistData(HIST_ENTITY_NAME, ret, HIST_OPERATION_APPLY, null); // 履歴作成
		} else {
			updateApGetTan(loginStaffCode, accessLevel, obj, true, true, true, HIST_OPERATION_APPLY);
		}

		//////////////////////////////////// 自動承認(故障交換でe申請回付無しの場合)
		if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE) && eappId == null) { // 故障交換の場合は自動承認
			obj.setApStatus(Constants.AP_STATUS_GET_TAN_APPROVE);
			updateApGetTan(Constants.STAFF_CODE_SYSTEM, accessLevel, obj, false, false, true, HIST_OPERATION_APPROVE);
		}

		return ret;
	}

	/**
	 * e申請サービス呼び出し
	 * @param obj 申請データ
	 * @return e申請ID
	 * @throws AppException
	 */
	private Long callEappService(ApGetTan obj) throws AppException {
		// e申請WebServiceエンドポイント取得
		CodeName codeNameUrl = masterSession.getCodeName(Constants.CATEGORY_CODE_WS_URL_EAPP, Constants.INTERNAL_CODE_WS_URL_AP_GET_TAN, null, null);
		String eappWsEndpoint = codeNameUrl.getValue1(); // e申請WebSerivceエンドポイント
		String eAssetUrl = codeNameUrl.getValue2(); // e申請インラインフレーム画面表示用のeAssetUrl
		String eappStopMessage = codeNameUrl.getValue3(); // e申請との連携停止期間中のエラーメッセージ

		Long eappId = null;
		List<ApGetTanLineAst> astList = obj.getApGetTanLineAstList();
		// List<ApGetTanLineLic> licList = obj.getApGetTanLineLicList();

		if(!Function.nvl(eappWsEndpoint, "").equals("")) { // e申請WebServiceエンドポイントが空白(PG検証用)の場合は連携無し
			try {
				eAssetUrl += "&amp;companyCode=" + obj.getApCompanyCode();
				eAssetUrl += "&amp;param2="; // e申請から書類IDが指定される

				// 金額範囲マスタ取得
				CodeName codeNameRange;

				if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) {
					// 故障交換は固定の金額範囲データ作成
					codeNameRange = new CodeName();
					codeNameRange.setValue4(Constants.AP_TITLE_GET_TAN_REPLACE);
					codeNameRange.setValue8(Constants.EAPP_ROUTE_AUTH_AP_GET_TAN_REPLACE);
				} else {
					// 故障交換以外は金額範囲データ取得
					codeNameRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_GET_AMOUNT_RANGE, obj.getApGetAmountRange(), obj.getApCompanyCode(), null);
				}

				// タイトルセット
				String title = codeNameRange.getValue4();
				if(codeNameRange.getValue5() != null) {
					title = title + "\\n" + codeNameRange.getValue5();
				} else {
					title = "\\n" + title;
				}

				// サブタイトル(金額)セット
				String subTitle = Function.nvl(obj.getApGetAmountRangeName(), "");
				if(codeNameRange.getValue6() != null) subTitle = codeNameRange.getValue6() + " " + subTitle;
				if(!Function.nvl(subTitle, "").equals("")) subTitle = "(" + subTitle + ")";

				//////////////////////////////////	タイトルの付加情報セット
				String dffTitle = "";
				//	メーカー
				String maker = "";
				String manageTypeName = "";
				String failureAssetId = "";
				String oir = "";
				Integer quantity = 0;
				boolean isCpuFlag = false;	//	全体の明細がCPUか判定フラグ
				if(astList != null && astList.size() > 0){
					for(int i = 0; i < astList.size(); i++) {
						ApGetTanLineAst row = astList.get(i);
						boolean isCpuLineFlag = false;	//	1つの明細がCPUか判定フラグ
						//	資産機器分類:大分類 CPU本体明細？
						CodeName codeNameAssetCategory1 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY1, row.getAstCategory1Code(), null, null);
						if(codeNameAssetCategory1.getValue2().equals("1")){
							//	付加情報リセット
							if( !isCpuFlag ){
								manageTypeName = "";
								maker = "";
								quantity = 0;
								failureAssetId = "";
								oir = "";
							}
							isCpuLineFlag = true;
							isCpuFlag = true;
						}

						if( isCpuLineFlag	//	CPU明細？
						|| (!isCpuFlag && !isCpuLineFlag)	//	CPU明細が存在しない？
						){
							//	情シス配備判別
							//	故障交換?
							if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) {
								Asset failureAsset = assetSession.getAsset(row.getFailureAssetId(), false);
								if(!Function.nvl(failureAsset.getAstManageType(), "").equals("")) {
									CodeName codeNameAssetManageType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, failureAsset.getAstManageType(), null, null);
//									情報機器等の”情シス配備”にチェックが付いていて且つ、移動対象機器の管理区分：情シス配備制御が「持ち出しPC」、「シンクライアント」の場合
									if(
										(codeNameAssetManageType.getValue2().equals(Constants.ASSET_MANAGE_TYPE_VALUE2_TAKEN_PC) && Function.nvl(failureAsset.getAstSystemSectionDeployFlag(), "").equals("1"))
									|| (codeNameAssetManageType.getValue2().equals(Constants.ASSET_MANAGE_TYPE_VALUE2_THINCLIENT) && Function.nvl(failureAsset.getAstSystemSectionDeployFlag(), "").equals("1") )
									){
										manageTypeName = Constants.FLAG_YES;
									}
									//	情シス配備以外?
									else if(!Function.nvl(manageTypeName, "").equals(Constants.FLAG_YES)){
										manageTypeName =  Constants.FLAG_NO;
									}
								}
							}
							//	故障交換以外
							else{
								if(!Function.nvl(row.getAstCategory2Code(), "").equals("")) {
									CodeName codeNameAstCategory2 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, row.getAstCategory2Code(), null, null);
									//	取得申請の”情シス配備”にチェックが付いていて且つ、明細に資産(機器)小分類のシンクライアント区分がシンクライアントの機器が含まれる場合
									if(
										(Function.nvl(codeNameAstCategory2.getValue3(), "").equals(Constants.ASSET_MANAGE_TYPE_VALUE2_THINCLIENT) && Function.nvl(obj.getGetSystemSectionDeployFlag(), "").equals("1"))
									) {
										manageTypeName = Constants.FLAG_YES;
									}
									//	それ以外
									else if(!Function.nvl(manageTypeName, "").equals(Constants.FLAG_YES)){
										manageTypeName =  Constants.FLAG_NO;
									}
								}
							}
							//	メーカー
							if(!Function.nvl(row.getAstMakerName(), "").equals("")){
								if( !maker.equals("") && !maker.equals(row.getAstMakerName()) ){
									maker = "混在";
								}
								else{
									maker = row.getAstMakerName();
								}
							}
							//	情報機器等管理番号
							if(!Function.nvl(row.getFailureAssetId(), "").equals("")){
								if( !failureAssetId.equals("") && !failureAssetId.equals(row.getFailureAssetId()) ){
									failureAssetId = "複数";
								}
								else{
									failureAssetId = row.getFailureAssetId();
								}
							}
							//	OIR
							if(!Function.nvl(row.getFailureAstOir(), "").equals("")){
								if( !oir.equals("") && !oir.equals(row.getFailureAstOir()) ){
									oir = "複数";
								}
								else{
									oir = row.getFailureAstOir();
								}
							}
							//	数量
							if(!Function.nvl(row.getQuantity(), "").equals("")){
								quantity = row.getQuantity() + quantity;
							}
						}
					}
					// プロパティフォーマット
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
					//	故障交換？
					if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) {
						//	[情シス配備判別] ／ [メーカー] ／ [情報機器等 ] ／ [OIR]
						dffTitle = ( Function.nvl(manageTypeName, "").equals(Constants.FLAG_YES) ? "情シス配備" : "-") + "／" + Function.nvl(maker, "") + "／"  + Function.nvl(failureAssetId, "")  + "／"  + Function.nvl(oir, "");
					}
					//	それ以外
					else{
						//	[情シス配備判別] ／ [メーカー] ／ [台数] ／ [納入予定日] ／ [オフィス] ／ [リース・レンタル会社]
						dffTitle = ( Function.nvl(manageTypeName, "").equals(Constants.FLAG_YES) ? "情シス配備" : "-") + "／" + Function.nvl(maker, "") + "／" + quantity + "／" + Function.nvl(dateFormat.format(obj.getGetDeliveryDate()), "") + "／" + Function.nvl(obj.getHolOfficeName(), "") + "／" + Function.nvl(obj.getGetLeReCompanyName(), "");
					}
				}

				//	e申請件名
				String apListTitle = "";
				//	故障交換？
				if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) {
					//	[タイトル] + [会社名] + [サブタイトル] + [付加情報]
					apListTitle =  title.replaceAll("\\\\n", "") + obj.getApCompanyName() + " " + subTitle + " " + dffTitle;
				}
				//	それ以外
				else{
					//	[会社名] + [タイトル] + [サブタイトル] + [付加情報]
					apListTitle =  obj.getApCompanyName() + title.replaceAll("\\\\n", " ") + " " + subTitle + " " + dffTitle;
				}

				//////////////////////////////////// 経路設定
				// e申請経路担当情報取得
				List<CodeName> codeNameEappChargeList = masterSession.getCodeNameList(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_GET_TAN, null, obj.getApCompanyCode(), null);
				CodeName codeNameEappCharge = codeNameEappChargeList.get(0);
				CodeName codeNameEappCharge2 = null;
				if(codeNameEappChargeList.size() > 1) codeNameEappCharge2 = codeNameEappChargeList.get(1);

				// e申請経路権限情報取得
				List<String> attributeParam = new ArrayList<String>();
				String eappRouteKey = Function.nvl(codeNameRange.getValue8(), "XXXXXXXXXXXX");
				attributeParam.add(eappRouteKey);
				CodeName codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_TAN, null, obj.getApCompanyCode(), attributeParam);

				if(codeNameEappRoute == null) codeNameEappRoute = new CodeName();

				// 特殊経路判定
				// 稟議書経営会議承認済
				if(Function.nvl(obj.getSpecialApproveFlag(), "").equals(Constants.FLAG_YES)) {
					attributeParam.clear();
					attributeParam.add(eappRouteKey.substring(0, 2) + Constants.EAPP_ROUTE_AUTH_AP_GET_TAN_SPECIAL_APPROVE_SUFFIX);
					CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_TAN, null, obj.getApCompanyCode(), attributeParam);
					if(cn != null) codeNameEappRoute = cn; // 経営会議承認済用の経路に置き換え
				}

				 // 要CIO審査
				if(Function.nvl(codeNameEappRoute.getValue1(), "").endsWith("-1") && Function.nvl(obj.getGetNeedCioJudgeFlag(), "").equals(Constants.FLAG_YES)) {
					eappRouteKey = codeNameEappRoute.getValue1().replaceAll("-1$", "-2"); // 経路をCIO審査込みの経路に変更
					attributeParam.clear();
					attributeParam.add(eappRouteKey);
					codeNameEappRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_TAN, null, obj.getApCompanyCode(), attributeParam);

					// 申請部署が情報システム部門かどうかを判別
					ArrayList<String> param = new ArrayList<String>();
					param.add(null); // value1
					param.add(Function.nvl(obj.getApSectionYear(), -1).toString()); // value2
					param.add(null); // value3
					param.add(Function.getHigherSectionCode(Function.nvl(obj.getApSectionCode(), "XXXXXX"))); // value4
					param.add("1"); // value5
					CodeName systemSection = masterSession.getCodeName(Constants.CATEGORY_CODE_LONG_TIME_SECTION, null, obj.getApCompanyCode(), param);

					// 情報システム部門の場合、情シス担当役員 = 担当役員のため
					// 担当役員承認が必要な場合の審査ステップを一つ除外
					if(systemSection != null) {
						codeNameEappRoute.setValue17(null);
					}
				}

				// 情シス配備で資産(機器)明細にシンクライアントが含まれる場合の経路を追加
				boolean isThinClient = false;
				if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) {
					// 故障交換
					for(int i = 0; i < astList.size(); i++) {
						ApGetTanLineAst row = astList.get(i);
						Asset failureAsset = assetSession.getAsset(row.getFailureAssetId(), false);
						if(!Function.nvl(failureAsset.getAstManageType(), "").equals("")) {
							CodeName codeNameAssetManageType = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_MANAGE_TYPE, failureAsset.getAstManageType(), null, null);
							// 情シス配備のシンクライアント
							if(Function.nvl(failureAsset.getAstSystemSectionDeployFlag(), "").equals(Constants.FLAG_YES) && Function.nvl(codeNameAssetManageType.getValue2(), "").equals(Constants.ASSET_MANAGE_TYPE_VALUE2_THINCLIENT)) {
								isThinClient = true;
								break;
							}
						}
					}

				} else {
					// 故障交換以外
					if(Function.nvl(obj.getGetSystemSectionDeployFlag(), "").equals(Constants.FLAG_YES) && astList != null) {
						for(int i = 0; i < astList.size(); i++) {
							ApGetTanLineAst row = astList.get(i);
							if(!Function.nvl(row.getAstCategory2Code(), "").equals("")) {
								CodeName codeNameAstCategory2 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, row.getAstCategory2Code(), null, null);
								if(Function.nvl(codeNameAstCategory2.getValue3(), "").equals(Constants.FLAG_YES)) {
									isThinClient = true;
									break;
								}
							}
						}
					}
				}

				if(isThinClient) { // 情シス配備シンクライアント
					attributeParam.clear();
					attributeParam.add(Constants.EAPP_ROUTE_AUTH_AP_GET_TAN_THIN_CLIENT);
					CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_TAN, null, obj.getApCompanyCode(), attributeParam);
					if(cn != null) {
						for(int j = 0; j < Constants.MAX_EAPP_ROUTE_COUNT_AP_GET_TAN; j++) {
							Object val = PropertyUtils.getProperty(cn, "value" + (j + 3)); // 申請経路はvalue3～
							if(val != null){
								//	e申請経路削除？
								if(val.equals(Constants.EAPP_ROUTE_DELETE)){
									PropertyUtils.setProperty(codeNameEappRoute, "value" + (j + 3), null);
								}
								else{
									PropertyUtils.setProperty(codeNameEappRoute, "value" + (j + 3), val);
								}
							}
						}
					}
				}
				// 減価償却プロジェクトコード入力有の場合の経路を追加
				if(!Function.nvl(obj.getCostDepPrjCode(), "").equals("")) {
					attributeParam.clear();
					attributeParam.add(Constants.EAPP_ROUTE_AUTH_AP_GET_TAN_PROJECT);
					CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_AUTH_AP_GET_TAN, null, obj.getApCompanyCode(), attributeParam);
					if(cn != null) {
						for(int i = 0; i < Constants.MAX_EAPP_ROUTE_COUNT_AP_GET_TAN; i++) {
							Object val = PropertyUtils.getProperty(cn, "value" + (i + 3)); // 申請経路はvalue3～
							if(val != null) PropertyUtils.setProperty(codeNameEappRoute, "value" + (i + 3), val);
						}
					}
				}

				// 課長承認不要
				if(Function.nvl(obj.getApSkipApproveFlag(), "").equals(Constants.FLAG_YES)) {
					//	CTCBS?
					if(Function.nvl(obj.getApCompanyCode(), "").equals("021")){
						codeNameEappRoute.setValue4(null); // 課長承認経路削除
					}
					else{
						codeNameEappRoute.setValue3(null); // 課長承認経路削除
					}
				}

				// 故障交換は上長承認不要
				if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) {
					//	CTCBS?
					if(Function.nvl(obj.getApCompanyCode(), "").equals("021")){
						codeNameEappRoute.setValue4(null); // 課長承認経路削除
						codeNameEappRoute.setValue5(null); // 部長承認経路削除
					}
					else{
						codeNameEappRoute.setValue3(null); // 課長承認経路削除
						codeNameEappRoute.setValue4(null); // 部長承認経路削除
					}
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

				for(int i = 0; i < Constants.MAX_EAPP_ROUTE_COUNT_AP_GET_TAN; i++) {
					List<String> authDispList;
					List<String> chargeDispList;
					List<String> dispTypeList;

					// 対象経路判別
					if(i <= 2) { // 申請部経路
						authDispList = applyRouteAuthDispList;
						chargeDispList = applyRouteChargeDispList;
						dispTypeList = applyRouteDispTypeList;
					} else if((3 <= i && i <= 8) || (11 <= i && i <= 12) || 16 <= i) {// 受付経路
						authDispList = acceptRouteAuthDispList;
						chargeDispList = acceptRouteChargeDispList;
						dispTypeList = acceptRouteDispTypeList;
					} else { // 承認経路
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
					// e申請との連携停止期間中のエラーメッセージ
					if(!eappWsEndpoint.startsWith("http")){
						throw new AppException(eappStopMessage);
					}

					//////////////////////////////////// e申請サービス呼び出し
					String eappIdStr = null;
					try {
						ApGetService service = new ApGetServiceProxy(eappWsEndpoint);
						eappIdStr = service.apply(
								obj.getApplicationId() // applicationId
								, Constants.AP_TYPE_GET_TAN // applicationType
								, obj.getApCompanyCode() // companyCode
								, obj.getApSectionCode() // apSectionCode
								, obj.getApCreateStaffCode() // apCreateStaffCode
								, obj.getApStaffCode() // apStaffCode
								, obj.getApTel() // apTel
								, title // apTitle
								, subTitle // apSubTitle
								, apListTitle // apListTitle
								, eAssetUrl // eAssetUrl
								, applyRouteAuthDispList.toArray(new String[0]) // applyRouteAuthDispArray
								, applyRouteChargeDispList.toArray(new String[0]) // applyRouteChargeDispArray
								, applyRouteDispTypeList.toArray(new String[0]) // applyRouteDispTypeArray
								, approveRouteAuthDispList.toArray(new String[0]) // approveRouteAuthDispArray
								, approveRouteChargeDispList.toArray(new String[0]) // approveRouteChargeDispArray
								, approveRouteDispTypeList.toArray(new String[0]) // approveRouteDispTypeArray
								, acceptRouteAuthDispList.toArray(new String[0]) // acceptRouteAuthDispArray
								, acceptRouteChargeDispList.toArray(new String[0]) // acceptRouteChargeDispArray
								, acceptRouteDispTypeList.toArray(new String[0]) // acceptRouteDispTypeArray
								, (Function.nvl(obj.getSpecialApproveFlag(), "").equals(Constants.FLAG_YES) ? Function.toCalendar(obj.getSpecialApproveDate()) : null) // specialApproveDate
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
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#deleteApGetTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan)
	 */
	public void deleteApGetTan(String loginStaffCode, String accessLevel, ApGetTan obj) throws AppException {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();
			ApGetTan apGetTanOld = getApGetTan(obj.getApplicationId(), true); // 現データの取得(データロック)

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer(); // エラーメッセージ格納

			// バージョンチェック
			if(obj.getVersion().intValue() != apGetTanOld.getVersion().intValue()) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_VER));
			}

			// 資産(機器)明細登録済チェック
			List<ApGetTanLineAst> astList = apGetTanOld.getApGetTanLineAstList();
			if(astList != null && astList.size() > 0) {
				for(int i = 0; i < astList.size(); i++) {
					ApGetTanLineAst astObj = astList.get(i);
					if(Function.nvl(astObj.getRegistQuantity(), 0).intValue() > 0) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "登録申請済みの資産(機器)明細が存在するため削除できません。"));
						break;
					}
				}
			}
			// ライセンス明細登録済チェック
			List<ApGetTanLineLic> licList = apGetTanOld.getApGetTanLineLicList();
			if(licList != null && licList.size() > 0) {
				for(int i = 0; i < licList.size(); i++) {
					ApGetTanLineLic licObj = licList.get(i);
					if(Function.nvl(licObj.getRegistQuantity(), 0).intValue() > 0) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "登録申請済みのライセンス明細が存在するため削除できません。"));
						break;
					}
				}
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// データ更新(履歴作成用にバージョンアップ)
			// 現データを更新に使用
			// 更新日・更新者
			Date sysdate = new Date(); // システム日付取得
			apGetTanOld.setUpdateDate(sysdate);
			apGetTanOld.setUpdateStaffCode(loginStaffCode);

			// バージョン・改訂番号
			apGetTanOld.setVersion(Function.nvl(obj.getVersion(), Integer.valueOf(1)) + 1);
			apGetTanOld.setDisplayVersion(Function.nvl(obj.getDisplayVersion(), Integer.valueOf(1)) + 1);

			// 更新コメント
			apGetTanOld.setUpdateComment(null);

			apGetTanDAO.updateApGetTan(apGetTanOld);

			//////////////////////////////////// 履歴作成
			histSession.createHistData(HIST_ENTITY_NAME, obj.getApplicationId(), HIST_OPERATION_DELETE, null);

			//////////////////////////////////// データ削除
			apGetTanDAO.deleteApGetTan(obj.getApplicationId());
			deleteLine(loginStaffCode, obj, apGetTanDAO);

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請削除"), e);
		}
	}

	/*
	 * 明細データの作成
	 */
	private void createLine(String loginStaffCode, ApGetTan obj, ApGetTanDAO apGetTanDAO, String applicationId) throws SQLException, IOException {
		Date sysdate = new Date(); // システム日付取得

		// 購入明細作成
		List<ApGetTanLinePur> purList = obj.getApGetTanLinePurList();
		if(purList != null && purList.size() > 0) {
			for(int i = 0; i < purList.size(); i++) {
				ApGetTanLinePur row = purList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apGetTanDAO.insertApGetTanLinePur(row);
			}
		}

		// その他費用明細作成
		List<ApGetTanLineOtrCost> otrCostList = obj.getApGetTanLineOtrCostList();
		if(otrCostList != null && otrCostList.size() > 0) {
			for(int i = 0; i < otrCostList.size(); i++) {
				ApGetTanLineOtrCost row = otrCostList.get(i);
				row.setApplicationId(applicationId); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apGetTanDAO.insertApGetTanLineOtrCost(row);
			}
		}

		// 添付明細作成
		List<ApGetTanLineAtt> attList = obj.getApGetTanLineAttList();
		if(attList != null && attList.size() > 0) {
			for(int i = 0; i < attList.size(); i++) {
				ApGetTanLineAtt row = attList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				if(row.getAttFileId() == null) { // 一時領域にのみファイルが存在する（保存前）
					// 一時領域 ⇒ 本領域にファイルコピー
					masterSession.fileCommit(row.getAttFileIdTmp(), FILE_SAVE_ENTITY_NAME, applicationId);
					row.setAttFileId(row.getAttFileIdTmp());
				}

				apGetTanDAO.insertApGetTanLineAtt(row);
			}
		}

		// 経費負担部署明細作成
		List<ApGetTanLineCostSec> costSecList = obj.getApGetTanLineCostSecList();
		if(costSecList != null && costSecList.size() > 0) {
			for(int i = 0; i < costSecList.size(); i++) {
				ApGetTanLineCostSec row = costSecList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				apGetTanDAO.insertApGetTanLineCostSec(row);
			}
		}

		// 資産(機器)明細作成
		List<ApGetTanLineAst> astList = obj.getApGetTanLineAstList();
		if(astList != null && astList.size() > 0) {
			for(int i = 0; i < astList.size(); i++) {
				ApGetTanLineAst row = astList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				if(row.getApGetTanLineAstId() == null) row.setApGetTanLineAstId(masterSession.nextVal(LINE_AST_SEQ_NAME)); // 明細ID新規
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				row.setCreateDate(Function.nvl(row.getCreateDate(), sysdate));
				row.setCreateStaffCode(Function.nvl(row.getCreateStaffCode(), loginStaffCode));
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				row.setRegistQuantity(Function.nvl(row.getRegistQuantity(), Integer.valueOf(0))); // 登録数の初期値は0

				row.setAutoRegistFlag(Function.nvl(row.getAutoRegistFlag(), Constants.FLAG_NO));
				row.setNoRegistFlag(Function.nvl(row.getNoRegistFlag(), Constants.FLAG_NO));
				row.setSealIssueFlag(Function.nvl(row.getSealIssueFlag(), Constants.FLAG_NO));
				row.setEquipmentFlag(Function.nvl(row.getEquipmentFlag(), Constants.FLAG_NO));

				apGetTanDAO.insertApGetTanLineAst(row);
			}
		}

		// ライセンス明細作成
		List<ApGetTanLineLic> licList = obj.getApGetTanLineLicList();
		if(licList != null && licList.size() > 0) {
			for(int i = 0; i < licList.size(); i++) {
				ApGetTanLineLic row = licList.get(i);
				row.setApplicationId(obj.getApplicationId()); // IDのセット
				if(row.getApGetTanLineLicId() == null) row.setApGetTanLineLicId(masterSession.nextVal(LINE_LIC_SEQ_NAME)); // 明細ID新規
				row.setLineSeq(i + 1); // 行シーケンスの振り直し(念のため)

				// 更新日・更新者
				if(row.getCreateDate() == null) {
					row.setCreateDate(sysdate);
					row.setCreateStaffCode(loginStaffCode);
				}
				row.setUpdateDate(sysdate);
				row.setUpdateStaffCode(loginStaffCode);

				row.setRegistQuantity(Function.nvl(row.getRegistQuantity(), Integer.valueOf(0))); // 登録数の初期値は0

				if(Function.nvl(row.getLicQuantityType(), "").equals(Constants.LIC_QUANTITY_TYPE_UNLIMITED)) {
					// ライセンス数制限が無限の場合は数量null
					row.setLicQuantity(null);
				}

				apGetTanDAO.insertApGetTanLineLic(row);
			}
		}
	}

	/*
	 * 明細データの削除
	 */
	private void deleteLine(String loginStaffCode, ApGetTan obj, ApGetTanDAO apGetTanDAO) throws SQLException {
		apGetTanDAO.deleteApGetTanLinePur(obj.getApplicationId());
		apGetTanDAO.deleteApGetTanLineOtrCost(obj.getApplicationId());
		apGetTanDAO.deleteApGetTanLineAtt(obj.getApplicationId());
		apGetTanDAO.deleteApGetTanLineCostSec(obj.getApplicationId());
		apGetTanDAO.deleteApGetTanLineAst(obj.getApplicationId());
		apGetTanDAO.deleteApGetTanLineLic(obj.getApplicationId());
	}

	/*
	 * 登録が完了しているかどうかを資産(機器)・ライセンス明細で判別
	 * 全明細が登録不要もしくは取得数 <= 登録数となっていれば完了
	 */
	private String getRegistCompleteFlag(List<ApGetTanLineAst> astList, List<ApGetTanLineLic> licList) {
		String ret = Constants.FLAG_YES;

		// 資産(機器)明細
		if(astList != null && astList.size() > 0) {
			for(int i = 0; i < astList.size(); i++) {
				ApGetTanLineAst row = astList.get(i);

				// 登録不要かどうかの判別
				if(Function.nvl(row.getNoRegistFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {
					// 取得数と登録数の比較
					int qty = Function.nvl(row.getQuantity(), Integer.valueOf(0)).intValue();
					int regQty = Function.nvl(row.getRegistQuantity(), Integer.valueOf(0)).intValue();

					if(qty > regQty) {
						ret = Constants.FLAG_NO;
						break;
					}
				}
			}
		}

		// ライセンス明細
		if(ret.equals(Constants.FLAG_YES)) {
			if(licList != null && licList.size() > 0) {
				for(int i = 0; i < licList.size(); i++) {
					ApGetTanLineLic row = licList.get(i);

					// 取得数と登録数の比較
					int qty = Function.nvl(row.getQuantity(), Integer.valueOf(0)).intValue();
					int regQty = Function.nvl(row.getRegistQuantity(), Integer.valueOf(0)).intValue();

					if(qty > regQty) {
						ret = Constants.FLAG_NO;
						break;
					}
				}
			}
		}

		return ret;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#createPossibleInputMasterCsv(java.lang.String, java.lang.String)
	 */
	public String createPossibleInputMasterCsv(String loginStaffCode, String accessLevel) {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = apGetTanDAO.createPossibleInputMasterList();

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_GET_TAN_MASTER, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel);

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "入力可能マスタ値ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#createApGetTanLineAstCsv(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public String createApGetTanLineAstCsv(String loginStaffCode, String accessLevel, String applicationId, List<ApGetTanLineAst> astLine) {
		// CSVヘッダ・出力プロパティ・出力フォーマット定義
		String headerRow = "No,資産(機器)名,資産(機器)分類,取得数,メーカー,メーカー型番,筐体/形状,資産プロジェクトコード,資産プロジェクト名";
		String[] outputProps = new String[]{"lineSeq", "astName", "astCategory2Name", "quantity", "astMakerName", "astMakerModel", "astShapeName", "astPrjCode", "astPrjName"};
		Format[] outputFormats = new Format[]{null, null, null, null, null, null, null, null, null};

		CsvWriterRowHandler handler = null;
		try {
			//////////////////////////////////// ファイル作成
			handler = new CsvWriterRowHandler(headerRow, outputProps, outputFormats);

			for(int i = 0; i < astLine.size(); i++) {
				handler.handleRow(astLine.get(i)); // 行出力
			}

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_GET_TAN_LINE_AST, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",applicationId:" + applicationId);

			return handler.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "資産(機器)明細CSVファイル作成"), e);
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#createApGetTanLineLicCsv(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public String createApGetTanLineLicCsv(String loginStaffCode, String accessLevel, String applicationId, List<ApGetTanLineLic> licLine) {
		// CSVヘッダ・出力プロパティ・出力フォーマット定義
		String headerRow = "No,ソフトウェアメーカー,ソフトウェア,ライセンス数制限,ライセンス数/1取得,取得数";
		String[] outputProps = new String[]{"lineSeq", "softwareMakerName", "softwareName", "licQuantityTypeName", "licQuantity", "quantity"};
		Format[] outputFormats = new Format[]{null, null, null, null, null, null};

		CsvWriterRowHandler handler = null;
		try {
			//////////////////////////////////// ファイル作成
			handler = new CsvWriterRowHandler(headerRow, outputProps, outputFormats);

			for(int i = 0; i < licLine.size(); i++) {
				handler.handleRow(licLine.get(i)); // 行出力
			}

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_AP_GET_TAN_LINE_LIC, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + ",applicationId:" + applicationId);

			return handler.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス明細CSVファイル作成"), e);
		} finally {
			if(handler != null) handler.close(); // ファイルクローズ
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#getApGetTanLineAstByCsv(java.lang.String)
	 */
	public List<ApGetTanLineAst> getApGetTanLineAstByCsv(String fileId) throws AppException {
		String[] inputProps = new String[]{"lineSeq", "astName", "astCategory2Name", "quantity", "astMakerName", "astMakerModel", "astShapeName", "astPrjCode", "astPrjName"};
		Format[] inputFormats = new Format[]{null, null, null, null, null, null, null, null, null};
		int headerRowCt = 1;

		CsvReaderRowHandler handler = null;

		try {
			List<ApGetTanLineAst> ret = new ArrayList<ApGetTanLineAst>();

			handler = new CsvReaderRowHandler(fileId, headerRowCt, ApGetTanLineAst.class, inputProps, inputFormats);

			ApGetTanLineAst row = null;
			StringBuffer errorMessage = new StringBuffer(); // 全行エラーメッセージ
			int rowCt = headerRowCt;
			do {
				////////////////////////////////////////// 行データ取得
				row = (ApGetTanLineAst) handler.handleRow();
				if(row == null) break; // 行データが取得できない場合は終了

				rowCt++;
				String rowNumStr = "[" + rowCt + "行目] ";
				StringBuffer rowErrorMessage = new StringBuffer(); // 一行エラーメッセージ

				////////////////////////////////////////// 読み込みエラー判定
				if(handler.getRowStatus() == CsvReaderRowHandler.ROW_STATUS_ERROR) { // CSV読み込み時のエラー有
					rowErrorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr, handler.getRowErrorMessage()));
				}

				////////////////////////////////////////// マスタ値セット
				// 資産(機器)分類マスタ値セット
				if(!Function.nvl(row.getAstCategory2Name(), "").equals("")) {
					String assetCategory2 = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, null, row.getAstCategory2Name());
					String assetCategory1 = masterSession.getCodeNameParentIdByName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, null, row.getAstCategory2Name());

					if(assetCategory2 == null || assetCategory1 == null) { // 対応するマスタが見つからない場合はエラー
						rowErrorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "資産(機器)分類", row.getAstCategory2Name()));
					} else {
						row.setAstCategory2Code(assetCategory2);

						CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, row.getAstCategory2Code(), null, null);

						if(Function.nvl(cn.getValue2(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) { // 数量管理
							row.setAutoRegistFlag(Constants.FLAG_YES);
						}
						if(Function.nvl(cn.getValue4(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) { // シール発行
							row.setSealIssueFlag(Constants.FLAG_YES);
						}

						CodeName astCategory1 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY1, assetCategory1, null, null);
						row.setAstCategory1Code(astCategory1.getInternalCode());
						row.setAstCategory1Name(astCategory1.getValue1());

						// 付加項目
						row.setAstQuantityManageType(cn.getValue2()); // 数量管理区分
						row.setAstThinClientType(cn.getValue3()); // シンクライアント区分
					}
				}

				// メーカーマスタ値セット
				if(!Function.nvl(row.getAstMakerName(), "").equals("")) {
					String makerCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_MAKER, null, row.getAstMakerName());
					if(makerCode == null) {  // 対応するマスタが見つからない場合は手入力扱い

					} else {
						row.setAstMakerCode(makerCode);
					}
				}

				// 筐体/形状マスタ値セット
				if(!Function.nvl(row.getAstShapeName(), "").equals("")) {
					String shapeCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_ASSET_SHAPE, null, row.getAstShapeName());
					if(shapeCode == null) { // 対応するマスタが見つからない場合はエラー
						rowErrorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "筐体/形状", row.getAstShapeName()));
					} else {
						row.setAstShapeCode(shapeCode);
					}
				}

				// プロジェクト情報セット
				if(!Function.nvl(row.getAstPrjCode(), "").equals("")) {
					Project prj = masterSession.getProject(row.getAstPrjCode());
					if(prj == null) {
						rowErrorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT_DISP_VAL, rowNumStr + "プロジェクトコード", row.getAstPrjCode()));
					} else {
						row.setAstPrjName(prj.getProjectName());
						row.setAstPrjType(prj.getProjectType());
					}
				}

				////////////////////////////////////////// 行登録
				ret.add(row);


				if(rowErrorMessage.length() > 0) { // 対象行でエラー発生
					errorMessage.append(rowErrorMessage.toString());
				}

			} while(true);

			if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

			return ret;
		} finally {
			if(handler != null) handler.close();
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#getApGetTanLineLicByCsv(java.lang.String)
	 */
	public List<ApGetTanLineLic> getApGetTanLineLicByCsv(String fileId) throws AppException {
		String[] inputProps = new String[]{"lineSeq", "softwareMakerName", "softwareName", "licQuantityTypeName", "licQuantity", "quantity"};
		Format[] inputFormats = new Format[]{null, null, null, null, null, null};

		int headerRowCt = 1;

		CsvReaderRowHandler handler = null;

		try {
			List<ApGetTanLineLic> ret = new ArrayList<ApGetTanLineLic>();

			handler = new CsvReaderRowHandler(fileId, headerRowCt, ApGetTanLineLic.class, inputProps, inputFormats);

			ApGetTanLineLic row = null;
			StringBuffer errorMessage = new StringBuffer(); // 全行エラーメッセージ
			int rowCt = headerRowCt;
			do {
				// 行データ取得
				row = (ApGetTanLineLic) handler.handleRow();
				if(row == null) break; // 行データが取得できない場合は終了

				rowCt++;
				String rowNumStr = "[" + rowCt + "行目] ";
				StringBuffer rowErrorMessage = new StringBuffer(); // 一行エラーメッセージ

				////////////////////////////////////////// 読み込みエラー判定
				if(handler.getRowStatus() == CsvReaderRowHandler.ROW_STATUS_ERROR) { // CSV読み込み時のエラー有
					rowErrorMessage.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, rowNumStr, handler.getRowErrorMessage()));
				}


				// ソフトウェアメーカーマスタ値セット
				if(!Function.nvl(row.getSoftwareMakerName(), "").equals("")) {
					Long softwareMakerId = licenseSession.getSoftwareMakerIdByName(row.getSoftwareMakerName());
					if(softwareMakerId == null) {  // 対応するマスタが見つからない場合は手入力扱い(ソフトウェアも)

					} else {
						row.setSoftwareMakerId(softwareMakerId);

						// ソフトウェアマスタ値セット
						if(!Function.nvl(row.getSoftwareName(), "").equals("")) {
							Long softwareId = licenseSession.getSoftwareIdByName(softwareMakerId, row.getSoftwareName());
							if(softwareId == null) {  // 対応するマスタが見つからない場合は手入力扱い

							} else {
								row.setSoftwareId(softwareId);
							}
						}
					}
				}

				// ライセンス数量入力値によるライセンス数制限セット
				if(Function.nvl(row.getLicQuantityTypeName(), Constants.LIC_QUANTITY_TYPE_NAME_LIMIT).equals(Constants.LIC_QUANTITY_TYPE_NAME_LIMIT)) {
					row.setLicQuantityType(Constants.LIC_QUANTITY_TYPE_LIMIT);
					row.setLicQuantityTypeName(Constants.LIC_QUANTITY_TYPE_NAME_LIMIT);
				} else {
					row.setLicQuantityType(Constants.LIC_QUANTITY_TYPE_UNLIMITED);
					row.setLicQuantity(null);
				}

				////////////////////////////////////////// 行登録
				ret.add(row);

				if(rowErrorMessage.length() > 0) { // 対象行でエラー発生
					errorMessage.append(rowErrorMessage.toString());
				}
			} while(true);

			if(errorMessage.length() > 0) throw new AppException(errorMessage.toString());

			return ret;
		} finally {
			if(handler != null) handler.close();
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#createApGetTanPdf(java.util.List, java.lang.Boolean)
	 */
	public String createApGetTanPdf(List<String> applicationIdList, Boolean lineOutputFlag) {
		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();

		reportParam.put("applicationIdWhere", Function.getInCondition("nagt.APPLICATION_ID", applicationIdList, true));
		if(lineOutputFlag != null && lineOutputFlag.booleanValue()) reportParam.put("lineOutputFlag", "1");

		// PDF生成
		PdfExporter exporter = new PdfExporter();

		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApGetTan.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#createApGetTanHistPdf(java.lang.String, java.lang.Integer)
	 */
	public String createApGetTanHistPdf(String applicationId, Integer version) {
		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();

		reportParam.put("applicationIdWhere", "nagt.APPLICATION_ID = '" + applicationId + "'");
		reportParam.put("version", version);
		reportParam.put("lineOutputFlag", "1");

		// PDF生成
		PdfExporter exporter = new PdfExporter();

		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ApGetTan.jasper", reportParam);
			return exporter.getFileId();
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "申請書印刷用データ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#approveApGetTan(java.lang.Long, java.lang.String)
	 */
	public void approveApGetTan(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApGetTan apGetTan = getApGetTan(eappId, true);

		// 更新前の申請ステータス取得
		String oldApStatus = null;
		if(apGetTan != null) {
			oldApStatus = apGetTan.getApStatus();
		}

		CodeName appRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_GET_TAN, null, apGetTan.getApCompanyCode(), null);

		// 故障交換もしくは、取得申請をe申請で承認する会社は承認済に更新
		if(Function.nvl(apGetTan.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)
			|| (appRoute != null && Function.nvl(appRoute.getValue2(), "").equals(Constants.FLAG_YES))) {
			// ステータス
			apGetTan.setApStatus(Constants.AP_STATUS_GET_TAN_APPROVE);
			// 更新コメント
			apGetTan.setUpdateComment(null);
			// 承認日
			apGetTan.setApproveDate(new Date());

			//	注文書？eAsset発注不要チェックなし？
			if(!Function.nvl(apGetTan.getReoOrderType(), "").equals("") && Function.nvl(apGetTan.getReoDisuseFlag(), "").equals(Constants.FLAG_NO)){
				CodeName codeName = masterSession.getCodeName(Constants.CATEGORY_CODE_REO_ORDER_SET, apGetTan.getReoOrderType(), apGetTan.getApCompanyCode(), null);
				//	注文書印刷可能で、発注処理を行う場合のみ納品担当者依頼メール送信
				if(!Function.nvl(codeName.getValue10(), "").equals(Constants.FLAG_NO) && !Function.nvl(codeName.getValue11(), "").equals(Constants.LE_PO_TYPE_NO)){
					//	納品担当者依頼メール送信
					apGetTanSession.sendReoDlvStaff(apGetTan);
				}
			}

			updateApGetTan(execStaffCode, null, apGetTan, false, false, true, HIST_OPERATION_APPROVE);

			//////////////////////////////////// 自動登録処理
			autoRegistApGetTanLineAst(execStaffCode, null, apGetTan);

			// 承認完了報告メール
			sendApproveMail(apGetTan.getApplicationId(), oldApStatus);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#approveSuperiorApGetTan(java.lang.Long, java.lang.String)
	 */
	public void approveSuperiorApGetTan(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApGetTan apGetTan = getApGetTan(eappId, true);

		CodeName appRoute = masterSession.getCodeName(Constants.CATEGORY_CODE_EAPP_ROUTE_CHARGE_AP_GET_TAN, null, apGetTan.getApCompanyCode(), null);
		if(appRoute != null && Function.nvl(appRoute.getValue2(), "").equals(Constants.FLAG_YES)) { // 取得申請をe申請で承認する会社以外は受付承認フラグセット無し
			// 受付承認フラグ
			apGetTan.setApproveSuperiorFlag(Constants.FLAG_YES);
		}
		// 更新コメント
		apGetTan.setUpdateComment(null);

		updateApGetTan(execStaffCode, null, apGetTan, false, false, true, HIST_OPERATION_APPROVE_SUPERIOR);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#cancelApplyApGetTan(java.lang.Long, java.lang.String)
	 */
	public void cancelApplyApGetTan(Long eappId, String execStaffCode) throws AppException {
		//////////////////////////////////// 更新
		ApGetTan apGetTan = getApGetTan(eappId, true);

		if(apGetTan != null) {
			// ステータス
			apGetTan.setApStatus(Constants.AP_STATUS_GET_TAN_NOAPPLY);
			// 更新コメント
			apGetTan.setUpdateComment(null);
			apGetTan.setEappId(null); // 書類IDクリア

			updateApGetTan(execStaffCode, null, apGetTan, false, false, true, HIST_OPERATION_CANCEL_APPLY);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#rejectApGetTan(java.lang.Long, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void rejectApGetTan(Long eappId, String execStaffCode, String rejectType, String rejectReason) throws AppException {
		String histOperation;

		//////////////////////////////////// 更新
		ApGetTan apGetTan = getApGetTan(eappId, true);

		if(apGetTan != null) {
			// ステータス
			if(Function.nvl(rejectType, "").equals(Constants.AP_REJECT_TYPE_REJECT)) { // 却下
				apGetTan.setApStatus(Constants.AP_STATUS_GET_TAN_REJECT);
				histOperation = HIST_OPERATION_REJECT;
			} else { // 差戻し
				apGetTan.setApStatus(Constants.AP_STATUS_GET_TAN_SENDBACK);
				apGetTan.setEappId(null); // 書類IDクリア
				histOperation = HIST_OPERATION_SENDBACK;
			}

			// コメント
			apGetTan.setUpdateComment(rejectReason);

			updateApGetTan(execStaffCode, null, apGetTan, false, false, true, histOperation);
		}
	}

	/**
	 * バリデーション
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return チェックOK：空文字、チェックNG：エラーメッセージ
	 */
	private String validateApGetTan(String loginStaffCode, String accessLevel, ApGetTan obj) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 項目定義バリデーション
		int apStatus = obj.getApStatus() == null ? null : Integer.valueOf(obj.getApStatus());

		if(!Function.isAccessLevelAdmin(accessLevel)) { // 全社権限ではない場合
			apStatus += 6;
		}
		if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_REPLACE)) { // 故障交換の場合
			apStatus += 12; // 項目定義が異なる
		}

		// ヘッダ
		errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, "NEA_AP_GET_TAN", obj, apStatus, null));

		// 明細
		List<ApGetTanLinePur> purLine = obj.getApGetTanLinePurList();
		if(purLine != null && purLine.size() > 0) {
			for(int i = 0; i < purLine.size(); i++) {
				ApGetTanLinePur purObj = purLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, "NEA_AP_GET_TAN_LINE_PUR", purObj, apStatus, null));
			}
		}
		List<ApGetTanLineOtrCost> otrCostLine = obj.getApGetTanLineOtrCostList();
		if(otrCostLine != null && otrCostLine.size() > 0) {
			for(int i = 0; i < otrCostLine.size(); i++) {
				ApGetTanLineOtrCost otrCostObj = otrCostLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, "NEA_AP_GET_TAN_LINE_OTR_COST", otrCostObj, apStatus, null));
			}
		}
		List<ApGetTanLineAtt> attLine = obj.getApGetTanLineAttList();
		if(attLine != null && attLine.size() > 0) {
			for(int i = 0; i < attLine.size(); i++) {
				ApGetTanLineAtt attObj = attLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, "NEA_AP_GET_TAN_LINE_ATT", attObj, apStatus, null));
			}
		}
		List<ApGetTanLineCostSec> costSecLine = obj.getApGetTanLineCostSecList();
		if(costSecLine != null && costSecLine.size() > 0) {
			for(int i = 0; i < costSecLine.size(); i++) {
				ApGetTanLineCostSec costSecObj = costSecLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, "NEA_AP_GET_TAN_LINE_COST_SEC", costSecObj, apStatus, null));
			}
		}
		List<ApGetTanLineAst> astLine = obj.getApGetTanLineAstList();
		if(astLine != null && astLine.size() > 0) {
			for(int i = 0; i < astLine.size(); i++) {
				ApGetTanLineAst astObj = astLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, "NEA_AP_GET_TAN_LINE_AST", astObj, apStatus, null));
			}
		}
		List<ApGetTanLineLic> licLine = obj.getApGetTanLineLicList();
		if(licLine != null && licLine.size() > 0) {
			for(int i = 0; i < licLine.size(); i++) {
				ApGetTanLineLic licObj = licLine.get(i);
				errMsg.append(masterSession.validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_AP_GET_TAN, "NEA_AP_GET_TAN_LINE_LIC", licObj, apStatus, null));
			}
		}

		//////////////////////////////////// 条件付必須バリデーション
		String stat = Function.nvl(obj.getApStatus(), ""); // ステータス
		String apGetType = Function.nvl(obj.getApGetType(), ""); // 取得形態
		boolean reOrderPrintFlag = false;	//	注文書印刷使用判別フラグ
		boolean estimateFlag = false;	//	注文書印刷見積書参照判別フラグ

		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_GET_TAN_APPROVE)
				&& !stat.equals(Constants.AP_STATUS_GET_TAN_REJECT)
				&& !stat.equals(Constants.AP_STATUS_GET_TAN_CANCEL)) {
			// 承認日
			if(Function.nvl(obj.getSpecialApproveFlag(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) {
				if(obj.getSpecialApproveDate() == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "承認日", "稟議書・経営会議等承認済がチェックされている場合は"));
				}
			}

			// クラウド区分
			if(!Function.nvl(obj.getGetItemName(), "").equals("")) {
				if(Function.nvl(obj.getGetItemCloudType(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "取得-クラウド区分", "案件名が入力されている場合は"));
				}
			}
			// 案件グループ
			if(Function.nvl(obj.getGetItemCloudType(), "").equals(Constants.CLOUD_TYPE_CLOUD)) {
				if(Function.nvl(obj.getGetItemGroupCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "取得-案件グループ", "クラウド案件の場合は"));
				}
			}

			// 購入先明細
			if(!apGetType.equals(Constants.AP_GET_TYPE_RENTAL) && !apGetType.equals(Constants.AP_GET_TYPE_REPLACE)) {
				// レンタル・故障交換以外では必須
				if(purLine == null || purLine.size() == 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "取得-購入先/購入金額", "選択された取得形態では1件以上"));
				}
			}

			// リース/レンタル
			if(apGetType.equals(Constants.AP_GET_TYPE_LEASE) || apGetType.equals(Constants.AP_GET_TYPE_RENTAL)) {
				String leReLabel;
				if(apGetType.equals(Constants.AP_GET_TYPE_LEASE)) {
					leReLabel = "リース";
				} else {
					leReLabel = "レンタル";
				}

				if(!stat.equals(Constants.AP_STATUS_GET_TAN_APPLY)) {
					// 会社
					if(Function.nvl(obj.getGetLeReCompanyCode(), "").equals("")) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "取得-" + leReLabel + "会社"));
					}
					// 月額
					if(obj.getGetLeReMonthAmount() == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "取得-" + leReLabel + "料月額"));
					}
					// 契約月数
					if(obj.getGetLeReCount() == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "取得-" + leReLabel + "契約月数"));
					}
					// 総額
					if(obj.getGetLeReTotalAmount() == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "取得-" + leReLabel + "料総額"));
					}
					if(apGetType.equals(Constants.AP_GET_TYPE_LEASE)) {
						// リース見積依頼書書類ID
						if(Function.nvl(obj.getGetLeEappId(), "").equals("")) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "取得-" + leReLabel + "書類ID(リース見積依頼書)"));
						}
					}
				}

				// 見積書番号
				if(Function.nvl(obj.getGetLeReEstimateNumber(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "取得-" + leReLabel + "見積番号"));
				}
			}

			// 設置場所が社外の場合
			if(Function.nvl(obj.getHolOfficeOutsideFlag(), "").equals(Constants.FLAG_YES)) {
				// 社外常設PC申請書番号
				if(Function.nvl(obj.getHolOfficeOutsidePcId(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "保有・設置-社外常設PC申請書番号", "設置(使用)場所が社外の場合は"));
				}
				// 設置場所補足
				if(Function.nvl(obj.getHolOfficeDescription(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "保有・設置-設置場所補足", "設置(使用)場所が社外の場合は"));
				}
			}

			String useCostSecFlag = Function.nvl(obj.getApGetAmountRangeUseCostSecType(), "");

			// 販管／原価区分
			if(useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_BOTH)
				|| useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_TYPE)) {
				if(Function.nvl(obj.getCostType(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "経費負担-販売管理費/原価区分"));
				}

				// 原価でプロジェクトコードの指定無し（未定でもない）
				if(Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)
					&& Function.nvl(obj.getCostDepPrjUndecidedFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)
					&& Function.nvl(obj.getCostDepPrjCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "経費負担-減価償却プロジェクトコード"));
				}
			}
			// 経費負担部署
			if(useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_BOTH)
				|| useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_SEC)) {
				if(costSecLine == null || costSecLine.size() == 0) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "経費負担-経費負担部署", "1件以上"));
				}

				if(useCostSecFlag.equals(Constants.AP_GET_AMOUNT_RANGE_USE_COST_SEC_BOTH)) {
					if(Function.nvl(obj.getCostType(), "").equals(Constants.COST_TYPE_GENKA)) {
						if(costSecLine != null && costSecLine.size() > 1) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "経費負担-経費負担部署", "販売管理費/原価区分に\"原価\"を指定した場合1件しか入力できません。"));
						}

					}
				}

				//	資産計上部課入力チェック
				if(Function.nvl(obj.getCostSectionCode(), "").equals("")){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "経費負担-資産計上部課"));
				}

			}

		}

		// 承認済・却下・取下げ以外（項目編集可）
		if(
			 !stat.equals(Constants.AP_STATUS_GET_TAN_REJECT)
			&& !stat.equals(Constants.AP_STATUS_GET_TAN_CANCEL)
		) {
			//	リース・レンタル会社から注文書使用するか確認
			CodeName leReCompany = masterSession.getCodeName(Constants.CATEGORY_CODE_LEASE_RENTAL_CUSTOMER, obj.getGetLeReCompanyCode(), obj.getApCompanyCode(), null);

			//	注文書区分
			if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_RENTAL) && leReCompany != null && Function.nvl(leReCompany.getValue10(), "").equals(Constants.FLAG_YES) ){
				if(Function.nvl(obj.getReoOrderType(), "").equals("")){
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "取得-注文書区分"));
				}
				else{
					CodeName reoOreder = masterSession.getCodeName(Constants.CATEGORY_CODE_REO_ORDER_SET, obj.getReoOrderType(), obj.getApCompanyCode(), null);
					if(reoOreder != null){
						//	注文書印刷使用？
						if(Function.nvl(reoOreder.getValue10(), "").equals(Constants.FLAG_YES)){
							reOrderPrintFlag = true;
						}
						if(Function.nvl(reoOreder.getValue7(), "").equals(Constants.FLAG_YES)){
							estimateFlag = true;
							String leReLabel;
							if(apGetType.equals(Constants.AP_GET_TYPE_LEASE)) {
								leReLabel = "リース";
							} else {
								leReLabel = "レンタル";
							}
							//	日数入力あり？
							if(obj.getGetLeReDateCount() != null){
								//	30日以上？
								if(obj.getGetLeReDateCount() > 30){
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "取得-" + leReLabel + "1ヶ月未満の日数","1ヶ月未満の日数を指定してください。"));
								}
								//	1ヶ月未満の金額必須チェック
								if(obj.getGetLeReMonthLessAmount() == null){
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "取得-" + leReLabel + "1ヶ月未満の金額(税抜)"));
								}
							}
						}
						//	情シス配備？
						if(Function.nvl(reoOreder.getValue30(), "").equals(Constants.FLAG_YES)){
							if(Function.nvl(obj.getGetSystemSectionDeployFlag(), "").equals(Constants.FLAG_NO)){
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "取得目的/納入-情報システム部配備","情報システム部配備を選択してください。"));
							}
						}
					}
				}
			}

			if(!Function.nvl(obj.getReoOrderType(), "").equals("") && reOrderPrintFlag){
				//	資産管理担当者:電話番号
				if(Function.nvl(obj.getReoAstTel(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-資産管理担当者-電話番号"));
				}
				//	資産管理担当者:メールアドレス
				if(Function.nvl(obj.getReoAstMailAddress(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-資産管理担当者-メールアドレス"));
				}
				//	使用希望開始日
/*
				if(obj.getReoUseHopeStartDate() == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-使用希望開始日"));
				}
*/
				//	請求書送付先担当者:社員番号
				//	自動指定以外の場合
				if(!Function.nvl(obj.getReoInvStaffInputType(), "").equals("1")){
					if(Function.nvl(obj.getReoInvStaffName(), "").equals("")) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-請求書送付先担当者-社員名"));
					}
					if(Function.nvl(obj.getReoInvSectionName(), "").equals("")) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-請求書送付先担当者-部署名"));
					}
				}
				//	請求書送付先担当者:住所
				if(Function.nvl(obj.getReoInvAddress(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-請求書送付先担当者-住所"));
				}
				//	請求書送付先担当者:電話番号
				if(Function.nvl(obj.getReoInvTel(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-請求書送付先担当者-電話番号"));
				}
				//	請求書送付先担当者:メールアドレス
				if(Function.nvl(obj.getReoInvMailAddress(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-請求書送付先担当者-メールアドレス"));
				}
				//	納品先担当者:社員番号
				if(Function.nvl(obj.getReoDlvStaffCode(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-納品先担当者-社員番号"));
				}
				//	請求書送付先担当者:住所
				if(Function.nvl(obj.getReoDlvAddress(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-納品先担当者-住所"));
				}
				//	請求書送付先担当者:電話番号
				if(Function.nvl(obj.getReoDlvTel(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-納品先担当者-電話番号"));
				}
				//	請求書送付先担当者:メールアドレス
				if(Function.nvl(obj.getReoDlvMailAddress(), "").equals("")) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "注文書-納品先担当者-メールアドレス"));
				}
			}
		}

		// 明細件数
		if(apGetType.equals(Constants.AP_GET_TYPE_LEASE) || apGetType.equals(Constants.AP_GET_TYPE_RENTAL)) { // リース・レンタル
			if((astLine == null || astLine.size() == 0)
					&& (licLine == null || licLine.size() == 0)) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "明細", "資産(機器)かライセンスのどちらかは1件以上"));
			}
		} else { // リース・レンタル
			if(astLine == null || astLine.size() == 0) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "明細-資産(機器)", "1件以上"));
			}
		}

		// 資産(機器)明細(故障交換対象以外)
		if(!apGetType.equals(Constants.AP_GET_TYPE_REPLACE)) {
			if(astLine != null && astLine.size() > 0) {
				int astGetLeContractMonth = 0;
				for(int i = 0; i < astLine.size(); i++) {
					ApGetTanLineAst astObj = astLine.get(i);

					if(!Function.nvl(astObj.getAstCategory2Code(), "").equals("")) {
						CodeName astCategory2 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, astObj.getAstCategory2Code(), null, null);

						// 資産カテゴリが数量管理以外の場合
						if(astCategory2 != null && !Function.nvl(astCategory2.getValue2(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) {
							if(Function.nvl(astObj.getRegistQuantity(), 0).intValue() == 0) { // 未登録
								// メーカー
								if(Function.nvl(astObj.getAstMakerName(), "").equals("")) {
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "明細-資産(機器)[No" + astObj.getLineSeq() + "]-メーカー", "該当資産(機器)分類では"));
								}
								// メーカー型番
								if(Function.nvl(astObj.getAstMakerModel(), "").equals("")) {
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED_CONDITIONAL, "明細-資産(機器)[No" + astObj.getLineSeq() + "]-メーカー型番", "該当資産(機器)分類では"));
								}
								// 自動登録
								if(Function.nvl(astObj.getAutoRegistFlag(), "").equals(Constants.FLAG_YES)) {
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "明細-資産(機器)[No" + astObj.getLineSeq() + "]-自動登録", "該当資産(機器)分類では自動登録指定はできません。"));
								}
							}
						}
					}

					//	注文書項目使用？
					if(Function.nvl(obj.getApGetType(), "").equals(Constants.AP_GET_TYPE_RENTAL) && reOrderPrintFlag){
						if(!estimateFlag){
							if(!Function.nvl(astObj.getAstGetLeContractMonth(), "").equals("")){
								if(i == 0){
									astGetLeContractMonth = astObj.getAstGetLeContractMonth();
								}
								else{
									if(astGetLeContractMonth != astObj.getAstGetLeContractMonth()){
										errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "明細-資産(機器)", "選択した資産(機器)明細の契約月数が異なります。"));
									}
								}
							}
						}
					}

				}
			}
		}

		// ライセンス明細
		if(licLine != null && licLine.size() > 0) {
			for(int i = 0; i < licLine.size(); i++) {
				ApGetTanLineLic licObj = licLine.get(i);

				// ライセンス数制限が有限の場合
				if(Function.nvl(licObj.getLicQuantityType(), "").equals(Constants.LIC_QUANTITY_TYPE_LIMIT)) {
					if(licObj.getLicQuantity() == null) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, "明細-ライセンス[No" + licObj.getLineSeq() + "]-ライセンス数"));
					}
				}
			}
		}

		//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)

		// 承認済・却下・取下げ以外（項目編集可）
		if(!stat.equals(Constants.AP_STATUS_GET_TAN_APPROVE)
				&& !stat.equals(Constants.AP_STATUS_GET_TAN_REJECT)
				&& !stat.equals(Constants.AP_STATUS_GET_TAN_CANCEL)) {

			// 申請者
			if(!Function.nvl(obj.getApStaffCode(), "").equals("")) {
				if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_TAN_APPROVE)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_TAN_REJECT)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_TAN_CANCEL)) {
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

			// 案件グループ
			if(!Function.nvl(obj.getGetItemGroupCode(), "").equals("")) {
				Map<String, Object> lovParam = new HashMap<String, Object>();
				lovParam.put("companyCode", obj.getApCompanyCode());
				if(masterSession.getLovData("selectPpfs_Group_LOV", lovParam, obj.getGetItemGroupCode()) == null) {
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
			// 経費負担部課コード
			if(costSecLine != null && costSecLine.size() > 0) {
				// カレント年度取得
				CodeName currentYearCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
				int currentYear = Integer.valueOf(currentYearCodeName.getValue1());

				for(int i = 0; i < costSecLine.size(); i++) {
					ApGetTanLineCostSec costSecObj = costSecLine.get(i);
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
			//	資産機器明細
			if(astLine != null && astLine.size() > 0) {
				Map<String, Object> lovParam = new HashMap<String, Object>();
				lovParam.put("companyCode", obj.getApCompanyCode());
				lovParam.put("prjCategory", Constants.PROJECT_GATEGORY_AST);
				for(int i = 0; i < astLine.size(); i++) {
					ApGetTanLineAst astObj = astLine.get(i);
					// 資産プロジェクトコード
					if(!Function.nvl(astObj.getAstPrjCode(), "").equals("")) {
						LovDataEx lovData = masterSession.getLovData("selectProject_LOV", lovParam, astObj.getAstPrjCode());
						if(lovData == null) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "資産明細[No" + astObj.getLineSeq() + "]-資産プロジェクトコード"));
						}
/*
						else{
							//	CTC、CTCTのみ対象
							if(Function.nvl(obj.getApCompanyCode(), "").equals("001") || Function.nvl(obj.getApCompanyCode(), "").equals("017")){
								//	間接？
								if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_INDIRECTION)){
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産明細[No" + astObj.getLineSeq() + "]-資産プロジェクトコード", "間接プロジェクトが選択されています。"));
								}
								//	？
								if(Function.nvl(lovData.getValue4(), "").equals(Constants.PROJECT_CONTRACT)){
									errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産明細[No" + astObj.getLineSeq() + "]-資産プロジェクトコード", "契約プロジェクトが選択されています。"));
								}
							}
						}
*/
					}
				}
			}

			//	注文書：納品先担当者
			if(!Function.nvl(obj.getReoDlvStaffCode(), "").equals("")){
				// 退職社員NG
				if(masterSession.getStaffValid(obj.getReoDlvCompanyCode(), obj.getReoDlvStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "注文書-納品先担当者"));
				}
			}

		}

		// 登録申請担当者
		if(!Function.nvl(obj.getApRegistStaffCode(), "").equals("")) {
			if(Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_TAN_APPROVE)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_TAN_REJECT)
					|| Function.nvl(obj.getApStatus(),"").equals(Constants.AP_STATUS_GET_TAN_CANCEL)) {
					// 承認済・却下・取下げは退職社員OK
				// 承認済・却下・取下げ以外は退職社員NG
				if(masterSession.getStaff(obj.getApCompanyCode(), obj.getApRegistStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者-登録申請担当者"));
				}
			} else {
				// 承認済・却下・取下げ以外は退職社員NG
				if(masterSession.getStaffValid(obj.getApCompanyCode(), obj.getApRegistStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "申請者-登録申請担当者"));
				}
			}
		}

		//////////////////////////////////// その他バリデーション
		// 日付範囲チェック 納入予定日 > 返却予定日
		if(obj.getGetDeliveryDate() != null && obj.getGetReturnDate() != null) {
			if(obj.getGetDeliveryDate().compareTo(obj.getGetReturnDate()) > 0) {
				errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "取得-返却予定日", "納入予定日"));
			}
		}

		// 経費負担部課重複・配分合計
		if(costSecLine != null && costSecLine.size() > 0) {
			Set<String> sectionCodeSet = new HashSet<String>();
			int distTotal = 0;
			double adjustNum = 10;
			boolean isDup = false; // 重複フラグ
			for(int i = 0; i < costSecLine.size(); i++) {
				ApGetTanLineCostSec costSecObj = costSecLine.get(i);
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
		// 金額範囲
		if(!Function.isAccessLevelAdmin(accessLevel) && !Function.nvl(obj.getApGetAmountRange(), "").equals("")) { // 一般権限の場合
			// 取得金額範囲 != 取得金額
			CodeName amountRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_GET_AMOUNT_RANGE, obj.getApGetAmountRange(), obj.getApCompanyCode(), null);

			if(amountRange != null && obj.getGetTotalAmount() != null) {
				Long fromAmount = amountRange.getValue2() == null ? null : Long.valueOf(amountRange.getValue2());
				Long toAmount = amountRange.getValue3() == null ? null : Long.valueOf(amountRange.getValue3());

				if(fromAmount != null && fromAmount.longValue() > obj.getGetTotalAmount().longValue()) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "取得金額範囲", "取得金額合計[" + new DecimalFormat("#,##0").format(obj.getGetTotalAmount()) + "]が取得金額範囲[" + obj.getApGetAmountRangeName() + "]外です。"));
				} else if(toAmount != null && toAmount.longValue() < obj.getGetTotalAmount().longValue()) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "取得金額範囲", "取得金額合計[" + new DecimalFormat("#,##0").format(obj.getGetTotalAmount()) + "]が取得金額範囲[" + obj.getApGetAmountRangeName() + "]外です。"));
				}

			}
		}
		// 資産(機器)明細登録数
		if(astLine != null && astLine.size() > 0) {
			for(int i = 0; i < astLine.size(); i++) {
				ApGetTanLineAst astObj = astLine.get(i);
				if(Function.nvl(astObj.getQuantity(), 0).intValue() < Function.nvl(astObj.getRegistQuantity(), 0).intValue()) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "明細-資産(機器)[No" + astObj.getLineSeq() + "]-取得数", "登録数"));
				}
			}
		}
		// ライセンス明細登録数
		if(licLine != null && licLine.size() > 0) {
			for(int i = 0; i < licLine.size(); i++) {
				ApGetTanLineLic licObj = licLine.get(i);
				if(Function.nvl(licObj.getQuantity(), 0).intValue() < Function.nvl(licObj.getRegistQuantity(), 0).intValue()) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, "明細-ライセンス[No" + licObj.getLineSeq() + "]-取得数", "登録数"));
				}
			}
		}
		// 故障交換対象の資産(機器)分類チェック
		if(apGetType.equals(Constants.AP_GET_TYPE_REPLACE)) {
			if(astLine != null && astLine.size() > 0) {
				for(int i = 0; i < astLine.size(); i++) {
					ApGetTanLineAst astObj = astLine.get(i);
					CodeName astCategory2 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, astObj.getAstCategory2Code(), null, null);

					// 数量管理資産分類はNG
					if(astCategory2 != null && Function.nvl(astCategory2.getValue2(), "").equals(Constants.FLAG_YES)) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM, "明細-資産(機器)[No" + astObj.getLineSeq() + "]", "該当資産(機器)分類のものは故障交換できません。"));
					}
				}
			}
		}

		return errMsg.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#updateApGetTanLineAstRegist(java.lang.String, java.lang.String, java.lang.Long, java.lang.Integer, java.lang.String)
	 */
	public void updateApGetTanLineAstRegist(String loginStaffCode, String applicationId, Long apGetTanLineAstId, Integer addRegistQuantity, String operation) throws AppException {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();

			//////////////////////////////////// 明細の登録数更新
			apGetTanDAO.updateApGetTanLineAstRegist(loginStaffCode, apGetTanLineAstId, addRegistQuantity);

			//////////////////////////////////// 取得申請の更新
			ApGetTan apGetTan = getApGetTan(applicationId);

			updateApGetTan(loginStaffCode, null, apGetTan, false, false, true, operation);

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "取得申請資産(機器)明細登録数更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#updateApGetTanLineLicRegist(java.lang.String, java.lang.String, java.lang.Long, java.lang.Integer, java.lang.String)
	 */
	public void updateApGetTanLineLicRegist(String loginStaffCode, String applicationId, Long apGetTanLineLicId, Integer addRegistQuantity, String operation) throws AppException {
		try {
			ApGetTanDAO apGetTanDAO = new ApGetTanDAO();

			//////////////////////////////////// 明細の登録数更新
			apGetTanDAO.updateApGetTanLineLicRegist(loginStaffCode, apGetTanLineLicId, addRegistQuantity);

			//////////////////////////////////// 取得申請の更新
			ApGetTan apGetTan = getApGetTan(applicationId);

			updateApGetTan(loginStaffCode, null, apGetTan, false, false, true, operation);

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ライセンス明細登録数更新"), e);
		}
	}

	/**
	 * 情報機器等自動登録
	 * @param loginStaffCode ログイン者社員番号
	 * @param accessLevel    アクセスレベル
	 * @param obj データオブジェクト
	 * @return 作成した情報機器管理番号一覧
	 * @throws AppException
	 */
	private List<String> autoRegistApGetTanLineAst(String loginStaffCode, String accessLevel, ApGetTan obj) throws AppException {
		List<String> assetIdList = new ArrayList<String>();

		// 承認済の場合
		if(Function.nvl(obj.getApStatus(), "").equals(Constants.AP_STATUS_GET_TAN_APPROVE)) {
			User userData = masterSession.getStaff(null, obj.getApStaffCode());
			CodeName currentYearCodeName = masterSession.getCodeName(Constants.CATEGORY_CODE_CURRENT_YEAR, null, null, null);
			int currentYear = Integer.valueOf(currentYearCodeName.getValue1());

			List<ApGetTanLineAst> astLine = obj.getApGetTanLineAstList();
			if(astLine != null && astLine.size() > 0) {
				for(int i = 0; i < astLine.size(); i++) {
					ApGetTanLineAst astObj = astLine.get(i);
					// 自動登録指定明細
					if(Function.nvl(astObj.getAutoRegistFlag(), Constants.FLAG_NO).equals(Constants.FLAG_YES)
						&& Function.nvl(astObj.getNoRegistFlag(), Constants.FLAG_NO).equals(Constants.FLAG_NO)) {

						// 数量管理品ではない場合は自動登録を行わない。
						CodeName astCategory2 = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_CATEGORY2, astObj.getAstCategory2Code(), null, null);
						if(astCategory2 != null && !Function.nvl(astCategory2.getValue2(), Constants.FLAG_NO).equals(Constants.FLAG_YES)) continue;

						// 取得数と登録数の比較
						int qty = Function.nvl(astObj.getQuantity(), Integer.valueOf(0)).intValue();
						int regQty = Function.nvl(astObj.getRegistQuantity(), Integer.valueOf(0)).intValue();
						try {
							if(qty > regQty) { // 登録残がある場合自動登録する
								//////////////////////////////////// 登録申請作成
								Asset apAsset = assetSession.getApAssetByApGetTan(astObj.getApGetTanLineAstId());

								// 申請情報セット(取得申請と同じ値でセット)
								apAsset.setApDate(new Date());

								apAsset.setApCreateCompanyCode(obj.getApCreateCompanyCode());
								apAsset.setApCreateStaffCode(obj.getApCreateStaffCode());
								apAsset.setApCreateSectionCode(obj.getApCreateSectionCode());
								apAsset.setApCreateSectionYear(obj.getApCreateSectionYear());

								apAsset.setApCompanyCode(obj.getApCompanyCode());
								apAsset.setApStaffCode(obj.getApStaffCode());
								apAsset.setApSectionCode(obj.getApSectionCode());
								apAsset.setApSectionYear(obj.getApSectionYear());

								apAsset.setApTel(obj.getApTel());
								apAsset.setApOfficeName(userData.getOfficeName());

								// 固定値セット
								// 使用目的
								List<String> paramList = new ArrayList<String>();
								paramList.add(null); // value1
								paramList.add(Constants.FLAG_YES); // value2
								CodeName cn = masterSession.getCodeName(Constants.CATEGORY_CODE_ASSET_USE_PURPOSE, null, null, paramList);
								if(cn != null) apAsset.setHolPurposeCode(cn.getInternalCode());

								// OIR
								apAsset.setAstOir(Constants.OIR_DEFAULT); // 資産保有者がリース・レンタル以外の場合は登録時自動的にクリアされる。

								// 使用者セット(資産管理担当者と同じ値)
								User holUserData = masterSession.getStaff(obj.getHolCompanyCode(), obj.getHolStaffCode());
								if(holUserData != null) {
									apAsset.setUseStaffCode(holUserData.getStaffCode());
									apAsset.setUseStaffCompanyCode(holUserData.getCompanyCode());
									apAsset.setUseStaffSectionCode(holUserData.getSectionCode());
									apAsset.setUseStaffSectionYear(currentYear);
								}
								else {
									// 取得出来なかった場合は申請者情報をセットする。)
									apAsset.setUseStaffCode(obj.getApStaffCode());
									apAsset.setUseStaffCompanyCode(obj.getApCompanyCode());
									apAsset.setUseStaffSectionCode(obj.getApSectionCode());
									apAsset.setUseStaffSectionYear(obj.getApSectionYear());
								}

								// ステータスを承認済にセット
								apAsset.setApStatus(Constants.AP_STATUS_ASSET_APPROVE);

								String assetId = assetSession.createAsset(Constants.STAFF_CODE_SYSTEM, accessLevel, apAsset, true);

								//////////////////////////////////// 情報機器等作成
								apAsset = assetSession.getAsset(assetId, true);
								assetId = assetSession.createAsset(Constants.STAFF_CODE_SYSTEM, accessLevel, apAsset, false);

								assetIdList.add(assetId);
							}
						} catch (AppException e) {
							String errMessage = e.getMessage();
							errMessage = Msg.getBindMessage(Msg.ERR_MSG_ITEM, "資産(機器)明細[No" + astObj.getLineSeq() + "]", "情報機器等の自動登録時に以下のエラーが発生しました。") + errMessage;
							throw new AppException(errMessage);
						}
					}
				}
			}
		}

		return assetIdList;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#validateWarningApGetTan(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan)
	 */
	public String validateWarningApGetTan(String loginStaffCode, String accessLevel, ApGetTan obj) {
		StringBuffer errMsg = new StringBuffer();

		//////////////////////////////////// 金額範囲整合性確認
		if(Function.isAccessLevelAdmin(accessLevel) && !Function.nvl(obj.getApGetAmountRange(), "").equals("")) {
			// 取得金額範囲 != 取得金額
			CodeName amountRange = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_GET_AMOUNT_RANGE, obj.getApGetAmountRange(), obj.getApCompanyCode(), null);

			if(amountRange != null && obj.getGetTotalAmount() != null) {
				Long fromAmount = amountRange.getValue2() == null ? null : Long.valueOf(amountRange.getValue2());
				Long toAmount = amountRange.getValue3() == null ? null : Long.valueOf(amountRange.getValue3());

				if(fromAmount != null && fromAmount.longValue() > obj.getGetTotalAmount().longValue()) {
					errMsg.append("取得金額合計[" + new DecimalFormat("#,##0").format(obj.getGetTotalAmount()) + "]が取得金額範囲[" + obj.getApGetAmountRangeName() + "]外ですがよろしいですか？");
				} else if(toAmount != null && toAmount.longValue() < obj.getGetTotalAmount().longValue()) {
					errMsg.append("取得金額合計[" + new DecimalFormat("#,##0").format(obj.getGetTotalAmount()) + "]が取得金額範囲[" + obj.getApGetAmountRangeName() + "]外ですがよろしいですか？");
				}
			}
		}

		return errMsg.toString();
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#sendReoDlvStaff(jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void sendReoDlvStaff(ApGetTan obj){
		////////// メール送信
		CodeName mailTemplate = new CodeName();
		String from = "";
		String subject = "";
		List<String> toList = new ArrayList<String>();
		List<String> ccList = new ArrayList<String>();
		try {
			// 件名・本文取得
			mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_REO_ORDER_SET, obj.getReoOrderType(), obj.getApCompanyCode(), null);
			from = mailTemplate.getValue23();	//	FROMメールアドレス設定
			//	現場発注の場合
			if(Function.nvl(mailTemplate.getValue11(), "").equals(Constants.LE_PO_TYPE_SPOT)){
				//	申請者メールアドレス取得
				User apStaff = masterSession.getStaff(null, obj.getApStaffCode()); // 申請者
				if(apStaff != null && !Function.nvl(apStaff.getMailAddress(), "").equals("")){
					toList.add(apStaff.getMailAddress());	//	申請者メールアドレス設定
				}
			}
			else{
				if(!Function.nvl(mailTemplate.getValue14(),"").equals("")) toList.add(mailTemplate.getValue14());	//	宛先主管部メールアドレス設定
			}

			//	現場発注の場合、CCに追加
			if(Function.nvl(mailTemplate.getValue11(), "").equals(Constants.LE_PO_TYPE_SPOT)){
				//	資産管理担当者メールアドレスCC設定
				if(obj != null && !Function.nvl(obj.getReoAstMailAddress(), "").equals("")){
					ccList.add(obj.getReoAstMailAddress());
				}
				//	納品担当者メールアドレスCC設定
				if(obj != null && !Function.nvl(obj.getReoAstMailAddress(), "").equals("")){
					ccList.add(obj.getReoDlvMailAddress());
				}
			}

			subject = mailTemplate.getValue24();	//	件名
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_APPLICATION_ID, obj.getApplicationId());
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_LE_ESTIMATE_NUMBER, obj.getGetLeReEstimateNumber());
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_HOL_SECTION, obj.getHolSectionName()); // 保有部署
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_REO_DLV_STAFF, obj.getReoDlvStaffName()); // 納品担当者
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_HOL_STTAF, obj.getHolStaffName()); // 資産管理担当者
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AP_STAFF, obj.getApStaffName()); // 申請者
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_REO_DLV_MAIN_SECTION, mailTemplate.getValue12()); // 汎用マスタの部署名
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_REO_DLV_MAIN_STAFF, mailTemplate.getValue13()); // 汎用マスタの主管部担当者

			String body = mailTemplate.getValue25();	//	本文
			body = body.replaceAll(MAIL_TEMPLATE_VAR_APPLICATION_ID, obj.getApplicationId());
			body = body.replaceAll(MAIL_TEMPLATE_VAR_LE_ESTIMATE_NUMBER, obj.getGetLeReEstimateNumber());
			body = body.replaceAll(MAIL_TEMPLATE_VAR_HOL_SECTION, obj.getHolSectionName()); // 保有部署
			body = body.replaceAll(MAIL_TEMPLATE_VAR_REO_DLV_STAFF, obj.getReoDlvStaffName()); // 納品担当者
			body = body.replaceAll(MAIL_TEMPLATE_VAR_HOL_STTAF, obj.getHolStaffName()); // 資産管理担当者
			body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_STAFF, obj.getApStaffName()); // 申請者
			body = body.replaceAll(MAIL_TEMPLATE_VAR_REO_DLV_MAIN_SECTION, mailTemplate.getValue12()); // 汎用マスタの部署名
			body = body.replaceAll(MAIL_TEMPLATE_VAR_REO_DLV_MAIN_STAFF, mailTemplate.getValue13()); // 汎用マスタの主管部担当者

			// 送信
			sendMailSession.sendMail(from, toList, ccList, subject, body);

		} catch (Exception e) {
			//	メール送信失敗時
			toList = new ArrayList<String>();
			toList.add(from);
			subject = "納品担当者依頼メール送信処理失敗" + subject;
			StringBuffer body = new StringBuffer();
			body.append("申請書番号　納品担当者担当者\n");
			body.append("---------------------------------------------------------------\n");
			body.append(obj.getApplicationId() + "　" + obj.getReoDlvStaffCode() + "　" + obj.getReoDlvStaffName());
			body.append("\n");
			body.append("\n以下送信失敗時のエラーメッセージ\n");
			body.append(Function.getStackTraceStr(e));
			sendMailSession.sendMail(from, toList, null, subject, body.toString());
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#createOrdernPdf(java.util.List)
	 */
	public String createOrderPdf(String companyCode, String applicationId, String reoOrderType) throws AppException, IOException{
		PdfExporter exporter = createOrderBody(companyCode, applicationId, reoOrderType);
		if(exporter != null){
			return exporter.getFileId();
		}
		else{
			return null;
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#createOrdernPdf(java.util.List)
	 */
	public PdfExporter createOrderBody(String companyCode, String applicationId, String reoOrderType) throws AppException{
		// レポートパラメータ
		HashMap<String, Object> reportParam = new HashMap<String, Object>();

		reportParam.put("applicationId", applicationId);

		//	画像ファイル保存先ディレクトリ取得
		CodeName filePath = masterSession.getCodeName(Constants.CATEGORY_CODE_EASSET_PRINT_RESOURCE_PATH, null, null, null);
		//	画像ファイル名取得
		CodeName imageFile = masterSession.getCodeName(Constants.CATEGORY_CODE_REO_ORDER_SET, reoOrderType, companyCode, null);

		if(imageFile != null && !Function.nvl(imageFile.getValue6(), "").equals("")){
			reportParam.put("fromImage", filePath.getValue1() + "/" + imageFile.getValue6());
		}
		else{
			reportParam.put("fromImage", null);
		}
		// PDF生成
		PdfExporter exporter = new PdfExporter();

		try {
			exporter.exportPdf( "jp/co/ctcg/easset/report/ReOrder.jasper", reportParam);
			return exporter;
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "注文書印刷用データ作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.ApGetTanSession#sendReOrder(jp.co.ctcg.easset.dto.ap_get_tan.ApGetTan)
	 */
	public void sendReOrder(String loginStaffCode, String accessLevel, ApGetTan obj) throws AppException{
		////////// メール送信
		String from = "";
		List<String> toList = new ArrayList<String>();
		String subject = "";
		CodeName mailTemplate = new CodeName();
		ApGetTanDAO apGetTanDAO = new ApGetTanDAO();
		Long receptNumber = 0L;
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
		User loginStaff = masterSession.getStaff(null, loginStaffCode); // 処理実行者

		//	受付番号取得
		try {
			receptNumber = apGetTanDAO.selectMaxReoReceptNumber();
			//	初回以降に発注？
			if(receptNumber != null){
				obj.setReoReceptNumber(String.valueOf(receptNumber + 1));
			}
			//	初回発注？
			else{
				obj.setReoReceptNumber(df.format(new Date()) + StringUtils.leftPad("1", Constants.REO_RECEPT_LENGTH, "0"));
			}
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "受付番号取得"), e);
		}

		//	レンタル発注フラグ
		obj.setReoOrderFlag(Constants.FLAG_YES);
		//	発注日
		obj.setReoOrderDate(new Date());

		updateApGetTan(loginStaffCode, accessLevel, obj, false, false, true, HIST_OPERATION_RENTAL_ORDER);
		File[] file = new File[2];

		try {
			// 件名・本文取得
			mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_REO_ORDER_SET, obj.getReoOrderType(), obj.getApCompanyCode(), null);
			//	FROM
			from = mailTemplate.getValue26();
			//	宛先
			String toListStr = mailTemplate.getValue27();	//	発注先メールアドレス設定
			String[] toListStrArray = toListStr.split(",");
			for(int i = 0; i < toListStrArray.length; i++) {
				toList.add(toListStrArray[i]);
			}

			//	CC
			List<String> ccList = new ArrayList<String>();
			if(!Function.nvl(obj.getReoAstMailAddress(), "").equals("")) ccList.add(obj.getReoAstMailAddress());	//	資産管理担当者
			if(!Function.nvl(obj.getReoInvMailAddress(), "").equals("")) ccList.add(obj.getReoInvMailAddress());	//	請求書送付先担当者
			if(!Function.nvl(obj.getReoDlvMailAddress(), "").equals("")) ccList.add(obj.getReoDlvMailAddress());	//	納品先担当者
			if(!Function.nvl(mailTemplate.getValue14(),"").equals("")) ccList.add(mailTemplate.getValue14());	//	主管部納品担当者
			// 申請者
			User apStaff = masterSession.getStaff(null, obj.getApStaffCode());
			if(apStaff != null && !Function.nvl(apStaff.getMailAddress(), "").equals("")) ccList.add(apStaff.getMailAddress());
			//	件名
			subject = mailTemplate.getValue28();
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_ORDER_TO, mailTemplate.getValue5());
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_RECEPT_NUMBER, obj.getReoReceptNumber());
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_ORDER_NUMBER, obj.getApplicationId());
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_LE_ESTIMATE_NUMBER, obj.getGetLeReEstimateNumber());
			subject = subject.replaceAll(MAIL_TEMPLATE_VAR_HOL_SECTION, loginStaff.getSectionName());
			//	本文
			String body = mailTemplate.getValue29();
			body = body.replaceAll(MAIL_TEMPLATE_VAR_ORDER_TO, mailTemplate.getValue5());
			body = body.replaceAll(MAIL_TEMPLATE_VAR_RECEPT_NUMBER, obj.getReoReceptNumber());
			body = body.replaceAll(MAIL_TEMPLATE_VAR_ORDER_NUMBER, obj.getApplicationId());
			body = body.replaceAll(MAIL_TEMPLATE_VAR_LE_ESTIMATE_NUMBER, obj.getGetLeReEstimateNumber());
			body = body.replaceAll(MAIL_TEMPLATE_VAR_HOL_SECTION, loginStaff.getSectionName());

			//	注文書添付ファイル(注文書印刷ありの場合)
			if(Function.nvl(mailTemplate.getValue10(), "").equals(Constants.FLAG_YES)){
				PdfExporter exporter = createOrderBody(obj.getApCompanyCode(), obj.getApplicationId(), obj.getReoOrderType());
				File reOrderFile = null;
				if(exporter != null){
					reOrderFile = new File(exporter.getFile().getParentFile() + "/" + "レンタル注文書_" + obj.getApplicationId() + ".pdf");
					//	ファイルが存在する場合削除
					if (reOrderFile.exists()){
						reOrderFile.delete();
					}
					// boolean flag = exporter.getFile().renameTo(reOrderFile);
					file[0] = reOrderFile;
				}
			}

			//	請書
			//	請書あり？
			if(Function.nvl(mailTemplate.getValue8(), "").equals(Constants.FLAG_YES)){
				File reAck = null;
				// XLS生成
				ExcelExporter excelExporter = new ExcelExporter();
				// レポートパラメータ
				HashMap<String, Object> reportParam = new HashMap<String, Object>();
				reportParam.put("applicationId", obj.getApplicationId());
				reportParam.put("getLeReEstimateNumber", obj.getGetLeReEstimateNumber());
				reportParam.put("orderDate", obj.getReoOrderDate());
				reportParam.put("reoOrderTypeName", obj.getReoOrderTypeName());
				reportParam.put("managementFlag", mailTemplate.getValue9());
				excelExporter.exportExcel("jp/co/ctcg/easset/report/ReAck.jasper", reportParam);
				if(excelExporter != null){
					reAck = new File(excelExporter.getFile().getParentFile() + "/" + "レンタル請書_" + obj.getApplicationId() + ".xls");
					//	ファイルが存在する場合削除
					if (reAck.exists()){
						reAck.delete();
					}
					// boolean flag = excelExporter.getFile().renameTo(reAck);
					file[1] = reAck;
				}
			}

			// 送信
			sendMailSession.sendMail(from, toList, ccList, subject, body, file);
		} catch (Exception e) {
			//	メール送信失敗時
			toList = new ArrayList<String>();
			toList.add(from);
			subject = "発注メール送信処理失敗" + subject;
			StringBuffer body = new StringBuffer();
			body.append("申請書番号　レンタル会社\n");
			body.append("---------------------------------------------------------------\n");
			body.append(obj.getApplicationId() + "　" + obj.getGetLeReCompanyName());
			body.append("\n");
			body.append("\n以下送信失敗時のエラーメッセージ\n");
			body.append(Function.getStackTraceStr(e));
			sendMailSession.sendMail(from, toList, null, subject, body.toString());
		}

	}
	// メールテンプレート変数
	private static final String MAIL_TEMPLATE_VAR_AP_GET_TYPE = "\\$\\{取得形態\\}";
	private static final String MAIL_TEMPLATE_VAR_AP_STAFF_NAME = "\\$\\{申請者名\\}";
	private static final String MAIL_TEMPLATE_VAR_APPLICATION_NUMBER = "\\$\\{申請書番号\\}";
	private static final String MAIL_TEMPLATE_VAR_AST_NAME = "\\$\\{機器名\\}";
	private static final String MAIL_TEMPLATE_VAR_AST_CATEGORY = "\\$\\{機器分類\\}";
	private static final String MAIL_TEMPLATE_VAR_AST_QUANTITY = "\\$\\{取得数\\}";
	private static final String MAIL_TEMPLATE_VAR_AST_AUTO_REGIST = "\\$\\{自動登録有無\\}";
	private static final String MAIL_TEMPLATE_VAR_NO_REGIST = "\\$\\{登録区分\\}";
	private static final String MAIL_TEMPLATE_VAR_AST_SEAL = "\\$\\{シール発行有無\\}";
	private static final String MAIL_TEMPLATE_VAR_AST_EQUIPMENT = "\\$\\{備品・固定資産区分\\}";
	private static final String MAIL_TEMPLATE_VAR_LIC_MAKER = "\\$\\{メーカー\\}";
	private static final String MAIL_TEMPLATE_VAR_LIC_SOFTWARE = "\\$\\{ソフトウェア\\}";
	private static final String MAIL_TEMPLATE_VAR_LIC_QUANTITY = "\\$\\{取得数\\}";

	/**
	 * 取得申請承認報告メール送信
	 * @param applicationId 申請書番号
	 * @param oldApStatus 更新前申請ステータス
	 * @throws AppException
	 */
	private void sendApproveMail(String applicationId, String oldApStatus) throws AppException{

		ApGetTan apGetTan = getApGetTan(applicationId, false);

		if(apGetTan != null) {

			// 承認済み？
			if(!Function.nvl(oldApStatus, "").equals(Function.nvl(apGetTan.getApStatus(), "")) &&
			    Function.nvl(apGetTan.getApStatus(), "").equals(Constants.AP_STATUS_GET_TAN_APPROVE)) {

				// 件名・本文取得
				CodeName mailTemplate = masterSession.getCodeName(Constants.CATEGORY_CODE_AP_APPROVAL_GET_TAN, apGetTan.getApCompanyCode(), apGetTan.getApCompanyCode(), null);

				if(mailTemplate != null && !Function.nvl(mailTemplate.getDeleteFlag(), "").equals("1")) {

					List<String> toList = new ArrayList<String>();
					List<String> ccList = new ArrayList<String>();
					String from = Function.nvl(mailTemplate.getValue5(), "");	//	メール用固定アドレス取得
					List<String> bccList = new ArrayList<String>();
					String bcc = Function.nvl(mailTemplate.getValue6(), "");	//	メール用固定アドレス取得
					if(!bcc.equals("")) {
						bccList.add(bcc);
					}
					// 申請者
					String apSendFlag = Function.nvl(mailTemplate.getValue2(), "");
					String apStaffCode = Function.nvl(apGetTan.getApStaffCode(), "");
					String apStaffName = Function.nvl(apGetTan.getApStaffName(), "");
					if(apSendFlag.equals("1")) {
						User apStaff = masterSession.getStaff(null, apStaffCode);
						if(apStaff != null) {
							toList.add(apStaff.getMailAddress());
						}
					}
					// 起票者
					String apCreateSendFlag = Function.nvl(mailTemplate.getValue1(), "");
					String apCreateStaffCode = Function.nvl(apGetTan.getApCreateStaffCode(), "");
					if(apCreateSendFlag.equals("1")) {
						if(!apCreateStaffCode.equals(apStaffCode)) {
							User apCreateStaff = masterSession.getStaff(null, apCreateStaffCode);
							if(apCreateStaff != null) {
								ccList.add(apCreateStaff.getMailAddress());
							}
						}
					}
					// 登録申請担当者
					String apRegistSendFlag = Function.nvl(mailTemplate.getValue3(), "");
					String apRegistStaffCode = Function.nvl(apGetTan.getApRegistStaffCode(), "");
					if(apRegistSendFlag.equals("1")) {
						if(!apRegistStaffCode.equals(apStaffCode) &&
						   !apRegistStaffCode.equals(apCreateStaffCode)) {
							User apRegistStaff = masterSession.getStaff(null, apRegistStaffCode);
							if(apRegistStaff != null) {
								ccList.add(apRegistStaff.getMailAddress());
							}
						}
					}
					// 資産管理担当者
					String holSendFlag = Function.nvl(mailTemplate.getValue4(), "");
					String holStaffCode = Function.nvl(apGetTan.getHolStaffCode(), "");
					if(holSendFlag.equals("1")) {
						if(!holStaffCode.equals(apStaffCode) &&
						   !holStaffCode.equals(apCreateStaffCode) &&
						   !holStaffCode.equals(apRegistStaffCode)) {
							User holStaff = masterSession.getStaff(null, holStaffCode);
							if(holStaff != null) {
								ccList.add(holStaff.getMailAddress());
							}
						}
					}

					// 件名
					String subject = Function.nvl(mailTemplate.getValue7(), "");
					subject = subject.replaceAll(MAIL_TEMPLATE_VAR_AP_GET_TYPE, Function.nvl(apGetTan.getApGetTypeName(), ""));

					// 承認完了報告メール送信フラグ－機器明細の登録区分が「登録必要」が1件でもあればメールを送信する。
					boolean mailSendFlag = false;

					// 本文
					String body = "";
					// ヘッダー
					String heder = Function.nvl(mailTemplate.getValue8(), "");
					// 明細
					String detail = "";
					String pauseLine = "\n--------------------------------------------------------------------";
					List<ApGetTanLineAst> astList = apGetTan.getApGetTanLineAstList();
					if(astList != null && astList.size() > 0) {
						// 区切り線
						detail += "\n■資産(機器)明細";
						String astLineTemplate = Function.nvl(mailTemplate.getValue9(), "");
						for(int i = 0; i < astList.size(); i++) {
							ApGetTanLineAst row = astList.get(i);
							String astLine = "\n" + astLineTemplate;
							astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_AST_NAME, Function.nvl(row.getAstName(), ""));
							astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_AST_CATEGORY, Function.nvl(row.getAstCategory2Name(), ""));
							astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_AST_QUANTITY, Function.nvl(row.getQuantity(), ""));
							if(Function.nvl(row.getAutoRegistFlag(), "").equals("1")) {
								astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_AST_AUTO_REGIST, "自動登録");
							}
							else {
								astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_AST_AUTO_REGIST, "手動登録");
							}
							if(Function.nvl(row.getNoRegistFlag(), "").equals("1")) {
								astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_NO_REGIST, "登録不要");
							}
							else {
								astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_NO_REGIST, "登録必要");
								// 手動登録で登録必要な機器明細が有る場合はメールを送信する。
								if(!Function.nvl(row.getAutoRegistFlag(), "").equals("1")) {
									mailSendFlag = true;
								}
							}
							if(Function.nvl(row.getSealIssueFlag(), "").equals("1")) {
								astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_AST_SEAL, "シール発行必要");
							}
							else {
								astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_AST_SEAL, "シール発行不要");
							}
							if(Function.nvl(row.getEquipmentFlag(), "").equals("1")) {
								astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_AST_EQUIPMENT, "備品");
							}
							else {
								astLine = astLine.replaceAll(MAIL_TEMPLATE_VAR_AST_EQUIPMENT, "固定資産");
							}
							detail += pauseLine + astLine;
						}
						detail += pauseLine;
					}
					List<ApGetTanLineLic> licList = apGetTan.getApGetTanLineLicList();
					if(licList != null && licList.size() > 0) {
						// 区切り線
						detail += "\n\n■ライセンス明細";
						String licLineTemplate = Function.nvl(mailTemplate.getValue10(), "");
						for(int i = 0; i < licList.size(); i++) {
							ApGetTanLineLic row = licList.get(i);
							String licLine = "\n" + licLineTemplate;
							licLine = licLine.replaceAll(MAIL_TEMPLATE_VAR_LIC_MAKER, Function.nvl(row.getSoftwareMakerName(), ""));
							licLine = licLine.replaceAll(MAIL_TEMPLATE_VAR_LIC_SOFTWARE, Function.nvl(row.getSoftwareName(), ""));
							licLine = licLine.replaceAll(MAIL_TEMPLATE_VAR_LIC_QUANTITY, Function.nvl(row.getQuantity(), ""));

							detail += pauseLine + licLine;
						}
						detail += pauseLine;
					}
					// フッター
					String footer = Function.nvl(mailTemplate.getValue11(), "");

					body = heder + "\n" + detail + "\n" + footer;
					body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_STAFF_NAME, apStaffName);
					body = body.replaceAll(MAIL_TEMPLATE_VAR_APPLICATION_NUMBER, applicationId);
					body = body.replaceAll(MAIL_TEMPLATE_VAR_AP_GET_TYPE, Function.nvl(apGetTan.getApGetTypeName(), ""));

					if(mailSendFlag) {
						try {
							// 送信
							sendMailSession.sendMail(from, toList, ccList, bccList, subject, body);
						} catch (Exception e) {
							//	メール送信失敗時
							toList = new ArrayList<String>();
							toList.add(from);
							subject = "取得申請承認完了報告メール送信処理失敗" + subject;
							StringBuffer eBody = new StringBuffer();
							eBody.append("申請書番号\n");
							eBody.append("------------------------\n");
							eBody.append(applicationId);
							eBody.append("\n");
							eBody.append("\n以下送信失敗時のエラーメッセージ\n");
							eBody.append(Function.getStackTraceStr(e));
							sendMailSession.sendMail(from, toList, null, subject, body.toString());
						}
					}
				}
			}
		}
	}

}