package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfInstanceParticipan;
import com.zswxsqxt.wf.query.WfInstanceParticipanQuery;
/**
	describe:流程参与者Dao
	 
*/
@Repository
public class WfInstanceParticipanDao   extends BaseHibernateDao<WfInstanceParticipan,String>
{
	public Class getEntityClass()
	{
		return WfInstanceParticipan.class;
	}
	/**
		通过WfInstanceParticipanQuery对象，查询流程参与者
	*/
	public Page findPage(WfInstanceParticipanQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfInstanceParticipan ett where 1=1");
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId());
			}
			if(!StringUtils.isEmpty(query.getRefId()))
			{
				hql.append(" and ett.refId=?");
				param.add(query.getRefId());
			}
			if(query.getRefType()!=null)
			{
				hql.append(" and ett.refType=?");
				param.add(query.getRefType());
			}
			if(!StringUtils.isEmpty(query.getRemark()))
			{
				hql.append(" and ett.remark=?");
				param.add(query.getRemark());
			}
			if(!StringUtils.isEmpty(query.getOrgId()))
			{
				hql.append(" and ett.orgId=?");
				param.add(query.getOrgId());
			}
			if(query.getTs()!=null)
			{
				hql.append(" and ett.ts=?");
				param.add(query.getTs());
			}
			if(query.getWfInstanceNode()!=null)
			{
				hql.append(" and ett.wfInstanceNode=?");
				param.add(query.getWfInstanceNode());
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
