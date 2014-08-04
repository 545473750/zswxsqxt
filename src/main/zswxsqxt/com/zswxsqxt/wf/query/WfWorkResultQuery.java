package com.zswxsqxt.wf.query;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import com.zswxsqxt.wf.model.WfWork;
import com.opendata.common.base.BaseQuery;

/*
 describe:流程工作结果查询
 
*/
public class WfWorkResultQuery extends BaseQuery
{

	private String id;//id
	private Integer proResult;//操作结果
	private String addUserId;//操作人
	private String addUserName;//创建人姓名
	private String remark;//操作备注
	private Date ts;//创建时间
	private WfWork wfWork;//流程进程ID


	public WfWorkResultQuery()
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
		设置操作结果
	*/
	public void setProResult(Integer proResult)
	{
		this.proResult=proResult;
	}
	/**
		获取操作结果
	*/
	public Integer  getProResult()
	{
		return this.proResult;
	}
	/**
		设置操作人
	*/
	public void setAddUserId(String addUserId)
	{
		this.addUserId=addUserId;
	}
	/**
		获取操作人
	*/
	public String  getAddUserId()
	{
		return this.addUserId;
	}
	/**
		设置创建人姓名
	*/
	public void setAddUserName(String addUserName)
	{
		this.addUserName=addUserName;
	}
	/**
		获取创建人姓名
	*/
	public String  getAddUserName()
	{
		return this.addUserName;
	}
	/**
		设置操作备注
	*/
	public void setRemark(String remark)
	{
		this.remark=remark;
	}
	/**
		获取操作备注
	*/
	public String  getRemark()
	{
		return this.remark;
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
		设置流程进程ID
	*/
	public void setWfWork(WfWork wfWork)
	{
		this.wfWork=wfWork;
	}
	/**
		获取流程进程ID
	*/
	public WfWork  getWfWork()
	{
		return this.wfWork;
	}
}
