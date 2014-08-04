/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.rs.dao.RsStructureDao;
import com.opendata.rs.model.RsStructure;
import com.opendata.rs.vo.query.RsStructureQuery;

/**
 * 业务层,对上层action提供支持
 * 
 * @author 王海龙
 */
@Service
@Transactional
public class RsStructureManager extends BaseManager<RsStructure,java.lang.String>{

	private RsStructureDao rsStructureDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setRsStructureDao(RsStructureDao dao) {
		this.rsStructureDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.rsStructureDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(RsStructureQuery query) {
		return rsStructureDao.findPage(query);
	}
	
	public List<RsStructure> findAllRoot() {
		return rsStructureDao.findAllRoot();
	}
	
}
