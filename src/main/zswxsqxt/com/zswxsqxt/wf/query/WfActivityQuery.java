package com.zswxsqxt.wf.query;

import com.zswxsqxt.wf.model.WfFunction;
import com.zswxsqxt.wf.model.WfProject;
import java.lang.Integer;
import java.lang.String;
import java.util.Set;
import java.util.Date;
import com.zswxsqxt.wf.model.WfActivityParticipan;
import com.opendata.common.base.BaseQuery;

/*
 describe:流程节点表查询
 
*/
public class WfActivityQuery extends BaseQuery
{

	private String id;//id
	private String name;//名称
	private Integer orderNum;//节点顺序
	private Integer actType;//节点类型
	private Integer actFlag;//节点状态
	private String description;//功能描述
	private String url;//扩展字段1
	private String groupFlag;//所在组，传值用于过滤功能点
	private String extFiled3;//扩展字段3
	private Date ts;//创建时间
	private WfProject wfProject;//工程id
	private WfFunction wfInstance;//功能
	private Set<WfActivityParticipan> wfActivityParticipans;//流程节点参与者

	public WfActivityQuery()
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
	/**
		设置节点顺序
	*/
	public void setOrderNum(Integer orderNum)
	{
		this.orderNum=orderNum;
	}
	/**
		获取节点顺序
	*/
	public Integer  getOrderNum()
	{
		return this.orderNum;
	}
	/**
		设置节点类型
	*/
	public void setActType(Integer actType)
	{
		this.actType=actType;
	}
	/**
		获取节点类型
	*/
	public Integer  getActType()
	{
		return this.actType;
	}
	/**
		设置节点状态
	*/
	public void setActFlag(Integer actFlag)
	{
		this.actFlag=actFlag;
	}
	/**
		获取节点状态
	*/
	public Integer  getActFlag()
	{
		return this.actFlag;
	}
	/**
		设置功能描述
	*/
	public void setDescription(String description)
	{
		this.description=description;
	}
	/**
		获取功能描述
	*/
	public String  getDescription()
	{
		return this.description;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

	/**
		设置扩展字段3
	*/
	public void setExtFiled3(String extFiled3)
	{
		this.extFiled3=extFiled3;
	}
	/**
		获取扩展字段3
	*/
	public String  getExtFiled3()
	{
		return this.extFiled3;
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
		设置工程id
	*/
	public void setWfProject(WfProject wfProject)
	{
		this.wfProject=wfProject;
	}
	/**
		获取工程id
	*/
	public WfProject  getWfProject()
	{
		return this.wfProject;
	}
	/**
		设置功能
	*/
	public void setWfInstance(WfFunction wfInstance)
	{
		this.wfInstance=wfInstance;
	}
	/**
		获取功能
	*/
	public WfFunction  getWfInstance()
	{
		return this.wfInstance;
	}
	/**
		设置流程节点参与者
	*/
	public void setWfActivityParticipans(Set<WfActivityParticipan> wfActivityParticipans)
	{
		this.wfActivityParticipans=wfActivityParticipans;
	}
	/**
		获取流程节点参与者
	*/
	public Set<WfActivityParticipan>  getWfActivityParticipans()
	{
		return this.wfActivityParticipans;
	}
}
