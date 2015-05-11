package net.corp.core.dao;

import java.util.List;

import net.corp.core.model.Vehicles;

public interface VehicleDAO extends GenericDAO<Vehicles, Integer> {

	Vehicles findVehicleByNumber(String vehicleNumber, Integer vendorId);
	List<Vehicles> searchVehicleByName(String vehicleName);
	List<Vehicles> searchVehicleByTransport(Integer transportId);
	
}
