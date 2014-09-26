package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.VibhagDAO;
import net.corp.core.model.Vibhag;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class VibhagDAOImpl extends GenericDAOImpl<Vibhag, Integer> implements VibhagDAO {

	@SuppressWarnings("unchecked")
	@Override
	public Vibhag findVibhagByName(String vibhagName) {
		Criteria crit = getSession().createCriteria(Vibhag.class);
		crit.add(Restrictions.eq("vibhagName", vibhagName));
		
		List<Vibhag> list = crit.list(); 
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null; 
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Vibhag> searchVibhagsByName(String vibhagName) {
		Criteria crit = getSession().createCriteria(Vibhag.class);
		crit.add(Restrictions.ilike("vibhagName", vibhagName));
		
		return crit.list(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public Vibhag findVibhagByPhone(String phone) {
		Criteria crit = getSession().createCriteria(Vibhag.class);
		crit.add(Restrictions.eq("phone", phone));
		
		List<Vibhag> list = crit.list(); 
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null; 
	}

}
