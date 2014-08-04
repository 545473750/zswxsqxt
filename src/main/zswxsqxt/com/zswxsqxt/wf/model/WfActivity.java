package com.zswxsqxt.wf.model;

import com.zswxsqxt.wf.model.WfFunction;
import com.zswxsqxt.wf.model.WfProject;
import java.lang.Integer;
import java.lang.String;
import java.util.Set;
import java.util.Date;
import com.zswxsqxt.wf.model.WfActivityParticipan;

/*
 describe:流程节点表
 
*/
public class WfActivity
{
	public static final String TABLE_ALIAS = "流程节点表";
	private String id;//id
	private String name;//名称
	private Integer orderNum;//节点顺序
	private Integer actType;//节点类型(1 一般审核，2专家审核)
	private Integer actFlag;//节点状态 (是否完成标示0，未完成；1，已完成)
	private String description;//功能描述
	private String url;//url
	private String groupFlag;//所在组
	private String share;//是否共享审核意见，0不共享，1共享
	private Date ts;//创建时间
	private WfProject wfProject;//工程id
	private WfFunction wfFunction;//功能
	private String funId;
	private String participans;//流程节点参与者姓名字符串，临时变量，不保存到数据库
	
	private Set<WfActivityParticipan> wfActivityParticipans;//流程节点参与者
	
	public WfActivity()
	{
	}
	public WfActivity(String id)
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
		设置工程id
	*/
	public void setWfProject(WfProject wfProject)
	{
		this.wfProject=wfProject;
	}
	/**
		获取工程id
	*/
	public WfProject  getWfProject()
	{
		return this.wfProject;
	}
	/**
		设置功能
	*/
	public void setWfFunction(WfFunction wfFunction)
	{
		this.wfFunction=wfFunction;
	}
	/**
		获取功能
	*/
	public WfFunction  getWfFunction()
	{
		return this.wfFunction;
	}
	/**
		设置流程节点参与者
	*/
	public void setWfActivityParticipans(Set<WfActivityParticipan> wfActivityParticipans)
	{
		this.wfActivityParticipans=wfActivityParticipans;
	}
	/**
		获取流程节点参与者
	*/
	public Set<WfActivityParticipan>  getWfActivityParticipans()
	{
		return this.wfActivityParticipans;
	}
	public String getFunId() {
		return funId;
	}
	public void setFunId(String funId) {
		this.funId = funId;
	}
	public String getParticipans() {
		return participans;
	}
	public void setParticipans(String participans) {
		this.participans = participans;
	}
	
}
