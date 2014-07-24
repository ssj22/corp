package net.corp.biz.test;

import java.util.Calendar;

import javax.annotation.Resource;

import net.corp.core.exception.CorpException;
import net.corp.core.service.UserService;
import net.corp.core.vo.AddressVO;
import net.corp.core.vo.UserVO;

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
public class UserServiceTest {
	
	@Resource
	private UserService userService;
	
	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testFetchActiveUserByUsername() {
		String username = "Shyam22";
		try {
			UserVO userVo = userService.fetchActiveUserByUsername(username);
			System.out.println("Full Name = " + userVo.getFullName() + " followed by Privileges: ");
			if (userVo.getPrivileges() != null) {
				for (String priv: userVo.getPrivileges()) {
					System.out.println(priv);
				}
			}
		} catch (CorpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testSaveUser() {
		System.out.println("In the Method");
		UserVO userVo = new UserVO();
		userVo.setActive(true);
		
		AddressVO addressVo = new AddressVO();
		addressVo.setActive(true);
		addressVo.setAddressLine1("J1 203");
		addressVo.setAddressLine2("Empire Estate Chinchwad");
		addressVo.setAddressLine3("Near Old mumbai pune highway");
		addressVo.setCity("Pune");
		addressVo.setDistrict("Pune");
		addressVo.setState("Maharashtra");
		addressVo.setPincode(411019);
		userVo.setAddress(addressVo);
		
		Calendar cal = Calendar.getInstance();
		cal.set(1980, 8, 22);
		userVo.setDob(cal.getTime());
		
		userVo.setFirstName("Sushil");
		userVo.setLastName("Joshi");
		userVo.setFullName("SUSHIL_JOSHI");
		userVo.setGuestUser(false);
		//PasswordEncoder encoder = new BCryptPasswordEncoder();
		userVo.setPassword("$2a$10$WtbMxauBToDI/0tWs3LjeOhs/w1bOUgO0yvwlTN6mmGMNo0A9dFgi"); // raw pwd = test
		userVo.setPhone(9422464281l);
		userVo.setTitle("Project Manager");
		userVo.setUsername("user1");
				
		try {
			if (userService.saveUser(userVo)) {
				System.out.println(userVo.getUserId());
			}
		} catch (CorpException e) {
			e.printStackTrace();
		}
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
