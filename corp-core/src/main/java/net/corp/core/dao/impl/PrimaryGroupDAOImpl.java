package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.PrimaryGroupDAO;
import net.corp.core.model.PrimaryGroup;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class PrimaryGroupDAOImpl extends GenericDAOImpl<PrimaryGroup, Integer> implements PrimaryGroupDAO {

	@SuppressWarnings("unchecked")
	@Override
	public PrimaryGroup findPrimaryGroupByName(String transporterName) {
		Criteria crit = getSession().createCriteria(PrimaryGroup.class);
		crit.add(Restrictions.eq("vendorName", transporterName));
		
		List<PrimaryGroup> list = crit.list(); 
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null; 
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<PrimaryGroup> searchPrimaryGroupsByName(String pgName) {
		Criteria crit = getSession().createCriteria(PrimaryGroup.class);
		crit.add(Restrictions.ilike("vendorName", pgName));
		
		return crit.list(); 
	}
}
