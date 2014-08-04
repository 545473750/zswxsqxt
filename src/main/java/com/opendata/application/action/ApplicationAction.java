/*
 * Powered By [rapid-framework]
 * Web Site: http://www.rapid-framework.org.cn
 * Google Code: http://code.google.com/p/rapid-framework/
 * Since 2008 - 2012
 */

package com.opendata.application.action;

import com.opendata.common.io.FileUtils;
import com.opendata.common.io.ZipFileUtils;
import com.opendata.common.log.LogWriter;
import com.opendata.common.sql.SQLExecute;
import com.opendata.common.xml.XMLParser;
import it.sauronsoftware.ftp4j.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;


import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import org.jdom.Element;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import cn.org.rapid_framework.web.scope.Flash;

import com.opensymphony.xwork2.Preparable;
import com.opensymphony.xwork2.ModelDriven;

import java.util.*;

import com.opendata.common.base.*;
import com.opendata.common.dict.DicUtil;
import com.opendata.sys.service.SysvariableManager;
import com.opendata.webservice.application.client.InsParm;
import com.opendata.webservice.application.client.InsService;

import cn.org.rapid_framework.web.util.*;
import cn.org.rapid_framework.page.*;



import com.opendata.application.model.*;
import com.opendata.application.dao.*;
import com.opendata.application.service.*;
import com.opendata.application.vo.query.*;

/**
 * 应用管理Action类 负责和表现层的交互以及调用相关业务逻辑层完成功能
 * @author 付威
 */  
@Namespace("/application")
@Results({
@Result(name="list",location="/WEB-INF/pages/application/Application/list.jsp", type = "dispatcher"),
@Result(name="loadAppList", location="/WEB-INF/pages/component/appList.jsp", type="dispatcher"),
@Result(name="query",location="/WEB-INF/pages/application/Application/query.jsp", type = "dispatcher"),
@Result(name="create",location="/WEB-INF/pages/application/Application/create.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/application/Application/edit.jsp", type = "dispatcher"),
@Result(name="insEdit",location="/WEB-INF/pages/application/Application/ins_edit.jsp", type = "dispatcher"),
@Result(name="show",location="/WEB-INF/pages/application/Application/show.jsp", type = "dispatcher"),
@Result(name="remote_install",location="/WEB-INF/pages/application/Application/remote_install.jsp", type = "dispatcher"),
@Result(name="local_install",location="/WEB-INF/pages/application/Application/local_install.jsp", type = "dispatcher"),
@Result(name="iconList",location="/WEB-INF/pages/commons/grant_icon.jsp", type = "dispatcher"),
@Result(name="install_message",location="/WEB-INF/pages/application/Application/install_message.jsp", type = "dispatcher"),
@Result(name="uninstall_rely_message",location="/WEB-INF/pages/application/Application/uninstall_rely_message.jsp", type = "dispatcher"),
@Result(name="listAction",location="..//application/Application!list.do", type = "redirectAction"),
@Result(name="uninstallAction",location="..//application/Application!unInstall.do?id=${id}", type = "redirectAction"),
@Result(name="isUniqueApplicationAction",location="..//application/Application!isUniqueByApplication.do", type = "redirectAction"),
@Result(name="installAction",location="..//application/Application!install.do", type = "redirectAction")
})
public class ApplicationAction extends BaseStruts2Action implements Preparable,ModelDriven{
	//默认多列排序,example: username desc,createTime asc
	protected static final String DEFAULT_SORT_COLUMNS = "sequence asc"; 
	
	//forward paths
	protected static final String QUERY_JSP = "query";
	protected static final String LIST_JSP= "list";
	protected static final String LOAD_LIST_JSP= "loadAppList";
	protected static final String CREATE_JSP = "create";
	protected static final String EDIT_JSP = "edit";
	protected static final String INS_EDIT_JSP="insEdit";
	protected static final String SHOW_JSP = "show";
	protected static final String REMOTE_INSTALL_JSP ="remote_install";
	protected static final String LOCAL_INSTALL_JSP = "local_install";
	protected static final String ICON_LIST="iconList";
	protected static final String INSTALL_MESSAGE ="install_message";
	//redirect paths,startWith: !
	protected static final String LIST_ACTION = "listAction";
	
	protected static final String INSTALL_ACTION = "installAction";
	
	protected static final String UNINSTALL_ACTION = "uninstallAction";
	
	protected static final String ISUNIQUE_APPLICATION_ACTION ="isUniqueApplicationAction";
	
	protected static final String UNINSTALL_RELY_MESSAGE = "uninstall_rely_message";
	
	private ApplicationManager applicationManager;
	
	private SysvariableManager sysvariableManager;
	
	private PermissionManager permissionManager;
	
	private ApplicationRelyManager applicationRelyManager;
	
	private Map<String,String> appStateMap;
	
	private Map<String,String> appPropertyMap;
	
	private Map<String,String> appTypeMap;
	
	private Application application;
	
	private File localZipFile;
	
	ApplicationQuery queryAQ = new ApplicationQuery();
	String ftpDic ;//FTP目录
	String ftpFile ;//FTP文件名
	String ftpIp ;
	String ftpPort ;
	String ftpUsername ;
	String ftpPassword ;
	
	ApplicationQuery query = new ApplicationQuery();
	java.lang.String id = null;
	private String[] items;
	
	public void prepare() throws Exception {
		if (isNullOrEmptyString(id)) {
			application = new Application();
		} else {
			application = (Application)applicationManager.getById(id);
		}
	}
	
	
	
	public void setSysvariableManager(SysvariableManager sysvariableManager) {
		this.sysvariableManager = sysvariableManager;
	}
	
	
	


	private DicUtil dicUtil;
	public void setDicUtil(DicUtil dicUtil) {
		this.dicUtil = dicUtil;
	}



	public Map<String, String> getAppStateMap() {
		return appStateMap;
	}



	public Map<String, String> getAppTypeMap() {
		return appTypeMap;
	}



	public Map<String, String> getAppPropertyMap() {
		return appPropertyMap;
	}



	public void setAppPropertyMap(Map<String, String> appPropertyMap) {
		this.appPropertyMap = appPropertyMap;
	}



	/** 增加setXXXX()方法,spring就可以通过autowire自动设置对象属性,注意大小写 */
	public void setApplicationManager(ApplicationManager manager) {
		this.applicationManager = manager;
	}	
	
	public Object getModel() {
		return application;
	}
	
	public void setId(java.lang.String val) {
		this.id = val;
	}

	public void setItems(String[] items) {
		this.items = items;
	}
	
	/** 执行搜索 */
	public String list() {
		queryAQ.setDf("0");
		appStateMap = dicUtil.getDicList("APP_STATE");
		appTypeMap = dicUtil.getDicList("APP_TYPE");
		appPropertyMap = new HashMap<String,String>();
		appPropertyMap.put(Application.PROPERTY_BASE, "基础组件");
		appPropertyMap.put(Application.PROPERTY_INSTALL, "安装");
		appPropertyMap.put(Application.PROPERTY_REGISTER, "注册");
		application.setState(queryAQ.getState());
		Page page = this.applicationManager.findPage(queryAQ);
		this.saveCurrentPage(page, queryAQ);
		return LIST_JSP;
	}
	
	/**
	 * 取得应用列表
	 * @return
	 */
	public String loadAppList() {
		query.setDf("0");
		Page page = this.applicationManager.findPage(query);
		this.saveCurrentPage(page,query);
		return LOAD_LIST_JSP;
	}
	
	/** 查看对象*/
	public String show() {
		appStateMap = dicUtil.getDicList("APP_STATE");
		appTypeMap = dicUtil.getDicList("APP_TYPE");
		appPropertyMap = new HashMap<String,String>();
		appPropertyMap.put(Application.PROPERTY_BASE, "基础组件");
		appPropertyMap.put(Application.PROPERTY_INSTALL, "安装");
		appPropertyMap.put(Application.PROPERTY_REGISTER, "注册");
		return SHOW_JSP;
	}
	
	/** 进入新增页面*/
	public String create() {
		appTypeMap = dicUtil.getDicList("APP_TYPE");
		application.setSequence(applicationManager.findSequence());
		return CREATE_JSP;
	}
	
	/** 保存新增对象 */
	public String save() {
		if(!applicationManager.isUniqueByDf(application, "code")){
			Flash.current().success("应用code已经存在");
			return CREATE_JSP;
		}
		application.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		applicationManager.save(application);
		Flash.current().success(CREATED_SUCCESS); //存放在Flash中的数据,在下一次http请求中仍然可以读取数据,error()用于显示错误消息
		return LIST_ACTION;
	}
	
	
	/**保存更新对象*/
	public String update() {
		if(!applicationManager.isUniqueByDf(application, "code")){
			Flash.current().success("应用code已经存在");
			return EDIT_JSP;
		}
		applicationManager.update(this.application);
		Flash.current().success(UPDATE_SUCCESS);
		return LIST_ACTION;
	}
	

	
	/**删除对象*/
	public String delete() {
		for(int i = 0; i < items.length; i++) {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			java.lang.String id = new java.lang.String((String)params.get("id"));
			application = (Application)applicationManager.getById(id);
		
			applicationManager.delete(application);
		}
		Flash.current().success(DELETE_SUCCESS);
		return LIST_ACTION;
	}
	
	
	/**取得所有图标*/
	public String getIcons(){
		//取得图标文件夹下的所有图标在页面显示让用户选择
		String iconPath = "images/tree/icon/s/";
		File iconFile = new File(getRootPath()+iconPath);
		//取得一个此文件夹下的所有文件名
		String[] icons = iconFile.list();
		getRequest().setAttribute("icons", icons);
		getRequest().setAttribute("iconPath",iconPath);
		getRequest().setAttribute("bigIconPath","images/tree/icon/b/");
		return ICON_LIST;
	}

	 /**
	  * 取得项目部署目录
	  * @return
	  */
	  private String getRootPath()
		{
		  String classpath = this.getClass().getResource("").getPath();
			String path = classpath.substring(0,classpath.indexOf("WEB-INF/classes"));
			path = path.replace("%20", " ");
			
			return path;
		}
	
	
	/**进入更新页面*/
	public String edit() {
		appTypeMap = dicUtil.getDicList("APP_TYPE");
		return EDIT_JSP;
	}
	
	
	/**
	 * 去应用远程安装页面
	 * @return
	 */
	public String toRemoteInstall(){
		ftpIp = sysvariableManager.getCodeByName("ftp_ip");
		ftpPort = sysvariableManager.getCodeByName("ftp_port");
		ftpUsername = sysvariableManager.getCodeByName("ftp_username");
		ftpPassword = sysvariableManager.getCodeByName("ftp_password");
		return REMOTE_INSTALL_JSP;
	}

	/**
	 * 去应用本地安装页面
	 * @return
	 */
	public String toLocalInstall(){
		
		
		return LOCAL_INSTALL_JSP;
	}
	
	

	
	/**
	 * 远程应用安装
	 * @return
	 */
	public String remoteInstall(){
		
		File localZip = null;
	    String logs ="";
	    LogWriter logger = null;
	    Properties props = new Properties();
	    //取得配置属性
		try{
			InputStream fis = new FileInputStream(new File(getRootPath() + "\\WEB-INF\\classes\\" +"AppConfig.properties"));
            props.load(fis);
             
		}catch(Exception e){
			Flash.current().error("配置文件不存在，请联系管理员!");
            return REMOTE_INSTALL_JSP;
		}
		//取得日志文件
		try{
			logger = LogWriter.getLogWriter(props.getProperty("system.log.path"));
		}catch(Exception e){
			Flash.current().error("日志文件件不存在，请联系管理员!");
            return REMOTE_INSTALL_JSP;
		}
            logs = "尝试连接FTP服务器：" + ftpIp;
            FTPClient client = new FTPClient();
        //连接FTP
        try{    
            
            client.connect(ftpIp, Integer.parseInt(ftpPort));
            logs += "\n" + ftpIp + "连接成功.";
        }catch(Exception e){
        	Flash.current().error("FTP连接不成功!");
        	logs += "\n" + "出现错误：FTP连接不成功" + e.getMessage() + "\n-----------------------------------------------.";
            logger.log(logs);
            return REMOTE_INSTALL_JSP;
        }    
        try{    
            client.login(ftpUsername, ftpPassword);
            logs += "\n登录成功.";
        }catch(Exception e){
        	Flash.current().error("FTP登录不成功!");
        	logs += "\n" + "出现错误：FTP登录不成功" + e.getMessage() + "\n-----------------------------------------------.";
            logger.log(logs);
            return REMOTE_INSTALL_JSP;
        }    
            logs += "\n开始从服务器获取数据..";
            
        try{    
            client.changeDirectory(ftpDic);
        }catch(Exception e){
        	Flash.current().error("FTP目录不存在!");
        	logs += "\n" + "出现错误：FTP目录不存在!" + e.getMessage() + "\n-----------------------------------------------.";
            logger.log(logs);
            return REMOTE_INSTALL_JSP;
        }   
        try{
            logs += "\n" + "成功切换至" + ftpDic + "目录..";
            localZip = new File(props.getProperty("system.ftp.localdic") + ftpFile);
            client.download(ftpFile, localZip);
            logs += "\n" + "安装包下载成功.";
	   }catch(Exception e){
		   Flash.current().error("FTP目录不存在!");
		   logs += "\n" + "出现错误：FTP目录不存在!" + e.getMessage() + "\n-----------------------------------------------.";
           logger.log(logs);
           return REMOTE_INSTALL_JSP;
	   }
            if (!localZip.exists()) {
                logs += "\n" + "压缩包不存在,请检查.";
                Flash.current().error("压缩包不存在,请检查..");
                logs += "\n" + "出现错误：FTP目录不存在!"  + "\n-----------------------------------------------.";
                logger.log(logs);
                return REMOTE_INSTALL_JSP;
            }
        try{
            //解压缩文件 判断必须文件是否存在
            String decompressionPath = decompression(localZip, logs, logger, props);
            //判断强依赖应用是否存在 如果不存在 则不能安装  如果是弱依赖应用不存在 则弹出对话框提示
            int relyResult = rely(decompressionPath);
            
            
            //根据依赖关系返回结果 转到相应页面
            //如果结果为1 则返回到安装页面
            if(relyResult==1){
            	return REMOTE_INSTALL_JSP;
            }else if(relyResult==2){
            	getRequest().getSession().setAttribute("decompressionPath", decompressionPath);
                getRequest().getSession().setAttribute("installPath", REMOTE_INSTALL_JSP);
            	return INSTALL_MESSAGE;
            }
            getRequest().getSession().setAttribute("decompressionPath", decompressionPath);
            getRequest().getSession().setAttribute("installPath", REMOTE_INSTALL_JSP);
		}catch(Exception e){
			logs += "\n" + "出现错误：" + e.getMessage() + "\n-----------------------------------------------.";
            logger.log(logs);
            Flash.current().error("安装出现问题请检查:"+e.getMessage());
            return REMOTE_INSTALL_JSP;
		}
		Flash.current().success("安装成功!请重启系统!");
		return INSTALL_ACTION;
	}
    
	/**
	 * 本地安装
	 * @return
	 */
	@Transactional
    public String localInstall(){
    	
    	Properties props = new Properties();
		
		String logs ="";
		LogWriter logger =null;
		if (!localZipFile.exists()) {
	         Flash.current().success("压缩包不存在,请检查..");
	         return LOCAL_INSTALL_JSP;
	    }
		try{
			InputStream fis = new FileInputStream(new File(getRootPath() + "\\WEB-INF\\classes\\" +"AppConfig.properties"));
            props.load(fis);
             
		}catch(Exception e){
			Flash.current().error("配置文件不存在，请联系管理员!");
            return LOCAL_INSTALL_JSP;
		}   
		try{
			logger = LogWriter.getLogWriter(props.getProperty("system.log.path"));
		}catch(Exception e){
			Flash.current().error("日志文件件不存在，请联系管理员!");
            return LOCAL_INSTALL_JSP;
		}
		try{	
	        
			String decompressionPath = decompression(localZipFile, logs, logger, props);
			//判断强依赖应用是否存在 如果不存在 则不能安装  如果是弱依赖应用不存在 则弹出对话框提示
			int relyResult = rely(decompressionPath);
			//根据依赖关系返回结果 转到相应页面
            //如果结果为1 则返回到安装页面
            if(relyResult==1){
            	return LOCAL_INSTALL_JSP;
            }else if(relyResult==2){
            	getRequest().getSession().setAttribute("decompressionPath", decompressionPath);
                getRequest().getSession().setAttribute("installPath", LOCAL_INSTALL_JSP);
                getRequest().setAttribute("resultAction", "isUniqueByApplication");
            	return INSTALL_MESSAGE;
            }
			
			getRequest().getSession().setAttribute("decompressionPath", decompressionPath);
			getRequest().getSession().setAttribute("installPath", LOCAL_INSTALL_JSP);
		}catch(Exception e){
			logs += "\n" + "出现错误：" + e.getMessage() + "\n------------------------------------------------.";
            Flash.current().error("安装出现问题请检查:"+e.getMessage());
            return LOCAL_INSTALL_JSP;
		}
		
        return ISUNIQUE_APPLICATION_ACTION;
    }
    
	/**
	 * 解压缩确定必须文件是不是存在，返回最后解压缩文件路径
	 */
    @Transactional
	private String decompression(File localZip, String logs, LogWriter logger,
			Properties props) throws Exception, IOException, SQLException,
			ClassNotFoundException {
		long currTime = System.currentTimeMillis();
		
		
		String decompressionPath = props.getProperty("system.ftp.localdic") + currTime;
        
		logs += "\n" + "开始解压文件.";
		ZipFileUtils.unZip(localZip, decompressionPath);//解压
		logs += "\n" + "解压完成,开始安装.";
		File installFile = new File(decompressionPath + "/install.xml");
		if(!installFile.exists()){
			throw new Exception("安装文件install.xml文件不存在!");
		}
		
		Element rootElement = XMLParser.getRootElement(decompressionPath + "/install.xml");
		//备份设置文件setting.xml 备份的文件名为应用编号_setting.xml
		//判断setting.xml文件是否存在
		String appCode =null;
        try{
		   appCode = rootElement.getAttributeValue("code");//应用编号	
        }catch(Exception e){
        	throw new Exception("应用编码不存在!");
        }
		
		
        File uninstallFile = new File(props.getProperty("system.ftp.localdic") + currTime  + "\\uninstall.xml");
        if(!uninstallFile.exists()){
			throw new Exception("卸载文件uninstall.xml文件不存在!");
		}
        
        return decompressionPath;
		
	}
    
    /**
     * 安装前判断强依赖的应用是否已存在，弱依赖关系应用不存在弹出对话框确认
     * 返回结果 如果依赖的应用都存在 则返回0 如果有强依赖关系的应用不存在 则返回1 如果有弱依赖关系的应用不存在 则返回2
     */
    private int rely(String decompressionPath){
    	try{
    		String message = "";
    		Element rootElement = XMLParser.getRootElement(decompressionPath + "/install.xml");
    		Element strongRelys = rootElement.getChild("StrongRelys");
    		if(strongRelys!=null){
    			List<Element> strongRelyList = strongRelys.getChildren("Rely");
    			for(Element element : strongRelyList){
    				String applicationCode = element.getAttributeValue("applicationCode");
    				String versions = element.getAttributeValue("version");
    				if(applicationCode == null){
    					throw new Exception("安装文件的强依赖关系配置错误!");
    				}

    				//取得依赖的应用不存在的所有应用提示
    				message = getRelyMessage(message, applicationCode, versions);

    				if(applicationManager.findByCode(applicationCode)==null){
    					Flash.current().error("应用所依赖组件"+applicationCode+"不存在,停止安装!");
    					return 1;
    				}
    			}
    			if(!"".equals(message)){
	    			Flash.current().error("安装应用有强依赖关系的"+message+"不存在!");
					return 1;
    			}
    		}
    		
    		Element weakRelys = rootElement.getChild("WeakRelys");
    		
    		if(weakRelys!=null){
    			List<Element> weakRelyList = weakRelys.getChildren("Rely");
    			for(Element element : weakRelyList){
    				String applicationCode = element.getAttributeValue("applicationCode");
    				String versions = element.getAttributeValue("version");
    				if(applicationCode==null){
    					throw new Exception("安装文件的弱依赖关系配置错误!");
    				}
    				//取得依赖的应用不存在的所有应用提示
    				message = getRelyMessage(message, applicationCode, versions);
    			}
    		}
    		if(!"".equals(message)){
    			getRequest().setAttribute("message", "应用所依赖组件"+message+"不存在!,是否继续安装?");
    			return 2;
    		}
    		
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		Flash.current().error(e.getMessage());
			return 1;
    	}
    	return 0;
    }
    

    /**
     * 判断安装的应用是否存在 如果已存在提示是否覆盖更新
     * 如果应用不存在返回true 否则返回false
     */
    public String isUniqueByApplication(){
    	String installPath="";//安装页面返回地址 用来记录是本地安装还是远程安装
    	try{
    		installPath = getRequest().getSession().getAttribute("installPath").toString();
	    	//安装文件解压缩地址
	    	String decompressionPath = getRequest().getSession().getAttribute("decompressionPath").toString();
    		Element rootElement = XMLParser.getRootElement(decompressionPath + "/install.xml");
    		String appCode = rootElement.getAttributeValue("code");//应用编号	
    		if(applicationManager.findByCode(appCode)!=null){
    			getRequest().setAttribute("message", "此应用已安装!是否覆盖继续安装?");
    			getRequest().setAttribute("resultAction", "install");//记录选择继续安装 调用action方法
    			return INSTALL_MESSAGE;
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    		Flash.current().error(e.getMessage());
    		return installPath;
    	}
    	return INSTALL_ACTION;
    }



    /**
     * 取得依赖关系是否存在的提示
     * @param message 提示
     * @param applicationCode 应用编号
     * @param versions 版本
     * @return 如果此应用版本不存在 则提示累加
     */
	private String getRelyMessage(String message, String applicationCode,
			String versions) {
		//如果依赖关系存在版本 则查询这些版本的应用是否存在
		if(versions!=null&&!"".equals(versions)){
			String[] strVers = versions.split(",");
			boolean result = false;//用来记录此应用的相关版本是否存在
			String strResult = "";//用来记录此应用相关版本的结果
			for(String ver : strVers){
				if(applicationManager.findByCodeVersion(applicationCode,ver)!=null){
					result = true;
					break;
				}else{
					strResult += applicationCode+"["+ver+"],";//应用code+版本
				}
			}
			//如果应用要依赖的此应用的所有版本都不存在，则把此应用的所有版本加入message提示用户
			if(!result){
				message += strResult;
			}
		}else{
			if(applicationManager.findByCode(applicationCode)==null){
				message += applicationCode+",";
			}
		}
		return message;
	}
    
    
	/**
	 * 安装应用 
	 */
    @Transactional
    public String install(){
    	
    	String logs ="";
	    LogWriter logger = null;
	    Properties props = new Properties();
	    String installPath="";//安装页面返回地址 用来记录是本地安装还是远程安装
	    
	    try{
	    	InputStream fis = new FileInputStream(new File(getRootPath() + "\\WEB-INF\\classes\\" +"AppConfig.properties"));
            props.load(fis);
            
            logger = LogWriter.getLogWriter(props.getProperty("system.log.path"));
	    	
	    	String time = new java.text.SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
	    	
	    	installPath = getRequest().getSession().getAttribute("installPath").toString();
	    	//安装文件解压缩地址
	    	String decompressionPath = getRequest().getSession().getAttribute("decompressionPath").toString();
	    	
	    	
	    	Element rootElement = XMLParser.getRootElement(decompressionPath + "/install.xml");
			//备份设置文件setting.xml 备份的文件名为应用编号_setting.xml
			//判断setting.xml文件是否存在
			String appCode =null;
	        try{
			   appCode = rootElement.getAttributeValue("code");//应用编号	
	        }catch(Exception e){
	        	
	        	throw new Exception("应用编码不存在!");
	        }
	    	
	    	
    	
	    	File backupUninstall = new File(getRootPath()+props.getProperty("system.uninstall.path")+"/"+appCode+"_uninstall.xml");
			
			
	        File uninstallFile = new File(decompressionPath  + "\\uninstall.xml");
	        if(!uninstallFile.exists()){
				throw new Exception("卸载文件uninstall.xml文件不存在!");
			}
			FileUtils.copyFile(uninstallFile, backupUninstall);	//备份卸载文件 以便卸载用	
	
			Element class_ = rootElement.getChild("Class");
			Element libs = rootElement.getChild("Libs");
			Element pages = rootElement.getChild("Pages");
			Element sql = rootElement.getChild("SQL");
			Element permissions = rootElement.getChild("Permissions");
			
			logs = backup(logs,time);
			
			logs += "\n" + "正在安装后台文件..";
			try{
				if (class_ != null) {//后台类与配置文件
				    String class_path = class_.getAttributeValue("path");
				    String class_target = getRootPath()+class_.getAttributeValue("target");
				    FileUtils.copyDirectory(new File(decompressionPath + class_path), new File(class_target));
				}
			}catch(Exception e){
				throw new Exception("后台信息不对!");
			}
			logs += "\n" + "正在安装类库.";
			try{
				if (libs != null) {//类库
				    String libs_path = libs.getAttributeValue("path");
				    String libs_target = getRootPath()+libs.getAttributeValue("target");
				    FileUtils.copyDirectory(new File(decompressionPath + libs_path), new File(libs_target));
				}
			}catch(Exception e){
				throw new Exception("类库信息不对!");
			}
			logs += "\n" + "正在安装页面文件..";
			try{
				copyPages(props, decompressionPath, pages);//复制页面信息
			}catch(Exception e){
				throw new Exception("页面信息不对!");
			}
			logs += "\n" + "正在初始化数据库..";
			if (sql != null) {//数据库生成与数据初始化
				try{
				    logs = initSql(logs, props, decompressionPath, sql);
				}catch(Exception e){
					throw new Exception("初始化数据不对!");
				}
			}
			Application app = null;
	        try{
	        	//将应用注册到服务器上
				app = saveApplication(appCode, rootElement);
	        }catch(Exception e){
	        	e.printStackTrace();
	        	throw new Exception("应用信息不全!");
	        }
			if (permissions != null) {//后台类与配置文件
				try{
				    List<Element> perms = permissions.getChildren("Permission");
		
				    for (Element permission : perms) {
				    	//保存应用访问入口信息
				        savePermission(permission,app);
				    }    
			    }catch(Exception e){
			    	applicationManager.delete(app);
			    	throw new Exception("应用访问入口信息不全!");
			    }
			   
			}
			try{
				//存储应用之间依赖关系
				//强依赖关系
	    		Element strongRelys = rootElement.getChild("StrongRelys");
	    		if(strongRelys!=null){
	    			List<Element> strongRelyList = strongRelys.getChildren("Rely");
	    			for(Element element : strongRelyList){
	    				String applicationCode = element.getAttributeValue("applicationCode");
	    				saveApplicationRely(app, applicationCode,"0");
	    			}
	    		}
	    		//保存弱依赖关系
	    		Element weakRelys = rootElement.getChild("WeakRelys");
	    		if(weakRelys!=null){
	    			List<Element> weakRelyList = weakRelys.getChildren("Rely");
	    			for(Element element : weakRelyList){
	    				String applicationCode = element.getAttributeValue("applicationCode");
	    				saveApplicationRely(app, applicationCode,"1");
	    			}
	    		}
			}catch(Exception e){
				throw new Exception("依赖关系信息不全!");
			}
	    }catch(Exception e){
	    	Flash.current().error(e.getMessage());
	    	return installPath;
	    }
		logs += "安装顺利完成\n-----------------------------------------------.";
		logger.log(logs);
		Flash.current().success("安装成功!请重启系统!");
		return LIST_ACTION;
    }



    /**
     * 保存应用依赖关系
     * @param app 依赖应用
     * @param applicationCode  被依赖应用编号
     * @param type 类别 标识是强依赖还是弱依赖
     */
	private void saveApplicationRely(Application app, String applicationCode,String type) {
		Application relyApplication = applicationManager.findByCode(applicationCode);
		if(relyApplication!=null){
			ApplicationRely applicationRely = new ApplicationRely();
			applicationRely.setApplicationId(app.getId());
			applicationRely.setDf("0");
			applicationRely.setRelyApplicationId(relyApplication.getId());
			applicationRely.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
			applicationRely.setType(type);
			applicationRelyManager.save(applicationRely);
		}
	}


	/**
	 * 复制页面文件
	 */
	private void copyPages(Properties props, String decompressionPath, Element pages)
			throws IOException {
		if (pages != null) {//页面文件
		    List<Element> pageDirs = pages.getChildren("PageDir");
		    for (Element pageDir : pageDirs) {
		        String page_path = pageDir.getAttributeValue("path");
		        String page_target = getRootPath()+pageDir.getAttributeValue("target");
		        FileUtils.copyDirectory(new File(decompressionPath + page_path), new File(page_target));
		    }
		}
	}


   
	/**
	 * 初始化数据库信息
	 */
	private String initSql(String logs, Properties props, String decompressionPath,
			Element sql) {
		String db = sql.getAttributeValue("DB");
		String connURL = sql.getAttributeValue("connURL");
		String username = sql.getAttributeValue("username");
		String password = sql.getAttributeValue("password");
		String dbName = sql.getAttributeValue("dbName");

		Element createSQL = sql.getChild("CreateSQL");
		Element initSQL = sql.getChild("InitSQL");

		if (createSQL != null) {
		    String sql_path = createSQL.getAttributeValue("path");
		    //执行生成数据库
		    if (sql_path != null && !sql_path.equals("")) {
		    	try{
		    		if(db!=null){
		    			SQLExecute.executeSQLFile(decompressionPath + sql_path, SQLExecute.getConnection(db, connURL, dbName, username, password));
		    		}else{
		    			SQLExecute.executeSQLFile(decompressionPath + sql_path, SessionFactoryUtils.getDataSource(((ApplicationDao)applicationManager.getEntityDao()).getSessionFactory()).getConnection());
		    		}
		    	}catch(Exception ex){
		    		logs += "\n" + "初始化数据库失败，表已经存在..";
		    	}
		    }
		}

		if (initSQL != null) {
		    String sql_path = initSQL.getAttributeValue("path");
		    //执行生成数据库
		    if (sql_path != null && !sql_path.equals("")) {
		    	try{
		    		SQLExecute.executeSQLFile(decompressionPath + sql_path, SQLExecute.getConnection(db, connURL, dbName, username, password));
		    	}catch(Exception ex){
		    		logs += "\n" + "初始化数据库失败，数据已存在..";
		    	}
		    }
		}
		return logs;
	}


    /**
     * 保存应用信息到数据库
     * @param appCode 应用编码
     * @param rootElement
     * @return
     */
	private Application saveApplication(String appCode,Element rootElement) {
		String appName = rootElement.getAttributeValue("name");//应用名称
		String appDesc = rootElement.getAttributeValue("desc");//应用描述
		String version = rootElement.getAttributeValue("version");//应用版本
		String appIcon = rootElement.getAttributeValue("icon");//应用小图标
		String appBigIcon = rootElement.getAttributeValue("bigIcon");//应用大图标	
		String initializePage = rootElement.getAttributeValue("initializePage");//应用初始化页面地址
		String configurationPage = rootElement.getAttributeValue("configurationPage");//配置管理页面地址	
		Application app = applicationManager.findByCode(appCode);
		if(app ==null){
			app = new Application();
		}else{
			//删除此应用下的应用访问入口
			permissionManager.deleteByApplication(app);
		}
		app.setCode(appCode);
		app.setBigIcon(appBigIcon);
		app.setVersion(version);
		app.setConfigurationPage(configurationPage);
		app.setDescription(appDesc);
		app.setIcon(appIcon);
		app.setInitializePage(initializePage);
		app.setName(appName);
		app.setProperty(Application.PROPERTY_INSTALL);
		app.setSequence(applicationManager.findAllByDf().size()+1);
		app.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		
		applicationManager.save(app);
		return app;
	}


	

	/**
	 * 保存应用访问入口到数据库
	 * @param permission 应用访问入口Element
	 * @param app 所属应用
	 * @throws Exception
	 */
	private void savePermission(Element permission,Application app) throws Exception{
		String permission_code = permission.getAttributeValue("code");
		String permission_name = permission.getAttributeValue("name");
		String permission_url = permission.getAttributeValue("url");
		String permission_parent = permission.getAttributeValue("parent");
		String permission_desc = permission.getAttributeValue("desc");
		String permission_icon = permission.getAttributeValue("icon");
		String permission_type = permission.getAttributeValue("type");
		String permission_bigicon = permission.getAttributeValue("bigIcon");
		String permission_sequence = permission.getAttributeValue("sequence");
		if(permission_type==null){
			permission_type =Permission.TYPE_DIC;
		}
		

		Permission per = new Permission();
		per.setApplicationId(app.getId());
		per.setBigIcon(permission_bigicon);
		per.setType(permission_type);
		per.setCode(permission_code);
		per.setDescription(permission_desc);
		per.setIcon(permission_icon);
		per.setName(permission_name);
		per.setSequence(Integer.parseInt(permission_sequence));
		per.setTs(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		if(permission_parent!=null&&!"".equals(permission_parent)){
			Permission parentPer = permissionManager.findByCode(permission_parent);
			if(parentPer!=null){
				per.setParentId(parentPer.getId());
			}
		}
		per.setUrl(permission_url);
		//同一应用下的目录和主功能入口编码不能重复
		//同一主功能入口下的子功能入口编码不能重复
		if(!Permission.TYPE_FUNCTION.equals(permission_type)){
			if(!permissionManager.isUniqueByDf(per, "applicationId,code")){
				throw new Exception("同一应用下"+Permission.ALIAS_CODE+permission_code+"已存在!");
			}
		}else{
			if(!permissionManager.isUniqueByDf(per, "parentId,code")){
				throw new Exception("同一主功能入口下的"+Permission.ALIAS_CODE+permission_code+"已存在!");
			}
		}
		permissionManager.save(per);
	}



	/**
	 * 备份源程序
	 * @param logs
	 * @param time
	 * @return
	 * @throws IOException
	 */
	private String backup(String logs,String time) throws IOException {
		logs += "\n" + "对原有程序进行备份..";
        File disFile = new File(getRootPath()  + "\\..\\..\\backups\\UE_" + time);
        FileUtils.copyDirectory(new File(getRootPath()), disFile);
        logs += "\n" + "备份成功,文件存放在：" + disFile.getAbsolutePath() + "..";
		return logs;
	}
	
	/**
	 * 卸载前先判断依赖关系 如果被別的应用强依赖 则不能卸载 如果有被别的应用弱依赖则弹出对话框
	 */
	public String unInstallRely(){
		//判断是否为安装应用，则先判断是否有被强依赖或被弱依赖
		if(Application.PROPERTY_INSTALL.equals(application.getProperty())){
			List<ApplicationRely> strongRelyList = applicationRelyManager.findRelyApplicationByType(application.getId(), "0");
			if(strongRelyList!=null){
				String strongRelyApplication = "";
				for(ApplicationRely appRely : strongRelyList){
					strongRelyApplication += appRely.getApplication().getName()+"、";
				}
				if(!"".equals(strongRelyApplication)){
					Flash.current().error("要卸载的应用被"+strongRelyApplication+"等应用强依赖!不能卸载");
			    	return LIST_ACTION;
				}
			}
			List<ApplicationRely> weakRelyList = applicationRelyManager.findRelyApplicationByType(application.getId(), "1");
			if(weakRelyList!=null){
				String weakRelyApplication ="";
				for(ApplicationRely appRely : weakRelyList){
					weakRelyApplication += appRely.getApplication().getName()+"、";
				}
				if(!"".equals(weakRelyApplication)){
					getRequest().setAttribute("message", "此应用被"+weakRelyApplication+"等应用弱依赖，确定要卸载吗？");
					return UNINSTALL_RELY_MESSAGE;
				}
			}
		}
		return UNINSTALL_ACTION;
	}
	
	/**
	 * 卸载应用
	 * @return
	 */
	@Transactional
	public String unInstall(){
		String logs ="";
		LogWriter logger =null;
	    String time = new java.text.SimpleDateFormat("yyyyMMdd_hhmmss").format(new Date());
	    Properties props = new Properties();	
			
			try{
				InputStream fis = new FileInputStream(new File(getRootPath() + "\\WEB-INF\\classes\\" +"AppConfig.properties"));
		        
		        props.load(fis);
		        logger = LogWriter.getLogWriter(props.getProperty("system.log.path"));
		    }catch(Exception e){
		    	Flash.current().error("配置文件不存在，请联系管理员!");
		    	return LIST_ACTION;
			}   
		    try{
		      //判断是否为安装应用，如果是安装文件 则先删除文件 再删除相关应用数据
				if(Application.PROPERTY_INSTALL.equals(application.getProperty())){
					
			        logs += "\n" + "对应用"+application.getCode()+"卸载开始..";
			        //卸载前先备份
			        logs = backup(logs,time);
			        
			        File uninstallFile = new File(getRootPath()+props.getProperty("system.uninstall.path")+"/"+application.getCode()+"_uninstall.xml");
			        if(!uninstallFile.exists()){
						Flash.current().error("卸载文件uninstall.xml文件不存在，请联系管理员!");
				    	return LIST_ACTION;
					}
			        //取得卸载文件xml
					Element rootElement = XMLParser.getRootElement(getRootPath()+props.getProperty("system.uninstall.path")+"/"+application.getCode()+"_uninstall.xml");
					//取得要删除的class
					Element class_ = rootElement.getChild("Class");
					logs += "\n 开始删除CLASS文件..";
					if(class_ != null){
						List<Element> classDirs = class_.getChildren("ClassDir");
						for(Element classDir : classDirs){
							String classPath = classDir.getAttributeValue("path");
					        FileUtils.delete(getRootPath()+"\\WEB-INF\\classes\\"+classPath);
						}
					}
					logs += "\n 删除CLASS文件完成..";
					logs += "\n 开始删除pages文件..";
					//取得要删除的pages
					Element pages = rootElement.getChild("Pages");
					if (pages != null) {//页面文件
					    List<Element> pageDirs = pages.getChildren("PageDir");
					    for (Element pageDir : pageDirs) {
					        String pagePath = pageDir.getAttributeValue("path");
					        FileUtils.delete(getRootPath()+pagePath);
					    }
					} 
					logs += "\n 删除pages文件完成..";
					//删除配置文件
					FileUtils.delete(getRootPath()+props.getProperty("system.uninstall.path")+"/"+application.getCode()+"_uninstall.xml");
				}	
				//删除完文件后删除此应用的数据
				logs += "\n 删除应用数据开始..";
				applicationManager.delete(application);
				
				logs += "\n 删除应用数据完成..";
				logs += "卸载顺利完成\n-----------------------------------------------.";
			
			logger.log(logs);
				
			}catch(Exception e){
				Flash.current().error("卸载出现问题请检查."+e.getMessage());
				e.printStackTrace();
				//判断是否为安装应用，如果是安装文件 则先删除文件 再删除相关应用数据
				if(Application.PROPERTY_INSTALL.equals(application.getProperty())){
					//恢复文件
					try{
						File disFile = new File(getRootPath()  + "\\..\\..\\backups\\UE_" + time);
				        FileUtils.copyDirectory( disFile,new File(getRootPath()));
					}catch(Exception ex){
						ex.printStackTrace();
					}
				}
				return LIST_ACTION;
			}
		
		Flash.current().success("卸载成功!");
		return LIST_ACTION;
	}
	
	/**保存更新对象*/
	public String insUpdate() {
		String appCode = getRequest().getParameter("appCode");//应用编号
		String ftpDic = getRequest().getParameter("ftpDic");//FTP目录
		String ftpFile = getRequest().getParameter("ftpFile");//FTP文件名
		//设置调用应用注册接口的值 包括FTP地址 用户名 密码等
		InsParm insParm = new InsParm();
		insParm.setAppCode(appCode);
		insParm.setFtpDic(ftpDic);
		insParm.setFtpFile(ftpFile);
		insParm.setFtpIP(sysvariableManager.getCodeByName("ftp_ip"));
		insParm.setFtpPort(sysvariableManager.getCodeByName("ftp_port"));
		insParm.setUsername(sysvariableManager.getCodeByName("ftp_username"));
		insParm.setPassword(sysvariableManager.getCodeByName("ftp_password"));
		
		try{
			//调用应用注册接口
			WebApplicationContext  wac = WebApplicationContextUtils.getRequiredWebApplicationContext(getSession().getServletContext()); 
			InsService insService = (InsService)wac.getBean("insService");
			insService.update(insParm);
		}catch(Exception e){
			e.printStackTrace();
		}
		return LIST_ACTION;
	}



	public void setPermissionManager(PermissionManager permissionManager) {
		this.permissionManager = permissionManager;
	}



	public String getFtpDic() {
		return ftpDic;
	}



	public void setFtpDic(String ftpDic) {
		this.ftpDic = ftpDic;
	}



	public String getFtpFile() {
		return ftpFile;
	}



	public void setFtpFile(String ftpFile) {
		this.ftpFile = ftpFile;
	}



	public String getFtpIp() {
		return ftpIp;
	}



	public void setFtpIp(String ftpIp) {
		this.ftpIp = ftpIp;
	}





	public String getFtpPort() {
		return ftpPort;
	}



	public void setFtpPort(String ftpPort) {
		this.ftpPort = ftpPort;
	}



	public String getFtpUsername() {
		return ftpUsername;
	}



	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}



	public String getFtpPassword() {
		return ftpPassword;
	}



	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}



	public File getLocalZipFile() {
		return localZipFile;
	}



	public void setLocalZipFile(File localZipFile) {
		this.localZipFile = localZipFile;
	}



	public void setApplicationRelyManager(
			ApplicationRelyManager applicationRelyManager) {
		this.applicationRelyManager = applicationRelyManager;
	}



	public ApplicationQuery getQuery() {
		return query;
	}



	public void setQuery(ApplicationQuery query) {
		this.query = query;
	}
	
}
