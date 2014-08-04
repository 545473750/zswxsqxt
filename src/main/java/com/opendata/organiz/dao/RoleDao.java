/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.dao;


import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.*;
import com.opendata.organiz.model.*;
import com.opendata.organiz.vo.query.*;
import org.springframework.stereotype.Repository;

/**
 * 角色管理的DAO层 负责和数据库的交互
 * @author 付威
 */
@Repository
public class RoleDao extends BaseHibernateDao<Role,String>{

	public Class getEntityClass() {
		return Role.class;
	}
	/**
	 * 根据查询条件查询结果 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	public Page findPage(RoleQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
				String sql = "select t from Role t where 1=1 "
						+ "/~ and t.code like '%[code]%' ~/"
				  		+ "/~ and t.name like '%[name]%'  ~/"
				  		+ "/~ and t.description = {description} ~/"
				  		+ "/~ and t.df = {df} ~/"
				  		+ "/~ and t.partitionId = {partitionId} ~/"
					+ "/~ and t.ts >= {createtimeBegin} ~/"
					+ "/~ and t.ts <= {createtimeEnd} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}
	

}
