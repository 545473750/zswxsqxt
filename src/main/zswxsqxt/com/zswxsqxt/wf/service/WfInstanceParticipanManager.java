package com.zswxsqxt.wf.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.zswxsqxt.wf.dao.WfInstanceParticipanDao;
import com.zswxsqxt.wf.model.WfInstanceFunction;
import com.zswxsqxt.wf.model.WfInstanceNode;
import com.zswxsqxt.wf.model.WfInstanceParticipan;
import com.zswxsqxt.wf.query.WfInstanceParticipanQuery;

/**
  	 describe:流程参与者服务类
	 
*/
@Service
@Transactional
public class WfInstanceParticipanManager extends BaseManager<WfInstanceParticipan, String>
{

	private WfInstanceParticipanDao wfInstanceParticipanDao;

	public void setWfInstanceParticipanDao(WfInstanceParticipanDao dao)
	{
		this.wfInstanceParticipanDao = dao;
	}

	public EntityDao getEntityDao()
	{
		return this.wfInstanceParticipanDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfInstanceParticipanQuery query)
	{
		return wfInstanceParticipanDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
	
	/**
	 * 根据当前用户查询该用户在流程中所有的权限
	 * @param userId	用户id
	 * @param groupFlag	流程所在组（课程、组班、公文等）
	 * @return	改用户所拥有的权限功能集合
	 */
	public List<WfInstanceNode> findMenu(String userId,String groupFlag){
		Map<String, String> parameter = new HashMap<String, String>();
		parameter.put("userId", userId);
		parameter.put("groupFlag", groupFlag);
		List<WfInstanceNode> list = wfInstanceParticipanDao.findFastByFreeSql("findPermissionByUser", parameter);
		List<WfInstanceNode> list2 = wfInstanceParticipanDao.findFastByFreeSql("findPermissionByRole", parameter);
		List<WfInstanceNode> list3 = wfInstanceParticipanDao.findFastByFreeSql("findPermissionByZJZ", parameter);
		list.addAll(list2);
		list.addAll(list3);
		return list;
	}
}
