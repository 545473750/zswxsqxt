package com.zg.message.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zg.message.dao.MessageDao;
import com.zg.message.model.Message;
import com.zg.message.query.MessageQuery;

/**
  	 describe:消息提醒表服务类
	 
*/
@Service
@Transactional
public class MessageManager extends BaseManager<Message, String>
{

	private MessageDao messageDao;

	
	/**
	 * 根据用户ID查询当前所有的提醒消息数量
	 * */
	public long findAllManagerByUserId(String userId){
		String sql="SELECT COUNT(*) FROM t_message  WHERE receiverId=? and state='0' and status='1'";
		return messageDao.countBySql(sql, userId);
	}
	public void setMessageDao(MessageDao dao)
	{
		this.messageDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.messageDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(MessageQuery query)
	{
		return messageDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
}
