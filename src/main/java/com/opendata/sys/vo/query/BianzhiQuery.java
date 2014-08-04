package com.opendata.sys.vo.query;

import java.lang.Integer;
import java.lang.String;
import java.util.Date;
import com.opendata.common.base.BaseQuery;

/*
 describe:系统编制查询
 
*/
public class BianzhiQuery extends BaseQuery
{

	private String id;//id
	private String name;//编制名称
	private String orgId;//单位id
	private String deptId;//部门id
	private String scope;//业务范围
	private String roleId;//角色
	private String subjectId;//学科
	private String semester;//学段
	private String userId;//在编人员
	private String isOut;//是否外部编制
	private String directHeader;//直接上级
	private String description;//编制说明
	private Integer seq;//显示顺序
	private String addUserId;//添加人ID
	private String addUserName;//添加人姓名
	private Date addTime;//添加时间
	private Date ts;//创建时间
	
	
	

	public BianzhiQuery()
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
		设置编制名称
	*/
	public void setName(String name)
	{
		this.name=name;
	}
	/**
		获取编制名称
	*/
	public String  getName()
	{
		return this.name;
	}
	/**
		设置所属部门
	*/
	public void setOrgId(String orgId)
	{
		this.orgId=orgId;
	}
	/**
		获取所属部门
	*/
	public String  getOrgId()
	{
		return this.orgId;
	}
	/**
		设置业务范围
	*/
	public void setScope(String scope)
	{
		this.scope=scope;
	}
	/**
		获取业务范围
	*/
	public String  getScope()
	{
		return this.scope;
	}
	/**
		设置角色
	*/
	public void setRoleId(String roleId)
	{
		this.roleId=roleId;
	}
	/**
		获取角色
	*/
	public String  getRoleId()
	{
		return this.roleId;
	}
	/**
		设置学科
	*/
	public void setSubjectId(String subjectId)
	{
		this.subjectId=subjectId;
	}
	/**
		获取学科
	*/
	public String  getSubjectId()
	{
		return this.subjectId;
	}
	/**
		设置学段
	*/
	public void setSemester(String semester)
	{
		this.semester=semester;
	}
	/**
		获取学段
	*/
	public String  getSemester()
	{
		return this.semester;
	}
	/**
		设置在编人员
	*/
	public void setUserId(String userId)
	{
		this.userId=userId;
	}
	/**
		获取在编人员
	*/
	public String  getUserId()
	{
		return this.userId;
	}
	/**
		设置是否外部编制
	*/
	public void setIsOut(String isOut)
	{
		this.isOut=isOut;
	}
	/**
		获取是否外部编制
	*/
	public String  getIsOut()
	{
		return this.isOut;
	}
	/**
		设置直接上级
	*/
	public void setDirectHeader(String directHeader)
	{
		this.directHeader=directHeader;
	}
	/**
		获取直接上级
	*/
	public String  getDirectHeader()
	{
		return this.directHeader;
	}
	/**
		设置编制说明
	*/
	public void setDescription(String description)
	{
		this.description=description;
	}
	/**
		获取编制说明
	*/
	public String  getDescription()
	{
		return this.description;
	}
	/**
		设置显示顺序
	*/
	public void setSeq(Integer seq)
	{
		this.seq=seq;
	}
	/**
		获取显示顺序
	*/
	public Integer  getSeq()
	{
		return this.seq;
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
	public void setAddUserName(String addUserName)
	{
		this.addUserName=addUserName;
	}
	/**
		获取添加人姓名
	*/
	public String  getAddUserName()
	{
		return this.addUserName;
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

	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
}
