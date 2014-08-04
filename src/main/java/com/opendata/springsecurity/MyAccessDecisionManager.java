package com.opendata.springsecurity;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import com.opendata.application.model.Permission;
import com.opendata.application.service.PermissionManager;
import com.opendata.common.log.UserContextHolder;
import com.opendata.login.model.LoginInfo;
import com.opendata.sys.service.LogManager;

/**
 * 判断用户是否拥有所请求资源的权限 最重要的是decide方法，如果不存在对该资源的定义，直接放行； 否则，如果找到正确的角色，即认为拥有权限，并放行否则throw new AccessDeniedException("no right"); 这样，就会进入配置的错误页面 403.jsp页面
 * @author 付威
 */
public class MyAccessDecisionManager implements AccessDecisionManager {
	
	private PermissionManager permissionManager;
	
	private LogManager logManager;
	
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) 
				throws AccessDeniedException, InsufficientAuthenticationException {
		if(configAttributes == null) {
			return;
		}
		//所请求的资源拥有的权限(一个资源对多个权限)
		Iterator<ConfigAttribute> iterator = configAttributes.iterator();
		
		while(iterator.hasNext()) {
			ConfigAttribute configAttribute = iterator.next();
			//访问所请求资源所需要的权限
			String needPermission = configAttribute.getAttribute();
			
			//用户所拥有的权限authentication
			for(GrantedAuthority ga : authentication.getAuthorities()) {
				if(needPermission.equals(ga.getAuthority())) {
					LoginInfo loginUser = UserContextHolder.getUser();
					Permission per = permissionManager.getById(needPermission);
					loginUser.setPermission(per.getApplication().getName()+"-"+per.getName()+per.getUrl());
					UserContextHolder.setUser(loginUser);
					//添加日志					
//					Log log = new Log();
//					log.setDf("0");
//					log.setIp(loginUser.getIp());
//					log.setPermission(per.getApplication().getName()+"-"+per.getName()+per.getUrl());
//					log.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//					log.setType("APP_LOG001");
//					log.setUsername(loginUser.getUserName());
//					logManager.save(log);
					
					return;
				}
			}
		}
		//没有权限
		throw new AccessDeniedException(" 没有权限访问！ ");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public void setLogManager(LogManager logManager) {
		this.logManager = logManager;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}
}
