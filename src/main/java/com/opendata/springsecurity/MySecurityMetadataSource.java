package com.opendata.springsecurity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.opendata.application.model.Permission;
import com.opendata.application.service.PermissionManager;

/**
 * 
 * 最核心的地方，就是提供某个资源对应的权限定义，即getAttributes方法返回的结果。
 * 注意，使用的是AntUrlPathMatcher这个path matcher来检查URL是否与资源定义匹配，
 * 事实上你还要用正则的方式来匹配，或者自己实现一个matcher。
 * 此类在初始化时，应该取到所有资源及其对应角色的定义
 * 说明：对于方法的spring注入，只能在方法和成员变量里注入，
 * 如果一个类要进行实例化的时候，不能注入对象和操作对象，
 * 所以在构造函数里不能进行操作注入的数据。
 * @author 付威
 */
//1 加载资源与权限的对应关系
public class MySecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	private PermissionManager permissionManager;
	//由spring调用
	public MySecurityMetadataSource(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
		loadResourceDefine();
	}

	private static Map<String, Collection<ConfigAttribute>> resourceMap = null;

	public PermissionManager getPermissionManager() {
		return permissionManager;
	}

	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}
	//加载所有资源与权限的关系
	private void loadResourceDefine() {
		if(resourceMap == null) {
			resourceMap = new HashMap<String, Collection<ConfigAttribute>>();
			List<Permission> resources = this.permissionManager.findAll();
			for (Permission resource : resources) {
				Collection<ConfigAttribute> configAttributes = new ArrayList<ConfigAttribute>();
                //以权限名封装为Spring的security Object
				ConfigAttribute configAttribute = new SecurityConfig(resource.getId());
				configAttributes.add(configAttribute);
				resourceMap.put(resource.getUrl(), configAttributes);
			}
		}
		
		Set<Entry<String, Collection<ConfigAttribute>>> resourceSet = resourceMap.entrySet();
		Iterator<Entry<String, Collection<ConfigAttribute>>> iterator = resourceSet.iterator();
		
	}
	//返回所请求资源所需要的权限
	public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
		
		String requestUrl = ((FilterInvocation) object).getRequestUrl();
		if(resourceMap == null) {
			loadResourceDefine();
		}
		return resourceMap.get(requestUrl);
	}

}