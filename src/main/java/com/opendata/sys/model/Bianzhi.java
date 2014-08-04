package com.opendata.sys.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.opendata.organiz.model.Dept;
import com.opendata.organiz.model.Organization;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.User;

/*
 describe:系统编制
 
*/
public class Bianzhi
{
	public static final String TABLE_ALIAS = "系统编制";
	private String id;//id
	private String name;//编制名称
	private String orgId;//单位id
	private String deptId;//部门id
	private String scope;//业务范围
	private String roleId;//角色
	private String subjectId;//学科
	private String semester;//学段
	private String userId;//在编人员
	private String userName;//在编人员姓名
	private String userNum;//继教号
	private String isOut;//是否外部编制 0是  1否
	private String directHeader;//直接上级
	private String description;//编制说明
	private Integer seq;//显示顺序
	private String addUserId;//添加人ID
	private String addUserName;//添加人姓名
	private Date addTime;//添加时间
	private Date ts;//创建时间
	
	private String parentId;//直接上级
	private Bianzhi bianzhi;//上级编制
	private Set<Bianzhi> bianzhis = new HashSet<Bianzhi>();//下级编制集合
	


	public Bianzhi()
	{
	}
	public Bianzhi(String id)
	{
		this.id=id;
	}
	public Bianzhi( String id , String name , String orgId , String scope , String roleId , String subjectId , String semester , String userId , String isOut , String directHeader , String description , Integer seq , String addUserId , String addUserName , Date addTime , Date ts )
	{
			this.id=id;
			this.name=name;
			this.orgId=orgId;
			this.scope=scope;
			this.roleId=roleId;
			this.subjectId=subjectId;
			this.semester=semester;
			this.userId=userId;
			this.isOut=isOut;
			this.directHeader=directHeader;
			this.description=description;
			this.seq=seq;
			this.addUserId=addUserId;
			this.addUserName=addUserName;
			this.addTime=addTime;
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
		if("".equals(this.roleId)){
			return null;
		}
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
		if("".equals(this.userId)){
			return null;
		}
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
	
	private Role role;//角色
	public Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
	
	private User user;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	
	/*public String getDeptId() {
		return deptId;
	}
	
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}*/
	
	private  Dept dept;//所属部门
	public Dept getDept() {
		return dept;
	}
	public void setDept(Dept dept) {
		this.dept = dept;
	}
	
	// 所属单位
	private Organization org;
	public Organization getOrg() {
		return org;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	public String getParentId() {
		if("".equals(this.parentId)){
			return null;
		}
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public Bianzhi getBianzhi() {
		return bianzhi;
	}
	public void setBianzhi(Bianzhi bianzhi) {
		this.bianzhi = bianzhi;
	}
	public Set<Bianzhi> getBianzhis() {
		return bianzhis;
	}
	public void setBianzhis(Set<Bianzhi> bianzhis) {
		this.bianzhis = bianzhis;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserNum() {
		return userNum;
	}
	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	
	
	
	
	
	
	
	
}
