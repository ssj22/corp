package net.corp.core.service.impl;

import java.util.ArrayList;
import java.util.List;

import net.corp.core.service.MessageService;
import net.corp.core.service.helper.CallNotification;
import net.corp.core.service.helper.CoreServiceHelper;
import net.corp.core.service.helper.GatewayStatusNotification;
import net.corp.core.service.helper.InboundNotification;
import net.corp.core.service.helper.OrphanedMessageNotification;
import net.corp.core.service.helper.OutboundNotification;
import net.corp.core.vo.MaterialsVO;

import org.apache.log4j.Logger;
import org.smslib.AGateway.Protocols;
import org.smslib.InboundMessage;
import org.smslib.InboundMessage.MessageClasses;
import org.smslib.Service.ServiceStatus;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageServiceImpl implements MessageService {

	private static final Logger log = Logger.getLogger(MessageServiceImpl.class);

	@Autowired
	private CoreServiceHelper coreServiceHelper;
	
	@Override
	public void sendMessage(MaterialsVO materialVo) throws Exception {
		
//		System.out.println("Example: Send message from a serial gsm modem.");
//		System.out.println(Library.getLibraryDescription());
//		System.out.println("Version: " + Library.getLibraryVersion());
		if (!(Service.getInstance().getServiceStatus().equals(ServiceStatus.STARTED) ||
				Service.getInstance().getServiceStatus().equals(ServiceStatus.STARTING))) {
			OutboundNotification outboundNotification = new OutboundNotification();
			SerialModemGateway gateway = new SerialModemGateway("hotmix", "COM9", 115200, "ZTE", "CE1588");
			gateway.setInbound(true);
			gateway.setOutbound(true);
			gateway.setSimPin("0000");
			//gateway.setProtocol(Protocols.TEXT);
			// Explicit SMSC address set is required for some modems.
			// Below is for VODAFONE GREECE - be sure to set your own!
			//gateway.setSmscNumber("+919822078000");
			Service.getInstance().setOutboundMessageNotification(outboundNotification);
			Service.getInstance().addGateway(gateway);
			
			Service.getInstance().startService();
			log.info("Service Started");
		}	
//		System.out.println();
//		System.out.println("Modem Information:");
//		System.out.println("  Manufacturer: " + gateway.getManufacturer());
//		System.out.println("  Model: " + gateway.getModel());
//		System.out.println("  Serial No: " + gateway.getSerialNo());
//		System.out.println("  SIM IMSI: " + gateway.getImsi());
//		System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
//		System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
//		System.out.println();
		// Send a message synchronously.
		OutboundMessage msg = new OutboundMessage("+919767942400", "Test Message.");
		Service.getInstance().sendMessage(msg);
		//System.out.println(msg);
		// Or, send out a WAP SI message.
		//OutboundWapSIMessage wapMsg = new OutboundWapSIMessage("306974000000",  new URL("http://www.smslib.org/"), "Visit SMSLib now!");
		//Service.getInstance().sendMessage(wapMsg);
		//System.out.println(wapMsg);
		// You can also queue some asynchronous messages to see how the callbacks
		// are called...
		//msg = new OutboundMessage("309999999999", "Wrong number!");
		//srv.queueMessage(msg, gateway.getGatewayId());
		//msg = new OutboundMessage("308888888888", "Wrong number!");
		//srv.queueMessage(msg, gateway.getGatewayId());
		//System.out.println("Now Sleeping - Hit <enter> to terminate.");
		//System.in.read();
		//Service.getInstance().stopService();
	}

	@Override
	public void readMessage() throws Exception	{
		// Define a list which will hold the read messages.
		List<InboundMessage> msgList;
		// Create the notification callback method for inbound & status report
		// messages.
		InboundNotification inboundNotification = new InboundNotification();
		// Create the notification callback method for inbound voice calls.
		CallNotification callNotification = new CallNotification();
		//Create the notification callback method for gateway statuses.
		GatewayStatusNotification statusNotification = new GatewayStatusNotification();
		OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
		try
		{
//			System.out.println("Example: Read messages from a serial gsm modem.");
//			System.out.println(Library.getLibraryDescription());
//			System.out.println("Version: " + Library.getLibraryVersion());
			// Create the Gateway representing the serial GSM modem.
			//SerialModemGateway gateway = new SerialModemGateway("modem.com4", "COM4", 115200, "Huawei", "E160");
			if (!(Service.getInstance().getServiceStatus().equals(ServiceStatus.STARTED) ||
					Service.getInstance().getServiceStatus().equals(ServiceStatus.STARTING))) {
				SerialModemGateway gateway = new SerialModemGateway("hotmix", "COM9", 115200, "ZTE", "CE1588");
				// Set the modem protocol to PDU (alternative is TEXT). PDU is the default, anyway...
				gateway.setProtocol(Protocols.PDU);
				// Do we want the Gateway to be used for Inbound messages?
				gateway.setInbound(true);
				// Do we want the Gateway to be used for Outbound messages?
				gateway.setOutbound(true);
				// Let SMSLib know which is the SIM PIN.
				gateway.setSimPin("0000");
				// Set up the notification methods.
				Service.getInstance().setInboundMessageNotification(inboundNotification);
				Service.getInstance().setCallNotification(callNotification);
				Service.getInstance().setGatewayStatusNotification(statusNotification);
				Service.getInstance().setOrphanedMessageNotification(orphanedMessageNotification);
				// Add the Gateway to the Service object.
				Service.getInstance().addGateway(gateway);
				// Similarly, you may define as many Gateway objects, representing
				// various GSM modems, add them in the Service object and control all of them.
				// Start! (i.e. connect to all defined Gateways)
				Service.getInstance().startService();
			}	
			// Printout some general information about the modem.
//			System.out.println();
//			System.out.println("Modem Information:");
//			System.out.println("  Manufacturer: " + gateway.getManufacturer());
//			System.out.println("  Model: " + gateway.getModel());
//			System.out.println("  Serial No: " + gateway.getSerialNo());
//			System.out.println("  SIM IMSI: " + gateway.getImsi());
//			System.out.println("  Signal Level: " + gateway.getSignalLevel() + " dBm");
//			System.out.println("  Battery Level: " + gateway.getBatteryLevel() + "%");
//			System.out.println();
			// In case you work with encrypted messages, its a good time to declare your keys.
			// Create a new AES Key with a known key value. 
			// Register it in KeyManager in order to keep it active. SMSLib will then automatically
			// encrypt / decrypt all messages send to / received from this number.
			//Service.getInstance().getKeyManager().registerKey("+306948494037", new AESKey(new SecretKeySpec("0011223344556677".getBytes(), "AES")));
			// Read Messages. The reading is done via the Service object and
			// affects all Gateway objects defined. This can also be more directed to a specific
			// Gateway - look the JavaDocs for information on the Service method calls.
			msgList = new ArrayList<InboundMessage>();
			Service.getInstance().readMessages(msgList, MessageClasses.ALL);
			for (InboundMessage msg : msgList)
				System.out.println(msg);
			// Sleep now. Emulate real world situation and give a chance to the notifications
			// methods to be called in the event of message or voice call reception.
//			System.out.println("Now Sleeping - Hit <enter> to stop service.");
//			System.in.read();
		}
		catch (Exception e) {
			log.error("Exception during readMessage : " + e.getMessage());
		}
		finally {
			Service.getInstance().stopService();
		}
	}
	
	public static void main(String args[]) {
		MessageService srv = new MessageServiceImpl();
		try {
			srv.sendMessage(null);
			//readMessage();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public CoreServiceHelper getCoreServiceHelper() {
		return coreServiceHelper;
	}

	public void setCoreServiceHelper(CoreServiceHelper coreServiceHelper) {
		this.coreServiceHelper = coreServiceHelper;
	}
	
	
}
