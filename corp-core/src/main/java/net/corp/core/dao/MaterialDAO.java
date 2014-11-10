package net.corp.core.dao;

import java.util.Date;
import java.util.List;

import net.corp.core.model.Materials;
import net.corp.core.vo.SiteVO;

public interface MaterialDAO extends GenericDAO<Materials, Integer> {
	 List<Materials> findPaginatedMaterialEntries(Integer pageNumber, Integer pageSize, Integer time, boolean more, Date from, Date to);
	 List<Long> findAllCounts(Integer time);
	 List<SiteVO> findAllSites();
	 Integer linkMaterial(List<Integer> children);
	 Integer unlinkMaterial(List<Integer> children);
	 List<Materials> findRelatedEntries(Integer materialId);
}
