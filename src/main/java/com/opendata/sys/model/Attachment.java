package com.opendata.sys.model;

import java.io.Serializable;
import java.lang.String;
import java.lang.Integer;
import java.math.BigDecimal;
import java.util.Date;

/*
 describe:附件表
 
*/
public class Attachment implements Serializable
{
	public static final String TABLE_ALIAS = "附件表";
	private String id;//id
	private String fileName;//文件名
	private String filePath;//文件路径
	private String uriFileName;//文件全路径
	private String extension;//文件类型
	private String fullName;//文件全名
	private String refId;//关联id
	private String resId;//资源云中资源的ID
	private String addUserId;//创建人
	private String addUserName;//创建人姓名
	private Date ts;//创建时间
	private Integer fileSize;//文件大小
	private Integer attachmentType;//通知类别 1:效能监察；2：重点项目监察     课程类别：1课程教材   2课程封面
	
	private String suffix ;//文件后缀 视频打点使用

	public Attachment()
	{
	}
	public Attachment(String id)
	{
		this.id=id;
	}
	public Attachment( String id , String fileName , String filePath , String uriFileName , String extension , String fullName , String refId , String addUserId , String addUserName , Date ts , Integer fileSize )
	{
			this.id=id;
			this.fileName=fileName;
			this.filePath=filePath;
			this.uriFileName=uriFileName;
			this.extension=extension;
			this.fullName=fullName;
			this.refId=refId;
			this.addUserId=addUserId;
			this.addUserName=addUserName;
			this.ts=ts;
			this.fileSize=fileSize;
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
	public Integer getAttachmentType() {
		return attachmentType;
	}
	public void setAttachmentType(Integer attachmentType) {
		this.attachmentType = attachmentType;
	}
	public String getSuffix() {
		return suffix;
	}
	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	
	public String getFileSizeIncludeUnit(){
		if ( fileSize!=null&&fileSize.intValue()>0 ) {
			BigDecimal size = null;
	    	if ( fileSize.intValue()<1024 ) {
	    		return fileSize+"B";
	    	}
	    	if ( fileSize<1048576L ) {
	    		size = new BigDecimal(fileSize).divide(new BigDecimal(1024), 2, BigDecimal.ROUND_CEILING);
	    		return size.doubleValue()+"KB";
	    	}
	    	if ( fileSize<1073741824L ) {
	    		return new BigDecimal(fileSize).divide(new BigDecimal(1048576), 2, BigDecimal.ROUND_CEILING).doubleValue()+"M";
	    	}
	    	return new BigDecimal(fileSize).divide(new BigDecimal(1099511627776L), 2, BigDecimal.ROUND_CEILING).doubleValue()+"G";
		}else{
			return "-";
		}
	}
	
}
