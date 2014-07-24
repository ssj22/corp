package net.corp.core.dao;

import java.util.List;

import net.corp.core.model.Vibhag;

public interface VibhagDAO extends GenericDAO<Vibhag, Integer> {

	Vibhag findVibhagByName(String vibhagName);
	
	List<Vibhag> searchVibhagsByName(String vibhagName);
	 
}
