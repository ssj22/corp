package net.corp.core.dao.impl;

import java.util.List;

import net.corp.core.dao.StockItemDAO;
import net.corp.core.model.StockItems;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

public class StockItemDAOImpl extends GenericDAOImpl<StockItems, Integer> implements StockItemDAO {

	@SuppressWarnings("unchecked")
	@Override
	public StockItems findStockByName(String stockName) {
		Criteria crit = getSession().createCriteria(StockItems.class);
		crit.add(Restrictions.eq("stockItemname", stockName));
		
		List<StockItems> list = crit.list(); 
		
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		
		return null; 
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<StockItems> searchStockItemsByName(String stockItemName) {
		Criteria crit = getSession().createCriteria(StockItems.class);
		crit.add(Restrictions.ilike("stockItemname", stockItemName));
		return crit.list();
	}

}
