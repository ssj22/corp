package net.corp.core.dao;

import java.util.Date;
import java.util.List;

import net.corp.core.model.LogBook;



public interface LogDAO extends GenericDAO<LogBook, Integer> {
	boolean exists(String sms, String phone);

	List<LogBook> findLogsByCriteria(Integer time, Date from, Date to);
}	
