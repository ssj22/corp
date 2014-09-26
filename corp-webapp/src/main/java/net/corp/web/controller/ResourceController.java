package net.corp.web.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.corp.auth.AuthUser;
import net.corp.core.exception.CorpException;
import net.corp.core.model.PrimaryGroup;
import net.corp.core.model.StockItems;
import net.corp.core.model.Vehicles;
import net.corp.core.model.Vibhag;
import net.corp.core.service.MaterialService;
import net.corp.core.service.MessageService;
import net.corp.core.service.UserService;
import net.corp.core.vo.CountVO;
import net.corp.core.vo.LogVO;
import net.corp.core.vo.MaterialsVO;
import net.corp.core.vo.SiteVO;
import net.corp.core.vo.TabsVO;
import net.corp.core.vo.UserPreferenceVO;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/rest")
public class ResourceController {

	private static final Logger logger = Logger.getLogger(ResourceController.class); 
	
	@Autowired
	UserService userService;
	
	@Autowired
	MaterialService materialService;
	
	@Autowired
	MessageService messageService;
	
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public @ResponseBody AuthUser getLoggedInUser() {
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication auth = context.getAuthentication();
		if (auth.getPrincipal() instanceof AuthUser) {
			return (AuthUser) auth.getPrincipal();
		}
		return null;
	}
	
	@RequestMapping(value="/tabs", method=RequestMethod.GET)
	public @ResponseBody List<TabsVO> getTabs() {
		try {
			return userService.fetchAllTabs();
		} catch (CorpException e) {
			logger.error("Exception while retrieving tabs " + e.getExceptionMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="sendSms", method=RequestMethod.POST)
	public @ResponseBody String sendSms(@RequestBody MaterialsVO material) {
		try {
			messageService.sendMessage(null, null);
			return "OK";
		} catch (Exception e) {
			logger.error("Exception while sending message for material id " + material.getMaterialId() + " : " + e.getMessage(), e);
			return "ERROR";
		}
	}
	
	@RequestMapping(value="pref", method=RequestMethod.GET)
	public @ResponseBody UserPreferenceVO fetchUserPrefs(@RequestParam(value = "userId", required = true) Integer userId) {
		try {
			return userService.fetchUserPreference(userId);
		} catch (Exception e) {
			logger.error("Exception while fetchUserPrefs " + e.getMessage(), e);
			return null;
		}
	}
	
	@RequestMapping(value="pref", method=RequestMethod.POST)
	public @ResponseBody Boolean saveUserPrefs(@RequestBody UserPreferenceVO prefVo) {
		try {
			return userService.saveUserPreference(prefVo);
		} catch (Exception e) {
			logger.error("Exception while fetchUserPrefs " + e.getMessage(), e);
			return false;
		}
	}
	
	@RequestMapping(value="/counts", method=RequestMethod.GET)
	public @ResponseBody List<CountVO> getCounts() {
		try {
			return materialService.fetchAllCounts();
		} catch (CorpException e) {
			logger.error("Exception while retrieving counts " + e.getExceptionMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/sites", method=RequestMethod.GET)
	public @ResponseBody List<SiteVO> getSites() {
		try {
			return materialService.fetchAllSites();
		} catch (Exception e) {
			logger.error("Exception while retrieving sites " + e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/weight", method=RequestMethod.GET)
	public @ResponseBody String getWeight() {
		//return RestHelper.liveWeight();
		return null;
	}
	
	@RequestMapping(value="/dummyweight", method=RequestMethod.GET)
	public @ResponseBody String getDummyWeight() {
		return "4700";
	}
	
	@RequestMapping(value="/materials", method=RequestMethod.POST)
	public @ResponseBody MaterialsVO saveMaterial(@RequestBody MaterialsVO material) {
		return materialService.saveMaterial(material);
	}
	
	@RequestMapping(value="/link", method=RequestMethod.POST)
	public @ResponseBody Integer linkMaterial(@RequestBody List<String> materialIds,
			@RequestParam(value = "type", required = false) Integer type) {
		return materialService.linkMaterial(type, materialIds);
	}
	
	@RequestMapping(value="/materials", method=RequestMethod.GET)
	public @ResponseBody
	List<MaterialsVO> getMaterials(
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer pageSize,
			@RequestParam(value = "time", required = false) Integer time,
			@RequestParam(value = "from", required = false) Date from,
			@RequestParam(value = "to", required = false) Date to,
			@RequestParam(value = "more", required = false) boolean more,
			@RequestParam(value = "rel", required = false) Integer materialId) {
		try {
			return materialService.fetchAllMaterialEntries(pageSize, pageNumber, time, more, from, to, materialId);
		} catch (CorpException e) {
			logger.error("Exception while retrieving materials " + e.getExceptionMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/vehicles", method=RequestMethod.GET)
	public @ResponseBody List<Vehicles> getVehicles(
			@RequestParam(value = "name", required = false) String vehicleName,
			@RequestParam(value = "tname", required = false) String transportName) {
		try {
			if (vehicleName != null && !vehicleName.isEmpty()) {
				return materialService.searchVehicleByName(vehicleName);
			}
			
			if (transportName != null && !transportName.isEmpty()) {
				return materialService.searchVehicleByTransport(transportName);
			}
			
			return materialService.fetchAllVehicles();
		} catch (Exception e) {
			logger.error("Exception while retrieving Vehicles " + e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/vibhags", method=RequestMethod.GET)
	public @ResponseBody List<Vibhag> getVibhags(
			@RequestParam(value = "name", required = false) String vibhagName) {
		try {
			if (vibhagName != null && !vibhagName.isEmpty()) {
				return materialService.searchVibhagsByName(vibhagName);
			}
			return materialService.fetchAllVibhags(true);
		} catch (Exception e) {
			logger.error("Exception while retrieving Vibhags " + e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/stockItems", method=RequestMethod.GET)
	public @ResponseBody List<StockItems> getStockItems(
			@RequestParam(value = "name", required = false) String stockItemName) {
		try {
			if (stockItemName != null && !stockItemName.isEmpty()) {
				return materialService.searchStockItemsByName(stockItemName);
			}
			return materialService.fetchAllStockItems(true);
		} catch (Exception e) {
			logger.error("Exception while retrieving StockItems " + e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/primaryGroups", method=RequestMethod.GET)
	public @ResponseBody List<PrimaryGroup> getPrimaryGroups(
			@RequestParam(value = "name", required = false) String pgName) {
		try {
			if (pgName != null && !pgName.isEmpty()) {
				return materialService.searchPrimaryGroupsByName(pgName);
			}
			return materialService.fetchAllPrimaryGroups(true);
		} catch (Exception e) {
			logger.error("Exception while retrieving PrimaryGroups " + e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/eligVibhags", method=RequestMethod.GET)
	public @ResponseBody List<String> getEligVibhags() {
		try {
			return materialService.fetchEligVibhags();
		}
		catch(Exception e) {
			logger.error("Exception while retrieving Eligible Vibhag: " + e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/preentry", method=RequestMethod.GET)
	public @ResponseBody Map<String, Map<String, List<String>>> getStaticData(@RequestParam(value = "name", required = false) String vibhagName) {
		try {
			return materialService.fetchStaticDataForVibhag(vibhagName);
		}
		catch(Exception e) {
			logger.error("Exception while retrieving Static data for Vibhag " + vibhagName + ":" + e.getMessage(), e);
		}
		return null;
	}
			
	
	@RequestMapping(value="/logs", method=RequestMethod.GET)
	public @ResponseBody LogVO getLogs(
			@RequestParam(value = "vno", required = false) String vehicleNo,
			@RequestParam(value = "time", required = false) Integer time,
			@RequestParam(value = "from", required = false) Date from,
			@RequestParam(value = "to", required = false) Date to,
			@RequestParam(value = "shift", required = false) Integer shift) {
		
		return materialService.fetchLogByCriteria(vehicleNo, time, from, to, shift);
	}
	
	@RequestMapping(value="/logs", method=RequestMethod.POST)
	public @ResponseBody Boolean enterLog(
			@RequestParam(value = "vno", required = true) String vno,
			@RequestParam(value = "entryType", required = true) Integer entryType) {
		Timestamp date = new Timestamp(System.currentTimeMillis());
		if (entryType != null) {
			if (entryType.intValue() == 1) {
				return materialService.updateLogEntry(vno, date, null);
			}
			else {
				return materialService.updateLogEntry(vno, null, date);
			}
		}
		return false;
		
	}
}
