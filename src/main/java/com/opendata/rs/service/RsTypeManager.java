/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.rs.dao.RsTypeDao;
import com.opendata.rs.model.RsType;
import com.opendata.rs.vo.query.RsTypeQuery;

/**
 * 业务层,对上层action提供支持
 * @author 王海龙
 */
@Service
@Transactional
public class RsTypeManager extends BaseManager<RsType, java.lang.String> {

	private RsTypeDao rsTypeDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setRsTypeDao(RsTypeDao dao) {
		this.rsTypeDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.rsTypeDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(RsTypeQuery query) {
		return rsTypeDao.findPage(query);
	}
	
}
