package net.corp.core.dao;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import net.corp.core.model.LogMaterial;

public interface LogMaterialDAO extends GenericDAO<LogMaterial, Integer> {
	List<LogMaterial> findLogByGateTimes(Timestamp inTime, Timestamp outTime);
	
	List<LogMaterial> findLogByVibhag(String vibhagPhone, String vibhagName, boolean today);
	
	List<LogMaterial> findLogByTransporter(String transporterName, boolean today);
	
	List<LogMaterial> findLogByVehicle(String vehicleNumber, boolean today);
	
	List<LogMaterial> findLogByMaterialId(Integer materialId);
	
	List<LogMaterial> findLogByStockItem(String stockItemName, boolean today);
	
	List<LogMaterial> findLogByShift(boolean nightShift);
	
	boolean updateMaterialLog(Integer materialId, String status, Integer stockId);
	
	boolean completeMaterialLog(Integer materialId);
	
	List<LogMaterial> findLogMaterialEntries(Integer time, Date from, Date to, Integer shift); 
}
