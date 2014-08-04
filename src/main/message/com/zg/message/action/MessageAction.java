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
@Result(name="view",location="/WEB-INF/pages/message/view.jsp", type = "dispatcher"),
@Result(name="listAction",location="../message/Message!list.do", type = "redirectAction"),
@Result(name="sendAction",location="../message/Message!sendList.do", type = "redirectAction"),
@Result(name="draftAction",location="../message/Message!draftList.do", type = "redirectAction"),
@Result(name="findAllUser",location="/WEB-INF/pages/message/userList.jsp",type="dispatcher")
})
public class MessageAction extends BaseStruts2Action implements Preparable,ModelDriven
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
	private Message message;
	private File[] file;//上传附件
	private String[] fileFileName;//上传附件名称
	private String[] fileContentType;//上传附件类型
	private List<User> userList;
	private UserManager userManager;
	private AttachmentManager attachmentManager;
	private OrganizationUserRelationQuery ouRelationQuery = new OrganizationUserRelationQuery();//部门用户关系query
	private OrganizationUserRelationManager organizationUserRelationManager;//部门用户关系管理
	private MessageQuery query=new MessageQuery();
	private ReceiveMessageQuery reQuery=new ReceiveMessageQuery();
	
	public void prepare() throws Exception {
		this.id=getRequest().getParameter("id");
		this.items=getRequest().getParameterValues("items");
		saveParameters();
		if (isNullOrEmptyString(id)) {
			this.message= new Message();
		} else {
			this.message = (Message)this.messageManager.getById(id);
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
	public void findAllMessageByUserId() {
			long count = messageManager.findAllManagerByUserId(this
					.getCurrUser().getUserID());
			this.getRequest().setAttribute("count", count);
	}
	
	public Object getModel() {
		return this.message;
	}
	
	public MessageQuery getQuery() {
		return query;
	}

	public void setQuery(MessageQuery query) {
		this.query = query;
	}
	
	/**
		查询未读列表
	*/
	public String list()
	{
		BeanUtils.copyProperties(reQuery, query);
		if(this.getRequest().getParameter("query.state")==null)
		{
		query.setState("0");
		}
		query.setStatus("1");
		query.setReceiverId(this.getCurrUser().getUserID());//接收人id
		Page page = this.messageManager.findPage(query);
		
		super.saveCurrentPage(page,query);
		return LIST_JSP;
	}
	
	/**
		查询发送列表
	*/
	public String sendList()
	{
		query.setStatus("1");//已发送
		query.setSponsorId(this.getCurrUser().getUserID());//发送人id
		Page page = this.messageManager.findPage(query);
		super.saveCurrentPage(page,query);
		return "sendList";
	}
	
	/**
		查询草稿箱列表
	*/
	public String draftList()
	{
		query.setStatus("0");//未发送
		query.setSponsorId(this.getCurrUser().getUserID());//发送人id
		Page page = this.messageManager.findPage(query);
		super.saveCurrentPage(page,query);
		return "drafList";
	}
	
	/**
		新增
	*/
	public String add()
	{
		return ADD_JSP;
	}
	
	public String index()
	{
		return "index";
	}
		
	/**	
		保存新增发送
	*/
	public String saveAdd()
	{
		String title = getRequest().getParameter("title");
		String content = getRequest().getParameter("content");
		String receids = getRequest().getParameter("receids");
		String[] rids = receids.split(",");
		for (String rid : rids) {
			User user = userManager.getById(rid);
			Message mess = new Message();
			mess.setTitle(title);
			mess.setContent(content);
			mess.setReceiverId(user.getId());
			mess.setReceiver(user.getUsername());
			mess.setStatus("1");//已发送
			mess.setTime(new Date());
			mess.setState("0");//消息状态    0为未查看
			mess.setTs(new Date());
			mess.setSponsor(this.getCurrUser().getUserName());
			mess.setSponsorId(this.getCurrUser().getUserID());
			this.messageManager.save(mess);
			if(file!=null){
				uploadFile(mess.getId());//上传附件
			}
			ReceiveMessage receicemess = new ReceiveMessage();
			receicemess.setReceiverId(user.getId());
			receicemess.setReceiver(user.getUsername());
			receicemess.setTitle(title);
			receicemess.setContent(content);
			receicemess.setTime(new Date());//发生时间
			receicemess.setState("0");//消息状态    0为未查看
			receicemess.setTs(new Date());
			receicemess.setSponsor(this.getCurrUser().getUserName());
			receicemess.setSponsorId(this.getCurrUser().getUserID());
			this.receiveMessageManager.save(receicemess);
			if(file!=null){
				uploadFile(receicemess.getId());//上传附件
			}
		}
			return "sendAction";
	}
	
	/**	
		保存新增草稿
	 */
	public String saveadd()
	{
		String title = getRequest().getParameter("title");
		String content = getRequest().getParameter("content");
		String receids = getRequest().getParameter("receids");
		String[] rids = receids.split(",");
		for (String rid : rids) {
			User user = userManager.getById(rid);
			Message mess = new Message();
			mess.setTitle(title);
			mess.setContent(content);
			mess.setReceiverId(user.getId());
			mess.setReceiver(user.getUsername());
			mess.setStatus("0");//未发送
			mess.setTime(new Date());
			mess.setState("0");//消息状态    0为未查看
			mess.setTs(new Date());
			mess.setSponsor(this.getCurrUser().getUserName());
			mess.setSponsorId(this.getCurrUser().getUserID());
			this.messageManager.save(mess);
			if(file!=null){
				uploadFile(mess.getId());//上传附件
			}
		}
				return "sendAction";
	}
	
	/**
	 * 上传附件
	 */
	public void uploadFile(String objId){
		String fordername = "message";//保存文件的文件夹名称
		for(int i = 0; i < file.length; i++){
			String fullName = FileUtil.creatFullName(this.fileFileName[i], i);//创建附件全称
			String filePath = FileUtil.createFilePath(fordername, getRequest(), fullName);//创建附件全路径
			FileUtil.CopyFile(this.file[i].getPath(), filePath);//上传附件
			//保存附件信息
			Attachment attachment = new Attachment();
			attachment.setFileName(this.fileFileName[i]);		//文件名称
			attachment.setFilePath(filePath);				//文件路径
			attachment.setExtension(this.fileContentType[i]);	//文件类型
			attachment.setFullName(fullName);				//文件全称
			attachment.setRefId(objId);						//关联ID
			attachment.setAddUserId(getCurrUser().getUserID());	//创建人ID
			attachment.setAddUserName(getCurrUser().getUserName());	//创建人姓名
			attachment.setTs(new Date());		//创建时间
			attachmentManager.save(attachment);
		}
		
	}
	
	/**
	 * 下载附件
	 * @return
	 * @throws Exception
	 */
	public void download() throws Exception{
		String attachId = getRequest().getParameter("attachId");
		String fileName = attachmentManager.getById(attachId).getFileName();
		String filePath = attachmentManager.getById(attachId).getFilePath();
		//文件路径
		//String folderPath = getRequest().getSession().getServletContext().getRealPath("/upload") +File.separator+ "inform" ;
		FileUtil.downLoad(filePath,fileName, getResponse(), false);	//false为下载，true为在线打开，此处是下载所以是false
	}

	/**
	 * 获取附件URL
	 * @return
	 */
	private String getServletUrl()

	{
		ClientAttachmentApplication attachmentApp = (ClientAttachmentApplication) Platform.getBean(ClientAttachmentApplication.Bean);
		return attachmentApp.getDownloadPath();
	}
	
	
	/**
	 * 查询用户列表
	 * @return
	 */
	public String userList(){
		//将query中的分页数据放入ouRelationQuery中
		ouRelationQuery.setPageNumber(query.getPageNumber());
		ouRelationQuery.setPageSize(query.getPageSize());
		String username = getRequest().getParameter("username");
		String orgName = getRequest().getParameter("orgName");
		ouRelationQuery.setUsername(username);
		ouRelationQuery.setOrgname(orgName);
		Page page = this.organizationUserRelationManager.findPage(ouRelationQuery);
		super.saveCurrentPage(page, ouRelationQuery);
		getRequest().setAttribute("username", username);
		getRequest().setAttribute("orgName", orgName);
		getRequest().setAttribute("teacher", getRequest().getParameter("teacher"));//传值，教师的id
		return "userList";
	}
	
	/**	
		标记已读
	*/
	public String edit()
	{
			this.message.setState("1");
			this.message.setChackTime(new Date());
			this.messageManager.update(this.message);
			return LIST_ACTION;
	}
	
	/**	
		修改
	*/
	public String editdraft()
	{
		List<Attachment> attachments = attachmentManager.findAllBy("refId",this.message.getId());
		getRequest().setAttribute("attachments", attachments);
		return EDIT_JSP;
	}
	
	/**	
		保存修改
	*/
	public String saveEdit()
	{
		this.message.setStatus("1");
		this.message.setTime(new Date());
		this.messageManager.update(this.message);
		if(file!=null){
			uploadFile(message.getId());//上传附件
		}
		ReceiveMessage receicemess = new ReceiveMessage();
		receicemess.setReceiverId(this.message.getReceiverId());
		receicemess.setReceiver(this.message.getReceiver());
		receicemess.setTitle(this.message.getTitle());
		receicemess.setContent(this.message.getContent());
		receicemess.setTime(new Date());//发生时间
		receicemess.setState("0");//消息状态    0为未查看
		receicemess.setTs(new Date());
		receicemess.setSponsor(this.getCurrUser().getUserName());
		receicemess.setSponsorId(this.getCurrUser().getUserID());
		this.receiveMessageManager.save(receicemess);
		if(file!=null){
			uploadFile(receicemess.getId());//上传附件
		}
		return "draftAction";
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
			this.messageManager.removeById(id);
		}
		return "sendAction";
	}
	
	/**	
		删除草稿
	*/
	public String removeDraf()
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
			this.messageManager.removeById(id);
		}
		return "draftAction";
	}
	
	/**
	 * 删除附件
	 * @return
	 * @throws IOException 
	 */
	public void deleteAtt() throws IOException{
		String id = getRequest().getParameter("attachmentId");
		Attachment attachment = attachmentManager.getById(id);
		String filePath = attachment.getFilePath();
		File file = new File(filePath);//删除文件
		if(file.exists()){
			file.delete();
		}
		attachmentManager.removeById(attachment.getId());//删除数据库记录
		PrintWriter out = getResponse().getWriter();
		out.print("1");
		out.flush();
		out.close();
	}
	
	/**	
		查看
	*/
	public String view()
	{
		List<Attachment> attachments = attachmentManager.findAllBy("refId",this.message.getId());
		getRequest().setAttribute("attachments", attachments);
		String rs = this.message.getReceiver();
		String username = this.getCurrUser().getUserName();
		if(rs.equals(username)){
			this.message.setState("1");
			if(message.getChackTime()==null){
				this.message.setChackTime(new Date());
			}
			this.messageManager.update(this.message);
		}
		return VIEW_JSP;
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

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
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

	public ReceiveMessageQuery getReQuery() {
		return reQuery;
	}

	public void setReQuery(ReceiveMessageQuery reQuery) {
		this.reQuery = reQuery;
	}


}
