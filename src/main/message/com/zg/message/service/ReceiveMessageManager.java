package com.zg.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zg.message.dao.ReceiveMessageDao;
import com.zg.message.model.ReceiveMessage;
import com.zg.message.query.ReceiveMessageQuery;

/**
  	 describe:消息提醒表服务类
	 
*/
@Service
@Transactional
public class ReceiveMessageManager extends BaseManager<ReceiveMessage, String>
{

	private ReceiveMessageDao receiveMessageDao;

	
	/**
	 * 根据用户ID查询当前所有的提醒消息数量
	 * */
	public long findAllManagerByUserId(String userId){
		String sql="SELECT COUNT(*) FROM t_receivemessage  WHERE receiverId=? and state='0'";
		return receiveMessageDao.countBySql(sql, userId);
	}
	public void setReceiveMessageDao(ReceiveMessageDao dao)
	{
		this.receiveMessageDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.receiveMessageDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(ReceiveMessageQuery query)
	{
		return receiveMessageDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
}
