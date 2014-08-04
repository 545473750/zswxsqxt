package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfWork;
import com.zswxsqxt.wf.query.WfWorkQuery;
/**
	describe:流程工作表Dao
	 
*/
@Repository
public class WfWorkDao   extends BaseHibernateDao<WfWork,String>
{
	public Class getEntityClass()
	{
		return WfWork.class;
	}
	/**
		通过WfWorkQuery对象，查询流程工作表
	*/
	public Page findPage(WfWorkQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfWork ett where 1=1");
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId());
			}
			if(!StringUtils.isEmpty(query.getProjectId()))
			{
				hql.append(" and ett.projectId=?");
				param.add(query.getProjectId());
			}
			if(!StringUtils.isEmpty(query.getName()))
			{
				hql.append(" and ett.name=?");
				param.add(query.getName());
			}
			if(query.getStartTime()!=null)
			{
				hql.append(" and ett.startTime=?");
				param.add(query.getStartTime());
			}
			if(query.getEndTime()!=null)
			{
				hql.append(" and ett.endTime=?");
				param.add(query.getEndTime());
			}
			if(query.getWorkState()!=null)
			{
				hql.append(" and ett.workState=?");
				param.add(query.getWorkState());
			}
			if(!StringUtils.isEmpty(query.getUserId()))
			{
				hql.append(" and ett.userId=?");
				param.add(query.getUserId());
			}
			if(!StringUtils.isEmpty(query.getUserName()))
			{
				hql.append(" and ett.userName=?");
				param.add(query.getUserName());
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
