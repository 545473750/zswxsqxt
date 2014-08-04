package com.zswxsqxt.wf.query;

import java.lang.String;
import com.zswxsqxt.wf.model.WfInstanceNode;
import java.util.Date;
import com.opendata.common.base.BaseQuery;

/*
 describe:流程审核表查询
 
*/
public class WfInstanceAuditQuery extends BaseQuery
{

	private String id;//id
	private String result;//审核结果
	private String opinion;//审核意见
	private String auditUserId;//审核人
	private String auditUserName;//审核人姓名
	private Date ts;//创建时间
	private WfInstanceNode wfInstanceNode;//实例节点
	private String insId;//流程实例id
	private Integer state;//流程实例状态，即实例节点的标示
	private String insType;//课程类型
	public WfInstanceAuditQuery()
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
		设置审核结果
	*/
	public void setResult(String result)
	{
		this.result=result;
	}
	/**
		获取审核结果
	*/
	public String  getResult()
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

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getInsType() {
		return insType;
	}

	public void setInsType(String insType) {
		this.insType = insType;
	}
}
