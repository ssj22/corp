package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.VehicleDAO;
import net.corp.core.model.Vehicles;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class VehicleDAOImpl extends GenericDAOImpl<Vehicles, Integer> implements VehicleDAO {

	@SuppressWarnings("unchecked")
	@Override
	public Vehicles findVehicleByNumber(String vehicleNumber, Integer vendorId) {
		Criteria crit = getSession().createCriteria(Vehicles.class);
		crit.add(Restrictions.eq("vehicleNumber", vehicleNumber));
        crit.createAlias("vendor", "v");
        crit.add(Restrictions.eq("v.vendorId", vendorId));
		
		List<Vehicles> list = crit.list(); 
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null; 
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Vehicles> searchVehicleByName(String vehicleName) {
		Criteria crit = getSession().createCriteria(Vehicles.class);
		crit.add(Restrictions.ilike("vehicleNumber", vehicleName));
		
		return crit.list(); 
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Vehicles> searchVehicleByTransport(Integer transportId) {
		Criteria crit = getSession().createCriteria(Vehicles.class);
		crit.createAlias("vendor", "v");
		crit.add(Restrictions.eq("v.vendorId", transportId));
		
		return crit.list();
	}

}
