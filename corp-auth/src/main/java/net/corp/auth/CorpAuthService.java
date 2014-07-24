package net.corp.auth;

import java.util.ArrayList;
import java.util.List;

import net.corp.core.exception.CorpException;
import net.corp.core.service.UserService;
import net.corp.core.vo.UserVO;

import org.apache.log4j.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CorpAuthService implements UserDetailsService {

	private UserService userService;
	private static final Logger LOGGER = Logger.getLogger(CorpAuthService.class);
	
	@Override
	public AuthUser loadUserByUsername(String username)
			throws UsernameNotFoundException {
		AuthUser auth = null;
		
		try {
			UserVO userVo = getUserService().fetchActiveUserByUsername(username);
			if (userVo == null) {
				String strErr = "User Not Found";
				LOGGER.error(strErr);
				throw new UsernameNotFoundException(strErr);
			}
			else {
				auth = new AuthUser();
				auth.setUser(userVo);
				List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
				authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
				auth.setAuthorities(authorities);
			}
		} catch (CorpException e) {
			LOGGER.error(e.getExceptionMessage() + e.getMessage(), e.getException());
		}
		
		return auth;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}
	
}
