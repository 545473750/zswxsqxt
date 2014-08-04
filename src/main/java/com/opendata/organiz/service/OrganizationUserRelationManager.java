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

import com.opendata.common.base.*;

import com.opendata.organiz.model.*;
import com.opendata.organiz.vo.query.OrganizationUserRelationQuery;
import com.opendata.organiz.vo.query.UserQuery;
import com.opendata.organiz.dao.*;

/**
 * 组织机构和用户关系业务层
 * @author 顾保臣
 */
@Service
@Transactional
public class OrganizationUserRelationManager extends BaseManager<OrganizationUserRelation, java.lang.String> {

	private OrganizationUserRelationDao organizationUserRelationDao;
	
	@Transactional(readOnly=true)
	public Page findPage(OrganizationUserRelationQuery query) {
		return organizationUserRelationDao.findPage(query);
	}
	
	@Transactional(readOnly=true)
	public Page findByUserPage(UserQuery query){
		return organizationUserRelationDao.findByUserPage(query);
	}
	
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setOrganizationUserRelationDao(OrganizationUserRelationDao dao) {
		this.organizationUserRelationDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.organizationUserRelationDao;
	}

	/** 
	 * 清空所有的和当前部门关联的用户记录
	 * @param organizationId
	 * @return
	 */
	public int removeOURByOrganizationId(String organizationId) {
		return this.organizationUserRelationDao.removeOURByOrganizationId(organizationId);
	}

	/**
	 * 清空所有的和当前用户关联的组织机构记录
	 * @param id
	 */
	public int removeOURByUserId(String userId) {
		return this.organizationUserRelationDao.removeOURByUserId(userId);
	}

	public List<OrganizationUserRelation> findAllByOrgId(String organizationId) {
		return this.organizationUserRelationDao.findAllByOrgId(organizationId);
	}
	
	/**
	 * 查询外请教师，在组班申请时使用
	 * @param query
	 * @return
	 */
	public Page findPageOutTeacher(OrganizationUserRelationQuery query){
		return this.organizationUserRelationDao.findPageOutTeacher(query);
	}

}
