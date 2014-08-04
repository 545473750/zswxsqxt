/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseHibernateDao;
import com.opendata.organiz.model.Dept;
import com.opendata.organiz.vo.query.DeptQuery;

/**
 * 部门dao层,用于和数据库交互,对上层service提供支持
 */
@Repository
public class DeptDao extends BaseHibernateDao<Dept,java.lang.String>{

	public Class getEntityClass() {
		return Dept.class;
	}
	
	public Page findPage(DeptQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from Dept t where 1=1 "
				  		+ "/~ and t.name like '%[name]%' ~/";
		
				if(StringUtils.isBlank(query.getParentId())){
					sql	+= " and t.parentId is null ";
				}else{
					sql	+= "/~ and t.parentId = {parentId} ~/";
				}
				  		
				  	sql	+= "/~ and t.organizationId = {organizationId} ~/"
				  		+ "/~ and t.leaderId = {leaderId} ~/"
				  		+ "/~ and t.scope = {scope} ~/"
				  		+ "/~ and t.description = {description} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				  		+ "/~ and t.sequence = {sequence} ~/"
						+ "/~ order by [sortColumns] ~/";
		

        return pageQuery(sql,query);
	}
	
	@SuppressWarnings("rawtypes")
	public String findSequence(String organization) {
		String sql = "select " +
						"MAX(org.sequence) " +
					 "from Organization org ";
		StringBuffer sb = new StringBuffer(sql);
		if(null == organization || "root".equals(organization)) {
			sb.append("where org.parentId is NULL");
		} else {
			sb.append("where org.parentId = '" + organization + "'");
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
