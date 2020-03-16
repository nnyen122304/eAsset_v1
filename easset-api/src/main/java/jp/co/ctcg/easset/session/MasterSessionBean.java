/*===================================================================
 * ファイル名 : MasterSessionBean.java
 * 概要説明   : マスタセッションEJB定義
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
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.servlet.http.HttpServletRequest;

import jp.co.ctcg.easset.dao.MasterDAO;
import jp.co.ctcg.easset.dto.AssetCategory;
import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.dto.CodeNameSet;
import jp.co.ctcg.easset.dto.CodeNameSetItem;
import jp.co.ctcg.easset.dto.CodeNameSetValid;
import jp.co.ctcg.easset.dto.LovDataEx;
import jp.co.ctcg.easset.dto.Project;
import jp.co.ctcg.easset.dto.RoleAdmin;
import jp.co.ctcg.easset.dto.RoleSection;
import jp.co.ctcg.easset.dto.Section;
import jp.co.ctcg.easset.dto.User;
import jp.co.ctcg.easset.dto.master.CompanyMasterData;
import jp.co.ctcg.easset.dto.master.DivisionMasterData;
import jp.co.ctcg.easset.dto.master.NewDivisionMasterData;
import jp.co.ctcg.easset.dto.master.SapAccountMasterData;
import jp.co.ctcg.easset.dto.master.SapAssignmentsMasterData;
import jp.co.ctcg.easset.dto.master.SapCustAccountsMasterData;
import jp.co.ctcg.easset.dto.master.SapExpDeptMasterData;
import jp.co.ctcg.easset.dto.master.SapVendorsMasterData;
import jp.co.ctcg.easset.dto.master.UserCompanyMasterData;
import jp.co.ctcg.easset.dto.master.UserMasterData;
import jp.co.ctcg.easset.flex_common.MsgManager;
import jp.co.ctcg.easset.template.session.AppSession;
import jp.co.ctcg.easset.template.utils.AppException;
import jp.co.ctcg.easset.template.utils.SysException;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.CsvWriterRowHandler;
import jp.co.ctcg.easset.util.FileUtility;
import jp.co.ctcg.easset.util.Function;
import jp.co.ctcg.easset.util.Msg;

import org.apache.commons.beanutils.PropertyUtils;

@Stateless
public class MasterSessionBean implements MasterSession {

	@Resource
	SessionContext context;

	@EJB
	AppSession appSession;

	@EJB
	HistSession histSession;

	private static final String CATEGORY_CODE_FILE_SAVE_ROOT_PATH = "FILE_SAVE_ROOT_PATH";

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getUserByAccount(javax.servlet.http.HttpServletRequest)
	 */
	public User getUserByAccount(HttpServletRequest httpRequest) {
		String account = httpRequest.getRemoteUser();
		if(Optional.ofNullable(account).orElse("").equals("")) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー情報の取得"));
		}
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectUserByAccount(account);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー情報の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#selectUsableCompanyList(java.lang.String)
	 */
	public List<CodeName> getUsableCompanyList(String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectUsableCompanyList(staffCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "利用可能な会社情報の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getRoleAdminList(java.lang.String, java.lang.String)
	 */
	public List<CodeName> getRoleAdminList(String staffCode, String companyCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectRoleAdminList(staffCode, companyCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "全社権限情報の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getRoleSectionList(java.lang.String, java.lang.String)
	 */
	public List<CodeName> getRoleSectionList(String staffCode, String companyCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectRoleSectionList(staffCode, companyCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "部署権限情報の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeNameList(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public List<CodeName> getCodeNameList(String categoryCode, String internalCode, String companyCode, List<String> values) {
		return getCodeNameList(categoryCode, internalCode, companyCode, values, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeNameList(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public List<CodeName> getCodeNameList(String categoryCode, String internalCode, String companyCode, List<String> values, boolean isCompanyFlag) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectCodeNameList(categoryCode, internalCode, companyCode, null, values, true, isCompanyFlag);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ情報の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeNameListByParent(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<CodeName> getCodeNameListByParent(String categoryCode, String parentInternalCode, String companyCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectCodeNameList(categoryCode, null, companyCode, parentInternalCode, null, true);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ情報の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeName(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public CodeName getCodeName(String categoryCode, String internalCode, String companyCode, List<String> values) {
		return getCodeName(categoryCode, internalCode, companyCode, values, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeName(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public CodeName getCodeName(String categoryCode, String internalCode, String companyCode, List<String> values, boolean isCompanyCode) {
		CodeName ret = null;

		List<CodeName> list = this.getCodeNameList(categoryCode, internalCode, companyCode, values, isCompanyCode);
		if(list != null && list.size() > 0) {
			ret = list.get(0); // １行目を戻す
		}

		return ret;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getAccessLevelList(java.lang.String, java.util.List)
	 */
	public List<CodeName> getAccessLevelList(String companyCode, List<String> roleCodeList) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectAccessLevelList(companyCode, roleCodeList);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "機能アクセスレベル情報の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchSection(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<Section> searchSection(String loginStaffCode, String accessLevel, String companyCode, String sectionName, Integer year) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectSectionList(loginStaffCode, accessLevel, companyCode, sectionName, null, year);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "人事部課検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getSection(java.lang.String, java.lang.String)
	 */
	public Section getSection(String companyCode, String sectionCode) {
		return getSection(companyCode, sectionCode, null);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getSection(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public Section getSection(String companyCode, String sectionCode, Integer year) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			List<Section> list = masterDao.selectSectionList(null, null, companyCode, null, sectionCode, year);

			Section ret = null;
			if(list != null && list.size() == 1) ret = list.get(0);

			return ret;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "人事部課検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchAssetCategory(java.lang.String, java.lang.String)
	 */
	public List<AssetCategory> searchAssetCategory(String categoryName, String selectedAssetCategory1) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.searchAssetCategory(categoryName, selectedAssetCategory1);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "資産(機器)分類検索"), e);
		}
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchStaff(java.lang.String, java.lang.String, java.lang.String, java.lang.Boolean)
	 */
	public List<User> searchStaff(String companyCode, String staffName, String sectionName, Boolean enableStaffOnly) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectStaffList(companyCode, staffName, sectionName, enableStaffOnly);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "社員検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getStaff(java.lang.String, java.lang.String)
	 */
	public User getStaff(String companyCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectStaff(companyCode, staffCode, false);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "社員検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getStaffValid(java.lang.String, java.lang.String)
	 */
	public User getStaffValid(String companyCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectStaff(companyCode, staffCode, true);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "社員検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchCostApproveStaff(java.lang.String, java.lang.String)
	 */
	public List<User> searchCostApproveStaff(String companyCode, String costSectionCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectCostApproveStaffList(companyCode, costSectionCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "課長検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchCostApproveStaff(java.lang.String, java.lang.String)
	 */
	public User getCostApproveStaff(String companyCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectCostApproveStaff(companyCode, staffCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "課長検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchHolApproveStaff(java.lang.String, java.lang.String)
	 */
	public List<User> searchHolApproveStaff(String companyCode, String holSectionCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectHolApproveStaffList(companyCode, holSectionCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "部長検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchHolApproveStaff(java.lang.String, java.lang.String)
	 */
	public User getHolApproveStaff(String companyCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectHoleApproveStaff(companyCode, staffCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "部長検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchCostSection(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	public List<Section> searchCostSection(String companyCode, String sectionName, Date searchDateFrom, Date searchDateTo) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectCostSectionList(companyCode, sectionName, searchDateFrom, searchDateTo);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCostSection(java.lang.String, java.lang.String, java.util.Date, java.util.Date)
	 */
	public Section getCostSection(String companyCode, String sectionCode, Date searchDateFrom, Date searchDateTo) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectCostSection(companyCode, sectionCode, searchDateFrom, searchDateTo);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#isMICostSection(java.lang.String, java.lang.String)
	 */
	public boolean isMICostSection(String companyCode, String sectionCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.isMICostSection(companyCode, sectionCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "NextMI経費負担部課検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getIdNextVal(java.lang.String)
	 */
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW) // 新規トランザクション
	public String nextValId(String idPrefix) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			Integer year = masterDao.getCurrentYear();

			int updCt = masterDao.updateIdSeq(idPrefix, year); // IDシーケンス更新

			if(updCt == 0) { // 更新対象が無い（年度で最初のアクセス）
				masterDao.insertIdSeq(idPrefix, year); // IDシーケンス作成
			}

			Long seq = masterDao.selectIdSeq(idPrefix, year); // シーケンス取得

			String nextVal = idPrefix + String.valueOf(year).substring(2) + String.format("%05d", seq);

			return nextVal;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "IDの採番"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#nextVal(java.lang.String)
	 */
	public Long nextVal(String seqName) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			return masterDao.selectNextVal(seqName);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "OracleIDの採番"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#fileCommit(java.lang.String, java.lang.String, java.lang.String)
	 */
	public File fileCommit(String fileIdTmp, String entityName, String id) {
		try {
			// ファイル保存先rootパス取得
			CodeName codeName = this.getCodeName(CATEGORY_CODE_FILE_SAVE_ROOT_PATH, null, null, null);
			String rootPath = codeName.getValue1();
			String tmpPath = codeName.getValue2();

			File dir = null;
			if(entityName.indexOf("BULK_UPDATE") >= 0) { // 一括更新
				dir = new File(rootPath + "/" + entityName + "/" + id.substring(0, 6) + "/" + id);
			} else { // 一括更新以外
				dir = new File(rootPath + "/" + entityName + "/" + id.substring(0, id.length() - 2) + "/" + id);
			}

			dir.mkdirs(); // ディレクトリ作成

			File tmpFile = new File(tmpPath + "/" + fileIdTmp);
			File commitFile = new File(dir + "/" + fileIdTmp);

			boolean ret = FileUtility.copy(tmpFile, commitFile, true);

			if(!ret) throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ファイル保存先の確定"));

			return commitFile;
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ファイル保存の確定"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getLovDate(java.lang.String, java.util.Map, java.lang.String)
	 */
	public LovDataEx getLovData(String sqlName, Map<String,Object> constParamMap, String code) {

		constParamMap.put("code", code); // キー条件セット

		List<LovDataEx> list = searchLovList(sqlName, constParamMap);

		LovDataEx ret = null;
		if(list != null && list.size() == 1) ret = list.get(0); // 値が一件だけ存在した場合のみ戻り値セット

		return ret;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeNameIdByName(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getCodeNameIdByName(String categoryCode, String companyCode,  String value1) {
		return getCodeNameIdByName(categoryCode, companyCode, value1, false);
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeNameIdByName(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getCodeNameIdByName(String categoryCode, String companyCode,  String value1, boolean isCompanyFlag ) {
		List<String> valueParamList = new ArrayList<String>();
		valueParamList.add(value1);

		CodeName codeName = getCodeName(categoryCode, null, companyCode, valueParamList, isCompanyFlag);

		String ret = null;

		if(codeName != null) ret = codeName.getInternalCode();

		return ret;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeNameParentIdByName(java.lang.String, java.lang.String, java.lang.String)
	 */
	public String getCodeNameParentIdByName(String categoryCode, String companyCode,  String value1) {
		List<String> valueParamList = new ArrayList<String>();
		valueParamList.add(value1);

		CodeName codeName = getCodeName(categoryCode, null, companyCode, valueParamList);

		String ret = null;

		if(codeName != null) ret = codeName.getParentInternalCode();

		return ret;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#createOldEaSession(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String createOldEaSession(String loginAccount, String companyCode, String roleCode, String urlPath) {
		CodeName cn = getCodeName(Constants.CATEGORY_CODE_OUTER_SITE_URL, null, null, null);
		String url = cn.getValue1() + urlPath;

		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		String sessionId;
		try {
			String oldRoleCode;
			// 新旧ロールコードマッピング
			if(roleCode.equals("B01")) { // 資産管理担当者
				oldRoleCode = "4";
			} else if(roleCode.equals("B02")) { // 部署長
				oldRoleCode = "3";
			} else if(roleCode.equals("A02")) { // 資産管理者(財経)
				oldRoleCode = "5";
			} else if(roleCode.startsWith("A") || roleCode.startsWith("S")){ // 管理者権限
				if(companyCode.equals("001")) { // CTC
					oldRoleCode = "1";
				} else { // CTC以外
					oldRoleCode = "2";
				}
			} else { // 一般
				oldRoleCode = "5";
			}

			sessionId = masterDao.insertSession(loginAccount, companyCode, oldRoleCode, url);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "旧eAssetセッション生成"), e);
		}

		url += "&neaSessionId=" + sessionId;

		return url;
	}


	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getDownloadItemList(java.lang.String, java.lang.String)
	 */
	public List<CodeName> getDownloadItemList(String itemDefName, String lineItem) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectDownloadItemList(itemDefName, lineItem);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ダウンロード項目の取得"), e);
		}
	}

	/*
	 *(非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchRoleAdmin(java.lang.String, java.lang.String, java.lang.String)
	 */
	public  List<RoleAdmin> searchRoleAdmin(String companyCode, String roleCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			return masterDao.selectRoleAdmin(companyCode, roleCode, staffCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー権限_全社権限の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchRoleSection(java.lang.String, java.lang.String, java.lang.String)
	 */
	public  List<RoleSection> searchRoleSection(String companyCode, String sectionCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			return masterDao.selectRoleSection(companyCode, sectionCode, staffCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー権限_部署(資産管理担当者)権限の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#createRoleAdmin(java.lang.String, jp.co.ctcg.easset.dto.RoleAdmin, )
	 */
	public void createRoleAdmin(String loginStaffCode, String companyCode, RoleAdmin roleAdmin) throws AppException{
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {

			StringBuffer errMsg = new StringBuffer();
			//////////////////////////////////// 項目定義バリデーション
			int status = 1;

			// ヘッダ
			errMsg.append(validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_ROLE_ADMIN, "NEA_USER_ROLE_ADMIN", roleAdmin, status, null));

			//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)
			// 権限者
			if(!Function.nvl(roleAdmin.getStaffCode(), "").equals("")) {
				if(getStaff(null, roleAdmin.getStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "権限者"));
				}
			}

			if(errMsg.length() == 0){
				//	同じ権限チェック
				List<RoleAdmin> list = searchRoleAdmin(companyCode, roleAdmin.getRoleCode(), roleAdmin.getStaffCode());
				if(list.size() > 0){
					errMsg.append("その社員の同権限が既に設定されています。");
				}
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 登録
			masterDao.insertRoleAdmin(companyCode, roleAdmin);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_ROLE_SETTING_ADMIN, Constants.COM_OP_OPERATION_CREATE, Function.toString(roleAdmin));
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー権限_全社権限の作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#createRoleSection(java.lang.String, java.lang.String, jp.co.ctcg.easset.dto.RoleSection)
	 */
	public void createRoleSection(String loginStaffCode, String companyCode, RoleSection roleSection) throws AppException{
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {

			StringBuffer errMsg = new StringBuffer();
			//////////////////////////////////// 項目定義バリデーション
			int status = 1;

			// ヘッダ
			errMsg.append(validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_ROLE_SECTION, "NEA_USER_ROLE_SECTION", roleSection, status, null));

			//////////////////////////////////// マスタ整合性バリデーション(コードを手入力できる項目のみに限定)
			// 権限者
			if(!Function.nvl(roleSection.getStaffCode(), "").equals("")) {
				if(getStaff(companyCode, roleSection.getStaffCode()) == null) {
					errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, "権限者"));
				}
			}

			if(errMsg.length() == 0){
				//	同じ権限チェック
				List<RoleSection> list = searchRoleSection(companyCode, roleSection.getSectionCode(), roleSection.getStaffCode());
				if(list.size() > 0){
					errMsg.append("その社員の同権限が既に設定されています。");
				}
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 登録
			masterDao.insertRoleSection(companyCode, roleSection);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_ROLE_SETTING_SECTION, Constants.COM_OP_OPERATION_CREATE, Function.toString(roleSection));
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー権限_部署(資産管理担当者)権限の作成"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#deleteRoleAdmin(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void deleteRoleAdmin(String loginStaffCode, String companyCode, String roleCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			//////////////////////////////////// 登録
			masterDao.deleteRoleAdmin(companyCode, roleCode, staffCode);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_ROLE_SETTING_ADMIN, Constants.COM_OP_OPERATION_DELETE, "companyCode:" + companyCode + ",roleCode:" + roleCode + ",staffCode:" + staffCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー権限_全社権限の削除"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#deleteRoleSection(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void deleteRoleSection(String loginStaffCode, String companyCode, String sectionCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			//////////////////////////////////// 登録
			masterDao.deleteRoleSection(companyCode, sectionCode, staffCode);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_ROLE_SETTING_SECTION, Constants.COM_OP_OPERATION_DELETE, "companyCode:" + companyCode + ",sectionCode:" + sectionCode + ",staffCode:" + staffCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー権限_部署(資産管理担当者)権限の削除"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#createRoleSectionCsv(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String createRoleSectionCsv(String loginStaffCode, String companyCode, String sectionCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = masterDao.createRoleSectionListCsv(companyCode, sectionCode, staffCode);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_ROLE_SETTING_SECTION, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",companyCode:" + companyCode + ",sectionCode=" + sectionCode + ",staffCode:" + staffCode);

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー権限_部署(資産管理担当者)権限" + "ダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー権限_部署(資産管理担当者)権限" + "ダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchSectionRoleProfile(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public  List<RoleSection> searchSectionRoleProfile(String loginStaffCode, String accessLevel, String companyCode, String sectionCode, String staffCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			return masterDao.selectSectionRoleProfileList(loginStaffCode, accessLevel, companyCode,sectionCode, staffCode, false);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "資産管理担当者プロファイルの取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#updateRoleSection(java.lang.String, jp.co.ctcg.easset.dto.RoleSection)
	 */
	public void updateSectionRoleProfile(String loginStaffCode, String accessLevel, RoleSection roleSection) throws AppException{
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			StringBuffer errMsg = new StringBuffer();
			//////////////////////////////////// 項目定義バリデーション
			int status = 1;

			// ヘッダ
			errMsg.append(validateItemDef(Constants.CATEGORY_CODE_ITEM_DEF_ROLE_SECTION_PROFILE, "NEA_USER_ROLE_SECTION", roleSection, status, null));

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 固定値セット
			//	更新者・更新日
			Date sysdate = new Date();
			roleSection.setUpdateStaffCode(loginStaffCode);
			roleSection.setUpdateDate(sysdate);

			//////////////////////////////////// 更新
			masterDao.updateSectionRoleProfile(loginStaffCode, roleSection);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_SECTION_ROLE_PROFILE, Constants.COM_OP_OPERATION_UPDATE, Function.toString(roleSection));
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "資産管理担当者プロファイル更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#validateItemDef(java.lang.String, java.lang.String, java.lang.Object, int)
	 */
	public String validateItemDef(String itemDefName, String entityName, Object obj, int status, Set<String> updatePropSet) {
		StringBuffer errMsg = new StringBuffer();

		try {
			List<String> valueParamList = new ArrayList<String>();
			valueParamList.add(entityName);
			List<CodeName> itemDefList = getCodeNameList(itemDefName, null, null, valueParamList);

			for(int i = 0; i < itemDefList.size(); i++) {
				CodeName itemDef = itemDefList.get(i);

				String columnName = itemDef.getValue2(); // カラム名
				String propertyName = itemDef.getValue3(); // プロパティ名
				if(propertyName == null) continue;
				//	一括更新？と一括更新項目対象？
				if(updatePropSet != null && !updatePropSet.contains(propertyName)){
					continue;
				}

				Object value = null;
				try {
					value = PropertyUtils.getProperty(obj, propertyName); // 入力値
				} catch(NoSuchMethodException e) {
					continue;
				}

				Object lineSeq = null;
				try {
					lineSeq = PropertyUtils.getProperty(obj, "lineSeq"); // 明細の場合はNo
				} catch(NoSuchMethodException e) {
				}


				String propertyLogicalName = Function.nvl(itemDef.getValue5(), ""); // プロパティ論理名

				if(!propertyLogicalName.equals("社員番号")) {
					propertyLogicalName = propertyLogicalName.replaceAll("社員番号", "").replaceAll("部署コード", "部署").replaceAll("部課コード", "部課");
				}

				if(itemDef.getDescription() != null) {
					propertyLogicalName = itemDef.getDescription() + (lineSeq == null ? "" : "[No" + lineSeq.toString() + "]") + "-" + propertyLogicalName; // 論理名のカテゴリ
				}

				Integer maxByte = itemDef.getValue8() == null ? null : Integer.valueOf(itemDef.getValue8()); // 最大サイズ
				Long minValue = itemDef.getValue9() == null ? null : Long.valueOf(itemDef.getValue9()); // 最小値
				Long maxValue = itemDef.getValue10() == null ? null : Long.valueOf(itemDef.getValue10()); // 最大値
				String restrict = itemDef.getValue11(); // フォーマット

				String statValid = Function.nvl((String) PropertyUtils.getProperty(itemDef, "value" + ((Constants.ITEM_DEF_STAT_POS - 1) + status)), "");

				if(!statValid.equals(Constants.ITEM_DEF_READ_ONLY)) { // 項目が入力可能な場合のみチェック
					// 文字数チェック
					if(maxByte != null && value != null) {
						if(value instanceof String) {
							if(((String) value).getBytes(Constants.DB_CHARSET).length > maxByte) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MAX_SIZE, propertyLogicalName, new DecimalFormat().format(maxByte)));
							}
						} else if(value instanceof Number) {
							if(Function.getNumberSize((Number) value) > maxByte) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MAX_SIZE, propertyLogicalName, new DecimalFormat().format(maxByte)));
							}
						}
					}

					// 最小値チェック
					if(minValue != null && value != null) {
						try {
							long longValue;
							if(value instanceof Number) {
								longValue = ((Number) value).longValue();
							} else {
								longValue = Long.valueOf(value.toString());
							}
							if(minValue.compareTo(longValue) > 0) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MIN_VALUE, propertyLogicalName, new DecimalFormat().format(minValue)));
							}
						} catch (NumberFormatException e) {}
					}

					// 最大値チェック
					if(maxValue != null && value != null) {
						try {
							long longValue;
							if(value instanceof Number) {
								longValue = ((Number) value).longValue();
							} else {
								longValue = Long.valueOf(value.toString());
							}
							if(maxValue.compareTo(longValue) < 0) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MAX_VALUE, propertyLogicalName, new DecimalFormat().format(maxValue)));
							}
						} catch (NumberFormatException e) {}
					}

					// 正規表現チェック
					if(restrict != null && !Function.nvl(value, "").equals("")) {
						if(!value.toString().matches("[" + restrict + "]*")) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, propertyLogicalName));
						}
					}

					// 必須チェック
					if(columnName != null && statValid.equals(Constants.ITEM_DEF_REQUIRED)) {
						if(Function.nvl(value, "").equals("")) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, propertyLogicalName));
						}
					}
				}
			}

			return errMsg.toString();
		} catch (IllegalAccessException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "項目制御値取得"), e);
		} catch (InvocationTargetException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "項目制御値取得"), e);
		} catch (NoSuchMethodException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "項目制御値取得"), e);
		} catch (UnsupportedEncodingException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "項目チェック（最大文字数）"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getAccessLevel(java.lang.String, java.lang.String, java.lang.String, java.lang.String, int)
	 */
	public String getAccessLevel(String menuId, String staffCode, String companyCode, String sectionCode, int sectionYear) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectAccessLevel(menuId, staffCode, companyCode, sectionCode, sectionYear);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "アクセス権限情報の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getProject(java.lang.String)
	 */
	public Project getProject(String projectCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectProject(projectCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "プロジェクト情報取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#updateCodeName(java.lang.String, jp.co.ctcg.easset.dto.CodeName)
	 */
	public void updateCodeName(String loginStaffCode, CodeName codeName) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			masterDao.updateCodeName(loginStaffCode, codeName);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "コードネーム更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchCodeNameSet(java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<CodeNameSet> searchCodeNameSet(String loginStaffCode, String companyCode, String categoryName, List<String> roleCodeList) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			return masterDao.selectCodeNameSet(loginStaffCode, companyCode, categoryName, roleCodeList);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ設定検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeNameSetItemList(java.lang.String)
	 */
	public List<CodeNameSetItem> getCodeNameSetItemList(String categoryCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			List<CodeNameSetItem> codeNameSetItemList = masterDao.selectCodeNameSetItem(categoryCode);
			return codeNameSetItemList;
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ設定検索"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchCodeNameList(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public List<CodeName> searchCodeNameList(String categoryCode, String internalCode, String companyCode, List<String> values, boolean isEnableOnly) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();

		try {
			return masterDao.selectCodeNameItemList(categoryCode, internalCode, companyCode, values, isEnableOnly);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ情報の取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#updateCodeNameList(java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void updateCodeNameList(String loginStaffCode, String categoryCode, String companyCode, List<CodeName> codeNameList) throws AppException {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			if(codeNameList == null) codeNameList = new ArrayList<CodeName>();

			//////////////////////////////////// バリデーション
			StringBuffer errMsg = new StringBuffer();

			// valueの必須・文字数・正規表現チェック
			List<CodeNameSetItem> codeNameSetItemList = getCodeNameSetItemList(categoryCode);
			DecimalFormat sortDf = new DecimalFormat();
			sortDf.setGroupingUsed(false);
			for(int i = 0; i < codeNameList.size(); i++){
				CodeName codeName = codeNameList.get(i);
				for(int j = 0; j < 60; j++) { // value1 ～ value60
					if(j >= codeNameSetItemList.size()) break;

					String value = (String) PropertyUtils.getProperty(codeName, "value" + (j + 1));
					CodeNameSetItem setItem = codeNameSetItemList.get(j);

					String dispPropName = "[" + (i + 1) + "行目";

					if(codeName.getSortNumber() != null) dispPropName += " (ソート値 " + sortDf.format(codeName.getSortNumber()) + ")";
					if(codeName.getSortNumber() == null && !Function.nvl(codeName.getInternalCode(), "").equals("")) dispPropName += " (コード " + codeName.getInternalCode() + ")";
					dispPropName += "]";
					dispPropName += setItem.getItemName();

					// 必須
					if(Function.nvl(setItem.getRequired(), "").equals("true")) {
						if(Function.nvl(value, "").equals("")) {
							errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_REQUIRED, dispPropName));
						}
					}

					// 文字数
					long maxByte = Constants.MAX_CHAR_SIZE_CODE_NAME_VALUE;
					if(setItem.getMaxChars() != null) maxByte = setItem.getMaxChars().longValue();
					if((Function.nvl(value, "").getBytes(Constants.DB_CHARSET).length > maxByte)) {
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_MAX_SIZE, dispPropName, new DecimalFormat().format(maxByte)));
					}

					// 正規表現
					if(!Function.nvl(setItem.getRestrict(), "").equals("")) {
						if(!Function.nvl(value, "").equals("")) {
							if(!value.toString().matches("[" + setItem.getRestrict() + "]*")) {
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG_ITEM_RESTRICT, dispPropName));
							}
						}
					}
				}
			}

			//	同じコードの場合、エラー
			Map<String, Object> errorInternalCodeList = new HashMap<String, Object>();
			for(int i = 0; i < codeNameList.size(); i++){
				CodeName codeName = codeNameList.get(i);
				for(int j = 0; j < codeNameList.size(); j++){
					CodeName codeName2 = codeNameList.get(j);
					if(i != j
					&& codeName.getInternalCode().equals(codeName2.getInternalCode())
					&& Function.nvl(errorInternalCodeList.get(codeName2.getInternalCode()), "").equals("")
					){
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "コード：" + codeName.getInternalCode() + "が重複しているため、保存できません。"));
						errorInternalCodeList.put(codeName2.getInternalCode(), codeName2.getInternalCode());
						break;
					}
				}
			}

			//	既存のマスターデータを取得
			List<CodeName> codeNameListOld = searchCodeNameList(categoryCode, null, companyCode, null, false);
			//	差分チェック
			for(int i=0; i< codeNameListOld.size(); i++){
				CodeName codeNameOld = codeNameListOld.get(i);

				boolean flag = false;
				CodeName codeName = new CodeName();
				for(int j = 0; j < codeNameList.size(); j++){
					codeName = codeNameList.get(j);
					//	削除対象チェック
					if(codeName.getInternalCode().equals(codeNameOld.getInternalCode())){
						flag = true;
						break;
					}
				}
				//	削除対象?
				if(!flag){
					//	汎用マスタ設定バリデーション項目取得
					List<CodeNameSetValid> codeNameSetValidList = masterDao.selectCodeNameSetValid(categoryCode);
					if(codeNameSetValidList != null && codeNameSetValidList.size() > 0){
						for(int k = 0; k < codeNameSetValidList.size(); k++){
							CodeNameSetValid codeNameSetValid = codeNameSetValidList.get(k);
							Object validCheck = masterDao.selectCodeNameSetValidCheck(codeNameSetValid.getValidTable(), codeNameSetValid.getValidColumn() , codeNameOld.getInternalCode());
							//	1件以上はエラー
							if(validCheck != null){
								errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, "コード：" + codeNameOld.getInternalCode() + "は、" + codeNameSetValid.getValidTableName() + "の" + codeNameSetValid.getValidColumnName() + "で使用されているため、削除できません。再度、開き直して編集してください。"));
							}
						}
					}
				}
			}

			if(errMsg.length() > 0) { // バリデーションエラー有
				throw new AppException(errMsg.toString());
			}

			//////////////////////////////////// 既存データ削除
			//	汎用マスタ一覧削除
			masterDao.deleteCodeName(companyCode, categoryCode);

			//////////////////////////////////// データ追加
			int sortNumber = 0;

			for(int i = 0; i< codeNameList.size(); i++){
				CodeName codeName = codeNameList.get(i);
				//	更新者・更新日セット.新規の場合、作成者・作成日もセット
				Date sysdate = new Date(); // システム日付取得
				codeName.setUpdateDate(sysdate);
				codeName.setUpdateStaffCode(loginStaffCode);
				if(Function.nvl(codeName.getCreateStaffCode(), "").length() == 0){
					codeName.setCreateDate(sysdate);
					codeName.setCreateStaffCode(loginStaffCode);
				}

				//	ソート順の振りなおし(10単位)
				sortNumber = sortNumber + 10;
				codeName.setSortNumber(sortNumber);

				//	汎用マスタ作成
				masterDao.insertCodeName(codeName);

			}

			//	汎用マスタ設定の更新者、更新日を更新
			masterDao.updateCodeNameSet(loginStaffCode, categoryCode);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_CODE_NAME_SETTING, Constants.COM_OP_OPERATION_UPDATE, "categoryCode:" + categoryCode + ",companyCode:" + companyCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ設定更新"), e);
		} catch (IllegalAccessException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ設定更新"), e);
		} catch (InvocationTargetException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ設定更新"), e);
		} catch (NoSuchMethodException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ設定更新"), e);
		} catch (UnsupportedEncodingException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタ設定更新"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#createCodeNameCsv(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public String createCodeNameCsv(String loginStaffCode, String categoryCode, String companyCode, String setCompanyCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try{
			//////////////////////////////////// ファイル作成
			CsvWriterRowHandler handler = masterDao.createCodeNameListCsv(categoryCode, companyCode, setCompanyCode);

			//////////////////////////////////// 操作ログ作成
			histSession.createOpLog(loginStaffCode, Constants.COM_OP_FUNCTION_CODE_NAME_SETTING, Constants.COM_OP_OPERATION_DOWNLOAD, "rowCount:" + handler.getRowCount() + ",categoryCode:" + categoryCode + ",companyCode:" + companyCode + ",setCompanyCode=" + setCompanyCode);

			return handler.getFileId();
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタダウンロード"), e);
		} catch (IOException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getCodeNameUseStatus(jp.co.ctcg.easset.dto.CodeName)
	 */
	public String getCodeNameUseStatus(CodeName obj) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try{
			StringBuffer errMsg = new StringBuffer();

			//	汎用マスタ設定バリデーション項目取得
			List<CodeNameSetValid> codeNameSetValidList = masterDao.selectCodeNameSetValid(obj.getCategoryCode());
			if(codeNameSetValidList != null && codeNameSetValidList.size() > 0){
				for(int k = 0; k < codeNameSetValidList.size(); k++){
					CodeNameSetValid codeNameSetValid = codeNameSetValidList.get(k);
					Object validCheck = masterDao.selectCodeNameSetValidCheck(codeNameSetValid.getValidTable(), codeNameSetValid.getValidColumn() , obj.getInternalCode());
					//	1件以上はエラー
					if(validCheck != null){
						errMsg.append(Msg.getBindMessage(Msg.ERR_MSG, codeNameSetValid.getValidTableName() + "の" + codeNameSetValid.getValidColumnName() + "で使用されているため、削除できません。"));
					}
				}
			}

			return errMsg.toString();

		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "汎用マスタダウンロード"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getPpfsCurrentPeriod(java.lang.String)
	 */
	public String getPpfsCurrentPeriod(String companyCode) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try{
			return masterDao.getPpfsCurrentPeriod(companyCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ProPlus会計年月取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getUserRoleSectionAllList(java.lang.String, java.lang.String, java.lang.Integer)
	 */
	public List<RoleSection> getUserRoleSectionAllList(String companyCode, String sectionCode, Integer sectionYear) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try{
			return masterDao.selectUserRoleSectionAllList(companyCode, sectionCode, sectionYear);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "資産管理担当者・部署長情報取得"), e);
		}
	}

	public String searchParentSectionCode(String companyCode, String sectionCode, Integer sectionYear){
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try{
			return masterDao.selectParentSectionCode(companyCode, sectionCode, sectionYear);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "親部署コード取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#sarchAstName(java.lang.String, java.lang.String, java.lang.String, java.util.Date, java.lang.String, java.lang.String)
	 */
	public List<LovDataEx> sarchAstName(String parentCategoryCode, String parentInternalCode, String companyCode, Date sysdate, String makerName, String astName) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try{
			return masterDao.selectAstName(parentCategoryCode, parentInternalCode, companyCode, sysdate, makerName, astName);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "資産機器情報取得"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#updateAccessControl(java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void updateAccessControl(String loginStaffCode, String companyCode, String menuCode, String publicCode){
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try{
			masterDao.updateAccessControl(loginStaffCode, companyCode, menuCode, publicCode);
		} catch (SQLException e) {
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "メニュー制御更新処理"), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#insertCompanyMaster(java.util.List)
	 */
	public void insertCompanyMaster(List<CompanyMasterData> companyList){
		if(companyList != null && companyList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				masterDao.deleteMasterData("COMPANYMASTERTABLE");

				for(int i = 0; i < companyList.size(); i ++){
					CompanyMasterData obj = (CompanyMasterData)companyList.get(i);
					masterDao.insertCompanyMaster(obj);
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "会社マスタデータ登録処理"), e);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#insertDivisionMaster(java.util.List)
	 */
	public void insertDivisionMaster(List<DivisionMasterData> divisionList){
		if(divisionList != null && divisionList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				masterDao.deleteMasterData("DIVISIONMASTERTABLE");

				for(int i = 0; i < divisionList.size(); i ++){
					DivisionMasterData obj = (DivisionMasterData)divisionList.get(i);
					masterDao.insertDivisionMaster(obj);
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "部署マスタデータ登録処理"), e);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#insertNewDivisionMaster(java.util.List)
	 */
	public void insertNewDivisionMaster(List<NewDivisionMasterData> newDivisionList){
		if(newDivisionList != null && newDivisionList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				masterDao.deleteMasterData("NEWDIVISIONMASTERTABLE");

				for(int i = 0; i < newDivisionList.size(); i ++){
					NewDivisionMasterData obj = (NewDivisionMasterData)newDivisionList.get(i);
					masterDao.insertNewDivisionMaster(obj);
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "組織マスタデータ登録処理"), e);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#insertUserCompanyMaster(java.util.List)
	 */
	public String insertUserCompanyMaster(List<UserCompanyMasterData> userCompanyList){
		String retStr = "";
		if(userCompanyList != null && userCompanyList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				masterDao.deleteMasterData("USERCOMPANYTABLE");

				for(int i = 0; i < userCompanyList.size(); i ++){
					UserCompanyMasterData obj = (UserCompanyMasterData)userCompanyList.get(i);
					masterDao.insertUserCompanyMaster(obj);
				}

				List<UserCompanyMasterData> userCompanyOtherList = masterDao.selectUserCompanyOtherList();
				if(userCompanyOtherList != null) {
					String competentErrorMsg = "";
					String duplicationErrorMsg = "";

					for(int j = 0; j < userCompanyOtherList.size(); j ++){
						UserCompanyMasterData otherObj = (UserCompanyMasterData)userCompanyOtherList.get(j);
						if(Function.nvl(otherObj.getAdditionalFlg(), "").equals("0") &&
						   masterDao.isUserCompanyMaster(otherObj.getUserId(), null, null, null, null, null, otherObj.getAdditionalFlg())) {
							competentErrorMsg += "　　　USERID：" + otherObj.getUserId().toString()
											  +  "、開始日：" + otherObj.getStartDate()
											  +  "、オフィスコード：" + otherObj.getOfficeCode().trim()
											  +  "、会社コード：" + otherObj.getCompanyCode().trim()
											  +  "、部課コード：" + otherObj.getDivisionCode().trim()
											  +  "、役職コード：" + otherObj.getTitleCode().trim()
											  +  "、主務/兼務区分：" + otherObj.getAdditionalFlg()
											  + "\n";
						}
						else {
							if(!masterDao.isUserCompanyMaster(otherObj.getUserId(), otherObj.getStartDate(), otherObj.getOfficeCode(), otherObj.getCompanyCode(), otherObj.getDivisionCode(), otherObj.getTitleCode(), otherObj.getAdditionalFlg())) {
								masterDao.insertUserCompanyMaster(otherObj);
							}
							else {
								duplicationErrorMsg += "　　　USERID：" + otherObj.getUserId().toString()
													+  "、開始日：" + otherObj.getStartDate()
													+  "、オフィスコード：" + otherObj.getOfficeCode().trim()
													+  "、会社コード：" + otherObj.getCompanyCode().trim()
													+  "、部課コード：" + otherObj.getDivisionCode().trim()
													+  "、役職コード：" + otherObj.getTitleCode().trim()
													+  "、主務/兼務区分：" + otherObj.getAdditionalFlg()
													+ "\n";
							}
						}
					}
					if(!competentErrorMsg.equals("")) {
						retStr += "　　・下記ユーザは主務が存在している為、追加登録しません。\n";
						retStr += competentErrorMsg;
					}
					if(!duplicationErrorMsg.equals("")) {
						retStr += "　　・下記ユーザの所属は既に存在している為、追加登録しません。\n";
						retStr += duplicationErrorMsg;
					}
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザー所属マスタデータ登録処理"), e);
			}
		}
		return retStr;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#insertUserMster(java.util.List)
	 */
	public String insertUserMaster(List<UserMasterData> userList){
		String retStr = "";
		if(userList != null && userList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				masterDao.deleteMasterData("USERTABLE");

				for(int i = 0; i < userList.size(); i ++){
					UserMasterData obj = (UserMasterData)userList.get(i);
					masterDao.insertUserMaster(obj);
				}

				List<UserMasterData> userOtherList = masterDao.selectUserOtherList();
				if(userOtherList != null) {
					String errorData = "";
					for(int j = 0; j < userOtherList.size(); j ++){
						UserMasterData otherObj = (UserMasterData)userOtherList.get(j);
						if(!masterDao.isUserMaster(otherObj.getUserId())) {
							masterDao.insertUserMaster(otherObj);
						}
						else {
							errorData += "　　　USERID：" + otherObj.getUserId().toString() + "\n";
						}
					}
					if(!errorData.equals("")) {
						retStr += "　　・下記のユーザは既に存在している為、追加登録しません。\n";
						retStr += errorData;
					}
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "ユーザーマスタデータ登録処理"), e);
			}
		}
		return retStr;
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#mergeSapAssignmentsMaster(java.util.List)
	 */
	public void mergeSapAssignmentsMaster(List<SapAssignmentsMasterData> assignmentsList){
		if(assignmentsList != null && assignmentsList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				for(int i = 0; i < assignmentsList.size(); i ++){
					SapAssignmentsMasterData obj = (SapAssignmentsMasterData)assignmentsList.get(i);
					masterDao.mergeSapAssignmentsMaster(obj);
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "従業員所属マスタデータ登録処理"), e);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#mergeSapAccountMaster(java.util.List)
	 */
	public void mergeSapAccountMaster(List<SapAccountMasterData> accountList){
		if(accountList != null && accountList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				masterDao.deleteMasterData("NEA_SAP_ACCOUNT");

				for(int i = 0; i < accountList.size(); i ++){
					SapAccountMasterData obj = (SapAccountMasterData)accountList.get(i);
					masterDao.mergeSapAccountMaster(obj);
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "勘定科目マスタデータ登録処理"), e);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#mergeSapCustAccountsMaster(java.util.List)
	 */
	public void mergeSapCustAccountsMaster(List<SapCustAccountsMasterData> custAccountsList){
		if(custAccountsList != null && custAccountsList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				for(int i = 0; i < custAccountsList.size(); i ++){
					SapCustAccountsMasterData obj = (SapCustAccountsMasterData)custAccountsList.get(i);
					masterDao.mergeSapCustAccountsMaster(obj);
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "顧客マスタデータ更新処理"), e);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#mergeSapVendorsMaster(java.util.List)
	 */
	public void mergeSapVendorsMaster(List<SapVendorsMasterData> vendorsList){
		if(vendorsList != null && vendorsList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				for(int i = 0; i < vendorsList.size(); i ++){
					SapVendorsMasterData obj = (SapVendorsMasterData)vendorsList.get(i);
					masterDao.mergeSapVendorsMaster(obj);
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "仕入先マスタデータ更新処理"), e);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#mergeSapExpDeptMaster(java.util.List)
	 */
	public void mergeSapExpDeptMaster(List<SapExpDeptMasterData> expDeptList){
		if(expDeptList != null && expDeptList.size() > 0){
			MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
			try{
				masterDao.deleteMasterData("NEA_SAP_EXP_DEPT");

				for(int i = 0; i < expDeptList.size(); i ++){
					SapExpDeptMasterData obj = (SapExpDeptMasterData)expDeptList.get(i);
					masterDao.mergeSapExpDeptMaster(obj);
				}
			} catch (SQLException e) {
				throw new SysException(MsgManager.getMessage(MsgManager.MSGID200041, "経費負担部課マスタデータ登録処理"), e);
			}
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#searchLovList(java.lang.String, java.util.Map)
	 */
	public List<LovDataEx> searchLovList(String sqlName, Map<String,Object> paramMap) {
		MasterDAO masterDao = new jp.co.ctcg.easset.dao.MasterDAO();
		try {
			return masterDao.searchLovList(sqlName, paramMap);
		} catch (SQLException e) {
			// SQLの実行に失敗しました。（SQL名：$1）
			throw new SysException(MsgManager.getMessage(MsgManager.MSGID100016, sqlName), e);
		}
	}

	/*
	 * (非 Javadoc)
	 * @see jp.co.ctcg.easset.session.MasterSession#getLeaseRentalHantei(java.lang.String, java.lang.String)
     */
	public String getLeaseRentalHantei(String latorihikikbnC,String bunruicdC)  {

		// ①汎用マスタ参照(PPFS_RENTAL_HANTEI_TORIHIKIKBN)
		CodeName rentalHanteiTorihikikbn = getCodeName(Constants.CATEGORY_CODE_PPFS_RENTAL_HANTEI_TORIHIKIKBN, latorihikikbnC, null, null);

		// if(①のValue1が'1'の場合){
		if(rentalHanteiTorihikikbn != null && rentalHanteiTorihikikbn.getValue1().equals("1")){
			// ②汎用マスタ参照(PPFS_RENTAL_HANTEI_BUNRUICODE)
			CodeName rentalHanteiBunruicode = getCodeName(Constants.CATEGORY_CODE_PPFS_RENTAL_HANTEI_BUNRUICODE, bunruicdC, null, null);

			// if(②の件数がNULLでない場合){
			if(rentalHanteiBunruicode != null){
				return Constants.PPFS_RENTAL_HANTEI_RENTAL;
			}else{
				return Constants.PPFS_RENTAL_HANTEI_LEASE;
			}
		}else if(rentalHanteiTorihikikbn.getValue2().equals("1")){
			return Constants.PPFS_RENTAL_HANTEI_RENTAL;
		}else{
			return Constants.PPFS_RENTAL_HANTEI_LEASE;
		}
	}
}