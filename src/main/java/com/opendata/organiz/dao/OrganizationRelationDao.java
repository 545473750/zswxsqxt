/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.dao;

import java.util.List;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.*;

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
public class OrganizationRelationDao extends BaseHibernateDao<OrganizationUserRelation,java.lang.String>{

	public Class getEntityClass() {
		return OrganizationRelation.class;
	}
	
	public Page findPage(OrganizationUserRelationQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from OrganizationRelation t where 1=1 "
				  		+ "/~ and t.organizationId = {organizationId} ~/"
				  		+ "/~ and t.organization.name like  '%[orgname]%'  ~/"
				  		+ "/~ and t.sortNumber = {sortNumber} ~/"
				+ "/~ order by [sortColumns] ~/";
		
        return pageQuery(sql,query);
	}
	
	
}
