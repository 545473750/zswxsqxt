package cn.com.opendata.attachment.client;

import java.io.InputStream;
import java.util.List;

import net.sf.json.JSONArray;
import cn.com.opendata.attachment.model.Attachment;
import cn.com.opendata.attachment.model.impl.AttachmentImpl;
import cn.com.opendata.attachment.util.File2Base64Code;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public  class ClientAttachmentService
{

	private static String saveAttachName = "saveAttach";
	private static String deleteByAttachId = "deleteByAttachId";
	private static String deleteByExteriorId = "deleteByExteriorId";
	private static String copeAttachsName = "copeAttachs";
	private static String findXmlAttachName = "findXmlAttach";
	private static String findJsonAttachName = "findJsonAttach";
	private static String findByAttachId = "findByAttachId";
	private static String findTempFile = "findTempFile";

	/**
	 * 存储附件webservice服务方法（用于前台附件上传）
	 * 
	 * @param exteriorId
	 *            外部主键ID
	 * @param clientId
	 *            附件客户端ID
	 * @param attach_form_key
	 *            附件form id 即为附件另存名
	 * @param uploaded_file
	 *            控件的名称 <attach:file name="articleAttachPic" />
	 *            的articleAttachPic
	 * @param attachmentMode
	 *            附件的所属应用模块
	 * @param isSaveFileContent
	 *            是否保存文件内容，当为true时，将文件转成base64字符串存入数据库中，仅当需要进行读取文件内容进行业务操作的附件才这么做
	 * @return 返回true 保存成功 返回false 保存失败
	 */
	public String saveAttach(String exteriorId, String clientId, String attach_form_key, String uploaded_file, String attachmentMode, String isSaveFileContent)
	{
		return ServletServiceUtil.transferWebService(this.saveAttachName, exteriorId, clientId, attach_form_key, uploaded_file, attachmentMode,
				isSaveFileContent);
	}

	/**
	 * 存储附件webservice服务方法（用于前台附件上传）
	 * 
	 * @param exteriorId
	 *            外部主键ID
	 * @param clientId
	 *            附件客户端ID
	 * @param attach_form_key
	 *            附件form id 即为附件另存名
	 * @param uploaded_file
	 *            控件的名称 <attach:file name="articleAttachPic" />
	 *            的articleAttachPic
	 * @param attachmentMode
	 *            附件的所属应用模块
	 * @return 返回true 保存成功 返回false 保存失败
	 */
	public String saveAttach(String exteriorId, String clientId, String attach_form_key, String uploaded_file, String attachmentMode)
	{
		return ServletServiceUtil.transferWebService(this.saveAttachName, exteriorId, clientId, attach_form_key, uploaded_file, attachmentMode,
				"false");
	}
	
	/**
	 * 存储附件webservice服务方法（用于前台附件上传）
	 * 
	 * @param exteriorId
	 *            外部主键ID
	 * @param clientId
	 *            附件客户端ID
	 * @param attach_form_key
	 *            附件form id 即为附件另存名
	 * @param uploaded_file
	 *            控件的名称 <attach:file name="articleAttachPic" />
	 *            的articleAttachPic
	 * @param attachmentMode
	 *            附件的所属应用模块
	 * @param isSaveFileContent
	 *            是否保存文件内容，当为true时，将文件转成base64字符串存入数据库中，仅当需要进行读取文件内容进行业务操作的附件才这么做
	 * @return 返回true 保存成功 返回false 保存失败
	 */
	public String saveAttach(String exteriorId, String clientId, String attach_form_key, String[] uploaded_files, String attachmentMode, String isSaveFileContent)
	{
		String uploaded_file="";
		for(int i=0;i<uploaded_files.length;i++)
		{
			uploaded_file+=uploaded_files[i];
		}
		return ServletServiceUtil.transferWebService(this.saveAttachName, exteriorId, clientId, attach_form_key, uploaded_file, attachmentMode,
				isSaveFileContent);
	}

	/**
	 * 存储附件webservice服务方法（用于前台附件上传）
	 * 
	 * @param exteriorId
	 *            外部主键ID
	 * @param clientId
	 *            附件客户端ID
	 * @param attach_form_key
	 *            附件form id 即为附件另存名
	 * @param uploaded_file
	 *            控件的名称 <attach:file name="articleAttachPic" />
	 *            的articleAttachPic
	 * @param attachmentMode
	 *            附件的所属应用模块
	 * @return 返回true 保存成功 返回false 保存失败
	 */
	public String saveAttach(String exteriorId, String clientId, String attach_form_key, String[] uploaded_files, String attachmentMode)
	{
		String uploaded_file="";
		for(int i=0;i<uploaded_files.length;i++)
		{
			uploaded_file+=uploaded_files[i];
		}
		return ServletServiceUtil.transferWebService(this.saveAttachName, exteriorId, clientId, attach_form_key, uploaded_file, attachmentMode,
				"false");
	}

	
	/**
	 * 删除，通过附件ID
	 * @param attachId
	 * @param clientId
	 * @return
	 */
	public String deleteByAttachId(String attachId,  String clientId)
	{
		return ServletServiceUtil.transferWebService(this.deleteByAttachId,attachId,  clientId);
	}
	
	/**
	 * 删除，通过外部对象ID
	 * @param exteriorId
	 * @param clientId
	 * @return
	 */
	public String deleteByExteriorId(String exteriorId,String clientId)
	{
		return ServletServiceUtil.transferWebService(this.deleteByExteriorId,exteriorId, clientId);
	}
	

	/**
	 * 复制文章附件
	 * 
	 * @param attachmentIds
	 *            附件ID集 用|隔开
	 * @param clientId
	 *            客户端ID
	 * @return 返回附件ID集 用|隔开
	 */
	public String copeAttachs(String attachmentIds, String newExteriorId, String clientId)
	{
		return ServletServiceUtil.transferWebService(this.copeAttachsName, attachmentIds, newExteriorId, clientId);
	}

	/**
	 * 查询附件信息
	 * 
	 * @param exteriorId
	 *            外部ID,即业务数据的主键
	 * @param clientId
	 *            客户端ID 即附件服务器存储根目录名称
	 * @param attachmentMode
	 *            所属应用模块名称
	 * @param isReturnFile 是否返回文件內容 true 返回 false 不返回
	 * @return
	 */
	public List<Attachment> findAttachmentByXml(String exteriorId, String clientId, String attachmentMode, String isReturnFile)
	{
		String content = ServletServiceUtil.transferWebService(this.findXmlAttachName, exteriorId, clientId, attachmentMode, isReturnFile);
		XStream xStream = new XStream(new DomDriver());
		xStream.aliasType(AttachmentImpl.class.getSimpleName(), AttachmentImpl.class);
		return (List<Attachment>) xStream.fromXML(content);
	}

	/**
	 * 查询附件信息
	 * 
	 * @param exteriorId
	 *            外部ID,即业务数据的主键
	 * @param clientId
	 *            客户端ID 即附件服务器存储根目录名称
	 * @param attachmentMode
	 *            所属应用模块名称
	 * @param isReturnFile
	 *            是否返回文件內容 true 返回 false 不返回
	 * @return
	 */
	public List<Attachment> findAttachmentByJson(String exteriorId, String clientId, String attachmentMode, String isReturnFile)
	{
		String content = ServletServiceUtil.transferWebService(this.findJsonAttachName, exteriorId, clientId, attachmentMode, isReturnFile);
		JSONArray jsonArray = JSONArray.fromObject(content);
		return JSONArray.toList(jsonArray, AttachmentImpl.class);
	}

	/**
	 * 通过附件ID查询附件
	 * 
	 * @param attachmentId
	 *            附件ID
	 * @param isReturnFile
	 *            是否返回文件內容 true 返回 false 不返回
	 * @return
	 */
	public Attachment findByAttachId(String attachmentId, String isReturnFile)
	{
		String content = ServletServiceUtil.transferWebService(this.findByAttachId, attachmentId, isReturnFile);
		XStream xStream = new XStream(new DomDriver());
		xStream.aliasType(AttachmentImpl.class.getSimpleName(), AttachmentImpl.class);
		return (Attachment) xStream.fromXML(content);
	}

	/**
	 * 查询附件信息
	 * 
	 * @param exteriorId
	 *            外部ID,即业务数据的主键
	 * @param clientId
	 *            客户端ID 即附件服务器存储根目录名称
	 * @param attachmentMode
	 *            所属应用模块名称
	 * @return
	 */
	public List<Attachment> findAttachmentByXml(String exteriorId, String clientId, String attachmentMode)
	{
		return this.findAttachmentByXml(exteriorId, clientId, attachmentMode, "false");
	}

	/**
	 * 查询附件信息
	 * 
	 * @param exteriorId
	 *            外部ID,即业务数据的主键
	 * @param clientId
	 *            客户端ID 即附件服务器存储根目录名称
	 * @param attachmentMode
	 *            所属应用模块名称
	 * @return
	 */
	public List<Attachment> findAttachmentByJson(String exteriorId, String clientId, String attachmentMode)
	{
		return this.findAttachmentByJson(exteriorId, clientId, attachmentMode, "false");
	}

	/**
	 * 通过附件ID查询附件
	 * 
	 * @param attachmentId
	 *            附件ID
	 * @param isReturnFile
	 *            是否返回文件內容 true 返回 false 不返回
	 * @return
	 */
	public Attachment findByAttachId(String attachmentId)
	{
		return this.findByAttachId(attachmentId, "false");
	}

	/**
	 * 获得临时文件输入流
	 * 
	 * @param clientId
	 *            客户端ID
	 * @param attachFormKey
	 *            上传附件的formKey 即<attach:file name="articleAttachPic" />
	 *            的articleAttachPic
	 * @return
	 * @throws Exception
	 */
	public InputStream findTempTile(String clientId, String attachFormKey) throws Exception
	{
		String content = ServletServiceUtil.transferWebService(this.findTempFile, clientId, attachFormKey);
		InputStream in = null;
		if (!content.equals(""))
		{
			in = File2Base64Code.base64ToInputStream(content);
		}
		return in;
	}
}
