package net.corp.core.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import net.corp.core.service.MaterialService;
import net.corp.core.service.MessageService;
import net.corp.core.service.helper.CoreServiceHelper;

import org.apache.log4j.Logger;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.springframework.beans.factory.annotation.Autowired;

public class MessageServiceImpl implements MessageService {

	private static final Logger log = Logger.getLogger(MessageServiceImpl.class);

	@Autowired
	private CoreServiceHelper coreServiceHelper;
	
	@Autowired
	private MaterialService materialService;
	
	@Override
	public void syncUp() {
		Connection c = null;
		Statement stmt = null;
		ResultSet rs = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:C:\\UUDb");
	      //System.out.println("Opened database successfully");
	      stmt = c.createStatement();
	      rs = stmt.executeQuery( "SELECT * FROM Sms;" );
	      while ( rs.next() ) {
	    	 Integer complex = rs.getInt("SmsComplexIndex");
	    	 if (complex == null) {
	    		 continue;
	    	 }
	         String phone = rs.getString("SmsNumber").replace("+91", "");
	         String sms = rs.getString("Content");
	         
	         if (!getCoreServiceHelper().getLogDao().exists(sms, phone)) {
	        	 getMaterialService().saveLog(phone, sms);
	         }
	      }
	      rs.close();
	      stmt.close();
	      c.close();
	    } catch (Exception e) {
	      log.error("Exception while SyncUp: " + e.getMessage(), e);
	    }
	}

	@Override
	public void sendMessage(String phone, String text) throws Exception {
		OutboundMessage msg = new OutboundMessage(phone, text);
		Service.getInstance().sendMessage(msg);
	}

	@Override
	public void sendMessages(List<String> phones, String text) throws Exception {
		for (String phone: phones) {
			OutboundMessage msg = new OutboundMessage(phone, text);
			Service.getInstance().sendMessage(msg);
		}
	}
	
	public CoreServiceHelper getCoreServiceHelper() {
		return coreServiceHelper;
	}

	public void setCoreServiceHelper(CoreServiceHelper coreServiceHelper) {
		this.coreServiceHelper = coreServiceHelper;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	
}
