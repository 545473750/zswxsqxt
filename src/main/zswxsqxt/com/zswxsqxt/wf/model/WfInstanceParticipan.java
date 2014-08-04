package com.zswxsqxt.wf.model;

import java.lang.Integer;
import java.lang.String;
import com.zswxsqxt.wf.model.WfInstanceNode;
import java.util.Date;

/*
 describe:流程参与者
 
*/
public class WfInstanceParticipan
{
	public static final String TABLE_ALIAS = "流程参与者";
	private String id;//id
	private String refId;//关联id
	private String refName;//关联名称
	private Integer refType;//关联类型
	private String remark;//备注
	private String orgId;//单位id
	private Date ts;//创建时间
	private WfInstanceNode wfInstanceNode;//节点id


	public WfInstanceParticipan()
	{
	}
	public WfInstanceParticipan(String id)
	{
		this.id=id;
	}
	public WfInstanceParticipan( String id , String refId , Integer refType , String remark , String orgId , Date ts , WfInstanceNode wfInstanceNode )
	{
			this.id=id;
			this.refId=refId;
			this.refType=refType;
			this.remark=remark;
			this.orgId=orgId;
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
		设置关联id
	*/
	public void setRefId(String refId)
	{
		this.refId=refId;
	}
	/**
		获取关联id
	*/
	public String  getRefId()
	{
		return this.refId;
	}
	
	
	public String getRefName() {
		return refName;
	}
	public void setRefName(String refName) {
		this.refName = refName;
	}
	/**
		设置关联类型
	*/
	public void setRefType(Integer refType)
	{
		this.refType=refType;
	}
	/**
		获取关联类型
	*/
	public Integer  getRefType()
	{
		return this.refType;
	}
	/**
		设置备注
	*/
	public void setRemark(String remark)
	{
		this.remark=remark;
	}
	/**
		获取备注
	*/
	public String  getRemark()
	{
		return this.remark;
	}
	/**
		设置单位id
	*/
	public void setOrgId(String orgId)
	{
		this.orgId=orgId;
	}
	/**
		获取单位id
	*/
	public String  getOrgId()
	{
		return this.orgId;
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
		设置节点id
	*/
	public void setWfInstanceNode(WfInstanceNode wfInstanceNode)
	{
		this.wfInstanceNode=wfInstanceNode;
	}
	/**
		获取节点id
	*/
	public WfInstanceNode  getWfInstanceNode()
	{
		return this.wfInstanceNode;
	}
}
