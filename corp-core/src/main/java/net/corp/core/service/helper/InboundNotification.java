package net.corp.core.service.helper;

import net.corp.core.service.MaterialService;

import org.apache.log4j.Logger;
import org.smslib.*;
import org.smslib.Message.MessageTypes;

import java.io.IOException;

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

        try
        {
            gateway.deleteMessage(msg);
        }
        catch (TimeoutException e)
        {
            log.error("TimeoutException while deleting new message from " + phone + ": " + e.getMessage(), e);
        }
        catch (GatewayException e)
        {
            log.error("GatewayException while deleting new message from " + phone + ": " + e.getMessage(), e);
        }
        catch (IOException e)
        {
            log.error("IOException while deleting new message from " + phone + ": " + e.getMessage(), e);
        }
        catch (InterruptedException e)
        {
            log.error("InterruptedException while deleting new message from " + phone + ": " + e.getMessage(), e);
        }

	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	
}
