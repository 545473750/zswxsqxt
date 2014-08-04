package com.zswxsqxt.wf.query;

import java.lang.String;
import java.lang.Integer;
import java.util.Set;
import java.util.Date;
import com.zswxsqxt.wf.model.WfActivity;
import com.opendata.common.base.BaseQuery;

/*
 describe:流程功能点表查询
 
*/
public class WfFunctionQuery extends BaseQuery
{

	private String id;//id
	private String name;//名称
	private String url;//页面url
	private Integer groupFlag;//1表课程，2表协作组，3表示组班；4表示公文
	private String parName;//参数名称
	private String description;//功能描述
	private Integer funType;//功能分类
	private Integer insType;//节点类型
	private Date ts;//创建时间

	private Set<WfActivity> wfActivitys;//流程节点表

	public WfFunctionQuery()
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
		设置页面url
	*/
	public void setUrl(String url)
	{
		this.url=url;
	}
	/**
		获取页面url
	*/
	public String  getUrl()
	{
		return this.url;
	}
	
	public Integer getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(Integer groupFlag) {
		this.groupFlag = groupFlag;
	}

	/**
		设置参数名称
	*/
	public void setParName(String parName)
	{
		this.parName=parName;
	}
	/**
		获取参数名称
	*/
	public String  getParName()
	{
		return this.parName;
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
	/**
		设置功能分类
	*/
	public void setFunType(Integer funType)
	{
		this.funType=funType;
	}
	/**
		获取功能分类
	*/
	public Integer  getFunType()
	{
		return this.funType;
	}
	/**
		设置节点类型
	*/
	public void setInsType(Integer insType)
	{
		this.insType=insType;
	}
	/**
		获取节点类型
	*/
	public Integer  getInsType()
	{
		return this.insType;
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
