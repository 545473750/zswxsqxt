package com.zg.message.query;

import java.lang.String;
import java.util.Date;
import com.opendata.common.base.BaseQuery;

/*
 describe:消息提醒表查询
 
*/
public class ReceiveMessageQuery extends BaseQuery
{

	private String id;//id
	private String receiver;//接收者
	private String receiverId;//接收者id
	private String sponsor;//发起者
	private String sponsorId;//发起人id
	private String title;//标题
	private Date time;//提醒创建时间
	private String state;//消息状态
	private Date chackTime;//查看消息时间
	private String content;//消息内容
	private Date ts;//创建时间
	private String status;//发送状态  0为未发送 ，1为已发送


	public ReceiveMessageQuery()
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
		设置接收者
	*/
	public void setReceiver(String receiver)
	{
		this.receiver=receiver;
	}
	/**
		获取接收者
	*/
	public String  getReceiver()
	{
		return this.receiver;
	}
	/**
		设置发起者
	*/
	public void setSponsor(String sponsor)
	{
		this.sponsor=sponsor;
	}
	/**
		获取发起者
	*/
	public String  getSponsor()
	{
		return this.sponsor;
	}
	/**
		设置提醒创建时间
	*/
	public void setTime(Date time)
	{
		this.time=time;
	}
	/**
		获取提醒创建时间
	*/
	public Date  getTime()
	{
		return this.time;
	}
	/**
		设置消息状态
	*/
	public void setState(String state)
	{
		this.state=state;
	}
	/**
		获取消息状态
	*/
	public String  getState()
	{
		return this.state;
	}
	/**
		设置查看消息时间
	*/
	public void setChackTime(Date chackTime)
	{
		this.chackTime=chackTime;
	}
	/**
		获取查看消息时间
	*/
	public Date  getChackTime()
	{
		return this.chackTime;
	}
	/**
		设置消息内容
	*/
	public void setContent(String content)
	{
		this.content=content;
	}
	/**
		获取消息内容
	*/
	public String  getContent()
	{
		return this.content;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}

	public String getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}


	
}
