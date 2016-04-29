package net.corp.core.service.impl;

import net.corp.core.constants.CorpConstants;
import net.corp.core.exception.CorpException;
import net.corp.core.model.*;
import net.corp.core.service.MaterialService;
import net.corp.core.service.helper.CoreServiceHelper;
import net.corp.core.vo.CountVO;
import net.corp.core.vo.LogVO;
import net.corp.core.vo.MaterialsVO;
import net.corp.core.vo.SiteVO;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.*;

public class MaterialServiceImpl implements MaterialService, CorpConstants {
	
	private CoreServiceHelper coreServiceHelper;
	private static final Logger log = Logger.getLogger(MaterialServiceImpl.class);

	@Override
	public List<Items> fetchAllItems() {
		return getCoreServiceHelper().getItemsMainDao().findAll();
	}

	@Override
	public List<VibhagTypes> fetchAllVibhagTypes() {
		return getCoreServiceHelper().getVibhagTypesDao().findAll();
	}

	@Override
	public List<List<Contacts>> fetchAllContacts() {
		List<List<Contacts>> rtnList = new ArrayList<List<Contacts>>();
        rtnList.add(getCoreServiceHelper().getContactsDao().findGroupContacts());
        rtnList.add(getCoreServiceHelper().getContactsDao().findIndividualContacts());
        return rtnList;
	}

	@Override
	public boolean saveContact(Contacts contact) {
		try {
			getCoreServiceHelper().getContactsDao().saveOrUpdate(contact);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving Contact: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean saveVibhag(Vibhag vibhag) {
		try {
			getCoreServiceHelper().getVibhagDao().saveOrUpdate(vibhag);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving vibhag: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean saveVibhagType(VibhagTypes vibhagTypes) {
		try {
			getCoreServiceHelper().getVibhagTypesDao().saveOrUpdate(vibhagTypes);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving vibhag types: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean saveVehicle(Vehicles vehicles) {
		try {
			getCoreServiceHelper().getVehicleDao().saveOrUpdate(vehicles);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving vehicles: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean savePrimaryGroup(PrimaryGroup primaryGroup) {
		try {
			getCoreServiceHelper().getPrimaryGroupDao().saveOrUpdate(primaryGroup);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving Vendor: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean saveStock(StockItems stockItems) {
		try {
			getCoreServiceHelper().getStockItemDao().saveOrUpdate(stockItems);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving Stock Item: " + e.getMessage());
		}
		return false;
	}

	@Override
	public boolean saveStockType(Items items) {
		try {
			getCoreServiceHelper().getItemsMainDao().saveOrUpdate(items);
			return true;
		} catch (Exception e) {
			log.error("Exception while saving Item Type: " + e.getMessage());
		}
		return false;
	}

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
			boolean firstEntry = false;
			if (entity.getStatus() == null) {
				firstEntry = true;
				entity.setStatus(STATUS_MATERIAL_ENTRY_INITIATED);
				entity.setInCreatedDate(new Timestamp(System.currentTimeMillis()));
				entity.setVehicleInTime(entity.getInCreatedDate());
				entity.setInCreatedBy(material.getUserId());
			} else if (STATUS_MATERIAL_ENTRY_INITIATED.equalsIgnoreCase(entity.getStatus())) {
				firstEntry = false;
				entity.setStatus(STATUS_MATERIAL_ENTRY_COMPLETED);
				entity.setOutCreatedDate(new Timestamp(System.currentTimeMillis()));
				entity.setVehicleOutTime(entity.getOutCreatedDate());
				entity.setOutCreatedBy(material.getUserId());
			}
			if (!firstEntry) {
				StockItems item = getCoreServiceHelper().getStockItemDao().getById(entity.getStockId());
				Double inRate = item.getStockRateInword();
                Double effInRate = inRate;
                if (entity.getVendorId() != null) {
                    PrimaryGroup vendor = getCoreServiceHelper().getPrimaryGroupDao().getById(entity.getVendorId());
                    if (vendor.getPercentage() != null) {
                        effInRate = (1 + vendor.getPercentage() / 100) * inRate;
                    }
                }
				Vehicles vehicle = getCoreServiceHelper().getVehicleDao().getById(entity.getVehicleId());
				Double volume = (vehicle.getVolume() == 0) ? (vehicle.getBreadth() * vehicle.getHeight() * vehicle.getLength()) : vehicle.getVolume();

				Double effOutRate = item.getStockRate();
				Double effRate = 0.0;
				Double amount = 0.0;
				Double qty = entity.getNetWt();
                Double qtyKl = qty;
				switch (item.getItem().getMainItemid()) {
					case 1:
						if (entity.getEntryType() == 1) {
							log.info("Qty=" + qty);
							Double density = 1.52 * 1000;
							Double calcVol = qty / density;
                            log.info("calcVol=" + calcVol);
							if (calcVol < volume) {
								Double newHeight = vehicle.getHeight() - (calcVol/(vehicle.getBreadth() * vehicle.getLength()));
								material.setHeightCorrection(newHeight - vehicle.getHeight());
								qty = calcVol;
							}
                            else {
                                qty = volume;
                            }
                            log.info("Qty=" + qty);
							amount = qty * effInRate;
							effRate = effInRate;
						} else {
                            log.info("Qty=" + qty);
							// Use factor as per metric ton
							double factor = 1.0;
							if (item.getItem().getFactor() != null) {
								factor = item.getItem().getFactor();
                                log.info("factor=" + factor);
							}

							qty = (qty / 1000) * factor;
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
							} else {
								effInRate = 0.0;
							}
							effRate = effInRate;
						} else {
							qty = entity.getQuantity() != null ? (1.0 * entity.getQuantity()) : 0;
							amount = effOutRate * qty;
							effRate = effOutRate;
						}
						break;
					case 12:
						if (entity.getEntryType() == 1) {
							amount = entity.getAmount() != null ? entity.getAmount() : 0;
							qtyKl = entity.getQuantityKl() != null ? (1.0 * entity.getQuantityKl()) : 0;
							effRate = inRate;
						} else {
							qtyKl = entity.getQuantityKl() != null ? (1.0 * entity.getQuantityKl()) : 0;
							amount = effOutRate * qtyKl;
							effRate = effOutRate;
						}
						break;
					case 16:
						if (entity.getEntryType() == 1) {
							amount = entity.getAmount() != null ? entity.getAmount() : 0;
							qtyKl = entity.getQuantityKl() != null ? (1.0 * entity.getQuantityKl()) : 0;
							if (qtyKl != null && qtyKl.intValue() > 0) {
								effInRate = amount / qtyKl;
							} else {
								effInRate = 0.0;
							}
							effRate = effInRate;
						} else {
							qtyKl = entity.getQuantityKl() != null ? (1.0 * entity.getQuantityKl()) : 0;
							amount = effOutRate * qtyKl;
							effRate = effOutRate;
						}
						break;
					case 14:
						amount = entity.getAmount() != null ? entity.getAmount() : 0;
						qty = entity.getQuantity() != null ? (1.0 * entity.getQuantity()) : 0;
						effRate = 0.0;
						break;
				}
                log.info("Before Setting Qty=" + qty);
				entity.setAmount(amount);
				entity.setRate(effRate);
				entity.setQuantity(qty);
                entity.setQuantityKl(qtyKl);
                entity.setVehicleQuantity(volume);
			}
			getCoreServiceHelper().getMaterialDao().saveOrUpdate(entity);
            if (entity.getEntryType().intValue() == 2) {
                getCoreServiceHelper().getLogMaterialDao().updateMaterialLog(entity.getMaterialId(), entity.getStatus(), entity.getLogMaterialId());
            }
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
				vehicle.setVehicleNumber(vehicle.getVehicleNumber());
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
			return getCoreServiceHelper().getVibhagDao().findActiveVibhags();
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
	public String readLogEntry(String stockName, String transporterName, String vibhagName, String siteName) {
		List<LogMaterial> list = getCoreServiceHelper().getLogMaterialDao().findLogByVibhag(null, vibhagName, true);
		Integer rtnVal = 0;
		for (LogMaterial logMaterial: list) {
			if (logMaterial.getLog().getSiteName().equals(siteName) &&
					logMaterial.getLog().getTransport().getVendorName().equals(transporterName) &&
					logMaterial.getItem().getStockItemname().equals(stockName) ) {
				if (rtnVal == 0 || rtnVal > logMaterial.getLogMaterialId()) {
					rtnVal = logMaterial.getLogMaterialId();
				}
			}
		}
		if (rtnVal == 0) {
			return null;
		}
		return rtnVal.toString();
	}

	@Override
	public boolean saveLog(String phone, String sms, Date date) {
		try {
			LogVO logVo = getCoreServiceHelper().getLogFromSms(phone, sms, date);
			logVo.setNewEntry(true);
			saveLog(logVo, null);
		} catch (Exception e) {
			log.error("Exception while saving raw logs: " + e.getMessage(), e);
			return false;
		}
		
		return true;
	}

    @Override
    public Boolean saveLog(String vibhagName, String transportName, String vehicleName, String siteName, List<String> stockNames, Boolean nightShift) {
        Vibhag vibhag = getCoreServiceHelper().getVibhagDao().findVibhagByName(vibhagName);
        if (vibhag != null) {
            StringBuffer str = new StringBuffer();
            str.append(nightShift?"N":"D");
            str.append("$" + StringUtils.trim(vehicleName));
            str.append("$" + StringUtils.trim(transportName));
            str.append("$");
            boolean first = true;
            for (String stock: stockNames) {
                if (!first) {
                    str.append(",");
                }
                first = false;
                str.append(StringUtils.trim(stock) + "@2@U");
            }
            str.append("$" + StringUtils.trim(siteName));
            return saveLog(vibhag.getPhone(), str.toString(), new Date());
        }
        else {
            return false;
        }

    }

    @Override
	public boolean saveLog(LogVO logVo, Integer attachId) {
		try {
			List<LogMaterial> logMaterials = getCoreServiceHelper().convertVOToModel(logVo);
			if (logMaterials != null && !logMaterials.isEmpty()) {
				LogBook logbook = logMaterials.get(0).getLog();
				if (logbook.getVehicle() != null && logbook.getTransport() != null && 
						logbook.getVehicle().getVendor().getVendorId().intValue() != logbook.getTransport().getVendorId().intValue()) {
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
