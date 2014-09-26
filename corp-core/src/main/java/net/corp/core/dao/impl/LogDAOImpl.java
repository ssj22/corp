package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.LogDAO;
import net.corp.core.model.LogBook;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class LogDAOImpl extends GenericDAOImpl<LogBook, Integer> implements LogDAO {

	@SuppressWarnings("unchecked")
	@Override
	public boolean exists(String sms, String phone) {
		Criteria crit = getSession().createCriteria(LogBook.class);
		crit.add(Restrictions.eq("phone", phone));
		crit.add(Restrictions.eq("msg", sms));
		
		List<LogBook> list = crit.list();
		if (list == null || list.isEmpty()) {
			return false;
		}
		return true;
	}
}
