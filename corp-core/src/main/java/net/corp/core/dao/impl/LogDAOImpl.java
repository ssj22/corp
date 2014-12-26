package net.corp.core.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.corp.core.dao.LogDAO;
import net.corp.core.model.LogBook;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
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

	@SuppressWarnings("unchecked")
	@Override
	public List<LogBook> findLogsByCriteria(Integer time, Date from, Date to) {
		Criteria criteria = getSession().createCriteria(LogBook.class);
		if (time != null && time > 0) {
			Calendar cal = Calendar.getInstance();
			cal.add(Calendar.DAY_OF_YEAR, -1 * (time-1));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Timestamp startDate = new Timestamp(cal.getTime().getTime());
            cal.setTimeInMillis(System.currentTimeMillis());
            cal.add(Calendar.DAY_OF_YEAR, 1);
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            Timestamp endDate = new Timestamp(cal.getTime().getTime());
			criteria.add(Restrictions.ge("gateInTime", startDate));
			criteria.add(Restrictions.le("gateInTime", endDate));
		}
		else if (from != null && to != null) {
			Timestamp startDate = new Timestamp(from.getTime());
			
			Calendar cal = Calendar.getInstance();
			cal.setTime(to);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Timestamp endDate = new Timestamp(cal.getTime().getTime());
			criteria.add(Restrictions.ge("gateInTime", startDate));
			criteria.add(Restrictions.le("gateInTime", endDate));
		}
		
		criteria.addOrder(Order.desc("gateInTime"));
		
		return criteria.list();
	}
	
	
}
