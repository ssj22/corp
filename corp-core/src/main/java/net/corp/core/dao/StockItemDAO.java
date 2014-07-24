package net.corp.core.dao;

import java.util.List;

import net.corp.core.model.StockItems;

public interface StockItemDAO extends GenericDAO<StockItems, Integer> {

	StockItems findStockByName(String stockName);
	List<StockItems> searchStockItemsByName(String stockItemName);
	
}
