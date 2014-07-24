package net.corp.core.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import net.corp.core.dao.MaterialDAO;
import net.corp.core.model.Materials;
import net.corp.core.vo.SiteVO;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class MaterialDAOImpl extends GenericDAOImpl<Materials, Integer> implements MaterialDAO {

	@Override
	@SuppressWarnings({ "rawtypes" })
	public List<Long> findAllCounts(Integer time) {

		Criteria criteria = getSession().createCriteria(Materials.class);
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("entryType"));
		projectionList.add(Projections.rowCount());
		if (time != null && time > 0) {
			Timestamp endDate = new Timestamp(System.currentTimeMillis());
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_YEAR, -1 * (time-1));
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Timestamp startDate = new Timestamp(cal.getTimeInMillis());
			criteria.add(Restrictions.gt("vehicleInTime", startDate));
		}
		
		criteria.setProjection(projectionList);
		List<Long> currentStateList = new LinkedList<Long>();
		List results = criteria.list();
		if (results != null && !results.isEmpty()) {
			Iterator it=results.iterator();
			while (it.hasNext()) {
				Object obj[]=(Object[])it.next();
				Integer type = (Integer)obj[0];
				Long count = (Long)obj[1];
				if (results.size() < 2) {
					if (type == 1) {
						currentStateList.add(count);
						currentStateList.add(0l);
					}	
					else {
						currentStateList.add(0l);
						currentStateList.add(count);
					}
				}
				else {
					if (type == 0) {
						continue;
					}
					currentStateList.add(count);
				}
			}
		}
		else {
			currentStateList.add(0l);
			currentStateList.add(0l);
		}
		return currentStateList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Materials> findPaginatedMaterialEntries(Integer pageNumber,
			Integer pageSize, Integer time, boolean more) {
		
		Criteria criteria = getSession().createCriteria(Materials.class);
		
		if (time != null && time > 0) {
			
			Timestamp endDate = new Timestamp(System.currentTimeMillis());
			Calendar cal = Calendar.getInstance();
			cal.setTime(endDate);
			cal.add(Calendar.DAY_OF_YEAR, -1 * (time-1));
			cal.set(Calendar.HOUR, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			Timestamp startDate = new Timestamp(cal.getTimeInMillis());
			criteria.add(Restrictions.gt("vehicleInTime", startDate));
			criteria.add(Restrictions.le("vehicleInTime", endDate));
		}
		else {
			if (more) {
				criteria.setFirstResult(pageNumber);
			}
			else {
				criteria.setFirstResult((pageNumber - 1) * pageSize);
			}
			criteria.setMaxResults(pageSize);
		}
		criteria.addOrder(Order.desc("vehicleInTime"));
		
		return criteria.list();
	}

	@Override
	@SuppressWarnings({ "rawtypes" })
	public List<SiteVO> findAllSites() {
		List<SiteVO> sites = new ArrayList<SiteVO>();
		Criteria criteria = getSession().createCriteria(Materials.class);
		
		ProjectionList projectionList = Projections.projectionList();
		projectionList.add(Projections.groupProperty("siteName"));
		projectionList.add(Projections.rowCount());
		
		List results = criteria.list();
		if (results != null && !results.isEmpty()) {
			Iterator it=results.iterator();
			while (it.hasNext()) {
				Object obj[]=(Object[])it.next();
				SiteVO site = new SiteVO();
				site.setSiteName((String)obj[0]);
				site.setCount((Long)obj[1]);
			}
		}
		return sites;
	}
	
}
