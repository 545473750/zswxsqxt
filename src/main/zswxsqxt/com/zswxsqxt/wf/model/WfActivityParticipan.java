package com.zswxsqxt.wf.model;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import com.zswxsqxt.wf.model.WfActivity;
import com.opendata.organiz.model.Organization;

/*
 describe:流程节点参与者
 
*/
public class WfActivityParticipan
{
	public static final String TABLE_ALIAS = "流程节点参与者";
	private String id;//id
	private String refId;//关联id 参与者
	private String refName;//关联名称	参与者名称
	private Integer refType;//关联类型 (1、用户，2、部门，3、角色,4、专家组)
	private String remark;//备注
	private Date ts;//创建时间
	private WfActivity wfActivity;//节点id
	private Organization organization;//单位
	private String orgId;//单位ID
	private String activityId;//节点id


	public WfActivityParticipan()
	{
	}
	public WfActivityParticipan(String id)
	{
		this.id=id;
	}
	public WfActivityParticipan( String id , String refId , Integer refType , String remark , Date ts , WfActivity wfActivity )
	{
			this.id=id;
			this.refId=refId;
			this.refType=refType;
			this.remark=remark;
			this.ts=ts;
			this.wfActivity=wfActivity;
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
	public String getActivityId() {
		return activityId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public Organization getOrganization() {
		return organization;
	}
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}
	
}
