package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfInstanceResult;
import com.zswxsqxt.wf.query.WfInstanceResultQuery;
/**
	describe:实例结果Dao
	 
*/
@Repository
public class WfInstanceResultDao   extends BaseHibernateDao<WfInstanceResult,String>
{
	public Class getEntityClass()
	{
		return WfInstanceResult.class;
	}
	/**
		通过WfInstanceResultQuery对象，查询实例结果
	*/
	public Page findPage(WfInstanceResultQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfInstanceResult ett where 1=1");
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId());
			}
			if(!StringUtils.isEmpty(query.getRefFlag()))
			{
				hql.append(" and ett.refFlag=?");
				param.add(query.getRefFlag());
			}
			if(!StringUtils.isEmpty(query.getRefId()))
			{
				hql.append(" and ett.refId=?");
				param.add(query.getRefId());
			}
			if(query.getState()!=null)
			{
				hql.append(" and ett.state=?");
				param.add(query.getState());
			}
			if(!StringUtils.isEmpty(query.getAuditUserId()))
			{
				hql.append(" and ett.auditUserId=?");
				param.add(query.getAuditUserId());
			}
			if(query.getResult()!=null)
			{
				hql.append(" and ett.result=?");
				param.add(query.getResult());
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
