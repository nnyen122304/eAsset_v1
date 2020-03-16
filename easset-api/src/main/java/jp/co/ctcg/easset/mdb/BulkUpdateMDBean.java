package jp.co.ctcg.easset.mdb;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import jp.co.ctcg.easset.dto.CodeName;
import jp.co.ctcg.easset.session.AssetSession;
import jp.co.ctcg.easset.session.FldSession;
import jp.co.ctcg.easset.session.InvSession;
import jp.co.ctcg.easset.session.LicenseSession;
import jp.co.ctcg.easset.util.Function;

@MessageDriven (
	name = "BulkUpdateQueue"
	, activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/BulkUpdateQueue")
	}
	)
public class BulkUpdateMDBean implements MessageListener {
	public static final String FUNCTION_ASSET = "ASSET";
	public static final String FUNCTION_LICENSE = "LICENSE";
	public static final String FUNCTION_FLD_APP = "FLD_APP";
	public static final String FUNCTION_INV = "INV";

	@EJB
	private AssetSession assetSession; // 情報機器等

	@EJB
	private LicenseSession licenseSession; // ライセンス

	@EJB
	private FldSession fldSession; // 固定資産照会

	@EJB
	private InvSession invSession; // 棚卸

	/*
	 * (非 Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@SuppressWarnings("unchecked")
	public void onMessage(Message msg) {
		try {
			//////////////////////////////////// パラメータ取得
			HashMap<String, Object> param = (HashMap<String, Object>) ((ObjectMessage) msg).getObject();
			String functionName = Function.nvl((String) param.get("functionName"), "");
			String companyCode = (String) param.get("companyCode");
			String loginStaffCode = (String) param.get("loginStaffCode");
			String accessLevel = (String) param.get("accessLevel");
			String fileId = (String) param.get("fileId");
			File execFile = (File) param.get("execFile");
			List<CodeName> updatePropertyList = (List<CodeName>) param.get("updatePropertyList");
			Long logId = (Long) param.get("logId");

			//////////////////////////////////// 処理呼び出し
			if(functionName.equals(FUNCTION_ASSET)) {
				assetSession.updateAssetBulk(loginStaffCode, accessLevel, companyCode, fileId, execFile, updatePropertyList, logId);
			} else if(functionName.equals(FUNCTION_LICENSE)) {
				licenseSession.updateLicenseBulk(loginStaffCode, accessLevel, companyCode, fileId, execFile, updatePropertyList, logId);
			} else if(functionName.equals(FUNCTION_FLD_APP)){
				fldSession.updateFldBulk(companyCode, loginStaffCode, accessLevel, fileId, execFile, updatePropertyList, logId);
			}else if(functionName.equals(FUNCTION_INV)){
				String period = (String) param.get("period");
				invSession.updateInvBulk(loginStaffCode, companyCode, accessLevel, fileId, execFile, updatePropertyList, logId, period);
			}

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
