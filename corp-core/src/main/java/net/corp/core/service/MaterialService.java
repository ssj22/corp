package net.corp.core.service;

import java.util.List;

import net.corp.core.exception.CorpException;
import net.corp.core.model.PrimaryGroup;
import net.corp.core.model.StockItems;
import net.corp.core.model.Vehicles;
import net.corp.core.model.Vibhag;
import net.corp.core.vo.CountVO;
import net.corp.core.vo.MaterialsVO;
import net.corp.core.vo.SiteVO;

public interface MaterialService {
	
	List<MaterialsVO> fetchAllMaterialEntries(Integer pageSize, Integer pageNumber, Integer time, boolean more) throws CorpException;
	List<Vehicles> fetchAllVehicles();
	List<StockItems> fetchAllStockItems();
	List<PrimaryGroup> fetchAllPrimaryGroups();
	List<Vibhag> fetchAllVibhags();
	List<CountVO> fetchAllCounts() throws CorpException;
	List<SiteVO> fetchAllSites();
	
	MaterialsVO saveMaterial(MaterialsVO material);
	
	List<Vehicles> searchVehicleByName(String vehicleName);
	List<StockItems> searchStockItemsByName(String stockItemName);
	List<PrimaryGroup> searchPrimaryGroupsByName(String pgName);
	List<Vibhag> searchVibhagsByName(String vibhagName);
	
	
}
