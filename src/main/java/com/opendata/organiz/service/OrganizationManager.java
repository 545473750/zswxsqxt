/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.Combotree;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.organiz.dao.OrganizationDao;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.vo.query.OrganizationQuery;
import com.opendata.sys.model.Partition;

/**
 * 组织机构业务层，对上层action提供支持
 * @author 顾保臣
 */
@Service
@Transactional
public class OrganizationManager extends BaseManager<Organization,java.lang.String>{

	private OrganizationDao organizationDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setOrganizationDao(OrganizationDao dao) {
		this.organizationDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.organizationDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(OrganizationQuery query) {
		return organizationDao.findPage(query);
	}
	
	@Transactional(readOnly=true)
	public String findSequence(String organization) {
		return this.organizationDao.findSequence(organization);
	}
	/**
	 * 通过单位id查询部门列表
	 * @param unitId
	 * @return
	 */
	public List<Organization> findDeptsByUnitId(String unitId){
		String hql = " from Organization org where org.df='0' and ";
		return null;
	}
	
	
	public List<Organization> findAllByDf(){
		return this.organizationDao.getHibernateTemplate().find(" from Organization org where org.df='0' order by parentId");
	}
	
	public List<Organization> findTopByDf(){
		return this.organizationDao.getHibernateTemplate().find(" from Organization org where org.df='0' and parentId is null ");
	}
	
	public List<Organization> findByPartition(String partitionId){
		return this.organizationDao.findAllByDf("partitions", "id", partitionId);
	}
	
	/**
	 * 取得一个分区下所有的组织机构，包括组织机构下的所有子组织机构ID
	 * @return
	 */
	public String findAllChildByPartition(Partition partition){
		StringBuffer  result = new StringBuffer("");
		Set<Organization> orgSet = partition.getOrganizations();
		if(orgSet!=null && orgSet.size()>0){
			for(Organization org : orgSet){
				result.append("'"+org.getId()+"',");
				getChindByParent(org,result);
			}
		}
		String strResult = result.toString();
		if(!"".equals(strResult)){
			strResult = strResult.substring(0,result.length()-1);
		}
		return strResult;
	}
	
	private void getChindByParent(Organization parentOrg,StringBuffer result){
		Set<Organization> orgSet = parentOrg.getOrganizations();
		if(orgSet!=null&&orgSet.size()>0){
			for(Organization org : orgSet){
				result.append("'"+org.getId()+"',");
				getChindByParent(org,result);
			}
		}
	}
	/**
	 * 通过用户id查询所在组织机构
	 * @param userId
	 * @return
	 */
	public List<Organization> findByUserId(String userId){
//		String hql = " from Organization where id in ( select organizationId from OrganizationUserRelation where userId = ? )";
		String hql = "  select organization from OrganizationUserRelation where userId = ? ";
		return organizationDao.findFastByHql(hql, userId);
	}
	/**
	 * 查询树形下拉菜单
	 * @return
	 */
	public List<Combotree>  getAllCombotree(){
		String hql = " from Organization where parentId is null and df='0' ";
		String hql2 = "  from Organization where parentId = ? and df='0' ";
		List<Organization> orgs = organizationDao.getHibernateTemplate().find(hql);
		List<Combotree> coms = new ArrayList<Combotree>();
		for(Organization org : orgs){
			Combotree com = new Combotree();
			com.setId(org.getId());
			com.setText(org.getName());
			com.setState("open");
			List<Organization> child = organizationDao.findFastByHql(hql2,org.getId());
			com.setChildren(getCombotree(child,org.getId()));
			coms.add(com);
		}
		return coms;
	}
	
	public List<Combotree> getCombotree(List<Organization> child ,String id){
		String hql = " from Organization where parentId = ? and df='0' ";
		List<Combotree> coms = new ArrayList<Combotree>();
		for(Organization org : child){
			Combotree com = new Combotree();
			com.setId(org.getId());
			com.setText(org.getName());
			List<Organization> children = organizationDao.findFastByHql(hql,org.getId());
			if(children.size()>0){
				com.setChildren(getCombotree(children,org.getId()));
			}
			
			coms.add(com);
		}
		return coms;
	}
	/**
	 * 查询子组织机构id
	 * @param orgId
	 * @return
	 */
	public String findChildrenIds(String orgId){
		if(StringUtils.isBlank(orgId)){
			return null;
		}
		String orgIds = "('"+orgId+"',";
		String hql = " select id from Organization where parentId = ? and df='0' ";
		List<String> list = organizationDao.findFastByHql(hql, orgId);
		for(String id : list){
			String ids = getOrgIds(list,id);
			orgIds += "'"+id+"',";;
			orgIds += ids;
		}
		orgIds=orgIds.substring(0, orgIds.lastIndexOf(","));
		orgIds += ")";
		return orgIds;
	}
	public String getOrgIds(List<String> list,String orgId){
		String hql = " select id from Organization where parentId = ? and df='0' ";
		String ids="";
		for(String id : list){
			List<String> children = organizationDao.findFastByHql(hql,id);
			if(children.size()>0){
				for(String id2:children){
					ids+="'"+id2+"',";
				}
				getOrgIds(children,id);
			}
			
		}
		return ids;
	}
	
	/**
	 * 更新ids同步下来的
	 */
	public void updateParentId(){
		List<Organization> list = this.findAll();
		for(Organization org:list){
			String parentRefId = org.getParentRefId();
			if(StringUtils.isNotBlank(parentRefId)&&!"EveryOne".equals(parentRefId)){
				Organization org1 = organizationDao.findByProperty("refId", parentRefId);
				org.setParentId(org1.getId());
				organizationDao.update(org1);
			}
		}
	}
	
}
