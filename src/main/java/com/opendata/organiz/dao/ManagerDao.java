package com.opendata.organiz.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseHibernateDao;
import com.opendata.organiz.model.Manager;
import com.opendata.organiz.model.User;
import com.opendata.organiz.query.ManagerQuery;
/**
	describe:系统分级管理员Dao
	 
*/
@Repository
public class ManagerDao   extends BaseHibernateDao<Manager,String>
{
	public Class getEntityClass()
	{
		return Manager.class;
	}
	/**
		通过ManagerQuery对象，查询系统分级管理员
	*/
	public Page findPage(ManagerQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from Manager ett where 1=1");
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId());
			}
			if(!StringUtils.isEmpty(query.getUserId()))
			{
				hql.append(" and ett.userId=?");
				param.add(query.getUserId());
			}
			if(!StringUtils.isEmpty(query.getName()))
			{
				hql.append(" and ett.name like ?");
				param.add("%"+query.getName()+"%");
			}
			if(query.getDataScope()!=null)
			{
				hql.append(" and ett.dataScope=?");
				param.add(query.getDataScope());
			}
			if(!StringUtils.isEmpty(query.getScopeValue()))
			{
				hql.append(" and ett.scopeValue=?");
				param.add(query.getScopeValue());
			}
			if(!StringUtils.isEmpty(query.getMenuIds()))
			{
				hql.append(" and ett.menuIds=?");
				param.add(query.getMenuIds());
			}
			if(query.getUserState()!=null)
			{
				hql.append(" and ett.userState=?");
				param.add(query.getUserState());
			}
			if(!StringUtils.isEmpty(query.getAddUserId()))
			{
				hql.append(" and ett.addUserId=?");
				param.add(query.getAddUserId());
			}
			if(!StringUtils.isEmpty(query.getAddName()))
			{
				hql.append(" and ett.addName=?");
				param.add(query.getAddName());
			}
			if(query.getAddTime()!=null)
			{
				hql.append(" and ett.addTime=?");
				param.add(query.getAddTime());
			}
			if(query.getTs()!=null)
			{
				hql.append(" and ett.ts=?");
				param.add(query.getTs());
			}
		}
		if(!StringUtils.isEmpty(query.getSortColumns())){
			if(!query.getSortColumns().equals("ts")){
				hql.append("  order by ett."+query.getSortColumns()+" , ett.ts desc ");
			}else{
				hql.append(" order by ett.ts desc ");
			}
		}else{
			hql.append(" order by ett.ts desc ");
		}
		return super.findByHql(hql.toString(), pageSize, pageNum, param.toArray());
	}
	public List findName() {
		String hql=" from User";
    	return this.findFastByHql(hql);
	}
	public User findNameAndId(String user) {
		String hql=" from User where userid='"+user+"'";
    	return (User) this.getHibernateTemplate().find(hql);
	}
	public List findById(String id) {
		String hql="select * from Manager where id='"+id+"'";
		
		return this.getHibernateTemplate().find(hql);
	}
	public User findUserById(String id) {
		String hql=" from User where id='"+id+"'";
    	return (User) this.getHibernateTemplate().find(hql);
	}
	
}
