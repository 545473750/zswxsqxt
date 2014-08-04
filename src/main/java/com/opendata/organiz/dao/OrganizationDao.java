/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.dao;

import java.util.*;

import com.opendata.common.base.*;
import cn.org.rapid_framework.page.*;

import com.opendata.organiz.model.*;
import com.opendata.organiz.vo.query.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

/**
 * 组织机构模块dao层,用于和数据库交互,对上层service提供支持
 * @author顾保臣
 */
@Repository
public class OrganizationDao extends BaseHibernateDao<Organization,java.lang.String>{

	public Class getEntityClass() {
		return Organization.class;
	}
	
	public Page findPage(OrganizationQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from Organization t where 1=1 "
				  		+ "/~ and t.name like '%[name]%' ~/"
				  		+ "/~ and t.type = {type} ~/"
				  		+ "/~ and t.code = {code} ~/"
				  		+ "/~ and t.unitlevel = {unitlevel} ~/"
				  		+ "/~ and t.nature = {nature} ~/"
				  		+ "/~ and t.description = {description} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				  		+ "/~ and t.df = {df} ~/"
				  		+ "/~ and t.sequence = {sequence} ~/";
		StringBuffer sb = new StringBuffer(sql);
		if(query.getParentId() != null) {
			sb.append("/~ and t.parentId = {parentId} ~/");
		} /*else {
			sb.append(" and t.parentId is null ");
		}*/
		sb.append("/~ order by [sortColumns] ~/");

        return pageQuery(sb.toString(),query);
	}
	
	@SuppressWarnings("rawtypes")
	public String findSequence(String organization) {
		String sql = "select " +
						"MAX(org.sequence) " +
					 "from Organization org where org.parentId is not null ";
		StringBuffer sb = new StringBuffer(sql);
		
		if(StringUtils.isNotBlank(organization)){
			sb.append(" and org.parentId = '" + organization + "'");
		}
		List result = this.getHibernateTemplate().find(sb.toString());
		if(result == null || result.size() == 0) {
			return "1";
		} else {
			if(result.get(0) != null) {
				Long seq = (Long) result.get(0);
				return seq + 1 + "";
			} else {
				return "1";
			}
		}
	}
	
}
