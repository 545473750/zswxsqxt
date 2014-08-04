/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2011
 */

package com.opendata.rs.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.rs.dao.RsResourcesDao;
import com.opendata.rs.model.RsResources;
import com.opendata.rs.vo.query.RsResourcesQuery;

/**
 * 业务层,对上层action提供支持
 * @author 王海龙
 */
@Service
@Transactional
public class RsResourcesManager extends BaseManager<RsResources,java.lang.String>{

	private RsResourcesDao rsResourcesDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setRsResourcesDao(RsResourcesDao dao) {
		this.rsResourcesDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.rsResourcesDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(RsResourcesQuery query) {
		return rsResourcesDao.findPage(query);
	}
	
	/**
	 * 查询用户收藏的资源
	 * @return
	 */
	public Page findResourceFavoriteByuser(RsResourcesQuery query,String userid){
		return rsResourcesDao.findResourceFavoriteByuser(query,userid);
	}
	
	/**
	 * 查询个人上传的资源
	 * @return
	 */
	public Page findResourceByuser(RsResourcesQuery query){
		return rsResourcesDao.findResourceByuser(query);
	}
	
	
	public Page<RsResources> findRootChildren(String tid, RsResourcesQuery query) {
		return rsResourcesDao.findRootChildren(tid,query);
	}
	
	public Page<RsResources> easySearch(RsResourcesQuery query) {
		return rsResourcesDao.easySearch(query);
	}
	
	/**
	 * 资源搜索
	 * @param query
	 * @return
	 */
	public Page<RsResources> resoucesSearch(RsResourcesQuery query) {
		return rsResourcesDao.resoucesSearch(query);
	}
	
	/**
	 * 按条件的资源
	 * 
	 * @param propertyName 字段名称
	 * @param value 字段的传入值
	 * @return
	 */
	public List<RsResources> findAllByProVal(String propertyName, Object value) {
		return rsResourcesDao.findAllBy(propertyName, value);
	}
	
	/**
	 * 按下载排行查询
	 * @param query
	 * @return
	 */
	public List<RsResources> findRanking() {
		String sql="from RsResources t order by downloadNumber desc";
		return rsResourcesDao.getHibernateTemplate().find(sql);
	}
	
	/**
	 * 所有未审核的资源
	 * @param query
	 */
	public Page<RsResources> findUnaudited(RsResourcesQuery query){
		return rsResourcesDao.findUnaudited(query);
	}
	
	/**
	 * 高级查询
	 * @param query
	 * @param map
	 * @return
	 */
	public Page<RsResources> advancedSearch(RsResourcesQuery query, Map<String,String> map) {
		return rsResourcesDao.advancedSearch(query,map);
	}
	
	/**
	 * 有关联关系的资源
	 * @param query
	 * @return
	 */
	public Page<RsResources> beRelatedResouces(RsResourcesQuery query){
		return rsResourcesDao.beRelatedResouces(query);
	}
	
	/**
	 * 未关联关系的资源
	 * @param query
	 * @return
	 */
	public Page<RsResources> irrelevantResouces(RsResourcesQuery query){
		return rsResourcesDao.irrelevantResouces(query);
	}
}
