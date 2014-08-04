package com.zswxsqxt.wf.model;

import java.lang.String;
import com.zswxsqxt.wf.model.WfInstanceNode;
import java.util.Date;

/*
 describe:流程审核表
 
*/
public class WfInstanceAudit
{
	public static final String TABLE_ALIAS = "流程审核表";
	private String id;//id
	private Integer result;//1：通过，2：未通过
	private String opinion;//审核意见
	private String auditUserId;//审核人
	private String auditUserName;//审核人姓名
	private Date ts;//创建时间
	private WfInstanceNode wfInstanceNode;//实例节点


	public WfInstanceAudit()
	{
	}
	public WfInstanceAudit(String id)
	{
		this.id=id;
	}
	public WfInstanceAudit( String id , Integer result , String opinion , String auditUserId , String auditUserName , Date ts , WfInstanceNode wfInstanceNode )
	{
			this.id=id;
			this.result=result;
			this.opinion=opinion;
			this.auditUserId=auditUserId;
			this.auditUserName=auditUserName;
			this.ts=ts;
			this.wfInstanceNode=wfInstanceNode;
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
		设置审核结果
	*/
	public void setResult(Integer result)
	{
		this.result=result;
	}
	/**
		获取审核结果
	*/
	public Integer getResult()
	{
		return this.result;
	}
	/**
		设置审核意见
	*/
	public void setOpinion(String opinion)
	{
		this.opinion=opinion;
	}
	/**
		获取审核意见
	*/
	public String  getOpinion()
	{
		return this.opinion;
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
		设置审核人姓名
	*/
	public void setAuditUserName(String auditUserName)
	{
		this.auditUserName=auditUserName;
	}
	/**
		获取审核人姓名
	*/
	public String  getAuditUserName()
	{
		return this.auditUserName;
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
		设置实例节点
	*/
	public void setWfInstanceNode(WfInstanceNode wfInstanceNode)
	{
		this.wfInstanceNode=wfInstanceNode;
	}
	/**
		获取实例节点
	*/
	public WfInstanceNode  getWfInstanceNode()
	{
		return this.wfInstanceNode;
	}
}
