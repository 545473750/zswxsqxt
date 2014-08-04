package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfInstanceAudit;
import com.zswxsqxt.wf.query.WfInstanceAuditQuery;
/**
	describe:流程审核表Dao
	 
*/
@Repository
public class WfInstanceAuditDao   extends BaseHibernateDao<WfInstanceAudit,String>
{
	public Class getEntityClass()
	{
		return WfInstanceAudit.class;
	}
	/**
		通过WfInstanceAuditQuery对象，查询流程审核表
	*/
	public Page findPage(WfInstanceAuditQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfInstanceAudit ett where 1=1");
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql.append(" and ett.id=?");
				param.add(query.getId());
			}
			if(!StringUtils.isEmpty(query.getResult()))
			{
				hql.append(" and ett.result=?");
				param.add(query.getResult());
			}
			if(!StringUtils.isEmpty(query.getOpinion()))
			{
				hql.append(" and ett.opinion=?");
				param.add(query.getOpinion());
			}
			if(!StringUtils.isEmpty(query.getAuditUserId()))
			{
				hql.append(" and ett.auditUserId=?");
				param.add(query.getAuditUserId());
			}
			if(!StringUtils.isEmpty(query.getAuditUserName()))
			{
				hql.append(" and ett.auditUserName=?");
				param.add(query.getAuditUserName());
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
	
	/**
	 * 根据实例节点id查询节点的审核意见，并按时间排序，从小到大
	 * @param nodeId
	 * @return
	 */
	public List<WfInstanceAudit> findAudits(String nodeId){
		String hql = "from WfInstanceAudit where wfInstanceNode.id = ? order by ts ";
		return super.findFastByHql(hql, nodeId);
	}
}
