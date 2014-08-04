package com.zswxsqxt.wf.query;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import com.zswxsqxt.wf.model.WfActivity;
import com.opendata.common.base.BaseQuery;

/*
 describe:流程节点参与者查询
 
*/
public class WfActivityParticipanQuery extends BaseQuery
{

	private String id;//id
	private String refId;//关联id
	private String refName;//关联名称
	private Integer refType;//关联类型
	private String remark;//备注
	private Date ts;//创建时间
	private WfActivity wfActivity;//节点id
	private String actType;//1一般审核，2专家组审核
	private String proId;//流程id,返回传值使用
	private String groupFlag;//所在组,返回传值使用
	public WfActivityParticipanQuery()
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
	public void setWfActivity(WfActivity wfActivity)
	{
		this.wfActivity=wfActivity;
	}
	/**
		获取节点id
	*/
	public WfActivity  getWfActivity()
	{
		return this.wfActivity;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getProId() {
		return proId;
	}

	public void setProId(String proId) {
		this.proId = proId;
	}

	public String getGroupFlag() {
		return groupFlag;
	}

	public void setGroupFlag(String groupFlag) {
		this.groupFlag = groupFlag;
	}

}
