package com.opendata.sys.vo.query;

import java.lang.String;
import java.lang.Integer;
import java.util.Date;
import com.opendata.common.base.BaseQuery;

/*
 describe:附件表查询
 
*/
public class AttachmentQuery extends BaseQuery
{

	private String id;//id
	private String fileName;//文件名
	private String filePath;//文件路径
	private String uriFileName;//文件全路径
	private String extension;//文件类型
	private String fullName;//文件全名
	private String refId;//关联id
	private String addUserId;//创建人
	private String addUserName;//创建人姓名
	private Date ts;//创建时间
	private Integer fileSize;//文件大小


	public AttachmentQuery()
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
		设置文件名
	*/
	public void setFileName(String fileName)
	{
		this.fileName=fileName;
	}
	/**
		获取文件名
	*/
	public String  getFileName()
	{
		return this.fileName;
	}
	/**
		设置文件路径
	*/
	public void setFilePath(String filePath)
	{
		this.filePath=filePath;
	}
	/**
		获取文件路径
	*/
	public String  getFilePath()
	{
		return this.filePath;
	}
	/**
		设置文件全路径
	*/
	public void setUriFileName(String uriFileName)
	{
		this.uriFileName=uriFileName;
	}
	/**
		获取文件全路径
	*/
	public String  getUriFileName()
	{
		return this.uriFileName;
	}
	/**
		设置文件类型
	*/
	public void setExtension(String extension)
	{
		this.extension=extension;
	}
	/**
		获取文件类型
	*/
	public String  getExtension()
	{
		return this.extension;
	}
	/**
		设置文件全名
	*/
	public void setFullName(String fullName)
	{
		this.fullName=fullName;
	}
	/**
		获取文件全名
	*/
	public String  getFullName()
	{
		return this.fullName;
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
	/**
		设置创建人
	*/
	public void setAddUserId(String addUserId)
	{
		this.addUserId=addUserId;
	}
	/**
		获取创建人
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
		设置文件大小
	*/
	public void setFileSize(Integer fileSize)
	{
		this.fileSize=fileSize;
	}
	/**
		获取文件大小
	*/
	public Integer  getFileSize()
	{
		return this.fileSize;
	}
}
