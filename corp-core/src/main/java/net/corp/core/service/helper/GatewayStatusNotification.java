package net.corp.core.service.helper;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.AGateway.GatewayStatuses;
import org.smslib.IGatewayStatusNotification;

public class GatewayStatusNotification implements IGatewayStatusNotification
{
	private static final Logger log = Logger.getLogger(GatewayStatusNotification.class);
	
	public void process(AGateway gateway, GatewayStatuses oldStatus, GatewayStatuses newStatus)
	{
		log.debug(">>> Gateway Status change for " + gateway.getGatewayId() + ", OLD: " + oldStatus + " -> NEW: " + newStatus);
	}
}
