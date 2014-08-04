package com.opendata.webservice.application.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;

import com.opendata.application.model.Application;
import com.opendata.application.model.Permission;
import com.opendata.application.service.ApplicationManager;
import com.opendata.application.service.PermissionManager;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.UserManager;
import com.opendata.webservice.application.ApplicationWebService;

@WebService(endpointInterface="com.opendata.webservice.application.ApplicationWebService")
public class ApplicationWebServiceImpl implements ApplicationWebService{
	
	private ApplicationManager applicationManager;
	private PermissionManager permissionManager;
	

	public void setApplicationManager(ApplicationManager applicationManager) {
		this.applicationManager = applicationManager;
	}

	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}
	

	@Override
	public String insertApp(String code, String name, String description,String setUrl) {
		if(code==null||"".equals(code)||name==null||"".equals(name)){
			return "1";//表示标识或名称为空
		}
		Application application = new Application();
		application.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		application.setCode(code);
		application.setName(name);
		application.setDescription(description);
		//判断CODE是不是唯一 
		if(applicationManager.findByCode(code)!=null){
			return "2";//表示CODE重复 添加失败
		}
		try{
		// TODO Auto-generated method stub
			applicationManager.save(application);
		}catch(Exception e){
			return "3";//表示存入数据库时出现错误 添加失败
		}
		 return "0";
	}
    /**
     * 返回0表示添加成功  1表示应用标识或标识、名称为空 2表示权限所属应用不存在 3表示父权限不存在 4表示同一应用下code重复 5表示同一父权限下的名称重复 6表示保存数据失败
     */
	@Override
	public String insertPer(String appCode, String code, String name,
			String url, String parentCode,String description) {
		if(appCode==null||"".equals(appCode)||code==null||"".equals(code)||name==null||"".equals(name)){
			return "1";//表示应用标识或标识、名称为空
		}
		//先通过appCode 查询应用 然后通过parentCode 查询上级权限 然后判断code是不是唯一
		Application application = applicationManager.findByCode(appCode);//权限所属应用
		if(application == null){
			return "2";//表示权限所属应用不存在
		}
		String parentId=null;
		Permission permission = new Permission();
		//如果parentCode不为空，表示此权限有父权限，先查询所属父权限
		if(parentCode!=null&&!"".equals(parentCode)){
			Permission parentPer = permissionManager.findByCode(parentCode);//查询权限所属父权限
			if(parentPer==null){
				return "3";//表示所属父权限不存在
			}
			parentId = parentPer.getId();
			permission.setParentId(parentId);
			
		}
		//判断同一应用下权限CODE是否重复
		if(permissionManager.findByCode(code)!=null){
			return "4";//表示同一应用下CODE重复
		}
		//判断同一父权限下的权限是否重名
//		if(permissionManager.findByName(name, application.getId(), parentId)!=null){
//			return "5";//表示同意父权限下的名称重复
//		}
		permission.setApplicationId(application.getId());
		permission.setCode(code);
		permission.setName(name);
		permission.setUrl(url);
		permission.setDescription(description);
		permission.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		try{
			permissionManager.save(permission);
		}catch(Exception e){
			return "6";//保存数据失败
		}
		// TODO Auto-generated method stub
		return "0";
	}

	

	@Override
	public String getAppList() {
		List<Application> appList = applicationManager.findAllByDf();
		StringBuffer result=new StringBuffer("");
		for(Application app : appList){
			result.append(app.getCode()+":"+app.getName()+"|");
		}
			
		return result.toString();
	}

	@Override
	public String deletePer(String code) {
		Permission permission = permissionManager.findByCode(code);
		try{
			if(permission!=null){
				permission.setDf("1");
				permissionManager.update(permission);
			}
		}catch(Exception e){
			return "1";
		}
		// TODO Auto-generated method stub
		return "0";
	}

}
