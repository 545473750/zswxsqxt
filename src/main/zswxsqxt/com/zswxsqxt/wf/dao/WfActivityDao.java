package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zswxsqxt.wf.model.WfActivity;
import com.zswxsqxt.wf.model.WfProject;
import com.zswxsqxt.wf.query.WfActivityQuery;
/**
	describe:流程节点表Dao
	 
*/
@Repository
public class WfActivityDao   extends BaseHibernateDao<WfActivity,String>
{
	public Class getEntityClass()
	{
		return WfActivity.class;
	}
	/**
		通过WfActivityQuery对象，查询流程节点表
	*/
	public Page findPage(WfActivityQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfActivity ett where 1=1");
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
				hql.append(" and ett.name like ?");
				param.add("%"+query.getName()+"%");
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
			if(query.getWfProject()!=null)
			{
				hql.append(" and ett.wfProject.id=?");
				param.add(query.getWfProject().getId());
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
				hql.append(" order by ett.orderNum asc ");
			}
		}else{
			hql.append(" order by ett.orderNum asc ");
		}
		return super.findByHql(hql.toString(), pageSize, pageNum, param.toArray());
	}
	
	/**
	 * 根据流程id得到流程下所有节点，并按照节点顺序排序
	 * @param proId
	 * @return
	 */
	public List<WfActivity> getWfActivity(String proId){
		String hql = "from WfActivity where wfProject.id = ? order by orderNum asc";
		List<WfActivity> list = super.findFastByHql(hql, proId);
		if(list.size()>0){
			return list;
		}else{
			return null;
		}
	}
	
}
