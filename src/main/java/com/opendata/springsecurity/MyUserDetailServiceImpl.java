package com.opendata.springsecurity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.UserManager;
import com.opendata.sys.model.Resources;
import com.opendata.sys.service.ResourcesManager;


/**
 * 根据登录用户取得用户权限
 * @author 付威
 *
 */
public class MyUserDetailServiceImpl implements UserDetailsService {
	
	private UserManager userManager;
	private ResourcesManager resourcesManager;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User users = this.userManager.findByName(username);
		if(users == null) {
			throw new UsernameNotFoundException(username);
		}
		Collection<GrantedAuthority> grantedAuths = obtionGrantedAuthorities(users);
		
		boolean enables = true;
		boolean accountNonExpired = true;
		boolean credentialsNonExpired = true;
		boolean accountNonLocked = true;
		
		org.springframework.security.core.userdetails.User userdetail = new org.springframework.security.core.userdetails.User(users.getLoginname(), users.getPassword(), enables, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuths);
		return userdetail;
	}
	
	//取得用户的权限
	private Set<GrantedAuthority> obtionGrantedAuthorities(User user) {
		Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
		List<Resources> resourcesList = resourcesManager.findByUser(user);
		for(Resources res : resourcesList) {
			
			if(res.getPermission()!=null){
				authSet.add(new GrantedAuthorityImpl(res.getPermission().getId()));
			}
		}
		return authSet;
	}
	
	public UserManager getUserManager() {
		return userManager;
	}
	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setResourcesManager(ResourcesManager resourcesManager) {
		this.resourcesManager = resourcesManager;
	}
}