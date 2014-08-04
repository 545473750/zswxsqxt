package com.zswxsqxt.wf.query;

import java.lang.String;
import java.util.Set;
import java.util.Date;
import com.zswxsqxt.wf.model.WfActivity;
import com.zswxsqxt.wf.model.WfWork;
import com.opendata.common.base.BaseQuery;

/*
 describe:流程表查询
 
*/
public class WfProjectQuery extends BaseQuery
{

	private String id;//id
	private String name;//名称
	private String groupFlag;//分组 1表课程，2表协作组，3表示组班；4表示公文
	private String addUserId;//添加人
	private String addUserName;//添加人姓名
	private Date updateTime;//最后更新时间
	private String description;//描述
	private Date ts;//创建时间

	private Set<WfWork> wfWorks;//流程工作表
	private Set<WfActivity> wfActivitys;//流程节点表

	public WfProjectQuery()
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
		设置名称
	*/
	public void setName(String name)
	{
		this.name=name;
	}
	/**
		获取名称
	*/
	public String  getName()
	{
		return this.name;
	}
	
	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	/**
		设置添加人
	*/
	public void setAddUserId(String addUserId)
	{
		this.addUserId=addUserId;
	}
	/**
		获取添加人
	*/
	public String  getAddUserId()
	{
		return this.addUserId;
	}
	/**
		设置添加人姓名
	*/
	public void setAddUserName(String addUserName)
	{
		this.addUserName=addUserName;
	}
	/**
		获取添加人姓名
	*/
	public String  getAddUserName()
	{
		return this.addUserName;
	}
	/**
		设置最后更新时间
	*/
	public void setUpdateTime(Date updateTime)
	{
		this.updateTime=updateTime;
	}
	/**
		获取最后更新时间
	*/
	public Date  getUpdateTime()
	{
		return this.updateTime;
	}
	/**
		设置描述
	*/
	public void setDescription(String description)
	{
		this.description=description;
	}
	/**
		获取描述
	*/
	public String  getDescription()
	{
		return this.description;
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
		设置流程工作表
	*/
	public void setWfWorks(Set<WfWork> wfWorks)
	{
		this.wfWorks=wfWorks;
	}
	/**
		获取流程工作表
	*/
	public Set<WfWork>  getWfWorks()
	{
		return this.wfWorks;
	}
	/**
		设置流程节点表
	*/
	public void setWfActivitys(Set<WfActivity> wfActivitys)
	{
		this.wfActivitys=wfActivitys;
	}
	/**
		获取流程节点表
	*/
	public Set<WfActivity>  getWfActivitys()
	{
		return this.wfActivitys;
	}
}
