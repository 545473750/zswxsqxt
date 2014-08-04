package com.opendata.organiz.query;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import com.opendata.common.base.BaseQuery;

/*
 describe:系统分级管理员查询
 
*/
public class ManagerQuery extends BaseQuery
{

	private String id;//id
	private String userId;//用户id
	private String name;//用户姓名
	private Integer dataScope;//数据范围
	private String scopeValue;//数据范围值
	private String menuIds;//菜单
	private Integer userState;//管理状态
	private String addUserId;//添加人ID
	private String addName;//添加人姓名
	private Date addTime;//添加时间
	private Date ts;//创建时间


	public ManagerQuery()
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
		设置用户id
	*/
	public void setUserId(String userId)
	{
		this.userId=userId;
	}
	/**
		获取用户id
	*/
	public String  getUserId()
	{
		return this.userId;
	}
	/**
		设置用户姓名
	*/
	public void setName(String name)
	{
		this.name=name;
	}
	/**
		获取用户姓名
	*/
	public String  getName()
	{
		return this.name;
	}
	/**
		设置数据范围
	*/
	public void setDataScope(Integer dataScope)
	{
		this.dataScope=dataScope;
	}
	/**
		获取数据范围
	*/
	public Integer  getDataScope()
	{
		return this.dataScope;
	}
	/**
		设置数据范围值
	*/
	public void setScopeValue(String scopeValue)
	{
		this.scopeValue=scopeValue;
	}
	/**
		获取数据范围值
	*/
	public String  getScopeValue()
	{
		return this.scopeValue;
	}
	/**
		设置菜单
	*/
	public void setMenuIds(String menuIds)
	{
		this.menuIds=menuIds;
	}
	/**
		获取菜单
	*/
	public String  getMenuIds()
	{
		return this.menuIds;
	}
	/**
		设置管理状态
	*/
	public void setUserState(Integer userState)
	{
		this.userState=userState;
	}
	/**
		获取管理状态
	*/
	public Integer  getUserState()
	{
		return this.userState;
	}
	/**
		设置添加人ID
	*/
	public void setAddUserId(String addUserId)
	{
		this.addUserId=addUserId;
	}
	/**
		获取添加人ID
	*/
	public String  getAddUserId()
	{
		return this.addUserId;
	}
	/**
		设置添加人姓名
	*/
	public void setAddName(String addName)
	{
		this.addName=addName;
	}
	/**
		获取添加人姓名
	*/
	public String  getAddName()
	{
		return this.addName;
	}
	/**
		设置添加时间
	*/
	public void setAddTime(Date addTime)
	{
		this.addTime=addTime;
	}
	/**
		获取添加时间
	*/
	public Date  getAddTime()
	{
		return this.addTime;
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
}
