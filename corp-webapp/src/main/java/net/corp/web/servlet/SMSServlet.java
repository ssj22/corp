    package net.corp.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Formatter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import net.corp.core.constants.CorpConstants;
import net.corp.core.service.helper.CallNotification;
import net.corp.core.service.helper.GatewayStatusNotification;
import net.corp.core.service.helper.InboundNotification;
import net.corp.core.service.helper.OrphanedMessageNotification;
import net.corp.core.service.helper.OutboundNotification;
import net.corp.core.util.PropertyUtil;

import org.apache.log4j.Logger;
import org.smslib.GatewayException;
import org.smslib.SMSLibException;
import org.smslib.Service;
import org.smslib.TimeoutException;
import org.smslib.helper.CommPortIdentifier;
import org.smslib.helper.SerialPort;
import org.smslib.modem.SerialModemGateway;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

@SuppressWarnings("serial")
public class SMSServlet extends HttpServlet implements CorpConstants {
	
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
		LOGGER.info("Initializing SMS Servlet");
		Boolean smsOn = Boolean.parseBoolean(PropertyUtil.getInstance().getProperty("smsOn"));
		Boolean commTest = Boolean.parseBoolean(PropertyUtil.getInstance().getProperty("commTest"));
		if (smsOn) {
			if (commTest) {
				CommTest.main(null);
			}

			ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
			InboundNotification inboundNotification = (InboundNotification) context.getBean("inboundNotification");
			OutboundNotification outboundNotification = (OutboundNotification) context.getBean("outboundNotification");
			CallNotification callNotification = new CallNotification();
			GatewayStatusNotification statusNotification = new GatewayStatusNotification();
			OrphanedMessageNotification orphanedMessageNotification = new OrphanedMessageNotification();
			
			Service.getInstance().setInboundMessageNotification(inboundNotification);
			Service.getInstance().setCallNotification(callNotification);
			Service.getInstance().setGatewayStatusNotification(statusNotification);
			Service.getInstance().setOrphanedMessageNotification(orphanedMessageNotification);
			
			Service.getInstance().setOutboundMessageNotification(outboundNotification);
			
			String port = PropertyUtil.getInstance().getProperty(PROP_PORT);
			String commId = PropertyUtil.getInstance().getProperty(PROP_COMM_ID);
			Integer baudRate = Integer.parseInt(PropertyUtil.getInstance().getProperty(PROP_BAUD_RATE));
			String simPin = PropertyUtil.getInstance().getProperty(PROP_SIM_PIN);
			Boolean serialPoll = Boolean.parseBoolean(PropertyUtil.getInstance().getProperty(PROP_SERIAL_POLL));
			Integer serialPollInt = Integer.parseInt(PropertyUtil.getInstance().getProperty(PROP_SERIAL_POLL_INT));
						
			SerialModemGateway gateway = new SerialModemGateway(commId, port, baudRate, "ZTE", "CE1588");
            System.setProperty("gnu.io.rxtx.SerialPorts", port);
			gateway.setInbound(true);
			gateway.setOutbound(true);
			gateway.setSimPin(simPin);
			
			try {
				//gateway.getATHandler().setStorageLocations("ME");
				Service.getInstance().addGateway(gateway);
				
				if (serialPoll) {
					Service.getInstance().getSettings().SERIAL_POLLING = true;
					Service.getInstance().getSettings().SERIAL_POLLING_INTERVAL = serialPollInt;
				}	
				Service.getInstance().startService();
				
				PropertyUtil.getInstance().addProperty(PROP_SMS_SERVICE, "true");
				LOGGER.info("SMS Servlet Started");
			} 
			catch (GatewayException e) {
				e.printStackTrace();
				LOGGER.error("Exception in the Gateway: " + e.getMessage(), e);
			} catch (TimeoutException e) {
				e.printStackTrace();
				LOGGER.error("Timeout Exception: " + e.getMessage(), e);
			} catch (SMSLibException e) {
				e.printStackTrace();
				LOGGER.error("SMS Lib Exception: " + e.getMessage(), e);
			} catch (IOException e) {
				e.printStackTrace();
				LOGGER.error("IO Exception: " + e.getMessage(), e);
			} catch (InterruptedException e) {
				e.printStackTrace();
				LOGGER.error("Interrupted Exception: " + e.getMessage(), e);
			} 
			catch (Exception e) {
				LOGGER.error("Exception in SMSServlet at port " + port + " : " + e.getMessage(), e);
			}
		}
		else {
			LOGGER.info("SMS Servlet not initialized as per the configuration");
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


class CommTest
{
	private static final String _NO_DEVICE_FOUND = "  no device found";

	private final static Formatter _formatter = new Formatter(System.out);

	static CommPortIdentifier portId;

	static Enumeration<CommPortIdentifier> portList;

	static int bauds[] = { 9600, 14400, 19200, 28800, 33600, 38400, 56000, 57600, 115200 };

	/**
	 * Wrapper around {@link CommPortIdentifier#getPortIdentifiers()} to be
	 * avoid unchecked warnings.
	 */
	private static Enumeration<CommPortIdentifier> getCleanPortIdentifiers()
	{
		return CommPortIdentifier.getPortIdentifiers();
	}

	public static void main(String[] args)
	{
		System.out.println("\nSearching for devices...");
		portList = getCleanPortIdentifiers();
		while (portList.hasMoreElements())
		{
			portId = portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL)
			{
				_formatter.format("%nFound port: %-5s%n", portId.getName());
				for (int i = 0; i < bauds.length; i++)
				{
					SerialPort serialPort = null;
					_formatter.format("       Trying at %6d...", bauds[i]);
					try
					{
						InputStream inStream;
						OutputStream outStream;
						int c;
						String response;
						serialPort = portId.open("SMSLibCommTester", 1971);
						serialPort.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN);
						serialPort.setSerialPortParams(bauds[i], SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
						inStream = serialPort.getInputStream();
						outStream = serialPort.getOutputStream();
						serialPort.enableReceiveTimeout(1000);
						c = inStream.read();
						while (c != -1)
							c = inStream.read();
						outStream.write('A');
						outStream.write('T');
						outStream.write('\r');
						Thread.sleep(1000);
						response = "";
						StringBuilder sb = new StringBuilder();
						c = inStream.read();
						while (c != -1)
						{
							sb.append((char) c);
							c = inStream.read();
						}
						response = sb.toString();
						if (response.indexOf("OK") >= 0)
						{
							try
							{
								System.out.print("  Getting Info...");
								outStream.write('A');
								outStream.write('T');
								outStream.write('+');
								outStream.write('C');
								outStream.write('G');
								outStream.write('M');
								outStream.write('M');
								outStream.write('\r');
								response = "";
								c = inStream.read();
								while (c != -1)
								{
									response += (char) c;
									c = inStream.read();
								}
								System.out.println(" Found: " + response.replaceAll("\\s+OK\\s+", "").replaceAll("\n", "").replaceAll("\r", ""));
							}
							catch (Exception e)
							{
								System.out.println(_NO_DEVICE_FOUND);
							}
						}
						else
						{
							System.out.println(_NO_DEVICE_FOUND);
						}
					}
					catch (Exception e)
					{
						System.out.print(_NO_DEVICE_FOUND);
						Throwable cause = e;
						while (cause.getCause() != null)
						{
							cause = cause.getCause();
						}
						System.out.println(" (" + cause.getMessage() + ")");
					}
					finally
					{
						if (serialPort != null)
						{
							serialPort.close();
						}
					}
				}
			}
		}
		System.out.println("\nTest complete.");
	}
}