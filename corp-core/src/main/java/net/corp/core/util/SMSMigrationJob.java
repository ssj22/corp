package net.corp.core.util;

import net.corp.core.service.MessageService;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class SMSMigrationJob extends QuartzJobBean {
	
	private MessageService messageService;
	
	@Override
    public void executeInternal(final JobExecutionContext ctx)
            throws JobExecutionException {
		//getMessageService().syncUp();
    }

	public MessageService getMessageService() {
		return messageService;
	}

	public void setMessageService(MessageService messageService) {
		this.messageService = messageService;
	}
	
}
