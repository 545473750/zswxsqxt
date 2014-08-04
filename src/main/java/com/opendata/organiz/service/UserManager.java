/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.organiz.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.org.rapid_framework.page.Page;

import com.opendata.common.base.BaseManager;
import com.opendata.common.base.EntityDao;
import com.opendata.common.util.SecretKeyUtil;
import com.opendata.organiz.dao.RoleDao;
import com.opendata.organiz.dao.UserDao;
import com.opendata.organiz.model.Role;
import com.opendata.organiz.model.User;
import com.opendata.organiz.vo.query.UserQuery;

/**
 * 用户业务层
 * @author 顾保臣
 */
@Service
@Transactional
public class UserManager extends BaseManager<User,java.lang.String>{

	private UserDao userDao;
	public RoleDao roleDao;
	/**增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,请注意大小写*/
	public void setUserDao(UserDao dao) {
		this.userDao = dao;
	}
	public EntityDao getEntityDao() {
		return this.userDao;
	}
	
	@Transactional(readOnly=true)
	public Page findPage(UserQuery query) {
		return userDao.findPage(query);
	}
	
	/**
	 * 加载组织机构下的用户列表
	 * @param query
	 * @return
	 */
	@Transactional(readOnly=true)
	public Page findPageCommon(UserQuery query) {
		return userDao.findPageCommon(query);
	}
	/**
	 * 批量更新用户
	 */
	public void batchUpdate(List<User> users){
		for(User user:users){
			this.userDao.update(user);
		}
	}
	/**
	 * 批量更新用户继教号
	 * @param list
	 */
	public void batchUpdateUserNum(List<Map<String ,String>> list){
//		UPDATE t_user SET userNum = '05000884' WHERE IDNum = '110228197902195922';  UPDATE t_user SET userNum = '05026181' WHERE IDNum = '372423196410140014';
		String sql="";
		int i=1;
		for(Map<String ,String> map:list){
			String userNum = map.get("userNum");
			String IDNum = map.get("IDNum");
			sql+=" UPDATE t_user SET userNum = '"+userNum+"' WHERE IDNum = '"+IDNum+"'; ";
			i++;
			if(i%100==0){
				this.userDao.executeBySql(sql);
				sql="";
				System.out.println("同步第"+i+"条");
			}
		}
		
	}
	/**
	 * 为每个用户赋予默认角色教师
	 */
	public void saveUserRole(){
		List<User> users = this.userDao.findAll();
		for(User user : users){
			Set<Role> roles = user.getRoles();
			if(roles.size()==0){
				Role role = this.roleDao.getById("4028801946754b2b0146756a2412009e");
	            roles.add(role);
	            user.setRoles(roles);
			}
            user.setSource("0");
            userDao.update(user);
		}
	}
	
	/**
	 * 按照登录名匹配用户
	 * @param name
	 * @return
	 */
	public User findByName(String name){
		List<User> list = userDao.getHibernateTemplate().find(" from User where df='0' and loginname=? ",name);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	/**
	 * 按照登录名匹配用户(包括df='0'的用户)
	 * @param name
	 * @return
	 */
	public User findAllUserByName(String name){
		List<User> list = userDao.getHibernateTemplate().find(" from User where loginname=? ",name);
		if(list.isEmpty()){
			return null;
		}else{
			return list.get(0);
		}
	}
	
	/**
	 * 去除部分用户，查询用户
	 * @param userIds
	 * @return
	 */
	public List<User> findUserNotInIds(String userIds){
		String sql = " select id,username from t_user where df='0'  ";
		if(StringUtils.isNotBlank(userIds)){
			sql+=" and id not in "+userIds+"";
		}
		return userDao.findFastBySql(sql, User.class);
	}
	
	/**
	 * 更新工资为0
	 */
	public void updateSalary(){
		String sql ="update t_user set salary = ?";
		String st="";
		try {
				SecretKeyUtil des = new SecretKeyUtil("");
				st=des.encrypt("0");
		} catch (Exception e) {
		}
		userDao.executeBySql(sql, st);
		
	}
	
	/**
	 * 根据用户id和期数招生id查询集体报名的学员，排除个人报过名的
	 * @param paramter
	 * @return
	 */
	public List<User> findUsers(Map<String, String> paramter){
		List<User> users = userDao.findFastByFreeSql("findUsers", paramter);
		if(users!=null && users.size()>0){
			return users;
		}else{
			return null;
		}
	}
	
	/**
	 * 根据用户id和期数招生id查询集体报名的学员
	 * @param paramter
	 * @return
	 */
	public List<User> findUsers2(Map<String, String> paramter){
		List<User> users = userDao.findFastByFreeSql("findUsers2", paramter);
		if(users!=null && users.size()>0){
			return users;
		}else{
			return null;
		}
	}
	
	/**
	 * 协作组组长 所有用户
	 */
	public Page findHead(UserQuery query){
		StringBuilder hql=new StringBuilder();
		hql.append("select u.id,u.username,relation.organization.name from User u join u.ouRelations relation  where u.df='0' ");
		List<Object> param=new ArrayList<Object>();
		if(StringUtils.isNotEmpty(query.getUsername())){
			hql.append(" and u.username like ? ");
			param.add("%" + query.getUsername() + "%");
		}
		if(StringUtils.isNotEmpty(query.getDeptName())){
			hql.append(" and relation.organization.name like ? ");
			param.add("%" + query.getDeptName() + "%");
		}
		Page page = this.userDao.findByHql(hql.toString(), query.getPageSize(), query.getPageNumber(), param.toArray());
		List<Object[]> objList = page.getResult();
		List<User> userList = new ArrayList<User>();
		if(objList != null){
			for(Object[] obj : objList){
				User user = new User();
				user.setId(obj[0].toString());
				user.setUsername(obj[1].toString());
				user.setDeptsString(obj[2]==null?"":obj[2].toString());
				userList.add(user);
			}
		}
		page.setResult(userList);
		return page;
	}
	
	/**
	 * 评课教师列表(本单位-发起测评)
	 * @param query
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page findLocalPage(UserQuery query , String deptId)
	{
		String hql = "select u from User u join u.ouRelations relation with  relation.organizationId = ? where 1=1 ";
		List<Object> param=new ArrayList<Object>();
		param.add(deptId);
		if(StringUtils.isNotEmpty(query.getUsername())) {
			hql += " and relation.user.username like ? ";
			param.add("%" + query.getUsername() + "%");
		}
		return this.userDao.findByHql(hql,query.getPageSize(),query.getPageNumber(),param.toArray());
	}
	
	/**
	 * 评课教师列表(跨单位-发起测评)
	 * @param query
	 * @return
	 */
	@Transactional(readOnly = true)
	public Page findOutPage(UserQuery query , String deptId)
	{
		StringBuilder hql=new StringBuilder();
		List<Object> param=new ArrayList<Object>();
		hql.append("select u.id,u.username,relation.organization.name from User u join u.ouRelations relation with  relation.organizationId != ? where 1=1 ");
		param.add(deptId);
		
		if(StringUtils.isNotEmpty(query.getUsername())){
			hql.append(" and u.username like ? ");
			param.add("%" + query.getUsername() + "%");
		}
		if(StringUtils.isNotEmpty(query.getDeptName())){
			hql.append(" and relation.organization.name like ? ");
			param.add("%" + query.getDeptName() + "%");
		}
		Page page = this.userDao.findByHql(hql.toString(), query.getPageSize(), query.getPageNumber(), param.toArray());
		List<Object[]> objList = page.getResult();
		List<User> userList = new ArrayList<User>();
		if(objList != null){
			for(Object[] obj : objList){
				User user = new User();
				user.setId(obj[0].toString());
				user.setUsername(obj[1].toString());
				user.setDeptsString(obj[2]==null?"":obj[2].toString());
				userList.add(user);
			}
		}
		page.setResult(userList);
		return page;
	}
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	
	
}
