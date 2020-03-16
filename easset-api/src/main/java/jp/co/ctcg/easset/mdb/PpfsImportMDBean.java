package jp.co.ctcg.easset.mdb;

import java.util.HashMap;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import jp.co.ctcg.easset.session.PpfsImportSession;
import jp.co.ctcg.easset.util.Constants;
import jp.co.ctcg.easset.util.Function;

@MessageDriven (
	name = "PpfsImportQueue"
	, activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/PpfsImportQueue")
	}
	)
public class PpfsImportMDBean implements MessageListener {

	@EJB
	private PpfsImportSession ppfsImportSession; // ProPlus取込

	/*
	 * (非 Javadoc)
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@SuppressWarnings("unchecked")
	public void onMessage(Message msg) {
		try {
			//////////////////////////////////// パラメータ取得
			HashMap<String, Object> param = (HashMap<String, Object>) ((ObjectMessage) msg).getObject();
			String companyCode = Function.nvl((String) param.get("companyCode"), "");
			String dataType = Function.nvl((String) param.get("dataType"), "");
			String execStaffCode = (String) param.get("execStaffCode");

			//////////////////////////////////// 処理呼び出し
			// 台帳・実績
			if(dataType.equals(Constants.PPFS_IMPORT_DATA_TYPE_FLD) || dataType.equals(Constants.PPFS_IMPORT_DATA_TYPE_LLD)) {
				ppfsImportSession.ppfsImport(companyCode, dataType, execStaffCode);
			}
			// 予測
			if(dataType.equals(Constants.PPFS_IMPORT_DATA_TYPE_FLD_SCH) || dataType.equals(Constants.PPFS_IMPORT_DATA_TYPE_LLD_SCH)) {
				ppfsImportSession.ppfsImportSch(companyCode, dataType, execStaffCode);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
