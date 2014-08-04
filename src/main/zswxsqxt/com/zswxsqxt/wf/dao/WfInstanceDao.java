package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfInstance;
import com.zswxsqxt.wf.query.WfInstanceQuery;
/**
	describe:流程实例Dao
	 
*/
@Repository
public class WfInstanceDao   extends BaseHibernateDao<WfInstance,String>
{
	public Class getEntityClass()
	{
		return WfInstance.class;
	}
	/**
		通过WfInstanceQuery对象，查询流程实例
	*/
	public Page findPage(WfInstanceQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfInstance ett where 1=1");
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
			if(query.getState()!=null)
			{
				hql.append(" and ett.state=?");
				param.add(query.getState());
			}
			if(query.getTs()!=null)
			{
				hql.append(" and ett.ts=?");
				param.add(query.getTs());
			}
			if(query.getWfProject()!=null)
			{
				hql.append(" and ett.wfProject=?");
				param.add(query.getWfProject());
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
