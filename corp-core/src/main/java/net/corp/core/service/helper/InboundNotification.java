package net.corp.core.service.helper;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.IInboundMessageNotification;
import org.smslib.InboundMessage;
import org.smslib.Message.MessageTypes;

public class InboundNotification implements IInboundMessageNotification
{
	private static final Logger log = Logger.getLogger(InboundNotification.class);
	
	public void process(AGateway gateway, MessageTypes msgType, InboundMessage msg)
	{
		if (msgType == MessageTypes.INBOUND) log.debug(">>> New Inbound message detected from Gateway: " + gateway.getGatewayId());
		else if (msgType == MessageTypes.STATUSREPORT) log.debug(">>> New Inbound Status Report message detected from Gateway: " + gateway.getGatewayId());
		log.debug(msg);
	}
}
