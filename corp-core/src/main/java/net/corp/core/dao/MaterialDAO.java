package net.corp.core.dao;

import java.util.List;

import net.corp.core.model.Materials;
import net.corp.core.vo.SiteVO;

public interface MaterialDAO extends GenericDAO<Materials, Integer> {
	 List<Materials> findPaginatedMaterialEntries(Integer pageNumber, Integer pageSize, Integer time, boolean more);
	 List<Long> findAllCounts(Integer time);
	 List<SiteVO> findAllSites();
}
