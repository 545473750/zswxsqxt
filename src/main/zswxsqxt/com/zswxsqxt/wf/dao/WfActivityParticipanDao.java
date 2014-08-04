package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfActivityParticipan;
import com.zswxsqxt.wf.query.WfActivityParticipanQuery;
/**
	describe:流程节点参与者Dao
	 
*/
@Repository
public class WfActivityParticipanDao   extends BaseHibernateDao<WfActivityParticipan,String>
{
	public Class getEntityClass()
	{
		return WfActivityParticipan.class;
	}
	/**
		通过WfActivityParticipanQuery对象，查询流程节点参与者
	*/
	public Page findPage(WfActivityParticipanQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfActivityParticipan ett where 1=1");
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
			if(!StringUtils.isEmpty(query.getRefName())){
				hql.append(" and ett.refName like ?");
				param.add("%"+query.getRefName()+"%");
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
			if(query.getTs()!=null)
			{
				hql.append(" and ett.ts=?");
				param.add(query.getTs());
			}
			if(query.getWfActivity()!=null)
			{
				hql.append(" and ett.wfActivity=?");
				param.add(query.getWfActivity());
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
	 * 根据引用Id和节点Id查询参与者是否已存在
	 * @param refId
	 * @param activityId
	 * @return
	 */
	public WfActivityParticipan getParticipan(String refId,String activityId){
		String hql = "from WfActivityParticipan p where p.refId = ? and p.activityId = ?";
		return (WfActivityParticipan) super.findOneByHql(hql, refId,activityId);
	}
}
