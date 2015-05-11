package net.corp.core.service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.corp.core.exception.CorpException;
import net.corp.core.model.*;
import net.corp.core.vo.CountVO;
import net.corp.core.vo.LogVO;
import net.corp.core.vo.MaterialsVO;
import net.corp.core.vo.SiteVO;

public interface MaterialService {
	
	List<MaterialsVO> fetchAllMaterialEntries(Integer pageSize, Integer pageNumber, Integer time, boolean more, Date from, Date to, Integer materialId) throws CorpException;
	List<Vehicles> fetchAllVehicles();
	List<StockItems> fetchAllStockItems(boolean showAll);
	List<PrimaryGroup> fetchAllPrimaryGroups(boolean showAll);
	List<Vibhag> fetchAllVibhags(boolean showAll);
	List<CountVO> fetchAllCounts() throws CorpException;
	List<SiteVO> fetchAllSites();
	List<VibhagTypes> fetchAllVibhagTypes();
	List<Items> fetchAllItems();
	List<List<Contacts>> fetchAllContacts();
	
	MaterialsVO saveMaterial(MaterialsVO material);
	Integer linkMaterial(Integer type, List<String> materialIds);
	
	List<Vehicles> searchVehicleByName(String vehicleName);
	List<StockItems> searchStockItemsByName(String stockItemName);
	List<PrimaryGroup> searchPrimaryGroupsByName(String pgName);
	List<Vibhag> searchVibhagsByName(String vibhagName);
	List<Vehicles> searchVehicleByTransport(String transportName);
	
	boolean saveLog(LogVO logVo, Integer attachId);
	boolean saveLog(String phone, String sms, Date date);
	boolean completeLogMaterial(Integer materialId);
	String readLogEntry(String stockName, String transporterName, String vibhagName, String siteName);
	boolean updateLogMaterial(MaterialsVO materialsVo);
	boolean updateLogEntry(String vehicleNumber, Timestamp gateInTime, Timestamp gateOutTime);
	LogVO fetchLogByVehicleNumber(String vehicleNumber);
	LogVO fetchLogByCriteria(String vehicleNo, Integer time, Date from, Date to, Integer shift);
	List<LogVO> fetchLogsByCriteria(Integer time, Date from, Date to);
	Map<String, Map<String, List<String>>> fetchStaticDataForVibhag(String vibhagName);
	List<String> fetchEligVibhags();

	boolean saveVibhag(Vibhag vibhag);
	boolean saveVibhagType(VibhagTypes vibhagTypes);
	boolean saveVehicle(Vehicles vehicles);
	boolean savePrimaryGroup(PrimaryGroup primaryGroup);
	boolean saveStock(StockItems stockItems);
	boolean saveStockType(Items items);
	boolean saveContact(Contacts contact);

    Boolean saveLog(String vibhagName, String transportName, String vehicleName, String siteName, List<String> stockNames, Boolean nightShift);
}
