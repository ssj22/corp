package net.corp.biz.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.corp.core.dao.MaterialDAO;
import net.corp.core.exception.CorpException;
import net.corp.core.service.MaterialService;
import net.corp.core.vo.MaterialsVO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext-test.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class MaterialServiceTest {
	
	@Resource
	private MaterialDAO materialDAO;
	
	@Resource
	private MaterialService materialService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testFetchAllMaterialEntries() {
		try {
			List<MaterialsVO> list = materialService.fetchAllMaterialEntries(100, 1, null, false, null, null, null);
			if (list != null && !list.isEmpty()) {
				System.out.println(list.get(0).getChallanNo());
			}
		} catch (CorpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testLinkMaterial() {
		List<Integer> list = new ArrayList<Integer>();
		list.add(142886);
		list.add(142885);
		System.out.println(materialDAO.linkMaterial(list));
	}
	
	@Test
	public void testSaveMaterial() {
		List<MaterialsVO> list = null;
		try {
			list = materialService.fetchAllMaterialEntries(1, 1, null, false, null, null, null);
//			Materials entity = new Materials(); 
//			entity.setStatus("INITIATED");
//			entity.setMaterialId(23);
//			materialDAO.saveOrUpdate(entity);
//			System.out.println(entity.getMaterialId());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && !list.isEmpty()) {
			MaterialsVO materialsVO = list.get(0);
			System.out.println(materialsVO.getTareWt());
			materialsVO.setTareWt(31.0);
			materialService.saveMaterial(materialsVO);
			System.out.println(materialsVO.getMaterialId());
		}
		
		
	}
	
	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	
}
