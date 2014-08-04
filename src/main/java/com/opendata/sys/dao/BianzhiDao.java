package com.opendata.sys.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.opendata.sys.model.Bianzhi;
import com.opendata.sys.vo.query.BianzhiQuery;
/**
	describe:系统编制Dao
	 
*/
@Repository
public class BianzhiDao   extends BaseHibernateDao<Bianzhi,String>
{
	public Class getEntityClass()
	{
		return Bianzhi.class;
	}
	/**
		通过BianzhiQuery对象，查询系统编制
	*/
	public Page findPage(BianzhiQuery query,int pageSize,int pageNum)
	{
		StringBuilder hql=new StringBuilder();
		hql.append(" from Bianzhi ett where 1=1");
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
			if(!StringUtils.isEmpty(query.getOrgId()))
			{
				hql.append(" and ett.orgId=?");
				param.add(query.getOrgId());
			}
			if(!StringUtils.isEmpty(query.getDeptId()))
			{
				hql.append(" and ett.deptId=?");
				param.add(query.getDeptId());
			}else{
				hql.append(" and ett.deptId is null ");
			}
			if(!StringUtils.isEmpty(query.getScope()))
			{
				hql.append(" and ett.scope=?");
				param.add(query.getScope());
			}
			if(!StringUtils.isEmpty(query.getRoleId()))
			{
				hql.append(" and ett.roleId=?");
				param.add(query.getRoleId());
			}
			if(!StringUtils.isEmpty(query.getSubjectId()))
			{
				hql.append(" and ett.subjectId=?");
				param.add(query.getSubjectId());
			}
			if(query.getSemester()!=null)
			{
				hql.append(" and ett.semester=?");
				param.add(query.getSemester());
			}
			if(!StringUtils.isEmpty(query.getUserId()))
			{
				hql.append(" and ett.userId=?");
				param.add(query.getUserId());
			}
			if(!StringUtils.isEmpty(query.getIsOut()))
			{
				hql.append(" and ett.isOut=?");
				param.add(query.getIsOut());
			}
			if(query.getDirectHeader()!=null)
			{
				hql.append(" and ett.directHeader=?");
				param.add(query.getDirectHeader());
			}
			if(!StringUtils.isEmpty(query.getDescription()))
			{
				hql.append(" and ett.description=?");
				param.add(query.getDescription());
			}
			if(query.getSeq()!=null)
			{
				hql.append(" and ett.seq=?");
				param.add(query.getSeq());
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
			if(query.getAddTime()!=null)
			{
				hql.append(" and ett.addTime=?");
				param.add(query.getAddTime());
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
