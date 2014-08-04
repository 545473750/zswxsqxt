/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.sys.dao;

import java.util.*;

import com.opendata.common.base.*;

import cn.org.rapid_framework.page.*;

import com.opendata.sys.model.*;
import com.opendata.sys.vo.query.*;
import org.springframework.stereotype.Repository;

/**
 * 数据字典值dao层,用于和数据库进行交互 
 * @author顾保臣
 */
@Repository
public class DictvalueDao extends BaseHibernateDao<Dictvalue,java.lang.String>{

	public Class getEntityClass() {
		return Dictvalue.class;
	}
	
	public Page findPage(DictvalueQuery query) {
        //XsqlBuilder syntax,please see http://code.google.com/p/rapid-xsqlbuilder
        // [column]为字符串拼接, {column}为使用占位符. [column]为使用字符串拼接,如username='[username]',偷懒时可以使用字符串拼接 
        // [column] 为PageRequest的属性
		String sql = "select t from Dictvalue t where 1=1 "
				  		+ "/~ and t.code like '%[code]%' ~/"
				  		+ "/~ and t.value like '%[value]%' ~/" // 字典值
				  		+ "/~ and t.parentId = {parentValue} ~/" // 字典值父值
				  		+ "/~ and t.dictitem.id = {dictitemId} ~/" // 字典项
				  		+ "/~ and t.discription = {discription} ~/"
					+ "/~ and t.ts >= {tsBegin} ~/"
					+ "/~ and t.ts <= {tsEnd} ~/"
				  		+ "/~ and t.df = {df} ~/"
				+ "/~ order by [sortColumns] ~/";
		
		if(query.getParentValue() != null && query.getParentValue().equals("item")) {
			sql = "select t from Dictvalue t where 1=1 "
		  		+ "/~ and t.code like '%[code]%' ~/"
		  		+ "/~ and t.value like '%[value]%' ~/" // 字典值
		  		+ "and t.parentId is null " // 字典值父值
		  		+ "/~ and t.dictitem.id = {dictitemId} ~/" // 字典项
		  		+ "/~ and t.discription = {discription} ~/"
			+ "/~ and t.ts >= {tsBegin} ~/"
			+ "/~ and t.ts <= {tsEnd} ~/"
		  		+ "/~ and t.df = {df} ~/"
		+ "/~ order by [sortColumns] ~/";
		}

        return pageQuery(sql,query);
	}
	
    public List<Dictvalue> getAll(){
    	return this.getHibernateTemplate().find(" from Dictvalue t where df='0' order by dictitemId ");
    }

	public List<Dictvalue> findListByItemId(String dictitemId) {
		String hql = "from Dictvalue t where t.df = '0' and t.dictitem.id = '" + dictitemId + "' ";
		return this.getHibernateTemplate().find(hql);
	}

	/**
	 * 校验编号是否重复
	 * @param code
	 * @param itemid
	 */
	public List<Dictvalue> findByCode(String code, String itemid, String nodetype) {
		// 字典项下校验
		if(nodetype.equals("dictitem")) {
			String hql = "from Dictvalue t where t.code = '" + code + "' and t.dictitem.id = '" + itemid + "' and t.df = '0'";
			return this.getHibernateTemplate().find(hql);
		} else if(nodetype.equals("dictvalue")) {
			// 字典值下校验
			String hql = "from Dictvalue t where t.code = '" + code + "' and t.dictvalue.id = '" + itemid + "' and t.df = '0'";
			return this.getHibernateTemplate().find(hql);
		}
		return null;
	}
	
	/**
	 * 根据itemcode 查询divvalue,只查第一级
	 * @param itemCode
	 * @return
	 */
	public List<Dictvalue> findByItemCode(String itemCode)
	{
		String hql = "from Dictvalue t where t.df = '0' and t.dictitem.code = ? and t.parentId is null ";
		return super.findFastByHql(hql, itemCode);
	}
}
