package net.corp.core.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.corp.core.constants.CorpConstants;
import net.corp.core.exception.CorpException;
import net.corp.core.model.LogBook;
import net.corp.core.model.LogMaterial;
import net.corp.core.model.Materials;
import net.corp.core.model.PrimaryGroup;
import net.corp.core.model.StockItems;
import net.corp.core.model.Vehicles;
import net.corp.core.model.Vibhag;
import net.corp.core.service.MaterialService;
import net.corp.core.service.helper.CoreServiceHelper;
import net.corp.core.vo.CountVO;
import net.corp.core.vo.LogVO;
import net.corp.core.vo.MaterialsVO;
import net.corp.core.vo.SiteVO;

import org.apache.log4j.Logger;

public class MaterialServiceImpl implements MaterialService, CorpConstants {
	
	private CoreServiceHelper coreServiceHelper;
	private static final Logger log = Logger.getLogger(MaterialServiceImpl.class);

	@Override
	public Integer linkMaterial(Integer type, List<String> materialIds) {
		List<Integer> children = new ArrayList<Integer>(materialIds.size());
		for (String strId: materialIds) {
			int id = Integer.parseInt(strId);
			children.add(id);
		}
		
		if (type.intValue() == 1) {
			return getCoreServiceHelper().getMaterialDao().linkMaterial(children);
		}
		else {
			return getCoreServiceHelper().getMaterialDao().unlinkMaterial(children);
		}
	}

	@Override
	public MaterialsVO saveMaterial(MaterialsVO material) {
		try {
			Materials entity = getCoreServiceHelper().convertVOToModel(material);
			
			if (entity.getStatus() == null) {
				entity.setStatus(STATUS_MATERIAL_ENTRY_INITIATED);
				entity.setInCreatedDate(new Timestamp(System.currentTimeMillis()));
				entity.setVehicleInTime(entity.getInCreatedDate());
				entity.setInCreatedBy(material.getUserId());
			}
			else if (STATUS_MATERIAL_ENTRY_INITIATED.equalsIgnoreCase(entity.getStatus())) {
				entity.setStatus(STATUS_MATERIAL_ENTRY_COMPLETED);
				entity.setOutCreatedDate(new Timestamp(System.currentTimeMillis()));
				entity.setVehicleOutTime(entity.getOutCreatedDate());
				entity.setOutCreatedBy(material.getUserId());
			}
			
			StockItems item = getCoreServiceHelper().getStockItemDao().getById(entity.getStockId());
			Double inRate = item.getStockRateInword();
			PrimaryGroup vendor = getCoreServiceHelper().getPrimaryGroupDao().getById(entity.getVendorId());
			Vehicles vehicle = getCoreServiceHelper().getVehicleDao().getById(entity.getVehicleId());
			Double volume = (vehicle.getVolume() == 0)? (vehicle.getBreadth() * vehicle.getHeight() * vehicle.getLength()) : vehicle.getVolume();
			Double effInRate = inRate;
			if (vendor.getPercentage() != null) {
				effInRate = (1 + vendor.getPercentage()/100) * inRate;
			}
			Double effOutRate = item.getStockRate();
			Double effRate = 0.0;
			Double amount = 0.0;
			Double qty = entity.getNetWt();
			
			switch(item.getItem().getMainItemid()) {
				case 1: 
					if (entity.getEntryType() == 1) {
						qty = volume;
						if (entity.getNetWt() > 5000) {
							amount = qty * effInRate;
						}
						else {
							amount = 0.0; 
						}
						effRate = effInRate;
					}
					else {
						// Use factor as per metric ton
						double factor = 1.0;
						if (item.getItem().getFactor() != null) {
							factor = item.getItem().getFactor();
						}
						
						qty = (qty/1000) * factor;
						amount = effOutRate * qty;
						effRate = effOutRate;
					}
					break;
				case 2: 
					amount = qty * effInRate; 
					effRate = effInRate;
					break;
				case 3:
				case 15:
				case 19:
				case 20:
					if (entity.getEntryType() == 1) {
						amount = entity.getAmount() != null ? entity.getAmount() : 0;
						qty = entity.getQuantity() != null ? (1.0 * entity.getQuantity()) : 0;
						if (qty != null && qty.intValue() > 0) {
							effInRate = amount / qty; 
						}
						else {
							effInRate = 0.0;
						}
						qty = entity.getNetWt() - qty;
						effRate = effInRate;
					}
					else {
						qty = entity.getQuantity() != null ? (1.0 * entity.getQuantity()) : 0;
						amount = effOutRate * qty;
						effRate = effOutRate;
					}
					break;
				case 12:
					if (entity.getEntryType() == 1) {
						amount = entity.getAmount() != null ? entity.getAmount() : 0;
						qty = entity.getQuantity() != null ? (1.0 * entity.getQuantity()) : 0;
						effRate = inRate; 
					}
					else {
						qty = entity.getQuantity() != null ? (1.0 * entity.getQuantity()) : 0;
						amount = effOutRate * qty;
						effRate = effOutRate;
					}
					break;
				case 16:
					if (entity.getEntryType() == 1) {
						amount = entity.getAmount() != null ? entity.getAmount() : 0;
						qty = entity.getQuantity() != null ? (1.0 * entity.getQuantity()) : 0;
						if (qty != null && qty.intValue() > 0) {
							effInRate = amount / qty; 
						}
						else {
							effInRate = 0.0;
						}
						effRate = effInRate;
					}
					else {
						qty = entity.getQuantity() != null ? (1.0 * entity.getQuantity()) : 0;
						amount = effOutRate * qty;
						effRate = effOutRate;
					}
					break;
				case 14:
					amount = entity.getAmount() != null ? entity.getAmount() : 0;
					qty = entity.getQuantity() != null ? (1.0 * entity.getQuantity()) : 0;
					effRate = 0.0;
					break;
			}
			
			entity.setAmount(amount);
			entity.setRate(effRate);
			entity.setQuantity(qty.intValue());
			
			getCoreServiceHelper().getMaterialDao().saveOrUpdate(entity);
			
			if (material.getParentMaterialId() != null) {
				List<Integer> list = new ArrayList<Integer>();
				list.add(material.getMaterialId());
				list.add(material.getParentMaterialId());
				getCoreServiceHelper().getMaterialDao().linkMaterial(list);
			}
			
			material.setMaterialId(entity.getMaterialId());
		}
		catch(Exception e) {
			log.error("Exception while saving Material: " + e.getMessage(), e);
			return null;
		}
		
		return material;
	}

	@Override
	public List<MaterialsVO> fetchAllMaterialEntries(Integer pageSize, Integer pageNumber, Integer time, boolean more, Date from, Date to, Integer materialId) throws CorpException {
		List<MaterialsVO> rtnList = new ArrayList<MaterialsVO>();
		List<Materials> tempList = null;
		if ((time == null || time == 0) 
				&& (pageSize == null || pageNumber == null || 
					pageSize == 0 || pageNumber == 0)
				&& (from == null || to == null)
				&& materialId == null){
			tempList = getCoreServiceHelper().getMaterialDao().findAll();
		}
		else if (materialId != null) {
			tempList = getCoreServiceHelper().getMaterialDao().findRelatedEntries(materialId);
		}
		else {
			tempList = getCoreServiceHelper().getMaterialDao().findPaginatedMaterialEntries(pageNumber, pageSize, time, more, from, to);
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
		return getCoreServiceHelper().getMaterialDao().findAllSites();
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
		List<Vehicles> vehicles = getCoreServiceHelper().getVehicleDao().findAll();
		if (vehicles != null && !vehicles.isEmpty()) {
			for (Vehicles vehicle: vehicles) {
				vehicle.setVehicleNumber(vehicle.getVehicleNumber().replace(" ", "-"));
			}
		}
		return vehicles;
	}
	
	@Override
	public List<StockItems> fetchAllStockItems(boolean showAll) {
		return getCoreServiceHelper().getStockItemDao().findAll();
	}
	
	@Override
	public List<PrimaryGroup> fetchAllPrimaryGroups(boolean showAll) {
		if (showAll) {
			return getCoreServiceHelper().getPrimaryGroupDao().findAll();
		}
		return null;
	}
	
	@Override
	public List<Vibhag> fetchAllVibhags(boolean showAll) {
		if (showAll) {
			return getCoreServiceHelper().getVibhagDao().findAll();
		}
		return null;
	}
	
	@Override
	public List<String> fetchEligVibhags() {
		List<String> rtnList = new ArrayList<String>();
		List<LogMaterial> logMaterials = getCoreServiceHelper().getLogMaterialDao().findLogByVibhag(null, null, true);
		if (logMaterials != null) {
			for(LogMaterial lm: logMaterials) {
				if (!rtnList.contains(lm.getLog().getVibhag().getVibhagName())) {
					rtnList.add(lm.getLog().getVibhag().getVibhagName());
				}
			}
			return rtnList;
		}
		return null;
	}

	@Override
	public Map<String, Map<String, List<String>>> fetchStaticDataForVibhag(String vibhagName) {
		Map<String, Map<String, List<String>>> tmap = new LinkedHashMap<String, Map<String,List<String>>>();
		List<LogMaterial> logMaterials = getCoreServiceHelper().getLogMaterialDao().findLogByVibhag(null, vibhagName, true);
		if (logMaterials != null) {
			List<String> vList = new ArrayList<String>();
			List<String> iList = new ArrayList<String>();
			List<String> sList = null;
			for(LogMaterial lm: logMaterials) {
				String tName = lm.getLog().getTransport().getVendorName();
				String vName = lm.getLog().getVehicle().getVehicleNumber();
				String iName = lm.getItem().getStockItemname();
				String sName = lm.getLog().getSiteName();
				Map<String, List<String>> subMap = null;
				if (tmap.containsKey(tName)) {
					subMap = tmap.get(tName);
					if (subMap.containsKey("s")) {
						sList = subMap.get("s");
					}
					else {
						sList = new ArrayList<String>();
						subMap.put("s", sList);
					}
					
					if (subMap.containsKey("i")) {
						iList = subMap.get("i");
					}
					else {
						iList = new ArrayList<String>();
						subMap.put("i", iList);
					}
					
					if (subMap.containsKey("v")) {
						vList = subMap.get("v");
					}
					else {
						vList = new ArrayList<String>();
						subMap.put("v", vList);
					}
				}
				else {
					subMap = new HashMap<String, List<String>>();
					vList = new ArrayList<String>();
					subMap.put("v", vList);
					iList = new ArrayList<String>();
					subMap.put("i", iList);
					sList = new ArrayList<String>();
					subMap.put("s", sList);
					tmap.put(tName, subMap);
				}
				
				
				if (!vList.contains(vName)) {
					vList.add(vName);
				}
				if (!sList.contains(sName)) {
					sList.add(sName);
				}
				if (!iList.contains(iName) && (lm.getMaterial() == null || lm.getMaterial().getMaterialId() == null)) {
					iList.add(iName);
				}
			}
		}	
		
		return tmap;
	}

	@Override
	public List<Vehicles> searchVehicleByName(String vehicleName) {
		return getCoreServiceHelper().getVehicleDao().searchVehicleByName(vehicleName);
	}
	
	@Override
	public List<Vehicles> searchVehicleByTransport(String transportName) {
		PrimaryGroup pg = getCoreServiceHelper().getPrimaryGroupDao().findPrimaryGroupByName(transportName);
		if (pg != null) {
			return getCoreServiceHelper().getVehicleDao().searchVehicleByTransport(pg.getVendorId());
		}
		return null;
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
	
	@Override
	public boolean completeLogMaterial(Integer logMaterialId) {
		try {
			LogMaterial logMaterial = getCoreServiceHelper().getLogMaterialDao().getById(logMaterialId);
			if (logMaterial != null) {
				logMaterial.setComplete(true);
				getCoreServiceHelper().getLogMaterialDao().saveOrUpdate(logMaterial);
			}
		} catch (Exception e) {
			log.error("Exception while completing log material entry: " + e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public boolean saveLog(String phone, String sms) {
		try {
			LogVO logVo = getCoreServiceHelper().getLogFromSms(phone, sms);
			logVo.setNewEntry(true);
			saveLog(logVo);
		} catch (Exception e) {
			log.error("Exception while saving raw logs: " + e.getMessage(), e);
			return false;
		}
		
		return true;
	}
	
	@Override
	public boolean saveLog(LogVO logVo) {
		try {
			List<LogMaterial> logMaterials = getCoreServiceHelper().convertVOToModel(logVo);
			if (logMaterials != null && !logMaterials.isEmpty()) {
				LogBook logbook = logMaterials.get(0).getLog();
				if (logbook.getVehicle() != null && logbook.getTransport() != null && 
						logbook.getVehicle().getVendorId().intValue() != logbook.getTransport().getVendorId().intValue()) {
					logbook.setValid(false);
				}
				
				getCoreServiceHelper().getLogDao().saveOrUpdate(logbook);
				
				for (LogMaterial entity: logMaterials) {
					entity.setLog(logbook);
					getCoreServiceHelper().getLogMaterialDao().saveOrUpdate(entity);
				}
			}
		} catch (Exception e) {
			log.error("Exception while saving logs: " + e.getMessage(), e);
			return false;
		}
		
		return true;
	}

	@Override
	public boolean updateLogMaterial(MaterialsVO materialsVo) {
		try {
			LogMaterial logMaterial = getCoreServiceHelper().getLogMaterialDao().getById(materialsVo.getLogMaterialId());
			if (logMaterial != null) {
				logMaterial.setMaterial(getCoreServiceHelper().convertVOToModel(materialsVo));
				getCoreServiceHelper().getLogMaterialDao().saveOrUpdate(logMaterial);
			}
		} catch (Exception e) {
			log.error("Exception while updating log material entry: " + e.getMessage(), e);
			return false;
		}
		return true;
	}
	
	public boolean updateLogEntry(String vehicleNumber, Timestamp gateInTime, Timestamp gateOutTime) {
		try {
			LogVO logVo = fetchLogByVehicleNumber(vehicleNumber);
			if (logVo != null) {
				List<LogMaterial> logMaterials = getCoreServiceHelper().convertVOToModel(logVo);
				LogBook entity = logMaterials.get(0).getLog();
				if (gateInTime != null) entity.setGateInTime(gateInTime);
				if (gateOutTime != null) entity.setGateOutTime(gateOutTime);
				entity.setNewEntry(false);
				getCoreServiceHelper().getLogDao().saveOrUpdate(entity);
			}
			else {
				return false;
			}
		} catch (Exception e) {
			log.error("Exception while updating log material entry: " + e.getMessage(), e);
			return false;
		}
		return true;
	}

	@Override
	public LogVO fetchLogByCriteria(String vehicleNo, Integer time, Date from,
			Date to, Integer shift) {
		if (vehicleNo == null) {
			return getCoreServiceHelper().convertModelToVO(getCoreServiceHelper().getLogMaterialDao().findLogMaterialEntries(time, from, to, shift));
		}
		else {
			return fetchLogByVehicleNumber(vehicleNo);
		}
	}
	
	@Override
	public List<LogVO> fetchLogsByCriteria(Integer time, Date from, Date to) {
		List<LogVO> logVos = new ArrayList<LogVO>();
		List<LogBook> list = getCoreServiceHelper().getLogDao().findLogsByCriteria(time, from, to);
		if (list != null && !list.isEmpty()) {
			for (LogBook log: list) {
				logVos.add(getCoreServiceHelper().convertModelToVO(log));
			}
		}
		
		return logVos;
	}

	@Override
	public LogVO fetchLogByVehicleNumber(String vehicleNumber) {
		return getCoreServiceHelper().convertModelToVO(getCoreServiceHelper().getLogMaterialDao().findLogByVehicle(vehicleNumber, true));
	}

	public CoreServiceHelper getCoreServiceHelper() {
		return coreServiceHelper;
	}

	public void setCoreServiceHelper(CoreServiceHelper coreServiceHelper) {
		this.coreServiceHelper = coreServiceHelper;
	}
	
}
