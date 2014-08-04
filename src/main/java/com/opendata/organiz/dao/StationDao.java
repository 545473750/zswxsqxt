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

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 岗位模块dao层，用于和数据库交互
 * @author顾保臣
 */
@Repository
public class StationDao extends BaseHibernateDao<Station,java.lang.String>{

	public Class getEntityClass() {
		return Station.class;
	}
	
	public Page findPage(StationQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from Station t where 1=1 "
				  		+ "/~ and t.organizationId = {organizationId} ~/";
		if(StringUtils.isNotBlank(query.getDeptId())){
				sql += "/~ and t.deptId = {deptId} ~/";
		}else{
				sql += " and t.deptId is null ";
		}
				  		
				sql += "/~ and t.name like '%[name]%' ~/"
				  		+ "/~ and t.code like '%[code]%' ~/"
				  		+ "/~ and t.level = {level} ~/"
				  		+ "/~ and t.sequence = {sequence} ~/"
				  		+ "/~ and t.remark = {remark} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				  		+ "/~ and t.df = {df} ~/"
				+ "/~ order by [sortColumns] ~/";

        return pageQuery(sql,query);
	}

	public Page findPageCommon(StationQuery query) {
		String sql = "select t from Station t where 1=1 "
			  		+ "/~ and t.organizationId = {organizationId} ~/"
			  		+ "/~ and t.name like '%[name]%' ~/"
			  		+ "/~ and t.code like '%[code]%' ~/"
			  		+ "/~ and t.level = {level} ~/"
			  		+ "/~ and t.sequence = {sequence} ~/"
			  		+ "/~ and t.remark = {remark} ~/"
				+ "/~ and t.ts >= {tsBegin} ~/"
				+ "/~ and t.ts <= {tsEnd} ~/"
			  		+ "/~ and t.df = {df} ~/"
			  		+ "/~ and t.organization.id = {organizationId} ~/" // 组织机构
			+ "/~ order by [sortColumns] ~/";
		
		return pageQuery(sql,query);
	}

}
