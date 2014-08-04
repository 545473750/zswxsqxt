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
import com.opendata.rs.dao.RsExpandPropertyDao;
import com.opendata.rs.model.RsExpandProperty;
import com.opendata.rs.vo.query.RsExpandPropertyQuery;

/**
 * 业务处,对上层action提供支持
 * @author 王海龙
 */
@Service
@Transactional
public class RsExpandPropertyManager extends BaseManager<RsExpandProperty,java.lang.String>{

	private RsExpandPropertyDao rsExpandPropertyDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setRsExpandPropertyDao(RsExpandPropertyDao dao) {
		this.rsExpandPropertyDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.rsExpandPropertyDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(RsExpandPropertyQuery query) {
		return rsExpandPropertyDao.findPage(query);
	}
	
	/**
	 * 查询该资源所拥有的所有拓展属性
	 * @param resoucesId 资源ID
	 * @return
	 */
	public List<RsExpandProperty> findByresoucrsId(String resoucesId) {
		return rsExpandPropertyDao.findAllBy("resourcesId", resoucesId);
	}
	
	public void physicsDeleteByResource(String id) {
		rsExpandPropertyDao.physicsDeleteByResource(id);
	}
	
}
