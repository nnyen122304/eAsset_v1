package jp.co.ctcg.easset.mdb;

import java.util.HashMap;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import jp.co.ctcg.easset.session.InvSession;
import jp.co.ctcg.easset.util.Function;

@MessageDriven (
	name = "CreateInvDataQueue"
	, activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/CreateInvDataQueue")
	}
	)
public class CreateInvDataMDBean implements MessageListener {
	public static final String FUNCTION_CREATE = "CREATE";
	public static final String FUNCTION_SENDMAIL = "SENDMAIL";

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

			String companyCode = Function.nvl((String) param.get("companyCode"), "");
			String period = Function.nvl((String) param.get("period"), "");
			String execStaffCode = (String) param.get("execStaffCode");
			Boolean overwriteFlag = (Boolean) param.get("overwriteFlag");

			Boolean[] sendExecArray = (Boolean[]) param.get("sendExecArray");

			//////////////////////////////////// 処理呼び出し
			if(functionName.equals(FUNCTION_CREATE)) { // 棚卸データ作成
				invSession.createInvData(companyCode, period, execStaffCode, overwriteFlag);
			} else if(functionName.equals(FUNCTION_SENDMAIL)) { // メール送信
				invSession.sendPublicInvMail(companyCode, period, sendExecArray, execStaffCode);
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
