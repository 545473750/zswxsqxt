package com.zswxsqxt.wf.model;

import com.zswxsqxt.wf.model.WfWorkProcess;
import com.zswxsqxt.wf.model.WfProject;
import java.lang.Integer;
import java.lang.String;
import java.util.Set;
import com.zswxsqxt.wf.model.WfWorkResult;
import java.util.Date;

/*
 describe:流程工作表
 
*/
public class WfWork
{
	public static final String TABLE_ALIAS = "流程工作表";
	private String id;//id
	private String projectId;//工程id
	private String name;//当前业务名称
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private Integer workState;//状态
	private String userId;//启动人
	private String userName;//启动人姓名
	private Date ts;//创建时间
	private WfProject wfProject;//projectId

	private Set<WfWorkProcess> wfWorkProcesss;//流程工作任务表
	private Set<WfWorkResult> wfWorkResults;//流程工作结果

	public WfWork()
	{
	}
	public WfWork(String id)
	{
		this.id=id;
	}
	public WfWork( String id , String projectId , String name , Date startTime , Date endTime , Integer workState , String userId , String userName , Date ts , WfProject wfProject )
	{
			this.id=id;
			this.projectId=projectId;
			this.name=name;
			this.startTime=startTime;
			this.endTime=endTime;
			this.workState=workState;
			this.userId=userId;
			this.userName=userName;
			this.ts=ts;
			this.wfProject=wfProject;
	}
	/**
		设置id
	*/
	public void setId(String id)
	{
		this.id=id;
	}
	/**
		获取id
	*/
	public String  getId()
	{
		return this.id;
	}
	/**
		设置工程id
	*/
	public void setProjectId(String projectId)
	{
		this.projectId=projectId;
	}
	/**
		获取工程id
	*/
	public String  getProjectId()
	{
		return this.projectId;
	}
	/**
		设置当前业务名称
	*/
	public void setName(String name)
	{
		this.name=name;
	}
	/**
		获取当前业务名称
	*/
	public String  getName()
	{
		return this.name;
	}
	/**
		设置开始时间
	*/
	public void setStartTime(Date startTime)
	{
		this.startTime=startTime;
	}
	/**
		获取开始时间
	*/
	public Date  getStartTime()
	{
		return this.startTime;
	}
	/**
		设置结束时间
	*/
	public void setEndTime(Date endTime)
	{
		this.endTime=endTime;
	}
	/**
		获取结束时间
	*/
	public Date  getEndTime()
	{
		return this.endTime;
	}
	/**
		设置状态
	*/
	public void setWorkState(Integer workState)
	{
		this.workState=workState;
	}
	/**
		获取状态
	*/
	public Integer  getWorkState()
	{
		return this.workState;
	}
	/**
		设置启动人
	*/
	public void setUserId(String userId)
	{
		this.userId=userId;
	}
	/**
		获取启动人
	*/
	public String  getUserId()
	{
		return this.userId;
	}
	/**
		设置启动人姓名
	*/
	public void setUserName(String userName)
	{
		this.userName=userName;
	}
	/**
		获取启动人姓名
	*/
	public String  getUserName()
	{
		return this.userName;
	}
	/**
		设置创建时间
	*/
	public void setTs(Date ts)
	{
		this.ts=ts;
	}
	/**
		获取创建时间
	*/
	public Date  getTs()
	{
		return this.ts;
	}
	/**
		设置id
	*/
	public void setWfProject(WfProject wfProject)
	{
		this.wfProject=wfProject;
	}
	/**
		获取id
	*/
	public WfProject  getWfProject()
	{
		return this.wfProject;
	}
	/**
		设置流程工作任务表
	*/
	public void setWfWorkProcesss(Set<WfWorkProcess> wfWorkProcesss)
	{
		this.wfWorkProcesss=wfWorkProcesss;
	}
	/**
		获取流程工作任务表
	*/
	public Set<WfWorkProcess>  getWfWorkProcesss()
	{
		return this.wfWorkProcesss;
	}
	/**
		设置流程工作结果
	*/
	public void setWfWorkResults(Set<WfWorkResult> wfWorkResults)
	{
		this.wfWorkResults=wfWorkResults;
	}
	/**
		获取流程工作结果
	*/
	public Set<WfWorkResult>  getWfWorkResults()
	{
		return this.wfWorkResults;
	}
}
