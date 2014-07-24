package net.corp.biz.test;

import java.util.List;

import javax.annotation.Resource;

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
@ContextConfiguration("classpath:applicationContext.xml")
@TransactionConfiguration(transactionManager = "transactionManager", defaultRollback = false)
@Transactional
public class MaterialServiceTest {
	
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
			List<MaterialsVO> list = materialService.fetchAllMaterialEntries(100, 1, null, false);
			if (list != null && !list.isEmpty()) {
				System.out.println(list.get(0).getChallanNo());
			}
		} catch (CorpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testSaveMaterial() {
		List<MaterialsVO> list = null;
		try {
			list = materialService.fetchAllMaterialEntries(1, 1, null, false);
		} catch (CorpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (list != null && !list.isEmpty()) {
			MaterialsVO materialsVO = list.get(0);
			System.out.println(materialsVO.getTareWt());
			materialsVO.setTareWt(31.0);
			materialService.saveMaterial(materialsVO);
		}
		
		
	}
	
	public MaterialService getMaterialService() {
		return materialService;
	}

	public void setMaterialService(MaterialService materialService) {
		this.materialService = materialService;
	}
	
}
