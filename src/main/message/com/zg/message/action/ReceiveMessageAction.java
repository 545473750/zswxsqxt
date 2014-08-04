package com.zg.message.action;
import com.zg.message.model.Message;
import com.zg.message.model.ReceiveMessage;
import com.zg.message.service.MessageManager;
import com.zg.message.service.ReceiveMessageManager;
import com.zg.message.query.MessageQuery;
import com.zg.message.query.ReceiveMessageQuery;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

import com.opendata.common.base.BaseStruts2Action;
import com.opendata.common.util.FileUtil;
import com.opendata.common.util.Platform;
import com.opendata.organiz.model.User;
import com.opendata.organiz.service.OrganizationUserRelationManager;
import com.opendata.organiz.service.UserManager;
import com.opendata.organiz.vo.query.OrganizationUserRelationQuery;
import com.opendata.sys.model.Attachment;
import com.opendata.sys.service.AttachmentManager;
import com.opendata.webservice.sys.model.LoginInfo;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;

import cn.com.opendata.attachment.ClientAttachmentApplication;
import cn.org.rapid_framework.beanutils.BeanUtils;
import cn.org.rapid_framework.page.Page;
import cn.org.rapid_framework.web.scope.Flash;
import cn.org.rapid_framework.web.util.HttpUtils;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * describe:消息提醒表管理
 * 
 *
 */
@Namespace("/message")
@Results({
@Result(name="sendList",location="/WEB-INF/pages/message/sendlist.jsp", type = "dispatcher"),
@Result(name="userList",location="/WEB-INF/pages/message/user_list.jsp", type = "dispatcher"),
@Result(name="drafList",location="/WEB-INF/pages/message/draflist.jsp", type = "dispatcher"),
@Result(name="index",location="/WEB-INF/pages/message/index.jsp", type = "dispatcher"),
@Result(name="list",location="/WEB-INF/pages/message/list.jsp", type = "dispatcher"),
@Result(name="add",location="/WEB-INF/pages/message/add.jsp", type = "dispatcher"),
@Result(name="edit",location="/WEB-INF/pages/message/edit.jsp", type = "dispatcher"),
@Result(name="view",location="/WEB-INF/pages/message/receiverview.jsp", type = "dispatcher"),
@Result(name="viewMessage",location="/WEB-INF/pages/message/viewMessage.jsp", type = "dispatcher"),
@Result(name="listAction",location="../message/ReceiveMessage!list.do", type = "redirectAction"),
@Result(name="sendAction",location="../message/ReceiveMessage!draftList.do", type = "redirectAction"),
@Result(name="findAllUser",location="/WEB-INF/pages/message/userList.jsp",type="dispatcher")
})
public class ReceiveMessageAction extends BaseStruts2Action implements Preparable,ModelDriven
{
	private static final String LIST_JSP = "list";
	private static final String ADD_JSP = "add";
	private static final String EDIT_JSP = "edit";
	private static final String VIEW_JSP = "view";
	private static final String DEFAULT_SORT_COLUMNS = "";  //排序列
	protected static final String LIST_ACTION = "listAction";
	@Autowired
	private MessageManager messageManager;
	private ReceiveMessageManager receiveMessageManager;
	private String id=null;//主键id
	private String[] items;//数组Id
	private ReceiveMessage receiveMessage;
	private File[] file;//上传附件
	private String[] fileFileName;//上传附件名称
	private String[] fileContentType;//上传附件类型
	private List<User> userList;
	private UserManager userManager;
	private AttachmentManager attachmentManager;
	private OrganizationUserRelationQuery ouRelationQuery = new OrganizationUserRelationQuery();//部门用户关系query
	private OrganizationUserRelationManager organizationUserRelationManager;//部门用户关系管理
	private ReceiveMessageQuery query =new ReceiveMessageQuery();
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.receiveMessage= new ReceiveMessage();
		} else {
			this.receiveMessage = (ReceiveMessage)this.receiveMessageManager.getById(id);
		}
	}
	
	/*
	 * 根据用户ID查询所有用户名
	 */
	public String findAllUser(){
		List<User> userList=userManager.findAll();
		getRequest().setAttribute("userList", userList);
		return "findAllUser";
	}
	
	/*
	 * 定时更新查看最新消息提醒
	 */
	public void findAllMessageByUserId()throws IOException {
		//String uid = getCurrUser().getUserID();
		com.opendata.login.model.LoginInfo user = getCurrUser();
		if(isNullOrEmptyString(user)){
			String s = "100";
			PrintWriter out = getResponse().getWriter();
			out.print(s);
			out.flush();
			out.close();
		}else{
			long count = receiveMessageManager.findAllManagerByUserId(user.getUserID()
					);
			String s = String.valueOf(count);
			PrintWriter out = getResponse().getWriter();
			out.print(s);
			out.flush();
			out.close();
		}
	}
	
	public Object getModel() {
		return this.receiveMessage;
	}
	
	
	/**
		查询未读列表
	*/
	public String list()
	{
		if(this.getRequest().getParameter("query.state")==null)
		{
		query.setState("0");
		}
		query.setReceiverId(this.getCurrUser().getUserID());//接收人id
		Page page = this.receiveMessageManager.findPage(query);
		
		super.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	
	
	/**	
		标记已读
	*/
	public String edit()
	{
			this.receiveMessage.setState("1");
			this.receiveMessage.setChackTime(new Date());
			this.receiveMessageManager.update(this.receiveMessage);
			return LIST_ACTION;
	}
	
	
	/**	
		删除
	*/
	public String remove()
	{
		for(int i = 0; i < items.length; i++)
		 {
			Hashtable params = HttpUtils.parseQueryString(items[i]);
			String id = (String)params.get("id");
			List<Attachment> attachments = attachmentManager.findAllBy("refId",id);
			if (attachments != null && !attachments.isEmpty()) {
				getRequest().setAttribute("attachments", attachments);
				for (Attachment attachment : attachments) {
					 attachmentManager.delete(attachment);
					 String fileName = attachment.getFilePath();//得到文件路径
						File file = new File(fileName);
						if(file.exists()){
							file.delete();
						}
				}
			}
			this.receiveMessageManager.removeById(id);
		}
		return LIST_ACTION;
	}
	

	
	/**	
		查看
	*/
	public String view()
	{
		List<Attachment> attachments = attachmentManager.findAllBy("refId",this.receiveMessage.getId());
		getRequest().setAttribute("attachments", attachments);
		String rs = this.receiveMessage.getReceiver();
		String username = this.getCurrUser().getUserName();
		if(rs.equals(username)){
			this.receiveMessage.setState("1");
			if(receiveMessage.getChackTime()==null){
				this.receiveMessage.setChackTime(new Date());
			}
			this.receiveMessageManager.update(this.receiveMessage);
		}
		return VIEW_JSP;
	}

	/**	
		查看首页通知公告+个人消息
	*/
	public String viewMessage()
	{
		List<Attachment> attachments = attachmentManager.findAllBy("refId",this.receiveMessage.getId());
		getRequest().setAttribute("attachments", attachments);
		String rs = this.receiveMessage.getReceiver();
		String username = this.getCurrUser().getUserName();
		if(rs.equals(username)){
			this.receiveMessage.setState("1");
			if(receiveMessage.getChackTime()==null){
				this.receiveMessage.setChackTime(new Date());
			}
			this.receiveMessageManager.update(this.receiveMessage);
		}
		return "viewMessage";
	}	
	
	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public UserManager getUserManager() {
		return userManager;
	}

	public void setUserManager(UserManager userManager) {
		this.userManager = userManager;
	}

	public MessageManager getMessageManager() {
		return messageManager;
	}

	public void setMessageManager(MessageManager messageManager) {
		this.messageManager = messageManager;
	}



	public void setItems(String[] items) {
		this.items = items;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AttachmentManager getAttachmentManager() {
		return attachmentManager;
	}

	public void setAttachmentManager(AttachmentManager attachmentManager) {
		this.attachmentManager = attachmentManager;
	}


	public String[] getItems() {
		return items;
	}

	public OrganizationUserRelationQuery getOuRelationQuery() {
		return ouRelationQuery;
	}

	public void setOuRelationQuery(OrganizationUserRelationQuery ouRelationQuery) {
		this.ouRelationQuery = ouRelationQuery;
	}

	public OrganizationUserRelationManager getOrganizationUserRelationManager() {
		return organizationUserRelationManager;
	}

	public void setOrganizationUserRelationManager(
			OrganizationUserRelationManager organizationUserRelationManager) {
		this.organizationUserRelationManager = organizationUserRelationManager;
	}

	public File[] getFile() {
		return file;
	}

	public void setFile(File[] file) {
		this.file = file;
	}

	public String[] getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String[] fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String[] getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String[] fileContentType) {
		this.fileContentType = fileContentType;
	}

	public ReceiveMessageManager getReceiveMessageManager() {
		return receiveMessageManager;
	}

	public void setReceiveMessageManager(ReceiveMessageManager receiveMessageManager) {
		this.receiveMessageManager = receiveMessageManager;
	}

	public ReceiveMessageQuery getQuery() {
		return query;
	}

	public void setQuery(ReceiveMessageQuery query) {
		this.query = query;
	}

	public ReceiveMessage getReceiveMessage() {
		return receiveMessage;
	}

	public void setReceiveMessage(ReceiveMessage receiveMessage) {
		this.receiveMessage = receiveMessage;
	}



}
