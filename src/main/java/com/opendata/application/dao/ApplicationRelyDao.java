/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.dao;


import com.opendata.common.base.*;

import cn.org.rapid_framework.page.*;


import org.springframework.stereotype.Repository;

import com.opendata.application.model.*;
import com.opendata.application.vo.query.*;

/**
 * 应用依赖关系DAO层 负责和数据库的交互
 * @author 付威
 */


@Repository
public class ApplicationRelyDao extends BaseHibernateDao<ApplicationRely,java.lang.String>{

	public Class getEntityClass() {
		return ApplicationRely.class;
	}
	
	
	/**
	 * 根据查询条件查询 执行分页
	 * @param query 查询条件对象
	 * @return
	 */
	public Page findPage(ApplicationRelyQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from ApplicationRely t where 1=1 "
				  		+ "/~ and t.applicationId = {applicationId} ~/"
				  		+ "/~ and t.relyApplicationId = {relyApplicationId} ~/"
				  		+ "/~ and t.df = {df} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				  		+ "/~ and t.type = {type} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}
	

}
