package net.corp.biz.test;

import javax.annotation.Resource;

import net.corp.core.service.MaterialService;
import net.corp.core.service.MessageService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class MessageServiceTest {
	
	@Resource
	private MessageService messageService;
	
	@Resource
	private MaterialService materialService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testSaveLogs() {
		try {
			getMaterialService().saveLog("9689931970", "D$MH 12 RA 7245$MOTILAL DYUT UDYOG SAMUH$20 M.M. METAL@2000@KG,BITUMEN 60/70 GRADE@400@KL$ABINAVA SHALA KARVE ROAD", null);
			//Thread.sleep(10000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}

	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	
	
}
