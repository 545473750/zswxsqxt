package com.zswxsqxt.wf.model;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;

/*
 describe:实例结果
 
*/
public class WfInstanceResult
{
	public static final String TABLE_ALIAS = "实例结果";
	private String id;
	private String refFlag;//关联类别 1分院课程 2 教研课程 3校本课程 4培训班 6 选单课
	private String refId;//关联对象	流程实例id
	private Integer state;//审核节点
	private String auditUserId;//审核人
	private Integer result;//审核结果2通过，3不通过
	private Date ts;//创建时间


	public WfInstanceResult()
	{
	}
	public WfInstanceResult(String id)
	{
		this.id=id;
	}
	public WfInstanceResult( String id , String refFlag , String refId , Integer state , String auditUserId , Integer result , Date ts )
	{
			this.id=id;
			this.refFlag=refFlag;
			this.refId=refId;
			this.state=state;
			this.auditUserId=auditUserId;
			this.result=result;
			this.ts=ts;
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
