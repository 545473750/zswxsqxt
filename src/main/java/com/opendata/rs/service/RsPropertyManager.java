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
import com.opendata.rs.dao.RsPropertyDao;
import com.opendata.rs.model.RsProperty;
import com.opendata.rs.vo.query.RsPropertyQuery;

/**
 * 业务层,对上层action提供支持
 * @author 王海龙
 */
@Service
@Transactional
public class RsPropertyManager extends BaseManager<RsProperty,java.lang.String>{

	private RsPropertyDao rsPropertyDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setRsPropertyDao(RsPropertyDao dao) {
		this.rsPropertyDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.rsPropertyDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(RsPropertyQuery query) {
		return rsPropertyDao.findPage(query);
	}
	
	/**
	 *根据id集合查询 
	 * @param ids
	 * @return
	 */
	public List<RsProperty> findByids(String ids){
		String hql="from RsProperty where id in (?)";
		return rsPropertyDao.getHibernateTemplate().find(hql, ids);
	}
	
	/**
	 * 根据编号查询
	 * @param code
	 * @return
	 */
	public RsProperty findByCode(String code){
		return rsPropertyDao.findByProperty("attrCode", code);
	}
	
}
