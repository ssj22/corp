package net.corp.core.service;

import java.util.List;


public interface MessageService {
	public void syncUp();

	void sendMessage(String phone, String text) throws Exception;

	void sendMessages(List<String> phones, String text) throws Exception;
}
