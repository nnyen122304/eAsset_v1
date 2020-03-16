package jp.co.ctcg.easset.mdb;

import java.util.HashMap;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import jp.co.ctcg.easset.session.CostScrSession;
import jp.co.ctcg.easset.util.Function;

@MessageDriven (
	name = "CreateCostScrDataQueue"
	, activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/CreateCostScrDataQueue")
	}
	)
public class CreateCostScrDataMDBean implements MessageListener {
	public static final String FUNCTION_CREATE = "CREATE";
	public static final String FUNCTION_SENDMAIL = "SENDMAIL";

	@EJB
	private CostScrSession costScrSession; // 経費負担部課精査

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

//			Boolean[] sendExecArray = (Boolean[]) param.get("sendExecArray");

			//////////////////////////////////// 処理呼び出し
			if(functionName.equals(FUNCTION_CREATE)) { // 経費負担部課精査データ作成
				costScrSession.createCostScrData(companyCode, period, execStaffCode, overwriteFlag);
			} else if(functionName.equals(FUNCTION_SENDMAIL)) { // メール送信
			}
		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
