package com.zg.message.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseHibernateDao;

import com.zg.message.model.ReceiveMessage;
import com.zg.message.query.ReceiveMessageQuery;
/**
	describe:消息提醒表Dao
	 
*/
@Repository
public class ReceiveMessageDao   extends BaseHibernateDao<ReceiveMessage,String>
{
	public Class getEntityClass()
	{
		return ReceiveMessage.class;
	}
	/**
		通过MessageQuery对象，查询消息提醒表
	*/
	public Page findPage(ReceiveMessageQuery query,int pageSize,int pageNum)
	{
		String hql=" from ReceiveMessage ett where 1=1";
		List param=new ArrayList();
		if(query!=null)
		{
			if(!StringUtils.isEmpty(query.getId()))
			{
				hql+=" and ett.id=?";
				param.add(query.getId());
			}
			if(!StringUtils.isEmpty(query.getReceiverId()))
			{
				hql+=" and ett.receiverId=?";
				param.add(query.getReceiverId());
			}
			if(!StringUtils.isEmpty(query.getReceiver()))
			{
				hql+=" and ett.receiver=?";
				param.add(query.getReceiver());
			}
			if(!StringUtils.isEmpty(query.getSponsor()))
			{
				hql+=" and ett.sponsor=?";
				param.add(query.getSponsor());
			}
			if(!StringUtils.isEmpty(query.getSponsorId()))
			{
				hql+=" and ett.sponsorId=?";
				param.add(query.getSponsorId());
			}
			if(!StringUtils.isEmpty(query.getTitle()))
			{
				hql+=" and ett.title=?";
				param.add(query.getTitle());
			}
			if(query.getTime()!=null)
			{
				hql+=" and ett.time=?";
				param.add(query.getTime());
			}
			if(!StringUtils.isEmpty(query.getState()))
			{
				hql+=" and ett.state=?";
				param.add(query.getState());
			}
			if(!StringUtils.isEmpty(query.getStatus()))
			{
				hql+=" and ett.status=?";
				param.add(query.getStatus());
			}
			if(query.getChackTime()!=null)
			{
				hql+=" and ett.chackTime=?";
				param.add(query.getChackTime());
			}
			if(!StringUtils.isEmpty(query.getContent()))
			{
				hql+=" and ett.content=?";
				param.add(query.getContent());
			}
			if(query.getTs()!=null)
			{
				hql+=" and ett.ts=?";
				param.add(query.getTs());
			}
		}
		if(!StringUtils.isEmpty(query.getSortColumns())){
			if(!query.getSortColumns().equals("ts")){
				hql+="  order by ett."+query.getSortColumns()+" , ett.ts desc ";
			}else{
				hql+=" order by ett.ts desc ";
			}
		}else{
			hql+=" order by ett.ts desc ";
		}
		return super.findByHql(hql, pageSize, pageNum, param.toArray());
	}
}
