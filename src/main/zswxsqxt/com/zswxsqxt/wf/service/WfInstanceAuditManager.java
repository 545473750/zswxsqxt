package com.zswxsqxt.wf.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.org.rapid_framework.page.Page;
import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.UserManager;
import com.zg.message.model.Message;
import com.zg.message.model.ReceiveMessage;
import com.zg.message.service.MessageManager;
import com.zg.message.service.ReceiveMessageManager;
//import com.zswxsqxt.bj.model.ClassBigclass;
//import com.zswxsqxt.bj.model.ClassTermBj;
//import com.zswxsqxt.bj.service.ClassBigclassManager;
//import com.zswxsqxt.bj.service.ClassTermBjManager;
//import com.zswxsqxt.course.model.Apply;
//import com.zswxsqxt.course.model.Course;
//import com.zswxsqxt.course.model.Detail;
//import com.zswxsqxt.course.service.ApplyManager;
//import com.zswxsqxt.course.service.CourseManager;
//import com.zswxsqxt.course.service.DetailManager;
import com.zswxsqxt.wf.dao.WfInstanceAuditDao;
import com.zswxsqxt.wf.dao.WfInstanceDao;
import com.zswxsqxt.wf.dao.WfInstanceNodeDao;
import com.zswxsqxt.wf.model.WfInstance;
import com.zswxsqxt.wf.model.WfInstanceAudit;
import com.zswxsqxt.wf.model.WfInstanceNode;
import com.zswxsqxt.wf.model.WfInstanceResult;
import com.zswxsqxt.wf.query.WfInstanceAuditQuery;
import com.zswxsqxt.wf.util.Constant;

/**
  	 describe:流程审核表服务类
	 
*/
@Service
@Transactional
public class WfInstanceAuditManager extends BaseManager<WfInstanceAudit, String>
{

	private WfInstanceAuditDao wfInstanceAuditDao;
	private WfInstanceNodeDao wfInstanceNodeDao;//流程实例节点dao
	private WfInstanceResultManager wfInstanceResultManager;//审核结果管理 
	private WfInstanceDao wfInstanceDao;//流程实例dao
//	private ClassBigclassManager classBigclassManager;//组班dao
//	private ApplyManager applyManager;
//	private DetailManager detailManager;//实验课管理
//	private ClassTermBjManager classTermBjManager;//期数班级管理
//	private CourseManager courseManager;//课程信息管理
	private MessageManager messageManager;//消息提醒表服务类
	private ReceiveMessageManager receiveMessageManager;//消息提醒表服务类
	private UserManager userManager;//用户管理
	
//	public void setApplyManager(ApplyManager applyManager) {
//		this.applyManager = applyManager;
//	}

	public void setWfInstanceAuditDao(WfInstanceAuditDao dao)
	{
		this.wfInstanceAuditDao = dao;
	}

	public void setWfInstanceNodeDao(WfInstanceNodeDao wfInstanceNodeDao) {
		this.wfInstanceNodeDao = wfInstanceNodeDao;
	}

	public void setWfInstanceDao(WfInstanceDao wfInstanceDao) {
		this.wfInstanceDao = wfInstanceDao;
	}

//	public void setClassBigclassManager(ClassBigclassManager classBigclassManager) {
//		this.classBigclassManager = classBigclassManager;
//	}
//
//	public CourseManager getCourseManager() {
//		return courseManager;
//	}
//
//	public void setCourseManager(CourseManager courseManager) {
//		this.courseManager = courseManager;
//	}

	public EntityDao getEntityDao()
	{
		return this.wfInstanceAuditDao;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}

	public void setReceiveMessageManager(ReceiveMessageManager receiveMessageManager) {
		this.receiveMessageManager = receiveMessageManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public void setWfInstanceResultManager(
			WfInstanceResultManager wfInstanceResultManager) {
		this.wfInstanceResultManager = wfInstanceResultManager;
	}

	@Transactional(readOnly = true)
	public Page findPage(WfInstanceAuditQuery query)
	{
		return wfInstanceAuditDao.findPage(query,query.getPageSize(),query.getPageNumber());
	}
	
	/**
	 * 根据流程实例和状态得到流程实例节点，并得到该节点的审核意见，得到该流程实例的所有审核意见及结果
	 * @param insId 流程实例
	 * @param state	流程实例状态即节点的标示
	 * @return 审核意见集合
	 */
	public List<WfInstanceAudit> findAudidts(String insId,Integer state){
		List<WfInstanceNode> wfInstanceNodes = wfInstanceNodeDao.getZbNodeByState(insId, state);
		List<WfInstanceAudit> audits = new ArrayList<WfInstanceAudit>();
		for (WfInstanceNode wfInstanceNode : wfInstanceNodes) {
			List<WfInstanceAudit> audit = wfInstanceAuditDao.findAudits(wfInstanceNode.getId());
			if(audit!=null){
				audits.addAll(audit);
			}
		}
		if(audits.size()>0){
			return audits;
		}else{
			return null;
		}
	}
	/**
	 * 根据流程实例得到流程实例节点，并得到流程实例节点下的审核意见，得到流程实例的所有审核意见及结果
	 * @param insId
	 * @return
	 */
	public List<WfInstanceAudit> findAllAudidt(String insId){
		List<WfInstanceNode> wfInstanceNodes = wfInstanceNodeDao.getZbNode(insId);
		List<WfInstanceAudit> audits = new ArrayList<WfInstanceAudit>();
		for (WfInstanceNode wfInstanceNode : wfInstanceNodes) {
			List<WfInstanceAudit> audit = wfInstanceAuditDao.findAllBy("wfInstanceNode.id", wfInstanceNode.getId());
			if(audit!=null&&audit.size()>0){
				audits.addAll(audit);
			}
		}
		if(audits.size()>0){
			return audits;
		}else{
			return null;
		}
	}
	
	/**
	 * 保存审核意见表和审核结果表
	 */
	public void save(WfInstanceAudit wfInstanceAudit,String insId,int state){
		this.wfInstanceAuditDao.save(wfInstanceAudit);
		//添加审核信息到结果表中
		WfInstanceResult wfInstanceResult = new WfInstanceResult();
		wfInstanceResult.setAuditUserId(wfInstanceAudit.getAuditUserId());//审核人
		wfInstanceResult.setRefFlag(wfInstanceAudit.getWfInstanceNode().getGroupFlag());//所在组，关联类别
		wfInstanceResult.setRefId(insId);//关联id
		wfInstanceResult.setResult(wfInstanceAudit.getResult());//审核结果
		wfInstanceResult.setState(state);//节点
		wfInstanceResult.setTs(new Date());//创建时间
		wfInstanceResultManager.save(wfInstanceResult);//保存结果表
		
		updateInsState(insId, state,wfInstanceAudit.getAuditUserId());//满足条件修改流程实例状态，进入下个节点
	}
	
	/**
	 * 根据流程实例和状态得到流程实例节点，得到该节点的审核意见，满足条件修改流程实例状态，进入下个节点
	 * @param insId
	 * @param state
	 * @return
	 */
	public void updateInsState(String insId,Integer state,String userId){
			WfInstanceNode wfInstanceNode = this.wfInstanceNodeDao.getNode(insId, state);//得到被审核的组班流程实例的审核节点
			WfInstance wfInstance = wfInstanceDao.getById(wfInstanceNode.getWfInstance().getId());
			String groupFlag = wfInstanceNode.getGroupFlag();//得到所在组
			int countersign = wfInstanceNode.getActType();//会签状态
			if(countersign == 1){//1一般审核，即只有一个人审核
				WfInstanceAudit wfInstanceAudit = this.wfInstanceAuditDao.findByProperty("wfInstanceNode.id", wfInstanceNode.getId());
				if(wfInstanceAudit.getResult().equals("2")){//审核通过
					wfInstance.setState(wfInstance.getState()+1);
					wfInstanceDao.update(wfInstance);
					int lastNode = wfInstanceNodeDao.getMaxOrderNode(wfInstanceNode.getWfInstance().getId());//得到流程最后的节点
					if(lastNode!=0&&wfInstance.getState()>lastNode){
						pass(wfInstance.getId(),groupFlag,userId);//被审核对象审核通过
					}
				}else{//审核不通过
					wfInstance.setState(0);//审核不通过，不进行之后的审核
					wfInstanceDao.update(wfInstance);
					noPass(wfInstance.getId(),groupFlag);//被审核对象审核不通过
				}
			}
			if(countersign == 2){//2是专家审核，多个人审核
				List<WfInstanceAudit> list = this.wfInstanceAuditDao.findAllBy("wfInstanceNode.id", wfInstanceNode.getId());
				if(list.size() == wfInstanceNode.getSignNum()){
					int passNum = 0;
					for(WfInstanceAudit wfInstanceAudit:list){
						if(wfInstanceAudit.getResult().equals("2")){//审核通过
							passNum++;
						}
					}
					if(passNum>(list.size()/2)){//如果审核通过人数大于一半的参与审核人数，则进入下一个节点
						wfInstance.setState(wfInstance.getState()+1);
						wfInstanceDao.update(wfInstance);
						int lastNode = wfInstanceNodeDao.getMaxOrderNode(wfInstanceNode.getWfInstance().getId());//得到最后的审核节点
						if(lastNode!=0&&wfInstance.getState()>lastNode){//如果流程实例的状态大于最后审核节点的序号，即已审核完成，修改课程的专题
							pass(wfInstance.getId(),groupFlag,userId);//被审核对象审核通过
						}
					}else{//专家组审核不通过
						wfInstance.setState(0);//审核不通过，不进行之后的审核
						wfInstanceDao.update(wfInstance);
						noPass(wfInstance.getId(),groupFlag);//被审核对象审核不通过
					}
				}
			}
	}
	
	/**
	 * 被审核对象审核不通过
	 * @param insId
	 * @param groupFlag
	 */
	public void noPass(String insId,String groupFlag){
//		if("1".equals(groupFlag)){
//			Apply apply = applyManager.findByProperty("insId", insId);
//			apply.setApplyState(3);
//			applyManager.update(apply);
//		}
//		if("2".equals(groupFlag)){
//			Apply apply = applyManager.findByProperty("insId", insId);
//			apply.setApplyState(3);
//			applyManager.update(apply);
//		}
//		if("3".equals(groupFlag)){
//			Apply apply = applyManager.findByProperty("insId", insId);
//			apply.setApplyState(3);
//			applyManager.update(apply);
//		}
//		if("4".equals(groupFlag)){
//			ClassBigclass bigclass = classBigclassManager.findByProperty("insId", insId);
//			bigclass.setApplyState(3);//审核不通过
//			classBigclassManager.update(bigclass);
//		}
//		if("6".equals(groupFlag)){
//			Detail detail = detailManager.findByProperty("insId", insId);
//			detail.setDetailState("3");//审核不通过
//			detailManager.update(detail);
//		}
	}
	
	/**
	 * 被审核对象审核通过
	 * @param insId
	 * @param groupFlag
	 */
	public void pass(String insId,String groupFlag,String userId){
//		if("1".equals(groupFlag)){
//			Apply apply = applyManager.findByProperty("insId", insId);
//			apply.setApplyState(2);
//			applyManager.update(apply);
//			saveCourse(apply);//入库
//			saveMessage(apply.getApplyUserId(),apply.getCourseName(),userId);//发通知
//		}
//		if("2".equals(groupFlag)){
//			Apply apply = applyManager.findByProperty("insId", insId);
//			apply.setApplyState(2);
//			applyManager.update(apply);
//			saveCourse(apply);//入库
//			saveMessage(apply.getApplyUserId(),apply.getCourseName(),userId);//发通知
//		}
//		if("3".equals(groupFlag)){
//			Apply apply = applyManager.findByProperty("insId", insId);
//			apply.setApplyState(2);
//			applyManager.update(apply);
//			saveCourse(apply);//入库
//			saveMessage(apply.getApplyUserId(),apply.getCourseName(),userId);//发通知
//		}
//		if("4".equals(groupFlag)){
//			ClassBigclass bigclass = classBigclassManager.findByProperty("insId", insId);
//			bigclass.setApplyState(2);//审核通过
//			classBigclassManager.update(bigclass);
//			saveMessage(bigclass.getApplyUser(),bigclass.getName(),userId);//发通知
//		}
//		if("6".equals(groupFlag)){
//			Detail detail = detailManager.findByProperty("insId", insId);
//			detail.setDetailState("2");
//			detailManager.update(detail);//审核通过并新建一条班级数据
//			ClassTermBj classTermBj = new ClassTermBj();
//			classTermBj.setDetailId(detail.getId());//选单课id
//			classTermBj.setName(detail.getName());//班级名称   暂定为选单课名称
//			classTermBj.setState("1");//是否是选单课下的班级 0不是,1是
//			classTermBj.setPlanCount(detail.getClassCount());//计划招生人数
//			classTermBj.setTs(new Date());
//			classTermBj.setAddUserId(detail.getAddUserId());
//			classTermBj.setAddUserName(detail.getAddUserName());
//			classTermBjManager.save(classTermBj);
//			saveMessage(detail.getAddUserId(),detail.getName(),userId);//发通知
//		}
	}
	
	/**
	 * 课程审核通过，进行入库操作，即保存到Course表中
	 */
//	public void saveCourse(Apply apply){
//		Course course = new Course();
//		course.setName(apply.getCourseName());//课程名称
//		course.setCourseFlag(apply.getInsType());//课程标示
//		course.setCourseType(apply.getCourseType());//课程类型
//		course.setDescription(apply.getCourseRemark());//课程描述
//		course.setCourseTime(apply.getCourseTime());//课程学时
//		course.setTs(new Date());//创建时间
//		course.setApplyId(apply.getId());//课程申报id
//		courseManager.save(course);//保存课程信息
//	}
	
	/**
	 * 课程、组班、选单课审核通过后，发通知
	 */
	public void saveMessage(String userId,String name,String userid){
		User user = this.userManager.getById(userId);//得到用户
		User u = this.userManager.getById(userid);//发送人
		String title = "审核结果通知";
		String content = "<p>"+user.getUsername()+
		"(先生/女士):</p><p>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 您申报的"
		+name+"已审核通过。</p>";
		Message mess = new Message();
		mess.setTitle(title);
		mess.setContent(content);
		mess.setReceiverId(user.getId());
		mess.setReceiver(user.getUsername());
		mess.setStatus("1");//已发送
		mess.setTime(new Date());
		mess.setState("0");//消息状态    0为未查看
		mess.setTs(new Date());
		mess.setSponsorId(u.getId());
		mess.setMessageType("2");//消息类别 1，交互消息 ；2，业务消息；3，系统消息
		mess.setModule("3");//模块 1，学习 ；2，资源；3，教师培训；4，教学教务
		mess.setSponsor("系统");//发送人
		this.messageManager.save(mess);
		ReceiveMessage receicemess = new ReceiveMessage();
		receicemess.setReceiverId(user.getId());
		receicemess.setReceiver(user.getUsername());
		receicemess.setTitle(title);
		receicemess.setContent(content);
		receicemess.setTime(new Date());//发生时间
		receicemess.setState("0");//消息状态    0为未查看
		receicemess.setTs(new Date());
		receicemess.setModule("3");//模块 1，学习 ；2，资源；3，教师培训；4，教学教务
		receicemess.setMessageType("2");//消息类别 1，交互消息 ；2，业务消息；3，系统消息
		receicemess.setSponsorId(u.getId());//发送人
		receicemess.setSponsor("系统");
		this.receiveMessageManager.save(receicemess);
	}

//	public DetailManager getDetailManager() {
//		return detailManager;
//	}
//
//	public void setDetailManager(DetailManager detailManager) {
//		this.detailManager = detailManager;
//	}
//
//	public ClassTermBjManager getClassTermBjManager() {
//		return classTermBjManager;
//	}
//
//	public void setClassTermBjManager(ClassTermBjManager classTermBjManager) {
//		this.classTermBjManager = classTermBjManager;
//	}

}
