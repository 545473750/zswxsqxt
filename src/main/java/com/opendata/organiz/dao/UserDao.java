/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.dao;


import com.opendata.common.base.*;
import cn.org.rapid_framework.page.*;

import com.opendata.organiz.model.*;
import com.opendata.organiz.vo.query.*;

import org.springframework.stereotype.Repository;

/**
 * 用户dao层，用于和数据库交互
 * @author顾保臣
 */
@Repository
public class UserDao extends BaseHibernateDao<User, String> {

	public Class getEntityClass() {
		return User.class;
	}
	
	public Page findPage(UserQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from User t where 1=1 "
				  		+ "/~ and t.loginname = {loginname} ~/"
				  		+ "/~ and t.password = {password} ~/"
				  		+ "/~ and t.username like '%[username]%' ~/"
//				  		+ "/~ and t.phone = {phone} ~/"
				  		+ "/~ and t.ouRelations.organization.name like '%[deptName]%' ~/"
				  		+ "/~ and t.deptId = {deptId} ~/"
				  		+ "/~ and t.source = {source} ~/"
				  		+ "/~ and t.df = {df} ~/"  // 删除标识
					+ "/~ and t.ts >= {createtimeBegin} ~/"
					+ "/~ and t.ts <= {createtimeEnd} ~/"
						+ "/~ and t.abledFlag = {abledFlag} ~/" ;// 启用停用标识
		 		if(query.getOrganizIds()!=null&&!"".equals(query.getOrganizIds())){
		 			sql += " and t.ouRelations.organizationId in (" + query.getOrganizIds() +")";
		 		}
		 	
		 		sql += "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}
	
	/**
	 * 加载组织机构下的用户
	 * @param query
	 * @return
	 */
	public Page findPageCommon(UserQuery query) {
		
		String hql = "select t from User t inner join fetch t.depts d " +
						"where 1=1" +
						"/~ and t.loginname = {loginname} ~/" +
						"/~ and t.password = {password} ~/" + 
						"/~ and t.username like '%[username]%' ~/" + 
						"/~ and t.phone = {phone} ~/" + 
						"/~ and t.df = {df} ~/" +
						"/~ and t.source = {source} ~/" +
						"/~ and t.abledFlag = {abledFlag} ~/" +
						"/~ and d.id = {deptId} ~/" +
						"/~ order by [sortColumns] ~/";

        return pageQuery(hql,query);
	}
	
}
