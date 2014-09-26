package net.corp.core.dao.impl;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import net.corp.core.dao.LogMaterialDAO;
import net.corp.core.model.LogMaterial;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

public class LogMaterialDAOImpl extends GenericDAOImpl<LogMaterial, Integer> implements LogMaterialDAO {

	@SuppressWarnings("unchecked")
	@Override
	public List<LogMaterial> findLogByGateTimes(Timestamp inTime,
			Timestamp outTime) {
		Criteria crit = getSession().createCriteria(LogMaterial.class);
		crit.createAlias("log", "logbook");
		if (inTime != null) {
			crit.add(Restrictions.eq("logbook.gateInTime", inTime));
		}
		if (outTime != null) {
			crit.add(Restrictions.eq("logbook.gateOutTime", outTime));
		}	
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LogMaterial> findLogByVibhag(String vibhagPhone, String vibhagName, boolean today) {
		Criteria crit = getSession().createCriteria(LogMaterial.class);
		crit.createAlias("log", "logbook");
		crit.createAlias("logbook.vibhag", "vib");
		if (vibhagPhone != null) {
			crit.add(Restrictions.eq("vib.phone", vibhagPhone));
		}
		if (vibhagName != null) {
			crit.add(Restrictions.eq("vib.vendorName", vibhagName));
		}
		if (today) {
			addTodayCriteria(crit, "log.gateInTime");
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LogMaterial> findLogByStockItem(String stockItemName, boolean today) {
		Criteria crit = getSession().createCriteria(LogMaterial.class);
		crit.createAlias("item", "stock");
		crit.createAlias("log", "logbook");
		crit.add(Restrictions.eq("stock.stockItemname", stockItemName));
		if (today) {
			addTodayCriteria(crit, "log.gateInTime");
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LogMaterial> findLogByTransporter(String transporterName, boolean today) {
		Criteria crit = getSession().createCriteria(LogMaterial.class);
		crit.createAlias("log", "logbook");
		crit.createAlias("logbook.transport", "trans");
		crit.add(Restrictions.eq("trans.vendorName", transporterName));
		if (today) {
			addTodayCriteria(crit, "log.gateInTime");
		}
		crit.addOrder(Order.asc("logMaterialId"));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LogMaterial> findLogByVehicle(String vehicleNumber, boolean today) {
		Criteria crit = getSession().createCriteria(LogMaterial.class);
		crit.createAlias("log", "logbook");
		crit.createAlias("logbook.vehicle", "veh");
		crit.add(Restrictions.eq("veh.vehicleNumber", vehicleNumber));
		if (today) {
			addTodayCriteria(crit, "log.gateInTime");
		}
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LogMaterial> findLogByMaterialId(Integer materialId) {
		Criteria crit = getSession().createCriteria(LogMaterial.class);
		crit.createAlias("material", "mat");
		crit.add(Restrictions.eq("mat.materialId", materialId));
		return crit.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<LogMaterial> findLogByShift(boolean nightShift) {
		Criteria crit = getSession().createCriteria(LogMaterial.class);
		crit.createAlias("log", "logbook");
		crit.add(Restrictions.eq("logbook.nightShift", nightShift));
		return crit.list();
	}

	@Override
	public boolean updateMaterialLog(Integer materialId, String status, Integer stockId) {
		StringBuffer str = new StringBuffer("UPDATE d_log_material set MATERIAL_ID = " + materialId);
		if ("COMPLETED".equalsIgnoreCase(status)) {
			str.append(" , COMPLETE = 1 ");
		}
		str.append(" WHERE STOCK_ITEM_ID = " + stockId);
		SQLQuery query = getSession().createSQLQuery(str.toString());
		query.executeUpdate();
		return true;
	}

	@Override
	public boolean completeMaterialLog(Integer materialId) {
		SQLQuery query = getSession().createSQLQuery("UPDATE d_log_material set COMPLETE = 1 WHERE MATERIAL_ID = " + materialId);
		query.executeUpdate();
		return true;
	}
	
	private void addTodayCriteria(Criteria crit, String fieldName) {
		Timestamp endDate = new Timestamp(System.currentTimeMillis());
		Calendar cal = Calendar.getInstance();
		cal.setTime(endDate);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Timestamp startDate = new Timestamp(cal.getTimeInMillis());
		crit.add(Restrictions.gt(fieldName, startDate));
		crit.add(Restrictions.le(fieldName, endDate));
	}
	
	@SuppressWarnings("unchecked")
	public List<LogMaterial> findLogMaterialEntries(Integer time, Date from, Date to, Integer shift) {
		
		Criteria criteria = getSession().createCriteria(LogMaterial.class);
		
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
			criteria.add(Restrictions.gt("gateInTime", startDate));
			criteria.add(Restrictions.le("gateInTime", endDate));
		}
		else if (from != null && to != null) {
			Timestamp endDate = new Timestamp(to.getTime());
			Timestamp startDate = new Timestamp(from.getTime());
			criteria.add(Restrictions.ge("gateInTime", startDate));
			criteria.add(Restrictions.le("gateInTime", endDate));
		}
		
		if (shift > 0) {
			criteria.add(Restrictions.eq("nightShift", (shift == 2)));
		}
		
		criteria.addOrder(Order.desc("gateInTime"));
		
		return criteria.list();
	}
	
}
