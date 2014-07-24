package net.corp.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import net.corp.core.constants.CorpConstants;
import net.corp.core.exception.CorpException;
import net.corp.core.model.Materials;
import net.corp.core.model.PrimaryGroup;
import net.corp.core.model.StockItems;
import net.corp.core.model.Vehicles;
import net.corp.core.model.Vibhag;
import net.corp.core.service.MaterialService;
import net.corp.core.service.helper.CoreServiceHelper;
import net.corp.core.vo.CountVO;
import net.corp.core.vo.MaterialsVO;
import net.corp.core.vo.SiteVO;

import org.apache.log4j.Logger;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

public class MaterialServiceImpl implements MaterialService, CorpConstants {
	
	private CoreServiceHelper coreServiceHelper;
	private static final Logger log = Logger.getLogger(MaterialServiceImpl.class);

	@Override
	@Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
	public MaterialsVO saveMaterial(MaterialsVO material) {
		try {
			Materials entity = getCoreServiceHelper().convertVOToModel(material);
			
			if (entity.getVehicleInTime() != null) {
				entity.setStatus(STATUS_MATERIAL_ENTRY_INITIATED);
				entity.setInCreatedDate(new Timestamp(System.currentTimeMillis()));
				entity.setInUpdatedDate(new Timestamp(System.currentTimeMillis()));
			}
			if (entity.getVehicleOutTime() != null) {
				entity.setStatus(STATUS_MATERIAL_ENTRY_COMPLETED);
				entity.setOutCreatedDate(new Timestamp(System.currentTimeMillis()));
				entity.setOutUpdatedDate(new Timestamp(System.currentTimeMillis()));
			}
			
			getCoreServiceHelper().getMaterialDao().saveOrUpdate(entity);
			
			material.setMaterialId(entity.getMaterialId());
		}
		catch(Exception e) {
			log.error("Exception while saving Material: " + e.getMessage(), e);
			return null;
		}
		
		return material;
	}

	@Override
	public List<MaterialsVO> fetchAllMaterialEntries(Integer pageSize, Integer pageNumber, Integer time, boolean more) throws CorpException {
		List<MaterialsVO> rtnList = new ArrayList<MaterialsVO>();
		List<Materials> tempList = null;
		if ((time == null || time == 0) && (pageSize == null || pageNumber == null || pageSize == 0 || pageNumber == 0)) {
			tempList = getCoreServiceHelper().getMaterialDao().findAll();
		}
		else {
			tempList = getCoreServiceHelper().getMaterialDao().findPaginatedMaterialEntries(pageNumber, pageSize, time, more);
		}	
		
		if (tempList != null && !tempList.isEmpty()) {
			for (Materials material: tempList) {
				rtnList.add(getCoreServiceHelper().convertModelToVO(material));
			}
		}
		
		return rtnList;
	}

	@Override
	public List<SiteVO> fetchAllSites() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<CountVO> fetchAllCounts() throws CorpException {
		List<CountVO> list = new ArrayList<CountVO>();
		CountVO countVo = new CountVO();
		List<Long> dayCountList = getCoreServiceHelper().getMaterialDao().findAllCounts(1);
		countVo.setTodayInCount(dayCountList.get(0));
		countVo.setTodayOutCount(dayCountList.get(1));
		countVo.setTodayCount(dayCountList.get(0) + dayCountList.get(1));
		
		List<Long> weekCountList = getCoreServiceHelper().getMaterialDao().findAllCounts(7);
		countVo.setWeekInCount(weekCountList.get(0));
		countVo.setWeekOutCount(weekCountList.get(1));
		countVo.setWeekCount(weekCountList.get(0) + weekCountList.get(1));
		
		List<Long> monthCountList = getCoreServiceHelper().getMaterialDao().findAllCounts(30);
		countVo.setMonthInCount(monthCountList.get(0));
		countVo.setMonthOutCount(monthCountList.get(1));
		countVo.setMonthCount(monthCountList.get(0) + monthCountList.get(1));
		
		List<Long> countList = getCoreServiceHelper().getMaterialDao().findAllCounts(null);
		countVo.setTotalInCount(countList.get(0));
		countVo.setTotalOutCount(countList.get(1));
		countVo.setTotalCount(countList.get(0) + countList.get(1));
		
		list.add(countVo);
		return list;
	}
	
	@Override
	public List<Vehicles> fetchAllVehicles() {
		return getCoreServiceHelper().getVehicleDao().findAll();
	}
	
	@Override
	public List<StockItems> fetchAllStockItems() {
		return getCoreServiceHelper().getStockItemDao().findAll();
	}
	
	@Override
	public List<PrimaryGroup> fetchAllPrimaryGroups() {
		return getCoreServiceHelper().getPrimaryGroupDao().findAll();
	}
	
	@Override
	public List<Vibhag> fetchAllVibhags() {
		return getCoreServiceHelper().getVibhagDao().findAll();
	}
	
	@Override
	public List<Vehicles> searchVehicleByName(String vehicleName) {
		return getCoreServiceHelper().getVehicleDao().searchVehicleByName(vehicleName);
	}

	@Override
	public List<StockItems> searchStockItemsByName(String stockItemName) {
		return getCoreServiceHelper().getStockItemDao().searchStockItemsByName(stockItemName);
	}

	@Override
	public List<PrimaryGroup> searchPrimaryGroupsByName(String pgName) {
		return getCoreServiceHelper().getPrimaryGroupDao().searchPrimaryGroupsByName(pgName);
	}

	@Override
	public List<Vibhag> searchVibhagsByName(String vibhagName) {
		return getCoreServiceHelper().getVibhagDao().searchVibhagsByName(vibhagName);
	}
	
	
	public CoreServiceHelper getCoreServiceHelper() {
		return coreServiceHelper;
	}

	public void setCoreServiceHelper(CoreServiceHelper coreServiceHelper) {
		this.coreServiceHelper = coreServiceHelper;
	}
	
}
