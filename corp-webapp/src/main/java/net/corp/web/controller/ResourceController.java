package net.corp.web.controller;

import java.util.List;

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
import net.corp.core.vo.MaterialsVO;
import net.corp.core.vo.SiteVO;
import net.corp.core.vo.TabsVO;
import net.corp.web.util.RestHelper;

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
			messageService.sendMessage(material);
			return "OK";
		} catch (Exception e) {
			logger.error("Exception while sending message for material id " + material.getMaterialId() + " : " + e.getMessage(), e);
			return "ERROR";
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
		return RestHelper.liveWeight();
	}
	
	@RequestMapping(value="/materials", method=RequestMethod.POST)
	public @ResponseBody MaterialsVO saveMaterial(@RequestBody MaterialsVO material) {
		return materialService.saveMaterial(material);
	}
	
	@RequestMapping(value="/materials", method=RequestMethod.GET)
	public @ResponseBody
	List<MaterialsVO> getMaterials(
			@RequestParam(value = "page", required = false) Integer pageNumber,
			@RequestParam(value = "size", required = false) Integer pageSize,
			@RequestParam(value = "time", required = false) Integer time,
			@RequestParam(value = "more", required = false) boolean more) {
		try {
			return materialService.fetchAllMaterialEntries(pageSize, pageNumber, time, more);
		} catch (CorpException e) {
			logger.error("Exception while retrieving materials " + e.getExceptionMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/vehicles")
	public @ResponseBody List<Vehicles> getVehicles(
			@RequestParam(value = "name", required = false) String vehicleName) {
		try {
			if (vehicleName != null && !vehicleName.isEmpty()) {
				return materialService.searchVehicleByName(vehicleName);
			}
			
			return materialService.fetchAllVehicles();
		} catch (Exception e) {
			logger.error("Exception while retrieving Vehicles " + e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/vibhags")
	public @ResponseBody List<Vibhag> getVibhags(
			@RequestParam(value = "name", required = false) String vibhagName) {
		try {
			if (vibhagName != null && !vibhagName.isEmpty()) {
				return materialService.searchVibhagsByName(vibhagName);
			}
			return materialService.fetchAllVibhags();
		} catch (Exception e) {
			logger.error("Exception while retrieving Vibhags " + e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/stockItems")
	public @ResponseBody List<StockItems> getStockItems(
			@RequestParam(value = "name", required = false) String stockItemName) {
		try {
			if (stockItemName != null && !stockItemName.isEmpty()) {
				return materialService.searchStockItemsByName(stockItemName);
			}
			return materialService.fetchAllStockItems();
		} catch (Exception e) {
			logger.error("Exception while retrieving StockItems " + e.getMessage(), e);
		}
		return null;
	}
	
	@RequestMapping(value="/PrimaryGroups")
	public @ResponseBody List<PrimaryGroup> getPrimaryGroups(
			@RequestParam(value = "name", required = false) String pgName) {
		try {
			if (pgName != null && !pgName.isEmpty()) {
				return materialService.searchPrimaryGroupsByName(pgName);
			}
			return materialService.fetchAllPrimaryGroups();
		} catch (Exception e) {
			logger.error("Exception while retrieving PrimaryGroups " + e.getMessage(), e);
		}
		return null;
	}
}
