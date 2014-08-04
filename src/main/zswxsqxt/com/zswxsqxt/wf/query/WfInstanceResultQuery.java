package com.zswxsqxt.wf.query;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import com.opendata.common.base.BaseQuery;

/*
 describe:实例结果查询
 
*/
public class WfInstanceResultQuery extends BaseQuery
{

	private String id;//id
	private String refFlag;//关联类别
	private String refId;//关联对象
	private Integer state;//审核节点
	private String auditUserId;//审核人
	private Integer result;//审核结果
	private Date ts;//创建时间


	public WfInstanceResultQuery()
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
		设置关联类别
	*/
	public void setRefFlag(String refFlag)
	{
		this.refFlag=refFlag;
	}
	/**
		获取关联类别
	*/
	public String  getRefFlag()
	{
		return this.refFlag;
	}
	/**
		设置关联对象
	*/
	public void setRefId(String refId)
	{
		this.refId=refId;
	}
	/**
		获取关联对象
	*/
	public String  getRefId()
	{
		return this.refId;
	}
	/**
		设置审核节点
	*/
	public void setState(Integer state)
	{
		this.state=state;
	}
	/**
		获取审核节点
	*/
	public Integer  getState()
	{
		return this.state;
	}
	/**
		设置审核人
	*/
	public void setAuditUserId(String auditUserId)
	{
		this.auditUserId=auditUserId;
	}
	/**
		获取审核人
	*/
	public String  getAuditUserId()
	{
		return this.auditUserId;
	}
	/**
		设置审核结果
	*/
	public void setResult(Integer result)
	{
		this.result=result;
	}
	/**
		获取审核结果
	*/
	public Integer  getResult()
	{
		return this.result;
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
}
