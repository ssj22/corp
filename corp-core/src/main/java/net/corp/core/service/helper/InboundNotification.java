package net.corp.core.service.helper;

import net.corp.core.service.MaterialService;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message.MessageTypes;

public class InboundNotification implements IInboundMessageNotification {
	private static final Logger log = Logger.getLogger(InboundNotification.class);
	
	private MaterialService materialService;
	
	public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg)
	{
//		if (msgType == MessageTypes.INBOUND) log.debug(">>> New Inbound message : "  +  msg.getText() + " : detected from Gateway: " + gateway.getGatewayId());
//		else if (msgType == MessageTypes.STATUSREPORT) log.debug(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
		
		String msgText = msg.getText();
		String phone = msg.getOriginator();
		log.info("Message received from "+ phone + ", Content = " + msgText);
		getMaterialService().saveLog(phone, msgText);

	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	
}
