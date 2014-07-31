package net.corp.core.service.helper;

import org.apache.log4j.Logger;
import org.smslib.AGateway;
import org.smslib.ICallNotification;

public class CallNotification implements ICallNotification
{
	private static final Logger log = Logger.getLogger(CallNotification.class);
	public void process(AGateway gateway, String callerId)
	{
		log.debug(">>> New call detected from Gateway: " + gateway.getGatewayId() + " : " + callerId);
	}
}
