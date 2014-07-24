package net.corp.core.util;


public class SMSUtility {

	private static String port = "COM12"; //Modem Port.
    private static int bitRate = 115200; //this is also optional. leave as it is.
    private static String modemName = "ZTE"; //this is optional.
    private static String modemPin = "0000"; //Pin code if any have assigned to the modem.
    private static String SMSC = "+919822078000"; //Message Center Number ex. Mobitel
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		try {
//			GsmModem gsmModem = new GsmModem();
//		    GsmModem.configModem(port, bitRate, modemName, modemPin, SMSC);
//		    gsmModem.Sender("+919767942400", "Test Message"); // (tp, msg) 
//		} catch (IOException e) {
//			System.out.println(e.toString());
//			e.printStackTrace();
//		} catch (Exception e) {
//			System.out.println(e.toString());
//			e.printStackTrace();
//		}
	}
}
