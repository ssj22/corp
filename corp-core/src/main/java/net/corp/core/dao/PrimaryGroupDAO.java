package net.corp.core.dao;

import java.util.List;

import net.corp.core.model.PrimaryGroup;

public interface PrimaryGroupDAO extends GenericDAO<PrimaryGroup, Integer> {

	PrimaryGroup findPrimaryGroupByName(String transporterName);
	List<PrimaryGroup> searchPrimaryGroupsByName(String pgName);
}	
