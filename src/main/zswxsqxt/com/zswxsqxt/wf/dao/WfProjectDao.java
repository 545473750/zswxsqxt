package com.zswxsqxt.wf.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;
import com.opendata.common.util.StrUtil;

import com.zswxsqxt.wf.model.WfProject;
import com.zswxsqxt.wf.query.WfProjectQuery;
/**
	describe:流程表Dao
	 
*/
@Repository
public class WfProjectDao   extends BaseHibernateDao<WfProject,String>
{
	public Class getEntityClass()
	{
		return WfProject.class;
	}
	/**
		通过WfProjectQuery对象，查询流程表
	*/
	public Page findPage(WfProjectQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from WfProject ett where 1=1");
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
			if(query.getGroupFlag()!=null){
				hql.append(" and ett.groupFlag=?");
				param.add(query.getGroupFlag());
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
			if(query.getUpdateTime()!=null)
			{
				hql.append(" and ett.updateTime=?");
				param.add(query.getUpdateTime());
			}
			if(!StringUtils.isEmpty(query.getDescription()))
			{
				hql.append(" and ett.description=?");
				param.add(query.getDescription());
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
	
	/**
	 * 根据流程分类查询该分类的启用流程
	 * @return
	 */
	public WfProject getProject(String groupFlag){
		String hql = "from WfProject where useState = 1 and groupFlag = ?";
		WfProject wfProject = new WfProject();
		Object obj = super.findOneByHql(hql, groupFlag);//状态是启用的
		if(obj != null){
			wfProject = (WfProject) obj;
		}else{
			wfProject = null;
		}
		return wfProject;
	}
	
	/**
	 * 查询培训班类型的流程
	 * @return
	 */
	public Page findProjects(WfProjectQuery query){
		String hql = "from WfProject where useState = 1 and groupFlag = ?";
		List params = new ArrayList();
		params.add(query.getGroupFlag());
		if(StrUtil.isNotNullOrBlank(query.getName())){
			hql += " and name like ?";
			params.add("%"+query.getName()+"%");
		}
		Page page = super.findByHql(hql, query.getPageSize(), query.getPageNumber(), params.toArray());
		return page;
	}
}
