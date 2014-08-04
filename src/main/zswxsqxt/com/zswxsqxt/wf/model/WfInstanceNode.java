package com.zswxsqxt.wf.model;

import com.zswxsqxt.wf.model.WfInstanceAudit;
import com.zswxsqxt.wf.model.WfInstanceFunction;
import com.zswxsqxt.wf.model.WfInstance;
import com.zswxsqxt.wf.model.WfInstanceParticipan;
import java.lang.Integer;
import java.lang.String;
import java.util.Set;
import java.util.Date;

/*
 describe:流程实例节点表
 
*/
public class WfInstanceNode
{
	public static final String TABLE_ALIAS = "流程实例节点表";
	private String id;//id
	private String name;//名称
	private Integer orderNum;//节点顺序
	private Integer actType;//节点类型(1 一般审核，2专家审核)
	private Integer actFlag;//节点标示
	private String description;//功能描述
	private Integer ynCountersign;//是否会签 否0，是1  0否就是一个人审核，1就是多个人审核
	private Integer signNum;//会签人数
	private String url;//url
	private String groupFlag;//所在组
	private String share;//是否共享审核意见，0不共享，1共享
	private Date ts;//创建时间
	private WfInstanceFunction wfInstanceFunction;//功能
	private WfInstance wfInstance;//实例id
	
	private Set<WfInstanceParticipan> wfInstanceParticipans;//流程参与者
	private Set<WfInstanceAudit> wfInstanceAudits;//流程审核表

	public WfInstanceNode()
	{
	}
	public WfInstanceNode(String id)
	{
		this.id=id;
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

	public String getShare() {
		return share;
	}
	public void setShare(String share) {
		this.share = share;
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
		设置功能
	*/
	public void setWfInstanceFunction(WfInstanceFunction wfInstanceFunction)
	{
		this.wfInstanceFunction=wfInstanceFunction;
	}
	/**
		获取功能
	*/
	public WfInstanceFunction  getWfInstanceFunction()
	{
		return this.wfInstanceFunction;
	}
	/**
		设置实例id
	*/
	public void setWfInstance(WfInstance wfInstance)
	{
		this.wfInstance=wfInstance;
	}
	/**
		获取实例id
	*/
	public WfInstance  getWfInstance()
	{
		return this.wfInstance;
	}
	/**
		设置流程参与者
	*/
	public void setWfInstanceParticipans(Set<WfInstanceParticipan> wfInstanceParticipans)
	{
		this.wfInstanceParticipans=wfInstanceParticipans;
	}
	/**
		获取流程参与者
	*/
	public Set<WfInstanceParticipan>  getWfInstanceParticipans()
	{
		return this.wfInstanceParticipans;
	}
	/**
		设置流程审核表
	*/
	public void setWfInstanceAudits(Set<WfInstanceAudit> wfInstanceAudits)
	{
		this.wfInstanceAudits=wfInstanceAudits;
	}
	/**
		获取流程审核表
	*/
	public Set<WfInstanceAudit>  getWfInstanceAudits()
	{
		return this.wfInstanceAudits;
	}
	
	public Integer getSignNum() {
		return signNum;
	}
	public void setSignNum(Integer signNum) {
		this.signNum = signNum;
	}
	
	public Integer getYnCountersign() {
		return ynCountersign;
	}
	public void setYnCountersign(Integer ynCountersign) {
		this.ynCountersign = ynCountersign;
	}
}
