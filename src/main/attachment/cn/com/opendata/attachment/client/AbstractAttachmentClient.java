package cn.com.opendata.attachment.client;

import java.io.InputStream;
import java.util.List;

import cn.com.opendata.attachment.model.Attachment;

/**
 * 一个抽象的客户端，用于从服务器端上传、下载、删除、复制文件操作，一个真实的客户端，需要继承此客户端，方可使用
 * @author Administrator
 *
 */
public abstract class AbstractAttachmentClient
{

	/**
	 * 是否保存文件内容至数据库中，如果为true时，则将文件转换为base64格式存储至数据库中
	 */
	private String isSaveFileContent="false";
	/**
	 * 查询时是否返回文件内容,如为true时，则将文件以base64格式返回
	 */
	private String isReturnFile="false";//是否返回文件
	private ClientAttachmentService clientAttachService=new ClientAttachmentService();
	
	
	public abstract String getClientId();

	public abstract String getModuleName();

	public String getIsSaveFileContent()
	{
		return isSaveFileContent;
	}

	public void setIsSaveFileContent(String isSaveFileContent)
	{
		this.isSaveFileContent = isSaveFileContent;
	}

	/**
	 * 存储附件webservice服务方法（用于前台附件上传）
	 * @param exteriorId 外部主键ID
	 * @param attach_form_key  附件form id 即为附件另存名
	 * @param uploaded_file  控件的名称 <attach:file name="articleAttachPic" /> 的articleAttachPic
	 * @return 返回true 保存成功 返回false 保存失败
	 */
	public String saveAttach(String exteriorId,  String attach_form_key, String uploaded_file)
	{
		return clientAttachService.saveAttach(exteriorId, this.getClientId(),  attach_form_key,  uploaded_file, this.getModuleName(), this.getIsSaveFileContent());
	}

	
	/**
	 * 存储附件webservice服务方法（用于前台附件上传）
	 * @param exteriorId  外部主键ID
	 * @param attach_form_key 附件form id 即为附件另存名
	 * @param uploaded_file  控件的名称 <attach:file name="articleAttachPic" /> 的articleAttachPic
	 * @return 返回true 保存成功 返回false 保存失败
	 */
	public String saveAttach(String exteriorId, String attach_form_key, String[] uploaded_files)
	{
		String uploaded_file="";
		for(int i=0;i<uploaded_files.length;i++)
		{
			uploaded_file+=uploaded_files[i]+",";
		}
		return clientAttachService.saveAttach(exteriorId, this.getClientId(),  attach_form_key,  uploaded_file, this.getModuleName(), this.getIsSaveFileContent());
	}

	

	/**
	 * 根据外部ID进行删除附件，此删除，将删除此外部ID所关联的所有附件
	 * 
	 * @param exteriorId   外部ID
	 * @return 返回true 删除成功 返回false 删除失败
	 */
	public String deleteByExteriorId(String exteriorId)
	{
		return clientAttachService.deleteByExteriorId(exteriorId, this.getClientId());
	}
	
	/**
	 * 按照附件id删除附件
	 * 
	 * @return 返回true 删除成功 返回false 删除失败
	 */
	public String deleteById(String attachId)
	{
		return clientAttachService.deleteByAttachId(attachId, this.getClientId());
	}

	/**
	 * 复制文章附件
	 * 
	 * @param attachmentIds  附件ID集 用|隔开
	 * @return 返回附件ID集 用|隔开
	 */
	public String copeAttachs(String attachmentIds, String newExteriorId)
	{
		return clientAttachService.copeAttachs(attachmentIds, newExteriorId, this.getClientId());
	}

	/**
	 * 查询附件信息
	 * 
	 * @param exteriorId  外部ID,即业务数据的主键
	 * @return
	 */
	public List<Attachment> findAttachments(String exteriorId)
	{
		return clientAttachService.findAttachmentByXml(exteriorId, this.getClientId(), this.getModuleName(), this.getIsReturnFile());
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
	public Attachment findAttachment(String attachmentId)
	{
		return clientAttachService.findByAttachId(attachmentId, this.getIsReturnFile());
	}

	/**
	 * 获得临时文件输入流
	 * 
	 * @param attachFormKey
	 *            上传附件的formKey 即<attach:file name="articleAttachPic" />
	 *            的articleAttachPic
	 * @return
	 * @throws Exception
	 */
	public InputStream findTempTile(String attachFormKey) throws Exception
	{
		return clientAttachService.findTempTile(this.getClientId(), attachFormKey);
	}

	public String getIsReturnFile()
	{
		return isReturnFile;
	}

	public void setIsReturnFile(String isReturnFile)
	{
		this.isReturnFile = isReturnFile;
	}
	
}
