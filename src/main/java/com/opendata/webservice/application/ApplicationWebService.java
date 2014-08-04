package com.opendata.webservice.application;

import javax.jws.WebService;


@WebService
public interface ApplicationWebService {
	
	/**
	 * 添加应用
	 * @param code 应用编号
	 * @param name 应用名称
	 * @param description 应用描述
	 * @return 返回0表示添加成功 1表示标识或名称为空 2表示code重复 3表示添加失败
	 */
	public String insertApp(String code,String name,String description,String setUrl);
	
	
	/**
	 * 添加权限
	 * @param appCode 应用编号
	 * @param code 权限编号
	 * @param name 权限名称
	 * @param url 权限地址
	 * @param parentCode 上级权限CODE 如果没有上级则为空就行
	 * @param description 权限描述
	 * @return 0表示添加成功  1表示应用标识或标识、名称为空 2表示权限所属应用不存在 3表示父权限不存在 4表示同一应用下code重复 5表示同一父权限下的名称重复 6表示保存数据失败
	 */
	public String insertPer(String appCode,String code,String name,String url,String parentCode,String description);
	
	
	
	/**
	 * 删除权限
	 * @param code 权限编码
	 * @return 0表示删除成功 1表示删除失败
	 */
	public String deletePer(String code);
	
	
	/**
	 * 取得所有应用 包括应用名称和CODE
	 * @return 返回结果为拼成的字符串 code:name|
	 */ 
	public String getAppList();

}
