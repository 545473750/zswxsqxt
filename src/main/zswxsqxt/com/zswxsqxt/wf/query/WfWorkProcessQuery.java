package com.zswxsqxt.wf.query;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import com.zswxsqxt.wf.model.WfWork;
import com.opendata.common.base.BaseQuery;

/*
 describe:流程工作任务表查询
 
*/
public class WfWorkProcessQuery extends BaseQuery
{

	private String id;//id
	private String projectId;//工作流ID
	private String actId;//节点ID
	private Integer proState;//状态
	private Date startTime;//开始时间
	private Date endTime;//结束时间
	private String userId;//创建人
	private String userName;//创建人姓名
	private Date ts;//创建时间
	private WfWork wfWork;//运行流程ID


	public WfWorkProcessQuery()
	{
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
		设置工作流ID
	*/
	public void setProjectId(String projectId)
	{
		this.projectId=projectId;
	}
	/**
		获取工作流ID
	*/
	public String  getProjectId()
	{
		return this.projectId;
	}
	/**
		设置节点ID
	*/
	public void setActId(String actId)
	{
		this.actId=actId;
	}
	/**
		获取节点ID
	*/
	public String  getActId()
	{
		return this.actId;
	}
	/**
		设置状态
	*/
	public void setProState(Integer proState)
	{
		this.proState=proState;
	}
	/**
		获取状态
	*/
	public Integer  getProState()
	{
		return this.proState;
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
		设置创建人
	*/
	public void setUserId(String userId)
	{
		this.userId=userId;
	}
	/**
		获取创建人
	*/
	public String  getUserId()
	{
		return this.userId;
	}
	/**
		设置创建人姓名
	*/
	public void setUserName(String userName)
	{
		this.userName=userName;
	}
	/**
		获取创建人姓名
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
		设置运行流程ID
	*/
	public void setWfWork(WfWork wfWork)
	{
		this.wfWork=wfWork;
	}
	/**
		获取运行流程ID
	*/
	public WfWork  getWfWork()
	{
		return this.wfWork;
	}
}
