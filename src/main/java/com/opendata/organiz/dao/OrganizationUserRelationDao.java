/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.dao;

import java.util.ArrayList;
import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.*;
import com.opendata.common.util.StrUtil;

import com.opendata.organiz.model.*;
import com.opendata.organiz.vo.query.OrganizationUserRelationQuery;
import com.opendata.organiz.vo.query.UserQuery;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 组织机构和用户关联关系dao层，用于对数据库的交互
 * @author顾保臣
 */
@Repository
public class OrganizationUserRelationDao extends BaseHibernateDao<OrganizationUserRelation,java.lang.String>{

	public Class getEntityClass() {
		return OrganizationUserRelation.class;
	}
	
	public Page findPage(OrganizationUserRelationQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from OrganizationUserRelation t where 1=1 "
				  		+ "/~ and t.organizationId = {organizationId} ~/"
				  		+ "/~ and t.userId = {userId} ~/"
				  		+ "/~ and t.user.username like  '%[username]%'  ~/"
				  		+ "/~ and t.user.userNum like  '%[userNum]%'  ~/"
				  		+ "/~ and t.organization.name like  '%[orgname]%'  ~/"
				  		+ "/~ and t.sortNumber = {sortNumber} ~/"
				+ "/~ order by [sortColumns] ~/";
		
		if(query.getOrganizationId() != null && query.getOrganizationId().equals("root")) {
			sql = "select t from OrganizationUserRelation t where 1=1 "
				  		+ " and t.organizationId is null "
				  		+ "/~ and t.userId = {userId} ~/"
				  		+ "/~ and t.sortNumber = {sortNumber} ~/"
				+ "/~ order by [sortColumns] ~/";
		}
		
        return pageQuery(sql,query);
	}
	
	/**
	 * 查询外请教师，在申请培训班时使用
	 * @param query
	 * @return
	 */
	public Page findPageOutTeacher(OrganizationUserRelationQuery query){
		String hql = "select t from OrganizationUserRelation t where 1=1 and t.organizationId != ?";
		List params = new ArrayList();
		params.add(query.getOrganizationId());
		if(StrUtil.isNotNullOrBlank(query.getUsername())){
			hql += " and t.user.username like ?";
			params.add("%"+query.getUsername()+"%");
		}
		if(StrUtil.isNotNullOrBlank(query.getOrgname())){
			hql += " and t.organization.name like ?";
			params.add("%"+query.getOrgname()+"%");
		}
		if(StrUtil.isNotNullOrBlank(query.getUserNum())){
			hql += " and t.user.userNum like ?";
			params.add("%"+query.getUserNum()+"%");
		}
		return super.findByHql(hql, query.getPageSize(), query.getPageNumber(), params.toArray());
	}
	
	public Page findByUserPage(UserQuery query){
		String sql = "select t from  User as t where t.id in ( select distinct user.id from User as user  left join user.ouRelations as our  where 1=1 "
	  		+ "/~ and user.loginname = {loginname} ~/";
		if(StringUtils.isNotBlank(query.getOrgids()) ){
			sql += " and t.id in ( select userId from OrganizationUserRelation where organization.id in "+query.getOrgids()+") ";
		}
		sql+= "/~ and user.password = {password} ~/"
	  		+ "/~ and user.username like '%[username]%' ~/"
	  		+ "/~ and user.phone = {phone} ~/"
	  		+ "/~ and our.organization.name like '%[deptName]%' ~/"
	  		//+ "/~ and our.organization.id = {deptId} ~/"//部门ID
	  		+ "/~ and user.station = {station} ~/"//岗位
	  		+ "/~ and user.stationLevel = {stationLevel} ~/"//岗位级别
	  		+ "/~ and user.df = {df} ~/"  // 删除标记
	  		+ "/~ and user.source = {source} ~/"  // 用户类别
		+ "/~ and user.ts >= {createtimeBegin} ~/"
		+ "/~ and user.ts <= {createtimeEnd} ~/"
			+ "/~ and user.isLeave = {isLeave} ~/"  //--在职状态
			+ "/~ and user.abledFlag = {abledFlag} ~/" ;// 启用禁用标记
		if(query.getOrganizIds()!=null&&!"".equals(query.getOrganizIds())){
			sql += " and our.organizationId in ("+query.getOrganizIds()+")";
		}
		sql += " ) /~ order by [sortColumns] ~/ ";

		return pageQuery(sql,query);
	}

	public int del(String organizationId, String userId) {
		String sql = "delete OrganizationUserRelation t " +
					 "where t.organizationId = '" + organizationId + "' and t.userId = '" + userId + "'";
		return this.getHibernateTemplate().bulkUpdate(sql);
	}

	public int removeOURByOrganizationId(String organizationId) {
		String sql = "delete OrganizationUserRelation t " +
		 			 "where t.organizationId = '" + organizationId + "'";
		if(organizationId != null && organizationId.equals("root")) {
			sql = "delete OrganizationUserRelation t " +
			 		"where t.organizationId is null";
		}
		
		return this.getHibernateTemplate().bulkUpdate(sql);
	}

	public int removeOURByUserId(String userId) {
		String sql = "delete OrganizationUserRelation t " +
				 "where t.userId = '" + userId + "'";
		
		return this.getHibernateTemplate().bulkUpdate(sql);
	}

	public List<OrganizationUserRelation> findAllByOrgId(String organizationId) {
		String hql = "select t from OrganizationUserRelation t where t.organizationId = '" + organizationId + "'";
		if(organizationId != null && organizationId.equals("root")) {
			hql = "select t from OrganizationUserRelation t where t.organizationId is null";
		}
		
		return this.findFastByHql(hql);
	}
	
}
