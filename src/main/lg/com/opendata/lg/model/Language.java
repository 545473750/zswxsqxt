package com.opendata.lg.model;

import java.lang.String;
import java.util.Date;

/*
 describe:国际化
 
*/
public class Language
{
	public static final String TABLE_ALIAS = "国际化";
	private String id;//id
	private String code;//代码
	private String chinese;//中文
	private String english;//英文
	private String japanese;//日文
	private String russian;//法文
	private Date ts;//创建时间


	public Language()
	{
	}
	public Language(String id)
	{
		this.id=id;
	}
	public Language( String id , String code , String chinese , String english , String japanese , String russian , Date ts )
	{
			this.id=id;
			this.code=code;
			this.chinese=chinese;
			this.english=english;
			this.japanese=japanese;
			this.russian=russian;
			this.ts=ts;
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
		设置代码
	*/
	public void setCode(String code)
	{
		this.code=code;
	}
	/**
		获取代码
	*/
	public String  getCode()
	{
		return this.code;
	}
	/**
		设置中文
	*/
	public void setChinese(String chinese)
	{
		this.chinese=chinese;
	}
	/**
		获取中文
	*/
	public String  getChinese()
	{
		return this.chinese;
	}
	/**
		设置英文
	*/
	public void setEnglish(String english)
	{
		this.english=english;
	}
	/**
		获取英文
	*/
	public String  getEnglish()
	{
		return this.english;
	}
	/**
		设置日文
	*/
	public void setJapanese(String japanese)
	{
		this.japanese=japanese;
	}
	/**
		获取日文
	*/
	public String  getJapanese()
	{
		return this.japanese;
	}
	/**
		设置法文
	*/
	public void setRussian(String russian)
	{
		this.russian=russian;
	}
	/**
		获取法文
	*/
	public String  getRussian()
	{
		return this.russian;
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
