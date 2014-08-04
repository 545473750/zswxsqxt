package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfInstanceFunction;
import com.zswxsqxt.wf.query.WfInstanceFunctionQuery;
/**
	describe:流程节点功能点表Dao
	 
*/
@Repository
public class WfInstanceFunctionDao   extends BaseHibernateDao<WfInstanceFunction,String>
{
	public Class getEntityClass()
	{
		return WfInstanceFunction.class;
	}
	/**
		通过WfInstanceFunctionQuery对象，查询流程节点功能点表
	*/
	public Page findPage(WfInstanceFunctionQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfInstanceFunction ett where 1=1");
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId());
			}
			if(!StringUtils.isEmpty(query.getName()))
			{
				hql.append(" and ett.name=?");
				param.add(query.getName());
			}
			if(!StringUtils.isEmpty(query.getUrl()))
			{
				hql.append(" and ett.url=?");
				param.add(query.getUrl());
			}
			if(!StringUtils.isEmpty(query.getParName()))
			{
				hql.append(" and ett.parName=?");
				param.add(query.getParName());
			}
			if(!StringUtils.isEmpty(query.getDescription()))
			{
				hql.append(" and ett.description=?");
				param.add(query.getDescription());
			}
			if(query.getFunType()!=null)
			{
				hql.append(" and ett.funType=?");
				param.add(query.getFunType());
			}
			if(query.getInsType()!=null)
			{
				hql.append(" and ett.insType=?");
				param.add(query.getInsType());
			}
			if(query.getGroupFlag()!=null)
			{
				hql.append(" and ett.groupFlag=?");
				param.add(query.getGroupFlag());
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
}
