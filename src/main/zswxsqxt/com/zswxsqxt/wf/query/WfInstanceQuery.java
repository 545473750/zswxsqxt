package com.zswxsqxt.wf.query;

import com.zswxsqxt.wf.model.WfProject;
import java.lang.Integer;
import java.lang.String;
import java.util.Set;
import com.zswxsqxt.wf.model.WfInstanceNode;
import java.util.Date;
import com.opendata.common.base.BaseQuery;

/*
 describe:流程实例查询
 
*/
public class WfInstanceQuery extends BaseQuery
{

	private String id;//id
	private String name;//流程名称
	private Integer state;//当前状态
	private Date ts;//创建时间
	private WfProject wfProject;//流程id

	private Set<WfInstanceNode> wfInstanceNodes;//流程实例节点表

	public WfInstanceQuery()
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
		设置流程id
	*/
	public void setWfProject(WfProject wfProject)
	{
		this.wfProject=wfProject;
	}
	/**
		获取流程id
	*/
	public WfProject  getWfProject()
	{
		return this.wfProject;
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
}
