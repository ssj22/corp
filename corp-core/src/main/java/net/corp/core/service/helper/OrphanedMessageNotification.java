package net.corp.core.service.helper;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.IOrphanedMessageNotification;
import org.smslib.InboundMessage;

public class OrphanedMessageNotification implements IOrphanedMessageNotification {
	
	private static final Logger log = Logger.getLogger(OrphanedMessageNotification.class);
	
	public boolean process(AGateway gateway, InboundMessage msg)
	{
		log.debug(">>> Orphaned message part detected from " + gateway.getGatewayId());
		log.debug(msg);
		// Since we are just testing, return FALSE and keep the orphaned message part.
		return false;
	}
}

