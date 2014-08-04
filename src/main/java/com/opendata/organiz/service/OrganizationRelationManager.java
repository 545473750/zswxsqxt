/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.organiz.dao.OrganizationRelationDao;
import com.opendata.organiz.model.OrganizationRelation;
import com.opendata.organiz.vo.query.OrganizationUserRelationQuery;

/**
 * 组织机构和用户关系业务层
 * @author 顾保臣
 */
@Service
@Transactional
public class OrganizationRelationManager extends BaseManager<OrganizationRelation, java.lang.String> {

	private OrganizationRelationDao organizationRelationDao;
	
	@Transactional(readOnly=true)
	public Page findPage(OrganizationUserRelationQuery query) {
		return organizationRelationDao.findPage(query);
	}
	
	
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	
	public EntityDao getEntityDao() {
		return this.organizationRelationDao;
	}

	

	public void setOrganizationRelationDao(
			OrganizationRelationDao organizationRelationDao) {
		this.organizationRelationDao = organizationRelationDao;
	}

}
