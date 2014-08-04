/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.application.dao.PermissionDao;
import com.opendata.application.model.Application;
import com.opendata.application.model.Permission;
import com.opendata.application.vo.query.PermissionQuery;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.sys.service.ResourcesManager;

/**
 * 应用访问入口的service层 负责处理业务逻辑
 * @author 付威
 */
@Service
@Transactional
public class PermissionManager extends BaseManager<Permission,java.lang.String>{

	private PermissionDao permissionDao;
	
	private ResourcesManager resourcesManager;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setPermissionDao(PermissionDao dao) {
		this.permissionDao = dao;
	}
	
	
	
	public void setResourcesManager(ResourcesManager resourcesManager) {
		this.resourcesManager = resourcesManager;
	}



	public EntityDao getEntityDao() {
		return this.permissionDao;
	}
	
	/**
	 * 查询 执行分页
	 * @param query
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page findPage(PermissionQuery query) {
		return permissionDao.findPage(query);
	}
	
	/**
	 * 取得所有的应用访问入口
	 */
	public List<Permission> findAll(){
		return permissionDao.findAllByDf();
	}
	
	
	/**
	 * 取得某应用的所有应用访问入口 根据父访问入口排序
	 * @param appId
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Permission> findByApp(String appId){
		List<Permission> list = this.permissionDao.getHibernateTemplate().find(" from Permission  where df=0 and applicationId=? order by parentId asc,sequence asc", appId);
		return list;
	}
	
	
	/**
	 * 取得某应用某父访问入口下的所有子访问入口
	 * @param parentId 父访问入口ID
	 * @param appId 应用ID
	 * @return
	 */
	@Transactional(readOnly=true)
	public List<Permission> findByParentId(String parentId,String appId){
		if(parentId==null){
			return this.permissionDao.getHibernateTemplate().find(" from Permission per where df=0 and parentId is null and applicationId=? ", appId);
		}else{
			return this.permissionDao.getHibernateTemplate().find(" from Permission per where df=0 and parentId=? and applicationId=? ", parentId,appId);
		}
	}

	
	
	/**
	 * 根据Code查询权限
	 * @param name
	 * @return
	 */
	@Transactional(readOnly=true)
	public Permission findByCode(String code){
		List<Permission> list = this.permissionDao.getHibernateTemplate().find(" from Permission per where df=0 and code=? ", code);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	
	
    /**
     * 删除访问入口 如果有子的访问入口一并删除
     * @param per
     */
	public void deleteByAllChild(Permission per){
		
		List<Permission> perList = findByParentId(per.getId(),per.getApplicationId());
		for(Permission tempPer : perList){
			deleteByAllChild(tempPer);
		}
		delete(per);
	}
	
	
	/**
	 * 删除访问入口对象
	 */
	public void delete(Permission per){
		//删除应用访问入口时，把与应用访问入口的菜单删除
		resourcesManager.deleteByPermission(per);
		per.setDf("1");
		super.delete(per);
	}
	
	
	/**
	 * 删除某应用下的所有访问入口
	 * @param app
	 */
	public void deleteByApplication(Application app){
		List<Permission> perList = permissionDao.findAllByDf("applicationId", app.getId());
		for(Permission per : perList){
			this.delete(per);
		}
	}
	
	
	/**
	 * 取得某应用下某父访问入口下最大排序号
	 * @param parentPermissionId 父访问入口ID
	 * @param applicationId 应用ID
	 * @return
	 */
	@Transactional(readOnly=true)
	public int findSequence(String parentPermissionId,String applicationId) {
		return this.permissionDao.findSequence(parentPermissionId,applicationId);
	}
	/**
	 * 统计URL是否存在
	 * @param url
	 * @return
	 */
	@Transactional(readOnly=true)
	public long countUrl(String url){
		String hql ="select count(*) from Permission p where p.url=?";
		Object obj = permissionDao.findOneByHql(hql, url);
		if(null!=obj){
			return (Long)obj;
		}
		return 0l;
	}
	
}
