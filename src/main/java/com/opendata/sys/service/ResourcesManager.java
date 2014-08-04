/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.application.dao.PermissionDao;
import com.opendata.application.model.Permission;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.User;
import com.opendata.sys.dao.ResourcesDao;
import com.opendata.sys.model.LeftMenu;
import com.opendata.sys.model.Resources;
import com.opendata.sys.vo.query.ResourcesQuery;

/**
 * 菜单的service层 负责处理业务逻辑
 * @author 付威
 */
@Service
@Transactional
public class ResourcesManager extends BaseManager<Resources,java.lang.String>{

	private ResourcesDao resourcesDao;
	private Permission permission;
	private PermissionDao permissionDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setResourcesDao(ResourcesDao dao) {
		this.resourcesDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.resourcesDao;
	}
	
	/**
	 * 根据查询条件查询 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page findPage(ResourcesQuery query) {
		return resourcesDao.findPage(query);
	}
	
	
	/**
	 * 取得所有未被删除的菜单 按父菜单和排序号排序
	 */
	public List<Resources> findAllByDf(){
		return this.resourcesDao.getHibernateTemplate().find(" from Resources res where df=0  order by parentId,sequence ");
	}
	
	/**
	 * 取得某角色拥有的菜单权限
	 * @param roleId 角色ID
	 * @return
	 */
	public List<Resources> findByRole(String roleId){
		return this.resourcesDao.getHibernateTemplate().find("select  t FROM Resources t inner join t.roles as b where t.df='0' and b.df='0' and b.id = ? order by t.parentId,t.sequence",roleId);
	}
	/**
	 * 取得某角色拥有的菜单权限
	 * @param stationId 角色ID
	 * @return
	 */
	public List<Resources> findByStation(String stationId){
		return this.resourcesDao.getHibernateTemplate().find("select r from Resources r inner join r.stations as s where r.df='0' and s.df='0' and s.id = ? order by r.parentId,r.sequence",stationId);
	}
	
	/**
	 * 这是取得某用户的菜单权限 去除分区管理员的菜单权限 取得所有其他菜单权限
	 */
	public List<Resources> findByUserNoPartition(String userId){
		return this.resourcesDao.getHibernateTemplate().find("select distinct t FROM Resources t inner join t.roles as b where t.df='0' and b.df='0'  and b.id in (select  t.id FROM Role t inner join t.users as b where t.df='0' and b.df='0' and b.id = ? and t.code != 'partition_administrator' ) order by t.parentId,t.sequence",userId);
	}
	
	/**
	 * 取得用户的权限
	 * @param userId
	 * @return
	 */
	public List<Resources> findByUser(User user){
		List<Resources> list = resourcesDao.findFastByFreeSql("findPermission", user);
		List<Resources> resourcesList = new ArrayList<Resources>();
		for(Resources res :list){
			res = resourcesDao.getById(res.getId());
			resourcesList.add(res);
		}
		return resourcesList;
	}
	
	/**
	 * 查询所有的一级菜单
	 * @param userId
	 * @return
	 */
	public List<LeftMenu> findRootByUserNoPartition(String userId){
		String sql = "SELECT t.id,t.name,t.code,t.parent_id parentId,t.permission_id permissionId,t.type menuType FROM t_resources t WHERE t.type='0' and t.parent_id is null  "+
					"  AND t.id IN " +
					" (SELECT resources_id FROM t_role_resources  WHERE role_id in (SELECT role_id FROM t_user_role u WHERE u.user_id=? ))";
		List<LeftMenu> list = this.resourcesDao.findFastBySql(sql, LeftMenu.class, userId) ;
		return list;
	}
	
	/**
	 * 查询所有的子菜单
	 * @param userId
	 * @param ceId
	 * @return
	 */
	public List<LeftMenu> findChildByUserNoPartition(String userId,String parentId){
		String sql="SELECT a.id,a.code,a.parent_id parentId,a.permission_id permissionId,a.name,a.type menuType FROM t_resources a WHERE a.parent_id='"+parentId+"' "+
		"AND a.id IN(SELECT resources_id FROM t_role_resources b "+
		"WHERE b.role_id in (SELECT role_id FROM t_user_role WHERE user_id='"+userId+"'))";
		return this.resourcesDao.findFastBySql(sql,LeftMenu.class);
	}
	
	/**
	 * 取得某用户拥有的所有菜单权限
	 */
	public List<Resources> findByUser(String userId){
		return this.resourcesDao.getHibernateTemplate().find("select distinct t FROM Resources t inner join t.roles as b where t.df='0' and b.df='0' and b.id in (select  t.id FROM Role t inner join t.users as b where t.df='0' and b.df='0' and b.id = ?) order by t.parentId,t.sequence",userId);
	}
	
	/**
	 * 查询所有不是功能入口的菜单 根据父菜单排序
	 */
	public List<Resources> findAllByNoFunction(){
		List<Resources> resourcesList =  this.resourcesDao.getHibernateTemplate().find(" from Resources res where df=0 and type != "+Resources.TYPE_FUNCTION+" order by parentId,sequence ");
		return resourcesList;
	}
	
	

	
	/**
	 * 取得最上级的菜单 就是父菜单为null的菜单
	 */
	public List<Resources> findByTop(){
		List<Resources> resourcesList =  this.resourcesDao.getHibernateTemplate().find(" from Resources res where df=0 and parentId is null order by parentId,sequence ");
		return resourcesList;
	}
	
	
	/**
	 * 根据Code查询菜单
	 * @param name
	 * @return
	 */
	@Transactional(readOnly=true)
	public Resources findByCode(String code){
		List<Resources> list = this.resourcesDao.getHibernateTemplate().find(" from Resources per where df=0 and code=? ", code);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	/**
	 * 取得某应用下与应用访问入口有关的所有菜单
	 * @param applicationId
	 * @return
	 */
	public List<Resources> findByAppLication(String applicationId){
		List<Resources> resourcesList =  this.resourcesDao.getHibernateTemplate().find(" from Resources res where df=0 and permissionId in (select id from Permission per where df=0 and applicationId=? ) order by parentId,sequence", applicationId);
		List<Resources> resultList = new ArrayList<Resources>();
		Map<String,Resources> resultMap = new HashMap<String,Resources>();//定义一个Map避免重复
		String tempParentId ="";//记录临时的父ID 如果是相同的父ID 则不用重复取上级菜单
		for(Resources res : resourcesList){
			//如果不是相同菜单目录下的功能菜单，则添加父菜单
			if(!tempParentId.equals(res.getParentId())){
				addParent(res, resultList,resultMap);
				tempParentId = res.getParentId();
			}else{
				//如果相同的父菜单 则直接添加此菜单就行
				if(resultMap.get(res.getId())==null){
					resultList.add(res);
				}
				resultMap.put(res.getId(), res);
			}
		}
		return resultList;
	}
	
	/**
	 * 递归取得此菜单的所有上级菜单
	 */
	private void addParent(Resources res,List<Resources> resultList,Map<String,Resources> resultMap){
		if(res.getResources()!=null){
			addParent(res.getResources(),resultList,resultMap);
		}
		
		if(resultMap.get(res.getId())==null){
			resultList.add(res);
		}
		resultMap.put(res.getId(), res);

		
	}
	
	/**
	 * 删除与某访问入口管理的所有菜单
	 * @param per
	 */
	public void deleteByPermission(Permission per){
		List<Resources> resList = this.resourcesDao.findAllByDf("permissionId", per.getId());
		if(resList!=null){
			for(Resources res : resList){
				deleteByAllChild(res);
			}
		}
	}
	
	 /**
     * 删除访问入口 如果有子菜单入口一并删除
     * @param per
     */
	public void deleteByAllChild(Resources res){
		List<Resources> resList = this.resourcesDao.findAllByDf("parentId", res.getId());
		for(Resources tempRes : resList){
			deleteByAllChild(tempRes);
		}
		this.delete(res);
	}
	
	
	
	/**
	 * 删除菜单
	 */
	public void delete(Resources resources){
		//删除菜单时，把菜单和角色的关系也删除
		resources.setRoles(new HashSet<Role>(0));
		resources.setDf("1");
		super.delete(resources);
	}
	
	/**
	 * 根据父ID取得同一个父菜单下的最大的排序号
	 * @param parentRerourcesId 父菜单ID
	 * @return  最大排序号
	 */
	@Transactional(readOnly=true)
	public int findSequence(String parentRerourcesId) {
		return this.resourcesDao.findSequence(parentRerourcesId);
	}
	
}
