package jp.co.ctcg.easset.mdb;

import java.util.HashMap;
import java.util.List;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import jp.co.ctcg.easset.dto.inv.InvSumSR;
import jp.co.ctcg.easset.session.InvSession;

@MessageDriven (
	name = "UpdateAssetInvDataQueue"
	, activationConfig = {
		@ActivationConfigProperty(propertyName = "destination", propertyValue = "java:/jms/queue/UpdateAssetInvDataQueue")
	}
	)		
public class UpdateAssetInvMDBean implements MessageListener {


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
			String execStaffCode = (String) param.get("execStaffCode");
			List<InvSumSR> list = (List<InvSumSR>) param.get("list");

			//////////////////////////////////// 処理呼び出し
			invSession.updateAssetInvData(execStaffCode, list);

		} catch (JMSException e) {
			e.printStackTrace();
		}
	}
}
