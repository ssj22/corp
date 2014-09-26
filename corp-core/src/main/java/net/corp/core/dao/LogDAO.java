package net.corp.core.dao;

import net.corp.core.model.LogBook;



public interface LogDAO extends GenericDAO<LogBook, Integer> {
	boolean exists(String sms, String phone);
}	
