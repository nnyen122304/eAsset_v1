/*===================================================================
 * ファイル名 : FldSessionBean.java
 * 概要説明   : 固定資産(照会・管理項目修正)セッションEJB定義
 * バージョン : 1.0
*====================================================================
 * 修正履歴
 * 日付       Ver. 担当者       修正内容
 * ---------- ---- ------------ -------------------------------------
 * 2010-10-06 1.0  リヨン           新規
 *=================================================================== */
package jp.co.ctcg.easset.session;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;

import jp.co.ctcg.easset.dao.LldDAO;
import jp.co.ctcg.easset.dto.asset.AssetSC;
import jp.co.ctcg.easset.dto.license.LicenseSC;
import jp.co.ctcg.easset.dto.lld.PpfsLld;
import jp.co.ctcg.easset.dto.lld.PpfsLldSC;
import jp.co.ctcg.easset.dto.lld.PpfsLldSR;
import jp.co.ctcg.easset.dto.ppfs_import.PpfsStat;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;

@Stateless
public class LldSessionBean implements LldSession {

	@Resource
	SessionContext context;

	@EJB
	MasterSession masterSession; // マスタセッション

	@EJB
	HistSession histSession; // 履歴セッション

	@EJB
	LicenseSession licenseSession; // ﾗｲｾﾝｽセッション

	@EJB
	AssetSession assetSession; // 情報機器セッション

	@EJB
	FldSession fldSession; // 固定資産セッション

	@EJB
	PpfsImportSession ppfsImportSession; // ProPlus取込セッション

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#searchFld(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.fld.FldSC)
	 */
	public List<PpfsLldSR> searchLld(String loginStaffCode, String accessLevel, String companyCode, PpfsLldSC searchParam) {
		try {
			LldDAO lldDao = new LldDAO();
			replacePluralSearchCondition(companyCode, searchParam);

			List<PpfsLldSR> lldSr = lldDao.selectLldList(loginStaffCode, accessLevel, companyCode, searchParam);

			if(lldSr != null){
				for(int i = 0; i < lldSr.size(); i++) {
					PpfsLldSR ppfsLld = lldSr.get(i);

					String leaseRentalHantei = masterSession.getLeaseRentalHantei(ppfsLld.getLatorihikikbnC(),ppfsLld.getBunruicdC());

					ppfsLld.setLeaseRentalHantei(leaseRentalHantei);

					lldSr.set(i,ppfsLld);
				}
			}

			return lldSr;
			//return lldDao.selectLldList(loginStaffCode, accessLevel, companyCode, searchParam);

		} catch (SQLException e) {
			//	固定資産検索処理に失敗しました。
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース/レンタル検索"), e);
		}

	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.LldSession#createLldCsv(java.lang.String, java.lang.String, java.lang.String, java.util.List, jp.co.ctcg.easset.dto.lld.PpfsLldSC, java.lang.String)
	 */
	public String createLldCsv(String loginStaffCode, String accessLevel, String companyCode, List<String> outputPropList, PpfsLldSC searchParam, String itemDef) {
		try {
			LldDAO lldDao = new LldDAO();
			replacePluralSearchCondition(companyCode, searchParam);

			//////////////////////////////////// 管理帳票の場合はカレント-履歴どちらを参照するかを判別
			boolean isHist = false;
			// 台帳・請求明細・スケジュールは過去履歴の場合有り
			if(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD.equals(itemDef)
					|| itemDef.startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD + " ")
					|| itemDef.startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PAY)
					|| itemDef.startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PL_SCH)
					|| itemDef.startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_BS_SCH)) {

				// 最新の取込年月取得
				String maxPeriod = "";
				List<PpfsStat> importStatList = ppfsImportSession.getPpfsStatList(companyCode, Constants.PPFS_IMPORT_DATA_TYPE_LLD);
				for(Iterator<PpfsStat> iter = importStatList.iterator(); iter.hasNext();) {
					PpfsStat stat = iter.next();
					if(stat.getLastSuccessCreateDate() != null) {
						maxPeriod = stat.getPeriod();
						break;
					}
				}

				// 最新の取込年月と検索条件の年月比較
				if(!maxPeriod.equals(searchParam.getReportPeriod())) {
					isHist = true;
				}
			}

			//////////////////////////////////// スケジュールの場合は基準年月・計算年数をパラ絵メータに設定
			if(itemDef.startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_PL_SCH)
				|| itemDef.startsWith(Constants.CATEGORY_CODE_ITEM_DEF_PPFS_LLD_BS_SCH)) {
				List<PpfsStat> importStatList = ppfsImportSession.getPpfsStatList(companyCode, Constants.PPFS_IMPORT_DATA_TYPE_LLD_SCH);
				for(Iterator<PpfsStat> iter = importStatList.iterator(); iter.hasNext();) {
					PpfsStat stat = iter.next();
					if(stat.getLastSuccessCreateDate() != null) {
						searchParam.setReportSchCalcBasePeriod(stat.getSchCalcBasePeriod());
						searchParam.setReportSchCalcYear(stat.getSchCalcYear());
						break;
					}
				}
			}

			////////////////////////////////////ファイル作成

			CsvWriterRowHandler handler = lldDao.createLldListCsv(loginStaffCode, accessLevel, companyCode, outputPropList, searchParam, itemDef, isHist);

			//////////////////////////////////// 操作ログ作成
			StringBuffer propStr = new StringBuffer();
			if(outputPropList != null) {
				for(int i = 0; i < outputPropList.size(); i++) {
					if(propStr.length() > 0) propStr.append(" ");
					propStr.append(outputPropList.get(i));
				}
			}
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_LLD_SEARCH, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",accessLevel:" + accessLevel + "," + Function.toString(searchParam) + ",itemDef:" + itemDef + ",outputProperty:" + propStr.toString());

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース/レンタル" + "ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース/レンタル" + "ダウンロード"), e);
		}
	}

	/**
	 * 検索条件の名称複数入力項目の名称をコードに変換する。
	 * @param searchParam
	 */
	private void replacePluralSearchCondition(String companyCode, PpfsLldSC searchParam) {
		// 種類（0padding）
		List<String> shuruinmAPluralList = Function.getPluralList(searchParam.getShuruinmAPlural());
		if(shuruinmAPluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < shuruinmAPluralList.size(); i++) {
				String row = Function.nvl(shuruinmAPluralList.get(i), "");
				if(!row.equals("")) {
					replaceStr.append(Function.paddingLeft(row, Constants.PPFS_MS01_CD_LEN, Constants.PPFS_MS_CD_PAD_CHAR) + " ");
				}
			}

			searchParam.setShuruinmAPlural(replaceStr.toString());
		}

		// 分類（0padding）
		List<String> bunruinmAPluralList = Function.getPluralList(searchParam.getBunruinmAPlural());
		if(bunruinmAPluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < bunruinmAPluralList.size(); i++) {
				String row = Function.nvl(bunruinmAPluralList.get(i), "");
				if(!row.equals("")) {
					replaceStr.append(Function.paddingLeft(row, Constants.PPFS_MS03_CD_LEN, Constants.PPFS_MS_CD_PAD_CHAR) + " ");
				}
			}

			searchParam.setBunruinmAPlural(replaceStr.toString());
		}

		// 代表設置場所（0padding）
		List<String> setchinmAPluralList = Function.getPluralList(searchParam.getSetchinmAPlural());
		if(setchinmAPluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < setchinmAPluralList.size(); i++) {
				String row = Function.nvl(setchinmAPluralList.get(i), "");
				if(!row.equals("")) {
					replaceStr.append(Function.paddingLeft(row, Constants.PPFS_MS07_CD_LEN, Constants.PPFS_MS_CD_PAD_CHAR) + " ");
				}
			}

			searchParam.setSetchinmAPlural(replaceStr.toString());
		}

		// 案件グループ（0padding）
		List<String> itemGroupNameAPluralList = Function.getPluralList(searchParam.getItemGroupNameAPlural());
		if(itemGroupNameAPluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < itemGroupNameAPluralList.size(); i++) {
				String row = Function.nvl(itemGroupNameAPluralList.get(i), "");
				if(!row.equals("")) {
					replaceStr.append(Function.paddingLeft(row, Constants.PPFS_GROUP_CD_LEN, Constants.PPFS_MS_CD_PAD_CHAR) + " ");
				}
			}

			searchParam.setItemGroupNameAPlural(replaceStr.toString());
		}

		// オフィス 名称⇒コード
		List<String> holOfficePluralList = Function.getPluralList(searchParam.getHolOfficePlural());
		if(holOfficePluralList.size() > 0) {
			StringBuffer replaceStr = new StringBuffer();
			for(int i = 0; i < holOfficePluralList.size(); i++) {
				if(!Function.nvl(holOfficePluralList.get(i), "").equals("")) {
					String internalCode = masterSession.getCodeNameIdByName(Constants.CATEGORY_CODE_OFFICE, companyCode, holOfficePluralList.get(i));
					if(!Function.nvl(internalCode, "").equals("")) {
						replaceStr.append(internalCode + " ");
					} else {
						replaceStr.append("--------NOT_EXISTS--------" + " ");
					}
				}
			}

			searchParam.setHolOfficePlural(replaceStr.toString());
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
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#getFld(java.lang.Long)
	 */
	public PpfsLld getLld(String loginStaffCode, String accessLevel, String companyCode, Long koyunoA) {
		try {
			LldDAO lldDao = new LldDAO();

			PpfsLld lld = lldDao.selectLld(koyunoA); // ヘッダの取得

			//	経費負担部課履歴を検索(補助台帳から検索する。)
			lld.setCostSecHistList(fldSession.getFldSupportList(koyunoA, Constants.PPFS_SUPPORT_COST_SEC_HIST));

			//	リース・レンタル契約に紐付く情報機器、ﾗｲｾﾝｽを取得
			if(!Function.nvl(lld.getShisanNumA(), "").equals("")){
				//	情報機器検索
				AssetSC searchParam = new AssetSC();
				searchParam.setShisanNumPlural(lld.getShisanNumA());
				searchParam.setHolCompanyCode(companyCode);
				lld.setAssetList(assetSession.searchAsset(loginStaffCode, accessLevel, searchParam, false));

				//	ﾗｲｾﾝｽ検索
				LicenseSC searchParam2 = new LicenseSC();
				searchParam2.setShisanNumPlural(lld.getShisanNumA());
				searchParam2.setHolCompanyCode(companyCode);
				lld.setLicenseList(licenseSession.searchLicense(loginStaffCode, accessLevel, searchParam2, false));
			}

			return lld;

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース/レンタル詳細取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#getFld(java.lang.Long)
	 */
	public PpfsLld getLldC(String loginStaffCode, String accessLevel, String companyCode, Long koyunoC) {
		try {
			LldDAO lldDao = new LldDAO();

			PpfsLld lld = lldDao.selectLldC(koyunoC); // ヘッダの取得

			//	経費負担部課履歴を検索(補助台帳から検索する。)
			lld.setCostSecHistList(fldSession.getFldSupportList(koyunoC, Constants.PPFS_SUPPORT_COST_SEC_HIST));

			//	リース・レンタル契約に紐付く情報機器、ﾗｲｾﾝｽを取得
			if(!Function.nvl(lld.getShisanNumA(), "").equals("")){
				//	情報機器検索
				AssetSC searchParam = new AssetSC();
				searchParam.setShisanNum(lld.getShisanNumA());
				searchParam.setHolCompanyCode(companyCode);
				lld.setAssetList(assetSession.searchAsset(loginStaffCode, accessLevel, searchParam, false));

				//	ﾗｲｾﾝｽ検索
				LicenseSC searchParam2 = new LicenseSC();
				searchParam2.setShisanNum(lld.getShisanNumA());
				searchParam2.setHolCompanyCode(companyCode);
				lld.setLicenseList(licenseSession.searchLicense(loginStaffCode, accessLevel, searchParam2, false));
			}

			return lld;

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "リース/レンタル詳細取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.FldSession#checkPpfsFldUpdate(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void checkPpfsLldUpdate(String loginStaffCode, String accessLevel, String companyCode, List<PpfsLldSR> obj) throws AppException{
		try{
			LldDAO lldDAO = new LldDAO();

			String koyunoAPlural = "";
			for(int i = 0; i < obj.size(); i++){
				PpfsLldSR item = obj.get(i);
				if(!Function.nvl(koyunoAPlural, "").equals("")){
					koyunoAPlural = koyunoAPlural + " " + item.getKoyunoA();
				}
				else{
					koyunoAPlural = String.valueOf(item.getKoyunoA());
				}
			}

			List<PpfsLldSR> result = lldDAO.selectPpfsLld(companyCode, koyunoAPlural);

			if(result != null){
				HashMap<Long, String> resultMap = new HashMap<Long, String>();
				// 検索結果を日付比較用に保持
				for(int i = 0; i < result.size(); i++) {
					PpfsLldSR ppfsLld  = result.get(i);
					resultMap.put(ppfsLld.getKoyunoA(), Function.nvl(ppfsLld.getUpdymdA(), "") + "_" + Function.nvl(ppfsLld.getUpdtimeA(), ""));
				}

				StringBuffer errMsg = new StringBuffer();
				for(int i = 0; i < obj.size(); i++){
					PpfsLldSR lld = obj.get(i);

					String ppfsYmdTime = resultMap.get(lld.getKoyunoA());

					if(ppfsYmdTime != null) {
						String eaYmdTime = Function.nvl(lld.getUpdymdA(), "") + "_" + Function.nvl(lld.getUpdtimeA(), "");

						if(!ppfsYmdTime.equals(eaYmdTime)) { // eAsset取込後にProPlus更新
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "eAssetの物件情報が最新ではありませんので、管理者にお問合せ下さい。(契約番号,契約枝番：" + lld.getKyknoA() + "," + lld.getNiniLd_17cdA() + ")"));
						}
					} else { // 対応するPPFS情報無し
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "eAssetの物件情報が最新ではありませんので、管理者にお問合せ下さい。(契約番号,契約枝番：" + lld.getKyknoA() + "," + lld.getNiniLd_17cdA() + ")"));
					}
				}

				// エラー有り
				if(errMsg.length() > 0) throw new AppException(errMsg.toString());
			}

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "物件取得"), e);
		}
	}


}