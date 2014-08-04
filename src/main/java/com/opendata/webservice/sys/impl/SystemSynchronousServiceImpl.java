package com.opendata.webservice.sys.impl;

import java.util.HashSet;
import java.util.Set;

import javax.jws.WebService;

import com.opendata.organiz.model.OrganizationUserRelation;
import com.opendata.organiz.model.User;
import com.opendata.sys.service.SystemManager;
import com.opendata.webservice.sys.SystemSynchronousService;

@WebService(endpointInterface="com.opendata.webservice.sys.SystemSynchronousService")
public class SystemSynchronousServiceImpl implements SystemSynchronousService{

	/**
	 * 系统同步业务对象
	 */
	private SystemManager systemManager;
	public void setSystemManager(SystemManager systemManager) {
		this.systemManager = systemManager;
	}
	
	@Override
	public String[][] getAllData(String code, String username, String password, int startIndex, int endIndex) {
		// 操作流程
		// 首先根据code,查找到对应的数据记录,判断username和password是否匹配,如果匹配则返回关联的用户列表
		com.opendata.sys.model.System system = this.systemManager.findByDf("code", code);
		if(system != null) {
			// 判断username和password是否匹配
			if(system.getUsername().equals(username) && system.getPassword().equals(password)) {
				Set<User> users = new HashSet<User>();
				// 支持分页
				if(startIndex == 0 && endIndex == 0) {
					// 用户列表
					users = system.getUsers();
				} else {
					users = this.systemManager.findAllData(system.getUsers(), startIndex, endIndex);
				}
				
				if(users != null && users.size() > 0) {
					String[][] userList = new String[users.size()][4];
					int row = 0;
					for(User user : users){
						userList[row][0] = user.getId(); // 唯一标示
						userList[row][1] = user.getUsername(); // 用户名
						userList[row][2] = null; // 组织机构ids
						userList[row][3] = null; // 组织机构名称
						// 组织机构
						Set<OrganizationUserRelation> ours = user.getOuRelations();
						StringBuffer organizationIds = new StringBuffer();
						StringBuffer organizationNames = new StringBuffer();
						for (OrganizationUserRelation our : ours) {
							if(our.getOrganization() != null) {
								organizationIds.append(our.getOrganization().getId() + ",");
								organizationNames.append(our.getOrganization().getName() + ",");
							}
						}
						if(organizationIds.toString().length() > 0) {
							String _organizationIds = organizationIds.toString().substring(0, organizationIds.toString().length() - 1);
							userList[row][2] = _organizationIds; // 组织机构ids
						}
						if(organizationNames.toString().length() > 0) {
							String _organizationNames = organizationNames.toString().substring(0, organizationNames.toString().length() - 1);
							userList[row][3] = _organizationNames; // 组织机构名称
						}
					}
					
					return userList;
				}
			} else {
				// 用户名或密码错误
			}
		} else {
			// 没有匹配的数据
		}
		
		return null;
	}
	
}
