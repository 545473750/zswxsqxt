package com.zg.message.model;

import java.lang.String;
import java.util.Date;

/*
 describe:接收消息提醒表
 
*/
public class ReceiveMessage
{
	public static final String TABLE_ALIAS = "接收消息提醒";
	private String id;//id
	private String receiver;//接收者
	private String receiverId;//接收者id
	private String sponsor;//发起者
	private String sponsorId;//发起人id
	private String title;//标题
	private Date time;//提醒创建时间
	private String state;//消息状态    0为未查看，1为已经查看
	private Date chackTime;//查看消息时间
	private String content;//消息内容
	private String status;//发送状态  0为未发送 ，1为已发送
	
	private String module;//模块 1，学习 ；2，资源；3，教师培训；4，教学教务
	private String messageType;//消息类别 1，交互消息 ；2，业务消息；3，系统消息
	private Date ts;//创建时间


	public ReceiveMessage()
	{
	}
	public ReceiveMessage(String id)
	{
		this.id=id;
	}
	public ReceiveMessage( String id , String receiver ,String receiverId, String sponsor , String title ,Date time , String state , Date chackTime , String content ,String status, Date ts )
	{
			this.id=id;
			this.receiverId=receiverId;
			this.receiver=receiver;
			this.sponsor=sponsor;
			this.title = title;
			this.time=time;
			this.state=state;
			this.chackTime=chackTime;
			this.content=content;
			this.ts=ts;
			this.status=status;
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
	/**
	 	获取发送状态
	 */
	public String getStatus() {
		return status;
	}
	/**
 		设置发送状态
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
		获取接收人id
	 */
	public String getReceiverId() {
		return receiverId;
	}
	/**
		设置接收人id
	 */
	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
	}
	public String getSponsorId() {
		return sponsorId;
	}
	public void setSponsorId(String sponsorId) {
		this.sponsorId = sponsorId;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
}
