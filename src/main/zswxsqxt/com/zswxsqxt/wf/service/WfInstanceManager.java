package com.zswxsqxt.wf.service;

import java.util.Date;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.zswxsqxt.wf.dao.WfActivityParticipanDao;
import com.zswxsqxt.wf.dao.WfInstanceDao;
import com.zswxsqxt.wf.dao.WfInstanceNodeDao;
import com.zswxsqxt.wf.dao.WfInstanceParticipanDao;
import com.zswxsqxt.wf.dao.WfProjectDao;
import com.zswxsqxt.wf.model.WfActivity;
import com.zswxsqxt.wf.model.WfActivityParticipan;
import com.zswxsqxt.wf.model.WfInstance;
import com.zswxsqxt.wf.model.WfInstanceNode;
import com.zswxsqxt.wf.model.WfInstanceParticipan;
import com.zswxsqxt.wf.model.WfProject;
import com.zswxsqxt.wf.query.WfInstanceQuery;

/**
  	 describe:流程实例服务类
	 
*/
@Service
@Transactional
public class WfInstanceManager extends BaseManager<WfInstance, String>
{

	private WfInstanceDao wfInstanceDao;//流程实例dao
	private WfProjectDao wfProjectDao;
	private WfInstanceNodeDao wfInstanceNodeDao;//流程实例节点dao
	private WfInstanceParticipanDao wfInstanceParticipanDao;//流程实例节点参与者dao
	private WfActivityParticipanDao wfActivityParticipanDao;//流程节点参与者dao

	public void setWfInstanceDao(WfInstanceDao dao)
	{
		this.wfInstanceDao = dao;
	}

	public void setWfProjectDao(WfProjectDao wfProjectDao) {
		this.wfProjectDao = wfProjectDao;
	}

	public void setWfInstanceNodeDao(WfInstanceNodeDao wfInstanceNodeDao) {
		this.wfInstanceNodeDao = wfInstanceNodeDao;
	}

	public void setWfInstanceParticipanDao(
			WfInstanceParticipanDao wfInstanceParticipanDao) {
		this.wfInstanceParticipanDao = wfInstanceParticipanDao;
	}

	public void setWfActivityParticipanDao(
			WfActivityParticipanDao wfActivityParticipanDao) {
		this.wfActivityParticipanDao = wfActivityParticipanDao;
	}

	public WfInstanceDao getEntityDao()
	{
		return this.wfInstanceDao;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfInstanceQuery query)
	{
		return wfInstanceDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
	
	/**
	 * 创建流程实例
	 * @param projectId 流程
	 * @param targetId 被审核的数据项ID
	 * @param state 流程实例初始状态
	 * @return 流程实例
	 */
	public WfInstance createInstance(String groupFlag,String targetId,Integer state){
		WfProject wfProject = wfProjectDao.getProject(groupFlag);
		WfInstance wfInstance = new WfInstance();
		wfInstance.setName(wfProject.getName());//流程实例名称
		wfInstance.setState(1);//默认是审核的第一个节点
		wfInstance.setTs(new Date());	//创建时间
		wfInstance.setTargetId(targetId);
		wfInstanceDao.save(wfInstance);
		
		Set<WfActivity> set = wfProject.getWfActivitys();
		for (WfActivity wfActivity : set) {
				//保存流程实例节点
				WfInstanceNode wfInstanceNode = new WfInstanceNode();
				wfInstanceNode.setName(wfActivity.getName());				//实例节点名称
				wfInstanceNode.setOrderNum(wfActivity.getOrderNum());		//实例节点顺序
				wfInstanceNode.setActType(wfActivity.getActType());			//实例节点类型
				wfInstanceNode.setShare(wfActivity.getShare());				//是否共享信息
				wfInstanceNode.setUrl(wfActivity.getUrl());					//url
				wfInstanceNode.setGroupFlag(wfActivity.getGroupFlag());		//所在组
				wfInstanceNode.setDescription(wfActivity.getDescription());	//实例节点功能描述
				wfInstanceNode.setSignNum(wfActivity.getWfActivityParticipans().size());//审核人员人数
				wfInstanceNode.setTs(new Date());				//创建时间
				wfInstanceNode.setWfInstance(wfInstance);					//流程实例
				wfInstanceNodeDao.save(wfInstanceNode);//保存流程实例节点
				
				//保存流程实例节点参与者
				Set<WfActivityParticipan> wfActivityParaticipans = wfActivity.getWfActivityParticipans();
				if(wfActivityParaticipans!=null){
					for (WfActivityParticipan wfActivityParticipan : wfActivityParaticipans) {
						WfInstanceParticipan wfInstanceParticipan = new WfInstanceParticipan();
						wfInstanceParticipan.setRefId(wfActivityParticipan.getRefId());//关联id
						wfInstanceParticipan.setRefName(wfActivityParticipan.getRefName());//关联名称
						wfInstanceParticipan.setRefType(wfActivityParticipan.getRefType());//关联类型
						wfInstanceParticipan.setRemark(wfActivityParticipan.getRemark());//备注
						wfInstanceParticipan.setOrgId(wfActivityParticipan.getOrgId());//创建单位
						wfInstanceParticipan.setWfInstanceNode(wfInstanceNode);//所属节点
						wfInstanceParticipan.setTs(new Date());//创建时间
						wfInstanceParticipanDao.save(wfInstanceParticipan);//保存流程实例节点参与者
					}
				}
			}
		return wfInstance;
	}
}
