package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfWorkResult;
import com.zswxsqxt.wf.query.WfWorkResultQuery;
/**
	describe:流程工作结果Dao
	 
*/
@Repository
public class WfWorkResultDao   extends BaseHibernateDao<WfWorkResult,String>
{
	public Class getEntityClass()
	{
		return WfWorkResult.class;
	}
	/**
		通过WfWorkResultQuery对象，查询流程工作结果
	*/
	public Page findPage(WfWorkResultQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfWorkResult ett where 1=1");
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId());
			}
			if(query.getProResult()!=null)
			{
				hql.append(" and ett.proResult=?");
				param.add(query.getProResult());
			}
			if(!StringUtils.isEmpty(query.getAddUserId()))
			{
				hql.append(" and ett.addUserId=?");
				param.add(query.getAddUserId());
			}
			if(!StringUtils.isEmpty(query.getAddUserName()))
			{
				hql.append(" and ett.addUserName=?");
				param.add(query.getAddUserName());
			}
			if(!StringUtils.isEmpty(query.getRemark()))
			{
				hql.append(" and ett.remark=?");
				param.add(query.getRemark());
			}
			if(query.getTs()!=null)
			{
				hql.append(" and ett.ts=?");
				param.add(query.getTs());
			}
			if(query.getWfWork()!=null)
			{
				hql.append(" and ett.wfWork=?");
				param.add(query.getWfWork());
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
