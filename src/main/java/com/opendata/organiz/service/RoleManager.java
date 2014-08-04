/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.organiz.dao.RoleDao;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.vo.query.RoleQuery;

/**
 * 角色的service层 负责处理业务逻辑
 * @author 付威
 */
@Service
@Transactional
public class RoleManager extends BaseManager<Role, java.lang.String> {

	private RoleDao roleDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setRoleDao(RoleDao dao) {
		this.roleDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.roleDao;
	}
	
	/**
	 * 根据查询条件查询 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page findPage(RoleQuery query) {
		return roleDao.findPage(query);
	}
	
	/**
	 * 根据用户查询用户所属角色
	 * @param userId 用户ID
	 * @return 角色集合
	 */
	public List<Role> findByUser(String userId){
		return roleDao.getHibernateTemplate().find("select role from Role role inner join role.users as user where user.id=?",userId);
	}
	
	/**
	 * 根据code查询角色
	 * @param code 
	 * @return 角色
	 */
	public Role findByCode(String code){
		Object obj=this.roleDao.findOneByHql("select role from Role role where role.code=?",code);
		if(obj!=null)
		{
			return (Role)obj;
		}
		return null;
	}
}
