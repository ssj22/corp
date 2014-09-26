package net.corp.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.corp.core.service.helper.CallNotification;
import net.corp.core.service.helper.GatewayStatusNotification;
import net.corp.core.service.helper.InboundNotification;
import net.corp.core.service.helper.OrphanedMessageNotification;
import net.corp.core.service.helper.OutboundNotification;

import org.apache.log4j.Logger;
import org.smslib.GatewayException;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.modem.SerialModemGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class SMSServlet extends HttpServlet {
	
	private static final Logger LOGGER = Logger.getLogger(SMSServlet.class);
	
	public static void main(String[] args) {
		SMSServlet obj= new SMSServlet();
		try {
			obj.init();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void init() throws ServletException {
		LOGGER.debug("Initializing SMS Servlet");
		ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
		InboundNotification inboundNotification = (InboundNotification) context.getBean("inboundNotification");
		OutboundNotification outboundNotification = (OutboundNotification) context.getBean("outboundNotification");
		LOGGER.debug("inboundNotification is " + inboundNotification);
		LOGGER.debug("outboundNotification is " + outboundNotification);
		CallNotification callNotification = new CallNotification();
		GatewayStatusNotification statusNotification = new GatewayStatusNotification();
		OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
		
		Service.getInstance().setInboundMessageNotification(inboundNotification);
		Service.getInstance().setCallNotification(callNotification);
		Service.getInstance().setGatewayStatusNotification(statusNotification);
		Service.getInstance().setOrphanedMessageNotification(orphanedMessageNotification);
		
		Service.getInstance().setOutboundMessageNotification(outboundNotification);
		
		SerialModemGateway gateway = new SerialModemGateway("hotmix", "COM12", 9600, "ZTE", "CE1588");
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("0000");
		
		try {
			//gateway.getATHandler().setStorageLocations("ME");
//			Service.getInstance().addGateway(gateway);
			
//			Service.getInstance().startService();
			
//			List<InboundMessage> msgList = new ArrayList<InboundMessage>();
//			int i = Service.getInstance().readMessages(msgList, MessageClasses.READ);
//			System.out.println("Value of i = " + i);
//			for (InboundMessage msg : msgList) {
//				System.out.println("Msg Text Received = " + msg.getText());
//			}
//			
			System.out.println("SMS Servlet Started");
		} 
//		catch (GatewayException e) {
//			e.printStackTrace();
//			LOGGER.error("Exception in the Gateway: " + e.getMessage(), e);
//		} catch (TimeoutException e) {
//			e.printStackTrace();
//			LOGGER.error("Timeout Exception: " + e.getMessage(), e);
//		} catch (SMSLibException e) {
//			e.printStackTrace();
//			LOGGER.error("SMS Lib Exception: " + e.getMessage(), e);
//		} catch (IOException e) {
//			e.printStackTrace();
//			LOGGER.error("IO Exception: " + e.getMessage(), e);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//			LOGGER.error("Interrupted Exception: " + e.getMessage(), e);
//		} 
		catch (Exception e) {
			LOGGER.error("Exception in SMSServlet: " + e.getMessage(), e);
		}
	}

	
	
	@Override
	public void destroy() {
		try {
			Service.getInstance().stopService();
			System.out.println("SMS Servlet Stopped");
		} catch (TimeoutException e) {
			LOGGER.error("Timeout Exception: " + e.getMessage(), e);
		} catch (GatewayException e) {
			LOGGER.error("Exception in the Gateway: " + e.getMessage(), e);
		} catch (SMSLibException e) {
			LOGGER.error("SMS Lib Exception: " + e.getMessage(), e);
		} catch (IOException e) {
			LOGGER.error("IO Exception: " + e.getMessage(), e);
		} catch (InterruptedException e) {
			LOGGER.error("Interrupted Exception: " + e.getMessage(), e);
		} catch (Exception e) {
			LOGGER.error("Exception: " + e.getMessage(), e);
		}
		super.destroy();
	}

}
