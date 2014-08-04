package com.zswxsqxt.wf.model;

import java.util.Date;
import java.util.Set;

/*
 describe:流程实例
 
*/
public class WfInstance
{
	public static final String TABLE_ALIAS = "流程实例";
	private String id;//id
	private String name;//流程名称
	private Integer state;//当前状态
	private Date ts;//创建时间
	private String targetId;//被审核数据项的ID
	
	private Set<WfInstanceNode> wfInstanceNodes;//流程实例节点表

	public WfInstance()
	{
	}
	public WfInstance(String id)
	{
		this.id=id;
	}
	public WfInstance( String id , String name , Integer state , Date ts , String targetId )
	{
			this.id=id;
			this.name=name;
			this.state=state;
			this.ts=ts;
			this.targetId=targetId;
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
		设置流程名称
	*/
	public void setName(String name)
	{
		this.name=name;
	}
	/**
		获取流程名称
	*/
	public String  getName()
	{
		return this.name;
	}
	/**
		设置当前状态
	*/
	public void setState(Integer state)
	{
		this.state=state;
	}
	/**
		获取当前状态
	*/
	public Integer  getState()
	{
		return this.state;
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
		设置流程实例节点表
	*/
	public void setWfInstanceNodes(Set<WfInstanceNode> wfInstanceNodes)
	{
		this.wfInstanceNodes=wfInstanceNodes;
	}
	/**
		获取流程实例节点表
	*/
	public Set<WfInstanceNode>  getWfInstanceNodes()
	{
		return this.wfInstanceNodes;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
}
