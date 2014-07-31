package net.corp.core.service.helper;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;

public class OutboundNotification implements IOutboundMessageNotification
{
	private static final Logger log = Logger.getLogger(OutboundNotification.class);
	public void process(AGateway gateway, OutboundMessage msg)
	{
		log.debug("Outbound handler called from Gateway: " + gateway.getGatewayId());
		log.debug(msg);
	}
}
