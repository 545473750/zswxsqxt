package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfInstanceNode;
import com.zswxsqxt.wf.query.WfInstanceNodeQuery;
/**
	describe:流程实例节点表Dao
	 
*/
@Repository
public class WfInstanceNodeDao   extends BaseHibernateDao<WfInstanceNode,String>
{
	public Class getEntityClass()
	{
		return WfInstanceNode.class;
	}
	/**
		通过WfInstanceNodeQuery对象，查询流程实例节点表
	*/
	public Page findPage(WfInstanceNodeQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfInstanceNode ett where 1=1");
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
			if(query.getOrderNum()!=null)
			{
				hql.append(" and ett.orderNum=?");
				param.add(query.getOrderNum());
			}
			if(query.getActType()!=null)
			{
				hql.append(" and ett.actType=?");
				param.add(query.getActType());
			}
			if(query.getActFlag()!=null)
			{
				hql.append(" and ett.actFlag=?");
				param.add(query.getActFlag());
			}
			if(!StringUtils.isEmpty(query.getDescription()))
			{
				hql.append(" and ett.description=?");
				param.add(query.getDescription());
			}
			if(!StringUtils.isEmpty(query.getUrl()))
			{
				hql.append(" and ett.url=?");
				param.add(query.getUrl());
			}
			if(!StringUtils.isEmpty(query.getGroupFlag()))
			{
				hql.append(" and ett.groupFlag=?");
				param.add(query.getGroupFlag());
			}
			if(!StringUtils.isEmpty(query.getExtFiled3()))
			{
				hql.append(" and ett.extFiled3=?");
				param.add(query.getExtFiled3());
			}
			if(query.getTs()!=null)
			{
				hql.append(" and ett.ts=?");
				param.add(query.getTs());
			}
			if(query.getWfInstanceFunction()!=null)
			{
				hql.append(" and ett.wfInstanceFunction=?");
				param.add(query.getWfInstanceFunction());
			}
			if(query.getWfInstance()!=null)
			{
				hql.append(" and ett.wfInstance=?");
				param.add(query.getWfInstance());
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
	 * 根据组班流程实例id和流程实例状态查询节点之前的所有节点
	 * @param courseId
	 * @param state
	 * @return
	 */
	public List<WfInstanceNode> getZbNodeByState(String insId,Integer state){
		String hql = "from WfInstanceNode where wfInstance.id=? and orderNum <= ? order by orderNum asc";
		Object[] objs = new Object[2];
		objs[0] = insId;
		objs[1] = state;
		List<WfInstanceNode> wfInstanceNodes = super.findFastByHql(hql, objs);
		return wfInstanceNodes;
	}
	/**
	 * 根据组班流程实例id查询所有节点
	 * @param insId
	 * @return
	 */
	public List<WfInstanceNode> getZbNode(String insId){
		String hql = "from WfInstanceNode where wfInstance.id=? order by orderNum asc";
		List<WfInstanceNode> wfInstanceNodes = super.findFastByHql(hql, insId);
		return wfInstanceNodes;
	}
	
	/**
	 * 根据流程实例id和流程实例当前状态查询下一个节点是不是专家审核
	 * @param insId
	 * @param state
	 * @return
	 */
	public WfInstanceNode getNode(String insId,Integer state){
		String hql = "from WfInstanceNode where wfInstance.id=? and orderNum = ?";
		Object[] objs = new Object[2];
		objs[0] = insId;
		objs[1] = state;
		WfInstanceNode wfInstanceNode = (WfInstanceNode) super.findOneByHql(hql, objs);
		return wfInstanceNode;
	}
	public List<WfInstanceNode> getNodess(String insId){
		String hql = "from WfInstanceNode where wfInstance.id in ?";
		Object[] objs = new Object[2];
		objs[0] = insId;
		WfInstanceNode wfInstanceNode = (WfInstanceNode) super.findOneByHql(hql, objs);
		return (List)wfInstanceNode;
	}
	
	/**
	 * 根据流程实例Id查询该流程对应的节点中顺序最大的节点，即最后的一个节点
	 * @param insId
	 * @return
	 */
	public Integer getMaxOrderNode(String insId){
		String hql = "select max(orderNum) from WfInstanceNode where wfInstance.id=?";
		return (Integer) super.findOneByHql(hql, insId);
		
	}
	
}
