package net.corp.core.service;

import net.corp.core.vo.MaterialsVO;

public interface MessageService {
	public void sendMessage(MaterialsVO materialVo) throws Exception;
	
	public void readMessage() throws Exception;
}
